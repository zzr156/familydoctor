var table;

$(function() {
	initFunction();
})
function initFunction() {
	var idcardno = getQueryString("idcardno");
	var signStartTime = getQueryString("signStartTime");
	var signEndTime = getQueryString("signEndTime");
    var areaCode = getQueryString("areaCode");

	var param = {
		'idcardno' : idcardno,
		'signStartTime' : signStartTime,
		'signEndTime' : signEndTime,
		'areaCode' : areaCode
	};

	layui.use('table', function() {
		table = layui.table;

		table.render({
			elem : '#visitRecordsTable',
			cols : [ [ {
				templet : '#indexTpl',
				title : '序号',
				width : 60
			}, {
				field : 'orgname',
				title : '机构名称',
				width : 150
			}, {
				field : 'czxw',
				title : '操作行为',
				width : 100
			}, {
				field : 'ksbh',
				title : '科室编号',
				width : 150
			}, {
				field : 'ksmc',
				title : '科室名称',
				width : 150
			}, {
				field : 'sfzh',
				title : '身份证号',
				width : 150
			}, {
				field : 'ysbh',
				title : '医生编号',
				width : 150
			}, {
				field : 'ysxm',
				title : '医生姓名',
				width : 150
			}, {
				field : 'yxsksj',
				title : '有效刷卡时间',
				width : 150
			} ] ],
			cellMinWidth : 30,
			id : 'sign',
			url : 'assessmentReadJwAction.action',
			where : {
				act : 'findVisitRecordsDetail',
				strJson : JSON.stringify(param)
			},
			method : 'post',
			skin : 'row',
			even : true,
			page : false, // 是否显示分页
			done : function(res, curr, count) {
			}
		});
	});
}