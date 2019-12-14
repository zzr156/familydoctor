<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/open/commonjs/tldLayui.jsp"%>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/open/commonjs/chosen_v1.8.2/chosen.css" />
    <script type="text/javascript" src="<%=request.getContextPath() %>/open/commonjs/chosen_v1.8.2/chosen.jquery.min.js"></script>
    <title>成员管理</title>
</head>
<body bgcolor="white">
<form class="layui-form-pane" id="form_qvo">
    <div class="layui-form-item">
        <label class="layui-form-label">成员姓名</label>
        <div class="layui-input-block">
            <select pofield="appMemName" id="appMemName" data-live-search="true" onchange="addDr($(this))" class="layui-input" validator='{"msg":"成员姓名不能为空!"}'>
                <option value="">--请选择--</option>
            </select>
        </div>
        <label class="layui-form-label">工作类型</label>
        <div class="layui-input-block">
            <select pofield="appWorkType" id="appWorkType" class="layui-input" validator='{"msg":"工作类型不能为空!"}'>
                <option value="">--请选择--</option>
            </select>
        </div>
        <label class="layui-form-label">职称</label>
        <div class="layui-input-block">
            <select  name="city" id="drRole" pofield="drRole" class="layui-input" validator='{"msg":"职称不能为空!"}'>
                <option value="">选择职位</option>
                <option value="1.1">主任医师</option>
                <option value="1.2">副主任医师</option>
                <option value="1.3">主治（主管）医师</option>
                <option value="1.4">医师</option>
                <option value="1.5">医士</option>
                <option value="2.1">主任药师</option>
                <option value="2.2">副主任药师</option>
                <option value="2.3">主管药师</option>
                <option value="2.4">药师</option>
                <option value="2.5">药士</option>
                <option value="3.1">主任护师</option>
                <option value="3.2">副主任护师</option>
                <option value="3.3">主管护师</option>
                <option value="3.4">护师</option>
                <option value="3.5">护士</option>
                <option value="4.1">主任技师</option>
                <option value="4.2">副主任技师</option>
                <option value="4.3">主管技师</option>
                <option value="4.4">技师</option>
                <option value="4.5">技士</option>
            </select>
        </div>
        <label class="layui-form-label">联系电话</label>
        <div class="layui-input-block">
            <input type="text" id="drTel" name="drTel" pofield="drTel" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <input type="hidden" id="id" pofield="id">
            <a class="layui-btn" id="add" onclick="addFrom();">确认新增</a>
            <a class="layui-btn" id="modify" onclick="modifyFrom();" style="display: none">确认修改</a>
            <a class="layui-btn" id="forAdd" onclick="forAdd();" style="display: none">返回新增</a>
            <a class="layui-btn" onclick="defualtHref('team_list.jsp?1=1')">返回</a>
        </div>
    </div>
</form>

<%--成员列表--%>
<table id="memberTabel" lay-filter="member">
</table>

</body>

</body>
<script type="text/javascript" src="js/team_member.js?v=1.0"></script>
<script type="text/html" id="barMember">
    <a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="del"><i class=layui-icon>&#xe640;</i>删除</a>
    <a class="layui-btn layui-btn-mini" lay-event="modify"><i class=layui-icon>&#xe642;</i>修改</a>
</script>
</html>
