var per={
    'jkzd':'健康指导'
    ,'jkjy':'健康教育'
    ,'yyzd':'用药指导'
    ,'jkzx':'健康咨询'
    ,'gxy':'高血压'
    ,'tnb':'糖尿病'
    ,'yzjczi':'严重精神障碍'
    ,'jhb':'结核病'
    ,'zyyjkzd':'中医药健康指导'
    ,'ettj':'儿童体检'
    ,'etxsfs':'新生儿访视'
    ,'jktj':'健康体检'
    ,'zytzbs':'中医体质辨识'
    ,'yqbjfw':'孕期保健服务'
    ,'chfs':'产后视访'
}
var vo={};
var qvo={};
var vos={};
$(function(){
    //履约
    findList();
});
function findList() {
    // qvo = uiToData("form_qvo", qvo);
    qvo.drId=drid;
    qvo.signYear =$("#nowtime").val();
    doAjaxPost('signAction.action?act=findAppSingPerformanceDr',{qvoJson:JSON.stringify(qvo)},findListBack);
}

function findListBack(data) {
    ui=$("#lytjBody");
    for(var key in data.map){
        var countShouldBecompleted="-";
        var countyear="-";
        var countYearNotDone="-";
        if(data.map[key].countShouldBecompleted!=null)
            countShouldBecompleted=data.map[key].countShouldBecompleted;
        if(data.map[key].countyear!=null)
            countyear=data.map[key].countyear;
        if(data.map[key].countYearNotDone!=null)
            countYearNotDone=data.map[key].countYearNotDone;
        if(data.map[key].type!=null) {
            ui.append("<tr ondblclick=findPerList('"+data.map[key].type+"','"+data.map[key].drId+"') ><td>" + per[key] + "</td><td>" + countShouldBecompleted + "</td><td>" + countyear + "</td><td>" + countYearNotDone + "</td></tr>")
        }else {
            ui.append("<tr><td>" + per[key] + "</td><td>" + countShouldBecompleted + "</td><td>" + countyear + "</td><td>" + countYearNotDone + "</td></tr>")
        }
    }
}

function findPerList(type,drId) {
    myOpen("人员列表","sign_performance_list.jsp?drId="+drId+"&fwType="+type);
}