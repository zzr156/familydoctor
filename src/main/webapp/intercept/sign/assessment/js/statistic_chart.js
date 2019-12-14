/**
 * 定义全局变量
 */
var reportData = window.parent.currentRes;// 报表原始数据

/**
 * 页面加载完成后立即执行
 */
$(function () {
    var lastRowData = reportData[reportData.length - 1];
    // 渲染左边饼图
    var signNum = lastRowData.signNum;
    var assessNum = lastRowData.assessNum;
    var notAssessNum = signNum - assessNum;
    renderLeftPieChart(assessNum, notAssessNum);

    // 渲染右边饼图
    var excellentNum = lastRowData.excellentNum;
    var goodNum = lastRowData.goodNum;
    var qualifiedNum = lastRowData.qualifiedNum;
    var unQualifiedNum = lastRowData.unQualifiedNum;
    renderRightPieChart(excellentNum, goodNum, qualifiedNum, unQualifiedNum);

    // 渲染柱状图
    var histogramCatagory = [];
    var histogramData = [];
    for (var i = 0; i < (reportData.length - 1); i++) {
        histogramCatagory.push(reportData[i].statisticObjName);
        histogramData.push(reportData[i].subsidy);
    }
    var xAxisName = "";
    if (reportData[0].statisticObjType == "1") {
        xAxisName = "地区";
    } else if (reportData[0].statisticObjType == "2") {
        xAxisName = "机构";
    } else if (reportData[0].statisticObjType == "3") {
        xAxisName = "团队";
    } else if (reportData[0].statisticObjType == "4") {
        xAxisName = "医生";
    }
    renderHistogram(xAxisName, histogramCatagory, histogramData);
});

/**
 * 渲染左饼图
 */
function renderLeftPieChart(assessNum, notAssessNum) {
    var leftPieChart = echarts.init(document.getElementById('leftPieChart'));// 初始化
    var data = [{name: '已考核', value: assessNum}, {name: '未考核', value: notAssessNum}];
    var option = {// 配置参数
        title: {
            text: '考核情况',
            subtext: '',
            x: 'center',
            textStyle: {
                fontSize: 15
            },
            left: 20
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'horizontal',
            x: 'left',
            y: 'bottom',
            data: ['已考核', '未考核']
        },
        toolbox: {
            show: true,
            feature: {
                mark: {show: true},
                dataView: {show: true, readOnly: false},
                magicType: {
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
                restore: {show: true},
                saveAsImage: {show: true}
            },
            itemGap: 5,
            itemSize: 12
        },
        calculable: true,
        series: [
            {
                name: '考核情况',
                type: 'pie',
                radius: '55%',
                center: ['50%', '60%'],
                data: data
            }
        ]
    };
    leftPieChart.setOption(option);
}

/**
 * 渲染右饼图
 */
function renderRightPieChart(excellentNum, goodNum, qualifiedNum, unQualifiedNum) {
    var rightPieChart = echarts.init(document.getElementById('rightPieChart'));// 初始化
    var data = [{name: '优', value: excellentNum}, {name: '良', value: goodNum}, {name: '合格', value: qualifiedNum}, {name: '不合格', value: unQualifiedNum}];
    var option = {// 配置参数
        title: {
            text: '考核评级情况',
            subtext: '',
            x: 'center',
            textStyle: {
                fontSize: 15
            },
            left: 20
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'horizontal',
            x: 'left',
            y: 'bottom',
            data: ['优', '良', '合格', '不合格']
        },
        toolbox: {
            show: true,
            feature: {
                mark: {show: true},
                dataView: {show: true, readOnly: false},
                magicType: {
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
                restore: {show: true},
                saveAsImage: {show: true}
            },
            itemGap: 5,
            itemSize: 12
        },
        calculable: true,
        series: [
            {
                name: '考核评级情况',
                type: 'pie',
                radius: '55%',
                center: ['50%', '60%'],
                data: data
            }
        ]
    };
    rightPieChart.setOption(option);
}

/**
 * 渲染柱状图
 */
function renderHistogram(xAxisName, histogramCatagory, histogramData) {
    var histogram = echarts.init(document.getElementById('histogram'));// 初始化
    var option = {
        title: {
            text: '绩效金额统计',
            subtext: '',
            x: 'center',
            textStyle: {
                fontSize: 15
            },
            left: 20
        },
        tooltip: {
            trigger: 'axis'
        },
        xAxis: {
            type: 'category',
            name: xAxisName,
            data: histogramCatagory
        },
        yAxis: {
            type: 'value',
            name: '',
            axisLabel: {
                formatter: '{value} 元'
            }
        },
        series: [{
            data: histogramData,
            type: 'bar',
            label: {
                normal: {
                    show: true,
                    position: 'top'
                }
            }
        }]
    };
    histogram.setOption(option);
}