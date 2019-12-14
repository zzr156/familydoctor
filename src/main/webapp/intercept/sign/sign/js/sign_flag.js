var addtpye=getQueryString("addtpye");
var vo={};
var form;
var vo1={};
$(function(){
   getFlag();
});
function getFlag(){
    doAjaxPost("applabel.action?act=findList",{},function(data){
        if(data.rows!=null){
             append(data);
        }
    });
}




function add() {
    debugger
    vo1=JSON.parse(vo1);
    var check_val="" ;
    $("#flagDiv input[type='hidden']").each(function(){
        if($(this).val()!="")
            check_val=check_val+$(this).val()+";"
    });
    check_val=check_val.substring(0,check_val.length-1);
    vo1["personLable"]= check_val;
    vo1["lableState"]="1";
    if(addtpye=='modify'){
        doAjaxPost('signAction.action?act=signRenew', {strJson: JSON.stringify(vo1)}, function (data) {
            if (data.vo != null && data.code == "800") {
                layer.msg("续约成功！");
                setTimeout("history.back(-1)", "1500");
            } else {
                if (data.msg == "true") {
                    layer.msg("该居民已经续签，请勿重复续签！");
                }
            }
        })

    }else if(addtpye=='add') {
        doAjaxPost('signAction.action?act=signAdd', {strJson: JSON.stringify(vo1)}, function (data) {
            if (data.code == "800") {

                window.parent.selectLabel(data.vo.id, data.vo.signTeamId, data.vo.signDrId, data.vo.signPatientId);
                var index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);
            } else {
                layer.msg(data.msg);
            }
        }, function (data) {
            layer.msg("签约保存失败！");
        });
    }
}

function child(d) {//接收父层的参数
       vo1=JSON.stringify(d);
}

