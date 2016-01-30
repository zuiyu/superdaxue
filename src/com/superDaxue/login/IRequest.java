package com.superDaxue.login;

import java.util.List;

import org.apache.http.NameValuePair;

public interface IRequest {
	String doPost(List<NameValuePair> headers,List<NameValuePair> params,String url);
	String doGet(List<NameValuePair> headers,String url);
	String doGet(List<NameValuePair> headers,String url,String charset);
	String doGet(List<NameValuePair> headers,String url,int time);
	String getCodeCookie(String url,String savePath);
	String getCookie();
	String getGetUrl(List<NameValuePair> headers, String url) throws Exception;
}
