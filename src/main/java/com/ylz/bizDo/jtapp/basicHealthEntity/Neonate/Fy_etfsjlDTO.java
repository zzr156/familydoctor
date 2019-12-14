package com.ylz.bizDo.jtapp.basicHealthEntity.Neonate;

import com.fasterxml.jackson.annotation.JsonView;
/**
 * 新生儿家庭访视表
 * @author hp
 *
 */
public class Fy_etfsjlDTO {
	@JsonView({IFindGroup.class})
    private String xtbhsf;//随访系统编号

    private String scxtbh;//手册系统内码

    private String ssnnz0;//所属年龄组（0--新生儿，1-1岁以内儿童，2--1-2岁儿童，3--3岁以上儿童）

    @JsonView({IFindGroup.class})
    private String xmxl00;//检查项目时间段（0--新生儿，1--满月，2-3月龄，3--6月龄，4-8月龄，5-12月龄，6-18月龄，7-24月龄，8-30月龄,9-3岁,a-4岁,b-5岁,c-6岁

	@JsonView({IFindGroup.class})
    private String etsg00;//目前身长

	@JsonView({IFindGroup.class})
    private String ettz00;//目前身长

	@JsonView({IFindGroup.class})
    private String sgqk00;//身高情况（1--上，2-中，3-下）

	@JsonView({IFindGroup.class})
    private String tzqk00;//体重情况（1--上，2--中，3--下）

	@JsonView({IFindGroup.class})
    private String tw0000;//头围cm(头围的测量值，计量单位为cm)   2

    @JsonView({IFindGroup.class})
    private String wyfs00;//喂养方式(1纯母乳，2混合，3人工)

    @JsonView({IFindGroup.class})
    private String cnl000;//吃奶量（单位ml/次）

    @JsonView({IFindGroup.class})
    private String cncs00;//吃奶次数（单位次/日）

    @JsonView({IFindGroup.class})
    private String ot0000;//呕吐（1无，2有）

    @JsonView({IFindGroup.class})
    private String db0000;//大便（1糊状，2稀）

    @JsonView({IFindGroup.class})
    private String dbcs00;//大便次数(单位：次/日)

	@JsonView({IFindGroup.class})
    private String twqk00;//体温

	@JsonView({IFindGroup.class})
    private String ml0000;//脉率

	@JsonView({IFindGroup.class})
    private String hxpl00;//呼吸频率

	@JsonView({IFindGroup.class})
    private String msqk00;//面色情况（1--红润，2--黄染，3-其他）   123(3检查时2表示其他)

	@JsonView({IFindGroup.class})
    private String hhbw00;//黄疸部位  1-面部 2-躯干 3-四肢 4手足

	@JsonView({IFindGroup.class})
    private String qcone0;//前囱1   123

	@JsonView({IFindGroup.class})
    private String qctwo0;//前囱2   123

	@JsonView({IFindGroup.class})
    private String qcqk00;//前囱类型（1-正常，2-膨隆，3-凹陷，4--其他）  123（23检查时是只填1闭合，2未闭）

	@JsonView({IFindGroup.class})
    private String eyeqk0;//眼外观（1未见异常，2异常）   123

	@JsonView({IFindGroup.class})
    private String earqk0;//耳外观（1未见异常，2异常）   123

	@JsonView({IFindGroup.class})
    private String noseqk;//鼻（1未见异常，2异常）   1

	@JsonView({IFindGroup.class})
    private String kqqk00;//口腔情况（1未见异常，2异常）   1234（6月龄，8月龄表示出牙数；1～2岁表示出牙数和龋齿数）

	@JsonView({IFindGroup.class})
    private String xftz00;//心肺听诊（1未见异常，2异常）   1234

	@JsonView({IFindGroup.class})
    private String fbcz00;//腹部触诊（1未见异常，2异常）   1234

	@JsonView({IFindGroup.class})
    private String szhdqk;//四肢活动度（1未见异常，2异常） 123

	@JsonView({IFindGroup.class})
    private String jbbkqk;//颈部包块（1无，2有）           12（一岁检查是只填1有，2无）

	@JsonView({IFindGroup.class})
    private String skinqk;//皮肤情况（1-未见异常，2-湿疹，3--糜烂，4-其他）   123（23检查是只填1未见异常，2异常）

	@JsonView({IFindGroup.class})
    private String gmqk00;//肛门情况（1未见异常，2异常）

	@JsonView({IFindGroup.class})
    private String wszqqk;//外生殖器情况（1未见异常，2异常）

	@JsonView({IFindGroup.class})
    private String jzqk00;//脊柱情况（1未见异常，2异常）         1

	@JsonView({IFindGroup.class})
    private String qdqk00;//脐带情况（1--未脱，2-脱落，3--脐部有渗出，4-其他）  12

    private String tl0000;//听力（1通过，2未通过）

    @JsonView({IFindGroup.class})
    private String glbzz0;//佝偻病症状（1--无，2-夜惊，3--多汗，4--烦躁）

    @JsonView({IFindGroup.class})
    private String glbtz0;//佝偻病体征（1--无，2--颅外骨软化，3--方颅，4--枕秃，5-肋串珠，6--肋外翻，7--肋软骨沟，8-鸡胸，9--手镯征，10-o型腿，11-X型腿)

    @JsonView({IFindGroup.class})
    private String xhdb00;//血红蛋白值（单位：g/L）

    @JsonView({IFindGroup.class})
    private String hwhdqk;//户外活动(单位：小时/日)

