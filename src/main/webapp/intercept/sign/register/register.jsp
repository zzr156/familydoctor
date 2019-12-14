<%--
  Created by IntelliJ IDEA.
  User: CCJ
  Date: 2017/12/6
  Time: 17:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.ylz.packcommon.common.Constant" %>
<%@ page import="com.ylz.bizDo.cd.po.CdUser" %>
<%@ page import="com.ylz.packaccede.allDo.SysDao" %>
<%@ page import="com.ylz.bizDo.register.po.HealthCareParameter" %>
<head>
    <object id="iccard" height="0" classid="clsid:84C4FA72-83B4-4AE8-8178-250A9410E27A"
            codebase="iccard.cab#version=1,1,8,1"></object>
    <%@ include file="/open/commonjs/tldLayui.jsp"%>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/open/commonjs/chosen_v1.8.2/chosen.css" />
    <link rel="stylesheet" type="text/css" href="css/yibao.css">
    <script type="text/javascript" src="<%=request.getContextPath() %>/open/commonjs/chosen_v1.8.2/chosen.jquery.min.js"></script>
    <title>签约医保登记</title>
    <style>
        .layui-table-view .layui-table{
            width: auto;
        }
    </style>
</head>
<body bgcolor="white">
<%--查询条件--%>
<div id="form_qvo">
    <blockquote class="layui-elem-quote" style="text-align: left;font-size: 15px;"><a style="margin-left: 30px">签约人信息：</a></blockquote>
    <div class="mwrap" id="queryForm" style="margin-top: 10px">
        <div class="layui-form-item">
            <label class="layui-form-label">医保卡号</label>
            <div class="layui-input-inline">
                <input type="text"  pofield="ybCardNo" id="ybCardNo" class="layui-input" disabled="disabled" validator='{"msg":"IC卡号不能为空，请重新读卡！"}'/>
            </div>
            <label class="layui-form-label">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名</label>
            <div class="layui-input-inline">
                <input type="text"  pofield="patientName" id="patientName" class="layui-input" disabled="disabled"/>
            </div>
            <label class="layui-form-label">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别</label>
            <div class="layui-input-inline">
                <input type="text"  pofield="patientSex" id="patientSex" class="layui-input" disabled="disabled"/>
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">身&nbsp;&nbsp;份&nbsp;&nbsp;证</label>
            <div class="layui-input-inline">
                <input type="text"  pofield="patientIdno" id="patientIdno" class="layui-input"/>
            </div>
            <label class="layui-form-label">出生日期</label>
            <div class="layui-input-inline">
                <input type="text"  pofield="patientBirth"id="patientBirth" class="layui-input" disabled="disabled"/>
            </div>
            <label class="layui-form-label">医保类型</label>
            <div class="layui-input-inline">
                <input type="text" pofield="signlx" id="signlx" class="layui-input" disabled="disabled"/>
            </div>
            <!--签约单ID-->
            <input type="hidden" pofield="signFormId" id="signFormId" class="layui-input" style=""/>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">签约状态</label>
            <div class="layui-input-inline">
                <input type="text"  pofield="signState" id="signState" class="layui-input" disabled="disabled"/>
            </div>
            <label class="layui-form-label">签约开始日期</label>
            <div class="layui-input-inline">
                <input type="text" pofield="signDateStart" id="signDateStart" autocomplete="off" class="layui-input" disabled="disabled"/>
            </div>
            <label class="layui-form-label">签约结束日期</label>
            <div class="layui-input-inline">
                <input type="text" pofield="signDateEnd" id="signDateEnd" autocomplete="off" class="layui-input" disabled="disabled"/>
            </div>
        </div>
    </div>
    <blockquote class="layui-elem-quote" style="text-align: center" id="blockId">
        <input type="hidden" pofield="signHospId" id="signHospId"/>
        <button class="layui-btn layui-btn-small  layui-btn-warm" onclick=readcard()>
            <i class='layui-icon'>&#xe615;</i>读卡
        </button>
        <button class='layui-btn'  onclick=register()>  <i class='layui-icon'>&#xe62d;</i>签约</button>
        <button class='layui-btn'  onclick=changeRegisterInfo()>  <i class='layui-icon'>&#xe62d;</i>改签</button>
        <button class='layui-btn'  onclick=cancelRegister()>  <i class='layui-icon'>&#xe62d;</i>退签</button>
    </blockquote>
</div>
<%--签约列表--%>
<div>
    <div id="registerTabel" lay-filter="register"></div>
</div>

</body>
<script type="text/javascript" src="js/register.js?v=1.0"></script>
<script type="text/javascript" src="yibao/yibao.js?v=1.0"></script>
<script>
//    var jklj00 = "C:\\\\SFJK\\\\";
//    var show_url = "yibao/yibaowait.jsp?a=1";
</script>
</html>
