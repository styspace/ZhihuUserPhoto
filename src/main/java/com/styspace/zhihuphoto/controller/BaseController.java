/**
 * Package name:com.styspace.zhihuphoto.controller
 * File name:BaseController.java
 * Date:2016年12月1日-上午11:21:07
 * feiniu.com Inc.Copyright (c) 2013-2015 All Rights Reserved.
 *
 */
package com.styspace.zhihuphoto.controller;

import com.styspace.zhihuphoto.constant.ResultCodeConstant;
import com.styspace.zhihuphoto.constant.ResultDescConstant;
import com.styspace.zhihuphoto.pojo.ReponseResult;

/**
 * @ClassName BaseController
 * @Description 返回结果基类：定义返回数据结构
 * @date 2016年12月1日 上午11:21:07
 * @author tingyong.song
 * @version 1.0.0
 *
 */
public class BaseController {
	public static Object getResponse(Object result, long elapsedTime) {
		ReponseResult reResult;
		if(result == null){
			reResult = new ReponseResult(elapsedTime, ResultCodeConstant.NORMAL, ResultDescConstant.NORMAL, null);
		}else{
			reResult = new ReponseResult(elapsedTime, ResultCodeConstant.OK, ResultDescConstant.OK, result);
		}
		return reResult;
	}
}
