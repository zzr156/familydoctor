var vo={};
var qvo={};
var vos={};
var voexcel=[];
var table;
var laydate;
var numberCount = 0;
var drRole="";
$(function(){

    $("#jdSourceType").val(jdSourceType);
    changecity();
    $("#signHospId").val(orgid);
    if(jdSourceType == '0'){
        $("#proInsur").show();
        $("#cancelS").hide();
    }
    var code = HospAreaCode.substring(0,4);
    layui.use('table', function(){
        table = layui.table;
        table.on('tool(archiving)', function(obj){
            var ui = obj.data; //获得当前行数据
            if(obj.event == 'add'){//进入新增
                forArchivingCheckAdd(ui);
            }else if(obj.event == 'modify'){ //进入修改
                forArchivingCheckModify(ui);
            }else if(obj.event=='look'){//进入查看
                forArchivingCheckLook(ui);
            } else if (obj.event == "findHealthRecords") {//add by WangCheng
                findHealthRecordsArchive(ui);
            }
        });
    });
});
//查询
function findList() {
    var index = layer.load(1);
    qvo = uiToData("form_qvo",qvo);//界面查询值绑定到变量
    // if(jdSourceType == '0'){
        var oppo=$("#patientNeighborhoodCommittee option:selected");//获取当前选择项.
        if(oppo.val() != ""){
            qvo["patientNeighborhoodCommittee"]=oppo.text();
        }
        //根据权限显示按钮
        //市、区权限只能查看不能新增和修改
        if(drRole == "2" || drRole == "3"){
            table.render({
                // height:'400'
                height: 'full-250' //高度最大化减去差值
                ,elem: '#archivingTable'
                ,cols: [[
                    {field: 'myIndex',title: '序号',width: 40,fixed: "true"}
                    ,{field: 'rhfId', title: '健康档案号', width: 110}
                    ,{field: 'addrCountyName', title: '县', width: 60}
                    ,{field: 'addrRuralName', title: '街道(乡镇)', width: 65}
                    ,{field: 'addrVillageName', title: '社区(村)', width: 65}
                    ,{field: 'patientName', title: '姓名', width: 60}
                    ,{field: 'patientIdno', title: '身份证号码', width: 120}
                    ,{field: 'patientTel', title:'联系电话',width: 60}
                    ,{field: 'signState', title: '签约状态', width: 60}
                    ,{field: 'isNotPoverty', title: '是否脱贫',width: 60 }
                    ,{field: 'notSignReason', title: '未签原因',width:70}
                    ,{field: 'sourceType', title: '来源',width:50}
                    ,{field: 'delState', title: '状态',width:50}
                    ,{field: 'provincialInsurance', title: '对象类型',width:150}
                    ,{field: 'signFromDate', title: '签约时间',width:100}
                    ,{fixed: 'right', width:180, align:'center', toolbar: '#barRole1'}
                ]]
                ,id: 'archiving'
                ,url: 'archivingCheck.action'
                ,where: {act: 'findArchivingWeb',strJson:JSON.stringify(qvo)}
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
                    ,{field: 'rhfId', title: '健康档案号', width: 110}
                    ,{field: 'addrCountyName', title: '县', width: 60}
                    ,{field: 'addrRuralName', title: '街道(乡镇)', width: 65}
                    ,{field: 'addrVillageName', title: '社区(村)', width: 65}
                    ,{field: 'patientName', title: '姓名', width: 60}
                    ,{field: 'patientIdno', title: '身份证号码', width: 120}
                    ,{field: 'patientTel', title:'联系电话',width: 60}
                    ,{field: 'signState', title: '签约状态', width: 60}
                    ,{field: 'isNotPoverty', title: '是否脱贫',width: 60 }
                    ,{field: 'notSignReason', title: '未签原因',width:70}
                    ,{field: 'sourceType', title: '来源',width:50}
                    ,{field: 'delState', title: '状态',width:50}
                    ,{field: 'provincialInsurance', title: '对象类型',width:150}
                    ,{field: 'signFromDate', title: '签约时间',width:100}
                    ,{fixed: 'right', width:180, align:'center', toolbar: '#barRoleO'}
                ]]
                ,id: 'archiving'
                ,url: 'archivingCheck.action'
                ,where: {act: 'findArchivingWeb',strJson:JSON.stringify(qvo)}
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
    // }else{
    //     table.render({
    //         // height:'400'
    //         height: 'full-250' //高度最大化减去差值
    //         ,elem: '#archivingTable'
    //         ,cols: [[
    //             {field: 'myIndex',title: '序号',width: 40,fixed: "true"}
    //             ,{field: 'rhfId', title: '健康档案号', width: 110}
    //             ,{field: 'addrCountyName', title: '县', width: 60}
    //             ,{field: 'addrRuralName', title: '街道(乡镇)', width: 65}
    //             ,{field: 'addrVillageName', title: '社区(村)', width: 65}
    //             ,{field: 'patientName', title: '姓名', width: 60}
    //             ,{field: 'patientIdno', title: '身份证号码', width: 120}
    //             ,{field: 'patientTel', title:'联系电话',width: 60}
    //             ,{field: 'lowInsured', title:'低保户',width: 55}
    //             ,{field: 'poorHouseholds', title:'特困户',width: 55}
    //             ,{field: 'signState', title: '签约状态', width: 60}
    //             ,{field: 'isNotPoverty', title: '是否脱贫',width: 60 }
    //             ,{field: 'notSignReason', title: '未签原因',width:70}
    //             ,{field: 'sourceType', title: '来源',width:50}
    //             ,{field: 'delState', title: '状态',width:50}
    //             ,{field: 'signFromDate', title: '签约时间',width:100}
    //             ,{fixed: 'right', width:240, align:'center', toolbar: '#barRole'}
    //         ]]
    //         ,id: 'archiving'
    //         ,url: 'archivingCheck.action'
    //         ,where: {act: 'findArchivingWeb',strJson:JSON.stringify(qvo)}
    //         ,method: 'post'
    //         ,skin: 'row' //表格风格
    //         // ,size: 'sm'
    //         ,even: true
    //         ,page: true //是否显示分页
    //         ,limits: [5,10,25,50,100,500,1000,3000,5000,10000,30000,50000]
    //         ,limit: 10 //每页默认显示的数量
    //         ,done: function(res, curr, count){
    //             vos=res.data;
    //             if(vos != null){
    //                 numberCount=res.data.length;
    //             }
    //             //遮罩关闭
    //             layer.close(index);
    //
    //         }
    //     });
    // }
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
    var cancelStates = document.getElementsByName("cancelState");
    for(var i=0;i<cancelStates.length;i++){
        if(cancelStates[i].checked){
            cancelStates[i].checked = false;
        }
    }
    $("#todayStatee").hide();
    var todayStates = document.getElementsByName("toDayState");
    for(var i=0;i<todayStates.length;i++){
        if(todayStates[i].checked){
            todayStates[i].checked = false;
        }
    }
    var isHaveStates = document.getElementsByName("isHaveState");
    for(var i=0;i<isHaveStates.length;i++){
        if(isHaveStates[i].checked){
            isHaveStates[i].checked = false;
        }
    }

    var provincialInsurances = document.getElementsByName("provincialInsurance");
    for(var i=0;i<provincialInsurances.length;i++){
        if(provincialInsurances[i].checked){
            provincialInsurances[i].checked = false;
        }
    }

    $("#checkStartDate").val("");
    $("#checkEndDate").val("");
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
                            // var ccc = upHospAreaCode;
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
                            option.setAttribute("value", v.jcode);
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
    // qvo = encodeURI(encodeURI(qvo));
    var oppo=$("#patientNeighborhoodCommittee option:selected");//获取当前选择项.
    if(oppo.val() != ""){
        qvo["patientNeighborhoodCommittee"]= encodeURI(encodeURI(oppo.text()));
    }
    var patientName = $("#patientName").val();
    if(patientName != ""){
        qvo["patientName"] = encodeURI(encodeURI(patientName));
    }
    window.open("archivingCheck.action?act=pListPrintByPeoIds&typeNum="+num+"&strJson="+JSON.stringify(qvo)+"&numberCount="+numberCount);
    // window.location.href="<%=request.getContextPath()%>/archivingCheck.action?act=pListPrintByPeoIds&typeNum="+num;
    // window.location.href="<%=request.getContextPath()%>/archivingCheck.action?act=pListPrintByPeoIds&typeNum="+num+"&strJson="+JSON.stringify(qvo);
}

function forArchivingCheckAdd(ui){
    var id = ui.id;
    //判断是否签约，没有签约不可添加核查表
    if("已签约" == ui.signState){
        var url = "archivingCheck_add.jsp?id="+ui.id+"&handle=add";
        defualtHref(url);
    }else{
        layer.msg("该居民未签约，请先签约");
    }

}

function forArchivingCheckModify(ui) {
    if(ui.addState == "1"){
        var id=ui.id;
        var url = "archivingCheck_add.jsp?id="+ui.id+"&handle=modify";
        defualtHref(url);
    }else{
        layer.msg("该居民未新增核查表，请先新增");
    }

}
function forArchivingCheckLook(ui){
    var id = ui.id;
    var url = "archivingCheck_add.jsp?id="+ui.id+"&handle=look";
    defualtHref(url);
}

function forRevoke(ui) {
    var id=ui.id;
    //判断是不是删除的数据是的话不允许注销

    doAjaxPost('archivingAction.action?act=findDeleteState', {id:id}, function (data) {
        if(data.code=="900"){
            layer.msg("该数据为删除状态，不允许注销");
        }else{
            var url = "archiving_delete.jsp?id="+ui.id;
            defualtHref(url);
        }
    })
}

function findRole() {
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
                if(drRole == "2" || drRole == "3"){
                    var isHaveStates = document.getElementsByName("isHaveState");
                    for(var i=0;i<isHaveStates.length;i++){
                        isHaveStates[i].checked = true;
                    }
                }
            }
        }

    },function(data){
        layer.msg("级联初始化异常，请联系管理员！");
    })
}

