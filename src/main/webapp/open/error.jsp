<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
<%@ include file="/open/commonjs/tld.jsp"%>
<base href="<%=basePath%>">
<title>错误页</title>

<style>
*{margin:0;padding:0;}
body {
 width:100%; height:100%; padding:0px; margin:0px;background:#fff;
}
.systembg{background:#dbe7ee url(<%=request.getContextPath()%>/open/images/login/log_bottom.jpg) center top repeat-x; height:160px;}
.syserror{width:970px; margin:0 auto; text-align:center; padding-top:120px;}
.syserorpic{width:253px; height:316px; margin:0 auto; text-align:center; background:url(<%=request.getContextPath()%>/open/images/system/error.png) center top repeat-x; padding-top:60px; padding-bottom:20px; padding-left:270px;}
.syserorpic h1{font-size:32px; font-family:'微软雅黑';}
.syserorpic p{margin-top:20px; font-size:14px; color:#2152AB;}
</style>
</head>

<body>
    <div class="systembg">
    	<div class="syserror">
        	<div class="syserorpic">

                <p>${msg}</p>
            </div>
        </div>
         
    </div>
</body>
</html>
