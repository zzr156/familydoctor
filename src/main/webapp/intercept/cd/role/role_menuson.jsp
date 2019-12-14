<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
  <head>
	<%@ include file="/open/commonjs/tldHtml.jsp"%>
	<%@ include file="/open/commonjs/roleson.jsp"%>
    <base href="<%=basePath%>">
    
    <title>添加新角色</title>
    
  </head>
  <body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="row">
	        <div class="col-sm-12">
	            <div class="ibox float-e-margins">
	                <div class="ibox-content tabble-c">
	                    <div class="table-responsive">
	                        <table class="table table-striped" id="mytable">
	                            <thead>
	                            <tr>
									<th></th>
									<th></th>
									<th style="text-align:left;">菜单名称</th>
									<th style="text-align:left;">功能选择</th>
									<th style="text-align:left;">功能名称</th>
								</tr>
	                            </thead>
	                            <tbody id="tb">
	
								</tbody>
								<tfoot>
									<tr>
										<td colspan="3" nowrap="nowrap" align="center">
											<button onclick="saveTable();" type="button" class="btn btn-primary system roleMenu_modify">保存</button>
											<button onclick="backTable()"  type="button" class="btn btn-default system role_list">返回</button>
											<button onclick="selectall()"  type="button" class="btn btn-info system role_list">全选</button>
											<button onclick="noselectall()"  type="button" class="btn btn-info system role_list">不选</button>
											<input type="hidden" id="id" pofield="id" name="id" value="${role.id}" />
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
  <jsp:include page="/open/mould/cd/role/role_tmp_menu.jsp" flush="false" />
  <script type="text/javascript">
		function uiFromTmp(id, data,idx) {
			var t = $($("#" + id + "").html());
			dataToUiTmp_idlsttr_rs(t, data,idx);
			$("#tb").append(t);
			t.fadeIn();
		}
		function dataToUiTmp_idlsttr_rs(ui, data,idx) {
			ui.data("vo", data);
			ui.find("td[pofield=id]").text(data.id);
			ui.find("td[pofield=pid]").text(data.pid);
			var d=document.createElement("input");
	        d.setAttribute("type","checkbox");
	        d.setAttribute("name","menuck");
	        d.setAttribute("id","menuck");
	        d.setAttribute("value",data.id);
			ui.find("td[pofield=mname]").append(d);
			ui.find("td[pofield=mname]").append(data.mname);
			ui.find("td[pofield=rolechoose]").html("<a href=javascript:selectSinleRoleAll('"+data.id+"')>[全选]</a><a href=javascript:selectNotRoleAll('"+data.id+"')>[全不选]</a>")
			doAjaxPost('<%=request.getContextPath()%>/son.action?act=findMenuSonId',{mid:data.id},function(datas){
				$.each(datas, function(k, v) {
					var i = k+1;
					
					if(i%5 ==0){
						var s=document.createElement("input");
				        s.setAttribute("type","checkbox");
				        s.setAttribute("name","sonck");
				        s.setAttribute("id","sonck");
				        s.setAttribute("value",data.id+";;;"+v.id);
				        ui.find("td[pofield=mson]").append(s);
				        ui.find("td[pofield=mson]").append(v.sname);
				        ui.find("td[pofield=mson]").append("&nbsp;&nbsp;");
				        ui.find("td[pofield=mson]").append("<br/>");
					}else{
						var s=document.createElement("input");
				        s.setAttribute("type","checkbox");
				        s.setAttribute("name","sonck");
				        s.setAttribute("id","sonck");
				        s.setAttribute("value",data.id+";;;"+v.id);
				        ui.find("td[pofield=mson]").append(s);
				        ui.find("td[pofield=mson]").append(v.sname);
				        ui.find("td[pofield=mson]").append("&nbsp;&nbsp;");
					}
				});
			});
			
		}
	</script>
  <script type="text/javascript">
    	var vo={};
		//页面加载完成时启动的方法
		$(document).ready(function() {
			onInit();
		});
	
		//加载完成时，查询数据，记载到表格的方法
		function onInit() {
			if($("#id").val() != ""){
				doAjaxPost('<%=request.getContextPath()%>/menu.action?act=findMenulist',{},callDataToMenu);
			}
		}
		
		function callDataToMenu(data){
			 $.each(data, function(k, v) {
				 uiFromTmp("tlist", v,k);
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
	   		doAjaxPost('<%=request.getContextPath()%>/role.action?act=findCmmRoleMenu',{id:$("#id").val()},function(datamenu){
	   			$("input[name='menuck']:checkbox").each(function(){ 
	   				if(JSON.stringify(datamenu).indexOf($(this).attr("value"))!= -1){
						$(this).attr("checked", true);
					}
    			})
	   		});
	   		doAjaxPost('<%=request.getContextPath()%>/role.action?act=findCmmRoleMenuSon',{id:$("#id").val()},function(datason){
				$("input[name='sonck']:checkbox").each(function(){ 
	   				if(JSON.stringify(datason).indexOf($(this).attr("value"))!= -1){
						$(this).attr("checked", true);
					}
    			})
	   		});
		}
		
		//点击返回
		function backTable(){
			history.go(-1);
		}
		
	    
		//保存的方法
		function saveTable() {
			var menucks=$('[id=menuck]:checkbox');
			var soncks=$('[id=sonck]:checkbox');
			var menus = "";
			var menuSons="";
			for(i=0;i<menucks.length;i++){
				if(menucks[i].checked){
					if(menus.indexOf(menucks[i].value) == -1){
						menus+=menucks[i].value+",";}
				}
			}
			for(i=0;i<soncks.length;i++){
				if(soncks[i].checked){
					if(menuSons.indexOf(soncks[i].value) == -1){
						menuSons+=soncks[i].value+",";}
				}
			}
			doAjaxPost('<%=request.getContextPath()%>/roleMenu.action?act=addOrEdit',
					{
						mids : menus,
						msids: menuSons,
						rid:$("#id").val()
					},callDataSave);
		}
		
		function callDataSave(data){
			if(data.msg == 'true'){
                var url = '<%=request.getContextPath()%>/role.action?act=forList';
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
		
		
		//全选
		function selectall(){
 			var checkboxs=document.getElementsByName("menuck");
			for (var i=0;i<checkboxs.length;i++){
				var e = checkboxs[i];
				if(!e.checked)
				e.checked = true;
			}
	 		var checkboxs1=document.getElementsByName("sonck");
	 		for (var i=0;i<checkboxs1.length;i++){
				var e = checkboxs1[i];
				if(!e.checked)
				e.checked = true;
			}
	    }
		
		//全不选
		function noselectall(){//全不选
	 		var checkboxs=document.getElementsByName("menuck");
			for (var i=0;i<checkboxs.length;i++){
				var e = checkboxs[i];
				if(e.checked)
				e.checked = false;
			}
	 		var checkboxs1=document.getElementsByName("sonck");
	 		for (var i=0;i<checkboxs1.length;i++){
				var e = checkboxs1[i];
				if(e.checked)
				e.checked = false;
			}
	   }
	
		//单个菜单全选
		function selectSinleRoleAll(id){
			$("input[type=checkbox]").each(function () {
               	if(($(this).val()).indexOf(id)>=0){
               		$(this).prop("checked",true);
               	}
            });
			//子菜单的全选
			$("td[pofield=pid]").each(function(){
				if($(this).text()==id){
					var sonid = $(this).prev().text();
					$("input[type=checkbox]").each(function () {
               			if(($(this).val()).indexOf(sonid)>=0){
               			$(this).prop("checked",true);
               			}
           			 });
				}
				
			});
			
	
	    }
	    //单个菜单全不选
	    function selectNotRoleAll(id){
	        $("input[type=checkbox]").each(function () {
               	if(($(this).val()).indexOf(id)>=0){
               		$(this).prop("checked",false);
               	}
            });
            
            $("td[pofield=pid]").each(function(){
				if($(this).text()==id){
					var sonid = $(this).prev().text();
					$("input[type=checkbox]").each(function () {
               			if(($(this).val()).indexOf(sonid)>=0){
               			$(this).prop("checked",false);
               			}
           			 });
				}
				
			});
	
	    }
	</script>
  </body>
</html>
