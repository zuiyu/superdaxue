
<%@page import="com.sun.corba.se.impl.protocol.giopmsgheaders.Message"%>
<%@ page language="java" import="java.util.*,net.sf.json.JSONObject" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<base href="<%=basePath%>">
    <meta charset="UTF-8">
    <title>超级大学，一个就够了</title>
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
		
		*{
			margin:0;
			padding: 0;
		}
		html{
			max-width: 480px;
   			margin:0 auto;
		}
		img{
			width: 96%;
			margin-top: 20%;
			margin-left: 2%;
		}
		.btn{
			width: 12rem;
			padding:.6rem 0;
			border-radius: 4px;
			background-color: rgb(0,154,68);
			color: #fff;
			text-align: center;
			margin: 60px auto 0;
			font-weight: bolder;
		}
		a{
			text-decoration:none;
		}
	</style>
</head>


<body>
	<img src="img/404.png">
	<a href="http://form.mikecrm.com/f.php?t=tGQOkI"><div class="btn">提交账号</div></a>
</body>

</html>

