/**
 * Created by Dmao on 2017/11/2.
 */
var vo = {};
var form;

var ptidno = getQueryString("ptidno");
var ptsscno = getQueryString("ptsccno");
var ptname = decodeURI(decodeURI(getQueryString("ptname")));
var ptgender = getQueryString("ptgender");
var ptlx = getQueryString("ptlx");
var addtpye = getQueryString("addtpye");// 进入签约页面的方式（1-签约筛选进入，2-手动签约进入，空值-读卡签约进入）
var ptregion = getQueryString("ptregion");
var economicType = getQueryString("economicType");
var isGeneralPeople = getQueryString("isGeneralPeople");
var ptdfid;// 居民档案号
var c_id;
var c_teamId;
var c_drid;
var c_PatientId;
var isFilingGroup = 0;//建档立卡人群标志（1：是建档立卡人群，0：否）

/**
 * 页面加载完成后执行
 */
$(function () {

    Initialization();// 初始化参数

    if (ptgender != "" && ptgender != null) {
        var xb = ""
        if (ptgender == "男") {
            xb = "1";
        } else {
            xb = "2";
        }
        var checkptgender = document.getElementsByName("patientGender");
        for (var i = 0; i < checkptgender.length; i++) {
            if (checkptgender[i].value == xb) {
                checkptgender[i].checked = true;
                break;
            }
        }
    }

    if (ptlx != null && ptlx != "") {
        var lx = "";
        if (ptlx == "1" || ptlx == "2") {
            lx = "1";
        } else if (ptlx == "3") {
            lx = "2";
        }
        var checksignlx = document.getElementsByName("signlx");
        for (var i = 0; i < checksignlx.length; i++) {
            if (checksignlx[i].value == lx) {
                checksignlx[i].checked = true;
                break;
            }
        }
    }

    $("#hospId").val(orgid);
    $("#hospName").val(OrgName);
    $("#patientIdno").val(ptidno);
    $("#patientCard").val(ptsscno);
    if (ptname != null && ptname != "null") {
        $("#patientName").val(ptname);
    }
    $("#patientAddress").val(ptregion);// 居民详细地址
    $("#batchOperatorId").val(drid);// 操作人ID
    $("#batchOperatorName").val(username);// 操作人名称

    <!-- 计算年纪 -->
    if (ptidno != null && ptidno != "") {
        var nowtime = $("#nowtime").val();
        $("#patientAge").val(GetAge(ptidno, nowtime));
        var sex = GetSEX(ptidno);
        if (sex == "1") {
            $('input:radio').eq(0).attr("checked", 'true');
        } else if (sex == "2") {
            $('input:radio').eq(1).attr("checked", 'true');
        }
        patientAgePk();
    }

    <!-- 触发级联 -->
    changecity();
    <!-- 初始化团队 -->
    findteem();
    <!-- 调取居民健康档案 -->
    findjmda();
    <!-- 初始化服务器时间 -->
    findDate();
    <!-- 初始化服务项 -->
    findPk();
    <!-- 初始配置 -->
    signcode();
});

/**
 * 服务套餐特殊处理
 */
function npPKspecial(HospAreaCode, checkbox) {
    var id = $(checkbox).val();
    if (HospAreaCode.substr(0, 4) == "3507") {// 南平
        if (id == "np_default") {
            $(checkbox).prop("checked", true);
            Pkradio($(checkbox));
        }
    } else if (HospAreaCode.substr(0, 4) == "3503") {// 莆田
        if (id == "pt_20171218") {
            $(checkbox).prop("checked", true);
            Pkradio($(checkbox));
        } else if (id == "pt_20171218001") {
            $(checkbox).prop("disabled", true);
        }
    } else if (HospAreaCode.substr(0, 4) == "3506") {// 漳州
        if (id == "38d4dd31-bf1d-4372-a4a8-5cf5865d296f") {
            $(checkbox).prop("checked", true);
            Pkradio($(checkbox));
        }
    } else if (HospAreaCode.substr(0, 4) == "3501") {// 福州
        if (id == "fb4c94c1-9342-4aae-a5db-8646ad061f07") {
            $(checkbox).prop("checked", true);
            Pkradio($(checkbox));
        }
    }
}

/**
 * 南平关联居民档案后的相应判断
 */
function npjmdaJudge(entityvo) {
    if (HospAreaCode.substr(0, 4) == "3507" && entityvo.sqh != orgid.substr(3)) {
        showMsg("该居民档案不属于本机构!", function () {
            window.location.href = "sign_card.jsp";
        });
        return false;
    }
    if (HospAreaCode.substr(0, 4) == "3507" && entityvo.jmxm != $("#patientName").val()) {
        layer.confirm('该社保卡姓名与居民档案上的姓名不一致,是否继续签约?', {
            btn: ['继续', '否'],
            btn2: function () {
                window.location.href = "sign_card.jsp";
            }
        });
    }
}

/**
 * 获取社保卡信息
 * WangCheng
 */
function getSecurityCard(ptidno) {
    vo["patientIdno"] = ptidno;
    vo["orgId"] = orgid;
    doAjaxPost("signAction.action?act=getSecurityCard", {strJson: JSON.stringify(vo)}, function (data) {
        if (data.vo != null) {
            var object = eval("(" + data.vo + ")");
            $.each(object, function (k, v) {
                if (v.status == "正常") {
                    $("#patientCard").val(v.ssCard);
                }
            });
        }
        if (HospAreaCode.substr(0, 4) == "3501" || HospAreaCode.substr(0, 4) == "3504") {// 福州和三明个性化需求、add by WangCheng
            findArchivingCardPeople(vo);
        }
    });
}

/**
 * 查看是否为建档立卡人群
 */
function findArchivingCardPeople(vo) {
    vo["ptidno"] = vo.patientIdno;
    doAjaxPost("signAction.action?act=findArchivingCardPeople", {strJson: JSON.stringify(vo)}, function (data) {
        if (data.map != null) {
            if (data.map.result == "1") {// 如果是建档立卡人群的话默认选择惠普服务包
                if (HospAreaCode.substr(0, 4) == "3501") {
                    $("input[id='fb4c94c1-9342-4aae-a5db-8646ad061f07']").prop("checked", true);// 福州默认选择惠普服务包
                    $("input[name = 'signpackageid']").attr("disabled", "disabled");
                    isFilingGroup = 1;
                }
                if (HospAreaCode.substr(0, 4) == "3504") {
                    isFilingGroup = 1;
                }
            } else {
                layer.msg("该居民不是建档立卡人群，不支持手动签约！");
            }
        }
    });
}

/**
 * 查询某个居民的所属服务人群
 */
function findFwType(ptidno, orgid) {
    vo['idNo'] = ptidno;
    vo['orgId'] = orgid;
    doAjaxPost('signAction.action?act=findFwType', {strJson: JSON.stringify(vo)}, function (data) {
        if (data.code == "800") {
            // 清空选项
            $("#ordinary").prop("checked", false);
            $("#young").prop("checked", false);
            $("#cjr").prop("checked", false);
            $("#gxy").prop("checked", false);
            $("#jhb").prop("checked", false);
            $("#oldman").prop("checked", false);
            $("#tnb").prop("checked", false);
            $("#yuncf").prop("checked", false);
            $("#jsb").prop("checked", false);

            // 重新勾选选项
            if (data.vo != "null" && data.vo != "" && data.vo != null) {
                var flag = 0;
                var vo = JSON.parse(data.vo);
                if (vo.comservice == '1') {
                    $("#ordinary").prop("checked", true);
                    flag++;
                }
                if (vo.is06child == '1') {
                    $("#young").prop("checked", true);
                    flag++;
                }
                if (vo.iscjr == '1') {
                    $("#cjr").prop("checked", true);
                    flag++;
                }
                if (vo.isgxy == '1') {
                    $("#gxy").prop("checked", true);
                    flag++;
                }
                if (vo.isjhb == '1') {
                    $("#jhb").prop("checked", true);
                    flag++;
                }
                if (vo.islnr == '1') {
                    $("#oldman").prop("checked", true);
                    flag++;
                }
                if (vo.istnb == '1') {
                    $("#tnb").prop("checked", true);
                    flag++;
                }
                if (vo.isycf == '1') {
                    $("#yuncf").prop("checked", true);
                    flag++;
                }
                if (vo.iszxjsb == '1') {
                    $("#jsb").prop("checked", true);
                    flag++;
                }
                if (flag === 0) {// 如果是其他疾病类型则默认选中普通服务
                    $("#ordinary").prop("checked", true);
                }
            } else {// 没有查询到服务人群信息时默认设置为普通服务
                $("#ordinary").prop("checked", true);// 普通服务
            }
        } else {
            layer.msg("获取服务人群时出错，请联系系统管理员！");
        }
    });
}

/**
 * 调取居民健康档案
 */
