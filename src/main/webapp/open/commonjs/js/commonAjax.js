/**
 * Created by admin on 2016/3/22.
 */
var familyUrl = "/family-doctor";
var Day = "0"; //  时间全局变量
var dateModify = "0";// 协议时间是否能修改（0-不能修改，1-能修改）
var jdBeforeSign = "0";// 是否开启签约前先建档功能
var openResitAssess = "0";// 是否开启补充考核功能
var sqylOnline = "1";// 基卫项目是否在线（默认为1，即在线）
var openYear = "0";// 是否开启自然年度签约（0-未开启，1-开启）
var assessViewUrl = "http://18.1.3.170:8080/";// 绩效考核图片加载路径前缀

/**
 * 基卫跳转 参数
 */
/*var xuuser = getQueryString("xuuser");// 新农合
var xpaw = getQueryString("xpaw");
var yuuser = getQueryString("yuuser");// 医保
var ypaw = getQueryString("ypaw");
var dkqlx = getQueryString("icklb0");// 使用读卡卡具型号
var dkqdk = getQueryString("ickdk0");// 读卡卡具端口
var dqbm = getQueryString("linkman");// 地区编码
var wdbm = getQueryString("shortname");// 网点*/

/**
 * 异步Ajax提交
 * @param url 地址
 * @param params 参数
 * @param callback 回调函数
 */
function doAjaxPost(url, params, callback) {
    var index = layer.load(1);// 遮罩打开
    $.post(url, params, function (data) {
        layer.close(index);// 遮罩关闭
        callback(data);
    }, "json");
}

/**
 * 同步Ajax Post请求
 * @param url 地址
 * @param params 参数
 * @param callback 回调函数
 */
function doAjaxPostSync(url, params, callback) {
    var index = layer.load(1);// 遮罩打开
    $.ajax({
        type: "POST",
        dataType: "json",
        data: params,
        url: url,
        async: false,// 是否异步请求
        success: function (data) {
            layer.close(index);// 遮罩关闭
            callback(data);
        }
    });
}

/**
 * Ajax Post提交
 * @param url 地址
 * @param params 参数
 * @param callback 回调函数
 */
function doAjaxPostNotLoad(url, params, callback) {
    $.post(url, params, function (data) {
        callback(data);
    }, "json");
}

function Previous() {
    if (parseInt($("#pageStartNo").val()) != 1)
        $("#pageStartNo").val(parseInt($("#pageStartNo").val()) - 1);
}

function Next() {
    if (parseInt($("#pageStartNo").val()) != parseInt($("#pageCount").val()))
        $("#pageStartNo").val(parseInt($("#pageStartNo").val()) + 1);
}

function qvodesc(qvodesc) {
    $("#" + qvodesc).html(
        $("#pageStartNo").val() + "/" + $("#pageCount").val() + "每页显示:"
        + $("#pageSize").val() + "条,共有" + $("#itemCount").val()
        + "条");
}

function qvoGo() {
    $("#pageStartNo").val(parseInt($("#gotext").val()));
}

function qvoPN(qvo) {
    if (qvo.pageStartNo == "1") {
        $("#previous").attr("disabled", true);
    } else {
        $("#previous").attr("disabled", false);
    }
    if (qvo.pageStartNo == qvo.pageCount) {
        $("#next").attr("disabled", true);
    } else {
        $("#next").attr("disabled", false);
    }
}

/**
 * 页面属性转数据
 * @param ui
 * @param vo
 */
function uiToData(ui, vo) {
    vo = {};
    $.each($("#" + ui).find("[pofield]"), function (k, v) {
        if ($(this).attr("type") == 'radio') {
            $("input:radio[name='" + $(this).attr("pofield") + "']:checked").each(function () {
                vo[$(this).attr("pofield")] = $(this).attr("value");
            });
        } else if ($(this).attr("type") == 'checkbox') {
            var chk_value = [];
            $("input[name='" + $(this).attr("pofield") + "']:checked").each(function () {
                chk_value.push($(this).attr("value"));
            });
            if (chk_value.length >= 0) {
                vo[$(this).attr("pofield")] = chk_value;
            }
        } else if ($(this).get(0).tagName == 'TD') {
            vo[$(this).attr("pofield")] = $(this).text();
        } else {
            vo[$(this).attr("pofield")] = $(this).val();
        }
    });
    return vo;
}

function uiToData2(ui, vo) {
    vo = {};
    $.each($("#" + ui).find("[pofield]"), function (k, v) {
        if ($(this).attr("type") == 'radio') {
            $("input:radio[name='" + $(this).attr("pofield") + "']:checked").each(function () {
                vo[$(this).attr("pofield")] = $(this).attr("value");
            });
        } else if ($(this).attr("type") == 'checkbox') {
            var chk_value = [];
            $("input[name='" + $(this).attr("pofield") + "']:checked").each(function () {
                chk_value.push($(this).attr("value"));
            });
            if (chk_value.length >= 0) {
                vo[$(this).attr("pofield")] = chk_value;
            }
        } else if ($(this).get(0).tagName.toLowerCase() == "select") {
            vo[$(this).attr("pofield")] = $(this).find("option:selected").val();
        } else if ($(this).get(0).tagName.toLowerCase() == "td") {
            vo[$(this).attr("pofield")] = $(this).text();
        } else {
            vo[$(this).attr("pofield")] = $(this).val();
        }
    });
    return vo;
}

/**
 * 数据转页面属性
 * @param data
 * @param ui
 */
function dataToUi(data, ui) {
    $.each($("#" + ui).find("[pofield]"), function (k, v) {
        if ($(this).attr("type") == 'radio') {
            if ($(this).attr("value") == data[$(this).attr("pofield")]) {
                $(this).attr("checked", true);
            }
        } else if ($(this).attr("type") == 'checkbox') {
            if (data[$(this).attr("pofield")] != null) {
                $("input[name='" + $(this).attr("pofield") + "']:checkbox").each(function () {
                    if (JSON.stringify(data[$(this).attr("pofield")]).indexOf($(this).attr("value")) != -1) {
                        $(this).attr("checked", true);
                    }
                })
            }
        } else if ($(this).get(0).tagName == 'TD') {
            $(this).text(data[$(this).attr("pofield")])
        } else {
            $(this).val(data[$(this).attr("pofield")]);
        }
    });
}

function dataToUi2(data, ui) {
    $.each($("#" + ui).find("[pofield]"), function (k, v) {
        if (data[$(this).attr("pofield")] == null) {
            data[$(this).attr("pofield")] = "";//将null转化为""
        }
        if ($(this).attr("type") == 'radio') {
            if ($(this).attr("value") == data[$(this).attr("pofield")]) {
                $(this).attr("checked", true);
            }
        } else if ($(this).attr("type") == 'checkbox') {
            if (data[$(this).attr("pofield")] != null) {
                $("input[name='" + $(this).attr("pofield") + "']:checkbox").each(function () {
                    if ($.inArray($(this).attr("value"), data[$(this).attr("pofield")]) != -1) {
                        // if (data[$(this).attr("pofield")].indexOf($(this).attr("value")) != -1) {
                        $(this).attr("checked", true);
                    }
                });
            }
        } else if ($(this).attr("type") == 'text') {
            $(this).val(data[$(this).attr("pofield")]);
        } else if ($(this).attr("type") == 'hidden') {
            $(this).val(data[$(this).attr("pofield")]);
        } else if ($(this).attr("type") == 'email') {
            $(this).val(data[$(this).attr("pofield")]);
        } else if ($(this).attr("type") == 'image') {
            $(this).val(data[$(this).attr("pofield")]);
        } else if ($(this).get(0).tagName.toLowerCase() == "textarea") {
            $(this).val(data[$(this).attr("pofield")]);
        } else if ($(this).get(0).tagName.toLowerCase() == "select") {
            $(this).val(data[$(this).attr("pofield")]);
        } else if ($(this).attr("type") == 'number') {
            $(this).val(data[$(this).attr("pofield")]);
        } else {
            $(this).text(data[$(this).attr("pofield")] || '');
        }
    });
}

