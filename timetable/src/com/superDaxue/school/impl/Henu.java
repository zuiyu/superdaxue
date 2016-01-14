package com.superDaxue.school.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;

import net.sf.json.JSONObject;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import com.superDaxue.login.IRequest;
import com.superDaxue.login.impl.HttpClientRequest;
import com.superDaxue.parse.ParseTool;
import com.superDaxue.school.ISchool;
import com.superDaxue.tool.DownLoadImg;
import com.superDaxue.tool.OCR;

public class Henu implements ISchool{
	private HenuRequestData data=new HenuRequestData();
	private String cookie;
	private String imgCookie;
	private IRequest requestclient = new HttpClientRequest();
	//此处验证码参数无效
	public JSONObject doLogin(String username, String password,String checkNum) {
		JSONObject jsonObject=new JSONObject();
        jsonObject = doLogin(cookie, checkNum, username, password);//登录
        if(!"null".equals(jsonObject.get("result").toString())){
        	this.cookie=requestclient.getCookie();
        }
        return jsonObject;
	}

	public String getScore() {
		String usercode = getUsercode();
		String path = "http://xk1.henu.edu.cn:80/student/xscj.stuckcj_data.jsp";
		try {
			String temp= requestclient.doPost(data.getScoreHeader(cookie), data.getScoreParams(usercode), path);
			return temp;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getTimetable() {
		//String path = "http://xk1.henu.edu.cn/wsxk/xkjg.ckdgxsxdkchj_data10319.jsp?params=eG49MjAxNSZ4cT0wJnhoPTAwMDAzODIyOA==";
		String path = "http://xk1.henu.edu.cn:80/student/wsxk.zxjg.jsp?menucode=JW130404";
		try {
			String temp=requestclient.doGet(data.getTimetableHeader(cookie), path);
			return temp;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private JSONObject doLogin(String cookie, String code, String username,
			String password) {
		JSONObject jsonObject = new JSONObject();
		String login_url = "http://xk1.henu.edu.cn:80/cas/logon.action"; // 登录提交到的url
		HenuRequestData data = new HenuRequestData();
		String str = "";
		str = requestclient.doPost(data.getLoginHeader(cookie),
				data.getLoginParams(username, password, code), login_url);
		jsonObject = JSONObject.fromObject(str);
		return jsonObject;

	}

	private String getUsercode() {
		String url = "http://xk1.henu.edu.cn:80/custom/js/SetRootPath.jsp";
		String html=requestclient.doGet(null, url);
		String str = "var G_USER_CODE = '";
		int start = html.indexOf(str);
		if (start == -1) {
			return "";
		}
		start=start+ str.length();
		int end=html.indexOf("'", start);
		String usercode = html.substring(start, end);
		return usercode;
           
	}
	
	public String getCheckNum(String savePath) {
		String n = new Random().nextInt(100000)+""+new Date().getTime();
		String image_save_path = savePath+ "checkNum" + n + ".jpg";
		// 处理验证url
		String img_path_url = "http://xk1.henu.edu.cn/cas/genValidateCode?rand=" + n;
		this.imgCookie=requestclient.getCodeCookie(img_path_url, image_save_path);
		return n;
	}
}
