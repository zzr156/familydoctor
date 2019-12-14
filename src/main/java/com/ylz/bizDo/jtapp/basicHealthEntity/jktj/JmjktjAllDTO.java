package com.ylz.bizDo.jtapp.basicHealthEntity.jktj;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.ArrayList;
import java.util.List;

public class JmjktjAllDTO {
	/**
	 * 默认构造函数
	 */
	public JmjktjAllDTO() {
	}

	private String df_id; // 居民档案号
	private String name; // 居民姓名
	private String telphone; // 联系电话
	private String adress_city; // 常住地址：市（地区）
	private String adress_county; // 常住地址：县（区）
	private String adress_rural; // 常住地址：乡（镇、街道办事处）
	private String adress_village; // 常住地址：村（街、路、弄）
	private String adrss_hnumber; // 常住地址：门牌号码
	private String adress_pro; // 常住地址：省（自治区、直辖市）
	private String birthday; // 出生日期
	private java.math.BigDecimal sex; // 性别
	private String ref_ci_id; // 社区号
	private String f_id; // 家庭档案号
	private String jktj_ctid; // 健康体检--查体ID
	private String yyid00; // 机构ID
	private String jktjcs; // 健康体检次数

	private String ct_pf; // 查体--皮肤（1-正常,2-潮红,3-苍白,4-发绀,5-黄染,6-色素沉着,7-其他）

	private String ct_pfqt; // 查体--皮肤其他

	private String ct_gm; // 查体--巩膜（1-正常,2-黄染,3-充血,4-其他）

	private String ct_gmqt; // 查体--巩膜其他

	private String ct_lbj; // 查体--淋巴结（1-未触及,2-锁骨上,3-腋窝,4-其他）

	private String ct_lbjqt; // 查体--淋巴结其他

	private String ct_ftzx; // 查体--肺--桶状胸(1-是,0-否)

	private String ct_fhxy; // 查体--肺--呼吸音(1-正常,2-异常)

	private String ct_fhxyyc; // 查体--肺--呼吸音异常

	private String ct_fly; // 查体--肺--罗音(1-无,2-干啰音,3-湿啰音,4-其他)

	private String ct_flyqt; // 查体--肺--罗音其他

	private String ct_xzxl; // 查体--心脏--心率

	private String ct_xzxinl; // 查体--心脏--心律(1-齐,2-不齐,3-绝对不齐)

	private String ct_xzzy; // 查体--心脏--杂音(1-有,0-无)

	private String ct_xzzyqt; // 查体--心脏--杂音其他

	private String ct_fbyt; // 查体--腹部--压痛(1-有,0-无)

	private String ct_fbytqt; // 查体--腹部--压痛其他

	private String ct_fbbk; // 查体--腹部--包块(1-有,0-无)

	private String ct_fbbkqt; // 查体--腹部--包块其他

	private String ct_fbgd; // 查体--腹部--肝大(1-有,0-无)

	private String ct_fbgdqt; // 查体--腹部--肝大其他

	private String ct_fbpd; // 查体--腹部--脾大(1-有,0-无)

	private String ct_fbpdqt; // 查体--腹部--脾大其他

	private String ct_fbydzy; // 查体--腹部--移动性浊音(1-有,0-无)

	private String ct_fbydzyqt; // 查体--腹部--移动性浊音其他

	private String ct_xzsz; // 查体--下肢水肿(1-无,2-单侧,3-双侧不对称,4-双侧对称)

	private String ct_zbdmbd; // 查体--足背动脉搏动(1-未触及,2-触及双侧对称,3-触及左侧弱或消失,4-触及右侧弱或消失)

	private String ct_gmzz; // 查体--肛门指诊(1-未见异常,2-触痛,3-包块,4-前列腺异常,5-其他)

	private String ct_gmzzqt; // 查体--肛门指诊其他
	private String ct_rx_rfqc; // 查体--乳腺-乳房切除

	private String ct_rxqt; // 查体--乳腺其他

	private String ct_fkwy; // 查体--妇科--外阴(1-未见异常,2-异常)

	private String ct_fkwyqt; // 查体--妇科--外阴其他

	private String ct_fkyd; // 查体--妇科--阴道(1-未见异常,2-异常)

	private String ct_fkydqt; // 查体--妇科--阴道其他

	private String ct_fkgj; // 查体--妇科--宫颈(1-未见异常,2-异常)

	private String ct_fkgjqt; // 查体--妇科--宫颈其他

	private String ct_fkgt; // 查体--妇科--宫体(1-未见异常,2-异常)

	private String ct_fkgtqt; // 查体--妇科--宫体其他

	private String ct_fkfj; // 查体--妇科--附件(1-未见异常,2-异常)

	private String ct_fkfjqt; // 查体--妇科--附件其他

	private String ct_qt; // 查体--其他
	private String ct_rx_ycmr; // 查体--乳腺--异常泌乳
	private String ct_rx_rxbk; // 查体--乳腺--乳腺包块
	private String jktj_fzjcid; // 健康体检--辅助检查ID(SEQ_JKJC_FZJCID)

	private String fzjc_kfxt; // 辅助检查--空腹血糖

	private String fzjc_kfxthk; // 辅助检查--空腹血糖mg

	private String fzjc_xcghdb; // 辅助检查--血常规--血红蛋白

	private String fzjc_xcgbxb; // 辅助检查--血常规--白细胞

	private String fzjc_xcgxxb; // 辅助检查--血常规--血小板

	private String fzjc_xcgqt; // 辅助检查--血常规--其他

	private String fzjc_ncgndb; // 辅助检查--尿常规-- 尿蛋白(1.-,2.+,3.++,4.+++,5.++++,6.+-)

	private String fzjc_ncgnt; // 辅助检查--尿常规--尿糖(1.-,2.+,3.++,4.+++,5.++++,6.+-)

	private String fzjc_ncgntt; // 辅助检查--尿常规-- 尿酮体(1.-,2.+,3.++,4.+++,5.++++,6.+-)

	private String fzjc_ncgnqx; // 辅助检查--尿常规--尿潜血(1.-,2.+,3.++,4.+++,5.++++,6.+-)

	private String fzjc_ncgqt; // 辅助检查--尿常规--其他

	private String fzjc_nwlbdb; // 辅助检查--尿微量白蛋白

	private String fzjc_dbqx; // 辅助检查--大便潜血(1-阴性,2-阳性)

	private String fzjc_ggnxqb; // 辅助检查--肝功能--血清谷丙转氨酶

	private String fzjc_ggnxqc; // 辅助检查--肝功能--血清谷草转氨酶

	private String fzjc_ggnbdb; // 辅助检查--肝功能--白蛋白

	private String fzjc_ggnzdh; // 辅助检查--肝功能--总胆红素

	private String fzjc_ggnjhd; // 辅助检查--肝功能--结合胆红素

	private String fzjc_sgnqjg; // 辅助检查--肾功能--血清肌酐

	private String fzjc_sgnnsd; // 辅助检查--肾功能--血尿素氮

	private String fzjc_sgnjnd; // 辅助检查--肾功能--血钾浓度

	private String fzjc_sgnnnd; // 辅助检查--肾功能--血钠浓度

	private String fzjc_xzzdgc; // 辅助检查--血脂--总胆固醇

	private String fzjc_xzgysz; // 辅助检查--血脂--甘油三酯

	private String fzjc_xzdmdz; // 辅助检查--血脂--血清低密度脂蛋白胆固醇

	private String fzjc_xzgmdz; // 辅助检查--血脂--血清高密度脂蛋白胆固醇

	private String fzjc_thxhdb; // 辅助检查--糖化血红蛋白

	private String fzjc_yxgyky; // 辅助检查--乙型肝炎表面抗原(1-阴性,2-阳性)

	private String fzjc_yd; // 辅助检查--眼底(1-正常,2-异常)

	private String fzjc_ydqt; // 辅助检查--眼底异常

	private String fzjc_xdt; // 辅助检查--心电图(1-正常,2-异常)

	private String fzjc_xdtqt; // 辅助检查--心电图异常

	private String fzjc_xbxxp; // 辅助检查--胸部X线片(1-正常,2-异常)

	private String fzjc_xbxxqt; // 辅助检查--胸部X线片异常

	private String fzjc_bc; // 辅助检查--B超(1-正常,2-异常)

	private String fzjc_bcqt; // 辅助检查--B超其他

	private String fzjc_gjtp; // 辅助检查--宫颈涂片(1-正常,2-异常)

	private String fzjc_gjtpqt; // 辅助检查--宫颈涂片异常

	private String fzjc_qt0000; // 辅助检查--其他
	private String jktj_jkpjid; // 健康体检--健康评价ID

	private String jkpj_tjsfyc; // 健康评价--体检是否异常(1-无异常,2-有异常)

	private String jkpj_tjyc1; // 健康评价--体检异常1

	private String jkpj_tjyc2; // 健康评价--体检异常2

	private String jkpj_tjyc3; // 健康评价--体检异常3

	private String jkpj_tjyc4; // 健康评价--体检异常4
	private String jktj_jkzdid; // 健康体检--健康指导ID
	private String jkzd_dqsf; // 健康指导--定期随访
	private String jkzd_mxbgl; // 健康指导--纳入慢性病患者健康管理
	private String jkzd_jyfz; // 健康指导--建议复诊
	private String jkzd_jyzz; // 健康指导--建议转诊
	private String jktj_wxysid; // 健康体检--危险因素ID
	private String wxyskz_jy; // 危险因素控制--戒烟
	private String wxyskz_jkyj; // 危险因素控制--健康饮酒
	private String wxyskz_ys; // 危险因素控制--饮食
	private String wxyskz_dl; // 危险因素控制--锻炼
	private String wxyskz_jtz; // 危险因素控制--减体重

	private String wxyskz_jtzmb; // 危险因素控制--减体重目标
	private String wxyskz_jyymjz; // 危险因素控制--建议疫苗接种

	private String wxyskz_ymjzqt; // 危险因素控制--疫苗接种其他(流感疫苗-流感疫苗,肺炎疫苗-肺炎疫苗)

	private String wxyskz_qt; // 危险因素控制--其他
	private String jktj_ybzkid; // 健康体检--一般状况ID(SEQ_JKTJ_YBZKID)
	private String ybzk_tiwen; // 健康体检--一般状况--体温
	private String ybzk_ml; // 健康体检--一般状况--脉率
	private String ybzk_hxpl; // 健康体检--一般状况--呼吸频率
	private String ybzk_zszy; // 健康体检--一般状况--左舒张压
	private String ybzk_zssy; // 健康体检--一般状况--左收缩压
	private String ybzk_yszy; // 健康体检--一般状况--右舒张压
	private String ybzk_yssy; // 健康体检--一般状况--右收缩压
	private String ybzk_sg; // 健康体检--一般状况--身高
	private String ybzk_tz; // 健康体检--一般状况--体重
	private String ybzk_yw; // 健康体检--一般状况--腰围
	private String ybzk_tzzs; // 健康体检--一般状况--体质指数
	private String ybzk_tunwei; // 健康体检--一般状况--臀围
	private String ybzk_ytwbz; // 健康体检--一般状况--腰臀围比值

	private String ybzk_lnrzgn; // 健康体检--一般状况--老年人认知功能(0-请选择,1-粗筛阴性,2-粗筛阳性)

	private String ybzk_lnzljc; // 健康体检--一般状况--老年人智力状态检查

