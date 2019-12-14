/**
 * Created by lenovo on 2017/11/2.
 */
var vo={};
var id=getQueryString("id");
var type=getQueryString("type");
var protocoltpye = getQueryString("protocoltpye");
var typeadd=getQueryString("typeadd");
var addtpye=getQueryString("addtpye");
var drId;
$(function(){
    Initialization();
$("#hospId").val(orgid);
$("#hospName").val(OrgName);
    var code = HospAreaCode.substring(0,4);
    if(code=="1405"){ // 山西高平
        $("#familyybId").html("");
        $("#familydaId").html("");
    }
    findPk();
    //初始配置
    signcode();
})


function signLook(){
        if(id!=null && id!=""){
            vo["id"]=id;
            doAjaxPost('signAction.action?act=signLook',{strJson:JSON.stringify(vo)},function(data){
                if(data.vo!=null){
                drId=data.vo.drId;
                   /* $.each($("#form_vo").find("[pofield]"),function(k,v){
                        $(v).val(data.vo[$(v).attr("pofield")]);
                        if(data.vo["patientGender"]!=undefined){
                            var checkptgender = document.getElementsByName("patientGender");
                            for(var i=0;i<checkptgender.length;i++){
                                if(checkptgender[i].value==data.vo["patientGender"]){
                                    checkptgender[i].checked = true;
                                    break;
                                }
                            }
                        }
                        if(data.vo["signlx"]!=undefined){
                            var checksignlx = document.getElementsByName("signlx");
                            for(var i=0;i<checksignlx.length;i++){
                                if(checksignlx[i].value==data.vo["signlx"]){
                                    checksignlx[i].checked = true;
                                    break;
                                }
                            }
                        }
                        if(data.vo["signsJjType"]!=undefined){
                            var checksignsJjType = document.getElementsByName("signsJjType");
                            for(var i=0;i<checksignsJjType.length;i++){
                                if(checksignsJjType[i].value==data.vo["signsJjType"]){
                                    checksignsJjType[i].checked = true;
                                    break;
                                }
                            }
                        }
                        if(data.vo["persGroup"]!=undefined){
                            var checkpersGroup = document.getElementsByName("persGroup");
                            for(var i=0;i<checkpersGroup.length;i++){
                                if(checkpersGroup[i].value==data.vo["persGroup"]){
                                    checkpersGroup[i].checked = true;
                                    break;
                                }
                            }
                        }
                        var rObj = document.getElementsByName("svsPkg");
                        for(var i = 0;i < rObj.length;i++){//服务类型
                            var svs={};
                            svs = vo.svsPkg.split(",");
                            for(var j = 0;j <svs.length;j++) {
                                if (rObj[i].value == svs[j]) {
                                    rObj[i].checked = true ;
                                    break;
                                }
                            }
                        }
                        if(data.vo["signPersGroup"]!=undefined){
                            var signPersGroup=data.vo["signPersGroup"].split(";");
                            var checkpersGroup = document.getElementsByName("persGroup");
                            for(var i=0;i<checkpersGroup.length;i++){
                               for(var j=0;j<signPersGroup.length;j++){
                                   if(checkpersGroup[i].value==signPersGroup[j]){
                                       checkpersGroup[i].checked = true;
                                       break;
                                   }
                               }

                            }
                        }

                        if(data.vo["signpackageid"]!=undefined && data.vo["signpackageid"]!=null){
                            var checksignlx = document.getElementsByName("signpackageid");
                            for(var i=0;i<checksignlx.length;i++){
                                if(checksignlx[i].value==data.vo["signpackageid"]){
                                    checksignlx[i].checked = true;
                                    break;
                                }
                            }
                        }

                    });*/
                   //年龄显示转化
                    data.vo.patientAge = GetAge(data.vo.patientIdno,$("#nowtime").val());
                    dataToUi2(data.vo,"form_vo");

                    if(data.vo["signpackageid"]!=undefined && data.vo["signpackageid"]!=null){
                        var signPersGroup=data.vo["signpackageid"].split(";");
                        var checkpersGroup = document.getElementsByName("signpackageid");
                        for(var i=0;i<checkpersGroup.length;i++){
                            for(var j=0;j<signPersGroup.length;j++){
                                if(checkpersGroup[i].value==signPersGroup[j]){
                                    checkpersGroup[i].checked = true;
                                    break;
                                }
                            }

                        }
                    }
                    PkradioInit();
                }
            },function(data){
                layer.msg("查询失败，联系管理员！");
            });
        }

}

function lookprotocol(){
//    window.location.href= "sign_look.jsp?id="+data.vo.id;

    window.location.href="sign_protocol.jsp?teamId="+$("#teamId").val()+"&patientId="+$("#patientId").val()+"&id="+id+"&protocoltpye=800"+"&type="+true+"&addtpye="+addtpye+"&drId="+drId;
}

