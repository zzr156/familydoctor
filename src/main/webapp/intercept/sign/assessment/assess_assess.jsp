<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/open/commonjs/tldLayui.jsp" %>
    <link href="css/assessment_look.css?v=1.1.2" rel="stylesheet">
    <title>考核信息表</title>
</head>

<body>
<div id="text"><span></span></div>

<form method="post" class="uploadForm" id="assessForm" enctype="multipart/form-data">
    <table align="center" border="1" cellpadding="0" cellspacing="0" text-align:left; vertical-align:bottom>
        <tr>
            <th>项目名称</th>
            <th>服务内容</th>
            <th>分值</th>
            <th>得分</th>
            <th>上传佐证记录</th>
            <th>考核方式</th>
        </tr>
        <tr>
            <input type="hidden" contentCode="11"/>
            <td>1、基础工作</td>
            <td>签约档案的齐全</td>
            <td>10</td>
            <td>
                <input class="scoreInput" type="text" readonly="readonly"/>
            </td>
            <td>签约对象档案记录</td>
            <td>
                <span>打印后签字拍照或扫描上传</span><br/>
                <input id="fileWorkRecordComplete" class="a-upload" type="file" name="fileWorkRecordComplete" multiple="multiple" accept="image/*" onchange="pictureFilter(this)"/>
            </td>
        </tr>
        <tr>
            <input type="hidden" contentCode="12"/>
            <td></td>
            <td>建立居民电子健康档案</td>
            <td>15</td>
            <td>
                <input class="scoreInput" type="text" readonly="readonly"/>
            </td>
            <td>依托家庭医生签约服务平台完善居民健康档案，并及时更新服务内容</td>
            <td>自动生成</td>
        </tr>
        <tr>
            <input type="hidden" contentCode="13"/>
            <td></td>
            <td>有效沟通1次以上</td>
            <td>5</td>
            <td>
                <input class="scoreInput" type="text" readonly="readonly"/>
            </td>
            <td>电话沟通记录或访视记录（本人或监护人）</td>
            <td>
                <span>打印后签字拍照或扫描上传</span><br/>
                <input id="fileWorkValidTalk" class="a-upload" type="file" name="fileWorkValidTalk" multiple="multiple" accept="image/*,audio/mpeg" onchange="soundFilter(this)"/>
            </td>
        </tr>
        <tr>
            <input type="hidden" contentCode="21"/>
            <td>2、基本医疗</td>
            <td>提供签约对象刷社会保障卡服务记录1次</td>
            <td>15</td>
            <td>
                <input class="scoreInput" type="text" readonly="readonly"/>
            </td>
            <td>1、签约居民预约门诊：为签约居民提供家庭医生预约门诊服务。2、分级转诊服务：根据居民病情需要协调医疗资源，优先为居民联系上级医疗机构门诊、会诊、转诊。3、插卡查阅居民健康档案病历有关信息。</td>
            <td>自动生成</td>
        </tr>
        <tr>
            <input type="hidden" contentCode="31"/>
            <td>3、团队服务</td>
            <td>签约对象提供健康咨询服务。通过线上线下多种沟通渠道，如电话、短信、微信、qq、公众号、app软件等</td>
            <td>5</td>
            <td>
                <input class="scoreInput" type="text" readonly="readonly"/>
            </td>
            <td>健康咨询服务记录</td>
            <td>
                <span>从团队服务账号提取</span></br>
                <input id="fileTeamConsult" class="a-upload" type="file" name="fileTeamConsult" multiple="multiple" accept="image/*" onchange="pictureFilter(this)"/>
            </td>
            </td>
        </tr>
        <tr>
            <input type="hidden" contentCode="32"/>
            <td></td>
            <td>由“家庭医生服务团队”提供每月不少于半天的下定点的（村居）社区主动开展健康教育活动</td>
            <td>10</td>
            <td>
                <input class="scoreInput" type="text" readonly="readonly"/>
            </td>
            <td>每月规定时间上传定点服务记录照片,每次服务记录1分（第三个月开始计分）</td>
            <td>
                <span>上传到服务团队管理</span><br/><br/>
                <div id="activityTimeDiv">
                    <select pofield="activityTime" id="activityTime" class="layui-input">
                        <option value="">---请选择开展活动的时间---</option>
                    </select>
                </div><br/>
                <input id="commAct" class="a-upload" type="file" name="fileTeamCommAct" multiple="multiple" accept="image/*" onchange="pictureFilter(this)"/>
            </td>
        </tr>
        <tr>
            <input type="hidden" contentCode="41"/>
            <td>4、个性化服务</td>
            <td>所有人群：提供年度健康评估报告（包括健康诊疗、宣传、咨询情况等），进行个性化健康指导。(各签约团队根据实际情况细化个性化健康指导内容）</td>
            <td>15</td>
            <td>
                <input class="scoreInput" type="text" readonly="readonly"/>
            </td>
            <td>
                健康评估报告<br><br>
                <input id="resitHealthReportButton" type="button" onclick="resitHealthReport()" class="layui-btn layui-btn-lg" value="补录健康评估报告" />
            </td>
            <td>自动生成</td>
        </tr>
        <tr pofield="health" style="display:none">
            <input type="hidden" contentCode="42"/>
            <td></td>
            <td>健康人群调阅健康档案情况</td>
            <td>15</td>
            <td>
                <input class="scoreInput" type="text" readonly="readonly"/>
            </td>
            <td>
                健康档案调阅情况<br><br>
                <input type="button" onclick="findReadFileLog()" class="layui-btn layui-btn-lg" value="调阅记录" />
            </td>
            <td>自动生成</td>
        </tr>
        <tr pofield="old" style="display:none">
            <input type="hidden" contentCode="43"/>
            <td></td>
            <td>65岁老年人：每年体检一次(身高、体重、腰围和生活方式等)。检查项目包括:血常规、尿常规、空腹血糖、血脂、肝、肾功能、心电图、腹部黑白B超。</td>
            <td>10</td>
            <td>
                <input class="scoreInput" type="text" readonly="readonly"/>
            </td>
            <td>体检报告</td>
            <td>自动生成</td>
        </tr>
        <tr pofield="old" style="display:none">
            <input type="hidden" contentCode="44"/>
            <td></td>
            <td>65岁老年人：为有需要上门服务的签约对象提供约定服务项目1次。(各签约团队根据实际情况制定）</td>
            <td>5</td>
            <td>
                <input class="scoreInput" type="text" readonly="readonly"/>
            </td>
            <td></td>
            <td>
                <span></span><br/>
                <input id="fileOld" class="a-upload" type="file" name="fileOld" multiple="multiple" accept="image/*" onchange="pictureFilter(this)"/>
            </td>
        </tr>
        <tr pofield="pregnant" style="display:none">
            <input type="hidden" contentCode="45"/>
            <td></td>
            <td>孕产妇：建立早孕手册、体检一次(身高、体重、腰围和生活方式等)、产前检查、产后访视等；</td>
            <td>10</td>
            <td>
                <input class="scoreInput" type="text" readonly="readonly"/>
            </td>
            <td>体检报告</td>
            <td>自动生成</td>
        </tr>
        <tr pofield="pregnant" style="display:none">
            <input type="hidden" contentCode="46"/>
            <td></td>
            <td>孕产妇：提供签约对象约定上门健康访视1次</td>
            <td>5</td>
            <td>
                <input class="scoreInput" type="text" readonly="readonly"/>
            </td>
            <td></td>
            <td>
                <span></span><br/>
                <input id="filePregnant" class="a-upload" type="file" name="filePregnant" multiple="multiple" accept="image/*" onchange="pictureFilter(this)"/>
            </td>
        </tr>
        <tr pofield="young" style="display:none">
            <input type="hidden" contentCode="47"/>
            <td></td>
            <td>儿童：新生儿访视，按照《0～6岁儿童保健手册》服务要求进行随访、体检；</td>
            <td>10</td>
            <td>
                <input class="scoreInput" type="text" readonly="readonly"/>
            </td>
            <td>随访记录和体检报告</td>
            <td>自动生成</td>
        </tr>
        <tr pofield="young" style="display:none">
            <input type="hidden" contentCode="48"/>
            <td></td>
            <td>儿童：提供签约对象约定上门健康访视1次。</td>
            <td>5</td>
            <td>
                <input class="scoreInput" type="text" readonly="readonly"/>
            </td>
            <td></td>
            <td>
                <span></span><br/>
                <input id="fileYoung" class="a-upload" type="file" name="fileYoung" multiple="multiple" accept="image/*" onchange="pictureFilter(this)"/>
            </td>
        </tr>
        <tr pofield="chronic" style="display:none">
            <input type="hidden" contentCode="49"/>
            <td></td>
            <td>慢性病患者：高血压患者每年四次随访(每次测血压一次)；糖尿病患者每年四次随访（每次测空腹血糖一次）。</td>
            <td>10</td>
            <td>
                <input class="scoreInput" type="text" readonly="readonly"/>
            </td>
            <td>随访记录和体检报告</td>
            <td>自动生成</td>
        </tr>
        <tr pofield="chronic" style="display:none">
            <input type="hidden" contentCode="410"/>
            <td></td>
            <td>慢性病患者：为高血压、糖尿病等慢性病签约患者提供上门随访服务。(各签约团队根据实际情况制定）</td>
            <td>5</td>
            <td>
                <input class="scoreInput" type="text" readonly="readonly"/>
            </td>
            <td></td>
            <td>
                <span></span><br/>
                <input id="fileChronic" class="a-upload" type="file" name="fileChronic" multiple="multiple" accept="image/*" onchange="pictureFilter(this)"/>
            </td>
        </tr>
        <tr pofield="psychosis" style="display:none">
            <input type="hidden" contentCode="411"/>
            <td></td>
            <td>结核病、严重精神障碍患者：提供随访评估报告。</td>
            <td>10</td>
            <td>
                <input class="scoreInput" type="text" readonly="readonly"/>
            </td>
            <td>随访评估报告</td>
            <td>
                <span></span><br/>
                <input id="filePsychosis" class="a-upload" type="file" name="filePsychosis" multiple="multiple" accept="image/*" onchange="pictureFilter(this)"/>
            </td>
        </tr>
        <tr pofield="psychosis" style="display:none">
            <input type="hidden" contentCode="412"/>
            <td></td>
            <td>结核病、严重精神障碍患者：上传签约年度服务记录。</td>
            <td>5</td>
            <td>
                <input class="scoreInput" type="text" readonly="readonly"/>
            </td>
            <td>服务记录</td>
            <td>
                <span></span><br/>
                <input id="filePsychosisRecord" class="a-upload" type="file" name="filePsychosisRecord" multiple="multiple" accept="image/*" onchange="pictureFilter(this)"/>
            </td>
        </tr>
        <tr>
            <input type="hidden" contentCode="51"/>
            <td>5、满意度测评</td>
            <td>1、签约对象通过手机用户端APP评价2、通过刷社会保障卡运用家签系统评价设备提供评价</td>
            <td>10</td>
            <td>
                <input class="scoreInput" type="text" readonly="readonly"/>
            </td>
            <td>APP端用户评价报告</td>
            <td>自动生成</td>
        </tr>
        <tr>
            <td>得分</td>
            <td colspan="2"></td>
            <td pofield="totalScorePre" style="border-left:white solid 1px; border-right:white solid 1px;"></td>
            <td colspan="2"></td>
        </tr>
    </table>
</form>

<div style="margin-top: 20px;font-size:20px">考核操作记录</div>
<table id="assessLog" border="1" style="text-align:center; vertical-align:bottom; width: 100%"></table>

<button class="layui-btn layui-btn-lg layui-btn-danger finishBtn" operate="finish" id="finishBtn" onclick="finish(this)">标记为考核完成</button>
<button class="layui-btn layui-btn-lg layui-btn-danger saveBtn" operate="normal" id="saveBtn" onclick="save(this)">保存</button>

</body>
<script type="text/javascript" src="js/assess_assess.js?v=1.2.1"></script>
<script type="text/javascript" src="js/jquery.form.js?v=1.1.1"></script>
</html>
