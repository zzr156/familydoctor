<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/open/commonjs/tldLayui.jsp"%>
    <script type="text/javascript" src="js/summaryArchiving_list.js?v=1.0"></script>
    <title>建档立卡居民信息</title>
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
                <input type="radio" name="signState" value="0" title="否" style="width:20px;height:20px;" pofield="signState">&nbsp;&nbsp;否&nbsp;&nbsp;
                <input type="radio" name="signState" value="1" title="是" style="width:20px;height:20px;" pofield="signState">&nbsp;&nbsp;是&nbsp;&nbsp;
                <input type="radio" name="signState" value="" title="无" style="width:20px;height:20px;" pofield="signState">&nbsp;&nbsp;无&nbsp;&nbsp;
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">是否脱贫：</label>
            <div class="layui-input-inline">
                <input type="radio" name="povertyState" value="0" title="否" style="width:20px;height:20px;" pofield="povertyState">&nbsp;&nbsp;否&nbsp;&nbsp;
                <input type="radio" name="povertyState" value="1" title="是" style="width:20px;height:20px;" pofield="povertyState">&nbsp;&nbsp;是&nbsp;&nbsp;
                <input type="radio" name="povertyState" value="" title="无" style="width:20px;height:20px;" pofield="povertyState">&nbsp;&nbsp;无&nbsp;&nbsp;
            </div>
            <label class="layui-form-label">是否去重：</label>
            <div class="layui-input-inline">
                <input type="radio" name="removalState" value="" title="否" style="width:20px;height:20px;" pofield="removalState">&nbsp;&nbsp;否&nbsp;&nbsp;
                <input type="radio" onclick="hideVillageAndIdno()" name="removalState" value="1" title="是" style="width:20px;height:20px;" pofield="removalState">&nbsp;&nbsp;是&nbsp;&nbsp;
            </div>
        </div>
        <div class="layui-form-item">

            <label class="layui-form-label">查询居委会异常：</label>
            <div class="layui-input-inline">
                <input type="checkbox" onclick="hideIdnoAndRemoval()" name="villageState" value="1" style="width:20px;height:20px;" pofield="villageState">
            </div>
            <label class="layui-form-label" id="idnoRemark">身份证异常：</label>
            <div class="layui-input-inline">
                <input type="checkbox" onclick="hideVillageAndRemoval()" name="idnoState" value="1" style="width:20px;height:20px;" pofield="idnoState">
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
        <div class="layui-form-item" >
            <label class="layui-form-label">姓名：</label>
            <div class="layui-input-inline">
                <input type="text" name="patientName" id="patientName" class="layui-input" pofield="patientName">
            </div>
            <label class="layui-form-label">身份证：</label>
            <div class="layui-input-inline">
                <input type="text" name="patientIdno" id="patientIdno" class="layui-input" pofield="patientIdno">
            </div>
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
    <a class="layui-btn layui-btn-normal layui-btn-mini phis sysRoleAction_delete" lay-event="delete"><i class=layui-icon>&#xe642;</i>删除</a>
</script>
<%--<script type="text/html" id="barRoleO">
    <a class="layui-btn layui-btn-danger layui-btn-mini phis sysRoleAction_delete" lay-event="modify"><i class=layui-icon>&#xe642;</i>修改</a>
</script>--%>
</body>
</html>
