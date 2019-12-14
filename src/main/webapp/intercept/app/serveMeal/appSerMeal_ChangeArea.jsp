<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="<%=request.getContextPath() %>/open/commonjs/css/demo.css?v=1.1.0" media="all">
<link rel="stylesheet" href="<%=request.getContextPath() %>/open/commonjs/css/zTreeStyle/zTreeStyle.css?v=1.1.0" media="all">
<script type="text/javascript" src="<%=request.getContextPath() %>/open/commonjs/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/open/commonjs/js/jquery.ztree.excheck.js"></script>
<style>
    .main_div{
        width:100%;
        height:400px;
        margin:5px 5px 5px 5px;
    }
    .main_left{
        width: 100%;
        padding: 5px 5px 5px 5px;
        border: 1px solid #DDDDDD;
    }

    .float_inline{
        display: block;
        float: left;
    }

    .title{
        height: 20px;
        width: 100%;
        line-height: 20px;
        color: #009688;
        text-align: center;
        font-weight: bold;background: #F2F2F2;
    }
    .div_hight{
        height: auto;
    }
</style>

<body class="gray-bg">
<div class="modal fade" id="myModalArea" tabindex="-1" role="dialog" aria-labelledby="myModalLabelArea" aria-hidden="true">
    <div class="modal-dialog" style="width: 850px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabelArea">
                    选择区域
                </h4>
            </div>
            <div class="modal-body">
                <div class="main_div">
                    <div class="main_left float_inline div_hight">
                        <div class="title layui-elem-quote"><span>行政区划</span></div>
                        <ul id="areaTree" class="ztree">
                        </ul>
                    </div>
                </div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
                <button type="button" onclick="getCheckValue()" class="btn btn-primary">
                    确定
                </button>
            </div>
        </div>
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
        var resultName='';
        if(nodes.length==0){
            alert("请选择！！");
            return false;
        }
        for (var i = 0; i < nodes.length; i++) {
            if(i == 0){
                result += nodes[i].id;
                resultName += nodes[i].name;
            }else{
                result += ','+ nodes[i].id;
                resultName += ','+nodes[i].name;
            }
        }
//        alert(resultName);
//        alert(result);
        $("#sersmOpenArea").val(result);
        $("#sersmOpenName").val(resultName);
        $('#myModalArea').modal('hide');
    }

</script>

