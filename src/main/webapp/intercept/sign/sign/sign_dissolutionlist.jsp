<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/open/commonjs/tldLayui.jsp"%>
    <script type="text/javascript" src="js/sign_dissolutionlist.js?v=1.0"></script>
    <title>解约信息管理</title>
</head>
<body bgcolor="white">
<%--查询条件--%>
<div id="form_qvo">
    <input type="hidden" pofield="signHospId" id="signHospId"/>
    <input type="hidden" id="signrenew" pofield="signrenew" value="1">
    <div class="mwrap" id="queryForm">
        <div class="layui-form-item">
            <label class="layui-form-label">姓名</label>
            <div class="layui-input-inline">
                <input type="text"  pofield="patientName"id="patientName" class="layui-input">
            </div>
            <label class="layui-form-label">身份证</label>
            <div class="layui-input-inline">
                <input type="text"  pofield="patientIdno" id="patientIdno" class="layui-input">
            </div>
            <div id="familyId">
            <label class="layui-form-label">居民健康档案</label>
            <div class="layui-input-inline">
                <input type="text" pofield="patientjmda" id="patientjmda" class="layui-input">
            </div>
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">团队名称</label>
            <div class="layui-input-inline">
                <select id="teamId" pofield="teamId" class="layui-input" onchange="addDr()">
                </select>
            </div>
            <label class="layui-form-label">签约医生</label>
            <div class="layui-input-inline">
                <select id="drId" pofield="drId" class="layui-input">
                </select>
            </div>
            <label class="layui-form-label">助理姓名</label>
            <div class="layui-input-inline">
                <select id="signDrAssistantId" pofield="signDrAssistantId" class="layui-input">
                </select>
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">详细地址</label>
            <div class="layui-input-inline">
                <input type="text" pofield="patientAddress" id="patientAddress" class="layui-input">
            </div>
            <%-- <label class="layui-form-label">操作人</label>
             <div class="layui-input-inline">
                 <input type="text"  pofield="batchOperatorName"id="batchOperatorName" class="layui-input">
             </div>--%>
            <label class="layui-form-label">签约来源</label>
            <div class="layui-input-inline">
                <select id="upHpis" pofield="upHpis" class="layui-input">
                    <option value="">全部</option>
                    <option value="1">APP端</option>
                    <option value="2">web端</option>
                    <option value="3">微信端</option>
                </select>
            </div>

            <label class="layui-form-label">解约原因</label>
            <div class="layui-input-inline">
                <select id="reason" pofield="reason" class="layui-input">
                    <option value="">全部</option>
                    <option value="1">签约到期，自动解约</option>
                    <option value="2">死亡</option>
                    <option value="3">其他</option>
                </select>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">是否显示历史解约单</label>
            <div class="layui-input-inline">
                <select id="showHistory" pofield="showHistory" class="layui-input">
                    <option value="0">否</option>
                    <option value="1">是</option>
                </select>
            </div>
        </div>
    </div>
    <blockquote class="layui-elem-quote" style="text-align: center" id="blockId">
        <td>
            <input type="file" id="upExcel" name="upExcel" onchange=""/>
        </td>
        <button onclick="findList()" class="layui-btn layui-btn-small">
            <i class=layui-icon>&#xe615;</i>查询
        </button>
        <button onclick="signsxcz()" class="layui-btn layui-btn-primary">
            <i class=layui-icon>&#xe60e;</i>重置
        </button>
        <button class='layui-btn' id="ptExport" onclick=exportDissolutionDate() style="display:inline;">  <i class=layui-icon>&#xe62d;</i>导出全部</button>
        <button class='layui-btn' id="ptImport" onclick=importFile() style="display:inline;">  <i class=layui-icon>&#xe62d;</i>导入文件</button>
    </blockquote>
</div>
<%--签约列表--%>
<table id="signTabel" lay-filter="sign">
</table>

</body>


<script type="text/html" id="barRole">
    <a class="layui-btn layui-btn-mini " lay-event="look" ><i class=layui-icon>&#xe615;</i>查看</a>
    {{#  if(d.signGoToSignState == "0" && d.signUrrenderReason.indexOf("死亡") <= -1){ }}
    <a class="layui-btn layui-btn-danger layui-btn-mini phis sysRoleAction_delete" lay-event="renew" ><i class=layui-icon>&#xe615;</i>续签</a>
    {{#  } }}
</script>
<script type="text/html" id="barRolePt">
    <a class="layui-btn layui-btn-mini " lay-event="look" ><i class=layui-icon>&#xe615;</i>查看</a>
</script>
<script type="text/html" id="barRolePtxq">
    {{#  if(d.signGoToSignState == "0" && d.signUrrenderReason == "签约到期，自动解约" && d.signUrrenderReason.indexOf("死亡") <= -1){ }}
    <a class="layui-btn layui-btn-danger layui-btn-mini phis sysRoleAction_delete" lay-event="renew" ><i class=layui-icon>&#xe615;</i>续签</a>
    {{#  } }}
</script>
</html>
