var vo={};
var qvo={};
var vos={};
var table;

var teamKey=getQuerytoString("teamKey");
var economicType=getQueryString("economicType");

var userId = userid;
var userCount = "";
var userPwd = "";

$(function(){
    $("#teamId").val(teamKey);
    $("#economicType").val(economicType);

    //findUser(userId);

    layui.use('table', function(){
        table = layui.table;
        table.on('tool(sign)', function(obj){
            var ui = obj.data; //获得当前行数据
            if(obj.event == 'findHealthRecords'){ //查看建档立卡
                findHealthRecords(ui);
            }else if(obj.event=='findMunicipalPlatform'){//查看市级平台
                findMunicipalPlatform(ui);
            }
        });
        ptInitTable();
    });
});

/**
 * 根据userId获取用户信息
 * @param userId
 */
function findUser(userId){
    doAjaxPost('users.action?act=findUserByUserId',{userId : userId},function(data){
        debugger
        if(data != null){
            userCount = data.account;
            userPwd = data.userPassword;
        }
    });
}

//莆田建档立卡贫困人口签约表格初始化
function ptInitTable() {
    var index = layer.load(1);
    qvo = uiToData("form_qvo",qvo);//界面查询值绑定到变量
    table.render({
        height:'400'
        ,elem: '#signJdlkTabel'
        ,cols: [[
             {field: 'myIndex',title: '序号',width: 60,fixed: true}
            ,{field: 'signNum', title: '签约编号', width: 100,sort: true}
            ,{field: 'name', title: '姓名', width: 60,sort: true}
            ,{field: 'sex', title: '性别', width: 60,sort: true}
            ,{field: 'age', title: '年龄', width: 60,templet:'<div>{{  GetAge(d.patientIdno)}}</div>'}
            ,{field: 'patientIdno', title: '身份证', width: 150}
            ,{field: 'patientJmda', title: '居民档案', width: 150}
            ,{field:'tel', title: '联系电话', width: 100}
            ,{field: 'patientAddress', title: '详细地址', width: 150}
            ,{field:'signPersGroup', title: '服务类型', width: 100}
            ,{field: 'signsJjType', title: '人口经济性质', width: 100}
            ,{field: 'signlx', title: '医保类型', width: 80}
            ,{field: 'signDate', title: '签约时间', width: 90}
            ,{field: '', title: '签约状态', width: 90,templet:'<div>已签约</div>'}
            ,{field: 'signState', title: '医保交互', width: 80}
            ,{field: 'signTeamName', title: '签约团队', width: 80}
            ,{field: 'signDrName', title: '签约医生', width: 80}
            ,{field: 'batchOperatorName', title: '签约操作人', width: 80}
            ,{field: 'signgwpay', title: '公卫补助', width: 80}
            ,{field: 'signybpay', title: '医保预支付', width: 80}
            ,{field: 'signczpay', title: '财政补贴', width: 80}
            ,{field: 'signzfpay', title: '自费', width: 80}
            ,{field: 'upHpis', title: '签约来源', width: 80}
            ,{fixed: 'right', width:180, align:'center', toolbar: '#barRole'}
        ]]
        ,id: 'sign'
        ,url: 'signAction.action'
        ,where: {act: 'findPoorSign',strJson:JSON.stringify(qvo)}
        ,method: 'post'
        ,skin: 'row' //表格风格
        // ,size: 'sm'
        ,even: true
        ,page: true //是否显示分页
        ,limits: [5,10,25,50,100,500,1000,3000,5000,10000,30000,50000]
        ,limit: 10 //每页默认显示的数量
        ,done: function(res, curr, count){
            vos=res.data;
            if(vos != null){
                numberCount=res.data.length;
                $.each(res.data,function(k,v){
                    if(v.signState=="预签约"){
                        $("td[data-field='signState'] :eq("+k+")").text("未交互");
                    }else if(v.signState=="已签约"){
                        $("td[data-field='signState'] :eq("+k+")").text("已交互");
                    }
                })
            }
            //遮罩关闭
            layer.close(index);
        }
    });
}

/**
 * 查看建档立卡
 * @param ui
 */
function findHealthRecords(ui) {
    debugger
    var patientJmda = ui.patientJmda;
    if(patientJmda == null){
        layer.msg("该居民未建档，请先为该居民建档！");
    }else{
        //调用基卫健康档案界面
        //window.location.href = "http://18.1.3.27:70/sqyl/logonAction.do?method=logon2&userid=" + userCount + "&passwd=" + userPwd + "&df_id=" + patientJmda;
        //window.location.href = "http://18.1.3.28:7001/sqyl/logonAction.do?method=logon2&userid=csyh&passwd=c958d44fa3c5937507e0d2c06f63ae8f&df_id=" + patientJmda;
        window.open(window.location.href = "http://18.1.3.28:7001/sqyl/logonAction.do?method=logon2&userid=csyh&passwd=c958d44fa3c5937507e0d2c06f63ae8f&df_id=" + patientJmda);
    }
}

/**
 * 查看市级平台
 * @param ui
 */
function  findMunicipalPlatform(ui) {
    var patientJmda = ui.patientJmda;
    var patientIdno = ui.patientIdno;
    if(patientJmda == null){
        layer.msg("该居民未建档，请先为该居民建档！");
    }else{
        vo["idNo"] = patientIdno;
        vo["orgId"] = orgid;
        doAjaxPost("signAction.action?act=findMunicipalPlatformUrl",{strJson:JSON.stringify(vo)},function(data){
            debugger
            if(data.result == null){
                layer.msg("查找市级平台出错");
            }else{
                var object = eval("(" + data.result + ")");
                if(object.sjpturl != null){
                    //window.location.href = object.sjpturl+"&s="+Math.random();
                    window.open(object.sjpturl+"&s="+Math.random());
                }else {
                    layer.msg("未找到该居民的市级平台");
                }
            }
        });
    }
}