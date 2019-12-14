<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>功能管理</title>
	<%@ include file="/open/commonjs/tldHtml.jsp"%>
	<%@ include file="/open/commonjs/roleson.jsp"%>
  </head>
  <body  class="gray-bg">
	 <div class="wrapper wrapper-content">
	   <div class="row">
	        <div class="col-sm-12">
	            <div class="ibox float-e-margins">
	                <div class="ibox-title">
	                    <div class="text-center">
	                    	<a class="btn btn-success system menu_add" onclick="newMenu();">添加顶级菜单</a> 
	                    </div>
	                </div>
	                <div class="ibox-content">
	                    <div class="table-responsive">
	                        <table class="table table-striped" id="mytable">
	                            <thead>
	                            <tr>
	                                <th></th>
									<th></th>
									<th width="20%" style="text-align:left;">菜单名称</th>
									<th width="30%" >按钮列表</th>
									<th >排序号码</th>
									<th >状态</th>
									<th width="35%">操作</th>
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
  <jsp:include page="/open/mould/cd/menu/menu_tmp.jsp" flush="false" />
  <script type="text/javascript">
  function uiFromTmpMenu(id, data,idx) {
		var t = $($("#" + id + "").html());
		dataToUiTmp_idlsttr_rs(t, data,idx);
		$("#tb").append(t);
		t.fadeIn();
	}
	function dataToUiTmp_idlsttr_rs(ui, data,idx) {
		//alert(JSON.stringify(data));
		ui.data("vo", data);
		ui.find("td[pofield=id]").text(data.id);
		ui.find("td[pofield=pid]").text(data.parnentId);
		ui.find("td[pofield=mname]").text(data.mname);
		ui.find("td[pofield=sonListName]").text(data.sonListName);
		ui.find("td[pofield=onumber]").text(data.onumber);
		ui.find("td[pofield=stateName]").text(data.stateName);
		
	}
  
  </script>
  <script type="text/javascript">
     $(function(){
    	 findMenu();
     })
     function findMenu(){
    	 doAjaxPost('<%=request.getContextPath()%>/menu.action?act=list',{},callDataToMenu);	
 	}
 	function callDataToMenu(data){
 		$("#tb").html("");
 	    $.each(data, function(k, v) {
 	    	uiFromTmpMenu("menu_tlist", v,k);
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
 	//添加顶级菜单
 	function newMenu(){
 		var url = '<%=request.getContextPath()%>/menu.action?act=forAddOrEdit&handle=add';
 		defualtHref(url);
 	}
 	//添加子菜单
 	function menu_ForAddZi(ui){
 		var addson=$(ui).data("vo").id;
 		var url = '<%=request.getContextPath()%>/menu.action?act=forAddOrEdit&handle=addzi&addson='+addson;
 		defualtHref(url);
 	}
 	//添加功能
 	function menu_ForAdd(ui){
 		var url = '<%=request.getContextPath()%>/menu.action?act=forAdd&&menu.id='+$(ui).data("vo").id;
 		defualtHref(url);
 	}
 	//修改
 	function menu_ForModify(ui){
 		var url = '<%=request.getContextPath()%>/menu.action?act=forAddOrEdit&handle=modify&&menu.id='+$(ui).data("vo").id;
 		defualtHref(url);
 	}
 	//删除
	function menu_del(ui){
		layer.confirm('确认删除数据?', {
		  btn: ['确定','取消'] 
		}, function(){
			var menuId = $(ui).data("vo").id;
	        del(menuId);
		});
	}  
	function del(menuId){
     	$.post('<%=request.getContextPath()%>/menu.action?act=delete',{'id':menuId},function(data){
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
           		findMenu();
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
