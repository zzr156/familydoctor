/**
 * Created by lenovo on 2017/12/3.
 */

var id=getQueryString("id");
var signDrId=getQueryString("signDrId");
var teamId=getQueryString("teamId");
var drName=decodeURI(getQueryString("drName"));
var patientId=getQueryString("patientId");
var index = parent.layer.getFrameIndex(window.name);
var batchOperatorId=getQueryString("batchOperatorId");
var batchOperatorName=decodeURI(getQueryString("batchOperatorName"));
var vo={};
$(function(){
   $("#teamId").val(teamId);
   $("#drId").val(signDrId);
   $("#hospId").val(orgid);
   $("#drName").val(drName);
   $("#patientId").val(patientId);

    findteem();
    addOptions();

});

<!-- 初始化团队 -->
function findteem(){
    doAjaxPost('teamAction.action?act=findAll',{hospid:orgid},function(data){
        if(data!=null){
            $("#changeTeam").html("");
            var option1 = document.createElement('option');
            option1.setAttribute("value","");
            option1.innerText = "--请选择--";
            document.getElementById("changeTeam").appendChild(option1);
            $.each(data,function (k,v) {
                var option = document.createElement('option');
                option.setAttribute("value",v.id);
                option.innerText = v.teamName;
                document.getElementById("changeTeam").appendChild(option);
            })
        }
    },function(data){
        layer.msg("初始化异常-001，请联系管理员！");
    });
}

<!--  触发查询团队队长 -->
function changeteam(){
    var teamid=$("#changeTeam").val();
    doAjaxPost('teamAction.action?act=findteemAll',{teamid:teamid},function(data){
        $("#changeDr").html("");
        var option1 = document.createElement('option');
        option1.setAttribute("value","");
        option1.innerText = "--请选择--";
        document.getElementById("changeDr").appendChild(option1);
        if(data!=null){
            var code=HospAreaCode.substring(0,4);
            $.each(data,function(k,v){
                if(code=="3503"){
                    if(v.memState=="0" || v.memWorkType=="2" || v.memWorkType=="3") {
                        var option = document.createElement('option');
                        option.setAttribute("value", v.memDrId+";;;"+v.drtel);
                        option.innerText = v.memDrName;
                        document.getElementById("changeDr").appendChild(option);
                    }
                }else if(code=="3507") {//南平处理
                    if(v.memWorkType=="2" || v.memWorkType=="3") {
                        var option = document.createElement('option');
                        option.setAttribute("value", v.memDrId+";;;"+v.drtel);
                        option.innerText = v.memDrName;
                        document.getElementById("changeDr").appendChild(option);
                    }
                }else {
                    if(v.memState=="0" ) {
                        var option = document.createElement('option');
                        option.setAttribute("value", v.memDrId+";;;"+v.drtel);
                        option.innerText = v.memDrName;
                        document.getElementById("changeDr").appendChild(option);
                    }
                }
                /* if(v.memState=="0" ) {
                    var option = document.createElement('option');
                    option.setAttribute("value", v.memDrId+";;;"+v.drtel);
                    option.innerText = v.memDrName;
                    document.getElementById("changeDr").appendChild(option);
                }*/
            });
        }
    },function(data){
        layer.msg("团队信息初始化异常，请联系管理员！");
    })
}
<!--  触发查询团队成员 -->
function changedr(){
    if($("#changeDr").val()!=null && $("#changeDr").val()!=""){
        var drtel=$("#changeDr").val() .split(";;;");
        if(drtel[1]!="null" &&  drtel[1]!=""){
            $("#drTel").val(drtel[1]);
        }
    }
    var teamid=$("#changeTeam").val();
    doAjaxPost('teamAction.action?act=findteemAll',{teamid:teamid},function(data){
        $("#changeDrAssistantId").html("");

        var option1 = document.createElement('option');
        option1.setAttribute("value","");
        option1.innerText = "--请选择--";
        document.getElementById("changeDrAssistantId").appendChild(option1);
        if(data!=null){
            $.each(data,function(k,v){
                if(v.memState!="0" ) {
                    var option = document.createElement('option');
                    option.setAttribute("value", v.memDrId+";;;"+v.drtel);
                    option.innerText = v.memDrName;
                    document.getElementById("changeDrAssistantId").appendChild(option);
                }
            });
        }
    },function(data){
        layer.msg("团队信息初始化异常，请联系管理员！");
    })
}
<!-- 随便触发一下 取助理电话号码 -->
function changedrass(){
    if($("#changeDrAssistantId").val()!=null && $("#changeDrAssistantId").val()!=""){
        var signDrAssistanttel=$("#changeDrAssistantId").val() .split(";;;");
        if(signDrAssistanttel[1]!="null" && signDrAssistanttel[1]!=""){
            $("#signDrAssistantTel").val(signDrAssistanttel[1]);
        }
    }
}

function goto(){
    parent.layer.close(index);
}


function changeAdd() {

        if(validator("form_vo")) {
            var drId=$("#changeDr").val() .split(";;;");
            vo = uiToData2("form_vo", vo);
            vo.batchOperatorId=$("#batchOperatorName").val();
            vo.batchOperatorName=$("#batchOperatorName option:selected").text();
            doAjaxPost('signAction.action?act=submitChange', {strJson: JSON.stringify(vo)},function(data){

                if(data.msgCode=="800"){
                    layer.msg("变更成功！");
                  //  parent.layer.close(index);
                }else{
                    layer.msg("变更失败！");
                }
            });
        }
}


function  addOptions() {
    $("#batchOperatorName").html("");
    var option1 = document.createElement('option');
    option1.setAttribute("value",drid);
    option1.innerText = username;
    document.getElementById("batchOperatorName").appendChild(option1);
    if(batchOperatorName!=null&&batchOperatorName!="") {
        if (drid != batchOperatorId) {
            var option = document.createElement('option');
            option.setAttribute("value", batchOperatorId);
            option.setAttribute("selected", true);
            option.innerText = batchOperatorName;
            document.getElementById("batchOperatorName").appendChild(option);
        }
    }
}