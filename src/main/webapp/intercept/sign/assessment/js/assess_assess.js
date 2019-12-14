/**
 * 定义全局变量
 */
var detailNum = 8;// 除个性化考核项外其他考核项总数量
var groupNum = {"health": 1, "young": 2, "pregnant": 2, "old": 2, "chronic": 2, "psychosis": 2};// 各个人群的考核项数量
var group = [];// 所属人群
var groupItem = {// 各个人群对应的考核项目
    "health": ["42"],
    "young": ["47", "48"],
    "pregnant": ["45", "46"],
    "old": ["44", "43"],
    "chronic": ["49", "410"],
    "psychosis": ["411", "412"]
};
var groupMap = {};// 所属人群对应的项目
var detailData;// 明细图片地址对象存储
var isDel = false;
var isFirstInit = false;// 是否首次初始化
var isJwInit = false;// 是否是调取基卫接口
var signId = "";// 签约单ID
var patientId = "";// 居民ID
var teamId = "";// 团队ID
var drId = "";// 医生ID
var patientIdno = "";// 居民身份证号
var patientName = "";// 居民姓名
var patientSex = "";// 居民性别
var patientAge = "";// 居民年龄
var signFromDate = "";// 协议开始时间
var signToDate = "";// 协议截止日期
var groupName = "";// 所属人群名称
var isFinish = "";// 是否已考核完成
var operate = "";// 操作类型
var assessmentId = "";// 考核记录ID
var isFirstClick = true;// 是否是第一次点击

/**
 * 页面加载完成后执行
 */
$(function () {

    initParameter();// 获取参数
    initFunction();// 初始化功能
    initPage();// 考核表初始化

});

/**
 * 初始化功能
 */
function initFunction() {
    if (operate == "assess") {// 考核时不要显示月份选择框和生成评估报告按钮
        $("#activityTimeDiv").hide();
        $("#resitHealthReportButton").hide();
    } else if (operate == "resitAssess") {// 补考时初始下拉框的值
        var date = new Date(Date.parse(signFromDate.replace(/-/g, "/")));
        var select = $("#activityTime");
        var selectValue = "";
        var selectText = "";
        for (var i = 0; i < 13; i++) {// 显示连续的13个月
            selectValue = date.getFullYear() + "-" + (date.getMonth() + 1);
            selectText = date.getFullYear() + "年" + (date.getMonth() + 1) + "月";
            date.setMonth(date.getMonth() + 1);// 下一个月
            var option = document.createElement('option');
            option.setAttribute("value", selectValue);
            option.innerText = selectText;
            select.append(option);
        }
        //暂时屏蔽
        // $("#fileWorkRecordComplete").hide();
        // $("#fileWorkValidTalk").hide();
        // $("#fileTeamConsult").hide();
        // $("#activityTimeDiv").hide();
        // $("#commAct").hide();
        // $("#fileOld").hide();
        // $("#filePregnant").hide();
        // $("#fileYoung").hide();
        // $("#fileChronic").hide();
        // $("#filePsychosis").hide();
        // $("#filePsychosisRecord").hide();
    }
    if (isFinish == "是") {// 如果是已考核完成状态则掩藏“标记为考核完成”按钮
        $("#finishBtn").hide();
    }
}

/**
 * 初始化页面
 */
function initPage() {
    var vo = {};
    vo.signId = signId;
    doAjaxPostSync('assessmentAction.action?act=commList', {strJson: JSON.stringify(vo)}, assessInit);
    if (!isFirstInit) {// 如果是首次初始化则不执行（避免多执行一次初始化）
        initJwAssess();
    }
}

/**
 * 初始化考核表
 */
