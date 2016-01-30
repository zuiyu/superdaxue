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
import com.superDaxue.model.TimeTable;
import com.superDaxue.parse.IParse;

public class HneuParse implements IParse{
/**
 * 强智
 */
	public List<Courses> parseCourses(String html) {
		html=html.replace("&nbsp;", "");
		 Parser parser = new Parser();  
	        try {
				parser.setInputHTML(html);
				parser.setEncoding("utf-8");  
			} catch (ParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
	        NodeFilter filter = new NodeClassFilter(TableTag.class);  
	        NodeFilter attrFilter=new HasAttributeFilter( "id", "xxxxxx" );
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
	       // int i=3;
	            if(nodeList.elementAt(i) instanceof TableTag){  
	                TableTag tag = (TableTag) nodeList.elementAt(i);
	                TableRow[] rows = tag.getRows();  
	                Courses courses=null;
	                boolean flag=false;
	                for (int j = 0; j < rows.length; j++) {  
	                    TableRow row = (TableRow) rows[j];  
	                    if(row.toPlainTextString().indexOf("学期")!=-1){
	                    	flag=true;
	                    	continue;
	                    }
	                    if(flag){
		                    TableColumn[] columns = row.getColumns(); 
		                    courses=new Courses();
		                    for (int k = 0; k < columns.length; k++) {  
		                    	Node columnNode=columns[k];
		                        String info = columnNode.toPlainTextString().trim(); 
		                        if(k==1){
		                        	String[] arr= info.split("-");
		                        	if(arr.length==3){
			                        	courses.setSchoolYear(arr[0]+"-"+arr[1]);
			                        	courses.setSemester(arr[2]);
		                        	}
		                        }
		                        else if(k==2){
		                        	courses.setCourseCode(info);
		                        }
		                        else if(k==3){
		                        	courses.setCoursesname(info);
		                        }
		                        else if(k==4){
		                        	courses.setScore(info);
		                        }
		                        else if(k==5){
		                        	courses.setCredit(Double.parseDouble(info));
		                        }
		                        else if(k==8){
		                        	courses.setType(info);
		                        }
		                        else if(k==9){
		                        	courses.setGetType(info);
		                        }
		                        else if(k==10){
		                        	courses.setCheckType(info);
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
         
       List<TimeTable> list=new ArrayList<TimeTable>();
        NodeFilter filter = new NodeClassFilter(TableTag.class); 
        NodeList nodeList=null;
		try {
			nodeList = parser.extractAllNodesThatMatch(filter);
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
                        String info = columnNode.toHtml();
                        info=info.replace("\n", "");
                        if(info.indexOf("<a href=")==-1){
                        	continue;
                        }
                        timeTable=new TimeTable();
                        String[] strarr={"课程编码：","课程名称：","授课教师：","开课时间：","上课周次：","开课地点：","上课班级：","'>"};
                        int code_start=info.indexOf(strarr[0]);
                        if(code_start!=-1){
	                        int name_start=info.indexOf(strarr[1],code_start);
	                        int teather_start=info.indexOf(strarr[2],name_start);
	                        int time_start=info.indexOf(strarr[3],teather_start);
	                        int cycle_start=info.indexOf(strarr[4],time_start);
	                        int add_start=info.indexOf(strarr[5],cycle_start);
	                        int class_start=info.indexOf(strarr[6],add_start);
	                        int tag_end=info.indexOf(strarr[7],class_start);
	                        String code=info.substring(code_start+5,name_start);
	                        timeTable.setCourseCode(code);
	                        String name=info.substring(name_start+5,teather_start);
	                        timeTable.setCourseName(name);
	                        String teacher=info.substring(teather_start+5,time_start);
	                        timeTable.setTeacher(teacher);
	                        String timeAll=info.substring(time_start+5,cycle_start);
	                        if(timeAll.length()>=5){
	                        	timeTable.setWeek(timeAll.substring(0,1));
	                        	int t_start=Integer.parseInt(timeAll.substring(1,3));
	                        	int t_end=Integer.parseInt(timeAll.substring(3,5));
	                        	timeTable.setTime(t_start+"-"+t_end);
	                        }
	                        String cycle=info.substring(cycle_start+5,add_start);
	                        timeTable.setCycle(cycle);
	                        String add=info.substring(add_start+5,class_start);
	                        timeTable.setAddress(add);
	                        String classNum=info.substring(class_start+5,tag_end);
	                        timeTable.setClassNum(classNum);
	                        list.add(timeTable);
                        }
                    }
                }
            }
        }
        return list;
	}
}
