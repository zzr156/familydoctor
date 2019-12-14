/**
 * 健康评估报告居民列表核心js
 * WangCheng
 */

var vo = {};
var qvo = {};
var vos = {};
var allTeamId = [];
var table;
var laydate;
var numberCount = 0;

$(function () {
    //查询当前机构团队
    doAjaxPost('signAction.action?act=findTeam', {id: orgid}, initBack);
});

function initBack(data) {
    $("#signHospId").val(orgid);
    $("#drHospName").val(orgid);
    if (data.map != null) {
        //团队
        if (data.map.teamList != null) {
            $("#teamId").html("");
            var option = document.createElement('option');
            option.setAttribute("value", "");
            option.innerText = "--全部--";
            document.getElementById("teamId").appendChild(option);
            $.each(data.map.teamList, function (k, v) {
                dataUiCodeGroup("select", "teamId", v.teamName, v.id);
            });
        }
    }
    layui.use('table', function () {
        table = layui.table;

        table.on('tool(sign)', function (obj) {
            var ui = obj.data; //获得当前行数据
            if (obj.event == 'lookReport') { //查看报告
                lookReport(ui);
            } else if (obj.event == "exportReport") {//导出报告
                exportReport(ui);
            } else if (obj.event == "createReport") {//生成报告
                createReport(ui);
            } else if (obj.event == "modifyReport") {//修改报告
                modifyReport(ui);
            }
        });
    });
}

//下拉框签约医生
function addDr() {
    var options = $("#teamId option:selected");
    var value = $("#teamId").val();
    doAjaxPost('signAction.action?act=findMem', {id: value}, findMenBack);
}

//医生
function findMenBack(data) {
    if (data != null) {
        if (data.rows != null) {
            $("#drId").html("");
            var option = document.createElement('option');
            option.setAttribute("value", "");
            option.innerText = "--全部--";
            document.getElementById("drId").appendChild(option);
            $.each(data.rows, function (k, v) {
                dataUiCodeGroup("select", "drId", v.memDrName, v.memDrId);
            });
            $("#signDrAssistantId").html("");
            var option = document.createElement('option');
            option.setAttribute("value", "");
            option.innerText = "--全部--";
            document.getElementById("signDrAssistantId").appendChild(option);
            $.each(data.rows, function (k, v) {
                dataUiCodeGroup("select", "signDrAssistantId", v.memDrName, v.memDrId);
            });
        }
    }
}

//查询
function findList() {
    qvo = uiToData("form_qvo", qvo);//界面查询值绑定到变量
    if (HospAreaCode.substr(0, 4) == "3503") {
        ptInitTable();
    } else {
        var index = layer.load(1);
        table.render({
            height: '400'
            , elem: '#signTabel'
            , cols: [[
                {field: 'myIndex', title: '序号', width: 60, fixed: true}
                , {field: 'signNum', title: '签约编号', width: 140, sort: true}
                , {field: 'name', title: '姓名', width: 60, sort: true}
                , {field: 'sex', title: '性别', width: 60, sort: true}
                , {field: 'age', title: '年龄', width: 60, templet: '<div>{{  GetAge(d.patientIdno)}}</div>'}
                , {field: 'patientIdno', title: '身份证', width: 150}
                , {field: 'tel', title: '联系电话', width: 100}
                , {field: 'patientAddress', title: '详细地址', width: 150}
                , {field: 'signPersGroup', title: '服务类型', width: 100}
                , {field: 'signsJjType', title: '人口经济性质', width: 100}
                , {field: 'signlx', title: '医保类型', width: 80}
                , {field: 'signDate', title: '签约时间', width: 90}
                , {field: 'signState', title: '签约状态', width: 120}
                , {field: 'signTeamName', title: '签约团队', width: 80}
                , {field: 'signDrName', title: '签约医生', width: 80}
                , {field: 'batchOperatorName', title: '签约操作人', width: 80}
                , {field: 'signgwpay', title: '公卫补助', width: 80}
                , {field: 'signybpay', title: '医保预支付', width: 80}
                , {field: 'signczpay', title: '财政补贴', width: 80}
                , {field: 'signzfpay', title: '自费', width: 80}
                , {field: 'upHpis', title: '签约来源', width: 80}
                , {fixed: 'right', width: 220, align: 'center', toolbar: '#barRole'}
            ]]
            , id: 'sign'
            , url: 'signAction.action'
            , where: {act: 'findSignXxWeb', strJson: JSON.stringify(qvo)}
            , method: 'post'
            , skin: 'row' //表格风格
            // ,size: 'sm'
            , even: true
            , page: true //是否显示分页
            , limits: [5, 10, 25, 50, 100, 500, 1000, 3000, 5000, 10000, 30000, 50000]
            , limit: 10 //每页默认显示的数量
            , done: function (res, curr, count) {
                if (res.data != null) {
                    vos = res.data;
                    numberCount = res.data.length;
                }
            }
        });
        //遮罩关闭
        layer.close(index);
    }
}

