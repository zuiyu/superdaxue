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
import com.superDaxue.school.ISchool;

public class Npumd implements ISchool{
	private String cookie;
	private String imgCookie;
	private String baseurl="urp.npumd.cn";
	private IRequest requestclient = new HttpClientRequest();
	public String getCheckNum(String savePath) {
		String n = new Random().nextInt(100000)+""+new Date().getTime();
		String image_save_path = savePath+ "checkNum" + n + ".jpg";
		// 处理验证url
		String img_path_url = "http://"+baseurl+"/validateCodeAction.do?random=" + n;
		this.imgCookie=requestclient.getCodeCookie(img_path_url, image_save_path);
		return n;
	}
	
	public JSONObject doLogin(String username, String password,String checkNum) {
		JSONObject jsonObject=new JSONObject();
		String login_url="http://"+baseurl+"/loginAction.do";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("zjh", username));
		nvps.add(new BasicNameValuePair("mm", password));
		nvps.add(new BasicNameValuePair("v_yzm", checkNum));
		List<NameValuePair> mapHeader=new ArrayList<NameValuePair>();
		mapHeader.add(new BasicNameValuePair("Host",baseurl));
		mapHeader.add(new BasicNameValuePair("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
		mapHeader.add(new BasicNameValuePair("Referer","http://"+baseurl+"/loginAction.do"));
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
		if(temp.indexOf("frame src=\"/menu/mainFrame.jsp\"")!=-1){
			jsonObject.put("result","成功！");
			jsonObject.put("isSuccess","1");
		}
		else{
			jsonObject.put("message", "登录失败请检查您的用户名和密码");
		}
		return jsonObject;
	}
	
	public String getScore() {
		String path="http://"+baseurl+"/gradeLnAllAction.do?type=ln&oper=qbinfo";
		List<NameValuePair> mapHeader=new ArrayList<NameValuePair>();
		mapHeader.add(new BasicNameValuePair("Host",baseurl));
		mapHeader.add(new BasicNameValuePair("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
		mapHeader.add(new BasicNameValuePair("Referer","http://"+baseurl+"/gradeLnAllAction.do?type=ln&oper=qb"));
		mapHeader.add(new BasicNameValuePair("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.85 Safari/537.36"));
		mapHeader.add(new BasicNameValuePair("Cookie",cookie));
		StringBuffer sbBuffer=new StringBuffer();
		String str="";
		try {
			str = requestclient.doGet(mapHeader,path);
			sbBuffer.append(str);
		} catch (Exception e1) {
			e1.printStackTrace();
			return null;
		}
		String unpassurl="http://"+baseurl+"/gradeLnAllAction.do?type=ln&oper=bjg";
		mapHeader.add(new BasicNameValuePair("Referer","http://"+baseurl+"/gradeLnAllAction.do?type=ln&oper=fa"));
		try {
			str = requestclient.doGet(mapHeader,unpassurl);
			sbBuffer.append(str);
		} catch (Exception e1) {
			e1.printStackTrace();
			return null;
		}
		return sbBuffer.toString();
	}

	public String getTimetable() {
		String path="http://"+baseurl+"/xkAction.do?actionType=6";
		List<NameValuePair> mapHeader=new ArrayList<NameValuePair>();
		mapHeader.add(new BasicNameValuePair("Host",baseurl));
		mapHeader.add(new BasicNameValuePair("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
		mapHeader.add(new BasicNameValuePair("Referer","http://"+baseurl+"/menu/s_menu.jsp"));
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
