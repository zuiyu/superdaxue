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

import com.superDaxue.school.ISchool;
import com.superDaxue.tool.DownLoadImg;
import com.superDaxue.tool.OCR;

public class HenuEXP implements ISchool{
	private DefaultHttpClient httpclient = new DefaultHttpClient();
	private String image_save_path = getClass().getResource(File.separator).getFile().toString()+"checkNum";//图片保存地址
	private String image_save_dir=image_save_path;
	private HenuRequestData data=new HenuRequestData();
	private String cookie;
	//此处验证码参数无效
	public JSONObject doLogin(String username, String password,String checkNum) {
		JSONObject jsonObject=new JSONObject();
		String cookie = imgCookie();	//下载验证码，获取cookie
		if(cookie==null){
			jsonObject.put("message", "网络异常请稍后再试");
			return jsonObject;
		}
        String code = identifyImg();	//ocr识别二维码
        for (int i = 0; i < 5; i++) {
			if(code==null){
				code=identifyImg();
			}else{
				break;
			}
		}
        jsonObject = doLogin(cookie, code, username, password);//登录
        if(!"null".equals(jsonObject.get("result").toString())){
        	this.cookie=getLoginCookie();
        }
        return jsonObject;
	}

	public String getScore() {
		String usercode = getUsercode();
		String path = "http://xk1.henu.edu.cn:80/student/xscj.stuckcj_data.jsp";
		try {
			String temp= doPost(data.getScoreHeader(cookie), data.getScoreParams(usercode), path);
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
			return doGet(data.getTimetableHeader(cookie), path);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//获取验证码cookie
	private String imgCookie() {
		//处理验证url
		 BufferedReader in = null;
	        String img_path_url = "http://xk1.henu.edu.cn/cas/genValidateCode?dateTime="+new Date();
	        img_path_url=img_path_url.replace(" ", "%20");
	        try
	        {
	            httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 50000);
	            HttpGet httpGet = new HttpGet(img_path_url);
	            httpGet.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 50000);
	            HttpResponse response = httpclient.execute(httpGet); 
	            //保存图片
	            String n = new Random().nextInt(100000)+""+new Date().getTime()+".jpg";
	            image_save_path=image_save_path+File.separator+n;
	            new DownLoadImg().download(response.getEntity().getContent(), image_save_path);
	            return getLoginCookie();
	        }  catch (Exception e){
	            e.printStackTrace();
	            return null;
	        }
	}
	//利用ocr获取验证码的值
	private String identifyImg() {
		String str = new OCR().getResult(image_save_path);// CQZDMDLL.result(image_save_path);
		if (str != null && str.trim().matches("\\d{4,}"))
			return str;
		return null;
	}
	private JSONObject doLogin(String cookie,String code,String username,String password) {
		JSONObject jsonObject=new JSONObject();
		  String login_url = "http://xk1.henu.edu.cn:80/cas/logon.action"; //登录提交到的url
	        HenuRequestData data=new HenuRequestData();
	        httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 50000);
			HttpPost httppost = new HttpPost(login_url);
			for (NameValuePair valuePair:data.getLoginHeader(cookie)) {
				httppost.addHeader(valuePair.getName(), valuePair.getName());
			}
			httppost.setEntity(new UrlEncodedFormEntity(data.getLoginParams(username, password, code), Consts.UTF_8));
			httppost.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,50000);
			HttpResponse response = null;
			try {
				response = httpclient.execute(httppost);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				jsonObject.put("message", "网络异常请稍后再试");
				return jsonObject;
				
			} catch (IOException e) {
				e.printStackTrace();
				jsonObject.put("message", "网络异常请稍后再试");
				return jsonObject;
			} //提交到登录的url实现登录
			HttpEntity resEntity = response.getEntity();
			
			if (resEntity != null) {
				try {
					String str=EntityUtils.toString(resEntity, "utf-8");
					jsonObject=JSONObject.fromObject(str);
					System.out.println(jsonObject);
				} catch (Exception e) {
					e.printStackTrace();
					jsonObject.put("message", "网络异常请稍后再试");
				}
			}
			httppost.releaseConnection();
			return jsonObject;

	}
	
	public String getLoginCookie(){
		List<Cookie> cookies = httpclient.getCookieStore().getCookies();
		StringBuilder cookiesSB = new StringBuilder();
		if (cookies.isEmpty()) {
			System.out.println("None");
		} else {
			for (int i = 0; i < cookies.size(); i++) {
				cookiesSB.append(cookies.get(i).getName()).append("=")
						.append(cookies.get(i).getValue()).append("; ");
			}
		}
		return cookiesSB.toString();
	}

	private String getUsercode() {
		String url = "http://xk1.henu.edu.cn:80/custom/js/SetRootPath.jsp";
		httpclient.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 50000);//httpclient 
		HttpGet httpGet = new HttpGet(url);
		httpGet.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
				50000);											//httpget 2秒超时
		HttpResponse response = null;
		try {
			response = httpclient.execute(httpGet);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("get : "+ response.getStatusLine());
		HttpEntity resEntity = response.getEntity();
		String html = "";
		if (resEntity != null) {
			try {
				html = EntityUtils.toString(resEntity);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String str = "var G_USER_CODE = '";
		int start = html.indexOf(str);
		if (start == -1) {
			return "";
		}
		start=start+ str.length();
		int end=html.indexOf("'", start);
		String usercode = html.substring(start, end);
		httpGet.releaseConnection();	//断开连接
		return usercode;
           
	}
	
	
	private String doPost(List<NameValuePair> headers,List<NameValuePair> params, String url) throws Exception {
		httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 20000);
		HttpPost httppost = new HttpPost(url);
		for (NameValuePair valuePair : headers) {
			httppost.addHeader(valuePair.getName(), valuePair.getValue());
		}
		httppost.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));
		httppost.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,20000);
		HttpResponse response = httpclient.execute(httppost);
		System.out.println("post : " + response.getStatusLine());
		HttpEntity resEntity = response.getEntity();
		String html = "";
		if (resEntity != null) {
			html = EntityUtils.toString(resEntity);
		}
		httppost.releaseConnection();
		return html;
	}

	private String doGet(List<NameValuePair> headers, String url) throws Exception {
		httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 20000);// httpclient
		HttpGet httpGet = new HttpGet(url);
		httpGet.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 20000);
		for (NameValuePair valuePair : headers) {
			httpGet.addHeader(valuePair.getName(), valuePair.getValue());
		}// httpget 2秒超时
		HttpResponse response = httpclient.execute(httpGet);
		System.out.println("get : " + response.getStatusLine());
		HttpEntity resEntity = response.getEntity();
		String html = "";
		if (resEntity != null) {
			html = EntityUtils.toString(resEntity);
		}
		httpGet.releaseConnection(); // 断开连接
		return html;
	}

	public String getCheckNum(String savePath) {
		// TODO Auto-generated method stub
		return null;
	}
}
