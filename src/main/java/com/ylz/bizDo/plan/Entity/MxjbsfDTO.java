package com.ylz.bizDo.plan.Entity;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 各种慢性疾病患者随访信息
 * @author WTJ
 *
 */

public class MxjbsfDTO implements Serializable {


	private  String sf_id0000; //随访编号
	private  String ref_id; //慢性疾病患者编号
	private  String mxjbbz; //慢性疾病编号
	private  String sfrq00; //随访日期
	private  String sffs00; //随访方式（1-门诊，2--家庭，3--电话）
	private  String ssy; //收缩压（血压）
	private  String szy; //舒张压（血压）
	private  String tzone; //体重（目前）
	private  String tztwo; //体重（计划）
	private  String tzzs00; //体质指数
	private  String xlone; //心率（目前）
	private  String xltwo; //心率（计划）
	private  String zbdmpd; //足背动脉搏动（1--未触及，2--触及）
	private  String qttz00; //其他体征
	private  String rxylone; //日吸烟量（目前）
	private  String rxyltwo; //日吸烟量（计划）
	private  String ryjlone; //日饮酒量（目前）
	private  String ryjltwo; //日饮酒量（计划）
	private  String ydzcone; //运动周次（目前）
	private  String ydzctwo; //运动周次（计划）
	private  String ydrcone; //运动分次（目前）
	private  String ydrctwo; //运动分次（计划）
	private  String syqkone; //摄盐情况（目前）
	private  String syqktwo; //摄盐情况（计划）
	private  String zsqkone; //主食情况（目前）
	private  String zsqktwo; //主食情况（计划）
	private  String xltzqk; //心理调整（1--良好，2--一般，3--差）
	private  String zyxwqk; //遵医情况（1--良好，2---一般，3--差）
	private  String kfxtz0; //空腹血糖值
	private  String thxhdb; //糖化血红蛋白
	private  String jcrq00; //检查日期
	private  String qtfzjc; //其他辅助检查
	private  String fyycx0; //服药依从性（1--规律，2--间断，3--不服药）
	private  String ywblfy; //药物不良反应（0--无，1--有）
	private  String blfyms; //不良反应描述
	private  String dxtfy; //低血糖反应（1--无，2--偶尔，3--频繁）
	private  String ccsffl; //此次随访分类（1--控制满意，2--控制不满意，3--不良反应，4--并发症）
	private  String zzqk00; //转诊情况（0--无，1--有）
	private  String zzyy00; //转诊原因
	private  String zzjgks; //转诊机构科室
	private  String xcsfrq; //下次随访日期
	private  String sfysqm; //随访医生签名
	private  String sfysxm; // 随访医生姓名
	private  String ywysxm; //院外医生姓名
	private  String yds000; //胰岛素种类
	private  String zzbh_id; //症状编号
	private  String ref_sf_id; //随访记录编号
	private  String ttty; //头痛头晕
	private  String exot; //恶心呕吐
	private  String yhem; //眼花耳鸣
	private  String hxkn; //呼吸困难
	private  String xjxm; //心悸胸闷
	private  String bccxbz; //鼻子出血不止
	private  String szfm; //(高血压-四肢麻木，糖尿病-手脚麻木)
	private  String xzsz; //(高血压-下肢水肿 )
	private  String dy; //多饮
	private  String ds; //多食
	private  String dn; //多尿
	private  String slmh; //视力模糊
	private  String gl; //感染
	private  String xzfz; //糖尿病-四肢浮肿
	private  String tqzz; //其他症状
	private  String df_id; //居民档案号
	private  String name; //居民姓名
	private  String telphone; //联系电话
	private  String adress_city; //常住地址：市（地区）
	private  String adress_rural; //常住地址：乡（镇、街道办事处）
	private  String adress_village; //常住地址：村（街、路、弄）
	private  String adrss_hnumber; //常住地址：门牌号码
	private  String adress_pro; //常住地址：省（自治区、直辖市）
	private  String adress_county; //常住地址：县（区）
	private  String sex; //性别
	private  String birthday; //出生日期
	private  String ref_ci_id; //社区号
	private  String f_id; //家庭档案号
	private  String sg0000; //身高
	private String addrzh;//组合地址
	private String address;//完整地址
	private  String wuzz00; //无症状
	private String mark;//慢性疾病标志(1-高血压患者用药情况，2-糖尿病患者用药情况)
	private  String ydsyf0; //胰岛素用法和用量
	private String flag;//查询类型0为基本查询1为个人随访的查询
	private String gender;//中文性别  '男','女'
	private String age;//生日年龄
	private String id0000;
	private String zzbz00; //转诊备注
	private String sfzh00;//身份证号
	private String chxtz0; // 餐后血糖值
	private String bz0000;
	private String fname;
	private String mname;
	private String sfys;//随访医生
	private List<T_mxjb_sf_yyqkDTO> yyqkList=new ArrayList<T_mxjb_sf_yyqkDTO>();//用药情况

