var ghlsh0_0000="";
var vo={};
var qvo={};
var param={};
var table;
// var jklj00 = "C:\\\\SFJK\\\\";
var jklj00 = "";
var hospId= "";
var show_url = "yibao/yibaowait.jsp?a=1";
layui.use('table', function(){
    table = layui.table;
    table.on('tool(sign)', function(obj){
        var ui = obj.data; //获得当前行数据
        if(obj.event == 'modify'){ //进入修改
            forTeamModify(ui);
        }else if(obj.event=='look'){//
            findlook(ui);
        }else if(obj.event=='print'){//
            lookprotocol(ui);
        }else if(obj.event=='changesign'){//变更团队
            changesign(ui);
        }
    });
});

$(function(){

    doAjaxPost('registerAction.action?act=commList',{}, function(data){
        if(data!=null && data.code !=""){
            jklj00 = data.code;
            hospId = data.result;
            findList();
        }else{
            alert("医保接口未配置！");
        }
    });

});
//读卡
function readcard(){
    var cardObj=publicReadCard();
    var ptidno= cardObj.ic_ybid;//身份证
    var ptsccno= cardObj.ic_icno;//医保卡号
    var ptname= cardObj.ic_name;//姓名
    if(""!=ptsccno && ""!=ptidno) {
        qvo["patientIdno"]=ptidno;
        doAjaxPost('registerAction.action?act=list', {idno: qvo.patientIdno}, function (data) {
            var birthday = data.vo.patientIdno.substr(6, 8);
            $("#ybCardNo").val(data.vo.patientCard);
            $("#patientName").val(data.vo.name);
            $("#patientSex").val(data.vo.sex);
            $("#patientIdno").val(data.vo.patientIdno);
            $("#patientBirth").val(birthday);
            $("#signState").val(data.vo.signState);
            $("#signDateStart").val(data.vo.signDateStart);
            $("#signDateEnd").val(data.vo.signDateEnd);
            $("#signFormId").val(data.vo.id);
            $("#signlx").val(data.vo.signlx);
            findList();
        });
    }else{
        alert("请重新读卡！");
    }
}
//登记
function register(){
    var cardObj=publicReadCard();
    var ptidno= cardObj.ic_ybid;//身份证
    var ptsccno= cardObj.ic_icno;//医保卡号
    var ptname= cardObj.ic_name;//姓名
    if(""!=ptsccno && ""!=ptidno) {
        qvo["patientIdno"]=ptidno;
        doAjaxPost('registerAction.action?act=findRegisterInfo', {idno: qvo.patientIdno}, function (data) {
            if (data.code == "900") {
                alert(data.msg);
                return;
            }
            if (data != null) {
                if (null != data.vo && null != data.vo.resultStr && "" != data.vo.resultStr) {
                    /*layer.confirm('请选择支付方式，个人医保账户支付或现金支付。', {
                        btn: ['个人医保账户支付','现金支付']
                    }, function(){
                        //  什么都不做
                    },function () {
                        //
                        data.vo.resultStr;
                    });*/

                    if (data.vo.hcRegistrationId == "0") {
                        //1门诊挂号刷卡
                        $YiBao.promptTitle = '';
                        $YiBao.isDebug = false;
                        $YiBao.reqCallBack = success_mzghsk;
                        $YiBao.reqOverCallBack = fail_mzghsk;
                        $YiBao.sfjkPath = jklj00;
                        $YiBao.requesturl = show_url;

                        var requestArrs = [];
                        var mzghsk = new Object();
                        mzghsk.request = 'TRUE';
                        mzghsk.cardno = qvo.ybCardNo;//医保卡号
                        mzghsk.yy_xming0 = "";

                        requestArrs['mzghsk'] = mzghsk;
                        $YiBao.postToYiBao(requestArrs);

                        function success_mzghsk(obj) {
                            var success = obj['mzghsk'][0]['success'];
                            if (success != true && success != "TRUE" && success != "true") {
                                alert("发送失败！该卡号不存在！");
                                return;
                            }
                            //医保挂号号为0，先挂医保号，再进行医保收费
                            //2.发送医保挂号请求
                            $YiBao.promptTitle = '';
                            $YiBao.isDebug = false;
                            $YiBao.reqCallBack = success_mzgh;
                            $YiBao.reqOverCallBack = fail_mzgh;
                            $YiBao.sfjkPath = jklj00;
                            $YiBao.requesturl = show_url;

                            var requestArrs = [];
                            var mzgh = new Object();
                            mzgh.request = 'TRUE';
                            mzgh.cardno =  qvo.ybCardNo;
                            mzgh.ksbm00 = "000001";
                            mzgh.ghksmc = "签约";
                            mzgh.ghfy00 = 0;

                            requestArrs['mzgh'] = mzgh;
                            $YiBao.postToYiBao(requestArrs);

                            function success_mzgh(obj) {
                                var success = obj['mzgh'][0]['success'];
                                if (success != true && success != "TRUE" && success != "true") {
                                    alert("发送失败！");
                                    return;
                                } else {
                                    var ksbm00 = obj['mzgh'][0]['ksbm00'];
                                    var ghksmc = obj['mzgh'][0]['ghksmc'];
                                    var ghlsh0 = obj['mzgh'][0]['ghlsh0'];
                                    ghlsh0_0000 = ghlsh0;
                                    param = {
                                        mainId: data.vo.mainId,
                                        ksbm00: ksbm00,
                                        ghksmc: ghksmc,
                                        ghlsh0: ghlsh0
                                    };
                                    doAjaxPost("registerAction.action?act=modifyRegisteredNumber", {strJson: JSON.stringify(param)}, function (result) {
                                        if (result.code == "SUCCESS") {
                                            hc_Register();
                                        } else {
                                            alert("医保挂号失败！");
                                        }
                                    });
                                }
                            }

                            function fail_mzgh() {
                                alert("刷卡超时！");
                            }
                        }

                        function fail_mzghsk() {
                            alert("刷卡超时！");
                        }
                    } else {
                        hc_Register();
                    }

                    function hc_Register() {
                        var listDr = data.vo.doctorListStr.split(";");
                        var qyksrq = data.vo.rsVo.signDateStart.split(" ");
                        var qyksrq_str = qyksrq[0].replace(new RegExp('-', 'gm'), "");//date
                        var qyjzrq = data.vo.rsVo.signDateEnd.split(" ");
                        var qyjzrq_str = qyjzrq[0].replace(new RegExp('-', 'gm'), "");
                        //3.发送医保收费请求
                        $YiBao.promptTitle = '';
                        $YiBao.isDebug = false;
                        $YiBao.reqCallBack = success_mzsf;
                        $YiBao.reqOverCallBack = fail_mzsf;
                        $YiBao.sfjkPath = jklj00;
                        $YiBao.requesturl = show_url;

                        var requestArrs = [];
                        var mzsf = new Object();
                        mzsf.request = 'TRUE';
                        mzsf.cardno = qvo.ybCardNo;
                        mzsf.mzlsh0 = ghlsh0_0000;
                       // mzsf.mzlsh0 = "YLZQYLS" + data.vo.rsVo.signNum;
                        mzsf.bqbm00 = "000003000012";
                        mzsf.cfxms0 = "2";
                        mzsf.yy_djlsh0 = "YLZYNDJ" + data.vo.rsVo.signNum;
                        mzsf.qyslr0 = listDr[0];
                        mzsf.qygx00 = "签约关系";
                        mzsf.yssls0 = data.vo.drCount;
                        mzsf.qyksrq = qyksrq_str;
                        mzsf.qyjzrq = qyjzrq_str;
                        mzsf.yyqyh0 = data.vo.rsVo.signNum;
                        mzsf.lxdh00 = data.vo.rsVo.tel;
                        mzsf.txdzhi = data.vo.rsVo.patientAddress;
                        //mzsf.qybz00 = "999;";
                        mzsf.qybz00=data.vo.signGroup;
                       // mzsf.fwnr00 = data.vo.rsVo.sersmPkValues;
                        mzsf.fwnr00 = "999;";
                        mzsf.fwfs00 = "999;";

                        requestArrs['mzsf'] = mzsf;

                        var yb0000 = new Object();
                        var ybarr0 = new Array();
                        var name00 = "西药费";
                        // yb0000.name00 = data.vo.count;
                        ybarr0[0] = "西药费=2";
                        yb0000.hiddenKey = ybarr0[0];
                        requestArrs['yb0000'] = yb0000;

                        //门诊收费明细
                        var sfmx = data.vo.resultStr.split(";");
                        var mzsfmx = new Object();
                        mzsfmx.hiddenKey = sfmx[0];
                        for (var i = 1; i < sfmx.length; i++) {
                            mzsfmx[i] = sfmx[i];
                        }
                        requestArrs['mzsfmx'] = mzsfmx;

                        //签约医生信息
                        var listArr = data.vo.doctorListStr.split(";");
                        var qyysxx = new Object();
                        qyysxx.hiddenKey = listArr[0];
                        for (var i = 1; i < listArr.length; i++) {
                            qyysxx[i] = listArr[i];
                        }
                        requestArrs['qyysxx'] = qyysxx;
                        $YiBao.postToYiBao(requestArrs);

                        function success_mzsf(obj) {
                            var success = obj['mzsf'][0]['success'];
                            if (success != true && success != "TRUE" && success != "true") {
                                alert(obj['mzsf'][0]['error']);
                                return;
                            }
                            var djlsh0 = obj['mzsf'][0]['djlsh0'];
                            var mzlsh0 = obj['mzsf'][0]['mzlsh0'];
                            var id0000 = obj['mzsf'][0]['id0000'];
                            var cardno = obj['mzsf'][0]['cardno'];
                            var xming0 = obj['mzsf'][0]['xming0'];
                            var xbie00 = obj['mzsf'][0]['xbie00'];
                            var brnl00 = obj['mzsf'][0]['brnl00'];
                            var bckbcs = obj['mzsf'][0]['bckbcs'];
                            var ghksmc = obj['mzsf'][0]['ghksmc'];
                            var zhzfe0 = obj['mzsf'][0]['zhzfe0'];
                            var grzfe0 = obj['mzsf'][0]['grzfe0'];
                            var jjzfe0 = obj['mzsf'][0]['jjzfe0'];
                            var gwybz0 = obj['mzsf'][0]['gwybz0'];
                            var bcbxf0 = obj['mzsf'][0]['bcbxf0'];
                            var sfrq00 = obj['mzsf'][0]['sfrq00'];
                            var sfsj00 = obj['mzsf'][0]['sfsj00'];
                            var sfrxm0 = obj['mzsf'][0]['sfrxm0'];
                            var cfxms0 = obj['mzsf'][0]['cfxms0'];
                            var xzqh00 = obj['mzsf'][0]['xzqh00'];
                            var grzhye = obj['mzsf'][0]['grzhye'];
                            var dqzhye = obj['mzsf'][0]['dqzhye'];
                            var pmlj00 = obj['mzsf'][0]['pmlj00'];
                            var tmlj00 = obj['mzsf'][0]['tmlj00'];

                            //20141124 商保
                            var dbzhzf = obj['mzsf'][0]['dbzhzf'];
                            var dbgrzf = obj['mzsf'][0]['dbgrzf'];
                            var dbjjzf = obj['mzsf'][0]['dbjjzf'];

                            //20151021南平医保大病保障金额
                            var sbzfje = obj['mzsf'][0]['sbzfje'];

                            //6.Ajax请求：记录医保返回收费数据到SF_JZB000，不成功则冲销医保收费，成功则提示“收费成功！”
                            param = {
                                djlsh0: djlsh0,
                                mzlsh0: mzlsh0,
                                id0000: id0000,
                                cardno: cardno,
                                xming0: xming0,
                                xbie00: xbie00,
                                brnl00: brnl00,
                                bckbcs: bckbcs,
                                ghksmc: ghksmc,
                                zhzfe0: zhzfe0,
                                grzfe0: grzfe0,
                                jjzfe0: jjzfe0,
                                gwybz0: gwybz0,
                                bcbxf0: bcbxf0,
                                sfrq00: sfrq00,
                                sfsj00: sfsj00,
                                sfrxm0: sfrxm0,
                                cfxms0: cfxms0,
                                xzqh00: xzqh00,
                                grzhye: grzhye,
                                dbzhzf: dbzhzf,
                                dbgrzf: dbgrzf,
                                dbjjzf: dbjjzf,
                                dqzhye: dqzhye,
                                sbzfje: sbzfje,
                                mainId: data.vo.mainId,
                                hospId: hospId,
                                brzjbh: qvo.patientIdno
                            };
                            doAjaxPost('registerAction.action?act=RegisterJS', {strJson: JSON.stringify(param)}, function processResB(returnObj) {
                                if (returnObj.code != "SUCCESS") {
                                    //冲销此医保收费单据号
                                    $YiBao.promptTitle = '';
                                    $YiBao.isDebug = false;
                                    $YiBao.reqCallBack = success_mzsf;
                                    $YiBao.reqOverCallBack = fail_mzsf;
                                    $YiBao.sfjkPath = jklj00;
                                    $YiBao.requesturl = show_url;

                                    var requestArrs = [];
                                    var mzsfcx = new Object();
                                    mzsfcx.request = 'TRUE';
                                    mzsfcx.cardno = cardno;
                                    mzsfcx.cxdjh0 = djlsh0;

                                    requestArrs['mzsfcx'] = mzsfcx;
                                    $YiBao.postToYiBao(requestArrs);
                                    alert("结算失败，医保已冲销，请重新结算!");
                                    return;
                                } else {
                                    var zfjeAll = "0.00";
                                    zfjeAll = accAdd(grzfe0, zfjeAll);
                                    alert("登记成功！自付： " + zfjeAll + "元");
                                }
                            });
                        }

                        function fail_mzsf() {
                            alert("刷卡超时！");
                        }
                    }
                } else {
                    alert("没有费用需要登记！");
                }
            }
        });
    }else{
        alert("请重新读卡！");
    }
}

