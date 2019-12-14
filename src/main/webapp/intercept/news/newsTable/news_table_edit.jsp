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
	<title>新闻发布管理</title>
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
	                	<h5 id="newsTableAddTit"></h5>                
	                    <a class="label label-primary pull-right system newsTable_list"  onclick="backTable();">返回</a>
	                </div>
	                <div class="ibox-content">
							<form role="form" id="form_vo"  class="form-horizontal">
									<input type="hidden" id="id" pofield="id" name="id" value="${vo.id}" />
			          				<input type="hidden" id="handle"  name="handle" value="${handle}" />
			                		<div class="form-group">
			                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>公告标题：</label>
			                            <div class="col-sm-4">					                           
			                                <input id="tableTitle" name="tableTitle" pofield="tableTitle" type="text" class="form-control">
			                            </div>
			                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>公告类别：</label>
			                            <div class="col-sm-4">
											<select id="tableHealthType" name="tableHealthType" pofield="tableHealthType" class="form-control"></select>
			                            </div>
			                        </div>
			                        <div class="form-group">
			                        	 <label class="col-sm-2 control-label">公告来源：</label>
			                             <div class="col-sm-10">					                           
			                                <input id="tableSource" name="tableSource" pofield="tableSource" type="text" class="form-control">
			                             </div>
			                        </div>
			                        <div class="form-group">
			                        	 <label class="col-sm-2 control-label"><span class="text-danger">*</span>公告内容：</label>
			                             <div class="col-sm-10">	
			                             	<textarea rows="15" cols="110" id="tableContent" name="tableContent" pofield="tableContent" style="width:100%;border: 1px solid #e5e6e7;"></textarea>
			                             </div>
			                        </div>
			                        <div class="form-group">
			                        	 <label class="col-sm-2 control-label">创建人：</label>
			                             <div class="col-sm-4" id="codeState">	
			                             	<input pofield="tableCjrxm" id="tableCjrxm" readonly="readonly" value="${user.userName }"class="form-control"/>
											<input type="hidden" pofield="tableCjrid" id="tableCjrid" value="${user.userId }"/>					                           
			                             </div>
			                             <label class="col-sm-2 control-label">创建时间：</label>
			                             <div class="col-sm-4">	
			                             	<input type="text" id="strTableCjsj" pofield="strTableCjsj" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
											   readonly="readonly" value="<%=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) %>"
											   class="form-control">       				                           
			                             </div>
			                        </div>
			                        <div class="form-group">
			                        	 <label class="col-sm-2 control-label">图片上传：</label>
			                             <div class="col-sm-4">					                           
			                               		<input  type="file" id="image" name="image" onchange="addfile();"/>
			                             </div>
			                             <label class="col-sm-2 control-label">图片：</label>
			                             <div class="col-sm-4">	
			                             	<div id="simage"></div>
											<input type="hidden" id="tableImageUrl" name="tableImageUrl" pofield="tableImageUrl" />			                           
			                             </div>
			                        </div>

								   <div class="text-center btnBar">
			                        	<a id="handleMethod" class="btn btn-primary system newsTable_add" onclick="saveTable();">保存</a>                       	                           
			                       </div>
						      </form>
						 </div>
	
	               </div>
	          </div>
	    </div>
	</div>
	<jsp:include page="/intercept/news/newsType/news_ChangeType.jsp" flush="false" />
	<script type="text/javascript">
    	var vo={};
//        UE.getEditor('tableContent',{ initialFrameWidth:$(".table-responsive").width()*0.86,initialFrameHeight: 300});
        //页面加载完成时启动的方法
		$(document).ready(function() {
            findCmmInit();
		});
		//初始信息
		function findCmmInit(){
            doAjaxPost('<%=request.getContextPath()%>/newsTable.action?act=findCmmInit',{},callDataToInit)
		}
		function callDataToInit(data){
		    if(data.map!=null){
		        if(data.map.newsType!=null){
		            $("#tableHealthType").html("");
                    var option = document.createElement('option');
                    option.setAttribute("value","");
					option.innerText = "--请选择--";
                    document.getElementById("tableHealthType").appendChild(option);
                    $.each(data.map.newsType,function(k,v){
                        dataUiCodeGroup("select","tableHealthType",v.labelTitle,v.labelValue);
					})
                    var optionZc = document.createElement('option');
                    optionZc.setAttribute("value","100");
                    optionZc.innerText = "家签政策";
                    document.getElementById("tableHealthType").appendChild(optionZc);
				}
			}
            onInit();
		}
		//加载完成时，查询数据，记载到表格的方法
		function onInit() {
			if($("#id").val() != ""){
				doAjaxPost('<%=request.getContextPath()%>/newsTable.action?act=jsonByOne',{id:$("#id").val(),handle:$("#handle").val()},callDataToNewsTable);
			}
			if($("#handle").val() == "look"){
				$("#newsTableAddTit").text("查看公告消息");
				$("#handleMethod").hide();
			}else if($("#handle").val() == "modify"){
				$("#newsTableAddTit").text("修改公告消息");
				$("#handleMethod").text("修改");
			}else if($("#handle").val() == "add"){
				$("#newsTableAddTit").text("新增公告消息");
			}
				
			
		}
		
		function callDataToNewsTable(data){
			dataToUi(data,"form_vo");
            if(data.tableContent != "" && data.tableContent != null){
//                var ue = UE.getEditor('tableContent');
//                ue.addListener("ready", function () {
//                    // editor准备好之后才可以使用
//                    ue.setContent(data.tableContent);
//                });
				$("#tableContent").val(data.tableContent);
            }

            if(data.tableImageUrl != "" && data.tableImageUrl != null){
				$('#simage').append('<img width="200" height="100" src="'+data.tableImageUrl+'"/>')
			}
		}
		
		function ChangeDataToNewsTable(){
			if($("#typePid").val() != null && $("#typePName").val() != ""){
				var idName = $("#typePid").val()+";;;"+$("#typePName").val();
				 $("input[name='changeNewsType']").each(function(){ 
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
	                url: '<%=request.getContextPath()%>/image.action?act=addImageBase64', //用于文件上传的服务器端请求地址
	                secureuri: false, //是否需要安全协议，一般设置为false
	                fileElementId: 'image', //文件上传域的ID
	                dataType: 'JSON', //返回值类型 一般设置为json
	                success: function (data, status)  //服务器成功响应处理函数
	                {
	                	var da=JSON.parse(data);
	                	$("#tableImageUrl").val(da.vo.filePath);
	                	$('#simage').html("");
	                	$('#simage').append('<img width="200" height="100" src="'+da.vo.filePath+'"/>')
	                	
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
            /*if(iecs($("#tableTitle").val())){
                showMsg("公告标题不能为空！");
                $("#tableTitle").focus();
                return;
            }
            if(iecs($("#tableHealthType").val())){
                showMsg("请选择公告类别！")
				$("#tableHealthType").focus();
                return
			}
            var ue = UE.getEditor('tableContent');
			if(iecs(ue.getContent())){
                showMsg("公告内容不能为空！");
                $("#tableContent").focus();
                return;
			}*/
			vo = uiToData("form_vo",vo);
			var method = "add";
			if($("#id").val() != ""){
				method = "modify";	
			}
			doAjaxPost('<%=request.getContextPath()%>/newsTable.action?act='+method,{strJson:JSON.stringify(vo)},callDataSave);
		}
		
		function callDataSave(data){
			if(data.msg == 'true'){
				var url = '<%=request.getContextPath()%>/newsTable.action?act=forList';
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
