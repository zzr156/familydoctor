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
    <title>医院管理主页</title>
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
                        <input type="hidden" id="id" pofield="id" name="id" value="${vo.id}" />
                        <input type="hidden" id="handle"  name="handle" value="${handle}" />
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>单位名称：</label>
                            <div class="col-sm-4">
                                <input id="hospName" name="hospName" pofield="hospName" type="text" class="form-control">
                            </div>
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>医院编码：</label>
                            <div class="col-sm-4">
                                <input id="hospCode" name="hospCode" pofield="hospCode" type="text" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>医院级别：</label>
                            <div class="col-sm-4"  >
                                <select id="hospLevel" class="form-control" onchange="show()">
                                    <option>--请选择--</option>
                                </select>
                            </div>
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>是否开启签约服务：</label>
                            <div class="col-sm-4" id="hospSerState" >

                            </div>

                        </div>
                        <div class="form-group" id="cityArea">
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>区域：</label>
                            <input type="hidden" id="hospCityareaId" name="hospCityareaId" pofield="hospCityareaId">
                            <div class="col-sm-2" id="province" style="display: none">
                                <select id="provinceId" onchange="findAddress('cityId',$('#provinceId option:selected').val())" class="form-control">
                                    <option value="0">--请选择省--</option>
                                </select>
                            </div>
                            <div class="col-sm-2" id="city" style="display: none">
                                <select id="cityId" onchange="findAddress('areaId',$('#cityId option:selected').val())" class="form-control">
                                    <option value="0">--请选择市--</option>

                                </select>
                            </div>
                            <div class="col-sm-2" id="area" style="display: none">
                                <select id="areaId" onchange="findAddress('streetId',$('#areaId option:selected').val())" class="form-control">
                                    <option value="0">--请选择区--</option>
                                </select>
                            </div>
                            <div class="col-sm-2" id="street" style="display: none">
                                <select id="streetId" class="form-control" onchange="findAddress('villageId',$('#streetId option:selected').val())">
                                    <option value="0">--请选择街道--</option>
                                </select>
                            </div>
                            <div class="col-sm-2" id="village" style="display:none;">
                                <select id="villageId" class="form-control" onchange="findAddress('end',$('#villageId option:selected').val())">
                                    <option value="0">--请选择社区--</option>
                                </select>
                            </div>

                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>状态：</label>
                            <div class="col-sm-4" id="hospState">

                            </div>
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>医院电话：</label>
                            <div class="col-sm-4" >
                                <input id="hospTel" name="hospTel" pofield="hospTel" type="text" class="form-control">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label">医院地址：</label>
                            <div class="col-sm-10">
                                <input onblur="" id="hospAddress" name="hospAddress" pofield="hospAddress" type="text" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">医院简介：</label>
                            <div class="col-sm-10">
                                <textarea id="hospIntro" name="hospIntro" pofield="hospIntro" type="text" class="form-control"></textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">医院图片：</label>
                            <div class="col-sm-4">
                                <input type="hidden" id="hospImageurl" name="hospImageurl" pofield="hospImageurl" />
                                <input  type="file" id="image" name="image" onchange="addfile();"/>
                            </div>
                            <div class="col-sm-4" style="margin-top: 10px">
                                <div class="col-sm-1"></div>
                                <div class="col-sm-10" id="simage">

                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">医院类型：</label>
                            <div class="col-sm-10" id="hospLevelType">

                            </div>
                        </div>
                        <div class="form-group" style="margin-left: 80px">
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>是否开启疾病类型标签：</label>
                            <div class="col-sm-4" id="hospLabelState" style="margin-top: 5px">
                                <input type="radio" name="labelState" value="0" title="否" style="width:12px;height:12px;" pofield="labelState" checked> 否
                                <input type="radio" name="labelState" value="1" title="是" style="width:12px;height:12px;" pofield="labelState">  是
                            </div>
                            <label class="col-sm-2 control-label">页面风格：</label>
                            <div class="col-sm-4" id="hospPageStyle">

                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">机构X轴坐标：</label>
                            <div class="col-sm-4" style="margin-top: 5px">
                                <input id="hospX" name="hospX" pofield="hospX" type="text" class="form-control">
                            </div>
                            <label class="col-sm-2 control-label">机构Y轴坐标：</label>
                            <div class="col-sm-4" style="margin-top: 5px">
                                <input id="hospY" name="hospY" pofield="hospY" type="text" class="form-control">
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
    var level=0;
    //页面加载完成时启动的方法
    $(document).ready(function() {
        findCmmInit();//初始化
    });

    //统一初始化
    function findCmmInit(){
        doAjaxPost('<%=request.getContextPath()%>/apphosp.action?act=findCmmInit',{},callDataToInit);
    }
    function callDataToInit(data){
        if(data.map != null) {
            //医院级别
            if (data.map.hospLevel != null) {
                //$("#hospLevel").html("");
                $.each(data.map.hospLevel, function (k, v) {
                    dataUiCodeGroup("select", "hospLevel", v.codeTitle, v.codeValue);
                });
            }
            //页面风格
            if (data.map.pageStyle != null) {
                //$("#hospPageStyle").html("");
                $.each(data.map.pageStyle, function (k, v) {
                    dataUiCodeGroup("radio", "hospPageStyle", v.codeTitle+" ", v.codeValue);
                });
                if($("#handle").val() == "add"){
                    $("input[name='hospPageStyle'][value=1]").attr("checked",true);
                }
            }
            //状态
            if (data.map.state != null) {
                //$("#hospState").html("");
                $.each(data.map.state, function (k, v) {
                    dataUiCodeGroup("radio", "hospState", v.codeTitle+" ", v.codeValue);
                });
                if($("#handle").val() == "add"){
                    $("input[name='hospState'][value=1]").attr("checked",true);
                }
            }
            if(data.map.sf!=null){
                $.each(data.map.sf, function (k, v) {
                    dataUiCodeGroup("radio", "hospSerState", v.codeTitle+" ", v.codeValue);
                });
                if($("#handle").val() == "add"){
                    $("input[name='hospSerState'][value=0]").attr("checked",true);
                }
            }
            if(data.map.hospLevelType !=null){
                $.each(data.map.hospLevelType, function (k, v) {
                    dataUiCodeGroup("radio", "hospLevelType", v.codeTitle+" ", v.codeValue);
                });
                if($("#handle").val() == "add"){
                    $("input[name='hospLevelType'][value=7]").attr("checked",true);
                }
            }

        }
           onInit();//回选
    }

    //加载完成时，查询数据，记载到表格的方法
    function onInit() {
        if($("#handle").val() == "look"){
            $("#handleMethod").hide();
            $("#title").text("查看医院信息");
        }else if($("#handle").val() == "modify"){
            $("#handleMethod").text("修改");
            $("#title").text("修改医院信息");
        }else if($("#handle").val() == "add"){
            $("#title").text("新增医院信息");
        }

        if($("#id").val()!=""){
            doAjaxPost('<%=request.getContextPath()%>/apphosp.action?act=jsonByOne',{id:$("#id").val()},callToTable);
        }

    }
    function callToTable(data) {
        $("#cityArea").hide();
        dataToUi(data,"form_vo");
        if(data.hospLevel!=null){
            $("#hospLevel").val(data.hospLevel);
            $("#hospLevel").attr("disabled",true);
        }
        $("#hospCityareaId").val(data.hospCityareaId);
        $("#hospImageurl").val(data.hospImageurl);
        $('#simage').html("");
        $('#simage').append('<img width="200" height="100" src="<%=request.getContextPath()%>/image.action?act=getUrlImage&url='+data.hospImageurl+'"/>')
    }


    //选择级别
    function show(){
        if($("#hospLevel").val()=="1"){
            $("#province").show();
            $("#city").hide();
            $("#area").hide();
            $("#street").hide();
            $("#village").hide();
            level=1;
            $("#cityId").val("");
            $("#areaId").val("");
            $("#streetId").val("");
            $("#village").val("");
            findProvince();
        }else if($("#hospLevel").val()=="2"){
            $("#province").show();
            $("#city").show();
            $("#area").hide();
            $("#street").hide();
            $("#village").hide();
            level=2;
            $("#areaId").val("");
            $("#streetId").val("");
            $("#village").val("");
            findProvince();
        }else if($("#hospLevel").val()=="3"){
            $("#province").show();
            $("#city").show();
            $("#area").show();
            $("#street").hide();
            $("#village").hide();
            level=3;
            $("#streetId").val("");
            $("#village").val("");
            findProvince();
        }else if($("#hospLevel").val()=="4"){
            $("#province").show();
            $("#city").show();
            $("#area").show();
            $("#street").show();
            $("#village").hide();
            level=4;
            $("#village").val("");
            findProvince();
        }else if($("#hospLevel").val()=="5"){
            $("#province").show();
            $("#city").show();
            $("#area").show();
            $("#street").show();
            $("#village").show();
            level=5;
            findProvince();
        }

    }

    var pro = "";
    var city = "";
    var area = "";
    var street = "";
    var village = "";
    function findAddress(id,pid,value){
        doAjaxPost('<%=request.getContextPath()%>/address.action?act=findCmmByPid',{pid : pid},function(data){
            $("#"+id).html("");
            var option = document.createElement('option');
            option.setAttribute("value","");
            $("#hospAddress").val("");
            if(id=='cityId'){
                pro = "";
                if($("#provinceId").val()=="0"||$("#provinceId").val()==""){
                    cz("cityId");
                    pro="";
                }else{
                    pro = $("#provinceId option:selected").text();
                }
                city="";
                area = "";
                street = "";
                village="";
                option.innerText = "--请选择市--";
            }
            if(id=='areaId'){
                city="";
                if($("#cityId").val()==""){
                    cz("areaId");
                    city="";
                }else{
                    city = $("#cityId option:selected").text();
                }
                area = "";
                street = "";
                village="";
                option.innerText = "--请选择区--";
            }
            if(id=='streetId'){
                area = "";
                if($("#areaId").val()==""){
                    cz("streetId");
                    area="";
                }else{
                    area = $("#areaId option:selected").text();
                }
                street = "";
                village="";
                option.innerText = "--请选择街道--";
            }
            if(id=="villageId"){
                street = "";
                if($("#villageId").val()==""){
                    cz("cityId");
                    street="";
                }else{
                    street = $("#streetId option:selected").text();
                }
                village="";
                option.innerText = "--请选择社区--";
            }
            if(id=="end"){
                village = "";
                if($("#villageId").val()==""){
                    village="";
                }else{
                    village = $("#villageId option:selected").text();
                }
            }
            $("#hospAddress").val(pro+city+area+street+village);
            document.getElementById(id).appendChild(option);
            if(pid==null||pid==""){
               /* if(id=="cityId"){
                    $.each(data,function(k,v){
                        dataUiCodeGroup("select",id,v.areaSname,v.id);
                    })
                }*/
            }else{
                $.each(data,function(k,v){
                    dataUiCodeGroup("select",id,v.areaSname,v.id);
                })

            }


            if(value!=""){
                $("#"+id).val(value);
            }
        });
    }
    function cz(id){
        $("#"+id).html("");
        var option = document.createElement('option');
        option.setAttribute("value","");
        if(id=="cityId"){
            cz("areaId");
            option.innerText = "--请选择市--";
        }else if(id=="areaId"){
            cz("streetId");
            option.innerText = "--请选择区--";
        }else if(id=="streetId") {
            cz("villageId");
            option.innerText = "--请选择街道--";
        }else if(id=="villageId"){
            option.innerText = "--请选择社区--";
        }
       // document.getElementById(id).appendChild(option);
    }
    //查找省
    function findProvince(){
        var pid=null;
        doAjaxPost('<%=request.getContextPath()%>/address.action?act=findCmmByPid',{pid:pid},function (data) {
            if(data!=null){
                $("#provinceId").html("");
                var option = document.createElement('option');
                option.setAttribute("value","");
                option.innerText = "--请选择省--";
                document.getElementById("provinceId").appendChild(option);
                $.each(data, function (k, v) {
                    dataUiCodeGroup("select", "provinceId", v.areaSname, v.id);
                });
            }
        });
    }

    //点击返回
    function backTable(){
        history.go(-1);
    }

    //上传文件
    function addfile(){
        var pic = $("#image").val().split(".");
        if(pic[1]==undefined || (pic[1]!='jpg'&&pic[1]!='jpeg'&&pic[1]!='png')){
            layer.open({
                skin: 'layui-layer-molv',
                closeBtn: 0,
                title: false,
                content:'上传照片格式不正确（照片格式应该为jpg/jpeg/png）!' ,
                anim: 5 ,
                btn: ['关闭']
            });
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
                    $("#hospImageurl").val(da.vo.filePath);
                    $('#simage').html("");
                    $('#simage').append('<img width="200" height="100" src="<%=request.getContextPath()%>/image.action?act=getUrlImage&url='+da.vo.filePath+'"/>')
                },
                error: function (data, status, e)//服务器响应失败处理函数
                {
                    layer.open({
                        skin: 'layui-layer-molv',
                        closeBtn: 0,
                        title: false,
                        content:e ,
                        anim: 5 ,
                        btn: ['关闭']
                    });
                }
            }
        )
        return false;
    }
    //保存的方法
    function saveTable() {
        if(iecs($("#hospName").val())){
            showMsg("单位名称不能为空！");
            $("#hospName").focus();
            return;
        }
        if(iecs($("#hospCode").val())){
            showMsg("医院编码不能为空！");
            $("#hospcode").focus();
            return;
        }
        if(iecs($("#hospLevel").val())){
            showMsg("级别不能为空！");
            $("#hospLevel").focus();
            return;
        }
       if(iecs($("#hospState input[pofield=hospState]:checked").val())){
           showMsg("请选择状态！");
            return;
        }
        if(iecs($("#hospTel").val())){
            showMsg("电话号码不能为空！");
            return;
        }
        vo = uiToData("form_vo",vo);
        vo["hospState"]=$("#hospState input[pofield=hospState]:checked").val();
        vo["hospPageStyle"]=$("#hospPageStyle input[pofield=hospPageStyle]:checked").val();
        if(level==1){
            vo["hospCityareaId"]=$("#provinceId").val();
        }else if(level==2){
            vo["hospCityareaId"]=$("#cityId").val();
        }else if(level==3){
            vo["hospCityareaId"]=$("#areaId").val();
        }else if(level==4){
            vo["hospCityareaId"]=$("#streetId").val();
        }
        vo["hospLevel"]=$("#hospLevel").val();
        if(iecs(vo.hospCityareaId)){
            showMsg("请选择区域！");
            return;
        }
        var method = "add";
        if($("#id").val() != ""){
            method = "modify";
        }
        doAjaxPost('<%=request.getContextPath()%>/apphosp.action?act='+method,{strJson : JSON.stringify(vo)},callDataSave);
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
