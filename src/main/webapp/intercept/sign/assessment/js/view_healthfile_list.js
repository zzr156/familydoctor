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

		table.on('tool(healthfileTable)', function(obj) {
			var data = obj.data; // 获得当前行数据
			if (obj.event == 'look') {
				layer.open({
					title : "体检表详情",
					type : 2,
					maxmin : true,
					content : familyUrl + "/intercept/sign/assessment/view_healthfile_detail.jsp?strJson=" + encodeURI(JSON.stringify(data)),
					area : [ "60%", "90%" ],
					success : function(layero, index) {
					},
					cancel : function(layero, index) {
					}
				});
			}
		});
		table.render({
			elem : '#healthfileTable',
			cols : [ [ {
				templet : '#indexTpl',
				title : '序号',
				width : 60
			}, {
				field : 'name',
				title : '姓名',
				width : 100
			}, {
				field : 'sex',
				title : '性别',
				width : 60
			}, {
				field : 'birthday',
				title : '出生日期',
				width : 100
			}/*, {
				field : 'jname',
				title : '居委会',
				width : 200
			}*/, {
				field : 'adress_rural',
				title : '常住地址：乡（镇、街道办事处）',
				width : 200
			}, {
				field : 'address',
				title : '组合地址',
				width : 400
			}, {
				field : 'edate',
				title : '体检日期',
				width : 100
			}, {
				fixed : 'right',
				width : 100,
				align : 'center',
				toolbar : '#barRole'
			} ] ],
			cellMinWidth : 30,
			id : 'sign',
			url : 'assessmentReadJwAction.action',
			where : {
				act : 'findHealthfileDetail',
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