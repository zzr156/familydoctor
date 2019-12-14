<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/open/commonjs/tldLayui.jsp"%>
    <title>建档立卡异常居民修改界面</title>
</head>
<body bgcolor="white">
<!-- 新增 -->
<div style="padding:0 10px;">
    <form class="layui-form-pane" id="form_vo">
        <blockquote class="layui-elem-quote" style="text-align: center;font-size: 20px" >建档立卡居民信息</blockquote>
        <input type="hidden" id="id"  pofield="id"/>
        <input id="jdSourceType" pofield="jdSourceType" type="hidden">
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">健康档案号：</label>
                <div class="layui-input-inline">
                    <input type="text" disabled="rhfId" pofield="rhfId" id="rhfId" class="layui-input"/>
                </div>
            </div>

            <div class="layui-inline">
                <label class="layui-form-label">姓名</label>
                <div class="layui-input-inline">
                    <input type="text" disabled="disabled" id="patientName" name="patientName" pofield="patientName" class="layui-input"/>
                </div>
            </div>

            <div class="layui-inline">
                <label class="layui-form-label">身份证号码</label>
                <div class="layui-input-inline">
                    <input type="text" disabled="disabled" id="patientIdno" name="patientIdno" pofield="patientIdno" class="layui-input"/>
                </div>
            </div>

            <div class="layui-inline">
                <label class="layui-form-label">联系电话</label>
                <div class="layui-input-inline">
                    <input type="text" id="patientTel" name="patientTel" pofield="patientTel" class="layui-input"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline" id="lowState">
                <label class="layui-form-label">低保户</label>
                <div class="layui-input-inline" id="lowInsured">
                    <input type="radio" name="lowInsured" value="1" title="是" style="width:20px;height:20px;" pofield="lowInsured">&nbsp;&nbsp;是&nbsp;&nbsp;
                    <input type="radio" name="lowInsured" value="0" title="否" style="width:20px;height:20px;" pofield="lowInsured">&nbsp;&nbsp;否&nbsp;&nbsp;
                </div>
            </div>
            <div class="layui-inline" id="poorState">
                <label class="layui-form-label">特困户</label>
                <div class="layui-input-inline">
                    <input type="radio" name="poorHouseholds" value="1" title="是" style="width:20px;height:20px;" pofield="poorHouseholds">&nbsp;&nbsp;是&nbsp;&nbsp;
                    <input type="radio" name="poorHouseholds" value="0" title="否" style="width:20px;height:20px;" pofield="poorHouseholds">&nbsp;&nbsp;否&nbsp;&nbsp;
                </div>
            </div>

            <div class="layui-inline">
                <label class="layui-form-label">是否脱贫</label>
                <div class="layui-input-inline">
                    <input type="radio" name="isNotPoverty" value="1" title="是" style="width:20px;height:20px;" pofield="isNotPoverty">&nbsp;&nbsp;是&nbsp;&nbsp;
                    <input type="radio" name="isNotPoverty" value="0" title="否" style="width:20px;height:20px;" pofield="isNotPoverty">&nbsp;&nbsp;否&nbsp;&nbsp;
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">是否建档立卡</label>
                <div class="layui-input-inline">
                    <input type="radio" name="jdgfState" value="1" title="是" style="width:20px;height:20px;" pofield="jdgfState">&nbsp;&nbsp;是&nbsp;&nbsp;
                    <input type="radio" name="jdgfState" value="0" title="否" style="width:20px;height:20px;" pofield="jdgfState">&nbsp;&nbsp;否&nbsp;&nbsp;
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">县</label>
                <div class="layui-input-inline">
                    <input type="hidden" pofield="addrCountyCode" id="addrCountyCode">
                    <input type="text" disabled="disabled" pofield="addrCountyName" id="addrCountyName" class="layui-input"/>
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label"><b>* </b>街道(乡镇)</label>
                <div class="layui-input-inline">
                    <input type="hidden" id="addrRuralCode" pofield="addrRuralCode">
                    <input type="hidden" id="addrRuralName" pofield="addrRuralName">
                    <select id="addrRural" pofield="addrRural"  onchange="changeCommittee()" class="layui-input" validator='{"msg":"街道(乡镇)不能为空!"}'>
                        <option value="">--请选择街道--</option>
                    </select>
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label"><b>* </b>社区(村)</label>
                <div class="layui-input-inline">
                    <input type="hidden" id="addrVillageCode" pofield="addrVillageCode">
                    <input type="hidden" id="addrVillageName" pofield="addrVillageName">
                    <select id="addrVillage" pofield="addrVillage" class="layui-input" onchange="getValueName()" validator='{"msg":"社区(村)不能为空!"}'>
                        <option value="">--请选择社区(村)--</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">签约状态</label>
                <div class="layui-input-inline">
                    <input type="text" disabled="disabled" id="signState" pofield="signState" class="layui-input"/>
                </div>
            </div>

            <div class="layui-inline" id="reason">
                <label class="layui-form-label">未签原因</label>
                <div class="layui-input-inline">
                    <select id="notSignReason" class="layui-input" name="notSignReason" onchange="showReaSon()">
                        <option value="0">--请选择--</option>
                        <option value="1">死亡</option>
                        <option value="2">失联失踪</option>
                        <option value="3">迁出</option>
                        <option value="4">长期外出</option>
                        <option value="5">拒签</option>
                        <option value="6">服刑</option>
                        <option value="7">名单重复</option>
                        <option value="8">精神病人住康复医院，家属外出</option>
                        <option value="9">服兵役</option>
                        <option value="10">外籍</option>
                        <option value="11">外嫁</option>
                        <option value="12">新增人员</option>
                        <option value="13">联系不上</option>
                        <option value="14">身份名字不符</option>
                        <option value="15">暂时外出无法签约</option>
                        <option value="16">外地建档</option>
                        <option value="17">退出</option>
                        <option value="18">无社保卡</option>
                        <option value="19">出国</option>
                        <option value="20">其他</option>
                        <option value="21">未填写</option>
                    </select>
                </div>
            </div>
            <div class="layui-form-item" id="yyid">
                <label class="layui-form-label"><b>*</b><span id="cxyy">其他原因</span></label>
                <div class="layui-input-block">
                    <textarea type="text" id="otherReason" pofield="otherReason" placeholder="请输入原因" class="layui-textarea" ></textarea>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-block" style="text-align: center;">
                    <input type="button" id="back" value="返回" onclick="goback();" class="layui-btn layui-btn-normal" />
                    <input type="button" id="roleadd" value="修改保存" onclick="signModify()" class="layui-btn layui-btn-normal"/>
                </div>
            </div>
        </div>
    </form>


</div>
</body>
<script type="text/javascript" src="js/summaryArchiving_modify.js?v=1.0"></script>
</html>
