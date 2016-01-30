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

public class GdhscParse implements IParse {

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
	        NodeFilter attrFilter=new HasAttributeFilter( "id", "Datagrid1" );
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
		                       // System.out.println(info+"====="+k);
		                        if(k==0){
		                        	courses.setSchoolYear(info);
		                        }
		                        else if(k==1){
		                        	courses.setSemester(info);
		                        }
		                        else if(k==2){
		                        	courses.setCourseCode(info);
		                        }
		                        else if(k==3){
		                        	courses.setCoursesname(info);
		                        }
		                        else if(k==4){
		                        	courses.setType(info);
		                        }
		                        else if(k==6){
		                        	courses.setCredit(Double.parseDouble(info));
		                        }
		                        else if(k==7){
		                        }
		                        else if(k==8){
		                        	courses.setScore(info);
		                        }
		                        else if(k==10||k==11){
		                        	try {
		                        		info=info.replace("&nbsp;", "");
										Double score=Double.parseDouble(info);
										Double old=Double.parseDouble(courses.getScore());
										if(score>old){
											courses.setScore(score+"");
										}
									} catch (Exception e) {
										
									}
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
		 Parser parser = new Parser();  
	        try {
				parser.setInputHTML(html);
				parser.setEncoding("utf-8");  
			} catch (ParserException e) {
				e.printStackTrace();
			}  
	        NodeFilter filter = new NodeClassFilter(TableTag.class);  
	        NodeFilter attrFilter=new HasAttributeFilter( "id", "DBGrid" );
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
	                for (int j = 1; j < rows.length; j++) {  
	                	TableRow row = (TableRow) rows[j];  
	                	TableColumn[] columns = row.getColumns(); 
	                	timeTable=new TimeTable();
	                	String[] weekTime = null ;
	                    for (int k = 0; k < columns.length; k++) {  
	                    	Node columnNode=columns[k];
	                        String info = columnNode.toPlainTextString().trim(); 
	                        switch (k) {
							case 0:
								int y_start=info.indexOf("(");
								int y_end=info.indexOf(")");
								if(y_start!=-1&&y_end!=-1){
									String year=info.substring(y_start+1,y_end);
									String[] yearArr=year.split("-");
									if(yearArr.length==3){
										timeTable.setSchoolyear(yearArr[0]+"-"+yearArr[1]);
										timeTable.setSemester(yearArr[2]);
									}
								}
								break;
							case 2:
								timeTable.setCourseName(info);	
								break;
							case 3:
								timeTable.setType(info);	
								break;
							case 5:
								timeTable.setTeacher(info);	
								break;
							case 6:
								timeTable.setCredit(Double.parseDouble(info));	
								break;
							case 8:
								weekTime=info.split(";");	
								break;
							case 9:
								String[] arr=info.split(";");
								int m=0;
								for (int l = 0; l < weekTime.length; l++) {
									String item=weekTime[l];
									int w_start=item.indexOf("周");
									if(w_start!=-1){
										timeTable.setWeek(item.substring(w_start,w_start+2));
										String week=timeTable.getWeek();
										int t_start=item.indexOf(week+"第");
										int t_end=item.indexOf("节{第");
										String time=item.substring(t_start+3,t_end);
										String[] timeArr=time.split(",");
										timeTable.setTime(timeArr[0]+"-"+timeArr[timeArr.length-1]);
										int c_end=item.indexOf("周",t_end);
										timeTable.setCycle(item.substring(t_end+3,c_end));
										int d_start=item.indexOf("|",c_end);
										if(d_start!=-1){
											timeTable.setSingleDouble(item.substring(d_start+1,d_start+2));
										}
										if(m>arr.length){
											m=arr.length-1;
										}
										timeTable.setAddress(arr[m]);
										list.add(timeTable.clone());
										m++;
									}
									
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
