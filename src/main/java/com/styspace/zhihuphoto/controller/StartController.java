/**
 * Package name:com.styspace.zhihuphoto.controller
 * File name:startController.java
 * Date:2016年12月1日-下午4:27:15
 * feiniu.com Inc.Copyright (c) 2013-2015 All Rights Reserved.
 *
 */
package com.styspace.zhihuphoto.controller;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.styspace.zhihuphoto.service.interfaces.StartService;

/**
 * @ClassName startController
 * @Description 
 * @date 2016年12月1日 下午4:27:15
 * @author tingyong.song
 * @version 1.0.0
 *
 */
@Controller
@RequestMapping("/start")
public class StartController extends BaseController{
	@Autowired
	protected StartService startService;
	
	@ResponseBody
	@POST
	@RequestMapping(value="/getPhoto", method=RequestMethod.POST)
	public Object getPhoto(@FormParam("captch") String captch) {
		Object result = startService.getPhoto(captch);
		return result;
	}
}
