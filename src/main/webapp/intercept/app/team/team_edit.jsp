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
    <title>团队管理主页</title>
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
                    <a class="label label-primary pull-right system appteam_list"  onclick="backTable();">返回</a>
                </div>
                <div class="ibox-content">
                    <form role="form" id="form_vo"  class="form-horizontal">
                        <input type="hidden" id="id" pofield="id" name="id" value="${vo.id}" />
                        <input type="hidden" id="handle"  name="handle" value="${handle}" />
                        <div id="list" class="form-group" style="border-top: 1px dashed #D7D7D7;padding-top: 10px;">
                            <label class="col-sm-1 control-label"><span class="text-danger">*</span>区域：</label>
                            <div class="col-sm-2">
                                <select id="provinceId" class="form-control" onchange="findAddress('cityId',$('#provinceId option:selected').val())">
                                    <option value="">--请选择省--</option>
                                </select>
                            </div>
                            <div class="col-sm-2">
                                <select id="cityId" class="form-control" onchange="findAddress('areaId',$('#cityId option:selected').val())" >
                                    <option value="">--请选择市--</option>
                                </select>
                            </div>
                            <div class="col-sm-2">
                                <select id="areaId" class="form-control" onchange="findAddress('streetId',$('#areaId option:selected').val())" >
                                    <option value="">--请选择区--</option>
                                </select>
                            </div>
                            <div class="col-sm-2">
                                <select id="streetId" class="form-control" <%--onchange="findAddress('endId',$('#streetId option:selected').val())"--%> >
                                    <option value="">--请选择街道--</option>
                                </select>
                            </div>
                            <div class="col-sm-2">
                                <a class="btn btn-primary system appteam_list" onclick="findTable();">查询</a>
                            </div>
                        </div>
                        <div style="border-top: 1px dashed #D7D7D7;padding-top: 10px;">
                            <div id="body" style="display: none">
                                <div class="form-group" >
                                    <label class="col-sm-2 control-label"><span class="text-danger">*</span>机构名称：</label>
                                    <div class="col-sm-4">
                                        <select id="hosps" class="form-control" onchange="findDrUser($('#hosps option:selected').val())"></select>
                                    </div>
                                    <label class="col-sm-2 control-label"><span class="text-danger">*</span>团队名称：</label>
                                    <div class="col-sm-4">
                                        <input id="teamName" name="teamName" pofield="teamName" type="text" class="form-control">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><span class="text-danger">*</span>团队类别：</label>
                                    <div class="col-sm-4">
                                        <select id="teamType" class="form-control"></select>
                                    </div>
                                    <%--<label class="col-sm-2 control-label">目标签约量：</label>
                                    <div class="col-sm-4">
                                        <input id="teamPeople" name="teamPeople" pofield="teamPeople" type="number" min="0" class="form-control">
                                    </div>--%>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><span class="text-danger">*</span>家庭医生：</label>
                                    <div class="col-sm-4">
                                        <input type="hidden" id="teamDrId" name="teamDrId" pofield="teamDrId">
                                        <input type="hidden" id="teamDrName" name="teamDrName" pofield="teamDrName">
                                       <select id="teamDr" onchange="addCode()" class="form-control"></select>
                                    </div>
                                    <label class="col-sm-2 control-label">医生代码：</label>
                                    <div class="col-sm-4">
                                        <input id="teamDrCode" name="teamDrCode" pofield="teamDrCode" class="form-control" readonly="readonly">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">联系电话：</label>
                                    <div class="col-sm-4">
                                        <input type="text" id="teamTel" name="teamTel" pofield="teamTel" class="form-control">
                                    </div>
                                    <label class="col-sm-2 control-label">团队编号：</label>
                                    <div class="col-sm-4">
                                        <input type="text" id="teamCode" name="teamCode" pofield="teamCode" class="form-control">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">排序号：</label>
                                    <div class="col-sm-4">
                                        <input type="text" id="teamSort" name="teamSort" pofield="teamSort" class="form-control">
                                    </div>
                                    <label class="col-sm-2 control-label">有效标识：</label>
                                    <div class="col-sm-4" id="teamState">

                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">备注：</label>
                                    <div class="col-sm-10">
                                        <textarea id="teamRemarks" name="teamRemarks" pofield="teamRemarks" class="form-control"></textarea>
                                    </div>
                                </div>
                                <div class="text-center btnBar">
                                    <a id="handleMethod" class="btn btn-primary system appteam_add" onclick="saveTable();">保存</a>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>

            </div>
        </div>
    </div>
