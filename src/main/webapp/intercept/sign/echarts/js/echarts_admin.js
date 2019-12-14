/**
 * Created by lenovo on 2017/11/12.
 */
/**
 * Created by lenovo on 2017/11/12.
 */
var Chart_zdrq=null;
var Chart_rkxz=null;
var Chart_qyzt=null;
var vo={};
var qyname=[];
var qyvalue=[];
var orglist=[];
var signsum=0;
/*layui.use('laydate', function(){
 laydate = layui.laydate;
    var gaga = new Date(),
     _y = gaga.getFullYear()
laydate.render({
 elem: '#signDateStart',
 type: 'month',
 value: new Date(_y+"-01")
 });
 laydate.render({
 elem: '#signDateEnd',
 type: 'month',
 value: new Date()
 });
});*/
var CodeNmae = '';

var dmao=0;
var zddmao=0;
function startbar(){
    var showbar=setInterval("setbar()");
}
function setbar(){
    document.getElementById("bar").style.width=dmao+"%";
    document.getElementById("bar").innerHTML=dmao+"%";

    document.getElementById("bars").style.width=zddmao+"%";
    document.getElementById("bars").innerHTML=zddmao+"%";

}


$(function (){

    var gaga = new Date(),
        _y = gaga.getFullYear(),
        _m = gaga.getMonth()+1
    $("#signDateStart").val(_y+"-01");
    $("#signDateEnd").val(_y+"-"+_m);
    onede();
});

