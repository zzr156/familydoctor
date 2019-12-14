/**
 * Created by lenovo on 2017/11/10.
 */





var qvo={};
var vo={};
var menucode=getQueryString("loginMenuId");


<!--list-->
$(function(){
    $("#hospId").val(orgid);
    layui.use('table', function(){
        table = layui.table;
        table.on('tool(sign)', function(obj){
            var ui = obj.data; //获得当前行数据
            if(obj.event == 'modify'){ //进入修改
                forTeamModify(ui);
            }else if(obj.event=='look'){//成员管理
                findlook(ui);
            }else if(obj.event=='del'){
                del(ui);
            }
        });
        // findList();
    });

})


//查询
function findList() {
    var index = layer.load(1);
    qvo = uiToData("form_qvo",qvo);//界面查询值绑定到变量
    table.render({
        height:'400'
        ,elem: '#signTabel'
        ,cols: [[
            {field: 'myIndex',title: '序号',width: 60,fixed: true}
            // ,{field: 'mentCityName', title: '所属市', width: 120,sort: true}
            ,{field: 'mentHospName', title: '所属医院', width: 180,sort: true}
            ,{field: 'mentTitle', title: '协议标题', width: 120}
            ,{field: 'mentStateName', title: '协议状态', width: 60,templet:'<div>{{ mentStateName(d.mentStateName)}}</div>'}
            ,{fixed: 'right', width:300, align:'center', toolbar: '#barRole'}
        ]]
        ,id: 'sign'
        ,url: 'openagreement.action'
        ,where: {act: 'findListHosp',strJson:JSON.stringify(qvo)}
        ,method: 'post'
        ,skin: 'row' //表格风格
        // ,size: 'sm'
        ,even: true
        ,page: true //是否显示分页
        ,limits: [5,10,15,20,25,50,100,200,1000]
        ,limit: 10 //每页默认显示的数量
        ,done: function(res, curr, count){
            vos=res.data;
            //遮罩关闭
            layer.close(index);
        }
    });
}

function findlook(ui){
    if(ui.id!=null && ui.id!=""){
        window.location.href= "agreement_edit.jsp?id="+ui.id+"&type="+true+"&menucode="+menucode;
    }
}

function forTeamModify(ui){
    if(ui.id!=null && ui.id!=""){
        window.location.href= "agreement_edit.jsp?id="+ui.id+"&type="+false;
    }

}

function AddTable(){
    var mentCityId = HospAreaCode.substr(0,4);
    window.location.href= "agreement_add.jsp?mentCityId="+mentCityId;
}


    

function del(ui){
    layer.confirm('确定要删除？',{btn:['确定','取消']},function(){
            doAjaxPost('openagreement.action?act=delete',{'id':ui.id,'hospId':orgid},function(data){
                if(data.msg=="true"){
                    layer.msg("删除成功!");
                    findList();
                } else {
                    layer.msg(data.msg);
                }
            });
        }
    );

}
function mentStateName(stateName){
    if(stateName == "启用"){
        return "<span style='color: red;'>"+stateName+"</span>"
    }else{
        return stateName;
    }
}