/**
 * 公用组织单选框或多选框
 * @param type
 * @param name
 * @param title
 * @param value
 */
function dataUiCodeGroup(type, name, title, value) {
    if (type == 'select') {
        var option = document.createElement('option');
        option.setAttribute("value", value);
        option.innerText = title;
        $("#" + name).append(option); //3、在末尾中添加元素"#" + name).append(option); //3、在末尾中添加元素
        return option;
    } else {
        var input = document.createElement('input');
        input.setAttribute("type", type);
        input.setAttribute("name", name);
        input.setAttribute("pofield", name);
        input.setAttribute("id", name);
        input.setAttribute("value", value);
        $("#" + name).append(input); //3、在末尾中添加元素
        $("#" + name).append(title); //3、在末尾中添加元素
    }
}

/**
 * 默认选项
 * @param name
 * @param value
 */
function defaultToRadio(name, value) {
    $("input:radio[name='" + name + "']").each(function () {
        if ($(this).attr("value") == value) {
            $(this).attr("checked", true);
        }
    });
}

function defualtHref(url) {
    var loginMenuId = $("#loginMenuId").val();
    window.location.href = url + "&loginMenuId=" + loginMenuId
}

function showMsg(msg, callback) {
    layer.open({
        skin: 'layui-layer-molv',
        closeBtn: 0,
        title: false,
        content: msg,
        anim: 5,
        btn: ['关闭'],
        end: callback
    });
}

// 邮箱 正则表达式
var mail = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
// 手机 正则表达式
var phone = /^(\\+\\d+)?1[3456789]\d{9}$/;
// 数字 正则表达式
var number = /^[0-9]*$/;
// 定位后背景色
var bgclr = "#e0f2be";

/**
 * rule：   规则包含 mail，phone，idCard(身份证) ，myphone（0开头的判断位数11或12位，开头的只能11位），number
 * max：  最大值
 * min：   最小值
 * msg：  不为空 自动判断字段不能为空
 * check：多选必选
 * @param ui
 * @returns {boolean}
 */
function validator(ui) {
    var b = true;
    debugger
    $.each($("#" + ui).find("[validator]"), function (k, v) {
        $(this).css("background-color", "");
        if ($(this).attr("validator") != null) {
            var val = JSON.parse($(this).attr("validator").toString());
            if (val != null) {
                // 必填
                if (val.msg != null) {// 必填
                    if ($(this).val() == null || $(this).val().length == 0) {
                        alertMsg(val.msg);
                        $(this).focus();
                        $(this).css("background-color", bgclr);
                        b = false;
                        return false;
                    }
                }
                // 规则
                if (val.rule != null) {
                    if (val.rule == "mail") {
                        if (!mail.test($(this).val())) {// 邮箱
                            alertMsg("邮箱格式不对！");
                            $(this).focus();
                            $(this).css("background-color", bgclr);
                            b = false;
                            return false;
                        }
                    } else if (val.rule == "phone") {
                        if (!phone.test($(this).val())) {// 手机
                            alertMsg("手机格式不对！");
                            $(this).focus();
                            $(this).css("background-color", bgclr);
                            b = false;
                            return false;
                        }
                    } else if (val.rule == "idCard") {// 身份证
                        var flag = isCardID($(this).val());
                        if (flag != true) {
                            alertMsg(flag);
                            $(this).focus();
                            $(this).css("background-color", bgclr);
                            b = false;
                            return false;
                        }
                    } else if (val.rule == "myphone") {// 电话
                        /*if ($.trim($(this).val()) == "") {
                            alertMsg("电话不能为空！");
                            return false;
                        }*/
                        var phoneValue = $.trim($(this).val());
                        var reg = new RegExp("^[0-9]*$");//正则 只能是数字
                        if (phoneValue.substring(0, 1) == "0") {
                            if (HospAreaCode.substring(0, 4) == "3505" || HospAreaCode.substring(0, 4) == "3501") {
                                if (phoneValue.length != 12 || !reg.test(phoneValue)) {
                                    alertMsg("电话格式不对！");
                                    $(this).focus();
                                    $(this).css("background-color", bgclr);
                                    b = false;
                                    return false;
                                }
                            } else {
                                if (phoneValue.length != 11 || !reg.test(phoneValue)) {
                                    alertMsg("电话格式不对！");
                                    $(this).focus();
                                    $(this).css("background-color", bgclr);
                                    b = false;
                                    return false;
                                }
                            }

                        } else if (phoneValue.substring(0, 1) == "1") {
                            if (phoneValue.length != 11 || !phone.test(phoneValue)) {
                                alertMsg("电话格式不对！");
                                $(this).focus();
                                $(this).css("background-color", bgclr);
                                b = false;
                                return false;
                            }
                        } else {
                            alertMsg("联系方式必须以 0 或 1 开头！");
                            $(this).focus();
                            $(this).css("background-color", bgclr);
                            b = false;
                            return false;
                        }
                    } else if (val.rule == "number") {
                        if (!number.test($(this).val())) {//数字
                            alertMsg("数字格式不对！");
                            $(this).focus();
                            $(this).css("background-color", bgclr);
                            b = false;
                            return false;
                        }
                    }
                }
                // 长度
                if (val.max != null) {
                    if ($(this).val().length > parseInt(val.max)) {
                        alertMsg("长度必须小于" + val.max);
                        $(this).focus();
                        $(this).css("background-color", bgclr);
                        b = false;
                        return false;
                    }
                }
                if (val.min != null) {
                    if ($(this).val().length < parseInt(val.min)) {
                        alertMsg("长度必须大于" + val.min);
                        $(this).focus();
                        $(this).css("background-color", bgclr);
                        b = false;
                        return false;
                    }
                }
                // 必需为数字
                if (val.num != null) {
                    if (isNotBlank($(this).val())) {
                        if (isNaN(parseInt($(this).val()))) {
                            alertMsg("必需为数字！");
                            $(this).focus();
                            $(this).css("background-color", bgclr);
                            b = false;
                            return false;
                        }
                    }
                }
                // 多选必选
                if (val.check != null) {
                    var ed = true;
                    var checkArry = document.getElementsByName($(this).context.name);
                    for (var i = 0; i < checkArry.length; i++) {
                        if (checkArry[i].checked == true) {
                            ed = false;
                        }
                    }
                    if (ed) {
                        alertMsg(val.check);
                        $(this).focus();
                        $(this).css("background-color", bgclr);
                        b = false;
                        return false;
                    }
                }
            }
        }
    });
    return b;
}


function publicReadCard() {// FZ0009999 K53568439 FZ53568439
    // 为区分各地市，会在orgid前面加上地市简称
    var orgbm = orgid.substring(3, orgid.length);
    dkqlx = dkqlx == null ? "" : dkqlx; // 卡具类型
    dkqdk = dkqdk == null ? "" : dkqdk; // 卡具端口
    // 地区编码  基卫登陆跳转dqbm ，家签登陆   HospAreaCode
    dqbm = dqbm == null ? HospAreaCode : dqbm;
    // dqbm = "3502";
    // var NewObj = icReadCard("002", "4", "39602", "0208", "350103");
    // return {"ic_icno": "K53654863"};
    // return {"ic_icno": "A739636871"};
    var NewObj = icReadCard(dkqlx, dkqdk, orgbm, wdbm, dqbm);
    // var NewObj = icReadCard("002", dkqdk, orgbm, wdbm, dqbm);
    return NewObj;
}

