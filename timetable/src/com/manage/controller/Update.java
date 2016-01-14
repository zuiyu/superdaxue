package com.manage.controller;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.http.impl.conn.ConnectionShutdownException;

import com.sun.org.apache.bcel.internal.generic.ISTORE;
import com.superDaxue.controller.SchoolController;
import com.superDaxue.model.User;
import com.superDaxue.sql.UserSql;

public class Update {
	private String school;
	public Update(String school){
		this.school=school;
	}
		
	public boolean updateManage() {
		UserSql userSql=new UserSql(school);
		List<User> list=userSql.getUserList();
		//SchoolController controller=new SchoolController(school);
		System.out.println(list.size());
	/*	for (User user:list) {
			System.out.println(user.getId());
			JSONObject jsonObject=controller.login(user.getUsername(), user.getPassword(),null);
			if(jsonObject.get("result")!=null&&!"null".equals(jsonObject.get("result").toString())){
				controller.crawler(user.getUsername(), user.getPassword(), user.getId(),null);
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}  
		}
		return true; */
		if(list.size()<10){
			User[] arr=list.toArray(new User[list.size()]);
			UpdateThread thread=new UpdateThread(arr,school);
			thread.start();
			while(true) {
				if(!thread.isAlive()){
					break;
				}
				 try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}  
			}
			return true;
			
		}
		else{
			int threadLen=10;
			UpdateThread[] threads=new UpdateThread[threadLen];
			for (int i = 0; i < threadLen; i++) {
				UpdateThread updateThread=new UpdateThread(subList(list, i,threadLen),school);
				threads[i]=updateThread;
				updateThread.start();
			}
			for (UpdateThread thread:threads) {
				while(true) {
					if(!thread.isAlive()){
						break;
					}
					 try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}  
				}
			}
			return true;
		}
		
	}
	
	public User[] subList(List<User> list,int n,int threadLen){
		int size=list.size();
		int listLen=size/threadLen;
		int start=0;
		int end=0;
		if(n==threadLen-1){
			start=n*listLen;
			end=size;
		}else{
			start=n*listLen;
			end=(n+1)*listLen;
		}
		User[] arrUsers=new User[end-start];
		int j=0;
		for (int i = start; i < end; i++) {
			arrUsers[j]=list.get(i);
			j++;
		}
		return arrUsers;
	}             
	
	public static void main(String[] args) {
		Update aUpdate=new Update("henu");
		aUpdate.updateManage();
		
	}
	
}
