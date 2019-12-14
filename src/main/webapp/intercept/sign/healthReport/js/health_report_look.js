/**
 * 查看健康评估报告核心js
 * WangCheng
 */
var vo = {};
var gxyDate = [];
var gxySzy = [];
var gxySsy = [];
var tnbDate = [];
var fastBloodValue = [];
var chnNumChar = ["零","一","二","三","四","五","六","七","八","九"];
var chnUnitChar = ["","十","百","千"];
var patientIdNo = getQueryString("patientIdno");
var lookType = getQueryString("lookType");

$(function () {
    if(necs(lookType)){
        if(lookType == '2'){
            $("#goto").hide();
        }
    }
    initData();
});

/**
 * 初始化数据
 */
function initData(){
    if (lookType && lookType === "2") {
    } else {
        vo["orgId"] = orgid;
    }
    vo["patientIdNo"] = patientIdNo;
    doAjaxPost('signAction.action?act=findHealthReport', {strJson: JSON.stringify(vo)}, function (data) {
        if(data.vo != null){
            dataToUi2(data.vo,"form_qvo");
            var object = eval("(" + data.vo.resultJson + ")");
            var serverCount = 1;
            var serverWay = "随访";
            var serverProject = "健康体检";
            if(object.grjbxxRecordDTOList.length != "0"){
                $("#medicalHistory").text(object.grjbxxRecordDTOList[0].jwbsjb);
            }
            if(object.gxysfRecordDTOList.length != "0"){
                $.each(object.gxysfRecordDTOList,function(k,v){
                    var serverGxyDiv = "<div style='float : left;width : 33.33%;margin-bottom: 10px;text-align: left;'>" +
                                    "<span>服务" + SectionToChinese(serverCount) + "：</span></br>" +
                                    "<span>服务日期：" + v.sfrq + "</span></br>" +
                                    "<span>服务医生：" + v.sfys + "</span></br>" +
                                    "<span>服务方式：" + v.sffl + "</span></br>" +
                                    "</div>";
                    $("#serverLog").append(serverGxyDiv);
                    gxyDate.push(v.sfrq);
                    gxySzy.push(v.szy);
                    gxySsy.push(v.ssy);
                    serverCount ++;
                });
                initGxyDiagram(gxyDate,gxySzy,gxySsy);
            }else {
                $("#gxyDiv").css("display","none");
                $("#gxyDiagram").css("display","none");
            }
            if(object.tnbsfRecordDTOList.length != "0"){
                $.each(object.tnbsfRecordDTOList,function(k,v){
                    var serverTnbDiv = "<div style='float : left;width : 33.33%;margin-bottom: 10px;text-align: left;'>" +
                        "<span>服务" + SectionToChinese(serverCount) + "：</span></br>" +
                        "<span>服务日期：" + v.sfrq + "</span></br>" +
                        "<span>服务医生：" + v.sfys + "</span></br>" +
                        "<span>服务方式：" + v.sffl + "</span></br>" +
                        "</div>";
                    $("#serverLog").append(serverTnbDiv);
                    tnbDate.push(v.sfrq);
                    fastBloodValue.push(v.kfxt);
                    serverCount ++;
                });
                initTnbDiagram(tnbDate,fastBloodValue);
            }else {
                $("#tnbDiv").css("display","none");
                $("#tnbDiagram").css("display","none");
            }
            if(object.jkjtRecordDTOList.length != "0"){
                $.each(object.jkjtRecordDTOList,function(k,v){
                    var serverJktjDiv = "<div style='float : left;width : 33.33%;margin-bottom: 10px;text-align: left;'>" +
                        "<span>服务" + SectionToChinese(serverCount) + "：</span></br>" +
                        "<span>服务日期：" + v.tjrq + "</span></br>" +
                        "<span>服务医生：" + v.zrys + "</span></br>" +
                        "<span>服务方式：" + serverProject + "</span></br>" +
                        "</div>";
                    $("#serverLog").append(serverJktjDiv);
                    serverCount ++;
                });
            }
            if(object.jsbsfRecordDTOList.length != "0"){
                $.each(object.jsbsfRecordDTOList,function(k,v){
                    var serverJsbDiv = "<div style='float : left;width : 33.33%;margin-bottom: 10px;text-align: left;'>" +
                        "<span>服务" + SectionToChinese(serverCount) + "：</span></br>" +
                        "<span>服务日期：" + v.sfrq + "</span></br>" +
                        "<span>服务医生：" + v.sfys + "</span></br>" +
                        "<span>服务方式：" + v.sffl + "</span></br>" +
                        "</div>";
                    $("#serverLog").append(serverJsbDiv);
                    serverCount ++;
                });
            }
            if(object.fjhsfFirstRecordDTOList.length != "0"){
                $.each(object.fjhsfFirstRecordDTOList,function(k,v){
                    var serverFjhFirstDiv = "<div style='float : left;width : 33.33%;margin-bottom: 10px;text-align: left;'>" +
                        "<span>服务" + SectionToChinese(serverCount) + "：</span></br>" +
                        "<span>服务日期：" + v.sfrq + "</span></br>" +
                        "<span>服务医生：" + v.pgys + "</span></br>" +
                        "<span>服务方式：" + serverWay + "</span></br>" +
                        "</div>";
                    $("#serverLog").append(serverFjhFirstDiv);
                    serverCount ++;
                });
            }
            if(object.fjhsfRecordDTOList.length != "0"){
                $.each(object.fjhsfRecordDTOList,function(k,v){
                    var serverFjhDiv = "<div style='float : left;width : 33.33%;margin-bottom: 10px;text-align: left;'>" +
                        "<span>服务" + SectionToChinese(serverCount) + "：</span></br>" +
                        "<span>服务日期：" + v.sfrq + "</span></br>" +
                        "<span>服务医生：" + v.pgys + "</span></br>" +
                        "<span>服务方式：" + serverWay + "</span></br>" +
                        "</div>";
                    $("#serverLog").append(serverFjhDiv);
                    serverCount ++;
                });
            }
            initDoctor(data.vo.doctorId);
        }
    });
}

