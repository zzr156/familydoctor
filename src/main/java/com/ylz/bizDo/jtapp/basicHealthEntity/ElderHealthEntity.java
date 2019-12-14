package com.ylz.bizDo.jtapp.basicHealthEntity;

/**
 * 老年人档案
 * Created by lintingjie on 2017/7/22.
 */
public class ElderHealthEntity {

    private String eld_id;//老年人保健ID
    private String df_id;//居民档案ID
    private String jjsrqk;//经济收入情况
    private String bxqk;//报销情况
    private String yjs;//月经史
    private String sys;//生育史
    private String jkqk;//健康情况
    private String wcqk;//卧床情况
    private String cjqk;//残疾情况
    private String mxbqk;//慢性病情况
    private String oxygentime;//吸氧时间(h)(患者平均每日的吸氧时间，计量单位为h)
    private String fbedreason;//家庭病床建立原因
    private String fbedcreatedata;//家庭病床建床日期
    private String fbedcanceldata;//家庭病床撤床日期
    private String coalslong;//家中煤火取暖时间(年)
    private String coalsmark;//家中煤火取暖标志
    private String fsmoke;//家庭成员吸烟标志
    private String protectmark;//防护措施标志
    private String hazardworkmark;//职业暴露标志
    private String nodrinkage;//戒酒年龄(岁)
    private String nodrinkmark;//戒酒标志
    private String drunkmark;//醉酒标志
    private String bdrinkage;//开始饮酒年龄(岁)
    private String drinktype;//饮酒种类代码
    private String drinkfrequency ;//饮酒频率代码
    private String nosmokeage  ;//戒烟年龄(岁）
    private String bsmokeage  ;//开始吸烟年龄(岁)
    private String weeksport   ;//周运动次数
    private String holdonsport   ;//坚持运动时长(年)
    private String daysmoke   ;//日吸烟量（支）
    private String hazardworktime ;//从事有危害因素职业时长(年)
    private String hazardwork  ;//有危害因素的具体职业
    private String hazardworktype  ;//职业暴露危险因素种类
    private String hazardworkname  ;//职业暴露危险因素名称
    private String cbehavior  ;//1.良好 2.一般 3.差
    private String psychological;//心理状态代码
    private String smokestatus ;//吸烟状况代码1.从不吸烟 2.过去吸，已戒烟 3.吸烟
    private String dietcode ;//饮食习惯代码
    private String sporttime  ;//运动时间(min)
    private String sportfrequency;//运动频率类别代码
    private String sportdesc;//运动方式说明
    private String daydrink;//日饮酒量(ml)
    private String doctor;//责任医师
    private String yyno00;//院内编号

    public String getEld_id() {
        return eld_id;
    }

    public void setEld_id(String eld_id) {
        this.eld_id = eld_id;
    }

    public String getDf_id() {
        return df_id;
    }

    public void setDf_id(String df_id) {
        this.df_id = df_id;
    }

    public String getJjsrqk() {
        return jjsrqk;
    }

    public void setJjsrqk(String jjsrqk) {
        this.jjsrqk = jjsrqk;
    }

    public String getBxqk() {
        return bxqk;
    }

    public void setBxqk(String bxqk) {
        this.bxqk = bxqk;
    }

    public String getYjs() {
        return yjs;
    }

    public void setYjs(String yjs) {
        this.yjs = yjs;
    }

    public String getSys() {
        return sys;
    }

    public void setSys(String sys) {
        this.sys = sys;
    }

    public String getJkqk() {
        return jkqk;
    }

    public void setJkqk(String jkqk) {
        this.jkqk = jkqk;
    }

    public String getWcqk() {
        return wcqk;
    }

    public void setWcqk(String wcqk) {
        this.wcqk = wcqk;
    }

    public String getCjqk() {
        return cjqk;
    }

    public void setCjqk(String cjqk) {
        this.cjqk = cjqk;
    }

    public String getMxbqk() {
        return mxbqk;
    }

    public void setMxbqk(String mxbqk) {
        this.mxbqk = mxbqk;
    }

    public String getOxygentime() {
        return oxygentime;
    }

    public void setOxygentime(String oxygentime) {
        this.oxygentime = oxygentime;
    }

    public String getFbedreason() {
        return fbedreason;
    }

    public void setFbedreason(String fbedreason) {
        this.fbedreason = fbedreason;
    }

    public String getFbedcreatedata() {
        return fbedcreatedata;
    }

    public void setFbedcreatedata(String fbedcreatedata) {
        this.fbedcreatedata = fbedcreatedata;
    }

    public String getFbedcanceldata() {
        return fbedcanceldata;
    }

    public void setFbedcanceldata(String fbedcanceldata) {
        this.fbedcanceldata = fbedcanceldata;
    }

