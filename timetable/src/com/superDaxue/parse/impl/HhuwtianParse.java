package com.superDaxue.parse.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import javax.servlet.jsp.tagext.BodyTag;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.filters.StringFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.superDaxue.model.Courses;
import com.superDaxue.model.TimeTable;
import com.superDaxue.parse.IParse;

public class HhuwtianParse implements IParse{
/**
 * URP
 */
	public List<Courses> parseCourses(String html) {
		html=html.replace("&nbsp;", "");
		Parser parser = new Parser();  
	        try {
				parser.setInputHTML(html);
				parser.setEncoding("utf-8");  
			} catch (ParserException e) {
				e.printStackTrace();
			}  
	        NodeFilter tableFilter=new NodeClassFilter(TableTag.class);
	        NodeFilter afilter=new StringFilter("学年");
	        NodeFilter attrFilter2=new HasAttributeFilter( "id", "user" );
	        NodeFilter andFilter=new AndFilter(tableFilter, attrFilter2);
	        NodeList aList=null;
	        NodeList nodeList=null;
			try {
				aList=parser.extractAllNodesThatMatch(afilter);
				parser.setInputHTML(html);
				nodeList=parser.extractAllNodesThatMatch(andFilter);
			} catch (ParserException e) {
				e.printStackTrace();
			}
	        List<Courses> list = new ArrayList<Courses>();
	        Courses courses=null;
	        if(nodeList.size()>=aList.size())
	        for(int i = 0; i < aList.size(); i++){  
	            if(nodeList.elementAt(i) instanceof TableTag){  
	                TableTag tag = (TableTag) nodeList.elementAt(i);
	                TableRow[] rows = tag.getRows();  
	                Node headNode=aList.elementAt(i);
	                String headStr=headNode.toHtml().trim();
	                int h_start=headStr.indexOf("学年");
	                String schoolyear=headStr.substring(0,h_start);
	                String semester="";
	                if(headStr.indexOf("第")!=-1){
	                	semester=headStr.substring(h_start+3,h_start+4);
	                }
	                else{
	                	semester=headStr.substring(h_start+2,h_start+3);
	                	if("春".equals(semester)){
		                	semester="2";
		                }
		                else if("秋".equals(semester)){
		                	semester="1";
		                }
	                }
	                if(semester.equals("一")){
	                	semester="1";
	                }else if(semester.equals("二")){
	                	semester="2";
	                }
	                for (int j = 0; j < rows.length; j++) {  
	                    TableRow row = (TableRow) rows[j];  
		                TableColumn[] columns = row.getColumns(); 
		                courses=new Courses();
		                courses.setSchoolYear(schoolyear);
		                courses.setSemester(semester);
		                for (int k = 0; k < columns.length; k++) {  
		                  	Node columnNode=columns[k];
		                    String info = columnNode.toPlainTextString().trim(); 
		                    switch (k) {
							case 0:
								courses.setCourseCode(info);
								break;
							case 2:
								courses.setCoursesname(info);
								break;
							case 4:
								courses.setCredit(Double.parseDouble(info));
								break;	
							case 5:
								courses.setType(info);
								break;
							case 6:
								courses.setScore(info);
								break;
							default:
								break;
							}
		                }//end for k
		                if(courses.getCoursesname()!=null){
		                	list.add(courses);
		                }
		                
	                }// end for j  
	            }  
	        }
	        for(int i = aList.size(); i < nodeList.size(); i++){  
	            if(nodeList.elementAt(i) instanceof TableTag){  
	                TableTag tag = (TableTag) nodeList.elementAt(i);
	                TableRow[] rows = tag.getRows();  
	                for (int j = 0; j < rows.length; j++) {  
	                    TableRow row = (TableRow) rows[j];  
		                TableColumn[] columns = row.getColumns(); 
		                if(columns.length>1&&"0.0".equals(columns[1].toPlainTextString().trim())){
		                	continue;
		                }
		                courses=new Courses();
		                for (int k = 0; k < columns.length; k++) {  
		                  	Node columnNode=columns[k];
		                    String info = columnNode.toPlainTextString().trim(); 
		                    switch (k) {
							case 0:
								courses.setCourseCode(info);
								break;
							case 2:
								courses.setCoursesname(info);
								break;
							case 4:
								courses.setCredit(Double.parseDouble(info));
								break;
							case 5:
								courses.setType(info);
								break;
							case 6:
								courses.setScore(info);
								break;
							case 7:
								String yearStr=info.substring(0,4);
								int year=Integer.parseInt(yearStr);
								String monthStr=info.substring(4,6);
								int month=Integer.parseInt(monthStr);
								String schoolYear="";
								String n="";
								if (month<8) {
									schoolYear=year-1+"-"+year;
									n="2";
								}else {
									schoolYear=year+"-"+year+1;
									n="1";
								}
								courses.setSchoolYear(schoolYear);
								courses.setSemester(n);
								break;
							default:
								break;
							}
		                }
		                if(courses.getCoursesname()!=null)
		                	list.add(courses);
	                }
	            }
	        }
	        return list;
	}

