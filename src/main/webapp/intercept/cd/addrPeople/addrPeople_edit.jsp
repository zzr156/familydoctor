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
    <title>指标设置管理主页</title>
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
                    <a class="label label-primary pull-right system addrPeople_list"  onclick="backTable();">返回</a>
                </div>
                <div class="ibox-content">
                    <form role="form" id="form_vo"  class="form-horizontal">
                        <input type="hidden" id="id" pofield="id" name="id" value="${vo.id}" />
                        <input type="hidden" id="handle"  name="handle" value="${handle}" />

                        <div style="border-top: 1px dashed #D7D7D7;padding-top: 10px;">
                            <div class="form-group">
                                <label class="col-sm-2 control-label"><span class="text-danger">*</span>等级：</label>
                                <div class="col-sm-4">
                                    <select id="level" name="level" pofield="level" class="form-control" onchange="show()">
                                    </select>
                                </div>
                                <label class="col-sm-2 control-label"><span class="text-danger">*</span>年份：</label>
                                <div class="col-sm-4">
                                    <input type="text" id="areaYear" placeholder="请选择年份" name="areaYear" pofield="areaYear" onfocus="WdatePicker({dateFmt:'yyyy'})" class="form-control">
                                </div>
                            </div>

                            <div class="form-group" id="addArea">
                                <label class="col-sm-2 control-label"><span class="text-danger">*</span>地址：</label>
                                <div class="col-sm-2">
                                    <select id="province" class="form-control" onchange="findAddress('city',$('#province option:selected').val())">
                                        <option value="">--请选择省--</option>
                                    </select>
                                </div>
                                <div class="col-sm-2">
                                    <select id="city" class="form-control" style="display:none;" onchange="findAddress('area',$('#city option:selected').val())">
                                        <option value="">--请选择市--</option>
                                    </select>
                                </div>
                                <div class="col-sm-2">
                                    <select id="area" class="form-control" style="display:none;" onchange="findAddress('street',$('#area option:selected').val())">
                                        <option value="">--请选择区--</option>
                                    </select>
                                </div>
                                <div class="col-sm-2">
                                    <select id="street" class="form-control" style="display:none;" onchange="findAddress('end',$('#street option:selected').val())">
                                        <option value="">--请选择街道--</option>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group" id="modifyArea" style="display: none">
                                <label class="col-sm-2 control-label"><span class="text-danger">*</span>地址简称：</label>
                                <div class="col-sm-4">
                                    <input id="areaSname" name="areaSname" pofield="areaSname" type="text" class="form-control" disabled="disabled">
                                </div>
                                <label class="col-sm-2 control-label"><span class="text-danger">*</span>地址全称：</label>
                                <div class="col-sm-4">
                                    <input id="areaName" name="areaName" pofield="areaName" type="text" class="form-control" disabled="disabled">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label"><span class="text-danger">*</span>人口数：</label>
                                <div class="col-sm-2">
                                    <input id="areaPopulation" name="areaPopulation" pofield="areaPopulation" type="number" min="0" class="form-control" placeholder="">
                                    <span>参考人数：<label id="upAreaPopulation"></label></span>
                                </div>
                                <label class="col-sm-2 control-label">目标率(百分比)：</label>
                                <div class="col-sm-2">
                                    <%--参考值--%>
                                    <input id="mbl" type="hidden">
                                    <input type="number" onkeyup="peopleNum()" min="0" max="100" id="areaRate" name="areaRate" pofield="areaRate" class="form-control" placeholder="请输入0~100数字">
                                </div>
                                <label class="col-sm-2 control-label"><span class="text-danger">*</span>目标数：</label>
                                <div class="col-sm-2">
                                    <input id="areaTarget" name="areaTarget" pofield="areaTarget" type="number" min="0" class="form-control" disabled="disabled">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label"><span class="text-danger">*</span>重点人群数：</label>
                                <div class="col-sm-2">
                                    <input type="number" min="0" id="areaFocus" name="areaFocus" pofield="areaFocus" class="form-control">
                                    <span>参考人数：<label id="upAreaFocus"></label></span>
                                </div>
                                <label class="col-sm-2 control-label"><span class="text-danger">*</span>重点人群率(百分比)：</label>
                                <div class="col-sm-2">
                                    <%--参考值--%>
                                    <input id="zdl" type="hidden">
                                    <input id="areaFocusRate" onkeyup="peopleZdNum()" name="areaFocusRate" pofield="areaFocusRate" type="number" min="0" max="100" class="form-control" placeholder="请输入0~100数字">
                                </div>
                                <label class="col-sm-2 control-label"><span class="text-danger">*</span>重点人群目标数：</label>
                                <div class="col-sm-2">
                                    <input type="number" min="0" id="areaFocusTarget" name="areaFocusTarget" pofield="areaFocusTarget" class="form-control" disabled="disabled">
                                </div>
                            </div>
                            <div class="form-group" id="signTop" >
                                <label class="col-sm-2 control-label">签约人数上限：</label>
                                <div class="col-sm-4">
                                    <input id="areaSignTop" name="areaSignTop" pofield="areaSignTop" type="number" min="0" class="form-control">
                                </div>
                                <div class="col-sm-6" id="areaSignWay">

                                </div>
                            </div>
                            <div class="form-group" id="disSignTop" >
                                <label class="col-sm-2 control-label">慢性病患者签约人数上限：</label>
                                <div class="col-sm-4">
                                    <input id="areaDisSignTop" name="areaDisSignTop" pofield="areaDisSignTop" type="number" min="0" class="form-control" >
                                </div>
                                <div class="col-sm-6" id="areaDisSignWay">

                                </div>
                            </div>
                            <%--<div class="form-group">
                                <label class="col-sm-2 control-label"><span class="text-danger">*</span>高血压人群率(百分比)：</label>
                                <div class="col-sm-4">
                                    <input id="areaHighbloodRate" name="areaHighbloodRate" pofield="areaHighbloodRate" type="number" min="0" max="100" class="form-control" placeholder="请输入0~100数字">
                                </div>
                                <label class="col-sm-2 control-label"><span class="text-danger">*</span>高血压人群数：</label>
                                <div class="col-sm-4">
                                    <input type="number" min="0" id="areaHighblood" name="areaHighblood" pofield="areaHighblood" class="form-control">
                                </div>
                            </div>--%>
                           <%-- <div class="form-group">
                                <label class="col-sm-2 control-label"><span class="text-danger">*</span>糖尿病人群率(百分比)：</label>
                                <div class="col-sm-4">
                                    <input id="areaDiabetesRate" name="areaDiabetesRate" pofield="areaDiabetesRate" type="number" min="0" max="100" class="form-control" placeholder="请输入0~100数字">
                                </div>
                                <label class="col-sm-2 control-label"><span class="text-danger">*</span>糖尿病人群数：</label>
                                <div class="col-sm-4">
                                    <input type="number" min="0" id="areaDiabetes" name="areaDiabetes" pofield="areaDiabetes" class="form-control">
                                </div>

                            </div>--%>

                            <div class="text-center btnBar">
                                <a id="handleMethod" class="btn btn-primary system addrPeople_add" onclick="saveTable();">保存</a>
                            </div>
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
        //onInit();
    });
    function findCmmInit(){
        doAjaxPost('<%=request.getContextPath()%>/addrPeople.action?act=findCmmInit',{},callDataToCmmInit);
    }
    function callDataToCmmInit(data){
        if(data.map!=null){
            if(data.map.province!=null){
                $.each(data.map.province,function(k,v){
                    dataUiCodeGroup("select","province",v.areaSname,v.id);
                })
            }
            if(data.map.leve!=null){
                $.each(data.map.leve,function(k,v){
                    if(v.codeValue!="5"){
                        dataUiCodeGroup("select","level",v.codeTitle,v.codeValue);
                    }
                })
            }
            if(data.map.top!=null){
                $.each(data.map.top,function(k,v){
                    dataUiCodeGroup("radio","areaSignWay",v.codeTitle+" ",v.codeValue);
                    dataUiCodeGroup("radio","areaDisSignWay",v.codeTitle+" ",v.codeValue);
                })
            }
        }
        onInit();
    }
    //加载完成时，查询数据，记载到表格的方法
    function onInit() {
        if($("#id").val()!=""){
            doAjaxPost('<%=request.getContextPath()%>/addrPeople.action?act=jsonByOne',{id:$("#id").val()},callDataToDrUser);
        }
        if($("#handle").val() == "look"){
            $("#handleMethod").hide();
            $("#userAddTit").text("查看指标信息");
        }else if($("#handle").val() == "modify"){
            $("#handleMethod").text("修改");
            $("#userAddTit").text("修改指标信息");
        }else if($("#handle").val() == "add"){
            $("#userAddTit").text("新增指标信息");
            $("#list").show();
        }

    }
    function callDataToDrUser(data){
        dataToUi(data,"form_vo");
        $("#level").attr("disabled",true);
        $("#upAreaPopulation").text(data.upAreaPopulation);
        $("#upAreaFocus").text(data.upAreaFocus);
        $("#modifyArea").show();
        $("#addArea").hide();
        $("#areaYear").attr("disabled",true);


    }
    //点击返回
    function backTable(){
        history.go(-1);
    }

    function peopleNum(){
        var NumO = $("#areaRate").val();
        if(parseInt(NumO)>100||parseInt(NumO)<0){
            showMsg("比例值范围是0~100");
            return;
        }
        if(NumO<$("#mbl").text()){
            showMsg("目标率不能小于参考率");
            return;
        }
        var NumT = $("#areaPopulation").val();
        if(NumT!=null && NumT!="0" && NumT !=""){
            var num=parseFloat(NumO)/100* parseFloat(NumT);
            $("#areaTarget").val(num.toFixed(0));
        }else{
            $("#areaTarget").val("0");
        }
    }
    function peopleZdNum(){
        if($("#areaFocusRate").val()<$("#zdl").text()){
            showMsg("重点人群率不能小于参考率");
            return;
        }
        if($("#areaFocusRate").val()!=null && $("#areaFocusRate").val()!="0" && $("#areaFocusRate").val() !=""){
            var num=parseFloat($("#areaFocusRate").val())/100* parseFloat($("#areaFocus").val());
            $("#areaFocusTarget").val(num.toFixed(0));
        }else{
            $("#areaFocusTarget").val("0");
        }
    }
    function peopleRete(){
        var NumO = $("#areaTarget").val();
        var NumT = $("#areaPopulation").val();
        var reta = parseFloat(NumO)/parseFloat(NumT)*100
        $("#areaRate").val(reta.toFixed(0));
    }
    //保存的方法
    function saveTable() {
        vo = uiToData("form_vo",vo);
        if($("#areaRate").val()<$("#mbl").text()){
            showMsg("目标率不能小于参考率");
            return;
        }
        if(parseInt($("#areaRate").val())>100||parseInt($("#areaRate").val())<0){
            showMsg("比例值范围是0~100");
            return;
        }
        if($("#handle").val()=="add"){
            if($("#level").val()=="1"){
                if(iecs(pro)){
                    showMsg("请选择省");
                    return;
                }
                vo["areaCode"]=pro;
            }else if($("#level").val()=="2"){
                if(iecs(city)){
                    showMsg("请选择市");
                    return;
                }
                vo["areaCode"]=city;
            }else if($("#level").val()=="3"){
                if(iecs(area)){
                    showMsg("请选择区");
                    return;
                }
                vo["areaCode"]=area;
            }else if($("#level").val()=="4"){
                if(iecs(street)){
                    showMsg("请选择街道");
                    return;
                }
                vo["areaCode"]=street;
            }
        }

        if(iecs($("#areaPopulation").val())){
            showMsg("人口数不能为空！");
            return;
        }
        if(iecs($("#areaYear").val())){
            showMsg("请选择年份");
            return;
        }
        var method = "add";
        if($("#id").val() != ""){
            method = "modify";
        };
        doAjaxPost('<%=request.getContextPath()%>/addrPeople.action?act='+method,{strJson : JSON.stringify(vo)},callDataSave);
    }

    function callDataSave(data){
        if(data.msg == 'true'){
            var url = '<%=request.getContextPath()%>/addrPeople.action?act=forList';
            defualtHref(url);
        }else{
            showMsg(data.msg);
        }
    }
    var pro="";
    var city="";
    var area="";
    var street="";
    function findAddress(id,pid){
        doAjaxPost('<%=request.getContextPath()%>/address.action?act=findCmmByPid',{pid : pid},function(data){
            $("#"+id).html("");
            var option = document.createElement('option');
            option.setAttribute("value","");
            $("#hospAddress").val("");
            if(id=='city'){
                pro = pid;
                option.innerText = "--请选择市--";
            }
            if(id=='area'){
               city = pid;
                option.innerText = "--请选择区--";
            }
            if(id=='street'){
                area = pid;
                option.innerText = "--请选择街道--";
            }
            if(id=="end"){
                street = pid;
            }
            document.getElementById(id).appendChild(option);
            if(pid==null||pid==""){

            }else{
                $.each(data,function(k,v){
                    dataUiCodeGroup("select",id,v.areaSname,v.id);
                })
            }
        });
        if($("#level").val()=="1"&&id=="city"){
            //查省的上一年度参考值
           doAjaxPost('<%=request.getContextPath()%>/addrPeople.action?act=findPeopleCmm',{code : pid},function(data){
               if(data.map!=null){
                   if(data.map.v1!=null){
                        var v= data.map.v1;
                       $("#areaPopulation").attr("placeholder","参考人数"+v.areaPopulation+"人");
                       $("#upAreaPopulation").val(data.areaPopulation);

                   }
                   if(data.map.v2!=null){
                       var v = data.map.v2;
                       $("#mbl").text(v.areaRate);
                       $("#zdl").text(v.areaFocusRate)
                   }
               }
            })
        }
        if($("#level").val()=="2"&&id=="area"){
            //查市的上一年度参考值
            doAjaxPost('<%=request.getContextPath()%>/addrPeople.action?act=findPeopleCmm',{code : pid},function(data){
                if(data.map!=null){
                    if(data.map.v1!=null){
                        var v= data.map.v1;
                        $("#areaPopulation").attr("placeholder","参考人数"+v.areaPopulation+"人");
                        $("#upAreaPopulation").val(data.areaPopulation);

                    }
                    if(data.map.v2!=null){
                        var v = data.map.v2;
                        $("#mbl").text(v.areaRate);
                        $("#zdl").text(v.areaFocusRate)
                    }
                }
            })
        }
        if($("#level").val()=="3"&&id=="street"){
            //查区的上一年度参考值
            doAjaxPost('<%=request.getContextPath()%>/addrPeople.action?act=findPeopleCmm',{code : pid},function(data){
                if(data.map!=null){
                    if(data.map.v1!=null){
                        var v= data.map.v1;
                        $("#areaPopulation").attr("placeholder","参考人数"+v.areaPopulation+"人");
                        $("#upAreaPopulation").val(data.areaPopulation);

                    }
                    if(data.map.v2!=null){
                        var v = data.map.v2;
                        $("#mbl").text(v.areaRate);
                        $("#zdl").text(v.areaFocusRate)
                    }
                }
            })
        }
        if($("#level").val()=="4"&&id=="end"){
            //查街道的上一年度参考值
            doAjaxPost('<%=request.getContextPath()%>/addrPeople.action?act=findPeopleCmm',{code : pid},function(data){
                if(data.map!=null){
                    if(data.map.v1!=null){
                        var v= data.map.v1;
                        $("#areaPopulation").attr("placeholder","参考人数"+v.areaPopulation+"人");
                        $("#upAreaPopulation").val(data.areaPopulation);

                    }
                    if(data.map.v2!=null){
                        var v = data.map.v2;
                        $("#mbl").text(v.areaRate);
                        $("#zdl").text(v.areaFocusRate)
                    }
                }
            })
        }
    }
    function show(){
        $("#province").val("");
        $("#city").val("");
        $("#area").val("");
        $("#street").val();
        if($("#level").val()=="1"){
            $("#city").hide();
            $("#area").hide();
            $("#street").hide();
        }else if($("#level").val()=="2"){
            $("#city").show();
            $("#area").hide();
            $("#street").hide();
        }else if($("#level").val()=="3"){
            $("#city").show();
            $("#area").show();
            $("#street").hide();
        }else if($("#level").val()=="4"){
            $("#city").show();
            $("#area").show();
            $("#street").show();
        }
    }
</script>
</body>
</html>
