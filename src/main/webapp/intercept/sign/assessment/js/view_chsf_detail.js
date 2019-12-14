var form;

$(function() {
    initFunction();
})
function initFunction() {
    var reg = new RegExp('(^|&)' + 'strJson' + '=([^&]*)(&|$)', 'i');
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        var strJson = decodeURIComponent(r[2]);
        var jsonObj = JSON.parse(strJson);
        dataToUi2(jsonObj, "view_chsf_detail");

        layui.use('form', function() {
            form = layui.form;
            form.render(); // 渲染全部
            form.render('select');
            form.render('checkbox');
        });
    }

}
