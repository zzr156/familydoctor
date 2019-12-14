<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<title>协议内容主页</title>
	<%@ include file="/open/commonjs/tldLayui.jsp"%>
	<%@ include file="/open/commonjs/roleson.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/intercept/ueditor/ueditor.config.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/intercept/ueditor/ueditor.all.js"></script>
</head>

<body bgcolor="white">
<form class="layui-form-pane" id="form_vo">
	<blockquote class="layui-elem-quote" style="text-align: center;font-size: 20px" >协议编辑</blockquote>
	<input type="hidden" id="hospId"   pofield="hospId" />
	<input type="hidden" id="id"   pofield="id" />
	<div class="layui-form-item" style="text-align: center">
		<div class="layui-inline">
			<label class="layui-form-label">标题</label>
			<div class="layui-input-block">
				<input type="text"  pofield="mentTitle" id="mentTitle" class="layui-input" validator='{"msg":"标题不能为空!"}'/>
			</div>
		</div>

	</div>
	<div class="layui-form-item">
		<div class="layui-inline">
			<div class="layui-input-block">
			<input id="mentContent" pofield="mentContent" type="hidden"/>
			<textarea id="editor" name="editor" ></textarea>
			</div>
		</div>
	</div>
	<div class="layui-form-item" style="text-align: center">
		<div class="layui-inline" id="sjid" style="display: none">
			<label class="layui-form-label">所属市</label>
			<div class="layui-input-inline" >
				<select id="mentCityId" name="mentCityId" pofield="mentCityId"  class="layui-input" onchange="findHosp($('#mentCityId option:selected').val())" validator='{"msg":"市名称不能为空!"}'>
					<option value="">--请选择市--</option>
				</select>
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">所属医院</label>
			<div class="layui-input-inline" >
				<select id="mentHospId" name="mentHospId" pofield="mentHospId"  class="layui-input"  validator='{"msg":"医院名称不能为空!"}'>
					<option value="">--请选择医院--</option>
				</select>
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">是否启用</label>
			<div class="layui-input-block" id="mentState" >
				<input type="radio" name="mentState" value="1" title="启用" style="width:25px;height:25px;" pofield="mentState"> 启用
				<input type="radio" name="mentState" value="0" title="禁用" style="width:25px;height:25px;" pofield="mentState">  禁用
			</div>
		</div>
	</div>
	<div class="layui-form-item">
		<div class="layui-input-block" style="text-align: center;">
			<input type="button" id="roleadd" value="保存" onclick="saveTable()" class="layui-btn layui-btn-normal"/>
			<input type="button" id="" value="返回" onclick="goto()" class="layui-btn layui-btn-normal"/>
		</div>
	</div>
</form>
</body>
<script type="text/javascript" src="js/agreement_add.js?v=1.0"></script>
</html>
