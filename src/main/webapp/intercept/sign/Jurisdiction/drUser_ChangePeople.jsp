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
    <title>签约权限管理</title>
    <%@ include file="/open/commonjs/tldLayui.jsp"%>
</head>
<body bgcolor="white">
<%--查询条件--%>
<div id="form_qvo">
    <div class="mwrap" id="queryForm">
        <div class="layui-form-item">
            <div class="layui-inline">
                <div class="layui-input-inline" id="proid">
                    <select id="pro" name="pro" pofield="pro" disabled="disabled" class="layui-input" onchange="findAddress('city',$('#pro option:selected').val())" >
                        <option value="">--请选择省--</option>
                    </select>
                </div>
            </div>
            <div class="layui-inline">
                <div class="layui-input-inline" id="cityid">
                    <select id="city" name="city" pofield="city" disabled="disabled"  class="layui-input" onchange="findAddress('area',$('#city option:selected').val())" >
                        <option value="">--请选择市--</option>
                    </select>
                </div>
            </div>
            <div class="layui-inline">
                <div class="layui-input-inline" id="areaid">
                    <select id="area" pofield="area"  class="layui-input" >
                        <option value="">--请选择区--</option>
                    </select>
                </div>
            </div>
        </div>
    </div>
    <blockquote class="layui-elem-quote" style="text-align: center" id="blockId">
        <input type="hidden" pofield="pageSize" id="pageSize" value="900">
        <input type="hidden" pofield="itemCount" id="itemCount" value="">
        <input type="hidden" pofield="pageCount" id="pageCount" value="">
        <input type="hidden" pofield="pageStartNo" id="pageStartNo" value="1">
        <button onclick="findTable()" class="layui-btn layui-btn-small">
            <i class=layui-icon>&#xe615;</i>查询
        </button>

        <button onclick="Tograntauthorization()" class="layui-btn layui-btn-small">
            <i class=layui-icon>&#xe615;</i>授权
        </button>
    </blockquote>
    <table class="layui-table">
        <thead>
        <tr>
            <th ><input type="checkbox" id="selectAll"style="width:22px;height:22px" onclick="selectAll()"/>名称</th>
            <%--<th >名称</th>--%>
        </tr>
        </thead>
    <tbody id="tbHospDept">
    </tbody>
    </table>
</div>

<jsp:include page="/intercept/sign/Jurisdiction/drUser_change_tmp.jsp" flush="false" />
</body>
<script type="text/javascript">

    $(function(){
        findProvince();
    });

    function findProvince(){
        var pid=null;
        doAjaxPost('<%=request.getContextPath()%>/address.action?act=findCmmByPid',{pid:pid},function (data) {
            if(data!=null){
                $("#pro").html("");
                var option = document.createElement('option');
                option.setAttribute("value","");
                option.innerText = "--请选择省--";
                document.getElementById("pro").appendChild(option);
                $.each(data, function (k, v) {
                    dataUiCodeGroup("select", "pro", v.areaSname, v.id);
                });
            }
            if(necs(HospAreaCode)){
                var procode = HospAreaCode.substring(0,2)+"0000000000";
                $("#pro").val(procode);
                //市
                 var citycode = HospAreaCode.substring(0,4)+"00000000";
                findAddress("city",$("#pro").val(),citycode);

            }
        });

    }


    function findAddress(id,pid,value){
        doAjaxPost('<%=request.getContextPath()%>/address.action?act=findCmmByPid',{pid : pid},function(data){
            $("#"+id).html("");
            var option = document.createElement('option');
            option.setAttribute("value","");
            if(id=='city'){
                option.innerText = "--请选择市--";
            }
            if(id=='area'){
                option.innerText = "--请选择区--";
            }

            document.getElementById(id).appendChild(option);
            $.each(data,function(k,v){
                dataUiCodeGroup("select",id,v.areaSname,v.id);
            })
            if(value!=""){
                $("#"+id).val(value);
                //查询市底下区县
                //findDept(value,"0");
                //选中市级后 查询街道
                findAddress("area",$("#city").val(),"");
            }
        });
    }

    function uiFromTmpDept(id, data,idx) {
        var t = $($("#" + id + "").html());
        dataToUiTmp_idlsttr_rs(t, data,idx);
        $("#tbHospDept").append(t);
        t.fadeIn();


    }
    function dataToUiTmp_idlsttr_rs(ui, data,idx) {
        ui.data("vo", data);
        ui.find("td[pofield=id]").text(data.id);
        var d=document.createElement("input");
        d.setAttribute("type","checkbox");
        if(data.hospSerState!=undefined){
            if(data.hospSerState!=null){
                if(data.hospSerState=="1"){
                    d.setAttribute("checked",true);
                }
            }
        }
        d.setAttribute("name","changeHospDept");
        d.setAttribute("id","changeHospDept");
        d.setAttribute("value",data.id);
        //d.setAttribute("value",data.id+";;;"+data.hospName);
        ui.find("td[pofield=sname]").append(d);
        ui.find("td[pofield=sname]").append(data.hospName);
    }