/**
 * IC卡读卡方法
 * 调用例子：var iccard_no = publicReadCard();
 * if (iccard_no != "") {......操作......}
 *
 * @param icklb0
 * @param ickdk0
 * @param ybjg00
 * @param fwwdbh
 * @param xzqh00
 * @returns {*}
 */
function icReadCard(icklb0, ickdk0, ybjg00, fwwdbh, xzqh00) {
    debugger
    // 读卡错误返回
    var ic_err = {
        "ic_type": "",
        "ic_icno": "",
        "ic_ybid": "",
        "ic_name": "",
        "ic_xb00": "",
        "ic_csrq": "",
        "ic_comp": "",
        "ic_lxdz": "",
        "ic_lxdh": ""
    };
    if (ybjg00 == "null" || ybjg00.replace(/ /g, '') == "") {// 机构代码为空
        alert("\u673a\u6784\u4ee3\u7801\u5c1a\u672a\u521d\u59cb\u5316\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\uff01");// 机构代码尚未初始化，请联系管理员！
        return ic_err;
    } else if (ybjg00 == "XM") {// 机构代码为厦门
        // 厦门健康卡和医保卡调同一函数参数(1)
        // 参数(2)，传入医院的服务网点编号，健康卡临时卡使用
        // 这里为了方便，统一全部传入服务网点编号参数
        var xmk = iccard.GetSSSECardno("3502", fwwdbh);
        var result = "";
        if (xmk.substr(0, 3) == "+OK") {
            result = xmk;
        } else {
            alert(xmk);// 提示读卡出错信息
            return ic_err;
        }
        // 若卡号后面有+TYPE:2 则去掉(厦门社保卡)
        if (result.indexOf("+TYPE:") > 0) {
            result = result.substring(0, result.indexOf("+TYPE:"))
        }
        // 直接返回卡号(去掉卡号的前缀+OK:,去掉卡号的后缀+TYPE:)
        return result.substr(4);
    } else if (icklb0 == "CET") {// 其它厂商卡具(升腾卡具,需要配置SYSUSERINFO表的ICKLB0字段)
        KdbKbdControl1.iComPort = ickdk0;
        KdbKbdControl1.iTimeOut = 15;// 超时时间(秒)
        KdbKbdControl1.iIbmCharacter = 0;
        KdbKbdControl1.m_iPadTimes = 0;
        KdbKbdControl1.ReadCardInfo();// 读取卡信息函数
        if (KdbKbdControl1.iStatus == 22) {
            KdbKbdControl1.iComPort = ickdk0;
            KdbKbdControl1.iTimeOut = 15;// 超时时间(秒)
            KdbKbdControl1.iIbmCharacter = 0;
            KdbKbdControl1.m_iPadTimes = 100;
            KdbKbdControl1.YKT_readopencard4428_YLZ();
            if (KdbKbdControl1.iStatus == 22) {
                KdbKbdControl1.iStatus = 0;
                alert("error:read card error!");
                return ic_err;
            }
            var ic_type = "";// 卡片类型
            var ic_ybid = "";// 医保ID
            var ic_icno = KdbKbdControl1.cszTransOutInfo;// 卡号
            var ic_name = "";// 姓名(社保卡)
            var ic_xb00 = "";// 性别
            var ic_csrq = "";// 出生日期
            var ic_comp = "";// 公司
            var ic_lxdz = "";// 地址
            var ic_lxdh = "";// 联系电话
            var ic_info = {
                "ic_type": ic_type,
                "ic_icno": ic_icno,
                "ic_ybid": ic_ybid,
                "ic_name": ic_name,
                "ic_xb00": ic_xb00,
                "ic_csrq": ic_csrq,
                "ic_comp": ic_comp,
                "ic_lxdz": ic_lxdz,
                "ic_lxdh": ic_lxdh
            };
            return ic_info;
        }

        var ic_type = KdbKbdControl1.iCardType;// 卡类型
        var ic_icno = KdbKbdControl1.cCardNo;// 社保卡号
        var cIssuedeptid = KdbKbdControl1.cIssuedeptid;// 发卡机构编号
        var cIssuedate = KdbKbdControl1.cIssuedate;// 发卡日期
        var ic_ybid = KdbKbdControl1.cId;// 身份证号
        var ic_name = KdbKbdControl1.cName;// 姓名
        var ic_xb00 = KdbKbdControl1.iSex;// 性别
        if (ic_xb00 == "0") {
            ic_xb00 = "2";
        }
        var ic_csrq = KdbKbdControl1.cBirthDate;// 出生日期
        var ic_comp = KdbKbdControl1.cCompany;// 单位名称
        var ic_lxdz = KdbKbdControl1.cAddress;// 通讯地址
        var ic_lxdh = KdbKbdControl1.cTelephone;// 联系电话
        // IC读卡内容返回
        var ic_info = {
            "ic_type": ic_type,
            "ic_icno": ic_icno,
            "ic_ybid": ic_ybid,
            "ic_name": ic_name,
            "ic_xb00": ic_xb00,
            "ic_csrq": ic_csrq,
            "ic_comp": ic_comp,
            "ic_lxdz": ic_lxdz,
            "ic_lxdh": ic_lxdh
        };
        return ic_info;
    } else if (icklb0 == "PS") {// 其它厂商卡片(动力卡片,需要配置SYSUSERINFO表的ICKLB0字段)
        if (xzqh00 == "") {
            // 行政区划代码尚未初始化，请联系管理员！
            alert("\u884c\u653f\u533a\u5212\u4ee3\u7801\u5c1a\u672a\u521d\u59cb\u5316\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\uff01");
            return ic_err;
        } else {
            xzqh00 = xzqh00.substr(0, 4);
        }
        // 1.读医保卡
        var ybk = "";
        icklb0 = icklb0.replace(/ /g, '');
        switch (icklb0) {
            case '002':// 卡具型号 2型
                ybk = iccard.GetSSSECardno(xzqh00, 10);
                break;
            case '003':// 3型
                ybk = iccard.GetSSSECardno(xzqh00, 12);
                break;
            default:  // 默认型号为2型
                ybk = iccard.GetSSSECardno(xzqh00, 10);
        }
        if (ybk.substr(0, 3) == "+OK") {
            result = ybk;
        } else {
            // 2.读就诊卡
            var jzk = iccard.GetSSSECardno("PTJK", 12);
            var result = "";
            if (jzk.substr(0, 3) == "+OK") {
                result = jzk;
            } else {
                jzk = iccard.YKT_GetCardno_ps("3505", 12, 9, 3);
                result = "+OK:" + jzk;
            }
        }
        // 20110212修改IC读卡返回值类型为字符串对象，约定相关值如下：
        var ic_type = "";// 卡片类型
        var ic_psam = "";// 卡具内装PSAM卡卡号
        var ic_serial = "";// 读卡器序列号
        var ic_ybid = "";// 医保ID
        var ic_icno = "";// 卡号
        var ic_name = "";// 姓名(社保卡)
        var ic_xb00 = "";// 性别
        var ic_csrq = "";// 出生日期
        var ic_comp = "";// 公司
        var ic_lxdz = "";// 地址
        var ic_lxdh = "";// 联系电话
        var ic_str = result.split("+");
        for (var i = 1; i < ic_str.length; i++) {// 取出读出值内容
            var ic_key = ic_str[i];
            if (ic_key.indexOf("OK:") >= 0) {// IC卡号
                ic_icno = (ic_key.substring(ic_key.indexOf("OK:") + 3, ic_key.length)).replace(new RegExp(' ', 'gm'), "");
            }
            if (ic_key.indexOf("TYPE:") >= 0) {// IC卡卡片类型
                ic_type = (ic_key.substring(ic_key.indexOf("TYPE:") + 5, ic_key.length)).replace(new RegExp(' ', 'gm'), "");
            }
            if (ic_key.indexOf("PSAM:") >= 0) {// 卡具内装PSAM卡卡号
                ic_psam = (ic_key.substring(ic_key.indexOf("PSAM:") + 5, ic_key.length)).replace(new RegExp(' ', 'gm'), "");
            }
            if (ic_key.indexOf("SERIAL:") >= 0) {// 读卡器序列号
                ic_serial = (ic_key.substring(ic_key.indexOf("SERIAL:") + 5, ic_key.length)).replace(new RegExp(' ', 'gm'), "");
            }
            if (ic_key.indexOf("ID0000:") >= 0) {// 医保ID
                ic_ybid = (ic_key.substring(ic_key.indexOf("ID0000:") + 7, ic_key.length)).replace(new RegExp(' ', 'gm'), "");
            }
            if (ic_key.indexOf("NAME:") >= 0) {// 姓名
                ic_name = (ic_key.substring(ic_key.indexOf("NAME:") + 5, ic_key.length)).replace(new RegExp(' ', 'gm'), "");
            }
            if (ic_key.indexOf("SEX:") >= 0) {// 性别
                ic_xb00 = (ic_key.substring(ic_key.indexOf("SEX:") + 4, ic_key.length)).replace(new RegExp(' ', 'gm'), "");
                if (ic_xb00 == "0") {
                    ic_xb00 = "2";
                }
            }
            if (ic_key.indexOf("BIRTHDATE:") >= 0) {// 出生日期
                ic_csrq = (ic_key.substring(ic_key.indexOf("BIRTHDATE:") + 10, ic_key.length)).replace(new RegExp(' ', 'gm'), "");
            }
            if (ic_key.indexOf("COMPANY:") >= 0) {// 公司
                ic_comp = (ic_key.substring(ic_key.indexOf("COMPANY:") + 8, ic_key.length)).replace(new RegExp(' ', 'gm'), "");
            }
            if (ic_key.indexOf("ADDRESS:") >= 0) {// 地址
                ic_lxdz = (ic_key.substring(ic_key.indexOf("ADDRESS:") + 8, ic_key.length)).replace(new RegExp(' ', 'gm'), "");
            }
            if (ic_key.indexOf("TELEPHONE:") >= 0) {// 联系电话
                ic_lxdh = (ic_key.substring(ic_key.indexOf("TELEPHONE:") + 10, ic_key.length)).replace(new RegExp(' ', 'gm'), "");
            }
        }
        // IC读卡内容返回
        var ic_info = {
            "ic_type": ic_type,
            "ic_psam": ic_psam,
            "ic_serial": ic_serial,
            "ic_icno": ic_icno,
            "ic_ybid": ic_ybid,
            "ic_name": ic_name,
            "ic_xb00": ic_xb00,
            "ic_csrq": ic_csrq,
            "ic_comp": ic_comp,
            "ic_lxdz": ic_lxdz,
            "ic_lxdh": ic_lxdh
        };
        return ic_info;
    } else {// 机构代码为其它
        if (xzqh00 == "") {
            // 行政区划代码尚未初始化，请联系管理员！
            alert("\u884c\u653f\u533a\u5212\u4ee3\u7801\u5c1a\u672a\u521d\u59cb\u5316\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\uff01");
            return ic_err;
        } else {
            xzqh00 = xzqh00.substr(0, 4);
        }
        // 1.读医保卡 cjw
        var ybk = "";
        icklb0 = icklb0.replace(/ /g, '');
        if (DrTypeRole == null || DrTypeRole == "1" || DrTypeRole == "null" || DrTypeRole == "") {
            switch (icklb0) {
                case '002':// 卡具型号 2型
                    ybk = iccard.GetSSSECardno(xzqh00, 10);
                    break;
                case '003':// 3型
                    if ("" == "MY") {
                        ybk = iccard.GetSSSECardno(xzqh00, 121);
                    } else {
                        ybk = iccard.GetSSSECardno(xzqh00, 12);
                    }
                    break;
                default:  // 默认型号为2型
                    ybk = iccard.GetSSSECardno(xzqh00, 10);
            }
        } else {
            ybk = iccard.GetSSSECardno(xzqh00, 121);
        }
        if (ybk.substr(0, 3) == "+OK") {
            result = ybk;
        } else {
            // 2.读就诊卡
            var jzk = "";
            if (ybjg00 == "ZZ") {
                jzk = iccard.GetSSSECardno("ZZJK", 12);
            } else if (ybjg00 == "PT") {
                jzk = iccard.GetSSSECardno("PTNEW", 10);// 莆田市民健康卡
            } else {
                if (fwwdbh == "QZHA") {
                    // 泉州惠安涂寨卫生院卡特殊处理
                    jzk = iccard.GetSSSECardno("QZHA", 12);
                } else {
                    jzk = iccard.GetSSSECardno("PTJK", 12);
                }
            }
            var result = "";
            if (jzk.substr(0, 3) == "+OK") {
                result = jzk;
            } else {
                // 提示读卡出错信息
                alert(ybk);
                return ic_err;
            }
        }
        // 20110212修改IC读卡返回值类型为字符串对象，约定相关值如下：
        var ic_type = "";// 卡片类型
        var ic_psam = "";// 卡具内装PSAM卡卡号
        var ic_serial = "";// 读卡器序列号
        var ic_ybid = "";// 医保ID
        var ic_icno = "";// 卡号
        var ic_name = "";// 姓名(社保卡)
        var ic_xb00 = "";// 性别
        var ic_csrq = "";// 出生日期
        var ic_comp = "";// 公司
        var ic_lxdz = "";// 地址
        var ic_lxdh = "";// 联系电话
        var ic_str = result.split("+");
        for (var i = 1; i < ic_str.length; i++) {// 取出读出值内容
            var ic_key = ic_str[i];
            if (ic_key.indexOf("OK:") >= 0) {// IC卡号
                ic_icno = (ic_key.substring(ic_key.indexOf("OK:") + 3, ic_key.length)).replace(new RegExp(' ', 'gm'), "");
            }
            if (ic_key.indexOf("TYPE:") >= 0) {// IC卡卡片类型
                ic_type = (ic_key.substring(ic_key.indexOf("TYPE:") + 5, ic_key.length)).replace(new RegExp(' ', 'gm'), "");
            }
            if (ic_key.indexOf("PSAM:") >= 0) {// 卡具内装PSAM卡卡号
                ic_psam = (ic_key.substring(ic_key.indexOf("PSAM:") + 5, ic_key.length)).replace(new RegExp(' ', 'gm'), "");
            }
            if (ic_key.indexOf("SERIAL:") >= 0) {// 读卡器序列号
                ic_serial = (ic_key.substring(ic_key.indexOf("SERIAL:") + 5, ic_key.length)).replace(new RegExp(' ', 'gm'), "");
            }
            if (ic_key.indexOf("ID0000:") >= 0) {// 医保ID
                ic_ybid = (ic_key.substring(ic_key.indexOf("ID0000:") + 7, ic_key.length)).replace(new RegExp(' ', 'gm'), "");
            }
            if (ic_key.indexOf("NAME:") >= 0) {// 姓名
                ic_name = (ic_key.substring(ic_key.indexOf("NAME:") + 5, ic_key.length)).replace(new RegExp(' ', 'gm'), "");
            }
            if (ic_key.indexOf("SEX:") >= 0) {// 性别
                ic_xb00 = (ic_key.substring(ic_key.indexOf("SEX:") + 4, ic_key.length)).replace(new RegExp(' ', 'gm'), "");
                if (ic_xb00 == "0") {
                    ic_xb00 = "2";
                }
            }
            if (ic_key.indexOf("BIRTHDATE:") >= 0) {// 出生日期
                ic_csrq = (ic_key.substring(ic_key.indexOf("BIRTHDATE:") + 10, ic_key.length)).replace(new RegExp(' ', 'gm'), "");
            }
            if (ic_key.indexOf("COMPANY:") >= 0) {// 公司
                ic_comp = (ic_key.substring(ic_key.indexOf("COMPANY:") + 8, ic_key.length)).replace(new RegExp(' ', 'gm'), "");
            }
            if (ic_key.indexOf("ADDRESS:") >= 0) {// 地址
                ic_lxdz = (ic_key.substring(ic_key.indexOf("ADDRESS:") + 8, ic_key.length)).replace(new RegExp(' ', 'gm'), "");
            }
            if (ic_key.indexOf("TELEPHONE:") >= 0) {// 联系电话
                ic_lxdh = (ic_key.substring(ic_key.indexOf("TELEPHONE:") + 10, ic_key.length)).replace(new RegExp(' ', 'gm'), "");
            }
        }
        // 正则表达验证IC卡号
        var reg1 = /^([A-Za-z0-9])+([\@])*([A-Za-z0-9])+$/;// 验证字母或数字 或@符号(异地医保)
        var dex = ic_icno.indexOf("@");
        if (!reg1.test(ic_icno) && ic_icno != "") {
            // 此卡有问题，请联系管理员
            alert("(\u5361\u53f7\uff1a " + ic_icno + " )\u6b64\u5361\u6709\u95ee\u9898\uff01\n\u3000\u3000\u8bf7\u786e\u8ba4\u5361\u7247\u662f\u5426\u6b63\u786e\u63d2\u5165\u6216\u79fb\u4ea4\u7ba1\u7406\u5458\uff01");
            return {"ic_icno": ""};
        }
        // IC读卡内容返回
        var ic_info = {
            "ic_type": ic_type,
            "ic_psam": ic_psam,
            "ic_serial": ic_serial,
            "ic_icno": ic_icno,
            "ic_ybid": ic_ybid,
            "ic_name": ic_name,
            "ic_xb00": ic_xb00,
            "ic_csrq": ic_csrq,
            "ic_comp": ic_comp,
            "ic_lxdz": ic_lxdz,
            "ic_lxdh": ic_lxdh
        };
        return ic_info;

        /*// 若卡号后面有+TYPE:1则去掉
        if (result.indexOf("+TYPE:1") > 0) {
            result = result.replace("+TYPE:1", "")
        }
        // 若卡号后面有+PSAM:则去掉
        if (result.indexOf("+PSAM:") > 0) {
            result = result.substring(0, result.indexOf("+PSAM:"));
        }
        // 若卡号后面有+TYPE:莆田 则去掉(莆田健康卡)
        if (result.indexOf("+TYPE:") > 0) {
            result = result.substring(0, result.indexOf("+TYPE:"))
        }
        // 直接返回卡号(去掉卡号前面的前缀+OK:)
        return result//.substr(4);// 原返回值类型*/
    }
}

