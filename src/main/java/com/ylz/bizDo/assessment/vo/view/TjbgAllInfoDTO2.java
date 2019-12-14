package com.ylz.bizDo.assessment.vo.view;

import com.fasterxml.jackson.annotation.JsonView;

public class TjbgAllInfoDTO2 {
	private String bz0000;
	public String getBz0000() {
		return bz0000;
	}
	public void setBz0000(String bz0000) {
		this.bz0000 = bz0000;
	}
	@JsonView({IFindGroup.class})
	private String df_id;//居民档案号
	
	private String idcardno;// 身份证号
	private String name;// 居民姓名
	private String telphone;// 联系电话
	private String birthday; // 出生日期
	private String sex; // 性别
	private String f_id; // 家庭档案号
	private String address;// 完整地址
	private String adress_city; // 常住地址：市（地区）
	private String adress_county; // 常住地址：县（区）
	private String adress_rural; // 常住地址：乡（镇、街道办事处）
	private String adress_village; // 常住地址：村（街、路、弄）
	private String adrss_hnumber; // 常住地址：门牌号码
	private String adress_pro; // 常住地址：省（自治区、直辖市）
	private String jname; // 居委会名称
	
	@JsonView({IFindGroup.class})
	private String yyid00;//医院编号
	@JsonView({IFindGroup.class})
	private String  jktj_ybzkid;//健康体检--一般状况ID(SEQ_JKTJ_YBZKID)
	@JsonView({IFindGroup.class})
	private String  ybzk_tiwen;//健康体检--一般状况--体温
	@JsonView({IFindGroup.class})
	private String  ybzk_ml;//健康体检--一般状况--脉率
	@JsonView({IFindGroup.class})
	private String  ybzk_hxpl;//健康体检--一般状况-呼吸频率
	@JsonView({IFindGroup.class})
	private String  ybzk_zszy;//健康体检--一般状况-左舒张压
	@JsonView({IFindGroup.class})
	private String  ybzk_zssy;//健康体检--一般状况-左收缩压
	@JsonView({IFindGroup.class})
	private String  ybzk_yszy;//健康体检--一般状况-右舒张压
	@JsonView({IFindGroup.class})
	private String  ybzk_yssy;//健康体检--一般状况-右收缩压
	@JsonView({IFindGroup.class})
	private String  ybzk_sg;//健康体检--一般状况--身高
	@JsonView({IFindGroup.class})
	private String  ybzk_tz;//健康体检--一般状况--体重
	@JsonView({IFindGroup.class})
	private String  ybzk_yw;//健康体检--一般状况--腰围
	@JsonView({IFindGroup.class})
	private String  ybzk_tzzs;//健康体检一般状况-体质指数
	@JsonView({IFindGroup.class})
	private String  ybzk_tunwei;//健康体检--一般状况--臀围
	@JsonView({IFindGroup.class})
	private String  ybzk_ytwbz;//健康体检--一般状况--腰臀围比值
	@JsonView({IFindGroup.class})
	private String  ybzk_lnrzgn;//健康体检--一般状况--老年人认知功能
	@JsonView({IFindGroup.class})
	private String  ybzk_lnzljc;//健康体检--一般状况--老年人智力状态检查
	@JsonView({IFindGroup.class})
	private String  ybzk_lnqgzt;//健康体检--一般状况--老年人情感状态
	@JsonView({IFindGroup.class})
	private String  ybzk_lnyypf;//健康体检--一般状况--老年人抑郁评分检查
	@JsonView({IFindGroup.class})
	private String  jktjcs;//健康体检--体检次数
	@JsonView({IFindGroup.class})
	private String  edate;//检查日期
	@JsonView({IFindGroup.class})
	private String  doctor;//责任医生
	@JsonView({IFindGroup.class})
	private String  tjzzqk;//健康体检症状情况
	@JsonView({IFindGroup.class})
	private String  zz_qt0000;//症状其他
	@JsonView({IFindGroup.class})
	private String  lnrjkpj;//老年人健康状态自我评估
	@JsonView({IFindGroup.class})
	private String  lnrshpj;//老年人生活自理能力自我评估
	@JsonView({IFindGroup.class})
	private String  gxrq00;//更新日期
	@JsonView({IFindGroup.class})
	private String  gxsj00;//更新时间
	@JsonView({IFindGroup.class})
	private String  gxygbh;//更新员工编号
	@JsonView({IFindGroup.class})
	private String  gxygxm;//更新员工姓名
	@JsonView({IFindGroup.class})
	private String  dsrc;//数据来源  1移动端  其它web端
	@JsonView({IFindGroup.class})
	private String  jktj_sfhs_id;//编号
	@JsonView({IFindGroup.class})
	private String  sfhs_zybl_jtzy;//职业暴露--具体职业
	@JsonView({IFindGroup.class})
	private String  sfhs_zybl_cysj;//职业暴露--从业时间（年）
	@JsonView({IFindGroup.class})
	private String  sfhs_zybl_hxp;//职业暴露--化学品
	@JsonView({IFindGroup.class})
	private String  sfhs_zybl_hxpcs;//职业暴露--化学品--防护措施（1--无，2--有）
	@JsonView({IFindGroup.class})
	private String  sfhs_zybl_dw;//职业暴露--毒物
	@JsonView({IFindGroup.class})
	private String  sfhs_zybl_dwcs;//职业暴露--毒物--防护措施（1--无，2--有）
	@JsonView({IFindGroup.class})
	private String  sfhs_zybl_sx;//职业暴露--射线
	@JsonView({IFindGroup.class})
	private String  sfhs_zybl_sxcs;//职业暴露--射线--防护措施（1--无，2--有）
	@JsonView({IFindGroup.class})
	private String  shfs_tydl_jcdlsj;//体育锻炼--坚持锻炼时间
	@JsonView({IFindGroup.class})
	private String  shsf_ysxg;//饮食习惯（1--荤素均衡，2--荤食为主，3--素食为主 ，4--嗜盐，5--嗜油，6--嗜糖）
	@JsonView({IFindGroup.class})
	private String  shsf_xyqk_xynl;//吸烟情况--吸烟年龄
	@JsonView({IFindGroup.class})
	private String  shsf_xyqk_jynl;//吸烟情况--戒烟年龄
	@JsonView({IFindGroup.class})
	private String  shfs_yjqk_jjnl;//饮酒情况--戒酒年龄
	@JsonView({IFindGroup.class})
	private String  shfs_yjqk_ksyjnl;//饮酒情况--开始饮酒年龄
	@JsonView({IFindGroup.class})
	private String  shfs_yjqk_sfcjj;//饮酒情况--近一年内是否曾醉酒
	@JsonView({IFindGroup.class})
	private String  shfs_yjzl_;//饮酒情况--饮酒种类（1--白酒，2--啤酒，3--红酒，4--黄酒，5--其他）
	@JsonView({IFindGroup.class})
	private String  shfs_yjzl_qt0000;//饮酒情况--饮酒种类--其他
	@JsonView({IFindGroup.class})
	private String  shfs_zybl_qk;//职业暴露--情况（是否）
	@JsonView({IFindGroup.class})
	private String  sfhs_zybl_hxpcsnr;//职业暴露--化学品--防护措施内容
	@JsonView({IFindGroup.class})
	private String  sfhs_zybl_dwcsnr;//职业暴露--毒物--防护措施内容
	@JsonView({IFindGroup.class})
	private String  sfhs_zybl_sxcsnr;//职业暴露--射线--防护措施内容
	@JsonView({IFindGroup.class})
	private String  shfs_tydl_dlpl;//锻炼频率
	@JsonView({IFindGroup.class})
	private String  shfs_tydl_mcdlsj;//每次锻炼时间（分钟）
	@JsonView({IFindGroup.class})
	private String  shfs_tydl_dlfs;//锻炼方式
	@JsonView({IFindGroup.class})
	private String  shsf_xyqk_xyzk;//吸烟情况--吸烟状况
	@JsonView({IFindGroup.class})
	private String  shsf_xyqk_rxyl;//吸烟情况--日吸烟量
	@JsonView({IFindGroup.class})
	private String  shfs_yjqk_yjpl;//饮酒情况--饮酒频率
	@JsonView({IFindGroup.class})
	private String  shfs_yjqk_ryjl;//饮酒情况--日饮酒量
	@JsonView({IFindGroup.class})
	private String  shfs_yjqk_sfjj;//饮酒情况--是否戒酒
	@JsonView({IFindGroup.class})
	private String  sfhs_zybl_fc;//职业暴露--粉尘
	@JsonView({IFindGroup.class})
	private String  sfhs_zybl_fccs;//职业暴露--粉尘--防护措施（1--无，2--有）
	@JsonView({IFindGroup.class})
	private String  sfhs_zybl_qt;//职业暴露--其他
	@JsonView({IFindGroup.class})
	private String  sfhs_zybl_qtcs;//职业暴露--其他--防护措施（1--无，2--有）
	@JsonView({IFindGroup.class})
	private String  sfhs_zybl_fccsnr;//职业暴露--粉尘--防护措施内容
	@JsonView({IFindGroup.class})
	private String  sfhs_zybl_qtcsnr;//职业暴露--其他--防护措施内容
	@JsonView({IFindGroup.class})
	private String  jktj_zqgnid;//健康体检--脏器功能ID
	@JsonView({IFindGroup.class})
	private String  jktj_zqgn_kqkc;//健康体检--脏器功能--口腔-口唇
	@JsonView({IFindGroup.class})
	private String  jktj_zqgn_kqcl;//健康体检--脏器功能--口腔-齿列（1--正常，2--缺齿，3--龋齿，4--义齿）
	@JsonView({IFindGroup.class})
	private String  jktj_zqgn_kqyb;//健康体检--脏器功能--口腔-咽部
	@JsonView({IFindGroup.class})
	private String  jktj_zqgn_slzy;//健康体检--脏器功能-视力左眼
	@JsonView({IFindGroup.class})
	private String  jktj_zqgn_slyy;//健康体检--脏器功能-视力右眼
	@JsonView({IFindGroup.class})
	private String  jktj_zqgn_jzslzy;//健康体检--脏器功能--矫正视力左眼
	@JsonView({IFindGroup.class})
	private String  jktj_zqgn_jzslyy;//健康体检--脏器功能--矫正视力右眼
	@JsonView({IFindGroup.class})
	private String  jktj_zqgn_tl;//健康体检--脏器功能--听力
	@JsonView({IFindGroup.class})
	private String  jktj_zqgn_ydgn;//健康体检--脏器功能-运动功能
	@JsonView({IFindGroup.class})
	private String  jktj_zqgn_quechi;//健康体检--脏器功能--口腔--齿列（缺齿情况，上下左右分别用分号拼接起来）
	@JsonView({IFindGroup.class})
	private String  jktj_zqgn_quchi0;//健康体检--脏器功能--口腔--齿列（龋齿情况，上下左右分别用分号拼接起来）
	@JsonView({IFindGroup.class})
	private String  jktj_zqgn_yichi0;//健康体检--脏器功能--口腔--齿列（义齿情况，上下左右分别用分号拼接起来）
	@JsonView({IFindGroup.class})
	private String  jktj_ctid;//健康体检--查体ID
	@JsonView({IFindGroup.class})
	private String  ct_pf;//查体--皮肤
	@JsonView({IFindGroup.class})
	private String  ct_pfqt;//查体--皮肤其他
	@JsonView({IFindGroup.class})
	private String  ct_gm;//查体--巩膜
	@JsonView({IFindGroup.class})
	private String  ct_gmqt;//查体--巩膜其他
	@JsonView({IFindGroup.class})
	private String  ct_lbj;//查体--淋巴结
	@JsonView({IFindGroup.class})
	private String  ct_lbjqt;//查体--淋巴结其他
	@JsonView({IFindGroup.class})
	private String  ct_ftzx;//查体--肺--桶状胸
	@JsonView({IFindGroup.class})
	private String  ct_fhxy;//查体--肺--呼吸音
	@JsonView({IFindGroup.class})
	private String  ct_fhxyyc;//查体--肺--呼吸音异常
	@JsonView({IFindGroup.class})
	private String  ct_fly;//查体--肺--罗音
	@JsonView({IFindGroup.class})
	private String  ct_flyqt;//查体--肺--罗音其他
	@JsonView({IFindGroup.class})
	private String  ct_xzxl;//查体--心脏--心率
	@JsonView({IFindGroup.class})
	private String  ct_xzxinl;//查体--心脏--心律
	@JsonView({IFindGroup.class})
	private String  ct_xzzy;//查体--心脏--杂音
	@JsonView({IFindGroup.class})
	private String  ct_xzzyqt;//查体--心脏--杂音其他
	@JsonView({IFindGroup.class})
	private String  ct_fbyt;//查体--腹部--压痛
	@JsonView({IFindGroup.class})
	private String  ct_fbytqt;//查体--腹部--压痛其他
	@JsonView({IFindGroup.class})
	private String  ct_fbbk;//查体--腹部--包块
	@JsonView({IFindGroup.class})
	private String  ct_fbbkqt;//查体--腹部--包块其他
	@JsonView({IFindGroup.class})
	private String  ct_fbgd;//查体--腹部--肝大
	@JsonView({IFindGroup.class})
	private String  ct_fbgdqt;//查体--腹部--肝大其他
	@JsonView({IFindGroup.class})
	private String  ct_fbpd;//查体--腹部--脾大
	@JsonView({IFindGroup.class})
	private String  ct_fbpdqt;//查体--腹部--脾大其他
	@JsonView({IFindGroup.class})
	private String  ct_fbydzy;//查体--腹部--移动性浊音
	@JsonView({IFindGroup.class})
	private String  ct_fbydzyqt;//查体--腹部--移动性浊音其他
	@JsonView({IFindGroup.class})
	private String  ct_xzsz;//查体--下肢水肿
	@JsonView({IFindGroup.class})
	private String  ct_zbdmbd;//查体--足背动脉搏动
	@JsonView({IFindGroup.class})
	private String  ct_gmzz;//查体--肛门指诊
	@JsonView({IFindGroup.class})
	private String  ct_gmzzqt;//查体--肛门指诊其他
	@JsonView({IFindGroup.class})
	private String  ct_rxqk;//查体--乳腺情况
	@JsonView({IFindGroup.class})
	private String  ct_rxqt;//查体--乳腺其他
	@JsonView({IFindGroup.class})
	private String  ct_fkwy;//查体--妇科--外阴
	@JsonView({IFindGroup.class})
	private String  ct_fkwyqt;//查体--妇科--外阴其他
	@JsonView({IFindGroup.class})
	private String  ct_fkyd;//查体--妇科--阴道
	@JsonView({IFindGroup.class})
	private String  ct_fkydqt;//查体--妇科--阴道其他
	@JsonView({IFindGroup.class})
	private String  ct_fkgj;//查体--妇科--宫颈
	@JsonView({IFindGroup.class})
	private String  ct_fkgjqt;//查体--妇科--宫颈其他
	@JsonView({IFindGroup.class})
	private String  ct_fkgt;//查体--妇科--宫体
	@JsonView({IFindGroup.class})
	private String  ct_fkgtqt;//查体--妇科--宫体其他
	@JsonView({IFindGroup.class})
	private String  ct_fkfj;//查体--妇科--附件
	@JsonView({IFindGroup.class})
	private String  ct_fkfjqt;//查体--妇科--附件其他
	@JsonView({IFindGroup.class})
	private String  ct_qt;//查体--其他
	@JsonView({IFindGroup.class})
	private String  jktj_fzjcid;//健康体检--辅助检查ID(SEQ_JKJC_FZJCID)
	@JsonView({IFindGroup.class})
	private String  fzjc_kfxt;//辅助检查--空腹血糖
	@JsonView({IFindGroup.class})
	private String  fzjc_kfxthk;//辅助检查--空腹血糖mg
	@JsonView({IFindGroup.class})
	private String  fzjc_xcghdb;//辅助检查--血常规--血红蛋白
	@JsonView({IFindGroup.class})
	private String  fzjc_xcgqt;//辅助检查--血常规--其他
	@JsonView({IFindGroup.class})
	private String  fzjc_ncgnt;//辅助检查--尿常规--尿糖
	@JsonView({IFindGroup.class})
	private String  fzjc_ncgqt;//辅助检查--尿常规--其他
	@JsonView({IFindGroup.class})
	private String  fzjc_nwlbdb;//辅助检查--尿微量白蛋白
	@JsonView({IFindGroup.class})
	private String  fzjc_dbqx;//辅助检查--大便潜血
	@JsonView({IFindGroup.class})
	private String  fzjc_ggnxqb;//辅助检查--肝功能--血清谷丙转氨酶
	@JsonView({IFindGroup.class})
	private String  fzjc_ggnxqc;//辅助检查--肝功能--血清谷草转氨酶
	@JsonView({IFindGroup.class})
	private String  fzjc_ggnzdh;//辅助检查--肝功能--总胆红素
	@JsonView({IFindGroup.class})
	private String  fzjc_ggnjhd;//辅助检查--肝功能--结合胆红素
	@JsonView({IFindGroup.class})
	private String  fzjc_sgnqjg;//辅助检查--肾功能--血清肌酐
	@JsonView({IFindGroup.class})
	private String  fzjc_sgnnsd;//辅助检查--肾功能--血尿素氮
	@JsonView({IFindGroup.class})
	private String  fzjc_sgnjnd;//辅助检查--肾功能--血钾浓度
	@JsonView({IFindGroup.class})
	private String  fzjc_sgnnnd;//辅助检查--肾功能--血钠浓度
	@JsonView({IFindGroup.class})
	private String  fzjc_xzzdgc;//辅助检查--血脂--总胆固醇
	@JsonView({IFindGroup.class})
	private String  fzjc_xzgysz;//辅助检查--血脂--甘油三酯
	@JsonView({IFindGroup.class})
	private String  fzjc_xzdmdz;//辅助检查--血脂--血清低密度脂蛋白胆固醇
	@JsonView({IFindGroup.class})
	private String  fzjc_xzgmdz;//辅助检查--血脂--血清高密度脂蛋白胆固醇
	@JsonView({IFindGroup.class})
	private String  fzjc_thxhdb;//辅助检查--糖化血红蛋白
	@JsonView({IFindGroup.class})
	private String  fzjc_yxgyky;//辅助检查--乙型肝炎表面抗原
	@JsonView({IFindGroup.class})
	private String  fzjc_yd;//辅助检查--眼底
	@JsonView({IFindGroup.class})
	private String  fzjc_ydqt;//辅助检查--眼底异常
	@JsonView({IFindGroup.class})
	private String  fzjc_xdt;//辅助检查--心电图
	@JsonView({IFindGroup.class})
	private String  fzjc_xdtqt;//辅助检查--心电图异常
	@JsonView({IFindGroup.class})
	private String  fzjc_xbxxp;//辅助检查--胸部X线片
	@JsonView({IFindGroup.class})
	private String  fzjc_xbxxqt;//辅助检查--胸部X线片异常
	@JsonView({IFindGroup.class})
	private String  fzjc_bc;//辅助检查--B超
	@JsonView({IFindGroup.class})
	private String  fzjc_bcqt;//辅助检查--B超其他
	@JsonView({IFindGroup.class})
	private String  fzjc_gjtp;//辅助检查--宫颈涂片
	@JsonView({IFindGroup.class})
	private String  fzjc_gjtpqt;//辅助检查--宫颈涂片异常
	@JsonView({IFindGroup.class})
	private String  fzjc_qt0000;//辅助检查--其他
	@JsonView({IFindGroup.class})
	private String  ncgndb;//辅助检查--尿常规--尿蛋白（填写）
	@JsonView({IFindGroup.class})
	private String  ncgnt;//辅助检查--尿常规--尿糖（填写）
	@JsonView({IFindGroup.class})
	private String  ncgntt;//辅助检查--尿常规--尿酮体（填写）
	@JsonView({IFindGroup.class})
	private String  ncgnqx;//辅助检查--尿常规--尿潜血（填写）
	@JsonView({IFindGroup.class})
	private String  fzjc_ncgbxb;//尿常规 尿白细胞
	@JsonView({IFindGroup.class})
	private String  fzjc_ncgdhs;//尿常规 尿胆红素
	@JsonView({IFindGroup.class})
	private String  fzjc_ncgyxsy;//尿常规 尿亚硝酸盐
	@JsonView({IFindGroup.class})
	private String  fzjc_ggnag;//肝功能 白蛋白/球蛋白比值(*)
	@JsonView({IFindGroup.class})
	private String  fzjc_ggnudbil;//肝功能 间接胆红素(*)
	@JsonView({IFindGroup.class})
	private String  fzjc_ncgalp;//肝功能 碱性磷酸酶(ALP)
	@JsonView({IFindGroup.class})
	private String  fzjc_ggnggt;//肝功能 r-谷氨酰转移酶
	@JsonView({IFindGroup.class})
	private String  fzjc_sgnns;//肾功能 尿酸
	@JsonView({IFindGroup.class})
	private String  fzjc_xdtfrist;//辅助检查--心电图
	@JsonView({IFindGroup.class})
	private String  fzjc_xdtsecond;//辅助检查--心电图
	@JsonView({IFindGroup.class})
	private String  fzjc_xdtthree;//辅助检查--心电图
	@JsonView({IFindGroup.class})
	private String  jktj_zyjkwtid;//健康体检--主要健康问题ID
	@JsonView({IFindGroup.class})
	private String  zyjkwt_nxg;//主要健康问题--脑血管疾病（1--未发现，2--缺血性卒中，3--脑出血，4--蛛网膜下腔出血，5--短暂性脑缺血发作，6--其他）
	@JsonView({IFindGroup.class})
	private String  zyjkwt_nxgqt;//主要健康问题--脑血管疾病--其他
	@JsonView({IFindGroup.class})
	private String  zyjkwt_sz;//主要健康问题--肾脏疾病（1--未发现，2--糖尿病肾病，3--肾功能衰竭，4--急性肾炎，5--慢性肾炎，6--其他）
	@JsonView({IFindGroup.class})
	private String  zyjkwt_szqt;//主要健康问题--肾脏疾病--其他
	@JsonView({IFindGroup.class})
	private String  zyjkwt_xzwfx;//主要健康问题--心脏疾病（1--未发现，2--心肌梗死，3--心绞痛，4--冠状动脉血运重建，5--心力衰竭，6--心前区疼痛，7--其他）
	@JsonView({IFindGroup.class})
	private String  zyjkwt_xzqt;//主要健康问题--心脏疾病--其他
	@JsonView({IFindGroup.class})
	private String  zyjkwt_xgwfx;//主要健康问题--血管疾病（1--未发现，2--夹层动脉瘤，3--动脉闭塞性疾病，4--其他）
	@JsonView({IFindGroup.class})
	private String  zyjkwt_xgqt;//主要健康问题--血管疾病--其他
	@JsonView({IFindGroup.class})
	private String  zyjkwt_ybwfx;//主要健康问题--眼部疾病（1--未发现，2--视网膜出血或渗出，3--视乳头水肿，4--白内障，5--其他）
	@JsonView({IFindGroup.class})
	private String  zyjkwt_ybqt;//主要健康问题--眼部疾病--其他
	@JsonView({IFindGroup.class})
	private String  zyjkwt_sjxtjb;//主要健康问题--神经系统疾病（1-未发现，2-有）

