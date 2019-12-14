var vo = {};
var qvo = {};
var vos = {};
var voexcel = [];
var table;
var laydate;
var numberCount = 0;
var drRoleResult = [];

//莆田表格初始化
function ptInitTable() {
    var index = layer.load(1);
    table.render({
        height: '400'
        , elem: '#signTabel'
        , cols: [[
            {field: 'myIndex', title: '序号', width: 60, fixed: true}
            , {field: 'signNum', title: '签约编号', width: 100, sort: true}
            , {field: 'name', title: '姓名', width: 60, sort: true}
            , {field: 'sex', title: '性别', width: 60, sort: true}
            , {field: 'age', title: '年龄', width: 60, templet: '<div>{{  GetAge(d.patientIdno)}}</div>'}
            , {field: 'patientIdno', title: '身份证', width: 150}
            , {field: 'tel', title: '联系电话', width: 100}
            , {field: 'patientAddress', title: '详细地址', width: 150}
            , {field: 'signPersGroup', title: '服务类型', width: 100}
            , {field: 'signsJjType', title: '人口经济性质', width: 100}
            , {field: 'signlx', title: '医保类型', width: 80}
            , {field: 'signDate', title: '签约时间', width: 90}
            , {field: '', title: '签约状态', width: 90, templet: '<div>已签约</div>'}
            , {field: 'signState', title: '医保交互', width: 80}
            , {field: 'signTeamName', title: '签约团队', width: 80}
            , {field: 'signDrName', title: '签约医生', width: 80}
            , {field: 'batchOperatorName', title: '签约操作人', width: 80}
            , {field: 'batchChangeOperatorName', title: '变更操作人', width: 80}
            , {field: 'signgwpay', title: '公卫补助', width: 80}
            , {field: 'signybpay', title: '医保预支付', width: 80}
            , {field: 'signczpay', title: '财政补贴', width: 80}
            , {field: 'signzfpay', title: '自费', width: 80}
            , {field: 'upHpis', title: '签约来源', width: 80}
            , {fixed: 'right', width: 440, align: 'center', toolbar: '#barRole'}
        ]]
        , id: 'sign'
        , url: 'signAction.action'
        , where: {act: 'findSignXxWeb', strJson: JSON.stringify(qvo)}
        , method: 'post'
        , skin: 'row' //表格风格
        // ,size: 'sm'
        , even: true
        , page: true //是否显示分页
        , limits: [5, 10, 25, 50, 100, 500, 1000, 3000, 5000, 10000, 30000, 50000]
        , limit: 10 //每页默认显示的数量
        , done: function (res, curr, count) {
            vos = res.data;
            if(vos != null){
                numberCount = res.data.length;
                $.each(res.data, function (k, v) {
                    if (v.signState == "预签约") {
                        $("td[data-field='signState'] :eq(" + k + ")").text("未交互");
                    } else if (v.signState == "已签约") {
                        $("td[data-field='signState'] :eq(" + k + ")").text("已交互");
                    }
                })
            }
            //遮罩关闭
            layer.close(index);
        }
    });
}

$(function () {
    var code = HospAreaCode.substring(0, 4);

    //页面初始化默认起始时间为当前时间的前一个月、add by WangCheng
    initSignDateSatrt(code);//初始化签约起始日期

    if (code == "1405") { // 山西高平
        $("#familyId").html("");
        // $("#familyId").append("<label class='layui-form-label'>查询家庭成员</label> <div class='layui-input-inline'><input type='checkbox' pofield='familysubpage' id='familysubpage' value='0' class='layui-input'></div>");
    }
    //查询当前机构团队
    doAjaxPost('signAction.action?act=findTeam', {id: orgid}, initBack);
    //南平居委会查询功能
    // if (code == "3507") {
        $("#committee").show();
        doAjaxPost('signAction.action?act=findCommittee', {hospAreaCode: HospAreaCode}, function (data) {
            var committees = data.rows;
            if (committees != null) {
                $.each(committees, function (k, v) {
                    $("#patientNeighborhoodCommittee").append("<option value='" + $.trim(v.id) + "'>" + v.areaSname + "</option>");
                });
            }
        })
    // }
    //暂不要 级联查询
    //changecity();
    //判断drRole，包含1的话是可查看全身签约数据，2全市，3全区，49医院,add by WangCheng
    if(drRole != null && drRole != ""){
        drRoleResult = drRole.split(";");
        debugger
        if($.inArray("1",drRoleResult) > -1){//包含1(省)
            initCity();//初始化市级
        }else if($.inArray("2",drRoleResult) > -1){//包含2（市）
            initArea();//初始化区县
        }else if($.inArray("3",drRoleResult) > -1){//包含3（区县）
            initHospital();//初始化社区
        }else if($.inArray("4",drRoleResult) > -1 || $.inArray("9",drRoleResult) > -1){//包含4或9（医院）
            initAllLevel();//初始化市级、区县、社区
        }
    }
});