	private String ybzk_lnqgzt; // 健康体检--一般状况--老年人情感状态(0-请选择,1-粗筛阴性,2-粗筛阳性)

	private String ybzk_lnyypf; // 健康体检--一般状况--老年人抑郁评分检查

	private String edate; // 检查日期

	private String doctor; // 责任医生
	private String jktj_zqgnid; // 健康体检--脏器功能ID
	private String jktj_zqgn_kqkc; // 健康体检--脏器功能--口腔-口唇

	private String jktj_zqgn_kqcl; // 健康体检--脏器功能--口腔-齿列(1-正常,2-缺齿,3-龋齿,4-义齿(假牙))
	private String jktj_zqgn_kqyb; // 健康体检--脏器功能--口腔-咽部
	private String jktj_zqgn_slzy; // 健康体检--脏器功能--视力左眼
	private String jktj_zqgn_slyy; // 健康体检--脏器功能--视力右眼
	private String jktj_zqgn_jzslzy; // 健康体检--脏器功能--矫正视力左眼
	private String jktj_zqgn_jzslyy; // 健康体检--脏器功能--矫正视力右眼
	private String jktj_zqgn_tl; // 健康体检--脏器功能--听力
	private String jktj_zqgn_ydgn; // 健康体检--脏器功能--运动功能
	private String jktj_zyjkwtid; // 健康体检--主要健康问题ID
	private String zyjkwt_nxgwfx; // 主要健康问题--脑血管疾病--未发现
	private String zyjkwt_nxgzz; // 主要健康问题--脑血管疾病--缺血性卒中
	private String zyjkwt_nxgncx; // 主要健康问题--脑血管疾病--脑出血
	private String zyjkwt_nxgzwm; // 主要健康问题--脑血管疾病--蛛网膜下腔出血
	private String zyjkwt_nxgnqx; // 主要健康问题--脑血管疾病--短暂性脑缺血发作

	private String zyjkwt_nxgqt; // 主要健康问题--脑血管疾病--其他
	private String zyjkwt_szwfx; // 主要健康问题--肾脏疾病--未发现
	private String zyjkwt_sztnsb; // 主要健康问题--肾脏疾病--糖尿病肾病
	private String zyjkwt_szsgn; // 主要健康问题--肾脏疾病--肾功能衰竭
	private String zyjkwt_szjxsy; // 主要健康问题--肾脏疾病--急性肾炎
	private String zyjkwt_szmxsy; // 主要健康问题--肾脏疾病--慢性肾炎

	private String zyjkwt_szqt; // 主要健康问题--肾脏疾病--其他

	private String zyjkwt_xzwfx; // 主要健康问题--心脏疾病(1-未发现,2-心肌梗死,3-心绞痛,4-冠状动脉血运重建,5-充血性心力衰竭,6-心前区疼痛,7-其他)
	private String zyjkwt_xzxjgs; // 主要健康问题--心脏疾病--心肌梗塞
	private String zyjkwt_xzxjt; // 主要健康问题--心脏疾病--心绞痛
	private String zyjkwt_xzgzdm; // 主要健康问题--心脏疾病--冠状动脉血运重建
	private String zyjkwt_xzxlsj; // 主要健康问题--心脏疾病--心力衰竭
	private String zyjkwt_xzxqqt; // 主要健康问题--心脏疾病--心前区疼痛

	private String zyjkwt_xzqt; // 主要健康问题--心脏疾病--其他

	private String zyjkwt_xgwfx; // 主要健康问题--血管疾病(1-未发现,2-夹层动脉瘤,3-动脉闭塞性疾病,4-其他)
	private String zyjkwt_xgdml; // 主要健康问题--血管疾病--夹层动脉瘤
	private String zyjkwt_xgdmjb; // 主要健康问题--血管疾病--动脉闭塞性疾病

	private String zyjkwt_xgqt; // 主要健康问题--血管疾病--其他

	private String zyjkwt_ybwfx; // 主要健康问题--眼部疾病(1-未发现,2-视网膜出血或渗出,3-视乳头水肿,4-白内障,5-其他)
	private String zyjkwt_ybswm; // 主要健康问题--眼部疾病--视网膜出血或渗出
	private String zyjkwt_ybsrsz; // 主要健康问题--眼部疾病--视乳头水肿
	private String zyjkwt_ybbnz; // 主要健康问题--眼部疾病--白内障

	private String zyjkwt_ybqt; // 主要健康问题--眼部疾病--其他

	private String zyjkwt_sjxtjb; // 主要健康问题--神经系统疾病(1-未发现,2-是)

	private String zyjkwt_sjxtqt; // 主要健康问题--神经系统疾病其他

	private String zyjkwt_qtxtjb; // 主要健康问题--其他系统疾病(1-未发现,2-是)

	private String zyjkwt_qtxtqt; // 主要健康问题--其他系统疾病其他
	private String jktj_zytzbsid; // 健康体检--中医体质辨识ID
	private String zytzbs_phz; // 健康体检--中医体质辨识--平和质
	private String zytzbs_qxz; // 健康体检--中医体质辨识--气虚质
	private String zytzbs_yangxz; // 健康体检--中医体质辨识--阳虚质
	private String zytzbs_yinxz; // 健康体检--中医体质辨识--阴虚质
	private String zytzbs_tsz; // 健康体检--中医体质辨识--痰湿质
	private String zytzbs_srz; // 健康体检--中医体质辨识--湿热质
	private String zytzbs_xyz; // 健康体检--中医体质辨识--血瘀质
	private String zytzbs_qyz; // 健康体检--中医体质辨识--气郁质
	private String zytzbs_tbz; // 健康体检--中医体质辨识--特秉质
	private String zz_zzid00; // 症状ID(SEQ_ZZ_ZZID00)
	private String zz_wzz; // 症状--无症状
	private String zz_tt; // 症状--头痛
	private String zz_ty; // 症状--头晕
	private String zz_xj; // 症状--心悸
	private String zz_xm; // 症状--胸闷
	private String zz_xt; // 症状--胸痛
	private String zz_mxks; // 症状--慢性咳嗽
	private String zz_kt; // 症状--咳痰
	private String zz_hxkn; // 症状--呼吸困难
	private String zz_dy; // 症状--多饮
	private String zz_dn; // 症状--多尿
	private String zz_tzxj; // 症状--体重下降
	private String zz_fl; // 症状--乏力
	private String zz_gjzt; // 症状--关节肿痛
	private String zz_slmh; // 症状--视力模糊
	private String zz_sjmm; // 症状--手脚麻木
	private String zz_nj; // 症状--尿急
	private String zz_nt; // 症状--尿痛
	private String zz_bm; // 症状--便秘
	private String zz_fx; // 症状--腹泻
	private String zz_exot; // 症状--恶心呕吐
	private String zz_yh; // 症状--眼花
	private String zz_em; // 症状--耳鸣
	private String zz_rfzt; // 症状--乳房胀痛

	private String zz_qt0000; // 症状--其他
	private String hrf_id; // 编号
	private java.math.BigDecimal smoke; // 吸烟
	private String s_bgdate; // 吸烟开始时间
	private java.math.BigDecimal s_dose; // 吸烟量(最近1 个月内平均每天的吸烟量，计量单位为支)
	private java.math.BigDecimal hdrink_id; // 饮酒
	private String d_bgdate; // 饮酒开始时间
	private java.math.BigDecimal d_dose; // 饮酒量
	private java.math.BigDecimal meddep; // 药物依赖MD
	private java.math.BigDecimal dwellcond; // 居住条件
	private String rcheckdate; // 复核日期
	private String past; // 既往史
	private String vaccination; // 接种史
	private java.math.BigDecimal fgxy; // 父高血压
	private java.math.BigDecimal fxzb; // 父心脏病
	private java.math.BigDecimal fnxg; // 父脑血管病
	private java.math.BigDecimal ftnb; // 父糖尿病
	private java.math.BigDecimal ffjh; // 父肺结核
	private java.math.BigDecimal fjsjb; // 父精神疾病
	private java.math.BigDecimal fbnz; // 父白内瘴
	private java.math.BigDecimal fxtel; // 父先天耳聋
	private java.math.BigDecimal mgxy; // 母高血压
	private java.math.BigDecimal mxzb; // 母心脏病
	private java.math.BigDecimal mnxg; // 母脑血管病
	private java.math.BigDecimal mtnb; // 母糖尿病
	private java.math.BigDecimal mfjh; // 母肺结核
	private java.math.BigDecimal mjsjb; // 母精神疾病
	private java.math.BigDecimal mbnz; // 母白内瘴
	private java.math.BigDecimal mxtel; // 母先天耳聋
	private java.math.BigDecimal stype; // 体育锻炼--运动方式(锻炼方式)
	private java.math.BigDecimal stimes; // 体育锻炼--运动次数(锻炼频率)
	private java.math.BigDecimal smeasure; // 体育锻炼--运动量(每次锻炼时间)
	private java.math.BigDecimal habit; // 饮食习惯
	private String mfood; // 主要食物
	private java.math.BigDecimal fruit; // 水果食用量
	private String s_beveryday; // 开始每天吸烟年龄（岁）
	private String tobaccotype; // 吸食烟草种类代码
	private String stopsmoke; // 停止吸烟时长（d）(本人停止吸烟的时间长度，计量单位为d)
	private String quitsmoke; // 戒烟方法类别代码
	private String secondhsmoke; // 接触二手烟代码(标识一周内，一天内接触二手烟超过15分钟的代码)
	private String secondhsday; // 接触二手烟天数（d）(标识一周内，一天内接触二手烟超过15分钟的天数，计量单位为d)
	private String passivesplace; // 被动吸烟场所类别代码
	private java.math.BigDecimal d_type; // 标识饮酒种类的代码
	private java.math.BigDecimal d_mark; // 表示是否饮酒(戒酒)
	private String diettype; // 标识饮食种类的代码
	private String dietfrequency; // 饮食频率（次/天）(标识食用特定种类食品的频率，计量单位为次/天)
	private String walkcycling; // 步行或骑自行车累计时长（min）(一天内步行或骑自行车活动的累计时长，计量单位为min)
	private String staticlong; // 日静态行为时长（min）(通常一天内，累计坐着、靠着或躺着的时间长度（包括坐着工作、学习、阅读、看电视、用电脑、做手工活、休息等所有静态行为的时间，但不包括睡觉时间），计量单位为min)
	private String daysleep; // 中午及其他时间睡眠时长（min）(通常一天内，中午及其他时间睡眠的时间长度，计量单位为min)
	private String nightsleep; // 晚上睡眠时长（min）(通常一天内，晚上睡眠的时间长度，计量单位为min)
	private String fzsxfb; // 父慢性阻塞性肺病
	private String fexzl; // 父恶性肿瘤
	private String fqt000; // 父其他
	private String mzsxfb; // 母慢性阻塞性肺病
	private String mexzl; // 母恶性肿瘤
	private String mqt000; // 母其他
	private String xmgxy; // 兄妹高血压
	private String xmtnb; // 兄妹糖尿病
	private String xmgxb; // 兄妹冠心病
	private String xmzsxfb; // 兄妹慢性阻塞性肺疾病
	private String xmexzl; // 兄妹恶性肿瘤
	private String xmnzz; // 兄妹脑卒中
	private String xmzxjsb; // 兄妹重性精神疾病
	private String xmjhb; // 兄妹结核病
	private String xmgy; // 兄妹肝炎
	private String xmxtjx; // 兄妹先天畸形
	private String xmqt00; // 兄妹其他
	private String zngxy; // 子女高血压
	private String zntnb; // 子女糖尿病
	private String zngxb; // 子女冠心病
	private String znzsxfb; // 子女慢性阻塞性肺疾病
	private String znexzl; // 子女恶性肿瘤
	private String znnzz; // 子女脑卒中
	private String znzxjsb; // 子女重性精神疾病
	private String znjhb; // 子女结核病
	private String zngy; // 子女肝炎
	private String znxtjx; // 子女先天畸形
	private String znqt00; // 子女其他
	private String zybl_jtzy; // 职业暴露--具体职业
	private String zybl_cysj; // 职业暴露--从业时间
	private String zybl_hxp; // 职业暴露--化学品
	private String zybl_hxpcs; // 职业暴露--化学品--防护措施
	private String zybl_dw; // 职业暴露--毒物
	private String zybl_dwcs; // 职业暴露--毒物--防护措施
	private String zybl_sx; // 职业暴露--射线
	private String zybl_sxcs; // 职业暴露--射线--防护措施
	private String tydl_jcdlsj; // 体育锻炼--坚持锻炼时间
	private String ysxg_hsjh; // 饮食习惯--荤素均衡
	private String ysxg_hswz; // 饮食习惯--荤食为主
	private String ysxg_sswz; // 饮食习惯--素食为主
	private String ysxg_syan; // 饮食习惯--嗜盐
	private String ysxg_syou; // 饮食习惯--嗜油
	private String ysxg_stang; // 饮食习惯--嗜糖
	private String xyqk_xynl; // 吸烟情况--吸烟年龄
	private String xyqk_jynl; // 吸烟情况--戒烟年龄
	private String yjqk_jjnl; // 饮酒情况--戒酒年龄
	private String yjqk_ksyjnl; // 饮酒情况--开始饮酒年龄
	private String yjqk_sfcjj; // 饮酒情况--近一年内是否曾醉酒
	private String yjzl_bj; // 饮酒情况--饮酒种类--白酒
	private String yjzl_pj; // 饮酒情况--饮酒种类--啤酒
	private String yjzl_hongj; // 饮酒情况--饮酒种类--红酒
	private String yjzl_huangj; // 饮酒情况--饮酒种类--黄酒
	private String yjzl_qt0000; // 饮酒情况--饮酒种类--其他
	private String zybl_qk; // 职业暴露--情况（是否）

