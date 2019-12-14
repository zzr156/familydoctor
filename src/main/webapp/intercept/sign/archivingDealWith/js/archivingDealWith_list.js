function addfile(num){
    //根据num的值判断是新增导入、修改导入、删除导入
    var areaCode = $("#areaCode").val();
    if(areaCode ==0){
        layer.msg("城市选择不能为空！");
    }else{
        var pic = $("#upExcel").val().split(".");
        if(pic[1] == undefined || (pic[1] != 'xls')){
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