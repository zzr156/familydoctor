<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<%@ include file="/open/commonjs/tldLayui.jsp"%>
<title>慢性病随访记录调阅</title>
</head>
<body>

	<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
		<legend>高血压基本资料</legend>
	</fieldset>

	<form class="layui-form layui-form-pane" id="chronicDiseaseFollowUpRecordsForm">
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">发生于</label>
				<div class="layui-input-inline">
					<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="ci_bdate" name="ci_bdate" pofield="ci_bdate">
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">合并症</label>
				<div class="layui-input-inline">
					<select name="hsick" pofield="hsick" id="hsick" disabled="disabled">
						<option value="">--请选择--</option>
						<option value="0">无</option>
						<option value="1">头疼</option>
						<option value="2">眩晕</option>
						<option value="3">耳鸣</option>
						<option value="4">心悸气短</option>
						<option value="5">失眠</option>
						<option value="6">肢体麻木</option>
						<option value="7">恶心</option>
						<option value="8">脑血管疾病</option>
						<option value="9">糖尿病</option>
						<option value="10">高血压性心脏病</option>
						<option value="11">主动脉夹层血肿</option>
						<option value="12">其它</option>
						<option value="13">头晕</option>
					</select>
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">相关疾病</label>
				<div class="layui-input-inline">
					<select name="asick" pofield="asick" id="asick" disabled="disabled">
						<option value="">--请选择--</option>
						<option value="0">无</option>
						<option value="1">心力衰竭</option>
						<option value="2">脑出血</option>
						<option value="3">肾功能不全</option>
						<option value="4">脑血管病</option>
						<option value="5">高胆固醇血症</option>
						<option value="6">冠心病</option>
						<option value="7">慢支</option>,冠心
						<option value="8">缺血性卒中</option>
						<option value="9">其它</option>
						<option value="10">糖尿病</option>
					</select>
				</div>
			</div>
		</div>
		
		<div class="layui-form-item">
		
			<div class="layui-inline">
				<label class="layui-form-label">常用药物</label>
				<div class="layui-input-inline">
					<select name="udrug" pofield="udrug" id="udrug" disabled="disabled">
						<option value="">--请选择--</option>
						<option value="0">未服</option>
						<option value="1">尼群地平</option>
						<option value="2">甲基多巴</option>
						<option value="5">复方降压胶囊</option>
						<option value="6">合贝爽</option>
						<option value="7">利血平</option>
						<option value="8">钠催离</option>
						<option value="9">北京降压0号</option>
						<option value="10">脑复康</option>
						<option value="11">北京0号</option>
						<option value="12">乐脉颗粒</option>
						<option value="13">未服药</option>
						<option value="14">缬沙坦</option>
						<option value="15">丹参片</option>
						<option value="16">闽清降压0号</option>
						<option value="17">北京降压0</option>
						<option value="18">其它</option>
						<option value="101">卡托普利</option>
						<option value="102">司乐平</option>
						<option value="103">珍菊降压片</option>
						<option value="104">复方降压片</option>
						<option value="105">复方丹参片</option>
						<option value="106">洛汀新</option>
						<option value="107">尼福达</option>
						<option value="108">银杏叶片</option>
						<option value="109">倍他乐克</option>
						<option value="110">波依定</option>
						<option value="111">寿比山</option>
						<option value="112">安内真</option>
						<option value="113">厄贝沙坦</option>
						<option value="114">络活喜</option>
						<option value="115">尼莫地平</option>
						<option value="116">罗布麻</option>
						<option value="117">依那普利</option>
						<option value="118">硝苯地平</option>
						<option value="119">麦利平</option>
						<option value="120">开搏通</option>
						<option value="121">氢氯噻嗪</option>
						<option value="122">氨氯地平</option>
						<option value="123">缬沙坦胶囊</option>
						<option value="124">科索亚</option>
						<option value="125">代文</option>
						<option value="126">博苏</option>
						<option value="127">达爽</option>
						<option value="128">盖衡</option>
						<option value="129">维拉帕米缓释片</option>
						<option value="130">阿司匹林肠溶片</option>
						<option value="131">吲达帕胺</option>
						<option value="132">拜心同</option>
						<option value="133">替米沙坦</option>
						<option value="134">药名不详</option>
						<option value="135">其他</option>
						<option value="136">环戊甲噻嗪</option>
					</select>
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">类型</label>
				<div class="layui-input-inline">
					<select name="citype" pofield="citype" id="citype" disabled="disabled">
						<option value="">--请选择--</option>
						<option value="0">正常</option>
						<option value="1">高血压1级(低危)</option>
						<option value="2">高血压1级(中危)</option>
						<option value="3">高血压1级(高危)</option>
						<option value="4">高血压2级(中危)</option>
						<option value="5">高血压2级(高危)</option>
						<option value="6">高血压3级(中危)</option>
						<option value="7">高血压3级(高危)</option>
						<option value="8">其它</option>
					</select>
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">目前状况</label>
				<div class="layui-input-inline">
					<select name="ci_type" pofield="cstatus" id="cstatus" disabled="cstatus">
						<option value="">--请选择--</option>
						<option value="0">不详</option>
						<option value="1">治疗中</option>
						<option value="2">好转</option>
						<option value="3">治愈</option>
						<option value="4">其它</option>
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
		<legend>高血压患者随访信息</legend>
	</fieldset>

	<table id="chronicDiseaseFollowUpRecordsTable" lay-filter="chronicDiseaseFollowUpRecordsTable"></table>
</body>
<script type="text/javascript" src="js/view_chronicDiseaseFollowUpRecords_list.js?v=1.0"></script>

<script type="text/html" id="barRole">
    <a class="layui-btn layui-btn-mini " lay-event="look" ><i class=layui-icon>&#xe615;</i>查看详情</a>
</script>
<script type="text/html" id="indexTpl">
	{{d.LAY_TABLE_INDEX+1}}
</script>
</html>
