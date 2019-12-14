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
    <title>服务设置管理主页</title>
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
                    <a class="label label-primary pull-right system appServe_list"  onclick="backTable();">返回</a>
                </div>
                <div class="ibox-content">
                    <form role="form" id="form_vo"  class="form-horizontal">
                        <input type="hidden" id="id" pofield="id" name="id" value="${vo.id}" />
                        <input type="hidden" id="handle"  name="handle" value="${handle}" />
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>服务人群：</label>
                            <div class="col-sm-4">
                                <select id="serObjectValue" class="form-control">
                                    <option value="">--请选择--</option>
                                </select>
                            </div>
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>服务类型：</label>
                            <div class="col-sm-4">
                                <select id="serValue" class="form-control">
                                    <option value="">--请选择--</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">是否启用：</label>
                            <div class="col-sm-4" id="serStartState">

                            </div>
                            <label class="col-sm-2 control-label" id="setNum">设置次数：</label>
                            <div class="col-sm-4">
                                <input type="text" id="serSetNum" name="serSetNum" pofield="serSetNum" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label" >图标名称：</label>
                            <div class="col-sm-4">
                                <input type="text" id="serImageName" name="serImageName" pofield="serImageName" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label" >图标介绍：</label>
                            <div class="col-sm-10">
                                <textarea type="text" id="serImageTitle" name="serImageTitle" pofield="serImageTitle"  class="form-control"></textarea>
                            </div>
                        </div>
                        <div class="text-center btnBar">
                            <a id="handleMethod" class="btn btn-primary system appServe_add" onclick="saveTable();">保存</a>
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
        doAjaxPost('<%=request.getContextPath()%>/appServe.action?act=findCmmInit',{},callDataToInit);
    }
    function callDataToInit(data){
        if(data.map != null){

            //是否设置
            if(data.map.state != null){
                $("#serStartState").html("");
                $.each(data.map.state, function(k, v) {
                    var input = document.createElement('input');
                    input.setAttribute("type","radio");
                    input.setAttribute("name","serStartState");
                    input.setAttribute("pofield","serStartState");
                    input.setAttribute("id","serStartState");
                    input.setAttribute("value",v.codeValue);
                    input.setAttribute("onclick","show(this)");
                    $("#serStartState").append(input); //3、在末尾中添加元素
                    $("#serStartState").append(v.codeTitle+" "); //3、在末尾中添加元素

                });
                if($("#handle").val() == "add"){
                    $("input[name='serStartState'][value=0]").attr("checked",true);
                    $("#serSetNum").hide();
                    $("#setNum").hide();
                }
            }
            //服务人群
            if(data.map.servePeople != null){
                $("#serObjectValue").html("");
                $.each(data.map.servePeople,function(k,v){
                    dataUiCodeGroup("select","serObjectValue",v.codeTitle+" ",v.codeValue);
                })
            }
            //服务类型
            if(data.map.serverType != null){
                $("#serValue").html("");
                $.each(data.map.serverType,function(k,v){
                    dataUiCodeGroup("select","serValue",v.codeTitle+" ",v.codeValue);
                })
            }
            onInit();//回选
        }
    }

    //加载完成时，查询数据，记载到表格的方法
    function onInit() {
        if($("#id").val()!=""){
            doAjaxPost('<%=request.getContextPath()%>/appServe.action?act=jsonByOne',{id:$("#id").val()},callDataToDrUser);
        }
        if($("#handle").val() == "look"){
            $("#handleMethod").hide();
            $("#userAddTit").text("查看服务信息");
        }else if($("#handle").val() == "modify"){
            $("#handleMethod").text("修改");
            $("#userAddTit").text("修改服务信息");
        }else if($("#handle").val() == "add"){
            $("#userAddTit").text("新增服务信息");
        }

    }
    function show(ui){
        if($(ui).val()=="1"){
            $("#serSetNum").show();
            $("#setNum").show();
        }else if($(ui).val()=="0"){
            $("#serSetNum").hide();
            $("#setNum").hide();
            $("#serSetNum").val("");
        }
    }
    function callDataToDrUser(data){

        dataToUi(data,"form_vo");
        $("#serObjectValue").val(data.serObjectValue);
        $("#serValue").val(data.serValue);
        if(data.serStartState=="0"){
            $("#serSetNum").hide();
            $("#setNum").hide();
        }

    }

    //点击返回
    function backTable(){
        history.go(-1);
    }

    //保存的方法
    function saveTable() {
        if(iecs($("#serObjectValue").val())){
            showMsg("请选择服务人群！");
            $("#serObjectValue").focus();
            return;
        }
        if(iecs($("#serValue").val())){
            showMsg("请选择服务类型！");
            $("#serValue").focus();
            return;
        }
        vo = uiToData("form_vo",vo);
        var options = $("#serObjectValue option:selected");
        vo["serObjectTitle"] = options.text();
        vo["serObjectValue"] = options.val();
        var option = $("#serValue option:selected");
        vo["serTitle"] = option.text();
        vo["serValue"] = option.val();
        var method = "add";
        if($("#id").val() != ""){
            method = "modify";
        };
        doAjaxPost('<%=request.getContextPath()%>/appServe.action?act='+method,{strJson : JSON.stringify(vo)},callDataSave);
    }

    function callDataSave(data){
        if(data.msg == 'true'){
            var url = '<%=request.getContextPath()%>/appServe.action?act=forList';
            defualtHref(url);
        }else{
            showMsg(data.msg);
        }
    }
</script>
</body>
</html>
