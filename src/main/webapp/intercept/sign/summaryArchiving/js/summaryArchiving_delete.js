var vo={};
var id=getQueryString("id");
var old_vo={};
$(function(){
    // alert(id);
    findsignDelete();
});
function goback() {
    history.go(-1);
}
function findsignDelete(){
    if(id!=null && id!=""){
        vo["id"]=id;
        doAjaxPost('archivingAction.action?act=archivingLook',{strJson:JSON.stringify(vo)},function(data){
            if(data.vo!=null) {
                dataToUi2(data.vo,"form_vo");
                old_vo=data.vo;
            }
        },function(data){
            layer.msg("查询失败，联系管理员！");
        });
    }
}
function signModify() {
    if(validator("form_vo")) {
        vo = uiToData2("form_vo", vo);
        vo.logVo=getLogVo(old_vo,vo);
        doAjaxPost('archivingAction.action?act=archivingDelete', {strJson: JSON.stringify(vo)}, function (data) {
            if(data.vo!=null && data.code=="800"){
                layer.msg("删除成功！");
            }
        })
    }
}