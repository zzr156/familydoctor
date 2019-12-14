<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<body class="gray-bg">
<div class="modal fade" id="myModalHospDept" tabindex="-1" role="dialog" aria-labelledby="myModalLabelHospDept" aria-hidden="true">
    <div class="modal-dialog" style="width: 850px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabelHospDept">
                    选择服务人群
                </h4>
            </div>
            <div class="modal-body" id="form_qvo">
                <div  style="display:none">
                    <table id="text">

                    </table>
                </div>
                <table id="mytableHospDept" class="table table-striped  table-hover table-left">
                    <thead>
                    <tr>
                        <td>
                            <div class="form-group">
                                <label class="col-sm-4 control-label">服务人群：</label>
                                <div class="col-sm-8">
                                    <input type="text" class="form-control" pofield="fwrq"  id="fwrq"  >
                                </div>
                            </div>
                        </td>
                        <td>
                            <div class="form-group">
                                <label class="col-sm-4 control-label">服务内容：</label>
                                <div class="col-sm-8">
                                    <input type="text" class="form-control" pofield="fwnr"  id="fwnr"  >
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="form-group">
                                <label class="col-sm-4 control-label">显示条数：</label>
                                <div class="col-sm-8">
                                    <input type="text" class="form-control" pofield="pageSize"  id="pageSize"  value="15" >
                                </div>
                            </div>
                        </td>
                        <td>
                            <div class="input-group">
                                <span class="input-group-btn">
                                        <button type="button" class="btn btn-primary" onclick="findDept()">查询</button>
                                    </span>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td style="text-align: center;">服务人群</td>
                        <td style="text-align: center;">服务内容</td>
                        <td style="text-align: center;">费用(元)</td>
                    </tr>
                    <input type="hidden" pofield="itemCount" id="itemCount" value="">
                    <input type="hidden" pofield="pageCount" id="pageCount" value="">
                    <input type="hidden" pofield="pageStartNo" id="pageStartNo" value="1">
                    </thead>
                    <tbody id="tbHospDept">

                    </tbody>
                </table>
            </div>
            <div class="text-center" style="background: #fff; padding-top: 5px;">
                <button id="previous" type="button" class="btn btn-sm" onclick="Previous();findDept();">前一页</button>
                <span id="qvodesc">1/1每页显示:10条,共有1条</span>&nbsp;&nbsp;
                <button id="next" type="button" class="btn btn-sm" onclick="Next();findDept();">下一页</button>
                <input style="width: 40px" class="span2" id="gotext" type="text">
                <button class="btn btn-sm" onclick="qvoGo();findDept();" type="button">Go!</button>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
                <button type="button" onclick="chageHospDept()" class="btn btn-primary" data-dismiss="modal">
                    提交
                </button>
            </div>
        </div>
    </div>
</div>
<jsp:include page="/open/mould/app/serveMeal/appServeMeal_change_tmp.jsp" flush="false" />
<script type="text/javascript">
    function uiFromTmpDept(id, data,idx) {
        var t = $($("#" + id + "").html());
        dataToUiTmp_idlsttr_rs(t, data,idx);
        $("#tbHospDept").append(t);
        t.fadeIn();
    }
    function dataToUiTmp_idlsttr_rs(ui, data,idx) {
        ui.data("vo", data);
        ui.find("td[pofield=id]").text(data.id);
        var d=document.createElement("input");
        d.setAttribute("type","checkbox");
        d.setAttribute("name","changeHospDept");
        d.setAttribute("id","changeHospDept");
        d.setAttribute("onclick","addOrDel(this)");
        d.setAttribute("value",data.sergValue+";;;"+data.sergObjectValue+";;;"+data.sergObjectTitle+";;;"+data.sergObjectType+
            ";;;"+data.sergPkValue+";;;"+data.sergPkTitle+";;;"+data.sergPkType+";;;"+data.sergGroupFee);
        ui.find("td[pofield=sname]").append(d);
        ui.find("td[pofield=sname]").append(data.sergObjectTitle);
        ui.find("td[pofield=content]").append(data.strSergPkTitle)
        ui.find("td[pofield=state]").append(data.sergGroupFee);
    }

