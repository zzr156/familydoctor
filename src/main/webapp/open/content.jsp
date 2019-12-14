<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>工作台首頁</title>
	<%@ include file="/open/commonjs/tldHtml.jsp"%>
	<%@ include file="/open/commonjs/roleson.jsp"%>
	<style>
        .ibox .ibox-title{color:#000;}
        .ibox .ibox-content{ color:#000;font-size: 13px;}
        .ibox .ibox-content .table{font-size: 12px;}
        .ibox .clink{line-height:30px;}
		.ibox .clink p{line-height:24px;}       
        .ibox .clink a{ color:#0000ff;text-decoration: underline;}
        .ibox .clink a:hover{text-decoration: none; }
    </style>
  </head>

 <body>
<div class="wrapper wrapper-content">
	<input type="hidden" id="testxiaom"  value="xiaom"/>
    <div class="row">
    	<div id="form_qvo">
    		<input type="hidden" pofield="itemCount" id="itemCount" value="">
            <input type="hidden" pofield="pageCount" id="pageCount" value="">
            <input type="hidden" pofield="pageStartNo" id="pageStartNo" value="1">
            <input type="hidden" pofield="pageSize" id="pageSize" value="8">
    	</div>
        <div class="col-sm-4" id="msgId" style="display: none;">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <!-- <span class="label label-info pull-right"><i class="glyphicon glyphicon-alert"></i></span> -->
                     <span class="label label-default pull-right"><a style="color: #fff;font-weight: normal;" onclick="msgMore();">操作</a></span> 
                    <h5>消息中心</h5>
                </div>
                <div class="ibox-content clink">
                	<div style="min-height: 180px;" id="msg">
	                   <!-- <p><i class="fa fa-caret-right"></i>编号 <span style="color:#000;">BY (2016)0002</span>号 的送检单检验结果已出，请及时<a>查阅</a>。</p>
	                   <p><i class="fa fa-caret-right"></i>编号 <span style="color:#000;">BY (2016)0004</span>号 的送检单即将超过样品保鲜日期，请及时<a>送样</a>。</p>
	                   <p><i class="fa fa-caret-right"></i>编号 <span style="color:#000;">BY (2016)0005</span>号 的送检单<span class="text-danger">已超过样品保鲜日期</span>。</p>
	                   <p><i class="fa fa-caret-right"></i>编号 <span style="color:#000;">BY (2016)0007</span>号 的送检单距规定的报告签发时间还余2小时，请及时<a>签发</a>。</p> -->
                    </div>
                </div>
                <div class="ibox-title" id="msgMore" onclick="msgMore();" style="display: none;">
                	<span class="btn btn-block btn-sm btn-default pull-center" style="background-color: #ace18e;border-color: #f8f8f8;"><a style="color: #fff;font-weight: normal;"  >查看更多</a></span>
                    <!-- <span class="label label-info pull-right"><a style="color: #fff;font-weight: normal;" onclick="msgMore();">查看更多</a></span> -->
               </div>
           </div>
       </div>
       <div class="col-sm-4" id="yjId" style="display: none;">
           <div class="ibox float-e-margins">
               <div class="ibox-title">
                   <!-- <span class="label label-warning pull-right"><i class="glyphicon glyphicon-alert"></i></span>
                   <span class="label label-info pull-right"><i class="glyphicon glyphicon-alert"></i></span> -->
                   <span class="label label-default pull-right"><a style="color: #fff;font-weight: normal;"  onclick="yjMore();">操作</a></span> 
                    <h5>预警中心</h5>
                </div>
                <div class="ibox-content">
                	<div style="min-height: 180px;" id="alertMsg">
                    	<!-- <p><i class="fa fa-caret-right"></i>编号 BY (2016)0003号 的送检单中所检样品检验的结果为<span class="text-danger">阳性</span></p>
						<p><i class="fa fa-caret-right"></i>编号 BY (2016)0004号 的送检单中所检样品检验的结果为<span class="text-success">阴性</span></p> -->
					</div>
                </div>
                <div class="ibox-title" id="yjMore" style="display: none;" onclick="yjMore();">
                	<span class="btn btn-block btn-sm btn-default pull-center" style="background-color: #ace18e;border-color: #f8f8f8;"><a style="color: #fff;font-weight: normal;"  >查看更多</a></span>
                     <!-- <span class="label label-info pull-right"><a style="color: #fff;font-weight: normal;" onclick="yjMore();">更多</a></span> -->
                </div>
            </div>
        </div>
        <div class="col-sm-4" id="jyxmId" style="display: none;">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <span class="label label-info pull-right"><i class="glyphicon glyphicon-alert"></i></span>
                    <h5>2016年检验项目统计</h5>
                </div>
                <div class="ibox-content">
                    <div class="flot-chart">
                        <div class="flot-chart-content" id="flot-bar-chart"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row" id="sourcesId" style="display: none;">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <!-- <span class="label label-default pull-right"><a style="color: #fff;font-weight: normal;" onclick="sjjlMore();">更多</a></span> -->
                    <h5>病原微生物送检记录跟踪</h5>
                </div>
                <div class="ibox-content">
                    <div class="table-responsive">
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
                            <tbody id="flowingSources">
                           		
                           		
                            </tbody>
                            <tfoot>
                            	<tr onclick="sjjlMore();">
	                            	<td id="sjjlMore" style="display: none;text-align: center;" colspan="10">
	                            		<span class="btn btn-block btn-sm btn-default pull-center" style="background-color: #ace18e;border-color: #f8f8f8;"><a style="color: #fff;font-weight: normal;"  >查看更多</a></span>
	                            	</td>
                            	</tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row" id="dshsjId" style="display: none;">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <!-- <span class="label label-default pull-right"><a style="color: #fff;font-weight: normal;" onclick="shsjMore()">更多</a></span> -->
                    <h5>待审核送检信息</h5>
                </div>
                <div class="ibox-content">
                    <div class="table-responsive">
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
                            <tbody id="waitAudit">
                           
                            </tbody>
                            <tfoot>
                            	<tr onclick="shsjMore();">
	                            	<td id="shsjMore" style="display: none;text-align: center;" colspan="9">
	                            		<span class="btn btn-block btn-sm btn-default pull-center" style="background-color: #ace18e;border-color: #f8f8f8;"><a style="color: #fff;font-weight: normal;"  >查看更多</a></span>
	                            	</td>
                            	</tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row" id="dModifyId" style="display: none;">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <!-- <span class="label label-default pull-right"><a style="color: #fff;font-weight: normal;" onclick="modifyMore()">更多</a></span> -->
                    <h5>待修改信息</h5>
                </div>
                <div class="ibox-content">
                    <div class="table-responsive">
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
                            <tbody id="waitModify">
                           
                            </tbody>
                            <tfoot>
                            	<tr onclick="modifyMore();">
	                            	<td id="modifyMore" style="display: none;text-align: center;" colspan="9">
	                            		<span class="btn btn-block btn-sm btn-default pull-center" style="background-color: #ace18e;border-color: #f8f8f8;"><a style="color: #fff;font-weight: normal;"  >查看更多</a></span>
	                            	</td>
                            	</tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row" id="djyId" style="display: none;">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <!-- <span class="label label-default pull-right"><a style="color: #fff;font-weight: normal;" onclick="jyMore();">更多</a></span> -->
                    <h5>待接样信息</h5>
                </div>
                <div class="ibox-content">
                    <div class="table-responsive">
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
                            <tbody id="waitSample">
                            
                            </tbody>
                            <tfoot>
                            	<tr onclick="jyMore();">
	                            	<td id="jyMore" style="display: none;text-align: center;" colspan="9">
	                            		<span class="btn btn-block btn-sm btn-default pull-center" style="background-color: #ace18e;border-color: #f8f8f8;"><a style="color: #fff;font-weight: normal;"  >查看更多</a></span>
	                            	</td>
                            	</tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
     <div class="row" id="dtyId" style="display: none;">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <span class="label label-default pull-right"><a style="color: #fff;font-weight: normal;" onclick="tyMore();">更多</a></span>
                    <h5>待确认退样信息</h5>
                </div>
                <div class="ibox-content">
                    <div class="table-responsive">
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
                            <tbody id="exitSample"> 
                            
                            </tbody>
                            <tfoot>
                            	<tr onclick="tyMore();">
	                            	<td id="tyMore" style="display: none;text-align: center;" colspan="9">
	                            		<span class="btn btn-block btn-sm btn-default pull-center" style="background-color: #ace18e;border-color: #f8f8f8;"><a style="color: #fff;font-weight: normal;"  >查看更多</a></span>
	                            	</td>
                            	</tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
     <div class="row" id="jyId" style="display: none;">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <!-- <span class="label label-default pull-right"><a style="color: #fff;font-weight: normal;" onclick="djyMore();">更多</a></span> -->
                    <h5>待检验信息</h5>
                </div>
                <div class="ibox-content">
                    <div class="table-responsive">
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
                            <tbody id="waitInspection">
                            
                            </tbody>
                            <tfoot>
                            	<tr onclick="djyMore();">
	                            	<td id="djyMore" style="display: none;text-align: center;" colspan="9">
	                            		<span class="btn btn-block btn-sm btn-default pull-center" style="background-color: #ace18e;border-color: #f8f8f8;"><a style="color: #fff;font-weight: normal;"  >查看更多</a></span>
	                            	</td>
                            	</tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
     <div class="row" id="dshjybgId" style="display: none;">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                   <!--  <span class="label label-default pull-right"><a style="color: #fff;font-weight: normal;" onclick="shjyMore();">更多</a></span> -->
                    <h5>待审核检验报告信息</h5>
                </div>
                <div class="ibox-content">
                    <div class="table-responsive">
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
                            <tbody id="waitAuditInspectionReport">
                            
                            </tbody>
                            <tfoot>
                            	<tr onclick="shjyMore();">
	                            	<td id="shjyMore" style="display: none;text-align: center;" colspan="9">
	                            		<span class="btn btn-block btn-sm btn-default pull-center" style="background-color: #ace18e;border-color: #f8f8f8;"><a style="color: #fff;font-weight: normal;"  >查看更多</a></span>
	                            	</td>
                            	</tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
     <div class="row" id="dqfjybgId" style="display: none;">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <!-- <span class="label label-default pull-right"><a style="color: #fff;font-weight: normal;" onclick="qfjy();">更多</a></span> -->
                    <h5>待签发检验报告信息</h5>
                </div>
                <div class="ibox-content">
                    <div class="table-responsive">
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
                            <tbody id="waitIssuance">
                            
                            </tbody>
                            <tfoot>
                            	<tr onclick="qfjy();">
	                            	<td id="qfjy" style="display: none;text-align: center;" colspan="9">
	                            		<span class="btn btn-block btn-sm btn-default pull-center" style="background-color: #ace18e;border-color: #f8f8f8;"><a style="color: #fff;font-weight: normal;"  >查看更多</a></span>
	                            	</td>
                            	</tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row" id="dFjAndQzId" style="display: none;">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                   <!--  <span class="label label-default pull-right"><a style="color: #fff;font-weight: normal;" onclick="fjAndQzMore();">更多</a></span> -->
                    <h5>待复检和确证信息</h5>
                </div>
                <div class="ibox-content">
                    <div class="table-responsive">
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
                            <tbody id="waitFjAndQz">
                            
                            </tbody>
                            <tfoot>
                            	<tr onclick="fjAndQzMore();">
	                            	<td id="fjAndQzMore" style="display: none;text-align: center;" colspan="9">
	                            		<span class="btn btn-block btn-sm btn-default pull-center" style="background-color: #ace18e;border-color: #f8f8f8;"><a style="color: #fff;font-weight: normal;"  >查看更多</a></span>
	                            	</td>
                            	</tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="/open/mould/common/content_tmp.jsp" flush="false" />
<script type="text/javascript">
	
	function uiTmpToContent(name,id,data,idx){
		var t = $($("#" + id + "").html());
		dataToUiTmp_idlsttr_rs(t, data,idx);
		$("#"+name+"").append(t);
		t.fadeIn();
	}
	function dataToUiTmp_idlsttr_rs(ui, data,idx) {
		ui.data("vo", data);
		ui.find("td[pofield=id]").text(data.id);
		ui.find("td[pofield=]").text();
		
	}
	
	function uiTmpToSources(id,data,idx){
		var t = $($("#" + id + "").html());
		dataToUiTmp_idlsttr_sources(t, data,idx);
		$("#flowingSources").append(t);
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
			}else if(data.state==9){//审核退回
				ui.find("button[pofield=modify]").css("display","inline-block");
			} */
			
		
		}else if(data.type==3){//通用疾病
			if(data.state==1||data.state==3||data.state==4||data.state==10){//打印送检单
				ui.find("button[pofield=print1]").fadeIn();
				
			}else {//打印检验报告
				ui.find("button[pofield=print2]").html("<i class='fa fa-print'></i>打印检验报告");
				ui.find("button[pofield=print2]").fadeIn();
				
			}
			/* if("1"==data.state){//送检才有修改和删除选项
				ui.find("button[pofield=modify]").css("display","inline-block");
				ui.find("button[pofield=delete]").fadeIn();
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
	
	function uiTmpToAudit(id,data,idx){
		var t = $($("#audit_tlist").html());
		dataToUiTmp_idlsttr_Audit(t, data,idx);
		$("#" + id ).append(t);
		t.fadeIn();
		
	}
	function dataToUiTmp_idlsttr_Audit(ui,data,idx){
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
	function uiTmpToModify(id,data,idx){
		var t = $($("#" + id + "").html());
		dataToUiTmp_idlsttr_Modify(t, data,idx);
		$("#waitModify").append(t);
		t.fadeIn();
	}
	function dataToUiTmp_idlsttr_Modify(ui,data,idx){
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
	function uiTmpToWait(data,idx){
		var t = $($("#wait_tlist").html());
		dataToUiTmp_idlsttr_wait(t, data,idx);
		$("#waitSample").append(t);
		t.fadeIn();
	}
	function dataToUiTmp_idlsttr_wait(ui,data,idx){
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
	function uiTmpToTmpCommon(id,data,idx){
		var ui = $($("#common_tlist").html());
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
		if(data.state==10){
			ui.find("button[pofield=look]").fadeOut();
			ui.find("button[pofield=print1]").css("display","inline-block");
			ui.find("button[pofield=confirm]").css("display","inline-block");
		}
		if(data.state==4){
			ui.find("button[pofield=print1]").css("display","inline-block");
			ui.find("button[pofield=inspect]").css("display","inline-block");
		}
		$("#"+id).append(ui);
		ui.fadeIn();
	}
	
	function uiTmpToAids(id,data,idx){
		var t = $($("#" + id + "").html());
		dataToUiTmp_idlsttr_aids(t, data,idx);
		$("#waitFjAndQz").append(t);
		t.fadeIn();
	}
	function dataToUiTmp_idlsttr_aids(ui, data,idx){
		ui.data("vo",data);
		ui.find("td[pofield=id]").val(data.id);
		ui.find("td[pofield=idx]").text(idx+1);
		ui.find("td[pofield=spNum]").text(data.num);
		ui.find("td[pofield=name]").text(data.name);
		ui.find("td[pofield=age]").text(data.ageName);
		ui.find("td[pofield=sex]").text(data.sexName);
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
	//待检验,审核，签发
	function uiTmpToTmpJy(id,data,idx){
		var ui = $($("#waiJy_tlist").html());
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
		if(data.state==4){
			ui.find("button[pofield=confirm1]").css("display","inline-block");
			ui.find("button[pofield=print1]").css("display","inline-block");
		}else if(data.state==5){
			ui.find("button[pofield=confirm2]").css("display","inline-block");
			ui.find("button[pofield=print2]").css("display","inline-block");
		}else if(data.state==6){
			ui.find("button[pofield=confirm3]").css("display","inline-block");
			ui.find("button[pofield=print3]").css("display","inline-block");
		}
		$("#"+id).append(ui);
		ui.fadeIn();
	}
</script>
<script type="text/javascript">
	var qvo={};
	$(function(){
		//查找工作台功能权限
		//findTable();
		//findMsg();//消息中心
		//findAlertMsg();//预警中心
	})
	function findTableAids(){
		doAjaxPost('<%=request.getContextPath()%>/recheck.action?act=jsonByOne',{},callDataToAids);
	}
	function callDataToAids(data){
		if(data!=null){
			$("#waitFjAndQz").html("");
			$.each(data,function(k,v){
				uiTmpToAids("aids_tlist",v,k);
				if(k>=7){
					$("#fjAndQzMore").show();
					return false;
				}
			})
			
		}
		
	}
	
	function findTable(){
		doAjaxPost('<%=request.getContextPath()%>/funct.action?act=jsonByOne',{},callDataToFun);
	}
	function callDataToFun(data){
		$.each(data.rows,function(k,v){
			var tableName=v.tableName;
			var state=v.state;
			if(state=="1"){
				$("#"+tableName).css("display","block");
				findCmmInit(tableName);
			}
			
		})
	}
	

	function findCmmInit(name){
		if(name=="msgId"){
			findTableMsg();//查询消息中心数据
		}else if(name=="yjId"){
			findTableYj();//查询预警中心数据
		}else if(name=="sourcesId"){
			findTableSources();//查询病原微生物送检记录跟踪数据
		}else if(name=="dshsjId"){
			findTableAudit();//待审核送检信息
		}else if(name=="djyId"){
			findTableSample();//待接样信息
		}else if(name=="dtyId"){
			findTableExit();//待确认退样信息
		}else if(name=="jyId"){
			findTableInspection();//待检验信息
		}else if(name=="dshjybgId"){
			findTableAuditInspection();//待审核检验报告信息
		}else if(name=="dqfjybgId"){
			findTableIssuance();//待签发检验报告信息
		}else if(name=="dFjAndQzId"){//待复检和确证测试
			findTableAids();
		}else if(name=="dModifyId"){//待修改
			findTableModify();
		}
	}
	function findTableModify(){
		//qvo = uiToData("form_qvo",qvo);//界面值绑定到变量
		var qvo={};
		doAjaxPost('<%=request.getContextPath()%>/flow.action?act=waitModifyCmm',{state:"9",qvoJson:JSON.stringify(qvo)},callDataToModify);
	}
	function callDataToModify(data){
		$("#waitModify").html("");
		if(data.rows!=null){
			$.each(data.rows,function(k,v){
				uiTmpToModify("waitModify_tlist",v,k);
				if(k>=7){
					$("#modifyMore").show();
					return false;
				}
			})
		}
	}
	function findTableMsg(){
		//doAjaxPost('<%=request.getContextPath()%>/dept.action?act=jsonCmmList',{},callDataToMsg);
	}
	function callDataToMsg(data){
		$("#").html("");
		$.each(data,function(k,v){
			uiTmpToContent("massage","content_tlist",v,k);
		})
	}
	
	function findTableYj(){
		//doAjaxPost('<%=request.getContextPath()%>/dept.action?act=jsonCmmList',{},callDataToYj);
	}
	function callDataToYj(data){
		$("#").html("");
		$.each(data,function(k,v){
			uiTmpToContent("warning","content_tlist",v,k);
		})
	}
	
	function findTableSources(){
		//qvo = uiToData("form_qvo",qvo);//界面值绑定到变量
		var qvo={};
		doAjaxPost('<%=request.getContextPath()%>/flow.action?act=sourcesListCmm',{qvoJson:JSON.stringify(qvo)},callDataToSources);
	}
	function callDataToSources(data){
		$("#flowingSources").html("");
		if(data.rows!=null){
			$.each(data.rows,function(k,v){
				uiTmpToSources("sources_tlist",v,k);
				if(k>=7){
					$("#sjjlMore").show();
					return false;
				}
			})
		}
		
	}
	
	function findTableAudit(){
		//qvo = uiToData("form_qvo",qvo);//界面值绑定到变量
		var qvo={};
		doAjaxPost('<%=request.getContextPath()%>/flow.action?act=shCmm',{state:"2",qvoJson:JSON.stringify(qvo)},callDataToAudit);
	}
	function callDataToAudit(data){
		$("#waitAudit").html("");
		if(data.rows!=null){
			$.each(data.rows,function(k,v){
				uiTmpToAudit("waitAudit",v,k);
				if(k>=7) {
					$("#shsjMore").show();
					return false;
				}
			})
		}
		
	}
	
	function findTableSample(){
		//qvo = uiToData("form_qvo",qvo);//界面值绑定到变量
		var qvo={};
		doAjaxPost('<%=request.getContextPath()%>/flow.action?act=stateListCmm',{state:"3",qvoJson:JSON.stringify(qvo)},callDataToSample);
	}
	function callDataToSample(data){
		$("#waitSample").html("");
		if(data.rows!=null){
			$.each(data.rows,function(k,v){
				 uiTmpToWait(v,k);
				 if(k>=7){
					 $("#jyMore").show();
					 return false;
				 }
			})
		}
		
	}
	
	function findTableExit(){
		//qvo = uiToData("form_qvo",qvo);//界面值绑定到变量
		var qvo={};
		doAjaxPost('<%=request.getContextPath()%>/flow.action?act=selfListCmm',{state:"10",qvoJson:JSON.stringify(qvo)},callDataToExit);
	}
	function callDataToExit(data){
		$("#exitSample").html("");
		if(data.rows!=null){
			$.each(data.rows,function(k,v){
				uiTmpToTmpCommon("exitSample",v,k);
				if(k>=7) {
					$("#tyMore").show();
					return false;
				}
			})
		}
	}
	//待检验
	function findTableInspection(){
		var qvo={};
		qvo["spType"]="3";
		doAjaxPost('<%=request.getContextPath()%>/flow.action?act=stateListCmm',{state:"4",qvoJson:JSON.stringify(qvo)},callDataToInspection);
	}
	function callDataToInspection(data){
		$("#waitInspection").html("");
		if(data.rows!=null){
			$.each(data.rows,function(k,v){
				uiTmpToTmpJy("waitInspection",v,k);
				if(k>=7) {
					$("#djyMore").show();
					return false;
				}
			})
		}
	}
	//待审核检验报告信息
	function findTableAuditInspection(){
		var qvo={};
		qvo["spType"]="3";
		doAjaxPost('<%=request.getContextPath()%>/flow.action?act=stateListCmm',{state:"5",qvoJson:JSON.stringify(qvo)},callDataToAuditInspection);
	}
	function callDataToAuditInspection(data){
		$("#waitAuditInspectionReport").html("");
		if(data.rows!=null){
			$.each(data.rows,function(k,v){
				uiTmpToTmpJy("waitAuditInspectionReport",v,k);
				if(k>=7){
					$("#shjyMore").show();
					return false;
				}
			})
		}
	}
	//待签发检验报告信息
	function findTableIssuance(){
		var qvo={};
		qvo["spType"]="3";
		doAjaxPost('<%=request.getContextPath()%>/flow.action?act=stateListCmm',{state:"6",qvoJson:JSON.stringify(qvo)},callDataToIssuance);
	}
	function callDataToIssuance(data){
		$("#waitIssuance").html("");
		if(data.rows!=null){
			$.each(data.rows,function(k,v){
				uiTmpToTmpJy("waitIssuance",v,k);
				if(k>=7) {
					$("#qfjy").show();
					return false;
				}
			})
		}
	}
	
	//查看更多方法
	function msgMore(){
		//window.location.href="<%=request.getContextPath()%>/open/more/msgMore.jsp";
		var url="<%=request.getContextPath()%>/open/more/msgMore.jsp";
		var code="xx";
		var title = "消息中心";
		comTabs(url,code,title);
		
	}
	
	function yjMore(){
		//window.location.href="<%=request.getContextPath()%>/open/more/yjMore.jsp";
		var url="<%=request.getContextPath()%>/open/more/yjMore.jsp";
		var code="yj";
		var title = "预警中心";
		comTabs(url,code,title);
	}
	
	function sjjlMore(){
		//window.location.href="<%=request.getContextPath()%>/open/more/sourcesMore.jsp";
		var url="<%=request.getContextPath()%>/open/more/sourcesMore.jsp";
		var code="1";
		var title = "病原微生物送检记录跟踪";
		comTabs(url,code,title);
	}
	
	function shsjMore(){
		var url="<%=request.getContextPath()%>/open/more/auditMore.jsp";
		var code="2";
		var title = "待审核信息";
		comTabs(url,code,title);
	}
	
	function jyMore(){
		var url="<%=request.getContextPath()%>/open/more/sampleMore.jsp";
		var code="3";
		var title = "待接样信息";
		comTabs(url,code,title);
	}
	
	function tyMore(){
		var url="<%=request.getContextPath()%>/open/more/tyMore.jsp";
		var code="4";
		var title = "待确认退样信息";
		comTabs(url,code,title);
	}
	function modifyMore(){
		var url="<%=request.getContextPath()%>/open/more/waitModifyMore.jsp";
		var code="9";
		var title = "待修改信息";
		comTabs(url,code,title);
	}
	
	function djyMore(){
		//window.location.href="<%=request.getContextPath()%>/open/more/jyMore.jsp";
		var url="<%=request.getContextPath()%>/open/more/jyMore.jsp";
		var code="djyxx";
		var title = "待检验";
		comTabs(url,code,title);
	}
	
	function shjyMore(){
		var url="<%=request.getContextPath()%>/open/more/shjyMore.jsp";
		var code="dshjybgxx";
		var title = "待审核检验报告信息";
		comTabs(url,code,title);
	}
	
	function qfjy(){
		//window.location.href="<%=request.getContextPath()%>/open/more/qfjyMore.jsp";
		var url="<%=request.getContextPath()%>/open/more/qfjyMore.jsp";
		var code="dqfjybgxx";
		var title = "待签发检验报告信息";
		comTabs(url,code,title);
	}
	function fjAndQzMore(){
		var url="<%=request.getContextPath()%>/open/more/fjAndQzMore.jsp";
		var code="dfjhqzxx";
		var title = "待复检和确证信息";
		comTabs(url,code,title);
	}
	
	//消息中心 查询
	function findMsg(){
	    //qvo = uiToData("form_qvo",qvo);//界面值绑定到变量
	    //qvo["pageSize"]='4';
		doAjaxPost('<%=request.getContextPath()%>/flow.action?act=findMsgCmm',{qvoJson:JSON.stringify(qvo)},function(data){
			if(data!=null&&data.rows!=null){
				$.each(data.rows,function(k,v){
					var ui = $($("#msg_list").html());
					ui.data("vo",v);
					ui.find("span[pofield=msgText]").html(v.msgText);
					$("#msg").append(ui);
					ui.fadeIn();
					if(k>=4){
						$("#msgMore").show();
						return false;
					}
				})
			}
		});
		
	}
	
	//预警中心 查询
	function findAlertMsg(){
	    /* qvo = uiToData("form_qvo",qvo);//界面值绑定到变量
	    qvo["pageSize"]='6'; */
	    var qvo={};
		doAjaxPost('<%=request.getContextPath()%>/flow.action?act=findMsgAlertCmm',{qvoJson:JSON.stringify(qvo)},function(data){
			if(data!=null&&data.rows!=null){
				$.each(data.rows,function(k,v){
					var ui = $($("#msg_list").html());
					ui.data("vo",v);
					ui.find("span[pofield=msgText]").html(v.msgText);
					$("#alertMsg").append(ui);
					ui.fadeIn();
					if(k>=4){
						$("#yjMore").show();
						return false;
					}
					
				})
			}
		});
		
	}
	
	
	
</script>

</body>
</html>
