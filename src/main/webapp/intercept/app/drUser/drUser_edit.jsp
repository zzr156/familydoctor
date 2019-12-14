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
    <title>医生管理主页</title>
    <%@ include file="/open/commonjs/tldHtml.jsp"%>
    <%@ include file="/open/commonjs/roleson.jsp"%>
    <%@ include file="/open/commonjs/tldLayui.jsp"%>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5 id="userAddTit"></h5>
                    <a class="label label-primary pull-right system appdr_list"  onclick="backTable();">返回</a>
                </div>
                <div class="ibox-content">
                    <form role="form" id="form_vo"  class="form-horizontal">
                        <input type="hidden" id="id" pofield="id" name="id" value="${vo.id}" />
                        <input type="hidden" id="handle"  name="handle" value="${handle}" />
                        <%--<div class="form-group" id="areaa">
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>区域：</label>
                            <div class="col-sm-2">
                                <select id="pro" class="form-control" onchange="findAddress('city',$('#pro option:selected').val())"><option value="">--请选择省--</option></select>
                            </div>
                            <div class="col-sm-2">
                                <select id="city" class="form-control" onchange="findAddress('area',$('#city option:selected').val())"><option value="">--请选择市--</option></select>
                            </div>
                            <div class="col-sm-2">
                                <select id="area" class="form-control" onchange="findAddress('street',$('#area option:selected').val())"><option value="">--请选择区--</option></select>
                            </div>
                            <div class="col-sm-2">
                                <select id="street" class="form-control"><option value="">--请选择街道--</option></select>
                            </div>
                            &lt;%&ndash;<div class="col-sm-2">
                                <select id="commt" class="form-control" ><option value="">--请选择社区--</option></select>
                            </div>&ndash;%&gt;
                            <div class="col-sm-1">
                                <a class="btn btn-primary system appdr_list" onclick="findTable();">查询</a>
                            </div>
                        </div>--%>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>账号：</label>
                            <div class="col-sm-4">
                                <input id="drAccount" name="drAccount" pofield="drAccount" type="text" class="form-control">
                            </div>
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>用户名：</label>
                            <div class="col-sm-4">
                                <input id="drName" name="drName" pofield="drName" type="text" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>密码：</label>
                            <div class="col-sm-4">
                                <input id="drPwd" name="drPwd" pofield="drPwd" type="password" class="form-control">
                            </div>
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>性别：</label>
                            <div class="col-sm-4" id="drGender">

                            </div>
                        </div>

                        <div class="form-group" id="hosp" >
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span> 所属医院：</label>
                            <div class="col-sm-4">
                                <div class="input-group">
                                    <input type="text" class="form-control" id="drHospName" name="drHospName" pofield="drHospName" type="text" readonly="readonly">
                                    <input type="hidden" pofield="drHospId" id="drHospId"  />
                                    <span class="input-group-btn">
                                        <button type="button" class="btn btn-primary"  id="clickHospBtn" data-toggle="modal" data-target="#myModalHospDept" onclick="ChangeDateToUser()">选择医院</button>
                                    </span>
                                </div>
                            </div>
                            <label class="col-sm-2 control-label">科室：</label>
                            <div class="col-sm-4">
                                <select id="drDepartmentId" class="form-control"><option value="">--请选择科室--</option></select></td>
                            </div>
                        </div>
                        <div class="form-group" id="hosp" >
                            <label class="col-sm-2 control-label">是否是Web用户：</label>
                            <div class="col-sm-4" id="drWebState">

                            </div>
                            <label class="col-sm-2 control-label">是否接收转诊消息：</label>
                            <div class="col-sm-4" id="drReferralState">

                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>编号：</label>
                            <div class="col-sm-4">
                                <input type="text" id="drCode" name="drCode" pofield="drCode" class="form-control">
                            </div>
                            <label class="col-sm-2 control-label">角色：</label>
                            <div class="col-sm-4" id="drRole">

                            </div>
                        </div>
                        <div class="form-group">

                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>电话：</label>
                            <div class="col-sm-4">
                                <input type="text" id="drTel" name="drTel" pofield="drTel" class="form-control">
                            </div>
                            <label class="col-sm-2 control-label">医生工作职称：</label>
                            <div class="col-sm-4">
                                <%--<input id="drJobTitle" name="drJobTitle" pofield="drJobTitle" type="text" class="form-control">--%>
                                <select id="drJobTitle"  pofield="drJobTitle" class="form-control">
                                    <option value="">--选择职位--</option>
                                    <option value="1.1">主任医师</option>
                                    <option value="1.2">副主任医师</option>
                                    <option value="1.3">主治（主管）医师</option>
                                    <option value="1.4">医师</option>
                                    <option value="1.5">医士</option>
                                    <option value="2.1">主任药师</option>
                                    <option value="2.2">副主任药师</option>
                                    <option value="2.3">主管药师</option>
                                    <option value="2.4">药师</option>
                                    <option value="2.5">药士</option>
                                    <option value="3.1">主任护师</option>
                                    <option value="3.2">副主任护师</option>
                                    <option value="3.3">主管护师</option>
                                    <option value="3.4">护师</option>
                                    <option value="3.5">护士</option>
                                    <option value="4.1">主任技师</option>
                                    <option value="4.2">副主任技师</option>
                                    <option value="4.3">主管技师</option>
                                    <option value="4.4">技师</option>
                                    <option value="4.5">技士</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">简介：</label>
                            <div class="col-sm-4">
                                <textarea id="drIntro" name="drIntro" pofield="drIntro" class="form-control"></textarea>
                            </div>
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>擅长：</label>
                            <div class="col-sm-4">
                                <textarea id="drGood" name="drGood" pofield="drGood" class="form-control"></textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">医生类别：</label>
                            <div class="col-sm-4" id="drType">

                            </div>
                            <label class="col-sm-2 control-label">医生状态：</label>
                            <div class="col-sm-4" id="drState">

                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">医生头像：</label>
                            <div class="col-sm-4">
                                <input  type="file" id="image" name="image" onchange="addfile();"/>
                            </div>
                            <label class="col-sm-2 control-label">当前头像：</label>
                            <div class="col-sm-4">
                                <div id="simage"></div>
                                <input type="hidden" id="drImageurl" name="drImageurl" pofield="drImageurl" />
                                <input type="hidden" id="drImageName" name="drImageName" pofield="drImageName">
                            </div>
                        </div>
                        <div class="text-center btnBar">
                            <a id="handleMethod" class="btn btn-primary system appdr_add" onclick="saveTable();">保存</a>
                        </div>
                    </form>
                </div>

            </div>
        </div>
    </div>