    @JsonView({IFindGroup.class})
    private String wss000;//维生素D/钙剂添加标志(表示是否添加维生素D和（或）钙剂)

    @JsonView({IFindGroup.class})
    private String fypgqk;//发育评估情况（1-通过，2--未通过）

    @JsonView({IFindGroup.class})
    private String hbqk00;//两次随访间患病情况（1-未患病，2--患病）

    @JsonView({IFindGroup.class})
    private String hbqkfy;//两次随访间患病具体情况-肺炎(2)

    @JsonView({IFindGroup.class})
    private String hbqkfx;//两次随访间患病具体情况-腹泻

    @JsonView({IFindGroup.class})
    private String hbqkws;//两次随访间患病具体情况-外伤(4)

    @JsonView({IFindGroup.class})
    private String hbqkqt;//两次随访间患病具体情况-其他(3-6岁)

    @JsonView({IFindGroup.class})
    private String qt0000;//两次随访间患病具体情况-其他(1岁以内、1-2岁)

    private String btqk00;//

    @JsonView({IFindGroup.class})
    private String tgfypj;//体格发育评价:1.正常2.低体重3.消瘦4.发育迟缓5.超重6.肥胖

    private String sl0000;//

    @JsonView({IFindGroup.class})
    private String zdqk00;//指导:1.科学喂养2.生长发育3.疾病预防4.预防意外伤害5.口腔保健，6.其他

    @JsonView({IFindGroup.class})
    private String zzqk00;//转诊情况（2.有，1.无）

    @JsonView({IFindGroup.class})
    private String zzyy00;//转诊原因

    @JsonView({IFindGroup.class})
    private String zzjgks;//转诊机构及科室

	@JsonView({IFindGroup.class})
    private String xcsfrq;//下次随访日期      1234

	@JsonView({IFindGroup.class})
    private String xcsfdd;//下次随访地点      1

	@JsonView({IFindGroup.class})
    private String sfrq00;//随访日期

	@JsonView({IFindGroup.class})
    private String sfys00;//随访医生        1234

    private String ewfsbj;//

    private String sfysid;//

    private String yyid00;//

    private String kqqkse;//

    private String sly000;//

	@JsonView({IFindGroup.class})
    private String sfjg00;//随访结果 1正常   2异常

    private String zzks00;//

    private String zzbjz0;//

    private String zhbjrq;//

    private String bz0000;//

    private String etsjss;//

    private String etsjys;//

    private String etsjts;//

    private String wiId;//

    private String yscys;//

    private String cjz000;//

    private String cjrq00;//

    private String tzpjxx;//

    private String sgpjxx;//

    private String twpj00;//

    private String zdjgyy;//

    private String zdjgbt;//

    private String zdjgqt;//

    private String zdjggw;//

    private String zhpj00;//

    private String zhpjna;//

    private String yypj00;//

    private String yypjna;//

    private String delmak;//

    private String issc00;//

    private String sctzpj;//

    private String yygs00;//

    private String gwidyy;//

    private String gwidpx;//

    private String gwidgl;//

    private String gwiddj;//

    private String gwpg00;//

    private String czwt00;//

    private String gwzd00;//

    private String gwzl00;//

    private String gwvdzl;//

    private String czwtpx;//

    private String gwzdpx;//

    private String czwtgl;//

    private String gwzdgl;//

    private String twbzc0;//

    private String yeyid0;//

    private String bjid00;//

    private String zyjkzd;//中医指导情况（1--中医饮食调养指导，2--中医起居调摄指导，3--传授摩腹、捏脊方法，4--传授按揉迎香穴、足三里穴方法，5--传授按揉四神聪穴方法，6--其他）

    private String ysty00;//

    private String qjts00;//

    private String xwarff;//

    private String zwqk00;//

    private String myd000;//

	@JsonView({IFindGroup.class})
    private String xl0000;//心率

    private String zzdpx0;//

    private String gwidza;//

    private String pgjg00;//

    private String pgjgcl;//

    private String pgffxl;//

    private String pgjgxl;//

    private String gwzdxl;//

    private String gwzdcl;//

    private String gwidxl;//

    private String sfwc00;//

    private String pgffza;//

    private String pgjgza;//

    private String zd00za;//

    private String cl00za;//

    private String remark;//

    private String tsnan0;//

    private String tsnv00;//

    private String ts0000;//

    private String scbz00;//

    private String scxtbhtemp;//

    private String qdpx00;//

    /**
     * 儿童保健手册中参数
     */
    @JsonView({IFindGroup.class})
    private String mqsfzh;//母亲身份证号
    @JsonView({IFindGroup.class})
    private String fqsfzh;//父亲身份证号
    @JsonView({IFindGroup.class})
    private String xm0000;//儿童姓名
    @JsonView({IFindGroup.class})
    private String mqxm00;//母亲姓名
    @JsonView({IFindGroup.class})
    private String fqxm00;//父亲姓名
    private String sfdq00;//是否单亲1.是2.否
    @JsonView({IFindGroup.class})
    private String fqzy00;//父亲职业
    @JsonView({IFindGroup.class})
    private String fqcsrq;//父亲出生日期
    @JsonView({IFindGroup.class})
    private String mqzy00;//母亲职业
    @JsonView({IFindGroup.class})
    private String mqcsrq;//母亲出生日期
    @JsonView({IFindGroup.class})
    private String xb0000;//儿童性别
    @JsonView({IFindGroup.class})
    private String csrq00;//儿童出生日期
    @JsonView({IFindGroup.class})
    private String cssc00;//出生身长
    @JsonView({IFindGroup.class})
    private String cstz00;//出生体重

