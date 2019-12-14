<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>

    <title>协议管理</title>
	  <%@ include file="/open/commonjs/tldLayui.jsp"%>
	  <%@ include file="/open/commonjs/roleson.jsp"%>
</head>
<body bgcolor="white">
<div id="form_qvo">
	<blockquote class="layui-elem-quote" style="text-align: center" id="blockId">
		<input type="hidden" pofield="hospId" id="hospId"/>
		<button onclick="findList()" class="layui-btn layui-btn-small">
			<i class=layui-icon>&#xe615;</i>查询
		</button>
		<button onclick="AddTable()" class="layui-btn layui-btn-danger system openagreement_add">
			<i class=layui-icon>&#xe642;</i>新增
		</button>
	</blockquote>
<table id="signTabel" lay-filter="sign">

</table>
</div>

</body>
  <script type="text/javascript" src="js/agreement_list.js?v=1.0"></script>
  <script type="text/html" id="barRole">
	  <a class="layui-btn layui-btn-mini " lay-event="look" ><i class=layui-icon>&#xe615;</i>查看</a>
	  <a class="layui-btn layui-btn-danger layui-btn-mini system sysRoleAction_modify" lay-event="modify"><i class=layui-icon>&#xe642;</i>修改</a>
	  <a class="layui-btn layui-btn-danger layui-btn-danger system sysRoleAction_delete" lay-event="del"><i class=layui-icon>&#xe640;</i>删除</a>
  </script>
</html>
