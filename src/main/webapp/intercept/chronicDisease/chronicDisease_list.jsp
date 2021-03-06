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
    <title>高血压、糖尿病标签管理</title>
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
                            <label class="col-sm-2 control-label">用户名：</label>
                            <div class="col-sm-4 ">
                                <input type="text" class="form-control" pofield="name"  id="name">
                            </div>
                            <label class="col-sm-2 control-label">身份证号码：</label>
                            <div class="col-sm-4 ">
                                <input type="text" class="form-control" pofield="idno"  id="idno">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">显示条数：</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" pofield="pageSize"  id="pageSize"  value="15" >
                            </div>
                        </div>

                        <div class="text-center" style="border-top: 1px dashed #D7D7D7;padding-top: 10px;">
                            <a class="btn btn-primary system appNCD_list" onclick="findTable();">查询</a>
                            <a class="btn btn-default system appNCD_list" onclick="findReset();">重置</a>
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
                <div class="ibox-content tabble-c">
                    <div class="table-responsive">
                        <table class="table table-striped" id="drUserTable">
                            <thead>
                            <tr>
                                <th style="text-align:center;"><input type="checkbox" id="selectAll"style="width:22px;height:22px" onclick="selectAll()"/></th>
                                <th style="text-align: center;">序号</th>
                                <th style="text-align: center;">居民姓名</th>
                                <th style="text-align: center;">居民身份证号</th>
                                <th style="text-align: center;">高血压标签</th>
                                <th style="text-align: center;">糖尿病标签</th>
                                <th style="text-align: center;">严重精神障碍标签</th>
                                <th style="text-align: center;">结核病标签</th>
                                <th style="text-align: center;">操作</th>
                            </tr>
                            </thead>
                            <tbody id="ncd_list">

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
<jsp:include page="/open/mould/chronicDisease/chronicDisease_tmp.jsp" flush="false" />
<script type="text/javascript">
    function uiFromTmp(id, data,idx) {
        var ui = $($("#" + id + "").html());
        ui.data("vo", data);
        ui.find("td[pofield=id] input[name=chckBox]").val(data.id);
        ui.find("td[pofield=num]").text(idx+1);
        ui.find("td[pofield=patientName]").text(data.patientName);
        ui.find("td[pofield=patientIdno]").text(data.patientIdno);
//        ui.find("td[pofield=patientAddr]").text(data.drAccount);
        ui.find("td[pofield=HBPLabel]").html("<span style='color:"+data.hbpLabelColor+" '>"+data.hbpLabel+"</span>");
        ui.find("td[pofield=DMLabel]").html("<span style='color:"+data.dmLabelColor+" '>"+data.dmLabel+"</span>");
        ui.find("td[pofield=PMPLabel]").html("<span style='color:"+data.pmPLabelColor+" '>"+data.pmPLabel+"</span>");
        ui.find("td[pofield=TBLabel]").html("<span style='color:"+data.tbLabelColor+" '>"+data.tbLabel+"</span>");
        $("#ncd_list").append(ui);
        ui.fadeIn();
    }

</script>
<script type="text/javascript">
    var qvo={};
    var vo={};
    <!--list-->
    $(function(){
        findTable();
    })
    //重置
    function findReset(){
        $('#name').val("");
        $("#idno").val("");
        $('#disType').val("");
        $("#pageSize").val(15)
    }
    //查询
    function findTable(){
        qvo = uiToData("form_qvo",qvo);//界面值绑定到变量
//        qvo["appDrState"]=$("#appDrState").val();
        doAjaxPost('<%=request.getContextPath()%>/appNCD.action?act=list',{qvoJson:JSON.stringify(qvo)},callDataToUi);
    }
    function callDataToUi(data){
        dataToUi(data.qvo,"form_qvo");//数据绑定到界面
        qvodesc("qvodesc");
        qvoPN(data.qvo);
        $("#ncd_list").html("");
        if(data.rows != null){
            $.each(data.rows, function(k, v) {
                uiFromTmp("tlist", v,k);
            });
        }
    }

    //准备修改
    function forModifyTable(ui){
        var url = '<%=request.getContextPath()%>/appNCD.action?act=forAddOrEdit&handle=modify&vo.id='+$(ui).data("vo").id;
        defualtHref(url);
    }
    //查看
    function forLookTable(ui){
        var url = '<%=request.getContextPath()%>/appNCD.action?act=forAddOrEdit&handle=look&vo.id='+$(ui).data("vo").signId;
        defualtHref(url);
    }

    //全选和全选取消
    function selectAll(){
        if($("#selectAll").prop("checked")){
            $.each($("#drUser_list").find("input[name=chckBox]"),function(k,v){
                $(this).prop("checked",true);
            });
        }else{
            $.each($("#drUser_list").find("input[name=chckBox]"),function(k,v){
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
        $.each($("#drUser_list").find("input[name=chckBox]"),function(k,v){
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
        $.post('<%=request.getContextPath()%>/appdr.action?act=delete',{'id':userId},function(data){
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
    var drJobTitle={
        // '':'',
        1.1:'主任医师',
        1.2:'副主任医师',
        1.3:'主治（主管）医师',
        1.4:'医师',
        1.5:'医士',
        2.1:'主任药师',
        2.2:'副主任药师',
        2.3:'主管药师',
        2.4:'药师',
        2.5:'药士',
        3.1:'主任护师',
        3.2:'副主任护师',
        3.3:'主管护师',
        3.4:'护师',
        3.5:'护士',
        4.1:'主任技师',
        4.2:'副主任技师',
        4.3:'主管技师',
        4.4:'技师',
        4.5:'技士',
        1:'健康管理师',
        2:'专科医生',
        3:'全科医生',
        4:'医技人员',
        5:'公卫医师',
        6:'社区护士'
    }
</script>
</body>
</html>