    @JsonView({IFindGroup.class})
    private String csyz00;//出生孕周

    @JsonView({IFindGroup.class})
    private String etsfzh;//儿童身份证号

    @JsonView({IFindGroup.class})
    private String sfyjx0;//是否有畸形（1无，2有）

    @JsonView({IFindGroup.class})
    private String xsezz0;//新生儿窒息（1无，2有）

    @JsonView({IFindGroup.class})
    private String mqhbqk;//母亲妊娠期患病情况(1无，2糖尿病，3.妊娠期高血压，4.其他)

    @JsonView({IFindGroup.class})
    private String csqk00;//出生情况（1顺产，2胎头吸引，3产钳，4剖宫，5双多胎，6臀位，7其他）

    @JsonView({IFindGroup.class})
    private String jbscjd;//新生儿疾病筛查-甲低（1通过，2未通过）

    @JsonView({IFindGroup.class})
    private String jbscbb;//新生儿疾病筛查-苯丙酮尿症（1通过，2未通过）

    @JsonView({IFindGroup.class})
    private String jbsc00;//是否疾病筛查(0,已经筛查；1未筛查)

    @JsonView({IFindGroup.class})
    private String zcjg00;//助产机构名称

    @JsonView({IFindGroup.class})
    private String tlsc00;//新生儿听力筛查（1通过，2未通过，3未筛查，4不详,手动设置：a通过，b未通过)

    @JsonView({IFindGroup.class})
    private String jbtemp;//疾病筛查关联字段:其他遗传代谢病(0.无，1.其他遗传代谢病)

    @JsonView({IFindGroup.class})
    private String appfy0;//appf评分 1分钟

    @JsonView({IFindGroup.class})
    private String appfw0;//appf评分 5分钟

    @JsonView({IFindGroup.class})
    private String appf00;//apgar评分 3.不详

    @JsonView({IFindGroup.class})
    private String homeaddress;//家庭住址

    private String sfzh00;//身份证号1（允许两个,用；分隔）
    private String sfzh01;//身份证号2（允许两个,用；分隔）

    public String getXtbhsf() {
        return xtbhsf;
    }

    public void setXtbhsf(String xtbhsf) {
        this.xtbhsf = xtbhsf == null ? null : xtbhsf.trim();
    }

    public String getScxtbh() {
        return scxtbh;
    }

    public void setScxtbh(String scxtbh) {
        this.scxtbh = scxtbh == null ? null : scxtbh.trim();
    }

    public String getSsnnz0() {
        return ssnnz0;
    }

    public void setSsnnz0(String ssnnz0) {
        this.ssnnz0 = ssnnz0 == null ? null : ssnnz0.trim();
    }

    public String getXmxl00() {
        return xmxl00;
    }

    public void setXmxl00(String xmxl00) {
        this.xmxl00 = xmxl00 == null ? null : xmxl00.trim();
    }

    public String getEtsg00() {
        return etsg00;
    }

    public void setEtsg00(String etsg00) {
        this.etsg00 = etsg00;
    }



    public String getEttz00() {
        return ettz00;
    }

    public void setEttz00(String ettz00) {
        this.ettz00 = ettz00;
    }

    public String getSgqk00() {
        return sgqk00;
    }

    public void setSgqk00(String sgqk00) {
        this.sgqk00 = sgqk00 == null ? null : sgqk00.trim();
    }

    public String getTzqk00() {
        return tzqk00;
    }

    public void setTzqk00(String tzqk00) {
        this.tzqk00 = tzqk00 == null ? null : tzqk00.trim();
    }

    public String getTw0000() {
        return tw0000;
    }

    public void setTw0000(String tw0000) {
        this.tw0000 = tw0000 == null ? null : tw0000.trim();
    }

    public String getWyfs00() {
        return wyfs00;
    }

    public void setWyfs00(String wyfs00) {
        this.wyfs00 = wyfs00 == null ? null : wyfs00.trim();
    }

    public String getCnl000() {
        return cnl000;
    }

    public void setCnl000(String cnl000) {
        this.cnl000 = cnl000 == null ? null : cnl000.trim();
    }

    public String getCncs00() {
        return cncs00;
    }

    public void setCncs00(String cncs00) {
        this.cncs00 = cncs00 == null ? null : cncs00.trim();
    }

    public String getOt0000() {
        return ot0000;
    }

    public void setOt0000(String ot0000) {
        this.ot0000 = ot0000 == null ? null : ot0000.trim();
    }

    public String getDb0000() {
        return db0000;
    }

    public void setDb0000(String db0000) {
        this.db0000 = db0000 == null ? null : db0000.trim();
    }

    public String getDbcs00() {
        return dbcs00;
    }

    public void setDbcs00(String dbcs00) {
        this.dbcs00 = dbcs00 == null ? null : dbcs00.trim();
    }

    public String getTwqk00() {
        return twqk00;
    }

    public void setTwqk00(String twqk00) {
        this.twqk00 = twqk00 == null ? null : twqk00.trim();
    }

    public String getMl0000() {
        return ml0000;
    }

    public void setMl0000(String ml0000) {
        this.ml0000 = ml0000 == null ? null : ml0000.trim();
    }

    public String getHxpl00() {
        return hxpl00;
    }

