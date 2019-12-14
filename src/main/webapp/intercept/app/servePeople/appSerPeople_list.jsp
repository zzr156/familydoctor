<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>服务对象管理</title>
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
                            <label class="col-sm-2 control-label">服务对象：</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" pofield="serName"  id="serName">
                            </div>
                            <label class="col-sm-2 control-label">显示条数：</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" pofield="pageSize"  id="pageSize"  value="15" >
                            </div>
                        </div>
                        <div class="text-center" style="border-top: 1px dashed #D7D7D7;padding-top: 10px;">
                            <a class="btn btn-primary system appSerPeople_list" onclick="findTable();">查询</a>
                            <a class="btn btn-default system appSerPeople_list" onclick="findReset();">重置</a>
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
                    <a class="label label-danger pull-right system appSerPeople_delete" onclick="approle_del();">批量删除</a>
                    <a class="label label-success pull-right system appSerPeople_add" onclick="forAddTable();">新增</a>
                </div>
                <div class="ibox-content tabble-c">
                    <div class="table-responsive">
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th style="text-align:center;"><input type="checkbox" id="selectAll"style="width:22px;height:22px" onclick="selectAll()"/></th>
                                <th style="text-align: center;">序号</th>
                                <th style="text-align: center;">服务对象编号</th>
                                <th style="text-align: center;">服务对象名称</th>
                                <th style="text-align: center;">是否基本公共卫生服务对象</th>
                                <th style="text-align: center;">创建时间</th>
                                <th style="text-align: center;">操作</th>
                            </tr>
                            </thead>
                            <tbody id="appSerPeople_list">

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
<jsp:include page="appSerPeople_change.jsp" flush="false" />
<jsp:include page="/open/mould/app/servePeople/appServePeople_tmp.jsp" flush="false" />
<script type="text/javascript">
    function uiFromTmp(id, data,idx) {
        var ui = $($("#" + id + "").html());
        ui.data("vo", data);
        ui.find("td[pofield=oId] input[name=chckBox]").val(data.id);
        ui.find("td[pofield=num]").text(idx+1);
        ui.find("td[pofield=serValue]").text(data.seroValue);
        ui.find("td[pofield=serName]").text(data.seroName);
        ui.find("td[pofield=serState]").text(data.stateName);
        ui.find("td[pofield=serCreateTime]").text(data.strCreateTime);
        if(data.seroOpenState=="1"){
            ui.find("button[pofield=open]").html("<i class='fa fa-search'></i>关闭");
        }else{
            ui.find("button[pofield=open]").html("<i class='fa fa-search'></i>开启");
            ui.find("button[pofield=openHosp]").css('display','none');
        }
        if(data.seroTabState=="1"){
            ui.find("button[pofield=bj]").html("<i class='fa fa-search'></i>已标记");
        }else{
            ui.find("button[pofield=bj]").html("<i class='fa fa-search'></i>标记");
        }
        $("#appSerPeople_list").append(ui);
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
    //查询
    function findTable(){
        qvo = uiToData("form_qvo",qvo);//界面值绑定到变量
        doAjaxPost('<%=request.getContextPath()%>/appSerPeople.action?act=list',{qvoJson:JSON.stringify(qvo)},callDataToUi);
    }
    function callDataToUi(data){
        dataToUi(data.qvo,"form_qvo");//数据绑定到界面
        qvodesc("qvodesc");
        qvoPN(data.qvo);
        $("#appSerPeople_list").html("");
        if(data.rows != null){
            $.each(data.rows, function(k, v) {
                uiFromTmp("tlist", v,k);
            });
        }
    }
    //重置
    function findReset(){
        $('#serName').val("");
    }

    //准备新增
    function forAddTable(){
        var url = '<%=request.getContextPath()%>/appSerPeople.action?act=forAddOrEdit&handle=add';
        defualtHref(url);
    }
    //准备修改
    function forModifyTable(ui){
        $.post('<%=request.getContextPath()%>/appSerPeople.action?act=roleCmm',{'id':$(ui).data("vo").id},function(data) {
            var data = eval('(' + data + ')');
            if (data.msg == "true") {
                var url = '<%=request.getContextPath()%>/appSerPeople.action?act=forAddOrEdit&handle=modify&vo.id='+$(ui).data("vo").id;
                defualtHref(url);
            }else{
                showMsg(data.msg);
            }
        })

    }
    //查看
    function forLookTable(ui){
        var url = '<%=request.getContextPath()%>/appSerPeople.action?act=forAddOrEdit&handle=look&vo.id='+$(ui).data("vo").id;
        defualtHref(url);
    }

    //全选和全选取消
    function selectAll(){
        if($("#selectAll").prop("checked")){
            $.each($("#appSerPeople_list").find("input[name=chckBox]"),function(k,v){
                $(this).prop("checked",true);
            });
        }else{
            $.each($("#appSerPeople_list").find("input[name=chckBox]"),function(k,v){
                $(this).prop("checked",false);
            });
        }
    }

    //删除页面获取删除ID
    function approle_del(){
        var ids="";
        $.each($("#appSerPeople_list").find("input[name=chckBox]"),function(k,v){
            if($(this).prop("checked")){
                if(ids!=""){
                    ids+=";";
                }
                ids+=$(this).val();
            }
        });
        if(ids==""){
            showMsg("请选择需要删除的信息!");
        }else{
            layer.confirm('确认删除数据?', {
                btn: ['确定','取消']
            }, function(){
                del(ids);
            });
        }
    }

    //删除
    function delForm(ui){
        layer.confirm('确认删除数据?', {
            btn: ['确定','取消']
        }, function(){
            $.post('<%=request.getContextPath()%>/appSerPeople.action?act=roleCmm',{'id':$(ui).data("vo").id,'method':'delete'},function(data){
                var data=eval('(' + data + ')');
                if(data.msg=="true"){
                    var id= $(ui).data("vo").id;
                    del(id);
                }else{
                    showMsg(data.msg);
                }
            })
        });
    }

    function del(id){
        $.post('<%=request.getContextPath()%>/appSerPeople.action?act=delete',{'id':id},function(data){
            var data=eval('(' + data + ')');
            if(data.msg=="true"){
                showMsg("删除成功!");
                findTable();
            } else {
                showMsg(data.msg);
            }
        });
    }

    //指定开放对象
    function ChangeDateToUser(ui){
        $("#serId").val($(ui).data("vo").id);
        findDept();
    }
    function openState(ui){
        $.post('<%=request.getContextPath()%>/appSerPeople.action?act=openState',{'id':$(ui).data("vo").id},function(data){
            var data=eval('(' + data + ')');
            if(data.msg=="true"){
                if(ui.find("button[pofield=open]").text()=="开启"){
                    ui.find("button[pofield=open]").html("<i class='fa fa-search'></i>关闭");
                    ui.find("button[pofield=openHosp]").css('display','inline-block');
                }else{
                    ui.find("button[pofield=open]").html("<i class='fa fa-search'></i>开启");
                    ui.find("button[pofield=openHosp]").css('display','none');
                }
            }else{
                showMsg(data.msg);
            }
        })
    }
    //标记
    function bj(ui){
        doAjaxPost('<%=request.getContextPath()%>/appSerPeople.action?act=bjCmm',{id:$(ui).data("vo").id},function(data){
            if(data.msg=="true"){
                $.each($("#appSerPeople_list").find("button[pofield=bj]"),function(k,v){
                    $(this).html("<i class='fa fa-search'></i>标记");
                });
                ui.find("button[pofield=bj]").html("<i class='fa fa-search'></i>已标记");
            }else if(data.msg=="888"){
                ui.find("button[pofield=bj]").html("<i class='fa fa-search'></i>标记");
            }else{
                showMsg(data.msg);
            }
        });
    }
</script>
</body>
</html>
