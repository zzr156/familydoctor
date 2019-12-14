<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/open/commonjs/tldLayui.jsp"%>
    <title>拒签信息</title>
</head>
<body bgcolor="white">
<!-- 新增 -->
<div style="padding:0 10px;">
<form class="layui-form-pane" id="form_vo">
    <blockquote class="layui-elem-quote" style="text-align: center;font-size: 20px" >拒签信息</blockquote>
    <input type="hidden" id="idx"   pofield="idx" />
    <input type="hidden" id="nowtime" value="<%=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date())%>" />
    <div class="layui-form-item">

        <div class="layui-form-item layui-form-text">
            <label class="layui-form-label">拒签理由</label>
            <div class="layui-input-block ">
                <textarea id="reason" name="reason" pofield="reason"  placeholder="请输入拒签理由" class="layui-textarea"></textarea>
            </div>
        </div>

    <div class="layui-form-item">
        <div class="layui-input-block" style="text-align: center;">
                <a class="layui-btn layui-btn-danger " id="roleadd" onclick="signrefuse_add()"><i class=layui-icon> &#xe654;</i>保存</a>
                <a class="layui-btn layui-btn-normals " id="" onclick="Goto()"><i class=layui-icon>&#xe65c;</i>返回</a>
        </div>
    </div>
    </div>
</form>


</div>
</body>
<script type="text/javascript" src="js/sign_refuse.js?v=1.0"></script>
</html>
