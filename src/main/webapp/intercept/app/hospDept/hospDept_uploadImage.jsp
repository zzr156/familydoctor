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
    <title>上传公章图片主页</title>
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
                        <input type="hidden" id="id" pofield="id" name="id" value="${vo.id}">
                        <input type="hidden" id="imageBase" pofield="imageBase" name="imageBase" >
                        <input type="hidden" id="handle"  name="handle" value="${handle}" />
                        <div class="form-group">
                            <label class="col-sm-2 control-label">上传图片：</label>
                            <div class="col-sm-4">
                                <input  type="file" id="image" name="image" onchange="gen_base64();"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">公章图片：</label>
                            <div class="col-sm-4" >
                                <div id="simage"></div>
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
        onInit();//回选
    });

    //加载完成时，查询数据，记载到表格的方法
    function onInit() {
        $("#title").text("上传公章图片");
        doAjaxPost('<%=request.getContextPath()%>/apphosp.action?act=jsonByOne',{id:$("#id").val()},callData);
    }

    function callData(data){
        if(data.hospCachetAbroabUrl != "" && data.hospCachetAbroabUrl != null){
            $('#simage').append('<img  src="'+data.hospCachetAbroabUrl+'"/>')
        }

    }

    //点击返回
    function backTable(){
        history.go(-1);
    }
    //保存的方法
    function saveTable() {
        vo = uiToData("form_vo",vo);
        doAjaxPost('<%=request.getContextPath()%>/apphosp.action?act=modifyUploadImage',{strJson : JSON.stringify(vo)},callDataSave);
    }
    function callDataSave(data) {
        if(data.msg == 'true'){
            var url = '<%=request.getContextPath()%>/apphosp.action?act=forList';
            defualtHref(url);
        }else{
            showMsg(data.msg);
        }
    }

    function gen_base64() {
        var file = $(image)[0].files[0];
        r = new FileReader();  //本地预览
        r.onload = function(){
            var result = r.result.split(",");
            $('#imageBase').val(result[1]);
        }
        r.readAsDataURL(file);    //Base64
    }
</script>
</body>
</html>