function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg); // 匹配目标参数
    if (r != null) return r[2];
    return null; // 返回参数值
}

function getQueryString(name) {
    var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return unescape(r[2]);
    }
    return null;
}

/**
 * cjw
 * 中文转译
 * @param name
 * @returns {*}
 */
function getQuerytoString(name) {
    var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return decodeURI(r[2]);
    }
    return null;
}

function myOpen(name, url, callback) {
    layer.open({
        type: 2,
        maxmin: true,
        title: name,
        offset: "10px",
        shadeClose: true,
        shade: 0.8,
        area: ['68%', '90%'],
        content: url,
        end: callback
    });
}

function myAssessViewOpen(name, url, w, h, callback) {
    // top的URL需填写绝对路径
    top.layer.open({
        type: 2,
        maxmin: true,
        title: name,
        offset: "10px",
        shadeClose: true,
        shade: 0.8,
        area: [w, h],
        content: url,
        end: callback
    });
}

function myOpenwh(name, url, w, h) {
    layer.open({
        type: 2,
        maxmin: true,
        title: name,
        shadeClose: true,
        shade: 0.8,
        area: [w, h],
        content: phisUrl + url
    });
}

/**
 * 消息弹出层
 */
function alertMsgOpen(content) {
    layer.open({
        title: '提示',
        content: content
    });
}

