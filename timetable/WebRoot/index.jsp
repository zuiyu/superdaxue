<%@ page language="java" import="java.util.*,net.sf.json.JSONArray,net.sf.json.JSONObject,com.superDaxue.model.Courses" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<head>
	<base href="<%=basePath%>">
    <meta charset="UTF-8">
    <title>超级大学，一个就够了</title>
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
    <style type="text/css">
    * {
        margin: 0;
        padding: 0;
    }
    
    html {
        height: 100%;
    }
    
    body {
        height: 100%;
        background: #F0EFF5;
    }
    
    nav {
        position: fixed;
        top: 50%;
        right: .5rem;
    }
    
    nav a {
        display: block;
        color: #7e7e7e;
        text-decoration: none;
        font-weight: bold;
    }
    
    .main {
        border: solid #D7D6DC;
        border-width: 1px 0;
        margin: 5px 0 10px;
    }
    
    .alpha {
        background: #fff;
        padding: 0 10px;
        line-height: 22px;
    }
    
    .tr {
        border: 1px solid #EFEEF4;
        padding: 5px 0;
    }
    
    .tr img {
        height: 3rem;
        vertical-align: middle;
        padding-right: 15px;
    }
    .tr a{
    	text-decoration:none;
    	color:#000;
    }
    </style>
</head>

