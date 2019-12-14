/**
 * Created by lenovo on 2017/12/29.
 */
var vo = {};
vo.familyVo = [];
var thisvo = {};
var fvolist = [];// 家庭成员数组
$("#batchOperatorName").val(username);// 操作人名称
$("#batchOperatorId").val(drid);// 操作人ID
$("#hospId").val(orgid);// 机构ID
$("#hospName").val(OrgName);// 机构名称

/**
 * 页面加载完成后执行
 */
$(function () {
    <!-- 合计初始化 -->
    $("#amountPrivateybz").val(0);
    $("#amountPrivate").val(0);
    $("#signczpay").val(0);
    $("#signzfpay").val(0);
    $("#totalpay").val(0);
    <!-- 触发级联 -->
    changecity();
    <!-- 初始化团队-->
    findteem();
    <!-- 初始化时间-->
    findDate();
});

/**
 * 新增家庭成员
 */
function familymemberadd() {
    myOpen("新增家庭成员", "sign_family_subpageadd.jsp?openType=add", familylist);
}

/**
 * 市级联
 */
function changecity() {
    var code = HospAreaCode.substring(0, 2);
    vo["upId"] = code + "0000000000";
    doAjaxPost('manageCounAction.action?act=appAddressResult', {strJson: JSON.stringify(vo)}, function (data) {
        $("#patientCity").html("");
        var option1 = document.createElement('option');
        option1.setAttribute("value", "");
        option1.innerText = "--请选择--";
        document.getElementById("patientCity").appendChild(option1);
        if (data != null) {
            $.each(data.rows, function (k, v) {
                if (v.state != "0") {
                    var option = document.createElement('option');
                    option.setAttribute("value", v.id);
                    option.innerText = v.name;
                    document.getElementById("patientCity").appendChild(option);
                }
            });
        }
    }, function (data) {
        layer.msg("级联初始化异常，请联系管理员！");
    });
}

/**
 * 区县级联
 */
function changecounty() {
    if ($("#patientCity").val() != null && $("#patientCity").val() != "") {
        vo["upId"] = $("#patientCity").val();
        doAjaxPost('manageCounAction.action?act=appAddressResult', {strJson: JSON.stringify(vo)}, function (data) {
            $("#patientArea").html("");
            var option1 = document.createElement('option');
            option1.setAttribute("value", "");
            option1.innerText = "--请选择--";
            document.getElementById("patientArea").appendChild(option1);
            if (data != null) {
                $.each(data.rows, function (k, v) {
                    if (v.state != "0") {
                        var option = document.createElement('option');
                        option.setAttribute("value", v.id);
                        option.innerText = v.name;
                        document.getElementById("patientArea").appendChild(option);
                    }
                });
            }
        }, function (data) {
            layer.msg("级联初始化异常，请联系管理员！");
        });
    }
}

/**
 * 街道级联
 */
function changestreet() {
    if ($("#patientArea").val() != null && $("#patientArea").val() != "") {
        vo["upId"] = $("#patientArea").val();
        doAjaxPost('manageCounAction.action?act=appAddressResult', {strJson: JSON.stringify(vo)}, function (data) {
            $("#patientStreet").html("");
            var option1 = document.createElement('option');
            option1.setAttribute("value", "");
            option1.innerText = "--请选择--";
            document.getElementById("patientStreet").appendChild(option1);
            if (data != null) {
                $.each(data.rows, function (k, v) {
                    if (v.state != "0") {
                        var option = document.createElement('option');
                        option.setAttribute("value", v.id);
                        option.innerText = v.name;
                        document.getElementById("patientStreet").appendChild(option);
                    }
                });
            }
        }, function (data) {
            layer.msg("级联初始化异常，请联系管理员！");
        });
    }
}

/**
 * 居委会级联
 */