	@JsonView({IFindGroup.class})
	private String  zyjkwt_sjxtqt;//主要健康问题--神经系统疾病其他
	@JsonView({IFindGroup.class})
	private String  zyjkwt_qtxtjb;//主要健康问题--其他系统疾病（1--未发现，2-有）
	@JsonView({IFindGroup.class})
	private String  zyjkwt_qtxtqt;//主要健康问题-其他系统疾病其他
	@JsonView({IFindGroup.class})
	private String  zytzbs_phz;//健康体检--中医体质辨识--平和质
	@JsonView({IFindGroup.class})
	private String  zytzbs_qxz;//健康体检--中医体质辨识--气虚质
	@JsonView({IFindGroup.class})
	private String  zytzbs_yangxz;//健康体检--中医体质辨识--阳虚质
	@JsonView({IFindGroup.class})
	private String  zytzbs_yinxz;//健康体检--中医体质辨识--阴虚质
	@JsonView({IFindGroup.class})
	private String  zytzbs_tsz;//健康体检--中医体质辨识--痰湿质
	@JsonView({IFindGroup.class})
	private String  zytzbs_srz;//健康体检--中医体质辨识--湿热质
	@JsonView({IFindGroup.class})
	private String  zytzbs_xyz;//健康体检--中医体质辨识--血瘀质
	@JsonView({IFindGroup.class})
	private String  zytzbs_qyz;//健康体检--中医体质辨识--气郁质
	@JsonView({IFindGroup.class})
	private String  zytzbs_tbz;//健康体检--中医体质辨识--特秉质
	@JsonView({IFindGroup.class})
	private String  jktj_jkpjid;//健康体检--健康评价ID
	@JsonView({IFindGroup.class})
	private String  jkpj_tjsfyc;//健康评价--体检是否异常（1--体检无异常，2--有异常）
	@JsonView({IFindGroup.class})
	private String  jkpj_tjyc1;//健康评价--体检异常1
	@JsonView({IFindGroup.class})
	private String  jkpj_tjyc2;//健康评价--体检异常2
	@JsonView({IFindGroup.class})
	private String  jkpj_tjyc3;//健康评价--体检异常3
	@JsonView({IFindGroup.class})
	private String  jkpj_tjyc4;//健康评价--体检异常4
	@JsonView({IFindGroup.class})
	private String  jkzd;//健康指导（1--定期随访，2--纳入慢性病患者健康管理，3--建议复诊，4---建议转诊）
	@JsonView({IFindGroup.class})
	private String  wxyskz;//危险因素控制（1--戒烟，2--健康饮酒，3--饮食，4--锻炼，5--减体重，6--建议疫苗接种，7--其他）
	@JsonView({IFindGroup.class})
	private String  wxyskz_jtzmb;//危险因素控制--减体重目标
	@JsonView({IFindGroup.class})
	private String  wxyskz_ymjz;//危险因素控制--疫苗接种
	@JsonView({IFindGroup.class})
	private String  wxyskz_qt;//危险因素控制-其他
	@JsonView({IFindGroup.class})
	private String  bsxxjg;//辨识选项结果
	@JsonView({IFindGroup.class})
	private String  qxzscore;//气虚质分数
	@JsonView({IFindGroup.class})
	private String  yx0score;//阳虚质
	@JsonView({IFindGroup.class})
	private String  yxzscore;//阴虚质
	@JsonView({IFindGroup.class})
	private String  tszscore;//痰湿质
	@JsonView({IFindGroup.class})
	private String  srzscore;//湿热质
	@JsonView({IFindGroup.class})
	private String  xyzscore;//血瘀质
	@JsonView({IFindGroup.class})
	private String  qyzscore;//气郁质
	@JsonView({IFindGroup.class})
	private String  tbzscore;//特禀质
	@JsonView({IFindGroup.class})
	private String  phzscore;//平和质
	@JsonView({IFindGroup.class})
	private String  tzbstj;//体质辨识统计
	@JsonView({IFindGroup.class})
	private String  bjzdtj;//中医药保健指导统计
	@JsonView({IFindGroup.class})
	private String  zdid00;//测试医生
	@JsonView({IFindGroup.class})
	private String  cjrq00;//创建日期
	@JsonView({IFindGroup.class})
	private String  zhbjrq;//最后编辑日期
	@JsonView({IFindGroup.class})
	private String  qxzqt0;//气虚质其他
	@JsonView({IFindGroup.class})
	private String  yx0qt0;//阳虚质其他
	@JsonView({IFindGroup.class})
	private String  yxzqt0;//阴虚质其他
	@JsonView({IFindGroup.class})
	private String  tszqt0;//痰湿质其他
	@JsonView({IFindGroup.class})
	private String  srzqt0;//湿热质其他
	@JsonView({IFindGroup.class})
	private String  xyzqt0;//血瘀质其他
	@JsonView({IFindGroup.class})
	private String  qyzqt0;//气郁质其他
	@JsonView({IFindGroup.class})
	private String  tbzqt0;//特禀质其他
	@JsonView({IFindGroup.class})
	private String  phzqt0;//平和质其他
	@JsonView({IFindGroup.class})
	private String  xgpg00;//效果评估
	@JsonView({IFindGroup.class})
	private String  myddc0;//满意度调查
	@JsonView({IFindGroup.class})
	private String  tzlx00;//体质类型
	@JsonView({IFindGroup.class})
	private String  tzbm00;//体质名称
	@JsonView({IFindGroup.class})
	private String  qzts00;//情志调摄
	@JsonView({IFindGroup.class})
	private String  state;//标志
	@JsonView({IFindGroup.class})
	private String  zybszdid;//体质类型ID
	@JsonView({IFindGroup.class})
	private String  userid;//所属人ID
	@JsonView({IFindGroup.class})
	private String  ysty00;//饮食调养
	@JsonView({IFindGroup.class})
	private String  qjts00;//起居调摄
	@JsonView({IFindGroup.class})
	private String  ydbj00;//运动保健
	@JsonView({IFindGroup.class})
	private String  xwbj00;//穴位保健
	@JsonView({IFindGroup.class})
	private String  xcsfrq;//下次随访日期
	@JsonView({IFindGroup.class})
	private String  zdrq00;//指导日期
	@JsonView({IFindGroup.class})
	private String  ysxm00;//医生姓名