</div>
<%--<jsp:include page="team_ChangePeople.jsp" flush="false" />--%>
<script type="text/javascript">
    var vo={};
    //页面加载完成时启动的方法
    $(document).ready(function() {
        //findDrUser();//医生列表
        if($("#handle").val()=="add"){
            findProvince();
        }else{
            $("#list").hide();
            findCmmInit();
        }

        //findCmmInit();//初始化
    });
    //查找省
    function findProvince(){
        var pid=null;
        doAjaxPost('<%=request.getContextPath()%>/address.action?act=findCmmByPid',{pid:pid},function (data) {
            if(data!=null){
                $("#provinceId").html("");
                var option = document.createElement('option');
                option.setAttribute("value","");
                option.innerText = "--请选择省--";
                document.getElementById("provinceId").appendChild(option);
                $.each(data, function (k, v) {
                    dataUiCodeGroup("select", "provinceId", v.areaSname, v.id);
                });
            }
        });
    }
    function findAddress(id,pid,value){
        doAjaxPost('<%=request.getContextPath()%>/address.action?act=findCmmByPid',{pid : pid},function(data){
            $("#"+id).html("");
            var option = document.createElement('option');
            option.setAttribute("value","");
            if(id=='cityId'){
                option.innerText = "--请选择市--";
            }
            if(id=='areaId'){
                option.innerText = "--请选择区--";
            }
            if(id=='streetId'){
                option.innerText = "--请选择街道--";
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
    function findTable(){
       var address = $("#streetId").val();
        if(iecs(address)){
            showMsg("请选择街道");
            return;
        }else{
            address=address.substr(0,9);
            var qvoo = {};
            qvoo["areaCode"]=address;
            //查医院
            doAjaxPost('<%=request.getContextPath()%>/apphosp.action?act=findCmmByAreaCode',{qvoJson:JSON.stringify(qvoo)},function(data){
                if(data.rows!=null){
                    $("#hosps").html("");
                    var option = document.createElement('option');
                    option.setAttribute("value","");
                    option.innerText = "--请选择医院--";
                    document.getElementById("hosps").appendChild(option);
                    $.each(data.rows,function(k,v){//hosps
                        dataUiCodeGroup("select","hosps",v.hospName,v.id);
                    })
                }
                findCmmInit();
            });

        }
    }
    //查找医生
    function findDrUser(hospId){
        doAjaxPost('<%=request.getContextPath()%>/appdr.action?act=findCmmByHosp',{hospId:hospId},function(data){
            if(data.rows!=null){
                $("#teamDr").html("");
                var option = document.createElement('option');
                option.setAttribute("value","");
                option.innerText = "--请选择医生--";
                document.getElementById("teamDr").appendChild(option);
                $.each(data.rows,function(k,v){//hosps
                    dataUiCodeGroup("select","teamDr",v.drName,v.id+";"+v.drCode);
                })
            }
        });
    }
    //统一初始化
    function findCmmInit(){
        doAjaxPost('<%=request.getContextPath()%>/appteam.action?act=findCmmInit',{},callDataToInit);
    }
    function callDataToInit(data){
        if(data.map != null){
           /* if(data.map.message!=null){
                var mes = data.map.message.split(";");
                $("#teamHospId").val(mes[0]);
                $("#teamHospName").val(mes[1]);
            }*/
            //团队类别
            if(data.map.teamType!=null){
                $("#teamType").html("");
                var option = document.createElement('option');
                option.setAttribute("value","");
                option.innerText = "--请选择--";
                document.getElementById("teamType").appendChild(option);
                $.each(data.map.teamType, function(k, v) {
                    dataUiCodeGroup("select","teamType",v.codeTitle,v.codeValue);
                });
            }
            //有效标识
            if(data.map.teamState != null){
                $("#teamState").html("");
                $.each(data.map.teamState, function(k, v) {
                    dataUiCodeGroup("radio","teamState",v.codeTitle+" ",v.codeValue);
                });
                if($("#handle").val() == "add"){
                    $("input[name='teamState'][value=1]").attr("checked",true);
                }
            }
            $("#body").show();
            onInit();//回选
        }
    }

    //加载完成时，查询数据，记载到表格的方法
    function onInit() {
        if($("#id").val()!=""){
            doAjaxPost('<%=request.getContextPath()%>/appteam.action?act=jsonByOne',{id:$("#id").val()},callDataToDrUser);
        }
        if($("#handle").val() == "look"){
            $("#handleMethod").hide();
            $("#userAddTit").text("查看团队信息");
        }else if($("#handle").val() == "modify"){
            $("#handleMethod").text("修改");
            $("#userAddTit").text("修改团队信息");
        }else if($("#handle").val() == "add"){
            $("#userAddTit").text("新增团队信息");
            $("#list").show();
        }

    }
    function callDataToDrUser(data){
        dataToUi(data,"form_vo");
        var option = document.createElement('option');
        option.setAttribute("value",data.teamHospId);
        option.innerText = data.teamHospName;
        document.getElementById("hosps").appendChild(option);
        $("#hosps").attr("disabled","disabled");
        //$("#hosps").val(data.teamHospId);
        var option1 = document.createElement('option');
        option1.setAttribute("value",data.teamDrId+";"+data.teamDrCode);
        option1.innerText = data.teamDrName;
        document.getElementById("teamDr").appendChild(option1);
        $("#teamDr").attr("disabled","disable");
        $("#teamType").val(data.teamType);

    }
//    function ChangeDateToUser(){
//        if($("#drHospId").val() != null && $("#drHospId").val() != ""){
//            var idName = $("#drHospId").val()+";;;"+$("#drHospName").val();
//            $("input[name='changeHospDept']").each(function(){
//                if(idName.indexOf($(this).attr("value"))!= -1){
//                    $(this).attr("checked", true);
//                }
//            });
//        }
//    }

    //点击返回
    function backTable(){
        history.go(-1);
    }

    //保存的方法
    function saveTable() {
        if(iecs($("#teamName").val())){
            showMsg("团队不能为空！");
            $("#teamName").focus();
            return;
        }
        if(iecs($("#teamDr").val())){
            showMsg("请选择家庭医生！");
            $("#teamDr").focus();
            return;
        }
        vo = uiToData("form_vo",vo);
        var method = "add";
        if($("#id").val() != ""){
            method = "modify";
        };
        //机构名称和id
        var options=$("#hosps option:selected");
        vo["teamHospId"]=options.val();
        vo["teamHospName"]=options.text();
        vo["teamType"]=$("#teamType").val();
        doAjaxPost('<%=request.getContextPath()%>/appteam.action?act='+method,{strJson : JSON.stringify(vo)},callDataSave);
    }

    function callDataSave(data){
        if(data.msg == 'true'){
            var url = '<%=request.getContextPath()%>/appteam.action?act=forList';
            defualtHref(url);
        }else{
            showMsg(data.msg);
        }
    }
    function addCode(){
        var options=$("#teamDr option:selected");
        var value = $("#teamDr").val();
        if(value!=""&&value!=null){
            var values = value.split(";");
            var code = values[1];
            $("#teamDrCode").val(code);
            $("#teamDrId").val(values[0]);
            $("#teamDrName").val(options.text());
        }else{
            $("#teamDrCode").val("");
            $("#teamDrId").val("");
            $("#teamDrName").val("");
        }

    }
</script>
</body>
</html>