	private String ygbh00;		//员工编号
	private String urlType;//请求地市  1.福州,2泉州,3漳州,4.莆田,5.南平,6.三明,7.测试

	public String getSf_id0000() {
		return sf_id0000;
	}

	public void setSf_id0000(String sf_id0000) {
		this.sf_id0000 = sf_id0000;
	}

	/**
	 * 慢性疾病患者编号
	 * @return
	 */
	public String getRef_id() {
		return ref_id;
	}

	/**
	 * 慢性疾病患者编号
	 * @param ref_id
	 */
	public void setRef_id(String ref_id) {
		this.ref_id = ref_id;
	}

	public String getMxjbbz() {
		return mxjbbz;
	}

	public void setMxjbbz(String mxjbbz) {
		this.mxjbbz = mxjbbz;
	}

	public String getSfrq00() {
		return sfrq00;
	}

	public void setSfrq00(String sfrq00) {
		this.sfrq00 = sfrq00;
	}

	public String getSffs00() {
		return sffs00;
	}

	public void setSffs00(String sffs00) {
		this.sffs00 = sffs00;
	}

	public String getSsy() {
		return ssy;
	}

	public void setSsy(String ssy) {
		this.ssy = ssy;
	}

	public String getSzy() {
		return szy;
	}

	public void setSzy(String szy) {
		this.szy = szy;
	}

	public String getTzone() {
		return tzone;
	}

	public void setTzone(String tzone) {
		this.tzone = tzone;
	}

	public String getTztwo() {
		return tztwo;
	}

	public void setTztwo(String tztwo) {
		this.tztwo = tztwo;
	}

	public String getTzzs00() {
		return tzzs00;
	}

	public void setTzzs00(String tzzs00) {
		this.tzzs00 = tzzs00;
	}

	public String getXlone() {
		return xlone;
	}

	public void setXlone(String xlone) {
		this.xlone = xlone;
	}

	public String getXltwo() {
		return xltwo;
	}

	public void setXltwo(String xltwo) {
		this.xltwo = xltwo;
	}

	public String getZbdmpd() {
		return zbdmpd;
	}

	public void setZbdmpd(String zbdmpd) {
		this.zbdmpd = zbdmpd;
	}

	public String getQttz00() {
		return qttz00;
	}

	public void setQttz00(String qttz00) {
		this.qttz00 = qttz00;
	}

	public String getRxylone() {
		return rxylone;
	}

	public void setRxylone(String rxylone) {
		this.rxylone = rxylone;
	}

	public String getRxyltwo() {
		return rxyltwo;
	}

	public void setRxyltwo(String rxyltwo) {
		this.rxyltwo = rxyltwo;
	}

	public String getRyjlone() {
		return ryjlone;
	}

	public void setRyjlone(String ryjlone) {
		this.ryjlone = ryjlone;
	}

