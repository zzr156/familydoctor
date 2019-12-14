/**
 * Created by lenovo on 2017/11/9.
 */
var vo = {};
var refusevo = "";
var patientCity = "";
var patientArea = "";
var patientStreet = "";
var id = getQueryString("id");
var type = getQueryString("type");
var toexamine = getQueryString("toexamine");
var renew = getQueryString("renew");
var economicType = getQueryString("economicType");
var dissolution = getQueryString("dissolution");
var selectText = "";

/**
 * 页面加载完成后执行
 */
$(function () {
    Initialization();// 初始化参数

    $("#signzfpay").attr("disabled", "disabled");

    if (toexamine == "1") {
        $("#toexamine").show();
    }
    if (renew == "1") {
        $("#roleadd").val("续签保存");
        $("#batch_select").show();

    }
    $("#hospId").val(orgid);
    $("#hospName").val(OrgName);
    var code = HospAreaCode.substring(0, 4);
    if (code == "1405") { // 山西高平
        $("#familyybId").html("");
        $("#familydaId").html("");
    }
    changecity();
    // changecounty();
    // changestreet();
    // findsignModify();
    // 莆田初始配置
    signcode();
});

/**
 * 南平、莆田、漳州服务套餐特殊处理
 */
function npPKspecial(orgid, checkbox) {
    var id = $(checkbox).val();
    if (orgid.substr(0, 2) == "np") {// 南平
        if (id == "np_default") {
            $(checkbox).prop("checked", true);
            Pkradio($(checkbox));
        }
    }
    if (orgid.substr(0, 2) == "pt") {// 莆田
        if (id == "pt_20171218") {
            $(checkbox).prop("checked", true);
            Pkradio($(checkbox));
        } else if (id == "pt_20171218001") {
            $(checkbox).prop("disabled", true);
        }
    }
    if (orgid.substr(0, 2) == "zz") {// 漳州
        if (id == "38d4dd31-bf1d-4372-a4a8-5cf5865d296f") {
            $(checkbox).prop("checked", true);
            Pkradio($(checkbox));
        }
    }
}

/**
 * 查询签约信息
 */
function findsignModify() {
    //限制服务项目不可修改
    var pdks = document.getElementsByName("signpackageid");
    for (var i = 0; i < pdks.length; i++) {
       pdks[i].disabled = true;
    }
    if (id != null && id != "") {
        vo["id"] = id;
        doAjaxPost('signAction.action?act=signLook', {strJson: JSON.stringify(vo)}, function (data) {
            if (data.vo != null) {
                if (dateModify == '0' && dissolution != "1") {
                    $("#signFromDate").attr("disabled", "disabled");
                    if (data.vo.patientCard != null && data.vo.patientCard != "") {
                        $("#patientCard").attr("disabled", "disabled");
                    }
                } else {
                    if (data.vo.patientCard != null && data.vo.patientCard != "") {
                        $("#patientCard").attr("disabled", "disabled");
                    } else {
                        $("#patientCard").removeAttr('validator');
                    }
                }
                if (data.vo.patientCity != null && data.vo.patientCity != "") {
                    patientCity = data.vo.patientCity;
                    changecounty();
                    if (data.vo.patientArea != null) {
                        patientArea = data.vo.patientArea;
                    }
                    changestreet();

                    if (data.vo.patientStreet != null) {
                        patientStreet = data.vo.patientStreet;
                    }
                    changeCommittee();
                    signeach();
                } else {
                    signeach();
                }
            }
        }, function (data) {
            layer.msg("查询失败，联系管理员！");
        });
    }
}

/**
 * 根据省查询市
 */
