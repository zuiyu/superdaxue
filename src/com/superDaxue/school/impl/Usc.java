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
import com.superDaxue.tool.DateTool;
import com.superDaxue.tool.DownLoadImg;

public class Usc implements ISchool{
	private String username;
	private String cookie;
	private String imgCookie;
	private IRequest requestclient = new HttpClientRequest();
	private ParseTool parseTool=new ParseTool();
	private String baseurl="jw.nhcsr.cn";
	
	public String getCheckNum(String savePath) {
		return null;
	}
	
	
	public JSONObject doLogin(String username, String password,String checkNum) {
		
		
		JSONObject jsonObject=new JSONObject();
		this.username=username;
		//String path_index="http://"+baseurl+"/default2.aspx";
		String path_index="http://"+baseurl+"/USCTAMisLogin.aspx";
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
		String login_url="http://"+baseurl+"/USCTAMisLogin.aspx";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("RadFormDecorator1_ClientState", ""));
		nvps.add(new BasicNameValuePair("Login$UserName", username));
		nvps.add(new BasicNameValuePair("Login$Password", password));
		nvps.add(new BasicNameValuePair("Login_UserName_ClientState", ""));
		nvps.add(new BasicNameValuePair("Login$Img_Login.x", "10"));
		nvps.add(new BasicNameValuePair("Login$Img_Login.y", "15"));
		nvps.add(new BasicNameValuePair("Login_Password_ClientState", ""));
		nvps.addAll(headers);
		List<NameValuePair> mapHeader=new ArrayList<NameValuePair>();
		mapHeader.add(new BasicNameValuePair("Host",baseurl));
		mapHeader.add(new BasicNameValuePair("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
		mapHeader.add(new BasicNameValuePair("Referer","http://"+baseurl+"/USCTAMisLogin.aspx"));
		mapHeader.add(new BasicNameValuePair("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.85 Safari/537.36"));
		mapHeader.add(new BasicNameValuePair("Cookie",imgCookie));
		String temp="";
		try {
			temp = requestclient.doPost(mapHeader, nvps, login_url);
			this.cookie=requestclient.getCookie();
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("message", "网络异常，登录错误");
		}		
		if(temp.indexOf("验证码不正确")!=-1){
			jsonObject.put("message", "验证码不正确");
		}else 
		if(temp.indexOf("Object moved to")!=-1){
			jsonObject.put("result","成功！");
			jsonObject.put("isSuccess","1");
		}
		else{
			jsonObject.put("message", "登录失败请检查您的用户名和密码");
		}
		return jsonObject;
	}
	
	public String getScore() {
		/*String path="http://"+baseurl+"/StudentUI/StuScoreQueryAndEnroll.aspx";
		List<NameValuePair> mapHeader=new ArrayList<NameValuePair>();
		mapHeader.add(new BasicNameValuePair("Host",baseurl));
		mapHeader.add(new BasicNameValuePair("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,* /*;q=0.8"));
		mapHeader.add(new BasicNameValuePair("Referer","http://"+baseurl+"/StudentUI/StuScoreQueryAndEnroll.aspx"));
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
			List<NameValuePair> pairs=parseTool.parseCoursesParam(str);
			nvps.addAll(pairs);
			nvps.add(new BasicNameValuePair("_StudentNo",username));
			//nvps.add(new BasicNameValuePair("RadScriptManager1","RadScriptManager1|ddl_YearTerm"));
			//nvps.add(new BasicNameValuePair("RadScriptManager1_TSM",";;System.Web.Extensions, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35:zh-CN:6ddb313d-ac0f-4792-8aae-d7e9c0b89a66:ea597d4b:b25378d2;Telerik.Web.UI:zh-CN:ac331549-681c-4402-9fb7-09ec3c579ee8:16e4e7cd:f7645509:22a6274a:ed16cbdc:88144a7a:b7778d6c:24ee1bba:f46195d3:2003d0b8:1e771326:aa288e2d:7165f74:58366029;"));
			//nvps.add(new BasicNameValuePair("txt_StudentNo_ClientState","{\"enabled\":true,\"emptyMessage\":\"\",\"validationText\":\""+username+"\",\"valueAsString\":\""+username+"\",\"lastSetTextBoxValue\":\""+username+"\"}"));
			String name="陈杰峰";
		//	nvps.add(new BasicNameValuePair("txt_StudentName",name));
			//nvps.add(new BasicNameValuePair("btn_OK_ClientState",""));
			//nvps.add(new BasicNameValuePair("wg_ScoreInfo_ClientState",""));
			//nvps.add(new BasicNameValuePair("__EVENTTARGET","ddl_YearTerm"));
			//nvps.add(new BasicNameValuePair("__EVENTARGUMENT","{\"Command\":\"Select\",\"Index\":4}"));
			//nvps.add(new BasicNameValuePair("__VIEWSTATEGENERATOR","D4671837"));
			//nvps.add(new BasicNameValuePair("__EVENTVALIDATION","/wEdAAk/6s24q5EFmU8s0jXt4xTcGut6wA0F7CeR8j7qOuRo03O6GpJ6Z8HCKlebA8N2vPhSIB8AUc1j1XHf0pCFn0tYG2FJuNqn9On7+9olO0mlHq259UowxV9nXVZJnj0klrIsxK6OcQ131dlLw+bkjX4bdqQsUjd5IAlKaHQJ80vpSck71Xnm6XGqRszWDsUKVt6nxCZAeZTk6Ac7y6Q0ZXj4bmowjBzBazIGIX2Nl5w2BQ=="));
			//nvps.add(new BasicNameValuePair("__ASYNCPOST","true"));
			//nvps.add(new BasicNameValuePair("RadAJAXControlID","RadAjaxManager1"));
			String yearStr=username.substring(0,4);
			List<String> yearList = new DateTool().getYearListFen(Integer.parseInt(yearStr));
			StringBuffer sb=new StringBuffer();
			for (int i = 0; i < yearList.size(); i++) {
				String yearitem=yearList.get(i);
				nvps.add(new BasicNameValuePair("ddl_YearTerm",yearitem));
				nvps.add(new BasicNameValuePair("ddl_YearTerm_ClientState", "{\"logEntries\":[],\"value\":\""+yearitem+"\",\"text\":\""+yearitem+"\",\"enabled\":true,\"checkedIndices\":[],\"checkedItemsTextOverflows\":true}"));
				try {
					String temp=requestclient.doPost(mapHeader,nvps,path);
					System.out.println(temp);
					sb.append(temp);
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}
		//	System.out.println(sb.toString());
			return sb.toString();
		
		} catch (ParserException e) {
			e.printStackTrace();
			return null;
		}*/
		return "";
	}

	public String getTimetable() {
		String path="http://"+baseurl+"/StudentUI/StudentTimeTableQuery.aspx";
		List<NameValuePair> mapHeader=new ArrayList<NameValuePair>();
		mapHeader.add(new BasicNameValuePair("Host",baseurl));
		mapHeader.add(new BasicNameValuePair("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
		mapHeader.add(new BasicNameValuePair("Referer","http://"+baseurl+"/StudentMain.aspx"));
		mapHeader.add(new BasicNameValuePair("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.85 Safari/537.36"));
		mapHeader.add(new BasicNameValuePair("Cookie",cookie));
		try {
			String temp=requestclient.doGet(mapHeader,path);
			return temp;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
