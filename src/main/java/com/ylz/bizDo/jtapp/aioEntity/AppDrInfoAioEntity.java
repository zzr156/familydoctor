package com.ylz.bizDo.jtapp.aioEntity;

import com.ylz.bizDo.app.po.AppHospDept;
import com.ylz.bizDo.jtapp.drEntity.AppServeEntity;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.comEnum.FollowPlanState;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * Created by hzk on 2017/6/16.
 * 医生信息
 */
public class AppDrInfoAioEntity {
    private String id;
    private String drName;//医生名称
    private String drImageurl;//医生头像
    private String teamState;//队长或成员
    private String teamWorkType;//成员工作类型 1..健康管理师,2.专科医生,3.全科医生,4.医技人员,5.公卫医师,6.社区护士
    private String drHospName;//医院名称
    private String drGood;////医生擅长
    private String drIntro;//医生简介
    private String teamId;//团队id
    private String hospId;//医院id

    public void setId(String id) {
        this.id = id;
    }

    public void setDrName(String drName) {
        this.drName = drName;
    }

    public void setDrImageurl(String drImageurl) {
        this.drImageurl = drImageurl;
    }

    public void setTeamState(String teamState) {
        this.teamState = teamState;
    }

    public void setTeamWorkType(String teamWorkType) {
        this.teamWorkType = teamWorkType;
    }

    public void setDrHospName(String drHospName) {
        this.drHospName = drHospName;
    }

    public void setDrGood(String drGood) {
        this.drGood = drGood;
    }

    public void setDrIntro(String drIntro) {
        this.drIntro = drIntro;
    }



    public String getId() {
        return id;
    }

    public String getDrName() {
        return drName;
    }

    public String getDrImageurl() {
        return drImageurl;
    }

    public String getTeamState() {
        return teamState;
    }

    public String getTeamWorkType() {
        return teamWorkType;
    }

    public String getDrHospName() {
        return drHospName;
    }

    public String getDrGood() {
        return drGood;
    }

    public String getDrIntro() {
        return drIntro;
    }


    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getHospId() {
        return hospId;
    }

    public void setHospId(String hospId) {
        this.hospId = hospId;
    }
}
