<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>基础数据管理</title>
	<%@ include file="/open/commonjs/tldHtml.jsp"%>
<%@ include file="/open/commonjs/roleson.jsp"%>
</head>

<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">							                
	                <div class="ibox-content" id="form_qvo">
	                    <form method="get" class="form-horizontal">
	                        <div class="form-group">
	                            <label class="col-sm-2 control-label">账号：</label>
	                            <div class="col-sm-4">
	                                <input type="text" class="form-control" pofield="cdName"  id="cdName">
	                            </div>
	                            <label class="col-sm-2 control-label">组名：</label>
                            	<div class="col-sm-4 ">
                                   	 <input type="text" class="form-control" pofield="cdGroupName"  id="cdGroupName">
                                   	 <input type="hidden" pofield="cdGroup"  id="cdGroup">
                            	</div>
	                        </div>      
	                        <div class="form-group">
	                            <label class="col-sm-2 control-label">用户状态：</label>
	                            <div class="col-sm-4 ">
                                    	<select class="form-control" pofield="cdState" id="cdState">
											<option value="">--请选择--</option>
				                        </select>
	                            </div>
	                            <label class="col-sm-2 control-label">显示条数：</label>
	                           	<div class="col-sm-4">
	                                  	<input type="text" class="form-control" pofield="pageSize"  id="pageSize"  value="15" >
	                           	</div>
	                        </div>     
	                        <div class="text-center" style="border-top: 1px dashed #D7D7D7;padding-top: 10px;">
	                        	<a class="btn btn-primary system code_list" onclick="findTable();">查询</a>
	                        	<a class="btn btn-default system code_list" onclick="findReset();">重置</a>                           
	                        </div>
	                        <input type="hidden" pofield="itemCount" id="itemCount" value="">
	                        <input type="hidden" pofield="pageCount" id="pageCount" value="">
	                        <input type="hidden" pofield="pageStartNo" id="pageStartNo" value="1">
	                 	</form>
	                 </div>       
				</div>
			</div>
		</div>
		
	
		<div class="row">
	        <div class="col-sm-12">
	            <div class="ibox float-e-margins">
	            	<div class="ibox-title">
						<a class="label label-danger pull-right system code_add" onclick="code_del();">批量删除</a>
	                    <a class="label label-success pull-right system code_delete" onclick="forAddTable();">新增</a>
					</div>	
	                <div class="ibox-content tabble-c">
	                    <div class="table-responsive">
	                        <table class="table table-striped" id="codetable">
	                            <thead>
	                            <tr>
                                	<th style="text-align:center;"><input type="checkbox" id="selectAll"style="width:22px;height:22px" onclick="selectAll()"/></th>
								 	<th style="text-align: center;">序号</th>
		            				<th style="text-align: center;">组名</th>
		            				<th style="text-align: center;">基础数据名称</th>
		            				<th style="text-align: center;">基础属性值</th>
		            				<th style="text-align: center;">排序</th>
		            				<th style="text-align: center;">状态</th>
		            				<th style="text-align: center;">操作</th>
	                            </tr>
	                            </thead>
	                            <tbody id="code_list">
	
								</tbody>
	                        </table>
	                    </div>
	                    <!--分页按钮-->
	                    <div class="text-center" style="background: #fff; padding-top: 5px;">
	                        <button type="button" class="btn btn-sm" onclick="Previous();findTable();">前一页</button>
	                        <span id="qvodesc">1/1每页显示:15条,共有1条</span>&nbsp;&nbsp;
	                        <button type="button" class="btn btn-sm" onclick="Next();findTable();">下一页</button>
	                        <input style="width: 40px" class="span2" id="gotext" type="text">
	                        <button class="btn btn-sm" onclick="qvoGo();findTable();" type="button">Go!</button>
	                    </div>
	                </div>
	            </div>
	        </div>
	    </div>
	</div>
	<jsp:include page="/open/mould/cd/code/code_tmp.jsp" flush="false" />
	<jsp:include page="/open/mould/common/idx_tmp_sg.jsp" flush="false" />
	<script type="text/javascript">
	   function uiFromTmp(id, data,idx) {
		    var ui = $($("#" + id + "").html());
		    ui.data("vo", data);
		    ui.find("td[pofield=id] input[name=chckBox]").val(data.id);
	        ui.find("td[pofield=num]").text(idx+1);
	        ui.find("td[pofield=codeRemark]").text(data.codeRemark);
	        ui.find("td[pofield=codeTitle]").text(data.codeTitle);
	        ui.find("td[pofield=codeValue]").text(data.codeValue);
	        ui.find("td[pofield=codeSort]").text(data.codeSort);
	        ui.find("td[pofield=stateName]").text(data.stateName);
	        $("#code_list").append(ui);
	        ui.fadeIn();
	    }
	
	</script>
	<script type="text/javascript">
	    var qvo={};
	    var vo={};
	    <!--list-->
	    $(function(){
	 		findTable();
	 		findState();
	 		//findGroup();
	 		initUi_group_find($("#cdGroupName"));
	    })
	    
	    //诱导组名
	    function initUi_group_find(groupui){
	    	$(groupui).autocomplete({
		    	serviceUrl :  "<%=request.getContextPath()%>/code.action?act=findCmmCodeGroup",
				paramName : "strJson",
				type : "POST",
				minChars : 1,
				width:300,
				myHeaders : [ '组名中文'],
				myFields : [ 'codeRemark'],
				dataType : 'json',
				noCache : true,
				onSelect : function(v) {
					$(groupui).val(v.codeRemark);
					$("#cdGroup").val(v.codeGroup);
				},
				transformResult : function(response) {
					return {
						suggestions : response
					};

				}
	    	});
	    }
	    //查询
	    function findTable(){
	    	qvo = uiToData("form_qvo",qvo);//界面值绑定到变量
	        doAjaxPost('<%=request.getContextPath()%>/code.action?act=list',{qvoJson:JSON.stringify(qvo)},callDataToUi);
	    }
	    function callDataToUi(data){
	        dataToUi(data.qvo,"form_qvo");//数据绑定到界面
	        qvodesc("qvodesc");
	        $("#codetable tbody").html("");
	        if(data.rows != null){
	        	$.each(data.rows, function(k, v) {
		            uiFromTmp("tlist", v,k);
		        });
	        }
	    }
	    
	    //重置
	    function findReset(){
        	$('#cdGroup').val("");
        	$('#cdState').val("");
        	$('#cdName').val("");
        	$('#cdGroupName').val("");
        }
	
	  	//状态
	    function findState(){
	    	doAjaxPost('<%=request.getContextPath()%>/code.action?act=jsonTreelist',{group:'enable'},callDataToState);	
	    }
	    function callDataToState(data){
	     	$("#cdState").html();
        	var obj = document.getElementById('cdState'); //3、在末尾中添加元素
	        $.each(data, function(k, v) {
	        	obj.options.add(new Option(v.codeTitle,v.codeValue));
	        });
	    }
	    
	    //组名
	    function findGroup(){
	    	doAjaxPost('<%=request.getContextPath()%>/code.action?act=findCmmCodeGroup',{},callDataToGroup);
	    }
	    
	    function callDataToGroup(data){
	     	$("#cdGroup").html();
        	var obj = document.getElementById('cdGroup'); //3、在末尾中添加元素
	        $.each(data, function(k, v) {
	        	obj.options.add(new Option(v.codeRemark,v.codeGroup));
	        });
	    }
	    
	    //准备新增
	    function forAddTable(){
	    	var url = '<%=request.getContextPath()%>/code.action?act=forAddOrEdit&handle=add';
	    	defualtHref(url);
	    }
	    //准备修改
	    function forModifyTable(ui){
	        var url = '<%=request.getContextPath()%>/code.action?act=forAddOrEdit&handle=modify&vo.id='+$(ui).data("vo").id;
	        defualtHref(url);
	    }
	    //查看
	    function forLookTable(ui){
	        var url = '<%=request.getContextPath()%>/code.action?act=forAddOrEdit&handle=look&vo.id='+$(ui).data("vo").id;
	        defualtHref(url);
	    }
	    
	 	//全选和全选取消
	    function selectAll(){
	    	if($("#selectAll").prop("checked")){
	    		$.each($("#code_list").find("input[name=chckBox]"),function(k,v){
	    			$(this).prop("checked",true);
	    		});
	    	}else{
	    		$.each($("#code_list").find("input[name=chckBox]"),function(k,v){
	    			$(this).prop("checked",false);
	    		});
	    	}
	    }
	  
	    //删除页面获取删除ID
	    function code_del(){
	    	var ids="";
	    	$.each($("#code_list").find("input[name=chckBox]"),function(k,v){
	    		if($(this).prop("checked")){
	    			if(ids!=""){
	    				ids+=";";
	    			}
	    			ids+=$(this).val();
	    		}
	    	});
	    	if(ids==""){
	    		layer.open({
           			skin: 'layui-layer-molv',
           			closeBtn: 0,	    			  
           			title: false,
           			content:'请选择需要删除的信息!' ,
           			anim: 5 ,
           			btn: ['关闭']
         		});
	    	}else{
    		  	layer.confirm('确认删除数据?', {
	   			  btn: ['确定','取消'] 
	   			}, function(){
	   				del(ids);
	   			});
	    	}
	    }
	    
	    //删除
	    function delForm(ui){
	    	layer.confirm('确认删除数据?', {
   			  btn: ['确定','取消'] 
   			}, function(){
   				var id= $(ui).data("vo").id;
		        del(id);
   			});
        }
	    
	    function del(id){
        	$.post('<%=request.getContextPath()%>/code.action?act=delete',{'id':id},function(data){
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
