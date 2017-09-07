/**
 * Package name:com.styspace.zhihuphoto.utils
 * File name:ParseTask.java
 * Date:2016年12月2日-下午7:20:34
 * feiniu.com Inc.Copyright (c) 2013-2015 All Rights Reserved.
 *
 */
package com.styspace.zhihuphoto.utils.executor.zhihu;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.styspace.zhihuphoto.dao.mybatis.IndexUrlDao;
import com.styspace.zhihuphoto.dao.mybatis.ZhihuUserDao;
import com.styspace.zhihuphoto.pojo.ZhiHuPage;
import com.styspace.zhihuphoto.pojo.ZhiHuUser;
import com.styspace.zhihuphoto.utils.parser.zhihu.DetailPageParser;
import com.styspace.zhihuphoto.utils.parser.zhihu.FollowingListPageParser;

/**
 * @ClassName ParseTask
 * @Description 
 * @date 2016年12月2日 下午7:20:34
 * @author tingyong.song
 * @version 1.0.0
 *
 */
public class ParseTask implements Runnable {
	private static final Logger LOGGER = LoggerFactory.getLogger(ParseTask.class);
//	@Value("${dbEnable}")
//	private boolean dbEnable;
//	@Autowired
//	private ZhihuUserDao zhihuUserDao;
//	@Autowired
//	private IndexUrlDao indexUrlDao;
	
	public static AtomicInteger parseUserCount = new AtomicInteger(0);// 统计爬取用户数
	public static ZhiHuThreadPoolExecutor zhiHuThreadPoolExecutor = ZhiHuThreadPoolExecutor.getInstance();// 线程池类，单例模式
	
	private ZhihuUserDao zhihuUserDao;
	private IndexUrlDao indexUrlDao;
	
	public static volatile boolean isStopDownload = false;// 下载线程过多的话，暂停下载
	public static final String FOLLOWEES_API_URL = "https://www.zhihu.com/api/v4/members/";// 关注列表获取信息的接口url
	
	public static volatile boolean dbEnable = true;
	
	private ZhiHuPage page;
	
	public ParseTask(ZhiHuPage page, ZhihuUserDao zhihuUserDao, IndexUrlDao indexUrlDao) {
		this.zhihuUserDao = zhihuUserDao;
		this.indexUrlDao = indexUrlDao;
		this.page = page;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// 有两种请求返回的结构，一种是用户首页，得到的html格式，另外一种是关注人列表，得到的json格式
		Document doc = Jsoup.parse(this.page.getHtml());
		if(doc.select("title").size() != 0) {
			 // 用户首页解析
			ZhiHuUser user = DetailPageParser.getUserInfoByParser(doc);
			
			if(dbEnable && 0 == zhihuUserDao.isExits(user.getUserToken())){
				// 写入数据库
				zhihuUserDao.insertUser(user);
				parseUserCount.incrementAndGet();
				LOGGER.info("解析用户成功：" + user.toString());
			}
			
			// 获取改用户的关注列表数据，10个每页
			for (int i = 0; i < user.getFollowees()/10 + 1; i++) {
				if(!isStopDownload && !zhiHuThreadPoolExecutor.getDownloadThreadPoolExecutor().isShutdown() 
						&& zhiHuThreadPoolExecutor.getDownloadThreadPoolExecutor().getQueue().size() <= 100){
					handler(formatGetFolloweeUrl(10*i, user.getUserToken()), false);
				}
			}
		}else{
			// 关注人列表解析
			if(!isStopDownload && !zhiHuThreadPoolExecutor.getDownloadThreadPoolExecutor().isShutdown() 
					&& zhiHuThreadPoolExecutor.getDownloadThreadPoolExecutor().getQueue().size() <= 100){
				// 得到关注人首页的列表
				List<String> userHomepageList = FollowingListPageParser.getFolloweesByParser(page);
				for(String url : userHomepageList){
					handler(url, true);
				}
			}
		}

	}
	
	// 拼接获取关注列表的接口url
	private String formatGetFolloweeUrl(int offset, String userToken) {
		String url = FOLLOWEES_API_URL + userToken + "/followees?include=data%5B*%5D.answer_count%2Carticles_count%2Cfollower_count%2Cis_followed%2Cis_following%2Cbadge%5B%3F(type%3Dbest_answerer)%5D.topics&offset=" + offset + "&limit=10";
		return url;
	}
	
	
	private void handler(String url, boolean isInsertDb) {
		// 如果数据库不可以，直接添加到任务池中
		if(!dbEnable || !isInsertDb){
			zhiHuThreadPoolExecutor.getDownloadThreadPoolExecutor().execute(new DownloadTask(url, zhihuUserDao, indexUrlDao));
			return;
		}else{			
			// 判断是否已在数据库中
			if(indexUrlDao.isExits(url) > 0){
				LOGGER.info(url + ":isExits...");
			}else if (indexUrlDao.isExits(url) == 0 && !zhiHuThreadPoolExecutor.getDownloadThreadPoolExecutor().isShutdown() 
					&& zhiHuThreadPoolExecutor.getDownloadThreadPoolExecutor().getQueue().size() <= 100) {
				indexUrlDao.insertIndexUrl(url);
				zhiHuThreadPoolExecutor.getDownloadThreadPoolExecutor().execute(new DownloadTask(url, zhihuUserDao, indexUrlDao));
			}
		}
	}

}
