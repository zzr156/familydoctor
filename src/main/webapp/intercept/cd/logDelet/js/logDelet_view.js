var vo={};
var qvo={};
$(function(){
    qvo.id=getUrlParam("id");
    doAjaxPost('sysLogDeletAction.action?act=view',{strJson:JSON.stringify(qvo)},oneBack);
})


function oneBack(data) {   //查询回调
    dataToUi2(data.vo,"form_vo");
}
