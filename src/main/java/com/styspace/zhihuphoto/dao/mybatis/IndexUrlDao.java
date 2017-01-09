/**
 * Package name:com.styspace.zhihuphoto.dao.mybatis
 * File name:IndexUrlDao.java
 * Date:2016年12月14日-下午5:19:56
 * feiniu.com Inc.Copyright (c) 2013-2015 All Rights Reserved.
 *
 */
package com.styspace.zhihuphoto.dao.mybatis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.styspace.zhihuphoto.mapper.IndexUrlMapper;


/**
 * @ClassName IndexUrlDao
 * @Description 知乎用户首页数据库操作
 * @date 2016年12月14日 下午5:19:56
 * @author tingyong.song
 * @version 1.0.0
 *
 */
@Repository
public class IndexUrlDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(IndexUrlDao.class);
	
	@Autowired
	private IndexUrlMapper indexUrlMapper;
	
	/**
	 * 写入用户首页url
	 * insertIndexUrl
	 * @param homepage
	 * @return
	 *int
	 * @exception
	 * @since  1.0.0
	 */
	public int insertIndexUrl(String homepage) {
		LOGGER.info("[Insert]:" + homepage);
		return indexUrlMapper.insertIndexUrl(homepage);
	}
	
	/**
	 * 判断用户首页是否已经写入
	 * isExits
	 * @param homepage
	 * @return
	 *int
	 * @exception
	 * @since  1.0.0
	 */
	public int isExits(String homepage) {
		int isExits = indexUrlMapper.isExits(homepage);
		LOGGER.info(homepage + " [isExits]:" + isExits);
		return isExits;
	}
}
