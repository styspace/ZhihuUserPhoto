/**
 * Package name:com.styspace.zhihuphoto.pojo
 * File name:ReponseResult.java
 * Date:2016年12月1日-上午11:24:13
 * feiniu.com Inc.Copyright (c) 2013-2015 All Rights Reserved.
 *
 */
package com.styspace.zhihuphoto.pojo;

import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName ReponseResult
 * @Description 
 * @date 2016年12月1日 上午11:24:13
 * @author tingyong.song
 * @version 1.0.0
 *
 */
public class ReponseResult {
	private long elapsedTime;// 执行时间
	private int errorCode;// 状态码
	private String errorDesc;// 状态描述
	private Object body;// 返回结果
	
	// 构造函数
	public ReponseResult(long elapsedTime, int errorCode, String errorDesc, Object body){
		this.elapsedTime = elapsedTime;
		this.errorCode = errorCode;
		if(errorDesc == null){
			this.errorDesc = "";
		}else{
			this.errorDesc = errorDesc;
		}
		if(body == null){
			this.body = new JSONObject();
		}else{
			this.body = body;	
		}
	}
	
	public long getElapsedTime() {
		return elapsedTime;
	}
	public void setElapsedTime(long elapsedTime) {
		this.elapsedTime = elapsedTime;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorDesc() {
		return errorDesc;
	}
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}
	public Object getBody() {
		return body;
	}
	public void setBody(Object body) {
		this.body = body;
	}
	
	public String toString() {
		return "errorCode:" + errorCode + "\terrorDesc:" + errorDesc + "\telapsedTime:" + elapsedTime + "\tbody:" + body;
	}
}
