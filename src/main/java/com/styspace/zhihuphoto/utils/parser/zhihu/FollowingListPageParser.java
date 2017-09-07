/**
 * Package name:com.styspace.zhihuphoto.utils.parser
 * File name:FollowingListPageParser.java
 * Date:2016年12月13日-下午2:12:24
 * feiniu.com Inc.Copyright (c) 2013-2015 All Rights Reserved.
 *
 */
package com.styspace.zhihuphoto.utils.parser.zhihu;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.styspace.zhihuphoto.pojo.ZhiHuPage;
import com.styspace.zhihuphoto.utils.parser.Parser;

/**
 * @ClassName FollowingListPageParser
 * @Description 我关注的人的首页列表
 * 接口：https://www.zhihu.com/api/v4/members/tangyifan/followees
 * ?per_page=10&include=data[*].answer_count,articles_count,follower_count,is_followed,
 * is_following,badge[?(type=best_answerer)].topics&limit=10&offset=10
 * 
 * 返回结果好像是json数据
 * @date 2016年12月13日 下午2:12:24
 * @author tingyong.song
 * @version 1.0.0
 *
 */
public class FollowingListPageParser implements Parser {
	private static final Logger LOGGER = LoggerFactory.getLogger(FollowingListPageParser.class);
	
	private static final String ZHIHU_INDEX_URL = "https://www.zhihu.com/people/";
	
	public static List<String> getFolloweesByParser(ZhiHuPage page) {
		// 分页获取，每次10个
		List<String> followeesIndexUrl = new ArrayList<String>(10);
		String dataStr = page.getHtml();
		JSONArray data = JSONObject.parseObject(dataStr).getJSONArray("data");
		
		if(data != null && data.size() > 0){
			JSONObject itemTmp;
			for (int i = 0; i < data.size(); i++) {
				itemTmp = data.getJSONObject(i);
				followeesIndexUrl.add(ZHIHU_INDEX_URL + itemTmp.getString("url_token") + "/following");
			}
		}
		
		return followeesIndexUrl;
	}
}
