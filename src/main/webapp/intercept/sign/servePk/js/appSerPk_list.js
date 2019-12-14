/**
 * Created by lenovo on 2017/11/20.
 */
var table;
var qvo={};
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
});

$(function(){
     findTable();
})



function findTable(){
    var index = layer.load(1);
    qvo = uiToData("form_qvo",qvo);
    table.render({
        height:'400'
        ,elem: '#appserpk'
        ,cols: [[
            {field: 'myIndex',title: '序号',width: 50,fixed: true}
            ,{field: 'serpkValue', title: '服务编号', width: 180,sort: true}
            ,{field: 'serpkImageUrl', title: '服务图标', width: 180,sort: true}
            ,{field: 'serpkName', title: '服务名称', width: 180}
            ,{field: 'serpkOpenState', title: '是否频次', width: 180}
            ,{field:'serpkNum', title: '频次次数', width: 100}
            ,{field: 'serpkCreateTime', title: '创建时间', width: 150}
            ,{fixed: 'right', width:210, align:'center', toolbar: '#barRole'}
        ]]
        ,id: 'team'
        ,url: 'signAction.action'
        ,where: {act: 'pklist',strJson:JSON.stringify(qvo)}
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

//重置
function findReset(){
    $('#pkName').val("");
}
