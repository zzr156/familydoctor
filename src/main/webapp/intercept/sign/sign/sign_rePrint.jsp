<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/open/commonjs/tldLayui.jsp"%>
    <script type="text/javascript" src="js/sign_rePrint.js?v=1.0"></script>
    <title>签约信息管理</title>
</head>
<body bgcolor="white">
<%--查询条件--%>
<div id="form_qvo">
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
            <label class="layui-form-label">签约日期</label>
            <div class="layui-input-inline">
                <input type="text" pofield="signDateStart" id="signDateStart" autocomplete="off" class="layui-input" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:45%;display:inline;"/>
                ~
                <input type="text" pofield="signDateEnd" id="signDateEnd" autocomplete="off" class="layui-input" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:45%;display:inline;"/>
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

        <!-- 增加管理增加人口经济性质、服务人群类型、居委会查询条件 by WangCheng -->
        <div class="layui-form-item">
            <label class="layui-form-label">人口经济性质</label>
            <div class="layui-input-block">
                <input type="checkbox" name="signsJjTypes" value="1" title="一般人口" style="width:20px;height:20px;" pofield="signsJjTypes">&nbsp;&nbsp;一般人口&nbsp;&nbsp;
                <input type="checkbox" name="signsJjTypes" value="2" title="建档立卡贫困人口" style="width:20px;height:20px;" pofield="signsJjTypes">&nbsp;&nbsp;建档立卡贫困人口&nbsp;&nbsp;
                <input type="checkbox" name="signsJjTypes" value="3" title="低保户"style="width:20px;height:20px;" pofield="signsJjTypes">&nbsp;&nbsp;低保户&nbsp;&nbsp;
                <input type="checkbox" name="signsJjTypes" value="4" title="特困户（五保户）" style="width:20px;height:20px;" pofield="signsJjTypes">&nbsp;&nbsp;特困户（五保户）&nbsp;&nbsp;
                <input type="checkbox" name="signsJjTypes" value="5" title="计生特殊家庭" style="width:20px;height:20px;"  pofield="signsJjTypes">&nbsp;&nbsp;计生特殊家庭&nbsp;&nbsp;
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">服务人群类型</label>
            <div class="layui-input-block">
                <input type="checkbox" name="persGroup" value="1"  title="普通服务" style="width:20px;height:20px;"  pofield="persGroup">&nbsp;&nbsp;普通服务&nbsp;&nbsp;
                <input type="checkbox" name="persGroup" value="4"  title="老年人服务"style="width:20px;height:20px;"   pofield="persGroup">&nbsp;&nbsp;老年人服务&nbsp;&nbsp;
                <input type="checkbox" name="persGroup" value="5"  title="高血压" style="width:20px;height:20px;"  pofield="persGroup">&nbsp;&nbsp;高血压&nbsp;&nbsp;
                <input type="checkbox" name="persGroup" value="6" title="糖尿病" style="width:20px;height:20px;" pofield="persGroup">&nbsp;&nbsp;糖尿病&nbsp;&nbsp;
                <input type="checkbox" name="persGroup" value="2" title="儿童（0-6岁）"style="width:20px;height:20px;" pofield="persGroup">&nbsp;&nbsp;儿童（0-6岁）
                <input type="checkbox" name="persGroup" value="3"  title="孕产妇"style="width:20px;height:20px;"  pofield="persGroup">&nbsp;&nbsp;孕产妇&nbsp;&nbsp;
                <input type="checkbox" name="persGroup" value="8"  title="结核病" style="width:20px;height:20px;" pofield="persGroup">&nbsp;&nbsp;结核病&nbsp;&nbsp;
                <input type="checkbox" name="persGroup" value="7"  title="严重精神障碍患者"style="width:20px;height:20px;" pofield="persGroup">&nbsp;&nbsp;严重精神障碍患者&nbsp;&nbsp;
                <input type="checkbox" name="persGroup" value="9"  title="残疾人"style="width:20px;height:20px;" pofield="persGroup">&nbsp;&nbsp;残疾人
            </div>
        </div>

        <div class="layui-form-item">

            <label class="layui-form-label">居委会地址</label>

            <div class="layui-input-inline" id="cityid">
                <select id="patientCity" name="patientCity" pofield="patientCity"  class="layui-input" onchange="changecounty()">
                    <option value="">--请选择市--</option>
                </select>
            </div>

            <div class="layui-input-inline" id="county">
                <select id="patientArea" pofield="patientArea"  class="layui-input" onchange="changestreet()">
                    <option value="">--请选择区--</option>
                </select>
            </div>

            <div class="layui-inline">
                <div class="layui-input-inline" id="street">
                    <select id="patientStreet" pofield="patientStreet" class="layui-input" onchange="changeCommittee()">
                        <option value="">--请选择街道--</option>
                    </select>
                </div>
            </div>

            <div class="layui-inline">
                <div class="layui-input-inline" id="committee">
                    <select id="patientNeighborhoodCommittee" pofield="patientNeighborhoodCommittee" class="layui-input">
                        <option value="">--请选择居委会--</option>
                    </select>
                </div>
            </div>

        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">来源</label>
            <div class="layui-input-inline">
                <select id="upHpis" pofield="upHpis" class="layui-input">
                    <option value="">--请选择--</option>
                    <option value="2">Web端</option>
                    <option value="1">App端</option>
                </select>
            </div>
            <label class="layui-form-label">协议状态</label>
            <div class="layui-input-inline">
                <select id="signPrintFlag" pofield="signPrintFlag" class="layui-input">
                    <option value="">--请选择--</option>
                    <option value="0">未打</option>
                    <option value="1">已打</option>
                    <option value="2">已再打</option>
                </select>
            </div>
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
        <button class='layui-btn'  onclick=rePrintAll()>  <i class=layui-icon>&#xe62d;</i>批量打印</button>
    </blockquote>
</div>
<%--签约列表--%>
<table id="signTabel" lay-filter="sign">
</table>

</body>
<script type="text/html" id="barRole">
    <a class="layui-btn layui-btn-normal layui-btn-mini phis sysRoleAction_delete" lay-event="print"><i class=layui-icon>&#xe615;</i>查看协议</a>
</script>
</html>
