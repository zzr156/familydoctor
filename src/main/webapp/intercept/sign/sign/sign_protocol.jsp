<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/open/commonjs/tldLayui.jsp" %>
    <script type="text/javascript" src="<%=request.getContextPath() %>/open/commonjs/jq/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/open/commonjs/jq/jquery.jqprint-0.3.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/open/commonjs/jq/jQuery.print.js"></script>
    <title>协议</title>
</head>
<body>
    <div style="text-align: center;margin: 0 auto; padding: 20px 0;">
        <button class="layui-btn layui-btn-warm" onclick="print();">打印协议</button>
        <button class="layui-btn layui-btn-warm" style="display:none" id="printheader" onclick="printheader();">打印头部协议</button>
        <button class="layui-btn layui-btn-warm" style="display:none" id="printtailId" onclick="printtailId();">打印尾部协议</button>
        <button class="layui-btn layui-btn-warm" onclick="goto()">返回</button>
    </div>

    <div id="protocolId" style=" position: relative;padding: 0 20px;">
        <div id="protocoImage" style="position: absolute;bottom: 0px;left:240px;z-index: -1;">
            <img width="135" height="135" id="imageSrcId" src="">
        </div>
    </div>

    <script type="text/javascript" src="js/sign_protocol.js?v=1.1"></script>
</body>
</html>
