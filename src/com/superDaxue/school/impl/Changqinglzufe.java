package com.superDaxue.school.impl;

import java.util.ArrayList;
import java.util.Calendar;
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
import com.superDaxue.tool.DownLoadImg;

public class Changqinglzufe implements ISchool{
	private String username;
	private String cookie;
	private String imgCookie;
	private IRequest requestclient = new HttpClientRequest();
	private ParseTool parseTool=new ParseTool();
	private String baseurl="222.23.177.125";
	
	public String getCheckNum(String savePath) {
		String n = new Random().nextInt(100000)+""+new Date().getTime();
		String image_save_path = savePath+ "checkNum" + n + ".jpg";
		// 处理验证url
		String img_path_url = "http://"+baseurl+"/jwweb/sys/ValidateCode.aspx?rand=" + n;
		this.imgCookie=requestclient.getCodeCookie(img_path_url, image_save_path);
		return n;
	}
	
	
	public JSONObject doLogin(String username, String password,String checkNum) {
		JSONObject jsonObject=new JSONObject();
		this.username=username;
		//String path_index="http://"+baseurl+"/default2.aspx";
		String path_index="http://"+baseurl+"/jwweb/_data/index_LOGIN.aspx";
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
		String login_url="http://"+baseurl+"/jwweb/_data/index_LOGIN.aspx";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("UserID", username));
		nvps.add(new BasicNameValuePair("PassWord", password));
		nvps.add(new BasicNameValuePair("cCode", checkNum));
		nvps.add(new BasicNameValuePair("sbtState", ""));
		nvps.add(new BasicNameValuePair("typeName", ""));
		nvps.add(new BasicNameValuePair("Sel_Type", "STU"));
		nvps.addAll(headers);
		nvps.add(new BasicNameValuePair("pcInfo","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:43.0) Gecko/20100101 Firefox/43.0Windows NT 6.1; WOW645.0 (Windows) SN:NULL"));
		List<NameValuePair> mapHeader=new ArrayList<NameValuePair>();
		mapHeader.add(new BasicNameValuePair("Host",baseurl));
		mapHeader.add(new BasicNameValuePair("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
		mapHeader.add(new BasicNameValuePair("Referer","http://"+baseurl+"/jwweb/_data/index_LOGIN.aspx"));
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
		if(temp.indexOf("replace(\"../MAINFRM.aspx\"")!=-1){
			jsonObject.put("result","成功！");
			jsonObject.put("isSuccess","1");
		}
		else{
			jsonObject.put("message", "登录失败请检查您的用户名和密码");
		}
		return jsonObject;
	}
	
	public String getScore() {
		String path="http://"+baseurl+"/jwweb/xscj/Stu_MyScore.aspx";
		List<NameValuePair> mapHeader=new ArrayList<NameValuePair>();
		mapHeader.add(new BasicNameValuePair("Host",baseurl));
		mapHeader.add(new BasicNameValuePair("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
		mapHeader.add(new BasicNameValuePair("Referer","http://"+baseurl+"/jwweb/xscj/Stu_MyScore.aspx"));
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
			nvps.add(new BasicNameValuePair("SJ", "1"));
			nvps.add(new BasicNameValuePair("btn_search", "¼ìË÷"));
			nvps.add(new BasicNameValuePair("SelXNXQ", "0"));
			nvps.add(new BasicNameValuePair("zfx_flag", "0"));
			nvps.add(new BasicNameValuePair("zxf", "0"));
			//nvps.add(new BasicNameValuePair("txt_xm", "201400000116"));
			List<NameValuePair> nvp=parseTool.parseHiddenParam(str);
			nvps.addAll(nvp);
			try {
				path="http://"+baseurl+"/jwweb/xscj/Stu_MyScore_rpt.aspx";
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
		String path="http://"+baseurl+"/jwweb/znpk/Pri_StuSel_rpt.aspx";
		List<NameValuePair> mapHeader=new ArrayList<NameValuePair>();
		mapHeader.add(new BasicNameValuePair("Host",baseurl));
		mapHeader.add(new BasicNameValuePair("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
		mapHeader.add(new BasicNameValuePair("Referer","http://"+baseurl+"/jwweb/znpk/Pri_StuSel.aspx"));
		mapHeader.add(new BasicNameValuePair("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.85 Safari/537.36"));
		mapHeader.add(new BasicNameValuePair("Cookie",cookie));
	
			Calendar calendar=Calendar.getInstance();
			int year=calendar.get(Calendar.YEAR);
			int month=calendar.get(calendar.MONTH)+1;
			String schoolyear="";
			if(month>7){
				schoolyear=year+"0";
			}else {
				schoolyear=year-1+"1";
			}
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("Sel_XNXQ",schoolyear));
			nvps.add(new BasicNameValuePair("rad", "1"));
			nvps.add(new BasicNameValuePair("px", "0"));
			nvps.add(new BasicNameValuePair("Submit01", ""));
			try {
				String temp=requestclient.doPost(mapHeader,nvps,path);
				return temp;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		
	}
}
