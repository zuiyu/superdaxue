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

public class HenuParse implements IParse{
/**
 * 老青果，从正选结果分课表
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
       NodeFilter tagfilter = new NodeClassFilter(TableTag.class); 
       NodeFilter idFilter=new HasAttributeFilter("id","reportArea");
       NodeFilter filter=new AndFilter(tagfilter, idFilter);
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
               TableRow[] rows = tag.getRows(); 
               for (int j = 0; j < rows.length; j++) {  
                   TableRow row = (TableRow) rows[j];  
                   TableColumn[] columns = row.getColumns();  
                   boolean isCourse=false;
                   TimeTable timeTable=null;
                  
                   for (int k = 0; k < columns.length; k++) {  
                   	Node columnNode=columns[k];
                       String info = columnNode.toPlainTextString().trim(); 
                    //   System.out.println(info+"=="+k);
                       if(k==1&&info.indexOf("[")!=-1){
                       	timeTable=new TimeTable();
               			String courseCode=info.substring(1,9);
               			String coursesname=info.substring(10);
               			timeTable.setCourseName(coursesname);
               			timeTable.setCourseCode(courseCode);
               			isCourse=true;
               		} 
                       if(k==2&&isCourse){
                       	double credit=Double.parseDouble(info);
                       	timeTable.setCredit(credit);
                       }
                       if(k==3&&isCourse){
                       	timeTable.setType(info);
                       }
                       if(k==4&&isCourse){
                       	timeTable.setTeacher(info);
                       }
                       if(k==5&&isCourse){
                       	timeTable.setClassId(info);
                       }
                       if(k==6&&isCourse){
                       	timeTable.setClassNum(info);
                       }
                       if(k==11&&isCourse){
                       	List<TimeAndAdress> ta_list=praseStr(info);
                       	for(TimeAndAdress ta:ta_list){
                       		timeTable.setAddress(ta.getAddress());
                       		timeTable.setTime(ta.getTime());
                       		timeTable.setCycle(ta.getCycle());
                       		timeTable.setSingleDouble(ta.getSingleDouble());
                       		timeTable.setWeek(ta.getWeek());
                       		list.add(timeTable.clone());
                       	}
                       }
                   }// end for k  
               }// end for j 
           }  
       }
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
			//i--;
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
			address=info.substring(i);
		}
		if(time.endsWith("7")&&address.startsWith("号")){
			time=time.substring(0,time.length()-1);
			address="7"+address;
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