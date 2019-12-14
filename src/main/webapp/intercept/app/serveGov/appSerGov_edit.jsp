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
    <title>政府补贴管理主页</title>
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
                    <a class="label label-primary pull-right system appGov_list"  onclick="backTable();">返回</a>
                </div>
                <div class="ibox-content">
                    <form role="form" id="form_vo"  class="form-horizontal">
                        <input type="hidden" id="id" pofield="id" name="id" value="${vo.id}" />
                        <input type="hidden" id="handle"  name="handle" value="${handle}" />
                        <div class="form-group">
                            <label class="col-sm-2 control-label">编号：</label>
                            <div class="col-sm-4">
                                <input type="hidden" id="value" value="0">
                                <%--<input type="text" id="govValue" name="govValue" pofield="govValue" class="form-control" onblur="findNum()">--%>
                                <input type="text" id="govValue" name="govValue" pofield="govValue" class="form-control" readonly="readonly">
                            </div>
                           <%-- <label class="col-sm-2 control-label"><span class="text-danger">*</span>类型：</label>
                            <div class="col-sm-4" id="govType">

                            </div>--%>
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span> 医保对应项目：</label>
                            <div class="col-sm-4">
                                <div class="input-group">
                                    <input type="text" class="form-control" id="govHcProjectName" name="govHcProjectName" pofield="govHcProjectName" type="text" readonly="readonly">
                                    <input type="hidden" pofield="govHcProjectId" id="govHcProjectId"  />
                                    <span class="input-group-btn">
                                        <button type="button" class="btn btn-primary"  data-toggle="modal" data-target="#myModalHospDept">选择医保项目</button>
                                    </span>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>政府补贴类型名称：</label>
                            <div class="col-sm-4">
                                <input type="text" id="govTitle" name="govTitle" pofield="govTitle" class="form-control">
                            </div>
                            <label class="col-sm-2 control-label">价格：</label>
                            <div class="col-sm-4">
                                <div class="input-group">
                                    <input type="number" id="govMoney" name="govMoney" pofield="govMoney" class="form-control">
                                    <span class="input-group-addon">元</span>
                                </div>
                            </div>
                        </div>

                        <div class="text-center btnBar">
                            <a id="handleMethod" class="btn btn-primary system appGov_add" onclick="saveTable();">保存</a>
                            <a id="removeMethod" class="btn btn-primary system appGov_add" onclick="removeHcPro();">移除医保项目</a>
                        </div>
                    </form>
                </div>

            </div>
        </div>
    </div>
</div>
<jsp:include page="app_SerGov_Search.jsp" flush="false" />
<script type="text/javascript">
    var vo={};
    //页面加载完成时启动的方法
    $(document).ready(function() {
        findCmmInit();

    });
    function findCmmInit(){
        doAjaxPost('<%=request.getContextPath()%>/appGov.action?act=findCmmInit',{},calFindCmmInit);
    }
    function calFindCmmInit(data){
        if(data.map != null) {
            /*if (data.map.govType != null) {
                $("#govType").html("");
                $.each(data.map.govType, function (k, v) {
                    var input = document.createElement('input');
                    input.setAttribute("type","radio");
                    input.setAttribute("name","govType");
                    input.setAttribute("pofield","govType");
                    input.setAttribute("id","govType");
                    input.setAttribute("value",v.codeValue);
                    $("#govType").append(input); //3、在末尾中添加元素
                    $("#govType").append(v.codeTitle+" "); //3、在末尾中添加元素
                });
                if($("#handle").val() == "add") {
                    $("input[name='govType'][value=1]").attr("checked", true);
                }
            }*/
            if($("#handle").val()=="add"){
                $("#govValue").val(data.map.code);
            }
        }
        onInit();
    }
    //加载完成时，查询数据，记载到表格的方法
    function onInit() {
        if($("#id").val()!=""){
            doAjaxPost('<%=request.getContextPath()%>/appGov.action?act=jsonByOne',{id:$("#id").val()},callDataToDrUser);
        }
        if($("#handle").val() == "look"){
            $("#handleMethod").hide();
            $("#removeMethod").hide();
            $("#userAddTit").text("查看政府补贴信息");
        }else if($("#handle").val() == "modify"){
            $("#handleMethod").text("修改");
            $("#userAddTit").text("修改政府补贴信息");
        }else if($("#handle").val() == "add"){
            $("#userAddTit").text("新增政府补贴信息");
        }
    }
    function callDataToDrUser(data){
        dataToUi(data,"form_vo");
//        $("input[name='govType'][value="+data.govType+"]").attr("checked", true);
        if(""!=$("#govHcProjectName").val()){
            $("#govMoney").attr("disabled","disabled");
        }
    }

    //点击返回
    function backTable(){
        history.go(-1);
    }

    //保存的方法
    function saveTable() {
        if(iecs($("#govValue").val())){
            showMsg("政府补贴类型值不能为空！");
            $("#govValue").focus();
            return;
        }
        if(iecs($("#govTitle").val())){
            showMsg("请输入政府补贴类型名称！");
            $("#govTitle").focus();
            return;
        }
        if($("#value")=="1"){
            showMsg("政府补贴类型值已存在");
            return;
        }
        vo = uiToData("form_vo",vo);
        var method = "add";
        if($("#id").val() != ""){
            method = "modify";
        };
        doAjaxPost('<%=request.getContextPath()%>/appGov.action?act='+method,{strJson : JSON.stringify(vo)},callDataSave);
    }

    function callDataSave(data){
        if(data.msg == 'true'){
            var url = '<%=request.getContextPath()%>/appGov.action?act=forList';
            defualtHref(url);
        }else{
            showMsg(data.msg);
        }
    }
    function findNum(){
        var value = $("#govValue").val();
        if(necs(value)){
            doAjaxPost('<%=request.getContextPath()%>/appGov.action?act=findCmmValue',{value : value},function(data){
                if(data.msg=="true"){
                    showMsg("该编号已存在");
                    $("#value").val("1");
                }else{
                    $("#value").val("0");
                }
            });
        }
    }
    function removeHcPro(){
        if(""!=$("#govHcProjectName").val()){
            $("#govHcProjectId").val("");
            $("#govHcProjectName").val("");
            $("#govMoney").val("");
            $("#govMoney").removeAttr("disabled");
        }
    }
</script>
</body>
</html>
