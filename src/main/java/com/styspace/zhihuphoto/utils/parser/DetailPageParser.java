/**
 * Package name:com.styspace.zhihuphoto.utils.parser
 * File name:DetailPageParser.java
 * Date:2016年12月12日-下午3:59:01
 * feiniu.com Inc.Copyright (c) 2013-2015 All Rights Reserved.
 *
 */
package com.styspace.zhihuphoto.utils.parser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.styspace.zhihuphoto.pojo.ZhiHuUser;

/**
 * @ClassName DetailPageParser
 * @Description 解析知乎个人主页信息
 * 首页：https://www.zhihu.com/people/space-sty/following
 * @date 2016年12月12日 下午3:59:01
 * @author tingyong.song
 * @version 1.0.0
 *
 */
@Component
public class DetailPageParser implements Parser {
//	private static final Logger LOGGER = LoggerFactory.getLogger(DetailPageParser.class);
	private static String ZHIHU_INDEX = "https://www.zhihu.com";
	
	private static final String FOLLOW_TIPS = "关注的话题";
	private static final String FOLLOW_COLUMNS = "关注的专栏";
	private static final String FOLLOW_QUESTIONS = "关注的问题";
	private static final String FOLLOW_FAVORITES = "关注的收藏夹";
	
	public static ZhiHuUser getUserInfoByParser(final Document doc){
		ZhiHuUser user = new ZhiHuUser();
		// 行业、性别、居住地、公司、职位、教育信息暂时获取不到
		
		// 用户名
		user.setUsername(doc.select(".ProfileHeader-name").text());
		// 用户头像地址
		String userImg = doc.select("img.Avatar.Avatar--large.UserAvatar-inner").attr("src");
		// 取原图
		String userImgOri = userImg.replace("_xl", "");
		user.setUserImg(userImgOri);
		
		// userToken
		String userHref = doc.select("a.Tabs-link").get(0).attr("href");
		String[] userHrefArr = userHref.split("/");
		user.setUserToken(userHrefArr[2]);
		
		
		// 6项：动态（忽略）、回答、文章、提问、收藏、关注
		Elements baseInfo1 = doc.select("a.Tabs-link");
		user.setAnswers(Integer.valueOf(baseInfo1.get(1).getElementsByClass("Tabs-meta").text()));
		user.setArticles(Integer.valueOf(baseInfo1.get(2).getElementsByClass("Tabs-meta").text()));
		user.setQuestions(Integer.valueOf(baseInfo1.get(3).getElementsByClass("Tabs-meta").text()));
		user.setCollections(Integer.valueOf(baseInfo1.get(4).getElementsByClass("Tabs-meta").text()));
		user.setHomePage(ZHIHU_INDEX + baseInfo1.get(5).attr("href"));
		
		// 2项：关注数、被关注数
		Elements baseInfo2 = doc.select(".NumberBoard-value");
		user.setFollowees(Integer.valueOf(baseInfo2.get(0).text()));
		user.setFollowers(Integer.valueOf(baseInfo2.get(1).text()));
		
		// 4项：关注的话题、关注的专栏、关注的问题、关注的收藏夹——可能还有live，所以要判断前面的文字
		Elements baseInfo3 = doc.select("a.Profile-lightItem");
		Element tmp;
		for (int i = 0; i < baseInfo3.size(); i++) {
			tmp = baseInfo3.get(i);
			String itemName = tmp.getElementsByClass("Profile-lightItemName").text();
			int itemValue = Integer.valueOf(tmp.getElementsByClass("Profile-lightItemValue").text());
			if(FOLLOW_TIPS.equals(itemName)){
				user.setFollowTips(itemValue);
			}else if (FOLLOW_COLUMNS.equals(itemName)) {
				user.setFollowColumns(itemValue);
			}else if (FOLLOW_QUESTIONS.equals(itemName)) {
				user.setFollowQuestions(itemValue);
			}else if (FOLLOW_FAVORITES.equals(itemName)) {
				user.setFollowFavorites(itemValue);
			}
		}
		
		return user;
	}
}
