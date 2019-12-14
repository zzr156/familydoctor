package com.ylz.bizDo.jtapp.patientEntity;

import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppHospDept;
import com.ylz.bizDo.app.po.AppServeSetting;
import com.ylz.bizDo.jtapp.commonEntity.AppDrEvaluationDrEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppDrEvaluationViewEntity;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by zzl on 2017/8/3.
 */
public class AppSMSignEntity  {
    private String drId;//医生id
    private String drName;//医生姓名
    private String drCode;//医生编号
    private String hospId;//机构id
    private String hospName;//机构名称
    private String fwType;
    private String fwbName;
    private String drTel;//电话
    private String zypj;//专业能力评价
    private String fwpj;//服务态度评价
    private List<AppDrEvaluationViewEntity> pjList;//评价集合
    private String drSex;//



    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getDrCode() {
        return drCode;
    }

    public void setDrCode(String drCode) {
        if(StringUtils.isNotBlank(this.getDrId())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            AppDrUser drUser = (AppDrUser)dao.getServiceDo().find(AppDrUser.class,this.getDrId());
            if(drUser!=null){
                drCode = drUser.getDrCode();
            }
        }
        this.drCode = drCode;
    }

    public String getHospId() {
        return hospId;
    }

    public void setHospId(String hospId) {
        this.hospId = hospId;
    }

    public String getHospName() {
        return hospName;
    }

    public void setHospName(String hospName) {
        if(StringUtils.isNotBlank(this.getHospId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            AppHospDept dept = (AppHospDept)dao.getServiceDo().find(AppHospDept.class,this.getHospId());
            if(dept!=null){
                hospName = dept.getHospName();
            }
        }
        this.hospName = hospName;
    }

    public String getFwType() {
        return fwType;
    }

    public void setFwType(String fwType) {
        this.fwType = fwType;
    }

    public String getFwbName() {
        return fwbName;
    }

    public void setFwbName(String fwbName) {
        String str = "";
        if(StringUtils.isNotBlank(this.getFwType())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            String[] strss = this.getFwType().split(",");
            for(String strs:strss){
                List<AppServeSetting> list = dao.getServiceDo().loadByPk(AppServeSetting.class,"serObjectValue",strs);
                for(AppServeSetting ls:list){
                    if(StringUtils.isBlank(str)){
                        str = ls.getSerValue();
                    }else{
                        if(str.indexOf(ls.getSerTitle())==-1){
                            str +=";"+ls.getSerValue();
                        }

                    }
                }
            }
        }
        this.fwbName = str;
    }

    public String getDrTel() {
        return drTel;
    }

    public void setDrTel(String drTel) {
        if(StringUtils.isNotBlank(this.getDrId())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            AppDrUser drUser = (AppDrUser)dao.getServiceDo().find(AppDrUser.class,this.getDrId());
            if(drUser!=null){
                drTel = drUser.getDrTel();
            }
        }
        this.drTel = drTel;
    }

    public String getZypj() {
        return zypj;
    }

    public void setZypj(String zypj) throws Exception {
        SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
        AppDrEvaluationDrEntity entity = dao.getAppDrEvaluationDao().findEvaluationDr(this.getDrId());
        if(entity!=null){
            zypj = entity.getStrProfessionalAbility();
        }
        this.zypj = zypj;
    }

    public String getFwpj() {
        return fwpj;
    }

    public void setFwpj(String fwpj) throws Exception {
        SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
        AppDrEvaluationDrEntity entity = dao.getAppDrEvaluationDao().findEvaluationDr(this.getDrId());
        if(entity!=null){
            fwpj = entity.getStrServiceAttitude();
        }
        this.fwpj = fwpj;
    }

    public List<AppDrEvaluationViewEntity> getPjList() {
        return pjList;
    }

    public void setPjList(String pjList) throws Exception {
        SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
        List<AppDrEvaluationViewEntity> ls = dao.getAppDrEvaluationDao().findEvaluationPatientView(this.getDrId());
        this.pjList = ls;
    }

    public String getDrName() {
        return drName;
    }

    public void setDrName(String drName) {
        if(StringUtils.isNotBlank(this.getDrId())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            AppDrUser drUser = (AppDrUser)dao.getServiceDo().find(AppDrUser.class,this.getDrId());
            if(drUser!=null){
                drName = drUser.getDrName();
            }
        }
        this.drName = drName;
    }

    public String getDrSex() {
        return drSex;
    }

    public void setDrSex(String drSex) {
        if(StringUtils.isNotBlank(this.getDrId())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            AppDrUser drUser = (AppDrUser)dao.getServiceDo().find(AppDrUser.class,this.getDrId());
            if(drUser!=null){
                drSex = drUser.getDrGender();
            }
        }
        this.drSex = drSex;
    }
}
