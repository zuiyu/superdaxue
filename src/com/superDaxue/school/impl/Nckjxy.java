package com.superDaxue.school.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import net.sf.json.JSONObject;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.htmlparser.util.ParserException;

import com.superDaxue.login.IRequest;
import com.superDaxue.login.impl.HttpClientRequest;
import com.superDaxue.parse.ParseTool;
import com.superDaxue.school.ISchool;
import com.superDaxue.tool.DateTool;

public class Nckjxy implements ISchool{
	private String username;
	private String un_cookie;
	private String cookie;
	private IRequest requestclient = new HttpClientRequest();
	private ParseTool parseTool=new ParseTool();
	private String baseurl="jwgl.nchu.edu.cn";
	
	public String getCheckNum(String savePath) {
		String hostUrl="";
		try {
			hostUrl=requestclient.getGetUrl(null, "http://"+baseurl+"/");
			un_cookie=hostUrl.substring(hostUrl.indexOf("("),hostUrl.indexOf(")")+1);
			un_cookie=un_cookie+")";
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		String n = new Random().nextInt(100000)+""+new Date().getTime();
		String image_save_path = savePath+ "checkNum" + n + ".jpg";
		// 处理验证url
		String img_path_url = "http://"+baseurl+"/"+un_cookie+"/VerifyCode.aspx";
	//	this.imgCookie=
		requestclient.getCodeCookie(img_path_url, image_save_path);
		return n;
	}
	
	
	public JSONObject doLogin(String username, String password,String checkNum) {
		JSONObject jsonObject=new JSONObject();
		this.username=username;
		String path_index="http://"+baseurl+"/"+un_cookie+"/login.aspx";
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
		String login_url="http://"+baseurl+"/"+un_cookie+"/login.aspx";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("User_ID", username));
		nvps.add(new BasicNameValuePair("User_Pass", password));
		nvps.add(new BasicNameValuePair("txtVolidate", checkNum));
		nvps.add(new BasicNameValuePair("Button1", ""));
		nvps.add(new BasicNameValuePair("cobRole", "学生"));
		nvps.addAll(headers);
		List<NameValuePair> mapHeader=new ArrayList<NameValuePair>();
		mapHeader.add(new BasicNameValuePair("Host",baseurl));
		mapHeader.add(new BasicNameValuePair("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
		mapHeader.add(new BasicNameValuePair("Referer","http://"+baseurl+"/"+un_cookie+"/login.aspx"));
		mapHeader.add(new BasicNameValuePair("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.85 Safari/537.36"));
	//	mapHeader.add(new BasicNameValuePair("Cookie",imgCookie));
		String temp="";
		try {
			temp = requestclient.doPost(mapHeader, nvps, login_url);
			cookie=requestclient.getCookie();
						
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("message", "网络异常，登录错误");
		}		
		if(temp.indexOf("验证码不正确")!=-1){
			//jsonObject.put("message", "验证码不正确");
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
		String path="http://"+baseurl+"/"+un_cookie+"/Grade/SStudentGradeSelect.aspx";
		List<NameValuePair> mapHeader=new ArrayList<NameValuePair>();
		mapHeader.add(new BasicNameValuePair("Host",baseurl));
		mapHeader.add(new BasicNameValuePair("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
		mapHeader.add(new BasicNameValuePair("Referer","http://"+baseurl+"/"+un_cookie+"/ischool.aspx"));
		mapHeader.add(new BasicNameValuePair("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.85 Safari/537.36"));
		mapHeader.add(new BasicNameValuePair("Cookie",cookie));
		String str="";
		try {
			str = requestclient.doGet(mapHeader,path);
		} catch (Exception e1) {
			e1.printStackTrace();
			return null;
		}
		return str;
		//return "";
	}

	public String getTimetable() {
		String path="http://"+baseurl+"/"+un_cookie+"/TimetableSearch/TimetableSerachStudentSingleSpan.aspx";
		List<NameValuePair> mapHeader=new ArrayList<NameValuePair>();
		mapHeader.add(new BasicNameValuePair("Host",baseurl));
		mapHeader.add(new BasicNameValuePair("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
		mapHeader.add(new BasicNameValuePair("Referer","http://"+baseurl+"/TimetableSearch/TimetableSerachStudentSingleSpan.aspx"));
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
			String[] schoolStrings=new DateTool().getThisYearSemesterQing();
			String schoolyear=schoolStrings[0]+""+(Integer.parseInt(schoolStrings[1])+1);
			List<NameValuePair> tempPairs=parseTool.parseCoursesParam(str);
			nvps.addAll(tempPairs);
			nvps.add(new BasicNameValuePair("cobTermNo_VI",schoolyear));
			nvps.add(new BasicNameValuePair("cobTermNo$DDD$L", schoolyear));
			nvps.add(new BasicNameValuePair("txtweeks", "20"));
			nvps.add(new BasicNameValuePair("txtweeks$CVS", ""));
			nvps.add(new BasicNameValuePair("cobTermNo", ""));//2015-2016第一学期[当前学期]
			nvps.add(new BasicNameValuePair("cobTermNo$DDD$L$CVS", ""));
			nvps.add(new BasicNameValuePair("cobTermNo$CVS", ""));
			nvps.remove(new BasicNameValuePair("__EVENTTARGET",""));
			nvps.remove(new BasicNameValuePair("__EVENTARGUMENT",""));
			nvps.add(new BasicNameValuePair("__EVENTTARGET","ASPxButton1"));
			nvps.add(new BasicNameValuePair("__EVENTARGUMENT","Click"));
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
}
