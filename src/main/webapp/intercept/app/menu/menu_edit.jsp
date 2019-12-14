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
    <title>菜单管理主页</title>
    <%@ include file="/open/commonjs/tldHtml.jsp"%>
    <%@ include file="/open/commonjs/roleson.jsp"%>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5 id="title"></h5>
                    <a class="label label-primary pull-right system appmenu_list"  onclick="backTable();">返回</a>
                </div>
                <div class="ibox-content">
                    <form role="form" id="form_vo"  class="form-horizontal">
                        <input type="hidden" id="id" pofield="id" name="id" value="${vo.id}" />
                        <input type="hidden" id="handle"  name="handle" value="${handle}" />
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>菜单名称：</label>
                            <div class="col-sm-4">
                                <input id="menuName" name="menuName" pofield="menuName" type="text" class="form-control">
                            </div>
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>菜单值：</label>
                            <div class="col-sm-4">
                                <input id="menuValue" name="menuValue" pofield="menuValue" type="text" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>菜单模块：</label>
                            <div class="col-sm-4" id="menuModule">
                            </div>
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>菜单类型：</label>
                            <div class="col-sm-4" id="menuType">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>状态：</label>
                            <div class="col-sm-4"  id="menuState">
                            </div>
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>排序：</label>
                            <div class="col-sm-4">
                                <input id="menuSort" name="menuSort" pofield="menuSort" type="text" class="form-control">
                            </div>
                        </div>
                        <div class="text-center btnBar">
                            <a id="handleMethod" class="btn btn-primary system appmenu_add" onclick="saveTable();">保存</a>
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
        findCmmInit();
    });
    function findCmmInit(){
        doAjaxPost('<%=request.getContextPath()%>/appmenu.action?act=findCmmInit',{},callDataToInit);
    }

    //统一初始化
    function callDataToInit(data){
            if(data.map != null){
                //状态
                if(data.map.menuState != null){
                    $("#menuState").html("");
                    $.each(data.map.menuState, function(k, v) {
                        dataUiCodeGroup("radio","menuState",v.codeTitle,v.codeValue);
                    });
                    if($("#handle").val() == "add"){
                        $("input[name='menuState'][value=1]").attr("checked",true);
                    }
                }
                //模块
                if(data.map.menuModule != null){
                    $("#menuModule").html("");
                    $.each(data.map.menuModule, function(k, v) {
                        dataUiCodeGroup("radio","menuModule",v.codeTitle,v.codeValue);
                    });
                    if($("#handle").val() == "add"){
                        $("input[name='menuModule'][value=1]").attr("checked",true);
                    }
                }
                //类型
                if(data.map.menuType != null){
                    $("#menuType").html("");
                    $.each(data.map.menuType, function(k, v) {
                        dataUiCodeGroup("radio","menuType",v.codeTitle,v.codeValue);
                    });
                    if($("#handle").val() == "add"){
                        $("input[name='menuType'][value=1]").attr("checked",true);
                    }
                }
                onInit();//回选
            }
    }
    //加载完成时，查询数据，记载到表格的方法
    function onInit() {
        if($("#handle").val() == "look"){
            $("#handleMethod").hide();
            $("#title").text("查看手机菜单信息");
        }else if($("#handle").val() == "modify"){
            $("#handleMethod").text("修改");
            $("#title").text("修改手机菜单信息");
        }else if($("#handle").val() == "add"){
            $("#title").text("新增手机菜单信息");
        }

        if($("#id").val()!=""){
            doAjaxPost('<%=request.getContextPath()%>/appmenu.action?act=jsonByOne',{id:$("#id").val()},callToTable);
        }
    }
    function callToTable(data) {
        dataToUi(data,"form_vo");
    }
    //点击返回
    function backTable(){
        history.go(-1);
    }
    //保存的方法
    function saveTable() {
        if(iecs($("#menuName").val())){
            showMsg("菜单名称不能为空！");
            $("#menuName").focus();
            return;
        }
        if(iecs($("#menuValue").val())){
            showMsg("菜单值不能为空！");
            $("#menuValue").focus();
            return;
        }
        vo = uiToData("form_vo",vo);
        var method = "add";
        if($("#id").val() != ""){
            method = "modify";
        }
        doAjaxPost('<%=request.getContextPath()%>/appmenu.action?act='+method,{strJson : JSON.stringify(vo)},callDataSave);
    }
    function callDataSave(data) {
        if(data.msg == 'true'){
            var url = '<%=request.getContextPath()%>/appmenu.action?act=forList';
            defualtHref(url);
        }else{
            showMsg(data.msg);
        }
    }
</script>
</body>
</html>
