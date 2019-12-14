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
<title>更多待修改信息</title>
<%@ include file="/open/commonjs/tldHtml.jsp"%>
<%@ include file="/open/commonjs/roleson.jsp"%>
</head>
<body>
<div class="wrapper wrapper-content">
   <div class="col-sm-12">
       <div class="ibox float-e-margins">
           <div class="ibox-title">
               <!-- <span class="label pull-right"><a onclick="back();">返回</a></span> -->
               <h5>待修改信息</h5>
           </div>
           <div class="ibox-content" id="form_qvo">
               <div class="table-responsive clink">
               	   <input type="hidden" pofield="itemCount" id="itemCount" value="">
                   <input type="hidden" pofield="pageCount" id="pageCount" value="">
                   <input type="hidden" pofield="pageStartNo" id="pageStartNo" value="1">
                   <input type="hidden" pofield="pageSize" id="pageSize" value="15">
                   <table class="table table-striped">
                       <thead>
                       <tr>
                           <th>序号</th>
                           <th>编号</th>
                           <th>姓名</th>
                           <th>年龄</th>
                           <th>性别</th>
                           <th>送检单位</th>
                           <th>送样时间</th>
                           <th>操作</th>
                       </tr>
                       </thead>
                       <tbody id="waitModify_list">
                      		
                      		
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
<jsp:include page="/open/mould/common/content_tmp.jsp" flush="false" />
<script type="text/javascript">
	function uiTmpToAudit(id,data,idx){
		var t = $($("#" + id + "").html());
		dataToUiTmp_idlsttr_rs(t, data,idx);
		$("#waitModify_list").append(t);
		t.fadeIn();
	}
	function dataToUiTmp_idlsttr_rs(ui, data,idx) {
		ui.data("vo",data);
		ui.find("td[pofield=id]").text(data.id);
		ui.find("td[pofield=idx]").text(idx+1);
		ui.find("td[pofield=num]").text(data.num);
		ui.find("td[pofield=name]").text(data.name);
		ui.find("td[pofield=age]").text(data.ageName);
		ui.find("td[pofield=sex]").text(data.sexName);
		ui.find("td[pofield=sendUnitId]").text(data.sendUnitName);
		ui.find("td[pofield=sendUserName]").text(data.sendUserName);
		ui.find("td[pofield=sendDate]").text(data.strSpSendDate);
	}

</script>
<script type="text/javascript">
	var qvo={};
	$(function(){
		//初始加载数据
		findTable();
	})
	function findTable(){
		qvo = uiToData("form_qvo",qvo);//界面值绑定到变量
		doAjaxPost('<%=request.getContextPath()%>/flow.action?act=waitModifyCmm',{qvoJson:JSON.stringify(qvo),state:"9"},callDataToModify);
	}
	function callDataToModify(data){
		dataToUi(data.qvo,"form_qvo");//数据绑定到界面(分页)
		qvodesc("qvodesc");
		$("#waitModify_list").html("");
		if(data.rows!=null){
			$.each(data.rows,function(k,v){
				uiTmpToAudit("waitModify_tlist",v,k);
			})
		}				
	}
	


</script>
</body>
</html>
