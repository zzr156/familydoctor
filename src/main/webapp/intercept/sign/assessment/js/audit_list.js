/**
 * 定义全局变量
 */
var vo = {};
var qvo = {};
var vos = {};
var table;
var laydate;
var numberCount = 0;
var recordCount = 0;// 总的记录数

/**
 * 页面内容加载完成后立即执行
 */
$(function () {

    initPrivilege();// 根据用户身份的不同控制按钮是否显示

    layui.use('table', function () {
        table = layui.table;
        table.on('tool(sign)', function (obj) {
            var rowData = obj.data;
            if (obj.event == 'look') {// 查看
                look(rowData);
            } else if (obj.event == 'audit') {// 审核
                audit(rowData);
            }
        });
        //findList();// 查询列表
    });
});

/**
 * 根据用户身份的不同控制按钮是否显示
 */
function initPrivilege() {
    if (HospLevel == "2") {// 市级账号
        initArea(HospAreaCode);
        $("#extract").show();
        $("#random").show();
    } else if (HospLevel == "3") {// 区县级账号
        initArea(HospAreaCode);
        $("#extract").show();
        $("#random").show();
    } else if (HospLevel == "4") {// 乡镇级账号
        initArea(HospAreaCode.substring(0, 6) + "000000");
        var areaCodeElement = $("#areaCode");
        areaCodeElement.val(HospAreaCode);// 设置值为当前用户所在区域
        areaCodeElement.attr("disabled", true);// 设置为不可操作
        areaCodeElement.trigger("change");// 触发onchange事件，初始化机构下拉框
    }
}

/**
 * 初始化地区下拉框
 */
function initArea(areaCode) {
    var vo = {};
    vo.areaCode = areaCode;
    doAjaxPostSync('assessmentAction.action?act=queryCommList', {strJson: JSON.stringify(vo)}, function (data) {
        if (data.rows != null) {
            var areaCodeElement = $("#areaCode");
            areaCodeElement.empty();
            areaCodeElement.append("<option value =''>---请选择---</option>");
            $.each(data.rows, function (k, v) {
                $("#areaCode").append("<option value='" + v.id + "'>" + v.areaSname + "</option>");
            });
        }
    });
}

/**
 * 初始化机构下拉框
 */
function initHosp() {
    $("#teamId").empty();
    $("#drId").empty();

    var value = $("#areaCode").val();
    doAjaxPost('openapphosp.action?act=findCmmByAreaCode', {areaCode: value}, function (data) {
        if (data.rows != null) {
            var hospIdElement = $("#hospId");
            hospIdElement.empty();
            hospIdElement.append("<option value =''>---请选择---</option>");
            $.each(data.rows, function (k, v) {
                $("#hospId").append("<option value='" + v.id + "'>" + v.hospName + "</option>");
            });
        }
    });
}

/**
 * 初始化团队名称下拉框
 */
function initTeam() {
    $("#drId").empty();

    var value = $("#hospId").val();
    doAjaxPost('signAction.action?act=findTeam', {id: value}, function (data) {
        if (data.map != null) {
            if (data.map.teamList != null) {
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
 * 初始化医生下拉框
 */
function initDr() {
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
 * 查询列表
 */
function findList() {
    var index = layer.load(1);// 打开遮罩
    qvo = uiToData("form_qvo", qvo);// 界面查询值绑定到变量
    table.render({
        height: '505',
        elem: '#signTabel',
        cols: [[
            {field: 'myIndex', title: '序号', width: 60, fixed: true},
            {field: 'name', title: '姓名', width: 60},
            {field: 'signNum', title: '签约编号', width: 140},
            {field: 'sex', title: '性别', width: 60},
            {field: 'age', title: '年龄', width: 60},
            {field: 'idno', title: '身份证号', width: 140},
            {field: 'patientTel', title: '联系电话', width: 100},
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
                /*if (parseInt(res.msg) <= 0) {
                    layer.msg("当前机构暂无进行考核过的人！");
                }*/
                recordCount = count;
                vos = res.data;
                numberCount = res.data.length;
            }
        }
    });
}

/**
 * 查看
 */
function look(rowData) {
    var url = encodeURI(encodeURI("audit_look.jsp?assessmentId=" + rowData.assessmentId +
        "&name=" + rowData.name + "&idno=" + rowData.idno + "&signFromDate=" + rowData.signFromDate +
        "&signToDate=" + rowData.signToDate + "&sex=" + rowData.sex + "&signId=" + rowData.signId +
        "&patientId=" + rowData.patientId + "&age=" + rowData.age + "&signDate=" + rowData.signDate +
        "&totalScore=" + rowData.totalScore + "&operate=reviewLook"));// operate:操作类型
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
 * 审核
 */
function audit(rowData) {
    var url = encodeURI(encodeURI("audit_look.jsp?assessmentId=" + rowData.assessmentId +
        "&name=" + rowData.name + "&idno=" + rowData.idno + "&signFromDate=" + rowData.signFromDate +
        "&signToDate=" + rowData.signToDate + "&sex=" + rowData.sex + "&signId=" + rowData.signId +
        "&patientId=" + rowData.patientId + "&age=" + rowData.age + "&signDate=" + rowData.signDate +
        "&totalScore=" + rowData.totalScore));
    if (rowData.rating != undefined && rowData.rating != "") {
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
    } else {
        layer.msg("该居民还未考核，无法进行审核！");
    }

}


/**
 * 重置
 */
function reset() {
    if (HospLevel != "4") {// 乡镇账号
        $("#areaCode").val("");// 地区
    }
    $("#hospId").val("");// 机构
    $("#teamId").val("");// 团队名称
    $("#drId").val("");// 签约医生
    $("#patientName").val("");// 居民姓名
    $("#patientIdNo").val("");// 居民身份证号
    $("#signDateStart").val("");// 签约日期开始
    $("#signDateEnd").val("");// 签约日期结束
    $("#signFromDateStart").val("");// 协议开始日期开始
    $("#signFromDateEnd").val("");// 协议开始日期结束
    $("#signToDateStart").val("");// 协议截止日期开始
    $("#signToDateEnd").val("");// 协议截止日期结束
    $("#group").val("");// 服务人群
    $("#economics").val("");// 经济类型
    $("#isAssess").val("1");// 是否已考核（审核的目标是已考核的数据）
    $("#isReview").val("");// 是否已审核
    $("#rating").val("");// 考核评级
    $("#random").val("");// 随机抽查份数
    $("#history").val("0");// 是否显示历史记录
}

/**
 * 随机抽查
 */
function extract() {
    var random = $("#random").val();
    if (random == "") {
        layer.msg("请输入抽取的份数！");
        return;
    }
    findList();// 查询列表
}

/**
 * 导出当前列表（1-导出当前页数据，2-导出所有页数据）
 */
function exportCurrent(typeNum) {
    var qvo = uiToData("form_qvo", qvo);//界面查询值绑定到变量
    if ($("#rating").val() != "") {
        qvo['isAssess'] = "1";// 如果考核评级不为空，则是否考核必定为是
    }
    window.open("assessmentAction.action?act=exportAuditList&typeNum=" + typeNum + "&strJson=" + JSON.stringify(qvo) + "&numberCount=" + numberCount);
}

/**
 * 修改是否考核
 */
function changeIsAssess() {
    if ($("#isReview").val() != "" || $("#rating").val() != "") {
        $("#isAssess").val("1");// 是否已考核
    }
}