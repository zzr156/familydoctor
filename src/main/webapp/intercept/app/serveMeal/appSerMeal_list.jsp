<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>服务包定制管理</title>
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
                            <label class="col-sm-2 control-label">查询条件：</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" pofield="content" name="content" id="content">
                            </div>
                            <div class="col-sm-4">
                                <select id="type" pofield="type" class="form-control">

                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">开放区域：</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" pofield="openArea" name="openArea" id="openArea">
                            </div>
                            <label class="col-sm-2 control-label">显示条数：</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" pofield="pageSize"  id="pageSize"  value="15" >
                            </div>
                        </div>
                        <div class="text-center" style="border-top: 1px dashed #D7D7D7;padding-top: 10px;">
                            <a class="btn btn-primary system appSerMeal_list" onclick="findTable();">查询</a>
                            <a class="btn btn-default system appSerMeal_list" onclick="findReset();">重置</a>
                            <a class="btn btn-primary system appSerMeal_outLoad" onclick="exportTable();">导出服务包</a>
                            <a class="btn btn-info system appSerMeal_outLoad" onclick="sign_getOut();">下载模板</a>
                            <a class="btn btn-primary system appSerMeal_inLoad" onclick="addfile();">上传数据</a>
                            <a class="btn btn-info system appSerMeal_inLoad" ><input type="file" id="upExcel" name="upExcel" class="layui-btn layui-btn-normal"/></a>
                            <%--<div class="layui-form-item">--%>
                                <%--<div class="layui-input-block"  style="text-align: center;">--%>

                                <%--</div>--%>
                            <%--</div>--%>
                            <%--<a class="btn btn-primary system appSerMeal_list" onclick="layerOpenUpload();">上传文件</a>--%>
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
                    <a class="label label-danger pull-right system appSerMeal_delete" onclick="approle_del();">批量删除</a>
                    <a class="label label-success pull-right system appSerMeal_add" onclick="forAddTable();">新增</a>
                </div>
                <div class="ibox-content tabble-c">
                    <div class="table-responsive">
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th style="text-align:center;"><input type="checkbox" id="selectAll"style="width:22px;height:22px" onclick="selectAll()"/></th>
                                <th style="text-align: center;">序号</th>
                                <th style="text-align: center;">服务包编号</th>
                                <th style="text-align: center">服务包名称</th>
                                <th style="text-align: center;">服务人群</th>
                                <th style="text-align: center;width: 20%">服务内容</th>
                                <th style="text-align: center;">套餐总金额</th>
                                <th style="text-align: center">是否含减免</th>
                                <th style="text-align: center">减免对象经济类型</th>
                                <th style="text-align: center;width: 20%">政府补贴方式</th>
                                <%--<th style="text-align: center">套餐实付金额</th>--%>
                                <th style="text-align: center">有效期状态</th>
                                <th style="text-align: center">是否可变更医生</th>
                                <th style="text-align: center">开放区域</th>
                                <th style="text-align: center;width: 20%">操作</th>
                            </tr>
                            </thead>
                            <tbody id="appSerMeal_list">

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
<jsp:include page="appSerMeal_change.jsp" flush="false" />
<jsp:include page="/open/mould/app/serveMeal/appServeMeal_tmp.jsp" flush="false" />
<script type="text/javascript">
    function uiFromTmp(id, data,idx) {
        var ui = $($("#" + id + "").html());
        ui.data("vo", data);
        ui.find("td[pofield=mId] input[name=chckBox]").val(data.id);
        ui.find("td[pofield=num]").text(idx+1);
        ui.find("td[pofield=smValue]").text(data.sersmValue);
        ui.find("td[pofield=smName]").text(data.sersmName);
        ui.find("td[pofield=smObjectName]").text(data.sersmObjectTitle);
        ui.find("td[pofield=smPkName]").text(data.strSersmPkTitle);
        ui.find("td[pofield=smTotalFee]").text(data.sersmTotalFee);
        ui.find("td[pofield=smJmState]").text(data.strSmDownState);
        ui.find("td[pofield=smJjType]").text(data.strSmJjType);
        ui.find("td[pofield=smBtWay]").text(data.strsmSubsidyWay);
        ui.find("td[pofield=smTimeState]").text(data.sergGroupFee);
        ui.find("td[pofield=smBgState]").text(data.strSmBgDr);
        ui.find("td[pofield=areaName]").text(data.strAreaName);
        if(data.sersmOpenState=="1"){
            ui.find("button[pofield=open]").html("<i class='fa fa-search'></i>关闭");
        }else{
            ui.find("button[pofield=open]").html("<i class='fa fa-search'></i>开启");
            ui.find("button[pofield=openHosp]").css('display','none');
        }
        if(data.sersmTabState=="1"){
            ui.find("button[pofield=bj]").html("<i class='fa fa-search'></i>已设为快签包");
        }else{
            ui.find("button[pofield=bj]").html("<i class='fa fa-search'></i>设为快签包");
        }
        $("#appSerMeal_list").append(ui);
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
        doAjaxPost('<%=request.getContextPath()%>/appSerMeal.action?act=findCmmList',{},callDataToInit);
    }
    function callDataToInit(data){
        if(data.map != null){
            if(data.map.cxtj != null){
                $("#type").html("");
                var option = document.createElement('option');
                option.setAttribute("value","");
                option.innerText = "--请选择查询类型--";
                document.getElementById("type").appendChild(option);
                $.each(data.map.cxtj,function(k,v){
                    dataUiCodeGroup("select","type",v.codeTitle,v.codeValue);
                })
            }
            findTable();
        }
    }
    //查询
    function findTable(){
        qvo = uiToData("form_qvo",qvo);//界面值绑定到变量
        var options = $("#type option:selected");
        qvo["type"] = options.val();
        if(necs($("#content").val())){
            if(iecs(options.val())){
                showMsg("查询条件查询类型不能为空！");
                $("#type").focus();
                return;
            }
        }
        doAjaxPost('<%=request.getContextPath()%>/appSerMeal.action?act=list',{qvoJson:JSON.stringify(qvo)},callDataToUi);
    }
    function callDataToUi(data){
        dataToUi(data.qvo,"form_qvo");//数据绑定到界面
        qvodesc("qvodesc");
        qvoPN(data.qvo);
        $("#appSerMeal_list").html("");
        if(data.rows != null){
            $.each(data.rows, function(k, v) {
                uiFromTmp("tlist", v,k);
            });
        }
    }
    //重置
    function findReset(){
        $('#content').val("");
    }

    //准备新增
    function forAddTable(){
        var url = '<%=request.getContextPath()%>/appSerMeal.action?act=forAddOrEdit&handle=add';
        defualtHref(url);
    }
    //准备修改
    function forModifyTable(ui){
        $.post('<%=request.getContextPath()%>/appSerMeal.action?act=roleCmm',{'id':$(ui).data("vo").id},function(data) {
            var data = eval('(' + data + ')');
            if (data.msg == "true") {
                var url = '<%=request.getContextPath()%>/appSerMeal.action?act=forAddOrEdit&handle=modify&vo.id='+$(ui).data("vo").id;
                defualtHref(url);
            }else{
                showMsg(data.msg);
            }
        })

    }
    //查看
    function forLookTable(ui){
        var url = '<%=request.getContextPath()%>/appSerMeal.action?act=forAddOrEdit&handle=look&vo.id='+$(ui).data("vo").id;
        defualtHref(url);
    }

    //全选和全选取消
    function selectAll(){
        if($("#selectAll").prop("checked")){
            $.each($("#appSerMeal_list").find("input[name=chckBox]"),function(k,v){
                $(this).prop("checked",true);
            });
        }else{
            $.each($("#appSerMeal_list").find("input[name=chckBox]"),function(k,v){
                $(this).prop("checked",false);
            });
        }
    }

    //删除页面获取删除ID
    function approle_del(){
        var ids="";
        $.each($("#appSerMeal_list").find("input[name=chckBox]"),function(k,v){
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
    //南平删除操作处理
    function npDelRule(ui){
        if($(ui).find("[pofield=mId] input").val()=="np_default"){
            showMsg("默认服务包不能删除!");
            return false;
        }
        return true;

    }
    //删除
    function delForm(ui){
        layer.confirm('确认删除数据?', {
            btn: ['确定','取消']
        }, function(){
            if(npDelRule(ui)) {//南平默认包处理
                $.post('<%=request.getContextPath()%>/appSerMeal.action?act=roleCmm', {'id': $(ui).data("vo").id}, function (data) {
                    var data = eval('(' + data + ')');
                    if (data.msg == "true") {
                        var id = $(ui).data("vo").id;
                        del(id);
                    } else {
                        showMsg(data.msg);
                    }
                })
            }
        });
    }

    function del(id){
        $.post('<%=request.getContextPath()%>/appSerMeal.action?act=delete',{'id':id},function(data){
            var data=eval('(' + data + ')');
            if(data.msg=="true"){
                showMsg("删除成功!");
                findTable();
            } else {
                showMsg(data.msg);
            }
        });
    }
    //标记
    function bj(ui){
        doAjaxPost('<%=request.getContextPath()%>/appSerMeal.action?act=bjCmm',{id:$(ui).data("vo").id},function(data){
            if(data.msg=="true"){
                $.each($("#appSerMeal_list").find("button[pofield=bj]"),function(k,v){
                    $(this).html("<i class='fa fa-search'></i>设为快签包");
                });
                ui.find("button[pofield=bj]").html("<i class='fa fa-search'></i>已设为快签包");
            }else if(data.msg=="888"){
                ui.find("button[pofield=bj]").html("<i class='fa fa-search'></i>设为快签包");
            }else{
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
        $.post('<%=request.getContextPath()%>/appSerMeal.action?act=openState',{'id':$(ui).data("vo").id},function(data){
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
    /**
     * 打印协议
     * @param ui
     */
    function print(ui){
        var url = '<%=request.getContextPath()%>/appSerMeal.action?act=print&vo.id='+$(ui).data("vo").id;
        defualtHref(url);
       /*$("#book").jqprint({
            debug: false, //如果是true则可以显示iframe查看效果（iframe默认高和宽都很小，可以再源码中调大），默认是false
            importCSS: true, //true表示引进原来的页面的css，默认是true。（如果是true，先会找$("link[media=print]")，若没有会去找$("link")中的css文件）
            printContainer: true, //表示如果原来选择的对象必须被纳入打印（注意：设置为false可能会打破你的CSS规则）。
            operaSupport: true//表示如果插件也必须支持歌opera浏览器，在这种情况下，它提供了建立一个临时的打印选项卡。默认是true
        });*/
    }
    function addfile(){
        var pic = $("#upExcel").val().split(".");
        if(pic[1]==undefined || (pic[1]!='xls')){
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
                url: 'appSerMeal.action?act=importExcel', //用于文件上传的服务器端请求地址
                secureuri: false, //是否需要安全协议，一般设置为false
                fileElementId: 'upExcel', //文件上传域的ID
                dataType: 'JSON', //返回值类型 一般设置为json
                success: function (data, status)  //服务器成功响应处理函数
                {
                    var da=JSON.parse(data);
                    console.log(da);
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

    function exportTable() {
        var peopleids = "";
        $.each($("#appSerMeal_list").find("input[name=chckBox]"), function(k, v) {
            if ($(this).prop("checked")) {
                if (peopleids != "") {
                    peopleids += ";";
                }
                peopleids += $(this).parent().find("input").val();
            }
        });
        if (peopleids == "") {
            alert("请选择需要导出的数据！");
        } else {
            pListPrintByPeoIds(peopleids);
        }
    }
    function pListPrintByPeoIds(peopleids){
        window.location.href="<%=request.getContextPath()%>/appSerMeal.action?act=pListPrintByPeoIds&strIds="+peopleids;
    }

    function sign_getOut(){
        window.location.href="<%=request.getContextPath()%>/appSerMeal.action?act=noExcelDownload";
    }


</script>
</body>
</html>