function changecity() {
    var areaCode = HospAreaCode.substring(0, 2);
    vo["areaCode"] = areaCode + "0000000000";
    doAjaxPostSync('manageCounAction.action?act=findSubAreas', {strJson: JSON.stringify(vo)}, function (data) {
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
    if (($("#patientCity").val() != null && $("#patientCity").val() != "") || patientCity != "") {
        if (patientCity != "") {
            vo["areaCode"] = patientCity;
            patientCity = "";
        } else {
            vo["areaCode"] = $("#patientCity").val();
        }
        doAjaxPostSync('manageCounAction.action?act=findSubAreas', {strJson: JSON.stringify(vo)}, function (data) {
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
    if (($("#patientArea").val() != null && $("#patientArea").val() != "") || patientArea != "") {
        if (patientArea != "") {
            vo["areaCode"] = patientArea;
            patientArea = "";
        } else {
            vo["areaCode"] = $("#patientArea").val();
        }
        doAjaxPostSync('manageCounAction.action?act=findSubAreas', {strJson: JSON.stringify(vo)}, function (data) {
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
function changeCommittee() {
    if ($("#patientStreet").val() != null && $("#patientStreet").val() != "" || patientStreet != "") {
        if (patientStreet != "") {
            vo["areaCode"] = patientStreet;
            patientStreet = "";
        } else {
            vo["areaCode"] = $("#patientStreet").val();
        }
        doAjaxPostSync('manageCounAction.action?act=findSubAreas', {strJson: JSON.stringify(vo)}, function (data) {
            $("#patientNeighborhoodCommittee").html("");
            var option1 = document.createElement('option');
            option1.setAttribute("value", "");
            option1.innerText = "---请选择---";
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
        }, function (data) {
            layer.msg("级联初始化异常，请联系管理员！");
        });
    }
}

function dateAdd(startDate) {
    var startDate = new Date(startDate.replace(/\-/g, "\/"));
    var _y = startDate.getFullYear();
    var _m = startDate.getMonth();
    var _d = startDate.getDate();
    var new_gaga = new Date(_y, _m, _d + 1);
    var new_y = new_gaga.getFullYear();
    var new_m = new_gaga.getMonth() + 1;
    new_m = addPreZero(new_m);
    var new_d = new_gaga.getDate();
    var todate = new_y + '-' + new_m + '-' + new_d;
    return todate;
}

function signeach() {
    doAjaxPost('signAction.action?act=signLook', {strJson: JSON.stringify(vo)}, function (data) {
        // 年龄显示转
        debugger
        data.vo.patientAge = GetAge(data.vo.patientIdno, $("#nowtime").val());
        dataToUi2(data.vo, "form_vo");
        selectText = data.vo.persGroup;
       /* if(JSON.stringify(data.vo.sJjType).indexOf("2")>-1){*/
        if( $.inArray( "2", data.vo.sJjType )>-1){
            if (HospAreaCode.substring(0, 4) == "3503") {// 莆田
                $("#sJjType").attr("disabled", "disabled");
            } else {
                $("#jdlk").attr("disabled", "disabled");
            }
            $("#ybrk").attr("disabled", "disabled");
        }
        PkradioInit();
        // 需求说：续签时要改一下他的签约时间
        if (renew == "1") {
            var time = dateAdd(data.vo.signToDate);
            $("#signFromDate").val(time);
            wdate();
            addOptions();
            if(data.vo.batchOperatorId!=null&&data.vo.batchOperatorId!="") {
                if (drid != data.vo.batchOperatorId) {
                    var option = document.createElement('option');
                    option.setAttribute("value", data.vo.batchOperatorId);
                    //option.setAttribute("selected", true);
                    option.innerText = data.vo.batchOperatorName;
                    document.getElementById("batchName").appendChild(option);
                }
            }
        }
        if(data.vo.signpackageid!=null&&data.vo.signpackageid!="") {
            var packId = data.vo.signpackageid.split(";");
            var pack = $("input[name='signpackageid']");
            for (var i = 0; i < packId.length; i++) {
                for (var j = 0; j < pack.length; j++) {
                    if (packId[i] == pack[j].value) {
                        $("input[value=" + pack[j].value + "]").prop("checked", true);
                    }
                }
            }
        }

        // 解约查询的续签协议开始时间从系统时间开始、add by WangCheng
        if (dissolution == "1") {
            initSignFromDate();
            wdate();
        }
        $("#patientId").val(data.vo.patientId);
        // 莆田规则

        if (HospAreaCode.substr(0, 4) == "3503") {
            var flag = $("#sJjType").prop("checked");
            if (flag) {
                $($("#signpackageid").children(":eq(1)")).prop("checked", true);
                Pkradio($($("#signpackageid").children(":eq(1)")));
                $("#signczpay").val("20");
            } else {
                $($("#signpackageid").children(":eq(1)")).prop("checked", false);
                Pkradio($($("#signpackageid").children(":eq(1)")));
                $("#signczpay").val("0");
            }
        }
    });
}

/**
 * 修改签约信息
 */
function signModify() {
    $("#batchOperatorName").val(username);
    if (validator("form_vo")) {
        if (chnangeDate()) {
            if (checkP()) {
                if (checkLx()) {
                    vo = uiToData2("form_vo", vo);
                    vo.patientTel = $.trim(vo.patientTel);// 联系方式去空格
                    debugger
                    if (renew == "1") {
                        vo.batchOperatorId=$("#batchName").val();//添加操作人ID
                        vo.batchOperatorName=$("#batchName option:selected").text();//添加操作人
                        $("#roleadd").attr('disabled', true);// 不可点击
                        $("#roleadd").css("background", "#808080");// 设置成灰色
                        // 添加疾病标签
                        if (HospLabelState == '1') {// 判断是否有疾病标签的权限
                            var t = 1;
                            // 添加标签弹出框
                            if ($("input[id='gxy']").is(':checked') && t == 1) {
                                layer.open({
                                    type: 2,
                                    offset: '100px',
                                    area: ['600px', '450px'],
                                    title: '疾病类型标签',
                                    shadeClose: false,
                                    content: 'sign_flag.jsp?addtpye=modify',
                                    success: function (layero, index) {
                                        var iframe = window['layui-layer-iframe' + index];
                                        iframe.child(vo);//调用弹出层的方法，用以传值
                                    },
                                    cancel: function (index, layero) {
                                        doModify(vo)
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
                                    content: 'sign_flag.jsp?addtpye=modify',
                                    success: function (layero, index) {
                                        var iframe = window['layui-layer-iframe' + index];
                                        iframe.child(vo);
                                    },
                                    cancel: function (index, layero) {
                                        doModify(vo)
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
                                    content: 'sign_flag.jsp?addtpye=modify',
                                    success: function (layero, index) {
                                        var iframe = window['layui-layer-iframe' + index];
                                        iframe.child(vo);
                                    },
                                    cancel: function (index, layero) {
                                        doModify(vo)
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
                                    content: 'sign_flag.jsp?addtpye=modify',
                                    success: function (layero, index) {
                                        var iframe = window['layui-layer-iframe' + index];
                                        iframe.child(vo);
                                    },
                                    cancel: function (index, layero) {
                                        doModify(vo)
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
                                    content: 'sign_flag.jsp?addtpye=modify',
                                    success: function (layero, index) {
                                        var iframe = window['layui-layer-iframe' + index];
                                        iframe.child(vo);
                                    },
                                    cancel: function (index, layero) {
                                        doModify(vo)
                                    }
                                });
                                t = 0;
                            }
                            if (t == 1) {
                                vo["lableState"] = "1"; // 传入当前是否拥有疾病标签权限 0为否，1为是
                                doAjaxPost('signAction.action?act=signRenew', {strJson: JSON.stringify(vo)}, function (data) {
                                    if (data.vo != null && data.code == "800") {
                                        layer.msg("续约成功！");
                                        setTimeout("history.back(-1)", "1500");
                                    } else {
                                        if (data.msg == "true") {
                                            layer.msg("该居民已经续签，请勿重复续签！");
                                        }
                                    }
                                })
                            }
                        } else {
                            vo["lableState"] = "0"; // 传入当前是否拥有疾病标签权限 0为否，1为是
                            doAjaxPost('signAction.action?act=signRenew', {strJson: JSON.stringify(vo)}, function (data) {
                                if (data.vo != null && data.code == "800") {
                                    layer.msg("续约成功！");
                                    setTimeout("history.back(-1)", "1500");
                                } else {
                                    if (data.msg == "true") {
                                        layer.msg("该居民已经续签，请勿重复续签！");
                                    }
                                }
                            })
                        }
                    } else {
                        var signlx = $('#singlx input[name="signlx"]:checked').val();
                        // 泉州添加签约医生限制（必须有身份证号码）
                        // 泉州添加医保签约信息
                        if (HospAreaCode.substring(0, 4) == "3505" && (signlx == "0" || signlx == "1")) {
                            doAjaxPost('registerAction.action?act=getDoctorIdNo', {strJson: JSON.stringify(vo)}, function (data) {
                                if (data.code == "800") {
                                    if (null != data.vo.drIdno && "" != data.vo.drIdno) {
                                        $("#roleadd").attr('disabled', true);
                                        $("#roleadd").css("background", "#808080");
                                        doAjaxPost('signAction.action?act=signModify', {strJson: JSON.stringify(vo)}, function (data) {
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
                                                                layer.msg("修改成功！");
                                                            } else if (data_qz.code == "700") {
                                                                layer.msg("修改成功！");
                                                            }
                                                        });
                                                    }
                                                }
                                            } else {
                                                layer.msg("修改成功！");
                                            }
                                        }, function (data) {
                                            layer.msg("签约保存失败！");
                                        });
                                    } else {
                                        alert("签约医生的身份证信息为空!请到基卫系统补全医生身份证信息！");
                                        return;
                                    }
                                }
                            });
                        } else {
                            doAjaxPost('signAction.action?act=signModify', {strJson: JSON.stringify(vo)}, function (data) {
                                if (data.vo != null && data.code == "800") {
                                    layer.msg("修改成功！");
                                }
                            });
                        }
                    }
                }
            }
        }
    }
}

/**
 * 时间有效性检查
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
    var tryFlag = false;
    var ora = false;

    if (jdBeforeSign == "1") {// 开启了签约前先建档功能
        for (var i = 0; i < selectText.length; i++) {  // 判断是否是原有的服务人群
            if ($(span).find(":checkbox").val() == selectText[i]) {
                tryFlag = true;
            }
            if (selectText[i] != "7" && selectText[i] != "8") {
                ora = true;
            }
        }

        if ($(span).find(":checkbox").is(':checked')) {
            if ($(span).find(":checkbox").val() != "8" && $(span).find(":checkbox").val() != "7" && $(span).find(":checkbox").val() != "3" && !tryFlag) {
                if ($(span).find(":checkbox").val() == "1" && !ora) {
                    if ($("input[id='jhb']").is(':checked') || $("input[id='jsb']").is(':checked')) {
                        doAlert(3);
                        $(span).find(":checkbox").prop("checked", false);
                    } else {
                        $(span).find(":checkbox").prop("checked", true);
                    }
                } else {
                    if ($("input[id='ordinary']").is(':checked') && $(span).find(":checkbox").val() != "1") {
                        doAlert(3); // 提示普通人与其他类型不能重复选择！！
                        $(span).find(":checkbox").prop("checked", false);
                    } else {
                        $(span).find(":checkbox").prop("checked", false);
                        openThreeWindow();// 提示修改档案
                    }
                }
            } else {
                if ($(span).find(":checkbox").val() == "3") {
                    // 调用接口判断是否是孕产妇
                    vo['idNo'] = $("#patientIdno").val();
                    vo['orgId'] = orgid;
                    doAjaxPost('signAction.action?act=findFwType', {strJson: JSON.stringify(vo)}, function (data) {
                        if (data.code == "800") {
                            if (data.vo != "null" && data.vo != "" && data.vo != null) {
                                var vo = JSON.parse(data.vo);
                                if (vo.isycf == '1') {
                                    $("#yuncf").prop("checked", true);
                                } else {
                                    doAlert(1);// 提示孕妇建档
                                    $(span).find(":checkbox").prop("checked", false);
                                }
                            }
                        }
                    });
                } else {
                    if ($("input[id='ordinary']").is(':checked') && $(span).find(":checkbox").val() != "1") {
                        doAlert(3); // 提示普通人与其他类型不能重复选择！！
                        $(span).find(":checkbox").prop("checked", false);
                    } else {
                        $(span).find(":checkbox").prop("checked", true);
                    }
                }
            }
        } else {
            if ($(span).find(":checkbox").val() == "3") {
                // 调用接口判断是否是孕产妇
                vo['idNo'] = $("#patientIdno").val();
                vo['orgId'] = orgid;
                doAjaxPost('signAction.action?act=findFwType', {strJson: JSON.stringify(vo)}, function (data) {
                    if (data.code == "800") {
                        if (data.vo != "null" && data.vo != "" && data.vo != null) {
                            var vo = JSON.parse(data.vo);
                            if (vo.isycf == '0') {
                                $("#yuncf").prop("checked", false);
                            } else {
                                doAlert(2);// 提示孕妇归档
                                $(span).find(":checkbox").prop("checked", true);
                            }
                        }
                    }
                });

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
 * 提示前往基卫修改档案
 */
function openThreeWindow() {
    var dfId = $("#patientjmda").val();
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
            openFourWindow();// 提示修改档案后继续修改签约信息
            doAjaxPostSync('signAction.action?act=getUpdateDwellerfileUrl&df_id=' + dfId, {strJson: JSON.stringify(vo)}, function (data) {
                if (data.map != null && data.map != "") {
                    window.open(data.map.updateDwellerfileUrl);
                } else {
                    layer.msg("接口出错，请联系系统管理员！");
                }
            });
        },
        btn2: function () {
            layer.closeAll();
        }
    });
}

/**
 * 继续修改签约信息，重新查询服务人群
 */
function openFourWindow() {
    var idno = $("#patientIdno").val();
    layer.open({
        type: 1,
        title: false,
        closeBtn: false,
        area: '500px;',// 弹框宽度
        offset: [150, ($(window).width() / 2 - 250)],// 弹框位置
        shade: 0.3,
        id: 'layer_open_four',
        btn: ['继续修改签约信息'],
        btnAlign: 'c',
        moveType: 1,
        content: '<div style="padding: 50px; line-height: 22px; background-color: #393D49;' +
        'color: #fff; font-weight: 100; text-align : center;">档案修改成功就可以继续修改签约信息啦！</div>',
        btn1: function () {
            layer.closeAll();
            findFwType(idno, orgid);
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

function checkLx() {
    var cityCode = HospAreaCode.substring(0, 4);
    if (cityCode == "1405") { // 山西高平
        return true;
    }else{
        var pkglx = $('#singlx input[name="signlx"]:checked ').val();// 签约类型
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
                    pk = "<input onclick='Pkradio($(this))' pofield='signpackageid' type='checkbox' name='signpackageid' data-index='" + k + "' style='width:25px;height:25px;' value=" + v.id + ">" + v.sersmName;
                } else {
                    pk += "<input onclick='Pkradio($(this))' pofield='signpackageid' type='checkbox' name='signpackageid' data-index='" + k + "' style='width:25px;height:25px;' value=" + v.id + ">" + v.sersmName;
                }
            });
            if (pk != "") {
                $("#signpackageid").append(pk);
                npPKspecial(orgid, $("#signpackageid").children(":first"));
                npPKspecial(orgid, $("#signpackageid").children(":eq(1)"));
            } else {
                layer.msg("请先维护服务项，再进行签约！");
            }
        }
        findsignModify();// 查询改为初始服务之后
    }, function (data) {
        layer.msg("初始化失败-004，请联系管理员！");
    });
}

// 特色服务包 回显内容 充分利用标签对象，有点意思
var i = 1;

function Pkradio(e) {
    $("#signzfpay").attr("disabled", "disabled");
    if (HospAreaCode.substr(0, 4) == "3501") {// 福州
        if ($("input[id='0e5ade54-f9d1-40f8-a4d6-84b8e5166bb6']").is(':checked') && $("input[id='ddd2c47b-5b92-4a14-b4f9-bf9b97b2d34e']").is(':checked')) {
            layer.msg("基础套餐服务包与财政补助不能重复选择！！");
            $("#ddd2c47b-5b92-4a14-b4f9-bf9b97b2d34e").prop("checked", false);
            return false;
        }
    } else if (HospAreaCode.substr(0, 4) == "3505") {// 泉州
        if ($("input[id='f73047ec-f3e8-4dfa-9e97-8f29e5aa4787']").is(':checked') && $("input[id='13697901-584c-43cb-9b70-17815398aab7']").is(':checked')) {
            layer.msg("自费基础套餐服务包与免费基础套餐服务包不能重复选择！！");
            $("#13697901-584c-43cb-9b70-17815398aab7").prop("checked", false);
            return false;
        }
    }
    var sid = "";
    var signpackageid = document.getElementsByName("signpackageid");
    if (signpackageid != null && signpackageid.length > 0) {
        for (i = 0; i < signpackageid.length; i++) {
            if (signpackageid[i].checked) {
                sid = sid + signpackageid[i].value + ";";
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
                        /*$("#bt").append("<label class=\"layui-form-label\" style=\"width: 150px;\">" + v.govTitle + "</label>\n" +
                            "            <div class=\"layui-input-inline\" style=\"width: 120px;\" id=\"ybz\">\n" +
                            "                <input type=\"text\" disabled=\"disabled\" id=\"amountPrivateybz\" name=\"amountPrivateybz\" lay-verify=\"email\" autocomplete=\"off\" class=\"layui-input\" value=\"" + v.govMoney + "\">\n" +
                            "            </div>")*/
                        if (HospAreaCode.substr(0, 4) == "3505") {// 泉州
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
                    })
                }
                var v = i + "、";
                var text = $("#signtext").val();
                var serpkRemark = data.vo.sersmName + "\n" + data.map.nr;
                if (HospAreaCode.substr(0, 4) != "3507") {
                    $("#signtext").val(text + serpkRemark + "\n");
                }
                // if (necs(text)) {
                //     $("#signtext").val(text + v + serpkRemark + "\n");
                // } else {
                //     $("#signtext").val(v + serpkRemark + "\n");
                // }
                i = i + 1;
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
                            if (HospAreaCode.substr(0, 4) == "3505") {// 泉州
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
                            /* $("#bt").append("<label class=\"layui-form-label\" style=\"width: 150px;\">" + v.govTitle + "</label>\n" +
                                 "            <div class=\"layui-input-inline\" style=\"width: 120px;\" id=\"ybz\">\n" +
                                 "                <input type=\"text\" disabled=\"disabled\" id=\"amountPrivateybz\" name=\"amountPrivateybz\" lay-verify=\"email\" autocomplete=\"off\" class=\"layui-input\" value=\"" + v.govMoney + "\">\n" +
                                 "            </div>")*/
                        })
                    }
                    var v = i + "、";
                    var text = $("#signtext").val();
                    var serpkRemark = data.vo.sersmName + "\n" + data.map.nr;
                    $("#signtext").val(serpkRemark + "\n");
                    // if (necs(text)) {
                    //     $("#signtext").val(text + v + serpkRemark + "\n");
                    // } else {
                    //     $("#signtext").val(v + serpkRemark + "\n");
                    // }
                    i = i + 1;
                }
            });
        })
    }
}

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

