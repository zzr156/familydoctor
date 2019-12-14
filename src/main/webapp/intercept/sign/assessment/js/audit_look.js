/**
 * 定义全局变量
 */
var vo = {};
var assessmentId = "";// 考核表主键
var idno = "";// 身份证号
var patientId = "";// 居民ID
var signId = "";// 签约单ID
var signFromDate = "";// 协议开始时间
var signToDate = "";// 协议结束时间
var groups = "";// 所属人群
var operate = "";// 操作类型

/**
 * 页面内容加载完成后立即执行
 */
$(function () {

    initParameter();// 初始化参数
    initPage();// 初始化页面
    initFuntcion();// 初始化功能

});

/**
 * 初始化参数
 */
function initParameter() {
    idno = getQueryString("idno");
    patientId = getQueryString("patientId");
    signId = getQueryString("signId");
    signFromDate = getQueryString("signFromDate");
    signToDate = getQueryString("signToDate");
    operate = getQueryString("operate");
}

/**
 * 初始化页面
 */
function initPage() {
    initTag(operate);// 初始化标签（标签显掩控制）
    var vo = {};
    vo.patientId = patientId;
    vo.signId = signId;
    doAjaxPost('assessmentAction.action?act=commList', {strJson: JSON.stringify(vo)}, assessInit)
}

/**
 * 功能初始化
 */
function initFuntcion() {
    // 审核修改得分
    scoreAftInit();
    // 原因
    reasonInit();
}

/**
 * 初始化标签
 */
function initTag(operate) {
    if (operate != null && operate == "assessLook") {// 考核查看
        $("#saveBtn").hide();
        $(".scoreInput").attr("readonly", "readonly");
        $("textarea").attr("readonly", "readonly");
        $("textarea").css("background-color", "#F5F5F5");
        $("textarea").css("border-color", "#DDD");
        // 最后一列隐藏
        $("tr").find("th:last").hide();
        $("tr").find("td:last").hide();
        $("#retreat").hide();
    }
    if (operate != null && operate == "reviewLook") {// 审核查看
        // 倒数第三列隐藏
        $("tr").find("th:eq(-3)").hide();
        $("tr").find("td:eq(-3)").hide();
        // 倒数第二列隐藏
        $("tr").find("th:eq(-2)").hide();
        $("tr").find("td:eq(-2)").hide();
        // 按钮隐藏
        $("#saveBtn").hide();
        $("#retreat").hide();
    }
}

/**
 * 考核表初始化
 */
function assessInit(data) {
    groups = data.qvo;
    groupClassify(groups);// 人群分类
    var assessment = data.vo;
    reviewNumWarn(assessment);// 审核次数提醒
    assessmentId = assessment.id;
    var details = data.rows;
    dataToUi(assessment, "assessForm");
    $("#totalScore").text(assessment.totalScoreAft);// 得分显示为审核后得分
    $.each(details, function (k, v) {
        detailToUi(this, "assessForm");
    });
    // 审核操作记录日志
    if (data.map.reviewLog != null) {
        showReviewLog(data.map.reviewLog);
    }
}

/**
 * 人群分类
 */
function groupClassify(groups) {
    for (i in groups) {
        $("[pofield='" + groups[i] + "']").css("display", "table-row");
        readJwAllGroups(groups);
    }
}

/**
 * 所有人群调阅规则、健康人群的调阅就是本身、其他人群则变为居民电子健康档案
 */
function readJwAllGroups(group) {
    if (group == "health") {
        // $("[contentCode=41]").closest("tr").find("td:last").append("<input type='button' onclick='findHealthfileDetail(1)' class='layui-btn layui-btn-lg' value='查看1' />");
        $("[contentCode=41]").closest("tr").find("td:last").append("<input type='button' onclick='findHealthReport()' class='layui-btn layui-btn-lg' value='查看' />");
    } else {
        $("[contentCode=41]").closest("tr").find("td:last").empty();// 先清空
        // $("[contentCode=41]").closest("tr").find("td:last").append("<input type='button' onclick='findElectronicArchivesDetail()' class='layui-btn layui-btn-lg' value='查看2' />");
        $("[contentCode=41]").closest("tr").find("td:last").append("<input type='button' onclick='findHealthReport()' class='layui-btn layui-btn-lg' value='查看' />");
    }


}

/**
 *  查看健康报告
 */
