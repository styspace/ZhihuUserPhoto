/**
 * Package name:com.styspace.zhihuphoto.utils
 * File name:ZhiHuHttpClientUtils.java
 * Date:2016年12月16日-上午11:00:29
 * feiniu.com Inc.Copyright (c) 2013-2015 All Rights Reserved.
 *
 */
package com.styspace.zhihuphoto.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.BestMatchSpecFactory;
import org.apache.http.impl.cookie.BrowserCompatSpecFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @ClassName ZhiHuHttpClientUtils
 * @Description httpClient工具类，用户获取httpClient连接，httpClientContext连接上下文，以及一些不涉及到连接状态的方法
 * @date 2016年12月16日 上午11:00:29
 * @author tingyong.song
 * @version 1.0.0
 *
 */
@Component
public class ZhiHuHttpClientUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(ZhiHuHttpClientUtils.class);
	
	private static String cookiePath;// cookies序列化数据存放位置
	
	@Value("${cookiePath}")  
    public void setCookiePath(String cookiePath) {  
		this.cookiePath = cookiePath;  
    } 
	
	// 得到httpClient连接——被代理类调用，所以只会调用一次，不会有线程安全问题
	public static CloseableHttpClient getHttpClient() {
		LOGGER.info("======得到CloseableHttpClient======");
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
		connManager.setMaxTotal(200);//设置最大连接数200
		connManager.setDefaultMaxPerRoute(50);//设置每个路由默认连接数
		// cookies策略
		RequestConfig globalConfig = RequestConfig.custom()
				.setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY)
				.build();
		return HttpClients.custom().setConnectionManager(connManager).setDefaultRequestConfig(globalConfig).build();
	}
	
	// 得到httpClientContext连接上下文——同样也是被单例类调用一次
	public static HttpClientContext getHttpClientContext() {
		HttpClientContext context = null;
		context = HttpClientContext.create();
		Registry<CookieSpecProvider> registry = RegistryBuilder
				.<CookieSpecProvider> create()
				.register(CookieSpecs.BEST_MATCH, new BestMatchSpecFactory())
				.register(CookieSpecs.BROWSER_COMPATIBILITY,
						new BrowserCompatSpecFactory()).build();
		context.setCookieSpecRegistry(registry);
		return context;
	}
	
	// 将cookies上反序列化
	public static Object deserializeHttpClientCookies() {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(cookiePath)));
			Object object = ois.readObject();
			ois.close();
			return object;
		} catch (Exception e) {
			LOGGER.info("not found cookie");
			return null;
		}
	}
	
	// 序列化cookies信息
	public static void serializeCookieStore(Object cookiesInfo) {
		FileOutputStream fos;
		try{
			fos = new FileOutputStream(cookiePath);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(cookiesInfo);
			LOGGER.info("cookies序列化成功");
			oos.flush();
			fos.close();
			oos.close();
		}catch(Exception e){
			LOGGER.info("cookies序列化失败：" + e.getMessage());
		}
	}
}
