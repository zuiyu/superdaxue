<%@ page language="java" import="java.util.*,net.sf.json.JSONArray,net.sf.json.JSONObject,com.superDaxue.model.Courses" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String schoolName=(String)session.getAttribute("schoolName") ;
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<head>
	<base href="<%=basePath%>">
    <meta charset="UTF-8">
    <title><%=schoolName %>·超级大学，一个就够了</title>
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <!-- 如果使用双核浏览器，强制使用webkit来进行页面渲染 -->
    <meta name="renderer" content="webkit" />
    <!-- 网站描述 -->
    <meta name="description" content="超级大学，通过为高校公众号运营提供教务、社交等功能与便携的校园服务，给大学生们提供最快捷获取校园新闻，社团活动，校园招聘信息，让大学不再是一片信息孤岛，你的大学，一个就够了" />
    <!-- 网站搜索关键词 -->
    <meta name="keywords" content="超级大学, 微信第三方, 微信, 公众, 服务, 平台,微信联盟, 大学联盟, 接口, 微信查成绩, 查课表, 课程表,广告主,流量主,学生兼职,校园二手,app,手机版,">
    <script type="text/javascript" src="js/jquery-1.11.1.min.js"></script>
    <link rel="shortcut icon" href="img/logo.ico">
    <link rel="stylesheet" href="css/grade.css">
   
    <script type="text/javascript" >
    	$(document).ready(function() {
    		$(".navbar>div").on('click', function(event) {
    			$(".selected").removeClass('selected');
    			$(this).addClass('selected');
    			$(".grades").hide().eq($(this).index()).show();
    			/* Act on the event */
    		});
    		$(".update").on("click",function(){
    			if(codeClick()){
    				$(".code").show();
    			}else{
    				$(".hover").show().find("p").text("正在更新");
    				$("form").submit();
    			}
    		});
    		$(".close").on("click",function(){
    			$(".code").hide();
    		});
    		$(".code img").on("click",function(){
    			codeClick();
    		});
    		$(".ok").on("click",function(){
    			$(".code").hide();
    			$(".hover").show().find("p").text("正在更新");
    			$("form").submit();
    		});
    	});
    	
    	function codeClick(){
    	var value = false;
    	$.ajax({
           type: "POST",
           url: "servlet/LoginServlet?method=checkNum",
           timeout: 20000,
           async:false,
           error: function(){},
         //  data: "",
           success: function(msg)
           {
           //console.trace(msg);
           //breakpoint;
           	if(msg!=="null"){
           		$(".code img").attr("src",msg);
           		value = true;
           	}
           }
         });
         return value;
    	}
    </script>
</head>

