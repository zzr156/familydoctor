var vo={};
var qvo={};
var vos={};
var voexcel=[];
var table;
var laydate;
var numberCount = 0;
var drRole="";
$(function(){
    changecity();
    $("#signHospId").val(orgid);
    $("#jdSourceType").val(jdSourceType);
    if(jdSourceType == "0"){
        $("#proInsur").show();
        $("#idnoRemark").text("修改过身份证");
        var idnoStates = document.getElementsByName("idnoState");
        for(var i=0;i<idnoStates.length;i++){
            idnoStates[i].checked = true;
        }
    }
    // //查询当前医生权限
    // doAjaxPost('appdr.action?act=jsonByOne',{id:drid},function(data){
    //     if(data != null){
    //         if(data.drRole != null){
    //             if(data.drRole.indexOf("1")!=-1){
    //                 drRole = "1";
    //             }else if(data.drRole.indexOf("2")!=-1){
    //                 drRole = "2";
    //                 var cc = HospAreaCode.substring(0,4)+"00000000";
    //                 $("#patientCity").val(cc);
    //                 $("#patientCity").attr("disabled","disabled");
    //                 changecounty();
    //             }else if(data.drRole.indexOf("3")!=-1){
    //                 drRole = "3";
    //                 $("#hospAreaCode").val(HospAreaCode);
    //                 var cc = HospAreaCode.substring(0,4)+"00000000";
    //                 $("#patientCity").val(cc);
    //                 $("#patientCity").attr("disabled","disabled");
    //                 changecounty();
    //             }else if(data.drRole.indexOf("4")!=-1){
    //                 drRole = "4";
    //                 $("#hospAreaCode").val(HospAreaCode);
    //                 var cc = HospAreaCode.substring(0,4)+"00000000";
    //                 $("#patientCity").val(cc);
    //                 $("#patientCity").attr("disabled","disabled");
    //                 changecounty();
    //             }else if(data.drRole.indexOf("9") !=-1){
    //                 drRole = "9";
    //                 var cc = HospAreaCode.substring(0,4)+"00000000";
    //                 $("#patientCity").val(cc);
    //                 $("#patientCity").attr("disabled","disabled");
    //                 changecounty();
    //                 $("#signHospId").val(orgid);
    //             }
    //
    //         }
    //     }
    //
    // },function(data){
    //     layer.msg("级联初始化异常，请联系管理员！");
    // })
    var code = HospAreaCode.substring(0,4);
    layui.use('table', function(){
        table = layui.table;
        table.on('tool(archiving)', function(obj){
            var ui = obj.data; //获得当前行数据
            if(obj.event == 'modify'){ //进入修改
                forTeamModify(ui);
            }else if (obj.event == 'delete'){
                forDelete(ui);
            }
        });
    });
});
//查询
function findList() {
    var index = layer.load(1);
    qvo = uiToData("form_qvo",qvo);//界面查询值绑定到变量
    qvo["notConfirm"]="1";
    if(jdSourceType == "0"){
        var oppo=$("#patientNeighborhoodCommittee option:selected");//获取当前选择项.
        if(oppo.val() != ""){
            qvo["patientNeighborhoodCommittee"]=oppo.text();
        }
        table.render({
            // height:'400'
            height: 'full-250' //高度最大化减去差值
            ,elem: '#archivingTable'
            ,cols: [[
                {field: 'myIndex',title: '序号',width: 40,fixed: "true"}
                ,{field: 'rhfId', title: '健康档案号', width: 130}
                ,{field: 'addrCountyName', title: '县', width: 60}
                ,{field: 'addrRuralName', title: '街道(乡镇)', width: 80}
                ,{field: 'addrVillageName', title: '社区(村)', width: 80}
                ,{field: 'patientName', title: '姓名', width: 60}
                ,{field: 'patientIdno', title: '身份证号码', width: 150}
                ,{field: 'patientTel', title:'联系电话',width: 80}
                ,{field: 'signState', title: '签约状态', width: 60}
                ,{field:'isNotPoverty', title: '是否脱贫',width: 60 }
                ,{field: 'notSignReason', title: '未签原因',width:150}
                ,{field: 'provincialInsurance', title: '对象类型',width:200}

            ]]
            ,id: 'archiving'
            ,url: 'archivingAction.action'
            ,where: {act: 'findArchivingWebT',strJson:JSON.stringify(qvo)}
            ,method: 'post'
            ,skin: 'row' //表格风格
            // ,size: 'sm'
            ,even: true
            ,page: true //是否显示分页
            ,limits: [5,10,25,50,100,500,1000,3000,5000,10000,30000,50000]
            ,limit: 10 //每页默认显示的数量
            ,done: function(res, curr, count){
                vos=res.data;
                if(vos != null){
                    numberCount=res.data.length;
                }
                //遮罩关闭
                layer.close(index);
            }
        });
    }else{
        table.render({
            // height:'400'
            height: 'full-250' //高度最大化减去差值
            ,elem: '#archivingTable'
            ,cols: [[
                {field: 'myIndex',title: '序号',width: 40,fixed: "true"}
                ,{field: 'rhfId', title: '健康档案号', width: 130}
                ,{field: 'addrCountyName', title: '县', width: 60}
                ,{field: 'addrRuralName', title: '街道(乡镇)', width: 80}
                ,{field: 'addrVillageName', title: '社区(村)', width: 80}
                ,{field: 'patientName', title: '姓名', width: 60}
                ,{field: 'patientIdno', title: '身份证号码', width: 150}
                ,{field: 'patientTel', title:'联系电话',width: 80}
                ,{field: 'lowInsured', title:'低保户',width: 60}
                ,{field: 'poorHouseholds', title:'特困户',width: 60}
                ,{field: 'signState', title: '签约状态', width: 60}
                ,{field:'isNotPoverty', title: '是否脱贫',width: 60 }
                ,{field: 'notSignReason', title: '未签原因',width:150}
                ,{fixed: 'right', width:120, align:'center', toolbar: '#barRole'}
            ]]
            ,id: 'archiving'
            ,url: 'archivingAction.action'
            ,where: {act: 'findArchivingWebT',strJson:JSON.stringify(qvo)}
            ,method: 'post'
            ,skin: 'row' //表格风格
            // ,size: 'sm'
            ,even: true
            ,page: true //是否显示分页
            ,limits: [5,10,25,50,100,500,1000,3000,5000,10000,30000,50000]
            ,limit: 10 //每页默认显示的数量
            ,done: function(res, curr, count){
                vos=res.data;
                if(vos != null){
                    numberCount=res.data.length;
                }
                //遮罩关闭
                layer.close(index);
            }
        });
    }

}

