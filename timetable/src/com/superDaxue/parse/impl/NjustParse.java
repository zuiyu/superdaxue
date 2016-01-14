package com.superDaxue.parse.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

import com.superDaxue.model.Courses;
import com.superDaxue.model.TimeTable;
import com.superDaxue.parse.IParse;

public class NjustParse implements IParse {

	public List<Courses> parseCourses(String html) {
		 Parser parser = new Parser();  
	        try {
				parser.setInputHTML(html);
			//	System.out.println(html);
				parser.setEncoding("utf-8");  
			} catch (ParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
	        NodeFilter filter = new NodeClassFilter(TableTag.class);  
	        NodeFilter attrFilter=new HasAttributeFilter( "id", "dataList" );
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
	                for (int j = 0; j < rows.length; j++) {  
	                	TableRow row = (TableRow) rows[j];  
	                	TableColumn[] columns = row.getColumns(); 
	                	courses=new Courses();
	                    for (int k = 0; k < columns.length; k++) {  
	                    	Node columnNode=columns[k];
	                        String info = columnNode.toPlainTextString().trim(); 
	                        switch (k) {
							case 1:
								String[] arr=info.split("-");
								if(arr.length==3){
									courses.setSchoolYear(arr[0]+"-"+arr[1]);
									courses.setSemester(arr[2]);
								}
								break;
							case 2:
								courses.setCourseCode(info);
								break;
							case 3:
								courses.setCoursesname(info);
								break;
							case 4:
								courses.setScore(info);
								break;
							case 6:
								courses.setCredit(Double.parseDouble(info));
								break;
							case 8:
								courses.setGetType(info);
								break;
							case 9:
								courses.setType(info);
								break;
							default:
								break;
	                    }
	                }
	                if(courses.getCoursesname()!=null){
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
	        NodeFilter filter = new NodeClassFilter(TableTag.class);  
	        NodeFilter attrFilter=new HasAttributeFilter( "id", "dataList" );
	        NodeFilter andfFilter=new AndFilter(filter, attrFilter);
	        NodeList nodeList = null;
			try {
				nodeList = parser.extractAllNodesThatMatch(andfFilter);
			} catch (ParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        List<TimeTable> list = new ArrayList<TimeTable>();
	        for(int i = 0; i < nodeList.size(); i++){  
	            if(nodeList.elementAt(i) instanceof TableTag){  
	                TableTag tag = (TableTag) nodeList.elementAt(i);
	                TableRow[] rows = tag.getRows();  
	                TimeTable timeTable=new TimeTable();
	                for (int j = 0; j < rows.length; j++) {  
	                	TableRow row = (TableRow) rows[j];  
	                	TableColumn[] columns = row.getColumns(); 
	                	timeTable=new TimeTable();
	                	List<String> weekTime = null;
	                    for (int k = 0; k < columns.length; k++) {  
	                    	Node columnNode=columns[k];
	                        String info = columnNode.toPlainTextString().trim(); 
	                        //System.out.println(info+"====="+k);
	                        switch (k) {
							case 1:
								timeTable.setCourseCode(info);
								break;
							case 3:
								timeTable.setCourseName(info);
								break;
							case 4:
								timeTable.setTeacher(info);
								break;
							case 5:
								weekTime=new ArrayList<String>();
								info=info.replaceAll("0", "");
								String[] arrTemp=info.split("[小节)]");
								for (int l = 0; l < arrTemp.length; l++) {
									if(!"".equals(arrTemp[l])){
										weekTime.add(arrTemp[l]);
									}
								}
								break;
							case 6:
								timeTable.setCredit(Double.parseDouble(info));
								break;
							case 7:
								String[] addArr=info.split(",");
								int n=0;
								for (int m=0;m<weekTime.size();m++) {
									String item=weekTime.get(m);
									int start=item.indexOf("星期");
									int end=item.indexOf("(");
									if(start==-1||end==-1){
										continue;
									}
									timeTable.setWeek("周"+item.substring(start+2,end));
									timeTable.setTime(item.substring(end+1));
									if(m>addArr.length){
										n=addArr.length-1;
									}
									timeTable.setAddress(addArr[n]);
									n++;
									list.add(timeTable.clone());
								}
								break;
							default:
								break;
	                    }
	                }
	            }
	        }
	   }
	   return list;
	}

}