    public void setHxpl00(String hxpl00) {
        this.hxpl00 = hxpl00 == null ? null : hxpl00.trim();
    }

    public String getMsqk00() {
        return msqk00;
    }

    public void setMsqk00(String msqk00) {
        this.msqk00 = msqk00 == null ? null : msqk00.trim();
    }

    public String getHhbw00() {
        return hhbw00;
    }

    public void setHhbw00(String hhbw00) {
        this.hhbw00 = hhbw00 == null ? null : hhbw00.trim();
    }

    public String getQcone0() {
        return qcone0;
    }

    public void setQcone0(String qcone0) {
        this.qcone0 = qcone0 == null ? null : qcone0.trim();
    }

    public String getQctwo0() {
        return qctwo0;
    }

    public void setQctwo0(String qctwo0) {
        this.qctwo0 = qctwo0 == null ? null : qctwo0.trim();
    }

    public String getQcqk00() {
        return qcqk00;
    }

    public void setQcqk00(String qcqk00) {
        this.qcqk00 = qcqk00 == null ? null : qcqk00.trim();
    }

    public String getEyeqk0() {
        return eyeqk0;
    }

    public void setEyeqk0(String eyeqk0) {
        this.eyeqk0 = eyeqk0 == null ? null : eyeqk0.trim();
    }

    public String getEarqk0() {
        return earqk0;
    }

    public void setEarqk0(String earqk0) {
        this.earqk0 = earqk0 == null ? null : earqk0.trim();
    }

    public String getNoseqk() {
        return noseqk;
    }

    public void setNoseqk(String noseqk) {
        this.noseqk = noseqk == null ? null : noseqk.trim();
    }

    public String getKqqk00() {
        return kqqk00;
    }

    public void setKqqk00(String kqqk00) {
        this.kqqk00 = kqqk00 == null ? null : kqqk00.trim();
    }

    public String getXftz00() {
        return xftz00;
    }

    public void setXftz00(String xftz00) {
        this.xftz00 = xftz00 == null ? null : xftz00.trim();
    }

    public String getFbcz00() {
        return fbcz00;
    }

    public void setFbcz00(String fbcz00) {
        this.fbcz00 = fbcz00 == null ? null : fbcz00.trim();
    }

    public String getSzhdqk() {
        return szhdqk;
    }

    public void setSzhdqk(String szhdqk) {
        this.szhdqk = szhdqk == null ? null : szhdqk.trim();
    }

    public String getJbbkqk() {
        return jbbkqk;
    }

    public void setJbbkqk(String jbbkqk) {
        this.jbbkqk = jbbkqk == null ? null : jbbkqk.trim();
    }

    public String getSkinqk() {
        return skinqk;
    }

    public void setSkinqk(String skinqk) {
        this.skinqk = skinqk == null ? null : skinqk.trim();
    }

    public String getGmqk00() {
        return gmqk00;
    }

    public void setGmqk00(String gmqk00) {
        this.gmqk00 = gmqk00 == null ? null : gmqk00.trim();
    }

    public String getWszqqk() {
        return wszqqk;
    }

    public void setWszqqk(String wszqqk) {
        this.wszqqk = wszqqk == null ? null : wszqqk.trim();
    }

    public String getJzqk00() {
        return jzqk00;
    }

    public void setJzqk00(String jzqk00) {
        this.jzqk00 = jzqk00 == null ? null : jzqk00.trim();
    }

    public String getQdqk00() {
        return qdqk00;
    }

    public void setQdqk00(String qdqk00) {
        this.qdqk00 = qdqk00 == null ? null : qdqk00.trim();
    }

    public String getTl0000() {
        return tl0000;
    }

    public void setTl0000(String tl0000) {
        this.tl0000 = tl0000 == null ? null : tl0000.trim();
    }

    public String getGlbzz0() {
        return glbzz0;
    }

    public void setGlbzz0(String glbzz0) {
        this.glbzz0 = glbzz0 == null ? null : glbzz0.trim();
    }

    public String getGlbtz0() {
        return glbtz0;
    }

    public void setGlbtz0(String glbtz0) {
        this.glbtz0 = glbtz0 == null ? null : glbtz0.trim();
    }

    public String getXhdb00() {
        return xhdb00;
    }

    public void setXhdb00(String xhdb00) {
        this.xhdb00 = xhdb00 == null ? null : xhdb00.trim();
    }

    public String getHwhdqk() {
        return hwhdqk;
    }

    public void setHwhdqk(String hwhdqk) {
        this.hwhdqk = hwhdqk == null ? null : hwhdqk.trim();
    }

    public String getWss000() {
        return wss000;
    }

    public void setWss000(String wss000) {
        this.wss000 = wss000 == null ? null : wss000.trim();
    }

    public String getFypgqk() {
        return fypgqk;
    }

    public void setFypgqk(String fypgqk) {
        this.fypgqk = fypgqk == null ? null : fypgqk.trim();
    }

    public String getHbqk00() {
        return hbqk00;
    }

    public void setHbqk00(String hbqk00) {
        this.hbqk00 = hbqk00 == null ? null : hbqk00.trim();
    }

    public String getHbqkfy() {
        return hbqkfy;
    }

    public void setHbqkfy(String hbqkfy) {
        this.hbqkfy = hbqkfy == null ? null : hbqkfy.trim();
    }

    public String getHbqkfx() {
        return hbqkfx;
    }

    public void setHbqkfx(String hbqkfx) {
        this.hbqkfx = hbqkfx == null ? null : hbqkfx.trim();
    }

