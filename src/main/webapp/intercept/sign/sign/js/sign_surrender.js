/**
 * Created by lenovo on 2017/12/3.
 */

var id = getQueryString("id");
var signDrId = getQueryString("signDrId");
var teamId = getQueryString("teamId");
var drName = decodeURI(getQueryString("drName"));
var patientId = getQueryString("patientId");
var patientjmda = getQueryString("patientjmda");
var index = parent.layer.getFrameIndex(window.name);

/**
 * 页面加载完成后执行
 */
$(function () {
    $("#teamId").val(teamId);
    $("#drId").val(signDrId);
    $("#hospId").val(orgid);
    $("#drName").val(drName);
    $("#patientId").val(patientId);
});

/**
 * 返回
 */
function goto() {
    parent.layer.close(index);
}

/**
 * 解约操作
 */
function surrenderAdd() {
    if (validator("form_vo")) {
        var vo = {};
        vo = uiToData2("form_vo", vo);
        doAjaxPost('signAction.action?act=signDelPatient', {strJson: JSON.stringify(vo)}, function (data) {
            if (data.code == "800") {
                layer.msg(data.msg);
                setTimeout(function () {
                    goto();// 返回
                }, 1000);
            } else {
                layer.msg(data.msg);
            }
        });
    }
}

/**
 * 解约类型单选按钮点击事件处理
 */
function onTypevalue(type) {
    if (type == "1") {// 死亡解约
        if (patientjmda != "") { // 居民档案不为空（先调用家签接口工程，判断其在基卫是否已经是死亡状态）
            var dQvo = {};
            dQvo["orgId"] = orgid;
            dQvo["df_id"] = patientjmda;
            doAjaxPost('signAction.action?act=findDeathState', {strJson: JSON.stringify(dQvo)}, function (data) {
                var result = eval("(" + data.result + ")");
                if (!result) {
                    showMsg("调用的接口不通，请联系系统管理员！");
                    setTimeout(function () {
                        goto();// 返回
                    }, 2000);
                } else if (result && result.isdead == "0") {// 未死亡
                    var src = "";
                    if (HospAreaCode.substr(0, 4) == "3503") {// 莆田
                        src = "http://18.1.3.28:7001/sqyl/healthfile/dwellerfileAction.do?method=enterDwellerfileEdit&df_id=" + patientjmda;
                    } else if (HospAreaCode.substr(0, 4) == "3504") {// 三明
                        src = "http://10.128.191.8:7001/sqyl/healthfile/dwellerfileAction.do?method=enterDwellerfileEdit&df_id=" + patientjmda;
                    } else if (HospAreaCode.substr(0, 4) == "3501") {// 福州
                        src = "http://10.120.1.28:7001/sqyl/healthfile/dwellerfileAction.do?method=enterDwellerfileEdit&df_id=" + patientjmda;
                    } else if (HospAreaCode.substr(0, 4) == "3507") {// 南平
                        src = "http://10.120.9.61:7001/sqyl/healthfile/dwellerfileAction.do?method=enterDwellerfileEdit&df_id=" + patientjmda;
                    } else if (HospAreaCode.substr(0, 4) == "3505") {// 泉州
                        src = "http://10.120.5.131:7001/sqyl/healthfile/dwellerfileAction.do?method=enterDwellerfileEdit&df_id=" + patientjmda;
                    } else if (HospAreaCode.substr(0, 4) == "3506") {// 漳州
                        src = "http://10.120.6.29:7001/sqyl/healthfile/dwellerfileAction.do?method=enterDwellerfileEdit&df_id=" + patientjmda;
                    } else if (HospAreaCode.substr(0, 2) == "14") {// 山西
                        src = "http://10.10.10.60:7101/sqyl/healthfile/dwellerfileAction.do?method=enterDwellerfileEdit&df_id=" + patientjmda;
                    }
                    // 调基卫档案修改页面修改死亡状态
                    window.open(src, '基卫档案信息', 'left=0,top=0,width=' + (screen.availWidth - 10) + ',height=' + (screen.availHeight - 50) + ',scrollbars,resizable=yes,toolbar=no,location=no');
                    // 登记死亡信息后继续死亡解约操作
                    layer.open({
                        type: 1,
                        title: false,
                        closeBtn: false,
                        area: '350px;',// 弹框宽度
                        shade: 0.3,
                        btn: ['继续解约'],
                        btnAlign: 'c',
                        moveType: 1,
                        content: '<div style="padding: 50px; line-height: 22px; background-color: #393D49;' +
                        'color: #fff; font-weight: 100; text-align : center;font-size: 13px;">档案信息修改成功后请继续解约操作！</div>',
                        btn1: function () {
                            layer.closeAll();
                            onTypevalue(1);// 重新调用当前方法
                        }
                    });
                } else {
                    layer.confirm('该患者已登记为死亡，是否进行死亡解约？', {
                        btn: ['确定', '取消']
                    }, function () {
                        var dQvo = {};
                        dQvo["ptJmdah"] = patientjmda;
                        dQvo["signDieDate"] = result.ddate;
                        dQvo["signOperatorName"] = username;
                        doAjaxPost('signAction.action?act=signSurrender', {strJson: JSON.stringify(dQvo)}, function (data) {
                            if (data.msgCode == "800") {
                                showMsg("解约成功！");
                                setTimeout(function () {
                                    goto();// 返回
                                }, 1000);
                            } else {
                                showMsg(data.msg);
                            }
                        });
                    }, function () {
                        $("#signDelTypeTwo").click();
                    });
                }
            });
        } else {// 居民档案号为空
            var html =
                "<label class='layui-form-label'><b>* </b>死亡时间</label>" +
                "<div class='layui-input-inline' style='width: 200px;padding-left: 10px;' >" +
                "<input type='text' id='signDieDate' pofield='signDieDate' class='layui-input' style='width: 187px;'" +
                "onfocus=\"WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})\" placeholder='请选择死亡时间' " +
                "validator='{\"msg\":\"死亡时间不能为空！\"}' >" +
                "</div>";
            $("#swsj").html(html).show();
        }
    } else if (type == "2") {// 默认解约
        $("#swsj").html('').hide();
    }
}


