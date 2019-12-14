<!DOCTYPE html>
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
                <div class="ibox-content" id="form_qvo">
                    <form method="get" class="form-horizontal">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">医院名称：</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" pofield="appHospName"  id="appHospName">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">所属省：</label>
                            <div class="col-sm-4">
                                <select id="pro" pofield="pro"  class="form-control" onchange="findAddress('city',$('#pro option:selected').val())">
                                    <option value="">--请选择省--</option>
                                </select>
                            </div>
                            <label class="col-sm-2 control-label">所属市：</label>
                            <div class="col-sm-4">
                                <select id="city" pofield="city"  class="form-control" onchange="findAddress('area',$('#city option:selected').val())">
                                    <option value="">--请选择市--</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">所属区：</label>
                            <div class="col-sm-4">
                                <select id="area" pofield="area"  class="form-control" onchange="findAddress('drHospId',$('#area option:selected').val())">
                                    <option value="">--请选择区--</option>
                                </select>
                            </div>
                            <label class="col-sm-2 control-label">所属单位：</label>
                            <div class="col-sm-4">
                                <select id="drHospId"  pofield="drHospId" class="form-control">
                                    <option value="">--请选择所属单位--</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">显示条数：</label>
                            <div class="col-sm-10 ">
                                <input type="text" class="form-control" pofield="pageSize"  id="pageSize"  value="15" >
                            </div>
                        </div>
                        <div class="text-center" style="border-top: 1px dashed #D7D7D7;padding-top: 10px;">
                            <a class="btn btn-primary system apphosp_list" onclick="findTable();">查询</a>
                            <a class="btn btn-default system apphosp_list" onclick="findReset();">重置</a>
                            <a class="btn btn-primary system apphosp_inLoad" onclick="addfile();">导入数据</a>
                            <a class="btn btn-info system apphosp_inLoad" ><input type="file" id="upExcel" name="upExcel" class="layui-btn layui-btn-normal"/></a>
                        </div>
                        <input type="hidden" pofield="itemCount" id="itemCount" value="">
                        <input type="hidden" pofield="pageCount" id="pageCount" value="">
                        <input type="hidden" pofield="pageStartNo" id="pageStartNo" value="1">
                    </form>
                </div>
            </div>
        </div>
    </div>


    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <a class="label label-danger pull-right system apphosp_delete" onclick="hosp_del();">批量删除</a>
                    <a class="label label-success pull-right system apphosp_add" onclick="forAddTable();">新增</a>
                </div>
                <div class="ibox-content tabble-c">
                    <div class="table-responsive">
                        <table class="table table-striped" id="hospTable">
                            <thead>
                            <tr>
                                <th style="text-align:center;"><input type="checkbox" id="selectAll"style="width:22px;height:22px" onclick="selectAll()"/></th>
                                <th>序号</th>
                                <th>单位名称</th>
                                <th>单位编码</th>
                                <th>单位地址</th>
                                <th>单位电话</th>
                                <th>级别</th>
                                <th>状态</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody id="hosp_list">

                            </tbody>
                        </table>
                    </div>
                    <!--分页按钮-->
                    <div class="text-center" style="background: #fff; padding-top: 5px;">
                        <button id="previous" type="button" class="btn btn-sm" onclick="Previous();findTable();">前一页</button>
                        <span id="qvodesc">1/1每页显示:15条,共有1条</span>&nbsp;&nbsp;
                        <button id="next" type="button" class="btn btn-sm" onclick="Next();findTable();">下一页</button>
                        <input style="width: 40px" class="span2" id="gotext" type="text">
                        <button class="btn btn-sm" onclick="qvoGo();findTable();" type="button">Go!</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="/open/mould/app/hospDept/hospDept_tmp.jsp" flush="false" />
<script type="text/javascript">
    function uiFromTmp(id, data,idx) {
        var ui = $($("#" + id + "").html());
        ui.data("vo", data);
        ui.find("td[pofield=hospId] input[name=chckBox]").val(data.id);
        ui.find("td[pofield=num]").text(idx+1);
        ui.find("td[pofield=hospName]").text(data.hospName);
        ui.find("td[pofield=hospCode]").text(data.hospCode);
        ui.find("td[pofield=hospAddress]").text(data.hospAddress);
        ui.find("td[pofield=hospTel]").text(data.hospTel);
        ui.find("td[pofield=hospLevel]").text(data.hospLevelName);
        ui.find("td[pofield=hospState]").text(data.hospStateName);
        if(data.hospLevelType=="0"||data.hospLevelType=="1"||data.hospLevelType=="2"||data.hospLevelType=="3"||
            data.hospLevelType=="4"||data.hospLevelType=="5"||data.hospLevelType=="6"){
            ui.find("button[pofield=addHosp]").hide();
        }
        $("#hosp_list").append(ui);
        ui.fadeIn();
    }