/**
 * 固定位置弹出
 */
function myFixOpenwh(name, url, w, h) {
    layer.open({
        type: 2,
        maxmin: true,
        title: name,
        offset: "10px",
        shadeClose: false,
        shade: 0.8,
        area: [w, h],
        content: phisUrl + url
    });
}

/**
 * 重置input和select组件的数据
 * @param id
 */
function resetData(id) {
    $('#' + id + ' input').val('');
    $('#' + id + ' select').val('');
}

function alertMsg(msgs, tm, posit, styl) {
    var sh = 6;
    var ofs = 100;
    var time = 3000;
    if (isNotBlank(tm)) {
        time = tm;
    }
    if (isNotBlank(styl)) {
        sh = styl;
    }
    if (isNotBlank(posit)) {
        ofs = posit;
    }
    layer.msg(msgs, {
        time: time,
        offset: ofs,
        shift: sh
    });
}

function isNotBlank(data) {// 判断不为空
    if (data == null || data == undefined || data == "") {
        return false;
    } else {
        return true;
    }
}

/**
 * 身份证算年纪 实岁
 * @param identityCard
 * @param nowtime
 * @returns {*}
 * @constructor
 */
function GetAge(identityCard, nowtime) {
    var len = (identityCard + "").length;
    if (len == 0) {
        return 0;
    } else {
        if ((len != 15) && (len != 18))// 身份证号码只能为15位或18位其它不合法
        {
            return 0;
        }
    }
    var strBirthday = "";
    if (len == 18) {// 处理18位的身份证号码从号码中得到生日和性别代码
        strBirthday = identityCard.substr(6, 4) + "/" + identityCard.substr(10, 2) + "/" + identityCard.substr(12, 2);
    }
    if (len == 15) {
        strBirthday = "19" + identityCard.substr(6, 2) + "/" + identityCard.substr(8, 2) + "/" + identityCard.substr(10, 2);
    }
    // 时间字符串里，必须是“/”
    var birthDate = new Date(strBirthday);
    var nowDateTime;
    if (nowtime == null) {
        nowDateTime = new Date();
    } else {
        nowDateTime = new Date(nowtime.replace(/-/, "/"));// 获取服务器上的时间
    }
    var age = nowDateTime.getFullYear() - birthDate.getFullYear();
    // 再考虑月、天的因素;.getMonth()获取的是从0开始的，这里进行比较，不需要加1
    if (nowDateTime.getMonth() < birthDate.getMonth() || (nowDateTime.getMonth() == birthDate.getMonth() && nowDateTime.getDate() < birthDate.getDate())) {
        age--;
    }
    // 0岁的显示几个月
    if (age == 0) {
        var diffMonth = (nowDateTime.getFullYear() * 12 + nowDateTime.getMonth()) - (birthDate.getFullYear() * 12 + birthDate.getMonth());
        var diff = nowDateTime.getDate() - birthDate.getDate();
        if (diff >= 0) {
            return diffMonth + "个月";
        } else {
            return diffMonth - 1 + "个月";
        }
    }
    return age;
}


/**
 * 把对象中的NULL属性替换成""
 * @param obj
 * @returns {*}
 */
function objectNullToEmp(obj) {
    $.each(obj, function (name, val) {
        if (val == null) {
            obj[name] = "";
        }
    });
    return obj;
}

/**
 * 把对像中的属性值都清空
 * @param obj
 * @returns {*}
 */
function objectClearValue(obj) {
    $.each(obj, function (name, val) {
        obj[name] = "";
    });
    return obj;
}

/**
 *关闭layer 的弹出的查看和编辑页
 */