function onede(){
      var code=HospAreaCode.substring(0,4);

     $("#OrgName").html("山西省家庭医生签约服务情况统计");
     vo["areaId"]=code+"00000000";

    vo["yearStart"]=$("#signDateStart").val();
    vo["yearEnd"]=$("#signDateEnd").val();
    /* vo["yearStart"]="2017-01";
     vo["yearEnd"]="2017-12";*/
    vo["hospLevel"]=HospLevel;

    doAjaxPost('signAction.action?act=appManageIndexCountNew',{strJson:JSON.stringify(vo)},function (data) {
        qyname=[];
        qyvalue=[];
        orglist=[];
        signsum=0;
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
                    }

                }
                for(var j=0;j<data.map.signTotalState.total.length;j++){
                    qyvalue.push(data.map.signTotalState.total[j].yqy);

                    if(signsum==0){
                        signsum=data.map.signTotalState.total[j].yqy;
                    }else{
                        signsum+=data.map.signTotalState.total[j].yqy;
                    }
                }
                $("#demoid").html(data.map.signTotal.yqy);
                $("#signsum").html(data.map.maprksum);
                $("#wdemo").html(data.map.signTotal.wqys);
                $("#zddemo").html(data.map.signTotal.zdrqyqy);
                if(data.map.signTotal.areaName != null){
                    CodeNmae = data.map.signTotal.areaName;
                }
                if(data.map.signTotal.hospName != null){
                    CodeNmae = data.map.signTotal.hospName;
                }
                // 进度条
                dmao=data.map.signTotal.qywcl;
                zddmao=data.map.signTotal.zdrqwcl;
                startbar();

               /* layui.use('element', function(){
                    var element = layui.element;
                    /!*var vdemo=(signsum/data.map.maprksum)*100;
                    vdemo=Number(vdemo.toString().match(/^\d+(?:\.\d{0,2})?/));
                    element.progress('demo',vdemo+"%");*!/
                    element.progress('zddemo',data.map.signTotal.zdrqwcl+"%");
                    element.progress('demo',data.map.signTotal.qywcl+"%");
                });*/
                var gaga = new Date(),
                    _y = gaga.getFullYear(),
                    _m = gaga.getMonth()+1,
                    _d = gaga.getDate();
                $("#demodate").html(_y+"年"+_m+"月"+_d+"日");
                $("#ycdemo").html(data.map.signTotal.yqy);
            }
        }

         var option1 = {
            title : {
                text: '重点人群签约数',
                subtext: '',
                x:'center'
            },
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient : 'vertical',
                x : 'left',
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
                }
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
                x:'center'
            },
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient : 'vertical',
                x : 'left',
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
                }
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
            title: {
               /* x: 'center',
                text: '',
                subtext: 'Rainbow bar example',
                link: 'http://echarts.baidu.com/doc/example.html'*/
            },
            tooltip: {
                trigger: 'item'
            },
            toolbox: {
                show: true,
                feature: {
                    /*dataView: {show: true, readOnly: false},
                    restore: {show: true},
                    saveAsImage: {show: true}*/
                }
            },
            calculable: true,
            grid: {
                borderWidth: 0,
                y: 100,
                y2: 100
            },
            xAxis: [
                {
                 //   type: 'category',
                    show: true,
                    data: qyname,
                    axisLabel: {
                        interval:0,
                        rotate:40
                    }
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    show: true
                }
            ],
            series: [
                {
                    name: '',
                    type: 'bar',
                    itemStyle: {
                        normal: {
                            color: function(params) {
                                // build a color map as your need.
                                var colorList = [
                                    '#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
                                    '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
                                    '#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0'
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
                    data: qyvalue,
                    dataorg:orglist,
                    markPoint: {
                        tooltip: {
                            trigger: 'item',
                            backgroundColor: 'rgba(0,0,0,0)',
                            formatter: function(params){
                                return '<img src="'
                                    + params.data.symbol.replace('image://', '')
                                    + '"/>';
                            }
                        }
                        /*data: [
                            /!*{xAxis:0, y: 350, name:'Line', symbolSize:20, symbol: 'image://../asset/ico/折线图.png'},
                            {xAxis:1, y: 350, name:'Bar', symbolSize:20, symbol: 'image://../asset/ico/柱状图.png'},
                            {xAxis:2, y: 350, name:'Scatter', symbolSize:20, symbol: 'image://../asset/ico/散点图.png'},
                            {xAxis:3, y: 350, name:'K', symbolSize:20, symbol: 'image://../asset/ico/K线图.png'},
                            {xAxis:4, y: 350, name:'Pie', symbolSize:20, symbol: 'image://../asset/ico/饼状图.png'},
                            {xAxis:5, y: 350, name:'Radar', symbolSize:20, symbol: 'image://../asset/ico/雷达图.png'},
                            {xAxis:6, y: 350, name:'Chord', symbolSize:20, symbol: 'image://../asset/ico/和弦图.png'},
                            {xAxis:7, y: 350, name:'Force', symbolSize:20, symbol: 'image://../asset/ico/力导向图.png'},
                            {xAxis:8, y: 350, name:'Map', symbolSize:20, symbol: 'image://../asset/ico/地图.png'},
                            {xAxis:9, y: 350, name:'Gauge', symbolSize:20, symbol: 'image://../asset/ico/仪表盘.png'},
                            {xAxis:10, y: 350, name:'Funnel', symbolSize:20, symbol: 'image://../asset/ico/漏斗图.png'},*!/
                        ]*/
                    }
                }
            ]
        };


            // Chart_qyzt.dispose;

        Chart_zdrq = echarts.init(document.getElementById('zdrq'));
        Chart_rkxz = echarts.init(document.getElementById('rkxz'));

        Chart_qyzt = echarts.init(document.getElementById('qyzt'));

        Chart_zdrq.setOption(option1,true);
        Chart_rkxz.setOption(option2,true);
        // Chart_qyzt.resize();
       Chart_qyzt.setOption(option3,true);
        Chart_qyzt.on('click', function (params) {

            var test = window.location.href;
            if(orglist[params.dataIndex]!=null && orglist[params.dataIndex]!=""){
                if(orglist[params.dataIndex].length>10){
                   if(test.indexOf("intercept")>0){
                       window.location.href="echartses_admin.jsp?Etype=false&orgValue="+orglist[params.dataIndex]+"&goto=1&CodeNmae="+params.name;
                   }else{
                       window.location.href="intercept/sign/echarts/echartses_admin.jsp?Etype=false&orgValue="+orglist[params.dataIndex]+"&goto=1&CodeNmae="+params.name;
                   }

                }else {
                    window.location.href="intercept/sign/echarts/echartses_admin.jsp?Etype=true&orgValue="+orglist[params.dataIndex]+"&goto=1&CodeNmae="+params.name;
                }
            }

        });
    })

};


function signsx(){

  //  var Chart_qyzt = echarts.init(document.getElementById('qyzt'));
    //Chart_qyzt.clean();
    //Chart_qyzt.setOption(option,true)
    //Chart_qyzt.setOption("");
    //history.go(0);
    onede();
//window.location.href='intercept/sign/echarts/echarts_admin.jsp?';
}




