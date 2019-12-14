<%--
  Created by IntelliJ IDEA.
  User: PC
  Date: 2015/9/6
  Time: 11:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
    <title></title>
  <%@ include file="/open/commonjs/tld.jsp"%>
  <%@ include file="/open/commonjs/roleson.jsp"%>
</head>
<body class="gray-bg">
<%--<a href="<%=request.getContextPath()%>/repository.action?act=forList" class="easyui-linkbutton">返回</a>--%>
<table width="100%">
  <tr>
    <td align="center" height="100%">
      <img  src="<%=request.getContextPath()%>/repository.action?act=view&id=<%=request.getParameter("id")%>"/>
    </td>
  </tr>
</table>

</body>
</html>