	private String addrzh;// 组合地址
	private String address;// 完整地址

	@JsonView({IFindJktj_MxGroup.class})
	private List<T_elderlyinhospital_ylkDTO> t_elderlyinhospital=new ArrayList<T_elderlyinhospital_ylkDTO>();//住院史
	@JsonView({IFindJktj_MxGroup.class})
	private List<Jktj_jtbcsDTO> jktj_jtbcs=new ArrayList<Jktj_jtbcsDTO>();//家庭病床史
	@JsonView({IFindJktj_MxGroup.class})
	private List<T_mxjb_sf_yyqkDTO> t_mxjb_sf_yyqk=new ArrayList<T_mxjb_sf_yyqkDTO>();//用药情况
	@JsonView({IFindJktj_MxGroup.class})
	private List<Jktj_fmyghDTO> jktj_fmygh=new ArrayList<Jktj_fmyghDTO>();//非免疫规划情况

	private String edateBegin;// 检查日期 始
	private String edateEnd;// 检查日期 止

	private String istsrq;// 是否特殊人群(0--否，1--是)
	private String gxrq00; // 更新日期
	private String gxsj00; // 更新时间
	private String gxygbh; // 更新员工编号
	private String gxygxm; // 更新员工姓名


	private String tjzzqk;// 症状(以；号分隔:1-无症状,2-头痛,3-头晕,4-心悸,5-胸闷,6-胸痛,7-慢性咳嗽,8-咳痰,9-呼吸困难,10-多饮,11-多尿,12-体重下降,13-乏力,14-关节肿痛,15-视力模糊,16-手脚麻木,17-尿急,18-尿痛,19-便秘,20-腹泻,21-恶心呕吐,22-眼花,23-耳鸣,24-乳房胀痛,25-其他)
	private String lnrjkpj;// 老人健康状态自我评估
	private String lnrshpj;// 老年人生活自理能力自我评估

	private String jkzd;// 健康指导(1-定期随访,2-纳入慢性病患者健康管理,3-建议复查,4-建议转诊)

	private String wxyskz;// 危险因素控制(1-戒烟,2-健康饮酒,3-饮食,4-锻炼5-减体重,6-建议接种疫苗,7-其他)

	private String zyjkwt_nxg;//主要健康问题--脑血管疾病(1-未发现,2-缺血性卒中,3-脑出血,4-蛛网膜下腔出血,5-短暂性脑缺血发作,6-其他)

	private String zyjkwt_sz;//主要健康问题--肾脏疾病(1-未发现,2-糖尿病肾病,3-肾功能衰竭,4-急性肾炎,5-慢性肾炎,6-其他)

	private String jktj_zqgn_quechi;// 缺齿(以；号分隔)

	private String jktj_zqgn_quchi0;// 龋齿(以；号分隔)

	private String jktj_zqgn_yichi0;// 义齿(以；号分隔)

	private String ct_rxqk;// 乳腺情况(1-未见异常,2-乳房切除,3-异常泌乳,4-乳腺包块,5-其他)

	////
	private String jktj_sfhs_id; // 编号

	private String sfhs_zybl_jtzy; // 职业暴露--具体职业

	private String sfhs_zybl_cysj; // 职业暴露--从业时间（年）

	private String sfhs_zybl_hxp; // 职业暴露--化学品

	private String sfhs_zybl_hxpcs; // 职业暴露--化学品--防护措施（1--有，0--无）

	private String sfhs_zybl_dw; // 职业暴露--毒物

	private String sfhs_zybl_dwcs; // 职业暴露--毒物--防护措施（1--有，0--无）

	private String sfhs_zybl_sx; // 职业暴露--射线

	private String sfhs_zybl_sxcs; // 职业暴露--射线--防护措施（1--有，0--无）
	private String shfs_tydl_jcdlsj; // 体育锻炼--坚持锻炼时间

	private String shsf_ysxg; // 饮食习惯（1--荤素均衡，2--荤食为主，3--素食为主，4--嗜盐，5--嗜油，6--嗜糖）
	private String shsf_xyqk_xynl; // 吸烟情况--吸烟年龄
	private String shsf_xyqk_jynl; // 吸烟情况--戒烟年龄

	private String shfs_yjqk_jjnl; // 饮酒情况--戒酒年龄

	private String shfs_yjqk_ksyjnl; // 饮酒情况--开始饮酒年龄

	private String shfs_yjqk_sfcjj; // 饮酒情况--近一年内是否曾醉酒(1-是，0-否)

	private String shfs_yjzl_; // 饮酒情况--饮酒种类（1--白酒，2--啤酒，3--红酒，4--黄酒，5--其他）

	private String shfs_yjzl_qt0000; // 饮酒情况--饮酒种类--其他

	private String shfs_zybl_qk; // 职业暴露--情况（1-是，0-否）

	private String sfhs_zybl_hxpcsnr; // 职业暴露--化学品--防护措施内容

	private String sfhs_zybl_dwcsnr; // 职业暴露--毒物--防护措施内容

	private String sfhs_zybl_sxcsnr; // 职业暴露--射线--防护措施内容
	private String shfs_tydl_dlpl; // 锻炼频率
	private String shfs_tydl_mcdlsj; // 每次锻炼时间（分钟）
	private String shfs_tydl_dlfs; // 锻炼方式
	private String shsf_xyqk_xyzk; // 吸烟情况--吸烟状况
	private String shsf_xyqk_rxyl; // 吸烟情况--日吸烟量
	private String shfs_yjqk_yjpl; // 饮酒情况--饮酒频率
	private String shfs_yjqk_ryjl; // 饮酒情况--日饮酒量

	private String shfs_yjqk_sfjj; // 饮酒情况--是否戒酒(1-未戒酒,2-已戒酒)

	private String sfhs_zybl_fc;// 职业暴露--粉尘

	private String sfhs_zybl_fccs;// 职业暴露--粉尘--防护措施（1--有，0--无）

	private String sfhs_zybl_qt;// 职业暴露--其他

	private String sfhs_zybl_qtcs;// 职业暴露--其他--防护措施（1--有，0--无）

	private String sfhs_zybl_fccsnr;// 职业暴露--粉尘--防护措施内容

	private String sfhs_zybl_qtcsnr;// 职业暴露--其他--防护措施内容

	private String fmyghid; // 非免疫规划预防接种编号
	private String ymmc; // 疫苗名称
	private String jzrq; // 接种日期
	private String jzyy; // 接种医院
	private String fzjc_ncgbxb; // 尿常规 尿白细胞
	private String fzjc_ncgdhs; // 尿常规 尿胆红素
	private String fzjc_ncgyxsy; // 尿常规 尿亚硝酸盐
	private String fzjc_ggnag; // 肝功能 白蛋白/球蛋白比值(*)
	private String fzjc_ggnudbil; // 肝功能 间接胆红素(*)
	private String fzjc_ncgalp;// 肝功能 碱性磷酸酶(ALP)
	private String fzjc_ggnggt;// 肝功能 r-谷氨酰转移酶
	private String fzjc_sgnns;// 肾功能 尿酸
	private String fzjc_xdtfrist;
	private String fzjc_xdtsecond;
	private String fzjc_xdtthree;

	private String wxyskz_ymjz; // 危险因素控制--疫苗接种
	private String jkzd00; // 健康指导
	private String jjcf00; // 健教处方
	private String jtbcsid; // 家庭病床史编号
	private String jcrq; // 建床日期
	private String ccrq; // 撤床日期
	private String bjyy; // 病床原因
	private String yljgmc; // 医院名称
	private String bcbah; // 病床病案号
	private String ywbh_id; // 药物编号
	private String ywmc; // 药物名称
	private String ywyf; // 用法
	private String ywyl; // 用量
	private String yysj; // 用药时间
	private String fyycx; // 服药依从性
	private String ref_sf_id; // 随访编号

	private String sf_id0000; // 随访编号
	private String ref_id; // 慢性疾病患者编号
	private String mxjbbz; // 慢性疾病编号

	private String sfmb; // 是否模板

	private String ih_id; // 住院记录ID
	private String eld_id; // 老年人保健ID
	private String indate; // 入院日期
	private String outdate; // 出院日期
	private String hospital; // 医院
	private String departments; // 科别
	private String diagnosis; // 诊断
	private String surgery; // 手术
	private String result; // 结果
	private String inhreason; // 入院原因
	private String zybah; // 住院病案号
	private String tzbs_id; // 中医体质辨识ID
	private String bsxxjg; // 辨识选项结果

	private String qxzscore; // 气虚质分数(分数>=11是，9或者10倾向是)

	private String yx0score; // 阳虚质分数

	private String yxzscore; // 阴虚质分数

	private String tszscore; // 痰湿质分数

	private String srzscore; // 湿热质分数

	private String xyzscore; // 血瘀质分数

	private String qyzscore; // 气郁质分数

	private String tbzscore; // 特禀质分数

	private String phzscore; // 平和质分数

