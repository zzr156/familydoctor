/**
 * Created by lenovo on 2017/11/12.
 */

var vo={};
var qvo ={};
var qyname=[];
var qyvalue=[];
var fzqyl=[];
var orglist=[];
var gxrklist=[];
var rkzslist=[];
var signsum=0;
var CodeNmae = '';
var nowtime = '';
var teamKey = [];
$(function (){
    if(HospAreaCode.substr(0,4) != "3504"){
        initDissolutionWarning();//初始化医生解约预警
    }else{
        $("#ycfwdiv").hide();
    }
    nowtime = $("#nowtime").val();
    var gaga = new Date(nowtime.replace(/-/,"/")),
        _q = gaga.getFullYear()-1,
        _y = gaga.getFullYear(),
        _m = gaga.getMonth()+1

    $("#signDateStart").val(_q+"-01");
    if(_m.length >1){
        $("#signDateEnd").val(_y+"-"+0+_m);
    }else{
        $("#signDateEnd").val(_y+"-"+_m);
    }
    //莆田特有
    if(HospAreaCode==null){
        $("#pt_").remove();
    }else if(HospAreaCode.substr(0,4)!="3503"){
        $("#pt_").remove();
    }else {
        if(HospLevel.indexOf("4")>=0){
            $("#ptLevel").remove();
        }else{
            onptTable();
        }
    }
    if(HospAreaCode.substr(0,4)=="3501"){ // 陈希伟负责的福州
        $("#outpatient").show();
        $("#fill").show();
    }
    findAll();
});
function findAll(){
    if(HospLevel.indexOf("4")>=0){
        vo["hospId"]=orgid;
    }else {
        vo["areaId"]=HospAreaCode;
    }
    if(OrgName!="null" ){
        $("#OrgName").html(OrgName);
    }else{
       $("#OrgName").html("山西省家庭医生签约服务情况统计");
        vo["areaId"]="140000000000";
    }
    vo["yearStart"]=$("#signDateStart").val();
    vo["yearEnd"]=$("#signDateEnd").val();
    vo["hospLevel"]=HospLevel;

    doAjaxPost('signAction.action?act=appManageIndexCountNew',{strJson:JSON.stringify(vo)},function (data) {

        if(data.map != null){

            if(data.map.signTotalState.total!=null){
                for(var i=0;i<data.map.signTotalState.total.length;i++){
                    if(data.map.signTotalState.total[i].areaName!=null && data.map.signTotalState.total[i].areaName!=""){
                        qyname.push(data.map.signTotalState.total[i].areaName);
                        orglist.push(data.map.signTotalState.total[i].areaCode);
                    }else if(data.map.signTotalState.total[i].hospName!=null && data.map.signTotalState.total[i].hospName!=""){
                        /*if (data.map.signTotalState.total[i].hospName.toString().indexOf("服务中心") > 0) {
                            qyname.push(data.map.signTotalState.total[i].hospName.toString().substring(0, 2));
                            orglist.push(data.map.signTotalState.total[i].hospId);
                        } else {
                            qyname.push(data.map.signTotalState.total[i].hospName.toString().substring(3, 5));
                            orglist.push(data.map.signTotalState.total[i].hospId);
                        }*/
                        qyname.push(data.map.signTotalState.total[i].hospName.toString());
                        orglist.push(data.map.signTotalState.total[i].hospId);
                    }else if(data.map.signTotalState.total[i].teamName!=null && data.map.signTotalState.total[i].teamName!=""){
                        qyname.push(data.map.signTotalState.total[i].teamName);
                        teamKey.push(data.map.signTotalState.total[i].teamId);//add by WangCheng 增加团队主键
                    }
                }
                for(var j=0;j<data.map.signTotalState.total.length;j++){
                    qyvalue.push(data.map.signTotalState.total[j].yqy);
                    fzqyl.push(data.map.signTotalState.total[j].fzqyl.toFixed(2))
                    if(signsum==0){
                        signsum=data.map.signTotalState.total[j].yqy;
                    }else{
                        signsum+=data.map.signTotalState.total[j].yqy;
                    }
                }
                qyvalue = bubbleSort(qyvalue,qyname,orglist,fzqyl);
                // fzqyl = bubbleSort(fzqyl);
                $("#demoid").html(data.map.signTotal.yqy);
                $("#signsum").html(data.map.maprksum);
                $("#wdemo").html(data.map.signTotal.wqys);
                $("#zddemo").html(data.map.signTotal.zdrqyqy);
                $("#zdjjdemo").html(data.map.signTotal.economic);

                if(data.map.signTotal.areaName != null){
                    CodeNmae = data.map.signTotal.areaName;
                }
                if(data.map.signTotal.hospName != null){
                    CodeNmae = data.map.signTotal.hospName;
                }
                layui.use('element', function(){
                    var element = layui.element;
                   /* var vdemo=(signsum/data.map.maprksum)*100;
                    vdemo=Number(vdemo.toString().match(/^\d+(?:\.\d{0,2})?/));
                    element.progress('demo',vdemo+"%");*/
                    element.progress('zddemo',data.map.signTotal.zdrqwcl+"%");
                    element.progress('demo',data.map.signTotal.qywcl+"%");
                    element.progress('zdjjdemo',data.map.signTotal.economicRate+"%");
                    var gaga = new Date(nowtime.replace(/-/,"/"));//通过HTTP头部里的Date获取服务器上的时间
                        _y = gaga.getFullYear(),
                        _m = gaga.getMonth()+1,
                        _d = gaga.getDate();
                    $("#demodate").html(_y+"年"+_m+"月"+_d+"日");
                    $("#ycdemo").html(data.map.signTotal.yqy);
                });
            }
            if(data.map.mapOutpatient != undefined && data.map.mapOutpatient != null ){
                var optionmz = {
                    title : {
                        text: '诊疗次数占比',
                        subtext: '',
                        x:'center'
                    },
                    tooltip : {
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
                    legend: {
                        orient : 'horizontal',
                        x : 'left',
                        y : 'bottom',
                        data: ['基层医疗机构','二级医院','三级医院']
                    },
                    toolbox: {
                        show : true,
                        feature : {
                            mark : {show: true},
                            dataView : {show: true, readOnly: false},
                            magicType : {
                                show: true,
                                type: ['pie', 'funnel'],
                                option: {
                                    funnel: {
                                        x: '25%',
                                        width: '50%',
                                        funnelAlign: 'left',
                                        max: 1548
                                    }
                                }
                            },
                            restore : {show: true},
                            saveAsImage : {show: true}
                        }
                    },
                    calculable : true,
                    series : [
                        {
                            name:'机构等级',
                            type:'pie',
                            radius : '55%',
                            center: ['50%', '60%'],
                            data:data.map.mapOutpatient.OutpatientNumber
                        }
                    ]
                };
                var optionnd = {
                    title : {
                        text: '门诊医保费用',
                        subtext: '',
                        x:'center'
                    },
                    tooltip : {
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
                    legend: {
                        orient : 'horizontal',
                        x : 'left',
                        y : 'bottom',
                        data: ['上年度门诊医保费用','本年度已产生门诊医保费用']
                    },
                    toolbox: {
                        show : true,
                        feature : {
                            mark : {show: true},
                            dataView : {show: true, readOnly: false},
                            magicType : {
                                show: true,
                                type: ['pie', 'funnel'],
                                option: {
                                    funnel: {
                                        x: '25%',
                                        width: '50%',
                                        funnelAlign: 'left',
                                        max: 1548
                                    }
                                }
                            },
                            restore : {show: true},
                            saveAsImage : {show: true}
                        }
                    },
                    calculable : true,
                    series : [
                        {
                            name:'年度费用',
                            type:'pie',
                            radius : '55%',
                            center: ['50%', '60%'],
                            data:data.map.mapOutpatient.OutpatientCost
                        }
                    ]
                };

                var Chart_ybnd = echarts.init(document.getElementById('ybnd'));
                var Chart_ybmz = echarts.init(document.getElementById('ybmz'));
                Chart_ybnd.setOption(optionnd);
                Chart_ybmz.setOption(optionmz);
            }


        }

         var option1 = {
            title : {
                text: '重点人群签约数',
                subtext: '',
                x:'center',
                textStyle:{
                    fontSize:15
                },
                left:20
            },
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient : 'horizontal',
                x : 'left',
                y : 'bottom',
                data: ['儿童(0-6岁)','孕产妇','老年人','高血压','糖尿病','严重精神障碍','结核病','残疾人','脑血管病患者','冠心病患者','癌症患者','其他']
                //data:['直接访问','邮件营销','联盟广告','视频广告','搜索引擎']
            },
            toolbox: {
                show : true,
                feature : {
                    mark : {show: true},
                    dataView : {show: true, readOnly: false},
                    magicType : {
                        show: true,
                        type: ['pie', 'funnel'],
                        option: {
                            funnel: {
                                x: '25%',
                                width: '50%',
                                funnelAlign: 'left',
                                max: 1548
                            }
                        }
                    },
                    restore : {show: true},
                    saveAsImage : {show: true}
                },
                itemGap:5,
                itemSize:12
            },
            calculable : true,
            series : [
                {
                    name:'人群',
                    type:'pie',
                    radius : '55%',
                    center: ['50%', '60%'],
                    data:data.map.mapPers
                }
            ]
        };
        var option2 = {
            title : {
                text: '人口经济性质签约数',
                subtext: '',
                x:'center',
                textStyle:{
                    fontSize:15
                },
                left:0
            },
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient : 'horizontal',
                x : 'left',
                y : 'bottom',
                data: ['一般人口','建档立卡贫困人口','低保户','特困户（五保户）','计生独伤残家庭','计生独子女户','计生双女户','因病致贫户','其他']
            },
            toolbox: {
                show : true,
                feature : {
                    mark : {show: true},
                    dataView : {show: true, readOnly: false},
                    magicType : {
                        show: true,
                        type: ['pie', 'funnel'],
                        option: {
                            funnel: {
                                x: '25%',
                                width: '50%',
                                funnelAlign: 'left',
                                max: 1548
                            }
                        }
                    },
                    restore : {show: true},
                    saveAsImage : {show: true}
                },
                itemGap:5,
                itemSize:12
            },
            calculable : true,
            series : [
                {
                    name:'人口性质',
                    type:'pie',
                    radius : '55%',
                    center: ['50%', '60%'],
                    data:data.map.mapEconomic
                }
            ]
        };

        var  option3 = {
              title : {
                  text: '',
                  subtext: '',
                  textStyle:{
                      fontSize:15
                  },
                  left:20
              },
              tooltip : {
                  trigger: 'axis'
              },
              legend: {
                  data:['签约量(位)','签约率(%)']
              },
              toolbox: {
                  show : true,
                  feature : {
                      mark : {show: true},
                      dataView : {show: true, readOnly: false},
                      magicType : {show: true, type: ['line', 'bar']},
                      restore : {show: true},
                      saveAsImage : {show: true}
                  },
                  itemGap:5,
                  itemSize:12
              },
              calculable : true,
              grid: {
                borderWidth: 0,
                y: 100,
                y2: 100
              },
              xAxis : [
                  {
                     // type : 'category',
                      show: true,
                      data : qyname,
                      axisLabel: {
                          interval:0,
                          rotate:40
                      }
                  }
              ],
              yAxis : [
                  {
                      type : 'value',
                      show: true
                  }
              ],
              series : [
                  {
                      name:'签约量(位)',
                      type:'bar',
                      itemStyle:{
                          normal: {
                              label: {
                                  show: true,
                                  position: 'top',
                              }
                          }
                      },
                      data:qyvalue,
                    /*  markPoint : {
                          data : [
                              {type : 'max', name: '最大值'},
                              {type : 'min', name: '最小值'}
                          ]
                      },*/
                      markLine : {
                          data : [
                              {type : 'average', name: '平均值'}
                          ]
                      }
                  },
                  {
                      name:'签约率(%)',
                      type:'bar',
                      data:fzqyl,
                      itemStyle:{
                          normal: {
                              label: {
                                  show: true,
                                  position: 'top',
                                  formatter:function(params){
                                      return fzqyl[params.dataIndex]+"%"
                                  }
                              }
                          }
                      },
                      /*markPoint : {
                          data : [
                              {name : '年最高', value : 182.2, xAxis: 7, yAxis: 183, symbolSize:18},
                              {name : '年最低', value : 2.3, xAxis: 11, yAxis: 3}
                          ]
                      },*/
                      markLine : {
                          data : [
                              {type : 'average', name : '平均值'}
                          ]
                      }
                  }
              ]
        };

        var  option4 = {
            title : {
                text: '签约数据来源',
                subtext: '',
                x:'center',
                textStyle:{
                    fontSize:15
                },
                left:20
            },
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient : 'horizontal',
                x : 'left',
                y : 'bottom',
                data: ['APP','WEB']
            },
            toolbox: {
                show : true,
                feature : {
                    mark : {show: true},
                    dataView : {show: true, readOnly: false},
                    magicType : {
                        show: true,
                        type: ['pie', 'funnel'],
                        option: {
                            funnel: {
                                x: '25%',
                                width: '50%',
                                funnelAlign: 'left',
                                max: 1548
                            }
                        }
                    },
                    restore : {show: true},
                    saveAsImage : {show: true}
                },
                itemGap:5,
                itemSize:12
            },
            calculable : true,
            series : [
                {
                    name:'数据来源',
                    type:'pie',
                    radius : '55%',
                    center: ['50%', '60%'],
                    data:[{name:'APP',value:data.map.signTotal.appyqy},{name:'WEB',value:data.map.signTotal.webyqy}]
                }
            ]
        };

        var Chart_zdrq = echarts.init(document.getElementById('zdrq'));
        var Chart_rkxz = echarts.init(document.getElementById('rkxz'));
        var Chart_qyzt = echarts.init(document.getElementById('qyzt'));
        var Chart_sjly = echarts.init(document.getElementById('sjly'));

        Chart_zdrq.setOption(option1);
        Chart_rkxz.setOption(option2);
        Chart_qyzt.setOption(option3);
        Chart_sjly.setOption(option4);
        Chart_qyzt.on('click', function (params) {
            if(orglist[params.dataIndex]!=null && orglist[params.dataIndex]!=""){
                if(orglist[params.dataIndex].length>10){
                    window.location.href="echartses.jsp?Etype=false&orgValue="+orglist[params.dataIndex]+"&goto=1&CodeNmae="+encodeURI(CodeNmae);
                }else {
                    window.location.href="echartses.jsp?Etype=true&orgValue="+orglist[params.dataIndex]+"&goto=1&CodeNmae="+encodeURI(CodeNmae);
                }
            }else{
                openPoorSign(params);
            }
        });

        //————————————————————莆田测试区域/大佬门勿动——————————————————————
        if(HospAreaCode.substr(0,4)=="3503"){
            gxrklist=[];
            rkzslist=[];
            ptBiaoGeFuZhi(data.map);
            var option4 = {
                title : {
                    /* text: 'ECharts2 vs ECharts1',
                     subtext: 'Chrome下测试数据'*/
                },
                tooltip : {
                    trigger: 'item'
                },
                legend: {
                    data:[
                        '各项管理总数','',
                        '各项签约数'
                    ],
                    left:20
                },
                toolbox: {
                    show : true,
                    feature : {
                        mark : {show: true},
                        dataView : {show: true, readOnly: false},
                        magicType : {show: true, type: ['line', 'bar']},
                        restore : {show: true},
                        saveAsImage : {show: true}
                    },
                    itemGap:5,
                    itemSize:12
                },
                calculable : true,
                grid: {y: 70, y2:30, x2:20,left:50},
                xAxis : [
                    {
                        type : 'category',
                        data : ['签约人口数','重点人群数','建档立卡人口数','计生特殊家庭人口数']
                    },
                    {
                        type : 'category',
                        axisLine: {show:false},
                        axisTick: {show:false},
                        axisLabel: {show:false},
                        splitArea: {show:false},
                        splitLine: {show:false},
                        data : ['管理人口数','重点人群管理数','建档立卡人口管理数','计生特殊家庭人口管理数']
                    }
                ],
                yAxis : [
                    {
                        type : 'value',
                        axisLabel:{formatter:'{value}'}
                    }
                ],
                series : [
                    {
                        name:'各项管理总数',
                        type:'bar',
                        xAxisIndex:1,
                        itemStyle: {
                            normal: {
                                color:'rgba(252,206,16,0.5)',
                                label: {
                                    show: true,
                                    position: 'top',
                                    formatter: '{c}',
                                    textStyle:{fontSize:15}
                                }
                            }
                        },
                        data:rkzslist
                    },
                    {
                        name:'各项签约数',
                        type:'bar',
                        itemStyle: {
                            normal: {
                                color: function(params) {
                                    var colorList = [
                                        '#C1232B','#FCCE10','#27727B','#0000FF'
                                    ];
                                    return colorList[params.dataIndex]
                                },
                                label: {
                                    show: true,
                                    position: 'top',
                                    formatter: '{c}',
                                    textStyle:{
                                        fontSize:15
                                    }
                                }
                            }
                        },
                        data:gxrklist
                    }
                ]
            };
            var Chart_ptgxh = echarts.init(document.getElementById('ptgxh'));
            Chart_ptgxh.setOption(option4);
        }
        //————————————————————莆田测试区域/End——————————————————————
    })

};

