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
    <title>基础管理主页</title>
    <%@ include file="/open/commonjs/tldHtml.jsp"%>
    <%@ include file="/open/commonjs/roleson.jsp"%>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5 id="codeAddTit"></h5>
                    <a class="label label-primary pull-right system code_listJs"  onclick="backTable();">返回</a>
                </div>
                <div class="ibox-content">
                    <form role="form" id="form_vo"  class="form-horizontal">
                        <input type="hidden" id="id" pofield="id" name="id" value="${vo.id}" />
                        <input type="hidden" id="handle"  name="handle" value="${handle}" />
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>核查员姓名：</label>
                            <div class="col-sm-4">
                                <input id="codeTitle" name="codeTitle" pofield="codeTitle" type="text" class="form-control">
                            </div>
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>核查员手机号：</label>
                            <div class="col-sm-4">
                                <input id="codeValue" name="codeValue" pofield="codeValue" type="text" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>是否启用：</label>
                            <div class="col-sm-4" id="codeState">
                            </div>
                            <label class="col-sm-2 control-label">排序：</label>
                            <div class="col-sm-4">
                                <input id="codeSort" name="codeSort" pofield="codeSort" type="text" class="form-control">
                            </div>
                        </div>
                        <div class="text-center btnBar">
                            <a id="handleMethod" class="btn btn-primary system code_addJs" onclick="saveTable();">保存</a>
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


    //统一初始化
    function findCmmInit(){
        doAjaxPost('<%=request.getContextPath()%>/code.action?act=findCmmInit',{},callDataToInit);
    }
    function callDataToInit(data){
        if(data.map != null){
            //状态
            if(data.map.enable != null){
                $("#codeState").html("");
                $.each(data.map.enable, function(k, v) {
                    dataUiCodeGroup("radio","codeState",v.codeTitle,v.codeValue);
                });
                if($("#handle").val() == "add"){
                    $("input[name='codeState'][value=1]").attr("checked",true);
                }
            }
            onInit();//回选
        }
    }


    //加载完成时，查询数据，记载到表格的方法
    function onInit() {
        if($("#id").val() != ""){
            doAjaxPost('<%=request.getContextPath()%>/code.action?act=jsonByOne',{id:$("#id").val()},callDataToCode);
        }
        if($("#handle").val() == "look"){
            $("#codeAddTit").text("查看基础数据");
            $("#handleMethod").hide();
        }else if($("#handle").val() == "modify"){
            $("#codeAddTit").text("修改基础数据");
            $("#handleMethod").text("修改");
        }else if($("#handle").val() == "add"){
            $("#codeAddTit").text("新增基础数据");
        }
    }

    function callDataToCode(data){
        dataToUi(data,"form_vo");
    }

    //点击返回
    function backTable(){
        history.go(-1);
    }


    //保存的方法
    function saveTable() {
        if(iecs($("#codeTitle").val())){
            layer.open({
                skin: 'layui-layer-molv',
                closeBtn: 0,
                title: false,
                content:'基础属性名称不能为空！' ,
                anim: 5 ,
                btn: ['关闭']
            });
            $("#codeTitle").focus();
            return;
        }
        if(iecs($("#codeValue").val())){
            layer.open({
                skin: 'layui-layer-molv',
                closeBtn: 0,
                title: false,
                content:'基础属性值不能为空！' ,
                anim: 5 ,
                btn: ['关闭']
            });
            $("#codeValue").focus();
            return;
        }
        vo = uiToData("form_vo",vo);
        var method = "addJs";
        if($("#id").val() != ""){
            method = "modifyJs";
        }
        doAjaxPost('<%=request.getContextPath()%>/code.action?act='+method,{strJson : JSON.stringify(vo)},callDataSave);
    }

    function callDataSave(data){
        if(data.msg == 'true'){
            var url = '<%=request.getContextPath()%>/code.action?act=forListJs';
            defualtHref(url);
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
