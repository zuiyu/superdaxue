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

public class YcitParse implements IParse{
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
                   // if(columns.length==18){
                    	timeTable=new TimeTable();
	                   for (int k = 0; k < columns.length; k++) {  
	                       	Node columnNode=columns[k];
	                        String infoAll = columnNode.toPlainTextString().trim();
	                        infoAll=infoAll.replaceAll("	", "");
	                        int time=j;
	                        int week=k;
	                        if(j==1||j==6||j==11){
	                        	week=week-1;
	                        }
	                        if(j>=6){
	                        	time--;
	                        }
	                        if(j>=11){
	                        	time--;
	                        }
	                        timeTable.setWeek(week+"");
	                        timeTable.setTime(time+"-"+time);
	                        String[] infoArr=infoAll.split("\r\n");
	                        for (int l = 0; l < infoArr.length; l++) {
								String info=infoArr[l];
								int name_end=info.indexOf(":（");
								if(name_end==-1){
									continue;
								}
		                        String name=info.substring(0,name_end);
		                        timeTable.setCourseName(name);
		                        int add_start=info.indexOf("）");
		                        int add_end=info.indexOf(",");
		                        String address=info.substring(add_start+1,add_end);
		                        timeTable.setAddress(address);
		                        int cyc_end=info.indexOf("周",add_end);
		                        timeTable.setCycle(info.substring(add_end+1,cyc_end));
		                        list.add(timeTable.clone());
							}
	                        
                    }
                }
            }
        }
        return repeat(list);
	}
	
	List<TimeTable> repeat(List<TimeTable> list){
		 for (int i = 0; i < list.size(); i++) {
				TimeTable table1=list.get(i);
				if(table1==null){
					continue;
				}
				for (int j = i+1; j < list.size(); j++) {
					TimeTable table2=list.get(j);
					if(table2==null){
						continue;
					}
					String type=isSame(table1, table2);
					if(type!=null){
						if("cycle".equals(type)){
							table1.setCycle(table1.getCycle()+","+table2.getCycle());
							list.set(i, table1);
							list.set(j, null);
						}
					}
				}
			}
		 Collection nuCon = new Vector(); 
		 nuCon.add(null); 
		 list.removeAll(nuCon);
		 for (int i = 0; i < list.size(); i++) {
				TimeTable table1=list.get(i);
				if(table1==null){
					continue;
				}
				for (int j = i+1; j < list.size(); j++) {
					TimeTable table2=list.get(j);
					if(table2==null){
						continue;
					}
					String type=isSame(table1, table2);
					if(type!=null){
						if(type.indexOf("-")!=-1){
							table1.setTime(type);
							list.set(i, table1);
							list.set(j, null);
						}
					}
				}
			}
		 list.removeAll(nuCon);
		 return list;
	 }
	 
	 String isSame(TimeTable table1,TimeTable table2){
		 if(table1.getCourseName().equalsIgnoreCase(table2.getCourseName())&&(table1.getSingleDouble()==null||"".equals(table1.getSingleDouble()))){
			 if(table2.getSingleDouble()==null||"".equals(table2.getSingleDouble())){//都不是单双周的课
				 boolean week=table1.getWeek().equalsIgnoreCase(table2.getWeek());
				 String time1=table1.getTime();
				 String time2=table2.getTime();
				 boolean time = time1.equalsIgnoreCase(time2);
				 boolean address=table1.getAddress().equalsIgnoreCase(table2.getAddress());
				// boolean teacher=table1.getTeacher().equalsIgnoreCase(table2.getTeacher());
				 if(week&&time&&address){
					 return "cycle";
				 }
				 else if(week&&address){
					 return isNext(time1, time2);
					
				}
			 }
		 }
		 return null;
	 }

	String isNext(String time1, String time2) {
		String[] arr = time1.split("-");
		int[] arrint1=new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			int time_n = Integer.parseInt(arr[i]);
			arrint1[i]=time_n;
		}
		String[] arr2 = time2.split("-");
		int[] arrint2=new int[arr2.length];
		for (int i = 0; i < arr2.length; i++) {
			int time_n = Integer.parseInt(arr2[i]);
			arrint2[i]=time_n;
		}
		if(arrint1.length==2&&arrint2.length==2){
			if(arrint1[0]>arrint2[0]){
				if(arrint1[0]-arrint2[1]==1){
					return arrint2[0]+"-"+arrint1[1];
				}
			}else{
				if(arrint2[0]-arrint1[1]==1){
					return arrint1[0]+"-"+arrint2[1];
				}
			}
		}
		return null;
	}

}
