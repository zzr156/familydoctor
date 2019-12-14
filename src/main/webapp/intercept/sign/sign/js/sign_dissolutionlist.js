var vo={};
var qvo={};
var vos={};
var voexcel=[];
var table;
var laydate;
var numberCount = 0;

//莆田表格初始化
function ptInitTable() {
    var index = layer.load(1);
    table.render({
        height:'400'
        ,elem: '#signTabel'
        ,cols: [[
            {field: 'myIndex',title: '序号',width: 60,fixed: true}
            ,{fixed: 'xqname', width: 80, align: 'center', toolbar: '#barRolePtxq'}
            ,{field: 'name', title: '姓名', width: 60,sort: true}
            ,{field: 'sex', title: '性别', width: 60,sort: true}
            ,{field: 'patientIdno', title: '身份证', width: 150}
            ,{field: 'signDate', title: '签约时间', width: 90}
            ,{field: 'signFromDate', title: '协议开始时间', width: 90}
            ,{field: 'signToDateOther', title: '协议结束时间', width: 90}
            ,{field: 'signState', title: '签约状态', width: 120}
            ,{field: 'signSurrenderDate', title: '解约时间', width: 120}
            ,{field: 'signUrrenderReason', title: '解约原因', width: 120}
            ,{field: 'signSurrenderDate', title: '基卫操作时间', width: 120}
            ,{field: 'signOperatorName', title: '基卫操作人', width: 120}
            ,{field: 'signTeamName', title: '签约团队', width: 80}
            ,{field: 'signDrName', title: '签约医生', width: 80}
            ,{field: 'upHpis', title: '签约来源', width: 80}
            ,{fixed: 'right', width: 100, align: 'center', toolbar: '#barRolePt'}
        ]]
        ,id: 'sign'
        ,url: 'signAction.action'
        ,where: {act: 'findDisslution',strJson:JSON.stringify(qvo)}
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
                debugger
                numberCount=res.count;
                $.each(res.data,function(k,v){
                    if(v.signState=="预签约"){
                        $("td[data-field='signState'] :eq("+k+")").text("未交互");
                    }else if(v.signState=="已签约"){
                        $("td[data-field='signState'] :eq("+k+")").text("已交互");
                    }
                })
            }
            //遮罩关闭
            layer.close(index);
        }
    });
}

