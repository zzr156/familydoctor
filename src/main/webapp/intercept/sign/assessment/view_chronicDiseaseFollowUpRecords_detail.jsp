<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<%@ include file="/open/commonjs/tldLayui.jsp"%>
<title>慢性病随访记录详情</title>
	<script>
        
	</script>
</head>
<body>
	<input type="hidden" val="test" />
	<div id="btns"></div>
	<div align="center"></div>

	<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
		<legend>慢性病随访记录详情</legend>
	</fieldset>

	<form class="layui-form layui-form-pane" id="chronicDiseaseFollowUpRecordsForm">
		<fieldset class="layui-elem-field" style="margin-top: 20px;">
			<legend>高血压患者随访服务信息</legend>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label"  style="width: 160px" >随访日期</label>
					<div class="layui-input-inline">
						<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="sfrq00" name="sfrq00" pofield="sfrq00">
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label"  style="width: 160px" >随访方式</label>
					<div class="layui-input-inline">
						<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="sffs00" name="sffs00" pofield="sffs00" >
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label"  >症状</label>
					<div class="layui-input-block">
						<input type="checkbox" name="wuzz00" lay-skin="primary" title="无症状" id="wuzz00" pofield="wuzz00" value="1" disabled="disabled">
						<input type="checkbox" name="ttty" lay-skin="primary" title="头痛头晕" id="ttty" pofield="ttty" value="1" disabled="disabled">
						<input type="checkbox" name="exot" lay-skin="primary" title="恶心呕吐" id="exot" pofield="exot" value="1" disabled="disabled">
						<input type="checkbox" name="yhem" lay-skin="primary" title="眼花耳鸣" id="yhem" pofield="yhem" value="1" disabled="disabled">
						<input type="checkbox" name="hxkn" lay-skin="primary" title="呼吸困难" id="hxkn" pofield="hxkn" value="1" disabled="disabled">
						<input type="checkbox" name="xjxm" lay-skin="primary" title="心悸胸闷" id="xjxm" pofield="xjxm" value="1" disabled="disabled">
						<input type="checkbox" name="bccxbz" lay-skin="primary" title="鼻血出血不止" id="bccxbz" pofield="bccxbz" value="1" disabled="disabled">
						<input type="checkbox" name="szfm" lay-skin="primary" title="四肢麻木" id="szfm" pofield="szfm" value="1" disabled="disabled">
						<input type="checkbox" name="xzsz" lay-skin="primary" title="下肢水肿" id="xzsz" pofield="xzsz" value="1" disabled="disabled">
						<div class="layui-inline">
							<label class="layui-form-label">其他症状</label>
							<div class="layui-input-inline">
								<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="tqzz" name="tqzz" pofield="tqzz">
							</div>
						</div>
					</div>
				</div>
				<fieldset class="layui-elem-field" style="margin-top: 20px;">
					<legend>体征</legend>
					<div class="layui-form-item">
						<div class="layui-inline" >
							<label class="layui-form-label"  style="width: 160px" >血压（mmHg）</label>
							<div class="layui-input-inline" style="width: 80px">
								<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="ssy" name="ssy" pofield="ssy">
							</div>
							<span style="display: inline-block; font-size:20px;position: relative;left: -97px;top: 3px">/</span>
							<div class="layui-input-inline" style="width: 80px">
								<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="szy" name="szy" pofield="szy">
							</div>
						</div>
						<div class="layui-inline" >
							<label class="layui-form-label"  style="width: 160px" >体重kg(目前/下次)</label>
							<div class="layui-input-inline" style="width: 80px">
								<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="tzone" name="tzone" pofield="tzone">
							</div>
							<span style="display: inline-block; font-size:20px;position: relative;left: -97px;top: 3px">/</span>
							<div class="layui-input-inline" style="width: 80px">
								<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="tztwo" name="tztwo" pofield="tztwo" >
							</div>
						</div>
						<div class="layui-inline">
							<label class="layui-form-label"  style="width: 160px" >身高（cm）</label>
							<div class="layui-input-inline">
								<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="sg0000" name="sg0000" pofield="sg0000">
							</div>
						</div>
						<div class="layui-inline">
							<label class="layui-form-label"  style="width: 160px" >体质指数</label>
							<div class="layui-input-inline">
								<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="tzzs00" name="tzzs00" pofield="tzzs00">
							</div>
						</div>
						<div class="layui-inline" >
							<label class="layui-form-label"  style="width: 160px" >心率(目前/下次)</label>
							<div class="layui-input-inline" style="width: 80px">
								<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="xlone" name="xlone" pofield="xlone">
							</div>
							<span style="display: inline-block; font-size:20px;position: relative;left: -97px;top: 3px">/</span>
							<div class="layui-input-inline" style="width: 80px">
								<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="xltwo" name="xltwo" pofield="xltwo" >
							</div>
						</div>
						<div class="layui-inline">
							<label class="layui-form-label"  style="width: 160px" >其他</label>
							<div class="layui-input-inline">
								<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="qttz00" name="qttz00" pofield="qttz00">
							</div>
						</div>
					</div>
				</fieldset>
				<fieldset class="layui-elem-field" style="margin-top: 20px;">
					<legend>生活方式指导</legend>
					<div class="layui-form-item">
						<div class="layui-inline" >
							<label class="layui-form-label" style="width: 160px">吸烟量(目前/下次)支</label>
							<div class="layui-input-inline" style="width: 80px">
								<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="rxylone" name="rxylone" pofield="rxylone">
							</div>
							<span style="display: inline-block; font-size:20px;position: relative;left: -97px;top: 3px">/</span>
							<div class="layui-input-inline" style="width: 80px">
								<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="rxyltwo" name="rxyltwo" pofield="rxyltwo" >
							</div>
						</div>
						<div class="layui-inline" >
							<label class="layui-form-label" style="width: 160px">饮酒量(目前/下次)两</label>
							<div class="layui-input-inline" style="width: 80px">
								<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="ryjlone" name="ryjlone" pofield="ryjlone">
							</div>
							<span style="display: inline-block; font-size:20px;position: relative;left: -97px;top: 3px">/</span>
							<div class="layui-input-inline" style="width: 80px">
								<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="ryjltwo" name="ryjltwo" pofield="ryjltwo" >
							</div>
						</div>
						<div class="layui-inline">
							<label class="layui-form-label"  >运动（目前）</label>
							<div class="layui-input-inline">
								<input   type="text" autocomplete="off" class="layui-input" id="ydzcone" name="ydzcone" pofield="ydzcone" readonly="readonly">
							</div>
							<span style="display: inline-block; font-size:15px;">次/周</span>
							<div class="layui-inline">
							<div class="layui-input-inline">
								<input   type="text" autocomplete="off" class="layui-input" id="ydrcone" name="ydrcone" pofield="ydrcone" readonly="readonly">
							</div>
							</div>
							<span style="display: inline-block; font-size:15px;position: relative;left: 0px">分钟/次</span>
						</div>
						<div class="layui-inline">
							<label class="layui-form-label"   >运动（下次）</label>
							<div class="layui-input-inline">
								<input   type="text" autocomplete="off" class="layui-input" id="ydzctwo" name="ydzctwo" pofield="ydzctwo" readonly="readonly">
							</div>
							<span style="display: inline-block; font-size:15px;">次/周</span>
							<div class="layui-inline">
								<div class="layui-input-inline">
									<input   type="text" autocomplete="off" class="layui-input" id="ydrctwo" name="ydrctwo" pofield="ydrctwo" readonly="readonly">
								</div>
							</div>
							<span style="display: inline-block; font-size:15px;position: relative;left: 0px">分钟/次</span>
						</div>
						<div class="layui-inline">
							<label class="layui-form-label" style="width: 160px" >摄盐情况（目前）</label>
							<div class="layui-input-inline">
								<select name="syqkone" pofield="syqkone" id="syqkone" disabled="disabled">
									<option value="">--请选择--</option>
									<option value="1">中</option>
									<option value="2">轻</option>
									<option value="3">重</option>
								</select>
							</div>
						</div>
						<div class="layui-inline">
							<label class="layui-form-label" style="width: 160px" >摄盐情况（下次）</label>
							<div class="layui-input-inline">
								<select name="syqktwo" pofield="syqktwo" id="syqktwo" disabled="disabled">
									<option value="">--请选择--</option>
									<option value="1">中</option>
									<option value="2">轻</option>
									<option value="3">重</option>
								</select>
							</div>
						</div>
						<div class="layui-inline">
							<label class="layui-form-label"  style="width: 160px" >心理调整</label>
							<div class="layui-input-inline">
								<input   type="text" autocomplete="off" class="layui-input" id="xltzqk" name="xltzqk" pofield="xltzqk" readonly="readonly">
							</div>
						</div>
						<div class="layui-inline">
							<label class="layui-form-label"  style="width: 160px" >遵医情况</label>
							<div class="layui-input-inline">
								<input   type="text" autocomplete="off" class="layui-input" id="zyxwqk" name="zyxwqk" pofield="zyxwqk" readonly="readonly">
							</div>
						</div>
					</div>
				</fieldset>
				<div class="layui-inline">
					<label class="layui-form-label"  style="width: 160px" >辅助检查</label>
					<div class="layui-input-inline"  >
						<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="qtfzjc" name="qtfzjc" pofield="qtfzjc" style="width: 325px">
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label" style="width: 160px" >服药依从性</label>
					<div class="layui-input-inline">
						<select name="fyycx0" pofield="fyycx0" id="fyycx0" disabled="disabled">
							<option value="">--请选择--</option>
							<option value="1">规律</option>
							<option value="2">间断</option>
							<option value="3">不服药</option>
						</select>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label" style="width: 160px" >药物不良反应</label>
					<div class="layui-input-inline">
						<select name="ywblfy" pofield="ywblfy" id="ywblfy" disabled="disabled">
							<option value="">--请选择--</option>
							<option value="0">无</option>
							<option value="1">有</option>
						</select>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label" style="width: 160px" >此次随访分类</label>
					<div class="layui-input-inline">
						<select name="ccsffl" pofield="ccsffl" id="ccsffl" disabled="disabled">
							<option value="">--请选择--</option>
							<option value="1">控制满意</option>
							<option value="2">控制不满意</option>
							<option value="3">不良反应</option>
							<option value="4">并发症</option>
						</select>
					</div>
				</div>
				<fieldset class="layui-elem-field" style="margin-top: 20px;">
					<legend>用药情况</legend>
					<div class="layui-form-item">
				         <table class="layui-hide" id="yyqk"></table>
					</div>
				</fieldset>
				<div class="layui-inline">
					<label class="layui-form-label" style="width: 160px" >转诊情况</label>
					<div class="layui-input-inline">
						<select name="zzqk00" pofield="zzqk00" id="zzqk00" disabled="disabled">
							<option value="">--请选择--</option>
							<option value="1">无</option>
							<option value="2">有</option>
						</select>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label"  style="width: 160px" >转诊原因</label>
					<div class="layui-input-inline">
						<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="zzyy00" name="zzyy00" pofield="zzyy00" >
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label"  style="width: 160px" >转诊机构及科室</label>
					<div class="layui-input-inline">
						<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="zzjgks" name="zzjgks" pofield="zzjgks" >
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label"  style="width: 160px" >转诊备注</label>
					<div class="layui-input-inline">
						<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="zzbz00" name="zzbz00" pofield="zzbz00" >
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label"  style="width: 160px" >下次随访日期</label>
					<div class="layui-input-inline">
						<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="xcsfrq" name="xcsfrq" pofield="xcsfrq" >
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label"  style="width: 160px" >随访医生签名</label>
					<div class="layui-input-inline">
						<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="sfysqm" name="sfysqm" pofield="sfysqm" >
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label"  style="width: 160px" >外科医生姓名</label>
					<div class="layui-input-inline">
						<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="ywysxm" name="ywysxm" pofield="ywysxm" >
					</div>
				</div>
			</div>
		</fieldset>
	</form>

</body>
<script type="text/javascript" src="js/view_chronicDiseaseFollowUpRecords_detail.js?v=1.0"></script>
</html>
