/**
 * Package name:com.styspace.zhihuphoto.utils.executor
 * File name:ThreadPoolMonitor.java
 * Date:2016年12月1日-下午6:24:52
 * feiniu.com Inc.Copyright (c) 2013-2015 All Rights Reserved.
 *
 */
package com.styspace.zhihuphoto.utils.executor.zhihu;

import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName ThreadPoolMonitor
 * @Description 线程池状态管理线程
 * @date 2016年12月1日 下午6:24:52
 * @author tingyong.song
 * @version 1.0.0
 *
 */
public class ThreadPoolMonitor implements Runnable {
	private static final Logger LOGGER = LoggerFactory.getLogger(ThreadPoolMonitor.class);
	
	public static boolean isStop = false;// 是否停止监控
	private ThreadPoolExecutor executor;
	private String name = "";
	// 构造函数
	public ThreadPoolMonitor(ThreadPoolExecutor executor,String name) {
		this.executor = executor;
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while (!isStop) {
			LOGGER.info(name + 
                    String.format("[monitor] [%d/%d] Active: %d, Completed: %d, queueSize: %d, Task: %d, isShutdown: %s, isTerminated: %s",
                            this.executor.getPoolSize(),
                            this.executor.getCorePoolSize(),
                            this.executor.getActiveCount(),
                            this.executor.getCompletedTaskCount(),
                            this.executor.getQueue().size(),
                            this.executor.getTaskCount(),
                            this.executor.isShutdown(),
                            this.executor.isTerminated()));
			try {
				// 每秒钟跑一次
				Thread.sleep(1000);
			} catch (Exception e) {
				LOGGER.error("exception", e);
			}
		}
	}

}
