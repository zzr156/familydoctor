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
                        <input type="hidden" id="upId" pofield="upId" name="upId" value="${vo.id}" />
                        <input type="hidden" id="handle"  name="handle" value="${handle}" />
                        <div class="form-group">
                            <label class="col-sm-2 control-label">上级医院：</label>
                            <div class="col-sm-3">
                                <select class="form-control" pofield="upHospName" id="upHospName">
                                    <option value="">--请选择--</option>
                                </select>
                            </div>
                            <div class="col-sm-4">
                                <a class="btn btn-primary system apphosp_list" onclick="addFrom();">确认新增</a>
                                <a class="btn btn-info system apphosp_list" onclick="backTable();">返回</a>
                                <a class="btn btn-info system apphosp_list" onclick="upHosp_del();">批量删除</a>
                            </div>
                        </div>
                        <input type="hidden" pofield="itemCount" id="itemCount" value="">
                        <input type="hidden" pofield="pageCount" id="pageCount" value="">
                        <input type="hidden" pofield="pageStartNo" id="pageStartNo" value="1">
                        <input type="hidden" pofield="pageSize" id="pageSize" value="20">
                    </form>
                </div>
            </div>
        </div>
    </div>


    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content tabble-c">
                    <div class="table-responsive">
                        <table class="table table-striped" id="drUserTable">
                            <thead>
                            <tr>
                                <th style="text-align:center;"><input type="checkbox" id="selectAll"style="width:22px;height:22px" onclick="selectAll()"/></th>
                                <th style="text-align: center;">序号</th>
                                <th style="text-align: center;">本机构名称</th>
                                <th style="text-align: center;">上级机构名称</th>
                                <th style="text-align: center;">操作</th>
                            </tr>
                            </thead>
                            <tbody id="hosp_listt">

                            </tbody>
                        </table>
                    </div>
                    <!--分页按钮-->
                    <div class="text-center" style="background: #fff; padding-top: 5px;">
                        <button type="button" class="btn btn-sm" onclick="Previous();findTable();">前一页</button>
                        <span id="qvodesc">1/1每页显示:15条,共有1条</span>&nbsp;&nbsp;
                        <button type="button" class="btn btn-sm" onclick="Next();findTable();">下一页</button>
                        <input style="width: 40px" class="span2" id="gotext" type="text">
                        <button class="btn btn-sm" onclick="qvoGo();findTable();" type="button">Go!</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%--<jsp:include page="drUser_ChangePeople.jsp" flush="false" />--%>
<jsp:include page="/open/mould/app/hospDept/hospDept_tmp.jsp" flush="false" />
<script type="text/javascript">
    function uiFromTmp(id, data,idx) {
        var ui = $($("#" + id + "").html());
        ui.data("vo", data);
        ui.find("td[pofield=uupId] input[name=chckBox]").val(data.id);
        ui.find("td[pofield=num]").text(idx+1);
        ui.find("td[pofield=upName]").text(data.hospName);
        ui.find("td[pofield=upHospName]").text(data.upHospName);
        if(data.upState=="1"){
            ui.find("button[pofield=state]").text("禁用");
        }else{
            ui.find("button[pofield=state]").text("启用");
        }
        $("#hosp_listt").append(ui);
        ui.fadeIn();
    }
