var vo={};
var qvo={};
var vos={};
var voexcel=[];
var table;
var laydate;
var numberCount = 0;
var drRole ="";
var isFind = 0;
$(function(){
    changecity();
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
    //         }
    //     }
    //
    // },function(data){
    //     layer.msg("级联初始化异常，请联系管理员！");
    // })
    var code = HospAreaCode.substring(0,4);
});
//查询
function findList() {
    var index = layer.load(1);
    qvo = uiToData("form_qvo",qvo);//界面查询值绑定到变量
    if($("#hospId").val()!=""){
        table.render({
            height: 'full-250'
            ,elem: '#manageArchivingTable'
            ,cols: [[
                {field: 'index',title: '序号',width: 60}
                ,{field: 'name', title: '团队名称', width: 150}
                ,{field: 'dyzrr', title: '第一责任人', width: 150}
                ,{field: 'tdcy', title: '团队成员', width: 150}
                ,{field: 'signCount', title: '已签约', width: 60}
                ,{field: 'ptr', title: '一般人群', width: 60}
                ,{field: 'et', title: '0～6岁儿童', width: 100}
                ,{field: 'lnr', title: '老年人', width: 60}
                ,{field:'gxy', title: '高血压', width: 60}
                ,{field: 'tnb', title: '2型糖尿病', width: 100}
                ,{field:'ycf', title: '孕产妇', width: 60}
                ,{field: 'jsb', title: '严重精神障碍', width: 100}
                ,{field: 'jhb', title: '肺结核', width: 60}
                ,{field: 'cjr', title: '残疾人', width: 60}
            ]]
            ,id: 'manageArchiving'
            ,url: 'manageArchivingAction.action'
            ,where: {act: 'findManageArchivingWeb',strJson:JSON.stringify(qvo)}
            ,method: 'post'
            ,skin: 'row' //表格风格
            // ,size: 'sm'
            ,even: true
            ,page: false //是否显示分页
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
    }else {
        if($("#patientStreet").val()!=""){
            table.render({
                height: 'full-250'
                ,elem: '#manageArchivingTable'
                ,cols: [[
                    {field: 'index',title: '序号',width: 60}
                    ,{field: 'name', title: '机构', width: 150}
                    ,{field: 'signCount', title: '已签约', width: 60}
                    ,{field: 'ptr', title: '一般人群', width: 60}
                    ,{field: 'et', title: '0～6岁儿童', width: 100}
                    ,{field: 'lnr', title: '老年人', width: 60}
                    ,{field:'gxy', title: '高血压', width: 60}
                    ,{field: 'tnb', title: '2型糖尿病', width: 100}
                    ,{field:'ycf', title: '孕产妇', width: 60}
                    ,{field: 'jsb', title: '严重精神障碍', width: 100}
                    ,{field: 'jhb', title: '肺结核', width: 60}
                    ,{field: 'cjr', title: '残疾人', width: 60}
                ]]
                ,id: 'manageArchiving'
                ,url: 'manageArchivingAction.action'
                ,where: {act: 'findManageArchivingWeb',strJson:JSON.stringify(qvo)}
                ,method: 'post'
                ,skin: 'row' //表格风格
                // ,size: 'sm'
                ,even: true
                ,page: false //是否显示分页
                ,limits: [5,10,25,50,100,500,1000,3000,5000,10000,30000,50000]
                ,limit: 10 //每页默认显示的数量
                ,done: function(res, curr, count){
                    vos=res.data;
                    numberCount=res.data.length;
                    //遮罩关闭
                    layer.close(index);
                }
            });
        }else{
            table.render({
                height: 'full-250'
                ,elem: '#manageArchivingTable'
                ,cols: [[
                    {field: 'index',title: '序号',width: 60}
                    ,{field: 'name', title: '地区', width: 150}
                    ,{field: 'shouldSignCount', title: '应签约', width: 100}
                    ,{field: 'signCount', title: '已签约', width: 60}
                    ,{field: 'ptr', title: '一般人群', width: 60}
                    ,{field: 'et', title: '0～6岁儿童', width: 100}
                    ,{field: 'lnr', title: '老年人', width: 60}
                    ,{field:'gxy', title: '高血压', width: 60}
                    ,{field: 'tnb', title: '2型糖尿病', width: 100}
                    ,{field:'ycf', title: '孕产妇', width: 60}
                    ,{field: 'jsb', title: '严重精神障碍', width: 100}
                    ,{field: 'jhb', title: '肺结核', width: 60}
                    ,{field: 'cjr', title: '残疾人', width: 60}
                ]]
                ,id: 'manageArchiving'
                ,url: 'manageArchivingAction.action'
                ,where: {act: 'findManageArchivingWeb',strJson:JSON.stringify(qvo)}
                ,method: 'post'
                ,skin: 'row' //表格风格
                // ,size: 'sm'
                ,even: true
                ,page: false //是否显示分页
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
    if(!$("#hospId").attr("disabled")){
        $("#hospId").val("");
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
    // findList();
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
                if(drRole != ""){
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
                            changeStreet();
                        }
                    }
                }

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
            }
        },function(data){
            layer.msg("级联初始化异常，请联系管理员！");
        })
    }
}
function changeStreet(){
    if($("#patientArea").val()!=null && $("#patientArea").val()!=""){
        vo["upId"]=$("#patientArea").val();
        doAjaxPost('manageCounAction.action?act=appAddressResult',{strJson: JSON.stringify(vo)},function(data){
            $("#patientStreet").html("");
            var option1 = document.createElement('option');
            option1.setAttribute("value","");
            option1.innerText = "--请选择--";
            document.getElementById("patientStreet").appendChild(option1);
            if(data!=null){
                var flag = false;
                $.each(data.rows,function(k,v){
                    if(v.state!="0"){
                        if(HospAreaCode==v.id){
                            flag = true;
                        }
                        var option = document.createElement('option');
                        option.setAttribute("value", v.id);
                        option.innerText = v.name;
                        document.getElementById("patientStreet").appendChild(option);
                    }
                });
                if(drRole != "" ){
                    if(drRole!="1" && drRole!="2" && drRole !="3"){
                        // if(flag){
                        //     $("#patientStreet").val(HospAreaCode);
                        //     $("#patientStreet").attr("disabled","disabled");
                        //     changeCommittee();
                        // }else{
                        //
                        // }
                        if(HospAreaCode.substring(0,9)!=HospAreaCode.substring(0,6)+"000"){
                            var ccc = HospAreaCode.substring(0,9)+"000";
                            $("#patientStreet").val(ccc);
                            $("#patientStreet").attr("disabled","disabled");
                            changeHospId();
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

//医院
function changeHospId() {
    if($("#patientStreet").val()!=null && $("#patientStreet").val()!=""){
        vo["patientStreet"]=$("#patientStreet").val();
        $("input:radio[name=isFindState]:checked").each(function(){
            vo["isFindState"] = $(this).attr("value");
        });
        doAjaxPost('manageArchivingAction.action?act=findHospByArea',{strJson : JSON.stringify(vo)},function(data){
            $("#hospId").html("");
            var option1 = document.createElement('option');
            option1.setAttribute("value","");
            option1.innerText = "--请选择--";
            document.getElementById("hospId").appendChild(option1);
            if(data!=null && data.rows != null){
                $.each(data.rows,function(k,v){
                    if(v.state!="0"){
                        var option = document.createElement('option');
                        option.setAttribute("value", v.id);
                        option.innerText = v.hospName;
                        document.getElementById("hospId").appendChild(option);
                        // if(isFind==0){
                        //     if(v.hospAreaCode ==$("#patientStreet").val() ){
                        //         var option = document.createElement('option');
                        //         option.setAttribute("value", v.id);
                        //         option.innerText = v.hospName;
                        //         document.getElementById("hospId").appendChild(option);
                        //     }
                        // }else{
                        //     if(v.hospAreaCode != $("#patientStreet").val()){
                        //         var option = document.createElement('option');
                        //         option.setAttribute("value", v.id);
                        //         option.innerText = v.hospName;
                        //         document.getElementById("hospId").appendChild(option);
                        //     }
                        // }
                    }
                });
                if(drRole!=""){
                    if(drRole != "1" && drRole !="2" && drRole !="3"){
                        if(isFind==0){
                            if( HospAreaCode ==$("#patientStreet").val()){
                                $("#hospId").val(orgid);
                                $("#hospId").attr("disabled","disabled");
                            }
                        }else{
                            $("#hospId").attr("disabled",false);
                            if(HospAreaCode !=$("#patientStreet").val()){
                                $("#hospId").val(orgid);
                                $("#hospId").attr("disabled","disabled");
                            }
                        }
                        // if($("#patientArea").attr("disabled")){
                        //     $("#hospId").val(orgid);
                        //     $("#hospId").attr("disabled","disabled");
                        // }
                    }
                }

                // if(drRole =="4"){
                //     $("#hospId").val(orgid);
                //     $("#hospId").attr("disabled","disabled");
                // }else if(drRole=="9"){
                //     $("#hospId").val(orgid);
                //     $("#hospId").attr("disabled","disabled");
                // }
            }
        },function(data){
            layer.msg("级联初始化异常，请联系管理员！");
        })
    }

}


//导出当前页
function exportCurrent(num) {
    qvo = uiToData("form_qvo",qvo);//界面查询值绑定到变量
    window.open("manageArchivingAction.action?act=findManageArchivingXxWebExcel&typeNum="+num+"&strJson="+JSON.stringify(qvo)+"&numberCount="+numberCount);

}
function  showHosp(num) {
    isFind = num;
    $("#hospId").removeAttr("disabled");
    changeHospId();
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
