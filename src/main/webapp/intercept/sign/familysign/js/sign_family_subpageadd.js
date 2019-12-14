/**
 * Created by lenovo on 2017/12/29.
 */
var vo = {};// 家庭成员信息
var openType = getQueryString("openType");
var vojson = getQuerytoString("vojson");// 家庭成员信息
var index = parent.layer.getFrameIndex(window.name);
var ptidno = "";// 居民身份证号
var ptdfid = "";// 居民档案号

/**
 * 页面加载完成后执行
 */
$(function () {
    <!-- 初始化配置参数 -->
    Initialization();
    <!-- 初始化服务项 -->
    findPk();
    <!-- 初始化家庭关系 -->
    familyrelations();
    <!-- 初始化功能按钮 -->
    initFunc();
});

/**
 * 初始化功能按钮
 */
function initFunc() {
    if (openType == "add") {// 新增
        // 暂无操作
    } else if (openType == "look") {// 查看
        if (vojson != "") {
            vo = JSON.parse(vojson.replace(/\'/g, "\""));
            for (var i = 0; i < vo.signpackageid.length; i++) {
                $("input[name='signpackageid']").each(function () {
                    if ($(this).attr("value") == vo.signpackageid[i]) {
                        $(this).attr("checked", 'true');// 先选中
                        $(this).trigger("onclick");// 然后触发单击
                    }
                });
            }
            dataToUi2(vo, "form_vo");
            $("#roleadd").hide();
        }
    } else if (openType == "modify") {// 修改
        if (vojson != "") {
            vo = JSON.parse(vojson.replace(/\'/g, "\""));
            for (var i = 0; i < vo.signpackageid.length; i++) {
                $("input[name='signpackageid']").each(function () {
                    if ($(this).attr("value") == vo.signpackageid[i]) {
                        $(this).attr("checked", 'true');// 先选中
                        $(this).trigger("onclick");// 然后触发单击
                    }
                });
            }
            dataToUi2(vo, "form_vo");
            $("#roleadd").text("修改保存");
        }
    }
}

/**
 * 新增或修改家庭成员
 */
function memberadd() {
    if (validator("form_vo")) {
        if (clickP()) {
            if (openType == "modify") {// 修改
                vo = uiToData2("form_vo", vo);
                parent.fvolist.splice(vo.idx, 1, vo);// 参数1-指定的数组下标；参数2-指定的操作的条数；参数3-指定的替换的参数
                parent.layer.close(index);
            } else {// 新增
                vo = uiToData2("form_vo", vo);
                parent.fvolist.push(vo);
                parent.layer.close(index);
            }
        }
    }
}

/**
 * 手动录入 身份证算性别 年纪 服务包
 */
function idnoonchange(e) {
    ptidno = e.context.value;// 身份证号
    if (ptidno != "") {
        // 设置性别
        var sex = GetSEX(ptidno);
        if (sex == "1") {
            $('input:radio').eq(0).attr("checked", 'true');
        } else if (sex == "2") {
            $('input:radio').eq(1).attr("checked", 'true');
        }
        // 设置年龄
        var nowtime = $("#nowtime").val();
        $("#patientAge").val(GetAge(ptidno, nowtime));
        // 基卫是否在线
        if (sqylOnline == "1") {
            findjmda(ptidno);// 查询居民健康档案
            getSecurityCard(ptidno);// 查询社保卡号
        } else {
            patientAgePk();// 通过身份证判断所属服务人群
        }
    }
}

/**
 * 查询居民健康档案
 */
function findjmda(ptidno) {
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

                    // 基本信息自动填充
                    $("#patientName").val(entityvo.jmxm);// 居民姓名
                    $("#patientTel").val(entityvo.lxdh);// 联系电话
                    $("#patientjmda").val(entityvo.jmdah);// 居民档案号
                    $("#patientjtda").val(entityvo.jtdah);// 家庭档案号

                    // 查找并设置服务人群类型
                    findFwType(ptidno, orgid);
                }
            }
        }, function (data) {
            layer.msg("调取居民档案异常，请联系管理员！");
        });
    }
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
                var vo = JSON.parse(data.vo);
                if (vo.comservice == '1') {
                    $("#ordinary").prop("checked", true);
                }
                if (vo.is06child == '1') {
                    $("#young").prop("checked", true);
                }
                if (vo.iscjr == '1') {
                    $("#cjr").prop("checked", true);
                }
                if (vo.isgxy == '1') {
                    $("#gxy").prop("checked", true);
                }
                if (vo.isjhb == '1') {
                    $("#jhb").prop("checked", true);
                }
                if (vo.islnr == '1') {
                    $("#oldman").prop("checked", true);
                }
                if (vo.istnb == '1') {
                    $("#tnb").prop("checked", true);
                }
                if (vo.isycf == '1') {
                    $("#yuncf").prop("checked", true);
                }
                if (vo.iszxjsb == '1') {
                    $("#jsb").prop("checked", true);
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
 * 获取社保卡号
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
    });
}

