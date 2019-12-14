<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<body class="gray-bg">
<div class="modal fade" id="myModalDrPeople" tabindex="-1" role="dialog" aria-labelledby="myModalLabelDrUser" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabelDrUser">
                    选择成员
                </h4>
            </div>
            <div class="modal-body">
                <table id="mytableDrUser" class="table table-striped  table-hover table-left">
                    <tbody id="tbDrUser">
                    </tbody>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
                <button type="button" onclick="chageDrUser()" class="btn btn-primary" data-dismiss="modal">
                    提交
                </button>

            </div>
        </div>
    </div>
</div>
<jsp:include page="/open/mould/app/team/team_change_tmp.jsp" flush="false" />
<script type="text/javascript">
    function uiFromTmpDrUser(id, data,idx) {
        var t = $($("#" + id + "").html());
        dataToUiTmp_idlsttr_rs(t, data,idx);
        $("#tbDrUser").append(t);
        t.fadeIn();
    }
    function dataToUiTmp_idlsttr_rs(ui, data,idx) {
        //alert(JSON.stringify(data));
        ui.data("vo", data);
        ui.find("td[pofield=id]").text(data.id);
        var d=document.createElement("input");
        d.setAttribute("type","checkbox");
        d.setAttribute("name","changeDrUser");
        d.setAttribute("value",data.id+";;;"+data.drName);
        ui.find("td[pofield=sname]").append(d);
        ui.find("td[pofield=sname]").append(data.drName);
    }

</script>

<script type="text/javascript">
    //医院
    function findDrUser(){
        doAjaxPost('<%=request.getContextPath()%>/appdr.action?act=findCmmList',{},callDataToDrU);
    }
    function callDataToDrU(data){
        alert(JSON.stringify(data));
        $("#tbDrUser").html("");
        $.each(data.rows, function(k, v) {
            uiFromTmpDrUser("drUser_tlist",v,k);
        });
    }

    function chageDrUser(){
        var ids = "";
        var names = "";
        $("input:checkbox[name='changeDrUser']:checked").each(function(){
            var chkRadios = $(this).attr("value").split(";;;");
            if(ids==""){
                ids = chkRadios[0];
            }else{
                ids += ";"+chkRadios[0];
            }
            if(names==""){
                names = chkRadios[1];
            }else{
                names += ";"+chkRadios[1];
            }
        });
        if (ids == "") {
            alert("没有选中项");
            return false;
        } else {
            $('#teamMemIds').val(ids);
            $('#teamMemNames').val(names);
        }
    }
</script>
