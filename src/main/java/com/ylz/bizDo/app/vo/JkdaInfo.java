package com.ylz.bizDo.app.vo;

public class JkdaInfo {
    private String name;// 入参 姓名
    private String idno;// 入参 身份证号
    private String tel;// 入参 联系电话
    private String fid;// 入参 家庭档案号

    private int type = 0; // 入参 请求类型(默认0) 1-判断档案是否本机构管理；2-获取档案信息；3-判断是何种慢病；0-获取1 2 3全部信息

    private String jmdah;// 居民档案号
    private String jmxm;// 居民姓名
    private String sfzh;// 身份证号
    private String hj;// 户籍
    private String lxdh;// 联系电话
    private String shi;// 常住地址：市（地区）
    private String xian;// 常住地址：县（区）
    private String xiang;// 常住地址：乡（镇、街道办事处）
    private String cun;// 常住地址：村（街、路、弄）
    private String mphm;// 常住地址：门牌号码
    private String sheng;// 常住地址：省（自治区、直辖市）
    private String csrq;// 出生日期
    private String sg;// 身高
    private String tz;// 体重
    private String xb;// 性别
    private String mz;// 民族
    private String sqh;// 社区号
    private String jwhbh;// 居委会编号
    private String jtdah;// 家庭档案号
    private String yhzgx;// 居民与户主关系
    private String gxmc00;//关系名称
    //private List<JwbsjbDTO> jwbsjb;// 既往病史，疾病编号，以；号分隔()（例：高血压 时间:19930201,）
    private String gms;// 过敏史
    private String bls;// 暴露史
    private String jwbsss;// 既往病史手术
    private String jwbsws;// 既往病史外伤
    private String jwbssx;// 既往病史输血
    private String jdrq;// 建档日期
    //private HyxxDTO hyxx;// 怀孕信息
    private String zrys;// 责任医生
    private String zyzkwt;// 主要健康问题
    private String jwbsjb0;
    private String MCYJ00;// 末次月经
    private String sfyxda;    //是否有效档案，0-无效 1-有效
    private String isdead;    //是否死亡，0-否 1-是
    private String zhgxsj;    //最后更新时间
    private String xzqydm;//行政区域代码

    private int isManageFile; // 是否本机构管理的档案 1-是、0-否
    private String ncdtype; // 慢病种类：1：高血压病；2：脑血管病；3：糖尿病；4：冠心病；5：恶性肿瘤；6：慢性阻塞性肺病；-1：其他疾病；0：无

    public String getGxmc00() {
        return gxmc00;
    }

    public void setGxmc00(String gxmc00) {
        this.gxmc00 = gxmc00;
    }

    public String getXzqydm() {
        return xzqydm;
    }

    public void setXzqydm(String xzqydm) {
        this.xzqydm = xzqydm;
    }

    public String getMCYJ00() {
        return MCYJ00;
    }

    public void setMCYJ00(String mCYJ00) {
        MCYJ00 = mCYJ00;
    }

    public String getJwbsjb0() {
        return jwbsjb0;
    }

