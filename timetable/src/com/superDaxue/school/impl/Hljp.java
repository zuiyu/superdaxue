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
import com.superDaxue.tool.MD5Tool;

public class Hljp implements ISchool{
	private String cookie;
	private String imgCookie;
	private String username;
	private IRequest requestclient = new HttpClientRequest();
	private ParseTool parseTool=new ParseTool();
	private String baseurl="222.171.247.81:9000";
	private String ua="Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; Trident/7.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; GWX:QUALIFIED; .NET4.0E)";
	public String getCheckNum(String savePath) {
		String n = new Random().nextInt(100000)+""+new Date().getTime();
		String image_save_path = savePath+ "checkNum" + n + ".jpg";
		// 处理验证url
		String img_path_url = "http://"+baseurl+"/verifycode.servlet";
		this.imgCookie=requestclient.getCodeCookie(img_path_url, image_save_path);
		return n;
	}
	
	
	public JSONObject doLogin(String username, String password,String checkNum) {
		this.username=username;
		JSONObject jsonObject=new JSONObject();
		String login_url="http://"+baseurl+"/Logon.do?method=logon";
		List<NameValuePair> mapHeader = new ArrayList<NameValuePair>();
		mapHeader.add(new BasicNameValuePair("Host", baseurl));
		mapHeader
				.add(new BasicNameValuePair("Accept",
						"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
		mapHeader.add(new BasicNameValuePair("Referer", "http://" + baseurl
				+ "/Logon.do?method=logon"));
		mapHeader
				.add(new BasicNameValuePair(
						"User-Agent",ua));
		mapHeader.add(new BasicNameValuePair("Cookie", imgCookie));

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("USERNAME", username));
		nvps.add(new BasicNameValuePair("PASSWORD", password));
		nvps.add(new BasicNameValuePair("useDogCode", ""));
		nvps.add(new BasicNameValuePair("useDogCode", ""));
		nvps.add(new BasicNameValuePair("RANDOMCODE", checkNum));
		nvps.add(new BasicNameValuePair("x", "30"));
		nvps.add(new BasicNameValuePair("y", "12"));
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
		else if(temp.indexOf("framework/main.jsp")!=-1){
			jsonObject.put("result","成功！");
			jsonObject.put("isSuccess","1");
		}
		else{
			jsonObject.put("message", "登录失败请检查您的用户名和密码");
		}
		return jsonObject;
	}
	
	public String getScore() {
		String path = "http://" + baseurl + "/xszqcjglAction.do?method=queryxscj";
		List<NameValuePair> mapHeader = new ArrayList<NameValuePair>();
		mapHeader.add(new BasicNameValuePair("Host", baseurl));
		mapHeader
				.add(new BasicNameValuePair("Accept",
						"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
		mapHeader.add(new BasicNameValuePair("Referer", "http://" + baseurl
				+ "/jiaowu/cjgl/xszq/query_xscj.jsp"));
		mapHeader
				.add(new BasicNameValuePair(
						"User-Agent",ua));
		mapHeader.add(new BasicNameValuePair("Cookie", cookie));

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("kksj", ""));
		nvps.add(new BasicNameValuePair("kcxz", ""));
		nvps.add(new BasicNameValuePair("kcmc", ""));
		nvps.add(new BasicNameValuePair("xsfs", "qbcj"));
		nvps.add(new BasicNameValuePair("ok", ""));
		try {
			String temp = requestclient.doPost(mapHeader, nvps, path);
			return temp;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public String getTimetable() {
		String path="http://"+baseurl+"/tkglAction.do?method=goListKbByXs&istsxx=no&xnxqh=2015-2016-1&zc=&xs0101id="+username;
		List<NameValuePair> mapHeader=new ArrayList<NameValuePair>();
		mapHeader.add(new BasicNameValuePair("Host",baseurl));
		mapHeader.add(new BasicNameValuePair("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
		mapHeader.add(new BasicNameValuePair("Referer","http://"+baseurl+"/tkglAction.do?method=kbxxXs&tktime=1452326360000"));
		mapHeader.add(new BasicNameValuePair("User-Agent",ua));
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
