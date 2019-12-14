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
    <title>服务组合管理主页</title>
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
                    <a class="label label-primary pull-right system appSerGroup_list"  onclick="backTable();">返回</a>
                </div>
                <div class="ibox-content">
                    <form role="form" id="form_vo"  class="form-horizontal">
                        <input type="hidden" id="id" pofield="id" name="id" value="${vo.id}" />
                        <input type="hidden" id="handle"  name="handle" value="${handle}" />
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>服务组合编号：</label>
                            <div class="col-sm-4">
                                <input type="hidden" id="value" value="0">
                                <%--<input type="text" id="sergValue" name="sergValue" pofield="sergValue" class="form-control" onblur="findNum()">--%>
                                <input type="text" id="sergValue" name="sergValue" pofield="sergValue" class="form-control" readonly="readonly">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"><span class="text-danger">*</span>服务人群：</label>
                            <div class="col-sm-4">
                                <div class="input-group">
                                    <input type="text" class="form-control" id="sergObjectTitle" name="sergObjectTitle" pofield="sergObjectTitle" type="text" readonly="readonly">
                                    <input type="hidden" pofield="sergObjectId" id="sergObjectId" name="sergObjectId">
                                    <input type="hidden" pofield="sergObjectValue" id="sergObjectValue" name="sergObjectValue" />
                                    <input type="hidden" pofield="sergObjectType" id="sergObjectType" name="sergObjectType" >
                                    <input type="hidden" pofield="sergPkId" id="sergPkId" name="sergPkId">
                                    <span class="input-group-btn">
                                        <button type="button" class="btn btn-primary"  data-toggle="modal" data-target="#myModalHospDept" onclick="ChangeDateToUser()">选择服务人群</button>
                                    </span>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label" ><span class="text-danger">*</span>服务内容：</label>
                            <div class="col-sm-10">
                                <table width="100%">
                                    <tr>
                                        <td style="width:20%;">基本公共服务内容：</td>
                                        <td id="baseFw"></td>
                                    </tr>
                                    <tr>
                                        <td>特色服务内容：</td>
                                        <td id="tsFw"></td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label" ><span class="text-danger">*</span>服务组合费用：</label>
                            <div class="col-sm-4">
                                <div class="input-group">
                                    <input type="text" id="sergGroupFee" name="sergGroupFee" pofield="sergGroupFee" class="form-control" value="0">
                                    <span class="input-group-addon">元</span>
                                </div>
                            </div>
                        </div>
                        <div class="text-center btnBar">
                            <a id="handleMethod" class="btn btn-primary system appSerGroup_add" onclick="saveTable();">保存</a>
                        </div>
                    </form>
                </div>

            </div>
        </div>
    </div>
