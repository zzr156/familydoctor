var vo = {};
var qvo = {};
var vos = {};
var voexcel = [];
var table;
var laydate;
var numberCount = 0;
var drRoleResult = [];

/**
 * WangCheng
 */

$(function () {
    $("#signHospId").val(orgid);
    $("#drHospName").val(orgid);
    layui.use('table', function () {
        table = layui.table;

        table.on('tool(sign)', function (obj) {
            var ui = obj.data; //获得当前行数据
            if (obj.event == 'look') { //查看
                findlook(ui);
            }else if(obj.event = 'print'){//查看协议
                lookprotocol(ui);
            }
        });
    });

    //判断drRole，包含1的话是可查看全身签约数据，2全市，3全区，49医院,add by WangCheng
    if(drRole != null && drRole != ""){
        drRoleResult = drRole.split(";");
        if($.inArray("1",drRoleResult) > -1){//包含1(省)
            initCity();//初始化市级
        }else if($.inArray("2",drRoleResult) > -1){//包含2（市）
            initArea();//初始化区县
        }else if($.inArray("3",drRoleResult) > -1){//包含3（区县）
            initHospital();//初始化社区
        }else if($.inArray("4",drRoleResult) > -1 || $.inArray("9",drRoleResult) > -1){//包含4或9（医院）
            initAllLevel();//初始化市级、区县、社区
        }
    }

});

//查询
function findList() {
    qvo = uiToData("form_qvo", qvo);//界面查询值绑定到变量
    if (HospAreaCode.substr(0, 4) == "3503") {
        $("#pts_").show();
        $("#pt_").show();
        ptInitTable();
    } else {

        table.render({
            height: '400'
            , elem: '#signTabel'
            , cols: [[
                {field: 'myIndex', title: '序号', width: 60, fixed: true}
                , {field: 'signNum', title: '签约编号', width: 140, sort: true}
                , {field: 'name', title: '姓名', width: 60, sort: true}
                , {field: 'sex', title: '性别', width: 60, sort: true}
                , {field: 'age', title: '年龄', width: 60, templet: '<div>{{  GetAge(d.patientIdno)}}</div>'}
                , {field: 'patientIdno', title: '身份证', width: 150}
                , {field: 'tel', title: '联系电话', width: 100}
                , {field: 'patientAddress', title: '详细地址', width: 150}
                , {field: 'signPersGroup', title: '服务类型', width: 100}
                , {field: 'signsJjType', title: '人口经济性质', width: 100}
                , {field: 'signlx', title: '医保类型', width: 80}
                , {field: 'signDate', title: '签约时间', width: 90}
                , {field: 'signState', title: '签约状态', width: 120}
                , {field: 'signTeamName', title: '签约团队', width: 80}
                , {field: 'signDrName', title: '签约医生', width: 80}
                , {field: 'batchOperatorName', title: '签约操作人', width: 80}
                , {field: 'signgwpay', title: '公卫补助', width: 80}
                , {field: 'signybpay', title: '医保预支付', width: 80}
                , {field: 'signczpay', title: '财政补贴', width: 80}
                , {field: 'signzfpay', title: '自费', width: 80}
                , {field: 'upHpis', title: '签约来源', width: 80}
                , {fixed: 'right', width: 200, align: 'center', toolbar: '#barRole'}
            ]]
            , id: 'sign'
            , url: 'signAction.action'
            , where: {act: 'findAllSignForm', strJson: JSON.stringify(qvo)}
            , method: 'post'
            , skin: 'row' //表格风格
            // ,size: 'sm'
            , even: true
            , page: true //是否显示分页
            , limits: [5, 10, 25, 50, 100, 500, 1000, 3000, 5000, 10000, 30000, 50000]
            , limit: 10 //每页默认显示的数量
            , done: function (res, curr, count) {
                    vos = res.data;
                    if(vos != null){
                        numberCount = res.data.length;
                    }
            }
        });
    }
}

