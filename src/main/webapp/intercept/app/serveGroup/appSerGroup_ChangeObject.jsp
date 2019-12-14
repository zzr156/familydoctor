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
                    选择服务人群
                </h4>
            </div>
            <div class="modal-body" id="form_qvo">
                <table id="mytableHospDept" class="table table-striped  table-hover table-left">
                    <thead>
                        <tr>
                            <td>
                                <div class="form-group">
                                    <label class="col-sm-4 control-label">显示条数：</label>
                                    <div class="col-sm-8">
                                        <input type="text" class="form-control" pofield="pageSize"  id="pageSize"  value="15" >
                                    </div>
                                </div>
                            </td>
                            <td>
                                <div class="input-group">
                                    <input id="content" pofield="content" name="content" class="form-control">
                                    <span class="input-group-btn">
                                        <button type="button" class="btn btn-primary" onclick="findDept();">查询</button>
                                    </span>
                                </div>
                            </td>

                        </tr>
                        <tr>
                            <td>服务人群</td>
                            <td>是否是基本公共卫生服务人群</td>
                        </tr>
                        <input type="hidden" pofield="itemCount" id="itemCount" value="">
                        <input type="hidden" pofield="pageCount" id="pageCount" value="">
                        <input type="hidden" pofield="pageStartNo" id="pageStartNo" value="1">
                    </thead>
                    <tbody id="tbHospDept">

                    </tbody>
                </table>
            </div>
            <div class="text-center" style="background: #fff; padding-top: 5px;">
                <button id="previous" type="button" class="btn btn-sm" onclick="Previous();findDept();">前一页</button>
                <span id="qvodesc">1/1每页显示:10条,共有1条</span>&nbsp;&nbsp;
                <button id="next" type="button" class="btn btn-sm" onclick="Next();findDept();">下一页</button>
                <input style="width: 40px" class="span2" id="gotext" type="text">
                <button class="btn btn-sm" onclick="qvoGo();findDept();" type="button">Go!</button>
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
<jsp:include page="/open/mould/app/serveGroup/appServeGroup_change_tmp.jsp" flush="false" />
<script type="text/javascript">
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
        d.setAttribute("type","radio");
        d.setAttribute("name","changeHospDept");
        d.setAttribute("id","changeHospDept");
        d.setAttribute("value",data.seroValue+";;;"+data.seroName+";;;"+data.seroState);
        ui.find("td[pofield=sname]").append(d);
        ui.find("td[pofield=sname]").append(data.seroName);
        ui.find("td[pofield=state]").append(data.stateName);
    }

</script>

<script type="text/javascript">
    var qvo={};
    //医院
    function findDept(){
        qvo = uiToData("form_qvo",qvo);//界面值绑定到变量
        doAjaxPost('<%=request.getContextPath()%>/appSerGroup.action?act=findCmmObject',{qvoJson:JSON.stringify(qvo)},callDataToDept);
    }
    function callDataToDept(data){
        console.log(JSON.stringify(data.qvo));
        dataToUi(data.qvo,"form_qvo");//数据绑定到界面
        qvodesc("qvodesc");
        qvoPN(data.qvo);
        $("#tbHospDept").html("");
        $.each(data.rows, function(k, v) {
            uiFromTmpDept("hosp_tlist",v,k);
        });
        ChangeDateToUser();
    }
    function chageHospDept(){
        var chkRadio = null;
        var objectId = "";
        $("input:radio[name='changeHospDept']:checked").each(function(){
            chkRadio = $(this).attr("value");
            objectId+=$(this).parent().parent().find("td[pofield=id]").text();
        });
        if (chkRadio == null) {
            alert("没有选中项");
            return false;
        } else {
            var chkRadios = chkRadio.split(";;;");
            $('#sergObjectValue').val(chkRadios[0]);
            $('#sergObjectTitle').val(chkRadios[1]);
            $("#sergObjectType").val(chkRadios[2]);
            $("#sergObjectId").val(objectId);
        }
    }
</script>
