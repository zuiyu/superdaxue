package com.superDaxue.school.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import net.sf.json.JSONObject;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.InputTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.superDaxue.login.IRequest;
import com.superDaxue.login.impl.HttpClientRequest;
import com.superDaxue.parse.ParseTool;
import com.superDaxue.school.ISchool;
import com.superDaxue.tool.DownLoadImg;

public class Cust implements ISchool{
	private String cookie;
	private IRequest requestclient = new HttpClientRequest();
	private ParseTool parseTool=new ParseTool();
	private String baseurl="jwgl.cust.edu.cn";
	
	public String getCheckNum(String savePath) {
		return null;
	}
	
	
	public JSONObject doLogin(String username, String password,String checkNum) {
		JSONObject jsonObject=new JSONObject();
		
		String path_index="http://"+baseurl+"/TeachWebSL/Login.aspx";
		List<NameValuePair> headers= new ArrayList<NameValuePair>();
		String html;
		try {
			html = requestclient.doGet(headers, path_index);
			headers= parseTool.parseCoursesParam(html);
		} catch (Exception e1) {
			e1.printStackTrace();
			jsonObject.put("message", "网络异常请稍后再试");
			return jsonObject;
		}
		String login_url="http://"+baseurl+"/teachwebsl/login.aspx";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("txtUserName", username));
		nvps.add(new BasicNameValuePair("txtPassWord", password));
		nvps.add(new BasicNameValuePair("Button1", "登录"));
		nvps.addAll(headers);
		
		List<NameValuePair> mapHeader=new ArrayList<NameValuePair>();
		mapHeader.add(new BasicNameValuePair("Host",baseurl));
		mapHeader.add(new BasicNameValuePair("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
		mapHeader.add(new BasicNameValuePair("Referer","http://"+baseurl+"/teachwebsl/login.aspx"));
		mapHeader.add(new BasicNameValuePair("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:43.0) Gecko/20100101 Firefox/43.0"));
		String temp="";
		try {
			temp = requestclient.doPost(mapHeader, nvps, login_url);
			
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("message", "网络异常，登录错误");
		}		
		if(temp.indexOf("用户名或密码错误")!=-1){
			jsonObject.put("message", "用户名或密码错误");
		}else 
		if(temp.indexOf("Object moved to")!=-1){
			int start=temp.indexOf("<a href=\"");
			if(start!=-1){
				this.cookie=requestclient.getCookie();
				int end=temp.indexOf("\">here</a>.</h2>");
				String movePath=temp.substring(start+"<a href=\"".length(),end);
				movePath=movePath.replaceAll("   &amp;", "&");
				mapHeader.add(new BasicNameValuePair("Cookie",cookie));
				String string=requestclient.doGet(null, movePath);
				//this.cookie=requestclient.getCookie();
				if(string.indexOf("教务管理软件-登录")!=-1){
					jsonObject.put("message", "登录失败");
				}else{
					jsonObject.put("result","成功！");
					jsonObject.put("isSuccess","1");
					this.cookie=requestclient.getCookie();
				}
			}
		}
		else{
			jsonObject.put("message", "登录失败请检查您的用户名和密码");
		}
		return jsonObject;
	}
	
	public String getScore() {
		String path="http://"+baseurl+"/teachweb/cjcx/StudentGrade.aspx";
		List<NameValuePair> mapHeader=new ArrayList<NameValuePair>();
		mapHeader.add(new BasicNameValuePair("Host",baseurl));
		mapHeader.add(new BasicNameValuePair("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
		mapHeader.add(new BasicNameValuePair("Referer","http://"+baseurl+"/teachweb/back/LeftFrame.aspx"));
		mapHeader.add(new BasicNameValuePair("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:43.0) Gecko/20100101 Firefox/43.0"));
	//	mapHeader.add(new BasicNameValuePair("Cookie",cookie));
		String str="";
		try {
			str = requestclient.doGet(mapHeader,path);
		} catch (Exception e1) {
			e1.printStackTrace();
			return null;
		}
		return str;
	}

	public String getTimetable() {
		String path="http://"+baseurl+"/teachweb/kbcx/PersonalCourses.aspx?role=student";
		List<NameValuePair> mapHeader=new ArrayList<NameValuePair>();
		mapHeader.add(new BasicNameValuePair("Host",baseurl));
		mapHeader.add(new BasicNameValuePair("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
		mapHeader.add(new BasicNameValuePair("Referer","http://"+baseurl+"/teachweb/back/LeftFrame.aspx"));
		mapHeader.add(new BasicNameValuePair("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:43.0) Gecko/20100101 Firefox/43.0"));
	//	mapHeader.add(new BasicNameValuePair("Cookie",cookie));
		try {
			String temp=requestclient.doGet(mapHeader,path);
			return temp;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
