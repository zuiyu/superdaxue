package com.superDaxue.controller;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import net.sf.json.JSONObject;

import com.superDaxue.model.Courses;
import com.superDaxue.model.TimeTable;
import com.superDaxue.parse.IParse;
import com.superDaxue.parse.impl.YpcParse;
import com.superDaxue.school.ISchool;
import com.superDaxue.sql.CourseScoreSql;
import com.superDaxue.sql.CoursesTimetableSql;
import com.superDaxue.tool.DateTool;
import com.superDaxue.tool.XmlManage;

public class SchoolController {
	ISchool iSchool=null;
	IParse parse=null;
	CourseScoreSql scoreSql=null;
	CoursesTimetableSql timetableSql=null;
	private boolean isFinsh=false;
	private boolean isCheck=false;
	private String imgsavePath;
	
	public SchoolController(String school){
		
		XmlManage xmlManage=new XmlManage();
		String parseName=xmlManage.getParse(school);
		if(parseName!=null){
			try {
				parse=(IParse)Class.forName(parseName).newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			parse=new YpcParse();
		}
		String choolName=school.substring(0,1).toUpperCase()+school.substring(1);
		String className="com.superDaxue.school.impl."+choolName;
		try {
			iSchool=(ISchool)Class.forName(className).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		/*parse=new HenuParse();
		iSchool=new Henu();*/
		scoreSql=new CourseScoreSql(school);
		timetableSql=new CoursesTimetableSql(school);
	}
	public String CheckNumUrl(String savePath){
		String str=iSchool.getCheckNum(savePath);
		if(str!=null){
			setCheck(true);
			imgsavePath=savePath+"checkNum" + str + ".jpg";
		}
		return str;
	}
	
	private void delFile(String path) {
		File file = new File(path);
		if (file.exists()) {
			if (!file.isDirectory()) {
				file.delete();
			}
		}
	}
	
	//若是无验证码情况，传验证码参数为null
	public JSONObject crawler(String username,String password,int userid,String checkNum) {
		if(imgsavePath!=null){
			delFile(imgsavePath);
		}
		JSONObject jsonObject=new JSONObject();
			String scoreHtml=iSchool.getScore();
			if(scoreHtml==null){
				jsonObject.put("message", "成绩获取失败");
				return jsonObject;
			}
			List<Courses> courses_list = parse.parseCourses(scoreHtml);
			System.out.println("开始导入成绩");
			for(Courses item:courses_list){
				//System.out.println(item);
 				if(scoreSql.searchBycourseCode(item,userid)){
					continue;
				}
				scoreSql.insert(item, userid);
			}
			System.out.println("结束");
			String timetableHtml=iSchool.getTimetable();
			if(timetableHtml==null){
				jsonObject.put("message", "课表获取失败");
				return jsonObject;
			}
			List<TimeTable> timeTable_list= parse.parseTimeTables(timetableHtml);
			System.out.println("开始导入课表");
			DateTool dateTool=new DateTool();
			for(TimeTable item:timeTable_list){
				//System.out.println(item);
				item=dateTool.setSchool(item);
				if(timetableSql.searchByAll(item, userid)){
					continue;
				}
				timetableSql.insert(item, userid);
			}
			System.out.println("结束");
			jsonObject.put("isSuccess", 1);
       System.out.println(jsonObject);
       this.setFinsh(true);
       return jsonObject;
	}
	
	
	//若是无验证码情况，传验证码参数为null
	public JSONObject login(String username,String password,String checkNum){
		JSONObject jsonObject=iSchool.doLogin(username, password,checkNum);
		System.out.println(jsonObject);
		return jsonObject;
	}
	public boolean isFinsh() {
		return isFinsh;
	}
	public void setFinsh(boolean isFinsh) {
		this.isFinsh = isFinsh;
	}
	public boolean isCheck() {
		return isCheck;
	}
	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}
}