	public List<TimeTable> parseTimeTables(String html) {
		html=html.replace("&nbsp;", "");
		Parser parser = new Parser();  
        try {
			parser.setInputHTML(html);
			 parser.setEncoding("utf-8");
		} catch (ParserException e) {
			e.printStackTrace();
		}  
         
       List<TimeTable> list=new ArrayList<TimeTable>();
        NodeFilter attrFilter1 = new NodeClassFilter(TableTag.class); 
        NodeFilter attrFilter2=new HasAttributeFilter( "id", "user" );
        NodeFilter andfFilter=new AndFilter(attrFilter1, attrFilter2);
        NodeList nodeList=null;
		try {
			nodeList=parser.extractAllNodesThatMatch(andfFilter);
			if(nodeList.size()>1)
				nodeList.remove(0);
		} catch (ParserException e) {
			e.printStackTrace();
		}
        for(int i = 0; i < nodeList.size(); i++){  
            if(nodeList.elementAt(i) instanceof TableTag){  
                TableTag tag = (TableTag) nodeList.elementAt(i);
                TableRow[] rows = tag.getRows(); 
                TimeTable timeTable=null;
                
                for (int j = 1; j < rows.length; j++) {  
                    TableRow row = (TableRow) rows[j];  
                    TableColumn[] columns = row.getColumns(); 
                    if(columns.length==18){
                    	timeTable=new TimeTable();
	                   for (int k = 0; k < columns.length; k++) {  
	                       	Node columnNode=columns[k];
	                        String info = columnNode.toPlainTextString().trim();
	                        if ("".equals(info)) {
								continue;
							}
	                        switch (k) {
							case 0:
								timeTable.setClassNum(info);
								break;
							case 1:
								timeTable.setCourseCode(info);
								break;
							case 2:
								timeTable.setCourseName(info);
								break;
							case 4:
								timeTable.setCredit(Double.parseDouble(info));
								break;
							case 5:
								timeTable.setType(info);
								break;
							case 7:
								timeTable.setTeacher(info);
								break;
							case 11:
								int cyc_start=info.indexOf("周");
								timeTable.setCycle(info.substring(0,cyc_start));
								break;
							case 12:
								timeTable.setWeek(info);
								break;
							case 13:
								int start=Integer.parseInt(info.replace("小", ""));
		                        String temp = columns[++k].toPlainTextString().trim();
								int len=Integer.parseInt(temp);
								timeTable.setTime(start+"-"+(start+len-1));
								break;
							case 17:
								timeTable.setAddress(info);
								break;
							default:
								break;
							}
						}//end for k
	                   if(timeTable.getWeek()!=null){
	                	   list.add(timeTable);
	                   }
                    }
                    else if(columns.length==7){
                    	TimeTable timeTable2=timeTable.clone();
                    	 for (int k = 0; k < columns.length; k++) {  
 	                       	Node columnNode=columns[k];
 	                        String info = columnNode.toPlainTextString().trim();
 	                        switch (k) {
							case 0:
								timeTable2.setCycle(info.replace("周", ""));
								break;
							case 1:
								timeTable2.setWeek(info);
								break;
							case 2:
								int start=Integer.parseInt(info.replace("小", ""));
								String temp = columns[++k].toPlainTextString().trim();
								int len=Integer.parseInt(temp);
								timeTable2.setTime(start+"-"+(start+len-1));
								break;
							case 6:
								timeTable2.setAddress(info);
								break;
							default:
								break;
							}
 						}//end for k
                    	 if(timeTable2.getWeek()!=null){
                    		 list.add(timeTable2);
                    	 }
                    	 
                    }
                }
            }
        }
        return list;
	}

}
