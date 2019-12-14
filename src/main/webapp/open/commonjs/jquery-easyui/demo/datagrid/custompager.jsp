<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/commonjs/tld.jsp"%>

<html>
<head>
	<meta charset="UTF-8">
	<title>Custom DataGrid Pager - jQuery EasyUI Demo</title>

</head>
<body>
	<h2>Custom DataGrid Pager</h2>
	<p>You can append some buttons to the standard datagrid pager bar.</p>
	<div style="margin:20px 0;"></div>
	<table id="dg" title="Custom DataGrid Pager" style="width:700px;height:250px" toolbar="#toolbar"
			data-options="rownumbers:true,singleSelect:true,pagination:true,url:'<%=request.getContextPath()%>/users.action?act=list_json',method:'get'">
		<thead>
			<tr>
                <th data-options="field:'account',width:100">账号</th>
                <th data-options="field:'workState',width:60,align:'center'">状态</th>
                <th data-options="
                    field: 'sector.sname',
                    width: 50,
                    formatter: function (value, row) {
                        if(row.sector != null && row.sector != ''){
                            return  row.sector.sname;
                        }
                    }">
                    状态
                </th>
			</tr>
		</thead>
	</table>
    <div id="toolbar" style="padding:5px;height:auto">
        <div>
            <a href="<%=request.getContextPath()%>/users.action?act=forAdd" class="easyui-linkbutton" iconCls="icon-add" plain="true">新增</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">修改</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyUser()">删除</a>
        </div>
        <div>
            账号: <s:textfield name="qvo.account" id="account" cssClass="easyui-textbox" size="10"/>
            <a href="javascript:FindData('list_json')" class="easyui-linkbutton" iconCls="icon-search">查询</a>
        </div>
    </div>
	<script type="text/javascript">
		$(function(){
			var pager = $('#dg').datagrid().datagrid('getPager');	// get the pager of datagrid
			pager.pagination({
                pageSize: 10,//每页显示的记录条数，默认为10
                pageList: [10,50,100],//可以设置每页记录条数的列表
                beforePageText: '第',//页数文本框前显示的汉字
                afterPageText: '页    共 {pages} 页',
                displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
			});
		})
        function FindData(actvalue){
            $('#dg').datagrid('load',{
                        act:actvalue
                    }
            );
        }

        function optFormater(value,row,index){
            var loginId = row.loginId;
            var loginCode = row.loginCode;
            var id_code = loginId+",'"+loginCode+"'";
            var opt = '';
            if(row.statuId==0){
                opt = '<a href="#" onclick="doUsable('+row.loginId+')">启用</a> | ';
            }else if(row.statuId==1){
                opt = '<a href="#" onclick="doForbidden('+row.loginId+')">禁用</a> |  ';
            }
            var detail = '<a href="#" onclick="goDetail('+row.loginId+')">详细</a> |  ';
            var edit = '<a href="javascript:openDialog_edit('+id_code+')">编辑</a> | ';
            var del = '<a href="#" onclick="doDel('+row.loginId+')">删除</a>';
            return opt+edit+del;
        };
    </script>
</body>
</html>
