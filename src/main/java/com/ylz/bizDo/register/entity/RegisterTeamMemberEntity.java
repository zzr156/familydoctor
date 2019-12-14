package com.ylz.bizDo.register.entity;

import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppSignServeTeam;
import com.ylz.bizDo.app.po.AppTeam;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.comEnum.CommSF;
import com.ylz.packcommon.common.comEnum.SignFormType;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterTeamMemberEntity {
    private String id;
    private String memTeamid;//团队主键
    private String memDrId;//成员主键
    private String memDrName;//成员名称
    private String memWorkType;//成员工作类型 1..健康管理师,2.专科医生,3.全科医生,4.医技人员,5.公卫医师,6.社区护士
    //    private String memTeamName;//团队名称
    private String memState;// 团队角色 0：队长 1：成员
    private String memWorkName;//成员工作类型名称
    private String toplimit;//是否签约上限（0否 1是）
    private String drrole; // 职称
    private String drtel;//医生电话号码
    private String drCode;//医生编号
    private String drIdNo;//医生身份证号


    public String getDrrole() {
        return drrole;
    }

    public void setDrrole(String drrole) {
        this.drrole = drrole;
    }

    public String getDrIdNo() {
        return drIdNo;
    }

    public void setDrIdNo(String drIdNo) {
        this.drIdNo = drIdNo;
    }

    public String getDrCode() {
        return drCode;
    }

    public void setDrCode(String drCode) {
        this.drCode = drCode;
    }

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
        // 为空默认 家庭成员 001  ，002 传医保
        if(StringUtils.isNotBlank(memState)){
           /* if(memState.equals("0")){
                this.memState = "001";
            }else{
                this.memState = "002";
            }
        }else{
            this.memState = "001";
        }*/
            if(memState.equals("0")){
                this.memState = "家庭医生";
            }else{
                this.memState = "团队成员";
            }
        }else{
            this.memState = "家庭医生";
        }

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

    public void setMemWorkName(String memWorkName) {
        // 001住院医师、002主治医师、003副主任医师、004主任医师、005无职称
        if(StringUtils.isNotBlank(this.getDrrole())){
            /*SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_WORKTYPE, this.getMemWorkType());
            if(value!=null) {
                memWorkName = value.getCodeTitle();
            }*/
           /* if(this.getDrrole().equals("1.1")){
                this.memWorkName = "004";
            }else if(this.getDrrole().equals("1.2")){
                this.memWorkName = "003";
            }else if(this.getDrrole().equals("1.3")){
                this.memWorkName = "002";
            }else {
                this.memWorkName = "005";
            }

        }else{
            this.memWorkName = "005";
        }*/
            if(this.getDrrole().equals("1.1")){
                this.memWorkName = "主任医师";
            }else if(this.getDrrole().equals("1.2")){
                this.memWorkName = "副主任医师";
            }else if(this.getDrrole().equals("1.3")){
                this.memWorkName = "主治医师";
            }else {
                this.memWorkName = "无职称";
            }

        }else{
            this.memWorkName = "无职称";
        }

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

    public void setToplimit(String toplimit) {
        if(StringUtils.isNotBlank(this.getMemDrId())){
            toplimit ="0";
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("signState", SignFormType.YQY.getValue());
            map.put("contractState", CommSF.NOT.getValue());
            map.put("drId",this.getMemDrId());
            String sql = "SELECT COUNT(1) FROM APP_SIGN_FORM WHERE 1=1";
            sql += " AND SIGN_STATE = :signState AND SIGN_CONTRACT_STATE = :contractState AND SIGN_DR_ID = :drId";
            int count = dao.getServiceDo().findCount(sql,map);
            AppDrUser drUser = (AppDrUser)dao.getServiceDo().find(AppDrUser.class,this.getMemDrId());
            if(drUser!=null){
                List<AppSignServeTeam> ssTeamList = dao.getServiceDo().loadByPk(AppSignServeTeam.class,"sstDeptId",drUser.getDrHospId());
                if(ssTeamList != null && ssTeamList.size()>0){
                    AppSignServeTeam ssTeam = ssTeamList.get(0);
                    if(CommSF.YES.getValue().equals(ssTeam.getSstSignState())){//开启签约上限
                        if(count<Integer.parseInt(ssTeam.getSstSignToDr())){
                            toplimit = "0";//还没上限
                        }else{
                            toplimit = "1";//签约上限
                        }
                    }
                }
            }
        }
        this.toplimit = toplimit;
    }
}
