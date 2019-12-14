<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>解约</title>
    <%@ include file="/open/commonjs/tldLayui.jsp" %>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/open/commonjs/js/jautocomplete/jquery-ui.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/open/commonjs/chosen_v1.8.2/chosen.css"/>
    <script src="<%=request.getContextPath() %>/open/commonjs/js/jautocomplete/jquery-ui.min.js"></script>
    <script src="<%=request.getContextPath() %>/open/commonjs/chosen_v1.8.2/chosen.jquery.min.js"></script>
</head>

<body bgcolor="white">
    <div style="padding: 15px 15px;">
        <form class="layui-form-pane" id="form_vo">
            <input type="hidden" pofield="patientId" id="patientId" class="layui-input"/>
            <input type="hidden" pofield="drId" id="drId" class="layui-input"/>
            <input type="hidden" pofield="teamId" id="teamId" class="layui-input"/>
            <input type="hidden" pofield="hospId" id="hospId" class="layui-input"/>
            <input type="hidden" pofield="drName" id="drName" class="layui-input"/>

            <div class="layui-form-item">
                <label class="layui-form-label">
                    <b>* </b><span id="cslx">解约类型</span>
                </label>
                <div class="layui-input-block" id="signDelType" style="padding-left: 10px;padding-top: 4px;">
                    <input onclick="onTypevalue(1);" type="radio" pofield="signDelType"
                           name="signDelType" value="1" title="死亡">&nbsp;&nbsp;死亡&nbsp;&nbsp;
                    <input onclick="onTypevalue(2);" type="radio" pofield="signDelType" id="signDelTypeTwo"
                           name="signDelType" value="2" title="解约" checked>&nbsp;&nbsp;解约&nbsp;&nbsp;
                </div>
            </div>

            <!-- 死亡时间 -->
            <div class="layui-form-item" id="swsj" style="display:none"></div>

            <!-- 解约原因 -->
            <div class="layui-form-item" id="yyid">
                <label class="layui-form-label">
                    <b>* </b><span id="scyy">解约原因</span>
                </label>
                <div class="layui-input-block" style="left: 10px;line-height: 18px;">
                        <textarea type="text" id="signDelReason" pofield="signDelReason" placeholder="请输入解约原因"
                                  class="layui-textarea" validator='{"msg":"解约原因不能为空！"}' style="width: 500px;"></textarea>
                </div>
            </div>

            <!-- 操作按钮 -->
            <div style="text-align: center;">
                <input type="button" id="surrenderadd" value="解 约" onclick="surrenderAdd()" class="layui-btn"/>
                <input type="button" id="back" value="返 回" onclick="goto()" class="layui-btn"/>
            </div>
        </form>
    </div>
</body>
<script type="text/javascript" src="js/sign_surrender.js?v=1.2"></script>
</html>
