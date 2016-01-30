package com.superDaxue.school.impl;

import java.util.ArrayList;
import java.util.List;

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

public class YpcEXP implements ISchool{
	private String username;
	private String cookie;
	
	private IRequest requestclient = new HttpClientRequest();
	private ParseTool parseTool=new ParseTool();
	//此处验证码参数无效
	public JSONObject doLogin(String username, String password,String checkNum) {
		JSONObject jsonObject=new JSONObject();
		this.username=username;
		String path_index="http://jwgl5.ypc.edu.cn/";
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
		String login_url="http://jwgl5.ypc.edu.cn/default2.aspx";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("TextBox1", username));
		nvps.add(new BasicNameValuePair("TextBox2", password));
		nvps.add(new BasicNameValuePair("RadioButtonList1", "Ñ§Éú"));
		nvps.add(new BasicNameValuePair("Button1", ""));
		nvps.addAll(headers);
		 
		
		List<NameValuePair> mapHeader=new ArrayList<NameValuePair>();
		mapHeader.add(new BasicNameValuePair("Host","jwgl5.ypc.edu.cn"));
		mapHeader.add(new BasicNameValuePair("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
		mapHeader.add(new BasicNameValuePair("Referer","http://jwgl5.ypc.edu.cn/"));
		mapHeader.add(new BasicNameValuePair("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.85 Safari/537.36"));
		String temp="";
		try {
			temp = requestclient.doPost(mapHeader, nvps, login_url);
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("message", "网络异常，登录错误");
		}
		this.cookie=requestclient.getCookie();
		if(temp.indexOf("Object moved to")!=-1){
			jsonObject.put("result","成功！");
		}
		else {
			jsonObject.put("message", "登录失败请检查您的用户名和密码");
		}
		return jsonObject;
	}
	
	public String getScore() {
		String path="http://jwgl5.ypc.edu.cn/xscjcx.aspx?xh="+username+"&xm=%CB%EF%CD%AC%BF%AD&gnmkdm=N121605";
		List<NameValuePair> mapHeader=new ArrayList<NameValuePair>();
		mapHeader.add(new BasicNameValuePair("Host","jwgl5.ypc.edu.cn"));
		mapHeader.add(new BasicNameValuePair("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
		mapHeader.add(new BasicNameValuePair("Referer","http://jwgl5.ypc.edu.cn/xscjcx.aspx?xh="+username+"&xm=%u5b59%u540c%u51ef&gnmkdm=N121605"));
		mapHeader.add(new BasicNameValuePair("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.85 Safari/537.36"));
		mapHeader.add(new BasicNameValuePair("Cookie",cookie));
		String str="";
		try {
			str = requestclient.doGet(mapHeader,path);
		} catch (Exception e1) {
			e1.printStackTrace();
			return null;
		}
		try {
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("__EVENTTARGET", ""));
			nvps.add(new BasicNameValuePair("__EVENTARGUMENT", ""));
			nvps.add(new BasicNameValuePair("__LASTFOCUS", ""));
			nvps.add(new BasicNameValuePair("hidLanguage", ""));
			nvps.add(new BasicNameValuePair("ddlXN", ""));
			nvps.add(new BasicNameValuePair("ddlXQ", ""));
			nvps.add(new BasicNameValuePair("ddl_kcxz", ""));
			nvps.add(new BasicNameValuePair("btn_zcj", ""));
			nvps.addAll(parseTool.parseCoursesParam(str));
			try {
				return requestclient.doPost(mapHeader,nvps,path);
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
		String path="http://jwgl5.ypc.edu.cn/xskbcx.aspx?xh="+username+"&xm=%CB%EF%CD%AC%BF%AD&gnmkdm=N121603";
		List<NameValuePair> mapHeader=new ArrayList<NameValuePair>();
		mapHeader.add(new BasicNameValuePair("Host","jwgl5.ypc.edu.cn"));
		mapHeader.add(new BasicNameValuePair("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
		mapHeader.add(new BasicNameValuePair("Referer","http://jwgl5.ypc.edu.cn/"));
		mapHeader.add(new BasicNameValuePair("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.85 Safari/537.36"));
		mapHeader.add(new BasicNameValuePair("Cookie",cookie));
		try {
			return requestclient.doGet(mapHeader,path);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String getCheckNum(String savePath) {
		return null;
	}
}
