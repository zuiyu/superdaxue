
<%@page import="com.sun.corba.se.impl.protocol.giopmsgheaders.Message"%>
<%@ page language="java" import="java.util.*,net.sf.json.JSONObject,com.superDaxue.school.impl.*,com.superDaxue.parse.IParse" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String schoolName=(String)session.getAttribute("schoolName");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<base href="<%=basePath%>">
    <meta charset="UTF-8">
    <title><%=schoolName %>·超级大学，一个就够了</title>
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <link rel="shortcut icon" href="img/logo.ico">
    <!-- 如果使用双核浏览器，强制使用webkit来进行页面渲染 -->
    <meta name="renderer" content="webkit" />
    <!-- 网站描述 -->
    <meta name="description" content="超级大学，通过为高校公众号运营提供教务、社交等功能与便携的校园服务，给大学生们提供最快捷获取校园新闻，社团活动，校园招聘信息，让大学不再是一片信息孤岛，你的大学，一个就够了" />
    <!-- 网站搜索关键词 -->
    <meta name="keywords" content="超级大学, 微信第三方, 微信, 公众, 服务, 平台,微信联盟, 大学联盟, 接口, 微信查成绩, 查课表, 课程表,广告主,流量主,学生兼职,校园二手,app,手机版,">
    <style type="text/css">
    * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
    }
    
    html:root {
        font-family: "SimHei" arial helvetica;
    }
    
    html{
   	max-width: 480px;
   	margin:0 auto;
   }
    
    h1 {
        text-align: center;
        line-height: 10rem;
        font-weight: 400;
        color: #878787;
    }
    
    .input {
        width: 86%;
        height: 1rem;
        margin: 2rem auto 0;
        border-bottom: 1px solid #b3b3b4;
        padding: 2px 0;
        height: 2rem;
        border-radius: 0.5rem;
        position: relative;
        font-size:1.5rem;
    }
    
    .input img {
        height: 1.6rem;
        margin: 0 0.7rem;
        padding-bottom:.3rem;
        vertical-align: bottom;
    }
    
    .input input {
        border: none;
        outline: none;
        line-height: 1.2rem;
        width: 75%;
        margin-bottom: 0.2rem;
        font-size: 1.1rem;
    }
    
    .input select {
        width: 80%;
        border: none;
        outline: none;
    }
    
    .input input:last-of-type{
    	width:70%;
    }
    .code{
    	position:absolute;
    	right:0;
    }

    .button{
    	width: 88%;
    	margin: 3.5rem auto 0;
    }
	
	.button button{
		width: 100%;
		border: none;
		color: #fff;
		line-height: 3rem;
		background: #3681FD;
		border-radius: 0.3rem;
		font-size: 1.1rem;
	}
	
	.tip{
		color:#f00;
		display: block;
		text-align:center;
		font-size: 1.5rem;
	}
	.spinner {
  margin: 100px auto;
  width: 60px;
  height: 60px;
  text-align: center;
  font-size: 10px;
  display: none;
}
 
.spinner > div {
  background-color: #67CF22;
  height: 100%;
  width: 6px;
  display: inline-block;
   
  -webkit-animation: stretchdelay 1.2s infinite ease-in-out;
  animation: stretchdelay 1.2s infinite ease-in-out;
}
 
.spinner .rect2 {
  -webkit-animation-delay: -1.1s;
  animation-delay: -1.1s;
}
 
.spinner .rect3 {
  -webkit-animation-delay: -1.0s;
  animation-delay: -1.0s;
}
 
.spinner .rect4 {
  -webkit-animation-delay: -0.9s;
  animation-delay: -0.9s;
}
 
.spinner .rect5 {
  -webkit-animation-delay: -0.8s;
  animation-delay: -0.8s;
}
 
@-webkit-keyframes stretchdelay {
  0%, 40%, 100% { -webkit-transform: scaleY(0.4) } 
  20% { -webkit-transform: scaleY(1.0) }
}
 
@keyframes stretchdelay {
  0%, 40%, 100% {
    transform: scaleY(0.4);
    -webkit-transform: scaleY(0.4);
  }  20% {
    transform: scaleY(1.0);
    -webkit-transform: scaleY(1.0);
  }
}
    </style>
</head>

<body>
<!-- <br><a href="servlet/ScoreServlet" style="margin-left:30px;size:20px;">返回</a> -->
<%
     	JSONObject jsonObject=(JSONObject)request.getAttribute("resultJson");
    	String message="";
    	if(jsonObject!=null){
    		message=(String)jsonObject.get("message");
    		if(message==null){
    			message="";
    		}
    	} 
    	request.removeAttribute("resultJson");
		
     %>
    <h1>请输入信息</h1><span class="tip"><%=message %></span>
    
    
  	
    <form action="servlet/LoginServlet" method="post" id="loginForm">
        <div class="spinner">
	  <div class="rect1"></div>
	  <div class="rect2"></div>
	  <div class="rect3"></div>
	  <div class="rect4"></div>
	  <div class="rect5"></div>
	</div>
	    <div class="input"><img src="img/ac.png" alt="account">
	        <input type="text" placeholder="请输入教务系统账号" name="username">
	    </div>
	    <div class="input"><img src="img/pw.png" alt="password">
	        <input type="password" placeholder="请输入教务密码"  name="password">
	    </div>
	    <div class="input" hidden><img src="img/code.png" alt="checkNum">
	        <input type="text" placeholder="请输入验证码"  name="checkNum">
	        <img class="code" width="90rem" height="40rem"/>
	    </div>
	    
	    <div class="button">
	    	<button>绑定</button>
	    </div>
    </form>
    <script type="text/javascript" src="js/jquery.min.js"></script>
    <script type="text/javascript">
    $(document).ready(function(){
    	<%
    	if(session.getAttribute("school")==null){
    	%>
    	window.location="index.jsp";
    	<%
    	}
    	%>
    	
    	$(".button button").on("click",function(){
    		i = 0;
	    	$("input").each(function(){
	    		if(!$(this).is(":hidden")&&$(this).val()==""){
	    			$(this).focus();
	    			$(".tip").text("请将信息填写完整！").show().delay(2000).hide(0);
	    			i++;
	    			return false;
	    		}
	    	});
	    	if(i==0){
	    		console.log(1);
	    		$(".input").hide();
	    		$(".spinner").show();
	    		$("button").hide();
	    		$(".tip").hide();
	    		$("#loginForm").submit();
	    	}else{
	    		return false;
	    	}
    	});
    	
    	function codeClick(){
    	$.ajax({
           type: "POST",
           url: "servlet/LoginServlet?method=checkNum",
           timeout: 20000,
           error: function(){},
         //  data: "",
           success: function(msg)
           {
           //console.trace(msg);
           //breakpoint;
           	if(msg!=="null"){
           		$(".code").attr("src",msg);
           	}
           }
         });
    	}
    	
    	 $.ajax({
           type: "POST",
           url: "servlet/LoginServlet?method=checkNum",
           timeout: 20000,
           error: function(){},
         //  data: "",
           success: function(msg)
           {
           //console.trace(msg);
           //breakpoint;
           	if(msg!=="null"){
           		$(".input").show().last().find(".code").attr("src",msg).on("click",function(){
           		codeClick();
           		});
           	}
           }
         });
    });
    </script>
</body>

</html>

