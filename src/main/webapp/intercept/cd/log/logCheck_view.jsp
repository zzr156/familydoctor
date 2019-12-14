<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/open/commonjs/tldLayui.jsp"%>
    <title>日志详情</title>
    <style>
        .title{
            height:35px;
        }
        .inline{
            display:inline;
        }
        .right{
            float: right;
            margin-right: 40px;
        }
        .span_head{
            font-family: STHeiti;
            font-weight:bold;
        }
    </style>
</head>
<body>
<table class="layui-table" id="tview">
    <thead>
    <tr>
        <th width="25%">业务ID</th>
        <th width="25%">字段</th>
        <th width="25%">旧值</th>
        <th width="25%">新值</th>
    </tr>
    </thead>
    <tbody id="tlist">

    </tbody>



</table>
</body>
<jsp:include page="mould/logCheck_mould.jsp" flush="false" />
<script type="text/javascript" src="<%=request.getContextPath() %>/intercept/cd/log/js/logCheck_view.js?v=1.0"></script>

</html>
