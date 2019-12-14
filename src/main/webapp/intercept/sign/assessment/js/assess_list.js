/**
 * 定义全局变量
 */
var vo = {};
var qvo = {};
var vos = {};
var table;
var laydate;
var numberCount = 0;
var queryNotAssess = 0;
var isFirstClick = true;

/**
 * 页面内容加载完成后立即执行
 */
$(function () {

    Initialization();// 初始化配置参数
    //assessmentRemind32();// 32项目每月考核到期前一周提醒（性能问题，暂时关闭）
    assessRemind();// 考核任务到期前一个月提醒
    initTeam();// 初始化团队下拉框
    initCommittee();// 初始化村、居委会

    layui.use('table', function () {
        table = layui.table;
        table.on('tool(sign)', function (obj) {
            var rowData = obj.data;
            if (obj.event == 'look') {// 查看
                look(rowData);
            } else if (obj.event == 'assess') {
                assess(rowData);// 考核
            } else if (obj.event == 'resitAssess') {
                resitAssess(rowData);// 补考
            }
        });
        findList();// 查询列表
    });
});

/**
 * 初始化团队下拉框
 */
function initTeam() {
    doAjaxPost('signAction.action?act=findTeam', {id: orgid}, function (data) {
        if (data.map != null) {
            if (data.map.teamList != null) {// 渲染团队下拉框
                $("#teamId").empty();
                var option = document.createElement('option');
                option.setAttribute("value", "");
                option.innerText = "---请选择---";
                document.getElementById("teamId").appendChild(option);
                $.each(data.map.teamList, function (k, v) {
                    dataUiCodeGroup("select", "teamId", v.teamName, v.id);
                });
            }
        }
    });
}

/**
 * 根据团队查询签约医生
 */
function addDr() {
    var options = $("#teamId option:selected");
    var value = $("#teamId").val();
    doAjaxPost('signAction.action?act=findMem', {id: value}, function (data) {
        if (data != null) {
            if (data.rows != null) {
                $("#drId").empty();
                var option = document.createElement('option');
                option.setAttribute("value", "");
                option.innerText = "---请选择---";
                document.getElementById("drId").appendChild(option);
                $.each(data.rows, function (k, v) {
                    dataUiCodeGroup("select", "drId", v.memDrName, v.memDrId);
                });
            }
        }
    });
}

/**
 * 初始化村、居委会
 */
function initCommittee() {
    doAjaxPost('signAction.action?act=findCommitteeByAreaCode', {hospAreaCode: HospAreaCode}, function (data) {
        if (data.map != null) {
            if (data.map.committeeList != null) {// 渲染村、居委会下拉框
                $("#committee").empty();
                var option = document.createElement('option');
                option.setAttribute("value", "");
                option.innerText = "---请选择---";
                document.getElementById("committee").appendChild(option);
                $.each(data.map.committeeList, function (k, v) {
                    dataUiCodeGroup("select", "committee", v.areaSname, v.id);
                });
            }
        }
    });
}

/**
 * 查询列表
 */
function findList() {
    var index = layer.load(1);// 打开遮罩
    qvo = uiToData("form_qvo", qvo);// 界面查询值绑定到变量
    qvo.hospId = orgid;// 机构ID
    if (queryNotAssess == 1) {// 弹框-查看详情
        qvo.isAssess = "3";// 查询协议到期前一个月还未进行考核的人员
        queryNotAssess++;
    }
    table.render({
        height: '485',
        elem: '#signTabel',
        cols: [[
            {field: 'myIndex', title: '序号', width: 60, fixed: true},
            {field: 'name', title: '姓名', width: 60},
            {field: 'signNum', title: '签约编号', width: 140},
            {field: 'sex', title: '性别', width: 60},
            {field: 'age', title: '年龄', width: 60},
            {field: 'idno', title: '身份证号', width: 140},
            {field: 'groupName', title: '所属人群', width: 90},
            {field: 'signDate', title: '签约时间', width: 90, sort: true},
            {field: 'isExpire', title: '已到期', width: 90},
            {field: 'isFinish', title: '已考核完成', width: 90},
            {field: 'totalScore', title: '当前得分', width: 90},
            {field: 'rating', title: '当前评级', width: 90},
            {field: 'isReview', title: '已审核', width: 90},
            {fixed: 'right', width: 150, align: 'center', toolbar: '#barRole'}
        ]],
        id: 'sign',
        url: 'assessmentAction.action',
        where: {act: 'assessList', strJson: JSON.stringify(qvo)},
        method: 'post',
        skin: 'row',// 表格风格
        even: true,
        page: true,// 是否显示分页栏
        limits: [5, 10, 25, 50, 100, 500, 1000, 3000, 5000],
        limit: 10, // 每页默认显示的行数
        loadingX: true,
        done: function (res, curr, count) {
            layer.close(index);// 关闭遮罩
            if (res.data != null) {
                vos = res.data;
                if (vos != null) {
                    numberCount = res.data.length;
                }
            }
        }
    });
}

