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
    <title>服务对象管理主页</title>
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
                    <a class="label label-primary pull-right system appSerPeople_list"  onclick="backTable();">返回</a>
                </div>
                <div class="ibox-content">
                    <form role="form" id="form_vo"  class="form-horizontal">
                        <input type="hidden" id="id" pofield="id" name="id" value="${vo.id}" />
                        <input type="hidden" id="handle"  name="handle" value="${handle}" />

                        <div class="form-group">
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>所属服务类型：</label>
                            <div class="col-sm-4" >
                                <select id="seroLabelType" class="form-control" onchange="findFwCmm($('#seroLabelType option:selected').val())">
                                    <option value="">--请选择--</option>
                                    <option value="3">服务人群</option>
                                    <option value="4">经济类型</option>
                                </select>
                            </div>
                            <%--<label class="col-sm-2 control-label"><span class="text-danger">*</span>服务对象类型：</label>--%>
                            <div class="col-sm-4" >
                                <select id="seroFwType" class="form-control">
                                    <option value="">--请选择--</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>服务对象名称：</label>
                            <div class="col-sm-4">
                                <input type="text" id="seroName" name="seroName" pofield="seroName" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>服务对象编号：</label>
                            <div class="col-sm-4">
                                <input type="hidden" id="value" value="0">
                                <%--<input type="number" id="seroValue" name="seroValue" pofield="seroValue" class="form-control" onblur="findNum()" placeholder="请输入数字">--%>
                                <input type="text" id="seroValue" name="seroValue" pofield="seroValue" class="form-control" readonly="readonly">
                            </div>
                            <label class="col-sm-2 control-label">是否设为基本公共卫生服务对象：</label>
                            <div class="col-sm-4" id="seroState">

                            </div>
                        </div>
                        <div class="text-center btnBar">
                            <a id="handleMethod" class="btn btn-primary system appSerPeople_add" onclick="saveTable();">保存</a>
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
        doAjaxPost('<%=request.getContextPath()%>/appSerPeople.action?act=findCmmInit',{},callDataToInit);
    }
    function callDataToInit(data){
        if(data.map != null){
            //是否开启频次
            if(data.map.state != null){
                $("#seroState").html("");
                $.each(data.map.state, function(k, v) {
                    var input = document.createElement('input');
                    input.setAttribute("type","radio");
                    input.setAttribute("name","seroState");
                    input.setAttribute("pofield","seroState");
                    input.setAttribute("id","seroState");
                    input.setAttribute("value",v.codeValue);
                    $("#seroState").append(input); //3、在末尾中添加元素
                    $("#seroState").append(v.codeTitle+" "); //3、在末尾中添加元素

                });
                if($("#handle").val() == "add"){
                    $("input[name='seroState'][value=0]").attr("checked",true);
                }
            }
            if($("#handle").val()=="add"){
                $("#seroValue").val(data.map.code);
            }
            onInit();//回选
        }
    }

    //加载完成时，查询数据，记载到表格的方法
    function onInit() {
        if($("#id").val()!=""){
            doAjaxPost('<%=request.getContextPath()%>/appSerPeople.action?act=jsonByOne',{id:$("#id").val()},callDataToDrUser);
        }
        if($("#handle").val() == "look"){
            $("#handleMethod").hide();
            $("#userAddTit").text("查看服务对象信息");
            $("#seroFwType").attr("disabled",true);
            $("#seroName").attr("disabled",true);
            $("#seroLabelType").attr("disabled",true);
        }else if($("#handle").val() == "modify"){
            $("#handleMethod").text("修改");
            $("#userAddTit").text("修改服务对象信息");
        }else if($("#handle").val() == "add"){
            $("#userAddTit").text("新增服务对象信息");
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
        $("#seroLabelType").val(data.seroLabelType);
        var seroFwType = data.seroFwType;
        if(necs(data.seroLabelType)){
            doAjaxPost('<%=request.getContextPath()%>/appSerPeople.action?act=findFwCmm',{value : data.seroLabelType},function(data){
                if(data.rows!=null){
                    $("#seroFwType").html("");
                    var option = document.createElement('option');
                    option.setAttribute("value","");
                    option.innerText = "--请选择--";
                    document.getElementById("seroFwType").appendChild(option);
                    $.each(data.rows,function(k,v){
                        dataUiCodeGroup("select","seroFwType",v.labelTitle,v.labelValue);
                    })
                    $("#seroFwType").val(seroFwType);
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
        if(iecs($("#seroName").val())){
            showMsg("服务对象名称不能为空！");
            $("#seroName").focus();
            return;
        }
        if(iecs($("#seroValue").val())){
            showMsg("服务对象编号不能为空！");
            $("#seroValue").focus();
            return;
        }
        if($("#value").val()=="1"){
            showMsg("编号已存在");
            return;
        }

        vo = uiToData("form_vo",vo);
        var optionss = $("#seroLabelType option:selected");
        if(iecs(optionss.val())){
            showMsg("请选择服务类型！");
            $("#seroLabelType").focus();
            return;
        }else{
            vo["seroLabelType"]=optionss.val();
            var options = $("#seroFwType option:selected");
            if(iecs(options.val())){
                showMsg("请选择服务类型！");
                $("#seroFwType").focus();
                return;
            }else{
                vo["seroFwType"]=options.val();
            }
        }

        var method = "add";
        if($("#id").val() != ""){
            method = "modify";
        };
        doAjaxPost('<%=request.getContextPath()%>/appSerPeople.action?act='+method,{strJson : JSON.stringify(vo)},callDataSave);
    }

    function callDataSave(data){
        if(data.msg == 'true'){
            var url = '<%=request.getContextPath()%>/appSerPeople.action?act=forList';
            defualtHref(url);
        }else{
            showMsg(data.msg);
        }
    }
    function findNum(){
        var value = $("#seroValue").val();
        if(necs(value)){
            doAjaxPost('<%=request.getContextPath()%>/appSerPeople.action?act=findCmmValue',{value : value},function(data){
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
                    $("#seroFwType").html("");
                    var option = document.createElement('option');
                    option.setAttribute("value","");
                    option.innerText = "--请选择--";
                    document.getElementById("seroFwType").appendChild(option);
                    $.each(data.rows,function(k,v){
                        dataUiCodeGroup("select","seroFwType",v.labelTitle,v.labelValue);
                    })
                }
            });
        }else{
            $("#seroFwType").html("");
            var option = document.createElement('option');
            option.setAttribute("value","");
            option.innerText = "--请选择--";
            document.getElementById("seroFwType").appendChild(option);
        }
    }
</script>
</body>
</html>
