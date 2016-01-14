package com.superDaxue.servlet.school;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.superDaxue.model.User;
import com.superDaxue.sql.DBSchoolName;
import com.superDaxue.sql.UserSql;
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
	//************************微信登录******************************
		/*String rand=request.getParameter("rand");
		String randtype=request.getParameter("randtype");
		String token=request.getParameter("token");
		String school=(String)request.getSession().getAttribute("school");
		if(school==null||"".equals(school)){
			response.sendRedirect("../index.jsp");
			return;
		}
    	if(token==null||"".equals(token)){
    		String url="grade.superdaxue.com/servlet/SchoolServlet?school="+school;
    		url=Base64.encode(url.getBytes());
    		String location="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx32ad82b30f035496&redirect_uri=http://www.superdaxue.com/Auth/index/refer/"+url+"&response_type=code&scope=snsapi_login&state=STATE&connect_redirect=1#wechat_redirect";
    		response.sendRedirect(location);
    	}
    	else{
    		String temp_token=MD5Tool.GetMD5Code(MD5Tool.GetMD5Code(rand+MD5Tool.GetMD5Code(randtype))+school);
    		if(token.equals(temp_token)){
    			request.getSession().setAttribute("userid",new String(Base64.decode(rand)));
				//request.getSession().setAttribute("userid","2");
    			request.getSession().setAttribute("school", school);
    			DBSchoolName dbSchoolName=new DBSchoolName();
				String schoolName=dbSchoolName.searchName(school);
				request.getSession().setAttribute("schoolName", schoolName);
    			String path="TimetableServlet";
    			response.sendRedirect(path);
    		}
    	}
    	response.setContentType("text/html");
    	response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.print("校验没通过");
		out.flush();
		out.close();*/
		//*************************结束*************************
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
				String value=md5Tool.GetMD5Code(md5Tool.GetMD5Code(user.getPassword()+school)+user.getUsername());
				int j = 0;
				for (; j < cookies.length; j++) {
					if("token".equalsIgnoreCase(cookies[j].getName())){
						String token=cookies[j].getValue();
						if(value.equals(token)){
							request.getSession().setAttribute("userid", id);
							path="TimetableServlet";
							/*RequestDispatcher dispatcher=request.getRequestDispatcher(path);
							dispatcher.forward(request, response);*/
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
