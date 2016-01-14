package com.superDaxue.servlet;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.superDaxue.model.Courses;
import com.superDaxue.sql.CourseScoreSql;
import com.superDaxue.tool.XmlManage;

public class ScoreServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ScoreServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String path="";
		//if(request.getSession().getAttribute("scoreJsonArray")==null){
			
			String school=(String) request.getSession().getAttribute("school");
			if(school==null||"".equals(school)){
				response.sendRedirect("../index.jsp");
				return;
			}
			int userid=0;
			try{
				userid=Integer.parseInt(request.getSession().getAttribute("userid").toString());
			}catch (Exception e) {
				response.sendRedirect("../"+school);
				return;
			}
			CourseScoreSql scoreSql=new CourseScoreSql(school);
			/*if(!scoreSql.searchByUserid(userid)){
				response.sendRedirect("../jsp/signin.jsp");
				return;
			}*/
			List<Courses> list=scoreSql.searchForInfo(userid);
			
			int minYear=Integer.MAX_VALUE;
			int maxYear=Integer.MIN_VALUE;
			for (Courses courses:list) {
				String[] arr=courses.getSchoolYear().split("-");
				int year=Integer.parseInt(arr[0]);
				if(year<minYear){
					minYear=year;
				}
				if(year>maxYear){
					maxYear=year;
				}
			}
			JSONArray jsonArray=new JSONArray();
			//List<String> schoolyearList=new ArrayList<String>();
			for (int i = 0; i < (maxYear-minYear+1); i++) {
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("grade", i+1);
				String temp=(minYear+i)+"-"+(minYear+i+1);
				jsonObject.put("schoolyear", temp);
				JSONArray course_array=new JSONArray();
				jsonObject.put("course_array", course_array);
				//schoolyearList.add();
				jsonArray.add(jsonObject);
			}
			for (Courses courses:list) {
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject json=(JSONObject) jsonArray.get(i);
					if(json.get("schoolyear").toString().equals(courses.getSchoolYear())){
						JSONObject temp_json=new JSONObject();
						temp_json.put("name", courses.getCoursesname());
						temp_json.put("credit", courses.getCredit());
						temp_json.put("semester", courses.getSemester());
						temp_json.put("score", courses.getScore());
						JSONArray temp_array=(JSONArray)json.get("course_array");
						temp_array.add(temp_json);
						json.put("course_array",temp_array);
					}
				}
			}
			if(list!=null){
				double gpa=getGPA(list);
				DecimalFormat df=new DecimalFormat("##.##");
				request.setAttribute("gpa", df.format(gpa));
				request.setAttribute("scoreJsonArray", jsonArray);
				XmlManage xmlManage=new XmlManage();
				String schoolType=xmlManage.getType(school);
				if("s".equals(schoolType)){
					path="../jsp/gradeAlone.jsp";
				}else{
					path="../jsp/grade.jsp";
				}
				
			}
		//}
		//else{
		//	path="../jsp/grade.jsp";
		//}
		RequestDispatcher dispatcher=request.getRequestDispatcher(path);
		dispatcher.forward(request, response);
	}
	
	
	private double getGPA(List<Courses> list){
		double result=0;
		double sumGrade=0;
		double sumCredit=0;
		for(Courses courses:list){
			double credit=courses.getCredit();
			String score=courses.getScore();
			sumCredit+=credit;
			double grade=50;
			try {
				grade=Double.parseDouble(score);
			} catch (Exception e) {
				if("优".equals(score)){
					grade=95;
				}
				else if("良好".equals(score)){
					grade=85;
				}
				else if("良".equals(score)){
					grade=75;
				}
				else if("中".equals(score)){
					grade=65;
				}
				else if("差".equals(score)){
					grade=60;
				}
				
			}
			if(grade>100){
				continue;
			}
			if(grade>60){
				double temp=grade/10-5;
				sumGrade+=temp*credit;
			}
		}
		if(sumCredit!=0){
			result=sumGrade/sumCredit;
		}
		return result;
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