function signsxcz(){
    if(!$("#patientCity").attr("disabled")){
        $("#patientCity").val("");
    }
    if(!$("#patientArea").attr("disabled")){
        $("#patientArea").val("");
    }
    if(!$("#patientStreet").attr("disabled")){
        $("#patientStreet").val("");
    }
    if(!$("#patientNeighborhoodCommittee").attr("disabled")){
        $("#patientNeighborhoodCommittee").val("");
    }
    $("#patientName").val("");
    $("#patientIdno").val("");
    var dd = document.getElementsByName("signState");
    var jdStates = document.getElementsByName("jdState");
    for(var i=0;i<jdStates.length;i++){
        if(jdStates[i].checked){
            jdStates[i].checked = false;
        }
    }
    var signStates = document.getElementsByName("signState");
    for(var i=0;i<signStates.length;i++){
        if(signStates[i].checked){
            signStates[i].checked = false;
        }
    }
    var povertyStates = document.getElementsByName("povertyState");
    for(var i=0;i<povertyStates.length;i++){
        if(povertyStates[i].checked){
            povertyStates[i].checked = false;
        }
    }
    var notConfirms = document.getElementsByName("notConfirm");
    for(var i=0;i<notConfirms.length;i++){
        if(notConfirms[i].checked){
            notConfirms[i].checked = false;
        }
    }
    var removalStates = document.getElementsByName("removalState");
    for(var i=0;i<removalStates.length;i++){
        if(removalStates[i].checked){
            removalStates[i].checked = false;
        }
    }

    var villageStates = document.getElementsByName("villageState");
    for(var i=0;i<villageStates.length;i++){
        if(villageStates[i].checked){
            villageStates[i].checked = false;
        }
    }
    var idnoStates = document.getElementsByName("idnoState");
    for(var i=0;i<idnoStates.length;i++){
        if(idnoStates[i].checked){
            idnoStates[i].checked = false;
        }
    }
    var provincialInsurances = document.getElementsByName("provincialInsurance");
    for(var i=0;i<provincialInsurances.length;i++){
        if(provincialInsurances[i].checked){
            provincialInsurances[i].checked = false;
        }
    }
}

