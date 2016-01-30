package com.superDaxue.school.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.superDaxue.tool.MD5Tool;

public class HenuRequestData {
	public List<NameValuePair> getLoginParams(String username,String psd,String code) {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();//参数列表
		MD5Tool md5Tool = new MD5Tool();
		String password = md5Tool.GetMD5Code(md5Tool.GetMD5Code(psd)
				+ md5Tool.GetMD5Code(code));
		nvps.add(new BasicNameValuePair("username", username));
		nvps.add(new BasicNameValuePair("password", password));
		nvps.add(new BasicNameValuePair("randnumber", code));
		nvps.add(new BasicNameValuePair("isPasswordPolicy", "1"));
		return nvps;
	}
	
	public List<NameValuePair> getLoginHeader(String cookie){
		List<NameValuePair> mapHeader=new ArrayList<NameValuePair>();
		mapHeader.add(new BasicNameValuePair("Cookie", cookie));
		mapHeader.add(new BasicNameValuePair("Host", "xk1.henu.edu.cn"));
		mapHeader.add(new BasicNameValuePair("Origin", "http://xk1.henu.edu.cn"));
		mapHeader.add(new BasicNameValuePair("Referer","http://xk1.henu.edu.cn/cas/login.action"));
		mapHeader.add(new BasicNameValuePair("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.85 Safari/537.36"));
		
		return mapHeader;
	}
	
	public List<NameValuePair> getScoreParams(String usercode) {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("sjxz", "sjxz1"));
		nvps.add(new BasicNameValuePair("ysyx", "yscj"));
		nvps.add(new BasicNameValuePair("userCode", usercode));
		Calendar calendar=Calendar.getInstance();
		int year=calendar.get(Calendar.YEAR)+1;
		nvps.add(new BasicNameValuePair("xn1", year + ""));
		nvps.add(new BasicNameValuePair("ysyxS", "on"));
		nvps.add(new BasicNameValuePair("sjxzS", "on"));
		nvps.add(new BasicNameValuePair("menucode_current", ""));
		return nvps;
	}
	
	public List<NameValuePair> getScoreHeader(String cookie){
		List<NameValuePair> mapHeader=new ArrayList<NameValuePair>();
 		mapHeader.add(new BasicNameValuePair("Host","xk1.henu.edu.cn"));
     	mapHeader.add(new BasicNameValuePair("Cache-Control", "max-age=0"));
     	mapHeader.add(new BasicNameValuePair("Connection", "keep-alive"));
     	mapHeader.add(new BasicNameValuePair("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*;q=0.8"));
     	mapHeader.add(new BasicNameValuePair("Origin", "http://xk1.henu.edu.cn"));
     	mapHeader.add(new BasicNameValuePair("Upgrade-Insecure-Requests", "1"));
     	mapHeader.add(new BasicNameValuePair("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.85 Safari/537.36"));
     //	mapHeader.add(new BasicNameValuePair("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36"));
     	mapHeader.add(new BasicNameValuePair("Content-Type", "application/x-www-form-urlencoded"));
     	mapHeader.add(new BasicNameValuePair("Referer", "http://xk1.henu.edu.cn/student/xscj.stuckcj.jsp?menucode=JW130706"));
     	mapHeader.add(new BasicNameValuePair("Accept-Language", "zh-CN,zh;q=0.8"));
   //  	mapHeader.add(new BasicNameValuePair("Accept-Encoding", "gzip"));
     	mapHeader.add(new BasicNameValuePair("Cookie", cookie));
     	return mapHeader;
	}
	
	public List<NameValuePair> getTimetableHeader(String cookie){
		List<NameValuePair> mapHeader=new ArrayList<NameValuePair>();
 		mapHeader.add(new BasicNameValuePair("Host","xk1.henu.edu.cn"));
 	  	mapHeader.add(new BasicNameValuePair("Connection", "keep-alive"));
     	mapHeader.add(new BasicNameValuePair("Cache-Control", "max-age=0"));
     	mapHeader.add(new BasicNameValuePair("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*;q=0.8"));
     	mapHeader.add(new BasicNameValuePair("Origin", "http://xk1.henu.edu.cn"));
     	mapHeader.add(new BasicNameValuePair("Upgrade-Insecure-Requests", "1"));
     	mapHeader.add(new BasicNameValuePair("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36"));
     	mapHeader.add(new BasicNameValuePair("Content-Type", "application/x-www-form-urlencoded"));
     	//http://xk3.henu.edu.cn/student/xkjg.wdkb.jsp?menucode=JW130501
     	mapHeader.add(new BasicNameValuePair("Referer", "http://xk1.henu.edu.cn/frame/jw/teacherstudentmenu.jsp?menucode=JW1304"));
     	mapHeader.add(new BasicNameValuePair("Accept-Language", "zh-CN,zh;q=0.8"));
     	mapHeader.add(new BasicNameValuePair("Cookie", cookie));
     	return mapHeader;
     	
	}
}
