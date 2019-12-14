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
                    选择人员
                </h4>
            </div>
            <div class="modal-body">
                <table id="mytableHospDept" class="table table-striped  table-hover table-left">
                    <thead>
                    <tr>
                        <td>人员姓名：</td>
                        <td><input id="patientName" pofield="patientName" class="form-control"></td>
                        <td>每页显示条数：</td>
                        <td><input id="pageSize" pofield="pageSize" class="form-control" value="5"></td>
                        <input type="hidden" pofield="itemCount" id="itemCount" value="">
                        <input type="hidden" pofield="pageCount" id="pageCount" value="">
                        <input type="hidden" pofield="pageStartNo" id="pageStartNo" value="1">
                        <td><a class="btn btn-primary" onclick="findPeople();">查询</a></td>
                    </tr>
                    <tr>
                        <th style="text-align:center;"><input type="checkbox" id="allSelect" style="width:22px;height:22px" onclick="allSelect()"/></th>
                        <td>序号</td>
                        <td>姓名</td>
                        <td>性别</td>
                        <td>年龄</td>
                        <td>签约机构</td>
                        <td>签约医生</td>
                        <td>签约时间</td>
                    </tr>
                    </thead>
                    <tbody id="tbHospDept">
                    </tbody>
                </table>
            </div>
            <div class="text-center" style="background: #fff; padding-top: 5px;" id="fy">
                <button type="button" class="btn btn-sm" onclick="Previous();findPeople();">前一页</button>
                <span id="qvodesc">1/1每页显示:15条,共有1条</span>&nbsp;&nbsp;
                <button type="button" class="btn btn-sm" onclick="Next();findPeople();">下一页</button>
                <input style="width: 40px" class="span2" id="gotext" type="text">
                <button class="btn btn-sm" onclick="qvoGo();findPeople();" type="button">Go!</button>
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
<jsp:include page="/open/mould/news/newsTable/news_table_tmp.jsp" flush="false" />
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
        ui.find("td[pofield=userId] input[name=chckBoxx]").val(data.id);
        ui.find("td[pofield=num]").text(idx+1);
        ui.find("td[pofield=patientName]").text(data.patientName);
        if(data.sex==1){
            ui.find("td[pofield=sex]").text("男");
        }else if(data.sex==2){
            ui.find("td[pofield=sex]").text("女");
        }

        ui.find("td[pofield=age]").text(data.age);
        ui.find("td[pofield =orgName]").text(data.signOrg);
        ui.find("td[pofield=drName]").text(data.signDr);
        ui.find("td[pofield=signTime]").text(data.signTime);
    }

</script>

<script type="text/javascript">

    function findPeople(){
        var qqvo = {};
        qqvo["patientName"]=$("#patientName").val();
        qqvo["pageSize"]=$("#pageSize").val();
        qqvo["itemCount"]=$("#itemCount").val();
        qqvo["pageCount"]=$("#pageCount").val();
        qqvo["pageStartNo"]=$("#pageStartNo").val();
        doAjaxPost('<%=request.getContextPath()%>/newsTable.action?act=findPeopleCmm',{qvoJson:JSON.stringify(qqvo)},function(data){
            $("#pageSize").val(data.qvo.pageSize);
            $("#itemCount").val(data.qvo.itemCount);
            $("#pageCount").val(data.qvo.pageCount);
            $("#pageStartNo").val(data.qvo.pageStartNo);
            $("#qvodesc").text(data.qvo.descs);
            $("#tbHospDept").html("");
            $.each(data.rows, function(k, v) {
                uiFromTmpDept("hosp_tlist",v,k);
            });
        });
    }
    function allSelect(){
        if($("#allSelect").prop("checked")){
            $.each($("#tbHospDept").find("input[name=chckBoxx]"),function(k,v){
                $(this).prop("checked",true);
            });
        }else{
            $.each($("#tbHospDept").find("input[name=chckBoxx]"),function(k,v){
                $(this).prop("checked",false);
            });
        }
    }
    function chageHospDept(){
        var ids="";
        var names="";
        $.each($("#tbHospDept").find("input[name=chckBoxx]"),function(k,v){
            if($(this).prop("checked")){
                if(ids!=""){
                    ids+=";";
                    names+=",";
                }
                ids+=$(this).val();
                names+=$(this).closest('tr').data("vo").patientName
            }
        });

        if (ids == "") {
            alert("没有选中项");
            return false;
        } else {
            $('#tablePeopleList').val(ids);
            $('#tablePeopleName').val(names);
        }
    }
</script>
