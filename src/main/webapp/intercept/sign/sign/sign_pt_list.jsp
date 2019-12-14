<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2017/11/7
  Time: 13:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/open/commonjs/tldLayui.jsp"%>
    <title>莆田签约筛选</title>
</head>
<body bgcolor="white">
<%--查询条件--%>
<div id="form_qvo">
    <div class="mwrap" id="queryForm"><br>
        <input id="ptorg" pofield="ptorg" type="hidden" >
        <div class="layui-form-item">
            <label class="layui-form-label">姓 名</label>
            <div class="layui-input-inline">
                <input type="text"  pofield="ptname" id="ptname" class="layui-input">
            </div>
            <label class="layui-form-label">身份证</label>
            <div class="layui-input-inline">
                <input type="text"  pofield="ptidno" id="ptidno" class="layui-input">
            </div>
            <label class="layui-form-label">参保类型</label>
            <div class="layui-input-inline">
                <select pofield="ptlx" id="ptlx" class="layui-input" onchange="signlx()">
                    <option value="">全部</option>
                    <option value="1">医保(职工)</option>
                    <option value="2">医保(城镇)</option>
                    <option value="3">新农合</option>
                </select>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline" style="display:none;" id="zgyb">
                <label class="layui-form-label">人口性质</label>
                <div class="layui-input-block " >
                    <input type="checkbox" name="ptnature" lay-skin="primary" pofield="ptnature" value="军队离职" title="军队离职" style="width:20px;height:20px;">军队离职
                    <input type="checkbox" name="ptnature" lay-skin="primary" pofield="ptnature" value="在职人员" title="在职人员" style="width:20px;height:20px;">在职人员
                    <input type="checkbox" name="ptnature" lay-skin="primary" pofield="ptnature" value="512人员" title="512人员" style="width:20px;height:20px;">512人员
                    <input type="checkbox" name="ptnature" lay-skin="primary" pofield="ptnature" value="在职公务员" title="在职公务员" style="width:20px;height:20px;">在职公务员
                    <input type="checkbox" name="ptnature" lay-skin="primary" pofield="ptnature" value="退休人员" title="退休人员" style="width:20px;height:20px;">退休人员
                    <input type="checkbox" name="ptnature" lay-skin="primary" pofield="ptnature" value="退休公务员" title="退休公务员" style="width:20px;height:20px;">退休公务员
                    <input type="checkbox" name="ptnature" lay-skin="primary" pofield="ptnature" value="离休" title="离休" style="width:20px;height:20px;">离休
                    <input type="checkbox" name="ptnature" lay-skin="primary" pofield="ptnature" value="保健对象在职" title="保健对象在职" style="width:20px;height:20px;">保健对象在职
                    <input type="checkbox" name="ptnature" lay-skin="primary" pofield="ptnature" value="军队退休" title="军队退休" style="width:20px;height:20px;">军队退休
                    <input type="checkbox" name="ptnature" lay-skin="primary" pofield="ptnature" value="保健对象退休" title="保健对象退休" style="width:20px;height:20px;">保健对象退休
                    <input type="checkbox" name="ptnature" lay-skin="primary" pofield="ptnature" value="二乙军人" title="二乙军人" style="width:20px;height:20px;">二乙军人
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline" style="display:none;" id="czyb">
                <label class="layui-form-label">人口性质</label>
                <div class="layui-input-block " >
                    <input type="checkbox" name="ptnature" lay-skin="primary" pofield="ptnature" value="成年人" title="成年人" style="width:20px;height:20px;">成年人
                    <input type="checkbox" name="ptnature" lay-skin="primary" pofield="ptnature" value="成年低保" title="成年低保" style="width:20px;height:20px;">成年低保
                    <input type="checkbox" name="ptnature" lay-skin="primary" pofield="ptnature" value="成年重残" title="成年重残" style="width:20px;height:20px;">成年重残
                    <input type="checkbox" name="ptnature" lay-skin="primary" pofield="ptnature" value="未成年人" title="未成年人" style="width:20px;height:20px;">未成年人
                    <input type="checkbox" name="ptnature" lay-skin="primary" pofield="ptnature" value="未成年低保" title="未成年低保" style="width:20px;height:20px;">未成年低保
                    <input type="checkbox" name="ptnature" lay-skin="primary" pofield="ptnature" value="未成年重残" title="未成年重残" style="width:20px;height:20px;">未成年重残
                    <input type="checkbox" name="ptnature" lay-skin="primary" pofield="ptnature" value="贫困生" title="贫困生" style="width:20px;height:20px;">贫困生
                    <input type="checkbox" name="ptnature" lay-skin="primary" pofield="ptnature" value="离休" title="离休" style="width:20px;height:20px;">离休
                    <input type="checkbox" name="ptnature" lay-skin="primary" pofield="ptnature" value="普通大学生" title="普通大学生" style="width:20px;height:20px;">普通大学生

                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline" style="display:none;" id="xnh">
                <label class="layui-form-label">人口性质</label>
                <div class="layui-input-block " >
                    <input type="checkbox" name="ptnature" lay-skin="primary" pofield="ptnature" value="特困供养人员" title="特困供养人员" style="width:20px;height:20px;">特困供养人员
                    <input type="checkbox" name="ptnature" lay-skin="primary" pofield="ptnature" value="独子户" title="独子户" style="width:20px;height:20px;">独子户
                    <input type="checkbox" name="ptnature" lay-skin="primary" pofield="ptnature" value="五保户" title="五保户" style="width:20px;height:20px;">五保户
                    <input type="checkbox" name="ptnature" lay-skin="primary" pofield="ptnature" value="贫困户" title="贫困户" style="width:20px;height:20px;">贫困户
                    <input type="checkbox" name="ptnature" lay-skin="primary" pofield="ptnature" value="建档立卡的贫困人口" title="建档立卡的贫困人口" style="width:20px;height:20px;">建档立卡的贫困人口
                    <input type="checkbox" name="ptnature" lay-skin="primary" pofield="ptnature" value="低保户" title="低保户" style="width:20px;height:20px;">低保户
                    <input type="checkbox" name="ptnature" lay-skin="primary" pofield="ptnature" value="孤儿" title="孤儿" style="width:20px;height:20px;">孤儿
                    <input type="checkbox" name="ptnature" lay-skin="primary" pofield="ptnature" value="武警" title="武警" style="width:20px;height:20px;">武警
                    <input type="checkbox" name="ptnature" lay-skin="primary" pofield="ptnature" value="独女户" title="独女户" style="width:20px;height:20px;">独女户
                    <input type="checkbox" name="ptnature" lay-skin="primary" pofield="ptnature" value="重点优抚对象" title="重点优抚对象" style="width:20px;height:20px;">重点优抚对象
                    <input type="checkbox" name="ptnature" lay-skin="primary" pofield="ptnature" value="二女户" title="二女户" style="width:20px;height:20px;">二女户
                    <input type="checkbox" name="ptnature" lay-skin="primary" pofield="ptnature" value="革命五老人员" title="革命五老人员" style="width:20px;height:20px;">革命五老人员
                    <input type="checkbox" name="ptnature" lay-skin="primary" pofield="ptnature" value="重度残疾人" title="重度残疾人" style="width:20px;height:20px;">重度残疾人
                    <input type="checkbox" name="ptnature" lay-skin="primary" pofield="ptnature" value="特困户" title="特困户" style="width:20px;height:20px;">特困户
                    <input type="checkbox" name="ptnature" lay-skin="primary" pofield="ptnature" value="一般农户" title="一般农户" style="width:20px;height:20px;">一般农户
                    <input type="checkbox" name="ptnature" lay-skin="primary" pofield="ptnature" value="其他" title="其他" style="width:20px;height:20px;">其他

                </div>
            </div>
        </div>
        <%--<div class="layui-form-item">
            <label class="layui-form-label">服务类型</label>
            <div class="layui-input-block" id="signPersGroup" >
                <input type="checkbox" name="persGroup" value="1"  title="普通服务" style="width:20px;height:20px;" validator='{"check":"请选择服务类型!"}' pofield="persGroup">&nbsp;&nbsp;普通服务&nbsp;&nbsp;
                <span onclick="clickP()"><input type="checkbox" id="oldman" name="persGroup" value="4"  title="老年人服务"style="width:20px;height:20px;"  validator='{"check":"请选择服务类型!"}' pofield="persGroup">&nbsp;&nbsp;老年人服务</span>&nbsp;&nbsp;
                <input type="checkbox" name="persGroup" value="5"  title="高血压" style="width:20px;height:20px;" validator='{"check":"请选择服务类型!"}' pofield="persGroup">&nbsp;&nbsp;高血压&nbsp;&nbsp;
                <input type="checkbox" name="persGroup" value="6" title="糖尿病" style="width:20px;height:20px;" validator='{"check":"请选择服务类型!"}' pofield="persGroup">&nbsp;&nbsp;糖尿病&nbsp;&nbsp;
                <span onclick="clickP()"><input type="checkbox" id="young"name="persGroup" value="2" title="儿童（0-6岁）"style="width:20px;height:20px;" validator='{"check":"请选择服务类型!"}' pofield="persGroup">&nbsp;&nbsp;儿童（0-6岁）</span>
                <span onclick="clickP()"><input type="checkbox" name="persGroup" id="yuncf" value="3"  title="孕产妇"style="width:20px;height:20px;" validator='{"check":"请选择服务类型!"}' pofield="persGroup"></span>&nbsp;&nbsp;孕产妇&nbsp;&nbsp;
                <input type="checkbox" name="persGroup" value="8"  title="结核病" style="width:20px;height:20px;" validator='{"check":"请选择服务类型!"}' pofield="persGroup">&nbsp;&nbsp;结核病&nbsp;&nbsp;
                <input type="checkbox" name="persGroup" value="7"  title="严重精神障碍患者"style="width:20px;height:20px;" validator='{"check":"请选择服务类型!"}' pofield="persGroup">&nbsp;&nbsp;严重精神障碍患者&nbsp;&nbsp;
                <input type="checkbox" name="persGroup" value="9"  title="残疾人"style="width:20px;height:20px;" validator='{"check":"请选择服务类型!"}' pofield="persGroup">&nbsp;&nbsp;残疾人
            </div>
        </div>--%>
        <div class="layui-form-item">
            <label class="layui-form-label">团队名称</label>
            <div class="layui-input-inline" >
                <select id="ptteamid" name="ptteamid" pofield="ptteamid"  class="layui-input">
                </select>
            </div>
            <label class="layui-form-label">地址(居委会)</label>
            <div class="layui-input-inline" >
                <input type="text" id="ptregion" pofield="ptregion" placeholder="请输入详细地址" class="layui-input">
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">签约状态</label>
                <div class="layui-input-inline" >
                    <select class="layui-input" name="signwebstate" pofield="signwebstate">
                        <option value="">全部</option>
                        <option value="2">已签约</option>
                        <option value="0">预签约</option>
                        <option value="3">未签约</option>
                    </select>
                    <%--<input type="checkbox" name="signwebstate" pofield="signwebstate" value="1" title="已签约" style="width:20px;height:20px;">--%>
                </div>
            </div>
            <div class="layui-inline">
            <label class="layui-form-label">家庭成员</label>
            <div class="layui-input-block " >
                <input type="checkbox" name="ptfamily"  pofield="ptfamily" value="1" title="显示家庭成员" style="width:20px;height:20px;">
            </div>
            </div>

        </div>
    </div>

    <blockquote class="layui-elem-quote" style="text-align: center">
        <button onclick="sign_card()" class="layui-btn layui-btn-danger">
            <i class=layui-icon>&#xe642;</i>读卡签约
        </button>
        <button onclick="findsignsx()" class="layui-btn layui-btn-small">
        <i class=layui-icon>&#xe615;</i>查询
    </button>
        <button onclick="signsxcz()" class="layui-btn layui-btn-primary">
            <i class=layui-icon>&#xe60e;</i>重置
        </button>

    </blockquote>
    <table id="teamTabel" lay-filter="team">
    </table>
</div>
</body>
<script type="text/html" id="barRole">
    {{#  if(d.signwebstate == 0 || d.signwebstate == 2 ){ }}
    <a class="layui-btn layui-btn-mini " lay-event="look" ><i class=layui-icon>&#xe615;</i>查看</a>
    {{#  } }}
    {{#  if(d.signwebstate != 0 && d.signwebstate!= 2){ }}
    <a  class="layui-btn layui-btn-danger layui-btn-mini" lay-event="member"><i class=layui-icon>&#xe642;</i>签约</a>
    {{#  } }}
</script>
<script type="text/javascript" src="js/sign_pt_list.js?v=1.0"></script>
<script type="text/javascript" src="js/sign_card.js?v=1.0"></script>
</html>