$(function(){
    var code = HospAreaCode.substring(0,4);
    if(code=="1405"){ // 山西高平
        $("#familyId").html("");
       // $("#familyId").append("<label class='layui-form-label'>查询家庭成员</label> <div class='layui-input-inline'><input type='checkbox' pofield='familysubpage' id='familysubpage' value='0' class='layui-input'></div>");
    }

    //文件导入导出功能仅限开放给莆田地区  add by WangCheng
    if(code != "3503"){
        debugger
        $("#ptExport").css("display","none");
        $("#ptImport").css("display","none");
    }

    //查询当前机构团队
    doAjaxPost('signAction.action?act=findTeam',{id:orgid},initBack);
    //南平居委会查询功能
    if(code=="3507"){
        $("#committee").show();
        doAjaxPost('signAction.action?act=findCommittee',{hospAreaCode:HospAreaCode},function(data){
            var committees = data.rows;
            if(committees != null){
                $.each(committees,function(k,v){
                    $("#patientNeighborhoodCommittee").append("<option value='"+$.trim(v.id)+"'>"+v.name+"</option>");
                })
            }
        })
    }
    //暂不要 级联查询
    //changecity();
});
function initBack(data) {
    $("#signHospId").val(orgid);
    $("#drHospName").val(orgid);
    if(data.map != null){
        //团队
        if(data.map.teamList!=null){
            $("#teamId").html("");
            var option = document.createElement('option');
            option.setAttribute("value","");
            option.innerText = "--全部--";
            document.getElementById("teamId").appendChild(option);
            $.each(data.map.teamList, function(k, v) {
                dataUiCodeGroup("select","teamId",v.teamName,v.id);
            });
        }
    }
    layui.use('table', function(){
        table = layui.table;

        table.on('tool(sign)', function(obj){
            var ui = obj.data; //获得当前行数据
            if(obj.event == 'renew'){ //进入续签
                renewSign(ui);
            }else if(obj.event=='look'){//查看
                findlook(ui);
            }

        });

        // findList();
    });

}
function addDr() {
    var options=$("#teamId option:selected");
    var value = $("#teamId").val();
    doAjaxPost('signAction.action?act=findMem',{id:value},findMenBack);
}
//医生
function findMenBack(data) {
    if(data != null){
        if(data.rows!=null){
            $("#drId").html("");
            var option = document.createElement('option');
            option.setAttribute("value","");
            option.innerText = "--全部--";
            document.getElementById("drId").appendChild(option);
            $.each(data.rows, function(k, v) {
                dataUiCodeGroup("select","drId",v.memDrName,v.memDrId);
            });

            $("#signDrAssistantId").html("");
            var option = document.createElement('option');
            option.setAttribute("value","");
            option.innerText = "--全部--";
            document.getElementById("signDrAssistantId").appendChild(option);
            $.each(data.rows, function(k, v) {
                dataUiCodeGroup("select","signDrAssistantId",v.memDrName,v.memDrId);
            });
        }
    }
}
//查询
function findList() {
    qvo = uiToData("form_qvo",qvo);//界面查询值绑定到变量
    if(HospAreaCode.substr(0,4)=="3503"){
        $("#pts_").show();
        $("#pt_").show();
        ptInitTable();
    }else{

        var index = layer.load(1);
        table.render({
            height:'400'
            ,elem: '#signTabel'
            ,cols: [[
                 {field: 'myIndex',title: '序号',width: 60,fixed: true}
                ,{field: 'name', title: '姓名', width: 60,sort: true}
                ,{field: 'sex', title: '性别', width: 60,sort: true}
                ,{field: 'patientIdno', title: '身份证', width: 150}
                ,{field: 'signDate', title: '签约时间', width: 90}
                ,{field: 'signState', title: '签约状态', width: 120}
                ,{field: 'signSurrenderDate', title: '解约时间', width: 120}
                ,{field: 'signUrrenderReason', title: '解约原因', width: 120}
                ,{field: 'signSurrenderDate', title: '基卫操作时间', width: 120}
                ,{field: 'signOperatorName', title: '基卫操作人', width: 120}
                ,{field: 'signTeamName', title: '签约团队', width: 80}
                ,{field: 'signDrName', title: '签约医生', width: 80}
                ,{field: 'upHpis', title: '签约来源', width: 80}
                ,{fixed: 'right', width: 200, align: 'center', toolbar: '#barRole'}
            ]]
            ,id: 'sign'
            ,url: 'signAction.action'
            ,where: {act: 'findDisslution',strJson:JSON.stringify(qvo)}
            ,method: 'post'
            ,skin: 'row' //表格风格
            // ,size: 'sm'
            ,even: true
            ,page: true //是否显示分页
            ,limits: [5,10,25,50,100,500,1000,3000,5000,10000,30000,50000]
            ,limit: 10 //每页默认显示的数量
            ,done: function(res, curr, count){
                vos=res.data;
                if(vos != null ){
                    numberCount=res.count;
                }
                //遮罩关闭
                layer.close(index);
            }
        });

    }
}

/**
 * 续签
 * WangCheng
 * @param ui
 */
function renewSign(ui) {
    var id=ui.id;
    //续签的时候判断是不是建档立卡人群
    vo["ptidno"] = ui.patientIdno;
    vo["dissolutionType"] = "4";
    doAjaxPost('signAction.action?act=findRenew', {strJson: JSON.stringify(vo)}, function (data) {
        if(data.msg == null){
            doAjaxPost('signAction.action?act=findArchivingCardPeople', {strJson: JSON.stringify(vo)}, function (data) {
                if(data.map != null){
                    if (data.map.result == "1") {
                        var url = "sign_modify.jsp?id="+ui.id+"&renew="+$("#signrenew").val()+"&economicType=2&dissolution=1";
                    }else {
                        var url = "sign_modify.jsp?id="+ui.id+"&renew="+$("#signrenew").val()+"&dissolution=1";
                    }
                    defualtHref(url);
                }
            });
        }else {
            layer.msg("该居民已续签，请勿重复续签")
        }
    });
}