//莆田表格初始化
function ptInitTable() {
    var index = layer.load(1);
    table.render({
        height: '400'
        , elem: '#signTabel'
        , cols: [[
            {field: 'myIndex', title: '序号', width: 60, fixed: true}
            , {field: 'signNum', title: '签约编号', width: 100, sort: true}
            , {field: 'name', title: '姓名', width: 60, sort: true}
            , {field: 'sex', title: '性别', width: 60, sort: true}
            , {field: 'age', title: '年龄', width: 60, templet: '<div>{{  GetAge(d.patientIdno)}}</div>'}
            , {field: 'patientIdno', title: '身份证', width: 150}
            , {field: 'tel', title: '联系电话', width: 100}
            , {field: 'patientAddress', title: '详细地址', width: 150}
            , {field: 'signPersGroup', title: '服务类型', width: 100}
            , {field: 'signsJjType', title: '人口经济性质', width: 100}
            , {field: 'signlx', title: '医保类型', width: 80}
            , {field: 'signDate', title: '签约时间', width: 90}
            , {field: '', title: '签约状态', width: 90, templet: '<div>已签约</div>'}
            , {field: 'signState', title: '医保交互', width: 80}
            , {field: 'signTeamName', title: '签约团队', width: 80}
            , {field: 'signDrName', title: '签约医生', width: 80}
            , {field: 'batchOperatorName', title: '签约操作人', width: 80}
            , {field: 'signgwpay', title: '公卫补助', width: 80}
            , {field: 'signybpay', title: '医保预支付', width: 80}
            , {field: 'signczpay', title: '财政补贴', width: 80}
            , {field: 'signzfpay', title: '自费', width: 80}
            , {field: 'upHpis', title: '签约来源', width: 80}
            , {fixed: 'right', width: 300, align: 'center', toolbar: '#barRole'}
        ]]
        , id: 'sign'
        , url: 'signAction.action'
        , where: {act: 'findSignXxWeb', strJson: JSON.stringify(qvo)}
        , method: 'post'
        , skin: 'row' //表格风格
        , even: true
        , page: true //是否显示分页
        , limits: [5, 10, 25, 50, 100, 500, 1000, 3000, 5000, 10000, 30000, 50000]
        , limit: 10 //每页默认显示的数量
        , done: function (res, curr, count) {
            vos = res.data;
            if (vos != null) {
                numberCount = res.data.length;
                $.each(res.data, function (k, v) {
                    if (v.signState == "预签约") {
                        $("td[data-field='signState'] :eq(" + k + ")").text("未交互");
                    } else if (v.signState == "已签约") {
                        $("td[data-field='signState'] :eq(" + k + ")").text("已交互");
                    }
                })
            }
            //遮罩关闭
            layer.close(index);
        }
    });
}

/**
 * 查看报告
 * @param ui
 */
function lookReport(ui) {
    if (ui.id != null && ui.id != "") {
        vo["patientIdNo"] = ui.patientIdno;
        vo["orgId"] = orgid;
        vo["signLableId"] = ui.id;
        doAjaxPost('signAction.action?act=findHealthReport', {strJson: JSON.stringify(vo)}, function (data) {
            if (data.vo == null) {
                layer.msg("该居民未生成报告，请先生成报告！");
            } else {
                window.location.href = "health_report_look.jsp?id=" + ui.id + "&patientIdno=" + ui.patientIdno + "&lookType=1";
            }
        });
    }
}

/**
 * 生成报告
 * @param ui
 */