	private String tzbstj; // 体质辨识统计 体质辨识统计（有以下9项，通过";"拼接。气虚质：1-是，2-倾向是；阳虚质：3-是，4-倾向是；阴虚质：5-是，6-倾向是；痰湿质7-是，8-倾向是；湿热质：9-是，10-倾向是；血瘀质：11-是，12-倾向是；气郁质：13-是，14-倾向是；特禀质：15-是，16-倾向是；平和质：17-是，18-基本是；）
	private String bjzdtj; // 中医药保健指导统计
	private String zdid00;
	private java.sql.Timestamp cjrq00; // 创建日期
	private java.sql.Timestamp zhbjrq; // 最后编辑日期
	private String qxzqt0;
	private String yx0qt0;
	private String yxzqt0;
	private String tszqt0;
	private String srzqt0;
	private String xyzqt0;
	private String qyzqt0;
	private String tbzqt0;
	private String phzqt0;

	private String xgpg00; // 效果评估(1-完全掌握,2-熟练掌握 ,3-基本掌握,4-困难掌握 )

	private String myddc0; // 满意度调查(1-很满意,2-比较满意 ,3-一般清楚,4-不满意 )

	/*private  java.lang.String lnr000; //老年人
	private  java.lang.String gxy000; //高血压
	private  java.lang.String tnb000; //糖尿病
*/
	private  String qzts00;//情志调摄
	private  String ysty00;//饮食调养
	private  String qjts00;//情志调摄
	private  String ydbj00;//运动保健
	private  String xwbj00;//穴位保健
	private  String tzmc00;//体质名称

	private  String tzbsmc; //体质辨识名称
	@JsonView({IFindJktj_MxGroup.class})
	private Jktj_ybzk_ylkDTO jktj_ybzk=new Jktj_ybzk_ylkDTO();//健康体检一般状况信息对象
	@JsonView({IFindJktj_MxGroup.class})
	@JsonInclude(Include.NON_NULL)
	private jktj_shfsDTO jktj_shfs;//健康体检生活方式信息对象
	@JsonView({IFindJktj_MxGroup.class})
	@JsonInclude(Include.NON_NULL)
	private jktj_zqgnDTO jktj_zqgn;//健康体检脏器功能信息对象
	@JsonView({IFindJktj_MxGroup.class})
	@JsonInclude(Include.NON_NULL)
	private jktj_ctDTO jktj_ct;//居民健康体检之查体信息对象
	@JsonView({IFindJktj_MxGroup.class})
	@JsonInclude(Include.NON_NULL)
	private jktj_fzjcDTO jktj_fzjc;//健康体检之辅助检查对象
	@JsonView({IFindJktj_MxGroup.class})
	@JsonInclude(Include.NON_NULL)
	private jktj_zyjkwtDTO jktj_zyjkwt;//健康体检之主要健康问题对象
	@JsonView({IFindJktj_MxGroup.class})
	@JsonInclude(Include.NON_NULL)
	private jktj_jkpjDTO jktj_jkpj;//健康体检之健康评价对象
	@JsonView({IFindJktj_MxGroup.class})
	@JsonInclude(Include.NON_NULL)
	private T_elderlyzytzbs_ylzDTO t_elderlyzytzbs;//
	@JsonView({IFindJktj_MxGroup.class})
	@JsonInclude(Include.NON_NULL)
	private T_elderly_zybszdDTO t_elderlyzybszd;
	//private T_elderlyzytzbs_ylzDTO t_elderlyzytzbs=new  T_elderlyzytzbs_ylzDTO();//老年人中医体质辨识信息对象


	/**
	 * 居民档案号
	 * @return
	 */
	public String getDf_id() {
		return df_id;
	}