function findHealthReport() {
    vo["patientIdNo"] = idno;
    //vo["orgId"] = orgid;
    vo["signLableId"] = signId;
    doAjaxPost('signAction.action?act=findHealthReport', {strJson: JSON.stringify(vo)}, function (data) {
        if (data.vo == null) {
            layer.msg("该居民未生成报告！");
        } else {
            layer.open({
                title: "查看健康报告",
                type: 2,
                maxmin: true,
                content: familyUrl + "/intercept/sign/healthReport/health_report_look.jsp?id=" + data.vo.id + "&patientIdno=" + idno + "&lookType=2",
                area: ["65%", "90%"],
                success: function (layero, index) {
                },
                cancel: function (layero, index) {
                }
            });
        }
    });
}

/**
 * 考核细表回显
 */
function detailToUi(detail, ui) {
    var obj = $("#" + ui).find("[contentCode=" + detail.contentCode + "]");
    if (obj != null) {
        var tr = obj.closest("tr");
        tr.data("id", detail.id);// 设置主键
        var scorePre = tr.find("td:eq(3)").find("input");// 分数
        var uploadText = tr.find("td:eq(5)").find("span");// 已上传的文件名
        var optionUrl = tr.find("td:eq(5)").find("input");// 文件路径
        var scoreAft = tr.find("td:eq(6)").find("input");// 审核修改后分数
        var reason = tr.find("td:eq(-2)").find("textarea");// 原因
        scorePre.val(detail.scorePre);
        if (detail.contentCode == "32") {
            viewFile32(tr, detail.optionWeb);
        } else {
            optionUrl.val(detail.optionWeb);
        }
        scoreAft.val(detail.scoreAft);
        reason.val(detail.reason);
        // 保存旧的审核分数和原因
        tr.data("scoreAftOld", detail.scoreAft);
        tr.data("reasonOld", detail.reason);
        if (detail.fileName != null && detail.fileName != "") {
            uploadText.closest("td").find("a").remove();// 清掉原先动态生成的"预览"按钮
            uploadText.text("");
            // 动态插入
            if (detail.contentCode == "32") {
                viewFile32(tr, detail.optionWeb);
            } else {
                uploadText.after("<input type='hidden' value='" + detail.optionWeb + "'/>" +
                    "<a style='margin-bottom: 5px;margin-right: 5px;'  class='layui-btn' onclick='viewPicture(this)'>预览</a>");
            }
        }
    }
}

/**
 * 审核操作记录表
 */
