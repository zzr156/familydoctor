<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<%@ include file="/open/commonjs/tldLayui.jsp"%>
<title>查看刷卡记录</title>
</head>
<body>

	<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
		<legend>查看刷卡记录</legend>
	</fieldset>

	<table id="visitRecordsTable" lay-filter="visitRecordsTable"></table>
</body>
<script type="text/javascript" src="js/view_visitRecords_list.js?v=1.0"></script>

<script type="text/html" id="indexTpl">
	{{d.LAY_TABLE_INDEX+1}}
</script>
</html>
