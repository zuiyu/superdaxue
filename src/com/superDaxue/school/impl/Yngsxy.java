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

public class Yngsxy implements ISchool{
	private String username;
	private String cookie;
	private String imgCookie;
	private IRequest requestclient = new HttpClientRequest();
	private ParseTool parseTool=new ParseTool();
	private String baseurl="jw.yngsxy.net";
	
	public String getCheckNum(String savePath) {
		return "noPassword";
	}
	
	
	public JSONObject doLogin(String username, String password,String checkNum) {
		this.username=username;
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("result","成功！");
		jsonObject.put("isSuccess","1");
		return jsonObject;
	}
	
	public String getScore() {
		List<NameValuePair> mapHeader=new ArrayList<NameValuePair>();
		mapHeader.add(new BasicNameValuePair("Host",baseurl));
		mapHeader.add(new BasicNameValuePair("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
		mapHeader.add(new BasicNameValuePair("Referer","http://jw.yngsxy.net/(4kvm5445how4q0ixrhbluezn)/xscjcx.aspx?xh="+username));
		mapHeader.add(new BasicNameValuePair("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.85 Safari/537.36"));

		String path="http://jw.yngsxy.net/(4kvm5445how4q0ixrhbluezn)/xscjcx.aspx?xh="+username;
		String str="";
		try {
			str = requestclient.doGet(mapHeader,path);
		} catch (Exception e1) {
			e1.printStackTrace();
			return null;
		}
		try {
			
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("ddlXN", ""));
			nvps.add(new BasicNameValuePair("ddlXQ", ""));
			nvps.add(new BasicNameValuePair("ddl_kcxz", ""));
			nvps.add(new BasicNameValuePair("btn_zcj", ""));
			nvps.addAll(parseTool.parseCoursesParam(str));
			try {
				String temp=requestclient.doPost(mapHeader,nvps,path);
				return temp;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} catch (ParserException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getTimetable() {
		return "";
	}
}