    public String getHbqkws() {
        return hbqkws;
    }

    public void setHbqkws(String hbqkws) {
        this.hbqkws = hbqkws == null ? null : hbqkws.trim();
    }

    public String getHbqkqt() {
        return hbqkqt;
    }

    public void setHbqkqt(String hbqkqt) {
        this.hbqkqt = hbqkqt == null ? null : hbqkqt.trim();
    }

    public String getQt0000() {
        return qt0000;
    }

    public void setQt0000(String qt0000) {
        this.qt0000 = qt0000 == null ? null : qt0000.trim();
    }

    public String getBtqk00() {
        return btqk00;
    }

    public void setBtqk00(String btqk00) {
        this.btqk00 = btqk00 == null ? null : btqk00.trim();
    }

    public String getTgfypj() {
        return tgfypj;
    }

    public void setTgfypj(String tgfypj) {
        this.tgfypj = tgfypj == null ? null : tgfypj.trim();
    }

    public String getSl0000() {
        return sl0000;
    }

    public void setSl0000(String sl0000) {
        this.sl0000 = sl0000 == null ? null : sl0000.trim();
    }

    public String getZdqk00() {
        return zdqk00;
    }

    public void setZdqk00(String zdqk00) {
        this.zdqk00 = zdqk00 == null ? null : zdqk00.trim();
    }

    public String getZzqk00() {
        return zzqk00;
    }

    public void setZzqk00(String zzqk00) {
        this.zzqk00 = zzqk00 == null ? null : zzqk00.trim();
    }

    public String getZzyy00() {
        return zzyy00;
    }

    public void setZzyy00(String zzyy00) {
        this.zzyy00 = zzyy00 == null ? null : zzyy00.trim();
    }

    public String getZzjgks() {
        return zzjgks;
    }

    public void setZzjgks(String zzjgks) {
        this.zzjgks = zzjgks == null ? null : zzjgks.trim();
    }

    public String getXcsfrq() {
        return xcsfrq;
    }

    public void setXcsfrq(String xcsfrq) {
        this.xcsfrq = xcsfrq;
    }

    public String getXcsfdd() {
        return xcsfdd;
    }

    public void setXcsfdd(String xcsfdd) {
        this.xcsfdd = xcsfdd == null ? null : xcsfdd.trim();
    }

    public String getSfrq00() {
        return sfrq00;
    }

    public void setSfrq00(String sfrq00) {
        this.sfrq00 = sfrq00;
    }

    public String getSfys00() {
        return sfys00;
    }

    public void setSfys00(String sfys00) {
        this.sfys00 = sfys00 == null ? null : sfys00.trim();
    }

    public String getEwfsbj() {
        return ewfsbj;
    }

    public void setEwfsbj(String ewfsbj) {
        this.ewfsbj = ewfsbj == null ? null : ewfsbj.trim();
    }

    public String getSfysid() {
        return sfysid;
    }

    public void setSfysid(String sfysid) {
        this.sfysid = sfysid == null ? null : sfysid.trim();
    }

    public String getYyid00() {
        return yyid00;
    }

    public void setYyid00(String yyid00) {
        this.yyid00 = yyid00 == null ? null : yyid00.trim();
    }

    public String getKqqkse() {
        return kqqkse;
    }

    public void setKqqkse(String kqqkse) {
        this.kqqkse = kqqkse == null ? null : kqqkse.trim();
    }

    public String getSly000() {
        return sly000;
    }

    public void setSly000(String sly000) {
        this.sly000 = sly000 == null ? null : sly000.trim();
    }

    public String getSfjg00() {
        return sfjg00;
    }

    public void setSfjg00(String sfjg00) {
        this.sfjg00 = sfjg00 == null ? null : sfjg00.trim();
    }

    public String getZzks00() {
        return zzks00;
    }

    public void setZzks00(String zzks00) {
        this.zzks00 = zzks00 == null ? null : zzks00.trim();
    }

    public String getZzbjz0() {
        return zzbjz0;
    }

    public void setZzbjz0(String zzbjz0) {
        this.zzbjz0 = zzbjz0 == null ? null : zzbjz0.trim();
    }

    public String getZhbjrq() {
        return zhbjrq;
    }

    public void setZhbjrq(String zhbjrq) {
        this.zhbjrq = zhbjrq;
    }

    public String getBz0000() {
        return bz0000;
    }

    public void setBz0000(String bz0000) {
        this.bz0000 = bz0000 == null ? null : bz0000.trim();
    }

    public String getEtsjss() {
        return etsjss;
    }

    public void setEtsjss(String etsjss) {
        this.etsjss = etsjss == null ? null : etsjss.trim();
    }

    public String getEtsjys() {
        return etsjys;
    }

    public void setEtsjys(String etsjys) {
        this.etsjys = etsjys == null ? null : etsjys.trim();
    }

    public String getEtsjts() {
        return etsjts;
    }

    public void setEtsjts(String etsjts) {
        this.etsjts = etsjts == null ? null : etsjts.trim();
    }

    public String getWiId() {
        return wiId;
    }

    public void setWiId(String wiId) {
        this.wiId = wiId == null ? null : wiId.trim();
    }

    public String getYscys() {
        return yscys;
    }

    public void setYscys(String yscys) {
        this.yscys = yscys == null ? null : yscys.trim();
    }

    public String getCjz000() {
        return cjz000;
    }

