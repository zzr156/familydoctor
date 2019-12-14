/*
 * CopyRight: StartTech 2010 cop. ltd.
 * All Right Reverse.
 * Class Name: <code>Fy_chfsjl</code>.
 */
package com.ylz.bizDo.assessment.vo.view;

import java.io.Serializable;
import java.util.Date;

import com.ylz.packcommon.common.util.ExtendDate;

/**
 * 产后访视记录表
 * 
 * @author hp
 *
 */
public class Fy_chfsjl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9008640276268704501L;

	private java.lang.String xtbh00; // 系统编号
	private java.lang.String scxtbh; // 手册系统编号
	private java.lang.String xm0000; // 项目（1为产后，2为产后42天）
	private Date sfrq00; // 随访日期
	private java.math.BigDecimal tiwen0; // 体温
	private java.lang.String ybjkqk; // 一般健康情况
	private java.lang.String ybxlzk; // 一般心理状况
	private java.math.BigDecimal xyssy0; // 血压：收缩压
	private java.math.BigDecimal xyszy0; // 血压：舒张压
	private java.lang.String rf0000; // 乳房
	private java.lang.String el0000; // 恶露
	private java.lang.String zg0000; // 子宫
	private java.lang.String sk0000; // 伤口
	private java.lang.String qt0000; // 其他
	private java.lang.String fl0000; // 分类
	private java.lang.String zd0000; // 指导
	private java.lang.String zz0000; // 转诊
	private java.lang.String zzyy00; // 转诊原因
	private java.lang.String zzjgks; // 转诊机构及科室
	private Date xcsfrq; // 下次随访日期
	private java.lang.String ynys00; // 院内医生签名
	private java.lang.String ywys00; // 院外医生签名
	private java.lang.String yyid00; // 医院编号
	private java.lang.String zhbjz0; // 最后编辑者
	private Date zhbjrq; // 最后编辑日期
	private Date cjrq00;// 创建日期
	private java.lang.String cjz000;// 创建者

	private String blflag; // 补录标志(0-否，1-是)

	private String zcrxjc; // 左侧乳腺检查结果代码
	private String ycrxjc;// 右侧乳腺检查结果代码
	private String bins00;// 病史

	private String isout;// 是否外出(1-是，2-否)
	private String outbz0;// 外出备注

	private Date xcgkdrq;
	private Date xxkdrq;
	private Date ncgkdrq;
	private Date bdcgkdrq;
	private Date yxgykdrq;
	private Date rprkdrq;
	private Date tppakdrq;
	private Date hivkdrq;
	private Date bgkdrq;
	private Date hbsagkdrq;
	private Date cqsckdrq;
	private Date wcjykdrq;
	private Date ogttkdrq;
	private Date sgkdrq;
	private Date ggkdrq;
	private Date shqtkdrq;
	private Date bckdrq;
	private Date jzshkdrq;
	private Date jzxkdrq;
	private Date xdtkdrq;
	private Date nxgnkdrq;
	private Date pttkdrq;
	private Date yszdkdrq;
	private Date kaxjkdrq;
	private Date kbxjkdrq;
	private Date zghdkdrq;

	private Date gjcdkdrq;
	private Date crpkdrq;
	private Date coomkdrq;
	private Date thxhkdrq;
	private Date hcgzkdrq;
	private Date gjxbkdrq;
	private Date pxkdrq;

	private String fjxdb0;
	private String xcgbxb;
	private String xcgzxl;
	private String xcglbx;
	private String xcghxb;
	private String xcgxhd;
	private String xcgxxb;
	private String xcgqt0;
	private String fjxx00;
	private String fjxxrh;
	private String ncgwyc;
	private String fjncg0;
	private String ncgnt0;
	private String ncgntt;
	private String ncgnqx;
	private String ncgbxb;
	private String ncgdhs;
	private String ncgndy;
	private String ncgqt0;
	private String fjbdcg;
	private String bdcgmj;
	private String bdcgdc;
	private String bdjcnq;
	private String bdjclq;
	private String bdcgbv;
	private String bdcgqt;
	private String ydqjd0;
	private String ygbmky;
	private String ygbmkt;
	private String ygeky0;
	private String ygekt0;
	private String yghxkt;
	private String fjbg00;
	private String fjrpr0;
	private String rprdd0;
	private String fjtppa;
	private String fjhiv0;
	private String fjhbsa;
	private String mdxqlb;
	private String scybst;
	private String sceyst;
	private String sjgqx0;
	private String fjtsc0;
	private String fjtsc2;
	private String kfxt00;
	private String fjsg00;
	private String sgnxns;
	private String sgqt00;
	private String fjgg00;
	private String ggngc0;
	private String ggnzdb;
	private String ggnbdb;
	private String ggnzdh;
	private String ggzdzs;
	private String ggkfxt;
	private String ggqt00;
	private String bcsdj0;
	private String bctw00;
	private String bcggj0;
	private String bcfw00;
	private String bcys00;
	private String bctx00;
	private String bctpcs;
	private String bctpfy;
	private String bctpxy;
	private String bcjxl0;
	private String bczgxd;
	private String bcqt00;
	private String bcjl00;
	private String fjshqt;
	private String shkfxt;
	private String shgbza;
	private String shgcza;
	private String shzdb0;
	private String shbdb0;
	private String shzdzs;
	private String shzdhs;
	private String shjg00;
	private String shnsd0;
	private String shns00;
	private String shldh0;
	private String shqt00;
	private String jzsh00;
	private String jzk000;
	private String jzna00;
	private String jzcl00;
	private String jzca00;
	private String jzmg00;
	private String jzzdb0;
	private String jzbdb0;
	private String jzzdhs;
	private String jzgbzam;
	private String jzgczam;
	private String jzzdgc;
	private String jzxt00;
	private String jzrstam;
	private String jzns00;
	private String jzjg00;
	private String jznsh0;
	private String ft3000;
	private String ft4000;
	private String tsh000;
	private String tpoab0;
	private String fjxdt0;
	private String qctext;
	private String fjnxgn;
	private String nxpt00;
	private String nxinr0;
	private String nxfg00;
	private String nxappt;
	private String nxmsj0;
	private String nxdejt;
	private String pttcd0;
	private String kaxj00;
	private String kbxj00;
	private String yszd00;
	private String gjxbjc;
	private String cszghd;
	private String csgjcd;
	private String wcjysb;
	private String wcjyey;
	private String wcjyss;
	private String cscrp0;
	private String pxjy01;
	private String pxjy02;
	private String pxjy03;
	private String coombsiggc;
	private String coombsigg;
	private String coombsc;
	private String thxhdb;
	private String tdhcgz;
	private String fjjcqt;
	private String jcxmlx;
	private String jcxmmc;
	private String sbkh00;
	private String jzjdhs;
	private String ggnjhd;
	private String fzjcqt;
	private String xdtwyc;
	private String fjqt00;
	private String czzdnr;

	private String zzjgid;
	private String clyj00;
	private String sfj000;
	private String wy0000;
	private String yd0000;
	private String gj0000;
	private String chts00;
	private String zs0000;
	private String qkyhqk;
	private String bjzdyj;

	/*
	 * private String zgid00; private String sfjid0; private String ydid00;
	 * private String rfid00; private String gjid00; private String bjzdid;
	 */

	private String ydqt00;
	private String ydwc00;
	private String ydjc00;

	private String zgqt00;
	private String zgwc00;
	private String zgjc00;

	private String gjqt00;
	private String gjwc00;
	private String gjjc00;

	private String rfqt00;
	private String sfjqt0;
	private String sfjwc0;
	private String sfjjc0;

	private Date fmrq00; // 分娩日期
	private String tz0000;
	private String zczw00;
	private String yczw00;
	private String lrz000;
	private String ogttqt;// OGTT其他
	private String cqscqt;// 产前筛查其他

	private String bczgwz;
	private String gtdx01;
	private String gtdx02;
	private String gtdx03;
	private String bcszhs;
	private String bcnmx0;
	private String bcnmhd;
	private String bcgqn0;
	private String gqdx01;
	private String gqdx02;
	private String gqdx03;
	private String zcrc01;
	private String zcrc02;
	private String ycrc01;
	private String ycrc02;
	private String scfjq0;
	private String csts00;
	private String cstsqt;
	private String fmfs00;

	private String tc0000;// 胎次
	private String cc0000;// 产次
	private String czzdnr2;

	private String qjx000;// 前静息阶段平均值
	private String kj2000;// 快肌（II类肌纤维）阶段最大值
	private String mj1000;// 慢肌（I类纤维）阶段平均值
	private String hjx000;// 后静息阶段平均值
	private Date pdgnjcrq;// 盆底功能检测日期
	
	public String getXm0000Str() {
		if ("1".equals(getXm0000())) {
			return "产后";
		} else if ("2".equals(getXm0000())) {
			return "产后42天";
		}
		return getXm0000();
	}
	
	public String getSfrq00Str() {
		return ExtendDate.getYMD(getSfrq00());
	}

	public java.lang.String getXtbh00() {
		return xtbh00;
	}

	public void setXtbh00(java.lang.String xtbh00) {
		this.xtbh00 = xtbh00;
	}

	public java.lang.String getScxtbh() {
		return scxtbh;
	}

	public void setScxtbh(java.lang.String scxtbh) {
		this.scxtbh = scxtbh;
	}

	public java.lang.String getXm0000() {
		return xm0000;
	}

	public void setXm0000(java.lang.String xm0000) {
		this.xm0000 = xm0000;
	}

	public Date getSfrq00() {
		return sfrq00;
	}

	public void setSfrq00(Date sfrq00) {
		this.sfrq00 = sfrq00;
	}

	public java.math.BigDecimal getTiwen0() {
		return tiwen0;
	}

	public void setTiwen0(java.math.BigDecimal tiwen0) {
		this.tiwen0 = tiwen0;
	}

	public java.lang.String getYbjkqk() {
		return ybjkqk;
	}

	public void setYbjkqk(java.lang.String ybjkqk) {
		this.ybjkqk = ybjkqk;
	}

	public java.lang.String getYbxlzk() {
		return ybxlzk;
	}

	public void setYbxlzk(java.lang.String ybxlzk) {
		this.ybxlzk = ybxlzk;
	}

	public java.math.BigDecimal getXyssy0() {
		return xyssy0;
	}

	public void setXyssy0(java.math.BigDecimal xyssy0) {
		this.xyssy0 = xyssy0;
	}

	public java.math.BigDecimal getXyszy0() {
		return xyszy0;
	}

	public void setXyszy0(java.math.BigDecimal xyszy0) {
		this.xyszy0 = xyszy0;
	}

	public java.lang.String getRf0000() {
		return rf0000;
	}

	public void setRf0000(java.lang.String rf0000) {
		this.rf0000 = rf0000;
	}

	public java.lang.String getEl0000() {
		return el0000;
	}

	public void setEl0000(java.lang.String el0000) {
		this.el0000 = el0000;
	}

	public java.lang.String getZg0000() {
		return zg0000;
	}

	public void setZg0000(java.lang.String zg0000) {
		this.zg0000 = zg0000;
	}

	public java.lang.String getSk0000() {
		return sk0000;
	}

	public void setSk0000(java.lang.String sk0000) {
		this.sk0000 = sk0000;
	}

	public java.lang.String getQt0000() {
		return qt0000;
	}

	public void setQt0000(java.lang.String qt0000) {
		this.qt0000 = qt0000;
	}

	public java.lang.String getFl0000() {
		return fl0000;
	}

	public void setFl0000(java.lang.String fl0000) {
		this.fl0000 = fl0000;
	}

	public java.lang.String getZd0000() {
		return zd0000;
	}

	public void setZd0000(java.lang.String zd0000) {
		this.zd0000 = zd0000;
	}

	public java.lang.String getZz0000() {
		return zz0000;
	}

	public void setZz0000(java.lang.String zz0000) {
		this.zz0000 = zz0000;
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

	public java.lang.String getYnys00() {
		return ynys00;
	}

	public void setYnys00(java.lang.String ynys00) {
		this.ynys00 = ynys00;
	}

	public java.lang.String getYwys00() {
		return ywys00;
	}

	public void setYwys00(java.lang.String ywys00) {
		this.ywys00 = ywys00;
	}

	public java.lang.String getYyid00() {
		return yyid00;
	}

	public void setYyid00(java.lang.String yyid00) {
		this.yyid00 = yyid00;
	}

	public java.lang.String getZhbjz0() {
		return zhbjz0;
	}

	public void setZhbjz0(java.lang.String zhbjz0) {
		this.zhbjz0 = zhbjz0;
	}

	public Date getZhbjrq() {
		return zhbjrq;
	}

	public void setZhbjrq(Date zhbjrq) {
		this.zhbjrq = zhbjrq;
	}

	public Date getCjrq00() {
		return cjrq00;
	}

	public void setCjrq00(Date cjrq00) {
		this.cjrq00 = cjrq00;
	}

	public java.lang.String getCjz000() {
		return cjz000;
	}

	public void setCjz000(java.lang.String cjz000) {
		this.cjz000 = cjz000;
	}

	public String getBlflag() {
		return blflag;
	}

	public void setBlflag(String blflag) {
		this.blflag = blflag;
	}

	public String getZcrxjc() {
		return zcrxjc;
	}

	public void setZcrxjc(String zcrxjc) {
		this.zcrxjc = zcrxjc;
	}

	public String getYcrxjc() {
		return ycrxjc;
	}

	public void setYcrxjc(String ycrxjc) {
		this.ycrxjc = ycrxjc;
	}

	public String getBins00() {
		return bins00;
	}

	public void setBins00(String bins00) {
		this.bins00 = bins00;
	}

	public String getIsout() {
		return isout;
	}

	public void setIsout(String isout) {
		this.isout = isout;
	}

	public String getOutbz0() {
		return outbz0;
	}

	public void setOutbz0(String outbz0) {
		this.outbz0 = outbz0;
	}

	public Date getXcgkdrq() {
		return xcgkdrq;
	}

	public void setXcgkdrq(Date xcgkdrq) {
		this.xcgkdrq = xcgkdrq;
	}

	public Date getXxkdrq() {
		return xxkdrq;
	}

	public void setXxkdrq(Date xxkdrq) {
		this.xxkdrq = xxkdrq;
	}

	public Date getNcgkdrq() {
		return ncgkdrq;
	}

	public void setNcgkdrq(Date ncgkdrq) {
		this.ncgkdrq = ncgkdrq;
	}

	public Date getBdcgkdrq() {
		return bdcgkdrq;
	}

	public void setBdcgkdrq(Date bdcgkdrq) {
		this.bdcgkdrq = bdcgkdrq;
	}

	public Date getYxgykdrq() {
		return yxgykdrq;
	}

	public void setYxgykdrq(Date yxgykdrq) {
		this.yxgykdrq = yxgykdrq;
	}

	public Date getRprkdrq() {
		return rprkdrq;
	}

	public void setRprkdrq(Date rprkdrq) {
		this.rprkdrq = rprkdrq;
	}

	public Date getTppakdrq() {
		return tppakdrq;
	}

	public void setTppakdrq(Date tppakdrq) {
		this.tppakdrq = tppakdrq;
	}

	public Date getHivkdrq() {
		return hivkdrq;
	}

	public void setHivkdrq(Date hivkdrq) {
		this.hivkdrq = hivkdrq;
	}

	public Date getBgkdrq() {
		return bgkdrq;
	}

	public void setBgkdrq(Date bgkdrq) {
		this.bgkdrq = bgkdrq;
	}

	public Date getHbsagkdrq() {
		return hbsagkdrq;
	}

	public void setHbsagkdrq(Date hbsagkdrq) {
		this.hbsagkdrq = hbsagkdrq;
	}

	public Date getCqsckdrq() {
		return cqsckdrq;
	}

	public void setCqsckdrq(Date cqsckdrq) {
		this.cqsckdrq = cqsckdrq;
	}

	public Date getWcjykdrq() {
		return wcjykdrq;
	}

	public void setWcjykdrq(Date wcjykdrq) {
		this.wcjykdrq = wcjykdrq;
	}

	public Date getOgttkdrq() {
		return ogttkdrq;
	}

	public void setOgttkdrq(Date ogttkdrq) {
		this.ogttkdrq = ogttkdrq;
	}

	public Date getSgkdrq() {
		return sgkdrq;
	}

	public void setSgkdrq(Date sgkdrq) {
		this.sgkdrq = sgkdrq;
	}

	public Date getGgkdrq() {
		return ggkdrq;
	}

	public void setGgkdrq(Date ggkdrq) {
		this.ggkdrq = ggkdrq;
	}

	public Date getShqtkdrq() {
		return shqtkdrq;
	}

	public void setShqtkdrq(Date shqtkdrq) {
		this.shqtkdrq = shqtkdrq;
	}

	public Date getBckdrq() {
		return bckdrq;
	}

	public void setBckdrq(Date bckdrq) {
		this.bckdrq = bckdrq;
	}

	public Date getJzshkdrq() {
		return jzshkdrq;
	}

	public void setJzshkdrq(Date jzshkdrq) {
		this.jzshkdrq = jzshkdrq;
	}

	public Date getJzxkdrq() {
		return jzxkdrq;
	}

	public void setJzxkdrq(Date jzxkdrq) {
		this.jzxkdrq = jzxkdrq;
	}

	public Date getXdtkdrq() {
		return xdtkdrq;
	}

	public void setXdtkdrq(Date xdtkdrq) {
		this.xdtkdrq = xdtkdrq;
	}

	public Date getNxgnkdrq() {
		return nxgnkdrq;
	}

	public void setNxgnkdrq(Date nxgnkdrq) {
		this.nxgnkdrq = nxgnkdrq;
	}

	public Date getPttkdrq() {
		return pttkdrq;
	}

	public void setPttkdrq(Date pttkdrq) {
		this.pttkdrq = pttkdrq;
	}

	public Date getYszdkdrq() {
		return yszdkdrq;
	}

	public void setYszdkdrq(Date yszdkdrq) {
		this.yszdkdrq = yszdkdrq;
	}

	public Date getKaxjkdrq() {
		return kaxjkdrq;
	}

	public void setKaxjkdrq(Date kaxjkdrq) {
		this.kaxjkdrq = kaxjkdrq;
	}

	public Date getKbxjkdrq() {
		return kbxjkdrq;
	}

	public void setKbxjkdrq(Date kbxjkdrq) {
		this.kbxjkdrq = kbxjkdrq;
	}

	public Date getZghdkdrq() {
		return zghdkdrq;
	}

	public void setZghdkdrq(Date zghdkdrq) {
		this.zghdkdrq = zghdkdrq;
	}

	public Date getGjcdkdrq() {
		return gjcdkdrq;
	}

	public void setGjcdkdrq(Date gjcdkdrq) {
		this.gjcdkdrq = gjcdkdrq;
	}

	public Date getCrpkdrq() {
		return crpkdrq;
	}

	public void setCrpkdrq(Date crpkdrq) {
		this.crpkdrq = crpkdrq;
	}

	public Date getCoomkdrq() {
		return coomkdrq;
	}

	public void setCoomkdrq(Date coomkdrq) {
		this.coomkdrq = coomkdrq;
	}

	public Date getThxhkdrq() {
		return thxhkdrq;
	}

	public void setThxhkdrq(Date thxhkdrq) {
		this.thxhkdrq = thxhkdrq;
	}

	public Date getHcgzkdrq() {
		return hcgzkdrq;
	}

	public void setHcgzkdrq(Date hcgzkdrq) {
		this.hcgzkdrq = hcgzkdrq;
	}

	public Date getGjxbkdrq() {
		return gjxbkdrq;
	}

	public void setGjxbkdrq(Date gjxbkdrq) {
		this.gjxbkdrq = gjxbkdrq;
	}

	public Date getPxkdrq() {
		return pxkdrq;
	}

	public void setPxkdrq(Date pxkdrq) {
		this.pxkdrq = pxkdrq;
	}

	public String getFjxdb0() {
		return fjxdb0;
	}

	public void setFjxdb0(String fjxdb0) {
		this.fjxdb0 = fjxdb0;
	}

	public String getXcgbxb() {
		return xcgbxb;
	}

	public void setXcgbxb(String xcgbxb) {
		this.xcgbxb = xcgbxb;
	}

	public String getXcgzxl() {
		return xcgzxl;
	}

	public void setXcgzxl(String xcgzxl) {
		this.xcgzxl = xcgzxl;
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

	public String getXcgxxb() {
		return xcgxxb;
	}

	public void setXcgxxb(String xcgxxb) {
		this.xcgxxb = xcgxxb;
	}

	public String getXcgqt0() {
		return xcgqt0;
	}

	public void setXcgqt0(String xcgqt0) {
		this.xcgqt0 = xcgqt0;
	}

	public String getFjxx00() {
		return fjxx00;
	}

	public void setFjxx00(String fjxx00) {
		this.fjxx00 = fjxx00;
	}

	public String getFjxxrh() {
		return fjxxrh;
	}

	public void setFjxxrh(String fjxxrh) {
		this.fjxxrh = fjxxrh;
	}

	public String getNcgwyc() {
		return ncgwyc;
	}

	public void setNcgwyc(String ncgwyc) {
		this.ncgwyc = ncgwyc;
	}

	public String getFjncg0() {
		return fjncg0;
	}

	public void setFjncg0(String fjncg0) {
		this.fjncg0 = fjncg0;
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

	public String getNcgbxb() {
		return ncgbxb;
	}

	public void setNcgbxb(String ncgbxb) {
		this.ncgbxb = ncgbxb;
	}

	public String getNcgdhs() {
		return ncgdhs;
	}

	public void setNcgdhs(String ncgdhs) {
		this.ncgdhs = ncgdhs;
	}

	public String getNcgndy() {
		return ncgndy;
	}

	public void setNcgndy(String ncgndy) {
		this.ncgndy = ncgndy;
	}

	public String getNcgqt0() {
		return ncgqt0;
	}

	public void setNcgqt0(String ncgqt0) {
		this.ncgqt0 = ncgqt0;
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

	public String getYdqjd0() {
		return ydqjd0;
	}

	public void setYdqjd0(String ydqjd0) {
		this.ydqjd0 = ydqjd0;
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

	public String getFjbg00() {
		return fjbg00;
	}

	public void setFjbg00(String fjbg00) {
		this.fjbg00 = fjbg00;
	}

	public String getFjrpr0() {
		return fjrpr0;
	}

	public void setFjrpr0(String fjrpr0) {
		this.fjrpr0 = fjrpr0;
	}

	public String getRprdd0() {
		return rprdd0;
	}

	public void setRprdd0(String rprdd0) {
		this.rprdd0 = rprdd0;
	}

	public String getFjtppa() {
		return fjtppa;
	}

	public void setFjtppa(String fjtppa) {
		this.fjtppa = fjtppa;
	}

	public String getFjhiv0() {
		return fjhiv0;
	}

	public void setFjhiv0(String fjhiv0) {
		this.fjhiv0 = fjhiv0;
	}

	public String getFjhbsa() {
		return fjhbsa;
	}

	public void setFjhbsa(String fjhbsa) {
		this.fjhbsa = fjhbsa;
	}

	public String getMdxqlb() {
		return mdxqlb;
	}

	public void setMdxqlb(String mdxqlb) {
		this.mdxqlb = mdxqlb;
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

	public String getFjtsc0() {
		return fjtsc0;
	}

	public void setFjtsc0(String fjtsc0) {
		this.fjtsc0 = fjtsc0;
	}

	public String getFjtsc2() {
		return fjtsc2;
	}

	public void setFjtsc2(String fjtsc2) {
		this.fjtsc2 = fjtsc2;
	}

	public String getKfxt00() {
		return kfxt00;
	}

	public void setKfxt00(String kfxt00) {
		this.kfxt00 = kfxt00;
	}

	public String getFjsg00() {
		return fjsg00;
	}

	public void setFjsg00(String fjsg00) {
		this.fjsg00 = fjsg00;
	}

	public String getSgnxns() {
		return sgnxns;
	}

	public void setSgnxns(String sgnxns) {
		this.sgnxns = sgnxns;
	}

	public String getSgqt00() {
		return sgqt00;
	}

	public void setSgqt00(String sgqt00) {
		this.sgqt00 = sgqt00;
	}

	public String getFjgg00() {
		return fjgg00;
	}

	public void setFjgg00(String fjgg00) {
		this.fjgg00 = fjgg00;
	}

	public String getGgngc0() {
		return ggngc0;
	}

	public void setGgngc0(String ggngc0) {
		this.ggngc0 = ggngc0;
	}

	public String getGgnzdb() {
		return ggnzdb;
	}

	public void setGgnzdb(String ggnzdb) {
		this.ggnzdb = ggnzdb;
	}

	public String getGgnbdb() {
		return ggnbdb;
	}

	public void setGgnbdb(String ggnbdb) {
		this.ggnbdb = ggnbdb;
	}

	public String getGgnzdh() {
		return ggnzdh;
	}

	public void setGgnzdh(String ggnzdh) {
		this.ggnzdh = ggnzdh;
	}

	public String getGgzdzs() {
		return ggzdzs;
	}

	public void setGgzdzs(String ggzdzs) {
		this.ggzdzs = ggzdzs;
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

	public String getJzsh00() {
		return jzsh00;
	}

	public void setJzsh00(String jzsh00) {
		this.jzsh00 = jzsh00;
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

	public String getJzzdgc() {
		return jzzdgc;
	}

	public void setJzzdgc(String jzzdgc) {
		this.jzzdgc = jzzdgc;
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

	public String getJznsh0() {
		return jznsh0;
	}

	public void setJznsh0(String jznsh0) {
		this.jznsh0 = jznsh0;
	}

	public String getFt3000() {
		return ft3000;
	}

	public void setFt3000(String ft3000) {
		this.ft3000 = ft3000;
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

	public String getFjxdt0() {
		return fjxdt0;
	}

	public void setFjxdt0(String fjxdt0) {
		this.fjxdt0 = fjxdt0;
	}

	public String getQctext() {
		return qctext;
	}

	public void setQctext(String qctext) {
		this.qctext = qctext;
	}

	public String getFjnxgn() {
		return fjnxgn;
	}

	public void setFjnxgn(String fjnxgn) {
		this.fjnxgn = fjnxgn;
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

	public String getNxmsj0() {
		return nxmsj0;
	}

	public void setNxmsj0(String nxmsj0) {
		this.nxmsj0 = nxmsj0;
	}

	public String getNxdejt() {
		return nxdejt;
	}

	public void setNxdejt(String nxdejt) {
		this.nxdejt = nxdejt;
	}

	public String getPttcd0() {
		return pttcd0;
	}

	public void setPttcd0(String pttcd0) {
		this.pttcd0 = pttcd0;
	}

	public String getKaxj00() {
		return kaxj00;
	}

	public void setKaxj00(String kaxj00) {
		this.kaxj00 = kaxj00;
	}

	public String getKbxj00() {
		return kbxj00;
	}

	public void setKbxj00(String kbxj00) {
		this.kbxj00 = kbxj00;
	}

	public String getYszd00() {
		return yszd00;
	}

	public void setYszd00(String yszd00) {
		this.yszd00 = yszd00;
	}

	public String getGjxbjc() {
		return gjxbjc;
	}

	public void setGjxbjc(String gjxbjc) {
		this.gjxbjc = gjxbjc;
	}

	public String getCszghd() {
		return cszghd;
	}

	public void setCszghd(String cszghd) {
		this.cszghd = cszghd;
	}

	public String getCsgjcd() {
		return csgjcd;
	}

	public void setCsgjcd(String csgjcd) {
		this.csgjcd = csgjcd;
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

	public String getCscrp0() {
		return cscrp0;
	}

	public void setCscrp0(String cscrp0) {
		this.cscrp0 = cscrp0;
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

	public String getThxhdb() {
		return thxhdb;
	}

	public void setThxhdb(String thxhdb) {
		this.thxhdb = thxhdb;
	}

	public String getTdhcgz() {
		return tdhcgz;
	}

	public void setTdhcgz(String tdhcgz) {
		this.tdhcgz = tdhcgz;
	}

	public String getFjjcqt() {
		return fjjcqt;
	}

	public void setFjjcqt(String fjjcqt) {
		this.fjjcqt = fjjcqt;
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

	public String getSbkh00() {
		return sbkh00;
	}

	public void setSbkh00(String sbkh00) {
		this.sbkh00 = sbkh00;
	}

	public String getJzjdhs() {
		return jzjdhs;
	}

	public void setJzjdhs(String jzjdhs) {
		this.jzjdhs = jzjdhs;
	}

	public String getGgnjhd() {
		return ggnjhd;
	}

	public void setGgnjhd(String ggnjhd) {
		this.ggnjhd = ggnjhd;
	}

	public String getFzjcqt() {
		return fzjcqt;
	}

	public void setFzjcqt(String fzjcqt) {
		this.fzjcqt = fzjcqt;
	}

	public String getXdtwyc() {
		return xdtwyc;
	}

	public void setXdtwyc(String xdtwyc) {
		this.xdtwyc = xdtwyc;
	}

	public String getFjqt00() {
		return fjqt00;
	}

	public void setFjqt00(String fjqt00) {
		this.fjqt00 = fjqt00;
	}

	public String getCzzdnr() {
		return czzdnr;
	}

	public void setCzzdnr(String czzdnr) {
		this.czzdnr = czzdnr;
	}

	public String getZzjgid() {
		return zzjgid;
	}

	public void setZzjgid(String zzjgid) {
		this.zzjgid = zzjgid;
	}

	public String getClyj00() {
		return clyj00;
	}

	public void setClyj00(String clyj00) {
		this.clyj00 = clyj00;
	}

	public String getSfj000() {
		return sfj000;
	}

	public void setSfj000(String sfj000) {
		this.sfj000 = sfj000;
	}

	public String getWy0000() {
		return wy0000;
	}

	public void setWy0000(String wy0000) {
		this.wy0000 = wy0000;
	}

	public String getYd0000() {
		return yd0000;
	}

	public void setYd0000(String yd0000) {
		this.yd0000 = yd0000;
	}

	public String getGj0000() {
		return gj0000;
	}

	public void setGj0000(String gj0000) {
		this.gj0000 = gj0000;
	}

	public String getChts00() {
		return chts00;
	}

	public void setChts00(String chts00) {
		this.chts00 = chts00;
	}

	public String getZs0000() {
		return zs0000;
	}

	public void setZs0000(String zs0000) {
		this.zs0000 = zs0000;
	}

	public String getQkyhqk() {
		return qkyhqk;
	}

	public void setQkyhqk(String qkyhqk) {
		this.qkyhqk = qkyhqk;
	}

	public String getBjzdyj() {
		return bjzdyj;
	}

	public void setBjzdyj(String bjzdyj) {
		this.bjzdyj = bjzdyj;
	}

	public String getYdqt00() {
		return ydqt00;
	}

	public void setYdqt00(String ydqt00) {
		this.ydqt00 = ydqt00;
	}

	public String getYdwc00() {
		return ydwc00;
	}

	public void setYdwc00(String ydwc00) {
		this.ydwc00 = ydwc00;
	}

	public String getYdjc00() {
		return ydjc00;
	}

	public void setYdjc00(String ydjc00) {
		this.ydjc00 = ydjc00;
	}

	public String getZgqt00() {
		return zgqt00;
	}

	public void setZgqt00(String zgqt00) {
		this.zgqt00 = zgqt00;
	}

	public String getZgwc00() {
		return zgwc00;
	}

	public void setZgwc00(String zgwc00) {
		this.zgwc00 = zgwc00;
	}

	public String getZgjc00() {
		return zgjc00;
	}

	public void setZgjc00(String zgjc00) {
		this.zgjc00 = zgjc00;
	}

	public String getGjqt00() {
		return gjqt00;
	}

	public void setGjqt00(String gjqt00) {
		this.gjqt00 = gjqt00;
	}

	public String getGjwc00() {
		return gjwc00;
	}

	public void setGjwc00(String gjwc00) {
		this.gjwc00 = gjwc00;
	}

	public String getGjjc00() {
		return gjjc00;
	}

	public void setGjjc00(String gjjc00) {
		this.gjjc00 = gjjc00;
	}

	public String getRfqt00() {
		return rfqt00;
	}

	public void setRfqt00(String rfqt00) {
		this.rfqt00 = rfqt00;
	}

	public String getSfjqt0() {
		return sfjqt0;
	}

	public void setSfjqt0(String sfjqt0) {
		this.sfjqt0 = sfjqt0;
	}

	public String getSfjwc0() {
		return sfjwc0;
	}

	public void setSfjwc0(String sfjwc0) {
		this.sfjwc0 = sfjwc0;
	}

	public String getSfjjc0() {
		return sfjjc0;
	}

	public void setSfjjc0(String sfjjc0) {
		this.sfjjc0 = sfjjc0;
	}

	public Date getFmrq00() {
		return fmrq00;
	}

	public void setFmrq00(Date fmrq00) {
		this.fmrq00 = fmrq00;
	}

	public String getTz0000() {
		return tz0000;
	}

	public void setTz0000(String tz0000) {
		this.tz0000 = tz0000;
	}

	public String getZczw00() {
		return zczw00;
	}

	public void setZczw00(String zczw00) {
		this.zczw00 = zczw00;
	}

	public String getYczw00() {
		return yczw00;
	}

	public void setYczw00(String yczw00) {
		this.yczw00 = yczw00;
	}

	public String getLrz000() {
		return lrz000;
	}

	public void setLrz000(String lrz000) {
		this.lrz000 = lrz000;
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

	public String getBczgwz() {
		return bczgwz;
	}

	public void setBczgwz(String bczgwz) {
		this.bczgwz = bczgwz;
	}

	public String getGtdx01() {
		return gtdx01;
	}

	public void setGtdx01(String gtdx01) {
		this.gtdx01 = gtdx01;
	}

	public String getGtdx02() {
		return gtdx02;
	}

	public void setGtdx02(String gtdx02) {
		this.gtdx02 = gtdx02;
	}

	public String getGtdx03() {
		return gtdx03;
	}

	public void setGtdx03(String gtdx03) {
		this.gtdx03 = gtdx03;
	}

	public String getBcszhs() {
		return bcszhs;
	}

	public void setBcszhs(String bcszhs) {
		this.bcszhs = bcszhs;
	}

	public String getBcnmx0() {
		return bcnmx0;
	}

	public void setBcnmx0(String bcnmx0) {
		this.bcnmx0 = bcnmx0;
	}

	public String getBcnmhd() {
		return bcnmhd;
	}

	public void setBcnmhd(String bcnmhd) {
		this.bcnmhd = bcnmhd;
	}

	public String getBcgqn0() {
		return bcgqn0;
	}

	public void setBcgqn0(String bcgqn0) {
		this.bcgqn0 = bcgqn0;
	}

	public String getGqdx01() {
		return gqdx01;
	}

	public void setGqdx01(String gqdx01) {
		this.gqdx01 = gqdx01;
	}

	public String getGqdx02() {
		return gqdx02;
	}

	public void setGqdx02(String gqdx02) {
		this.gqdx02 = gqdx02;
	}

	public String getGqdx03() {
		return gqdx03;
	}

	public void setGqdx03(String gqdx03) {
		this.gqdx03 = gqdx03;
	}

	public String getZcrc01() {
		return zcrc01;
	}

	public void setZcrc01(String zcrc01) {
		this.zcrc01 = zcrc01;
	}

	public String getZcrc02() {
		return zcrc02;
	}

	public void setZcrc02(String zcrc02) {
		this.zcrc02 = zcrc02;
	}

	public String getYcrc01() {
		return ycrc01;
	}

	public void setYcrc01(String ycrc01) {
		this.ycrc01 = ycrc01;
	}

	public String getYcrc02() {
		return ycrc02;
	}

	public void setYcrc02(String ycrc02) {
		this.ycrc02 = ycrc02;
	}

	public String getScfjq0() {
		return scfjq0;
	}

	public void setScfjq0(String scfjq0) {
		this.scfjq0 = scfjq0;
	}

	public String getCsts00() {
		return csts00;
	}

	public void setCsts00(String csts00) {
		this.csts00 = csts00;
	}

	public String getCstsqt() {
		return cstsqt;
	}

	public void setCstsqt(String cstsqt) {
		this.cstsqt = cstsqt;
	}

	public String getFmfs00() {
		return fmfs00;
	}

	public void setFmfs00(String fmfs00) {
		this.fmfs00 = fmfs00;
	}

	public String getTc0000() {
		return tc0000;
	}

	public void setTc0000(String tc0000) {
		this.tc0000 = tc0000;
	}

	public String getCc0000() {
		return cc0000;
	}

	public void setCc0000(String cc0000) {
		this.cc0000 = cc0000;
	}

	public String getCzzdnr2() {
		return czzdnr2;
	}

	public void setCzzdnr2(String czzdnr2) {
		this.czzdnr2 = czzdnr2;
	}

	public String getQjx000() {
		return qjx000;
	}

	public void setQjx000(String qjx000) {
		this.qjx000 = qjx000;
	}

	public String getKj2000() {
		return kj2000;
	}

	public void setKj2000(String kj2000) {
		this.kj2000 = kj2000;
	}

	public String getMj1000() {
		return mj1000;
	}

	public void setMj1000(String mj1000) {
		this.mj1000 = mj1000;
	}

	public String getHjx000() {
		return hjx000;
	}

	public void setHjx000(String hjx000) {
		this.hjx000 = hjx000;
	}

	public Date getPdgnjcrq() {
		return pdgnjcrq;
	}

	public void setPdgnjcrq(Date pdgnjcrq) {
		this.pdgnjcrq = pdgnjcrq;
	}

}
