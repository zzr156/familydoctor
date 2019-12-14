package com.ylz.bizDo.jtapp.patientEntity;

import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppHospDept;
import com.ylz.bizDo.app.po.AppSignServeTeam;
import com.ylz.bizDo.app.po.AppTeam;
import com.ylz.bizDo.app.vo.AppSignVo;
import com.ylz.bizDo.cd.po.CdAddressPeople;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.jtapp.commonVo.AppCommQvo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.comEnum.CommSF;
import com.ylz.packcommon.common.comEnum.SignFormType;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2017/6/17.
 */
public class AppTeamMemberEntity {
    private String id;
    private String memTeamid;//团队主键
    private String memDrId;//成员主键
    private String memDrName;//成员名称
    private String memWorkType;//成员工作类型 1..健康管理师,2.专科医生,3.全科医生,4.医技人员,5.公卫医师,6.社区护士
//    private String memTeamName;//团队名称
    private String memState;// 团队角色 0：队长 1：成员
    private String memWorkName;//成员工作类型名称
    private String toplimit;//是否签约上限（0否 1是）

    private String drtel;//医生电话号码

    public String getDrtel() {
        return drtel;
    }

    public void setDrtel(String drtel) {
        this.drtel = drtel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemTeamid() {
        return memTeamid;
    }

    public void setMemTeamid(String memTeamid) {
        this.memTeamid = memTeamid;
    }

    public String getMemDrId() {
        return memDrId;
    }

    public void setMemDrId(String memDrId) {
        this.memDrId = memDrId;
    }

    public String getMemDrName() {
        return memDrName;
    }

    public void setMemDrName(String memDrName) {
        this.memDrName = memDrName;
    }

    public String getMemState() {
        return memState;
    }

    public void setMemState(String memState) {
        this.memState = memState;
    }

    public String getMemWorkType() {
        return memWorkType;
    }

    public void setMemWorkType(String memWorkType) {
        this.memWorkType = memWorkType;
    }

    public String getMemWorkName() {
        return memWorkName;
    }

    public void setMemWorkName(String memWorkName) throws Exception {
        if(StringUtils.isNotBlank(this.getMemWorkType())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_WORKTYPE, this.getMemWorkType());
            if(value!=null) {
                memWorkName=value.getCodeTitle();
            }
        }
        this.memWorkName = memWorkName;
    }

    public String getStrMemTeamName(){
       if(StringUtils.isNotBlank(this.getMemTeamid())){
           SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
           AppTeam team = (AppTeam) dao.getServiceDo().find(AppTeam.class,this.getMemTeamid());
           if(team != null) {
               return team.getTeamName();
           }

       }
       return "";
    }

    public String getToplimit() {
        return toplimit;
    }

    public void setToplimit(String toplimit) throws Exception{
        toplimit ="0";
        if(StringUtils.isNotBlank(this.getMemDrId())&&StringUtils.isNotBlank(this.getMemTeamid())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            AppDrUser drUser =(AppDrUser) dao.getServiceDo().find(AppDrUser.class,this.getMemDrId());
            if(drUser!=null){
                AppHospDept dept = (AppHospDept)dao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
                if(dept!=null){
                    CdAddressPeople cdAdd = dao.getCdAddressPeopleDao().findByCacheCode(dept.getHospAreaCode(), ExtendDate.getYYYY(Calendar.getInstance()));
                    if(cdAdd!=null){
                        AppCommQvo app = new AppCommQvo();
                        app.setSignHospId(dept.getId());
                        if("0".equals(cdAdd.getAreaSignWay())){//医生上限签约人数
                            app.setTeamId(this.getMemTeamid());
                            List<AppSignVo> ls = dao.getAppSignformDao().findSignXxWeb(app);
                            if (ls != null && ls.size() > 0) {
                                if (ls.size() >= Integer.parseInt(cdAdd.getAreaSignTop())) {
                                    toplimit = "1";
                                }
                            }
                        }
                    }
                }
            }
        }
        this.toplimit = toplimit;
    }
}
