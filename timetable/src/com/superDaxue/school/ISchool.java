package com.superDaxue.school;

import com.superDaxue.model.User;
import com.superDaxue.parse.IParse;

import net.sf.json.JSONObject;

public interface ISchool{
	public JSONObject doLogin(String username,String password,String checkNum);//登录
	//public JSONObject doLogin(User user,String imgCookie,String checkNum);//登录
	public String getScore();//获取成绩
	public String getTimetable();//获取课表
	public String getCheckNum(String savePath);
	
}