/**
 * 查看居民健康档案
 * WangCheng
 * @param ui
 */
function findHealthRecordsArchive(ui) {
    var patientJmda = ui.rhfId;
    if (patientJmda == null) {
        layer.msg("该居民未建档，请先为该居民建档！");
    } else {
        //调用基卫健康档案界面
        if (HospAreaCode.substr(0, 4) == "3503") {//莆田
            //window.location.href = "http://18.1.3.28:7001/sqyl/logonAction.do?method=logon2&userid=csyh&passwd=c958d44fa3c5937507e0d2c06f63ae8f&df_id=" + patientJmda;
            window.open("http://18.1.3.28:7001/sqyl/logonAction.do?method=logon2&userid=csyh&passwd=c958d44fa3c5937507e0d2c06f63ae8f&df_id=" + patientJmda);
        } else if (HospAreaCode.substr(0, 4) == "3504") {//三明
            window.open("http://10.128.191.8:7007/sqyl/logonAction.do?method=logon2&userid=yxcxhfj&passwd=21218cca77804d2ba1922c33e0151105&df_id=" + patientJmda);
        } else if (HospAreaCode.substr(0, 4) == "3501") {//福州
            window.open("http://10.120.1.28:7001/sqyl/logonAction.do?method=logon2&userid=lyxyswsy&passwd=21218cca77804d2ba1922c33e0151105&df_id=" + patientJmda);
        } else if (HospAreaCode.substr(0, 4) == "3507") {//南平
            window.open("http://10.120.9.61:7006/sqyl/logonAction.do?method=logon2&userid=cswsy&passwd=21218cca77804d2ba1922c33e0151105&df_id=" + patientJmda);
        } else if (HospAreaCode.substr(0, 4) == "3505") {//泉州
            window.open("http://10.120.5.131:7005/sqyl/logonAction.do?method=logon2&userid=lcfyadmin&passwd=21218cca77804d2ba1922c33e0151105&df_id=" + patientJmda);
        }else if(HospAreaCode.substr(0,2) == "14"){//山西
            window.open("http://10.10.10.60:7101/sqyl/logonAction.do?method=logon2&userid=pdshzxp&passwd=21218cca77804d2ba1922c33e0151105&df_id=" + patientJmda);
        }
    }
}
function showToday(num){
    if(num == 1){
        $("#todayStatee").show();
    }else {
        var todayStates = document.getElementsByName("toDayState");
        for(var i=0;i<todayStates.length;i++){
            if(todayStates[i].checked){
                todayStates[i].checked = false;
            }
        }
        $("#todayStatee").hide();
    }
}