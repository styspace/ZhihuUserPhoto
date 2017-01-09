/**
 * Package name:com.styspace.zhihuphoto.mapper
 * File name:IndexUrlMapper.java
 * Date:2016年12月14日-下午5:15:39
 * feiniu.com Inc.Copyright (c) 2013-2015 All Rights Reserved.
 *
 */
package com.styspace.zhihuphoto.mapper;

/**
 * @ClassName IndexUrlMapper
 * @Description 用户个人主页数据库操作
 * @date 2016年12月14日 下午5:15:39
 * @author tingyong.song
 * @version 1.0.0
 *
 */
public interface IndexUrlMapper {
	/**
	 * 
	 * insertIndexUrl
	 * @param homepage
	 * @return
	 *int
	 * @exception
	 * @since  1.0.0
	 */
	public int insertIndexUrl(String homepage);
	
	/**
	 * 
	 * isExits
	 * @param homepage
	 * @return
	 *int
	 * @exception
	 * @since  1.0.0
	 */
	public int isExits(String homepage);
}