    public void setCjz000(String cjz000) {
        this.cjz000 = cjz000 == null ? null : cjz000.trim();
    }

    public String getCjrq00() {
        return cjrq00;
    }

    public void setCjrq00(String cjrq00) {
        this.cjrq00 = cjrq00;
    }

    public String getTzpjxx() {
        return tzpjxx;
    }

    public void setTzpjxx(String tzpjxx) {
        this.tzpjxx = tzpjxx == null ? null : tzpjxx.trim();
    }

    public String getSgpjxx() {
        return sgpjxx;
    }

    public void setSgpjxx(String sgpjxx) {
        this.sgpjxx = sgpjxx == null ? null : sgpjxx.trim();
    }

    public String getTwpj00() {
        return twpj00;
    }

    public void setTwpj00(String twpj00) {
        this.twpj00 = twpj00 == null ? null : twpj00.trim();
    }

    public String getZdjgyy() {
        return zdjgyy;
    }

    public void setZdjgyy(String zdjgyy) {
        this.zdjgyy = zdjgyy == null ? null : zdjgyy.trim();
    }

    public String getZdjgbt() {
        return zdjgbt;
    }

    public void setZdjgbt(String zdjgbt) {
        this.zdjgbt = zdjgbt == null ? null : zdjgbt.trim();
    }

    public String getZdjgqt() {
        return zdjgqt;
    }

    public void setZdjgqt(String zdjgqt) {
        this.zdjgqt = zdjgqt == null ? null : zdjgqt.trim();
    }

    public String getZdjggw() {
        return zdjggw;
    }

    public void setZdjggw(String zdjggw) {
        this.zdjggw = zdjggw == null ? null : zdjggw.trim();
    }

    public String getZhpj00() {
        return zhpj00;
    }

    public void setZhpj00(String zhpj00) {
        this.zhpj00 = zhpj00 == null ? null : zhpj00.trim();
    }

    public String getZhpjna() {
        return zhpjna;
    }

    public void setZhpjna(String zhpjna) {
        this.zhpjna = zhpjna == null ? null : zhpjna.trim();
    }

    public String getYypj00() {
        return yypj00;
    }

    public void setYypj00(String yypj00) {
        this.yypj00 = yypj00 == null ? null : yypj00.trim();
    }

    public String getYypjna() {
        return yypjna;
    }

    public void setYypjna(String yypjna) {
        this.yypjna = yypjna == null ? null : yypjna.trim();
    }

    public String getDelmak() {
        return delmak;
    }

    public void setDelmak(String delmak) {
        this.delmak = delmak == null ? null : delmak.trim();
    }

    public String getIssc00() {
        return issc00;
    }

    public void setIssc00(String issc00) {
        this.issc00 = issc00 == null ? null : issc00.trim();
    }

    public String getSctzpj() {
        return sctzpj;
    }

    public void setSctzpj(String sctzpj) {
        this.sctzpj = sctzpj == null ? null : sctzpj.trim();
    }

    public String getYygs00() {
        return yygs00;
    }

    public void setYygs00(String yygs00) {
        this.yygs00 = yygs00 == null ? null : yygs00.trim();
    }

    public String getGwidyy() {
        return gwidyy;
    }

    public void setGwidyy(String gwidyy) {
        this.gwidyy = gwidyy == null ? null : gwidyy.trim();
    }

    public String getGwidpx() {
        return gwidpx;
    }

    public void setGwidpx(String gwidpx) {
        this.gwidpx = gwidpx == null ? null : gwidpx.trim();
    }

    public String getGwidgl() {
        return gwidgl;
    }

    public void setGwidgl(String gwidgl) {
        this.gwidgl = gwidgl == null ? null : gwidgl.trim();
    }

    public String getGwiddj() {
        return gwiddj;
    }

    public void setGwiddj(String gwiddj) {
        this.gwiddj = gwiddj == null ? null : gwiddj.trim();
    }

    public String getGwpg00() {
        return gwpg00;
    }

    public void setGwpg00(String gwpg00) {
        this.gwpg00 = gwpg00 == null ? null : gwpg00.trim();
    }

    public String getCzwt00() {
        return czwt00;
    }

    public void setCzwt00(String czwt00) {
        this.czwt00 = czwt00 == null ? null : czwt00.trim();
    }

    public String getGwzd00() {
        return gwzd00;
    }

    public void setGwzd00(String gwzd00) {
        this.gwzd00 = gwzd00 == null ? null : gwzd00.trim();
    }

    public String getGwzl00() {
        return gwzl00;
    }

    public void setGwzl00(String gwzl00) {
        this.gwzl00 = gwzl00 == null ? null : gwzl00.trim();
    }

    public String getGwvdzl() {
        return gwvdzl;
    }

    public void setGwvdzl(String gwvdzl) {
        this.gwvdzl = gwvdzl == null ? null : gwvdzl.trim();
    }

    public String getCzwtpx() {
        return czwtpx;
    }

    public void setCzwtpx(String czwtpx) {
        this.czwtpx = czwtpx == null ? null : czwtpx.trim();
    }

    public String getGwzdpx() {
        return gwzdpx;
    }

    public void setGwzdpx(String gwzdpx) {
        this.gwzdpx = gwzdpx == null ? null : gwzdpx.trim();
    }

    public String getCzwtgl() {
        return czwtgl;
    }

    public void setCzwtgl(String czwtgl) {
        this.czwtgl = czwtgl == null ? null : czwtgl.trim();
    }

    public String getGwzdgl() {
        return gwzdgl;
    }

