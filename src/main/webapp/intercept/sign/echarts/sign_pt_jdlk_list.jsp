<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/open/commonjs/tldLayui.jsp"%>
    <script type="text/javascript" src="js/sign_pt_jdlk_list.js?v=1.0"></script>
    <title>建档立卡贫困人口签约信息</title>
</head>
<body bgcolor="white">

<form class="layui-form-pane" id="form_qvo">
    <div class="layui-form-item">
        <input type="hidden"  pofield="teamId" id="teamId" class="layui-input" />
        <input type="hidden"  pofield="economicType" id="economicType" class="layui-input" />
    </div>
</form>

<%--签约列表--%>
<table id="signJdlkTabel" lay-filter="sign">
</table>

</body>


<script type="text/html" id="barRole">
    <a class="layui-btn layui-btn-mini " lay-event="findHealthRecords" ><i class=layui-icon>&#xe615;</i>健康档案</a>
    <a class="layui-btn layui-btn-normal layui-btn-mini phis sysRoleAction_delete" lay-event="findMunicipalPlatform"><i class=layui-icon>&#xe615;</i>市级平台</a>
</script>
</html>
