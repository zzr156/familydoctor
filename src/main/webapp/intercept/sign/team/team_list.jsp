<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/open/commonjs/tldLayui.jsp"%>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/open/commonjs/js/jautocomplete/jquery-ui.min.css">
    <script src="<%=request.getContextPath() %>/open/commonjs/js/jautocomplete/jquery-ui.min.js"></script>
    <title>团队管理</title>
</head>
<body bgcolor="white">
<%--查询条件--%>
<div id="form_qvo">
    <div class="mwrap" id="queryForm">
        <div class="layui-form-item">
            <label class="layui-form-label">选择机构</label>
            <div class="layui-input-inline">
                <input type="text"  pofield="drHospName" id="drHospName" class="layui-input">
                <input type="hidden"  pofield="drHospId" id="drHospId" class="layui-input">
            </div>
            <label class="layui-form-label">团队名称</label>
            <div class="layui-input-inline">
                <input type="text"  pofield="appTeamId" class="layui-input">
            </div>
            <label class="layui-form-label" style="width: 100px">团队负责人</label>
            <div class="layui-input-inline">
                <input type="text" pofield="appTeamDrId" class="layui-input">
            </div>

        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">有效标识</label>
            <div class="layui-input-inline">
                <select pofield="appTeamState" class="layui-input">
                    <option value="">请选择...</option>
                    <option value="1">是</option>
                    <option value="2">否</option>
                </select>
            </div>
        </div>
    </div>
    <blockquote class="layui-elem-quote">
        <button onclick="findList()" class="layui-btn layui-btn-small">
            <i class=layui-icon>&#xe615;</i>查询
        </button>
        <button onclick="forTeamAdd()" class="layui-btn layui-btn-small">
            <i class=layui-icon>&#xe61f;</i>新增
        </button>
    </blockquote>
</div>
<%--团队列表--%>
<table id="teamTabel" lay-filter="team">
</table>

</body>

<script type="text/javascript" src="js/team_list.js?v=1.0"></script>
<script type="text/html" id="barRole">
    <a class="layui-btn layui-btn-primary layui-btn-mini" lay-event="member"><i class=layui-icon>&#xe615;</i>成员管理</a>
    <a class="layui-btn layui-btn-mini " lay-event="modify" ><i class=layui-icon>&#xe642;</i>修改</a>
    <a class="layui-btn layui-btn-danger layui-btn-mini phis sysRoleAction_delete" lay-event="del"><i class=layui-icon>&#xe640;</i>删除</a>
</script>
</html>
