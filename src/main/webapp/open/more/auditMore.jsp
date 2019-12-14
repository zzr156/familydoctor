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
<title>更多待审核送检信息</title>
<%@ include file="/open/commonjs/tldHtml.jsp"%>
<%@ include file="/open/commonjs/roleson.jsp"%>
</head>
<body>
<div class="wrapper wrapper-content">
   <div class="col-sm-12">
       <div class="ibox float-e-margins">
           <div class="ibox-title">
               <!-- <span class="label pull-right"><a onclick="back();">返回</a></span> -->
               <h5>待审核送检信息</h5>
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
                       <tbody id="audit_list">
                      		
                      		
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
		$("#audit_list").append(t);
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
		qvo=uiToData("form_qvo",qvo);//界面值绑定到变量
		doAjaxPost('<%=request.getContextPath()%>/flow.action?act=shCmm',{state:"2",qvoJson:JSON.stringify(qvo)},callDataToAudit);
	}
	function callDataToAudit(data){
		dataToUi(data.qvo,"form_qvo");//数据绑定到界面(分页)
		qvodesc("qvodesc");
		$("#audit_list").html("");
		if(data.rows!=null){
			$.each(data.rows,function(k,v){
				uiTmpToAudit("audit_tlist",v,k);
			})
		}				
	}
	<%-- //准备查看
    function forLookTable(ui){
		var state = $(ui).data("vo").state;
		if(state=="5"||state=="6"){
			var url = '<%=request.getContextPath()%>/recheck.action?act=forCmmfj&handle=look&isBack=hide&vo.id='+$(ui).data("vo").id;
			var code = $(ui).data("vo").id;
			var title = "检测单";
			comTabs(url,code,title);
		}else if(state=="7"||state=="8"){
			var url = '<%=request.getContextPath()%>/recheck.action?act=forCmmqz&handle=look&isBack=hide&vo.id='+$(ui).data("vo").id;
			var code = $(ui).data("vo").id;
			var title = "检测单";
			comTabs(url,code,title);
		}else{
			var url = '<%=request.getContextPath()%>/aids.action?act=forLook&isBack=hide&handle=look&vo.id='+$(ui).data("vo").id;;
			var code = $(ui).data("vo").id;
			var title = "检测单";
			comTabs(url,code,title);
		}
       
    }
    //打印送检单
    function forPrintTable(ui){	
    	var url;
    	var type = $(ui).data("vo").type;
    	if(type=="3"){
    		url = '<%=request.getContextPath()%>/others.action?act=printWrite&isBack=hide&id='+$(ui).data("vo").id;
    	}else{
    		url = '<%=request.getContextPath()%>/aids.action?act=printWrite&isBack=hide&id='+$(ui).data("vo").id;
    	}
		var code = $(ui).data("vo").id+"8";
		var title = "打印送检单";
		comTabs(url,code,title);
    }
    //打印复检报告
    function forPrintTable2(ui){
    	var url;
    	var type = $(ui).data("vo").type;
    	if(type=="3"){
    		url = '<%=request.getContextPath()%>/others.action?act=printWrite2&isBack=hide&id='+$(ui).data("vo").id;
    	}else{
    		url = '<%=request.getContextPath()%>/aids.action?act=printWrite2&isBack=hide&id='+$(ui).data("vo").id;
    	}
    	//var url = '<%=request.getContextPath()%>/aids.action?act=printWrite2&isBack=hide&id='+$(ui).data("vo").id;
		var code = $(ui).data("vo").id+"9";
		var title = "打印复检报告";
		comTabs(url,code,title);	
    }
    //打印确证报告
    function forPrintTable3(ui){
	    var url;
    	var type = $(ui).data("vo").type;
    	if(type=="3"){
    		url = '<%=request.getContextPath()%>/others.action?act=printWrite3&isBack=hide&id='+$(ui).data("vo").id;
    	}else{
    		url = '<%=request.getContextPath()%>/aids.action?act=printWrite3&isBack=hide&id='+$(ui).data("vo").id;
    	}	
    	//var url = '<%=request.getContextPath()%>/aids.action?act=printWrite3&isBack=hide&id='+$(ui).data("vo").id;
		var code = $(ui).data("vo").id+"10";
		var title = "打印确证报告";
		comTabs(url,code,title);
    }
    //打印  && 次数限制
	function forPrint(ui,printType){
		var id = $(ui).data("vo").id;
		var state = $(ui).data("vo").state;
		doAjaxPost('<%=request.getContextPath()%>/flow.action?act=printCountCmm',{"id":id,"state":state},function(data){
			if(data.msg!=null){
				layer.open({
         			skin: 'layui-layer-molv',
         			closeBtn: 0,
         			title: false,
         			content:data.msg ,
         			anim: 5 ,
         			btn: ['关闭']
       		  	});
			}else if(printType==1){
				forPrintTable(ui);
			}else if(printType==2){
				forPrintTable2(ui);
			}else if(printType==3){
			    forPrintTable3(ui);
			}
		});
	}
    //准备审核
	var idd="";
	function forAudit(ui){
    	idd=$(ui).data("vo").id;
    	doAjaxPost('<%=request.getContextPath()%>/aids.action?act=isAuditCmm',{id:$(ui).data("vo").id},callDataToForAudit);
    }
    
    function callDataToForAudit(data){
    	if(data.msg == 'true'){
    		var url = '<%=request.getContextPath()%>/aids.action?act=forLook&handle=audit&isBack=hide&id='+idd;
			var code = idd+"11";
			var title = "审核";
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
    } --%>


</script>
</body>
</html>