	public String getDf_id() {
		return df_id;
	}
	public void setDf_id(String df_id) {
		this.df_id = df_id;
	}
	public String getIdcardno() {
		return idcardno;
	}
	public void setIdcardno(String idcardno) {
		this.idcardno = idcardno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTelphone() {
		return telphone;
	}
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getF_id() {
		return f_id;
	}
	public void setF_id(String f_id) {
		this.f_id = f_id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAdress_city() {
		return adress_city;
	}
	public void setAdress_city(String adress_city) {
		this.adress_city = adress_city;
	}
	public String getAdress_county() {
		return adress_county;
	}
	public void setAdress_county(String adress_county) {
		this.adress_county = adress_county;
	}
	public String getAdress_rural() {
		return adress_rural;
	}
	public void setAdress_rural(String adress_rural) {
		this.adress_rural = adress_rural;
	}
	public String getAdress_village() {
		return adress_village;
	}
	public void setAdress_village(String adress_village) {
		this.adress_village = adress_village;
	}
	public String getAdrss_hnumber() {
		return adrss_hnumber;
	}
	public void setAdrss_hnumber(String adrss_hnumber) {
		this.adrss_hnumber = adrss_hnumber;
	}
	public String getAdress_pro() {
		return adress_pro;
	}
	public void setAdress_pro(String adress_pro) {
		this.adress_pro = adress_pro;
	}
	public String getJname() {
		return jname;
	}
	public void setJname(String jname) {
		this.jname = jname;
	}
	public String getYyid00() {
		return yyid00;
	}
	public void setYyid00(String yyid00) {
		this.yyid00 = yyid00;
	}
	public String getJktj_ybzkid() {
		return jktj_ybzkid;
	}
	public void setJktj_ybzkid(String jktj_ybzkid) {
		this.jktj_ybzkid = jktj_ybzkid;
	}
	public String getYbzk_tiwen() {
		return ybzk_tiwen;
	}
	public void setYbzk_tiwen(String ybzk_tiwen) {
		this.ybzk_tiwen = ybzk_tiwen;
	}
	public String getYbzk_ml() {
		return ybzk_ml;
	}
	public void setYbzk_ml(String ybzk_ml) {
		this.ybzk_ml = ybzk_ml;
	}
	public String getYbzk_hxpl() {
		return ybzk_hxpl;
	}
	public void setYbzk_hxpl(String ybzk_hxpl) {
		this.ybzk_hxpl = ybzk_hxpl;
	}
	public String getYbzk_zszy() {
		return ybzk_zszy;
	}
	public void setYbzk_zszy(String ybzk_zszy) {
		this.ybzk_zszy = ybzk_zszy;
	}
	public String getYbzk_zssy() {
		return ybzk_zssy;
	}
	public void setYbzk_zssy(String ybzk_zssy) {
		this.ybzk_zssy = ybzk_zssy;
	}
	public String getYbzk_yszy() {
		return ybzk_yszy;
	}
	public void setYbzk_yszy(String ybzk_yszy) {
		this.ybzk_yszy = ybzk_yszy;
	}
	public String getYbzk_yssy() {
		return ybzk_yssy;
	}
	public void setYbzk_yssy(String ybzk_yssy) {
		this.ybzk_yssy = ybzk_yssy;
	}
	public String getYbzk_sg() {
		return ybzk_sg;
	}
	public void setYbzk_sg(String ybzk_sg) {
		this.ybzk_sg = ybzk_sg;
	}
	public String getYbzk_tz() {
		return ybzk_tz;
	}
	public void setYbzk_tz(String ybzk_tz) {
		this.ybzk_tz = ybzk_tz;
	}
	public String getYbzk_yw() {
		return ybzk_yw;
	}
	public void setYbzk_yw(String ybzk_yw) {
		this.ybzk_yw = ybzk_yw;
	}
	public String getYbzk_tzzs() {
		return ybzk_tzzs;
	}
	public void setYbzk_tzzs(String ybzk_tzzs) {
		this.ybzk_tzzs = ybzk_tzzs;
	}
	public String getYbzk_tunwei() {
		return ybzk_tunwei;
	}
	public void setYbzk_tunwei(String ybzk_tunwei) {
		this.ybzk_tunwei = ybzk_tunwei;
	}
	public String getYbzk_ytwbz() {
		return ybzk_ytwbz;
	}
	public void setYbzk_ytwbz(String ybzk_ytwbz) {
		this.ybzk_ytwbz = ybzk_ytwbz;
	}
	public String getYbzk_lnrzgn() {
		return ybzk_lnrzgn;
	}
	public void setYbzk_lnrzgn(String ybzk_lnrzgn) {
		this.ybzk_lnrzgn = ybzk_lnrzgn;
	}
	public String getYbzk_lnzljc() {
		return ybzk_lnzljc;
	}
	public void setYbzk_lnzljc(String ybzk_lnzljc) {
		this.ybzk_lnzljc = ybzk_lnzljc;
	}
	public String getYbzk_lnqgzt() {
		return ybzk_lnqgzt;
	}
	public void setYbzk_lnqgzt(String ybzk_lnqgzt) {
		this.ybzk_lnqgzt = ybzk_lnqgzt;
	}
	public String getYbzk_lnyypf() {
		return ybzk_lnyypf;
	}
	public void setYbzk_lnyypf(String ybzk_lnyypf) {
		this.ybzk_lnyypf = ybzk_lnyypf;
	}
	public String getJktjcs() {
		return jktjcs;
	}
	public void setJktjcs(String jktjcs) {
		this.jktjcs = jktjcs;
	}
	public String getEdate() {
		return edate;
	}
	public void setEdate(String edate) {
		this.edate = edate;
	}
	public String getDoctor() {
		return doctor;
	}
	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}
	public String getTjzzqk() {
		return tjzzqk;
	}
	public void setTjzzqk(String tjzzqk) {
		this.tjzzqk = tjzzqk;
	}
	public String getZz_qt0000() {
		return zz_qt0000;
	}
	public void setZz_qt0000(String zz_qt0000) {
		this.zz_qt0000 = zz_qt0000;
	}
	public String getLnrjkpj() {
		return lnrjkpj;
	}
	public void setLnrjkpj(String lnrjkpj) {
		this.lnrjkpj = lnrjkpj;
	}
	public String getLnrshpj() {
		return lnrshpj;
	}
	public void setLnrshpj(String lnrshpj) {
		this.lnrshpj = lnrshpj;
	}
	public String getGxrq00() {
		return gxrq00;
	}
	public void setGxrq00(String gxrq00) {
		this.gxrq00 = gxrq00;
	}
	public String getGxsj00() {
		return gxsj00;
	}
	public void setGxsj00(String gxsj00) {
		this.gxsj00 = gxsj00;
	}
	public String getGxygbh() {
		return gxygbh;
	}
	public void setGxygbh(String gxygbh) {
		this.gxygbh = gxygbh;
	}
	public String getGxygxm() {
		return gxygxm;
	}
	public void setGxygxm(String gxygxm) {
		this.gxygxm = gxygxm;
	}
	public String getDsrc() {
		return dsrc;
	}
	public void setDsrc(String dsrc) {
		this.dsrc = dsrc;
	}
	public String getJktj_sfhs_id() {
		return jktj_sfhs_id;
	}
	public void setJktj_sfhs_id(String jktj_sfhs_id) {
		this.jktj_sfhs_id = jktj_sfhs_id;
	}
	public String getSfhs_zybl_jtzy() {
		return sfhs_zybl_jtzy;
	}
	public void setSfhs_zybl_jtzy(String sfhs_zybl_jtzy) {
		this.sfhs_zybl_jtzy = sfhs_zybl_jtzy;
	}
	public String getSfhs_zybl_cysj() {
		return sfhs_zybl_cysj;
	}
	public void setSfhs_zybl_cysj(String sfhs_zybl_cysj) {
		this.sfhs_zybl_cysj = sfhs_zybl_cysj;
	}
	public String getSfhs_zybl_hxp() {
		return sfhs_zybl_hxp;
	}
	public void setSfhs_zybl_hxp(String sfhs_zybl_hxp) {
		this.sfhs_zybl_hxp = sfhs_zybl_hxp;
	}
	public String getSfhs_zybl_hxpcs() {
		return sfhs_zybl_hxpcs;
	}
	public void setSfhs_zybl_hxpcs(String sfhs_zybl_hxpcs) {
		this.sfhs_zybl_hxpcs = sfhs_zybl_hxpcs;
	}
	public String getSfhs_zybl_dw() {
		return sfhs_zybl_dw;
	}
	public void setSfhs_zybl_dw(String sfhs_zybl_dw) {
		this.sfhs_zybl_dw = sfhs_zybl_dw;
	}
	public String getSfhs_zybl_dwcs() {
		return sfhs_zybl_dwcs;
	}
	public void setSfhs_zybl_dwcs(String sfhs_zybl_dwcs) {
		this.sfhs_zybl_dwcs = sfhs_zybl_dwcs;
	}
	public String getSfhs_zybl_sx() {
		return sfhs_zybl_sx;
	}
	public void setSfhs_zybl_sx(String sfhs_zybl_sx) {
		this.sfhs_zybl_sx = sfhs_zybl_sx;
	}
	public String getSfhs_zybl_sxcs() {
		return sfhs_zybl_sxcs;
	}
	public void setSfhs_zybl_sxcs(String sfhs_zybl_sxcs) {
		this.sfhs_zybl_sxcs = sfhs_zybl_sxcs;
	}
	public String getShfs_tydl_jcdlsj() {
		return shfs_tydl_jcdlsj;
	}
	public void setShfs_tydl_jcdlsj(String shfs_tydl_jcdlsj) {
		this.shfs_tydl_jcdlsj = shfs_tydl_jcdlsj;
	}
	public String getShsf_ysxg() {
		return shsf_ysxg;
	}
	public void setShsf_ysxg(String shsf_ysxg) {
		this.shsf_ysxg = shsf_ysxg;
	}
	public String getShsf_xyqk_xynl() {
		return shsf_xyqk_xynl;
	}
	public void setShsf_xyqk_xynl(String shsf_xyqk_xynl) {
		this.shsf_xyqk_xynl = shsf_xyqk_xynl;
	}
	public String getShsf_xyqk_jynl() {
		return shsf_xyqk_jynl;
	}
	public void setShsf_xyqk_jynl(String shsf_xyqk_jynl) {
		this.shsf_xyqk_jynl = shsf_xyqk_jynl;
	}
	public String getShfs_yjqk_jjnl() {
		return shfs_yjqk_jjnl;
	}
	public void setShfs_yjqk_jjnl(String shfs_yjqk_jjnl) {
		this.shfs_yjqk_jjnl = shfs_yjqk_jjnl;
	}
	public String getShfs_yjqk_ksyjnl() {
		return shfs_yjqk_ksyjnl;
	}
	public void setShfs_yjqk_ksyjnl(String shfs_yjqk_ksyjnl) {
		this.shfs_yjqk_ksyjnl = shfs_yjqk_ksyjnl;
	}
	public String getShfs_yjqk_sfcjj() {
		return shfs_yjqk_sfcjj;
	}
	public void setShfs_yjqk_sfcjj(String shfs_yjqk_sfcjj) {
		this.shfs_yjqk_sfcjj = shfs_yjqk_sfcjj;
	}
	public String getShfs_yjzl_() {
		return shfs_yjzl_;
	}
	public void setShfs_yjzl_(String shfs_yjzl_) {
		this.shfs_yjzl_ = shfs_yjzl_;
	}
	public String getShfs_yjzl_qt0000() {
		return shfs_yjzl_qt0000;
	}
	public void setShfs_yjzl_qt0000(String shfs_yjzl_qt0000) {
		this.shfs_yjzl_qt0000 = shfs_yjzl_qt0000;
	}
	public String getShfs_zybl_qk() {
		return shfs_zybl_qk;
	}
	public void setShfs_zybl_qk(String shfs_zybl_qk) {
		this.shfs_zybl_qk = shfs_zybl_qk;
	}
	public String getSfhs_zybl_hxpcsnr() {
		return sfhs_zybl_hxpcsnr;
	}
	public void setSfhs_zybl_hxpcsnr(String sfhs_zybl_hxpcsnr) {
		this.sfhs_zybl_hxpcsnr = sfhs_zybl_hxpcsnr;
	}
	public String getSfhs_zybl_dwcsnr() {
		return sfhs_zybl_dwcsnr;
	}
	public void setSfhs_zybl_dwcsnr(String sfhs_zybl_dwcsnr) {
		this.sfhs_zybl_dwcsnr = sfhs_zybl_dwcsnr;
	}
	public String getSfhs_zybl_sxcsnr() {
		return sfhs_zybl_sxcsnr;
	}
	public void setSfhs_zybl_sxcsnr(String sfhs_zybl_sxcsnr) {
		this.sfhs_zybl_sxcsnr = sfhs_zybl_sxcsnr;
	}
	public String getShfs_tydl_dlpl() {
		return shfs_tydl_dlpl;
	}
	public void setShfs_tydl_dlpl(String shfs_tydl_dlpl) {
		this.shfs_tydl_dlpl = shfs_tydl_dlpl;
	}
	public String getShfs_tydl_mcdlsj() {
		return shfs_tydl_mcdlsj;
	}
	public void setShfs_tydl_mcdlsj(String shfs_tydl_mcdlsj) {
		this.shfs_tydl_mcdlsj = shfs_tydl_mcdlsj;
	}
	public String getShfs_tydl_dlfs() {
		return shfs_tydl_dlfs;
	}
	public void setShfs_tydl_dlfs(String shfs_tydl_dlfs) {
		this.shfs_tydl_dlfs = shfs_tydl_dlfs;
	}
	public String getShsf_xyqk_xyzk() {
		return shsf_xyqk_xyzk;
	}
	public void setShsf_xyqk_xyzk(String shsf_xyqk_xyzk) {
		this.shsf_xyqk_xyzk = shsf_xyqk_xyzk;
	}
	public String getShsf_xyqk_rxyl() {
		return shsf_xyqk_rxyl;
	}
	public void setShsf_xyqk_rxyl(String shsf_xyqk_rxyl) {
		this.shsf_xyqk_rxyl = shsf_xyqk_rxyl;
	}
	public String getShfs_yjqk_yjpl() {
		return shfs_yjqk_yjpl;
	}
	public void setShfs_yjqk_yjpl(String shfs_yjqk_yjpl) {
		this.shfs_yjqk_yjpl = shfs_yjqk_yjpl;
	}
	public String getShfs_yjqk_ryjl() {
		return shfs_yjqk_ryjl;
	}
	public void setShfs_yjqk_ryjl(String shfs_yjqk_ryjl) {
		this.shfs_yjqk_ryjl = shfs_yjqk_ryjl;
	}
	public String getShfs_yjqk_sfjj() {
		return shfs_yjqk_sfjj;
	}
	public void setShfs_yjqk_sfjj(String shfs_yjqk_sfjj) {
		this.shfs_yjqk_sfjj = shfs_yjqk_sfjj;
	}
	public String getSfhs_zybl_fc() {
		return sfhs_zybl_fc;
	}
	public void setSfhs_zybl_fc(String sfhs_zybl_fc) {
		this.sfhs_zybl_fc = sfhs_zybl_fc;
	}
	public String getSfhs_zybl_fccs() {
		return sfhs_zybl_fccs;
	}
	public void setSfhs_zybl_fccs(String sfhs_zybl_fccs) {
		this.sfhs_zybl_fccs = sfhs_zybl_fccs;
	}
	public String getSfhs_zybl_qt() {
		return sfhs_zybl_qt;
	}
	public void setSfhs_zybl_qt(String sfhs_zybl_qt) {
		this.sfhs_zybl_qt = sfhs_zybl_qt;
	}
	public String getSfhs_zybl_qtcs() {
		return sfhs_zybl_qtcs;
	}
	public void setSfhs_zybl_qtcs(String sfhs_zybl_qtcs) {
		this.sfhs_zybl_qtcs = sfhs_zybl_qtcs;
	}
	public String getSfhs_zybl_fccsnr() {
		return sfhs_zybl_fccsnr;
	}
	public void setSfhs_zybl_fccsnr(String sfhs_zybl_fccsnr) {
		this.sfhs_zybl_fccsnr = sfhs_zybl_fccsnr;
	}
	public String getSfhs_zybl_qtcsnr() {
		return sfhs_zybl_qtcsnr;
	}
	public void setSfhs_zybl_qtcsnr(String sfhs_zybl_qtcsnr) {
		this.sfhs_zybl_qtcsnr = sfhs_zybl_qtcsnr;
	}
	public String getJktj_zqgnid() {
		return jktj_zqgnid;
	}
	public void setJktj_zqgnid(String jktj_zqgnid) {
		this.jktj_zqgnid = jktj_zqgnid;
	}
	public String getJktj_zqgn_kqkc() {
		return jktj_zqgn_kqkc;
	}
	public void setJktj_zqgn_kqkc(String jktj_zqgn_kqkc) {
		this.jktj_zqgn_kqkc = jktj_zqgn_kqkc;
	}
	public String getJktj_zqgn_kqcl() {
		return jktj_zqgn_kqcl;
	}
	public void setJktj_zqgn_kqcl(String jktj_zqgn_kqcl) {
		this.jktj_zqgn_kqcl = jktj_zqgn_kqcl;
	}
	public String getJktj_zqgn_kqyb() {
		return jktj_zqgn_kqyb;
	}
	public void setJktj_zqgn_kqyb(String jktj_zqgn_kqyb) {
		this.jktj_zqgn_kqyb = jktj_zqgn_kqyb;
	}
	public String getJktj_zqgn_slzy() {
		return jktj_zqgn_slzy;
	}
	public void setJktj_zqgn_slzy(String jktj_zqgn_slzy) {
		this.jktj_zqgn_slzy = jktj_zqgn_slzy;
	}
	public String getJktj_zqgn_slyy() {
		return jktj_zqgn_slyy;
	}
	public void setJktj_zqgn_slyy(String jktj_zqgn_slyy) {
		this.jktj_zqgn_slyy = jktj_zqgn_slyy;
	}
	public String getJktj_zqgn_jzslzy() {
		return jktj_zqgn_jzslzy;
	}
	public void setJktj_zqgn_jzslzy(String jktj_zqgn_jzslzy) {
		this.jktj_zqgn_jzslzy = jktj_zqgn_jzslzy;
	}
	public String getJktj_zqgn_jzslyy() {
		return jktj_zqgn_jzslyy;
	}
	public void setJktj_zqgn_jzslyy(String jktj_zqgn_jzslyy) {
		this.jktj_zqgn_jzslyy = jktj_zqgn_jzslyy;
	}
	public String getJktj_zqgn_tl() {
		return jktj_zqgn_tl;
	}
	public void setJktj_zqgn_tl(String jktj_zqgn_tl) {
		this.jktj_zqgn_tl = jktj_zqgn_tl;
	}
	public String getJktj_zqgn_ydgn() {
		return jktj_zqgn_ydgn;
	}
	public void setJktj_zqgn_ydgn(String jktj_zqgn_ydgn) {
		this.jktj_zqgn_ydgn = jktj_zqgn_ydgn;
	}
	public String getJktj_zqgn_quechi() {
		return jktj_zqgn_quechi;
	}
	public void setJktj_zqgn_quechi(String jktj_zqgn_quechi) {
		this.jktj_zqgn_quechi = jktj_zqgn_quechi;
	}
	public String getJktj_zqgn_quchi0() {
		return jktj_zqgn_quchi0;
	}
	public void setJktj_zqgn_quchi0(String jktj_zqgn_quchi0) {
		this.jktj_zqgn_quchi0 = jktj_zqgn_quchi0;
	}
	public String getJktj_zqgn_yichi0() {
		return jktj_zqgn_yichi0;
	}
	public void setJktj_zqgn_yichi0(String jktj_zqgn_yichi0) {
		this.jktj_zqgn_yichi0 = jktj_zqgn_yichi0;
	}
	public String getJktj_ctid() {
		return jktj_ctid;
	}
	public void setJktj_ctid(String jktj_ctid) {
		this.jktj_ctid = jktj_ctid;
	}
	public String getCt_pf() {
		return ct_pf;
	}
	public void setCt_pf(String ct_pf) {
		this.ct_pf = ct_pf;
	}
	public String getCt_pfqt() {
		return ct_pfqt;
	}
	public void setCt_pfqt(String ct_pfqt) {
		this.ct_pfqt = ct_pfqt;
	}
	public String getCt_gm() {
		return ct_gm;
	}
	public void setCt_gm(String ct_gm) {
		this.ct_gm = ct_gm;
	}
	public String getCt_gmqt() {
		return ct_gmqt;
	}
	public void setCt_gmqt(String ct_gmqt) {
		this.ct_gmqt = ct_gmqt;
	}
	public String getCt_lbj() {
		return ct_lbj;
	}
	public void setCt_lbj(String ct_lbj) {
		this.ct_lbj = ct_lbj;
	}
	public String getCt_lbjqt() {
		return ct_lbjqt;
	}
	public void setCt_lbjqt(String ct_lbjqt) {
		this.ct_lbjqt = ct_lbjqt;
	}
	public String getCt_ftzx() {
		return ct_ftzx;
	}
	public void setCt_ftzx(String ct_ftzx) {
		this.ct_ftzx = ct_ftzx;
	}
	public String getCt_fhxy() {
		return ct_fhxy;
	}
	public void setCt_fhxy(String ct_fhxy) {
		this.ct_fhxy = ct_fhxy;
	}
	public String getCt_fhxyyc() {
		return ct_fhxyyc;
	}
	public void setCt_fhxyyc(String ct_fhxyyc) {
		this.ct_fhxyyc = ct_fhxyyc;
	}
	public String getCt_fly() {
		return ct_fly;
	}
	public void setCt_fly(String ct_fly) {
		this.ct_fly = ct_fly;
	}
	public String getCt_flyqt() {
		return ct_flyqt;
	}
	public void setCt_flyqt(String ct_flyqt) {
		this.ct_flyqt = ct_flyqt;
	}
	public String getCt_xzxl() {
		return ct_xzxl;
	}
	public void setCt_xzxl(String ct_xzxl) {
		this.ct_xzxl = ct_xzxl;
	}
	public String getCt_xzxinl() {
		return ct_xzxinl;
	}
	public void setCt_xzxinl(String ct_xzxinl) {
		this.ct_xzxinl = ct_xzxinl;
	}
	public String getCt_xzzy() {
		return ct_xzzy;
	}
	public void setCt_xzzy(String ct_xzzy) {
		this.ct_xzzy = ct_xzzy;
	}
	public String getCt_xzzyqt() {
		return ct_xzzyqt;
	}
	public void setCt_xzzyqt(String ct_xzzyqt) {
		this.ct_xzzyqt = ct_xzzyqt;
	}
	public String getCt_fbyt() {
		return ct_fbyt;
	}
	public void setCt_fbyt(String ct_fbyt) {
		this.ct_fbyt = ct_fbyt;
	}
	public String getCt_fbytqt() {
		return ct_fbytqt;
	}
	public void setCt_fbytqt(String ct_fbytqt) {
		this.ct_fbytqt = ct_fbytqt;
	}
	public String getCt_fbbk() {
		return ct_fbbk;
	}
	public void setCt_fbbk(String ct_fbbk) {
		this.ct_fbbk = ct_fbbk;
	}
	public String getCt_fbbkqt() {
		return ct_fbbkqt;
	}
	public void setCt_fbbkqt(String ct_fbbkqt) {
		this.ct_fbbkqt = ct_fbbkqt;
	}
	public String getCt_fbgd() {
		return ct_fbgd;
	}
	public void setCt_fbgd(String ct_fbgd) {
		this.ct_fbgd = ct_fbgd;
	}
	public String getCt_fbgdqt() {
		return ct_fbgdqt;
	}
	public void setCt_fbgdqt(String ct_fbgdqt) {
		this.ct_fbgdqt = ct_fbgdqt;
	}
	public String getCt_fbpd() {
		return ct_fbpd;
	}
	public void setCt_fbpd(String ct_fbpd) {
		this.ct_fbpd = ct_fbpd;
	}
	public String getCt_fbpdqt() {
		return ct_fbpdqt;
	}
	public void setCt_fbpdqt(String ct_fbpdqt) {
		this.ct_fbpdqt = ct_fbpdqt;
	}
	public String getCt_fbydzy() {
		return ct_fbydzy;
	}
	public void setCt_fbydzy(String ct_fbydzy) {
		this.ct_fbydzy = ct_fbydzy;
	}
	public String getCt_fbydzyqt() {
		return ct_fbydzyqt;
	}
	public void setCt_fbydzyqt(String ct_fbydzyqt) {
		this.ct_fbydzyqt = ct_fbydzyqt;
	}
	public String getCt_xzsz() {
		return ct_xzsz;
	}
	public void setCt_xzsz(String ct_xzsz) {
		this.ct_xzsz = ct_xzsz;
	}
	public String getCt_zbdmbd() {
		return ct_zbdmbd;
	}
	public void setCt_zbdmbd(String ct_zbdmbd) {
		this.ct_zbdmbd = ct_zbdmbd;
	}
	public String getCt_gmzz() {
		return ct_gmzz;
	}
	public void setCt_gmzz(String ct_gmzz) {
		this.ct_gmzz = ct_gmzz;
	}
	public String getCt_gmzzqt() {
		return ct_gmzzqt;
	}
	public void setCt_gmzzqt(String ct_gmzzqt) {
		this.ct_gmzzqt = ct_gmzzqt;
	}
	public String getCt_rxqk() {
		return ct_rxqk;
	}
	public void setCt_rxqk(String ct_rxqk) {
		this.ct_rxqk = ct_rxqk;
	}
	public String getCt_rxqt() {
		return ct_rxqt;
	}
	public void setCt_rxqt(String ct_rxqt) {
		this.ct_rxqt = ct_rxqt;
	}
	public String getCt_fkwy() {
		return ct_fkwy;
	}
	public void setCt_fkwy(String ct_fkwy) {
		this.ct_fkwy = ct_fkwy;
	}
	public String getCt_fkwyqt() {
		return ct_fkwyqt;
	}
	public void setCt_fkwyqt(String ct_fkwyqt) {
		this.ct_fkwyqt = ct_fkwyqt;
	}
	public String getCt_fkyd() {
		return ct_fkyd;
	}
	public void setCt_fkyd(String ct_fkyd) {
		this.ct_fkyd = ct_fkyd;
	}
	public String getCt_fkydqt() {
		return ct_fkydqt;
	}
	public void setCt_fkydqt(String ct_fkydqt) {
		this.ct_fkydqt = ct_fkydqt;
	}
	public String getCt_fkgj() {
		return ct_fkgj;
	}
	public void setCt_fkgj(String ct_fkgj) {
		this.ct_fkgj = ct_fkgj;
	}
	public String getCt_fkgjqt() {
		return ct_fkgjqt;
	}
	public void setCt_fkgjqt(String ct_fkgjqt) {
		this.ct_fkgjqt = ct_fkgjqt;
	}
	public String getCt_fkgt() {
		return ct_fkgt;
	}
	public void setCt_fkgt(String ct_fkgt) {
		this.ct_fkgt = ct_fkgt;
	}
	public String getCt_fkgtqt() {
		return ct_fkgtqt;
	}
	public void setCt_fkgtqt(String ct_fkgtqt) {
		this.ct_fkgtqt = ct_fkgtqt;
	}
	public String getCt_fkfj() {
		return ct_fkfj;
	}
	public void setCt_fkfj(String ct_fkfj) {
		this.ct_fkfj = ct_fkfj;
	}
	public String getCt_fkfjqt() {
		return ct_fkfjqt;
	}
	public void setCt_fkfjqt(String ct_fkfjqt) {
		this.ct_fkfjqt = ct_fkfjqt;
	}
	public String getCt_qt() {
		return ct_qt;
	}
	public void setCt_qt(String ct_qt) {
		this.ct_qt = ct_qt;
	}
	public String getJktj_fzjcid() {
		return jktj_fzjcid;
	}
	public void setJktj_fzjcid(String jktj_fzjcid) {
		this.jktj_fzjcid = jktj_fzjcid;
	}
	public String getFzjc_kfxt() {
		return fzjc_kfxt;
	}
	public void setFzjc_kfxt(String fzjc_kfxt) {
		this.fzjc_kfxt = fzjc_kfxt;
	}
	public String getFzjc_kfxthk() {
		return fzjc_kfxthk;
	}
	public void setFzjc_kfxthk(String fzjc_kfxthk) {
		this.fzjc_kfxthk = fzjc_kfxthk;
	}
	public String getFzjc_xcghdb() {
		return fzjc_xcghdb;
	}
	public void setFzjc_xcghdb(String fzjc_xcghdb) {
		this.fzjc_xcghdb = fzjc_xcghdb;
	}
	public String getFzjc_xcgqt() {
		return fzjc_xcgqt;
	}
	public void setFzjc_xcgqt(String fzjc_xcgqt) {
		this.fzjc_xcgqt = fzjc_xcgqt;
	}
	public String getFzjc_ncgnt() {
		return fzjc_ncgnt;
	}
	public void setFzjc_ncgnt(String fzjc_ncgnt) {
		this.fzjc_ncgnt = fzjc_ncgnt;
	}
	public String getFzjc_ncgqt() {
		return fzjc_ncgqt;
	}
	public void setFzjc_ncgqt(String fzjc_ncgqt) {
		this.fzjc_ncgqt = fzjc_ncgqt;
	}
	public String getFzjc_nwlbdb() {
		return fzjc_nwlbdb;
	}
	public void setFzjc_nwlbdb(String fzjc_nwlbdb) {
		this.fzjc_nwlbdb = fzjc_nwlbdb;
	}
	public String getFzjc_dbqx() {
		return fzjc_dbqx;
	}
	public void setFzjc_dbqx(String fzjc_dbqx) {
		this.fzjc_dbqx = fzjc_dbqx;
	}
	public String getFzjc_ggnxqb() {
		return fzjc_ggnxqb;
	}
	public void setFzjc_ggnxqb(String fzjc_ggnxqb) {
		this.fzjc_ggnxqb = fzjc_ggnxqb;
	}
	public String getFzjc_ggnxqc() {
		return fzjc_ggnxqc;
	}
	public void setFzjc_ggnxqc(String fzjc_ggnxqc) {
		this.fzjc_ggnxqc = fzjc_ggnxqc;
	}
	public String getFzjc_ggnzdh() {
		return fzjc_ggnzdh;
	}
	public void setFzjc_ggnzdh(String fzjc_ggnzdh) {
		this.fzjc_ggnzdh = fzjc_ggnzdh;
	}
	public String getFzjc_ggnjhd() {
		return fzjc_ggnjhd;
	}
	public void setFzjc_ggnjhd(String fzjc_ggnjhd) {
		this.fzjc_ggnjhd = fzjc_ggnjhd;
	}
	public String getFzjc_sgnqjg() {
		return fzjc_sgnqjg;
	}
	public void setFzjc_sgnqjg(String fzjc_sgnqjg) {
		this.fzjc_sgnqjg = fzjc_sgnqjg;
	}
	public String getFzjc_sgnnsd() {
		return fzjc_sgnnsd;
	}
	public void setFzjc_sgnnsd(String fzjc_sgnnsd) {
		this.fzjc_sgnnsd = fzjc_sgnnsd;
	}
	public String getFzjc_sgnjnd() {
		return fzjc_sgnjnd;
	}
	public void setFzjc_sgnjnd(String fzjc_sgnjnd) {
		this.fzjc_sgnjnd = fzjc_sgnjnd;
	}
	public String getFzjc_sgnnnd() {
		return fzjc_sgnnnd;
	}
	public void setFzjc_sgnnnd(String fzjc_sgnnnd) {
		this.fzjc_sgnnnd = fzjc_sgnnnd;
	}
	public String getFzjc_xzzdgc() {
		return fzjc_xzzdgc;
	}
	public void setFzjc_xzzdgc(String fzjc_xzzdgc) {
		this.fzjc_xzzdgc = fzjc_xzzdgc;
	}
	public String getFzjc_xzgysz() {
		return fzjc_xzgysz;
	}
	public void setFzjc_xzgysz(String fzjc_xzgysz) {
		this.fzjc_xzgysz = fzjc_xzgysz;
	}
	public String getFzjc_xzdmdz() {
		return fzjc_xzdmdz;
	}
	public void setFzjc_xzdmdz(String fzjc_xzdmdz) {
		this.fzjc_xzdmdz = fzjc_xzdmdz;
	}
	public String getFzjc_xzgmdz() {
		return fzjc_xzgmdz;
	}
	public void setFzjc_xzgmdz(String fzjc_xzgmdz) {
		this.fzjc_xzgmdz = fzjc_xzgmdz;
	}
	public String getFzjc_thxhdb() {
		return fzjc_thxhdb;
	}
	public void setFzjc_thxhdb(String fzjc_thxhdb) {
		this.fzjc_thxhdb = fzjc_thxhdb;
	}
	public String getFzjc_yxgyky() {
		return fzjc_yxgyky;
	}
	public void setFzjc_yxgyky(String fzjc_yxgyky) {
		this.fzjc_yxgyky = fzjc_yxgyky;
	}
	public String getFzjc_yd() {
		return fzjc_yd;
	}
	public void setFzjc_yd(String fzjc_yd) {
		this.fzjc_yd = fzjc_yd;
	}
	public String getFzjc_ydqt() {
		return fzjc_ydqt;
	}
	public void setFzjc_ydqt(String fzjc_ydqt) {
		this.fzjc_ydqt = fzjc_ydqt;
	}
	public String getFzjc_xdt() {
		return fzjc_xdt;
	}
	public void setFzjc_xdt(String fzjc_xdt) {
		this.fzjc_xdt = fzjc_xdt;
	}
	public String getFzjc_xdtqt() {
		return fzjc_xdtqt;
	}
	public void setFzjc_xdtqt(String fzjc_xdtqt) {
		this.fzjc_xdtqt = fzjc_xdtqt;
	}
	public String getFzjc_xbxxp() {
		return fzjc_xbxxp;
	}
	public void setFzjc_xbxxp(String fzjc_xbxxp) {
		this.fzjc_xbxxp = fzjc_xbxxp;
	}
	public String getFzjc_xbxxqt() {
		return fzjc_xbxxqt;
	}
	public void setFzjc_xbxxqt(String fzjc_xbxxqt) {
		this.fzjc_xbxxqt = fzjc_xbxxqt;
	}
	public String getFzjc_bc() {
		return fzjc_bc;
	}
	public void setFzjc_bc(String fzjc_bc) {
		this.fzjc_bc = fzjc_bc;
	}
	public String getFzjc_bcqt() {
		return fzjc_bcqt;
	}
	public void setFzjc_bcqt(String fzjc_bcqt) {
		this.fzjc_bcqt = fzjc_bcqt;
	}
	public String getFzjc_gjtp() {
		return fzjc_gjtp;
	}
	public void setFzjc_gjtp(String fzjc_gjtp) {
		this.fzjc_gjtp = fzjc_gjtp;
	}
	public String getFzjc_gjtpqt() {
		return fzjc_gjtpqt;
	}
	public void setFzjc_gjtpqt(String fzjc_gjtpqt) {
		this.fzjc_gjtpqt = fzjc_gjtpqt;
	}
	public String getFzjc_qt0000() {
		return fzjc_qt0000;
	}
	public void setFzjc_qt0000(String fzjc_qt0000) {
		this.fzjc_qt0000 = fzjc_qt0000;
	}
	public String getNcgndb() {
		return ncgndb;
	}
	public void setNcgndb(String ncgndb) {
		this.ncgndb = ncgndb;
	}
	public String getNcgnt() {
		return ncgnt;
	}
	public void setNcgnt(String ncgnt) {
		this.ncgnt = ncgnt;
	}
	public String getNcgntt() {
		return ncgntt;
	}
	public void setNcgntt(String ncgntt) {
		this.ncgntt = ncgntt;
	}
	public String getNcgnqx() {
		return ncgnqx;
	}
	public void setNcgnqx(String ncgnqx) {
		this.ncgnqx = ncgnqx;
	}
	public String getFzjc_ncgbxb() {
		return fzjc_ncgbxb;
	}
	public void setFzjc_ncgbxb(String fzjc_ncgbxb) {
		this.fzjc_ncgbxb = fzjc_ncgbxb;
	}
	public String getFzjc_ncgdhs() {
		return fzjc_ncgdhs;
	}
	public void setFzjc_ncgdhs(String fzjc_ncgdhs) {
		this.fzjc_ncgdhs = fzjc_ncgdhs;
	}
	public String getFzjc_ncgyxsy() {
		return fzjc_ncgyxsy;
	}
	public void setFzjc_ncgyxsy(String fzjc_ncgyxsy) {
		this.fzjc_ncgyxsy = fzjc_ncgyxsy;
	}
	public String getFzjc_ggnag() {
		return fzjc_ggnag;
	}
	public void setFzjc_ggnag(String fzjc_ggnag) {
		this.fzjc_ggnag = fzjc_ggnag;
	}
	public String getFzjc_ggnudbil() {
		return fzjc_ggnudbil;
	}
	public void setFzjc_ggnudbil(String fzjc_ggnudbil) {
		this.fzjc_ggnudbil = fzjc_ggnudbil;
	}
	public String getFzjc_ncgalp() {
		return fzjc_ncgalp;
	}
	public void setFzjc_ncgalp(String fzjc_ncgalp) {
		this.fzjc_ncgalp = fzjc_ncgalp;
	}
	public String getFzjc_ggnggt() {
		return fzjc_ggnggt;
	}
	public void setFzjc_ggnggt(String fzjc_ggnggt) {
		this.fzjc_ggnggt = fzjc_ggnggt;
	}
	public String getFzjc_sgnns() {
		return fzjc_sgnns;
	}
	public void setFzjc_sgnns(String fzjc_sgnns) {
		this.fzjc_sgnns = fzjc_sgnns;
	}
	public String getFzjc_xdtfrist() {
		return fzjc_xdtfrist;
	}
	public void setFzjc_xdtfrist(String fzjc_xdtfrist) {
		this.fzjc_xdtfrist = fzjc_xdtfrist;
	}
	public String getFzjc_xdtsecond() {
		return fzjc_xdtsecond;
	}
	public void setFzjc_xdtsecond(String fzjc_xdtsecond) {
		this.fzjc_xdtsecond = fzjc_xdtsecond;
	}
	public String getFzjc_xdtthree() {
		return fzjc_xdtthree;
	}
	public void setFzjc_xdtthree(String fzjc_xdtthree) {
		this.fzjc_xdtthree = fzjc_xdtthree;
	}
	public String getJktj_zyjkwtid() {
		return jktj_zyjkwtid;
	}
	public void setJktj_zyjkwtid(String jktj_zyjkwtid) {
		this.jktj_zyjkwtid = jktj_zyjkwtid;
	}
	public String getZyjkwt_nxg() {
		return zyjkwt_nxg;
	}
	public void setZyjkwt_nxg(String zyjkwt_nxg) {
		this.zyjkwt_nxg = zyjkwt_nxg;
	}
	public String getZyjkwt_nxgqt() {
		return zyjkwt_nxgqt;
	}
	public void setZyjkwt_nxgqt(String zyjkwt_nxgqt) {
		this.zyjkwt_nxgqt = zyjkwt_nxgqt;
	}
	public String getZyjkwt_sz() {
		return zyjkwt_sz;
	}
	public void setZyjkwt_sz(String zyjkwt_sz) {
		this.zyjkwt_sz = zyjkwt_sz;
	}
	public String getZyjkwt_szqt() {
		return zyjkwt_szqt;
	}
	public void setZyjkwt_szqt(String zyjkwt_szqt) {
		this.zyjkwt_szqt = zyjkwt_szqt;
	}
	public String getZyjkwt_xzwfx() {
		return zyjkwt_xzwfx;
	}
	public void setZyjkwt_xzwfx(String zyjkwt_xzwfx) {
		this.zyjkwt_xzwfx = zyjkwt_xzwfx;
	}
	public String getZyjkwt_xzqt() {
		return zyjkwt_xzqt;
	}
	public void setZyjkwt_xzqt(String zyjkwt_xzqt) {
		this.zyjkwt_xzqt = zyjkwt_xzqt;
	}
	public String getZyjkwt_xgwfx() {
		return zyjkwt_xgwfx;
	}
	public void setZyjkwt_xgwfx(String zyjkwt_xgwfx) {
		this.zyjkwt_xgwfx = zyjkwt_xgwfx;
	}
	public String getZyjkwt_xgqt() {
		return zyjkwt_xgqt;
	}
	public void setZyjkwt_xgqt(String zyjkwt_xgqt) {
		this.zyjkwt_xgqt = zyjkwt_xgqt;
	}
	public String getZyjkwt_ybwfx() {
		return zyjkwt_ybwfx;
	}
	public void setZyjkwt_ybwfx(String zyjkwt_ybwfx) {
		this.zyjkwt_ybwfx = zyjkwt_ybwfx;
	}
	public String getZyjkwt_ybqt() {
		return zyjkwt_ybqt;
	}
	public void setZyjkwt_ybqt(String zyjkwt_ybqt) {
		this.zyjkwt_ybqt = zyjkwt_ybqt;
	}
	public String getZyjkwt_sjxtjb() {
		return zyjkwt_sjxtjb;
	}
	public void setZyjkwt_sjxtjb(String zyjkwt_sjxtjb) {
		this.zyjkwt_sjxtjb = zyjkwt_sjxtjb;
	}
	public String getZyjkwt_sjxtqt() {
		return zyjkwt_sjxtqt;
	}
	public void setZyjkwt_sjxtqt(String zyjkwt_sjxtqt) {
		this.zyjkwt_sjxtqt = zyjkwt_sjxtqt;
	}
	public String getZyjkwt_qtxtjb() {
		return zyjkwt_qtxtjb;
	}
	public void setZyjkwt_qtxtjb(String zyjkwt_qtxtjb) {
		this.zyjkwt_qtxtjb = zyjkwt_qtxtjb;
	}
	public String getZyjkwt_qtxtqt() {
		return zyjkwt_qtxtqt;
	}
	public void setZyjkwt_qtxtqt(String zyjkwt_qtxtqt) {
		this.zyjkwt_qtxtqt = zyjkwt_qtxtqt;
	}
	public String getZytzbs_phz() {
		return zytzbs_phz;
	}
	public void setZytzbs_phz(String zytzbs_phz) {
		this.zytzbs_phz = zytzbs_phz;
	}
	public String getZytzbs_qxz() {
		return zytzbs_qxz;
	}
	public void setZytzbs_qxz(String zytzbs_qxz) {
		this.zytzbs_qxz = zytzbs_qxz;
	}
	public String getZytzbs_yangxz() {
		return zytzbs_yangxz;
	}
	public void setZytzbs_yangxz(String zytzbs_yangxz) {
		this.zytzbs_yangxz = zytzbs_yangxz;
	}
	public String getZytzbs_yinxz() {
		return zytzbs_yinxz;
	}
	public void setZytzbs_yinxz(String zytzbs_yinxz) {
		this.zytzbs_yinxz = zytzbs_yinxz;
	}
	public String getZytzbs_tsz() {
		return zytzbs_tsz;
	}
	public void setZytzbs_tsz(String zytzbs_tsz) {
		this.zytzbs_tsz = zytzbs_tsz;
	}
	public String getZytzbs_srz() {
		return zytzbs_srz;
	}
	public void setZytzbs_srz(String zytzbs_srz) {
		this.zytzbs_srz = zytzbs_srz;
	}
	public String getZytzbs_xyz() {
		return zytzbs_xyz;
	}
	public void setZytzbs_xyz(String zytzbs_xyz) {
		this.zytzbs_xyz = zytzbs_xyz;
	}
	public String getZytzbs_qyz() {
		return zytzbs_qyz;
	}
	public void setZytzbs_qyz(String zytzbs_qyz) {
		this.zytzbs_qyz = zytzbs_qyz;
	}
	public String getZytzbs_tbz() {
		return zytzbs_tbz;
	}
	public void setZytzbs_tbz(String zytzbs_tbz) {
		this.zytzbs_tbz = zytzbs_tbz;
	}
	public String getJktj_jkpjid() {
		return jktj_jkpjid;
	}
	public void setJktj_jkpjid(String jktj_jkpjid) {
		this.jktj_jkpjid = jktj_jkpjid;
	}
	public String getJkpj_tjsfyc() {
		return jkpj_tjsfyc;
	}
	public void setJkpj_tjsfyc(String jkpj_tjsfyc) {
		this.jkpj_tjsfyc = jkpj_tjsfyc;
	}
	public String getJkpj_tjyc1() {
		return jkpj_tjyc1;
	}
	public void setJkpj_tjyc1(String jkpj_tjyc1) {
		this.jkpj_tjyc1 = jkpj_tjyc1;
	}
	public String getJkpj_tjyc2() {
		return jkpj_tjyc2;
	}
	public void setJkpj_tjyc2(String jkpj_tjyc2) {
		this.jkpj_tjyc2 = jkpj_tjyc2;
	}
	public String getJkpj_tjyc3() {
		return jkpj_tjyc3;
	}
	public void setJkpj_tjyc3(String jkpj_tjyc3) {
		this.jkpj_tjyc3 = jkpj_tjyc3;
	}
	public String getJkpj_tjyc4() {
		return jkpj_tjyc4;
	}
	public void setJkpj_tjyc4(String jkpj_tjyc4) {
		this.jkpj_tjyc4 = jkpj_tjyc4;
	}
	public String getJkzd() {
		return jkzd;
	}
	public void setJkzd(String jkzd) {
		this.jkzd = jkzd;
	}
	public String getWxyskz() {
		return wxyskz;
	}
	public void setWxyskz(String wxyskz) {
		this.wxyskz = wxyskz;
	}
	public String getWxyskz_jtzmb() {
		return wxyskz_jtzmb;
	}
	public void setWxyskz_jtzmb(String wxyskz_jtzmb) {
		this.wxyskz_jtzmb = wxyskz_jtzmb;
	}
	public String getWxyskz_ymjz() {
		return wxyskz_ymjz;
	}
	public void setWxyskz_ymjz(String wxyskz_ymjz) {
		this.wxyskz_ymjz = wxyskz_ymjz;
	}
	public String getWxyskz_qt() {
		return wxyskz_qt;
	}
	public void setWxyskz_qt(String wxyskz_qt) {
		this.wxyskz_qt = wxyskz_qt;
	}
	public String getBsxxjg() {
		return bsxxjg;
	}
	public void setBsxxjg(String bsxxjg) {
		this.bsxxjg = bsxxjg;
	}
	public String getQxzscore() {
		return qxzscore;
	}
	public void setQxzscore(String qxzscore) {
		this.qxzscore = qxzscore;
	}
	public String getYx0score() {
		return yx0score;
	}
	public void setYx0score(String yx0score) {
		this.yx0score = yx0score;
	}
	public String getYxzscore() {
		return yxzscore;
	}
	public void setYxzscore(String yxzscore) {
		this.yxzscore = yxzscore;
	}
	public String getTszscore() {
		return tszscore;
	}
	public void setTszscore(String tszscore) {
		this.tszscore = tszscore;
	}
	public String getSrzscore() {
		return srzscore;
	}
	public void setSrzscore(String srzscore) {
		this.srzscore = srzscore;
	}
	public String getXyzscore() {
		return xyzscore;
	}
	public void setXyzscore(String xyzscore) {
		this.xyzscore = xyzscore;
	}
	public String getQyzscore() {
		return qyzscore;
	}
	public void setQyzscore(String qyzscore) {
		this.qyzscore = qyzscore;
	}
	public String getTbzscore() {
		return tbzscore;
	}
	public void setTbzscore(String tbzscore) {
		this.tbzscore = tbzscore;
	}
	public String getPhzscore() {
		return phzscore;
	}
	public void setPhzscore(String phzscore) {
		this.phzscore = phzscore;
	}
	public String getTzbstj() {
		return tzbstj;
	}
	public void setTzbstj(String tzbstj) {
		this.tzbstj = tzbstj;
	}
	public String getBjzdtj() {
		return bjzdtj;
	}
	public void setBjzdtj(String bjzdtj) {
		this.bjzdtj = bjzdtj;
	}
	public String getZdid00() {
		return zdid00;
	}
	public void setZdid00(String zdid00) {
		this.zdid00 = zdid00;
	}
	public String getCjrq00() {
		return cjrq00;
	}
	public void setCjrq00(String cjrq00) {
		this.cjrq00 = cjrq00;
	}
	public String getZhbjrq() {
		return zhbjrq;
	}
	public void setZhbjrq(String zhbjrq) {
		this.zhbjrq = zhbjrq;
	}
	public String getQxzqt0() {
		return qxzqt0;
	}
	public void setQxzqt0(String qxzqt0) {
		this.qxzqt0 = qxzqt0;
	}
	public String getYx0qt0() {
		return yx0qt0;
	}
	public void setYx0qt0(String yx0qt0) {
		this.yx0qt0 = yx0qt0;
	}
	public String getYxzqt0() {
		return yxzqt0;
	}
	public void setYxzqt0(String yxzqt0) {
		this.yxzqt0 = yxzqt0;
	}
	public String getTszqt0() {
		return tszqt0;
	}
	public void setTszqt0(String tszqt0) {
		this.tszqt0 = tszqt0;
	}
	public String getSrzqt0() {
		return srzqt0;
	}
	public void setSrzqt0(String srzqt0) {
		this.srzqt0 = srzqt0;
	}
	public String getXyzqt0() {
		return xyzqt0;
	}
	public void setXyzqt0(String xyzqt0) {
		this.xyzqt0 = xyzqt0;
	}
	public String getQyzqt0() {
		return qyzqt0;
	}
	public void setQyzqt0(String qyzqt0) {
		this.qyzqt0 = qyzqt0;
	}
	public String getTbzqt0() {
		return tbzqt0;
	}
	public void setTbzqt0(String tbzqt0) {
		this.tbzqt0 = tbzqt0;
	}
	public String getPhzqt0() {
		return phzqt0;
	}
	public void setPhzqt0(String phzqt0) {
		this.phzqt0 = phzqt0;
	}
	public String getXgpg00() {
		return xgpg00;
	}
	public void setXgpg00(String xgpg00) {
		this.xgpg00 = xgpg00;
	}
	public String getMyddc0() {
		return myddc0;
	}
	public void setMyddc0(String myddc0) {
		this.myddc0 = myddc0;
	}
	public String getTzlx00() {
		return tzlx00;
	}
	public void setTzlx00(String tzlx00) {
		this.tzlx00 = tzlx00;
	}
	public String getTzbm00() {
		return tzbm00;
	}
	public void setTzbm00(String tzbm00) {
		this.tzbm00 = tzbm00;
	}
	public String getQzts00() {
		return qzts00;
	}
	public void setQzts00(String qzts00) {
		this.qzts00 = qzts00;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZybszdid() {
		return zybszdid;
	}
	public void setZybszdid(String zybszdid) {
		this.zybszdid = zybszdid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getYsty00() {
		return ysty00;
	}
	public void setYsty00(String ysty00) {
		this.ysty00 = ysty00;
	}
	public String getQjts00() {
		return qjts00;
	}
	public void setQjts00(String qjts00) {
		this.qjts00 = qjts00;
	}
	public String getYdbj00() {
		return ydbj00;
	}
	public void setYdbj00(String ydbj00) {
		this.ydbj00 = ydbj00;
	}
	public String getXwbj00() {
		return xwbj00;
	}
	public void setXwbj00(String xwbj00) {
		this.xwbj00 = xwbj00;
	}
	public String getXcsfrq() {
		return xcsfrq;
	}
	public void setXcsfrq(String xcsfrq) {
		this.xcsfrq = xcsfrq;
	}
	public String getZdrq00() {
		return zdrq00;
	}
	public void setZdrq00(String zdrq00) {
		this.zdrq00 = zdrq00;
	}
	public String getYsxm00() {
		return ysxm00;
	}
	public void setYsxm00(String ysxm00) {
		this.ysxm00 = ysxm00;
	}



	public static interface IFindGroup{}

}
