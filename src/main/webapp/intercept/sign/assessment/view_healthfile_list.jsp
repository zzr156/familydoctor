<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<%@ include file="/open/commonjs/tldLayui.jsp"%>
<title>健康体检调阅</title>
</head>
<body>
	<table id="healthfileTable" lay-filter="healthfileTable"></table>
</body>
<script type="text/javascript" src="js/view_healthfile_list.js?v=1.0"></script>

<script type="text/html" id="barRole">
    <a class="layui-btn layui-btn-mini " lay-event="look" ><i class=layui-icon>&#xe615;</i>查看详情</a>
</script>
<script type="text/html" id="indexTpl">
	{{d.LAY_TABLE_INDEX+1}}
</script>
</html>
