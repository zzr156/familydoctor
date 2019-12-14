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
	                	<h5 id="deptAddTit"></h5>                
	                    <a class="label label-primary pull-right system dept_list"  onclick="backTable();">返回</a>
	                </div>
	                <div class="ibox-content">
							<form role="form" id="form_vo"  class="form-horizontal">
									<input type="hidden" id="id" pofield="id" name="id" value="${vo.id}" />
									<input type="hidden" id="handle"  name="handle" value="${handle}" />
			    					<input type="hidden" id="deptAddzi" name="deptAddzi" value="${deptAddzi}">
			                		<div class="form-group">
			                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>部门编号：</label>
			                            <div class="col-sm-4">					                           
			                                <input id="snumber" name="snumber" pofield="snumber" type="text" class="form-control">
			                            </div>
			                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>部门名称：</label>
			                            <div class="col-sm-4">
			                                  <input id="sname" name="sname" pofield="sname" type="text" class="form-control">
			                            </div>
			                        </div>
			                        <div class="form-group">
			                        	<label class="col-sm-2 control-label"><span class="text-danger">*</span> 所属部门或机构：</label>
			                        	<div class="col-sm-4">
			                        		<div class="input-group" id="dept">
				                        			<input type="text" id="deptName" pofield="ParnentSname"  readonly="readonly" class="form-control" />               		
													<input type="hidden" pofield="cdDept" id="cdDept" />
													<input type="hidden" pofield="sid" id="deptId"/>
													<input type="hidden" pofield="cdDeptId" id="cdDeptId"/>
													<input type="hidden" pofield="deptSid" id="deptSid"/>
                                       			<span class="input-group-btn"> 
                                       				<button type="button" class="btn btn-primary"  data-toggle="modal" data-target="#myModalCdUserDept" onclick="chooseDept()">选择部门</button>
                                       			</span>
                                   			</div>
			                        	</div>
			                        	<label class="col-sm-2 control-label">是否启用：</label>
			                            <div class="col-sm-4"  id="state">
			                            
			                            </div>
			                        </div>
			                         <div class="form-group">
			                        	<label class="col-sm-2 control-label"><span class="text-danger">*</span>排序：</label>
			                            <div class="col-sm-4">	
			                            		<input pofield="sort" id="sort" name="sort" type="text" class="form-control"/>			                           
			                            </div>
			                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>所属角色：</label>
			                            <div class="col-sm-4" id="roleid">	
			                            </div>
			                        </div>
			                        <div class="form-group" id="unitOrdept">
			                        	<label class="col-sm-2 control-label">单位类型：</label>
			                            <div class="col-sm-4" id="unitType">	
			                            </div>
			                            <label class="col-sm-2 control-label">部门类别：</label>
			                            <div class="col-sm-4" id="deptType">	
			                            </div>
			                        </div>
			                        <div class="form-group" id="cityOrArea">
			                        	<label class="col-sm-2 control-label">市：</label>
			                            <div class="col-sm-4" id="city">	
			                            	<select id="citySelect" onchange="findArea()" class="form-control"></select>
			                            </div>
			                            <label class="col-sm-2 control-label">区：</label>
			                            <div class="col-sm-4" id="area">	
			                            	<select id="areaSelect" class="form-control">
												<option>--请选择上级市--<option>
											</select>
			                            </div>
			                        </div>
								   <div class="text-center btnBar">
			                        	<a id="handleMethod" class="btn btn-primary system dept_add" onclick="addForm();">保存</a>                       	                           
			                       </div>
						      </form>
						 </div>
	               </div>
	          </div>
	    </div>
	</div>
	<jsp:include page="/intercept/cd/user/user_ChangePeople.jsp" flush="false" />
    <script type="text/javascript">
	    var vo={};
	    var sName="";
	    var handle=$("#handle").val();
		//页面加载完成时启动的方法
		$(document).ready(function() {
			findDept();
			findCmmInit();//初始化
		});
		
		//统一初始化
		function findCmmInit(){
			doAjaxPost('<%=request.getContextPath()%>/dept.action?act=findCmmInit',{},callDataToInit);
		}
		function callDataToInit(data){
			if(data.map != null){
				//角色
				if(data.map.role != null){
					$("#roleid").html("");
			        $.each(data.map.role, function(k, v) {
			        	dataUiCodeGroup("checkbox","roleid",v.rname,v.id);
			        });
				}
				//部门类别
				if(data.map.deptType != null){
					$("#deptType").html("");
			    	$.each(data.map.deptType, function(k, v) {
			    		dataUiCodeGroup("radio","deptType",v.codeTitle,v.codeValue);
			        });
			    	if($("#handle").val() == "add"){
						$("input[name='deptType'][value=0]").attr("checked",true); 
				  	} 
			    	if($("#handle").val() == "addzi"){
						$("input[name='deptType'][value=1]").attr("checked",true); 
				  	} 
			    	$("#deptType").find("input[name='deptType']").attr("disabled", true); 
			    	//onIn("deptType","1");
			    	
				}
				//单位类型
				if(data.map.unitType != null){
					$("#unitType").html("");
					$.each(data.map.unitType, function(k, v) {
			    		dataUiCodeGroup("radio","unitType",v.codeTitle,v.codeValue);
			        });
					if($("#handle").val() == "add"){
						$("input[name='unitType'][value=0]").attr("checked",true); 
				  	} 
					// onIn("unitType","1");
				}
				//状态
				if(data.map.enable != null){
					$("#state").html("");
			    	$.each(data.map.enable, function(k, v) {
			    		dataUiCodeGroup("radio","state",v.codeTitle,v.codeValue);
			        });
			    	if($("#handle").val() == "add"||$("#handle").val() == "addzi"){
						$("input[name='state'][value=1]").attr("checked",true); 
				  	} 
			       // onIn("state","1");
				}
				//城市
				if(data.map.city != null){
					$("#citySelect").html("");
					var option = document.createElement('option');
			    	option.setAttribute("value","");
			    	option.innerText = "--请选择--";
			    	document.getElementById("citySelect").appendChild(option);
					$.each(data.map.city,function(k,v){
						dataUiCodeGroup("select","citySelect",v.areaSname,v.id);
					})
				}
				onInit();//回选
			}
		} 
		
		//加载区
		function findArea(){
			var option=$("#citySelect option:selected");
			var pid=option.val();
			doAjaxPost('<%=request.getContextPath()%>/address.action?act=findCmmByPid',{pid : pid},callDataToArea); 
		}
		function callDataToArea(data){
			$("#areaSelect").html("");
			var option = document.createElement('option');
	    	option.setAttribute("value","");
	    	option.innerText = "--请选择--";
	    	document.getElementById("areaSelect").appendChild(option);
			$.each(data,function(k,v){
				dataUiCodeGroup("select","areaSelect",v.areaSname,v.id);
			})
			if(sName!=""){
				$.each($("#areaSelect").find("option"),function(k,v){
					if($(v).val()==sName){
						$(v).attr("selected","selected");
					}
				})
			}
		}
		
		//调用默认方法
		function onIn(name,value){
			if(handle=="add"||handle=="addzi"){
				defaultToRadio(name,value);
				//添加机构，部门类型固定为机构单选
				if(handle=="add"&&name=="deptType"){
					$("input:radio[name='"+name+"']").each(function(){
						   if($(this).attr("value") == '0'){
							   $(this).attr("checked", true);
						   }
			     	});
				}
				//添加部门时，限制部门了类型单选
				if(handle=="addzi"&&name=="deptType"){
					$("input:radio[name='"+name+"']").each(function(){
						   if($(this).attr("value") == '1'){
							   $(this).attr("checked", true);
						   }
			     	});
				}
				$("#deptType").find("input[name='deptType']").attr("disabled", true); 
			} 
			if(handle=="modify"){
				$("#deptType").find("input[name='deptType']").attr("disabled", true);
				$("#unitType").find("input[name='unitType']").attr("disabled", true);
			}
		}
		
		function onInit(){
			var deptId=$("#id").val();
			if($("#id").val() != ""&&handle=="modify"){
				doAjaxPost('<%=request.getContextPath()%>/dept.action?act=jsonByOne',{id:$("#id").val()},callToDept);
			}
			if(handle=="add"){
				$("#deptAddTit").text("新增顶级部门");
				$("#dept").hide();
			}else{
				$("#dept").show();
			}
			if(handle=="addzi"){
				$("#deptAddTit").text("新增子级部门");
				$("#cityOrArea").hide();
				$("#unitOrdept").hide();
				var id = $("#deptAddzi").val();
				doAjaxPost('<%=request.getContextPath()%>/dept.action?act=jsonByOne',{id:id},function(dataDeptSid){
					var idName = dataDeptSid.id+";;;"+dataDeptSid.sname;
					 $("input[name='changeDeptUser']").each(function(){ 
			    			if($(this).val() == idName){
			    				$(this).attr("checked", true);
			    			} 
			    	 }); 
					 //根据父级的单位类型限制子部门单位类型
					var type=dataDeptSid.unitType;
					$("input:radio[name='unitType']").each(function(){
						   if($(this).attr("value") == type){
							   $(this).attr("checked", true);
						   }
			     	}); 
					$("#unitType").find("input[name='unitType']").attr("disabled", true); 
					var deptAddzi = idName.split(";;;");
		        	$('#deptId').val(deptAddzi[0]);
		        	$('#deptName').val(deptAddzi[1]);
				});
			}else if(handle=="look"){
				$("#deptAddTit").text("查看部门");
				$("#handleMethod").hide();
			}else if(handle=="modify"){
				$("#handleMethod").text("修改");
				$("#deptAddTit").text("修改部门");
			}
		}
		
		function callToDept(data){
			dataToUi(data,"form_vo");
			var deptId = $("#deptId").val();
			var deptSid = $("#deptSid").val();
			if(deptId != null && deptId != ""){
				$("#deptId").val(deptId); 
			}else if(deptSid != null && deptSid != ""){
				$("#deptId").val(deptSid); 
			}
			if(data.parnentSname==""||data.parnentSname==null){
				$("#dept").hide();
			}else{
				$("#deptName").val(data.parnentSname);
				$("#dept").show();
			}
			deptId=$("#deptId").val();
			
			if(data.city!=null){
				$.each($("#citySelect").find("option"),function(k,v){
					if($(v).val()==data.city){
						$(v).attr("selected","selected");
						findArea();
					}
				})
			}
			if(data.area!=null){
				sName=data.area;
			}
			var type=data.unitType;
			$("input:radio[name='unitType']").each(function(){
				   if($(this).attr("value") == type){
					   $(this).attr("checked", true);
				   }
	     	}); 
			if(data.deptType=="1"){
				$("#unitOrdept").hide();
				$("#cityOrArea").hide();
			}
		}
		//部门选择回选
		function chooseDept(){
			var deptId=$("#deptId").val();
			if(deptId != null && deptId != ""){
				var idName =$("#deptId").val()+";;;"+$("#deptName").val();
				 $("input[name='changeCdUserDept']").each(function(){ 
					 if(idName.indexOf($(this).attr("value"))!= -1){
		    				$(this).attr("checked", true);
		    			} 
		    	 }); 
			}
		}
    	//点击保存
    	function addForm(){
    		var valueType=$('input[name="deptType"]:checked').val();
    		var valueState=$('input[name="state"]:checked').val();
    		var unitType=$('input[name="unitType"]:checked').val();
    		var city=$("#citySelect option:selected");
    		if(iecs($("#snumber").val())){
    			 layer.open({
 	    			skin: 'layui-layer-molv',
 	    			closeBtn: 0,	    			  
 	    			title: false,
 	    			content:'部门编号不能为空！' ,
 	    			anim: 5 ,
 	    			btn: ['关闭']
     		  	 }); 
    			$("#snumber").focus();
    			return;
    		}
    		if(iecs($("#sname").val())){
    			 layer.open({
  	    			skin: 'layui-layer-molv',
  	    			closeBtn: 0,	    			  
  	    			title: false,
  	    			content:'部门名称不能为空！' ,
  	    			anim: 5 ,
  	    			btn: ['关闭']
      		  	 }); 
    			$("#sname").focus();
    			return;
    		}
    		
    		if(handle=="add"||valueType=="0"){
    			if(iecs(valueType)){
    				layer.open({
      	    			skin: 'layui-layer-molv',
      	    			closeBtn: 0,	    			  
      	    			title: false,
      	    			content:'请选择部门类别！' ,
      	    			anim: 5 ,
      	    			btn: ['关闭']
          		  	 }); 
        			return;
        		}
    			if(iecs(unitType)){
    				layer.open({
      	    			skin: 'layui-layer-molv',
      	    			closeBtn: 0,	    			  
      	    			title: false,
      	    			content:'请选择单位类型！' ,
      	    			anim: 5 ,
      	    			btn: ['关闭']
          		  	 }); 
        			return;
        		}
    			/*if(iecs(city.val())){
    				layer.open({
      	    			skin: 'layui-layer-molv',
      	    			closeBtn: 0,	    			  
      	    			title: false,
      	    			content:'请选择地区市！' ,
      	    			anim: 5 ,
      	    			btn: ['关闭']
          		  	 }); 
    				return;
    			}	*/
    		}
    		if(iecs(valueState)){
    			layer.open({
  	    			skin: 'layui-layer-molv',
  	    			closeBtn: 0,	    			  
  	    			title: false,
  	    			content:'请选择启用情况！' ,
  	    			anim: 5 ,
  	    			btn: ['关闭']
      		  	 }); 
    			return;
    		}
    		vo = uiToData("form_vo",vo);
    		var option1=$("#city option:selected"); 
    		var option2=$("#area option:selected");
    		if(option1.val()!=""){
    			vo["city"]=option1.val();
    		}
    		if(option2.val()!=""){
    			vo["area"]=option2.val();
    		}
    		var method = "add";
    		if($("#id").val() != ""){
				method = "modify";	
			}
    		doAjaxPost('<%=request.getContextPath()%>/dept.action?act='+method,{strJson : JSON.stringify(vo),handle : handle},callDataSave);
    	}
    	function callDataSave(data){
			if(data.msg == 'true'){
                var url = '<%=request.getContextPath()%>/dept.action?act=forList';
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
