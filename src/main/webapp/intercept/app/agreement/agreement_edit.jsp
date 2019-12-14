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
<title>协议内容主页</title>
<%@ include file="/open/commonjs/tldHtml.jsp"%>
<%@ include file="/open/commonjs/roleson.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/intercept/ueditor/ueditor.config.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/intercept/ueditor/ueditor.all.js"></script>
</head>

<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="row">
			<div class="col-sm-12">
	            <div class="ibox float-e-margins">
	                <div class="ibox-title">
	                	<h5 id="codeAddTit"></h5>
	                    <a class="label label-primary pull-right system agreement_list"  onclick="backTable();">返回</a>
	                </div>
	                <div class="ibox-content">
							<form role="form" id="form_vo"  class="form-horizontal">
									<input type="hidden" id="id" pofield="id" name="id" value="${vo.id}" />
			          				<input type="hidden" id="handle"  name="handle" value="${handle}" />
			                		<div class="form-group">
										<label class="col-sm-2 control-label"><span class="text-danger">*</span> 标题：</label>
										<div class="col-sm-4">
											<input id="mentTitle" name="mentTitle" pofield="mentTitle" type="text" class="form-control">
										</div>
										<label class="col-sm-2 control-label"><span class="text-danger">*</span>协议类型：</label>
										<div class="col-sm-4" id="mentType">
										</div>
			                        </div>
									<div class="form-group">
										<label class="col-sm-2 control-label">内容：</label>
										<div class="col-sm-10">
											<input id="mentContent" pofield="mentContent" type="hidden"/>
											<textarea id="editor" name="editor" ></textarea>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label"><span class="text-danger">*</span>所属市：</label>
										<div class="col-sm-4" >
											<select id="mentCityId" pofield="mentCityId" class="form-control" onchange="findHosp($('#mentCityId option:selected').val())">
												<option value="">--请选择--</option>
											</select>
										</div>
										<label class="col-sm-2 control-label"><span class="text-danger">*</span>所属医院：</label>
										<div class="col-sm-4" >
											<select id="mentHospId" pofield="mentHospId" class="form-control">
												<option value="">--请选择--</option>
											</select>
										</div>
									</div>
			                        <div class="form-group">
										<label class="col-sm-2 control-label"><span class="text-danger">*</span>使用类型：</label>
										<div class="col-sm-4" id="mentUseType">
										</div>
			                        	 <label class="col-sm-2 control-label"><span class="text-danger">*</span>是否启用：</label>
			                             <div class="col-sm-4" id="mentState">
			                             </div>
			                        </div>
								   <div class="text-center btnBar">
			                        	<a id="handleMethod" class="btn btn-primary system agreement_add" onclick="saveTable();">保存</a>
			                       </div>
						      </form>
						 </div>

	               </div>
	          </div>
	    </div>
	</div>
    <script type="text/javascript">
    	var vo={};
    	var ue;
    	var mentHospId;
		//页面加载完成时启动的方法
		$(document).ready(function() {
			findCmmInit();
		});


		//统一初始化
		function findCmmInit(){
			doAjaxPost('<%=request.getContextPath()%>/agreement.action?act=findCmmInit',{},callDataToInit);
		}
		function callDataToInit(data){
//            UE.getEditor('editor',{ initialFrameWidth:$(".table-responsive").width()*0.86,initialFrameHeight: 400});
			if(data.map != null){
				//状态
				if(data.map.enable != null){
						$("#mentState").html("");
				    	$.each(data.map.enable, function(k, v) {
					        	dataUiCodeGroup("radio","mentState",v.codeTitle,v.codeValue);
					    });
			    	 	if($("#handle").val() == "add"){
			    	 		$("input[name='mentState'][value=1]").attr("checked",true);
					    }
				}
                /*if(data.map.hosp != null){
                    $("#mentHospId").html();
                    $.each(data.map.hosp, function(k, v) {
                        dataUiCodeGroup("select","mentHospId",v.hospName,v.id);
                    });
                }*/
                if(data.map.city != null){
                    $("#mentCityId").html();
                    $.each(data.map.city, function(k, v) {
                        dataUiCodeGroup("select","mentCityId",v.areaSname,v.id);
                    });
                }
                if(data.map.mentType != null){
                    $("#mentType").html("");
                    $.each(data.map.mentType, function(k, v) {
                        dataUiCodeGroup("radio","mentType",v.codeTitle,v.codeValue);
                    });
                    if($("#handle").val() == "add"){
                        $("input[name='mentType'][value=1]").attr("checked",true);
                    }
                }
                if(data.map.mentUseType != null){
                    $("#mentUseType").html("");
                    $.each(data.map.mentUseType, function(k, v) {
                        dataUiCodeGroup("radio","mentUseType",v.codeTitle,v.codeValue);
                    });
                    if($("#handle").val() == "add"){
                        $("input[name='mentUseType'][value=1]").attr("checked",true);
                    }
                }
                ue=UE.getEditor('editor',{ initialFrameWidth:$(".table-responsive").width()*0.86,initialFrameHeight: 400,onready:function(){
                    onInit();//回选
                }
                });
			}
		}


		//加载完成时，查询数据，记载到表格的方法
		function onInit() {
			if($("#id").val() != ""){
				doAjaxPost('<%=request.getContextPath()%>/agreement.action?act=jsonByOne',{id:$("#id").val()},callDataToAgreement);
			}
			if($("#handle").val() == "look"){
				$("#codeAddTit").text("查看协议数据");
				$("#handleMethod").hide();
			}else if($("#handle").val() == "modify"){
				$("#codeAddTit").text("修改协议数据");
				$("#handleMethod").text("修改");
			}else if($("#handle").val() == "add"){
				$("#codeAddTit").text("新增协议数据");
			}
		}

		function callDataToAgreement(data){
			dataToUi(data,"form_vo");
            if(data.mentContent != "" && data.mentContent != null){
//                var ue = UE.getEditor('editor');
//                ue.addListener("ready", function () {
                    // editor准备好之后才可以使用
                    ue.setContent(data.mentContent);
//                });
            }
            if(data.mentCityId !="" && data.mentCityId != null){
                var areaCode = data.mentCityId+"00000000";
                $("#mentCityId").val(areaCode);
                mentHospId = data.mentHospId;
                findHosp(areaCode);
			}

        }

		//点击返回
		function backTable(){
			history.go(-1);
		}


		//保存的方法
		function saveTable() {
			if(iecs($("#mentTitle").val())){
	    		showMsg("协议标题不能为空！");
	    		$("#roleName").focus();
	    		return;
	    	}
            $('#mentContent').val(UE.getEditor('editor').getContent());
			vo = uiToData("form_vo",vo);
			var method = "add";
			if($("#id").val() != ""){
				method = "modify";
			}
			doAjaxPost('<%=request.getContextPath()%>/agreement.action?act='+method,{strJson : JSON.stringify(vo)},callDataSave);
		}

		function callDataSave(data){
			if(data.msg == 'true'){
				var url = '<%=request.getContextPath()%>/agreement.action?act=forList';
                defualtHref(url);
			}else{
                showMsg(data.msg);
			}
		}

		function findHosp(areaCode){
			//根据选择的市查询医院
			if(necs(areaCode)){
                areaCode= areaCode.substr(0,4);
			}
            doAjaxPost('<%=request.getContextPath()%>/agreement.action?act=findHospCmm',{areaCode : areaCode},callDataHosp);
		}
		function callDataHosp(data){
			if(data.rows!=null){
                $("#mentHospId").html("");
                $.each(data.rows,function(k,v){
                    dataUiCodeGroup("select","mentHospId",v.hospName,v.id);
                })
				if(mentHospId != "" && mentHospId != null){
                    $("#mentHospId").val(mentHospId);
				}
			}
		}
	</script>
</body>
</html>
