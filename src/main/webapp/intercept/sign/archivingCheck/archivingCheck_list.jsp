<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/open/commonjs/tldLayui.jsp"%>
    <script type="text/javascript" src="js/archivingCheck_list.js?v=1.1.8"></script>
    <title>建档立卡居民健康核查</title>
</head>
<body bgcolor="white">
<%--查询条件--%>
<div id="form_qvo">
    <div class="mwrap" id="queryForm">
        <div class="layui-form-item">
            <input id="orgName" pofield="orgName" type="hidden" >
            <input id="jdSourceType" pofield="jdSourceType" type="hidden">
            <label class="layui-form-label">市级</label>
            <div class="layui-input-inline" id="cityid">
                <select id="patientCity" name="patientCity" pofield="patientCity"  class="layui-input" onchange="changecounty()" >
                    <option value="">--请选择市--</option>
                </select>
            </div>
            <label class="layui-form-label">区县级</label>
            <div class="layui-input-inline" id="county">
                <select id="patientArea" pofield="patientArea"  class="layui-input" onchange="changestreet()">
                    <option value="">--请选择区--</option>
                </select>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">街道乡镇</label>
            <div class="layui-input-inline" id="street">
                <select id="patientStreet" pofield="patientStreet" class="layui-input" onchange="changeCommittee()">
                    <option value="">--请选择街道--</option>
                </select>
            </div>
            <label class="layui-form-label">居委会</label>
            <div class="layui-input-inline" id="committee">
                <select id="patientNeighborhoodCommittee" pofield="patientNeighborhoodCommittee" class="layui-input">
                    <option value="">--请选择居委会--</option>
                </select>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">是否建档：</label>
            <div class="layui-input-inline" id = "jdState">
                <input type="radio" name="jdState" value="0" title="否" style="width:20px;height:20px;" pofield="jdState">&nbsp;&nbsp;否&nbsp;&nbsp;
                <input type="radio" name="jdState" value="1" title="是" style="width:20px;height:20px;" pofield="jdState">&nbsp;&nbsp;是&nbsp;&nbsp;
                <input type="radio" name="jdState" value="" title="无" style="width:20px;height:20px;" pofield="jdState">&nbsp;&nbsp;无&nbsp;&nbsp;
            </div>
            <label class="layui-form-label">是否签约：</label>
            <div class="layui-input-inline">
                <input type="radio" name="signState" onclick="showToday(0)" value="0" title="否" style="width:20px;height:20px;" pofield="signState">&nbsp;&nbsp;否&nbsp;&nbsp;
                <input type="radio" name="signState" onclick="showToday(1)" value="1" title="是" style="width:20px;height:20px;" pofield="signState">&nbsp;&nbsp;是&nbsp;&nbsp;
                <input type="radio" name="signState" onclick="showToday(0)" value="" title="无" style="width:20px;height:20px;" pofield="signState">&nbsp;&nbsp;无&nbsp;&nbsp;
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">是否脱贫：</label>
            <div class="layui-input-inline">
                <input type="radio" name="povertyState" value="0" title="否" style="width:20px;height:20px;" pofield="povertyState">&nbsp;&nbsp;否&nbsp;&nbsp;
                <input type="radio" name="povertyState" value="1" title="是" style="width:20px;height:20px;" pofield="povertyState">&nbsp;&nbsp;是&nbsp;&nbsp;
                <input type="radio" name="povertyState" value="" title="无" style="width:20px;height:20px;" pofield="povertyState">&nbsp;&nbsp;无&nbsp;&nbsp;
            </div>
            <label class="layui-form-label">未确认：</label>
            <div class="layui-input-inline">
                <input type="radio" name="notConfirm" value="1" title="是" style="width:20px;height:20px;" pofield="notConfirm">&nbsp;&nbsp;是&nbsp;&nbsp;
                <input type="radio" name="notConfirm" value="" title="无" style="width:20px;height:20px;" pofield="notConfirm">&nbsp;&nbsp;无&nbsp;&nbsp;
            </div>
        </div>
        <div class="layui-form-item">
            <div id="cancelS" >
                <label class="layui-form-label">查询注销数据：</label>
                <div class="layui-input-inline">
                    <input type="checkbox" name="cancelState" value="1" style="width:20px;height:20px;" pofield="cancelState">
                </div>
            </div>

            <div id="todayStatee" hidden="hidden">
                <label class="layui-form-label">查询当天签约：</label>
                <div class="layui-input-inline">
                    <input type="checkbox"  name="toDayState" value="1" style="width:20px;height:20px;" pofield="toDayState">
                </div>
            </div>
        </div>
        <div  id="proInsur" hidden="hidden">
            <label class="layui-form-label">对象类型：</label>
            <div class="layui-input-inline">
                <input type="radio" name="provincialInsurance" value="1" title="建档立卡贫困人口" style="width:20px;height:20px;" pofield="provincialInsurance">&nbsp;&nbsp;建档立卡贫困人口&nbsp;&nbsp;
                <input type="radio" name="provincialInsurance" value="2" title="省定扶贫标准下的低保户对象" style="width:20px;height:20px;" pofield="provincialInsurance">&nbsp;&nbsp;省定扶贫标准下的低保户对象&nbsp;&nbsp;
                <input type="radio" name="provincialInsurance" value="" title="无" style="width:20px;height:20px;" pofield="provincialInsurance">&nbsp;&nbsp;无&nbsp;&nbsp;
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">姓名：</label>
            <div class="layui-input-inline">
                <input type="text" name="patientName" id="patientName" class="layui-input" pofield="patientName">
            </div>
            <label class="layui-form-label">身份证：</label>
            <div class="layui-input-inline">
                <input type="text" name="patientIdno" id="patientIdno" class="layui-input" pofield="patientIdno">
            </div>
            <label class="layui-form-label" style="width: 170px">是否已生成健康状况核查表：</label>
            <div class="layui-input-inline">
                <input type="checkbox"  name="isHaveState" value="1" style="width:20px;height:20px;" pofield="isHaveState">
            </div>
            <label class="layui-form-label">核查日期：</label>
            <div class="layui-input-inline">
                <input type="text" pofield="checkStartDate" id="checkStartDate" autocomplete="off" class="layui-input" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:45%;display:inline;"/>
                ~&nbsp;
                <input type="text" pofield="checkEndDate" id="checkEndDate" autocomplete="off" class="layui-input" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:45%;display:inline;"/>
            </div>
          <%--  <label class="layui-form-label" style="width: 170px">核查年份：</label>
            <div class="layui-input-inline">
                <input type="text" pofield="checkYear" id="checkYear" autocomplete="off" class="layui-input" onfocus="WdatePicker({dateFmt:'yyyy'})" style="width:45%;display:inline;"/>
            </div>--%>
        </div>
    </div>
    <blockquote class="layui-elem-quote" style="text-align: center" id="blockId">
        <input type="hidden" pofield="signHospId" id="signHospId"/>
        <button  onclick="findList()" class="layui-btn layui-btn-small">
            <i class=layui-icon>&#xe615;</i>查询
        </button>
        <button onclick="signsxcz()" class="layui-btn layui-btn-primary">
            <i class=layui-icon>&#xe60e;</i>重置
        </button>
        <button class='layui-btn'  onclick=exportCurrent(1)>  <i class=layui-icon>&#xe62d;</i>导出当前页</button>
        <button class='layui-btn'  onclick=exportCurrent(2)>  <i class=layui-icon>&#xe62d;</i>导出全部</button>
    </blockquote>
