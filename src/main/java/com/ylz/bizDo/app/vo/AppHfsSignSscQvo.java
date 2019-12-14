package com.ylz.bizDo.app.vo;

import com.ylz.packcommon.common.CommConditionVo;

/**
 * Created by lenovo on 2017/11/7.
 */
public class AppHfsSignSscQvo extends CommConditionVo {


    private String id;
    private String ptsccno ;//社保卡
    private String ptnumber;//个人编号
    private String[] ptnature;//人口性质
    private String ptname;//名称
    private String ptgender ;//性别
    private String ptbirth;//时间
    private String ptuserph;//关系
    private String ptusername;//户主名称
    private String ptidno;//身份证
    private String ptregion;//地址
    private String pttype;//参合缴费
    private String ptlx;//参保类型
    private String ptnation;//民族
    private String pttelephone;
    private String ptorg;//机构id
    private String ptnl;//年纪
    private String ptstate;//签约状态
    private String ptteamname;//团队名称
    private String ptfwlx;//服务类型
    private String ptteamid;//团队id
    private String ptjtbh;//家庭编号
    private String signteamname;
    private String signwebstate;

    private String[] ptfamily;

    public String getSignwebstate() {
        return signwebstate;
    }

    public void setSignwebstate(String signwebstate) {
        this.signwebstate = signwebstate;
    }

    public String[] getPtfamily() {
        return ptfamily;
    }

    public void setPtfamily(String[] ptfamily) {
        this.ptfamily = ptfamily;
    }

    public String getSignteamname() {
        return signteamname;
    }

    public void setSignteamname(String signteamname) {
        this.signteamname = signteamname;
    }

    public String getPtjtbh() {
        return ptjtbh;
    }

    public void setPtjtbh(String ptjtbh) {
        this.ptjtbh = ptjtbh;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPtsccno() {
        return ptsccno;
    }

    public void setPtsccno(String ptsccno) {
        this.ptsccno = ptsccno;
    }

    public String getPtnumber() {
        return ptnumber;
    }

    public void setPtnumber(String ptnumber) {
        this.ptnumber = ptnumber;
    }

    public String[] getPtnature() {
        return ptnature;
    }

    public void setPtnature(String[] ptnature) {
        this.ptnature = ptnature;
    }

    public String getPtname() {
        return ptname;
    }

    public void setPtname(String ptname) {
        this.ptname = ptname;
    }

    public String getPtgender() {
        return ptgender;
    }

    public void setPtgender(String ptgender) {
        this.ptgender = ptgender;
    }

    public String getPtbirth() {
        return ptbirth;
    }

    public void setPtbirth(String ptbirth) {
        this.ptbirth = ptbirth;
    }

    public String getPtuserph() {
        return ptuserph;
    }

    public void setPtuserph(String ptuserph) {
        this.ptuserph = ptuserph;
    }

    public String getPtusername() {
        return ptusername;
    }

    public void setPtusername(String ptusername) {
        this.ptusername = ptusername;
    }

    public String getPtidno() {
        return ptidno;
    }

    public void setPtidno(String ptidno) {
        this.ptidno = ptidno;
    }

    public String getPtregion() {
        return ptregion;
    }

    public void setPtregion(String ptregion) {
        this.ptregion = ptregion;
    }

    public String getPttype() {
        return pttype;
    }

    public void setPttype(String pttype) {
        this.pttype = pttype;
    }

    public String getPtlx() {
        return ptlx;
    }

    public void setPtlx(String ptlx) {
        this.ptlx = ptlx;
    }

    public String getPtnation() {
        return ptnation;
    }

    public void setPtnation(String ptnation) {
        this.ptnation = ptnation;
    }

    public String getPttelephone() {
        return pttelephone;
    }

    public void setPttelephone(String pttelephone) {
        this.pttelephone = pttelephone;
    }

    public String getPtorg() {
        return ptorg;
    }

    public void setPtorg(String ptorg) {
        this.ptorg = ptorg;
    }

    public String getPtnl() {
        return ptnl;
    }

    public void setPtnl(String ptnl) {
        this.ptnl = ptnl;
    }

    public String getPtstate() {
        return ptstate;
    }

    public void setPtstate(String ptstate) {
        this.ptstate = ptstate;
    }

    public String getPtteamname() {
        return ptteamname;
    }

    public void setPtteamname(String ptteamname) {
        this.ptteamname = ptteamname;
    }

    public String getPtfwlx() {
        return ptfwlx;
    }

    public void setPtfwlx(String ptfwlx) {
        this.ptfwlx = ptfwlx;
    }

    public String getPtteamid() {
        return ptteamid;
    }

    public void setPtteamid(String ptteamid) {
        this.ptteamid = ptteamid;
    }
}
