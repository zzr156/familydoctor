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
    <title>新闻推送管理</title>
    <%@ include file="/open/commonjs/tldHtml.jsp"%>
    <%@ include file="/open/commonjs/roleson.jsp"%>
    <script type="text/javascript" src="<%=request.getContextPath()%>/intercept/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/intercept/ueditor/ueditor.all.js"></script>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5 id="newsTableAddTit"></h5>
                    <a class="label label-primary pull-right system newsTable_list"  onclick="backTable();">返回</a>
                </div>
                <div class="ibox-content">
                    <form role="form" id="form_vo"  class="form-horizontal">
                        <input type="hidden" id="id" pofield="id" name="id" value="${vo.id}" />
                        <div class="form-group">
                            <label class="col-sm-2 control-label">分类：</label>
                            <div class="col-sm-10">
                                <input id="tableHealthType" type="hidden">
                                <input id="strTableHealthType" name="strTableHealthType" pofield="strTableHealthType" type="text" class="form-control" disabled="disabled">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">标题：</label>
                            <div class="col-sm-10">
                                <input id="tableTitle" name="tableTitle" pofield="tableTitle" type="text" class="form-control" disabled="disabled">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">内容：</label>
                            <div class="col-sm-10">
                                <textarea rows="15" cols="110" id="tableContent" name="tableContent" pofield="tableContent" style="width:100%;border: 1px solid #e5e6e7;"></textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">接收对象：</label>
                            <div class="col-sm-4">
                                <select id="tablePushObject" class="form-control" onchange="showxx($(this).val())">
                                    <option value="">--请选择--</option>
                                </select>
                            </div>
                            <div class="col-sm-6" id="tableObject">

                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">图片：</label>
                            <div class="col-sm-10">
                                <div id="simage"></div>
                                <input type="hidden" id="tableImageUrl" name="tableImageUrl" pofield="tableImageUrl" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">发送时间：</label>
                            <div class="col-sm-10">
                                <input type="text" id="strTablePushDate" pofield="strTablePushDate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
                                       readonly="readonly" value="<%=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) %>"
                                       class="form-control">
                            </div>
                        </div>
                        <div class="text-center btnBar">
                            <a id="nowFs" class="btn btn-primary system newsTable_nowSet" onclick="nowPush();">立即发送</a>
                            <a id="dqFs" class="btn btn-primary system newsTable_set" onclick="dqPush();">定期发送</a>
                        </div>
                    </form>
                </div>

            </div>
        </div>
    </div>
