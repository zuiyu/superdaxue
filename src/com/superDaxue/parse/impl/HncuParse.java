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

public class HncuParse implements IParse{
/***
 * 青果非图片版
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
	        NodeFilter attrFilter=new HasAttributeFilter( "id", "ID_Table" );
	        NodeFilter andfFilter=new AndFilter(filter, attrFilter);
	        NodeList nodeList = null;
			try {
				nodeList = parser.extractAllNodesThatMatch(andfFilter);
			} catch (ParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    
			 List<Courses> list = new ArrayList<Courses>();
		        String schoolyear="";
		        String semester="";
		        for(int i = 0; i < nodeList.size(); i++){  
		            if(nodeList.elementAt(i) instanceof TableTag){  
		                TableTag tag = (TableTag) nodeList.elementAt(i);  
		                TableRow[] rows = tag.getRows();  
		                for (int j = 0; j < rows.length; j++) {  
		                    TableRow row = (TableRow) rows[j];  
		                    TableColumn[] columns = row.getColumns();  
		                    Courses courses= null;
		                    boolean isCourse=false;
		                    for (int k = 0; k < columns.length; k++) {  
		                    	Node columnNode=columns[k];
		                        String info = columnNode.toPlainTextString().trim(); 
		                        String temp="学年第";
		                        int start = info.indexOf(temp);
		                        int len="2012-2013".length();
		                		if (start != -1) {
		                			schoolyear = info.substring(0,start);
		                			//semester = info.substring(start+len+2);
		                			//网络正常时候测试学期改为数字
		                			semester = info.substring(start+3,start+4);
		                			if("一".equals(semester)){
		                				semester="1";
		                			}
		                			else if("二".equals(semester)){
		                				semester="2";
		                			}
		                		}
		                		if(k==1&&info.indexOf("[")!=-1){
		                			courses=new Courses();
		                			int code_start=info.indexOf("[");
		                			int code_end=info.indexOf("]");
		                			String courseCode=info.substring(code_start+1,code_end);
		                			String coursesname=info.substring(code_end+1);
		                			courses.setCourseCode(courseCode);
		                			courses.setCoursesname(coursesname);
		                			isCourse=true;
		                		} 
		                		if(k==2&&isCourse){
		                			double credit=Double.parseDouble(info);
		                			courses.setCredit(credit);
		                		}
		                		if(k==3&&isCourse){
		                			courses.setType(info);
		                		}
		                		if(k==5&&isCourse){
		                			courses.setLeanType(info);
		                		}
		                		if(k==4&&isCourse){
		                			courses.setCheckType(info);
		                		}
		                		if(k==6&&isCourse){
		                			courses.setScore(info);
		                		}
		                		if(k==7&&isCourse){
		                			courses.setCredit(Double.parseDouble(info));
		                		}
		                		if(k==10&&isCourse){
		                			courses.setRemark(info);
		                		}                	
		                    }// end for k  
		                    if(courses!=null){
	        				courses.setSchoolYear(schoolyear);
	            			courses.setSemester(semester);
	            			list.add(courses);
	        			}	
		                }// end for j  
		            }  
		        }
		        return list;
	}
	
	public List<TimeTable> parseTimeTables(String html) {
		Parser parser = new Parser();  
        try {
			parser.setInputHTML(html);
			 parser.setEncoding("utf-8");
		} catch (ParserException e) {
			e.printStackTrace();
		}  
         
       List<TimeTable> list=new ArrayList<TimeTable>();
       NodeFilter filter = new NodeClassFilter(TableTag.class); 
       NodeFilter idFilter=new HasAttributeFilter("bgcolor","#89bfa7");
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
                TimeTable timeTable=new TimeTable();
                for (int j = 2; j < rows.length; j++) {  
                    TableRow row = (TableRow) rows[j];  
                    TableColumn[] columns = row.getColumns();  
                    for (int k = 0; k < columns.length; k++) {  
                    	Node columnNode=columns[k];
                        String info = columnNode.toPlainTextString().trim();
                        if("".equals(info)||"&nbsp;".equals(info)){
                        	continue;
                        }
                        //System.out.println(info+"====="+j+","+k);
                        switch (k) {
						case 1:
							int start =info.indexOf("[");
							int end = info.indexOf("]");
							if(start!=-1&&end!=-1){
								timeTable.setCourseCode(info.substring(start+1,end));
								timeTable.setCourseName(info.substring(end+1));
							}
							break;
						case 2:
							timeTable.setCredit(Double.parseDouble(info));
							break;
						case 6:
							timeTable.setType(info);
							break;
						case 10:
							timeTable.setCycle(info);
							break;
						case 11:
							
							int t_start=info.indexOf("[");
							int t_end=info.indexOf("]");
							if(t_start!=-1&&t_end!=-1){
								timeTable.setWeek("周"+info.substring(0,1));
								timeTable.setTime(info.substring(t_start+1,t_end-1));
							}
							break;
						case 12:
							timeTable.setAddress(info);
							if(timeTable.getCourseName()!=null){
	                        	list.add(timeTable.clone());
	                        }
							break;
						default:
							break;
						}
                        
                    }// end for k  
                }// end for j  
            }  
        }
        return list;
	}
	
}