    public void setJwbsjb0(String jwbsjb0) {
        this.jwbsjb0 = jwbsjb0;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getZyzkwt() {
        return zyzkwt;
    }

    public void setZyzkwt(String zyzkwt) {
        this.zyzkwt = zyzkwt;
    }

    public String getZrys() {
        return zrys;
    }

    public void setZrys(String zrys) {
        this.zrys = zrys;
    }

    public String getJdrq() {
        return jdrq;
    }

    public void setJdrq(String jdrq) {
        this.jdrq = jdrq;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getJmdah() {
        return jmdah;
    }

    public void setJmdah(String jmdah) {
        this.jmdah = jmdah;
    }

    public String getJmxm() {
        return jmxm;
    }

    public void setJmxm(String jmxm) {
        this.jmxm = jmxm;
    }

    public String getSfzh() {
        return sfzh;
    }

    public void setSfzh(String sfzh) {
        this.sfzh = sfzh;
    }

    public String getHj() {
        return hj;
    }

    public void setHj(String hj) {
        this.hj = hj;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    public String getShi() {
        return shi;
    }

    public void setShi(String shi) {
        this.shi = shi;
    }

    public String getXian() {
        return xian;
    }

    public void setXian(String xian) {
        this.xian = xian;
    }

    public String getXiang() {
        return xiang;
    }

    public void setXiang(String xiang) {
        this.xiang = xiang;
    }

    public String getCun() {
        return cun;
    }

    public void setCun(String cun) {
        this.cun = cun;
    }

    public String getMphm() {
        return mphm;
    }

    public void setMphm(String mphm) {
        this.mphm = mphm;
    }

    public String getSheng() {
        return sheng;
    }

    public void setSheng(String sheng) {
        this.sheng = sheng;
    }

    public String getCsrq() {
        return csrq;
    }

    public void setCsrq(String csrq) {
        this.csrq = csrq;
    }

    public String getSg() {
        return sg;
    }

    public void setSg(String sg) {
        this.sg = sg;
    }

    public String getTz() {
        return tz;
    }

    public void setTz(String tz) {
        this.tz = tz;
    }

    public String getXb() {
        return xb;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }

    public String getMz() {
        return mz;
    }

    public void setMz(String mz) {
        this.mz = mz;
    }

    public String getSqh() {
        return sqh;
    }

    public void setSqh(String sqh) {
        this.sqh = sqh;
    }

    public String getJwhbh() {
        return jwhbh;
    }

    public void setJwhbh(String jwhbh) {
        this.jwhbh = jwhbh;
    }

    public String getJtdah() {
        return jtdah;
    }

    public void setJtdah(String jtdah) {
        this.jtdah = jtdah;
    }

    public String getYhzgx() {
        return yhzgx;
    }

    public void setYhzgx(String yhzgx) {
        this.yhzgx = yhzgx;
    }

    public String getGms() {
        return gms;
    }

    public void setGms(String gms) {
        this.gms = gms;
    }

    public String getBls() {
        return bls;
    }

    public void setBls(String bls) {
        this.bls = bls;
    }

    public String getJwbsss() {
        return jwbsss;
    }

    public void setJwbsss(String jwbsss) {
        this.jwbsss = jwbsss;
    }

    public String getJwbsws() {
        return jwbsws;
    }

    public void setJwbsws(String jwbsws) {
        this.jwbsws = jwbsws;
    }

    public String getJwbssx() {
        return jwbssx;
    }

    public void setJwbssx(String jwbssx) {
        this.jwbssx = jwbssx;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIsManageFile() {
        return isManageFile;
    }

    public void setIsManageFile(int isManageFile) {
        this.isManageFile = isManageFile;
    }

    public String getNcdtype() {
        return ncdtype;
    }

    public void setNcdtype(String ncdtype) {
        this.ncdtype = ncdtype;
    }

    public String getSfyxda() {
        return sfyxda;
    }

    public void setSfyxda(String sfyxda) {
        this.sfyxda = sfyxda;
    }

    public String getIsdead() {
        return isdead;
    }

    public void setIsdead(String isdead) {
        this.isdead = isdead;
    }

    public static interface IFindGroup {
    }

    public String getZhgxsj() {
        return zhgxsj;
    }

    public void setZhgxsj(String zhgxsj) {
        this.zhgxsj = zhgxsj;
    }

    public static void main(String[] args) {
        String s1 = "20170516 12:18:51";
        String s2 = "20170515 24:18:51";
        int i = s1.compareTo(s2);
        if (i == 1) {
            System.out.println("s1:" + s1);
        } else if (i == -1) {
            System.out.println("s2:" + s2);
        } else {
            System.out.println("相等");
        }
    }
}
