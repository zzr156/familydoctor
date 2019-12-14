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
                    <a class="label label-primary pull-right system appSignSet_list"  onclick="backTable();">返回</a>
                </div>
                <div class="ibox-content">
                    <form role="form" id="form_vo"  class="form-horizontal">
                        <input type="hidden" id="id" pofield="id" name="id" value="${vo.id}" />
                        <input type="hidden" id="handle"  name="handle" value="${handle}" />
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>省：</label>
                            <div class="col-sm-4">
                                <select class="form-control" id="province" onchange="findAddress($('#province option:selected').val())">

                                </select>
                            </div>
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>市：</label>
                            <div class="col-sm-4">
                                <input type="hidden" id="signsCityName" name="signsCityName" pofield="signsCityName" >
                                <input type="hidden" id="signsAreaCode" name="signsAreaCode" pofield="signsAreaCode" >
                                <select class="form-control" id="citySelect" >

                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>经济类型：</label>
                            <div class="col-sm-4" id="signsJjType">

                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>是否家庭签约：</label>
                            <div class="col-sm-4" id="signsSignType">

                            </div>
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>是否建档才可签约：</label>
                            <div class="col-sm-4" id="signsOpenJd">

                            </div>
                        </div>

                        <div class="text-center btnBar">
                            <a id="handleMethod" class="btn btn-primary system appSignSet_add" onclick="saveTable();">保存</a>
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
        findProvince();//查找省
        findCmmInit();//初始化
    });
    //查找省
    function findProvince(){
        var pid=null;
        doAjaxPost('<%=request.getContextPath()%>/address.action?act=findCmmByPid',{pid:pid},function (data) {
            if(data!=null){
                $("#province").html("");
                var option = document.createElement('option');
                option.setAttribute("value","");
                option.innerText = "--请选择省--";
                document.getElementById("province").appendChild(option);
                $.each(data, function (k, v) {
                    dataUiCodeGroup("select", "province", v.areaSname, v.id);
                });
            }
        });
    }
    //根据省查询市
    function findAddress(pid,areaCode){
        doAjaxPost('<%=request.getContextPath()%>/address.action?act=findCmmByPid',{pid:pid},function (data) {
            if(data!=null){
                $("#citySelect").html("");
                var option = document.createElement('option');
                option.setAttribute("value","");
                option.innerText = "--请选择市--";
                document.getElementById("citySelect").appendChild(option);
                $.each(data, function (k, v) {
                    dataUiCodeGroup("select", "citySelect", v.areaSname, v.id);
                });
                if(necs(areaCode)){
                    var areaCodeCy = areaCode+"00000000";
                    $("#citySelect").val(areaCodeCy);
                }
            }
        });

    }
    //统一初始化
    function findCmmInit(){
        doAjaxPost('<%=request.getContextPath()%>/appSignSet.action?act=findCmmInit',{},callDataToInit);
    }
    function callDataToInit(data){
        if(data.map != null){
            if(data.map.jjlxs!=null){
                $("#signsJjType").html("");
                $.each(data.map.jjlxs,function(k,v){
                    dataUiCodeGroup("checkbox","signsJjType",v.labelTitle+" ",v.labelValue);
                })
            }
            if(data.map.signType!=null){
                $("#signsSignType").html("");
                $.each(data.map.signType,function(k,v){
                    dataUiCodeGroup("radio","signsSignType",v.codeTitle+" ",v.codeValue);
                })
                if($("#handle").val()=="add"){
                    $("input[name='signsSignType'][value=1]").attr("checked",true);
                }
            }
            if(data.map.sfcommon!=null){
                $("#signsOpenJd").html("");
                $.each(data.map.sfcommon,function(k,v){
                    dataUiCodeGroup("radio","signsOpenJd",v.codeTitle+" ",v.codeValue);
                })
                if($("#handle").val()=="add"){
                    $("input[name='signsOpenJd'][value=0]").attr("checked",true);
                }
            }
            onInit();//回选
        }
    }

    //加载完成时，查询数据，记载到表格的方法
    function onInit() {
        if($("#id").val()!=""){
            doAjaxPost('<%=request.getContextPath()%>/appSignSet.action?act=jsonByOne',{id:$("#id").val()},callDateToSignSetO);
        }
        if($("#handle").val() == "look"){
            $("#handleMethod").hide();
            $("#userAddTit").text("查看签约设置信息");
            $("#province").attr("disabled",true);
            $("#citySelect").attr("disabled",true);
        }else if($("#handle").val() == "modify"){
            $("#handleMethod").text("修改");
            $("#userAddTit").text("修改签约设置信息");
            $("#province").attr("disabled",true);
            $("#citySelect").attr("disabled",true);
        }else if($("#handle").val() == "add"){
            $("#userAddTit").text("新增签约设置信息");
        }

    }
    function callDateToSignSetO(data){
        dataToUi(data,"form_vo");
        if(necs(data.signsAreaCode)){
            var areaCodePr = data.signsAreaCode.substr(0,2)+"0000000000";
            $("#province").val(areaCodePr);
            findAddress(areaCodePr,data.signsAreaCode);
        }

    }
    function callDateToSignSetT(data){
        if(data!=null){
            dataToUi(data,"form_vo");
            $("#handle").val("modify");
            $("#handleMethod").text("修改");
            $("#userAddTit").text("修改签约设置信息");
        }
    }
    //点击返回
    function backTable(){
        history.go(-1);
    }

    //保存的方法
    function saveTable() {
        if(iecs($("#citySelect").val())){
            showMsg("请选择市！");
            $("#citySelect").focus();
            return;
        }else{
            var options = $("#citySelect option:selected");
            $("#signsAreaCode").val(options.val().substr(0,4));
            $("#signsCityName").val(options.text());
        }
        var signsJjType = "";
        $.each($("#signsJjType").find("input[name=signsJjType]"),function(k,v){
            if($(this).prop("checked")){
                if(signsJjType!=""){
                    signsJjType+=";";
                }
                signsJjType+=$(this).val();
            }
        });
        if(signsJjType==""){
            showMsg("请选择经济类型");
            return;
        }
        vo = uiToData("form_vo",vo);
        vo["signsJjType"]=signsJjType;
        var method = "add";
        if($("#id").val() != ""){
            method = "modify";
        };
        doAjaxPost('<%=request.getContextPath()%>/appSignSet.action?act='+method,{strJson : JSON.stringify(vo)},callDataSave);
    }

    function callDataSave(data){
        if(data.msg == 'true'){
            var url = '<%=request.getContextPath()%>/appSignSet.action?act=forList';
            defualtHref(url);
        }else{
            showMsg(data.msg);
        }
    }
</script>
</body>
</html>
