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
            <label class="layui-form-label">选择城市：</label>
            <div class="layui-input-inline" id="cityid">
                <select class="layui-input" id="areaCode">
                    <option value="0">--请选择--</option>
                    <option value="3501">福州市</option>
                    <option value="3503">莆田市</option>
                    <option value="3504">三明市</option>
                    <option value="3505">泉州市</option>
                    <option value="3506">漳州市</option>
                    <option value="3507">南平市</option>
                </select>
            </div>
            <input type="file" id="upExcel" name="upExcel" class="layui-btn layui-btn-normal"/>
            <button type="button" class='layui-btn layui-btn-small'  onclick=addfile(1)>  <i class=layui-icon>&#xe61f;</i>新增导入</button>
            <button type="button" class='layui-btn'  onclick=addfile(2)>  <i class=layui-icon>&#xe642;</i>修改导入</button>
            <button type="button" class='layui-btn layui-btn-danger'  onclick=addfile(3)>  <i class=layui-icon>&#xe640;</i>删除导入</button>
        </div>
        </blockquote>
    </form>
</div>

</body>
<script type="text/javascript">
    function addfile(num){
        //根据num的值判断是新增导入、修改导入、删除导入
        var areaCode = $("#areaCode").val();
        if(areaCode ==0){
            layer.msg("城市选择不能为空！");
        }else{
            var file=$("#upExcel").val();
//            var pic = $("#upExcel").val().split(".");/*此方法判断是否符合格式要排除文件名为2017.1.1.xls这种格式*/
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
                    url: 'archivingDealWith.action?act=importExcel&num='+num+'&areaCode='+areaCode, //用于文件上传的服务器端请求地址
                    secureuri: false, //是否需要安全协议，一般设置为false
                    fileElementId: 'upExcel', //文件上传域的ID
                    dataType: 'JSON', //返回值类型 一般设置为json
                    success: function (data)  //服务器成功响应处理函数
                    {
                        layer.close(index);// 遮罩关闭
                        var da=JSON.parse(data);
                        layer.open({
                            skin: 'layui-layer-molv',
                            closeBtn: 0,
                            title: false,
                            content:da.msg,
                            anim: 5 ,
                            btn: ['关闭'],
                            end: function () {// 关闭窗口事件
                                window.location.href='archivingDealWith.action?act=forAddOrEdit';
                            }
                        });
                    },
                    error: function (data)//服务器响应失败处理函数
                    {
                        layer.close(index);// 遮罩关闭
                        layer.msg(e);
                    }
                }
            )
        }
    }
</script>
</html>
