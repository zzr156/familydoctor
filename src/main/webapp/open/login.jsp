<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<%@ include file="/open/commonjs/tldHtml.jsp"%>
<base href="<%=basePath%>">
<title>家庭医生签约管理系统 - 登录</title>
<script>if(window.top !== window.self){ window.top.location = window.location;}</script>


<script type="text/javascript">
function eventKeyCode(){
    if(event.keyCode==13){
    	var yzm = document.getElementById("yzm").value;
		 var account = document.getElementById("account").value;
		 var userPassword = document.getElementById("userPassword").value;
		 var msg ='';

		 if(account == null || account == ''){
			 msg += "用户名不能为空!<br/>";
		 }
		 if(userPassword == null || userPassword == ''){
			 msg += "密码不能为空!<br/>";
		 }
		 if(yzm == null || yzm == ''){
			msg += "验证码不能为空!<br/>";
		 }


		 if(msg != null && msg != ''){
			 parent.layer.alert(msg, {
	   			    skin: 'layui-layer-lan'
	   			    ,closeBtn: 0
	   			    ,shift: 4 //动画类型
	   			 });
			 return false;
		 }else{
			//$("#login").submit();
			 //return true;
			 doAjaxPostNotLoad('<%=request.getContextPath()%>/login.action?act=login',{yzm : yzm,account : account,userPassword : userPassword},	function(data){

				  if(data.msg=="true"){
						window.location.href = '<%=request.getContextPath()%>/login.action?act=indexCmm';

				 }else{
						layer.open({
							 skin: 'demo-class',
							 title: false,
							 content: data.msg ,
							 anim: 5,
							 closeBtn: 0,
							 end: function () {
					                location.reload();
					            }
							});
					}

				}
			      );
		 }
    }

}

 function checkData(){
	 var yzm = document.getElementById("yzm").value;
	 var account = document.getElementById("account").value;
	 var userPassword = document.getElementById("userPassword").value;
	 var msg ='';

	 if(account == null || account == ''){
		 msg += "用户名不能为空!<br/>";
	 }
	 if(userPassword == null || userPassword == ''){
		 msg += "密码不能为空!<br/>";
	 }
	 if(yzm == null || yzm == ''){
		msg += "验证码不能为空!<br/>";
	 }

	 if(msg != null && msg != ''){
		 layer.open({
			 skin: 'demo-class',
			 title: false,
			 content: msg ,
			 anim: 5,
			 closeBtn: 0,
			});
		 return false;
	 }else{
		//$("#login").submit();
		//return true;
		doAjaxPostNotLoad('<%=request.getContextPath()%>/login.action?act=login',{yzm : yzm,account : account,userPassword : userPassword},	function(data){
			if(data.msg=="true"){
				window.location.href = '<%=request.getContextPath()%>/login.action?act=indexCmm';
			}else{
				layer.open({
					 skin: 'demo-class',
					 title: false,
					 content: data.msg ,
					 anim: 5,
					 closeBtn: 0,
					 end: function () {
			                location.reload();
			            }
					});
			}

		}
	      );
	 }
}

 function callCheckData(){
	 return true;
 }
 var InterValObj; //timer变量，控制时间
 var count = 60; //间隔函数，1秒执行
 var curCount;//当前剩余秒数

 function sendMessage() {
	 var account = document.getElementById("account").value;
	 var userPassword = document.getElementById("userPassword").value;
	 var msg ='';

	 if(account == null || account == ''){
		 msg += "用户名不能为空!<br/>";
	 }
	 if(msg != null && msg != ''){
		 layer.open({
			 skin: 'demo-class',
			 title: false,
			 content: msg ,
			 anim: 5,
			 closeBtn: 0,
			});
	 }else{
		 curCount = count;
	 　　		//设置button效果，开始计时
	      $("#btnSendCode").attr("disabled", "true");
	      $("#btnSendCode").val("请在" + curCount + "秒内输入验证码");
	      InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
	      doAjaxPostNotLoad('<%=request.getContextPath()%>/login.action?act=loginIdentifyCode',{account : account},function(data){
	    	  if(data.msg=="false"){
	    		  layer.open({
	    				 skin: 'demo-class',
	    				 title: false,
	    				 content: "该用户不存在或密码错误,请重新输入!" ,
	    				 anim: 5,
	    				 closeBtn: 0,
	    				});
	    	  }else{
	    		  alert(data.msg);
	    	  }

	      });
	 }
   　
 }

 //timer处理函数
 function SetRemainTime() {
      if (curCount == 0) {
          window.clearInterval(InterValObj);//停止计时器
          $("#btnSendCode").removeAttr("disabled");//启用按钮
          $("#btnSendCode").val("重新发送验证码");
      }
      else {
          curCount--;
          $("#btnSendCode").val("请在" + curCount + "秒内输入验证码");
      }
  }
