<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.text.SimpleDateFormat" %>
<html>
<head>
    <%@ include file="/open/commonjs/tldLayui.jsp"%>
    <script type="text/javascript" src="js/sign_renew_look.js?v=1.0"></script>
    <title>已续签信息</title>
</head>
<body bgcolor="white">
<%--查询条件--%>
<div id="form_qvo">
    <input type="hidden" id="signrenew" pofield="signrenew" value="1">
    <div class="mwrap" id="queryForm">
        <div class="layui-form-item">
            <label class="layui-form-label">姓名</label>
            <div class="layui-input-inline">
                <input type="text"  pofield="patientName"id="patientName" class="layui-input">
            </div>
            <label class="layui-form-label">身份证</label>
            <div class="layui-input-inline">
                <input type="text"  pofield="patientIdno" id="patientIdno" class="layui-input">
            </div>
            <div id="familyId">
                <label class="layui-form-label">居民健康档案</label>
                <div class="layui-input-inline">
                    <input type="text" pofield="patientjmda" id="patientjmda" class="layui-input">
                </div>
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">自费金额</label>
            <div class="layui-input-inline">
                <input type="text"  pofield="signzfpay" id="signzfpay" class="layui-input">
            </div>
            <label class="layui-form-label">续签日期</label>
            <div class="layui-input-inline">
                <input type="text" pofield="signDateStart" id="signDateStart" autocomplete="off" class="layui-input" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:45%;display:inline;"/>
                ~
                <input type="text" pofield="signDateEnd" id="signDateEnd" autocomplete="off" class="layui-input" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:45%;display:inline;"/>
            </div>
            <label class="layui-form-label">医保类型</label>
            <div class="layui-input-inline">
                <select id="signlx" pofield="signlx" class="layui-input">
                    <option value="">全部</option>
                    <option value="1">医保</option>
                    <option value="2">农合</option>
                </select>
            </div>
        </div>
        <div id="pts_" style="display: none">
            <div class="layui-form-item">
                <label class="layui-form-label">协议日期</label>
                <div class="layui-input-inline">
                    <input type="text" pofield="signFromDateStart" id="signFromDateStart" autocomplete="off" class="layui-input" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:45%;display:inline;"/>
                    ~
                    <input type="text" pofield="signFromDateEnd" id="signFromDateEnd" autocomplete="off" class="layui-input" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:45%;display:inline;"/>
                </div>
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">人口经济性质</label>
            <div class="layui-input-block">
                <input type="checkbox" name="signsJjTypes" value="1" title="一般人口" style="width:20px;height:20px;" pofield="signsJjTypes">&nbsp;&nbsp;一般人口&nbsp;&nbsp;
                <input type="checkbox" name="signsJjTypes" value="2" title="建档立卡贫困人口" style="width:20px;height:20px;" pofield="signsJjTypes">&nbsp;&nbsp;建档立卡贫困人口&nbsp;&nbsp;
                <input type="checkbox" name="signsJjTypes" value="3" title="低保户"style="width:20px;height:20px;" pofield="signsJjTypes">&nbsp;&nbsp;低保户&nbsp;&nbsp;
                <input type="checkbox" name="signsJjTypes" value="4" title="特困户（五保户）" style="width:20px;height:20px;" pofield="signsJjTypes">&nbsp;&nbsp;特困户（五保户）&nbsp;&nbsp;
                <input type="checkbox" name="signsJjTypes" value="5" title="计生独伤残家庭" style="width:20px;height:20px;"  pofield="signsJjTypes">&nbsp;&nbsp;计生独伤残家庭&nbsp;&nbsp;

                <input type="checkbox" name="signsJjTypes" value="7" title="计生独子女户" style="width:20px;height:20px;"  pofield="signsJjTypes">&nbsp;&nbsp;计生独子女户&nbsp;&nbsp;
                <input type="checkbox" name="signsJjTypes" value="8" title="计生双女户" style="width:20px;height:20px;"  pofield="signsJjTypes">&nbsp;&nbsp;计生双女户&nbsp;&nbsp;
                <input type="checkbox" name="signsJjTypes" value="9" title="因病致贫户" style="width:20px;height:20px;"  pofield="signsJjTypes">&nbsp;&nbsp;因病致贫户&nbsp;&nbsp;
                <input type="checkbox" name="signsJjTypes" value="10" title="其他" style="width:20px;height:20px;"  pofield="signsJjTypes">&nbsp;&nbsp;其他&nbsp;&nbsp;
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">服务人群类型</label>
            <div class="layui-input-block">
                <input type="checkbox" name="persGroup" value="1"  title="普通服务" style="width:20px;height:20px;"  pofield="persGroup">&nbsp;&nbsp;普通服务&nbsp;&nbsp;
                <input type="checkbox" name="persGroup" value="4"  title="老年人服务"style="width:20px;height:20px;"   pofield="persGroup">&nbsp;&nbsp;老年人服务&nbsp;&nbsp;
                <input type="checkbox" name="persGroup" value="5"  title="高血压" style="width:20px;height:20px;"  pofield="persGroup">&nbsp;&nbsp;高血压&nbsp;&nbsp;
                <input type="checkbox" name="persGroup" value="6" title="糖尿病" style="width:20px;height:20px;" pofield="persGroup">&nbsp;&nbsp;糖尿病&nbsp;&nbsp;
                <input type="checkbox" name="persGroup" value="2" title="儿童（0-6岁）"style="width:20px;height:20px;" pofield="persGroup">&nbsp;&nbsp;儿童（0-6岁）
                <input type="checkbox" name="persGroup" value="3"  title="孕产妇"style="width:20px;height:20px;"  pofield="persGroup">&nbsp;&nbsp;孕产妇&nbsp;&nbsp;
                <input type="checkbox" name="persGroup" value="8"  title="结核病" style="width:20px;height:20px;" pofield="persGroup">&nbsp;&nbsp;结核病&nbsp;&nbsp;
                <input type="checkbox" name="persGroup" value="7"  title="严重精神障碍患者"style="width:20px;height:20px;" pofield="persGroup">&nbsp;&nbsp;严重精神障碍患者&nbsp;&nbsp;
                <input type="checkbox" name="persGroup" value="9"  title="残疾人"style="width:20px;height:20px;" pofield="persGroup">&nbsp;&nbsp;残疾人

                <input type="checkbox" name="persGroup" value="10"  title="脑血管病患者"style="width:20px;height:20px;" pofield="persGroup">&nbsp;&nbsp;脑血管病患者
                <input type="checkbox" name="persGroup" value="11"  title="冠心病患者"style="width:20px;height:20px;" pofield="persGroup">&nbsp;&nbsp;冠心病患者
                <input type="checkbox" name="persGroup" value="12"  title="癌症患者"style="width:20px;height:20px;" pofield="persGroup">&nbsp;&nbsp;癌症患者
                <input type="checkbox" name="persGroup" value="99"  title="其他"style="width:20px;height:20px;" pofield="persGroup">&nbsp;&nbsp;其他
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">团队名称</label>
            <div class="layui-input-inline">
                <select id="teamId" pofield="teamId" class="layui-input" onchange="addDr()">
                </select>
            </div>
            <label class="layui-form-label">签约医生</label>
            <div class="layui-input-inline">
                <select id="drId" pofield="drId" class="layui-input">
                </select>
            </div>
            <label class="layui-form-label">助理姓名</label>
            <div class="layui-input-inline">
                <select id="signDrAssistantId" pofield="signDrAssistantId" class="layui-input">
                </select>
            </div>
        </div>
        <%-- <div class="layui-form-item">

                 <label class="layui-form-label">市级</label>
                 <div class="layui-input-inline" id="cityid">
                     <select id="patientCity" name="patientCity" pofield="patientCity"  class="layui-input" onchange="changecounty()"validator='{"msg":"现住地址不能为空!"}' >
                         <option value="">--请选择市--</option>
                     </select>
                 </div>


                 <label class="layui-form-label">区县级</label>
                 <div class="layui-input-inline" id="county">
                     <select id="patientArea" pofield="patientArea"  class="layui-input" onchange="changestreet()"validator='{"msg":"现住地址不能为空!"}'>
                         <option value="">--请选择区--</option>
                     </select>
                 </div>


                 <label class="layui-form-label">街道乡镇</label>
                 <div class="layui-input-inline" id="street">
                     <select id="patientStreet" pofield="patientStreet" class="layui-input" onchange="changeCommittee()" validator='{"msg":"现住地址不能为空!"}'>
                         <option value="">--请选择街道--</option>
                     </select>
                 </div>

         </div>--%>
        <div class="layui-form-item">

            <%--<label class="layui-form-label">居委会</label>
                <div class="layui-input-inline" id="committee">
                    <select id="patientNeighborhoodCommittee" pofield="patientNeighborhoodCommittee" class="layui-input" validator='{"msg":"现住地址不能为空!"}'>
                        <option value="">--请选择居委会--</option>
                    </select>
                </div>--%>
            <label class="layui-form-label">详细地址</label>
            <div class="layui-input-inline">
                <input type="text"  pofield="patientAddress"id="patientAddress" class="layui-input">
            </div>
            <label class="layui-form-label">操作人</label>
            <div class="layui-input-inline">
                <input type="text"  pofield="batchOperatorName"id="batchOperatorName" class="layui-input">
            </div>
            <div id="pt_" style="display: none">
                <label class="layui-form-label">交互状态</label>
                <div class="layui-input-inline">
                    <select id="signState" pofield="signState" class="layui-input">
                        <option value="">--请选择--</option>
                        <option value="0">未交互</option>
                        <option value="2">已交互</option>
                    </select>
                </div>
            </div>
        </div>
    </div>
    <blockquote class="layui-elem-quote" style="text-align: center" id="blockId">
        <input type="hidden" pofield="signHospId" id="signHospId"/>
        <button onclick="findList()" class="layui-btn layui-btn-small">
            <i class=layui-icon>&#xe615;</i>查询
        </button>
        <button onclick="signsxcz()" class="layui-btn layui-btn-primary">
            <i class=layui-icon>&#xe60e;</i>重置
        </button>

    </blockquote>
</div>
<%--签约列表--%>
<table id="newSignTabel" lay-filter="sign_look">
</table>

</body>


<script type="text/html" id="newBarRole">
    <a class="layui-btn layui-btn-mini " lay-event="look" ><i class=layui-icon>&#xe615;</i>查看</a>
</script>
</html>
