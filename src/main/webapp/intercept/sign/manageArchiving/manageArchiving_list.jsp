<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/open/commonjs/tldLayui.jsp"%>
    <script type="text/javascript" src="js/manageArchiving_list.js?v=1.0"></script>
    <title>建档立卡居民数据统计</title>
</head>
<body bgcolor="white">
<%--查询条件--%>
<div id="form_qvo">
    <div class="mwrap" id="queryForm">
        <div class="layui-form-item">
            <label class="layui-form-label">市级</label>
            <div class="layui-input-inline" id="cityid">
                <select id="patientCity" name="patientCity" pofield="patientCity"  class="layui-input" onchange="changecounty()" >
                    <option value="">--请选择市--</option>
                </select>
            </div>
            <label class="layui-form-label">区县级</label>
            <div class="layui-input-inline" id="county">
                <select id="patientArea" pofield="patientArea"  class="layui-input" onchange="changeStreet()">
                    <option value="">--请选择区--</option>
                </select>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">街道乡镇</label>
            <div class="layui-input-inline" id="street">
                <select id="patientStreet" pofield="patientStreet" class="layui-input" onchange="changeHospId()">
                    <option value="">--请选择街道--</option>
                </select>
            </div>
            <%--<label class="layui-form-label">居委会</label>--%>
            <%--<div class="layui-input-inline" id="committee">--%>
                <%--<select id="patientNeighborhoodCommittee" pofield="patientNeighborhoodCommittee" class="layui-input">--%>
                    <%--<option value="">--请选择居委会--</option>--%>
                <%--</select>--%>
            <%--</div>--%>
            <label class="layui-form-label">机构</label>
            <div class="layui-input-inline" id="shop">
                <select id="hospId" pofield="hospId" class="layui-input">
                    <option value="">--请选择机构--</option>
                </select>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">是否查询居委会机构：</label>
            <div class="layui-input-inline" id = "isFind">
                <input type="radio" onclick="showHosp(0)" name="isFindState" value="0" title="否" style="width:20px;height:20px;" pofield="isFindState">&nbsp;&nbsp;否&nbsp;&nbsp;
                <input type="radio" onclick="showHosp(1)" name="isFindState" value="1" title="是" style="width:20px;height:20px;" pofield="isFindState">&nbsp;&nbsp;是&nbsp;&nbsp;
            </div>
        </div>
    </div>
    <blockquote class="layui-elem-quote" style="text-align: center" id="blockId">
        <input type="hidden" pofield="signHospId" id="signHospId"/>
        <input type="hidden" pofield="hospAreaCode" id="hospAreaCode"/>

        <button onclick="findList()" class="layui-btn layui-btn-small">
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
<table id="manageArchivingTable" lay-filter="manageArchiving">

</table>
</body>
</html>