</script>
<script type="text/javascript">
    var qvo={};
    var vo={};
    var upId=GetQueryString("vo.id");
    function GetQueryString(name)
    {
        var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if(r!=null)return  unescape(r[2]); return null;
    }
    $(function(){
        findCmmInit();
    })
    function findCmmInit(){
        doAjaxPost('<%=request.getContextPath()%>/apphosp.action?act=findCmmUpHosp',{hospId:$("#upId").val()},callDataToInit);
    }
    function callDataToInit(data){
        if(data.rows!=null){
            $("#upHospName").html("");
            var option = document.createElement('option');
            option.setAttribute("value","");
            option.innerText = "--请选择--";
            document.getElementById("upHospName").append(option);
            $.each(data.rows,function(k,v){
                dataUiCodeGroup("select","upHospName",v.hospName, v.id);
            })
        }
        onInit();
    }
    //加载完成时，查询数据，记载到表格的方法
    function onInit() {
        findTable();
    }
    //查询
    function findTable(){
//        alert($("#upId").val());
        qvo = uiToData("form_qvo",qvo);//界面值绑定到变量
        qvo["drHospId"]=$("#upId").val();

        doAjaxPost('<%=request.getContextPath()%>/apphosp.action?act=findUpHosp',{qvoJson:JSON.stringify(qvo)},callDataToUi);
    }
    function callDataToUi(data){
        dataToUi(data.qvo,"form_qvo");//数据绑定到界面
        qvodesc("qvodesc");
        $("#hosp_listt").html("");
        if(data.rows != null){
            $.each(data.rows, function(k, v) {
                uiFromTmp("ttlist",v,k);
            });
        }
        $("#upId").val(upId);
    }
    //全选和全选取消
    function selectAll(){
        if($("#selectAll").prop("checked")){
            $.each($("#hosp_listt").find("input[name=chckBox]"),function(k,v){
                $(this).prop("checked",true);
            });
        }else{
            $.each($("#hosp_listt").find("input[name=chckBox]"),function(k,v){
                $(this).prop("checked",false);
            });
        }
    }

    function ChangeDateToUser(){
        if($("#drHospId").val() != null && $("#drHospId").val() != ""){
            var idName = $("#drHospId").val()+";;;"+$("#drHospName").val();
            $("input[name='changeHospDept']").each(function(){
                if(idName.indexOf($(this).attr("value"))!= -1){
                    $(this).attr("checked", true);
                }
            });
        }
    }
    //删除页面获取删除用户ID
    function upHosp_del(){
        var userIds="";
        $.each($("#hosp_listt").find("input[name=chckBox]"),function(k,v){
            if($(this).prop("checked")){
                if(userIds!=""){
                    userIds+=";";
                }
                userIds+=$(this).val();
            }
        });
        if(userIds==""){
            showMsg("请选择需要删除的用户!");
        }else{
            layer.confirm('确认删除数据?', {
                btn: ['确定','取消']
            }, function(){
                del(userIds);
            });
        }
    }

    //删除
    function delForm(ui){
        layer.confirm('确认删除数据?', {
            btn: ['确定','取消']
        }, function(){
            var userId = $(ui).data("vo").id;
            del(userId);
        });
    }
    function forSetTable(ui){
        var id = $(ui).data("vo").id;
        doAjaxPost('<%=request.getContextPath()%>/apphosp.action?act=chageState',{id:id},changeState);
    }
    function changeState(data){
//        var data=eval('(' + date + ')');
        if(data.msg=="true"){
            showMsg("修改成功");
            findTable();
        }else{
            showMsg(data.msg);
        }
    }

    function del(userId){
        $.post('<%=request.getContextPath()%>/apphosp.action?act=deleteUpHosp',{'id':userId},function(data){
            var data=eval('(' + data + ')');
            if(data.msg=="true"){
                showMsg("删除成功!")
                findCmmInit();
            } else {
                showMsg(data.msg);
            }
        });
    }
    function addFrom(){
        if(iecs($("#upHospName").val())){
            showMsg("请选择医院！");
            return false;
        }
        vo = uiToData("form_vo",vo);
        var options=$("#upHospName option:selected");
        vo["upHospId"]=$("#upHospName").val();
        vo["upId"]=$("#upId").val();
//        alert(JSON.stringify(vo));
        doAjaxPost('<%=request.getContextPath()%>/apphosp.action?act=addUpHospCmm',{strJson : JSON.stringify(vo)},callDataSave);
    }
    function callDataSave(data){
        if(data.msg == 'true'){
            <%--var url = '<%=request.getContextPath()%>/apphosp.action?act=forAddMem&vo.id='+memTeamId;--%>
//            defualtHref(url);
            findCmmInit();
        }else{
            showMsg(data.msg);
        }
    }
    //点击返回
    function backTable(){
        var url = '<%=request.getContextPath()%>/apphosp.action?act=forList';
        defualtHref(url);
    }
</script>
</body>
</html>
