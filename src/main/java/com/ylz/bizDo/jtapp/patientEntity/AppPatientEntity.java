package com.ylz.bizDo.jtapp.patientEntity;

import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang3.StringUtils;

/**
 * 随访居民分组返回列表
 * Created by zzl on 2017/6/28.
 */
public class AppPatientEntity {
    private String id;//患者id
    private String name;//患者姓名
    private String imageUrl;//头像
    private String sex;//性别

    private String patientIdNo;
    private String patientJmda;

    public String getPatientIdNo() {
        return patientIdNo;
    }

    public void setPatientIdNo(String patientIdNo) {
        this.patientIdNo = patientIdNo;
    }

    public String getPatientJmda() {
        return patientJmda;
    }

    public void setPatientJmda(String patientJmda) {
        this.patientJmda = patientJmda;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) throws Exception {
        if(StringUtils.isNotBlank(sex)){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CODESEX, sex);
            if(value!=null) {
                sex=value.getCodeTitle();
            }
        }
        this.sex = sex;
    }
}
