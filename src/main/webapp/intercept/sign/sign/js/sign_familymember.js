/**
 * Created by lenovo on 2018/1/25.
 */
var vo = {};
var table;
var patientidno = getQueryString("patientidno");
var patientjmda = getQueryString("patientjmda");

/**
 * 页面加载完成后执行
 */
$(function () {
    layui.use('table', function () {
        table = layui.table;
    });
    familylook();
});

/**
 * 查询家庭成员
 */
function familylook() {
    vo["patientidno"] = patientidno;
    vo["patientjmda"] = patientjmda;
    vo["hospAreaCode"] = HospAreaCode;
    if (isNotBlank(patientjmda)) {
        var index = layer.load(1);// 打开遮罩
        table.render({
            // height:'400'
            elem: '#signTabel',
            cols: [[
                {field: 'myIndex', title: '序号', width: 60, fixed: true},
                {field: 'patientname', title: '名称', width: 80, sort: true},
                {field: 'patientidno', title: '身份证', width: 150, sort: true},
                {field: 'patientjmda', title: '居民档案号', width: 150, sort: true},
                {field: 'patientrelationship', title: '与户主关系', width: 80},
                {field: 'patientstate', title: '是否签约', width: 60}
            ]],
            id: 'sign',
            url: 'signAction.action',
            where: {act: 'familymember', strJson: JSON.stringify(vo)},
            method: 'post',
            skin: 'row',// 表格风格
            even: true,
            page: true,// 是否显示分页
            limits: [5, 10, 25, 50, 100, 500, 1000, 3000, 5000, 10000, 30000, 50000],
            limit: 10, //每页默认显示的数量
            done: function (res, curr, count) {
                vos = res.data;
                layer.close(index);// 关闭遮罩
            }
        });
    } else {
        layer.msg("居民档案不能为空！");
    }
}
