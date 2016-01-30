package com.superDaxue.school.impl;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.superDaxue.login.IRequest;
import com.superDaxue.login.impl.HttpClientRequest;
import com.superDaxue.parse.ParseTool;
import com.superDaxue.school.ISchool;
import com.superDaxue.tool.DownLoadImg;

public class Sziitxy implements ISchool{
	private String username;
	private IRequest requestclient = new HttpClientRequest();
	private String baseurl="score.shenxin.ren";
	
	public String getCheckNum(String savePath) {
		return "noPassword";
	}
	
	
	public JSONObject doLogin(String username, String password,String checkNum) {
		this.username=username;
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("result","成功！");
		jsonObject.put("isSuccess","1");
		return jsonObject;
	}
	
	public String getScore() {
		String path="http://"+baseurl+"/QueryJump.php?xh="+username;
		String str="";
		try {
			str = requestclient.doGet(null,path,"gb2312");
		} catch (Exception e1) {
			e1.printStackTrace();
			return null;
		}
		return str;
	}

	public String getTimetable() {
		return "";
	}
}
