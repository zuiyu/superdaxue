package com.superDaxue.parse.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;
import com.superDaxue.model.Courses;
import com.superDaxue.model.TimeTable;
import com.superDaxue.parse.IParse;

public class SanquanyixueyParse implements IParse{
/***
 * 
 */
	public List<Courses> parseCourses(String html) {
		 Parser parser = new Parser();  
	        try {
				parser.setInputHTML(html);
				parser.setEncoding("utf-8");  
			} catch (ParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
	        NodeFilter filter = new NodeClassFilter(TableTag.class);  
	        NodeFilter attrFilter=new HasAttributeFilter( "id", "DataGrid1" );
	        NodeFilter andfFilter=new AndFilter(filter, attrFilter);
	        NodeList nodeList = null;
			try {
				nodeList = parser.extractAllNodesThatMatch(andfFilter);
			} catch (ParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    
	        List<Courses> list = new ArrayList<Courses>();
	        for(int i = 0; i < nodeList.size(); i++){  
	            if(nodeList.elementAt(i) instanceof TableTag){  
	                TableTag tag = (TableTag) nodeList.elementAt(i);
	                TableRow[] rows = tag.getRows();  
	                Courses courses=null;
	                boolean flag=false;
	                for (int j = 0; j < rows.length; j++) {  
	                    TableRow row = (TableRow) rows[j];  
	                    if(row.toPlainTextString().indexOf("学年学期")!=-1){
	                    	flag=true;
	                    	continue;
	                    }
	                    if(flag){
		                    TableColumn[] columns = row.getColumns(); 
		                    courses=new Courses();
		                    for (int k = 0; k < columns.length; k++) {  
		                    	Node columnNode=columns[k];
		                        String info = columnNode.toPlainTextString().trim(); 
		                        if(k==0){
		                        	courses.setSchoolYear(info);
		                        }
		                        else if(k==1){
		                        	courses.setSemester(info);
		                        }
		                        else if(k==2){
		                        	courses.setCoursesname(info);
		                        }
		                        else if(k==3){
		                        	courses.setType(info);
		                        }
		                        else if(k==4){
		                        	courses.setRemark(info);
		                        }
		                        else if(k==5){
		                        	courses.setCheckType(info);
		                        }
		                        else if(k==6){
		                        	courses.setScore(info);
		                        }
		                        else if(k==7){
									if(!"".equals(info)){
										try {
											double temp=Double.parseDouble(info);
											if(temp>Double.parseDouble(courses.getScore())){
												courses.setScore(info);
											}
										} catch (Exception e) {
										}
									}
								}
		                        else if(k==8){
		                        	courses.setCredit(Double.parseDouble(info));
		                        }
		                       
		                    }//end for k
		                    list.add(courses); 
	                    }
	                }// end for j  
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
       NodeFilter filter = new NodeClassFilter(TableTag.class); 
       NodeFilter idFilter=new HasAttributeFilter("id","table6");
       NodeFilter andFilter=new AndFilter(filter, idFilter);
        NodeList nodeList=null;
		try {
			nodeList = parser.extractAllNodesThatMatch(andFilter);
		} catch (ParserException e) {
			e.printStackTrace();
		}
        for(int i = 0; i < nodeList.size(); i++){  
            if(nodeList.elementAt(i) instanceof TableTag){  
                TableTag tag = (TableTag) nodeList.elementAt(i); 
                TableRow[] rows = tag.getRows(); 
                for (int j = 0; j < rows.length; j++) {  
                    TableRow row = (TableRow) rows[j];  
                    TableColumn[] columns = row.getColumns();  
                    TimeTable timeTable=null;
                    for (int k = 0; k < columns.length; k++) {  
                    	Node columnNode=columns[k];
                    	String ex_info = columnNode.toHtml().trim(); 
                    	//System.out.println(ex_info+"======="+i+","+j+","+k);
                    	int base=ex_info.indexOf("\">");
                    	int flag=ex_info.indexOf("<br>");
                    	if(base!=-1&&flag!=-1&&j<=16){
                    		timeTable=new TimeTable();
                    		int time=j;
                    		if(j<=6){
                    			time--;
                    		}
                    		else if(j<=11){
                    			time=time-2;
                    		}
                    		else if(j<=16){
                    			time=time-3;
                    		}
                    		int week=k;
                    		if(j==2||j==7||j==12){
                    			week--;
                    		}
                    		int na_end=ex_info.indexOf("<br>",base);
                    		int time_end=ex_info.indexOf("节/周<br>",na_end);
                    		if(time_end==-1){
                    			int week_start=ex_info.indexOf("周",na_end+1);
                    			int time_start=ex_info.indexOf("第",week_start);
                    			time_end=ex_info.indexOf("节<br>",na_end);
                    			String weekString=ex_info.substring(week_start+1,time_start);
                    			String timeString=ex_info.substring(time_start+1,time_end);
                    			timeTable.setWeek(weekString);
                    			timeTable.setTime(timeString.replace(",", "-"));
                    		}
                    		else{
                    			String lenStr=ex_info.substring(na_end+4,time_end);
                        		int len=Integer.parseInt(lenStr);
                        		String timeStr=time+"-"+(time+len-1);
                    			timeTable.setTime(timeStr);
                        		timeTable.setWeek(week+"");
                    		}
                    		int tea_end=ex_info.indexOf("<br>",time_end+"节/周<br>".length()+1);
                    		int add_end=ex_info.indexOf("<br>",tea_end+1);
                    		String name=ex_info.substring(base+3,na_end);
                    		timeTable.setCourseName(name);
                    		String teacher=ex_info.substring(time_end+"节/周<br>".length(),tea_end);
                    		timeTable.setTeacher(teacher);
                    		String address=ex_info.substring(tea_end+4,add_end);
                    		timeTable.setAddress(address);
                    		list.add(timeTable);
                    	}
                    }// end for k  
                }// end for j  
            }  
        }
        return list;
	}
}
