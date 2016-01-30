package com.superDaxue.parse.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import javax.sound.midi.MidiDevice.Info;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.StringFilter;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;
import com.superDaxue.model.Courses;
import com.superDaxue.model.TimeTable;
import com.superDaxue.parse.IParse;
import com.superDaxue.tool.DateTool;

public class XxuParse implements IParse{
/***
 * 青果图文版
 */
	public List<Courses> parseCourses(String html) {
		 Parser parser = new Parser();  
	        try {
				parser.setInputHTML(html);
				parser.setEncoding("utf-8");  
			} catch (ParserException e) {
				e.printStackTrace();
			}  
	        NodeFilter filter = new NodeClassFilter(TableTag.class);  
	        NodeFilter attrFilter=new HasAttributeFilter( "bgcolor", "#89bfa7" );
	        NodeFilter andfFilter=new AndFilter(filter, attrFilter);
	        NodeList nodeList = null;
			try {
				nodeList = parser.extractAllNodesThatMatch(andfFilter);
			} catch (ParserException e) {
				e.printStackTrace();
			}
	    
	        List<Courses> list = new ArrayList<Courses>();
	        for(int i = 0; i < nodeList.size(); i++){  
	            if(nodeList.elementAt(i) instanceof TableTag){  
	                TableTag tag = (TableTag) nodeList.elementAt(i);
	                TableRow[] rows = tag.getRows();  
	                Courses courses=null;
	                for (int j = 0; j < rows.length; j++) {  
	                    TableRow row = (TableRow) rows[j];  
	                    	if(row.toPlainTextString().indexOf("[")==-1){
	                    		continue;
	                    	}
		                    TableColumn[] columns = row.getColumns(); 
		                    courses=new Courses();
		                    boolean flag=false;
		                    for (int k = 0; k < columns.length; k++) {  
		                    	Node columnNode=columns[k];
		                        String info = columnNode.toPlainTextString().trim(); 
		                        switch (k) {
								case 0:
									int co_start=info.indexOf("[");
									int co_end=info.indexOf("]");
									String code=info.substring(co_start+1,co_end);
									courses.setCourseCode(code);
									String name=info.substring(co_end+1);
									courses.setCoursesname(name);
									break;
								case 1:
									courses.setCredit(Double.parseDouble(info));
									break;
								case 2:
									courses.setType(info);
									break;
								case 3:
									courses.setGetType(info);
									break;
								case 4:
									courses.setCheckType(info);
									break;
								case 7:
									courses.setScore(info);
									break;
								case 8:
									int y_end=info.indexOf("学年");
									String year=info.substring(0,y_end);
									courses.setSchoolYear(year);
									int n_start=info.indexOf("第");
									int n_end=info.indexOf("学期");
									String nStr=info.substring(n_start+1,n_end);
									String n="";
									if("一".equals(nStr)){
										n="1";
									}
									else if("二".equals(nStr)){
										n="2";
									}
									courses.setSemester(n);
									break;
								default:
									break;
								}
		                    }//end for k
	                    list.add(courses);
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
       NodeFilter idFilter=new HasAttributeFilter("name","theExportData");
       NodeFilter andFilter=new AndFilter(filter, idFilter);
       NodeFilter heardFilter=new StringFilter("学年学期");
       NodeList headList=null;
       NodeList nodeList=null;
		try {
			nodeList = parser.extractAllNodesThatMatch(andFilter);
			parser.setInputHTML(html);
			headList=parser.extractAllNodesThatMatch(heardFilter);
		} catch (ParserException e) {
			e.printStackTrace();
		}
		int n=-1;
		
		String[] arr=new DateTool().getThisYearSemester();
		String schoolyear=arr[0];
		String semester=arr[1];
		for (int i = 0; i < headList.size(); i++) {
			String info=headList.elementAt(i).toPlainTextString();
			int start=info.indexOf("学年学期：");
			int end=info.indexOf("学年第");
			String yearString=info.substring(start+5,end);
			String semesterString=info.substring(end+3,end+4);
			if(schoolyear.equals(yearString)&&semester.equals(semesterString)){
				n=i;
			}
		}
		if (n == -1) {
			return list;
		}
		if (nodeList.elementAt(n) instanceof TableTag) {
			TableTag tag = (TableTag) nodeList.elementAt(n);
			TableRow[] rows = tag.getRows();
			TimeTable timeTable = new TimeTable();
			for (int j = 2; j < rows.length; j++) {
				TableRow row = (TableRow) rows[j];
				TableColumn[] columns = row.getColumns();
				for (int k = 0; k < columns.length; k++) {
					Node columnNode = columns[k];
					String info = columnNode.toPlainTextString().trim();
					if("".equals(info)){
						continue;
					}
					switch (k) {
					case 1:
						int co_start=info.indexOf("[");
						int co_end=info.indexOf("]");
						if(co_start!=-1&&co_end!=-1){
							String code=info.substring(co_start+1,co_end);
							timeTable.setCourseCode(code);
							String name=info.substring(co_end+1);
							timeTable.setCourseName(name);
						}
						break;
					case 2:
						try{
							timeTable.setCredit(Double.parseDouble(info));
						}catch (Exception e) {
						}
						break;
					case 3:
						timeTable.setType(info);
						break;
					case 5:
						timeTable.setTeacher(info);
						break;
					case 7:
						timeTable.setClassNum(info);
						break;
					case 11:
						String[] addArr=info.split("@");
						for (int i = 0; i < addArr.length; i++) {
							String add=addArr[i];
							if("".equals(add)){
								continue;
							}
							int cyc_start=add.indexOf("[",0);
							int cyc_end=add.indexOf("周]",cyc_start);
							String cyc=add.substring(cyc_start+1,cyc_end);
							timeTable.setCycle(cyc);
							int week_start=add.indexOf("星期",cyc_end);
							String week=add.substring(week_start+2,week_start+3);
							timeTable.setWeek("周"+week);
							int time_start=add.indexOf("[",week_start+3);
							int time_end=add.indexOf("节]",time_start);
							String time=add.substring(time_start+1,time_end);
							if(time.indexOf("-")==-1&&time.length()<3){
								timeTable.setTime(time+"-"+time);
							}
							else{
								timeTable.setTime(time);
							}
							int address_start=add.indexOf("]/",time_end);
							String address=add.substring(address_start+2);
							timeTable.setAddress(address);
							list.add(timeTable.clone());
						}
						break;
					default:
						break;
					}
				}// end for k
			}// end for j
		}
		return repeat(list);
	}
	
	private List<TimeTable> repeat(List<TimeTable> list){
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
		 return list;
	 }
	
	private String isSame(TimeTable table1,TimeTable table2){
		 if(table1.getCourseName().equalsIgnoreCase(table2.getCourseName())&&(table1.getSingleDouble()==null||"".equals(table1.getSingleDouble()))){
			 if(table2.getSingleDouble()==null||"".equals(table2.getSingleDouble())){//都不是单双周的课
				 boolean week=table1.getWeek().equalsIgnoreCase(table2.getWeek());
				 String time1=table1.getTime();
				 String time2=table2.getTime();
				 boolean time = time1.equalsIgnoreCase(time2);
				 boolean address=table1.getAddress().equalsIgnoreCase(table2.getAddress());
				 boolean teacher=true;
				 if(week&&time&&address&&teacher){
					 return "cycle";
				 }
			 }
		 }
		 return null;
	 }
}
