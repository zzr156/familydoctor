<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/open/commonjs/tldLayui.jsp"%>
    <%--<script type="text/javascript" src="js/archivingDealWith_list.js?v=1.1.8"></script>--%>
    <title>建档立卡居民健康核查</title>
</head>
<body bgcolor="white">
<%--查询条件--%>
<div id="form_qvo">
    <form class="form-horizontal">
        <blockquote class="layui-elem-quote" style="text-align: center" id="blockId">
            <div class="layui-form-item">
                <input type="file" id="upExcel" name="upExcel" class="layui-btn layui-btn-normal"/>
                <button type="button" class='layui-btn layui-btn-small'  onclick=addfile()>  <i class=layui-icon>&#xe61f;</i>新增导入</button>
            </div>
        </blockquote>
    </form>
</div>

</body>
<script type="text/javascript">
    function addfile(){
        var file=$("#upExcel").val();
        var ext = file.slice(file.lastIndexOf(".")+1).toLowerCase();
        if(ext == undefined || (ext != 'xls')){
            layer.open({
                skin: 'layui-layer-molv',
                closeBtn: 0,
                title: false,
                content:'上传EXCEL格式不正确（EXCEL格式应该为xls）!' ,
                anim: 5 ,
                btn: ['关闭']
            });
            return false;
        }
        var index = layer.load(1);// 遮罩打开
        $.ajaxFileUpload({
                url: 'signAction.action?act=importExcel', //用于文件上传的服务器端请求地址
                secureuri: false, //是否需要安全协议，一般设置为false
                fileElementId: 'upExcel', //文件上传域的ID
                dataType: 'JSON', //返回值类型 一般设置为json
                success: function (data, status)  //服务器成功响应处理函数
                {
                    layer.close(index);// 遮罩关闭
                    var da=JSON.parse(data);
                    layer.open({
                        skin: 'layui-layer-molv',
                        closeBtn: 0,
                        title: false,
                        content:da.msg ,
                        anim: 5 ,
                        btn: ['关闭']
                    });
//                    layer.msg(da.msg);
                },
                error: function (data, status, e)//服务器响应失败处理函数
                {
                    layer.msg(e);
                }
            }
        )
        return false;
    }
</script>
</html>