	public String getRyjltwo() {
		return ryjltwo;
	}

	public void setRyjltwo(String ryjltwo) {
		this.ryjltwo = ryjltwo;
	}

	public String getYdzcone() {
		return ydzcone;
	}

	public void setYdzcone(String ydzcone) {
		this.ydzcone = ydzcone;
	}

	public String getYdzctwo() {
		return ydzctwo;
	}

	public void setYdzctwo(String ydzctwo) {
		this.ydzctwo = ydzctwo;
	}

	public String getYdrcone() {
		return ydrcone;
	}

	public void setYdrcone(String ydrcone) {
		this.ydrcone = ydrcone;
	}

	public String getYdrctwo() {
		return ydrctwo;
	}

	public void setYdrctwo(String ydrctwo) {
		this.ydrctwo = ydrctwo;
	}

	public String getSyqkone() {
		return syqkone;
	}

	public void setSyqkone(String syqkone) {
		this.syqkone = syqkone;
	}

	public String getSyqktwo() {
		return syqktwo;
	}

	public void setSyqktwo(String syqktwo) {
		this.syqktwo = syqktwo;
	}

	public String getZsqkone() {
		return zsqkone;
	}

	public void setZsqkone(String zsqkone) {
		this.zsqkone = zsqkone;
	}

	public String getZsqktwo() {
		return zsqktwo;
	}

	public void setZsqktwo(String zsqktwo) {
		this.zsqktwo = zsqktwo;
	}

	public String getXltzqk() {
		return xltzqk;
	}

	public void setXltzqk(String xltzqk) {
		this.xltzqk = xltzqk;
	}

	public String getZyxwqk() {
		return zyxwqk;
	}

	public void setZyxwqk(String zyxwqk) {
		this.zyxwqk = zyxwqk;
	}

	public String getKfxtz0() {
		return kfxtz0;
	}

	public void setKfxtz0(String kfxtz0) {
		this.kfxtz0 = kfxtz0;
	}

	public String getThxhdb() {
		return thxhdb;
	}

	public void setThxhdb(String thxhdb) {
		this.thxhdb = thxhdb;
	}

	public String getJcrq00() {
		return jcrq00;
	}

	public void setJcrq00(String jcrq00) {
		this.jcrq00 = jcrq00;
	}

	public String getQtfzjc() {
		return qtfzjc;
	}

	public void setQtfzjc(String qtfzjc) {
		this.qtfzjc = qtfzjc;
	}

	public String getFyycx0() {
		return fyycx0;
	}

	public void setFyycx0(String fyycx0) {
		this.fyycx0 = fyycx0;
	}

	public String getYwblfy() {
		return ywblfy;
	}

	public void setYwblfy(String ywblfy) {
		this.ywblfy = ywblfy;
	}

	public String getBlfyms() {
		return blfyms;
	}

	public void setBlfyms(String blfyms) {
		this.blfyms = blfyms;
	}

	public String getDxtfy() {
		return dxtfy;
	}

	public void setDxtfy(String dxtfy) {
		this.dxtfy = dxtfy;
	}

	public String getCcsffl() {
		return ccsffl;
	}

	public void setCcsffl(String ccsffl) {
		this.ccsffl = ccsffl;
	}

	public String getZzqk00() {
		return zzqk00;
	}

	public void setZzqk00(String zzqk00) {
		this.zzqk00 = zzqk00;
	}

	public String getZzyy00() {
		return zzyy00;
	}

	public void setZzyy00(String zzyy00) {
		this.zzyy00 = zzyy00;
	}

	public String getZzjgks() {
		return zzjgks;
	}

	public void setZzjgks(String zzjgks) {
		this.zzjgks = zzjgks;
	}

	public String getXcsfrq() {
		return xcsfrq;
	}

	public void setXcsfrq(String xcsfrq) {
		this.xcsfrq = xcsfrq;
	}

	public String getSfysqm() {
		return sfysqm;
	}

