<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<%@ include file="/open/commonjs/tldLayui.jsp"%>
<title>儿童随访记录调阅</title>
</head>
<body>
	<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
		<legend>儿童随访信息</legend>
	</fieldset>

	<table id="followRecordsAndHealthTable" lay-filter="followRecordsAndHealthTable"></table>
</body>
<script type="text/javascript" src="js/view_followRecordsAndHealth_list.js?v=1.0"></script>

<script type="text/html" id="barRole">
    <a class="layui-btn layui-btn-mini " lay-event="look" ><i class=layui-icon>&#xe615;</i>查看详情</a>
</script>
<script type="text/html" id="indexTpl">
	{{d.LAY_TABLE_INDEX+1}}
</script>
</html>
