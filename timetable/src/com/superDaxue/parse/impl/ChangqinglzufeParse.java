package com.superDaxue.parse.impl;

import java.util.ArrayList;
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
import com.superDaxue.model.TimeAndAdress;
import com.superDaxue.model.TimeTable;
import com.superDaxue.parse.IParse;

public class ChangqinglzufeParse implements IParse{
/**
 * 老青果，从课表
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
	        NodeFilter idFilter=new HasAttributeFilter("id","ID_Table");
	        NodeFilter andFilter=new AndFilter(filter, idFilter);
	        NodeList nodeList = null;
			try {
				nodeList = parser.extractAllNodesThatMatch(andFilter);
			} catch (ParserException e) {
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
	                		if (k==0&&!"".equals(info)) {
	                			int sc_end=info.indexOf("学年第");
	                			schoolyear=info.substring(0,sc_end);
	                			semester=info.substring(sc_end+3,sc_end+4);
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
	                		if(k==4&&isCourse){
	                			courses.setCheckType(info);
	                		}
	                		if(k==5&&isCourse){
	                			courses.setLeanType(info);
	                		}
	                		if(k==6&&isCourse){
	                			courses.setScore(info);
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
       NodeFilter tagfilter = new NodeClassFilter(TableTag.class); 
       NodeList nodeList=null;
		try {
			nodeList = parser.extractAllNodesThatMatch(tagfilter);
		} catch (ParserException e) {
			e.printStackTrace();
		}
		
 //      for(int i = 1; i < nodeList.size(); i++){  
		int i=1;
           if(nodeList.elementAt(i) instanceof TableTag){  
               TableTag tag = (TableTag) nodeList.elementAt(i);  
               TableRow[] rows = tag.getRows(); 
               TimeTable timeTable=new TimeTable();
               for (int j = 2; j < rows.length; j++) {  
                   TableRow row = (TableRow) rows[j];  
                   if(row.toPlainTextString().trim().indexOf("[")==-1){
                	   continue;
                   }
                   TableColumn[] columns = row.getColumns();  
                   
                   for (int k = 0; k < columns.length; k++) {  
                   		Node columnNode=columns[k];
                   		String info = columnNode.toPlainTextString().trim(); 
                   		//String ex_info=columnNode.toHtml().trim();
                   		if ("".equals(info)&&timeTable!=null) {
							continue;
						}
                   		switch (k) {
						case 1:
							int co_start=info.indexOf("[");
							int co_end=info.indexOf("]");
							String code=info.substring(co_start+1,co_end);
							String name=info.substring(co_end+1);
							timeTable.setCourseCode(code);
							timeTable.setCourseName(name);
							break;
						case 2:
							timeTable.setCredit(Double.parseDouble(info));
							break;
						case 6:
							timeTable.setType(info);
							break;
						
						case 9:
							timeTable.setTeacher(info);
							break;
						case 10:
							timeTable.setCycle(info);
							break;
						case 11:
							int week_end=info.indexOf("[");
							int time_end=info.indexOf("节]");
							String week=info.substring(0,week_end);
							String time=info.substring(week_end+1,time_end);
							if(time_end+2<info.length()){
								timeTable.setSingleDouble(info.substring(time_end+2));
							}
							if(time.indexOf("-")==-1){
								time=time+"-"+time;
							}
							timeTable.setTime(time);
							timeTable.setWeek("周"+week);
							break;
						case 12:
							timeTable.setAddress(info);
							if(timeTable.getCourseName()!=null)
							list.add(timeTable.clone());
							break;
						default:
							break;
						}
                   	
                   }// end for k  
               }// end for j 
           }  
 //      }
       return list;
}
	
	

private List<TimeAndAdress> praseStr(String info){
	List<TimeAndAdress> list=new ArrayList<TimeAndAdress>();
	int start=info.indexOf("[");
	while (start!=-1) {
		int c_end=info.indexOf("]",start);
		String cycle=info.substring(start+1,c_end);
		int sd_start=info.indexOf("(",c_end);
		String singleDouble="";
		if(sd_start!=-1&&!(sd_start-c_end>5)){
			int sd_end=info.indexOf(")",sd_start);
			singleDouble=info.substring(sd_start+1,sd_end);
		}
		int w_start=info.indexOf("周",c_end);
		String week=info.substring(w_start,w_start+2);
		int t_start=w_start+8;
		int i=t_start;
		char index=info.charAt(i);
		while (i<info.length()&&(Character.isDigit(index)||index=='-')) {
			index=info.charAt(i);
			i++;
		}
		String time="";
		if(i==info.length()){
			time=info.substring(t_start,i);
			i--;
		}else{
			time=info.substring(t_start,--i);
		}
		int ad_end=info.indexOf("[",i);
		String address="";//上课地点
		if(ad_end!=-1){
			int ad_start=i;
			address=info.substring(ad_start,ad_end);
		}
		else{
			//address=info.substring(ad_start);
		}
		start=info.indexOf("[",i);
		//System.out.println(address+","+cycle+","+week+","+time+","+singleDouble);
		TimeAndAdress timeAndAdress=new TimeAndAdress(address,cycle,week,
				time,singleDouble);
		list.add(timeAndAdress);
	}
	return list;
	}
}