var vo={};
var qvo={};
var table;
var laydate;
layui.use('table', function(){
    table = layui.table;
    table.on('tool(logx)', function(obj){
        var ui = obj.data; //获得当前行数据
        if(obj.event=='view'){//查看
            logView(ui);
        }else if(obj.event=='del'){//删除
            logDel(ui);
        }
    });
});
layui.use('laydate', function(){
    laydate = layui.laydate;
    laydate.render({
        elem: '#startTime'
    });
    laydate.render({
        elem: '#endTime'
    });
});

$(function(){
    findList();
})

function findList() {   //查询列表
    qvo = uiToData("form_qvo",qvo);//界面查询值绑定到变量
    table.render({
        height:400
        // ,size: 'sm'
        ,elem: '#logTabel'
        ,cols: [[
            {field: 'myIndex',title: '序号',width: 60,fixed: true}
            ,{field: 'formatTime', title: '修改时间', width: 140,sort: true}
            ,{field: 'businessName', title: '业务名', width: 250}
            ,{field: 'businessTable', title: '表名', width: 250}
            ,{field: 'businessDesc', title: '修改内容', width: 200}
            ,{field: 'userName', title: '修改人', width: 100}
            ,{fixed: 'right', title: '操作',width:130, align:'center', toolbar: '#barLog'}
        ]]
        ,id: 'logx'
        ,url: 'logAction.action'
        ,where: {act: 'findList',strJson:JSON.stringify(qvo)}
        ,method: 'post'
        ,skin: 'row' //表格风格
        ,even: true
        ,page: true //是否显示分页
        ,limits: [5,10,15,20,25,50,100,200]
        ,limit: 10 //每页默认显示的数量
    });
}

function logDel(ui){    //删除
    layer.confirm('确认删除数据?', {
        skin: 'layui-layer-molv',
        btn: ['确定','取消']
    }, function() {
        var logsId = ui.id;
        doAjaxPostNotLoad('logAction.action?act=deleteLog', {'id': logsId}, delBack);
    });
}

function delBack(data){     //删除回调
    if(data.msg == "true"){
        showMsg("删除成功！");
        findList();
    }else if((data.msg == "false")) {
        showMsg("删除失败！");
    }
}

function logView(ui){
    var id = ui.id;
    var businessId = ui.businessId;
    myOpen("日志详请","logCheck_view.jsp?act=view&logsId="+id+"&businessId="+businessId);
}



