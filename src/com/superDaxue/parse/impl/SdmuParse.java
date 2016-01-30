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
import org.htmlparser.tags.OptionTag;
import org.htmlparser.tags.Span;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.sun.org.apache.xpath.internal.operations.Div;
import com.superDaxue.model.Courses;
import com.superDaxue.model.TimeTable;
import com.superDaxue.parse.IParse;

public class SdmuParse implements IParse{
/**
 * 成绩没有学期学年，多个页面拼接
 * 课表调课停课较多
 */
	public List<Courses> parseCourses(String html) {
		List<Courses> list = new ArrayList<Courses>();
		Parser parser = new Parser();  
        try {
			parser.setInputHTML(html);
			parser.setEncoding("utf-8");
		} catch (ParserException e) {
			e.printStackTrace();
		}  
        NodeFilter spanfilter = new NodeClassFilter(Span.class); 
        NodeFilter idFilter=new HasAttributeFilter("id","Label4");
        NodeFilter andFilter=new AndFilter(spanfilter, idFilter);
        
        NodeFilter tablefilter = new NodeClassFilter(TableTag.class); 
        NodeFilter idTable=new HasAttributeFilter("id","DataGrid1");
        NodeFilter andTable=new AndFilter(tablefilter, idTable);
        
        NodeList headList=null;
        NodeList nodeList=null;
		try {
			headList = parser.extractAllNodesThatMatch(andFilter);
			parser.setInputHTML(html);
			nodeList =parser.extractAllNodesThatMatch(andTable);
			
			System.out.println(nodeList.size());
		} catch (ParserException e) {
			e.printStackTrace();
		}
		List<String[]> heads=new ArrayList<String[]>();
		for (int i = 0; i < headList.size(); i++) {
			if(headList.elementAt(i) instanceof Span){
				Span span=(Span)headList.elementAt(i);
				String info=span.toPlainTextString().trim();
				int year_end=info.indexOf("学年");
				int n_start=info.indexOf("第",year_end);
				int n_end=info.indexOf("学期",n_start);
				String year=info.substring(0,year_end);
				String n=info.substring(n_start+1,n_end);
				String[] arr={year,n};
				heads.add(arr);		
			}
		}
		
		for (int i = 0; i < nodeList.size(); i++) {
			if (nodeList.elementAt(i) instanceof TableTag) {
				TableTag tag = (TableTag) nodeList.elementAt(i);
				TableRow[] rows = tag.getRows();
				Courses courses = null;
				String schoolyear=heads.get(i)[0];
				String semester=heads.get(i)[1];
				for (int j = 1; j < rows.length; j++) {
					TableRow row = (TableRow) rows[j];
					TableColumn[] columns = row.getColumns();
					courses = new Courses();
					courses.setSchoolYear(schoolyear);
					courses.setSemester(semester);
					for (int k = 0; k < columns.length; k++) {
						Node columnNode = columns[k];
						String info = columnNode.toPlainTextString().trim();
						switch (k) {
						case 0:
							courses.setCourseCode(info);
							break;
						case 1:
							courses.setCoursesname(info);
							break;
						case 4:
							courses.setScore(info);
							break;
						case 8:
							courses.setCredit(Double.parseDouble(info));
							break;
						}
					}
					list.add(courses);
				}
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
        NodeFilter idFilter=new HasAttributeFilter("id","Table1");
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
                        String info = columnNode.toPlainTextString().trim();
                        if("".equals(info)||"&nbsp;".equals(info)){
                        	continue;
                        }
                    	String ex_info = columnNode.toHtml().trim(); 
                    	boolean flag=true;
                    	boolean isDouble=false;
                        int base=info.indexOf("{");
                        int baseStart=0;
                        int ex_base=0;
                        int cyc_max=Integer.MIN_VALUE;
                        int cyc_min=Integer.MAX_VALUE;
                        while (flag){
	                        if(base!=-1){
	                        	
	                        	timeTable=new TimeTable();
	                        	int n_end=info.indexOf("周",baseStart);
	                        	if(n_end>base){
	                        		//continue;
	                        		timeTable.setCourseName(info.substring(baseStart,base));
	                        	}
	                        	else{
		                        	timeTable.setCourseName(info.substring(baseStart,n_end));
		                        	timeTable.setWeek(info.substring(n_end,n_end+2));
		                        	int t_end=info.indexOf("节",n_end+2);
		                        	if(t_end!=-1){
		                        		String time=info.substring(n_end+3,t_end);
		                            	String[] t_arr=time.split(",");
		                            	int[] t_arrint=new int[t_arr.length];
		                            	for (int l = 0; l < t_arr.length; l++) {
		    								t_arrint[l]=Integer.parseInt(t_arr[l]);
		    							}
		                            	Arrays.sort(t_arrint);
		                            	time=t_arrint[0]+"-"+t_arrint[t_arrint.length-1];
		                            	timeTable.setTime(time);
		                        	}
	                        	}
	                        	int c_start=info.indexOf("第",base);
	                        	int c_end=info.indexOf("周",c_start);
	                        	timeTable.setCycle(info.substring(c_start+1,c_end));
	                        	int sd_start=info.indexOf("|",c_end);
	                        	if(sd_start!=-1){
	                        		int often_end=info.indexOf("/",sd_start);
	                        		if(often_end!=-1&&often_end-sd_start<5){
	                        			int often_len=Integer.parseInt(info.substring(sd_start+1, sd_start+2));
	                        			int start_time=j-1;
	                        			int start_week=k;
	                        			switch (j) {
										case 2:
										case 6:
										case 10:
											start_week--;
											break;
										}
	                        			timeTable.setWeek(start_week+"");
	                        			timeTable.setTime(start_time+"-"+(start_time+often_len-1));
	                        		}
	                        		else{
	                        			timeTable.setSingleDouble(info.substring(sd_start+1,sd_start+2));
	                        		}
	                        	}
	                        	int fen_start=info.indexOf("}",c_end);
	                        	int tea_start=ex_info.indexOf("}",ex_base);
	                        	int ta_start=ex_info.indexOf("<br>",tea_start);
	                        	int ta_end=ex_info.indexOf("<br>",ta_start+1);
	                        	String teacher=ex_info.substring(ta_start+4,ta_end);
	                        	timeTable.setTeacher(teacher);
	                        	if(info.indexOf("{",fen_start)!=-1){
	                        		base=info.indexOf("{",fen_start);
	                        		ex_base=ta_end;
	                        		int add_end=ex_info.indexOf("<br>",ta_end+teacher.length());
	                        		String address=ex_info.substring(ta_end+4,add_end);
	                        		int tiao_start=ex_info.indexOf("<font color='red'>",add_end);
	                        		if(tiao_start!=-1&&tiao_start-add_end<"<font color='red'>".length()+15){
	                        			int tiao_end=ex_info.indexOf("</font>",tiao_start);
	                        			String tiaoStr=ex_info.substring(tiao_start+"<font color='red'>".length(),tiao_end);
	                        			address=address+tiaoStr;
	                        		}
	                        		address=address.replaceAll("<br>", "");
	                            	timeTable.setAddress(address);
	                            	baseStart=info.indexOf(address,fen_start)+address.length();
	                           
	                            	
	                            	String cycle=timeTable.getCycle();
	                            	String[] cycle_arr=cycle.split("-");
	                            	if(cycle_arr[0].equals(cycle_arr[1])){
	                            		int n=Integer.parseInt(cycle_arr[0]);
	                            		if(n>cyc_max){
	                            			cyc_max=n;
	                            		}
	                            		if(n<cyc_min){
	                            			cyc_min=n;
	                            		}
	                            		isDouble=true;
	                            	}else{
	                            		if(timeTable.getTime()!=null&&timeTable.getWeek()!=null){
	                        				list.add(timeTable);
	                        			}
	                            	}
	                        	}else{
	                        		int add_end=0;
	                        		if(ex_info.indexOf("<br>",ta_end+4)!=-1){
	                        			add_end=ex_info.indexOf("<br>",ta_end+4);
	                        		}
	                        		else{
	                        			add_end=ex_info.indexOf("</td>",ta_end+4);
	                        		}
	                        		String address=ex_info.substring(ta_end+4,add_end);
	                        		int tiao_start=ex_info.indexOf("<font color='red'>",add_end);
	                        		if(tiao_start!=-1){
	                        			int tiao_end=ex_info.indexOf("</font>",tiao_start);
	                        			String tiaoStr=ex_info.substring(tiao_start+"<font color='red'>".length(),tiao_end);
	                        			address=address+tiaoStr;
	                        		}
	                        		
	                            	timeTable.setAddress(address);
	                        		if(!isDouble){
	                        			if(timeTable.getTime()!=null&&timeTable.getWeek()!=null){
	                        				timeTable.setAddress(timeTable.getAddress().replaceAll("<br>", ""));
	                        				list.add(timeTable);
	                        			}
	                        		}
	                            	flag=false;
	                        	}
	                        }//end for if
	                        else{
	                        	flag=false;
	                        }
	                        }//end for while
                        
                        if(isDouble){
                        	timeTable.setCycle(cyc_min+"-"+cyc_max);
                        	if(timeTable.getTime()!=null&&timeTable.getWeek()!=null){
                				list.add(timeTable);
                			}
                        }
                    }// end for k  
                }// end for j  
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
				 boolean teacher=table1.getTeacher().equalsIgnoreCase(table2.getTeacher());
				 if(week&&time&&address&&teacher){
					 return "cycle";
				 }
				 else if(week&&address&&teacher){
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
