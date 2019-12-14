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
<title>新闻类别管理</title>
<%@ include file="/open/commonjs/tldHtml.jsp"%>
<%@ include file="/open/commonjs/roleson.jsp"%>

</head>
<body class="gray-bg">
<div class="wrapper wrapper-content">
   <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <div class="text-center">
                    	<a class="btn btn-success system newsType_add" onclick="forAddTable();">添加顶级类别</a> 
                    </div>
                </div>
                <div class="ibox-content">
                    <div class="table-responsive">
                        <table class="table table-striped" id="mytable">
                            <thead>
                            <tr>
                                <th></th>
								<th></th>
								<th style="text-align:left;">类别名称</th>
								<th>类别编码</th>
								<th>状态</th>
								<th>操作</th>
                            </tr>
                            </thead>
                            <tbody id="tb"></tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>    
<jsp:include page="/open/mould/news/newsType/news_type_tmp.jsp" flush="false" />
<script type="text/javascript">  
   function uiFromTmpNewType(id, data,idx) {
		var t = $($("#" + id + "").html());
		dataToUiTmp_idlsttr_rs(t, data,idx);
		$("#tb").append(t);
		t.fadeIn();
	}
	function dataToUiTmp_idlsttr_rs(ui, data,idx) {
        ui.data("vo", data);
		ui.find("td[pofield=id]").text(data.id);
		ui.find("td[pofield=typePid]").text(data.typePid);
		ui.find("td[pofield=typeName]").text(data.typeName);
		ui.find("td[pofield=typeNum]").text(data.typeNum);
		ui.find("td[pofield=typeStateName]").text(data.typeStateName);
	}
	
</script>

<script type="text/javascript">
	$(function(){
		findTable();
    })
    function findTable(){
    	doAjaxPost('<%=request.getContextPath()%>/newsType.action?act=list',{},callDataToNewType);	
	}
	function callDataToNewType(data){
		$("#tb").html("");
		if(data.rows != null){
		    $.each(data.rows, function(k, v) {
		    	uiFromTmpNewType("tlist", v,k);
		    });
		}
	    $.treetable.defaults={
 			id_col:0,//ID td列 {从0开始}
 			parent_col:1,//父ID td列
 			handle_col:2,//加上操作展开操作的 td列
 			order_col:4,
 			open_img:"open/images/minus.gif",
 			close_img:"open/images/plus.gif"
 		}
 		//只能应用于tbody
 		$("#tb").treetable();
 		//应用样式
 		$("#tb tr:even td").addClass("alt");
 		$("#tb tr").find("td:eq(2)").addClass("spec");
 		$("#tb tr:even").find("td:eq(2)").removeClass().addClass("specalt");
 		
 		//隐藏数据列
 		$("#tb tr").find("td:eq(0)").hide();
 		$("#tb tr").find("td:eq(1)").hide();
 		$("#mytable tr:eq(0)").find("th:eq(0)").hide();
 		$("#mytable tr:eq(0)").find("th:eq(1)").hide();
	}
    //准备添加顶级类型
    function forAddTable(){
    	var url = '<%=request.getContextPath()%>/newsType.action?act=forAddOrEdit&handle=add';
    	defualtHref(url);
    }
    //准备添加子类型
    function forAddTableZi(ui){
    	var newsTypeAddzi = $(ui).data("vo").id;
    	var url = '<%=request.getContextPath()%>/newsType.action?act=forAddOrEdit&handle=addzi&newsTypeAddzi='+newsTypeAddzi;
    	defualtHref(url);
    }
	//准备修改
	function forModifyTable(ui){
		var url = '<%=request.getContextPath()%>/newsType.action?act=forAddOrEdit&handle=modify&vo.id='+$(ui).data("vo").id;
		defualtHref(url);
	}
	//删除
	function delForm(ui){
		    layer.confirm('确认删除数据?', {
			  btn: ['确定','取消'] 
			}, function(){
				var id = $(ui).data("vo").id;
		        del(id);
			});
	}  
	function del(id){
     	$.post('<%=request.getContextPath()%>/newsType.action?act=delete',{'id':id},function(data){
             var data=eval('(' + data + ')');
           	if(data.msg=="true"){
           		layer.open({
           			skin: 'layui-layer-molv',
           			closeBtn: 0,	    			  
           			title: false,
           			content:'删除成功!' ,
           			anim: 5 ,
           			btn: ['关闭']
           			});
           		findTable();
               } else {
            	   layer.open({
              			skin: 'layui-layer-molv',
              			closeBtn: 0,	    			  
              			title: false,
              			content:data.msg ,
              			anim: 5 ,
              			btn: ['关闭']
              			});
               }
         });
	    }
	</script>
  </body>
</html>
