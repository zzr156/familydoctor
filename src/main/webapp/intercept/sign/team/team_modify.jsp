<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/open/commonjs/tldLayui.jsp"%>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/open/commonjs/chosen_v1.8.2/chosen.css" />
    <script type="text/javascript" src="<%=request.getContextPath() %>/open/commonjs/chosen_v1.8.2/chosen.jquery.min.js"></script>
    <title>团队新增</title>
</head>
<body bgcolor="white">
<%--新增--%>
<form class="layui-form-pane" id="form_vo">
        <div class="layui-form-item">
            <label class="layui-form-label">查询机构</label>
            <div class="layui-input-block">
                <input type="text"  pofield="teamHospName" id="teamHospName" class="layui-input" disabled="true"/>
                <input type="hidden"  pofield="teamHospId" id="teamHospId"/>
            </div>
            <label class="layui-form-label">团队名称<font color="red">*</font></label>
            <div class="layui-input-block">
                <input type="text" id="teamName"  pofield="teamName" class="layui-input" validator='{"msg":"团队名称不能为空!"}'/>
            </div>
            <label class="layui-form-label">团队负责人<font color="red">*</font></label>
            <div class="layui-input-block">
                <input type="hidden" id="teamDrId" name="teamDrId" pofield="teamDrId">
                <input type="hidden" id="teamDrName" name="teamDrName" pofield="teamDrName">
                <select id="teamDr" onchange="addCode()" class="layui-input" data-live-search="true" validator='{"msg":"团队负责人不能为空!"}'></select>
            </div>
            <label class="layui-form-label">医生代码</label>
            <div class="layui-input-block">
                <input id="teamDrCode" name="teamDrCode" pofield="teamDrCode" class="layui-input" disabled="true"/>
            </div>
            <label class="layui-form-label">团队类别</label>
            <div class="layui-input-block">
                <select id="teamType" pofield="teamType" class="layui-input"></select>
            </div>
            <label class="layui-form-label">有效标识</label>
            <div class="layui-input-block">
                <select pofield="teamState" class="layui-input">
                    <option value="1">是</option>
                    <option value="2">否</option>
                </select>
            </div>
            <label class="layui-form-label">联系电话<font color="red">*</font></label>
            <div class="layui-input-block">
                <input type="text" id="teamTel" name="teamTel" pofield="teamTel" class="layui-input" validator='{"msg":"联系电话不能为空!","rule":"myphone"}'>
            </div>
            <label class="layui-form-label">团队编号<font color="red">*</font></label>
            <div class="layui-input-block">
                <input type="text" id="teamCode" name="teamCode" pofield="teamCode" class="layui-input" validator='{"msg":"团队编号不能为空!"}'>
            </div>
            <%--<label class="layui-form-label">排序号</label>--%>
            <%--<div class="layui-input-block">--%>
                <%--<input type="text" id="teamSort" name="teamSort" pofield="teamSort" class="layui-input">--%>
            <%--</div>--%>
            <label class="layui-form-label">备注</label>
            <div class="layui-input-block">
                <textarea id="teamRemarks" name="teamRemarks" pofield="teamRemarks" class="layui-input"></textarea>
            </div>
        </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <input type="hidden" name="id" pofield="id">
            <input type="hidden" name="teamCreateTime" pofield="teamCreateTime">
            <input type="button" id="roleadd" value="保存" onclick="teamModify()" class="layui-btn" />
            <input type="button" id="back" value="返回" onclick="defualtHref('team_list.jsp?1=1')" class="layui-btn" />
        </div>
    </div>
</form>

</body>
<script type="text/javascript" src="js/team_modify.js?v=1.0"></script>

</html>
