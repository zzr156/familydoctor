package com.ylz.bizDo.jtapp.drEntity;

import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.util.SericuryUtil;
import org.apache.commons.lang.StringUtils;

/**返回团队成员
 * Created by zzl on 2017/7/10.
 */
public class AppDrTeamMemEntiry  {
    private String id;//主键id
    private String teamId;//团队id
    private String teamName;//团队名称
    private String drId;//医生id
    private String drName;//医生姓名
    private String workType;//医生工作类型
    private String state;//成员角色
    private String workTypeName;//工作类型名称

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
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

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getWorkTypeName() {
        return workTypeName;
    }

    public void setWorkTypeName(String workTypeName) throws Exception {
        if(StringUtils.isNotBlank(this.getWorkType())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_WORKTYPE, this.getWorkType());
            if(value!=null) {
                workTypeName=value.getCodeTitle();
            }
        }
        this.workTypeName = workTypeName;
    }

    public String getEaseId(){
        if(StringUtils.isNotBlank(this.getDrId())){
           try {
               SericuryUtil p = new SericuryUtil();
               return p.encrypt(this.getDrId());
           }catch (Exception e){

           }
        }
        return  "";
    }

    public String getTeamDrGender(){
        if(StringUtils.isNotBlank(this.getDrId())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            AppDrUser user = (AppDrUser) dao.getServiceDo().find(AppDrUser.class,this.getDrId());
            if(user!=null) {
                return user.getDrGender();
            }
        }
        return "";
    }
}
