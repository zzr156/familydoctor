package com.ylz.bizDo.jtapp.ysChangeEntity;

import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppTeam;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;

/**
 * Created by zzl on 2017/9/5.
 */
public class YsChangeMsgEntity {
    private String time;//申请时间
    private String changeState;//变更状态
    private String drId;//申请医生
    private String teamId;//申请团队
    private String changeTeamId;//变更团队
    private String changeDrId;//变更医生
    private String count;//人数
    private String type;//（1申请记录 2批准记录）
    private String num;//变更编号
    private String agreeTime;//同意或拒绝时间

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getChangeState() {
        return changeState;
    }

    public void setChangeState(String changeState) {
        this.changeState = changeState;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getChangeDrId() {
        return changeDrId;
    }

    public void setChangeDrId(String changeDrId) {
        this.changeDrId = changeDrId;
    }

    public String getChangeTeamId() {
        return changeTeamId;
    }

    public void setChangeTeamId(String changeTeamId) {
        this.changeTeamId = changeTeamId;
    }

    public String getCount() {
        return count;
    }

    public void setCount(BigInteger count) {
        if(count ==null){
            this.count = "0";
        }else{
            this.count = count.toString();
        }
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    /**
     * 获取申请医生姓名
     * @return
     */
    public String getStrDr(){
        if(StringUtils.isNotBlank(this.getDrId())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            AppDrUser drUser = (AppDrUser)dao.getServiceDo().find(AppDrUser.class,this.getDrId());
            if(drUser!=null){
                return drUser.getDrName();
            }
        }
        return "";
    }

    /**
     * 获取申请医生团队名称
     * @return
     */
    public String getStrTeam(){
        if(StringUtils.isNotBlank(this.getTeamId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            AppTeam team = (AppTeam)dao.getServiceDo().find(AppTeam.class,this.getTeamId());
            if(team!=null){
                return team.getTeamName();
            }
        }
        return "";
    }

    /**
     * 获取变更医生名称
     * @return
     */
    public String getStrChangeDr(){
        if(StringUtils.isNotBlank(this.getChangeDrId())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            AppDrUser drUser = (AppDrUser)dao.getServiceDo().find(AppDrUser.class,this.getChangeDrId());
            if(drUser!=null){
                return drUser.getDrName();
            }
        }
        return "";
    }

    /**
     * 获取变更医生团队名称
     * @return
     */
    public String getStrChangeTeam(){
        if(StringUtils.isNotBlank(this.getChangeTeamId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            AppTeam team = (AppTeam)dao.getServiceDo().find(AppTeam.class,this.getChangeTeamId());
            if(team!=null){
                if(StringUtils.isNotBlank(team.getTeamName())){
                    if(team.getTeamName().substring(team.getTeamName().length()-2,team.getTeamName().length()).equals("团队")){
                        String str = team.getTeamName().replace("团队","");
                        return str;
                    }
                }
                return team.getTeamName();
            }
        }
        return "";
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getAgreeTime() {
        return agreeTime;
    }

    public void setAgreeTime(String agreeTime) {
        this.agreeTime = agreeTime;
    }
}
