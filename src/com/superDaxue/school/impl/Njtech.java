package com.superDaxue.school.impl;

import java.io.File;
import java.net.URLEncoder;
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
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
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

public class Njtech implements ISchool{
	private String username;
	private String cookie;
	private String imgCookie;
	private HttpClientRequest requestclient = new HttpClientRequest();
	private ParseTool parseTool=new ParseTool();
	private String baseurl="jwgl.njtech.edu.cn";
	
	public String getCheckNum(String savePath) {
		return null;
	}
	
	
	public JSONObject doLogin(String username, String password,String checkNum) {
		JSONObject jsonObject=new JSONObject();
		this.username=username;
		//String path_index="http://"+baseurl+"/default2.aspx";
		
		String login_url1="http://"+baseurl+"/xtgl/login_cxCheckYh.html";
	//	String login_url2="http://"+baseurl+"/xtgl/login_login.html";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("yhm", username));
		nvps.add(new BasicNameValuePair("mm", password));
		nvps.add(new BasicNameValuePair("yzm", ""));
			
		List<NameValuePair> mapHeader=new ArrayList<NameValuePair>();
		mapHeader.add(new BasicNameValuePair("Host",baseurl));
		mapHeader.add(new BasicNameValuePair("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
		mapHeader.add(new BasicNameValuePair("Referer","http://"+baseurl+"http:/xtgl/dl_loginForward.html"));
		mapHeader.add(new BasicNameValuePair("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:43.0) Gecko/20100101 Firefox/43.0"));
		String temp="";
		try {
			temp = requestclient.doPost(mapHeader, nvps, login_url1);
	//		temp = requestclient.doPost(mapHeader, nvps, login_url2);
			this.cookie=requestclient.getCookie();
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("message", "网络异常，登录错误");
		}		
		if(temp.indexOf("验证码不正确")!=-1){
			jsonObject.put("message", "验证码不正确");
		}
		else 
		if(temp.indexOf("success")!=-1){
			jsonObject.put("result","成功！");
			jsonObject.put("isSuccess","1");
			System.out.println(requestclient.doGet(mapHeader, "http://jwgl.njtech.edu.cn/xtgl/index_initMenu.html"));
		}
		else{
			jsonObject.put("message", "登录失败请检查您的用户名和密码");
		}
		return jsonObject;
	}
	
	public String getScore() {
		String path="http://jwgl.njtech.edu.cn/xtgl/index_initMenu.html";
		//String path="http://jwgl.njtech.edu.cn/xjyj/xjyj_cxXjyjjdlb.html?gnmkdmKey=N105505&sessionUserKey=1302150308";
		//String path="http://"+baseurl+"/xjyj/xjyj_cxXjyjjdlb.html?gnmkdmKey=N105505&sessionUserKey="+username;
		List<NameValuePair> mapHeader=new ArrayList<NameValuePair>();
		mapHeader.add(new BasicNameValuePair("Host",baseurl));
		mapHeader.add(new BasicNameValuePair("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
		mapHeader.add(new BasicNameValuePair("Referer","http://"+baseurl+"/xtgl/init_cxGnPage.html"));
		mapHeader.add(new BasicNameValuePair("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:43.0) Gecko/20100101 Firefox/43.0"));
		mapHeader.add(new BasicNameValuePair("Cookie",cookie));
	/*	mapHeader.add(new BasicNameValuePair("Upgrade-Insecure-Requests","1"));
		mapHeader.add(new BasicNameValuePair("Accept-Language","zh-CN,zh;q=0.8"));
		mapHeader.add(new BasicNameValuePair("Accept-Encoding","gzip, deflate"));
		mapHeader.add(new BasicNameValuePair("Content-Type","application/x-www-form-urlencoded"));*/
		//	List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			/*nvps.add(new BasicNameValuePair("gnmkdm", "N105505"));
			String urlStr="/xjyj/xjyj_cxXjyjIndex.html";
			nvps.add(new BasicNameValuePair("dyym", urlStr));
			nvps.add(new BasicNameValuePair("gnmkmc", URLEncoder.encode("学业情况")));
			nvps.add(new BasicNameValuePair("sfgnym", "undefined"));	*/
			
			try {
				String temp=requestclient.doGet(null, path);
				System.out.println(temp);
				return temp;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		
	}

	public String getTimetable() {
		String path="http://"+baseurl+"/xtgl/init_cxGnPage.html";
		List<NameValuePair> mapHeader=new ArrayList<NameValuePair>();
		mapHeader.add(new BasicNameValuePair("Host",baseurl));
		mapHeader.add(new BasicNameValuePair("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
		mapHeader.add(new BasicNameValuePair("Referer","http://"+baseurl+"/xtgl/index_initMenu.html"));
		mapHeader.add(new BasicNameValuePair("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:43.0) Gecko/20100101 Firefox/43.0"));
		mapHeader.add(new BasicNameValuePair("Cookie",cookie));
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("gnmkdm", "N253508"));
		nvps.add(new BasicNameValuePair("dyym", "/kbcx/xskbcx_cxXskbcxIndex.html"));
		nvps.add(new BasicNameValuePair("gnmkmc", "%E8%AF%BE%E8%A1%A8%E6%9F%A5%E8%AF%A2"));
		nvps.add(new BasicNameValuePair("sfgnym", "undefined"));
			
		try {
			String temp=requestclient.doPost(mapHeader,nvps,path);
			return temp;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
