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
<title>新闻类别主页</title>
<%@ include file="/open/commonjs/tldHtml.jsp"%>
<%@ include file="/open/commonjs/roleson.jsp"%>

</head>

<body class="gray-bg">
<div class="wrapper wrapper-content">
	<div class="col-sm-12">
			<input type="hidden" id="id" pofield="id" name="id" value="${vo.id}" />
			<input type="hidden" id="handle"  name="handle" value="${handle}" />
			<input type="hidden" id="newsTypeAddzi" name="newsTypeAddzi" value="${newsTypeAddzi}">
            <div class="ibox float-e-margins">
                <div class="ibox-title">                 
                	<h5 id="newsAddTit"></h5>                
                    <a class="label label-primary pull-right"  onclick="backTable();">返回</a>
                </div>
                <div class="ibox-content">
                	<form method="get" class="form-horizontal" id="form_vo">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">类别名称：</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" pofield="typeName" id="typeName"  maxlength="15">
                            </div>                            
                        </div>      
                        <div class="form-group" id="ssType">
					    	<label class="col-sm-2 control-label">所属类别:</label>
					    	<div class="col-sm-10">
					        	<div class="input-group">
                                	<input type="text" class="form-control" id="typePName" pofield="typePName" type="text" readonly="readonly"> 
                                	<input  id="typePid" class="form-control" pofield="typePid" type="hidden"  />
                                	<span class="input-group-btn"> 
                                    	<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal">选择类别</button> 
                                 	</span>
                             	</div>
					     	</div>
					    </div>
					    <div class="form-group">
                            <label class="col-sm-2 control-label">状态：</label>
                            <div class="col-sm-10" id="typeState">

                            </div>                            
                        </div>
					    <div class="form-group">
                            <label class="col-sm-2 control-label">类别图片上传：</label>
                            <div class="col-sm-4">
                            	<input  type="file" id="image"  name="image" onchange="addfile();"/>				        	
					     	</div>
					     	<label class="col-sm-2 control-label">类别图片：</label>
					     	<div class="col-sm-4">
					     		<div id="simage"></div>
								<input type="hidden" id="typeImageUrl" name="typeImageUrl" pofield="typeImageUrl" />
					     	</div>
                        </div>                
                        <div class="text-center btnBar">
                        	<a class="btn btn-primary" onclick="saveTable();">保存</a>	                            
                        </div>
                        
                 	</form>
                </div>
            </div>
	</div>                
</div>
	
     <jsp:include page="news_ChangeType.jsp" flush="false" />
    <script type="text/javascript">
    	var vo={};
		//页面加载完成时启动的方法
		$(document).ready(function() {
			findNewsType();
			findCmmInit();//初始化
		});
		//统一初始化
		function findCmmInit(){
			doAjaxPost('<%=request.getContextPath()%>/newsType.action?act=findCmmInit',{},callDataToInit);
		}
		function callDataToInit(data){
			if(data.map!=null){
				//状态
				if(data.map.newType!=null){
					$("#typeState").html("");
					$.each(data.map.newType, function(k, v) {
			        	dataUiCodeGroup("radio","typeState",v.codeTitle,v.codeValue);
			      	});
		    	  	if($("#handle").val() == "add"||$("#handle").val()=="addzi"){
						$("input[name='typeState'][value=1]").attr("checked",true); 
				  	}
				}
				if($("#handle").val() == "add"){
		        	$("#ssType").hide();
		        }else{
		        	$("#ssType").show();
		        }
		        onInit();
			}
			
		}
	    
		//加载完成时，查询数据，记载到表格的方法
		function onInit() {
			if($("#id").val() != ""){
				doAjaxPost('<%=request.getContextPath()%>/newsType.action?act=jsonByOne',{id:$("#id").val()},callDataToNewsType);
			}
			if($("#handle").val() == "look"){
				$("#handleMethod").hide();
				$("#newsAddTit").text("查看菜单");
			}else if($("#handle").val() == "modify"){
				$("#handleMethod").text("修改");
				$("#newsAddTit").text("修改菜单");
			}else if($("#handle").val() == "addzi"){
				$("#newsAddTit").text("添加子级菜单");
				var id = $("#newsTypeAddzi").val();
				doAjaxPost('<%=request.getContextPath()%>/newsType.action?act=jsonByOne',{id:id},function(dataAddzi){
					var idName = dataAddzi.id+";;;"+dataAddzi.typeName;
					$("input[name='changeNewsType']").each(function(){ 
		    			if($(this).val() == idName){
		    				$(this).attr("checked", true);
		    			} 
		    	 	}); 
					var chkNewsTypeAddzi = idName.split(";;;");
		        	$('#typePid').val(chkNewsTypeAddzi[0]);
		        	$('#typePName').val(chkNewsTypeAddzi[1]);
				});
			}else if($("#handle").val() == "add"){
				$("#newsAddTit").text("添加顶级菜单");
			}
		}
		
		function callDataToNewsType(data){
			dataToUi(data,"form_vo");
			if(data.typeImageUrl != "" && data.typeImageUrl != null){
				$('#simage').append('<img width="200" height="100" src="<%=request.getContextPath()%>/image.action?act=getUrlImage&url='+data.typeImageUrl+'"/>')	
			}
			if(data.typePid !="" && data.typePid != null){
				var idName = data.typePid+";;;"+data.typePName;
				 $("input[name='changeNewsType']").each(function(){ 
		    			if($(this).val() == idName){
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
	                	$("#typeImageUrl").val(da.vo.filePath);
	                	$('#simage').html("");
	                	$('#simage').append('<img width="200" height="100" src="<%=request.getContextPath()%>/image.action?act=getUrlImage&url='+da.vo.filePath+'"/>')
	                	
	                },
	                error: function (data, status, e)//服务器响应失败处理函数
	                {
	                    layer.open({
	                    	skin: 'layui-layer-molv',
	                    	closeBtn: 0,	    			  
	                    	title: false,
	                    	content:e,
	                    	anim: 5 ,
	                    	btn: ['关闭']
	                    	});
	                }
	            }
	        )
	        return false;
		}
		//保存的方法
		function saveTable() {
			vo = uiToData("form_vo",vo);
			var method = "add";
			if($("#id").val() != ""){
				method = "modify";	
			}
			doAjaxPost('<%=request.getContextPath()%>/newsType.action?act='+method,{strJson : JSON.stringify(vo)},callDataSave);
		}
		
		function callDataSave(data){
			if(data.msg == 'true'){
				window.location.href = '<%=request.getContextPath()%>/newsType.action?act=forList';
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