function assessInit(data) {
    var assessment = data.vo;
    var groups = data.qvo;
    groupClassify(groups);// 人群分类
    if (assessment == null) {
        isFirstInit = true;
        initJwAssess();// 基卫考核内容初始化
        return;
    }
    assessmentId = assessment.id;

    if (!isJwInit) {//调取基卫接口后执行的回显操作不需要再次提醒
        remind(assessment);// 提醒
    }
    var details = data.rows;
    dataToUi(assessment, "assessForm");// 考核细表回显
    parent.$("#windowScore").text(assessment.totalScorePre);
    $.each(details, function (k, v) {
        detailToUi(this, "assessForm");
        if (v.contentCode == '41' && v.scorePre > 0) {
            $("#resitHealthReportButton").hide();
        }
    });
    if (data.map.assessLog != null) {// 考核操作日志
        showAssessLog(data.map.assessLog);
    }
}

/**
 * 基卫考核内容初始化
 */
function initJwAssess() {
    isJwInit = true;
    var vo = {};
    vo.groups = group;
    vo.detailNum = detailNum;
    vo.signId = signId;
    vo.patientId = patientId;
    vo.hospId = orgid;
    vo.teamId = teamId;
    vo.drId = drId;
    vo.assessId = assessmentId;
    doAjaxPost('assessmentAction.action?act=initAssess', {strJson: JSON.stringify(vo)}, function (data) {
        if (data.code = "800") {// 初始化完成后再进行页面回显
            doAjaxPost('assessmentAction.action?act=commList', {strJson: JSON.stringify(vo)}, assessInit);
        }
    });
}

/**
 * 人群分类
 */
function groupClassify(groups) {
    for (i in groups) {
        $("[pofield='" + groups[i] + "']").css("display", "table-row");
        detailNum += groupNum["" + groups[i] + ""];
        group.push(groups[i]);
        groupMap["" + groups[i] + ""] = groupItem["" + groups[i] + ""];
    }
}

/**
 * 考核明细表回显
 */
function detailToUi(detail, ui) {
    var obj = $("#" + ui).find("[contentCode=" + detail.contentCode + "]");
    if (obj != null) {
        var tr = obj.closest("tr");
        tr.data("id", detail.id);// 设置主键
        var scorePre = tr.find("td:eq(3)").find("input");// 分数
        var uploadText = tr.find("td:eq(5)").find("span");// 已上传的文件名
        scorePre.val(detail.scorePre);
        if (detail.optionWeb != null && detail.optionWeb != "") {
            uploadText.closest("td").find("a").remove();// 清掉原先动态生成的"预览"按钮
            uploadText.text("");
            if (detail.contentCode == "32") {// 动态插入
                viewFile32(tr, detail.optionWeb);
            } else {
                uploadText.after("<input type='hidden' value='" + detail.optionWeb + "'/><a class='layui-btn' onclick='viewPicture(this)'>预览</a>");
            }
        }
    }
}

/**
 * 考核操作记录
 */
function showAssessLog(logs) {
    $("#assessLog").find("tr").remove();
    $("#assessLog").append("<tr><th>时间</th><th>操作人</th><th>操作内容</th></tr>");
    $.each(logs, function (k, v) {
        $("#assessLog").append("<tr><td>" + v.hsCreateDate + "</td><td>" + v.updateUserName + "</td><td>" + v.content + "</td></tr>")
    });
}

/**
 * 32文件名字显示特殊处理
 */
function detail32UploadText(fileName) {
    var fileNames = fileName.split(";");
    var result = "";
    for (i in fileNames) {
        result += fileNames[i] + "</br>";
    }
    return result;
}

/**
 * 32文件查看特殊处理
 */
function viewFile32(tr, optionWeb) {
    var jObj = JSON.parse(optionWeb);// 此时是字符串需要先转换成JSON对象才能进行处理
    var td = tr.find("td:eq(5)");
    var span = td.find("span");
    for (i in jObj) {
        span.after("<a style='margin-bottom: 5px;margin-right: 5px;' class='layui-btn layui-btn-lg' month=" + i + " onclick='viewPicture(this)'>" + i + "</a>");
        span.after("<input type='hidden' value='" + JSON.stringify(jObj[i]) + "'/>");// 用json对象取出的是字符串需转换成json字符串再传过去
    }
}