</script>
<script type="text/javascript">
    var qvo={};
    var vo={};
    <!--list-->
    $(function(){
        findCmmInit();
    })


    function findCmmInit(){
        doAjaxPost('<%=request.getContextPath()%>/apphosp.action?act=findCmmInit',{},callDataToCmmInit);
    }
    function callDataToCmmInit(data){
        if(data.map!=null){
            if(data.map.province!=null){
                $.each(data.map.province,function(k,v){
                    dataUiCodeGroup("select","pro",v.areaSname,v.id);
                })
            }
        }
        onInit();
    }

    //重置
    function findReset(){
        $('#appHospName').val("");
        $('#drHospId').val("");
        $('#pro').val("");
        $('#city').val("");
        $('#area').val("");
    }
    //加载完成时，查询数据，记载到表格的方法
    function onInit() {
       // findState();//状态
        findTable();
    }

    //状态
    function findState(){
        doAjaxPost('<%=request.getContextPath()%>/code.action?act=jsonTreelist',{group:'workState'},callDataToState);
    }
    function callDataToState(data){
        $("#appDrState").html();
        $.each(data, function(k, v) {
            dataUiCodeGroup("select","appDrState",v.codeTitle,v.codeValue);
        });
    }

    //查询
    function findTable(){

        qvo = uiToData("form_qvo",qvo);//界面值绑定到变量
        doAjaxPost('<%=request.getContextPath()%>/apphosp.action?act=list',{qvoJson:JSON.stringify(qvo)},callDataToUi);
    }
    function callDataToUi(data){
        dataToUi(data.qvo,"form_qvo");//数据绑定到界面
        qvodesc("qvodesc");
        qvoPN(data.qvo);
        $("#hosp_list").html("");
        $.each(data.rows, function(k, v) {
            uiFromTmp("tlist", v,k);
        });
    }

    //准备新增
    function forAddTable(){
        var url = '<%=request.getContextPath()%>/apphosp.action?act=forAddOrEdit&handle=add';
        defualtHref(url);
    }
    //准备修改
    function forModifyTable(ui){
        var url = '<%=request.getContextPath()%>/apphosp.action?act=forAddOrEdit&handle=modify&vo.id='+$(ui).data("vo").id;
        defualtHref(url);
    }
    //查看
    function forLookTable(ui){
        var url = '<%=request.getContextPath()%>/apphosp.action?act=forAddOrEdit&handle=look&vo.id='+$(ui).data("vo").id;
        defualtHref(url);
    }
    //准备设置提醒天数
    function forSetTable(ui){
        var url = '<%=request.getContextPath()%>/apphosp.action?act=forExtend&handle=look&vo.id='+$(ui).data("vo").id;
        defualtHref(url);
    }
    //配制签约服务团队
    function signTeam(ui){
        var url = '<%=request.getContextPath()%>/apphosp.action?act=signTeamCmm&handle=modify&vo.id='+$(ui).data("vo").id;
        defualtHref(url);
    }
    //添加上级医院
    function forAddUpHosp(ui){
        var url = '<%=request.getContextPath()%>/apphosp.action?act=addUpHosp&handle=add&vo.id='+$(ui).data("vo").id;
        defualtHref(url);
    }
    //上传公章图片
    function forUploadImage(ui){
        var url = '<%=request.getContextPath()%>/apphosp.action?act=addUploadImage&handle=add&vo.id='+$(ui).data("vo").id;
        defualtHref(url);
    }

    //全选和全选取消
    function selectAll(){
        if($("#selectAll").prop("checked")){
            $.each($("#hosp_list").find("input[name=chckBox]"),function(k,v){
                $(this).prop("checked",true);
            });
        }else{
            $.each($("#hosp_list").find("input[name=chckBox]"),function(k,v){
                $(this).prop("checked",false);
            });
        }
    }
    //删除页面获取删除用户ID
    function hosp_del(){
        var hospIds="";
        $.each($("#hosp_list").find("input[name=chckBox]"),function(k,v){
            if($(this).prop("checked")){
                if(hospIds!=""){
                    hospIds+=";";
                }
                hospIds+=$(this).val();
            }
        });
        if(hospIds==""){
           showMsg("请选择需要删除的用户!");
        }else{
            layer.confirm('确认删除数据?', {
                btn: ['确定','取消']
            }, function(){
                del(hospIds);
            });
        }
    }

    //删除
    function delForm(ui){
        layer.confirm('确认删除数据?', {
            btn: ['确定','取消']
        }, function(){
            var hospId = $(ui).data("vo").id;
            del(hospId);
        });
    }

    function del(hospId){
        $.post('<%=request.getContextPath()%>/apphosp.action?act=delete',{'id':hospId},function(data){
            var data=eval('(' + data + ')');
            if(data.msg=="true"){
                showMsg("删除成功！");
                findTable();
            } else {
                showMsg(data.msg)

            }
        });
    }
    var pro="";
    var city="";
    var area="";
    var street="";
    function findAddress(id,pid){
        doAjaxPost('<%=request.getContextPath()%>/address.action?act=findCmmByPid',{pid : pid,id:id},function(data){
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
            if(id=="drHospId"){
                option.innerText = "--请选择医院--";
            }
            document.getElementById(id).appendChild(option);
            if(id == "drHospId"){
                $.each(data,function(k,v){
                    dataUiCodeGroup("select",id,v.hospName,v.id);
                })
            }else{
                $.each(data,function(k,v){
                    dataUiCodeGroup("select",id,v.areaSname,v.id);
                })
            }
        });
    }
    function addfile(){
        var file=$("#upExcel").val();
        var ext = file.slice(file.lastIndexOf(".")+1).toLowerCase();
        if(ext == undefined || (ext != 'xls')){
            layer.open({
                skin: 'layui-layer-molv',
                closeBtn: 0,
                title: false,
                content:'上传EXCEL格式不正确（EXCEL格式应该为xls）!' ,
                anim: 5 ,
                btn: ['关闭']
            });
            return false;
        }
        $.ajaxFileUpload({
                url: 'apphosp.action?act=importExcel', //用于文件上传的服务器端请求地址
                secureuri: false, //是否需要安全协议，一般设置为false
                fileElementId: 'upExcel', //文件上传域的ID
                dataType: 'JSON', //返回值类型 一般设置为json
                success: function (data, status)  //服务器成功响应处理函数
                {
                    var da=JSON.parse(data);
//                    console.log(da);
                    layer.msg(da.msg);
                },
                error: function (data, status, e)//服务器响应失败处理函数
                {
                    layer.msg(e);
                }
            }
        )
        return false;
    }
</script>
</body>
</html>
