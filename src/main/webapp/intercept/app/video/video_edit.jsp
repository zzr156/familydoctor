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
    <title>健康教育视频</title>
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
                    <a class="label label-primary pull-right system videoAction_list"  onclick="backTable();">返回</a>
                </div>
                <div class="ibox-content">
                    <form role="form" id="form_vo"  class="form-horizontal">
                        <input type="hidden" id="id" pofield="id" name="id" value="${vo.id}" />
                        <input type="hidden" id="handle"  name="handle" value="${handle}" />
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>视频名称：</label>
                            <div class="col-sm-10" >
                                <input id="videoName" name="videoName" pofield="videoName" type="text" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>视频文本：</label>
                            <div class="col-sm-10" >
                                <textarea rows="15" cols="110" id="videoContent" name="videoContent" pofield="videoContent" style="width:100%;border: 1px solid #e5e6e7;"></textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>视频地址：</label>
                            <div class="col-sm-10" >
                                <input id="videoAddress" name="videoAddress" pofield="videoAddress" type="text" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>视频状态：</label>
                            <div class="col-sm-10" id="videoState">
                            </div>
                        </div>
                        <div class="text-center btnBar">
                            <a id="handleMethod" class="btn btn-primary system videoAction_add" onclick="saveTable();">保存</a>
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
        doAjaxPost('<%=request.getContextPath()%>/videoAction.action?act=findCmmInit',{},callDataToInit);
    }

    //统一初始化
    function callDataToInit(data){
            if(data.map != null){
                //是否启用
                if(data.map.systemEnable != null){
                    $("#videoState").html("");
                    $.each(data.map.systemEnable, function(k, v) {
                        dataUiCodeGroup("radio","videoState",v.codeTitle,v.codeValue);
                    });
                    if($("#handle").val() == "add"){
                        $("input[name='videoState'][value=1]").attr("checked",true);
                    }
                }
                onInit();//回选
            }
    }
    //加载完成时，查询数据，记载到表格的方法
    function onInit() {
        if($("#handle").val() == "look"){
            $("#handleMethod").hide();
            $("#title").text("查看视频信息");
        }else if($("#handle").val() == "modify"){
            $("#handleMethod").text("修改");
            $("#title").text("修改视频信息");
        }else if($("#handle").val() == "add"){
            $("#title").text("新增视频信息");
        }

        if($("#id").val()!=""){
            doAjaxPost('<%=request.getContextPath()%>/videoAction.action?act=jsonByOne',{id:$("#id").val()},callToTable);
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
        vo = uiToData("form_vo",vo);
        var method = "add";
        if($("#id").val() != ""){
            method = "modify";
        }
        doAjaxPost('<%=request.getContextPath()%>/videoAction.action?act='+method,{strJson : JSON.stringify(vo)},callDataSave);
    }
    function callDataSave(data) {
        if(data.msg == 'true'){
            var url = '<%=request.getContextPath()%>/videoAction.action?act=forList';
            defualtHref(url);
        }else{
            showMsg(data.msg);
        }
    }
</script>
</body>
</html>
