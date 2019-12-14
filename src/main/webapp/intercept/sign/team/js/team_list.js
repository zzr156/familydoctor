var vo={};
var qvo={};
var vos={};
var table;


$(function(){

    doAjaxPost('teamAction.action?act=commList',{},initBack);
    $( "#drHospName" ).autocomplete({
        source: function( request, response ) {
            $.getJSON( "teamAction.action?act=findOrg", {
                name: request.term
            }, response );
        },
        minLength: 1,
        select: function( event, ui ) {
            $( "#drHospName" ).val( ui.item.label );
            $( "#drHospId" ).val( ui.item.value );
            return false;
        }
    });
    //查询当前机构
    doAjaxPost('teamAction.action?act=findOrgById',{id:orgid},orginit);
})
function initBack(data) {
    //查询当前机构
    doAjaxPost('teamAction.action?act=findOrgById',{id:orgid},orginit);
}
function orginit(data) {
    if(data!=null && data.vo!=null && data.vo.hospName!=null) {
        $("#drHospName").val(data.vo.hospName);
        $("#drHospId").val(data.vo.id);
        $("#drHospName").attr("disabled",true);
    }

    layui.use('table', function(){
        table = layui.table;
        table.on('tool(team)', function(obj){
            var ui = obj.data; //获得当前行数据
            if(obj.event == 'modify'){ //进入修改
                forTeamModify(ui);
            }else if(obj.event=='member'){//成员管理
                forMember(ui);
            }else if(obj.event=='del'){//删除
                teamDel(ui);
            }
        });
        // findList();
    });

}
//查询
function findList() {
    var index = layer.load(1);
    qvo = uiToData("form_qvo",qvo);//界面查询值绑定到变量
    table.render({
        height:'400'
        ,elem: '#teamTabel'
        ,cols: [[
            {field: 'myIndex',title: '序号',width: 60,fixed: true}
            ,{field: 'teamCode', title: '团队编号', width: 80,sort: true}
            ,{field: 'teamHospName', title: '机构名称', width: 220,sort: true}
            ,{field: 'teamName', title: '团队名称', width: 150,sort: true}
            ,{field: 'teamDrName', title: '团队负责人', width: 120}
            ,{field: 'teamTel', title: '联系电话', width: 120}
            ,{field:'teamStateName', title: '有效标识', width: 100}
            ,{field: 'strTeamCreateTime', title: '创建时间', width: 150}
            ,{fixed: 'right', width:210, align:'center', toolbar: '#barRole'}
        ]]
        ,id: 'team'
        ,url: 'teamAction.action'
        ,where: {act: 'list',strJson:JSON.stringify(qvo)}
        ,method: 'post'
        ,skin: 'row' //表格风格
        // ,size: 'sm'
        ,even: true
        ,page: true //是否显示分页
        ,limits: [5,10,15,20,25,50,100,200]
        ,limit: 10 //每页默认显示的数量
        ,done: function(res, curr, count){
            vos=res.data;
            //遮罩关闭
            layer.close(index);
        }
    });
}

//新增团队
function forTeamAdd() {
    var url = 'team_add.jsp?1=1';
    defualtHref(url);
}
//修改团队
function forTeamModify(ui) {
    var id=ui.id;
    var orgid=ui.teamHospId;
    var url = "team_modify.jsp?id="+id+"&orgid="+orgid;
    //window.location.href= "sign_modify.jsp?id="+ui.id;
    defualtHref(url);
}

//删除
function teamDel(ui){
    layer.confirm('删除团队会同时删除队员列表！确认是否删除数据?', {
        btn: ['确定','取消']
    }, function(){
        var id = ui.id;
        $.post('teamAction.action?act=delete',{'id':id},function(data){
            if(data.msg=="true"){
                showMsg("删除成功!")
                findList();
            } else {
                showMsg(data.msg);
            }
        });
    });
}
//成员管理
function forMember(ui){
    var url = 'team_member.jsp?id='+ui.id;
    defualtHref(url);
}