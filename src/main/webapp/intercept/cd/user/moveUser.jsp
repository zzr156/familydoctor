<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/open/commonjs/fielErr.jsp"%>
<%@ include file="/open/commonjs/tld.jsp"%>
<%@ include file="/open/commonjs/roleson.jsp"%>
<%
	String path = request.getContextPath();
	pageContext.setAttribute("path", path);
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>移动人员</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<%@ include file="/open/commonjs/ieComand.jsp"%>
		<link href="<%=basePath%>/open/css/styles.css" type="text/css" rel="stylesheet" />
		<style type="text/css">
body {
	text-align: center;
}
</style>
<script type="text/javascript">
function fn(){
	var cddept = document.getElementById("cdDept");
	if (trim(cddept.value) == "") {
		alert("请选择数据!!!");
		return false;
	}
	return true;
}
//去除字符串两边空格
function trim(str) {
	return str.replace(/^\s+/, '').replace(/\s+$/, '');
}
</script>
</head>
<body  class="gray-bg">
<table class="form">
 <thead><tr><td colspan="4">人员移动</td></tr></thead></table>
 <div class="list">
  <s:form action="users" method="post">
  <input type="hidden" name="vers" id="vers" value="<%=Calendar.getInstance().getTimeInMillis() %>"/>
   <s:hidden id="act" name="act" />
   <input type="hidden" id="paramId" name="paramId" value="${paramId }" />
   <table class="form">
    <tr>
     <td>部门:</td>
     <td>
     		<select id="cdDept" name="cdDeptid">
							<s:iterator value="cdDeptlist" status="status">
								<option value="${id}"
								<%if (request.getAttribute("id").equals(
							     request.getAttribute("vo.cdDept.id"))) {%>
										selected="selected" 
								<%}%>
								>${sname}
								</option>
							</s:iterator>
			</select>
     
    
    </td>
   </tr>
   <tfoot>
	   <tr>
	    <td colspan="2">
	     <input class="system users_move" type="button" value="保存" onclick="if(fn()){act.value='move';submit();}" />
	     <input type="button" value="返回" onclick="act.value='list';submit()" />
	    </td>
	   </tr>
   </tfoot>
  </table>
 </s:form>
</div>
</body>
</html>
