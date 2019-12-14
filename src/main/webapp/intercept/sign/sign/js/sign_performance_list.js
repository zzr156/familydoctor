var per={
    'listLr':'老年人'
    ,'listTnb':'糖尿病'
    ,'listWz':'未知'
    ,'listGxy':'高血压'
    ,'listYcf':'孕产妇'
    ,'listCjr':'残疾人'
    ,'listJhb':'结核病'
    ,'listJsb':'精神病'
    ,'listEt':'儿童'
    ,'listPt':'普通'
}
var vo={};
var qvo={};
var vos={};
$(function(){
    //履约
    findList();
});
function findList() {
    qvo.drId=getUrlParam("drId");
    qvo.fwType=getUrlParam("fwType");
    doAjaxPost('appCommon.action?act=findPerListWeb',{qvoJson:JSON.stringify(qvo)},findPerListBack);
}

function findPerListBack(data) {
    ui=$("#lytjBody");
    for(var key in data.map){
            ui.append("<tr bgcolor='#f2f2f2'>\n" +
                "           <th colspan=\"5\">" + per[key] +"("+data.map[key].length+")</th>\n" +
                "        </tr>");
            if(data.map[key].length>0){
                $.each(data.map[key], function(k, v) {
                    ui.append("<tr>\n" +
                        "            <td>"+""+"</td>\n" +
                        "            <td>"+v.patientName+"</td>\n" +
                        "            <td>"+sex[v.patientGender]+"</td>\n" +
                        "            <td>"+v.patientAge+"</td>\n" +
                        "            <td>"+v.patientIdNo+"</td>\n" +
                        "        </tr>");
                });
            }

        }
}
var sex={
    1:"男",
    2:"女"
}

