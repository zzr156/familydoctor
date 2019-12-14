<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>家庭成员列表</title>
    <%@ include file="/open/commonjs/tldLayui.jsp" %>
</head>
<body bgcolor="white">
<div style="padding:0 10px;">
    <form class="layui-form-pane" id="form_vo">
        <blockquote class="layui-elem-quote" style="text-align: center;font-size: 20px">家庭成员信息</blockquote>
        <table id="signTabel" lay-filter="sign">
        </table>
    </form>
</div>
</body>
<script type="text/javascript" src="js/sign_familymember.js?v=1.1"></script>
</html>
