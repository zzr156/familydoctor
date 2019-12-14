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
	                <div class="ibox-title">
	                	<h5 id="userAddTit"></h5>                
	                    <a class="label label-primary pull-right system users_list"  onclick="backTable();">返回</a>
	                </div>
	                <div class="ibox-content">
							<form role="form" id="form_vo"  class="form-horizontal">
							        <input type="hidden" id="userId" pofield="userId" name="userId" value="${vo.userId}" />
			          				<input type="hidden" id="handle"  name="handle" value="${handle}" />
			                		<div class="form-group">
			                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>账号：</label>
			                            <div class="col-sm-4">					                           
			                                <input id="account" name="account" pofield="account" type="text" class="form-control">
			                            </div>
			                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>用户名：</label>
			                            <div class="col-sm-4">
			                                  <input id="userName" name="userName" pofield="userName" type="text" class="form-control">
			                            </div>
			                        </div>
			                        <div class="form-group">
			                        	<label class="col-sm-2 control-label"><span class="text-danger">*</span>密码：</label>
			                            <div class="col-sm-4">					                           
			                                <input id="userPassword" name="userPassword" pofield="userPassword" type="password" class="form-control">
			                            </div>
			                            <label class="col-sm-2 control-label">签名密码：</label>
			                            <div class="col-sm-4">
			                                  <input id="userSignaturePwd" name="userSignaturePwd" pofield="userSignaturePwd" type="password" class="form-control">
			                            </div>
			                        </div>
			                        <div class="form-group">
			                        	<label class="col-sm-2 control-label">部门：</label>
			                        	<div class="col-sm-4">
				                        	<div class="input-group">
                                       			<input type="text" class="form-control" id="deptName" name="deptName" pofield="deptName" type="text" readonly="readonly"> 
                                       				<input type="hidden" pofield="cdDept" id="deptId"  />
													<input type="hidden" pofield="cdDeptId" id="cdDeptId"/>
                                       			<span class="input-group-btn"> 
                                       				<button type="button" class="btn btn-primary"  data-toggle="modal" data-target="#myModalCdUserDept" onclick="ChangeDateToUser()">选择部门</button>
                                       			</span>
                                   			</div>
			                        	</div>
			                        	<label class="col-sm-2 control-label">职位：</label>
			                            <div class="col-sm-4"  id="postid">
			                            
			                            </div>
			                        </div>
			                         <div class="form-group">
			                        	<label class="col-sm-2 control-label"><span class="text-danger">*</span>性别：</label>
			                            <div class="col-sm-4" id="userSex">	
			                            						                           
			                            </div>
			                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>在职状态：</label>
			                            <div class="col-sm-4" id="workState">
			                            	
			                            </div>
			                        </div>
			                        <div class="form-group">
			                        	<label class="col-sm-2 control-label">卡号：</label>
			                            <div class="col-sm-4">					                           
			                                <input id="cardNum" name="cardNum" pofield="cardNum" type="text" class="form-control">
			                            </div>
			                            <label class="col-sm-2 control-label">手机：</label>
			                            <div class="col-sm-4">
			                                  <input id="mobilePhone" name="mobilePhone" pofield="mobilePhone" type="text" class="form-control">
			                            </div>
			                        </div>
			                        <div class="form-group">
			                        	<label class="col-sm-2 control-label">家庭电话：</label>
			                            <div class="col-sm-4">					                           
			                                <input id="homeTelephone" name="homeTelephone" pofield="homeTelephone" type="text" class="form-control">
			                            </div>
			                            <label class="col-sm-2 control-label">办公电话：</label>
			                            <div class="col-sm-4">
			                                  <input id="officePhone" name="officePhone" pofield="officePhone" type="text" class="form-control">
			                            </div>
			                        </div>
			                        <div class="form-group">
			                        	<label class="col-sm-2 control-label">电子邮件：</label>
			                            <div class="col-sm-4">					                           
			                                <input id="email" name="email" pofield="email" type="text" class="form-control">
			                            </div>
			                            <label class="col-sm-2 control-label">排序：</label>
			                            <div class="col-sm-4">
			                                  <input id="userSort" name="userSort" pofield="userSort" type="text" class="form-control">
			                            </div>
			                        </div>
			                        <div class="form-group">
			                        	<label class="col-sm-2 control-label">用户编码：</label>
			                            <div class="col-sm-4">					                           
			                                <input id="userNum" name="userNum" pofield="userNum" type="text" class="form-control">
			                            </div>
			                            <label class="col-sm-2 control-label">权限：</label>
			                            <div class="col-sm-4" id="roleid">
			                            
			                            </div>
			                       </div>
			                       <div class="form-group">
			                        	<label class="col-sm-2 control-label">电子签名：</label>
			                            <div class="col-sm-4">					         
			                            	<input  type="file" id="image" name="image" onchange="addfile();"/>                  
			                            </div>
			                            <label class="col-sm-2 control-label">当前签名：</label>
			                            <div class="col-sm-4">
			                            		 <div id="simage"></div>
												<input type="hidden" id="userFilePath" name="userFilePath" pofield="userFilePath" />
												<input type="hidden" id="userFileName" name="userFileName" pofield="userFileName" />
			                            </div>
			                       </div>
								   <div class="text-center btnBar">
			                        	<a id="handleMethod" class="btn btn-primary system users_add" onclick="saveTable();">保存</a>                       	                           
			                       </div>
						      </form>
						 </div>
	
	               </div>
	          </div>
	    </div>
	</div>
    <jsp:include page="user_ChangePeople.jsp" flush="false" />
    <script type="text/javascript">
    	var vo={};
		//页面加载完成时启动的方法
		$(document).ready(function() {
			findDept();//部门 树形
			findCmmInit();//初始化
		});
		
		//统一初始化
		function findCmmInit(){
			doAjaxPost('<%=request.getContextPath()%>/users.action?act=findCmmInit',{},callDataToInit);
		}
		function callDataToInit(data){
			if(data.map != null){
				//性别
				if(data.map.sex != null){
					  $("#userSex").html("");
			    	  $.each(data.map.sex, function(k, v) {
				        	dataUiCodeGroup("radio","userSex",v.codeTitle,v.codeValue);
				      });
			    	  if($("#handle").val() == "add"){
							$("input[name='userSex'][value=1]").attr("checked",true); 
					  }
				}
				//状态
				if(data.map.workState != null){
					  $("#workState").html("");
			    	  $.each(data.map.workState, function(k, v) {
				        	dataUiCodeGroup("radio","workState",v.codeTitle,v.codeValue);
				      });
			    	  if($("#handle").val() == "add"){
							$("input[name='workState'][value=1]").attr("checked",true); 
					  }
				}
				//职位
				if(data.map.posittion != null){
					$("#postid").html("");
			        $.each(data.map.posittion, function(k, v) {
			        	dataUiCodeGroup("checkbox","postid",v.account,v.id);
			        });
				}
				//角色
				if(data.map.role != null){
					$("#roleid").html("");
			        $.each(data.map.role, function(k, v) {
			        	dataUiCodeGroup("checkbox","roleid",v.rname,v.id);
			        });
				}
				onInit();//回选
			}
		} 
		
		//加载完成时，查询数据，记载到表格的方法
		function onInit() {
			if($("#userId").val() != ""){
				$("#userPassword").attr("readonly","readonly")//将input元素设置为readonly 　
				$("#userSignaturePwd").attr("readonly","readonly")//将input元素设置为readonly 　
				doAjaxPost('<%=request.getContextPath()%>/users.action?act=jsonByOne',{userId:$("#userId").val()},callDataToUser);
			}
			if($("#handle").val() == "look"){
				$("#handleMethod").hide();
				$("#userAddTit").text("查看用户信息");
			}else if($("#handle").val() == "modify"){
				$("#handleMethod").text("修改");
				$("#userAddTit").text("修改用户信息");
			}else if($("#handle").val() == "add"){
				$("#userAddTit").text("新增用户信息");
			}
			
		}
		
		function callDataToUser(data){
			dataToUi(data,"form_vo");
			//权限回显
            //清空
			var roleids = document.getElementsByName("roleid");
            for(var i=0;i<roleids.length;i++){
                if(roleids[i].checked){
                    roleids[i].checked = false;
                }
            }
            var roleidss = document.getElementsByName("roleid");
            for(var i=0;i<roleidss.length;i++){
                if(data.roleid != null){
                    for(var j=0;j<data.roleid.length;j++){
						if($(roleidss[i]).val()==data.roleid[j]){
                            roleidss[i].checked = true;
						}
                    }
                }
            }

			if(data.userFilePath != "" && data.userFilePath != null){
				$('#simage').append('<img width="200" height="100" src="<%=request.getContextPath()%>/image.action?act=getUrlImage&url='+data.userFilePath+'"/>')	
			}
			var deptId = $("#cdDeptId").val();
			if(deptId != null && deptId != ""){
				$("#deptId").val(deptId);
			}
			
		}
		
		function ChangeDateToUser(){
			if($("#cdDeptId").val() != null && $("#cdDeptId").val() != ""){
				var idName = $("#cdDeptId").val()+";;;"+$("#deptName").val();
				 $("input[name='changeCdUserDept']").each(function(){ 
		    			if(idName.indexOf($(this).attr("value"))!= -1){
		    				$(this).attr("checked", true);
		    			} 
		    	 }); 
			}
    	}
		
		//点击返回
		function backTable(){
			history.go(-1);
		}
		
		
	  //上传文件
		function addfile(){
			var pic = $("#image").val().split(".");
			if(pic[1]==undefined || (pic[1]!='jpg'&&pic[1]!='jpeg'&&pic[1]!='png')){
				layer.open({
	    			skin: 'layui-layer-molv',
	    			closeBtn: 0,	    			  
	    			title: false,
	    			content:'上传照片格式不正确（照片格式应该为jpg/jpeg/png）!' ,
	    			anim: 5 ,
	    			btn: ['关闭']
    			});  
				return false;
			}
			$.ajaxFileUpload({
	                url: '<%=request.getContextPath()%>/image.action?act=addImage', //用于文件上传的服务器端请求地址
	                secureuri: false, //是否需要安全协议，一般设置为false
	                fileElementId: 'image', //文件上传域的ID
	                dataType: 'JSON', //返回值类型 一般设置为json
	                success: function (data, status)  //服务器成功响应处理函数
	                {
	                	var da=JSON.parse(data);
	                	$("#userFileName").val(da.vo.fileName);
	                	$("#userFilePath").val(da.vo.filePath);
	                	$('#simage').html("");
	                	$('#simage').append('<img width="200" height="100" src="<%=request.getContextPath()%>/image.action?act=getUrlImage&url='+da.vo.filePath+'"/>')
	                	
	                },
	                error: function (data, status, e)//服务器响应失败处理函数
	                {
	                    alert(e);
	                }
	            }
	        )
	        return false;
		}
		//保存的方法
		function saveTable() {
			if(iecs($("#account").val())){
				layer.open({
	    			skin: 'layui-layer-molv',
	    			closeBtn: 0,	    			  
	    			title: false,
	    			content:'账号不能为空！' ,
	    			anim: 5 ,
	    			btn: ['关闭']
    			});  
	    		$("#account").focus();
	    		return; 
	    	}
		    	if(iecs($("#userName").val())){
		    	  layer.open({
		    			skin: 'layui-layer-molv',
		    			closeBtn: 0,	    			  
		    			title: false,
		    			content:'用户名不能为空！' ,
		    			anim: 5 ,
		    			btn: ['关闭']
	    		  });  
	    		 $("#userName").focus();
	    		 return;
	    	}
	    	if(iecs($("#userPassword").val())){
	    		layer.open({
	    			skin: 'layui-layer-molv',
	    			closeBtn: 0,	    			  
	    			title: false,
	    			content:'密码不能为空！' ,
	    			anim: 5 ,
	    			btn: ['关闭']
    		  	}); 
	    		$("#userPassword").focus();
	    		return; 
	    	}
	    	/*if(iecs($("#userSignaturePwd").val())){
	    		 layer.open({
	    			skin: 'layui-layer-molv',
	    			closeBtn: 0,
	    			title: false,
	    			content:'签名密码不能为空！' ,
	    			anim: 5 ,
	    			btn: ['关闭']
    		  	 });
	    		 $("#userSignaturePwd").focus();
	    		 return;
	    	}*/
	    	/*if(iecs($("#deptName").val())){
	    		 layer.open({
	    			skin: 'layui-layer-molv',
	    			closeBtn: 0,	    			  
	    			title: false,
	    			content:'请选择部门！' ,
	    			anim: 5 ,
	    			btn: ['关闭']
    		  	 }); 
	    		 return;
	    	}*/
	    	if(necs($("#mobilePhone").val())){
	    		if(!isCheckPhone($("#mobilePhone").val())){
	    			 layer.open({
	 	    			skin: 'layui-layer-molv',
	 	    			closeBtn: 0,	    			  
	 	    			title: false,
	 	    			content:'手机号码有误,请重新填写！' ,
	 	    			anim: 5 ,
	 	    			btn: ['关闭']
	     		  	 }); 
	    			 $("#mobilePhone").focus();
	    			 return;
	    		}
	    	}
	    	/*else{
	    		layer.open({
	    			skin: 'layui-layer-molv',
	    			closeBtn: 0,
	    			title: false,
	    			content:'手机号码不能为空！' ,
	    			anim: 5 ,
	    			btn: ['关闭']
    		  	 });
	    		 return;
	    	}*/
	    	/*if(iecs($("#userNum").val())){
	    		 layer.open({
 	    			skin: 'layui-layer-molv',
 	    			closeBtn: 0,	    			  
 	    			title: false,
 	    			content:'用户编码不能为空！' ,
 	    			anim: 5 ,
 	    			btn: ['关闭']
     		  	 }); 
	    		 $("#userNum").focus();
	    		 return;
	    	}*/
			vo = uiToData("form_vo",vo);
			var method = "add";
			if($("#userId").val() != ""){
				method = "modify";	
			}
//			alert(JSON.stringify(vo));
			doAjaxPost('<%=request.getContextPath()%>/users.action?act='+method,{strJson : JSON.stringify(vo)},callDataSave);
		}
		
		function callDataSave(data){
			if(data.msg == 'true'){
                var url = '<%=request.getContextPath()%>/users.action?act=forList';
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
