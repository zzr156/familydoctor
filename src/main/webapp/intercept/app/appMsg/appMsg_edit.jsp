<%@page import="java.text.SimpleDateFormat"%>
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
    <title>应用管理主页</title>
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
                    <a class="label label-primary pull-right system appMsg_list"  onclick="backTable();">返回</a>
                </div>
                <div class="ibox-content">
                    <form role="form" id="form_vo"  class="form-horizontal">
                        <input type="hidden" id="id" pofield="id" name="id" value="${vo.id}" />
                        <input type="hidden" id="handle"  name="handle" value="${handle}" />
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>应用名称：</label>
                            <div class="col-sm-4">
                                <input id="appName" name="appName" pofield="appName" type="text" class="form-control">
                            </div>
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>应用包名：</label>
                            <div class="col-sm-4">
                                <input id="appPgName" name="appPgName" pofield="appPgName" type="text" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">应用key：</label>
                            <div class="col-sm-4">
                                <input type="text" id="appKey" name="appKey" pofield="appKey" disabled="disabled" class="form-control" >
                               <%-- <div class="input-group">
                                    <input type="text" id="appKey" name="appKey" pofield="appKey" disabled="disabled" class="form-control" >
                                    <a class="input-group-addon" onclick="generate();">生成</a>
                                </div>--%>
                            </div>
                            <label class="col-sm-2 control-label">应用id：</label>
                            <div class="col-sm-4">
                                <input type="text" id="appId" name="appId" pofield="appId" disabled="disabled" class="form-control" >
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">应用主密钥：</label>
                            <div class="col-sm-4">
                                <input type="text" id="appMasterSecret" name="appMasterSecret" pofield="appMasterSecret" disabled="disabled" class="form-control" >
                            </div>
                            <label class="col-sm-2 control-label">创建时间：</label>
                            <div class="col-sm-4">
                                <input type="text" id="appCreateTime" pofield="appCreateTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
                                       value="<%=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) %>"
                                       class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">区域选择：</label>
                            <div class="col-sm-4" >
                                <select id="appAreaCode" pofield="appAreaCode" class="form-control" >
                                    <option value="">--请选择--</option>
                                    <option value="3501">福州市</option>
                                    <option value="3503">莆田市</option>
                                    <option value="3504">三明市</option>
                                    <option value="3505">泉州市</option>
                                    <option value="3506">漳州市</option>
                                    <option value="3507">南平市</option>
                                </select>
                            </div>
                        </div>
                        <div class="text-center btnBar">
                            <a id="handleMethod" class="btn btn-primary system appMsg_add" onclick="saveTable();">保存</a>
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
        onInit();//回选
    });
    //加载完成时，查询数据，记载到表格的方法
    function onInit() {
        if($("#id").val()!=""){
            doAjaxPost('<%=request.getContextPath()%>/appMsg.action?act=jsonByOne',{id:$("#id").val()},callDataToDrUser);
        }
        if($("#handle").val() == "look"){
            $("#handleMethod").hide();
            $("#userAddTit").text("查看应用信息");
        }else if($("#handle").val() == "modify"){
            $("#handleMethod").text("修改");
            $("#userAddTit").text("修改应用信息");
        }else if($("#handle").val() == "add"){
            $("#userAddTit").text("新增应用信息");
        }

    }

    function callDataToDrUser(data){
        dataToUi(data,"form_vo");
        $("#appCreateTime").val(data.strCreateTime);

    }

    //点击返回
    function backTable(){
        history.go(-1);
    }
    //保存的方法
    function saveTable() {
        if(iecs($("#appName").val())){
            showMsg("应用名不能为空！");
            $("#appName").focus();
            return;
        }
        vo = uiToData("form_vo",vo);
        vo["appAreaCode"]=$("#appAreaCode").val();
        var method = "add";
        if($("#id").val() != ""){
            method = "modify";
        };
        doAjaxPost('<%=request.getContextPath()%>/appMsg.action?act='+method,{strJson : JSON.stringify(vo)},callDataSave);
    }

    function callDataSave(data){
        if(data.msg == 'true'){
            var url = '<%=request.getContextPath()%>/appMsg.action?act=forList';
            defualtHref(url);
        }else{
            showMsg(data.msg);
        }
    }
</script>
</body>
</html>