	/**
	 * 居民档案号
	 * @param df_id
	 */
	public void setDf_id(String df_id) {
		this.df_id = df_id;
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

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public java.math.BigDecimal getSex() {
		return sex;
	}

	public void setSex(java.math.BigDecimal sex) {
		this.sex = sex;
	}

	public String getRef_ci_id() {
		return ref_ci_id;
	}

	public void setRef_ci_id(String ref_ci_id) {
		this.ref_ci_id = ref_ci_id;
	}

	public String getF_id() {
		return f_id;
	}

	public void setF_id(String f_id) {
		this.f_id = f_id;
	}

	public String getJktj_ctid() {
		return jktj_ctid;
	}

	public void setJktj_ctid(String jktj_ctid) {
		this.jktj_ctid = jktj_ctid;
	}

	public String getYyid00() {
		return yyid00;
	}

	public void setYyid00(String yyid00) {
		this.yyid00 = yyid00;
	}

	/**
	 * 健康体检--体检次数
	 * @return
	 */
	public String getJktjcs() {
		return jktjcs;
	}

	/**
	 * 健康体检--体检次数
	 * @param jktjcs
	 */
	public void setJktjcs(String jktjcs) {
		this.jktjcs = jktjcs;
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

	public String getCt_rx_rfqc() {
		return ct_rx_rfqc;
	}

	public void setCt_rx_rfqc(String ct_rx_rfqc) {
		this.ct_rx_rfqc = ct_rx_rfqc;
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

	public String getCt_rx_ycmr() {
		return ct_rx_ycmr;
	}

	public void setCt_rx_ycmr(String ct_rx_ycmr) {
		this.ct_rx_ycmr = ct_rx_ycmr;
	}

	public String getCt_rx_rxbk() {
		return ct_rx_rxbk;
	}

	public void setCt_rx_rxbk(String ct_rx_rxbk) {
		this.ct_rx_rxbk = ct_rx_rxbk;
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

	public String getFzjc_xcgbxb() {
		return fzjc_xcgbxb;
	}

	public void setFzjc_xcgbxb(String fzjc_xcgbxb) {
		this.fzjc_xcgbxb = fzjc_xcgbxb;
	}

	public String getFzjc_xcgxxb() {
		return fzjc_xcgxxb;
	}

	public void setFzjc_xcgxxb(String fzjc_xcgxxb) {
		this.fzjc_xcgxxb = fzjc_xcgxxb;
	}

	public String getFzjc_xcgqt() {
		return fzjc_xcgqt;
	}

	public void setFzjc_xcgqt(String fzjc_xcgqt) {
		this.fzjc_xcgqt = fzjc_xcgqt;
	}

	public String getFzjc_ncgndb() {
		return fzjc_ncgndb;
	}

	public void setFzjc_ncgndb(String fzjc_ncgndb) {
		this.fzjc_ncgndb = fzjc_ncgndb;
	}

	public String getFzjc_ncgnt() {
		return fzjc_ncgnt;
	}

	public void setFzjc_ncgnt(String fzjc_ncgnt) {
		this.fzjc_ncgnt = fzjc_ncgnt;
	}

	public String getFzjc_ncgntt() {
		return fzjc_ncgntt;
	}

	public void setFzjc_ncgntt(String fzjc_ncgntt) {
		this.fzjc_ncgntt = fzjc_ncgntt;
	}

	public String getFzjc_ncgnqx() {
		return fzjc_ncgnqx;
	}

	public void setFzjc_ncgnqx(String fzjc_ncgnqx) {
		this.fzjc_ncgnqx = fzjc_ncgnqx;
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

	public String getFzjc_ggnbdb() {
		return fzjc_ggnbdb;
	}

	public void setFzjc_ggnbdb(String fzjc_ggnbdb) {
		this.fzjc_ggnbdb = fzjc_ggnbdb;
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

	public String getJktj_jkzdid() {
		return jktj_jkzdid;
	}

	public void setJktj_jkzdid(String jktj_jkzdid) {
		this.jktj_jkzdid = jktj_jkzdid;
	}

	public String getJkzd_dqsf() {
		return jkzd_dqsf;
	}

	public void setJkzd_dqsf(String jkzd_dqsf) {
		this.jkzd_dqsf = jkzd_dqsf;
	}

	public String getJkzd_mxbgl() {
		return jkzd_mxbgl;
	}

	public void setJkzd_mxbgl(String jkzd_mxbgl) {
		this.jkzd_mxbgl = jkzd_mxbgl;
	}

	public String getJkzd_jyfz() {
		return jkzd_jyfz;
	}

	public void setJkzd_jyfz(String jkzd_jyfz) {
		this.jkzd_jyfz = jkzd_jyfz;
	}

	public String getJkzd_jyzz() {
		return jkzd_jyzz;
	}

	public void setJkzd_jyzz(String jkzd_jyzz) {
		this.jkzd_jyzz = jkzd_jyzz;
	}

	public String getJktj_wxysid() {
		return jktj_wxysid;
	}

	public void setJktj_wxysid(String jktj_wxysid) {
		this.jktj_wxysid = jktj_wxysid;
	}

	public String getWxyskz_jy() {
		return wxyskz_jy;
	}

	public void setWxyskz_jy(String wxyskz_jy) {
		this.wxyskz_jy = wxyskz_jy;
	}

	public String getWxyskz_jkyj() {
		return wxyskz_jkyj;
	}

	public void setWxyskz_jkyj(String wxyskz_jkyj) {
		this.wxyskz_jkyj = wxyskz_jkyj;
	}

	public String getWxyskz_ys() {
		return wxyskz_ys;
	}

	public void setWxyskz_ys(String wxyskz_ys) {
		this.wxyskz_ys = wxyskz_ys;
	}

	public String getWxyskz_dl() {
		return wxyskz_dl;
	}

	public void setWxyskz_dl(String wxyskz_dl) {
		this.wxyskz_dl = wxyskz_dl;
	}

	public String getWxyskz_jtz() {
		return wxyskz_jtz;
	}

	public void setWxyskz_jtz(String wxyskz_jtz) {
		this.wxyskz_jtz = wxyskz_jtz;
	}

	public String getWxyskz_jtzmb() {
		return wxyskz_jtzmb;
	}

	public void setWxyskz_jtzmb(String wxyskz_jtzmb) {
		this.wxyskz_jtzmb = wxyskz_jtzmb;
	}

	public String getWxyskz_jyymjz() {
		return wxyskz_jyymjz;
	}

	public void setWxyskz_jyymjz(String wxyskz_jyymjz) {
		this.wxyskz_jyymjz = wxyskz_jyymjz;
	}

	public String getWxyskz_ymjzqt() {
		return wxyskz_ymjzqt;
	}

	public void setWxyskz_ymjzqt(String wxyskz_ymjzqt) {
		this.wxyskz_ymjzqt = wxyskz_ymjzqt;
	}

	public String getWxyskz_qt() {
		return wxyskz_qt;
	}

	public void setWxyskz_qt(String wxyskz_qt) {
		this.wxyskz_qt = wxyskz_qt;
	}

	/**
	 * 健康体检--一般状况ID(SEQ_JKTJ_YBZKID)
	 * @return
	 */
	public String getJktj_ybzkid() {
		return jktj_ybzkid;
	}

	/**
	 * 健康体检--一般状况ID(SEQ_JKTJ_YBZKID)
	 * @param jktj_ybzkid
	 */
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

	public String getJktj_zyjkwtid() {
		return jktj_zyjkwtid;
	}

	public void setJktj_zyjkwtid(String jktj_zyjkwtid) {
		this.jktj_zyjkwtid = jktj_zyjkwtid;
	}

	public String getZyjkwt_nxgwfx() {
		return zyjkwt_nxgwfx;
	}

	public void setZyjkwt_nxgwfx(String zyjkwt_nxgwfx) {
		this.zyjkwt_nxgwfx = zyjkwt_nxgwfx;
	}

	public String getZyjkwt_nxgzz() {
		return zyjkwt_nxgzz;
	}

	public void setZyjkwt_nxgzz(String zyjkwt_nxgzz) {
		this.zyjkwt_nxgzz = zyjkwt_nxgzz;
	}

	public String getZyjkwt_nxgncx() {
		return zyjkwt_nxgncx;
	}

	public void setZyjkwt_nxgncx(String zyjkwt_nxgncx) {
		this.zyjkwt_nxgncx = zyjkwt_nxgncx;
	}

	public String getZyjkwt_nxgzwm() {
		return zyjkwt_nxgzwm;
	}

	public void setZyjkwt_nxgzwm(String zyjkwt_nxgzwm) {
		this.zyjkwt_nxgzwm = zyjkwt_nxgzwm;
	}

	public String getZyjkwt_nxgnqx() {
		return zyjkwt_nxgnqx;
	}

	public void setZyjkwt_nxgnqx(String zyjkwt_nxgnqx) {
		this.zyjkwt_nxgnqx = zyjkwt_nxgnqx;
	}

	public String getZyjkwt_nxgqt() {
		return zyjkwt_nxgqt;
	}

	public void setZyjkwt_nxgqt(String zyjkwt_nxgqt) {
		this.zyjkwt_nxgqt = zyjkwt_nxgqt;
	}

	public String getZyjkwt_szwfx() {
		return zyjkwt_szwfx;
	}

	public void setZyjkwt_szwfx(String zyjkwt_szwfx) {
		this.zyjkwt_szwfx = zyjkwt_szwfx;
	}

	public String getZyjkwt_sztnsb() {
		return zyjkwt_sztnsb;
	}

	public void setZyjkwt_sztnsb(String zyjkwt_sztnsb) {
		this.zyjkwt_sztnsb = zyjkwt_sztnsb;
	}

	public String getZyjkwt_szsgn() {
		return zyjkwt_szsgn;
	}

	public void setZyjkwt_szsgn(String zyjkwt_szsgn) {
		this.zyjkwt_szsgn = zyjkwt_szsgn;
	}

	public String getZyjkwt_szjxsy() {
		return zyjkwt_szjxsy;
	}

	public void setZyjkwt_szjxsy(String zyjkwt_szjxsy) {
		this.zyjkwt_szjxsy = zyjkwt_szjxsy;
	}

	public String getZyjkwt_szmxsy() {
		return zyjkwt_szmxsy;
	}

	public void setZyjkwt_szmxsy(String zyjkwt_szmxsy) {
		this.zyjkwt_szmxsy = zyjkwt_szmxsy;
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

	public String getZyjkwt_xzxjgs() {
		return zyjkwt_xzxjgs;
	}

	public void setZyjkwt_xzxjgs(String zyjkwt_xzxjgs) {
		this.zyjkwt_xzxjgs = zyjkwt_xzxjgs;
	}

	public String getZyjkwt_xzxjt() {
		return zyjkwt_xzxjt;
	}

	public void setZyjkwt_xzxjt(String zyjkwt_xzxjt) {
		this.zyjkwt_xzxjt = zyjkwt_xzxjt;
	}

	public String getZyjkwt_xzgzdm() {
		return zyjkwt_xzgzdm;
	}

	public void setZyjkwt_xzgzdm(String zyjkwt_xzgzdm) {
		this.zyjkwt_xzgzdm = zyjkwt_xzgzdm;
	}

	public String getZyjkwt_xzxlsj() {
		return zyjkwt_xzxlsj;
	}

	public void setZyjkwt_xzxlsj(String zyjkwt_xzxlsj) {
		this.zyjkwt_xzxlsj = zyjkwt_xzxlsj;
	}

	public String getZyjkwt_xzxqqt() {
		return zyjkwt_xzxqqt;
	}

	public void setZyjkwt_xzxqqt(String zyjkwt_xzxqqt) {
		this.zyjkwt_xzxqqt = zyjkwt_xzxqqt;
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

	public String getZyjkwt_xgdml() {
		return zyjkwt_xgdml;
	}

	public void setZyjkwt_xgdml(String zyjkwt_xgdml) {
		this.zyjkwt_xgdml = zyjkwt_xgdml;
	}

	public String getZyjkwt_xgdmjb() {
		return zyjkwt_xgdmjb;
	}

	public void setZyjkwt_xgdmjb(String zyjkwt_xgdmjb) {
		this.zyjkwt_xgdmjb = zyjkwt_xgdmjb;
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

	public String getZyjkwt_ybswm() {
		return zyjkwt_ybswm;
	}

	public void setZyjkwt_ybswm(String zyjkwt_ybswm) {
		this.zyjkwt_ybswm = zyjkwt_ybswm;
	}

	public String getZyjkwt_ybsrsz() {
		return zyjkwt_ybsrsz;
	}

	public void setZyjkwt_ybsrsz(String zyjkwt_ybsrsz) {
		this.zyjkwt_ybsrsz = zyjkwt_ybsrsz;
	}

	public String getZyjkwt_ybbnz() {
		return zyjkwt_ybbnz;
	}

	public void setZyjkwt_ybbnz(String zyjkwt_ybbnz) {
		this.zyjkwt_ybbnz = zyjkwt_ybbnz;
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

	public String getJktj_zytzbsid() {
		return jktj_zytzbsid;
	}

	public void setJktj_zytzbsid(String jktj_zytzbsid) {
		this.jktj_zytzbsid = jktj_zytzbsid;
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

	public String getZz_zzid00() {
		return zz_zzid00;
	}

	public void setZz_zzid00(String zz_zzid00) {
		this.zz_zzid00 = zz_zzid00;
	}

	public String getZz_wzz() {
		return zz_wzz;
	}

	public void setZz_wzz(String zz_wzz) {
		this.zz_wzz = zz_wzz;
	}

	public String getZz_tt() {
		return zz_tt;
	}

	public void setZz_tt(String zz_tt) {
		this.zz_tt = zz_tt;
	}

	public String getZz_ty() {
		return zz_ty;
	}

	public void setZz_ty(String zz_ty) {
		this.zz_ty = zz_ty;
	}

	public String getZz_xj() {
		return zz_xj;
	}

	public void setZz_xj(String zz_xj) {
		this.zz_xj = zz_xj;
	}

	public String getZz_xm() {
		return zz_xm;
	}

	public void setZz_xm(String zz_xm) {
		this.zz_xm = zz_xm;
	}

	public String getZz_xt() {
		return zz_xt;
	}

	public void setZz_xt(String zz_xt) {
		this.zz_xt = zz_xt;
	}

	public String getZz_mxks() {
		return zz_mxks;
	}

	public void setZz_mxks(String zz_mxks) {
		this.zz_mxks = zz_mxks;
	}

	public String getZz_kt() {
		return zz_kt;
	}

	public void setZz_kt(String zz_kt) {
		this.zz_kt = zz_kt;
	}

	public String getZz_hxkn() {
		return zz_hxkn;
	}

	public void setZz_hxkn(String zz_hxkn) {
		this.zz_hxkn = zz_hxkn;
	}

	public String getZz_dy() {
		return zz_dy;
	}

	public void setZz_dy(String zz_dy) {
		this.zz_dy = zz_dy;
	}

	public String getZz_dn() {
		return zz_dn;
	}

	public void setZz_dn(String zz_dn) {
		this.zz_dn = zz_dn;
	}

	public String getZz_tzxj() {
		return zz_tzxj;
	}

	public void setZz_tzxj(String zz_tzxj) {
		this.zz_tzxj = zz_tzxj;
	}

	public String getZz_fl() {
		return zz_fl;
	}

	public void setZz_fl(String zz_fl) {
		this.zz_fl = zz_fl;
	}

	public String getZz_gjzt() {
		return zz_gjzt;
	}

	public void setZz_gjzt(String zz_gjzt) {
		this.zz_gjzt = zz_gjzt;
	}

	public String getZz_slmh() {
		return zz_slmh;
	}

	public void setZz_slmh(String zz_slmh) {
		this.zz_slmh = zz_slmh;
	}

	public String getZz_sjmm() {
		return zz_sjmm;
	}

	public void setZz_sjmm(String zz_sjmm) {
		this.zz_sjmm = zz_sjmm;
	}

	public String getZz_nj() {
		return zz_nj;
	}

	public void setZz_nj(String zz_nj) {
		this.zz_nj = zz_nj;
	}

	public String getZz_nt() {
		return zz_nt;
	}

	public void setZz_nt(String zz_nt) {
		this.zz_nt = zz_nt;
	}

	public String getZz_bm() {
		return zz_bm;
	}

	public void setZz_bm(String zz_bm) {
		this.zz_bm = zz_bm;
	}

	public String getZz_fx() {
		return zz_fx;
	}

	public void setZz_fx(String zz_fx) {
		this.zz_fx = zz_fx;
	}

	public String getZz_exot() {
		return zz_exot;
	}

	public void setZz_exot(String zz_exot) {
		this.zz_exot = zz_exot;
	}

	public String getZz_yh() {
		return zz_yh;
	}

	public void setZz_yh(String zz_yh) {
		this.zz_yh = zz_yh;
	}

	public String getZz_em() {
		return zz_em;
	}

	public void setZz_em(String zz_em) {
		this.zz_em = zz_em;
	}

	public String getZz_rfzt() {
		return zz_rfzt;
	}

	public void setZz_rfzt(String zz_rfzt) {
		this.zz_rfzt = zz_rfzt;
	}

	public String getZz_qt0000() {
		return zz_qt0000;
	}

	public void setZz_qt0000(String zz_qt0000) {
		this.zz_qt0000 = zz_qt0000;
	}

	public String getHrf_id() {
		return hrf_id;
	}

	public void setHrf_id(String hrf_id) {
		this.hrf_id = hrf_id;
	}

	public java.math.BigDecimal getSmoke() {
		return smoke;
	}

	public void setSmoke(java.math.BigDecimal smoke) {
		this.smoke = smoke;
	}

	public String getS_bgdate() {
		return s_bgdate;
	}

	public void setS_bgdate(String s_bgdate) {
		this.s_bgdate = s_bgdate;
	}

	public java.math.BigDecimal getS_dose() {
		return s_dose;
	}

	public void setS_dose(java.math.BigDecimal s_dose) {
		this.s_dose = s_dose;
	}

	public java.math.BigDecimal getHdrink_id() {
		return hdrink_id;
	}

	public void setHdrink_id(java.math.BigDecimal hdrink_id) {
		this.hdrink_id = hdrink_id;
	}

	public String getD_bgdate() {
		return d_bgdate;
	}

	public void setD_bgdate(String d_bgdate) {
		this.d_bgdate = d_bgdate;
	}

	public java.math.BigDecimal getD_dose() {
		return d_dose;
	}

	public void setD_dose(java.math.BigDecimal d_dose) {
		this.d_dose = d_dose;
	}

	public java.math.BigDecimal getMeddep() {
		return meddep;
	}

	public void setMeddep(java.math.BigDecimal meddep) {
		this.meddep = meddep;
	}

	public java.math.BigDecimal getDwellcond() {
		return dwellcond;
	}

	public void setDwellcond(java.math.BigDecimal dwellcond) {
		this.dwellcond = dwellcond;
	}

	public String getRcheckdate() {
		return rcheckdate;
	}

	public void setRcheckdate(String rcheckdate) {
		this.rcheckdate = rcheckdate;
	}

	public String getPast() {
		return past;
	}

	public void setPast(String past) {
		this.past = past;
	}

	public String getVaccination() {
		return vaccination;
	}

	public void setVaccination(String vaccination) {
		this.vaccination = vaccination;
	}

	public java.math.BigDecimal getFgxy() {
		return fgxy;
	}

	public void setFgxy(java.math.BigDecimal fgxy) {
		this.fgxy = fgxy;
	}

	public java.math.BigDecimal getFxzb() {
		return fxzb;
	}

	public void setFxzb(java.math.BigDecimal fxzb) {
		this.fxzb = fxzb;
	}

	public java.math.BigDecimal getFnxg() {
		return fnxg;
	}

	public void setFnxg(java.math.BigDecimal fnxg) {
		this.fnxg = fnxg;
	}

	public java.math.BigDecimal getFtnb() {
		return ftnb;
	}

	public void setFtnb(java.math.BigDecimal ftnb) {
		this.ftnb = ftnb;
	}

	public java.math.BigDecimal getFfjh() {
		return ffjh;
	}

	public void setFfjh(java.math.BigDecimal ffjh) {
		this.ffjh = ffjh;
	}

	public java.math.BigDecimal getFjsjb() {
		return fjsjb;
	}

	public void setFjsjb(java.math.BigDecimal fjsjb) {
		this.fjsjb = fjsjb;
	}

	public java.math.BigDecimal getFbnz() {
		return fbnz;
	}

	public void setFbnz(java.math.BigDecimal fbnz) {
		this.fbnz = fbnz;
	}

	public java.math.BigDecimal getFxtel() {
		return fxtel;
	}

	public void setFxtel(java.math.BigDecimal fxtel) {
		this.fxtel = fxtel;
	}

	public java.math.BigDecimal getMgxy() {
		return mgxy;
	}

	public void setMgxy(java.math.BigDecimal mgxy) {
		this.mgxy = mgxy;
	}

	public java.math.BigDecimal getMxzb() {
		return mxzb;
	}

	public void setMxzb(java.math.BigDecimal mxzb) {
		this.mxzb = mxzb;
	}

	public java.math.BigDecimal getMnxg() {
		return mnxg;
	}

	public void setMnxg(java.math.BigDecimal mnxg) {
		this.mnxg = mnxg;
	}

	public java.math.BigDecimal getMtnb() {
		return mtnb;
	}

	public void setMtnb(java.math.BigDecimal mtnb) {
		this.mtnb = mtnb;
	}

	public java.math.BigDecimal getMfjh() {
		return mfjh;
	}

	public void setMfjh(java.math.BigDecimal mfjh) {
		this.mfjh = mfjh;
	}

	public java.math.BigDecimal getMjsjb() {
		return mjsjb;
	}

	public void setMjsjb(java.math.BigDecimal mjsjb) {
		this.mjsjb = mjsjb;
	}

	public java.math.BigDecimal getMbnz() {
		return mbnz;
	}

	public void setMbnz(java.math.BigDecimal mbnz) {
		this.mbnz = mbnz;
	}

	public java.math.BigDecimal getMxtel() {
		return mxtel;
	}

	public void setMxtel(java.math.BigDecimal mxtel) {
		this.mxtel = mxtel;
	}

	public java.math.BigDecimal getStype() {
		return stype;
	}

	public void setStype(java.math.BigDecimal stype) {
		this.stype = stype;
	}

	public java.math.BigDecimal getStimes() {
		return stimes;
	}

	public void setStimes(java.math.BigDecimal stimes) {
		this.stimes = stimes;
	}

	public java.math.BigDecimal getSmeasure() {
		return smeasure;
	}

	public void setSmeasure(java.math.BigDecimal smeasure) {
		this.smeasure = smeasure;
	}

	public java.math.BigDecimal getHabit() {
		return habit;
	}

	public void setHabit(java.math.BigDecimal habit) {
		this.habit = habit;
	}

	public String getMfood() {
		return mfood;
	}

	public void setMfood(String mfood) {
		this.mfood = mfood;
	}

	public java.math.BigDecimal getFruit() {
		return fruit;
	}

	public void setFruit(java.math.BigDecimal fruit) {
		this.fruit = fruit;
	}

	public String getS_beveryday() {
		return s_beveryday;
	}

	public void setS_beveryday(String s_beveryday) {
		this.s_beveryday = s_beveryday;
	}

	public String getTobaccotype() {
		return tobaccotype;
	}

	public void setTobaccotype(String tobaccotype) {
		this.tobaccotype = tobaccotype;
	}

	public String getStopsmoke() {
		return stopsmoke;
	}

	public void setStopsmoke(String stopsmoke) {
		this.stopsmoke = stopsmoke;
	}

	public String getQuitsmoke() {
		return quitsmoke;
	}

	public void setQuitsmoke(String quitsmoke) {
		this.quitsmoke = quitsmoke;
	}

	public String getSecondhsmoke() {
		return secondhsmoke;
	}

	public void setSecondhsmoke(String secondhsmoke) {
		this.secondhsmoke = secondhsmoke;
	}

	public String getSecondhsday() {
		return secondhsday;
	}

	public void setSecondhsday(String secondhsday) {
		this.secondhsday = secondhsday;
	}

	public String getPassivesplace() {
		return passivesplace;
	}

	public void setPassivesplace(String passivesplace) {
		this.passivesplace = passivesplace;
	}

	public java.math.BigDecimal getD_type() {
		return d_type;
	}

	public void setD_type(java.math.BigDecimal d_type) {
		this.d_type = d_type;
	}

	public java.math.BigDecimal getD_mark() {
		return d_mark;
	}

	public void setD_mark(java.math.BigDecimal d_mark) {
		this.d_mark = d_mark;
	}

	public String getDiettype() {
		return diettype;
	}

	public void setDiettype(String diettype) {
		this.diettype = diettype;
	}

	public String getDietfrequency() {
		return dietfrequency;
	}

	public void setDietfrequency(String dietfrequency) {
		this.dietfrequency = dietfrequency;
	}

	public String getWalkcycling() {
		return walkcycling;
	}

	public void setWalkcycling(String walkcycling) {
		this.walkcycling = walkcycling;
	}

	public String getStaticlong() {
		return staticlong;
	}

	public void setStaticlong(String staticlong) {
		this.staticlong = staticlong;
	}

	public String getDaysleep() {
		return daysleep;
	}

	public void setDaysleep(String daysleep) {
		this.daysleep = daysleep;
	}

	public String getNightsleep() {
		return nightsleep;
	}

	public void setNightsleep(String nightsleep) {
		this.nightsleep = nightsleep;
	}

	public String getFzsxfb() {
		return fzsxfb;
	}

	public void setFzsxfb(String fzsxfb) {
		this.fzsxfb = fzsxfb;
	}

	public String getFexzl() {
		return fexzl;
	}

	public void setFexzl(String fexzl) {
		this.fexzl = fexzl;
	}

	public String getFqt000() {
		return fqt000;
	}

	public void setFqt000(String fqt000) {
		this.fqt000 = fqt000;
	}

	public String getMzsxfb() {
		return mzsxfb;
	}

	public void setMzsxfb(String mzsxfb) {
		this.mzsxfb = mzsxfb;
	}

	public String getMexzl() {
		return mexzl;
	}

	public void setMexzl(String mexzl) {
		this.mexzl = mexzl;
	}

	public String getMqt000() {
		return mqt000;
	}

	public void setMqt000(String mqt000) {
		this.mqt000 = mqt000;
	}

	public String getXmgxy() {
		return xmgxy;
	}

	public void setXmgxy(String xmgxy) {
		this.xmgxy = xmgxy;
	}

	public String getXmtnb() {
		return xmtnb;
	}

	public void setXmtnb(String xmtnb) {
		this.xmtnb = xmtnb;
	}

	public String getXmgxb() {
		return xmgxb;
	}

	public void setXmgxb(String xmgxb) {
		this.xmgxb = xmgxb;
	}

	public String getXmzsxfb() {
		return xmzsxfb;
	}

	public void setXmzsxfb(String xmzsxfb) {
		this.xmzsxfb = xmzsxfb;
	}

	public String getXmexzl() {
		return xmexzl;
	}

	public void setXmexzl(String xmexzl) {
		this.xmexzl = xmexzl;
	}

	public String getXmnzz() {
		return xmnzz;
	}

	public void setXmnzz(String xmnzz) {
		this.xmnzz = xmnzz;
	}

	public String getXmzxjsb() {
		return xmzxjsb;
	}

	public void setXmzxjsb(String xmzxjsb) {
		this.xmzxjsb = xmzxjsb;
	}

	public String getXmjhb() {
		return xmjhb;
	}

	public void setXmjhb(String xmjhb) {
		this.xmjhb = xmjhb;
	}

	public String getXmgy() {
		return xmgy;
	}

	public void setXmgy(String xmgy) {
		this.xmgy = xmgy;
	}

	public String getXmxtjx() {
		return xmxtjx;
	}

	public void setXmxtjx(String xmxtjx) {
		this.xmxtjx = xmxtjx;
	}

	public String getXmqt00() {
		return xmqt00;
	}

	public void setXmqt00(String xmqt00) {
		this.xmqt00 = xmqt00;
	}

	public String getZngxy() {
		return zngxy;
	}

	public void setZngxy(String zngxy) {
		this.zngxy = zngxy;
	}

	public String getZntnb() {
		return zntnb;
	}

	public void setZntnb(String zntnb) {
		this.zntnb = zntnb;
	}

	public String getZngxb() {
		return zngxb;
	}

	public void setZngxb(String zngxb) {
		this.zngxb = zngxb;
	}

	public String getZnzsxfb() {
		return znzsxfb;
	}

	public void setZnzsxfb(String znzsxfb) {
		this.znzsxfb = znzsxfb;
	}

	public String getZnexzl() {
		return znexzl;
	}

	public void setZnexzl(String znexzl) {
		this.znexzl = znexzl;
	}

	public String getZnnzz() {
		return znnzz;
	}

	public void setZnnzz(String znnzz) {
		this.znnzz = znnzz;
	}

	public String getZnzxjsb() {
		return znzxjsb;
	}

	public void setZnzxjsb(String znzxjsb) {
		this.znzxjsb = znzxjsb;
	}

	public String getZnjhb() {
		return znjhb;
	}

	public void setZnjhb(String znjhb) {
		this.znjhb = znjhb;
	}

	public String getZngy() {
		return zngy;
	}

	public void setZngy(String zngy) {
		this.zngy = zngy;
	}

	public String getZnxtjx() {
		return znxtjx;
	}

	public void setZnxtjx(String znxtjx) {
		this.znxtjx = znxtjx;
	}

	public String getZnqt00() {
		return znqt00;
	}

	public void setZnqt00(String znqt00) {
		this.znqt00 = znqt00;
	}

	public String getZybl_jtzy() {
		return zybl_jtzy;
	}

	public void setZybl_jtzy(String zybl_jtzy) {
		this.zybl_jtzy = zybl_jtzy;
	}

	public String getZybl_cysj() {
		return zybl_cysj;
	}

	public void setZybl_cysj(String zybl_cysj) {
		this.zybl_cysj = zybl_cysj;
	}

	public String getZybl_hxp() {
		return zybl_hxp;
	}

	public void setZybl_hxp(String zybl_hxp) {
		this.zybl_hxp = zybl_hxp;
	}

	public String getZybl_hxpcs() {
		return zybl_hxpcs;
	}

	public void setZybl_hxpcs(String zybl_hxpcs) {
		this.zybl_hxpcs = zybl_hxpcs;
	}

	public String getZybl_dw() {
		return zybl_dw;
	}

	public void setZybl_dw(String zybl_dw) {
		this.zybl_dw = zybl_dw;
	}

	public String getZybl_dwcs() {
		return zybl_dwcs;
	}

	public void setZybl_dwcs(String zybl_dwcs) {
		this.zybl_dwcs = zybl_dwcs;
	}

	public String getZybl_sx() {
		return zybl_sx;
	}

	public void setZybl_sx(String zybl_sx) {
		this.zybl_sx = zybl_sx;
	}

	public String getZybl_sxcs() {
		return zybl_sxcs;
	}

	public void setZybl_sxcs(String zybl_sxcs) {
		this.zybl_sxcs = zybl_sxcs;
	}

	public String getTydl_jcdlsj() {
		return tydl_jcdlsj;
	}

	public void setTydl_jcdlsj(String tydl_jcdlsj) {
		this.tydl_jcdlsj = tydl_jcdlsj;
	}

	public String getYsxg_hsjh() {
		return ysxg_hsjh;
	}

	public void setYsxg_hsjh(String ysxg_hsjh) {
		this.ysxg_hsjh = ysxg_hsjh;
	}

	public String getYsxg_hswz() {
		return ysxg_hswz;
	}

	public void setYsxg_hswz(String ysxg_hswz) {
		this.ysxg_hswz = ysxg_hswz;
	}

	public String getYsxg_sswz() {
		return ysxg_sswz;
	}

	public void setYsxg_sswz(String ysxg_sswz) {
		this.ysxg_sswz = ysxg_sswz;
	}

	public String getYsxg_syan() {
		return ysxg_syan;
	}

	public void setYsxg_syan(String ysxg_syan) {
		this.ysxg_syan = ysxg_syan;
	}

	public String getYsxg_syou() {
		return ysxg_syou;
	}

	public void setYsxg_syou(String ysxg_syou) {
		this.ysxg_syou = ysxg_syou;
	}

	public String getYsxg_stang() {
		return ysxg_stang;
	}

	public void setYsxg_stang(String ysxg_stang) {
		this.ysxg_stang = ysxg_stang;
	}

	public String getXyqk_xynl() {
		return xyqk_xynl;
	}

	public void setXyqk_xynl(String xyqk_xynl) {
		this.xyqk_xynl = xyqk_xynl;
	}

	public String getXyqk_jynl() {
		return xyqk_jynl;
	}

	public void setXyqk_jynl(String xyqk_jynl) {
		this.xyqk_jynl = xyqk_jynl;
	}

	public String getYjqk_jjnl() {
		return yjqk_jjnl;
	}

	public void setYjqk_jjnl(String yjqk_jjnl) {
		this.yjqk_jjnl = yjqk_jjnl;
	}

	public String getYjqk_ksyjnl() {
		return yjqk_ksyjnl;
	}

	public void setYjqk_ksyjnl(String yjqk_ksyjnl) {
		this.yjqk_ksyjnl = yjqk_ksyjnl;
	}

	public String getYjqk_sfcjj() {
		return yjqk_sfcjj;
	}

	public void setYjqk_sfcjj(String yjqk_sfcjj) {
		this.yjqk_sfcjj = yjqk_sfcjj;
	}

	public String getYjzl_bj() {
		return yjzl_bj;
	}

	public void setYjzl_bj(String yjzl_bj) {
		this.yjzl_bj = yjzl_bj;
	}

	public String getYjzl_pj() {
		return yjzl_pj;
	}

	public void setYjzl_pj(String yjzl_pj) {
		this.yjzl_pj = yjzl_pj;
	}

	public String getYjzl_hongj() {
		return yjzl_hongj;
	}

	public void setYjzl_hongj(String yjzl_hongj) {
		this.yjzl_hongj = yjzl_hongj;
	}

	public String getYjzl_huangj() {
		return yjzl_huangj;
	}

	public void setYjzl_huangj(String yjzl_huangj) {
		this.yjzl_huangj = yjzl_huangj;
	}

	public String getYjzl_qt0000() {
		return yjzl_qt0000;
	}

	public void setYjzl_qt0000(String yjzl_qt0000) {
		this.yjzl_qt0000 = yjzl_qt0000;
	}

	public String getZybl_qk() {
		return zybl_qk;
	}

	public void setZybl_qk(String zybl_qk) {
		this.zybl_qk = zybl_qk;
	}

	public String getAddrzh() {
		return addrzh;
	}

	public void setAddrzh(String addrzh) {
		this.addrzh = addrzh;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEdateBegin() {
		return edateBegin;
	}

	public void setEdateBegin(String edateBegin) {
		this.edateBegin = edateBegin;
	}

	public String getEdateEnd() {
		return edateEnd;
	}

	public void setEdateEnd(String edateEnd) {
		this.edateEnd = edateEnd;
	}

	public String getIstsrq() {
		return istsrq;
	}

	public void setIstsrq(String istsrq) {
		this.istsrq = istsrq;
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

	public String getTjzzqk() {
		return tjzzqk;
	}

	public void setTjzzqk(String tjzzqk) {
		this.tjzzqk = tjzzqk;
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

	public String getZyjkwt_nxg() {
		return zyjkwt_nxg;
	}

	public void setZyjkwt_nxg(String zyjkwt_nxg) {
		this.zyjkwt_nxg = zyjkwt_nxg;
	}

	public String getZyjkwt_sz() {
		return zyjkwt_sz;
	}

	public void setZyjkwt_sz(String zyjkwt_sz) {
		this.zyjkwt_sz = zyjkwt_sz;
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

	public String getCt_rxqk() {
		return ct_rxqk;
	}

	public void setCt_rxqk(String ct_rxqk) {
		this.ct_rxqk = ct_rxqk;
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

	public String getFmyghid() {
		return fmyghid;
	}

	public void setFmyghid(String fmyghid) {
		this.fmyghid = fmyghid;
	}

	public String getYmmc() {
		return ymmc;
	}

	public void setYmmc(String ymmc) {
		this.ymmc = ymmc;
	}

	public String getJzrq() {
		return jzrq;
	}

	public void setJzrq(String jzrq) {
		this.jzrq = jzrq;
	}

	public String getJzyy() {
		return jzyy;
	}

	public void setJzyy(String jzyy) {
		this.jzyy = jzyy;
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

	public String getWxyskz_ymjz() {
		return wxyskz_ymjz;
	}

	public void setWxyskz_ymjz(String wxyskz_ymjz) {
		this.wxyskz_ymjz = wxyskz_ymjz;
	}

	public String getJkzd00() {
		return jkzd00;
	}

	public void setJkzd00(String jkzd00) {
		this.jkzd00 = jkzd00;
	}

	public String getJjcf00() {
		return jjcf00;
	}

	public void setJjcf00(String jjcf00) {
		this.jjcf00 = jjcf00;
	}

	public String getJtbcsid() {
		return jtbcsid;
	}

	public void setJtbcsid(String jtbcsid) {
		this.jtbcsid = jtbcsid;
	}

	public String getJcrq() {
		return jcrq;
	}

	public void setJcrq(String jcrq) {
		this.jcrq = jcrq;
	}

	public String getCcrq() {
		return ccrq;
	}

	public void setCcrq(String ccrq) {
		this.ccrq = ccrq;
	}

	public String getBjyy() {
		return bjyy;
	}

	public void setBjyy(String bjyy) {
		this.bjyy = bjyy;
	}

	public String getYljgmc() {
		return yljgmc;
	}

	public void setYljgmc(String yljgmc) {
		this.yljgmc = yljgmc;
	}

	public String getBcbah() {
		return bcbah;
	}

	public void setBcbah(String bcbah) {
		this.bcbah = bcbah;
	}

	public String getYwbh_id() {
		return ywbh_id;
	}

	public void setYwbh_id(String ywbh_id) {
		this.ywbh_id = ywbh_id;
	}

	public String getYwmc() {
		return ywmc;
	}

	public void setYwmc(String ywmc) {
		this.ywmc = ywmc;
	}

	public String getYwyf() {
		return ywyf;
	}

	public void setYwyf(String ywyf) {
		this.ywyf = ywyf;
	}

	public String getYwyl() {
		return ywyl;
	}

	public void setYwyl(String ywyl) {
		this.ywyl = ywyl;
	}

	public String getYysj() {
		return yysj;
	}

	public void setYysj(String yysj) {
		this.yysj = yysj;
	}

	public String getFyycx() {
		return fyycx;
	}

	public void setFyycx(String fyycx) {
		this.fyycx = fyycx;
	}

	public String getRef_sf_id() {
		return ref_sf_id;
	}

	public void setRef_sf_id(String ref_sf_id) {
		this.ref_sf_id = ref_sf_id;
	}

	public String getSf_id0000() {
		return sf_id0000;
	}

	public void setSf_id0000(String sf_id0000) {
		this.sf_id0000 = sf_id0000;
	}

	public String getRef_id() {
		return ref_id;
	}

	public void setRef_id(String ref_id) {
		this.ref_id = ref_id;
	}

	public String getMxjbbz() {
		return mxjbbz;
	}

	public void setMxjbbz(String mxjbbz) {
		this.mxjbbz = mxjbbz;
	}

	public String getSfmb() {
		return sfmb;
	}

	public void setSfmb(String sfmb) {
		this.sfmb = sfmb;
	}

	public String getIh_id() {
		return ih_id;
	}

	public void setIh_id(String ih_id) {
		this.ih_id = ih_id;
	}

	public String getEld_id() {
		return eld_id;
	}

	public void setEld_id(String eld_id) {
		this.eld_id = eld_id;
	}

	public String getIndate() {
		return indate;
	}

	public void setIndate(String indate) {
		this.indate = indate;
	}

	public String getOutdate() {
		return outdate;
	}

	public void setOutdate(String outdate) {
		this.outdate = outdate;
	}

	public String getHospital() {
		return hospital;
	}

	public void setHospital(String hospital) {
		this.hospital = hospital;
	}

	public String getDepartments() {
		return departments;
	}

	public void setDepartments(String departments) {
		this.departments = departments;
	}

	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	public String getSurgery() {
		return surgery;
	}

	public void setSurgery(String surgery) {
		this.surgery = surgery;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getInhreason() {
		return inhreason;
	}

	public void setInhreason(String inhreason) {
		this.inhreason = inhreason;
	}

	public String getZybah() {
		return zybah;
	}

	public void setZybah(String zybah) {
		this.zybah = zybah;
	}

	public String getTzbs_id() {
		return tzbs_id;
	}

	public void setTzbs_id(String tzbs_id) {
		this.tzbs_id = tzbs_id;
	}

	/**
	 * 辨识选项结果
	 * @return
	 */
	public String getBsxxjg() {
		return bsxxjg;
	}

	/**
	 * 辨识选项结果
	 * @param bsxxjg
	 */
	public void setBsxxjg(String bsxxjg) {
		this.bsxxjg = bsxxjg;
	}

	/**
	 * 气虚质分数
	 * @return
	 */
	public String getQxzscore() {
		return qxzscore;
	}

	/**
	 * 气虚质分数
	 * @param qxzscore
	 */
	public void setQxzscore(String qxzscore) {
		this.qxzscore = qxzscore;
	}

	/**
	 * 阳虚质分数
	 * @return
	 */
	public String getYx0score() {
		return yx0score;
	}

	/**
	 * 阳虚质分数
	 * @param yx0score
	 */
	public void setYx0score(String yx0score) {
		this.yx0score = yx0score;
	}

	/**
	 * 阴虚质分数
	 * @return
	 */
	public String getYxzscore() {
		return yxzscore;
	}

	/**
	 * 阴虚质分数
	 * @param yxzscore
	 */
	public void setYxzscore(String yxzscore) {
		this.yxzscore = yxzscore;
	}

	/**
	 * 痰湿质分数
	 * @return
	 */
	public String getTszscore() {
		return tszscore;
	}

	/**
	 * 痰湿质分数
	 * @param tszscore
	 */
	public void setTszscore(String tszscore) {
		this.tszscore = tszscore;
	}

	/**
	 * 湿热质分数
	 * @return
	 */
	public String getSrzscore() {
		return srzscore;
	}

	/**
	 * 湿热质分数
	 * @param srzscore
	 */
	public void setSrzscore(String srzscore) {
		this.srzscore = srzscore;
	}

	/**
	 * 血瘀质分数
	 * @return
	 */
	public String getXyzscore() {
		return xyzscore;
	}

	/**
	 * 血瘀质分数
	 * @param xyzscore
	 */
	public void setXyzscore(String xyzscore) {
		this.xyzscore = xyzscore;
	}

	/**
	 * 气郁质分数
	 * @return
	 */
	public String getQyzscore() {
		return qyzscore;
	}

	/**
	 * 气郁质分数
	 * @param qyzscore
	 */
	public void setQyzscore(String qyzscore) {
		this.qyzscore = qyzscore;
	}

	/**
	 * 特禀质分数
	 * @return
	 */
	public String getTbzscore() {
		return tbzscore;
	}

	/**
	 * 特禀质分数
	 * @param tbzscore
	 */
	public void setTbzscore(String tbzscore) {
		this.tbzscore = tbzscore;
	}

	/**
	 * 平和质分数
	 * @return
	 */
	public String getPhzscore() {
		return phzscore;
	}

	/**
	 * 平和质分数
	 * @param phzscore
	 */
	public void setPhzscore(String phzscore) {
		this.phzscore = phzscore;
	}

	/**
	 * 体质辨识统计
	 * @return
	 */
	public String getTzbstj() {
		return tzbstj;
	}

	/**
	 * 体质辨识统计
	 * @param tzbstj
	 */
	public void setTzbstj(String tzbstj) {
		this.tzbstj = tzbstj;
	}

	/**
	 * 体质辨识统计
	 * @return
	 */
	public String getBjzdtj() {
		return bjzdtj;
	}

	/**
	 * 体质辨识统计
	 * @param bjzdtj
	 */
	public void setBjzdtj(String bjzdtj) {
		this.bjzdtj = bjzdtj;
	}

	public String getZdid00() {
		return zdid00;
	}

	public void setZdid00(String zdid00) {
		this.zdid00 = zdid00;
	}

	public java.sql.Timestamp getCjrq00() {
		return cjrq00;
	}

	public void setCjrq00(java.sql.Timestamp cjrq00) {
		this.cjrq00 = cjrq00;
	}

	public java.sql.Timestamp getZhbjrq() {
		return zhbjrq;
	}

	public void setZhbjrq(java.sql.Timestamp zhbjrq) {
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

	/**
	 * 住院史
	 * @return
	 */
	public List<T_elderlyinhospital_ylkDTO> getT_elderlyinhospital() {
		return t_elderlyinhospital;
	}

	/**
	 * 住院史
	 */
	public void setT_elderlyinhospital(List<T_elderlyinhospital_ylkDTO> t_elderlyinhospital) {
		this.t_elderlyinhospital = t_elderlyinhospital;
	}

	/**
	 * 家庭病床史
	 * @return
	 */
	public List<Jktj_jtbcsDTO> getJktj_jtbcs() {
		return jktj_jtbcs;
	}

	/**
	 * 家庭病床史
	 */
	public void setJktj_jtbcs(List<Jktj_jtbcsDTO> jktj_jtbcs) {
		this.jktj_jtbcs = jktj_jtbcs;
	}

	/**
	 * 用药情况
	 * @return
	 */
	public List<T_mxjb_sf_yyqkDTO> getT_mxjb_sf_yyqk() {
		return t_mxjb_sf_yyqk;
	}

	/**
	 * 用药情况
	 */
	public void setT_mxjb_sf_yyqk(List<T_mxjb_sf_yyqkDTO> t_mxjb_sf_yyqk) {
		this.t_mxjb_sf_yyqk = t_mxjb_sf_yyqk;
	}

	/**
	 * 非免疫规划情况
	 * @return
	 */
	public List<Jktj_fmyghDTO> getJktj_fmygh() {
		return jktj_fmygh;
	}

	/**
	 * 非免疫规划情况
	 */
	public void setJktj_fmygh(List<Jktj_fmyghDTO> jktj_fmygh) {
		this.jktj_fmygh = jktj_fmygh;
	}

	/**
	 * 老年人
	 * @return
	 */
	/*public java.lang.String getLnr000() {
		return lnr000;
	}*/

	/**
	 * 老年人
	 * @param lnr000
	 */
	/*public void setLnr000(java.lang.String lnr000) {
		this.lnr000 = lnr000;
	}*/

	/**
	 * 高血压
	 * @return
	 */
	/*public java.lang.String getGxy000() {
		return gxy000;
	}*/

	/**
	 * 高血压
	 * @param gxy000
	 */
	/*public void setGxy000(java.lang.String gxy000) {
		this.gxy000 = gxy000;
	}*/

	/**
	 * 糖尿病
	 * @return
	 */
	/*public java.lang.String getTnb000() {
		return tnb000;
	}*/

	/**
	 * 糖尿病
	 * @param tnb000
	 */
	/*public void setTnb000(java.lang.String tnb000) {
		this.tnb000 = tnb000;
	}*/

	/**
	 * 情志调摄
	 * @return
	 */
	public String getQzts00() {
		return qzts00;
	}

	/**
	 * 情志调摄
	 * @param qzts00
	 */
	public void setQzts00(String qzts00) {
		this.qzts00 = qzts00;
	}

	/**
	 * 饮食调养
	 * @return
	 */
	public String getYsty00() {
		return ysty00;
	}

	/**
	 * 饮食调养
	 * @param ysty00
	 */
	public void setYsty00(String ysty00) {
		this.ysty00 = ysty00;
	}

	/**
	 * 情志调摄
	 * @return
	 */
	public String getQjts00() {
		return qjts00;
	}

	/**
	 * 情志调摄
	 * @param qjts00
	 */
	public void setQjts00(String qjts00) {
		this.qjts00 = qjts00;
	}

	/**
	 * 运动保健
	 * @return
	 */
	public String getYdbj00() {
		return ydbj00;
	}

	/**
	 * 运动保健
	 * @param ydbj00
	 */
	public void setYdbj00(String ydbj00) {
		this.ydbj00 = ydbj00;
	}

	/**
	 * 穴位保健
	 * @return
	 */
	public String getXwbj00() {
		return xwbj00;
	}

	/**
	 * 穴位保健
	 * @param xwbj00
	 */
	public void setXwbj00(String xwbj00) {
		this.xwbj00 = xwbj00;
	}

	/**
	 * 体质名称
	 * @return
	 */
	public String getTzmc00() {
		return tzmc00;
	}

	/**
	 * 体质名称
	 * @param tzmc00
	 */
	public void setTzmc00(String tzmc00) {
		this.tzmc00 = tzmc00;
	}

	/**
	 * 体质辨识名称
	 * @return
	 */
	public String getTzbsmc() {
		return tzbsmc;
	}

	/**
	 * 体质辨识名称
	 * @param tzbsmc
	 */
	public void setTzbsmc(String tzbsmc) {
		this.tzbsmc = tzbsmc;
	}

	/**
	 * 健康体检一般状况信息对象
	 * @return
	 */
	public Jktj_ybzk_ylkDTO getJktj_ybzk() {
		return jktj_ybzk;
	}

	/**
	 * 健康体检一般状况信息对象
	 * @param jktj_ybzk
	 */
	public void setJktj_ybzk(Jktj_ybzk_ylkDTO jktj_ybzk) {
		this.jktj_ybzk = jktj_ybzk;
	}

	/**
	 * 健康体检生活方式信息对象
	 * @return
	 */
	public jktj_shfsDTO getJktj_shfs() {
		return jktj_shfs;
	}

	/**
	 * 健康体检生活方式信息对象
	 * @param jktj_shfs
	 */
	public void setJktj_shfs(jktj_shfsDTO jktj_shfs) {
		/*jktj_shfs=new  jktj_shfsDTO();*/
		this.jktj_shfs = jktj_shfs;
	}

	/**
	 * 健康体检脏器功能信息对象
	 * @return
	 */
	public jktj_zqgnDTO getJktj_zqgn() {
		return jktj_zqgn;
	}

	/**
	 * 健康体检脏器功能信息对象
	 * @param jktj_zqgn
	 */
	public void setJktj_zqgn(jktj_zqgnDTO jktj_zqgn) {
		this.jktj_zqgn = jktj_zqgn;
	}

	/**
	 * 健康体检之查体信息对象
	 * @return
	 */
	public jktj_ctDTO getJktj_ct() {
		return jktj_ct;
	}

	/**
	 * 健康体检之查体信息对象
	 * @param jktj_ct
	 */
	public void setJktj_ct(jktj_ctDTO jktj_ct) {
		this.jktj_ct = jktj_ct;
	}

	/**
	 * 健康体检之辅助检查对象
	 * @return
	 */
	public jktj_fzjcDTO getJktj_fzjc() {
		return jktj_fzjc;
	}

	/**
	 * 健康体检之辅助检查对象
	 * @param jktj_fzjc
	 */
	public void setJktj_fzjc(jktj_fzjcDTO jktj_fzjc) {
		this.jktj_fzjc = jktj_fzjc;
	}

	/**
	 * 健康体检之主要健康问题对象
	 * @return
	 */
	public jktj_zyjkwtDTO getJktj_zyjkwt() {
		return jktj_zyjkwt;
	}

	/**
	 * 健康体检之主要健康问题对象
	 * @param jktj_zyjkwt
	 */
	public void setJktj_zyjkwt(jktj_zyjkwtDTO jktj_zyjkwt) {
		this.jktj_zyjkwt = jktj_zyjkwt;
	}

	/**
	 * 健康体检之健康评价对象
	 * @return
	 */
	public jktj_jkpjDTO getJktj_jkpj() {
		return jktj_jkpj;
	}

	/**
	 * 健康体检之健康评价对象
	 * @param jktj_jkpj
	 */
	public void setJktj_jkpj(jktj_jkpjDTO jktj_jkpj) {
		this.jktj_jkpj = jktj_jkpj;
	}

	/**
	 * 老年人中医体质辨识信息对象
	 * @return
	 */
	public T_elderlyzytzbs_ylzDTO getT_elderlyzytzbs() {
		return t_elderlyzytzbs;
	}

	/**
	 * 老年人中医体质辨识信息对象
	 * @param t_elderlyzytzbs
	 */
	public void setT_elderlyzytzbs(T_elderlyzytzbs_ylzDTO t_elderlyzytzbs) {
		this.t_elderlyzytzbs = t_elderlyzytzbs;
	}

	public T_elderly_zybszdDTO getT_elderlyzybszd() {
		return t_elderlyzybszd;
	}

	public void setT_elderlyzybszd(T_elderly_zybszdDTO t_elderlyzybszd) {
		this.t_elderlyzybszd = t_elderlyzybszd;
	}

	public static interface IFindJktj_MxGroup {}

}
