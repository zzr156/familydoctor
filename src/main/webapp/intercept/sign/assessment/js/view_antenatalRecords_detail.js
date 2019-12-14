/**
 * Created by cjw on 2018/7/17.
 */

var vo = [];

$(function() {
	formAll();
})

/**
 * 基础信息
 */
function formAll() {
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
	doAjaxPost('assessmentReadJwAction.action?act=findAntenatalRecordsDetail', {strJson:JSON.stringify(param)},
		function(data) {
			if (data.vo != null && data.vo.fyFncqjcDTOList != null
					&& data.vo.fyFncqjcDTOList.length > 0) {
				var fyvo = data.vo.fyFncqjcDTOList;
				$.each(fyvo, function(k, v) {
					uiFromTmpfy("fnbj_tlist", v, k);
				})
			}
	
		})
}

function uiFromTmpfy(id, data, idx) {
	var t = $($("#" + id + "").html());
	if (data != undefined) {
		dataToUiTmp_idlsttr(t, data, idx);
		$("#fnbj_nkjl").append(t);
		t.fadeIn();
	}
}

function dataToUiTmp_idlsttr(ui, data, idx) {
	ui.data("vo", data);
	ui.find("td[pofield=idx]").text(idx);
	ui.find("td[pofield=zydw00]").text(data.zydw00);
	ui.find("td[pofield=jcdwna]").text(data.jcdwna);
	ui.find("td[pofield=bjzd00]").text(data.bjzd00);
	ui.find("td[pofield=mcyj00]").text(data.mcyj00);
	ui.find("td[pofield=ycq000]").text(data.ycq000);
	ui.find("td[pofield=yunci0]").text(data.yunci0);
	ui.find("td[pofield=lxdh00]").text(data.lxdh00);
	ui.find("td[pofield=xzdcun]").text(data.xzdcun);
	ui.find("td[pofield=hkdcun]").text(data.hkdcun);
	ui.find("td[pofield=zfxm00]").text(data.zfxm00);
	ui.find("td[pofield=sznl00]").text(data.sznl00);
	ui.find("td[pofield=xm0000]").text(data.xm0000);
	ui.find("td[pofield=ckmzbh]").text(data.ckmzbh);
	ui.find("td[pofield=jcrq00]").text(data.jcrq00);
}

function listTable(e) {
	dataToUi2(e.data("vo"), "form_jc");
	dataToUi2(e.data("vo"), "form_yfls");
	dataToUi2(e.data("vo"), "form_grls");
	dataToUi2(e.data("vo"), "from_grslbz");
	dataToUi2(e.data("vo"), "from_sljz");
	dataToUi2(e.data("vo"), "from_fncj");
	dataToUi2(e.data("vo"), "from_fngw");
	dataToUi2(e.data("vo"), "from_fnzy");
}