    public void setGwzdgl(String gwzdgl) {
        this.gwzdgl = gwzdgl == null ? null : gwzdgl.trim();
    }

    public String getTwbzc0() {
        return twbzc0;
    }

    public void setTwbzc0(String twbzc0) {
        this.twbzc0 = twbzc0 == null ? null : twbzc0.trim();
    }

    public String getYeyid0() {
        return yeyid0;
    }

    public void setYeyid0(String yeyid0) {
        this.yeyid0 = yeyid0 == null ? null : yeyid0.trim();
    }

    public String getBjid00() {
        return bjid00;
    }

    public void setBjid00(String bjid00) {
        this.bjid00 = bjid00 == null ? null : bjid00.trim();
    }

    public String getZyjkzd() {
        return zyjkzd;
    }

    public void setZyjkzd(String zyjkzd) {
        this.zyjkzd = zyjkzd == null ? null : zyjkzd.trim();
    }

    public String getYsty00() {
        return ysty00;
    }

    public void setYsty00(String ysty00) {
        this.ysty00 = ysty00 == null ? null : ysty00.trim();
    }

    public String getQjts00() {
        return qjts00;
    }

    public void setQjts00(String qjts00) {
        this.qjts00 = qjts00 == null ? null : qjts00.trim();
    }

    public String getXwarff() {
        return xwarff;
    }

    public void setXwarff(String xwarff) {
        this.xwarff = xwarff == null ? null : xwarff.trim();
    }

    public String getZwqk00() {
        return zwqk00;
    }

    public void setZwqk00(String zwqk00) {
        this.zwqk00 = zwqk00 == null ? null : zwqk00.trim();
    }

    public String getMyd000() {
        return myd000;
    }

    public void setMyd000(String myd000) {
        this.myd000 = myd000 == null ? null : myd000.trim();
    }

    public String getXl0000() {
        return xl0000;
    }

    public void setXl0000(String xl0000) {
        this.xl0000 = xl0000 == null ? null : xl0000.trim();
    }

    public String getZzdpx0() {
        return zzdpx0;
    }

    public void setZzdpx0(String zzdpx0) {
        this.zzdpx0 = zzdpx0 == null ? null : zzdpx0.trim();
    }

    public String getGwidza() {
        return gwidza;
    }

    public void setGwidza(String gwidza) {
        this.gwidza = gwidza == null ? null : gwidza.trim();
    }

    public String getPgjg00() {
        return pgjg00;
    }

    public void setPgjg00(String pgjg00) {
        this.pgjg00 = pgjg00 == null ? null : pgjg00.trim();
    }

    public String getPgjgcl() {
        return pgjgcl;
    }

    public void setPgjgcl(String pgjgcl) {
        this.pgjgcl = pgjgcl == null ? null : pgjgcl.trim();
    }

    public String getPgffxl() {
        return pgffxl;
    }

    public void setPgffxl(String pgffxl) {
        this.pgffxl = pgffxl == null ? null : pgffxl.trim();
    }

    public String getPgjgxl() {
        return pgjgxl;
    }

    public void setPgjgxl(String pgjgxl) {
        this.pgjgxl = pgjgxl == null ? null : pgjgxl.trim();
    }

    public String getGwzdxl() {
        return gwzdxl;
    }

    public void setGwzdxl(String gwzdxl) {
        this.gwzdxl = gwzdxl == null ? null : gwzdxl.trim();
    }

    public String getGwzdcl() {
        return gwzdcl;
    }

    public void setGwzdcl(String gwzdcl) {
        this.gwzdcl = gwzdcl == null ? null : gwzdcl.trim();
    }

    public String getGwidxl() {
        return gwidxl;
    }

    public void setGwidxl(String gwidxl) {
        this.gwidxl = gwidxl == null ? null : gwidxl.trim();
    }

    public String getSfwc00() {
        return sfwc00;
    }

    public void setSfwc00(String sfwc00) {
        this.sfwc00 = sfwc00 == null ? null : sfwc00.trim();
    }

    public String getPgffza() {
        return pgffza;
    }

    public void setPgffza(String pgffza) {
        this.pgffza = pgffza == null ? null : pgffza.trim();
    }

    public String getPgjgza() {
        return pgjgza;
    }

    public void setPgjgza(String pgjgza) {
        this.pgjgza = pgjgza == null ? null : pgjgza.trim();
    }

    public String getZd00za() {
        return zd00za;
    }

    public void setZd00za(String zd00za) {
        this.zd00za = zd00za == null ? null : zd00za.trim();
    }

    public String getCl00za() {
        return cl00za;
    }

