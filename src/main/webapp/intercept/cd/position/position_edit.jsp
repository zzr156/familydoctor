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
<title>职位管理主页</title>
<%@ include file="/open/commonjs/tldHtml.jsp"%>
<%@ include file="/open/commonjs/roleson.jsp"%>
</head>

<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="row">
			<div class="col-sm-12">
	            <div class="ibox float-e-margins">
	                <div class="ibox-title">
	                	<h5 id="positionAddTit"></h5>                
	                    <a class="label label-primary pull-right system position_list"  onclick="backTable();">返回</a>
	                </div>
	                <div class="ibox-content">
							<form role="form" id="form_vo"  class="form-horizontal">
									<input type="hidden" id="id" pofield="id" name="id" value="${vo.id}" />
			          				<input type="hidden" id="handle"  name="handle" value="${handle}" />
			                		<div class="form-group">
			                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>编号：</label>
			                            <div class="col-sm-10">					                           
			                                <input id="num" name="num" pofield="num" type="text" class="form-control">
			                            </div>
			                        </div>
			                         <div class="form-group">
			                        	<label class="col-sm-2 control-label"><span class="text-danger">*</span>职位：</label>
			                            <div class="col-sm-10" >	
			                            	   <input id="account" name="account" pofield="account" type="text" class="form-control">		                           
			                            </div>
			                        </div>
								   <div class="text-center btnBar">
			                        	<a id="handleMethod" class="btn btn-primary system position_add" onclick="saveTable();">保存</a>                       	                           
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
			onInit();
		});
	
		//加载完成时，查询数据，记载到表格的方法
		function onInit() {
			if($("#id").val() != ""){
				doAjaxPost('<%=request.getContextPath()%>/position.action?act=jsonByOne',{id:$("#id").val()},callDataToPosition);
			}
			if($("#handle").val() == "look"){
				$("#handleMethod").hide();
				$("#positionAddTit").text("查看职位");
			}else if($("#handle").val() == "modify"){
				$("#handleMethod").text("修改");
				$("#positionAddTit").text("修改职位");
			}else if($("#handle").val() == "add"){
				$("#positionAddTit").text("新增职位");
			}
			
		}
		
		function callDataToPosition(data){
			dataToUi(data,"form_vo");
		}
		
		//点击返回
		function backTable(){
			history.go(-1);
		}
		
		//保存的方法
		function saveTable() {
			if(iecs($("#num").val())){
				layer.open({
	    			skin: 'layui-layer-molv',
	    			closeBtn: 0,	    			  
	    			title: false,
	    			content:'编号不能为空！' ,
	    			anim: 5 ,
	    			btn: ['关闭']
    			});  
	    		$("#num").focus();
	    		return; 
	    	}
	    	if(iecs($("#account").val())){
	    		layer.open({
	    			skin: 'layui-layer-molv',
	    			closeBtn: 0,	    			  
	    			title: false,
	    			content:'职位不能为空！' ,
	    			anim: 5 ,
	    			btn: ['关闭']
    			});  
	    		$("#account").focus();
	    		return;
	    	}
			vo = uiToData("form_vo",vo);
			var method = "add";
			if($("#id").val() != ""){
				method = "modify";	
			}
			doAjaxPost('<%=request.getContextPath()%>/position.action?act='+method,{strJson : JSON.stringify(vo)},callDataSave);
		}
		
		function callDataSave(data){
			if(data.msg == 'true'){
				window.location.href = '<%=request.getContextPath()%>/position.action?act=forList';
			}else{
				layer.open({
 	    			skin: 'layui-layer-molv',
 	    			closeBtn: 0,	    			  
 	    			title: false,
 	    			content:data.msg ,
 	    			anim: 5 ,
 	    			btn: ['关闭']
     		  	 }); 
			}
		}
	</script>
</body>
</html>
