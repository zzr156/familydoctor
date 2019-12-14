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

		table.on('tool(followRecordsAndHealthTable)', function(obj) {
			var data = obj.data; // 获得当前行数据
			if (obj.event == 'look') {
				layer.open({
					title : "儿童随访记录详情",
					type : 2,
					maxmin : true,
					content : familyUrl + "/intercept/sign/assessment/view_followRecordsAndHealth_detail.jsp?strJson=" + encodeURIComponent(JSON.stringify(data)),
					area : [ "80%", "90%" ],
					success : function(layero, index) {
					},
					cancel : function(layero, index) {
					}
				});
			}
		});

		table.render({
			elem : '#followRecordsAndHealthTable',
			cols : [ [ {
				field : 'ssnnz0',
				title : '状态',
				width : 150
			}, {
				field : 'etsg00',
				title : '身长',
				width : 150
			}, {
				field : 'ettz00',
				title : '体重',
				width : 150
			}, {
				field : 'sfrq00',
				title : '随访日期',
				width : 150
			}, {
				fixed : 'right',
				width : 150,
				align : 'center',
				toolbar : '#barRole'
			} ] ],
			cellMinWidth : 30,
			id : 'sign',
			url : 'assessmentReadJwAction.action',
			where : {
				act : 'findChildFollowUpRecordsDetail',
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

/**
 * 界面Form表单赋值
 * 
 * @returns
 */
function initForm(data) {
	var jsonObj = $.parseJSON(data.result);
	if (jsonObj != null && jsonObj.entity != null) {
		var entityJsonStr = jsonObj.entity;
		dataToUi2(entityJsonStr, "followRecordsAndHealthForm");

		layui.use('form', function() {
			form = layui.form;
			form.render(); // 渲染全部
			form.render('select');
			form.render('checkbox');
		});
	}
}