/**
 * 获取参数
 */
function initParameter() {
    signId = getQueryString("signId");// 签约单ID
    patientId = getQueryString("patientId");// 居民ID
    teamId = getQueryString("teamId");// 团队ID
    drId = getQueryString("drId");// 医生ID
    patientIdno = getQueryString("idno");// 居民身份证号
    patientName = getQueryString("name");// 居民姓名
    patientSex = getQueryString("sex");// 居民性别
    patientAge = getQueryString("age");// 居民年龄
    signFromDate = getQueryString("signFromDate");// 协议开始时间
    signToDate = getQueryString("signToDate");// 协议截止时间
    groupName = getQueryString("groupName");// 所属人群名称
    isFinish = decodeURI(decodeURI(getQueryString("isFinish")));// 是否考核完成
    operate = getQueryString("operate");// 操作类型
}

/**
 * 保存考核信息
 */
function save(obj) {
    if (isFirstClick) {
        isFirstClick = false;
        var operate = $(obj).attr("operate");
        $(obj).attr("disabeld", "disabled");// 防止表单重复提交
        $(obj).addClass("layui-btn-disabled");
        var vo = {};
        vo = uiToData("assessForm", vo);
        vo.groups = group;
        vo.signId = signId;
        vo.patientId = patientId;
        vo.detailMap = {};// 封装数据结构为map
        vo.detailNum = detailNum;
        vo.hospId = orgid;
        vo.drId = drid;
        vo.teamId = teamId;
        vo.assessId = assessmentId;
        $.each($("[contentCode]"), function (k, v) {
            var contentCode = $(this).attr("contentCode");
            // 如果执行的保存操作是只保存32项目，那么封装的map也只传32项目到后台
            if (operate == "32" && contentCode != "32") {
                return true;// 相当于continue
            }
            var tr = $(this).closest("tr");
            var obj = {};
            obj.id = tr.data("id");
            obj.scorePre = tr.find("td:eq(3)").find("input").val();
            obj.optionWeb = tr.find("td:last").find("span").text();
            vo.detailMap["" + contentCode + ""] = obj;
        });
        $(".uploadForm").ajaxSubmit({
            type: "POST",
            url: "assessmentAction.action?act=saveAssessment",
            dataType: "json",
            data: {strJson: JSON.stringify(vo)},
            success: function (data) {
                if (data.code != "800") {
                    layer.msg("保存失败！");
                } else {
                    layer.msg("保存成功！");
                    $(obj).removeAttr("disabeld", "disabled");
                    $(obj).removeClass("layui-btn-disabled");
                    setTimeout(function () {
                        window.location.reload();
                    }, 1000);
                }
            }
        });
    }
}

/**
 * 标记考核是否完成
 */
