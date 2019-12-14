<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/open/commonjs/tldLayui.jsp" %>
    <title></title>
    <style>
        .layui-btn {
            margin-top: 1px;
            margin-right: 1px;
            margin-bottom: 1px;
            margin-left: 0
        }
        .layui-btn + .layui-btn {
            margin-left: 0
        }
    </style>
</head>
<body>
<!-- 附件序号显示区域 -->
<div id="btns" style="padding: 2px 2.5% 2px 2.5%;"></div>

<!-- 附件内容显示区域 -->
<div align="center" id="options"></div>

<!-- 删除按钮 -->
<div align="center" style="margin-top: 2px;margin-bottom: 5px;">
    <button id="del" class="layui-btn layui-btn-normal" onclick=delOptions(this)>删除</button>
</div>
</body>
<script type="text/javascript" src="js/view_picture.js?v=1.1.5"></script>
</html>
