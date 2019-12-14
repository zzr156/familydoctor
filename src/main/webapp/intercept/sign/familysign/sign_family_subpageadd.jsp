<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/open/commonjs/tldLayui.jsp" %>
    <title>新增家庭成员</title>
</head>
<body bgcolor="white">
<!-- 新增 -->
<div style="padding:0 10px;">
    <form class="layui-form-pane" id="form_vo">
        <blockquote class="layui-elem-quote" style="text-align: center;font-size: 20px">家庭成员信息</blockquote>

        <input type="hidden" id="idx" pofield="idx"/>
        <input type="hidden" id="nowtime" value="<%=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date())%>"/>

        <div class="layui-form-item">
            <div class="layui-inline ">
                <label class="layui-form-label"><b>* </b>姓名</label>
                <div class="layui-input-block">
                    <input type="text" pofield="patientName" id="patientName" class="layui-input"
                           validator='{"msg":"姓名不能为空!"}'/>
                </div>
            </div>

            <div class="layui-inline">
                <label class="layui-form-label"><b>* </b>年龄</label>
                <div class="layui-input-block">
                    <input type="text" id="patientAge" pofield="patientAge" class="layui-input"
                           validator='{"msg":"年龄不能为空!"}'/>
                </div>
            </div>

            <div class="layui-inline">
                <label class="layui-form-label"><b>* </b>性别</label>
                <div class="layui-input-block" id="patientGender">
                    &nbsp;
                    <input type="radio" name="patientGender" value="1" title="男" style="width:25px;height:25px;"
                           pofield="patientGender"> 男
                    <input type="radio" name="patientGender" value="2" title="女" style="width:25px;height:25px;"
                           pofield="patientGender"> 女
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
                <label class="layui-form-label"><b></b>社保卡</label>
                <div class="layui-input-block">
                    <input type="text" pofield="patientCard" id="patientCard" class="layui-input"/>
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
                <label class="layui-form-label"><b>* </b>关系</label>
                <div class="layui-input-block">
                    <select id="mfFmNickName" name="mfFmNickName" pofield="mfFmNickName" class="layui-input"
                            style="width: 165px" validator='{"msg":"关系不能为空!"}'>
                    </select>
                </div>
            </div>

            <div class="layui-inline">
                <label class="layui-form-label"><b></b>居民档案</label>
                <div class="layui-input-block">
                    <input type="text" id="patientjmda" name="patientjmda" pofield="patientjmda" class="layui-input"/>
                </div>
            </div>

            <div class="layui-inline">
                <label class="layui-form-label">家庭档案</label>
                <div class="layui-input-block">
                    <input type="text" id="patientjtda" name="patientjtda" pofield="patientjtda" class="layui-input"/>
                </div>
            </div>

            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 6px;">
                <legend style="font-size:20px;"><b>*</b>服务类型</legend>
            </fieldset>

            <div class="layui-form-item">
                <div class="layui-input-block" id="signPersGroup" style="margin-left: 0;">
                    <span onclick="clickP(this)">
                        <input type="checkbox" id="ordinary" name="persGroup" value="1"
                            title="普通服务" style="width:20px;height:20px;"
                            validator='{"check":"请选择服务类型!"}' pofield="persGroup">&nbsp;&nbsp;普通服务
                    </span>&nbsp;&nbsp;
                    <span onclick="clickP(this)">
                        <input type="checkbox" id="oldman" name="persGroup" value="4"
                            title="老年人服务" style="width:20px;height:20px;"
                            validator='{"check":"请选择服务类型!"}' pofield="persGroup">&nbsp;&nbsp;老年人服务
                    </span>&nbsp;&nbsp;
                    <span onclick="clickP(this)">
                        <input type="checkbox" id="gxy" name="persGroup" value="5" title="高血压"
                            style="width:20px;height:20px;" validator='{"check":"请选择服务类型!"}'
                            pofield="persGroup">&nbsp;&nbsp;高血压
                    </span>&nbsp;&nbsp;
                    <span onclick="clickP(this)">
                        <input type="checkbox" id="tnb" name="persGroup" value="6" title="糖尿病"
                            style="width:20px;height:20px;" validator='{"check":"请选择服务类型!"}'
                            pofield="persGroup">&nbsp;&nbsp;糖尿病
                    </span>&nbsp;&nbsp;
                    <span onclick="clickP(this)">
                        <input type="checkbox" id="young" name="persGroup" value="2"
                            title="儿童（0-6岁）" style="width:20px;height:20px;"
                            validator='{"check":"请选择服务类型!"}' pofield="persGroup">&nbsp;&nbsp;儿童（0-6岁）
                    </span>
                    <span onclick="clickP(this)">
                        <input type="checkbox" id="yuncf" name="persGroup" value="3"
                            title="孕产妇" style="width:20px;height:20px;"
                            validator='{"check":"请选择服务类型!"}' pofield="persGroup">&nbsp;&nbsp;孕产妇
                    </span>&nbsp;&nbsp;
                    <span onclick="clickP(this)">
                        <input type="checkbox" id="jhb" name="persGroup" value="8" title="结核病"
                            style="width:20px;height:20px;" validator='{"check":"请选择服务类型!"}'
                            pofield="persGroup">&nbsp;&nbsp;结核病
                    </span>&nbsp;&nbsp;
                    <span onclick="clickP(this)">
                        <input type="checkbox" id="jsb" name="persGroup" value="7"
                            title="严重精神障碍患者" style="width:20px;height:20px;"
                            validator='{"check":"请选择服务类型!"}' pofield="persGroup">&nbsp;&nbsp;严重精神障碍患者
                    </span>&nbsp;&nbsp;
                    <span onclick="clickP(this)">
                        <input type="checkbox" id="cjr" name="persGroup" value="9" title="残疾人"
                            style="width:20px;height:20px;" validator='{"check":"请选择服务类型!"}'
                            pofield="persGroup">&nbsp;&nbsp;残疾人
                    </span>
                </div>
            </div>

            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 6px;">
                <legend style="font-size:20px;"><b>*</b>签约费</legend>
            </fieldset>

            <div class="layui-form-item">
                <div id="bt"></div>

                <label class="layui-form-label">自费</label>
                <div class="layui-input-inline" style="width: 100px;" id="sccc">
                    <input type="text" style="background-color: yellow" validator='{"msg":"请输入自费金额!"}'
                           pofield="signzfpay" id="signzfpay" name="signzfpay" lay-verify="email" autocomplete="off"
                           class="layui-input">
                </div>

                <div class="layui-input-inline">&nbsp;&nbsp;
                    <%--<input type="checkbox" disabled="disabled" id="payStatePrivate" name="payStatePrivate" title="是否全自费" >&nbsp;&nbsp;是否全自费--%>
                </div>
            </div>

            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 6px;">
                <legend style="font-size:20px;"><b>*</b>协议</legend>
            </fieldset>

            <div class="layui-form-item">
                <label class="layui-form-label">服务套餐</label>
                <div class="layui-input-block" id="signpackageid">&nbsp;</div>
            </div>

            <div class="layui-form-item layui-form-text">
                <label class="layui-form-label">补充协议内容</label>
                <div class="layui-input-block ">
                    <textarea id="signtext" name="signtext" pofield="signtext" placeholder="请输入补充协议内容"
                              class="layui-textarea"></textarea>
                </div>
            </div>

            <div class="layui-form-item">
                <div class="layui-input-block" style="text-align: center;">
                    <a class="layui-btn layui-btn-danger " id="roleadd" onclick="memberadd()">
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
</body>
<script type="text/javascript" src="js/sign_family_subpageadd.js?v=1.2"></script>
</html>
