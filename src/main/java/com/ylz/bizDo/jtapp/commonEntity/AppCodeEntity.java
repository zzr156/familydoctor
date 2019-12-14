package com.ylz.bizDo.jtapp.commonEntity;

import org.apache.commons.lang.StringUtils;

/**
 * Created by zzl on 2018/1/18.
 */
public class AppCodeEntity {
    private String id;//主键
    private String codeValue;//值
    private String codeTitle;//名称,标题
    private String codeGroup;//组名
    private String codeRemark;//备注
    private Integer codeSort;//排序
    private String codeState;//状态
    private String codeSex;//性别

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCodeValue() {
        return codeValue;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }

    public String getCodeTitle() {
        return codeTitle;
    }

    public void setCodeTitle(String codeTitle) {
        if(StringUtils.isNotBlank(codeTitle)){
            String[] titles = codeTitle.split(";");
            this.codeTitle = titles[0];
        }
    }

    public String getCodeGroup() {
        return codeGroup;
    }

    public void setCodeGroup(String codeGroup) {
        this.codeGroup = codeGroup;
    }

    public String getCodeRemark() {
        return codeRemark;
    }

    public void setCodeRemark(String codeRemark) {
        this.codeRemark = codeRemark;
    }

    public Integer getCodeSort() {
        return codeSort;
    }

    public void setCodeSort(Integer codeSort) {
        this.codeSort = codeSort;
    }

    public String getCodeState() {
        return codeState;
    }

    public void setCodeState(String codeState) {
        this.codeState = codeState;
    }

    public String getCodeSex() {
        return codeSex;
    }

    public void setCodeSex(String codeSex) {
        if(StringUtils.isNotBlank(codeSex)){
            String[] titles = codeSex.split(";");
            if(titles.length>=2){
                this.codeSex = titles[1];
            }
        }

    }
}