</div>
<jsp:include page="drUser_ChangePeople.jsp" flush="false" />
<script type="text/javascript">
    var vo={};
    //页面加载完成时启动的方法
    $(document).ready(function() {
        if($("#handle").val()=="look"||$("#handle").val()=="modify"){
            $("#areaa").hide();
            $("#hosp").show();
        }
       // findDept();//部门 树形
        findProvince();
        findCmmInit();//初始化
    });
    //查找省
    function findProvince(){
        var pid=null;
        doAjaxPost('<%=request.getContextPath()%>/address.action?act=findCmmByPid',{pid:pid},function (data) {
            if(data!=null){
                $("#pro").html("");
                var option = document.createElement('option');
                option.setAttribute("value","");
                option.innerText = "--请选择省--";
                document.getElementById("pro").appendChild(option);
                $.each(data, function (k, v) {
                    dataUiCodeGroup("select", "pro", v.areaSname, v.id);
                });
            }
        });
    }
    function findAddress(id,pid,value){
        doAjaxPost('<%=request.getContextPath()%>/address.action?act=findCmmByPid',{pid : pid},function(data){
            $("#"+id).html("");
            var option = document.createElement('option');
            option.setAttribute("value","");
            if(id=='city'){
                option.innerText = "--请选择市--";
            }
            if(id=='area'){
                option.innerText = "--请选择区--";
            }
            if(id=='street'){
                option.innerText = "--请选择街道--";
            }
            if(id=="commt"){
                option.innerText = "--请选择社区--";
            }
            document.getElementById(id).appendChild(option);
            $.each(data,function(k,v){
                dataUiCodeGroup("select",id,v.areaSname,v.id);
            })
            if(value!=""){
                $("#"+id).val(value);
            }
        });
    }
    function findTable(){
//        if(iecs($("#street").val())){
//            showMsg("请选择街道");
//            return;
//        }
        if(iecs($("#street").val())){
            if(iecs($("#area").val())){
                if(iecs($("#city").val())){
                    $("#pro").show();
                    findDept($("#pro").val());
                }else{
                    $("#hosp").show();
                    findDept($("#city").val());
                }
            }else{
                $("#hosp").show();
                findDept($("#area").val());
            }
        }else{
            $("#hosp").show();
            findDept($("#street").val());
        }
//        $("#hosp").show();
//        findDept($("#street").val());
    }
    //统一初始化
    function findCmmInit(){
        doAjaxPost('<%=request.getContextPath()%>/appdr.action?act=findCmmInit',{},callDataToInit);
    }
    function callDataToInit(data){
        if(data.map != null){
            //性别
            if(data.map.drGender != null){
                $("#drGender").html("");
                $.each(data.map.drGender, function(k, v) {
                    dataUiCodeGroup("radio","drGender",v.codeTitle+" ",v.codeValue);
                });
                if($("#handle").val() == "add"){
                    $("input[name='drGender'][value=1]").attr("checked",true);
                }
            }
            if(data.map.sf!=null){
                $("#drWebState").html("");
                $.each(data.map.sf, function(k, v) {
                    dataUiCodeGroup("radio","drWebState",v.codeTitle+" ",v.codeValue);
                });
                if($("#handle").val() == "add"){
                    $("input[name='drWebState'][value=0]").attr("checked",true);
                }
                $("#drReferralState").html("");
                $.each(data.map.sf, function(k, v) {
                    dataUiCodeGroup("radio","drReferralState",v.codeTitle+" ",v.codeValue);
                });
                if($("#handle").val() == "add"){
                    $("input[name='drReferralState'][value=0]").attr("checked",true);
                }
            }
            //状态
            if(data.map.drState != null){
                $("#drState").html("");
                $.each(data.map.drState, function(k, v) {
                    dataUiCodeGroup("radio","drState",v.codeTitle+" ",v.codeValue);
                });
                if($("#handle").val() == "add"){
                    $("input[name='drState'][value=1]").attr("checked",true);
                }
            }
            //类别
            if(data.map.drType != null){
                $("#drType").html("");
                $.each(data.map.drType, function(k, v) {
                    dataUiCodeGroup("radio","drType",v.codeTitle+" ",v.codeValue);
                });
                if($("#handle").val() == "add"){
                    $("input[name='drType'][value=1]").attr("checked",true);
                }
            }
            if(data.map.roles != null){
                $("#drRole").html("");
                $.each(data.map.roles,function(k,v){
                    dataUiCodeGroup("checkbox","drRole",v.roleName+" ",v.roleValue);
                })
                if($("#handle").val()=="add"){
                    $("input[name='drRole'][value=9]").attr("checked",true);
                }
            }
            onInit();//回选
        }
    }

    //加载完成时，查询数据，记载到表格的方法
    function onInit() {
        if($("#id").val()!=""){
            doAjaxPost('<%=request.getContextPath()%>/appdr.action?act=jsonByOne',{id:$("#id").val()},callDataToDrUser);
        }
        if($("#handle").val() == "look"){
            $("#handleMethod").hide();
            $("#userAddTit").text("查看医生信息");
        }else if($("#handle").val() == "modify"){
            $("#handleMethod").text("修改");
            $("#userAddTit").text("修改医生信息");
            if(drRole.indexOf("1") == -1 && drRole.indexOf("2") == -1
                && drRole.indexOf("3") == -1 ){
                if(drRole.indexOf("4") != -1 || drRole.indexOf("9") != -1) {
                    $("#drAccount").attr("disabled", "disabled");
                    $("#drName").attr("disabled", "disabled");
                    $('#clickHospBtn').attr("disabled", "disabled");
                    $("input[name='drRole']").attr("disabled", "disabled");
                }
            }
        }else if($("#handle").val() == "add"){
            $("#userAddTit").text("新增医生信息");
            if(drRole.indexOf("1") == -1 && drRole.indexOf("2") == -1
                && drRole.indexOf("3") == -1 ){
                if(drRole.indexOf("4") != -1 || drRole.indexOf("9") != -1){
                $('#clickHospBtn').attr("disabled","disabled");
                $("input[name='drRole']").attr("disabled","disabled");
                $('#drHospId').val(orgid);
                $('#drHospName').val(HospName);
                $("#drPwd").val("");
                $("#drName").val("");
                findDepartment(orgid,null);
                }
            }
        }
    }

    function callDataToDrUser(data){
        dataToUi(data,"form_vo");
        if(data.drImageurl != "" && data.drImageurl != null){
            $('#simage').append('<img width="200" height="100" src="<%=request.getContextPath()%>/image.action?act=getUrlImage&url='+data.drImageurl+'"/>')
        }
        var drHospId = $("#drHospId").val();
        if(drHospId != null && drHospId != ""){
            $("#drHospId").val(drHospId);
        }
        var t = $($("#hosp_tlist").html());
        var d=document.createElement("input");
        d.setAttribute("type","radio");
        d.setAttribute("name","changeHospDept");
        d.setAttribute("id","changeHospDept");
        d.setAttribute("value",data.drHospId+";;;"+data.drHospName);
        t.find("td[pofield=sname]").append(d);
        t.find("td[pofield=sname]").append(data.drHospName);
        $("#tbHospDept").append(t);
        findDepartment(data.drHospId,data.drDepartmentId);
        t.fadeIn();


    }

    function ChangeDateToUser(){
//        var option = document.createElement("option");
//        option.setAttribute("value","");
//        option.innerText = "--请选择街道--";
//        document.getElementById(id).appendChild(option);
        if($("#drHospId").val() != null && $("#drHospId").val() != ""){
//            var idName = $("#drHospId").val()+";;;"+$("#drHospName").val();
            $("input[name='changeHospDept']").each(function(){
                var value = $(this).attr("value").split(";;;")[0];
                if($("#drHospId").val()==value){
                    $(this).attr("checked", true);
                }
            });
        }
    }

    //点击返回
    function backTable(){
        history.go(-1);
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
                    $("#drImageurl").val(da.vo.filePath);
                    $("#drImageName").val(da.vo.fileName);
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
    //保存的方法
    function saveTable() {
        if(iecs($("#drAccount").val())){
            showMsg("账号不能为空！");
            $("#drAccount").focus();
            return;
        }
        if(iecs($("#drName").val())){
            showMsg("用户名不能为空！");
            $("#drName").focus();
            return;
        }
        if(iecs($("#drPwd").val())){
            showMsg("密码不能为空！");
            $("#drPwd").focus();
            return;
        }
        if(iecs($("#drHospName").val())){
            showMsg("请选择医院！")
            return;
        }
        if(iecs($("#drTel").val())){
            showMsg("手机号码不能为空！");
            $("#drTel").focus();
            return;
        }
        if(iecs($("#drCode").val())){
            showMsg("编号不能为空！");
            $("#drCode").focus();
            return;
        }
        vo = uiToData("form_vo",vo);
        var role = "";
        var obj=document.getElementsByName('drRole');
        for(var i=0; i<obj.length; i++){
            if(obj[i].checked) role+=obj[i].value+';';
        }
        vo["drRole"]=role;
        vo["drDepartmentId"] = $("#drDepartmentId").val();
        var method = "add";
        if($("#id").val() != ""){
            method = "modify";
        };
        doAjaxPost('<%=request.getContextPath()%>/appdr.action?act='+method,{strJson : JSON.stringify(vo)},callDataSave);
    }

    function callDataSave(data){
        if(data.msg == 'true'){
            var url = '<%=request.getContextPath()%>/appdr.action?act=forList';
            defualtHref(url);
        }else{
            showMsg(data.msg);
        }
    }
    /**
     * 查询科室
     * @param hospId
     */
    function findDepartment(hospId,value){
        doAjaxPost('<%=request.getContextPath()%>/appdr.action?act=findCmmDepartment',{hospId :hospId},function(data){
            $("#drDepartmentId").html("");
            var option = document.createElement('option');
            option.setAttribute("value","");
            option.innerText = "--请选择科室--";
            document.getElementById("drDepartmentId").appendChild(option);
//            alert(JSON.stringify(data));
            $.each(data.rows,function(k,v){
                dataUiCodeGroup("select","drDepartmentId",v.deptName,v.id);
            })
            if(value!=null){
                $("#drDepartmentId").val(value);
            }
        });
    }
</script>
</body>
</html>
