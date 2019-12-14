<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>帝魁互联用户管理</title>
	<%@ include file="/open/commonjs/tld.jsp"%>
	<%@ include file="/open/commonjs/roleson.jsp"%>
  </head>
   
  <body onload="FindData('userBankCardList')" class="gray-bg">
		<!--list-->
		<table id="dg" toolbar="#toolbar"
			data-options="fit:true,rownumbers:true,singleSelect:true,pagination:false,
    		url:'<%=request.getContextPath()%>/users.action',method:'post',idField:'id'">
			<thead>
				<tr>
	                <th data-options="field:'userBankNum',align:'center'" width="15%">银行卡号码</th>
	                <th data-options="field:'userName',align:'center'" width="7%">开户名 </th>
	                <th data-options="field:'idCardNum',align:'center'" width="15%">身份证 </th>
	                <th data-options="field:'bankPreMobile',align:'center',sortable:true" width="10%">银行预留电话</th>
	                <th data-options="field:'strBankName',align:'center'" width="15%">开户行</th>
	                <th data-options="field:'branch',align:'center'" width="10%">分行</th>
	                <th data-options="field:'subbranch',align:'center'" width="10%">支行</th>
					<th data-options="field:'publicOrPrivateName',align:'center'" width="6%" >公/私</th>
				</tr>
			</thead>
		</table>
		<div id="toolbar" style="padding: 5px; height: auto">
			<table cellspacing="0" cellpadding="0">
				<tr>
	                <td><a href="javascript:void(0)" class="easyui-linkbutton system users_userBankCardAddOrModify" 
	                	iconCls="icon-add" plain="true" onclick="newUser()">新增</a></td>
	                <td><a href="javascript:void(0)" class="easyui-linkbutton system users_userBankCardAddOrModify" 
	                	iconCls="icon-edit" plain="true" onclick="editUser()">修改</a></td>
	                <td><a href="javascript:void(0)" class="easyui-linkbutton system users_userBankCardDelete" 
	                	iconCls="icon-remove" plain="true" onclick="deleteForm()">删除</a></td>
				</tr>
			</table>
		</div>
		
	<!--add-->
    <div id="dlg" class="easyui-dialog" style="width:750px;height:auto;padding:10px 20px" closed="true" buttons="#dlg-buttons">
    <s:form action="users" method="post" name="BankCardForm" id="BankCardForm" theme="simple" validate="true" cssClass="easyui-form">
        <table width="100%" cellpadding="0" cellspacing="0" border="0" class="t-bordered" id="tableId">
            <tr>
                <td align="right">银行卡号码：</td>
                <td><s:textfield name="userBankNum" id="userBankNum" cssClass="easyui-textbox" 
                	cssStyle="width:200" data-options="required:true,missingMessage:'不能为空'"/></td>
                <td align="right">开户名：</td>
                <td><s:textfield name="userName" id="userName" cssClass="easyui-textbox" 
                	cssStyle="width:200" data-options="required:true,missingMessage:'不能为空'"/></td>
            </tr>
            <tr>
                <td align="right">身份证:</td>
                <td><s:textfield name="idCardNum" id="idCardNum" cssClass="easyui-textbox" 
                	cssStyle="width:200" data-options="required:true,missingMessage:'不能为空'"/>
                </td>
                <td align="right">银行预留电话:</td>
                <td><s:textfield name="bankPreMobile" id="bankPreMobile" 
                	cssClass="easyui-textbox" cssStyle="width:200" data-options="required:true,missingMessage:'不能为空'"/>
                </td>
            </tr>
            <tr>
                <td align="right">开户行：</td>
                <td colspan="3">
					<s:radio name="bankName" list="emperorBankList" id="bankName" 
 						listKey="id" listValue="bankName" data-options="required:true"/>
                </td>
            </tr>
             <tr>
                <td align="right">分行:</td>
                <td><s:textfield name="branch" id="branch"  data-options="required:true,missingMessage:'不能为空'"
                	cssClass="easyui-textbox" cssStyle="width:200" />
                </td>
                <td align="right">支行:</td>
                <td><s:textfield name="subbranch" id="subbranch"  data-options="required:true,missingMessage:'不能为空'"
                	cssClass="easyui-textbox" cssStyle="width:200" />
                </td>
            </tr>
            <tr>
                <td align="right">公/私:</td>
                <td colspan="3">
					<s:radio name="publicOrPrivate" id="publicOrPrivate" 
						list="#{'0':'公&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;','1':'私&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'}"
						listKey="key" listValue="value" data-options="required:true,missingMessage:'不能为空'"/>
				</td>
            </tr>
        </table>
        <input type="hidden" id="id" name="id"/>
        </s:form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" id="add" name="add" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="addForm()" style="width:90px">保存</a>
        <a href="javascript:void(0)" id="modify" name="modify" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="modifyForm()" style="width:90px">修改</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')" style="width:90px">关闭</a>
    </div>
		
		
		<script type="text/javascript">
		<!--userBankCardList-->
        $(function(){
        	 $('#dg').datagrid({	
     	   		pageSize: PAGE, 
     	   		pageList: PAGELIST
     	    });
            var pager = $('#dg').datagrid().datagrid('getPager');	// get the pager of datagrid
            pager.pagination({
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
        
		function reloadData(){
			$('#dg').datagrid('reload');
		}
        
        function findReset(){
        }
        
        <!--add-->
        function newUser(){
            $('#dlg').dialog({modal:true}).dialog('open').dialog('setTitle','添加银行卡')
            	.dialog('move',{top:$(document).scrollTop() + ($(window).height()-450) * 0.5});
            $('#BankCardForm').form('clear');
            $('#BankCardForm').form('load',{});
            $('#modify').hide();
            $('#add').show();
            document.getElementsByName('publicOrPrivate')[1].checked=true;
        }
        
        function addForm(){
			$('#dlg').dialog('close');
			load();
			$('#BankCardForm').form('submit',{
			   url:"<%=request.getContextPath()%>/users.action?act=userBankCardAddOrModify",
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
			        }else if(data.msg=="绑定银行卡成功!"){
                        $.messager.alert('操作提示',data.msg,'info');
                    }else{
			            $.messager.alert('操作提示',data.msg,'error');
			        }
			        disLoad();
			        FindData('userBankCardList');
			    }
			});
        }
        
        <!--modify-->
        function editUser(){
            var row = $('#dg').datagrid('getSelected');
            if (row){
                $('#BankCardForm').form('clear');
                $('#dlg').dialog({modal:true}).dialog('open').dialog('setTitle','修改银行卡')
					.dialog('move',{top:$(document).scrollTop() + ($(window).height()-450) * 0.5});
                $('#BankCardForm').form('load',row);
                $('#add').hide();
                $('#modify').show();
            }else{
                $.messager.alert('操作提示','必须选中一条记录进行操作','error');
            }
        }
        function modifyForm(){
			$('#dlg').dialog('close');
			load();
            $('#BankCardForm').form('submit',{
                url:"<%=request.getContextPath()%>/users.action?act=userBankCardAddOrModify",
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
                    }else if(data.msg=="绑定银行卡成功!"){
                        $.messager.alert('操作提示',data.msg,'info');
                    }else{
                        $.messager.alert('操作提示',data.msg,'error');
                    }
			        disLoad();
                    FindData('userBankCardList');
                }
            });
        }

        <!--delete-->
        function deleteForm(){
            var row = $('#dg').datagrid('getSelected');
            if (row){
                $.messager.confirm('删除提示','确认删除数据?',function(r){
                    if (r){
                        $.post('<%=request.getContextPath()%>/users.action?act=userBankCardDelete&id='+row.id,{id:row.id},function(data){
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
        
		//弹出加载层
		function load() {  
		    $("<div class=\"datagrid-mask\"></div>").css({ display: "block", width: "100%", height: $(window).height() }).appendTo("body");  
		    $("<div class=\"datagrid-mask-msg\"></div>").html("数据处理中，请稍候。。。").appendTo("body").css({ display: "block", left: ($(document.body).outerWidth(true) - 190) / 2, top: ($(window).height() - 45) / 2 });  
		}
		//取消加载层  
		function disLoad() {  
		    $(".datagrid-mask").remove();  
		    $(".datagrid-mask-msg").remove();  
		}
	</script>
	</body>
</html>