<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
  <head>
	<%@ include file="/open/commonjs/tldHtml.jsp"%>
	<%@ include file="/open/commonjs/roleson.jsp"%>
    <base href="<%=basePath%>">
    
    <title>添加新角色</title>
    
  </head>
  
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="row">
			<div class="col-sm-12">
	            <div class="ibox float-e-margins">
	                <div class="ibox-title">
	                	<h5 id="roleAddTit"></h5>                
	                    <a class="label label-primary pull-right system role_list"  onclick="backTable();">返回</a>
	                </div>
	                <div class="ibox-content">
							<form role="form" id="form_vo"  class="form-horizontal">
									<input type="hidden" id="id" pofield="id" name="id" value="${role.id}" />
			         				<input type="hidden" id="handle"  name="handle" value="${handle}" />
			                		<div class="form-group">
			                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>角色名称：</label>
			                            <div class="col-sm-4">					                           
			                                <input id="rname" name="rname" pofield="rname" type="text" class="form-control">
			                            </div>
			                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>角色编码：</label>
			                            <div class="col-sm-4">
			                                  <input id="rnum" name="rnum" pofield="rnum" type="text" class="form-control">
			                            </div>
			                        </div>
			                         <div class="form-group">
			                        	<label class="col-sm-2 control-label"><span class="text-danger">*</span>描述：</label>
			                            <div class="col-sm-4" >	
			                            	   <input id="remark" name="remark" pofield="remark" type="text" class="form-control">		                           
			                            </div>
			                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>状态：</label>
			                            <div class="col-sm-4" id="state">
			                            	
			                            </div>
			                        </div>
								   <div class="text-center btnBar">
			                        	<a id="handleMethod" class="btn btn-primary system role_add" onclick="saveTable();">保存</a>                       	                           
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
			doAjaxPost('<%=request.getContextPath()%>/role.action?act=findCmmInit',{},callDataToInit);
		}
		function callDataToInit(data){
			if(data.map != null){
				//状态
				if(data.map.enable != null){
						$("#state").html("");
				    	$.each(data.map.enable, function(k, v) {
					        	dataUiCodeGroup("radio","state",v.codeTitle,v.codeValue);
					    });
			    	 	if($("#handle").val() == "add"){
			    	 		$("input[name='state'][value=1]").attr("checked",true);
					    }
				}
				onInit();//回选
			}
		} 
		
	
		//加载完成时，查询数据，记载到表格的方法
		function onInit() {
			if($("#id").val() != ""){
				doAjaxPost('<%=request.getContextPath()%>/role.action?act=jsonByOne',{id:$("#id").val()},callDataToRole);
			}

			if($("#handle").val() == "modify"){
				$("#roleAddTit").text("修改权限");
				$("#handleMethod").text("修改");
			}else if($("#handle").val() == "add"){
				$("#roleAddTit").text("新增权限");
			}
			
		}
		
		function callDataToRole(data){
			dataToUi(data,"form_vo");
		}
		
		//点击返回
		function backTable(){
			history.go(-1);
		}
		
	    
		//保存的方法
		function saveTable() {
			if(iecs($("#rname").val())){
	    		layer.open({
	    			skin: 'layui-layer-molv',
	    			closeBtn: 0,	    			  
	    			title: false,
	    			content:'角色名称不能为空！' ,
	    			anim: 5 ,
	    			btn: ['关闭']
    			});  
	    		$("#rname").focus();
	    		return; 
	    	}
	    	if(iecs($("#rnum").val())){
	    		layer.open({
	    			skin: 'layui-layer-molv',
	    			closeBtn: 0,	    			  
	    			title: false,
	    			content:'角色编码不能为空！' ,
	    			anim: 5 ,
	    			btn: ['关闭']
    			}); 
	    		$("#rnum").focus();
	    		return;
	    	}
	    	if(iecs($("#remark").val())){
	    		layer.open({
	    			skin: 'layui-layer-molv',
	    			closeBtn: 0,	    			  
	    			title: false,
	    			content:'描述不能为空!' ,
	    			anim: 5 ,
	    			btn: ['关闭']
    			});
	    		$("#remark").focus();
	    		return; 
	    	}
			vo = uiToData("form_vo",vo);
			var method = "add";
			if($("#id").val() != ""){
				method = "modify";	
			}
			doAjaxPost('<%=request.getContextPath()%>/role.action?act='+method,{strJson : JSON.stringify(vo)},callDataSave);
		}
		
		function callDataSave(data){
			if(data.msg == 'true'){
                var url = '<%=request.getContextPath()%>/role.action?act=forList';
                defualtHref(url);
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
