<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/open/commonjs/tldLayui.jsp"%>
    <title>建档立卡异常居民删除界面</title>
</head>
<body bgcolor="white">
<!-- 新增 -->
<div style="padding:0 10px;">
    <form class="layui-form-pane" id="form_vo">
        <input type="hidden" id="id"  pofield="id"/>
        <div class="layui-form-item">
            <div class="layui-form-item layui-form-text">
                <label class="layui-form-label">删除原因</label>
                <div class="layui-input-block">
                    <textarea id="delReason" pofield="delReason" name="delReason" class="layui-textarea" validator='{"msg":"原因不能为空!"}'></textarea>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-block" style="text-align: center;">
                    <input type="button" id="back" value="返回" onclick="goback();" class="layui-btn layui-btn-normal" />
                    <input type="button" id="roleadd" value="删除" onclick="signModify()" class="layui-btn layui-btn-normal"/>
                </div>
            </div>
        </div>
    </form>


</div>
</body>
<script type="text/javascript" src="js/summaryArchiving_delete.js?v=1.0"></script>
</html>