function goback() {
    history.go(-1);
}

/**
 * 莆田规则
 */
function onJjType(e) {
    if (e == 2) {
        var flag = $("#sJjType").prop("checked");
        if (flag) {
            $($("#signpackageid").children(":eq(1)")).prop("checked", true);
            Pkradio($($("#signpackageid").children(":eq(1)")));
            $("#signczpay").val("20");
        } else {
            $($("#signpackageid").children(":eq(1)")).prop("checked", false);
            Pkradio($($("#signpackageid").children(":eq(1)")));
            $("#signczpay").val("0");
        }
    }
}

function signcode() {
    doAjaxPost('signAction.action?act=signcode', {hospid: orgid}, function (data) {
        if (data.vo != null) {
            var code = data.vo.codeTitle.split(";");
            $("#amountPrivateybz").val(code[0]);
            $("#amountPrivate").val(code[1]);
            if (economicType != undefined && economicType == "2") {
                $("#jdlk").prop("checked", "true");
                $("#jdlk").attr("disabled", "disabled");
                $("#ybrk").attr("disabled", "disabled");
            }
            if (data.vo.codeValue == "3503") {// 莆田
                $("#signsJjType").html("");
                var span = "<span onclick='onJjType(1)'> <input type='checkbox' validator='{\"check\":\"人口经济性质不能为空!\"}' name='sJjType' id='ybrk' value='1' title='一般人口' style='width:20px;height:20px;' pofield='sJjType'></span>&nbsp;&nbsp;一般人口&nbsp;&nbsp; " +
                    "<span onclick='onJjType(2)'> <input type='checkbox' validator='{\"check\":\"人口经济性质不能为空!\"}' name='sJjType' id='sJjType' value='2' title='建档立卡贫困人口' style='width:20px;height:20px;' pofield='sJjType'> </span>&nbsp;&nbsp;建档立卡贫困人口&nbsp;&nbsp; " +
                    "<span onclick='onJjType(3)'> <input type='checkbox' validator='{\"check\":\"人口经济性质不能为空!\"}' name='sJjType' value='3' title='低保户'style='width:20px;height:20px;' pofield='sJjType'></span>&nbsp;&nbsp;低保户&nbsp;&nbsp; " +
                    "<span onclick='onJjType(4)'> <input type='checkbox' validator='{\"check\":\"人口经济性质不能为空!\"}' name='sJjType' value='4' title='特困户（五保户）' style='width:20px;height:20px;' pofield='sJjType'></span>&nbsp;&nbsp;特困户（五保户）&nbsp;&nbsp; " +
                    "<span onclick='onJjType(5)'> <input type='checkbox' validator='{\"check\":\"人口经济性质不能为空!\"}' name='sJjType' value='5' title='计生特殊家庭'style='width:20px;height:20px;'  pofield='sJjType'></span>&nbsp;&nbsp;计生特殊家庭&nbsp;&nbsp;";
                var czbz = "<input type='hidden' id='signczpay' pofield='signczpay' name='signczpay' value='0' />";
                $("#signsJjType").append(span);
                $("#sccc").append(czbz);
                $("#patientCard").removeAttr('validator');
                $("#patientCard").removeAttr('disabled');
                $("#redsbk").removeAttr();
                $("#signzfpay").removeAttr('onblur');
                $("#jiafan").text("乙方");
                $("#yifan").text("甲方");
                if (economicType != undefined && economicType == "2") {
                    $("#sJjType").prop("checked", "true");
                    $("#sJjType").attr("disabled", "disabled");
                    $("#ybrk").attr("disabled", "disabled");
                }
            }
        }
        findPk();
    });
}