<!-- 市级联 -->
function changecity(){
    vo["upId"]="350000000000";
    doAjaxPost('manageCounAction.action?act=appAddressResult',{strJson: JSON.stringify(vo)},function(data){
        $("#patientCity").html("");
        var option1 = document.createElement('option');
        option1.setAttribute("value","");
        option1.innerText = "--请选择--";
        document.getElementById("patientCity").appendChild(option1);
        if(data!=null){
            $.each(data.rows,function(k,v){
                if(v.id!="350200000000" && v.id!="350800000000" && v.id!="350900000000" && v.id!="357100000000"){
                    if(v.state!="0"){
                        var option = document.createElement('option');
                        option.setAttribute("value", v.id);
                        option.innerText = v.name;
                        document.getElementById("patientCity").appendChild(option);
                    }
                }

            });
            findRole();
        }
    },function(data){
        layer.msg("级联初始化异常，请联系管理员！");
    })
    layui.use('table', function(){
        table = layui.table;
        table.on('tool(archiving)', function(obj){
            var ui = obj.data; //获得当前行数据
        });
    });
}
<!-- 区县级联 -->
function changecounty(){
    if($("#patientCity").val()!=null && $("#patientCity").val()!=""){
        vo["upId"]=$("#patientCity").val();
        doAjaxPost('manageCounAction.action?act=appAddressResult',{strJson: JSON.stringify(vo)},function(data){
            $("#patientArea").html("");

            var option1 = document.createElement('option');
            option1.setAttribute("value","");
            option1.innerText = "--请选择--";
            document.getElementById("patientArea").appendChild(option1);
            if(data!=null){
                $.each(data.rows,function(k,v){
                    if(v.state!="0"){
                        var option = document.createElement('option');
                        option.setAttribute("value", v.id);
                        option.innerText = v.name;
                        document.getElementById("patientArea").appendChild(option);
                    }
                });
                if(drRole != "" ){
                    if(drRole !="1" && drRole!="2"){
                        if(HospAreaCode.substring(0,6)!=HospAreaCode.substring(0,4)+"00"){
                            var ccc = HospAreaCode.substring(0,6)+"000000";
                            if(upHospAreaCode != 'null' && upHospAreaCode != ""){
                                ccc = upHospAreaCode.substring(0,6)+"000000";
                            }
                            if(HospLevel != null && HospLevel != ""){
                                if(HospLevel == 3){
                                    ccc = HospAreaCode;
                                }
                            }
                            $("#patientArea").val(ccc);
                            $("#patientArea").attr("disabled","disabled");
                            changestreet();
                        }
                    }
                }
                // else if(drRole=="4"){
                //     var ccc = HospAreaCode.substring(0,6)+"000000";
                //     $("#patientArea").val(ccc);
                //     $("#patientArea").attr("disabled","disabled");
                //     changestreet();
                // }else if(drRole=="9"){
                //     var ccc = HospAreaCode.substring(0,6)+"000000";
                //     $("#patientArea").val(ccc);
                //     $("#patientArea").attr("disabled","disabled");
                //     changestreet();
                // }
            }
        },function(data){
            layer.msg("级联初始化异常，请联系管理员！");
        })
    }
}

