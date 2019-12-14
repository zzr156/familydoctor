<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/open/commonjs/tldLayui.jsp"%>
    <link href="css/report_add.css" rel="stylesheet" >
    <title>查看健康评估报告</title>

</head>
<body >
<form style="margin-top: 50px;" id="form_qvo">
    <input type="hidden" id="signId" pofield="signId">
    <input type="hidden" id="resultJson">
    <table align="center" border="1" cellpadding="0" cellspacing="0" >
        <tr>
            <th colspan="8" id="title" >健康评估报告</th>
        </tr>

        <tr>
            <td>姓名</td>
            <td pofield="patientName" id="patientName"></td>

            <td >性别</td>
            <td pofield="patientGender" id="patientGender"></td>

            <td>年龄</td>
            <td pofield="patientAge" id="patientAge"></td>

            <td >报告日期</td>
            <td pofield="reportDate" id="reportDate"></td>
        </tr>

        <tr>
            <td >签约协议日期</td>
            <td colspan="3" pofield="signToDate" id="signToDate"></td>

            <td >身份证</td>
            <td colspan="3" pofield="patientIdNo" id="patientIdNo"></td>
        </tr>

        <tr>
            <td >既往病史</td>
            <td colspan="7" pofield="medicalHistory" id="medicalHistory"></td>
        </tr>

        <tr>
            <td >服务人群类型</td>
            <td colspan="7" pofield="signPersGroup" id="signPersGroup"></td>
        </tr>

        <tr>
            <td >服务记录</td>
            <td colspan="7" pofield="serverLog" id="serverLog">
            </td>
        </tr>
        <tr>
            <td >健康状况总结</td>
            <td colspan="7">
                <div id="gxyDiv">签约年度血压动态变化趋势图</div>
                <div id="gxyDiagram" style="height:400px;width: 800px;padding: 20px"></div>
                <div id="tnbDiv">签约年度血糖动态变化趋势图</div>
                <div id="tnbDiagram" style="height:400px;width: 800px;padding: 20px"></div>
                <div style="height: 100px">
                    总结：
                    <textarea style="height:100%;width: 820px;" type="text" style="width: 820px" id="summary" pofield="summary"></textarea>
                </div>
            </td>
        </tr>
        <tr>
            <td >健康评价</td>
            <td colspan="7"><textarea style="height: 100px;width: 100%;" type="text" id="healthAssessment" pofield="healthAssessment"></textarea></td>
        </tr>
        <tr>
            <td >健康指导</td>
            <td colspan="7"><textarea style="height: 100px;width: 100%;" type="text" id="healthGuidance" pofield="healthGuidance"></textarea></td>
        </tr>
        <tr>
            <td >报告医生</td>
            <td colspan="7" pofield="doctorName" id="doctorName"></td>
        </tr>

    </table>
</form>

<div align="center" style="padding:20px;">
    <!--<button id="printReport" class="layui-btn layui-btn-lg layui-btn-normal" onclick="print()">打印报告</button>-->
    <button id="goto" class="layui-btn layui-btn-lg layui-bg-red" style="margin-left: 25px;" onclick="goto()">返回</button>
</div>

</body>
<script type="text/javascript" src="js/health_report_look.js?v=1.1.0"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/open/commonjs/js/echarts.common.min.js"></script>
</html>

