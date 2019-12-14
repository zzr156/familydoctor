<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/open/commonjs/tldLayui.jsp" %>
    <script type="text/javascript" src="js/sign_add.js?v=1.5"></script>
    <title>签约界面</title>
</head>
<body bgcolor="white">
<!-- 新增 -->
<div style="padding:0 10px;">
    <form class="layui-form-pane" id="form_vo">
        <blockquote class="layui-elem-quote" style="text-align: center;font-size: 20px">签约信息</blockquote>
        <input type="hidden" id="hospId" pofield="hospId"/>
        <input type="hidden" id="batchOperatorId" pofield="batchOperatorId"/>
        <input type="hidden" id="batchOperatorName" pofield="batchOperatorName"/>
        <input type="hidden" id="yuser" pofield="yuser"/>
        <input type="hidden" id="ypaw" pofield="ypaw"/>
        <input type="hidden" id="xuser" pofield="xuser"/>
        <input type="hidden" id="xpaw" pofield="xpaw"/>

        <input type="hidden" id="nowtime" value="<%=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date())%>"/>
        <div class="layui-form-item">
            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 6px;">
                <legend style="font-size:20px;" id="jiafan">甲方</legend>
            </fieldset>

            <div class="layui-inline ">
                <label class="layui-form-label"><b>* </b>姓名</label>
                <div class="layui-input-block">
                    <input type="text" pofield="patientName" id="patientName" class="layui-input" validator='{"msg":"姓名不能为空!"}'/>
                </div>
            </div>

            <div class="layui-inline">
                <label class="layui-form-label"><b>* </b>性别</label>
                <div class="layui-input-block" id="patientGender">
                    <input type="radio" name="patientGender" value="1" title="男" style="width:25px;height:25px;" pofield="patientGender"> 男
                    <input type="radio" name="patientGender" value="2" title="女" style="width:25px;height:25px;" pofield="patientGender"> 女
                </div>
            </div>

            <div class="layui-inline">
                <label class="layui-form-label"><b>* </b>年龄</label>
                <div class="layui-input-block">
                    <input type="text" id="patientAge" pofield="patientAge" onblur="patientAgePk()" class="layui-input" validator='{"msg":"年龄不能为空!"}'/>
                </div>
            </div>

            <div class="layui-inline">
                <label class="layui-form-label"><b>* </b>身份证</label>
                <div class="layui-input-block">
                    <input type="text" onchange="idnoonchange($(this))" pofield="patientIdno" id="patientIdno"
                           class="layui-input" validator='{"msg":"身份证不能为空!","rule":"idCard"}'/>
                </div>
            </div>

            <div class="layui-inline">
                <label class="layui-form-label"><b id="redsbk"></b>社保卡</label>
                <div class="layui-input-block">
                    <input type="text" pofield="patientCard" id="patientCard" class="layui-input"/>
                </div>
            </div>

            <div class="layui-inline">
                <label class="layui-form-label">家庭档案</label>
                <div class="layui-input-block">
                    <input type="text" id="patientjtda" name="patientjtda" pofield="patientjtda" class="layui-input"/>
                </div>
            </div>

            <div class="layui-inline">
                <label class="layui-form-label"><b></b>居民档案</label>
                <div class="layui-input-block">
                    <input type="text" id="patientjmda" name="patientjmda" pofield="patientjmda" class="layui-input"/>
                </div>
            </div>

            <div class="layui-inline">
                <label class="layui-form-label"><b>* </b>联系方式</label>
                <div class="layui-input-block">
                    <input type="text" id="patientTel" name="patientTel" pofield="patientTel" class="layui-input"
                           validator='{"msg":"联系方式不能为空!","rule":"myphone"}'/>
                </div>
            </div>

            <div class="layui-inline">
                <label class="layui-form-label"><b>* </b>户籍所在地</label>
                <div class="layui-input-inline" id="cityid">
                    <select id="patientCity" name="patientCity" pofield="patientCity" class="layui-input"
                            onchange="changecounty()" validator='{"msg":"户籍所在地不能为空!"}'>
                        <option value="">--请选择市--</option>
                    </select>
                </div>
            </div>

            <div class="layui-inline">
                <div class="layui-input-inline" id="county">
                    <select id="patientArea" pofield="patientArea" class="layui-input" onchange="changestreet()"
                            validator='{"msg":"户籍所在地不能为空!"}'>
                        <option value="">--请选择区--</option>
                    </select>
                </div>
            </div>

            <div class="layui-inline">
                <div class="layui-input-inline" id="street">
                    <select id="patientStreet" pofield="patientStreet" class="layui-input" onchange="changeCommittee()"
                            validator='{"msg":"户籍所在地不能为空!"}'>
                        <option value="">--请选择街道--</option>
                    </select>
                </div>
            </div>

            <div class="layui-inline">
                <div class="layui-input-inline" id="committee">
                    <select id="patientNeighborhoodCommittee" pofield="patientNeighborhoodCommittee" class="layui-input"
                            validator='{"msg":"户籍所在地不能为空!"}'>
                        <option value="">--请选择居委会--</option>
                    </select>
                </div>
            </div>

            <div class="layui-form-item layui-form-text">
                <label class="layui-form-label"><b>* </b>现住地址</label>
                <div class="layui-input-block">
                    <textarea type="text" id="patientAddress" pofield="patientAddress" placeholder="请输入内容"
                              class="layui-textarea" validator='{"msg":"现住地址不能为空!"}'></textarea>
                </div>
            </div>

            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 6px;">
                <legend style="font-size:20px;" id="yifan">乙方</legend>
            </fieldset>

            <div class="layui-inline">
                <label class="layui-form-label"><b>* </b>签约机构</label>
                <div class="layui-input-block">
                    <input type="text" disabled="disabled" id="hospName" name="hospName" class="layui-input"
                           placeholder="当前机构" size="30">
                </div>
            </div>

            <div class="layui-inline">
                <label class="layui-form-label"><b>* </b>团队选择</label>
                <div class="layui-input-inline">
                    <select id="teamId" name="teamId" pofield="teamId" class="layui-input" onchange="changeteam()"
                            validator='{"msg":"团队名称不能为空!"}'>
                        <option value="">--请选择团队--</option>
                    </select>
                </div>
            </div>

            <div class="layui-inline">
                <label class="layui-form-label"><b>* </b>家庭医生</label>
                <div class="layui-input-inline">
                    <select id="drId" name="drId" pofield="drId" class="layui-input" onchange="changedr()"
                            validator='{"msg":"家庭医生不能为空!"}'>
                        <option value="">--请选择医生--</option>
                    </select>
                </div>
            </div>

            <div class="layui-inline">
                <label class="layui-form-label"><b>* </b>医生电话</label>
                <div class="layui-input-block">
                    <input type="text" pofield="drTel" id="drTel" class="layui-input"/>
                </div>
            </div>

            <div class="layui-inline">
                <label class="layui-form-label">家庭医生助理</label>
                <div class="layui-input-inline">
                    <select id="signDrAssistantId" pofield="signDrAssistantId" class="layui-input"
                            onchange="changedrass()">
                        <option value="">--请选择医生--</option>
                    </select>
                </div>
            </div>

            <div class="layui-inline">
                <label class="layui-form-label">家庭医生助理电话</label>
                <div class="layui-input-block">
                    <input type="text" pofield="signDrAssistantTel" id="signDrAssistantTel" class="layui-input"/>
                </div>
            </div>

            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 6px;">
                <legend style="font-size:20px;"><b>*</b>服务人群类型</legend>
            </fieldset>
            <div class="layui-form-item">
                <div class="layui-input-block" id="signPersGroup">
                    <span onclick="clickP(this)">
                        <input type="checkbox" id="ordinary" name="persGroup" value="1"
                            title="普通服务" style="width:20px;height:20px;"
                            validator='{"check":"请选择服务类型!"}' pofield="persGroup"></span>&nbsp;&nbsp;普通服务
                    &nbsp;&nbsp;
                    <span onclick="clickP(this)">
                        <input type="checkbox" id="oldman" name="persGroup" value="4"
                            title="老年人服务" style="width:20px;height:20px;"
                            validator='{"check":"请选择服务类型!"}' pofield="persGroup"></span>&nbsp;&nbsp;老年人服务
                    &nbsp;&nbsp;
                    <span onclick="clickP(this)">
                        <input type="checkbox" id="gxy" name="persGroup" value="5" title="高血压"
                            style="width:20px;height:20px;" validator='{"check":"请选择服务类型!"}'
                            pofield="persGroup"></span>&nbsp;&nbsp;高血压
                    &nbsp;&nbsp;
                    <span onclick="clickP(this)">
                        <input type="checkbox" id="tnb" name="persGroup" value="6" title="糖尿病"
                            style="width:20px;height:20px;" validator='{"check":"请选择服务类型!"}'
                            pofield="persGroup"> </span>&nbsp;&nbsp;糖尿病
                   &nbsp;&nbsp;
                    <span onclick="clickP(this)">
                        <input type="checkbox" id="young" name="persGroup" value="2"
                            title="儿童（0-6岁）" style="width:20px;height:20px;"
                            validator='{"check":"请选择服务类型!"}' pofield="persGroup"></span>&nbsp;&nbsp;儿童（0-6岁）

                    <span onclick="clickP(this)">
                        <input type="checkbox" id="yuncf" name="persGroup" value="3"
                            title="孕产妇" style="width:20px;height:20px;"
                            validator='{"check":"请选择服务类型!"}' pofield="persGroup"></span>&nbsp;&nbsp;孕产妇
                    &nbsp;&nbsp;
                    <span onclick="clickP(this)">
                        <input type="checkbox" id="jhb" name="persGroup" value="8" title="结核病"
                            style="width:20px;height:20px;" validator='{"check":"请选择服务类型!"}'
                            pofield="persGroup"></span>&nbsp;&nbsp;结核病
                    &nbsp;&nbsp;
                    <span onclick="clickP(this)">
                        <input type="checkbox" id="jsb" name="persGroup" value="7"
                            title="严重精神障碍患者" style="width:20px;height:20px;"
                            validator='{"check":"请选择服务类型!"}' pofield="persGroup"></span>&nbsp;&nbsp;严重精神障碍患者
                    &nbsp;&nbsp;
                    <span onclick="clickP(this)">
                        <input type="checkbox" id="cjr" name="persGroup" value="9" title="残疾人"
                            style="width:20px;height:20px;" validator='{"check":"请选择服务类型!"}'
                            pofield="persGroup"></span>&nbsp;&nbsp;残疾人
                    &nbsp;&nbsp;
                    <span onclick="clickP(this)">
                        <input type="checkbox" id="nxg" name="persGroup" value="10" title="脑血管病患者"
                               style="width:20px;height:20px;" validator='{"check":"请选择服务类型!"}'
                               pofield="persGroup"></span>&nbsp;&nbsp;脑血管病患者
                    &nbsp;&nbsp;
                    <span onclick="clickP(this)">
                        <input type="checkbox" id="gxb" name="persGroup" value="11" title="冠心病患者"
                               style="width:20px;height:20px;" validator='{"check":"请选择服务类型!"}'
                               pofield="persGroup"></span>&nbsp;&nbsp;冠心病患者
                    &nbsp;&nbsp;
                    <span onclick="clickP(this)">
                        <input type="checkbox" id="azhz" name="persGroup" value="12" title="癌症患者"
                               style="width:20px;height:20px;" validator='{"check":"请选择服务类型!"}'
                               pofield="persGroup"></span>&nbsp;&nbsp;癌症患者
                    &nbsp;&nbsp;
                    <span onclick="clickP(this)">
                        <input type="checkbox" id="qt" name="persGroup" value="99" title="其他"
                               style="width:20px;height:20px;" validator='{"check":"请选择服务类型!"}'
                               pofield="persGroup"></span>&nbsp;&nbsp;其他

                </div>
            </div>

            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 6px;">
                <legend style="font-size:20px;"><b>*</b>人口经济性质</legend>
            </fieldset>
            <div class="layui-form-item">
                <div class="layui-input-block" id="signsJjType">
                    <span onclick="clickJJ(this)">
                        <input id="ybrk" type="checkbox" validator='{"check":"人口经济性质不能为空!"}'
                             name="sJjType" value="1" title="一般人口"
                             style="width:20px;height:20px;" pofield="sJjType">&nbsp;&nbsp;一般人口
                    </span>&nbsp;&nbsp;
                    <span onclick="clickJJ(this)">
                        <input id="jdlk" type="checkbox" validator='{"check":"人口经济性质不能为空!"}'
                             name="sJjType" value="2" title="建档立卡贫困人口"
                             style="width:20px;height:20px;" pofield="sJjType">&nbsp;&nbsp;建档立卡贫困人口
                    </span>&nbsp;&nbsp;
                    <span onclick="clickJJ(this)">
                        <input id="dbh" type="checkbox" validator='{"check":"人口经济性质不能为空!"}'
                             name="sJjType" value="3" title="低保户"
                             style="width:20px;height:20px;" pofield="sJjType">&nbsp;&nbsp;低保户
                    </span>&nbsp;&nbsp;
                    <span onclick="clickJJ(this)">
                        <input id="tkh" type="checkbox" validator='{"check":"人口经济性质不能为空!"}'
                             name="sJjType" value="4" title="特困户（五保户）"
                             style="width:20px;height:20px;" pofield="sJjType">&nbsp;&nbsp;特困户（五保户）
                    </span>&nbsp;&nbsp;
                    <span onclick="clickJJ(this)">
                        <input id="jsds" type="checkbox" validator='{"check":"人口经济性质不能为空!"}'
                             name="sJjType" value="5" title="计生独伤残家庭"
                             style="width:20px;height:20px;" pofield="sJjType">&nbsp;&nbsp;计生独伤残家庭
                    </span>&nbsp;&nbsp;
                    <span onclick="clickJJ(this)">
                        <input id="jsdzn" type="checkbox" validator='{"check":"人口经济性质不能为空!"}'
                               name="sJjType" value="7" title="计生独子女户"
                               style="width:20px;height:20px;" pofield="sJjType">&nbsp;&nbsp;计生独子女户
                    </span>&nbsp;&nbsp;
                    <span onclick="clickJJ(this)">
                        <input id="jssn" type="checkbox" validator='{"check":"人口经济性质不能为空!"}'
                               name="sJjType" value="8" title="计生双女户"
                               style="width:20px;height:20px;" pofield="sJjType">&nbsp;&nbsp;计生双女户
                    </span>&nbsp;&nbsp;
                    <span onclick="clickJJ(this)">
                        <input id="pkh" type="checkbox" validator='{"check":"人口经济性质不能为空!"}'
                               name="sJjType" value="9" title="因病致贫户"
                               style="width:20px;height:20px;" pofield="sJjType">&nbsp;&nbsp;因病致贫户
                    </span>&nbsp;&nbsp;
                    <span onclick="clickJJ(this)">
                        <input id="qtJj" type="checkbox" validator='{"check":"人口经济性质不能为空!"}'
                               name="sJjType" value="10" title="其他"
                               style="width:20px;height:20px;" pofield="sJjType">&nbsp;&nbsp;其他
                    </span>&nbsp;&nbsp;
                </div>
            </div>

            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 6px;">
                <legend style="font-size:20px;"><b>*</b>签约期限</legend>
            </fieldset>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">协议开始<font color="red">*</font></label>
                    <div class="layui-input-inline" style="width: 200px;">
                        <input type="text" id="signFromDate" name="signFromDate" pofield="signFromDate"
                               onchange="wdate()" class="layui-input"
                               onfocus="WdatePicker({minDate:startMinDate(),dateFmt:'yyyy-MM-dd'})"
                               validator='{"msg":"签约时间不能为空!"}'>
                    </div>
                    <div class="layui-form-mid">起<font color="red">*</font> &nbsp; &nbsp;-</div>
                    <div class="layui-input-inline" style="width: 200px;">
                        <input disabled type="text" id="signToDate" name="signToDate" pofield="signToDate"
                               class="layui-input" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
                               validator='{"msg":"签约时间不能为空!"}'>
                    </div>
                    <div class="layui-form-mid">止</div>
                </div>
            </div>

            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 6px;">
                <legend style="font-size:20px;"><b>*</b>签约费</legend>
            </fieldset>
            <div class="layui-form-item">
                <input type="hidden" name="signczpay" pofield="signczpay" id="signczpay">
                <div id="bt">
                </div>
                <label class="layui-form-label">自费(必填)<font color="red">*</font></label>
                <div class="layui-input-inline" style="width: 100px;" id="sccc">
                    <input type="text" style="background-color: yellow" onblur="signPay();"
                           validator='{"msg":"请输入自费金额!"}' pofield="signzfpay" id="signzfpay" name="signzfpay"
                           lay-verify="email" autocomplete="off" class="layui-input">
                </div>
                <div class="layui-input-inline">&nbsp;&nbsp;
                    <%--<input type="checkbox" disabled="disabled" id="payStatePrivate" name="payStatePrivate" title="是否全自费" >&nbsp;&nbsp;是否全自费--%>
                </div>
            </div>

            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 6px;">
                <legend style="font-size:20px;"><b>*</b>协议</legend>
            </fieldset>
            <div class="layui-inline">
                <label class="layui-form-label">服务套餐</label>
                <div class="layui-input-block" id="signpackageid"></div>
            </div>

            <div class="layui-form-item layui-form-text">
                <label class="layui-form-label">补充协议内容</label>
                <div class="layui-input-block ">
                    <textarea id="signtext" name="signtext" pofield="signtext" placeholder="请输入补充协议内容"
                              class="layui-textarea"></textarea>
                </div>
            </div>

            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 6px;">
                <legend style="font-size:20px;"><b>*</b>医保类型</legend>
            </fieldset>
            <div class="layui-inline">
                <div class="layui-input-block" id="singlx">
                    <input type="radio" validator='{"msg":"医保类型不能为空!"}' value="0" disabled="disabled" title="省医保"
                           style="width:25px;height:25px;"> &nbsp;&nbsp;省医保&nbsp;&nbsp;
                    <input type="radio" validator='{"msg":"医保类型不能为空!"}' name="signlx" value="1" title="市医保"
                           style="width:25px;height:25px;" pofield="signlx">&nbsp;&nbsp;市医保&nbsp;&nbsp;
                    <input type="radio" validator='{"msg":"医保类型不能为空!"}' name="signlx" value="2" title="新农合"
                           style="width:25px;height:25px;" pofield="signlx">&nbsp;&nbsp;新农合&nbsp;&nbsp;
                    <input type="radio" validator='{"msg":"医保类型不能为空!"}' name="signlx" value="4" title="居民医保"
                           style="width:25px;height:25px;" pofield="signlx">&nbsp;&nbsp;居民医保&nbsp;&nbsp;
                    <input type="radio" validator='{"msg":"医保类型不能为空!"}' name="signlx" value="5" title="职工医保"
                           style="width:25px;height:25px;" pofield="signlx">&nbsp;&nbsp;职工医保&nbsp;&nbsp;
                    <input id="ybwcbId" type="radio" validator='{"msg":"医保类型不能为空!"}' name="signlx" value="3" title="未参保"
                           style="width:25px;height:25px;" pofield="signlx">&nbsp;&nbsp;未参保
                </div>
            </div>

            <div class="layui-form-item">
                <div class="layui-input-block" style="text-align: center;">
                    <input type="button" value="查看家庭成员" onclick="familymemger()" class="layui-btn layui-btn-small"/>
                    <input type="button" id="roleadd" value="保存" onclick="signAdd()"
                           class="layui-btn layui-btn-normal"/>
                    <input type="button" value="返回" onclick="Goto()" class="layui-btn layui-btn-normal"/>
                </div>
            </div>
        </div>
    </form>
</div>
</body>
</html>
