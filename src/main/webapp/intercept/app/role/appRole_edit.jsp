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
<title>手机权限主页</title>
<%@ include file="/open/commonjs/tldHtml.jsp"%>
<%@ include file="/open/commonjs/roleson.jsp"%>
</head>

<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="row">
			<div class="col-sm-12">
	            <div class="ibox float-e-margins">
	                <div class="ibox-title">
	                	<h5 id="codeAddTit"></h5>
	                    <a class="label label-primary pull-right system approle_list"  onclick="backTable();">返回</a>
	                </div>
	                <div class="ibox-content">
							<form role="form" id="form_vo"  class="form-horizontal">
									<input type="hidden" id="id" pofield="id" name="id" value="${vo.id}" />
			          				<input type="hidden" id="handle"  name="handle" value="${handle}" />
			                		<div class="form-group">
			                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>权限名称：</label>
			                            <div class="col-sm-4">
			                                <input id="roleName" name="roleName" pofield="roleName" type="text" class="form-control">
			                            </div>
			                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>权限值：</label>
			                            <div class="col-sm-4">
			                                <input id="roleValue" name="roleValue" pofield="roleValue" type="text" class="form-control">
			                            </div>
			                        </div>
			                        <div class="form-group">
			                        	 <label class="col-sm-2 control-label"><span class="text-danger">*</span>是否启用：</label>
			                             <div class="col-sm-10" id="roleState">
			                             </div>
			                        </div>
								   <div class="text-center btnBar">
			                        	<a id="handleMethod" class="btn btn-primary system approle_add" onclick="saveTable();">保存</a>
			                       </div>
						      </form>
						 </div>

	               </div>
	          </div>
	    </div>
	</div>
    <script type="text/javascript">
    	var vo={};
		//页面加载完成时启动的方法
		$(document).ready(function() {
			findCmmInit();
		});


		//统一初始化
		function findCmmInit(){
			doAjaxPost('<%=request.getContextPath()%>/approle.action?act=findCmmInit',{},callDataToInit);
		}
		function callDataToInit(data){
			if(data.map != null){
				//状态
				if(data.map.enable != null){
						$("#roleState").html("");
				    	$.each(data.map.enable, function(k, v) {
					        	dataUiCodeGroup("radio","roleState",v.codeTitle,v.codeValue);
					    });
			    	 	if($("#handle").val() == "add"){
			    	 		$("input[name='roleState'][value=1]").attr("checked",true);
					    }
				}
				onInit();//回选
			}
		}


		//加载完成时，查询数据，记载到表格的方法
		function onInit() {
			if($("#id").val() != ""){
				doAjaxPost('<%=request.getContextPath()%>/approle.action?act=jsonByOne',{id:$("#id").val()},callDataToAppRole);
			}
			if($("#handle").val() == "look"){
				$("#codeAddTit").text("查看手机权限数据");
				$("#handleMethod").hide();
			}else if($("#handle").val() == "modify"){
				$("#codeAddTit").text("修改手机权限数据");
				$("#handleMethod").text("修改");
			}else if($("#handle").val() == "add"){
				$("#codeAddTit").text("新增手机权限数据");
			}
		}

		function callDataToAppRole(data){
			dataToUi(data,"form_vo");
		}

		//点击返回
		function backTable(){
			history.go(-1);
		}


		//保存的方法
		function saveTable() {
			if(iecs($("#roleName").val())){
	    		showMsg("权限名称不能为空！");
	    		$("#roleName").focus();
	    		return;
	    	}
	    	if(iecs($("#roleValue").val())){
	    		 showMsg("权限值不能为空!");
	    		 $("#roleValue").focus();
	    		 return;
	    	}
			vo = uiToData("form_vo",vo);
			var method = "add";
			if($("#id").val() != ""){
				method = "modify";
			}
			doAjaxPost('<%=request.getContextPath()%>/approle.action?act='+method,{strJson : JSON.stringify(vo)},callDataSave);
		}

		function callDataSave(data){
			if(data.msg == 'true'){
				var url = '<%=request.getContextPath()%>/approle.action?act=forList';
                defualtHref(url);
			}else{
                showMsg(data.msg);
			}
		}
	</script>
</body>
</html>
