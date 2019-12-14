<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>

<head>
    <%@ include file="/open/commonjs/tldLayui.jsp"%>
    <title>产后42天访视记录详情</title>
</head>

<body>
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>产后42天访视记录详情</legend>
</fieldset>
<form class="layui-form layui-form-pane" id="view_chsf_detail1">
    <div class="layui-form-item layui-form">
        <div class="layui-inline">
            <label class="layui-form-label">
                <span class="layui-badge-dot"> </span> 随访日期</label>
            <div class="layui-input-inline">
                <input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="sfrq00" name="sfrq00" pofield="sfrq00">
            </div>
        </div>
    </div>

    <div class="layui-form-item layui-form">
        <div class="layui-inline">
            <label class="layui-form-label">一般健康情况</label>
            <div class="layui-input-inline">
                <input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="ybjkqk" name="ybjkqk" pofield="ybjkqk">
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">一般心里状况</label>
            <div class="layui-input-inline">
                <input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="ybxlzk" name="ybxlzk" pofield="ybxlzk">
            </div>
        </div>
    </div>
    <div class="layui-form-item layui-form">
        <div class="layui-inline">
            <label class="layui-form-label">血压(mmHg)</label>
            <input name="xyssy0" pofield="xyssy0" id="xyssy0" style="width: 78px;height:20px; " readonly="readonly"> /
            <input name="xyszy0" pofield="xyszy0" id="xyszy0" style="width: 78px;height:20px; " readonly="readonly">
        </div>
    </div>
    <div class="layui-form-item layui-form">
        <label class="layui-form-label" style="margin-top:10px">乳房</label>
        <div class="layui-input-block">
            <input type="checkbox" name="rf0000" title="未见异常" lay-skin="primary"  id="rf0000" value="0" pofield="rf0000" disabled>
            <input type="checkbox" name="rf0000" title="异常" lay-skin="primary"  id="rf0000" value="1" pofield="rf0000" disabled>
            <div class="layui-inline" style="margin-top:10px">
                <div class="layui-input-inline">
                    <input type="text" autocomplete="off" class="layui-input" readonly="readonly"  id="rfqt00" name="rfqt00" pofield="rfqt00">
                </div>
            </div>
        </div>
    </div>
    <div class="layui-form-item layui-form">
        <div class="layui-inline">
            <label class="layui-form-label">乳腺检查(左)</label>
            <div class="layui-input-inline">
                <input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="zcrxjc" name="zcrxjc" pofield="zcrxjc">
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">乳腺检查(右)</label>
            <div class="layui-input-inline">
                <input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="ycrxjc" name="ycrxjc" pofield="ycrxjc">
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label" style="margin-top:10px">恶露</label>
        <div class="layui-input-block">
            <input type="checkbox" name="el0000" title="未见异常" lay-skin="primary"  id="el0000" value="0" pofield="el0000" disabled="disabled" autocomplete="off">
            <input type="checkbox" name="el0000" title="异常" lay-skin="primary"  id="el0000" value="1" pofield="el0000" disabled="disabled" autocomplete="off">
            <div class="layui-inline" style="margin-top:10px">
                <div class="layui-input-inline">
                    <input type="text" autocomplete="off" class="layui-input" readonly="readonly"  id="" name="" pofield="">
                </div>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label" style="margin-top:10px">子宫</label>
        <div class="layui-input-block">
            <input type="checkbox" name="zg0000" title="未见异常" lay-skin="primary"  id="zg0000" value="0" pofield="zg0000" disabled>
            <input type="checkbox" name="zg0000" title="异常" lay-skin="primary"  id="zg0000" value="1" pofield="zg0000" disabled>
            <div class="layui-inline" style="margin-top:10px">
                <div class="layui-input-inline">
                    <input type="text" autocomplete="off" class="layui-input" readonly="readonly"  id="zgqt00" name="zgqt00" pofield="zgqt00">
                </div>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label" style="margin-top:10px">伤口</label>
        <div class="layui-input-block">
            <input type="checkbox" name="sk0000" title="未见异常" lay-skin="primary"  id="sk0000" value="0" pofield="sk0000" disabled>
            <input type="checkbox" name="sk0000" title="异常" lay-skin="primary"  id="sk0000" value="1" pofield="sk0000" disabled>
            <div class="layui-inline" style="margin-top:10px">
                <div class="layui-input-inline">
                    <input type="text" autocomplete="off" class="layui-input" readonly="readonly"  id="skqt00" name="skqt00" pofield="skqt00">
                </div>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">其他</label>
        <div class="layui-input-block">
            <div class="layui-inline">
                <div class="layui-input-inline" style="width: 387px">
                    <input type="text" autocomplete="off" class="layui-input" readonly="readonly"  id="qt0000" name="qt0000" pofield="qt0000">
                </div>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label" style="margin-top:10px">分类</label>
        <div class="layui-input-block">
            <input type="checkbox" name="fl0000" title="恢复" lay-skin="primary"  id="fl0000" value="0" pofield="fl0000" disabled>
            <input type="checkbox" name="fl0000" title="已恢复" lay-skin="primary"  id="fl0000" value="1" pofield="fl0000" disabled>
            <div class="layui-inline" style="margin-top:10px">
                <div class="layui-input-inline">
                    <input type="text" autocomplete="off" class="layui-input" readonly="readonly"  id="" name="" pofield="">
                </div>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label" style="margin-top:5px">指导</label>
        <div class="layui-input-block">
            <input type="checkbox" name="zd0000" title="性保健" lay-skin="primary" id="zd0000" value="0" pofield="zd0000" disabled>
            <input type="checkbox" name="zd0000" title="避孕" lay-skin="primary" id="zd0000" value="0" pofield="zd0000" disabled>
            <input type="checkbox" name="zd0000" title="婴儿喂养及营养" lay-skin="primary" id="zd0000" value="0" pofield="zd0000" disabled>
            <br><input type="checkbox" name="zd0000" title="其他" lay-skin="primary" id="zd0000" value="0" pofield="zd0000" disabled >
            <div class="layui-inline" style="margin-top:10px">
                <div class="layui-input-inline">
                    <input type="text" autocomplete="off" class="layui-input" readonly="readonly"  id="" name="" pofield="">
                </div>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label" style="margin-top:5px">处理</label>
        <div class="layui-input-block">
            <input type="checkbox" name="zz0000" title="结案" lay-skin="primary"  id="zz0000" value="0" pofield="zz0000" disabled>
            <input type="checkbox" name="zz0000" title="转诊" lay-skin="primary"  id="zz0000" value="1" pofield="zz0000" disabled>
        </div>
    </div>
    <div class="layui-form-item">
        原因：
        <div class="layui-inline">
            <div class="layui-input-inline" >
                <input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="zzyy00" name="zzyy00" pofield="zzyy00">
            </div>
        </div>
        机构及科室：
        <div class="layui-inline" >
            <div class="layui-input-inline" style="width:110px">
                <input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="zzjgks" name="zzjgks" pofield="zzjgks">
            </div>
        </div>
    </div>

    <div class="layui-form-item layui-form">
        <div class="layui-inline">
            <label class="layui-form-label">随访医生</label>
            <div class="layui-input-inline">
                <input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="ywys00" name="ywys00" pofield="ywys00">
            </div>
        </div>
    </div>
    <div class="layui-form-item layui-form">
        <div class="layui-inline">
            <label class="layui-form-label">是否外出</label>
            <div class="layui-input-inline">
                <input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="isout" name="isout" pofield="isout">
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">备注</label>
            <div class="layui-input-inline">
                <input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="outbz0" name="outbz0" pofield="outbz0">
            </div>
        </div>
    </div>

    </div>
</form>
</body>
<script type="text/javascript" src="js/view_chsf_detail1.js?v=1.0"></script>
</html>