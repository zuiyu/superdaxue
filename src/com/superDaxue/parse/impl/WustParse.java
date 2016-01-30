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

public class WustParse implements IParse{
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
        NodeFilter idTable=new HasAttributeFilter("id","mxh");
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
				for (int j = 1; j < rows.length; j++) {
					TableRow row = (TableRow) rows[j];
					TableColumn[] columns = row.getColumns();
					courses = new Courses();
					for (int k = 0; k < columns.length; k++) {
						Node columnNode = columns[k];
						String info = columnNode.toPlainTextString().trim();
						switch (k) {
						case 3:
							String[] arrStr=info.split("-");
							if(arrStr.length==3){
								courses.setSchoolYear(arrStr[0]+"-"+arrStr[1]);
								courses.setSemester(arrStr[2]);
							}
							break;
						case 4:
							courses.setCoursesname(info);
							break;
						case 5:
							courses.setScore(info);
							break;
						case 8:
							courses.setType(info);
							break;
						case 10:
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
        NodeFilter idFilter=new HasAttributeFilter("id","kbtable");
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
                        String ex_info = columnNode.toHtml().trim(); 
                        info=info.replaceAll("[&nbsp; \t]", "");
                        if("".equals(info)||"\r\n".equals(info)){
                        	continue;
                        }
                        String[] infoArr=info.split("\r\n");
                        if(infoArr.length==2){
                        	timeTable=new TimeTable();
                        	int time=j;
                            int week=k;
                            ex_info=ex_info.replaceAll("[&nbsp; \t]", "");
                        	String[] nameArr=infoArr[0].split(",");
                        	int baseIndex=0;
                        	for (int l = 0; l < nameArr.length; l++) {
                        		timeTable.setCourseName(nameArr[l]);
                            	timeTable.setWeek(week+"");
                            	timeTable.setTime((time*2-1)+"-"+(time*2));
                            	int add_start=ex_info.indexOf("<or><r>",baseIndex);
                            	int add_end=0;
                            	String fenString="";
                            	if (l==nameArr.length) {
                            		fenString="<r></div>";
								}
                            	else {
									fenString="<r>";
								}
                            	add_end=ex_info.indexOf(fenString,add_start+7);
                            	String address=ex_info.substring(add_start+7,add_end);
                            	timeTable.setAddress(address);
                            	list.add(timeTable.clone());
                            	baseIndex=add_end+fenString.length();
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