</script>
</head>
<body onkeyup="eventKeyCode()" class="gray-bg loginBody">
	<div class="logoName logo">
		<%--家&nbsp;&nbsp;庭&nbsp;&nbsp;医&nbsp;&nbsp;生&nbsp;&nbsp;后&nbsp;&nbsp;台&nbsp;&nbsp;管&nbsp;&nbsp;理&nbsp;&nbsp;系&nbsp;&nbsp;统--%>
		家庭医生签约管理系统
	</div>
	<div style=" background: #2db9ff; width: 100%; margin-top: 100px; box-shadow:inset 0 1px 2px #31a8eb;" class="text-center loginscreen">
		<div class="container text-center row" style="margin: 0 auto;">
			<div class="col-sm-7 col-lg-6 hidden-xs" style="position: relative;">
				<div class="centerDiv" style="position: absolute; top:-80px; left:0;right:0;margin:auto;">
					<img src="<%=request.getContextPath()%>/open/images/login/Artboard.png">
				</div>
			</div>
			<div class="middle-box animated fadeInDown col-sm-5 col-lg-6" style="position: relative;">
				<%--<div class="centerDiv" style="position: absolute; top:-100px; left:0;right:0;margin:auto;">
                    <img src="<%=request.getContextPath()%>/open/images/img/by_login_pic.png">
                </div>--%>
				<div class="loginBox">
					<div class="waveBorder">
					</div>
					<div class="loginform">
						<input type="hidden"  id="vers" name="vers" value="<%=Calendar.getInstance().getTimeInMillis()%>"/>
						<div class="form-group">
							<input class="form-control"
								   value="CA用户名"
								   onfocus="if(value==defaultValue){value='';}" onblur="if(!value){value=defaultValue;}"
								   type="text" id="account" name="account"
								<%if(request.getAttribute("account")!=null){ %>
								   value=<%=request.getAttribute("account") %> <%}%> maxlength="12">
						</div>
						<div class="form-group">
							<input name="" type="text" value="请输入密码" class="form-control"  id="tx"  />
							<input name="userPassword" type="password" class="form-control" style="display:none;" id="userPassword" />
							<script type="text/javascript">
                                var tx = document.getElementById("tx"), pwd = document.getElementById("userPassword");
                                tx.onfocus = function(){
                                    if(this.value != "请输入密码") return;
                                    this.style.display = "none";
                                    pwd.style.display = "";
                                    pwd.value = "";
                                    pwd.focus();
                                }
                                pwd.onblur = function(){
                                    if(this.value != "") return;
                                    this.style.display = "none";
                                    tx.style.display = "";
                                    tx.value = "请输入密码";
                                }
							</script>
						</div>
						<div class="form-group">
							<div class="input-group">
								<input class="form-control" type="text" id="yzm" name="yzm">
								<span class="input-group-btn">
								<img
									src="<%=request.getContextPath()%>/kaptcha" id="tp" name="tp"
									alt="点击刷新" onClick="this.src=this.src+'?'+Math.random();" />
								<%-- <input id="btnSendCode" class="btn btn-primary" type="button" value="发送验证码" onclick="sendMessage()" />--%>
							</span>

							</div>
						</div>
						<div class="form-group">
							<!-- <span class="text-danger"><i class="fa fa-exclamation-circle"></i>错误信息提示</span> -->
							<p>&nbsp;<input type="hidden" name="act" value="login" id="act" />	</p>
						</div>
						<button type="button" class="btn btn-success block full-width m-b"
								onclick="checkData()">登 录</button>
					</div>
				</div>
			</div>
		</div>


		<%--<div class="middle-box animated fadeInDown">
		&lt;%&ndash;<div class="centerDiv">
			<img
				src="<%=request.getContextPath()%>/open/images/img/by_login_pic.png">
		</div>&ndash;%&gt;
		<div class="loginBox">
			<div class="waveBorder">
			</div>
			<div class="loginform">
					<input type="hidden"  id="vers" name="vers" value="<%=Calendar.getInstance().getTimeInMillis()%>"/>
					<div class="form-group">
						<input class="form-control"
						 value="CA用户名"
						 onfocus="if(value==defaultValue){value='';}" onblur="if(!value){value=defaultValue;}"
						 type="text" id="account" name="account"
						<%if(request.getAttribute("account")!=null){ %>
						 value=<%=request.getAttribute("account") %> <%}%> maxlength="10">
					</div>
					<div class="form-group">
						<input name="" type="text" value="请输入密码" class="form-control"  id="tx"  />
						<input name="userPassword" type="password" class="form-control" style="display:none;" id="userPassword" />
						<script type="text/javascript">
								var tx = document.getElementById("tx"), pwd = document.getElementById("userPassword");
								tx.onfocus = function(){
								if(this.value != "请输入密码") return;
									this.style.display = "none";
									pwd.style.display = "";
									pwd.value = "";
									pwd.focus();
								}
								pwd.onblur = function(){
									if(this.value != "") return;
									this.style.display = "none";
									tx.style.display = "";
									tx.value = "请输入密码";
								}
						</script>
					</div>
					<div class="form-group">
						<div class="input-group">
							<input class="form-control" type="text" id="yzm" name="yzm">
							<span class="input-group-btn">
								&lt;%&ndash; <img
									src="<%=request.getContextPath()%>/kaptcha" id="tp" name="tp"
									alt="点击刷新" onClick="this.src=this.src+'?'+Math.random();" />  &ndash;%&gt;
								<input id="btnSendCode" class="btn btn-primary" type="button" value="发送验证码" onclick="sendMessage()" />
							</span>

						</div>
					</div>
					<div class="form-group">
						<!-- <span class="text-danger"><i class="fa fa-exclamation-circle"></i>错误信息提示</span> -->
						<p>&nbsp;<input type="hidden" name="act" value="login" id="act" />	</p>
					</div>
					<button type="button" class="btn btn-primary block full-width m-b"
						onclick="checkData()">登 录</button>
			</div>
		</div>
	</div>--%>
	</div>
</body>


<!-- 消息框 -->
    <script>
	    var flag = "${empty msg}";
	    if(flag!="true"){
	    	 layer.open({
	    			skin: 'layui-layer-molv',
	    			closeBtn: 0,
	    			title: false,
	    			content:"${msg}" ,
	    			anim: 5 ,
	    			btn: ['关闭']
	    	});
	    }
    </script>
