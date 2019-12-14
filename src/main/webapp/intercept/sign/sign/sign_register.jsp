<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <object id="iccard" height="0" classid="clsid:84C4FA72-83B4-4AE8-8178-250A9410E27A"
            codebase="iccard.cab#version=1,1,8,1"></object>
    <%@ include file="/open/commonjs/tldLayui.jsp"%>
    <script type="text/javascript" src="js/sign_register.js?v=1.1.3"></script>
    <title>签约信息医保登记管理</title>
</head>
<body bgcolor="white">
<%--查询条件--%>
<div id="form_qvo">
    <input type="hidden" pofield="signHospId" id="signHospId"/>
    <div class="mwrap" id="queryForm">
        <div class="layui-form-item">
            <label class="layui-form-label">医社保卡</label>
            <div class="layui-input-inline">
                <input type="text"  pofield="patientCard" id="patientCard" class="layui-input">
            </div>
            <label class="layui-form-label">姓名</label>
            <div class="layui-input-inline">
                <input type="text"  pofield="patientName" id="patientName" class="layui-input">
            </div>
            <label class="layui-form-label">性别</label>
            <div class="layui-input-inline">
                <input type="text" pofield="patientGender" id="patientGender" class="layui-input">
            </div>
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">身份证</label>
            <div class="layui-input-inline">
                <input type="text"  pofield="patientIdno" id="patientIdno" class="layui-input">
            </div>
            <label class="layui-form-label">签约状态</label>
            <div class="layui-input-inline">
                <select id="signState" pofield="signState" class="layui-input">
                    <option value="">--请选择--</option>
                    <option value="0">未交互</option>
                    <option value="2">已交互</option>
                </select>
            </div>
            <label class="layui-form-label">医保类型</label>
            <div class="layui-input-inline">
                <select id="signlx" pofield="signlx" class="layui-input">
                    <option value="">全部</option>
                    <option value="1">医保</option>
                    <option value="2">农合</option>
                </select>
            </div>
        </div>

</div>

    <blockquote class="layui-elem-quote" style="text-align: center" id="blockId">
        <button class="layui-btn layui-btn-small  layui-btn-warm" onclick="readCard()">
            <i class='layui-icon'>&#xe615;</i>读卡
        </button>
        <%--<button onclick="findList()" class="layui-btn layui-btn-small">--%>
            <%--<i class=layui-icon>&#xe615;</i>查询--%>
        <%--</button>--%>
        <%--<button class="layui-btn layui-btn-small" lay-event="modify" onclick="signRegister(obj)">--%>
            <%--<i class=layui-icon>&#xe642;</i>签约--%>
        <%--</button>--%>
        <button class="layui-btn layui-btn-primary" onclick="reset()">
            <i class=layui-icon>&#xe60e;</i>重置
        </button>
    </blockquote>
</div>
<%--签约列表--%>
<table id="signTabel" lay-filter="sign">
</table>

</body>

<script type="text/html" id="barRole">
    <a class="layui-btn layui-btn-mini " lay-event="signRegister" ><i class=layui-icon>&#xe642;</i>签约</a>
</script>
</html>
