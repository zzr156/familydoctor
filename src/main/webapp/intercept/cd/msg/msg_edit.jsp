<%@page import="java.text.SimpleDateFormat"%>
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
<title>新增消息</title>
<%@ include file="/open/commonjs/tldHtml.jsp"%>
<%@ include file="/open/commonjs/roleson.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/intercept/ueditor/ueditor.config.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/intercept/ueditor/ueditor.all.js"></script>

</head>
<body class="gray-bg">
<div class="wrapper wrapper-content">
	<div class="row">
		<div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                	<h5 id="msgAddTit"></h5>                
                    <a class="label label-primary pull-right system cdmsg_list"  onclick="backTable();">返回</a>
                </div>
                <div class="ibox-content">
						<s:form role="form" id="form_vo" method="get" class="form-horizontal">
						    	<input type="hidden" id="id" pofield="id" name="id" value="${vo.id}" />
						        <input type="hidden" id="handle"  name="handle" value="${handle}" />	
					                		<div class="form-group">
					                            <label class="col-sm-2 control-label"><span class="text-danger">*</span> 标题：</label>
					                            <div class="col-sm-4">					                           
					                                <input id="msgTitle" name="msgTitle" pofield="msgTitle" type="text" class="form-control">
					                            </div>
					                            <label class="col-sm-2 control-label"><span class="text-danger">*</span> 类别：</label>
					                            <div class="col-sm-4" id="msgType">
					                                <select id="msgTypeSelect" class="form-control">
		                    						</select>
					                            </div>
					                        </div>
					                        <div class="form-group">
					                        	<label class="col-sm-2 control-label"><span class="text-danger">*</span> 接收者：</label>
					                        	<div class="col-sm-10">
					                        	<div class="input-group">
                                        			<input type="text" class="form-control" id="userNames" name="userNames" pofield="userNames" type="text" readonly="readonly"> 
                                        			<input id="userCodes"class="form-control" name="userCodes" pofield="userCodes" type="hidden"  />
                                        			<span class="input-group-btn"> 
                                        				<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal">选择人员 </button> 
                                        			</span>
                                    			</div>
					                        	</div>
					                        </div>
					                        <div class="form-group">
					                        	<label class="col-sm-2 control-label">内容：</label>
					                        	<div class="col-sm-10">
					                        	<textarea rows="15" cols="110" id="msgText" name="msgText" pofield="msgText" style="width:100%;border: 1px solid #e5e6e7;"></textarea>
					                        	</div>
					                        </div>
					                        <div class="form-group">
					                            <label class="col-sm-2 control-label"> 管理员：</label>
					                            <div class="col-sm-4">					                               
					                                <input name="msgUserName" pofield="msgUserName" id="msgUserName" type="text" readonly="readonly" class="form-control"  value="${sessionUser.userName}" />
		                        					<input name="msgUserid" type="hidden" pofield="msgUserid" id="msgUserid" value="${sessionUser.userId}"/>
					                            </div>
					                            <label class="col-sm-2 control-label">发送时间：</label>
					                            <div class="col-sm-4">
					                                <input type="text" class="form-control" readonly="readonly" id="strMsgCreateDate" pofield="strMsgCreateDate" name="strMsgCreateDate"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="<%=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) %>">
					                            </div>
					                        </div>
						<div class="text-center btnBar">
                        	<a id="handleMethod" class="btn btn-primary system cdmsg_add" onclick="saveTable();">发送</a>                       	                           
                        </div>
					      </s:form>
					 </div>

                </div>
            </div>
        </div>
	</div>
</body>
<jsp:include page="msg_ChangePeople.jsp" flush="false" />
<script type="text/javascript">
	var vo={};
    /**起始加载**/
    $(function(){
       findUser();
       findCmmInit();//初始化
    });
  	//统一初始化
	function findCmmInit(){
		doAjaxPost('<%=request.getContextPath()%>/cdmsg.action?act=findCmmInit',{},callDataToInit);
	}
	function callDataToInit(data){
		if(data.map!=null){
			//消息类别
			if(data.map.msgType!=null){
				 $("#msgTypeSelect").html("");
		    	  $.each(data.map.msgType, function(k, v) {
			        	dataUiCodeGroup("select","msgTypeSelect",v.codeTitle,v.codeValue);
			      });
			}
			onInit();
		}
	}
    function onInit(){
    	if($("#id").val() != ""){
			doAjaxPost('<%=request.getContextPath()%>/cdmsg.action?act=jsonByOne',{id:$("#id").val()},callDataToCdmsg);
		}
    	if($("#handle").val() == "look"){
			$("#handleMethod").hide();
			$("#msgAddTit").text("查看个人消息");
		}else if($("#handle").val() == "modify"){
			$("#handleMethod").text("修改");
		}else if($("#handle").val() == "add"){
			$("#msgAddTit").text("新增个人消息");
		}
    }
    function callDataToCdmsg(data){
    	
		dataToUi(data,"form_vo");
		$("#msgTypeSelect").find("option[value='"+data.msgType+"']").attr("selected","selected");
	}
    //点击保存
    function saveTable(){
    	if(iecs($("#msgTitle").val())){
    		layer.open({
    			skin: 'layui-layer-molv',
    			closeBtn: 0,	    			  
    			title: false,
    			content:'信息标题不能为空！' ,
    			anim: 5 ,
    			btn: ['关闭']
    			});    		
    		$("#msgTitle").focus();
    		return; 
    	}
    	if(iecs($("#userNames").val())){
    		layer.open({
    			skin: 'layui-layer-molv',
    			closeBtn: 0,	    			  
    			title: false,
    			content:'请选择接收人员！' ,
    			anim: 5 ,
    			btn: ['关闭']
    			});   		 
    		 $("#userNames").focus();
    		 return;
    	}
    	var options=$("#msgType option:selected"); 
    	if(iecs(options.val())){
    		layer.open({
    			skin: 'layui-layer-molv',
    			closeBtn: 0,	    			  
    			title: false,
    			content:'请选择信息类别！' ,
    			anim: 5 ,
    			btn: ['关闭']
    			}); 
    		$("#msgTypeSelect").focus();
    		return;
    	}
    	vo = uiToData("form_vo",vo);
    	vo["msgType"]=options.val();
		var method = "add";
		if($("#id").val() != ""){
			method = "modify";	
		}
		doAjaxPost('<%=request.getContextPath()%>/cdmsg.action?act='+method,{strJson : JSON.stringify(vo),userNames : $("#userNames").val(),userCodes : $("#userCodes").val()},callDataSave);
	}
	
	function callDataSave(data){
		if(data.msg == 'true'){
            var url = '<%=request.getContextPath()%>/cdmsg.action?act=forList';
            defualtHref(url);
		}else{
			layer.open({
				skin: 'layui-layer-molv',
				closeBtn: 0,	    			  
				title: false,
				content: data.msg ,
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
</html>