    public String getCoalslong() {
        return coalslong;
    }

    public void setCoalslong(String coalslong) {
        this.coalslong = coalslong;
    }

    public String getCoalsmark() {
        return coalsmark;
    }

    public void setCoalsmark(String coalsmark) {
        this.coalsmark = coalsmark;
    }

    public String getFsmoke() {
        return fsmoke;
    }

    public void setFsmoke(String fsmoke) {
        this.fsmoke = fsmoke;
    }

    public String getProtectmark() {
        return protectmark;
    }

    public void setProtectmark(String protectmark) {
        this.protectmark = protectmark;
    }

    public String getHazardworkmark() {
        return hazardworkmark;
    }

    public void setHazardworkmark(String hazardworkmark) {
        this.hazardworkmark = hazardworkmark;
    }

    public String getNodrinkage() {
        return nodrinkage;
    }

    public void setNodrinkage(String nodrinkage) {
        this.nodrinkage = nodrinkage;
    }

    public String getNodrinkmark() {
        return nodrinkmark;
    }

    public void setNodrinkmark(String nodrinkmark) {
        this.nodrinkmark = nodrinkmark;
    }

    public String getDrunkmark() {
        return drunkmark;
    }

    public void setDrunkmark(String drunkmark) {
        this.drunkmark = drunkmark;
    }

    public String getBdrinkage() {
        return bdrinkage;
    }

    public void setBdrinkage(String bdrinkage) {
        this.bdrinkage = bdrinkage;
    }

    public String getDrinktype() {
        return drinktype;
    }

    public void setDrinktype(String drinktype) {
        this.drinktype = drinktype;
    }

    public String getDrinkfrequency() {
        return drinkfrequency;
    }

    public void setDrinkfrequency(String drinkfrequency) {
        this.drinkfrequency = drinkfrequency;
    }

    public String getNosmokeage() {
        return nosmokeage;
    }

    public void setNosmokeage(String nosmokeage) {
        this.nosmokeage = nosmokeage;
    }

    public String getBsmokeage() {
        return bsmokeage;
    }

    public void setBsmokeage(String bsmokeage) {
        this.bsmokeage = bsmokeage;
    }

    public String getWeeksport() {
        return weeksport;
    }

    public void setWeeksport(String weeksport) {
        this.weeksport = weeksport;
    }

    public String getHoldonsport() {
        return holdonsport;
    }

    public void setHoldonsport(String holdonsport) {
        this.holdonsport = holdonsport;
    }

    public String getDaysmoke() {
        return daysmoke;
    }

    public void setDaysmoke(String daysmoke) {
        this.daysmoke = daysmoke;
    }

    public String getHazardworktime() {
        return hazardworktime;
    }

    public void setHazardworktime(String hazardworktime) {
        this.hazardworktime = hazardworktime;
    }

    public String getHazardwork() {
        return hazardwork;
    }

    public void setHazardwork(String hazardwork) {
        this.hazardwork = hazardwork;
    }

    public String getHazardworktype() {
        return hazardworktype;
    }

    public void setHazardworktype(String hazardworktype) {
        this.hazardworktype = hazardworktype;
    }

    public String getHazardworkname() {
        return hazardworkname;
    }

    public void setHazardworkname(String hazardworkname) {
        this.hazardworkname = hazardworkname;
    }

    public String getCbehavior() {
        return cbehavior;
    }

    public void setCbehavior(String cbehavior) {
        this.cbehavior = cbehavior;
    }

    public String getPsychological() {
        return psychological;
    }

    public void setPsychological(String psychological) {
        this.psychological = psychological;
    }

    public String getSmokestatus() {
        return smokestatus;
    }

    public void setSmokestatus(String smokestatus) {
        this.smokestatus = smokestatus;
    }

    public String getDietcode() {
        return dietcode;
    }

    public void setDietcode(String dietcode) {
        this.dietcode = dietcode;
    }

    public String getSporttime() {
        return sporttime;
    }

    public void setSporttime(String sporttime) {
        this.sporttime = sporttime;
    }

    public String getSportfrequency() {
        return sportfrequency;
    }

    public void setSportfrequency(String sportfrequency) {
        this.sportfrequency = sportfrequency;
    }

    public String getSportdesc() {
        return sportdesc;
    }

    public void setSportdesc(String sportdesc) {
        this.sportdesc = sportdesc;
    }

    public String getDaydrink() {
        return daydrink;
    }

    public void setDaydrink(String daydrink) {
        this.daydrink = daydrink;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getYyno00() {
        return yyno00;
    }

    public void setYyno00(String yyno00) {
        this.yyno00 = yyno00;
    }
}