/**
 * 查看
 */
function look(rowData) {
    var url = encodeURI(encodeURI("audit_look.jsp?assessmentId=" + rowData.assessmentId +
        "&name=" + rowData.name + "&sex=" + rowData.sex + "&signId=" + rowData.signId +
        "&patientId=" + rowData.patientId + "&age=" + rowData.age + "&signDate=" + rowData.signDate +
        "&totalScore=" + rowData.totalScore + "&operate=assessLook"));
    if (rowData.rating != undefined && rowData.rating != "") {
        var title = "姓名: " + rowData.name + "  性别: " + rowData.sex + "  年龄: " + rowData.age +
            "  签约日期: " + rowData.signDate + "  当前得分: " + (rowData.totalScore == undefined ? 0 : rowData.totalScore);
        var initWidth = document.body.clientWidth + "px";
        var initHeight = document.body.clientHeight + "px";
        var index = layer.open({
            type: 2, // 2代表iframe
            title: title,// 窗口标题
            shade: 0,// 阴影效果
            area: [initWidth, initHeight],
            content: url// 页面URL
        });
        layer.full(index);// 打开窗口时默认最大化
    } else {
        layer.msg("该居民还未考核，无法查看考核信息！");
    }
}

/**
 * 考核
 */
function assess(rowData) {
    if (isFirstClick) {
        isFirstClick = false;
        var url = encodeURI(encodeURI("assess_assess.jsp?assessmentId=" + rowData.assessmentId +
            "&name=" + rowData.name + "&sex=" + rowData.sex + "&signId=" + rowData.signId + "&idno=" + rowData.idno +
            "&patientId=" + rowData.patientId + "&age=" + rowData.age + "&signDate=" + rowData.signDate +
            "&totalScore=" + rowData.totalScore + "&drId=" + rowData.drId + "&teamId=" + rowData.teamId +
            "&signFromDate=" + rowData.signFromDate + "&signToDate=" + rowData.signToDate +
            "&groupName=" + rowData.groupName + "&isFinish=" + rowData.isFinish + "&operate=assess"));
        var title = "姓名: " + rowData.name + "  性别: " + rowData.sex + "  年龄: " + rowData.age +
            "  签约日期: " + rowData.signDate + "  当前得分: <span id='windowScore'>" + (rowData.totalScore == undefined ? 0 : rowData.totalScore) + "</span>";
        var initWidth = document.body.clientWidth + "px";
        var initHeight = document.body.clientHeight + "px";
        var index = layer.open({
            type: 2, // 2代表iframe
            title: title,// 窗口标题
            shade: 0,// 阴影效果
            area: [initWidth, initHeight],
            content: url,// 页面URL
            end: function () {// 关闭窗口事件
                findList();// 刷新列表
            }
        });
        layer.full(index);// 打开窗口时默认最大化
        setTimeout(function () {
            isFirstClick = true;
        }, 1000);
    }
}

/**
 * 补考核
 */
