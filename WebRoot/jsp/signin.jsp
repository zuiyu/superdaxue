
<%@page import="com.sun.corba.se.impl.protocol.giopmsgheaders.Message"%>
<%@ page language="java" import="java.util.*,net.sf.json.JSONObject" pageEncoding="UTF-8"%>
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
   	<meta name="keywords" content="微信查成绩,手机查成绩，超级大学，教务系统开发，超越超级课程表，超越萌小助，超越大学助手，超越掌上大学，更好正方教务系统查成绩，青果教务系统查成绩，强智教务系统查成绩,urp教务系统查成绩,正方查成绩，青果查成绩，强智查成绩" /> 
	<meta name="description" content="超级大学，手机查成绩查课表就是这么简单，妈妈再也不担心我会忘记上啥课了，你的大学一个就够了，" />
	<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <link rel="stylesheet" href="css/example.css">
    <link rel="stylesheet" href="css/weui.css">
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
    
    
  	

    <div class="hd">
        <h1 class="page_title">手机查成绩</h1>
        <p class="page_desc">请先绑定教务系统</p>
    </div>
    <div class="weui_cells weui_cells_form">
        <form>
            <div class="weui_cell">
                <div class="weui_cell_hd">
                    <label class="weui_label">学号</label>
                </div>
                <div class="weui_cell_bd weui_cell_primary">
                    <input class="weui_input" type="input" name="username" placeholder="请输入你的学号">
                </div>
            </div>
            <div class="weui_cell">
                <div class="weui_cell_hd">
                    <label class="weui_label">密码</label>
                </div>
                <div class="weui_cell_bd weui_cell_primary">
                    <input class="weui_input" type="password" name="password" placeholder="请输入你的密码">
                </div>
            </div>
            <div class="weui_cell weui_vcode" style="display: none;">
                <div class="weui_cell_hd">
                    <label class="weui_label">验证码</label>
                </div>
                <div class="weui_cell_bd weui_cell_primary">
                    <input class="weui_input" type="text" name="checkNum" placeholder="请输入验证码">
                </div>
                <div class="weui_cell_ft">
                    <img class="code" src="" alt="验证码">
                </div>
            </div>
        </form>
    </div>
    <div class="weui_btn_area">
                <a class="weui_btn weui_btn_primary" href="javascript:" id="showTooltips">绑定</a>
            </div>
    <!-- 登录中弹框 -->
    <div class="weui_mask" style="display: none"></div>
    <div id="loadingToast" class="weui_loading_toast" style="display: none">
        <div class="weui_mask_transparent"></div>
        <div class="weui_toast">
            <div class="weui_loading">
                <div class="weui_loading_leaf weui_loading_leaf_0"></div>
                <div class="weui_loading_leaf weui_loading_leaf_1"></div>
                <div class="weui_loading_leaf weui_loading_leaf_2"></div>
                <div class="weui_loading_leaf weui_loading_leaf_3"></div>
                <div class="weui_loading_leaf weui_loading_leaf_4"></div>
                <div class="weui_loading_leaf weui_loading_leaf_5"></div>
                <div class="weui_loading_leaf weui_loading_leaf_6"></div>
                <div class="weui_loading_leaf weui_loading_leaf_7"></div>
                <div class="weui_loading_leaf weui_loading_leaf_8"></div>
                <div class="weui_loading_leaf weui_loading_leaf_9"></div>
                <div class="weui_loading_leaf weui_loading_leaf_10"></div>
                <div class="weui_loading_leaf weui_loading_leaf_11"></div>
            </div>
            <p class="weui_toast_content">绑定中</p>
        </div>
    </div>
    <!-- 错误提示，显示时用$.show();隐藏时用$.hide(); -->
    <div class="weui_toptips weui_warn js_tooltips"><%=message %></div>

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
    	
    	//更新验证码
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
           		$(".weui_vcode").show();
           		$(".code").attr("src",msg);
           	}
           }
         });
    	}
    	
    	//初次加载获取验证码
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
	           	if(msg=="noPassword"){
	           		$("input[type='password']").closest("div").hide();
	           	}else{
	           	$(".weui_vcode").show();
	           	$(".code").attr("src",msg).on("click",function(){
	           		codeClick();
	           		});
	           	}	
	           	}
           }
         });
         
         //提交表单数据，绑定
         $("#showTooltips").on("click",function(){
         	var i = 0;
	    	$("input").each(function(){
	    		if(!$(this).is(":hidden")&&$(this).val()==""){
	    			$(".weui_warn").text("请将信息填写完整！").show().delay(2000).hide(0);
	    			i++;
	    			return false;
	    		}
	    	});
	    	if(i>0){
	    		return false;
	    	}
           	$("#loadingToast, .weui_mask").show();
         	$.post("servlet/LoginServlet",$("form").serialize(),function(data){
         		if(data.isSuccess==1){
         			location.href = 'servlet/ScoreServlet';
         		}else{
         			$("#loadingToast, .weui_mask").hide();
         			codeClick();
         			$(".weui_warn").text(data.message).fadeIn().delay(2000).fadeOut(0);
         		}
         	},"json");
         });
    });
    </script>
</body>

</html>

