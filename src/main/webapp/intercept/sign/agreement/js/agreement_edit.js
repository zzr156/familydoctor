/**
 * Created by lenovo on 2017/11/10.
 */

var id=getQueryString("id");
var type=getQueryString("type");
var menucode=getQueryString("menucode");
var vo={};
var ue;
//页面加载完成时启动的方法
$(document).ready(function() {
    $("#hospId").val(orgid);
    if(type=="true"){
        $("#roleadd").hide();
    }else{
        $("#roleadd").show();
    }


    $("#id").val(id);
    findOrgById();
    findCmmInit();
});


//统一初始化
function findCmmInit(){
    doAjaxPost('openagreement.action?act=findCmmInit',{},callDataToInit);
}
function callDataToInit(data){

    if(data.map != null){
        //状态
        if(data.map.enable != null){
            $("#mentState").html("");
            $.each(data.map.enable, function(k, v) {
                dataUiCodeGroup("radio","mentState",v.codeTitle,v.codeValue);
            });
            if($("#handle").val() == "add"){
                $("input[name='mentState'][value=1]").attr("checked",true);
            }
        }

        if(data.map.city != null){
            $("#mentCityId").html();
            $.each(data.map.city, function(k, v) {
                dataUiCodeGroup("select","mentCityId",v.areaSname,v.id);
            });
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
        ue=UE.getEditor('editor',{ initialFrameWidth:$(".table-responsive").width()*0.86,initialFrameHeight: 400,onready:function(){
            onInit();//回选
        }
        });

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
        ue.setContent(data.mentContent);
    }

}




//保存的方法
function saveTable() {
    debugger
    if(iecs($("#mentTitle").val())){
        showMsg("协议标题不能为空！");
        $("#roleName").focus();
        return;
    }
    $('#mentContent').val(UE.getEditor('editor').getContent());
    vo = uiToData("form_vo",vo);
    if(vo.mentHospId!=null){
        //modify by WangCheng
        doAjaxPost('openagreement.action?act=modify',{strJson : JSON.stringify(vo)},function (data) {
            if(data.code == "800"){
                showMsg("修改协议成功！");
                //setTimeout(function(){goto()},1000);
            }else {
                showMsg(data.msg);
            }
        });
    }
}



function findHosp(areaCode){

    //根据选择的市查询医院
    if(necs(areaCode)){
        areaCode= areaCode.substr(0,4);
    }
    doAjaxPost('<%=request.getContextPath()%>/agreement.action?act=findHospCmm',{areaCode : areaCode},callDataHosp);
}
/*function callDataHosp(data){
 if(data.rows!=null){
 $("#mentHospId").html("");
 $.each(data.rows,function(k,v){
 dataUiCodeGroup("select","mentHospId",v.hospName,v.id);
 })
 }
 }*/


function goto(data) {
    if(data !=null && data.code != "800"){
        alertMsg(data.msg);
        return;
    }else{
        defualtHref('agreement_list.jsp?1=1&loginMenuId='+menucode);
    }
}


function findOrgById() {
    doAjaxPost('teamAction.action?act=findOrgById',{id : orgid},function(data){

        if(data.vo!=null && data.vo!=""){
            callDataHosp(data.vo);
        }

    },function(data){
        layer.msg("初始化失败，请联系管理员！");
    });
}

function callDataHosp(data){
    if(data!=null){
        $("#mentHospId").html("");
        var option1 = document.createElement('option');
        option1.setAttribute("value","");
        option1.innerText = "--请选择--";
        document.getElementById("mentHospId").appendChild(option1);

            var option = document.createElement('option');
            option.setAttribute("value",data.id);
            option.innerText = data.hospName;
            document.getElementById("mentHospId").appendChild(option);

    }
}