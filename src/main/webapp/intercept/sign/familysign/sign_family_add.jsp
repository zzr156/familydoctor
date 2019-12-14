<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/open/commonjs/tldLayui.jsp" %>
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
        <input type="hidden" id="subpagevo" pofield="subpagevo"/>
        <input type="hidden" id="nowtime" value="<%=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date())%>"/>

        <div class="layui-form-item">
            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 6px;">
                <legend style="font-size:20px;" id="jiafan">甲方</legend>
            </fieldset>

            <div class="layui-inline">
                <a class="layui-btn layui-btn-danger layui-btn-mini phis sysRoleAction_delete"
                   onclick="familymemberadd()"><i class=layui-icon>&#xe654;</i>新增家庭成员</a>
            </div>

            <div class="layui-form-item layui-form-text">
                <table class="layui-table">
                    <thead>
                        <tr>
                            <th style="width:3%;text-align:center;">名称</th>
                            <th style="width:3%;text-align:center;">身份证</th>
                            <th style="width:3%;text-align:center;">社保卡</th>
                            <th style="width:3%;text-align:center;">性别</th>
                            <th style="width:3%;text-align:center;">年龄</th>
                            <th style="width:3%;text-align:center;">关系</th>
                            <th style="width:3%;text-align:center;">操作</th>
                        </tr>
                    </thead>
                    <tbody id="family_list"></tbody>
                </table>
            </div>

            <div class="layui-inline">
                <label class="layui-form-label"><b>* </b>现住地址</label>
                <div class="layui-input-inline" id="cityid">
                    <select id="patientCity" name="patientCity" pofield="patientCity" class="layui-input"
                            onchange="changecounty()" validator='{"msg":"现住地址不能为空!"}'>
                        <option value="">--请选择市--</option>
                    </select>
                </div>
            </div>

            <div class="layui-inline">
                <div class="layui-input-inline" id="county">
                    <select id="patientArea" pofield="patientArea" class="layui-input" onchange="changestreet()"
                            validator='{"msg":"现住地址不能为空!"}'>
                        <option value="">--请选择区--</option>
                    </select>
                </div>
            </div>

            <div class="layui-inline">
                <div class="layui-input-inline" id="street">
                    <select id="patientStreet" pofield="patientStreet" class="layui-input" onchange="changeCommittee()"
                            validator='{"msg":"现住地址不能为空!"}'>
                        <option value="">--请选择街道--</option>
                    </select>
                </div>
            </div>

            <div class="layui-inline">
                <div class="layui-input-inline" id="committee">
                    <select id="patientNeighborhoodCommittee" pofield="patientNeighborhoodCommittee" class="layui-input"
                            validator='{"msg":"现住地址不能为空!"}'>
                        <option value="">--请选择居委会--</option>
                    </select>
                </div>
            </div>

            <div class="layui-form-item layui-form-text">
                <label class="layui-form-label"><b>* </b>详细地址</label>
                <div class="layui-input-block">
                    <textarea type="text" id="patientAddress" pofield="patientAddress" placeholder="请输入内容"
                              class="layui-textarea" validator='{"msg":"详细地址不能为空!"}'></textarea>
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
                    <input type="text" pofield="drTel" id="drTel" class="layui-input"
                           validator='{"msg":"联系方式不能为空!","rule":"phone"}'/>
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
                <legend style="font-size:20px;"><b>*</b>人口经济性质</legend>
            </fieldset>

            <div class="layui-form-item">
                <div class="layui-input-block" id="signsJjType">
                    <span onclick="clickJJ(this)">
                        <input id="ybrk" type="checkbox" validator='{"msg":"人口经济性质不能为空!"}'
                             name="sJjType" value="1" title="一般人口"
                             style="width:20px;height:20px;" pofield="sJjType">&nbsp;&nbsp;一般人口
                    </span>&nbsp;&nbsp;
                    <span onclick="clickJJ(this)">
                        <input id="jdlk" type="checkbox" validator='{"msg":"人口经济性质不能为空!"}'
                             name="sJjType" value="2" title="建档立卡贫困人口"
                             style="width:20px;height:20px;" pofield="sJjType">&nbsp;&nbsp;建档立卡贫困人口
                    </span>&nbsp;&nbsp;
                    <span onclick="clickJJ(this)">
                        <input id="dbh" type="checkbox" validator='{"msg":"人口经济性质不能为空!"}'
                             name="sJjType" value="3" title="低保户"
                             style="width:20px;height:20px;" pofield="sJjType">&nbsp;&nbsp;低保户
                    </span>&nbsp;&nbsp;
                    <span onclick="clickJJ(this)">
                        <input id="tkh" type="checkbox" validator='{"msg":"人口经济性质不能为空!"}'
                             name="sJjType" value="4" title="特困户（五保户）"
                             style="width:20px;height:20px;" pofield="sJjType">&nbsp;&nbsp;特困户（五保户）
                    </span>&nbsp;&nbsp;
                    <span onclick="clickJJ(this)">
                        <input id="jsjt" type="checkbox" validator='{"msg":"人口经济性质不能为空!"}'
                             name="sJjType" value="5" title="计生特殊家庭"
                             style="width:20px;height:20px;" pofield="sJjType">&nbsp;&nbsp;计生特殊家庭
                    </span>&nbsp;&nbsp;
                </div>
            </div>

            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 6px;">
                <legend style="font-size:20px;"><b>*</b>签约期限</legend>
            </fieldset>

            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">协议开始</label>
                    <div class="layui-input-inline" style="width: 200px;">
                        <input type="text" id="signFromDate" name="signFromDate" pofield="signFromDate"
                               onchange="wdate()" class="layui-input" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
                               validator='{"msg":"签约时间不能为空!"}'>
                    </div>
                    <div class="layui-form-mid">起 &nbsp; &nbsp;-</div>
                    <div class="layui-input-inline" style="width: 200px;">
                        <input type="text" id="signToDate" name="signToDate" pofield="signToDate" class="layui-input"
                               onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" validator='{"msg":"签约时间不能为空!"}'>
                    </div>
                    <div class="layui-form-mid">止</div>
                </div>
            </div>

            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 6px;">
                <legend style="font-size:20px;">签约费用合计</legend>
            </fieldset>

            <div class="layui-form-item">
                <label class="layui-form-label">基本公卫预补助</label>
                <div class="layui-input-inline" style="width: 100px;" id="ybz">
                    <input type="text" disabled="disabled" id="amountPrivateybz" name="amountPrivateybz" pofield="amountPrivateybz"
                           lay-verify="email" autocomplete="off" class="layui-input">
                </div>
                <label class="layui-form-label">医保预支付</label>
                <div class="layui-input-inline" style="width: 100px;" id="scc">
                    <input type="text" disabled="disabled" id="amountPrivate" name="amountPrivate" pofield="amountPrivate" lay-verify="email"
                           autocomplete="off" class="layui-input">
                </div>
                <label class="layui-form-label">财政补助</label>
                <div class="layui-input-inline" style="width: 100px;" id="czbz">
                    <input type="text" id="signczpay" name="signczpay" pofield="signczpay" lay-verify="email"
                           autocomplete="off" class="layui-input" disabled="disabled">
                </div>
                <label class="layui-form-label">自费</label>
                <div class="layui-input-inline" style="width: 100px;">
                    <input type="text" pofield="signzfpay" id="signzfpay" name="signzfpay" lay-verify="email"
                           autocomplete="off" class="layui-input" disabled="disabled">
                </div>
                <label class="layui-form-label">合计</label>
                <div class="layui-input-inline" style="width: 100px;">
                    <input type="text" pofield="totalpay" id="totalpay" name="totalpay" lay-verify="email"
                           autocomplete="off" class="layui-input" disabled="disabled">
                </div>
            </div>

            <div class="layui-form-item">
                <div class="layui-input-block" style="text-align: center;margin: 50px 0 20px 0;">
                    <a class="layui-btn layui-btn-danger " id="roleadd" onclick="familyAdd()">
                        <i class=layui-icon>&#xe654;</i>保存
                    </a>
                    <a class="layui-btn layui-btn-normals " id="" onclick="Goto()">
                        <i class=layui-icon>&#xe65c;</i>返回
                    </a>
                </div>
            </div>
        </div>
    </form>
</div>
<jsp:include page="/intercept/sign/familysign/template/sign_family_list.jsp" flush="false"/>
</body>
<script type="text/javascript" src="js/sign_family_add.js?v=1.1"></script>
</html>
