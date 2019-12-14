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
    <title>医生用户管理主页</title>
    <%@ include file="/open/commonjs/tldHtml.jsp"%>
    <%@ include file="/open/commonjs/roleson.jsp"%>
    <%@ include file="/open/commonjs/tldLayui.jsp"%>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content" id="form_qvo">
                    <form method="get" class="form-horizontal">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">账号：</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" pofield="appDrAccount"  id="appDrAccount">
                            </div>
                            <label class="col-sm-2 control-label">用户名：</label>
                            <div class="col-sm-4 ">
                                <input type="text" class="form-control" pofield="appDrName"  id="appDrName">
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
                            <label class="col-sm-2 control-label">用户状态：</label>
                            <div class="col-sm-4 ">
                                <select class="form-control" pofield="appDrState" id="appDrState">
                                    <option value="">--请选择--</option>
                                </select>
                            </div>
                            <label class="col-sm-2 control-label">显示条数：</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" pofield="pageSize"  id="pageSize"  value="15" >
                            </div>
                        </div>
                        <div class="text-center" style="border-top: 1px dashed #D7D7D7;padding-top: 10px;">
                            <a class="btn btn-primary system appdr_list" onclick="findTable();">查询</a>
                            <a class="btn btn-default system appdr_list" onclick="findReset();">重置</a>
                            <a class="btn btn-primary system appdr_inLoad" onclick="addfile();">导入数据</a>
                            <a class="btn btn-info system appdr_inLoad" ><input type="file" id="upExcel" name="upExcel" class="layui-btn layui-btn-normal"/></a>
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
                    <a class="label label-danger pull-right system appdr_delete" onclick="user_del();">批量删除</a>
                    <a class="label label-success pull-right system appdr_add" onclick="forAddTable();">新增</a>
                </div>
                <div class="ibox-content tabble-c">
                    <div class="table-responsive">
                        <table class="table table-striped" id="drUserTable">
                            <thead>
                            <tr>
                                <th style="text-align:center;"><input type="checkbox" id="selectAll"style="width:22px;height:22px" onclick="selectAll()"/></th>
                                <th style="text-align: center;">序号</th>
                                <th style="text-align: center;">医生姓名</th>
                                <th style="text-align: center;">医生编码</th>
                                <th style="text-align: center;">医生账号</th>
                                <th style="text-align: center;">所属单位</th>
                                <th style="text-align: center;">工作职称</th>
                                <th style="text-align: center;">医生类别</th>
                                <th style="text-align: center;">状态</th>
                                <th style="text-align: center;">操作</th>
                            </tr>
                            </thead>
                            <tbody id="drUser_list">

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
<jsp:include page="/open/mould/app/drUser/drUser_tmp.jsp" flush="false" />
<script type="text/javascript">
    function uiFromTmp(id, data,idx) {
        var ui = $($("#" + id + "").html());
        ui.data("vo", data);
        ui.find("td[pofield=drUserId] input[name=chckBox]").val(data.id);
        ui.find("td[pofield=num]").text(idx+1);
        ui.find("td[pofield=drName]").text(data.drName);
        ui.find("td[pofield=drCode]").text(data.drCode);
        ui.find("td[pofield=drAccount]").text(data.drAccount);
        ui.find("td[pofield=hospName]").text(data.drHospName);
        ui.find("td[pofield=drJobTitle]").text(data.drJobTitle);
        ui.find("td[pofield=drTypeName]").text(data.drTypeName);
        ui.find("td[pofield=drStateName]").text(data.drStateName);
        $("#drUser_list").append(ui);
        ui.fadeIn();
    }

