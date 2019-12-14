<!DOCTYPE html>
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
<title>用户管理主页</title>
<%@ include file="/open/commonjs/tldHtml.jsp"%>
<%@ include file="/open/commonjs/roleson.jsp"%>
</head>

<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">							                
	                <div class="ibox-content" id="form_qvo">
	                    <form method="get" class="form-horizontal">
	                        <div class="form-group">
	                            <label class="col-sm-2 control-label">账号：</label>
	                            <div class="col-sm-4">
	                                <input type="text" class="form-control" pofield="account"  id="account">
	                            </div>
	                            <label class="col-sm-2 control-label">用户名：</label>
	                            	<div class="col-sm-4 ">
                                    	 <input type="text" class="form-control" pofield="userName"  id="userName">
	                            </div>
	                        </div>      
	                        <div class="form-group">
	                            <label class="col-sm-2 control-label">部门：</label>
	                            <div class="col-sm-4">
		                            <div class="input-group">
		                             	<input id="deptName" readonly="readonly" type="text" class="form-control"/>
										<input type="hidden" pofield="deptId" id="deptId" >
	                                	<span class="input-group-btn"> 
	                                		  <button type="button" class="btn btn-primary"  data-toggle="modal" data-target="#myModalCdUserDept">选择部门</button> 
	                                 	</span>
	                             	</div>
	                            </div>
	                            <label class="col-sm-2 control-label">用户状态：</label>
	                            	<div class="col-sm-4 ">
                                    	<select class="form-control" pofield="workState" id="workState">
											<option value="">--请选择--</option>
				                        </select>
	                            </div>
	                        </div>     
	                        <div class="form-group">
		                            <label class="col-sm-2 control-label">显示条数：</label>
		                           	<div class="col-sm-10 ">
		                                  	<input type="text" class="form-control" pofield="pageSize"  id="pageSize"  value="15" >
		                           	</div>
	                        </div>              
	                        <div class="text-center" style="border-top: 1px dashed #D7D7D7;padding-top: 10px;">
	                        	<a class="btn btn-primary system users_list" onclick="findTable();">查询</a>
	                        	<a class="btn btn-default system users_list" onclick="findReset();">重置</a>                           
	                        </div>
	                        <input type="hidden" pofield="itemCount" id="itemCount" value="">
	                        <input type="hidden" pofield="pageCount" id="pageCount" value="">
	                        <input type="hidden" pofield="pageStartNo" id="pageStartNo" value="1">
	                 	</form>
	                 </div>       
				</div>
			</div>
		</div>
		
	
		<div class="row">
	        <div class="col-sm-12">
	            <div class="ibox float-e-margins">
	            	<div class="ibox-title">
						<a class="label label-danger pull-right system users_delete" onclick="user_del();">批量删除</a>
	                    <a class="label label-success pull-right system users_add" onclick="forAddTable();">新增</a>
					</div>	
	                <div class="ibox-content tabble-c">
	                    <div class="table-responsive">
	                        <table class="table table-striped" id="usertable">
	                            <thead>
	                            <tr>
	                                	<th style="text-align:center;"><input type="checkbox" id="selectAll"style="width:22px;height:22px" onclick="selectAll()"/></th>
									 	<th style="text-align: center;">序号</th>
			            				<th style="text-align: center;">用户名</th>
			            				<th style="text-align: center;">用户编码</th>
			            				<th style="text-align: center;">账号</th>
			             				<th style="text-align: center;">所属单位</th>
			            				<th style="text-align: center;">部门名称</th>
			            				<th style="text-align: center;">岗位</th>
			            				<th style="text-align: center;">状态</th>
			            				<th style="text-align: center;">操作</th>
	                            </tr>
	                            </thead>
	                            <tbody id="user_list">
	
								</tbody>
	                        </table>
	                    </div>
	                    <!--分页按钮-->
	                    <div class="text-center" style="background: #fff; padding-top: 5px;">
	                        <button type="button" class="btn btn-sm" onclick="Previous();findTable();">前一页</button>
	                        <span id="qvodesc">1/1每页显示:15条,共有1条</span>&nbsp;&nbsp;
	                        <button type="button" class="btn btn-sm" onclick="Next();findTable();">下一页</button>
	                        <input style="width: 40px" class="span2" id="gotext" type="text">
	                        <button class="btn btn-sm" onclick="qvoGo();findTable();" type="button">Go!</button>
	                    </div>
	                </div>
	            </div>
	        </div>
	    </div>
	</div>
	<jsp:include page="user_ChangePeople.jsp" flush="false" />
	<jsp:include page="/open/mould/cd/user/user_tmp.jsp" flush="false" />
	<script type="text/javascript">
	   function uiFromTmp(id, data,idx) {
		    var ui = $($("#" + id + "").html());
		    ui.data("vo", data);
		    ui.find("td[pofield=userId] input[name=chckBox]").val(data.userId);
	        ui.find("td[pofield=num]").text(idx+1);
	        ui.find("td[pofield=userName]").text(data.userName);
	        ui.find("td[pofield=userNum]").text(data.userNum);
	        ui.find("td[pofield=account]").text(data.account);
	        ui.find("td[pofield=cdDeptUnit]").text(data.cdDeptUnit);
	        ui.find("td[pofield=cdDeptName]").text(data.cdDeptName);
	        ui.find("td[pofield=userPostname]").text(data.userPostname);
	        ui.find("td[pofield=workStateName]").text(data.workStateName);
	        $("#user_list").append(ui);
	        ui.fadeIn();
	    }
	
	</script>
	<script type="text/javascript">
	    var qvo={};
	    var vo={};
	    <!--list-->
	    $(function(){    	
	 		onInit();
	 		
	    })
	
	    //重置
	    function findReset(){
        	$('#account').val("");
        	$('#deptId').val("");
        	$('#deptName').val("");
        	$('#userName').val("");
        	$('#workState').val("");
        }
	    //加载完成时，查询数据，记载到表格的方法
		function onInit() {
	    	findState();//状态
	    	findDept();
		}
		
	    
	  	//状态
	    function findState(){
	    	doAjaxPost('<%=request.getContextPath()%>/code.action?act=jsonTreelist',{group:'workState'},callDataToState);	
	    }
	    function callDataToState(data){
	     	$("#workState").html();
	        $.each(data, function(k, v) {
	        	dataUiCodeGroup("select","workState",v.codeTitle,v.codeValue);
	        });
	    }
	    
	    //查询
	    function findTable(){
	        qvo = uiToData("form_qvo",qvo);//界面值绑定到变量
	        doAjaxPost('<%=request.getContextPath()%>/users.action?act=list',{qvoJson:JSON.stringify(qvo)},callDataToUi);
	    }
	    function callDataToUi(data){
	        dataToUi(data.qvo,"form_qvo");//数据绑定到界面
	        qvodesc("qvodesc");
	        $("#usertable tbody").html("");
	        if(data.rows != null){
	        	$.each(data.rows, function(k, v) {
		            uiFromTmp("tlist", v,k);
		        });
	        }
	    }
	
	    //准备新增
	    function forAddTable(){
	    	var url = '<%=request.getContextPath()%>/users.action?act=forAddOrEdit&handle=add';
	    	defualtHref(url);
	    }
	    //准备修改
	    function forModifyTable(ui){
	    	var url = '<%=request.getContextPath()%>/users.action?act=forAddOrEdit&handle=modify&vo.userId='+$(ui).data("vo").userId;
	    	defualtHref(url);
	    }
	    //查看
	    function forLookTable(ui){
	    	var url = '<%=request.getContextPath()%>/users.action?act=forAddOrEdit&handle=look&vo.userId='+$(ui).data("vo").userId;
	    	defualtHref(url);
	    }
	    
	 	//全选和全选取消
	    function selectAll(){
	    	if($("#selectAll").prop("checked")){
	    		$.each($("#user_list").find("input[name=chckBox]"),function(k,v){
	    			$(this).prop("checked",true);
	    		});
	    	}else{
	    		$.each($("#user_list").find("input[name=chckBox]"),function(k,v){
	    			$(this).prop("checked",false);
	    		});
	    	}
	    }
	  
	    //删除页面获取删除用户ID
	    function user_del(){
	    	var userIds="";
	    	$.each($("#user_list").find("input[name=chckBox]"),function(k,v){
	    		if($(this).prop("checked")){
	    			if(userIds!=""){
	    				userIds+=";";
	    			}
	    			userIds+=$(this).val();
	    		}
	    	});
	    	if(userIds==""){
	    		layer.open({
           			skin: 'layui-layer-molv',
           			closeBtn: 0,	    			  
           			title: false,
           			content:'请选择需要删除的用户!' ,
           			anim: 5 ,
           			btn: ['关闭']
         		});
	    	}else{
    		    layer.confirm('确认删除数据?', {
	   			  btn: ['确定','取消'] 
	   			}, function(){
	   				del(userIds);
	   			});
	    	}
	    }
	    
	    //删除
	    function delForm(ui){
	    	layer.confirm('确认删除数据?', {
   			  btn: ['确定','取消'] 
   			}, function(){
   				var userId = $(ui).data("vo").userId;
		        del(userId);
   			});
        }
	    
	    function del(userId){
        	$.post('<%=request.getContextPath()%>/users.action?act=delete',{'id':userId},function(data){
                var data=eval('(' + data + ')');
              	if(data.msg=="true"){
              		layer.open({
               			skin: 'layui-layer-molv',
               			closeBtn: 0,	    			  
               			title: false,
               			content:'删除成功!' ,
               			anim: 5 ,
               			btn: ['关闭']
             		});
              		findTable();
                } else {
                      layer.open({
                 			skin: 'layui-layer-molv',
                 			closeBtn: 0,	    			  
                 			title: false,
                 			content:data.msg ,
                 			anim: 5 ,
                 			btn: ['关闭']
               		  });
                }
            });
	    }
	</script>
</body>
</html>
