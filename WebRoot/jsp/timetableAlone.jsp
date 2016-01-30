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
    <meta name="keywords" content="微信查成绩,手机查成绩，超级大学，教务系统开发，超越超级课程表，超越萌小助，超越大学助手，超越掌上大学，更好正方教务系统查成绩，青果教务系统查成绩，强智教务系统查成绩,urp教务系统查成绩,正方查成绩，青果查成绩，强智查成绩" /> 
	<meta name="description" content="超级大学，手机查成绩查课表就是这么简单，妈妈再也不担心我会忘记上啥课了，你的大学一个就够了，" />
	
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
   
    <div class="append">
    </div>
    <!-- <nav>
       <div><a class="on"><img src="img/timetableon.png"><br/>课表</a></div>
    	<div><a href="servlet/ScoreServlet"><img src="img/grade.png"><br/>成绩</a></div>
        <div><a href="javascript:;"><img src="img/emptyroom.png"><br/>空教室</a></div>
    </nav> -->
    <script type="text/javascript">
    <%
    	List<TimeTable> list=(List<TimeTable>)request.getAttribute("timetableList");
    	if(list.size()==0){
    	%>
    	
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
