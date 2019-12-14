<%--
  Created by IntelliJ IDEA.
  User: PC
  Date: 2015/9/6
  Time: 11:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
    <title></title>
  <%@ include file="/open/commonjs/tld.jsp"%>
  <%@ include file="/open/commonjs/roleson.jsp"%>
</head>
<body onload="listData('listLcbz')" class="gray-bg">
	<s:hidden id="flowId" name="flowId" />
	<div  class="easyui-layout" data-options="fit : true,border : false">
        <div data-options="region:'west',split:true" title="步骤" style="width:30%;">
        	<table id="lcbz" class="easyui-datagrid" data-options="fit:true,singleSelect:true,collapsible:true,
    				url:'<%=request.getContextPath()%>/repository.action',method:'post',idField:'id'">
                <thead>
                    <tr>
                        <th data-options="field:'name',align:'center'" width="100%">任务名称</th>
                    </tr>
                </thead>
            </table>
        </div>
        <div data-options="region:'center'" title="规则">
            <div class="easyui-layout" data-options="fit:true">
                <div data-options="region:'north'"  style="height:20%">
                	<table  style="width: 100%;height: 100%;" border="0"  >
                		<tr>
                			<td align="right" style="width: 40%">规则:</td>
                			<td >
                				<input class="easyui-combobox" id="flowStep"  name="flowStep"  /> 
                			</td>
                		</tr>
                	</table>
                </div>
                <div data-options="region:'center'">
                	<table id="ryxz" toolbar="#toolbar" class="easyui-datagrid" data-options="fit:true,singleSelect:true,collapsible:true,
		    				url:'<%=request.getContextPath()%>/repository.action',method:'post',idField:'id'">
		                <thead>
		                    <tr>
		                        <th data-options="field:'name',align:'center'" width="100%"><span id="mc">名称</span></th>
		                    </tr>
		                </thead>
		            </table>
                	<div id="toolbar" style="padding: 5px; height: auto;" >
						<table cellspacing="0" cellpadding="0">
							<tr>
								<td>
									部门名称:<s:textfield name="deptId" id="deptId" cssClass="easyui-combotree" cssStyle="width:200px;"/>  
									<a href="javascript:loadData('listLcZdry')"
										class="easyui-linkbutton system code_list" iconCls="icon-search">查询</a>
								</td>
							</tr>
						</table>
					</div>
                </div>
            </div>
        </div>
    </div>
    
    <script type="text/javascript">
	$(function() {
		$('#toolbar').hide();
		$('#lcbz').datagrid({
			onClickRow : function(index, data) {
				var row = $('#lcbz').datagrid('getSelected');
				$('#toolbar').hide();
				$("#mc").html("名称"); 
				if (row) {
					$('#flowStep').combobox({                     
		       	      url: '<%=request.getContextPath()%>/repository.action?act=jsonList',
			       	   method:'post',  
	                   valueField:'typeid',  
	                   textField:'typeName',
	                   onSelect: function(rec){
	                       $("#mc").html(rec.typeName); 
	                       if(rec.typeState == "1"){
	                    	   $('#toolbar').show();
	                       }else{
	                    	   $('#toolbar').hide();
	                       }
	                   }
		          	});  
				}
			}
		});
	});
	function listData(actvalue) {
		$('#lcbz').datagrid('load', {
			act : actvalue,
			"flowId" : $('#flowId').val()
		});
	}
	
	function loadData(actvalue) {
		$('#lcbz').datagrid('load', {
			act : actvalue,
			"flowId" : $('#flowId').val()
		});
	}
	
	
</script>
</body>

</html>
