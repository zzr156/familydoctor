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
<title>解约预警</title>
<%@ include file="/open/commonjs/tldHtml.jsp"%>
<%@ include file="/open/commonjs/roleson.jsp"%>
<%@ include file="/open/commonjs/tldLayui.jsp"%>
</head>

<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="row">
			<div class="col-sm-12">
	            <div class="ibox float-e-margins">
	                <div class="ibox-title">
	                	<h5 id="messageAddTit">解约预警</h5>
	                </div>
	                <div class="ibox-content">
							<form role="form" id="form_vo"  class="form-horizontal">
			          				<input type="hidden" id="id" pofield="id">
			                		<div class="form-group">
			                            <label class="col-sm-4 control-label"><span class="text-danger">*</span>红色预警 <input type="text" id="redWarningDay" pofield="redWarningDay" style="width: 15%"> 天 :</label>
			                            <div class="col-sm-8" id="redWarningState">
			                            </div>
			                        </div>
			                       <div class="form-group">
			                            <label class="col-sm-4 control-label"><span class="text-danger">*</span>黄色预警 <input type="text" id="yellowWarningDay" pofield="yellowWarningDay" style="width: 15%"> 天 :</label>
			                            <div class="col-sm-8" id="yellowWarningState">
			                            </div>
			                        </div>
			                        <div class="form-group">
			                            <label class="col-sm-4 control-label"><span class="text-danger">*</span>绿色预警 <input type="text" id="greenWarningDay" pofield="greenWarningDay" style="width: 15%"> 天 :</label>
			                            <div class="col-sm-8" id="greenWarningState">
			                            </div>
			                        </div>
								    <div class="text-center btnBar">
			                        	<button id="handleMethod" type="button" class="btn btn-primary message_add" onclick="saveTable();">保存</button>
			                        </div>
						      </form>
						 </div>
	               </div>
	          </div>
	    </div>
	</div>
	<script type="text/javascript">
        /**
		 * 核心js
		 * WangCheng
         */
        var vo = {};

        $(function(){
            initCmm();
        });

        //统一初始化
        function initCmm(){
            doAjaxPost('<%=request.getContextPath()%>/message.action?act=findCmmInit',{},callDataToInit);
        }

        function callDataToInit(data){
            if(data.map != null){
                if(data.map.enable != null){
                    var datat=data.map.enable;
                    $("#redWarningState").html("");
                    $.each(datat, function(k, v) {
                        dataUiCodeGroup("radio","redWarningState",v.codeTitle,v.codeValue);
                    });
                    $("#yellowWarningState").html("");
                    $.each(datat, function(k, v) {
                        dataUiCodeGroup("radio","yellowWarningState",v.codeTitle,v.codeValue);
                    });
                    $("#greenWarningState").html("");
                    $.each(datat, function(k, v) {
                        dataUiCodeGroup("radio","greenWarningState",v.codeTitle,v.codeValue);
                    });
                    $("input[name='redWarningState'][value=1]").attr("checked",true);
                    $("input[name='yellowWarningState'][value=1]").attr("checked",true);
                    $("input[name='greenWarningState'][value=1]").attr("checked",true);
                }
                onInit();//回选
            }
        }

        //加载初始数据
        function onInit(){
            vo["orgId"] = orgid;
            vo["drId"] = drid;
            doAjaxPost('<%=request.getContextPath()%>/message.action?act=getDissolutionWarning',{strJson: JSON.stringify(vo)},callToUi);
        }

        function callToUi(data){
            if(data.vo != null){
                $("#id").val(data.vo.id);
                $("#redWarningDay").val(data.vo.redWarningDay);
                $("#yellowWarningDay").val(data.vo.yellowWarningDay);
                $("#greenWarningDay").val(data.vo.greenWarningDay);
                $("input[name='redWarningState'][value="+data.vo.redWarningState+"]").attr("checked",true);
                $("input[name='yellowWarningState'][value="+data.vo.yellowWarningState+"]").attr("checked",true);
                $("input[name='greenWarningState'][value="+data.vo.greenWarningState+"]").attr("checked",true);
            }
        }

        //点击保存
        function saveTable(){
            vo = uiToData("form_vo",vo);
            vo["orgId"] = orgid;
            vo["drId"] = drid;
            doAjaxPost('<%=request.getContextPath()%>/message.action?act=addDissolutionWarning',{strJson : JSON.stringify(vo)},callDataSave);
        }

        function callDataSave(data){
            if(data.msg == 'true'){
                layer.open({
                    skin: 'layui-layer-molv',
                    closeBtn: 0,
                    title: false,
                    content:'保存成功！' ,
                    anim: 5 ,
                    btn: ['关闭']
                });
                onInit();
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
        }
	</script>
</body>
</html>