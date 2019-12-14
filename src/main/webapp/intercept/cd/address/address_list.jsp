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
<title>行政区划管理主页</title>
<%@ include file="/open/commonjs/tldHtml.jsp"%>
	<link rel="stylesheet" href="<%=request.getContextPath() %>/open/commonjs/css/demo.css?v=1.1.0" media="all">
	<link rel="stylesheet" href="<%=request.getContextPath() %>/open/commonjs/css/zTreeStyle/zTreeStyle.css?v=1.1.0" media="all">
	<script type="text/javascript" src="<%=request.getContextPath() %>/open/commonjs/js/jquery.ztree.core.js"></script>


	<style>
		.main_div{
			width:97%;
			height:820px;
			margin:30px 20px 100px 20px;
		}
		.main_left{
			width: 15%;
			padding: 5px 5px 5px 5px;
			border: 1px solid #DDDDDD;
		}
		.main_middle{
			width: 15%;
			margin-left: 5px;
			padding: 5px 5px 5px 5px;
			border: 1px solid #DDDDDD;
		}
		.main_right{
			width: 75%;
			margin-left: 5px;
			padding: 5px 5px 5px 5px;
			border: 1px solid #DDDDDD;
		}
		.float_inline{
			display: block;
			float: left;
		}
		.pan_font{
			font-family: STHeiti;
			font-weight:bold;
			text-align: right;
		}
		.title{
			height: 20px;
			width: 93%;
			line-height: 20px;
			color: #009688;
			text-align: center;
			font-weight: bold;background: #F2F2F2;
		}
		.div_hight{
			height: 800px;
		}
		.blue{
			background: #2442C9;
		}
		.input_style{
			width:100px;
		}
		.select_style{
			width:100px;
			text-align: right;
		}
		.red{
			background: #ED5565;
		}
		.footBotton{
			width: 95%;
			height: 30px;
			line-height:30px;
			text-align: center;
		}
	</style>
</head>

<body  class="gray-bg">
	<div class="main_div">
		<div class="main_left float_inline div_hight">
			<div class="title layui-elem-quote"><span>行政区划</span></div>
			<ul id="areaTree" class="ztree">
			</ul>
		</div>
		<div class="main_right float_inline div_hight">
			<!--list-->
			<form class="form-inline" role="form">
				<div class="control-group" id="form_qvo">
					<button type="button" class="btn btn-primary system address_add" onclick="newAddress();">添加顶级行政区划</button>&nbsp;
				</div>
			</form>
			<table id="mytable" class="table table-bordered">
				<thead>
				<tr>
					<th>行政区划全称</th>
					<th>编码</th>
					<th>行政区划简称</th>
					<th>操作</th>
				</tr>
				</thead>
				<tbody id="tb">
				</tbody>
			</table>
		</div>
	</div>

<jsp:include page="/open/mould/cd/address/address_tmp.jsp" flush="false" />
<script type="text/javascript">  
   function uiFromTmpAddr(id, data,idx) {
		var t = $($("#" + id + "").html());
		dataToUiTmp_idlsttr_rs(t, data,idx);
		$("#tb").append(t);
		t.fadeIn();
	}
	function dataToUiTmp_idlsttr_rs(ui, data,idx) {
		//alert(JSON.stringify(data));
		ui.data("vo", data);
		ui.find("td[pofield=id]").text(data.id);
		ui.find("td[pofield=pid]").text(data.pid);
		ui.find("td[pofield=areaName]").text(data.areaName);
		ui.find("td[pofield=ctcode]").text(data.ctcode);
		ui.find("td[pofield=areaSname]").text(data.areaSname);
	}
	
</script>

<script type="text/javascript">
	var vo={};
	var qvo={};
	var addrId="";
	$(function(){
        findTreeTable();
    })

    var setting = {
        async:{
            autoParam:["id"],
            enable:true,
            type:"post",
            url:"<%=request.getContextPath()%>/address.action?act=jsonTreelist"
        },
		callback:{
            onClick:findTable
		}
    };

    function findTreeTable(){
        $.fn.zTree.init($("#areaTree"), setting);
        findTable(null,null,null)
	}


    function findTable(e, treeId, treeNode){
        if(treeNode != null)
        	qvo = {"upId":treeNode.id}
    	doAjaxPost('<%=request.getContextPath()%>/address.action?act=findCmmTreelist',{qvoJson:JSON.stringify(qvo)},callDataToAddr);
	}

	function callDataToAddr(data){
		$("#tb").html("");
	    $.each(data, function(k, v) {
	    	uiFromTmpAddr("address_tlist", v,k);
	    });
	}
	function findson(ui){
		addrId="";
		addrId = $(ui).data("vo").id;
		findTable();
	}
    //准备添加顶级部门
    function newAddress(){
    	var url = '<%=request.getContextPath()%>/address.action?act=forAddOrEdit&handle=add';
    	defualtHref(url);
    }
    //准备添加子部门
    function address_ForAddZi(ui){
    	var addrAddzi = $(ui).data("vo").id;
    	var url = '<%=request.getContextPath()%>/address.action?act=forAddOrEdit&handle=addzi&addrAddzi='+addrAddzi;
    	defualtHref(url);
    }
	//准备修改
	function address_ForModify(ui){
		var url = '<%=request.getContextPath()%>/address.action?act=forAddOrEdit&handle=modify&&vo.id='+$(ui).data("vo").id;
		defualtHref(url);
	}
	//删除
	function address_del(ui){
		if (confirm("确认删除数据?")==true){
	    	var addrId = $(ui).data("vo").id;
	        del(addrId);
    	}
	}  
	function del(addrId){
     	$.post('<%=request.getContextPath()%>/address.action?act=delete',{'id':addrId},function(data){
             var data=eval('(' + data + ')');
           	if(data.msg=="true"){
           		alert("删除成功!");
           		findTable();
               } else {
                   alert(data.msg);
               }
         });
	    }
	</script>
  </body>
</html>
