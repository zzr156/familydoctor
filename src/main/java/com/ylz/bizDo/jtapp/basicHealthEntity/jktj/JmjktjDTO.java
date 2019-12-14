/*
 * CopyRight: StartTech 2005 cop. ltd.
 * All Right Reverse.
 * Class Name: <code>Jktj_ybzk</code>.
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.jktj;

import com.fasterxml.jackson.annotation.JsonView;

import java.io.Serializable;

/**
 *
 * T_dwellerfile t1, T_communityjwh t2, Jktj_ybzk t3 居民档案,社区--居委会,健康体检一般状况
 */

public class JmjktjDTO implements Serializable {
	@JsonView({IFindGroup.class})
	private String df_id; // 居民档案号
	@JsonView({IFindGroup.class})
	private String name; // 居民姓名
	@JsonView({IFindGroup.class})
	private String telphone; // 联系电话
	@JsonView({IFindGroup.class})
	private String adress_pro; // 常住地址：省（自治区、直辖市）
	@JsonView({IFindGroup.class})
	private String adress_city; // 常住地址：市（地区）
	@JsonView({IFindGroup.class})
	private String adress_county; // 常住地址：县（区）
	@JsonView({IFindGroup.class})
	private String adress_rural; // 常住地址：乡（镇、街道办事处）
	@JsonView({IFindGroup.class})
	private String adress_village; // 常住地址：村（街、路、弄）
	@JsonView({IFindGroup.class})
	private String adrss_hnumber; // 常住地址：门牌号码
	@JsonView({IFindGroup.class})
	private String birthday; // 出生日期
	@JsonView({IFindGroup.class})
	private String sex; // 性别
	@JsonView({IFindGroup.class})
	private String ref_ci_id; // 社区号
	@JsonView({IFindGroup.class})
	private String f_id; // 家庭档案号
	@JsonView({IFindGroup.class})
	private String jktj_ybzkid; // 健康体检--一般状况ID(SEQ_JKTJ_YBZKID)
	private String ybzk_tiwen; // 健康体检--一般状况--体温
	private String ybzk_ml; // 健康体检--一般状况--脉率
	private String ybzk_hxpl; // 健康体检--一般状况-呼吸频率
	private String ybzk_zszy; // 健康体检--一般状况--左舒张压
	private String ybzk_zssy; // 健康体检--一般状况--左收缩压
	private String ybzk_yszy; // 健康体检--一般状况--右舒张压
	private String ybzk_yssy; // 健康体检--一般状况--右收缩压
	@JsonView({IFindGroup.class})
	private String ybzk_sg; // 健康体检--一般状况--身高
	@JsonView({IFindGroup.class})
	private String ybzk_tz; // 健康体检--一般状况--体重
	private String ybzk_yw; // 健康体检--一般状况--腰围
	private String ybzk_tzzs; // 健康体检--一般状况--体质指数
	private String ybzk_tunwei; // 健康体检--一般状况--臀围
	private String ybzk_ytwbz; // 健康体检--一般状况--腰臀围比值
	private String ybzk_lnrzgn; // 健康体检--一般状况--老年人认知功能
	private String ybzk_lnzljc; // 健康体检--一般状况--老年人智力状态检查
	private String ybzk_lnqgzt; // 健康体检--一般状况--老年人情感状态
	private String ybzk_lnyypf; // 健康体检--一般状况--老年人抑郁评分检查
	private String yyid00; // 机构ID
	@JsonView({IFindGroup.class})
	private String jktjcs; // 健康体检--体检次数
	@JsonView({IFindGroup.class})
	private String edate; // 检查日期
	@JsonView({IFindGroup.class})
	private String doctor; // 责任医生
	private String tjzzqk; // 健康体检症状情况
	@JsonView({IFindGroup.class})
	private String jname;// 居委会名称
	private String edateBegin;// 检查日期 始
	private String edateEnd;// 检查日期 止
	@JsonView({IFindGroup.class})
	private String addrzh;// 现居地址
	@JsonView({IFindGroup.class})
	private String address;// 完整地址
	private String zz_qt0000;// 症状其他
	private String lnrjkpj;// 老年人健康状态自我评估
	private String lnrshpj; // 老年人生活自理能力自我评估

	private String bcftj; // 过滤重复体检标识
	private String wxtj; // 过滤无效体检（身高和体重）

	private String gender;// 中文性别 男 女
	private String age;//生日年龄
	private String id0000;

	private String ybzk_sgdy;// 检查日期 始
	private String ybzk_sgxy;// 检查日期 止
	private String ybzk_tzdy;// 检查日期 始
	private String ybzk_tzxy;// 检查日期 止
	@JsonView({IFindGroup.class})
	private String gxrq00; //更新日期
	private String gxsj00; //更新时间
	private String gxygbh; //更新员工编号
	@JsonView({IFindGroup.class})
	private String gxygxm; //更新员工姓名
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
	public String getAdress_pro() {
		return adress_pro;
	}
	public void setAdress_pro(String adress_pro) {
		this.adress_pro = adress_pro;
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
	public String getYyid00() {
		return yyid00;
	}
	public void setYyid00(String yyid00) {
		this.yyid00 = yyid00;
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
	public String getJname() {
		return jname;
	}
	public void setJname(String jname) {
		this.jname = jname;
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
	public String getBcftj() {
		return bcftj;
	}
	public void setBcftj(String bcftj) {
		this.bcftj = bcftj;
	}
	public String getWxtj() {
		return wxtj;
	}
	public void setWxtj(String wxtj) {
		this.wxtj = wxtj;
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
	public String getYbzk_sgdy() {
		return ybzk_sgdy;
	}
	public void setYbzk_sgdy(String ybzk_sgdy) {
		this.ybzk_sgdy = ybzk_sgdy;
	}
	public String getYbzk_sgxy() {
		return ybzk_sgxy;
	}
	public void setYbzk_sgxy(String ybzk_sgxy) {
		this.ybzk_sgxy = ybzk_sgxy;
	}
	public String getYbzk_tzdy() {
		return ybzk_tzdy;
	}
	public void setYbzk_tzdy(String ybzk_tzdy) {
		this.ybzk_tzdy = ybzk_tzdy;
	}
	public String getYbzk_tzxy() {
		return ybzk_tzxy;
	}
	public void setYbzk_tzxy(String ybzk_tzxy) {
		this.ybzk_tzxy = ybzk_tzxy;
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
	public static interface IFindGroup{}


}