function changeCommittee(entityvo) {
    if ($("#patientStreet").val() != null && $("#patientStreet").val() != "") {
        vo["upId"] = $("#patientStreet").val();
        doAjaxPost('manageCounAction.action?act=appAddressResult', {strJson: JSON.stringify(vo)}, function (data) {
            $("#patientNeighborhoodCommittee").html("");
            var option1 = document.createElement('option');
            option1.setAttribute("value", "");
            option1.innerText = "--请选择--";
            document.getElementById("patientNeighborhoodCommittee").appendChild(option1);
            if (data != null && data.rows != null) {
                $.each(data.rows, function (k, v) {
                    if (v.state != "0") {
                        var option = document.createElement('option');
                        option.setAttribute("value", v.id);
                        option.innerText = v.name;
                        document.getElementById("patientNeighborhoodCommittee").appendChild(option);
                    }
                });
            }
            if (entityvo != undefined && entityvo != null) {
                if (entityvo.xzqydm != undefined && entityvo.xzqydm != null) {
                    var option = $("#patientNeighborhoodCommittee").find("option");
                    for (var i = 0; i < option.length; i++) {
                        if (option[i].value == entityvo.xzqydm) {
                            option[i].selected = true;
                            break;
                        }
                    }
                }
            }
        }, function (data) {
            layer.msg("级联初始化异常，请联系管理员！");
        });
    }
}

/**
 * 初始化团队
 */
function findteem() {
    doAjaxPost('teamAction.action?act=findAll', {hospid: orgid}, function (data) {
        if (data != null) {
            $("#teamId").html("");
            var option1 = document.createElement('option');
            option1.setAttribute("value", "");
            option1.innerText = "--请选择--";
            document.getElementById("teamId").appendChild(option1);
            $.each(data, function (k, v) {
                var option = document.createElement('option');
                option.setAttribute("value", v.id);
                option.innerText = v.teamName;
                document.getElementById("teamId").appendChild(option);
            });
        }
    }, function (data) {
        layer.msg("初始化异常-001，请联系管理员！");
    });
}

/**
 * 触发查询团队队长
 */
function changeteam() {
    var teamid = $("#teamId").val();
    doAjaxPost('teamAction.action?act=findteemAll', {teamid: teamid}, function (data) {
        $("#drId").html("");
        var option1 = document.createElement('option');
        option1.setAttribute("value", "");
        option1.innerText = "--请选择--";
        document.getElementById("drId").appendChild(option1);
        if (data != null) {
            var code = HospAreaCode.substring(0, 4);
            $.each(data, function (k, v) {
                if (code == "3503") {
                    if (v.memState == "0" || v.memWorkType == "2" || v.memWorkType == "3") {
                        var option = document.createElement('option');
                        option.setAttribute("value", v.memDrId + ";;;" + v.drtel);
                        option.innerText = v.memDrName;
                        document.getElementById("drId").appendChild(option);
                    }
                } else {
                    if (v.memState == "0") {
                        var option = document.createElement('option');
                        option.setAttribute("value", v.memDrId + ";;;" + v.drtel);
                        option.innerText = v.memDrName;
                        document.getElementById("drId").appendChild(option);
                    }
                }
            });
        }
    }, function (data) {
        layer.msg("团队信息初始化异常，请联系管理员！");
    });
}

/**
 * 触发查询团队成员
 */
function changedr() {
    if ($("#drId").val() != null && $("#drId").val() != "") {
        var drtel = $("#drId").val().split(";;;");
        if (drtel[1] != "null" && drtel[1] != "") {
            $("#drTel").val(drtel[1]);
        }
    }
    var teamid = $("#teamId").val();
    doAjaxPost('teamAction.action?act=findteemAll', {teamid: teamid}, function (data) {
        $("#signDrAssistantId").html("");
        var option1 = document.createElement('option');
        option1.setAttribute("value", "");
        option1.innerText = "--请选择--";
        document.getElementById("signDrAssistantId").appendChild(option1);
        if (data != null) {
            $.each(data, function (k, v) {
                if (v.memState != "0") {
                    var option = document.createElement('option');
                    option.setAttribute("value", v.memDrId + ";;;" + v.drtel);
                    option.innerText = v.memDrName;
                    document.getElementById("signDrAssistantId").appendChild(option);
                }
            });
        }
    }, function (data) {
        layer.msg("团队信息初始化异常，请联系管理员！");
    });
}

