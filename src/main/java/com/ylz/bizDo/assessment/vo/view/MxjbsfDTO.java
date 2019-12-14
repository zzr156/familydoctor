package com.ylz.bizDo.assessment.vo.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 各种慢性疾病患者随访信息
 * 
 * @author hp
 *
 */

public class MxjbsfDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5960034684667089183L;

	private java.lang.String sf_id0000; // 随访编号
	private java.lang.String ref_id; // 慢性疾病患者编号
	private java.lang.String mxjbbz; // 慢性疾病编号
	private java.lang.String sfrq00; // 随访日期
	private java.lang.String sffs00; // 随访方式（1-门诊，2--家庭，3--电话）
	private java.lang.String ssy; // 收缩压（血压）
	private java.lang.String szy; // 舒张压（血压）
	private java.lang.String tzone; // 体重（目前）
	private java.lang.String tztwo; // 体重（计划）
	private java.lang.String tzzs00; // 体质指数
	private java.lang.String xlone; // 心率（目前）
	private java.lang.String xltwo; // 心率（计划）
	private java.lang.String zbdmpd; // 足背动脉搏动（1--未触及，2--触及）
	private java.lang.String qttz00; // 其他体征
	private java.lang.String rxylone; // 日吸烟量（目前）
	private java.lang.String rxyltwo; // 日吸烟量（计划）
	private java.lang.String ryjlone; // 日饮酒量（目前）
	private java.lang.String ryjltwo; // 日饮酒量（计划）
	private java.lang.String ydzcone; // 运动周次（目前）
	private java.lang.String ydzctwo; // 运动周次（计划）
	private java.lang.String ydrcone; // 运动分次（目前）
	private java.lang.String ydrctwo; // 运动分次（计划）
	private java.lang.String syqkone; // 摄盐情况（目前）
	private java.lang.String syqktwo; // 摄盐情况（计划）
	private java.lang.String zsqkone; // 主食情况（目前）
	private java.lang.String zsqktwo; // 主食情况（计划）
	private java.lang.String xltzqk; // 心理调整（1--良好，2--一般，3--差）
	private java.lang.String zyxwqk; // 遵医情况（1--良好，2---一般，3--差）
	private java.lang.String kfxtz0; // 空腹血糖值
	private java.lang.String thxhdb; // 糖化血红蛋白
	private java.lang.String jcrq00; // 检查日期
	private java.lang.String qtfzjc; // 其他辅助检查
	private java.lang.String fyycx0; // 服药依从性（1--规律，2--间断，3--不服药）
	private java.lang.String ywblfy; // 药物不良反应（0--无，1--有）
	private java.lang.String blfyms; // 不良反应描述
	private java.lang.String dxtfy; // 低血糖反应（1--无，2--偶尔，3--频繁）
	private java.lang.String ccsffl; // 此次随访分类（1--控制满意，2--控制不满意，3--不良反应，4--并发症）
	private java.lang.String zzqk00; // 转诊情况（0--无，1--有）
	private java.lang.String zzyy00; // 转诊原因
	private java.lang.String zzjgks; // 转诊机构科室
	private java.lang.String xcsfrq; // 下次随访日期
	private BigDecimal sfysqm; // 随访医生签名
	private java.lang.String sfysxm; // 随访医生姓名
	private java.lang.String ywysxm; // 院外医生姓名
	private java.lang.String yds000; // 胰岛素种类
	private java.lang.String zzbh_id; // 症状编号
	private java.lang.String ref_sf_id; // 随访记录编号
	private java.lang.String ttty; // 头痛头晕
	private java.lang.String exot; // 恶心呕吐
	private java.lang.String yhem; // 眼花耳鸣
	private java.lang.String hxkn; // 呼吸困难
	private java.lang.String xjxm; // 心悸胸闷
	private java.lang.String bccxbz; // 鼻子出血不止
	private java.lang.String szfm; // (高血压-四肢麻木，糖尿病-手脚麻木)
	private java.lang.String xzsz; // (高血压-下肢水肿 )
	private java.lang.String dy; // 多饮
	private java.lang.String ds; // 多食
	private java.lang.String dn; // 多尿
	private java.lang.String slmh; // 视力模糊
	private java.lang.String gl; // 感染
	private java.lang.String xzfz; // 糖尿病-四肢浮肿
	private java.lang.String tqzz; // 其他症状
	private java.lang.String df_id; // 居民档案号
	private java.lang.String name; // 居民姓名
	private java.lang.String telphone; // 联系电话
	private java.lang.String adress_city; // 常住地址：市（地区）
	private java.lang.String adress_rural; // 常住地址：乡（镇、街道办事处）
	private java.lang.String adress_village; // 常住地址：村（街、路、弄）
	private java.lang.String adrss_hnumber; // 常住地址：门牌号码
	private java.lang.String adress_pro; // 常住地址：省（自治区、直辖市）
	private java.lang.String adress_county; // 常住地址：县（区）
	private java.lang.String sex; // 性别
	private java.lang.String birthday; // 出生日期
	private java.lang.String ref_ci_id; // 社区号
	private java.lang.String f_id; // 家庭档案号
	private java.lang.String sg0000; // 身高
	private java.lang.String addrzh;// 组合地址
	private java.lang.String address;// 完整地址
	private java.lang.String wuzz00; // 无症状
	private String mark;// 慢性疾病标志(1-高血压患者用药情况，2-糖尿病患者用药情况)
	private java.lang.String ydsyf0; // 胰岛素用法和用量
	private String flag;// 查询类型0为基本查询1为个人随访的查询
	private String gender;// 中文性别 '男','女'
	private String age;// 生日年龄
	private String id0000;
	private String zzbz00; // 转诊备注
	private String sfzh00;// 身份证号
	private String chxtz0; // 餐后血糖值
	private String bz0000;
	private String fname;
	private String mname;
	private String sfys;// 随访医生
	private List<T_mxjb_sf_yyqkDTO> yyqkList = new ArrayList<T_mxjb_sf_yyqkDTO>();// 用药情况
	private String yyid00;//

	public java.lang.String getSf_id0000() {
		return sf_id0000;
	}

	public void setSf_id0000(java.lang.String sf_id0000) {
		this.sf_id0000 = sf_id0000;
	}

	public java.lang.String getRef_id() {
		return ref_id;
	}

	public void setRef_id(java.lang.String ref_id) {
		this.ref_id = ref_id;
	}

	public java.lang.String getMxjbbz() {
		return mxjbbz;
	}

	public void setMxjbbz(java.lang.String mxjbbz) {
		this.mxjbbz = mxjbbz;
	}

	public java.lang.String getSfrq00() {
		return sfrq00;
	}

	public void setSfrq00(java.lang.String sfrq00) {
		this.sfrq00 = sfrq00;
	}

	public java.lang.String getSffs00() {
		return sffs00;
	}

	public void setSffs00(java.lang.String sffs00) {
		this.sffs00 = sffs00;
	}

	public java.lang.String getSsy() {
		return ssy;
	}

	public void setSsy(java.lang.String ssy) {
		this.ssy = ssy;
	}

	public java.lang.String getSzy() {
		return szy;
	}

	public void setSzy(java.lang.String szy) {
		this.szy = szy;
	}

	public java.lang.String getTzone() {
		return tzone;
	}

	public void setTzone(java.lang.String tzone) {
		this.tzone = tzone;
	}

	public java.lang.String getTztwo() {
		return tztwo;
	}

	public void setTztwo(java.lang.String tztwo) {
		this.tztwo = tztwo;
	}

	public java.lang.String getTzzs00() {
		return tzzs00;
	}

	public void setTzzs00(java.lang.String tzzs00) {
		this.tzzs00 = tzzs00;
	}

	public java.lang.String getXlone() {
		return xlone;
	}

	public void setXlone(java.lang.String xlone) {
		this.xlone = xlone;
	}

	public java.lang.String getXltwo() {
		return xltwo;
	}

	public void setXltwo(java.lang.String xltwo) {
		this.xltwo = xltwo;
	}

	public java.lang.String getZbdmpd() {
		return zbdmpd;
	}

	public void setZbdmpd(java.lang.String zbdmpd) {
		this.zbdmpd = zbdmpd;
	}

	public java.lang.String getQttz00() {
		return qttz00;
	}

	public void setQttz00(java.lang.String qttz00) {
		this.qttz00 = qttz00;
	}

	public java.lang.String getRxylone() {
		return rxylone;
	}

	public void setRxylone(java.lang.String rxylone) {
		this.rxylone = rxylone;
	}

	public java.lang.String getRxyltwo() {
		return rxyltwo;
	}

	public void setRxyltwo(java.lang.String rxyltwo) {
		this.rxyltwo = rxyltwo;
	}

	public java.lang.String getRyjlone() {
		return ryjlone;
	}

	public void setRyjlone(java.lang.String ryjlone) {
		this.ryjlone = ryjlone;
	}

	public java.lang.String getRyjltwo() {
		return ryjltwo;
	}

	public void setRyjltwo(java.lang.String ryjltwo) {
		this.ryjltwo = ryjltwo;
	}

	public java.lang.String getYdzcone() {
		return ydzcone;
	}

	public void setYdzcone(java.lang.String ydzcone) {
		this.ydzcone = ydzcone;
	}

	public java.lang.String getYdzctwo() {
		return ydzctwo;
	}

	public void setYdzctwo(java.lang.String ydzctwo) {
		this.ydzctwo = ydzctwo;
	}

	public java.lang.String getYdrcone() {
		return ydrcone;
	}

	public void setYdrcone(java.lang.String ydrcone) {
		this.ydrcone = ydrcone;
	}

	public java.lang.String getYdrctwo() {
		return ydrctwo;
	}

	public void setYdrctwo(java.lang.String ydrctwo) {
		this.ydrctwo = ydrctwo;
	}

	public java.lang.String getSyqkone() {
		return syqkone;
	}

	public void setSyqkone(java.lang.String syqkone) {
		this.syqkone = syqkone;
	}

	public java.lang.String getSyqktwo() {
		return syqktwo;
	}

	public void setSyqktwo(java.lang.String syqktwo) {
		this.syqktwo = syqktwo;
	}

	public java.lang.String getZsqkone() {
		return zsqkone;
	}

	public void setZsqkone(java.lang.String zsqkone) {
		this.zsqkone = zsqkone;
	}

	public java.lang.String getZsqktwo() {
		return zsqktwo;
	}

	public void setZsqktwo(java.lang.String zsqktwo) {
		this.zsqktwo = zsqktwo;
	}

	public java.lang.String getXltzqk() {
		return xltzqk;
	}

	public void setXltzqk(java.lang.String xltzqk) {
		this.xltzqk = xltzqk;
	}

	public java.lang.String getZyxwqk() {
		return zyxwqk;
	}

	public void setZyxwqk(java.lang.String zyxwqk) {
		this.zyxwqk = zyxwqk;
	}

	public java.lang.String getKfxtz0() {
		return kfxtz0;
	}

	public void setKfxtz0(java.lang.String kfxtz0) {
		this.kfxtz0 = kfxtz0;
	}

	public java.lang.String getThxhdb() {
		return thxhdb;
	}

	public void setThxhdb(java.lang.String thxhdb) {
		this.thxhdb = thxhdb;
	}

	public java.lang.String getJcrq00() {
		return jcrq00;
	}

	public void setJcrq00(java.lang.String jcrq00) {
		this.jcrq00 = jcrq00;
	}

	public java.lang.String getQtfzjc() {
		return qtfzjc;
	}

	public void setQtfzjc(java.lang.String qtfzjc) {
		this.qtfzjc = qtfzjc;
	}

	public java.lang.String getFyycx0() {
		return fyycx0;
	}

	public void setFyycx0(java.lang.String fyycx0) {
		this.fyycx0 = fyycx0;
	}

	public java.lang.String getYwblfy() {
		return ywblfy;
	}

	public void setYwblfy(java.lang.String ywblfy) {
		this.ywblfy = ywblfy;
	}

	public java.lang.String getBlfyms() {
		return blfyms;
	}

	public void setBlfyms(java.lang.String blfyms) {
		this.blfyms = blfyms;
	}

	public java.lang.String getDxtfy() {
		return dxtfy;
	}

	public void setDxtfy(java.lang.String dxtfy) {
		this.dxtfy = dxtfy;
	}

	public java.lang.String getCcsffl() {
		return ccsffl;
	}

	public void setCcsffl(java.lang.String ccsffl) {
		this.ccsffl = ccsffl;
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

	public java.lang.String getXcsfrq() {
		return xcsfrq;
	}

	public void setXcsfrq(java.lang.String xcsfrq) {
		this.xcsfrq = xcsfrq;
	}

	public BigDecimal getSfysqm() {
		return sfysqm;
	}

	public void setSfysqm(BigDecimal sfysqm) {
		this.sfysqm = sfysqm;
	}

	public java.lang.String getSfysxm() {
		return sfysxm;
	}

	public void setSfysxm(java.lang.String sfysxm) {
		this.sfysxm = sfysxm;
	}

	public java.lang.String getYwysxm() {
		return ywysxm;
	}

	public void setYwysxm(java.lang.String ywysxm) {
		this.ywysxm = ywysxm;
	}

	public java.lang.String getYds000() {
		return yds000;
	}

	public void setYds000(java.lang.String yds000) {
		this.yds000 = yds000;
	}

	public java.lang.String getZzbh_id() {
		return zzbh_id;
	}

	public void setZzbh_id(java.lang.String zzbh_id) {
		this.zzbh_id = zzbh_id;
	}

	public java.lang.String getRef_sf_id() {
		return ref_sf_id;
	}

	public void setRef_sf_id(java.lang.String ref_sf_id) {
		this.ref_sf_id = ref_sf_id;
	}

	public java.lang.String getTtty() {
		return ttty;
	}

	public void setTtty(java.lang.String ttty) {
		this.ttty = ttty;
	}

	public java.lang.String getExot() {
		return exot;
	}

	public void setExot(java.lang.String exot) {
		this.exot = exot;
	}

	public java.lang.String getYhem() {
		return yhem;
	}

	public void setYhem(java.lang.String yhem) {
		this.yhem = yhem;
	}

	public java.lang.String getHxkn() {
		return hxkn;
	}

	public void setHxkn(java.lang.String hxkn) {
		this.hxkn = hxkn;
	}

	public java.lang.String getXjxm() {
		return xjxm;
	}

	public void setXjxm(java.lang.String xjxm) {
		this.xjxm = xjxm;
	}

	public java.lang.String getBccxbz() {
		return bccxbz;
	}

	public void setBccxbz(java.lang.String bccxbz) {
		this.bccxbz = bccxbz;
	}

	public java.lang.String getSzfm() {
		return szfm;
	}

	public void setSzfm(java.lang.String szfm) {
		this.szfm = szfm;
	}

	public java.lang.String getXzsz() {
		return xzsz;
	}

	public void setXzsz(java.lang.String xzsz) {
		this.xzsz = xzsz;
	}

	public java.lang.String getDy() {
		return dy;
	}

	public void setDy(java.lang.String dy) {
		this.dy = dy;
	}

	public java.lang.String getDs() {
		return ds;
	}

	public void setDs(java.lang.String ds) {
		this.ds = ds;
	}

	public java.lang.String getDn() {
		return dn;
	}

	public void setDn(java.lang.String dn) {
		this.dn = dn;
	}

	public java.lang.String getSlmh() {
		return slmh;
	}

	public void setSlmh(java.lang.String slmh) {
		this.slmh = slmh;
	}

	public java.lang.String getGl() {
		return gl;
	}

	public void setGl(java.lang.String gl) {
		this.gl = gl;
	}

	public java.lang.String getXzfz() {
		return xzfz;
	}

	public void setXzfz(java.lang.String xzfz) {
		this.xzfz = xzfz;
	}

	public java.lang.String getTqzz() {
		return tqzz;
	}

	public void setTqzz(java.lang.String tqzz) {
		this.tqzz = tqzz;
	}

	public java.lang.String getDf_id() {
		return df_id;
	}

	public void setDf_id(java.lang.String df_id) {
		this.df_id = df_id;
	}

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public java.lang.String getTelphone() {
		return telphone;
	}

	public void setTelphone(java.lang.String telphone) {
		this.telphone = telphone;
	}

	public java.lang.String getAdress_city() {
		return adress_city;
	}

	public void setAdress_city(java.lang.String adress_city) {
		this.adress_city = adress_city;
	}

	public java.lang.String getAdress_rural() {
		return adress_rural;
	}

	public void setAdress_rural(java.lang.String adress_rural) {
		this.adress_rural = adress_rural;
	}

	public java.lang.String getAdress_village() {
		return adress_village;
	}

	public void setAdress_village(java.lang.String adress_village) {
		this.adress_village = adress_village;
	}

	public java.lang.String getAdrss_hnumber() {
		return adrss_hnumber;
	}

	public void setAdrss_hnumber(java.lang.String adrss_hnumber) {
		this.adrss_hnumber = adrss_hnumber;
	}

	public java.lang.String getAdress_pro() {
		return adress_pro;
	}

	public void setAdress_pro(java.lang.String adress_pro) {
		this.adress_pro = adress_pro;
	}

	public java.lang.String getAdress_county() {
		return adress_county;
	}

	public void setAdress_county(java.lang.String adress_county) {
		this.adress_county = adress_county;
	}

	public java.lang.String getSex() {
		return sex;
	}

	public void setSex(java.lang.String sex) {
		this.sex = sex;
	}

	public java.lang.String getBirthday() {
		return birthday;
	}

	public void setBirthday(java.lang.String birthday) {
		this.birthday = birthday;
	}

	public java.lang.String getRef_ci_id() {
		return ref_ci_id;
	}

	public void setRef_ci_id(java.lang.String ref_ci_id) {
		this.ref_ci_id = ref_ci_id;
	}

	public java.lang.String getF_id() {
		return f_id;
	}

	public void setF_id(java.lang.String f_id) {
		this.f_id = f_id;
	}

	public java.lang.String getSg0000() {
		return sg0000;
	}

	public void setSg0000(java.lang.String sg0000) {
		this.sg0000 = sg0000;
	}

	public java.lang.String getAddrzh() {
		return addrzh;
	}

	public void setAddrzh(java.lang.String addrzh) {
		this.addrzh = addrzh;
	}

	public java.lang.String getAddress() {
		return address;
	}

	public void setAddress(java.lang.String address) {
		this.address = address;
	}

	public java.lang.String getWuzz00() {
		return wuzz00;
	}

	public void setWuzz00(java.lang.String wuzz00) {
		this.wuzz00 = wuzz00;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public java.lang.String getYdsyf0() {
		return ydsyf0;
	}

	public void setYdsyf0(java.lang.String ydsyf0) {
		this.ydsyf0 = ydsyf0;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getId0000() {
		return id0000;
	}

	public void setId0000(String id0000) {
		this.id0000 = id0000;
	}

	public String getZzbz00() {
		return zzbz00;
	}

	public void setZzbz00(String zzbz00) {
		this.zzbz00 = zzbz00;
	}

	public String getSfzh00() {
		return sfzh00;
	}

	public void setSfzh00(String sfzh00) {
		this.sfzh00 = sfzh00;
	}

	public String getChxtz0() {
		return chxtz0;
	}

	public void setChxtz0(String chxtz0) {
		this.chxtz0 = chxtz0;
	}

	public String getBz0000() {
		return bz0000;
	}

	public void setBz0000(String bz0000) {
		this.bz0000 = bz0000;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getMname() {
		return mname;
	}

	public void setMname(String mname) {
		this.mname = mname;
	}

	public String getSfys() {
		return sfys;
	}

	public void setSfys(String sfys) {
		this.sfys = sfys;
	}

	public List<T_mxjb_sf_yyqkDTO> getYyqkList() {
		return yyqkList;
	}

	public void setYyqkList(List<T_mxjb_sf_yyqkDTO> yyqkList) {
		this.yyqkList = yyqkList;
	}

	public String getYyid00() {
		return yyid00;
	}

	public void setYyid00(String yyid00) {
		this.yyid00 = yyid00;
	}
}