</script>

<script type="text/javascript">
     var vo={};
    function findTable(){
    // 市
        if(necs($("#city").val())){
            // 区为空 查询 市
            if(necs($("#area").val())){
                findDept($("#area").val(),"1");
            }else{
                layer.msg("请选择查询的区县",{time:1000});
            }
        }
    }
    //医院
    function findDept(areaCode,value){
        var qvo = {};
        qvo["itemCount"]=$("#itemCount").val();
        qvo["pageSize"]=$("#pageSize").val();
        qvo["pageCount"]=$("#pageCount").val();
        qvo["pageStartNo"]=$("#pageStartNo").val();
        qvo["areaCode"]= areaCode;
        qvo["role"]="4";
        if(value=="0"){
            doAjaxPost('<%=request.getContextPath()%>/apphosp.action?act=findCmmByArea',{qvoJson:JSON.stringify(qvo)},callDataToDept);
        }else{
            doAjaxPost('<%=request.getContextPath()%>/apphosp.action?act=findCmmByAreaCode',{qvoJson:JSON.stringify(qvo)},callDataToDept);
        }
    }
    function callDataToDept(data){
        $("#itemCount").val(data.qvo.itemCount);
        $("#pageSize").val(data.qvo.pageSize);
        $("#pageCount").val(data.qvo.pageCount);
        $("#pageStartNo").val(data.qvo.pageStartNo);
        qvodesc("qvodesc");
        $("#tbHospDept").html("");
        $.each(data.rows, function(k, v) {

            uiFromTmpDept("hosp_tlist",v,k);
        });

    }

    function chageHospDept(){
        var chkRadio = null;
        $("input:radio[name='changeHospDept']:checked").each(function(){
            chkRadio = $(this).attr("value");
        });
        if (chkRadio == null) {
            layer.msg("没有选中项",{time:1000});
            return false;
        } else {
            var chkRadios = chkRadio.split(";;;");
            $('#drHospId').val(chkRadios[0]);
            $('#drHospName').val(chkRadios[1]);
            findDepartment(chkRadios[0],null);
        }
    }

    //全选和全选取消
    function selectAll(){
        if($("#selectAll").prop("checked")){
            $.each($("#tbHospDept").find("input[name=changeHospDept]"),function(k,v){
                $(this).prop("checked",true);
            });
        }else{
            $.each($("#tbHospDept").find("input[name=changeHospDept]"),function(k,v){
                $(this).prop("checked",false);
            });
        }
    }

    //授权
    function Tograntauthorization(){
        var codecked="";
        var codenockde="";
        $.each($("#tbHospDept").find("input[name=changeHospDept]"),function(k,v){

            if($(this).prop("checked")){
                if(codecked!=""){
                    codecked+=";";
                }
                codecked+=$(this).val();
            }else{
                if(codenockde!=""){
                    codenockde+=";";
                }
                codenockde+=$(this).val();
            }
        });
            layer.confirm('确认授权?', {
                btn: ['确定','取消']
            }, function(){
                addauthorization(codecked,codenockde);
            });

    }

    function addauthorization(code,codenockde){

          var type="";
          if(necs($("#area").val())){ // 社区街道进入
              type="0";
          }else{
            type="1";
          }
          vo["areaCode"]=code;
          vo["codenockde"]=codenockde;
          vo["type"]=type;
          vo["areaValue"]=$("#area").val();
          doAjaxPost('<%=request.getContextPath()%>/apphosp.action?act=addauthorization',{strJson:JSON.stringify(vo)},function(data){
            if(data.code=="800"){
                layer.msg("保存成功！");
            }else{
                layer.msg("保存失败！");
            }
          });

    }

</script>
