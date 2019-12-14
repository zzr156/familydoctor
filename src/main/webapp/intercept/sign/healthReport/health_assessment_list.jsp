<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/open/commonjs/tldLayui.jsp"%>
    <script type="text/javascript" src="js/health_assessment_list.js?v=1.1.3"></script>
    <title>健康评估报告居民列表</title>
</head>
<body bgcolor="white">
<%--查询条件--%>
<div id="form_qvo">
    <div class="mwrap" id="queryForm">
        <div class="layui-form-item">
            <label class="layui-form-label">姓名</label>
            <div class="layui-input-inline">
                <input type="text"  pofield="patientName" id="patientName" class="layui-input">
            </div>
            <label class="layui-form-label">身份证</label>
            <div class="layui-input-inline">
                <input type="text"  pofield="patientIdno" id="patientIdno" class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">签约日期</label>
            <div class="layui-input-inline">
                <input type="text" pofield="signDateStart" id="signDateStart" autocomplete="off" class="layui-input" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:45%;display:inline;"/>
                ~
                <input type="text" pofield="signDateEnd" id="signDateEnd" autocomplete="off" class="layui-input" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:45%;display:inline;"/>
            </div>
            <label class="layui-form-label">协议开始日期</label>
            <div class="layui-input-inline">
                <input type="text" pofield="signFromDateStart" id="signFromDateStart" autocomplete="off" class="layui-input" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:45%;display:inline;"/>
                ~&nbsp;
                <input type="text" pofield="signFromDateEnd" id="signFromDateEnd" autocomplete="off" class="layui-input" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:45%;display:inline;"/>
            </div>
            <label class="layui-form-label">协议结束日期</label>
            <div class="layui-input-inline">
                <input type="text" pofield="signToDateStart" id="signToDateStart" autocomplete="off" class="layui-input" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:45%;display:inline;"/>
                ~&nbsp;
                <input type="text" pofield="signToDateEnd" id="signToDateEnd" autocomplete="off" class="layui-input" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:45%;display:inline;"/>
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">服务人群类型</label>
            <div class="layui-input-block">
                <input type="checkbox" id="group_pt"  name="persGroup" value="1"  title="普通服务" style="width:20px;height:20px;"  pofield="persGroup">&nbsp;&nbsp;普通服务&nbsp;&nbsp;
                <input type="checkbox" id="group_lnr"  name="persGroup" value="4"  title="老年人服务"style="width:20px;height:20px;"   pofield="persGroup">&nbsp;&nbsp;老年人服务&nbsp;&nbsp;
                <input type="checkbox" id="group_gxy"  name="persGroup" value="5"  title="高血压" style="width:20px;height:20px;"  pofield="persGroup">&nbsp;&nbsp;高血压&nbsp;&nbsp;
                <input type="checkbox" id="group_tnb"  name="persGroup" value="6" title="糖尿病" style="width:20px;height:20px;" pofield="persGroup">&nbsp;&nbsp;糖尿病&nbsp;&nbsp;
                <input type="checkbox" id="group_et"  name="persGroup" value="2" title="儿童（0-6岁）"style="width:20px;height:20px;" pofield="persGroup">&nbsp;&nbsp;儿童（0-6岁）
                <input type="checkbox" id="group_ycf"  name="persGroup" value="3"  title="孕产妇"style="width:20px;height:20px;"  pofield="persGroup">&nbsp;&nbsp;孕产妇&nbsp;&nbsp;
                <input type="checkbox" id="group_jhb"  name="persGroup" value="8"  title="结核病" style="width:20px;height:20px;" pofield="persGroup">&nbsp;&nbsp;结核病&nbsp;&nbsp;
                <input type="checkbox" id="group_yzjszahz"  name="persGroup" value="7"  title="严重精神障碍患者"style="width:20px;height:20px;" pofield="persGroup">&nbsp;&nbsp;严重精神障碍患者&nbsp;&nbsp;
                <input type="checkbox" id="group_cjr"  name="persGroup" value="9"  title="残疾人"style="width:20px;height:20px;" pofield="persGroup">&nbsp;&nbsp;残疾人
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
                <select id="signDrAssistantId" name="signDrAssistantId" pofield="signDrAssistantId" class="layui-input">
                </select>
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">是否生成健康评估报告</label>
            <div class="layui-input-inline">
                <select id="signHealthReportState" pofield="signHealthReportState" class="layui-input">
                    <option value="0">否</option>
                    <option value="1">是</option>
                </select>
            </div>

            <%--<label class="layui-form-label">是否过期</label>--%>
            <%--<div class="layui-input-inline">--%>
                <%--<select id="signUrrenderState" pofield="signUrrenderState" class="layui-input">--%>
                    <%--<option value="0">否</option>--%>
                    <%--<option value="1">是</option>--%>
                <%--</select>--%>
            <%--</div>--%>
        </div>

    </div>

    <blockquote class="layui-elem-quote" style="text-align: center" id="blockId">
        <input type="hidden" pofield="signHospId" id="signHospId"/>
        <button onclick="findList()" class="layui-btn layui-btn-small">
            <i class=layui-icon>&#xe615;</i>查询
        </button>
        <button onclick="signsxcz()" class="layui-btn layui-btn-primary">
            <i class=layui-icon>&#xe60e;</i>重置
        </button>
    </blockquote>
</div>
<%--签约列表--%>
<table id="signTabel" lay-filter="sign">
</table>

</body>


<script type="text/html" id="barRole">
    <a class="layui-btn layui-btn-mini " lay-event="lookReport" ><i class=layui-icon>&#xe615;</i>查看报告</a>
    <a class="layui-btn layui-btn-normal layui-btn-mini phis sysRoleAction_delete" lay-event="exportReport"><i class=layui-icon>&#xe615;</i>导出报告</a>
    {{#  if(d.signHealthReportState == "0"){ }}
    <a class="layui-btn layui-btn-danger layui-btn-mini phis sysRoleAction_delete" lay-event="createReport"><i class=layui-icon>&#xe642;</i>生成报告</a>
    {{#  } }}
    {{#  if(d.signHealthReportState == "1"){ }}
    <a class="layui-btn layui-btn-danger layui-btn-mini phis sysRoleAction_delete" lay-event="modifyReport"><i class=layui-icon>&#xe642;</i>修改报告</a>
    {{#  } }}
</script>
</html>
