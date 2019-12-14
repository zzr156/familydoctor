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
<title>部门管理主页</title>
<%@ include file="/open/commonjs/tldHtml.jsp"%>
<%@ include file="/open/commonjs/roleson.jsp"%>
</head>

<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="row">
			<div class="col-sm-12">
	            <div class="ibox float-e-margins">
	                <div class="ibox-title">
	                	<h5 id="menuAddTit"></h5>                
	                    <a class="label label-primary pull-right system menu_list"  onclick="backTable();">返回</a>
	                </div>
	                <div class="ibox-content">
							<form role="form" id="form_vo"  class="form-horizontal">
									<input type="hidden" id="id" pofield="id" name="id" value="${menu.id}" />
									<input type="hidden" id="handle"  name="handle" value="${handle}" />
									<input type="hidden" id="addson" name="addson" value="${addson}"/>
			                		<div class="form-group">
			                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>菜单名称：</label>
			                            <div class="col-sm-4">					                           
			                                <input id="mname" name="mname" pofield="mname" type="text" class="form-control">
			                            </div>
			                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>顺序号：</label>
			                            <div class="col-sm-4">
			                                  <input id="onumber" name="onumber" pofield="onumber" type="text" class="form-control">
			                            </div>
			                        </div>
			                        <div class="form-group">
			                        	<label class="col-sm-2 control-label"><span class="text-danger">*</span>所属上级菜单：</label>
			                        	<div class="col-sm-4">
			                        		<div class="input-group" id="menu">
				                        				<input type="text" id="parnentMname" pofield="parnentMname" readonly="readonly"  class="form-control" />               		
														<input type="hidden" pofield="cdMenu" id="cdMenu" />
														<input type="hidden" pofield="pid" id="pid"/>
														<input type="hidden" pofield="_parentId" id="_parentId"/>
														<input type="hidden" pofield="parnentId" id="parnentId"/>
                                       			<span class="input-group-btn"> 
                                       				<button type="button" class="btn btn-primary"  data-toggle="modal" data-target="#myModal" onclick="chooseMenu()">选择菜单</button></span>
                                       			</span>
                                   			</div>
			                        	</div>
			                        	<label class="col-sm-2 control-label">状态：</label>
			                            <div class="col-sm-4"  id="state">
			                            
			                            </div>
			                        </div>
			                         <div class="form-group">
			                        	<label class="col-sm-2 control-label"><span class="text-danger">*</span>菜单链接地址：</label>
			                            <div class="col-sm-4">	
			                            		<input pofield="address" id="address" name="address" type="text" class="form-control"/>			                           
			                            </div>
			                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>菜单图标：</label>
			                            <div class="col-sm-4">	
			                            	<input pofield="menuIcon" id="menuIcon" name="menuIcon" type="text" class="form-control"/>
			                            </div>
			                        </div>
								   <div class="text-center btnBar">
			                        	<a id="handleMethod" class="btn btn-primary system menu_add" onclick="addForm();">保存</a>                       	                           
			                       </div>
						      </form>
						 </div>
	               </div>
	          </div>
	    </div>
	</div>
	<jsp:include page="menu_ChangeMenu.jsp" flush="false" />
    <script type="text/javascript">
	    var menu={};
	  //页面加载完成时启动的方法
		$(document).ready(function() {
			findMenu();
			findCmmInit();
		});
		
		//统一初始化
		function findCmmInit(){
			doAjaxPost('<%=request.getContextPath()%>/menu.action?act=findCmmInit',{},callDataToInit);
		}
		function callDataToInit(data){
			if(data.map != null){
				//状态
				if(data.map.enable != null){
						$("#state").html("");
				    	$.each(data.map.enable, function(k, v) {
					        	dataUiCodeGroup("radio","state",v.codeTitle,v.codeValue);
					    });
			    	 	if($("#handle").val() == "add" || $("#handle").val()=="addzi"){
			    	 		$("input[name='state'][value=1]").attr("checked",true);
					    }
				}
				onInit();//回选
			}
		}
		
		function onInit(){
			if($("#id").val()!=""){
				doAjaxPost('<%=request.getContextPath()%>/menu.action?act=jsonByOne',{id:$("#id").val()},callToMenu);
			}
			if($("#handle").val()=="add"){
				$("#menuAddTit").text("新增顶级菜单");
				$("#menu").hide();
			}else if($("#handle").val()=="addzi"){
				$("#menuAddTit").text("新增子菜单");
				var id = $("#addson").val();
				doAjaxPost('<%=request.getContextPath()%>/menu.action?act=jsonByOne',{id:id},function(dataSonMeun){
		        	$('#pid').val( dataSonMeun.id);
		        	$('#parnentMname').val(dataSonMeun.mname);
				});
			}else if($("#handle").val()=="modify"){
				$("#handleMethod").text("修改");
				$("#menuAddTit").text("修改菜单");
			}
		}
		function callToMenu(data){
			//alert(JSON.stringify(data));
			dataToUi(data,"form_vo");
			var pid = $("#pid").val();
			var _parentId = $("#_parentId").val();
			var parnentId = $("#parnentId").val();
			if(pid != null && pid != ""){
				$("#pid").val(pid); 
			}else if(_parentId != null && _parentId != ""){
				$("#pid").val(_parentId); 
			}else if(parnentId !=null && parnentId !=""){
				$("#pid").val(parnentId);
			}
			
			if($("#parnentMname").val()==""){
				$("#menu").hide();
			}else{
				$("#menu").show();
			}
		}
		//所属菜单回选
		function chooseMenu(){
			pid=$("#pid").val();
			if(pid != null && pid != ""){
				$("#pid").val(pid);
				 $("input[name='menuck']").each(function(){ 
					 	var idName =$("#pid").val()+";;;"+$("#parnentMname").val();
		    			if($(this).val() == idName){
		    				$(this).attr("checked", true);
		    			} 
		    	 }); 
			}
			
		}
		
		//点击保存
		function addForm(){
			if($("#mname").val()==""){
				layer.open({
 	    			skin: 'layui-layer-molv',
 	    			closeBtn: 0,	    			  
 	    			title: false,
 	    			content:'菜单名称不能为空！' ,
 	    			anim: 5 ,
 	    			btn: ['关闭']
     		  	 }); 
				$("#mname").focus();
				return;
			}
			var valueState=$('input[name="state"]:checked').val();
			if(iecs(valueState)){
				layer.open({
 	    			skin: 'layui-layer-molv',
 	    			closeBtn: 0,	    			  
 	    			title: false,
 	    			content:'请选择状态！' ,
 	    			anim: 5 ,
 	    			btn: ['关闭']
     		  	 }); 
    			return;
    		}
			menu = uiToData("form_vo",menu);
    		var method = "add";
    		if($("#id").val() != ""){
				method = "modify";	
			}
    		doAjaxPost('<%=request.getContextPath()%>/menu.action?act='+method,{strJson : JSON.stringify(menu)},callDataSave);
		}
		function callDataSave(data){
			if(data.msg == 'true'){
                var url = '<%=request.getContextPath()%>/menu.action?act=forList';
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
		
		//点击返回
		function backTable(){
			history.go(-1);
		}
		
    </script>
</body>
</html>
