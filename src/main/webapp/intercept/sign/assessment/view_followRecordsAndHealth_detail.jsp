<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<%@ include file="/open/commonjs/tldLayui.jsp"%>
<title>儿童随访记录详情</title>
</head>
<body>
	<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
		<legend>儿童随访记录详情</legend>
	</fieldset>
	<form class="layui-form layui-form-pane" id="followRecordsAndHealthForm">
		<div id="xse">
		<fieldset class="layui-elem-field" style="margin-top: 20px;">
			<legend>新生儿家庭随访</legend>
			<div class="layui-form-item">
		<div class="layui-inline">
			<label class="layui-form-label">随访日期</label>
			<div class="layui-input-inline">
				<input type="text" autocomplete="off" class="layui-input" id="sfrq" name="sfrq00" pofield="sfrq00" readonly="readonly">
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">月（年）龄</label>
				<div class="layui-input-inline">
					<select name="ssnnz0" pofield="ssnnz0" id="ssnnz0" disabled="disabled">
						<option value="0">新生儿</option>
						<option value="1">1岁以内儿童</option>
						<option value="2">1-2岁儿童</option>
						<option value="3">3-6岁儿童</option>
					</select>
				</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">实足年龄</label>
			    <input  style="width: 50px;display: inline-block" type="text" autocomplete="off" class="layui-input" id="etsjss" name="etsjss" pofield="etsjss" readonly="readonly">
			    <span style="display: inline-block; font-size:12px;">年</span>
			    <input  style="width: 50px;display: inline-block" type="text" autocomplete="off" class="layui-input" id="etsjys" name="etsjys" pofield="etsjys" readonly="readonly">
			    <span style="display: inline-block; font-size:12px;">月</span>
			    <input  style="width: 50px;display: inline-block" type="text" autocomplete="off" class="layui-input" id="etsjts" name="etsjts" pofield="etsjts" readonly="readonly">
			    <span style="display: inline-block; font-size:12px;text-align:center">天</span>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">是否外出</label>
			<div class="layui-input-inline">
				<div class="layui-input-inline">
					<select name="sfwc00" pofield="sfwc00" id="sfwc00" disabled="disabled">
						<option value="1">是</option>
						<option value="2">否</option>
					</select>
				</div>
			</div>
		</div>
		<br/>
		<div class="layui-inline">
			<label class="layui-form-label">当前体重</label>
			<div class="layui-input-inline">
				<input   type="text" autocomplete="off" class="layui-input" id="ettz00" name="ettz00" pofield="ettz00" readonly="readonly">
			</div>
			<span style="display: inline-block; font-size:15px">kg</span>
		</div>
		<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
		<div class="layui-inline">
			<label class="layui-form-label">当前身高</label>
			<div class="layui-input-inline">
				<input type="text" autocomplete="off" class="layui-input" id="etsg00" name="etsg00" pofield="etsg00" readonly="readonly">
			</div>
			<span style="display: inline-block; font-size:15px">cm</span>
		</div>
		<br/>
		<div class="layui-inline">
			<label class="layui-form-label">喂养方式</label>
				<div class="layui-input-inline">
					<select name="wyfs00" pofield="wyfs00" id="wyfs00" disabled="disabled">
						<option value="1">纯母乳</option>
						<option value="2">混合</option>
						<option value="3">人工</option>
					</select>
				</div>
		</div>
		<br/>
		<div class="layui-inline">
			<label class="layui-form-label">吃奶量</label>
			<div class="layui-input-inline">
				<input   type="text" autocomplete="off" class="layui-input" id="cnl000" name="cnl000" pofield="cnl000" readonly="readonly">
			</div>
			<span style="display: inline-block; font-size:15px">ml/次</span>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">吃奶次数</label>
			<div class="layui-input-inline">
				<input  type="text" autocomplete="off" class="layui-input" id="cncs00" name="cncs00" pofield="cncs00" readonly="readonly">
			</div>
			<span style="display: inline-block; font-size:15px">次/日</span>
		</div>
		<br/>
		<div class="layui-inline">
			<label class="layui-form-label">呕吐</label>
			<div class="layui-input-inline">
				<div class="layui-input-inline">
					<select name="ot0000" pofield="ot0000" id="ot0000" disabled="disabled">
						<option value="1">无</option>
						<option value="2">有</option>
					</select>
				</div>
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">大便</label>
			<div class="layui-input-inline">
				<div class="layui-input-inline">
					<select name="db0000" pofield="db0000" id="db0000" disabled="disabled">
						<option value="1">糊状</option>
						<option value="2">稀</option>
					</select>
				</div>
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">大便次数</label>
			<div class="layui-input-inline">
				<input   type="text" autocomplete="off" class="layui-input" id="dbcs00" name="dbcs00" pofield="dbcs00" readonly="readonly">
			</div>
			<span style="display: inline-block; font-size:15px">次/日</span>
		</div>
		<span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
		<div class="layui-inline">
			<label class="layui-form-label">体温</label>
			<div class="layui-input-inline">
				<input  type="text" autocomplete="off" class="layui-input" id="twqk00" name="twqk00" pofield="twqk00" readonly="readonly">
			</div>
			<span style="display: inline-block; font-size:15px">°C</span>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">脉率</label>
			<div class="layui-input-inline">
				<input   type="text" autocomplete="off" class="layui-input" id="ml0000" name="ml0000" pofield="ml0000" readonly="readonly">
			</div>
			<span style="display: inline-block; font-size:15px">次/分钟</span>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">呼吸频率</label>
			<div class="layui-input-inline">
				<input  type="text" autocomplete="off" class="layui-input" id="hxpl00" name="hxpl00" pofield="hxpl00" readonly="readonly">
			</div>
			<span style="display: inline-block; font-size:15px">次/分钟</span>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">心率</label>
			<div class="layui-input-inline">
				<input  type="text" autocomplete="off" class="layui-input" id="xl0000" name="xl0000" pofield="xl0000" readonly="readonly">
			</div>
			<span style="display: inline-block; font-size:15px">次/分钟</span>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">面色情况</label>
				<div class="layui-input-inline">
					<select name="msqk00" pofield="msqk00" id="msqk00" disabled="disabled">
						<option value="1">红润</option>
						<option value="2">黄染</option>
						<option value="3">其他</option>
					</select>
				</div>
			    <div class="layui-input-inline" style="display: inline-block" >
				    <input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="msqk04" name="msqk04" pofield="msqk04">
			    </div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">黄疸部位</label>
			<div class="layui-input-inline">
				<div class="layui-input-inline">
					<select name="hhbw00" pofield="hhbw00" id="hhbw00" disabled="disabled">
						<option value="1">面部</option>
						<option value="2">躯干</option>
						<option value="3">四肢</option>
						<option value="4">手足</option>
					</select>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">前囟</label>
			<div class="layui-inline">
			<div class="layui-input-inline">
				<input   type="text" autocomplete="off" class="layui-input" id="qcone0" name="qcone0" pofield="qcone0" readonly="readonly">
			</div>
			<span style="display: inline-block; font-size:12px;position: relative;top:8px">(cm)X</span><br/>
			<div class="layui-input-inline">
				<input   type="text" autocomplete="off" class="layui-input" id="qctwo0" name="qctwo0" pofield="qctwo0" readonly="readonly">
			</div>
				<p  style="display: inline-block; font-size:12px;position: relative;top:20px">(cm)</p>
			</div>
			<div class="layui-input-inline">
				<select name="qcqk00" pofield="qcqk00" id="qcqk00" disabled="disabled">
					<option value="1">正常</option>
					<option value="2">膨隆</option>
					<option value="3">凹陷</option>
					<option value="4">其他</option>
					<div class="layui-input-inline">
						<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="c_qcqk04" name="c_qcqk04" pofield="c_qcqk04">
					</div>
				</select>
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">眼外观</label>
			<div class="layui-input-inline">
				<div class="layui-input-inline">
					<select name="eyeqk0" pofield="eyeqk0" id="eyeqk0" disabled="disabled">
						<option value="1">未见异常</option>
						<option value="2">异常</option>
					</select>
				</div>
				<div class="layui-input-inline" style="display: inline-block" >
					<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="eyeqk02" name="eyeqk02" pofield="eyeqk02">
				</div>
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">四肢</label>
			<div class="layui-input-inline">
				<div class="layui-input-inline">
					<select name="szhdqk" pofield="szhdqk" id="szhdqk" disabled="disabled">
						<option value="1">未见异常</option>
						<option value="2">异常</option>
					</select>
				</div>
				<div class="layui-input-inline" style="display: inline-block" >
					<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="szhdqk02" name="szhdqk02" pofield="szhdqk02">
				</div>
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">耳外观</label>
			<div class="layui-input-inline">
				<div class="layui-input-inline">
					<select name="earqk0" pofield="earqk0" id="earqk0" disabled="disabled">
						<option value="1">未见异常</option>
						<option value="2">异常</option>
					</select>
				</div>
				<div class="layui-input-inline" style="display: inline-block" >
					<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="earqk02" name="earqk02" pofield="earqk02">
				</div>
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">颈部包块</label>
			<div class="layui-input-inline">
				<div class="layui-input-inline">
					<select name="jbbkqk" pofield="jbbkqk" id="jbbkqk" disabled="disabled">
						<option value="1">无</option>
						<option value="2">有</option>
					</select>
				</div>
				<div class="layui-input-inline" style="display: inline-block" >
					<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="jbbkqk02" name="jbbkqk02" pofield="jbbkqk02">
				</div>
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">鼻</label>
			<div class="layui-input-inline">
				<div class="layui-input-inline">
					<select name="noseqk" pofield="noseqk" id="noseqk" disabled="disabled">
						<option value="1">未见异常</option>
						<option value="2">异常</option>
					</select>
				</div>
				<div class="layui-input-inline" style="display: inline-block" >
					<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="noseqk2  " name="noseqk2  " pofield="noseqk2  ">
				</div>
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">皮肤</label>
			<div class="layui-input-inline">
				<div class="layui-input-inline">
					<select name="skinqk" pofield="skinqk" id="skinqk" disabled="disabled">
						<option value="1">未见异常</option>
						<option value="2">湿疹</option>
						<option value="3">糜烂</option>
						<option value="4">其他</option>
					</select>
				</div>
				<div class="layui-input-inline" style="display: inline-block" >
					<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="skinqk5" name="skinqk5" pofield="skinqk5">
				</div>
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">口腔</label>
			<div class="layui-input-inline">
				<div class="layui-input-inline">
					<select name="kqqk00" pofield="kqqk00" id="kqqk00" disabled="disabled">
						<option value="1">未见异常</option>
						<option value="2">异常</option>
					</select>
				</div>
				<div class="layui-input-inline" style="display: inline-block" >
					<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="kqqk02" name="kqqk02" pofield="kqqk02">
				</div>
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">肛门</label>
			<div class="layui-input-inline">
				<div class="layui-input-inline">
					<select name="gmqk00" pofield="gmqk00" id="gmqk00" disabled="disabled">
						<option value="1">未见异常</option>
						<option value="2">异常</option>
					</select>
				</div>
				<div class="layui-input-inline" style="display: inline-block" >
					<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="gmqk02" name="gmqk02  " pofield="gmqk02  ">
				</div>
			</div>
		</div>
				<div class="layui-inline">
					<label class="layui-form-label">心肺听诊</label>
					<div class="layui-input-inline">
						<div class="layui-input-inline">
							<select name="xftz00" pofield="xftz00" id="xftz00" disabled="disabled">
								<option value="1">未见异常</option>
								<option value="2">异常</option>
							</select>
						</div>
						<div class="layui-input-inline" style="display: inline-block" >
							<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="xftz02" name="xftz02" pofield="xftz02">
						</div>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">外生殖器</label>
					<div class="layui-input-inline">
						<div class="layui-input-inline">
							<select name="wszqqk" pofield="wszqqk" id="wszqqk" disabled="disabled">
								<option value="1">未见异常</option>
								<option value="2">异常</option>
							</select>
						</div>
						<div class="layui-input-inline" style="display: inline-block" >
							<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="wszqqk02" name="wszqqk02" pofield="wszqqk02">
						</div>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">腹部触诊</label>
					<div class="layui-input-inline">
						<div class="layui-input-inline">
							<select name="fbcz00" pofield="fbcz00" id="fbcz00" disabled="disabled">
								<option value="1">未见异常</option>
								<option value="2">异常</option>
							</select>
						</div>
						<div class="layui-input-inline" style="display: inline-block" >
							<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="fbcz02" name="fbcz02" pofield="fbcz02">
						</div>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">脊椎</label>
					<div class="layui-input-inline">
						<div class="layui-input-inline">
							<select name="jzqk00" pofield="jzqk00" id="jzqk00" disabled="disabled">
								<option value="1">未见异常</option>
								<option value="2">异常</option>
							</select>
						</div>
						<div class="layui-input-inline" style="display: inline-block" >
							<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="jzqk02" name="jzqk02" pofield="jzqk02">
						</div>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">脐部</label>
					<div class="layui-input-inline">
						<div class="layui-input-inline">
							<select name="qdqk00" pofield="qdqk00" id="qdqk00" disabled="disabled">
								<option value="1">未脱落</option>
								<option value="2">脱落</option>
								<option value="3">脐带有渗出</option>
								<option value="4">其他</option>
							</select>
						</div>
						<div class="layui-input-inline" style="display: inline-block" >
							<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="c_qdqk04" name="c_qdqk04" pofield="c_qdqk04">
						</div>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">转诊结果</label>
					<div class="layui-input-inline">
						<select name="zzqk00" pofield="zzqk00" id="zzqk00" disabled="disabled">
							<option value="">--请选择--</option>
							<option value="1">无</option>
							<option value="2">有</option>
						</select>
					</div>
				</div>
		<div class="layui-inline">
			<label class="layui-form-label">随访结果</label>
			<div class="layui-input-inline">
				<select name="sfjg00" pofield="sfjg00" id="sfjg00" disabled="disabled">
					<option value="">--请选择--</option>
					<option value="1">正常</option>
					<option value="2">异常</option>
				</select>
			</div>
		</div>
				<div class="layui-inline">
					<label class="layui-form-label">指导</label>
					<div class="layui-input-inline">
						<div class="layui-input-inline">
							<select name="zdqk00" pofield="zdqk00" id="zdqk00" disabled="disabled">
								<option value="1">科学喂养</option>
								<option value="2">生长发育</option>
								<option value="3">疾病预防</option>
								<option value="4">预防意外伤害</option>
								<option value="5">口腔保健</option>
								<option value="6">其他</option>
							</select>
						</div>
						<div class="layui-input-inline" style="display: inline-block" >
							<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="earqk780" name="earqk0" pofield="earqk0">
						</div>
					</div>
				</div>
		<div class="layui-inline">
			<label class="layui-form-label">备注</label>
			<div class="layui-input-inline">
				<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="bz0000" name="bz0000" pofield="bz0000">
			</div>
		</div><br/>
		<div class="layui-inline">
			<label class="layui-form-label">下次随访日期</label>
			<div class="layui-input-inline">
				<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="xcsfrq" name="xcsfrq" pofield="xcsfrq">
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">下次随访地点</label>
			<div class="layui-input-inline">
				<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="xcsfdd" name="xcsfdd" pofield="xcsfdd">
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">随访医生</label>
			<div class="layui-input-inline">
				<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="sfys00" name="sfys00" pofield="sfys00">
			</div>
		</div>
			</div>
	     </fieldset>
		</div>


		<div id="ysyn">
		<fieldset class="layui-elem-field" style="margin-top: 20px;">
			<legend>一岁以内儿童健康检测</legend>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">随访日期</label>
					<div class="layui-input-inline">
						<input type="text" autocomplete="off" class="layui-input" id="sfrq00" name="sfrq00" pofield="sfrq00" readonly="readonly">
					</div>
				</div>
				<br/>
				<div class="layui-inline">
					<label class="layui-form-label">月（年）龄</label>
					<div class="layui-input-inline">
						<select name="ssnnz0" pofield="ssnnz0" id="ssnnz0" disabled="disabled">
							<option value="0">新生儿</option>
							<option value="1">1岁以内儿童</option>
							<option value="2">1-2岁儿童</option>
							<option value="3">3-6岁儿童</option>
						</select>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">实足年龄</label>
					<input  style="width: 50px;display: inline-block" type="text" autocomplete="off" class="layui-input" id="etsjss" name="etsjss" pofield="etsjss" readonly="readonly">
					<span style="display: inline-block; font-size:12px;">年</span>
					<input  style="width: 50px;display: inline-block" type="text" autocomplete="off" class="layui-input" id="etsjys" name="etsjys" pofield="etsjys" readonly="readonly">
					<span style="display: inline-block; font-size:12px;">月</span>
					<input  style="width: 50px;display: inline-block" type="text" autocomplete="off" class="layui-input" id="etsjts" name="etsjts" pofield="etsjts" readonly="readonly">
					<span style="display: inline-block; font-size:12px;text-align:center">天</span>
				</div>
				<br/>
				<div class="layui-inline">
					<label class="layui-form-label">是否外出</label>
					<div class="layui-input-inline">
						<div class="layui-input-inline">
							<select name="sfwc00" pofield="sfwc00" id="sfwc00" disabled="disabled">
								<option value="1">是</option>
								<option value="2">否</option>
							</select>
						</div>
					</div>
				</div>
				<br/>
				<div class="layui-inline">
					<label class="layui-form-label">体重</label>
					<div class="layui-input-inline">
						<input   type="text" autocomplete="off" class="layui-input" id="ettz00" name="ettz00" pofield="ettz00" readonly="readonly">
					</div>
					<span style="display: inline-block; font-size:15px;position: relative;left: -110px">kg</span>
					<label class="layui-form-label" style="position: relative;left: 20px">评价</label>
					<div class="layui-input-inline">
						<select name="tzqk00" pofield="tzqk00" id="tzqk00" disabled="disabled">
							<option value="1">上</option>
							<option value="2">中</option>
							<option value="3">下</option>
						</select>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">身高</label>
					<div class="layui-input-inline">
						<input   type="text" autocomplete="off" class="layui-input" id="dqheight1" name="birthday" pofield="birthday" readonly="readonly">
					</div>
					<span style="display: inline-block; font-size:15px;position: relative;left: -110px">cm</span>
					<label class="layui-form-label" style="position: relative;left: 20px">评价</label>
					<div class="layui-input-inline">
						<select name="sgqk00" pofield="sgqk00" id="sgqk00" disabled="disabled">
							<option value="1">上</option>
							<option value="2">中</option>
							<option value="3">下</option>
						</select>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">标准体重</label>
					<div class="layui-input-inline">
						<input   type="text" autocomplete="off" class="layui-input" id="bztz00" name="bztz00" pofield="bztz00" readonly="readonly">
					</div>
					<span style="display: inline-block; font-size:15px;">kg</span>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">标准身高</label>
					<div class="layui-input-inline">
						<input   type="text" autocomplete="off" class="layui-input" id="bzsg00" name="bzsg00" pofield="bzsg00" readonly="readonly">
					</div>
					<span style="display: inline-block; font-size:15px;">cm</span>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">体重/身高标准值</label>
					<div class="layui-input-inline">
						<input   type="text" autocomplete="off" class="layui-input" id="tzzsgbz" name="tzzsgbz" pofield="tzzsgbz" readonly="readonly">
					</div>
					<span style="display: inline-block; font-size:15px;">kg</span>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">体重/年龄标准差</label>
					<div class="layui-input-inline">
						<select name="tzpjxx" pofield="tzpjxx" id="tzpjxx" disabled="disabled">
							<option value="">--请选择--</option>
							<option value="-4">-3SD以下</option>
							<option value="-3">-3SD～-2SD</option>
							<option value="-2">-2SD～-1SD</option>
							<option value="-1">-1SD～SD</option>
							<option value="1">SD～+1SD</option>
							<option value="2">+1SD～+2SD</option>
							<option value="3">+2SD～+3SD</option>
							<option value="4">3SD以上</option>
						</select>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">身高/年龄标准差</label>
					<div class="layui-input-inline">
						<select name="sgpjxx" pofield="sgpjxx" id="sgpjxx" disabled="disabled">
							<option value="">--请选择--</option>
							<option value="-4">-3SD以下</option>
							<option value="-3">-3SD～-2SD</option>
							<option value="-2">-2SD～-1SD</option>
							<option value="-1">-1SD～SD</option>
							<option value="1">SD～+1SD</option>
							<option value="2">+1SD～+2SD</option>
							<option value="3">+2SD～+3SD</option>
							<option value="4">3SD以上</option>
						</select>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">体重/身高标准差</label>
					<div class="layui-input-inline">
						<select name="sctzpj" pofield="sctzpj" id="sctzpj" disabled="disabled">
							<option value="">--请选择--</option>
							<option value="-4">-3SD以下</option>
							<option value="-3">-3SD～-2SD</option>
							<option value="-2">-2SD～-1SD</option>
							<option value="-1">-1SD～SD</option>
							<option value="1">SD～+1SD</option>
							<option value="2">+1SD～+2SD</option>
							<option value="3">+2SD～+3SD</option>
							<option value="4">3SD以上</option>
						</select>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">蛋白质能力营养不良</label>
					<div class="layui-input-inline">
						<select name="zdjgyy" pofield="zdjgyy" id="zdjgyy" disabled="disabled">
							<option value="">--请选择--</option>
							<option value="1">轻度</option>
							<option value="2">中度(低体重)</option>
							<option value="3">中度(发育迟缓)</option>
							<option value="4">重度(低体重)</option>
							<option value="5">重度(发育迟缓)</option>
						</select>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">营养过剩评价</label>
					<div class="layui-input-inline">
						<select name="zdjgyy" pofield="zdjgyy" id="zdjgyy" disabled="disabled">
							<option value="">--请选择--</option>
							<option value="0">正常</option>
							<option value="1">超重</option>
							<option value="2">肥胖</option>
						</select>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">体格发育评价</label>
					<div class="layui-input-inline">
						<select name="tgfypj" pofield="tgfypj" id="tgfypj" disabled="disabled">
							<option value="">--请选择--</option>
							<option value="1">正常</option>
							<option value="2">低体重</option>
							<option value="3">消瘦</option>
							<option value="4">发育迟缓</option>
							<option value="5">超重</option>
							<option value="6">肥胖</option>
						</select>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">营养评价</label>
					<div class="layui-input-inline">
						<select name="yypj00" pofield="yypj00" id="yypj00" disabled="disabled">
							<option value="">--请选择--</option>
							<option value="1">差</option>
							<option value="2">中</option>
							<option value="3">好</option>
						</select>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">头围</label>
					<div class="layui-input-inline">
						<input   type="text" autocomplete="off" class="layui-input" id="tw0000" name="tw0000" pofield="tw0000" readonly="readonly">
					</div>
					<span style="display: inline-block; font-size:15px;">(cm)</span>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">头围评价</label>
					<div class="layui-input-inline">
						<input   type="text" autocomplete="off" class="layui-input" id="twpj00" name="twpj00" pofield="twpj00" readonly="readonly">
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">头围/年龄标准差</label>
					<div class="layui-input-inline">
						<select name="twbzc0" pofield="twbzc0" id="twbzc0" disabled="disabled">
							<option value="">--请选择--</option>
							<option value="-4">-3SD以下</option>
							<option value="-3">-3SD～-2SD</option>
							<option value="-2">-2SD～-1SD</option>
							<option value="-1">-1SD～SD</option>
							<option value="1">SD～+1SD</option>
							<option value="2">+1SD～+2SD</option>
							<option value="3">+2SD～+3SD</option>
							<option value="4">3SD以上</option>
						</select>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">面色情况</label>
					<div class="layui-input-inline">
						<select name="msqk00" pofield="msqk00" id="msqk00" disabled="disabled">
							<option value="1">红润</option>
							<option value="2">黄染</option>
							<option value="3">其他</option>
						</select>
					</div>
					<div class="layui-input-inline" style="display: inline-block" >
						<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="msqk04" name="msqk04" pofield="msqk04">
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">皮肤</label>
					<div class="layui-input-inline">
						<div class="layui-input-inline">
							<select name="skinqk" pofield="skinqk" id="skinqk" disabled="disabled">
								<option value="1">未见异常</option>
								<option value="2">湿疹</option>
								<option value="3">糜烂</option>
								<option value="4">其他</option>
							</select>
						</div>
						<div class="layui-input-inline" style="display: inline-block" >
							<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="skinqk5" name="skinqk5" pofield="skinqk5">
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">前囟</label>
					<div class="layui-input-inline">
						<select name="qcqk00" pofield="qcqk00" id="qcqk00" disabled="disabled">
							<option value="">--请选择--</option>
							<option value="1">闭合</option>
							<option value="2">未闭合</option>
						</select>
					</div>
					<div class="layui-inline">
						<div class="layui-input-inline">
							<input   type="text" autocomplete="off" class="layui-input" id="qcone0" name="qcone0" pofield="qcone0" readonly="readonly">
						</div>
						<span style="display: inline-block; font-size:12px;position: relative;top:8px">(cm)X</span><br/>
						<div class="layui-input-inline">
							<input   type="text" autocomplete="off" class="layui-input" id="qctwo0" name="qctwo0" pofield="qctwo0" readonly="readonly">
						</div>
						<p  style="display: inline-block; font-size:12px;position: relative;top:20px">(cm)</p>
					</div>
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">颈部包块</label>
				<div class="layui-input-inline">
					<div class="layui-input-inline">
						<select name="jbbkqk" pofield="jbbkqk" id="jbbkqk" disabled="disabled">
							<option value="1">无</option>
							<option value="2">有</option>
						</select>
					</div>
					<div class="layui-input-inline" style="display: inline-block" >
						<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="jbbk02" name="jbbk02" pofield="jbbk02">
					</div>
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">眼外观</label>
				<div class="layui-input-inline">
					<div class="layui-input-inline">
						<select name="eyeqk0" pofield="eyeqk0" id="eyeqk0" disabled="disabled">
							<option value="1">未见异常</option>
							<option value="2">异常</option>
						</select>
					</div>
					<div class="layui-input-inline" style="display: inline-block" >
						<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="eyeqk02" name="eyeqk02" pofield="eyeqk02">
					</div>
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">耳外观</label>
				<div class="layui-input-inline">
					<div class="layui-input-inline">
						<select name="earqk0" pofield="earqk0" id="earqk0" disabled="disabled">
							<option value="1">未见异常</option>
							<option value="2">异常</option>
						</select>
					</div>
					<div class="layui-input-inline" style="display: inline-block" >
						<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="earqk02" name="earqk02" pofield="earqk02">
					</div>
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">听力</label>
				<div class="layui-input-inline">
					<div class="layui-input-inline">
						<select name="tl0000" pofield="tl0000" id="tl0000" disabled="disabled">
							<option value="1">通过</option>
							<option value="2">未通过</option>
						</select>
					</div>
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">口腔</label>
				<div class="layui-input-inline">
					<div class="layui-input-inline">
						<select name="kqqk00" pofield="kqqk00" id="kqqk00" disabled="disabled">
							<option value="1">未见异常</option>
							<option value="2">异常</option>
						</select>
					</div>
					<div class="layui-input-inline" style="display: inline-block" >
						<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="kqqk02" name="kqqk02" pofield="kqqk02">
					</div>
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">出牙数</label>
				<div class="layui-input-inline">
					<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="yscys " name="yscys " pofield="yscys ">
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">心肺</label>
				<div class="layui-input-inline">
					<div class="layui-input-inline">
						<select name="xftz00" pofield="xftz00" id="xftz00" disabled="disabled">
							<option value="1">未见异常</option>
							<option value="2">异常</option>
						</select>
					</div>
					<div class="layui-input-inline" style="display: inline-block" >
						<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="xftz02" name="xftz02" pofield="xftz02">
					</div>
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">腹部</label>
				<div class="layui-input-inline">
					<div class="layui-input-inline">
						<select name="fbcz00" pofield="fbcz00" id="fbcz00" disabled="disabled">
							<option value="1">未见异常</option>
							<option value="2">异常</option>
						</select>
					</div>
					<div class="layui-input-inline" style="display: inline-block" >
						<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="fbcz02" name="fbcz02" pofield="fbcz02">
					</div>
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">四肢</label>
				<div class="layui-input-inline">
					<div class="layui-input-inline">
						<select name="szhdqk" pofield="szhdqk" id="szhdqk" disabled="disabled">
							<option value="1">未见异常</option>
							<option value="2">异常</option>
						</select>
					</div>
					<div class="layui-input-inline" style="display: inline-block" >
						<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="szhdqk02" name="szhdqk02" pofield="szhdqk02">
					</div>
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">脐部</label>
				<div class="layui-input-inline">
					<div class="layui-input-inline">
						<select name="qdqk00" pofield="qdqk00" id="qdqk00" disabled="disabled">
							<option value="1">未脱落</option>
							<option value="2">脱落</option>
							<option value="3">脐带有渗出</option>
							<option value="4">其他</option>
						</select>
					</div>
					<div class="layui-input-inline" style="display: inline-block" >
						<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="c_qdqk04" name="c_qdqk04" pofield="c_qdqk04">
					</div>
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">可疑佝偻病症状</label>
				<div class="layui-input-inline">
					<div class="layui-input-inline">
						<select name="glbzz0" pofield="glbzz0" id="glbzz0" disabled="disabled">
							<option value="1">无</option>
							<option value="2">夜惊</option>
							<option value="3">多汗</option>
							<option value="4">烦躁</option>
						</select>
					</div>
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">可疑佝偻病体征</label>
				<div class="layui-input-inline">
					<div class="layui-input-inline">
						<select name="glbtz0" pofield="glbtz0" id="glbtz0" disabled="disabled">
							<option value="1">无</option>
							<option value="2">颅骨软化</option>
							<option value="3">方颅</option>
							<option value="4">枕秃</option>
							<option value="5">肋串珠</option>
							<option value="6">肋外翻</option>
							<option value="7">肋软骨沟</option>
							<option value="8">鸡胸</option>
							<option value="9">手镯征</option>
							<option value="10">o型腿</option>
							<option value="11">X型腿</option>
						</select>
					</div>
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">肛门/外生殖器</label>
				<div class="layui-input-inline">
					<div class="layui-input-inline">
						<select name="wszqqk" pofield="wszqqk" id="wszqqk" disabled="disabled">
							<option value="1">未见异常</option>
							<option value="2">异常</option>
						</select>
					</div>
					<div class="layui-input-inline" style="display: inline-block" >
						<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="wszqqk02" name="wszqqk02" pofield="wszqqk02">
					</div>
				</div>
			</div>
				<div class="layui-inline">
					<label class="layui-form-label">血红蛋白</label>
					<div class="layui-input-inline">
						<input   type="text" autocomplete="off" class="layui-input" id="xhdb00" name="xhdb00" pofield="xhdb00" readonly="readonly">
					</div>
					<span style="display: inline-block; font-size:15px;">g/L</span>
				</div>
			<div class="layui-inline">
				<label class="layui-form-label">中重度贫血</label>
				<div class="layui-input-inline">
					<div class="layui-input-inline">
						<select name="zzdpx0" pofield="zzdpx0" id="zzdpx0" disabled="disabled">
							<option value="0">否</option>
							<option value="1">是</option>
						</select>
					</div>
				</div>
			</div>
				<div class="layui-inline">
					<label class="layui-form-label">户外运动</label>
					<div class="layui-input-inline">
						<input   type="text" autocomplete="off" class="layui-input" id="hwhdqk" name="hwhdqk" pofield="hwhdqk" readonly="readonly">
					</div>
					<span style="display: inline-block; font-size:15px;">小时/日</span>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">服用维生素D</label>
					<div class="layui-input-inline">
						<input   type="text" autocomplete="off" class="layui-input" id="wss000" name="wss000" pofield="wss000" readonly="readonly">
					</div>
					<span style="display: inline-block; font-size:15px;">IU/日</span>
				</div>
			<div class="layui-inline">
				<label class="layui-form-label">发育评估</label>
				<div class="layui-input-inline">
					<div class="layui-input-inline">
						<select name="fypgqk" pofield="fypgqk" id="fypgqk" disabled="disabled">
							<option value="1">通过</option>
							<option value="2">未通过</option>
						</select>
					</div>
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">随访期间患病情况</label>
				<div class="layui-input-inline">
					<div class="layui-input-inline">
						<select name="hbqk00" pofield="hbqk00" id="hbqk00" disabled="disabled">
							<option value="1">未患病</option>
							<option value="2">患病</option>
						</select>
					</div>
					<div class="layui-inline">
						<label class="layui-form-label">其他</label>
						<div class="layui-input-inline">
							<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="qtfshb1" name="qtfs00" pofield="qtfs00">
						</div>
					</div>
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">转诊结果</label>
				<div class="layui-input-inline">
					<select name="zzqk00" pofield="zzqk00" id="zzqk00" disabled="disabled">
						<option value="">--请选择--</option>
						<option value="1">无</option>
						<option value="2">有</option>
					</select>
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">指导</label>
				<div class="layui-input-inline">
					<div class="layui-input-inline">
						<select name="zdqk00" pofield="zdqk00" id="zdqk00" disabled="disabled">
							<option value="1">科学喂养</option>
							<option value="2">生长发育</option>
							<option value="3">疾病预防</option>
							<option value="4">预防意外伤害</option>
							<option value="5">口腔保健</option>
							<option value="6">其他</option>
						</select>
					</div>
					<div class="layui-input-inline" style="display: inline-block" >
						<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="earqk780" name="earqk0" pofield="earqk0">
					</div>
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">备注</label>
				<div class="layui-input-inline">
					<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="bz0000" name="bz0000" pofield="bz0000">
				</div>
			</div><br/>
			<div class="layui-inline">
				<label class="layui-form-label">下次随访日期</label>
				<div class="layui-input-inline">
					<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="xcsfrq" name="xcsfrq" pofield="xcsfrq">
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">下次随访地点</label>
				<div class="layui-input-inline">
					<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="xcsfdd" name="xcsfdd" pofield="xcsfdd">
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">随访医生</label>
				<div class="layui-input-inline">
					<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="sfys00" name="sfys00" pofield="sfys00">
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">随访结果</label>
				<div class="layui-input-inline">
					<select name="sfjg00" pofield="sfjg00" id="sfjg00" disabled="disabled">
						<option value="">--请选择--</option>
						<option value="1">正常</option>
						<option value="2">异常</option>
					</select>
				</div>
			</div>
		</fieldset>
		</div>
		<div id="yzls">
		<fieldset class="layui-elem-field" style="margin-top: 20px;">
			<legend>1-2岁儿童健康检测</legend>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">随访日期</label>
					<div class="layui-input-inline">
						<input type="text" autocomplete="off" class="layui-input" id="sfrq00" name="sfrq00" pofield="sfrq00" readonly="readonly">
					</div>
				</div>
				<br/>
				<div class="layui-inline">
					<label class="layui-form-label">月（年）龄</label>
					<div class="layui-input-inline">
						<select name="ssnnz0" pofield="ssnnz0" id="ssnnz0" disabled="disabled">
							<option value="0">新生儿</option>
							<option value="1">1岁以内儿童</option>
							<option value="2">1-2岁儿童</option>
							<option value="3">3-6岁儿童</option>
						</select>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">实足年龄</label>
					<input  style="width: 50px;display: inline-block" type="text" autocomplete="off" class="layui-input" id="etsjss" name="etsjss" pofield="etsjss" readonly="readonly">
					<span style="display: inline-block; font-size:12px;">年</span>
					<input  style="width: 50px;display: inline-block" type="text" autocomplete="off" class="layui-input" id="etsjys" name="etsjys" pofield="etsjys" readonly="readonly">
					<span style="display: inline-block; font-size:12px;">月</span>
					<input  style="width: 50px;display: inline-block" type="text" autocomplete="off" class="layui-input" id="etsjts" name="etsjts" pofield="etsjts" readonly="readonly">
					<span style="display: inline-block; font-size:12px;text-align:center">天</span>
				</div>
				<br/>
				<div class="layui-inline">
					<label class="layui-form-label">是否外出</label>
					<div class="layui-input-inline">
						<div class="layui-input-inline">
							<select name="sfwc00" pofield="sfwc00" id="sfwc00" disabled="disabled">
								<option value="1">是</option>
								<option value="2">否</option>
							</select>
						</div>
					</div>
				</div>
				<br/>
				<div class="layui-inline">
					<label class="layui-form-label">体重</label>
					<div class="layui-input-inline">
						<input   type="text" autocomplete="off" class="layui-input" id="ettz00" name="ettz00" pofield="ettz00" readonly="readonly">
					</div>
					<span style="display: inline-block; font-size:15px;position: relative;left: -110px">kg</span>
					<label class="layui-form-label" style="position: relative;left: 20px">评价</label>
					<div class="layui-input-inline">
						<select name="tzqk00" pofield="tzqk00" id="tzqk00" disabled="disabled">
							<option value="1">上</option>
							<option value="2">中</option>
							<option value="3">下</option>
						</select>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">身高</label>
					<div class="layui-input-inline">
						<input   type="text" autocomplete="off" class="layui-input" id="dqheight1" name="birthday" pofield="birthday" readonly="readonly">
					</div>
					<span style="display: inline-block; font-size:15px;position: relative;left: -110px">cm</span>
					<label class="layui-form-label" style="position: relative;left: 20px">评价</label>
					<div class="layui-input-inline">
						<select name="sgqk00" pofield="sgqk00" id="sgqk00" disabled="disabled">
							<option value="1">上</option>
							<option value="2">中</option>
							<option value="3">下</option>
						</select>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">标准体重</label>
					<div class="layui-input-inline">
						<input   type="text" autocomplete="off" class="layui-input" id="bztz00" name="bztz00" pofield="bztz00" readonly="readonly">
					</div>
					<span style="display: inline-block; font-size:15px;">kg</span>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">标准身高</label>
					<div class="layui-input-inline">
						<input   type="text" autocomplete="off" class="layui-input" id="bzsg00" name="bzsg00" pofield="bzsg00" readonly="readonly">
					</div>
					<span style="display: inline-block; font-size:15px;">cm</span>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">体重/身高标准值</label>
					<div class="layui-input-inline">
						<input   type="text" autocomplete="off" class="layui-input" id="tzzsgbz" name="tzzsgbz" pofield="tzzsgbz" readonly="readonly">
					</div>
					<span style="display: inline-block; font-size:15px;">kg</span>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">体重/年龄标准差</label>
					<div class="layui-input-inline">
						<select name="tzpjxx" pofield="tzpjxx" id="tzpjxx" disabled="disabled">
							<option value="">--请选择--</option>
							<option value="-4">-3SD以下</option>
							<option value="-3">-3SD～-2SD</option>
							<option value="-2">-2SD～-1SD</option>
							<option value="-1">-1SD～SD</option>
							<option value="1">SD～+1SD</option>
							<option value="2">+1SD～+2SD</option>
							<option value="3">+2SD～+3SD</option>
							<option value="4">3SD以上</option>
						</select>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">身高/年龄标准差</label>
					<div class="layui-input-inline">
						<select name="sgpjxx" pofield="sgpjxx" id="sgpjxx" disabled="disabled">
							<option value="">--请选择--</option>
							<option value="-4">-3SD以下</option>
							<option value="-3">-3SD～-2SD</option>
							<option value="-2">-2SD～-1SD</option>
							<option value="-1">-1SD～SD</option>
							<option value="1">SD～+1SD</option>
							<option value="2">+1SD～+2SD</option>
							<option value="3">+2SD～+3SD</option>
							<option value="4">3SD以上</option>
						</select>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">体重/身高标准差</label>
					<div class="layui-input-inline">
						<select name="sctzpj" pofield="sctzpj" id="sctzpj" disabled="disabled">
							<option value="">--请选择--</option>
							<option value="-4">-3SD以下</option>
							<option value="-3">-3SD～-2SD</option>
							<option value="-2">-2SD～-1SD</option>
							<option value="-1">-1SD～SD</option>
							<option value="1">SD～+1SD</option>
							<option value="2">+1SD～+2SD</option>
							<option value="3">+2SD～+3SD</option>
							<option value="4">3SD以上</option>
						</select>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">蛋白质能力营养不良</label>
					<div class="layui-input-inline">
						<select name="zdjgyy" pofield="zdjgyy" id="zdjgyy" disabled="disabled">
							<option value="">--请选择--</option>
							<option value="1">轻度</option>
							<option value="2">中度(低体重)</option>
							<option value="3">中度(发育迟缓)</option>
							<option value="4">重度(低体重)</option>
							<option value="5">重度(发育迟缓)</option>
						</select>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">营养过剩评价</label>
					<div class="layui-input-inline">
						<select name="zdjgyy" pofield="zdjgyy" id="zdjgyy" disabled="disabled">
							<option value="">--请选择--</option>
							<option value="0">正常</option>
							<option value="1">超重</option>
							<option value="2">肥胖</option>
						</select>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">体格发育评价</label>
					<div class="layui-input-inline">
						<select name="tgfypj" pofield="tgfypj" id="tgfypj" disabled="disabled">
							<option value="">--请选择--</option>
							<option value="1">正常</option>
							<option value="2">低体重</option>
							<option value="3">消瘦</option>
							<option value="4">发育迟缓</option>
							<option value="5">超重</option>
							<option value="6">肥胖</option>
						</select>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">营养评价</label>
					<div class="layui-input-inline">
						<select name="yypj00" pofield="yypj00" id="yypj00" disabled="disabled">
							<option value="">--请选择--</option>
							<option value="1">差</option>
							<option value="2">中</option>
							<option value="3">好</option>
						</select>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">面色情况</label>
					<div class="layui-input-inline">
						<select name="msqk00" pofield="msqk00" id="msqk00" disabled="disabled">
							<option value="1">红润</option>
							<option value="2">黄染</option>
							<option value="3">其他</option>
						</select>
					</div>
					<div class="layui-input-inline" style="display: inline-block" >
						<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="msqk04" name="msqk04" pofield="msqk04">
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">皮肤</label>
					<div class="layui-input-inline">
						<div class="layui-input-inline">
							<select name="skinqk" pofield="skinqk" id="skinqk" disabled="disabled">
								<option value="1">未见异常</option>
								<option value="2">异常</option>
							</select>
						</div>
						<div class="layui-input-inline" style="display: inline-block" >
							<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="skinqk5" name="skinqk5" pofield="skinqk5">
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">前囟</label>
					<div class="layui-input-inline">
						<select name="qcqk00" pofield="qcqk00" id="qcqk00" disabled="disabled">
							<option value="">--请选择--</option>
							<option value="1">闭合</option>
							<option value="2">未闭合</option>
						</select>
					</div>
					<div class="layui-inline">
						<div class="layui-input-inline">
							<input   type="text" autocomplete="off" class="layui-input" id="qcone0" name="qcone0" pofield="qcone0" readonly="readonly">
						</div>
						<span style="display: inline-block; font-size:12px;position: relative;top:8px">(cm)X</span><br/>
						<div class="layui-input-inline">
							<input   type="text" autocomplete="off" class="layui-input" id="qctwo0" name="qctwo0" pofield="qctwo0" readonly="readonly">
						</div>
						<p  style="display: inline-block; font-size:12px;position: relative;top:20px">(cm)</p>
					</div>
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">眼外观</label>
				<div class="layui-input-inline">
					<div class="layui-input-inline">
						<select name="eyeqk0" pofield="eyeqk0" id="eyeqk0" disabled="disabled">
							<option value="1">未见异常</option>
							<option value="2">异常</option>
						</select>
					</div>
					<div class="layui-input-inline" style="display: inline-block" >
						<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="eyeqk02" name="eyeqk02" pofield="eyeqk02">
					</div>
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">耳外观</label>
				<div class="layui-input-inline">
					<div class="layui-input-inline">
						<select name="earqk0" pofield="earqk0" id="earqk0" disabled="disabled">
							<option value="1">未见异常</option>
							<option value="2">异常</option>
						</select>
					</div>
					<div class="layui-input-inline" style="display: inline-block" >
						<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="earqk02" name="earqk02" pofield="earqk02">
					</div>
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">听力</label>
				<div class="layui-input-inline">
					<div class="layui-input-inline">
						<select name="tl0000" pofield="tl0000" id="tl0000" disabled="disabled">
							<option value="1">通过</option>
							<option value="2">未通过</option>
						</select>
					</div>
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">口腔</label>
				<div class="layui-input-inline">
					<div class="layui-input-inline">
						<select name="kqqk00" pofield="kqqk00" id="kqqk00" disabled="disabled">
							<option value="1">未见异常</option>
							<option value="2">异常</option>
						</select>
					</div>
					<div class="layui-input-inline" style="display: inline-block" >
						<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="kqqk02" name="kqqk02" pofield="kqqk02">
					</div>
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label" style="width: 150px">牙齿/龋齿数（颗）</label>
				<div class="layui-input-inline">
					<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="kqqk00" name="kqqk00" pofield="kqqk00"  >
				</div>
				<span style="display: inline-block; font-size:20px;position: relative;left:0px;top: 3px">/</span>
				<div class="layui-input-inline">
					<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="kqqkse" name="kqqkse" pofield="kqqkse">
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">心肺</label>
				<div class="layui-input-inline">
					<div class="layui-input-inline">
						<select name="xftz00" pofield="xftz00" id="xftz00" disabled="disabled">
							<option value="1">未见异常</option>
							<option value="2">异常</option>
						</select>
					</div>
					<div class="layui-input-inline" style="display: inline-block" >
						<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="xftz02" name="xftz02" pofield="xftz02">
					</div>
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">腹部</label>
				<div class="layui-input-inline">
					<div class="layui-input-inline">
						<select name="fbcz00" pofield="fbcz00" id="fbcz00" disabled="disabled">
							<option value="1">未见异常</option>
							<option value="2">异常</option>
						</select>
					</div>
					<div class="layui-input-inline" style="display: inline-block" >
						<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="fbcz02" name="fbcz02" pofield="fbcz02">
					</div>
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">四肢</label>
				<div class="layui-input-inline">
					<div class="layui-input-inline">
						<select name="szhdqk" pofield="szhdqk" id="szhdqk" disabled="disabled">
							<option value="1">未见异常</option>
							<option value="2">异常</option>
						</select>
					</div>
					<div class="layui-input-inline" style="display: inline-block" >
						<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="szhdqk02" name="szhdqk02" pofield="szhdqk02">
					</div>
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">步态</label>
				<div class="layui-input-inline">
					<div class="layui-input-inline">
						<select name="btqk00" pofield="btqk00" id="btqk00" disabled="disabled">
							<option value="1">未见异常</option>
							<option value="2">异常</option>
						</select>
					</div>
					<div class="layui-input-inline" style="display: inline-block" >
						<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="btqk02" name="btqk0" pofield="btqk02">
					</div>
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">可疑佝偻病体征</label>
				<div class="layui-input-inline">
					<div class="layui-input-inline">
						<select name="glbtz0" pofield="glbtz0" id="glbtz0" disabled="disabled">
							<option value="1">无</option>
							<option value="2">颅骨软化</option>
							<option value="3">方颅</option>
							<option value="4">枕秃</option>
							<option value="5">肋串珠</option>
							<option value="6">肋外翻</option>
							<option value="7">肋软骨沟</option>
							<option value="8">鸡胸</option>
							<option value="9">手镯征</option>
							<option value="10">o型腿</option>
							<option value="11">X型腿</option>
						</select>
					</div>
				</div>
			</div>

				<div class="layui-inline">
					<label class="layui-form-label">血红蛋白</label>
					<div class="layui-input-inline">
						<input   type="text" autocomplete="off" class="layui-input" id="xhdb00" name="xhdb00" pofield="xhdb00" readonly="readonly">
					</div>
					<span style="display: inline-block; font-size:15px;">g/L</span>
				</div>
			<div class="layui-inline">
				<label class="layui-form-label">中重度贫血</label>
				<div class="layui-input-inline">
					<div class="layui-input-inline">
						<select name="zzdpx0" pofield="zzdpx0" id="zzdpx0" disabled="disabled">
							<option value="0">否</option>
							<option value="1">是</option>
						</select>
					</div>
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">轻度贫血</label>
				<div class="layui-input-inline">
					<div class="layui-input-inline">
						<select name="qdpx00" pofield="qdpx00" id="qdpx00" disabled="disabled">
							<option value="0">否</option>
							<option value="1">是</option>
						</select>
					</div>
				</div>
			</div>
				<div class="layui-inline">
					<label class="layui-form-label">户外运动</label>
					<div class="layui-input-inline">
						<input   type="text" autocomplete="off" class="layui-input" id="hwhdqk" name="hwhdqk" pofield="hwhdqk" readonly="readonly">
					</div>
					<span style="display: inline-block; font-size:15px;">小时/日</span>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">服用维生素D</label>
					<div class="layui-input-inline">
						<input   type="text" autocomplete="off" class="layui-input" id="wss000" name="wss000" pofield="wss000" readonly="readonly">
					</div>
					<span style="display: inline-block; font-size:15px;">IU/日</span>
				</div>
			<div class="layui-inline">
				<label class="layui-form-label">发育评估</label>
				<div class="layui-input-inline">
					<div class="layui-input-inline">
						<select name="fypgqk" pofield="fypgqk" id="fypgqk" disabled="disabled">
							<option value="1">通过</option>
							<option value="2">未通过</option>
						</select>
					</div>
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">随访期间患病情况</label>
				<div class="layui-input-inline">
					<div class="layui-input-inline">
						<select name="hbqk00" pofield="hbqk00" id="hbqk00" disabled="disabled">
							<option value="1">未患病</option>
							<option value="2">患病</option>
						</select>
					</div>
					<div class="layui-inline">
						<label class="layui-form-label">其他</label>
						<div class="layui-input-inline">
							<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="qtfshb1" name="qtfs00" pofield="qtfs00">
						</div>
					</div>
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">转诊结果</label>
				<div class="layui-input-inline">
					<select name="zzqk00" pofield="zzqk00" id="zzqk00" disabled="disabled">
						<option value="">--请选择--</option>
						<option value="1">无</option>
						<option value="2">有</option>
					</select>
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">指导</label>
				<div class="layui-input-inline">
					<div class="layui-input-inline">
						<select name="zdqk00" pofield="zdqk00" id="zdqk00" disabled="disabled">
							<option value="1">科学喂养</option>
							<option value="2">生长发育</option>
							<option value="3">疾病预防</option>
							<option value="4">预防意外伤害</option>
							<option value="5">口腔保健</option>
							<option value="6">其他</option>
						</select>
					</div>
					<div class="layui-input-inline" style="display: inline-block" >
						<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="earqk780" name="earqk0" pofield="earqk0">
					</div>
				</div>
			</div>
				<fieldset class="layui-elem-field" style="margin-top: 20px;">
					<legend>中医健康指导</legend>
				    <div class="layui-form-item">
						<div class="layui-inline">
							<label class="layui-form-label">中医饮食调养</label>
							<div class="layui-input-inline">
								<textarea cols="80" id="ysty00" name="ysty00" pofield="ysty00"></textarea>
							</div>
						</div>
						<div class="layui-form-item"></div>
						<div class="layui-inline">
							<label class="layui-form-label">中医起居调摄</label>
							<div class="layui-input-inline">
								<textarea cols="80" id="qjts00" name="qjts00" pofield="ysty00"></textarea>
							</div>
						</div>
						<div class="layui-form-item"></div>
						<div class="layui-inline">
							<label class="layui-form-label">传授穴位按揉方法</label>
							<div class="layui-input-inline">
								<textarea cols="80" id="xwarff" name="xwarff" pofield="xwarff"></textarea>
							</div>
						</div>
					</div>
				</fieldset>
			<div class="layui-inline">
				<label class="layui-form-label" style="width:250px">家长掌握中医药保健知识和操作方法情况</label>
					<div class="layui-input-inline">
						<select name="zdqk00" pofield="zdqk00" id="zdqk00" disabled="disabled">
							<option value="1">完全</option>
							<option value="2">熟悉</option>
							<option value="3">基本</option>
							<option value="4">困难</option>
						</select>
					</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label" style="width:250px">对医生的中医预防保健是否满意</label>
				<div class="layui-input-inline">
					<select name="myd000" pofield="myd000" id="myd000" disabled="disabled">
						<option value="1">很满意</option>
						<option value="2">比较满意</option>
						<option value="3">一般</option>
						<option value="4">不满意</option>
					</select>
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">备注</label>
				<div class="layui-input-inline">
					<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="bz0000" name="bz0000" pofield="bz0000">
				</div>
			</div><br/>
			<div class="layui-inline">
				<label class="layui-form-label">下次随访日期</label>
				<div class="layui-input-inline">
					<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="xcsfrq" name="xcsfrq" pofield="xcsfrq">
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">下次随访地点</label>
				<div class="layui-input-inline">
					<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="xcsfdd" name="xcsfdd" pofield="xcsfdd">
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">随访医生</label>
				<div class="layui-input-inline">
					<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="sfys00" name="sfys00" pofield="sfys00">
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">随访结果</label>
				<div class="layui-input-inline">
					<select name="sfjg00" pofield="sfjg00" id="sfjg00" disabled="disabled">
						<option value="">--请选择--</option>
						<option value="1">正常</option>
						<option value="2">异常</option>
					</select>
				</div>
			</div>
		</fieldset>
		</div>
		<div id="szls">
		<fieldset class="layui-elem-field" style="margin-top: 20px;">
			<legend>3-6岁儿童健康检测</legend>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">所属幼儿园</label>
					<div class="layui-input-inline">
						<input type="text" autocomplete="off" class="layui-input" id="yeyid0" name="yeyid0" pofield="yeyid0" readonly="readonly">
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">所属班级</label>
					<div class="layui-input-inline">
						<input type="text" autocomplete="off" class="layui-input" id="bjid00" name="bjid00" pofield="bjid00" readonly="readonly">
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">随访日期</label>
					<div class="layui-input-inline">
						<input type="text" autocomplete="off" class="layui-input" id="sfrq00" name="sfrq00" pofield="sfrq00" readonly="readonly">
					</div>
				</div>
				<br/>
				<div class="layui-inline">
					<label class="layui-form-label">月（年）龄</label>
					<div class="layui-input-inline">
						<select name="ssnnz0" pofield="ssnnz0" id="ssnnz0" disabled="disabled">
							<option value="0">新生儿</option>
							<option value="1">1岁以内儿童</option>
							<option value="2">1-2岁儿童</option>
							<option value="3">3-6岁儿童</option>
						</select>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">实足年龄</label>
					<input  style="width: 50px;display: inline-block" type="text" autocomplete="off" class="layui-input" id="etsjss" name="etsjss" pofield="etsjss" readonly="readonly">
					<span style="display: inline-block; font-size:12px;">年</span>
					<input  style="width: 50px;display: inline-block" type="text" autocomplete="off" class="layui-input" id="etsjys" name="etsjys" pofield="etsjys" readonly="readonly">
					<span style="display: inline-block; font-size:12px;">月</span>
					<input  style="width: 50px;display: inline-block" type="text" autocomplete="off" class="layui-input" id="etsjts" name="etsjts" pofield="etsjts" readonly="readonly">
					<span style="display: inline-block; font-size:12px;text-align:center">天</span>
				</div>
				<br/>
				<div class="layui-inline">
					<label class="layui-form-label">是否外出</label>
					<div class="layui-input-inline">
						<div class="layui-input-inline">
							<select name="sfwc00" pofield="sfwc00" id="sfwc00" disabled="disabled">
								<option value="1">是</option>
								<option value="2">否</option>
							</select>
						</div>
					</div>
				</div>
				<br/>
				<div class="layui-inline">
					<label class="layui-form-label">体重</label>
					<div class="layui-input-inline">
						<input   type="text" autocomplete="off" class="layui-input" id="ettz00" name="ettz00" pofield="ettz00" readonly="readonly">
					</div>
					<span style="display: inline-block; font-size:15px;position: relative;left: -110px">kg</span>
					<label class="layui-form-label" style="position: relative;left: 20px">评价</label>
					<div class="layui-input-inline">
						<select name="tzqk00" pofield="tzqk00" id="tzqk00" disabled="disabled">
							<option value="1">上</option>
							<option value="2">中</option>
							<option value="3">下</option>
						</select>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">身高</label>
					<div class="layui-input-inline">
						<input   type="text" autocomplete="off" class="layui-input" id="dqheight1" name="birthday" pofield="birthday" readonly="readonly">
					</div>
					<span style="display: inline-block; font-size:15px;position: relative;left: -110px">cm</span>
					<label class="layui-form-label" style="position: relative;left: 20px">评价</label>
					<div class="layui-input-inline">
						<select name="sgqk00" pofield="sgqk00" id="sgqk00" disabled="disabled">
							<option value="1">上</option>
							<option value="2">中</option>
							<option value="3">下</option>
						</select>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">标准体重</label>
					<div class="layui-input-inline">
						<input   type="text" autocomplete="off" class="layui-input" id="bztz00" name="bztz00" pofield="bztz00" readonly="readonly">
					</div>
					<span style="display: inline-block; font-size:15px;">kg</span>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">标准身高</label>
					<div class="layui-input-inline">
						<input   type="text" autocomplete="off" class="layui-input" id="bzsg00" name="bzsg00" pofield="bzsg00" readonly="readonly">
					</div>
					<span style="display: inline-block; font-size:15px;">cm</span>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">体重/身高标准值</label>
					<div class="layui-input-inline">
						<input   type="text" autocomplete="off" class="layui-input" id="tzzsgbz" name="tzzsgbz" pofield="tzzsgbz" readonly="readonly">
					</div>
					<span style="display: inline-block; font-size:15px;">kg</span>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">体重/年龄标准差</label>
					<div class="layui-input-inline">
						<select name="tzpjxx" pofield="tzpjxx" id="tzpjxx" disabled="disabled">
							<option value="">--请选择--</option>
							<option value="-4">-3SD以下</option>
							<option value="-3">-3SD～-2SD</option>
							<option value="-2">-2SD～-1SD</option>
							<option value="-1">-1SD～SD</option>
							<option value="1">SD～+1SD</option>
							<option value="2">+1SD～+2SD</option>
							<option value="3">+2SD～+3SD</option>
							<option value="4">3SD以上</option>
						</select>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">身高/年龄标准差</label>
					<div class="layui-input-inline">
						<select name="sgpjxx" pofield="sgpjxx" id="sgpjxx" disabled="disabled">
							<option value="">--请选择--</option>
							<option value="-4">-3SD以下</option>
							<option value="-3">-3SD～-2SD</option>
							<option value="-2">-2SD～-1SD</option>
							<option value="-1">-1SD～SD</option>
							<option value="1">SD～+1SD</option>
							<option value="2">+1SD～+2SD</option>
							<option value="3">+2SD～+3SD</option>
							<option value="4">3SD以上</option>
						</select>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">体重/身高标准差</label>
					<div class="layui-input-inline">
						<select name="sctzpj" pofield="sctzpj" id="sctzpj" disabled="disabled">
							<option value="">--请选择--</option>
							<option value="-4">-3SD以下</option>
							<option value="-3">-3SD～-2SD</option>
							<option value="-2">-2SD～-1SD</option>
							<option value="-1">-1SD～SD</option>
							<option value="1">SD～+1SD</option>
							<option value="2">+1SD～+2SD</option>
							<option value="3">+2SD～+3SD</option>
							<option value="4">3SD以上</option>
						</select>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">蛋白质能力营养不良</label>
					<div class="layui-input-inline">
						<select name="zdjgyy" pofield="zdjgyy" id="zdjgyy" disabled="disabled">
							<option value="">--请选择--</option>
							<option value="1">轻度</option>
							<option value="2">中度(低体重)</option>
							<option value="3">中度(发育迟缓)</option>
							<option value="4">重度(低体重)</option>
							<option value="5">重度(发育迟缓)</option>
						</select>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">营养过剩评价</label>
					<div class="layui-input-inline">
						<select name="zdjgyy" pofield="zdjgyy" id="zdjgyy" disabled="disabled">
							<option value="">--请选择--</option>
							<option value="0">正常</option>
							<option value="1">超重</option>
							<option value="2">肥胖</option>
						</select>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">体格发育评价</label>
					<div class="layui-input-inline">
						<select name="tgfypj" pofield="tgfypj" id="tgfypj" disabled="disabled">
							<option value="">--请选择--</option>
							<option value="1">正常</option>
							<option value="2">低体重</option>
							<option value="3">消瘦</option>
							<option value="4">发育迟缓</option>
							<option value="5">超重</option>
							<option value="6">肥胖</option>
						</select>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">营养评价</label>
					<div class="layui-input-inline">
						<select name="yypj00" pofield="yypj00" id="yypj00" disabled="disabled">
							<option value="">--请选择--</option>
							<option value="1">差</option>
							<option value="2">中</option>
							<option value="3">好</option>
						</select>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">视力</label>
					<span style="display: inline-block; font-size:15px;position: relative;left: -200px">左</span>
					<div class="layui-input-inline">
						<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="sl0000" name="sl0000" pofield="sl0000">
					</div>
					<span style="display: inline-block; font-size:15px;">右</span>
					<div class="layui-input-inline">
						<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="sly000" name="sly000" pofield="sly000" style="position: relative;left: 20px">
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">听力</label>
					<div class="layui-input-inline">
						<div class="layui-input-inline">
							<select name="tl0000" pofield="tl0000" id="tl0000" disabled="disabled">
								<option value="1">通过</option>
								<option value="2">未通过</option>
							</select>
						</div>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label" style="width: 150px">牙齿/龋齿数（颗）</label>
					<div class="layui-input-inline">
						<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="kqqk00" name="kqqk00" pofield="kqqk00"  >
					</div>
					<span style="display: inline-block; font-size:20px;position: relative;left:-205px;top: 3px">/</span>
					<div class="layui-input-inline">
						<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="kqqkse" name="kqqkse" pofield="kqqkse" >
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">口腔</label>
					<div class="layui-input-inline">
						<div class="layui-input-inline">
							<select name="kqqk00" pofield="kqqk00" id="kqqk00" disabled="disabled">
								<option value="1">未见异常</option>
								<option value="2">异常</option>
							</select>
						</div>
						<div class="layui-input-inline" style="display: inline-block" >
							<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="kqqk02" name="kqqk02" pofield="kqqk02">
						</div>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">心肺</label>
					<div class="layui-input-inline">
						<div class="layui-input-inline">
							<select name="xftz00" pofield="xftz00" id="xftz00" disabled="disabled">
								<option value="1">未见异常</option>
								<option value="2">异常</option>
							</select>
						</div>
						<div class="layui-input-inline" style="display: inline-block" >
							<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="xftz02" name="xftz02" pofield="xftz02">
						</div>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">腹部</label>
					<div class="layui-input-inline">
						<div class="layui-input-inline">
							<select name="fbcz00" pofield="fbcz00" id="fbcz00" disabled="disabled">
								<option value="1">未见异常</option>
								<option value="2">异常</option>
							</select>
						</div>
						<div class="layui-input-inline" style="display: inline-block" >
							<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="fbcz02" name="fbcz02" pofield="fbcz02">
						</div>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">血红蛋白</label>
					<div class="layui-input-inline">
						<input   type="text" autocomplete="off" class="layui-input" id="xhdb00" name="xhdb00" pofield="xhdb00" readonly="readonly">
					</div>
					<span style="display: inline-block; font-size:15px;">g/L</span>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">中重度贫血</label>
					<div class="layui-input-inline">
						<div class="layui-input-inline">
							<select name="zzdpx0" pofield="zzdpx0" id="zzdpx0" disabled="disabled">
								<option value="0">否</option>
								<option value="1">是</option>
							</select>
						</div>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">轻度贫血</label>
					<div class="layui-input-inline">
						<div class="layui-input-inline">
							<select name="qdpx00" pofield="qdpx00" id="qdpx00" disabled="disabled">
								<option value="0">否</option>
								<option value="1">是</option>
							</select>
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">随访期间患病情况</label>
					<div class="layui-input-block">
						<div class="layui-input-inline">
							<select name="hbqk00" pofield="hbqk00" id="hbqk00" disabled="disabled">
								<option value="1">未患病</option>
								<option value="2">患病</option>
							</select>
						</div><br/>
						<div class="layui-inline">
							<label class="layui-form-label">肺炎</label>
							<div class="layui-input-inline">
								<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="hbqkfy" name="hbqkfy" pofield="hbqkfy">
							</div>
						</div>
						<div class="layui-inline">
							<label class="layui-form-label">腹泻</label>
							<div class="layui-input-inline">
								<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="hbqkfx" name="hbqkfx" pofield="hbqkfx">
							</div>
						</div>
						<div class="layui-inline">
							<label class="layui-form-label">外伤</label>
							<div class="layui-input-inline">
								<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="hbqkws" name="hbqkws" pofield="hbqkws">
							</div>
						</div>
						<div class="layui-inline">
							<label class="layui-form-label">其他</label>
							<div class="layui-input-inline">
								<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="hbqkqt" name="hbqkqt" pofield="hbqkqt">
							</div>
						</div>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">转诊结果</label>
					<div class="layui-input-inline">
						<select name="zzqk00" pofield="zzqk00" id="zzqk00" disabled="disabled">
							<option value="">--请选择--</option>
							<option value="1">无</option>
							<option value="2">有</option>
						</select>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">指导</label>
					<div class="layui-input-inline">
						<div class="layui-input-inline">
							<select name="zdqk00" pofield="zdqk00" id="zdqk00" disabled="disabled">
								<option value="1">科学喂养</option>
								<option value="2">生长发育</option>
								<option value="3">疾病预防</option>
								<option value="4">预防意外伤害</option>
								<option value="5">口腔保健</option>
								<option value="6">其他</option>
							</select>
						</div>
						<div class="layui-input-inline" style="display: inline-block" >
							<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="earqk780" name="earqk0" pofield="earqk0">
						</div>
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">备注</label>
					<div class="layui-input-inline">
						<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="bz0000" name="bz0000" pofield="bz0000">
					</div>
				</div><br/>
				<div class="layui-inline">
					<label class="layui-form-label">随访医生</label>
					<div class="layui-input-inline">
						<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="sfys00" name="sfys00" pofield="sfys00">
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">随访结果</label>
					<div class="layui-input-inline">
						<select name="sfjg00" pofield="sfjg00" id="sfjg00" disabled="disabled">
							<option value="">--请选择--</option>
							<option value="1">正常</option>
							<option value="2">异常</option>
						</select>
					</div>
				</div>
			</div>
		</fieldset>
		</div>
	</form>
</body>
<script type="text/javascript" src="js/view_followRecordsAndHealth_detail.js?v=1.0"></script>
</html>
