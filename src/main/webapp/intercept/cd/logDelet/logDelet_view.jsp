<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/open/commonjs/tldLayui.jsp"%>
    <title>日志详情</title>
</head>
<body style="background: white">
<table id="form_vo" class="layui-table">
    <tr>
        <td>
            <label class="layui-form-label">操作者:</label>
            <label class="layui-form-label" pofield="userName"></label>
        </td>
        <td>
            <label class="layui-form-label">操作实体:</label>
            <label class="layui-form-label" pofield="className"></label>
        </td>
    </tr>
    <tr>
        <td>
            <label class="layui-form-label">业务主键:</label>
            <label class="layui-form-label" pofield="businessId"></label>
        </td>
        <td>
            <label class="layui-form-label">删除时间:</label>
            <label class="layui-form-label" pofield="createDate"></label>
        </td>
    </tr>
    <tr>
        <td colspan="2">
            <label class="layui-form-label">数据内容:</label>
        </td>

    </tr>
    <tr>
        <td colspan="2">
            <textarea pofield="businessJson" style="width:100%;height: 400px">

            </textarea>
        </td>
    </tr>

</table>
</body>
<script type="text/javascript" src="js/logDelet_view.js?v=1.0"></script>

</html>