/**
 * 返回到目标页面
 */
function goto() {
    if (addtpye == "1") {// 签约筛选进入
        if (protocoltpye == "800") {// 签约筛选>协议>查看>--筛选列表
            defualtHref('sign_pt_list.jsp?');
        } else {
            history.go(-1);// 筛选查看返回
        }
    } else {
        if (type) {
            if (typeadd) {
                if (addtpye == "2") {// 返回手动签约界面
                    defualtHref('sign_add.jsp?');
                } else {// 返回读卡签约界面
                    defualtHref('sign_card.jsp?');
                }
            } else if (protocoltpye == "800") {//居民签约信息列表查看>协议>查看>--居民签约信息列表
                history.go(-3);
            } else {
                history.go(-1);// 居民签约信息列表查看返回
            }
        } else {
            history.go(-1);
        }
    }
}

<!-- 初始化服务项 -->
function findPk(){
    vo["hospId"]=orgid;
    doAjaxPost('signAction.action?act=findPk',{qvoJson:JSON.stringify(vo)},function(data){

        if(data!=null){
            var pk="";
            $.each(data,function(k,v){
                if(pk==""){
                    pk="<input onclick='Pkradio($(this))' pofield='signpackageid' type='checkbox' name='signpackageid' data-index='"+k+"' style='width:25px;height:25px;' value="+v.id+">"+v.sersmName;
                }else{
                    pk+="<input onclick='Pkradio($(this))' pofield='signpackageid' type='checkbox' name='signpackageid' data-index='"+k+"' style='width:25px;height:25px;' value="+v.id+">"+v.sersmName;
                }
            })
            if(pk!=""){
                $("#signpackageid").append(pk);
            }else {
                layer.msg("请先维护服务项，再进行签约！");
            }
        }
        signLook();
    },function(data){
        layer.msg("初始化失败-004，请联系管理员！");
    });

}
//莆田 规则
function signcode(){
    doAjaxPost('signAction.action?act=signcode',{hospid:orgid},function(data){
        if(data.vo!=null){
            if(data.vo.codeValue=="3503"){
                $("#jiafan").text("乙方");
                $("#yifan").text("甲方");
            }
        }
    })
}


function  PkradioInit() {

    var sid="";
    var signpackageid=document.getElementsByName("signpackageid");
    if(signpackageid!=null &&　signpackageid.length>0){
        for(i=0;i<signpackageid.length;i++){
            if(signpackageid[i].checked) {
                sid = sid + signpackageid[i].value + ";";
            }
        }
    }
    if(sid=="") {
        $("#bt").html("");
    }

    doAjaxPost('signAction.action?act=findPkone',{sid:sid},function(data){

        if(data.map.bt!=null){
            $("#bt").html("");
            $.each(data.map.bt,function(k,v){
                /*$("#bt").append("<label class=\"layui-form-label\" style=\"width: 150px;\">"+v.govTitle+"</label>\n" +
                    "            <div class=\"layui-input-inline\" style=\"width: 120px;\" id=\"ybz\">\n" +
                    "                <input type=\"text\" disabled=\"disabled\" id=\"amountPrivateybz\" name=\"amountPrivateybz\" lay-verify=\"email\" autocomplete=\"off\" class=\"layui-input\" value=\""+v.govMoney+"\">\n" +
                    "            </div>")*/
                if(HospAreaCode.substr(0,4)=="3505"){
                    if(v.id!="75d480d4-456d-4a13-ba5b-bcd4477541b0"){
                        $("#bt").append("<label class=\"layui-form-label\" style=\"width: 150px;\">"+v.govTitle+"</label>\n" +
                            "            <div class=\"layui-input-inline\" style=\"width: 120px;\" id=\"ybz\">\n" +
                            "                <input type=\"text\" disabled=\"disabled\" id=\"amountPrivateybz\" name=\"amountPrivateybz\" lay-verify=\"email\" autocomplete=\"off\" class=\"layui-input\" value=\""+v.govMoney+"\">\n" +
                            "            </div>")
                    }
                }else{
                    $("#bt").append("<label class=\"layui-form-label\" style=\"width: 150px;\">"+v.govTitle+"</label>\n" +
                        "            <div class=\"layui-input-inline\" style=\"width: 120px;\" id=\"ybz\">\n" +
                        "                <input type=\"text\" disabled=\"disabled\" id=\"amountPrivateybz\" name=\"amountPrivateybz\" lay-verify=\"email\" autocomplete=\"off\" class=\"layui-input\" value=\""+v.govMoney+"\">\n" +
                        "            </div>")
                }
            })
        }

    });
}