/**
 * 触发查询助理电话号码
 */
function changedrass() {
    if ($("#signDrAssistantId").val() != null && $("#signDrAssistantId").val() != "") {
        var signDrAssistanttel = $("#signDrAssistantId").val().split(";;;");
        if (signDrAssistanttel[1] != "null" && signDrAssistanttel[1] != "") {
            $("#signDrAssistantTel").val(signDrAssistanttel[1]);
        }
    }
}


/**
 * 关闭窗口时渲染家庭成员列表并计算总的签约费用
 */
function familylist() {
    var totalGwpay = 0;// 公卫补助合计
    var totalYbpay = 0;// 医保支付合计
    var totalCzpay = 0;// 财政补助合计
    var totalZfpay = 0;// 自费支付合计
    if (fvolist.length > 0) {
        $("#family_list").html("");
        $.each(fvolist, function (k, v) {
            uiFromTmp("family_tlist", v, k);// 渲染家庭成员列表
            if (v.amountPrivateybz) {
                totalGwpay += parseInt(v.amountPrivateybz);
            }
            if (v.amountPrivate) {
                totalYbpay += parseInt(v.amountPrivate);
            }
            if (v.signczpay) {
                totalCzpay += parseInt(v.signczpay);
            }
            if (v.signzfpay) {
                totalZfpay += parseInt(v.signzfpay);
            }
        });
    }
    $("#amountPrivateybz").val(totalGwpay);
    $("#amountPrivate").val(totalYbpay);
    $("#signczpay").val(totalCzpay);
    $("#signzfpay").val(totalZfpay);
    $("#totalpay").val(totalGwpay + totalYbpay + totalCzpay + totalZfpay);
}

function uiFromTmp(id, data, idx) {
    var t = $($("#" + id + "").html());
    dataToUiTmp_idlsttr(t, data, idx);
    $("#family_list").append(t);
    t.fadeIn();
}

function dataToUiTmp_idlsttr(ui, data, idx) {
    data.idx = idx;
    ui.data("vo", data);
    ui.find("td[pofield=patientName]").text(data.patientName);
    ui.find("td[pofield=patientIdno]").text(data.patientIdno);
    ui.find("td[pofield=patientCard]").text(data.patientCard);
    ui.find("td[pofield=patientGender]").text(patientGender[data.patientGender]);
    ui.find("td[pofield=patientAge]").text(data.patientAge);
    ui.find("td[pofield=mfFmNickName]").text(householder[data.mfFmNickName]);
    ui.find("td[pofield=patientTel]").text(data.patientTel);
    ui.find("input[pofield=persGroup]").val(data.persGroup);
    ui.find("input[pofield=sJjType]").val(data.sJjType);
    ui.find("td[pofield=signFromDate]").text(data.signFromDate);
    ui.find("td[pofield=signToDate]").text(data.signToDate);
    ui.find("td[pofield=signzfpay]").text(data.signzfpay);
    ui.find("input[pofield=signpackageid]").val(data.signpackageid);
    ui.find("td[pofield=signtext]").text(data.signtext);
}

/**
 * 删除
 */
function subpageDelete(e) {
    thisvo = e.data("vo");
    e.remove();
    fvolist.splice(thisvo.idx);
}

/**
 * 查看
 */