/**
 * 初始化高血压图表
 */
function initGxyDiagram(gxyDate,gxySzy,gxySsy){
    var option = {
        // 标题
        title: {
            text: '血压值',
            subtext: ''
        },
        tooltip: {
            trigger: 'axis'
        },
        //图例名
        legend: {
            data:['舒张压','收缩压']
        },
        grid: {
            left: '3%',   //图表距边框的距离
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        //工具框，可以选择
        toolbox: {
            feature: {
                saveAsImage: {}
            }
        },
        //x轴信息样式
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: gxyDate,
            //坐标轴颜色
            axisLine:{
                lineStyle:{
                    color:'black'
                }
            },

        },

        yAxis : [
            {
                type : 'value',
                name : 'mmHg',
                min: 0,
                max: 200
            }
        ],
        series: [
            //虚线
            {
                name:'舒张压',
                type:'line',
                symbolSize:4,   //拐点圆的大小
                color:['red'],  //折线条的颜色
                data: gxySzy,
                smooth:false,   //关键点，为true是不支持虚线的，实线就用true
                itemStyle:{
                    normal:{
                        lineStyle:{
                            width:2,
                            type:'dotted'  //'dotted'虚线 'solid'实线
                        }
                    }
                }
            },
            //实线
            {
                name:'收缩压',
                type:'line',
                symbol:'circle',
                symbolSize:4,
                itemStyle:{
                    normal:{
                        color:'green',
                        borderColor:'red'  //拐点边框颜色
                    }
                },
                data:gxySsy
            }
        ]
    };
    var myChart = echarts.init(document.getElementById('gxyDiagram'));

    myChart.setOption(option);
}

/**
 * 初始化糖尿病图表
 */
function initTnbDiagram(tnbDate,fastBloodValue) {
    var option = {
        // 标题
        title: {
            text: '血糖值',
            subtext: ''
        },
        tooltip: {
            trigger: 'axis'
        },
        //图例名
        legend: {
            data:['空腹血糖值']
        },
        grid: {
            left: '3%',   //图表距边框的距离
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        //工具框，可以选择
        toolbox: {
            feature: {
                saveAsImage: {}
            }
        },
        //x轴信息样式
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: tnbDate,
            //坐标轴颜色
            axisLine:{
                lineStyle:{
                    color:'black'
                }
            },

        },

        yAxis : [
            {
                type : 'value',
                name : 'mmHg',
                min: 0,
                max: 30
            }
        ],
        series: [
            //实线
            {
                name:'空腹血糖值',
                type:'line',
                symbol:'circle',
                symbolSize:4,
                itemStyle:{
                    normal:{
                        color:'green',
                        borderColor:'red'  //拐点边框颜色
                    }
                },
                data:fastBloodValue
            }
        ]
    };
    var myChart = echarts.init(document.getElementById('tnbDiagram'));

    myChart.setOption(option);
}

/**
 * 初始化报告医生
 */
function initDoctor(doctorId){
    vo["drId"] = doctorId;
    doAjaxPost('signAction.action?act=getDoctorName', {strJson: JSON.stringify(vo)}, function (data) {
        if(data.vo != null){
            $("#doctorName").text(data.vo.drName);
        }
    });
}

/**
 * 打印
 */
function print(){
    $("form_qvo").printArea();
}

/**
 * 返回上一个页面
 */
function goto() {
    history.go(-1);
}

/**
 * 阿拉伯数字转换成中文
 * @param section
 * @returns {string}
 * @constructor
 */
function SectionToChinese(section){
    var strIns = '', chnStr = '';
    var unitPos = 0;
    var zero = true;
    while(section > 0){
        var v = section % 10;
        if(v === 0){
            if(!zero){
                zero = true;
                chnStr = chnNumChar[v] + chnStr;
            }
        }else{
            zero = false;
            strIns = chnNumChar[v];
            strIns += chnUnitChar[unitPos];
            chnStr = strIns + chnStr;
        }
        unitPos++;
        section = Math.floor(section / 10);
    }
    return chnStr;
}