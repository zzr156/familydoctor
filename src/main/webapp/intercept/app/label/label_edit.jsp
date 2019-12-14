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
    <title>标签管理主页</title>
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
                    <a class="label label-primary pull-right system apprm_list"  onclick="backTable();">返回</a>
                </div>
                <div class="ibox-content">
                    <form role="form" id="form_vo"  class="form-horizontal">
                        <input type="hidden" id="id" pofield="id" name="id" value="${vo.id}" />
                        <input type="hidden" id="handle"  name="handle" value="${handle}" />
                        <input type="hidden" id="labelPatientId" name="labelPatientId" pofield="labelPatientId">
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>标签名称：</label>
                            <div class="col-sm-4">
                                <input id="labelTitle" name="labelTitle" pofield="labelTitle" type="text" class="form-control">
                            </div>
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>标签值：</label>
                            <div class="col-sm-4">
                                <input id="labelValue" name="labelValue" pofield="labelValue" type="text" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>标签类别：</label>
                            <div class="col-sm-4"  >
                                <select id="labelType" class="form-control"></select>
                            </div>
                        </div>
                        <div class="text-center btnBar">
                            <a id="handleMethod" class="btn btn-primary system applabel_add" onclick="saveTable();">保存</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var vo={};
    var level=0;
    //页面加载完成时启动的方法
    $(document).ready(function() {
        findCmmInit();
    });
    function findCmmInit(){
        doAjaxPost('<%=request.getContextPath()%>/applabel.action?act=findCmmInit',{},callDataToInit);
    }
    function callDataToInit(data){
        if(data.map!=null){
            if(data.map.labelType!=null){
                var option = document.createElement('option');
                option.setAttribute("value","");
                option.innerText="--请选择--";
                document.getElementById("labelType").appendChild(option);
                $.each(data.map.labelType,function(k,v){
                    dataUiCodeGroup("select","labelType",v.codeTitle,v.codeValue);
                })

            }
        }
        onInit();
    }
    //加载完成时，查询数据，记载到表格的方法
    function onInit() {
        if($("#handle").val() == "look"){
            $("#handleMethod").hide();
            $("#title").text("查看标签信息");
        }else if($("#handle").val() == "modify"){
            $("#handleMethod").text("修改");
            $("#title").text("修改标签信息");
        }else if($("#handle").val() == "add"){
            $("#title").text("新增标签信息");
        }

        if($("#id").val()!=""){
            doAjaxPost('<%=request.getContextPath()%>/applabel.action?act=jsonByOne',{id:$("#id").val()},callToTable);
        }
    }
    function callToTable(data) {
        dataToUi(data,"form_vo");
        $("#labelType").val(data.labelType);
    }
    //点击返回
    function backTable(){
        history.go(-1);
    }
    //保存的方法
    function saveTable() {
        if(iecs($("#labelTitle").val())){
            showMsg("标签名称不能为空！");
            $("#labelTitle").focus();
            return;
        }
        if(iecs($("#labelValue").val())){
            showMsg("标签值不能为空！");
            $("#labelValue").focus();
            return;
        }
        if(iecs($("#labelType").val())){
            showMsg("前选择标签类别！");
            $("#labelType").focus();
            return;
        }
        vo = uiToData("form_vo",vo);
        var method = "add";
        if($("#id").val() != ""){
            method = "modify";
        }
        vo["labelType"] = $("#labelType").val();
        doAjaxPost('<%=request.getContextPath()%>/applabel.action?act='+method,{strJson : JSON.stringify(vo)},callDataSave);
    }
    function callDataSave(data) {
        if(data.msg == 'true'){
            var url = '<%=request.getContextPath()%>/applabel.action?act=forList';
            defualtHref(url);
        }else{
            showMsg(data.msg);
        }
    }
</script>
</body>
</html>
