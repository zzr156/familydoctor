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
<title>消息管理主页</title>
<%@ include file="/open/commonjs/tldHtml.jsp"%>
<%@ include file="/open/commonjs/roleson.jsp"%>
</head>

<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="row">
			<div class="col-sm-12">
	            <div class="ibox float-e-margins">
	                <div class="ibox-title">
	                	<h5 id="messageAddTit">消息管理</h5>                
	                </div>
	                <div class="ibox-content">
							<form role="form" id="form_vo"  class="form-horizontal">
			          				<input type="hidden" id="id" pofield="id">
			                		<div class="form-group">
			                            <label class="col-sm-4 control-label"><span class="text-danger">*</span>超期未送检提醒：</label>
			                            <div class="col-sm-8" id="cqtx">					                           
			                            </div>
			                        </div>
			                       <div class="form-group">
			                            <label class="col-sm-4 control-label"><span class="text-danger">*</span>检验报告时间签发超时：</label>
			                            <div class="col-sm-8" id="qfcs">					                           
			                            </div>
			                        </div>
			                        <div class="form-group">
			                            <label class="col-sm-4 control-label"><span class="text-danger">*</span>检验结果呈阳性：</label>
			                            <div class="col-sm-8" id="result">					                           
			                            </div>
			                        </div>
								   <div class="text-center btnBar">
			                        	<button id="handleMethod" type="button" class="btn btn-primary system message_add" onclick="saveTable();">保存</button>                       	                           
			                       </div>
						      </form>
						 </div>
	               </div>
	          </div>
	    </div>
	</div>
    <script type="text/javascript">
    	var vo={};
    	$(function(){
    		findCmmInit();
    	})
    	
    	//统一初始化
		function findCmmInit(){
			doAjaxPost('<%=request.getContextPath()%>/message.action?act=findCmmInit',{},callDataToInit);
		}
		function callDataToInit(data){
			if(data.map != null){
				//超期提醒 签发超时 检验结果
				if(data.map.enable != null){
					var datat=data.map.enable;
					$("#cqtx").html("");
			        $.each(datat, function(k, v) {
			        	dataUiCodeGroup("radio","cqtx",v.codeTitle,v.codeValue);
			        });
			        $("#qfcs").html("");
			    	$.each(datat, function(k, v) {
				        dataUiCodeGroup("radio","qfcs",v.codeTitle,v.codeValue);
				    });
			    	$("#result").html("");
			    	$.each(datat, function(k, v) {
					      dataUiCodeGroup("radio","result",v.codeTitle,v.codeValue);
					});
			    	$("input[name='cqtx'][value=1]").attr("checked",true);
					$("input[name='qfcs'][value=1]").attr("checked",true);
					$("input[name='result'][value=1]").attr("checked",true);
			        
			        
				}
				onInit();//回选
			}
		}
		
		
		//加载初始数据
		function onInit(){
			doAjaxPost('<%=request.getContextPath()%>/message.action?act=list',{},callToUi);
		}
		function callToUi(data){
			if(data.vo.length==0){
				
			}else{
				$("#id").val(data.vo[0].id);
				$("input[name='cqtx'][value="+data.vo[0].cqtx+"]").attr("checked",true);
				$("input[name='qfcs'][value="+data.vo[0].qfcs+"]").attr("checked",true);
				$("input[name='result'][value="+data.vo[0].result+"]").attr("checked",true);
			}
			
		}
		//点击保存
		function saveTable(){
			vo = uiToData("form_vo",vo);
    		doAjaxPost('<%=request.getContextPath()%>/message.action?act=add',{strJson : JSON.stringify(vo)},callDataSave);
    	}
    	function callDataSave(data){
			if(data.msg == 'true'){
				layer.open({
 	    			skin: 'layui-layer-molv',
 	    			closeBtn: 0,	    			  
 	    			title: false,
 	    			content:'保存成功！' ,
 	    			anim: 5 ,
 	    			btn: ['关闭']
     		  	 }); 
				findList();
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
