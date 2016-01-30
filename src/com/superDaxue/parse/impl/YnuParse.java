package com.superDaxue.parse.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.filters.StringFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.superDaxue.model.Courses;
import com.superDaxue.model.TimeTable;
import com.superDaxue.parse.IParse;
import com.superDaxue.tool.DateTool;

public class YnuParse implements IParse {
	
	public List<Courses> parseCourses(String html) {
		List<Courses> list = new ArrayList<Courses>();
		 Parser parser = new Parser();  
	        try {
				parser.setInputHTML(html);
				parser.setEncoding("utf-8");  
			} catch (ParserException e) {
				e.printStackTrace();
			}  
	        NodeFilter filter = new TagNameFilter("d2p1:teachclassinfo");  
	        NodeFilter filter2 = new TagNameFilter("d2p1:resultdetailmodel");  
	        NodeFilter filter3 = new TagNameFilter("d2p1:studentresultmodel");  
	        NodeList nodeList = null;
	        NodeList nodeList2=null;
	        NodeList nodeList3=null;
			try {
				nodeList = parser.extractAllNodesThatMatch(filter);
				parser.setInputHTML(html);
				nodeList2=parser.extractAllNodesThatMatch(filter2);
				parser.setInputHTML(html);
				nodeList3=parser.extractAllNodesThatMatch(filter3);
			} catch (ParserException e) {
				e.printStackTrace();
			}
			for (int i = 0; i < nodeList.size(); i++) {
				//课程名部分
				Courses courses=new Courses();
				Node node=nodeList.elementAt(i);
				int start=node.elementBegin();
				int end=0;
				if(i==nodeList.size()-1){
					end=html.indexOf("</TeachClassList>");
				}else{
					Node node2=nodeList.elementAt(i+1);
					end=node2.elementBegin();
				}
				String info=html.substring(start,end);
				String name=matcherTag("d2p1:CourseName",info);
				courses.setCoursesname(name);
				String type=matcherTag("d2p1:CourseNature",info);
				courses.setType(type);
				//学分部分
				node=nodeList2.elementAt(i);
				start=node.elementBegin();
				if(i==nodeList2.size()-1){
					end=html.indexOf("</ResultDetailList>");
				}else{
					Node node2=nodeList2.elementAt(i+1);
					end=node2.elementBegin();
				}
				info=html.substring(start,end);
				String schoolyearStr=matcherTag("d2p1:SemesterId",info);
				String schoolyear=schoolyearStr.substring(0,4);
				String semester=schoolyearStr.substring(4);
				courses.setSchoolYear(schoolyear+"-"+(Integer.parseInt(schoolyear)+1));
				courses.setSemester(semester);
				String credit=matcherTag("d2p1:Credit", info);
				courses.setCredit(Double.parseDouble(credit));
				//成绩部分
				node=nodeList3.elementAt(i);
				start=node.elementBegin();
				if(i==nodeList3.size()-1){
					end=html.indexOf("</ResultList>");
				}else{
					Node node2=nodeList3.elementAt(i+1);
					end=node2.elementBegin();
				}
				info=html.substring(start,end);
				String score=matcherTag("d2p1:Result",info);
				courses.setScore(score);
				list.add(courses);
			}
		return list;
	}

	private String matcherTag(String tagname,String info){
		String result="";
		Pattern pattern = Pattern.compile("(\\<"+tagname+"\\>).*(\\</"+tagname+"\\>)");
        Matcher matcher = pattern.matcher(info);
        if(matcher.find()){
            result = matcher.group();
            result=result.replace("<"+tagname+">", "");
            result=result.replace("</"+tagname+">", "");
        }
        return result;
	}
	
	

	public List<TimeTable> parseTimeTables(String html) {
		List<TimeTable> list = new ArrayList<TimeTable>();
		 Parser parser = new Parser();  
	        try {
				parser.setInputHTML(html);
				parser.setEncoding("utf-8");  
			} catch (ParserException e) {
				e.printStackTrace();
			}  
	        NodeFilter filter = new TagNameFilter("d2p1:curriculumdetailinfo");
	        NodeList nodeList = null;
			try {
				nodeList = parser.extractAllNodesThatMatch(filter);
			} catch (ParserException e) {
				e.printStackTrace();
			}
			String[] school=new DateTool().getThisYearSemesterQing();
			String schoolYear=school[0]+(Integer.parseInt(school[1])+1);
			for (int i = 0; i < nodeList.size(); i++) {
				//课程名部分
				Node node=nodeList.elementAt(i);
				int start=node.elementBegin();
				int end=0;
				if(i==nodeList.size()-1){
					end=html.indexOf("</StudentCurriculumDetailInfos>");
				}else{
					Node node2=nodeList.elementAt(i+1);
					end=node2.elementBegin();
				}
				String info=html.substring(start,end);
				
				String str=matcherTag("d2p1:SemesterId",info);
				if(schoolYear.equalsIgnoreCase(str)){
					TimeTable timeTable=new TimeTable();
					String name=matcherTag("d2p1:TeachClassName", info);
					timeTable.setCourseName(name);
					String teacher=matcherTag("d2p1:TeacherOneName", info);
					timeTable.setTeacher(teacher);
					String addressAll=matcherTag("d2p1:Address", info);
					String[] week=new String[7];
					week[0]=matcherTag("d2p1:Mon", info);
					week[1]=matcherTag("d2p1:Tue", info);
					week[2]=matcherTag("d2p1:Wed", info);
					week[3]=matcherTag("d2p1:Thu", info);
					week[4]=matcherTag("d2p1:Fri", info);
					week[5]=matcherTag("d2p1:Sat", info);
					week[6]=matcherTag("d2p1:Sun", info);
					String weekStr="";
					String time="";
					String[] addressArr= addressAll.split(",");
					int len=0;
					for (int j = 0; j < week.length; j++) {
						if(week[j].length()>0){
							weekStr=(j+1)+"";
							String item=week[j];
							if(item.indexOf("单")!=-1){
								timeTable.setSingleDouble("单");
							}
							if(item.indexOf("双")!=-1){
								timeTable.setSingleDouble("双");
							}
							item=item.replaceAll("[前 后 单 双]", "");
							if(!Character.isDigit(item.charAt(0))||!Character.isDigit(item.charAt(item.length()-1))){
								continue;
							}
							time=item.charAt(0)+"-"+item.charAt(item.length()-1);
							timeTable.setWeek(weekStr);
							timeTable.setTime(time);
							if(len>=addressArr.length){
								len=addressArr.length-1;
							}
							timeTable.setAddress(addressArr[len]);
							list.add(timeTable.clone());
							len++;
						}
					}
				}
			
			}
		return list;
	}
}