function initSignDateSatrt(code){
    if("3503" == code){
        vo["signAreaCode"] = code;
        doAjaxPost('signAction.action?act=findSignStartSate', {strJson: JSON.stringify(vo)}, function (data) {
            if(data.vo != null){
                if(data.vo.findState == "1"){//1是有开启
                    var nowDate = new Date();
                    var startYear = nowDate.getFullYear();
                    var startMonth = nowDate.getMonth() + 1 + "";
                    if(startMonth.length == 1){
                        startMonth = "0" + startMonth;
                    }
                    var startDay = nowDate.getDate() + "";
                    if(startDay.length == 1){
                        startDay = "0" + startDay;
                    }
                    $("#signDateStart").text(startYear + "-" + startMonth + "-" + startDay);
                }
            }
        });
    }
}

function initBack(data) {
    $("#signHospId").val(orgid);
    $("#drHospName").val(orgid);
    if (data.map != null) {
        //团队
        if (data.map.teamList != null) {
            $("#teamId").html("");
            var option = document.createElement('option');
            option.setAttribute("value", "");
            option.innerText = "--全部--";
            document.getElementById("teamId").appendChild(option);
            $.each(data.map.teamList, function (k, v) {
                dataUiCodeGroup("select", "teamId", v.teamName, v.id);
            });
        }
    }
    layui.use('table', function () {
        table = layui.table;

        table.on('tool(sign)', function (obj) {
            var ui = obj.data; //获得当前行数据
            if (obj.event == 'modify') { //进入修改
                forTeamModify(ui);
            } else if (obj.event == 'look') {//
                findlook(ui);
            } else if (obj.event == 'print') {//
                lookprotocol(ui);
            } else if (obj.event == "findHealthRecords") {//add by WangCheng
                findHealthRecords(ui);
            } else if (obj.event == 'changesign') {//变更团队
                changesign(ui);
            } else if (obj.event == 'surrenderSign') {//解约
                surrenderSign(ui);
            }

        });

        //findList();
    });

}
function addDr() {
    var options = $("#teamId option:selected");
    var value = $("#teamId").val();
    doAjaxPost('signAction.action?act=findMem', {id: value}, findMenBack);
}
//医生
function findMenBack(data) {
    if (data != null) {
        if (data.rows != null) {
            $("#drId").html("");
            var option = document.createElement('option');
            option.setAttribute("value", "");
            option.innerText = "--全部--";
            document.getElementById("drId").appendChild(option);
            $.each(data.rows, function (k, v) {
                dataUiCodeGroup("select", "drId", v.memDrName, v.memDrId);
            });

            $("#signDrAssistantId").html("");
            var option = document.createElement('option');
            option.setAttribute("value", "");
            option.innerText = "--全部--";
            document.getElementById("signDrAssistantId").appendChild(option);
            $.each(data.rows, function (k, v) {
                dataUiCodeGroup("select", "signDrAssistantId", v.memDrName, v.memDrId);
            });
        }
    }
}
//查询
function findList() {
    qvo = uiToData("form_qvo", qvo);//界面查询值绑定到变量
    //判断级别
    if($("#hospitalLevel option:selected").val() != ""){//查看的是社区级数据
        qvo["signAreaCode"]=$("#hospitalLevel option:selected").val();
    }else{
        if($("#areaLevel option:selected").val() != ""){//查看的是区县级数据
            qvo["signAreaCode"]=$("#areaLevel option:selected").val().substring(0,6);
        }else {
            if($("#cityLevel option:selected").val() != ""){//查看的是市级数据
                qvo["signAreaCode"]=$("#cityLevel option:selected").val().substring(0,4);
            }else {
                qvo["signAreaCode"]=HospAreaCode.substring(0,2);
            }
        }
    }
    if (HospAreaCode.substr(0, 4) == "3503") {
        //$("#pts_").show();
        $("#pt_").show();
        ptInitTable();
    } else {
        var index = layer.load(1);
        table.render({
            height: '400'
            , elem: '#signTabel'
            , cols: [[
                {field: 'myIndex', title: '序号', width: 60, fixed: true}
                , {field: 'signNum', title: '签约编号', width: 140, sort: true}
                , {field: 'name', title: '姓名', width: 60, sort: true}
                , {field: 'sex', title: '性别', width: 60, sort: true}
                , {field: 'age', title: '年龄', width: 60, templet: '<div>{{  GetAge(d.patientIdno)}}</div>'}
                , {field: 'patientIdno', title: '身份证', width: 150}
                , {field: 'tel', title: '联系电话', width: 100}
                , {field: 'patientAddress', title: '详细地址', width: 150}
                , {field: 'signPersGroup', title: '服务类型', width: 100}
                , {field: 'signsJjType', title: '人口经济性质', width: 100}
                , {field: 'signlx', title: '医保类型', width: 80}
                , {field: 'signDate', title: '签约时间', width: 90}
                , {field: 'signState', title: '签约状态', width: 120}
                , {field: 'signTeamName', title: '签约团队', width: 80}
                , {field: 'signDrName', title: '签约医生', width: 80}
                , {field: 'batchOperatorName', title: '签约操作人', width: 80}
                , {field: 'batchChangeOperatorName', title: '变更操作人', width: 80}
                , {field: 'signgwpay', title: '公卫补助', width: 80}
                , {field: 'signybpay', title: '医保预支付', width: 80}
                , {field: 'signczpay', title: '财政补贴', width: 80}
                , {field: 'signzfpay', title: '自费', width: 80}
                , {field: 'upHpis', title: '签约来源', width: 80}
                , {fixed: 'right', width: 440, align: 'center', toolbar: '#barRole'}
            ]]
            , id: 'sign'
            , url: 'signAction.action'
            , where: {act: 'findSignXxWeb', strJson: JSON.stringify(qvo)}
            , method: 'post'
            , skin: 'row' //表格风格
            // ,size: 'sm'
            , even: true
            , page: true //是否显示分页
            , limits: [5, 10, 25, 50, 100, 500, 1000, 3000, 5000, 10000, 30000, 50000]
            , limit: 10 //每页默认显示的数量
            , done: function (res, curr, count) {

                if (res.data != null) {
                    vos = res.data;
                    numberCount = res.data.length;
                }

            }
        });
        //遮罩关闭
        layer.close(index);

    }
}
/*var signlx={
 // undefined:'',
 null:'',
 999999:"",
 1:"医保",
 2:"农合",
 3:"未参保"
 }
 var signState={
 // undefined:'',
 //  null:'',
 0:'预签约',
 1:'待签约',
 2:'已签约',
 3:'解约中',
 4:'已解约',
 5:'改签解约中',
 6:'改签申请中',
 7:'已退约',
 8:'拒签',
 9:'删除'
 }
 var signsJjType={
 //   null:'',
 999999:"",
 0:'',
 1:'一般人口',
 2:'建档立卡贫困人口',
 3:'低保户',
 4:'特困户（五保户）',
 5:'计生特殊家庭'
 }
 var sex={
 null:'',
 999999:"",
 1:'男',
 2:'女'
 }*/
