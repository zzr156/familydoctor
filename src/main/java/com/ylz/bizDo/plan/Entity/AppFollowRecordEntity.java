package com.ylz.bizDo.plan.Entity;

import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.plan.po.*;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.comEnum.FollowPlanState;
import com.ylz.packcommon.common.comEnum.ResidentMangeType;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lintingjie on 2017/7/26.
 */
public class AppFollowRecordEntity {

    private String batch;//批次号
    private List<FollowInfo> followInfo;//多个随访信息
    private String followDate;//随访日期
    private String drId;//医生id
    private String drName;//医生姓名
    private String workType;//工作类型
    private String workTypeName;//工作类型名称
    private String followState;//随访流程状态
    private String reason;//原因（失访就是失访原因）
    private String date;//死亡时间或失访时间

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public List<FollowInfo> getFollowInfo() {
        return followInfo;
    }

    public void setFollowInfo(String xxx) throws Exception {
        followInfo= new ArrayList<>();
        if(FollowPlanState.YIWANCHENG.getValue().equals(this.getFollowState())){
            if(StringUtils.isNotBlank(this.getBatch())) {
                SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
                List<AppFollowPlan> ls=dao.getServiceDo().loadByPk(AppFollowPlan.class,"sfFollowNum",this.getBatch());
                if(ls!=null && !ls.isEmpty()) {
                    for(AppFollowPlan plan : ls){
                        AppPatientUser user = (AppPatientUser) dao.getServiceDo().find(AppPatientUser.class,plan.getSfFollowPatientid());
                        FollowInfo info = new FollowInfo();
                        if(user!=null){
                            info.setPatientName(user.getPatientName());
                            info.setPatientGender(user.getPatientGender());
                            info.setPatientAge(user.getPatientIdno());
                        }
                        info.setFollowType(plan.getSfFollowType());
                        info.setFollowNum(plan.getSfTypeNum());
                        if(ResidentMangeType.GXY.getValue().equals(plan.getSfFollowType())){
                            List<AppHdBlooPressureTable> gxyList =dao.getServiceDo().loadByPk(AppHdBlooPressureTable.class,"visitId",plan.getId());
                            if(gxyList!=null&&!gxyList.isEmpty()){
                                info.setVisitThisType(gxyList.get(0).getVisitThisType());
                                info.setReferral(gxyList.get(0).getReferral());
                                info.setFollowId(gxyList.get(0).getId());
                            }
                        }else if(ResidentMangeType.TNB.getValue().equals(plan.getSfFollowType())){
                            List<AppDiabetesTable> tnbList =dao.getServiceDo().loadByPk(AppDiabetesTable.class,"visitId",plan.getId());
                            if(tnbList!=null && !tnbList.isEmpty()){
                                info.setVisitThisType(tnbList.get(0).getVisitThisType());
                                info.setReferral(tnbList.get(0).getReferral());
                                info.setFollowId(tnbList.get(0).getId());
                            }
                        }else if(ResidentMangeType.YCF.getValue().equals(plan.getSfFollowType())){
                            List<AppPostpartumVisit> ycfList = dao.getServiceDo().loadByPk(AppPostpartumVisit.class,"postVisitId",plan.getId());
                            if(ycfList!=null && ycfList.size()>0){
//                            info.setVisitThisType(ycfList.get(0).getPostVisitWay());
                                info.setReferral(ycfList.get(0).getPostReferral());
                                info.setFollowId(ycfList.get(0).getId());
                            }
                        }else if(ResidentMangeType.ETLZLS.getValue().equals(plan.getSfFollowType())){
                            List<AppChild> childList = dao.getServiceDo().loadByPk(AppChild.class,"childVisitId",plan.getId());
                            if(childList!=null && childList.size()>0){
//                            info.setVisitThisType(childList.get(0).getChildVisitWay());
                                info.setReferral(childList.get(0).getChildReferral());
                                info.setFollowId(childList.get(0).getId());
                            }
                        }else if(ResidentMangeType.TY.getValue().equals(plan.getSfFollowType())){
                            List<AppGeneralTable> list = dao.getServiceDo().loadByPk(AppGeneralTable.class,"genVisitId",plan.getId());
                            if(list!=null && list.size()>0){
//                            info.setVisitThisType(list.get(0).getGenVisitWay());
                                info.setFollowId(list.get(0).getId());
                            }
                        }else if(ResidentMangeType.YZJSZY.getValue().equals(plan.getSfFollowType())){
                            List<AppMentalVisitTable> list = dao.getServiceDo().loadByPk(AppMentalVisitTable.class,"visitId",plan.getId());
                            if(list != null && list.size()>0){
                                info.setFollowId(list.get(0).getId());
                            }
                        }else if(ResidentMangeType.JHB.getValue().equals(plan.getSfFollowType())){
                            List<AppFirstTBFollowVisitTable> list = dao.getServiceDo().loadByPk(AppFirstTBFollowVisitTable.class,"ftbVisitId",plan.getId());
                            if(list != null && list.size()>0){
                                info.setFollowId(list.get(0).getId());
                            }else {
                                List<AppTBFollowVisitTable> listt = dao.getServiceDo().loadByPk(AppTBFollowVisitTable.class,"visitId",plan.getId());
                                if(listt != null && listt.size()>0){
                                    info.setFollowId(listt.get(0).getId());
                                }
                            }
                        }
                        if(FollowPlanState.YIWANCHENG.getValue().equals(plan.getSfFollowState())){
                            followInfo.add(info);
                        }
                    }
                }
            }
        }
    }

    public String getFollowDate() {
        return followDate;
    }

    public void setFollowDate(String followDate) {
        this.followDate = followDate;
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

    public String getWorkTypeName() {
        return workTypeName;
    }

    public void setWorkTypeName(String workTypeName) throws Exception {
        if(StringUtils.isNotBlank(this.getWorkType())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_WORKTYPE, this.getWorkType());
            if(value!=null) {
                workTypeName = value.getCodeTitle();
            }
        }
        this.workTypeName = workTypeName;
    }

    public String getFollowState() {
        return followState;
    }

    public void setFollowState(String followState) {
        this.followState = followState;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        if(date !=null){
            this.date = ExtendDate.getYMD_h_m(date);
        }else{
            this.date = "";
        }
    }
}
