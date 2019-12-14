var vo={};
var id=getQueryString("id");
var handle = getQueryString("handle");
/*初始页面*/
$(function(){
    $("#yyid").hide();
    if(handle == "add"){
        $("input[name='archivingCardState'][value=1]").prop("checked",true);
        $("#roleadd").val("保存");
        $("#createId").val(drid);
        $("#createName").val(username);
    }else if(handle == "modify"){
        $("#roleadd").val("修改");
    }else if(handle == "look"){
        $("#roleadd").hide();
    }
    var d = new Date()
    var vYear = d.getFullYear();
    $("#feeByYear").text(vYear+"年诊疗费用总额");
    $("#yearFee").text(vYear+"年在基层医疗机构救治资金构成情况");
   /* 初始化页面*/
    doAjaxPost('archivingCheck.action?act=findDrList',{},function(data){
        if(data.map != null){
            if(data.map.jsAdmin != null){
                $.each(data.map.jsAdmin,function(k,v){
                    var option = document.createElement('option');
                    option.setAttribute("value", v.codeValue);
                    option.innerText = v.codeTitle;
                    document.getElementById("inspectorOne").appendChild(option);
                });
            }
            if(data.map.drList != null){
                $.each(data.map.drList,function(k,v){
                    var option = document.createElement('option');
                    option.setAttribute("value", v.id);
                    option.innerText = v.drName;
                    document.getElementById("inspectorTwo").appendChild(option);
                });
            }
            if(data.map.lsHz != null){
                $.each(data.map.lsHz,function(k,v){
                    var option = document.createElement('option');
                    option.setAttribute("value", v.codeValue);
                    option.innerText = v.codeTitle;
                    document.getElementById("householdRelationship").appendChild(option);
                });
            }
            findByOne();
        }
    })
    // findDate();
})
function findByOne(){
    vo["id"]=id;
    if(handle == "add"){
        vo["type"]="1";
    }else if(handle == "modify"){
        vo["type"]="2";
    }else if(handle == "look"){
        vo["type"]="3";
    }
    doAjaxPost('archivingCheck.action?act=jsonByOne',{strJson:JSON.stringify(vo)},function(data){
        if(data.vo!=null) {
            dataToUi2(data.vo,"form_vo");
            $("input[name='illnessName']:checkbox").each(function(){
                $(this).attr("checked", false);
            })
            if(data.vo.illnessName != null){
                var illnessNames = data.vo.illnessName.split(";");
                for(var i = 0;i<illnessNames.length;i++){
                    $("input[name='illnessName']:checkbox").each(function(){
                        if($(this).attr("value") == illnessNames[i]){
                            $(this).prop("checked", true);
                        }
                    })
                }
            }
            $("#areaName").text(data.vo.areaName);
            $("#streetName").text(data.vo.streetName);
            $("#cunName").text(data.vo.cunName);
            $("#areaCode").text(data.vo.areaCode);
            if(data.vo.signFromDate != null){
                $("#reason").hide();
            }
            /*if(data.vo.fwType != null){
                if(data.vo.fwType.indexOf("2")==-1){
                    $("#etType").hide();
                }
                if(data.vo.fwType.indexOf("3")==-1){
                    $("#ycfType").hide();
                }
                if(data.vo.fwType.indexOf("5")==-1){
                    $("#gxyType").hide();
                }
                if(data.vo.fwType.indexOf("6")==-1){
                    $("#tnbType").hide();
                }
                if(data.vo.fwType.indexOf("7")==-1){
                    $("#jsbType").hide();
                }
            }*/
            if(data.vo.etFollowNum != null && data.vo.etFollowNum != ""){
                $("#etType").show();
            }
            if(data.vo.ycfFollowNum != null && data.vo.ycfFollowNum != ""){
                $("#ycfType").show();
            }
            if(data.vo.gxyNum != null && data.vo.gxyNum != ""){
                $("#gxyType").show();
            }
            if(data.vo.tnbNum != null && data.vo.tnbNum != ""){
                $("#tnbType").show();
            }
            if(data.vo.jsbFollowNum != null && data.vo.jsbFollowNum != ""){
                $("#jsbType").show();
            }
            if(data.vo.inspectorTwoUrl!="" && data.vo.inspectorTwoUrl != null){
                if(handle == "look"){
                    var option = "<option>"+data.vo.inspectorTwoName+"</option>";
                    $("#inspectorTwo").html(option);
                }else{
                    $("#inspectorTwo").val(data.vo.inspectorTwoUrl);
                }

            }else{
                if(handle == "add"){
                    $("#inspectorTwo").val(drid);
                    $("#inspectorTwoUrl").val(drid);
                }
            }
            if(data.vo.inspectorOneUrl!="" && data.vo.inspectorOneUrl != null){
                if(handle == "look"){
                    var option = "<option>"+data.vo.inspectorOneName+"</option>";
                    $("#inspectorOne").html(option);
                }else{
                    $("#inspectorOne").val(data.vo.inspectorOneUrl);
                }
            }
            if(data.vo.householdRelationship !="" && data.vo.householdRelationship != null){
                $("#householdRelationship").val(data.vo.householdRelationship);
            }

            if(data.vo.checkDate != null && data.vo.checkDate != ""){
                $("#checkDate").val(data.vo.checkDate);
            }
            if(data.vo.checkYwDate != null && data.vo.checkYwDate != ""){
                $("#checkYwDate").val(data.vo.checkYwDate);
            }
            //未签约显示未签约原因
            if(data.vo.signFromDate != null && data.vo.signFromDate != ""){
                $("#reason").hide();
            }else{
                $("#reason").show();
            }
        }else{
            layer.open({
                type: 1,
                title: false,
                closeBtn: false,
                area: '300px;',// 弹框宽度
                offset: [150, ($(window).width() / 2 - 250)],// 弹框位置
                shade: 0.3,
                id: 'layer_open_four',
                btn: ['确定'],
                btnAlign: 'c',
                moveType: 1,
                content: '<div style="padding: 50px; line-height: 22px; background-color: #393D49;' +
                'color: #fff; font-weight: 100; text-align : center;">'+data.msg+'</div>',
                btn1: function () {
                    layer.closeAll();
                    $("#roleadd").hide();
                }
            });
        }
        if(handle == "add"){
            findDate();
        }
    },function(data){
        layer.msg("查询失败，联系管理员！");
        $("#roleadd").hide();
    });
}

