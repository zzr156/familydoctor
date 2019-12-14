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
    <title>团队管理主页</title>
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
                        <input type="hidden" id="teamId" pofield="teamId" name="teamId" value="${vo.id}" />
                        <input type="hidden" id="handle"  name="handle" value="${handle}" />
                        <div class="form-group">
                            <label class="col-sm-2 control-label">成员姓名：</label>
                            <div class="col-sm-3">
                                <select class="form-control" pofield="appMemName" id="appMemName">
                                    <option value="">--请选择--</option>
                                </select>
                            </div>
                            <label class="col-sm-2 control-label">工作类型：</label>
                            <div class="col-sm-3 ">
                                <select class="form-control" pofield="appWorkType" id="appWorkType">
                                    <option value="">--请选择--</option>
                                </select>
                            </div>
                            <div class="col-sm-2">
                                <a class="btn btn-primary system appteam_list" onclick="addFrom();">确认新增</a>
                                <a class="btn btn-info system appteam_list" onclick="backTable();">返回</a>
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
                                <th style="text-align: center;">团队名称</th>
                                <th style="text-align: center;">员工姓名</th>
                                <th style="text-align: center;">工作类型</th>
                                <th style="text-align: center;">机构名称</th>
                                <th style="text-align: center;">操作</th>
                            </tr>
                            </thead>
                            <tbody id="mem_list">

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
<%--<jsp:include page="drUser_ChangePeople.jsp" flush="false" />--%>
<jsp:include page="/open/mould/app/team/team_tmp.jsp" flush="false" />
<script type="text/javascript">
    function uiFromTmp(id, data,idx) {
        var ui = $($("#" + id + "").html());
        ui.data("vo", data);
        ui.find("td[pofield=memId] input[name=chckBox]").val(data.id);
        ui.find("td[pofield=num]").text(idx+1);
        ui.find("td[pofield=teamName]").text(data.memTeamName);
        ui.find("td[pofield=memName]").text(data.memDrName);
        ui.find("td[pofield=workType]").text(data.memWorkTypeName);
        ui.find("td[pofield=hospName]").text(data.hospName);
        $("#mem_list").append(ui);
        ui.fadeIn();
    }
</script>
<script type="text/javascript">

    var qvo={};
    var vo={};
    //var memTeamId=${vo.id};
    var memTeamId=GetQueryString("vo.id");
    function GetQueryString(name)
    {
        var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if(r!=null)return  unescape(r[2]); return null;
    }
    <!--list-->
    $(function(){
        findCmmInit();
        onInit();
        //findTable();
    })
    function findCmmInit(){
        //获取机构下是医生成员
        doAjaxPost('<%=request.getContextPath()%>/appteam.action?act=findCmmInit',{teamId:$("#teamId").val()},callDataToInit);
    }
    function callDataToInit(data){
        if(data.map!=null) {
            //工作类型
            if(data.map.workType!=null){
                $("#appWorkType").html("");
                var option = document.createElement('option');
                option.setAttribute("value","");
                option.innerText = "--请选择--";
                document.getElementById("appWorkType").append(option);
                $.each(data.map.workType,function(k,v){
                    dataUiCodeGroup("select","appWorkType",v.codeTitle, v.codeValue);
                })
            }
        }
        doAjaxPost('<%=request.getContextPath()%>/appteam.action?act=findDrCmm',{teamId:$("#teamId").val()},function(data){
            if(data.rows!=null){
                $("#appMemName").html("");
                var option = document.createElement('option');
                option.setAttribute("value","");
                option.innerText = "--请选择--";
                document.getElementById("appMemName").append(option);
                $.each(data.rows,function(k,v){
                    dataUiCodeGroup("select","appMemName",v.drName, v.id);
                })
            }
        });
    }
    //加载完成时，查询数据，记载到表格的方法
    function onInit() {
        findTable();
    }
    //查询
    function findTable(){
        qvo = uiToData("form_qvo",qvo);//界面值绑定到变量
        qvo["appMemTeamId"] = $("#teamId").val();
        doAjaxPost('<%=request.getContextPath()%>/appteam.action?act=listMemCmm',{qvoJson:JSON.stringify(qvo)},callDataToUi);
    }
    function callDataToUi(data){
        dataToUi(data.qvo,"form_qvo");//数据绑定到界面
        qvodesc("qvodesc");
        qvoPN(data.qvo);
        $("#mem_list").html("");
        if(data.rows != null){
            $.each(data.rows, function(k, v) {
                uiFromTmp("mem_tlist",v,k);
            });
        }
        $("#teamId").val(memTeamId);
    }
    //准备添加成员
    function forAddMemTable(){
        var url = '<%=request.getContextPath()%>/appteam.action?act=forAddMem';
        defualtHref(url);
    }
    //准备新增
    function forAddTable(){
        var url = '<%=request.getContextPath()%>/appteam.action?act=forAddOrEdit&handle=add';
        defualtHref(url);
    }
    //准备修改
    function forModifyTable(ui){
        var url = '<%=request.getContextPath()%>/appteam.action?act=forAddOrEdit&handle=modify&vo.id='+$(ui).data("vo").id;
        defualtHref(url);
    }
    //查看
    function forLookTable(ui){
        var url = '<%=request.getContextPath()%>/appteam.action?act=forAddOrEdit&handle=look&vo.id='+$(ui).data("vo").id;
        defualtHref(url);
    }

    //全选和全选取消
    function selectAll(){
        if($("#selectAll").prop("checked")){
            $.each($("#team_list").find("input[name=chckBox]"),function(k,v){
                $(this).prop("checked",true);
            });
        }else{
            $.each($("#team_list").find("input[name=chckBox]"),function(k,v){
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
    function user_del(){
        var userIds="";
        $.each($("#team_list").find("input[name=chckBox]"),function(k,v){
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

    function del(userId){
        $.post('<%=request.getContextPath()%>/appteam.action?act=delMemCmm',{'id':userId},function(data){
            var data=eval('(' + data + ')');
            if(data.msg=="true"){
                showMsg("删除成功!")
                findTable();
            } else {
                showMsg(data.msg);
            }
        });
    }
    function addFrom(){
        if(iecs($("#appMemName").val())){
            showMsg("请选择成员！");
            return false;
        }
        if(iecs($("#appWorkType").val())){
            showMsg("请选择工作类型！");
            return false;
        }
        vo = uiToData("form_vo",vo);
        var options=$("#appMemName option:selected");
        vo["memDrId"]=$("#appMemName").val();
        vo["memDrName"] = options.text();
        vo["memWorkType"]=$("#appWorkType").val();
        vo["memTeamid"]=$("#teamId").val();
        doAjaxPost('<%=request.getContextPath()%>/appteam.action?act=addMemCmm',{strJson : JSON.stringify(vo)},callDataSave);
    }
    function callDataSave(data){
        if(data.msg == 'true'){
            var url = '<%=request.getContextPath()%>/appteam.action?act=forAddMem&vo.id='+memTeamId;
            defualtHref(url);
        }else{
            showMsg(data.msg);
        }
    }
    //点击返回
    function backTable(){
        var url = '<%=request.getContextPath()%>/appteam.action?act=forList';
        defualtHref(url);
    }
</script>
</body>
</html>
