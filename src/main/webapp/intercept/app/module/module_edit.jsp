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
    <title>手机模块权限管理主页</title>
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
                    <a class="label label-primary pull-right system appmodulerole_list"  onclick="backTable();">返回</a>
                </div>
                <div class="ibox-content">
                    <form role="form" id="form_vo"  class="form-horizontal">
                        <input type="hidden" id="id" pofield="id" name="id" value="${vo.id}" />
                        <input type="hidden" id="handle"  name="handle" value="${handle}" />
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>权限名称：</label>
                            <div class="col-sm-4">
                                <input id="moduleName" name="moduleName" pofield="moduleName" type="text" class="form-control">
                            </div>
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>描述：</label>
                            <div class="col-sm-4">
                                <input id="moduleRemark" name="moduleRemark" pofield="moduleRemark" type="text" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>类型：</label>
                            <div class="col-sm-4" >
                                <div class="col-sm-4" id="moduleType">

                                </div>
                            </div>
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>模块类型：</label>
                            <div class="col-sm-4" id="moduleMenuType">

                            </div>
                        </div>
                        <div class="form-group" id="cityType">
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>所属市：</label>
                            <div class="col-sm-10" >
                                <select class="form-control" pofield="moduleRoleAreaCode" id="moduleRoleAreaCode">
                                    <option value="">--请选择--</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-group" id="hosp" >
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span> 所属医院：</label>
                            <div class="col-sm-10">
                                <div class="input-group">
                                    <input type="text" class="form-control" id="drHospName" pofield="drHospName" name="drHospName" type="text" readonly="readonly">
                                    <input type="hidden" pofield="moduleRoleHospId" id="drHospId"  />
                                    <span class="input-group-btn">
                                        <button type="button" class="btn btn-primary"  data-toggle="modal" data-target="#myModalHospDept" onclick="ChangeDateToUser()">选择医院</button>
                                    </span>
                                </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>是否家庭签约：</label>
                            <div class="col-sm-4"  id="moduleRoleManySign">
                            </div>
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>状态：</label>
                            <div class="col-sm-4"  id="moduleState">
                            </div>
                        </div>
                        <div class="text-center btnBar">
                            <a id="handleMethod" class="btn btn-primary system appmodulerole_add" onclick="saveTable();">保存</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="/intercept/app/drUser/drUser_ChangePeople.jsp" flush="false" />
