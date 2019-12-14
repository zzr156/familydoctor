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
	
	doAjaxPost('assessmentReadJwAction.action?act=findGravidaFollowUpRecordsDetail',{strJson : JSON.stringify(param)} , initGrid);

}

function initGrid(data){
	var listEntity = data.vo;
	
	var yzFyEdwcsfList = listEntity.yzFyEdwcsfList; // 孕中
	var ywFyEdwcsfList = listEntity.ywFyEdwcsfList; // 孕晚
	var fyChfsjlList = listEntity.fyChfsjlList; // 产后访视
	
	layui.use('table', function() {
		table = layui.table;

		table.on('tool(yzsfTable)', function(obj) {
			var data = obj.data; // 获得当前行数据
			if (obj.event == 'look') {
				layer.open({
					title : "孕中期随访记录详情",
					type : 2,
					maxmin : true,
					content : familyUrl + "/intercept/sign/assessment/view_cqsf_detail.jsp?strJson=" + encodeURI(JSON.stringify(data)),
					area : [ "80%", "90%" ],
					success : function(layero, index) {
					},
					cancel : function(layero, index) {
					}
				});
			}
		});
		
		// 孕晚
        table.on('tool(ywsfTable)', function(obj) {
            var data = obj.data; // 获得当前行数据
            if (obj.event == 'look') {
                layer.open({
                    title : "孕晚期随访记录详情",
                    type : 2,
                    maxmin : true,
                    content : familyUrl + "/intercept/sign/assessment/view_cqsf_detail.jsp?strJson=" + encodeURI(JSON.stringify(data)),
                    area : [ "80%", "90%" ],
                    success : function(layero, index) {
                    },
                    cancel : function(layero, index) {
                    }
                });
            }
        });
        
        // 产后
        table.on('tool(chfsTable)', function(obj) {
            var data = obj.data; // 获得当前行数据
			if(data.xm0000==1){
            if (obj.event == 'look') {
                layer.open({
                    title : "产后随访记录详情",
                    type : 2,
                    maxmin : true,
                    content : familyUrl + "/intercept/sign/assessment/view_chsf_detail.jsp?strJson=" + encodeURI(JSON.stringify(data)),
                    area : [ "80%", "90%" ],
                    success : function(layero, index) {
                    },
                    cancel : function(layero, index) {
                    }
                });
            }
			}else {
                if (obj.event == 'look') {
                    layer.open({
                        title : "产后42天随访记录详情",
                        type : 2,
                        maxmin : true,
                        content : familyUrl + "/intercept/sign/assessment/view_chsf_detail1.jsp?strJson=" + encodeURI(JSON.stringify(data)),
                        area : [ "80%", "90%" ],
                        success : function(layero, index) {
                        },
                        cancel : function(layero, index) {
                        }
                    });
                }
			}
        });

        // 孕中
		table.render({
			elem : '#yzsfTable',
			cols : [ [ 
				{templet : '#indexTpl1', title : '序号', width : 60 }, 
				{field : 'cbbj00', title : '次别', width : 100 }, 
				{field : 'yz0000', title : '孕周（周）', width : 100 },
				{field : 'yzts00', title : '孕周（天）', width : 100 }, 
				{field : 'sfrq00Str', title : '随访日期', width : 100}, 
				{fixed : 'right', width : 100, align : 'center', toolbar : '#barRole'}
			] ],
			cellMinWidth : 30,
			skin : 'row',
			even : true,
			data : yzFyEdwcsfList,
			page : false, // 是否显示分页
			done : function(res, curr, count) {
			}
		});
		
		// 孕晚
		table.render({
			elem : '#ywsfTable',
			cols : [ [ 
				{templet : '#indexTpl2', title : '序号', width : 60 }, 
				{field : 'cbbj00', title : '次别', width : 100 }, 
				{field : 'yz0000', title : '孕周（周）', width : 100 },
				{field : 'yzts00', title : '孕周（天）', width : 100 }, 
				{field : 'sfrq00', title : '随访日期', width : 100}, 
				{fixed : 'right', width : 100, align : 'center', toolbar : '#barRole1'}
			] ],
			cellMinWidth : 30,
			skin : 'row',
			even : true,
			data : ywFyEdwcsfList,
			page : false, // 是否显示分页
			done : function(res, curr, count) {
			}
		});
		
		// 产后
		table.render({
			elem : '#chfsTable',
			cols : [ [ 
				{templet : '#indexTpl3', title : '序号', width : 60 }, 
				{field : 'xm0000', title : '项目(1：产后，2：产后42天)', width : 200 },
				{field : 'birthday', title : '出生日期', width : 100 }, 
				{field : 'adress_rural', title : '常住地址：乡（镇、街道办事处）', width : 200}, 
				{field : 'address', title : '组合地址', width : 400 }, 
				{field : 'sfrq00', title : '随访日期', width : 100}, 
				{fixed : 'right', width : 100, align : 'center', toolbar : '#barRole2'}

			] ],
			cellMinWidth : 30,
			method : 'post',
			even : true,
			data : fyChfsjlList,
			page : false, // 是否显示分页
			done : function(res, curr, count) {
			}
		});
	});
}