<body>
<%
	JSONArray data=JSONArray.fromObject("[{\"name\":\"广东海洋大学\",\"website\":\"gdou\",\"first\":\"G\"}"+
				",{\"name\":\"河南大学\",\"website\":\"henu\",\"first\":\"H\"}"+
				",{\"name\":\"苏州职业技术学院\",\"website\":\"siit\",\"first\":\"S\"}"+
				",{\"name\":\"德州学院\",\"website\":\"dzu\",\"first\":\"D\"}"+
				",{\"name\":\"陇东学院\",\"website\":\"ldxy\",\"first\":\"L\"}"+
				",{\"name\":\"许昌学院\",\"website\":\"xcu\",\"first\":\"X\"}"+
				",{\"name\":\"山东管理学院\",\"website\":\"sdmu\",\"first\":\"S\"}"+
				",{\"name\":\"南京工程学院\",\"website\":\"njit\",\"first\":\"N\"}"+
				",{\"name\":\"黑龙江东方学院\",\"website\":\"dfxy\",\"first\":\"H\"}"+
				",{\"name\":\"海南大学\",\"website\":\"hainu\",\"first\":\"H\"}"+
				",{\"name\":\"辽宁工业大学\",\"website\":\"lnit\",\"first\":\"L\"}"+
				",{\"name\":\"广东工业大学华立学院\",\"website\":\"hualixy\",\"first\":\"G\"}"+
				",{\"name\":\"广东轻工业职业技术学院\",\"website\":\"gdqy\",\"first\":\"G\"}"+
				",{\"name\":\"绍兴文理元培学院\",\"website\":\"ypc\",\"first\":\"S\"}"+
				",{\"name\":\"佛山职业技术学院\",\"website\":\"fspt\",\"first\":\"F\"}"+
				",{\"name\":\"河北工程大学\",\"website\":\"hebeu\",\"first\":\"H\"}"+
				",{\"name\":\"河海大学天文学院\",\"website\":\"hhuwtian\",\"first\":\"H\"}"+
				",{\"name\":\"新乡学院\",\"website\":\"xxu\",\"first\":\"X\"}"+
				",{\"name\":\"湖南涉外经济学院\",\"website\":\"hneu\",\"first\":\"H\"}"+
				",{\"name\":\"兰州财经大学长青学院\",\"website\":\"changqinglzufe\",\"first\":\"L\"}"+
				",{\"name\":\"芜湖职业技术学院\",\"website\":\"whit\",\"first\":\"W\"}"+
				",{\"name\":\"湖南工业大学\",\"website\":\"hut\",\"first\":\"H\"}"+
				",{\"name\":\"陕西邮电职业技术学院\",\"website\":\"sptc\",\"first\":\"S\"}"+
				",{\"name\":\"苏州信息职业技术学院\",\"website\":\"szitu\",\"first\":\"S\"}"+
				",{\"name\":\"江西陶瓷工艺美术职业技术学院\",\"website\":\"jxgymy\",\"first\":\"J\"}"+
				",{\"name\":\"山东英才学院\",\"website\":\"ycxy\",\"first\":\"S\"}"+
				",{\"name\":\"新乡医学院三全学院\",\"website\":\"sanquanyixuey\",\"first\":\"X\"}"+
				",{\"name\":\"昆明医科大学\",\"website\":\"kmmc\",\"first\":\"K\"}"+
				",{\"name\":\"江西工业职业技术学院\",\"website\":\"jxgzy\",\"first\":\"J\"}"+
				",{\"name\":\"西安医学院\",\"website\":\"xagdyz\",\"first\":\"X\"}"+
				",{\"name\":\"黄河水院\",\"website\":\"yrcti\",\"first\":\"H\"}"+
				",{\"name\":\"哈尔滨远东理工学院\",\"website\":\"feedu\",\"first\":\"H\"}"+
				",{\"name\":\"太原理工大学现代科技学院\",\"website\":\"xdkj\",\"first\":\"T\"}"+
				",{\"name\":\"福建师范大学\",\"website\":\"fjnu\",\"first\":\"F\"}"+
				",{\"name\":\"黑龙江农业经济职业学院\",\"website\":\"nyjj\",\"first\":\"H\"}"+
				",{\"name\":\"商丘师范学院\",\"website\":\"sqnc\",\"first\":\"S\"}"+
				",{\"name\":\"吉林师范大学\",\"website\":\"jlnu\",\"first\":\"J\"}"+
				",{\"name\":\"吕梁学院\",\"website\":\"llhc\",\"first\":\"L\"}"+
				",{\"name\":\"南京理工大学\",\"website\":\"njust\",\"first\":\"N\"}"+
				",{\"name\":\"江西外语外贸职业学院\",\"website\":\"jxcfs\",\"first\":\"J\"}"+
				",{\"name\":\"广东创新科技职业学院\",\"website\":\"gdcxxy\",\"first\":\"G\"}"+
				",{\"name\":\"广东财经大学华商学院\",\"website\":\"gdhsc\",\"first\":\"G\"}"+
				",{\"name\":\"牡丹江师范学院\",\"website\":\"mdjnu\",\"first\":\"M\"}"+
				",{\"name\":\"南通科技职业学院\",\"website\":\"ntst\",\"first\":\"N\"}"+
				",{\"name\":\"山西工程技术学院\",\"website\":\"sxit\",\"first\":\"S\"}"+
				",{\"name\":\"广东石油化工学院\",\"website\":\"gdpa\",\"first\":\"G\"}"+
				",{\"name\":\"天水师范学院\",\"website\":\"tsnc\",\"first\":\"T\"}"+
				//	",{\"name\":\"\",\"website\":\"\",\"first\":\"\"}"+
				"]");
	 %>
	<nav id="nav"> 
		<a href="#A">A</a>
		<a href="#B">B</a>
		<a href="#C">C</a>
		<a href="#D">D</a>
		<a href="#E">E</a> 
		<a href="#F">F</a> 
		<a href="#G">G</a>
		<a href="#H">H</a> 
		<a href="#I">I</a> 
		<a href="#J">J</a>
		<a href="#K">K</a>
		<a href="#L">L</a>
		<a href="#M">M</a> 
		<a href="#N">N</a> 
		<a href="#O">O</a> 
		<a href="#P">P</a> 
		<a href="#Q">Q</a> 
		<a href="#R">R</a> 
		<a href="#S">S</a> 
		<a href="#T">T</a> 
		<a href="#U">U</a> 
		<a href="#V">V</a> 
		<a href="#W">W</a> 
		<a href="#X">X</a> 
		<a href="#Y">Y</a> 
		<a href="#Z">Z</a> 
	</nav>

	<div class="main">
		<div class="alpha" id="A" hidden>
        	<p>A</p>
        </div>
        <div class="alpha" id="B" hidden>
        	<p>B</p>
        </div>
        <div class="alpha" id="C" hidden>
        	<p>C</p>
        </div>
        <div class="alpha" id="D" hidden>
        	<p>D</p>
        </div>
        <div class="alpha" id="E" hidden>
        	<p>E</p>
        </div>
        <div class="alpha" id="F" hidden>
        	<p>F</p>
        </div>
        <div class="alpha" id="G" hidden>
        	<p>G</p>
        </div>
        <div class="alpha" id="H" hidden>
        	<p>H</p>
        </div>
        <div class="alpha" id="I" hidden>
        	<p>I</p>
        </div>
        <div class="alpha" id="J" hidden>
        	<p>J</p>
        </div>
        <div class="alpha" id="K" hidden>
        	<p>K</p>
        </div>
        <div class="alpha" id="L" hidden>
        	<p>L</p>
        </div>
        <div class="alpha" id="M" hidden>
        	<p>M</p>
        </div>
        <div class="alpha" id="N" hidden>
        	<p>N</p>
        </div>
        <div class="alpha" id="O" hidden>
        	<p>O</p>
        </div>
        <div class="alpha" id="P" hidden>
        	<p>P</p>
        </div>
        <div class="alpha" id="Q" hidden>
        	<p>Q</p>
        </div>
        <div class="alpha" id="R" hidden>
        	<p>R</p>
        </div>
        <div class="alpha" id="S" hidden>
        	<p>S</p>
        </div>
        <div class="alpha" id="T" hidden>
        	<p>T</p>
        </div>
        <div class="alpha" id="U" hidden>
        	<p>U</p>
        </div>
        <div class="alpha" id="V" hidden>
        	<p>V</p>
        </div>
        <div class="alpha" id="W" hidden>
        	<p>W</p>
        </div>
        <div class="alpha" id="X" hidden>
        	<p>X</p>
        </div>
        <div class="alpha" id="Y" hidden>
        	<p>Y</p>
        </div>
        <div class="alpha" id="Z" hidden>
        	<p>Z</p>
        </div>
        
    </div>
</body>
	
<script type="text/javascript">
var nav = document.getElementsByTagName("nav")[0];
nav.style.marginTop = -(nav.offsetHeight / 2) + "px";
<%
	for(int i=0;i<data.size();i++){
		JSONObject obj=data.getJSONObject(i);
		%>
		var html="<div class='tr'><img src='img/school/<%=obj.get("website").toString()%>.jpg'><a href='<%=obj.get("website").toString()%>'><%=obj.get("name").toString()%></a></div>";
		$("#<%=obj.get("first").toString()%>").show();
		$("#<%=obj.get("first").toString()%>").append(html);
	
		<%
	}
%>
</script>
</html>
