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
    <title>服务包管理主页</title>
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
                    <a class="label label-primary pull-right system appSerMeal_list"  onclick="backTable();">返回</a>
                </div>
                <div class="ibox-content">
                    <form role="form" id="form_vo"  class="form-horizontal">
                        <input type="hidden" id="id" pofield="id" name="id" value="${vo.id}" />
                        <input type="hidden" id="handle"  name="handle" value="${handle}" />
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>服务包编号：</label>
                            <div class="col-sm-4">
                                <input type="hidden" id="value" value="0">
                                <%--<input type="text" id="sersmValue" name="sersmValue" pofield="sersmValue" class="form-control" onblur="findNum()">--%>
                                <input type="text" id="sersmValue" name="sersmValue" pofield="sersmValue" class="form-control" readonly="readonly">
                            </div>
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>服务包名称：</label>
                            <div class="col-sm-4">
                                <input type="text" id="sersmName" name="sersmName" pofield="sersmName" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>服务包内容：</label>
                            <div class="col-sm-4">
                                <div class="input-group">
                                    <span class="input-group-btn">
                                        <input type="hidden" id="sersmGroupValue" name="sersmGroupValue" pofield="sersmGroupValue">
                                        <input type="hidden" id="sersmGroupId" name="sersmGroupId" pofield="sersmGroupId">
                                        <button type="button" class="btn btn-primary"  data-toggle="modal" data-target="#myModalHospDept" onclick="ChangeDateToUser()">选择组合</button>
                                    </span>
                                </div>
                            </div>
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>服务包签约编号：</label>
                            <div class="col-sm-4">
                                <input type="text" id="sersmCode" name="sersmCode" pofield="sersmCode" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label" ><span class="text-danger">*</span>服务人群：</label>
                            <input type="hidden" id="sersmObjectType" name="sersmObjectType" pofield="sersmObjectType">
                            <input type="hidden" id="sersmObjectValue" name="sersmObjectValue" pofield="sersmObjectValue">
                            <input type="hidden" id="sersmObjectTitle" name="sersmObjectTitle" pofield="sersmObjectTitle">
                            <div class="col-sm-10" id="fwrq">

                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label" ><span class="text-danger">*</span>服务内容：</label>
                            <input type="hidden" id="sersmPkType" name="sersmPkType" pofield="sersmPkType">
                            <input type="hidden" id="sersmPkValue" name="sersmPkValue" pofield="sersmPkValue">
                            <input type="hidden" id="sersmPkTitle" name="sersmPkTitle" pofield="sersmPkTitle">
                            <div class="col-sm-10" id="fwnr">

                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label" ><span class="text-danger">*</span>是否补贴：</label>
                            <div class="col-sm-4">
                                <div class="input-group" id="sersmDownState">

                                </div>
                            </div>
                        </div>
                        <div class="form-group" id="ego" style="display: none">
                            <div id="down">
                                <label class="col-sm-2 control-label" ><span class="text-danger">*</span>减免对象经济类型：</label>
                                <div class="col-sm-4">
                                    <div class="input-group" id="sersmJjType">

                                    </div>
                                </div>
                            </div>
                            <label class="col-sm-2 control-label" ><span class="text-danger">*</span>政府补贴方式：</label>
                            <div class="col-sm-4">
                                <div class="input-group" id="sersmSubsidyWay">

                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label" ><span class="text-danger">*</span>服务包总金额：</label>
                            <div class="col-sm-4">
                                <div class="input-group">
                                    <input type="number" id="sersmTotalFee" name="sersmTotalFee" pofield="sersmTotalFee" class="form-control" value="0" onblur="reload()">
                                    <span class="input-group-addon">元</span>
                                </div>
                            </div>
                            <label class="col-sm-2 control-label" >个人需支付金额：</label>
                            <div class="col-sm-4">
                                <div class="input-group" id="sersmOneFee">
                                    <%--<input type="text" id="sersmOneFee" name="sersmOneFee" pofield="sersmOneFee" class="form-control" readonly="readonly">--%>
                                </div>
                            </div>
                        </div>

                        <div class="form-group" id="qzyh">
                            <label class="col-sm-2 control-label">个人支付优惠：</label>
                            <div class="col-sm-2">
                                <div class="input-group" id="sersmIsDiscount">

                                </div>
                            </div>
                            <div class="col-sm-4">
                                <div class="input-group">
                                    <div class="input-group" id="discount">
                                        <input type="number" id="sersmDiscount" name="sersmDiscount" pofield="sersmDiscount" class="form-control" value="0" onblur="reload()">
                                        <span class="input-group-addon">元</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label" ><span class="text-danger">*</span>服务包协议有效期：</label>
                            <div class="col-sm-4">
                                <div class="input-group" id="sersmYxTimeType">

                                </div>
                            </div>
                            <div id="yxTime" style="display: none">
                                <label class="col-sm-2 control-label" ><span class="text-danger">*</span>时间：</label>
                                <div class="col-sm-2" >
                                    <div class="input-group">
                                        <%--<input type="text" id="sersmStartTime" name="sersmStartTime" pofield="sersmStartTime" class="form-control" onfocus="WdatePicker({dateFmt:'MM-dd'})">--%>
                                            <input type="text" id="sersmStartTime" name="sersmStartTime" pofield="sersmStartTime" class="form-control" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})">
                                    </div>
                                </div>
                                <div class="col-sm-2" >
                                    <div class="input-group">
                                        <input type="text" id="sersmEndTime" name="sersmEndTime" pofield="sersmEndTime" class="form-control" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label" ><span class="text-danger">*</span>有效期间是否可变更医生：</label>
                            <div class="col-sm-4"id="sersmBgDr">

                            </div>

                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label" ><span class="text-danger">*</span>是否是基础服务包：</label>
                            <div class="col-sm-4" id="sersmJcState">

                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label" ><span class="text-danger">*</span>编辑协议：</label>
                            <div class="col-sm-10">
                            <textarea id="sersmBook" name="sersmBook" pofield="sersmBook" class="form-control"></textarea>
                        </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">服务包图片：</label>
                            <div class="col-sm-4">
                                <input  type="file" id="image" name="image" onchange="addfile();"/>
                            </div>
                            <label class="col-sm-2 control-label">当前图片：</label>
                            <div class="col-sm-4">
                                <div id="simage"></div>
                                <input type="hidden" id="sersmImageUrl" name="sersmImageUrl" pofield="sersmImageUrl" />
                                <input type="hidden" id="sersmImageName" name="sersmImageName" pofield="sersmImageName">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>开放区域：</label>
                            <div class="col-sm-8">
                                <div class="input-group">
                                    <span class="input-group-btn">
                                        <input type="hidden" id="sersmOpenArea" name="sersmOpenArea" pofield="sersmOpenArea">
                                        <input class = "form-control"  type="text" id="sersmOpenName" readonly="readonly">
                                        <button type="button" class="btn btn-primary"  data-toggle="modal" data-target="#myModalArea" onclick="ChangeDateToArea()" >选择开放区域</button>
                                    </span>
                                </div>
                            </div>
                        </div>
                        <div class="text-center btnBar">
                            <a id="handleMethod" class="btn btn-primary system appSerMeal_add" onclick="saveTable();">保存</a>
                        </div>
                    </form>
                </div>

            </div>
        </div>
    </div>
