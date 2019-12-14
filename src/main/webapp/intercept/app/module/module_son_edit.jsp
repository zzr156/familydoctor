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
    <title>手机模块权限管理主页</title>
    <%@ include file="/open/commonjs/tldHtml.jsp"%>
    <%@ include file="/open/commonjs/roleson.jsp"%>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content tabble-c">
                    <div class="table-responsive">
                        <table class="table table-striped" id="mytable">
                            <thead>
                                <tr >
                                    <th style="text-align:center;"><input type="checkbox" id="selectAll"style="width:22px;height:22px" onclick="selectAll()"/></th>
                                    <th style="text-align:center;">菜单模块</th>
                                    <th style="text-align:center;">菜单名称</th>
                                    <th style="text-align:center;">菜单值</th>
                                    <th style="text-align:center;">菜单类型</th>
                                </tr>
                            </thead>
                            <tbody id="tb">

                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="5" nowrap="nowrap" align="center">
                                    <button onclick="saveTable();" type="button" class="btn btn-primary system appmodulerole_sonModify">保存</button>
                                    <button onclick="backTable()"  type="button" class="btn btn-default system appmodulerole_list">返回</button>
                                    <button onclick="selectall()"  type="button" class="btn btn-info system appmodulerole_list">全选</button>
                                    <button onclick="noselectall()"  type="button" class="btn btn-info system appmodulerole_list">不选</button>
                                    <input type="hidden" id="id" pofield="id" name="id" value="${vo.id}" />
                                    <input type="hidden" id="handle"  name="handle" value="${handle}" />
                                </td>
                            </tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="/open/mould/app/module/module_tmp_son.jsp" flush="false" />

<script type="text/javascript">
    function uiFromTmp(id, data,idx) {
        var t = $($("#" + id + "").html());
        dataToUiTmp_idlsttr_rs(t, data,idx);
        $("#tb").append(t);
        t.fadeIn();
    }
    function dataToUiTmp_idlsttr_rs(ui, data,idx) {
        ui.data("vo", data);
        ui.find("td[pofield=id] input[name=chckBox]").val(data.id);
        ui.find("td[pofield=strMenuModule]").text(data.strMenuModule);
        ui.find("td[pofield=menuName]").text(data.menuName)
        ui.find("td[pofield=menuValue]").text(data.menuValue);
        ui.find("td[pofield=strMenuType]").text(data.strMenuType);
    }
</script>
<script type="text/javascript">
    var vo={};
    //页面加载完成时启动的方法
    $(document).ready(function() {
        onInit();
    });

    //加载完成时，查询数据，记载到表格的方法
    function onInit() {
        if($("#handle").val() != ""){
            var qvo={type:$("#handle").val()}
            doAjaxPost('<%=request.getContextPath()%>/appmenu.action?act=findMenulist',{qvoJson:JSON.stringify(qvo)},callDataToMenu);
        }
    }

    function callDataToMenu(data){
        $.each(data.rows, function(k, v) {
            uiFromTmp("tlist", v,k);
        });

        doAjaxPost('<%=request.getContextPath()%>/appmodulerole.action?act=findCmmModuleRoleMenu',{id:$("#id").val()},function(datamenu){
            $("input[name='chckBox']:checkbox").each(function(){
                if(JSON.stringify(datamenu).indexOf($(this).attr("value"))!= -1){
                    $(this).attr("checked", true);
                }
            })
        });
    }

    //点击返回
    function backTable(){
        history.go(-1);
    }

    //全选和全选取消
    function selectAll(){
        if($("#selectAll").prop("checked")){
            $.each($("#tb").find("input[name=chckBox]"),function(k,v){
                $(this).prop("checked",true);
            });
        }else{
            $.each($("#tb").find("input[name=chckBox]"),function(k,v){
                $(this).prop("checked",false);
            });
        }
    }


    //保存的方法
    function saveTable() {
        var id = $("#id").val();
        var ids = "";
        $.each($("#tb").find("input[name=chckBox]"),function(k,v){
            if($(this).prop("checked")){
                if(ids!=""){
                    ids+=";";
                }
                ids+=$(this).val();
            }
        });
        if(iecs(ids)){
            showMsg("请勾选要给予的权限！");
            return;
        }
        doAjaxPost('<%=request.getContextPath()%>/appmodulerole.action?act=sonModify',{id :id,ids:ids},callDataSave);
    }

    function callDataSave(data){
        if(data.msg == 'true'){
            var url = '<%=request.getContextPath()%>/appmodulerole.action?act=forList';
            defualtHref(url);
        }else{
            layer.open({
                skin: 'layui-layer-molv',
                closeBtn: 0,
                title: false,
                content:data.msg ,
                anim: 5 ,
                btn: ['关闭']
            });
        }
    }


    //全选
    function selectall(){
        $.each($("#tb").find("input[name=chckBox]"),function(k,v){
            $(this).prop("checked",true);
        });
    }

    //全不选
    function noselectall(){//全不选
        $.each($("#tb").find("input[name=chckBox]"),function(k,v){
            $(this).prop("checked",false);
        });
    }

</script>
</body>
</html>
