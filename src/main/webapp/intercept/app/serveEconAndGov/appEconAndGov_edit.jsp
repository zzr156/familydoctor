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
    <title>经济与政府补贴管理主页</title>
    <%@ include file="/open/commonjs/tldHtml.jsp"%>
    <%@ include file="/open/commonjs/roleson.jsp"%>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5 id="userAddTit"></h5>
                    <a class="label label-primary pull-right system appEag_list"  onclick="backTable();">返回</a>
                </div>
                <div class="ibox-content">
                    <form role="form" id="form_vo"  class="form-horizontal">
                        <input type="hidden" id="id" pofield="id" name="id" value="${vo.id}" />
                        <input type="hidden" id="handle"  name="handle" value="${handle}" />
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>经济类型：</label>
                            <div class="col-sm-4" >
                                <input type="hidden" pofield="eagEconId" id="eagEconId" name="eagEconId">
                                <select id="eagEconValue" class="form-control"></select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>政府补贴类型：</label>
                            <input type="hidden" id="eagGovId" pofield="eagGovId" name="eagGovId">
                            <div class="col-sm-4" id="eagGovValue">

                            </div>
                        </div>

                        <div class="text-center btnBar">
                            <a id="handleMethod" class="btn btn-primary system appEag_add" onclick="saveTable();">保存</a>
                        </div>
                    </form>
                </div>

            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var vo={};
    //页面加载完成时启动的方法
    $(document).ready(function() {
        findCmmInit();
    });
    function findCmmInit(){
        doAjaxPost('<%=request.getContextPath()%>/appEag.action?act=findCmmInit',{},function(data){
            if(data.map!=null){
                if(data.map.jjlx!=null){
                    $("#eagEconValue").html("");
                    var option = document.createElement('option');
                    option.setAttribute("value","");
                    option.innerText = "--请选择--";
                    document.getElementById("eagEconValue").appendChild(option);
                    $.each(data.map.jjlx, function (k, v) {
                        var optionO = document.createElement('option');
                        optionO.setAttribute("idd",v.id);
                        optionO.setAttribute("value",v.econValue);
                        optionO.innerText = v.econTitle;
                        $("#eagEconValue").append(optionO); //3、在末尾中添加元素
//                        dataUiCodeGroup("select", "eagEconValue", v.econTitle, v.econValue);
                    });
                }
                if(data.map.btfs!=null){
                    $("#eagGovValue").html("");
                    $.each(data.map.btfs,function(k,v){
                        var input = document.createElement('input');
                        input.setAttribute("type","checkbox");
                        input.setAttribute("name","eagGovValue");
                        input.setAttribute("pofield","eagGovValue");
                        input.setAttribute("idd",v.id);
                        input.setAttribute("id","eagGovValue");
                        input.setAttribute("value",v.govValue);
                        $("#eagGovValue").append(input); //3、在末尾中添加元素
                        if(v.govMoney!=null&&v.govMoney!=""){
                            $("#eagGovValue").append(v.govTitle+"("+v.govMoney+"元)"+" "); //3、在末尾中添加元素
//                            dataUiCodeGroup("checkbox","eagGovValue",v.govTitle+"("+v.govMoney+"元)"+" ",v.govValue);
                        }else{
                            $("#eagGovValue").append(v.govTitle+" "); //3、在末尾中添加元素
//                            dataUiCodeGroup("checkbox","eagGovValue",v.govTitle+" ",v.govValue);
                        }

                    })
                }
            }
            onInit();
        });
    }
    //加载完成时，查询数据，记载到表格的方法
    function onInit() {
        if($("#id").val()!=""){
            doAjaxPost('<%=request.getContextPath()%>/appEag.action?act=jsonByOne',{id:$("#id").val()},callDataToDrUser);
        }
        if($("#handle").val() == "look"){
            $("#handleMethod").hide();
            $("#userAddTit").text("查看经济类型与政府补贴信息");
        }else if($("#handle").val() == "modify"){
            $("#eagEconValue").attr("disabled",true);
            $("#handleMethod").text("修改");
            $("#userAddTit").text("修改经济类型与政府补贴类型信息");
        }else if($("#handle").val() == "add"){
            $("#userAddTit").text("新增经济类型与政府补贴类型信息");
        }
    }
    function callDataToDrUser(data){
        dataToUi(data,"form_vo");
        if(necs(data.eagEconId)){
            $("#eagEconValue").find("option").each(function(){
                var idd = $(this).attr("idd");
                if(data.eagEconId==idd){
                    $(this).attr("selected","selected");
                }
            });
//            $("#eagEconValue").val(data.eagEconValue);
        }

        if(necs(data.eagGovId)){
            $('input[name="eagGovValue"]').each(function(){
                $(this).attr("checked", false);
                if(data.eagGovId.indexOf($(this).attr("idd"))!=-1){
                    $(this).attr("checked", true);
                }
            });
        }
    }

    //点击返回
    function backTable(){
        history.go(-1);
    }

    //保存的方法
    function saveTable() {
        if(iecs($("#eagEconValue").val())){
            showMsg("请选择经济类型值！");
            $("#eagEconValue").focus();
            return;
        }
        var btType = "";
        var govId = "";
//        var obj=document.getElementsByName('eagGovValue');
        $('input[name="eagGovValue"]').each(function(){
            if($(this).prop("checked")){
                btType+=$(this).val()+";";
                govId+=$(this).attr("idd")+";";
            }
        });
//        for(var i=0; i<obj.length; i++){
//            if(obj[i].checked) {
//                btType+=obj[i].value+';';
//            }
//        }
        if(iecs(btType)){
            showMsg("请选择政府补贴方式");
            return;
        }
        $("#eagGovId").val(govId);
        vo = uiToData("form_vo",vo);
        vo["eagGovValue"]=btType;
        var options=$("#eagEconValue option:selected");
        vo["eagEconValue"]=options.val();
        vo["eagEconId"]=options.attr("idd");
        vo["eagEconTitle"]=options.text();
        vo["eagGovId"]=govId;
        var method = "add";
        if($("#id").val() != ""){
            method = "modify";
        };
        doAjaxPost('<%=request.getContextPath()%>/appEag.action?act='+method,{strJson : JSON.stringify(vo)},callDataSave);
    }

    function callDataSave(data){
        if(data.msg == 'true'){
            var url = '<%=request.getContextPath()%>/appEag.action?act=forList';
            defualtHref(url);
        }else{
            showMsg(data.msg);
        }
    }
</script>
</body>
</html>
