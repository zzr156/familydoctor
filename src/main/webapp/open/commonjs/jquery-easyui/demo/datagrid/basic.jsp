<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Basic DataGrid - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/commonjs/jquery-easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/commonjs/jquery-easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/commonjs/jquery-easyui/demo/demo.css">
	<script type="text/javascript" src="<%=request.getContextPath()%>/commonjs/jquery-easyui/jquery.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/commonjs/jquery-easyui/jquery.easyui.min.js"></script>
</head>
<body>
	<h2>Basic DataGrid</h2>
	<p>The DataGrid is created from markup, no JavaScript code needed.</p>
	<div style="margin:20px 0;"></div>
	
	<table class="easyui-datagrid" title="Basic DataGrid" style="width:700px;height:250px"
			data-options="singleSelect:true,collapsible:true,url:'http://localhost:8080/zk/users.action?act=list_json',method:'get'">
		<thead>
			<tr>
				<th data-options="field:'account',width:80">Item ID</th>
			</tr>
		</thead>
	</table>

</body>
</html>