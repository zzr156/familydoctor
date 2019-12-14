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
    <title>经济类型管理主页</title>
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
                    <a class="label label-primary pull-right system appEcon_list"  onclick="backTable();">返回</a>
                </div>
                <div class="ibox-content">
                    <form role="form" id="form_vo"  class="form-horizontal">
                        <input type="hidden" id="id" pofield="id" name="id" value="${vo.id}" />
                        <input type="hidden" id="handle"  name="handle" value="${handle}" />
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>所属服务类型：</label>
                            <div class="col-sm-4" >
                                <select id="econLabelType" class="form-control" onchange="findFwCmm($('#econLabelType option:selected').val())">
                                    <option value="">--请选择--</option>
                                    <option value="3">服务人群</option>
                                    <option value="4">经济类型</option>
                                </select>
                            </div>
                            <%--<label class="col-sm-2 control-label"><span class="text-danger">*</span>服务对象类型：</label>--%>
                            <div class="col-sm-4" >
                                <select id="econFwType" class="form-control">
                                    <option value="">--请选择--</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>经济类型值：</label>
                            <div class="col-sm-4">
                                <input type="hidden" id="value" value="0">
                                <%--<input type="text" id="econValue" name="econValue" pofield="econValue" class="form-control" onblur="findNum()">--%>
                                <input type="text" id="econValue" name="econValue" pofield="econValue" class="form-control" readonly="readonly">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>经济类型名称：</label>
                            <div class="col-sm-4">
                                <input type="text" id="econTitle" name="econTitle" pofield="econTitle" class="form-control">
                            </div>
                        </div>

                        <div class="text-center btnBar">
                            <a id="handleMethod" class="btn btn-primary system appEcon_add" onclick="saveTable();">保存</a>
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
        doAjaxPost('<%=request.getContextPath()%>/appEcon.action?act=findCmmInit',{},callDateToFindCmmInit);
    }
    function callDateToFindCmmInit(data){
        if(data.map!=null){
            if($("#handle").val()=="add"){
                $("#econValue").val(data.map.code);
            }
        }
        onInit();
    }
    //加载完成时，查询数据，记载到表格的方法
    function onInit() {
        if($("#id").val()!=""){
            doAjaxPost('<%=request.getContextPath()%>/appEcon.action?act=jsonByOne',{id:$("#id").val()},callDataToDrUser);
        }
        if($("#handle").val() == "look"){
            $("#handleMethod").hide();
            $("#userAddTit").text("查看经济类型");
            $("#econTitle").attr("disabled",true);
            $("#econFwType").attr("disabled",true);
            $("#econLabelType").attr("disabled",true);
        }else if($("#handle").val() == "modify"){
            $("#handleMethod").text("修改");
            $("#userAddTit").text("修改经济类型");
        }else if($("#handle").val() == "add"){
            $("#userAddTit").text("新增经济类型");
        }
    }
    function callDataToDrUser(data){
        dataToUi(data,"form_vo");
        $("#econLabelType").val(data.econLabelType);
        var econFwType = data.econFwType;
        if(necs(data.econLabelType)){
            doAjaxPost('<%=request.getContextPath()%>/appSerPeople.action?act=findFwCmm',{value : data.econLabelType},function(data){
                if(data.rows!=null){
                    $("#econFwType").html("");
                    var option = document.createElement('option');
                    option.setAttribute("value","");
                    option.innerText = "--请选择--";
                    document.getElementById("econFwType").appendChild(option);
                    $.each(data.rows,function(k,v){
                        dataUiCodeGroup("select","econFwType",v.labelTitle,v.labelValue);
                    })
                    $("#econFwType").val(econFwType);
                }
            });
        }
    }

    //点击返回
    function backTable(){
        history.go(-1);
    }

    //保存的方法
    function saveTable() {
        if(iecs($("#econValue").val())){
            showMsg("经济类型值不能为空！");
            $("#econValue").focus();
            return;
        }
        if(iecs($("#econTitle").val())){
            showMsg("请输入经济类型名称！");
            $("#econTitle").focus();
            return;
        }
        if($("#value")=="1"){
            showMsg("经济类型值已存在");
            return;
        }
        vo = uiToData("form_vo",vo);
        var optionss = $("#econLabelType option:selected");
        if(iecs(optionss.val())){
            showMsg("请选择服务类型！");
            $("#econLabelType").focus();
            return;
        }else{
            vo["econLabelType"]=optionss.val();
            var options = $("#econFwType option:selected");
            if(iecs(options.val())){
                showMsg("请选择服务类型！");
                $("#econFwType").focus();
                return;
            }else{
                vo["econFwType"]=options.val();
            }
        }
        var method = "add";
        if($("#id").val() != ""){
            method = "modify";
        };
        doAjaxPost('<%=request.getContextPath()%>/appEcon.action?act='+method,{strJson : JSON.stringify(vo)},callDataSave);
    }

    function callDataSave(data){
        if(data.msg == 'true'){
            var url = '<%=request.getContextPath()%>/appEcon.action?act=forList';
            defualtHref(url);
        }else{
            showMsg(data.msg);
        }
    }
    function findNum(){
        var value = $("#econValue").val();
        if(necs(value)){
            doAjaxPost('<%=request.getContextPath()%>/appEcon.action?act=findCmmValue',{value : value},function(data){
                if(data.msg=="true"){
                    showMsg("该编号已存在");
                    $("#value").val("1");
                }else{
                    $("#value").val("0");
                }
            });
        }
    }

    function findFwCmm(value){
        if(value!=""){
            doAjaxPost('<%=request.getContextPath()%>/appSerPeople.action?act=findFwCmm',{value : value},function(data){
                if(data.rows!=null){
                    $("#econFwType").html("");
                    var option = document.createElement('option');
                    option.setAttribute("value","");
                    option.innerText = "--请选择--";
                    document.getElementById("econFwType").appendChild(option);
                    $.each(data.rows,function(k,v){
                        dataUiCodeGroup("select","econFwType",v.labelTitle,v.labelValue);
                    })
                }
            });
        }else{
            $("#econFwType").html("");
            var option = document.createElement('option');
            option.setAttribute("value","");
            option.innerText = "--请选择--";
            document.getElementById("econFwType").appendChild(option);
        }
    }
</script>
</body>
</html>
