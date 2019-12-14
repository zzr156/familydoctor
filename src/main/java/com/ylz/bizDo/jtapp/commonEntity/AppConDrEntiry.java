package com.ylz.bizDo.jtapp.commonEntity;

import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.util.SericuryUtil;
import org.apache.commons.lang.StringUtils;

/**
 * Created by zzl on 2017/6/29.
 */
public class AppConDrEntiry {
    private String drId;//医生id
    private String drName;//医生姓名
    private String drImageurl;//医生头像
    private String drGender;//医生性别
    private String id;
    private String workType;//工作类型
    public String getId() {
        return id;
    }

    public void setId(String id) {
        if(StringUtils.isNotBlank(this.getDrId())){
            try {
                SericuryUtil p = new SericuryUtil();
                this.id = p.encrypt(this.getDrId());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
       this.drId = drId;
    }

    public String getDrName() {
        return drName;
    }

    public void setDrName(String drName) {
        this.drName = drName;
    }

    public String getDrImageurl() {
        return drImageurl;
    }

    public void setDrImageurl(String drImageurl) {
        if(StringUtils.isNotBlank(this.getDrId())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            AppDrUser drUser = (AppDrUser)dao.getServiceDo().find(AppDrUser.class,this.getDrId());
            if(drUser!=null){
                drImageurl = drUser.getDrImageurl();
            }

        }
        this.drImageurl = drImageurl;
    }

    public String getDrGender() {
        return drGender;
    }

    public void setDrGender(String drGender) {
        if(StringUtils.isNotBlank(this.getDrId())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            AppDrUser drUser = (AppDrUser)dao.getServiceDo().find(AppDrUser.class,this.getDrId());
            if(drUser!=null){
                drGender = drUser.getDrGender();
            }

        }
        this.drGender = drGender;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType)throws Exception {
        String str = "";
        if(StringUtils.isNotBlank(workType)){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_WORKTYPE, workType);
            if(value!=null) {
                str = value.getCodeTitle();
            }
        }
        this.workType = str;
    }
}
