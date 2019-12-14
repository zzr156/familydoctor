<%@ page import="com.ylz.packcommon.common.util.ExtendDate" %>
<%@ page import="java.util.Calendar" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>

    <object id="iccard" height="0" classid="clsid:84C4FA72-83B4-4AE8-8178-250A9410E27A"
            codebase="iccard.cab#version=1,1,8,1"></object>
   <%-- <OBJECT Name="GT2ICROCX"  width="0" height="0"
            CLASSID="CLSID:5A381625-B14C-4ACD-BD3B-8D2BA0B5C7DB"
          CODEBASE="GT_IdrOcx.cab#version=2,1,0,0"
    ></OBJECT>--%>
    <%@ include file="/open/commonjs/tldLayui.jsp"%>
    <title>签约读卡</title>
</head>
<body bgcolor="white">
<%--<%=ExtendDate.getYMD_h_m_s(Calendar.getInstance())%>--%>

<div style="text-align: center; width: 100%;height: 100%">
    <input type="HIDDEN" name="Name">
    <input type="HIDDEN" name="namel">
    <input type="HIDDEN" name="sexl">
    <input type="HIDDEN" name="sex">
    <input type="HIDDEN" name="nation">
    <input type="HIDDEN" name="nationl">
    <input type="HIDDEN" name="national">
    <input type="HIDDEN" name="born">
    <input type="HIDDEN" name="bornl">
    <input type="HIDDEN" name="address">
    <input type="HIDDEN" name="cardno">
    <input type="HIDDEN" name="police">
    <input type="HIDDEN" name="Activityfrom">
    <input type="HIDDEN" name="Activityto">
    <input type="HIDDEN" name="Base64Bmp">
    <input type="HIDDEN" name="Base64Jpg">
    <span style="align-content: center; font-size: 26px" id="codeid"></span>
    <br>
    <img src="<%=request.getContextPath() %>/open/images/p1_s.jpg"  width="450">
    <br>
    <button class="layui-btn layui-btn-big layui-btn-normal"  data-method="offset" data-type="auto" id="carddk" onclick="sign_card()" >读社保卡</button>
    <%--<button class="layui-btn layui-btn-big layui-btn-normal"  data-method="offset" data-type="auto" id="" onclick="sign_idno_cadr()" >读身份证卡</button>--%>
</div>
</body>
<script type="text/javascript" src="js/sign_card.js?v=1.0"></script>
</html>