//莆田表格初始化
function ptInitTable() {
    var index = layer.load(1);
    table.render({
        height: '400'
        , elem: '#signTabel'
        , cols: [[
            {field: 'myIndex', title: '序号', width: 60, fixed: true}
            , {field: 'signNum', title: '签约编号', width: 100, sort: true}
            , {field: 'name', title: '姓名', width: 60, sort: true}
            , {field: 'sex', title: '性别', width: 60, sort: true}
            , {field: 'age', title: '年龄', width: 60, templet: '<div>{{  GetAge(d.patientIdno)}}</div>'}
            , {field: 'patientIdno', title: '身份证', width: 150}
            , {field: 'tel', title: '联系电话', width: 100}
            , {field: 'patientAddress', title: '详细地址', width: 150}
            , {field: 'signPersGroup', title: '服务类型', width: 100}
            , {field: 'signsJjType', title: '人口经济性质', width: 100}
            , {field: 'signlx', title: '医保类型', width: 80}
            , {field: 'signDate', title: '签约时间', width: 90}
            , {field: '', title: '签约状态', width: 90, templet: '<div>已签约</div>'}
            , {field: 'signState', title: '医保交互', width: 80}
            , {field: 'signTeamName', title: '签约团队', width: 80}
            , {field: 'signDrName', title: '签约医生', width: 80}
            , {field: 'batchOperatorName', title: '签约操作人', width: 80}
            , {field: 'signgwpay', title: '公卫补助', width: 80}
            , {field: 'signybpay', title: '医保预支付', width: 80}
            , {field: 'signczpay', title: '财政补贴', width: 80}
            , {field: 'signzfpay', title: '自费', width: 80}
            , {field: 'upHpis', title: '签约来源', width: 80}
            , {fixed: 'right', width: 200, align: 'center', toolbar: '#barRole'}
        ]]
        , id: 'sign'
        , url: 'signAction.action'
        , where: {act: 'findAllSignForm', strJson: JSON.stringify(qvo)}
        , method: 'post'
        , skin: 'row' //表格风格
        // ,size: 'sm'
        , even: true
        , page: true //是否显示分页
        , limits: [5, 10, 25, 50, 100, 500, 1000, 3000, 5000, 10000, 30000, 50000]
        , limit: 10 //每页默认显示的数量
        , done: function (res, curr, count) {
            vos = res.data;
            numberCount = res.data.length;
            $.each(res.data, function (k, v) {
                if (v.signState == "预签约") {
                    $("td[data-field='signState'] :eq(" + k + ")").text("未交互");
                } else if (v.signState == "已签约") {
                    $("td[data-field='signState'] :eq(" + k + ")").text("已交互");
                }
            })
            //遮罩关闭
            layer.close(index);
        }
    });
}

//查询
function findAllList() {

    //判断级别
    if($("#hospitalLevel option:selected").val() != ""){//查看的是社区级数据
        $("#findLevel").val("4");
        $("#signAreaCode").val($("#hospitalLevel option:selected").val());
    }else{
        if($("#areaLevel option:selected").val() != ""){//查看的是区县级数据
            $("#findLevel").val("3");
            $("#signAreaCode").val($("#areaLevel option:selected").val().substring(0,6));
        }else {
            if($("#cityLevel option:selected").val() != ""){//查看的是市级数据
                $("#findLevel").val("2");
                $("#signAreaCode").val($("#cityLevel option:selected").val().substring(0,4));
            }else {
                $("#findLevel").val("1");
                $("#signAreaCode").val(HospAreaCode.substring(0,2));
            }
        }
    }

    qvo = uiToData("form_qvo", qvo);//界面查询值绑定到变量
    if (HospAreaCode.substr(0, 4) == "3503") {
        $("#pts_").show();
        $("#pt_").show();
        ptInitTable();
    } else {
        var index = layer.load(1);
        table.render({
            height: '400'
            , elem: '#signTabel'
            , cols: [[
                {field: 'myIndex', title: '序号', width: 60, fixed: true}
                , {field: 'signNum', title: '签约编号', width: 140, sort: true}
                , {field: 'name', title: '姓名', width: 60, sort: true}
                , {field: 'sex', title: '性别', width: 60, sort: true}
                , {field: 'age', title: '年龄', width: 60, templet: '<div>{{  GetAge(d.patientIdno)}}</div>'}
                , {field: 'patientIdno', title: '身份证', width: 150}
                , {field: 'tel', title: '联系电话', width: 100}
                , {field: 'patientAddress', title: '详细地址', width: 150}
                , {field: 'signPersGroup', title: '服务类型', width: 100}
                , {field: 'signsJjType', title: '人口经济性质', width: 100}
                , {field: 'signlx', title: '医保类型', width: 80}
                , {field: 'signDate', title: '签约时间', width: 90}
                , {field: 'signState', title: '签约状态', width: 120}
                , {field: 'signTeamName', title: '签约团队', width: 80}
                , {field: 'signDrName', title: '签约医生', width: 80}
                , {field: 'batchOperatorName', title: '签约操作人', width: 80}
                , {field: 'signgwpay', title: '公卫补助', width: 80}
                , {field: 'signybpay', title: '医保预支付', width: 80}
                , {field: 'signczpay', title: '财政补贴', width: 80}
                , {field: 'signzfpay', title: '自费', width: 80}
                , {field: 'upHpis', title: '签约来源', width: 80}
                , {fixed: 'right', width: 200, align: 'center', toolbar: '#barRole'}
            ]]
            , id: 'sign'
            , url: 'signAction.action'
            , where: {act: 'findAllSignForm', strJson: JSON.stringify(qvo)}
            , method: 'post'
            , skin: 'row' //表格风格
            , even: true
            , page: true //是否显示分页
            , limits: [5, 10, 25, 50, 100, 500, 1000, 3000, 5000, 10000, 30000, 50000]
            , limit: 10 //每页默认显示的数量
            , done: function (res, curr, count) {
                if (res.data != null) {
                    vos = res.data;
                    if(vos != null){
                        numberCount = res.data.length;
                    }
                    //遮罩关闭
                    layer.close(index);
                }
            }
        });

    }

}

