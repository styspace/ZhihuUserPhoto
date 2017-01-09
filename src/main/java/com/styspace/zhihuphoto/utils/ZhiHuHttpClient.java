/**
 * Package name:com.styspace.zhihuphoto.utils
 * File name:ZhiHuHttpClient.java
 * Date:2016年12月16日-上午11:06:08
 * feiniu.com Inc.Copyright (c) 2013-2015 All Rights Reserved.
 *
 */
package com.styspace.zhihuphoto.utils;


import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.styspace.zhihuphoto.pojo.ZhiHuPage;

/**
 * @ClassName ZhiHuHttpClient
 * @Description 单例模式，httpClient行为类，可以有各种操作，与连接上下文有关的方法
 * @date 2016年12月16日 上午11:06:08
 * @author tingyong.song
 * @version 1.0.0
 *
 */
public class ZhiHuHttpClient {
	private static final Logger LOGGER = LoggerFactory.getLogger(ZhiHuHttpClient.class);
	
	private CloseableHttpClient httpClient;
	private HttpClientContext httpClientContext;
	
	// 内部静态类,实现单例模式=========开始===========
	private static class ZhiHuHttpClientHolder{
		private final static ZhiHuHttpClient instance = new ZhiHuHttpClient();
	}
	
	private ZhiHuHttpClient() {
		LOGGER.info("ZhiHuHttpClient initializing...");
		this.httpClient = ZhiHuHttpClientUtils.getHttpClient();
		this.httpClientContext = ZhiHuHttpClientUtils.getHttpClientContext();
	}
	
	public static final ZhiHuHttpClient getInstance() {
		return ZhiHuHttpClientHolder.instance;
	}
	
	// 得到单例类中的httpClient属性值
	public CloseableHttpClient getHttpClient() {
		return this.httpClient;
	}
	
	// 得到单例类中的httpClientContext属性值
	public HttpClientContext getHttpClientContext() {
		return this.httpClientContext;
	}
	
	// 反序列化cookies信息，实现模拟登陆
	public boolean deserializeCookieStore() {
		try {
			CookieStore cookieStore = (CookieStore) ZhiHuHttpClientUtils.deserializeHttpClientCookies();
			if(cookieStore == null){
				return false;
			}
            httpClientContext.setCookieStore(cookieStore);
            return true;
		} catch (Exception e) {
			LOGGER.info("饭序列化cookies失败");
			return false;
		}
	}
	
	// 根据请求方式得到详细页面结果
	public ZhiHuPage getPage(HttpRequestBase request) {
		ZhiHuPage page = new ZhiHuPage();
		
		try {
			CloseableHttpResponse response = this.httpClient.execute(request, this.httpClientContext);
			page.setStatusCode(response.getStatusLine().getStatusCode());
	        page.setUrl(request.getURI().toString());
	        
	        if(200 == page.getStatusCode()){
	        	page.setHtml(EntityUtils.toString(response.getEntity()));
	        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOGGER.info(e.toString());
		} finally {
			// 释放连接
			request.releaseConnection();
		}
		return page;
	}
}
