<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/open/commonjs/tldLayui.jsp"%>
    <title>履约统计</title>
</head>
<body>
<div class="layui-form">
<table id="lytj" class="layui-table">
    <thead>
        <tr>
            <th width="100px"></th>
            <th>姓名</th>
            <th>性别</th>
            <th>年龄</th>
            <th>身份证</th>
        </tr>
    </thead>

    <tbody id="lytjBody">
    </tbody>
</table>
</div>
</body>

<script type="text/javascript" src="js/sign_performance_list.js?v=1.0"></script>
</html>
