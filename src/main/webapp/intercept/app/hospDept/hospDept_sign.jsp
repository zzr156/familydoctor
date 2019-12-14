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
    <title>签约服务团队配制主页</title>
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
                    <a class="label label-primary pull-right system apphosp_list"  onclick="backTable();">返回</a>
                </div>
                <div class="ibox-content">
                    <form role="form" id="form_vo"  class="form-horizontal">
                        <input type="hidden" id="id" pofield="id" name="id">
                        <input type="hidden" id="sstDeptId" pofield="sstDeptId" name="sstDeptId" value="${vo.id}" />
                        <input type="hidden" id="handle"  name="handle" value="${handle}" />
                        <div class="form-group">
                            <label class="col-sm-2 control-label">签约团队是否包含特定工作类型：</label>
                            <div class="col-sm-4" id="sstWorkState">

                            </div>
                        </div>
                        <div class="form-group" id="work" style="display: none">
                            <label class="col-sm-2 control-label">特定工作类型：</label>
                            <div class="col-sm-10" id="sstWorkValue">

                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">签约人数上限是否开启：</label>
                            <div class="col-sm-4" id="sstSignState" >

                            </div>
                        </div>
                        <div class="form-group" id="st" style="display: none">
                            <label class="col-sm-2 control-label">团队签约总人数上限数：</label>
                            <div class="col-sm-4">
                                <input type="text" id="sstSignToTeam" name="sstSignToTeam" pofield="sstSignToTeam" class="form-control">
                            </div>
                            <label class="col-sm-2 control-label">医生个人签约人数上限数：</label>
                            <div class="col-sm-4">
                                <input type="text" id="sstSignToDr" name="sstSignToDr" pofield="sstSignToDr" class="form-control">
                            </div>
                        </div>
                        <div class="text-center btnBar">
                            <a id="handleMethod" class="btn btn-primary system apphosp_add" onclick="saveTable();">保存</a>
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
        doAjaxPost('<%=request.getContextPath()%>/apphosp.action?act=findSignCmmInit',{},callDataToInit);
    }
    function callDataToInit(data){
        if(data.map != null) {
            if (data.map.state != null) {
                //$("#hospPageStyle").html("");
                $.each(data.map.state, function (k, v) {
                    var input = document.createElement('input');
                    input.setAttribute("type","radio");
                    input.setAttribute("name","sstWorkState");
                    input.setAttribute("pofield","sstWorkState");
                    input.setAttribute("id","sstWorkState");
                    input.setAttribute("value",v.codeValue);
                    input.setAttribute("onclick","showWork("+v.codeValue+")");
                    $("#sstWorkState").append(input); //3、在末尾中添加元素
                    $("#sstWorkState").append(v.codeTitle+" "); //3、在末尾中添加元素
                });
            }
            if (data.map.state != null) {
                //$("#hospState").html("");
                $.each(data.map.state, function (k, v) {
                    var input = document.createElement('input');
                    input.setAttribute("type","radio");
                    input.setAttribute("name","sstSignState");
                    input.setAttribute("pofield","sstSignState");
                    input.setAttribute("id","sstSignState");
                    input.setAttribute("value",v.codeValue);
                    input.setAttribute("onclick","showSign("+v.codeValue+")");
                    $("#sstSignState").append(input); //3、在末尾中添加元素
                    $("#sstSignState").append(v.codeTitle+" "); //3、在末尾中添加元素
                });
            }
            if(data.map.ls!=null){
                $.each(data.map.ls, function (k, v) {
                    dataUiCodeGroup("checkbox","sstWorkValue",v.workTitle+" ",v.workValue);
                })
            }
        }
        onInit();//回选
    }
    //加载完成时，查询数据，记载到表格的方法
    function onInit() {
        $("#title").text("签约服务团队配制");
        doAjaxPost('<%=request.getContextPath()%>/apphosp.action?act=findSingCmm',{sstDeptId:$("#sstDeptId").val()},callToTable);
    }
    function callToTable(data) {
        if(data==null){
            $("input[name='sstWorkState'][value=0]").attr("checked",true);
            $("input[name='sstSignState'][value=0]").attr("checked",true);
            $("#st").hide();
            $("#handleMethod").text("保存");
        }else{
            $("#handleMethod").text("修改");
            if(data.sstWorkState=="1"){
                $("#work").show();
            }
            if(data.sstSignState=="1"){
                $("#st").show();
            }
        }
        dataToUi(data,"form_vo");

    }
    function showWork(num){
        if(num=="1"){
            $("#work").show();
        }else{
            $("#work").hide();
            var jjType = document.getElementsByName("sstWorkValue");
            for(var i=0;i<jjType.length;i++){
                if(jjType[i].checked){
                    $(jjType[i]).attr("checked",false);
                }
            }
        }
    }
    function showSign(num){
        if(num=="1"){
            $("#st").show();
        }else{
            $("#st").hide();
            $("#sstSignToTeam").val("");
            $("#sstSignToDr").val("");
        }
    }
    //点击返回
    function backTable(){
        history.go(-1);
    }
    //保存的方法
    function saveTable() {
        vo = uiToData("form_vo",vo);
        var workValue="";
        var zfbt = document.getElementsByName("sstWorkValue");
        for(var i=0;i<zfbt.length;i++){
            if(zfbt[i].checked){
                workValue += zfbt[i].value + ";";
            }
        }
        vo["sstWorkValue"]=workValue;
        doAjaxPost('<%=request.getContextPath()%>/apphosp.action?act=modifySignCmm',{strJson : JSON.stringify(vo)},callDataSave);
    }
    function callDataSave(data) {
        if(data.msg == 'true'){
            var url = '<%=request.getContextPath()%>/apphosp.action?act=forList';
            defualtHref(url);
        }else{
            showMsg(data.msg);
        }
    }
</script>
</body>
</html>