function closeLayer() {
    var index = parent.layer.getFrameIndex(window.name);// 获取当前弹出层
    parent.layer.close(index);//关闭当前弹出层
    // window.parent.location.reload();// 刷新父页
}

/**
 * 导出插件 class带noExl 的行或列将不被导出
 * @param tableid
 * @param name
 * @param type
 */
function tabExp(tableid, name, type) {
    $("#" + tableid).table2excel({
        exclude: ".noExl",
        name: name,
        filename: name,
        fileext: "." + type,
        exclude_img: true,
        exclude_links: true,
        exclude_inputs: true
    });
}

function tabExpByClass(tableclass, name, type) {
    $("." + tableclass).table2excel({
        exclude: ".noExl",
        name: name,
        filename: name,
        fileext: "." + type,
        exclude_img: true,
        exclude_links: true,
        exclude_inputs: true
    });
}

/**
 * 小tips层显示
 * @param text  弹出显示的文字(必)
 * @param id    选择器Id(必)
 * @param rbg   层的底色(如#009688绿色、#01AAED蓝色)
 * @param timeNum   需要显示的毫秒数
 * @param posit 显示相对于与选择器的位置：1上 2右 3下 4左
 * @return index tips的index(关闭tips：layer.close(index))
 */
function tipsShow(text, id, rbg, timeNum, posit) {
    var color = '#009688';
    if (isNotBlank(rbg)) {
        color = rbg;
    }
    var time = 0;
    if (isNotBlank(timeNum)) {
        time = timeNum;
    }
    var position = 1;
    if (isNotBlank(posit)) {
        position = posit;
    }
    var index = layer.tips(text, '#' + id, {
        tips: [position, color],
        time: time,
        fixed: true // 吸附于元素上
    });
    return index;
}

var tipsIndex = 0;

/**
 * 控制当鼠标进入组件时显示tips，鼠标离开则tips消失
 * @param text  提示的文本
 * @param id    组件id
 * @param color 颜色rbg
 * @param timeNum   需要显示的毫秒数
 * @param posit 显示相对于与选择器的位置：1上 2右 3下 4左
 * @return index tips的index(关闭tips：layer.close(index)) mouseentershMenu
 */
function peControlTips(text, id, color, timeNum, posit) {
    $(document).off("mouseenter", '#' + id);// 不管有无，先解绑
    $(document).on('mouseenter', '#' + id, function () {
        tipsIndex = tipsShow(text, id, color, timeNum, posit);
    });
    $(document).off("mouseleave", '#' + id);
    $(document).on('mouseleave', '#' + id, function () {
        layer.close(tipsIndex);
    });
    return tipsIndex;
}


/**
 * 传入数组，和对像ID。拼接下拉菜单。
 * @param id
 * @param arr
 */
function arrToOption(id, arr) {
    var html = "";
    if (arr != null) {
        for (var i = 0; i < arr.length; i++) {
            html += "<option value=" + arr[i].code + ">" + arr[i].value + "</option>";
        }
    }
    $("#" + id).append(html);
}


/**
 * 身份证判断 男女
 * cjw
 */

function GetSEX(e) {
    var sexvalue = "";
    if (e != "" && e != null) {
        var sex = e.substring(16, 17);
        if (sex % 2 == 0) {
            sexvalue = "2";// 女
        } else {
            sexvalue = "1";// 男
        }
    }
    return sexvalue;
}

/**
 * 身份证读卡 功能蛮全的可以看一下  cjw
 */
function MyGetData() {// OCX读卡成功后的回调函数
    Name.value = GT2ICROCX.Name;// <-- 姓名--!>
    namel.value = GT2ICROCX.NameL;// <-- 姓名--!>
    sex.value = GT2ICROCX.Sex;// <-- 性别--!>
    sexl.value = GT2ICROCX.SexL;// <-- 性别--!>
    nation.value = GT2ICROCX.Nation;// <-- 民族--!>
    nationl.value = GT2ICROCX.NationL;// <-- 民族--!>
    born.value = GT2ICROCX.Born;// <-- 出生--!>
    bornl.value = GT2ICROCX.BornL;// <-- 出生--!>
    address.value = GT2ICROCX.Address;// <-- 地址--!>
    // 将全角字符串转换为半角
    address.value = GT2ICROCX.TransCaseString(GT2ICROCX.Address);
    cardno.value = GT2ICROCX.CardNo;// <-- 卡号--!>
    police.value = GT2ICROCX.Police;// <-- 发证机关--!>
    /* activity.value = GT2ICROCX.Activity;// <-- 有效期--!>
     activityl.value = GT2ICROCX.ActivityL;// <-- 有效期--!>
     activityfrom.value = GT2ICROCX.ActivityFrom;// <-- 有效期--!>
     activitylfrom.value = GT2ICROCX.ActivityLFrom;// <-- 有效期--!>
     activityto.value = GT2ICROCX.ActivityTo;// <-- 有效期--!>
     activitylto.value = GT2ICROCX.ActivityLTo;// <-- 有效期--!>*/
    /* cjw
    * 生成图片 暂无用
     */
    /*  GT2ICROCX.TxtData2File(GT2ICROCX.Name, "c:\\GtTest\\txt.txt");
      GT2ICROCX.DelFile("c:\\GtTest\\txt.txt");

      //取当前读取的身份证头像wlt格式
      wlt.value = GT2ICROCX.Base64Wlt ;
      GT2ICROCX.Base64Data2File(GT2ICROCX.Base64Wlt2Bmp(GT2ICROCX.Base64Wlt), "c:\\GtTest\\wltzp.bmp");
      GT2ICROCX.Base64Data2File(GT2ICROCX.Base64Wlt2Bmp(GT2ICROCX.Base64Wlt), "c:\\GtTest\\wltzp.jpg");

      //取当前读取的身份证头像BMP格式
      Base64Bmp.value = GT2ICROCX.Base64Bmp ;//
      GT2ICROCX.Base64Data2File(GT2ICROCX.Base64Bmp, "c:\\GtTest\\zp.bmp");
      document.all['Base64BmpDisplay'].src = "data:image/bmp;base64," + GT2ICROCX.Base64Bmp;

      //取当前读取的身份证头像JPG格式
      Base64Jpg.value = GT2ICROCX.Base64Jpg ;//
      GT2ICROCX.Base64Data2File(GT2ICROCX.Base64Jpg, "c:\\GtTest\\zp.jpg");
      document.all['Base64JpgDisplay'].src = "data:image/jpeg;base64," + GT2ICROCX.Base64Jpg;

      //取当前读取的身份证正反面纵向图
      printjpg.value = GT2ICROCX.Base64PrintJpg;
      document.all['Base64PrintJpgDisplay'].src = "data:image/jpeg;base64," + GT2ICROCX.Base64PrintJpg;
      GT2ICROCX.Base64Data2File(GT2ICROCX.Base64PrintJpg, "c:\\GtTest\\print.jpg");

      //取当前读取的身份证正面图
      facejpg.value = GT2ICROCX.Base64FaceJpg;
      document.all['Base64FaceJpgDisplay'].src = "data:image/jpeg;base64," + GT2ICROCX.Base64FaceJpg;
      GT2ICROCX.Base64Data2File(GT2ICROCX.Base64FaceJpg, "c:\\GtTest\\face.jpg");

      //取当前读取的身份证反面图
      backjpg.value = GT2ICROCX.Base64BackJpg;
      document.all['Base64BackJpgDisplay'].src = "data:image/jpeg;base64," + GT2ICROCX.Base64BackJpg;
      GT2ICROCX.Base64Data2File(GT2ICROCX.Base64BackJpg, "c:\\GtTest\\back.jpg");

      //取当前读取的身份证正反面横向图
      printjpg1.value = GT2ICROCX.Base64PrintJpg1;
      document.all['Base64PrintJpg1Display'].src = "data:image/jpeg;base64," + GT2ICROCX.Base64PrintJpg1;
      GT2ICROCX.Base64Data2File(GT2ICROCX.Base64PrintJpg1, "c:\\GtTest\\print1.jpg");

      var szTxt = GT2ICROCX.NameL + "|" + GT2ICROCX.SexL + "|" + GT2ICROCX.NationL + "|" + GT2ICROCX.Born + "|" +
          GT2ICROCX.Address + "|" + GT2ICROCX.CardNo + "|" + GT2ICROCX.Police + "|" + GT2ICROCX.StartDate + "|" + GT2ICROCX.EndDate + "|";

      //用给定身份证信息生成身份证纵向jpg的base64编码，然后保存成图片文件
      GT2ICROCX.Base64Data2File(GT2ICROCX.Base64JpgFrmTxt(szTxt, GT2ICROCX.Base64Wlt, 0), "c:\\GtTest\\Base64JpgFrmTxt.jpg");
      //用给定身份证信息生成身份证横向jpg的base64编码，然后保存成图片文件
      GT2ICROCX.Base64Data2File(GT2ICROCX.Base64Jpg1FrmTxt(szTxt, GT2ICROCX.Base64Wlt, 0), "c:\\GtTest\\Base64Jpg1FrmTxt.jpg");
  */
    /*f1.Name.value = GT2ICROCX.Name;
    f1.Sex.value = GT2ICROCX.SexL;
    f1.National.value = GT2ICROCX.NationL;
    f1.Born.value = GT2ICROCX.Born;
    f1.Address.value = GT2ICROCX.Address;
    f1.CardNo.value = GT2ICROCX.CardNo;
    f1.Police.value = GT2ICROCX.Police;
    f1.Activityfrom.value = GT2ICROCX.activityfrom;
    f1.Activityto.value = GT2ICROCX.activityto;
    f1.Base64Bmp.value = GT2ICROCX.Base64Bmp;
    f1.Base64Jpg.value = GT2ICROCX.Base64Jpg;*/
    var ic_info = {
        "ic_name": Name.value,
        "ic_xb00": sex.value,
        "ic_csrq": born.value,
        "ic_cerd": cardno.value,
        "ic_address": address.value
    };
    return ic_info;
}

