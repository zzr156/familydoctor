<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/open/commonjs/tldLayui.jsp"%>
    <title>履约统计</title>
</head>
<body>
<div class="layui-form">
    <%--<div id="form_qvo">
        <div>
            <input type="text" pofield="signDateStart" id="signDateStart" autocomplete="off" class="layui-input" onfocus="WdatePicker({dateFmt:'yyyy-MM'})" style="width:10%;display:inline;"/>
            ~&nbsp;
            <input type="text" pofield="signDateEnd" id="signDateEnd" autocomplete="off" class="layui-input" onfocus="WdatePicker({dateFmt:'yyyy-MM'})" style="width:10%;display:inline;"/>
            <button class="layui-btn" onclick="findList();">刷新</button>
        </div>
    </div>--%>
<table id="lytj" class="layui-table">
    <input type="hidden" id="nowtime" value="<%=new SimpleDateFormat("yyyy").format(new java.util.Date())%>" />
    <thead>
        <tr>
            <th></th>
            <th>应完成数</th>
            <th>已完成数</th>
            <th>未完成数</th>
        </tr>
    </thead>

    <tbody id="lytjBody">

    </tbody>
</table>
</div>
</body>

<script type="text/javascript" src="js/sign_performance.js?v=1.0"></script>
</html>