/**
 * 查看
 * WangCheng
 * @param ui
 */
function findlook(ui) {
    if (ui.id != null && ui.id != "") {
        window.location.href = "sign_look.jsp?id=" +
            "" + ui.id + "&type=" + true;
    }
}

function lookprotocol(ui) {
    if (necs(ui.patientId) && necs(ui.teamId)) {
        window.location.href = "sign_protocol.jsp?teamId=" + ui.teamId + "&patientId=" + ui.patientId + "&drId=" + ui.signDrId + "&type=" + true;
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
 * 初始化市级
 * WangCheng
 */
function initCity() {
    var hospStr = HospAreaCode.substring(0,2);
    vo["upId"] = hospStr + "0000000000";
    doAjaxPost('manageCounAction.action?act=appAddressResult', {strJson: JSON.stringify(vo)}, function (data) {
        $("#cityLevel").html("");

        var option1 = document.createElement('option');
        option1.setAttribute("value", "");
        option1.innerText = "--请选择--";
        document.getElementById("cityLevel").appendChild(option1);
        if (data != null) {
            $.each(data.rows, function (k, v) {
                if (v.state != "0") {
                    var option = document.createElement('option');
                    option.setAttribute("value", v.id);
                    option.innerText = v.name;
                    document.getElementById("cityLevel").appendChild(option);
                }
            });
            $("#findLevel").val("1");
            $("#signAreaCode").val(hospStr);
            // findList();
        }
    }, function (data) {
        layer.msg("级联初始化异常，请联系管理员！");
    })
}

/**
 * 初始化区县
 * WangCheng
 */
function initArea(){
    var hospStr = HospAreaCode.substring(0,4);
    vo["upId"] = hospStr + "00000000";
    doAjaxPost('manageCounAction.action?act=appAddressResult', {strJson: JSON.stringify(vo)}, function (data) {
        $("#areaLevel").html("");

        var option1 = document.createElement('option');
        option1.setAttribute("value", "");
        option1.innerText = "--请选择--";
        document.getElementById("areaLevel").appendChild(option1);
        if (data != null) {
            $.each(data.rows, function (k, v) {
                if (v.state != "0") {
                    var option = document.createElement('option');
                    option.setAttribute("value", v.id);
                    option.innerText = v.name;
                    document.getElementById("areaLevel").appendChild(option);
                }
            });
            $("#cityLevel").append("<option value=" + data.vo.id + " selected>" + data.vo.areaSname + "</option>");//直接在市级赋值
            $("#cityLevel").attr("disabled","disabled");
            $("#findLevel").val("2");
            $("#signAreaCode").val(hospStr);
            // findList();
        }
    }, function (data) {
        layer.msg("级联初始化异常，请联系管理员！");
    })
}

/**
 * 初始化社区
 * Wangcheng
 */
function initHospital(){
    var hospStr = HospAreaCode.substring(0,6);
    vo["upId"] = hospStr + "000000";
    doAjaxPost('manageCounAction.action?act=initHospital', {strJson: JSON.stringify(vo)}, function (data) {
        $("#hospitalLevel").html("");

        var option1 = document.createElement('option');
        option1.setAttribute("value", "");
        option1.innerText = "--请选择--";
        document.getElementById("hospitalLevel").appendChild(option1);
        if (data != null) {
            $.each(data.rows, function (k, v) {
                if (v.state != "0") {
                    var option = document.createElement('option');
                    option.setAttribute("value", v.id);
                    option.innerText = v.name;
                    document.getElementById("hospitalLevel").appendChild(option);
                }
            });
            $("#cityLevel").append("<option value=" + data.map.upHospNameId + " selected>" + data.map.upHospName + "</option>");//直接在市级赋值
            $("#areaLevel").append("<option value=" + data.map.hospNameId + " selected>" + data.map.hospName + "</option>");//直接在区县级
            $("#cityLevel").attr("disabled","disabled");
            $("#areaLevel").attr("disabled","disabled");
            $("#findLevel").val("3");
            $("#signAreaCode").val(hospStr);
            // findList();
        }
    }, function (data) {
        layer.msg("级联初始化异常，请联系管理员！");
    })
}

/**
 * 初始化市级、区县、社区
 * WangCheng
 */
function initAllLevel(){
    vo["areaCode"] = HospAreaCode;
    doAjaxPost('manageCounAction.action?act=initAllLevel', {strJson: JSON.stringify(vo)}, function (data) {
        if (data != null) {
            $("#cityLevel").html("");
            $("#areaLevel").html("");
            $("#hospitalLevel").html("");
            if(data.map != null){
                $("#cityLevel").append("<option value=" + data.map.cityNameId + " selected>" + data.map.cityName + "</option>");//直接在市级赋值
                $("#areaLevel").append("<option value=" + data.map.areaNameId + " selected>" + data.map.areaName + "</option>");//直接在区县级赋值
                $("#hospitalLevel").append("<option value=" + data.map.communityNameId + " selected>" + data.map.communityName + "</option>");//直接在社区级赋值
                $("#cityLevel").attr("disabled","disabled");
                $("#areaLevel").attr("disabled","disabled");
                $("#hospitalLevel").attr("disabled","disabled");
                $("#findLevel").val("4");
                $("#signAreaCode").val(HospAreaCode);
                // findList();
            }
        }
    }, function (data) {
        layer.msg("级联初始化异常，请联系管理员！");
    });
}

/**
 * 改变市级
 * Wangcheng
 */
function changeCityLevel() {
    if ($("#cityLevel").val() != null && $("#cityLevel").val() != "") {
        vo["upId"] = $("#cityLevel").val();
        doAjaxPost('manageCounAction.action?act=appAddressResult', {strJson: JSON.stringify(vo)}, function (data) {
            $("#areaLevel").html("");

            var option1 = document.createElement('option');
            option1.setAttribute("value", "");
            option1.innerText = "--请选择--";
            document.getElementById("areaLevel").appendChild(option1);
            if (data != null) {
                $.each(data.rows, function (k, v) {
                    if (v.state != "0") {
                        var option = document.createElement('option');
                        option.setAttribute("value", v.id);
                        option.innerText = v.name;
                        document.getElementById("areaLevel").appendChild(option);
                    }
                });
            }
        }, function (data) {
            layer.msg("级联初始化异常，请联系管理员！");
        })
    }
}

/**
 * 改变区县
 * WangCheng
 */
function changeAreaLevel() {
    if ($("#areaLevel").val() != null && $("#areaLevel").val() != "") {
        vo["upId"] = $("#areaLevel").val();
        doAjaxPost('manageCounAction.action?act=initHospital', {strJson: JSON.stringify(vo)}, function (data) {
            $("#hospitalLevel").html("");

            var option1 = document.createElement('option');
            option1.setAttribute("value", "");
            option1.innerText = "--请选择--";
            document.getElementById("hospitalLevel").appendChild(option1);
            if (data != null) {
                $.each(data.rows, function (k, v) {
                    if (v.state != "0") {
                        var option = document.createElement('option');
                        option.setAttribute("value", v.id);
                        option.innerText = v.name;
                        document.getElementById("hospitalLevel").appendChild(option);
                    }
                });
            }
        }, function (data) {
            layer.msg("级联初始化异常，请联系管理员！");
        })
    }

}

//导出当前页
function exportCurrent(num) {
    qvo = uiToData("form_qvo", qvo);//界面查询值绑定到变量
    //防止中文乱码
    var patientAddress = $("#patientAddress").val();
    if (patientAddress != null && patientAddress != "") {
        var add = encodeURI(encodeURI(patientAddress));
        qvo["patientAddress"] = add;
    }
    var batchOperatorName = $("#batchOperatorName").val();
    if (batchOperatorName != null && batchOperatorName != "") {
        var czrname = encodeURI(encodeURI(batchOperatorName));
        qvo["batchOperatorName"] = czrname;
    }
    var patientName = $("#patientName").val();
    if (patientName != null && patientName != "") {
        var ptname = encodeURI(encodeURI(patientName));
        qvo["patientName"] = ptname;
    }
    window.open("signAction.action?act=findSignXxWebExcel&typeNum=" + num + "&strJson=" + JSON.stringify(qvo) + "&numberCount=" + numberCount);
}