function append(data) {
    for(var i=0;i<data.rows.length;i++){
        if(data.rows[i].labelValue!="201"&&data.rows[i].labelValue!="202"&&data.rows[i].labelValue!="207"&&data.rows[i].labelValue!="208") {
            document.getElementById("flagDiv").innerHTML = document.getElementById("flagDiv").innerHTML + "<div class=\"layui-btn layui-btn-radius layui-btn-primary \" style=\"margin-left: 10px;margin-top: 10px;border-color: grey\"  onclick='changValue(this)'  id=" + data.rows[i].labelValue + "><input type='hidden' id=\"lableFlag \"  name=" + data.rows[i].labelValue + " lay-skin=\"primary\" pofield='lableFlag'  style='margin-top: -3px'  value=''>" + data.rows[i].labelTitle + "</div>"
        }
    }
    document.getElementById("flagDiv").innerHTML = document.getElementById("flagDiv").innerHTML + "<div class=\"layui-btn layui-btn-radius layui-btn-primary red207\" style=\"margin-left: 20px;margin-top: 30px;border-color: red;color: red\" onclick='changValue(this)' id='207|red'><input type='hidden' id=\"jsbRed\" name=\"207red\" lay-skin=\"primary\" pofield='jsbRed'  value=''>严重精神障碍</div>"
    document.getElementById("flagDiv").innerHTML = document.getElementById("flagDiv").innerHTML + "<div class=\"layui-btn layui-btn-radius layui-btn-primary yellow207\" style=\"margin-left: 30px;margin-top: 30px;border-color: orange;color: orange\" onclick='changValue(this)' id='207|yellow'><input type='hidden' id=\"jsbYellow\" name=\"207yellow\" lay-skin=\"primary\"  pofield='jsbYellow'  value=''>严重精神障碍</div>"
    document.getElementById("flagDiv").innerHTML = document.getElementById("flagDiv").innerHTML + "<div class=\"layui-btn layui-btn-radius layui-btn-primary green207\" style=\"margin-left: 30px;margin-top: 30px;border-color: green;color: green\" onclick='changValue(this)' id='207|green'><input type='hidden' id=\"jsbGreen\" name=\"207green\" lay-skin=\"primary\"  pofield='jsbGreen'  value=''>严重精神障碍</div>"
    document.getElementById("flagDiv").innerHTML = document.getElementById("flagDiv").innerHTML + "<br><div class=\"layui-btn layui-btn-radius layui-btn-primary red201\" style=\"margin-left: 80px;margin-top: 30px;border-color: red;color: red\" onclick='changValue(this)' id='201|red'><input type='hidden' id=\"gxyRed\" name=\"201red\" lay-skin=\"primary\"  pofield='gxyRed'  value=''>高血压</div>"
    document.getElementById("flagDiv").innerHTML = document.getElementById("flagDiv").innerHTML + "<div class=\"layui-btn layui-btn-radius layui-btn-primary yellow201\" style=\"margin-left: 30px;margin-top: 30px;border-color: orange;color: orange\" onclick='changValue(this)' id='201|yellow'><input type='hidden' id=\"gxyYellow\" name=\"201yellow\" lay-skin=\"primary\"  pofield='gxyYellow'  value=''>高血压</div>"
    document.getElementById("flagDiv").innerHTML = document.getElementById("flagDiv").innerHTML + "<div class=\"layui-btn layui-btn-radius layui-btn-primary green201\" style=\"margin-left: 30px;margin-top:30px;border-color: green;color: green\" onclick='changValue(this)' id='201|green'><input type='hidden' id=\"gxyGreen\" name=\"201green\" lay-skin=\"primary\" pofield='gxyGreen'  value=''>高血压</div><br>"
    document.getElementById("flagDiv").innerHTML = document.getElementById("flagDiv").innerHTML + "<div class=\"layui-btn layui-btn-radius layui-btn-primary red202\" style=\"margin-left: 80px;margin-top: 30px;border-color: red;color: red\" onclick='changValue(this)' id='202|red'><input type='hidden' id=\"tnbRed\" name=\"202red\" lay-skin=\"primary\" pofield='tnbRed'  value=''>糖尿病</div>"
    document.getElementById("flagDiv").innerHTML = document.getElementById("flagDiv").innerHTML + "<div class=\"layui-btn layui-btn-radius layui-btn-primary yellow202\" style=\"margin-left: 30px;margin-top: 30px;border-color: orange;color: orange\" onclick='changValue(this)' id='202|yellow'><input type='hidden' id=\"tnbYellow\" name=\"202yellow\" lay-skin=\"primary\"  pofield='tnbYellow'  value=''>糖尿病</div>"
    document.getElementById("flagDiv").innerHTML = document.getElementById("flagDiv").innerHTML + "<div class=\"layui-btn layui-btn-radius layui-btn-primary green202\" style=\"margin-left: 30px;margin-top: 30px;border-color: green;color: green\" onclick='changValue(this)' id='202|green'><input type='hidden' id=\"tnbGreen\" name=\"202green\" lay-skin=\"primary\"  pofield='tnbGreen'  value=''>糖尿病</div>"
    document.getElementById("flagDiv").innerHTML = document.getElementById("flagDiv").innerHTML + "<br><div class=\"layui-btn layui-btn-radius layui-btn-primary red208\" style=\"margin-left: 80px;margin-top: 30px;border-color: red;color: red\" onclick='changValue(this)' id='208|red'><input type='hidden' id=\"jhbRed\" name=\"208red\" lay-skin=\"primary\"  pofield='jhbRed'  value=''>结核病</div>"
    document.getElementById("flagDiv").innerHTML = document.getElementById("flagDiv").innerHTML + "<div class=\"layui-btn layui-btn-radius layui-btn-primary yellow208\" style=\"margin-left: 30px;margin-top: 30px;border-color: orange;color: orange\" onclick='changValue(this)' id='208|yellow'><input type='hidden' id=\"jhbYellow\" name=\"208yellow\" lay-skin=\"primary\"  pofield='jhbYellow'  value=''>结核病</div>"
    document.getElementById("flagDiv").innerHTML = document.getElementById("flagDiv").innerHTML + "<div class=\"layui-btn layui-btn-radius layui-btn-primary green208\" style=\"margin-left: 30px;margin-top:30px;border-color: green;color: green\" onclick='changValue(this)' id='208|green'><input type='hidden' id=\"jhbGreen\" name=\"208green\" lay-skin=\"primary\" pofield='jhbGreen'  value=''>结核病</div><br>"
    // document.getElementById("flagDiv").innerHTML = document.getElementById("flagDiv").innerHTML + "<div class=\"layui-btn layui-btn-radius layui-btn-primary red207\" style=\"margin-left: 20px;margin-top: 30px;border-color: red;color: red\" onclick='changValue(this)' id='207|red'><input type='hidden' id=\"jsbRed\" name=\"207red\" lay-skin=\"primary\" pofield='jsbRed'  value=''>严重精神障碍</div>"
    // document.getElementById("flagDiv").innerHTML = document.getElementById("flagDiv").innerHTML + "<div class=\"layui-btn layui-btn-radius layui-btn-primary yellow207\" style=\"margin-left: 30px;margin-top: 30px;border-color: orange;color: orange\" onclick='changValue(this)' id='207|yellow'><input type='hidden' id=\"jsbYellow\" name=\"207yellow\" lay-skin=\"primary\"  pofield='jsbYellow'  value=''>严重精神障碍</div>"
    // document.getElementById("flagDiv").innerHTML = document.getElementById("flagDiv").innerHTML + "<div class=\"layui-btn layui-btn-radius layui-btn-primary green207\" style=\"margin-left: 30px;margin-top: 30px;border-color: green;color: green\" onclick='changValue(this)' id='207|green'><input type='hidden' id=\"jsbGreen\" name=\"207green\" lay-skin=\"primary\"  pofield='jsbGreen'  value=''>严重精神障碍</div>"

    document.getElementById("flagDiv").innerHTML=document.getElementById("flagDiv").innerHTML+"<br><input type=\"button\" id=\"roleadd\" value=\"确定\" onclick=\"add()\" class=\"layui-btn layui-btn-normal\" style='left: 40%;top:110%;position: absolute'/>";

}
function changValue(data) {
    debugger
    var t="";
    if(data.id.length>3&&data.id.indexOf("201")>-1&&data.id.indexOf("|")==-1){
        data.id=data.id.substring(0,3)+"|"+data.id.substring(3,data.id.length);
    }
    if(data.id.length>3&&data.id.indexOf("202")>-1&&data.id.indexOf("|")==-1){
        data.id=data.id.substring(0,3)+"|"+data.id.substring(3,data.id.length);
    }
    if(data.id.length>3&&data.id.indexOf("207")>-1&&data.id.indexOf("|")==-1){
        data.id=data.id.substring(0,3)+"|"+data.id.substring(3,data.id.length);
    }
    if(data.id.length>3&&data.id.indexOf("208")>-1&&data.id.indexOf("|")==-1){
        data.id=data.id.substring(0,3)+"|"+data.id.substring(3,data.id.length);
    }
    if(data.id.indexOf("|")>-1){
        t=data.id;
       var a=data.id.indexOf("|");
       data.id=data.id.substring(0,a)+data.id.substring(a+1,data.id.length);
    }
    if($("input[name="+data.id+"]").val()==""){   //选中
        $("input[name="+data.id+"]").val(data.id);
        if(t!=""){
            if(data.id.indexOf("201")>-1){//设置高血压单选
              $("#201red").attr("class","layui-btn layui-btn-radius layui-btn-primary");
              $("#201red").css("color","red");
                $("input[name='201red']").val("")
              $("#201yellow").attr("class","layui-btn layui-btn-radius layui-btn-primary");
              $("#201yellow").css("color","orange");
              $("input[name='201yellow']").val("")
               $("#201green").attr("class","layui-btn layui-btn-radius layui-btn-primary");
               $("#201green").css("color","green");
               $("input[name='201green']").val("")
            }
            if(data.id.indexOf("202")>-1){//设置糖尿病单选
                $("#202red").attr("class","layui-btn layui-btn-radius layui-btn-primary");
                $("#202red").css("color","red");
                $("input[name='202red']").val("")
                $("#202yellow").attr("class","layui-btn layui-btn-radius layui-btn-primary");
                $("#202yellow").css("color","orange");
                $("input[name='202yellow']").val("")
                $("#202green").attr("class","layui-btn layui-btn-radius layui-btn-primary");
                $("#202green").css("color","green");
                $("input[name='202green']").val("")
            }
            if(data.id.indexOf("208")>-1){//设置结核病单选
                $("#208red").attr("class","layui-btn layui-btn-radius layui-btn-primary");
                $("#208red").css("color","red");
                $("input[name='208red']").val("")
                $("#208yellow").attr("class","layui-btn layui-btn-radius layui-btn-primary");
                $("#208yellow").css("color","orange");
                $("input[name='208yellow']").val("")
                $("#208green").attr("class","layui-btn layui-btn-radius layui-btn-primary");
                $("#208green").css("color","green");
                $("input[name='208green']").val("")
            }
            if(data.id.indexOf("207")>-1){//设置精神病单选
                $("#207red").attr("class","layui-btn layui-btn-radius layui-btn-primary");
                $("#207red").css("color","red");
                $("input[name='207red']").val("")
                $("#207yellow").attr("class","layui-btn layui-btn-radius layui-btn-primary");
                $("#207yellow").css("color","orange");
                $("input[name='207yellow']").val("")
                $("#207green").attr("class","layui-btn layui-btn-radius layui-btn-primary");
                $("#207green").css("color","green");
                $("input[name='207green']").val("")
            }
            $("input[name="+data.id+"]").val(t);
            if(data.id.indexOf("red")>-1){
            $(data).attr("class","layui-btn layui-btn-radius layui-btn-danger");
            $(data).css("color","");
            }
            if(data.id.indexOf("yellow")>-1){
                $(data).attr("class","layui-btn layui-btn-radius layui-btn-warm");
                $(data).css("color","");
            }
            if(data.id.indexOf("green")>-1){
                $(data).attr("class","layui-btn layui-btn-radius ");
                $(data).css("color","");
            }
        }else {
            $(data).css("border-color","red");
        }
    }else{  //点击取消选中
            $("input[name="+data.id+"]").val("");
        if(t!="") {
            if (data.id.indexOf("red") > -1) {
                $(data).attr("class", "layui-btn layui-btn-radius layui-btn-primary");
                $(data).css("color", "red");
            }
            if (data.id.indexOf("yellow") > -1) {
                $(data).attr("class", "layui-btn layui-btn-radius layui-btn-primary");
                $(data).css("color", "orange");
            }
            if (data.id.indexOf("green") > -1) {
                $(data).attr("class", "layui-btn layui-btn-radius layui-btn-primary");
                $(data).css("color", "green");
            }
        }else {
            $("input[name=" + data.id + "]").val("");
            $(data).css("border-color", "gray");
        }
    }
}

