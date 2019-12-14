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
	                            <label class="col-sm-2 control-label">显示条数：</label>
	                            <div class="col-sm-10">
	                                <input type="text" class="form-control" pofield="pageSize"  id="pageSize"  value="15" >
	                            </div>
	                        </div>      
	                        <div class="text-center" style="border-top: 1px dashed #D7D7D7;padding-top: 10px;">
	                        	<a class="btn btn-primary system cdsm_list" onclick="findTable();">查询</a>
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
	            	
					</div>	
	                <div class="ibox-content tabble-c">
	                    <div class="table-responsive">
	                        <table class="table table-striped" id="mytable">
	                            <thead>
	                            <tr>
                                	<th style="text-align: center;width:15%">序号</th>
									<th style="text-align: center;width:15%">手机号码</th>
									<th style="text-align: center;width:40%">发送内容</th>
									<th style="text-align: center;width:10%">创建人</th>
									<th style="text-align: center;width:10%">发送时间</th>
									<th style="text-align: center;width:10%">回执状态</th>
	                            </tr>
	                            </thead>
	                            <tbody id="shortMessage">
	
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
<jsp:include page="/open/mould/cd/cdsm/cdsm_tmp.jsp" flush="false" />	
<script type="text/javascript">
	function uiFromTmp(id, data,idx) {
		var t = $($("#" + id + "").html());
		dataToUiTmp_idlsttr_rs(t, data,idx);
		$("#shortMessage").append(t);
		t.fadeIn();
	}
	function dataToUiTmp_idlsttr_rs(ui, data,idx) {
		//alert(JSON.stringify(data));
		ui.data("vo", data);
		ui.find("td[pofield=num]").text(idx+1);
		ui.find("td[pofield=msgPhone]").text(data.msgPhone);
		ui.find("td[pofield=msgContent]").text(data.msgContent);
		ui.find("td[pofield=cjr]").text(data.cjrName);
		ui.find("td[pofield=cjsj]").text(data.strCjsj);
		ui.find("td[pofield=msgCzId]").text(data.msgCzId); 
	}

</script>
<script type="text/javascript">
	var qvo={};
	var vo={};
	$(function(){
		findTable();
	})
	//查询
    function findTable(){
		qvo = uiToData("form_qvo",qvo);//界面值绑定到变量
        doAjaxPost('<%=request.getContextPath()%>/cdsm.action?act=list',{qvoJson:JSON.stringify(qvo)},callDataToUi);
    }
    function callDataToUi(data){
    	dataToUi(data.qvo,"form_qvo");//数据绑定到界面
        qvodesc("qvodesc");
        $("#shortMessage").html("");
        $.each(data.rows, function(k, v) {
            uiFromTmp("shortMessage_tlist", v,k);
        });
    }
</script>
</body>
</html>
