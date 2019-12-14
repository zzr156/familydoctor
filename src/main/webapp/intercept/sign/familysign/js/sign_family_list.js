var vo = {};
var qvo = {};
var vos = {};
var voexcel = [];
var table;
var laydate;
var numberCount = 0;

/*layui.use('laydate', function () {
    laydate = layui.laydate;
    laydate.render({
        elem: '#signDateStart'
    });
    laydate.render({
        elem: '#signDateEnd'
    });
});*/

/**
 * 页面加载完成后执行
 */
$(function () {
    // 查询团队
    doAjaxPost('signAction.action?act=findTeam', {id: orgid}, initBack);
    // 暂不要级联查询
    // changecity();
});

/**
 * 团队初始化
 */
function initBack(data) {
    $("#signHospId").val(orgid);
    $("#drHospName").val(orgid);
    if (data.map != null) {
        if (data.map.teamList != null) {
            $("#teamId").html("");
            var option = document.createElement('option');
            option.setAttribute("value", "");
            option.innerText = "--全部--";
            document.getElementById("teamId").appendChild(option);
            $.each(data.map.teamList, function (k, v) {
                dataUiCodeGroup("select", "teamId", v.teamName, v.id);
            });
        }
    }
    layui.use('table', function () {
        table = layui.table;
        table.on('tool(sign)', function (obj) {
            var ui = obj.data; // 获得当前行数据
            if (obj.event == 'modify') {// 修改
                forTeamModify(ui);
            } else if (obj.event == 'look') {// 查看
                findlook(ui);
            } else if (obj.event == 'print') {// 打印
                lookprotocol(ui);
            } else if (obj.event == 'changesign') {// 变更团队
                changesign(ui);
            }
        });
        findList();
    });
}

/**
 * 查询医生
 */
function addDr() {
    var options = $("#teamId option:selected");
    var value = $("#teamId").val();
    doAjaxPost('signAction.action?act=findMem', {id: value}, findMenBack);
}

/**
 * 医生初始化
 */
function findMenBack(data) {
    if (data != null) {
        if (data.rows != null) {
            $("#drId").html("");
            var option = document.createElement('option');
            option.setAttribute("value", "");
            option.innerText = "--全部--";
            document.getElementById("drId").appendChild(option);
            $.each(data.rows, function (k, v) {
                dataUiCodeGroup("select", "drId", v.memDrName, v.memDrId);
            });

            $("#signDrAssistantId").html("");
            var option = document.createElement('option');
            option.setAttribute("value", "");
            option.innerText = "--全部--";
            document.getElementById("signDrAssistantId").appendChild(option);
            $.each(data.rows, function (k, v) {
                dataUiCodeGroup("select", "signDrAssistantId", v.memDrName, v.memDrId);
            });
        }
    }
}

/**
 * 列表查询
 */
function findList() {
    qvo = uiToData("form_qvo", qvo);// 界面查询值绑定到变量
    if (HospAreaCode.substr(0, 4) == "3503") {
        ptInitTable();
    } else {
        var index = layer.load(1);
        table.render({
            height: '400',
            elem: '#signTabel',
            cols: [[
                {field: 'myIndex', title: '序号', width: 60, fixed: true},
                {field: 'signNum', title: '签约编号', width: 100, sort: true},
                {field: 'name', title: '姓名', width: 60, sort: true},
                {field: 'sex', title: '性别', width: 60, sort: true},
                {field: 'age', title: '年龄', width: 60},
                {field: 'patientIdno', title: '身份证', width: 150},
                {field: 'tel', title: '联系电话', width: 100},
                {field: 'patientAddress', title: '详细地址', width: 150},
                {field: 'signPersGroup', title: '服务类型', width: 100},
                {field: 'signsJjType', title: '人口经济性质', width: 100},
                {field: 'signlx', title: '医保类型', width: 80},
                {field: 'signDate', title: '签约时间', width: 90},
                {field: 'signState', title: '签约状态', width: 80},
                {field: 'signTeamName', title: '签约团队', width: 80},
                {field: 'signDrName', title: '签约医生', width: 80},
                {field: 'batchOperatorName', title: '签约操作人', width: 80},
                {field: 'signgwpay', title: '公卫补助', width: 80},
                {field: 'signybpay', title: '医保预支付', width: 80},
                {field: 'signczpay', title: '财政补贴', width: 80},
                {field: 'signzfpay', title: '自费', width: 80},
                {fixed: 'right', width: 280, align: 'center', toolbar: '#barRole'}
            ]],
            id: 'sign',
            url: 'signAction.action',
            where: {act: 'findSignXxWeb', strJson: JSON.stringify(qvo)},
            method: 'post',
            skin: 'row',// 表格风格
            even: true,
            page: true,// 是否显示分页
            limits: [5, 10, 25, 50, 100, 500, 1000, 3000, 5000, 10000, 30000, 50000],
            limit: 10,// 每页默认显示的数量
            done: function (res, curr, count) {
                vos = res.data;
                if (vos != null) {
                    numberCount = res.data.length;
                }
                layer.close(index);// 关闭遮罩
            }
        });
    }
}

