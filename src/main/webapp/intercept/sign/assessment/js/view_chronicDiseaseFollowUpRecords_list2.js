var table;
var form;

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
	
	// 高血压基本资料FROM表单赋值
	doAjaxPost('assessmentReadJwAction.action?act=findTNBPeopleInfo', {strJson :JSON.stringify(param)}, initForm);

	layui.use('table', function() {
		table = layui.table;

		table.on('tool(chronicDiseaseFollowUpRecordsTable2)', function(obj) {
			var data = obj.data; // 获得当前行数据
			if (obj.event == 'look') {
				layer.open({
					title : "慢性病随访记录详情",
					type : 2,
					maxmin : true,
					content : familyUrl + "/intercept/sign/assessment/view_chronicDiseaseFollowUpRecords_detail2.jsp?strJson=" + encodeURIComponent(JSON.stringify(data)),
					area : [ "60%", "90%" ],
					success : function(layero, index) {
					},
					cancel : function(layero, index) {
					}
				});
			}
		});

		table.render({
			elem : '#chronicDiseaseFollowUpRecordsTable2',
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
				width : 200
			}, {
				field : 'xltzqk',
				title : '心里调整',
				width : 150
			}, {
				field : 'zyxwqk',
				title : '遵医情况',
				width : 150
			}, {
				field : 'sffs00',
				title : '随访方式',
				width : 100
			}, {
				field : 'sfrq00',
				title : '随访日期',
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
				act : 'findChronicDiseaseFollowUpRecordsDetail2',
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
		dataToUi2(entityJsonStr, "chronicDiseaseFollowUpRecordsForm2");

		layui.use('form', function() {
			form = layui.form;
			form.render(); // 渲染全部
			form.render('select');
			form.render('checkbox');
		});
	}
}