//导出当前页
/*function exportCurrent() {
 var option={};
 option.fileName = '签约列表';
 $.each(vos,function(k,v){
 vos[k].signlx=signlx[vos[k].signlx];
 vos[k].signState=signState[vos[k].signState];
 })
 option.datas=[
 {
 sheetData:vos,
 sheetName:'sheet',
 sheetFilter:['myIndex','signNum','name','age','patientIdno','tel','signsJjType','signlx','signDate','signState','signczpay','signzfpay','signDrName'],
 sheetHeader:['序号','签约编号','姓名','年龄','身份证','联系电话','人口经济性质','医保类型','签约时间','签约状态','财政补贴','自费','签约医生']
 }
 ];
 var toExcel=new ExportJsonExcel(option);
 toExcel.saveExcel();
 }*/

function forTeamModify(ui) {
    var id = ui.id;
    var orgid = ui.teamHospId;
    var url = "sign_modify.jsp?id=" + ui.id;
    //window.location.href= "sign_modify.jsp?id="+ui.id;
    defualtHref(url);
}
function findlook(ui) {
    if (ui.id != null && ui.id != "") {
        window.location.href = "sign_look.jsp?id=" +
            "" + ui.id + "&type=" + true;
    }
}
function lookprotocol(ui) {
    if (necs(ui.patientId) && necs(ui.teamId)) {
        window.location.href = "sign_protocol.jsp?teamId=" + ui.teamId + "&patientId=" + ui.patientId + "&drId=" + ui.signDrId + "&type=" + true+"&signId="+ui.id;
    }
}
function signsxcz() {
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
    $("#signFromDateStart").val("");
    $("#signFromDateEnd").val("");
    $("#signToDateStart").val("");
    $("#signToDateEnd").val("");

    var signsJjTypes = document.getElementsByName("signsJjTypes");
    for (var i = 0; i < signsJjTypes.length; i++) {
        if (signsJjTypes[i].checked) {
            signsJjTypes[i].checked = false;
        }
    }
    var persGroup = document.getElementsByName("persGroup");
    for (var i = 0; i < persGroup.length; i++) {
        if (persGroup[i].checked) {
            persGroup[i].checked = false;
        }
    }


}


