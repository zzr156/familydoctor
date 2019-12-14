var vo={};
$(function(){
    doAjaxPost('teamAction.action?act=commList',{},initBack);

})
function initBack(data) {
    if(data.map != null){
        //团队类别
        if(data.map.teamType!=null){
            $("#teamType").html("");
            var option = document.createElement('option');
            option.setAttribute("value","");
            option.innerText = "--请选择--";
            document.getElementById("teamType").appendChild(option);
            $.each(data.map.teamType, function(k, v) {
                dataUiCodeGroup("select","teamType",v.codeTitle,v.codeValue);
            });
        }
    }

    var orgid2=getUrlParam("orgid");
    //查询当前机构
    doAjaxPost('teamAction.action?act=findOrgById',{id:orgid2},orginit);

}
function orginit(data) {
    findDrUser(data.vo.id);
}
function findDrUser(hospId){
    doAjaxPost('appdr.action?act=findCmmByHosp',{hospId:hospId},function(data){
        if(data.rows!=null){
            $("#teamDr").html("");
            var option = document.createElement('option');
            option.setAttribute("value","");
            option.innerText = "--请选择医生--";
            document.getElementById("teamDr").appendChild(option);
            $.each(data.rows,function(k,v){//hosps
                dataUiCodeGroup("select","teamDr",v.drName,v.id+";"+v.drCode);
            })
            //查询团队
            var teamid=getUrlParam("id");
            doAjaxPost('teamAction.action?act=jsonByOne',{id:teamid},teaminit);
        }
    });

}
function teaminit(data) {
    dataToUi2(data.vo,"form_vo");
    $("#teamDr").val(data.vo.teamDrId+";"+data.vo.teamDrCode);
    $("#teamDrCode").val(data.vo.teamDrCode);
    $("#teamDr").chosen();
}

function teamModify() {
    if(validator("form_vo")) {
        vo = uiToData2("form_vo", vo);
        doAjaxPost('teamAction.action?act=modify', {strJson: JSON.stringify(vo)}, teamModifyBack);
    }
}
function teamModifyBack(data) {
    if(data.msg == 'true'){
        var url = 'team_list.jsp?1=1';
        defualtHref(url);
    }else{
        layer.msg(data.msg);
    }
}


function addCode(){
    var options=$("#teamDr option:selected");
    var value = $("#teamDr").val();
    if(value!=""&&value!=null){
        var values = value.split(";");
        var code = values[1];
        $("#teamDrCode").val(code);
        $("#teamDrId").val(values[0]);
        $("#teamDrName").val(options.text());
    }else{
        $("#teamDrCode").val("");
        $("#teamDrId").val("");
        $("#teamDrName").val("");
    }

}