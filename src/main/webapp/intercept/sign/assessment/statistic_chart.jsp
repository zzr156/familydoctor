<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/open/commonjs/tldLayui.jsp" %>
    <title>统计图表</title>
</head>
<body style="height:100%">
    <!-- 图表显示区域 -->
    <div style="width: 100%;">
        <!-- 饼图 -->
        <div style="width: 100%;">
            <!-- 左饼图 -->
            <div style="width: 49%;height: 200px;display: inline-block;" id="leftPieChart"></div>

            <!-- 右饼图 -->
            <div style="width: 49%;height: 200px;display: inline-block;" id="rightPieChart"></div>
        </div>

        <!-- 柱状图 -->
        <div style="width: 100%;height: 400px;margin-top: 30px;" id="histogram"></div>
    </div>
</body>
<script type="text/javascript" src="<%=request.getContextPath() %>/open/commonjs/js/echarts.common.min.js"></script>
<script type="text/javascript" src="js/statistic_chart.js?v=1.1.0"></script>
</html>
