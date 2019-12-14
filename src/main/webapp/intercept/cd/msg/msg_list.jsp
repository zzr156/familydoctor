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
<title>个人消息</title>
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
                            <label class="col-sm-2 control-label">标题：</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" pofield="msgtitle"  id="msgtitle">
                            </div>
                            <label class="col-sm-2 control-label">发送时间：</label>
                            	<div class="col-sm-4 ">
                                    <div class="input-daterange input-group">
                                        <input type="text" class="input-sm form-control"  pofield="sendstartdate"  id="sendstartdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
                                        <span class="input-group-addon">到</span>
                                        <input type="text" class="input-sm form-control" pofield="sendenddate" id="sendenddate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
                                    </div>
                            </div>
                        </div>                      
                        <div class="text-center" style="border-top: 1px dashed #D7D7D7;padding-top: 10px;">
                        	<a class="btn btn-primary system cdmsg_list" onclick="findTable();">查询</a>
                        	<a class="btn btn-default system cdmsg_list" onclick="findReset();">重置</a>                           
                        </div>
                        <input type="hidden" pofield="itemCount" id="itemCount" value="">
                        <input type="hidden" pofield="pageCount" id="pageCount" value="">
                        <input type="hidden" pofield="pageStartNo" id="pageStartNo" value="1">
                        <input type="hidden" pofield="pageSize" id="pageSize" value="15">
                 	</form>
                 </div>       
			</div>
		</div>
	</div>
	

	<div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
            	<div class="ibox-title">
					<a class="label label-danger pull-right system cdmsg_delete" onclick="cdmsg_del();">批量删除</a>
                    <a class="label label-success pull-right system cdmsg_add" onclick="forAddTable();">新增</a>
				</div>	
                <div class="ibox-content tabble-c">
                    <div class="table-responsive">
                        <table class="table table-striped" id="cdmsgtable">
                            <thead>
                            <tr>
                                <th><input type="checkbox" id="selectAll"style="width:16px;height:16px" onclick="selectAll()"/></th>
                                <th>序号</th>
                                <th>标题</th>
                                <th>消息类别</th>
                                <th>创建人</th>
                                <th>发送时间</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody id="cdmsg_list">

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

	
	<jsp:include page="/open/mould/cd/msg/msg_tmp.jsp" flush="false" />
	<script type="text/javascript">
	function uiFromTmp(id, data,idx) {
		var t = $($("#" + id + "").html());
		dataToUiTmp_idlsttr_rs(t, data,idx);
		$("#cdmsg_list").append(t);
		t.fadeIn();
	}
	function dataToUiTmp_idlsttr_rs(ui, data,idx) {
		//alert(JSON.stringify(data));
		ui.data("vo", data);
		ui.find("td[pofield=num]").text(idx+1);
		ui.find("td[pofield=id] input[name=chckBox]").val(data.id);
		ui.find("td[pofield=userId]").text(data.msgUserid);
		ui.find("td[pofield=msgTitle]").text(data.msgTitle);
		ui.find("td[pofield=msgType]").text(data.msgTypeName);
		ui.find("td[pofield=msgUserName]").text(data.msgUserName);
		ui.find("td[pofield=msgCreaterDate]").text(data.msgCreaterDate);
	}
	
	</script>
	<script type="text/javascript">
		var qvo={};
	    var vo={};
		$(function(){
			findTable();
				
		})
		//重置
	    function findReset(){
        	$('#msgtitle').val("");
        	$('#sendstartdate').val("");
        	$('#sendenddate').val("");
        }
		//查询
		function findTable(){
			qvo = uiToData("form_qvo",qvo);//界面值绑定到变量
	        doAjaxPost('<%=request.getContextPath()%>/cdmsg.action?act=list',{qvoJson:JSON.stringify(qvo)},callDataToUi);
		}
		function callDataToUi(data){
	        dataToUi(data.qvo,"form_qvo");//数据绑定到界面
	        qvodesc("qvodesc");
	        $("#cdmsg_list").html("");
	        if(data.rows != null){
	        	$.each(data.rows, function(k, v) {
		            uiFromTmp("msg_tlist", v,k);
		        });
	        }
	    }
		//准备新增
	    function forAddTable(){
	    	var url = '<%=request.getContextPath()%>/cdmsg.action?act=forAddOrEdit&handle=add';
	    	defualtHref(url);
	    }
	    //准备修改
	    function forModifyTable(ui){
	    	var url = '<%=request.getContextPath()%>/cdmsg.action?act=forAddOrEdit&handle=modify&vo.id='+$(ui).data("vo").id;
	    	defualtHref(url);
	    }
	    //查看
	    function forLookTable(ui){
	    	var url = '<%=request.getContextPath()%>/cdmsg.action?act=forAddOrEdit&handle=look&vo.id='+$(ui).data("vo").id;
	    	defualtHref(url);
	    }
	  //全选和全选取消
	    function selectAll(){
	    	if($("#selectAll").prop("checked")){
	    		$.each($("#cdmsg_list").find("input[name=chckBox]"),function(k,v){
	    			$(this).prop("checked",true);
	    		});
	    	}else{
	    		$.each($("#cdmsg_list").find("input[name=chckBox]"),function(k,v){
	    			$(this).prop("checked",false);
	    		});
	    	}
	    }
	  //删除页面获取删除用户ID
	    function cdmsg_del(){
	    	var ids="";
	    	$.each($("#cdmsg_list").find("input[name=chckBox]"),function(k,v){
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
	    			  content:'请选择需要删除的用户！' ,
	    		      anim: 5 ,
	    		      btn: ['关闭']
	    			});
	    	}else{
	    		layer.confirm('确认删除数据?', {
	    			skin: 'layui-layer-molv',
	    			  btn: ['确定','取消'] 
	    			}, function(){
	    			  del(ids);
	    			});
	    	}
	    }
	    //删除
	    function delForm(ui){
	    	layer.confirm('确认删除数据?', {
	    		skin: 'layui-layer-molv',
	    		  btn: ['确定','取消'] 
	    		}, function(){
	    		  var cdmsgId = $(ui).data("vo").id;
	    		  del(cdmsgId);
	    	});
        }
	    function del(cdmsgId){
        	$.post('<%=request.getContextPath()%>/cdmsg.action?act=delete',{'id':cdmsgId},function(data){
                var data=eval('(' + data + ')');
              	if(data.msg=="true"){
              		layer.open({
  	    			  skin: 'layui-layer-molv',
  	    			  closeBtn: 0,	    			  
  	    			  title: false,
  	    			  content: '删除成功!' ,
  	    		      anim: 5 ,
  	    		      btn: ['确定']
  	    			});              		
              		findTable();
                  } else {
                	  layer.open({
      	    			  skin: 'layui-layer-molv',
      	    			  closeBtn: 0,	    			  
      	    			  title: false,
      	    			  content: data.msg ,
      	    		      anim: 5 ,
      	    		      btn: ['确定']
      	    			});      
                      
                  }
            });
	    }
	</script>
</body>
</html>
