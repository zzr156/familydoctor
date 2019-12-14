<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>服务设置管理</title>
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
                            <label class="col-sm-2 control-label">服务人群：</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" pofield="resident"  id="resident">
                            </div>
                            <label class="col-sm-2 control-label">服务类型：</label>
                            <div class="col-sm-4 ">
                                   <input type="text" class="form-control" pofield="serveType"  id="serveType">

                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">显示条数：</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" pofield="pageSize"  id="pageSize"  value="15" >
                            </div>
                        </div>
                        <div class="text-center" style="border-top: 1px dashed #D7D7D7;padding-top: 10px;">
                            <a class="btn btn-primary system appServe_list" onclick="findTable();">查询</a>
                            <a class="btn btn-default system appServe_list" onclick="findReset();">重置</a>
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
                    <a class="label label-danger pull-right system appServe_delete" onclick="approle_del();">批量删除</a>
                    <a class="label label-success pull-right system appServe_add" onclick="forAddTable();">新增</a>
                </div>
                <div class="ibox-content tabble-c">
                    <div class="table-responsive">
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th style="text-align:center;"><input type="checkbox" id="selectAll"style="width:22px;height:22px" onclick="selectAll()"/></th>
                                <th style="text-align: center;">序号</th>
                                <th style="text-align: center;">服务人群名称</th>
                                <th style="text-align: center;">服务类型名称</th>
                                <th style="text-align: center;">创建人</th>
                                <th style="text-align: center;">状态</th>
                                <th style="text-align: center;">操作</th>
                            </tr>
                            </thead>
                            <tbody id="appServe_list">

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
<jsp:include page="/open/mould/app/serveSetting/serveSetting_tmp.jsp" flush="false" />
<script type="text/javascript">
    function uiFromTmp(id, data,idx) {
        var ui = $($("#" + id + "").html());
        ui.data("vo", data);
        ui.find("td[pofield=serId] input[name=chckBox]").val(data.id);
        ui.find("td[pofield=num]").text(idx+1);
        ui.find("td[pofield=serPeopleName]").text(data.serObjectTitle);
        ui.find("td[pofield=serTypeName]").text(data.serTitle);
        ui.find("td[pofield=state]").text(data.stateName);
        ui.find("td[pofield=createName]").text(data.userName);
        $("#appServe_list").append(ui);
        ui.fadeIn();
    }

</script>
<script type="text/javascript">
    var qvo={};
    var vo={};
    <!--list-->
    $(function(){
//        findCmmInit();
        findTable();
    })
    function findCmmInit(){
        doAjaxPost('<%=request.getContextPath()%>/appServe.action?act=findCmmInit',{},callDataToInit);
    }
    function callDataToInit(data){
        if(data.map!=null){
            //服务人群
            /*if(data.map.servePeople != null){
                $("#resident").html("");
                var option = document.createElement('option');
                option.setAttribute("value","");
                option.innerText = "--请选择服务人群--";
                document.getElementById("resident").appendChild(option);
                $.each(data.map.servePeople,function(k,v){
                    dataUiCodeGroup("select","resident",v.codeTitle+" ",v.codeValue);
                })
            }
            //服务类型
            if(data.map.serverType != null){
                $("#serveType").html("");
                var option = document.createElement('option');
                option.setAttribute("value","");
                option.innerText = "--请选择服务类型--";
                document.getElementById("serveType").appendChild(option);
                $.each(data.map.serverType,function(k,v){
                    dataUiCodeGroup("select","serveType",v.codeTitle+" ",v.codeValue);
                })
            }*/

        }
        findTable();
    }

    //查询
    function findTable(){
        qvo = uiToData("form_qvo",qvo);//界面值绑定到变量
        doAjaxPost('<%=request.getContextPath()%>/appServe.action?act=list',{qvoJson:JSON.stringify(qvo)},callDataToUi);
    }
    function callDataToUi(data){
        dataToUi(data.qvo,"form_qvo");//数据绑定到界面
        qvodesc("qvodesc");
        qvoPN(data.qvo);
        $("#appServe_list").html("");
        if(data.rows != null){
            $.each(data.rows, function(k, v) {
                uiFromTmp("tlist", v,k);
            });
        }
    }
    //重置
    function findReset(){
        $('#resident').val("");
        $('#serveType').val("");
    }

    //准备新增
    function forAddTable(){
        var url = '<%=request.getContextPath()%>/appServe.action?act=forAddOrEdit&handle=add';
        defualtHref(url);
    }
    //准备修改
    function forModifyTable(ui){
        var url = '<%=request.getContextPath()%>/appServe.action?act=forAddOrEdit&handle=modify&vo.id='+$(ui).data("vo").id;
        defualtHref(url);
    }
    //查看
    function forLookTable(ui){
        var url = '<%=request.getContextPath()%>/appServe.action?act=forAddOrEdit&handle=look&vo.id='+$(ui).data("vo").id;
        defualtHref(url);
    }

    //全选和全选取消
    function selectAll(){
        if($("#selectAll").prop("checked")){
            $.each($("#appServe_list").find("input[name=chckBox]"),function(k,v){
                $(this).prop("checked",true);
            });
        }else{
            $.each($("#appServe_list").find("input[name=chckBox]"),function(k,v){
                $(this).prop("checked",false);
            });
        }
    }

    //删除页面获取删除ID
    function approle_del(){
        var ids="";
        $.each($("#appServe_list").find("input[name=chckBox]"),function(k,v){
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
            var id= $(ui).data("vo").id;
            del(id);
        });
    }

    function del(id){
        $.post('<%=request.getContextPath()%>/appServe.action?act=delete',{'id':id},function(data){
            var data=eval('(' + data + ')');
            if(data.msg=="true"){
                showMsg("删除成功!");
                findTable();
            } else {
                showMsg(data.msg);
            }
        });
    }
</script>
</body>
</html>
