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
                        <div class="form-group">
                            <label class="col-sm-2 control-label">团队名称：</label>
                            <div class="col-sm-4">
                                <input type="text" id="appTeamId" pofield="appTeamId" class="form-control">
                            </div>
                            <label class="col-sm-2 control-label">家庭医生：</label>
                            <div class="col-sm-4 ">
                                <input type="text" id="appTeamDrName" pofield="appTeamDrName" class="form-control">
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
                            <label class="col-sm-2 control-label">有效标志：</label>
                            <div class="col-sm-4">
                                <select class="form-control" pofield="appTeamState" id="appTeamState">
                                    <option value="">--请选择--</option>
                                </select>
                            </div>
                            <label class="col-sm-2 control-label">显示条数：</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" pofield="pageSize"  id="pageSize"  value="15" >
                            </div>
                        </div>
                        <div class="text-center" style="border-top: 1px dashed #D7D7D7;padding-top: 10px;">
                            <a class="btn btn-primary system appteam_list" onclick="findTable();">查询</a>
                            <a class="btn btn-default system appteam_list" onclick="findReset();">重置</a>
                            <a class="btn btn-primary system appteam_inLoad" onclick="addfile();">导入数据</a>
                            <a class="btn btn-info system appteam_inLoad" ><input type="file" id="upExcel" name="upExcel" class="layui-btn layui-btn-normal"/></a>
                           <%-- <a class="btn btn-info system appteam_add" onclick="forAddMemTable();">添加成员</a>--%>
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
                    <a class="label label-danger pull-right system appteam_delete" onclick="user_del();">批量删除</a>
                    <a class="label label-success pull-right system appteam_add" onclick="forAddTable();">新增</a>
                </div>
                <div class="ibox-content tabble-c">
                    <div class="table-responsive">
                        <table class="table table-striped" id="drUserTable">
                            <thead>
                            <tr>
                                <th style="text-align:center;"><input type="checkbox" id="selectAll"style="width:22px;height:22px" onclick="selectAll()"/></th>
                                <th style="text-align: center;">序号</th>
                                <th style="text-align: center;">单位名称</th>
                                <th style="text-align: center;">团队编号</th>
                                <th style="text-align: center;">团队名称</th>
                                <th style="text-align: center;">家庭医生</th>
                               <%-- <th style="text-align: center;">医生代码</th>
                                <th style="text-align: center;">联系电话</th>--%>
                                <th style="text-align: center;">有效标志</th>
                                <th style="text-align: center;">创建时间</th>
                                <th style="text-align: center;">操作</th>
                            </tr>
                            </thead>
                            <tbody id="team_list">

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
        ui.find("td[pofield=teamId] input[name=chckBox]").val(data.id);
        ui.find("td[pofield=num]").text(idx+1);
        ui.find("td[pofield=teamHospName]").text(data.teamHospName);
        ui.find("td[pofield=teamCode]").text(data.teamCode);
        ui.find("td[pofield=teamName]").text(data.teamName);
        ui.find("td[pofield=teamDrName]").text(data.teamDrName);
       /* ui.find("td[pofield=teamDrCode]").text(data.teamDrCode);
        ui.find("td[pofield=teamTel]").text(data.teamTel);*/
        ui.find("td[pofield=teamStateName]").text(data.teamStateName);
        ui.find("td[pofield=teamCreateTime]").text(data.strTeamCreateTime);
        $("#team_list").append(ui);
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
    function findCmmInit() {
        <%--doAjaxPost('<%=request.getContextPath()%>/appteam.action?act=findDeptCmm',{},function(data){--%>
            <%--if(data.map!=null){--%>
                <%--if(data.map.hospName != null){--%>
                    <%--$("#appTeamHospName").val(data.map.hospName);--%>
                    <%--$("#appTeamHospName").attr("disabled","disabled");--%>
                <%--}--%>
            <%--}--%>
            doAjaxPost('<%=request.getContextPath()%>/appteam.action?act=findCmmInit',{},callDataToInit);
//        });
    }
    function callDataToInit(data){
        if(data.map != null){
            //团队appTeamId
            /*if(data.map.teams!=null){
                $("#appTeamId").html("");
                var option = document.createElement('option');
                option.setAttribute("value","");
                option.innerText = "--请选择--";
                document.getElementById("appTeamId").appendChild(option);
                $.each(data.map.teams, function(k, v) {
                    dataUiCodeGroup("select","appTeamId",v.teamName,v.id);
                });
            }*/
            //加载家庭医生
           /* if(data.map.dr != null){
                $("#appTeamDrId").html("");
                var option = document.createElement('option');
                option.setAttribute("value","");
                option.innerText = "--请选择--";
                document.getElementById("appTeamDrId").appendChild(option);
                $.each(data.map.dr, function(k, v) {
                    dataUiCodeGroup("select","appTeamDrId",v.drName,v.id);
                });
            }*/
            //有效标识
            if(data.map.teamState != null){
                $("#appTeamState").html("");
                var option = document.createElement('option');
                option.setAttribute("value","");
                option.innerText = "--请选择--";
                document.getElementById("appTeamState").appendChild(option);
                $.each(data.map.teamState, function(k, v) {
                    dataUiCodeGroup("select","appTeamState",v.codeTitle+" ",v.codeValue);
                });
            }
            if(data.map.province!=null){
                $.each(data.map.province,function(k,v){
                    dataUiCodeGroup("select","pro",v.areaSname,v.id);
                })
            }
            onInit();//回选
        }
    }
    //重置
    function findReset(){
        $('#appTeamId').val("");
        $('#appTeamDrId').val("");
        $('#appTeamState').val("");
        $('#appTeamHospName').val("");
        $('#drHospId').val("");
        $('#pro').val("");
        $('#city').val("");
        $('#area').val("");

    }
    //加载完成时，查询数据，记载到表格的方法
    function onInit() {
        findTable();
    }
    //查询
    function findTable(){
        qvo = uiToData("form_qvo",qvo);//界面值绑定到变量
        qvo["appTeamId"] = $("#appTeamId").val();
        qvo["appTeamDrId"] = $("#appTeamDrId").val();
        qvo["appTeamState"] = $("#appTeamState").val();
        doAjaxPost('<%=request.getContextPath()%>/appteam.action?act=list',{qvoJson:JSON.stringify(qvo)},callDataToUi);
    }
    function callDataToUi(data){
        dataToUi(data.qvo,"form_qvo");//数据绑定到界面
        qvodesc("qvodesc");
        qvoPN(data.qvo);
        $("#appTeamId").val(data.qvo.appTeamId);
        $("#appTeamDrId").val(data.qvo.appTeamDrId);
        $("#appTeamState").val(data.qvo.appTeamState);
        $("#team_list").html("");
        if(data.rows != null){
            $.each(data.rows, function(k, v) {
                uiFromTmp("tlist",v,k);
            });
        }
    }
    //准备添加成员
    function forAddMemTable(ui){
        var url = '<%=request.getContextPath()%>/appteam.action?act=forAddMem&handle=add&vo.id='+$(ui).data("vo").id;
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
        $.post('<%=request.getContextPath()%>/appteam.action?act=delete',{'id':userId},function(data){
            var data=eval('(' + data + ')');
            if(data.msg=="true"){
                showMsg("删除成功!")
                findTable();
            } else {
                showMsg(data.msg);
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
                url: 'appteam.action?act=importExcel', //用于文件上传的服务器端请求地址
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