<!-- 市级联 -->
function changecity() {
    vo["upId"] = "350000000000";
    doAjaxPost('manageCounAction.action?act=appAddressResult', {strJson: JSON.stringify(vo)}, function (data) {
        $("#patientCity").html("");

        var option1 = document.createElement('option');
        option1.setAttribute("value", "");
        option1.innerText = "--请选择--";
        document.getElementById("patientCity").appendChild(option1);
        if (data != null) {
            $.each(data.rows, function (k, v) {
                if (v.state != "0") {
                    var option = document.createElement('option');
                    option.setAttribute("value", v.id);
                    option.innerText = v.name;
                    document.getElementById("patientCity").appendChild(option);
                }
            });
        }
    }, function (data) {
        layer.msg("级联初始化异常，请联系管理员！");
    })
}
<!-- 区县级联 -->
function changecounty() {
    if ($("#patientCity").val() != null && $("#patientCity").val() != "") {
        vo["upId"] = $("#patientCity").val();
        doAjaxPost('manageCounAction.action?act=appAddressResult', {strJson: JSON.stringify(vo)}, function (data) {
            $("#patientArea").html("");

            var option1 = document.createElement('option');
            option1.setAttribute("value", "");
            option1.innerText = "--请选择--";
            document.getElementById("patientArea").appendChild(option1);
            if (data != null) {
                $.each(data.rows, function (k, v) {
                    if (v.state != "0") {
                        var option = document.createElement('option');
                        option.setAttribute("value", v.id);
                        option.innerText = v.name;
                        document.getElementById("patientArea").appendChild(option);
                    }
                });
            }
        }, function (data) {
            layer.msg("级联初始化异常，请联系管理员！");
        })
    }
}

function changestreet() {
    if ($("#patientArea").val() != null && $("#patientArea").val() != "") {
        vo["upId"] = $("#patientArea").val();
        doAjaxPost('manageCounAction.action?act=appAddressResult', {strJson: JSON.stringify(vo)}, function (data) {
            $("#patientStreet").html("");

            var option1 = document.createElement('option');
            option1.setAttribute("value", "");
            option1.innerText = "--请选择--";
            document.getElementById("patientStreet").appendChild(option1);
            if (data != null) {
                $.each(data.rows, function (k, v) {
                    if (v.state != "0") {
                        var option = document.createElement('option');
                        option.setAttribute("value", v.id);
                        option.innerText = v.name;
                        document.getElementById("patientStreet").appendChild(option);
                    }
                });
            }
        }, function (data) {
            layer.msg("级联初始化异常，请联系管理员！");
        })
    }

}
//居委会级联
function changeCommittee() {
    if ($("#patientStreet").val() != null && $("#patientStreet").val() != "") {
        vo["upId"] = $("#patientStreet").val();
        doAjaxPost('manageCounAction.action?act=appAddressResult', {strJson: JSON.stringify(vo)}, function (data) {
            $("#patientNeighborhoodCommittee").html("");
            var option1 = document.createElement('option');
            option1.setAttribute("value", "");
            option1.innerText = "--请选择--";
            document.getElementById("patientNeighborhoodCommittee").appendChild(option1);
            if (data != null && data.rows != null) {
                $.each(data.rows, function (k, v) {
                    if (v.state != "0") {
                        var option = document.createElement('option');
                        option.setAttribute("value", v.id);
                        option.innerText = v.name;
                        document.getElementById("patientNeighborhoodCommittee").appendChild(option);
                    }
                });
            }
        }, function (data) {
            layer.msg("级联初始化异常，请联系管理员！");
        })
    }
}


//导出当前页
function exportCurrent(num) {
    qvo = uiToData("form_qvo", qvo);//界面查询值绑定到变量
    //防止中文乱码
    var patientAddress = $("#patientAddress").val();
    if (patientAddress != null && patientAddress != "") {
        var add = encodeURI(encodeURI(patientAddress));
        qvo["patientAddress"] = add;
    }
    var batchOperatorName = $("#batchOperatorName").val();
    if (batchOperatorName != null && batchOperatorName != "") {
        var czrname = encodeURI(encodeURI(batchOperatorName));
        qvo["batchOperatorName"] = czrname;
    }
    var patientName = $("#patientName").val();
    if (patientName != null && patientName != "") {
        var ptname = encodeURI(encodeURI(patientName));
        qvo["patientName"] = ptname;
    }
    window.open("signAction.action?act=findSignXxWebExcel&typeNum=" + num + "&strJson=" + JSON.stringify(qvo) + "&numberCount=" + numberCount);
}