function PkradioInit() {
    var sid = "";
    var signpackageid = document.getElementsByName("signpackageid");
    if (signpackageid != null && signpackageid.length > 0) {
        for (i = 0; i < signpackageid.length; i++) {
            if (signpackageid[i].checked) {
                sid = sid + signpackageid[i].value + ";";
            }
        }
    }
    if (sid == "") {
        $("#bt").html("");
    }
    doAjaxPost('signAction.action?act=findPkone', {sid: sid}, function (data) {
        if (data.map.bt != null) {
            $("#bt").html("");
            $.each(data.map.bt, function (k, v) {
                if (HospAreaCode.substr(0, 4) == "3505") {// 泉州
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
    });
}

/**
 * 协议最小时间
 */
function startMinDate() {
    var nowtime = $("#nowtime").val();
    var gaga = new Date(nowtime.replace(/-/, "/"));// 通过HTTP头部里的Date获取服务器上的时间
    _y = gaga.getFullYear() - 1;
    var minTime = _y + "-01-01";
    return minTime;
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

function signPay() {
    // 漳州财政补助 = 100 - 医保预支付 - 基本公共卫生服务费 - 自费
    if (HospAreaCode.substr(0, 4) == "3506") {// 漳州
        if ($("#signzfpay").val() == "" || $("#signzfpay").val() == null) {
            layer.msg("请输入自费金额！");
            return;
        }
        if ($("#signzfpay").val() <= 20) {
            var signzfpay = $("#signzfpay").val();
            var ee = $("#bt").find("input:eq(2)").val(20 - signzfpay);
        }
        if ($("#signzfpay").val() > 20) {
            if (HospAreaCode.substr(0, 4) == "3504") {// 三明
                return;
            }
            layer.msg("非全自费状态下金额不可超过20！");
            $("#signzfpay").val(0);
            return;
        }
    }
}


/**
 * 拒签
 */
function refusesign() {
    myOpen("拒签信息", "sign_refuse.jsp?signId=" + $("#signId").val(), refuse);
}

function refuse() {
    if (refusevo == "false") {
        $("#roleadd").hide();
    }
}

/**
 * 前往基卫修改姓名
 */
function changePatientName() {
    var dfId = $("#patientjmda").val();
    layer.open({
        type: 1,
        title: false,
        closeBtn: false,
        area: '500px;',// 弹框宽度
        offset: [150, ($(window).width() / 2 - 250)],// 弹框位置
        shade: 0.3,
        id: 'layer_open_three',
        btn: ['是', '否'],
        btnAlign: 'c',
        moveType: 1,
        content: '<div style="padding: 50px; line-height: 22px; background-color: #393D49;' +
        'color: #fff; font-weight: 100;text-align : center;">请先修改居民健康档案，是否前往修改？</div>',
        btn1: function () {
            layer.closeAll();
            changePatientNameBack();// 提示修改档案后继续修改签约信息
            doAjaxPostSync('signAction.action?act=getUpdateDwellerfileUrl&df_id=' + dfId, {strJson: JSON.stringify(vo)}, function (data) {
                if (data.map != null && data.map != "") {
                    window.open(data.map.updateDwellerfileUrl);
                } else {
                    layer.msg("接口出错，请联系系统管理员！");
                }
            });
        },
        btn2: function () {
            layer.closeAll();
        }
    });
}

/**
 * 查询档案，更新姓名
 */
function changePatientNameBack() {
    layer.open({
        type: 1,
        title: false,
        closeBtn: false,
        area: '500px;',// 弹框宽度
        offset: [150, ($(window).width() / 2 - 250)],// 弹框位置
        shade: 0.3,
        id: 'layer_open_four',
        btn: ['继续修改'],
        btnAlign: 'c',
        moveType: 1,
        content: '<div style="padding: 50px; line-height: 22px; background-color: #393D49;' +
        'color: #fff; font-weight: 100; text-align : center;">档案修改成功就可以继续修改签约信息啦！</div>',
        btn1: function () {
            layer.closeAll();
            // 查询居民健康档案，更新居民姓名
            vo["idNo"] = $("#patientIdno").val();
            vo["orgId"] = orgid;
            doAjaxPost('signAction.action?act=findjmda', {strJson: JSON.stringify(vo)}, function (data) {
                if (data.vo != null) {
                    var datavo = JSON.parse(data.vo);
                    if (datavo.entity != null && datavo.entity != "") {
                        var entityvo = datavo.entity;
                        $("#patientName").val(entityvo.jmxm);
                    }
                } else {
                    layer.msg("未查询到该居民的健康档案信息，如有疑问请联系系统管理员！");
                }
            });
        }
    });
}

function initSignFromDate() {
    var nowDate = new Date();
    var startYear = nowDate.getFullYear();
    var startMonth = nowDate.getMonth() + 1 + "";
    if (startMonth.length == 1) {
        startMonth = "0" + startMonth;
    }
    var startDay = nowDate.getDate() + "";
    if (startDay.length == 1) {
        startDay = "0" + startDay;
    }
    $("#signFromDate").val(startYear + "-" + startMonth + "-" + startDay);
}


function doModify(vo) {
    vo["lableState"] = "1"; // 传入当前是否拥有疾病标签权限 0为否，1为是
    doAjaxPost('signAction.action?act=signRenew', {strJson: JSON.stringify(vo)}, function (data) {
        if (data.vo != null && data.code == "800") {
            layer.msg("续约成功！");
            setTimeout("history.back(-1)", "1500");
        } else {
            if (data.msg == "true") {
                layer.msg("该居民已经续签，请勿重复续签！");
            }
        }
    })
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


function  addOptions() {
    debugger
    $("#batchName").html("");
    var option1 = document.createElement('option');
    option1.setAttribute("value",drid);
    option1.setAttribute("selected", true);
    option1.innerText = username;
    document.getElementById("batchName").appendChild(option1);

}