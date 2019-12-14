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
<title>功能主页</title>
<%@ include file="/open/commonjs/tldHtml.jsp"%>
<%@ include file="/open/commonjs/roleson.jsp"%>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="row">
	        <div class="col-sm-12">
	            <div class="ibox float-e-margins">
	                <div class="ibox-content tabble-c">
	                    <div class="table-responsive">
	                        <table class="table table-striped" id="form_vo" >
	                        	<input type="hidden" id="mid" pofield="mid" name="mid" value="${menu.id}" />
								<input type="hidden" id="handle"  name="handle" value="${handle}" />
	                            <thead>
	                            <tr>
									<th style="display: none;"></th>
									<th>功能名称</th>
									<th>功能按钮class属性</th>
									<th>功能按钮描述</th>
									<th>操作</th>
								</tr>
	                            </thead>
	                            <tbody id="sonList">
	
								</tbody>
								<tfoot>
									<tr>
										<td style="display: none;"></td>
										<td><input id="sname" pofield="sname" name="sname" type="text" class="input-md form-control"/></td>
										<td><input id="nature" pofield="nature" name="nature" type="text" class="input-md form-control"/></td>
										<td><input id="remark" pofield="remark" name="remark" type="text" class="input-md form-control"/></td>
										<td style="text-align: center;"><button id="handleMethod" type="button" class="btn btn-primary system son_add" onclick="addForm();">保存</button>
											<button id="handleMethod" type="button" class="btn btn-primary system son_list" onclick="backTable();">返回</button>
										</td>
									</tr>
								</tfoot>
	                        </table>
	                    </div>
	                </div>
	            </div>
	        </div>
	    </div>
	</div> 
<jsp:include page="/open/mould/cd/menu/menu_tmp.jsp" flush="false" />
<script type="text/javascript">
	function uiFromTmpSon(id, data,idx) {
		var t = $($("#" + id + "").html());
		dataToUiTmp_idlsttr_rs(t, data,idx);
		$("#sonList").append(t);
		t.fadeIn();
	}
	function dataToUiTmp_idlsttr_rs(ui, data,idx) {
		//alert(JSON.stringify(data));
		ui.data("vo", data);
		ui.find("td[pofield=id]").val(data.id).hide();
		ui.find("td[pofield=sname]").text(data.sname);
		ui.find("td[pofield=nature]").text(data.nature);
		ui.find("td[pofield=remark]").text(data.remark);
	}
	
</script>
<script type="text/javascript">
	var menu={};
	$(function(){
		findSon();
	})
	function findSon(){
		doAjaxPost('<%=request.getContextPath()%>/son.action?act=findMenuSonId',{'mid':$("#mid").val()},callDataToSon);	
	}
	function callDataToSon(data){
		$("#sonList").html("");
 	    $.each(data, function(k, v) {
 	    	uiFromTmpSon("son_tlist", v,k);
 	    });
		
	}
	//点击保存
	function addForm(){
		menu = uiToData("form_vo",menu);
		menu["mid"]=$("#mid").val();
		doAjaxPost('<%=request.getContextPath()%>/son.action?act=add',{strJson : JSON.stringify(menu)},callDataSave);
	}
	function callDataSave(data){
		if(data.msg == 'true'){
			layer.open({
	    			skin: 'layui-layer-molv',
	    			closeBtn: 0,	    			  
	    			title: false,
	    			content:'保存成功!' ,
	    			anim: 5 ,
	    			btn: ['关闭']
 		  	 }); 
			findSon();
			//清空输入框
			$("#sname").val("");
			$("#nature").val("");
			$("#remark").val("");
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
	//删除
	function son_del(ui){
			layer.confirm('确认删除数据?', {
			  btn: ['确定','取消'] 
			}, function(){
				var sonId = $(ui).data("vo").id;
		    	var menuId = $("#mid").val();
		        del(sonId,menuId);
			});
	}  
	function del(sonId,menuId){
     	$.post('<%=request.getContextPath()%>/son.action?act=delete',{'id':sonId,'mid':menuId},function(data){
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
           		findSon();
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
	//点击返回
	function backTable(){
		history.go(-1);
	}
</script>
</body>
</html>
