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

public class Jcut implements ISchool{
	private String cookie;
	private String imgCookie;
	private IRequest requestclient = new HttpClientRequest();
	private ParseTool parseTool=new ParseTool();
	private String baseurl="218.199.48.12:1788";
	private String usermain;
	
	public String getCheckNum(String savePath) {
		String n = new Random().nextInt(100000)+""+new Date().getTime();
		String image_save_path = savePath+ "checkNum" + n + ".jpg";
		// 处理验证url
		String img_path_url = "http://"+baseurl+"/other/CheckCode.aspx?datetime=az";
		this.imgCookie=requestclient.getCodeCookie(img_path_url, image_save_path);
		return n;
	}
	
	
	public JSONObject doLogin(String username, String password,String checkNum) {
		
		JSONObject jsonObject=new JSONObject();
		//String path_index="http://"+baseurl+"/default2.aspx";
		String path_index="http://"+baseurl+"/Login.aspx";
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
		String login_url="http://"+baseurl+"/Login.aspx";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("Account", username));
		nvps.add(new BasicNameValuePair("PWD", password));
		nvps.add(new BasicNameValuePair("CheckCode", checkNum));
		nvps.add(new BasicNameValuePair("cmdok", ""));
		nvps.addAll(headers);
		
		List<NameValuePair> mapHeader=new ArrayList<NameValuePair>();
		mapHeader.add(new BasicNameValuePair("Host",baseurl));
		mapHeader.add(new BasicNameValuePair("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
		mapHeader.add(new BasicNameValuePair("Referer","http://"+baseurl+"/Login.aspx"));
		mapHeader.add(new BasicNameValuePair("User-Agent","Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; Trident/7.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; GWX:QUALIFIED)"));
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
			getUsermain();
		}
		else{
			jsonObject.put("message", "登录失败请检查您的用户名和密码");
		}
		return jsonObject;
	}
	
	public void getUsermain() {
		String url="http://"+baseurl+"/JWXS/xsMenu.aspx";
		try {
			List<NameValuePair> mapHeader=new ArrayList<NameValuePair>();
			mapHeader.add(new BasicNameValuePair("Host",baseurl));
			mapHeader.add(new BasicNameValuePair("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
			mapHeader.add(new BasicNameValuePair("Referer",url));
			mapHeader.add(new BasicNameValuePair("User-Agent","Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; Trident/7.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; GWX:QUALIFIED)"));
			mapHeader.add(new BasicNameValuePair("Cookie",cookie));
			String temp=requestclient.doGet(mapHeader, url);
			String strusermain=temp;
			int usermain_start=strusermain.indexOf("usermain=");
			int usermain_end=strusermain.indexOf("\">",usermain_start);
			usermain=strusermain.substring(usermain_start+"usermain=".length(),usermain_end);
			
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}
	
	public String getScore() {
		String path="http://"+baseurl+"/JWXS/cjcx/jwxs_cjcx_like.aspx?usermain="+usermain;
		List<NameValuePair> mapHeader=new ArrayList<NameValuePair>();
		mapHeader.add(new BasicNameValuePair("Host",baseurl));
		mapHeader.add(new BasicNameValuePair("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
		mapHeader.add(new BasicNameValuePair("Referer","http://"+baseurl+"/JWXS/cjcx/jwxs_cjcx_like.aspx?usermain="+usermain));
		mapHeader.add(new BasicNameValuePair("User-Agent","Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; Trident/7.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; GWX:QUALIFIED)"));
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
			nvps.add(new BasicNameValuePair("selSearch", "0"));
			nvps.add(new BasicNameValuePair("btnSearch", "+%B2%E9+%D1%AF+"));
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
		String path="http://"+baseurl+"/JWXS/pkgl/XsKB_List.aspx?usermain="+usermain;
		List<NameValuePair> mapHeader=new ArrayList<NameValuePair>();
		mapHeader.add(new BasicNameValuePair("Host",baseurl));
		mapHeader.add(new BasicNameValuePair("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
		mapHeader.add(new BasicNameValuePair("Referer","http://"+baseurl+"/JWXS/Default.aspx"));
		mapHeader.add(new BasicNameValuePair("User-Agent","Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; Trident/7.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; GWX:QUALIFIED)"));
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
