<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>

<head>
<%@ include file="/open/commonjs/tldLayui.jsp"%>
<title>孕中期随访记录详情</title>
</head>

	<body>
		<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
			<legend>孕中期随访记录详情</legend>
		</fieldset>
		<form class="layui-form layui-form-pane" id="view_cqsf_detail">
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">孕妇末次月经日期</label>
					<div class="layui-input-inline" >
						<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="" name="" pofield="">
					</div>
				</div>
					<div class="layui-inline" >
						<label class="layui-form-label" style="width: 200px">该记录是否由外单位导入</label>
						<div class="layui-input-inline" style="width:110px">
							<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="drwdw0" name="drwdw0" pofield="drwdw0">
						</div>
					</div>
			</div>

			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">
						<span class="layui-badge-dot" > </span>随访日期
					</label>
					<div class="layui-input-inline" >
						<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="sfrq00" name="sfrq00" pofield="sfrq00">
					</div>
				</div>
				<div class="layui-inline" >
					<label class="layui-form-label">
						<span class="layui-badge-dot"> </span>次别
					</label>
					<div class="layui-input-inline" style="width:110px">
						<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="cbbj00" name="cbbj00" pofield="cbbj00">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label"><span class="layui-badge-dot"> </span>孕期</label>
					<input name="yz0000" pofield="yz0000" id="yz0000"  style="width: 50px;height:22px; "type="text" readonly="readonly" autocomplete="off">周
					<input name="yzts00" pofield="yzts00" id="yzts00" style="width: 50px;height:22px; " type="text" readonly="readonly" autocomplete="off">天
				</div>

				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">主诉</label>
						<div class="layui-input-inline" >
							<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="zs0000" name="zs0000" pofield="zs0000">
						</div>
					</div>
					<div class="layui-inline" >
						<label class="layui-form-label" style="width: 200px">体重(Kg)</label>
						<div class="layui-input-inline" style="width:110px">
							<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="tz0000" name="tz0000" pofield="tz0000">
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">宫颈高度(cm)</label>
						<div class="layui-input-inline" >
							<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="ckjcgg" name="ckjcgg" pofield="ckjcgg">
						</div>
					</div>
					<div class="layui-inline" >
						<label class="layui-form-label" style="width: 200px">腹围(cm)</label>
						<div class="layui-input-inline" style="width:110px">
							<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="ckjcfw" name="ckjcfw" pofield="ckjcfw">
						</div>
					</div>
				</div>


				<div class="layui-form-item">
					<label class="layui-form-label">胎位及心率</label>
					<div class="layui-input-block">
						&nbsp;&nbsp;&nbsp;胎位
						<div class="layui-inline" >
							<div class="layui-input-inline" style="width: 120px;">
								<input type="text"  autocomplete="off" class="layui-input" readonly="readonly" id="ckjctw" name="ckjctw" pofield="ckjctw">
							</div>
						</div>
						&nbsp;&nbsp;&nbsp;胎心率
						<div class="layui-inline">
							<div class="layui-input-inline" style="width: 120px;">
								<input type="text"  autocomplete="off" class="layui-input" readonly="readonly" id="ckjctx" name="ckjctx" pofield="ckjctx">
							</div>
						</div>(次/分钟)
					</div>
				</div>

				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">血压(mmHg)</label>
						<div class="layui-input-inline" >
							<input type="text" autocomplete="off"  readonly="readonly" id="xyssy0" name="xyssy0" pofield="xyssy0" style="width: 50px;height:22px;" > /
							<input type="text" autocomplete="off"  readonly="readonly" id="xyszy0" name="xyszy0" pofield="xyszy0" style="width: 50px;height:22px;" >
						</div>
					</div>
					<div class="layui-inline" >
						<label class="layui-form-label" style="width: 200px">血红蛋白(g/L)</label>
						<div class="layui-input-inline" style="width:110px">
							<input type="text"  autocomplete="off" class="layui-input" readonly="readonly" id="xhdb00" name="xhdb00" pofield="xhdb00">
						</div>
					</div>
				</div>


				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">尿蛋白</label>
						<div class="layui-input-inline" >
							<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="ndb000" name="ndb000" pofield="ndb000">
						</div>
					</div>
					<div class="layui-inline" >
						<label class="layui-form-label" style="width: 200px">其他辅助检查</label>
						<div class="layui-input-inline" style="width:110px">
							<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="qtfzjc" name="qtfzjc" pofield="qtfzjc">
						</div>
					</div>
				</div>

				<div class="layui-form-item">
					<label class="layui-form-label" style="width:180px;margin-top:10px">分类</label>
					<div class="layui-input-block">
						<input type="checkbox" name="fl0000" lay-skin="primary" title="未见异常" id="fl0000" pofield="fl0000" value="1" disabled="disabled">
						<input type="checkbox" name="fl0000" lay-skin="primary" title="异常" id="fl0000" pofield="fl0000" value="2" disabled="disabled">
						<div class="layui-inline" style="margin-top:10px">
							<div class="layui-input-inline">
								<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="classifyOther" name="classifyOther" pofield="classifyOther">
							</div>
						</div>
					</div>
				</div>


				<div class="layui-form-item">
					<label class="layui-form-label" style="margin-top:10px">指导</label>
					<div class="layui-input-block">
						<input type="checkbox" name="zd0000" title="个人卫生" lay-skin="primary" id="zd0000" pofield="zd0000" value="1" disabled="disabled" >
						<input type="checkbox" name="zd0000" title="膳食" lay-skin="primary"  id="zd0000" pofield="zd0000" value="2" disabled="disabled">
						<input type="checkbox" name="zd0000" title="心理" lay-skin="primary" id="zd0000" pofield="zd0000" value="3" disabled="disabled">
						<input type="checkbox" name="zd0000" title="运动" lay-skin="primary" id="zd0000" pofield="zd0000" value="4" disabled="disabled">
						<input type="checkbox" name="zd0000" title="自我监测" lay-skin="primary" id="zd0000" pofield="zd0000" value="5" disabled="disabled">
						<input type="checkbox" name="zd0000" title="分娩准备" lay-skin="primary" id="zd0000" pofield="zd0000" value="6" disabled="disabled">
						<input type="checkbox" name="zd0000" title="母乳喂养" lay-skin="primary" id="zd0000" pofield="zd0000" value="7" disabled="disabled">
						<input type="checkbox" name="zd0000" title="其他" lay-skin="primary" id="zd0000" pofield="zd0000" value="8" disabled="disabled">
						<div class="layui-inline" style="margin-top:10px">
							<div class="layui-input-inline">
								<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="guideOther" name="guideOther" pofield="guideOther">
							</div>
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label" style="margin-top:5px">转诊</label>
					<div class="layui-input-block" >
						<input type="checkbox" name="zz0000" title="无" lay-skin="primary"  id="zz0000" value="0" pofield="zz0000" disabled>
						<input type="checkbox" name="zz0000" title="有" lay-skin="primary"  id="zz0000" value="1" pofield="zz0000" disabled>
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

				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">下次访视日期</label>
						<div class="layui-input-inline" >
							<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="xcsfrq" name="xcsfrq" pofield="xcsfrq">
						</div>
					</div>
					<div class="layui-inline" >
						<label class="layui-form-label" style="width: 200px">随访医生</label>
						<div class="layui-input-inline" style="width:110px">
							<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="sfysqm" name="sfysqm" pofield="sfysqm">
						</div>
					</div>
				</div>

				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">建档单位</label>
						<div class="layui-input-inline" >
							<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="" name="" pofield="">
						</div>
					</div>
					<div class="layui-inline">
						<label class="layui-form-label">是否外出</label>
						<div class="layui-input-inline" >
							<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="isout" name="isout" pofield="isout">
						</div>
					</div>
				</div>
				<div class="layui-form-item">
				<div class="layui-inline" >
					<label class="layui-form-label" >备注</label>
					<div class="layui-input-inline" style="width:512px">
						<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="outbz0" name="outbz0" pofield="outbz0">
					</div>
				</div>
			</div>
			</div>
		</form>
	</body>
	<script type="text/javascript" src="js/view_cqsf_detail.js?v=1.0"></script>
	</html>