function changesign(e) {

    /*  layer.open({
     type: 1
     ,title: false //不显示标题栏
     ,closeBtn: false
     ,area: ['600px', '500px']
     ,shade: 0.8
     ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
     ,resize: false
     ,btn: ['确定','取消']//,'发票打印'
     ,btnAlign: 'c'
     ,moveType: 1 //拖拽模式，0或者1
     ,content: '<div style="padding: 50px; line-height: 300px; background-color: #f2f2f2; color: #000; font-weight: 300;"></div>'
     ,yes: function(index,layero){
     // window.location.href= "sign_look.jsp?id="+data.vo.id+"&type="+true+"&typeadd="+true+"&addtpye="+addtpye;
     }
     /!*,btn2:function () {

     window.location.href="sign_protocol.jsp?teamId="+data.vo.signTeamId+"&PatientId="+data.vo.signPatientId;
     //window.open("protocol3.jsp?signdrid="+data.vo.signDr+"&ids="+data.vo.id+"&hospId="+hospId+"&city="+data.vo.ptCityName+"&county="+data.vo.ptCountyName+"&street="+data.vo.ptStreet+"&hospName="+hospName);
     }*!/
     });*/

    if (e.signDrId == drid) {
        doAjaxPost('signAction.action?act=renewState', {signId: e.id}, function (data) {
            if(data.code == "900"){
                layer.msg("该签约单已转签，不可变更！");
            }else if(data.code == "1000"){
                layer.msg("该签约单已续签，不可变更！");
            }else{
                layer.open({
                    type: 2,
                    area: ['700px', '450px'],
                    fixed: false, //不固定
                    maxmin: true,
                    content: "sign_change.jsp?id=" + e.id + "&signDrId=" + e.signDrId + "&patientId=" + e.patientId + "&teamId=" + e.teamId + "&drName=" + encodeURI(encodeURI(e.signDrName))+"&batchOperatorName=" + encodeURI(encodeURI(e.batchOperatorName))+"&batchOperatorId=" +e.batchOperatorId


                });
            }
        })
        /*layer.open({
            type: 2,
            area: ['700px', '450px'],
            fixed: false, //不固定
            maxmin: true,
            content: "sign_change.jsp?id=" + e.id + "&signDrId=" + e.signDrId + "&patientId=" + e.patientId + "&teamId=" + e.teamId + "&drName=" + encodeURI(encodeURI(e.signDrName))+"&batchOperatorName=" + encodeURI(encodeURI(e.batchOperatorName))+"&batchOperatorId=" +e.batchOperatorId


        });*/
    } else {
        layer.msg("该居民不属于您的团队，您无权限变更！");
    }


}

//解除签约
function surrenderSign(e) {
    //if(e.signPayState=="0"&& (e.signState=="已签约未缴费" || e.signState=="未交互")){
    if (e.signDrId == drid) {
        doAjaxPost("signAction.action?act=renewState", {signId: e.id}, function (data) {
            if(data.code == "800"){
                var patientjmda = "";
                if (e.patientjmda != undefined && e.patientjmda != null) {
                    patientjmda = e.patientjmda;
                }
                layer.open({
                    type: 2,
                    title: '解约 - ' + e.name,
                    area: ['700px', '380px'],
                    fixed: false, //不固定
                    maxmin: true,
                    content: "sign_surrender.jsp?id=" + e.id + "&signDrId=" + e.signDrId + "&patientId=" + e.patientId + "&teamId=" + e.teamId + "&drName=" + encodeURI(encodeURI(e.signDrName)) + "&patientjmda=" + patientjmda
                });
            }else if(data.code == "1000"){
                //解约判断是否已经续签
                layer.confirm('该签约单已经续签,是否继续解约?', {
                 btn: ['否', '是'],
                 btn2: function () {
                     var patientjmda = "";
                     if (e.patientjmda != undefined && e.patientjmda != null) {
                         patientjmda = e.patientjmda;
                     }
                     layer.open({
                         type: 2,
                         title: '解约 - ' + e.name,
                         area: ['700px', '380px'],
                         fixed: false, //不固定
                         maxmin: true,
                         content: "sign_surrender.jsp?id=" + e.id + "&signDrId=" + e.signDrId + "&patientId=" + e.patientId + "&teamId=" + e.teamId + "&drName=" + encodeURI(encodeURI(e.signDrName)) + "&patientjmda=" + patientjmda
                     });
                 }
                 });
            }
        })

       /* var patientjmda = "";
        if (e.patientjmda != undefined && e.patientjmda != null) {
            patientjmda = e.patientjmda;
        }
        layer.open({
            type: 2,
            area: ['700px', '450px'],
            fixed: false, //不固定
            maxmin: true,
            content: "sign_surrender.jsp?id=" + e.id + "&signDrId=" + e.signDrId + "&patientId=" + e.patientId + "&teamId=" + e.teamId + "&drName=" + encodeURI(encodeURI(e.signDrName)) + "&patientjmda=" + patientjmda
        });*/

    } else {
        layer.msg("该居民不属于您的团队，您无权限解约！");
    }
    /* }else{
     layer.msg("该居民已签约已登记或已签约已缴费，您无权限解约！");
     }*/
}

