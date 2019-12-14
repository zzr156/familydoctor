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
<title>短信管理主页</title>
<%@ include file="/open/commonjs/tldHtml.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/open/commonjs/jq/jquery.PrintArea.js"></script>
</head>
<body>

	<button type="button"  onclick="findTable();">生成条形码</button>

	<div id="simage" class="imgdiv" ></div>
<script type="text/javascript">
	//查询
    function findTable(){
		window.open('<%=request.getContextPath()%>/image.action?act=getOneBarcode');
    	//doAjaxPostNotLoad('<%=request.getContextPath()%>/image.action?act=getOneBarcode',callData);
    }
    function callData(data){
    	$('#simage').html("");
    	$('#simage').append('<img width="200" height="75" src="data:image/png;base64,'+data+'"/>')
    	if(window.attachEvent){ 
    		 $("div#simage").printArea({
        		 mode: 'popup'
        	 });   
	    }else{ 
	    	 $("div#simage").printArea();   
	    } 
    }
</script>
</body>
</html>
