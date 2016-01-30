package com.superDaxue.parse;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.InputTag;
import org.htmlparser.tags.Span;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class ParseTool {
	public List<NameValuePair> parseHiddenParam(String html) throws ParserException {
		List<NameValuePair> list=new ArrayList<NameValuePair>();
		Parser parser = new Parser();
		parser.setInputHTML(html);
		parser.setEncoding("utf-8");
		NodeFilter filter = new NodeClassFilter(InputTag.class);
		NodeList nodeList = parser.extractAllNodesThatMatch(filter);
		for(int i = 0; i < nodeList.size(); i++){  
			if(nodeList.elementAt(i) instanceof InputTag){  
				InputTag inputTag=(InputTag) nodeList.elementAt(i);
				String info=inputTag.toHtml();
				int hidden_start=info.indexOf("type=hidden");
				if(hidden_start!=-1){
					if(info.indexOf("name=\"")!=-1){
						int name_start=info.indexOf("name=\"")+"name=\"".length();
						int name_end = info.indexOf("\"",name_start+1);
						String name=info.substring(name_start,name_end);
						int value_start=info.indexOf("value=");
						if(value_start!=-1){
							value_start+="value=".length();
							int value_end=info.indexOf(">",value_start);
							String value=info.substring(value_start, value_end);
							list.add(new BasicNameValuePair(name,value));
						}
					}
				}
				
			}
		}
		return list;
	}
	
	public List<NameValuePair> parseInputParam(String html) throws ParserException {
		List<NameValuePair> list=new ArrayList<NameValuePair>();
		Parser parser = new Parser();
		parser.setInputHTML(html);
		parser.setEncoding("utf-8");
		NodeFilter filter = new NodeClassFilter(InputTag.class);
		NodeList nodeList = parser.extractAllNodesThatMatch(filter);
		for(int i = 0; i < nodeList.size(); i++){  
			if(nodeList.elementAt(i) instanceof InputTag){  
				InputTag inputTag=(InputTag) nodeList.elementAt(i);
				String info=inputTag.toHtml();
				if(info.indexOf("name=\"")!=-1){
					int name_start=info.indexOf("name=\"")+"name=\"".length();
					int name_end = info.indexOf("\"",name_start+1);
					String name=info.substring(name_start,name_end);
					int value_start=info.indexOf("value=\"");
					if(value_start!=-1){
						value_start+="value=\"".length();
						int value_end=info.indexOf("\"",value_start);
						String value=info.substring(value_start, value_end);
						list.add(new BasicNameValuePair(name,value));
					}
				}
				
			}
		}
		return list;
	}
	
	public int parseYear(String html) throws ParserException {
		int year=-1;
		Parser parser = new Parser();
		parser.setInputHTML(html);
		parser.setEncoding("utf-8");
		NodeFilter filter = new NodeClassFilter(Span.class);
		NodeFilter attrFilter=new HasAttributeFilter( "id", "Label8" );
		NodeFilter andFilter=new AndFilter(filter, attrFilter);
		NodeList nodeList = parser.extractAllNodesThatMatch(andFilter);
		for(int i = 0; i < nodeList.size(); i++){  
			if(nodeList.elementAt(i) instanceof Span){  
				Span span=(Span) nodeList.elementAt(i);
				String info=span.toPlainTextString().trim();
				String[] arr=info.split("：");
				String yearStr="";
				if(arr.length==2){
					yearStr=arr[1].substring(0,2);
				}
				try{
					year=Integer.parseInt(yearStr);
					year+=2000;
				}catch (Exception e) {
					return year;
				}
			}
		}
		return year;
	}
	
	public List<NameValuePair> parseCoursesParam(String html) throws ParserException {
		List<NameValuePair> list=new ArrayList<NameValuePair>();
		Parser parser = new Parser();
		parser.setInputHTML(html);
		parser.setEncoding("utf-8");
		NodeFilter filter = new NodeClassFilter(InputTag.class);
		NodeFilter attrFilter=new HasAttributeFilter( "type", "hidden" );
		NodeFilter andFilter=new AndFilter(filter, attrFilter);
		NodeList nodeList = parser.extractAllNodesThatMatch(andFilter);
		for(int i = 0; i < nodeList.size(); i++){  
			if(nodeList.elementAt(i) instanceof InputTag){  
				InputTag inputTag=(InputTag) nodeList.elementAt(i);
				String info=inputTag.toHtml();
				if(info.indexOf("name=\"")!=-1){
					int name_start=info.indexOf("name=\"")+"name=\"".length();
					int name_end = info.indexOf("\"",name_start+1);
					String name=info.substring(name_start,name_end);
					int value_start=info.indexOf("value=\"");
					if(value_start!=-1){
						value_start+="value=\"".length();
						int value_end=info.indexOf("\"",value_start);
						String value=info.substring(value_start, value_end);
						list.add(new BasicNameValuePair(name,value));
					}
				}
				
			}
		}
		return list;
	}
	
	public String parseUsercode(String html) throws ParserException{
		Parser parser = new Parser();
		parser.setInputHTML(html);
		parser.setEncoding("utf-8");
		NodeFilter filter = new NodeClassFilter(InputTag.class);
		NodeFilter attrFilter=new HasAttributeFilter( "name", "txt_xm" );
		NodeFilter andFilter=new AndFilter(filter, attrFilter);
		NodeList nodeList = parser.extractAllNodesThatMatch(andFilter);
		for(int i = 0; i < nodeList.size(); i++){  
			if(nodeList.elementAt(i) instanceof InputTag){  
				InputTag inputTag=(InputTag) nodeList.elementAt(i);
				String info=inputTag.toHtml();
					int value_start=info.indexOf("value=");
						value_start+="value=".length();
						int value_end=info.indexOf(">",value_start);
						String value=info.substring(value_start, value_end);
						return value;
			}
		}
		return "";
	}
}
