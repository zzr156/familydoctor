/**
 * 定义全局变量
 */
var table;

/**
 * 页面加载完成后执行
 */
$(function () {

    initFunction();

});

/**
 * 初始化表格
 */
function initFunction() {
    var signId = getQueryString("signId");

    var param = {
        'id': signId
    };

    layui.use('table', function () {
        table = layui.table;

        table.render({
            elem: '#readFileLogTable',
            cols: [[
                {title: '序号', width: 60, templet: '#indexTpl'},
                {field: 'patientName', title: '居民姓名', width: 100},
                {field: 'patientDfid', title: '居民档案号', width: 200},
                {field: 'readUserName', title: '调阅人姓名', width: 100},
                {field: 'hsCreateDate', title: '调阅日期', width: 150},
                {field: 'readWay', title: '调阅入口', width: 150, templet: '<div>{{DescReadWay(d.readWay)}}</div>'}
            ]],
            cellMinWidth: 30,
            id: 'readFileLog',
            url: 'signAction.action',
            where: {
                act: 'findReadFileLog',
                strJson: JSON.stringify(param)
            },
            method: 'post',
            skin: 'row',
            even: true,
            page: false, // 是否显示分页
            done: function (res, curr, count) {
            }
        });
    });
}

/**
 * 签约入口代码转描述
 */
function DescReadWay(readWay) {
    if (readWay == "1") {
        return "居民签约信息";
    } else {
        return "";
    }
}