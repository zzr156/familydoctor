/**
 * 签约医保登记核心js
 * Created by WangCheng on 2019/3/6.
 */

var vo = {};
var qvo = {};
var form;
var table;
var laydate;
var numberCount = 0;

/**
 * 页面初始化
 */
$(function () {
    $("#signHospId").val(orgid);

    layui.use(["table", "form", "laydate"], function () {
        table = layui.table;
        form = layui.form;
        laydate = layui.laydate;

        table.on('tool(sign)', function (obj) {
            var ui = obj.data; //获得当前行数据
            if (obj.event == 'signRegister') { //进入修改
                signRegister(ui);
            }
        });
    });
});

/**
 * 读卡
 */
function readCard() {
    //调用卡包获取社保卡信息
    var cardObj = publicReadCard();

    var ptidno = cardObj.ic_ybid;//身份证
    var ptsccno = cardObj.ic_icno;//医保卡号
    var ptname = cardObj.ic_name;//姓名

    if (ptidno != "" && ptsccno != "") {
        qvo["patientIdno"] = ptidno;//身份证
        qvo["patientCard"] = ptsccno;//医保卡号
        qvo["patientName"] = ptname;//姓名
        qvo["signHospId"] = $("#signHospId").val();
        qvo["signRegister"] = "1";

        var index = layer.load(1);
        table.render({
            height: '400'
            , elem: '#signTabel'
            , cols: [[
                {field: 'myIndex', title: '序号', width: 40,fixed: true}
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
                , {fixed: 'right', width: 100, align: 'center', toolbar: '#barRole'}
            ]]
            , id: 'sign'
            , url: 'signAction.action'
            , where: {act: 'findSignXxWeb', strJson: JSON.stringify(qvo)}
            , method: 'post'
            , skin: 'row' //表格风格
            , even: true
            , page: true //是否显示分页
            , limits: [5, 10, 25, 50, 100, 500, 1000, 3000, 5000, 10000, 30000, 50000]
            , limit: 10 //每页默认显示的数量
            , done: function (res, curr, count) {
                if (res.data != null) {
                    vos = res.data;
                    numberCount = res.data.length;
                }
            }
        });
        //遮罩关闭
        layer.close(index);
    }else {
        layer.msg("未检测到卡，请重新插卡！！");
        return;
    }
}

// //查询
// function findList() {
//     qvo = uiToData("form_qvo", qvo);//界面查询值绑定到变量
//     var index = layer.load(1);
//     table.render({
//         height: '400'
//         , elem: '#signTabel'
//         , cols: [[
//             {title: '选择', type: 'checkbox', width: '50', fixed: "left"}
//             , {field: 'myIndex', title: '序号', width: 60, fixed: true}
//             , {field: 'signNum', title: '签约编号', width: 140, sort: true}
//             , {field: 'name', title: '姓名', width: 60, sort: true}
//             , {field: 'sex', title: '性别', width: 60, sort: true}
//             , {field: 'age', title: '年龄', width: 60, templet: '<div>{{  GetAge(d.patientIdno)}}</div>'}
//             , {field: 'patientIdno', title: '身份证', width: 150}
//             , {field: 'tel', title: '联系电话', width: 100}
//             , {field: 'patientAddress', title: '详细地址', width: 150}
//             , {field: 'signPersGroup', title: '服务类型', width: 100}
//             , {field: 'signsJjType', title: '人口经济性质', width: 100}
//             , {field: 'signlx', title: '医保类型', width: 80}
//             , {field: 'signDate', title: '签约时间', width: 90}
//             , {field: 'signState', title: '签约状态', width: 120}
//             , {field: 'signTeamName', title: '签约团队', width: 80}
//             , {field: 'signDrName', title: '签约医生', width: 80}
//             , {field: 'batchOperatorName', title: '签约操作人', width: 80}
//             , {field: 'signgwpay', title: '公卫补助', width: 80}
//             , {field: 'signybpay', title: '医保预支付', width: 80}
//             , {field: 'signczpay', title: '财政补贴', width: 80}
//             , {field: 'signzfpay', title: '自费', width: 80}
//             , {field: 'upHpis', title: '签约来源', width: 80}
//         ]]
//         , id: 'sign'
//         , url: 'signAction.action'
//         , where: {act: 'findSignXxWeb', strJson: JSON.stringify(qvo)}
//         , method: 'post'
//         , skin: 'row' //表格风格
//         , even: true
//         , page: true //是否显示分页
//         , limits: [5, 10, 25, 50, 100, 500, 1000, 3000, 5000, 10000, 30000, 50000]
//         , limit: 10 //每页默认显示的数量
//         , done: function (res, curr, count) {
//             if (res.data != null) {
//                 vos = res.data;
//                 numberCount = res.data.length;
//             }
//         }
//     });
//     //遮罩关闭
//     layer.close(index);
// }

/**
 * 签约
 */
function signRegister(obj) {
    debugger
    qvo = uiToData("form_qvo",qvo);
    qvo["id"] = obj.id;
    qvo["name"] = obj.name;//姓名
    qvo["patientIdno"] = obj.patientIdno;//身份证号
    qvo["patientCard"] = obj.patientCard;//社保卡号
    qvo["packageId"] = obj.packageId;//服务包编号
    qvo["signDrName"] = obj.signDrName;//签约医生姓名
    qvo["signToDate"] = obj.signToDate;
    qvo["bke241"] = "1"; //1 窗口 2移动端
    doAjaxPost("signAction.action?act=signRegister",{strJson: JSON.stringify(qvo)}, function (data) {
        // if(data.code == "800"){
        //     // var result = eval('(' + data + ')');
        //     // alert(result);
        //     layer.msg(result.vo.cause);
        // }else {
        //     layer.msg(data.msg);
        // }
        layer.msg(data.msg);
    });
}

/**
 * 重置
 */
function reset() {
    $("#patientCard").val("");
    $("#patientName").val("");
    $("#patientGender").val("");
    $("#patientIdno").val("");
    $("#patientBirthday").val("");
    $("#signlx").val("");
    $("#signState").val("");
}