function finish(obj) {
    /*if ($(obj).val() == "1") {
        layer.open({
            type: 1,
            title: false,
            closeBtn: false,
            area: '360px',// 弹框宽度
            offset: [($(window).height() / 2 - 180), ($(window).width() / 2 - 180)],// 弹框位置
            shade: 0.3,
            id: 'layer_open_one',
            btn: ['确定', '取消'],
            btnAlign: 'c',
            moveType: 1,
            content: '<div style="padding: 50px; line-height: 22px; background-color: #393D49;' +
            'color: #fff; font-weight: 100;text-align : center;">' + '确定标记为考核完成吗?' + '</div>',
            btn1: function () {
                $(obj).val("0");
                $(obj).text("标记为未考核完成");
                layer.closeAll();
                saveFinish();
            },
            btn2: function () {
                layer.closeAll();
            }
        });
    } else {
        layer.open({
            type: 1,
            title: false,
            closeBtn: false,
            area: '360px',// 弹框宽度
            offset: [($(window).height() / 2 - 180), ($(window).width() / 2 - 180)],// 弹框位置
            shade: 0.3,
            id: 'layer_open_two',
            btn: ['确定', '取消'],
            btnAlign: 'c',
            moveType: 1,
            content: '<div style="padding: 50px; line-height: 22px; background-color: #393D49;' +
            'color: #fff; font-weight: 100;text-align : center;">' + '确定标记为未考核完成吗?' + '</div>',
            btn1: function () {
                $(obj).val("1");
                $(obj).text("标记为考核完成");
                layer.closeAll();
                saveFinish();
            },
            btn2: function () {
                layer.closeAll();
            }
        });
    }*/
    layer.open({
        type: 1,
        title: false,
        closeBtn: false,
        area: '360px',// 弹框宽度
        offset: [($(window).height() / 2 - 180), ($(window).width() / 2 - 180)],// 弹框位置
        shade: 0.3,
        id: 'layer_open_one',
        btn: ['确定', '取消'],
        btnAlign: 'c',
        moveType: 1,
        content: '<div style="padding: 50px; line-height: 22px; background-color: #393D49;' +
        'color: #fff; font-weight: 100;text-align : center;">' + '确定标记为考核完成吗?' + '</div>',
        btn1: function () {
            saveFinish();
            layer.closeAll();
        },
        btn2: function () {
            layer.closeAll();
        }
    });
}

function saveFinish() {
    var vo = {};
    vo.assessId = assessmentId;
    doAjaxPost('assessmentAction.action?act=finishSave', {strJson: JSON.stringify(vo)}, function (data) {
        layer.msg(data.msg);
        $("#finishBtn").hide();// 掩藏按钮
    });
}

/**
 * 查看图片
 */
function viewPicture(obj) {
    detailData = $(obj).prev().val();
    var tr = $(obj).closest("tr");
    var id = tr.data("id");
    var month = $(obj).attr("month");
    layer.open({
        type: 2,
        title: "预览",
        maxmin: true,
        content: familyUrl + "/intercept/sign/assessment/view_picture.jsp?id=" + id + "&month=" + month,
        area: ["50%", "90%"],
        success: function (layero, index) {
        },
        cancel: function (layero, index) {
            if (isDel) {// 有删除操作再重新加载
                window.location.reload();
            }
        }
    });
}

/**
 * 子层数据存储
 */
function dataStorage() {
    return detailData;
}

/**
 * 是否有删除图片的操作
 */
function isDelStorage() {
    isDel = true;
}

/**
 * 重置表单
 */
function signsxcz() {
    $("#patientName").val("");
    $("#patientIdno").val("");
    $("#patientjmda").val("");
    $("#signzfpay").val("");
    $("#signDateStart").val("");
    $("#signDateEnd").val("");
    $("#signlx").val("");
    $("#teamId").val("");
    $("#drId").val("");
    $("#signDrAssistantId").val("");

    var signsJjTypes = document.getElementsByName("signsJjTypes");
    for (var i = 0; i < signsJjTypes.length; i++) {
        if (signsJjTypes[i].checked) {
            signsJjTypes[i].checked = false;
        }
    }

    var persGroup = document.getElementsByName("persGroup");
    for (var i = 0; i < persGroup.length; i++) {
        if (persGroup[i].checked) {
            persGroup[i].checked = false;
        }
    }
}

/**
 * 返回按钮
 */
function back() {
    window.history.go(-1);// 返回，不清空表单
}

/**
 * 文件过滤（图片）
 */
function pictureFilter(file) {
    var fileTotalNum = 0;// 批量上传文件数量
    var inValidFileNum = 0;// 无效格式图片文件的数量
    var files = file.files;
    var td = $(file).closest("td");
    var $file = $(file);
    for (var i = 0; i < files.length; i++) {
        fileTotalNum++;
        var type = files[i].name.split(".")[1];
        if (type == "png" || type == "jpg" || type == "jpeg" || type == "bmp" || type == "gif" || type == "JPG") {

        } else {
            inValidFileNum++;
        }
    }
    if (fileTotalNum > 9) {
        alertMsg("每次最多只能上传 9 张图片，您当前上传图片数量为 " + fileTotalNum + " 张！");
        //ie8下清空file
        td.append($file.prop("outerHTML"));
        $file.remove();
        return false;
    }
    if (inValidFileNum > 0) {
        alertMsg("您选择的文件中有 " + inValidFileNum + " 个不是有效格式的图片文件！");
        //ie8下清空file
        td.append($file.prop("outerHTML"));
        $file.remove();
        return false;
    }
}

