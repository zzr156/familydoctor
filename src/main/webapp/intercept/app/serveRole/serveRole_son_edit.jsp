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
                                    <th style="text-align:center;">服务人群名称</th>
                                    <th style="text-align:center;">服务类型名称</th>
                                    <th style="text-align:center;">频次</th>
                                    <th style="text-align:center;">间隔</th>
                                </tr>
                            </thead>
                            <tbody id="tb">

                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="5" nowrap="nowrap" align="center">
                                    <button onclick="saveTable();" type="button" class="btn btn-primary system appserverole_sonModify">保存</button>
                                    <button onclick="backTable()"  type="button" class="btn btn-default system appserverole_list">返回</button>
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
<jsp:include page="/open/mould/app/serveRole/serve_tmp_son.jsp" flush="false" />

<script type="text/javascript">
    function uiFromTmp(id, data,idx) {
        var t = $($("#" + id + "").html());
        dataToUiTmp_idlsttr_rs(t, data,idx);
        $("#tb").append(t);
        t.fadeIn();
    }
    function dataToUiTmp_idlsttr_rs(ui, data,idx) {
        ui.data("vo", data);
        ui.find("td[pofield=serSettingId]").text(data.serSettingId);
        ui.find("td[pofield=serRoleSonId]").text(data.serRoleSonId);
        ui.find("td[pofield=serObjectTitle]").text(data.serObjectTitle);
        ui.find("td[pofield=serTitle]").text(data.serTitle);
        ui.find("td input[pofield=setNum]").val(data.setNum);
        ui.find("td input[pofield=setSpace]").val(data.setSpace);
        ui.find("td select[pofield=setSpaceType]").val(data.setSpaceType);
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
        if($("#id").val() != ""){
            doAjaxPost('<%=request.getContextPath()%>/appServe.action?act=findCmmServe',{serveId:$("#id").val()},callDataToMenu);
        }
    }

    function callDataToMenu(data){
        $.each(data.rows, function(k, v) {
            uiFromTmp("tlist", v,k);
        });

        doAjaxPost('<%=request.getContextPath()%>/appserverole.action?act=findCmmModuleRoleMenu',{id:$("#id").val()},function(datamenu){
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
        var settingIds = "";
        var setNums = "";
        var setSpace = "";
        var setSpaceType = "";
        for(var i=0;i<$("#tb tr").length;i++){
            if(i ==0){
                settingIds = $("#tb tr")[i].children[0].innerHTML;
                ids = $("#tb tr")[i].children[1].innerHTML;
                setNums = $("#tb tr")[i].children[4].children[0].children[0].children[0].value;
                setSpace = $("#tb tr")[i].children[5].children[0].children[0].children[0].value;
                setSpaceType = $("#tb tr")[i].children[5].children[0].children[1].children[0].value;
            }else{
                settingIds += ";"+$("#tb tr")[i].children[0].innerHTML;
                ids += ";"+ $("#tb tr")[i].children[1].innerHTML;
                setNums += ";"+  $("#tb tr")[i].children[4].children[0].children[0].children[0].value;
                setSpace += ";"+  $("#tb tr")[i].children[5].children[0].children[0].children[0].value;
                setSpaceType += ";"+  $("#tb tr")[i].children[5].children[0].children[1].children[0].value;
            }
        }
        doAjaxPost('<%=request.getContextPath()%>/appserverole.action?act=sonModify',{id :id,ids:ids,settingIds:settingIds,setNums:setNums,setSpace:setSpace,setSpaceType:setSpaceType},callDataSave);
    }

    function callDataSave(data){
        if(data.msg == 'true'){
            var url = '<%=request.getContextPath()%>/appserverole.action?act=forList';
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
