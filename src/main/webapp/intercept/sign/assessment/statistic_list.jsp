<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/open/commonjs/tldLayui.jsp" %>
    <link href="css/assessment_list.css?v=1.1.1" rel="stylesheet">
    <script type="text/javascript" src="js/statistic_list.js?v=1.1.6"></script>
    <title>考核统计</title>
</head>

<body bgcolor="white">
<!-- 查询条件 -->
<div id="form_qvo">
    <div class="mwrap" id="queryForm">
        <!-- 第一行 -->
        <div class="layui-form-item">
            <div>
                <label class="layui-form-label">地区</label>
                <div class="layui-input-inline">
                    <select id="areaCode" pofield="areaCode" class="layui-input" onchange="initHosp()">
                        <option value="">---请选择---</option>
                    </select>
                </div>
            </div>

            <div>
                <label class="layui-form-label">所属单位</label>
                <div class="layui-input-inline">
                    <select id="hospId" pofield="hospId" class="layui-input" onchange="initTeam()">
                        <option value="">---请选择---</option>
                    </select>
                </div>
            </div>

            <div>
                <label class="layui-form-label">团队名称</label>
                <div class="layui-input-inline">
                    <select id="teamId" pofield="teamId" class="layui-input" onchange="initDr()">
                        <option value="">---请选择---</option>
                    </select>
                </div>
            </div>

            <div>
                <label class="layui-form-label">签约医生</label>
                <div class="layui-input-inline">
                    <select id="drId" pofield="drId" class="layui-input">
                        <option value="">---请选择---</option>
                    </select>
                </div>
            </div>
        </div>

        <!-- 第二行 -->
        <div class="layui-form-item">
            <div>
                <label class="layui-form-label">经济类型</label>
                <div class="layui-input-inline">
                    <select pofield="economics" id="economics" class="layui-input">
                        <option value="">---请选择---</option>
                        <option value="2">建档立卡贫困人口</option>
                    </select>
                </div>
            </div>

            <div>
                <label class="layui-form-label">签约日期</label>
                <div class="layui-input-inline">
                    <input type="text" pofield="signDateStart" id="signDateStart" autocomplete="off" class="layui-input"
                           onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:45%;display:inline;"/>
                    ~&nbsp;
                    <input type="text" pofield="signDateEnd" id="signDateEnd" autocomplete="off" class="layui-input"
                           onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:45%;display:inline;"/>
                </div>
            </div>

            <div>
                <label class="layui-form-label">协议开始日期</label>
                <div class="layui-input-inline">
                    <input type="text" pofield="signFromDateStart" id="signFromDateStart" autocomplete="off"
                           class="layui-input"
                           onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:45%;display:inline;"/>
                    ~&nbsp;
                    <input type="text" pofield="signFromDateEnd" id="signFromDateEnd" autocomplete="off" class="layui-input"
                           onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:45%;display:inline;"/>
                </div>
            </div>

            <div>
                <label class="layui-form-label">协议截止日期</label>
                <div class="layui-input-inline">
                    <input type="text" pofield="signToDateStart" id="signToDateStart" autocomplete="off"
                           class="layui-input"
                           onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:45%;display:inline;"/>
                    ~&nbsp;
                    <input type="text" pofield="signToDateEnd" id="signToDateEnd" autocomplete="off" class="layui-input"
                           onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:45%;display:inline;"/>
                </div>
            </div>
        </div>

        <div class="layui-form-item">
            <div style="display: inline;">
                <label class="layui-form-label">历史记录</label>
                <div class="layui-input-inline">
                    <select pofield="history" id="history" class="layui-input">
                        <option value="">---请选择---</option>
                        <option value="1">显示</option>
                        <option value="0" selected>不显示</option>
                    </select>
                </div>
            </div>
        </div>

        <!-- 第三行 -->
        <blockquote class="layui-elem-quote" style="text-align: center" id="blockId">
            <button onclick="generateReport()" class="layui-btn layui-btn-small">
                <i class=layui-icon>&#xe615;</i>生成报表
            </button>
            <button onclick="exportStatisticReport()" class="layui-btn layui-btn-small">
                <i class=layui-icon>&#xe615;</i>导出报表
            </button>
            <button onclick="reset()" class="layui-btn layui-btn-primary">
                <i class=layui-icon>&#xe60e;</i>重置
            </button>
        </blockquote>
    </div>
</div>

<div>
    <div>
        <!-- 报表标题 -->
        <div align="center" style="font-family: 'KaiTi';font-size: 20px;margin-top: 15px;">
            <div id="statisticReportTitle" style="display: inline-block;"></div>
            <div id="statisticReportTitle2" style="display: none;">绩效考核报表</div>
        </div>
        <div align="right">
            <div id="drillUpBtnDiv" style="display: none;">
                <a style="margin-right: 10px;" class="layui-btn layui-btn-mini layui-btn-normal"
                   onclick="drillUp()">返回上级</a>
            </div>
            <div id="reportToChartBtnDiv" style="display: none;">
                <a style="margin-right: 10px;" class="layui-btn layui-btn-mini layui-btn-normal"
                   onclick="reportToChart()">查看图表</a>
            </div>
        </div>
    </div>

    <!-- 统计列表 -->
    <div>
        <table id="signTabel" lay-filter="sign"></table>
    </div>

</div>

</body>
<script type="text/html" id="statisticObjNameTpl">
    {{#  if (d.statisticObjType != '4') {  }}
        <a style="cursor: pointer;" class="layui-table-link" lay-event="drillDown" >{{d.statisticObjName}}</a>
    {{#  } else {  }}
        {{d.statisticObjName}}
    {{#  }  }}
</script>
<script type="text/html" id="subsidyTpl">
    <span style="color: #c7254e;">￥ {{formatMoney(d.subsidy, 0)}}</span>
</script>
</html>
