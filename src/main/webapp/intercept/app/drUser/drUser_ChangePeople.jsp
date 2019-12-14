<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<body class="gray-bg">
<div class="modal fade" id="myModalHospDept" tabindex="-1" role="dialog" aria-labelledby="myModalLabelHospDept" aria-hidden="true">
    <div class="modal-dialog" style="width: 850px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabelHospDept">
                    选择医院
                </h4>
            </div>
            <div class="modal-body">
                <table id="mytableHospDept" class="table table-striped  table-hover table-left">
                    <thead>
                        <tr>
                            <td>选择医院所在区域：</td>
                            <td><select id="pro" class="form-control" onchange="findAddress('city',$('#pro option:selected').val())"><option value="">--请选择省--</option></select></td>
                            <td><select id="city" class="form-control" onchange="findAddress('area',$('#city option:selected').val())"><option value="">--请选择市--</option></select></td>
                            <td><select id="area" class="form-control" onchange="findAddress('street',$('#area option:selected').val())"><option value="">--请选择区--</option></select></td>
                            <td><select id="street" class="form-control"><option value="">--请选择街道--</option></select></td>
                            <td><a class="btn btn-primary" onclick="findTable();">查询</a></td>
                        </tr>
                        <input type="hidden" pofield="pageSize" id="pageSize" value="10">
                        <input type="hidden" pofield="itemCount" id="itemCount" value="">
                        <input type="hidden" pofield="pageCount" id="pageCount" value="">
                        <input type="hidden" pofield="pageStartNo" id="pageStartNo" value="1">
                    </thead>
                    <tbody id="tbHospDept">
                    </tbody>
                </table>
            </div>
            <div class="text-center" style="background: #fff; padding-top: 5px;">
                <button id="previous" type="button" class="btn btn-sm" onclick="Previous();findTable();">前一页</button>
                <span id="qvodesc">1/1每页显示:10条,共有1条</span>&nbsp;&nbsp;
                <button id="next" type="button" class="btn btn-sm" onclick="Next();findTable();">下一页</button>
                <input style="width: 40px" class="span2" id="gotext" type="text">
                <button class="btn btn-sm" onclick="qvoGo();findTable();" type="button">Go!</button>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
                <button type="button" onclick="chageHospDept()" class="btn btn-primary" data-dismiss="modal">
                    提交
                </button>
            </div>
        </div>
    </div>
</div>
<jsp:include page="/open/mould/app/drUser/drUser_change_tmp.jsp" flush="false" />
<script type="text/javascript">
    function uiFromTmpDept(id, data,idx) {
        var t = $($("#" + id + "").html());
        dataToUiTmp_idlsttr_rs(t, data,idx);
        $("#tbHospDept").append(t);
        t.fadeIn();
    }
    function dataToUiTmp_idlsttr_rs(ui, data,idx) {
        //alert(JSON.stringify(data));
        ui.data("vo", data);
        ui.find("td[pofield=id]").text(data.id);
        var d=document.createElement("input");
        d.setAttribute("type","radio");
        d.setAttribute("name","changeHospDept");
        d.setAttribute("id","changeHospDept");
        d.setAttribute("value",data.id+";;;"+data.hospName);
        ui.find("td[pofield=sname]").append(d);
        ui.find("td[pofield=sname]").append(data.hospName);
    }

</script>

<script type="text/javascript">
    //医院
    function findDept(areaCode){
        var qvo = {};
        qvo["itemCount"]=$("#itemCount").val();
        qvo["pageSize"]=$("#pageSize").val();
        qvo["pageCount"]=$("#pageCount").val();
        qvo["pageStartNo"]=$("#pageStartNo").val();
        qvo["areaCode"]= areaCode;
      //  areaCode = areaCode.substr(0,10);
        doAjaxPost('<%=request.getContextPath()%>/apphosp.action?act=findCmmByAreaCode',{qvoJson:JSON.stringify(qvo)},callDataToDept);
    }
    function callDataToDept(data){
        $("#itemCount").val(data.qvo.itemCount);
        $("#pageSize").val(data.qvo.pageSize);
        $("#pageCount").val(data.qvo.pageCount);
        $("#pageStartNo").val(data.qvo.pageStartNo);
        qvodesc("qvodesc");
        qvoPN(data.qvo);
        $("#tbHospDept").html("");
        $.each(data.rows, function(k, v) {
            //dataUiCodeGroup("radio","tbHospDept",v.hospName+" ",v.id);
            uiFromTmpDept("hosp_tlist",v,k);
        });
        /*if(data.length=="1"&&data[0].deptType=="0"){
            //$("#sjdwSelect").attr("value",data[0].id);
            $("#deptName").val(data[0].sname);
            $("#deptId").val(data[0].id);
            $("#deptId").attr("disabled",true);
        }
        $.treetable.defaults={
            id_col:0,//ID td列 {从0开始}
            parent_col:1,//父ID td列
            handle_col:2,//加上操作展开操作的 td列
            order_col:4,
            open_img:"open/images/minus.gif",
            close_img:"open/images/plus.gif"
        }
        //$("#tb").treetable({id_col:0,parent_col:1,handle_col:2,open_img:"images/minus.gif",close_img:"images/plus.gif"});
        //只能应用于tbody
        $("#tbHospDept").treetable();
        //应用样式
        $("#tbHospDept tr:even td").addClass("alt");
        $("#tbHospDept tr").find("td:eq(2)").addClass("spec");
        $("#tbHospDept tr:even").find("td:eq(2)").removeClass().addClass("specalt");

        //隐藏数据列
        $("#tbHospDept tr").find("td:eq(0)").hide();
        $("#tbHospDept tr").find("td:eq(1)").hide();
        $("#mytableHospDept tr:eq(0)").find("th:eq(0)").hide();
        $("#mytableHospDept tr:eq(0)").find("th:eq(1)").hide();
*/
       // findTable();
    }

    function chageHospDept(){
        var chkRadio = null;
        $("input:radio[name='changeHospDept']:checked").each(function(){
            chkRadio = $(this).attr("value");
        });
        if (chkRadio == null) {
            alert("没有选中项");
            return false;
        } else {
            var chkRadios = chkRadio.split(";;;");
            $('#drHospId').val(chkRadios[0]);
            $('#drHospName').val(chkRadios[1]);
            findDepartment(chkRadios[0],null);
        }
    }
</script>
