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

public class CustParse implements IParse{
/**
 
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
        NodeFilter tablefilter = new NodeClassFilter(TableTag.class); 
        NodeFilter idTable=new HasAttributeFilter("id","Table1");
        NodeFilter andTable=new AndFilter(tablefilter, idTable);
        
        NodeList nodeList=null;
		try {
			nodeList =parser.extractAllNodesThatMatch(andTable);
		} catch (ParserException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < nodeList.size(); i++) {
			if (nodeList.elementAt(i) instanceof TableTag) {
				TableTag tag = (TableTag) nodeList.elementAt(i);
				TableRow[] rows = tag.getRows();
				Courses courses = null;
				String schoolYear="";
				String semester="";
				for (int j = 1; j < rows.length; j++) {
					TableRow row = (TableRow) rows[j];
					TableColumn[] columns = row.getColumns();
					courses = new Courses();
					int len=columns.length;
					for (int k = 0; k < len; k++) {
						Node columnNode = columns[k];
						String info = columnNode.toPlainTextString().trim();
						int index=k;
						if(len==12){
							switch (index) {
							case 0:
								info=info.replaceAll("学年", "");
								schoolYear=info;
								break;
							case 2:
								info=info.substring(1,2);
								if("一".equals(info)){
									semester="1";
								}else if("二".equals(info)){
									semester="2";
								}
								break;
							default:
								break;
								
							}
							index=index-4;
						}
						if(len==10){
							if(index==0){
								info=info.substring(1,2);
								if("一".equals(info)){
									semester="1";
								}else if("二".equals(info)){
									semester="2";
								}
							}
							index=index-2;
						}
						courses.setSchoolYear(schoolYear);
						courses.setSemester(semester);
						switch (index) {
						case 0:
							info=info.replaceAll("(试)", "");
							courses.setCoursesname(info);
							break;
						case 1:
							courses.setType(info);
							break;
						case 2:
							courses.setCredit(Double.parseDouble(info));
							break;
						case 4:
							courses.setScore(info);
							break;
						default:
							break;
						}
					}
					if(courses.getCoursesname()!=null&&!"".equals(courses.getCoursesname().trim())){
						list.add(courses);
					}
					
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
       // NodeFilter andFilter=new AndFilter(filter, idFilter);
        NodeList nodeList=null;
		try {
			nodeList = parser.extractAllNodesThatMatch(filter);
			nodeList=nodeList.extractAllNodesThatMatch(idFilter);
		} catch (ParserException e) {
			e.printStackTrace();
		}
        for(int i = 0; i < nodeList.size(); i++){  
            if(nodeList.elementAt(i) instanceof TableTag){  
                TableTag tag = (TableTag) nodeList.elementAt(i);  
                TableRow[] rows = tag.getRows(); 
                for (int j = 1; j < rows.length; j++) {  
                    TableRow row = (TableRow) rows[j];  
                    TableColumn[] columns = row.getColumns();  
                    TimeTable timeTable=null;
                    for (int k = 0; k < columns.length; k++) {  
                    	Node columnNode=columns[k];
                        String info = columnNode.toPlainTextString().trim();
                        info=info.replaceAll("[\t \r\n &nbsp;]", "");
                        String ex_info = columnNode.toHtml().trim();
                        ex_info=ex_info.replaceAll("[\t \r\n &nbsp;]", "");
                        if(info.length()>0&&info.indexOf("节")==-1){
                        	String time=(2*j-1)+"-"+(2*j);
                            String week=k+"";
                        	timeTable=new TimeTable();
                        	timeTable.setTime(time);
                        	timeTable.setWeek(week);
                        	String nameStartStr="TARGET=\"_lak\">";
                        	String nameEndStr="</a></fot></td></tr>";
                        	String addStartStr="<td><fotize=\"2\">";
                        	String addEndStr="</fot></td></tr>";
                        	int nameStart=ex_info.indexOf(nameStartStr);
                        	int nameEnd=ex_info.indexOf(nameEndStr,nameStart);
                        	String name=ex_info.substring(nameStart+nameStartStr.length(),nameEnd);
                        	timeTable.setCourseName(name);
                        	int teaStart=ex_info.indexOf(nameStartStr,nameEnd);
                        	int teaEnd=ex_info.indexOf(nameEndStr,teaStart);
                        	String teacher=ex_info.substring(teaStart+nameStartStr.length(), teaEnd);
                        	timeTable.setTeacher(teacher);
                        	int addStart=ex_info.indexOf(addStartStr,teaEnd);
                        	int addEnd=ex_info.indexOf(addEndStr,addStart);
                        	String address=ex_info.substring(addStart+addStartStr.length(),addEnd);
                        	timeTable.setAddress(address);
                        	int cycStart=ex_info.indexOf(addStartStr,addEnd);
                        	int cycEnd=ex_info.indexOf(addEndStr,cycStart);
                        	String cycle=ex_info.substring(cycStart+addStartStr.length(), cycEnd);
                        	cycle=cycle.replaceAll("周", "");
                        	String[] ds=cycle.split(",");
                        	if(ds.length==2){
                        		timeTable.setCycle(ds[0]);
                        		timeTable.setSingleDouble(ds[1]);
                        	}else {
                        		timeTable.setCycle(cycle);
							}
                        	list.add(timeTable);
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
				 boolean teacher=true;
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
