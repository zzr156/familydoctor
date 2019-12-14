/*
 * CopyRight: StartTech 2010 cop. ltd.
 * All Right Reverse.
 * Class Name: <code>Fy_fncqjcDTO</code>.
 */
package com.ylz.bizDo.assessment.vo.view;

import java.io.Serializable;
import java.sql.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * <p>
 * Title: Fy_fncqjcDTO
 * </p>
 * <p>
 * Description:产前检查记录表
 * </p>
 * <p>
 * Copyright:Copyright(c)2010
 * </p>
 * <p>
 * Company:易联众技术股份有限公司
 * </p>
 *
 * @author
 * @version 1.0
 */

public class Fy_fncqjcDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2075066569245737600L;

	/**
	 * 默认构造函数
	 */
	public Fy_fncqjcDTO() {
	}

	private java.lang.String xtbhcq; // 产前检查编号
	private java.lang.String scxtbh; // 手册系统编号
	private java.lang.String xm0000; // 姓名
	private java.sql.Date csrq00; // 出生日期
	private java.math.BigDecimal sznl00; // 实足年龄
	private java.lang.String sfzh00; // 身份证号
	private java.lang.String zy0000; // 职业
	private java.lang.String whcd00; // 文化程度
	private java.lang.String gzdw00; // 工作单位
	private java.lang.String jg0000; // 籍贯
	private java.lang.String lxdh00; // 联系电话
	private java.lang.String hkdshe; // 户口所在地：省
	private java.lang.String hkdshi; // 户口所在地：市
	private java.lang.String hkdxia; // 户口所在地：县(区)
	private java.lang.String hkdzhe; // 户口所在地：镇(乡)
	private java.lang.String hkdcun; // 户口所在地：村(居)
	private java.lang.String hkdmph; // 户口所在地：门牌号
	private java.lang.String xzdshe; // 现住地：省
	private java.lang.String xzdshi; // 现住地：市
	private java.lang.String xzdxia; // 现住地：县(区)
	private java.lang.String xzdzhe; // 现住地：镇(乡)
	private java.lang.String xzdcun; // 现住地：村(居)
	private java.lang.String xzdmph; // 现住地：门牌号
	private java.lang.String zfxm00; // 丈夫姓名
	private java.math.BigDecimal zfnl00; // 丈夫年龄
	private java.lang.String zfgzdw; // 丈夫工作单位
	private java.lang.String zfjkqk; // 丈夫健康情况
	private java.sql.Date mcyj00; // 婚姻史：末次月经(为空表示“不详”)
	private java.math.BigDecimal jhnl00; // 婚姻史：结婚年龄
	private java.lang.String qyjh00; // 婚姻史：亲缘结婚（1有，2 无）
	private java.lang.String yjs000; // 婚姻史：月经史
	private java.sql.Date ycq000; // 婚姻史：预产期
	private java.math.BigDecimal yunci0; // 婚姻史：孕次
	private java.math.BigDecimal ccydfm; // 婚姻史：产次：阴道分娩
	private java.math.BigDecimal ccpgc0; // 婚姻史：产次：剖宫产
	private java.lang.String rsfy00; // 现孕史:妊娠反应（1有，2 无）有的话填写月
	private java.lang.String cgtd00; // 现孕史：初感胎动（1感，2未感）
	private java.lang.String jt0000; // 现孕史：剧吐
	private java.lang.String ydcx00; // 现孕史：阴道出血
	private java.lang.String fr0000; // 现孕史：发热
	private java.lang.String gm0000; // 现孕史：过敏
	private java.lang.String fy0000; // 现孕史：服药
	private java.lang.String bfgr00; // 现孕史：病毒感染
	private java.lang.String jcyhwz; // 现孕史：接触有害物质
	private java.lang.String fbyy0; // 现孕史：服避孕药
	private java.lang.String xysqt0; // 现孕史：其他：
	private java.lang.String rsdyrq; // 妊娠史：第一胎日期
	private java.lang.String rsdyqk; // 妊娠史：第一胎情况
	private java.lang.String rsderq; // 妊娠史：第二胎日期
	private java.lang.String rsdeqk; // 妊娠史：第二胎情况
	private java.lang.String rsdsrq; // 妊娠史：第三胎日期
	private java.lang.String rsdsqk; // 妊娠史：第三胎情况
	private java.lang.String rsstrq; // 妊娠史：第四胎日期
	private java.lang.String rsstqk; // 妊娠史：第四胎情况
	private java.lang.String jwsx00; // 既往史：心
	private java.lang.String jwsf00; // 既往史：肺
	private java.lang.String jwsg00; // 既往史：肝
	private java.lang.String jwss00; // 既往史：肾
	private java.lang.String jwsgxy; // 既往史：高血压
	private java.lang.String jwstnb; // 既往史：糖尿病
	private java.lang.String jwsjk0; // 既往史：甲亢
	private java.lang.String jwsgms; // 既往史：过敏史
	private java.lang.String jwsjsb; // 既往史：精神病
	private java.lang.String jwsxyb; // 既往史：血液病
	private java.lang.String jwsdx0; // 既往史：癫痫
	private java.lang.String jwssss; // 既往史：手术史
	private java.lang.String jwsqt0; // 既往史：其他
	private java.lang.String jzsbr0; // 家族史：本人
	private java.lang.String jzsar0; // 家族史：爱人
	private java.lang.String tjjcxy; // 体检：基础血压
	private java.lang.String tjxy00; // 体检：血压
	private java.lang.String tjsg00; // 体检：身高
	private java.lang.String tjtz00; // 体检：体重
	private java.lang.String tjtzzs; // 体检：体重指数
	private java.lang.String tjjzx0; // 体检：甲状腺
	private java.lang.String tjx000; // 体检：心
	private java.lang.String tjf000; // 体检：肺
	private java.lang.String tjp000; // 体检：脾
	private java.lang.String tjs000; // 体检：肾
	private java.lang.String tjrt00; // 体检：乳头
	private java.lang.String tjjzsz; // 体检：脊柱四肢
	private java.lang.String tjfz00; // 体检：浮肿
	private java.lang.String tjjfs0; // 体检：腱反射
	private java.lang.String tjjmqz; // 体检：静脉曲张
	private java.lang.String tjqt00; // 体检：其他
	private java.lang.String fjwy00; // 妇检：外阴
	private java.lang.String fjyd00; // 妇检：阴道
	private java.lang.String fjgj00; // 妇检：宫颈
	private java.lang.String fjgt00; // 妇检：宫体
	private java.lang.String fjfj00; // 妇检：附件
	private java.lang.String cjgjjj; // 产检：骨盘测量：骼棘间径
	private java.lang.String cjgj00; // 产检：骨盘测量：骼嵴间径
	private java.lang.String cjdcwj; // 产检：骨盘测量：骶耻外径
	private java.lang.String cjzgjj; // 产检：骨盘测量：坐骨结节间径
	private java.lang.String fjxdb0; // 辅助检查：血红蛋白
	private java.lang.String fjncg0; // 辅助检查：尿常规
	private java.lang.String fjbcg0; // 辅助检查：白带常规
	private java.lang.String fjxx00; // 辅助检查：血型
	private java.lang.String fjrpr0; // 辅助检查：RPR
	private java.lang.String fjhiv0; // 辅助检查：HIV
	private java.lang.String fjhbsa; // 辅助检查：HBsAg
	private java.lang.String fjcqsc; // 辅助检查：产前筛查
	private java.lang.String fjsg00; // 辅助检查：肾功
	private java.lang.String fjgg00; // 辅助检查：肝功
	private java.lang.String fjtsc0; // 辅助检查：糖筛查
	private java.lang.String fjqt00; // 辅助检查：其他
	private java.lang.String fjbc00; // 辅助检查：B超
	private java.lang.String fjxdt0; // 辅助检查：心电图
	private java.lang.String bjzd00; // 保健指导
	private java.lang.String czzdg0; // 初诊诊断G
	private java.lang.String czzdp0; // 初诊诊断P
	private java.lang.String czzdrs; // 初诊诊断：周宫内妊娠
	private java.lang.String czzdnr; // 初诊诊断：诊断内容
	private java.sql.Date ryrq00; // 入院日期
	private java.lang.String zs0000; // 主诉
	private java.lang.String ryzd00; // 入院诊断
	private java.lang.String cl0000; // 处理
	private java.lang.String zz0000; // 转诊
	private java.lang.String jcdw00; // 检查单位
	private java.sql.Date jcrq00; // 检查日期
	private java.lang.String zyh000; // 住院号
	private java.lang.String ckmzbh; // 产科门诊编号
	private java.lang.String rsdytc; // 妊娠史：胎次一
	private java.lang.String rsdetc; // 妊娠史：胎次二
	private java.lang.String rsdstc; // 妊娠史：胎次三
	private java.lang.String rssttc; // 妊娠史：胎次四
	private java.lang.String bsxwz0; // 病史询问者
	private java.lang.String jcz000; // 检查者
	private java.lang.String hkzt00; // 户口状态
	private java.lang.String qm0000; // 签名
	private java.lang.String jcdwna; // 检查单位名称
	private java.lang.String jcys00; // 检查医生
	private java.lang.String jcysna; // 检查医生名称
	private String selorgid;
	private String lrzxm0;//录入者姓名
	private String jczxm0;//检查者姓名
	private java.lang.String fjxxrh;//血型RH
	private java.lang.String ggngc0;//血清谷草转氨酶(U/L)
	private java.lang.String ggnbdb;//血蛋白(g/L)
	private java.lang.String ggnzdh;//总胆红素(umol/L)
	private java.lang.String ggnjhd;//结合胆红素(umol/L)
	private java.lang.String sgnxns;//血尿素氮(mmol/L)
	private java.lang.String tbys00;//填表医生
	private java.lang.String zhbjz0;//最后编辑者
	private java.sql.Timestamp zhbjsj;//最后编辑时间
	private java.lang.String tjg000;//体检：肝
	private java.lang.String tbyz00;//填表孕周
	private java.lang.String tbyzts;//填表孕周天数
	private java.lang.String gwys00;//高危因素
	private java.lang.String gwysbh;//高危因素编号
	private java.lang.String gwpf00;//高危评分
	private java.lang.String jzsgxy;//家族史高血压
	private java.lang.String jzstnb;//家族史糖尿病
	private java.lang.String jzsycb;//家族史遗传病
	private java.lang.String jzsjsb;//家族史精神病
	private java.lang.String jzscd0;//家族史痴呆
	private java.lang.String jzsjx0;//家族史畸形
	private java.lang.String jzsqt0;//家族史其他
	private java.lang.String jzsgxya;//家族史高血压(爱人)
	private java.lang.String jzstnba;//家族史糖尿病(爱人)
	private java.lang.String jzsycba;//家族史遗传病(爱人)
	private java.lang.String jzsjsba;//家族史精神病(爱人)
	private java.lang.String jzscd0a;//家族史痴呆(爱人)
	private java.lang.String jzsjx0a;//家族史畸形(爱人)
	private java.lang.String jzsqt0a;//家族史其他(爱人)
	private String hivzx0;//hiv咨询
	private String ncgnt0;
	private String ncgntt;
	private String ncgnqx;
	private String ncgqt0;
	private String ydqjd0;
	private String fjjcgg;//妇检：宫高
	private String fjbg00;//辅助检查：丙肝
	private String scybst;//18三体
	private String sceyst;//21三体
	private String sjgqx0;//神经管缺陷
	private String sbkh00;//社保卡号
	private String cardtype;//卡类型   01  社保卡或居民健康卡    其它  医保卡或一卡通
	private String rsdwtc;//妊娠史：胎次五
	private java.lang.String rsdwrq;//妊娠史：第五胎日期
	private String rsdwqk;//妊娠史：第五胎情况
	private String rsstc6;//妊娠史：胎次六
	private java.lang.String rssrq6;
	private String rssqk6;
	private String rsstc7;//妊娠史：胎次七
	private java.lang.String rssrq7;
	private String rssqk7;
	private String xxsfwz;

	private String ft30dw;//'辅助检查：甲状腺-FT3单位（1、pg/ml；2、pmol/L）
	private String ft40dw;//'辅助检查：甲状腺-FT4单位（1、pg/ml；2、pmol/L）
	private String tsh0dw;//辅助检查：甲状腺-tsh单位（1、uIU/mL；2、Miu/L）
	private String fjjcbz;

	private String hjygbmky; //乙型肝炎表面抗原
    private String hjygbmkt; //乙型肝炎表面抗体
	private String hjygeky0; //乙型肝炎e抗原
	private String hjygekt0; //乙型肝炎e抗体
	private String hjyghxkt; //乙型肝炎核心抗体
	private String xhdbaf; //血红蛋白A+F
	private String xhdbh0; //血红蛋白H
	private String xhdbf0; //血红蛋白F
	private String xhdbs0; //血红蛋白S
	private String xhdba2; //血红蛋白A2
	private String xhdbc0; //血红蛋白C
	private String xhdbba; //血红蛋白Bart's
	private String gxcigg; //弓形虫IgG抗体测定
	private String jxbigm; //巨细胞病毒IgM抗体测定
	private String gxcigm; //弓形虫IgM抗体测定
	private String fzigg0; //风疹病毒IgG抗体测定
	private String jxbigg; //巨细胞病毒IgG抗体测定
	private String sfysjc; //孕前优生检查未检
	private String cjzcd0; //促甲状腺激素测定

	private String syzh00; //生育证号
	private String exigg0;
	private String exigm0;
	private String fzigm0;
	private String mdsx00;
	private String hivsc0;

	private String startdate; //
	private String enddate; //
	private String allorgid; //
	private String allthreeprim; //
	private String allmotoetablename; //
	private String allmiddletablename; //

	private String cjlx00;//产检类型

	public String getCjlx00() {
		return cjlx00;
	}

	public void setCjlx00(String cjlx00) {
		this.cjlx00 = cjlx00;
	}
	public String getAllmiddletablename() {
		return allmiddletablename;
	}
	public void setAllmiddletablename(String allmiddletablename) {
		this.allmiddletablename = allmiddletablename;
	}
	public String getAllmotoetablename() {
		return allmotoetablename;
	}
	public void setAllmotoetablename(String allmotoetablename) {
		this.allmotoetablename = allmotoetablename;
	}
	public String getAllthreeprim() {
		return allthreeprim;
	}
	public void setAllthreeprim(String allthreeprim) {
		this.allthreeprim = allthreeprim;
	}
	public String getAllorgid() {
		return allorgid;
	}
	public void setAllorgid(String allorgid) {
		this.allorgid = allorgid;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getExigg0() {
		return exigg0;
	}
	public void setExigg0(String exigg0) {
		this.exigg0 = exigg0;
	}
	public String getExigm0() {
		return exigm0;
	}
	public void setExigm0(String exigm0) {
		this.exigm0 = exigm0;
	}
	public String getFzigm0() {
		return fzigm0;
	}
	public void setFzigm0(String fzigm0) {
		this.fzigm0 = fzigm0;
	}
	public String getMdsx00() {
		return mdsx00;
	}
	public void setMdsx00(String mdsx00) {
		this.mdsx00 = mdsx00;
	}
	public String getHivsc0() {
		return hivsc0;
	}
	public void setHivsc0(String hivsc0) {
		this.hivsc0 = hivsc0;
	}
	public String getSyzh00() {
		return syzh00;
	}
	public void setSyzh00(String syzh00) {
		this.syzh00 = syzh00;
	}
	public String getHjygbmky() {
		return hjygbmky;
	}
	public void setHjygbmky(String hjygbmky) {
		this.hjygbmky = hjygbmky;
	}
	public String getHjygbmkt() {
		return hjygbmkt;
	}
	public void setHjygbmkt(String hjygbmkt) {
		this.hjygbmkt = hjygbmkt;
	}
	public String getHjygeky0() {
		return hjygeky0;
	}
	public void setHjygeky0(String hjygeky0) {
		this.hjygeky0 = hjygeky0;
	}
	public String getHjygekt0() {
		return hjygekt0;
	}
	public void setHjygekt0(String hjygekt0) {
		this.hjygekt0 = hjygekt0;
	}
	public String getHjyghxkt() {
		return hjyghxkt;
	}
	public void setHjyghxkt(String hjyghxkt) {
		this.hjyghxkt = hjyghxkt;
	}
	public String getXhdbaf() {
		return xhdbaf;
	}
	public void setXhdbaf(String xhdbaf) {
		this.xhdbaf = xhdbaf;
	}
	public String getXhdbh0() {
		return xhdbh0;
	}
	public void setXhdbh0(String xhdbh0) {
		this.xhdbh0 = xhdbh0;
	}
	public String getXhdbf0() {
		return xhdbf0;
	}
	public void setXhdbf0(String xhdbf0) {
		this.xhdbf0 = xhdbf0;
	}
	public String getXhdbs0() {
		return xhdbs0;
	}
	public void setXhdbs0(String xhdbs0) {
		this.xhdbs0 = xhdbs0;
	}
	public String getXhdba2() {
		return xhdba2;
	}
	public void setXhdba2(String xhdba2) {
		this.xhdba2 = xhdba2;
	}
	public String getXhdbc0() {
		return xhdbc0;
	}
	public void setXhdbc0(String xhdbc0) {
		this.xhdbc0 = xhdbc0;
	}
	public String getXhdbba() {
		return xhdbba;
	}
	public void setXhdbba(String xhdbba) {
		this.xhdbba = xhdbba;
	}
	public String getGxcigg() {
		return gxcigg;
	}
	public void setGxcigg(String gxcigg) {
		this.gxcigg = gxcigg;
	}
	public String getJxbigm() {
		return jxbigm;
	}
	public void setJxbigm(String jxbigm) {
		this.jxbigm = jxbigm;
	}
	public String getGxcigm() {
		return gxcigm;
	}
	public void setGxcigm(String gxcigm) {
		this.gxcigm = gxcigm;
	}
	public String getFzigg0() {
		return fzigg0;
	}
	public void setFzigg0(String fzigg0) {
		this.fzigg0 = fzigg0;
	}
	public String getJxbigg() {
		return jxbigg;
	}
	public void setJxbigg(String jxbigg) {
		this.jxbigg = jxbigg;
	}
	public String getSfysjc() {
		return sfysjc;
	}
	public void setSfysjc(String sfysjc) {
		this.sfysjc = sfysjc;
	}
	public String getCjzcd0() {
		return cjzcd0;
	}
	public void setCjzcd0(String cjzcd0) {
		this.cjzcd0 = cjzcd0;
	}
	public String getFjjcbz() {
		return fjjcbz;
	}
	public void setFjjcbz(String fjjcbz) {
		this.fjjcbz = fjjcbz;
	}
	public String getFt30dw() {
		return ft30dw;
	}
	public void setFt30dw(String ft30dw) {
		this.ft30dw = ft30dw;
	}
	public String getFt40dw() {
		return ft40dw;
	}
	public void setFt40dw(String ft40dw) {
		this.ft40dw = ft40dw;
	}
	public String getTsh0dw() {
		return tsh0dw;
	}
	public void setTsh0dw(String tsh0dw) {
		this.tsh0dw = tsh0dw;
	}
	public String getXxsfwz() {
		return xxsfwz;
	}
	public void setXxsfwz(String xxsfwz) {
		this.xxsfwz = xxsfwz;
	}
	public String getRsstc6() {
		return rsstc6;
	}
	public void setRsstc6(String rsstc6) {
		this.rsstc6 = rsstc6;
	}
	public java.lang.String getRssrq6() {
		return rssrq6;
	}
	public void setRssrq6(java.lang.String rssrq6) {
		this.rssrq6 = rssrq6;
	}
	public String getRssqk6() {
		return rssqk6;
	}
	public void setRssqk6(String rssqk6) {
		this.rssqk6 = rssqk6;
	}
	public String getRsstc7() {
		return rsstc7;
	}
	public void setRsstc7(String rsstc7) {
		this.rsstc7 = rsstc7;
	}
	public java.lang.String getRssrq7() {
		return rssrq7;
	}
	public void setRssrq7(java.lang.String rssrq7) {
		this.rssrq7 = rssrq7;
	}
	public String getRssqk7() {
		return rssqk7;
	}
	public void setRssqk7(String rssqk7) {
		this.rssqk7 = rssqk7;
	}
	private java.sql.Date gwyyrq;//高危预约日期
	private String bdjcnq;//脓球
	private String bdjclq;//琳球菌

	private java.sql.Timestamp cjrq00;//创建日期
	private java.lang.String cjz000;//创建者
	private java.lang.String dzxgyy;//错误地址修正医院(外院)
	private java.lang.String dzxgid;//错误地址修正医生id
	private java.lang.String dzxgxm;//错误地址修正姓名
	private java.sql.Timestamp dzxgsj;//错误地址修改时间

	private java.lang.String td0000; // 胎动
	private java.math.BigDecimal ckjcgg; // 产科检查：宫底高度（cm）
	private java.math.BigDecimal ckjcfw; // 产科检查：腹围（cm）
	private java.lang.String ckjctw; // 产科检查：胎位
	private String cktxl0; // 产科检查：胎心率（次/分钟）
	private java.lang.String xl0000; // 先露
	private java.lang.String fz0000; // 浮肿
	private java.lang.String zfsfzh;//丈夫身份证号
	private java.lang.String dhhm00;//电话号码
	private java.lang.String sftz00;//是否通知(0否 1是)
	private java.lang.String sczt00;//手册状态（1使用中，2已归档，3未归档,z通过产前检查自动生成的手册，y表示该手册为假地址）
	private java.lang.String bcts00;//B超推算
	private String fjtsc2;//糖筛查2
	private String kfxt00;//空腹血糖
	private String sfwz00;//是否完整(1是0否)

	private java.sql.Date jcrqQ0; // 检查日期起
	private java.sql.Date jcrqZ0; // 检查日期止


	private String gwyse0;//高危因素2
	private String gwyss0;//高危因素3
	private String gwysbhe;//高危因素编号2
	private String gwysbhs;//高危因素编号3
	private java.math.BigDecimal gwpfe0;//高危评分2
	private java.math.BigDecimal gwyze0;//高危孕周2
	private java.math.BigDecimal gwpfs0;//高危评分3
	private java.math.BigDecimal gwyzs0;//高危孕周3
	private String flag;//标记是否分娩
	private String lcrq00;//流产日期
	private String cxzt00;//查询状态 1使用中  2 已分娩  3 已流产  '' 全部
	private  java.lang.String status; //假删标记(-1：废弃)

	private java.math.BigDecimal xcgbxb;		//辅助检查：血常规-白细胞计数值
	private java.math.BigDecimal xcgxxb;		//辅助检查：血常规-血小板计数值
	private String xcgqt0;		//辅助检查：血常规-其他
	private String ygbmky;		//辅助检查：乙型肝炎五项-乙型肝炎表面抗原
	private String ygbmkt;		//辅助检查：乙型肝炎五项-乙型肝炎表面抗体
	private String ygeky0;		//辅助检查：乙型肝炎五项-乙型肝炎e抗原
	private String ygekt0;		//辅助检查：乙型肝炎五项-乙型肝炎e抗体
	private String yghxkt;		//辅助检查：乙型肝炎五项-乙型肝炎核心抗体

	private java.sql.Date fqcsrq; // 父亲出生日期

	private String resbz0;		//妊娠史备注

	private String jkdah0;		//健康档案号
	private String hjlx00;		//户籍类型
	private String sfnyh0;		//是否农业户
	private String yzbm00;		//邮政编码
	private String jwspx0;		//既往史-贫血
	private String fkssbz;		//妇科手术史标志
	private String fksss0;		//妇科手术史
	private String grsxy0;		//个人史-吸烟
	private String grsyj0;		//个人史-饮酒
	private String grsyw0;		//个人史-服用药物
	private String grsyhw;		//个人史-接触有毒有害物质
	private String grsfsx;		//个人史-接触放射线
	private String grsqt0;		//个人史-其他
	private String rshbzs;		//妊娠合并症史
	private String rsbfzs;		//妊娠并发症史
	private java.math.BigDecimal yqtz00;		//孕前体重(Kg)
	private String fjjjbz;		//妇检拒检标志
	private String wyjjbz;		//外阴拒检标志
	private String ydjjbz;		//阴道拒检标志
	private String gjjjbz;		//宫颈拒检标志
	private String gtjjbz;		//宫体拒检标志
	private String fjflag;		//附件拒检标志
	private String mdxqlb;		//梅毒血清学试验类别

	private  java.lang.String mqgj00; //母亲国籍
	private  java.lang.String fqgj00; //父亲国籍
	private  java.lang.String mqsfzl; //母亲身份证类别
	private  java.lang.String fqsfzl; //父亲身份证类别

	private String blflag;				//补录标志(0-否，1-是)

	private java.sql.Date tjrq00;		//体检日期
	private String ncgwyc;	//尿常规-未见异常
	private String ncgbxb;	//尿常规-白细胞
	private String zstj00;	//主诉停经周
	private String zszz00;	//主诉症状
	private String fkjcqt;	//妇科检查其他
	private String zszzqt;	//主诉症状其他
	private String jcxmjc;	//据查


	private  java.lang.String bcggj0; //辅助检查：B/彩超-股骨颈
	private  java.lang.String bcsdj0; //辅助检查：B/彩超-双顶径(mm)
	private  java.lang.String bctw00; //辅助检查：B/彩超-头围
	private  java.lang.String bcfw00; //辅助检查：B/彩超-腹围
	private  java.lang.String bcys00; //辅助检查：B/彩超-羊水
	private  java.lang.String bcyszs; //辅助检查：B/彩超-羊水指数
	private  java.lang.String bctx00; //辅助检查：B/彩超-胎心
	private  java.lang.String bctpcs; //辅助检查：B/彩超-胎盘成熟度
	private  java.lang.String bctpfy; //辅助检查：B/彩超-胎盘附于
	private  java.lang.String bctpxy; //辅助检查：B/彩超-胎盘下缘距子宫颈内口
	private  java.lang.String bcjxl0; //辅助检查：B/彩超-脐血流
	private  java.lang.String bczgxd; //辅助检查：B/彩超-子宫下段厚度
	private  java.lang.String bcqt00; //辅助检查：B/彩超-其他
	private  java.lang.String bcjl00; //辅助检查：B/彩超-结论
	private  java.lang.String ft3000; //辅助检查：甲状腺疾病筛查-FT3
	private  java.lang.String ft4000; //辅助检查：甲状腺疾病筛查-FT4
	private  java.lang.String tsh000; //辅助检查：甲状腺疾病筛查-TSH
	private  java.lang.String tpoab0; //辅助检查：甲状腺疾病筛查-TPOAb
	private  java.lang.String fjshqt; //辅助检查：生化全套-未见明显异常
	private  java.lang.String shkfxt; //辅助检查：生化全套-空腹血糖
	private  java.lang.String shgbza; //辅助检查：生化全套-谷丙转氨酶
	private  java.lang.String shgcza; //辅助检查：生化全套-谷草转氨酶
	private  java.lang.String shzdb0; //辅助检查：生化全套-总蛋白
	private  java.lang.String shbdb0; //辅助检查：生化全套-白蛋白
	private  java.lang.String shzdzs; //辅助检查：生化全套-总胆汁酸
	private  java.lang.String shzdhs; //辅助检查：生化全套-总胆红素
	private  java.lang.String shjg00; //辅助检查：生化全套-肌尿
	private  java.lang.String shnsd0; //辅助检查：生化全套-尿素氮
	private  java.lang.String shns00; //辅助检查：生化全套-尿酸
	private  java.lang.String shldh0; //辅助检查：生化全套-LDH
	private  java.lang.String shqt00; //辅助检查：生化全套-其他
	private  java.lang.String rprdd0; //辅助检查：RPR-如果是“阳性”，滴度
	private  java.lang.String ggkfxt; //辅助检查：肝功=空腹血糖
	private  java.lang.String ggqt00; //辅助检查：肝功-其他
	private  java.lang.String sgqt00; //辅助检查：肾功-其他

	private  java.lang.String jzsh00;  //辅助检查：急诊生化
	private  java.lang.String xdtwyc;  //辅助检查：心电图-未异常
	private  java.lang.String fjnxgn;  //辅助检查：凝血功能
	private  java.lang.String fjtppa; //辅助检查：TPPA
	private String jcxmlx;	//辅助检查项目大类编号字符串
	private String jcxmmc;  //辅助检查项目大类名称字符串

	private String zydw00;		//发现早孕单位

	private String mz0000; // 民族
	private String fqmz00;

	private java.sql.Date ycqsta; //  预产期--起
	private java.sql.Date ycqend; //  预产期--止
	private String sfjh00; //  是否结婚
	private String bjzdqt; //保健指导--其他
	private String sszqjk;//是否十四周前建卡
	private String jkyxx0;//建卡有效性

	private String fzjcqt; //辅助检查--其他

	private String wcjysb; //辅助检查：无创基因检测-18三体
	private String wcjyey; //辅助检查：无创基因检测-21三体
	private String wcjyss; //辅助检查：无创基因检测-13三体
	private String jcqt00; //辅助检查：拒查-其他
	private String xcgzxl;//辅助检查：血常规-中性粒百分比

	private String nxpt00; //辅助检查：凝血功能-PT
	private String nxinr0; //辅助检查：凝血功能-INR
	private String nxfg00; //辅助检查：凝血功能-FG
	private String nxappt; //辅助检查：凝血功能-APTT
	private String nxdejt; //辅助检查：凝血功能-D-二聚体
	private String nxmsj0; //辅助检查：凝血功能-凝血酶时间

	private String gwyzy0;//高危孕周1
	private String yyrqe0;//高危预约日期2
	private String yyrqs0;//高危预约日期3

	private java.sql.Date xcgkdrq; // 开单日期
	private java.sql.Date xxkdrq;
	private java.sql.Date ncgkdrq;
	private java.sql.Date bdcgkdrq;
	private java.sql.Date yxgykdrq;
	private java.sql.Date rprkdrq;
	private java.sql.Date tppakdrq;
	private java.sql.Date hivkdrq;
	private java.sql.Date bgkdrq;
	private java.sql.Date hbsagkdrq;
	private java.sql.Date cqsckdrq;
	private java.sql.Date wcjykdrq;
	private java.sql.Date ogttkdrq;
	private java.sql.Date sgkdrq;
	private java.sql.Date ggkdrq;
	private java.sql.Date shqtkdrq;
	private java.sql.Date bckdrq;
	private java.sql.Date jzshkdrq;
	private java.sql.Date jzxkdrq;
	private java.sql.Date xdtkdrq;
	private java.sql.Date nxgnkdrq;

	private java.sql.Date qxljckdrq;

	private String shk000;
	private String shna00;
	private String shcl00;
	private String shca00;
	private String shmg00;

	private String jzk000;
	private String jzna00;
	private String jzcl00;
	private String jzca00;
	private String jzmg00;
	private String jzzdb0;
	private String jzbdb0;
	private String jzzdhs;
	private String jzjdhs;
	private String jzgbzam;
	private String jzgczam;
	private String jzxt00;
	private String jzrstam;
	private String jzns00;
	private String jzjg00;
	private String ggzdzs;
	private String ggnzdb;
	private String jznsh0;
	private String xcglbx;
	private String xcghxb;
	private String xcgxhd;

	private java.sql.Date endjcrq;//检查日期
	private String ncgndy;//辅助检查：尿常规-尿胆原
	private String ncgdhs;//辅助检查：尿常规-胆红素
	private String jzzdgc;//辅助检查：急诊生化-总胆固醇
	private String gtqt00;	//宫体其他
	private String querType;//查询方式
	private String cjcs00;//产检次数

	private String fjbdcg;//白带常规：是否异常
	private String bdcgmj;//白带常规：霉菌
	private String bdcgdc;//白带常规：滴虫
	private String bdcgbv;//白带常规：细菌性阴道病 BV
	private String bdcgqt;//白带常规：其他

	private java.sql.Date jcrq00end; // 检查日期-止

	private String fqhkd0;		//父亲户口地

	private String nz0000;		//拟诊2014-04-16,hbt

	private java.sql.Date fjyyrq;		//复检预约日期,2014-05-04,hbt

	private String type00;

	private String tjmr00;//体检默认按钮
	private String fjmr00;//复检默认按钮
	private String fzjcmr;//体检默认按钮

	private String jcqk00;// 建册情况
	private String ogttqt;//OGTT其他
	private String cqscqt;//产前筛查其他
	private String ctrl;
	private String amyzx0;//艾梅乙咨询

	private String sfgw; // 是否高危
	private String sfcjk0; // 是否初建卡

	private String thxhkdrq;
	private String thxhdb; //辅助检查：糖化血红蛋白
	private String dhzt00; //电话状态

	private String qxlsd0; //辅助检查：胎儿脐血流监测
	private String qxlpi0; //辅助检查：胎儿脐血流监测
	private String qxlri0; //辅助检查：胎儿脐血流监测

	private java.lang.String hkdshe1; // 户口所在地：省
	private java.lang.String hkdshi1; // 户口所在地：市
	private java.lang.String hkdxia1; // 户口所在地：县(区)
	private java.lang.String hkdzhe1; // 户口所在地：镇(乡)
	private java.lang.String hkdcun1; // 户口所在地：村(居)
	private java.lang.String hkdmph1; // 户口所在地：门牌号
	private java.lang.String xzdshe1; // 现住地：省
	private java.lang.String xzdshi1; // 现住地：市
	private java.lang.String xzdxia1; // 现住地：县(区)
	private java.lang.String xzdzhe1; // 现住地：镇(乡)
	private java.lang.String xzdcun1; // 现住地：村(居)

	private String sfbyjk; // 是否本院建卡
	private String xcgjcdw;//血常规检查单位
	private String xxjcdw;
	private String ncgjcdw;
	private String bdcgjcdw;
	private String yxgyjcdw;
	private String rprjcdw;
	private String tppajcdw;
	private String hivjcdw;
	private String bgjcdw;
	private String hbsagjcdw;
	private String cqscjcdw;
	private String wcjyjcdw;
	private String ogttjcdw;
	private String sgjcdw;
	private String ggjcdw;
	private String bcjcdw;
	private String shqtjcdw;
	private String jzshjcdw;
	private String jzxjcdw;
	private String qxljcjcdw;
	private String thxhjcdw;
	private String xdtjcdw;
	private String nxgnjcdw;
	private String pttjcdw;
	private String kaxjjcdw;
	private String kbxjjcdw;
	private String gjxbjcdw;
	private String zghdjcdw;
	private String gjcdjcdw;
	private String crpjcdw;
	private String pxjcdw;
	private String coomjcdw;
	private String hcgzjcdw;
	private String pttcd0;//血糖测定
	private java.sql.Date pttkdrq;
	private String kaxj00;//抗A效价
	private java.sql.Date kaxjkdrq;
	private String kbxj00;//抗B效价
	private java.sql.Date kbxjkdrq;
	private String yszd00;//羊水诊断
	private java.sql.Date yszdkdrq;
	private String yszdjcdw;
	private String gjxbjc;//宫颈细胞学检查
	private java.sql.Date gjxbkdrq;
    private String cszghd;//超声测量子宫下段厚度
    private java.sql.Date zghdkdrq;
    private String csgjcd;//超声测量宫颈长度
    private java.sql.Date gjcdkdrq;
    private String cscrp0;//快速CRP
    private java.sql.Date crpkdrq;
    private String pxjy01;//地中海贫血基因检测
    private String pxjy02;
    private String pxjy03;
    private java.sql.Date pxkdrq;
    private String coombsiggc;//直接抗人球蛋白试验(Coombs′试验)
    private String coombsigg;
    private String coombsc;
    private java.sql.Date coomkdrq;
    private String tdhcgz;//胎儿核磁共振
    private java.sql.Date hcgzkdrq;
    private String orgid; // 机构编码
	private String orgname; // 机构名称
	private String principal; // 负责人
	private String rsfyy0;
    private String cgtdy0;
    private String fjtrus;//TRUST
    private java.sql.Date truskdrq;
    private String trusjcdw;
    private String fjelis;//ELISA
    private java.sql.Date eliskdrq;
    private String elisjcdw;
    private String fjrt00;//RT
    private java.sql.Date rtkdrq;
    private String rtjcdw;

    private String hivsfqz;//HIV是否确诊
	private java.sql.Date hivqzrq;//HIV确诊日期

	private String hivjc0; //HIV检测，0否 1是
	private String hivjcjg; //  HIV检测结果--阳性，阴性
	private java.sql.Date hivjcqzrq; //  HIV检测确诊日期
	private String rprjc0; //  梅毒检测，0否 1是
	private String rprzdgr; //  诊断梅毒感染
	private String rprzl; //  梅毒治疗
	private String ygjc0; //  乙肝检测，0否 1是
	private String ygjcjg; // 乙肝检测结果

	public String getHivjc0() {
		return hivjc0;
	}

	public void setHivjc0(String hivjc0) {
		this.hivjc0 = hivjc0;
	}

	public String getHivjcjg() {
		return hivjcjg;
	}

	public void setHivjcjg(String hivjcjg) {
		this.hivjcjg = hivjcjg;
	}

	public Date getHivjcqzrq() {
		return hivjcqzrq;
	}

	public void setHivjcqzrq(Date hivjcqzrq) {
		this.hivjcqzrq = hivjcqzrq;
	}

	public String getRprjc0() {
		return rprjc0;
	}

	public void setRprjc0(String rprjc0) {
		this.rprjc0 = rprjc0;
	}

	public String getRprzdgr() {
		return rprzdgr;
	}

	public void setRprzdgr(String rprzdgr) {
		this.rprzdgr = rprzdgr;
	}

	public String getRprzl() {
		return rprzl;
	}

	public void setRprzl(String rprzl) {
		this.rprzl = rprzl;
	}

	public String getYgjc0() {
		return ygjc0;
	}

	public void setYgjc0(String ygjc0) {
		this.ygjc0 = ygjc0;
	}

	public String getYgjcjg() {
		return ygjcjg;
	}

	public void setYgjcjg(String ygjcjg) {
		this.ygjcjg = ygjcjg;
	}

	private String mfdpsc;//免费地贫筛查
	private java.sql.Date mfdprq;//免费地贫检测日期
	private String mfdpdw;//免费地贫检测单位
	private String mfcqsc;//免费产前筛查
	private java.sql.Date mfcqrq;//免费产前检测日期
	private String mfcqdw;//免费产前检测单位
	private String mfcssc;//免费超声筛查
	private java.sql.Date mfcsrq;//免费超声检测日期
	private String mfcsdw;//免费超声检测单位
	private String mftnbsc;//免费糖尿病筛查
	private java.sql.Date mftnbrq;//免费糖尿病检测日期
	private String mftnbdw;//免费糖尿病检测单位
	private java.lang.String ycq000str;//预产日期

	public String getMfdpsc() {
		return mfdpsc;
	}
	public void setMfdpsc(String mfdpsc) {
		this.mfdpsc = mfdpsc;
	}
	public java.sql.Date getMfdprq() {
		return mfdprq;
	}
	public void setMfdprq(java.sql.Date mfdprq) {
		this.mfdprq = mfdprq;
	}
	public String getMfdpdw() {
		return mfdpdw;
	}
	public void setMfdpdw(String mfdpdw) {
		this.mfdpdw = mfdpdw;
	}
	public String getMfcqsc() {
		return mfcqsc;
	}
	public void setMfcqsc(String mfcqsc) {
		this.mfcqsc = mfcqsc;
	}
	public java.sql.Date getMfcqrq() {
		return mfcqrq;
	}
	public void setMfcqrq(java.sql.Date mfcqrq) {
		this.mfcqrq = mfcqrq;
	}
	public String getMfcqdw() {
		return mfcqdw;
	}
	public void setMfcqdw(String mfcqdw) {
		this.mfcqdw = mfcqdw;
	}
	public String getMfcssc() {
		return mfcssc;
	}
	public void setMfcssc(String mfcssc) {
		this.mfcssc = mfcssc;
	}
	public java.sql.Date getMfcsrq() {
		return mfcsrq;
	}
	public void setMfcsrq(java.sql.Date mfcsrq) {
		this.mfcsrq = mfcsrq;
	}
	public String getMfcsdw() {
		return mfcsdw;
	}
	public void setMfcsdw(String mfcsdw) {
		this.mfcsdw = mfcsdw;
	}
	public String getMftnbsc() {
		return mftnbsc;
	}
	public void setMftnbsc(String mftnbsc) {
		this.mftnbsc = mftnbsc;
	}
	public java.sql.Date getMftnbrq() {
		return mftnbrq;
	}
	public void setMftnbrq(java.sql.Date mftnbrq) {
		this.mftnbrq = mftnbrq;
	}
	public String getMftnbdw() {
		return mftnbdw;
	}
	public void setMftnbdw(String mftnbdw) {
		this.mftnbdw = mftnbdw;
	}
	public java.lang.String getBcyszs() {
		return bcyszs;
	}
	public void setBcyszs(java.lang.String bcyszs) {
		this.bcyszs = bcyszs;
	}
	public String getHivsfqz() {
		return hivsfqz;
	}
	public void setHivsfqz(String hivsfqz) {
		this.hivsfqz = hivsfqz;
	}
	public java.sql.Date getHivqzrq() {
		return hivqzrq;
	}
	public void setHivqzrq(java.sql.Date hivqzrq) {
		this.hivqzrq = hivqzrq;
	}
	public String getFjtrus() {
		return fjtrus;
	}
	public void setFjtrus(String fjtrus) {
		this.fjtrus = fjtrus;
	}
	public java.sql.Date getTruskdrq() {
		return truskdrq;
	}
	public void setTruskdrq(java.sql.Date truskdrq) {
		this.truskdrq = truskdrq;
	}
	public String getTrusjcdw() {
		return trusjcdw;
	}
	public void setTrusjcdw(String trusjcdw) {
		this.trusjcdw = trusjcdw;
	}
	public String getFjelis() {
		return fjelis;
	}
	public void setFjelis(String fjelis) {
		this.fjelis = fjelis;
	}
	public java.sql.Date getEliskdrq() {
		return eliskdrq;
	}
	public void setEliskdrq(java.sql.Date eliskdrq) {
		this.eliskdrq = eliskdrq;
	}
	public String getElisjcdw() {
		return elisjcdw;
	}
	public void setElisjcdw(String elisjcdw) {
		this.elisjcdw = elisjcdw;
	}
	public String getFjrt00() {
		return fjrt00;
	}
	public void setFjrt00(String fjrt00) {
		this.fjrt00 = fjrt00;
	}
	public java.sql.Date getRtkdrq() {
		return rtkdrq;
	}
	public void setRtkdrq(java.sql.Date rtkdrq) {
		this.rtkdrq = rtkdrq;
	}
	public String getRtjcdw() {
		return rtjcdw;
	}
	public void setRtjcdw(String rtjcdw) {
		this.rtjcdw = rtjcdw;
	}
	public String getRsfyy0() {
		return rsfyy0;
	}
	public void setRsfyy0(String rsfyy0) {
		this.rsfyy0 = rsfyy0;
	}
	public String getCgtdy0() {
		return cgtdy0;
	}
	public void setCgtdy0(String cgtdy0) {
		this.cgtdy0 = cgtdy0;
	}
	public String getOrgid() {
		return orgid;
	}
	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}
	public String getOrgname() {
		return orgname;
	}
	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}
	public String getPrincipal() {
		return principal;
	}
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	public String getGjxbjc() {
		return gjxbjc;
	}
	public void setGjxbjc(String gjxbjc) {
		this.gjxbjc = gjxbjc;
	}
	public java.sql.Date getGjxbkdrq() {
		return gjxbkdrq;
	}
	public void setGjxbkdrq(java.sql.Date gjxbkdrq) {
		this.gjxbkdrq = gjxbkdrq;
	}
	public String getCszghd() {
		return cszghd;
	}
	public void setCszghd(String cszghd) {
		this.cszghd = cszghd;
	}
	public java.sql.Date getZghdkdrq() {
		return zghdkdrq;
	}
	public void setZghdkdrq(java.sql.Date zghdkdrq) {
		this.zghdkdrq = zghdkdrq;
	}
	public String getCsgjcd() {
		return csgjcd;
	}
	public void setCsgjcd(String csgjcd) {
		this.csgjcd = csgjcd;
	}
	public java.sql.Date getGjcdkdrq() {
		return gjcdkdrq;
	}
	public void setGjcdkdrq(java.sql.Date gjcdkdrq) {
		this.gjcdkdrq = gjcdkdrq;
	}
	public String getCscrp0() {
		return cscrp0;
	}
	public void setCscrp0(String cscrp0) {
		this.cscrp0 = cscrp0;
	}
	public java.sql.Date getCrpkdrq() {
		return crpkdrq;
	}
	public void setCrpkdrq(java.sql.Date crpkdrq) {
		this.crpkdrq = crpkdrq;
	}
	public String getPxjy01() {
		return pxjy01;
	}
	public void setPxjy01(String pxjy01) {
		this.pxjy01 = pxjy01;
	}
	public String getPxjy02() {
		return pxjy02;
	}
	public void setPxjy02(String pxjy02) {
		this.pxjy02 = pxjy02;
	}
	public String getPxjy03() {
		return pxjy03;
	}
	public void setPxjy03(String pxjy03) {
		this.pxjy03 = pxjy03;
	}
	public java.sql.Date getPxkdrq() {
		return pxkdrq;
	}
	public void setPxkdrq(java.sql.Date pxkdrq) {
		this.pxkdrq = pxkdrq;
	}
	public String getCoombsiggc() {
		return coombsiggc;
	}
	public void setCoombsiggc(String coombsiggc) {
		this.coombsiggc = coombsiggc;
	}
	public String getCoombsigg() {
		return coombsigg;
	}
	public void setCoombsigg(String coombsigg) {
		this.coombsigg = coombsigg;
	}
	public String getCoombsc() {
		return coombsc;
	}
	public void setCoombsc(String coombsc) {
		this.coombsc = coombsc;
	}
	public java.sql.Date getCoomkdrq() {
		return coomkdrq;
	}
	public void setCoomkdrq(java.sql.Date coomkdrq) {
		this.coomkdrq = coomkdrq;
	}
	public String getTdhcgz() {
		return tdhcgz;
	}
	public void setTdhcgz(String tdhcgz) {
		this.tdhcgz = tdhcgz;
	}
	public java.sql.Date getHcgzkdrq() {
		return hcgzkdrq;
	}
	public void setHcgzkdrq(java.sql.Date hcgzkdrq) {
		this.hcgzkdrq = hcgzkdrq;
	}
	public String getYszd00() {
		return yszd00;
	}
	public void setYszd00(String yszd00) {
		this.yszd00 = yszd00;
	}
	public java.sql.Date getYszdkdrq() {
		return yszdkdrq;
	}
	public void setYszdkdrq(java.sql.Date yszdkdrq) {
		this.yszdkdrq = yszdkdrq;
	}
	public String getYszdjcdw() {
		return yszdjcdw;
	}
	public void setYszdjcdw(String yszdjcdw) {
		this.yszdjcdw = yszdjcdw;
	}
	public String getKaxj00() {
		return kaxj00;
	}
	public void setKaxj00(String kaxj00) {
		this.kaxj00 = kaxj00;
	}
	public java.sql.Date getKaxjkdrq() {
		return kaxjkdrq;
	}
	public void setKaxjkdrq(java.sql.Date kaxjkdrq) {
		this.kaxjkdrq = kaxjkdrq;
	}
	public String getKbxj00() {
		return kbxj00;
	}
	public void setKbxj00(String kbxj00) {
		this.kbxj00 = kbxj00;
	}
	public java.sql.Date getKbxjkdrq() {
		return kbxjkdrq;
	}
	public void setKbxjkdrq(java.sql.Date kbxjkdrq) {
		this.kbxjkdrq = kbxjkdrq;
	}
	public String getPttcd0() {
		return pttcd0;
	}
	public void setPttcd0(String pttcd0) {
		this.pttcd0 = pttcd0;
	}
	public java.sql.Date getPttkdrq() {
		return pttkdrq;
	}
	public void setPttkdrq(java.sql.Date pttkdrq) {
		this.pttkdrq = pttkdrq;
	}
	public String getXcgjcdw() {
		return xcgjcdw;
	}
	public void setXcgjcdw(String xcgjcdw) {
		this.xcgjcdw = xcgjcdw;
	}
	public String getXxjcdw() {
		return xxjcdw;
	}
	public void setXxjcdw(String xxjcdw) {
		this.xxjcdw = xxjcdw;
	}
	public String getNcgjcdw() {
		return ncgjcdw;
	}
	public void setNcgjcdw(String ncgjcdw) {
		this.ncgjcdw = ncgjcdw;
	}
	public String getBdcgjcdw() {
		return bdcgjcdw;
	}
	public void setBdcgjcdw(String bdcgjcdw) {
		this.bdcgjcdw = bdcgjcdw;
	}
	public String getYxgyjcdw() {
		return yxgyjcdw;
	}
	public void setYxgyjcdw(String yxgyjcdw) {
		this.yxgyjcdw = yxgyjcdw;
	}
	public String getRprjcdw() {
		return rprjcdw;
	}
	public void setRprjcdw(String rprjcdw) {
		this.rprjcdw = rprjcdw;
	}
	public String getTppajcdw() {
		return tppajcdw;
	}
	public void setTppajcdw(String tppajcdw) {
		this.tppajcdw = tppajcdw;
	}
	public String getHivjcdw() {
		return hivjcdw;
	}
	public void setHivjcdw(String hivjcdw) {
		this.hivjcdw = hivjcdw;
	}
	public String getBgjcdw() {
		return bgjcdw;
	}
	public void setBgjcdw(String bgjcdw) {
		this.bgjcdw = bgjcdw;
	}
	public String getHbsagjcdw() {
		return hbsagjcdw;
	}
	public void setHbsagjcdw(String hbsagjcdw) {
		this.hbsagjcdw = hbsagjcdw;
	}
	public String getCqscjcdw() {
		return cqscjcdw;
	}
	public void setCqscjcdw(String cqscjcdw) {
		this.cqscjcdw = cqscjcdw;
	}
	public String getWcjyjcdw() {
		return wcjyjcdw;
	}
	public void setWcjyjcdw(String wcjyjcdw) {
		this.wcjyjcdw = wcjyjcdw;
	}
	public String getOgttjcdw() {
		return ogttjcdw;
	}
	public void setOgttjcdw(String ogttjcdw) {
		this.ogttjcdw = ogttjcdw;
	}
	public String getSgjcdw() {
		return sgjcdw;
	}
	public void setSgjcdw(String sgjcdw) {
		this.sgjcdw = sgjcdw;
	}
	public String getGgjcdw() {
		return ggjcdw;
	}
	public void setGgjcdw(String ggjcdw) {
		this.ggjcdw = ggjcdw;
	}
	public String getBcjcdw() {
		return bcjcdw;
	}
	public void setBcjcdw(String bcjcdw) {
		this.bcjcdw = bcjcdw;
	}
	public String getShqtjcdw() {
		return shqtjcdw;
	}
	public void setShqtjcdw(String shqtjcdw) {
		this.shqtjcdw = shqtjcdw;
	}
	public String getJzshjcdw() {
		return jzshjcdw;
	}
	public void setJzshjcdw(String jzshjcdw) {
		this.jzshjcdw = jzshjcdw;
	}
	public String getJzxjcdw() {
		return jzxjcdw;
	}
	public void setJzxjcdw(String jzxjcdw) {
		this.jzxjcdw = jzxjcdw;
	}
	public String getQxljcjcdw() {
		return qxljcjcdw;
	}
	public void setQxljcjcdw(String qxljcjcdw) {
		this.qxljcjcdw = qxljcjcdw;
	}
	public String getThxhjcdw() {
		return thxhjcdw;
	}
	public void setThxhjcdw(String thxhjcdw) {
		this.thxhjcdw = thxhjcdw;
	}
	public String getXdtjcdw() {
		return xdtjcdw;
	}
	public void setXdtjcdw(String xdtjcdw) {
		this.xdtjcdw = xdtjcdw;
	}
	public String getNxgnjcdw() {
		return nxgnjcdw;
	}
	public void setNxgnjcdw(String nxgnjcdw) {
		this.nxgnjcdw = nxgnjcdw;
	}
	public String getPttjcdw() {
		return pttjcdw;
	}
	public void setPttjcdw(String pttjcdw) {
		this.pttjcdw = pttjcdw;
	}
	public String getKaxjjcdw() {
		return kaxjjcdw;
	}
	public void setKaxjjcdw(String kaxjjcdw) {
		this.kaxjjcdw = kaxjjcdw;
	}
	public String getKbxjjcdw() {
		return kbxjjcdw;
	}
	public void setKbxjjcdw(String kbxjjcdw) {
		this.kbxjjcdw = kbxjjcdw;
	}
	public String getGjxbjcdw() {
		return gjxbjcdw;
	}
	public void setGjxbjcdw(String gjxbjcdw) {
		this.gjxbjcdw = gjxbjcdw;
	}
	public String getZghdjcdw() {
		return zghdjcdw;
	}
	public void setZghdjcdw(String zghdjcdw) {
		this.zghdjcdw = zghdjcdw;
	}
	public String getGjcdjcdw() {
		return gjcdjcdw;
	}
	public void setGjcdjcdw(String gjcdjcdw) {
		this.gjcdjcdw = gjcdjcdw;
	}
	public String getCrpjcdw() {
		return crpjcdw;
	}
	public void setCrpjcdw(String crpjcdw) {
		this.crpjcdw = crpjcdw;
	}
	public String getPxjcdw() {
		return pxjcdw;
	}
	public void setPxjcdw(String pxjcdw) {
		this.pxjcdw = pxjcdw;
	}
	public String getCoomjcdw() {
		return coomjcdw;
	}
	public void setCoomjcdw(String coomjcdw) {
		this.coomjcdw = coomjcdw;
	}
	public String getHcgzjcdw() {
		return hcgzjcdw;
	}
	public void setHcgzjcdw(String hcgzjcdw) {
		this.hcgzjcdw = hcgzjcdw;
	}
	public String getJczxm0() {
		return jczxm0;
	}
	public void setJczxm0(String jczxm0) {
		this.jczxm0 = jczxm0;
	}
	public String getLrzxm0() {
		return lrzxm0;
	}
	public void setLrzxm0(String lrzxm0) {
		this.lrzxm0 = lrzxm0;
	}
	public String getThxhkdrq() {
		return thxhkdrq;
	}
	public void setThxhkdrq(String thxhkdrq) {
		this.thxhkdrq = thxhkdrq;
	}
	public java.lang.String getThxhdb() {
		return thxhdb;
	}
	public void setThxhdb(java.lang.String thxhdb) {
		this.thxhdb = thxhdb;
	}
	public String getAmyzx0() {
		return amyzx0;
	}
	public void setAmyzx0(String amyzx0) {
		this.amyzx0 = amyzx0;
	}
	public String getCtrl() {
		return ctrl;
	}
	public void setCtrl(String ctrl) {
		this.ctrl = ctrl;
	}
	public String getOgttqt() {
		return ogttqt;
	}
	public void setOgttqt(String ogttqt) {
		this.ogttqt = ogttqt;
	}
	public String getCqscqt() {
		return cqscqt;
	}
	public void setCqscqt(String cqscqt) {
		this.cqscqt = cqscqt;
	}
	public String getJcqk00() {
		return jcqk00;
	}
	public void setJcqk00(String jcqk00) {
		this.jcqk00 = jcqk00;
	}
	public String getTjmr00() {
		return tjmr00;
	}
	public void setTjmr00(String tjmr00) {
		this.tjmr00 = tjmr00;
	}
	public String getFjmr00() {
		return fjmr00;
	}
	public void setFjmr00(String fjmr00) {
		this.fjmr00 = fjmr00;
	}
	public String getFzjcmr() {
		return fzjcmr;
	}
	public void setFzjcmr(String fzjcmr) {
		this.fzjcmr = fzjcmr;
	}
	public String getType00() {
		return type00;
	}
	public void setType00(String type00) {
		this.type00 = type00;
	}
	public java.sql.Date getFjyyrq() {
		return fjyyrq;
	}
	public void setFjyyrq(java.sql.Date fjyyrq) {
		this.fjyyrq = fjyyrq;
	}
	public String getNz0000() {
		return nz0000;
	}
	public void setNz0000(String nz0000) {
		this.nz0000 = nz0000;
	}

	public String getFqhkd0() {
		return fqhkd0;
	}
	public void setFqhkd0(String fqhkd0) {
		this.fqhkd0 = fqhkd0;
	}
	public String getFjbdcg() {
		return fjbdcg;
	}
	public void setFjbdcg(String fjbdcg) {
		this.fjbdcg = fjbdcg;
	}
	public String getBdcgmj() {
		return bdcgmj;
	}
	public void setBdcgmj(String bdcgmj) {
		this.bdcgmj = bdcgmj;
	}
	public String getBdcgdc() {
		return bdcgdc;
	}
	public void setBdcgdc(String bdcgdc) {
		this.bdcgdc = bdcgdc;
	}
	public String getBdcgbv() {
		return bdcgbv;
	}
	public void setBdcgbv(String bdcgbv) {
		this.bdcgbv = bdcgbv;
	}
	public String getBdcgqt() {
		return bdcgqt;
	}
	public void setBdcgqt(String bdcgqt) {
		this.bdcgqt = bdcgqt;
	}
	public String getCjcs00() {
		return cjcs00;
	}
	public void setCjcs00(String cjcs00) {
		this.cjcs00 = cjcs00;
	}
	public String getQuerType() {
		return querType;
	}
	public void setQuerType(String querType) {
		this.querType = querType;
	}
	public String getGtqt00() {
		return gtqt00;
	}
	public void setGtqt00(String gtqt00) {
		this.gtqt00 = gtqt00;
	}
	public java.sql.Date getEndjcrq() {
		return endjcrq;
	}
	public void setEndjcrq(java.sql.Date endjcrq) {
		this.endjcrq = endjcrq;
	}
	public String getJznsh0() {
		return jznsh0;
	}
	public void setJznsh0(String jznsh0) {
		this.jznsh0 = jznsh0;
	}
	public String getJzk000() {
		return jzk000;
	}
	public void setJzk000(String jzk000) {
		this.jzk000 = jzk000;
	}
	public String getJzna00() {
		return jzna00;
	}
	public void setJzna00(String jzna00) {
		this.jzna00 = jzna00;
	}
	public String getJzcl00() {
		return jzcl00;
	}
	public void setJzcl00(String jzcl00) {
		this.jzcl00 = jzcl00;
	}
	public String getJzca00() {
		return jzca00;
	}
	public void setJzca00(String jzca00) {
		this.jzca00 = jzca00;
	}
	public String getJzmg00() {
		return jzmg00;
	}
	public void setJzmg00(String jzmg00) {
		this.jzmg00 = jzmg00;
	}
	public String getJzzdb0() {
		return jzzdb0;
	}
	public void setJzzdb0(String jzzdb0) {
		this.jzzdb0 = jzzdb0;
	}
	public String getJzbdb0() {
		return jzbdb0;
	}
	public void setJzbdb0(String jzbdb0) {
		this.jzbdb0 = jzbdb0;
	}
	public String getJzzdhs() {
		return jzzdhs;
	}
	public void setJzzdhs(String jzzdhs) {
		this.jzzdhs = jzzdhs;
	}
	public String getJzjdhs() {
		return jzjdhs;
	}
	public void setJzjdhs(String jzjdhs) {
		this.jzjdhs = jzjdhs;
	}
	public String getJzgbzam() {
		return jzgbzam;
	}
	public void setJzgbzam(String jzgbzam) {
		this.jzgbzam = jzgbzam;
	}
	public String getJzgczam() {
		return jzgczam;
	}
	public void setJzgczam(String jzgczam) {
		this.jzgczam = jzgczam;
	}
	public String getJzxt00() {
		return jzxt00;
	}
	public void setJzxt00(String jzxt00) {
		this.jzxt00 = jzxt00;
	}
	public String getJzrstam() {
		return jzrstam;
	}
	public void setJzrstam(String jzrstam) {
		this.jzrstam = jzrstam;
	}
	public String getJzns00() {
		return jzns00;
	}
	public void setJzns00(String jzns00) {
		this.jzns00 = jzns00;
	}
	public String getJzjg00() {
		return jzjg00;
	}
	public void setJzjg00(String jzjg00) {
		this.jzjg00 = jzjg00;
	}
	public String getShk000() {
		return shk000;
	}
	public void setShk000(String shk000) {
		this.shk000 = shk000;
	}
	public String getShna00() {
		return shna00;
	}
	public void setShna00(String shna00) {
		this.shna00 = shna00;
	}
	public String getShcl00() {
		return shcl00;
	}
	public void setShcl00(String shcl00) {
		this.shcl00 = shcl00;
	}
	public String getShca00() {
		return shca00;
	}
	public void setShca00(String shca00) {
		this.shca00 = shca00;
	}
	public String getShmg00() {
		return shmg00;
	}
	public void setShmg00(String shmg00) {
		this.shmg00 = shmg00;
	}
	public String getGwyzy0() {
		return gwyzy0;
	}
	public void setGwyzy0(String gwyzy0) {
		this.gwyzy0 = gwyzy0;
	}
	public String getYyrqe0() {
		return yyrqe0;
	}
	public void setYyrqe0(String yyrqe0) {
		this.yyrqe0 = yyrqe0;
	}
	public String getYyrqs0() {
		return yyrqs0;
	}
	public void setYyrqs0(String yyrqs0) {
		this.yyrqs0 = yyrqs0;
	}
	public String getNxpt00() {
		return nxpt00;
	}
	public void setNxpt00(String nxpt00) {
		this.nxpt00 = nxpt00;
	}
	public String getNxinr0() {
		return nxinr0;
	}
	public void setNxinr0(String nxinr0) {
		this.nxinr0 = nxinr0;
	}
	public String getNxfg00() {
		return nxfg00;
	}
	public void setNxfg00(String nxfg00) {
		this.nxfg00 = nxfg00;
	}
	public String getNxappt() {
		return nxappt;
	}
	public void setNxappt(String nxappt) {
		this.nxappt = nxappt;
	}
	public String getNxdejt() {
		return nxdejt;
	}
	public void setNxdejt(String nxdejt) {
		this.nxdejt = nxdejt;
	}
	public String getJcqt00() {
		return jcqt00;
	}
	public void setJcqt00(String jcqt00) {
		this.jcqt00 = jcqt00;
	}
	public String getSszqjk() {
		return sszqjk;
	}
	public void setSszqjk(String sszqjk) {
		this.sszqjk = sszqjk;
	}
	public String getJkyxx0() {
		return jkyxx0;
	}
	public void setJkyxx0(String jkyxx0) {
		this.jkyxx0 = jkyxx0;
	}
	public String getMz0000() {
		return mz0000;
	}
	public void setMz0000(String mz0000) {
		this.mz0000 = mz0000;
	}
	public String getFqmz00() {
		return fqmz00;
	}
	public void setFqmz00(String fqmz00) {
		this.fqmz00 = fqmz00;
	}

	public String getZydw00() {
		return zydw00;
	}
	public void setZydw00(String zydw00) {
		this.zydw00 = zydw00;
	}
	public String getJcxmlx() {
		return jcxmlx;
	}
	public void setJcxmlx(String jcxmlx) {
		this.jcxmlx = jcxmlx;
	}
	public String getJcxmmc() {
		return jcxmmc;
	}
	public void setJcxmmc(String jcxmmc) {
		this.jcxmmc = jcxmmc;
	}
	public String getBcsdj0() {
		return bcsdj0;
	}
	public void setBcsdj0(String bcsdj0) {
		this.bcsdj0 = bcsdj0;
	}
	public String getBctw00() {
		return bctw00;
	}
	public void setBctw00(String bctw00) {
		this.bctw00 = bctw00;
	}
	public String getBcggj0() {
		return bcggj0;
	}
	public void setBcggj0(String bcggj0) {
		this.bcggj0 = bcggj0;
	}
	public String getBcfw00() {
		return bcfw00;
	}
	public void setBcfw00(String bcfw00) {
		this.bcfw00 = bcfw00;
	}
	public String getBcys00() {
		return bcys00;
	}
	public void setBcys00(String bcys00) {
		this.bcys00 = bcys00;
	}
	public String getBctx00() {
		return bctx00;
	}
	public void setBctx00(String bctx00) {
		this.bctx00 = bctx00;
	}
	public String getBctpcs() {
		return bctpcs;
	}
	public void setBctpcs(String bctpcs) {
		this.bctpcs = bctpcs;
	}
	public String getBctpfy() {
		return bctpfy;
	}
	public void setBctpfy(String bctpfy) {
		this.bctpfy = bctpfy;
	}
	public String getBctpxy() {
		return bctpxy;
	}
	public void setBctpxy(String bctpxy) {
		this.bctpxy = bctpxy;
	}
	public String getBcjxl0() {
		return bcjxl0;
	}
	public void setBcjxl0(String bcjxl0) {
		this.bcjxl0 = bcjxl0;
	}
	public String getBczgxd() {
		return bczgxd;
	}
	public void setBczgxd(String bczgxd) {
		this.bczgxd = bczgxd;
	}
	public String getBcqt00() {
		return bcqt00;
	}
	public void setBcqt00(String bcqt00) {
		this.bcqt00 = bcqt00;
	}
	public String getBcjl00() {
		return bcjl00;
	}
	public void setBcjl00(String bcjl00) {
		this.bcjl00 = bcjl00;
	}
	public String getFt4000() {
		return ft4000;
	}
	public void setFt4000(String ft4000) {
		this.ft4000 = ft4000;
	}
	public String getTsh000() {
		return tsh000;
	}
	public void setTsh000(String tsh000) {
		this.tsh000 = tsh000;
	}
	public String getTpoab0() {
		return tpoab0;
	}
	public void setTpoab0(String tpoab0) {
		this.tpoab0 = tpoab0;
	}
	public String getFjshqt() {
		return fjshqt;
	}
	public void setFjshqt(String fjshqt) {
		this.fjshqt = fjshqt;
	}
	public String getShkfxt() {
		return shkfxt;
	}
	public void setShkfxt(String shkfxt) {
		this.shkfxt = shkfxt;
	}
	public String getShgbza() {
		return shgbza;
	}
	public void setShgbza(String shgbza) {
		this.shgbza = shgbza;
	}
	public String getShgcza() {
		return shgcza;
	}
	public void setShgcza(String shgcza) {
		this.shgcza = shgcza;
	}
	public String getShzdb0() {
		return shzdb0;
	}
	public void setShzdb0(String shzdb0) {
		this.shzdb0 = shzdb0;
	}
	public String getShbdb0() {
		return shbdb0;
	}
	public void setShbdb0(String shbdb0) {
		this.shbdb0 = shbdb0;
	}
	public String getShzdzs() {
		return shzdzs;
	}
	public void setShzdzs(String shzdzs) {
		this.shzdzs = shzdzs;
	}
	public String getShzdhs() {
		return shzdhs;
	}
	public void setShzdhs(String shzdhs) {
		this.shzdhs = shzdhs;
	}
	public String getShjg00() {
		return shjg00;
	}
	public void setShjg00(String shjg00) {
		this.shjg00 = shjg00;
	}
	public String getShnsd0() {
		return shnsd0;
	}
	public void setShnsd0(String shnsd0) {
		this.shnsd0 = shnsd0;
	}
	public String getShns00() {
		return shns00;
	}
	public void setShns00(String shns00) {
		this.shns00 = shns00;
	}
	public String getShldh0() {
		return shldh0;
	}
	public void setShldh0(String shldh0) {
		this.shldh0 = shldh0;
	}
	public String getShqt00() {
		return shqt00;
	}
	public void setShqt00(String shqt00) {
		this.shqt00 = shqt00;
	}
	public String getZszzqt() {
		return zszzqt;
	}
	public void setZszzqt(String zszzqt) {
		this.zszzqt = zszzqt;
	}
	public String getFkjcqt() {
		return fkjcqt;
	}
	public void setFkjcqt(String fkjcqt) {
		this.fkjcqt = fkjcqt;
	}
	public String getNcgwyc() {
		return ncgwyc;
	}
	public void setNcgwyc(String ncgwyc) {
		this.ncgwyc = ncgwyc;
	}
	public String getNcgbxb() {
		return ncgbxb;
	}
	public void setNcgbxb(String ncgbxb) {
		this.ncgbxb = ncgbxb;
	}
	public java.sql.Date getTjrq00() {
		return tjrq00;
	}
	public void setTjrq00(java.sql.Date tjrq00) {
		this.tjrq00 = tjrq00;
	}
	public String getBlflag() {
		return blflag;
	}
	public void setBlflag(String blflag) {
		this.blflag = blflag;
	}

	public java.lang.String getMqgj00() {
		return mqgj00;
	}
	public void setMqgj00(java.lang.String mqgj00) {
		this.mqgj00 = mqgj00;
	}
	public java.lang.String getFqgj00() {
		return fqgj00;
	}
	public void setFqgj00(java.lang.String fqgj00) {
		this.fqgj00 = fqgj00;
	}
	public java.lang.String getMqsfzl() {
		return mqsfzl;
	}
	public void setMqsfzl(java.lang.String mqsfzl) {
		this.mqsfzl = mqsfzl;
	}
	public java.lang.String getFqsfzl() {
		return fqsfzl;
	}
	public void setFqsfzl(java.lang.String fqsfzl) {
		this.fqsfzl = fqsfzl;
	}
	public String getResbz0() {
		return resbz0;
	}
	public void setResbz0(String resbz0) {
		this.resbz0 = resbz0;
	}
	public java.sql.Date getFqcsrq() {
		return fqcsrq;
	}
	public void setFqcsrq(java.sql.Date fqcsrq) {
		this.fqcsrq = fqcsrq;
	}
	public java.lang.String getStatus() {
		return status;
	}
	public void setStatus(java.lang.String status) {
		this.status = status;
	}
	public String getCxzt00() {
		return cxzt00;
	}
	public void setCxzt00(String cxzt00) {
		this.cxzt00 = cxzt00;
	}
	public String getLcrq00() {
		return lcrq00;
	}
	public void setLcrq00(String lcrq00) {
		this.lcrq00 = lcrq00;
	}

	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getGwyse0() {
		return gwyse0;
	}
	public void setGwyse0(String gwyse0) {
		this.gwyse0 = gwyse0;
	}
	public String getGwyss0() {
		return gwyss0;
	}
	public void setGwyss0(String gwyss0) {
		this.gwyss0 = gwyss0;
	}
	public String getGwysbhe() {
		return gwysbhe;
	}
	public void setGwysbhe(String gwysbhe) {
		this.gwysbhe = gwysbhe;
	}
	public String getGwysbhs() {
		return gwysbhs;
	}
	public void setGwysbhs(String gwysbhs) {
		this.gwysbhs = gwysbhs;
	}
	public java.math.BigDecimal getGwpfe0() {
		return gwpfe0;
	}
	public void setGwpfe0(java.math.BigDecimal gwpfe0) {
		this.gwpfe0 = gwpfe0;
	}
	public java.math.BigDecimal getGwyze0() {
		return gwyze0;
	}
	public void setGwyze0(java.math.BigDecimal gwyze0) {
		this.gwyze0 = gwyze0;
	}
	public java.math.BigDecimal getGwpfs0() {
		return gwpfs0;
	}
	public void setGwpfs0(java.math.BigDecimal gwpfs0) {
		this.gwpfs0 = gwpfs0;
	}
	public java.math.BigDecimal getGwyzs0() {
		return gwyzs0;
	}
	public void setGwyzs0(java.math.BigDecimal gwyzs0) {
		this.gwyzs0 = gwyzs0;
	}
	public java.sql.Date getJcrqQ0() {
		return jcrqQ0;
	}
	public void setJcrqQ0(java.sql.Date jcrqQ0) {
		this.jcrqQ0 = jcrqQ0;
	}
	public java.sql.Date getJcrqZ0() {
		return jcrqZ0;
	}
	public void setJcrqZ0(java.sql.Date jcrqZ0) {
		this.jcrqZ0 = jcrqZ0;
	}
	public String getSfwz00() {
		return sfwz00;
	}
	public void setSfwz00(String sfwz00) {
		this.sfwz00 = sfwz00;
	}
	public java.lang.String getBcts00() {
		return bcts00;
	}
	public void setBcts00(java.lang.String bcts00) {
		this.bcts00 = bcts00;
	}
	public java.lang.String getSczt00() {
		return sczt00;
	}
	public void setSczt00(java.lang.String sczt00) {
		this.sczt00 = sczt00;
	}
	public java.lang.String getSftz00() {
		return sftz00;
	}
	public void setSftz00(java.lang.String sftz00) {
		this.sftz00 = sftz00;
	}
	public java.lang.String getZfsfzh() {
		return zfsfzh;
	}
	public void setZfsfzh(java.lang.String zfsfzh) {
		this.zfsfzh = zfsfzh;
	}
	/**
	 * 胎动
	 *
	 * @return td0000
	 */
	public java.lang.String getTd0000() {
		return this.td0000;
	}
	/**
	 * @param td0000
	 *            胎动
	 */
	public void setTd0000(java.lang.String td0000) {
		this.td0000 = td0000;
	}
	/**
	 * 产科检查：宫底高度（cm）
	 *
	 * @return ckjcgg
	 */
	public java.math.BigDecimal getCkjcgg() {
		return this.ckjcgg;
	}

	/**
	 * 产科检查：腹围（cm）
	 *
	 * @return ckjcfw
	 */
	public java.math.BigDecimal getCkjcfw() {
		return this.ckjcfw;
	}

	/**
	 * 产科检查：胎位
	 *
	 * @return ckjctw
	 */
	public java.lang.String getCkjctw() {
		return this.ckjctw;
	}

	/**
	 * 产科检查：胎心率（次/分钟）
	 *
	 * @return ckjctx
	 */
	public String getCktxl0() {
		return this.cktxl0;
	}

	/**
	 * 先露
	 *
	 * @return xl0000
	 */
	public java.lang.String getXl0000() {
		return this.xl0000;
	}

	/**
	 * 浮肿
	 *
	 * @return fz0000
	 */
	public java.lang.String getFz0000() {
		return this.fz0000;
	}
	/**
	 * @param ckjcgg
	 *            产科检查：宫底高度（cm）
	 */
	public void setCkjcgg(java.math.BigDecimal ckjcgg) {
		this.ckjcgg = ckjcgg;
	}

	/**
	 * @param ckjcfw
	 *            产科检查：腹围（cm）
	 */
	public void setCkjcfw(java.math.BigDecimal ckjcfw) {
		this.ckjcfw = ckjcfw;
	}

	/**
	 * @param ckjctw
	 *            产科检查：胎位
	 */
	public void setCkjctw(java.lang.String ckjctw) {
		this.ckjctw = ckjctw;
	}

	/**
	 * @param ckjctx
	 *            产科检查：胎心率（次/分钟）
	 */
	public void setCktxl0(String cktxl0) {
		this.cktxl0 = cktxl0;
	}

	/**
	 * @param xl0000
	 *            先露
	 */
	public void setXl0000(java.lang.String xl0000) {
		this.xl0000 = xl0000;
	}

	/**
	 * @param fz0000
	 *            浮肿
	 */
	public void setFz0000(java.lang.String fz0000) {
		this.fz0000 = fz0000;
	}



	public java.sql.Timestamp getDzxgsj() {
		return dzxgsj;
	}
	public void setDzxgsj(java.sql.Timestamp dzxgsj) {
		this.dzxgsj = dzxgsj;
	}




	public java.lang.String getDzxgyy() {
		return dzxgyy;
	}
	public void setDzxgyy(java.lang.String dzxgyy) {
		this.dzxgyy = dzxgyy;
	}
	public java.lang.String getDzxgid() {
		return dzxgid;
	}
	public void setDzxgid(java.lang.String dzxgid) {
		this.dzxgid = dzxgid;
	}
	public java.lang.String getDzxgxm() {
		return dzxgxm;
	}
	public void setDzxgxm(java.lang.String dzxgxm) {
		this.dzxgxm = dzxgxm;
	}
	public java.sql.Timestamp getCjrq00() {
		return cjrq00;
	}
	public void setCjrq00(java.sql.Timestamp cjrq00) {
		this.cjrq00 = cjrq00;
	}
	public java.lang.String getCjz000() {
		return cjz000;
	}
	public void setCjz000(java.lang.String cjz000) {
		this.cjz000 = cjz000;
	}

	public String getBdjcnq() {
		return bdjcnq;
	}

	public void setBdjcnq(String bdjcnq) {
		this.bdjcnq = bdjcnq;
	}

	public String getBdjclq() {
		return bdjclq;
	}

	public void setBdjclq(String bdjclq) {
		this.bdjclq = bdjclq;
	}

	public java.sql.Date getGwyyrq() {
		return gwyyrq;
	}

	public void setGwyyrq(java.sql.Date gwyyrq) {
		this.gwyyrq = gwyyrq;
	}

	public String getRsdwtc() {
		return rsdwtc;
	}

	public void setRsdwtc(String rsdwtc) {
		this.rsdwtc = rsdwtc;
	}

	public java.lang.String getRsdwrq() {
		return rsdwrq;
	}

	public void setRsdwrq(java.lang.String rsdwrq) {
		this.rsdwrq = rsdwrq;
	}

	public String getRsdwqk() {
		return rsdwqk;
	}

	public void setRsdwqk(String rsdwqk) {
		this.rsdwqk = rsdwqk;
	}
	public String getSbkh00() {
		return sbkh00;
	}

	public void setSbkh00(String sbkh00) {
		this.sbkh00 = sbkh00;
	}

	public String getCardtype() {
		return cardtype;
	}

	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}

	public String getScybst() {
		return scybst;
	}

	public void setScybst(String scybst) {
		this.scybst = scybst;
	}

	public String getSceyst() {
		return sceyst;
	}

	public void setSceyst(String sceyst) {
		this.sceyst = sceyst;
	}

	public String getSjgqx0() {
		return sjgqx0;
	}

	public void setSjgqx0(String sjgqx0) {
		this.sjgqx0 = sjgqx0;
	}





	public String getFjjcgg() {
		return fjjcgg;
	}

	public void setFjjcgg(String fjjcgg) {
		this.fjjcgg = fjjcgg;
	}

	public String getFjbg00() {
		return fjbg00;
	}

	public void setFjbg00(String fjbg00) {
		this.fjbg00 = fjbg00;
	}

	public String getHivzx0() {
		return hivzx0;
	}

	public void setHivzx0(String hivzx0) {
		this.hivzx0 = hivzx0;
	}

	public String getNcgnt0() {
		return ncgnt0;
	}

	public void setNcgnt0(String ncgnt0) {
		this.ncgnt0 = ncgnt0;
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

	public String getNcgqt0() {
		return ncgqt0;
	}

	public void setNcgqt0(String ncgqt0) {
		this.ncgqt0 = ncgqt0;
	}

	public String getYdqjd0() {
		return ydqjd0;
	}

	public void setYdqjd0(String ydqjd0) {
		this.ydqjd0 = ydqjd0;
	}

	public java.lang.String getJzsgxya() {
		return jzsgxya;
	}

	public void setJzsgxya(java.lang.String jzsgxya) {
		this.jzsgxya = jzsgxya;
	}

	public java.lang.String getJzstnba() {
		return jzstnba;
	}

	public void setJzstnba(java.lang.String jzstnba) {
		this.jzstnba = jzstnba;
	}

	public java.lang.String getJzsycba() {
		return jzsycba;
	}

	public void setJzsycba(java.lang.String jzsycba) {
		this.jzsycba = jzsycba;
	}

	public java.lang.String getJzsjsba() {
		return jzsjsba;
	}

	public void setJzsjsba(java.lang.String jzsjsba) {
		this.jzsjsba = jzsjsba;
	}

	public java.lang.String getJzscd0a() {
		return jzscd0a;
	}

	public void setJzscd0a(java.lang.String jzscd0a) {
		this.jzscd0a = jzscd0a;
	}

	public java.lang.String getJzsjx0a() {
		return jzsjx0a;
	}

	public void setJzsjx0a(java.lang.String jzsjx0a) {
		this.jzsjx0a = jzsjx0a;
	}

	public java.lang.String getJzsqt0a() {
		return jzsqt0a;
	}

	public void setJzsqt0a(java.lang.String jzsqt0a) {
		this.jzsqt0a = jzsqt0a;
	}

	public java.lang.String getJzsgxy() {
		return jzsgxy;
	}

	public void setJzsgxy(java.lang.String jzsgxy) {
		this.jzsgxy = jzsgxy;
	}

	public java.lang.String getJzstnb() {
		return jzstnb;
	}

	public void setJzstnb(java.lang.String jzstnb) {
		this.jzstnb = jzstnb;
	}

	public java.lang.String getJzsycb() {
		return jzsycb;
	}

	public void setJzsycb(java.lang.String jzsycb) {
		this.jzsycb = jzsycb;
	}

	public java.lang.String getJzsjsb() {
		return jzsjsb;
	}

	public void setJzsjsb(java.lang.String jzsjsb) {
		this.jzsjsb = jzsjsb;
	}

	public java.lang.String getJzscd0() {
		return jzscd0;
	}

	public void setJzscd0(java.lang.String jzscd0) {
		this.jzscd0 = jzscd0;
	}

	public java.lang.String getJzsjx0() {
		return jzsjx0;
	}

	public void setJzsjx0(java.lang.String jzsjx0) {
		this.jzsjx0 = jzsjx0;
	}

	public java.lang.String getJzsqt0() {
		return jzsqt0;
	}

	public void setJzsqt0(java.lang.String jzsqt0) {
		this.jzsqt0 = jzsqt0;
	}

	public java.lang.String getGwpf00() {
		return gwpf00;
	}

	public void setGwpf00(java.lang.String gwpf00) {
		this.gwpf00 = gwpf00;
	}


	public java.lang.String getTbyz00() {
		return tbyz00;
	}

	public void setTbyz00(java.lang.String tbyz00) {
		this.tbyz00 = tbyz00;
	}

	public java.lang.String getTbyzts() {
		return tbyzts;
	}

	public void setTbyzts(java.lang.String tbyzts) {
		this.tbyzts = tbyzts;
	}

	public java.lang.String getGwys00() {
		return gwys00;
	}

	public void setGwys00(java.lang.String gwys00) {
		this.gwys00 = gwys00;
	}

	public java.lang.String getGwysbh() {
		return gwysbh;
	}

	public void setGwysbh(java.lang.String gwysbh) {
		this.gwysbh = gwysbh;
	}

	public java.lang.String getTjg000() {
		return tjg000;
	}

	public void setTjg000(java.lang.String tjg000) {
		this.tjg000 = tjg000;
	}

	public java.lang.String getTbys00() {
		return tbys00;
	}

	public void setTbys00(java.lang.String tbys00) {
		this.tbys00 = tbys00;
	}

	public java.lang.String getZhbjz0() {
		return zhbjz0;
	}

	public void setZhbjz0(java.lang.String zhbjz0) {
		this.zhbjz0 = zhbjz0;
	}

	public java.sql.Timestamp getZhbjsj() {
		return zhbjsj;
	}

	public void setZhbjsj(java.sql.Timestamp zhbjsj) {
		this.zhbjsj = zhbjsj;
	}


	public java.lang.String getGgngc0() {
		return ggngc0;
	}

	public void setGgngc0(java.lang.String ggngc0) {
		this.ggngc0 = ggngc0;
	}

	public java.lang.String getGgnbdb() {
		return ggnbdb;
	}

	public void setGgnbdb(java.lang.String ggnbdb) {
		this.ggnbdb = ggnbdb;
	}

	public java.lang.String getGgnzdh() {
		return ggnzdh;
	}

	public void setGgnzdh(java.lang.String ggnzdh) {
		this.ggnzdh = ggnzdh;
	}

	public java.lang.String getGgnjhd() {
		return ggnjhd;
	}

	public void setGgnjhd(java.lang.String ggnjhd) {
		this.ggnjhd = ggnjhd;
	}

	public java.lang.String getSgnxns() {
		return sgnxns;
	}

	public void setSgnxns(java.lang.String sgnxns) {
		this.sgnxns = sgnxns;
	}

	public java.lang.String getFjxxrh() {
		return fjxxrh;
	}

	public void setFjxxrh(java.lang.String fjxxrh) {
		this.fjxxrh = fjxxrh;
	}
	public String getSelorgid() {
		return selorgid;
	}

	public void setSelorgid(String selorgid) {
		this.selorgid = selorgid;
	}
	/**
	 * 产前检查编号
	 *
	 * @return xtbhcq
	 */
	public java.lang.String getXtbhcq() {
		return this.xtbhcq;
	}

	/**
	 * 手册系统编号
	 *
	 * @return scxtbh
	 */
	public java.lang.String getScxtbh() {
		return this.scxtbh;
	}

	/**
	 * 姓名
	 *
	 * @return xm0000
	 */
	public java.lang.String getXm0000() {
		return this.xm0000;
	}

	/**
	 * 出生日期
	 *
	 * @return csrq00
	 */
	public java.sql.Date getCsrq00() {
		return this.csrq00;
	}

	/**
	 * 实足年龄
	 *
	 * @return sznl00
	 */
	public java.math.BigDecimal getSznl00() {
		return this.sznl00;
	}

	/**
	 * 身份证号
	 *
	 * @return sfzh00
	 */
	public java.lang.String getSfzh00() {
		return this.sfzh00;
	}

	/**
	 * 职业
	 *
	 * @return zy0000
	 */
	public java.lang.String getZy0000() {
		return this.zy0000;
	}

	/**
	 * 文化程度
	 *
	 * @return whcd00
	 */
	public java.lang.String getWhcd00() {
		return this.whcd00;
	}

	/**
	 * 工作单位
	 *
	 * @return gzdw00
	 */
	public java.lang.String getGzdw00() {
		return this.gzdw00;
	}

	/**
	 * 籍贯
	 *
	 * @return jg0000
	 */
	public java.lang.String getJg0000() {
		return this.jg0000;
	}

	/**
	 * 联系电话
	 *
	 * @return lxdh00
	 */
	public java.lang.String getLxdh00() {
		return this.lxdh00;
	}

	/**
	 * 户口所在地：省
	 *
	 * @return hkdshe
	 */
	public java.lang.String getHkdshe() {
		return this.hkdshe;
	}

	/**
	 * 户口所在地：市
	 *
	 * @return hkdshi
	 */
	public java.lang.String getHkdshi() {
		return this.hkdshi;
	}

	/**
	 * 户口所在地：县(区)
	 *
	 * @return hkdxia
	 */
	public java.lang.String getHkdxia() {
		return this.hkdxia;
	}

	/**
	 * 户口所在地：镇(乡)
	 *
	 * @return hkdzhe
	 */
	public java.lang.String getHkdzhe() {
		return this.hkdzhe;
	}

	/**
	 * 户口所在地：村(居)
	 *
	 * @return hkdcun
	 */
	public java.lang.String getHkdcun() {
		return this.hkdcun;
	}

	/**
	 * 户口所在地：门牌号
	 *
	 * @return hkdmph
	 */
	public java.lang.String getHkdmph() {
		return this.hkdmph;
	}

	/**
	 * 现住地：省
	 *
	 * @return xzdshe
	 */
	public java.lang.String getXzdshe() {
		return this.xzdshe;
	}

	/**
	 * 现住地：市
	 *
	 * @return xzdshi
	 */
	public java.lang.String getXzdshi() {
		return this.xzdshi;
	}

	/**
	 * 现住地：县(区)
	 *
	 * @return xzdxia
	 */
	public java.lang.String getXzdxia() {
		return this.xzdxia;
	}

	/**
	 * 现住地：镇(乡)
	 *
	 * @return xzdzhe
	 */
	public java.lang.String getXzdzhe() {
		return this.xzdzhe;
	}

	/**
	 * 现住地：村(居)
	 *
	 * @return xzdcun
	 */
	public java.lang.String getXzdcun() {
		return this.xzdcun;
	}

	/**
	 * 现住地：门牌号
	 *
	 * @return xzdmph
	 */
	public java.lang.String getXzdmph() {
		return this.xzdmph;
	}

	/**
	 * 丈夫姓名
	 *
	 * @return zfxm00
	 */
	public java.lang.String getZfxm00() {
		return this.zfxm00;
	}

	/**
	 * 丈夫年龄
	 *
	 * @return zfnl00
	 */
	public java.math.BigDecimal getZfnl00() {
		return this.zfnl00;
	}

	/**
	 * 丈夫工作单位
	 *
	 * @return zfgzdw
	 */
	public java.lang.String getZfgzdw() {
		return this.zfgzdw;
	}

	/**
	 * 丈夫健康情况
	 *
	 * @return zfjkqk
	 */
	public java.lang.String getZfjkqk() {
		return this.zfjkqk;
	}

	/**
	 * 婚姻史：末次月经(为空表示“不详”)
	 *
	 * @return mcyj00
	 */
	public java.sql.Date getMcyj00() {
		return this.mcyj00;
	}

	/**
	 * 婚姻史：结婚年龄
	 *
	 * @return jhnl00
	 */
	public java.math.BigDecimal getJhnl00() {
		return this.jhnl00;
	}

	/**
	 * 婚姻史：亲缘结婚（1有，2 无）
	 *
	 * @return qyjh00
	 */
	public java.lang.String getQyjh00() {
		return this.qyjh00;
	}

	/**
	 * 婚姻史：月经史
	 *
	 * @return yjs000
	 */
	public java.lang.String getYjs000() {
		return this.yjs000;
	}

	/**
	 * 婚姻史：预产期
	 *
	 * @return ycq000
	 */
	public java.sql.Date getYcq000() {
		return this.ycq000;
	}

	/**
	 * 婚姻史：孕次
	 *
	 * @return yunci0
	 */
	public java.math.BigDecimal getYunci0() {
		return this.yunci0;
	}

	/**
	 * 婚姻史：产次：阴道分娩
	 *
	 * @return ccydfm
	 */
	public java.math.BigDecimal getCcydfm() {
		return this.ccydfm;
	}

	/**
	 * 婚姻史：产次：剖宫产
	 *
	 * @return ccpgc0
	 */
	public java.math.BigDecimal getCcpgc0() {
		return this.ccpgc0;
	}

	/**
	 * 现孕史:妊娠反应（1有，2 无）有的话填写月
	 *
	 * @return rsfy00
	 */
	public java.lang.String getRsfy00() {
		return this.rsfy00;
	}

	/**
	 * 现孕史：初感胎动（1感，2未感）
	 *
	 * @return cgtd00
	 */
	public java.lang.String getCgtd00() {
		return this.cgtd00;
	}

	/**
	 * 现孕史：剧吐
	 *
	 * @return jt0000
	 */
	public java.lang.String getJt0000() {
		return this.jt0000;
	}

	/**
	 * 现孕史：阴道出血
	 *
	 * @return ydcx00
	 */
	public java.lang.String getYdcx00() {
		return this.ydcx00;
	}

	/**
	 * 现孕史：发热
	 *
	 * @return fr0000
	 */
	public java.lang.String getFr0000() {
		return this.fr0000;
	}

	/**
	 * 现孕史：过敏
	 *
	 * @return gm0000
	 */
	public java.lang.String getGm0000() {
		return this.gm0000;
	}

	/**
	 * 现孕史：服药
	 *
	 * @return fy0000
	 */
	public java.lang.String getFy0000() {
		return this.fy0000;
	}

	/**
	 * 现孕史：病毒感染
	 *
	 * @return bfgr00
	 */
	public java.lang.String getBfgr00() {
		return this.bfgr00;
	}

	/**
	 * 现孕史：接触有害物质
	 *
	 * @return jcyhwz
	 */
	public java.lang.String getJcyhwz() {
		return this.jcyhwz;
	}

	/**
	 * 现孕史：服避孕药
	 *
	 * @return fbyy0
	 */
	public java.lang.String getFbyy0() {
		return this.fbyy0;
	}

	/**
	 * 现孕史：其他：
	 *
	 * @return xysqt0
	 */
	public java.lang.String getXysqt0() {
		return this.xysqt0;
	}

	/**
	 * 妊娠史：第一胎日期
	 *
	 * @return rsdyrq
	 */
	public java.lang.String getRsdyrq() {
		return this.rsdyrq;
	}

	/**
	 * 妊娠史：第一胎情况
	 *
	 * @return rsdyqk
	 */
	public java.lang.String getRsdyqk() {
		return this.rsdyqk;
	}

	/**
	 * 妊娠史：第二胎日期
	 *
	 * @return rsderq
	 */
	public java.lang.String getRsderq() {
		return this.rsderq;
	}

	/**
	 * 妊娠史：第二胎情况
	 *
	 * @return rsdeqk
	 */
	public java.lang.String getRsdeqk() {
		return this.rsdeqk;
	}

	/**
	 * 妊娠史：第三胎日期
	 *
	 * @return rsdsrq
	 */
	public java.lang.String getRsdsrq() {
		return this.rsdsrq;
	}

	/**
	 * 妊娠史：第三胎情况
	 *
	 * @return rsdsqk
	 */
	public java.lang.String getRsdsqk() {
		return this.rsdsqk;
	}

	/**
	 * 妊娠史：第四胎日期
	 *
	 * @return rsstrq
	 */
	public java.lang.String getRsstrq() {
		return this.rsstrq;
	}

	/**
	 * 妊娠史：第四胎情况
	 *
	 * @return rsstqk
	 */
	public java.lang.String getRsstqk() {
		return this.rsstqk;
	}

	/**
	 * 既往史：心
	 *
	 * @return jwsx00
	 */
	public java.lang.String getJwsx00() {
		return this.jwsx00;
	}

	/**
	 * 既往史：肺
	 *
	 * @return jwsf00
	 */
	public java.lang.String getJwsf00() {
		return this.jwsf00;
	}

	/**
	 * 既往史：肝
	 *
	 * @return jwsg00
	 */
	public java.lang.String getJwsg00() {
		return this.jwsg00;
	}

	/**
	 * 既往史：肾
	 *
	 * @return jwss00
	 */
	public java.lang.String getJwss00() {
		return this.jwss00;
	}

	/**
	 * 既往史：高血压
	 *
	 * @return jwsgxy
	 */
	public java.lang.String getJwsgxy() {
		return this.jwsgxy;
	}

	/**
	 * 既往史：糖尿病
	 *
	 * @return jwstnb
	 */
	public java.lang.String getJwstnb() {
		return this.jwstnb;
	}

	/**
	 * 既往史：甲亢
	 *
	 * @return jwsjk0
	 */
	public java.lang.String getJwsjk0() {
		return this.jwsjk0;
	}

	/**
	 * 既往史：过敏史
	 *
	 * @return jwsgms
	 */
	public java.lang.String getJwsgms() {
		return this.jwsgms;
	}

	/**
	 * 既往史：精神病
	 *
	 * @return jwsjsb
	 */
	public java.lang.String getJwsjsb() {
		return this.jwsjsb;
	}

	/**
	 * 既往史：血液病
	 *
	 * @return jwsxyb
	 */
	public java.lang.String getJwsxyb() {
		return this.jwsxyb;
	}

	/**
	 * 既往史：癫痫
	 *
	 * @return jwsdx0
	 */
	public java.lang.String getJwsdx0() {
		return this.jwsdx0;
	}

	/**
	 * 既往史：手术史
	 *
	 * @return jwssss
	 */
	public java.lang.String getJwssss() {
		return this.jwssss;
	}

	/**
	 * 既往史：其他
	 *
	 * @return jwsqt0
	 */
	public java.lang.String getJwsqt0() {
		return this.jwsqt0;
	}

	/**
	 * 家族史：本人
	 *
	 * @return jzsbr0
	 */
	public java.lang.String getJzsbr0() {
		return this.jzsbr0;
	}

	/**
	 * 家族史：爱人
	 *
	 * @return jzsar0
	 */
	public java.lang.String getJzsar0() {
		return this.jzsar0;
	}

	/**
	 * 体检：基础血压
	 *
	 * @return tjjcxy
	 */
	public java.lang.String getTjjcxy() {
		return this.tjjcxy;
	}

	/**
	 * 体检：血压
	 *
	 * @return tjxy00
	 */
	public java.lang.String getTjxy00() {
		return this.tjxy00;
	}

	/**
	 * 体检：身高
	 *
	 * @return tjsg00
	 */
	public java.lang.String getTjsg00() {
		return this.tjsg00;
	}

	/**
	 * 体检：体重
	 *
	 * @return tjtz00
	 */
	public java.lang.String getTjtz00() {
		return this.tjtz00;
	}

	/**
	 * 体检：体重指数
	 *
	 * @return tjtzzs
	 */
	public java.lang.String getTjtzzs() {
		return this.tjtzzs;
	}

	/**
	 * 体检：甲状腺
	 *
	 * @return tjjzx0
	 */
	public java.lang.String getTjjzx0() {
		return this.tjjzx0;
	}

	/**
	 * 体检：心
	 *
	 * @return tjx000
	 */
	public java.lang.String getTjx000() {
		return this.tjx000;
	}

	/**
	 * 体检：肺
	 *
	 * @return tjf000
	 */
	public java.lang.String getTjf000() {
		return this.tjf000;
	}

	/**
	 * 体检：脾
	 *
	 * @return tjp000
	 */
	public java.lang.String getTjp000() {
		return this.tjp000;
	}

	/**
	 * 体检：肾
	 *
	 * @return tjs000
	 */
	public java.lang.String getTjs000() {
		return this.tjs000;
	}

	/**
	 * 体检：乳头
	 *
	 * @return tjrt00
	 */
	public java.lang.String getTjrt00() {
		return this.tjrt00;
	}

	/**
	 * 体检：脊柱四肢
	 *
	 * @return tjjzsz
	 */
	public java.lang.String getTjjzsz() {
		return this.tjjzsz;
	}

	/**
	 * 体检：浮肿
	 *
	 * @return tjfz00
	 */
	public java.lang.String getTjfz00() {
		return this.tjfz00;
	}

	/**
	 * 体检：腱反射
	 *
	 * @return tjjfs0
	 */
	public java.lang.String getTjjfs0() {
		return this.tjjfs0;
	}

	/**
	 * 体检：静脉曲张
	 *
	 * @return tjjmqz
	 */
	public java.lang.String getTjjmqz() {
		return this.tjjmqz;
	}

	/**
	 * 体检：其他
	 *
	 * @return tjqt00
	 */
	public java.lang.String getTjqt00() {
		return this.tjqt00;
	}

	/**
	 * 妇检：外阴
	 *
	 * @return fjwy00
	 */
	public java.lang.String getFjwy00() {
		return this.fjwy00;
	}

	/**
	 * 妇检：阴道
	 *
	 * @return fjyd00
	 */
	public java.lang.String getFjyd00() {
		return this.fjyd00;
	}

	/**
	 * 妇检：宫颈
	 *
	 * @return fjgj00
	 */
	public java.lang.String getFjgj00() {
		return this.fjgj00;
	}

	/**
	 * 妇检：宫体
	 *
	 * @return fjgt00
	 */
	public java.lang.String getFjgt00() {
		return this.fjgt00;
	}

	/**
	 * 妇检：附件
	 *
	 * @return fjfj00
	 */
	public java.lang.String getFjfj00() {
		return this.fjfj00;
	}

	/**
	 * 产检：骨盘测量：骼棘间径
	 *
	 * @return cjgjjj
	 */
	public java.lang.String getCjgjjj() {
		return this.cjgjjj;
	}

	/**
	 * 产检：骨盘测量：骼嵴间径
	 *
	 * @return cjgj00
	 */
	public java.lang.String getCjgj00() {
		return this.cjgj00;
	}

	/**
	 * 产检：骨盘测量：骶耻外径
	 *
	 * @return cjdcwj
	 */
	public java.lang.String getCjdcwj() {
		return this.cjdcwj;
	}

	/**
	 * 产检：骨盘测量：坐骨结节间径
	 *
	 * @return cjzgjj
	 */
	public java.lang.String getCjzgjj() {
		return this.cjzgjj;
	}

	/**
	 * 辅助检查：血红蛋白
	 *
	 * @return fjxdb0
	 */
	public java.lang.String getFjxdb0() {
		return this.fjxdb0;
	}

	/**
	 * 辅助检查：尿常规
	 *
	 * @return fjncg0
	 */
	public java.lang.String getFjncg0() {
		return this.fjncg0;
	}

	/**
	 * 辅助检查：白带常规
	 *
	 * @return fjbcg0
	 */
	public java.lang.String getFjbcg0() {
		return this.fjbcg0;
	}

	/**
	 * 辅助检查：血型
	 *
	 * @return fjxx00
	 */
	public java.lang.String getFjxx00() {
		return this.fjxx00;
	}

	/**
	 * 辅助检查：RPR
	 *
	 * @return fjrpr0
	 */
	public java.lang.String getFjrpr0() {
		return this.fjrpr0;
	}

	/**
	 * 辅助检查：HIV
	 *
	 * @return fjhiv0
	 */
	public java.lang.String getFjhiv0() {
		return this.fjhiv0;
	}

	/**
	 * 辅助检查：HBsAg
	 *
	 * @return fjhbsa
	 */
	public java.lang.String getFjhbsa() {
		return this.fjhbsa;
	}

	/**
	 * 辅助检查：产前筛查
	 *
	 * @return fjcqsc
	 */
	public java.lang.String getFjcqsc() {
		return this.fjcqsc;
	}

	/**
	 * 辅助检查：肾功
	 *
	 * @return fjsg00
	 */
	public java.lang.String getFjsg00() {
		return this.fjsg00;
	}

	/**
	 * 辅助检查：肝功
	 *
	 * @return fjgg00
	 */
	public java.lang.String getFjgg00() {
		return this.fjgg00;
	}

	/**
	 * 辅助检查：糖筛查
	 *
	 * @return fjtsc0
	 */
	public java.lang.String getFjtsc0() {
		return this.fjtsc0;
	}

	/**
	 * 辅助检查：其他
	 *
	 * @return fjqt00
	 */
	public java.lang.String getFjqt00() {
		return this.fjqt00;
	}

	/**
	 * 辅助检查：B超
	 *
	 * @return fjbc00
	 */
	public java.lang.String getFjbc00() {
		return this.fjbc00;
	}

	/**
	 * 辅助检查：心电图
	 *
	 * @return fjxdt0
	 */
	public java.lang.String getFjxdt0() {
		return this.fjxdt0;
	}

	/**
	 * 保健指导
	 *
	 * @return bjzd00
	 */
	public java.lang.String getBjzd00() {
		return this.bjzd00;
	}

	/**
	 * 初诊诊断G
	 *
	 * @return czzdg0
	 */
	public java.lang.String getCzzdg0() {
		return this.czzdg0;
	}

	/**
	 * 初诊诊断P
	 *
	 * @return czzdp0
	 */
	public java.lang.String getCzzdp0() {
		return this.czzdp0;
	}

	/**
	 * 初诊诊断：周宫内妊娠
	 *
	 * @return czzdrs
	 */
	public java.lang.String getCzzdrs() {
		return this.czzdrs;
	}

	/**
	 * 初诊诊断：诊断内容
	 *
	 * @return czzdnr
	 */
	public java.lang.String getCzzdnr() {
		return this.czzdnr;
	}

	/**
	 * 入院日期
	 *
	 * @return ryrq00
	 */
	public java.sql.Date getRyrq00() {
		return this.ryrq00;
	}

	/**
	 * 主诉
	 *
	 * @return zs0000
	 */
	public java.lang.String getZs0000() {
		return this.zs0000;
	}

	/**
	 * 入院诊断
	 *
	 * @return ryzd00
	 */
	public java.lang.String getRyzd00() {
		return this.ryzd00;
	}

	/**
	 * 处理
	 *
	 * @return cl0000
	 */
	public java.lang.String getCl0000() {
		return this.cl0000;
	}

	/**
	 * 转诊
	 *
	 * @return zz0000
	 */
	public java.lang.String getZz0000() {
		return this.zz0000;
	}

	/**
	 * 检查单位
	 *
	 * @return jcdw00
	 */
	public java.lang.String getJcdw00() {
		return this.jcdw00;
	}

	/**
	 * 检查日期
	 *
	 * @return jcrq00
	 */
	public java.sql.Date getJcrq00() {
		return this.jcrq00;
	}

	/**
	 * 住院号
	 *
	 * @return zyh000
	 */
	public java.lang.String getZyh000() {
		return this.zyh000;
	}

	/**
	 * 产科门诊编号
	 *
	 * @return ckmzbh
	 */
	public java.lang.String getCkmzbh() {
		return this.ckmzbh;
	}

	/**
	 * 妊娠史：胎次一
	 *
	 * @return rsdytc
	 */
	public java.lang.String getRsdytc() {
		return this.rsdytc;
	}

	/**
	 * 妊娠史：胎次二
	 *
	 * @return rsdetc
	 */
	public java.lang.String getRsdetc() {
		return this.rsdetc;
	}

	/**
	 * 妊娠史：胎次三
	 *
	 * @return rsdstc
	 */
	public java.lang.String getRsdstc() {
		return this.rsdstc;
	}

	/**
	 * 妊娠史：胎次四
	 *
	 * @return rssttc
	 */
	public java.lang.String getRssttc() {
		return this.rssttc;
	}

	/**
	 * 病史询问者
	 *
	 * @return bsxwz0
	 */
	public java.lang.String getBsxwz0() {
		return this.bsxwz0;
	}

	/**
	 * 检查者
	 *
	 * @return jcz000
	 */
	public java.lang.String getJcz000() {
		return this.jcz000;
	}

	/**
	 * 户口状态
	 *
	 * @return hkzt00
	 */
	public java.lang.String getHkzt00() {
		return this.hkzt00;
	}

	/**
	 * 签名
	 *
	 * @return qm0000
	 */
	public java.lang.String getQm0000() {
		return this.qm0000;
	}

	/**
	 * 检查单位名称
	 *
	 * @return jcdwna
	 */
	public java.lang.String getJcdwna() {
		return this.jcdwna;
	}

	/**
	 * 检查医生
	 *
	 * @return jcys00
	 */
	public java.lang.String getJcys00() {
		return this.jcys00;
	}

	/**
	 * 检查医生名称
	 *
	 * @return jcysna
	 */
	public java.lang.String getJcysna() {
		return this.jcysna;
	}

	/**
	 * @param xtbhcq
	 *            产前检查编号
	 */
	public void setXtbhcq(java.lang.String xtbhcq) {
		this.xtbhcq = xtbhcq;
	}

	/**
	 * @param scxtbh
	 *            手册系统编号
	 */
	public void setScxtbh(java.lang.String scxtbh) {
		this.scxtbh = scxtbh;
	}

	/**
	 * @param xm0000
	 *            姓名
	 */
	public void setXm0000(java.lang.String xm0000) {
		this.xm0000 = xm0000;
	}

	/**
	 * @param csrq00
	 *            出生日期
	 */
	public void setCsrq00(java.sql.Date csrq00) {
		this.csrq00 = csrq00;
	}

	/**
	 * @param sznl00
	 *            实足年龄
	 */
	public void setSznl00(java.math.BigDecimal sznl00) {
		this.sznl00 = sznl00;
	}

	/**
	 * @param sfzh00
	 *            身份证号
	 */
	public void setSfzh00(java.lang.String sfzh00) {
		this.sfzh00 = sfzh00;
	}

	/**
	 * @param zy0000
	 *            职业
	 */
	public void setZy0000(java.lang.String zy0000) {
		this.zy0000 = zy0000;
	}

	/**
	 * @param whcd00
	 *            文化程度
	 */
	public void setWhcd00(java.lang.String whcd00) {
		this.whcd00 = whcd00;
	}

	/**
	 * @param gzdw00
	 *            工作单位
	 */
	public void setGzdw00(java.lang.String gzdw00) {
		this.gzdw00 = gzdw00;
	}

	/**
	 * @param jg0000
	 *            籍贯
	 */
	public void setJg0000(java.lang.String jg0000) {
		this.jg0000 = jg0000;
	}

	/**
	 * @param lxdh00
	 *            联系电话
	 */
	public void setLxdh00(java.lang.String lxdh00) {
		this.lxdh00 = lxdh00;
	}

	/**
	 * @param hkdshe
	 *            户口所在地：省
	 */
	public void setHkdshe(java.lang.String hkdshe) {
		this.hkdshe = hkdshe;
	}

	/**
	 * @param hkdshi
	 *            户口所在地：市
	 */
	public void setHkdshi(java.lang.String hkdshi) {
		this.hkdshi = hkdshi;
	}

	/**
	 * @param hkdxia
	 *            户口所在地：县(区)
	 */
	public void setHkdxia(java.lang.String hkdxia) {
		this.hkdxia = hkdxia;
	}

	/**
	 * @param hkdzhe
	 *            户口所在地：镇(乡)
	 */
	public void setHkdzhe(java.lang.String hkdzhe) {
		this.hkdzhe = hkdzhe;
	}

	/**
	 * @param hkdcun
	 *            户口所在地：村(居)
	 */
	public void setHkdcun(java.lang.String hkdcun) {
		this.hkdcun = hkdcun;
	}

	/**
	 * @param hkdmph
	 *            户口所在地：门牌号
	 */
	public void setHkdmph(java.lang.String hkdmph) {
		this.hkdmph = hkdmph;
	}

	/**
	 * @param xzdshe
	 *            现住地：省
	 */
	public void setXzdshe(java.lang.String xzdshe) {
		this.xzdshe = xzdshe;
	}

	/**
	 * @param xzdshi
	 *            现住地：市
	 */
	public void setXzdshi(java.lang.String xzdshi) {
		this.xzdshi = xzdshi;
	}

	/**
	 * @param xzdxia
	 *            现住地：县(区)
	 */
	public void setXzdxia(java.lang.String xzdxia) {
		this.xzdxia = xzdxia;
	}

	/**
	 * @param xzdzhe
	 *            现住地：镇(乡)
	 */
	public void setXzdzhe(java.lang.String xzdzhe) {
		this.xzdzhe = xzdzhe;
	}

	/**
	 * @param xzdcun
	 *            现住地：村(居)
	 */
	public void setXzdcun(java.lang.String xzdcun) {
		this.xzdcun = xzdcun;
	}

	/**
	 * @param xzdmph
	 *            现住地：门牌号
	 */
	public void setXzdmph(java.lang.String xzdmph) {
		this.xzdmph = xzdmph;
	}

	/**
	 * @param zfxm00
	 *            丈夫姓名
	 */
	public void setZfxm00(java.lang.String zfxm00) {
		this.zfxm00 = zfxm00;
	}

	/**
	 * @param zfnl00
	 *            丈夫年龄
	 */
	public void setZfnl00(java.math.BigDecimal zfnl00) {
		this.zfnl00 = zfnl00;
	}

	/**
	 * @param zfgzdw
	 *            丈夫工作单位
	 */
	public void setZfgzdw(java.lang.String zfgzdw) {
		this.zfgzdw = zfgzdw;
	}

	/**
	 * @param zfjkqk
	 *            丈夫健康情况
	 */
	public void setZfjkqk(java.lang.String zfjkqk) {
		this.zfjkqk = zfjkqk;
	}

	/**
	 * @param mcyj00
	 *            婚姻史：末次月经(为空表示“不详”)
	 */
	public void setMcyj00(java.sql.Date mcyj00) {
		this.mcyj00 = mcyj00;
	}

	/**
	 * @param jhnl00
	 *            婚姻史：结婚年龄
	 */
	public void setJhnl00(java.math.BigDecimal jhnl00) {
		this.jhnl00 = jhnl00;
	}

	/**
	 * @param qyjh00
	 *            婚姻史：亲缘结婚（1有，2 无）
	 */
	public void setQyjh00(java.lang.String qyjh00) {
		this.qyjh00 = qyjh00;
	}

	/**
	 * @param yjs000
	 *            婚姻史：月经史
	 */
	public void setYjs000(java.lang.String yjs000) {
		this.yjs000 = yjs000;
	}

	/**
	 * @param ycq000
	 *            婚姻史：预产期
	 */
	public void setYcq000(java.sql.Date ycq000) {
		this.ycq000 = ycq000;
	}

	/**
	 * @param yunci0
	 *            婚姻史：孕次
	 */
	public void setYunci0(java.math.BigDecimal yunci0) {
		this.yunci0 = yunci0;
	}

	/**
	 * @param ccydfm
	 *            婚姻史：产次：阴道分娩
	 */
	public void setCcydfm(java.math.BigDecimal ccydfm) {
		this.ccydfm = ccydfm;
	}

	/**
	 * @param ccpgc0
	 *            婚姻史：产次：剖宫产
	 */
	public void setCcpgc0(java.math.BigDecimal ccpgc0) {
		this.ccpgc0 = ccpgc0;
	}

	/**
	 * @param rsfy00
	 *            现孕史:妊娠反应（1有，2 无）有的话填写月
	 */
	public void setRsfy00(java.lang.String rsfy00) {
		this.rsfy00 = rsfy00;
	}

	/**
	 * @param cgtd00
	 *            现孕史：初感胎动（1感，2未感）
	 */
	public void setCgtd00(java.lang.String cgtd00) {
		this.cgtd00 = cgtd00;
	}

	/**
	 * @param jt0000
	 *            现孕史：剧吐
	 */
	public void setJt0000(java.lang.String jt0000) {
		this.jt0000 = jt0000;
	}

	/**
	 * @param ydcx00
	 *            现孕史：阴道出血
	 */
	public void setYdcx00(java.lang.String ydcx00) {
		this.ydcx00 = ydcx00;
	}

	/**
	 * @param fr0000
	 *            现孕史：发热
	 */
	public void setFr0000(java.lang.String fr0000) {
		this.fr0000 = fr0000;
	}

	/**
	 * @param gm0000
	 *            现孕史：过敏
	 */
	public void setGm0000(java.lang.String gm0000) {
		this.gm0000 = gm0000;
	}

	/**
	 * @param fy0000
	 *            现孕史：服药
	 */
	public void setFy0000(java.lang.String fy0000) {
		this.fy0000 = fy0000;
	}

	/**
	 * @param bfgr00
	 *            现孕史：病毒感染
	 */
	public void setBfgr00(java.lang.String bfgr00) {
		this.bfgr00 = bfgr00;
	}

	/**
	 * @param jcyhwz
	 *            现孕史：接触有害物质
	 */
	public void setJcyhwz(java.lang.String jcyhwz) {
		this.jcyhwz = jcyhwz;
	}

	/**
	 * @param fbyy0
	 *            现孕史：服避孕药
	 */
	public void setFbyy0(java.lang.String fbyy0) {
		this.fbyy0 = fbyy0;
	}

	/**
	 * @param xysqt0
	 *            现孕史：其他：
	 */
	public void setXysqt0(java.lang.String xysqt0) {
		this.xysqt0 = xysqt0;
	}

	/**
	 * @param rsdyrq
	 *            妊娠史：第一胎日期
	 */
	public void setRsdyrq(java.lang.String rsdyrq) {
		this.rsdyrq = rsdyrq;
	}

	/**
	 * @param rsdyqk
	 *            妊娠史：第一胎情况
	 */
	public void setRsdyqk(java.lang.String rsdyqk) {
		this.rsdyqk = rsdyqk;
	}

	/**
	 * @param rsderq
	 *            妊娠史：第二胎日期
	 */
	public void setRsderq(java.lang.String rsderq) {
		this.rsderq = rsderq;
	}

	/**
	 * @param rsdeqk
	 *            妊娠史：第二胎情况
	 */
	public void setRsdeqk(java.lang.String rsdeqk) {
		this.rsdeqk = rsdeqk;
	}

	/**
	 * @param rsdsrq
	 *            妊娠史：第三胎日期
	 */
	public void setRsdsrq(java.lang.String rsdsrq) {
		this.rsdsrq = rsdsrq;
	}

	/**
	 * @param rsdsqk
	 *            妊娠史：第三胎情况
	 */
	public void setRsdsqk(java.lang.String rsdsqk) {
		this.rsdsqk = rsdsqk;
	}

	/**
	 * @param rsstrq
	 *            妊娠史：第四胎日期
	 */
	public void setRsstrq(java.lang.String rsstrq) {
		this.rsstrq = rsstrq;
	}

	/**
	 * @param rsstqk
	 *            妊娠史：第四胎情况
	 */
	public void setRsstqk(java.lang.String rsstqk) {
		this.rsstqk = rsstqk;
	}

	/**
	 * @param jwsx00
	 *            既往史：心
	 */
	public void setJwsx00(java.lang.String jwsx00) {
		this.jwsx00 = jwsx00;
	}

	/**
	 * @param jwsf00
	 *            既往史：肺
	 */
	public void setJwsf00(java.lang.String jwsf00) {
		this.jwsf00 = jwsf00;
	}

	/**
	 * @param jwsg00
	 *            既往史：肝
	 */
	public void setJwsg00(java.lang.String jwsg00) {
		this.jwsg00 = jwsg00;
	}

	/**
	 * @param jwss00
	 *            既往史：肾
	 */
	public void setJwss00(java.lang.String jwss00) {
		this.jwss00 = jwss00;
	}

	/**
	 * @param jwsgxy
	 *            既往史：高血压
	 */
	public void setJwsgxy(java.lang.String jwsgxy) {
		this.jwsgxy = jwsgxy;
	}

	/**
	 * @param jwstnb
	 *            既往史：糖尿病
	 */
	public void setJwstnb(java.lang.String jwstnb) {
		this.jwstnb = jwstnb;
	}

	/**
	 * @param jwsjk0
	 *            既往史：甲亢
	 */
	public void setJwsjk0(java.lang.String jwsjk0) {
		this.jwsjk0 = jwsjk0;
	}

	/**
	 * @param jwsgms
	 *            既往史：过敏史
	 */
	public void setJwsgms(java.lang.String jwsgms) {
		this.jwsgms = jwsgms;
	}

	/**
	 * @param jwsjsb
	 *            既往史：精神病
	 */
	public void setJwsjsb(java.lang.String jwsjsb) {
		this.jwsjsb = jwsjsb;
	}

	/**
	 * @param jwsxyb
	 *            既往史：血液病
	 */
	public void setJwsxyb(java.lang.String jwsxyb) {
		this.jwsxyb = jwsxyb;
	}

	/**
	 * @param jwsdx0
	 *            既往史：癫痫
	 */
	public void setJwsdx0(java.lang.String jwsdx0) {
		this.jwsdx0 = jwsdx0;
	}

	/**
	 * @param jwssss
	 *            既往史：手术史
	 */
	public void setJwssss(java.lang.String jwssss) {
		this.jwssss = jwssss;
	}

	/**
	 * @param jwsqt0
	 *            既往史：其他
	 */
	public void setJwsqt0(java.lang.String jwsqt0) {
		this.jwsqt0 = jwsqt0;
	}

	/**
	 * @param jzsbr0
	 *            家族史：本人
	 */
	public void setJzsbr0(java.lang.String jzsbr0) {
		this.jzsbr0 = jzsbr0;
	}

	/**
	 * @param jzsar0
	 *            家族史：爱人
	 */
	public void setJzsar0(java.lang.String jzsar0) {
		this.jzsar0 = jzsar0;
	}

	/**
	 * @param tjjcxy
	 *            体检：基础血压
	 */
	public void setTjjcxy(java.lang.String tjjcxy) {
		this.tjjcxy = tjjcxy;
	}

	/**
	 * @param tjxy00
	 *            体检：血压
	 */
	public void setTjxy00(java.lang.String tjxy00) {
		this.tjxy00 = tjxy00;
	}

	/**
	 * @param tjsg00
	 *            体检：身高
	 */
	public void setTjsg00(java.lang.String tjsg00) {
		this.tjsg00 = tjsg00;
	}

	/**
	 * @param tjtz00
	 *            体检：体重
	 */
	public void setTjtz00(java.lang.String tjtz00) {
		this.tjtz00 = tjtz00;
	}

	/**
	 * @param tjtzzs
	 *            体检：体重指数
	 */
	public void setTjtzzs(java.lang.String tjtzzs) {
		this.tjtzzs = tjtzzs;
	}

	/**
	 * @param tjjzx0
	 *            体检：甲状腺
	 */
	public void setTjjzx0(java.lang.String tjjzx0) {
		this.tjjzx0 = tjjzx0;
	}

	/**
	 * @param tjx000
	 *            体检：心
	 */
	public void setTjx000(java.lang.String tjx000) {
		this.tjx000 = tjx000;
	}

	/**
	 * @param tjf000
	 *            体检：肺
	 */
	public void setTjf000(java.lang.String tjf000) {
		this.tjf000 = tjf000;
	}

	/**
	 * @param tjp000
	 *            体检：脾
	 */
	public void setTjp000(java.lang.String tjp000) {
		this.tjp000 = tjp000;
	}

	/**
	 * @param tjs000
	 *            体检：肾
	 */
	public void setTjs000(java.lang.String tjs000) {
		this.tjs000 = tjs000;
	}

	/**
	 * @param tjrt00
	 *            体检：乳头
	 */
	public void setTjrt00(java.lang.String tjrt00) {
		this.tjrt00 = tjrt00;
	}

	/**
	 * @param tjjzsz
	 *            体检：脊柱四肢
	 */
	public void setTjjzsz(java.lang.String tjjzsz) {
		this.tjjzsz = tjjzsz;
	}

	/**
	 * @param tjfz00
	 *            体检：浮肿
	 */
	public void setTjfz00(java.lang.String tjfz00) {
		this.tjfz00 = tjfz00;
	}

	/**
	 * @param tjjfs0
	 *            体检：腱反射
	 */
	public void setTjjfs0(java.lang.String tjjfs0) {
		this.tjjfs0 = tjjfs0;
	}

	/**
	 * @param tjjmqz
	 *            体检：静脉曲张
	 */
	public void setTjjmqz(java.lang.String tjjmqz) {
		this.tjjmqz = tjjmqz;
	}

	/**
	 * @param tjqt00
	 *            体检：其他
	 */
	public void setTjqt00(java.lang.String tjqt00) {
		this.tjqt00 = tjqt00;
	}

	/**
	 * @param fjwy00
	 *            妇检：外阴
	 */
	public void setFjwy00(java.lang.String fjwy00) {
		this.fjwy00 = fjwy00;
	}

	/**
	 * @param fjyd00
	 *            妇检：阴道
	 */
	public void setFjyd00(java.lang.String fjyd00) {
		this.fjyd00 = fjyd00;
	}

	/**
	 * @param fjgj00
	 *            妇检：宫颈
	 */
	public void setFjgj00(java.lang.String fjgj00) {
		this.fjgj00 = fjgj00;
	}

	/**
	 * @param fjgt00
	 *            妇检：宫体
	 */
	public void setFjgt00(java.lang.String fjgt00) {
		this.fjgt00 = fjgt00;
	}

	/**
	 * @param fjfj00
	 *            妇检：附件
	 */
	public void setFjfj00(java.lang.String fjfj00) {
		this.fjfj00 = fjfj00;
	}

	/**
	 * @param cjgjjj
	 *            产检：骨盘测量：骼棘间径
	 */
	public void setCjgjjj(java.lang.String cjgjjj) {
		this.cjgjjj = cjgjjj;
	}

	/**
	 * @param cjgj00
	 *            产检：骨盘测量：骼嵴间径
	 */
	public void setCjgj00(java.lang.String cjgj00) {
		this.cjgj00 = cjgj00;
	}

	/**
	 * @param cjdcwj
	 *            产检：骨盘测量：骶耻外径
	 */
	public void setCjdcwj(java.lang.String cjdcwj) {
		this.cjdcwj = cjdcwj;
	}

	/**
	 * @param cjzgjj
	 *            产检：骨盘测量：坐骨结节间径
	 */
	public void setCjzgjj(java.lang.String cjzgjj) {
		this.cjzgjj = cjzgjj;
	}

	/**
	 * @param fjxdb0
	 *            辅助检查：血红蛋白
	 */
	public void setFjxdb0(java.lang.String fjxdb0) {
		this.fjxdb0 = fjxdb0;
	}

	/**
	 * @param fjncg0
	 *            辅助检查：尿常规
	 */
	public void setFjncg0(java.lang.String fjncg0) {
		this.fjncg0 = fjncg0;
	}

	/**
	 * @param fjbcg0
	 *            辅助检查：白带常规
	 */
	public void setFjbcg0(java.lang.String fjbcg0) {
		this.fjbcg0 = fjbcg0;
	}

	/**
	 * @param fjxx00
	 *            辅助检查：血型
	 */
	public void setFjxx00(java.lang.String fjxx00) {
		this.fjxx00 = fjxx00;
	}

	/**
	 * @param fjrpr0
	 *            辅助检查：RPR
	 */
	public void setFjrpr0(java.lang.String fjrpr0) {
		this.fjrpr0 = fjrpr0;
	}

	/**
	 * @param fjhiv0
	 *            辅助检查：HIV
	 */
	public void setFjhiv0(java.lang.String fjhiv0) {
		this.fjhiv0 = fjhiv0;
	}

	/**
	 * @param fjhbsa
	 *            辅助检查：HBsAg
	 */
	public void setFjhbsa(java.lang.String fjhbsa) {
		this.fjhbsa = fjhbsa;
	}

	/**
	 * @param fjcqsc
	 *            辅助检查：产前筛查
	 */
	public void setFjcqsc(java.lang.String fjcqsc) {
		this.fjcqsc = fjcqsc;
	}

	/**
	 * @param fjsg00
	 *            辅助检查：肾功
	 */
	public void setFjsg00(java.lang.String fjsg00) {
		this.fjsg00 = fjsg00;
	}

	/**
	 * @param fjgg00
	 *            辅助检查：肝功
	 */
	public void setFjgg00(java.lang.String fjgg00) {
		this.fjgg00 = fjgg00;
	}

	/**
	 * @param fjtsc0
	 *            辅助检查：糖筛查
	 */
	public void setFjtsc0(java.lang.String fjtsc0) {
		this.fjtsc0 = fjtsc0;
	}

	/**
	 * @param fjqt00
	 *            辅助检查：其他
	 */
	public void setFjqt00(java.lang.String fjqt00) {
		this.fjqt00 = fjqt00;
	}

	/**
	 * @param fjbc00
	 *            辅助检查：B超
	 */
	public void setFjbc00(java.lang.String fjbc00) {
		this.fjbc00 = fjbc00;
	}

	/**
	 * @param fjxdt0
	 *            辅助检查：心电图
	 */
	public void setFjxdt0(java.lang.String fjxdt0) {
		this.fjxdt0 = fjxdt0;
	}

	/**
	 * @param bjzd00
	 *            保健指导
	 */
	public void setBjzd00(java.lang.String bjzd00) {
		this.bjzd00 = bjzd00;
	}

	/**
	 * @param czzdg0
	 *            初诊诊断G
	 */
	public void setCzzdg0(java.lang.String czzdg0) {
		this.czzdg0 = czzdg0;
	}

	/**
	 * @param czzdp0
	 *            初诊诊断P
	 */
	public void setCzzdp0(java.lang.String czzdp0) {
		this.czzdp0 = czzdp0;
	}

	/**
	 * @param czzdrs
	 *            初诊诊断：周宫内妊娠
	 */
	public void setCzzdrs(java.lang.String czzdrs) {
		this.czzdrs = czzdrs;
	}

	/**
	 * @param czzdnr
	 *            初诊诊断：诊断内容
	 */
	public void setCzzdnr(java.lang.String czzdnr) {
		this.czzdnr = czzdnr;
	}

	/**
	 * @param ryrq00
	 *            入院日期
	 */
	public void setRyrq00(java.sql.Date ryrq00) {
		this.ryrq00 = ryrq00;
	}

	/**
	 * @param zs0000
	 *            主诉
	 */
	public void setZs0000(java.lang.String zs0000) {
		this.zs0000 = zs0000;
	}

	/**
	 * @param ryzd00
	 *            入院诊断
	 */
	public void setRyzd00(java.lang.String ryzd00) {
		this.ryzd00 = ryzd00;
	}

	/**
	 * @param cl0000
	 *            处理
	 */
	public void setCl0000(java.lang.String cl0000) {
		this.cl0000 = cl0000;
	}

	/**
	 * @param zz0000
	 *            转诊
	 */
	public void setZz0000(java.lang.String zz0000) {
		this.zz0000 = zz0000;
	}

	/**
	 * @param jcdw00
	 *            检查单位
	 */
	public void setJcdw00(java.lang.String jcdw00) {
		this.jcdw00 = jcdw00;
	}

	/**
	 * @param jcrq00
	 *            检查日期
	 */
	public void setJcrq00(java.sql.Date jcrq00) {
		this.jcrq00 = jcrq00;
	}

	/**
	 * @param zyh000
	 *            住院号
	 */
	public void setZyh000(java.lang.String zyh000) {
		this.zyh000 = zyh000;
	}

	/**
	 * @param ckmzbh
	 *            产科门诊编号
	 */
	public void setCkmzbh(java.lang.String ckmzbh) {
		this.ckmzbh = ckmzbh;
	}

	/**
	 * @param rsdytc
	 *            妊娠史：胎次一
	 */
	public void setRsdytc(java.lang.String rsdytc) {
		this.rsdytc = rsdytc;
	}

	/**
	 * @param rsdetc
	 *            妊娠史：胎次二
	 */
	public void setRsdetc(java.lang.String rsdetc) {
		this.rsdetc = rsdetc;
	}

	/**
	 * @param rsdstc
	 *            妊娠史：胎次三
	 */
	public void setRsdstc(java.lang.String rsdstc) {
		this.rsdstc = rsdstc;
	}

	/**
	 * @param rssttc
	 *            妊娠史：胎次四
	 */
	public void setRssttc(java.lang.String rssttc) {
		this.rssttc = rssttc;
	}

	/**
	 * @param bsxwz0
	 *            病史询问者
	 */
	public void setBsxwz0(java.lang.String bsxwz0) {
		this.bsxwz0 = bsxwz0;
	}

	/**
	 * @param jcz000
	 *            检查者
	 */
	public void setJcz000(java.lang.String jcz000) {
		this.jcz000 = jcz000;
	}

	/**
	 * @param hkzt00
	 *            户口状态
	 */
	public void setHkzt00(java.lang.String hkzt00) {
		this.hkzt00 = hkzt00;
	}

	/**
	 * @param qm0000
	 *            签名
	 */
	public void setQm0000(java.lang.String qm0000) {
		this.qm0000 = qm0000;
	}

	/**
	 * @param jcdwna
	 *            检查单位名称
	 */
	public void setJcdwna(java.lang.String jcdwna) {
		this.jcdwna = jcdwna;
	}

	/**
	 * @param jcys00
	 *            检查医生
	 */
	public void setJcys00(java.lang.String jcys00) {
		this.jcys00 = jcys00;
	}

	/**
	 * @param jcysna
	 *            检查医生名称
	 */
	public void setJcysna(java.lang.String jcysna) {
		this.jcysna = jcysna;
	}

	public String toString() {
		return (new ToStringBuilder(this)).append("xtbhcq", getXtbhcq())
				.toString();
	}

	public int hashCode() {
		return (new HashCodeBuilder(17, 37)).append(getXtbhcq()).toHashCode();
	}

	public boolean equals(Object o) {
		if (!(o instanceof Fy_fncqjcDTO)) {
			return false;
		}
		if (o == this)
			return true;
		Fy_fncqjcDTO me = (Fy_fncqjcDTO) o;
		return new EqualsBuilder().append(getXtbhcq(), me.getXtbhcq())
				.isEquals();
	}
	public void setDhhm00(java.lang.String dhhm00) {
		this.dhhm00 = dhhm00;
	}
	public java.lang.String getDhhm00() {
		return dhhm00;
	}
	public void setFjtsc2(String fjtsc2) {
		this.fjtsc2 = fjtsc2;
	}
	public String getFjtsc2() {
		return fjtsc2;
	}
	public void setKfxt00(String kfxt00) {
		this.kfxt00 = kfxt00;
	}
	public String getKfxt00() {
		return kfxt00;
	}
	public java.math.BigDecimal getXcgbxb() {
		return xcgbxb;
	}
	public void setXcgbxb(java.math.BigDecimal xcgbxb) {
		this.xcgbxb = xcgbxb;
	}
	public java.math.BigDecimal getXcgxxb() {
		return xcgxxb;
	}
	public void setXcgxxb(java.math.BigDecimal xcgxxb) {
		this.xcgxxb = xcgxxb;
	}
	public String getXcgqt0() {
		return xcgqt0;
	}
	public void setXcgqt0(String xcgqt0) {
		this.xcgqt0 = xcgqt0;
	}
	public String getYgbmky() {
		return ygbmky;
	}
	public void setYgbmky(String ygbmky) {
		this.ygbmky = ygbmky;
	}
	public String getYgbmkt() {
		return ygbmkt;
	}
	public void setYgbmkt(String ygbmkt) {
		this.ygbmkt = ygbmkt;
	}
	public String getYgeky0() {
		return ygeky0;
	}
	public void setYgeky0(String ygeky0) {
		this.ygeky0 = ygeky0;
	}
	public String getYgekt0() {
		return ygekt0;
	}
	public void setYgekt0(String ygekt0) {
		this.ygekt0 = ygekt0;
	}
	public String getYghxkt() {
		return yghxkt;
	}
	public void setYghxkt(String yghxkt) {
		this.yghxkt = yghxkt;
	}
	public String getJkdah0() {
		return jkdah0;
	}
	public void setJkdah0(String jkdah0) {
		this.jkdah0 = jkdah0;
	}
	public String getHjlx00() {
		return hjlx00;
	}
	public void setHjlx00(String hjlx00) {
		this.hjlx00 = hjlx00;
	}
	public String getSfnyh0() {
		return sfnyh0;
	}
	public void setSfnyh0(String sfnyh0) {
		this.sfnyh0 = sfnyh0;
	}
	public String getYzbm00() {
		return yzbm00;
	}
	public void setYzbm00(String yzbm00) {
		this.yzbm00 = yzbm00;
	}
	public String getJwspx0() {
		return jwspx0;
	}
	public void setJwspx0(String jwspx0) {
		this.jwspx0 = jwspx0;
	}
	public String getFkssbz() {
		return fkssbz;
	}
	public void setFkssbz(String fkssbz) {
		this.fkssbz = fkssbz;
	}
	public String getFksss0() {
		return fksss0;
	}
	public void setFksss0(String fksss0) {
		this.fksss0 = fksss0;
	}
	public String getGrsxy0() {
		return grsxy0;
	}
	public void setGrsxy0(String grsxy0) {
		this.grsxy0 = grsxy0;
	}
	public String getGrsyj0() {
		return grsyj0;
	}
	public void setGrsyj0(String grsyj0) {
		this.grsyj0 = grsyj0;
	}
	public String getGrsyw0() {
		return grsyw0;
	}
	public void setGrsyw0(String grsyw0) {
		this.grsyw0 = grsyw0;
	}
	public String getGrsyhw() {
		return grsyhw;
	}
	public void setGrsyhw(String grsyhw) {
		this.grsyhw = grsyhw;
	}
	public String getGrsfsx() {
		return grsfsx;
	}
	public void setGrsfsx(String grsfsx) {
		this.grsfsx = grsfsx;
	}
	public String getGrsqt0() {
		return grsqt0;
	}
	public void setGrsqt0(String grsqt0) {
		this.grsqt0 = grsqt0;
	}
	public String getRshbzs() {
		return rshbzs;
	}
	public void setRshbzs(String rshbzs) {
		this.rshbzs = rshbzs;
	}
	public String getRsbfzs() {
		return rsbfzs;
	}
	public void setRsbfzs(String rsbfzs) {
		this.rsbfzs = rsbfzs;
	}
	public java.math.BigDecimal getYqtz00() {
		return yqtz00;
	}
	public void setYqtz00(java.math.BigDecimal yqtz00) {
		this.yqtz00 = yqtz00;
	}
	public String getFjjjbz() {
		return fjjjbz;
	}
	public void setFjjjbz(String fjjjbz) {
		this.fjjjbz = fjjjbz;
	}
	public String getWyjjbz() {
		return wyjjbz;
	}
	public void setWyjjbz(String wyjjbz) {
		this.wyjjbz = wyjjbz;
	}
	public String getYdjjbz() {
		return ydjjbz;
	}
	public void setYdjjbz(String ydjjbz) {
		this.ydjjbz = ydjjbz;
	}
	public String getGjjjbz() {
		return gjjjbz;
	}
	public void setGjjjbz(String gjjjbz) {
		this.gjjjbz = gjjjbz;
	}
	public String getGtjjbz() {
		return gtjjbz;
	}
	public void setGtjjbz(String gtjjbz) {
		this.gtjjbz = gtjjbz;
	}
	public String getFjflag() {
		return fjflag;
	}
	public void setFjflag(String fjflag) {
		this.fjflag = fjflag;
	}
	public String getMdxqlb() {
		return mdxqlb;
	}
	public void setMdxqlb(String mdxqlb) {
		this.mdxqlb = mdxqlb;
	}
	public String getZstj00() {
		return zstj00;
	}
	public void setZstj00(String zstj00) {
		this.zstj00 = zstj00;
	}
	public String getZszz00() {
		return zszz00;
	}
	public void setZszz00(String zszz00) {
		this.zszz00 = zszz00;
	}
	public String getRprdd0() {
		return rprdd0;
	}
	public void setRprdd0(String rprdd0) {
		this.rprdd0 = rprdd0;
	}
	public String getGgkfxt() {
		return ggkfxt;
	}
	public void setGgkfxt(String ggkfxt) {
		this.ggkfxt = ggkfxt;
	}
	public String getGgqt00() {
		return ggqt00;
	}
	public void setGgqt00(String ggqt00) {
		this.ggqt00 = ggqt00;
	}
	public java.lang.String getSgqt00() {
		return sgqt00;
	}
	public void setSgqt00(java.lang.String sgqt00) {
		this.sgqt00 = sgqt00;
	}
	public java.lang.String getJzsh00() {
		return jzsh00;
	}
	public void setJzsh00(java.lang.String jzsh00) {
		this.jzsh00 = jzsh00;
	}
	public java.lang.String getXdtwyc() {
		return xdtwyc;
	}
	public void setXdtwyc(java.lang.String xdtwyc) {
		this.xdtwyc = xdtwyc;
	}
	public java.lang.String getFjnxgn() {
		return fjnxgn;
	}
	public void setFjnxgn(java.lang.String fjnxgn) {
		this.fjnxgn = fjnxgn;
	}
	public String getJcxmjc() {
		return jcxmjc;
	}
	public void setJcxmjc(String jcxmjc) {
		this.jcxmjc = jcxmjc;
	}
	public java.lang.String getFjtppa() {
		return fjtppa;
	}
	public void setFjtppa(java.lang.String fjtppa) {
		this.fjtppa = fjtppa;
	}
	public java.sql.Date getYcqsta() {
		return ycqsta;
	}
	public void setYcqsta(java.sql.Date ycqsta) {
		this.ycqsta = ycqsta;
	}
	public java.sql.Date getYcqend() {
		return ycqend;
	}
	public void setYcqend(java.sql.Date ycqend) {
		this.ycqend = ycqend;
	}
	public String getBjzdqt() {
		return bjzdqt;
	}
	public void setBjzdqt(String bjzdqt) {
		this.bjzdqt = bjzdqt;
	}
	public String getSfjh00() {
		return sfjh00;
	}
	public void setSfjh00(String sfjh00) {
		this.sfjh00 = sfjh00;
	}
	public String getFzjcqt() {
		return fzjcqt;
	}
	public void setFzjcqt(String fzjcqt) {
		this.fzjcqt = fzjcqt;
	}
	public String getWcjysb() {
		return wcjysb;
	}
	public void setWcjysb(String wcjysb) {
		this.wcjysb = wcjysb;
	}
	public String getWcjyey() {
		return wcjyey;
	}
	public void setWcjyey(String wcjyey) {
		this.wcjyey = wcjyey;
	}
	public String getWcjyss() {
		return wcjyss;
	}
	public void setWcjyss(String wcjyss) {
		this.wcjyss = wcjyss;
	}
	public String getXcgzxl() {
		return xcgzxl;
	}
	public void setXcgzxl(String xcgzxl) {
		this.xcgzxl = xcgzxl;
	}
	public java.sql.Date getXcgkdrq() {
		return xcgkdrq;
	}
	public void setXcgkdrq(java.sql.Date xcgkdrq) {
		this.xcgkdrq = xcgkdrq;
	}
	public java.sql.Date getXxkdrq() {
		return xxkdrq;
	}
	public void setXxkdrq(java.sql.Date xxkdrq) {
		this.xxkdrq = xxkdrq;
	}
	public java.sql.Date getNcgkdrq() {
		return ncgkdrq;
	}
	public void setNcgkdrq(java.sql.Date ncgkdrq) {
		this.ncgkdrq = ncgkdrq;
	}
	public java.sql.Date getBdcgkdrq() {
		return bdcgkdrq;
	}
	public void setBdcgkdrq(java.sql.Date bdcgkdrq) {
		this.bdcgkdrq = bdcgkdrq;
	}
	public java.sql.Date getYxgykdrq() {
		return yxgykdrq;
	}
	public void setYxgykdrq(java.sql.Date yxgykdrq) {
		this.yxgykdrq = yxgykdrq;
	}
	public java.sql.Date getRprkdrq() {
		return rprkdrq;
	}
	public void setRprkdrq(java.sql.Date rprkdrq) {
		this.rprkdrq = rprkdrq;
	}
	public java.sql.Date getTppakdrq() {
		return tppakdrq;
	}
	public void setTppakdrq(java.sql.Date tppakdrq) {
		this.tppakdrq = tppakdrq;
	}
	public java.sql.Date getHivkdrq() {
		return hivkdrq;
	}
	public void setHivkdrq(java.sql.Date hivkdrq) {
		this.hivkdrq = hivkdrq;
	}
	public java.sql.Date getBgkdrq() {
		return bgkdrq;
	}
	public void setBgkdrq(java.sql.Date bgkdrq) {
		this.bgkdrq = bgkdrq;
	}
	public java.sql.Date getHbsagkdrq() {
		return hbsagkdrq;
	}
	public void setHbsagkdrq(java.sql.Date hbsagkdrq) {
		this.hbsagkdrq = hbsagkdrq;
	}
	public java.sql.Date getCqsckdrq() {
		return cqsckdrq;
	}
	public void setCqsckdrq(java.sql.Date cqsckdrq) {
		this.cqsckdrq = cqsckdrq;
	}
	public java.sql.Date getWcjykdrq() {
		return wcjykdrq;
	}
	public void setWcjykdrq(java.sql.Date wcjykdrq) {
		this.wcjykdrq = wcjykdrq;
	}
	public java.sql.Date getOgttkdrq() {
		return ogttkdrq;
	}
	public void setOgttkdrq(java.sql.Date ogttkdrq) {
		this.ogttkdrq = ogttkdrq;
	}
	public java.sql.Date getSgkdrq() {
		return sgkdrq;
	}
	public void setSgkdrq(java.sql.Date sgkdrq) {
		this.sgkdrq = sgkdrq;
	}
	public java.sql.Date getGgkdrq() {
		return ggkdrq;
	}
	public void setGgkdrq(java.sql.Date ggkdrq) {
		this.ggkdrq = ggkdrq;
	}
	public java.sql.Date getShqtkdrq() {
		return shqtkdrq;
	}
	public void setShqtkdrq(java.sql.Date shqtkdrq) {
		this.shqtkdrq = shqtkdrq;
	}
	public java.sql.Date getBckdrq() {
		return bckdrq;
	}
	public void setBckdrq(java.sql.Date bckdrq) {
		this.bckdrq = bckdrq;
	}
	public java.sql.Date getJzshkdrq() {
		return jzshkdrq;
	}
	public void setJzshkdrq(java.sql.Date jzshkdrq) {
		this.jzshkdrq = jzshkdrq;
	}
	public java.sql.Date getJzxkdrq() {
		return jzxkdrq;
	}
	public void setJzxkdrq(java.sql.Date jzxkdrq) {
		this.jzxkdrq = jzxkdrq;
	}
	public java.sql.Date getXdtkdrq() {
		return xdtkdrq;
	}
	public void setXdtkdrq(java.sql.Date xdtkdrq) {
		this.xdtkdrq = xdtkdrq;
	}
	public java.sql.Date getNxgnkdrq() {
		return nxgnkdrq;
	}
	public void setNxgnkdrq(java.sql.Date nxgnkdrq) {
		this.nxgnkdrq = nxgnkdrq;
	}
	public java.lang.String getFt3000() {
		return ft3000;
	}
	public void setFt3000(java.lang.String ft3000) {
		this.ft3000 = ft3000;
	}
	public String getNxmsj0() {
		return nxmsj0;
	}
	public void setNxmsj0(String nxmsj0) {
		this.nxmsj0 = nxmsj0;
	}
	public String getGgzdzs() {
		return ggzdzs;
	}
	public void setGgzdzs(String ggzdzs) {
		this.ggzdzs = ggzdzs;
	}
	public String getGgnzdb() {
		return ggnzdb;
	}
	public void setGgnzdb(String ggnzdb) {
		this.ggnzdb = ggnzdb;
	}
	public String getXcglbx() {
		return xcglbx;
	}
	public void setXcglbx(String xcglbx) {
		this.xcglbx = xcglbx;
	}
	public String getXcghxb() {
		return xcghxb;
	}
	public void setXcghxb(String xcghxb) {
		this.xcghxb = xcghxb;
	}
	public String getXcgxhd() {
		return xcgxhd;
	}
	public void setXcgxhd(String xcgxhd) {
		this.xcgxhd = xcgxhd;
	}
	public String getNcgndy() {
		return ncgndy;
	}
	public void setNcgndy(String ncgndy) {
		this.ncgndy = ncgndy;
	}
	public String getNcgdhs() {
		return ncgdhs;
	}
	public void setNcgdhs(String ncgdhs) {
		this.ncgdhs = ncgdhs;
	}
	public String getJzzdgc() {
		return jzzdgc;
	}
	public void setJzzdgc(String jzzdgc) {
		this.jzzdgc = jzzdgc;
	}
	public java.sql.Date getJcrq00end() {
		return jcrq00end;
	}
	public void setJcrq00end(java.sql.Date jcrq00end) {
		this.jcrq00end = jcrq00end;
	}
	public String getSfgw() {
		return sfgw;
	}
	public void setSfgw(String sfgw) {
		this.sfgw = sfgw;
	}
	public String getSfcjk0() {
		return sfcjk0;
	}
	public void setSfcjk0(String sfcjk0) {
		this.sfcjk0 = sfcjk0;
	}
	public String getDhzt00() {
		return dhzt00;
	}
	public void setDhzt00(String dhzt00) {
		this.dhzt00 = dhzt00;
	}
	public java.sql.Date getQxljckdrq() {
		return qxljckdrq;
	}
	public void setQxljckdrq(java.sql.Date qxljckdrq) {
		this.qxljckdrq = qxljckdrq;
	}
	public String getQxlsd0() {
		return qxlsd0;
	}
	public void setQxlsd0(String qxlsd0) {
		this.qxlsd0 = qxlsd0;
	}
	public String getQxlpi0() {
		return qxlpi0;
	}
	public void setQxlpi0(String qxlpi0) {
		this.qxlpi0 = qxlpi0;
	}
	public String getQxlri0() {
		return qxlri0;
	}
	public void setQxlri0(String qxlri0) {
		this.qxlri0 = qxlri0;
	}
	public java.lang.String getHkdshe1() {
		return hkdshe1;
	}
	public void setHkdshe1(java.lang.String hkdshe1) {
		this.hkdshe1 = hkdshe1;
	}
	public java.lang.String getHkdshi1() {
		return hkdshi1;
	}
	public void setHkdshi1(java.lang.String hkdshi1) {
		this.hkdshi1 = hkdshi1;
	}
	public java.lang.String getHkdxia1() {
		return hkdxia1;
	}
	public void setHkdxia1(java.lang.String hkdxia1) {
		this.hkdxia1 = hkdxia1;
	}
	public java.lang.String getHkdzhe1() {
		return hkdzhe1;
	}
	public void setHkdzhe1(java.lang.String hkdzhe1) {
		this.hkdzhe1 = hkdzhe1;
	}
	public java.lang.String getHkdcun1() {
		return hkdcun1;
	}
	public void setHkdcun1(java.lang.String hkdcun1) {
		this.hkdcun1 = hkdcun1;
	}
	public java.lang.String getHkdmph1() {
		return hkdmph1;
	}
	public void setHkdmph1(java.lang.String hkdmph1) {
		this.hkdmph1 = hkdmph1;
	}
	public java.lang.String getXzdshe1() {
		return xzdshe1;
	}
	public void setXzdshe1(java.lang.String xzdshe1) {
		this.xzdshe1 = xzdshe1;
	}
	public java.lang.String getXzdshi1() {
		return xzdshi1;
	}
	public void setXzdshi1(java.lang.String xzdshi1) {
		this.xzdshi1 = xzdshi1;
	}
	public java.lang.String getXzdxia1() {
		return xzdxia1;
	}
	public void setXzdxia1(java.lang.String xzdxia1) {
		this.xzdxia1 = xzdxia1;
	}
	public java.lang.String getXzdzhe1() {
		return xzdzhe1;
	}
	public void setXzdzhe1(java.lang.String xzdzhe1) {
		this.xzdzhe1 = xzdzhe1;
	}
	public java.lang.String getXzdcun1() {
		return xzdcun1;
	}
	public void setXzdcun1(java.lang.String xzdcun1) {
		this.xzdcun1 = xzdcun1;
	}
	public String getSfbyjk() {
		return sfbyjk;
	}
	public void setSfbyjk(String sfbyjk) {
		this.sfbyjk = sfbyjk;
	}
	public java.lang.String getYcq000str() {
		return ycq000str;
	}
	public void setYcq000str(java.lang.String ycq000str) {
		this.ycq000str = ycq000str;
	}


}
