<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<head>
    <%@ include file="/open/commonjs/tldHtml.jsp"%>
    <%@ include file="/open/commonjs/roleson.jsp"%>
</head>
<body class="gray-bg">
<div class="modal fade" id="myModalHospDept" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" style="width: 850px;">
            <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title">
                    选择医保项目
                </h4>
            </div>
            <div class="modal-body" >
                <table id="mytableHospDept" class="table table-striped  table-hover table-left">
                    <thead>
                    <tr>
                        <td>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">显示条数：</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control" pofield="pageSize"  id="pageSize"  value="15" >
                                </div>
                                <label class="col-sm-3 control-label">选择输入医保项目：</label>
                                <div class="col-sm-4">
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="hcProjectName" name="hcProjectName" pofield="hcProjectName"/>
                                        <span class="input-group-btn">
                                            <button type="button" class="btn btn-primary" onclick="findDept()">查询</button>
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
            <div class="row" id="form_qvo">
                <div class="col-sm-12">
                    <div class="ibox float-e-margins">
                        <div class="ibox-content tabble-c">
                            <div class="table-responsive">
                                <table class="table table-striped" id="drUserTable">
                                    <thead>
                                    <tr>
                                        <th style="text-align:center;">
                                            <input type="checkbox" id="selectAll"style="width:18px;height:18px" disabled="disabled"/>
                                        </th>
                                        <th style="text-align: center;">序号</th>
                                        <th style="text-align: center;">项目编号</th>
                                        <th style="text-align: center;">是否有效</th>
                                        <th style="text-align: center;">是否医保项目</th>
                                        <th style="text-align: center;">收费金额</th>
                                    </tr>
                                    </thead>
                                    <tbody id="drUser_list">

                                    </tbody>
                                </table>
                                <input type="hidden" pofield="itemCount" id="itemCount" value="">
                                <input type="hidden" pofield="pageCount" id="pageCount" value="">
                                <input type="hidden" pofield="pageStartNo" id="pageStartNo" value="1">
                            </div>
                            <!--分页按钮-->
                            <div class="text-center" style="background: #fff; padding-top: 5px;">
                                <button id="previous" type="button" class="btn btn-sm" onclick="Previous();findDept();">前一页</button>
                                <span id="qvodesc">1/1每页显示:15条,共有1条</span>&nbsp;&nbsp;
                                <button id="next" type="button" class="btn btn-sm" onclick="Next();findDept();">下一页</button>
                                <input style="width: 40px" class="span2" id="gotext" type="text">
                                <button class="btn btn-sm" onclick="qvoGo();findDept();" type="button">Go!</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="modal-footer">
                <button type="button" id="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" onclick="submit()" class="btn btn-primary" data-dismiss="modal">提交</button>
            </div>
        </div>
    </div>
</div>
<script type="tmp/xmgrid" id="tlist">
    <tr style="display:none;text-align: center">
        <td pofield="projectId" style="text-align:center;"><input style="width:18px;height:18px" type="checkbox" name="chckBox"/></td>
        <td pofield="num"></td>
        <td pofield="projectName"></td>
        <td pofield="available"></td>
        <td pofield="medicalInsuranceProject"></td>
        <td pofield="chargeAmount"></td>
    </tr>
</script>
<script type="text/javascript">

    function uiFromTmp(id, data,idx) {
        var ui = $($("#" + id + "").html());
        ui.data("vo", data);
        ui.find("td[pofield=projectId] input[name=chckBox]").val(data.projectId+";"+data.projectName+";"+data.chargeAmount);
        ui.find("td[pofield=num]").text(idx+1);
        ui.find("td[pofield=projectName]").text(data.projectName);
        ui.find("td[pofield=available]").text(data.available);
        ui.find("td[pofield=medicalInsuranceProject]").text(data.medicalInsuranceProject);
        ui.find("td[pofield=chargeAmount]").text(data.chargeAmount);
        $("#drUser_list").append(ui);
        ui.fadeIn();
        $(':checkbox[name=chckBox]').each(function(){
            $(this).click(function(){
                if($(this).attr('checked')){
                    $(':checkbox[name=chckBox]').removeAttr('checked');
                    $(this).attr('checked','checked');
                }
            });
        });
    }
</script>
<script type="text/javascript">
    var qvo = {};
    function findDept() {
        <!--console.log($("input[name='hcProjectName']").val());-->
        qvo["projectName"] = $("input[name='hcProjectName']").val();
        qvo["pageSize"] = $("#pageSize").val();
        qvo["itemCount"] = $("#itemCount").val();
        qvo["pageCount"] = $("#pageCount").val();
        qvo["pageStartNo"] = $("#pageStartNo").val();
        doAjaxPost('registerAction.action?act=getHcProjectList', {strJson:JSON.stringify(qvo)}, callDataToUi);
    }
    function callDataToUi(data){
        dataToUi(data.qvo,"form_qvo");//数据绑定到界面
        qvodesc("qvodesc");
        qvoPN(data.qvo);
        $("#drUserTable tbody").html("");
        if(data.rows != null){
            $.each(data.rows, function(k, v) {
                uiFromTmp("tlist", v,k);
            });
        }
    }
    function submit(){
        var value="";
        $.each($('input:checkbox'),function(){
            if(this.checked){
                value = $(this).val().split(";");
                $("#govHcProjectId").val(value[0]);
                $("#govHcProjectName").val(value[1]);
                $("#govMoney").val(value[2]);
                $("#govMoney").attr("disabled","disabled");
                $("#myModalHospDept").modal('hide');
            }
        });
        if(""==value){
            alert("请选择一个项目！");
        }
    }
</script>
