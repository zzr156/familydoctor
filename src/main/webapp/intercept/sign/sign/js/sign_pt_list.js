/**
 * Created by lenovo on 2017/11/7.
 */
var vos={};
var qvo={};
var vo={};
var table;


var ptlx={
    1:"医保（职工）",
    2:"医保（城镇）",
    3:"新农合"
}

function signwebstate(b){
    if(b==null){
        b="未签约";
    }else if(b==0){
        b="预签约";
    }else if(b==2){
        b="已签约";
    }
    return b;
}

$(function(){
$("#ptorg").val(orgid);
    <!-- 初始化团队-->
    findteem();
    layui.use('table', function(){
        table = layui.table;
        table.on('tool(team)', function(obj){
            var ui = obj.data; //获得当前行数据
            if(obj.event == 'member'){ //进入签约
                signsave(ui);
            }else if(obj.event=='look'){//查看
                findlook(ui);
            }
        });
        // findsignsx();
    });
});


function findteem(){
    doAjaxPost('teamAction.action?act=findAll',{hospid:orgid},function(data){
        if(data!=null){
            $("#teamId").html("");
            var option1 = document.createElement('option');
            option1.setAttribute("value","");
            option1.innerText = "--请选择团队--";
            document.getElementById("ptteamid").appendChild(option1);
            $.each(data,function (k,v) {
                var option = document.createElement('option');
                option.setAttribute("value",v.id);
                option.innerText = v.teamName;
                document.getElementById("ptteamid").appendChild(option);
            })
        }
    },function(data){
        layer.msg("初始化异常，请联系管理员！");
    });
}

function signlx(){

    var ptlx= $("#ptlx").val();
    if(ptlx=="1"){
        $("#zgyb").show();
        $("#czyb").hide();
        $("#xnh").hide();
    }else if(ptlx=="2"){
        $("#czyb").show();
        $("#xnh").hide();
        $("#zgyb").hide();
    }else if(ptlx=="3"){
        $("#zgyb").hide();
        $("#czyb").hide();
        $("#xnh").show();
    }else if(ptlx==""){
        $("#zgyb").hide();
        $("#czyb").hide();
        $("#xnh").hide();
    }

}

function findsignsx(){
    qvo = uiToData("form_qvo",qvo);
    qvo["ptorg"]=qvo.ptorg.substring(3,qvo.ptorg.length);  //正式需要加上
    if(qvo.ptfamily.length>0){
        if(qvo.ptidno==""){
            layer.msg("查询家庭成员需填写身份证!");
            return;
        }
    }
    var index = layer.load(1);
    table.render({
        height:'400'
        ,elem: '#teamTabel'
        ,cols: [[
            {field: 'myIndex',title: '序号',width: 60,fixed: true}
            ,{field: 'ptname', title: '姓名', width: 60,sort: true}
            ,{field: 'ptgender', title: '性别', width: 60,sort: true}
            ,{field: 'ptnl', title: '年龄', width: 60}
            ,{field: 'ptidno', title: '身份证', width: 180}
            ,{field:'pttelephone', title: '联系电话', width: 100}
            ,{field: 'ptregion', title: '家庭地址', width: 160}
            ,{field: '', title: '参保类型', width: 100,templet:'<div>{{ptlx[d.ptlx]}}<div>'}
            ,{field: 'ptusername', title: '户主', width: 80}
            ,{field: 'ptnature', title: '人口性质', width: 80}
            ,{field: 'signteamname', title: '签约团队名称', width: 120}
            ,{field: '', title: '是否签约', width: 60,templet:'<div>{{signwebstate(d.signwebstate)}}<div>'}
            ,{field: '', title: '考核结果', width: 60}
            ,{fixed: 'right', width:150, align:'center', toolbar: '#barRole',templet:'<div>{{d.signwebstate}}<div>'}
        ]]
        ,id: 'team'
        ,url: 'signAction.action'
        ,where: {act: 'findsignsx',strJson:JSON.stringify(qvo)}
        ,method: 'post'
        ,skin: 'row' //表格风格
        // ,size: 'sm'
        ,even: true
        ,page: true //是否显示分页
        ,limits: [5,10,15,20,25,50,100,200]
        ,limit: 10 //每页默认显示的数量
        ,done: function(res, curr, count){
           vos=res.data;

            if(vos==null){
               if(qvo.ptidno!=null && qvo.ptidno!="") {
                   if(qvo.ptfamily.length>0){}else{
                       layer.open({
                           type: 1
                           ,title: false //不显示标题栏
                           ,closeBtn: false
                           ,area: '300px;'
                           ,shade: 0.8
                           ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
                           ,resize: false
                           ,btn: ['预签约','取消']
                           ,btnAlign: 'c'
                           ,moveType: 1 //拖拽模式，0或者1
                           ,content: '<div style="padding: 50px; line-height: 22px; background-color: #f2f2f2; color: #000; font-weight: 300;">该居民未签约是否进行预签约！</div>'
                           ,yes: function(index,layero){
                               window.location.href= "sign_add.jsp?addtpye=1&ptidno="+qvo.ptidno;
                           }
                           ,btn2:function () {

                           }
                       });
                   }
               }
            }
            //遮罩关闭
            layer.close(index);
        }
    });

}

