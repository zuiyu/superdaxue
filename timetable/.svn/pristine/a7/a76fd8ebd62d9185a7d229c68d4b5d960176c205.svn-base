package com.superDaxue.servlet;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.superDaxue.model.TimeTable;
import com.superDaxue.sql.CoursesTimetableSql;
import com.superDaxue.tool.XmlManage;

public class TimetableServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public TimetableServlet() {
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
			String path="";//../jsp/timetable.jsp";
			int userid=0;
			String school="";
			school=(String) request.getSession().getAttribute("school");
			if(school==null||"".equals(school)){
				response.sendRedirect("../index.jsp");
				return;
			}
			try{
				System.out.println();
				String id=request.getSession().getAttribute("userid").toString();
				userid=Integer.parseInt(id);
			}catch (Exception e) {
				response.sendRedirect("../"+school);
				return;
			}
			
			CoursesTimetableSql timetableSql=new CoursesTimetableSql(school);
			/*if(!timetableSql.searchByUserid(userid)){
				response.sendRedirect("../jsp/signin.jsp");
				return;
			}*/
			TimeTable timeTable=new TimeTable();
			timeTable=setSchool(timeTable);
			List<TimeTable> list=timetableSql.searchForInfo(userid,timeTable.getSchoolyear(),timeTable.getSemester());
			//同一课程id一致
			for (int i=0;i<list.size();i++) {
				TimeTable table=list.get(i);
				for (int j = i+1; j < list.size(); j++) {
					TimeTable temp=list.get(j);
					if(table.getCourseName().endsWith(temp.getCourseName())){
						temp.setId(table.getId());
					}
				}
			}
			request.setAttribute("timetableList", list);
			XmlManage xmlManage=new XmlManage();
			String schoolType=xmlManage.getType(school);
			if("t".equals(schoolType)){
				path="../jsp/timetableAlone.jsp";
			}else if ("s".equals(schoolType)) {
				path="ScoreServlet";
			}else{
				path="../jsp/timetable.jsp";
			}
			
			
		RequestDispatcher dispatcher=request.getRequestDispatcher(path);
		dispatcher.forward(request, response);
	}

	private TimeTable setSchool(TimeTable timeTable){
		Calendar calendar=Calendar.getInstance();
		int year=calendar.get(Calendar.YEAR);
		int month=calendar.get(calendar.MONTH);
		if(month>8){
			timeTable.setSchoolyear(year+"-"+(year+1));
			timeTable.setSemester("1");
		}else {
			timeTable.setSchoolyear((year-1)+"-"+year);
			timeTable.setSemester("2");
		}
		return timeTable;
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
