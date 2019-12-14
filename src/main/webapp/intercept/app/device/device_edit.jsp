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
    <title>设备管理主页</title>
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
                    <a class="label label-primary pull-right system appdev_list"  onclick="backTable();">返回</a>
                </div>
                <div class="ibox-content">
                    <form role="form" id="form_vo"  class="form-horizontal">
                        <input type="hidden" id="id" pofield="id" name="id" value="${vo.id}" />
                        <input type="hidden" id="handle"  name="handle" value="${handle}" />
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>设备名称：</label>
                            <div class="col-sm-4">
                                <input id="devName" name="devName" pofield="devName" type="text" class="form-control">
                            </div>
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>设备类型：</label>
                            <div class="col-sm-4">
                                <select id="devType" class="form-control"></select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>设备图片：</label>
                            <div class="col-sm-4">
                                <input  type="file" id="image" name="image" onchange="addfile();"/>
                            </div>
                            <label class="col-sm-2 control-label">当前图片：</label>
                            <div class="col-sm-4">
                                <div id="simage"></div>
                                <input type="hidden" id="devImageUrl" name="devImageUrl" pofield="devImageUrl" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>厂家型号</label>
                            <div class="col-sm-4">
                                <input type="text" id="devFactoryModel" name="devFactoryModel" pofield="devFactoryModel" class="form-control">
                            </div>
                        </div>
                        <div class="text-center btnBar">
                            <a id="handleMethod" class="btn btn-primary system appdev_add" onclick="saveTable();">保存</a>
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
    function findCmmInit() {
        doAjaxPost('<%=request.getContextPath()%>/appdev.action?act=findCmmInit',{},callDataToInit);
    }
    function callDataToInit(data){
        if(data.map!=null){
            if(data.map.devType!=null){
                var option = document.createElement('option');
                option.setAttribute("value","");
                option.innerText="--请选择--";
                document.getElementById("devType").appendChild(option);
                $.each(data.map.devType,function(k,v){
                    dataUiCodeGroup("select","devType",v.codeTitle,v.codeValue);
                })
            }
        }
        onInit();
    }
    //加载完成时，查询数据，记载到表格的方法
    function onInit() {
        if($("#handle").val() == "look"){
            $("#handleMethod").hide();
            $("#title").text("查看设备信息");
        }else if($("#handle").val() == "modify"){
            $("#handleMethod").text("修改");
            $("#title").text("修改设备信息");
        }else if($("#handle").val() == "add"){
            $("#title").text("新增设备信息");
        }
        if($("#id").val()!=""){
            doAjaxPost('<%=request.getContextPath()%>/appdev.action?act=jsonByOne',{id:$("#id").val()},callToTable);
        }
    }
    function callToTable(data) {
        dataToUi(data,"form_vo");
        $("#devType").val(data.devType);
        if(data.devImageUrl != "" && data.devImageUrl != null){
            $('#simage').append('<img width="200" height="100" src="<%=request.getContextPath()%>/image.action?act=getUrlImage&url='+data.devImageUrl+'"/>')
        }
    }
    //点击返回
    function backTable(){
        history.go(-1);
    }
    //保存的方法
    function saveTable() {
        if(iecs($("#devName").val())){
            showMsg("设备名称不能为空！");
            $("#devName").focus();
            return;
        }
        if(iecs($("#devImageUrl").val())){
            showMsg("请上传设备图片！");
            return;
        }
        if(iecs($("#devFactoryModel").val())){
            showMsg("厂家型号不能为空！");
            $("#devFactoryModel").focus();
            return
        }
        vo = uiToData("form_vo",vo);
        var method = "add";
        if($("#id").val() != ""){
            method = "modify";
        }
        vo["devType"] = $("#devType").val();
        doAjaxPost('<%=request.getContextPath()%>/appdev.action?act='+method,{strJson : JSON.stringify(vo)},callDataSave);
    }
    function callDataSave(data) {
        if(data.msg == 'true'){
            var url = '<%=request.getContextPath()%>/appdev.action?act=forList';
            defualtHref(url);
        }else{
            showMsg(data.msg);
        }
    }
    //上传文件
    function addfile(){
        var pic = $("#image").val().split(".");
        if(pic[1]==undefined || (pic[1]!='jpg'&&pic[1]!='jpeg'&&pic[1]!='png')){
            showMsg("上传照片格式不正确（照片格式应该为jpg/jpeg/png）!");
            return false;
        }
        $.ajaxFileUpload({
                url: '<%=request.getContextPath()%>/image.action?act=addImage', //用于文件上传的服务器端请求地址
                secureuri: false, //是否需要安全协议，一般设置为false
                fileElementId: 'image', //文件上传域的ID
                dataType: 'JSON', //返回值类型 一般设置为json
                success: function (data, status)  //服务器成功响应处理函数
                {
                    var da=JSON.parse(data);
                    $("#devImageUrl").val(da.vo.filePath);
                    $('#simage').html("");
                    $('#simage').append('<img width="200" height="100" src="<%=request.getContextPath()%>/image.action?act=getUrlImage&url='+da.vo.filePath+'"/>')

                },
                error: function (data, status, e)//服务器响应失败处理函数
                {
                    alert(e);
                }
            }
        )
        return false;
    }
</script>
</body>
</html>