function signsxcz() {
    $("#ptname").val("");
    $("#ptidno").val("");
    $("#ptlx").val("");
    $("#ptregion").val("");
    $("#signwebstate").val("");
    $("#ptfamily").val("");
    $("#teamId").val("");

    var ptfamily = document.getElementsByName("ptfamily");
    for(var i=0;i<ptfamily.length;i++){
            if(ptfamily[i].checked){
                ptfamily[i].checked = false;
            }
    }
    var signwebstate = document.getElementsByName("signwebstate");
    for(var i=0;i<signwebstate.length;i++){

        if(signwebstate[i].checked){
            signwebstate[i].checked = false;
        }
    }
    var ptnature = document.getElementsByName("ptnature");
    for(var i=0;i<ptnature.length;i++){
        if(ptnature[i].checked){
            ptnature[i].checked = false;
        }
    }
    $("#zgyb").hide();
    $("#czyb").hide();
    $("#xnh").hide();

}


function  findlook(ui) {
    if(ui.id !=null && ui.id!=""){
        window.location.href= "sign_look.jsp?id="+ui.id+"&addtpye=1";
        //window.location.href= "sign_modify.jsp?id="+ui.id;
    }
}

function signsave(ui){

    var ptidno=ui.ptidno;
    var ptsccno=ui.ptsccno;
    var ptname=ui.ptname;
    var ptgender=ui.ptgender;
    var ptlx=ui.ptlx;
    var ptregion=ui.ptregion;
    if(ptidno!=null && ptidno !=""){
        vo["ptidno"]=ptidno;
        vo["ptsccno"]=ptsccno;
        doAjaxPost('signAction.action?act=signfind',{strJson:JSON.stringify(vo)},function(data){
            if(data.code!="900"){
            if(data.vo!=null){
                if(data.vo.signhospid!=orgid) {
                    if(data.vo.signState=="0"){
                        layer.open({
                            type: 1
                            ,
                            title: false //不显示标题栏
                            ,
                            closeBtn: false
                            ,
                            area: '300px;'
                            ,
                            shade: 0.8
                            ,
                            id: 'LAY_layuipro' //设定一个id，防止重复弹出
                            ,
                            resize: false
                            ,
                            btn: ['确定', '取消']
                            ,
                            btnAlign: 'c'
                            ,
                            moveType: 1 //拖拽模式，0或者1
                            ,
                            content: '<div style="padding: 50px; line-height: 22px; background-color: #f2f2f2; color: #000; font-weight: 300;">该居民已预签约，可进行查看！</div>'
                            ,
                            success: function (layero) {
                                var btn = layero.find('.layui-layer-btn');
                                btn.find('.layui-layer-btn0').attr({
                                    href: "sign_look.jsp?id=" + data.vo.id
                                });
                            }
                        });
                    }else if(data.vo.signState=="2"){
                        layer.open({
                            type: 1
                            ,
                            title: false //不显示标题栏
                            ,
                            closeBtn: false
                            ,
                            area: '300px;'
                            ,
                            shade: 0.8
                            ,
                            id: 'LAY_layuipro' //设定一个id，防止重复弹出
                            ,
                            resize: false
                            ,
                            btn: ['确定', '取消']
                            ,
                            btnAlign: 'c'
                            ,
                            moveType: 1 //拖拽模式，0或者1
                            ,
                            content: '<div style="padding: 50px; line-height: 22px; background-color: #f2f2f2; color: #000; font-weight: 300;">该居民已签约，可进行查看！</div>'
                            ,
                            success: function (layero) {
                                var btn = layero.find('.layui-layer-btn');
                                btn.find('.layui-layer-btn0').attr({
                                    href: "sign_look.jsp?id=" + data.vo.id
                                });
                            }
                        });
                    }

                }else{
                    layer.msg("该居民已在"+data.vo.hospname+"签约，无权查看！！");
                }
            }else{
                document.url=location.href="sign_add.jsp?ptidno="+ptidno+"&ptsccno="+ptsccno+"&ptname="+ptname+"&ptgender="+ptgender+"&ptlx="+ptlx+"&ptregion="+ptregion+"&addtpye=1"
            }
            }else{
                layer.msg("系统异常，请联系管理员！");
            }
        });
    }

}