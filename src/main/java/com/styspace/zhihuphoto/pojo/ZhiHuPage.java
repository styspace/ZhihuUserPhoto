/**
 * Package name:com.styspace.zhihuphoto.pojo
 * File name:ZhiHuPage.java
 * Date:2016年12月1日-下午9:39:51
 * feiniu.com Inc.Copyright (c) 2013-2015 All Rights Reserved.
 *
 */
package com.styspace.zhihuphoto.pojo;

/**
 * @ClassName ZhiHuPage
 * @Description 知乎网页信息
 * @date 2016年12月1日 下午9:39:51
 * @author tingyong.song
 * @version 1.0.0
 *
 */
public class ZhiHuPage {
	private String url;// 地址
    private int statusCode;// 响应状态码
    private String html;// 网页内容
    
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}
    
    
	
}
