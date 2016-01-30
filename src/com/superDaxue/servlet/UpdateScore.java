package com.superDaxue.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.superDaxue.controller.SchoolController;
import com.superDaxue.model.User;
import com.superDaxue.sql.UserSql;
import com.superDaxue.tool.AESTool;

public class UpdateScore extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public UpdateScore() {
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
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		int userid=0;
		String school="";
		String code=request.getParameter("code");
		school=(String) request.getSession().getAttribute("school");
		if(school==null||"".equals(school)){
			response.sendRedirect("../index.jsp");
			return;
		}
		try{
			String id=request.getSession().getAttribute("userid").toString();
			userid=Integer.parseInt(id);
		}catch (Exception e) {
			response.sendRedirect("../"+school);
			return;
		}
		
		UserSql userSql=new UserSql(school);
		User user=userSql.getUser(userid);
		if(user==null){
			response.sendRedirect("../"+school);
			return;
		}
		//解密用户名密码
		AESTool aesTool=new AESTool();
		String username=aesTool.decrypt(user.getUsername(), "zhixin");
		String password=aesTool.decrypt(user.getPassword(), "zhixin");
		//end
		RequestDispatcher dispatcher=request.getRequestDispatcher("LoginServlet?username="+username+"&password="+password+"&checkNum="+code);
		dispatcher.forward(request, response);
		/*JSONObject jsonObject=new JSONObject();
		SchoolController controller=new SchoolController(school);
		//只限自动更新的情况。。。。要修改！！！！
		jsonObject=controller.crawler(user.getUsername(), user.getPassword(), userid,null);
	
	request.getSession().setAttribute("resultJson", jsonObject);
	String path="";
	if(jsonObject.get("isSuccess")!=null&&"1".equals(jsonObject.get("isSuccess").toString())){
		request.getSession().removeAttribute("scoreJsonArray");
		request.getSession().removeAttribute("timetableList");
		path="ScoreServlet";
	}   
	else{
		path="../jsp/signin.jsp";
	}
	RequestDispatcher dispatcher=request.getRequestDispatcher(path);
	dispatcher.forward(request, response);*/
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
