package com.superDaxue.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.sun.org.apache.bcel.internal.generic.NEW;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import sun.misc.BASE64Encoder;

public class OCR {
	public static void main(String[] args) {
		String s="\u8bf7\u6c42\u53c2\u6570\u975e\u6cd5";
		System.out.println(new OCR().decodeUnicode(s));
	}
	//设置参数并处理结果
	public String getResult(String path) {
		String httpUrl = "http://apis.baidu.com/apistore/idlocr/ocr";
		//String imagePath="C:\\Users\\Administrator\\Desktop\\img\\test.jpg";
		String str=encodeImgageToBase64(path);
		str = str.replace(System.getProperty("line.separator"), "");
		try {
			str= URLEncoder.encode(str, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String httpArg = "fromdevice=pc&clientip=172.0.0.1&detecttype=LocateRecognize&"+
		"languagetype=CHN_ENG&imagetype=1"+
		"&image="+str;
		String jsonResult = request(httpUrl, httpArg);
		System.out.println(decodeUnicode(jsonResult));
		JSONObject jsonObject = JSONObject.fromObject(jsonResult);
		if(jsonObject.get("errNum").equals("0")){
			JSONArray jsonArray = JSONArray.fromObject(jsonObject.get("retData"));
		    String word = ((JSONObject)jsonArray.get(0)).get("word").toString();
		    return word;
		}
		return jsonObject.get("errMsg").toString();
	}
	//提交数据到接口解析
	public String request(String httpUrl, String httpArg) {
	    BufferedReader reader = null;
	    String result = null;
	    StringBuffer sbf = new StringBuffer();

	    try {
	        URL url = new URL(httpUrl);
	        HttpURLConnection connection = (HttpURLConnection) url
	                .openConnection();
	        connection.setRequestMethod("POST");
	        connection.setRequestProperty("Content-Type",
	                        "application/x-www-form-urlencoded");
	        connection.setRequestProperty("apikey",  "dd94b01eaf61ec1d4156f14f1133d26d");
	        connection.setDoOutput(true);
	        connection.getOutputStream().write(httpArg.getBytes("UTF-8"));
	        connection.connect();
	        InputStream is = connection.getInputStream();
	        reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	        String strRead = null;
	        while ((strRead = reader.readLine()) != null) {
	            sbf.append(strRead);
	            sbf.append(System.getProperty("line.separator"));
	        }
	        reader.close();
	        result = sbf.toString();
	    } catch (Exception e) {
	        e.printStackTrace();
	        return request(httpUrl,httpArg);
	    }
	    return result;
	}
	
	//base64编码
	public String encodeImgageToBase64(String imagePath) {
	    byte[] data = null;  
	    try {  
	    	File imageFile = new File(imagePath);
	        InputStream in = new FileInputStream(imageFile);  
	        data = new byte[in.available()];  
	        in.read(data);  
	        in.close();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }  
	  
	    BASE64Encoder encoder = new BASE64Encoder();  
	    return encoder.encode(data);
	} 
	
	//解码unicode
	 public String decodeUnicode(String theString) {
	        char aChar;
	        int len = theString.length();
	        StringBuffer outBuffer = new StringBuffer(len);	 
	        for (int x = 0; x < len;) {	 
	            aChar = theString.charAt(x++);
	            if (aChar == '\\') {	 
	                aChar = theString.charAt(x++);
	                if (aChar == 'u') {	 
	                    int value = 0;	 
	                    for (int i = 0; i < 4; i++) {
	                        aChar = theString.charAt(x++);
	                        switch (aChar) {	 
	                        case '0':	 
	                        case '1':	 
	                        case '2':	 
	                        case '3':	 
	                        case '4':	 
	                        case '5':	 
	                        case '6':
	                        case '7':
	                        case '8':
	                        case '9':
	                            value = (value << 4) + aChar - '0';
	                            break;
	                        case 'a':
	                        case 'b':
	                        case 'c':
	                        case 'd':
	                        case 'e':
	                        case 'f':
	                            value = (value << 4) + 10 + aChar - 'a';
	                            break;
	                        case 'A':
	                        case 'B':
	                        case 'C':
	                        case 'D':
	                        case 'E':
	                        case 'F':
	                            value = (value << 4) + 10 + aChar - 'A';
	                            break;
	                        default:
	                            throw new IllegalArgumentException(
	                                    "Malformed   \\uxxxx   encoding.");
	                        }
	                    }
	                    outBuffer.append((char) value);
	                } else {
	                    if (aChar == 't')
	                        aChar = '\t';
	                    else if (aChar == 'r')
	                        aChar = '\r';
	                    else if (aChar == 'n')
	                        aChar = '\n';
	                    else if (aChar == 'f')
	                        aChar = '\f';	 
	                    outBuffer.append(aChar);
	                }
	            } else
	                outBuffer.append(aChar);
	        }
	        return outBuffer.toString();
	    }
}
