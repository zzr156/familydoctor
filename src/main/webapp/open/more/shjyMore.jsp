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
<title>更多待审核检验报告信息</title>
<%@ include file="/open/commonjs/tldHtml.jsp"%>
<%@ include file="/open/commonjs/roleson.jsp"%>
</head>
<body>
<div class="wrapper wrapper-content">
   <div class="col-sm-12">
       <div class="ibox float-e-margins">
           <div class="ibox-title">
               <h5>待审核信息</h5>
           </div>
           <div class="ibox-content">
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
                           <th>送检人</th>
                           <th>送样时间</th>
                           <th>操作</th>
                       </tr>
                       </thead>
                       <tbody id="sh_list">
                      		
                      		
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
//待检验,审核，签发
function uiTmpToJy(id,data,idx){
	var ui = $($("#" + id + "").html());
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
	/* if(data.state==4){
		ui.find("button[pofield=look]").fadeOut();
		ui.find("button[pofield=print1]").css("display","inline-block");
		ui.find("button[pofield=confirm]").css("display","inline-block");
	} */
	ui.find("button[pofield=confirm2]").css("display","inline-block");
	ui.find("button[pofield=print2]").css("display","inline-block");
	$("#sh_list").append(ui);
	ui.fadeIn();
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
		qvo["spType"]="3";
		doAjaxPost('<%=request.getContextPath()%>/flow.action?act=stateListCmm',{state:"5",qvoJson:JSON.stringify(qvo)},callDataToJy);
	
	}
	function callDataToJy(data){
		//alert(JSON.stringify(data));
		dataToUi(data.qvo,"form_qvo");//数据绑定到界面(分页)
		qvodesc("qvodesc");
		$("#sh_list").html("");
		if(data.rows!=null){
			$.each(data.rows,function(k,v){
				uiTmpToJy("waiJy_tlist",v,k);
			})
		}
		
	}
	<%-- function confirmSh(ui){
		var url = '<%=request.getContextPath()%>/recheckOther.action?act=forCmmCheck&handle=sh&isBack=hide&vo.id='+$(ui).data("vo").id;
		var code = $(ui).data("vo").id;
		var title = "审核";
		comTabs(url,code,title);
	} --%>
</script>
</body>
</html>