function changestreet(){
    if($("#patientArea").val()!=null && $("#patientArea").val()!=""){
        vo["upId"]=$("#patientArea").val();
        doAjaxPost('manageCounAction.action?act=appAddressResult',{strJson: JSON.stringify(vo)},function(data){
            $("#patientStreet").html("");
            var option1 = document.createElement('option');
            option1.setAttribute("value","");
            option1.innerText = "--请选择--";
            document.getElementById("patientStreet").appendChild(option1);
            if(data!=null){
                $.each(data.rows,function(k,v){
                    if(v.state!="0"){
                        var option = document.createElement('option');
                        option.setAttribute("value", v.id);
                        option.innerText = v.name;
                        document.getElementById("patientStreet").appendChild(option);
                    }
                });
                if(drRole != "" ){
                    if(drRole!="1" && drRole!="2" && drRole !="3"){
                        if(HospAreaCode.substring(0,9)!=HospAreaCode.substring(0,6)+"000"){
                            var ccc = HospAreaCode.substring(0,9)+"000";
                            $("#patientStreet").val(ccc);
                            $("#patientStreet").attr("disabled","disabled");
                            changeCommittee();
                        }
                    }
                }

                // if(drRole=="4"){
                //
                // }else if(drRole=="9"){
                //     var ccc = HospAreaCode.substring(0,9)+"000";
                //     $("#patientStreet").val(ccc);
                //     $("#patientStreet").attr("disabled","disabled");
                //     changeCommittee();
                // }
            }
        },function(data){
            layer.msg("级联初始化异常，请联系管理员！");
        })
    }

}
//居委会级联
function changeCommittee() {
    if($("#patientStreet").val()!=null && $("#patientStreet").val()!=""){
        vo["upId"]=$("#patientStreet").val();
        if(jdSourceType == '0'){
            doAjaxPost('archivingAction.action?act=findJnameList',{strJson: JSON.stringify(vo)},function(data){
                $("#patientNeighborhoodCommittee").html("");
                var option1 = document.createElement('option');
                option1.setAttribute("value","");
                option1.innerText = "--请选择--";
                document.getElementById("patientNeighborhoodCommittee").appendChild(option1);
                if(data!=null && data.rows != null){
                    $.each(data.rows,function(k,v){
                        if(v.state!="0"){
                            var option = document.createElement('option');
                            option.setAttribute("value", v.jname);
                            option.innerText = v.jname;
                            document.getElementById("patientNeighborhoodCommittee").appendChild(option);
                        }
                    });
                    // if(drRole != "" ){
                    //     if(drRole!="1" && drRole!="2" && drRole !="3"){
                    //         // var ccc = HospAreaCode.substring(0,9)+"000";
                    //         $("#patientNeighborhoodCommittee").val(HospAreaCode);
                    //         $("#patientNeighborhoodCommittee").attr("disabled","disabled");
                    //     }
                    // }
                }
            },function(data){
                layer.msg("级联初始化异常，请联系管理员！");
            })
        }else{
            doAjaxPost('manageCounAction.action?act=appAddressResult',{strJson: JSON.stringify(vo)},function(data){
                $("#patientNeighborhoodCommittee").html("");
                var option1 = document.createElement('option');
                option1.setAttribute("value","");
                option1.innerText = "--请选择--";
                document.getElementById("patientNeighborhoodCommittee").appendChild(option1);
                if(data!=null && data.rows != null){
                    $.each(data.rows,function(k,v){
                        if(v.state!="0"){
                            var option = document.createElement('option');
                            option.setAttribute("value", v.id);
                            option.innerText = v.name;
                            document.getElementById("patientNeighborhoodCommittee").appendChild(option);
                        }
                    });
                    // if(drRole != "" ){
                    //     if(drRole!="1" && drRole!="2" && drRole !="3"){
                    //         // var ccc = HospAreaCode.substring(0,9)+"000";
                    //         $("#patientNeighborhoodCommittee").val(HospAreaCode);
                    //         $("#patientNeighborhoodCommittee").attr("disabled","disabled");
                    //     }
                    // }
                }
            },function(data){
                layer.msg("级联初始化异常，请联系管理员！");
            })
        }
    }
}

function xx(){
    var domName = $(this).attr('name');//获取当前单选框控件name 属性值
    var checkedState = $(this).attr('checked');//记录当前选中状态
    if(checkedState == 'checked'){
        $(this).attr('checked',false); //3.
    }
}

