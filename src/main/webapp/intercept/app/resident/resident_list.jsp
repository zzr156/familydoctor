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
    <title>居民管理主页</title>
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
                       <%-- <div class="form-group">
                            <label class="col-sm-2 control-label">医院名称：</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" pofield="appHospName"  id="appHospName">
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
                        </div>--%>
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
                <div class="ibox-title">
                    <a class="label label-danger pull-right system apprm_delete" onclick="hosp_del();">批量删除</a>
                    <a class="label label-success pull-right system apprm_add" onclick="forAddTable();">新增</a>
                </div>
                <div class="ibox-content tabble-c">
                    <div class="table-responsive">
                        <table class="table table-striped" id="residentTable">
                            <thead>
                            <tr>
                                <th style="text-align:center;"><input type="checkbox" id="selectAll"style="width:22px;height:22px" onclick="selectAll()"/></th>
                                <th>序号</th>
                                <th>分组名称</th>
                                <th>类型</th>
                                <th>创建者</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody id="resident_list">

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
<jsp:include page="/open/mould/app/resident/resident_tmp.jsp" flush="false" />
<script type="text/javascript">
    function uiFromTmp(id, data,idx) {
        var ui = $($("#" + id + "").html());
        ui.data("vo", data);
        ui.find("td[pofield=residentId] input[name=chckBox]").val(data.id);
        ui.find("td[pofield=num]").text(idx+1);
        ui.find("td[pofield=groupName]").text(data.rmGroupName);
        ui.find("td[pofield=typeName]").text(data.RmSignTypeName);
        ui.find("td[pofield=createName]").text(data.rmCreateName);
        $("#resident_list").append(ui);
        ui.fadeIn();
    }

</script>
<script type="text/javascript">
    var qvo={};
    var vo={};
    <!--list-->
    $(function(){
        onInit();

    })

    //重置
    function findReset(){
        $('#appHospName').val("");
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
        doAjaxPost('<%=request.getContextPath()%>/apprm.action?act=list',{qvoJson:JSON.stringify(qvo)},callDataToUi);
    }
    function callDataToUi(data){
        dataToUi(data.qvo,"form_qvo");//数据绑定到界面
        qvodesc("qvodesc");
        qvoPN(data.qvo);
        $("#resident_list").html("");
        $.each(data.rows, function(k, v) {
            uiFromTmp("tlist", v,k);
        });
    }

    //准备新增
    function forAddTable(){
        var url = '<%=request.getContextPath()%>/apprm.action?act=forAddOrEdit&handle=add';
        defualtHref(url);
    }
    //准备修改
    function forModifyTable(ui){
        var url = '<%=request.getContextPath()%>/apprm.action?act=forAddOrEdit&handle=modify&vo.id='+$(ui).data("vo").id;
        defualtHref(url);
    }
    //查看
    function forLookTable(ui){
        var url = '<%=request.getContextPath()%>/apprm.action?act=forAddOrEdit&handle=look&vo.id='+$(ui).data("vo").id;
        defualtHref(url);
    }

    //全选和全选取消
    function selectAll(){
        if($("#selectAll").prop("checked")){
            $.each($("#resident_list").find("input[name=chckBox]"),function(k,v){
                $(this).prop("checked",true);
            });
        }else{
            $.each($("#resident_list").find("input[name=chckBox]"),function(k,v){
                $(this).prop("checked",false);
            });
        }
    }
    //删除页面获取删除用户ID
    function hosp_del(){
        var hospIds="";
        $.each($("#resident_list").find("input[name=chckBox]"),function(k,v){
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
        $.post('<%=request.getContextPath()%>/apprm.action?act=delete',{'id':hospId},function(data){
            var data=eval('(' + data + ')');
            if(data.msg=="true"){
                showMsg("删除成功！");
                findTable();
            } else {
                showMsg(data.msg)

            }
        });
    }
</script>
</body>
</html>
