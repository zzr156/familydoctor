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
    <title>服务内容管理主页</title>
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
                    <a class="label label-primary pull-right system appPk_list"  onclick="backTable();">返回</a>
                </div>
                <div class="ibox-content">
                    <form role="form" id="form_vo"  class="form-horizontal">
                        <input type="hidden" id="id" pofield="id" name="id" value="${vo.id}" />
                        <input type="hidden" id="handle"  name="handle" value="${handle}" />
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>服务1名称：</label>
                            <div class="col-sm-4">
                                <input type="text" id="serpkName" name="serpkName" pofield="serpkName" class="form-control">
                            </div>
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>服务编号：</label>
                            <div class="col-sm-4">
                                <input type="hidden" id="value" value="0">
                                <input type="number" id="serpkValue" name="serpkValue" pofield="serpkValue" class="form-control" onblur="findNum()" placeholder="请输入数字">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">是否开启频次：</label>
                            <div class="col-sm-4" id="serpkOpenState">

                            </div>
                            <label class="col-sm-2 control-label" id="setNum">每年频次次数：</label>
                            <div class="col-sm-4">
                                <input type="number" id="serpkNum" name="serpkNum" pofield="serpkNum" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label" >选择图标：</label>
                            <div class="col-sm-4">
                                <select id="serpkImageUrl" class="form-control">
                                    <option value="">--请选择--</option>
                                </select>
                                <%--<input type="text" id="serpkImageUrl" name="serpkImageUrl" pofield="serpkImageUrl" class="form-control">--%>
                            </div>
                            <label class="col-sm-2 control-label" >是否是特色服务：</label>
                            <div class="col-sm-4" id="serpkBaseType">

                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label" >服务介绍：</label>
                            <div class="col-sm-10">
                                <textarea id="serpkRemark" name="serpkRemark" pofield="serpkRemark" type="text" class="form-control"></textarea>
                            </div>
                        </div>
                        <div class="text-center btnBar">
                            <a id="handleMethod" class="btn btn-primary system appPk_add" onclick="saveTable();">保存</a>
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
        findCmmInit();//初始化
    });
    //统一初始化
    function findCmmInit(){
        doAjaxPost('openappPk.action?act=findCmmInit',{},callDataToInit);
    }
    function callDataToInit(data){
        if(data.map != null){
            //是否开启频次
            if(data.map.openState != null){
                $("#serpkOpenState").html("");
                $.each(data.map.openState, function(k, v) {
                    var input = document.createElement('input');
                    input.setAttribute("type","radio");
                    input.setAttribute("name","serpkOpenState");
                    input.setAttribute("pofield","serpkOpenState");
                    input.setAttribute("id","serStartState");
                    input.setAttribute("value",v.codeValue);
                    input.setAttribute("onclick","show(this)");
                    $("#serpkOpenState").append(input); //3、在末尾中添加元素
                    $("#serpkOpenState").append(v.codeTitle+" "); //3、在末尾中添加元素

                });
                if($("#handle").val() == "add"){
                    $("input[name='serpkOpenState'][value=0]").attr("checked",true);
                    $("#serpkNum").hide();
                    $("#setNum").hide();
                }
            }
            if(data.map.icon!=null){
                $("#serpkImageUrl").html("");
                var option = document.createElement('option');
                option.setAttribute("value","");
                option.innerText = "--请选择--";
                document.getElementById("serpkImageUrl").appendChild(option);
                $.each(data.map.icon,function(k,v){
                    dataUiCodeGroup("select","serpkImageUrl",v.codeTitle,v.codeValue);
                })
            }
            if(data.map.sf!=null){
                $("#serpkBaseType").html("");
                $.each(data.map.sf, function(k, v) {
                    var input = document.createElement('input');
                    input.setAttribute("type","radio");
                    input.setAttribute("name","serpkBaseType");
                    input.setAttribute("pofield","serpkBaseType");
                    input.setAttribute("id","serpkBaseType");
                    input.setAttribute("value",v.codeValue);
                    $("#serpkBaseType").append(input); //3、在末尾中添加元素
                    $("#serpkBaseType").append(v.codeTitle+" "); //3、在末尾中添加元素
                });
                if($("#handle").val()=="add"){
                    $("input[name='serpkBaseType'][value=0]").attr("checked",true);
                }
            }
            onInit();//回选
        }
    }

    //加载完成时，查询数据，记载到表格的方法
    function onInit() {
        if($("#id").val()!=""){
            doAjaxPost('openappPk.action?act=jsonByOne',{id:$("#id").val()},callDataToDrUser);
        }
        if($("#handle").val() == "look"){
            $("#handleMethod").hide();
            $("#userAddTit").text("查看服务内容信息");
        }else if($("#handle").val() == "modify"){
            $("#handleMethod").text("修改");
            $("#userAddTit").text("修改服务内容信息");
        }else if($("#handle").val() == "add"){
            $("#userAddTit").text("新增服务内容信息");
        }
    }
    function show(ui){
        if($(ui).val()=="1"){
            $("#serpkNum").show();
            $("#setNum").show();
        }else if($(ui).val()=="0"){
            $("#serpkNum").hide();
            $("#setNum").hide();
            $("#serpkNum").val("");
        }
    }
    function callDataToDrUser(data){
        dataToUi(data,"form_vo");
        $("#serpkImageUrl").val(data.serpkImageUrl);
        if(data.serpkOpenState=="0"){
            $("#serpkNum").hide();
            $("#setNum").hide();
        }
    }

    //点击返回
    function backTable(){
        history.go(-1);
    }

    //保存的方法
    function saveTable() {
        if(iecs($("#serpkName").val())){
            showMsg("服务名称不能为空！");
            $("#serpkName").focus();
            return;
        }
        if(iecs($("#serpkValue").val())){
            showMsg("服务编号不能为空！");
            $("#serpkValue").focus();
            return;
        }
        if($("#value").val()=="1"){
            showMsg("服务编号已存在");
            return;
        }
        vo = uiToData("form_vo",vo);
        var options = $("#serpkImageUrl option:selected");
        vo["serpkImageUrl"] = options.val();
        var method = "add";
        if($("#id").val() != ""){
            method = "modify";
        };
        doAjaxPost('openappPk.action?act='+method,{strJson : JSON.stringify(vo)},callDataSave);
    }

    function callDataSave(data){
        if(data.msg == 'true'){
            var url = 'openappPk.action?act=forList';
            defualtHref(url);
        }else{
            showMsg(data.msg);
        }
    }
    function findNum(){
        var value = $("#serpkValue").val();
        if(necs(value)){
            doAjaxPost('openappPk.action?act=findCmmValue',{value : value},function(data){
                if(data.msg=="true"){
                    showMsg("该编号已存在");
                    $("#value").val("1");
                }else{
                    $("#value").val("0");
                }
            });
        }
    }
</script>
</body>
</html>
