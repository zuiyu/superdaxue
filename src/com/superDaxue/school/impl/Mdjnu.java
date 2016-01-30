package com.superDaxue.school.impl;

import java.util.ArrayList;
import java.util.Calendar;
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
import com.superDaxue.tool.MD5Tool;

public class Mdjnu implements ISchool{
	private String username;
	private String cookie;
	private String imgCookie;
	private IRequest requestclient = new HttpClientRequest();
	private ParseTool parseTool=new ParseTool();
	private String baseurl="jw.mdjnu.cn";
	
	public String getCheckNum(String savePath) {
		String n = new Random().nextInt(100000)+""+new Date().getTime();
		String image_save_path = savePath+ "checkNum" + n + ".jpg";
		// 处理验证url
		String img_path_url = "http://"+baseurl+"/jw/cas/genValidateCode?rand=" + n;
		this.imgCookie=requestclient.getCodeCookie(img_path_url, image_save_path);
		return n;
	}
	
	
	public JSONObject doLogin(String username, String password,String checkNum) {
		JSONObject jsonObject=new JSONObject();
		this.username=username;
		String path_index="http://"+baseurl+"/jw/cas/logon.action";
		List<NameValuePair> mapHeader=new ArrayList<NameValuePair>();
		mapHeader.add(new BasicNameValuePair("Host",baseurl));
		mapHeader.add(new BasicNameValuePair("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
		mapHeader.add(new BasicNameValuePair("Referer","http://"+baseurl+"/jwweb/_data/login_new.aspx"));
		mapHeader.add(new BasicNameValuePair("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.85 Safari/537.36"));
		MD5Tool md5=new MD5Tool();
		String passCode=password = md5.GetMD5Code(md5.GetMD5Code(password)+md5.GetMD5Code(checkNum.toLowerCase()));
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("username", username));
		nvps.add(new BasicNameValuePair("password", passCode));
		nvps.add(new BasicNameValuePair("randnumber", checkNum));
		nvps.add(new BasicNameValuePair("isPasswordPolicy", "1"));
		nvps.add(new BasicNameValuePair("sbtState",""));
		String temp="";
		try {
			temp = requestclient.doPost(mapHeader, nvps, path_index);
			this.cookie=requestclient.getCookie();
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("message", "网络异常，登录错误");
		}		
		if(temp.indexOf("验证码错误")!=-1){
			jsonObject.put("message", "验证码不正确");
		}else 
		if(temp.indexOf("MainFrm.html")!=-1){
			jsonObject.put("result","成功！");
			jsonObject.put("isSuccess","1");
		}
		else{
			jsonObject.put("message", "登录失败请检查您的用户名和密码");
		}
		return jsonObject;
	}
	
	public String getScore() {
		/*String path="http://"+baseurl+"/jwweb/jxjh/Stu_byfakc_rpt.asp";
		List<NameValuePair> mapHeader=new ArrayList<NameValuePair>();
		mapHeader.add(new BasicNameValuePair("Host",baseurl));
		mapHeader.add(new BasicNameValuePair("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*//*;q=0.8"));
		mapHeader.add(new BasicNameValuePair("Referer","http://"+baseurl+"/jwweb/jxjh/Stu_byfakc.aspx"));
		mapHeader.add(new BasicNameValuePair("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.85 Safari/537.36"));
		mapHeader.add(new BasicNameValuePair("Cookie",cookie));
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("sjxz", "sjxz1"));
		nvps.add(new BasicNameValuePair("ysyx", "yscj"));
		nvps.add(new BasicNameValuePair("userCode", usercode));
		Calendar calendar=Calendar.getInstance();
		int year=calendar.get(Calendar.YEAR)+1;
		nvps.add(new BasicNameValuePair("xn1", year + ""));
		nvps.add(new BasicNameValuePair("ysyxS", "on"));
		nvps.add(new BasicNameValuePair("sjxzS", "on"));
		nvps.add(new BasicNameValuePair("menucode_current", ""));
		String temp="";
		try {
			temp=doGet(mapHeader, path);
		} catch (Exception e) {
			System.out.println(e);
		}*/
		return "";
	}

	public String getTimetable() {
		String path="http://"+baseurl+"/jw/student/wsxk.zxjg.jsp?menucode=JW130404";
		List<NameValuePair> mapHeader=new ArrayList<NameValuePair>();
		mapHeader.add(new BasicNameValuePair("Host",baseurl));
     	mapHeader.add(new BasicNameValuePair("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*;q=0.8"));
     	mapHeader.add(new BasicNameValuePair("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36"));
     	mapHeader.add(new BasicNameValuePair("Referer", "http://"+baseurl+"/jw/frame/jw/teacherstudentmenu.jsp?menucode=JW1304"));
     	mapHeader.add(new BasicNameValuePair("Cookie", cookie));
		try {
			String temp=requestclient.doGet(mapHeader,path);
			return temp;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}