/*var signlx = {
    // undefined:'',
    null: '',
    999999: "",
    1: "医保",
    2: "农合",
    3: "未参保"
};
var signState = {
    // undefined:'',
    // null:'',
    0: '预签约',
    1: '待签约',
    2: '已签约',
    3: '解约中',
    4: '已解约',
    5: '改签解约中',
    6: '改签申请中',
    7: '已退约',
    8: '拒签',
    9: '删除'
};
var signsJjType = {
    // null:'',
    999999: "",
    0: '',
    1: '一般人口',
    2: '建档立卡贫困人口',
    3: '低保户',
    4: '特困户（五保户）',
    5: '计生特殊家庭'
};
var sex = {
    null: '',
    999999: "",
    1: '男',
    2: '女'
};*/
/**
 * 导出当前页
 */

/*function exportCurrent() {
    var option = {};
    option.fileName = '签约列表';
    $.each(vos, function (k, v) {
        vos[k].signlx = signlx[vos[k].signlx];
        vos[k].signState = signState[vos[k].signState];
    });
    option.datas = [
        {
            sheetData: vos,
            sheetName: 'sheet',
            sheetFilter: ['myIndex', 'signNum', 'name', 'age', 'patientIdno', 'tel', 'signsJjType', 'signlx', 'signDate', 'signState', 'signczpay', 'signzfpay', 'signDrName'],
            sheetHeader: ['序号', '签约编号', '姓名', '年龄', '身份证', '联系电话', '人口经济性质', '医保类型', '签约时间', '签约状态', '财政补贴', '自费', '签约医生']
        }
    ];
    var toExcel = new ExportJsonExcel(option);
    toExcel.saveExcel();
}*/

function forTeamModify(ui) {
    var id = ui.id;
    var orgid = ui.teamHospId;
    var url = "sign_modify.jsp?id=" + ui.id;
    defualtHref(url);
}

function findlook(ui) {
    if (ui.id != null && ui.id != "") {
        window.location.href = "sign_look.jsp?id=" + ui.id + "&type=" + true;
    }
}

function lookprotocol(ui) {
    if (necs(ui.patientId) && necs(ui.teamId)) {
        window.location.href = "sign_protocol.jsp?teamId=" + ui.teamId + "&patientId=" + ui.patientId + "&type=" + true;
    }
}

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
 * 市级联
 */
function changecity() {
    vo["upId"] = "350000000000";
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
function changeCommittee() {
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
        }, function (data) {
            layer.msg("级联初始化异常，请联系管理员！");
        });
    }
}


//导出当前页
function exportCurrent(num) {
    qvo = uiToData("form_qvo", qvo);// 界面查询值绑定到变量
    window.open("signAction.action?act=findSignXxWebExcel&typeNum=" + num + "&strJson=" + JSON.stringify(qvo) + "&numberCount=" + numberCount);
    /*doAjaxPost('signAction.action?act=findSignXxWebEXCEL', {strJson: JSON.stringify(qvo)}, function (data) {
        if (data.data != null) {
            var option = {};
            option.fileName = '签约列表';
            $.each(data.data, function (k, v) {
                var vv = v;
                vv.signlx = signlx[v.signlx];
                vv.signState = signState[v.signState];
                vv.signsJjType = signsJjType[v.signsJjType];
                vv.myIndex = k + 1;
                voexcel.push(vv);
            });
            option.datas = [
                {
                    sheetData: voexcel,
                    sheetName: 'sheet',
                    sheetFilter: ['myIndex', 'signNum', 'name', 'age', 'patientIdno', 'tel', 'patientAddress', 'signsJjType', 'signPersGroup', 'signlx', 'signDate', 'signState', 'signczpay', 'signzfpay', 'signDrName'],
                    sheetHeader: ['序号', '签约编号', '姓名', '年龄', '身份证', '联系电话', '详细地址', '人口经济性质', '服务类型', '医保类型', '签约时间', '签约状态', '财政补贴', '自费', '签约医生']
                }
            ];
            var toExcel = new ExportJsonExcel(option);
            toExcel.saveExcel();
        }
    });*/
}

function changesign(e) {
    /*layer.open({
        type: 1,
        title: false,// 不显示标题栏
        closeBtn: false,
        area: ['600px', '500px'],
        shade: 0.8,
        id: 'LAY_layuipro',// 设定一个id，防止重复弹出
        resize: false,
        btn: ['确定', '取消'],// '发票打印'
        btnAlign: 'c',
        moveType: 1,// 拖拽模式，0或者1
        content: '<div style="padding: 50px; line-height: 300px; background-color: #f2f2f2; color: #000; font-weight: 300;"></div>',
        yes: function (index, layero) {
            // window.location.href= "sign_look.jsp?id="+data.vo.id+"&type="+true+"&typeadd="+true+"&addtpye="+addtpye;
        }/!*,
        btn2: function () {
            window.location.href = "sign_protocol.jsp?teamId=" + data.vo.signTeamId + "&PatientId=" + data.vo.signPatientId;
        }*!/
    });*/
    if (e.signDrId == drid) {
        layer.open({
            type: 2,
            area: ['700px', '450px'],
            fixed: false, //不固定
            maxmin: true,
            content: "sign_change.jsp?id=" + e.id + "&signDrId=" + e.signDrId + "&patientId=" + e.patientId + "&teamId=" + e.teamId + "&drName=" + e.signDrName
        });
    } else {
        layer.msg("该居民不属于您的团队，您无权限变更！");
    }
}