var form;

$(function() {
	initFunction();
})
function initFunction() {
    var reg = new RegExp('(^|&)' + 'strJson' + '=([^&]*)(&|$)', 'i');
    var r = window.location.search.substr(1).match(reg);
	
    if (r != null) {
    	var strJson = decodeURIComponent(r[2]);
        var jsoNnObj = JSON.parse(strJson);
        dataToUi2(JSON.parse(strJson), "chronicDiseaseFollowUpRecordsForm2");
        var yyqkList = jsoNnObj.yyqkList;


        layui.use('table', function() {
            var table = layui.table;
            //展示已知数据
            table.render({
                elem : '#yyqk',
                cols : [ [ {
                    field : 'ywmc',
                    title : '用药名称',
                    width : 200
                }, {
                    field : 'ywyl',
                    title : '用量：每次剂量     mg',
                    width : 150
                }, {
                    field : 'ywyf',
                    title : '用法：每日（月）   次',
                    width : 150
                } ] ],
                data : yyqkList,
                even : true,
                done : function(res, page, count) {
                    //分类显示中文名称
                    $("[data-field='ywyf']").children().each(function() {
                        if ($(this).text() == '1') {
                            $(this).text("qod")
                        } else if ($(this).text() == '2') {
                            $(this).text("prn")
                        } else if ($(this).text() == '3') {
                            $(this).text("qn")
                        } else if ($(this).text() == '4') {
                            $(this).text("qd")
                        } else if ($(this).text() == '5') {
                            $(this).text("bid")
                        } else if ($(this).text() == '6') {
                            $(this).text("tid")
                        } else if ($(this).text() == '7') {
                            $(this).text("qid")
                        }
                    })
                }
            });
        });


        layui.use('form', function() {
        	form = layui.form;
        	form.render(); // 渲染全部
        	form.render('select');
        	form.render('checkbox');
        });
    }
}
