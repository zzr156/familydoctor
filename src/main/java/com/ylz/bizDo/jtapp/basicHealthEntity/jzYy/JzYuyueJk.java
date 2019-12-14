package com.ylz.bizDo.jtapp.basicHealthEntity.jzYy;

public class JzYuyueJk {
	private String id;//预约state id
	private String etname;//儿童姓名
	private String ymmc;//疫苗名称
	private String jzmz;//接种门诊
	private String yyrq;//预约日期
	private String sjd;//时间段
	private String amOrpm;//上午或者下午
	private String jzzc;//接种针次
	private String jzZfmf;//接种价格
	private String jzlykey;//接种来源
	private String jzly;//接种来源
	private String yysj;//预约时间
	private String state;//状态
	private String pjfs;//平均分数
	private String jy;//建议
	private String czsj;//创建时间
	private String statename;//状态名称
	private String isJk;//是否是建卡 0接种 1建卡
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEtname() {
		return etname;
	}
	public void setEtname(String etname) {
		this.etname = etname;
	}
	public String getYmmc() {
		return ymmc;
	}
	public void setYmmc(String ymmc) {
		this.ymmc = ymmc;
	}
	public String getJzmz() {
		return jzmz;
	}
	public void setJzmz(String jzmz) {
		this.jzmz = jzmz;
	}
	public String getYyrq() {
		return yyrq;
	}
	public void setYyrq(String yyrq) {
		this.yyrq = yyrq;
	}
	public String getSjd() {
		return sjd;
	}
	public void setSjd(String sjd) {
		this.sjd = sjd;
	}
	public String getAmOrpm() {
		return amOrpm;
	}
	public void setAmOrpm(String amOrpm) {
		this.amOrpm = amOrpm;
	}
	public String getJzzc() {
		return jzzc;
	}
	public void setJzzc(String jzzc) {
		this.jzzc = jzzc;
	}
	public String getJzly() {
		return jzly;
	}
	public void setJzly(String jzly) {
		this.jzly = jzly;
	}
	public String getYysj() {
		return yysj;
	}
	public void setYysj(String yysj) {
		this.yysj = yysj;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPjfs() {
		return pjfs;
	}
	public void setPjfs(String pjfs) {
		this.pjfs = pjfs;
	}
	public String getJy() {
		return jy;
	}
	public void setJy(String jy) {
		this.jy = jy;
	}
	public String getCzsj() {
		return czsj;
	}
	public void setCzsj(String czsj) {
		this.czsj = czsj;
	}
	public String getStatename() {
		return statename;
	}
	public void setStatename(String statename) {
		this.statename = statename;
	}
	public JzYuyueJk() {
		super();
	}
	public String getJzZfmf() {
		return jzZfmf;
	}
	public void setJzZfmf(String jzZfmf) {
		this.jzZfmf = jzZfmf;
	}
	public String getIsJk() {
		return isJk;
	}
	public void setIsJk(String isJk) {
		this.isJk = isJk;
	}
	public JzYuyueJk(String id, String etname, String ymmc, String jzmz,
			String yyrq, String sjd, String amOrpm, String jzzc, String jzZfmf,
			String jzly, String yysj, String state, String pjfs, String jy,
			String czsj, String statename, String isJk,String jzlykey) {
		super();
		this.id = id;
		this.etname = etname;
		this.ymmc = ymmc;
		this.jzmz = jzmz;
		this.yyrq = yyrq;
		this.sjd = sjd;
		this.amOrpm = amOrpm;
		this.jzzc = jzzc;
		this.jzZfmf = jzZfmf;
		this.jzly = jzly;
		this.yysj = yysj;
		this.state = state;
		this.pjfs = pjfs;
		this.jy = jy;
		this.czsj = czsj;
		this.statename = statename;
		this.isJk = isJk;
		this.jzlykey = jzlykey;
	}
	public String getJzlykey() {
		return jzlykey;
	}
	public void setJzlykey(String jzlykey) {
		this.jzlykey = jzlykey;
	}
	
}
