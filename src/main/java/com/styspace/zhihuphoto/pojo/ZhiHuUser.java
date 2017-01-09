/**
 * Package name:com.styspace.zhihuphoto.pojo
 * File name:ZhihuUser.java
 * Date:2016年12月12日-上午11:29:31
 * feiniu.com Inc.Copyright (c) 2013-2015 All Rights Reserved.
 *
 */
package com.styspace.zhihuphoto.pojo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @ClassName ZhiHuUser
 * @Description 知乎用户信息
 * @date 2016年12月12日 上午11:29:31
 * @author tingyong.song
 * @version 1.0.0
 *
 */
public class ZhiHuUser {
	private String trade = "";// 所在行业
	private String sex = "";// 性别
	private String company = "";// 公司
	private String position = "";// 职位
	private String education = "";// 教育
	private String location = "";// 所在区域
	// 以上信息暂时获取不到
	
	private String userImg = "";// 用户头像地址
	private String userToken = "";// 用户token名：space-sty
	private String username = "";// 用户名：space sty
	private String homePage = "";// 个人主页
	
	private int answers = 0;// 回答数
	private int articles = 0;// 文章数
	private int questions = 0;// 提问数
	private int collections = 0;// 收藏数
	
    private int followees = 0;// 关注人数
    private int followers = 0;// 粉丝数量
    private int followTips = 0;// 关注的话题
    private int followColumns = 0;// 关注的专栏数
    private int followQuestions = 0;// 关注的专栏
    private int followFavorites = 0;// 关注的收藏夹 
    

	public String getTrade() {
		return trade;
	}
	public void setTrade(String trade) {
		this.trade = trade;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getUserImg() {
		return userImg;
	}
	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}
	public String getUserToken() {
		return userToken;
	}
	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getHomePage() {
		return homePage;
	}
	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}
	public int getAnswers() {
		return answers;
	}
	public void setAnswers(int answers) {
		this.answers = answers;
	}
	public int getArticles() {
		return articles;
	}
	public void setArticles(int articles) {
		this.articles = articles;
	}
	public int getQuestions() {
		return questions;
	}
	public void setQuestions(int questions) {
		this.questions = questions;
	}
	public int getCollections() {
		return collections;
	}
	public void setCollections(int collections) {
		this.collections = collections;
	}
	public int getFollowees() {
		return followees;
	}
	public void setFollowees(int followees) {
		this.followees = followees;
	}
	public int getFollowers() {
		return followers;
	}
	public void setFollowers(int followers) {
		this.followers = followers;
	}
	public int getFollowTips() {
		return followTips;
	}
	public void setFollowTips(int followTips) {
		this.followTips = followTips;
	}
	public int getFollowColumns() {
		return followColumns;
	}
	public void setFollowColumns(int followColumns) {
		this.followColumns = followColumns;
	}
	public int getFollowQuestions() {
		return followQuestions;
	}
	public void setFollowQuestions(int followQuestions) {
		this.followQuestions = followQuestions;
	}
	public int getFollowFavorites() {
		return followFavorites;
	}
	public void setFollowFavorites(int followFavorites) {
		this.followFavorites = followFavorites;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
	            ToStringStyle.MULTI_LINE_STYLE);
	}
}