function showReviewLog(logs) {
    $("#reviewLog").find("tr").remove();
    $("#reviewLog").append("<tr><th>时间</th><th>操作人</th><th>操作内容</th></tr>");
    $.each(logs, function (k, v) {
        $("#reviewLog").append("<tr><td>" + v.hsCreateDate + "</td><td>" + v.updateUserName + "</td><td>" + v.content + "</td></tr>")
    })
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
        content: familyUrl + "/intercept/sign/assessment/view_picture.jsp?id=" + id + "&month=" + month + "&operate=review",
        area: ["50%", "90%"],
        success: function (layero, index) {
        },
        cancel: function () {
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
 * 审核修改得分
 */
function scoreAftInit() {
    $("input[pofield=scoreAft]").keyup(function () {
        // 审核后得分限制只能输入数字
        this.value = this.value.replace(/[^\d]/g, '');
        // js数字比较parseInt转化
        var tr = $(this).closest("tr");
        var tdScorePre = tr.find("td:eq(3)").find("input");
        var tdScore = tr.find("td:eq(2)");
        if (tdScorePre.val() == "") {
            alertMsg("该考核项目尚未考核,不能进行审核!");
            this.value = '';
            return;
        }
        if (parseInt(this.value) > parseInt(tdScore.text())) {
            alertMsg("不能超过该考核的分值!");
            this.value = '';
        }
        // 审核修改得分相加
        var totalScoreAft = 0;
        $.each($("input[pofield=scoreAft]"), function (k, v) {
            var tdScorePre = $(this).closest("tr").find("td:eq(3)").find("input").val();// 获取考核得分
            if (tdScorePre == "") {// 如果未考核则分数视为0分
                tdScorePre = 0;
            }
            totalScoreAft += parseInt($(this).val() == "" ? tdScorePre : $(this).val());// 取审核后分数作为因子，如果无则取考核分
        });
        // 如果所属服务人群有两种以上，则取评分更高的作为审核分
        var flag = 1;
        var groupOneScoreAft = 0;
        var groupTwoScoreAft = 0;
        var groupThreeScoreAft = 0;
        var minScoreAft = 0;// 个性化人群中得分最小的人群
        if (groups.length >= 2) {
            for (var i = 0; i < groups.length; i++) {
                if (groups[i] == "old") {
                    var scoreAft43 = getScoreAft("43");
                    var scoreAft44 = getScoreAft("44");
                    if (flag == 1) {
                        groupOneScoreAft = scoreAft43 + scoreAft44;
                    } else if (flag == 2) {
                        groupTwoScoreAft = scoreAft43 + scoreAft44;
                    } else {
                        groupThreeScoreAft = scoreAft43 + scoreAft44;
                    }
                    flag++;
                } else if (groups[i] == "pregnant") {
                    var scoreAft45 = getScoreAft("45");
                    var scoreAft46 = getScoreAft("46");
                    if (flag == 1) {
                        groupOneScoreAft = scoreAft45 + scoreAft46;
                    } else if (flag == 2) {
                        groupTwoScoreAft = scoreAft45 + scoreAft46;
                    } else {
                        groupThreeScoreAft = scoreAft45 + scoreAft46;
                    }
                    flag++;
                } else if (groups[i] == "young") {
                    var scoreAft47 = getScoreAft("47");
                    var scoreAft48 = getScoreAft("48");
                    if (flag == 1) {
                        groupOneScoreAft = scoreAft47 + scoreAft48;
                    } else if (flag == 2) {
                        groupTwoScoreAft = scoreAft47 + scoreAft48;
                    } else {
                        groupThreeScoreAft = scoreAft47 + scoreAft48;
                    }
                    flag++;
                } else if (groups[i] == "chronic") {
                    var scoreAft49 = getScoreAft("49");
                    var scoreAft410 = getScoreAft("410");
                    if (flag == 1) {
                        groupOneScoreAft = scoreAft49 + scoreAft410;
                    } else if (flag == 2) {
                        groupTwoScoreAft = scoreAft49 + scoreAft410;
                    } else {
                        groupThreeScoreAft = scoreAft49 + scoreAft410;
                    }
                    flag++;
                } else if (groups[i] == "psychosis") {
                    var scoreAft411 = getScoreAft("411");
                    var scoreAft412 = getScoreAft("412");
                    if (flag == 1) {
                        groupOneScoreAft = scoreAft411 + scoreAft412;
                    } else if (flag == 2) {
                        groupTwoScoreAft = scoreAft411 + scoreAft412;
                    } else {
                        groupThreeScoreAft = scoreAft411 + scoreAft412;
                    }
                    flag++;
                }
            }
            minScoreAft = parseInt(groupOneScoreAft);// 需要减去的分数
            if (parseInt(groupTwoScoreAft) < minScoreAft) {
                minScoreAft = groupTwoScoreAft;
            }
            if (groups.length >= 3) {
                if (parseInt(groupThreeScoreAft) < minScoreAft) {
                    minScoreAft = groupThreeScoreAft;
                }
            }
        }
        // 将审核得分显示到窗口顶部和表格底部
        $("#totalScoreAft").text(totalScoreAft - minScoreAft);
        parent.$("#windowScore").text(totalScoreAft - minScoreAft);
    });
}

/**
 * 获取某个考核项的审核分数（没有审核分则取考核分）
 */
function getScoreAft(contentCode) {
    var formObj = $("#assessForm").find("[contentCode=" + contentCode + "]");
    var tableTr = formObj.closest("tr");
    var scoreAft = tableTr.find("td:eq(6)").find("input").val();// 审核分数
    if (scoreAft == "") {
        scoreAft = tableTr.find("td:eq(3)").find("input").val();// 考核分数
    }
    return scoreAft;
}


/**
 * 原因
 */
function reasonInit() {
    var reason = $("textarea");
    reason.css("background-color", "#F5F5F5");
    reason.attr("readonly", "readonly");
    reason.click(function () {
        if (!isNotBlank(($(this).closest("td").prev().find("input").val()))) {
            alertMsg("审核分数未填写，不能填写原因!");
            $(this).val("");// 清空
            $(this).css("background-color", "#F5F5F5");
            $(this).attr("readonly", "readonly");
            return;
        }
        $("textarea").removeAttr("readonly");
        $(this).css("background-color", "#ffffff");
    })
}

/**
 * 审核结果保存
 */
function saveReview() {
    var vo = {};
    vo.assessmentId = assessmentId;
    vo.hospLevel = HospLevel;
    vo.details = [];
    var isUpdate = true;
    var reasonIsNull = true;// 是否全都填写了审核原因
    $.each($("[contentCode]"), function (k, v) {
        var tr = $(this).closest("tr");
        var detail = {};
        detail.scoreAft = tr.find("td:eq(6)").find("input").val();
        detail.reason = tr.find("td:eq(-2)").find("textarea").val();
        if (detail.reason != "" && detail.reason != undefined) reasonIsNull = false;
        if (detail.scoreAft != "" && detail.reason == "") {
            alertMsg("修改原因未填写!");
            isUpdate = false;
            return;
        }
        if (detail.scoreAft == "") return;
        detail.id = tr.data("id");
        vo.details.push(detail);
    });
    if (!isUpdate) return;// 没有符合修改条件就不保存
    vo.totalScoreAft = getTotalScoreAft(reasonIsNull);
    if (reasonIsNull && confirm("确认已经审核过?")) {
        // 审核原因全都没有填写的弹出一个确认框
        save(vo);
    } else if (!reasonIsNull) {
        // 审核原因有填写的默认直接执行保存操作
        save(vo);
    }
}

/**
 * 保存
 */
function save(vo) {
    doAjaxPost('assessmentAction.action?act=reviewSave', {strJson: JSON.stringify(vo)}, function (data) {
        if (data.code != "800") {
            alertMsg("保存失败!");
        } else {
            alertMsg("保存成功!");
            window.location.reload();
        }
    });
}

function getTotalScoreAft(reasonIsNull) {
    // 如果全都没有填写审核原因就取审核前分数作为审核后分数
    if (reasonIsNull) {
        var totalScoreAft = 0;
        $.each($("input[pofield=scoreAft]"), function (k, v) {
            var tdScorePre = $(this).closest("tr").find("td:eq(3)").find("input").val();
            if (tdScorePre == "") return true;
            totalScoreAft += parseInt($(this).val() == "" ? tdScorePre : $(this).val());
        });
        return totalScoreAft;
    }
    // 直接返回审核后总分
    return $("#totalScoreAft").text();
}

/**
 * 退回
 */
function retreat() {
    var vo = {};
    vo.assessmentId = assessmentId;
    vo.hospLevel = HospLevel;
    vo.signAreaCode = HospAreaCode;
    doAjaxPost('assessmentAction.action?act=retreat', {strJson: JSON.stringify(vo)}, function (data) {
        if (data.code == "800") {
            layer.msg("退回成功!");
        } else {
            layer.msg("退回失败!");
        }
    });
}

/**
 * 审核次数提醒
 */
function reviewNumWarn(assessment) {
    if (HospLevel == "3" && assessment.isReview == "1" && assessment.reviewNum >= 1) {
        $("#saveBtn").hide();
    }
}

/**
 * 调阅居民健康档案
 */
function findElectronicArchivesDetail() {
    vo["idNo"] = idno;
    vo["orgId"] = orgid;
    vo["type"] = "2";
    doAjaxPost('signAction.action?act=findjmda', {strJson: JSON.stringify(vo)}, function (data) {
        if (data.vo != null) {
            var datavo = JSON.parse(data.vo);
            if (datavo.entity != null && datavo.entity != "") {
                var entityvo = datavo.entity;
                if (necs(entityvo.jmdah)) {
                    layer.open({
                        title: "调阅居民健康档案",
                        type: 2,
                        maxmin: true,
                        content: "http://18.1.3.28:7001/sqyl/logonAction.do?method=logon2&userid=csyh&passwd=c958d44fa3c5937507e0d2c06f63ae8f&df_id=" + entityvo.jmdah,
                        area: ["60%", "90%"],
                        success: function (layero, index) {
                        },
                        cancel: function (layero, index) {
                        }
                    });
                } else {
                    layer.msg("该居民当前未建立健康档案信息！");
                }
            } else {
                layer.msg("该居民当前未建立健康档案信息！");
            }
        } else {
            layer.msg("该居民当前未建立健康档案信息！");
        }
    }, function (data) {
        layer.msg("调取居民档案异常，请联系管理员！");
    });

    // layer.open({
    //     title: "调阅居民健康档案",
    //     type: 2,
    //     maxmin: true,
    //     content: familyUrl + "/intercept/sign/assessment/view_electronicArchives_detail.jsp?areaCode=" + HospAreaCode + "&idcardno=" + idno + "&signStartTime=" + signFromDate + "&signEndTime=" + signToDate + "",
    //     area: ["60%", "90%"],
    //     success: function (layero, index) {
    //     },
    //     cancel: function (layero, index) {
    //     }
    // });
}

/**
 * 调阅体检表  type 类型（1-健康人群，2-老年人，3-慢性病）
 */
function findHealthfileDetail(type) {
    layer.open({
        title: "调阅体检表",
        type: 2,
        maxmin: true,
        content: familyUrl + "/intercept/sign/assessment/view_healthfile_list.jsp?areaCode=" + HospAreaCode + "&idcardno=" + idno + "&signStartTime=" + signFromDate + "&signEndTime=" + signToDate + "",
        area: ["80%", "90%"],
        success: function (layero, index) {
        },
        cancel: function (layero, index) {
        }
    });
}

/**
 * 调阅慢性病高血压随访记录  type 类型（1-健康人群，2-老年人，3-慢性病）
 */
function findChronicDiseaseFollowUpRecordsDetail(type) {
    layer.open({
        title: "调阅慢性病高血压随访记录",
        type: 2,
        maxmin: true,
        content: familyUrl + "/intercept/sign/assessment/view_chronicDiseaseFollowUpRecords_list.jsp?areaCode=" + HospAreaCode + "&idcardno=" + idno + "&signStartTime=" + signFromDate + "&signEndTime=" + signToDate + "",
        area: ["80%", "90%"],
        success: function (layero, index) {
        },
        cancel: function (layero, index) {
        }
    });
}

/**
 * 调阅慢性病糖尿病随访记录  type 类型（1-健康人群，2-老年人，3-慢性病）
 */
function findChronicDiseaseFollowUpRecordsDetail2(type) {
    layer.open({
        title: "调阅慢性病糖尿病随访记录",
        type: 2,
        maxmin: true,
        content: familyUrl + "/intercept/sign/assessment/view_chronicDiseaseFollowUpRecords_list2.jsp?areaCode=" + HospAreaCode + "&idcardno=" + idno + "&signStartTime=" + signFromDate + "&signEndTime=" + signToDate + "",
        area: ["80%", "90%"],
        success: function (layero, index) {
        },
        cancel: function (layero, index) {
        }
    });
}

/**
 * 孕产期随访
 */
function findGravidaFollowUpRecordsDetail() {
    layer.open({
        title: "孕产期随访",
        type: 2,
        maxmin: true,
        content: familyUrl + "/intercept/sign/assessment/view_gravidaFollowUpRecords_list.jsp?areaCode=" + HospAreaCode + "&idcardno=" + idno + "&signStartTime=" + signFromDate + "&signEndTime=" + signToDate + "",
        area: ["80%", "90%"],
        success: function (layero, index) {
        },
        cancel: function (layero, index) {
        }
    });
}

/**
 * 调阅插卡记录  type 类型（1-健康人群，2-老年人，3-慢性病）
 */
function findVisitRecordsDetail() {
    layer.open({
        title: "查看刷卡记录",
        type: 2,
        maxmin: true,
        content: familyUrl + "/intercept/sign/assessment/view_visitRecords_list.jsp?areaCode=" + HospAreaCode + "&idcardno=" + idno + "&signStartTime=" + signFromDate + "&signEndTime=" + signToDate + "",
        area: ["80%", "90%"],
        success: function (layero, index) {
        },
        cancel: function (layero, index) {
        }
    });
}

/**
 * 调阅孕产妇基卫产检记录  type 类型（1-健康人群，2-老年人，3-慢性病）
 */
function findAntenatalRecordsDetail() {
    layer.open({
        title: "调阅产检记录",
        type: 2,
        maxmin: true,
        content: familyUrl + "/intercept/sign/assessment/view_antenatalRecords_detail.jsp?areaCode=" + HospAreaCode + "&idcardno=" + idno + "&signStartTime=" + signFromDate + "&signEndTime=" + signToDate + "",
        area: ["80%", "90%"],
        success: function (layero, index) {
        },
        cancel: function (layero, index) {
        }
    });
}

/**
 * 调阅儿童随访记录  type 类型（1-新生儿，2-1岁以内儿童，3-1-2岁儿童随访记录，4-3-6岁儿童随访记录）
 */
function findChildRecordsList() {
    layer.open({
        title: "调阅儿童随访记录",
        type: 2,
        maxmin: true,
        content: familyUrl + "/intercept/sign/assessment/view_followRecordsAndHealth_list.jsp?areaCode=" + HospAreaCode + "&idcardno=" + idno + "&signStartTime=" + signFromDate + "&signEndTime=" + signToDate + "",
        area: ["80%", "90%"],
        success: function (layero, index) {
        },
        cancel: function (layero, index) {
        }
    });
}