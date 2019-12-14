<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/open/commonjs/tldLayui.jsp"%>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/open/commonjs/js/jautocomplete/jquery-ui.min.css">
    <script src="<%=request.getContextPath() %>/open/commonjs/js/jautocomplete/jquery-ui.min.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/open/commonjs/chosen_v1.8.2/chosen.css" />
    <script type="text/javascript" src="<%=request.getContextPath() %>/open/commonjs/chosen_v1.8.2/chosen.jquery.min.js"></script>
    <title>变更团队</title>
</head>
<body bgcolor="white">

<form class="layui-form-pane" id="form_vo">
        <div class="layui-form-item">
            <input type="hidden"  pofield="patientId" id="patientId" class="layui-input" />
            <input type="hidden"  pofield="drId" id="drId" class="layui-input" />
            <input type="hidden"  pofield="teamId" id="teamId" class="layui-input" />
            <input type="hidden"  pofield="hospId" id="hospId" class="layui-input" />
            <input type="hidden"  pofield="drName" id="drName" class="layui-input" />
                <label class="layui-form-label">团队选择</label>
                <div class="layui-input-block" >
                    <select id="changeTeam" name="changeTeam" pofield="changeTeam"  class="layui-input" onchange="changeteam()" validator='{"msg":"团队名称不能为空!"}'>
                        <option value="">--请选择团队--</option>
                    </select>
                </div>

                <label class="layui-form-label">家庭医生</label>
                <div class="layui-input-block" >
                    <select id="changeDr" name="changeDr" pofield="changeDr" class="layui-input" onchange="changedr()" validator='{"msg":"家庭医生不能为空!"}'>
                        <option value="">--请选择医生--</option>
                    </select>
                </div>

                <label class="layui-form-label">医生电话</label>
                <div class="layui-input-block">
                    <input type="text"  pofield="drTel" id="drTel" class="layui-input" />
                </div>

                <label class="layui-form-label">助理医生</label>
                <div class="layui-input-block" >
                    <select id="changeDrAssistantId" pofield="changeDrAssistantId"  class="layui-input" onchange="changedrass()">
                        <option value="">--请选择医生--</option>
                    </select>
                </div>

                <label class="layui-form-label">助理医生电话</label>
                <div class="layui-input-block">
                    <input type="text"  pofield="signDrAssistantTel" id="signDrAssistantTel" class="layui-input"/>
                </div>

            <label class="layui-form-label">签约操作人</label>
            <div class="layui-input-block" >
                <select id="batchOperatorName" pofield="batchOperatorName"  class="layui-input" >
                    <option value="">--请选择签约操作人--</option>
                </select>
            </div>

        </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <input type="hidden" name="teamCreateTime" pofield="teamCreateTime">
            <input type="button" id="roleadd" value="保存" onclick="changeAdd()" class="layui-btn" />
            <input type="button" id="back" value="返回" onclick="goto()" class="layui-btn" />
        </div>
    </div>
</form>

</body>
<script type="text/javascript" src="js/sign_change.js?v=1.0"></script>

</html>
