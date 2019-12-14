var vo={};
var old_vo={};
var id=getQueryString("id");
$(function(){
    // alert(id);
    $("#yyid").hide();
    if(jdSourceType == "0"){
        //医保查询隐藏低保和特困
        $("#lowState").hide();
        $("#poorState").hide();
        //其他除手机号都不可修改
        var isNotPovertys = document.getElementsByName("isNotPoverty");
        for(var i=0;i<isNotPovertys.length;i++){
            var poverty =isNotPovertys[i];
            $(poverty).attr("disabled",true);
        }
        var jdgfStates = document.getElementsByName("jdgfState");
        for(var i=0;i<jdgfStates.length;i++){
            var jdgf =jdgfStates[i];
            $(jdgf).attr("disabled",true);
        }
        $("#addrRural").attr("disabled",true);
        $("#addrVillage").attr("disabled",true);
        // $("#notSignReason").attr("disabled",true);
        $("#otherReason").attr("disabled",true);
        var op = "<option value='0'>--请选择--</option>"+
            "<option value='1'>死亡</option>"+
            "<option value='3'>迁出</option>"+
            "<option value='4'>长期外出</option>"+
            "<option value='5'>拒签</option>"+
            "<option value='6'>服刑</option>"+
            "<option value='7'>信息重复</option>"+
            "<option value='8'>精神病院强制住院</option>"+
            "<option value='9'>服兵役</option>"+
            "<option value='10'>外籍</option>"+
            "<option value='11'>未入户</option>"+
            "<option value='12'>新增人员</option>"+
            "<option value='13'>联系不上</option>"+
            "<option value='14'>身份证信息错误</option>"+
            "<option value='15'>暂时未签约</option>"+
            "<option value='16'>外地建档</option>"+
            "<option value='17'>异地签约</option>"+
            "<option value='18'>无身份证或医保卡</option>";
        $("#notSignReason").html(op);
    }
    findsignModify();
});

function goback() {
    history.go(-1);
}
function  findsignModify(){
    if(id!=null && id!=""){
        vo["id"]=id;
        doAjaxPost('archivingAction.action?act=archivingLook',{strJson:JSON.stringify(vo)},function(data){
            if(data.vo!=null) {
                old_vo=data.vo;
                dataToUi2(data.vo,"form_vo");
                if(jdSourceType != "0" ){
                    if(data.vo.sex==null || data.vo.patientIdno==null){
                        $("#patientIdno").attr("disabled",false);
                    }
                    if(necs(data.vo.patientIdno)){
                        if( data.vo.patientIdno.length!=18){
                            $("#patientIdno").attr("disabled",false);
                        }
                        if(data.vo.patientIdno.length==18 && data.vo.patientIdno.indexOf("ⅹ")!=-1){
                            $("#patientIdno").attr("disabled",false);
                        }
                    }
                }

                if(data.vo.notSignReason!=""&& data.vo.notSignReason!=null){
                    $("#notSignReason").val(data.vo.notSignReason);
                }
                if(data.vo.notSignReason==20){
                    // $("#yyid").show();
                    $("#otherReason").val(data.vo.otherReason);
                }
                if(data.vo.signState=="已签约"){
                    $("#reason").hide();
                };
                if (data.vo.addrCountyCode != null && data.vo.addrCountyCode != "") {
                    $("#addrCountyCode").val(data.vo.addrCountyCode);
                    changestreet();
                    if(data.vo.delState=="0"){
                        var jdgfStates = document.getElementsByName("jdgfState");
                        for(var i=0;i<jdgfStates.length;i++){
                            if($(jdgfStates[i]).attr("value")=="1"){
                                jdgfStates[i].checked = true;
                            }
                        }
                    }else{
                        if(data.vo.delState=="2"){
                            var jdgfStates = document.getElementsByName("jdgfState");
                            for(var i=0;i<jdgfStates.length;i++){
                                if(jdgfStates[i].attr("value")=="0"){
                                    jdgfStates[i].checked = true;
                                }
                            }
                        }
                    }
                }
            }
            $("#jdSourceType").val(jdSourceType);
        },function(data){
            layer.msg("查询失败，联系管理员！");
        });
    }
}
/**
 * 根据区查询街道
 */
function changestreet(){
    if($("#addrCountyCode").val()!=null && $("#addrCountyCode").val()!=""){
        vo["upId"]=$("#addrCountyCode").val();
        doAjaxPost('manageCounAction.action?act=appAddressResult',{strJson: JSON.stringify(vo)},function(data){
            $("#addrRural").html("");
            var option1 = document.createElement('option');
            option1.setAttribute("value","");
            option1.innerText = "--请选择--";
            document.getElementById("addrRural").appendChild(option1);
            if(data!=null){
                $.each(data.rows,function(k,v){
                    if(v.state!="0"){
                        var option = document.createElement('option');
                        option.setAttribute("value", v.id);
                        option.innerText = v.name;
                        document.getElementById("addrRural").appendChild(option);
                    }
                });
                if($("#addrRuralCode").val()){
                    $("#addrRural").val($("#addrRuralCode").val());
                    changeCommittee();
                }
            }
        },function(data){
            layer.msg("级联初始化异常，请联系管理员！");
        })
    }

}
//居委会级联
function changeCommittee() {
    if($("#addrRural").val()!=null && $("#addrRural").val()!=""){
        $("#addrRuralCode").val($("#addrRural").val());
        $("#addrRuralName").val($("#addrRural").find("option:selected").text());
        vo["upId"]=$("#addrRural").val();
        doAjaxPost('manageCounAction.action?act=appAddressResult',{strJson: JSON.stringify(vo)},function(data){
            $("#addrVillage").html("");
            var option1 = document.createElement('option');
            option1.setAttribute("value","");
            option1.innerText = "--请选择--";
            document.getElementById("addrVillage").appendChild(option1);
            if(data!=null && data.rows != null){
                $.each(data.rows,function(k,v){
                    if(v.state!="0"){
                        var option = document.createElement('option');
                        option.setAttribute("value", v.id);
                        option.innerText = v.name;
                        document.getElementById("addrVillage").appendChild(option);
                    }
                });
                if($("#addrVillageCode").val()){
                    $("#addrVillage").val($("#addrVillageCode").val());
                }
            }
        },function(data){
            layer.msg("级联初始化异常，请联系管理员！");
        })
    }
}
function getValueName() {
    if($("#addrVillage").val()!=null && $("#addrVillage").val()!=""){
        $("#addrVillageCode").val($("#addrVillage").val());
        $("#addrVillageName").val($("#addrVillage").find("option:selected").text());
    }
}
function signModify() {
    if(validator("form_vo")) {
        vo = uiToData2("form_vo", vo);
        vo["notSignReason"] = $("#notSignReason").val();
        vo.logVo=getLogVo(old_vo,vo);
        doAjaxPost('archivingAction.action?act=archivingModify', {strJson: JSON.stringify(vo)}, function (data) {
            if(data.vo!=null && data.code=="800"){
                layer.msg("修改成功！");
            }
        })
    }
}
function showReaSon(){
    var options=$("#notSignReason option:selected");
    if(options.val()==20){
        // $("#yyid").show();
        // $("#otherReason").attr("validator", "{\"msg\":\"原因不能为空!\"}");
    }else{
        $("#yyid").hide();
        $("#otherReason").removeAttr("validator");
    }
}