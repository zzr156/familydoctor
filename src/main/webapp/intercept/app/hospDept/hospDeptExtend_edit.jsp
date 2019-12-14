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
    <title>医院续约设置主页</title>
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
                        <input type="hidden" id="id" pofield="id" name="id" />
                        <input type="hidden" id="extHospId" pofield="extHospId" name="extHospId" value="${vo.id}">
                        <input type="hidden" id="handle"  name="handle" value="${handle}" />
                        <div class="form-group">
                            <label class="col-sm-2 control-label">绿级天数：</label>
                            <div class="col-sm-2">
                                <input id="extGreenDay" name="extGreenDay" pofield="extGreenDay" type="text" class="form-control">
                            </div>
                            <label class="col-sm-2 control-label">黄级天数：</label>
                            <div class="col-sm-2">
                                <input id="extYellowDay" name="extYellowDay" pofield="extYellowDay" type="text" class="form-control">
                            </div>
                            <label class="col-sm-2 control-label">红级天数：</label>
                            <div class="col-sm-2">
                                <input id="extRedDay" name="extRedDay" pofield="extRedDay" type="text" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">是否同意签约：</label>
                            <div class="col-sm-2" id="extHreState">

                            </div>
                            <label class="col-sm-2 control-label">上限方式：</label>
                            <div class="col-sm-2" id="extFormulaMode">

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
   $(function(){
       findCmmInit();
   })
   function findCmmInit(){
       doAjaxPost('<%=request.getContextPath()%>/apphosp.action?act=findCmm',{id:$("#extHospId").val()},function(data){
           if(data.map!=null){
               if(data.map.state!=null){
                   $.each(data.map.state, function (k, v) {
                       dataUiCodeGroup("radio", "extHreState", v.codeTitle+" ", v.codeValue);
                   });
               }
               if(data.map.mode!=null){
                   $.each(data.map.mode, function (k, v) {
                       dataUiCodeGroup("radio", "extFormulaMode", v.codeTitle+" ", v.codeValue);
                   });
               }
           }
           findTable();
       });
   }
    function findTable(){
        doAjaxPost('<%=request.getContextPath()%>/apphosp.action?act=findExtendCmm',{id:$("#extHospId").val()},callDataToInit);
    }
    function callDataToInit(data){
        if(data!=null){
            dataToUi(data,"form_vo");
        }
    }
    function saveTable(){
        var vo={};
        vo = uiToData("form_vo",vo);
        doAjaxPost('<%=request.getContextPath()%>/apphosp.action?act=addCmm',{strJson : JSON.stringify(vo)},function(data){
            if(data.msg == 'true'){
                var url = '<%=request.getContextPath()%>/apphosp.action?act=forList';
                defualtHref(url);
            }else{
                showMsg(data.msg);
            }
        });
    }
   //点击返回
   function backTable(){
       history.go(-1);
   }
</script>
</body>
</html>
