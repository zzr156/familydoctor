var vo={};
var id=getQueryString("id");
var old_vo={};
$(function(){
    $("#id").val(id);
    findsignDelete();
    var revokeStates = document.getElementsByName("revokeState");
    for(var i=0;i<revokeStates.length;i++){
        if($(revokeStates[i]).attr("value")=='3'){
            revokeStates[i].checked = true;
            $("#yyid").hide();
        }
    }
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
                $("#revokeReason").val(data.vo.delReason);
                var revokeStates = document.getElementsByName("revokeState");
                for(var i=0;i<revokeStates.length;i++){
                    if($(revokeStates[i]).attr("value")==data.vo.delState){
                        revokeStates[i].checked = true;
                        if(data.vo.delState == 4){
                            $("#yyid").show();
                            $("#cxsj").text("死亡时间");
                            $("#cxyy").text("死亡原因");
                            $("#revokeReason").attr("validator", "{\"msg\":\"原因不能为空!\"}");
                        }
                    }
                }
                $("#revokeReason").val(data.vo.revokeReason);
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
        doAjaxPost('archivingAction.action?act=archivingRevoke', {strJson: JSON.stringify(vo)}, function (data) {
            if(data.vo!=null && data.code=="800"){
                layer.msg("注销成功！");
            }
        })
    }
}
function onTypevalue(e) {
    if(e=="3"){
        $("#yyid").hide();
        $("#revokeReason").removeAttr('validator');
        $("#cxsj").text("失访时间");
        $("#cxyy").text("失访原因");
    }else if(e=="4") {
        $("#yyid").show();
        $("#cxsj").text("死亡时间");
        $("#cxyy").text("死亡原因");
        $("#revokeReason").attr("validator", "{\"msg\":\"原因不能为空!\"}");
    }
}