function findjmda() {
    if (ptidno != null && ptidno != "") {
        vo["idNo"] = ptidno;
        vo["orgId"] = orgid;
        vo["type"] = "2";
        doAjaxPost('signAction.action?act=findjmda', {strJson: JSON.stringify(vo)}, function (data) {
            if (data.vo != null) {
                var datavo = JSON.parse(data.vo);
                if (datavo.entity != null && datavo.entity != "") {
                    var entityvo = datavo.entity;
                    ptdfid = entityvo.jmdah;
                    if (jdBeforeSign == "1") {// 开启了签约前先建档功能
                        findFwType(ptidno, orgid);
                    }
                    npjmdaJudge(entityvo);// 南平关联居民档案时的相应判断
                    // 添加居民姓名回显、by WangCheng
                    $("#patientName").val(entityvo.jmxm);
                    $("#patientjtda").val(entityvo.jtdah);
                    $("#patientjmda").val(entityvo.jmdah);
                    $("#patientAddress").val(entityvo.sheng + entityvo.shi + entityvo.xian + entityvo.xiang + entityvo.cun + entityvo.mphm);
                    //$("#cityid").html("");
                    if (entityvo.shi != undefined && entityvo.shi != null) {
                        var option = $("#patientCity").find("option");
                        var shibm = entityvo.jmdah.substr(0, 4) + "00000000";
                        for (var i = 0; i < option.length; i++) {
                            if (option[i].value == shibm) {
                                option[i].selected = true;
                                break;
                            }
                        }
                        jmdachangecounty(entityvo);
                        //$("#cityid").append("<input id='patientCity' pofield='patientCity' class='layui-input' value="+ entityvo.shi +">");
                    }
                    //$("#county").html("");
                    /*if (entityvo.xian != undefined && entityvo.xian != null) {
                        var option = $("#patientArea").find("option");
                        for (var i = 0; i < option.length; i++) {
                            if (option[i].innerText == entityvo.xian) {
                                option[i].selected = true;
                                break;
                            }
                        }
                        changestreet();
                        //$("#county").append("<input id='patientArea' pofield='patientArea' class='layui-input' value="+ entityvo.xian +">");
                    }
                    //$("#street").html("");
                    if (entityvo.xiang != undefined && entityvo.xiang != null) {
                        var option = $("#patientStreet").find("option");
                        for (var i = 0; i < option.length; i++) {
                            if (option[i].innerText == entityvo.xiang) {
                                option[i].selected = true;
                                break;
                            }
                        }
                        changeCommittee();
                        //$("#street").append("<input id='patientStreet' pofield='patientStreet' class='layui-input' value="+ entityvo.xiang +">");
                    }*/
                    if (entityvo.lxdh != null && entityvo.lxdh != "") {
                        $("#patientTel").val(entityvo.lxdh);
                    }
                } else {
                    if (jdBeforeSign == "1") {// 开启了签约前先建档功能
                        openOneWindow();// 提示建档
                    } else {
                        var areaCode = HospAreaCode.substring(0, 4);
                        if (areaCode == '3503') {// 莆田
                            layer.msg("该居民当前未建立健康档案信息，请先建档！");
                            if (addtpye == "2") {
                                setTimeout(function () {
                                    window.location.href = "sign_add.jsp?addtpye=2";
                                }, 2000);

                            } else {
                                setTimeout(function () {
                                    history.go(-1);
                                }, 2000);
                            }
                        }
                    }
                }
            } else {
                if (jdBeforeSign == "1") {// 开启了签约前先建档功能
                    openOneWindow();// 提示建档
                }
            }
        }, function (data) {
            layer.msg("调取居民档案异常，请联系管理员！");
        });
    } else {
        //layer.msg("初始化异常-002，请联系管理员！");
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
                if (code == "3503") {// 莆田
                    if (v.memState == "0" || v.memWorkType == "2" || v.memWorkType == "3") {
                        var option = document.createElement('option');
                        option.setAttribute("value", v.memDrId + ";;;" + v.drtel);
                        option.innerText = v.memDrName;
                        document.getElementById("drId").appendChild(option);
                    }
                } else if (code == "3507") {// 南平
                    if (v.memWorkType == "2" || v.memWorkType == "3") {
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
    })
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
 * 查询助理电话号码
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
 * 根据省查询市
 */
function changecity() {
    var areaCode = HospAreaCode.substring(0, 2);
    vo["areaCode"] = areaCode + "0000000000";
    doAjaxPost('manageCounAction.action?act=findSubAreas', {strJson: JSON.stringify(vo)}, function (data) {
        $("#patientCity").html("");
        var option1 = document.createElement('option');
        option1.setAttribute("value", "");
        option1.innerText = "---请选择---";
        document.getElementById("patientCity").appendChild(option1);
        if (data != null) {
            $.each(data.rows, function (k, v) {
                if (v.state != "0") {
                    var option = document.createElement('option');
                    option.setAttribute("value", v.id);
                    option.innerText = v.areaSname;
                    document.getElementById("patientCity").appendChild(option);
                }
            });
        }
    }, function (data) {
        layer.msg("级联初始化异常，请联系管理员！");
    });
}

/**
 * 根据市查询区县
 */
function changecounty() {
    if ($("#patientCity").val() != null && $("#patientCity").val() != "") {
        vo["areaCode"] = $("#patientCity").val();
        doAjaxPost('manageCounAction.action?act=findSubAreas', {strJson: JSON.stringify(vo)}, function (data) {
            $("#patientArea").html("");
            var option1 = document.createElement('option');
            option1.setAttribute("value", "");
            option1.innerText = "---请选择---";
            document.getElementById("patientArea").appendChild(option1);
            if (data != null) {
                $.each(data.rows, function (k, v) {
                    if (v.state != "0") {
                        var option = document.createElement('option');
                        option.setAttribute("value", v.id);
                        option.setAttribute("source", v.source);
                        option.setAttribute("level", v.level);
                        option.innerText = v.areaSname;
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
 * 根据区县查询街道或乡镇
 */
function changestreet() {
    if ($("#patientArea").val() != null && $("#patientArea").val() != "") {
        vo["areaCode"] = $("#patientArea").val();
        doAjaxPost('manageCounAction.action?act=findSubAreas', {strJson: JSON.stringify(vo)}, function (data) {
            $("#patientStreet").html("");
            var option1 = document.createElement('option');
            option1.setAttribute("value", "");
            option1.innerText = "---请选择---";
            document.getElementById("patientStreet").appendChild(option1);
            if (data != null) {
                $.each(data.rows, function (k, v) {
                    if (v.state != "0") {
                        var option = document.createElement('option');
                        option.setAttribute("value", v.id);
                        option.setAttribute("source", v.source);
                        option.setAttribute("level", v.level);
                        option.innerText = v.areaSname;
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
 * 根据乡镇或街道查询居委会或村
 */
function changeCommittee(entityvo) {
    if ($("#patientStreet").val() != null && $("#patientStreet").val() != "") {
        vo["areaCode"] = $("#patientStreet").val();
        doAjaxPost('manageCounAction.action?act=findSubAreas', {strJson: JSON.stringify(vo)}, function (data) {
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
                        option.innerText = v.areaSname;
                        document.getElementById("patientNeighborhoodCommittee").appendChild(option);
                    }
                });
            }
            if (entityvo != undefined && entityvo != null) {
                if (entityvo.xzqydm != undefined && entityvo.xzqydm != null) {
                    var option = $("#patientNeighborhoodCommittee").find("option");
                    for (var i = 0; i < option.length; i++) {
                        if (option[i].value == entityvo.xzqydm.substr(0, 12)) {
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
 * 查询协议起止日期
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
 * 设置日期可选范围
 */
function wdate() {
    var fromdate = $("#signFromDate").val();
    var gaga = new Date(fromdate.replace(/\-/g, "\/"));
    var _y = gaga.getFullYear();
    var _m = gaga.getMonth();
    var _d = gaga.getDate();
    var new_gaga = new Date(_y + 1, _m, _d - 1);
    var new_y = new_gaga.getFullYear();
    var new_m = new_gaga.getMonth() + 1;
    new_m = addPreZero(new_m);
    var new_d = new_gaga.getDate();
    var todate = new_y + '-' + new_m + '-' + new_d;
    if (openYear == "1") {// 开启自然年度签约
        $("#signToDate").val(_y + '-12-31');// 签约截止日期为当年最后一天
    } else {
        $("#signToDate").val(todate);
    }
}

/**
 * 时间有效性判断
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
 * 多选事件触发
 */
function clickP(span) {
    if (jdBeforeSign == "1") {// 开启了签约前先建档功能
        // if ($(span).find(":checkbox").is(':checked')) {
        //     $(span).find(":checkbox").prop("checked", false);
        // } else {
        //     $(span).find(":checkbox").prop("checked", true);
        // }
        // openThreeWindow();// 提示修改档案

        if ($(span).find(":checkbox").is(':checked')) {
            if ($(span).find(":checkbox").val() != "8" && $(span).find(":checkbox").val() != "7" && $(span).find(":checkbox").val() != "3") {

                $(span).find(":checkbox").prop("checked", false);
                openThreeWindow();// 提示修改档案
            } else {
                if ($(span).find(":checkbox").val() == "3") {
                    doAlert(1);//提示孕妇建档
                    $(span).find(":checkbox").prop("checked", false);
                } else {
                    if ($("input[id='ordinary']").is(':checked') && $(span).find(":checkbox").val() != "1") {
                        doAlert(3); //提示普通人与其他类型不能重复选择！！
                        $(span).find(":checkbox").prop("checked", false);
                    } else {
                        $(span).find(":checkbox").prop("checked", true);
                    }
                }
            }
        } else {
            if ($(span).find(":checkbox").val() == "3") {
                doAlert(2);//提示孕妇归档
                $(span).find(":checkbox").prop("checked", true);
            } else {
                if ($(span).find(":checkbox").val() != "1" && $(span).find(":checkbox").val() != "8" && $(span).find(":checkbox").val() != "7") {
                    openThreeWindow();// 提示修改档案
                    $(span).find(":checkbox").prop("checked", true);
                } else {
                    $(span).find(":checkbox").prop("checked", false);
                }
            }
        }
    } else {
        if ($("input[id='oldman']").is(':checked') && $("input[id='young']").is(':checked')) {
            layer.msg("老年人与儿童不能重复选择！！");
            $(span).find(":checkbox").prop("checked", false);
            return false;
        }

        if ($("input[id='yuncf']").is(':checked')) {
            if ($("input[id='young']").is(':checked')) {
                layer.msg("孕产妇与儿童不能重复选择！！");
                $(span).find(":checkbox").prop("checked", false);
                return false;
            } else if ($("input[id='oldman']").is(':checked')) {
                layer.msg("孕产妇与老年人不能重复选择！！");
                $(span).find(":checkbox").prop("checked", false);
                return false;
            }
        }

        if ($("input[id='ordinary']").is(':checked')) {
            if ($("input[id='oldman']").is(':checked')) {
                layer.msg("普通人与其他类型不能重复选择！");
                $(span).find(":checkbox").prop("checked", false);
                return false;
            } else if ($("input[id='gxy']").is(':checked')) {
                layer.msg("普通人与其他类型不能重复选择！！");
                $(span).find(":checkbox").prop("checked", false);
                return false;
            } else if ($("input[id='tnb']").is(':checked')) {
                layer.msg("普通人与其他类型不能重复选择！！");
                $(span).find(":checkbox").prop("checked", false);
                return false;
            } else if ($("input[id='young']").is(':checked')) {
                layer.msg("普通人与其他类型不能重复选择！！");
                $(span).find(":checkbox").prop("checked", false);
                return false;
            } else if ($("input[id='yuncf']").is(':checked')) {
                layer.msg("普通人与其他类型不能重复选择！！");
                $(span).find(":checkbox").prop("checked", false);
                return false;
            } else if ($("input[id='jhb']").is(':checked')) {
                layer.msg("普通人与其他类型不能重复选择！！");
                $(span).find(":checkbox").prop("checked", false);
                return false;
            } else if ($("input[id='jsb']").is(':checked')) {
                layer.msg("普通人与其他类型不能重复选择！！");
                $(span).find(":checkbox").prop("checked", false);
                return false;
            } else if ($("input[id='cjr']").is(':checked')) {
                layer.msg("普通人与其他类型不能重复选择！！");
                $(span).find(":checkbox").prop("checked", false);
                return false;
            }else if($("input[id='nxg']").is(':checked')) {
                layer.msg("普通人与其他类型不能重复选择！！");
                $(span).find(":checkbox").prop("checked", false);
                return false;
            }else if($("input[id='gxb']").is(':checked')) {
                layer.msg("普通人与其他类型不能重复选择！！");
                $(span).find(":checkbox").prop("checked", false);
                return false;
            }else if($("input[id='azhz']").is(':checked')) {
                layer.msg("普通人与其他类型不能重复选择！！");
                $(span).find(":checkbox").prop("checked", false);
                return false;
            }else if($("input[id='qt']").is(':checked')) {
                layer.msg("普通人与其他类型不能重复选择！！");
                $(span).find(":checkbox").prop("checked", false);
                return false;
            }
        }

        if ($("input[id='qt']").is(':checked')) {
            if ($("input[id='oldman']").is(':checked')) {
                layer.msg("其他与其他类型不能重复选择！");
                $(span).find(":checkbox").prop("checked", false);
                return false;
            } else if ($("input[id='gxy']").is(':checked')) {
                layer.msg("其他与其他类型不能重复选择！！");
                $(span).find(":checkbox").prop("checked", false);
                return false;
            } else if ($("input[id='tnb']").is(':checked')) {
                layer.msg("其他与其他类型不能重复选择！！");
                $(span).find(":checkbox").prop("checked", false);
                return false;
            } else if ($("input[id='young']").is(':checked')) {
                layer.msg("其他与其他类型不能重复选择！！");
                $(span).find(":checkbox").prop("checked", false);
                return false;
            } else if ($("input[id='yuncf']").is(':checked')) {
                layer.msg("其他与其他类型不能重复选择！！");
                $(span).find(":checkbox").prop("checked", false);
                return false;
            } else if ($("input[id='jhb']").is(':checked')) {
                layer.msg("其他与其他类型不能重复选择！！");
                $(span).find(":checkbox").prop("checked", false);
                return false;
            } else if ($("input[id='jsb']").is(':checked')) {
                layer.msg("其他与其他类型不能重复选择！！");
                $(span).find(":checkbox").prop("checked", false);
                return false;
            } else if ($("input[id='cjr']").is(':checked')) {
                layer.msg("其他与其他类型不能重复选择！！");
                $(span).find(":checkbox").prop("checked", false);
                return false;
            } else if ($("input[id='ordinary']").is(':checked')) {
                layer.msg("其他与其他类型不能重复选择！！");
                $(span).find(":checkbox").prop("checked", false);
                return false;
            }else if ($("input[id='nxg']").is(':checked')) {
                layer.msg("其他与其他类型不能重复选择！！");
                $(span).find(":checkbox").prop("checked", false);
                return false;
            }else if ($("input[id='gxb']").is(':checked')) {
                layer.msg("其他与其他类型不能重复选择！！");
                $(span).find(":checkbox").prop("checked", false);
                return false;
            }else if ($("input[id='azhz']").is(':checked')) {
                layer.msg("其他与其他类型不能重复选择！！");
                $(span).find(":checkbox").prop("checked", false);
                return false;
            }
        }
    }

    var flag = true;
    var checks = document.getElementsByName("signpackageid");
    for (i = 0; i < checks.length; i++) {
        if (checks[i].checked) {
            flag = false;
        }
    }
    if (flag) {
        layer.msg("请选择签约套餐！");
        return false;
    }
    return true;
}

/**
 * 保存时验证
 */
function checkP() {
    if ($("input[id='oldman']").is(':checked') && $("input[id='young']").is(':checked')) {
        layer.msg("老年人与儿童不能重复选择！！");
        $(span).find(":checkbox").prop("checked", false);
        return false;
    }

    if ($("input[id='yuncf']").is(':checked')) {
        if ($("input[id='young']").is(':checked')) {
            layer.msg("孕产妇与儿童不能重复选择！！");
            $(span).find(":checkbox").prop("checked", false);
            return false;
        } else if ($("input[id='oldman']").is(':checked')) {
            layer.msg("孕产妇与老年人不能重复选择！！");
            $(span).find(":checkbox").prop("checked", false);
            return false;
        }
    }

    if ($("input[id='ordinary']").is(':checked')) {
        if ($("input[id='oldman']").is(':checked')) {
            layer.msg("普通人与其他类型不能重复选择！！");
            $(span).find(":checkbox").prop("checked", false);
            return false;
        } else if ($("input[id='gxy']").is(':checked')) {
            layer.msg("普通人与其他类型不能重复选择！！");
            $(span).find(":checkbox").prop("checked", false);
            return false;
        } else if ($("input[id='tnb']").is(':checked')) {
            layer.msg("普通人与其他类型不能重复选择！！");
            $(span).find(":checkbox").prop("checked", false);
            return false;
        } else if ($("input[id='young']").is(':checked')) {
            layer.msg("普通人与其他类型不能重复选择！！");
            $(span).find(":checkbox").prop("checked", false);
            return false;
        } else if ($("input[id='yuncf']").is(':checked')) {
            layer.msg("普通人与其他类型不能重复选择！！");
            $(span).find(":checkbox").prop("checked", false);
            return false;
        } else if ($("input[id='jhb']").is(':checked')) {
            layer.msg("普通人与其他类型不能重复选择！！");
            $(span).find(":checkbox").prop("checked", false);
            return false;
        } else if ($("input[id='jsb']").is(':checked')) {
            layer.msg("普通人与其他类型不能重复选择！！");
            $(span).find(":checkbox").prop("checked", false);
            return false;
        } else if ($("input[id='cjr']").is(':checked')) {
            layer.msg("普通人与其他类型不能重复选择！！");
            $(span).find(":checkbox").prop("checked", false);
            return false;
        }
    }

    var flag = true;
    var checks = document.getElementsByName("signpackageid");
    for (i = 0; i < checks.length; i++) {
        if (checks[i].checked) {
            flag = false;
        }
    }
    if (flag) {
        layer.msg("请选择签约套餐！");
        return false;
    }
    return true;
}

/**
 * 提示建档
 */
function openOneWindow() {
    layer.open({
        type: 1,
        title: false,
        closeBtn: false,
        area: '500px;',// 弹框宽度
        offset: [150, ($(window).width() / 2 - 250)],// 弹框位置
        shade: 0.3,
        id: 'layer_open_one',
        btn: ['前往基卫建档', "换个居民签约"],
        btnAlign: 'c',
        moveType: 1,
        content: '<div style="padding: 50px; line-height: 22px; background-color: #393D49;' +
        'color: #fff; font-weight: 100;text-align : center;">该居民未建立居民健康档案，请建档后签约！</div>',
        btn1: function () {
            layer.closeAll();
            openTwoWindow();// 提示建档后继续签约
            doAjaxPostSync('signAction.action?act=getCreateDwellerfileUrl', {strJson: JSON.stringify(vo)}, function (data) {
                if (data.map != null && data.map != "") {
                    window.open(data.map.createDwellerfileUrl);
                } else {
                    layer.msg("接口出错，请联系系统管理员！");
                }
            });
        },
        btn2: function () {
            layer.closeAll();
            if (addtpye == "1") {// 签约筛选进入
                window.history.go(-1);
            } else if (addtpye == "2") {// 手动签约
                window.location.href = "sign_add.jsp?addtpye=2";
            } else {// 读卡签约
                window.location.href = "sign_card.jsp";
            }
        }
    });
}

/**
 * 提示继续签约，点击确定后重新查询档案
 */
function openTwoWindow() {
    layer.open({
        type: 1,
        title: false,
        closeBtn: false,
        area: '500px;',// 弹框宽度
        offset: [150, ($(window).width() / 2 - 250)],// 弹框位置
        shade: 0.3,
        id: 'layer_open_two',
        btn: ['继续签约'],
        btnAlign: 'c',
        moveType: 1,
        content: '<div style="padding: 50px; line-height: 22px; background-color: #393D49;' +
        'color: #fff; font-weight: 100; text-align : center;">档案新建成功就可以继续签约啦！</div>',
        btn1: function () {
            layer.closeAll();
            findjmda();
        }
    });
}

/**
 * 提示通过修改档案来修改服务人群
 */
function openThreeWindow() {
    layer.open({
        type: 1,
        title: false,
        closeBtn: false,
        area: '500px;',// 弹框宽度
        offset: [150, ($(window).width() / 2 - 250)],// 弹框位置
        shade: 0.3,
        id: 'layer_open_three',
        btn: ['前往基卫修改档案', '取消'],
        btnAlign: 'c',
        moveType: 1,
        content: '<div style="padding: 50px; line-height: 22px; background-color: #393D49;' +
        'color: #fff; font-weight: 100;text-align : center;">所选服务人群类型与该居民健康档案不符，是否前往修改居民健康档案？</div>',
        btn1: function () {
            layer.closeAll();
            openFourWindow();// 提示修改档案后继续签约
            doAjaxPostSync('signAction.action?act=getUpdateDwellerfileUrl&df_id=' + ptdfid, {strJson: JSON.stringify(vo)}, function (data) {
                if (data.map != null && data.map != "") {
                    window.open(data.map.updateDwellerfileUrl);
                } else {
                    layer.msg("接口出错，请联系系统管理员!");
                }
            });
        },
        btn2: function () {
            layer.closeAll();
        }
    });
}

/**
 * 提示继续签约，点击确定后重新查询服务人群
 */
function openFourWindow() {
    layer.open({
        type: 1,
        title: false,
        closeBtn: false,
        area: '500px;',// 弹框宽度
        offset: [150, ($(window).width() / 2 - 250)],// 弹框位置
        shade: 0.3,
        id: 'layer_open_four',
        btn: ['继续签约'],
        btnAlign: 'c',
        moveType: 1,
        content: '<div style="padding: 50px; line-height: 22px; background-color: #393D49;' +
        'color: #fff; font-weight: 100; text-align : center;">档案修改成功就可以继续签约啦！</div>',
        btn1: function () {
            layer.closeAll();
            findFwType(ptidno, orgid);
        }
    });
}

/**
 * 检查签约类型
 */
function checkLx() {
    var pkglx = $('#singlx input[name="signlx"]:checked').val();// 签约类型
    if (pkglx == "" || pkglx == null) {
        layer.msg("医保类型必须选择一项！");
        return false;
    } else {
        if (pkglx == 1) {
            vo["yuuser"] = yuuser;
            vo["ypaw"] = ypaw;
        } else if (pkglx == 2) {
            vo["xuuser"] = xuuser;
            vo["xpaw"] = xpaw;
        }
        vo["singlx"] = pkglx;
        return true;
    }
}

/**
 * 签约保存
 */
function signAdd() {
    if (HospAreaCode.substr(0, 4) == "3501" || HospAreaCode.substr(0, 4) == "3504") {// 福州跟三明手动签约不是建档立卡人群不支持手动签约、add by WangCheng
        if (addtpye == "2") {
            if (isFilingGroup == "0") {
                layer.msg("该居民不是建档立卡人群，不支持手动签约！");
                return;
            }
        }
    }
    if (validator("form_vo")) {
        if (chnangeDate()) {
            if (checkP()) {
                if (checkLx()) {
                    vo = uiToData2("form_vo", vo);
                    vo.patientTel = $.trim(vo.patientTel);// 联系方式去空格
                    // 泉州添加签约医生限制（必须有身份证号码）
                    // 泉州添加医保签约信息
                    // HospAreaCode = '3505777';
                    if (HospAreaCode != null) {
                        var signlx = $('#singlx input[name="signlx"]:checked').val();
                        if (vo.patientAge.indexOf("月") != -1) {
                            vo.patientAge = 0;
                        }
                        if (HospAreaCode.substring(0, 4) == "3505" && (signlx == "0" || signlx == "1")) {
                            doAjaxPost('registerAction.action?act=getDoctorIdNo', {strJson: JSON.stringify(vo)}, function (data) {
                                if (data.code == "800") {
                                    if (null != data.vo.drIdno && "" != data.vo.drIdno) {
                                        $("#roleadd").attr('disabled', true);
                                        $("#roleadd").css("background", "#808080");
                                        doAjaxPost('signAction.action?act=signAdd', {strJson: JSON.stringify(vo)}, function (data) {
                                            $("#roleadd").attr('disabled', false);
                                            $("#roleadd").css("background", "#1E90FF");
                                            if (data.code == "800") {
                                                // 泉州添加医保签约信息
                                                // HospAreaCode = '3505777';
                                                if (HospAreaCode != null) {
                                                    var signlx = $('#singlx input[name="signlx"]:checked').val();
                                                    if (HospAreaCode.substring(0, 4) == "3505" && (signlx == "0" || signlx == "1")) {
                                                        doAjaxPost('registerAction.action?act=addRegisterInfo', {idno: vo.patientIdno}, function (data_qz) {
                                                            if (data_qz.code == "800") {
                                                                layer.msg("医保登记信息添加成功！");
                                                            }
                                                        });
                                                    }
                                                }
                                                layer.open({
                                                    type: 1,
                                                    title: false, // 不显示标题栏
                                                    closeBtn: false,
                                                    area: '300px;',
                                                    shade: 0.8,
                                                    id: 'LAY_layuipro',// 设定一个id，防止重复弹出
                                                    resize: false,
                                                    btn: ['确定', '协议打印'],// '发票打印'
                                                    btnAlign: 'c',
                                                    moveType: 1, // 拖拽模式，0或者1
                                                    content: '<div style="padding: 50px; line-height: 22px; background-color: #f2f2f2; color: #000; font-weight: 300;">恭喜你，签约成功！！</div>',
                                                    yes: function (index, layero) {
                                                        window.location.href = "sign_look.jsp?id=" + data.vo.id + "&type=" + true + "&typeadd=" + true + "&addtpye=" + addtpye + "&protocoltpye=800";
                                                    },
                                                    btn2: function () {
                                                        window.location.href = "sign_protocol.jsp?teamId=" + data.vo.signTeamId + "&PatientId=" + data.vo.signPatientId + "&addtpye=2&protocoltpye=800";//查看协议界面返回读卡界面时addtpye设置为2
                                                    }
                                                });
                                            } else {
                                                layer.msg(data.msg);
                                            }
                                        }, function (data) {
                                            layer.msg("签约保存失败！");
                                        });
                                    } else {
                                        layer.msg("签约医生的身份证信息为空!请到基卫系统补全医生身份证信息！");
                                        return;
                                    }
                                }
                            });
                        } else {
                            $("#roleadd").attr('disabled', true);
                            $("#roleadd").css("background", "#808080");
                            if (HospLabelState == '1') {   // 判断是否有疾病标签的权限
                                $("#roleadd").attr('disabled', false);
                                $("#roleadd").css("background", "#1E90FF");

                                // 添加疾病标签
                                var t = 1;
                                // 添加标签弹出框
                                if ($("input[id='gxy']").is(':checked') && t == 1) {
                                    layer.open({
                                        type: 2,
                                        offset: '100px',
                                        area: ['600px', '450px'],
                                        title: '疾病类型标签',
                                        shadeClose: false,
                                        content: 'sign_flag.jsp?addtpye=add',
                                        success: function (layero, index) {
                                            var iframe = window['layui-layer-iframe' + index];
                                            iframe.child(vo);//调用弹出层的方法，用以传值
                                        },
                                        cancel: function (index, layero) {
                                            doAdd(vo)
                                        }
                                    });
                                    t = 0;
                                }
                                if ($("input[id='tnb']").is(':checked') && t == 1) {
                                    layer.open({
                                        type: 2,
                                        offset: '100px',
                                        area: ['600px', '450px'],
                                        title: '疾病类型标签',
                                        shadeClose: false,
                                        content: 'sign_flag.jsp?addtpye=add',
                                        success: function (layero, index) {
                                            var iframe = window['layui-layer-iframe' + index];
                                            iframe.child(vo);
                                        },
                                        cancel: function (index, layero) {
                                            doAdd(vo)
                                        }
                                    });
                                    t = 0;
                                }
                                if ($("input[id='jhb']").is(':checked') && t == 1) {
                                    layer.open({
                                        type: 2,
                                        offset: '100px',
                                        area: ['600px', '450px'],
                                        title: '疾病类型标签',
                                        shadeClose: false,
                                        content: 'sign_flag.jsp?addtpye=add',
                                        success: function (layero, index) {
                                            var iframe = window['layui-layer-iframe' + index];
                                            iframe.child(vo);
                                        },
                                        cancel: function (index, layero) {
                                            doAdd(vo)
                                        }
                                    });
                                    t = 0;
                                }
                                if ($("input[id='cjr']").is(':checked') && t == 1) {
                                    layer.open({
                                        type: 2,
                                        offset: '100px',
                                        area: ['600px', '450px'],
                                        title: '疾病类型标签',
                                        shadeClose: false,
                                        content: 'sign_flag.jsp?addtpye=add',
                                        success: function (layero, index) {
                                            var iframe = window['layui-layer-iframe' + index];
                                            iframe.child(vo);
                                        },
                                        cancel: function (index, layero) {
                                            doAdd(vo)
                                        }
                                    });
                                    t = 0;
                                }
                                if ($("input[id='jsb']").is(':checked') && t == 1) {
                                    layer.open({
                                        type: 2,
                                        offset: '100px',
                                        area: ['600px', '450px'],
                                        title: '疾病类型标签',
                                        shadeClose: false,
                                        content: 'sign_flag.jsp?addtpye=add',
                                        success: function (layero, index) {
                                            var iframe = window['layui-layer-iframe' + index];
                                            iframe.child(vo);
                                        },
                                        cancel: function (index, layero) {
                                            doAdd(vo)
                                        }
                                    });
                                    t = 0;
                                }
                                if (t == 1) {
                                    vo["lableState"] = "1"; // 传入当前是否拥有疾病标签权限 0为否，1为是
                                    doAjaxPost('signAction.action?act=signAdd', {strJson: JSON.stringify(vo)}, function (data) {
                                        $("#roleadd").attr('disabled', false);
                                        $("#roleadd").css("background", "#1E90FF");
                                        if (data.code == "800") {
                                            layer.open({
                                                type: 1,
                                                title: false,// 不显示标题栏
                                                closeBtn: false,
                                                offset: '100px',
                                                area: '300px;',
                                                shade: 0.8,
                                                id: 'LAY_layuipro',// 设定一个id，防止重复弹出
                                                resize: false,
                                                btn: ['确定', '协议打印'],// '发票打印'
                                                btnAlign: 'c',
                                                moveType: 1,// 拖拽模式，0或者1
                                                content: '<div style="padding: 50px; line-height: 22px; background-color: #f2f2f2; color: #000; font-weight: 300;">恭喜你，签约成功！！</div>',
                                                yes: function (index, layero) {
                                                    window.location.href = "sign_look.jsp?id=" + data.vo.id + "&type=" + true + "&typeadd=" + true + "&addtpye=" + addtpye + "&protocoltpye=800";
                                                },
                                                btn2: function (index, layero) {
                                                    window.location.href = "sign_protocol.jsp?teamId=" + data.vo.signTeamId + "&drId=" + data.vo.signDrId + "&PatientId=" + data.vo.signPatientId + "&addtpye=2&protocoltpye=800";//查看协议界面返回读卡界面时addtpye设置为2
                                                }
                                            });
                                        } else {
                                            layer.msg(data.msg);
                                        }
                                    }, function (data) {
                                        layer.msg("签约保存失败！");
                                    });
                                }
                            } else {
                                vo["lableState"] = "0"; // 传入当前是否拥有疾病标签权限 0为否，1为是
                                doAjaxPost('signAction.action?act=signAdd', {strJson: JSON.stringify(vo)}, function (data) {
                                    $("#roleadd").attr('disabled', false);
                                    $("#roleadd").css("background", "#1E90FF");

                                    if (data.code == "800") {
                                        layer.open({
                                            type: 1,
                                            title: false,// 不显示标题栏
                                            closeBtn: false,
                                            offset: '100px',
                                            area: '300px;',
                                            shade: 0.8,
                                            id: 'LAY_layuipro',// 设定一个id，防止重复弹出
                                            resize: false,
                                            btn: ['确定', '协议打印'],// '发票打印'
                                            btnAlign: 'c',
                                            moveType: 1,// 拖拽模式，0或者1
                                            content: '<div style="padding: 50px; line-height: 22px; background-color: #f2f2f2; color: #000; font-weight: 300;">恭喜你，签约成功！！</div>',
                                            yes: function (index, layero) {
                                                window.location.href = "sign_look.jsp?id=" + data.vo.id + "&type=" + true + "&typeadd=" + true + "&addtpye=" + addtpye + "&protocoltpye=800";
                                            },
                                            btn2: function (index, layero) {
                                                window.location.href = "sign_protocol.jsp?teamId=" + data.vo.signTeamId + "&drId=" + data.vo.signDrId + "&PatientId=" + data.vo.signPatientId + "&addtpye=2&protocoltpye=800";//查看协议界面返回读卡界面时addtpye设置为2
                                            }
                                        });
                                    } else {
                                        layer.msg(data.msg);
                                    }
                                }, function (data) {
                                    layer.msg("签约保存失败！");
                                });
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * 费用事件
 */
function signPay() {
    // 漳州财政补助 = 100 - 医保预支付 - 基本公共卫生服务费 - 自费
    if (HospAreaCode.substr(0, 4) == "3506") {
        if ($("#signzfpay").val() == "" || $("#signzfpay").val() == null) {
            layer.msg("请输入自费金额！");
            return;
        }
        if ($("#signzfpay").val() <= 20) {
            var signzfpay = $("#signzfpay").val();
            var ee = $("#bt").find("input:eq(2)").val(20 - signzfpay);
        }
        if ($("#signzfpay").val() > 20) {
            layer.msg("非全自费状态下金额不可超过20！");
            $("#signzfpay").val(0);
            return;
        }
    }

    if ($("#payStatePrivate").is(':checked') == false) {
        if ($("#signzfpay").val() == "" || $("#signzfpay").val() == null) {
            layer.msg("请输入自费金额！");
            return;
        }
        if ($("#signzfpay").val() <= 20) {
            var signzfpay = $("#signzfpay").val();
            $("#signczpay").val(20 - signzfpay);
        }
        if ($("#signzfpay").val() > 20) {
            if (HospAreaCode.substr(0, 4) == "3504") {// 三明
                return;
            }
            layer.msg("非全自费状态下金额不可超过20！");
            $("#signczpay").val(0);
            return;
        }
    } else {
        if ($("#signzfpay").val() == "" || $("#signzfpay").val() == null) {
            layer.msg("请输入自费金额！");
            return;
        }
    }
}

/**
 * 初始化服务项
 */
function findPk() {
    vo["hospId"] = orgid;
    doAjaxPost('signAction.action?act=findPk', {qvoJson: JSON.stringify(vo)}, function (data) {
        if (data != null) {
            var pk = "";
            $.each(data, function (k, v) {
                if (pk == "") {
                    pk = "<input onclick='Pkradio($(this))' code='"+ v.sersmCode+"' pofield='signpackageid' type='checkbox' name='signpackageid' data-index='" + k + "' style='width:25px;height:25px;' id='" + v.id + "' value=" + v.id + ">" + "<label>" + v.sersmName + "</label>";
                } else {
                    pk += "<input onclick='Pkradio($(this))' code='"+ v.sersmCode+"' pofield='signpackageid' type='checkbox' name='signpackageid' data-index='" + k + "' style='width:25px;height:25px;' id='" + v.id + "'  value=" + v.id + ">" + "<label>" + v.sersmName + "</label>";
                }
            });
            if (pk != "") {
                $("#signpackageid").append(pk);
                npPKspecial(HospAreaCode, $("#signpackageid").children(":first"));
                npPKspecial(HospAreaCode, $("#signpackageid").children(":eq(2)"));// 莆田
            } else {
                layer.msg("请先维护服务项，再进行签约！");
            }
        }
    }, function (data) {
        layer.msg("初始化失败-004，请联系管理员！");
    });
}

/**
 * 查询初始签约配置
 */
function signcode() {
    doAjaxPost('signAction.action?act=signcode', {hospid: orgid}, function (data) {
        if (data.vo != null) {
            var code = data.vo.codeTitle.split(";");
            $("#amountPrivateybz").val(code[0]);
            $("#amountPrivate").val(code[1]);
            //读卡签约的时候、如果不是建档立卡人群就默认一般人口打钩、add by WangCheng
            if (isGeneralPeople != undefined && isGeneralPeople == "1") {
                $("#ybrk").prop("checked", "true");
            }
            if (economicType != undefined && economicType == "2") {
                $("#jdlk").prop("checked", "true");
                $("#jdlk").attr("disabled", "disabled");
                $("#ybrk").attr("disabled", "disabled");
            }
            if (data.vo.codeValue == "3503") {// 莆田
                $("#signsJjType").html("");
                var span = "<span onclick='onJjType(1)'> <input id='ybrk' type='checkbox'validator='{\"check\":\"人口经济性质不能为空!\"}' name='sJjType' value='1' title='一般人口' style='width:20px;height:20px;' pofield='sJjType'></span>&nbsp;&nbsp;一般人口&nbsp;&nbsp; " +
                    "<span onclick='onJjType(2)'> <input id='sJjType' type='checkbox' validator='{\"check\":\"人口经济性质不能为空!\"}' name='sJjType'  value='2' title='建档立卡贫困人口' style='width:20px;height:20px;' pofield='sJjType'> </span>&nbsp;&nbsp;建档立卡贫困人口&nbsp;&nbsp; " +
                    "<span onclick='onJjType(3)'> <input id='dbh' type='checkbox' validator='{\"check\":\"人口经济性质不能为空!\"}' name='sJjType' value='3' title='低保户'style='width:20px;height:20px;' pofield='sJjType'></span>&nbsp;&nbsp;低保户&nbsp;&nbsp; " +
                    "<span onclick='onJjType(4)'> <input id='tkh' type='checkbox' validator='{\"check\":\"人口经济性质不能为空!\"}' name='sJjType' value='4' title='特困户（五保户）' style='width:20px;height:20px;' pofield='sJjType'></span>&nbsp;&nbsp;特困户（五保户）&nbsp;&nbsp; " +
                    "<span onclick='onJjType(5)'> <input id='jsjt' type='checkbox' validator='{\"check\":\"人口经济性质不能为空!\"}' name='sJjType' value='5' title='计生特殊家庭'style='width:20px;height:20px;'  pofield='sJjType'></span>&nbsp;&nbsp;计生特殊家庭&nbsp;&nbsp;";
                var czbz = "<input type='hidden' id='signczpay' pofield='signczpay' name='signczpay' value='0' />";
                $("#signsJjType").append(span);
                $("#sccc").append(czbz);
                $("#patientCard").removeAttr('validator');
                $("#redsbk").removeAttr();
                $("#signzfpay").removeAttr('onblur');
                $("#jiafan").text("乙方");
                $("#yifan").text("甲方");
                if (economicType != undefined && economicType == "2") {
                    $("#sJjType").prop("checked", "true");
                    $("#sJjType").attr("disabled", "disabled");
                    $("#ybrk").attr("disabled", "disabled");
                }
                if (isGeneralPeople != undefined && isGeneralPeople == "1") {
                    $("#ybrk").prop("checked", "true");
                }
            }
        }
    });
}

/**
 * 年龄默认选中
 */
function patientAgePk() {
    var Age = $("#patientAge").val();
    if (Age != null && Age != "") {
        if (Age >= 65) {
            $('input:checkbox').eq(1).attr("checked", 'true');
        } else if (Age <= 6) {
            $('input:checkbox').eq(4).attr("checked", 'true');
        }
    }
}

/**
 * 莆田规则
 */
function onJjType(e) {
    if (e == 2) {
        var flag = $("#sJjType").prop("checked");
        if (flag) {
            $($("#signpackageid").children(":eq(2)")).prop("checked", true);
            Pkradio($($("#signpackageid").children(":eq(2)")));
            $("#signczpay").val("20");
        } else {
            $($("#signpackageid").children(":eq(2)")).prop("checked", false);
            Pkradio($($("#signpackageid").children(":eq(2)")));
            $("#signczpay").val("0");
        }
    }

    if ($("input[id='ybrk']").is(':checked')) {
        if ($("input[id='sJjType']").is(':checked')) {
            layer.msg("一般人口与其他类型不能重复选择！！");
            $("#sJjType").prop("checked", false);
            return false;

        } else if ($("input[id='dbh']").is(':checked')) {
            layer.msg("一般人口与其他类型不能重复选择！！");
            $("#dbh").prop("checked", false);
            return false;
        } else if ($("input[id='tkh']").is(':checked')) {
            layer.msg("一般人口与其他类型不能重复选择！！");
            $("#tkh").prop("checked", false);
            return false;

        } else if ($("input[id='jsjt']").is(':checked')) {
            layer.msg("一般人口与其他类型不能重复选择！！");
            $("#jsjt").prop("checked", false);
            return false;
        }
    }
}

var i = 1;

function Pkradio(e) {
    $("#signzfpay").attr("disabled", "disabled");
    if (HospAreaCode.substr(0, 4) == "3501") {// 福州
        if ($("input[id='0e5ade54-f9d1-40f8-a4d6-84b8e5166bb6']").is(':checked') && $("input[id='ddd2c47b-5b92-4a14-b4f9-bf9b97b2d34e']").is(':checked')) {
            layer.msg("基础套餐服务包与财政补助不能重复选择！");
            $("#ddd2c47b-5b92-4a14-b4f9-bf9b97b2d34e").prop("checked", false);
            return false;
        }
    } else if (HospAreaCode.substr(0, 4) == "3505") {// 泉州
        if ($("input[id='f73047ec-f3e8-4dfa-9e97-8f29e5aa4787']").is(':checked') && $("input[id='13697901-584c-43cb-9b70-17815398aab7']").is(':checked')) {
            layer.msg("自费基础套餐服务包与免费基础套餐服务包不能重复选择！");
            $("#13697901-584c-43cb-9b70-17815398aab7").prop("checked", false);
            return false;
        }
    }

    var sid = "";
    var signpackageid = document.getElementsByName("signpackageid");
    var ii = 0;
    if (signpackageid != null && signpackageid.length > 0) {
        for (i = 0; i < signpackageid.length; i++) {
            if (signpackageid[i].checked) {
                if($(signpackageid[i]).attr("code")!="" && $(signpackageid[i]).attr("code")!="null"){
                    ii++;
                }
                if(ii >1 && $(e).attr("code")!="" && $(e).attr("code")!="null" ){
                    layer.msg("有编号不能多选！");
                    $("#"+signpackageid[i].value).prop("checked", false);
                    return false;
                }

                sid = sid + signpackageid[i].value + ";";
                if (signpackageid[i].value == "ddd2c47b-5b92-4a14-b4f9-bf9b97b2d34e") {
                    $("#signczpay").val("20");
                } else {
                    $("#signczpay").val("0");
                }
            }
        }
    }

    if (sid == "") {
        $("#signzfpay").val("0");
        $("#bt").html("");
    }

    var chk_value = [];
    if (e.context.checked) {
        var radValue = e.context.value;
        vo["hospId"] = orgid;
        doAjaxPost('signAction.action?act=findPkone', {pkid: radValue, sid: sid}, function (data) {
            if (data.vo != null) {
                $("#signzfpay").val(data.map.zje);
                if (data.map.bt != null) {
                    $("#bt").html("");
                    $.each(data.map.bt, function (k, v) {
                        if (HospAreaCode.substr(0, 4) == "3505") {
                            if (v.id != "75d480d4-456d-4a13-ba5b-bcd4477541b0" && v.id != "56ffa72b-37fd-41bd-9667-cbabca6c60ab") {
                                $("#bt").append("<label class=\"layui-form-label\" style=\"width: 150px;\">" + v.govTitle + "</label>\n" +
                                    "            <div class=\"layui-input-inline\" style=\"width: 120px;\" id=\"ybz\">\n" +
                                    "                <input type=\"text\" disabled=\"disabled\" id=\"amountPrivateybz\" name=\"amountPrivateybz\" lay-verify=\"email\" autocomplete=\"off\" class=\"layui-input\" value=\"" + v.govMoney + "\">\n" +
                                    "            </div>")
                            }
                        } else {
                            $("#bt").append("<label class=\"layui-form-label\" style=\"width: 150px;\">" + v.govTitle + "</label>\n" +
                                "            <div class=\"layui-input-inline\" style=\"width: 120px;\" id=\"ybz\">\n" +
                                "                <input type=\"text\" disabled=\"disabled\" id=\"amountPrivateybz\" name=\"amountPrivateybz\" lay-verify=\"email\" autocomplete=\"off\" class=\"layui-input\" value=\"" + v.govMoney + "\">\n" +
                                "            </div>")
                        }
                    });
                }
                var v = i + "、";
                if (HospAreaCode.substr(0, 4) == "3507") {// 南平
                    $("#signtext").val("");
                } else {
                    var text = $("#signtext").val();
                    if (data.map.nr != "") {
                        var serpkRemark = data.vo.sersmName + "\n" + data.map.nr;
                        $("#signtext").val(text + serpkRemark + "\n");
                    }

                    // if (necs(text)) {
                    //     $("#signtext").val(text + v + serpkRemark + "\n");
                    // } else {
                    //     $("#signtext").val(v + serpkRemark + "\n");
                    // }
                    i = i + 1;
                }
            }
        });
    } else {
        $("#signtext").val("");
        i = 1;
        // e.context.getAttribute("data-index");// 第几个
        $("input[name='signpackageid']:checked").each(function () {
            chk_value.push($(this).attr("value"));
        });
        $.each(chk_value, function (k, v) {
            doAjaxPost('signAction.action?act=findPkone', {pkid: v, sid: sid}, function (data) {
                if (data.vo != null) {
                    $("#signzfpay").val(data.map.zje);
                    if (data.map.bt != null) {
                        $("#bt").html("");
                        $.each(data.map.bt, function (k, v) {
                            if (HospAreaCode.substr(0, 4) == "3505") {
                                if (v.id != "75d480d4-456d-4a13-ba5b-bcd4477541b0" && v.id != "56ffa72b-37fd-41bd-9667-cbabca6c60ab") {
                                    $("#bt").append("<label class=\"layui-form-label\" style=\"width: 150px;\">" + v.govTitle + "</label>\n" +
                                        "            <div class=\"layui-input-inline\" style=\"width: 120px;\" id=\"ybz\">\n" +
                                        "                <input type=\"text\" disabled=\"disabled\" id=\"amountPrivateybz\" name=\"amountPrivateybz\" lay-verify=\"email\" autocomplete=\"off\" class=\"layui-input\" value=\"" + v.govMoney + "\">\n" +
                                        "            </div>")
                                }
                            } else {
                                $("#bt").append("<label class=\"layui-form-label\" style=\"width: 150px;\">" + v.govTitle + "</label>\n" +
                                    "            <div class=\"layui-input-inline\" style=\"width: 120px;\" id=\"ybz\">\n" +
                                    "                <input type=\"text\" disabled=\"disabled\" id=\"amountPrivateybz\" name=\"amountPrivateybz\" lay-verify=\"email\" autocomplete=\"off\" class=\"layui-input\" value=\"" + v.govMoney + "\">\n" +
                                    "            </div>")
                            }
                            /*$("#bt").append("<label class=\"layui-form-label\" style=\"width: 150px;\">" + v.govTitle + "</label>\n" +
                                "            <div class=\"layui-input-inline\" style=\"width: 120px;\" id=\"ybz\">\n" +
                                "                <input type=\"text\" disabled=\"disabled\" id=\"amountPrivateybz\" name=\"amountPrivateybz\" lay-verify=\"email\" autocomplete=\"off\" class=\"layui-input\" value=\"" + v.govMoney + "\">\n" +
                                "            </div>")*/
                        });
                    }
                    var v = i + "、";
                    if (HospAreaCode.substr(0, 4) == "3507") {// 南平
                        $("#signtext").val("");
                    } else {
                        var text = $("#signtext").val();
                        var serpkRemark = data.vo.sersmName + "\n" + data.map.nr;
                        $("#signtext").val(text + serpkRemark + "\n");
                        // if (necs(text)) {
                        //     $("#signtext").val(text + v + serpkRemark + "\n");
                        // } else {
                        //     $("#signtext").val(v + serpkRemark + "\n");
                        // }
                        i = i + 1;
                    }
                }
            });
        });
    }
}

/**
 * 返回目标页面
 */
function Goto() {
    if (addtpye == "1") {// 签约筛选进入
        history.go(-1);// 返回筛选查看页面
        //defualtHref('sign_pt_list.jsp?');// 返回sign_pt_list页面
    } else if (addtpye == "2") {// 手动签约进入
        defualtHref('sign_list.jsp?');// 返回sign_list页面
    } else {// 读卡签约进入
        defualtHref('sign_card.jsp?');
    }
}

/**
 * 手动录入 身份证算性别 年纪 服务包
 */
function idnoonchange(e) {
    //防止信息累加导成错误信息录入、add by WangCheng
    if (HospAreaCode.substr(0, 4) == "3503") {
        $("#ybrk").prop("checked", false);
        $("#sJjType").prop("checked", false);
        $("#dbh").prop("checked", false);
        $("#tkh").prop("checked", false);
        $("#jsjt").prop("checked", false);
        $("input[name = 'signpackageid']").prop("checked", false);
        $("input[name = 'signpackageid']").attr("disabled", false);
    } else {
        $("#ybrk").prop("checked", false);
        $("#jdlk").prop("checked", false);
        $("#dbh").prop("checked", false);
        $("#tkh").prop("checked", false);
        $("#jsjt").prop("checked", false);
        $("input[name = 'signpackageid']").prop("checked", false);
        $("input[name = 'signpackageid']").attr("disabled", false);
    }
    ptidno = e.context.value;
    if (addtpye == "2") {//手动签约的话
        getSecurityCard(ptidno);//获取社保卡
        // 手动签约判断居民是否是建档立卡人员
        findArchivingCardPeopleTwo();
    }
    findjmda();
    var idno = e.context.value;
    if (idno != "") {
        $("#signPersGroup").removeAttr("checked");
        var sex = GetSEX(idno);
        if (sex == "1") {
            $('input:radio').eq(0).attr("checked", 'true');
        } else if (sex == "2") {
            $('input:radio').eq(1).attr("checked", 'true');
        }

        var nowtime = $("#nowtime").val();
        $("#patientAge").val(GetAge(idno, nowtime));
        patientAgePk();
    }
}

/**
 * 根据居民档案中的市查找区县
 */
function jmdachangecounty(entityvo) {
    if ($("#patientCity").val() != null && $("#patientCity").val() != "") {
        vo["areaCode"] = $("#patientCity").val();
        doAjaxPost('manageCounAction.action?act=findSubAreas', {strJson: JSON.stringify(vo)}, function (data) {
            $("#patientArea").html("");
            var option1 = document.createElement('option');
            option1.setAttribute("value", "");
            option1.innerText = "---请选择---";
            document.getElementById("patientArea").appendChild(option1);
            if (data != null) {
                $.each(data.rows, function (k, v) {
                    if (v.state != "0") {
                        var option = document.createElement('option');
                        option.setAttribute("value", v.id);
                        option.innerText = v.areaSname;
                        document.getElementById("patientArea").appendChild(option);
                    }
                });
            }
            if (entityvo != undefined && entityvo != null) {
                if (entityvo.xian != undefined && entityvo.xian != null) {
                    var option = $("#patientArea").find("option");
                    var xianbm = entityvo.xzqydm.substr(0, 6) + '000000';// 县区编码
                    for (var i = 0; i < option.length; i++) {
                        if (option[i].value == xianbm) {
                            option[i].selected = true;
                            break;
                        }
                    }
                    jmdachangestreet(entityvo);
                    //$("#county").append("<input id='patientArea' pofield='patientArea' class='layui-input' value="+ entityvo.xian +">");
                }
            }
        }, function (data) {
            layer.msg("级联初始化异常，请联系管理员！");
        });
    }
}

/**
 * 根据居民档案中的区县查找街道或乡镇
 */
function jmdachangestreet(entityvo) {
    if ($("#patientArea").val() != null && $("#patientArea").val() != "") {
        vo["areaCode"] = $("#patientArea").val();
        doAjaxPost('manageCounAction.action?act=findSubAreas', {strJson: JSON.stringify(vo)}, function (data) {
            $("#patientStreet").html("");
            var option1 = document.createElement('option');
            option1.setAttribute("value", "");
            option1.innerText = "---请选择---";
            document.getElementById("patientStreet").appendChild(option1);
            if (data != null) {
                $.each(data.rows, function (k, v) {
                    if (v.state != "0") {
                        var option = document.createElement('option');
                        option.setAttribute("value", v.id);
                        option.innerText = v.areaSname;
                        document.getElementById("patientStreet").appendChild(option);
                    }
                });
            }
            if (entityvo.xiang != undefined && entityvo.xiang != null) {
                var option = $("#patientStreet").find("option");
                var streetbm = entityvo.xzqydm.substr(0, 9) + '000';// 街道编码
                for (var i = 0; i < option.length; i++) {
                    if (option[i].value == streetbm) {
                        option[i].selected = true;
                        break;
                    }
                }
                changeCommittee(entityvo);
                //$("#street").append("<input id='patientStreet' pofield='patientStreet' class='layui-input' value="+ entityvo.xiang +">");
            }
        }, function (data) {
            layer.msg("级联初始化异常，请联系管理员！");
        });
    }
}

/**
 * 协议最小时间
 */
function startMinDate() {
    var nowtime = $("#nowtime").val();
    var gaga = new Date(nowtime.replace(/-/, "/"));// 通过HTTP头部里的Date获取服务器上的时间
    /*_y = gaga.getFullYear();
    if (gaga.getDate() < Day) {
        _m = gaga.getMonth();
        var getday = getdate(gaga.getMonth(), gaga.getDate(), Day);
        _d = getday;
    } else if (gaga.getDate() == Day) {
        _m = gaga.getMonth();
        _d = "01";
    } else {
        _m = gaga.getMonth() + 1;
        _d = gaga.getDate() - Day;
    }
    var minTime = _y + "-" + _m + "-" + _d;*/

    var d = new Date(gaga);
    d.setDate(d.getDate() - Day);
    var m = d.getMonth() + 1;
    var minTime = d.getFullYear() + '-' + m + '-' + d.getDate();
    return minTime;

    /*var nowtime = $("#nowtime").val();
    var gaga = new Date(nowtime.replace(/-/, "/"));//通过HTTP头部里的Date获取服务器上的时间
    _y = gaga.getFullYear() - 1;
    var minTime = _y + "-01-01";
    return minTime;*/
}

/**
 * 经济类型判断
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
        } else if ($("input[id='jsds']").is(':checked')) {
            layer.msg("一般人口与其他类型不能重复选择！！");
            $(span).find(":checkbox").prop("checked", false);
            return false;
        }else if ($("input[id='jsdzn']").is(':checked')) {
            layer.msg("一般人口与其他类型不能重复选择！！");
            $(span).find(":checkbox").prop("checked", false);
            return false;
        }else if ($("input[id='jssn']").is(':checked')) {
            layer.msg("一般人口与其他类型不能重复选择！！");
            $(span).find(":checkbox").prop("checked", false);
            return false;
        }else if ($("input[id='pkh']").is(':checked')) {
            layer.msg("一般人口与其他类型不能重复选择！！");
            $(span).find(":checkbox").prop("checked", false);
            return false;
        }else if ($("input[id='qtJj']").is(':checked')) {
            layer.msg("一般人口与其他类型不能重复选择！！");
            $(span).find(":checkbox").prop("checked", false);
            return false;
        }

    }
    if ($("input[id='qtJj']").is(':checked')) {
        if ($("input[id='jdlk']").is(':checked')) {
            layer.msg("其他与其他类型不能重复选择！！");
            $(span).find(":checkbox").prop("checked", false);
            return false;
        } else if ($("input[id='dbh']").is(':checked')) {
            layer.msg("其他与其他类型不能重复选择！！");
            $(span).find(":checkbox").prop("checked", false);
            return false;
        } else if ($("input[id='tkh']").is(':checked')) {
            layer.msg("其他与其他类型不能重复选择！！");
            $(span).find(":checkbox").prop("checked", false);
            return false;
        } else if ($("input[id='jsds']").is(':checked')) {
            layer.msg("其他与其他类型不能重复选择！！");
            $(span).find(":checkbox").prop("checked", false);
            return false;
        }else if ($("input[id='jsdzn']").is(':checked')) {
            layer.msg("其他与其他类型不能重复选择！！");
            $(span).find(":checkbox").prop("checked", false);
            return false;
        }else if ($("input[id='jssn']").is(':checked')) {
            layer.msg("其他与其他类型不能重复选择！！");
            $(span).find(":checkbox").prop("checked", false);
            return false;
        }else if ($("input[id='pkh']").is(':checked')) {
            layer.msg("其他与其他类型不能重复选择！！");
            $(span).find(":checkbox").prop("checked", false);
            return false;
        }else if ($("input[id='ybrk']").is(':checked')) {
            layer.msg("其他与其他类型不能重复选择！！");
            $(span).find(":checkbox").prop("checked", false);
            return false;
        }
    }
    if ($("input[id='jsds']").is(':checked')) {
        if ($("input[id='jsdzn']").is(':checked')) {
            layer.msg("计生独伤残家庭与计生独子女户不能重复选择！！");
            $(span).find(":checkbox").prop("checked", false);
            return false;
        } else if ($("input[id='jssn']").is(':checked')) {
            layer.msg("计生独伤残家庭与计生双女户不能重复选择！！");
            $(span).find(":checkbox").prop("checked", false);
            return false;
        }
    }
    if ($("input[id='jsdzn']").is(':checked')) {
        if ($("input[id='jsds']").is(':checked')) {
            layer.msg("计生独子女户与计生独伤残家庭不能重复选择！！");
            $(span).find(":checkbox").prop("checked", false);
            return false;
        } else if ($("input[id='jssn']").is(':checked')) {
            layer.msg("计生独子女户与计生双女户不能重复选择！！");
            $(span).find(":checkbox").prop("checked", false);
            return false;
        }
    }
    if ($("input[id='jssn']").is(':checked')) {
        if ($("input[id='jsds']").is(':checked')) {
            layer.msg("计生双女户与计生独伤残家庭不能重复选择！！");
            $(span).find(":checkbox").prop("checked", false);
            return false;
        } else if ($("input[id='jsdzn']").is(':checked')) {
            layer.msg("计生双女户与计生独子女户不能重复选择！！");
            $(span).find(":checkbox").prop("checked", false);
            return false;
        }
    }
    return true;
}

/**
 * 查看家庭成员
 */
function familymemger() {
    var patientidno = $("#patientIdno").val();
    var patientjmda = $("#patientjmda").val();
    if (isNotBlank($("#patientjmda").val())) {
        myOpen("查看家庭成员", "sign_familymember.jsp?patientidno=" + patientidno + "&patientjmda=" + patientjmda);
    } else {
        layer.msg("查看家庭成员,居民档案号不能为空！");
    }
}

/**
 *
 */
function selectLabel(c_id, c_teamId, c_drId, c_PatientId) {
    layer.open({
        type: 1,
        title: false,// 不显示标题栏
        closeBtn: false,
        offset: '100px',
        area: '300px;',
        shade: 0.8,
        id: 'LAY_layuipro',// 设定一个id，防止重复弹出
        resize: false,
        btn: ['确定', '协议打印'],// '发票打印'
        btnAlign: 'c',
        moveType: 1,// 拖拽模式，0或者1
        content: '<div style="padding: 50px; line-height: 22px; background-color: #f2f2f2; color: #000; font-weight: 300;">恭喜你，签约成功！！</div>',
        yes: function (index, layero) {
            window.location.href = "sign_look.jsp?id=" + c_id + "&type=" + true + "&typeadd=" + true + "&addtpye=" + addtpye + "&protocoltpye=800";
        },
        btn2: function (index, layero) {
            window.location.href = "sign_protocol.jsp?teamId=" + c_teamId + "&drId=" + c_drId + "&PatientId=" + c_PatientId + "&addtpye=2&protocoltpye=800";//查看协议界面返回读卡界面时addtpye设置为2
        }
    });
}


function doAdd(vo) {
    vo["lableState"] = "1"; // 传入当前是否拥有疾病标签权限 0为否，1为是
    doAjaxPost('signAction.action?act=signAdd', {strJson: JSON.stringify(vo)}, function (data) {
        $("#roleadd").attr('disabled', false);
        $("#roleadd").css("background", "#1E90FF");
        if (data.code == "800") {
            layer.open({
                type: 1,
                title: false,
                closeBtn: false,
                offset: '100px',
                area: '300px;',
                shade: 0.8,
                id: 'LAY_layuipro',
                resize: false,
                btn: ['确定', '协议打印'],
                btnAlign: 'c',
                moveType: 1,
                content: '<div style="padding: 50px; line-height: 22px; background-color: #f2f2f2; color: #000; font-weight: 300;">恭喜你，签约成功！！</div>',
                yes: function (index, layero) {
                    window.location.href = "sign_look.jsp?id=" + data.vo.id + "&type=" + true + "&typeadd=" + true + "&addtpye=" + addtpye + "&protocoltpye=800";
                },
                btn2: function (index, layero) {
                    window.location.href = "sign_protocol.jsp?teamId=" + data.vo.signTeamId + "&drId=" + data.vo.signDrId + "&PatientId=" + data.vo.signPatientId + "&addtpye=2&protocoltpye=800";//查看协议界面返回读卡界面时addtpye设置为2
                }
            });
        } else {
            layer.msg(data.msg);
        }
    }, function (data) {
        layer.msg("签约保存失败！");
    });
}


function doAlert(t) {
    if (t == 1) {
        layer.open({
            type: 1,
            title: false,
            closeBtn: false,
            area: '500px;',// 弹框宽度
            offset: [150, ($(window).width() / 2 - 250)],// 弹框位置
            shade: 0.3,
            id: 'layer_open_four',
            btn: ['确定'],
            btnAlign: 'c',
            moveType: 1,
            content: '<div style="padding: 50px; line-height: 22px; background-color: #393D49;' +
            'color: #fff; font-weight: 100; text-align : center;">请先在公卫系统为该孕产妇建立《母子健康手册》</div>',
            btn1: function () {
                layer.closeAll();
            }
        });
    } else if (t == 2) {
        layer.open({
            type: 1,
            title: false,
            closeBtn: false,
            area: '500px;',// 弹框宽度
            offset: [150, ($(window).width() / 2 - 250)],// 弹框位置
            shade: 0.3,
            id: 'layer_open_four',
            btn: ['确定'],
            btnAlign: 'c',
            moveType: 1,
            content: '<div style="padding: 50px; line-height: 22px; background-color: #393D49;' +
            'color: #fff; font-weight: 100; text-align : center;">请先在公卫系统将该孕产妇的《母子健康手册》归档</div>',
            btn1: function () {
                layer.closeAll();
            }
        });
    } else {
        layer.open({
            type: 1,
            title: false,
            closeBtn: false,
            area: '500px;',// 弹框宽度
            offset: [150, ($(window).width() / 2 - 250)],// 弹框位置
            shade: 0.3,
            id: 'layer_open_four',
            btn: ['确定'],
            btnAlign: 'c',
            moveType: 1,
            content: '<div style="padding: 50px; line-height: 22px; background-color: #393D49;' +
            'color: #fff; font-weight: 100; text-align : center;">普通人与其他类型不能重复选择！！</div>',
            btn1: function () {
                layer.closeAll();
            }
        });
    }
}

/**
 * 查询是否是建档立卡的人群
 */
function findArchivingCardPeopleTwo() {
    //清空经济类型选中状态
    var sJjTypes = document.getElementsByName("sJjType");
    for (var i = 0; i < sJjTypes.length; i++) {
        if (sJjTypes[i].checked) {
            sJjTypes[i].checked = false;
        }
    }
    vo["ptidno"] = ptidno;
    doAjaxPost('signAction.action?act=findArchivingCardPeople', {strJson: JSON.stringify(vo)}, function (data) {
        if (data.map != null) {
            if (data.map.result == "1") {// 是建档立卡，默认勾选建档立卡经济类型且不可取消该选项
                $("#jdlk").prop("checked", "true");
                $("#jdlk").attr("disabled","disabled");
            } else {// 如果不是建档立卡则默认勾选一般人口，可修改
                $("#ybrk").attr("checked", "true");
            }
        }
    });
}
