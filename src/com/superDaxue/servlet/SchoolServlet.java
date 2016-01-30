package com.superDaxue.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.superDaxue.model.User;
import com.superDaxue.sql.DBSchoolName;
import com.superDaxue.sql.UserSql;
import com.superDaxue.tool.AESTool;
import com.superDaxue.tool.MD5Tool;

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

		doPost(request, response);
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
		request.getSession().removeAttribute("schoolController");
		String school=(String)request.getSession().getAttribute("school");
		DBSchoolName dbSchoolName=new DBSchoolName();
		String schoolName=dbSchoolName.searchName(school);
		request.getSession().setAttribute("schoolName", schoolName);
		String path="../jsp/signin.jsp";
		Cookie[] cookies=request.getCookies();
		if(cookies==null){
			response.sendRedirect(path);
			return;
		}
		int i = 0;
		int len=cookies.length;
		for (i=0; i < len; i++) {
			Cookie cookie=cookies[i];
			if("userCookie".equalsIgnoreCase(cookie.getName())){
				int id=Integer.parseInt(cookie.getValue());				
				User user=new UserSql(school).getUser(id);
				if(user==null){
					response.sendRedirect("../jsp/signin.jsp");
					return;
				}
				MD5Tool md5Tool=new MD5Tool();
				
				//解密用户名密码
				/*AESTool aesTool=new AESTool();
				String username=aesTool.decrypt(user.getUsername(), "zhixin");
				String password=aesTool.decrypt(user.getPassword(), "zhixin");*/
				//end
				
				String value=md5Tool.GetMD5Code(md5Tool.GetMD5Code(user.getPassword()+school)+user.getUsername());
				int j = 0;
				for (; j < cookies.length; j++) {
					if("token".equalsIgnoreCase(cookies[j].getName())){
						String token=cookies[j].getValue();
						if(value.equals(token)){
							request.getSession().setAttribute("userid", id);
							path="ScoreServlet";
							break;
						}
					}
				}
				if(j==cookies.length){
					i=cookies.length;
				}
				break;
			}
		}
		response.sendRedirect(path);
		return;
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
