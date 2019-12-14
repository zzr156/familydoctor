/**
 * Created by zzm on 2018/8/13.
 */
var vo={};
var qvo={"renew":"1"};
var vos={};
var voexcel=[];
var table;
var laydate;
var numberCount = 0;
/*layui.use('laydate', function(){
 laydate = layui.laydate;
 laydate.render({
 elem: '#signDateStart'
 });
 laydate.render({
 elem: '#signDateEnd'
 });
 });*/
//莆田表格初始化
function ptInitTable() {
    var index = layer.load(1);
    table.render({
        height:'400'
        ,elem: '#newSignTabel'
        ,cols: [[
            {field: 'myIndex',title: '序号',width: 60,fixed: true}
            ,{field: 'signNum', title: '签约编号', width: 100,sort: true}
            ,{field: 'name', title: '姓名', width: 60,sort: true}
            ,{field: 'sex', title: '性别', width: 60,sort: true}
            ,{field: 'age', title: '年龄', width: 60}
            ,{field: 'patientIdno', title: '身份证', width: 150}
            ,{field:'tel', title: '联系电话', width: 100}
            ,{field: 'patientAddress', title: '详细地址', width: 150}
            ,{field:'signPersGroup', title: '服务类型', width: 100}
            ,{field: 'signsJjType', title: '人口经济性质', width: 100}
            ,{field: 'signlx', title: '医保类型', width: 80}
            ,{field: 'signDate', title: '续签日期', width: 80}
            ,{field: 'signToDate', title: '续签协议日期', width: 180}
            ,{field: '', title: '签约状态', width: 90,templet:'<div>已签约</div>'}
            ,{field: 'signState', title: '医保交互', width: 80}
            ,{field: 'signTeamName', title: '签约团队', width: 80}
            ,{field: 'signDrName', title: '签约医生', width: 80}
            ,{field: 'batchOperatorName', title: '签约操作人', width: 80}
            ,{field: 'signgwpay', title: '公卫补助', width: 80}
            ,{field: 'signybpay', title: '医保预支付', width: 80}
            ,{field: 'signczpay', title: '财政补贴', width: 80}
            ,{field: 'signzfpay', title: '自费', width: 80}
            ,{field: 'upHpis', title: '签约来源', width: 80}
            ,{fixed: 'right', width:340, align:'center', toolbar: '#newBarRole'}
        ]]
        ,id: 'sign_look'
        ,url: 'signAction.action'
        ,where: {act: 'findSignXxWeb',strJson:JSON.stringify(qvo)}
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
    //查询当前机构团队
    doAjaxPost('signAction.action?act=findTeam',{id:orgid},initBack);
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
        table.on('tool(sign_look)', function(obj){
            var ui = obj.data; //获得当前行数据
           if(obj.event=='look'){//
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
    qvo.renew='1';
    if(HospAreaCode.substr(0,4)=="3503"){
        $("#pts_").show();
        $("#pt_").show();
        ptInitTable();
    }else{
        var index = layer.load(1);
        table.render({
            height:'400'
            ,elem: '#newSignTabel'
            ,cols: [[
                {field: 'myIndex',title: '序号',width: 60,fixed: true}
                ,{field: 'signNum', title: '签约编号', width: 140,sort: true}
                ,{field: 'name', title: '姓名', width: 60,sort: true}
                ,{field: 'sex', title: '性别', width: 60,sort: true}
                ,{field: 'age', title: '年龄', width: 60}
                ,{field: 'patientIdno', title: '身份证', width: 150}
                ,{field:'tel', title: '联系电话', width: 100}
                ,{field: 'patientAddress', title: '详细地址', width: 150}
                ,{field: 'signDate', title: '续签日期', width: 80}
                ,{field: 'signToDate', title: '续签协议日期', width: 180}
                ,{field:'signPersGroup', title: '服务类型', width: 100}
                ,{field: 'signsJjType', title: '人口经济性质', width: 100}
                ,{field: 'signlx', title: '医保类型', width: 80}
                /* ,{field: 'signDate', title: '签约时间', width: 90}*/
                ,{field: 'signState', title: '签约状态', width: 80}
                ,{field: 'signTeamName', title: '签约团队', width: 80}
                ,{field: 'signDrName', title: '签约医生', width: 80}
                ,{field: 'batchOperatorName', title: '签约操作人', width: 80}
                ,{field: 'signgwpay', title: '公卫补助', width: 80}
                ,{field: 'signybpay', title: '医保预支付', width: 80}
                ,{field: 'signczpay', title: '财政补贴', width: 80}
                ,{field: 'signzfpay', title: '自费', width: 80}
                ,{field: 'upHpis', title: '签约来源', width: 80}
                ,{fixed: 'right', width:140, align:'center', toolbar: '#newBarRole'}
            ]]
            ,id: 'sign_look'
            ,url: 'signAction.action'
            ,where: {act: 'findSignXxWeb',strJson:JSON.stringify(qvo)}
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



function  findlook(ui) {
    if(ui.id !=null && ui.id!=""){
        window.location.href= "sign_look.jsp?id="+ui.id+"&type="+true;
    }
}
function lookprotocol(ui){
    if(necs(ui.patientId) && necs(ui.teamId)){
        window.location.href="sign_protocol.jsp?teamId="+ui.teamId+"&patientId="+ui.patientId+"&type="+true;
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

