/**
 * Package name:com.styspace.zhihuphoto.utils
 * File name:LoginUtils.java
 * Date:2016年12月16日-下午2:43:18
 * feiniu.com Inc.Copyright (c) 2013-2015 All Rights Reserved.
 *
 */
package com.styspace.zhihuphoto.utils.zhihu;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.styspace.zhihuphoto.pojo.ZhiHuPage;
import com.styspace.zhihuphoto.utils.SpiderClient;

/**
 * @ClassName LoginUtils
 * @Description 登陆验证
 * @date 2016年12月16日 下午2:43:18
 * @author tingyong.song
 * @version 1.0.0
 *
 */
@Component
public class LoginUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(SpiderClient.class);
	
	@Value("${indexPageUrl}")
	private String indexPageUrl;// 知乎首页
	@Value("${loginPage}")
	private String loginPage;// 登陆界面
	@Value("${phoneLoginUrl}")
	private String phoneLoginUrl;//手机登陆接口
	@Value("${loginPhone}")
	private String loginPhone;// 手机登陆账号
	@Value("${loginPassword}")
	private String loginPassword;// 密码
	@Value("${loginGifUrl}")
	private String loginGifUrl; //登录验证码获取地址
	
	// 验证码地址
	public String getCaptchPic() {	
		// 验证码图片
		return loginGifUrl;
	}
	
	// 登录
	public boolean login(ZhiHuHttpClient zhiHuHttpClient, String captch) {
		boolean isLogin = false;
		
		HttpPost request = new HttpPost(phoneLoginUrl);
		
		// 登陆入参
		List<NameValuePair> formParams = new ArrayList<NameValuePair>();
		formParams.add(new BasicNameValuePair("phone_num", loginPhone));
		formParams.add(new BasicNameValuePair("password", loginPassword));
		formParams.add(new BasicNameValuePair("_xsrf", ""));//这个参数可以不用
		formParams.add(new BasicNameValuePair("captch", captch));
		
		UrlEncodedFormEntity entity = null;
        try {
            entity = new UrlEncodedFormEntity(formParams, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
		request.setEntity(entity);
		
		ZhiHuPage page = zhiHuHttpClient.getPage(request);
		
		if(page!= null && 200 == page.getStatusCode()){
			JSONObject responseObj = JSONObject.parseObject(page.getHtml());
			if(0 == responseObj.getIntValue("r")){
				LOGGER.info("知乎登陆成功...");
				isLogin = true;
				
				// 需要在访问主页，然后序列化cookies，否则cookies里面的认证信息就没有了，就会报401的错误
				HttpGet getRequest = new HttpGet(indexPageUrl);
				page = zhiHuHttpClient.getPage(getRequest);
				
				// 序列化cookies
				HttpClientContext context = zhiHuHttpClient.getHttpClientContext();
				ZhiHuHttpClientUtils.serializeCookieStore(context.getCookieStore());
			}else{
				LOGGER.info("登陆失败：" + page.getHtml());
			}
		}
		
		return isLogin;
	}

}