    public void setCl00za(String cl00za) {
        this.cl00za = cl00za == null ? null : cl00za.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getTsnan0() {
        return tsnan0;
    }

    public void setTsnan0(String tsnan0) {
        this.tsnan0 = tsnan0 == null ? null : tsnan0.trim();
    }

    public String getTsnv00() {
        return tsnv00;
    }

    public void setTsnv00(String tsnv00) {
        this.tsnv00 = tsnv00 == null ? null : tsnv00.trim();
    }

    public String getTs0000() {
        return ts0000;
    }

    public void setTs0000(String ts0000) {
        this.ts0000 = ts0000 == null ? null : ts0000.trim();
    }

    public String getScbz00() {
        return scbz00;
    }

    public void setScbz00(String scbz00) {
        this.scbz00 = scbz00 == null ? null : scbz00.trim();
    }

    public String getScxtbhtemp() {
        return scxtbhtemp;
    }

    public void setScxtbhtemp(String scxtbhtemp) {
        this.scxtbhtemp = scxtbhtemp == null ? null : scxtbhtemp.trim();
    }

    public String getQdpx00() {
        return qdpx00;
    }

    public void setQdpx00(String qdpx00) {
        this.qdpx00 = qdpx00 == null ? null : qdpx00.trim();
    }

	public String getFqsfzh() {
		return fqsfzh;
	}

	public void setFqsfzh(String fqsfzh) {
		this.fqsfzh = fqsfzh;
	}

	public String getXm0000() {
		return xm0000;
	}

	public void setXm0000(String xm0000) {
		this.xm0000 = xm0000;
	}

	public String getFqxm00() {
		return fqxm00;
	}

	public void setFqxm00(String fqxm00) {
		this.fqxm00 = fqxm00;
	}

	public String getMqsfzh() {
		return mqsfzh;
	}

	public void setMqsfzh(String mqsfzh) {
		this.mqsfzh = mqsfzh;
	}

	public String getMqxm00() {
		return mqxm00;
	}

	public void setMqxm00(String mqxm00) {
		this.mqxm00 = mqxm00;
	}

	public String getSfdq00() {
		return sfdq00;
	}

	public void setSfdq00(String sfdq00) {
		this.sfdq00 = sfdq00;
	}

	public String getFqzy00() {
		return fqzy00;
	}

	public void setFqzy00(String fqzy00) {
		this.fqzy00 = fqzy00;
	}

	public String getFqcsrq() {
		return fqcsrq;
	}

	public void setFqcsrq(String fqcsrq) {
		this.fqcsrq = fqcsrq;
	}

	public String getMqzy00() {
		return mqzy00;
	}

	public void setMqzy00(String mqzy00) {
		this.mqzy00 = mqzy00;
	}

	public String getMqcsrq() {
		return mqcsrq;
	}

	public void setMqcsrq(String mqcsrq) {
		this.mqcsrq = mqcsrq;
	}

	public String getXb0000() {
		return xb0000;
	}

	public void setXb0000(String xb0000) {
		this.xb0000 = xb0000;
	}

	public String getCsrq00() {
		return csrq00;
	}

	public void setCsrq00(String csrq00) {
		this.csrq00 = csrq00;
	}

	public String getCssc00() {
		return cssc00;
	}

	public void setCssc00(String cssc00) {
		this.cssc00 = cssc00;
	}

	public String getCstz00() {
		return cstz00;
	}

	public void setCstz00(String cstz00) {
		this.cstz00 = cstz00;
	}


	public String getSfzh00() {
		return sfzh00;
	}

	public void setSfzh00(String sfzh00) {
		this.sfzh00 = sfzh00;
	}

	public String getSfzh01() {
		return sfzh01;
	}

	public void setSfzh01(String sfzh01) {
		this.sfzh01 = sfzh01;
	}

	public String getCsyz00() {
		return csyz00;
	}

	public void setCsyz00(String csyz00) {
		this.csyz00 = csyz00;
	}

	public String getEtsfzh() {
		return etsfzh;
	}

	public void setEtsfzh(String etsfzh) {
		this.etsfzh = etsfzh;
	}

	public String getSfyjx0() {
		return sfyjx0;
	}

	public void setSfyjx0(String sfyjx0) {
		this.sfyjx0 = sfyjx0;
	}

	public String getXsezz0() {
		return xsezz0;
	}

	public void setXsezz0(String xsezz0) {
		this.xsezz0 = xsezz0;
	}

	public String getMqhbqk() {
		return mqhbqk;
	}

	public void setMqhbqk(String mqhbqk) {
		this.mqhbqk = mqhbqk;
	}

	public String getCsqk00() {
		return csqk00;
	}

	public void setCsqk00(String csqk00) {
		this.csqk00 = csqk00;
	}

	public String getJbscjd() {
		return jbscjd;
	}

	public void setJbscjd(String jbscjd) {
		this.jbscjd = jbscjd;
	}

	public String getJbscbb() {
		return jbscbb;
	}

	public void setJbscbb(String jbscbb) {
		this.jbscbb = jbscbb;
	}

	public String getJbsc00() {
		return jbsc00;
	}

	public void setJbsc00(String jbsc00) {
		this.jbsc00 = jbsc00;
	}

	public String getZcjg00() {
		return zcjg00;
	}

	public void setZcjg00(String zcjg00) {
		this.zcjg00 = zcjg00;
	}

	public String getTlsc00() {
		return tlsc00;
	}

	public void setTlsc00(String tlsc00) {
		this.tlsc00 = tlsc00;
	}

	public String getJbtemp() {
		return jbtemp;
	}

	public void setJbtemp(String jbtemp) {
		this.jbtemp = jbtemp;
	}

	public String getAppfy0() {
		return appfy0;
	}

	public void setAppfy0(String appfy0) {
		this.appfy0 = appfy0;
	}

	public String getAppfw0() {
		return appfw0;
	}

	public void setAppfw0(String appfw0) {
		this.appfw0 = appfw0;
	}

	public String getAppf00() {
		return appf00;
	}

	public void setAppf00(String appf00) {
		this.appf00 = appf00;
	}

	public String getHomeaddress() {
		return homeaddress;
	}

	public void setHomeaddress(String homeaddress) {
		this.homeaddress = homeaddress;
	}



	public static interface IFindGroup{}
}