function resitAssess(rowData) {
    if (isFirstClick) {
        isFirstClick = false;
        var url = encodeURI(encodeURI("assess_assess.jsp?assessmentId=" + rowData.assessmentId +
            "&name=" + rowData.name + "&sex=" + rowData.sex + "&signId=" + rowData.signId + "&idno=" + rowData.idno +
            "&patientId=" + rowData.patientId + "&age=" + rowData.age + "&signDate=" + rowData.signDate +
            "&totalScore=" + rowData.totalScore + "&drId=" + rowData.drId + "&teamId=" + rowData.teamId +
            "&signFromDate=" + rowData.signFromDate + "&signToDate=" + rowData.signToDate +
            "&groupName=" + rowData.groupName + "&isFinish=" + rowData.isFinish + "&operate=resitAssess"));
        var title = "姓名: " + rowData.name + "  性别: " + rowData.sex + "  年龄: " + rowData.age +
            "  签约日期: " + rowData.signDate + "  当前得分: <span id='windowScore'>" + (rowData.totalScore == undefined ? 0 : rowData.totalScore) + "</span>";
        var initWidth = document.body.clientWidth + "px";
        var initHeight = document.body.clientHeight + "px";
        var index = layer.open({
            type: 2, // 2代表iframe
            title: title,// 窗口标题
            shade: 0,// 阴影效果
            area: [initWidth, initHeight],
            content: url,// 页面URL
            end: function () {// 关闭窗口事件
                findList();// 刷新列表
            }
        });
        layer.full(index);// 打开窗口时默认最大化
        setTimeout(function () {
            isFirstClick = true;
        }, 1000);
    }
}

/**
 * 重置
 */
function reset() {
    $("#teamId").val("");// 团队名称
    $("#drId").val("");// 签约医生
    $("#group").val("");// 服务人群
    $("#economics").val("");// 经济类型
    $("#patientName").val("");// 居民姓名
    $("#patientIdNo").val("");// 居民身份证号
    $("#signDateStart").val("");// 签约日期开始
    $("#signDateEnd").val("");// 签约日期结束
    $("#signFromDateStart").val("");// 协议开始日期开始
    $("#signFromDateEnd").val("");// 协议开始日期结束
    $("#signToDateStart").val("");// 协议截止日期开始
    $("#signToDateEnd").val("");// 协议截止日期结束
    $("#isAssess").val("");// 是否考核
    $("#history").val("0");// 是否显示历史记录
    $("#committee").val("");// 考核评级
    $("#rating").val("");// 考核评级
    $("#state").val("");// 状态
}

/**
 * 考核任务到期前一个月提醒
 */
function assessRemind() {
    var vo = {};
    vo.hospId = orgid;
    doAjaxPost('assessmentAction.action?act=findNotAssess', {strJson: JSON.stringify(vo)}, function (data) {
        var notAssessNum = data.total;
        if (notAssessNum > 0) {
            layer.open({
                type: 1,
                title: false,
                closeBtn: false,
                area: '360px',// 弹框宽度
                offset: [($(window).height() / 2 - 180), ($(window).width() / 2 - 180)],// 弹框位置
                shade: 0.3,
                id: 'layer_open_one',
                btn: ['查看详情', '取消'],
                btnAlign: 'c',
                moveType: 1,
                content: '<div style="padding: 50px; line-height: 22px; background-color: #393D49;' +
                'color: #fff; font-weight: 100;text-align : center;">' + '到期前一个月还有 ' + notAssessNum + ' 人未考核！</div>',
                btn1: function () {
                    layer.closeAll();
                    // 设置参数并调用查询方法
                    queryNotAssess++;
                    findList();// 查询
                },
                btn2: function () {
                    layer.closeAll();
                }
            });
        }
    });
}

/**
 * 32项目每月考核到期前一周提醒
 */
function assessmentRemind32() {
    var date = new Date(GetDateStr(7));//是否为本月最后一周
    if (date.getMonth() + 1 <= new Date().getMonth() + 1) {
        return false;
    }

    var vo = {};
    vo.hospId = orgid;
    doAjaxPost('assessmentAction.action?act=findNotAssessDetail', {strJson: JSON.stringify(vo)}, function (data) {
        var patients = data.vo;
        if (patients != null && patients.length > 0) {
            layer.open({
                type: 1,
                title: false,
                closeBtn: false,
                area: '360px;',// 弹框宽度
                offset: [150, ($(window).width() / 2 - 180)],// 弹框位置
                shade: 0.3,
                id: 'layer_open_two',
                btn: ['确定'],
                btnAlign: 'c',
                moveType: 1,
                content: '<div style="padding: 50px; line-height: 22px; background-color: #393D49;' +
                'color: #fff; font-weight: 100;text-align : center;">还有 ' + patients.length + ' 人未上传每月社区服务凭证！</div>',
                btn1: function () {
                    layer.closeAll();
                }
            });
        }
    });
}

/**
 * 修改是否考核
 */
function changeIsAssess() {
    if ($("#state").val() != "" || $("#rating").val() != "") {
        $("#isAssess").val("1");// 是否已考核
    }
}
