<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>解约预警弹出窗页面</title>
    <%@ include file="/open/commonjs/tldLayui.jsp" %>
    <%@ include file="/open/commonjs/roleson.jsp" %>
    <script type="text/javascript" src="<%=request.getContextPath()%>/open/commonjs/layui/layui.js?v=1.1.3"></script>
    <style>
        .control-label {
            float: left;
        }
        .form-group {
            overflow: hidden;
            white-space:nowrap;
            line-height: 36px;
        }
        .form-group .input-box{
            float: left;
            width: 70px;
            margin-top: 5px;
            margin-right: 8px;
            margin-left: 8px;
        }
    </style>
</head>


<body class="gray-bg">
<div class="wrapper wrapper-content" style="background-color: white;">
    <div class="row">
        <input type="hidden" id="signrenew" pofield="signrenew" value="1">
        <input type="hidden" id="redDay" pofield="redDay">
        <input type="hidden" id="yellowDay" pofield="yellowDay">
        <input type="hidden" id="greenDay" pofield="greenDay">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title" style="text-align: center;font-size: x-large; color: black;">解约预警</div>
            </div>
        </div>

        <div class="col-sm-12" style="height: 400px;width: 600px;
    margin: 0 auto;
    margin-top: 140px;
    float: none;">
            <form method="get" class="form-horizontal">
                <div class="form-group">
                    <label class="control-label"><img src="/family-doctor/open/images/img/yj_red.png"></label>
                    <label class="control-label" style="font-size: large;padding-right: 0px;">您有</label>
                    <div class="col-sm-4 input-box">
                        <input type="text" class="layui-input form-control" pofield="redWarningCount" id="redWarningCount"
                               readonly="readonly" style="background-color: white;">
                    </div>
                    <label class="control-label" style="font-size: large;">位居民即将到期解约</label>
                    <label class="control-label">(<label id="redDayBack"
                                                         style="font-size: large;padding-right: 0px;"></label><label
                            style="font-size: large;padding-right: 0px;">天)</label></label>
                    <button id="redRenew" style="float: right;background-color: red;" class="layui-btn layui-btn-small color-renew"
                            onclick="colorRenew(1)"><i class=layui-icon>&#xe642;</i>续签
                    </button>
                </div>

                <div class="form-group">
                    <label class="control-label"><img src="/family-doctor/open/images/img/yj_yellow.png"></label>
                    <label class="control-label" style="font-size: large;padding-right: 0px;">您有</label>
                    <div class="col-sm-4 input-box">
                        <input type="text" class="layui-input form-control" pofield="yellowWarningCount" id="yellowWarningCount"
                               readonly="readonly" style="background-color: white;" >
                    </div>
                    <label class="control-label" style="font-size: large;">位居民即将到期解约</label>
                    <label class="control-label">(<label id="yellowDayBack"
                                                         style="font-size: large;padding-right: 0px;"></label><label
                            style="font-size: large;padding-right: 0px;">天)</label></label>
                    <button id="yellowRenew" style="float: right;background-color: #ff8729;"
                            class="layui-btn layui-btn-small color-renew" onclick="colorRenew(2)"><i class=layui-icon>&#xe642;</i>续签
                    </button>
                </div>

                <div class="form-group">
                    <label class="control-label"><img src="/family-doctor/open/images/img/yj_green.png"></label>
                    <label class="control-label" style="font-size: large;padding-right: 0px;">您有</label>
                    <div class="col-sm-4 input-box">
                        <input type="text" class="layui-input form-control" pofield="greenWarningCount" id="greenWarningCount"
                               readonly="readonly" style="background-color: white;">
                    </div>
                    <label class="control-label" style="font-size: large;">位居民即将到期解约</label>
                    <label class="control-label">(<label id="greenDayBack"
                                                         style="font-size: large;padding-right: 0px;"></label><label
                            style="font-size: large;padding-right: 0px;">天)</label></label>
                    <button id="greenRenew" style="float: right;" class="layui-btn layui-btn-small color-renew" onclick="colorRenew(3)">
                        <i class=layui-icon>&#xe642;</i>续签
                    </button>
                </div>

            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
    /**
     * 核心js
     * WangCheng
     */
    var vo = {};

    $(function () {
        if(hospDissoluation == "0"){
            $("#redRenew").hide();
            $("#yellowRenew").hide();
            $("#greenRenew").hide();
        }
        countWarning();
    });

    /**
     * 计算各级别即将解约的签约数量
     */
    function countWarning() {
        vo["orgId"] = orgid;
        vo["drId"] = drid;
        doAjaxPost('signAction.action?act=dissolutionCount', {strJson: JSON.stringify(vo)}, function (data) {
            if (data.map != null) {
                if (data.map.redCount != undefined) {
                    $("#redDay").val(data.map.redDay);
                    $("#redDayBack").html(data.map.redDay);
                    $("#redWarningCount").val(data.map.redCount);
                }
                if (data.map.yellowCount != undefined) {
                    $("#yellowDay").val(data.map.yellowDay);
                    $("#yellowDayBack").html(data.map.yellowDay);
                    $("#yellowWarningCount").val(data.map.yellowCount);
                }
                if (data.map.greenCount != undefined) {
                    $("#greenDay").val(data.map.greenDay);
                    $("#greenDayBack").html(data.map.greenDay);
                    $("#greenWarningCount").val(data.map.greenCount);
                }
            }
        });
    }

    /**
     * 续签点击事件
     */
    function colorRenew(e) {
        debugger
        if (e == 1) {
            var url = "<%=request.getContextPath()%>/intercept/sign/sign/sign_renew.jsp?warningDay=" + $("#redDay").val() + "&renew=" + $("#signrenew").val();
        } else if (e == 2) {
            var url = "<%=request.getContextPath()%>/intercept/sign/sign/sign_renew.jsp?warningDay=" + $("#yellowDay").val() + "&renew=" + $("#signrenew").val();
        } else if (e == 3) {
            var url = "<%=request.getContextPath()%>/intercept/sign/sign/sign_renew.jsp?warningDay=" + $("#greenDay").val() + "&renew=" + $("#signrenew").val();
        }
        var params = {};
        params.title = "预警续签管理";
        params.title += '<i class="layui-icon layui-unselect layui-tab-close" data-id="' + Math.random() + '">&#x1006;</i>';
        params.content = "<iframe src=" + url + " data-id='" + Math.random() + "'></frame>";
        params.id = new Date().getTime();
        parent.parent.addTab($(this), params);
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);//关闭当前弹出层
    }


</script>
</body>
</html>