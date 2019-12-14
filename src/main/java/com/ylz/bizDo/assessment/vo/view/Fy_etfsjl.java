/*
 * CopyRight: StartTech 2010 cop. ltd.
 * All Right Reverse.
 * Class Name: <code>Fy_etfsjl</code>.
 */
package com.ylz.bizDo.assessment.vo.view;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;

/**
 * 儿童访视记录表
 * @author hp
 *
 */
public class Fy_etfsjl implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2578900691127395633L;

	private java.lang.String xtbhsf; // 随访系统编号
	private java.lang.String scxtbh; // 手册系统内码
	private java.lang.String ssnnz0; // 所属年龄组（0--新生儿，1-1岁以内儿童，2--1-2岁儿童，3--3岁以上儿童）
	private java.lang.String xmxl00; // 检查项目时间段（0--新生儿，1--满月，2-3月龄，3--6月龄，4-8月龄，5-12月龄，6-18月龄，7-24月龄，8-30月龄,9-3岁,a-4岁,b-5岁,c-6岁
	private java.math.BigDecimal etsg00; // 目前身长
	private java.math.BigDecimal ettz00; // 目前体重
	private java.lang.String sgqk00; // 身高情况（1--上，2-中，3-下） 234
	private java.lang.String tzqk00; // 体重情况（1--上，2--中，3--下） 234
	private java.lang.String tw0000; // 头围cm(头围的测量值，计量单位为cm) 2
	private java.lang.String wyfs00; // 喂养方式(1纯母乳，2混合，3人工) 1
	private java.lang.String cnl000; // 吃奶量（单位ml/次） 1
	private java.lang.String cncs00; // 吃奶次数（单位次/日） 1
	private java.lang.String ot0000; // 呕吐（1无，2有） 1
	private java.lang.String db0000; // 大便（1糊状，2稀） 1
	private java.lang.String dbcs00; // 大便次数(单位：次/日) 1
	private java.lang.String twqk00; // 体温（单位：摄氏度） 1
	private java.lang.String ml0000; // 脉率（次/分钟） 1
	private java.lang.String hxpl00; // 呼吸频率（次/分钟） 1
	private java.lang.String msqk00; // 面色情况（1--红润，2--黄染，3-其他） 123(3检查时2表示其他)
	private java.lang.String hhbw00; // 黄疸部位 1-面部 2-躯干 3-四肢 4手足 1
	private java.lang.String qcone0; // 前囱1 123
	private java.lang.String qctwo0; // 前囱2 123
	private java.lang.String qcqk00; // 前囱类型（1-正常，2-膨隆，3-凹陷，4--其他）
										// 123（23检查时是只填1闭合，2未闭）
	private java.lang.String eyeqk0; // 眼外观（1未见异常，2异常） 123
	private java.lang.String earqk0; // 耳外观（1未见异常，2异常） 123
	private java.lang.String noseqk; // 鼻（1未见异常，2异常） 1
	private java.lang.String kqqk00; // 口腔情况（1未见异常，2异常）
										// 1234（6月龄，8月龄表示出牙数；1～2岁表示出牙数和龋齿数）
	private java.lang.String xftz00; // 心肺听诊（1未见异常，2异常） 1234
	private java.lang.String fbcz00; // 腹部触诊（1未见异常，2异常） 1234
	private java.lang.String szhdqk; // 四肢活动度（1未见异常，2异常） 123
	private java.lang.String jbbkqk; // 颈部包块（1无，2有） 12（一岁检查是只填1有，2无）
	private java.lang.String skinqk; // 皮肤情况（1-未见异常，2-湿疹，3--糜烂，4-其他）
										// 123（23检查是只填1未见异常，2异常）
	private java.lang.String gmqk00; // 肛门情况（1未见异常，2异常） 1
	private java.lang.String wszqqk; // 外生殖器情况（1未见异常，2异常） 12
	private java.lang.String jzqk00; // 脊柱情况（1未见异常，2异常） 1
	private java.lang.String qdqk00; // 脐带情况（1--未脱，2-脱落，3--脐部有渗出，4-其他） 12
	private java.lang.String tl0000; // 听力（1通过，2未通过） 23
	private java.lang.String glbzz0; // 佝偻病症状（1--无，2-夜惊，3--多汗，4--烦躁） 2
	private java.lang.String glbtz0; // 佝偻病体征（1--无，2--颅外骨软化，3--方颅，4--枕秃，5-肋串珠，6--肋外翻，7--肋软骨沟，8-鸡胸，9--手镯征，10-o型腿，11-X型腿)
										// 2
	private java.lang.String xhdb00; // 血红蛋白值（单位：g/L） 234
	private java.lang.String hwhdqk; // 户外活动(单位：小时/日) 23
	private java.lang.String wss000; // 维生素D/钙剂添加标志(表示是否添加维生素D和（或）钙剂) 23
	private java.lang.String fypgqk; // 发育评估情况（1-通过，2--未通过） 23
	private java.lang.String hbqk00; // 两次随访间患病情况（1-未患病，2--患病） 234
	private java.lang.String hbqkfy; // 两次随访间患病具体情况-肺炎(2)
	private java.lang.String hbqkfx; // 两次随访间患病具体情况-腹泻(3)
	private java.lang.String hbqkws; // 两次随访间患病具体情况-外伤(4)
	private java.lang.String hbqkqt; // 两次随访间患病具体情况-其他(3-6岁)
	private java.lang.String qt0000; // 两次随访间患病具体情况-其他(1岁以内、1-2岁)
	private java.lang.String btqk00; // 步态情况（1未见异常，2异常） 3
	private java.lang.String tgfypj; // //体格发育评价:1.正常2.低体重3.消瘦4.发育迟缓5.超重6.肥胖
	private java.lang.String sl0000; // 视力 4
	private java.lang.String zdqk00; // 指导:1.科学喂养2.生长发育3.疾病预防4.预防意外伤害5.口腔保健，6.其他
	private java.lang.String zzqk00; // 转诊情况（2.有，1.无）
	private java.lang.String zzyy00; // 转诊原因 1234
	private java.lang.String zzjgks; // 转诊机构及科室 1234
	private Date xcsfrq; // 下次随访日期 1234
	private java.lang.String xcsfdd; // 下次随访地点 1
	private Date sfrq00; // 随访日期 1234
	private java.lang.String sfys00; // 随访医生 1234
	private java.lang.String ewfsbj; // 额外访视标记(1是，2否)
	private java.lang.String sfysid; // 随访医生id
	private java.lang.String yyid00; // 医院id
	private java.lang.String kqqkse; // 口腔情况2（保存龋齿数）
	private java.lang.String sly000; // 视力右
	private java.lang.String sfjg00; // 随访结果 1正常 2异常
	private java.lang.String zzks00; // 转诊科室
	private java.lang.String zzbjz0; // 最后编辑者
	private Date zhbjrq; // 最后编辑日期
	private java.lang.String bz0000; // 备注
	private java.lang.String etsjss; // 儿童实际年龄(岁数)
	private java.lang.String etsjys; // 儿童实际年龄(月数)
	private java.lang.String etsjts; // 儿童实际年龄(天数)
	private java.lang.String wi_id; // 导入数据的旧系统编号
	private java.lang.String yscys; // 一岁出牙数
	private java.lang.String cjz000; // 创建者
	private Date cjrq00; // 创建日期
	private java.lang.String tzpjxx; // 体重评价详细(-4.-3SD以下 -3.-3SD～-2SD -2.-2SD～-1SD -1.-1SD～SD 1.SD～+1SD 2.+1SD～+2SD 3.+2SD～+3SD 4.3SD以上)

	private java.lang.String sgpjxx; // 身高评价详细(-4.-3SD以下 -3.-3SD～-2SD -2.-2SD～-1SD -1.-1SD～SD 1.SD～+1SD 2.+1SD～+2SD 3.+2SD～+3SD 4.3SD以上)
	private java.lang.String twpj00; // 头围评价
	private java.lang.String zdjgyy; // 诊断结果:营养不良:1.轻度 2.中度(低体重) 3.中度(发育迟缓)
										// 4.重度(低体重) 5.重度(发育迟缓)
	private java.lang.String zdjgbt; // 诊断结果:扁桃体:1.1,2.2,3.3
	private java.lang.String zdjgqt; // 诊断结果:其他:1.沙眼,2.斜视,3.鼻炎,4.龋齿,5.地图舌,6.鹅口疮7.腭裂,8.湿疹,9.隐睾,a.疝气,b先心,c轻度贫血,d轻度肥胖,e舌系带过短
	private java.lang.String zdjggw; // 诊断结果:高危:1,早产儿2,低出生体重儿3,消瘦4,严重慢性营养不良5,生长检测曲线连续两次平坦或向下倾斜6,中度以上肥胖7,中度以上贫血8,活动期佝偻病9,先天性心脏病a,先天性甲状腺机能低下b,苯丙酮尿症c,听力障碍d,精神发育迟滞0,其他
	private java.lang.String zhpj00; // 综合评价:1.轻度(低体重) 2.轻度（发育迟缓）3.轻度（消瘦）
										// 4.中度(低体重) 5.中度(发育迟缓) 6.中度(消瘦)
										// 7.重度(低体重) 8.重度(发育迟缓) 9.重度(消瘦) 10.超重
										// 11.重度肥胖 12.轻度贫血 13.中度贫血 14.重度贫血
										// 15.早期佝偻病 16.活动期佝偻病 17 恢复期 18后遗症期
	private java.lang.String zhpjna; // 综合评价:保存中文
	private java.lang.String yypj00; // 营养评价:1差,2中,3好
	private java.lang.String yypjna; //
	private java.lang.String delmak; // 删除标记
	private java.lang.String issc00; // 测量方式
	private java.lang.String sctzpj; // 体重/身高(身长)标准差(-4.-3SD以下 -3.-3SD～-2SD -2.-2SD～-1SD -1.-1SD～SD 1.SD～+1SD 2.+1SD～+2SD 3.+2SD～+3SD 4.3SD以上)
	private java.lang.String yygs00; // 营养过剩评价(0正常,1超重,2肥胖)
	private java.lang.String gwidyy; // 营养不良高危档案id
	private java.lang.String gwidpx; // 贫血高危档案id
	private java.lang.String gwidgl; // 佝偻病高危档案id
	private java.lang.String gwiddj; // 登记高危档案id
	private java.lang.String gwpg00; // 高危评估
	private java.lang.String czwt00; // 高危存在问题
	private java.lang.String gwzd00; // 高危指导
	private java.lang.String gwzl00; // 治疗（药物、剂量）
	private java.lang.String gwvdzl; // VitD治疗(品名、剂量)
	private java.lang.String czwtpx; // 存在问题贫血
	private java.lang.String gwzdpx; // 高危指导贫血
	private java.lang.String czwtgl; // 存在问题佝偻
	private java.lang.String gwzdgl; // 高危指导佝偻
	private java.lang.String twbzc0; // 头围标准差(-4.-3SD以下 -3.-3SD～-2SD -2.-2SD～-1SD -1.-1SD～SD 1.SD～+1SD 2.+1SD～+2SD 3.+2SD～+3SD 4.3SD以上)
	private java.lang.String yeyid0; // 幼儿园id
	private java.lang.String bjid00; // 班级id
	private java.lang.String zyjkzd; //中医指导情况（1--中医饮食调养指导，2--中医起居调摄指导，3--传授摩腹、捏脊方法，4--传授按揉迎香穴、足三里穴方法，5--传授按揉四神聪穴方法，6--其他）
	private java.lang.String ysty00; // 中医饮食调养
	private java.lang.String qjts00; // 中医起居调摄
	private java.lang.String xwarff; // 传授家长穴位按揉方法
	private java.lang.String zwqk00; // 家长掌握中医药保健知识和操作方法情况
	private java.lang.String myd000; // 对医生的中医药预防保健指导是否满意
	private java.lang.String xl0000; // 心率(次/min)
	private java.lang.String zzdpx0; // 中重度贫血（1-是，0-否）
	private java.lang.String gwidza; // 高危儿童专案管理记录id
	private java.lang.String pgjg00; // 评估结果
	private java.lang.String pgjgcl; // 处理
	private java.lang.String pgffxl; // 高危儿童及心理发育异常儿童登记表 -- 评估方法
	private java.lang.String pgjgxl; // 高危儿童及心理发育异常儿童登记表 -- 评估结果
	private java.lang.String gwzdxl; // 高危儿童及心理发育异常儿童登记表 -- 指导
	private java.lang.String gwzdcl; // 高危儿童及心理发育异常儿童登记表 -- 处理
	private java.lang.String gwidxl; // 心理发育异常高危档案id
	private java.lang.String sfwc00; // 是否外出(1是2否)
	private java.lang.String pgffza; // 高危儿童及心理发育异常儿童登记表 -- 评估方法
	private java.lang.String pgjgza; // 高危儿童及心理发育异常儿童登记表 -- 评估结果
	private java.lang.String zd00za; // 高危儿童专案管理记录 -- 指导
	private java.lang.String cl00za; // 高危儿童专案管理记录 -- 处理
	private java.lang.String remark; // 备注
	private java.lang.String tsnan0; //
	private java.lang.String tsnv00; //
	private java.lang.String ts0000; //
	private java.lang.String scbz00; // 上传标志
	private java.lang.String scxtbhtemp; // 导数据使用
	private java.lang.String qdpx00; // 轻度贫血
	private java.lang.String kqzk00; // 口腔状况
	// private String operate_source;//操作来源(1.家签app2.家签微信端)
	public java.lang.String getXtbhsf() {
		return xtbhsf;
	}
	public void setXtbhsf(java.lang.String xtbhsf) {
		this.xtbhsf = xtbhsf;
	}
	public java.lang.String getScxtbh() {
		return scxtbh;
	}
	public void setScxtbh(java.lang.String scxtbh) {
		this.scxtbh = scxtbh;
	}
	public java.lang.String getSsnnz0() {
		return ssnnz0;
	}
	public void setSsnnz0(java.lang.String ssnnz0) {
		this.ssnnz0 = ssnnz0;
	}
	public java.lang.String getXmxl00() {
		return xmxl00;
	}
	public void setXmxl00(java.lang.String xmxl00) {
		this.xmxl00 = xmxl00;
	}
	public java.math.BigDecimal getEtsg00() {
		return etsg00;
	}
	public void setEtsg00(java.math.BigDecimal etsg00) {
		this.etsg00 = etsg00;
	}
	public java.math.BigDecimal getEttz00() {
		return ettz00;
	}
	public void setEttz00(java.math.BigDecimal ettz00) {
		this.ettz00 = ettz00;
	}
	public java.lang.String getSgqk00() {
		return sgqk00;
	}
	public void setSgqk00(java.lang.String sgqk00) {
		this.sgqk00 = sgqk00;
	}
	public java.lang.String getTzqk00() {
		return tzqk00;
	}
	public void setTzqk00(java.lang.String tzqk00) {
		this.tzqk00 = tzqk00;
	}
	public java.lang.String getTw0000() {
		return tw0000;
	}
	public void setTw0000(java.lang.String tw0000) {
		this.tw0000 = tw0000;
	}
	public java.lang.String getWyfs00() {
		return wyfs00;
	}
	public void setWyfs00(java.lang.String wyfs00) {
		this.wyfs00 = wyfs00;
	}
	public java.lang.String getCnl000() {
		return cnl000;
	}
	public void setCnl000(java.lang.String cnl000) {
		this.cnl000 = cnl000;
	}
	public java.lang.String getCncs00() {
		return cncs00;
	}
	public void setCncs00(java.lang.String cncs00) {
		this.cncs00 = cncs00;
	}
	public java.lang.String getOt0000() {
		return ot0000;
	}
	public void setOt0000(java.lang.String ot0000) {
		this.ot0000 = ot0000;
	}
	public java.lang.String getDb0000() {
		return db0000;
	}
	public void setDb0000(java.lang.String db0000) {
		this.db0000 = db0000;
	}
	public java.lang.String getDbcs00() {
		return dbcs00;
	}
	public void setDbcs00(java.lang.String dbcs00) {
		this.dbcs00 = dbcs00;
	}
	public java.lang.String getTwqk00() {
		return twqk00;
	}
	public void setTwqk00(java.lang.String twqk00) {
		this.twqk00 = twqk00;
	}
	public java.lang.String getMl0000() {
		return ml0000;
	}
	public void setMl0000(java.lang.String ml0000) {
		this.ml0000 = ml0000;
	}
	public java.lang.String getHxpl00() {
		return hxpl00;
	}
	public void setHxpl00(java.lang.String hxpl00) {
		this.hxpl00 = hxpl00;
	}
	public java.lang.String getMsqk00() {
		return msqk00;
	}
	public void setMsqk00(java.lang.String msqk00) {
		this.msqk00 = msqk00;
	}
	public java.lang.String getHhbw00() {
		return hhbw00;
	}
	public void setHhbw00(java.lang.String hhbw00) {
		this.hhbw00 = hhbw00;
	}
	public java.lang.String getQcone0() {
		return qcone0;
	}
	public void setQcone0(java.lang.String qcone0) {
		this.qcone0 = qcone0;
	}
	public java.lang.String getQctwo0() {
		return qctwo0;
	}
	public void setQctwo0(java.lang.String qctwo0) {
		this.qctwo0 = qctwo0;
	}
	public java.lang.String getQcqk00() {
		return qcqk00;
	}
	public void setQcqk00(java.lang.String qcqk00) {
		this.qcqk00 = qcqk00;
	}
	public java.lang.String getEyeqk0() {
		return eyeqk0;
	}
	public void setEyeqk0(java.lang.String eyeqk0) {
		this.eyeqk0 = eyeqk0;
	}
	public java.lang.String getEarqk0() {
		return earqk0;
	}
	public void setEarqk0(java.lang.String earqk0) {
		this.earqk0 = earqk0;
	}
	public java.lang.String getNoseqk() {
		return noseqk;
	}
	public void setNoseqk(java.lang.String noseqk) {
		this.noseqk = noseqk;
	}
	public java.lang.String getKqqk00() {
		return kqqk00;
	}
	public void setKqqk00(java.lang.String kqqk00) {
		this.kqqk00 = kqqk00;
	}
	public java.lang.String getXftz00() {
		return xftz00;
	}
	public void setXftz00(java.lang.String xftz00) {
		this.xftz00 = xftz00;
	}
	public java.lang.String getFbcz00() {
		return fbcz00;
	}
	public void setFbcz00(java.lang.String fbcz00) {
		this.fbcz00 = fbcz00;
	}
	public java.lang.String getSzhdqk() {
		return szhdqk;
	}
	public void setSzhdqk(java.lang.String szhdqk) {
		this.szhdqk = szhdqk;
	}
	public java.lang.String getJbbkqk() {
		return jbbkqk;
	}
	public void setJbbkqk(java.lang.String jbbkqk) {
		this.jbbkqk = jbbkqk;
	}
	public java.lang.String getSkinqk() {
		return skinqk;
	}
	public void setSkinqk(java.lang.String skinqk) {
		this.skinqk = skinqk;
	}
	public java.lang.String getGmqk00() {
		return gmqk00;
	}
	public void setGmqk00(java.lang.String gmqk00) {
		this.gmqk00 = gmqk00;
	}
	public java.lang.String getWszqqk() {
		return wszqqk;
	}
	public void setWszqqk(java.lang.String wszqqk) {
		this.wszqqk = wszqqk;
	}
	public java.lang.String getJzqk00() {
		return jzqk00;
	}
	public void setJzqk00(java.lang.String jzqk00) {
		this.jzqk00 = jzqk00;
	}
	public java.lang.String getQdqk00() {
		return qdqk00;
	}
	public void setQdqk00(java.lang.String qdqk00) {
		this.qdqk00 = qdqk00;
	}
	public java.lang.String getTl0000() {
		return tl0000;
	}
	public void setTl0000(java.lang.String tl0000) {
		this.tl0000 = tl0000;
	}
	public java.lang.String getGlbzz0() {
		return glbzz0;
	}
	public void setGlbzz0(java.lang.String glbzz0) {
		this.glbzz0 = glbzz0;
	}
	public java.lang.String getGlbtz0() {
		return glbtz0;
	}
	public void setGlbtz0(java.lang.String glbtz0) {
		this.glbtz0 = glbtz0;
	}
	public java.lang.String getXhdb00() {
		return xhdb00;
	}
	public void setXhdb00(java.lang.String xhdb00) {
		this.xhdb00 = xhdb00;
	}
	public java.lang.String getHwhdqk() {
		return hwhdqk;
	}
	public void setHwhdqk(java.lang.String hwhdqk) {
		this.hwhdqk = hwhdqk;
	}
	public java.lang.String getWss000() {
		return wss000;
	}
	public void setWss000(java.lang.String wss000) {
		this.wss000 = wss000;
	}
	public java.lang.String getFypgqk() {
		return fypgqk;
	}
	public void setFypgqk(java.lang.String fypgqk) {
		this.fypgqk = fypgqk;
	}
	public java.lang.String getHbqk00() {
		return hbqk00;
	}
	public void setHbqk00(java.lang.String hbqk00) {
		this.hbqk00 = hbqk00;
	}
	public java.lang.String getHbqkfy() {
		return hbqkfy;
	}
	public void setHbqkfy(java.lang.String hbqkfy) {
		this.hbqkfy = hbqkfy;
	}
	public java.lang.String getHbqkfx() {
		return hbqkfx;
	}
	public void setHbqkfx(java.lang.String hbqkfx) {
		this.hbqkfx = hbqkfx;
	}
	public java.lang.String getHbqkws() {
		return hbqkws;
	}
	public void setHbqkws(java.lang.String hbqkws) {
		this.hbqkws = hbqkws;
	}
	public java.lang.String getHbqkqt() {
		return hbqkqt;
	}
	public void setHbqkqt(java.lang.String hbqkqt) {
		this.hbqkqt = hbqkqt;
	}
	public java.lang.String getQt0000() {
		return qt0000;
	}
	public void setQt0000(java.lang.String qt0000) {
		this.qt0000 = qt0000;
	}
	public java.lang.String getBtqk00() {
		return btqk00;
	}
	public void setBtqk00(java.lang.String btqk00) {
		this.btqk00 = btqk00;
	}
	public java.lang.String getTgfypj() {
		return tgfypj;
	}
	public void setTgfypj(java.lang.String tgfypj) {
		this.tgfypj = tgfypj;
	}
	public java.lang.String getSl0000() {
		return sl0000;
	}
	public void setSl0000(java.lang.String sl0000) {
		this.sl0000 = sl0000;
	}
	public java.lang.String getZdqk00() {
		return zdqk00;
	}
	public void setZdqk00(java.lang.String zdqk00) {
		this.zdqk00 = zdqk00;
	}
	public java.lang.String getZzqk00() {
		return zzqk00;
	}
	public void setZzqk00(java.lang.String zzqk00) {
		this.zzqk00 = zzqk00;
	}
	public java.lang.String getZzyy00() {
		return zzyy00;
	}
	public void setZzyy00(java.lang.String zzyy00) {
		this.zzyy00 = zzyy00;
	}
	public java.lang.String getZzjgks() {
		return zzjgks;
	}
	public void setZzjgks(java.lang.String zzjgks) {
		this.zzjgks = zzjgks;
	}
	public Date getXcsfrq() {
		return xcsfrq;
	}
	public void setXcsfrq(Date xcsfrq) {
		this.xcsfrq = xcsfrq;
	}
	public java.lang.String getXcsfdd() {
		return xcsfdd;
	}
	public void setXcsfdd(java.lang.String xcsfdd) {
		this.xcsfdd = xcsfdd;
	}
	public Date getSfrq00() {
		return sfrq00;
	}
	public void setSfrq00(Date sfrq00) {
		this.sfrq00 = sfrq00;
	}
	public java.lang.String getSfys00() {
		return sfys00;
	}
	public void setSfys00(java.lang.String sfys00) {
		this.sfys00 = sfys00;
	}
	public java.lang.String getEwfsbj() {
		return ewfsbj;
	}
	public void setEwfsbj(java.lang.String ewfsbj) {
		this.ewfsbj = ewfsbj;
	}
	public java.lang.String getSfysid() {
		return sfysid;
	}
	public void setSfysid(java.lang.String sfysid) {
		this.sfysid = sfysid;
	}
	public java.lang.String getYyid00() {
		return yyid00;
	}
	public void setYyid00(java.lang.String yyid00) {
		this.yyid00 = yyid00;
	}
	public java.lang.String getKqqkse() {
		return kqqkse;
	}
	public void setKqqkse(java.lang.String kqqkse) {
		this.kqqkse = kqqkse;
	}
	public java.lang.String getSly000() {
		return sly000;
	}
	public void setSly000(java.lang.String sly000) {
		this.sly000 = sly000;
	}
	public java.lang.String getSfjg00() {
		return sfjg00;
	}
	public void setSfjg00(java.lang.String sfjg00) {
		this.sfjg00 = sfjg00;
	}
	public java.lang.String getZzks00() {
		return zzks00;
	}
	public void setZzks00(java.lang.String zzks00) {
		this.zzks00 = zzks00;
	}
	public java.lang.String getZzbjz0() {
		return zzbjz0;
	}
	public void setZzbjz0(java.lang.String zzbjz0) {
		this.zzbjz0 = zzbjz0;
	}
	public Date getZhbjrq() {
		return zhbjrq;
	}
	public void setZhbjrq(Date zhbjrq) {
		this.zhbjrq = zhbjrq;
	}
	public java.lang.String getBz0000() {
		return bz0000;
	}
	public void setBz0000(java.lang.String bz0000) {
		this.bz0000 = bz0000;
	}
	public java.lang.String getEtsjss() {
		return etsjss;
	}
	public void setEtsjss(java.lang.String etsjss) {
		this.etsjss = etsjss;
	}
	public java.lang.String getEtsjys() {
		return etsjys;
	}
	public void setEtsjys(java.lang.String etsjys) {
		this.etsjys = etsjys;
	}
	public java.lang.String getEtsjts() {
		return etsjts;
	}
	public void setEtsjts(java.lang.String etsjts) {
		this.etsjts = etsjts;
	}
	public java.lang.String getWi_id() {
		return wi_id;
	}
	public void setWi_id(java.lang.String wi_id) {
		this.wi_id = wi_id;
	}
	public java.lang.String getYscys() {
		return yscys;
	}
	public void setYscys(java.lang.String yscys) {
		this.yscys = yscys;
	}
	public java.lang.String getCjz000() {
		return cjz000;
	}
	public void setCjz000(java.lang.String cjz000) {
		this.cjz000 = cjz000;
	}
	public Date getCjrq00() {
		return cjrq00;
	}
	public void setCjrq00(Date cjrq00) {
		this.cjrq00 = cjrq00;
	}
	public java.lang.String getTzpjxx() {
		return tzpjxx;
	}
	public void setTzpjxx(java.lang.String tzpjxx) {
		this.tzpjxx = tzpjxx;
	}
	public java.lang.String getSgpjxx() {
		return sgpjxx;
	}
	public void setSgpjxx(java.lang.String sgpjxx) {
		this.sgpjxx = sgpjxx;
	}
	public java.lang.String getTwpj00() {
		return twpj00;
	}
	public void setTwpj00(java.lang.String twpj00) {
		this.twpj00 = twpj00;
	}
	public java.lang.String getZdjgyy() {
		return zdjgyy;
	}
	public void setZdjgyy(java.lang.String zdjgyy) {
		this.zdjgyy = zdjgyy;
	}
	public java.lang.String getZdjgbt() {
		return zdjgbt;
	}
	public void setZdjgbt(java.lang.String zdjgbt) {
		this.zdjgbt = zdjgbt;
	}
	public java.lang.String getZdjgqt() {
		return zdjgqt;
	}
	public void setZdjgqt(java.lang.String zdjgqt) {
		this.zdjgqt = zdjgqt;
	}
	public java.lang.String getZdjggw() {
		return zdjggw;
	}
	public void setZdjggw(java.lang.String zdjggw) {
		this.zdjggw = zdjggw;
	}
	public java.lang.String getZhpj00() {
		return zhpj00;
	}
	public void setZhpj00(java.lang.String zhpj00) {
		this.zhpj00 = zhpj00;
	}
	public java.lang.String getZhpjna() {
		return zhpjna;
	}
	public void setZhpjna(java.lang.String zhpjna) {
		this.zhpjna = zhpjna;
	}
	public java.lang.String getYypj00() {
		return yypj00;
	}
	public void setYypj00(java.lang.String yypj00) {
		this.yypj00 = yypj00;
	}
	public java.lang.String getYypjna() {
		return yypjna;
	}
	public void setYypjna(java.lang.String yypjna) {
		this.yypjna = yypjna;
	}
	public java.lang.String getDelmak() {
		return delmak;
	}
	public void setDelmak(java.lang.String delmak) {
		this.delmak = delmak;
	}
	public java.lang.String getIssc00() {
		return issc00;
	}
	public void setIssc00(java.lang.String issc00) {
		this.issc00 = issc00;
	}
	public java.lang.String getSctzpj() {
		return sctzpj;
	}
	public void setSctzpj(java.lang.String sctzpj) {
		this.sctzpj = sctzpj;
	}
	public java.lang.String getYygs00() {
		return yygs00;
	}
	public void setYygs00(java.lang.String yygs00) {
		this.yygs00 = yygs00;
	}
	public java.lang.String getGwidyy() {
		return gwidyy;
	}
	public void setGwidyy(java.lang.String gwidyy) {
		this.gwidyy = gwidyy;
	}
	public java.lang.String getGwidpx() {
		return gwidpx;
	}
	public void setGwidpx(java.lang.String gwidpx) {
		this.gwidpx = gwidpx;
	}
	public java.lang.String getGwidgl() {
		return gwidgl;
	}
	public void setGwidgl(java.lang.String gwidgl) {
		this.gwidgl = gwidgl;
	}
	public java.lang.String getGwiddj() {
		return gwiddj;
	}
	public void setGwiddj(java.lang.String gwiddj) {
		this.gwiddj = gwiddj;
	}
	public java.lang.String getGwpg00() {
		return gwpg00;
	}
	public void setGwpg00(java.lang.String gwpg00) {
		this.gwpg00 = gwpg00;
	}
	public java.lang.String getCzwt00() {
		return czwt00;
	}
	public void setCzwt00(java.lang.String czwt00) {
		this.czwt00 = czwt00;
	}
	public java.lang.String getGwzd00() {
		return gwzd00;
	}
	public void setGwzd00(java.lang.String gwzd00) {
		this.gwzd00 = gwzd00;
	}
	public java.lang.String getGwzl00() {
		return gwzl00;
	}
	public void setGwzl00(java.lang.String gwzl00) {
		this.gwzl00 = gwzl00;
	}
	public java.lang.String getGwvdzl() {
		return gwvdzl;
	}
	public void setGwvdzl(java.lang.String gwvdzl) {
		this.gwvdzl = gwvdzl;
	}
	public java.lang.String getCzwtpx() {
		return czwtpx;
	}
	public void setCzwtpx(java.lang.String czwtpx) {
		this.czwtpx = czwtpx;
	}
	public java.lang.String getGwzdpx() {
		return gwzdpx;
	}
	public void setGwzdpx(java.lang.String gwzdpx) {
		this.gwzdpx = gwzdpx;
	}
	public java.lang.String getCzwtgl() {
		return czwtgl;
	}
	public void setCzwtgl(java.lang.String czwtgl) {
		this.czwtgl = czwtgl;
	}
	public java.lang.String getGwzdgl() {
		return gwzdgl;
	}
	public void setGwzdgl(java.lang.String gwzdgl) {
		this.gwzdgl = gwzdgl;
	}
	public java.lang.String getTwbzc0() {
		return twbzc0;
	}
	public void setTwbzc0(java.lang.String twbzc0) {
		this.twbzc0 = twbzc0;
	}
	public java.lang.String getYeyid0() {
		return yeyid0;
	}
	public void setYeyid0(java.lang.String yeyid0) {
		this.yeyid0 = yeyid0;
	}
	public java.lang.String getBjid00() {
		return bjid00;
	}
	public void setBjid00(java.lang.String bjid00) {
		this.bjid00 = bjid00;
	}
	public java.lang.String getZyjkzd() {
		return zyjkzd;
	}
	public void setZyjkzd(java.lang.String zyjkzd) {
		this.zyjkzd = zyjkzd;
	}
	public java.lang.String getYsty00() {
		return ysty00;
	}
	public void setYsty00(java.lang.String ysty00) {
		this.ysty00 = ysty00;
	}
	public java.lang.String getQjts00() {
		return qjts00;
	}
	public void setQjts00(java.lang.String qjts00) {
		this.qjts00 = qjts00;
	}
	public java.lang.String getXwarff() {
		return xwarff;
	}
	public void setXwarff(java.lang.String xwarff) {
		this.xwarff = xwarff;
	}
	public java.lang.String getZwqk00() {
		return zwqk00;
	}
	public void setZwqk00(java.lang.String zwqk00) {
		this.zwqk00 = zwqk00;
	}
	public java.lang.String getMyd000() {
		return myd000;
	}
	public void setMyd000(java.lang.String myd000) {
		this.myd000 = myd000;
	}
	public java.lang.String getXl0000() {
		return xl0000;
	}
	public void setXl0000(java.lang.String xl0000) {
		this.xl0000 = xl0000;
	}
	public java.lang.String getZzdpx0() {
		return zzdpx0;
	}
	public void setZzdpx0(java.lang.String zzdpx0) {
		this.zzdpx0 = zzdpx0;
	}
	public java.lang.String getGwidza() {
		return gwidza;
	}
	public void setGwidza(java.lang.String gwidza) {
		this.gwidza = gwidza;
	}
	public java.lang.String getPgjg00() {
		return pgjg00;
	}
	public void setPgjg00(java.lang.String pgjg00) {
		this.pgjg00 = pgjg00;
	}
	public java.lang.String getPgjgcl() {
		return pgjgcl;
	}
	public void setPgjgcl(java.lang.String pgjgcl) {
		this.pgjgcl = pgjgcl;
	}
	public java.lang.String getPgffxl() {
		return pgffxl;
	}
	public void setPgffxl(java.lang.String pgffxl) {
		this.pgffxl = pgffxl;
	}
	public java.lang.String getPgjgxl() {
		return pgjgxl;
	}
	public void setPgjgxl(java.lang.String pgjgxl) {
		this.pgjgxl = pgjgxl;
	}
	public java.lang.String getGwzdxl() {
		return gwzdxl;
	}
	public void setGwzdxl(java.lang.String gwzdxl) {
		this.gwzdxl = gwzdxl;
	}
	public java.lang.String getGwzdcl() {
		return gwzdcl;
	}
	public void setGwzdcl(java.lang.String gwzdcl) {
		this.gwzdcl = gwzdcl;
	}
	public java.lang.String getGwidxl() {
		return gwidxl;
	}
	public void setGwidxl(java.lang.String gwidxl) {
		this.gwidxl = gwidxl;
	}
	public java.lang.String getSfwc00() {
		return sfwc00;
	}
	public void setSfwc00(java.lang.String sfwc00) {
		this.sfwc00 = sfwc00;
	}
	public java.lang.String getPgffza() {
		return pgffza;
	}
	public void setPgffza(java.lang.String pgffza) {
		this.pgffza = pgffza;
	}
	public java.lang.String getPgjgza() {
		return pgjgza;
	}
	public void setPgjgza(java.lang.String pgjgza) {
		this.pgjgza = pgjgza;
	}
	public java.lang.String getZd00za() {
		return zd00za;
	}
	public void setZd00za(java.lang.String zd00za) {
		this.zd00za = zd00za;
	}
	public java.lang.String getCl00za() {
		return cl00za;
	}
	public void setCl00za(java.lang.String cl00za) {
		this.cl00za = cl00za;
	}
	public java.lang.String getRemark() {
		return remark;
	}
	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}
	public java.lang.String getTsnan0() {
		return tsnan0;
	}
	public void setTsnan0(java.lang.String tsnan0) {
		this.tsnan0 = tsnan0;
	}
	public java.lang.String getTsnv00() {
		return tsnv00;
	}
	public void setTsnv00(java.lang.String tsnv00) {
		this.tsnv00 = tsnv00;
	}
	public java.lang.String getTs0000() {
		return ts0000;
	}
	public void setTs0000(java.lang.String ts0000) {
		this.ts0000 = ts0000;
	}
	public java.lang.String getScbz00() {
		return scbz00;
	}
	public void setScbz00(java.lang.String scbz00) {
		this.scbz00 = scbz00;
	}
	public java.lang.String getScxtbhtemp() {
		return scxtbhtemp;
	}
	public void setScxtbhtemp(java.lang.String scxtbhtemp) {
		this.scxtbhtemp = scxtbhtemp;
	}
	public java.lang.String getQdpx00() {
		return qdpx00;
	}
	public void setQdpx00(java.lang.String qdpx00) {
		this.qdpx00 = qdpx00;
	}
	public java.lang.String getKqzk00() {
		return kqzk00;
	}
	public void setKqzk00(java.lang.String kqzk00) {
		this.kqzk00 = kqzk00;
	}

	
}
