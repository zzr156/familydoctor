<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<%@ include file="/open/commonjs/tldLayui.jsp"%>
<title>糖尿病随访记录调阅</title>
</head>
<body>

	<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
		<legend>糖尿病基本资料</legend>
	</fieldset>

	<form class="layui-form layui-form-pane" id="chronicDiseaseFollowUpRecordsForm2">
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">发生于</label>
				<div class="layui-input-inline">
					<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="cibdate" name="cibdate" pofield="cibdate">
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">合并症</label>
				<div class="layui-input-inline">
					<select name="hsick" pofield="hsick" id="hsick" disabled="disabled">
						<option value="">--请选择--</option>
						<option value="30">无</option>
						<option value="31">多尿</option>
						<option value="32">多饮</option>
						<option value="33">多食</option>
						<option value="34">消瘦</option>
						<option value="35">疲乏无力</option>
						<option value="36">容易感染</option>
						<option value="37">皮肤感觉异常</option>
						<option value="38">视力障碍</option>
						<option value="39">性功能障碍</option>
						<option value="301">X综合症</option>
					</select>
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">相关疾病</label>
				<div class="layui-input-inline">
					<select name="asick" pofield="asick" id="asick" disabled="disabled">
						<option value="">--请选择--</option>
						<option value="30">无</option>
						<option value="31">感染</option>
						<option value="32">糖尿病高渗综合症</option>
						<option value="33">乳酸性酸中毒</option>
						<option value="34">糖尿病性心脏病</option>
						<option value="35">糖尿病性血管病变</option>
						<option value="36">糖尿病性肾性病变</option>
						<option value="37">眼部病变</option>
						<option value="38">神经病变</option>
						<option value="39">皮肤、肌肉关节病变</option>
					</select>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">常用药物</label>
				<div class="layui-input-inline">
					<select name="udrug" pofield="udrug" id="udrug" disabled="disabled">
						<option value="34">达美康</option>
						<option value="35">格列吡嗪</option>
						<option value="36">瑞格列奈</option>
						<option value="37">消喝丸</option>
						<option value="38">格列喹酮</option>
						<option value="39">二甲双呱</option>
						<option value="31">其他</option>
						<option value="32">药名不详</option>
						<option value="30">未服</option>
						<option value="33">胰岛素增敏剂</option>
					</select>
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">类型</label>
				<div class="layui-input-inline">
					<select name="citype" pofield="citype" id="citype" disabled="disabled">
						<option value="">--请选择--</option>
						<option value="31">I型</option>
						<option value="32">II型</option>
						<option value="33">妊娠期糖尿病</option>
						<option value="30">不详</option>
					</select>
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">目前状况</label>
				<div class="layui-input-inline">
					<select name="ci_type" pofield="cstatus" id="cstatus" disabled="cstatus">
						<option value="">--请选择--</option>
						<option value="20">不详</option>
						<option value="21">治疗中</option>
						<option value="22">好转</option>
						<option value="23">治愈</option>
					</select>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">纸质档案号</label>
				<div class="layui-input-inline">
					<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="zzdah0" name="zzdah0" pofield="zzdah0">
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">是否规范管理</label>
				<div class="layui-input-inline">
					<select name="sfgg00" pofield="sfgg00" id="sfgg00" disabled="cstatus">
						<option value="">--请选择--</option>
						<option value="0">否</option>
						<option value="1">是</option>
					</select>
				</div>
			</div>
		</div>
	</form>
	
	<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
		<legend>糖尿病患者随访信息</legend>
	</fieldset>

	<table id="chronicDiseaseFollowUpRecordsTable2" lay-filter="chronicDiseaseFollowUpRecordsTable2"></table>
</body>
<script type="text/javascript" src="js/view_chronicDiseaseFollowUpRecords_list2.js?v=1.0"></script>

<script type="text/html" id="barRole">
    <a class="layui-btn layui-btn-mini " lay-event="look" ><i class=layui-icon>&#xe615;</i>查看详情</a>
</script>
<script type="text/html" id="indexTpl">
	{{d.LAY_TABLE_INDEX+1}}
</script>
</html>
