package com.superDaxue.school.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.superDaxue.login.IRequest;
import com.superDaxue.login.impl.HttpClientRequest;
import com.superDaxue.school.ISchool;
import com.superDaxue.tool.DateTool;

public class Gzmodern implements ISchool{
	private String cookie;
	private String baseurl="my.gzmodern.cn";
	private String username;
	private IRequest requestclient = new HttpClientRequest();
	public String getCheckNum(String savePath) {
		return null;
	}
	
	public JSONObject doLogin(String username, String password,String checkNum) {
		this.username=username;
		JSONObject jsonObject=new JSONObject();
		String login_url="http://"+baseurl+"/userPasswordValidate.portal";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("Login.Token1", username));
		nvps.add(new BasicNameValuePair("Login.Token2", password));
		nvps.add(new BasicNameValuePair("goto", "http://"+baseurl+"/loginSuccess.portal"));
		nvps.add(new BasicNameValuePair("gotoOnFail", "http://"+baseurl+"/loginFailure.portal"));

		List<NameValuePair> mapHeader=new ArrayList<NameValuePair>();
		mapHeader.add(new BasicNameValuePair("Host",baseurl));
		mapHeader.add(new BasicNameValuePair("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
		mapHeader.add(new BasicNameValuePair("Referer","http://"+baseurl+"/index.portal"));
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
		}else 
		if(temp.indexOf("handleLoginSuccessed")!=-1){
			jsonObject.put("result","成功！");
			jsonObject.put("isSuccess","1");
			mapHeader.add(new BasicNameValuePair("Cookie",cookie));
			try {
				temp=requestclient.doGet(mapHeader, "http://jw.gzmodern.cn/cas_hnsf.aspx");
				cookie=requestclient.getCookie();
				baseurl="jw.gzmodern.cn";
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		else{
			jsonObject.put("message", "登录失败请检查您的用户名和密码");
		}
		return jsonObject;
	}
	
	public String getScore() {
		/*String path="http://"+baseurl+"/xscj/c_ydcjrdjl_rpt.aspx";
		List<NameValuePair> mapHeader=new ArrayList<NameValuePair>();
		mapHeader.add(new BasicNameValuePair("Host",baseurl));
		mapHeader.add(new BasicNameValuePair("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,* /*;q=0.8"));
		mapHeader.add(new BasicNameValuePair("Referer","http://"+baseurl+"/xscj/c_ydcjrdjl.asp"));
		mapHeader.add(new BasicNameValuePair("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.85 Safari/537.36"));
		mapHeader.add(new BasicNameValuePair("Cookie",cookie));
		try {
			
			int yearStudent=Integer.parseInt(username.substring(0, 2))+2000;
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
		}*/
		return "";
	}

	public String getTimetable() {
		String path="http://"+baseurl+"/wsxk/stu_zxjg_rxyl.aspx";
		List<NameValuePair> mapHeader=new ArrayList<NameValuePair>();
		mapHeader.add(new BasicNameValuePair("Host",baseurl));
		mapHeader.add(new BasicNameValuePair("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
		mapHeader.add(new BasicNameValuePair("Referer","http://"+baseurl+"/wsxk/stu_zxjg.aspx"));
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
