<%@ page import="java.util.Calendar" %>
<%@ page import="com.ylz.packcommon.common.util.ExtendDate" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/open/commonjs/tldLayui.jsp"%>
    <title>日志列表</title>
    <style>
        .align_center{
            text-align: center;
        }
    </style>
</head>
<body style="background: white">

<div class="mwrap">
    <form id="form_qvo">
        <div class="layui-form-item">
            <label class="layui-form-label">登录时间</label>
            <div class="layui-input-inline">
                <input type="text" pofield="startTime" id="startTime" autocomplete="off" class="layui-input" style="width:40%;display:inline;"/>
                到
                <input type="text" pofield="endTime" id="endTime" autocomplete="off" class="layui-input" style="width:40%;display:inline;"/>
            </div>
            <label class="layui-form-label">操作实体</label>
            <div class="layui-input-inline">
                <input type="text"  name="className" pofield="className" class="layui-input"/>
            </div>
        </div>
    </form>
</div>
    <blockquote class="layui-elem-quote">
        <button onclick="findList()" class="layui-btn layui-btn-small"><i class=layui-icon>&#xe615;</i>查询</button>
        <button onclick="resetData('form_qvo')" class="layui-btn layui-btn-small"><i class="layui-icon">&#xe625;</i>重置</button>
    </blockquote>

<table id="logTabel" lay-filter="logDelet">

</table>

</body>

<jsp:include page="mould/logDelet_mould.jsp" flush="false"/>
<script type="text/javascript" src="js/logDelet_list.js?v=1.0"></script>

</html>
