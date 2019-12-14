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
<title>更多病原微生物送检记录跟踪</title>
<%@ include file="/open/commonjs/tldHtml.jsp"%>
<%@ include file="/open/commonjs/roleson.jsp"%>

</head>
<body>
<div class="wrapper wrapper-content">
   <div class="col-sm-12">
       <div class="ibox float-e-margins">
           <div class="ibox-title">
               <h5>病原微生物送检记录跟踪</h5>
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
                           <th>送检人</th>
                           <th>送样时间</th>
                           <th>当前状态</th>
                           <th>操作</th>
                       </tr>
                       </thead>
                       <tbody id="Sources">
                      		
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
	function uiTmpToSources(id,data,idx){
		var t = $($("#" + id + "").html());
		dataToUiTmp_idlsttr_sources(t, data,idx);
		$("#Sources").append(t);
		t.fadeIn();
	}
	function dataToUiTmp_idlsttr_sources(ui,data,idx){
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
		ui.find("td[pofield=state]").text(data.stateName);
		if(data.type==2){//艾滋病
			
			if(data.state<=5||data.state==100||data.state==9||data.state==10){//打印送检单
				ui.find("button[pofield=print1]").css("display","inline-block");
			}else if(data.state<=8||data.state==98){//打印复检报告
				ui.find("button[pofield=print2]").css("display","inline-block");
			}else if(data.state==99){//打印确证报告
				ui.find("button[pofield=print3]").css("display","inline-block");
			}
			/* if("1"==data.state){//送检或者审核退回后才有修改选项
				ui.find("button[pofield=modify]").css("display","inline-block");
				ui.find("button[pofield=delete]").css("display","inline-block");
			} else if(data.state==10){//确认退样
				ui.find("button[pofield=confirm]").css("display","inline-block");
				ui.find("button[pofield=print1]").css("display","inline-block");
			} else if(data.state==2){//审核
				ui.find("button[pofield=audit]").css("display","inline-block");
			} else if(data.state==3){//接样
				ui.find("button[pofield=receiveSample]").css("display","inline-block");
			} else if(data.state==4){//复检检测
				ui.find("button[pofield=fjJc]").css("display","inline-block");
			} else if(data.state==5){//复检审核
				ui.find("button[pofield=fjSh]").css("display","inline-block");
			}else if(data.state==6){//确证检测
				ui.find("button[pofield=qzJc]").css("display","inline-block");
			}else if(data.state==7){//确证审核
				ui.find("button[pofield=qzSh]").css("display","inline-block");
			}else if(data.state==8){//确证签发
				ui.find("button[pofield=qzQf]").css("display","inline-block");
			}else if("9"==data.state){//审核退回
				ui.find("button[pofield=modify]").css("display","inline-block");
			} */
			
		
		}else if(data.type==3){//通用疾病
			if(data.state==1||data.state==3||data.state==4||data.state==10){//打印送检单
				ui.find("button[pofield=print1]").fadeIn();
			}else {//打印检验报告
				ui.find("button[pofield=print2]").html("<i class='fa fa-print'></i>打印检验报告");
				ui.find("button[pofield=print2]").fadeIn();
				
			}
			/* if("1"==data.state){//送检才有修改选项
				ui.find("button[pofield=modify]").css("display","inline-block");
				ui.find("button[pofield=delete]").css("display","inline-block");
			}else if(data.state==3){//接样
				ui.find("button[pofield=receiveSample]").css("display","inline-block");
			}else if(data.state==4){//检验
				ui.find("button[pofield=inspect]").css("display","inline-block");
			}else if(data.state==10){//确认退样
				ui.find("button[pofield=confirm]").css("display","inline-block");
			}else if(data.state==5){//审核
				ui.find("button[pofield=audit]").css("display","inline-block");
			}else if(data.state==6){//签发
				ui.find("button[pofield=sign]").css("display","inline-block");
			} */
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
		doAjaxPost('<%=request.getContextPath()%>/flow.action?act=sourcesListCmm',{qvoJson:JSON.stringify(qvo)},callDataToSources);
	}
	function callDataToSources(data){
		dataToUi(data.qvo,"form_qvo");//数据绑定到界面(分页)
		qvodesc("qvodesc");
		$("#Sources").html("");
		if(data.rows!=null){
			$.each(data.rows,function(k,v){
				uiTmpToSources("sources_tlist",v,k);
			})
		}
		
	}

	
</script>
</body>
</html>
