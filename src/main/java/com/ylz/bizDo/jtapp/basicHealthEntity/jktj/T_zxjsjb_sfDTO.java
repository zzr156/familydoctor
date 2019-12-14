package com.ylz.bizDo.jtapp.basicHealthEntity.jktj;

import java.util.ArrayList;
import java.util.List;

public class T_zxjsjb_sfDTO {
	public T_zxjsjb_sfDTO(){

	}
	private String sf_bhid; // 重性精神疾病随访编号
	private String id0000; // 重性精神疾病患者编号
	private String sfrq00; // 随访日期
	private String zzlqk0; // 自知力（1---自知力完全，2---自知力不全，3--自知力缺失）
	private String smqk00; // 睡眠情况（1---良好，2---一般，3---较差）
	private String ysqk00; // 饮食情况（1----良好，2---一般，3---较差）
	private String grshll; // 个人生活料理（1----良好，2---一般，3---较差）
	private String jwld00; // 家务劳动（1----良好，2---一般，3---较差）
	private String scldgz; // 生成劳动及工作（1----良好，2---一般，3---较差，9------此项不适用）
	private String xxnl00; // 学习能力（1----良好，2---一般，3---较差）
	private String shrjjw; // 社会人际交往（1----良好，2---一般，3---较差）
	private String sysjc0; // 实验室检查
	private String fyycx0; // 服药依从性（1---规律，2---间断，3---不服药）
	private String ywblfy; // 药物不良反应情况（无，有）
	private String blfyms; // 药物不良反应描述
	private String zlxg00; // 治疗效果（1---痊愈，2---好转，3---无变化，4---加重）
	private String ccsffl; // 此次随访分类（1---稳定，2----基本稳定，3---不稳定）
	private String zzqk00; // 转诊情况（是否）
	private String zzyy00; // 转诊原因
	private String zzjgks; // 转诊机构科室
	private String shldnl; // 生活劳动能力（康复措施）
	private String zyxlqk; // 职业训练（康复措施）
	private String xxnlqk; // 学习能力（康复措施）
	private String shjwqk; // 社会交往（康复措施）
	private String qtqk00; // 其他（康复措施）
	private String xcsfrq; // 下次随访日期
	private String sfys00; // 随访医生（院内）
	private String wyysxm; // 外院医生姓名
	private String jwszz0; // 既往主要症状
	private String qtzz00; // 其他症状（既往主要症状）

	private String qdzscs; // 轻度滋事（患者对家庭社会的影响）

	private String zhscs0; // 肇事（患者对家庭社会的影响）

	private String zhhcs0; // 肇祸（患者对家庭社会的影响）

	private String zscs00; // 自伤（患者对家庭社会的影响）

	private String zswscs; // 自杀未遂（患者对家庭社会的影响）

	private String sffs00; // 随访方式（T_SFFS：1--门诊，2--家庭，3--电话）
	private String qkbz00; // 情况备注
	private String wxxing; // 危险性
	private String dsqk00; // 关锁情况
	private String zyqk00; // 住院情况
	private String mccysj;// 末次出院时间
	private String bcsffl;// 本次随访分类
	private List<T_mxjb_sf_yyqkDTO> yyqkList=new ArrayList<T_mxjb_sf_yyqkDTO>();//用药情况
	private String ysxm00;//随访医生中文姓名

