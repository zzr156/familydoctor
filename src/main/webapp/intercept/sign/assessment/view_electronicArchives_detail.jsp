<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<%@ include file="/open/commonjs/tldLayui.jsp"%>
<title>居民健康档案调阅</title>
</head>
<body>
	<input type="hidden" val="test" />
	<div id="btns"></div>
	<div align="center"></div>

	<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
		<legend>居民健康档案</legend>
	</fieldset>

	<form class="layui-form layui-form-pane" id="electronicArchivesForm">
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">居民档案号</label>
				<div class="layui-input-inline">
					<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="df_id" name="df_id" pofield="df_id">
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">家庭档案号</label>
				<div class="layui-input-inline">
					<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="f_id" name="f_id" pofield="f_id">
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">个人电话</label>
				<div class="layui-input-inline">
					<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="telphone" name="telphone" pofield="telphone">
				</div>
			</div>
		</div>

		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">居民姓名</label>
				<div class="layui-input-inline">
					<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="name" name="name" pofield="name">
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">性别</label>
				<div class="layui-input-inline">
					<select name="sex" pofield="sex" id="sex" disabled="disabled">
						<option value="">--请选择--</option>
						<option value="1">男</option>
						<option value="2">女</option>
					</select>
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">出生日期</label>
				<div class="layui-input-inline">
					<input type="text" autocomplete="off" class="layui-input" id="birthday" name="birthday" pofield="birthday" readonly="readonly">
				</div>
			</div>
		</div>

		<div class="layui-form-item">
			<label class="layui-form-label">家庭地址</label>
			<div class="layui-input-block">
				<input type="text" autocomplete="off" class="layui-input" id="address" name="address" pofield="address" readonly="readonly" >
			</div>
		</div>
		
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">身份证号</label>
				<div class="layui-input-inline">
					<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="idcardno" name="idcardno" pofield="idcardno">
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">工作单位</label>
				<div class="layui-input-inline">
					<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="workplace" name="workplace" pofield="workplace">
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">本人电话</label>
				<div class="layui-input-inline">
					<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="telphone" name="telphone" pofield="telphone">
				</div>
			</div>
		</div>

		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">联系人姓名</label>
				<div class="layui-input-inline">
					<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="namecode" name="namecode" pofield="namecode">
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">联系人电话</label>
				<div class="layui-input-inline">
					<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="telphonetype" name="telphonetype" pofield="telphonetype">
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">常住类型</label>
				<div class="layui-input-inline">
					<div class="layui-input-inline">
						<select name="rprtype" pofield="rprtype" id="rprtype" disabled="disabled">
							<option value="">--请选择--</option>
							<option value="1">户籍</option>
							<option value="2">非户籍</option>
						</select>
					</div>
				</div>
			</div>
		</div>
		
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">民族</label>
				<div class="layui-input-inline">
					<select name="folk" pofield="folk" id="folk" disabled="disabled">
						<option value="">--请选择--</option>
						<option value="1">汉 族</option>
						<option value="10">拉祜族</option>
						<option value="11">纳西族</option>
						<option value="12">景颇族</option>
						<option value="13">水 族</option>
						<option value="14">怒 族</option>
						<option value="15">傈僳族</option>
						<option value="16">独龙族</option>
						<option value="17">布朗族</option>
						<option value="18">基诺族</option>
						<option value="19">羌 族</option>
						<option value="2">彝 族</option>
						<option value="20">门巴族</option>
						<option value="21">德昂族</option>
						<option value="22">阿昌族</option>
						<option value="23">普米族</option>
						<option value="24">布依族</option>
						<option value="25">珞巴族</option>
						<option value="26">仡佬族</option>
						<option value="27">回 族</option>
						<option value="28">东乡族</option>
						<option value="29">撒拉族</option>
						<option value="3">白 族</option>
						<option value="30">保安族</option>
						<option value="31">维吾尔族</option>
						<option value="32">土 族</option>
						<option value="33">裕固族</option>
						<option value="34">锡伯族</option>
						<option value="35">俄罗斯族</option>
						<option value="36">塔塔尔族</option>
						<option value="37">哈萨克族</option>
						<option value="38">柯尔克孜族</option>
						<option value="39">塔吉克族</option>
						<option value="4">藏 族</option>
						<option value="40">乌孜别克族</option>
						<option value="41">高山族</option>
						<option value="42">畲 族</option>
						<option value="43">黎 族</option>
						<option value="44">壮 族</option>
						<option value="45">瑶 族</option>
						<option value="46">京 族</option>
						<option value="47">仫佬族</option>
						<option value="48">毛南族</option>
						<option value="49">土家族</option>
						<option value="5">傣 族</option>
						<option value="50">满 族</option>
						<option value="51">朝鲜族</option>
						<option value="52">赫哲族</option>
						<option value="53">蒙古族</option>
						<option value="54">达斡尔族</option>
						<option value="55">鄂温克族</option>
						<option value="56">鄂伦春族</option>
						<option value="57">其他</option>
						<option value="59">亻革家人</option>
						<option value="6">佤 族</option>
						<option value="60">穿青人</option>
						<option value="7">侗 族</option>
						<option value="8">哈尼族</option>
						<option value="9">苗 族</option>
					</select>
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">血型</label>
				<div class="layui-input-inline">
					<select name="bloodtype" pofield="bloodtype" id="bloodtype" disabled="disabled">
						<option value="">--请选择--</option>
						<option value="1">O型</option>
						<option value="2">A型</option>
						<option value="3">B型</option>
						<option value="4">AB型</option>
						<option value="5">不详</option>
					</select>
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">RH血型</label>
				<div class="layui-input-inline">
					<select name="rhxx" pofield="rhxx" id="rhxx" disabled="disabled">
						<option value="">--请选择--</option>
						<option value="1">阳性</option>
						<option value="2">阴性</option>
						<option value="3">不详</option>
					</select>
				</div>
			</div>
		</div>
		
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">文化程度</label>
				<div class="layui-input-inline">
					<select name="cdegree" pofield="cdegree" id="cdegree" disabled="disabled">
						<option value="">--请选择--</option>
						<option value="1">文盲或半文盲</option>
						<option value="2">小学</option>
						<option value="3">初中</option>
						<option value="4">高中/技校/中专</option>
						<option value="5">大学专科及以上</option>
						<option value="6">不详</option>
					</select>
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">婚姻状况</label>
				<div class="layui-input-inline">
					<select name="mstatus" pofield="mstatus" id="mstatus" disabled="disabled">
						<option value="">--请选择--</option>
						<option value="1">未婚</option>
						<option value="2">已婚</option>
						<option value="3">丧偶</option>
						<option value="4">离婚</option>
						<option value="5">其他</option>
					</select>
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">职业状况</label>
				<div class="layui-input-inline">
					<select name="workstatus" pofield="workstatus" id="workstatus" disabled="disabled">
						<option value="">--请选择--</option>
						<option value="1">农、林、牧、渔、水利业生产人员</option>
						<option value="2">商业、服务业人员</option>
						<option value="3">专业技术人员</option>
						<option value="4">生产、运输设备操作人员及有关人员</option>
						<option value="5">办事人员和有关人员</option>
						<option value="6">国家机关、党群组织、企业、事业单位负责人</option>
						<option value="7">军人</option>
						<option value="8">不便分类的其他从业人员</option>
					</select>
				</div>
			</div>
		</div>

		<div class="layui-form-item">
			<label class="layui-form-label">医疗费用支付方式</label>
			<div class="layui-input-block">
				<input type="checkbox" name="czzgbx" lay-skin="primary" title="城镇职工基本医疗保险" id="czzgbx" pofield="czzgbx" value="1" disabled="disabled">
				<input type="checkbox" name="czjmbx" lay-skin="primary" title="城镇居民基本医疗保险" id="czjmbx" pofield="czjmbx" value="1" disabled="disabled">
				<input type="checkbox" name="xrhzyl" lay-skin="primary" title="新型农村合作医疗" id="xrhzyl" pofield="xrhzyl" value="1" disabled="disabled">
				<input type="checkbox" name="pkjz" lay-skin="primary" title="贫困救助" id="pkjz" pofield="pkjz" value="1" disabled="disabled">
				<input type="checkbox" name="syylbx" lay-skin="primary" title="商业医疗保险" id="syylbx" pofield="syylbx" value="1" disabled="disabled">
				<input type="checkbox" name="qgf" lay-skin="primary" title="全公费" id="qgf" pofield="qgf" value="1" disabled="disabled">
				<input type="checkbox" name="qzf" lay-skin="primary" title="全自费" id="qzf" pofield="qzf" value="1" disabled="disabled">
				<div class="layui-inline">
					<label class="layui-form-label">其他方式</label>
					<div class="layui-input-inline">
						<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="qtfs00" name="qtfs00" pofield="qtfs00">
					</div>
				</div>
			</div>
		</div>

		<div class="layui-form-item">
			<label class="layui-form-label">药物过敏史</label>
			<div class="layui-input-block">
				<input type="text" autocomplete="off" class="layui-input" id="sensitive" name="sensitive" pofield="sensitive" readonly="readonly" >
			</div>
		</div>
		
		<div class="layui-form-item">
			<label class="layui-form-label">暴露史</label>
			<div class="layui-input-block">
				<input type="text" autocomplete="off" class="layui-input" id="expose" name="expose" pofield="expose" readonly="readonly" >
			</div>
		</div>

		<fieldset class="layui-elem-field" style="margin-top: 20px;">
			<legend>既往史</legend>
			<div class="layui-field-box">
				<div class="layui-form-item">
					<label class="layui-form-label">疾病</label>
					<div class="layui-input-block">
						<input type="text" autocomplete="off" class="layui-input" id="jwbsjb" name="jwbsjb" pofield="jwbsjb" readonly="readonly">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">手术</label>
					<div class="layui-input-block">
						<input type="text" autocomplete="off" class="layui-input" id="jwbsss" name="jwbsss" pofield="jwbsss" readonly="readonly">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">外伤</label>
					<div class="layui-input-block">
						<input type="text" autocomplete="off" class="layui-input" id="jwbsws" name="jwbsws" pofield="jwbsws" readonly="readonly">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">输血</label>
					<div class="layui-input-block">
						<input type="text" autocomplete="off" class="layui-input" id="jwbssx" name="jwbssx" pofield="jwbssx" readonly="readonly">
					</div>
				</div>
			</div>
		</fieldset>
		
		<fieldset class="layui-elem-field" style="margin-top: 20px;">
			<legend>家族史</legend>
			<div class="layui-field-box">
				<div class="layui-form-item">
					<label class="layui-form-label">父亲</label>
					<div class="layui-input-block">
						<input type="text" autocomplete="off" class="layui-input" id="jzsfq" name="jzsfq" pofield="jzsfq" readonly="readonly">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">母亲</label>
					<div class="layui-input-block">
						<input type="text" autocomplete="off" class="layui-input" id="jzsmq" name="jzsmq" pofield="jzsmq" readonly="readonly">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">兄弟姐妹</label>
					<div class="layui-input-block">
						<input type="text" autocomplete="off" class="layui-input" id="jzsxm" name="jzsxm" pofield="jzsxm" readonly="readonly">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">子女</label>
					<div class="layui-input-block">
						<input type="text" autocomplete="off" class="layui-input" id="jzszn" name="jzszn" pofield="jzszn" readonly="readonly">
					</div>
				</div>
			</div>
		</fieldset>

		<div class="layui-form-item">
			<label class="layui-form-label">遗传病史</label>
			<div class="layui-input-block">
				<input type="text" autocomplete="off" class="layui-input" id="ycbs00" name="ycbs00" pofield="ycbs00" readonly="readonly" >
			</div>
		</div>
		
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">与户主关系</label>
				<div class="layui-input-inline">
					<select name="r_id" pofield="r_id" id="r_id" disabled="disabled">
						<option value="">--请选择--</option>
						<option value="1">户主</option>
						<option value="10">孙子</option>
						<option value="11">孙女</option>
						<option value="12">女婿</option>
						<option value="13">儿媳</option>
						<option value="14">外甥</option>
						<option value="15">外甥女</option>
						<option value="16">爷爷</option>
						<option value="17">奶奶</option>
						<option value="18">妹妹</option>
						<option value="19">哥哥</option>
						<option value="2">父亲</option>
						<option value="20">姐姐</option>
						<option value="21">弟弟</option>
						<option value="22">嫂子</option>
						<option value="23">姐夫</option>
						<option value="24">弟媳</option>
						<option value="25">妹夫</option>
						<option value="26">孙女婿</option>
						<option value="27">曾孙</option>
						<option value="28">孙媳妇</option>
						<option value="29">外孙</option>
						<option value="3">母亲</option>
						<option value="30">外孙女</option>
						<option value="31">岳父</option>
						<option value="32">岳母</option>
						<option value="33">外公</option>
						<option value="34">外婆</option>
						<option value="35">曾孙女</option>
						<option value="36">公公</option>
						<option value="37">婆婆</option>
						<option value="38">小姨子</option>
						<option value="39">小舅子</option>
						<option value="4">丈夫</option>
						<option value="40">姑妈</option>
						<option value="41">叔叔</option>
						<option value="42">婶婶</option>
						<option value="43">伯父</option>
						<option value="44">伯母</option>
						<option value="45">祖父</option>
						<option value="46">祖母</option>
						<option value="47">亲家公</option>
						<option value="48">亲家母</option>
						<option value="49">民政代养</option>
						<option value="5">妻子</option>
						<option value="50">集体成员</option>
						<option value="6">儿子</option>
						<option value="7">女儿</option>
						<option value="8">侄儿</option>
						<option value="9">侄女</option>
					</select>
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">负责医生</label>
				<div class="layui-input-inline">
					<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="zrysxm" name="zrysxm" pofield="zrysxm">
				</div>
			</div>
		</div>
		
		<div class="layui-form-item">
			<label class="layui-form-label">残疾情况</label>
			<div class="layui-input-block">
				<input type="checkbox" name="cjqk0" lay-skin="primary" title="无残疾" id="wcj" pofield="cjqk0" value="0" disabled="disabled">
				<input type="checkbox" name="cjqk0" lay-skin="primary" title="视力残疾" id="slcj" pofield="cjqk0" value="7" disabled="disabled">
				<input type="checkbox" name="cjqk0" lay-skin="primary" title="听力残疾" id="tlcj" pofield="cjqk0" value="8" disabled="disabled">
				<input type="checkbox" name="cjqk0" lay-skin="primary" title="言语残疾 " id="yycj" pofield="cjqk0" value="9" disabled="disabled">
				<input type="checkbox" name="cjqk0" lay-skin="primary" title="肢体残疾" id="ztcj" pofield="cjqk0" value="11" disabled="disabled">
				<input type="checkbox" name="cjqk0" lay-skin="primary" title="智力残疾" id="zlcj" pofield="cjqk0" value="10" disabled="disabled">
				<input type="checkbox" name="cjqk0" lay-skin="primary" title="精神残疾" id="jscj" pofield="cjqk0" value="12" disabled="disabled">
				<div class="layui-inline">
					<label class="layui-form-label">其它残疾</label>
					<div class="layui-input-inline">
						<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="cjqt0" name="cjqt0" pofield="cjqt0">
					</div>
				</div>
			</div>
		</div>
		
		<fieldset class="layui-elem-field" style="margin-top: 20px;">
			<legend>生活环境</legend>
			<div class="layui-field-box">
				<div class="layui-form-item">
					<label class="layui-form-label">厨房排风设施</label>
					<div class="layui-input-block">
						<select name="cfpfss" pofield="cfpfss" id="cfpfss" disabled="disabled">
							<option value="">--请选择--</option>
							<option value="1">无</option>
							<option value="2">油烟机</option>
							<option value="3">换气扇</option>
							<option value="4">换气扇</option>
						</select>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">燃料类型</label>
					<div class="layui-input-block">
						<select name="rllx" pofield="rllx" id="rllx" disabled="disabled">
							<option value="">--请选择--</option>
							<option value="1">液化气</option>
							<option value="2">煤</option>
							<option value="3">天然气</option>
							<option value="4">沼气</option>
							<option value="5">柴火</option>
							<option value="6">其他</option>
						</select>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">饮水</label>
					<div class="layui-input-block">
						<select name="ys" pofield="ys" id="ys" disabled="disabled">
							<option value="">--请选择--</option>
							<option value="1">自来水</option>
							<option value="2">经净化过滤的水</option>
							<option value="3">井水</option>
							<option value="4">河湖水</option>
							<option value="5">塘水</option>
							<option value="6">其他</option>
						</select>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">厕所</label>
					<div class="layui-input-block">
						<select name="cs" pofield="cs" id="cs" disabled="disabled">
							<option value="">--请选择--</option>
							<option value="1">卫生厕所</option>
							<option value="2">一格或二格粪池式</option>
							<option value="3">马桶</option>
							<option value="4">露天粪坑</option>
							<option value="5">简易棚厕</option>
						</select>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">禽畜栏</label>
					<div class="layui-input-block">
						<select name="qcl" pofield="qcl" id="qcl" disabled="disabled">
							<option value="">--请选择--</option>
							<option value="1">单设</option>
							<option value="2">室内</option>
							<option value="3">室外</option>
							<option value="4">无</option>
						</select>
					</div>
				</div>
			</div>
		</fieldset>
		
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">是否保健合同</label>
				<div class="layui-input-inline">
					<select name="ishcpact" pofield="ishcpact" id="ishcpact" disabled="disabled">
						<option value="">--请选择--</option>
						<option value="0">否</option>
						<option value="1">是</option>
					</select>
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">调查员</label>
				<div class="layui-input-inline">
					<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="dcyxm0" name="dcyxm0" pofield="dcyxm0">
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">调查日期</label>
				<div class="layui-input-inline">
					<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="idate" name="idate" pofield="idate">
				</div>
			</div>
		</div>
		
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">是否迁出</label>
				<div class="layui-input-inline">
					<select name="ismove" pofield="ismove" id="ismove" disabled="disabled">
						<option value="">--请选择--</option>
						<option value="0">否</option>
						<option value="1">是</option>
					</select>
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">迁出日期</label>
				<div class="layui-input-inline">
					<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="movedate" name="movedate" pofield="movedate">
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">建档人</label>
				<div class="layui-input-inline">
					<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="jdysxm" name="jdysxm" pofield="jdysxm">
				</div>
			</div>
		</div>
		
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">是否死亡</label>
				<div class="layui-input-inline">
					<select name="isdead" pofield="isdead" id="isdead" disabled="disabled">
						<option value="">--请选择--</option>
						<option value="0">否</option>
						<option value="1">是</option>
					</select>
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">死亡时间</label>
				<div class="layui-input-inline">
					<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="ddate" name="ddate" pofield="ddate">
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">建档日期</label>
				<div class="layui-input-inline">
					<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="jdrq00" name="jdrq00" pofield="jdrq00">
				</div>
			</div>
		</div>
		
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">录入员</label>
				<div class="layui-input-inline">
					<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="lryxm0" name="lryxm0" pofield="lryxm0">
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">最后更新人姓名</label>
				<div class="layui-input-inline">
					<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="gxygxm" name="gxygxm" pofield="gxygxm">
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">最后更新日期</label>
				<div class="layui-input-inline">
					<input type="text" autocomplete="off" class="layui-input" readonly="readonly" id="zhgxrq" name="zhgxrq" pofield="zhgxrq">
				</div>
			</div>
		</div>

	</form>

</body>
<script type="text/javascript" src="js/view_electronicArchives_detail.js?v=1.0"></script>
</html>
