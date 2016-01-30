package com.manage.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.superDaxue.controller.SchoolController;
import com.superDaxue.model.User;
import com.superDaxue.sql.UserSql;
import com.superDaxue.tool.OCR;

public class SchoolServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public SchoolServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	
	public void checkNum(HttpServletRequest request, HttpServletResponse response,SchoolController schoolController)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String basePath = request.getRealPath(File.separator);
		String imgCode=schoolController.CheckNumUrl(basePath+"img"+File.separator+"checkNum"+File.separator);
		if(imgCode!=null){
			out.print("img/checkNum/checkNum"+imgCode+".jpg");
		}else {
			out.print("null");
		}
		out.flush();
		out.close();
		//return schoolController;
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
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String school="henu";
		UserSql userSql=new UserSql(school);
		List<User> list=userSql.getUserList();
		for (User user:list) {
			SchoolController schoolController=new SchoolController(school);
			String basePath = request.getRealPath(File.separator);
			String saveDir=basePath+"img"+File.separator+"checkNum"+File.separator;
			String imgCode=schoolController.CheckNumUrl(saveDir);
			String savePath=saveDir+"checkNum"+imgCode+".jpg";
			OCR ocr=new OCR();
			String code=ocr.getResult(savePath);
			JSONObject jsonObject=schoolController.login(user.getUsername(), user.getPassword(), code);
			if(jsonObject.get("result")!=null&&!"null".equals(jsonObject.get("result").toString())){
				schoolController.crawler(user.getUsername(), user.getPassword(), user.getId(),code);
			}
		}
		
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
	/*public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String school=request.getParameter("school");
		boolean isSuccess=new Update(school).updateManage();
		out.print(isSuccess);
		out.flush();
		out.close();
	}*/

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
