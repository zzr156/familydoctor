/**
 * Created by lenovo on 2017/11/10.
 */

//var id=getQueryString("id");

var vo={};
var mentCityId = getQueryString("mentCityId");
//页面加载完成时启动的方法
$(document).ready(function() {
    //$("#id").val(id);
    findCmmInit();
});


//统一初始化
function findCmmInit(){
    var mentState = document.getElementsByName("mentState");
    for(var i=0;i<mentState.length;i++){
        if(mentState[i].value=="0"){
            mentState[i].checked = true;
            break;
        }
    }
    doAjaxPost('openagreement.action?act=findCmmInit',{},callDataToInit);
    
    if(HospAreaCode=="null"){
        $("#sjid").show();
    }else {findHosp(HospAreaCode);}
}
function callDataToInit(data){
    UE.getEditor('editor',{ initialFrameWidth:$(".table-responsive").width()*0.86,initialFrameHeight: 400});
    if(data.map != null){
        if(data.map.city != null){
                    $("#mentCityId").html("");
                    var option1 = document.createElement('option');
                    option1.setAttribute("value","");
                    option1.innerText = "--请选择--";
                    document.getElementById("mentCityId").appendChild(option1);
                    $.each(data.map.city,function (k,v) {
                        var option = document.createElement('option');
                        option.setAttribute("value",v.id);
                        option.innerText = v.areaSname;
                        document.getElementById("mentCityId").appendChild(option);
                    })
        }
        if(data.map.mentType != null){
            $("#mentType").html("");
            $.each(data.map.mentType, function(k, v) {
                dataUiCodeGroup("radio","mentType",v.codeTitle,v.codeValue);
            });
            if($("#handle").val() == "add"){
                $("input[name='mentType'][value=1]").attr("checked",true);
            }
        }
        if(data.map.mentUseType != null){
            $("#mentUseType").html("");
            $.each(data.map.mentUseType, function(k, v) {
                dataUiCodeGroup("radio","mentUseType",v.codeTitle,v.codeValue);
            });
            if($("#handle").val() == "add"){
                $("input[name='mentUseType'][value=1]").attr("checked",true);
            }
        }
        onInit();//回选
    }
}


//加载完成时，查询数据，记载到表格的方法
function onInit() {
    if($("#id").val() != ""){
        doAjaxPost('openagreement.action?act=jsonByOne',{id:$("#id").val()},callDataToAgreement);
    }

}

function callDataToAgreement(data){
    dataToUi(data,"form_vo");
    if(data.mentContent != "" && data.mentContent != null){
        var ue = UE.getEditor('editor');
        ue.addListener("ready", function () {
            // editor准备好之后才可以使用
            ue.setContent(data.mentContent);
        });
    }

}



//保存的方法
function saveTable() {
    debugger;
    if(iecs($("#mentTitle").val())){
        showMsg("协议标题不能为空！");
        $("#roleName").focus();
        return;
    }
    $('#mentContent').val(UE.getEditor('editor').getContent());
    vo = uiToData("form_vo",vo);
    vo.mentCityId = mentCityId;
    if(vo.mentHospId!=null){
        doAjaxPost('openagreement.action?act=findState',{id : vo.mentHospId},function (data) {
            if(data.code!="800"){
                doAjaxPost('openagreement.action?act=add',{strJson : JSON.stringify(vo)},goto);
            }else{
                showMsg("同一家社区机构只能启用一份有效协议！");
            }
        });

    }
}



function findHosp(areaCode){
    //根据选择的市查询医院
    if(necs(areaCode)){
        areaCode= areaCode.substr(0,4);
    }
    doAjaxPost('openagreement.action?act=findHospCmm',{areaCode : areaCode},callDataHosp);
}




function callDataHosp(data){
    if(data.rows!=null){
        $("#mentHospId").html("");
        var option1 = document.createElement('option');
        option1.setAttribute("value","");
        option1.innerText = "--请选择--";
        document.getElementById("mentHospId").appendChild(option1);
        $.each(data.rows,function (k,v) {
            var option = document.createElement('option');
            option.setAttribute("value",v.id);
            option.innerText = v.hospName;
            //默认选择当前医院机构
            if(v.id == orgid)
                option.selected = true;
            document.getElementById("mentHospId").appendChild(option);
        })
    }
}

function goto(data) {
    if(data !=null && data.code != "800"){
        alertMsg(data.msg);
        return;
    }else{
        defualtHref('agreement_list.jsp?');
    }
}