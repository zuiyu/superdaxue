package com.manage.controller;

import java.util.List;

import net.sf.json.JSONObject;

import com.superDaxue.controller.SchoolController;
import com.superDaxue.model.User;
import com.superDaxue.parse.IParse;
import com.superDaxue.parse.impl.HenuParse;
import com.superDaxue.parse.impl.YpcParse;
import com.superDaxue.school.ISchool;
import com.superDaxue.school.impl.Henu;
import com.superDaxue.school.impl.Ypc;
import com.superDaxue.sql.CourseScoreSql;
import com.superDaxue.sql.CoursesTimetableSql;


public class UpdateThread extends Thread{
	private User[] list;
	private String school;
	public UpdateThread(User[] list,String school) {
		this.list=list;
		this.school=school;
	}
	//只自动更新无验证码的，和能破解验证码的
	@Override
	public void run() {
		SchoolController controller=new SchoolController(school);
		for (User user:list) {
			System.out.println(user.getId());
			JSONObject jsonObject=controller.login(user.getUsername(), user.getPassword(),null);
			if(jsonObject.get("result")!=null&&!"null".equals(jsonObject.get("result").toString())){
				controller.crawler(user.getUsername(), user.getPassword(), user.getId(),null);
			}
		}
		System.out.println(this.getName()+"==========完成");
	}
}
