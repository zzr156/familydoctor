var vo={};
var qvo={};
var table;
var laydate;

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
    layui.use('table', function(){
        table = layui.table;
        table.on('tool(logDelet)', function(obj){
            var ui = obj.data; //获得当前行数据
            if(obj.event=='view'){//删除
                view(ui);
            }
        });
        // findList();
    });

})

function findList() {   //查询列表
    qvo = uiToData("form_qvo",qvo);//界面查询值绑定到变量
    table.render({
        height:400
        ,elem: '#logTabel'
        ,cols: [[
            {field: 'myIndex',title: '序号',width: 60,fixed: true}
            ,{field: 'userName', title: '操作者', width: 100}
            ,{field: 'className', title: '操作实体', width: 250}
            ,{field: 'businessId', title: '业务主键', width: 150}
            ,{field: 'createDate', title: '删除时间', width: 160,sort: true}
            ,{fixed: 'right', title: '操作',width:80, align:'center', toolbar: '#barLog'}
        ]]
        ,id: 'logDelet'
        ,url: 'sysLogDeletAction.action'
        ,where: {act: 'list',strJson:JSON.stringify(qvo)}
        ,method: 'post'
        ,skin: 'row' //表格风格
        ,even: true
        ,page: true //是否显示分页
        ,limits: [5,10,15,20,25,50,100,200]
        ,limit: 10 //每页默认显示的数量
    });
}


function view(ui){
    var id = ui.id;
    myOpen("日志详请","logDelet_view.jsp?id="+id);
}



