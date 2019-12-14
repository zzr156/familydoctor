var vo={};
$(function(){
    doAjaxPost('teamAction.action?act=commList',{},initBack);
    // $( "#drHospName" ).autocomplete({
    //     source: function( request, response ) {
    //         $.getJSON( "teamAction.action?act=findOrg", {
    //             name: request.term
    //         }, response );
    //     },
    //     minLength: 1,
    //     select: function( event, ui ) {
    //         $( "#drHospName" ).val( ui.item.label );
    //         $( "#drHospId" ).val( ui.item.value );
    //         return false;
    //     }
    // });
    //查询当前机构
    doAjaxPost('teamAction.action?act=findOrgById',{id:orgid},orginit);
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
}
function orginit(data) {
    $("#teamHospName").val(data.vo.hospName);
    $("#teamHospId").val(data.vo.id);
    findDrUser(data.vo.id);
}

function teamAdd() {
    if(validator("form_vo")) {
        vo = uiToData2("form_vo", vo);
        doAjaxPost('teamAction.action?act=add', {strJson: JSON.stringify(vo)}, teamAddBack);
    }
}
function teamAddBack(data) {
    if(data.msg == 'true'){
        var url = 'team_list.jsp?1=1';
        defualtHref(url);
    }else{
        layer.msg(data.msg);
    }
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
                var option=dataUiCodeGroup("select","teamDr",v.drName,v.id+";"+v.drCode);
                option.setAttribute("drTel",v.drTel);
            });
            $("#teamDr").chosen();
        }
    });
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
        $("#teamTel").val(options.attr("drTel"));
    }else{
        $("#teamDrCode").val("");
        $("#teamDrId").val("");
        $("#teamDrName").val("");
    }

}

