package com.superDaxue.school.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import net.sf.json.JSONObject;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.superDaxue.login.IRequest;
import com.superDaxue.login.impl.HttpClientRequest;
import com.superDaxue.parse.ParseTool;
import com.superDaxue.school.ISchool;
import com.superDaxue.tool.DateTool;
import com.superDaxue.tool.MD5Tool;

public class Shxy implements ISchool{
	private String username;
	private String cookie;
	private String imgCookie;
	private IRequest requestclient = new HttpClientRequest();
	private ParseTool parseTool=new ParseTool();
	private String baseurl="218.7.153.90";
	
	public String getCheckNum(String savePath) {
		String n = new Random().nextInt(100000)+""+new Date().getTime();
		String image_save_path = savePath+ "checkNum" + n + ".jpg";
		// 处理验证url
		String img_path_url = "http://"+baseurl+"/jwweb/sys/ValidateCode.aspx?t=" + n;
		this.imgCookie=requestclient.getCodeCookie(img_path_url, image_save_path);
		return n;
	}
	
	
	public JSONObject doLogin(String username, String password,String checkNum) {
		JSONObject jsonObject=new JSONObject();
		this.username=username;
		//String path_index="http://"+baseurl+"/jwweb/default2.aspx";
		String path_index="http://"+baseurl+"/jwweb/_data/index_LOGIN.aspx";
		List<NameValuePair> headers=new ArrayList<NameValuePair>();
		List<NameValuePair> mapHeader=new ArrayList<NameValuePair>();
		mapHeader.add(new BasicNameValuePair("Host",baseurl));
		mapHeader.add(new BasicNameValuePair("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
		mapHeader.add(new BasicNameValuePair("Referer","http://"+baseurl+"/jwweb/_data/index_LOGIN.aspx"));
		mapHeader.add(new BasicNameValuePair("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.85 Safari/537.36"));
		mapHeader.add(new BasicNameValuePair("Cookie",imgCookie));
		String html;
		try {
			html = requestclient.doGet(mapHeader, path_index);
			headers= parseTool.parseCoursesParam(html);
		} catch (Exception e1) {
			e1.printStackTrace();
			jsonObject.put("message", "网络异常请稍后再试");
			return jsonObject;
		}
		MD5Tool md5=new MD5Tool();
		String login_url="http://"+baseurl+"/jwweb/_data/index_LOGIN.aspx";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("Sel_Type", "STU"));
		nvps.add(new BasicNameValuePair("UserID", username));
		nvps.add(new BasicNameValuePair("PassWord", password));
		nvps.add(new BasicNameValuePair("cCode", checkNum));
		nvps.add(new BasicNameValuePair("typeName", "Ñ§Éú"));
		nvps.addAll(headers);
		nvps.add(new BasicNameValuePair("sbtState", ""));
		String temp="";
		try {
			temp = requestclient.doPost(mapHeader, nvps, login_url);
			this.cookie=requestclient.getCookie();
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("message", "网络异常，登录错误");
		}		
		if(temp.indexOf("验证码错误")!=-1){
			jsonObject.put("message", "验证码不正确");
		}else 
		if(temp.indexOf("正在通过身份验证")!=-1){
			jsonObject.put("result","成功！");
			jsonObject.put("isSuccess","1");
		}
		else{
			jsonObject.put("message", "登录失败请检查您的用户名和密码");
		}
		return jsonObject;
	}
	
	public String getScore() {
		String path="http://"+baseurl+"/jwweb/xscj/c_ydcjrdjl_rpt.aspx";
		List<NameValuePair> mapHeader=new ArrayList<NameValuePair>();
		mapHeader.add(new BasicNameValuePair("Host",baseurl));
		mapHeader.add(new BasicNameValuePair("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
		mapHeader.add(new BasicNameValuePair("Referer","http://"+baseurl+"/jwweb/xscj/c_ydcjrdjl.asp"));
		mapHeader.add(new BasicNameValuePair("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.85 Safari/537.36"));
		mapHeader.add(new BasicNameValuePair("Cookie",cookie));
		try {
			
			int yearStudent=Integer.parseInt(username.substring(0, 4));
			List<String> yearlist=new DateTool().gradeYearList(yearStudent);;
			StringBuffer resultTemp=new StringBuffer();
			for (int i = 0; i < yearlist.size(); i++) {
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				nvps.add(new BasicNameValuePair("sel_xnxq", yearlist.get(i)));
				nvps.add(new BasicNameValuePair("radCx", "1"));
				nvps.add(new BasicNameValuePair("btn_search", "¼ìË÷"));
				try {
					String temp=requestclient.doPost(mapHeader,nvps,path);
					resultTemp.append(temp);
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}
			return resultTemp.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getTimetable() {
		/*String path="http://"+baseurl+"/jwweb/wsxk/stu_zxjg_rxyl.aspx";
		List<NameValuePair> mapHeader=new ArrayList<NameValuePair>();
		mapHeader.add(new BasicNameValuePair("Host",baseurl));
		mapHeader.add(new BasicNameValuePair("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,* /*;q=0.8"));
		mapHeader.add(new BasicNameValuePair("Referer","http://"+baseurl+"/jwweb/wsxk/stu_zxjg.aspx"));
		mapHeader.add(new BasicNameValuePair("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.85 Safari/537.36"));
		mapHeader.add(new BasicNameValuePair("Cookie",cookie));
		try {
			String temp=requestclient.doGet(mapHeader,path);
			return temp;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}*/
		return "";
	}
	
}