</div>
<jsp:include page="appSerGroup_ChangeObject.jsp" flush="false" />
<script type="text/javascript">
    var vo={};
    //页面加载完成时启动的方法
    $(document).ready(function() {
        findDept();
        findCmmInit();//初始化
    });
    //统一初始化
    function findCmmInit(){
        doAjaxPost('<%=request.getContextPath()%>/appSerGroup.action?act=findCmmInit',{},callDataToInit);
    }
    function callDataToInit(data){
        if(data.map != null){
            if(data.map.baseFw != null){
                $("#baseFw").html("");
                $.each(data.map.baseFw, function(k, v) {
                    var input = document.createElement('input');
                    input.setAttribute("type","checkbox");
                    input.setAttribute("name","baseFw");
                    input.setAttribute("pofield","baseFw");
                    input.setAttribute("id","baseFw");
                    input.setAttribute("idd",v.id);
                    input.setAttribute("value",v.serpkValue);
                    $("#baseFw").append(input); //3、在末尾中添加元素
                    if(necs(v.serpkTime)){
                        if(isNaN(v.serpkTime)){
                            $("#baseFw").append(v.serpkName+" "); //3、在末尾中添加元素
//                        dataUiCodeGroup("checkbox","baseFw",v.serpkName+" ",v.serpkValue);
                        }else{
                            $("#baseFw").append(v.serpkName+"("+v.serpkTime+"次/年) "); //3、在末尾中添加元素
//                        dataUiCodeGroup("checkbox","baseFw",v.serpkName+"("+v.serpkNum+"次/年) ",v.serpkValue);
                        }
                    }else{
                        $("#baseFw").append(v.serpkName+" "); //3、在末尾中添加元素
                    }
                });
               /* if($("#handle").val()=="add"){
                    $('input[name="baseFw"]').each(function(){
                        $(this).attr("checked", true);
                    })
                }*/
            }
            if(data.map.tsFw!=null){
                $("#tsFw").html("");
                $.each(data.map.tsFw, function(k, v) {
                    var input = document.createElement('input');
                    input.setAttribute("type","checkbox");
                    input.setAttribute("name","tsFw");
                    input.setAttribute("pofield","tsFw");
                    input.setAttribute("id","tsFw");
                    input.setAttribute("idd",v.id);
                    input.setAttribute("value",v.serpkValue);
                    $("#tsFw").append(input); //3、在末尾中添加元素
                    if(necs(v.serpkTime)){
                        if(isNaN(v.serpkTime)){
                            $("#tsFw").append(v.serpkName+" "); //3、在末尾中添加元素
                        }else{
                            $("#tsFw").append(v.serpkName+"("+v.serpkTime+"次/年) "); //3、在末尾中添加元素
                        }
                    }else{
                        $("#tsFw").append(v.serpkName+" "); //3、在末尾中添加元素
                    }


                });
            }
            if($("#handle").val()=="add"){
                $("#sergValue").val(data.map.code);
            }
            onInit();//回选
        }
    }

    //加载完成时，查询数据，记载到表格的方法
    function onInit() {
        if($("#id").val()!=""){
            doAjaxPost('<%=request.getContextPath()%>/appSerGroup.action?act=jsonByOne',{id:$("#id").val()},callDataToDrUser);
        }
        if($("#handle").val() == "look"){
            $("#handleMethod").hide();
            $("#userAddTit").text("查看服务组合信息");
        }else if($("#handle").val() == "modify"){
            $("#handleMethod").text("修改");
            $("#userAddTit").text("修改服务组合信息");
        }else if($("#handle").val() == "add"){
            $("#userAddTit").text("新增服务组合信息");
        }
    }
    function callDataToDrUser(data){
        dataToUi(data,"form_vo");
        $('input[name="baseFw"]').each(function(){
            var strs = data.sergPkId.split(";");
            for(var i=0;i<strs.length;i++){
                if(strs[i]==$(this).attr("idd")){
                    $(this).attr("checked", true);
                }
            }
            /*if(data.sergPkValue.indexOf($(this).val())!=-1){
                $(this).attr("checked", true);
            }*/
        })
        $('input[name="tsFw"]').each(function(){
            var strs = data.sergPkId.split(";");
            for(var i=0;i<strs.length;i++){
                if(strs[i]==$(this).attr("idd")){
                    $(this).attr("checked", true);
                }
            }
            /*if(data.sergPkValue.indexOf($(this).val())!=-1){
                $(this).attr("checked", true);
            }*/
        })

    }

    //点击返回
    function backTable(){
        history.go(-1);
    }

    //保存的方法
    function saveTable() {
        if(iecs($("#sergValue").val())){
            showMsg("组合编号不能为空！");
            $("#sergValue").focus();
            return;
        }
        if(iecs($("#sergObjectValue").val())){
            showMsg("服务人群不能为空！");
            $("#sergObjectTitle").focus();
            return;
        }
        if(iecs($("#sergGroupFee").val())){
            showMsg("请输入费用");
            $("#sergGroupFee").focus();
            return;
        }
        if($("#value").val()=="1"){
            showMsg("服务编号已存在");
            return;
        }
        var baseValue = "";
        var pkId = "";
        $('input[name="baseFw"]:checked').each(function(){
            pkId += $(this).attr("idd")+";";
            baseValue +=$(this).val()+";";
        });
        $('input[name="tsFw"]:checked').each(function(){
            pkId += $(this).attr("idd")+";";
            baseValue +=$(this).val()+";";
        });
        $("#sergPkId").val(pkId);
        if(iecs(baseValue)){
            showMsg("请选择服务内容");
            return;
        }
        vo = uiToData("form_vo",vo);
        vo["sergPkValue"] = baseValue;
        vo["sergObjectId"]=$("#sergObjectId").val();
        vo["sergPkId"]=pkId;
        var method = "add";
        if($("#id").val() != ""){
            method = "modify";
        };
        doAjaxPost('<%=request.getContextPath()%>/appSerGroup.action?act='+method,{strJson : JSON.stringify(vo)},callDataSave);
    }

    function callDataSave(data){
        if(data.msg == 'true'){
            var url = '<%=request.getContextPath()%>/appSerGroup.action?act=forList';
            defualtHref(url);
        }else{
            showMsg(data.msg);
        }
    }
    function findNum(){
        var value = $("#sergValue").val();
        if(necs(value)){
            doAjaxPost('<%=request.getContextPath()%>/appSerGroup.action?act=findCmmValue',{value : value},function(data){
                if(data.msg=="true"){
                    showMsg("该编号已存在");
                    $("#value").val("1");
                }else{
                    $("#value").val("0");
                }
            });
        }
    }
  function ChangeDateToUser(){
      $('input[name="changeHospDept"]').each(function(){
          var strs = $(this).parent().parent().find("td[pofield=id]").text();
          if($("#sergObjectId").val()==strs){
              $(this).attr("checked", true);
          }
      });
  }
</script>
</body>
</html>
