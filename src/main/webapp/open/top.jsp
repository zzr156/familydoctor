<%@page import="com.ylz.packcommon.common.Constant"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.ylz.bizDo.cd.po.CdUser"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
CdUser user = (CdUser)request.getSession().getAttribute(Constant.SESSION_ATTRIBUTE_LOGIN_STAFF);
String userName = user.getUserName();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ include file="/open/commonjs/tld.jsp"%>
<base href="<%=basePath%>">
<title>头部</title>
</head>

<body>
<style>
* {
	margin: 0;
	padding: 0;
}

body {
	width: 100%;
	height: 60px;
	padding: 0px;
	margin: 0px;
	background: red;
}
.topbox{background:#fff url(<%=request.getContextPath()%>/open/images/top/02.png) 10px 0 no-repeat;width:100%; height:60px; overflow:hidden; position:relative;}
.topbox .leftlogo{position:absolute; left:0;}
.topbox .rightbtn{position:absolute; right:5px; top:5px;}
.rightbtn a{display:block; margin:0 5px;cursor: pointer;}
.rightbtn a img{border: 0;}
.topbox .rightinfo{position:absolute; right:10px; top:36px;}
.rightinfo img{vertical-align:middle; margin-right:5px;}
</style>
	<div class="topbox">
		<div class="leftlogo">
			<%-- <img src="<%=request.getContextPath()%>/open/images/top/022.png"> --%>
			<div style="font-size: 26px; color: #184AA5; font-family: '微软雅黑'; padding-top: 10px; margin-left:10px;">XXXX<span style="font-size: 26px; color: #101010; font-family: '微软雅黑'; padding-top: 10px;">管理系统平台</span></div>
		</div>
		<div class="rightbtn">
			<table cellpadding="0" cellspacing="0" border="0">
				<tr align="right">
					<td><a href="javascript:addTab('xiumi','修改密码','<%=request.getContextPath()%>/open/resetUserPwd.jsp');"><img src="<%=request.getContextPath()%>/open/images/top/xgmmbtn.png"></a></td>
					<td><a href="<%=request.getContextPath()%>/users.action?act=exit"><img src="<%=request.getContextPath()%>/open/images/top/tcbtn.png"></a>
					</td>
				</tr>
			</table>
		</div>
		<div class="rightinfo">
			<table cellpadding="0" cellspacing="0" border="0">
				<tr align="right">
					<td><img
						src="<%=request.getContextPath()%>/open/images/top/userpic.png"><%=userName%>&nbsp;你好，<span></span>欢迎登陆！
					</td>
					<td width="250"><span id="time"></span> <script
							language="javascript">
						showtime();
					</script></td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>
