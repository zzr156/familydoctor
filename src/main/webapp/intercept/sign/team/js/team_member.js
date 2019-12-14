var teamid;
var qvo={};
var vo={};
var users={};
var table;

$(function(){

    teamid=getUrlParam("id");
    doAjaxPost('teamAction.action?act=commList',{},initBack);
    doAjaxPost('teamAction.action?act=findDrCmm',{teamId:teamid},function(data){
        if(data.rows!=null){
            users=data.rows;
            $("#appMemName").html("");
            var option = document.createElement('option');
            option.setAttribute("value","");
            option.innerText = "--请选择--";
            document.getElementById("appMemName").appendChild(option);
            $.each(data.rows,function(k,v){
                dataUiCodeGroup("select","appMemName",v.drName, v.id);
            })
            $("#appMemName").chosen();
        }
    });
})

function initBack(data) {
    if(data.map!=null) {
        //工作类型
        if(data.map.workType!=null){
            $("#appWorkType").html("");
            var option = document.createElement('option');
            option.setAttribute("value","");
            option.innerText = "--请选择--";
            document.getElementById("appWorkType").appendChild(option);
            $.each(data.map.workType,function(k,v){
                dataUiCodeGroup("select","appWorkType",v.codeTitle, v.codeValue);
            })
        }
    }
    layui.use('table', function(){
        table = layui.table;
        table.on('tool(member)', function(obj){
            var ui = obj.data; //获得当前行数据
            if(obj.event=='del'){//删除
                memberDel(ui);
            }else if(obj.event=='modify'){//修改
                forModify(ui);
            }
        });
        findTable();
    });

}

function findTable() {
    qvo = uiToData("form_qvo",qvo);//界面查询值绑定到变量
    qvo["appMemTeamId"] = teamid;
    table.render({
        height:'400'
        ,elem: '#memberTabel'
        ,cols: [[
            {field: 'myIndex',title: '序号',width: 60,fixed: true}
            ,{field: 'memTeamName', title: '团队名称', width: 220}
            ,{field: '', title: '员工姓名', width: 150,sort: true,templet:'<div>{{#if(d.memState =="0"){ }}<span style="color: red;">{{d.memDrName}}</span>{{#} else{ }}{{d.memDrName}}{{#} }}</div>'}
            ,{field: 'drTel', title: '联系电话', width: 120}
            ,{field: 'memWorkTypeName', title: '工作类型', width: 120}
            ,{field: 'drRoleName', title: '职称', width: 120,templet:'<div>{{#if(d.drRole !=null){ }} {{drRole[d.drRole]}} {{#} }}</div>'}
            ,{field: 'hospName', title: '机构名称', width: 200}
            ,{fixed: 'right', width:140, align:'center', toolbar: '#barMember'}
        ]]
        ,id: 'member'
        ,url: 'teamAction.action'
        ,where: {act: 'listMemCmm',strJson:JSON.stringify(qvo)}
        ,method: 'post'
        ,skin: 'row' //表格风格
        // ,size: 'sm'
        ,even: true
        ,page: true //是否显示分页
        ,limits: [5,10,15,20,25,50,100,200]
        ,limit: 10 //每页默认显示的数量
        ,done: function(res, curr, count){
            vos=res.data;
        }
    });
}

function addFrom(){
    if(validator("form_vo")) {
        vo = uiToData2("form_vo", vo);
        var options=$("#appMemName option:selected");
        vo["memDrId"]=$("#appMemName").val();
        vo["memDrName"] = options.text();
        vo["memWorkType"]=$("#appWorkType").val();
        vo["drRole"]=$("#drRole").val();
        vo["drTel"]=$("#drTel").val();
        vo["memTeamid"]=teamid;
        doAjaxPost('teamAction.action?act=addMemCmm', {strJson: JSON.stringify(vo)}, addFromBack);
    }
}
function modifyFrom(){
    if(validator("form_vo")) {
        vo = uiToData2("form_vo", vo);
        var options=$("#appMemName option:selected");
        vo["memDrId"]=$("#appMemName").val();
        vo["memDrName"] = options.text();
        vo["memWorkType"]=$("#appWorkType").val();
        vo["drRole"]=$("#drRole").val();
        vo["drTel"]=$("#drTel").val();
        vo["memTeamid"]=teamid;
        vo["id"]=$("#id").val();

        doAjaxPost('teamAction.action?act=modifyMemCmm',{strJson: JSON.stringify(vo)}, modifyBack);
    }
}
function addFromBack(data){
    if(data.msg == 'true') {
        findTable();
    }else {
        layer.msg(data.msg);
    }
}
function modifyBack(data) {
    if(data.msg == 'true') {
        forAdd();
        findTable();
    }else {
        layer.msg(data.msg);
    }
}

function memberDel(ui) {
    if(ui.memState=="0"){
        showMsg("队长不能删除!");
        return false;
    }
    layer.confirm('确认删除数据?', {
        btn: ['确定','取消']
    }, function(){
        var id = ui.id;
        $.post('teamAction.action?act=delMemCmm',{'id':id},function(data){
            if(data.msg=="true"){
                showMsg("删除成功!");
                findTable();
            } else {
                showMsg(data.msg);
            }
        });
    });
}

function forModify(ui) {

    $("#id").val(ui.id);
    $("#appWorkType").val(ui.memWorkType);
    $("#drRole").val(ui.drRole);
    $("#drTel").val(ui.drTel);
    $("#appMemName").chosen("destroy");

    $("#modify").show();
    $("#forAdd").show();
    $("#add").hide();

    if(ui.memState=="0"){
        $("#appMemName").val(ui.memDrId);
        $("#appMemName").attr("disabled",true);
    }else {
        $("#appMemName").attr("disabled",false);
        $("#appMemName").val(ui.memDrId);
        $("#appMemName").chosen();
    }
    addDr(ui.memDrId);
}
function forAdd() {
    $("#id").val("");
    $("#appWorkType").val("");
    $("#drRole").val("");
    $("#drTel").val("");
    $("#appMemName").chosen("destroy");
    $("#appMemName").val("");
    $("#appMemName").chosen();

    $("#modify").hide();
    $("#forAdd").hide();
    $("#add").show();
}

function addDr(did) {
//    var value =did.val();
//    $.each(users,function(k,v){
//        if(value==v.id){
//            $("#drTel").val(v.drTel)
//            //$("#drRole").find("option[value='"+v.drJobTitle+"']").prop("selected",true);//回显职称
//            return;
//        }
//    })
}
var drRole={
    // '':'',
    1.1:'主任医师',
    1.2:'副主任医师',
    1.3:'主治（主管）医师',
    1.4:'医师',
    1.5:'医士',
    2.1:'主任药师',
    2.2:'副主任药师',
    2.3:'主管药师',
    2.4:'药师',
    2.5:'药士',
    3.1:'主任护师',
    3.2:'副主任护师',
    3.3:'主管护师',
    3.4:'护师',
    3.5:'护士',
    4.1:'主任技师',
    4.2:'副主任技师',
    4.3:'主管技师',
    4.4:'技师',
    4.5:'技士',
    1:'健康管理师',
    2:'专科医生',
    3:'全科医生',
    4:'医技人员',
    5:'公卫医师',
    6:'社区护士',
    7:'助理'
}