//查询
function findList() {
    qvo = uiToData("form_qvo",qvo);//界面查询值绑定到变量
    table.render({
        height:'400'
        ,elem: '#registerTabel'
        ,cols: [[
             {field: 'myIndex',title: '序号',width: 60, align:'center'}
            ,{field: 'itemName', title: '项目名称', width: 250, align:'center'}
            ,{field: 'itemUnit', title: '单位', width: 100, align:'center'}
            ,{field: 'unitPrice', title: '单价', width: 100, align:'center'}
            ,{field: 'quantity', title: '数量', width: 100, align:'center'}
            ,{field: 'totalAmount', title: '总金额', width: 100, align:'center'}
            ,{field: 'billingDate', title: '操作日期', width: 150, align:'center'}
            ,{field: 'billingTime', title: '操作时间', width: 150, align:'center'}
        ]]
        ,id: 'register'
        ,url: 'registerAction.action'
        ,where: {act: 'getRegisterInfo',strJson:JSON.stringify(qvo)}
        ,method: 'post'
        ,skin: 'row' //表格风格
        // ,size: 'sm'
        ,even: true
        ,page: false //是否显示分页
        ,limits: [5,10,15,20,25,50,100,200,1000,50000]
        ,limit: 10 //每页默认显示的数量
    });
}

