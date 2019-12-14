<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/open/commonjs/tldLayui.jsp"%>
    <title>业务性日志列表</title>
    <style>
        .align_center{
            text-align: center;
        }
    </style>
</head>
<body>

<div class="layui-elem-field">
    <form id="form_qvo">
        <div class="layui-form-item">
            <label class="layui-form-label">起止时间</label>
            <div class="layui-input-inline">
                <input type="text" pofield="startTime" id="startTime" autocomplete="off" class="layui-input" style="width:40%;display:inline;"/>
                到
                <input type="text" pofield="endTime" id="endTime" autocomplete="off" class="layui-input" style="width:40%;display:inline;"/>
            </div>
            <label class="layui-form-label">业务名</label>
            <div class="layui-input-inline">
                <input type="text"  name="businessName" pofield="businessName" class="layui-input"/>
            </div>
            <label class="layui-form-label">表名</label>
            <div class="layui-input-inline">
                <input type="text"  name="businessTable" pofield="businessTable" class="layui-input"/>
            </div>
        </div>
    </form>
</div>
    <blockquote class="layui-elem-quote">
        <button onclick="findList()" class="layui-btn"><i class=layui-icon>&#xe615;</i>查询</button>
        <button onclick="resetData('form_qvo')" class="layui-btn layui-btn-small"><i class="layui-icon">&#xe625;</i>重置</button>
    </blockquote>

<table id="logTabel" lay-filter="logx">

</table>

</body>

<jsp:include page="mould/logCheck_mould.jsp" flush="false"/>
<script type="text/javascript" src="<%=request.getContextPath() %>/open/commonjs/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/intercept/cd/log/js/logCheck_list.js?v=1.0"></script>

</html>
