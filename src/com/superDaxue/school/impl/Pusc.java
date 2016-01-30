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
public class Pusc implements ISchool{
	private String cookie;
	private String imgCookie;
	private String username;
	private IRequest requestclient = new HttpClientRequest();
	private ParseTool parseTool=new ParseTool();
	private String baseurl="218.59.189.229";
	private String ua="Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; Trident/7.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; GWX:QUALIFIED; .NET4.0E)";
	//"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; Trident/7.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; GWX:QUALIFIED; .NET4.0E)";
	public String getCheckNum(String savePath) {
		String n = new Random().nextInt(100000)+""+new Date().getTime();
		String image_save_path = savePath+ "checkNum" + n + ".jpg";
		// 处理验证url
		String img_path_url = "http://"+baseurl+"/cas/getVerificationCode?dateTime="+n;
		this.imgCookie=requestclient.getCodeCookie(img_path_url, image_save_path);
		return n;
	}
	
	
	public JSONObject doLogin(String username, String password,String checkNum) {
		this.username=username;
		JSONObject jsonObject=new JSONObject();
		String login_url="http://"+baseurl+"/cas/login?service=http%3A%2F%2F"+baseurl+"%2Flogin";
		List<NameValuePair> mapHeader = new ArrayList<NameValuePair>();
		mapHeader.add(new BasicNameValuePair("Host", baseurl));
		mapHeader
				.add(new BasicNameValuePair("Accept",
						"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
		mapHeader.add(new BasicNameValuePair("Referer", "http://" + baseurl
				+ "/cas/login?service=http%3A%2F%2F"+baseurl+"%2Flogin"));
		mapHeader
				.add(new BasicNameValuePair(
						"User-Agent",ua));
		mapHeader.add(new BasicNameValuePair("Cookie", imgCookie));
		
		List<NameValuePair> headers= new ArrayList<NameValuePair>();
		String html;
		try {
			html = requestclient.doGet(mapHeader, login_url);
			headers= parseTool.parseCoursesParam(html);
		} catch (Exception e1) {
			e1.printStackTrace();
			jsonObject.put("message", "网络异常请稍后再试");
			return jsonObject;
		}

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("username", username));
		nvps.add(new BasicNameValuePair("password", password));
		nvps.add(new BasicNameValuePair("vcode", checkNum));
		nvps.addAll(headers);
		
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
		//else if(temp.indexOf("framework/main.jsp")!=-1){
			jsonObject.put("result","成功！");
			jsonObject.put("isSuccess","1");
			baseurl="218.59.189.226";
			try {
				mapHeader.add(new BasicNameValuePair("Cookie",cookie));
				mapHeader.add(new BasicNameValuePair("Referer", "http://" + baseurl
						+ "/jsxsd/xskb/xskb_list.do"));
				requestclient.doGet(mapHeader, "http://218.59.189.226/jsxsd/framework/main.jsp");
			} catch (Exception e) {
				// TODO: handle exception
			}
		/*}
		else{
			jsonObject.put("message", "登录失败请检查您的用户名和密码");
		}*/
		return jsonObject;
	}
	
	public String getScore() {
		String path = "http://" + baseurl + "/jsxsd/kscj/cjcx_list";
		List<NameValuePair> mapHeader = new ArrayList<NameValuePair>();
		mapHeader.add(new BasicNameValuePair("Host", baseurl));
		mapHeader
				.add(new BasicNameValuePair("Accept",
						"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
		mapHeader.add(new BasicNameValuePair("Referer", "http://" + baseurl
				+ "/jsxsd/kscj/cjcx_query"));
		mapHeader
				.add(new BasicNameValuePair(
						"User-Agent",ua));
		mapHeader.add(new BasicNameValuePair("Cookie", cookie));

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("kksj", ""));
		nvps.add(new BasicNameValuePair("kcxz", ""));
		nvps.add(new BasicNameValuePair("kcmc", ""));
		nvps.add(new BasicNameValuePair("xsfs", "all"));
		
		try {
			String temp = requestclient.doPost(mapHeader,nvps, path);
			return temp;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public String getTimetable() {
		String path="http://"+baseurl+"/jsxsd/xskb/xskb_list.do";
		List<NameValuePair> mapHeader=new ArrayList<NameValuePair>();
		mapHeader.add(new BasicNameValuePair("Host",baseurl));
		mapHeader.add(new BasicNameValuePair("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
		mapHeader.add(new BasicNameValuePair("Referer","http://"+baseurl+"/jsxsd/pyfa/pyfa_query?Ves632DSdyV=NEW_XSD_PYGL"));
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