/**
 * 查看居民健康档案
 * WangCheng
 * @param ui
 */
function findHealthRecords(ui) {
    var patientJmda = ui.patientjmda;
    if (patientJmda == null) {
        layer.msg("该居民未建档，请先为该居民建档！");
    } else {
        // 保存健康档案调阅记录
        if (HospAreaCode.substr(0, 4) == "3503") {// 莆田
            doAjaxPost("signAction.action?act=addReadFileLog&patientId=" + ui.patientId + "&readWay=1", vo, null);
        }

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
        } else if (HospAreaCode.substr(0, 4) == "3506") {//漳州
            window.open("http://10.120.6.29:7001/sqyl/logonAction.do?method=logon2&userid=cswsy&passwd=8e35877fb272226206ea702a55c64e83&df_id=" + patientJmda);
        }else if(HospAreaCode.substr(0,2) == "14"){//山西
            // window.open("http://10.10.10.60:7101/sqyl/logonAction.do?method=logon2&userid=pdshzxp&passwd=21218cca77804d2ba1922c33e0151105&df_id=" + patientJmda);
            window.open("http://172.16.20.45:7101/sqyl/logonAction.do?method=logon2&userid=pdshzxp&passwd=21218cca77804d2ba1922c33e0151105&df_id=" + patientJmda);
        }
    }
}

/**
 * 多选验证
 */
function checkGroup() {
    if ($("#group_lnr").is(':checked') && $("#group_et").is(':checked')) {
        layer.msg("老年人与儿童不能重复选择！！");
        $("#group_et").prop("checked", false);
        return false;
    }

    if ($("#group_ycf").is(':checked')) {
        if ($("#group_et").is(':checked')) {
            layer.msg("孕产妇与儿童不能重复选择！！");
            $("#group_et").prop("checked", false);
            return false;
        } else if ($("#group_lnr").is(':checked')) {
            layer.msg("孕产妇与老年人不能重复选择！！");
            $("#group_lnr").prop("checked", false);
            return false;
        }
    }

    if ($("#group_pt").is(':checked')) {
        debugger;
        if ($("#group_lnr").is(':checked')) {
            layer.msg("普通人与其他类型不能重复选择！！");
            $("#group_pt").prop("checked", false);
            return false;
        } else if ($("#group_gxy").is(':checked')) {
            layer.msg("普通人与其他类型不能重复选择！！");
            $("#group_pt").prop("checked", false);
            return false;
        } else if ($("#group_tnb").is(':checked')) {
            layer.msg("普通人与其他类型不能重复选择！！");
            $("#group_pt").prop("checked", false);
            return false;
        } else if ($("#group_et").is(':checked')) {
            layer.msg("普通人与其他类型不能重复选择！！");
            $("#group_pt").prop("checked", false);
            return false;
        } else if ($("#group_ycf").is(':checked')) {
            layer.msg("普通人与其他类型不能重复选择！！");
            $("#group_pt").prop("checked", false);
            return false;
        } else if ($("#group_jhb").is(':checked')) {
            layer.msg("普通人与其他类型不能重复选择！！");
            $("#group_pt").prop("checked", false);
            return false;
        } else if ($("#group_yzjszahz").is(':checked')) {
            layer.msg("普通人与其他类型不能重复选择！！");
            $("#group_pt").prop("checked", false);
            return false;
        } else if ($("#group_cjr").is(':checked')) {
            layer.msg("普通人与其他类型不能重复选择！！");
            $("#group_pt").prop("checked", false);
            return false;
        }else if ($("#group_nxg").is(':checked')) {
            layer.msg("普通人与其他类型不能重复选择！！");
            $("#group_pt").prop("checked", false);
            return false;
        }else if ($("#group_gxb").is(':checked')) {
            layer.msg("普通人与其他类型不能重复选择！！");
            $("#group_pt").prop("checked", false);
            return false;
        }else if ($("#group_azhz").is(':checked')) {
            layer.msg("普通人与其他类型不能重复选择！！");
            $("#group_pt").prop("checked", false);
            return false;
        }else if ($("#group_qt").is(':checked')) {
            layer.msg("普通人与其他类型不能重复选择！！");
            $("#group_pt").prop("checked", false);
            return false;
        }
    }
    if ($("#group_qt").is(':checked')) {
        debugger;
        if ($("#group_lnr").is(':checked')) {
            layer.msg("其他与老年人类型不能重复选择！！");
            $("#group_qt").prop("checked", false);
            return false;
        } else if ($("#group_gxy").is(':checked')) {
            layer.msg("其他与高血压他类型不能重复选择！！");
            $("#group_qt").prop("checked", false);
            return false;
        } else if ($("#group_tnb").is(':checked')) {
            layer.msg("其他与糖尿病类型不能重复选择！！");
            $("#group_qt").prop("checked", false);
            return false;
        } else if ($("#group_et").is(':checked')) {
            layer.msg("其他与儿童类型不能重复选择！！");
            $("#group_qt").prop("checked", false);
            return false;
        } else if ($("#group_ycf").is(':checked')) {
            layer.msg("其他与孕产妇类型不能重复选择！！");
            $("#group_qt").prop("checked", false);
            return false;
        } else if ($("#group_jhb").is(':checked')) {
            layer.msg("其他与结核病类型不能重复选择！！");
            $("#group_qt").prop("checked", false);
            return false;
        } else if ($("#group_yzjszahz").is(':checked')) {
            layer.msg("其他与严重精神障碍患类型不能重复选择！！");
            $("#group_qt").prop("checked", false);
            return false;
        } else if ($("#group_cjr").is(':checked')) {
            layer.msg("其他与残疾人类型不能重复选择！！");
            $("#group_qt").prop("checked", false);
            return false;
        }else if ($("#group_nxg").is(':checked')) {
            layer.msg("其他与脑血管病患者类型不能重复选择！！");
            $("#group_qt").prop("checked", false);
            return false;
        }else if ($("#group_gxb").is(':checked')) {
            layer.msg("其他与冠心病患者类型不能重复选择！！");
            $("#group_qt").prop("checked", false);
            return false;
        }else if ($("#group_azhz").is(':checked')) {
            layer.msg("其他与癌症患者类型不能重复选择！！");
            $("#group_qt").prop("checked", false);
            return false;
        }else if ($("#group_pt").is(':checked')) {
            layer.msg("其他与普通人类型不能重复选择！！");
            $("#group_qt").prop("checked", false);
            return false;
        }
    }
}
function addfile(){
    var file=$("#upExcel").val();
    var ext = file.slice(file.lastIndexOf(".")+1).toLowerCase();
    if(ext == undefined || (ext != 'xls')){
        layer.open({
            skin: 'layui-layer-molv',
            closeBtn: 0,
            title: false,
            content:'上传EXCEL格式不正确（EXCEL格式应该为xls）!' ,
            anim: 5 ,
            btn: ['关闭']
        });
        return false;
    }
    $.ajaxFileUpload({
            url: 'signAction.action?act=importExcel', //用于文件上传的服务器端请求地址
            secureuri: false, //是否需要安全协议，一般设置为false
            fileElementId: 'upExcel', //文件上传域的ID
            dataType: 'JSON', //返回值类型 一般设置为json
            success: function (data, status)  //服务器成功响应处理函数
            {
                var da=JSON.parse(data);
//                    console.log(da);
                layer.msg(da.msg);
            },
            error: function (data, status, e)//服务器响应失败处理函数
            {
                layer.msg(e);
            }
        }
    )
    return false;
}