function showReaSon(){
    var options=$("#notSignReason option:selected");
    if(options.val()==13){
        $("#yyid").show();
        $("#otherReason").attr("validator", "{\"msg\":\"原因不能为空!\"}");
    }else{
        $("#yyid").hide();
        $("#otherReason").val("");
        $("#otherReason").removeAttr("validator");
    }
}

function goback(){
    history.go(-1);
}

function archivingCheckAddOrModify() {
    if(validator("form_vo")) {
        vo = uiToData2("form_vo", vo);
        vo["notSignReason"] = $("#notSignReason").val();
        vo["checkDate"] = $("#checkDate").val();
        vo["householdRelationship"]= $("#householdRelationship").val();
        doAjaxPost('archivingCheck.action?act=archivingCheckAddOrModify', {strJson: JSON.stringify(vo)}, function (data) {
            console.log(JSON.stringify(data));
            if(data.code=="800"){
                layer.msg("新增成功！");
                window.location.href= "archivingCheck_list.jsp";
            }else{
                layer.msg(data.msg);
            }
        })
    }
}
<!-- 多选事件触发-->
function clickP(span) {
    if (jdBeforeSign == "1") {// 开启了签约前先建档功能
        // 获取居民服务人群类型并判断选中的选项与该居民居民档案中的所属服务人群类型是否相同，如果不相同则提示
        // “所选服务人群类型与该居民健康档案不符，是否前往修改居民健康档案？”
        // 如果选择是则打开居民健康档案修改界面，修改完成后点击遮罩层的继续签约按钮重新查询居民的所属服务人群，
        // 如果点击否则将服务人群设置为接口返回的服务人群类型
        if ($(span).find(":checkbox").is(':checked')) {
            $(span).find(":checkbox").prop("checked", false);
        } else {
            $(span).find(":checkbox").prop("checked", true);
        }
        openThreeWindow();// 提示修改档案
    } else {
        if ($("input[id='oldman']").is(':checked') && $("input[id='young']").is(':checked')) {
            layer.msg("老年人与儿童不能重复选择！！");
            $(span).find(":checkbox").prop("checked", false);
            return false;
        }

        if ($("input[id='yuncf']").is(':checked')) {
            if ($("input[id='young']").is(':checked')) {
                layer.msg("孕产妇与儿童不能重复选择！！");
                $(span).find(":checkbox").prop("checked", false);
                return false;
            } else if ($("input[id='oldman']").is(':checked')) {
                layer.msg("孕产妇与老年人不能重复选择！！");
                $(span).find(":checkbox").prop("checked", false);
                return false;
            }
        }

        if ($("input[id='ordinary']").is(':checked')) {
            if ($("input[id='oldman']").is(':checked')) {
                layer.msg("普通人与其他类型不能重复选择！！");
                $(span).find(":checkbox").prop("checked", false);
                return false;
            } else if ($("input[id='gxy']").is(':checked')) {
                layer.msg("普通人与其他类型不能重复选择！！");
                $(span).find(":checkbox").prop("checked", false);
                return false;
            } else if ($("input[id='tnb']").is(':checked')) {
                layer.msg("普通人与其他类型不能重复选择！！");
                $(span).find(":checkbox").prop("checked", false);
                return false;
            } else if ($("input[id='young']").is(':checked')) {
                layer.msg("普通人与其他类型不能重复选择！！");
                $(span).find(":checkbox").prop("checked", false);
                return false;
            } else if ($("input[id='yuncf']").is(':checked')) {
                layer.msg("普通人与其他类型不能重复选择！！");
                $(span).find(":checkbox").prop("checked", false);
                return false;
            } else if ($("input[id='jhb']").is(':checked')) {
                layer.msg("普通人与其他类型不能重复选择！！");
                $(span).find(":checkbox").prop("checked", false);
                return false;
            } else if ($("input[id='jsb']").is(':checked')) {
                layer.msg("普通人与其他类型不能重复选择！！");
                $(span).find(":checkbox").prop("checked", false);
                return false;
            } else if ($("input[id='cjr']").is(':checked')) {
                layer.msg("普通人与其他类型不能重复选择！！");
                $(span).find(":checkbox").prop("checked", false);
                return false;
            }
        }
    }
    return true;
}
function startMinDate() {
    var nowtime = $("#nowtime").val();
    var gaga = new Date(nowtime.replace(/-/, "/"));//通过HTTP头部里的Date获取服务器上的时间
    /*_y = gaga.getFullYear();
     if(gaga.getDate()<Day){
     _m = gaga.getMonth();
     var getday =getdate(gaga.getMonth(),gaga.getDate(),Day);
     _d = getday;
     }else if(gaga.getDate()==Day){
     _m = gaga.getMonth();
     _d = "01";
     }else{
     _m = gaga.getMonth()+1;
     _d = gaga.getDate()-Day;
     }
     var minTime = _y +"-"+_m+"-"+_d;*/
    var d = new Date(gaga);
    d.setDate(d.getDate() - Day);
    var m = d.getMonth() + 1;
    var minTime = d.getFullYear() + '-' + m + '-' + d.getDate();
    return minTime;

    /*  var nowtime = $("#nowtime").val();
     var gaga = new Date(nowtime.replace(/-/, "/"));//通过HTTP头部里的Date获取服务器上的时间
     _y = gaga.getFullYear() - 1;
     var minTime = _y + "-01-01";
     return minTime;*/
}
function showNotSign(ui){
    if(ui == 0){
        $("#reason").show();
    }else{
        $("#reason").hide();
        $("#notSignReason").val("0");
        $("#otherReason").val("");
        $("#otherReason").removeAttr("validator");
    }
}
function chooseTowUrl(){
    if($("#inspectorTwo").val()!=null && $("#inspectorTwo").val()!=""){
        $("#inspectorTwoUrl").val($("#inspectorTwo").val());
    }
}
function chooseOneUrl(){
    if($("#inspectorOne").val()!=null && $("#inspectorOne").val()!=""){
        $("#inspectorOneUrl").val($("#inspectorOne").val());
    }
}
function findDate(){
    doAjaxPost('signAction.action?act=findDate',{},function(data){
        if(data.vo!=null){
            $("#checkDate").val(data.vo.formDate);
            $("#checkYwDate").val(data.vo.formDate);
        }
    },function(data){
        layer.msg("初始化失败-003，请联系管理员！");
    });
    // var form = layui.form; //只有执行了这一步，部分表单元素才会自动修饰成功
    // form.render();
    // findByOne();
}