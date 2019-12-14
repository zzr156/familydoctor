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
		dataToUi2(jsoNnObj, "followRecordsAndHealthForm");

		if (jsoNnObj.ssnnz0==0){//if判断条件成立的情况
                document.getElementById('ysyn').style.display = "none"//通过修改id名为“xse”的div的display属性，来隐藏
                document.getElementById('yzls').style.display = "none"//通过修改id名为“xse”的div的display属性，来隐藏
                document.getElementById('szls').style.display = "none"//通过修改id名为“xse”的div的display属性，来隐藏
		}
        if (jsoNnObj.ssnnz0==1){//if判断条件成立的情况
            document.getElementById('xse').style.display = "none"//通过修改id名为“xse”的div的display属性，来隐藏
            document.getElementById('yzls').style.display = "none"//通过修改id名为“xse”的div的display属性，来隐藏
            document.getElementById('szls').style.display = "none"//通过修改id名为“xse”的div的display属性，来隐藏
        }
        if (jsoNnObj.ssnnz0==2){//if判断条件成立的情况
            document.getElementById('ysyn').style.display = "none"//通过修改id名为“xse”的div的display属性，来隐藏
            document.getElementById('xse').style.display = "none"//通过修改id名为“xse”的div的display属性，来隐藏
            document.getElementById('szls').style.display = "none"//通过修改id名为“xse”的div的display属性，来隐藏
        }
        if (jsoNnObj.ssnnz0==3){//if判断条件成立的情况
            document.getElementById('ysyn').style.display = "none"//通过修改id名为“xse”的div的display属性，来隐藏
            document.getElementById('yzls').style.display = "none"//通过修改id名为“xse”的div的display属性，来隐藏
            document.getElementById('xse').style.display = "none"//通过修改id名为“xse”的div的display属性，来隐藏
        }

		layui.use('form', function() {
			form = layui.form;
			form.render(); // 渲染全部
			form.render('select');
			form.render('checkbox');
		});
	}
}