function  findlook(ui) {
    if(ui.id !=null && ui.id!=""){
        window.location.href= "sign_look.jsp?id=" +
            ""+ui.id+"&type="+true;
    }
}
function lookprotocol(ui){
    if(necs(ui.patientId) && necs(ui.teamId)){
        window.location.href="sign_protocol.jsp?teamId="+ui.teamId+"&patientId="+ui.patientId+"&drId="+ui.signDrId+"&type="+true;
    }
}
function signsxcz(){
    $("#patientName").val("");
    $("#patientIdno").val("");
    $("#patientjmda").val("");
    $("#signzfpay").val("");
    $("#signDateStart").val("");
    $("#signDateEnd").val("");
    $("#signlx").val("");
    $("#teamId").val("");
    $("#drId").val("");
    $("#signDrAssistantId").val("");

    var signsJjTypes = document.getElementsByName("signsJjTypes");
    for(var i=0;i<signsJjTypes.length;i++){
        if(signsJjTypes[i].checked){
            signsJjTypes[i].checked = false;
        }
    }
    var persGroup = document.getElementsByName("persGroup");
    for(var i=0;i<persGroup.length;i++){
        if(persGroup[i].checked){
            persGroup[i].checked = false;
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
                if(v.state!="0"){
                    var option = document.createElement('option');
                    option.setAttribute("value", v.id);
                    option.innerText = v.name;
                    document.getElementById("patientCity").appendChild(option);
                }
            });
        }
    },function(data){
        layer.msg("级联初始化异常，请联系管理员！");
    })
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

/**
 * 导出全部
 * WangCheng
 */
function exportDissolutionDate() {
    qvo = uiToData("form_qvo",qvo);//界面查询值绑定到变量
    //防止中文乱码
    var patientAddress = $("#patientAddress").val();
    if(patientAddress!=null && patientAddress!=""){
        var patientAddress = encodeURI(encodeURI(patientAddress));
        qvo["patientAddress"]= patientAddress;
    }
    var patientName = $("#patientName").val();
    if(patientName!=null && patientName!=""){
        var patientName = encodeURI(encodeURI(patientName));
        qvo["patientName"]= patientName;
    }
    qvo["numberCount"] = numberCount;
    window.open("signAction.action?act=exportDissolutionData&strJson="+JSON.stringify(qvo));
}

function changesign(e){
    if(e.signDrId==drid){
        layer.open({
            type: 2,
            area: ['700px', '450px'],
            fixed: false, //不固定
            maxmin: true,
            content: "sign_change.jsp?id="+e.id+"&signDrId="+e.signDrId+"&patientId="+e.patientId+"&teamId="+e.teamId+"&drName="+e.signDrName
        });

    }else {
        layer.msg("该居民不属于您的团队，您无权限变更！");
    }


}

//解除签约
function surrenderSign(e) {
        if(e.signDrId==drid){
            var patientjmda = "";
            if(e.patientjmda!=undefined && e.patientjmda !=null){
                patientjmda = e.patientjmda;
            }
            layer.open({
                type: 2,
                area: ['700px', '450px'],
                fixed: false, //不固定
                maxmin: true,
                content: "sign_surrender.jsp?id="+e.id+"&signDrId="+e.signDrId+"&patientId="+e.patientId+"&teamId="+e.teamId+"&drName="+e.signDrName+"&patientjmda="+patientjmda
            });

        }else {
            layer.msg("该居民不属于您的团队，您无权限解约！");
        }
}

/**
 * 导入文件
 * WangCheng
 */
function importFile(){
    if($("#upExcel").val() != ""){
        var fileType = $("#upExcel").val().split(".");
        if(fileType[1]==undefined || (fileType[1]!='xls')){
            layer.open({
                skin: 'layui-layer-molv',
                closeBtn: 0,
                title: false,
                content:'上传的文件类型不正确（文件格式应该为xls）!' ,
                anim: 5 ,
                btn: ['关闭']
            });
            return false;
        }
        $.ajaxFileUpload({  //Jquery插件上传文件
            url: "signAction.action?act=importFile&orgId="+orgid,
            secureuri: false,//是否启用安全提交  默认为false
            fileElementId: "upExcel", //type="file"的id
            //contentType: "application/json",
            dataType: "JSON",  //返回值类型
            success: function (data, status) {
                var result = JSON.parse(data);
                layer.msg(result.msg);
            },error: function (data, status, e) {//服务器响应失败处理函数
                layer.msg(e);
            }
        });
    }else {
        layer.msg("请选择你需要上传的EXCEl文件！");
    }
}