<script type="text/javascript">
    var vo={};
    //页面加载完成时启动的方法
    $(document).ready(function() {
        findProvince();
        findCmmInit();
    });
    function findCmmInit(){
        doAjaxPost('<%=request.getContextPath()%>/appmodulerole.action?act=findCmmInit',{},callDataToInit);
    }

    //查找省
    function findProvince(){
        var pid=null;
        doAjaxPost('<%=request.getContextPath()%>/address.action?act=findCmmByPid',{pid:pid},function (data) {
            if(data!=null){
                $("#pro").html("");
                var option = document.createElement('option');
                option.setAttribute("value","");
                option.innerText = "--请选择省--";
                document.getElementById("pro").appendChild(option);
                $.each(data, function (k, v) {
                    dataUiCodeGroup("select", "pro", v.areaSname, v.id);
                });
            }
        });
    }
    function findAddress(id,pid,value){
        doAjaxPost('<%=request.getContextPath()%>/address.action?act=findCmmByPid',{pid : pid},function(data){
            $("#"+id).html("");
            var option = document.createElement('option');
            option.setAttribute("value","");
            if(id=='city'){
                option.innerText = "--请选择市--";
            }
            if(id=='area'){
                option.innerText = "--请选择区--";
            }
            if(id=='street'){
                option.innerText = "--请选择街道--";
            }
            if(id=="commt"){
                option.innerText = "--请选择社区--";
            }
            document.getElementById(id).appendChild(option);
            $.each(data,function(k,v){
                dataUiCodeGroup("select",id,v.areaSname,v.id);
            })
            if(value!=""){
                $("#"+id).val(value);
            }
        });
    }


    function ChangeDateToUser(){
        if($("#drHospId").val() != null && $("#drHospId").val() != ""){
            var idName = $("#drHospId").val()+";;;"+$("#drHospName").val();
            $("input[name='changeHospDept']").each(function(){
                if(idName.indexOf($(this).attr("value"))!= -1){
                    $(this).attr("checked", true);
                }
            });
        }
    }

    function findTable(){
        if(iecs($("#street").val())){
            showMsg("请选择街道");
            return;
        }
        $("#hosp").show();
        findDept($("#street").val());
    }

    //统一初始化
    function callDataToInit(data){
            $("#hosp").hide();
            $("#cityType").hide();
            if(data.map != null){
                //状态
                if(data.map.moduleState != null){
                    $("#moduleState").html("");
                    $.each(data.map.moduleState, function(k, v) {
                        dataUiCodeGroup("radio","moduleState",v.codeTitle,v.codeValue);
                    });
                    if($("#handle").val() == "add"){
                        $("input[name='moduleState'][value=1]").attr("checked",true);
                    }
                }
                //签约选择类型
                if(data.map.moduleManySign != null){
                    $("#moduleRoleManySign").html("");
                    $.each(data.map.moduleManySign, function(k, v) {
                        dataUiCodeGroup("radio","moduleRoleManySign",v.codeTitle,v.codeValue);
                    });
                    if($("#handle").val() == "add"){
                        $("input[name='moduleRoleManySign'][value=1]").attr("checked",true);
                    }
                }
                //模块
                if(data.map.moduleRoleAreaCode != null){
                    $("#moduleRoleAreaCode").html();
                    $.each(data.map.moduleRoleAreaCode, function(k, v) {
                        dataUiCodeGroup("select","moduleRoleAreaCode",v.areaSname,v.id);
                    });
                }
                //类型
                if(data.map.moduleMenuType != null){
                    $("#moduleMenuType").html("");
                    $.each(data.map.moduleMenuType, function(k, v) {
                        dataUiCodeGroup("radio","moduleMenuType",v.codeTitle,v.codeValue);
                    });
                    if($("#handle").val() == "add"){
                        $("input[name='moduleMenuType'][value=1]").attr("checked",true);
                    }
                }
                if(data.map.moduleType != null){
                    $("#moduleType").html("");
                    $.each(data.map.moduleType, function(k, v) {
                        var input = document.createElement('input');
                        input.setAttribute("type","radio");
                        input.setAttribute("name","moduleType");
                        input.setAttribute("pofield","moduleType");
                        input.setAttribute("id","moduleType");
                        input.setAttribute("value",v.codeValue);
                        input.setAttribute("onclick","show("+v.codeValue+")");
                        $("#moduleType").append(input); //3、在末尾中添加元素
                        $("#moduleType").append(v.codeTitle+" "); //3、在末尾中添加元素
//                        dataUiCodeGroup("radio","moduleType",v.codeTitle,v.codeValue);
                    });
                    if($("#handle").val() == "add"){
                        $("input[name='moduleType'][value=1]").attr("checked",true);
                        $("#cityType").show();
                    }
                }
                onInit();//回选
            }
    }
    function show(value) {
        if(value == '1'){
            $("#hosp").hide();
            $("#cityType").show();
        }else{
            $("#hosp").show();
            $("#cityType").hide();
        }
    }
    //加载完成时，查询数据，记载到表格的方法
    function onInit() {
        if($("#handle").val() == "look"){
            $("#handleMethod").hide();
            $("#title").text("查看手机模块权限信息");
        }else if($("#handle").val() == "modify"){
            $("#handleMethod").text("修改");
            $("#title").text("修改手机模块权限信息");
        }else if($("#handle").val() == "add"){
            $("#title").text("新增手机模块权限信息");
        }

        if($("#id").val()!=""){
            doAjaxPost('<%=request.getContextPath()%>/appmodulerole.action?act=jsonByOne',{id:$("#id").val()},callToTable);
        }
    }


    function callToTable(data) {
        dataToUi(data,"form_vo");
        if(necs($("#moduleRoleAreaCode").val())){
            $("#cityType").show();
            $("#hosp").hide();
        }
        if(necs($("#drHospId").val())){
            $("#hosp").show();
            $("#cityType").hide();
        }
    }
    //点击返回
    function backTable(){
        history.go(-1);
    }
    //保存的方法
    function saveTable() {
        $("#drHospId").val();
        if(iecs($("#moduleName").val())){
            showMsg("权限名称不能为空！");
            $("#moduleName").focus();
            return;
        }
        vo = uiToData("form_vo",vo);
        var method = "add";
        if($("#id").val() != ""){
            method = "modify";
        }
        doAjaxPost('<%=request.getContextPath()%>/appmodulerole.action?act='+method,{strJson : JSON.stringify(vo)},callDataSave);
    }
    function callDataSave(data) {
        if(data.msg == 'true'){
            var url = '<%=request.getContextPath()%>/appmodulerole.action?act=forList';
            defualtHref(url);
        }else{
            showMsg(data.msg);
        }
    }
</script>
</body>
</html>
