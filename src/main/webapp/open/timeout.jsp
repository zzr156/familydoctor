<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ include file="/open/commonjs/tldHtml.jsp"%>
<base href="<%=basePath%>">
<title>登陆超时</title>

<style>
*{margin:0;
padding:0;}
body {
 width:100%; height:100%; padding:0px; margin:0px;background:#fff;
}
.systembg{background:#dbe7ee url(<%=request.getContextPath()%>/open/images/login/log_bottom.jpg) center top repeat-x; height:160px;}
.systimout{width:970px; margin:0 auto; text-align:center; padding-top:150px;}
.systopic{width:261px; height:200px; margin:0 auto; text-align:center; background:url(<%=request.getContextPath()%>/open/images/system/timeout.png) center top repeat-x; padding-top:60px; padding-bottom:20px;}
.systopic h1{font-size:32px; font-family:'微软雅黑';}
.systopic a{display:block;margin-top:20px; font-size:14px; color:#2152AB;}
.systopic a:hover{color:#3281C6;}
</style>
</head>

<body>
     <div class="systembg">
     	<div class="systimout">
         	<div class="systopic">
             	<h1>登陆超时</h1>
                 <a href="<%=path %>/open/login.jsp" >请重新登陆！</a>
             </div>
         </div>
          
     </div>
</body>