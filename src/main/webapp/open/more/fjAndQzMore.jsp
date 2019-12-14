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
<title>更多复检和确证信息</title>
<%@ include file="/open/commonjs/tldHtml.jsp"%>
<%@ include file="/open/commonjs/roleson.jsp"%>
</head>
<body>
<div class="wrapper wrapper-content">
	<div class="row" style="display: none;">
		<div class="col-sm-12">
			<div class="ibox float-e-margins">							                
                <div class="ibox-content" id="form_qvo">
                    <form method="get" class="form-horizontal">
                        <input type="hidden" pofield="itemCount" id="itemCount" value="">
                        <input type="hidden" pofield="pageCount" id="pageCount" value="">
                        <input type="hidden" pofield="pageSize" id="pageSize" value="20">
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
		               <h5>待复检和确证信息</h5>
		           </div>
		           <div class="ibox-content">
		               <div class="table-responsive clink">
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
		                       <tbody id="fjAndQz_lisdt">
		                      		
		                      		
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
<jsp:include page="/open/mould/common/content_tmp.jsp" flush="false" />
<script type="text/javascript">
function uiTmpToAidss(id,data,idx){
	//alert(JSON.stringify(data));
	var t = $($("#" + id + "").html());
	dataToUiTmp_idlsttr_aidss(t, data,idx);
	$("#fjAndQz_lisdt").append(t);
	t.fadeIn();
}
function dataToUiTmp_idlsttr_aidss(ui, data,idx){
	//alert(JSON.stringify(data));
	ui.data("vo",data);
	ui.find("td[pofield=id]").val(data.id);
	ui.find("td[pofield=idx]").text(idx+1);
	ui.find("td[pofield=spNum]").text(data.num);
	ui.find("td[pofield=name]").text(data.name);
	if(data.age==null||data.age==""){
		data.age="0";
	}
	ui.find("td[pofield=age]").text(data.ageName);
	ui.find("td[pofield=sex]").text(data.sex);
	ui.find("td[pofield=sjdw]").text(data.sendUnitName);
	ui.find("td[pofield=syr]").text(data.sendUserName);
	ui.find("td[pofield=sysj]").text(data.strSpSendDate);
	if(data.state=="4"){
		ui.find("button[pofield=fjSh]").hide();
		ui.find("button[pofield=qzJc]").hide();
		ui.find("button[pofield=qzSh]").hide();
		ui.find("button[pofield=qzQf]").hide();
		ui.find("button[pofield=print1]").show();
	}else if(data.state=="5"){
		ui.find("button[pofield=fjJc]").hide();
		ui.find("button[pofield=qzJc]").hide();
		ui.find("button[pofield=qzSh]").hide();
		ui.find("button[pofield=qzQf]").hide();
		ui.find("button[pofield=print1]").show();
	}else if(data.state=="6"){
		ui.find("button[pofield=fjJc]").hide();
		ui.find("button[pofield=fjSh]").hide();
		ui.find("button[pofield=qzSh]").hide();
		ui.find("button[pofield=qzQf]").hide();
		ui.find("button[pofield=print2]").show();
	}else if(data.state=="7"){
		ui.find("button[pofield=fjJc]").hide();
		ui.find("button[pofield=fjSh]").hide();
		ui.find("button[pofield=qzJc]").hide();
		ui.find("button[pofield=qzQf]").hide();
		ui.find("button[pofield=print2]").show();
	}else if(data.state=="8"){
		ui.find("button[pofield=fjJc]").hide();
		ui.find("button[pofield=fjSh]").hide();
		ui.find("button[pofield=qzSh]").hide();
		ui.find("button[pofield=qzJc]").hide();
		ui.find("button[pofield=print2]").show();
	}
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
		doAjaxPost('<%=request.getContextPath()%>/flow.action?act=fjOrQzCmm',{qvoJson:JSON.stringify(qvo)},callDataToAids);
	}
	function callDataToAids(data){
		dataToUi(data.qvo,"form_qvo");//数据绑定到界面(分页)
		qvodesc("qvodesc");
		$("#fjAndQz_lisdt").html("");
		if(data.rows!=null){
			$.each(data.rows,function(k,v){
				uiTmpToAidss("aids_tlist",v,k);
			})
		}				
	}
	function forLookTableTt(ui){
		var state=$(ui).data("vo").state;
		if(state=="4"||state=="5"){
			var url = '<%=request.getContextPath()%>/recheck.action?act=forCmmfj&handle=look&isBack=hide&vo.id='+$(ui).data("vo").id;
			var code = $(ui).data("vo").id;
			var title = "检测单";
			comTabs(url,code,title);
		}else if(state=="6"||state=="7"||state=="8"){
			var url = '<%=request.getContextPath()%>/recheck.action?act=forCmmqz&handle=look&isBack=hide&vo.id='+$(ui).data("vo").id;
			var code = $(ui).data("vo").id;
			var title = "检测单";
			comTabs(url,code,title);
		}
		
	}
	function forFjJcTable(ui){
		var url = '<%=request.getContextPath()%>/recheck.action?act=forCmmfj&handle=fj&isBack=hide&vo.id='+$(ui).data("vo").id;
		var code = $(ui).data("vo").id+"1";
		var title = "复检（检测）";
		comTabs(url,code,title);
	}
	function forFjShTable(ui){
		doAjaxPost('<%=request.getContextPath()%>/aids.action?act=fjShCmm',{id:$(ui).data("vo").id},function(data){
			if(data.msg == 'true'){
				var url = '<%=request.getContextPath()%>/recheck.action?act=forCmmfj&handle=fj&isBack=hide&vo.id='+$(ui).data("vo").id;
				var code = $(ui).data("vo").id+"2";
				var title = "复检（审核）";
				comTabs(url,code,title);
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
			
		});
		
	}
	function forQzJcTable(ui){
		var url = '<%=request.getContextPath()%>/recheck.action?act=forCmmqz&handle=qz&isBack=hide&vo.id='+$(ui).data("vo").id;
		var code = $(ui).data("vo").id+"3";
		var title = "确证（检测）";
		comTabs(url,code,title);
	}
	function forQzShTable(ui){
		doAjaxPost('<%=request.getContextPath()%>/aids.action?act=qzFhCmm',{id:$(ui).data("vo").id},function(data){
			if(data.msg == 'true'){
				var url = '<%=request.getContextPath()%>/recheck.action?act=forCmmqz&handle=qz&isBack=hide&vo.id='+$(ui).data("vo").id;
				var code = $(ui).data("vo").id+"4";
				var title = "确证（复核）";
				comTabs(url,code,title);
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
			
		})
	}
	function forQzQfTable(ui){
		doAjaxPost('<%=request.getContextPath()%>/aids.action?act=qzQfCmm',{id:$(ui).data("vo").id},function(data){
			if(data.msg == 'true'){
				var url = '<%=request.getContextPath()%>/recheck.action?act=forCmmqz&handle=qz&isBack=hide&vo.id='+$(ui).data("vo").id;
				var code = $(ui).data("vo").id+"5";
				var title = "确证（签发）";
				comTabs(url,code,title);
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
			
		})
		
	}
</script>
</body>
</html>
