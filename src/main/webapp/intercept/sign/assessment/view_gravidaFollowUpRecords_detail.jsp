<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>

<head>
<%@ include file="/open/commonjs/tldLayui.jsp"%>
<title>孕产期保健管理</title>
</head>

	<body>
		<input type="hidden" val="test" />
		<div id="btns"></div>
		<div align="center"></div>
		<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
			<legend>孕妇保健手册</legend>
		</fieldset>
		<form class="layui-form layui-form-pane" id="electronicArchivesForm">
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">手册编号</label>
					<div class="layui-input-inline">
						<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="" name="" pofield="">
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">是否高危</label>
					<div class="layui-input-inline">
						<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="" name="" pofield="">
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">手册状态</label>
					<div class="layui-input-inline">
						<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="state" name="state" pofield="state">
					</div>
				</div>
			</div>

			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">姓名</label>
					<div class="layui-input-inline">
						<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="name" name="name" pofield="name">
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">联系电话</label>
					<div class="layui-input-inline">
						<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="telphone" name="telphone" pofield="telphone">
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">孕妇状态</label>
					<div class="layui-input-inline">
						<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="" name="" pofield="">
					</div>
				</div>
			</div>
		</form>
		<div class="layui-tab">
			<ul class="layui-tab-title">
				<li class="layui-this">基本信息</li>
				<li>第1次产前随访</li>
				<li>第2~5次产前随访</li>
				<li>保健卡</li>
				<li>异常及高危处理</li>
				<li>分娩记录</li>
				<li>产后访视</li>
				<li>产后42天访视</li>
				<li>调阅妇幼平台</li>
			</ul>
			<div class="layui-tab-content">
				<!-- 1.0基本信息 -->
				<div class="layui-tab-item layui-show">
					<fieldset class="layui-elem-field " style="margin-top: 20px;padding: 5px;">
						<legend>孕产妇基本信息</legend>
						<div style="background-color: #cad7ec;text-align: center;padding: 5px;color: white;margin-bottom: 5px;">手册信息</div>
						<div class="layui-form-item layui-form">
							<div class="layui-inline">
								<label class="layui-form-label">手册编号</label>
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="" name="" pofield="">
								</div>
							</div>
							<div class="layui-inline">
								<label class="layui-form-label">
									<span class="layui-badge-dot"> </span> 手册状态</label>
								<div class="layui-input-inline">
									<div class="layui-input-inline">
										<select name="rprtype" pofield="rprtype" id="state" style="width: 100px;">
											<option value="">使用中</option>
											<option value="1">+</option>
											<option value="2">++</option>
										</select>
									</div>
								</div>
							</div>
							<div class="layui-inline">
								<label class="layui-form-label">
									<span class="layui-badge-dot"> </span> 孕妇状态</label>
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="" name="" pofield="">
								</div>
							</div>
							<div class="layui-inline">
								<label class="layui-form-label">
									<span class="layui-badge-dot"> </span> 建档日期</label>
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="jdrq00" name="jdrq00" pofield="jdrq00">
								</div>
							</div>
						</div>
						<div class="layui-form-item layui-form">
							<div class="layui-inline">
								<label class="layui-form-label">
									<span class="layui-badge-dot"> </span> 是否高危</label>
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="" name="" pofield="">
								</div>
							</div>
							<div class="layui-inline">
								<label class="layui-form-label">卡类型</label>
								<div class="layui-input-inline">
									<div class="layui-input-inline">
										<select name="rprtype" pofield="rprtype" id="state" style="width: 100px;">
											<option value="">市民健康卡</option>
											<option value="1">+</option>
											<option value="2">++</option>
										</select>
									</div>
								</div>
							</div>
							<div class="layui-inline">
								<label class="layui-form-label">卡号</label>
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="" name="" pofield="">
								</div>
							</div>
							<div class="layui-inline">
								<label class="layui-form-label">备注</label>
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="jdrq00" name="jdrq00" pofield="jdrq00">
								</div>
							</div>
						</div>
						<div style="background-color: #cad7ec;text-align: center;padding: 5px;color: white;margin-bottom: 5px;">个人信息</div>
						<div class="layui-form-item layui-form">
							<div class="layui-inline">
								<label class="layui-form-label">居民档案号</label>
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="" name="" pofield="">
								</div>
							</div>
							<div class="layui-inline">
								<label class="layui-form-label">
									<span class="layui-badge-dot"> </span> 姓名</label>
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="" name="" pofield="">
								</div>
							</div>
							<div class="layui-inline">
								<label class="layui-form-label">
									<span class="layui-badge-dot"> </span> 出生日期 </label>
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="jdrq00" name="jdrq00" pofield="jdrq00">
								</div>
							</div>
							<div class="layui-inline">
								<label class="layui-form-label">民族</label>
								<div class="layui-input-inline">
									<div class="layui-input-inline">
										<select name="rprtype" pofield="rprtype" id="state" style="width: 100px;">
											<option value="">请选择</option>
											<option value="1">+</option>
											<option value="2">++</option>
										</select>
									</div>
								</div>
							</div>
						</div>
						<div style="color: red">在线验证:根据姓名和身份证号到计生网在线提取个人信息</div>
						<div class="layui-form-item layui-form">
							<div class="layui-inline">
								<label class="layui-form-label">
									<span class="layui-badge-dot"> </span> 身份证号</label>
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="" name="" pofield="">
								</div>
							</div>
							<div class="layui-inline">
								<label class="layui-form-label">
									<span class="layui-badge-dot"> </span> 身份证类别</label>
								<div class="layui-input-inline">
									<div class="layui-input-inline">
										<select name="rprtype" pofield="rprtype" id="" style="width: 100px;">
											<option value="">请选择</option>
											<option value="1">+</option>
											<option value="2">++</option>
										</select>
									</div>
								</div>
							</div>
							<div class="layui-inline">
								<label class="layui-form-label">文化程度</label>
								<div class="layui-input-inline">
									<div class="layui-input-inline">
										<select name="rprtype" pofield="rprtype" id="" style="width: 100px;">
											<option value="">请选择</option>
											<option value="1">+</option>
											<option value="2">++</option>
										</select>
									</div>
								</div>
							</div>
							<div class="layui-inline">
								<label class="layui-form-label">职业</label>
								<div class="layui-input-inline">
									<div class="layui-input-inline">
										<select name="rprtype" pofield="rprtype" id="" style="width: 100px;">
											<option value="">请选择</option>
											<option value="1">+</option>
											<option value="2">++</option>
										</select>
									</div>
								</div>
							</div>
						</div>
						<div class="layui-form-item layui-form">
							<div class="layui-inline">
								<label class="layui-form-label">
									<span class="layui-badge-dot"> </span> 联系电话</label>
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="" name="" pofield="">
								</div>
							</div>
							<div class="layui-inline">
								<label class="layui-form-label">
									<span class="layui-badge-dot"> </span> 户口属性</label>
								<div class="layui-input-inline">
									<div class="layui-input-inline">
										<select name="rprtype" pofield="rprtype" id="" style="width: 100px;">
											<option value="">请选择</option>
											<option value="1">+</option>
											<option value="2">++</option>
										</select>
									</div>
								</div>
							</div>
							<div class="layui-inline">
								<label class="layui-form-label">
									<span class="layui-badge-dot"> </span> 居住状态</label>
								<div class="layui-input-inline">
									<div class="layui-input-inline">
										<select name="rprtype" pofield="rprtype" id="" style="width: 100px;">
											<option value="">请选择</option>
											<option value="1">+</option>
											<option value="2">++</option>
										</select>
									</div>
								</div>
							</div>
							<div class="layui-inline">
								<label class="layui-form-label">工作单位</label>
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="" name="" pofield="">
								</div>
							</div>
						</div>
						<div class="layui-form-item layui-form">
							<div class="layui-inline">
								<label class="layui-form-label">孕妇国籍</label>
								<div class="layui-input-inline">
									<div class="layui-input-inline">
										<select name="rprtype" pofield="rprtype" id="" style="width: 100px;">
											<option value="">请选择</option>
											<option value="1">+</option>
											<option value="2">++</option>
										</select>
									</div>
								</div>
							</div>
							<div class="layui-inline">
								<label class="layui-form-label">丈夫姓名
									<span class="layui-badge-dot"> </span>
								</label>
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="" name="" pofield="">
								</div>
							</div>
							<div class="layui-inline">
								<label class="layui-form-label">丈夫国籍</label>
								<div class="layui-input-inline">
									<div class="layui-input-inline">
										<select name="rprtype" pofield="rprtype" id="" style="width: 100px;">
											<option value="">请选择</option>
											<option value="1">+</option>
											<option value="2">++</option>
										</select>
									</div>
								</div>
							</div>
							<div class="layui-inline">
								<label class="layui-form-label">工作单位</label>
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="" name="" pofield="">
								</div>
							</div>
						</div>
						<div class="layui-form-item layui-form">
							<div class="layui-inline">
								<label class="layui-form-label">丈夫身份证号</label>
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="" name="" pofield="">
								</div>
							</div>
							<div class="layui-inline">
								<label class="layui-form-label">丈夫身份证类别</label>
								<div class="layui-input-inline">
									<div class="layui-input-inline">
										<select name="rprtype" pofield="rprtype" id="" style="width: 100px;">
											<option value="">请选择</option>
											<option value="1">+</option>
											<option value="2">++</option>
										</select>
									</div>
								</div>
							</div>
							<div class="layui-inline">
								<label class="layui-form-label">丈夫民族</label>
								<div class="layui-input-inline">
									<div class="layui-input-inline">
										<select name="rprtype" pofield="rprtype" id="" style="width: 100px;">
											<option value="">请选择</option>
											<option value="1">+</option>
											<option value="2">++</option>
										</select>
									</div>
								</div>
							</div>
							<div class="layui-inline">
								<label class="layui-form-label">是否单亲</label>
								<div class="layui-input-block">
									<input type="radio" name="sex" value="是" title="是">
									<input type="radio" name="sex" value="否" title="否" checked>
								</div>
							</div>
						</div>

						<div class="layui-form-item layui-form">
							<div class="layui-inline">
								<label class="layui-form-label">丈夫户口地</label>
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="" name="" pofield="">
								</div>
							</div>
						</div>
						<div style="background-color: #cad7ec;text-align: center;padding: 5px;color: white;margin-bottom: 5px;">地址</div>
						<div class="layui-form-item layui-input-block">
							<div class="layui-inline">
								<label class="layui-form-label ">
									<span class="layui-badge-dot"> </span> 户口地</label>
								<div class="layui-input-block">
									<select name="rprtype" pofield="rprtype" id="" style="width: 125px;height:30px; ">
										<option value="">请选择</option>
										<option value="1">+</option>
										<option value="2">++</option>
									</select>(省)
									<select name="rprtype" pofield="rprtype" id="" style="width: 125px;height:30px; ">
										<option value="">请选择</option>
										<option value="1">+</option>
										<option value="2">++</option>
									</select>(地/区)
									<select name="rprtype" pofield="rprtype" id="" style="width: 125px;height:30px; ">
										<option value="">请选择</option>
										<option value="1">+</option>
										<option value="2">++</option>
									</select>(县/区)
									<select name="rprtype" pofield="rprtype" id="" style="width: 125px;height:30px; ">
										<option value="">请选择</option>
										<option value="1">+</option>
										<option value="2">++</option>
									</select>(乡/镇)
									<select name="rprtype" pofield="rprtype" id="" style="width: 125px;height:30px; ">
										<option value="">请选择</option>
										<option value="1">+</option>
										<option value="2">++</option>
									</select>(村/居)
									<div class="layui-form-item layui-form">
										<div class="layui-inline">
											<label class="layui-form-label">详细</label>
											<div class="layui-input-inline">
												<input type="text" autocomplete="on" class="layui-input" readonly="readonly" id="" name="" pofield="">
											</div>
										</div>
									</div>
								</div>

							</div>
						</div>

						<div class="layui-form-item layui-input-block">
							<div class="layui-inline">
								<label class="layui-form-label ">
									<span class="layui-badge-dot"> </span> 现住地</label>
								<div class="layui-input-block">
									<select name="rprtype" pofield="rprtype" id="" style="width: 125px;height:30px; ">
										<option value="">请选择</option>
										<option value="1">+</option>
										<option value="2">++</option>
									</select>(省)
									<select name="rprtype" pofield="rprtype" id="" style="width: 125px;height:30px; ">
										<option value="">请选择</option>
										<option value="1">+</option>
										<option value="2">++</option>
									</select>(地/区)
									<select name="rprtype" pofield="rprtype" id="" style="width: 125px;height:30px; ">
										<option value="">请选择</option>
										<option value="1">+</option>
										<option value="2">++</option>
									</select>(县/区)
									<select name="rprtype" pofield="rprtype" id="" style="width: 125px;height:30px; ">
										<option value="">请选择</option>
										<option value="1">+</option>
										<option value="2">++</option>
									</select>(乡/镇)
									<select name="rprtype" pofield="rprtype" id="" style="width: 125px;height:30px; ">
										<option value="">请选择</option>
										<option value="1">+</option>
										<option value="2">++</option>
									</select>(村/居)
									<div class="layui-form-item layui-form">
										<div class="layui-inline">
											<label class="layui-form-label">详细</label>
											<div class="layui-input-inline">
												<input type="text" autocomplete="on" class="layui-input" readonly="readonly" id="" name="" pofield="">
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="layui-form-item layui-input-block">
							<div class="layui-inline">
								<label class="layui-form-label ">产后修养地</label>
								<div class="layui-input-block">
									<select name="rprtype" pofield="rprtype" id="" style="width: 125px;height:30px; ">
										<option value="">请选择</option>
										<option value="1">+</option>
										<option value="2">++</option>
									</select>(省)
									<select name="rprtype" pofield="rprtype" id="" style="width: 125px;height:30px; ">
										<option value="">请选择</option>
										<option value="1">+</option>
										<option value="2">++</option>
									</select>(地/区)
									<select name="rprtype" pofield="rprtype" id="" style="width: 125px;height:30px; ">
										<option value="">请选择</option>
										<option value="1">+</option>
										<option value="2">++</option>
									</select>(县/区)
									<select name="rprtype" pofield="rprtype" id="" style="width: 125px;height:30px; ">
										<option value="">请选择</option>
										<option value="1">+</option>
										<option value="2">++</option>
									</select>(乡/镇)
									<select name="rprtype" pofield="rprtype" id="" style="width: 125px;height:30px; ">
										<option value="">请选择</option>
										<option value="1">+</option>
										<option value="2">++</option>
									</select>(村/居)
									<div class="layui-form-item layui-form">
										<div class="layui-inline">
											<label class="layui-form-label">详细</label>
											<div class="layui-input-inline">
												<input type="text" autocomplete="on" class="layui-input" readonly="readonly" id="" name="" pofield="">
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</fieldset>
				</div>
				<!-- 2.0第一次产前随访记录 -->
				<div class="layui-tab-item">
					<fieldset class="layui-elem-field " style="margin-top: 20px;padding: 5px;">
						<legend>第一次产前随访记录</legend>
						<div class="layui-form-item layui-form">
							<div class="layui-inline">
								<label class="layui-form-label">
									<span class="layui-badge-dot"> </span> 填表日期</label>
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="" name="" pofield="">
								</div>
							</div>
							<div class="layui-inline">
								<label class="layui-form-label">建档单位</label>
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="" name="" pofield="">
								</div>
							</div>
						</div>
						<div class="layui-form-item layui-form">
							<div class="layui-inline">
								<label class="layui-form-label">
									<span class="layui-badge-dot"> </span> 孕妇年龄</label>
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="" name="" pofield="">
								</div>
							</div>
							<div class="layui-inline">
								<label class="layui-form-label">
									<span class="layui-badge-dot"> </span> 填表孕周</label>
								<input name="rprtype" pofield="rprtype" id="" style="width: 78px;height:20px; ">周
								<input name="rprtype" pofield="rprtype" id="" style="width: 78px;height:20px; ">天
							</div>
						</div>
						<div class="layui-form-item layui-form">
							<div class="layui-inline">
								<label class="layui-form-label">丈夫姓名</label>
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="" name="" pofield="">
								</div>
							</div>

							<div class="layui-inline">
								<label class="layui-form-label">丈夫年龄 </label>
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="" name="" pofield="">
								</div>
							</div>
						</div>
						<div class="layui-form-item layui-form">
							<div class="layui-inline">
								<label class="layui-form-label">孕次</label>
								<div class="layui-input-inline">
									<input name="rprtype" pofield="rprtype" id="" style="width: 78px;height:20px; "> 次
								</div>
							</div>
							<div class="layui-inline">
								<label class="layui-form-label">产次:</label>
								阴道分娩
								<input name="rprtype" pofield="rprtype" id="" style="width: 78px;height:20px; ">次 剖腹产
								<input name="rprtype" pofield="rprtype" id="" style="width: 78px;height:20px; ">次
							</div>
						</div>
						<div class="layui-form-item layui-form">
							<div class="layui-inline">
								<label class="layui-form-label">
									<span class="layui-badge-dot"> </span> 末次月经</label>
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="" name="" pofield="">
								</div>
							</div>
							<div class="layui-inline">
								<label class="layui-form-label">预产期 </label>
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="" name="" pofield="">
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">既往史</label>
							<div class="layui-input-block">
								<input type="checkbox" name="" title="无" lay-skin="primary" checked>无
								<input type="checkbox" name="" title="心脏病" lay-skin="primary"> 心脏病
								<input type="checkbox" name="" title="肾脏疾病" lay-skin="primary"> 肾脏疾病
								<input type="checkbox" name="" title="肝脏疾病" lay-skin="primary"> 肝脏疾病
								<input type="checkbox" name="" title="高血压" lay-skin="primary"> 高血压
								<input type="checkbox" name="" title="贫血" lay-skin="primary"> 贫血
								<input type="checkbox" name="" title="糖尿病" lay-skin="primary"> 糖尿病
								<input type="checkbox" name="" title="其他" lay-skin="primary"> 其他
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">家族史</label>
							<div class="layui-input-block">
								<input type="checkbox" name="" title="遗传性疾病史" lay-skin="primary">遗传性疾病史
								<input type="checkbox" name="" title="精神疾病史" lay-skin="primary"> 精神疾病史
								<input type="checkbox" name="" title="其他" lay-skin="primary"> 其他
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">个人史</label>
							<div class="layui-input-block">
								<input type="checkbox" name="" title="吸烟" lay-skin="primary">吸烟
								<input type="checkbox" name="" title="饮酒" lay-skin="primary"> 饮酒
								<input type="checkbox" name="" title="服用药物" lay-skin="primary"> 服用药物
								<input type="checkbox" name="" title="接触有毒有害物质" lay-skin="primary"> 接触有毒有害物质
								<input type="checkbox" name="" title="接触放射线" lay-skin="primary"> 接触放射线
								<input type="checkbox" name="" title="其他" lay-skin="primary"> 其他
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">妇科手术史</label>
							<div class="layui-input-block">
								<input type="checkbox" name="" title="无" lay-skin="primary" checked>无
								<input type="checkbox" name="" title="有" lay-skin="primary"> 有
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<div class="layui-inline">
								<label class="layui-form-label">孕产史：</label>
								流产(次)
								<input name="rprtype" pofield="rprtype" id="" style="width: 78px;height:20px; "> 死胎(次)
								<input name="rprtype" pofield="rprtype" id="" style="width: 78px;height:20px; "> 死产（次）
								<input name="rprtype" pofield="rprtype" id="" style="width: 78px;height:20px; "> 新生儿死亡（次）
								<input name="rprtype" pofield="rprtype" id="" style="width: 78px;height:20px; "> 出生缺陷儿
								<input name="rprtype" pofield="rprtype" id="" style="width: 78px;height:20px; ">
							</div>
						</div>
						<div class="layui-form-item layui-form">
							<div class="layui-inline">
								<label class="layui-form-label">身高（cm）</label>
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="" name="" pofield="">
								</div>
							</div>
							<div class="layui-inline">
								<label class="layui-form-label">体重（kg） </label>
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="" name="" pofield="">
								</div>
							</div>
						</div>
						<div class="layui-form-item layui-form">
							<div class="layui-inline">
								<label class="layui-form-label">体重指数</label>
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="" name="" pofield="">
								</div>
							</div>
							<div class="layui-inline">
								<label class="layui-form-label">血压(mmHg)</label>
								<input name="rprtype" pofield="rprtype" id="" style="width: 78px;height:20px; "> /
								<input name="rprtype" pofield="rprtype" id="" style="width: 78px;height:20px; ">
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">听诊</label>
							<div class="layui-input-block">
								心脏：
								<input type="checkbox" name="" title="未见异常" lay-skin="primary" checked>未见异常
								<input type="checkbox" name="" title="异常" lay-skin="primary"> 异常
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
								肺部：
								<input type="checkbox" name="" title="未见异常" lay-skin="primary" checked>未见异常
								<input type="checkbox" name="" title="异常" lay-skin="primary"> 异常
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">妇科检查</label>
							<div class="layui-input-block">
								外阴：
								<input type="checkbox" name="" title="未见异常" lay-skin="primary" checked>未见异常
								<input type="checkbox" name="" title="异常" lay-skin="primary"> 异常
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
								阴道：
								<input type="checkbox" name="" title="未见异常" lay-skin="primary" checked>未见异常
								<input type="checkbox" name="" title="异常" lay-skin="primary"> 异常
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<div class="layui-input-block">
								宫颈：
								<input type="checkbox" name="" title="未见异常" lay-skin="primary" checked>未见异常
								<input type="checkbox" name="" title="异常" lay-skin="primary"> 异常
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
								子宫：
								<input type="checkbox" name="" title="未见异常" lay-skin="primary" checked>未见异常
								<input type="checkbox" name="" title="异常" lay-skin="primary"> 异常
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<div class="layui-input-block">
								附件：
								<input type="checkbox" name="" title="未见异常" lay-skin="primary" checked>未见异常
								<input type="checkbox" name="" title="异常" lay-skin="primary"> 异常
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">血常规</label>
							<div class="layui-input-block">
								血红蛋白值(g/L)
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
								白细胞计数值(X10^9/L)
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
							</div>
							<div class="layui-input-block">
								血小板计数值(X10^9/L)
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
								其他
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<div class="layui-inline">
								<label class="layui-form-label">尿常规</label>
								尿蛋白
								<input name="rprtype" pofield="rprtype" id="" style="width: 78px;height:20px; "> 尿糖
								<input name="rprtype" pofield="rprtype" id="" style="width: 78px;height:20px; "> 尿酮体
								<input name="rprtype" pofield="rprtype" id="" style="width: 78px;height:20px; "> 尿潜血
								<input name="rprtype" pofield="rprtype" id="" style="width: 78px;height:20px; "> 其他
								<input name="rprtype" pofield="rprtype" id="" style="width: 78px;height:20px; ">
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">血型</label>
							<div class="layui-input-block">
								ABO
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
								Rh*
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">血糖*</label>
							<div class="layui-input-block">

								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>mmol/L
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">肝功能*</label>
							<div class="layui-input-block">
								血清谷丙转氨酶(U/L)
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
								血清谷草转氨酶(U/L)
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
							</div>
							<div class="layui-input-block">
								白蛋白(g/L)
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
								总胆红素(umol/L)
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
							</div>
							<div class="layui-input-block">
								直接胆红素(umol/L)
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">肾功能*</label>
							<div class="layui-input-block">
								血清肌酐(umol/L)
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
								血尿素氮(mmol/L)
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">阴道分泌物*</label>
							<div class="layui-input-block">
								<input type="checkbox" name="" title="未见异常" lay-skin="primary" checked>未见异常
								<input type="checkbox" name="" title="滴虫" lay-skin="primary"> 滴虫
								<input type="checkbox" name="" title="假丝酵母菌" lay-skin="primary"> 假丝酵母菌
								<input type="checkbox" name="" title="其他" lay-skin="primary"> 其他
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
							</div>
							<div class="layui-input-block">
								阴道清洁度：
								<input type="checkbox" name="" title="I度" lay-skin="primary">I度
								<input type="checkbox" name="" title="Ⅱ度" lay-skin="primary"> Ⅱ度
								<input type="checkbox" name="" title="Ⅲ度" lay-skin="primary"> Ⅲ度
								<input type="checkbox" name="" title="Ⅳ度" lay-skin="primary"> Ⅳ度
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">乙型肝炎五项</label>
							<div class="layui-input-block">
								乙型肝炎表面抗原
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
								乙型肝炎表面抗体
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
								乙型肝炎e抗原
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
							</div>
							<div class="layui-input-block">
								乙型肝炎e抗体
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
								乙型肝炎核心抗体
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">梅毒血清学试验*</label>
							<div class="layui-input-block">
								<input type="checkbox" name="" title="阴性" lay-skin="primary" checked>阴性
								<input type="checkbox" name="" title="阳性" lay-skin="primary"> 阳性
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">HIV抗体检测*</label>
							<div class="layui-input-block">
								<input type="checkbox" name="" title="阴性" lay-skin="primary" checked>阴性
								<input type="checkbox" name="" title="阳性" lay-skin="primary"> 阳性
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">白带常规</label>
							<div class="layui-input-block">
								<input type="checkbox" name="" title="未见异常" lay-skin="primary">未见异常
							</div>
							<div class="layui-input-block">
								霉菌
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
								滴虫
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
								脓球
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
							</div>
							<div class="layui-input-block">
								淋球菌
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
								BV
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
								其他
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
							</div>
							<div class="layui-input-block">
								阴道清洁度：
								<input type="checkbox" name="" title="I度" lay-skin="primary">I度
								<input type="checkbox" name="" title="Ⅱ度" lay-skin="primary"> Ⅱ度
								<input type="checkbox" name="" title="Ⅲ度" lay-skin="primary"> Ⅲ度
								<input type="checkbox" name="" title="Ⅳ度" lay-skin="primary"> Ⅳ度
								<input type="checkbox" name="" title="其他" lay-skin="primary"> 其他
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">B/彩超*</label>
							<div class="layui-input-block">
								双顶径(mm)
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
								头围(mm)
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
								股骨颈(mm)
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
							</div>
							<div class="layui-input-block">
								腹围(mm)
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
								羊水(mm)
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
								羊水指数(mm)
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
							</div>
							<div class="layui-input-block">
								胎心(次/分)
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
								胎盘成熟度
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
								胎盘附于
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
							</div>
							<div class="layui-input-block">
								胎盘下缘距子宫颈内扣(cm)
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
								脐血流
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
								子宫下段厚度
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
							</div>
							<div class="layui-input-block">
								其他
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
								结论
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">总体评估</label>
							<div class="layui-input-block">
								<input type="checkbox" name="" title="未见异常" lay-skin="primary" checked>未见异常
								<input type="checkbox" name="" title="异常" lay-skin="primary"> 异常
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">保健指导</label>
							<div class="layui-input-block">
								<input type="checkbox" name="" title="个人卫生" lay-skin="primary" checked>个人卫生
								<input type="checkbox" name="" title="心理" lay-skin="primary" checked>心理
								<input type="checkbox" name="" title="营养" lay-skin="primary" checked>营养
								<input type="checkbox" name="" title="避免致畸因素和疾病对胚胎的不良影响" lay-skin="primary" checked>避免致畸因素和疾病对胚胎的不良影响
								<input type="checkbox" name="" title="产前筛查宣传告知" lay-skin="primary" checked> 产前筛查宣传告知
							</div>
							<div class="layui-input-block">
								<input type="checkbox" name="" title="其他" lay-skin="primary"> 其他
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">转诊</label>
							<div class="layui-input-block">
								<input type="checkbox" name="" title="无" lay-skin="primary" checked>无
								<input type="checkbox" name="" title="有" lay-skin="primary">有
							</div>
							<div class="layui-input-block">
								原因：
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
								机构及科室：
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">
								<span class="layui-badge-dot"> </span>下次访视日期</label>
							<div class="layui-input-block">
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
								随访医生
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
							</div>
						</div>
					</fieldset>
				</div>
				<!-- 3.0第2~5次产前随访 -->
				<div class="layui-tab-item">
					<div style="display: flex;">
						<fieldset class="layui-elem-field " style="margin-top: 20px;padding: 5px;flex-basis: 450px">
							<legend>第2~5次产前随访服务记录表</legend>
							<table class="layui-table">
								<thead>
									<tr>
										<th style="text-align: center">次别</th>
										<th style="text-align: center">随访日期</th>
										<th style="text-align: center">检查单位</th>
										<th style="text-align: center">操作</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
								</tbody>
							</table>
						</fieldset>
						<fieldset class="layui-elem-field " style="margin-top: 20px;padding: 5px;width: 50%;flex:1;">
							<legend>产前随访服务记录</legend>
							<div class="layui-form-item">
								<div class="layui-input-block">
									<label class="layui-form-pane" style="color: red;font-weight: 700">未找到孕妇末次月经日期!</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 该记录是否由外单位导入
									<div class="layui-inline">
										<div class="layui-input-inline">
											<input type="text" autocomplete="off" class="layui-input">
										</div>
									</div>
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label">
									<span class="layui-badge-dot"> </span>次别</label>
								<div class="layui-input-block">
									<div class="layui-inline">
										<div class="layui-input-inline">
											<input type="text" autocomplete="off" class="layui-input">
										</div>
									</div>
									<span class="layui-badge-dot"> </span>随访日期
									<div class="layui-inline">
										<div class="layui-input-inline">
											<input type="text" autocomplete="off" class="layui-input">
										</div>
									</div>
									<span class="layui-badge-dot"> </span>孕期
									<input name="rprtype" pofield="rprtype" id="" style="width: 50px;height:20px; ">周
									<input name="rprtype" pofield="rprtype" id="" style="width: 50px;height:20px; ">天
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label">主诉</label>
								<div class="layui-input-block">
									<div class="layui-inline">
										<div class="layui-input-inline">
											<input type="text" autocomplete="off" class="layui-input">
										</div>
									</div>
									体重(Kg)
									<div class="layui-inline">
										<div class="layui-input-inline">
											<input type="text" autocomplete="off" class="layui-input">
										</div>
									</div>
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label">宫颈高度(cm)</label>
								<div class="layui-input-block">
									<div class="layui-inline">
										<div class="layui-input-inline">
											<input type="text" autocomplete="off" class="layui-input">
										</div>
									</div>
									腹围(cm)
									<div class="layui-inline">
										<div class="layui-input-inline">
											<input type="text" autocomplete="off" class="layui-input">
										</div>
									</div>
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label">胎位及心率</label>

								<div class="layui-input-block">
									第一胎情况：胎位
									<div class="layui-inline">
										<div class="layui-input-inline">
											<input type="text" autocomplete="off" class="layui-input">
										</div>
									</div>
									胎心率
									<div class="layui-inline">
										<div class="layui-input-inline">
											<input type="text" autocomplete="off" class="layui-input">
										</div>
									</div>(次/分钟)
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label">血压(mmHg)</label>
								<div class="layui-input-block">
									<input name="rprtype" pofield="rprtype" id="" style="width: 50px;height:20px; "> /
									<input name="rprtype" pofield="rprtype" id="" style="width: 50px;height:20px; ">&nbsp; 血红蛋白(g/L)
									<div class="layui-inline">
										<div class="layui-input-inline">
											<input type="text" autocomplete="off" class="layui-input">
										</div>
									</div>
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label">尿蛋白</label>
								<div class="layui-input-block">
									<div class="layui-inline">
										<div class="layui-input-inline">
											<input type="text" autocomplete="off" class="layui-input">
										</div>
									</div>
									其他辅助检查
									<div class="layui-inline">
										<div class="layui-input-inline">
											<input type="text" autocomplete="off" class="layui-input">
										</div>
									</div>
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label">分类</label>
								<div class="layui-input-block">
									<input type="checkbox" name="" title="未见异常" lay-skin="primary" checked>未见异常
									<input type="checkbox" name="" title="异常" lay-skin="primary"> 异常
									<div class="layui-inline">
										<div class="layui-input-inline">
											<input type="text" autocomplete="off" class="layui-input">
										</div>
									</div>
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label">指导</label>
								<div class="layui-input-block">
									<input type="checkbox" name="" title="个人卫生" lay-skin="primary" checked>个人卫生
									<input type="checkbox" name="" title="膳食" lay-skin="primary" checked>膳食
									<input type="checkbox" name="" title="心理" lay-skin="primary" checked>心理
									<input type="checkbox" name="" title="运动" lay-skin="primary" checked>运动
									<input type="checkbox" name="" title="自我监测" lay-skin="primary" checked>自我监测
									<input type="checkbox" name="" title="分娩准备" lay-skin="primary" checked>分娩准备
									<input type="checkbox" name="" title="母乳喂养" lay-skin="primary" checked> 母乳喂养
								</div>
								<div class="layui-input-block">
									<input type="checkbox" name="" title="其他" lay-skin="primary"> 其他
									<div class="layui-inline">
										<div class="layui-input-inline">
											<input type="text" autocomplete="off" class="layui-input">
										</div>
									</div>
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label">转诊</label>
								<div class="layui-input-block">
									<input type="checkbox" name="" title="无" lay-skin="primary" checked>无
									<input type="checkbox" name="" title="有" lay-skin="primary">有
								</div>
								<div class="layui-input-block">
									原因：
									<div class="layui-inline">
										<div class="layui-input-inline">
											<input type="text" autocomplete="off" class="layui-input">
										</div>
									</div>
									机构及科室：
									<div class="layui-inline">
										<div class="layui-input-inline">
											<input type="text" autocomplete="off" class="layui-input">
										</div>
									</div>
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label">
									<span class="layui-badge-dot"></span>下次访视日期</label>
								<div class="layui-input-block">
									<div class="layui-inline">
										<div class="layui-input-inline">
											<input type="text" autocomplete="off" class="layui-input">
										</div>
									</div>
									随访医生
									<div class="layui-inline">
										<div class="layui-input-inline" style="width: 150px;height:20px; ">
											<input type="text" autocomplete="off" class="layui-input">
										</div>
									</div>
									建档单位
									<div class="layui-inline">
										<div class="layui-input-inline">
											<input type="text" autocomplete="off" class="layui-input">
										</div>
									</div>
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label">是否外出</label>
								<div class="layui-input-block">
									<div class="layui-inline">
										<div class="layui-input-inline">
											<input type="text" autocomplete="off" class="layui-input">
										</div>
									</div>
									备注
									<div class="layui-inline">
										<div class="layui-input-inline">
											<input type="text" autocomplete="off" class="layui-input">
										</div>
									</div>
								</div>
							</div>
						</fieldset>
					</div>
				</div>
				<!-- 4.0保健卡 -->
				<div class="layui-tab-item">
					<fieldset class="layui-elem-field " style="margin-top: 20px;padding: 5px;flex-basis: 450px">
						<legend>孕产妇系统保健卡</legend>
						<div class="layui-form-item">
							<label class="layui-form-label">
								<span class="layui-badge-dot"></span>日期</label>
							<div class="layui-input-block">
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
								自觉症状
								<input type="checkbox" name="" title="无" lay-skin="primary" checked> 无
								<input type="checkbox" name="" title="头晕" lay-skin="primary">头晕
								<input type="checkbox" name="" title="头痛" lay-skin="primary">头痛
								<input type="checkbox" name="" title="腹痛" lay-skin="primary">腹痛
								<input type="checkbox" name="" title="阴道流水" lay-skin="primary"> 阴道流水
								<input type="checkbox" name="" title="阴道出血" lay-skin="primary"> 阴道出血
								<input type="checkbox" name="" title="其他" lay-skin="primary"> 其他
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
								<span class="layui-badge-dot"> </span> 孕期
								<input name="rprtype" pofield="rprtype" id="" style="width: 50px;height:20px; ">周
								<input name="rprtype" pofield="rprtype" id="" style="width: 50px;height:20px; ">天
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label">胎动</label>
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
								体重（Kg）
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-inline">血压(mmHg)
									<input name="rprtype" pofield="rprtype" id="" style="width: 50px;height:20px; "> /
									<input name="rprtype" pofield="rprtype" id="" style="width: 50px;height:20px; ">&nbsp;

								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label">
									<span class="layui-badge-dot"> </span> 宫高(cm)</label>
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
								<span class="layui-badge-dot"> </span> 腹围(cm)
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
								先露：
								<input type="checkbox" name="" title="固定" lay-skin="primary">固定
								<input type="checkbox" name="" title="半固定" lay-skin="primary">半固定
								<input type="checkbox" name="" title="浮" lay-skin="primary">浮
								<input type="checkbox" name="" title="头先露" lay-skin="primary">头先露
								<input type="checkbox" name="" title="臀先露" lay-skin="primary">臀先露
								<input type="checkbox" name="" title="肩先露" lay-skin="primary">肩先露
								<input type="checkbox" name="" title="足先露" lay-skin="primary">足先露
								<input type="checkbox" name="" title="不详" lay-skin="primary">不详
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">胎位及心率</label>
							<div class="layui-inline">
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input">
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">浮肿</label>
							<div class="layui-inline">
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input">
								</div>
							</div>
							尿蛋白
							<div class="layui-inline">
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input">
								</div>
							</div>
							血色素(g/L)
							<div class="layui-inline">
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input">
								</div>
							</div>
							其他
							<div class="layui-inline">
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input">
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">高危因素</label>
							<div class="layui-inline">
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input">
								</div>
							</div>
							高危评分
							<div class="layui-inline">
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input">
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">预约日期</label>
							<div class="layui-inline">
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input">
								</div>
							</div>
							治疗及处理意见:
							<input type="checkbox" name="" title="左侧卧位,自测胎动" lay-skin="primary">左侧卧位,自测胎动
							<input type="checkbox" name="" title="吸氧半小时,不适及时就诊" lay-skin="primary">吸氧半小时,不适及时就诊
							<input type="checkbox" name="" title="收住院" lay-skin="primary">收住院
							<input type="checkbox" name="" title="GDM(转综合性医院治疗)" lay-skin="primary">GDM(转综合性医院治疗)
							<input type="checkbox" name="" title="其他" lay-skin="primary">其他
							<div class="layui-inline">
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input">
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">检查护士</label>
							<div class="layui-inline">
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input">
								</div>
							</div>
							检查医生
							<div class="layui-inline">
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input">
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">检查单位</label>
							<div class="layui-inline">
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input">
								</div>
							</div>
						</div>
						<table class="layui-table">
							<thead>
								<tr>
									<th style="text-align: center">日期</th>
									<th style="text-align: center">孕周</th>
									<th style="text-align: center">胎动</th>
									<th style="text-align: center">体重(Kg)</th>
									<th style="text-align: center">宫底高度(cm)</th>
									<th style="text-align: center">腹围(cm)</th>
									<th style="text-align: center">高危评分</th>
									<th style="text-align: center">高危因素</th>
									<th style="text-align: center">预约日期</th>
									<th style="text-align: center">检查单位</th>
									<th style="text-align: center">有无内卡</th>
									<th style="text-align: center">检查类型</th>
								</tr>
							</thead>
							<tbody style="text-align: center">
								<tr>
									<td>2018.07.17</td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
							</tbody>
						</table>
					</fieldset>
				</div>
				<!-- 5.0异常及高危处理 -->
				<div class="layui-tab-item">
					<fieldset class="layui-elem-field " style="margin-top: 20px;padding: 5px;flex-basis: 450px">
						<legend>异常处理及高危专案监护记录表</legend>
						<div class="layui-form-item">
							<label class="layui-form-label">发现日期</label>
							<div class="layui-inline">
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input">
								</div>
							</div>
							<div class="layui-inline">
								<label class="layui-form-label">孕周</label>
								<input name="rprtype" pofield="rprtype" id="" style="width: 78px;height:20px; ">周
								<input name="rprtype" pofield="rprtype" id="" style="width: 78px;height:20px; ">天
							</div>
							高危评分
							<div class="layui-inline">
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input">
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">高危因素</label>
							<div class="layui-inline">
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input">
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form">治疗及处理意见</label>
							<div class="layui-inline">
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input">
								</div>
							</div>
							随访医生
							<div class="layui-inline">
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input">
								</div>
							</div>
						</div>
						<table class="layui-table">
							<thead>
								<tr>
									<th style="text-align: center">发现日期</th>
									<th style="text-align: center">孕周(周)</th>
									<th style="text-align: center">孕周(天)</th>
									<th style="text-align: center">高危因素</th>
									<th style="text-align: center">高危评分</th>
									<th style="text-align: center">治疗及处理意见</th>
									<th style="text-align: center">操作</th>
								</tr>
							</thead>
							<tbody style="text-align: center">
								<tr>
									<td>2018.07.17</td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td>2018.07.17</td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
							</tbody>
						</table>
					</fieldset>
				</div>
				<!-- 6.0分娩记录 -->
				<div class="layui-tab-item">
					<fieldset class="layui-elem-field " style="margin-top: 20px;padding: 5px;flex-basis: 450px">
						<legend>分娩情况记录表</legend>
						<div class="layui-form-item">
							<label class="layui-form-label">
								<span class="layui-badge-dot"> </span> 市外分娩</label>
							<div class="layui-input-block">
								<input type="checkbox" name="" title="否" lay-skin="primary">否
								<input type="checkbox" name="" title="是" lay-skin="primary">是
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">
								<span class="layui-badge-dot"> </span> 分娩日期</label>
							<div class="layui-inline">
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input">
								</div>
							</div>
							分娩孕周
							<div class="layui-inline">
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input">
								</div>
							</div>
							分娩地点
							<div class="layui-inline">
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input">
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">阵缩开始</label>
							<div class="layui-inline">
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input">
								</div>
							</div>
							胎膜破裂
							<div class="layui-inline">
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input">
								</div>
								<div class="layui-input-inline">
									<input type="checkbox" name="" title="自破" lay-skin="primary">自破
									<input type="checkbox" name="" title="人工" lay-skin="primary">人工
								</div>
							</div>
							羊水：
							<div class="layui-inline">
								<div class="layui-input-inline">
									<input type="checkbox" name="" title="清,浊：" lay-skin="primary" checked>清,浊：
									<input type="checkbox" name="" title="I°" lay-skin="primary">I°
									<input type="checkbox" name="" title="Ⅱ°" lay-skin="primary"> Ⅱ°
									<input type="checkbox" name="" title="Ⅲ°" lay-skin="primary"> Ⅲ°
									<input type="checkbox" name="" title="血性羊水" lay-skin="primary"> 血性羊水
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">宫口开全</label>
							<div class="layui-inline">
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input">
								</div>
							</div>
							<div class="layui-inline">
								<label class="layui-form">第一产程</label>
								<input name="rprtype" pofield="rprtype" id="" style="width: 78px;height:20px; ">时
								<input name="rprtype" pofield="rprtype" id="" style="width: 78px;height:20px; ">分
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">胎儿娩出</label>
							<div class="layui-inline">
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input">
								</div>
							</div>
							<div class="layui-inline">
								<label class="layui-form">第二产程</label>
								<input name="rprtype" pofield="rprtype" id="" style="width: 78px;height:20px; ">时
								<input name="rprtype" pofield="rprtype" id="" style="width: 78px;height:20px; ">分
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">胎盘娩出</label>
							<div class="layui-inline">
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input">
								</div>
							</div>
							<div class="layui-inline">
								<label class="layui-form">第三产程</label>
								<input name="rprtype" pofield="rprtype" id="" style="width: 78px;height:20px; ">时
								<input name="rprtype" pofield="rprtype" id="" style="width: 78px;height:20px; ">分
							</div>
							<div class="layui-inline">
								<label class="layui-form-label">总产程</label>
								<input name="rprtype" pofield="rprtype" id="" style="width: 78px;height:20px; ">时
								<input name="rprtype" pofield="rprtype" id="" style="width: 78px;height:20px; ">分
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">
								<span class="layui-badge-dot"> </span> 分娩方式</label>
							<div class="layui-input-block">
								<input type="checkbox" name="" title="顺产,手术产：" lay-skin="primary">顺产,手术产：
								<input type="checkbox" name="" title="胎吸" lay-skin="primary">胎吸
								<input type="checkbox" name="" title="殿助" lay-skin="primary">殿助
								<input type="checkbox" name="" title="臀牵" lay-skin="primary">臀牵
								<input type="checkbox" name="" title="产钳" lay-skin="primary">产钳
								<input type="checkbox" name="" title="剖宫产" lay-skin="primary">剖宫产
								<input type="checkbox" name="" title="其他" lay-skin="primary">其他
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
								胎方位
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">胎盘情况</label>
							<div class="layui-input-block">
								<input type="checkbox" name="" title="完整" lay-skin="primary">完整
								<input type="checkbox" name="" title="不完整" lay-skin="primary">不完整
								<input type="checkbox" name="" title="自产" lay-skin="primary">自产
								<input type="checkbox" name="" title="手剥" lay-skin="primary">手剥 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<label class="layui-form">胎膜</label>
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="checkbox" name="" title="完整" lay-skin="primary">完整
										<input type="checkbox" name="" title="不完整" lay-skin="primary">不完整
									</div>
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">会阴情况</label>
							<div class="layui-inline">
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input">
								</div>
							</div>
							破裂:
							<input type="checkbox" name="" title="0" lay-skin="primary">0
							<input type="checkbox" name="" title="I" lay-skin="primary">I
							<input type="checkbox" name="" title="Ⅱ" lay-skin="primary">Ⅱ
							<input type="checkbox" name="" title="Ⅲ" lay-skin="primary">Ⅲ
							<input type="checkbox" name="" title="切开" lay-skin="primary">切开
							<input type="checkbox" name="" title="阴唇破裂" lay-skin="primary">阴唇破裂
							<input type="checkbox" name="" title="阴道破裂" lay-skin="primary">阴道破裂
							<input type="checkbox" name="" title="宫颈裂伤" lay-skin="primary">宫颈裂伤
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">新生儿胎别</label>
							<div class="layui-input-block">
								<input type="radio" name="newbaby" value="单" title="单">单
								<input type="radio" name="newbaby" value="双" title="双">双
								<input type="radio" name="newbaby" value="多胎" title="多胎">多胎 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<div class="layui-inline">
									<label class="layui-form">胎儿情况</label>
									<input type="radio" name="babysex" value="男" title="男">男
									<input type="radio" name="babysex" value="女" title="女">女
									<input type="radio" name="babysex" value="不详" title="不详">不详 体重(g)
									<input name="rprtype" pofield="rprtype" id="" style="width: 78px;height:20px;"> 身长(cm)
									<input name="rprtype" pofield="rprtype" id="" style="width: 78px;height:20px;">
									<input type="radio" name="babylife" value="活产" title="活产">活产
									<input type="radio" name="babylife" value="死胎" title="死胎">死胎
									<input type="radio" name="babylife" value="死产" title="死产">死产
									<input type="radio" name="babylife" value="窒息" title="窒息">窒息
								</div>
								<div class="layui-input-block">
									其中:Apgar评分1分钟
									<input name="rprtype" pofield="rprtype" id="" style="width: 78px;height:20px;"> 5分钟
									<input name="rprtype" pofield="rprtype" id="" style="width: 78px;height:20px;"> 10分钟
									<input name="rprtype" pofield="rprtype" id="" style="width: 78px;height:20px;">
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">产时出血量</label>
							<div class="layui-inline">
								<input name="rprtype" pofield="rprtype" id="" style="width: 78px;height:20px; ">ml
							</div>
							<div class="layui-inline">
								<label class="layui-form">产后两小时出血量</label>
								<input name="rprtype" pofield="rprtype" id="" style="width: 78px;height:20px; ">ml
							</div>
							<div class="layui-inline">
								<label class="layui-form">产后血压</label>
								<input name="rprtype" pofield="rprtype" id="" style="width: 78px;height:20px; "> /
								<input name="rprtype" pofield="rprtype" id="" style="width: 78px;height:20px; ">mmHg
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">产时并发症</label>
							<div class="layui-input-block">
								<input type="checkbox" name="" title="无" lay-skin="primary">无
								<input type="checkbox" name="" title="滞产、" lay-skin="primary">滞产、
								<input type="checkbox" name="" title="胎膜早破、" lay-skin="primary">胎膜早破、
								<input type="checkbox" name="" title="胎儿窘迫、" lay-skin="primary">胎儿窘迫、
								<input type="checkbox" name="" title="子痫、脐带异常（" lay-skin="primary">子痫、脐带异常 （
								<input type="checkbox" name="" title="脱垂" lay-skin="primary">脱垂
								<input type="checkbox" name="" title="过长" lay-skin="primary">过长
								<input type="checkbox" name="" title="过短" lay-skin="primary">过短
								<input type="checkbox" name="" title="脐带扭转" lay-skin="primary">脐带扭转
								<input type="checkbox" name="" title="脐带绕颈" lay-skin="primary">脐带绕颈
								<input type="checkbox" name="" title="脐带绕身" lay-skin="primary">脐带绕身
								<input type="checkbox" name="" title="脐带打结）" lay-skin="primary">脐带打结）
								<input type="checkbox" name="" title="子宫破裂" lay-skin="primary"> 子宫破裂
								<input type="checkbox" name="" title="软产道损伤" lay-skin="primary">软产道损伤
								<input type="checkbox" name="" title="产后出血" lay-skin="primary">产后出血
								<input type="checkbox" name="" title="其他" lay-skin="primary">其他
								<input name="rprtype" pofield="rprtype" id="" style="width: 78px;height:20px; ">
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">接生者</label>
							<div class="layui-input-block">
								<input type="checkbox" name="" title="医务人员" lay-skin="primary">医务人员
								<input type="checkbox" name="" title="乡村医生" lay-skin="primary">乡村医生
								<input type="checkbox" name="" title="接生员" lay-skin="primary">接生员 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 接生者1
								<input name="rprtype" pofield="rprtype" id="" style="width: 78px;height:20px; "> 接生者2
								<input name="rprtype" pofield="rprtype" id="" style="width: 78px;height:20px; ">
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">住院日期</label>
							<div class="layui-inline">
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input">
								</div>
							</div>
							<div class="layui-inline">
								<label class="layui-form">出院日期</label>
								<input name="rprtype" pofield="rprtype" id="" style="width: 78px;height:20px; ">
							</div>
							<div class="layui-inline">
								<label class="layui-form">是否高危</label>
								<input name="rprtype" pofield="rprtype" id="" style="width: 78px;height:20px; ">
							</div>
							<div class="layui-inline">
								<label class="layui-form">登记者</label>
								<input name="rprtype" pofield="rprtype" id="" style="width: 78px;height:20px; ">
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">备注</label>
							<div class="layui-input-block">
								<input type="checkbox" name="" title="" lay-skin="primary">/
								<input type="checkbox" name="" title="胎膜残留" lay-skin="primary">胎膜残留
								<input type="checkbox" name="" title="副胎盘" lay-skin="primary">副胎盘
								<input type="checkbox" name="" title="帆状胎盘" lay-skin="primary">帆状胎盘
								<input type="checkbox" name="" title="胎盘散在粗糙面" lay-skin="primary">胎盘散在粗糙面
								<input type="checkbox" name="" title="胎盘欠完整" lay-skin="primary">胎盘欠完整
								<input name="rprtype" pofield="rprtype" id="" style="width: 78px;height:20px; ">

							</div>
						</div>
					</fieldset>
				</div>
				<!-- 7.0产后访视 -->
				<div class="layui-tab-item">
					<fieldset class="layui-elem-field " style="margin-top: 20px;padding: 5px;flex-basis: 450px">
						<legend>产后访视记录表</legend>
						<div class="layui-form-item layui-form">
							<div class="layui-inline">
								<label class="layui-form-label">
									<span class="layui-badge-dot"> </span> 随访日期</label>
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="" name="" pofield="">
								</div>
							</div>
							<div class="layui-inline">
								<label class="layui-form-label">
									<span class="layui-badge-dot"> </span> 体温(°C)</label>
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="" name="" pofield="">
								</div>
							</div>
						</div>
						<div class="layui-form-item layui-form">
							<div class="layui-inline">
								<label class="layui-form-label">一般健康情况</label>
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="" name="" pofield="">
								</div>
							</div>
						</div>
						<div class="layui-form-item layui-form">
							<div class="layui-inline">
								<label class="layui-form-label">一般心里状况</label>
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="" name="" pofield="">
								</div>
							</div>
						</div>
						<div class="layui-form-item layui-form">
							<div class="layui-inline">
								<label class="layui-form-label">血压(mmHg)</label>
								<input name="rprtype" pofield="rprtype" id="" style="width: 78px;height:20px; "> /
								<input name="rprtype" pofield="rprtype" id="" style="width: 78px;height:20px; ">
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">乳房</label>
							<div class="layui-input-block">
								<input type="checkbox" name="" title="未见异常" lay-skin="primary" checked>未见异常
								<input type="checkbox" name="" title="异常" lay-skin="primary"> 异常
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
							</div>
						</div>
						<div class="layui-form-item layui-form">
							<div class="layui-inline">
								<label class="layui-form-label">乳腺检查(左)</label>
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="" name="" pofield="">
								</div>
							</div>
							<div class="layui-inline">
								<label class="layui-form-label">乳腺检查(右)</label>
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="" name="" pofield="">
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">恶露</label>
							<div class="layui-input-block">
								<input type="checkbox" name="" title="未见异常" lay-skin="primary" checked>未见异常
								<input type="checkbox" name="" title="异常" lay-skin="primary"> 异常
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">子宫</label>
							<div class="layui-input-block">
								<input type="checkbox" name="" title="未见异常" lay-skin="primary" checked>未见异常
								<input type="checkbox" name="" title="异常" lay-skin="primary"> 异常
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">伤口</label>
							<div class="layui-input-block">
								<input type="checkbox" name="" title="未见异常" lay-skin="primary" checked>未见异常
								<input type="checkbox" name="" title="异常" lay-skin="primary"> 异常
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">其他</label>
							<div class="layui-input-block">
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">分类</label>
							<div class="layui-input-block">
								<input type="checkbox" name="" title="未见异常" lay-skin="primary" checked>未见异常
								<input type="checkbox" name="" title="异常" lay-skin="primary"> 异常
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">指导</label>
							<div class="layui-input-block">
								<input type="checkbox" name="" title="个人卫生" lay-skin="primary" checked>个人卫生
								<input type="checkbox" name="" title="心理" lay-skin="primary" checked>心理
								<input type="checkbox" name="" title="营养" lay-skin="primary" checked>营养
								<input type="checkbox" name="" title="母乳喂养" lay-skin="primary" checked>母乳喂养
								<input type="checkbox" name="" title="新生儿护理与喂养" lay-skin="primary" checked>新生儿护理与喂养
								<input type="checkbox" name="" title="其他" lay-skin="primary"> 其他
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">转诊</label>
							<div class="layui-input-block">
								<input type="checkbox" name="" title="无" lay-skin="primary" checked>无
								<input type="checkbox" name="" title="有" lay-skin="primary"> 有 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 原因：
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
								机构及科室：
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
							</div>
						</div>
						<div class="layui-form-item layui-form">
							<div class="layui-inline">
								<label class="layui-form-label">
									<span class="layui-badge-dot"></span>下次访视日期</label>
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="" name="" pofield="">
								</div>
							</div>
							<div class="layui-inline">
								<label class="layui-form-label">随访医生</label>
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="" name="" pofield="">
								</div>
							</div>
						</div>
						<div class="layui-form-item layui-form">
							<div class="layui-inline">
								<label class="layui-form-label">是否外出</label>
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="" name="" pofield="">
								</div>
							</div>
							<div class="layui-inline">
								<label class="layui-form-label">备注</label>
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="" name="" pofield="">
								</div>
							</div>
						</div>
					</fieldset>
				</div>
				<!-- 8.0产后42天访视 -->
				<div class="layui-tab-item">
					<fieldset class="layui-elem-field " style="margin-top: 20px;padding: 5px;flex-basis: 450px">
						<legend>产后访视记录表</legend>
						<div class="layui-form-item layui-form">
							<div class="layui-inline">
								<label class="layui-form-label">
									<span class="layui-badge-dot"> </span> 随访日期</label>
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="" name="" pofield="">
								</div>
							</div>
						</div>
						<div class="layui-form-item layui-form">
							<div class="layui-inline">
								<label class="layui-form-label">一般健康情况</label>
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="" name="" pofield="">
								</div>
							</div>
						</div>
						<div class="layui-form-item layui-form">
							<div class="layui-inline">
								<label class="layui-form-label">一般心理状况</label>
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="" name="" pofield="">
								</div>
							</div>
						</div>
						<div class="layui-form-item layui-form">
							<div class="layui-inline">
								<label class="layui-form-label">
									<span class="layui-badge-dot"> </span>血压(mmHg)</label>
								<input name="rprtype" pofield="rprtype" id="" style="width: 78px;height:20px; "> /
								<input name="rprtype" pofield="rprtype" id="" style="width: 78px;height:20px; ">
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">乳房</label>
							<div class="layui-input-block">
								<input type="checkbox" name="" title="未见异常" lay-skin="primary" checked>未见异常
								<input type="checkbox" name="" title="异常" lay-skin="primary"> 异常
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
							</div>
						</div>
						<div class="layui-form-item layui-form">
							<div class="layui-inline">
								<label class="layui-form-label">乳腺检查(左)</label>
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="" name="" pofield="">
								</div>
							</div>
							<div class="layui-inline">
								<label class="layui-form-label">乳腺检查(右)</label>
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="" name="" pofield="">
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">恶露</label>
							<div class="layui-input-block">
								<input type="checkbox" name="" title="未见异常" lay-skin="primary" checked>未见异常
								<input type="checkbox" name="" title="异常" lay-skin="primary"> 异常
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">子宫</label>
							<div class="layui-input-block">
								<input type="checkbox" name="" title="未见异常" lay-skin="primary" checked>未见异常
								<input type="checkbox" name="" title="异常" lay-skin="primary"> 异常
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">伤口</label>
							<div class="layui-input-block">
								<input type="checkbox" name="" title="未见异常" lay-skin="primary" checked>未见异常
								<input type="checkbox" name="" title="异常" lay-skin="primary"> 异常
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">其他</label>
							<div class="layui-input-block">
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">分类</label>
							<div class="layui-input-block">
								<input type="checkbox" name="" title="恢复" lay-skin="primary" checked>恢复
								<input type="checkbox" name="" title="已恢复" lay-skin="primary"> 已恢复
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">指导</label>
							<div class="layui-input-block">
								<input type="checkbox" name="" title="性保健" lay-skin="primary" checked>性保健
								<input type="checkbox" name="" title="避孕" lay-skin="primary" checked>避孕
								<input type="checkbox" name="" title="婴儿喂养及营养" lay-skin="primary" checked>婴儿喂养及营养
								<input type="checkbox" name="" title="其他" lay-skin="primary"> 其他
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">处理</label>
							<div class="layui-input-block">
								<input type="checkbox" name="" title="结案" lay-skin="primary" checked>结案
								<input type="checkbox" name="" title="转诊" lay-skin="primary"> 转诊 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 原因：
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
								机构及科室：
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input">
									</div>
								</div>
							</div>
						</div>
						<div class="layui-form-item layui-form">
							<div class="layui-inline">
								<label class="layui-form-label">随访医生</label>
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="" name="" pofield="">
								</div>
							</div>
						</div>
						<div class="layui-form-item layui-form">
							<div class="layui-inline">
								<label class="layui-form-label">是否外出</label>
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="" name="" pofield="">
								</div>
							</div>
							<div class="layui-inline">
								<label class="layui-form-label">备注</label>
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="" name="" pofield="">
								</div>
							</div>
						</div>
					</fieldset>
				</div>
				<div class="layui-tab-item">9</div>
			</div>
		</div>
	</body>
	<script type="text/javascript" src="js/view_electronicArchives_detail.js?v=1.0"></script>
	</html>