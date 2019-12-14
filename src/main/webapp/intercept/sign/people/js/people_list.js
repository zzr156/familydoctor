var vo={};
$(function() {
    //莆田特有
    if(HospAreaCode.substr(0,4)!="3503"){
        $("#pt_").remove();
    }
    doAjaxPost('peopleAction.action?act=commList', {}, initBack);
})
function initBack(data) {
    debugger
    //查询当前数据
    doAjaxPost('peopleAction.action?act=findPeopleByAreaCode',{id:orgid},orginit);
}
function orginit(data) {
    if(data!=null && data.vo!=null){
        dataToUi2(data.vo,"form_vo");
    }
}
//修改指标
function modifyFrom() {
    if(validator("form_vo")) {
        vo = uiToData2("form_vo", vo);
        vo["orgid"] =orgid;
        doAjaxPost('peopleAction.action?act=modify', {strJson: JSON.stringify(vo)},modifyFromBack);
    }
}

function modifyFromBack(data) {
    layer.msg(data.msg);
}