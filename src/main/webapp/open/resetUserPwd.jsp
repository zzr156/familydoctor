<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	pageContext.setAttribute("path", path);
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ include file="/open/commonjs/tldHtml.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/open/commonjs/bootstrap/css/bootstrap.min.css">
<base href="<%=basePath%>">

<title>重设密码</title>

<script type="text/javascript">
	function loginLayoutValidate() {
		var userOldPassword = document.getElementById("userOldPassword");
		var userNewPassword = document.getElementById("userNewPassword");
		var userPwdOk = document.getElementById("userPwdOk");
		var fa = true;
		if (trim(userOldPassword.value) == "") {
			layer.open({
				skin: 'layui-layer-molv',
				closeBtn: 0,	    			  
				title: false,
				content:'请输入旧密码' ,
				anim: 5 ,
				btn: ['关闭']
				});
			userOldPassword.focus();
			userOldPassword.select();
			fa = false;
			return fa;
		}else if (trim(userNewPassword.value) == "") {
			//userNewPassword.value = "请输入新密码";
			layer.open({
				skin: 'layui-layer-molv',
				closeBtn: 0,	    			  
				title: false,
				content:'请输入新密码' ,
				anim: 5 ,
				btn: ['关闭']
				});
			userNewPassword.focus();
			userNewPassword.select();
			fa = false;
			return fa;
		} else if (trim(userPwdOk.value) == "") {
			//userPwdOk.value = "请输入新密码确认";
			layer.open({
				skin: 'layui-layer-molv',
				closeBtn: 0,	    			  
				title: false,
				content:'请输入新密码确认' ,
				anim: 5 ,
				btn: ['关闭']
				});
			userPwdOk.focus();
			userPwdOk.select();
			fa = false;
			return fa;
		}else if(trim(userNewPassword.value).length != trim(userPwdOk.value).length){
			layer.open({
				skin: 'layui-layer-molv',
				closeBtn: 0,	    			  
				title: false,
				content:'新密码与新密码确认不一致,请重新输入!' ,
				anim: 5 ,
				btn: ['关闭']
				});
			userNewPassword.select();
			fa = false;
			return fa;
		}
			return true;
		
	}
	//去除字符串两边空格
	function trim(str) {
		return str.replace(/^\s+/, '').replace(/\s+$/, '');
	}
</script>
</head>

<body>
<div class="wrapper wrapper-content">
	<div class="col-sm-12">
		<s:form  role="form" action="users" method="post" id="listForm" name="listForm">
		<s:hidden id="act" name="act" />
		<input type="hidden" id="paramId" name="paramId" value="<%=request.getAttribute("paramId")%>" />
		<div class="ibox float-e-margins">
                <div class="ibox-title">                 
                	<h5>重置密码</h5>                                    
                </div>
                <div class="ibox-content">
                	<div class="form-group clear">
                		<label class="col-sm-2 control-label col-sm-offset-3">原密码:</label>
                		<div class="col-sm-4">
                			<s:password name="userOldPassword" class="form-control" id="userOldPassword"/>
                		</div>
                	</div>
                	<div class="form-group clear">
                		<label class="col-sm-2 control-label col-sm-offset-3">新密码：</label>
                		<div class="col-sm-4">
                			<s:password class="form-control" name="userNewPassword" id="userNewPassword"/>
                		</div>
                	</div>
                	<div class="form-group clear">
                		<label class="col-sm-2 control-label col-sm-offset-3">新密码确认:</label>
                		<div class="col-sm-4">
                			<s:password class="form-control" name="userPwdOk" id="userPwdOk"/>
                		</div>
                	</div>
                	<div class="text-center" style="border-top: 1px dashed #D7D7D7;padding-top: 10px;">
                        	<input class="btn btn-primary"	type="button" value="保存" onclick="act.value='resetPwd';if(loginLayoutValidate())submit()"
                        	                                        
                    </div>
                </div>
		</div>
		</s:form>
	</div>
</div>
	
</body>
</html>