/**
 * 文件过滤（图片、音频）
 */
function soundFilter(file) {
    var fileTotalNum = 0;// 批量上传文件数量
    var inValidFileNum = 0;// 无效格式文件的数量
    var files = file.files;
    var td = $(file).closest("td");
    var $file = $(file);
    for (var i = 0; i < files.length; i++) {
        fileTotalNum++;
        var type = files[i].name.split(".")[1];
        if (type == "png" || type == "jpg" || type == "jpeg" || type == "bmp" || type == "gif" || type == "mp3" || type == "JPG") {

        } else {
            inValidFileNum++;
        }
    }
    if (fileTotalNum > 9) {
        alertMsg("每次最多只能上传 9 个文件，您当前上传文件数量为 " + fileTotalNum + " 张！");
        //ie8下清空file
        td.append($file.prop("outerHTML"));
        $file.remove();
        return false;
    }
    if (inValidFileNum > 0) {
        alertMsg("您选择的文件中有 " + inValidFileNum + " 个不是有效格式的文件！");
        //ie8下清空file
        td.append($file.prop("outerHTML"));
        $file.remove();
        return false;
    }
}

/**
 * 提醒
 */
function remind(assessment) {
    if (assessment.state != "0") {
        layer.msg("该考核表被退回，请重新完善后再保存！");
    }
    if (assessment.isReview == "1") {
        if (assessment.saveNum >= 1) {
            $("#saveBtn").text("每月社区活动保存");
            $("#saveBtn").attr("operate", "32");
        } else {
            layer.msg("该考核表已被区县审核过，只能再修改保存 1 次！");
        }
    }
    if (assessment.isReview == "2") {
        if (assessment.saveNum >= 2) {
            $("#saveBtn").text("每月社区活动保存");
            $("#saveBtn").attr("operate", "32");
        } else {
            layer.msg("该考核表已被乡镇审核过，只能再修改保存 " + (2 - assessment.saveNum) + " 次！");
        }
    }
}

/**
 * 查看健康档案调阅记录
 */
function findReadFileLog() {
    layer.open({
        title: "查看健康档案调阅记录",
        type: 2,
        maxmin: true,
        content: familyUrl + "/intercept/sign/assessment/view_readFileLog.jsp?signId=" + signId,
        area: ["80%", "90%"],
        success: function (layero, index) {
        },
        cancel: function (layero, index) {
        }
    });
}

/**
 * 补录健康评估报告
 */
function resitHealthReport() {
    var url = "../healthReport/health_report_add.jsp?id=" + signId + "&patientName=" + encodeURI(encodeURI(patientName)) +
        "&patientGender=" + encodeURI(encodeURI(patientSex)) + "&patientAge=" + patientAge +
        "&signToDate=" + encodeURI(encodeURI(signFromDate.replace(/-/g, "/") + "至" + signToDate.replace(/-/g, "/")))
        + "&signPersGroup=" + encodeURI(encodeURI(groupName)) + "&patientIdno=" + patientIdno + "&healthType=2";
    var initWidth = document.body.clientWidth + "px";
    var initHeight = document.body.clientHeight + "px";
    var index = layer.open({
        type: 2, // 2代表iframe
        title: "生成健康评估报告",// 窗口标题
        shade: 0,// 阴影效果
        area: [initWidth, initHeight],
        content: url,// 页面URL
        end: function () {// 关闭窗口事件
            window.location.reload();// 刷新页面
        }
    });
    layer.full(index);// 打开窗口时默认最大化
}