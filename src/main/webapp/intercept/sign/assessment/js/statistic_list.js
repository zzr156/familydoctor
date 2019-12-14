/**
 * 定义全局变量
 */
var table;// 数据表格
var drillDownTitle = "";// 下钻路径名称
var historyQvo = [];// 历史查询参数
var historyRes = [];// 历史列表数据
var historyTitle = [];// 历史标题
var currentQvo = "";// 当前查询参数
var currentRes = "";// 当前列表数据
var currentTitle = "";// 当前标题

/**
 * 页面内容加载完成后立即执行
 */
$(function () {

    initPrivilege();// 根据用户身份的不同控制按钮是否显示

    layui.use('table', function () {
        table = layui.table;
        table.on('tool(sign)', function (obj) {
            var rowData = obj.data;
            if (obj.event == 'drillDown') {
                drillDown(rowData);// 下钻
            }
        });
    });
});

/**
 * 根据用户身份的不同控制按钮是否显示
 */
function initPrivilege() {
    if (HospLevel == "2") {// 市级账号
        initArea(HospAreaCode);
    } else if (HospLevel == "3") {// 区县级账号
        initArea(HospAreaCode);
    } else if (HospLevel == "4") {// 乡镇级账号、医院账号、医生账号
        initArea(HospAreaCode.substring(0, 6) + "000000");
        var areaCodeElement = $("#areaCode");
        areaCodeElement.val(HospAreaCode);// 设置值为当前用户所在区域
        areaCodeElement.attr("disabled", true);// 设置为不可操作
        areaCodeElement.trigger("change");// 触发onchange事件，初始化机构下拉框
    }

    if (drRole == "9") {// 医生权限（设置下拉框的值并且不允许选择）
        var areaCode = $("#areaCode").val();
        if (areaCode != "") {
            doAjaxPost('openapphosp.action?act=findCmmByAreaCode', {areaCode: areaCode}, function (data) {
                if (data.rows != null) {
                    var hospIdElement = $("#hospId");
                    hospIdElement.empty();
                    hospIdElement.append("<option value =''>---请选择---</option>");
                    $.each(data.rows, function (k, v) {
                        $("#hospId").append("<option value='" + v.id + "'>" + v.hospName + "</option>");
                    });
                    setTimeout(function () {// 防止出现设置机构不成功的情况
                        hospIdElement.val(orgid);// 设置值为当前用户所在机构
                        hospIdElement.attr("disabled", true);// 设置为不可操作

                        var hospId = $("#hospId").val();
                        if (hospId != "") {
                            doAjaxPost('signAction.action?act=findTeam', {id: hospId}, function (data) {
                                if (data.map != null) {
                                    if (data.map.teamList != null) {
                                        var teamIdElement = $("#teamId");
                                        teamIdElement.empty();
                                        var option = document.createElement('option');
                                        option.setAttribute("value", "");
                                        option.innerText = "---请选择---";
                                        document.getElementById("teamId").appendChild(option);
                                        $.each(data.map.teamList, function (k, v) {
                                            dataUiCodeGroup("select", "teamId", v.teamName, v.id);
                                        });
                                        teamIdElement.val(teamId);// 设置值为当前用户所属团队
                                        teamIdElement.attr("disabled", true);// 设置为不可操作
                                        teamIdElement.trigger("change");// 触发onchange事件，初始化医生下拉框

                                        generateReport();
                                    }
                                }
                            });
                        }
                    }, 200);
                }
            });
        }
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
    $("#hospId").empty();
    $("#hospId").append("<option value =''>---请选择---</option>");
    $("#teamId").empty();
    $("#teamId").append("<option value =''>---请选择---</option>");
    $("#drId").empty();
    $("#drId").append("<option value =''>---请选择---</option>");

    var value = $("#areaCode").val();
    if (value != "") {
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
    } else {
        $("#hospId").empty();
        $("#hospId").append("<option value =''>---请选择---</option>");
        $("#teamId").empty();
        $("#teamId").append("<option value =''>---请选择---</option>");
        $("#drId").empty();
        $("#drId").append("<option value =''>---请选择---</option>");
    }
}

/**
 * 初始化团队名称下拉框
 */
function initTeam() {
    $("#teamId").empty();
    $("#teamId").append("<option value =''>---请选择---</option>");
    $("#drId").empty();
    $("#drId").append("<option value =''>---请选择---</option>");

    var value = $("#hospId").val();
    if (value != "") {
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
    } else {
        $("#teamId").empty();
        $("#teamId").append("<option value =''>---请选择---</option>");
        $("#drId").empty();
        $("#drId").append("<option value =''>---请选择---</option>");
    }
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
 * 生成报表
 */
function generateReport() {
    var qvo = uiToData("form_qvo", qvo);
    var reportTitle = "";
    if (HospLevel == "2" || HospLevel == "3") {// 市级账号或区县级账号
        reportTitle = HospName;
    }
    if ($("#drId").val() != "") {// 医生
        qvo['drId'] = $("#drId").val();
        reportTitle += $("#hospId option:selected").text();
        reportTitle += $("#teamId option:selected").text() + "（团队）";
        reportTitle += $("#drId option:selected").text();
    } else if ($("#teamId").val() != "") {// 团队
        qvo['teamId'] = $("#teamId").val();
        reportTitle += $("#hospId option:selected").text();
        reportTitle += $("#teamId option:selected").text() + "（团队）";
    } else if ($("#hospId").val() != "") {// 机构
        qvo['hospId'] = $("#hospId").val();
        reportTitle += $("#hospId option:selected").text();
    } else if ($("#areaCode").val() != "") {// 地区
        qvo['areaCode'] = $("#areaCode").val();
        reportTitle += $("#areaCode option:selected").text();
    }

    $("#statisticReportTitle").text(reportTitle);// 设置标题
    $("#statisticReportTitle2").css("display", "inline-block");// 设置标题可见
    $("#reportToChartBtnDiv").css("display", "inline-block");// 显示“查看图表”按钮


    drawReport(qvo);// 根据参数查询数据并生成报表
}

/**
 * 下钻
 */
function drillDown(rowData) {
    historyQvo.push(currentQvo);// 保存下钻前的 查询参数
    historyRes.push(currentRes);// 保存下钻前的 列表数据
    historyTitle.push(currentTitle);// 保存下钻前的 标题

    var qvo = uiToData("form_qvo", qvo);
    if (rowData.statisticObjType == "3") {// 团队
        qvo['teamId'] = rowData.statisticObjCode;
    } else if (rowData.statisticObjType == "2") {// 机构
        qvo['hospId'] = rowData.statisticObjCode;
    } else if (rowData.statisticObjType == "1") {// 地区
        qvo['areaCode'] = rowData.statisticObjCode;
    }

    drillDownTitle = "";// 重置
    if (rowData.statisticObjType == "3") {// 有些团队都是数字，不好辨认，所以标识一下
        drillDownTitle += rowData.statisticObjName + "（团队）";
    } else {
        drillDownTitle += rowData.statisticObjName;
    }

    $("#statisticReportTitle").text(currentTitle + drillDownTitle);// 设置标题
    $("#drillUpBtnDiv").css("display", "inline-block");// 显示“返回上级”按钮

    drawReport(qvo);// 根据参数查询数据并生成报表
}

/**
 * 渲染表格
 */
function drawReport(qvo) {
    var index = layer.load(1);// 打开遮罩
    table.render({
        height: '485',
        elem: '#signTabel',
        cols: [[
            {field: 'myIndex', title: '序号', width: 60},
            {field: 'statisticObjName', title: ' - ', width: 160, templet: '#statisticObjNameTpl'},
            {field: 'signNum', title: '签约数', width: 120},
            {field: 'assessNum', title: '已考核数', width: 120},
            {field: 'excellentNum', title: '优', width: 120},
            {field: 'goodNum', title: '良', width: 120},
            {field: 'qualifiedNum', title: '合格', width: 120},
            {field: 'unQualifiedNum', title: '不合格', width: 120},
            {field: 'subsidy', title: '绩效金额', width: 120, templet: '#subsidyTpl'}
        ]],
        id: 'sign',
        url: 'assessmentAction.action',
        where: {act: 'statisticReport', strJson: JSON.stringify(qvo)},
        method: 'post',
        skin: 'row',// 表格风格
        even: true,
        page: false,// 是否显示分页栏
        loadingX: true,
        done: function (res, curr, count) {
            currentQvo = qvo;
            currentRes = res.data;
            currentTitle = $("#statisticReportTitle").text();

            var columnName = "地区/机构/团队/医生";
            if (currentRes.length > 0) {
                if (currentRes[0].statisticObjType == "1") {
                    columnName = "地区";
                } else if (currentRes[0].statisticObjType == "2") {
                    columnName = "机构";
                } else if (currentRes[0].statisticObjType == "3") {
                    columnName = "团队";
                } else if (currentRes[0].statisticObjType == "4") {
                    columnName = "医生";
                }
            }
            $("th div[class*='statisticObjName']").html("<span>" + columnName + "</span>");// 改变表格列名

            layer.close(index);// 关闭遮罩
        }
    });
}

/**
 * 渲染表格（本地数据）
 */
function drawStaticReport(data) {
    table.render({
        height: '485',
        elem: '#signTabel',
        cols: [[
            {field: 'myIndex', title: '序号', width: 60},
            {field: 'statisticObjName', title: ' - ', width: 160, templet: '#statisticObjNameTpl'},
            {field: 'signNum', title: '签约数', width: 120},
            {field: 'assessNum', title: '已考核数', width: 120},
            {field: 'excellentNum', title: '优', width: 120},
            {field: 'goodNum', title: '良', width: 120},
            {field: 'qualifiedNum', title: '合格', width: 120},
            {field: 'unQualifiedNum', title: '不合格', width: 120},
            {field: 'subsidy', title: '绩效金额', width: 120, templet: '#subsidyTpl'}
        ]],
        id: 'sign',
        data: data,
        skin: 'row',// 表格风格
        even: true,
        page: false,// 是否显示分页栏
        loadingX: true,
        done: function (res, curr, count) {
            currentQvo = historyQvo[historyQvo.length - 1];
            currentRes = res.data;
            currentTitle = historyTitle[historyTitle.length - 1];

            var columnName = "地区/机构/团队/医生";
            if (currentRes.length > 0) {
                if (currentRes[0].statisticObjType == "1") {
                    columnName = "地区";
                } else if (currentRes[0].statisticObjType == "2") {
                    columnName = "机构";
                } else if (currentRes[0].statisticObjType == "3") {
                    columnName = "团队";
                } else if (currentRes[0].statisticObjType == "4") {
                    columnName = "医生";
                }
            }
            $("th div[class*='statisticObjName']").html("<span>" + columnName + "</span>");// 改变表格列名
        }
    });
}

/**
 * 导出当前列表
 */
function exportStatisticReport() {
    var reportTitle = encodeURI(encodeURI($("#statisticReportTitle").text()));
    window.open("assessmentAction.action?act=exportStatisticReport&strJson=" + JSON.stringify(currentQvo) + "&reportTitle=" + reportTitle);
}

/**
 * 显示图表（报表数据转图表）
 */
function reportToChart() {
    var title = $("#statisticReportTitle").text() + " 绩效考核图表";
    var url = encodeURI(encodeURI("statistic_chart.jsp"));
    var initWidth = document.body.clientWidth + "px";
    var initHeight = document.body.clientHeight + "px";
    var index = layer.open({
        type: 2, // 2代表iframe
        title: title,// 窗口标题
        shade: 0,// 阴影效果
        area: [initWidth, initHeight],
        content: url,// 页面URL
        end: function () {// 关闭窗口事件

        }
    });
    layer.full(index);// 打开窗口时默认最大化
}

/**
 * 返回上一级
 */
function drillUp() {
    drawStaticReport(historyRes[historyRes.length - 1]);// 通过历史记录渲染报表

    $("#statisticReportTitle").text(historyTitle[historyTitle.length - 1]);// 显示上一级标题

    historyQvo = truncate(historyQvo);// 去除最后一个查询参数元素
    historyRes = truncate(historyRes);// 去除最后一个列表数据元素
    historyTitle = truncate(historyTitle);// 去除最后一个标题元素

    if (historyQvo.length == 0) {// 掩藏“返回上级”按钮
        $("#drillUpBtnDiv").css("display", "none");
    }
}

/**
 * 重置
 */
function reset() {
    if (HospLevel != "4") {// 乡镇账号、医院账号、医生账号不能把地区置空
        $("#areaCode").val("");// 地区
    }
    if (drRole != "9") {// 医生账号不能把机构和团队置空
        $("#hospId").val("");// 机构
        $("#teamId").val("");// 团队名称
    }
    $("#drId").val("");// 签约医生
    $("#economics").val("");// 经济类型
    $("#signDateStart").val("");// 签约日期开始
    $("#signDateEnd").val("");// 签约日期结束
    $("#signFromDateStart").val("");// 协议开始日期开始
    $("#signFromDateEnd").val("");// 协议开始日期结束
    $("#signToDateStart").val("");// 协议截止日期开始
    $("#signToDateEnd").val("");// 协议截止日期结束
    $("#history").val("0");// 是否显示历史记录
}

/**
 * formatMoney(s,type)
 * 功能：金额按千位逗号分隔
 * 参数：s，需要格式化的金额数值
 * 参数：type，判断格式化后的金额是否需要小数位
 * 返回：返回格式化后的数值字符串
 */
function formatMoney(s, type) {
    if (/[^0-9\.]/.test(s))
        return "0.00";
    if (s == null || s == "null" || s == "")
        return "0.00";
    s = s.toString().replace(/^(\d*)$/, "$1.");
    s = (s + "00").replace(/(\d*\.\d\d)\d*/, "$1");
    s = s.replace(".", ",");
    var re = /(\d)(\d{3},)/;
    while (re.test(s))
        s = s.replace(re, "$1,$2");
    s = s.replace(/,(\d\d)$/, ".$1");
    if (type == 0) {
        var a = s.split(".");
        if (a[1] == "00") {
            s = a[0];
        }
    }
    return s;
}

/**
 * 去除数组最后一个元素
 * @param arr  需要去除最后一个元素的数组
 * @returns {Array} 结果数组
 */
function truncate(arr) {
    var m = [];
    for (var i = 0; i < arr.length - 1; i++) {
        m.push(arr[i]);
    }
    return m;
}