</div>
<jsp:include page="news_change.jsp" flush="false"/>
<jsp:include page="/intercept/news/newsType/news_ChangeType.jsp" flush="false" />
<script type="text/javascript">
    var vo={};
    //页面加载完成时启动的方法
    $(document).ready(function() {
        findPeople();
        findCmmInitSet();
    });
    //初始信息
    function findCmmInitSet(){
        doAjaxPost('<%=request.getContextPath()%>/newsTable.action?act=findCmmInitSet',{},callDataToInit)
    }
    function callDataToInit(data){
        if(data.map!=null){
            if(data.map.object!=null){
                $("#tablePushObject").html("");
                var option = document.createElement('option');
                option.setAttribute("value","");
                option.innerText = "--请选择--";
                document.getElementById("tablePushObject").appendChild(option);
                $.each(data.map.object,function(k,v){
                    var option1 = document.createElement('option');
                    option1.setAttribute("value",v.codeValue);
                    option1.innerText = v.codeTitle;
                    document.getElementById("tablePushObject").appendChild(option1);
                })
            }
        }
        onInit();
    }
    //加载完成时，查询数据，记载到表格的方法
    function onInit() {
        if($("#id").val() != ""){
            doAjaxPost('<%=request.getContextPath()%>/newsTable.action?act=jsonByOne',{id:$("#id").val(),handle:""},callDataToNewsTable);
        }
    }
    function callDataToNewsTable(data){
        dataToUi(data,"form_vo");
        if(data.tableContent != "" && data.tableContent != null){
            $("#tableContent").val(data.tableContent);
        }
        var tableObject = data.tableObject;
        if(data.tablePushObject !="" && data.tablePushObject !=null){
            $("#tablePushObject").val(data.tablePushObject);
            if(data.tablePushObject!="4"){
                $("#tablePushObject").val(data.tablePushObject);
                doAjaxPost('<%=request.getContextPath()%>/newsTable.action?act=findLabelCmm',{num:data.tablePushObject},function(data){
                    if(data.map!=null){
                        if(data.map.newsType!=null){
                            $("#tableObject").html("");
                            $.each(data.map.newsType,function(k,v){
                                dataUiCodeGroup("checkbox","tableObject",v.labelTitle,v.labelValue);
                            })
                            $("input[name='tableObject']:checkbox").each(function(){
                                if(tableObject.indexOf($(this).attr("value"))!=-1){
                                    $(this).attr("checked", true);
                                }
                            })
                        }
                    }
                });
            }else{
                $("#tableObject").html(
                    '<div class="input-group">' +
                    '<input type="hidden" id="tablePeopleList" name="tablePeopleList" pofield="tablePeopleList">' +
                    '<input type="text" class="form-control" id="tablePeopleName" name="tablePeopleName" pofield="tablePeopleName" type="text" readonly="readonly">'+
                    '<span class="input-group-btn">'+
                    '<button type="button" class="btn btn-primary"  data-toggle="modal" data-target="#myModalHospDept" onclick="ChangeDateToUser()">选择人员</button>'+
                    '</span>' +
                    '</div>');
                $("#tablePeopleList").val(data.tablePeopleList);
                $("#tablePeopleName").val(data.tablePeopleName);
            }

        }
        if(data.tableImageUrl != "" && data.tableImageUrl != null){
            $('#simage').append('<img width="200" height="100" src="<%=request.getContextPath()%>/image.action?act=getUrlImage&url='+data.tableImageUrl+'"/>')
        }
    }
    //点击返回
    function backTable(){
        history.go(-1);
    }
    //定期发送
    function dqPush() {
        vo = uiToData("form_vo",vo);
        var options=$("#tablePushObject option:selected");
        if(options.val()!=4&&options.val()!=5){
            var ids="";
            $.each(document.getElementsByName("tableObject"),function(k,v){
                if($(this).prop("checked")){
                    ids+=$(this).val()+",";

                }
            })
            if(ids==""){
                showMsg("请选择接收对象");
                return;
            }
            vo["tableObject"]=ids;
        }
        if(iecs($("#strTablePushDate").val())){
            showMsg("发送时间不能为空！");
            return;
        }
        vo["tablePushObject"]=options.val();
        doAjaxPost('<%=request.getContextPath()%>/newsTable.action?act=set',{strJson:JSON.stringify(vo)},callDataSave);
    }
    //立即发送
    function nowPush(){
        vo = uiToData("form_vo",vo);
        var options=$("#tablePushObject option:selected");
        if(options.val()!=4&&options.val()!=5){
            var ids="";
            $.each(document.getElementsByName("tableObject"),function(k,v){
                if($(this).prop("checked")){
                    ids+=$(this).val()+",";
                }
            })
            if(ids==""){
                showMsg("请选择接收对象");
                return;
            }
            vo["tableObject"]=ids;
        }
        var options=$("#tablePushObject option:selected");
        vo["tablePushObject"]=options.val();
        doAjaxPost('<%=request.getContextPath()%>/newsTable.action?act=nowSet',{strJson:JSON.stringify(vo)},callDataSave);
    }

    function callDataSave(data){
        if(data.msg == 'true'){
            var url = '<%=request.getContextPath()%>/newsTable.action?act=forList';
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
    function showxx(num){
        if(num==0){
            $("#tableObject").html("");
        }else if(num== 1 || num == 2 || num == 3){
            doAjaxPost('<%=request.getContextPath()%>/newsTable.action?act=findLabelCmm',{num:num},function(data){
                if(data.map!=null){
                    if(data.map.newsType!=null){
                        $("#tableObject").html("");
                        $.each(data.map.newsType,function(k,v){
                            dataUiCodeGroup("checkbox","tableObject",v.labelTitle,v.labelValue);
                        })
                    }
                }
            });
        }else{
            $("#tableObject").html("");
            if(num==4){
                $("#tableObject").html(
                    '<div class="input-group">' +
                    '<input type="hidden" id="tablePeopleList" name="tablePeopleList" pofield="tablePeopleList">' +
                    '<input type="text" class="form-control" id="tablePeopleName" name="tablePeopleName" pofield="tablePeopleName" type="text" readonly="readonly">'+
                    '<span class="input-group-btn">'+
                    '<button type="button" class="btn btn-primary"  data-toggle="modal" data-target="#myModalHospDept" onclick="ChangeDateToUser()">选择人员</button>'+
                    '</span>' +
                    '</div>');
            }
        }
    }
    //选择人员回选
    function ChangeDateToUser(){
        if($("#tablePeopleList").val() != null && $("#tablePeopleList").val() != ""){
            var idName = $("#tablePeopleList").val();
            $("input[name='chckBoxx']").each(function(){
                if(idName.indexOf($(this).attr("value"))!= -1){
                    $(this).attr("checked", true);
                }
            });
        }
    }
</script>
</body>
</html>
