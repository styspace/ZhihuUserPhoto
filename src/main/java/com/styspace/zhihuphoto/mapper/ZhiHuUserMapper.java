/**
 * Package name:com.styspace.zhihuphoto.mapper
 * File name:ZhiHuUserMapper.java
 * Date:2016年12月14日-下午3:28:35
 * feiniu.com Inc.Copyright (c) 2013-2015 All Rights Reserved.
 *
 */
package com.styspace.zhihuphoto.mapper;

import com.styspace.zhihuphoto.pojo.ZhiHuUser;

/**
 * @ClassName ZhiHuUserMapper
 * @Description 知乎用户数据库操作mapper文件
 * @date 2016年12月14日 下午3:28:35
 * @author tingyong.song
 * @version 1.0.0
 *
 */
public interface ZhiHuUserMapper {
	/**
	 * 插入用户信息
	 * insertUser
	 * @param user
	 * @return
	 *int
	 * @exception
	 * @since  1.0.0
	 */
	public int insertUser(ZhiHuUser user);
	
	/**
	 * 用户是否已存在在数据库中
	 * isExits
	 * @param username
	 * @return
	 *int
	 * @exception
	 * @since  1.0.0
	 */
	public int isExits(String username);
}
