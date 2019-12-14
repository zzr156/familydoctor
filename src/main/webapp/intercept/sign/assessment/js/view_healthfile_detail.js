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
		dataToUi2(jsonObj, "healthfileForm");

		// 症状多选框
		var tjzzqk = jsonObj.tjzzqk;
		if (tjzzqk == "1") {
			document.getElementById("zz_wu").setAttribute("checked", "checked");
		}
		if (tjzzqk.indexOf(";2;") != -1) {
			document.getElementById("zz_tt").setAttribute("checked", "checked");
		}
		if (tjzzqk.indexOf(";3;") != -1) {
			document.getElementById("zz_ty").setAttribute("checked", "checked");
		}
		if (tjzzqk.indexOf(";4;") != -1) {
			document.getElementById("zz_xj").setAttribute("checked", "checked");
		}
		if (tjzzqk.indexOf(";5;") != -1) {
			document.getElementById("zz_xm").setAttribute("checked", "checked");
		}
		if (tjzzqk.indexOf(";6;") != -1) {
			document.getElementById("zz_xt").setAttribute("checked", "checked");
		}
		if (tjzzqk.indexOf(";7;") != -1) {
			document.getElementById("zz_mxks").setAttribute("checked", "checked");
		}
		if (tjzzqk.indexOf(";8;") != -1) {
			document.getElementById("zz_kt").setAttribute("checked", "checked");
		}
		if (tjzzqk.indexOf(";9;") != -1) {
			document.getElementById("zz_hxkn").setAttribute("checked", "checked");
		}
		if (tjzzqk.indexOf(";10;") != -1) {
			document.getElementById("zz_dy").setAttribute("checked", "checked");
		}
		if (tjzzqk.indexOf(";11;") != -1) {
			document.getElementById("zz_dn").setAttribute("checked", "checked");
		}
		if (tjzzqk.indexOf(";12;") != -1) {
			document.getElementById("zz_tzxj").setAttribute("checked", "checked");
		}
		if (tjzzqk.indexOf(";13;") != -1) {
			document.getElementById("zz_fl").setAttribute("checked", "checked");
		}
		if (tjzzqk.indexOf(";14;") != -1) {
			document.getElementById("zz_gjzt").setAttribute("checked", "checked");
		}
		if (tjzzqk.indexOf(";15;") != -1) {
			document.getElementById("zz_slmh").setAttribute("checked", "checked");
		}
		if (tjzzqk.indexOf(";16;") != -1) {
			document.getElementById("zz_sjmm").setAttribute("checked", "checked");
		}
		if (tjzzqk.indexOf(";17;") != -1) {
			document.getElementById("zz_nj").setAttribute("checked", "checked");
		}
		if (tjzzqk.indexOf(";18;") != -1) {
			document.getElementById("zz_nt").setAttribute("checked", "checked");
		}
		if (tjzzqk.indexOf(";19;") != -1) {
			document.getElementById("zz_bm").setAttribute("checked", "checked");
		}
		if (tjzzqk.indexOf(";20;") != -1) {
			document.getElementById("zz_fx").setAttribute("checked", "checked");
		}
		if (tjzzqk.indexOf(";21;") != -1) {
			document.getElementById("zz_exot").setAttribute("checked", "checked");
		}
		if (tjzzqk.indexOf(";22;") != -1) {
			document.getElementById("zz_yh").setAttribute("checked", "checked");
		}
		if (tjzzqk.indexOf(";23;") != -1) {
			document.getElementById("zz_em").setAttribute("checked", "checked");
		}
		if (tjzzqk.indexOf(";24;") != -1) {
			document.getElementById("zz_rfzt").setAttribute("checked", "checked");
		}
		if (tjzzqk.indexOf(";25;") != -1) {
			document.getElementById("zz_qt").setAttribute("checked", "checked");
		}

		//脑血管
		var zyjkwt_nxg = jsonObj.zyjkwt_nxg;
		if (zyjkwt_nxg.indexOf("1") != -1) {
			document.getElementById("zyjkwt_nxgfirst").setAttribute("checked", "checked");
		}

		if (zyjkwt_nxg.indexOf("2") != -1) {
			document.getElementById("zyjkwt_nxgzz").setAttribute("checked", "checked");
		}
		if (zyjkwt_nxg.indexOf("3") != -1) {
			document.getElementById("zyjkwt_nxgncx").setAttribute("checked", "checked");
		}
		if (zyjkwt_nxg.indexOf("4") != -1) {
			document.getElementById("zyjkwt_nxgzwm").setAttribute("checked", "checked");
		}
		if (zyjkwt_nxg.indexOf("5") != -1) {
			document.getElementById("zyjkwt_nxgnqx").setAttribute("checked", "checked");
		}
		if (zyjkwt_nxg.indexOf("6") != -1) {
			document.getElementById("zyjkwt_nxglast").setAttribute("checked", "checked");
		}

		//肾脏
		var zyjkwt_sz = jsonObj.zyjkwt_sz;
		if (zyjkwt_sz.indexOf("1") != -1) {
			document.getElementById("zyjkwt_szfirst").setAttribute("checked", "checked");
		}

		if (zyjkwt_sz.indexOf("2") != -1) {
			document.getElementById("zyjkwt_sztnsb").setAttribute("checked", "checked");
		}
		if (zyjkwt_sz.indexOf("3") != -1) {
			document.getElementById("zyjkwt_szsgn").setAttribute("checked", "checked");
		}
		if (zyjkwt_sz.indexOf("4") != -1) {
			document.getElementById("zyjkwt_szjxsy").setAttribute("checked", "checked");
		}
		if (zyjkwt_sz.indexOf("5") != -1) {
			document.getElementById("zyjkwt_szmxsy").setAttribute("checked", "checked");
		}
		if (zyjkwt_sz.indexOf("6") != -1) {
			document.getElementById("zyjkwt_szlast").setAttribute("checked", "checked");
		}
		//心脏
		var zyjkwt_xzwfx = jsonObj.zyjkwt_xzwfx;
		if (zyjkwt_xzwfx.indexOf("1") != -1) {
			document.getElementById("zyjkwt_xzfirst").setAttribute("checked", "checked");
		}

		if (zyjkwt_xzwfx.indexOf("2") != -1) {
			document.getElementById("zyjkwt_xzxjgs").setAttribute("checked", "checked");
		}
		if (zyjkwt_xzwfx.indexOf("3") != -1) {
			document.getElementById("zyjkwt_xzxjt").setAttribute("checked", "checked");
		}
		if (zyjkwt_xzwfx.indexOf("4") != -1) {
			document.getElementById("zyjkwt_xzgzdm").setAttribute("checked", "checked");
		}
		if (zyjkwt_xzwfx.indexOf("5") != -1) {
			document.getElementById("zyjkwt_xzxlsj").setAttribute("checked", "checked");
		}
		if (zyjkwt_xzwfx.indexOf("6") != -1) {
			document.getElementById("zyjkwt_xzxqqt").setAttribute("checked", "checked");
		}
		if (zyjkwt_xzwfx.indexOf("7") != -1) {
			document.getElementById("zyjkwt_xzlast").setAttribute("checked", "checked");

		}

		//血管
		var zyjkwt_xgwfx = jsonObj.zyjkwt_xgwfx;
		if (zyjkwt_xgwfx.indexOf("1") != -1) {
			document.getElementById("zyjkwt_xgfirst").setAttribute("checked", "checked");
		}
		if (zyjkwt_xgwfx.indexOf("2") != -1) {
			document.getElementById("zyjkwt_xgdml").setAttribute("checked", "checked");
		}
		if (zyjkwt_xgwfx.indexOf("3") != -1) {
			document.getElementById("zyjkwt_xgdmjb").setAttribute("checked", "checked");
		}
		if (zyjkwt_xgwfx.indexOf("4") != -1) {
			document.getElementById("zyjkwt_xglast").setAttribute("checked", "checked");

		}
		//眼部
		var zyjkwt_ybwfx = jsonObj.zyjkwt_ybwfx;
		if (zyjkwt_ybwfx.indexOf("1") != -1) {
			document.getElementById("zyjkwt_ybfirst").setAttribute("checked", "checked");
		}
		if (zyjkwt_ybwfx.indexOf("2") != -1) {
			document.getElementById("zyjkwt_ybswm").setAttribute("checked", "checked");
		}
		if (zyjkwt_ybwfx.indexOf("3") != -1) {
			document.getElementById("zyjkwt_ybsrsz").setAttribute("checked", "checked");
		}
		if (zyjkwt_ybwfx.indexOf("4") != -1) {
			document.getElementById("zyjkwt_ybbnz").setAttribute("checked", "checked");
		}
		if (zyjkwt_ybwfx.indexOf("5") != -1) {
			document.getElementById("zyjkwt_yblast").setAttribute("checked", "checked");
		}

		//乳腺
		var ct_rxqk = jsonObj.ct_rxqk;
		if (ct_rxqk == "1") {
			document.getElementById("ct_rx_wjyc").setAttribute("checked", "checked");
		}
		if (ct_rxqk.indexOf("2") != -1) {
			document.getElementById("ct_rx_rfqc").setAttribute("checked", "checked");
		}
		if (ct_rxqk.indexOf("3") != -1) {
			document.getElementById("ct_rx_ycmr").setAttribute("checked", "checked");
		}
		if (ct_rxqk.indexOf("4") != -1) {
			document.getElementById("ct_rx_rxbk").setAttribute("checked", "checked");
		}

		if (ct_rxqk.indexOf("5") != -1) {
			document.getElementById("ct_rx_qt").setAttribute("checked", "checked");
		}
		layui.use('form', function() {
			form = layui.form;
			form.render(); // 渲染全部
			form.render('select');
			form.render('checkbox');
		});
	}
}