//JS加法，处理JS加法BUG
function accAdd(arg1,arg2){
    var r1,r2,m;
    try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
    try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
    m=Math.pow(10,Math.max(r1,r2))
    return (arg1*m+arg2*m)/m
}
//改签
function changeRegisterInfo(){
    var cardObj=publicReadCard();
    var ptidno= cardObj.ic_ybid;//身份证
    var ptsccno= cardObj.ic_icno;//医保卡号
    var ptname= cardObj.ic_name;//姓名
    if(""!=ptsccno && ""!=ptidno){
        qvo["patientIdno"]=ptidno;
        //1门诊挂号刷卡
        $YiBao.promptTitle = '';
        $YiBao.isDebug = false;
        $YiBao.reqCallBack = success_mzghsk;
        $YiBao.reqOverCallBack = fail_mzghsk;
        $YiBao.sfjkPath = jklj00;
        $YiBao.requesturl = show_url;

        var requestArrs = [];
        var mzghsk = new Object();
        mzghsk.request = 'TRUE';
        mzghsk.cardno = ptsccno;//医保卡号
        mzghsk.yy_xming0 = "";

        requestArrs['mzghsk'] = mzghsk;
        $YiBao.postToYiBao(requestArrs);

        function success_mzghsk(obj) {
            var success = obj['mzghsk'][0]['success'];
            if (success != true && success != "TRUE" && success != "true") {
                alert("发送失败！该卡号不存在！");
                return;
            }
            doAjaxPost('registerAction.action?act=changeRegisterInfo', {idno: qvo.patientIdno}, function processResB(returnObj) {
                if(returnObj.code=="200"){
                    var listDr = returnObj.vo.doctorListStr.split(";");
                    $YiBao.promptTitle = '';
                    $YiBao.isDebug = false;
                    $YiBao.reqCallBack = success_jtysgq;
                    $YiBao.reqOverCallBack = fail_jtysgq;
                    $YiBao.sfjkPath = jklj00;
                    $YiBao.requesturl = show_url;

                    var requestArrs = [];
                    var jtysgq = new Object();
                    jtysgq.cardno=ptsccno;
                    jtysgq.bqbm00="000003000012";
                    jtysgq.qyslr0=listDr[0];
                    jtysgq.qygx00="签约关系";
                    jtysgq.yssls0=returnObj.vo.drCount;

                    requestArrs['jtysgq'] = jtysgq;
                    //签约医生信息
                    var listArr = returnObj.vo.doctorListStr.split(";");
                    var qyysxx = new Object();
                    qyysxx.hiddenKey = listArr[0];
                    for (var i = 1; i < listArr.length; i++) {
                        qyysxx[i] = listArr[i];
                    }
                    requestArrs['qyysxx'] = qyysxx;
                    $YiBao.postToYiBao(requestArrs);
                    function success_jtysgq() {
                        var success = obj['jtysgq'][0]['success'];
                        if (success != true && success != "TRUE" && success != "true") {
                            alert("发送失败！");
                            return;
                        }else{
                            alert("改签成功！");
                        }
                    }
                    function fail_jtysgq(){
                        alert("刷卡超时！");
                    }
                }else{
                    alert("获取信息失败！");
                }
            });
        }
        function fail_mzghsk(){
            alert("刷卡超时！");
        }
    }else{
        alert("请重新读卡！");
    }
}
//退签
function cancelRegister(){
     var cardObj=publicReadCard();
     var ptidno= cardObj.ic_ybid;//身份证
     var ptsccno= cardObj.ic_icno;//医保卡号
     var ptname= cardObj.ic_name;//姓名
     if(""!=ptsccno && ""!=ptidno){
         qvo["patientIdno"]=ptidno;
         //1门诊挂号刷卡
         $YiBao.promptTitle = '';
         $YiBao.isDebug = false;
         $YiBao.reqCallBack = success_mzghsk;
         $YiBao.reqOverCallBack = fail_mzghsk;
         $YiBao.sfjkPath = jklj00;
         $YiBao.requesturl = show_url;

         var requestArrs = [];
         var mzghsk = new Object();
         mzghsk.request = 'TRUE';
         mzghsk.cardno = ptsccno;//医保卡号
         mzghsk.yy_xming0 = "";

         requestArrs['mzghsk'] = mzghsk;
         $YiBao.postToYiBao(requestArrs);

         function success_mzghsk(obj) {
             var success = obj['mzghsk'][0]['success'];
             if (success != true && success != "TRUE" && success != "true") {
                 alert("发送失败！该卡号不存在！");
                 return;
             }
             doAjaxPost('registerAction.action?act=changeRegister', {idno: qvo.patientIdno}, function processResB(returnObj) {
                 if(returnObj.code=="800"){
                     $YiBao.promptTitle = '';
                     $YiBao.isDebug = false;
                     $YiBao.reqCallBack = successB;
                     $YiBao.reqOverCallBack = failB;
                     $YiBao.sfjkPath = jklj00;
                     $YiBao.requesturl = show_url;

                     var requestArrs = [];
                     var mzsfcx = new Object();
                     mzsfcx.request = 'TRUE';
                     mzsfcx.cardno = ptsccno;
                     mzsfcx.cxdjh0 = returnObj.vo.hcDocumentBillId;

                     requestArrs['mzsfcx'] = mzsfcx;
                     $YiBao.postToYiBao(requestArrs);
                     function successB(obj) {
                         var success = obj['mzsfcx'][0]['success'];
                         if (success != true && success != "TRUE" && success != "true") {
                             alert("发送失败！");
                             return;
                         }
                         var zhzfe0 = obj['mzsfcx'][0]['zhzfe0'];
                         var jjzfe0 = obj['mzsfcx'][0]['jjzfe0'];
                         var grzfe0 = obj['mzsfcx'][0]['grzfe0'];
                         var gwybz0 = obj['mzsfcx'][0]['gwybz0'];
                         var djlsh0 = obj['mzsfcx'][0]['djlsh0'];
                         var dbzhzf = obj['mzsfcx'][0]['dbzhzf'];
                         var dbgrzf = obj['mzsfcx'][0]['dbgrzf'];
                         var dbjjzf = obj['mzsfcx'][0]['dbjjzf'];
                         var sbzfje = obj['mzsfcx'][0]['sbzfje'];
                         var sfrq00 = obj['mzsfcx'][0]['sfrq00'];
                         var sfsj00 = obj['mzsfcx'][0]['sfsj00'];
                         var cxdjh0 = obj['mzsfcx'][0]['cxdjh0'];
                         if (gwybz0 == undefined) {
                             gwybz0 = 0;
                         }
                         //6.Ajax请求：记录医保返回收费数据到SF_JZB000，不成功则冲销医保收费，成功则提示“收费成功！”
                         param={
                             djlsh0:djlsh0,zhzfe0:zhzfe0,grzfe0:grzfe0,jjzfe0:jjzfe0,gwybz0:gwybz0,sfrq00:sfrq00,sfsj00:sfsj00,
                             dbzhzf:dbzhzf,dbgrzf:dbgrzf,dbjjzf:dbjjzf,sbzfje:sbzfje,hospId:hospId,brzjbh:qvo.patientIdno,cxdjh0:cxdjh0
                         };
                         doAjaxPost('registerAction.action?act=RegisterCancelJS', {strJson:JSON.stringify(param)}, function processResB(returnObj) {
                             if(returnObj.code=="800"){
                                 alert("退签成功！");
                             }else{
                                 alert("退签失败！");
                             }
                         });
                     }
                     function  failB() {
                         alert("刷卡超时！");
                     }
                 }else{
                     alert("获取数据失败，或没有需要退签的信息！");
                 }
             });
         }
         function fail_mzghsk(){
             alert("刷卡超时！");
         }
     }else{
         alert("请重新读卡！");
     }

}