/**
 * 初始化市级
 * WangCheng
 */
function initCity() {
    if(HospAreaCode!="null") {
        var hospStr = HospAreaCode.substring(0, 2);
        vo["upId"] = hospStr + "0000000000";
        doAjaxPost('manageCounAction.action?act=appAddressResult', {strJson: JSON.stringify(vo)}, function (data) {
            $("#cityLevel").html("");
            var option1 = document.createElement('option');
            option1.setAttribute("value", "");
            option1.innerText = "--请选择--";
            document.getElementById("cityLevel").appendChild(option1);
            if (data != null) {
                if (data.rows != null) {
                    $.each(data.rows, function (k, v) {
                        if (v.state != "0") {
                            var option = document.createElement('option');
                            option.setAttribute("value", v.id);
                            option.innerText = v.name;
                            document.getElementById("cityLevel").appendChild(option);
                        }
                    });
                }
                $("#findLevel").val("1");
                $("#signAreaCode").val(hospStr);
                // findList();
            }
        }, function (data) {
            layer.msg("级联初始化异常，请联系管理员！");
        })
    }
}

/**
 * 初始化区县
 * WangCheng
 */
function initArea(){
    var hospStr = HospAreaCode.substring(0,4);
    vo["upId"] = hospStr + "00000000";
    doAjaxPost('manageCounAction.action?act=appAddressResult', {strJson: JSON.stringify(vo)}, function (data) {
        $("#areaLevel").html("");

        var option1 = document.createElement('option');
        option1.setAttribute("value", "");
        option1.innerText = "--请选择--";
        document.getElementById("areaLevel").appendChild(option1);
        if (data != null) {
            $.each(data.rows, function (k, v) {
                if (v.state != "0") {
                    var option = document.createElement('option');
                    option.setAttribute("value", v.id);
                    option.innerText = v.name;
                    document.getElementById("areaLevel").appendChild(option);
                }
            });
            $("#cityLevel").append("<option value=" + data.vo.id + " selected>" + data.vo.areaSname + "</option>");//直接在市级赋值
            $("#cityLevel").attr("disabled","disabled");
            $("#findLevel").val("2");
            $("#signAreaCode").val(hospStr);
            // findList();
        }
    }, function (data) {
        layer.msg("级联初始化异常，请联系管理员！");
    })
}

