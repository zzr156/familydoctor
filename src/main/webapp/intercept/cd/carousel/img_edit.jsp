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
<title>新增图片</title>
<%@ include file="/open/commonjs/tldHtml.jsp"%>
<%@ include file="/open/commonjs/roleson.jsp"%>
</head>

<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="row">
			<div class="col-sm-12">
	            <div class="ibox float-e-margins">
	                <div class="ibox-title">
	                	<h5 id="imgAddTit">新增轮播图</h5>                
	                    <a class="label label-primary pull-right system carousel_list"  onclick="backTable();">返回</a>
	                </div>
	                <div class="ibox-content">
							<form role="form" id="form_vo"  class="form-horizontal">
		                		<div class="form-group">
		                            <label class="col-sm-2 control-label">文件图片：</label>
		                            <div class="col-sm-4">			
		                            	<input  type="file" id="image" name="image" onchange="addfile()"/>
							       		<input type="hidden" id="imgUrl" name="imgUrl" pofield="imgUrl" />
										<input type="hidden" id="FileName" name="FileName" pofield="FileName" />		                           
		                            </div>
		                            <label class="col-sm-2 control-label">排序：</label>
		                            <div class="col-sm-4">
		                            	  <input pofield="px" name="px" type="text" class="form-control" />
		                            </div>
		                        </div>
								<div class="text-center btnBar">
		                        	<a id="handleMethod" class="btn btn-primary system carousel_add" onclick="addForm();">保存</a>                       	                           
		                        </div>
						   </form>
						 </div>
	
	               </div>
	          </div>
	    </div>
	</div>
	
    <script type="text/javascript">
    	var vo={};
    	//点击保存
    	function addForm(){
    		vo = uiToData("form_vo",vo);
			doAjaxPost('<%=request.getContextPath()%>/carousel.action?act=add',{strJson : JSON.stringify(vo)},callDataSave);
		}
		
		function callDataSave(data){
			//alert(JSON.stringify(data));
			if(data.msg == 'true'){
                var url = '<%=request.getContextPath()%>/carousel.action?act=forList';
                defualtHref(url);
			}else{
				alert(data.msg);
			}
		}
		//上传文件
		function addfile(){
			var pic = $("#image").val().split(".");
			if(pic[1]==undefined || (pic[1]!='jpg'&&pic[1]!='jpeg'&&pic[1]!='png')){
				alert('上传照片格式不正确（照片格式应该为jpg/jpeg/png）!');
				 return false;
			}
			$.ajaxFileUpload({
				url: '<%=request.getContextPath()%>/image.action?act=addImage', //用于文件上传的服务器端请求地址
                secureuri: false, //是否需要安全协议，一般设置为false
                fileElementId: 'image', //文件上传域的ID
                dataType: 'JSON', //返回值类型 一般设置为json
                success: function (data, status){  //服务器成功响应处理函数
                	var da=JSON.parse(data);
                	$("#FileName").val(da.vo.fileName);
                	$("#imgUrl").val(da.vo.filePath); 
                },
                error: function (data, status, e){//服务器响应失败处理函数
                    alert(e);
                }
			})
			
		}
    	//点击返回
		function backTable(){
			history.go(-1);
		}
    </script>
</body>
</html>
