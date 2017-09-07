/**
 * Package name:com.styspace.zhihuphoto.service.impl
 * File name:StartServiceImpl.java
 * Date:2016年12月1日-下午4:33:04
 * feiniu.com Inc.Copyright (c) 2013-2015 All Rights Reserved.
 *
 */
package com.styspace.zhihuphoto.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.styspace.zhihuphoto.service.interfaces.StartService;
import com.styspace.zhihuphoto.utils.SpiderClient;
import com.styspace.zhihuphoto.utils.executor.zhihu.ThreadPoolMonitor;
import com.styspace.zhihuphoto.utils.zhihu.LoginUtils;
import com.styspace.zhihuphoto.utils.zhihu.ZhiHuHttpClient;

/**
 * @ClassName StartServiceImpl
 * @Description 
 * @date 2016年12月1日 下午4:33:04
 * @author tingyong.song
 * @version 1.0.0
 *
 */
@Service
public class StartServiceImpl implements StartService {
	private static final Logger LOGGER = LoggerFactory.getLogger(StartServiceImpl.class);
	@Autowired
	private SpiderClient spiderClient;
	@Autowired
	private LoginUtils loginUtils;

	/* (non-Javadoc)
	 * @see com.styspace.zhihuphoto.service.interfaces.StartService#getPhoto(java.lang.String)
	 */
	public Object getPhoto(String captch) {
		ZhiHuHttpClient zhiHuHttpClient = ZhiHuHttpClient.getInstance();
		if(!zhiHuHttpClient.deserializeCookieStore() && StringUtils.isEmpty(captch)){
			// 每次都会刷新，已最新的为准
			String captchPicUrl = loginUtils.getCaptchPic();
			JSONObject response = new JSONObject();
			response.put("picUrl", captchPicUrl);
			return response;
		}
		LOGGER.info("start...");
		spiderClient.startCrawl(captch);
		return null;
	}

}
