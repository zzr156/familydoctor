/**
 * 定义全局变量
 */
var vo = {};
var signIds = "";
var id = getQueryString("id");
var teamId = getQueryString("teamId");
var patientId = getQueryString("patientId");
var signId = getQueryString("signId");
var protocoltpye = getQueryString("protocoltpye");
var addtpye = getQueryString("addtpye");
var drId = getQueryString("drId");

/**
 * 页面加载完成后执行
 */
$(function () {
    if (addtpye == "pldy") {// 批量打印
        vo["type"] = "2";
        vo["qyType"] = "1";
        vo["patientId"] = patientId;
        doAjaxPost('signAction.action?act=appAgreeMentAll', {strJson: JSON.stringify(vo)}, function (data) {
            if (data.msg != null) {
                var div = document.createElement("div");
                div.innerHTML = data.msg;
                $("#protocolId").append(div.innerHTML);
                signIds = data.ukey;
            }
        });
    } else {
        Initialization();// 查询配置参数
        vo["teamId"] = teamId;
        vo["patientId"] = patientId;
        vo["signId"] = signId;
        vo["type"] = "2";
        vo["qyType"] = "1";
        vo["drId"] = drId;
        //window.open("sign_protocol_print.jsp?teamId="+teamId+"&patientId="+patientId+"&type=2"+"&qyType=1");
        doAjaxPost('signAction.action?act=appAgreeMent', {strJson: JSON.stringify(vo)}, function (data) {
            if (data.msg != null) {
                if (data.entity != null) {
                    $("#imageSrcId").attr("src", data.entity);
                } else {
                    $("#protocoImage").hide();
                }
                var div = document.createElement("div");
                div.innerHTML = data.msg;
                $("#protocolId").append(div.innerHTML);
                signIds = data.ukey;
            }
        });
    }
});

function print() {
    // $("#protocolId").jqprint();
/*    doAjaxPost('signAction.action?act=flagPrint', {signId:signId}, function (data) {
        if (data.code == "900") {
            layer.msg("未缴费无法打印协议");
            // layer.msg(data.msg());
        }else{

        }
    });*/
    $("#protocolId").print();
    if (signIds != null && signIds != "") {
        vo["patientId"] = signIds;
        doAjaxPost('signAction.action?act=changeSignPrintFlag', {strJson: JSON.stringify(vo)}, function (data) {
        });
    }
}

function printheader() {
    // $("#headerId").jqprint();
    $("#headerId").print();
}

function printtailId() {
    // $("#tailId").jqprint();
    $("#tailId").print();
}

/**
 * 返回
 */
function goto() {
    if (addtpye == "1") {
        if (protocoltpye == "800") {
            defualtHref('sign_pt_list.jsp?');// 筛查页面
        } else {
            history.go(-1);
        }
    } else if (addtpye == "2") {// 返回到读卡页面
        defualtHref('sign_card.jsp?')
    } else if (addtpye == "3") {// 返回到续签页面
        history.go(-1);
    } else {
        if (protocoltpye == "" || protocoltpye == undefined || protocoltpye == null) {
            defualtHref('sign_list.jsp?');
        } else if (protocoltpye == "800") {
            defualtHref('sign_look.jsp?id=' + id + "&type=" + true + "&protocoltpye=800");
        }
    }
}
