<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/open/commonjs/tldLayui.jsp"%>
    <title>建档立卡居民撤销界面</title>
</head>
<body bgcolor="white">
<!-- 新增 -->
<div style="padding:0 10px;">
    <form class="layui-form-pane" id="form_vo">
        <input type="hidden" id="id"  pofield="id"/>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">注销类型</label>
                <div class="layui-input-block">
                    <input type="radio" onclick="onTypevalue(3);" validator='{"msg":"类型不能为空!"}' name="revokeState" value="3" title="失访" style="width:20px;height:20px;" pofield="revokeState">&nbsp;&nbsp;失访&nbsp;&nbsp;
                    <input type="radio" onclick="onTypevalue(4);" validator='{"msg":"类型不能为空!"}' name="revokeState" value="4" title="死亡" style="width:20px;height:20px;" pofield="revokeState">&nbsp;&nbsp;死亡&nbsp;&nbsp;
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label" id="revokeTitle"><b>*</b><span id="cxsj">失访时间</span></label>
                <div class="layui-input-inline" style="width: 200px;" >
                    <input type="text" id="revokeDate" pofield="revokeDate" class="layui-input" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" validator='{"msg":"时间不能为空!"}' >
                </div>
            </div>
            <div class="layui-form-item" id="yyid">
                <label class="layui-form-label"><b>*</b><span id="cxyy">失访原因</span></label>
                <div class="layui-input-block">
                    <textarea type="text" id="revokeReason" pofield="revokeReason" placeholder="请输入原因" class="layui-textarea" ></textarea>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-block" style="text-align: center;">
                    <input type="button" id="back" value="返回" onclick="goback();" class="layui-btn layui-btn-normal" />
                    <input type="button" id="roleadd" value="注销" onclick="signModify()" class="layui-btn layui-btn-normal"/>
                </div>
            </div>
        </div>
    </form>


</div>
</body>
<script type="text/javascript" src="js/archiving_delete.js?v=1.0"></script>
</html>