<body>
	<header>
    	<span class="school"><%=schoolName%></span>
    	<span class="supply"><a href="jsp/signin.jsp">重新绑定</a></span>
    	<h4>我的成绩单</h4>
    	<span class="gpa">平均绩点：<%=request.getAttribute("gpa")%></span>
    	<span class="update"><a href="javascript:;">更新</a></span>
    </header>
    <div class="navbar">
        <div class="selected">大一</div>
        <div>大二</div>
        <div>大三</div>
        <div>大四</div>
        <div class="five" hidden>大五</div>
    </div>
    <div class="grades">
    	<div class="term" hidden>第一学期</div>
    	<div class="table" hidden>
    		<table>
    			<thead>
    				<tr>
    					<th width="65%">课程</th>
    					<th>学分</th>
    					<th>成绩</th>
    				</tr>
    			</thead>
    			<tbody>
    			
    			</tbody>
    		</table>
    	</div>
    	<div class="term" hidden>第二学期</div>
    	<div class="table" hidden>
    		<table>
    			<thead>
    				<tr>
    					<th width="65%">课程</th>
    					<th>学分</th>
    					<th>成绩</th>
    				</tr>
    			</thead>
    			<tbody>
    				
    			</tbody>
    		</table>
    	</div>
    </div>
    <div class="grades" hidden>
    	<div class="term" hidden>第一学期</div>
    	<div class="table" hidden>
    		<table>
    			<thead>
    				<tr>
    					<th width="65%">课程</th>
    					<th>学分</th>
    					<th>成绩</th>
    				</tr>
    			</thead>
    			<tbody>
    			
    			</tbody>
    		</table>
    	</div>
    	<div class="term" hidden>第二学期</div>
    	<div class="table" hidden>
    		<table>
    			<thead>
    				<tr>
    					<th width="65%">课程</th>
    					<th>学分</th>
    					<th>成绩</th>
    				</tr>
    			</thead>
    			<tbody>
    				
    			</tbody>
    		</table>
    	</div>
    </div>
    <div class="grades" hidden>
    	<div class="term" hidden>第一学期</div>
    	<div class="table" hidden>
    		<table>
    			<thead>
    				<tr>
    					<th width="65%">课程</th>
    					<th>学分</th>
    					<th>成绩</th>
    				</tr>
    			</thead>
    			<tbody>
    			
    			</tbody>
    		</table>
    	</div>
    	<div class="term" hidden>第二学期</div>
    	<div class="table" hidden>
    		<table>
    			<thead>
    				<tr>
    					<th width="65%">课程</th>
    					<th>学分</th>
    					<th>成绩</th>
    				</tr>
    			</thead>
    			<tbody>
    			
    			</tbody>
    		</table>
    	</div>
    </div>
    <div class="grades" hidden>
    	<div class="term" hidden>第一学期</div>
    	<div class="table" hidden>
    		<table>
    			<thead>
    				<tr>
    					<th width="65%">课程</th>
    					<th>学分</th>
    					<th>成绩</th>
    				</tr>
    			</thead>
    			<tbody>
    				
    			</tbody>
    		</table>
    	</div>
    	<div class="term" hidden>第二学期</div>
    	<div class="table" hidden>
    		<table>
    			<thead>
    				<tr>
    					<th width="65%">课程</th>
    					<th>学分</th>
    					<th>成绩</th>
    				</tr>
    			</thead>
    			<tbody>
    				
    		</table>
    	</div>
    </div>
    <div class="grades" hidden>
    	<div class="term" hidden>第一学期</div>
    	<div class="table" hidden>
    		<table>
    			<thead>
    				<tr>
    					<th width="65%">课程</th>
    					<th>学分</th>
    					<th>成绩</th>
    				</tr>
    			</thead>
    			<tbody>
    			
    			</tbody>
    		</table>
    	</div>
    	<div class="term" hidden>第二学期</div>
    	<div class="table" hidden>
    		<table>
    			<thead>
    				<tr>
    					<th width="65%">课程</th>
    					<th>学分</th>
    					<th>成绩</th>
    				</tr>
    			</thead>
    			<tbody>
    				
    			</tbody>
    		</table>
    	</div>
    </div>
    <div class="code">
    	<div class="window">
    		<p>输入验证码</p>
    		<div class="in">
    			<form action="servlet/UpdateScore" method="post">
    			<div class="input">
    				<input type="text" name="code" placeholder="请输入验证码">
    			</div>
    			<img src="" width="90rem" height="40rem">
    			<div class="button close">取消</div>
    			<div class="button ok">确定</div>
    			</form>
    		</div>
    	</div>
    </div>
    <div class="error">
    	<div class="tip"><p></p></div>
    </div>
    
	<div class="hover"><div class="spinner">
	  <div class="rect1"></div>
	  <div class="rect2"></div>
	  <div class="rect3"></div>
	  <div class="rect4"></div>
	  <div class="rect5"></div>
	</div></div>
    <div class="append">
    </div>
    
   <!--  <nav>
       <div><a href="servlet/TimetableServlet"><img src="img/timetable.png"><br/>课表</a></div>
    	<div><a class="on"><img src="img/gradeon.png"><br/>成绩</a></div>
        <div><a href="javascript:;"><img src="img/emptyroom.png"><br/>空教室</a></div>
    </nav> -->
    <script type="text/javascript">
		<%
		JSONArray jsonArray=(JSONArray)request.getAttribute("scoreJsonArray");
		for (int i = 0; i < jsonArray.size(); i++){
		JSONObject jsonObject=(JSONObject)jsonArray.get(i);
			if(i>=4){
			%>
			$(".five").show();
			<%
			}
			JSONArray course_array=(JSONArray)jsonObject.get("course_array");
			for(int j=0;j<course_array.size();j++){
				JSONObject courses=(JSONObject)course_array.get(j);
				
				String str="<tr><td>"+courses.get("name")+"</td><td>"+courses.get("credit")+"</td><td>"+courses.get("score")+"</td></tr>";
				boolean first=false;
				boolean second=false;
				if("第一学期".equals(courses.get("semester").toString())||"1".equals(courses.get("semester").toString())){
					first=true;
				%>
					
					$(".grades").eq(<%=i%>).find("table").eq(0).append("<%=str%>");
				<%
				}
				else
				if("第二学期".equals(courses.get("semester").toString())||"2".equals(courses.get("semester").toString())){
						second=true;
					%>
						$(".grades").eq(<%=i%>).find("table").eq(1).append("<%=str%>");
					<%
				}
				
				if(first){
				%>
				$(".grades").eq(<%=i%>).find(".term").eq(0).show();
				$(".grades").eq(<%=i%>).find(".table").eq(0).show();
				<%
				}
				if(second){
				%>
				$(".grades").eq(<%=i%>).find(".term").eq(1).show();
				$(".grades").eq(<%=i%>).find(".table").eq(1).show();
				<%
				}
			}
		}
	 %>
	 
	 
	  <%
    	String success=request.getParameter("success");
    	if(success!=null){
    		if("1".equals(success)){
    			success="更新成功！";
    	%>
	    		$(".error").find(".tip p").text("<%=success%>");
	    		$(".error").show();//.delay(2000).hide(0);
	    		setTimeout(function(){
	    			$(".error").hide();
	    		},2000);
	    		
	    	<%
    		}
    		else if("2".equals(success)){
    			success="验证码错误，刷新失败！";
    		%>
	    		$(".error").find(".tip p").text("<%=success%>");
	    		$(".error").show();//.delay(2000).hide(0);
	    		setTimeout(function(){
	    			$(".error").hide();
	    			$(".code").show();
	    			codeClick();
	    		},2000);
	    		
	    	<%
    		}
    	
    	}
     %>
	</script>
	
</body>

</html>
