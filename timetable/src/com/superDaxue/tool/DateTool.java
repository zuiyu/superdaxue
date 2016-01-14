package com.superDaxue.tool;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.Span;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.superDaxue.model.TimeTable;

import sun.security.action.GetBooleanAction;

public class DateTool {
	public List<String> getYearList(int yearStudent){
		List<String> yearlist = new ArrayList<String>();
		Calendar calendar=Calendar.getInstance();
		int year=calendar.get(Calendar.YEAR);
		int month=calendar.get(calendar.MONTH);
		int howtime=year-yearStudent+1;//当前年份-入学年份
		for (int i = 0; i < howtime; i++) {
			yearlist.add((yearStudent+i)+"-"+(yearStudent+i+1)+",1");
			yearlist.add((yearStudent+i)+"-"+(yearStudent+i+1)+",2");
		}
		return yearlist;
	}
	//成绩查询参数 20150,20151
	public List<String> gradeYearList(int yearStudent){
		List<String> yearlist = new ArrayList<String>();
		Calendar calendar=Calendar.getInstance();
		int year=calendar.get(Calendar.YEAR);
		int month=calendar.get(calendar.MONTH);
		int howtime=year-yearStudent+1;//当前年份-入学年份
		for (int i = 0; i < howtime; i++) {
			yearlist.add((yearStudent+i)+"0");
			yearlist.add((yearStudent+i)+"1");
		}
		return yearlist;
	}
	
	//2015-2016,一
	public String[] getThisYearSemester(){
		Calendar calendar=Calendar.getInstance();
		int year=calendar.get(Calendar.YEAR);
		int month=calendar.get(calendar.MONTH)+1;
		String schoolyear="";
		String semester="";
		if(month>8){
			schoolyear=year+"-"+(year+1);
			semester="一";
		}else if(month<3){
			schoolyear=(year-1)+"-"+year;
			semester="一";
		}else{
			schoolyear=(year-1)+"-"+year;
			semester="二";
		}
		return new String[]{schoolyear,semester};
	}
	
	public TimeTable setSchool(TimeTable timeTable){
		if(timeTable.getSchoolyear()==null&&timeTable.getSemester()==null){
			Calendar calendar=Calendar.getInstance();
			int year=calendar.get(Calendar.YEAR);
			int month=calendar.get(calendar.MONTH)+1;
			if(month>8){
				timeTable.setSchoolyear(year+"-"+(year+1));
				timeTable.setSemester("1");
			}else if(month<2){
				timeTable.setSchoolyear((year-1)+"-"+year);
				timeTable.setSemester("1");
			}else{
				timeTable.setSchoolyear((year-1)+"-"+year);
				timeTable.setSemester("2");
			}
			return timeTable;
		}
		else{
			return timeTable;
		}
	}
	
}