function createReport(ui) {
    if (ui.id != null && ui.id != "") {
        vo["doctorId"] = drid;
        doAjaxPost('signAction.action?act=findDrTeam', {strJson: JSON.stringify(vo)}, function (data) {
            debugger
            if(data.rows != null){
                $.each(data.rows,function(k,v){
                    allTeamId.push(v.id);
                });
                if($.inArray(ui.teamId,allTeamId) > -1){//本团队
                    vo["patientIdNo"] = ui.patientIdno;
                    vo["healthReportState"] = "1";
                    doAjaxPost('signAction.action?act=findHealthReport', {strJson: JSON.stringify(vo)}, function (data) {
                        if (data.vo != null) {
                            layer.msg("该居民已经生成报告，请勿重复操作！");
                        } else {
                            window.location.href = "health_report_add.jsp?id=" + ui.id + "&patientName=" + encodeURI(encodeURI(ui.name)) + "&patientGender=" + encodeURI(encodeURI(ui.sex)) + "&patientAge=" + ui.age + "&signToDate=" + encodeURI(encodeURI(ui.signToDate)) + "&signPersGroup=" + encodeURI(encodeURI(ui.signPersGroup)) + "&patientIdno=" + ui.patientIdno +"&healthType=1";
                        }
                    });
                }else {
                    layer.msg("请勿操作其他团队的居民报告！");
                }
            }
        });
    }
}

/**
 * 导出报告
 * @param ui
 */
function exportReport(ui) {
    if (ui.id != null && ui.id != "") {
        vo["doctorId"] = drid;
        doAjaxPost('signAction.action?act=findDrTeam', {strJson: JSON.stringify(vo)}, function (data) {
            if(data.rows != null){
                $.each(data.rows,function(k,v){
                    allTeamId.push(v.id);
                });
                if($.inArray(ui.teamId,allTeamId) > -1){//本团队
                    vo["patientIdNo"] = ui.patientIdno;
                    vo["healthReportState"] = "1";
                    doAjaxPost('signAction.action?act=findHealthReport', {strJson: JSON.stringify(vo)}, function (data) {
                        if (data.vo != null) {
                            //var url = 'signAction.action?act=exportHealthReport&id='+ui.id
                            window.open('signAction.action?act=exportHealthReport&id='+ui.id);
                            //window.open('signAction.action?act=exportHealthReport&id='+ui.id);
                            //window.location.href = url;
                        } else {
                            layer.msg("该居民未生成报告，请先生成报告！");
                        }
                    });
                }else {
                    layer.msg("请勿操作其他团队的居民报告！");
                }
            }
        });
    }
}

/**
 * 修改报告
 * @param ui
 */
function modifyReport(ui) {
    if (ui.id != null && ui.id != "") {
        vo["doctorId"] = drid;
        doAjaxPost('signAction.action?act=findDrTeam', {strJson: JSON.stringify(vo)}, function (data) {
            if(data.rows != null){
                $.each(data.rows,function(k,v){
                    allTeamId.push(v.id);
                });
                if($.inArray(ui.teamId,allTeamId) > -1){//本团队
                    window.location.href = "health_report_modify.jsp?id=" + ui.id + "&patientName=" + encodeURI(encodeURI(ui.name)) + "&patientGender=" + encodeURI(encodeURI(ui.sex)) + "&patientAge=" + ui.age + "&signToDate=" + encodeURI(encodeURI(ui.signToDate)) + "&signPersGroup=" + encodeURI(encodeURI(ui.signPersGroup)) + "&patientIdno=" + ui.patientIdno;
                }else {
                    layer.msg("请勿操作其他团队的居民报告！");
                }
            }
        });
    }
}

/**
 * 重置
 */
function signsxcz(){
    $("#patientName").val("");
    $("#patientIdno").val("");
    $("#signDateStart").val("");
    $("#signDateEnd").val("");
    $("#signFromDateStart").val("");
    $("#signFromDateEnd").val("");
    $("#signToDateStart").val("");
    $("#signToDateEnd").val("");
    $("#teamId").val("");
    $("#drId").val("");
    $("#signDrAssistantId").val("");

    var persGroup = document.getElementsByName("persGroup");
    for (var i = 0; i < persGroup.length; i++) {
        if (persGroup[i].checked) {
            persGroup[i].checked = false;
        }
    }
}