/**
 * 莆田特性需求：打开建档立卡贫困人口的签约信息
 */
function openPoorSign(params){
    /*layer.open({
        type: 2,
        area: ['700px', '450px'],
        fixed: false, //不固定
        maxmin: true,
        //content: "sign_pt_jdlk_list.jsp?teamId="+orglist[e.dataIndex]+"&signsJjTypes=2"
        content:"helloWorld.jsp"
    });*/
    myOpen("建档立卡贫困人口签约信息","sign_pt_jdlk_list.jsp?teamKey="+teamKey[params.dataIndex]+"&economicType=2",null);
}

//刷新
function signsx(){
    signsum=0;
    qyname=[];
    qyvalue=[];
    orglist=[];
    fzqyl=[];
    $("#demoid").html("");
    $("#ycdemo").html("");
    $("#zddemo").html("");
    $("#zdjjdemo").html("");
    findAll();
}

//莆田个性化表格赋值
function ptBiaoGeFuZhi(data) {
    //柱状图赋值
    gxrklist.push(data.signTotal.yqy);
    gxrklist.push(data.signTotal.zdrqyqy);
    gxrklist.push(data.mapEconomic[1].value);
    gxrklist.push(data.mapEconomic[4].value);
    rkzslist.push(data.maprksum);
    rkzslist.push(data.mapzdrqsum);
    rkzslist.push(data.mapjklmsum);
    rkzslist.push(data.mapjsjtsum);
    //  管理数                                   签约数
    $("#rksum").text(data.maprksum);         $("#qyrk").text(data.signTotal.yqy);
    $("#zdrqsum").text(data.mapzdrqsum);     $("#qyzdrq").text(data.signTotal.zdrqyqy);
    $("#jklmsum").text(data.mapjklmsum);     $("#qyjklm").text(data.mapEconomic[1].value);
    $("#dbhsum").text(data.mapdbhsum);       $("#qydbh").text(data.mapEconomic[2].value);
    $("#tkhsum").text(data.maptkhsum);       $("#qytkh").text(data.mapEconomic[3].value);
    $("#jsjtsum").text(data.mapjsjtsum);     $("#qyjsjt").text(data.mapEconomic[4].value);
    // 各项签约率
    if(data.maprksum!="0"){
        $("#qyrkl").text(((parseInt(data.signTotal.yqy)/parseInt(data.maprksum))*100).toFixed(2));
    }else{
        $("#qyrkl").text(0);
    }
    if(data.mapzdrqsum!="0"){
        $("#qyzdrql").text(((parseInt(data.signTotal.zdrqyqy)/parseInt(data.mapzdrqsum))*100).toFixed(2));
    }else{
        $("#qyzdrql").text(0);
    }
    if(data.mapjklmsum!="0"){
        $("#qyjklml").text(((parseInt(data.mapEconomic[1].value)/parseInt(data.mapjklmsum))*100).toFixed(2));
    }else{
        $("#qyjklml").text(0);
    }
    if(data.mapdbhsum!="0"){
        $("#qydbhl").text(((parseInt(data.mapEconomic[2].value)/parseInt(data.mapdbhsum))*100).toFixed(2));
    }else{
        $("#qydbhl").text(0);
    }
    if(data.maptkhsum!="0"){
        $("#qytkhl").text(((parseInt(data.mapEconomic[3].value)/parseInt(data.maptkhsum))*100).toFixed(2));
    }else{
        $("#qytkhl").text(0);
    }
    if(data.mapjsjtsum!="0"){
        $("#qyjsjtl").text(((parseInt(data.mapEconomic[4].value)/parseInt(data.mapjsjtsum))*100).toFixed(2));
    }else{
        $("#qyjsjtl").text(0);
    }
}
//加载莆田表格
function onptTable() {
    qvo["hospId"]=orgid;
    qvo["areaId"]=HospAreaCode;
    qvo["yearStart"]=$("#signDateStart").val();
    qvo["yearEnd"]=$("#signDateEnd").val();
    qvo["hospLevel"]=HospLevel;
    layui.use('table', function(){
        var table = layui.table;
        //第一个实例
        table.render({
            height:'450'
            ,elem: '#ptTable'
            ,cols: [[
                 {field: 'orgName',title: '区县/卫生所',width: 180,align:'center'}
                ,{field: 'rksum', title: '人口总数', width: 150,align:'center'}
                ,{field: 'qysum', title: '签约数', width: 120,align:'center'}
                ,{field: 'qyrkl', title: '签约率', width: 100,align:'center'}
                ,{field: 'jdlksum', title: '建档立卡总数', width: 150,align:'center'}
                ,{field: 'qyjdlk', title: '签约数', width: 120,align:'center'}
                ,{field:'qyjdlkl', title: '签约率', width: 100,align:'center'}
                ,{field: 'zdrqsum', title: '重点人群总数', width: 150,align:'center'}
                ,{field:'qyzdrq', title: '签约数', width: 120,align:'center'}
                ,{field: 'qyzdrql', title: '签约数', width: 100,align:'center'}
            ]]
            ,id: 'ptSign'
            ,url: 'signAction.action'
            ,where: {act: 'appManageCountByBiaoGe',strJson:JSON.stringify(qvo)}
            ,method: 'post'
            ,skin: 'row' //表格风格
            // ,size: 'sm'
            ,even: true
            ,done: function(res, curr, count){
                if(res.data != null ){
                    vos=res.data;
                }else {
                    layer.msg(res.msg);
                }
            }
        });
    });
}


function switchs(){
    var Chart_qyzt = echarts.init(document.getElementById('qyzt'));
    Chart_qyzt.setOption(option, true);

}

/**
 * 初始化医生解约预警
 * Wangcheng
 */
function initDissolutionWarning(){
    vo["orgId"] = orgid;
    vo["drId"] = drid;
    doAjaxPost('signAction.action?act=initDissolutionWarning',{strJson:JSON.stringify(vo)},function (data) {
        if(data.msg == "true"){
           myOpen_back(null,"dissolution_warning_window.jsp",null);
        }
    });

}

function myOpen_back(name,url,callback) {
    layer.open({
        type: 2,
        maxmin: true,
        title: name,
        offset:"8px",
        shadeClose: false,
        scrollbar: true,//取消滚动条
        shade: 0.8,
        area: ['55%', '45%'],
        content: url,
        end:callback
    });
}