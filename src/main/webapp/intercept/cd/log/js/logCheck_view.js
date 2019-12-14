var vo={};
var qvo={};
var businessId = getUrlParam("businessId");
$(function(){
    getLogsId();

})

function getLogsId(){   //取得LOGS的ID值，然后请求数据详情
    var logsId = getUrlParam("logsId");

    doAjaxPost('logAction.action?act=lookOneLogs',{"id":logsId},oneBack);
}

function oneBack(data) {   //查询回调
    $("#tlist").html("");
    //$("#blist").show();
    $.each(data.rows, function(k, v) {
        var ui = $($("#logView").html());//取行模板
        ui.data("vo", v);//保存行数据
        ui.find("td[pofield=id]").text(businessId);
        ui.find("td[pofield=key]").text(v.key);
        ui.find("td[pofield=oldValue]").text(v.oldValue);
        ui.find("td[pofield=newValue]").text(v.newValue);
        $("#tlist").append(ui);//添加行
        ui.fadeIn();
    });
}




function isNotBlank(data){
    if(data == null || data == undefined || data == ""){
        return false;
    }else{
        return true;
    }
}