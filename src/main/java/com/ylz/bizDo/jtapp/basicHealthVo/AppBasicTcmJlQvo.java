package com.ylz.bizDo.jtapp.basicHealthVo;

/**
 * Created by zzl on 2018/8/13.
 */
public class AppBasicTcmJlQvo {
    private String tcmCode;//编号
    private String tcmQuestionValue;//题号
    private String tcmChooseNum;//选择（A,B,C,D,E)
    private String tcmScode;//得分
    private String tcmType;//类型（1患者自测 2医生检测 基卫数据默认2）

    public String getTcmCode() {
        return tcmCode;
    }

    public void setTcmCode(String tcmCode) {
        this.tcmCode = tcmCode;
    }

    public String getTcmQuestionValue() {
        return tcmQuestionValue;
    }

    public void setTcmQuestionValue(String tcmQuestionValue) {
        this.tcmQuestionValue = tcmQuestionValue;
    }

    public String getTcmChooseNum() {
        return tcmChooseNum;
    }

    public void setTcmChooseNum(String tcmChooseNum) {
        this.tcmChooseNum = tcmChooseNum;
    }

    public String getTcmScode() {
        return tcmScode;
    }

    public void setTcmScode(String tcmScode) {
        this.tcmScode = tcmScode;
    }

    public String getTcmType() {
        return tcmType;
    }

    public void setTcmType(String tcmType) {
        this.tcmType = tcmType;
    }
}