function MyClearData() {// OCX读卡失败后的回调函数
    Name.value = "";
    namel.value = "";
    sex.value = "";
    sexl.value = "";
    nation.value = "";
    nationl.value = "";
    born.value = "";
    bornl.value = "";
    address.value = "";
    cardno.value = "";
    police.value = "";
    /* activity.value = "";
     activityl.value = "";
     activityfrom.value = "";
     activitylfrom.value = "";
     activityto.value = "";
     activitylto.value = "";
     wlt.value = "";
     Base64Jpg.value = "";
     Base64Bmp.value = "";
     printjpg.value = "";

     document.all['Base64BmpDisplay'].src = "";
     document.all['Base64JpgDisplay'].src = "";
     document.all['Base64PrintJpgDisplay'].src = "";
     document.all['Base64FaceJpgDisplay'].src = "";
     document.all['Base64BackJpgDisplay'].src = "";
     document.all['Base64PrintJpg1Display'].src = "";

     f1.Name.value = "";
     f1.Sex.value = "";
     f1.National.value = "";
     f1.Born.value = "";
     f1.Address.value = "";
     f1.CardNo.value = "";
     f1.Police.value = "";
     f1.Activityfrom.value = "";
     f1.Activityto.value = "";
     f1.Base64Bmp.value = "";
     f1.Base64Jpg.value = "";*/

}


function StartRead() {// 开始读卡
    MyClearData();
    var ret = GT2ICROCX.ReadCard();
    if (ret == "0") {
        var NewObj = MyGetData();
        return NewObj;
    }
    alert("读卡错误，错误原因：" + ret);
}

function PrintImg() {// 打印身份证
    GT2ICROCX.PrintImage(10, 10, 0);// 在指定位置(nX, nY)打印读取的纵向身份证信息（0-正反面，1-正面，2-反面）
}

function PrintImg1() {// 打印身份证
    GT2ICROCX.PrintImage1(10, 10, 0);// 在指定位置(nX, nY)打印读取的横向身份证信息（0-正反面，1-正面，2-反面）
}

function PrintImg2() {// 打印身份证
    var szTxt = GT2ICROCX.NameL + "|" + GT2ICROCX.SexL + "|" + GT2ICROCX.NationL + "|" + GT2ICROCX.Born + "|" +
        GT2ICROCX.Address + "|" + GT2ICROCX.CardNo + "|" + GT2ICROCX.Police + "|" + GT2ICROCX.StartDate + "|" + GT2ICROCX.EndDate + "|";
    GT2ICROCX.PrintImageFrmTxt(szTxt, GT2ICROCX.Base64Wlt, 10, 10, 0);// 在指定位置(nX, nY)打印读取的纵向身份证信息（0-正反面，1-正面，2-反面）
}

function PrintImg3() {// 打印身份证
    var szTxt = GT2ICROCX.NameL + "|" + GT2ICROCX.SexL + "|" + GT2ICROCX.NationL + "|" + GT2ICROCX.Born + "|" +
        GT2ICROCX.Address + "|" + GT2ICROCX.CardNo + "|" + GT2ICROCX.Police + "|" + GT2ICROCX.StartDate + "|" + GT2ICROCX.EndDate + "|";
    GT2ICROCX.PrintImage1FrmTxt(szTxt, GT2ICROCX.Base64Wlt, 10, 10, 0);// 在指定位置(nX, nY)打印读取的横向身份证信息（0-正反面，1-正面，2-反面）
}

/**
 *  cjw  加减乘除 方法
 * @param arg1 参数  减数    除数
 * @param arg2 参数  被减数  被除数
 * @param type 类型 1 加法 2 减法 3 乘法 4 除法
 * @returns {number}
 * @constructor
 */
function Calculation(arg1, arg2, type1) {
    if (type1 == "1") {
        var r1, r2, m, c;
        try {
            r1 = arg1.toString().split(".")[1].length;
        }
        catch (e) {
            r1 = 0;
        }
        try {
            r2 = arg2.toString().split(".")[1].length;
        }
        catch (e) {
            r2 = 0;
        }
        c = Math.abs(r1 - r2);
        m = Math.pow(10, Math.max(r1, r2));
        if (c > 0) {
            var cm = Math.pow(10, c);
            if (r1 > r2) {
                arg1 = Number(arg1.toString().replace(".", ""));
                arg2 = Number(arg2.toString().replace(".", "")) * cm;
            } else {
                arg1 = Number(arg1.toString().replace(".", "")) * cm;
                arg2 = Number(arg2.toString().replace(".", ""));
            }
        } else {
            arg1 = Number(arg1.toString().replace(".", ""));
            arg2 = Number(arg2.toString().replace(".", ""));
        }
        return (arg1 + arg2) / m;
    } else if (type1 == "2") {
        var r1, r2, m, n;
        try {
            r1 = arg1.toString().split(".")[1].length;
        }
        catch (e) {
            r1 = 0;
        }
        try {
            r2 = arg2.toString().split(".")[1].length;
        }
        catch (e) {
            r2 = 0;
        }
        m = Math.pow(10, Math.max(r1, r2));// 动态控制精度长度 // last modify by deeka
        n = (r1 >= r2) ? r1 : r2;
        return ((arg1 * m - arg2 * m) / m).toFixed(n);
    } else if (type1 == "3") {
        var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
        try {
            m += s1.split(".")[1].length;
        }
        catch (e) {
        }
        try {
            m += s2.split(".")[1].length;
        }
        catch (e) {
        }
        return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m);

    } else if (type1 == "4") {
        var t1 = 0, t2 = 0, r1, r2;
        try {
            t1 = arg1.toString().split(".")[1].length;
        }
        catch (e) {
        }
        try {
            t2 = arg2.toString().split(".")[1].length;
        }
        catch (e) {
        }
        with (Math) {
            r1 = Number(arg1.toString().replace(".", ""));
            r2 = Number(arg2.toString().replace(".", ""));
            return (r1 / r2) * pow(10, t2 - t1);
        }
    }

}

