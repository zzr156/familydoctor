<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>

<head>
<%@ include file="/open/commonjs/tldLayui.jsp"%>
<title>孕产期随访</title>
</head>

<body>
	<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
		<legend>孕中期随访记录</legend>
	</fieldset>
	<table id="yzsfTable" lay-filter="yzsfTable"></table>
	
	<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
		<legend>孕晚期随访记录</legend>
	</fieldset>
	<table id="ywsfTable" lay-filter="ywsfTable"></table>
	
	<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
		<legend>产后访视记录</legend>
	</fieldset>
	<table id="chfsTable" lay-filter="chfsTable"></table>
</body>
<script type="text/javascript"
	src="js/view_gravidaFollowUpRecords_list.js"></script>
</html>
<script type="text/html" id="indexTpl1">
	{{d.LAY_TABLE_INDEX+1}}
</script>
<script type="text/html" id="indexTpl2">
	{{d.LAY_TABLE_INDEX+1}}
</script>
<script type="text/html" id="indexTpl3">
	{{d.LAY_TABLE_INDEX+1}}
</script>
<script type="text/html" id="barRole">
	<a class="layui-btn layui-btn-mini " lay-event="look" ><i class=layui-icon>&#xe615;</i>查看详情</a>
</script>
<script type="text/html" id="barRole1">
	<a class="layui-btn layui-btn-mini " lay-event="look" ><i class=layui-icon>&#xe615;</i>查看详情</a>
</script>
<script type="text/html" id="barRole2">
	<a class="layui-btn layui-btn-mini " lay-event="look" ><i class=layui-icon>&#xe615;</i>查看详情</a>
</script>