</script>

<script type="text/javascript">
    var qvo={};
    //医院
    function findDept(){
        qvo = uiToData("form_qvo",qvo);//界面值绑定到变量
        doAjaxPost('<%=request.getContextPath()%>/appSerMeal.action?act=findCmmGroup',{qvoJson:JSON.stringify(qvo)},callDataToDept);
    }
    function callDataToDept(data){
        dataToUi(data.qvo,"form_qvo");//数据绑定到界面
        qvodesc("qvodesc");
        qvoPN(data.qvo);
        $("#tbHospDept").html("");
        $.each(data.rows, function(k, v) {
            uiFromTmpDept("hosp_tlist",v,k);
        });
        ChangeDateToUser();
    }
    function chageHospDept(){
        var chkRadio = [];
        var groupValue = "";//组合值
        var groupId = "";//组合id
        var objectValue = "";//服务人群值
        var objectTitle = "";//服务人群名称
        var objectType = "";//服务人群类型
        var pkValue = "";//服务内容值
        var pkTitle = "";//服务内容
        var pkType = "";//服务内容类型
        var fwnrss = "";
        var content = "";
        var text = "";
//        $("input:checkbox[name='changeHospDept']:checked").each(function(){
        $("#text").find("input:checkbox[name='changeHospDept']").each(function(){
            chkRadio.push( $(this).attr("value"));
            groupId+=$(this).parent().parent().find("td[pofield=id]").text()+";";
            text += $(this).parent().text()+";;;";
            content+= $(this).parent().parent().find("td[pofield=content]").text()+";;;";
//            fwnrss +=text+": "+ content+"<br>";
        });
        if (chkRadio == null) {
            alert("没有选中项");
            return false;
        } else {
            /*layer.confirm('是否合并相同服务项内容?', {
                btn: ['合并','不合并']
            }, function(){
                var strBook = "";
                var fee = 0;
                for(var i=0;i<chkRadio.length;i++){
//                    gr  obv obTi     obTy pkv                  pkTi                                                               pkTy
//                    6;;;6;;;糖尿病;;;0;;;1;2;3;4;5;12;13;;;居民健康档案,健康教育,健康指导,定期随访,健康咨询,健康体检,用药指导;;;0;0;0;0;0;0;0
                    var chkRadios = chkRadio[i].split(";;;");
                    fee += parseInt(chkRadios[7]);
                    if(i==0){
                        groupValue = chkRadios[0];
                        objectValue = chkRadios[1];
                        objectTitle = chkRadios[2];
                        objectType = chkRadios[3];
                        pkValue = chkRadios[4];
                        pkTitle = chkRadios[5];
                        pkType = chkRadios[6];
                        strBook = chkRadios[2]+":"+chkRadios[5]+"<br>";
                    }else{
                        groupValue += ";"+chkRadios[0];
                        objectValue += ";"+chkRadios[1];
                        objectTitle += ","+chkRadios[2];
                        objectType += ";"+chkRadios[3];
                        var pkValues = chkRadios[4].split(";");
                        var pkTitles = chkRadios[5].split(",");
                        var pkTypes = chkRadios[6].split(";");
                        strBook += chkRadios[2]+":"+chkRadios[5]+"<br>";
                        for(var j=0;j<pkValues.length;j++){
                            if(pkValue.indexOf(pkValues[j])==-1){
                                pkValue+=";"+pkValues[j];
                                pkTitle += ","+pkTitles[j];
                                pkType += ";"+pkTypes[j];
                            }
                        }
                    }
                }
                $("#sersmGroupValue").val(groupValue);
                $("#sersmObjectType").val(objectType);
                $("#sersmObjectTitle").val(objectTitle);
                $("#sersmObjectValue").val(objectValue);
                $("#sersmPkType").val(pkType);
                $("#sersmPkTitle").val(pkTitle);
                $("#sersmpkvalue").val(pkValue);
                var fwrq = "";
                var fwnr = "";
                var ovs = objectValue.split(";");
                var ovtys = objectType.split(";");
                var ovTis = objectTitle.split(",");
                for(var s=0;s<ovs.length;s++){
                    if(ovtys[s]=="1"){
                        fwrq += createLabel(ovTis[s],"0");
                    }else{
                        fwrq += createLabel(ovTis[s],"1");
                    }
                }
                var pkVs = pkValue.split(";");
                var pkTys = pkType.split(";");
                var pkTis = pkTitle.split(",");
                for(var s=0;s<pkVs.length;s++){
                    if(pkTys[s]=="1"){
                        fwnr += createLabel(pkTis[s],"1");
                    }else{
                        fwnr += createLabel(pkTis[s],"0");
                    }
                }
                $("#fwrq").html(fwrq);
                $("#fwnr").html(fwnr);
                layer.closeAll('dialog');
                $("#sersmBook").html(strBook);
                $("#sersmTotalFee").val(fee);
            },function(){
                var strBook = "";
                var fee = 0;
                for(var i=0;i<chkRadio.length;i++){
//                    gr  obv obTi     obTy pkv                  pkTi                                                               pkTy
//                    6;;;6;;;糖尿病;;;0;;;1;2;3;4;5;12;13;;;居民健康档案,健康教育,健康指导,定期随访,健康咨询,健康体检,用药指导;;;0;0;0;0;0;0;0
                    var chkRadios = chkRadio[i].split(";;;");
                    fee+=parseInt(chkRadios[7]);
                    if(i==0){
                        groupValue = chkRadios[0];
                        objectValue = chkRadios[1];
                        objectTitle = chkRadios[2];
                        objectType = chkRadios[3];
                        pkValue = chkRadios[4];
                        pkTitle = chkRadios[5];
                        pkType = chkRadios[6];
                        strBook = chkRadios[2]+":"+chkRadios[5]+"<br/>";
                    }else{
                        groupValue += ";"+chkRadios[0];
                        objectValue += ";"+chkRadios[1];
                        objectTitle += ","+chkRadios[2];
                        objectType += ";"+chkRadios[3];
                        pkValue +=";"+ chkRadios[4];
                        pkTitle +=","+chkRadios[5];
                        pkType +=";"+ chkRadios[6];
                        strBook += chkRadios[2]+":"+chkRadios[5]+"<br/>";
                    }
                }
                $("#sersmGroupValue").val(groupValue);
                $("#sersmObjectType").val(objectType);
                $("#sersmObjectTitle").val(objectTitle);
                $("#sersmObjectValue").val(objectValue);
                $("#sersmPkType").val(pkType);
                $("#sersmPkTitle").val(pkTitle);
                $("#sersmPkValue").val(pkValue);
                var fwrq = "";
                var fwnr = "";
                var ovs = objectValue.split(";");
                var ovtys = objectType.split(";");
                var ovTis = objectTitle.split(",");
                for(var s=0;s<ovs.length;s++){
                    if(ovtys[s]=="1"){
                        fwrq += createLabel(ovTis[s],"0");
                    }else{
                        fwrq += createLabel(ovTis[s],"1");
                    }
                }
                var pkVs = pkValue.split(";");
                var pkTys = pkType.split(";");
                var pkTis = pkTitle.split(",");
                for(var s=0;s<pkVs.length;s++){
                    if(pkTys[s]=="1"){
                        fwnr += createLabel(pkTis[s],"1");
                    }else{
                        fwnr += createLabel(pkTis[s],"0");
                    }
                }
                $("#fwrq").html(fwrq);
                $("#fwnr").html(fwnr);
                $("#sersmBook").html(strBook);
                $("#sersmTotalFee").val(fee);
            });*/
            var strBook = "";
            var fee = 0;
            for(var i=0;i<chkRadio.length;i++){
//                    gr  obv obTi     obTy pkv                  pkTi                                                               pkTy
//                    6;;;6;;;糖尿病;;;0;;;1;2;3;4;5;12;13;;;居民健康档案,健康教育,健康指导,定期随访,健康咨询,健康体检,用药指导;;;0;0;0;0;0;0;0
                var chkRadios = chkRadio[i].split(";;;");
                fee+=parseInt(chkRadios[7]);
                if(i==0){
                    groupValue = chkRadios[0];
                    objectValue = chkRadios[1];
                    objectTitle = chkRadios[2];
                    objectType = chkRadios[3];
                    pkValue = chkRadios[4];
                    pkTitle = chkRadios[5];
                    pkType = chkRadios[6];
                    strBook = chkRadios[2]+":"+chkRadios[5]+"<br/>";
                }else{
                    groupValue += ";"+chkRadios[0];
                    objectValue += ";"+chkRadios[1];
                    objectTitle += ","+chkRadios[2];
                    objectType += ";"+chkRadios[3];
                    pkValue +=";"+ chkRadios[4];
                    pkTitle +=";"+chkRadios[5];
                    pkType +=";"+ chkRadios[6];
                    strBook += chkRadios[2]+":"+chkRadios[5]+"<br/>";
                }
            }
            $("#sersmGroupValue").val(groupValue);
            $("#sersmObjectType").val(objectType);
            $("#sersmObjectTitle").val(objectTitle);
            $("#sersmObjectValue").val(objectValue);
            $("#sersmPkType").val(pkType);
            $("#sersmPkTitle").val(pkTitle);
            $("#sersmPkValue").val(pkValue);
            var fwrq = "";
            var fwnr = "";
            var ovs = objectValue.split(";");
            var ovtys = objectType.split(";");
            var ovTis = objectTitle.split(",");
            var texts = text.split(";;;");
            var contents = content.split(";;;");
            for(var s=0;s<ovs.length;s++){
                if(ovtys[s]=="1"){
                    fwrq += createLabel(ovTis[s],"0")+";";
                    fwnrss += "<label style='color: black'><b>"+texts[s]+":</b>"+contents[s]+"</label><br>";
                }else{
                    fwrq += createLabel(ovTis[s],"1")+";";
                    fwnrss += "<label style='color: orange'><b>"+texts[s]+":</b>"+contents[s]+"</label><br>";
                }
            }
            var pkVs = pkValue.split(";");
            var pkTys = pkType.split(";");
            var pkTis = pkTitle.split(",");
            for(var s=0;s<pkVs.length;s++){
                if(pkTys[s]=="1"){
                    fwnr += createLabel(pkTis[s],"1");
                }else{
                    fwnr += createLabel(pkTis[s],"0");
                }
            }
            $("#fwrq").html(fwrq);
//            $("#fwnr").html(fwnr);
            $("#fwnr").html(fwnrss);
            $("#sersmBook").html(strBook);
            $("#sersmTotalFee").val(fee);
            $("#sersmGroupId").val(groupId);
            reload ();
        }

    }
    //创建标签
    function createLabel(title,type){
        var str = "";
        if(type=="0"){
            str="<label style='color: black'>"+title+"</label>&nbsp;&nbsp;";
        }else{
            str="<label style='color: orange'>"+title+"</label>&nbsp;&nbsp;"
        }
        return str;
    }
    function addOrDel(ui){
        var iddd = $(ui).parent().parent().find("td[pofield='id']").text();
        var flag = true;
        $("#text").find("tr[id='"+iddd+"']").each(function(){
            $("#" + iddd + "").remove();
            flag = false;
        });
        if(flag){
            var ttt = "<tr id='"+iddd+"'>"+$(ui).parent().parent().html()+"</tr>";
            $("#text").append(ttt);
        }
    }
</script>
