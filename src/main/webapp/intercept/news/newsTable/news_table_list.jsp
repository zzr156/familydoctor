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
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">							                
	                <div class="ibox-content" id="form_qvo">
	                    <form method="get" class="form-horizontal">
	                        <div class="form-group">
	                            <label class="col-sm-2 control-label">分类：</label>
	                            <div class="col-sm-4">
									<select id="tableNewsType" class="form-control" >
										<option value="">--请选择--</option>
									</select>
	                                <%--<input type="text" class="form-control" pofield="tableNewsTitle"  id="tableNewsTitle">--%>
	                            </div>
	                            <%--<label class="col-sm-2 control-label">新闻类别：</label>
                            	<div class="col-sm-4 ">
	                            		<div class="input-group">
			                             	<input type="text" id="typePName" pofield="typePName" readonly="readonly"  class="form-control"/>               		
											<input type="hidden" pofield="tableNewsType" id="typePid" />
		                                	<span class="input-group-btn"> 
		                                		  <button type="button" class="btn btn-primary"  data-toggle="modal" data-target="#myModal">选择类型</button> 
		                                 	</span>
		                             	</div>
                            	</div>--%>
								<label class="col-sm-2 control-label">定期发送：</label>
								<div class="col-sm-4">
									<input style="width:20px" type="checkbox" value="1" id="dqState"/>
								</div>
	                        </div>      
	                        <div class="form-group">
	                            <label class="col-sm-2 control-label">发布开始时间：</label>
	                            <div class="col-sm-4 ">
                                    	<input type="text" class="form-control" pofield="tableStartCjsj" onchange="wdate()" id="tableStartCjsj" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})">
	                            </div>
	                            <label class="col-sm-2 control-label">发布结束时间：</label>
	                            <div class="col-sm-4 ">
                                    	<input type="text" class="form-control" pofield="tableEndCjsj"  id="tableEndCjsj" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})">
	                            </div>
	                        </div>   
	                         <div class="form-group">
	                         	  	<label class="col-sm-2 control-label">显示条数：</label>
		                           	<div class="col-sm-10">
		                                  	<input type="text" class="form-control" pofield="pageSize"  id="pageSize"  value="15" >
		                           	</div>  
	                         </div>
	                        <div class="text-center" style="border-top: 1px dashed #D7D7D7;padding-top: 10px;">
	                        	<a class="btn btn-primary system newsTable_list" onclick="findTable();">查询</a>
	                        	<a class="btn btn-default system newsTable_list" onclick="findReset();">重置</a>                           
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
						<a class="label label-danger pull-right system newsTable_delete" onclick="newsTable_del();">批量删除</a>
	                    <a class="label label-success pull-right system newsTable_add" onclick="forAddTable();">新增</a>
					</div>	
	                <div class="ibox-content tabble-c">
	                    <div class="table-responsive">
	                        <table class="table table-striped" id="newsTable">
	                            <thead>
		                             <tr>
									 	<th style="text-align:center;"><input type="checkbox" id="selectAll" style="width:22px;height:22px" onclick="selectAll()"/></th>
									 	<th style="text-align: center;">序号</th>
			            				<th style="text-align: center;">标题</th>
			            				<th style="text-align: center;">类型</th>
			            				<th style="text-align: center;">创建人</th>
			             				<th style="text-align: center;">创建时间</th>
			            				<th style="text-align: center;">操作</th>
									</tr>
	                            </thead>
	                            <tbody id="newsTable_list">
	
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
	<jsp:include page="/intercept/news/newsType/news_ChangeType.jsp" flush="false" />
	<jsp:include page="/open/mould/news/newsTable/news_table_tmp.jsp" flush="false" />
	<script type="text/javascript">
	   function uiFromTmp(id, data,idx) {
		    var ui = $($("#" + id + "").html());
		    ui.data("vo", data);
		    ui.find("td[pofield=id] input[name=chckBox]").val(data.id);
	        ui.find("td[pofield=num]").text(idx+1);
	        ui.find("td[pofield=tableTitle]").text(data.tableTitle);
	        ui.find("td[pofield=strTableType]").text(data.strTableType);
	        ui.find("td[pofield=tableCjrxm]").text(data.tableCjrxm);
	        ui.find("td[pofield=strTableCjsj]").text(data.strTableCjsj);
	        $("#newsTable_list").append(ui);
	        ui.fadeIn();
	    }
	
	</script>
	<script type="text/javascript">
		var qvo={};
	    var vo={};
	    <!--list-->
	    $(function(){
	        findCmmInit();
	    	findNewsType();
	    })
		function findCmmInit(){
            doAjaxPost('<%=request.getContextPath()%>/newsTable.action?act=findCmmInit',{},callDataToInit)
        }
        function callDataToInit(data){
            if(data.map!=null){
                if(data.map.newsType!=null){
                    $("#tableNewsType").html("");
                    var option = document.createElement('option');
                    option.setAttribute("value","");
                    option.innerText = "--请选择--";
                    document.getElementById("tableNewsType").appendChild(option);
                    $.each(data.map.newsType,function(k,v){
                        dataUiCodeGroup("select","tableNewsType",v.labelTitle,v.labelValue);
                    })
                }
            }
            findTable();
        }
	
	    //重置
	    function findReset(){
        	$('#tableNewsTitle').val("");
        	$('#typePName').val("");
        	$('#typePid').val("");
        	$('#tableEndCjsj').val("");
        	$('#tableStartCjsj').val("");
        }
	    
	    //查询
	    function findTable(){
	        qvo = uiToData("form_qvo",qvo);//界面值绑定到变量
			qvo["tableNewsType"]=$("#tableNewsType").val();
            if ($('#dqState').prop("checked")) {
                qvo["dqState"]=$("#dqState").val();
            }
	        doAjaxPost('<%=request.getContextPath()%>/newsTable.action?act=list',{qvoJson:JSON.stringify(qvo)},callDataToUi);
	    }
	    function callDataToUi(data){
	        dataToUi(data.qvo,"form_qvo");//数据绑定到界面
	        qvodesc("qvodesc");
	        $("#newsTable tbody").html("");
	        if(data.rows != null){
	        	$.each(data.rows, function(k, v) {
		            uiFromTmp("tlist", v,k);
		        });	        	
	        }
	    }
	
	    //准备新增
	    function forAddTable(){
	    	var url = '<%=request.getContextPath()%>/newsTable.action?act=forAddOrEdit&handle=add';
	    	defualtHref(url);
	    }
	    //准备修改
	    function forModifyTable(ui){
	        var url = '<%=request.getContextPath()%>/newsTable.action?act=forAddOrEdit&handle=modify&vo.id='+$(ui).data("vo").id;
	        defualtHref(url);
	    }
	    //查看
	    function forLookTable(ui){
	        var url = '<%=request.getContextPath()%>/newsTable.action?act=forAddOrEdit&handle=look&vo.id='+$(ui).data("vo").id;
	        defualtHref(url);
	    }
	    
	 	//全选和全选取消
	    function selectAll(){
	    	if($("#selectAll").prop("checked")){
	    		$.each($("#newsTable_list").find("input[name=chckBox]"),function(k,v){
	    			$(this).prop("checked",true);
	    		});
	    	}else{
	    		$.each($("#newsTable_list").find("input[name=chckBox]"),function(k,v){
	    			$(this).prop("checked",false);
	    		});
	    	}
	    }
	  
	    //删除页面获取删除ID
	    function newsTable_del(){
	    	var ids="";
	    	$.each($("#newsTable_list").find("input[name=chckBox]"),function(k,v){
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
        	$.post('<%=request.getContextPath()%>/newsTable.action?act=delete',{'id':id},function(data){
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
	  //设置推送
        function setTs(ui){
            var url = '<%=request.getContextPath()%>/newsTable.action?act=forSet&handle=modify&vo.id='+$(ui).data("vo").id;
            defualtHref(url);
		}

	</script>
	
</body>
</html>