	public List<T_mxjb_sf_yyqkDTO> getYyqkList() {
		return yyqkList;
	}
	public void setYyqkList(List<T_mxjb_sf_yyqkDTO> yyqkList) {
		this.yyqkList = yyqkList;
	}
	public String getSf_bhid() {
		return sf_bhid;
	}
	public void setSf_bhid(String sf_bhid) {
		this.sf_bhid = sf_bhid;
	}
	public String getId0000() {
		return id0000;
	}
	public void setId0000(String id0000) {
		this.id0000 = id0000;
	}
	public String getSfrq00() {
		return sfrq00;
	}
	public void setSfrq00(String sfrq00) {
		this.sfrq00 = sfrq00;
	}
	public String getZzlqk0() {
		return zzlqk0;
	}
	public void setZzlqk0(String zzlqk0) {
		this.zzlqk0 = zzlqk0;
	}
	public String getSmqk00() {
		return smqk00;
	}
	public void setSmqk00(String smqk00) {
		this.smqk00 = smqk00;
	}
	public String getYsqk00() {
		return ysqk00;
	}
	public void setYsqk00(String ysqk00) {
		this.ysqk00 = ysqk00;
	}
	public String getGrshll() {
		return grshll;
	}
	public void setGrshll(String grshll) {
		this.grshll = grshll;
	}
	public String getJwld00() {
		return jwld00;
	}
	public void setJwld00(String jwld00) {
		this.jwld00 = jwld00;
	}
	public String getScldgz() {
		return scldgz;
	}
	public void setScldgz(String scldgz) {
		this.scldgz = scldgz;
	}
	public String getXxnl00() {
		return xxnl00;
	}
	public void setXxnl00(String xxnl00) {
		this.xxnl00 = xxnl00;
	}
	public String getShrjjw() {
		return shrjjw;
	}
	public void setShrjjw(String shrjjw) {
		this.shrjjw = shrjjw;
	}
	public String getSysjc0() {
		return sysjc0;
	}
	public void setSysjc0(String sysjc0) {
		this.sysjc0 = sysjc0;
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
	public String getZlxg00() {
		return zlxg00;
	}
	public void setZlxg00(String zlxg00) {
		this.zlxg00 = zlxg00;
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
	public String getShldnl() {
		return shldnl;
	}
	public void setShldnl(String shldnl) {
		this.shldnl = shldnl;
	}
	public String getZyxlqk() {
		return zyxlqk;
	}
	public void setZyxlqk(String zyxlqk) {
		this.zyxlqk = zyxlqk;
	}
	public String getXxnlqk() {
		return xxnlqk;
	}
	public void setXxnlqk(String xxnlqk) {
		this.xxnlqk = xxnlqk;
	}
	public String getShjwqk() {
		return shjwqk;
	}
	public void setShjwqk(String shjwqk) {
		this.shjwqk = shjwqk;
	}
	public String getQtqk00() {
		return qtqk00;
	}
	public void setQtqk00(String qtqk00) {
		this.qtqk00 = qtqk00;
	}
	public String getXcsfrq() {
		return xcsfrq;
	}
	public void setXcsfrq(String xcsfrq) {
		this.xcsfrq = xcsfrq;
	}
	public String getSfys00() {
		return sfys00;
	}
	public void setSfys00(String sfys00) {
		this.sfys00 = sfys00;
	}
	public String getWyysxm() {
		return wyysxm;
	}
	public void setWyysxm(String wyysxm) {
		this.wyysxm = wyysxm;
	}
	public String getJwszz0() {
		return jwszz0;
	}
	public void setJwszz0(String jwszz0) {
		this.jwszz0 = jwszz0;
	}
	public String getQtzz00() {
		return qtzz00;
	}
	public void setQtzz00(String qtzz00) {
		this.qtzz00 = qtzz00;
	}
	public String getQdzscs() {
		return qdzscs;
	}
	public void setQdzscs(String qdzscs) {
		this.qdzscs = qdzscs;
	}
	public String getZhscs0() {
		return zhscs0;
	}
	public void setZhscs0(String zhscs0) {
		this.zhscs0 = zhscs0;
	}
	public String getZhhcs0() {
		return zhhcs0;
	}
	public void setZhhcs0(String zhhcs0) {
		this.zhhcs0 = zhhcs0;
	}
	public String getZscs00() {
		return zscs00;
	}
	public void setZscs00(String zscs00) {
		this.zscs00 = zscs00;
	}
	public String getZswscs() {
		return zswscs;
	}
	public void setZswscs(String zswscs) {
		this.zswscs = zswscs;
	}
	public String getSffs00() {
		return sffs00;
	}
	public void setSffs00(String sffs00) {
		this.sffs00 = sffs00;
	}
	public String getQkbz00() {
		return qkbz00;
	}
	public void setQkbz00(String qkbz00) {
		this.qkbz00 = qkbz00;
	}
	public String getWxxing() {
		return wxxing;
	}
	public void setWxxing(String wxxing) {
		this.wxxing = wxxing;
	}
	public String getDsqk00() {
		return dsqk00;
	}
	public void setDsqk00(String dsqk00) {
		this.dsqk00 = dsqk00;
	}
	public String getZyqk00() {
		return zyqk00;
	}
	public void setZyqk00(String zyqk00) {
		this.zyqk00 = zyqk00;
	}
	public String getMccysj() {
		return mccysj;
	}
	public void setMccysj(String mccysj) {
		this.mccysj = mccysj;
	}
	public String getBcsffl() {
		return bcsffl;
	}
	public void setBcsffl(String bcsffl) {
		this.bcsffl = bcsffl;
	}

	public String getYsxm00() {
		return ysxm00;
	}

	public void setYsxm00(String ysxm00) {
		this.ysxm00 = ysxm00;
	}
}
