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
	doAjaxPost('assessmentReadJwAction.action?act=findElectronicArchivesDetail', {strJson: JSON.stringify(param)}, initForm);
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
		dataToUi2(entityJsonStr, "electronicArchivesForm");

		layui.use('form', function() {
			form = layui.form;
			form.render(); // 渲染全部
			form.render('select');
			form.render('checkbox');
		});
	}
}