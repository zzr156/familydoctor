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
    <title>版本更新主页</title>
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
                    <a class="label label-primary pull-right system appsystemversion_list"  onclick="backTable();">返回</a>
                </div>
                <div class="ibox-content">
                    <form role="form" id="form_vo"  class="form-horizontal">
                        <input type="hidden" id="id" pofield="id" name="id" value="${vo.id}" />
                        <input type="hidden" id="handle"  name="handle" value="${handle}" />
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>手机类型：</label>
                            <div class="col-sm-10" id="system">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>版本号：</label>
                            <div class="col-sm-4">
                                <input id="vsersionCode" name="vsersionCode" pofield="vsersionCode" type="text" class="form-control">
                            </div>
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>版本名：</label>
                            <div class="col-sm-4">
                                <input id="vsersionName" name="vsersionName" pofield="vsersionName" type="text" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>更新内容：</label>
                            <div class="col-sm-10" >
                                <textarea rows="15" cols="110" id="changeLog" name="changeLog" pofield="changeLog" style="width:100%;border: 1px solid #e5e6e7;"></textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>下载地址：</label>
                            <div class="col-sm-10" >
                                <input id="downLoadUrl" name="downLoadUrl" pofield="downLoadUrl" type="text" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>是否更新：</label>
                            <div class="col-sm-4" id="systemUpdate">
                            </div>
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>是否强制更新：</label>
                            <div class="col-sm-4" id="systemForce">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>手机类型：</label>
                            <div class="col-sm-10" id="type">
                            </div>
                        </div>
                        <div class="text-center btnBar">
                            <a id="handleMethod" class="btn btn-primary system appsystemversion_add" onclick="saveTable();">保存</a>
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
        doAjaxPost('<%=request.getContextPath()%>/appsystemversion.action?act=findCmmInit',{},callDataToInit);
    }

    //统一初始化
    function callDataToInit(data){
            if(data.map != null){
                //版本
                if(data.map.systemCode != null){
                    $("#system").html("");
                    $.each(data.map.systemCode, function(k, v) {
                        dataUiCodeGroup("radio","system",v.codeTitle,v.codeValue);
                    });
                    if($("#handle").val() == "add"){
                        $("input[name='system'][value=1]").attr("checked",true);
                    }
                }
                //是否更新
                if(data.map.systemTrueFalse != null){
                    $("#systemUpdate").html("");
                    $.each(data.map.systemTrueFalse, function(k, v) {
                        dataUiCodeGroup("radio","systemUpdate",v.codeTitle,v.codeValue);
                    });
                    if($("#handle").val() == "add"){
                        $("input[name='systemUpdate'][value=1]").attr("checked",true);
                    }
                }
                //是否强制更新
                if(data.map.systemTrueFalse != null){
                    $("#systemForce").html("");
                    $.each(data.map.systemTrueFalse, function(k, v) {
                        dataUiCodeGroup("radio","systemForce",v.codeTitle,v.codeValue);
                    });
                    if($("#handle").val() == "add"){
                        $("input[name='systemForce'][value=1]").attr("checked",true);
                    }
                }
                //安卓IOS
                $("#type").html("");
                dataUiCodeGroup("radio","type","Android","1");
                dataUiCodeGroup("radio","type","Ios","2");
                dataUiCodeGroup("radio","type","一体机","3");
                dataUiCodeGroup("radio","type","POS","4");
                dataUiCodeGroup("radio","type","TV","5");
                if($("#handle").val() == "add"){
                    $("input[name='type'][value=1]").attr("checked",true);
                }
                onInit();//回选
            }
    }
    //加载完成时，查询数据，记载到表格的方法
    function onInit() {
        if($("#handle").val() == "look"){
            $("#handleMethod").hide();
            $("#title").text("查看版本更新信息");
        }else if($("#handle").val() == "modify"){
            $("#handleMethod").text("修改");
            $("#title").text("修改版本更新信息");
        }else if($("#handle").val() == "add"){
            $("#title").text("新增版本更新信息");
        }

        if($("#id").val()!=""){
            doAjaxPost('<%=request.getContextPath()%>/appsystemversion.action?act=jsonByOne',{id:$("#id").val()},callToTable);
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
        doAjaxPost('<%=request.getContextPath()%>/appsystemversion.action?act='+method,{strJson : JSON.stringify(vo)},callDataSave);
    }
    function callDataSave(data) {
        if(data.msg == 'true'){
            var url = '<%=request.getContextPath()%>/appsystemversion.action?act=forList';
            defualtHref(url);
        }else{
            showMsg(data.msg);
        }
    }
</script>
</body>
</html>