</script>
<script type="text/javascript">
    var qvo={};
    var vo={};
    <!--list-->
    $(function(){
        // findDept();
        findCmmInit();
        //医院权限用户屏蔽查询条件
        var pro=HospAreaCode.substring(0,2)+"0000000000";
        var city=HospAreaCode.substring(0,4)+"00000000";
        var area=HospAreaCode.substring(0,6)+"000000";
        if(drRole.indexOf("1") != -1){
            findAddress('city',pro)
            setTimeout(function () {
                $("#pro").val(pro);
                $("#pro").attr("disabled","disabled");
            },500);
        }else if(drRole.indexOf("2") != -1){
            findAddress('city',pro)
            findAddress('area',city)
            setTimeout(function () {
                $("#pro").val(pro);
                $("#city").val(city);
                $("#pro").attr("disabled","disabled");
                $("#city").attr("disabled","disabled");
            },500);

        }else if(drRole.indexOf("3") != -1){
            findAddress('city',pro)
            findAddress('area',city)
            findAddress('drHospId',area)
            setTimeout(function () {
                $("#pro").val(pro);
                $("#city").val(city);
                $("#area").val(area);
                $("#pro").attr("disabled","disabled");
                $("#city").attr("disabled","disabled");
                $("#area").attr("disabled","disabled");
            },500);
        }else if(drRole.indexOf("4") != -1 || drRole.indexOf("9") != -1){
            findAddress('city',pro)
            findAddress('area',city)
            findAddress('drHospId',area)
            setTimeout(function () {
                $("#pro").val(pro);
                $("#city").val(city);
                $("#area").val(area);
                $("#drHospId").val(orgid);
                $("#pro").attr("disabled","disabled");
                $("#city").attr("disabled","disabled");
                $("#area").attr("disabled","disabled");
                $("#drHospId").attr("disabled","disabled");
            },600);
        }
    })
    function findCmmInit(){
        doAjaxPost('<%=request.getContextPath()%>/appdr.action?act=findCmmInit',{},callDataToCmmInit);
    }
    function callDataToCmmInit(data){
        if(data.map!=null){
            if(data.map.province!=null){
                $.each(data.map.province,function(k,v){
                    dataUiCodeGroup("select","pro",v.areaSname,v.id);
                })
            }
            if(data.map.drState!=null){
                $.each(data.map.drState,function(k,v){
                    dataUiCodeGroup("select","appDrState",v.codeTitle,v.codeValue);
                })
            }
        }
        setTimeout(function () {
            onInit();
        },600);
    }

    //重置
    function findReset(){
        $('#appDrAccount').val("");
        $('#drHospId').val("");
        $('#drHospName').val("");
        $('#appDrName').val("");
        $('#appDrState').val("");
        $('#pro').val("");
        $('#city').val("");
        $('#area').val("");
    }
    //加载完成时，查询数据，记载到表格的方法
    function onInit() {
        findTable();
    }
    <%--//状态--%>
    <%--function findState(){--%>
        <%--doAjaxPost('<%=request.getContextPath()%>/code.action?act=jsonTreelist',{group:'workState'},callDataToState);--%>
    <%--}--%>
    <%--function callDataToState(data){--%>
        <%--$("#appDrState").html();--%>
        <%--$.each(data, function(k, v) {--%>
            <%--dataUiCodeGroup("select","appDrState",v.codeTitle,v.codeValue);--%>
        <%--});--%>
        <%--findTable();--%>
    <%--}--%>

    //查询
    function findTable(){
        qvo = uiToData("form_qvo",qvo);//界面值绑定到变量
        qvo["appDrState"]=$("#appDrState").val();
        //qvo["drHospId"]=$("#drHospId").val();
        if(drRole.indexOf("1") == -1 && drRole.indexOf("2") == -1
            && drRole.indexOf("3") == -1 && drRole.indexOf("4") == -1
            && drRole.indexOf("9") != -1){
            qvo["appDrAccount"]=username;
        }

        doAjaxPost('<%=request.getContextPath()%>/appdr.action?act=list',{qvoJson:JSON.stringify(qvo)},callDataToUi);
    }
    function callDataToUi(data){
        $.each(data.rows,function(k,v){
            v.drJobTitle=drJobTitle[v.drJobTitle];
        })
        dataToUi(data.qvo,"form_qvo");//数据绑定到界面
        qvodesc("qvodesc");
        qvoPN(data.qvo);
        $("#drUserTable tbody").html("");
        if(data.rows != null){
            $.each(data.rows, function(k, v) {
                uiFromTmp("tlist", v,k);
            });
        }
    }

    //准备新增
    function forAddTable(){
        var url = '<%=request.getContextPath()%>/appdr.action?act=forAddOrEdit&handle=add';
        defualtHref(url);
    }
    //准备修改
    function forModifyTable(ui){
        var url = '<%=request.getContextPath()%>/appdr.action?act=forAddOrEdit&handle=modify&vo.id='+$(ui).data("vo").id;
        defualtHref(url);
    }
    //查看
    function forLookTable(ui){
        var url = '<%=request.getContextPath()%>/appdr.action?act=forAddOrEdit&handle=look&vo.id='+$(ui).data("vo").id;
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
                url: 'appdr.action?act=importExcel', //用于文件上传的服务器端请求地址
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
