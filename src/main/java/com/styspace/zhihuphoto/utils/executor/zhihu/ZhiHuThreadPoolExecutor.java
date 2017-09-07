/**
 * Package name:com.styspace.zhihuphoto.utils
 * File name:ZhiHuThreadPoolExecutor.java
 * Date:2016年12月19日-下午2:02:55
 * feiniu.com Inc.Copyright (c) 2013-2015 All Rights Reserved.
 *
 */
package com.styspace.zhihuphoto.utils.executor.zhihu;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 线程池管理
 * 单例模式，生成下载线程池和解析线程池
 * @ClassName ZhiHuThreadPoolExecutor
 * @Description 
 * @date 2016年12月19日 下午2:02:55
 * @author tingyong.song
 * @version 1.0.0
 *
 */
@Component
public class ZhiHuThreadPoolExecutor {
	private static final Logger LOGGER = LoggerFactory.getLogger(ZhiHuThreadPoolExecutor.class);
	
	private ThreadPoolExecutor downloadThreadExecutor;// 下载线程池
	private ThreadPoolExecutor parserThreadExecutor;// 解析线程池
	
	// 私有构造器
	private  ZhiHuThreadPoolExecutor(){
		LOGGER.info("初始化线程池...");
		this.downloadThreadExecutor = new ThreadPoolExecutor(2, 2,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
		this.parserThreadExecutor = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
	}
	
	// 静态内部类
	private static class ZhiHuThreadPoolExecutorHolder{
		private static ZhiHuThreadPoolExecutor instance = new ZhiHuThreadPoolExecutor();
	}
	
	// 获取实例的唯一途径
	public static ZhiHuThreadPoolExecutor getInstance(){
		return ZhiHuThreadPoolExecutorHolder.instance;
	}
	
	public ThreadPoolExecutor getDownloadThreadPoolExecutor() {
		return this.downloadThreadExecutor;
	}
	
	public ThreadPoolExecutor getParserThreadPoolExecutor() {
		return this.parserThreadExecutor;
	}
	
}
