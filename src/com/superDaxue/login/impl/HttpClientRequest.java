package com.superDaxue.login.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import com.superDaxue.login.IRequest;
import com.superDaxue.tool.DownLoadImg;

public class HttpClientRequest implements IRequest {
	private DefaultHttpClient httpclient = new DefaultHttpClient();

	public String doPost(List<NameValuePair> headers,
			List<NameValuePair> params, String url) {
		httpclient.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 20000);
		HttpPost httppost = new HttpPost(url);
		if(headers!=null){
			for (NameValuePair valuePair : headers) {
				httppost.addHeader(valuePair.getName(), valuePair.getValue());
			}
		}
		httppost.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));
		httppost.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
				200000);
		HttpResponse response = null;
		try {
			response = httpclient.execute(httppost);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} // 提交到登录的url实现登录
		System.out.println("post : " + response.getStatusLine());
		HttpEntity resEntity = response.getEntity();
		String html = "";
		if (resEntity != null) {
			try {
				html = EntityUtils.toString(resEntity);
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		httppost.releaseConnection();
		return html;
	}

	public String doGet(List<NameValuePair> headers, String url) {
		httpclient.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 20000);// httpclient
																// 2秒超时
		HttpGet httpGet = new HttpGet(url);
		httpGet.getParams()
				.setParameter(CoreConnectionPNames.SO_TIMEOUT, 20000);
		if(headers!=null){
			for (NameValuePair valuePair : headers) {
				httpGet.addHeader(valuePair.getName(), valuePair.getValue());
			}// httpget 2秒超时
		}
		HttpResponse response = null;
		try {
			response = httpclient.execute(httpGet);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("get : " + response.getStatusLine());
		HttpEntity resEntity = response.getEntity();
		String html = "";
		if (resEntity != null) {
			try {
				html = EntityUtils.toString(resEntity);
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		httpGet.releaseConnection(); // 断开连接
		return html;
	}

	public String getCodeCookie(String url,String savePath) {
		List<NameValuePair> headers=new ArrayList<NameValuePair>();
		headers.add(new BasicNameValuePair("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
		headers.add(new BasicNameValuePair("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.85 Safari/537.36"));
		
		try {
			httpclient.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, 500000);
			HttpGet httpGet = new HttpGet(url);
			httpGet.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
					500000);
			if(headers!=null){
				for (NameValuePair valuePair : headers) {
					httpGet.addHeader(valuePair.getName(), valuePair.getValue());
				}
			}
			HttpResponse response = httpclient.execute(httpGet);
			new DownLoadImg().download(response.getEntity().getContent(),savePath);
			httpGet.releaseConnection(); // 断开连接
			return getCookie();
			// 保存图片
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public String getCookie(){
		List<Cookie> cookies = httpclient.getCookieStore().getCookies();
		StringBuilder cookiesSB = new StringBuilder();
		if (cookies.isEmpty()) {
		} else {
			for (int i = 0; i < cookies.size(); i++) {
				cookiesSB.append(cookies.get(i).getName()).append("=")
						.append(cookies.get(i).getValue()).append("; ");
			}
		}
		return cookiesSB.toString();
	}
	
	public String getGetUrl(List<NameValuePair> headers, String url) throws Exception {
		httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 50000);
		HttpGet httpget = new HttpGet(url);
		if(headers!=null){
			for (NameValuePair valuePair : headers) {
				httpget.addHeader(valuePair.getName(), valuePair.getValue());
			}
		}
		httpget.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,50000);
		HttpContext httpContext = new BasicHttpContext();
		HttpResponse response = httpclient.execute(httpget,httpContext);
		HttpUriRequest realRequest = (HttpUriRequest)httpContext.getAttribute(ExecutionContext.HTTP_REQUEST);
		httpget.releaseConnection();
		return realRequest.getURI().toString();
	}
	
	public String doGet(List<NameValuePair> headers, String url,int time) {
		httpclient.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 20000);// httpclient
																// 2秒超时
		HttpGet httpGet = new HttpGet(url);
		httpGet.getParams()
				.setParameter(CoreConnectionPNames.SO_TIMEOUT, 20000);
		if(headers!=null){
			for (NameValuePair valuePair : headers) {
				httpGet.addHeader(valuePair.getName(), valuePair.getValue());
			}// httpget 2秒超时
		}
		HttpResponse response = null;
		try {
			response = httpclient.execute(httpGet);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			Thread.sleep(time);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		System.out.println("get : " + response.getStatusLine());
		HttpEntity resEntity = response.getEntity();
		String html = "";
		if (resEntity != null) {
			try {
				html = EntityUtils.toString(resEntity);
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		httpGet.releaseConnection(); // 断开连接
		return html;
	}

	public String doGet(List<NameValuePair> headers, String url, String charset) {
		httpclient.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 20000);// httpclient
																// 2秒超时
		HttpGet httpGet = new HttpGet(url);
		httpGet.getParams()
				.setParameter(CoreConnectionPNames.SO_TIMEOUT, 20000);
		if(headers!=null){
			for (NameValuePair valuePair : headers) {
				httpGet.addHeader(valuePair.getName(), valuePair.getValue());
			}// httpget 2秒超时
		}
		HttpResponse response = null;
		try {
			response = httpclient.execute(httpGet);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("get : " + response.getStatusLine());
		HttpEntity resEntity = response.getEntity();
		String html = "";
		if (resEntity != null) {
			try {
				html = EntityUtils.toString(resEntity,charset);
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		httpGet.releaseConnection(); // 断开连接
		return html;
	}
}
