/**
 * Package name:com.styspace.zhihuphoto.dao.mybatis
 * File name:ZhihuUserDao.java
 * Date:2016年12月14日-下午2:34:52
 * feiniu.com Inc.Copyright (c) 2013-2015 All Rights Reserved.
 *
 */
package com.styspace.zhihuphoto.dao.mybatis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.styspace.zhihuphoto.mapper.ZhiHuUserMapper;
import com.styspace.zhihuphoto.pojo.ZhiHuUser;


/**
 * @ClassName ZhihuUserDao
 * @Description 知乎用户数据库操作
 * @date 2016年12月14日 下午2:34:52
 * @author tingyong.song
 * @version 1.0.0
 *
 */
@Repository
public class ZhihuUserDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(ZhihuUserDao.class);
	
	@Autowired
	private ZhiHuUserMapper zhiHuUserMapper;
	
	// 插入用户数据
	public int insertUser(ZhiHuUser user) {
		LOGGER.info("[Insert]:" + user.toString());
		return zhiHuUserMapper.insertUser(user);
	}
	
	// 判断用户是否已存在在数据库中
	public int isExits(String userToken) {
		int isExits = zhiHuUserMapper.isExits(userToken);
		LOGGER.info(userToken + " [isExits]:" + isExits);
		return isExits;
	}
	
}
