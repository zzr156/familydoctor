<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
	<base href="<%=basePath%>">
	<title>流程部署</title>
	<%@ include file="/open/commonjs/tld.jsp"%>
	<%@ include file="/open/commonjs/roleson.jsp"%>
</head>

<body onload="FindData('list')" class="gray-bg">
<!--list-->
<table id="dg" toolbar="#toolbar" class="easyui-datagrid" data-options="fit:true,singleSelect:true,collapsible:true,pagination:true,
    	url:'<%=request.getContextPath()%>/repository.action',method:'post',idField:'id'">
	<thead>
	<tr>
		<th data-options="field:'id',align:'center'" width="15%">编码</th>
		<th data-options="field:'name',align:'center'" width="15%">流程名称</th>
		<th data-options="field:'ves',align:'center'" width="15%">版本号</th>
		<th data-options="field:'actkey',align:'center'" width="15%">key</th>
		<th data-options="field:'opt',align:'center',formatter:rowformater" width="15%">操作</th>
	</tr>
	</thead>
</table>
<div id="toolbar" style="padding:5px;height:auto">
	<table cellspacing="0" cellpadding="0">
		<tr>
			<td><a href="javascript:void(0)" class="easyui-linkbutton system repository_add" iconCls="icon-add" plain="true" onclick="newUser()">部署流程</a></td>
			<td><div class="datagrid-btn-separator"></div></td>
			<td><a href="javascript:void(0)" class="easyui-linkbutton system repository_szblr" iconCls="icon-edit" plain="true" onclick="szblrForm()">设置步骤办理人</a></td>
			<td><div class="datagrid-btn-separator"></div></td>
			<td><a href="javascript:void(0)" class="easyui-linkbutton system repository_view" iconCls="icon-search" plain="true" onclick="viewForm()">查看</a></td>
			<td><div class="datagrid-btn-separator"></div></td>
			<td><a href="javascript:void(0)" class="easyui-linkbutton system repository_delete" iconCls="icon-remove" plain="true" onclick="deleteForm()">删除</a></td>
			<td><div class="datagrid-btn-separator"></div></td>
			<td>
			</td>
		</tr>
	</table>
	<table cellspacing="0" cellpadding="0">
		<tr>
			<td>
				key: <s:textfield name="qvo.key" id="key" cssClass="easyui-textbox" size="10"/>
				流程名称: <s:textfield name="qvo.name" id="name" cssClass="easyui-textbox" size="10"/>
				<a href="javascript:FindData('list')" class="easyui-linkbutton system repository_list" iconCls="icon-search">查询</a>
			</td>
		</tr>
	</table>
</div>

<!--add-->
<div id="dlg" class="easyui-dialog" style="width:750px;height:auto;padding:10px 20px" closed="true" buttons="#dlg-buttons">
	<s:form enctype="multipart/form-data" action="repository" method="post" name="listForm" id="listFormi" theme="simple"	validate="true" cssClass="easyui-form">
		<table width="100%" cellpadding="0" cellspacing="0" border="0" class="t-bordered">
			<tr>
				<td align="right">流程名称：</td>
				<td><input type="text" name="flowname" id="flowname" class="easyui-textbox" style="width:200px" data-options="required:true,missingMessage:'不能为空'"/></td>
				<td align="right">流程类型：</td>
				<td><input type="text" name="flowtype" id="flowtype" class="easyui-textbox" style="width:200px" data-options="required:true,missingMessage:'不能为空'"/></td>
			</tr>
			<tr>
				<td align="right">文件(zip):</td>
				<td>
					<s:file name="ufile" data-options="required:true,missingMessage:'不能为空'"/>
				</td>
			</tr>
		</table>
	</s:form>
</div>
<div id="dlg-buttons">
	<a href="javascript:void(0)" id="add" name="add" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="addForm()" style="width:90px">上传</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')" style="width:90px">关闭</a>
</div>

<div id="openRoleDiv" class="easyui-window" closed="true" modal="true" title="标题" style="width:950px;height:650px;overflow:hidden;">
	<iframe scrolling="auto" id='openIframe' frameborder="0"  src="" style="width:100%;height:100%;"></iframe>
</div>
<script type="text/javascript">

	function FindData(actvalue){
		$('#dg').datagrid('load',{
					act:actvalue,
					"qvo.key":$('#key').val(),
					"qvo.name":$('#name').val()
				}
		);
	}
	function reloadData(){
		$('#dg').datagrid('reload');
	}

	<!--add-->
	function newUser(){
		$('#dlg').dialog({modal:true}).dialog('open').dialog('setTitle','部署流程').dialog('move',{top:$(document).scrollTop() + ($(window).height()-450) * 0.5});
		$('#listFormi').form('clear');
	}

	function addForm(){
		$('#listFormi').form('submit',{
			url:"<%=request.getContextPath()%>/repository.action?act=add",
			onSubmit:function(){
				return $(this).form('enableValidation').form('validate');
			},
			success:function(data){
				var data=eval('(' + data + ')');
				if(data.msg=="true"){
					$.messager.show({
						showSpeed:2000,
						title:'提示信息',
						msg:'操作成功',
						timeout:1
					});
				}else{
					$.messager.alert('操作提示',data.msg,'error');
				}
				$('#dlg').dialog('close');
				reloadData();
			}
		});
	}




	<!--delete-->
	function deleteForm(){
		var row = $('#dg').datagrid('getSelected');
		if (row){
			$.messager.confirm('删除提示','确认删除数据?',function(r){
				if (r){
					$.post('<%=request.getContextPath()%>/repository.action?act=delete',{'id':row.id},function(data){
						var data=eval('(' + data + ')');
						if(data.msg=="true"){
							$.messager.show({
								showSpeed:2000,
								title:'提示信息',
								msg:'删除成功',
								timeout:1
							});
							reloadData();
						} else {
							$.messager.alert('操作提示',data.msg,'error');
						}
					});
				}
			});
		}else{
			$.messager.alert('操作提示','必须选中一条记录进行操作','error');
		}
	}
	<!--查看流程-->
	function viewForm(){
		var row = $('#dg').treegrid('getSelected');
		if (row){
			$('#openIframe')[0].src='<%=request.getContextPath()%>/intercept/flow/repository/repository_view.jsp?id='+row.id;
			$('#openRoleDiv').dialog('open').dialog('setTitle','流程图');
		}else{
			$.messager.alert('操作提示','请选择一条记录进行添加','error');
		}
	}

	function szblrForm(){
		var row = $('#dg').treegrid('getSelected');
		if (row){
			$('#openIframe')[0].src='<%=request.getContextPath()%>/repository.action?act=forSzblr&id='+row.id;
			$('#openRoleDiv').dialog('open').dialog('setTitle','设置步骤办理人');
		}else{
			$.messager.alert('操作提示','请选择一条记录进行添加','error');
		}
	}

	function rowformater(value,row,index)
	{
		return "<a href=javascript:void(0) onclick=viewForm2('"+row.id+"') class=easyui-linkbutton system repository_view iconCls=icon-search>查看</a>";
	}
	function viewForm2(id){
		if (id){
			$('#openIframe')[0].src='<%=request.getContextPath()%>/intercept/flow/repository/repository_view.jsp?id='+id;
			$('#openRoleDiv').dialog('open').dialog('setTitle','流程图');
		}else{
			$.messager.alert('操作提示','请选择一条记录进行添加','error');
		}
	}
</script>
</body>
</html>
