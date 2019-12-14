<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<body class="gray-bg">
<div class="modal fade" id="myModalHospDept" tabindex="-1" role="dialog" aria-labelledby="myModalLabelHospDept" aria-hidden="true">
    <div class="modal-dialog" style="width: 850px;">
        <div class="modal-content">
            <div class="modal-header">
                <input type="hidden" id="serId">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabelHospDept">
                    选择医院
                </h4>
            </div>
            <div class="modal-body">
                <table id="mytableHospDept" class="table table-striped  table-hover table-left">
                    <tbody id="tbHospDept">
                    </tbody>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
                <button type="button" onclick="chageHospDept()" class="btn btn-primary" data-dismiss="modal">
                    确定
                </button>
                <button type="button" onclick="openAllHosp()" class="btn btn-primary" data-dismiss="modal">
                    全部开放
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
        d.setAttribute("type","checkbox");
        d.setAttribute("name","changeHospDept");
        d.setAttribute("id","changeHospDept");
        d.setAttribute("value",data.id);
        ui.find("td[pofield=sname]").append(d);
        ui.find("td[pofield=sname]").append(data.hospName);
    }

</script>

<script type="text/javascript">
    //医院
    function findDept(areaCode){
        doAjaxPost('<%=request.getContextPath()%>/apphosp.action?act=findAllCmm',{},function (data) {
            $("#tbHospDept").html("");
            $.each(data.rows, function(k, v) {
                uiFromTmpDept("hosp_tlist",v,k);
            });
            show();
        })
    }
    function show(){
        doAjaxPost('<%=request.getContextPath()%>/appEag.action?act=findHospCmm',{id:$("#serId").val()},function (data) {
            if(data.map!=null){
                if(data.map.hospIds!=null){
                    var hospIds = data.map.hospIds;
                    $("input[name='changeHospDept']").each(function(){
                        if(hospIds.indexOf($(this).attr("value"))!=-1){
                            $(this).attr("checked", true);
                        }
                    });
                }
            }
        })
    }
    function chageHospDept(){
        var chkRadio = "";
        $("input[name='changeHospDept']:checked").each(function(){
            chkRadio += $(this).attr("value")+";";
        });
        doAjaxPost('<%=request.getContextPath()%>/appEag.action?act=openObject',{id:$("#serId").val(),hospIds:chkRadio},function (data) {

        })
    }
    function openAllHosp(){
        doAjaxPost('<%=request.getContextPath()%>/appEag.action?act=openAllCmm',{id:$("#serId").val()},function (data) {

        })
    }
</script>
