package com.superDaxue.school.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Vector;

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

public class Njci implements ISchool{
	private String username;
	private String scoreCookie;
	private String cookie;
	private String imgCookie;
	private IRequest requestclient = new HttpClientRequest();
	private ParseTool parseTool=new ParseTool();
	private String baseurl="go.njci.edu.cn";
	
	public String getCheckNum(String savePath) {
		return null;
	}
	
	
	public JSONObject doLogin(String username, String password,String checkNum) {
		
		JSONObject jsonObject=new JSONObject();
		this.username=username;
		List<NameValuePair> mapHeader=new ArrayList<NameValuePair>();
		mapHeader.add(new BasicNameValuePair("Host","go.njci.edu.cn"));
		mapHeader.add(new BasicNameValuePair("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
		
		mapHeader.add(new BasicNameValuePair("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.85 Safari/537.36"));
		//mapHeader.add(new BasicNameValuePair("Cookie",imgCookie));
		mapHeader.add(new BasicNameValuePair("Upgrade-Insecure-Requests","1"));
		String path_index="http://go.njci.edu.cn/login";
		List<NameValuePair> headers= new ArrayList<NameValuePair>();
		String html;
		try {
			html = requestclient.doGet(mapHeader, path_index);
			headers= parseTool.parseCoursesParam(html);
		} catch (Exception e1) {
			e1.printStackTrace();
			jsonObject.put("message", "网络异常请稍后再试");
			return jsonObject;
		}
		
		String login_url="http://go.njci.edu.cn/login";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("username", username));
		nvps.add(new BasicNameValuePair("password", password));
		nvps.addAll(headers);
		nvps.add(new BasicNameValuePair("submit", "登  录"));
		
		String temp="";
		try {
			mapHeader.add(new BasicNameValuePair("Referer","http://go.njci.edu.cn/login"));
			temp = requestclient.doPost(mapHeader, nvps, login_url);
			this.cookie=requestclient.getCookie();
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("message", "网络异常，登录错误");
		}		
		if(temp.indexOf("验证码不正确")!=-1){
			jsonObject.put("message", "验证码不正确");
		}/*else 
		if(temp.indexOf("登录成功")!=-1){*/
			jsonObject.put("result","成功！");
			jsonObject.put("isSuccess","1");
			try {
				mapHeader.add(new BasicNameValuePair("Cookie","UserID="+username));
				String path="";
				int pathSart=cookie.indexOf("CASTGC=");
				if(pathSart!=-1){
					int pathEnd=cookie.indexOf(";",pathSart);
					path=cookie.substring(pathSart+"CASTGC=".length(), pathEnd);
					cookie=cookie.substring(pathEnd+1);
				}
				path=path.replace("TGT", "ST");
				temp=requestclient.doGet(mapHeader,"http://jw.njci.edu.cn/welcome.do?ticket="+path);
				mapHeader.remove(new BasicNameValuePair("Cookie","UserID="+username));
				cookie=requestclient.getCookie();
				mapHeader.add(new BasicNameValuePair("Cookie",cookie));
				temp=requestclient.doGet(mapHeader, "http://jw.njci.edu.cn/CASLogin");
				cookie=requestclient.getCookie();
				mapHeader.remove(new BasicNameValuePair("Cookie",cookie));
				mapHeader.add(new BasicNameValuePair("Cookie",cookie));
				temp=requestclient.doGet(mapHeader, "http://go.njci.edu.cn/login?service=http%3A%2F%2Fjw.njci.edu.cn%2FCASLogin");
				cookie=requestclient.getCookie();
				mapHeader.remove(new BasicNameValuePair("Cookie",cookie));
				mapHeader.add(new BasicNameValuePair("Cookie",cookie));
				path="";
				pathSart=cookie.indexOf("CASTGC=");
				if(pathSart!=-1){
					int pathEnd=cookie.indexOf(";",pathSart);
					path=cookie.substring(pathSart+"CASTGC=".length(), pathEnd);
					this.cookie=cookie.substring(pathEnd+1);
				}
				path=path.replace("TGT", "ST");
				temp=requestclient.doGet(mapHeader,"http://jw.njci.edu.cn/welcome.do?ticket="+path);
				this.cookie=requestclient.getCookie();
				mapHeader.remove(new BasicNameValuePair("Cookie",cookie));
				mapHeader.add(new BasicNameValuePair("Cookie",cookie));
				temp=requestclient.doGet(mapHeader, "http://jw.njci.edu.cn/welcome.do");
				this.cookie=requestclient.getCookie();
				this.cookie=requestclient.getCookie();
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		//}
		/*else{
			jsonObject.put("message", "登录失败请检查您的用户名和密码");
		}*/
		return jsonObject;
	}
	
	public String getScore() {
		String path="http://jw.njci.edu.cn/UIProcessor?Table=VJX_GRCJCX&ObjDescribe=7FQcCG*6HI1haa1sJPqJDTo9Q9kzuz3k";
		List<NameValuePair> mapHeader=new ArrayList<NameValuePair>();
		mapHeader.add(new BasicNameValuePair("Host","jw.njci.edu.cn"));
		mapHeader.add(new BasicNameValuePair("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
		mapHeader.add(new BasicNameValuePair("Referer","http://jw.njci.edu.cn/welcome.do"));
		mapHeader.add(new BasicNameValuePair("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.85 Safari/537.36"));
	
		
		String str="";
		try {
			String token="-7rUDql3xp-SssfHZUHXgKaWn9Q1Fa1l-NcbfYTZUDmXDTR9nZ8NMQ..";
			mapHeader.add(new BasicNameValuePair("Cookie",cookie+"UserID="+username+";PortalToken="+token+";LBCLUSTERID="+"\"10.100.100.12:15780\""));
			str = requestclient.doGet(null,path);
			System.out.println(str);
		} catch (Exception e1) {
			e1.printStackTrace();
			return null;
		}
		return str;
	}

	public String getTimetable() {
		String path="http://report.njci.edu.cn/FrReportServer?_=1453629586034&__boxModel__=true&op=page_content&sessionID=DE4E948E6CE7C1DA3B05F5683D685E96-n1.cas17&pn=1";
		List<NameValuePair> mapHeader=new ArrayList<NameValuePair>();
		mapHeader.add(new BasicNameValuePair("Host","report.njci.edu.cn"));
		mapHeader.add(new BasicNameValuePair("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
		mapHeader.add(new BasicNameValuePair("Referer","http://jw.njci.edu.cn/s/report/wdxskcb?gh="+username));
		mapHeader.add(new BasicNameValuePair("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.85 Safari/537.36"));
		//mapHeader.add(new BasicNameValuePair("Cookie",cookie));
		try {
			String temp=requestclient.doGet(mapHeader,path);
			return temp;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
