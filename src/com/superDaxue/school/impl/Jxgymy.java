package com.superDaxue.school.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.htmlparser.util.ParserException;

import com.superDaxue.login.IRequest;
import com.superDaxue.login.impl.HttpClientRequest;
import com.superDaxue.parse.ParseTool;
import com.superDaxue.school.ISchool;

public class Jxgymy implements ISchool{
	private String username;
	private String cookie;
	private String baseurl="jwgl.jxgymy.com:8003";
	private IRequest requestclient = new HttpClientRequest();
	private ParseTool parseTool=new ParseTool();
	
	public String getCheckNum(String savePath) {
		return null;
	}
	public JSONObject doLogin(String username, String password,String checkNum) {
		JSONObject jsonObject=new JSONObject();
		this.username=username;
		String path_index="http://ca.jxgymy.com:8001/zfca/login";
		List<NameValuePair> headers= new ArrayList<NameValuePair>();
		String html;
		try {
			html = requestclient.doGet(headers, path_index);
			if(html.indexOf("/zfca/logout")!=-1){
				String path_logout="http://ca.jxgymy.com:8001/zfca/logout";
				headers.add(new BasicNameValuePair("Cookie",cookie));
				String logout=requestclient.doGet(headers, path_logout);
				headers.remove(new BasicNameValuePair("Cookie",cookie));
				html = requestclient.doGet(headers, path_index);
			}
			
			headers= parseTool.parseCoursesParam(html);
		} catch (Exception e1) {
			e1.printStackTrace();
			jsonObject.put("message", "网络异常请稍后再试");
			return jsonObject;
		}
		String login_url="http://ca.jxgymy.com:8001/zfca/login";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("username", username));
		nvps.add(new BasicNameValuePair("password", password));
		nvps.add(new BasicNameValuePair("losetime", "1"));
		nvps.add(new BasicNameValuePair("useValidateCode", "0"));
		nvps.add(new BasicNameValuePair("isremenberme", "1"));
		nvps.add(new BasicNameValuePair("ip", ""));
		nvps.add(new BasicNameValuePair("_eventId", "submit"));
		nvps.add(new BasicNameValuePair("submit1", ""));
		nvps.addAll(headers);
		List<NameValuePair> mapHeader=new ArrayList<NameValuePair>();
		mapHeader.add(new BasicNameValuePair("Host","ca.jxgymy.com:8001"));
		mapHeader.add(new BasicNameValuePair("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
		mapHeader.add(new BasicNameValuePair("Referer","http://ca.jxgymy.com:8001/zfca/login"));
		mapHeader.add(new BasicNameValuePair("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.85 Safari/537.36"));
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
		}
		else if("".equals(temp)){
			jsonObject.put("result","成功！");
			jsonObject.put("isSuccess","1");
			String urlString="http://ca.jxgymy.com:8001/zfca?yhlx=student&login=0122579031373493708&url=xs_main.aspx";
			mapHeader.add(new BasicNameValuePair("Cookie",cookie));
			try {
				String tempsString=requestclient.doGet(headers, urlString);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}
		else{
			jsonObject.put("message", "登录失败请检查您的用户名和密码");
		}
		return jsonObject;
	}
	
	public String getScore() {
		String path="http://"+baseurl+"/xscj_gc.aspx?xh="+username+"&gnmkdm=N121605";
		List<NameValuePair> mapHeader=new ArrayList<NameValuePair>();
		mapHeader.add(new BasicNameValuePair("Host",baseurl));
		mapHeader.add(new BasicNameValuePair("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
		mapHeader.add(new BasicNameValuePair("Referer","http://"+baseurl+"/xscj_gc.aspx?xh="+username+"&gnmkdm=N121605"));
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
			nvps.add(new BasicNameValuePair("ddlXN", ""));
			nvps.add(new BasicNameValuePair("ddlXQ", ""));
			nvps.add(new BasicNameValuePair("Button2", ""));
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
		String path="http://"+baseurl+"/xskbcx.aspx?xh="+username+"&gnmkdm=N121603";
		List<NameValuePair> mapHeader=new ArrayList<NameValuePair>();
		mapHeader.add(new BasicNameValuePair("Host",baseurl));
		mapHeader.add(new BasicNameValuePair("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
		mapHeader.add(new BasicNameValuePair("Referer","http://"+baseurl+"/xskbcx.aspx?xh="+username+"&gnmkdm=N121603"));
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
			Calendar calendar=Calendar.getInstance();
			int year=calendar.get(Calendar.YEAR);
			int month=calendar.get(calendar.MONTH)+1;
			String schoolyear="";
			String semester="";
			if(month>7){
				schoolyear=year+"-"+(year+1);
				semester="1";
			}else {
				schoolyear=(year-1)+"-"+year;
				semester="2";
			}
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("xnd",schoolyear));
			nvps.add(new BasicNameValuePair("xqd", semester));
			nvps.add(new BasicNameValuePair("ddlXQ", ""));
			nvps.add(new BasicNameValuePair("Button5", ""));
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
	
}
