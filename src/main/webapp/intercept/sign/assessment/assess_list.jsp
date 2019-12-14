<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/open/commonjs/tldLayui.jsp" %>
    <link href="css/assessment_list.css?v=1.1.1" rel="stylesheet">
    <script type="text/javascript" src="js/assess_list.js?v=1.1.8"></script>
    <title>考核列表</title>
</head>

<body bgcolor="white">
<!-- 查询条件 -->
<div id="form_qvo">
    <div class="mwrap" id="queryForm">
        <!-- 第一行-->
        <div class="layui-form-item">
            <div>
                <label class="layui-form-label">团队名称</label>
                <div class="layui-input-inline">
                    <select id="teamId" pofield="teamId" class="layui-input" onchange="addDr()">
                        <option value="">---请选择---</option>
                    </select>
                </div>
            </div>

            <div>
                <label class="layui-form-label">签约医生</label>
                <div class="layui-input-inline">
                    <select id="drId" pofield="drId" class="layui-input">
                        <option value="">---请选择---</option>
                    </select>
                </div>
            </div>

            <div>
                <label class="layui-form-label">服务人群</label>
                <div class="layui-input-inline">
                    <select pofield="group" id="group" class="layui-input">
                        <option value="">---请选择---</option>
                        <option value="health">健康人群</option>
                        <option value="young">儿童</option>
                        <option value="pregnant">孕产妇</option>
                        <option value="old">老年人</option>
                        <option value="chronic">慢性病</option>
                        <option value="tuberculosis">结核病</option>
                        <option value="psychosis">严重精神障碍</option>
                    </select>
                </div>
            </div>

            <div>
                <label class="layui-form-label">经济类型</label>
                <div class="layui-input-inline">
                    <select pofield="economics" id="economics" class="layui-input">
                        <option value="">---请选择---</option>
                        <option value="2">建档立卡贫困人口</option>
                    </select>
                </div>
            </div>
        </div>

        <!-- 第二行 -->
        <div class="layui-form-item">
            <div>
                <label class="layui-form-label">是否考核</label>
                <div class="layui-input-inline">
                    <select pofield="isAssess" id="isAssess" class="layui-input">
                        <option value="">---请选择---</option>
                        <option value="1">是</option>
                        <option value="0">否</option>
                    </select>
                </div>
            </div>

            <div>
                <label class="layui-form-label">居民姓名</label>
                <div class="layui-input-inline">
                    <input type="text" pofield="patientName" id="patientName" class="layui-input">
                </div>
            </div>

            <div>
                <label class="layui-form-label">居民身份证号</label>
                <div class="layui-input-inline">
                    <input type="text" pofield="patientIdNo" id="patientIdNo" class="layui-input">
                </div>
            </div>

            <div>
                <label class="layui-form-label">村/居委会</label>
                <div class="layui-input-inline">
                    <select pofield="committee" id="committee" class="layui-input">
                        <option value="">---请选择---</option>
                    </select>
                </div>
            </div>
        </div>

        <!-- 第三行 -->
        <div class="layui-form-item">
            <div>
                <label class="layui-form-label">签约日期</label>
                <div class="layui-input-inline">
                    <input type="text" pofield="signDateStart" id="signDateStart" autocomplete="off" class="layui-input"
                           onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:45%;display:inline;"/>
                    ~&nbsp;
                    <input type="text" pofield="signDateEnd" id="signDateEnd" autocomplete="off" class="layui-input"
                           onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:45%;display:inline;"/>
                </div>
            </div>

            <div>
                <label class="layui-form-label">协议开始日期</label>
                <div class="layui-input-inline">
                    <input type="text" pofield="signFromDateStart" id="signFromDateStart" autocomplete="off"
                           class="layui-input"
                           onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:45%;display:inline;"/>
                    ~&nbsp;
                    <input type="text" pofield="signFromDateEnd" id="signFromDateEnd" autocomplete="off" class="layui-input"
                           onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:45%;display:inline;"/>
                </div>
            </div>

            <div>
                <label class="layui-form-label">协议截止日期</label>
                <div class="layui-input-inline">
                    <input type="text" pofield="signToDateStart" id="signToDateStart" autocomplete="off"
                           class="layui-input"
                           onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:45%;display:inline;"/>
                    ~&nbsp;
                    <input type="text" pofield="signToDateEnd" id="signToDateEnd" autocomplete="off" class="layui-input"
                           onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:45%;display:inline;"/>
                </div>
            </div>

            <div>
                <label class="layui-form-label">考核评级</label>
                <div class="layui-input-inline">
                    <select pofield="rating" id="rating" class="layui-input" onchange="changeIsAssess()">
                        <option value="">---请选择---</option>
                        <option value="0">不合格</option>
                        <option value="1">合格</option>
                        <option value="2">良</option>
                        <option value="3">优</option>
                    </select>
                </div>
            </div>
        </div>

        <!-- 第三行 -->
        <div class="layui-form-item">
            <div style="display: inline;">
                <label class="layui-form-label">历史记录</label>
                <div class="layui-input-inline">
                    <select pofield="history" id="history" class="layui-input">
                        <option value="">---请选择---</option>
                        <option value="1">显示</option>
                        <option value="0" selected>不显示</option>
                    </select>
                </div>
            </div>

            <div>
                <label class="layui-form-label">状态</label>
                <div class="layui-input-inline">
                    <select pofield="state" id="state" class="layui-input" onchange="changeIsAssess()">
                        <option value="">---请选择---</option>
                        <option value="1">已完成</option>
                        <option value="2">未完成</option>
                        <option value="3">已审核</option>
                        <option value="4">审核中</option>
                    </select>
                </div>
            </div>
        </div>

        <!-- 第四行-->
        <blockquote class="layui-elem-quote" style="text-align: center" id="blockId">
            <button onclick="findList()" class="layui-btn layui-btn-small">
                <i class=layui-icon>&#xe615;</i>查询
            </button>
            <button onclick="reset()" class="layui-btn layui-btn-primary">
                <i class=layui-icon>&#xe60e;</i>重置
            </button>
        </blockquote>
    </div>
</div>

<!-- 考核列表 -->
<table id="signTabel" lay-filter="sign"></table>

</body>
<script type="text/html" id="barRole">
    <a class="layui-btn layui-btn-mini" lay-event="look"><i class=layui-icon>&#xe615;</i>查看</a>
    {{#  if (openResitAssess == '1' && d.isExpire == '是') {  }}
        <a class="layui-btn layui-btn-mini layui-btn-normal" lay-event="resitAssess"><i class=layui-icon>&#xe642;</i>补考</a>
    {{#  } else if (d.isExpire == '否') {  }}
        <a class="layui-btn layui-btn-mini layui-btn-danger" lay-event="assess"><i class=layui-icon>&#xe642;</i>考核</a>
    {{#  }  }}
</script>
</html>
