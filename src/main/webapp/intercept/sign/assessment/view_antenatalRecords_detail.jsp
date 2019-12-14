<%--
  Created by IntelliJ IDEA.
  User: cjw
  Date: 2018/7/16
  Time: 11:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<%@ include file="/open/commonjs/tldLayui.jsp"%>
<script type="text/javascript" src="js/view_antenatalRecords_detail.js?v=1.0"></script>
<title>产前检查记录表</title>
</head>
<body>

	<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
		<legend>产前检查记录列表</legend>
	</fieldset>

	<table class="layui-table" id="">
		<thead>
			<th>检查日期</th>
			<th>产科门诊编号</th>
			<th>姓名</th>
			<th>实足年龄</th>
			<th>丈夫姓名</th>
			<th>户口地：县镇村</th>
			<th>电话</th>
			<th>胎/产次(G/P)</th>
			<th>预产期</th>
			<th>建卡孕周</th>
			<th>建卡情况</th>
			<th>发现早孕单位</th>
			<th>检查单位</th>
		</thead>
		<tbody id="fnbj_nkjl">

		</tbody>
	</table>
	<jsp:include page="/intercept/sign/assessment/view_antenatalRecords_list.jsp" flush="false" />

	<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
		<legend>产前检查记录详情</legend>
	</fieldset>

	<table class="layui-table" id="form_jc">
		<tr>
			<td rowspan="15">基本信息</td>
			<td>卡号</td>
			<td><input type="text" id="sbkh00" pofield="sbkh00" name="sbkh00" class="layui-input" readonly="readonly" /></td>
			<td>生育证号</td>
			<td><input type="text" id="syzh00" pofield="syzh00" name="syzh00" class="layui-input" readonly="readonly" /></td>
		</tr>
		<tr>
			<td>检查单位</td>
			<td><input type="text" id="jcdwna" pofield="jcdwna" name="jcdwna" class="layui-input" readonly="readonly" /></td>
			<td>检查日期</td>
			<td><input type="text" id="jcrq00" pofield="jcrq00" name="jcrq00" class="layui-input" readonly="readonly" /></td>
		</tr>
		<tr>
			<td>产科门诊编号</td>
			<td><input type="text" id="ckmzbh" pofield="ckmzbh" name="ckmzbh" class="layui-input" readonly="readonly" /></td>
			<td>住院号</td>
			<td><input type="text" id="zyh000" pofield="zyh000" name="zyh000" class="layui-input" readonly="readonly" /></td>
			<td>出生日期</td>
			<td><input type="text" id="csrq00" pofield="csrq00" name="csrq00" class="layui-input" readonly="readonly" /></td>
		</tr>
		<tr>
			<td>姓名</td>
			<td><input type="text" id="xm0000" pofield="xm0000" name="xm0000" class="layui-input" readonly="readonly" /></td>
			<td>身份证号</td>
			<td><input type="text" id="sfzh00" pofield="sfzh00" name="sfzh00" class="layui-input" readonly="readonly" /></td>
		</tr>
		<tr>
			<td>国籍</td>
			<td>
				<SELECT id="mqgj00" pofield="mqgj00" name="mqgj00" class="layui-input" disabled="disabled">
					<OPTION value="" spellBeginStr="qxz">请选择...</OPTION>
					<OPTION id=mqgj00_1 value=10 spellBeginStr="wkl">乌克兰</OPTION>
					<OPTION id=mqgj00_2 value=102 spellBeginStr="ydnxy">印度尼西亚</OPTION>
					<OPTION id=mqgj00_3 value=104 spellBeginStr="md">缅甸</OPTION>
					<OPTION id=mqgj00_4 value=112 spellBeginStr="bels">白俄罗斯</OPTION>
					<OPTION id=mqgj00_5 value=116 spellBeginStr="jpz">柬埔寨</OPTION>
					<OPTION id=mqgj00_6 value=12 spellBeginStr="aejly">阿尔及利亚</OPTION>
					<OPTION id=mqgj00_7 value=124 spellBeginStr="jnd">加拿大</OPTION>
					<OPTION id=mqgj00_8 value=144 spellBeginStr="sllk">斯里兰卡</OPTION>
					<OPTION id=mqgj00_9 value=152 spellBeginStr="zl">智利</OPTION>
					<OPTION id=mqgj00_10  value=156 spellBeginStr="zg">中国</OPTION>
					<OPTION id=mqgj00_11 value=158 spellBeginStr="zgtw">中国(台湾)</OPTION>
					<OPTION id=mqgj00_12 value=16 spellBeginStr="mssmy">美属萨摩亚</OPTION>
					<OPTION id=mqgj00_13 value=166 spellBeginStr="kksjlqd">科科斯(基林)群岛</OPTION>
					<OPTION id=mqgj00_14 value=170 spellBeginStr="glby">哥伦比亚</OPTION>
					<OPTION id=mqgj00_15 value=178 spellBeginStr="ggb">刚果（布）</OPTION>
					<OPTION id=mqgj00_16 value=208 spellBeginStr="dm">丹麦</OPTION>
					<OPTION id=mqgj00_17 value=214 spellBeginStr="dmnj">多米尼加</OPTION>
					<OPTION id=mqgj00_18 value=218 spellBeginStr="egde">厄瓜多尔</OPTION>
					<OPTION id=mqgj00_19 value=250 spellBeginStr="fg">法国</OPTION>
					<OPTION id=mqgj00_20 value=275 spellBeginStr="blst">巴勒斯坦</OPTION>
					<OPTION id=mqgj00_21 value=276 spellBeginStr="dg">德国</OPTION>
					<OPTION id=mqgj00_22 value=288 spellBeginStr="jn">加纳</OPTION>
					<OPTION id=mqgj00_23 value=32 spellBeginStr="agt">阿根廷</OPTION>
					<OPTION id=mqgj00_24 value=344 spellBeginStr="zgxg">中国(香港)</OPTION>
					<OPTION id=mqgj00_25 value=348 spellBeginStr="xyl">匈牙利</OPTION>
					<OPTION id=mqgj00_26 value=356 spellBeginStr="yd">印度</OPTION>
					<OPTION id=mqgj00_27 value=36 spellBeginStr="adly">澳大利亚</OPTION>
					<OPTION id=mqgj00_28 value=372 spellBeginStr="ael">爱尔兰</OPTION>
					<OPTION id=mqgj00_29 value=380 spellBeginStr="ydl">意大利</OPTION>
					<OPTION id=mqgj00_30 value=388 spellBeginStr="ymj">牙买加</OPTION>
					<OPTION id=mqgj00_31 value=392 spellBeginStr="rb">日本</OPTION>
					<OPTION id=mqgj00_32 value=400 spellBeginStr="yd">约旦</OPTION>
					<OPTION id=mqgj00_33 value=410 spellBeginStr="hg">韩国</OPTION>
					<OPTION id=mqgj00_34 value=418 spellBeginStr="lw">老挝</OPTION>
					<OPTION id=mqgj00_35 value=446 spellBeginStr="zgam">中国(澳门)</OPTION>
					<OPTION id=mqgj00_36 value=450 spellBeginStr="mdjsj">马达加斯加</OPTION>
					<OPTION id=mqgj00_37 value=458 spellBeginStr="mlxy">马来西亚</OPTION>
					<OPTION id=mqgj00_38 value=484 spellBeginStr="mxg">墨西哥</OPTION>
					<OPTION id=mqgj00_39 value=496 spellBeginStr="mg">蒙古</OPTION>
					<OPTION id=mqgj00_40 value=498 spellBeginStr="medw">摩尔多瓦</OPTION>
					<OPTION id=mqgj00_41 value=50 spellBeginStr="mjlg">孟加拉国</OPTION>
					<OPTION id=mqgj00_42 value=528 spellBeginStr="hl">荷兰</OPTION>
					<OPTION id=mqgj00_43 value=548 spellBeginStr="wnat">瓦努阿图</OPTION>
					<OPTION id=mqgj00_44 value=554 spellBeginStr="xxl">新西兰</OPTION>
					<OPTION id=mqgj00_45 value=56 spellBeginStr="bls">比利时</OPTION>
					<OPTION id=mqgj00_46 value=578 spellBeginStr="nw">挪威</OPTION>
					<OPTION id=mqgj00_47 value=584 spellBeginStr="mseqd">马绍尔群岛</OPTION>
					<OPTION id=mqgj00_48 value=586 spellBeginStr="bjst">巴基斯坦</OPTION>
					<OPTION id=mqgj00_49 value=598 spellBeginStr="bbyxjny">巴布亚新几内亚</OPTION>
					<OPTION id=mqgj00_50 value=604 spellBeginStr="ml">秘鲁</OPTION>
					<OPTION id=mqgj00_51 value=608 spellBeginStr="flb">菲律宾</OPTION>
					<OPTION id=mqgj00_52 value=620 spellBeginStr="pty">葡萄牙</OPTION>
					<OPTION id=mqgj00_53 value=643 spellBeginStr="elslb">俄罗斯联邦</OPTION>
					<OPTION id=mqgj00_54 value=702 spellBeginStr="xjp">新加坡</OPTION>
					<OPTION id=mqgj00_55 value=704 spellBeginStr="yn">越南</OPTION>
					<OPTION id=mqgj00_56 value=710 spellBeginStr="nf">南非</OPTION>
					<OPTION id=mqgj00_57 value=724 spellBeginStr="xby">西班牙</OPTION>
					<OPTION id=mqgj00_58 value=740 spellBeginStr="sln">苏里南</OPTION>
					<OPTION id=mqgj00_59 value=752 spellBeginStr="rd">瑞典</OPTION>
					<OPTION id=mqgj00_60 value=756 spellBeginStr="rs">瑞士</OPTION>
					<OPTION id=mqgj00_61 value=76 spellBeginStr="bx">巴西</OPTION>
					<OPTION id=mqgj00_62 value=764 spellBeginStr="tg">泰国</OPTION>
					<OPTION id=mqgj00_63 value=826 spellBeginStr="yg">英国</OPTION>
					<OPTION id=mqgj00_64 value=840 spellBeginStr="mg">美国</OPTION>
					<OPTION id=mqgj00_65 value=887 spellBeginStr="ym">也门</OPTION>
				</SELECT>
			</td>
			<td>身份证类别</td>
			<td>
				<SELECT class="layui-input" id="mqsfzl" pofield="mqsfzl" name="mqsfzl" disabled="disabled">
					<OPTION value="" spellBeginStr="qxz">请选择...</OPTION>
					<OPTION id=mqsfzl_1 value=1 spellBeginStr="sfz">身份证</OPTION>
					<OPTION id=mqsfzl_2 value=2 spellBeginStr="hz">护照</OPTION>
					<OPTION id=mqsfzl_3 value=4 spellBeginStr="lk">绿卡</OPTION>
					<OPTION id=mqsfzl_4 value=fy01 spellBeginStr="jmsfz">居民身份证</OPTION>
					<OPTION id=mqsfzl_5 value=fy02 spellBeginStr="jmhkb">居民户口簿</OPTION>
					<OPTION id=mqsfzl_6 value=fy04 spellBeginStr="jgz">军官证</OPTION>
					<OPTION id=mqsfzl_7 value=fy05 spellBeginStr="jsz">驾驶证</OPTION>
					<OPTION id=mqsfzl_8 value=fy06 spellBeginStr="gajmlwndtxz">港澳居民来往内地通行证</OPTION>
					<OPTION id=mqsfzl_9 value=fy07 spellBeginStr="twjmlwndtxz">台湾居民来往内地通行证</OPTION>
					<OPTION id=mqsfzl_10 value=fy99 spellBeginStr="qtfdyxzj">其他法定有效证件</OPTION>
				</SELECT>
			</td>
			<td>实足年龄</td>
			<td><input type="text" id="sznl00" pofield="sznl00" name="sznl00" class="layui-input" readonly="readonly"/>岁</td>
		</tr>
		<tr>
			<td>籍贯</td>
			<td><input type="text" id="jg0000" pofield="jg0000" name="jg0000" class="layui-input" readonly="readonly" /></td>
			<td>联系电话</td>
			<td><input type="text" id="lxdh00" pofield="lxdh00" name="lxdh00" class="layui-input" readonly="readonly" /></td>
			<td>居民档案号</td>
			<td><input type="text" id="jkdah0" pofield="jkdah0" name="jkdah0" class="layui-input" readonly="readonly" /></td>
		</tr>
		<tr>
			<td>文化程度</td>
			<td>
				<SELECT id="whcd00" pofield="whcd00" name="whcd00" class="layui-input" disabled="disabled">
					<OPTION value="" spellBeginStr="qxz">请选择...</OPTION>
					<OPTION id=whcd00_1 value=1 spellBeginStr="wmhbwm">文盲或半文盲</OPTION>
					<OPTION id=whcd00_2 value=10 spellBeginStr="zz">中专</OPTION>
					<OPTION id=whcd00_3 value=2 spellBeginStr="xx">小学</OPTION>
					<OPTION id=whcd00_4 value=3 spellBeginStr="cz">初中</OPTION>
					<OPTION id=whcd00_5 value=5 spellBeginStr="dxzkjys">大学专科及以上</OPTION>
					<OPTION id=whcd00_6 value=6 spellBeginStr="bx">不详</OPTION>
					<OPTION id=whcd00_7 value=7 spellBeginStr="yjsys">研究生以上</OPTION>
					<OPTION id=whcd00_8 value=8 spellBeginStr="gz">高中</OPTION>
					<OPTION id=whcd00_9 value=9 spellBeginStr="jx">技校</OPTION>
				</SELECT>
			</td>
			<td>邮政编码</td>
			<td><input type="text" id="yzbm00" pofield="yzbm00" name="yzbm00" class="layui-input" disabled="disabled" /></td>
			<td>民族</td>
			<td>
				<SELECT id="mz0000" pofield="mz0000" name="mz0000" class="layui-input" disabled="disabled">
					<OPTION value="" spellBeginStr="qxz">请选择...</OPTION>
					<OPTION id=mz0000_1 value=1 spellBeginStr="hz">汉族</OPTION>
					<OPTION id=mz0000_2 value=10 spellBeginStr="lz">拉祜族</OPTION>
					<OPTION id=mz0000_3 value=11 spellBeginStr="nxz">纳西族</OPTION>
					<OPTION id=mz0000_4 value=12 spellBeginStr="jpz">景颇族</OPTION>
					<OPTION id=mz0000_5 value=13 spellBeginStr="sz">水 族</OPTION>
					<OPTION id=mz0000_6 value=14 spellBeginStr="nz">怒 族</OPTION>
					<OPTION id=mz0000_7 value=15 spellBeginStr="lsz">傈僳族</OPTION>
					<OPTION id=mz0000_8 value=16 spellBeginStr="dlz">独龙族</OPTION>
					<OPTION id=mz0000_9 value=17 spellBeginStr="blz">布朗族</OPTION>
					<OPTION id=mz0000_10 value=18 spellBeginStr="jnz">基诺族</OPTION>
					<OPTION id=mz0000_11 value=19 spellBeginStr="qz">羌 族</OPTION>
					<OPTION id=mz0000_12 value=2 spellBeginStr="yz">彝 族</OPTION>
					<OPTION id=mz0000_13 value=20 spellBeginStr="mbz">门巴族</OPTION>
					<OPTION id=mz0000_14 value=21 spellBeginStr="daz">德昂族</OPTION>
					<OPTION id=mz0000_15 value=22 spellBeginStr="acz">阿昌族</OPTION>
					<OPTION id=mz0000_16 value=23 spellBeginStr="pmz">普米族</OPTION>
					<OPTION id=mz0000_17 value=24 spellBeginStr="byz">布依族</OPTION>
					<OPTION id=mz0000_18 value=25 spellBeginStr="bz">珞巴族</OPTION>
					<OPTION id=mz0000_19 value=26 spellBeginStr="lz">仡佬族</OPTION>
					<OPTION id=mz0000_20 value=27 spellBeginStr="hz">回 族</OPTION>
					<OPTION id=mz0000_21 value=28 spellBeginStr="dxz">东乡族</OPTION>
					<OPTION id=mz0000_22 value=29 spellBeginStr="slz">撒拉族</OPTION>
					<OPTION id=mz0000_23 value=3 spellBeginStr="bz">白 族</OPTION>
					<OPTION id=mz0000_24 value=30 spellBeginStr="baz">保安族</OPTION>
					<OPTION id=mz0000_25 value=31 spellBeginStr="wwez">维吾尔族</OPTION>
					<OPTION id=mz0000_26 value=32 spellBeginStr="tz">土 族</OPTION>
					<OPTION id=mz0000_27 value=33 spellBeginStr="ygz">裕固族</OPTION>
					<OPTION id=mz0000_28 value=34 spellBeginStr="xbz">锡伯族</OPTION>
					<OPTION id=mz0000_29 value=35 spellBeginStr="elsz">俄罗斯族</OPTION>
					<OPTION id=mz0000_30 value=36 spellBeginStr="ttez">塔塔尔族</OPTION>
					<OPTION id=mz0000_31 value=37 spellBeginStr="hskz">哈萨克族</OPTION>
					<OPTION id=mz0000_32 value=38 spellBeginStr="kekzz">柯尔克孜族</OPTION>
					<OPTION id=mz0000_33 value=39 spellBeginStr="tjkz">塔吉克族</OPTION>
					<OPTION id=mz0000_34 value=4 spellBeginStr="cz">藏 族</OPTION>
					<OPTION id=mz0000_35 value=40 spellBeginStr="wzbkz">乌孜别克族</OPTION>
					<OPTION id=mz0000_36 value=41 spellBeginStr="gsz">高山族</OPTION>
					<OPTION id=mz0000_37 value=42 spellBeginStr="z">畲 族</OPTION>
					<OPTION id=mz0000_38 value=43 spellBeginStr="lz">黎 族</OPTION>
					<OPTION id=mz0000_39 value=44 spellBeginStr="zz">壮 族</OPTION>
					<OPTION id=mz0000_40 value=45 spellBeginStr="yz">瑶 族</OPTION>
					<OPTION id=mz0000_41 value=46 spellBeginStr="jz">京 族</OPTION>
					<OPTION id=mz0000_42 value=47 spellBeginStr="lz">仫佬族</OPTION>
					<OPTION id=mz0000_43 value=48 spellBeginStr="mnz">毛南族</OPTION>
					<OPTION id=mz0000_44 value=49 spellBeginStr="tjz">土家族</OPTION>
					<OPTION id=mz0000_45 value=5 spellBeginStr="dz">傣 族</OPTION>
					<OPTION id=mz0000_46 value=50 spellBeginStr="mz">满 族</OPTION>
					<OPTION id=mz0000_47 value=51 spellBeginStr="cxz">朝鲜族</OPTION>
					<OPTION id=mz0000_48 value=52 spellBeginStr="hzz">赫哲族</OPTION>
					<OPTION id=mz0000_49 value=53 spellBeginStr="mgz">蒙古族</OPTION>
					<OPTION id=mz0000_50 value=54 spellBeginStr="dwez">达斡尔族</OPTION>
					<OPTION id=mz0000_51 value=55 spellBeginStr="ewkz">鄂温克族</OPTION>
					<OPTION id=mz0000_52 value=56 spellBeginStr="elcz">鄂伦春族</OPTION>
					<OPTION id=mz0000_53 value=57 spellBeginStr="qt">其他</OPTION>
					<OPTION id=mz0000_54 value=59 spellBeginStr="gjr">亻革家人</OPTION>
					<OPTION id=mz0000_55 value=6 spellBeginStr="z">佤 族</OPTION>
					<OPTION id=mz0000_56 value=60 spellBeginStr="cqr">穿青人</OPTION>
					<OPTION id=mz0000_57 value=7 spellBeginStr="dz">侗 族</OPTION>
					<OPTION id=mz0000_58 value=8 spellBeginStr="hnz">哈尼族</OPTION>
					<OPTION id=mz0000_59 value=9 spellBeginStr="mz">苗 族</OPTION>
				</SELECT>
			</td>
		</tr>
		<tr>
			<td>工作单位</td>
			<td><input type="text" id="gzdw00" pofield="gzdw00" name="gzdw00" class="layui-input" readonly="readonly" /></td>
			<td>职业</td>
			<td>
				<SELECT id="zy0000" pofield="zy0000" name="zy0000" class="layui-input" disabled="disabled">
					<OPTION value="" spellBeginStr="qxz">请选择...</OPTION>
					<OPTION id=zy0000_1 value=01 spellBeginStr="xsdzxxs">学生(大中小学生)</OPTION>
					<OPTION id=zy0000_2 value=02 spellBeginStr="js">教师</OPTION>
					<OPTION id=zy0000_3 value=03 spellBeginStr="byyjbm">保育员及保姆</OPTION>
					<OPTION id=zy0000_4 value=04 spellBeginStr="cyspy">餐饮食品业</OPTION>
					<OPTION id=zy0000_5 value=05 spellBeginStr="ggcsfwy">公共场所服务员</OPTION>
					<OPTION id=zy0000_6 value=06 spellBeginStr="syfw">商业服务</OPTION>
					<OPTION id=zy0000_7 value=07 spellBeginStr="ywry">医务人员</OPTION>
					<OPTION id=zy0000_8 value=08 spellBeginStr="gr">工人</OPTION>
					<OPTION id=zy0000_9 value=09 spellBeginStr="mg">民工</OPTION>
					<OPTION id=zy0000_10 value=10 spellBeginStr="mm">牧民</OPTION>
					<OPTION id=zy0000_11 value=11 spellBeginStr="ycm">渔(船)民</OPTION>
					<OPTION id=zy0000_12 value=12 spellBeginStr="hyjctjsy">海员及长途驾驶员</OPTION>
					<OPTION id=zy0000_13 value=13 spellBeginStr="gbzy">干部职员</OPTION>
					<OPTION id=zy0000_14 value=14 spellBeginStr="ltry">离退人员</OPTION>
					<OPTION id=zy0000_15 value=15 spellBeginStr="jwjdy">家务及待业</OPTION>
					<OPTION id=zy0000_16 value=98 spellBeginStr="qt">其他</OPTION>
					<OPTION id=zy0000_17 value=99 spellBeginStr="bx">不详</OPTION>
				</SELECT>
			</td>
		</tr>
		<tr>
			<td>户口状态</td>
			<td>
				<input type="hidden" name="hkzt00" /> 
				<input type="checkbox" pofield="hkzt00" name="hkzt00" value="0" readonly="readonly"/>本省 
				<input type="checkbox" pofield="hkzt00" name="hkzt00" value="1" readonly="readonly"/>本市 
				<input type="checkbox" pofield="hkzt00" name="hkzt00" value="2" readonly="readonly"/>本县 
				<input type="checkbox" pofield="hkzt00" name="hkzt00" value="3" readonly="readonly"/>省外
			</td>
			<td>户籍类型</td>
			<td>
				<input type="hidden" name="hjlx00" />
				<input type="checkbox" pofield="hjlx00" name="hjlx00" value="1" readonly="readonly"/>城镇
				<input type="checkbox" pofield="hjlx00" name="hjlx00" value="2" readonly="readonly"/>农村
			</td>
			<td>是否农业户</td>
			<td>
				<input type="hidden" name="sfnyh0" />
				<input type="checkbox" pofield="sfnyh0" name="hjlx00" value="0" readonly="readonly"/>否 
				<input type="checkbox" pofield="sfnyh0" name="hjlx00" value="1" readonly="readonly"/>是
			</td>
		</tr>
		<tr>
			<td>户口地址</td>
			<td colspan="5">
				<input name="hkdshe" pofield="hkdshe" id="hkdshe" class="layui-input" style="width: 80px; display: inline" />省 
				<input name="hkdshi" pofield="hkdshi" id="hkdshi" class="layui-input" style="width: 80px; display: inline" />市 
				<input name="hkdxia" pofield="hkdxia" id="hkdxia" class="layui-input" style="width: 80px; display: inline" />区/县 
				<input name="hkdzhe" pofield="hkdzhe" id="hkdzhe" class="layui-input" style="width: 80px; display: inline" />乡/镇 
				<input name="hkdcun" pofield="hkdcun" id="hkdcun" class="layui-input" style="width: 80px; display: inline" />村/居
			</td>
		</tr>
		<tr>
			<td>常住地址</td>
			<td colspan="5">
				<input name="xzdshe" id="xzdshe" class="layui-input" style="width: 80px; display: inline" />省
				<input name="xzdshi" id="xzdshi" class="layui-input" style="width: 80px; display: inline" />市 
				<input name="xzdxia" id="xzdxia" class="layui-input" style="width: 80px; display: inline" />区/县 
				<input name="xzdzhe" id="xzdzhe" class="layui-input" style="width: 80px; display: inline" />乡/镇 
				<input name="xzdcun" id="xzdcun" class="layui-input" style="width: 80px; display: inline" />村/居
			</td>
		</tr>
		<tr>
			<td>爱人姓名</td>
			<td><input type="text" id="zfxm00" pofield="zfxm00" name="zfxm00" class="layui-input" readonly="readonly" /></td>
			<td>身份证号</td>
			<td><input type="text" id="zfsfzh" pofield="zfsfzh" name="zfsfzh" class="layui-input" readonly="readonly" /></td>
			<td>爱人出生日期</td>
			<td>
				<input type="text" id="fqcsrq" pofield="fqcsrq" name="fqcsrq" class="layui-input" style="width: 70px; display: inline" readonly="readonly" />年龄 
				<input type="text" id="patientname" pofield="patientname" name="patientname" class="layui-input" style="width: 70px; display: inline" />岁
			</td>
		</tr>
		<tr>
			<td>国籍</td>
			<td>
				<SELECT id="fqgj00" pofield="fqgj00" name="fqgj00" class="layui-input" disabled="disabled">
					<OPTION value="" spellBeginStr="qxz">请选择...</OPTION>
					<OPTION id=mqgj00_1 value=10 spellBeginStr="wkl">乌克兰</OPTION>
					<OPTION id=mqgj00_2 value=102 spellBeginStr="ydnxy">印度尼西亚</OPTION>
					<OPTION id=mqgj00_3 value=104 spellBeginStr="md">缅甸</OPTION>
					<OPTION id=mqgj00_4 value=112 spellBeginStr="bels">白俄罗斯</OPTION>
					<OPTION id=mqgj00_5 value=116 spellBeginStr="jpz">柬埔寨</OPTION>
					<OPTION id=mqgj00_6 value=12 spellBeginStr="aejly">阿尔及利亚</OPTION>
					<OPTION id=mqgj00_7 value=124 spellBeginStr="jnd">加拿大</OPTION>
					<OPTION id=mqgj00_8 value=144 spellBeginStr="sllk">斯里兰卡</OPTION>
					<OPTION id=mqgj00_9 value=152 spellBeginStr="zl">智利</OPTION>
					<OPTION id=mqgj00_10 value=156 spellBeginStr="zg">中国</OPTION>
					<OPTION id=mqgj00_11 value=158 spellBeginStr="zgtw">中国(台湾)</OPTION>
					<OPTION id=mqgj00_12 value=16 spellBeginStr="mssmy">美属萨摩亚</OPTION>
					<OPTION id=mqgj00_13 value=166 spellBeginStr="kksjlqd">科科斯(基林)群岛</OPTION>
					<OPTION id=mqgj00_14 value=170 spellBeginStr="glby">哥伦比亚</OPTION>
					<OPTION id=mqgj00_15 value=178 spellBeginStr="ggb">刚果（布）</OPTION>
					<OPTION id=mqgj00_16 value=208 spellBeginStr="dm">丹麦</OPTION>
					<OPTION id=mqgj00_17 value=214 spellBeginStr="dmnj">多米尼加</OPTION>
					<OPTION id=mqgj00_18 value=218 spellBeginStr="egde">厄瓜多尔</OPTION>
					<OPTION id=mqgj00_19 value=250 spellBeginStr="fg">法国</OPTION>
					<OPTION id=mqgj00_20 value=275 spellBeginStr="blst">巴勒斯坦</OPTION>
					<OPTION id=mqgj00_21 value=276 spellBeginStr="dg">德国</OPTION>
					<OPTION id=mqgj00_22 value=288 spellBeginStr="jn">加纳</OPTION>
					<OPTION id=mqgj00_23 value=32 spellBeginStr="agt">阿根廷</OPTION>
					<OPTION id=mqgj00_24 value=344 spellBeginStr="zgxg">中国(香港)</OPTION>
					<OPTION id=mqgj00_25 value=348 spellBeginStr="xyl">匈牙利</OPTION>
					<OPTION id=mqgj00_26 value=356 spellBeginStr="yd">印度</OPTION>
					<OPTION id=mqgj00_27 value=36 spellBeginStr="adly">澳大利亚</OPTION>
					<OPTION id=mqgj00_28 value=372 spellBeginStr="ael">爱尔兰</OPTION>
					<OPTION id=mqgj00_29 value=380 spellBeginStr="ydl">意大利</OPTION>
					<OPTION id=mqgj00_30 value=388 spellBeginStr="ymj">牙买加</OPTION>
					<OPTION id=mqgj00_31 value=392 spellBeginStr="rb">日本</OPTION>
					<OPTION id=mqgj00_32 value=400 spellBeginStr="yd">约旦</OPTION>
					<OPTION id=mqgj00_33 value=410 spellBeginStr="hg">韩国</OPTION>
					<OPTION id=mqgj00_34 value=418 spellBeginStr="lw">老挝</OPTION>
					<OPTION id=mqgj00_35 value=446 spellBeginStr="zgam">中国(澳门)</OPTION>
					<OPTION id=mqgj00_36 value=450 spellBeginStr="mdjsj">马达加斯加</OPTION>
					<OPTION id=mqgj00_37 value=458 spellBeginStr="mlxy">马来西亚</OPTION>
					<OPTION id=mqgj00_38 value=484 spellBeginStr="mxg">墨西哥</OPTION>
					<OPTION id=mqgj00_39 value=496 spellBeginStr="mg">蒙古</OPTION>
					<OPTION id=mqgj00_40 value=498 spellBeginStr="medw">摩尔多瓦</OPTION>
					<OPTION id=mqgj00_41 value=50 spellBeginStr="mjlg">孟加拉国</OPTION>
					<OPTION id=mqgj00_42 value=528 spellBeginStr="hl">荷兰</OPTION>
					<OPTION id=mqgj00_43 value=548 spellBeginStr="wnat">瓦努阿图</OPTION>
					<OPTION id=mqgj00_44 value=554 spellBeginStr="xxl">新西兰</OPTION>
					<OPTION id=mqgj00_45 value=56 spellBeginStr="bls">比利时</OPTION>
					<OPTION id=mqgj00_46 value=578 spellBeginStr="nw">挪威</OPTION>
					<OPTION id=mqgj00_47 value=584 spellBeginStr="mseqd">马绍尔群岛</OPTION>
					<OPTION id=mqgj00_48 value=586 spellBeginStr="bjst">巴基斯坦</OPTION>
					<OPTION id=mqgj00_49 value=598 spellBeginStr="bbyxjny">巴布亚新几内亚</OPTION>
					<OPTION id=mqgj00_50 value=604 spellBeginStr="ml">秘鲁</OPTION>
					<OPTION id=mqgj00_51 value=608 spellBeginStr="flb">菲律宾</OPTION>
					<OPTION id=mqgj00_52 value=620 spellBeginStr="pty">葡萄牙</OPTION>
					<OPTION id=mqgj00_53 value=643 spellBeginStr="elslb">俄罗斯联邦</OPTION>
					<OPTION id=mqgj00_54 value=702 spellBeginStr="xjp">新加坡</OPTION>
					<OPTION id=mqgj00_55 value=704 spellBeginStr="yn">越南</OPTION>
					<OPTION id=mqgj00_56 value=710 spellBeginStr="nf">南非</OPTION>
					<OPTION id=mqgj00_57 value=724 spellBeginStr="xby">西班牙</OPTION>
					<OPTION id=mqgj00_58 value=740 spellBeginStr="sln">苏里南</OPTION>
					<OPTION id=mqgj00_59 value=752 spellBeginStr="rd">瑞典</OPTION>
					<OPTION id=mqgj00_60 value=756 spellBeginStr="rs">瑞士</OPTION>
					<OPTION id=mqgj00_61 value=76 spellBeginStr="bx">巴西</OPTION>
					<OPTION id=mqgj00_62 value=764 spellBeginStr="tg">泰国</OPTION>
					<OPTION id=mqgj00_63 value=826 spellBeginStr="yg">英国</OPTION>
					<OPTION id=mqgj00_64 value=840 spellBeginStr="mg">美国</OPTION>
					<OPTION id=mqgj00_65 value=887 spellBeginStr="ym">也门</OPTION>
				</SELECT>
			</td>
			<td>身份证类别</td>
			<td>
				<SELECT class="layui-input" id="fqsfzl" pofield="fqsfzl" name="fqsfzl" disabled="disabled">
					<OPTION value="" spellBeginStr="qxz">请选择...</OPTION>
					<OPTION id=mqsfzl_1  value=1 spellBeginStr="sfz">身份证</OPTION>
					<OPTION id=mqsfzl_2 value=2 spellBeginStr="hz">护照</OPTION>
					<OPTION id=mqsfzl_3 value=4 spellBeginStr="lk">绿卡</OPTION>
					<OPTION id=mqsfzl_4 value=fy01 spellBeginStr="jmsfz">居民身份证</OPTION>
					<OPTION id=mqsfzl_5 value=fy02 spellBeginStr="jmhkb">居民户口簿</OPTION>
					<OPTION id=mqsfzl_6 value=fy04 spellBeginStr="jgz">军官证</OPTION>
					<OPTION id=mqsfzl_7 value=fy05 spellBeginStr="jsz">驾驶证</OPTION>
					<OPTION id=mqsfzl_8 value=fy06 spellBeginStr="gajmlwndtxz">港澳居民来往内地通行证</OPTION>
					<OPTION id=mqsfzl_9 value=fy07 spellBeginStr="twjmlwndtxz">台湾居民来往内地通行证</OPTION>
					<OPTION id=mqsfzl_10 value=fy99 spellBeginStr="qtfdyxzj">其他法定有效证件</OPTION>
				</SELECT>
			</td>
			<td>民族</td>
			<td>
				<SELECT id="fqmz00" pofield="fqmz00" name="fqmz00" class="layui-input" disabled="disabled">
					<OPTION value="" spellBeginStr="qxz">请选择...</OPTION>
					<OPTION id=mz0000_1 value=1 spellBeginStr="hz">汉族</OPTION>
					<OPTION id=mz0000_2 value=10 spellBeginStr="lz">拉祜族</OPTION>
					<OPTION id=mz0000_3 value=11 spellBeginStr="nxz">纳西族</OPTION>
					<OPTION id=mz0000_4 value=12 spellBeginStr="jpz">景颇族</OPTION>
					<OPTION id=mz0000_5 value=13 spellBeginStr="sz">水 族</OPTION>
					<OPTION id=mz0000_6 value=14 spellBeginStr="nz">怒 族</OPTION>
					<OPTION id=mz0000_7 value=15 spellBeginStr="lsz">傈僳族</OPTION>
					<OPTION id=mz0000_8 value=16 spellBeginStr="dlz">独龙族</OPTION>
					<OPTION id=mz0000_9 value=17 spellBeginStr="blz">布朗族</OPTION>
					<OPTION id=mz0000_10 value=18 spellBeginStr="jnz">基诺族</OPTION>
					<OPTION id=mz0000_11 value=19 spellBeginStr="qz">羌 族</OPTION>
					<OPTION id=mz0000_12 value=2 spellBeginStr="yz">彝 族</OPTION>
					<OPTION id=mz0000_13 value=20 spellBeginStr="mbz">门巴族</OPTION>
					<OPTION id=mz0000_14 value=21 spellBeginStr="daz">德昂族</OPTION>
					<OPTION id=mz0000_15 value=22 spellBeginStr="acz">阿昌族</OPTION>
					<OPTION id=mz0000_16 value=23 spellBeginStr="pmz">普米族</OPTION>
					<OPTION id=mz0000_17 value=24 spellBeginStr="byz">布依族</OPTION>
					<OPTION id=mz0000_18 value=25 spellBeginStr="bz">珞巴族</OPTION>
					<OPTION id=mz0000_19 value=26 spellBeginStr="lz">仡佬族</OPTION>
					<OPTION id=mz0000_20 value=27 spellBeginStr="hz">回 族</OPTION>
					<OPTION id=mz0000_21 value=28 spellBeginStr="dxz">东乡族</OPTION>
					<OPTION id=mz0000_22 value=29 spellBeginStr="slz">撒拉族</OPTION>
					<OPTION id=mz0000_23 value=3 spellBeginStr="bz">白 族</OPTION>
					<OPTION id=mz0000_24 value=30 spellBeginStr="baz">保安族</OPTION>
					<OPTION id=mz0000_25 value=31 spellBeginStr="wwez">维吾尔族</OPTION>
					<OPTION id=mz0000_26 value=32 spellBeginStr="tz">土 族</OPTION>
					<OPTION id=mz0000_27 value=33 spellBeginStr="ygz">裕固族</OPTION>
					<OPTION id=mz0000_28 value=34 spellBeginStr="xbz">锡伯族</OPTION>
					<OPTION id=mz0000_29 value=35 spellBeginStr="elsz">俄罗斯族</OPTION>
					<OPTION id=mz0000_30 value=36 spellBeginStr="ttez">塔塔尔族</OPTION>
					<OPTION id=mz0000_31 value=37 spellBeginStr="hskz">哈萨克族</OPTION>
					<OPTION id=mz0000_32 value=38 spellBeginStr="kekzz">柯尔克孜族</OPTION>
					<OPTION id=mz0000_33 value=39 spellBeginStr="tjkz">塔吉克族</OPTION>
					<OPTION id=mz0000_34 value=4 spellBeginStr="cz">藏 族</OPTION>
					<OPTION id=mz0000_35 value=40 spellBeginStr="wzbkz">乌孜别克族</OPTION>
					<OPTION id=mz0000_36 value=41 spellBeginStr="gsz">高山族</OPTION>
					<OPTION id=mz0000_37 value=42 spellBeginStr="z">畲 族</OPTION>
					<OPTION id=mz0000_38 value=43 spellBeginStr="lz">黎 族</OPTION>
					<OPTION id=mz0000_39 value=44 spellBeginStr="zz">壮 族</OPTION>
					<OPTION id=mz0000_40 value=45 spellBeginStr="yz">瑶 族</OPTION>
					<OPTION id=mz0000_41 value=46 spellBeginStr="jz">京 族</OPTION>
					<OPTION id=mz0000_42 value=47 spellBeginStr="lz">仫佬族</OPTION>
					<OPTION id=mz0000_43 value=48 spellBeginStr="mnz">毛南族</OPTION>
					<OPTION id=mz0000_44 value=49 spellBeginStr="tjz">土家族</OPTION>
					<OPTION id=mz0000_45 value=5 spellBeginStr="dz">傣 族</OPTION>
					<OPTION id=mz0000_46 value=50 spellBeginStr="mz">满 族</OPTION>
					<OPTION id=mz0000_47 value=51 spellBeginStr="cxz">朝鲜族</OPTION>
					<OPTION id=mz0000_48 value=52 spellBeginStr="hzz">赫哲族</OPTION>
					<OPTION id=mz0000_49 value=53 spellBeginStr="mgz">蒙古族</OPTION>
					<OPTION id=mz0000_50 value=54 spellBeginStr="dwez">达斡尔族</OPTION>
					<OPTION id=mz0000_51 value=55 spellBeginStr="ewkz">鄂温克族</OPTION>
					<OPTION id=mz0000_52 value=56 spellBeginStr="elcz">鄂伦春族</OPTION>
					<OPTION id=mz0000_53 value=57 spellBeginStr="qt">其他</OPTION>
					<OPTION id=mz0000_54 value=59 spellBeginStr="gjr">亻革家人</OPTION>
					<OPTION id=mz0000_55 value=6 spellBeginStr="z">佤 族</OPTION>
					<OPTION id=mz0000_56 value=60 spellBeginStr="cqr">穿青人</OPTION>
					<OPTION id=mz0000_57 value=7 spellBeginStr="dz">侗 族</OPTION>
					<OPTION id=mz0000_58 value=8 spellBeginStr="hnz">哈尼族</OPTION>
					<OPTION id=mz0000_59 value=9 spellBeginStr="mz">苗 族</OPTION>
				</SELECT>
			</td>
		</tr>
		<tr>
			<td>工作单位</td>
			<td><input type="text" id="zfgzdw" pofield="zfgzdw" name="zfgzdw" class="layui-input" readonly="readonly" /></td>
			<td>电话号码</td>
			<td><input type="text" id="dhhm00" pofield="dhhm00" name="dhhm00" class="layui-input" readonly="readonly" /></td>
			<td>健康情况</td>
			<td>
				<select id="zfjkqk" name="zfjkqk" class="layui-input" style="width: 160px;" disabled="disabled">
					<option value="-1">请选择</option>
					<option value="1">好</option>
					<option value="2">良好</option>
					<option value="3">差</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>父亲户口地</td>
			<td><input type="text" id="fqhkd0" pofield="fqhkd0" name="fqhkd0" class="layui-input" readonly="readonly" /></td>
		</tr>
	</table>
	<table class="layui-table" id="form_yfls">
		<tr>
			<td>婚 姻 史</td>
			<td>女方结婚年龄</td>
			<td>
				<input type="text" id="jhnl00" pofield="jhnl00" name="jhnl00" class="layui-input" style="width: 70px; display: inline" />岁 
				<input type="hidden" name="sfjh00" /> 
				<input type="checkbox" id="wh0000" pofield="wh0000" name="wh0000" class="layui-input" style="display: inline" />未婚
			</td>
			<td>亲缘结婚</td>
			<td>
				<input type="checkbox" id="qyjhs0" pofield="qyjhs0" name="qyjhs0" class="layui-input" value="1" style="display: inline" />是
				<input type="checkbox" id="qyjhf0" pofield="qyjhf0" name="qyjhf0" class="layui-input" value="0" style="display: inline" />否
			</td>
			<td>胎次</td>
			<td>
				<input type="text" id="yunci0" pofield="yunci0" name="yunci0" class="layui-input" style="width: 70px; display: inline" />产次 
				<input type="text" id="ccpgc0" pofield="ccpgc0" name="ccpgc0" class="layui-input" style="width: 70px; display: inline" />
			</td>
		</tr>
		<tr>
			<td>月 经 史</td>
			<td>月经史</td>
			<td><input type="text" id="yjs000" pofield="yjs000" name="yjs000" class="layui-input" readonly="readonly" /></td>
			<td>末次月经</td>
			<td><input type="text" id="mcyj00" pofield="mcyj00" name="mcyj00" class="layui-input" readonly="readonly" /></td>
			<td>预产期</td>
			<td><input type="text" id="ycq000" pofield="ycq000" name="ycq000" class="layui-input" readonly="readonly" /></td>
			<td><input type="checkbox" id="bcts00" pofield="bcts00" name="bcts00" style="width: 30px;" readonly="readonly"/>B超推算</td>
			<td>孕周 
				<input type="text" id="tbyz00" pofield="tbyz00" name="tbyz00" class="layui-input" style="width: 70px; display: inline" readonly="readonly" />周 
				<input type="text" id="tbyzts" pofield="tbyzts" name="tbyzts" class="layui-input" style="width: 70px; display: inline" readonly="readonly" />天
			</td>
		</tr>
		<tr></tr>
		<tr>
			<td rowspan="4">现 孕 史</td>
			<td>妊娠反应</td>
			<td>
				<input type="checkbox" pofield="rsfy00" name="rsfy00" value="0" style="width: 30px;" />无
				<input type="checkbox" type="checkbox" pofield="rsfy00" name="rsfy00" value="1" style="width: 30px;" />有 
				<input type="text" pofield="rsfyqt" id="rsfyqt" name="rsfyqt" style="width: 30px;" /> (孕
				<select id="rsfyy0" pofield="rsfyy0" name="rsfyy0" style="width: 65px;" data-options="">
					<option value="">--</option>
					<option value="1">1</option>
					<option value="1+">1+</option>
					<option value="2">2</option>
					<option value="2+">2+</option>
					<option value="3">3</option>
					<option value="3+">3+</option>
					<option value="4">4</option>
					<option value="4+">4+</option>
					<option value="5">5</option>
					<option value="5+">5+</option>
					<option value="6">6</option>
					<option value="6+">6+</option>
					<option value="7">7</option>
					<option value="7+">7+</option>
					<option value="8">8</option>
					<option value="8+">8+</option>
					<option value="9">9</option>
					<option value="9+">9+</option>
					<option value="10">10</option>
					<option value="10+">10+</option>
					<option value="11">11</option>
					<option value="11+">11+</option>
					<option value="12">12</option>
					<option value="12+">12+</option>
				</select>月)
			</td>
			<td>初感胎动</td>
			<td>
				<input type="checkbox" pofield="cgtd00" name="cgtd00" value="0" style="width: 30px;" />未感 
				<input type="checkbox" pofield="cgtd00" name="cgtd00" value="1" style="width: 30px;" />感 
				<input type="text" id="cgtdqt" name="cgtdqt" pofield="cgtdqt" style="width: 30px;" /> (孕
				<select id="cgtdy0" pofield="cgtdy0" name="cgtdy0" style="width: 65px;" data-options="">
					<option value="">--</option>
					<option value="1">1</option>
					<option value="1+">1+</option>
					<option value="2">2</option>
					<option value="2+">2+</option>
					<option value="3">3</option>
					<option value="3+">3+</option>
					<option value="4">4</option>
					<option value="4+">4+</option>
					<option value="5">5</option>
					<option value="5+">5+</option>
					<option value="6">6</option>
					<option value="6+">6+</option>
					<option value="7">7</option>
					<option value="7+">7+</option>
					<option value="8">8</option>
					<option value="8+">8+</option>
					<option value="9">9</option>
					<option value="9+">9+</option>
					<option value="10">10</option>
					<option value="10+">10+</option>
					<option value="11">11</option>
					<option value="11+">11+</option>
					<option value="12">12</option>
					<option value="12+">12+</option>
				</select>月)
			</td>
			<td>过敏</td>
			<td colspan="3">
				<input type="checkbox" pofield="gm0000" name="gm0000" value="0" />未发现 
				<input type="checkbox" pofield="gm0000" name="gm0000" value="1" />感 
				<input id="text3" name="text3" style="width: 50px;" />
			</td>
		</tr>
		<tr>
			<td>服药</td>
			<td>
				<input type="checkbox" pofield="fy0000" name="fy0000" value="0" />无 
				<input type="checkbox" pofield="fy0000" name="fy0000" value="1" />感 
				<input id="text4" name="text4" style="width: 50px;" />
			</td>
			<td>病毒感染</td>
			<td>
				<input type="checkbox" pofield="bfgr00" name="bfgr00" value="0" />无 
				<input type="checkbox" pofield="bfgr00" name="bfgr00" value="1" />感 
				<input id="text5" name="text5" style="width: 50px;" />
			</td>
			<td>接触有害物质</td>
			<td colspan="3">
				<input type="checkbox" pofield="jcyhwz" name="jcyhwz" value="0" />无 
				<input type="checkbox" pofield="jcyhwz" name="jcyhwz" value="1" />感 
				<input id="text6" name="text6" style="width: 50px;" />
			</td>
		</tr>
		<tr>
			<td>服避孕药</td>
			<td>
				<input type="checkbox" pofield="fbyy0" name="fbyy0" value="0" />无 
				<input type="checkbox" pofield="fbyy0" name="fbyy0" value="1" />感 
				<input id="text7" pofield="text7" name="text7" style="width: 50px;" />
			</td>
			<td colspan="6">
				剧吐 
				<input type="checkbox" pofield="jt0000" name="jt0000" value="0" />无 
				<input type="checkbox" pofield="jt0000" name="jt0000" value="1" />有
				 阴道出血 
				<input type="checkbox" pofield="ydcx00" name="ydcx00" value="0" />无 
				<input type="checkbox" pofield="ydcx00" name="ydcx00" value="1" />有 
				发热 
				<input type="checkbox" pofield="fr0000" name="fr0000" value="0" />无 
				<input type="checkbox" pofield="fr0000" name="fr0000" value="1" />有
			</td>
		</tr>
		<tr>
			<td>主诉</td>
			<td>停经<input type="text" id="zstj00" pofield="zstj00" name="zstj00" style="width: 30px;" />周</td>
			<td>症状</td>
			<td colspan="3"><textarea type="text" id="zszz00" pofield="zszz00" name="zszz00"></textarea></td>
			<td>其他</td>
			<td><textarea type="text" id="zszzqt" pofield="zszzqt" name="zszzqt"></textarea></td>
		</tr>
	</table>
	<table class="layui-table" id="form_grls">
		<tr></tr>
		<tr>
			<td rowspan="6" style="width: 20px;">妊 娠 史</td>
			<td style="width: 20px;">胎次</td>
			<td style="width: 50px;">日期</td>
			<td style="width: 20px;">足月</td>
			<td style="width: 20px;">早产</td>
			<td style="width: 20px;">引产</td>
			<td style="width: 20px;">人流</td>
			<td style="width: 20px;">药流</td>
			<td style="width: 20px;">自然流产</td>
			<td style="width: 20px;">异位妊娠</td>
			<td style="width: 20px;">葡萄胎</td>
			<td style="width: 20px;">死胎</td>
			<td style="width: 20px;">死产</td>
			<td style="width: 20px;">新生儿死亡</td>
			<td style="width: 20px;">男</td>
			<td style="width: 20px;">女</td>
			<td style="width: 20px;">存</td>
			<td style="width: 20px;">亡</td>
			<td style="width: 20px;">畸形</td>
			<td style="width: 20px;">顺产</td>
			<td style="width: 20px;">胎吸</td>
			<td style="width: 20px;">产钳</td>
			<td style="width: 20px;">臂助</td>
			<td style="width: 20px;">刨宫产</td>
			<td style="width: 20px;">产后出血</td>
			<td style="width: 20px;">其他</td>
		</tr>
		<tr>
			<input type="hidden" pofield="rsdyqk" name="rsdyqk" />
			<td><input type="text" id="rsdytc" pofield="rsdytc" name="rsdytc" style="width: 20px;" readonly="readonly" /></td>
			<td><input type="text" id="rsdyrq" pofield="rsdyrq" name="rsdyrq" style="width: 50px;" readonly="readonly" /></td>
			<td><input type="checkbox" value="n" pofield="rsdyqk" name="rsdyqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="1" pofield="rsdyqk" name="rsdyqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="2" pofield="rsdyqk" name="rsdyqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="3" pofield="rsdyqk" name="rsdyqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="4" pofield="rsdyqk"
				name="rsdyqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="5" pofield="rsdyqk"
				name="rsdyqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="m" pofield="rsdyqk"
				name="rsdyqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="7" pofield="rsdyqk"
				name="rsdyqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="8" pofield="rsdyqk"
				name="rsdyqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="9" pofield="rsdyqk"
				name="rsdyqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="a" pofield="rsdyqk"
				name="rsdyqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="b" pofield="rsdyqk"
				name="rsdyqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="c" pofield="rsdyqk"
				name="rsdyqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="d" pofield="rsdyqk"
				name="rsdyqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="e" pofield="rsdyqk"
				name="rsdyqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="f" pofield="rsdyqk"
				name="rsdyqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="g" pofield="rsdyqk"
				name="rsdyqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="h" pofield="rsdyqk"
				name="rsdyqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="i" pofield="rsdyqk"
				name="rsdyqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="j" pofield="rsdyqk"
				name="rsdyqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="k" pofield="rsdyqk"
				name="rsdyqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="l" pofield="rsdyqk"
				name="rsdyqk" style="width: 20px;" /></td>
			<td><input type="checkbox" id="" name="" style="width: 20px;" /></td>

		</tr>
		<tr>
			<input type="hidden" pofield="rsdeqk" name="rsdeqk" />
			<td><input type="text" id="rsdetc" pofield="rsdetc"
				name="rsdetc" style="width: 20px;" /></td>
			<td><input type="text" id="rsderq" pofield="rsderq"
				name="rsderq" style="width: 50px;" /></td>
			<td><input type="checkbox" value="n" pofield="rsdeqk"
				name="rsdeqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="1" pofield="rsdeqk"
				name="rsdeqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="2" pofield="rsdeqk"
				name="rsdeqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="3" pofield="rsdeqk"
				name="rsdeqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="4" pofield="rsdeqk"
				name="rsdeqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="5" pofield="rsdeqk"
				name="rsdeqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="m" pofield="rsdeqk"
				name="rsdeqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="7" pofield="rsdeqk"
				name="rsdeqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="8" pofield="rsdeqk"
				name="rsdeqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="9" pofield="rsdeqk"
				name="rsdeqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="a" pofield="rsdeqk"
				name="rsdeqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="b" pofield="rsdeqk"
				name="rsdeqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="c" pofield="rsdeqk"
				name="rsdeqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="d" pofield="rsdeqk"
				name="rsdeqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="e" pofield="rsdeqk"
				name="rsdeqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="f" pofield="rsdeqk"
				name="rsdeqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="g" pofield="rsdeqk"
				name="rsdeqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="h" pofield="rsdeqk"
				name="rsdeqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="i" pofield="rsdeqk"
				name="rsdeqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="j" pofield="rsdeqk"
				name="rsdeqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="k" pofield="rsdeqk"
				name="rsdeqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="l" pofield="rsdeqk"
				name="rsdeqk" style="width: 20px;" /></td>
			<td><input type="checkbox" id="" name="" style="width: 20px;" /></td>

		</tr>
		<tr>
			<input type="hidden" pofield="rsdsqk" name="rsdsqk" />
			<td><input type="text" id="rsdstc" pofield="rsdstc"
				name="rsdstc" style="width: 20px;" /></td>
			<td><input type="text" id="rsdsrq" pofield="rsdsrq"
				name="rsdsrq" style="width: 50px;" /></td>
			<td><input type="checkbox" value="n" pofield="rsdsqk"
				name="rsdsqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="1" pofield="rsdsqk"
				name="rsdsqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="2" pofield="rsdsqk"
				name="rsdsqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="3" pofield="rsdsqk"
				name="rsdsqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="4" pofield="rsdsqk"
				name="rsdsqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="5" pofield="rsdsqk"
				name="rsdsqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="m" pofield="rsdsqk"
				name="rsdsqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="7" pofield="rsdsqk"
				name="rsdsqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="8" pofield="rsdsqk"
				name="rsdsqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="9" pofield="rsdsqk"
				name="rsdsqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="a" pofield="rsdsqk"
				name="rsdsqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="b" pofield="rsdsqk"
				name="rsdsqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="c" pofield="rsdsqk"
				name="rsdsqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="d" pofield="rsdsqk"
				name="rsdsqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="e" pofield="rsdsqk"
				name="rsdsqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="f" pofield="rsdsqk"
				name="rsdsqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="g" pofield="rsdsqk"
				name="rsdsqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="h" pofield="rsdsqk"
				name="rsdsqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="i" pofield="rsdsqk"
				name="rsdsqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="j" pofield="rsdsqk"
				name="rsdsqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="k" pofield="rsdsqk"
				name="rsdsqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="l" pofield="rsdsqk"
				name="rsdsqk" style="width: 20px;" /></td>
			<td><input type="checkbox" id="" name="" style="width: 20px;" /></td>

		</tr>
		<tr>
			<input type="hidden" pofield="rssttc" name="rssttc" />
			<td><input type="text" id="rsstqk" pofield="rsstqk"
				name="rsstqk" style="width: 20px;" /></td>
			<td><input type="text" id="rsstrq" pofield="rsstrq"
				name="rsstrq" style="width: 50px;" /></td>
			<td><input type="checkbox" value="n" pofield="rssttc"
				name="rssttc" style="width: 20px;" /></td>
			<td><input type="checkbox" value="1" pofield="rssttc"
				name="rssttc" style="width: 20px;" /></td>
			<td><input type="checkbox" value="2" pofield="rssttc"
				name="rssttc" style="width: 20px;" /></td>
			<td><input type="checkbox" value="3" pofield="rssttc"
				name="rssttc" style="width: 20px;" /></td>
			<td><input type="checkbox" value="4" pofield="rssttc"
				name="rssttc" style="width: 20px;" /></td>
			<td><input type="checkbox" value="5" pofield="rssttc"
				name="rssttc" style="width: 20px;" /></td>
			<td><input type="checkbox" value="m" pofield="rssttc"
				name="rssttc" style="width: 20px;" /></td>
			<td><input type="checkbox" value="7" pofield="rssttc"
				name="rssttc" style="width: 20px;" /></td>
			<td><input type="checkbox" value="8" pofield="rssttc"
				name="rssttc" style="width: 20px;" /></td>
			<td><input type="checkbox" value="9" pofield="rssttc"
				name="rssttc" style="width: 20px;" /></td>
			<td><input type="checkbox" value="a" pofield="rssttc"
				name="rssttc" style="width: 20px;" /></td>
			<td><input type="checkbox" value="b" pofield="rssttc"
				name="rssttc" style="width: 20px;" /></td>
			<td><input type="checkbox" value="c" pofield="rssttc"
				name="rssttc" style="width: 20px;" /></td>
			<td><input type="checkbox" value="d" pofield="rssttc"
				name="rssttc" style="width: 20px;" /></td>
			<td><input type="checkbox" value="e" pofield="rssttc"
				name="rssttc" style="width: 20px;" /></td>
			<td><input type="checkbox" value="f" pofield="rssttc"
				name="rssttc" style="width: 20px;" /></td>
			<td><input type="checkbox" value="g" pofield="rssttc"
				name="rssttc" style="width: 20px;" /></td>
			<td><input type="checkbox" value="h" pofield="rssttc"
				name="rssttc" style="width: 20px;" /></td>
			<td><input type="checkbox" value="i" pofield="rssttc"
				name="rssttc" style="width: 20px;" /></td>
			<td><input type="checkbox" value="j" pofield="rssttc"
				name="rssttc" style="width: 20px;" /></td>
			<td><input type="checkbox" value="k" pofield="rssttc"
				name="rssttc" style="width: 20px;" /></td>
			<td><input type="checkbox" value="l" pofield="rssttc"
				name="rssttc" style="width: 20px;" /></td>
			<td><input type="checkbox" id="" name="" style="width: 20px;" /></td>

		</tr>
		<tr>
			<input type="hidden" pofield="rsdyqk" name="rsdyqk" />
			<td><input type="text" pofield="rsdytc" name="rsdytc"
				style="width: 20px;" /></td>
			<td><input type="text" pofield="rsdyrq" name="rsdyrq"
				style="width: 50px;" /></td>
			<td><input type="checkbox" value="n" pofield="rsdyqk"
				name="rsdyqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="1" pofield="rsdyqk"
				name="rsdyqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="2" pofield="rsdyqk"
				name="rsdyqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="3" pofield="rsdyqk"
				name="rsdyqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="4" pofield="rsdyqk"
				name="rsdyqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="5" pofield="rsdyqk"
				name="rsdyqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="m" pofield="rsdyqk"
				name="rsdyqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="7" pofield="rsdyqk"
				name="rsdyqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="8" pofield="rsdyqk"
				name="rsdyqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="9" pofield="rsdyqk"
				name="rsdyqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="a" pofield="rsdyqk"
				name="rsdyqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="b" pofield="rsdyqk"
				name="rsdyqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="c" pofield="rsdyqk"
				name="rsdyqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="d" pofield="rsdyqk"
				name="rsdyqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="e" pofield="rsdyqk"
				name="rsdyqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="f" pofield="rsdyqk"
				name="rsdyqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="g" pofield="rsdyqk"
				name="rsdyqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="h" pofield="rsdyqk"
				name="rsdyqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="i" pofield="rsdyqk"
				name="rsdyqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="j" pofield="rsdyqk"
				name="rsdyqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="k" pofield="rsdyqk"
				name="rsdyqk" style="width: 20px;" /></td>
			<td><input type="checkbox" value="l" pofield="rsdyqk"
				name="rsdyqk" style="width: 20px;" /></td>
			<td><input type="checkbox" id="" name="" style="width: 20px;" /></td>

		</tr>
		<tr>
			<td style="width: 20px;">妊娠合并症史</td>
			<td colspan="25"><input type="hidden" pofield="rshbzs"
				name="rshbzs" /> <input type="checkbox" pofield="rshbzs"
				name="rshbzs" value="0" style="width: 30px;" />无 <input
				type="checkbox" pofield="rshbzs" name="rshbzs" value="1"
				style="width: 30px;" />有 <input id="" name="" style="width: 70px;" />
				&nbsp;&nbsp; 妊娠并发症史 <input type="hidden" name="rsbfzs" /> <input
				type="checkbox" pofield="rsbfzs" value="0" name="rsbfzs"
				style="width: 30px;" />无 <input type="checkbox" pofield="rsbfzs"
				value="1" name="rsbfzs" style="width: 30px;" />有 <input id=""
				name="" style="width: 70px;" /></td>
		</tr>
		<tr>
			<td style="width: 20px;">备注</td>
			<td colspan="25"><textarea type="text" id="resbz0"
					pofield="resbz0" name="resbz0" style="width: 100%"></textarea></td>
		</tr>
	</table>

	<table class="layui-table" id="from_grslbz">
		<tr>
			<td rowspan="6">既 往 史</td>
			<td>心</td>
			<td><input type="checkbox" pofield="jwsx00" name="jwsx00"
				value="0" />无 <input type="checkbox" pofield="jwsx00" name="jwsx00"
				value="1" id="jws0000" />异常 <input id="jwsxyc" name="jwsxyc"
				style="width: 40px;" /></td>
			<td>肺</td>
			<td><input type="checkbox" pofield="jwsf00" name="jwsf00"
				value="0" />无 <input type="checkbox" pofield="jwsf00" name="jwsf00"
				value="1" id="jwsf00" />异常 <input id="jwsfyc" name="jwsfyc"
				style="width: 40px;" /></td>
		</tr>
		<tr>
			<td>肾</td>
			<td><input type="checkbox" pofield="jwsg00" name="jwsg00"
				value="0" />无 <input type="checkbox" pofield="jwsg00" name="jwsg00"
				value="1" id="jwsg00" />异常 <input id="jwsg000" name="jwsg000"
				style="width: 40px;" /></td>
			<td>高血压</td>
			<td><input type="checkbox" pofield="jwss00" name="jwss00"
				value="0" />无 <input type="checkbox" pofield="jwss00" name="jwss00"
				value="1" id="jwss00" />异常 <input id="jwssyc" name="jwssyc"
				style="width: 40px;" /></td>
			<td>糖尿病</td>
			<td><input type="checkbox" pofield="jwsgxy" name="jwsgxy"
				value="0" />无 <input type="checkbox" pofield="jwsgxy" name="jwsgxy"
				value="1" id="jwsgxy" />异常 <input id="text11" name="text11"
				style="width: 40px;" /></td>
		</tr>
		<tr>
			<td>过敏史</td>
			<td><input type="checkbox" pofield="jwsgms" name="jwsgms"
				value="0" />未发现 <input type="checkbox" pofield="jwsgms"
				name="jwsgms" value="1" id="jwsgms" />有 <input id="text14"
				name="text14" style="width: 40px;" /></td>
			<td>精神病</td>
			<td><input type="checkbox" pofield="jwsjsb" name="jwsjsb"
				value="0" />无 <input type="checkbox" pofield="jwsjsb" name="jwsjsb"
				value="1" id="jwsjsb" />有 <input id="text15" name="text15"
				style="width: 40px;" /></td>
			<td>血液病</td>
			<td><input type="checkbox" pofield="jwsxyb" name="jwsxyb"
				value="0" />无 <input type="checkbox" pofield="jwsxyb" name="jwsxyb"
				value="1" id="jwsxyb" />有 <input id="text16" name="text16"
				style="width: 40px;" /></td>
		</tr>
		<tr>
			<td>肝</td>
			<td><input type="checkbox" pofield="jwsg00" name="jwsg00"
				value="0" />无 <input type="checkbox" pofield="jwsg00" name="jwsg00"
				value="1" />异常 <input id="jwsg000" name="jwsg000"
				style="width: 40px;" /></td>
			<td>手术史</td>
			<td><input type="checkbox" pofield="jwssss" name="jwssss"
				value="0" />无 <input type="checkbox" pofield="jwssss" name="jwssss"
				value="1" id="jwssss" />有 <input id="text19" name="text19"
				style="width: 40px;" /></td>
			<td>贫血</td>
			<td><input type="checkbox" pofield="jwspx0" value="1"
				style="width: 30px;" />无 <input type="checkbox" pofield="jwspx0"
				value="0" style="width: 30px;" />异常 <input id="text20" name="text20"
				style="width: 40px;" /></td>
		</tr>
		<tr>
			<td>甲亢</td>
			<td><input type="checkbox" pofield="jwsjk0" name="jwsjk0"
				value="0" />无 <input type="checkbox" pofield="jwsjk0" name="jwsjk0"
				value="1" id="jwsjk0" />异常 <input id="text13" name="text13"
				style="width: 40px;" /></td>
			<td>癫痫</td>
			<td><input type="checkbox" pofield="jwsdx0" name="jwsdx0"
				value="0" />无 <input type="checkbox" pofield="jwsdx0" name="jwsdx0"
				value="1" id="jwsdx0" />有 <input id="text17" name="text17"
				style="width: 40px;" /></td>
			<td>其他</td>
			<td><input id="jwsqt0" name="jwsqt0" style="width: 160px;" /></td>
		</tr>
		<tr>
			<td>妇科手术史标志</td>
			<td><input type="checkbox" pofield="fkssbz" value="0"
				style="width: 30px;" />是 <input type="checkbox" pofield="fkssbz"
				value="1" style="width: 30px;" />否</td>
			<td>妇科手术史</td>
			<td><input type="text" id="fksss0" pofield="fksss0"
				name="fksss0" style="width: 160px;" /></td>
		</tr>

		<tr>
			<td rowspan="2">个 人 史</td>
			<td>吸烟</td>
			<td><input type="checkbox" pofield="grsxy0" name="grsxy0"
				value="0" />无 <input type="checkbox" pofield="grsxy0" name="grsxy0"
				value="1" />有 <input id="grsxy0" name="grsxy0" style="width: 40px;" />
			</td>
			<td>饮酒</td>
			<td><input type="checkbox" pofield="grsyj0" name="grsyj0"
				value="0" />无 <input type="checkbox" pofield="grsyj0" name="grsyj0"
				value="1" />有 <input id="grsyj0" name="grsyj0" style="width: 40px;" />
			</td>
			<td>服用药物</td>
			<td><input type="checkbox" pofield="grsyw0" name="grsyw0"
				value="0" />无 <input type="checkbox" pofield="grsyw0" name="grsyw0"
				value="1" />有 <input id="grsyw0" name="grsyw0" style="width: 40px;" />
			</td>
		</tr>
		<tr>
			<td>接触有毒 有害物质</td>
			<td><input type="checkbox" pofield="grsyhw" name="grsyhw"
				value="0" />无 <input type="checkbox" pofield="grsyhw" name="grsyhw"
				value="1" />有 <input id="grsyhw" name="grsyhw" style="width: 40px;" />
			</td>
			<td>接触放射线</td>
			<td><input type="checkbox" pofield="grsfsx" name="grsfsx"
				value="0" />无 <input type="checkbox" pofield="grsfsx" name="grsfsx"
				value="1" />有 <input id="grsfsx" name="grsfsx" style="width: 40px;" />
			</td>

		</tr>
	</table>

	<table class="layui-table" id="from_sljz">
		<tr>
			<td rowspan="3">家 族 史</td>
			<td></td>
			<td>双胎</td>
			<td>高血压</td>
			<td>糖尿病</td>
			<td>遗传病</td>
			<td>精神病</td>
			<td>痴呆</td>
			<td>畸形</td>
			<td>其他</td>
		</tr>
		<tr>
			<td>本人</td>
			<td><input type="text" pofield="jzsbr0" id="jzsbr0"
				name="jzsbr0" style="width: 70px;" /></td>
			<td><input type="text" pofield="jzsgxy" id="jzsgxy"
				name="jzsgxy" style="width: 70px;" /></td>
			<td><input type="text" pofield="jzstnb" id="jzstnb"
				name="jzstnb" style="width: 70px;" /></td>
			<td><input type="text" pofield="jzsycb" id="jzsycb"
				name="jzsycb" style="width: 70px;" /></td>
			<td><input type="text" pofield="jzsjsb" id="jzsjsb"
				name="jzsjsb" style="width: 70px;" /></td>
			<td><input type="text" pofield="jzscd0" id="jzscd0"
				name="jzscd0" style="width: 70px;" /></td>
			<td><input type="text" pofield="jzsjx0" id="jzsjx0"
				name="jzsjx0" style="width: 70px;" /></td>
			<td><input type="text" pofield="jzsqt0" id="jzsqt0"
				name="jzsqt0" style="width: 70px;" /></td>
		</tr>
		<tr>
			<td>爱人</td>
			<td><input type="text" pofield="jzsar0" id="jzsar0"
				name="jzsar0" style="width: 70px;" /></td>
			<td><input type="text" pofield="jzsgxya" id="jzsgxya"
				name="jzsgxya" style="width: 70px;" /></td>
			<td><input type="text" pofield="jzstnba" id="jzstnba"
				name="jzstnba" style="width: 70px;" /></td>
			<td><input type="text" pofield="jzsycba" id="jzsycba"
				name="jzsycba" style="width: 70px;" /></td>
			<td><input type="text" pofield="jzsjsba" id="jzsjsba"
				name="jzsjsba" style="width: 70px;" /></td>
			<td><input type="text" pofield="jzscd0a" id="jzscd0a"
				name="jzscd0a" style="width: 70px;" /></td>
			<td><input type="text" pofield="jzsjx0a" id="jzsjx0a"
				name="jzsjx0a" style="width: 70px;" /></td>
			<td><input type="text" pofield="jzsqt0a" id="jzsqt0a"
				name="jzsqt0a" style="width: 70px;" /></td>
		</tr>
	</table>

	<table class="layui-table" id="from_fncj">
		<tr>
			<td rowspan="5">产检</td>
			<td>基础血压</td>
			<td><input type="text" id="tjjcxy" name="tjjcxy"
				pofield="tjjcxy" style="width: 70px;" />mmHg</td>
			<td>血压</td>
			<td><input type="text" id="tjxy00" name="tjxy00"
				pofield="tjxy00" style="width: 70px;" />mmHg</td>
			<td>身高</td>
			<td><input type="text" id="tjsg00" name="tjsg00"
				pofield="tjsg00" style="width: 70px;" />cm</td>
		</tr>
		<tr>
			<td>体重</td>
			<td><input type="text" id="tjtz00" name="tjtz00"
				pofield="tjtz00" style="width: 70px;" />kg</td>
			<td>孕前体重</td>
			<td><input type="text" id="yqtz00" name="yqtz00"
				pofield="yqtz00" style="width: 70px;" />kg</td>
			<td>体重指数</td>
			<td><input type="text" id="tjtzzs" name="tjtzzs"
				pofield="tjtzzs" style="width: 70px;" /></td>
		</tr>
		<tr>
			<td>宫高(cm)</td>
			<td><input type="text" id="fjjcgg" name="fjjcgg"
				pofield="fjjcgg" style="width: 70px;" />腹围(cm)<input type="text"
				pofield="ckjcfw" id="ckjcfw" name="ckjcfw" style="width: 70px;" /></td>
			<td>胎动</td>
			<td><input type="text" id="td0000" name="td0000"
				pofield="td0000" style="width: 70px;" /></td>
			<td>先露</td>
			<td><input type="checkbox" pofield="xl0000" value="0" />固定 <input
				type="checkbox" pofield="xl0000" value="1" />半固定 <input
				type="checkbox" pofield="xl0000" value="2" />浮<<br> <input
				type="checkbox" pofield="xl0000" value="fy1" />头先露 <input
				type="checkbox" pofield="xl0000" value="fy2" />臀先露 <input
				type="checkbox" pofield="xl0000" value="fy3" />肩先露 <input
				type="checkbox" pofield="xl0000" value="fy4" />足先露 <input
				type="checkbox" pofield="xl0000" value="fy9" />不详</td>
		</tr>
		<tr>
			<td>胎位及胎心率</td>
			<td colspan="5"></td>
		</tr>
		<tr>
			<td>骨盆</td>
			<td>骼棘间径<input type="tetx" id="cjgjjj" name="cjgjjj"
				pofield="cjgjjj" style="width: 70px;" />cm骼嵴间径 <input type="tetx"
				id="cjgj00" name="cjgj00" pofield="cjgj00" style="width: 70px;" />cm
			</td>
			<td colspan="4">骶耻外径 <input type="tetx" id="cjdcwj"
				name="cjdcwj" pofield="cjdcwj" style="width: 70px;" />cm坐骨结节间径 <input
				type="tetx" id="cjzgjj" name="cjzgjj" pofield="cjzgjj"
				style="width: 70px;" />cm

			</td>
		</tr>
		<tr>
			<td rowspan="6">体检</td>
			<td>甲状腺</td>
			<td colspan="5"><input type="checkbox" name="tjjzx0"
				pofield="tjjzx0" value="0" />无肿大、 异常 ( <input type="checkbox"
				name="tjjzx0" pofield="tjjzx0" value="2" />甲状腺Ⅰ度肿大 <input
				type="checkbox" name="tjjzx0" pofield="tjjzx0" value="3" />甲状腺Ⅱ度肿大
				<input type="checkbox" name="tjjzx0" pofield="tjjzx0" value="4" />甲状腺Ⅲ度肿大
				<input type="checkbox" name="tjjzx0" pofield="tjjzx0" value="1" />
				其他 <input id="tjtext" name="tjtext" pofield="tjtext"
				style="width: 80px;" />)</td>
		</tr>
		<tr>
			<td>肺</td>
			<td colspan="5"><input type="checkbox" name="tjf000"
				pofield="tjf000" value="0" />未闻及啰音、异常( <input type="checkbox"
				name="tjf000" pofield="tjf000" value="2" />呼吸音增粗 <input
				type="checkbox" name="tjf000" pofield="tjf000" value="3" />哮鸣音 <input
				type="checkbox" name="tjf000" pofield="tjf000" value="4" />干性啰音 <input
				type="checkbox" name="tjf000" pofield="tjf000" value="5" />湿性啰音 <input
				type="checkbox" name="tjf000" pofield="tjf000" value="1" /> 其他 <input
				id="tftext" name="tftext" pofield="tftext" style="width: 80px;" />)
			</td>
		</tr>
		<tr>
			<td>心</td>
			<td colspan="5"><input type="checkbox" name="tjx000"
				pofield="tjx000" value="0" />律齐无杂音、异常( <input type="checkbox"
				name="tjx000" pofield="tjx000" value="1" />Ⅰ级杂音 <input
				type="checkbox" name="tjx000" pofield="tjx000" value="2" />Ⅱ级杂音 <input
				type="checkbox" name="tjx000" pofield="tjx000" value="3" />Ⅲ级杂音 <input
				type="checkbox" name="tjx000" pofield="tjx000" value="4" />Ⅳ级杂音 <input
				type="checkbox" name="tjx000" pofield="tjx000" value="5" />Ⅴ级杂音 <input
				type="checkbox" name="tjx000" pofield="tjx000" value="6" />偶发早博 <input
				type="checkbox" name="tjx000" pofield="tjx000" value="7" />频发早博 <input
				type="checkbox" name="tjx000" pofield="tjx000" value="8" /> 其他 <input
				id="txtext" name="tjtext" pofield="tjtext" style="width: 80px;" />)
			</td>
		</tr>
		<tr>
			<td>肾</td>
			<td><input type="checkbox" name="tjs000" pofield="tjs000"
				value="0" />无叩击痛 <input type="checkbox" name="tjs000"
				pofield="tjs000" value="1" />异常 <input id="tjsyc0" name="tjsyc0"
				style="width: 40px;" /></td>
			<td>脾</td>
			<td><input type="checkbox" name="tjp000" pofield="tjp000"
				value="0" />肋下未触及 <input type="checkbox" name="tjp000"
				pofield="tjp000" value="1" />异常 <input id="tjpyc0" name="tjpyc0"
				pofield="tjpyc0" style="width: 40px;" /></td>
			<td>乳头</td>
			<td><input type="checkbox" name="tjrt00" pofield="tjrt00"
				value="0" />凸 <input type="checkbox" name="tjrt00" pofield="tjrt00"
				value="1" />凹 <input id="tjrtyc" name="tjrtyc" pofield="tjrtyc"
				style="width: 40px;" /></td>
		</tr>
		<tr>
			<td>脊柱四肢</td>
			<td><input type="checkbox" name="tjjzsz" pofield="tjjzsz"
				value="0" />未见异常 <input type="checkbox" name="tjjzsz"
				pofield="tjjzsz" value="1" />异常 <input id="tjjzszyc"
				name="tjjzszyc" pofield="tjjzszyc" style="width: 40px;" /></td>
			<td>肝</td>
			<td colspan="3"><input type="checkbox" name="tjg000"
				pofield="tjg000" value="0" />肋下未触及或触及不满意 <input type="checkbox"
				name="tjg000" pofield="tjg000" value="1" />异常 <input id="tjgyc0"
				name="tjgyc0" pofield="tjgyc0" style="width: 40px;" /></td>
		</tr>
		<tr>
			<td>浮肿</td>
			<td colspan="3"><input type="checkbox" pofield="tjfz00"
				name="tjfz00" value="0" />无 <input type="checkbox" name="tjfz00"
				pofield="tjfz00" value="1" />有&nbsp; &nbsp; 膝腱反射&nbsp;&nbsp; <input
				type="checkbox" name="tjjfs0" pofield="tjjfs0" value="0" />存在 <input
				type="checkbox" name="tjjfs0" pofield="tjjfs0" value="1" />不存在
				&nbsp; &nbsp; 静脉曲张&nbsp;&nbsp; <input type="checkbox" name="tjjmqz"
				pofield="tjjmqz" value="0" />无 <input type="checkbox" name="tjjmqz"
				pofield="tjjmqz" value="1" />有</td>
			<td>其他</td>
			<td><input id="tjqt00" name="tjqt00" pofield="tjqt00"
				style="width: 80px;" /></td>
		</tr>
		<tr>
			<td rowspan="3">( 拒 检 <input type="checkbox" name="fjjjbz"
				pofield="fjjjbz" value="l" /> ) 妇 科 检 查
			</td>
			<td>外阴 (拒检<input type="checkbox" pofield="wyjjbz" name="wyjjbz"
				value="l" />)
			</td>
			<td><input type="checkbox" name="fjwy00" pofield="fjwy00"
				value="5" />未查 <input type="checkbox" name="fjwy00"
				pofield="fjwy00" value="0" />未见异常 &nbsp;&nbsp;异常( <input
				type="checkbox" name="fjwy00" pofield="fjwy00" value="1" />外阴潮红 <input
				type="checkbox" name="fjwy00" pofield="fjwy00" value="2" />静脉曲张 <input
				type="checkbox" name="fjwy00" pofield="fjwy00" value="3" />外阴囊肿) 其他
				<input id="tjtext" name="tjtext" pofield="tjtext"
				style="width: 150px;" /></td>
			<td>阴道 (拒检<input type="checkbox" name="ydjjbz" pofield="ydjjbz"
				value="l" />)
			</td>
			<td><input type="checkbox" name="fjyd00" pofield="fjyd00"
				value="2" />未查 <input type="checkbox" name="fjyd00"
				pofield="fjyd00" value="0" />畅 <input type="checkbox" name="fjyd00"
				pofield="fjyd00" value="1" />异常 <input id="jwssyc" name="fjydyc"
				pofield="fjydyc" style="width: 40px;" /></td>
		</tr>
		<tr>
			<td>宫颈 (拒检<input type="checkbox" pofield="gjjjbz" name="gjjjbz"
				value="l" /> 未查<input type="checkbox" pofield="gjjjbz"
				name="gjjjbz" value="0" />)
			</td>
			<td colspan="5"><select id="fjgj00" pofield="fjgj00"
				name="fjgj00">
					<option value="-1">请选择</option>
					<option value="1">光滑</option>
					<option value="2">糜烂轻</option>
					<option value="3">糜烂Ⅰ°</option>
					<option value="4">糜烂Ⅱ°</option>
					<option value="5">糜烂Ⅲ°</option>
					<option value="6">息肉</option>
					<option value="7">肥大</option>
					<option value="8">腺囊肿</option>
					<option value="9">接触性出血</option>
					<option value="10">举痛</option>
			</select> 宫体 (拒检<input type="checkbox" name="gtjjbz" pofield="gtjjbz"
				value="l" /> 未查<input type="checkbox" pofield="gtjjbz"
				name="gtjjbz" value="0" />) <input id="gtqt00" name="gtqt00"
				style="width: 400px;" /></td>
		</tr>
		<tr>
			<td>附件 (拒检<input type="checkbox" pofield="fjflag" name="fjflag"
				value="l" /> )
			</td>
			<td><input type="checkbox" pofield="fjfj00" name="fjfj00"
				value="-1" />未查 <input type="checkbox" pofield="fjfj00"
				name="fjfj00" value="0" />未见异常 异常(<input type="checkbox"
				pofield="fjfj00" name="fjfj00" value="1" />左侧肿块 <input
				type="checkbox" pofield="fjfj00" name="fjfj00" value="2" />右侧肿块)</td>
			<td>其他</td>
			<td colspan="3"><input id="tjqt00" pofield="tjqt00"
				name="tjqt00" style="width: 160px;" /></td>
		</tr>
		<tr>
			<td></td>
			<td colspan="6">梅毒、乙肝咨询 <input id="rprjc0" name="rprjc0"
				pofield="rprjc0" type="hidden" /> <input type="checkbox"
				pofield="rprjc0" value="0" />无 <input type="checkbox"
				pofield="rprjc0" value="1" />有&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; HIV咨询 <input
				id="hivjc0" pofield="hivjc0" name="hivjc0" type="hidden" /> <input
				type="checkbox" pofield="hivjc0" value="0" />无 <input
				type="checkbox" pofield="hivjc0" value="1" />有
			</td>
		</tr>
		<tr>
			<td rowspan="5">婚前检查</td>
			<td>地中海贫血</td>
			<td colspan="6">血红蛋白A+F <input id="xhdbaf" name="xhdbaf"
				pofield="xhdbaf" style="width: 70px;" />%&nbsp;&nbsp; 血红蛋白H <input
				id="xhdbh0" name="xhdbh0" pofield="xhdbh0" style="width: 70px;" />%&nbsp;&nbsp;
				血红蛋白F <input id="xhdbf0" name="xhdbf0" pofield="xhdbf0"
				style="width: 70px;" />%&nbsp;&nbsp; 血红蛋白S <input id="xhdbs0"
				name="xhdbs0" pofield="xhdbs0" style="width: 70px;" />%&nbsp;&nbsp;
				血红蛋白A2 <input id="xhdba2" name="xhdba2" pofield="xhdba2"
				style="width: 70px;" />%&nbsp;&nbsp; 血红蛋白C <input id="xhdbc0"
				name="xhdbc0" pofield="xhdbc0" style="width: 70px;" />%&nbsp;&nbsp;
				血红蛋白Bart's <input id="xhdbba" name="xhdbba" pofield="xhdbba"
				style="width: 70px;" />%&nbsp;&nbsp;
			</td>
		</tr>
		<tr>
			<td>乙肝五项</td>
			<td colspan="6">乙型肝炎表面抗原： <select id="hjygbmky"
				pofield="hjygbmky" name="hjygbmky">
					<option value="">请选择</option>
					<option value="0">阴性</option>
					<option value="1">阳性</option>
			</select> &nbsp;&nbsp; 乙型肝炎表面抗体： <select id="hjygbmkt" pofield="hjygbmkt"
				name="hjygbmkt">
					<option value="">请选择</option>
					<option value="0">阴性</option>
					<option value="1">阳性</option>
			</select>&nbsp;&nbsp; 乙型肝炎e抗原： <select id="hjygeky0" pofield="hjygeky0"
				name="hjygeky0">
					<option value="">请选择</option>
					<option value="0">阴性</option>
					<option value="1">阳性</option>
			</select>&nbsp;&nbsp; 乙型肝炎e抗体： <select id="hjygekt0" pofield="hjygekt0"
				name="hjygekt0">
					<option value="">请选择</option>
					<option value="0">阴性</option>
					<option value="1">阳性</option>
			</select>&nbsp;&nbsp; 乙型肝炎核心抗体： <select id="hjyghxkt" pofield="hjyghxkt"
				name="hjyghxkt">
					<option value="">请选择</option>
					<option value="0">阴性</option>
					<option value="1">阳性</option>
			</select>&nbsp;&nbsp;
			</td>
		</tr>
		<tr>
			<td>孕前优生健康检查</td>
			<td colspan="6">弓形虫IgG抗体测定： <select id="gxcigg" pofield="gxcigg"
				name="gxcigg">
					<option value="">请选择</option>
					<option value="0">阴性</option>
					<option value="1">阳性</option>
			</select>&nbsp;&nbsp; 巨细胞病毒IgG抗体测定： <select id="jxbigg" pofield="jxbigg"
				name="jxbigg">
					<option value="">请选择</option>
					<option value="0">阴性</option>
					<option value="1">阳性</option>
			</select>&nbsp;&nbsp; 风疹病毒IgG抗体测定： <select id="fzigg0" pofield="fzigg0"
				name="fzigg0">
					<option value="">请选择</option>
					<option value="0">阴性</option>
					<option value="1">阳性</option>
			</select>&nbsp;&nbsp; II型单纯疱疹IgG抗体测定： <select id="exigg0" pofield="exigg0"
				name="exigg0">
					<option value="">请选择</option>
					<option value="0">阴性</option>
					<option value="1">阳性</option>
			</select>&nbsp;&nbsp;<br> 弓形虫IgM抗体测定： <select id="gxcigm"
				pofield="gxcigm" name="gxcigm">
					<option value="">请选择</option>
					<option value="0">阴性</option>
					<option value="1">阳性</option>
			</select>&nbsp;&nbsp; 巨细胞病毒IgM抗体测定： <select id="jxbigm" pofield="jxbigm"
				name="jxbigm">
					<option value="">请选择</option>
					<option value="0">阴性</option>
					<option value="1">阳性</option>
			</select>&nbsp;&nbsp; 风疹病毒IgM抗体测定： <select id="fzigm0" pofield="fzigm0"
				name="fzigm0">
					<option value="">请选择</option>
					<option value="0">阴性</option>
					<option value="1">阳性</option>
			</select>&nbsp;&nbsp; II型单纯疱疹IgM抗体测定： <select id="exigm0" pofield="exigm0"
				name="exigm0">
					<option value="">请选择</option>
					<option value="0">阴性</option>
					<option value="1">阳性</option>
			</select>&nbsp;&nbsp; 未检： <select id="sfysjc" pofield="sfysjc" name="sfysjc">
					<option value="">请选择</option>
					<option value="0">是</option>
			</select>&nbsp;&nbsp;
			</td>
		</tr>
		<tr>
			<td>促甲状腺激素测定</td>
			<td colspan="6"><input type="hidden" id="cjzcd0"
				pofield="cjzcd0" name="cjzcd0" /> <input type="checkbox" value="0"
				pofield="cjzcd0" id="cjzcd01" />正常 <input type="checkbox" value="1"
				pofield="cjzcd0" id="cjzcd02" />异常 <input id="cjzcd03"
				name="cjzcd03" pofield="cjzcd03" style="width: 160px;" />uIU/mol</td>
		</tr>
		<tr>
			<td>梅毒筛查</td>
			<td><input type="hidden" id="mdsx00" pofield="mdsx00"
				name="mdsx00" /> <input type="checkbox" style="width: 15px"
				pofield="mdsx00" value="0" id="mdsc01"> 阴性 <input
				type="checkbox" style="width: 15px" pofield="mdsx00" value="1"
				id="mdsc02"> 阳性 <input type="text" style="width: 67%"
				pofield="mdsc03" id="mdsc03"></td>
			<td>HIV筛查</td>
			<td><input type="hidden" id="hivsc0" pofield="hivsc0"
				name="hivsc0" /> <input type="checkbox" style="width: 15px"
				value="-1" pofield="hivsc03" id="hivsc03"> 未筛查 <input
				type="text" style="width: 45%" pofield="hivsc04" id="hivsc04">
				<input type="checkbox" style="width: 15px" pofield="hivsc01"
				value="0" id="hivsc01"> 阴性 <input type="checkbox"
				style="width: 15px" pofield="hivsc02" value="1" id="hivsc02">
				阳性</td>
		</tr>
	</table>

	<table class="layui-table" id="">
		<tr>
			<td>( 大 致 正 常 <input type="checkbox" id="fjjcbz"
				pofield="fjjcbz" name="fjjcbz" /> ) 辅 助 检 查
			</td>
		</tr>

	</table>
	<table class="layui-table" id="from_fngw">
		<tr>
			<td rowspan="4">高 危 评 分</td>
			<td>孕周</td>
			<td colspan="2">高危因素</td>
			<td>评分</td>
			<td>预约时间</td>
		</tr>
		<tr>
			<td><input id="gwyzy0" name="gwyzy0" pofield="gwyzy0"
				style="width: 70px;" /></td>
			<td colspan="2"><textarea id="gwys00" name="gwys00"
					pofield="gwys00" style="width: 170px;"></textarea></td>
			<td><input id="gwpf00" name="gwpf00" pofield="gwpf00"
				style="width: 70px;" /></td>
			<td><input id="gwyyrq" name="gwyyrq" pofield="gwyyrq"
				style="width: 70px;" /></td>
		</tr>
		<tr>
			<td><input id="gwyze0" name="gwyze0" pofield="gwyze0"
				style="width: 70px;" /></td>
			<td colspan="2"><textarea id="gwyse0" name="gwyse0"
					pofield="gwyse0" style="width: 170px;"></textarea></td>
			<td><input id="gwpfe0" name="gwpfe0" pofield="gwpfe0"
				style="width: 70px;" /></td>
			<td><input id="yyrqe0" name="yyrqe0" pofield="yyrqe0"
				style="width: 70px;" /></td>
		</tr>
		<tr>
			<td><input id="gwyzs0" name="gwyzs0" pofield="gwyzs0"
				style="width: 70px;" /></td>
			<td colspan="2"><textarea id="gwyss0" name="gwyss0"
					pofield="gwyss0" style="width: 170px;"></textarea></td>
			<td><input id="gwpfs0" name="gwpfs0" pofield="gwpfs0"
				style="width: 70px;" /></td>
			<td><input id="yyrqs0" name="yyrqs0" pofield="yyrqs0"
				style="width: 70px;" /></td>
		</tr>
		<tr>
			<td colspan="2">保健指导</td>
			<td colspan="2"><textarea id="bjzd00" name="bjzd00"
					pofield="bjzd00" style="width: 170px;"></textarea></td>
			<td>其他</td>
			<td><textarea id="bjzdqt" name="bjzdqt" pofield="bjzdqt"
					style="width: 170px;"></textarea></td>
		</tr>
		<tr>
			<td colspan="2">初诊诊断</td>
			<td>1.G<input type="checkbox" id="czzdg0" pofield="czzdg0"
				name="" />&nbsp;&nbsp;&nbsp;&nbsp; P<input type="checkbox"
				id="czzdp0" pofield="czzdp0" name="" />&nbsp;&nbsp;&nbsp;&nbsp; <input
				type="checkbox" id="czzdrs" pofield="czzdrs" name="" />周宫内妊娠
			</td>
			<td>诊断</td>
			<td><input type="text" id="czzdnr" pofield="czzdnr"
				name="czzdnr" /></td>
		</tr>
		<tr>
			<td colspan="2">录入者</td>
			<td><input type="text" id="bsxwz0" pofield="bsxwz0"
				name="bsxwz0" /></td>
			<td>检查者</td>
			<td><input type="text" id="jcz000" pofield="jcz000"
				name="jcz000" /></td>
			<td>体检日期</td>
			<td><input type="text" id="tjrq00" pofield="tjrq00"
				name="tjrq00" /></td>
			<td>复检预约日期</td>
			<td><input type="text" id="fjyyrq" pofield="fjyyrq"
				name="fjyyrq" /></td>
		</tr>
	</table>
	<table class="layui-table" id="from_fnzy">
		<tr>
			<td rowspan="3">入院</td>
			<td>日期</td>
			<td><input type="text" id="ryrq00" pofield="ryrq00"
				name="ryrq00" /></td>
			<td>签名</td>
			<td><input type="text" id="qm0000" pofield="qm0000"
				name="qm0000" /></td>
		</tr>
		<tr>
			<td>主诉</td>
			<td><textarea type="text" id="zs0000" pofield="zs0000"
					name="zs0000"></textarea></td>
			<td>处理</td>
			<td><input type="hidden" id="cl0000" pofield="cl0000"
				name="cl0000" /> <input type="checkbox" pofield="cl0000" value="5" />左侧卧位，自测胎动&nbsp;
				<input type="checkbox" pofield="cl0000" value="1" />
				吸氧半小时，不适及时就诊&nbsp;<br> <input type="checkbox" pofield="cl0000"
				value="2" /> 收住院&nbsp; <input type="checkbox" pofield="cl0000"
				value="3" /> GDM(转综合性医院治疗)&nbsp; <input type="checkbox"
				pofield="cl0000" value="4" /> GDM(转综合性医院治疗)&nbsp;<br> <textarea
					type="text" pofield="zztext2" id="zztext2" name="zztext"></textarea></td>
		</tr>
		<tr>
			<td>诊断</td>
			<td><input type="text" id="ryzd00" pofield="ryzd00"
				name="ryzd00" /></td>
			<td>转诊</td>
			<td><input type="hidden" id="zz0000" pofield="zz0000"
				name="zz0000" /> <input type="checkbox" pofield="zz0000" value="0" />无
				<input type="checkbox" pofield="zz0000" value="1" />有&nbsp;&nbsp; 原因<input
				type="text" id="zztext" pofield="zztext" name="zztext" /></td>
		</tr>
	</table>

</body>
</html>