	public void setSfysqm(String sfysqm) {
		this.sfysqm = sfysqm;
	}

	public String getYwysxm() {
		return ywysxm;
	}

	public void setYwysxm(String ywysxm) {
		this.ywysxm = ywysxm;
	}

	public String getYds000() {
		return yds000;
	}

	public void setYds000(String yds000) {
		this.yds000 = yds000;
	}

	public String getZzbh_id() {
		return zzbh_id;
	}

	public void setZzbh_id(String zzbh_id) {
		this.zzbh_id = zzbh_id;
	}

	public String getRef_sf_id() {
		return ref_sf_id;
	}

	public void setRef_sf_id(String ref_sf_id) {
		this.ref_sf_id = ref_sf_id;
	}

	public String getTtty() {
		return ttty;
	}

	public void setTtty(String ttty) {
		this.ttty = ttty;
	}

	public String getExot() {
		return exot;
	}

	public void setExot(String exot) {
		this.exot = exot;
	}

	public String getYhem() {
		return yhem;
	}

	public void setYhem(String yhem) {
		this.yhem = yhem;
	}

	public String getHxkn() {
		return hxkn;
	}

	public void setHxkn(String hxkn) {
		this.hxkn = hxkn;
	}

	public String getXjxm() {
		return xjxm;
	}

	public void setXjxm(String xjxm) {
		this.xjxm = xjxm;
	}

	public String getBccxbz() {
		return bccxbz;
	}

	public void setBccxbz(String bccxbz) {
		this.bccxbz = bccxbz;
	}

	public String getSzfm() {
		return szfm;
	}

	public void setSzfm(String szfm) {
		this.szfm = szfm;
	}

	public String getXzsz() {
		return xzsz;
	}

	public void setXzsz(String xzsz) {
		this.xzsz = xzsz;
	}

	public String getDy() {
		return dy;
	}

	public void setDy(String dy) {
		this.dy = dy;
	}

	public String getDs() {
		return ds;
	}

	public void setDs(String ds) {
		this.ds = ds;
	}

	public String getDn() {
		return dn;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getSlmh() {
		return slmh;
	}

	public void setSlmh(String slmh) {
		this.slmh = slmh;
	}

	public String getGl() {
		return gl;
	}

	public void setGl(String gl) {
		this.gl = gl;
	}

	public String getXzfz() {
		return xzfz;
	}

	public void setXzfz(String xzfz) {
		this.xzfz = xzfz;
	}

	public String getTqzz() {
		return tqzz;
	}

	public void setTqzz(String tqzz) {
		this.tqzz = tqzz;
	}

	public String getDf_id() {
		return df_id;
	}

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

	public String getAdress_county() {
		return adress_county;
	}

	public void setAdress_county(String adress_county) {
		this.adress_county = adress_county;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
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

	public String getSg0000() {
		return sg0000;
	}

	public void setSg0000(String sg0000) {
		this.sg0000 = sg0000;
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

	public String getWuzz00() {
		return wuzz00;
	}

	public void setWuzz00(String wuzz00) {
		this.wuzz00 = wuzz00;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getYdsyf0() {
		return ydsyf0;
	}

	public void setYdsyf0(String ydsyf0) {
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


	public String getSfysxm() {
		return sfysxm;
	}

	public void setSfysxm(String sfysxm) {
		this.sfysxm = sfysxm;
	}

	public List<T_mxjb_sf_yyqkDTO> getYyqkList() {
		return yyqkList;
	}

	public void setYyqkList(List<T_mxjb_sf_yyqkDTO> yyqkList) {
		this.yyqkList = yyqkList;
	}

	public String getYgbh00() {
		return ygbh00;
	}

	public void setYgbh00(String ygbh00) {
		this.ygbh00 = ygbh00;
	}

	public String getUrlType() {
		return urlType;
	}

	public void setUrlType(String urlType) {
		this.urlType = urlType;
	}
}