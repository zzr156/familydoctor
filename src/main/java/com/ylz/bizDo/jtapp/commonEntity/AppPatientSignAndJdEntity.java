package com.ylz.bizDo.jtapp.commonEntity;

import com.ylz.bizDo.app.po.AppSignForm;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

/**
 * Created by zzl on 2018/7/17.
 */
public class AppPatientSignAndJdEntity {
    //用户主键
    private String id;
    //性别
    private String sex;
    //姓名
    private String name;
    //身份证
    private String idno;
    //出生日期
    private Date birthDay;
    //签约单id 有值代表有签约
    private String signId;
    //居民档案号 有值代表已建档
    private String jmdah;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSignId() {
        return signId;
    }

    public void setSignId(String signId) throws Exception {
        if(StringUtils.isNotBlank(this.getId())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            AppSignForm form = dao.getAppSignformDao().findSignFormByUser(this.getId());
            if(form!=null){
                signId = form.getId();
            }
        }
        this.signId = signId;
    }

    public String getJmdah() {
        return jmdah;
    }

    public void setJmdah(String jmdah) {
        this.jmdah = jmdah;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }
}
