<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<%@ include file="/open/commonjs/tldLayui.jsp"%>
<title>体检表详情</title>
</head>
<body>
	<input type="hidden" val="test" />
	<div id="btns"></div>
	<div align="center"></div>

	<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
		<legend>居民详情</legend>
	</fieldset>

	<form class="layui-form layui-form-pane" id="healthfileForm">
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">姓名</label>
				<div class="layui-input-inline" style="width:100px">
					<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="name" name="name" pofield="name">
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">性别</label>
				<div class="layui-input-inline" style="width:100px">
					<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="sex" name="sex" pofield="sex">
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">出生日期</label>
				<div class="layui-input-inline"  style="width:100px">
					<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="birthday" name="birthday" pofield="birthday">
				</div>
			</div>
		</div>
		
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">本人电话</label>
				<div class="layui-input-inline" style="width:100px">
					<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="telphone" name="telphone" pofield="telphone">
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">身份证</label>
				<div class="layui-input-inline">
					<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="idcardno" name="idcardno" pofield="idcardno">
				</div>
			</div>
		</div>
		
		<div class="layui-form-item">
			<label class="layui-form-label">地址</label>
			<div class="layui-input-block">
				<input type="text" autocomplete="off" class="layui-input" id="address" name="address" pofield="address" readonly="readonly" >
			</div>
		</div>
		
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">检查日期</label>
				<div class="layui-input-inline" style="width:100px" >
					<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="edate" name="edate" pofield="edate">
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">责任医生</label>
				<div class="layui-input-inline" style="width:100px" >
					<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="doctor" name="doctor" pofield="doctor">
				</div>
			</div>
		</div>

		<fieldset class="layui-elem-field" style="margin-top: 20px;">
			<legend>症状</legend>
			<div class="layui-field-box">
			    <input id="zz_wu" type="checkbox" lay-skin="primary"  disabled="disabled" >无症状</input>
			    <input id="zz_tt" type="checkbox" lay-skin="primary"  disabled="disabled" >头痛</input>
			    <input id="zz_ty" type="checkbox" lay-skin="primary"  disabled="disabled" >头晕</input>
			    <input id="zz_xj" type="checkbox" lay-skin="primary"  disabled="disabled" >心悸</input>
			    <input id="zz_xm" type="checkbox" lay-skin="primary"  disabled="disabled" >胸闷</input>
			    <input id="zz_xt" type="checkbox" lay-skin="primary"  disabled="disabled" >胸痛</input>
			    <input id="zz_mxks" type="checkbox" lay-skin="primary"  disabled="disabled" >慢性咳嗽</input>
			    <input id="zz_kt" type="checkbox" lay-skin="primary"  disabled="disabled" >咳痰</input>
			    <input id="zz_hxkn" type="checkbox" lay-skin="primary"  disabled="disabled" >呼吸困难</input>
			    <input id="zz_dy" type="checkbox" lay-skin="primary"  disabled="disabled" >多饮</input>
			    <input id="zz_dn" type="checkbox" lay-skin="primary"  disabled="disabled" >多尿</input>
			    <input id="zz_tzxj" type="checkbox" lay-skin="primary"  disabled="disabled" >体重下降</input>
			    <input id="zz_fl" type="checkbox" lay-skin="primary"  disabled="disabled" >乏力</input>
			    <input id="zz_gjzt" type="checkbox" lay-skin="primary"  disabled="disabled" >关节肿痛</input>
			    <input id="zz_slmh" type="checkbox" lay-skin="primary"  disabled="disabled" >视力模糊</input>
			    <input id="zz_sjmm" type="checkbox" lay-skin="primary"  disabled="disabled" >手脚麻木</input>
			    <input id="zz_nj" type="checkbox" lay-skin="primary"  disabled="disabled" >尿急</input>
			    <input id="zz_nt" type="checkbox" lay-skin="primary"  disabled="disabled" >尿痛</input>
			    <input id="zz_bm" type="checkbox" lay-skin="primary"  disabled="disabled" >便秘</input>
			    <input id="zz_fx" type="checkbox" lay-skin="primary"  disabled="disabled" >腹泻</input>
			    <input id="zz_exot" type="checkbox" lay-skin="primary"  disabled="disabled" >恶心呕吐</input>
			    <input id="zz_yh" type="checkbox" lay-skin="primary"  disabled="disabled" >眼花</input>
			    <input id="zz_em" type="checkbox" lay-skin="primary"  disabled="disabled" >耳鸣</input>
			    <input id="zz_rfzt" type="checkbox" lay-skin="primary"  disabled="disabled" >乳房胀痛</input>
			    <input id="zz_qt" type="checkbox" lay-skin="primary"  disabled="disabled" >其他</input>
			    <div class="layui-inline">
					<div class="layui-input-inline">
						<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="zz_qt0000" name="zz_qt0000" pofield="zz_qt0000">
					</div>
				</div>
			</div>
		</fieldset>

		<fieldset class="layui-elem-field" style="margin-top: 20px;">
			<legend>一般状况</legend>
			<div class="layui-field-box">
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">体温(℃)</label>
						<div class="layui-input-inline" style="width:100px">
							<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="ybzk_tiwen" name="ybzk_tiwen" pofield="ybzk_tiwen">
						</div>
					</div>
					<div class="layui-inline">
						<label class="layui-form-label">脉率(次/分钟)</label>
						<div class="layui-input-inline" style="width:100px">
							<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="ybzk_ml" name="ybzk_ml" pofield="ybzk_ml">
						</div>
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">呼吸频率(次/分钟)</label>
						<div class="layui-input-inline" style="width:100px">
							<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="ybzk_hxpl" name="ybzk_hxpl" pofield="ybzk_hxpl">
						</div>
					</div>
					<div class="layui-inline">
						<label class="layui-form-label">血压左侧(mmHg)</label>
						<div class="layui-input-inline" style="width:100px" >
							<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="ybzk_zssy" name="ybzk_zssy" pofield="ybzk_zssy">
						</div>
						<span style="position: absolute;margin-left:-7px;margin-top:5px">/</span>
						<div class="layui-input-inline" style="width:100px" >
							<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="ybzk_zszy" name="ybzk_zszy" pofield="ybzk_zszy">
						</div>
					</div>
					<div class="layui-inline">
						<label class="layui-form-label">血压右侧(mmHg)</label>
						<div class="layui-input-inline" style="width:100px" >
							<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="ybzk_yssy" name="ybzk_yssy" pofield="ybzk_yssy">
						</div>
						<span style="position: absolute;margin-left:-7px;margin-top:5px">/</span>
						<div class="layui-input-inline" style="width:100px" >
							<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="ybzk_yszy" name="ybzk_yszy" pofield="ybzk_yszy">
						</div>
					</div>
					<div class="layui-inline">
						<label class="layui-form-label">身高(cm)</label>
						<div class="layui-input-inline" style="width:100px" >
							<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="ybzk_sg" name="ybzk_sg" pofield="ybzk_sg">
						</div>
					</div>
					<div class="layui-inline">
						<label class="layui-form-label">体重(kg)</label>
						<div class="layui-input-inline" style="width:100px" >
							<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="ybzk_tz" name="ybzk_tz" pofield="ybzk_tz">
						</div>
					</div>
					<div class="layui-inline">
						<label class="layui-form-label">腰围(cm)</label>
						<div class="layui-input-inline" style="width:100px" >
							<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="ybzk_yw" name="ybzk_yw" pofield="ybzk_yw">
						</div>
					</div>
					<div class="layui-inline">
						<label class="layui-form-label">体质指数</label>
						<div class="layui-input-inline" style="width:100px" >
							<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="ybzk_tzzs" name="ybzk_tzzs" pofield="ybzk_tzzs">
						</div>
					</div>
					<div class="layui-inline">
						<label class="layui-form-label">臀围(cm)</label>
						<div class="layui-input-inline" style="width:100px" >
							<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="ybzk_tunwei" name="ybzk_tunwei" pofield="ybzk_tunwei">
						</div>
					</div>
					<div class="layui-inline">
						<label class="layui-form-label">臀腰围比值</label>
						<div class="layui-input-inline" style="width:100px" >
							<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="ybzk_ytwbz" name="ybzk_ytwbz" pofield="ybzk_ytwbz">
						</div>
					</div>
				</div>
			</div>
			<div class="layui-field-box">
			<div class="layui-form-item">
				<label class="layui-form-label" style="width:180px">老年人健康状态自我评估</label>
				<div class="layui-input-block">
					<input type="checkbox" name="ybzk_lnrzgn" lay-skin="primary" title="满意" id="ybzk_lnrzgn" pofield="ybzk_lnrzgn" value="1" disabled="disabled">
					<input type="checkbox" name="ybzk_lnrzgn" lay-skin="primary" title="基本满意" id="ybzk_lnrzgn" pofield="ybzk_lnrzgn" value="2" disabled="disabled">
					<input type="checkbox" name="ybzk_lnrzgn" lay-skin="primary" title="说不清楚" id="ybzk_lnrzgn" pofield="ybzk_lnrzgn" value="3" disabled="disabled">
					<input type="checkbox" name="ybzk_lnrzgn" lay-skin="primary" title="说不清楚 " id="ybzk_lnrzgn" pofield="ybzk_lnrzgn" value="4" disabled="disabled">
					<input type="checkbox" name="ybzk_lnrzgn" lay-skin="primary" title="说不清楚" id="ybzk_lnrzgn" pofield="ybzk_lnrzgn" value="5" disabled="disabled">
				</div>
			</div>
			</div>
			<div class="layui-field-box">
			<div class="layui-form-item">
				<label class="layui-form-label" style="width:180px">老年人生活自理能力自我评估</label>
				<div class="layui-input-block">
					<input type="checkbox" name="lnrshpj" lay-skin="primary" title="可自理（0～3分)" id="lnrshpj" pofield="lnrshpj" value="1" disabled="disabled">
					<input type="checkbox" name="lnrshpj" lay-skin="primary" title="轻度依赖（4～8分）" id="lnrshpj" pofield="lnrshpj" value="2" disabled="disabled">
					<input type="checkbox" name="lnrshpj" lay-skin="primary" title="中度依赖（9～18分)" id="lnrshpj" pofield="lnrshpj" value="3" disabled="disabled">
					<input type="checkbox" name="lnrshpj" lay-skin="primary" title="不能自理（≥19分） " id="lnrshpj" pofield="lnrshpj" value="4" disabled="disabled">
				</div>
			</div>
			</div>
			<div class="layui-field-box">
			<div class="layui-form-item">
				<label class="layui-form-label">老年人认知功能</label>
				<div class="layui-input-block">
					<input type="checkbox" name="ybzk_lnrzgn" lay-skin="primary" title="粗筛阴性" id="ybzk_lnrzgn" pofield="ybzk_lnrzgn" value="1" disabled="disabled">
					<input type="checkbox" name="ybzk_lnrzgn" lay-skin="primary" title="粗筛阳性，简易智力状态检查，总分：" id="ybzk_lnrzgn" pofield="ybzk_lnrzgn" value="2" disabled="disabled">
					<div class="layui-inline">
						<div class="layui-input-inline">
							<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="ybzk_lnzljc" name="ybzk_lnzljc" pofield="ybzk_lnzljc">
						</div>
					</div>
				</div>
			</div>
			</div>
			<div class="layui-field-box">
			<div class="layui-form-item">
				<label class="layui-form-label">老年人情感状态</label>
				<div class="layui-input-block">
					<input type="checkbox" name="ybzk_lnqgzt" lay-skin="primary" title="粗筛阴性" id="ybzk_lnqgzt" pofield="ybzk_lnqgzt" value="1" disabled="disabled">
					<input type="checkbox" name="ybzk_lnqgzt" lay-skin="primary" title="粗筛阳性，老年人抑郁评分检查，总分：" id="ybzk_lnqgzt" pofield="ybzk_lnqgzt" value="2" disabled="disabled">
					<div class="layui-inline">
						<div class="layui-input-inline">
							<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="ybzk_lnyypf" name="ybzk_lnyypf" pofield="ybzk_lnyypf">
						</div>
					</div>
				</div>
			</div>
			</div>
		</fieldset>

		<fieldset class="layui-elem-field" style="margin-top: 20px;">
			<legend>生活方式</legend>
			<div class="layui-field-box">
				<div class="layui-form-item">
					<div class="layui-form-item">
						<fieldset class="layui-elem-field" >
							<legend>体育锻炼</legend>
							<div class="layui-field-box">
								<div class="layui-form-item">
									<label class="layui-form-label">锻炼频率</label>
									<div class="layui-input-block">
										<input type="checkbox" name="shfs_tydl_dlpl" lay-skin="primary" title="每天" id="shfs_tydl_dlpl" pofield="shfs_tydl_dlpl" value="1" disabled="disabled">
										<input type="checkbox" name="shfs_tydl_dlpl" lay-skin="primary" title="每周一次以上" id="shfs_tydl_dlpl" pofield="shfs_tydl_dlpl" value="2" disabled="disabled">
										<input type="checkbox" name="shfs_tydl_dlpl" lay-skin="primary" title="偶尔" id="shfs_tydl_dlpl" pofield="shfs_tydl_dlpl" value="3" disabled="disabled">
										<input type="checkbox" name="shfs_tydl_dlpl" lay-skin="primary" title="不锻炼" id="shfs_tydl_dlpl" pofield="shfs_tydl_dlpl" value="4" disabled="disabled">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label" style="width:30%;float: left;">每次锻炼时间(分钟)</label>
									<div class="layui-input-inline" >
										<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="shfs_tydl_mcdlsj" name="shfs_tydl_mcdlsj" pofield="shfs_tydl_mcdlsj">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label" style="width:30%;float: left;">坚持锻炼时间(年)</label>
									<div class="layui-input-inline" >
										<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="shfs_tydl_jcdlsj" name="shfs_tydl_jcdlsj" pofield="shfs_tydl_jcdlsj">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label" style="width:30%;float: left;">锻炼方式</label>
									<div class="layui-input-inline" >
										<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="shfs_tydl_dlfs" name="shfs_tydl_dlfs" pofield="shfs_tydl_dlfs">
									</div>
								</div>
							</div>
						</fieldset>
					</div>
					
					<div class="layui-form-item">
						<fieldset class="layui-elem-field" >
							<legend>饮食习惯</legend>
							<div class="layui-field-box">
								<input type="checkbox" name="shsf_ysxg" lay-skin="primary" title="荤素均衡" id="shsf_ysxg" pofield="shsf_ysxg" value="1" disabled="disabled">
								<input type="checkbox" name="shsf_ysxg" lay-skin="primary" title="荤食为主" id="shsf_ysxg" pofield="shsf_ysxg" value="2" disabled="disabled">
								<input type="checkbox" name="shsf_ysxg" lay-skin="primary" title="素食为主" id="shsf_ysxg" pofield="shsf_ysxg" value="3" disabled="disabled">
								<input type="checkbox" name="shsf_ysxg" lay-skin="primary" title="嗜盐" id="shsf_ysxg" pofield="shsf_ysxg" value="4" disabled="disabled">
								<input type="checkbox" name="shsf_ysxg" lay-skin="primary" title="嗜油" id="shsf_ysxg" pofield="shsf_ysxg" value="5" disabled="disabled">
								<input type="checkbox" name="shsf_ysxg" lay-skin="primary" title="嗜糖" id="shsf_ysxg" pofield="shsf_ysxg" value="6" disabled="disabled">
							</div>
						</fieldset>
					</div>
					
					<div class="layui-form-item">
						<fieldset class="layui-elem-field" >
							<legend>吸烟情况</legend>
							<div class="layui-field-box">
								<div class="layui-form-item">
									<label class="layui-form-label">吸烟状况</label>
									<div class="layui-input-block">
										<input type="checkbox" name="shsf_xyqk_xyzk" lay-skin="primary" title="从不吸烟" id="shsf_xyqk_xyzk" pofield="shsf_xyqk_xyzk" value="1" disabled="disabled">
										<input type="checkbox" name="shsf_xyqk_xyzk" lay-skin="primary" title="已戒烟" id="shsf_xyqk_xyzk" pofield="shsf_xyqk_xyzk" value="2" disabled="disabled">
										<input type="checkbox" name="shsf_xyqk_xyzk" lay-skin="primary" title="吸烟" id="shsf_xyqk_xyzk" pofield="shsf_xyqk_xyzk" value="3" disabled="disabled">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label" style="width:30%;float: left;">日吸烟量(支)</label>
									<div class="layui-input-inline" >
										<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="shsf_xyqk_rxyl" name="shsf_xyqk_rxyl" pofield="shsf_xyqk_rxyl">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label" style="width:30%;float: left;">开始吸烟年龄(岁)</label>
									<div class="layui-input-inline" >
										<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="shsf_xyqk_xynl" name="shsf_xyqk_xynl" pofield="shsf_xyqk_xynl">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label" style="width:30%;float: left;">戒烟年龄(岁)</label>
									<div class="layui-input-inline" >
										<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="shsf_xyqk_jynl" name="shsf_xyqk_jynl" pofield="shsf_xyqk_jynl">
									</div>
								</div>
							</div>
						</fieldset>
					</div>
					
					<div class="layui-form-item">
						<fieldset class="layui-elem-field" >
							<legend>饮酒情况</legend>
							<div class="layui-field-box">
								<div class="layui-form-item">
									<label class="layui-form-label">饮酒频率</label>
									<div class="layui-input-block">
										<input type="checkbox" name="shfs_yjqk_yjpl" lay-skin="primary" title="从不" id="shfs_yjqk_yjpl" pofield="shfs_yjqk_yjpl" value="1" disabled="disabled">
										<input type="checkbox" name="shfs_yjqk_yjpl" lay-skin="primary" title="偶尔" id="shfs_yjqk_yjpl" pofield="shfs_yjqk_yjpl" value="2" disabled="disabled">
										<input type="checkbox" name="shfs_yjqk_yjpl" lay-skin="primary" title="经常" id="shfs_yjqk_yjpl" pofield="shfs_yjqk_yjpl" value="3" disabled="disabled">
										<input type="checkbox" name="shfs_yjqk_yjpl" lay-skin="primary" title="每天" id="shfs_yjqk_yjpl" pofield="shfs_yjqk_yjpl" value="4" disabled="disabled">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">日饮酒量(两)</label>
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="shfs_yjqk_ryjl" name="shfs_yjqk_ryjl" pofield="shfs_yjqk_ryjl">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">是否戒酒</label>
									<div class="layui-input-block">
										<input type="checkbox" name="shfs_yjqk_sfjj" lay-skin="primary" title="未戒酒" id="shfs_yjqk_sfjj" pofield="shfs_yjqk_sfjj" value="1" disabled="disabled">
										<input type="checkbox" name="shfs_yjqk_sfjj" lay-skin="primary" title="已戒酒，戒酒年龄(岁)：" id="shfs_yjqk_sfjj" pofield="shfs_yjqk_sfjj" value="2" disabled="disabled">
										<div class="layui-inline">
											<div class="layui-input-inline">
												<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="shfs_yjqk_jjnl" name="shfs_yjqk_jjnl" pofield="shfs_yjqk_jjnl">
											</div>
										</div>
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">开始饮酒年龄(岁)</label>
									<div class="layui-input-block">
										<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="shfs_yjqk_ksyjnl" name="shfs_yjqk_ksyjnl" pofield="shfs_yjqk_ksyjnl">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label" >近一年是否曾醉酒</label>
									<div class="layui-input-block" >
										<input type="checkbox" name="shfs_yjqk_sfcjj" lay-skin="primary" title="是" id="shfs_yjqk_sfcjj" pofield="shfs_yjqk_sfcjj" value="1" disabled="disabled">
										<input type="checkbox" name="shfs_yjqk_sfcjj" lay-skin="primary" title="否" id="shfs_yjqk_sfcjj" pofield="shfs_yjqk_sfcjj" value="2" disabled="disabled">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label" >饮酒种类</label>
									<div class="layui-input-block" >
										<input type="checkbox" name="shfs_yjzl_" lay-skin="primary" title="白酒" id="shfs_yjzl_" pofield="shfs_yjzl_" value="1" disabled="disabled">
										<input type="checkbox" name="shfs_yjzl_" lay-skin="primary" title="啤酒" id="shfs_yjzl_" pofield="shfs_yjzl_" value="2" disabled="disabled">
										<input type="checkbox" name="shfs_yjzl_" lay-skin="primary" title="红酒" id="shfs_yjzl_" pofield="shfs_yjzl_" value="3" disabled="disabled">
										<input type="checkbox" name="shfs_yjzl_" lay-skin="primary" title="黄酒" id="shfs_yjzl_" pofield="shfs_yjzl_" value="4" disabled="disabled">
										<input type="checkbox" name="shfs_yjzl_" lay-skin="primary" title="其他" id="shfs_yjzl_" pofield="shfs_yjzl_" value="5" disabled="disabled">
										<div class="layui-inline">
											<div class="layui-input-inline">
												<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="shfs_yjzl_qt0000" name="shfs_yjzl_qt0000" pofield="shfs_yjzl_qt0000">
											</div>
										</div>
									</div>
								</div>
							</div>
						</fieldset>
					</div>
					<div class="layui-form-item">
						<fieldset class="layui-elem-field" >
							<legend>职业病危害因素接触史</legend>
							<div class="layui-field-box">
								<div class="layui-form-item">
									<label class="layui-form-label" style="width: 180px" >职业病危害因素接触史</label>
									<div class="layui-input-block" >
										<input type="checkbox" name="shfs_zybl_qk" lay-skin="primary" title="无" id="shfs_zybl_qk" pofield="shfs_zybl_qk" value="0" disabled="disabled">
										<input type="checkbox" name="shfs_zybl_qk" lay-skin="primary" title="有" id="shfs_zybl_qk" pofield="shfs_zybl_qk" value="1" disabled="disabled">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">工种</label>
									<div class="layui-input-block">
										<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="sfhs_zybl_jtzy" name="sfhs_zybl_jtzy" pofield="sfhs_zybl_jtzy">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">从业时间(年)</label>
									<div class="layui-input-block">
										<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="sfhs_zybl_cysj" name="sfhs_zybl_cysj" pofield="sfhs_zybl_cysj">
									</div>
								</div>
								<div class="layui-form-item">
									<fieldset class="layui-elem-field" >
										<legend>毒物种类</legend>
										<div class="layui-form-item">
											<label class="layui-form-label" style="width: 180px" >粉尘-防护措施</label>
											<div class="layui-input-block" >
												<input type="checkbox" name="sfhs_zybl_fccs" lay-skin="primary" title="无" id="sfhs_zybl_fccs" pofield="sfhs_zybl_fccs" value="1" disabled="disabled">
												<input type="checkbox" name="sfhs_zybl_fccs" lay-skin="primary" title="有" id="sfhs_zybl_fccs" pofield="sfhs_zybl_fccs" value="2" disabled="disabled">
											</div>
										</div>
										<div class="layui-form-item">
											<label class="layui-form-label" style="width: 180px" >放射物质-放射物质</label>
											<div class="layui-input-block" >
												<input type="checkbox" name="sfhs_zybl_fccs" lay-skin="primary" title="无" id="sfhs_zybl_fccs" pofield="sfhs_zybl_fccs" value="1" disabled="disabled">
												<input type="checkbox" name="sfhs_zybl_fccs" lay-skin="primary" title="有" id="sfhs_zybl_fccs" pofield="sfhs_zybl_fccs" value="2" disabled="disabled">
											</div>
										</div>
										<div class="layui-form-item">
											<label class="layui-form-label" style="width: 180px" >放射物质-物理因素</label>
											<div class="layui-input-block" >
												<input type="checkbox" name="sfhs_zybl_fccs" lay-skin="primary" title="无" id="sfhs_zybl_fccs" pofield="sfhs_zybl_fccs" value="1" disabled="disabled">
												<input type="checkbox" name="sfhs_zybl_fccs" lay-skin="primary" title="有" id="sfhs_zybl_fccs" pofield="sfhs_zybl_fccs" value="2" disabled="disabled">
											</div>
										</div>
										<div class="layui-form-item">
											<label class="layui-form-label" style="width: 180px" >放射物质-其他</label>
											<div class="layui-input-block" >
												<input type="checkbox" name="shfs_zybl_qk" lay-skin="primary" title="无" id="shfs_zybl_qk" pofield="shfs_zybl_qk" value="1" disabled="disabled">
												<input type="checkbox" name="shfs_zybl_qk" lay-skin="primary" title="有" id="shfs_zybl_qk" pofield="shfs_zybl_qk" value="2" disabled="disabled">
											</div>
										</div>
									</fieldset>

								</div>
							</div>
						</fieldset>
					</div>
				</div>
			</div>
		</fieldset>
		<fieldset class="layui-elem-field" style="margin-top: 20px;">
			<legend>脏器功能</legend>
			<div class="layui-form-item">
				<fieldset class="layui-elem-field" >
					<legend>口腔</legend>
					<div class="layui-field-box">
						<div class="layui-form-item">
							<label class="layui-form-label">口唇</label>
							<div class="layui-input-block">
								<input type="checkbox" name="jktj_zqgn_kqkc" lay-skin="primary" title="红润" id="jktj_zqgn_kqkc" pofield="jktj_zqgn_kqkc" value="1" disabled="disabled">
								<input type="checkbox" name="jktj_zqgn_kqkc" lay-skin="primary" title="苍白" id="jktj_zqgn_kqkc" pofield="jktj_zqgn_kqkc" value="2" disabled="disabled">
								<input type="checkbox" name="jktj_zqgn_kqkc" lay-skin="primary" title="发绀" id="jktj_zqgn_kqkc" pofield="jktj_zqgn_kqkc" value="3" disabled="disabled">
								<input type="checkbox" name="jktj_zqgn_kqkc" lay-skin="primary" title="皲裂" id="jktj_zqgn_kqkc" pofield="jktj_zqgn_kqkc" value="4" disabled="disabled">
								<input type="checkbox" name="jktj_zqgn_kqkc" lay-skin="primary" title="疱疹" id="jktj_zqgn_kqkc" pofield="jktj_zqgn_kqkc" value="5" disabled="disabled">
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">齿列</label>
							<div class="layui-input-block">
								<input type="checkbox" name="jktj_zqgn_kqcl" lay-skin="primary" title="正常" id="kqcl_zz" pofield="jktj_zqgn_kqcl" value="1" disabled="disabled">
								<input type="checkbox" name="jktj_zqgn_kqcl" lay-skin="primary" title="缺齿" id="kqcl_quec" pofield="jktj_zqgn_kqcl" value="2" disabled="disabled">
								<input type="checkbox" name="jktj_zqgn_kqcl" lay-skin="primary" title="龋齿" id="kqcl_quc" pofield="jktj_zqgn_kqcl" value="3" disabled="disabled">
								<input type="checkbox" name="jktj_zqgn_kqcl" lay-skin="primary" title="义齿(假牙)" id="kqcl_yc" pofield="jktj_zqgn_kqcl" value="4" disabled="disabled">
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">咽部</label>
							<div class="layui-input-block">
								<input type="checkbox" name="jktj_zqgn_kqyb" lay-skin="primary" title="无充血" id="jktj_zqgn_kqyb" pofield="jktj_zqgn_kqyb" value="1" disabled="disabled">
								<input type="checkbox" name="jktj_zqgn_kqyb" lay-skin="primary" title="充血" id="jktj_zqgn_kqyb" pofield="jktj_zqgn_kqyb" value="2" disabled="disabled">
								<input type="checkbox" name="jktj_zqgn_kqyb" lay-skin="primary" title="淋巴滤泡增生" id="jktj_zqgn_kqyb" pofield="jktj_zqgn_kqyb" value="3" disabled="disabled">
							</div>
						</div>
					</div>
				</fieldset>

			</div>
			<div class="layui-form-item">
				<fieldset class="layui-elem-field" >
					<legend>视力</legend>
					<div class="layui-field-box">
						<div class="layui-form-item">
							<label class="layui-form-label" style="width: 100px" >左眼</label>
							<div class="layui-input-inline" >
								<input type="text" name="jktj_zqgn_slzy" autocomplete="off" class="layui-input" readonly="readonly" id="jktj_zqgn_slzy" pofield="jktj_zqgn_slzy"  >
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label" style="width: 100px" >右眼</label>
							<div class="layui-input-inline" >
								<input type="text" name="jktj_zqgn_slyy" autocomplete="off" class="layui-input" readonly="readonly" id="jktj_zqgn_slyy" pofield="jktj_zqgn_slyy" >
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label" style="width: 100px" >矫正视力：左眼</label>
							<div class="layui-input-inline" >
								<input type="text" name="jktj_zqgn_jzslzy" autocomplete="off" class="layui-input" readonly="readonly" id="jktj_zqgn_jzslzy" pofield="jktj_zqgn_jzslzy" >
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label" style="width: 100px" >矫正视力：右眼</label>
							<div class="layui-input-inline" >
								<input type="text" name="jktj_zqgn_jzslyy" autocomplete="off" class="layui-input" readonly="readonly" id="jktj_zqgn_jzslyy" pofield="jktj_zqgn_jzslyy" >
							</div>
						</div>
					</div>
				</fieldset>
			</div>
			<div class="layui-field-box">
			<div class="layui-form-item">
					<label class="layui-form-label" style="width: 100px" >听力</label>
						<div class="layui-input-block">
							<input type="checkbox" name="jktj_zqgn_tl" lay-skin="primary" title="听见" id="jktj_zqgn_tl" pofield="jktj_zqgn_tl" value="1" disabled="disabled">
							<input type="checkbox" name="jktj_zqgn_tl" lay-skin="primary" title="听不清或无法听见" id="jktj_zqgn_tl" pofield="jktj_zqgn_tl" value="2" disabled="disabled">
						</div>
					</div>
			</div>

					<div class="layui-form-item">
						<label class="layui-form-label" style="width: 100px" >运动功能</label>
						<div class="layui-input-block">
							<input type="checkbox" name="jktj_zqgn_ydgn" lay-skin="primary" title="可顺利完成" id="jktj_zqgn_ydgn" pofield="jktj_zqgn_ydgn" value="1" disabled="disabled">
							<input type="checkbox" name="jktj_zqgn_ydgn" lay-skin="primary" title="无法独立完成其中任何一个动作" id="jktj_zqgn_ydgn" pofield="jktj_zqgn_ydgn" value="2" disabled="disabled">
						</div>
				</div>
			</div>
		</fieldset>

		<fieldset class="layui-elem-field" style="margin-top: 20px;">
			<legend>查体</legend>
			<div class="layui-form-item">
				<div class="layui-field-box">
				<div class="layui-form-item">
					<label class="layui-form-label" style="width: 100px" >眼底*</label>
					<div class="layui-input-block">
						<input type="checkbox" name="fzjc_yd" lay-skin="primary" title="正常" id="fzjc_yd" pofield="fzjc_yd" value="1" disabled="disabled">
						<input type="checkbox" name="fzjc_yd" lay-skin="primary" title="异常" id="fzjc_yd" pofield="fzjc_yd" value="2" disabled="disabled">
						<div class="layui-inline">
							<div class="layui-input-inline">
								<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="fzjc_ydqt" name="fzjc_ydqt" pofield="fzjc_ydqt">
							</div>
						</div>
					</div>
				</div>
				</div>
				<div class="layui-field-box">
				<div class="layui-form-item">
					<label class="layui-form-label" style="width: 100px" >皮肤</label>
					<div class="layui-input-block">
						<input type="checkbox" name="ct_pf" lay-skin="primary" title="正常" id="ct_pf" pofield="ct_pf" value="1" disabled="disabled">
						<input type="checkbox" name="ct_pf" lay-skin="primary" title="潮红" id="ct_pf" pofield="ct_pf" value="2" disabled="disabled">
						<input type="checkbox" name="ct_pf" lay-skin="primary" title="苍白" id="ct_pf" pofield="ct_pf" value="3" disabled="disabled">
						<input type="checkbox" name="ct_pf" lay-skin="primary" title="发绀" id="ct_pf" pofield="ct_pf" value="4" disabled="disabled">
						<input type="checkbox" name="ct_pf" lay-skin="primary" title="黄染" id="ct_pf" pofield="ct_pf" value="5" disabled="disabled">
						<input type="checkbox" name="ct_pf" lay-skin="primary" title="色素沉着" id="ct_pf" pofield="ct_pf" value="6" disabled="disabled">
						<input type="checkbox" name="ct_pf" lay-skin="primary" title="其他" id="ct_pf" pofield="ct_pf" value="7" disabled="disabled">
						<div class="layui-inline">
							<div class="layui-input-inline">
								<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="ct_pfqt" name="ct_pfqt" pofield="ct_pfqt">
							</div>
						</div>
					</div>
				</div>
				</div>
				<div class="layui-field-box">
				<div class="layui-form-item">
					<label class="layui-form-label" style="width: 100px" >巩膜</label>
					<div class="layui-input-block">
						<input type="checkbox" name="ct_gm" lay-skin="primary" title="正常" id="ct_gm" pofield="ct_gm" value="1" disabled="disabled">
						<input type="checkbox" name="ct_gm" lay-skin="primary" title="黄染" id="ct_gm" pofield="ct_gm" value="2" disabled="disabled">
						<input type="checkbox" name="ct_gm" lay-skin="primary" title="充血" id="ct_gm" pofield="ct_gm" value="3" disabled="disabled">
						<input type="checkbox" name="ct_gm" lay-skin="primary" title="其他" id="ct_gm" pofield="ct_gm" value="4" disabled="disabled">
						<div class="layui-inline">
							<div class="layui-input-inline">
								<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="ct_gmqt" name="ct_gmqt" pofield="ct_gmqt">
							</div>
						</div>
					</div>
				</div>
				</div>
				<div class="layui-field-box">
				<div class="layui-form-item">
					<label class="layui-form-label" style="width: 100px" >淋巴结</label>
					<div class="layui-input-block">
						<input type="checkbox" name="ct_lbj" lay-skin="primary" title="未触及" id="ct_lbj" pofield="ct_lbj" value="1" disabled="disabled">
						<input type="checkbox" name="ct_lbj" lay-skin="primary" title="锁骨上" id="ct_lbj" pofield="ct_lbj" value="2" disabled="disabled">
						<input type="checkbox" name="ct_lbj" lay-skin="primary" title="腋窝" id="ct_lbj" pofield="ct_lbj" value="3" disabled="disabled">
						<input type="checkbox" name="ct_lbj" lay-skin="primary" title="其他" id="ct_lbj" pofield="ct_lbj" value="4" disabled="disabled">
						<input type="checkbox" name="ct_lbj" lay-skin="primary" title="其他_" id="ct_lbj" pofield="ct_lbj" value="5" disabled="disabled">
						<div class="layui-inline">
							<div class="layui-input-inline">
								<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="ct_lbjqt" name="ct_lbjqt" pofield="ct_lbjqt">
							</div>
						</div>
					</div>
				</div>
				</div>
				<fieldset class="layui-elem-field" >
					<legend>肺</legend>
					<div class="layui-field-box">
							<div class="layui-form-item">
								<label class="layui-form-label" style="width: 100px" >桶状胸</label>
								<div class="layui-input-block">
									<input type="checkbox" name="ct_ftzx" lay-skin="primary" title="否" id="ct_ftzx" pofield="ct_ftzx" value="1" disabled="disabled">
									<input type="checkbox" name="ct_ftzx" lay-skin="primary" title="是" id="ct_ftzx" pofield="ct_ftzx" value="2" disabled="disabled">
								</div>
							</div>
						<div class="layui-form-item">
							<label class="layui-form-label" style="width: 100px" >呼吸音</label>
							<div class="layui-input-block">
								<input type="checkbox" name="ct_fhxy" lay-skin="primary" title="正常" id="ct_fhxy" pofield="ct_fhxy" value="1" disabled="disabled">
								<input type="checkbox" name="ct_fhxy" lay-skin="primary" title="异常" id="ct_fhxy" pofield="ct_fhxy" value="2" disabled="disabled">
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label" style="width: 100px" >罗音</label>
							<div class="layui-input-block">
								<input type="checkbox" name="ct_fly" lay-skin="primary" title="无" id="ct_fly" pofield="ct_fly" value="1" disabled="disabled">
								<input type="checkbox" name="ct_fly" lay-skin="primary" title="干罗音" id="ct_fly" pofield="ct_fly" value="2" disabled="disabled">
								<input type="checkbox" name="ct_fly" lay-skin="primary" title="湿罗音" id="ct_fly" pofield="ct_fly" value="3" disabled="disabled">
								<input type="checkbox" name="ct_fly" lay-skin="primary" title="其他" id="ct_fly" pofield="ct_fly" value="4" disabled="disabled">
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="ct_flyqt" name="ct_flyqt" pofield="ct_flyqt">
									</div>
								</div>
							</div>
						</div>
					</div>
				</fieldset>

				<fieldset class="layui-elem-field" >
					<legend>心脏</legend>
					<div class="layui-field-box">
						<div class="layui-form-item">
							<label class="layui-form-label">心率</label>
							<div class="layui-input-inline">
								<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="ct_xzxl" name="ct_xzxl" pofield="ct_xzxl">
							</div>次/分钟
						</div>
					</div>
					<div class="layui-field-box">
						<div class="layui-form-item">
							<label class="layui-form-label" style="width: 100px" >心律</label>
							<div class="layui-input-block">
								<input type="checkbox" name="ct_xzxinl" lay-skin="primary" title="齐" id="ct_xzxinl" pofield="ct_xzxinl" value="1" disabled="disabled">
								<input type="checkbox" name="ct_xzxinl" lay-skin="primary" title="不齐" id="ct_xzxinl" pofield="ct_xzxinl" value="2" disabled="disabled">
								<input type="checkbox" name="ct_xzxinl" lay-skin="primary" title="绝对不齐" id="ct_xzxinl" pofield="ct_xzxinl" value="3" disabled="disabled">
							</div>
						</div>
					</div>
					<div class="layui-field-box">
						<div class="layui-form-item">
							<label class="layui-form-label" style="width: 100px" >杂音</label>
							<div class="layui-input-block">
								<input type="checkbox" name="ct_xzzy" lay-skin="primary" title="无" id="ct_xzzy" pofield="ct_xzzy" value="0" disabled="disabled">
								<input type="checkbox" name="ct_xzzy" lay-skin="primary" title="有" id="ct_xzzy" pofield="ct_xzzy" value="1" disabled="disabled">
							</div>
						</div>
					</div>
				</fieldset>


				<fieldset class="layui-elem-field" >
					<legend>腹部</legend>
					<div class="layui-field-box">
						<div class="layui-form-item">
							<label class="layui-form-label">压痛</label>
							<div class="layui-input-block">
								<input type="checkbox" name="ct_fbyt" lay-skin="primary" title="无" id="ct_fbyt" pofield="ct_fbyt" value="0" disabled="disabled">
								<input type="checkbox" name="ct_fbyt" lay-skin="primary" title="有" id="ct_fbyt" pofield="ct_fbyt" value="1" disabled="disabled">
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="ct_fbytqt" name="ct_fbytqt" pofield="ct_fbytqt">
									</div>
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label" style="width: 100px" >包块</label>
							<div class="layui-input-block">
								<input type="checkbox" name="ct_fbbk" lay-skin="primary" title="无" id="ct_fbbk" pofield="ct_fbbk" value="0" disabled="disabled">
								<input type="checkbox" name="ct_fbbk" lay-skin="primary" title="有" id="ct_fbbk" pofield="ct_fbbk" value="1" disabled="disabled">
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="ct_fbbkqt" name="ct_fbbkqt" pofield="ct_fbbkqt">
									</div>
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label" style="width: 100px" >肝大</label>
							<div class="layui-input-block">
								<input type="checkbox" name="ct_fbgd" lay-skin="primary" title="无" id="ct_fbgd" pofield="ct_fbgd" value="0" disabled="disabled">
								<input type="checkbox" name="ct_fbgd" lay-skin="primary" title="有" id="ct_fbgd" pofield="ct_fbgd" value="1" disabled="disabled">
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="ct_fbgdqt" name="ct_fbgdqt" pofield="ct_fbgdqt">
									</div>
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label" style="width: 100px" >脾大</label>
							<div class="layui-input-block">
								<input type="checkbox" name="ct_fbpd" lay-skin="primary" title="无" id="ct_fbpd" pofield="ct_fbpd" value="0" disabled="disabled">
								<input type="checkbox" name="ct_fbpd" lay-skin="primary" title="有" id="ct_fbpd" pofield="ct_fbpd" value="1" disabled="disabled">
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="ct_fbpdqt" name="ct_fbpdqt" pofield="ct_fbpdqt">
									</div>
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label" style="width: 100px" >移动性浊音</label>
							<div class="layui-input-block">
								<input type="checkbox" name="ct_fbydzy" lay-skin="primary" title="无" id="ct_fbydzy" pofield="ct_fbydzy" value="0" disabled="disabled">
								<input type="checkbox" name="ct_fbydzy" lay-skin="primary" title="有" id="ct_fbydzy" pofield="ct_fbydzy" value="1" disabled="disabled">
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="ct_fbydzyqt" name="ct_fbydzyqt" pofield="ct_fbydzyqt">
									</div>
								</div>
							</div>
						</div>
					</div>
				</fieldset>
				<div class="layui-field-box">
				<div class="layui-form-item">
					<label class="layui-form-label" style="width: 100px" >下肢水肿</label>
					<div class="layui-input-block">
						<input type="checkbox" name="ct_xzsz" lay-skin="primary" title="无" id="ct_xzsz" pofield="ct_xzsz" value="1" disabled="disabled">
						<input type="checkbox" name="ct_xzsz" lay-skin="primary" title="单侧" id="ct_xzsz" pofield="ct_xzsz" value="2" disabled="disabled">
						<input type="checkbox" name="ct_xzsz" lay-skin="primary" title="双侧不对称" id="ct_xzsz" pofield="ct_xzsz" value="3" disabled="disabled">
						<input type="checkbox" name="ct_xzsz" lay-skin="primary" title="双侧对称" id="ct_xzsz" pofield="ct_xzsz" value="4" disabled="disabled">
					</div>
				</div>
				</div>
				<div class="layui-field-box">
				<div class="layui-form-item">
					<label class="layui-form-label" style="width: 100px" >足背动脉搏动</label>
					<div class="layui-input-block">
						<input type="checkbox" name="ct_zbdmbd" lay-skin="primary" title="未触及" id="ct_zbdmbd" pofield="ct_zbdmbd" value="1" disabled="disabled">
						<input type="checkbox" name="ct_zbdmbd" lay-skin="primary" title="触及双侧对称" id="ct_zbdmbd" pofield="ct_zbdmbd" value="2" disabled="disabled">
						<input type="checkbox" name="ct_zbdmbd" lay-skin="primary" title="触及左侧弱或消失" id="ct_zbdmbd" pofield="ct_zbdmbd" value="3" disabled="disabled">
						<input type="checkbox" name="ct_zbdmbd" lay-skin="primary" title="触及右侧弱或消失" id="ct_zbdmbd" pofield="ct_zbdmbd" value="4" disabled="disabled">
					</div>
				</div>
				</div>
				<div class="layui-field-box">
				<div class="layui-form-item">
					<label class="layui-form-label" style="width: 100px" >肛门指诊</label>
					<div class="layui-input-block">
						<input type="checkbox" name="ct_gmzz" lay-skin="primary" title="未见异常" id="ct_gmzz" pofield="ct_gmzz" value="1" disabled="disabled">
						<input type="checkbox" name="ct_gmzz" lay-skin="primary" title="触痛" id="ct_gmzz" pofield="ct_gmzz" value="2" disabled="disabled">
						<input type="checkbox" name="ct_gmzz" lay-skin="primary" title="包块" id="ct_gmzz" pofield="ct_gmzz" value="3" disabled="disabled">
						<input type="checkbox" name="ct_gmzz" lay-skin="primary" title="前列腺异常" id="ct_gmzz" pofield="ct_gmzz" value="4" disabled="disabled">
						<input type="checkbox" name="ct_gmzz" lay-skin="primary" title="其他" id="ct_gmzz" pofield="ct_gmzz" value="4" disabled="disabled">
						<div class="layui-inline">
							<div class="layui-input-inline">
								<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="ct_gmzzqt" name="ct_gmzzqt" pofield="ct_gmzzqt">
							</div>
						</div>
					</div>
				</div>
				</div>
				<div class="layui-field-box">
				<div class="layui-form-item">
					<label class="layui-form-label" style="width: 100px" >乳腺*</label>
					<div class="layui-input-block">
						<input type="checkbox" name="ct_rxqk" lay-skin="primary" title="未见异常" id="ct_rx_wjyc" pofield="ct_rxqk" value="1" disabled="disabled">
						<input type="checkbox" name="ct_rxqk" lay-skin="primary" title="乳房切除" id="ct_rx_rfqc" pofield="ct_rxqk" value="2" disabled="disabled">
						<input type="checkbox" name="ct_rxqk" lay-skin="primary" title="异常泌乳" id="ct_rx_ycmr" pofield="ct_rxqk" value="3" disabled="disabled">
						<input type="checkbox" name="ct_rxqk" lay-skin="primary" title="乳腺包块" id="ct_rx_rxbk" pofield="ct_rxqk" value="4" disabled="disabled">
						<input type="checkbox" name="ct_rxqk" lay-skin="primary" title="其他" id="ct_rx_qt" pofield="ct_rxqk" value="4" disabled="disabled">
						<div class="layui-inline">
							<div class="layui-input-inline">
								<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="ct_rxqt" name="ct_rxqt" pofield="ct_rxqt">
							</div>
						</div>
					</div>
				</div>
				</div>
				<fieldset class="layui-elem-field" >
					<legend>妇科</legend>
					<div class="layui-field-box">
						<div class="layui-form-item">
							<label class="layui-form-label">外阴</label>
							<div class="layui-input-block">
								<input type="checkbox" name="ct_fkwy" lay-skin="primary" title="未见异常" id="ct_fkwy" pofield="ct_fkwy" value="1" disabled="disabled">
								<input type="checkbox" name="ct_fkwy" lay-skin="primary" title="异常" id="ct_fkwy" pofield="ct_fkwy" value="2" disabled="disabled">
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="ct_fkwyqt" name="ct_fkwyqt" pofield="ct_fkwyqt">
									</div>
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">阴道</label>
							<div class="layui-input-block">
								<input type="checkbox" name="ct_fkyd" lay-skin="primary" title="未见异常" id="ct_fkyd" pofield="ct_fkyd" value="1" disabled="disabled">
								<input type="checkbox" name="ct_fkyd" lay-skin="primary" title="异常" id="ct_fkyd" pofield="ct_fkyd" value="2" disabled="disabled">
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="ct_fkydqt" name="ct_fkydqt" pofield="ct_fkydqt">
									</div>
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">宫颈</label>
							<div class="layui-input-block">
								<input type="checkbox" name="ct_fkgj" lay-skin="primary" title="未见异常" id="ct_fkgj" pofield="ct_fkgj" value="1" disabled="disabled">
								<input type="checkbox" name="ct_fkgj" lay-skin="primary" title="异常" id="ct_fkgj" pofield="ct_fkgj" value="2" disabled="disabled">
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="ct_fkgjqt" name="ct_fkgjqt" pofield="ct_fkgjqt">
									</div>
								</div>
							</div>
						</div>

						<div class="layui-form-item">
							<label class="layui-form-label">宫体*</label>
							<div class="layui-input-block">
								<input type="checkbox" name="ct_fkgt" lay-skin="primary" title="未见异常" id="ct_fkgt" pofield="ct_fkgt" value="1" disabled="disabled">
								<input type="checkbox" name="ct_fkgt" lay-skin="primary" title="异常" id="ct_fkgt" pofield="ct_fkgt" value="2" disabled="disabled">
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="ct_fkgtqt" name="ct_fkgtqt" pofield="ct_fkgtqt">
									</div>
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">附件*</label>
							<div class="layui-input-block">
								<input type="checkbox" name="ct_fkfj" lay-skin="primary" title="未见异常" id="ct_fkfj" pofield="ct_fkfj" value="1" disabled="disabled">
								<input type="checkbox" name="ct_fkfj" lay-skin="primary" title="异常" id="ct_fkfj" pofield="ct_fkfj" value="2" disabled="disabled">
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="ct_fkfjqt" name="ct_fkfjqt" pofield="ct_fkfjqt">
									</div>
								</div>
							</div>
						</div>
					</div>
				</fieldset>
				<div class="layui-field-box">
				<div class="layui-form-item">
					<label class="layui-form-label">其他</label>
					<div class="layui-inline" >
						<div class="layui-input-inline">
							<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="ct_qt" name="ct_qt" pofield="ct_qt">
						</div>
					</div>
				</div>
			</div>
			</div>
		</fieldset>
		<fieldset class="layui-elem-field" style="margin-top: 20px;">
			<legend>辅助检查</legend>
			<div class="layui-field-box">
				<div class="layui-form-item">
					<label class="layui-form-label">空腹血糖</label>
					<div class="layui-inline">
						<div class="layui-input-inline">
							<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="fzjc_kfxt" name="fzjc_kfxt" pofield="fzjc_kfxt">
						</div>
					</div>mmol/L或
					<div class="layui-inline">
						<div class="layui-input-inline">
							<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="fzjc_kfxthk" name="fzjc_kfxthk" pofield="fzjc_kfxthk">
						</div>
					</div>mg/dL
				</div>
				<fieldset class="layui-elem-field" >
					<legend>血常规</legend>
					<div class="layui-field-box">
						<div class="layui-form-item">
							<label class="layui-form-label">血红蛋白</label>
							<div class="layui-inline">
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="fzjc_xcghdb" name="fzjc_xcghdb" pofield="fzjc_xcghdb">
								</div>
							</div>g/L
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">白细胞</label>
							<div class="layui-inline">
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="fzjc_xcghdb" name="fzjc_xcghdb" pofield="fzjc_xcghdb">
								</div>
							</div>×10⁹/L
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">血小板</label>
							<div class="layui-inline">
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="fzjc_xcghdb" name="fzjc_xcghdb" pofield="fzjc_xcghdb">
								</div>
							</div>×10⁹/L
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">其他</label>
							<div class="layui-inline">
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="fzjc_xcgqt" name="fzjc_xcgqt" pofield="fzjc_xcgqt">
								</div>
							</div>
						</div>
					</div>
				</fieldset>
				<fieldset class="layui-elem-field" >
					<legend>尿常规</legend>
					<div class="layui-field-box">
						<div class="layui-form-item">
							<label class="layui-form-label">尿蛋白</label>
							<div class="layui-inline">
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="ncgndb" name="ncgndb" pofield="ncgndb">
								</div>
							</div>g/L
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">尿糖</label>
							<div class="layui-inline">
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="ncgnt" name="ncgnt" pofield="ncgnt">
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">尿酮体</label>
							<div class="layui-inline">
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="ncgntt" name="ncgntt" pofield="ncgntt">
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">尿潜血</label>
							<div class="layui-inline">
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="ncgnqx" name="ncgnqx" pofield="ncgnqx">
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">其他</label>
							<div class="layui-inline">
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="fzjc_ncgqt" name="fzjc_ncgqt" pofield="fzjc_ncgqt">
								</div>
							</div>
						</div>
					</div>
				</fieldset>
				<div class="layui-field-box">
				<div class="layui-form-item">
					<label class="layui-form-label">尿微量白蛋白</label>
					<div class="layui-inline">
						<div class="layui-input-inline">
							<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="fzjc_nwlbdb" name="fzjc_nwlbdb" pofield="fzjc_nwlbdb">
						</div>
					</div>mg/dL
				</div>
				</div>
				<div class="layui-field-box">
				<div class="layui-form-item">
					<label class="layui-form-label">大便潜血</label>
					<div class="layui-input-block">
						<input type="checkbox" name="ct_fkgt" lay-skin="primary" title="阴性" id="fzjc_dbqx" pofield="fzjc_dbqx" value="1" disabled="disabled">
						<input type="checkbox" name="ct_fkgt" lay-skin="primary" title="阳性" id="fzjc_dbqx" pofield="fzjc_dbqx" value="2" disabled="disabled">
					</div>
				</div>
				</div>
				<fieldset class="layui-elem-field" >
					<legend>肝功能</legend>
					<div class="layui-field-box">
						<div class="layui-form-item">
							<label class="layui-form-label">血清谷丙转氨酶</label>
							<div class="layui-inline">
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="fzjc_ggnxqb" name="fzjc_ggnxqb" pofield="fzjc_ggnxqb">
								</div>
							</div>U/L
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">血清谷草转氨酶</label>
							<div class="layui-inline">
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="fzjc_ggnxqc" name="fzjc_ggnxqc" pofield="fzjc_ggnxqc">
								</div>
							</div>U/L
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">白蛋白</label>
							<div class="layui-inline">
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="fzjc_ggnag" name="fzjc_ggnag" pofield="fzjc_ggnag">
								</div>
							</div>g/L
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">总胆红素</label>
							<div class="layui-inline">
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="fzjc_ggnzdh" name="fzjc_ggnzdh" pofield="fzjc_ggnzdh">
								</div>
							</div>μmol/L
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">结合胆红素</label>
							<div class="layui-inline">
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="fzjc_ggnjhd" name="fzjc_ggnjhd" pofield="fzjc_ggnjhd">
								</div>
							</div>μmol/L
						</div>
					</div>
				</fieldset>
				<fieldset class="layui-elem-field" >
					<legend>肾功能</legend>
					<div class="layui-field-box">
						<div class="layui-form-item">
							<label class="layui-form-label">血清肌酐</label>
							<div class="layui-inline">
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="fzjc_sgnqjg" name="fzjc_sgnqjg" pofield="fzjc_sgnqjg">
								</div>
							</div>μmol/L
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">血尿素氮</label>
							<div class="layui-inline">
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="fzjc_sgnnsd" name="fzjc_sgnnsd" pofield="fzjc_sgnnsd">
								</div>
							</div>mmol/L
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">血钾浓度</label>
							<div class="layui-inline">
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="fzjc_sgnjnd" name="fzjc_sgnjnd" pofield="fzjc_sgnjnd">
								</div>
							</div>mmol/L
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">血钠浓度</label>
							<div class="layui-inline">
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="fzjc_sgnnnd" name="fzjc_sgnnnd" pofield="fzjc_sgnnnd">
								</div>
							</div>mmol/L
						</div>
					</div>
				</fieldset>
			</div>
		</fieldset>

		<fieldset class="layui-elem-field" style="margin-top: 20px;">
			<div class="layui-form-item">
				<fieldset class="layui-elem-field" >
					<legend>血  脂</legend>
					<div class="layui-field-box">
						<div class="layui-form-item">
							<label class="layui-form-label">总胆固醇</label>
							<div class="layui-inline">
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="fzjc_xzzdgc" name="fzjc_xzzdgc" pofield="fzjc_xzzdgc">
								</div>
							</div>μmol/L
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">甘油三脂</label>
							<div class="layui-inline">
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="fzjc_xzgysz" name="fzjc_xzgysz" pofield="fzjc_xzgysz">
								</div>
							</div>mmol/L
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">血清低密度脂蛋白胆固醇</label>
							<div class="layui-inline">
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="fzjc_xzdmdz" name="fzjc_xzdmdz" pofield="fzjc_xzdmdz">
								</div>
							</div>mmol/L
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">血清高密度脂蛋白胆固醇</label>
							<div class="layui-inline">
								<div class="layui-input-inline">
									<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="fzjc_xzgmdz" name="fzjc_xzgmdz" pofield="fzjc_xzgmdz">
								</div>
							</div>mmol/L
						</div>
					</div>
				</fieldset>
				<div class="layui-field-box">
				<div class="layui-form-item">
					<label class="layui-form-label">糖化血红蛋白</label>
					<div class="layui-inline">
						<div class="layui-input-inline">
							<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="fzjc_thxhdb" name="fzjc_thxhdb" pofield="fzjc_thxhdb">
						</div>
					</div>%
				</div>
				</div>
				<div class="layui-field-box">
				<div class="layui-form-item">
					<label class="layui-form-label">乙型肝炎表面抗原</label>
					<div class="layui-input-block">
						<input type="checkbox" name="fzjc_yxgyky" lay-skin="primary" title="阴性" id="fzjc_yxgyky" pofield="fzjc_yxgyky" value="1" disabled="disabled">
						<input type="checkbox" name="fzjc_yxgyky" lay-skin="primary" title="阳性" id="fzjc_yxgyky" pofield="fzjc_yxgyky" value="2" disabled="disabled">
					</div>
				</div>
				</div>
				<div class="layui-field-box">
				<div class="layui-form-item">
					<label class="layui-form-label">心电图*</label>
					<div class="layui-input-block">
						<input type="checkbox" name="fzjc_xdt" lay-skin="primary" title="正常" id="fzjc_xdt" pofield="fzjc_xdt" value="1" disabled="disabled">
						<input type="checkbox" name="fzjc_xdt" lay-skin="primary" title="异常" id="fzjc_xdt" pofield="fzjc_xdt" value="2" disabled="disabled">
						<div class="layui-inline">
							<div class="layui-input-inline">
								<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="fzjc_xdtqt" name="fzjc_xdtqt" pofield="fzjc_xdtqt">
							</div>
						</div>
					</div>
				</div>
				</div>
				<div class="layui-field-box">
				<div class="layui-form-item">
					<label class="layui-form-label">胸部X线片*</label>
					<div class="layui-input-block">
						<input type="checkbox" name="fzjc_xbxxp" lay-skin="primary" title="正常" id="fzjc_xbxxp" pofield="fzjc_xbxxp" value="1" disabled="disabled">
						<input type="checkbox" name="fzjc_xbxxp" lay-skin="primary" title="异常" id="fzjc_xbxxp" pofield="fzjc_xbxxp" value="2" disabled="disabled">
						<div class="layui-inline">
							<div class="layui-input-inline">
								<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="fzjc_xbxxqt" name="fzjc_xbxxqt" pofield="fzjc_xbxxqt">
							</div>
						</div>
					</div>
				</div>
				</div>
				<div class="layui-field-box">
				<div class="layui-form-item">
					<label class="layui-form-label">B  超*</label>
					<div class="layui-input-block">
						<input type="checkbox" name="fzjc_bc" lay-skin="primary" title="正常" id="fzjc_bc" pofield="fzjc_bc" value="1" disabled="disabled">
						<input type="checkbox" name="fzjc_bc" lay-skin="primary" title="异常" id="fzjc_bc" pofield="fzjc_bc" value="2" disabled="disabled">
						<div class="layui-inline">
							<div class="layui-input-inline">
								<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="fzjc_bcqt" name="fzjc_bcqt" pofield="fzjc_bcqt">
							</div>
						</div>
					</div>
				</div>
				</div>
				<div class="layui-field-box">
				<div class="layui-form-item">
					<label class="layui-form-label">宫颈涂片</label>
					<div class="layui-input-block">
						<input type="checkbox" name="fzjc_gjtp" lay-skin="primary" title="正常" id="fzjc_gjtp" pofield="fzjc_gjtp" value="1" disabled="disabled">
						<input type="checkbox" name="fzjc_gjtp" lay-skin="primary" title="异常" id="fzjc_gjtp" pofield="fzjc_gjtp" value="2" disabled="disabled">
						<div class="layui-inline">
							<div class="layui-input-inline">
								<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="fzjc_gjtpqt" name="fzjc_gjtpqt" pofield="fzjc_gjtpqt">
							</div>
						</div>
					</div>
				</div>
				</div>
				<div class="layui-field-box">
				<div class="layui-form-item">
					<label class="layui-form-label">其他</label>
						<div class="layui-inline">
							<div class="layui-input-inline">
								<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="fzjc_qt0000" name="fzjc_qt0000" pofield="fzjc_qt0000">
							</div>
					</div>
				</div>
				</div>
				<fieldset class="layui-elem-field" >
					<legend>中医体质辨识</legend>
					<div class="layui-field-box">
						<div class="layui-form-item">
							<label class="layui-form-label">平和质</label>
							<div class="layui-input-block">
								<input type="checkbox" name="zytzbs_phz" lay-skin="primary" title="是" id="zytzbs_phz" pofield="zytzbs_phz" value="1" disabled="disabled">
								<input type="checkbox" name="zytzbs_phz" lay-skin="primary" title="基本是" id="zytzbs_phz" pofield="zytzbs_phz" value="2" disabled="disabled">
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">气虚质</label>
							<div class="layui-input-block">
								<input type="checkbox" name="zytzbs_qxz" lay-skin="primary" title="是" id="zytzbs_qxz" pofield="zytzbs_qxz" value="1" disabled="disabled">
								<input type="checkbox" name="zytzbs_qxz" lay-skin="primary" title="倾向是" id="zytzbs_qxz" pofield="zytzbs_qxz" value="2" disabled="disabled">
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">阳虚质</label>
							<div class="layui-input-block">
								<input type="checkbox" name="zytzbs_yangxz" lay-skin="primary" title="是" id="zytzbs_yangxz" pofield="zytzbs_yangxz" value="1" disabled="disabled">
								<input type="checkbox" name="zytzbs_yangxz" lay-skin="primary" title="倾向是" id="zytzbs_yangxz" pofield="zytzbs_yangxz" value="2" disabled="disabled">
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">阴虚质</label>
							<div class="layui-input-block">
								<input type="checkbox" name="zytzbs_yinxz" lay-skin="primary" title="是" id="zytzbs_yinxz" pofield="zytzbs_yinxz" value="1" disabled="disabled">
								<input type="checkbox" name="zytzbs_yinxz" lay-skin="primary" title="倾向是" id="zytzbs_yinxz" pofield="zytzbs_yinxz" value="2" disabled="disabled">
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">痰湿质</label>
							<div class="layui-input-block">
								<input type="checkbox" name="zytzbs_tsz" lay-skin="primary" title="是" id="zytzbs_tsz" pofield="zytzbs_tsz" value="1" disabled="disabled">
								<input type="checkbox" name="zytzbs_tsz" lay-skin="primary" title="倾向是" id="zytzbs_tsz" pofield="zytzbs_tsz" value="2" disabled="disabled">
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">湿热质</label>
							<div class="layui-input-block">
								<input type="checkbox" name="zytzbs_srz" lay-skin="primary" title="是" id="zytzbs_srz" pofield="zytzbs_srz" value="1" disabled="disabled">
								<input type="checkbox" name="zytzbs_srz" lay-skin="primary" title="倾向是" id="zytzbs_srz" pofield="zytzbs_srz" value="2" disabled="disabled">
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">血瘀质</label>
							<div class="layui-input-block">
								<input type="checkbox" name="zytzbs_xyz" lay-skin="primary" title="是" id="zytzbs_xyz" pofield="zytzbs_xyz" value="1" disabled="disabled">
								<input type="checkbox" name="zytzbs_xyz" lay-skin="primary" title="倾向是" id="zytzbs_xyz" pofield="zytzbs_xyz" value="2" disabled="disabled">
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">气郁质</label>
							<div class="layui-input-block">
								<input type="checkbox" name="zytzbs_qyz" lay-skin="primary" title="是" id="zytzbs_qyz" pofield="zytzbs_qyz" value="1" disabled="disabled">
								<input type="checkbox" name="zytzbs_qyz" lay-skin="primary" title="倾向是" id="zytzbs_qyz" pofield="zytzbs_qyz" value="2" disabled="disabled">
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">特秉质</label>
							<div class="layui-input-block">
								<input type="checkbox" name="zytzbs_tbz" lay-skin="primary" title="是" id="zytzbs_tbz" pofield="zytzbs_tbz" value="1" disabled="disabled">
								<input type="checkbox" name="zytzbs_tbz" lay-skin="primary" title="倾向是" id="zytzbs_tbz" pofield="zytzbs_tbz" value="2" disabled="disabled">
							</div>
						</div>
					</div>
				</fieldset>
				<fieldset class="layui-elem-field" >
					<legend>现存主要健康问题</legend>
					<div class="layui-field-box">
						<div class="layui-form-item">
							<label class="layui-form-label">脑血管疾病</label>
							<div class="layui-input-block">
								<input type="checkbox" name="zyjkwt_nxg" lay-skin="primary" title="未发现" id="zyjkwt_nxgfirst" pofield="zyjkwt_nxg" value="1" disabled="disabled">
								<input type="checkbox" name="zyjkwt_nxg" lay-skin="primary" title="缺血性卒中" id="zyjkwt_nxgzz" pofield="zyjkwt_nxg" value="2" disabled="disabled">
								<input type="checkbox" name="zyjkwt_nxg" lay-skin="primary" title="脑出血" id="zyjkwt_nxgncx" pofield="zyjkwt_nxg" value="3" disabled="disabled">
								<input type="checkbox" name="zyjkwt_nxg" lay-skin="primary" title="蛛网膜下腔出血" id="zyjkwt_nxgzwm" pofield="zyjkwt_nxg" value="4" disabled="disabled">
								<input type="checkbox" name="zyjkwt_nxg" lay-skin="primary" title="短暂性脑缺血发作" id="zyjkwt_nxgnqx" pofield="zyjkwt_nxg" value="5" disabled="disabled">
								<input type="checkbox" name="zyjkwt_nxg" lay-skin="primary" title="其他" id="zyjkwt_nxglast" pofield="zyjkwt_nxg" value="6" disabled="disabled">
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="zyjkwt_nxgqt" name="zyjkwt_nxgqt" pofield="zyjkwt_nxgqt">
									</div>
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">肾脏疾病</label>
							<div class="layui-input-block">
								<input type="checkbox" name="zyjkwt_sz" lay-skin="primary" title="未发现" id="zyjkwt_szfirst" pofield="zyjkwt_sz" value="1" disabled="disabled">
								<input type="checkbox" name="zyjkwt_sz" lay-skin="primary" title="糖尿病肾病" id="zyjkwt_sztnsb" pofield="zyjkwt_sz" value="2" disabled="disabled">
								<input type="checkbox" name="zyjkwt_sz" lay-skin="primary" title="肾功能衰竭" id="zyjkwt_szsgn" pofield="zyjkwt_sz" value="3" disabled="disabled">
								<input type="checkbox" name="zyjkwt_sz" lay-skin="primary" title="急性肾炎" id="zyjkwt_szjxsy" pofield="zyjkwt_sz" value="4" disabled="disabled">
								<input type="checkbox" name="zyjkwt_sz" lay-skin="primary" title="慢性肾炎" id="zyjkwt_szmxsy" pofield="zyjkwt_sz" value="5" disabled="disabled">
								<input type="checkbox" name="zyjkwt_sz" lay-skin="primary" title="其他" id="zyjkwt_szlast" pofield="zyjkwt_sz" value="6" disabled="disabled">
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="zyjkwt_szqt" name="zyjkwt_szqt" pofield="zyjkwt_szqt">
									</div>
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">心脏疾病</label>
							<div class="layui-input-block">
								<input type="checkbox" name="zyjkwt_xzwfx" lay-skin="primary" title="未发现" id="zyjkwt_xzfirst" pofield="zyjkwt_xzwfx" value="1" disabled="disabled">
								<input type="checkbox" name="zyjkwt_xzwfx" lay-skin="primary" title="心肌梗死" id="zyjkwt_xzxjgs" pofield="zyjkwt_xzwfx" value="2" disabled="disabled">
								<input type="checkbox" name="zyjkwt_xzwfx" lay-skin="primary" title="心绞痛" id="zyjkwt_xzxjt" pofield="zyjkwt_xzwfx" value="3" disabled="disabled">
								<input type="checkbox" name="zyjkwt_xzwfx" lay-skin="primary" title="冠状动脉血运重建" id="zyjkwt_xzgzdm" pofield="zyjkwt_xzwfx" value="4" disabled="disabled">
								<input type="checkbox" name="zyjkwt_xzwfx" lay-skin="primary" title="充血性心力衰竭" id="zyjkwt_xzxlsj" pofield="zyjkwt_xzwfx" value="5" disabled="disabled">
								<input type="checkbox" name="zyjkwt_xzwfx" lay-skin="primary" title="心前区疼痛" id="zyjkwt_xzxqqt" pofield="zyjkwt_xzwfx" value="6" disabled="disabled">
								<input type="checkbox" name="zyjkwt_xzwfx" lay-skin="primary" title="其他" id="zyjkwt_xzlast" pofield="zyjkwt_xzwfx" value="7" disabled="disabled">
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="zyjkwt_xzqt" name="zyjkwt_xzqt" pofield="zyjkwt_xzqt">
									</div>
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">血管疾病</label>
							<div class="layui-input-block">
								<input type="checkbox" name="zyjkwt_xgwfx" lay-skin="primary" title="未发现" id="zyjkwt_xgfirst" pofield="zyjkwt_xgwfx" value="1" disabled="disabled">
								<input type="checkbox" name="zyjkwt_xgwfx" lay-skin="primary" title="夹层动脉瘤" id="zyjkwt_xgdml" pofield="zyjkwt_xgwfx" value="2" disabled="disabled">
								<input type="checkbox" name="zyjkwt_xgwfx" lay-skin="primary" title="动脉闭塞性疾病" id="zyjkwt_xgdmjb" pofield="zyjkwt_xgwfx" value="3" disabled="disabled">
								<input type="checkbox" name="zyjkwt_xgwfx" lay-skin="primary" title="其他" id="zyjkwt_xglast" pofield="zyjkwt_xgwfx" value="4" disabled="disabled">
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="zyjkwt_xgqt" name="zyjkwt_xgqt" pofield="zyjkwt_xgqt">
									</div>
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">眼部疾病</label>
							<div class="layui-input-block">
								<input type="checkbox" name="zyjkwt_ybwfx" lay-skin="primary" title="未发现" id="zyjkwt_ybfirst" pofield="zyjkwt_ybwfx" value="1" disabled="disabled" style="margin-top:-25px; ">
								<input type="checkbox" name="zyjkwt_ybwfx" lay-skin="primary" title="视网膜出血或渗出" id="zyjkwt_ybswm" pofield="zyjkwt_ybwfx" value="2" disabled="disabled">
								<input type="checkbox" name="zyjkwt_ybwfx" lay-skin="primary" title="视乳头水肿" id="zyjkwt_ybsrsz" pofield="zyjkwt_ybwfx" value="3" disabled="disabled">
								<input type="checkbox" name="zyjkwt_ybwfx" lay-skin="primary" title="白内障 " id="zyjkwt_ybbnz" pofield="zyjkwt_ybwfx" value="4" disabled="disabled"><br>
								<input type="checkbox" name="zyjkwt_ybwfx" lay-skin="primary" title="其他" id="zyjkwt_yblast" pofield="zyjkwt_ybwfx" value="7" disabled="disabled">
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="zyjkwt_ybqt" name="zyjkwt_ybqt" pofield="zyjkwt_ybqt">
									</div>
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">神经系统疾病</label>
							<div class="layui-input-block">
								<input type="checkbox" name="zyjkwt_sjxtjb" lay-skin="primary" title="未发现" id="zyjkwt_sjxtjb" pofield="zyjkwt_sjxtjb" value="1" disabled="disabled">
								<input type="checkbox" name="zyjkwt_sjxtjb" lay-skin="primary" title="有" id="zyjkwt_sjxtjb" pofield="zyjkwt_sjxtjb" value="2" disabled="disabled">
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="zyjkwt_sjxtqt" name="zyjkwt_sjxtqt" pofield="zyjkwt_sjxtqt">
									</div>
								</div>
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">其他系统疾病</label>
							<div class="layui-input-block">
								<input type="checkbox" name="zyjkwt_qtxtjb" lay-skin="primary" title="未发现" id="zyjkwt_qtxtjb" pofield="zyjkwt_qtxtjb" value="1" disabled="disabled">
								<input type="checkbox" name="zyjkwt_qtxtjb" lay-skin="primary" title="有" id="zyjkwt_qtxtjb" pofield="zyjkwt_qtxtjb" value="2" disabled="disabled">
								<div class="layui-inline">
									<div class="layui-input-inline">
										<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="zyjkwt_qtxtqt" name="zyjkwt_qtxtqt" pofield="zyjkwt_qtxtqt">
									</div>
								</div>
							</div>
						</div>
					</div>
				</fieldset>


			</div>
		</fieldset>

		</div>

	</form>

</body>
<script type="text/javascript" src="js/view_healthfile_detail.js?v=1.0" charset="UTF-8"></script>
</html>