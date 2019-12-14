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
<title>更多预警</title>
<%@ include file="/open/commonjs/tldHtml.jsp"%>
<%@ include file="/open/commonjs/roleson.jsp"%>
</head>
<body>
<<div class="wrapper wrapper-content">
   <div class="col-sm-12">
       <div class="ibox float-e-margins">
           <div clas预警中心</h5>
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
                           <th>内容</th>
                           <th>创建时间</th>
                         
                       </tr>
                       </thead>
                       <tbody id="yj_list">
                      		
                      		
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
	function uiTmpToContent(id,data,idx){
		var t = $($("#" + id + "").html());
		dataToUiTmp_idlsttr_rs(t, data,idx);
		$("#yj_list").append(t);
		t.fadeIn();
	}
	function dataToUiTmp_idlsttr_rs(ui, data,idx) {
		ui.data("vo", data);
		ui.find("td[pofield=id]").text(data.id);
		ui.find("td[pofield=contentId]").text(data.msgContentId);
		ui.find("td[pofield=title]").text(data.msgTitle);
		ui.find("td[pofield=idx]").text(idx+1);
		ui.find("td[pofield=content]").html(data.msgText)
		ui.find("td[pofield=createTime]").text(data.strMsgCreateDate)
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
		doAjaxPost('<%=request.getContextPath()%>/flow.action?act=findMsgAlertCmm',{qvoJson:JSON.stringify(qvo)},callDataToAlert);
	}
	function callDataToAlert(data){
		dataToUi(data.qvo,"form_qvo");//数据绑定到界面(分页)
		qvodesc("qvodesc");
		$("#yj_list").html("");
		if(data.rows!=null){
			$.each(data.rows,function(k,v){
				uiTmpToContent("content_tlist",v,k);
			})
		}
		
	}
	//点击返回
	function back(){
		history.go(-1);
	}

</script>
</body>
</html>