/**
 * 初始化社区
 * Wangcheng
 */
function initHospital(){
    var hospStr = HospAreaCode.substring(0,6);
    vo["upId"] = hospStr + "000000";
    doAjaxPost('manageCounAction.action?act=initHospital', {strJson: JSON.stringify(vo)}, function (data) {
        $("#hospitalLevel").html("");

        var option1 = document.createElement('option');
        option1.setAttribute("value", "");
        option1.innerText = "--请选择--";
        document.getElementById("hospitalLevel").appendChild(option1);
        if (data != null) {
            $.each(data.rows, function (k, v) {
                if (v.state != "0") {
                    var option = document.createElement('option');
                    option.setAttribute("value", v.id);
                    option.innerText = v.name;
                    document.getElementById("hospitalLevel").appendChild(option);
                }
            });
            $("#cityLevel").append("<option value=" + data.map.upHospNameId + " selected>" + data.map.upHospName + "</option>");//直接在市级赋值
            $("#areaLevel").append("<option value=" + data.map.hospNameId + " selected>" + data.map.hospName + "</option>");//直接在区县级
            $("#cityLevel").attr("disabled","disabled");
            $("#areaLevel").attr("disabled","disabled");
            $("#findLevel").val("3");
            $("#signAreaCode").val(hospStr);
            // findList();
        }
    }, function (data) {
        layer.msg("级联初始化异常，请联系管理员！");
    })
}

/**
 * 初始化市级、区县、社区
 * WangCheng
 */
function initAllLevel(){
    debugger
    vo["areaCode"] = HospAreaCode;
    doAjaxPost('manageCounAction.action?act=initAllLevel', {strJson: JSON.stringify(vo)}, function (data) {
        if (data != null) {
            $("#cityLevel").html("");
            $("#areaLevel").html("");
            $("#hospitalLevel").html("");
            if(data.map != null){
                $("#cityLevel").append("<option value=" + data.map.cityNameId + " selected>" + data.map.cityName + "</option>");//直接在市级赋值
                $("#areaLevel").append("<option value=" + data.map.areaNameId + " selected>" + data.map.areaName + "</option>");//直接在区县级赋值
                $("#hospitalLevel").append("<option value=" + data.map.communityNameId + " selected>" + data.map.communityName + "</option>");//直接在社区级赋值
                $("#cityLevel").attr("disabled","disabled");
                $("#areaLevel").attr("disabled","disabled");
                $("#hospitalLevel").attr("disabled","disabled");
                $("#findLevel").val("4");
                $("#signAreaCode").val(HospAreaCode);
                // findList();
            }
        }
    }, function (data) {
        layer.msg("级联初始化异常，请联系管理员！");
    });
}

/**
 * 改变市级
 * Wangcheng
 */
function changeCityLevel() {
    if ($("#cityLevel").val() != null && $("#cityLevel").val() != "") {
        vo["upId"] = $("#cityLevel").val();
        doAjaxPost('manageCounAction.action?act=appAddressResult', {strJson: JSON.stringify(vo)}, function (data) {
            $("#areaLevel").html("");

            var option1 = document.createElement('option');
            option1.setAttribute("value", "");
            option1.innerText = "--请选择--";
            document.getElementById("areaLevel").appendChild(option1);
            if (data != null) {
                $.each(data.rows, function (k, v) {
                    if (v.state != "0") {
                        var option = document.createElement('option');
                        option.setAttribute("value", v.id);
                        option.innerText = v.name;
                        document.getElementById("areaLevel").appendChild(option);
                    }
                });
            }
        }, function (data) {
            layer.msg("级联初始化异常，请联系管理员！");
        })
    }
}

/**
 * 改变区县
 * WangCheng
 */
function changeAreaLevel() {
    if ($("#areaLevel").val() != null && $("#areaLevel").val() != "") {
        vo["upId"] = $("#areaLevel").val();
        doAjaxPost('manageCounAction.action?act=initHospital', {strJson: JSON.stringify(vo)}, function (data) {
            $("#hospitalLevel").html("");

            var option1 = document.createElement('option');
            option1.setAttribute("value", "");
            option1.innerText = "--请选择--";
            document.getElementById("hospitalLevel").appendChild(option1);
            if (data != null) {
                $.each(data.rows, function (k, v) {
                    if (v.state != "0") {
                        var option = document.createElement('option');
                        option.setAttribute("value", v.id);
                        option.innerText = v.name;
                        document.getElementById("hospitalLevel").appendChild(option);
                    }
                });
            }
        }, function (data) {
            layer.msg("级联初始化异常，请联系管理员！");
        })
    }

}