/**
 *  公共初始化方法
 *  各地市要初始化什么配置 在这边操作
 * @constructor
 */
function Initialization() {
    if (HospAreaCode != null && HospAreaCode != "") {
        var code = HospAreaCode.substring(0, 4);
        doAjaxPost('signAction.action?act=findsignSetting', {hospAreaCode: code}, function (data) {
            if (data.msgCode == "800" && data.vo != null) {
                if (data.vo.serSignState == "1") {
                    if (data.vo.serSignDay != null && data.vo.serSignDay != "") {
                        Day = data.vo.serSignDay;// 协议日期可选天数
                        dateModify = data.vo.serSignModifyState;
                        jdBeforeSign = data.vo.serJdBeforeSign;// 是否开启签约前先建档功能
                        openResitAssess = data.vo.serResitAssess;// 是否开启补考功能
                        sqylOnline = data.vo.serSqylOnline;// 基卫项目是否在线
                        openYear = data.vo.serOpenYear;// 是否开启自然年度签约
                    } else {
                        layer.msg("初始化失败，参数不全，请联系管理员！");
                    }
                }
            } else {
                layer.msg("初始化失败，请联系管理员！");
            }
        });
        if (code == "3501") {// 福州
            // $("#patientCard").attr("disabled", "disabled");
            $("#singlx").html("");
            $("#singlx").append("<input type='radio' validator='{\"msg\":\"医保类型不能为空!\"}' value='0' name='signlx' title='省医保' style='width:25px;height:25px;'pofield='signlx' > &nbsp;&nbsp;省医保&nbsp;&nbsp;"
                + " <input type='radio' validator='{\"msg\":\"医保类型不能为空!\"}' name='signlx' value='1' title='市医保' style='width:25px;height:25px;' pofield='signlx' >&nbsp;&nbsp;市医保&nbsp;&nbsp;"
                + " <input type='radio' validator='{\"msg\":\"医保类型不能为空!\"}' disabled='disabled' name='signlx' value='2' title='新农合' style='width:25px;height:25px;' pofield='signlx'>&nbsp;&nbsp;新农合&nbsp;&nbsp;");
            $("#xuser").val(xuuser);
            $("#xpaw").val(xpaw);
            $("#ypaw").val(ypaw);
            $("#yuser").val(yuuser);
            $("#signFromDate").attr("disabled", "disabled");
            $("#printheader").show();
            $("#printtailId").show();
        } else if (code == "3505") {// 泉州
            $("#singlx").html("");
            $("#singlx").append(" <input type='radio' validator='{\"msg\":\"医保类型不能为空!\"}' name='signlx' value='1' title='市医保' style='width:25px;height:25px;' pofield='signlx' >&nbsp;&nbsp;市医保&nbsp;&nbsp;");
        }
    } else {
        layer.msg("初始化失败，请联系管理员！");
    }
}

/**
 * 冒泡排序法
 * cjw
 * @param arr
 * @returns {*}
 */
function bubbleSort(arr, cityArr, codeArr,lvArr) {
    for (var i = 0; i < arr.length - 1; i++) {
        for (var j = i + 1; j < arr.length; j++) {
            if (arr[i] * 100 < arr[j] * 100) {// 如果前面的数据比后面的小就交换
                var temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                // 还要对地区名称进行排序
                if (cityArr != null) {
                    var cityTemp = cityArr[i];
                    cityArr[i] = cityArr[j];
                    cityArr[j] = cityTemp;
                }
                if (codeArr != null) {
                    var cityTemp = codeArr[i];
                    codeArr[i] = codeArr[j];
                    codeArr[j] = cityTemp;
                }
                if(lvArr != null){
                    var lvTemp = lvArr[i];
                    lvArr[i] = lvArr[j];
                    lvArr[j] = lvTemp;
                }
            }
        }
    }
    return arr;
}

/**
 * 取每个月的最后一天
 * @param year
 * @param month
 * @returns {number}
 */
function getLastDay(year, month) {
    var new_year = year;// 取当前的年份
    var new_month = month++;// 取下一个月的第一天，方便计算（最后一天不固定）
    if (month > 12) {// 如果当前大于12月，则年份转到下一年
        new_month -= 12;// 月份减
        new_year++;// 年份增
    }
    var new_date = new Date(new_year, new_month, 1);// 取当年当月中的第一天
    return (new Date(new_date.getTime() - 1000 * 60 * 60 * 24)).getDate();// 获取当月最后一天日期
}

/**
 * 获取当前日期前后N天的方法
 * @param AddDayCount
 * @returns {string}
 * @constructor
 */
function GetDateStr(AddDayCount) {
    var dd = new Date();
    dd.setDate(dd.getDate() + AddDayCount);// 获取AddDayCount天后的日期
    var y = dd.getFullYear();
    var m = dd.getMonth() + 1;// 获取当前月份的日期
    var d = dd.getDate();
    return y + "/" + m + "/" + d;
}

function addPreZero(num) {
    if (num.toString().length < 2) {
        return '0' + num;
    } else {
        return num;
    }
}

/**
 *  计算 跨月份时间
 * @param m  月份
 * @param d 当前天
 * @param date
 * @returns {*}
 */
function getdate(m, d, date) {
    if (m.toString().indexOf("1") > -1) {// 30
        var a = 30 - date + d;
        return a;
    } else if (m.toString().indexOf("3") > -1) {
        var a = 30 - date + d;
        return a;
    } else if (m.toString().indexOf("5") > -1) {
        var a = 30 - date + d;
        return a;
    } else if (m.toString().indexOf("7") > -1) {
        var a = 30 - date + d;
        return a;
    } else if (m.toString().indexOf("8") > -1) {
        var a = 30 - date + d;
        return a;
    } else if (m.toString().indexOf("10") > -1) {
        var a = 30 - date + d;
        return a;
    } else if (m.toString().indexOf("12") > -1) {
        var a = 30 - date + d;
        return a;
    } else if (m.toString().indexOf("4") > -1) {// 31
        var a = 31 - date + d;
        return a;
    } else if (m.toString().indexOf("6") > -1) {// 31
        var a = 31 - date + d;
        return a;
    } else if (m.toString().indexOf("9") > -1) {// 31
        var a = 31 - date + d;
        return a;
    } else if (m.toString().indexOf("11") > -1) {// 31
        var a = 31 - date + d;
        return a;
    } else if (m.toString().indexOf("2") > -1) {// 28
        var a = 28 - date + d;
        return a;
    }
}

/**
 * 限制文本框只能输入数字
 */
function numValidate(obj) {
    obj.value = obj.value.replace(/[^\d]/g, '');
}

/**
 * 取修改对象
 * @param old_vo
 * @param new_vo
 * @returns {Array}
 */
function getLogVo(old_vo, new_vo) {
    var logs = [];
    for (var o in old_vo) {
        for (var n in new_vo) {
            if (o == n) {// 找到同属性值
                if (old_vo[o] == null) {// 旧值的null，赋值到界面后，对于取到后的新值为""
                    old_vo[o] = "";
                }
                if (old_vo[o] != new_vo[n]) {
                    var log = {};
                    log.key = o;
                    log.oldValue = old_vo[o];
                    log.newValue = new_vo[n];
                    logs.push(log);
                }
            }
        }
    }
    return logs;
}