//导出当前页
function exportCurrent(num) {
    qvo = uiToData("form_qvo",qvo);//界面查询值绑定到变量
    qvo["notConfirm"]="1";
    var oppo=$("#patientNeighborhoodCommittee option:selected");//获取当前选择项.
    if(oppo.val() != ""){
        qvo["patientNeighborhoodCommittee"]= encodeURI(encodeURI(oppo.text()));
    }
    var patientName = $("#patientName").val();
    if(patientName != ""){
        qvo["patientName"] = encodeURI(encodeURI(patientName));
    }
    window.open("archivingAction.action?act=findArchivingXxWebExcelT&typeNum="+num+"&strJson="+JSON.stringify(qvo)+"&numberCount="+numberCount);

}
function forTeamModify(ui) {
    var id=ui.id;
    var url = "summaryArchiving_modify.jsp?id="+ui.id;
    defualtHref(url);
    // doAjaxPost('archivingAction.action?act=findRole', {}, function (data) {
    //     if(data.code=="800"){
    //         var id=ui.id;
    //         var url = "summaryArchiving_modify.jsp?id="+ui.id;
    //         defualtHref(url);
    //     }else{
    //         layer.msg("您无权修改！");
    //     }
    // })

}
function forDelete(ui) {
    var id=ui.id;
    var url = "summaryArchiving_delete.jsp?id="+ui.id;
    defualtHref(url);
    // doAjaxPost('archivingAction.action?act=findRole', {}, function (data) {
    //     if(data.code=="800"){
    //         var id=ui.id;
    //         var url = "summaryArchiving_delete.jsp?id="+ui.id;
    //         defualtHref(url);
    //     }else{
    //         layer.msg("您无权删除！");
    //     }
    // })

}
function findRole(){
    //查询当前医生权限
    doAjaxPost('appdr.action?act=jsonByOne',{id:drid},function(data){
        if(data != null){
            if(data.drRole != null){
                if(data.drRole.indexOf("1")!=-1){
                    drRole = "1";
                }else if(data.drRole.indexOf("2")!=-1){
                    drRole = "2";
                    var cc = HospAreaCode.substring(0,4)+"00000000";
                    $("#patientCity").val(cc);
                    $("#patientCity").attr("disabled","disabled");
                    changecounty();
                }else if(data.drRole.indexOf("3")!=-1){
                    drRole = "3";
                    $("#hospAreaCode").val(HospAreaCode);
                    var cc = HospAreaCode.substring(0,4)+"00000000";
                    $("#patientCity").val(cc);
                    $("#patientCity").attr("disabled","disabled");
                    changecounty();
                }else if(data.drRole.indexOf("4")!=-1){
                    drRole = "4";
                    $("#hospAreaCode").val(HospAreaCode);
                    var cc = HospAreaCode.substring(0,4)+"00000000";
                    $("#patientCity").val(cc);
                    $("#patientCity").attr("disabled","disabled");
                    changecounty();
                }else if(data.drRole.indexOf("9") !=-1){
                    drRole = "9";
                    var cc = HospAreaCode.substring(0,4)+"00000000";
                    $("#patientCity").val(cc);
                    $("#patientCity").attr("disabled","disabled");
                    changecounty();
                    $("#signHospId").val(orgid);
                }

            }
        }

    },function(data){
        layer.msg("级联初始化异常，请联系管理员！");
    })
}
/**
 * 选择去重重置居委会和身份证异常
 */
function hideVillageAndIdno(){
    var villageStates = document.getElementsByName("villageState");
    for(var i=0;i<villageStates.length;i++){
        if(villageStates[i].checked){
            villageStates[i].checked = false;
        }
    }
    var idnoStates = document.getElementsByName("idnoState");
    for(var i=0;i<idnoStates.length;i++){
        if(idnoStates[i].checked){
            idnoStates[i].checked = false;
        }
    }
}

/**
 * 选择居委会异常重置去重和身份证异常
 */
function hideIdnoAndRemoval(){
    var removalStates = document.getElementsByName("removalState");
    for(var i=0;i<removalStates.length;i++){
        if(removalStates[i].checked){
            removalStates[i].checked = false;
        }
    }
    var idnoStates = document.getElementsByName("idnoState");
    for(var i=0;i<idnoStates.length;i++){
        if(idnoStates[i].checked){
            idnoStates[i].checked = false;
        }
    }
}

/**
 * 选择身份证异常重置去重和居委会异常
 */
function hideVillageAndRemoval(){
    var removalStates = document.getElementsByName("removalState");
    for(var i=0;i<removalStates.length;i++){
        if(removalStates[i].checked){
            removalStates[i].checked = false;
        }
    }
    var villageStates = document.getElementsByName("villageState");
    for(var i=0;i<villageStates.length;i++){
        if(villageStates[i].checked){
            villageStates[i].checked = false;
        }
    }
}

