<%@page import="com.superDaxue.model.TimeTable"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String schoolName=(String)session.getAttribute("schoolName") ;
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
    <!-- 如果使用双核浏览器，强制使用webkit来进行页面渲染 -->
    <meta name="renderer" content="webkit" />
    <!-- 网站描述 -->
    <meta name="description" content="超级大学，通过为高校公众号运营提供教务、社交等功能与便携的校园服务，给大学生们提供最快捷获取校园新闻，社团活动，校园招聘信息，让大学不再是一片信息孤岛，你的大学，一个就够了" />
    <!-- 网站搜索关键词 -->
    <meta name="keywords" content="超级大学, 微信第三方, 微信, 公众, 服务, 平台,微信联盟, 大学联盟, 接口, 微信查成绩, 查课表, 课程表,广告主,流量主,学生兼职,校园二手,app,手机版,">
    <link rel="stylesheet" href="css/timetable.css">
    <link rel="shortcut icon" href="img/logo.ico">
    <script type="text/javascript" src="js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="js/jquery.touchSwipe.min.js"></script>
    <script type="text/javascript" src="js/timetable.js"></script>
</head>

<body>
    <div>
        <table>
            <tr>
                <th></th>
                <th>周一</th>
                <th>周二</th>
                <th>周三</th>
                <th>周四</th>
                <th>周五</th>
                <th>周六</th>
                <th>周日</th>
            </tr>
            <tr>
                <td>1</td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <td>2</td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <td>3</td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <td>4</td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <td>5</td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <td>6</td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <td>7</td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <td>8</td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <td>9</td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <td>10</td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <td>11</td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <td>12</td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
             <tr>
                <td>13</td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
        </table>
    </div>
    <div class="code">
    	<div class="window">
    		<p>账号绑定</p>
    		<div class="in">
    			<p>没有发现课程，重新绑定试试吧</p>
    			<div class="button close">取消</div>
    			<div class="button ok">绑定</div>
    			</form>
    		</div>
    	</div>
    </div>
    <div class="append">
    </div>
    <nav>
       <div><a class="on"><img src="img/timetableon.png"><br/>课表</a></div>
    	<div><a href="servlet/ScoreServlet"><img src="img/grade.png"><br/>成绩</a></div>
       <!--  <div><a href="javascript:;"><img src="img/emptyroom.png"><br/>空教室</a></div> -->
    </nav>
    <script type="text/javascript">
    <%
    	List<TimeTable> list=(List<TimeTable>)request.getAttribute("timetableList");
    	if(list.size()==0){
    	%>
    	
    	$(".code").show();
    	$(".close").on("click",function(){
    		$(".code").hide();
    	});
    	$(".ok").on("click",function(){
    		window.location.href="jsp/signin.jsp";
    	});
    	<%
    	}
    	
    	for(TimeTable item:list){
    		String name=item.getCourseName();
    		if(name.length()>10){
    			name=name.substring(0,9)+"…";
    		}
    		String time=item.getTime();
    		if(time==null){
    			continue;
    		}
    		String[] arr=time.split("-");
    		int start=Integer.parseInt(arr[0]);
    		int len=Integer.parseInt(arr[1])-start+1;
    		String week = item.getWeek();
    		if(week==null||"".equals(week)){
    			continue;
    		}
    		int weekInt=0;
    		 try{
    			weekInt=Integer.parseInt(week);
    		}catch(Exception e){ 
    		char a=week.charAt(1);
    			switch(a){
	    		case '一':weekInt=1;break;
	    		case '二':weekInt=2;break;
	    		case '三':weekInt=3;break;
	    		case '四':weekInt=4;break;
	    		case '五':weekInt=5;break;
	    		case '六':weekInt=6;break;
	    		case '日':weekInt=7;break;
	    		} 
    		}
    		String address=item.getAddress();
    		int id=item.getId();
    		String sd=item.getSingleDouble();
    		if(!"".equals(address)){
    			address="@"+address;
    		}
    		if(!"".equals(sd)&&sd!=null){
    			address=address+"("+sd+")";
    		}
    		%>
    		 add(<%=weekInt%>, <%=start%>,<%=len%>, "<%=name%>", "<%=address%>", <%=id%>);
    		<%
    	}
    		
    %>
   
    </script>
</body>

</html>
