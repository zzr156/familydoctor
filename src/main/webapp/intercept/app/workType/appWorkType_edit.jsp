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
    <title>工作类型管理主页</title>
    <%@ include file="/open/commonjs/tldHtml.jsp"%>
    <%@ include file="/open/commonjs/roleson.jsp"%>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5 id="userAddTit"></h5>
                    <a class="label label-primary pull-right system appWork_list"  onclick="backTable();">返回</a>
                </div>
                <div class="ibox-content">
                    <form role="form" id="form_vo"  class="form-horizontal">
                        <input type="hidden" id="id" pofield="id" name="id" value="${vo.id}" />
                        <input type="hidden" id="handle"  name="handle" value="${handle}" />
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>工作类型值：</label>
                            <div class="col-sm-4">
                                <input type="hidden" id="value" value="0">
                                <input type="text" id="workValue" name="workValue" pofield="workValue" class="form-control" onblur="findNum()">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>工作类型名称：</label>
                            <div class="col-sm-4">
                                <input type="text" id="workTitle" name="workTitle" pofield="workTitle" class="form-control">
                            </div>
                        </div>

                        <div class="text-center btnBar">
                            <a id="handleMethod" class="btn btn-primary system appWork_add" onclick="saveTable();">保存</a>
                        </div>
                    </form>
                </div>

            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var vo={};
    //页面加载完成时启动的方法
    $(document).ready(function() {
        onInit();
    });
    //加载完成时，查询数据，记载到表格的方法
    function onInit() {
        if($("#id").val()!=""){
            doAjaxPost('<%=request.getContextPath()%>/appWork.action?act=jsonByOne',{id:$("#id").val()},callDataToDrUser);
        }
        if($("#handle").val() == "look"){
            $("#handleMethod").hide();
            $("#userAddTit").text("查看服务信息");
        }else if($("#handle").val() == "modify"){
            $("#handleMethod").text("修改");
            $("#userAddTit").text("修改服务信息");
        }else if($("#handle").val() == "add"){
            $("#userAddTit").text("新增服务信息");
        }
    }
    function callDataToDrUser(data){
        dataToUi(data,"form_vo");
    }

    //点击返回
    function backTable(){
        history.go(-1);
    }

    //保存的方法
    function saveTable() {
        if(iecs($("#workValue").val())){
            showMsg("工作类型值不能为空！");
            $("#workValue").focus();
            return;
        }
        if(iecs($("#workTitle").val())){
            showMsg("请输入工作类型名称！");
            $("#workTitle").focus();
            return;
        }
        if($("#value")=="1"){
            showMsg("工作类型值已存在");
            return;
        }
        vo = uiToData("form_vo",vo);
        var method = "add";
        if($("#id").val() != ""){
            method = "modify";
        };
        doAjaxPost('<%=request.getContextPath()%>/appWork.action?act='+method,{strJson : JSON.stringify(vo)},callDataSave);
    }

    function callDataSave(data){
        if(data.msg == 'true'){
            var url = '<%=request.getContextPath()%>/appWork.action?act=forList';
            defualtHref(url);
        }else{
            showMsg(data.msg);
        }
    }
    function findNum(){
        var value = $("#workValue").val();
        if(necs(value)){
            doAjaxPost('<%=request.getContextPath()%>/appWork.action?act=findCmmValue',{value : value},function(data){
                if(data.msg=="true"){
                    showMsg("该编号已存在");
                    $("#value").val("1");
                }else{
                    $("#value").val("0");
                }
            });
        }
    }
</script>
</body>
</html>
