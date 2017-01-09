/**
 * Package name:com.styspace.zhihuphoto.utils
 * File name:DownloadTask.java
 * Date:2016年12月1日-下午11:25:25
 * feiniu.com Inc.Copyright (c) 2013-2015 All Rights Reserved.
 *
 */
package com.styspace.zhihuphoto.utils;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.styspace.zhihuphoto.dao.mybatis.IndexUrlDao;
import com.styspace.zhihuphoto.dao.mybatis.ZhihuUserDao;
import com.styspace.zhihuphoto.pojo.ZhiHuPage;

/**
 * @ClassName DownloadTask
 * @Description 
 * @date 2016年12月1日 下午11:25:25
 * @author tingyong.song
 * @version 1.0.0
 *
 */
public class DownloadTask implements Runnable {
	private static final Logger LOGGER = LoggerFactory.getLogger(DownloadTask.class);
	
	// 单例模式
	private static ZhiHuHttpClient zhiHuHttpClient = ZhiHuHttpClient.getInstance();
	
	//也是单例模式
	private static ZhiHuThreadPoolExecutor zhiHuThreadPoolExecutor = ZhiHuThreadPoolExecutor.getInstance();
	
	private ZhihuUserDao zhihuUserDao;
	private IndexUrlDao indexUrlDao;
	
	private String url = "";
	
	public DownloadTask(String url, ZhihuUserDao zhihuUserDao, IndexUrlDao indexUrlDao){
		this.zhihuUserDao = zhihuUserDao;
		this.indexUrlDao = indexUrlDao;
		this.url = url;
	}

	@Override
	public void run() {
		HttpGet request = new HttpGet(this.url);
		try {
			ZhiHuPage page = zhiHuHttpClient.getPage(request);
			int status = page.getStatusCode();

			LOGGER.info(Thread.currentThread().getName() + " executing request " + page.getUrl() + "   status:" + status);
			if(status == HttpStatus.SC_OK){
				
				zhiHuThreadPoolExecutor.getParserThreadPoolExecutor().execute(new ParseTask(page, zhihuUserDao, indexUrlDao));
			}
			else if(status == 502 || status == 504 || status == 500 || status == 429){
				/**
				 * 将请求继续加入线程池
                 */
				Thread.sleep(100);
				zhiHuThreadPoolExecutor.getDownloadThreadPoolExecutor().execute(new DownloadTask(url, zhihuUserDao, indexUrlDao));
				return;
			}
		} catch (InterruptedException e) {
			LOGGER.error("InterruptedException",e);
		} catch (NullPointerException e){
			LOGGER.error("NullPointerException",e);
		}
	}

}
