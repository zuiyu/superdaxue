package com.superDaxue.parse.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.print.attribute.HashAttributeSet;

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

import com.sun.net.httpserver.Filter;
import com.superDaxue.model.Courses;
import com.superDaxue.model.TimeAndAdress;
import com.superDaxue.model.TimeTable;
import com.superDaxue.parse.IParse;

public class HenuParseEXP implements IParse{
/**
 * 从课表处，分课表
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
	        NodeList nodeList = null;
			try {
				nodeList = parser.extractAllNodesThatMatch(filter);
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
	                        String temp="学年学期：";
	                        int start = info.indexOf(temp);
	                        int len="2012-2013".length();
	                		if (start != -1) {
	                			start=start + temp.length();
	                			schoolyear = info.substring(start,start+len);
	                			//semester = info.substring(start+len+2);
	                			//网络正常时候测试学期改为数字
	                			semester = info.substring(start+len+3,start+len+4);
	                			if("一".equals(semester)){
	                				semester="1";
	                			}
	                			else if("二".equals(semester)){
	                				semester="2";
	                			}
	                		}
	                		if(k==1&&info.indexOf("[")!=-1){
	                			courses=new Courses();
	                			String courseCode=info.substring(1,9);
	                			String coursesname=info.substring(10);
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
	                			courses.setLeanType(info);
	                		}
	                		if(k==5&&isCourse){
	                			courses.setCheckType(info);
	                		}
	                		if(k==6&&isCourse){
	                			courses.setGetType(info);
	                		}
	                		if(k==7&&isCourse){
	                		//	double score=Double.parseDouble(info);
	                			courses.setScore(info);
	                		}
	                		if(k==8&&isCourse){
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        
      List<TimeTable> list=new ArrayList<TimeTable>();
       NodeFilter filter = new NodeClassFilter(TableTag.class); 
       NodeList nodeList=null;
		try {
			nodeList = parser.extractAllNodesThatMatch(filter);
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       for(int i = 0; i < nodeList.size(); i++){  
           if(nodeList.elementAt(i) instanceof TableTag){  
               TableTag tag = (TableTag) nodeList.elementAt(i);  
               if(tag.getText().indexOf("[课程号]")==-1){
               	continue;
               }
               TableRow[] rows = tag.getRows(); 
               for (int j = 1; j < rows.length; j++) {  
                   TableRow row = (TableRow) rows[j];  
                   TableColumn[] columns = row.getColumns();  
                   boolean isCourse=false;
                   TimeTable timeTable=null;
                   for (int k = 0; k < columns.length; k++) {  
                   	Node columnNode=columns[k];
                       String info = columnNode.toPlainTextString().trim(); 
                       System.out.println(info+"==="+k);
                       	switch (k) {
							case 1:
								int start=info.indexOf("[");
								int end=info.indexOf("]");
								timeTable=new TimeTable();
								timeTable.setCourseCode(info.substring(start+1,end));
								timeTable.setCourseName(info.substring(end+1));
								break;
							case 3:
								timeTable.setCredit(Double.parseDouble(info));
								break;
							case 4:
								timeTable.setType(info);
								break;
							case 5:
								int t_start=info.indexOf("]");
								timeTable.setTeacher(info.substring(t_start+1));
								break;
							case 8:
								List<TimeAndAdress> ta_list=praseStr(info);
	                        	for(TimeAndAdress ta:ta_list){
	                        		timeTable.setAddress(ta.getAddress());
	                        		timeTable.setTime(ta.getTime());
	                        		timeTable.setCycle(ta.getCycle());
	                        		timeTable.setSingleDouble(ta.getSingleDouble());
	                        		timeTable.setWeek(ta.getWeek());
	                        		list.add(timeTable.clone());
	                        	}
								break;
							default:
								break;
                       }
                   }
               }// end for j  
           }  
       }
       return list;
}
	
	

private List<TimeAndAdress> praseStr(String info){
	List<TimeAndAdress> list=new ArrayList<TimeAndAdress>();
	TimeAndAdress timeAndAdress=null;
	String[] arr=info.split(",");
	for (int i = 0; i < arr.length; i++) {
		String[] array=arr[i].split(" ");
		if(array.length==3){
			String str0=array[0];
			int cyc_end=str0.indexOf("周");
			String cycle=str0.substring(0,cyc_end);
			String singleDouble=null;
			if (str0.indexOf("(")!=-1) {
				int s_start=str0.indexOf("(");
				int s_end=str0.indexOf(")");
				singleDouble=str0.substring(s_start+1,s_end);
			}
			String str1=array[1];
			String week=str1.substring(0,1);
			if("一".equals(week)){
				week="1";
			}
			else if("二".equals(week)){
				week="2";
			}
			else if("三".equals(week)){
				week="3";
			}
			else if("四".equals(week)){
				week="4";
			}
			else if("五".equals(week)){
				week="5";
			}
			else if("六".equals(week)){
				week="6";
			}
			else if("日".equals(week)){
				week="7";
			}
			String time="";
			if (str1.indexOf("[")!=-1) {
				int t_start=str1.indexOf("[");
				int t_end=str1.indexOf("]");
				time=str1.substring(t_start+1,t_end);
			}
			String address=array[2];
			timeAndAdress=new TimeAndAdress(address, cycle, week, time, singleDouble);
			list.add(timeAndAdress);
		}
	}
	return list;
}

}
