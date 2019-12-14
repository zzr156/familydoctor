<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/open/commonjs/tldLayui.jsp"%>
    <script type="text/javascript" src="js/archivingCheck_add.js?v=1.0"></script>
    <title>建档立卡居民健康核查新增界面</title>
</head>
<body bgcolor="white">
<!-- 新增 -->
<div style="padding:0 10px;">
    <form class="layui-form-pane" id="form_vo">
        <blockquote class="layui-elem-quote" style="text-align: center;font-size: 20px;background-color:#8B91A0;color: white" >建档立卡贫困人口健康状况核查表</blockquote>
        <input type="hidden" id="id"  pofield="id" name = "id"/>
        <input type="hidden" id="archivingId"  pofield="archivingId" name = "archivingId"/>
        <div class="layui-form-item">
            <p class="layui-elem-field" style="text-align: center;font-size: 15px">建档立卡所属区域：<span id="areaName" style="text-decoration: underline;">          县</span>
                <span id="streetName" style="text-decoration: underline;">            镇</span>
                <span id="cunName" style="text-decoration: underline;">              村</span>
                （区划代码：<span id="areaCode" style="text-decoration: underline;">              </span>）</br>
                建档立卡所属对象：  <span id="dxType">
                    <input type="radio" name="objectType" value="1" title="扶贫开发对象" style="width:20px;height:20px;" pofield="objectType">&nbsp;&nbsp;扶贫开发对象&nbsp;&nbsp;
                    <input type="radio" name="objectType" value="2" title="省定扶贫标准下的低保对象" style="width:20px;height:20px;" pofield="objectType">&nbsp;&nbsp;省定扶贫标准下的低保对象&nbsp;&nbsp;
                    <%--<input type="radio" name="objectType" value="3" title="既是1也是2的对象" style="width:20px;height:20px;" pofield="objectType">&nbsp;&nbsp;既是1也是2的对象&nbsp;&nbsp;--%>
                    <%--1.扶贫开发对象    2.省定扶贫标准下的低保对象    3.既是1也是2的对象--%>
                </span>
            </p>
        </div>
        <blockquote class="layui-elem-quote" style="text-align: center;font-size: 15px;background-color:#8B91A0;color: white" >扶贫办建档情况</blockquote>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">户编号</label>
                <div class="layui-input-inline">
                    <input type="text" pofield="patientNo" id="patientNo" name="patientNo" class="layui-input"/>
                </div>
            </div>

            <div class="layui-inline">
                <label class="layui-form-label">家庭人口数</label>
                <div class="layui-input-inline">
                    <input type="number" id="familySize" name="familySize" pofield="familySize" class="layui-input"/>
                </div>
                <div class="layui-input-inline">人</div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">是否脱贫</label>
                <div class="layui-input-inline">
                    <input type="radio" name="povertyState" value="1" title="是" style="width:20px;height:20px;" pofield="povertyState">&nbsp;&nbsp;是&nbsp;&nbsp;
                    <input type="radio" name="povertyState" value="0" title="否" style="width:20px;height:20px;" pofield="povertyState">&nbsp;&nbsp;否&nbsp;&nbsp;
                </div>
            </div>
        </div>
        <blockquote class="layui-elem-quote" style="text-align: center;font-size: 15px;background-color:#8B91A0;color: white" >基本情况</blockquote>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">姓名</label>
                <div class="layui-input-inline">
                    <input type="text" disabled="disabled" id="patientName" name="patientName" pofield="patientName" class="layui-input layui-bg-gray"/>
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">性别</label>
                <div class="layui-input-inline">
                    <input type="text" disabled="disabled" id="patientSex" name="patientSex" pofield="patientSex" class="layui-input layui-bg-gray"/>
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">出生日期</label>
                <div class="layui-input-inline">
                    <input type="text" disabled="disabled" id="patientBirthDay" name="patientBirthDay" pofield="patientBirthDay" class="layui-input layui-bg-gray"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">身份证</label>
                <div class="layui-input-inline">
                    <input type="text" disabled="disabled" id="patientIdno" name="patientIdno" pofield="patientIdno" class="layui-input layui-bg-gray"/>
                </div>
            </div>

            <div class="layui-inline">
                <label class="layui-form-label">与户主关系</label>
                <div class="layui-input-inline">
                    <%--<input type="text" id="householdRelationship" name="householdRelationship" pofield="householdRelationship" class="layui-input"/>--%>
                    <select id="householdRelationship" class="layui-input" name="householdRelationship">
                        <option value="0">--请选择--</option>
                    </select>
                </div>
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">有无户籍</label>
                <div class="layui-input-inline" id="lowInsured">
                    <input type="radio" name="residenceState" value="0" title="无" style="width:20px;height:20px;" pofield="residenceState">&nbsp;&nbsp;无&nbsp;&nbsp;
                    <input type="radio" name="residenceState" value="1" title="有" style="width:20px;height:20px;" pofield="residenceState">&nbsp;&nbsp;有&nbsp;&nbsp;
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">有无纳入计生管理</label>
                <div class="layui-input-inline" >
                    <input type="radio" name="fpaState" value="0" title="无" style="width:20px;height:20px;" pofield="fpaState">&nbsp;&nbsp;无&nbsp;&nbsp;
                    <input type="radio" name="fpaState" value="1" title="有" style="width:20px;height:20px;" pofield="fpaState">&nbsp;&nbsp;有&nbsp;&nbsp;
                </div>
            </div>
            <%--<div class="layui-inline">
                <label class="layui-form-label" style="width: 150px">有无纳入建档立卡人口</label>
                <div class="layui-input-inline" >
                    <input type="radio" name="archivingCardState" value="0" title="无" style="width:20px;height:20px;" pofield="archivingCardState">&nbsp;&nbsp;无&nbsp;&nbsp;
                    <input type="radio" name="archivingCardState" value="1" title="有" style="width:20px;height:20px;" pofield="archivingCardState">&nbsp;&nbsp;有&nbsp;&nbsp;
                </div>
            </div>--%>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">婚姻状况</label>
                <div class="layui-input-block" >
                    <input type="radio" name="maritalStatus" value="1" title="未婚" style="width:20px;height:20px;" pofield="maritalStatus">&nbsp;&nbsp;未婚&nbsp;&nbsp;
                    <input type="radio" name="maritalStatus" value="2" title="初婚" style="width:20px;height:20px;" pofield="maritalStatus">&nbsp;&nbsp;初婚&nbsp;&nbsp;
                    <input type="radio" name="maritalStatus" value="3" title="再婚" style="width:20px;height:20px;" pofield="maritalStatus">&nbsp;&nbsp;再婚&nbsp;&nbsp;
                    <input type="radio" name="maritalStatus" value="4" title="离婚" style="width:20px;height:20px;" pofield="maritalStatus">&nbsp;&nbsp;离婚&nbsp;&nbsp;
                    <input type="radio" name="maritalStatus" value="5" title="丧偶" style="width:20px;height:20px;" pofield="maritalStatus">&nbsp;&nbsp;丧偶&nbsp;&nbsp;
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">计生家庭户类型</label>
                <div class="layui-input-block" >
                    <input type="radio" name="familyType" value="1" title="无孩" style="width:20px;height:20px;" pofield="familyType">&nbsp;&nbsp;无孩&nbsp;&nbsp;
                    <input type="radio" name="familyType" value="2" title="一男" style="width:20px;height:20px;" pofield="familyType">&nbsp;&nbsp;一男&nbsp;&nbsp;
                    <input type="radio" name="familyType" value="3" title="一女" style="width:20px;height:20px;" pofield="familyType">&nbsp;&nbsp;一女&nbsp;&nbsp;
                    <input type="radio" name="familyType" value="4" title="二女" style="width:20px;height:20px;" pofield="familyType">&nbsp;&nbsp;二女&nbsp;&nbsp;
                    <input type="radio" name="familyType" value="5" title="二男" style="width:20px;height:20px;" pofield="familyType">&nbsp;&nbsp;二男&nbsp;&nbsp;
                    <input type="radio" name="familyType" value="6" title="一女一男" style="width:20px;height:20px;" pofield="familyType">&nbsp;&nbsp;一女一男&nbsp;&nbsp;
                    <input type="radio" name="familyType" value="7" title="一男一女" style="width:20px;height:20px;" pofield="familyType">&nbsp;&nbsp;一男一女&nbsp;&nbsp;
                    <input type="radio" name="familyType" value="8" title="三孩" style="width:20px;height:20px;" pofield="familyType">&nbsp;&nbsp;三孩&nbsp;&nbsp;
                    <input type="radio" name="familyType" value="9" title="四孩" style="width:20px;height:20px;" pofield="familyType">&nbsp;&nbsp;四孩&nbsp;&nbsp;
                    <input type="radio" name="familyType" value="10" title="五孩及以上" style="width:20px;height:20px;" pofield="familyType">&nbsp;&nbsp;五孩及以上&nbsp;&nbsp;
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">社会保障号</label>
                <div class="layui-input-inline">
                    <input type="text" id="patientCard" name="patientCard" pofield="patientCard" class="layui-input"/>
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">居民健康档案号</label>
                <div class="layui-input-inline">
                    <input type="text" disabled="disabled" id="patientJmda" name="patientJmda" pofield="patientJmda" class="layui-input layui-bg-gray"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">户籍地(县乡村）</label>
                <div class="layui-input-inline">
                    <input type="text" disabled="disabled" id="accDonicilePlace" name="accDonicilePlace" pofield="accDonicilePlace" class="layui-input layui-bg-gray"/>
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">居住地(县乡村）</label>
                <div class="layui-input-inline">
                    <input style="width: 350px" type="text" disabled="disabled" id="residencePlace" name="residencePlace" pofield="residencePlace" class="layui-input layui-bg-gray"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label" style="width: 200px">居住地详址（门牌号）</label>
                <div class="layui-input-inline">
                    <input type="text" disabled="disabled" id="residencePlaceAddr" name="residencePlaceAddr" pofield="residencePlaceAddr" class="layui-input layui-bg-gray"/>
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label"><b>*</b>联系电话</label>
                <div class="layui-input-inline">
                    <input type="text" id="patientTel" name="patientTel" pofield="patientTel" class="layui-input" validator='{"msg":"联系电话不能为空!"}'/>
                </div>
            </div>
        </div>
        <blockquote class="layui-elem-quote" style="text-align: center;font-size: 15px;background-color:#8B91A0;color: white" >建档立卡贫困户类型</blockquote>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">服务类型</label>
                <div class="layui-input-block" id="signPersGroup" >
                    <span onclick="clickP(this)"><input type="checkbox" disabled="disabled"  id="ordinary" name="serviceType" value="1"  title="普通服务人群" style="width:20px;height:20px;" validator='{"check":"请选择服务类型!"}' pofield="serviceType">&nbsp;&nbsp;普通服务人群</span>&nbsp;&nbsp;
                    <span onclick="clickP(this)"><input type="checkbox" disabled="disabled"  id="young"name="serviceType" value="2" title="0-6岁儿童"style="width:20px;height:20px;" validator='{"check":"请选择服务类型!"}' pofield="serviceType">&nbsp;&nbsp;0-6岁儿童</span>
                    <span onclick="clickP(this)"><input type="checkbox" disabled="disabled" id="yuncf" name="serviceType" value="3"  title="孕产妇"style="width:20px;height:20px;" validator='{"check":"请选择服务类型!"}' pofield="serviceType">&nbsp;&nbsp;孕产妇</span>&nbsp;&nbsp;
                    <span onclick="clickP(this)"><input type="checkbox" disabled="disabled" id="oldman" name="serviceType" value="4"  title="老年人服务"style="width:20px;height:20px;"  validator='{"check":"请选择服务类型!"}' pofield="serviceType">&nbsp;&nbsp;老年人服务</span>&nbsp;&nbsp;
                    <span onclick="clickP(this)"><input type="checkbox" disabled="disabled" id="gxy" name="serviceType" value="5"  title="高血压" style="width:20px;height:20px;" validator='{"check":"请选择服务类型!"}' pofield="serviceType">&nbsp;&nbsp;高血压</span>&nbsp;&nbsp;
                    <span onclick="clickP(this)"><input type="checkbox" disabled="disabled" id="tnb" name="serviceType" value="6" title="糖尿病" style="width:20px;height:20px;" validator='{"check":"请选择服务类型!"}' pofield="serviceType">&nbsp;&nbsp;糖尿病</span>&nbsp;&nbsp;
                    <span onclick="clickP(this)"><input type="checkbox" disabled="disabled" id="jsb" name="serviceType" value="7"  title="严重精神障碍患者"style="width:20px;height:20px;" validator='{"check":"请选择服务类型!"}' pofield="serviceType">&nbsp;&nbsp;严重精神障碍患者</span>&nbsp;&nbsp;
                    <span onclick="clickP(this)"><input type="checkbox" disabled="disabled" id="jhb" name="serviceType" value="8"  title="结核病" style="width:20px;height:20px;" validator='{"check":"请选择服务类型!"}' pofield="serviceType">&nbsp;&nbsp;结核病</span>&nbsp;&nbsp;
                    <span onclick="clickP(this)"><input type="checkbox" disabled="disabled" id="cjr" name="serviceType" value="9"  title="残疾人"style="width:20px;height:20px;" validator='{"check":"请选择服务类型!"}' pofield="serviceType">&nbsp;&nbsp;残疾人</span>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">是否计生特殊家庭</label>
                <div class="layui-input-inline">
                    <input type="radio" name="specialFamily" value="0" title="否" style="width:20px;height:20px;" pofield="specialFamily">&nbsp;&nbsp;否&nbsp;&nbsp;
                    <input type="radio" name="specialFamily" value="1" title="是" style="width:20px;height:20px;" pofield="specialFamily">&nbsp;&nbsp;是&nbsp;&nbsp;
                </div>
            </div>
            <%--<div class="layui-inline" hidden = "hidden">
                <label id="feeByYear" class="layui-form-label" style="width:150px;">2017年诊疗费用总额</label>
                <div class="layui-input-inline">
                    <input type="text" id="totalFee" name="totalFee" pofield="totalFee" class="layui-input"/>
                </div>
                <div class="layui-form-mid">元</div>
            </div>--%>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">大病名称</label>
                <div class="layui-input-block" >
                    <span ><input type="checkbox" id="etfjgqs" name="illnessName" value="1"  title="儿童先天性心脏房间隔缺损" style="width:20px;height:20px;"  pofield="illnessName">&nbsp;&nbsp;儿童先天性心脏房间隔缺损</span>&nbsp;&nbsp;
                    <span ><input type="checkbox" id="etsjgqs"name="illnessName" value="2" title="儿童先天性心脏室间隔缺损"style="width:20px;height:20px;"  pofield="illnessName">&nbsp;&nbsp;儿童先天性心脏室间隔缺损</span>
                    <span ><input type="checkbox" id="etdgwb" name="illnessName" value="3"  title="儿童先天性动脉导管未闭"style="width:20px;height:20px;"  pofield="illnessName">&nbsp;&nbsp;儿童先天性动脉导管未闭</span>&nbsp;&nbsp;
                    <span ><input type="checkbox" id="etbxz" name="illnessName" value="4"  title="儿童先天性肺动脉瓣狭窄"style="width:20px;height:20px;"   pofield="illnessName">&nbsp;&nbsp;儿童先天性肺动脉瓣狭窄</span>&nbsp;&nbsp;
                    <span ><input type="checkbox" id="etlbbxb" name="illnessName" value="5"  title="儿童急性淋巴细胞白血病" style="width:20px;height:20px;"  pofield="illnessName">&nbsp;&nbsp;儿童急性淋巴细胞白血病</span>&nbsp;&nbsp;
                    <span ><input type="checkbox" id="etzylbxb" name="illnessName" value="6" title="儿童急性早幼粒细胞白血病" style="width:20px;height:20px;"  pofield="illnessName">&nbsp;&nbsp;儿童急性早幼粒细胞白血病</span>&nbsp;&nbsp;
                    <span ><input type="checkbox" id="sga" name="illnessName" value="7"  title="食管癌"style="width:20px;height:20px;"  pofield="illnessName">&nbsp;&nbsp;食管癌</span>&nbsp;&nbsp;
                    <span ><input type="checkbox" id="wa" name="illnessName" value="8"  title="胃癌" style="width:20px;height:20px;"  pofield="illnessName">&nbsp;&nbsp;胃癌</span>&nbsp;&nbsp;
                    <span ><input type="checkbox" id="jca" name="illnessName" value="9"  title="结肠癌"style="width:20px;height:20px;"  pofield="illnessName">&nbsp;&nbsp;结肠癌</span>
                    <span ><input type="checkbox" id="zca" name="illnessName" value="10"  title="直肠癌"style="width:20px;height:20px;"  pofield="illnessName">&nbsp;&nbsp;直肠癌</span>
                    <span ><input type="checkbox" id="rxa" name="illnessName" value="11"  title="乳腺癌" style="width:20px;height:20px;"  pofield="illnessName">&nbsp;&nbsp;乳腺癌</span>&nbsp;&nbsp;
                    <span ><input type="checkbox" id="gja" name="illnessName" value="12"  title="宫颈癌"style="width:20px;height:20px;"  pofield="illnessName">&nbsp;&nbsp;宫颈癌</span>
                    <span ><input type="checkbox" id="zmqsb" name="illnessName" value="13"  title="终末期肾病"style="width:20px;height:20px;"  pofield="illnessName">&nbsp;&nbsp;终末期肾病</span>
                </div>
            </div>
        </div>
        <blockquote class="layui-elem-quote" style="text-align: center;font-size: 15px;background-color:#8B91A0;color: white" >签约情况</blockquote>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">纸质签约</label>
                <div class="layui-input-inline" >
                    <input type="radio" name="paperSignState" value="0" title="未签约" style="width:20px;height:20px;" pofield="paperSignState">&nbsp;&nbsp;未签约&nbsp;&nbsp;
                    <input type="radio" name="paperSignState" value="1" title="已签约" style="width:20px;height:20px;" pofield="paperSignState">&nbsp;&nbsp;已签约&nbsp;&nbsp;
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">有无签约协议</label>
                <div class="layui-input-inline" >
                    <input type="radio" name="signAgreementState" value="0" title="无" style="width:20px;height:20px;" pofield="signAgreementState">&nbsp;&nbsp;无&nbsp;&nbsp;
                    <input type="radio" name="signAgreementState" value="1" title="有" style="width:20px;height:20px;" pofield="signAgreementState">&nbsp;&nbsp;有&nbsp;&nbsp;
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">有无服务手册</label>
                <div class="layui-input-inline" >
                    <input type="radio" name="serviceHandbookState" value="0" title="无" style="width:20px;height:20px;" pofield="serviceHandbookState">&nbsp;&nbsp;无&nbsp;&nbsp;
                    <input type="radio" name="serviceHandbookState" value="1" title="有" style="width:20px;height:20px;" pofield="serviceHandbookState">&nbsp;&nbsp;有&nbsp;&nbsp;
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label" style="width: 250px;">有无爱心服务卡（联络卡）</label>
                <div class="layui-input-inline" >
                    <input type="radio" name="contactCardState" value="0" title="无" style="width:20px;height:20px;" pofield="contactCardState">&nbsp;&nbsp;无&nbsp;&nbsp;
                    <input type="radio" name="contactCardState" value="1" title="有" style="width:20px;height:20px;" pofield="contactCardState">&nbsp;&nbsp;有&nbsp;&nbsp;
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">签约服务开始时间</label>
                <div class="layui-input-inline">
                    <input type="text" disabled="disabled" id="signFromDate" name="signFromDate" pofield="signFromDate" class="layui-input layui-bg-gray"/>
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">签约服务结束时间</label>
                <div class="layui-input-inline">
                    <input type="text" disabled="disabled" id="signToDate" name="signToDate" pofield="signToDate" class="layui-input layui-bg-gray"/>
                </div>
            </div>
        </div>
        <div class="layui-form-item" id="reason">
            <div class="layui-inline" >
                <label class="layui-form-label">未签原因</label>
                <div class="layui-input-inline">
                    <select id="notSignReason" class="layui-input" name="notSignReason" onchange="showReaSon()">
                        <option value="0">--请选择--</option>
                        <option value="1">死亡</option>
                        <option value="2">迁出</option>
                        <option value="3">长期外出</option>
                        <option value="4">服刑</option>
                        <option value="5">精神病院强制住院</option>
                        <option value="6">服兵役</option>
                        <option value="7">未处户</option>
                        <option value="8">新增人员</option>
                        <option value="9">联系不上</option>
                        <option value="10">暂时未签约</option>
                        <option value="11">异地签约</option>
                        <option value="12">无有效证件</option>
                        <option value="13">其它</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="layui-form-item" id="yyid">
            <label class="layui-form-label"><b>*</b><span id="cxyy">其他原因</span></label>
            <div class="layui-input-block">
                <textarea type="text" id="otherReason" name="otherReason" pofield="otherReason" placeholder="请输入原因" class="layui-textarea" ></textarea>
            </div>
        </div>
        <blockquote class="layui-elem-quote" style="text-align: center;font-size: 15px;background-color:#8B91A0;color: white" >履约情况</blockquote>
        <%--所有人群--%>
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 6px;">
            <legend style="font-size:15px;">所有人群</legend>
        </fieldset>
        <div class="layui-form-item" id="allPeopleType">
            <div class="layui-inline">
                <label class="layui-form-label" style="width: 150px">居民健康档案建档时间</label>
                <div class="layui-input-inline">
                    <input type="text" disabled="disabled" id="jmdaTime" name="jmdaTime" pofield="jmdaTime" class="layui-input layui-bg-gray"/>
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label" style="width: 200px">居民健康档案最后更新时间</label>
                <div class="layui-input-inline">
                    <input type="text" disabled="disabled" id="jmdaLastUpdateTime" name="jmdaLastUpdateTime" pofield="jmdaLastUpdateTime" class="layui-input layui-bg-gray"/>
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label" style="width: 150px">签约后健康体检次数</label>
                <div class="layui-input-inline">
                    <input type="text" disabled="disabled" id="signPeNum" name="signPeNum" pofield="signPeNum" class="layui-input layui-bg-gray"/>
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label" style="width: 150px">最后一次健康体检时间</label>
                <div class="layui-input-inline">
                    <input type="text" disabled="disabled" id="lastPeTime" name="lastPeTime" pofield="lastPeTime" class="layui-input layui-bg-gray"/>
                </div>
            </div>
        </div>
        <%--0-6岁儿童--%>
        <div id="etType" hidden = "hidden" >
            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 6px;">
                <legend style="font-size:15px;">0-6岁儿童</legend>
            </fieldset>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">签约后随访次数</label>
                    <div class="layui-input-inline">
                        <input type="text" disabled="disabled" id="etFollowNum" name="etFollowNum" pofield="etFollowNum" class="layui-input layui-bg-gray"/>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label" style="width: 150px">最后一次随访时间</label>
                    <div class="layui-input-inline">
                        <input type="text" disabled="disabled" id="etLastFollowTime" name="etLastFollowTime" pofield="etLastFollowTime" class="layui-input layui-bg-gray"/>
                    </div>
                </div>
            </div>
        </div>
        <%--孕产妇--%>
        <div id="ycfType" hidden = "hidden">
            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 6px;">
                <legend style="font-size:15px;">孕产妇</legend>
            </fieldset>
            <div class="layui-form-item" >
                <div class="layui-inline">
                    <label class="layui-form-label">签约后随访次数</label>
                    <div class="layui-input-inline">
                        <input type="text" disabled="disabled" id="ycfFollowNum" name="ycfFollowNum" pofield="ycfFollowNum" class="layui-input layui-bg-gray"/>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">最后一次随访时间</label>
                    <div class="layui-input-inline">
                        <input type="text" disabled="disabled" id="ycfLastFollowTime" name="ycfLastFollowTime" pofield="ycfLastFollowTime" class="layui-input layui-bg-gray"/>
                    </div>
                </div>
            </div>
        </div>
        <%--高血压患者--%>
        <div id="gxyType" hidden = "hidden">
            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 6px;">
                <legend style="font-size:15px;">高血压患者</legend>
            </fieldset>
            <div class="layui-form-item" >
                <div class="layui-inline">
                    <label class="layui-form-label">签约后测血压次数</label>
                    <div class="layui-input-inline">
                        <input type="text" disabled="disabled" id="gxyNum" name="gxyNum" pofield="gxyNum" class="layui-input layui-bg-gray"/>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label" style="width: 150px">最后一次测血压时间</label>
                    <div class="layui-input-inline">
                        <input type="text" disabled="disabled" id="gxyLastTime" name="gxyLastTime" pofield="gxyLastTime" class="layui-input layui-bg-gray"/>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">签约后随访次数</label>
                    <div class="layui-input-inline">
                        <input type="text" disabled="disabled" id="gxyFollowNum" name="gxyFollowNum" pofield="gxyFollowNum" class="layui-input layui-bg-gray"/>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">最后一次随访时间</label>
                    <div class="layui-input-inline">
                        <input type="text" disabled="disabled" id="gxyLastFollowTime" name="gxyLastFollowTime" pofield="gxyLastFollowTime" class="layui-input layui-bg-gray"/>
                    </div>
                </div>
            </div>
        </div>


        <%--糖尿病患者--%>
        <div id="tnbType" hidden = "hidden">
            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 6px;">
                <legend style="font-size:15px;">糖尿病患者</legend>
            </fieldset>
            <div class="layui-form-item" >
                <div class="layui-inline">
                    <label class="layui-form-label">签约后测血糖次数</label>
                    <div class="layui-input-inline">
                        <input type="text" disabled="disabled" id="tnbNum" name="tnbNum" pofield="tnbNum" class="layui-input layui-bg-gray"/>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label" style="width: 150px">最后一次测血糖时间</label>
                    <div class="layui-input-inline">
                        <input type="text" disabled="disabled" id="tnbLastTime" name="tnbLastTime" pofield="tnbLastTime" class="layui-input layui-bg-gray"/>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">签约后随访次数</label>
                    <div class="layui-input-inline">
                        <input type="text" disabled="disabled" id="tnbFollowNum" name="tnbFollowNum" pofield="tnbFollowNum" class="layui-input layui-bg-gray"/>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">最后一次随访时间</label>
                    <div class="layui-input-inline">
                        <input type="text" disabled="disabled" id="tnbLastFollowTime" name="tnbLastFollowTime" pofield="tnbLastFollowTime" class="layui-input layui-bg-gray"/>
                    </div>
                </div>
            </div>
        </div>

        <%--严重精神障碍患者--%>
        <div id="jsbType" hidden = "hidden">
            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 6px;">
                <legend style="font-size:15px;">严重精神障碍患者</legend>
            </fieldset>
            <div class="layui-form-item" >
                <div class="layui-inline">
                    <label class="layui-form-label">签约后随访次数</label>
                    <div class="layui-input-inline">
                        <input type="text" disabled="disabled" id="jsbFollowNum" name="jsbFollowNum" pofield="jsbFollowNum" class="layui-input layui-bg-gray"/>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">最后一次随访时间</label>
                    <div class="layui-input-inline">
                        <input type="text" disabled="disabled" id="jsbLastFollowTime" name="jsbLastFollowTime" pofield="jsbLastFollowTime" class="layui-input layui-bg-gray"/>
                    </div>
                </div>
            </div>
        </div>
        <blockquote class="layui-elem-quote" style="text-align: center;font-size: 15px;background-color:#8B91A0;color: white"  id="yearFee">XXXX年在基层医疗机构救治资金构成情况</blockquote>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label" >医疗费用总额</label>
                <div class="layui-input-block"  >
                    <input type="number" id="totalCost" disabled="disabled" name="totalCost" pofield="totalCost" class="layui-input layui-bg-gray"/>
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label" >自付金额</label>
                <div class="layui-input-block"  >
                    <input type="number" id="zfFee" disabled="disabled" name="zfFee" pofield="zfFee" class="layui-input layui-bg-gray"/>
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label" >新农合报销</label>
                <div class="layui-input-block"  >
                    <input type="number" id="ncmsFee" disabled="disabled" name="ncmsFee" pofield="ncmsFee" class="layui-input layui-bg-gray"/>
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label" >大病补充补偿</label>
                <div class="layui-input-block"  >
                    <input type="number" id="civilAssistance" disabled="disabled" name="civilAssistance" pofield="civilAssistance" class="layui-input layui-bg-gray"/>
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label" >扶贫资金救助</label>
                <div class="layui-input-block" >
                    <input type="number" id="sgcpa" disabled="disabled" name="sgcpa" pofield="sgcpa" class="layui-input layui-bg-gray"/>
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label" >其他</label>
                <div class="layui-input-block"  >
                    <input type="number" id="otherFund" disabled="disabled" name="otherFund" pofield="otherFund" class="layui-input layui-bg-gray"/>
                </div>
            </div>
        </div>
        <blockquote class="layui-elem-quote" style="text-align: center;font-size: 15px;background-color:#8B91A0;color: white" >政策知晓与满意度</blockquote>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label" style="width: 150px">是否知晓健康扶贫政策</label>
                <div class="layui-input-block" style="width: 400px" >
                    <input type="radio" name="knowHelpPoorPolicy" value="0" title="不知道" style="width:20px;height:20px;" pofield="knowHelpPoorPolicy">&nbsp;&nbsp;不知道&nbsp;&nbsp;
                    <input type="radio" name="knowHelpPoorPolicy" value="1" title="知道" style="width:20px;height:20px;" pofield="knowHelpPoorPolicy">&nbsp;&nbsp;知道&nbsp;&nbsp;
                    <input type="radio" name="knowHelpPoorPolicy" value="2" title="有听说，但具体内容不了解" style="width:20px;height:20px;" pofield="knowHelpPoorPolicy">&nbsp;&nbsp;有听说，但具体内容不了解&nbsp;&nbsp;
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label" style="width: 150px">对签约服务是否满意</label>
                <div class="layui-input-block" style="width: 400px" >
                    <input type="radio" name="satisfiedState" value="1" title="满意" style="width:20px;height:20px;" pofield="satisfiedState">&nbsp;&nbsp;满意&nbsp;&nbsp;
                    <input type="radio" name="satisfiedState" value="2" title="基本满意" style="width:20px;height:20px;" pofield="satisfiedState">&nbsp;&nbsp;基本满意&nbsp;&nbsp;
                    <input type="radio" name="satisfiedState" value="3" title="不满意" style="width:20px;height:20px;" pofield="satisfiedState">&nbsp;&nbsp;不满意&nbsp;&nbsp;
                </div>
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label" style="width: 150px">核查员签名(计生管理员）</label>
                <div class="layui-input-inline" >
                    <input type="hidden" id="inspectorOneUrl" pofield="inspectorOneUrl">
                    <select id="inspectorOne" name="inspectorOne" pofield="inspectorOne" class="layui-input" onchange="chooseOneUrl()">

                    </select>
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">核查日期</label>
                <div class="layui-input-inline" >
                    <input type="text" id="checkDate"  pofield="checkDate" class="layui-input" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" >
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label" style="width: 150px">核查员签名(医务人员）</label>
                <div class="layui-input-inline" >
                    <input type="hidden" id="inspectorTwoUrl" pofield="inspectorTwoUrl">
                    <select id="inspectorTwo"  pofield="inspectorTwo" class="layui-input" onchange="chooseTowUrl()">

                    </select>
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">核查日期</label>
                <div class="layui-input-inline" >
                    <input type="text" id="checkYwDate"  pofield="checkYwDate" class="layui-input" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"  >
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">录入人员</label>
                <div class="layui-input-inline" >
                    <input type="hidden" id="createId" name="createId" pofield ="createId" >
                    <input type="text" disabled="disabled" id="createName" name="createName" pofield="createName" class="layui-input"   >
                </div>
            </div>
        </div>



        <div class="layui-form-item">
            <div class="layui-input-block" style="text-align: center;">
                <input type="button" id="back" value="返回" onclick="goback();" class="layui-btn layui-btn-normal" />
                <input type="button" id="roleadd" value="保存" onclick="archivingCheckAddOrModify()" class="layui-btn layui-btn-normal"/>
            </div>
        </div>
    </form>


</div>
</body>

</html>
