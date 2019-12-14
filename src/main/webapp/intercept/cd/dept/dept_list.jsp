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
<title>部门管理主页</title>
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
                    	<a class="btn btn-success system dept_add" onclick="newdept();">添加顶级部门</a> 
                    </div>
                </div>
                <div class="ibox-content">
                    <div class="table-responsive">
                        <table class="table table-striped" id="mytable">
                            <thead>
                            <tr>
                                <th></th>
								<th></th>
								<th style="text-align:left;">部门名称</th>
								<th>所属角色</th>
								<th>部门编码</th>
								<th>部门类型</th>
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
<jsp:include page="/open/mould/cd/dept/dept_tmp.jsp" flush="false" />
<script type="text/javascript">  
   function uiFromTmpDept(id, data,idx) {
		var t = $($("#" + id + "").html());
		dataToUiTmp_idlsttr_rs(t, data,idx);
		$("#tb").append(t);
		t.fadeIn();
	}
	function dataToUiTmp_idlsttr_rs(ui, data,idx) {
		//alert(JSON.stringify(data));
		ui.data("vo", data);
		ui.find("td[pofield=id]").text(data.id);
		ui.find("td[pofield=sid]").text(data.sid);
		ui.find("td[pofield=sname]").text(data.sname);
		ui.find("td[pofield=roleName]").text(data.rid);
		ui.find("td[pofield=snumber]").text(data.snumber);
		ui.find("td[pofield=state]").text(data.stateName);
		ui.find("td[pofield=deptType]").text(data.deptTypeName); 
	}
	
</script>

<script type="text/javascript">
	$(function(){
      	findDept();
    })
    function findDept(){
    	doAjaxPost('<%=request.getContextPath()%>/dept.action?act=jsonTreelist',{},callDataToDept);	
	}
	function callDataToDept(data){
		$("#tb").html("");
	    $.each(data, function(k, v) {
	    	uiFromTmpDept("dept_tlist", v,k);
	    });
	    $.treetable.defaults={
 			id_col:0,//ID td列 {从0开始}
 			parent_col:1,//父ID td列
 			handle_col:2,//加上操作展开操作的 td列
 			order_col:4,
 			open_img:"open/images/minus.gif",
 			close_img:"open/images/plus.gif"
 		}
 		//$("#tb").treetable({id_col:0,parent_col:1,handle_col:2,open_img:"images/minus.gif",close_img:"images/plus.gif"});
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
    //准备添加顶级部门
    function newdept(){
    	var url = '<%=request.getContextPath()%>/dept.action?act=forAddOrEdit&handle=add';
    	defualtHref(url);
    }
    //准备添加子部门
    function dept_ForAddZi(ui){
    	var deptAddzi = $(ui).data("vo").id;
    	var url = '<%=request.getContextPath()%>/dept.action?act=forAddOrEdit&handle=addzi&deptAddzi='+deptAddzi;
    	defualtHref(url);
    }
	//准备修改
	function dept_ForModify(ui){
		var url = '<%=request.getContextPath()%>/dept.action?act=forAddOrEdit&handle=modify&&vo.id='+$(ui).data("vo").id;
		defualtHref(url);
	}
	//删除
	function dept_del(ui){
		layer.confirm('确认删除数据?', {
		  btn: ['确定','取消'] 
		}, function(){
			var deptId = $(ui).data("vo").id;
	        del(deptId);
		});
	}  
	
	function del(deptId){
     	$.post('<%=request.getContextPath()%>/dept.action?act=delete',{'id':deptId},function(data){
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
	          		findDept();
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
