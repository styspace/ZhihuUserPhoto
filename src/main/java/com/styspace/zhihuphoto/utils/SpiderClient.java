/**
 * Package name:com.styspace.zhihuphoto.utils
 * File name:WorkClient.java
 * Date:2016年12月1日-下午7:10:21
 * feiniu.com Inc.Copyright (c) 2013-2015 All Rights Reserved.
 *
 */
package com.styspace.zhihuphoto.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.styspace.zhihuphoto.dao.mybatis.IndexUrlDao;
import com.styspace.zhihuphoto.dao.mybatis.ZhihuUserDao;
import com.styspace.zhihuphoto.utils.executor.ThreadPoolMonitor;


/**
 * @ClassName WorkClient
 * @Description 爬虫工作线程
 * @date 2016年12月1日 下午7:10:21
 * @author tingyong.song
 * @version 1.0.0
 *
 */
@Component
public class SpiderClient {
	private static final Logger LOGGER = LoggerFactory.getLogger(SpiderClient.class);
	@Autowired
	private LoginUtils loginUtils;
	@Autowired
	private ZhihuUserDao zhihuUserDao;
	@Autowired
	private IndexUrlDao indexUrlDao;
	
	@Value("${downloadPageMaxCount}")
	private long downloadPageMaxCount;
	
	@Value("${homepage}")
	private String homepage;
	
	// 单例模式，获取线程池
	private static final ZhiHuThreadPoolExecutor zhiHuThreadPoolExecutor = ZhiHuThreadPoolExecutor.getInstance();
	
	public void startCrawl(String captch) {
		ZhiHuHttpClient zhiHuHttpClient = ZhiHuHttpClient.getInstance();
		// 是否有cookies文件来反序列化，如果没有，重新登陆
		if(!zhiHuHttpClient.deserializeCookieStore()){
			LOGGER.info("反序列化cookies失败，正在重新登陆...");
			boolean isLogin = loginUtils.login(zhiHuHttpClient, captch);
			if(!isLogin){
				LOGGER.info("登陆失败...");
				return;
			}
		}
		new Thread(new ThreadPoolMonitor(zhiHuThreadPoolExecutor.getDownloadThreadPoolExecutor(), "DownloadPage ThreadPool")).start();
        new Thread(new ThreadPoolMonitor(zhiHuThreadPoolExecutor.getParserThreadPoolExecutor(), "ParsePage ThreadPool")).start();
        
        zhiHuThreadPoolExecutor.getDownloadThreadPoolExecutor().execute(new DownloadTask(homepage, zhihuUserDao, indexUrlDao));
//        manageZhiHuClient();
	}
	
	// 监控整个线程
	public void manageZhiHuClient() {
		while (true) {
			long downloadPageCountCur = zhiHuThreadPoolExecutor.getDownloadThreadPoolExecutor().getTaskCount();
			if(downloadPageCountCur > downloadPageMaxCount && !zhiHuThreadPoolExecutor.getDownloadThreadPoolExecutor().isShutdown()){
				ParseTask.isStopDownload = true;
				zhiHuThreadPoolExecutor.getDownloadThreadPoolExecutor().shutdown();
			}
			// 下载线程全部结束之后才关闭解析线程
			if(zhiHuThreadPoolExecutor.getDownloadThreadPoolExecutor().isTerminated() 
					&& !zhiHuThreadPoolExecutor.getParserThreadPoolExecutor().isShutdown()){
				zhiHuThreadPoolExecutor.getParserThreadPoolExecutor().shutdown();
			}
			
			// 下载和解析线程全部结束之后,关闭线程池监控
			if(zhiHuThreadPoolExecutor.getParserThreadPoolExecutor().isTerminated()){
				ThreadPoolMonitor.isStop = true;
				LOGGER.info("--------------爬取结束--------------");
				LOGGER.info("获取用户数:" +ParseTask.parseUserCount.get());
                break;
			}
			
			// 1s检查一次
 			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
