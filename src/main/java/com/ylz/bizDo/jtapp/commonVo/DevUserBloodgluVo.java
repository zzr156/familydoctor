package com.ylz.bizDo.jtapp.commonVo;



/**
 * 血糖设备传输过来的数据
 */
public class DevUserBloodgluVo  {

    private String bgState;//时段
    private String testTime;//测量时间
    private String bloodGlu;//血糖值
    private String tempTur;//温度值
    private String devid;//设备id
    private String codeNum;//血糖仪code码
    private String sourceType;//数据来源(1app 2智能设备 3随访 4门诊 5poss 6一体机)
    private String name;//姓名
    private String idno;//身份证

    public String getBgState() {
        return bgState;
    }

    public void setBgState(String bgState) {
        this.bgState = bgState;
    }

    public String getTestTime() {
        return testTime;
    }

    public void setTestTime(String testTime) {
        this.testTime = testTime;
    }

    public String getBloodGlu() {
        return bloodGlu;
    }

    public void setBloodGlu(String bloodGlu) {
        this.bloodGlu = bloodGlu;
    }

    public String getTempTur() {
        return tempTur;
    }

    public void setTempTur(String tempTur) {
        this.tempTur = tempTur;
    }

    public String getDevid() {
        return devid;
    }

    public void setDevid(String devid) {
        this.devid = devid;
    }

    public String getCodeNum() {
        return codeNum;
    }

    public void setCodeNum(String codeNum) {
        this.codeNum = codeNum;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
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
}
