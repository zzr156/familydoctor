<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/open/commonjs/tldLayui.jsp"%>
    <title>管理指标设置</title>
</head>
<body bgcolor="white">
<form class="layui-form-pane" id="form_vo">
    <div class="layui-form-item">
        <blockquote class="layui-elem-quote">
            户籍人口数参考值<label id="upAreaPopulation" pofield="upAreaPopulation"></label>
        </blockquote>
            <label class="layui-form-label" style="width: 160px">户籍人口数</label>
        <div class="layui-input-inline">
                <input type="text"  pofield="areaPopulation" class="layui-input" validator='{"msg":"户籍人口数不能为空!","rule":"number"}'/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label" style="width: 160px">目标率(%)</label>
        <div class="layui-input-inline">
            <input type="text"  pofield="areaRate"  class="layui-input" validator='{"msg":"目标率不能为空!","rule":"number"}'/>
        </div>
    </div>
    <div class="layui-form-item">
        <blockquote class="layui-elem-quote">
            重点人群(基公卫)数参考值<label id="upAreaFocus" pofield="upAreaFocus"></label>
        </blockquote>
        <label class="layui-form-label" style="width: 160px">重点人群数(基公卫)</label>
        <div class="layui-input-inline">
            <input type="text"  pofield="areaFocus" class="layui-input" validator='{"msg":"重点人群数不能为空!","rule":"number"}'/>
        </div>
    </div>

    <div id="pt_">
        <div class="layui-form-item">
            <label class="layui-form-label" style="width: 160px">建档立卡管理数</label>
            <div class="layui-input-inline">
                <input type="text" disabled="disabled"  pofield="areaEconomicJklm" class="layui-input" validator='{"msg":"建档立卡管理数不能为空!","rule":"number"}'/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label" style="width: 160px">低保户管理数</label>
            <div class="layui-input-inline">
                <input type="text"  pofield="areaEconomicDbh" class="layui-input" validator='{"msg":"低保户管理数不能为空!","rule":"number"}'/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label" style="width: 160px">特困户管理数</label>
            <div class="layui-input-inline">
                <input type="text"  pofield="areaEconomicTkh" class="layui-input" validator='{"msg":"特困户管理数不能为空!","rule":"number"}'/>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label" style="width: 160px">计生家庭管理数</label>
            <div class="layui-input-inline">
                <input type="text"  pofield="areaEconomicJsjt" class="layui-input" validator='{"msg":"计生家庭管理数不能为空!","rule":"number"}'/>
            </div>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label" style="width: 200px">重点人群(基公卫)数签约率(%)</label>
        <div class="layui-input-inline">
            <input type="text"  pofield="areaFocusRate" class="layui-input" validator='{"msg":"重点人群数签约率不能为空!","rule":"number"}'/>
        </div>
    </div>

    <div class="layui-form-item">
        <blockquote class="layui-elem-quote">
            重点人群(经济类型)数参考值<label id="areaUpEconomic" pofield="upAreaFocus"></label>
        </blockquote>
        <label class="layui-form-label" style="width: 160px">重点人群数(经济类型)</label>
        <div class="layui-input-inline">
            <input type="text"  pofield="areaEconomic" class="layui-input" validator='{"msg":"重点人群数不能为空!","rule":"number"}'/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label" style="width: 200px">重点人群(经济类型)数签约率(%)</label>
        <div class="layui-input-inline">
            <input type="text"  pofield="areaEconomicRate" class="layui-input" validator='{"msg":"重点人群数签约率不能为空!","rule":"number"}'/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label" style="width: 160px">签约人数上限</label>
        <div class="layui-inline">
            <input type="text"  pofield="areaSignTop" class="layui-input" validator='{"msg":"签约人数上限不能为空!","rule":"number"}'/>
        </div>
        <div class="layui-inline">
            <input type="radio" name="areaSignWay" pofield="areaSignWay" value="1" title="团队">团队
            <input type="radio" name="areaSignWay" pofield="areaSignWay" value="0" title="医生">医生
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label" title="慢性病患者签约人数上限" style="width: 160px">慢性病患者签约人数上限</label>
        <div class="layui-inline">
            <input type="text"  pofield="areaDisSignTop" class="layui-input" validator='{"msg":"慢性病患者签约人数上限不能为空!","rule":"number"}'/>
        </div>
        <div class="layui-inline">
            <input type="radio" name="areaDisSignWay" pofield="areaDisSignWay" value="1" title="团队">团队
            <input type="radio" name="areaDisSignWay" pofield="areaDisSignWay" value="0" title="医生">医生
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-input-block">
            <input type="hidden"  pofield="id" id="id"/>
            <a class="layui-btn" onclick="modifyFrom();">保存</a>
        </div>
    </div>
</form>
</body>
<script type="text/javascript" src="js/people_list.js?v=1.1"></script>
</html>