</div>
<jsp:include page="appSerMeal_ChangeArea.jsp" flush="false" />
<jsp:include page="appSerMeal_ChangeGroup.jsp" flush="false" />
<script type="text/javascript">
    var HospAreaCode ='<%=request.getSession().getAttribute("HospAreaCode")%>'=='null'?decodeURI(getQuerytoString("orgAreaCode")):'<%=request.getSession().getAttribute("HospAreaCode")%>';
    var vo={};
    //页面加载完成时启动的方法
    $(document).ready(function() {
        if("3505" == HospAreaCode.substring(0,4)){
            $("#qzyh").show();
        }else{
            $("#qzyh").hide();
        }
        findDept();
        findTreeTable();
        findCmmInit();//初始化
    });
    //统一初始化
    function findCmmInit(){
        doAjaxPost('<%=request.getContextPath()%>/appSerMeal.action?act=findCmmInit',{},callDataToInit);
        doAjaxPost('<%=request.getContextPath()%>/appSerMeal.action?act=findCmmEag',{},function (data){
            $("#sersmJjType").html("");
            //var str = "<table>"
            $.each(data.rows,function(k,v){
//                str +="<tr><input type='checkbox' name='sersmJjType' pofield='sersmJjType' idd='" +
//                    v.id+";;;"+v.eagEconTitle+"' onclick='showBt(this)'>"+v.eagEconTitle+"</tr>";
                var input = document.createElement('input');
                var idd = v.id;
                input.setAttribute("type","checkbox");
                input.setAttribute("name","sersmJjType");
                input.setAttribute("pofield","sersmJjType");
                input.setAttribute("id","sersmJjType");
                input.setAttribute("idd",v.id+";;;"+v.eagEconTitle);
                input.setAttribute("value",v.eagEconValue);
                input.setAttribute("onclick","showBt(this)");
                $("#sersmJjType").append(input); //3、在末尾中添加元素
                $("#sersmJjType").append(v.eagEconTitle+" "); //3、在末尾中添加元素
//                    dataUiCodeGroup("checkbox","sersmJjType",v.econTitle+" ",v.econValue);
            })
//            str+="</table>";
//            $("#sersmJjType").html(str);
        });
    }
    function callDataToInit(data){
        if(data.map != null){
            if(data.map.ls != null){
                $("#sersmDownState").html("");
                $("#sersmBgDr").html("");
                $.each(data.map.ls, function(k, v) {
                    var input = document.createElement('input');
                    input.setAttribute("type","radio");
                    input.setAttribute("name","sersmDownState");
                    input.setAttribute("pofield","sersmDownState");
                    input.setAttribute("id","sersmDownState");
                    input.setAttribute("value",v.codeValue);
                    input.setAttribute("onclick","showDown("+v.codeValue+")");
                    $("#sersmDownState").append(input); //3、在末尾中添加元素
                    $("#sersmDownState").append(v.codeTitle+" "); //3、在末尾中添加元素
                    var input1 = document.createElement('input');
                    input1.setAttribute("type","radio");
                    input1.setAttribute("name","sersmBgDr");
                    input1.setAttribute("pofield","sersmBgDr");
                    input1.setAttribute("id","sersmBgDr");
                    input1.setAttribute("value",v.codeValue);
                    $("#sersmBgDr").append(input1); //3、在末尾中添加元素
                    $("#sersmBgDr").append(v.codeTitle+" "); //3、在末尾中添加元素
                    var input2 = document.createElement('input');
                    input2.setAttribute("type","radio");
                    input2.setAttribute("name","sersmJcState");
                    input2.setAttribute("pofield","sersmJcState");
                    input2.setAttribute("id","sersmJcState");
                    input2.setAttribute("value",v.codeValue);
                    $("#sersmJcState").append(input2); //3、在末尾中添加元素
                    $("#sersmJcState").append(v.codeTitle+" "); //3、在末尾中添加元素
                    var input3 = document.createElement("input");
                    input3.setAttribute("type","radio");
                    input3.setAttribute("name","sersmIsDiscount");
                    input3.setAttribute("pofield","sersmIsDiscount");
                    input3.setAttribute("id","sersmIsDiscount");
                    input3.setAttribute("value",v.codeValue);
                    input3.setAttribute("onClick","showDiscount("+v.codeValue+")");
                    $("#sersmIsDiscount").append(input3);
                    $("#sersmIsDiscount").append(v.codeTitle+" ");

                });
                if($("#handle").val() == "add"){
                    $("input[name='sersmDownState'][value=0]").attr("checked",true);
                    $("input[name='sersmBgDr'][value=0]").attr("checked",true);
                    $("input[name='sersmJcState'][value=0]").attr("checked",true);
                    $("input[name='sersmIsDiscount'][value=0]").attr("checked",true);
                }

            }
            /*if(data.map.jjlx!=null){
                $("#sersmJjType").html("");
                $.each(data.map.jjlx,function(k,v){
                    var input = document.createElement('input');
                    var idd = v.id;
                    input.setAttribute("type","checkbox");
                    input.setAttribute("name","sersmJjType");
                    input.setAttribute("pofield","sersmJjType");
                    input.setAttribute("id","sersmJjType");
                    input.setAttribute("idd",v.id);
                    input.setAttribute("value",v.econValue);
                    input.setAttribute("onclick","showBt(this)");
                    $("#sersmJjType").append(input); //3、在末尾中添加元素
                    $("#sersmJjType").append(v.econTitle+" "); //3、在末尾中添加元素
//                    dataUiCodeGroup("checkbox","sersmJjType",v.econTitle+" ",v.econValue);
                })
            }*/
           /* if(data.map.btfs!=null){
                $("#sersmSubsidyWay").html("");
                $.each(data.map.btfs,function(k,v){
                    dataUiCodeGroup("checkbox","sersmSubsidyWay",v.govTitle+" ",v.govValue);
                })
            }*/
            if(data.map.tcyx!=null){
                $("#sersmYxTimeType").html("");
                $.each(data.map.tcyx,function(k,v){
                    var input = document.createElement('input');
                    input.setAttribute("type","radio");
                    input.setAttribute("name","sersmYxTimeType");
                    input.setAttribute("pofield","sersmYxTimeType");
                    input.setAttribute("id","sersmYxTimeType");
                    input.setAttribute("value",v.codeValue);
                    input.setAttribute("onclick","showTime("+v.codeValue+")");
                    $("#sersmYxTimeType").append(input); //3、在末尾中添加元素
                    $("#sersmYxTimeType").append(v.codeTitle+" "); //3、在末尾中添加元素
                })
            }
            if($("#handle").val()=="add"){
                $("#sersmValue").val(data.map.code);
            }
            onInit();//回选
        }
    }

    //加载完成时，查询数据，记载到表格的方法
    function onInit() {
        if($("#id").val()!=""){
            doAjaxPost('<%=request.getContextPath()%>/appSerMeal.action?act=jsonByOne',{id:$("#id").val()},callDataToDrUser);
        }
        if($("#handle").val() == "look"){
            $("#handleMethod").hide();
            $("#userAddTit").text("查看服务包信息");
        }else if($("#handle").val() == "modify"){
            $("#handleMethod").text("修改");
            $("#userAddTit").text("修改服务包信息");
        }else if($("#handle").val() == "add"){
            $("#discount").hide();
            $("#userAddTit").text("新增服务包信息");
        }
    }
    function callDataToDrUser(data){
        dataToUi(data,"form_vo");
        var types = document.getElementsByName("sersmJjType");
        $.each(types,function(k,v){
            $(v).attr("checked", false);
            if(necs(data.sersmJjId)){
                var ss = $(v).attr("idd").split(";;;")[0];
                if(data.sersmJjId.indexOf(ss)!=-1){
                    $(v).attr("checked", true);
                }
            }

        })
        if(necs(data.sersmObjectType)){
            var strs = data.sersmObjectType.split(";");
            var strTitles = data.sersmObjectTitle.split(",");
            var fwrqs = "";
            for(var i=0;i<strs.length;i++){
                if(strs[i]=="1"){
                    fwrqs += createLabel(strTitles[i],"0");
                }else{
                    fwrqs += createLabel(strTitles[i],"1");
                }
            }
            $("#fwrq").html(fwrqs);
        }

        $("#sersmOpenName").val(data.strAreaName);

        if(necs(data.sersmPkType)){
//            alert(JSON.stringify(data));
            var strs = data.sersmPkType.split(";");
            var strTitles = data.sersmPkTitle.split(",");
            var fwnrs = "";
            for(var i=0;i<strs.length;i++){
                if(strs[i]=="1"){
                    fwnrs += createLabel(strTitles[i],"1");
                }else{
                    fwnrs += createLabel(strTitles[i],"0");
                }
            }
//            chageHospDept();
            $("#fwnr").html(data.strSersmPkTitles);
        }
        if(data.sersmImageUrl != "" && data.sersmImageUrl != null){
            $('#simage').append('<img width="200" height="100" src="<%=request.getContextPath()%>/image.action?act=getUrlImage&url='+data.sersmImageUrl+'"/>')
        }
        if(data.sersmYxTimeType=="4"){
            $("#yxTime").show();
        }
        if(data.sersmDownState=="1"){
            $("#ego").show();
        }
        if(data.sersmDownState=="0"){
            $("#sersmOneFee").html(data.sersmOneFee);
        }
        var che = document.getElementsByName("sersmJjType");
        $.each(che,function (k,v) {
            if($(this).prop("checked")){
                showBt(v);
            }
        })
        if(necs(data.sersmGroupId)){
            var qqvo = {};
            qqvo["groupId"]=data.sersmGroupId;
            qqvo["pageSize"]="99";
            doAjaxPost('<%=request.getContextPath()%>/appSerMeal.action?act=findCmmGroup',{qvoJson:JSON.stringify(qqvo)},function(dataa){
                var stt = "";
                $.each(dataa.rows, function(k, v) {
                    stt += "<tr id='"+v.id+"'><td pofield='id'>"+v.id+"</td><td pofield='sname'><input type='checkbox' name='changeHospDept' id='changeHospDept' onclick='addOrDel(this)'" +
                        " value='"+v.sergValue+";;;"+v.sergObjectValue+";;;"+v.sergObjectTitle+";;;"+v.sergObjectType+
                        ";;;"+v.sergPkValue+";;;"+v.sergPkTitle+";;;"+v.sergPkType+";;;"+v.sergGroupFee+"'>" +v.sergObjectTitle+"</td><td pofield='content'>"+v.strSergPkTitle+"</td>" +
                        "<td pofield='state'>"+v.sergGroupFee+"</td></tr>";
                });
                $("#text").html(stt);
            });
        }
        if(data.sersmIsDiscount==1){
            $("#discount").show();
        }else{
            $("#discount").hide();
        }
    }

    //点击返回
    function backTable(){
        history.go(-1);
    }

    //保存的方法
    function saveTable() {
        debugger
        if(iecs($("#sersmValue").val())){
            showMsg("套餐编号不能为空！");
            $("#sersmValue").focus();
            return;
        }
        if(iecs($("#sersmName").val())){
            showMsg("套餐名称不能为空！");
            $("#sersmName").focus();
            return;
        }
        if(iecs($("#sersmTotalFee").val())){
            showMsg("请输入费用");
            $("#sersmTotalFee").focus();
            return;
        }
        if($("#value").val()=="1"){
            showMsg("服务编号已存在");
            return;
        }
//        var baseValue = "";
//        $('input[name="baseFw"]:checked').each(function(){
//            baseValue +=$(this).val()+";";
//        });
//        $('input[name="tsFw"]:checked').each(function(){
//            baseValue +=$(this).val()+";";
//        });
//        if(iecs(baseValue)){
//            showMsg("请选择服务内容");
//            return;
//        }
        vo = uiToData("form_vo",vo);
        var jjType = "";
        var jjId = "";
        var obj=document.getElementsByName('sersmJjType');
        for(var i=0; i<obj.length; i++){
            if(obj[i].checked) {
                jjType+=obj[i].value+';';
                var strs = $(obj[i]).attr("idd").split(";;;");
                jjId += strs[0]+";";
            }
        }
        vo["sersmJjType"]=jjType;
        vo["sersmJjId"]=jjId;
        /*var zfbtt="";
        var zfbt = document.getElementsByName("sersmSubsidyWay");
        for(var i=0;i<zfbt.length;i++){
            if(zfbt[i].checked){
                zfbtt += zfbt[i].value + ";";
            }
        }
        vo["sersmSubsidyWay"]=zfbtt;*/
        //个人支付金额
        var labelM = $("#sersmOneFee").find("span");
        var strs="";
        var fllag = false;
        var total = 0;
        $.each(labelM,function(k,v){
            fllag = true;
//            alert($(v).find("label").attr("pofield"));
//            alert($("#sersmOneFee span label").val())
//            alert(document.getElementsByName("sersmOneFee")[k].getAttribute("value"));
//            alert(document.getElementsByName("sersmOneFee")[k].getAttribute("pofield"));
            var ids = $(v).find("label").attr("pofield");
            var value = $(v).find("label").attr("value");
            if(ids == "m1"){
                strs = value;
            }else {
                strs += ids+":"+value+";";
            }
            total+=Number(value);

        })
        total=total.toFixed(1);
        if(fllag){
            vo["sersmOneFee"]=strs;
            vo["sersmTotalOneFee"]=total-$("#sersmDiscount").val();
        }else{
            vo["sersmOneFee"]=$("#sersmOneFee").html();
            vo["sersmTotalOneFee"]=$("#sersmOneFee").html()-$("#sersmDiscount").val();
        }

        var method = "add";
        if($("#id").val() != ""){
            method = "modify";
        };
        doAjaxPost('<%=request.getContextPath()%>/appSerMeal.action?act='+method,{strJson:JSON.stringify(vo)},callDataSave);
    }

    function callDataSave(data){
        if(data.msg == 'true'){
            var url = '<%=request.getContextPath()%>/appSerMeal.action?act=forList';
            defualtHref(url);
        }else{
            showMsg(data.msg);
        }
    }
    function findNum(){
        var value = $("#sergValue").val();
        if(necs(value)){
            doAjaxPost('<%=request.getContextPath()%>/appSerMeal.action?act=findCmmValue',{value : value},function(data){
                if(data.msg=="true"){
                    showMsg("该编号已存在");
                    $("#value").val("1");
                }else{
                    $("#value").val("0");
                }
            });
        }
    }
    function ChangeDateToUser(){
        $('input[name="changeHospDept"]').each(function(){
            var strs = $(this).parent().parent().find("td[pofield=id]").text();
            if($("#sersmGroupId").val().indexOf(strs)!=-1){
                $(this).attr("checked", true);
            }

        });
    }
    //上传文件
    function addfile(){
        var pic = $("#image").val().split(".");
        if(pic[1]==undefined || (pic[1].toLocaleLowerCase() !='jpg'&&pic[1].toLocaleLowerCase()!='jpeg'&&pic[1].toLocaleLowerCase()!='png')){
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
                    $("#sersmImageUrl").val(da.vo.filePath);
                    $("#sersmImageName").val(da.vo.fileName);
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
    function showTime(num){
        if(num=="4"){
            $("#yxTime").show();
        }else{
            $("#yxTime").hide();
            $("#sersmStartTime").val("");
            $("#sersmEndTime").val("");
        }
    }
    function showDown(num){
        $("#sersmOneFee").html("");
        if(num=="1"){
//            $("#down").show();
            $("#ego").show();
        }else{
//            $("#down").hide();
            $("#ego").hide();
            var jjType = document.getElementsByName("sersmJjType");
            for(var i=0;i<jjType.length;i++){
                if(jjType[i].checked){
                   $(jjType[i]).attr("checked",false);
                }
            }
            var sttr = "<span id='m'><label name='sersmOneFee' value='0'>"+Number($("#sersmTotalFee").val())+"</label><br></span>";
            $("#sersmOneFee").append(Number($("#sersmTotalFee").val()));
        }
    }
    function showBt(ui){
        var strs =$(ui).attr("idd").split(";;;");
        if($(ui).prop("checked")){
            var id = strs[0];
            doAjaxPost('<%=request.getContextPath()%>/appSerMeal.action?act=findCmmBt',{id : id},function(data){
                if(data.rows!=null){
                    var str = "<span id='"+strs[0]+"'><label>"+strs[1]+"：";
                    var money = 0;
                    var sttr = "";
                    $.each(data.rows,function(k,v){
                        if(v.govMoney!=null && v.govMoney!=""){
                            str +=v.govTitle+"("+v.govMoney+"元),";
                        }else{
                            str +=v.govTitle+",";
                        }
                        money +=Number(v.govMoney);
//                        dataUiCodeGroup("checkbox","sersmSubsidyWay",v.govTitle+" ",v.govValue);
                    })
                    var totalMoney = Number($("#sersmTotalFee").val());
                    sttr = "<span id='m"+strs[0]+"'><label pofield='"+strs[0];
                    if(totalMoney==0 || totalMoney<money){
                        sttr +="' name='sersmOneFee' value='0'>"+strs[1]+"："+ 0+"元；";
                    }else {
                        var num = Number(totalMoney)-Number(money);
                        var num = num.toFixed(1);
                        sttr +="' name='sersmOneFee' value='"+num+"'>"+strs[1]+"："+num+"元；";
                    }
                    str += "</label><br></span>";
                    sttr +="</label><br></span>";
                   // alert(sttr);
                    $("#sersmSubsidyWay").append(str);
                    $("#sersmOneFee").append(sttr);
                }
            })
        }else{
            $("#"+strs[0]+"").remove();
            $("#m"+strs[0]+"").remove();
        }

    }
    function print(){
        var groupValue = $("#sersmGroupValue").val();
//        alert(groupValue);
        doAjaxPost('<%=request.getContextPath()%>/appSerMeal.action?act=find',{id : id},function(data){

        })

    }
    //指定开放对象
    function ChangeDateToArea(){
        $("#serId").val($("#id").val());
//        findDept();
    }
    /**
     * 重新加载个人支付费用
     */
    function reload(){
        $("#sersmOneFee").html("");
        var moneys = document.getElementsByName("sersmJjType");
        var flag = true;
        $.each(moneys,function(k,v){
            if($(v).prop("checked")){
                var strs =$(v).attr("idd").split(";;;");
                flag = false;
                var id = strs[0];
                doAjaxPost('<%=request.getContextPath()%>/appSerMeal.action?act=findCmmBt',{id : id},function(data){
                    if(data.rows!=null){
                        var money = 0;
                        var sttr = "";
                        $.each(data.rows,function(k,v){
                            money +=Number(v.govMoney);
                        })
                        var totalMoney = Number($("#sersmTotalFee").val());
                        sttr = "<span id='m"+strs[0]+"'><label pofield='"+strs[0];
                        if(totalMoney==0 || totalMoney<money){
                            sttr +="' name='sersmOneFee' value='0'>"+strs[1]+"："+ 0+"元；";
                        }else {
                            var num = totalMoney-money;
                            num = num.toFixed(1);
                            sttr +="' name='sersmOneFee' value='"+num+"'>"+strs[1]+"："+num+"元；";
                        }
                        sttr +="</label><br></span>";
                        $("#sersmOneFee").append(sttr);
                    }
                })
            }
        });
        if(flag){
            var sttr = "<span id='m'><label pofield='m1' name='sersmOneFee' value='0'>"+Number($("#sersmTotalFee").val())+"</label><br></span>";
            $("#sersmOneFee").append(Number($("#sersmTotalFee").val()));
        }
    }
    //显示个人优惠
    function showDiscount(num){
        if(num==0){
            $("#discount").hide();
            $("#sersmDiscount").val(0);
        }else{
            $("#discount").show()
        }
    }
</script>
</body>
</html>