function subpageLook(e) {
    thisvo = e.data("vo");
    if (thisvo != null) {
        var vojson = JSON.stringify(thisvo);
        var jsonToString = vojson.replace(/\"/g, "'");
        myOpen("查看家庭成员", "sign_family_subpageadd.jsp?openType=look&vojson=" + jsonToString);
    }
}

/**
 * 修改
 */
function subpageModfiy(e) {
    thisvo = e.data("vo");
    if (thisvo != null) {
        var vojson = JSON.stringify(thisvo);
        var jsonToString = vojson.replace(/\"/g, "'");
        myOpen("修改家庭成员", "sign_family_subpageadd.jsp?openType=modify&vojson=" + jsonToString, familylist);
    }
}

/**
 * 签约保存
 */
function familyAdd() {
    if (validator("form_vo")) {
        if (chnangeDate()) {
            if (fvolist.length > 0) {
                vo = uiToData2("form_vo", vo);
                vo.familyVo = fvolist;
                $("#roleadd").attr('disabled', true);
                $("#roleadd").css("background", "#808080");
                doAjaxPost('signAction.action?act=familySignAdd', {strJson: JSON.stringify(vo)}, function (data) {
                    $("#roleadd").attr('disabled', false);
                    $("#roleadd").css("background", "#1E90FF");
                    if (data.code == "800") {
                        layer.msg("签约成功！");
                    } else {
                        layer.msg(data.msg);
                    }
                }, function (data) {
                    layer.msg("签约保存失败！");
                });
            } else {
                layer.msg("请先填写家庭成员信息！");
            }
        }
    }
}

/**
 * 日期有效性检查
 */
function chnangeDate() {
    var strDate = $("#signFromDate").val();
    var endDate = $("#signToDate").val();
    var d1 = new Date(strDate.replace(/\-/g, "\/"));
    var d2 = new Date(endDate.replace(/\-/g, "\/"));
    if (strDate != "" && endDate != "" && d1 >= d2) {
        layer.msg("开始时间不能大于结束时间！");
        return false;
    } else {
        return true;
    }
}

/**
 * 获取服务器当前时间
 */
function findDate() {
    doAjaxPost('signAction.action?act=findDate', {}, function (data) {
        if (data.vo != null) {
            $("#signFromDate").val(data.vo.formDate);
            $("#signToDate").val(data.vo.toDate);
        }
    }, function (data) {
        layer.msg("初始化失败-003，请联系管理员！");
    });
}

/**
 * 经济类型检查
 */
function clickJJ(span) {
    if ($("input[id='ybrk']").is(':checked')) {
        if ($("input[id='jdlk']").is(':checked')) {
            layer.msg("一般人口与其他类型不能重复选择！！");
            $(span).find(":checkbox").prop("checked", false);
            return false;
        } else if ($("input[id='dbh']").is(':checked')) {
            layer.msg("一般人口与其他类型不能重复选择！！");
            $(span).find(":checkbox").prop("checked", false);
            return false;
        } else if ($("input[id='tkh']").is(':checked')) {
            layer.msg("一般人口与其他类型不能重复选择！！");
            $(span).find(":checkbox").prop("checked", false);
            return false;
        } else if ($("input[id='jsjt']").is(':checked')) {
            layer.msg("一般人口与其他类型不能重复选择！！");
            $(span).find(":checkbox").prop("checked", false);
            return false;
        }
    }
    return true;
}

/**
 * 人物角色
 */
var householder = {
    '49': '户主',
    '0': '老公',
    '1': '老婆',
    '2': '儿子',
    '3': '女儿',
    '4': '父亲',
    '5': '母亲',
    '6': '祖父',
    '7': '祖母',
    '8': '公公',
    '9': '婆婆',
    '10': '女婿',
    '11': '孙女',
    '12': '孙子',
    '13': '哥哥',
    '14': '姐姐',
    '15': '妹妹',
    '16': '弟弟',
    '17': '外祖母',
    '18': '外祖父',
    '19': '侄子',
    '20': '侄女',
    '21': '姐夫',
    '22': '表哥',
    '23': '表姐',
    '24': '表弟',
    '25': '表妹',
    '26': '外孙',
    '27': '外孙女',
    '28': '妹夫',
    '29': '堂妹',
    '30': '堂弟',
    '31': '堂姐',
    '32': '堂哥',
    '33': '外甥女',
    '34': '外甥',
    '35': '姨妈',
    '36': '姨丈',
    '37': '舅妈',
    '38': '舅舅',
    '39': '姑妈',
    '40': '朋友',
    '41': '婶婶',
    '42': '叔父',
    '43': '伯父',
    '44': '弟媳',
    '45': '嫂嫂',
    '46': '儿媳妇',
    '47': '姑丈',
    '48': '亲人'
};

/**
 * 性别
 */
var patientGender = {
    1: '男',
    2: '女'
};