/**
 * 根据年龄设置服务人群
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
 * 服务人群检查
 */
function clickP(span) {
    if (jdBeforeSign == "1") {// 开启了签约前先建档功能
        if ($(span).find(":checkbox").is(':checked')) {
            $(span).find(":checkbox").prop("checked", false);
        } else {
            $(span).find(":checkbox").prop("checked", true);
        }
        goToUpdateDwellerFile();// 提示前往基卫修改档案
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
            }
        }
    }
    return true;
}

/**
 * 提示前往基卫修改档案
 */
function goToUpdateDwellerFile() {
    layer.open({
        type: 1,
        title: false,
        closeBtn: false,
        area: '500px;',// 弹框宽度
        offset: [150, ($(window).width() / 2 - 250)],// 弹框位置
        shade: 0.3,
        id: 'window_goToUpdateDwellerFile',
        btn: ['前往基卫修改档案', '取消'],
        btnAlign: 'c',
        moveType: 1,
        content: '<div style="padding: 50px; line-height: 22px; background-color: #393D49;' +
        'color: #fff; font-weight: 100;text-align : center;">所选服务人群类型与该居民健康档案不符，是否前往修改居民健康档案？</div>',
        btn1: function () {
            layer.closeAll();
            continueSignAdd();// 提示修改档案后继续签约
            doAjaxPostSync('signAction.action?act=getUpdateDwellerfileUrl&df_id=' + ptdfid, {strJson: JSON.stringify(vo)}, function (data) {
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
 * 提示继续签约，点击确定后重新查询服务人群
 */
function continueSignAdd() {
    layer.open({
        type: 1,
        title: false,
        closeBtn: false,
        area: '500px;',// 弹框宽度
        offset: [150, ($(window).width() / 2 - 250)],// 弹框位置
        shade: 0.3,
        id: 'window_continueSignAdd',
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
 * 初始化服务项
 */
function findPk() {
    vo["hospId"] = orgid;
    doAjaxPostSync('signAction.action?act=findPk', {qvoJson: JSON.stringify(vo)}, function (data) {
        if (data != null) {
            var pk = "";
            $.each(data, function (k, v) {
                if (pk == "") {
                    pk = "<input onclick='Pkradio($(this))' pofield='signpackageid' type='checkbox' name='signpackageid' data-index='" + k + "' style='width:25px;height:25px;' value=" + v.id + ">" + "<label>" + v.sersmName + "</label>";
                } else {
                    pk += "<input onclick='Pkradio($(this))' pofield='signpackageid' type='checkbox' name='signpackageid' data-index='" + k + "' style='width:25px;height:25px;' value=" + v.id + ">" + "<label>" + v.sersmName + "</label>";
                }
            });
            if (pk != "") {
                $("#signpackageid").append(pk);
            } else {
                layer.msg("请先维护服务项，再进行签约！");
            }
        }
    }, function (data) {
        layer.msg("初始化失败-004，请联系管理员！");
    });
}


// 特色服务包 回显内容 充分利用标签对象，有点意思
var i = 1;

function Pkradio(e) {
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
        doAjaxPostSync('signAction.action?act=findPkone', {pkid: radValue, sid: sid}, function (data) {
            if (data.vo != null) {
                $("#signzfpay").val(data.map.zje);
                if (data.map.bt != null) {
                    $("#bt").html("");
                    $.each(data.map.bt, function (k, v) {
                        if (v.govTitle.indexOf("公共卫生") != -1) {
                            $("#bt").append("<label class=\"layui-form-label\" style=\"width: 150px;\">" + v.govTitle + "</label>\n" +
                                "            <div class=\"layui-input-inline\" style=\"width: 120px;\" id=\"ybz\">\n" +
                                "                <input type=\"text\" disabled=\"disabled\" id=\"amountPrivateybz\" name=\"amountPrivateybz\" pofield=\"amountPrivateybz\" lay-verify=\"email\" autocomplete=\"off\" class=\"layui-input\" value=\"" + v.govMoney + "\">\n" +
                                "            </div>");
                        } else if (v.govTitle.indexOf("医保") != -1) {
                            $("#bt").append("<label class=\"layui-form-label\" style=\"width: 150px;\">" + v.govTitle + "</label>\n" +
                                "            <div class=\"layui-input-inline\" style=\"width: 120px;\" id=\"ybz\">\n" +
                                "                <input type=\"text\" disabled=\"disabled\" id=\"amountPrivate\" name=\"amountPrivate\" pofield=\"amountPrivate\" lay-verify=\"email\" autocomplete=\"off\" class=\"layui-input\" value=\"" + v.govMoney + "\">\n" +
                                "            </div>");
                        } else if (v.govTitle.indexOf("财政") != -1) {
                            $("#bt").append("<label class=\"layui-form-label\" style=\"width: 150px;\">" + v.govTitle + "</label>\n" +
                                "            <div class=\"layui-input-inline\" style=\"width: 120px;\" id=\"ybz\">\n" +
                                "                <input type=\"text\" disabled=\"disabled\" id=\"signczpay\" name=\"signczpay\" pofield=\"signczpay\" lay-verify=\"email\" autocomplete=\"off\" class=\"layui-input\" value=\"" + v.govMoney + "\">\n" +
                                "            </div>");
                        }
                        /*$("#bt").append("<label class=\"layui-form-label\" style=\"width: 150px;\">" + v.govTitle + "</label>\n" +
                            "            <div class=\"layui-input-inline\" style=\"width: 120px;\" id=\"ybz\">\n" +
                            "                <input type=\"text\" disabled=\"disabled\" id=\"amountPrivateybz\" name=\"amountPrivateybz\" lay-verify=\"email\" autocomplete=\"off\" class=\"layui-input\" value=\"" + v.govMoney + "\">\n" +
                            "            </div>");*/
                    });
                }
                var v = i + "、";
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
        });
    } else {
        $("#signtext").val("");
        i = 1;
        // e.context.getAttribute("data-index");// 第几个
        $("input[name='signpackageid']:checked").each(function () {
            chk_value.push($(this).attr("value"));
        });
        $.each(chk_value, function (k, v) {
            doAjaxPostSync('signAction.action?act=findPkone', {pkid: v, sid: sid}, function (data) {
                if (data.vo != null) {
                    $("#signzfpay").val(data.map.zje);
                    if (data.map.bt != null) {
                        $("#bt").html("");
                        $.each(data.map.bt, function (k, v) {
                            $("#bt").append("<label class=\"layui-form-label\" style=\"width: 150px;\">" + v.govTitle + "</label>\n" +
                                "            <div class=\"layui-input-inline\" style=\"width: 120px;\" id=\"ybz\">\n" +
                                "                <input type=\"text\" disabled=\"disabled\" id=\"amountPrivateybz\" name=\"amountPrivateybz\" lay-verify=\"email\" autocomplete=\"off\" class=\"layui-input\" value=\"" + v.govMoney + "\">\n" +
                                "            </div>")
                        });
                    }
                    var v = i + "、";
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
            });
        });
    }
}

/**
 * 初始化家庭关系
 */
function familyrelations() {
    doAjaxPostSync('code.action?act=jsonTreelist', {group: "familyRelation"}, function (data) {
        if (data != null && data.length > 0) {
            $("#mfFmNickName").html("");
            var option1 = document.createElement('option');
            option1.setAttribute("value", "");
            option1.innerText = "--请选择家庭关系--";
            document.getElementById("mfFmNickName").appendChild(option1);
            $.each(data, function (k, v) {
                if (k == 0) {
                    var option = document.createElement('option');
                    option.setAttribute("value", "49");
                    option.innerText = "户主";
                    document.getElementById("mfFmNickName").appendChild(option);
                } else {
                    var option = document.createElement('option');
                    option.setAttribute("value", v.codeValue);
                    option.innerText = v.codeTitle;
                    document.getElementById("mfFmNickName").appendChild(option);
                }
            });
            Initialization();
        }
    }, function (data) {
        layer.msg("初始化家庭关系失败，请联系管理员！");
    });
}

/**
 * 返回
 */
function Goto() {
    parent.layer.close(index);
}

