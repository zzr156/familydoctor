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
    <title>服务设置权限主页</title>
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
                    <a class="label label-primary pull-right system appserverole_list"  onclick="backTable();">返回</a>
                </div>
                <div class="ibox-content">
                    <form role="form" id="form_vo"  class="form-horizontal">
                        <input type="hidden" id="id" pofield="id" name="id" value="${vo.id}" />
                        <input type="hidden" id="handle"  name="handle" value="${handle}" />
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>权限名称：</label>
                            <div class="col-sm-4">
                                <input id="moduleName" name="serveName" pofield="serveName" type="text" class="form-control">
                            </div>
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>类型：</label>
                            <div class="col-sm-4" >
                                <div class="col-sm-4" id="serveType">

                                </div>
                            </div>
                        </div>
                        <div class="form-group" id="cityType">
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>所属市：</label>
                            <div class="col-sm-10" >
                                <select class="form-control" pofield="serveRoleAreaCode" id="serveRoleAreaCode">
                                    <option value="">--请选择--</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-group" id="hosp" >
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span> 所属医院：</label>
                            <div class="col-sm-10">
                                <div class="input-group">
                                    <input type="text" class="form-control" id="drHospName"  pofield="drHospName" name="drHospName" type="text" readonly="readonly">
                                    <input type="hidden" pofield="serveRoleHospId" id="drHospId"  />
                                    <span class="input-group-btn">
                                        <button type="button" class="btn btn-primary"  data-toggle="modal" data-target="#myModalHospDept" onclick="ChangeDateToUser()">选择医院</button>
                                    </span>
                                </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>状态：</label>
                            <div class="col-sm-4"  id="serveState">
                            </div>
                        </div>
                        <div class="text-center btnBar">
                            <a id="handleMethod" class="btn btn-primary system appserverole_add" onclick="saveTable();">保存</a>
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
        doAjaxPost('<%=request.getContextPath()%>/appserverole.action?act=findCmmInit',{},callDataToInit);
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
                if(data.map.serveState != null){
                    $("#serveState").html("");
                    $.each(data.map.serveState, function(k, v) {
                        dataUiCodeGroup("radio","serveState",v.codeTitle,v.codeValue);
                    });
                    if($("#handle").val() == "add"){
                        $("input[name='serveState'][value=1]").attr("checked",true);
                    }
                }
                //模块
                if(data.map.serveRoleAreaCode != null){
                    $("#serveRoleAreaCode").html();
                    $.each(data.map.serveRoleAreaCode, function(k, v) {
                        dataUiCodeGroup("select","serveRoleAreaCode",v.areaSname,v.id);
                    });
                }
                //类型
                if(data.map.serveType != null){
                    $("#serveType").html("");
                    $.each(data.map.serveType, function(k, v) {
                        var input = document.createElement('input');
                        input.setAttribute("type","radio");
                        input.setAttribute("name","serveType");
                        input.setAttribute("pofield","serveType");
                        input.setAttribute("id","serveType");
                        input.setAttribute("value",v.codeValue);
                        input.setAttribute("onclick","show("+v.codeValue+")");
                        $("#serveType").append(input); //3、在末尾中添加元素
                        $("#serveType").append(v.codeTitle+" "); //3、在末尾中添加元素
//                        dataUiCodeGroup("radio","moduleType",v.codeTitle,v.codeValue);
                    });
                    if($("#handle").val() == "add"){
                        $("input[name='serveType'][value=1]").attr("checked",true);
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
            $("#title").text("查看服务设置权限信息");
        }else if($("#handle").val() == "modify"){
            $("#handleMethod").text("修改");
            $("#title").text("修改服务设置权限信息");
        }else if($("#handle").val() == "add"){
            $("#title").text("新增服务设置权限信息");
        }

        if($("#id").val()!=""){
            doAjaxPost('<%=request.getContextPath()%>/appserverole.action?act=jsonByOne',{id:$("#id").val()},callToTable);
        }
    }


    function callToTable(data) {
        dataToUi(data,"form_vo");
        if(necs($("#serveRoleAreaCode").val())){
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
        doAjaxPost('<%=request.getContextPath()%>/appserverole.action?act='+method,{strJson : JSON.stringify(vo)},callDataSave);
    }
    function callDataSave(data) {
        if(data.msg == 'true'){
            var url = '<%=request.getContextPath()%>/appserverole.action?act=forList';
            defualtHref(url);
        }else{
            showMsg(data.msg);
        }
    }
</script>
</body>
</html>