</div>
<%--签约列表--%>
<table id="archivingTable" lay-filter="archiving" style="height: 70%">

</table>
<script type="text/html" id="barRole">
    <a class="layui-btn layui-btn-danger layui-btn-mini phis sysRoleAction_delete" lay-event="modify"><i class=layui-icon>&#xe642;</i>修改</a>
    <a class="layui-btn layui-btn-mini " lay-event="findHealthRecords" ><i class=layui-icon>&#xe615;</i>健康档案</a>
    <a class="layui-btn layui-btn-normal layui-btn-mini phis sysRoleAction_delete" lay-event="revoke"><i class=layui-icon>&#xe642;</i>注销</a>
</script>
<script type="text/html" id="barRoleO">
    {{# if(d.addState == "0"){ }}
    <a class="layui-btn layui-btn-danger layui-btn-mini phis sysRoleAction_delete" lay-event="add"><i class=layui-icon>&#xe642;</i>新增</a>
    {{# } }}
    {{# if(d.addState == "1"){ }}
    <a class="layui-btn layui-btn-small layui-btn-mini phis sysRoleAction_delete" lay-event="look"><i class=layui-icon>&#xe615;</i>查看</a>
    <a class="layui-btn layui-btn-normal layui-btn-mini phis sysRoleAction_delete" lay-event="modify"><i class=layui-icon>&#xe642;</i>修改</a>
    {{# } }}
</script>
<script type="text/html" id="barRole1">
    {{# if(d.addState == "1"){ }}
    <a class="layui-btn layui-btn-small layui-btn-mini phis sysRoleAction_delete" lay-event="look"><i class=layui-icon>&#xe615;</i>查看</a>
    {{# } }}
</script>
</body>
</html>
