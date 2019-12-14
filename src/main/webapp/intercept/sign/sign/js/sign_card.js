/*$(function(){
 doAjaxPost('teamAction.action?act=commList',{},initBack);
 })
 function initBack(data) {
 //findList();
 }*/
/**
 * 签约读卡
 */
var vo = {};
function sign_card() {
    debugger
    var cardObj = publicReadCard();

    var ptidno = cardObj.ic_ybid;//身份证
    var ptsccno = cardObj.ic_icno;//医保卡号
    var ptname = cardObj.ic_name;//姓名

    if (HospAreaCode.substring(0, 4) == "3501") {//福州
        sign_card_fz(ptidno, ptsccno, ptname);
    } else {
        if (ptidno != "" && ptsccno != "") {
            vo["ptidno"] = ptidno;
            vo["ptsccno"] = ptsccno;
            doAjaxPost('signAction.action?act=findRenew', {strJson: JSON.stringify(vo)}, function (data) {
                if(data.msg == null){
                    doAjaxPost('signAction.action?act=signfind', {strJson: JSON.stringify(vo)}, function (data) {
                        <!-- 是否签约 -->
                        if (data.vo != null) {

                            if (data.vo.signhospid == orgid) {
                                var msg = "该居民已经在" + data.vo.signDate + "日于" + data.vo.hospname + "签约。勿重复签约！";
                                layer.open({
                                    type: 1
                                    ,
                                    title: false //不显示标题栏
                                    ,
                                    closeBtn: false
                                    ,
                                    area: '600px;'
                                    ,
                                    shade: 0.8
                                    ,
                                    id: 'LAY_layuipro' //设定一个id，防止重复弹出
                                    ,
                                    resize: false
                                    ,
                                    btn: ['确定', '取消']
                                    ,
                                    btnAlign: 'c'
                                    ,
                                    moveType: 1 //拖拽模式，0或者1
                                    ,
                                    content: '<div style="padding: 50px; line-height: 22px; background-color: #f2f2f2; color: #000; font-weight: 700;"><input style="border:none;width: 500px" value="该居民已经在' + data.vo.signDate + '日于' + data.vo.hospname + '签约。勿重复签约！"></input></div>'
                                    ,
                                    success: function (layero) {
                                        var btn = layero.find('.layui-layer-btn');
                                        btn.find('.layui-layer-btn0').attr({
                                            href: "sign_look.jsp?id=" + data.vo.id
                                        });
                                    }
                                });
                            } else {
                                layer.msg("该居民已经在" + data.vo.signDate + "日于" + data.vo.hospname + "签约。勿重复签约！");
                            }
                        } else {
                            doAjaxPost('signAction.action?act=findArchivingCardPeople', {strJson: JSON.stringify(vo)}, function (data) {
                                if (data.map != null) {
                                    if (data.map.result == "1") {
                                        layer.open({
                                            type: 1
                                            ,
                                            title: false //不显示标题栏
                                            ,
                                            closeBtn: false
                                            ,
                                            area: '300px;'
                                            ,
                                            shade: 0.8
                                            ,
                                            id: 'LAY_layuipro' //设定一个id，防止重复弹出
                                            ,
                                            resize: false
                                            ,
                                            btn: ['确定', '取消']
                                            ,
                                            btnAlign: 'c'
                                            ,
                                            moveType: 1 //拖拽模式，0或者1
                                            ,
                                            content: '<div style="padding: 50px; line-height: 22px; background-color: #f2f2f2; color: #000; font-weight: 300;">该居民未签约，可进行签约！</div>'
                                            ,
                                            success: function (layero) {
                                                var btn = layero.find('.layui-layer-btn');
                                                btn.find('.layui-layer-btn0').attr({
                                                    href: "sign_add.jsp?ptidno=" + ptidno + "&economicType=2" + "&ptsccno=" + ptsccno + "&ptname=" + encodeURI(encodeURI(ptname))
                                                });
                                            }
                                        });
                                    } else {
                                        layer.open({
                                            type: 1
                                            ,
                                            title: false //不显示标题栏
                                            ,
                                            closeBtn: false
                                            ,
                                            area: '300px;'
                                            ,
                                            shade: 0.8
                                            ,
                                            id: 'LAY_layuipro' //设定一个id，防止重复弹出
                                            ,
                                            resize: false
                                            ,
                                            btn: ['确定', '取消']
                                            ,
                                            btnAlign: 'c'
                                            ,
                                            moveType: 1 //拖拽模式，0或者1
                                            ,
                                            content: '<div style="padding: 50px; line-height: 22px; background-color: #f2f2f2; color: #000; font-weight: 300;">该居民未签约，可进行签约！</div>'
                                            ,
                                            success: function (layero) {
                                                var btn = layero.find('.layui-layer-btn');
                                                btn.find('.layui-layer-btn0').attr({
                                                    href: "sign_add.jsp?ptidno=" + ptidno + "&ptsccno=" + ptsccno + "&isGeneralPeople=1" + "&ptname=" + encodeURI(encodeURI(ptname))
                                                });
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    });
                }else {
                    ayer.msg("该居民已经续签，勿重复签约！");
                }
            }), function () {
                //调用接口失败
                layer.msg('调用接口失败！', {
                    time: 1000 //1s后自动关闭
                });
            }
        } else {
            layer.msg("未检测到卡，请重新插卡！！");
            return;
        }
    }
}

$(function () {
    var code = HospAreaCode.substring(0, 4);
    if (code == "3501") {
        $("#codeid").html("福州市家庭医生签约平台");
    } else if (code == "3502") {
        $("#codeid").html("厦门市家庭医生签约平台");
    } else if (code == "3503") {
        $("#codeid").html("莆田市家庭医生签约平台");
    } else if (code == "3504") {
        $("#codeid").html("三明市家庭医生签约平台");
    } else if (code == "3505") {
        $("#codeid").html("泉州市家庭医生签约平台");
    } else if (code == "3506") {
        $("#codeid").html("漳州市家庭医生签约平台");
    } else if (code == "3507") {
        $("#codeid").html("南平市家庭医生签约平台");
    } else if (code == "3508") {
        $("#codeid").html("龙岩市家庭医生签约平台");
    } else if (code == "3509") {
        $("#codeid").html("宁德市家庭医生签约平台");
    }

    doAjaxPost('signAction.action?act=findSignRole', {signrole: orgid}, function (data) {

        if (data.code == "800") {
            if (data.result != "1000") {
                $("#carddk").attr('disabled', true);
                $("#carddk").css("background", "#808080");
                layer.msg("提示：该机构已被暂停签约，如有疑问，请联系卫计委。");
            }
        } else {
            layer.msg(data.msg);
        }
    });
});

//身份证读卡
function sign_idno_cadr() {

    var cardidnoObj = StartRead();
    var ptname = cardidnoObj.ic_name;//名称
    var ptGender = cardidnoObj.ic_xb00;//性别
    //var ptcsrq  = cardidnoObj.ic_csrq;//出生日期
    var ptidno = cardidnoObj.ic_cerd;//身份证
    //var address = cardidnoObj.ic_address;//户籍

    if (ptidno != "") {
        vo["ptidno"] = ptidno;
        //vo["ptsccno"]=ptsccno;
        doAjaxPost('signAction.action?act=signfind', {strJson: JSON.stringify(vo)}, function (data) {
            <!-- 是否签约 -->
            if (data.vo != null) {

                if (data.vo.signhospid != orgid) {
                    var msg = "该居民已经在" + data.vo.signDate + "日于" + data.vo.hospname + "签约。勿重复签约！";
                    layer.open({
                        type: 1
                        ,
                        title: false //不显示标题栏
                        ,
                        closeBtn: false
                        ,
                        area: '600px;'
                        ,
                        shade: 0.8
                        ,
                        id: 'LAY_layuipro' //设定一个id，防止重复弹出
                        ,
                        resize: false
                        ,
                        btn: ['确定', '取消']
                        ,
                        btnAlign: 'c'
                        ,
                        moveType: 1 //拖拽模式，0或者1
                        ,
                        content: '<div style="padding: 50px; line-height: 22px; background-color: #f2f2f2; color: #000; font-weight: 700;"><input style="border:none;width: 500px" value="该居民已经在' + data.vo.signDate + '日于' + data.vo.hospname + '签约。勿重复签约！"></input></div>'
                        ,
                        success: function (layero) {
                            var btn = layero.find('.layui-layer-btn');
                            btn.find('.layui-layer-btn0').attr({
                                href: "sign_look.jsp?id=" + data.vo.id
                            });
                        }
                    });
                } else {
                    layer.msg("该居民已经在" + data.vo.signDate + "日于" + data.vo.hospname + "签约。勿重复签约！");
                }
            } else {
                layer.open({
                    type: 1
                    ,
                    title: false //不显示标题栏
                    ,
                    closeBtn: false
                    ,
                    area: '300px;'
                    ,
                    shade: 0.8
                    ,
                    id: 'LAY_layuipro' //设定一个id，防止重复弹出
                    ,
                    resize: false
                    ,
                    btn: ['确定', '取消']
                    ,
                    btnAlign: 'c'
                    ,
                    moveType: 1 //拖拽模式，0或者1
                    ,
                    content: '<div style="padding: 50px; line-height: 22px; background-color: #f2f2f2; color: #000; font-weight: 300;">该居民未签约，可进行签约！</div>'
                    ,
                    success: function (layero) {
                        var btn = layero.find('.layui-layer-btn');
                        btn.find('.layui-layer-btn0').attr({
                            href: "sign_add.jsp?ptidno=" + ptidno + "&ptname=" + ptname + "&addtpye=2"
                        });
                    }
                });

            }
        }), function () {
            //调用接口失败
            layer.msg('调用接口失败！', {
                time: 1000 //1s后自动关闭
            });
        }
    } else {
        layer.msg("未检测到卡，请重新插卡！！");
        return;
    }

}

/**
 * 定制化需求，福州读卡签约只允许建档立卡人群签约
 * WangCheng
 * @param ptidno
 * @param ptsccno
 * @param ptname
 */
function sign_card_fz(ptidno,ptsccno,ptname){
    if (ptidno != "" && ptsccno != "") {
        vo["ptidno"] = ptidno;
        vo["ptsccno"] = ptsccno;
        doAjaxPost('signAction.action?act=signfind', {strJson: JSON.stringify(vo)}, function (data) {
            <!-- 是否签约 -->
            if (data.vo != null) {

                if (data.vo.signhospid == orgid) {
                    var msg = "该居民已经在" + data.vo.signDate + "日于" + data.vo.hospname + "签约。勿重复签约！";
                    layer.open({
                        type: 1
                        ,
                        title: false //不显示标题栏
                        ,
                        closeBtn: false
                        ,
                        area: '600px;'
                        ,
                        shade: 0.8
                        ,
                        id: 'LAY_layuipro' //设定一个id，防止重复弹出
                        ,
                        resize: false
                        ,
                        btn: ['确定', '取消']
                        ,
                        btnAlign: 'c'
                        ,
                        moveType: 1 //拖拽模式，0或者1
                        ,
                        content: '<div style="padding: 50px; line-height: 22px; background-color: #f2f2f2; color: #000; font-weight: 700;"><input style="border:none;width: 500px" value="该居民已经在' + data.vo.signDate + '日于' + data.vo.hospname + '签约。勿重复签约！"></input></div>'
                        ,
                        success: function (layero) {
                            var btn = layero.find('.layui-layer-btn');
                            btn.find('.layui-layer-btn0').attr({
                                href: "sign_look.jsp?id=" + data.vo.id
                            });
                        }
                    });
                } else {
                    layer.msg("该居民已经在" + data.vo.signDate + "日于" + data.vo.hospname + "签约。勿重复签约！");
                }
            } else {
                doAjaxPost('signAction.action?act=findArchivingCardPeople', {strJson: JSON.stringify(vo)}, function (data) {
                    if (data.map != null) {
                        if (data.map.result == "1") {
                            layer.open({
                                type: 1
                                ,
                                title: false //不显示标题栏
                                ,
                                closeBtn: false
                                ,
                                area: '300px;'
                                ,
                                shade: 0.8
                                ,
                                id: 'LAY_layuipro' //设定一个id，防止重复弹出
                                ,
                                resize: false
                                ,
                                btn: ['确定', '取消']
                                ,
                                btnAlign: 'c'
                                ,
                                moveType: 1 //拖拽模式，0或者1
                                ,
                                content: '<div style="padding: 50px; line-height: 22px; background-color: #f2f2f2; color: #000; font-weight: 300;">该居民未签约，可进行签约！</div>'
                                ,
                                success: function (layero) {
                                    var btn = layero.find('.layui-layer-btn');
                                    btn.find('.layui-layer-btn0').attr({
                                        href: "sign_add.jsp?ptidno=" + ptidno + "&economicType=2" + "&ptsccno=" + ptsccno + "&ptname=" + encodeURI(encodeURI(ptname))
                                    });
                                }
                            });
                        } else {
                            layer.msg("该居民不是建档立卡人群，当前不允许签约");
                            return;
                        }
                    }
                });
            }
        }), function () {
            //调用接口失败
            layer.msg('调用接口失败！', {
                time: 1000 //1s后自动关闭
            });
        }
    } else {
        layer.msg("未检测到卡，请重新插卡！！");
        return;
    }
}