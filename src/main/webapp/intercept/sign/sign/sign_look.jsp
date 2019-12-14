<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/open/commonjs/tldLayui.jsp"%>
    <title>签约界面</title>
</head>
<body bgcolor="white">
<!-- 新增 -->
<div style="padding:0 10px;">
<form class="layui-form-pane" id="form_vo">
    <blockquote class="layui-elem-quote" style="text-align: center;font-size: 20px" >签约信息</blockquote>
    <input type="hidden" id="hospId"   pofield="hospId" />
    <input type="hidden" id="patientId"   pofield="patientId" />
    <input type="hidden" id="teamId"   pofield="teamId" />
    <div class="layui-form-item">
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 6px;">
            <legend style="font-size:20px;" id="jiafan" >甲方</legend>
        </fieldset>
        <div class="layui-inline">
            <label class="layui-form-label"><b>* </b>姓名</label>
            <div class="layui-input-block">
                <input type="text"  pofield="patientName" id="patientName" class="layui-input" validator='{"msg":"姓名不能为空!"}'/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label"><b>* </b>性别</label>
            <div class="layui-input-block" id="patientGender" >
                <input type="radio" name="patientGender" value="1" title="男" style="width:25px;height:25px;" pofield="patientGender"> 男
                <input type="radio" name="patientGender" value="2" title="女" style="width:25px;height:25px;" pofield="patientGender">  女
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label"><b>* </b>年龄</label>
            <div class="layui-input-block">
                <input style="width: 80px;" type="text" id="patientAge" pofield="patientAge" class="layui-input" validator='{"msg":"年龄不能为空!"}'/>

            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label"><b>* </b>身份证</label>
            <div class="layui-input-block">
                <input style="width: 180px;" type="text"  pofield="patientIdno" id="patientIdno" class="layui-input" validator='{"msg":"身份证不能为空!","rule":"idCard"}'/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label"><b>* </b>社保卡</label>
            <div class="layui-input-block">
                <input style="width: 180px;" type="text" pofield="patientCard" id="patientCard" class="layui-input" validator='{"msg":"社保卡号不能为空!"}'/>
            </div>
        </div>
        <div id="familydaId">
        <div class="layui-inline">
            <label class="layui-form-label">家庭档案</label>
            <div class="layui-input-block">
                <input type="text" id="patientjtda" name="patientjtda" pofield="patientjtda" class="layui-input"/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">居民档案</label>
            <div class="layui-input-block">
                <input type="text" id="patientjmda" name="patientjmda" pofield="patientjmda" class="layui-input"/>
            </div>
        </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label"><b>* </b>联系方式</label>
            <div class="layui-input-block">
                <input type="text" id="patientTel" name="patientTel" pofield="patientTel" class="layui-input" validator='{"msg":"联系方式不能为空!","rule":"phone"}'/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label"><b>* </b>户籍所在地</label>
            <div class="layui-input-inline" id="cityid">
                <input type="text" id="patientCityname" pofield="patientCityname" class="layui-input">
            </div>
        </div>
        <div class="layui-inline">
            <div class="layui-input-inline" id="county">
                <input type="text" id="patientAreaname" pofield="patientAreaname" class="layui-input">
            </div>
        </div>
        <div class="layui-inline">
            <div class="layui-input-inline" id="street">
                <input type="text" id="patientStreetname" pofield="patientStreetname" class="layui-input">
            </div>
        </div>
        <div class="layui-inline">
            <div class="layui-input-inline" id="committee">
                <input type="text" id="patientNeighborhoodCommitteename" pofield="patientNeighborhoodCommitteename" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item layui-form-text">
            <label class="layui-form-label"><b>* </b>现住地址</label>
            <div class="layui-input-block">
                <textarea id="patientAddress" pofield="patientAddress" placeholder="请输入内容" class="layui-textarea" validator='{"msg":"现住地址不能为空!"}'></textarea>
            </div>
        </div>

        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 6px;">
            <legend style="font-size:20px;" id="yifan" >乙方</legend>
        </fieldset>

        <div class="layui-inline">
            <label class="layui-form-label"><b>* </b>签约机构</label>
            <div class="layui-input-block">
                <input type="text" id="hospName" name="hospName" pofield="hospName"   class="layui-input" placeholder="当前机构" size="30">
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label"><b>* </b>团队</label>
            <div class="layui-input-inline" >
                <input type="text" id="signTeamName" name="signTeamName" pofield="signTeamName"  class="layui-input" >
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label"><b>* </b>家庭医生</label>
            <div class="layui-input-inline" >
                <input type="text" id="drName" name="drName" pofield="drName" class="layui-input" >
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label"><b>* </b>医生电话</label>
            <div class="layui-input-block">
                <input type="text"  pofield="drTel" id="drTel" class="layui-input" />
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">家庭医生助理</label>
            <div class="layui-input-inline" >
                <input type="text"   id="signDrAssistantName" pofield="signDrAssistantName"  class="layui-input">
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">家庭医生助理电话</label>
            <div class="layui-input-block">
           <input type="text"   id="signDrAssistantTel" pofield="signDrAssistantTel"  class="layui-input" >
            </div>
        </div>
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 6px;">
            <legend style="font-size:20px;"><b>*</b>服务人群类型</legend>
        </fieldset>
        <div class="layui-form-item">
            <div class="layui-input-block"  >
                <input type="checkbox" name="persGroup" value="1"  title="普通服务" style="width:20px;height:20px;" validator='{"check":"请选择服务类型!"}' pofield="persGroup">&nbsp;&nbsp;普通服务&nbsp;&nbsp;
                <span onclick="clickP()"><input type="checkbox" id="oldman" name="persGroup" value="4"  title="老年人服务"style="width:20px;height:20px;"  validator='{"check":"请选择服务类型!"}' pofield="persGroup">&nbsp;&nbsp;老年人服务</span>&nbsp;&nbsp;
                <input type="checkbox" name="persGroup" value="5"  title="高血压" style="width:20px;height:20px;" validator='{"check":"请选择服务类型!"}' pofield="persGroup">&nbsp;&nbsp;高血压&nbsp;&nbsp;
                <input type="checkbox" name="persGroup" value="6" title="糖尿病" style="width:20px;height:20px;" validator='{"check":"请选择服务类型!"}' pofield="persGroup">&nbsp;&nbsp;糖尿病&nbsp;&nbsp;
                <span onclick="clickP()"><input type="checkbox" id="young"name="persGroup" value="2" title="儿童（0-6岁）"style="width:20px;height:20px;" validator='{"check":"请选择服务类型!"}' pofield="persGroup">&nbsp;&nbsp;儿童（0-6岁）</span>
                <span onclick="clickP()"><input type="checkbox" name="persGroup" id="yuncf" value="3"  title="孕产妇"style="width:20px;height:20px;" validator='{"check":"请选择服务类型!"}' pofield="persGroup"></span>&nbsp;&nbsp;孕产妇&nbsp;&nbsp;
                <input type="checkbox" name="persGroup" value="8"  title="结核病" style="width:20px;height:20px;" validator='{"check":"请选择服务类型!"}' pofield="persGroup">&nbsp;&nbsp;结核病&nbsp;&nbsp;
                <input type="checkbox" name="persGroup" value="7"  title="严重精神障碍患者"style="width:20px;height:20px;" validator='{"check":"请选择服务类型!"}' pofield="persGroup">&nbsp;&nbsp;严重精神障碍患者&nbsp;&nbsp;
                <input type="checkbox" name="persGroup" value="9"  title="残疾人"style="width:20px;height:20px;" validator='{"check":"请选择服务类型!"}' pofield="persGroup">&nbsp;&nbsp;残疾人
                <input type="checkbox" name="persGroup" value="10"  title="脑血管病患者"style="width:20px;height:20px;" validator='{"check":"请选择服务类型!"}' pofield="persGroup">&nbsp;&nbsp;脑血管病患者
                <input type="checkbox" name="persGroup" value="11"  title="冠心病患者"style="width:20px;height:20px;" validator='{"check":"请选择服务类型!"}' pofield="persGroup">&nbsp;&nbsp;冠心病患者
                <input type="checkbox" name="persGroup" value="12"  title="癌症患者"style="width:20px;height:20px;" validator='{"check":"请选择服务类型!"}' pofield="persGroup">&nbsp;&nbsp;癌症患者
                <input type="checkbox" name="persGroup" value="99"  title="其他"style="width:20px;height:20px;" validator='{"check":"请选择服务类型!"}' pofield="persGroup">&nbsp;&nbsp;其他
            </div>
        </div>
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 6px;">
            <legend style="font-size:20px;"><b>*</b>人口经济性质</legend>
        </fieldset>
        <div class="layui-form-item">
            <div class="layui-input-block" id="signsJjType" >
                <input type="checkbox" name="sJjType" value="1" title="一般人口" style="width:20px;height:20px;" pofield="sJjType">&nbsp;&nbsp;一般人口&nbsp;&nbsp;
                <input type="checkbox" name="sJjType" value="2" title="建档立卡贫困人口" style="width:20px;height:20px;" pofield="sJjType">&nbsp;&nbsp;建档立卡贫困人口&nbsp;&nbsp;
                <input type="checkbox" name="sJjType" value="3" title="低保户"style="width:20px;height:20px;" pofield="sJjType">&nbsp;&nbsp;低保户&nbsp;&nbsp;
                <input type="checkbox" name="sJjType" value="4" title="特困户（五保户）" style="width:20px;height:20px;" pofield="sJjType">&nbsp;&nbsp;特困户（五保户）&nbsp;&nbsp;
                <input type="checkbox" name="sJjType" value="5" title="计生独伤残家庭"style="width:20px;height:20px;"  pofield="sJjType">&nbsp;&nbsp;计生独伤残家庭&nbsp;&nbsp;
                <input type="checkbox" name="sJjType" value="7" title="计生独子女户"style="width:20px;height:20px;"  pofield="sJjType">&nbsp;&nbsp;计生独子女户&nbsp;&nbsp;
                <input type="checkbox" name="sJjType" value="8" title="计生双女户"style="width:20px;height:20px;"  pofield="sJjType">&nbsp;&nbsp;计生双女户&nbsp;&nbsp;
                <input type="checkbox" name="sJjType" value="9" title="因病致贫"style="width:20px;height:20px;"  pofield="sJjType">&nbsp;&nbsp;因病致贫&nbsp;&nbsp;
                <input type="checkbox" name="sJjType" value=10" title="其他"style="width:20px;height:20px;"  pofield="sJjType">&nbsp;&nbsp;其他&nbsp;&nbsp;
            </div>
        </div>
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 6px;">
            <legend style="font-size:20px;"><b>*</b>签约期限</legend>
        </fieldset>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label"><b>*</b>协议开始</label>
                <div class="layui-input-inline" style="width: 200px;">
                    <input type="text" id="signFromDate" name="signFromDate" pofield="signFromDate" onchange="wdate()" class="layui-input" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" validator='{"msg":"签约时间不能为空!"}' >
                </div>
                <div class="layui-form-mid">起 &nbsp; &nbsp;-</div>
                <div class="layui-input-inline" style="width: 200px;">
                    <input  type="text" id="signToDate" name="signToDate" pofield="signToDate" class="layui-input" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" validator='{"msg":"签约时间不能为空!"}'>
                </div>
                <div class="layui-form-mid">止</div>
            </div>
        </div>
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 6px;">
            <legend style="font-size:20px;"><b>*</b>签约费</legend>
        </fieldset>
        <div class="layui-form-item">
            <div id="bt">

            </div>
            <label class="layui-form-label">自费(必填)</label>
            <div class="layui-input-inline" style="width: 100px;" id="sccc">
                <input type="text" style="background-color: yellow" onblur="signPay();" pofield="signzfpay" id="signzfpay" name="signzfpay" lay-verify="email" autocomplete="off" class="layui-input">
            </div>
            <div class="layui-input-inline">&nbsp;&nbsp;
                <input type="checkbox" disabled="disabled" id="payStatePrivate" name="payStatePrivate" title="是否全自费" >&nbsp;&nbsp;是否全自费
            </div>
        </div>
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 6px;">
            <legend style="font-size:20px;"><b>*</b>协议</legend>
        </fieldset>


        <div class="layui-inline">
            <label class="layui-form-label">协议服务项</label>
            <div class="layui-input-block" id="signpackageid">

            </div>
        </div>
        <div class="layui-form-item layui-form-text">
            <label class="layui-form-label">补充协议内容</label>
            <div class="layui-input-block ">
                <textarea id="signtext" name="signtext" pofield="signtext" placeholder="请输入补充协议内容" class="layui-textarea"></textarea>
            </div>
        </div>
        <div id="familyybId">
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 6px;">
            <legend style="font-size:20px;"><b>*</b>医保类型</legend>
        </fieldset>
        <div class="layui-inline">
            <div class="layui-input-block" id="singlx">
                <input type="radio"  value="0" disabled="disabled" title="省医保" style="width:25px;height:25px;"> &nbsp;&nbsp;省医保&nbsp;&nbsp;
                <input type="radio" name="signlx" value="1" title="市医保" style="width:25px;height:25px;" pofield="signlx" >&nbsp;&nbsp;市医保&nbsp;&nbsp;
                <input type="radio" name="signlx" value="2" title="新农合" style="width:25px;height:25px;" pofield="signlx">&nbsp;&nbsp;新农合&nbsp;&nbsp;
                <input type="radio" name="signlx" value="3" title="未参保" style="width:25px;height:25px;" pofield="signlx">&nbsp;&nbsp;未参保
            </div>
        </div>
        </div>

    <div class="layui-form-item">
        <div class="layui-input-block"  style="text-align: center;">
            <input type="button" id="roleadd" value="返回" onclick="goto()" class="layui-btn layui-btn-normal"/>
            <input type="button" id="" value="查看协议" onclick="lookprotocol()" class="layui-btn layui-btn-normal"/>
        </div>
    </div>

    </div>
</form>


</div>
</body>
<script type="text/javascript" src="js/sign_look.js?v=1.0"></script>
</html>
