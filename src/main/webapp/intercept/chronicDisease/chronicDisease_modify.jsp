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
    <title>修改慢性病患者分标管理</title>
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
                    <a class="label label-primary pull-right system appNCD_list"  onclick="backTable();">返回</a>
                </div>
                <div class="ibox-content">
                    <form role="form" id="form_vo"  class="form-horizontal">
                        <input type="hidden" id="id" pofield="id" name="id" value="${vo.id}" />
                        <input type="hidden" id="handle"  name="handle" value="${handle}" />
                        <div class="form-group">
                            <label class="col-sm-2 control-label">姓名：</label>
                            <div class="col-sm-4">
                                <input id="patientName" disabled="disabled" name="patientName" pofield="patientName" type="text" class="form-control">
                            </div>
                            <label class="col-sm-2 control-label">身份证号：</label>
                            <div class="col-sm-4">
                                <input id="patientIdno" disabled="disabled" name="patientIdno" pofield="patientIdno" type="text" class="form-control">
                            </div>
                        </div>
                        <div class="form-group" id="hbp">
                            <label class="col-sm-2 control-label">是否高血压：</label>
                            <div class="col-sm-4" id="hbpLabel">

                            </div>
                            <label class="col-sm-2 control-label">颜色：</label>
                            <div class="col-sm-4">
                                <select id="hbpLabelColor" pofield="hbpLabelColor" class="form-control">
                                    <option value="0">灰标</option>
                                    <option value="1">红标</option>
                                    <option value="2">黄标</option>
                                    <option value="3">绿标</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group" id="dm">
                            <label class="col-sm-2 control-label">是否糖尿病：</label>
                            <div class="col-sm-4" id="dmLabel">

                            </div>
                            <label class="col-sm-2 control-label">颜色：</label>
                            <div class="col-sm-4">
                                <select id="dmLabelColor" pofield="dmLabelColor" class="form-control">
                                    <option value="0">灰标</option>
                                    <option value="1">红标</option>
                                    <option value="2">黄标</option>
                                    <option value="3">绿标</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group" id="pmp">
                            <label class="col-sm-2 control-label">是否严重精神障碍：</label>
                            <div class="col-sm-4" id="pmPLabel">

                            </div>
                            <label class="col-sm-2 control-label">颜色：</label>
                            <div class="col-sm-4">
                                <select id="pmPLabelColor" pofield="pmPLabelColor" class="form-control">
                                    <option value="0">灰标</option>
                                    <option value="1">红标</option>
                                    <option value="2">黄标</option>
                                    <option value="3">绿标</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group" id="tb">
                            <label class="col-sm-2 control-label">是否肺结核：</label>
                            <div class="col-sm-4" id="tbLabel">

                            </div>
                            <label class="col-sm-2 control-label">颜色：</label>
                            <div class="col-sm-4">
                                <select id="tbLabelColor" pofield="tbLabelColor" class="form-control">
                                    <option value="0">灰标</option>
                                    <option value="1">红标</option>
                                    <option value="2">黄标</option>
                                    <option value="3">绿标</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">X轴坐标：</label>
                            <div class="col-sm-4">
                                <input id="xCoordinate" name="xCoordinate" pofield="xCoordinate" type="text" class="form-control">
                            </div>
                            <label class="col-sm-2 control-label">Y轴坐标：</label>
                            <div class="col-sm-4">
                                <input id="yCoordinate" name="yCoordinate" pofield="yCoordinate" type="text" class="form-control">
                            </div>
                        </div>
                        <div class="text-center btnBar">
                            <a id="handleMethod" class="btn btn-primary system appNCD_modify" onclick="saveTable();">修改</a>
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
    //初始界面
    function findCmmInit(){
        doAjaxPost('<%=request.getContextPath()%>/appNCD.action?act=findCmmInit',{},callDataToInit);
    }
    function callDataToInit(data) {
        if(data.map != null){
            if(data.map.sf!=null){
                $("#hbpLabel").html("");
                $.each(data.map.sf, function(k, v) {
                    if(v.codeValue == 1){
                        dataUiCodeGroup("radio","hbpLabel",v.codeTitle+" ",v.codeValue);
                    }
                });

                $("#dmLabel").html("");
                $.each(data.map.sf, function(k, v) {
                    if(v.codeValue == 1){
                        dataUiCodeGroup("radio","dmLabel",v.codeTitle+" ",v.codeValue);
                    }
                });


                $("#tbLabel").html("");
                $.each(data.map.sf, function(k, v) {
                    if(v.codeValue == 1) {
                        dataUiCodeGroup("radio", "tbLabel", v.codeTitle + " ", v.codeValue);
                    }
                });

                $("#pmPLabel").html("");
                $.each(data.map.sf, function(k, v) {
                    if(v.codeValue == 1) {
                        dataUiCodeGroup("radio", "pmPLabel", v.codeTitle + " ", v.codeValue);
                    }
                });

            }
            onInit();//回选
        }
    }
    //加载完成时，查询数据，记载到表格的方法
    function onInit() {
        if($("#id").val()!=""){
            doAjaxPost('<%=request.getContextPath()%>/appNCD.action?act=jsonByOne',{id:$("#id").val()},callDataToDrUser);
        }
    }

    function callDataToDrUser(data){
        dataToUi(data,"form_vo");
        console.log(data);
        if(data.hbpLabel == 5){
            $("#hbp").show();
            $("input[name='hbpLabel'][value=1]").attr("checked",true);
        }else{
            $("#hbp").hide();
        }
        if(data.dmLabel == 6){
            $("#dm").show();
            $("input[name='dmLabel'][value=1]").attr("checked",true);
        }else{
            $("#dm").hide();
        }
        if(data.pmPLabel == 7){
            $("#pmp").show();
            $("input[name='pmPLabel'][value=1]").attr("checked",true);
        }else{
            $("#pmp").hide();
        }
        if(data.tbLabel == 8){
            $("#tb").show();
            $("input[name='tbLabel'][value=1]").attr("checked",true);
        }else{
            $("#tb").hide();
        }
        if(data.hbpLabelColor=="gray"){
            $("#hbpLabelColor").val("0");
        }else if(data.hbpLabelColor=="red"){
            $("#hbpLabelColor").val("1");
        }else if(data.hbpLabelColor=="yellow"){
            $("#hbpLabelColor").val("2");
        }else if(data.hbpLabelColor=="green"){
            $("#hbpLabelColor").val("3");
        }

        if(data.dmLabelColor == "gray"){
            $("#dmLabelColor").val("0");
        }else if(data.dmLabelColor == "red"){
            $("#dmLabelColor").val("1");
        }else if(data.dmLabelColor == "yellow"){
            $("#dmLabelColor").val("2");
        }else if(data.dmLabelColor == "green"){
            $("#dmLabelColor").val("3");
        }

        if(data.pmPLabelColor == "gray"){
            $("#pmPLabelColor").val("0");
        }else if(data.pmPLabelColor == "red"){
            $("#pmPLabelColor").val("1");
        }else if(data.pmPLabelColor == "yellow"){
            $("#pmPLabelColor").val("2");
        }else if(data.pmPLabelColor == "green"){
            $("#pmPLabelColor").val("3");
        }

        if(data.tbLabelColor == "gray"){
            $("#tbLabelColor").val("0");
        }else if(data.tbLabelColor == "red"){
            $("#tbLabelColor").val("1");
        }else if(data.tbLabelColor == "yellow"){
            $("#tbLabelColor").val("2");
        }else if(data.tbLabelColor == "green"){
            $("#tbLabelColor").val("3");
        }
//        findLog();
    }


    //点击返回
    function backTable(){
        history.go(-1);
    }


    //保存的方法
    function saveTable() {
        vo = uiToData("form_vo",vo);
        var method = "add";
        if($("#id").val() != ""){
            method = "modify";
        };
        doAjaxPost('<%=request.getContextPath()%>/appNCD.action?act='+method,{strJson : JSON.stringify(vo)},callDataSave);
    }

    function callDataSave(data){
        if(data.msg == 'true'){
            var url = '<%=request.getContextPath()%>/appNCD.action?act=forList';
            defualtHref(url);
        }else{
            showMsg(data.msg);
        }
    }

    function findLog(){
        if($("#id").val()!=""){
            doAjaxPost('<%=request.getContextPath()%>/appNCD.action?act=findCmmLog',{id:$("#id").val()},callDataToLog);

        }
    }
    function callDataToLog(data){


    }
</script>
</body>
</html>
