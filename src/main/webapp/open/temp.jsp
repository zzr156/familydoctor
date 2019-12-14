<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>行政区划管理主页</title>
    <%@ include file="/open/commonjs/tldHtml.jsp"%>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/open/commonjs/css/demo.css?v=1.1.0" media="all">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/open/commonjs/css/zTreeStyle/zTreeStyle.css?v=1.1.0" media="all">
    <script type="text/javascript" src="<%=request.getContextPath() %>/open/commonjs/js/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/open/commonjs/js/jquery.ztree.excheck.js"></script>


    <style>
        .main_div{
            width:97%;
            height:820px;
            margin:30px 20px 100px 20px;
        }
        .main_left{
            width: 25%;
            padding: 5px 5px 5px 5px;
            border: 1px solid #DDDDDD;
        }

        .float_inline{
            display: block;
            float: left;
        }

        .title{
            height: 20px;
            width: 93%;
            line-height: 20px;
            color: #009688;
            text-align: center;
            font-weight: bold;background: #F2F2F2;
        }
        .div_hight{
            height: 800px;
        }
    </style>
</head>

<body  class="gray-bg">
<div class="main_div">
    <div class="main_left float_inline div_hight">
        <div class="title layui-elem-quote"><span>行政区划</span></div>
        <ul id="areaTree" class="ztree">
        </ul>
    </div>
    <div>
        <input type="button"
               class="btn btn_main" value="确定" onClick="getCheckValue();">

    </div>
</div>

<script type="text/javascript">
    $(function(){
        findTreeTable();
    })

    var setting = {
        check: {
            enable: true,
            chkboxType : { "Y" : "", "N" : "" }
        },
        async:{
            autoParam:["id"],
            enable:true,
            type:"post",
            url:"<%=request.getContextPath()%>/address.action?act=jsonServicePackage"
        },
        callback: {
            onClick: function (e, treeId, treeNode, clickFlag) {
                    zTree.checkNode(treeNode, !treeNode.checked, true);
            }
        }

    },zTree;

    function findTreeTable(){
        zTree = $.fn.zTree.init($("#areaTree"), setting);
    }


    function  getCheckValue(){
        var zTree = $.fn.zTree.getZTreeObj("areaTree");
        var nodes=zTree.getChangeCheckedNodes(true);

        var result='';
        alert(nodes.length);
        if(nodes.length==0){
            alert("请选择！！");
            return false;
        }
        for (var i = 0; i < nodes.length; i++) {
            if(i == 0){
                result += nodes[i].id;
            }else{
                result += ','+ nodes[i].id;
            }
        }
        alert(result);
    }

</script>
</body>
</html>
