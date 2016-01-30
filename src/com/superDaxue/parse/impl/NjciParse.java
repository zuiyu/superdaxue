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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.superDaxue.model.Courses;
import com.superDaxue.model.TimeTable;
import com.superDaxue.parse.IParse;

public class NjciParse implements IParse {
	
	public List<Courses> parseCourses(String html) {
		List<Courses> list = new ArrayList<Courses>();
		String startStr="\"records\": ";
		int start = html.indexOf(startStr);
		int end = html.indexOf("};",start);
		if (start != -1 && end != -1) {
			String info = html.substring(start + startStr.length(), end)
					.trim();
		//	System.out.println(info);
			JSONArray jsonArray=JSONArray.fromObject(info);
			for (int i = 0; i < jsonArray.size(); i++) {
				Courses courses=new Courses();
				JSONObject json=(JSONObject) jsonArray.get(i);
				String schoolyear=((JSONObject)json.get("XQ")).get("text").toString();
				String type=((JSONObject)json.get("KCSX")).get("text").toString();
				String name=((JSONObject)json.get("KC")).get("text").toString();
				String score=json.get("ZPCJ2").toString();
				String credit=json.get("XF").toString();
				courses.setCoursesname(name);
				courses.setType(type);
				courses.setScore(score);
				courses.setCredit(Double.parseDouble(credit));
				int index=schoolyear.indexOf("学年 第");
				courses.setSchoolYear(schoolyear.substring(0, index));
				String semester=schoolyear.substring(index+4, index+5);
				if("一".equals(semester)){
					semester="1";
				}else if("二".equals(semester)){
					semester="2";
				}
				courses.setSemester(semester);
				list.add(courses);
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
       NodeFilter idFilter=new HasAttributeFilter("class","x-table");
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
                        int week=k-2;
                        if(info.indexOf("【")==-1){
                        	continue;
                        }
              //          System.out.println(info+"======="+i+","+j+","+k);
                        timeTable=new TimeTable();
                        int name_end=info.indexOf("节/");
                        timeTable.setCourseName(info.substring(0, name_end-1));
                        int time_start=info.indexOf("[");
                        int time_end=info.indexOf("节]",time_start);
                        timeTable.setTime(info.substring(time_start+1,time_end));
                        int teacher_start=info.indexOf("【",time_end);
                        int teacher_end=info.indexOf("】",teacher_start);
                        timeTable.setTeacher(info.substring(teacher_start+1,teacher_end));
                        int add_start=info.indexOf("【",teacher_end);
                        int add_end=info.indexOf("】",add_start);
                        if(add_start!=-1&&add_end!=-1){
                        	timeTable.setAddress(info.substring(add_start+1,add_end));
                        }
                        list.add(timeTable);
                    }
				}
			
			}
		}
		return list;
	}
}
