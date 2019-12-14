package com.ylz.task.async;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ylz.bizDo.jtapp.commonEntity.AssessNewsEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylz.bizDo.app.entity.AppPerformanceRecordEntity;
import com.ylz.bizDo.app.po.AppChildHealthPlan;
import com.ylz.bizDo.app.po.AppChildInoculationPlan;
import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppDrug;
import com.ylz.bizDo.app.po.AppDrugGuide;
import com.ylz.bizDo.app.po.AppDrugPlan;
import com.ylz.bizDo.app.po.AppDrugPlanExtend;
import com.ylz.bizDo.app.po.AppHomeCareSetting;
import com.ylz.bizDo.app.po.AppHospDept;
import com.ylz.bizDo.app.po.AppLabelManage;
import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.bizDo.app.po.AppPerformanceRecord;
import com.ylz.bizDo.app.po.AppPregnantPlan;
import com.ylz.bizDo.app.po.AppSerial;
import com.ylz.bizDo.app.po.AppServeSetmeal;
import com.ylz.bizDo.app.po.AppSignForm;
import com.ylz.bizDo.app.po.AppSignsRecordTable;
import com.ylz.bizDo.app.po.AppSignsWarningRecord;
import com.ylz.bizDo.app.po.AppWarningSetting;
import com.ylz.bizDo.app.vo.AppServeSetmealQvo;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.jtapp.commonEntity.AppChildLyEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppLyTxEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppTcmLyEntity;
import com.ylz.bizDo.jtapp.commonVo.AppCommLyQvo;
import com.ylz.bizDo.jtapp.commonVo.AppCommQvo;
import com.ylz.bizDo.jtapp.drEntity.AppDrSignPeoleListEntity;
import com.ylz.bizDo.jtapp.drEntity.AppDrTzyjEntity;
import com.ylz.bizDo.plan.Entity.AppFollowPlanTxEntity;
import com.ylz.bizDo.plan.po.AppFollowPlan;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.CommSF;
import com.ylz.packcommon.common.comEnum.CommonEnable;
import com.ylz.packcommon.common.comEnum.CommonWarnSet;
import com.ylz.packcommon.common.comEnum.DrPatientType;
import com.ylz.packcommon.common.comEnum.NoticesMType;
import com.ylz.packcommon.common.comEnum.NoticesType;
import com.ylz.packcommon.common.comEnum.PerType;
import com.ylz.packcommon.common.comEnum.PerformanceType;
import com.ylz.packcommon.common.comEnum.ResidentMangeType;
import com.ylz.packcommon.common.comEnum.SignFormType;
import com.ylz.packcommon.common.util.AreaUtils;
import com.ylz.packcommon.common.util.ExtendDate;

/**
 * Created by zzl on 2017/11/1.
 */
@Component("schedulingAsyncBean")
public class SchedulingAsyncBean {
    @Autowired
    private SysDao sysDao;
    /**
     * 体检提醒
     */
    public void medicalAlert(String userId)throws Exception{
        if(StringUtils.isNotBlank(userId)){
            Calendar cal = Calendar.getInstance();
            List<AppChildHealthPlan> ls =sysDao.getAppChildHealthPlanDao().findByYj(cal,userId);
            if (ls != null && ls.size() > 0) {
                for (AppChildHealthPlan v : ls) {
                    List<AppWarningSetting> setList = sysDao.getAppWarningSettingDao().findSetting(v.getChpUserId(), CommonWarnSet.TJXT.getValue());
                    int leftday = ExtendDate.getDayNumBetween2Date(Calendar.getInstance(),v.getChpPlanDate());
                    sysDao.getAppNoticeDao().addNotices("儿童" + v.getChpTitle() + "体检提醒", "您好，"
                            + ExtendDate.getChineseYMD(v.getChpPlanDate()) + "您家的儿童满" + v.getChpTitle() + "啦，请及时进行健康体检。",
                            NoticesType.TJTX.getValue(), "系统提醒", v.getChpUserId(), v.getId(), DrPatientType.PATIENT.getValue());
                    v.setChpTxState("1");
                    sysDao.getServiceDo().modify(v);

                    /*int warnNum = 3;//默认3天
                    if(!setList.isEmpty()){
                        warnNum = Integer.parseInt(setList.get(0).getWsNum());
                    }
                    if((!setList.isEmpty()&&setList.get(0).getWsState().equals(CommonEnable.QIYONG.getValue())&&warnNum>=leftday)
                            ||(setList.isEmpty()&&warnNum>=leftday)) {
                        sysDao.getAppNoticeDao().addNotices("儿童" + v.getChpTitle() + "体检提醒", "您好，" + ExtendDate.getChineseYMD(v.getChpPlanDate()) + "您家的儿童满" + v.getChpTitle() + "啦，请及时进行健康体检。", NoticesType.TJTX.getValue(), "系统提醒", v.getChpUserId(), v.getId(), DrPatientType.PATIENT.getValue());
                        v.setChpTxState("1");
                        sysDao.getServiceDo().modify(v);
                    }*/
                }
            }
        }
    }

    /**
     * 药品存量预警
     */
    public void drugRunOut(String userId)throws Exception{
        if(StringUtils.isNotBlank(userId)){
            List<AppDrugGuide> ls = sysDao.getAppDrugGuideDao().findGuidByTime(userId);
            if(ls!=null && ls.size()>0){
                for (AppDrugGuide guide:ls) {
                    double warnNum = 10;//默认10次
                    if(guide.getDgWarnNum()!=null){
                        warnNum = Double.parseDouble(guide.getDgWarnNum());
                    }else if(guide.getDgCommonWarnNum()!=null){
                        warnNum = Double.parseDouble(guide.getDgCommonWarnNum());
                    }
                    int leftday = ExtendDate.getDayNumBetween2Date(Calendar.getInstance(),guide.getDgEndTime());
                    double freq = Double.parseDouble(guide.getDgFrequency());
                    if((leftday>0&&leftday*freq < warnNum)||(leftday==0&&freq<warnNum)){
                        AppDrug drug = (AppDrug) sysDao.getServiceDo().find(AppDrug.class,guide.getDgDrugId());
                        AppPatientUser patient = (AppPatientUser) sysDao.getServiceDo().find(AppPatientUser.class,guide.getDgPatientId());
                        int num=0;
                        if(leftday!=0){
                            num= (new Double(leftday*freq)).intValue();
                        }else if(freq>=1){
                            num= (new Double(freq)).intValue();

                        }
                        if(drug!=null&&patient!=null){
                            String title = drug.getDrugName()+",剩余次数："+num;
                            String content1="您的"+drug.getStrDrugType()+":"+drug.getDrugName()+" 可使用次数低于"+(new Double(warnNum)).intValue()+"次，请及时就诊";
                            String content2= patient.getPatientName()+"的"+drug.getStrDrugType()+":"+drug.getDrugName()+" 可使用次数低于"+String.valueOf((int)warnNum)+"次，请及时就诊";
                            //发给患者
                            List<AppWarningSetting> setList = sysDao.getAppWarningSettingDao().findSetting(guide.getDgPatientId(), CommonWarnSet.YYDQ.getValue());
                            if(!setList.isEmpty()&&setList.get(0).getWsState().equals(CommonEnable.QIYONG.getValue())||setList.isEmpty()){
                                sysDao.getAppNoticeDao().addNotices(title,content1, NoticesType.YYDQJJ.getValue(),"系统提醒",guide.getDgPatientId(),guide.getId(),DrPatientType.PATIENT.getValue());
                            }
                            //发给医生
                            sysDao.getAppNoticeDao().addNotices(title,content2, NoticesType.YYDQJJ.getValue(),"系统提醒",guide.getDgDocId(),guide.getId(),DrPatientType.DR.getValue());
                            AppDrugGuide vo = (AppDrugGuide)sysDao.getServiceDo().find(AppDrugGuide.class,guide.getId());
                            if(vo != null){
                                vo.setDgSendState("1");//状态1为已发送
                                sysDao.getServiceDo().modify(vo);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 孕妇保健计划提醒
     */
    public void pregnantPlan(String userId)throws Exception{
        if(StringUtils.isNotBlank(userId)){
            List<AppPregnantPlan> list = sysDao.getAppPregnantPlanDao().findPlanAlert(userId);
            if(list!=null && list.size()>0){
                for(AppPregnantPlan ll:list){
                    int leftDay = ExtendDate.getDayNumBetween2Date(Calendar.getInstance(),ll.getPpPlanDate());
                    String content = "您好，今天"+ExtendDate.getYMD(Calendar.getInstance())+"离您"+ExtendDate.getYMD(ll.getPpPlanDate())+"的体检计划仅剩"+leftDay+"天,请及时进行体检";
                    sysDao.getAppNoticeDao().addNotices( "孕产妇保健计划", content, NoticesType.TJTX.getValue(), "系统提醒",
                            ll.getPpUserId(), ll.getId(), DrPatientType.PATIENT.getValue());
                    ll.setPpReadState("1");
                    sysDao.getServiceDo().modify(ll);
                }
            }
            /*if(!list.isEmpty()){
                for (AppPregnantPlan plan:list) {
                    int leftDay = ExtendDate.getDayNumBetween2Date(Calendar.getInstance(),plan.getPpPlanDate());
                    List<AppWarningSetting> setList = sysDao.getAppWarningSettingDao().findSetting(plan.getPpUserId(), CommonWarnSet.TJXT.getValue());
                    int warnNum = 3;//默认3天
                    if(!setList.isEmpty()){
                        warnNum = Integer.parseInt(setList.get(0).getWsNum());
                    }
                    String content = "您好，今天"+ExtendDate.getYMD(Calendar.getInstance())+"离您"+ExtendDate.getYMD(plan.getPpPlanDate())+"的体检计划仅剩"+leftDay+"天,请及时进行体检";
                    if((!setList.isEmpty()&&setList.get(0).getWsState().equals(CommonEnable.QIYONG.getValue())&&warnNum>=leftDay)
                            ||(setList.isEmpty()&&warnNum>=leftDay)) {
                        sysDao.getAppNoticeDao().addNotices( "孕产妇保健计划", content, NoticesType.TJTX.getValue(), "系统提醒",
                                plan.getPpUserId(), plan.getId(), DrPatientType.PATIENT.getValue());
                    }
                }
            }*/
        }
    }

    /**
     * 续签提醒
     */
    public void renewalReminder(String userId)throws Exception{
        if(StringUtils.isNotBlank(userId)){
            //发送绿级提醒
            List<AppSignForm> greenList = sysDao.getAppSignformDao().findByGreen(userId);
            if(greenList!=null && greenList.size()>0){
                for(AppSignForm ls:greenList){
                    sysDao.getAppNoticeDao().addNotices("续签提醒",  "您好，您的家庭医生签约服务将于"+ExtendDate.getChineseYMD(ls.getSignToDate())+"到期，建议续签。", NoticesType.XTXX.getValue()+","+ NoticesMType.XQXX.getValue(), "系统提醒",
                            ls.getSignPatientId(), ls.getId(), DrPatientType.PATIENT.getValue());
                    ls.setSignGreenState(CommSF.YES.getValue());
                    sysDao.getServiceDo().modify(ls);
                }
            }
            //发送黄级提醒
            List<AppSignForm> yellowList = sysDao.getAppSignformDao().findByYellow(userId);
            if(yellowList!=null && yellowList.size()>0){
                for(AppSignForm ls:yellowList){
                    sysDao.getAppNoticeDao().addNotices("续签提醒",  "您好，您的家庭医生签约服务将于"+ExtendDate.getChineseYMD(ls.getSignToDate())+"到期，建议续签。", NoticesType.XTXX.getValue()+","+NoticesMType.XQXX.getValue(), "系统提醒",
                            ls.getSignPatientId(), ls.getId(), DrPatientType.PATIENT.getValue());
                    ls.setSignYellowState(CommSF.YES.getValue());
                    sysDao.getServiceDo().modify(ls);
                }
            }
            //发送红级提醒
            List<AppSignForm> redList = sysDao.getAppSignformDao().findByRed(userId);
            if(redList!=null && redList.size()>0){
                for(AppSignForm ls:redList){
                    sysDao.getAppNoticeDao().addNotices("续签提醒", "您好，您的家庭医生签约服务将于"+ExtendDate.getChineseYMD(ls.getSignToDate())+"到期，建议续签。", NoticesType.XTXX.getValue()+","+NoticesMType.XQXX.getValue(), "系统提醒",
                            ls.getSignPatientId(), ls.getId(), DrPatientType.PATIENT.getValue());
                    ls.setSignRedState(CommSF.YES.getValue());
                    sysDao.getServiceDo().modify(ls);
                }
            }
        }
    }

    /**
     * 居家养老提醒
     */
    public void homeCareReminder(String userId)throws Exception{

        if(StringUtils.isNotBlank(userId)){
            //签约居家养老服务列表
            List<AppSignForm> signList = sysDao.getServiceDo().loadByPk(AppSignForm.class,"signServiceB","2");
            for(AppSignForm sign : signList){
                if(userId.equals(sign.getSignPatientId())){
                    List<AppHomeCareSetting> setList = sysDao.getServiceDo().loadByPk(AppHomeCareSetting.class,"homeTeamId",sign.getSignTeamId());
                    if(!setList.isEmpty() && sign.getSignServiceBDate()!=null){
                        Calendar bDate = sign.getSignServiceBDate();
                        //管理方式
                        CdCode codeManage = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_HOMECAREMANGE, setList.get(0).getHomeManageStyle());
                        String manageName = "居家养老服务";
                        if(codeManage!=null){
                            manageName = codeManage.getCodeTitle();
                        }
                        //居民周期
                        CdCode codeCycle = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_HOMECARECYCLE, setList.get(0).getHomeManageCycle());
                        //居民频次
                        CdCode codeFrequency = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_HOMECAREFREQUENCY, setList.get(0).getHomeManageFrequency());
                        String remindDays = setList.get(0).getHomeManagePeminderDays();
                        while(bDate.before(sign.getSignToDate())){
                            int num = 0;
                            int numm = 0;
                            if(!"0".equals(codeCycle.getCodeValue())){
                                num=Integer.parseInt(codeCycle.getCodeValue());
                            }
                            if(!"0".equals(codeFrequency.getCodeValue())){
                                numm = Integer.parseInt(codeFrequency.getCodeValue());
                            }
                            if(numm!=0){
                                bDate.add(Calendar.DAY_OF_YEAR,num/numm);
                            }
//                            bDate.add(Calendar.DAY_OF_YEAR,Integer.parseInt(codeCycle.getCodeValue())/Integer.parseInt(codeFrequency.getCodeValue()));
                            int leftDays = ExtendDate.getDayNumBetween2Date(Calendar.getInstance(),bDate);
                            if(leftDays>0 && leftDays<=Integer.parseInt(remindDays)){
                                sysDao.getAppNoticeDao().addNotices("居家养老提醒", "您好，"+ExtendDate.getChineseYMD(bDate)+"将对您进行"+manageName+"，请做好准备",
                                        NoticesType.JJYL.getValue(), "系统提醒", sign.getSignPatientId(), sign.getId(), DrPatientType.PATIENT.getValue());
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 儿童体检免疫提醒
     */
    public void chileInoculation(String userId)throws Exception{
        if(StringUtils.isNotBlank(userId)){
            AppPatientUser user =(AppPatientUser) sysDao.getServiceDo().find(AppPatientUser.class,userId);
            List<AppChildInoculationPlan> list = sysDao.getAppChildInoculationPlanDao().findList(userId);
            String parientId = "";
            String str = "";
            String strDate = "";
            String id = "";
            if(list !=null && list.size()>0){
                for(AppChildInoculationPlan ls:list){
                    ls.setInoculationTxState("1");
                    sysDao.getServiceDo().modify(ls);
                    strDate = ExtendDate.getChineseYMD(ls.getInoculationDate());
                    id = ls.getId();
                    if(StringUtils.isNotBlank(parientId)){
                        if(ls.getInoculationUserId().equals(parientId)){
                            if(StringUtils.isBlank(str)){
                                str = ls.getInoculationName();
                            }else{
                                str += "、"+ls.getInoculationName();
                            }
                        }else{
                            sysDao.getAppNoticeDao().addNotices("儿童计划免疫提醒",
                                    user.getPatientName()+"家长，请您于" + ExtendDate.getChineseYMD(ls.getInoculationDate()) + "带儿童到本门诊接种"+str+"。",
                                    NoticesType.YFJZTX.getValue(), "系统提醒", parientId, ls.getId(), DrPatientType.PATIENT.getValue());
                            parientId = ls.getInoculationUserId();
                            str = ls.getInoculationName();
                        }
                    }else{
                        parientId = ls.getInoculationUserId();
                        str = ls.getInoculationName();
                    }
                }
                if(StringUtils.isNotBlank(parientId)){
                    sysDao.getAppNoticeDao().addNotices("儿童计划免疫提醒",
                            user.getPatientName()+"家长，请您于" + strDate + "带儿童到门诊接种疫苗："+str+"。",
                            NoticesType.YFJZTX.getValue(), "系统提醒", parientId, id, DrPatientType.PATIENT.getValue());
                }
            }

        }
    }

    /**
     * 用药提醒
     */
    public void drugReminder(String userId)throws Exception{
        if(StringUtils.isNotBlank(userId)){
            HashMap<String,Object> map = new HashMap<>();
            map.put("userId",userId);
            String sql = "select * from APP_DRUG_PLAN where DP_STATE = '1' AND DP_PATIENT_ID=:userId ";
            Calendar cal = Calendar.getInstance();
            int week = cal.get(Calendar.DAY_OF_WEEK);
            switch(week) {
                case 1:
                    sql+=" and (sunday ='1' ";
                    break;
                case 2:
                    sql+=" and (monday ='1' ";
                    break;
                case 3:
                    sql+=" and (tuesday ='1' ";
                    break;
                case 4:
                    sql+=" and (wednesday ='1' ";
                    break;
                case 5:
                    sql+=" and (thursday ='1' ";
                    break;
                case 6:
                    sql+=" and (friday ='1' ";
                    break;
                case 7:
                    sql+=" and (saturday ='1' ";
                    break;
                default:
                    break;
            }
            sql +=" or ONLY_ONE = '1')";
            map.put("m",5);
            sql +=" and CONCAT(DP_REMIND_TIME, ':00') >= date_format(date_sub(NOW(), INTERVAL 5 MINUTE),'%H:%i:%s') and CONCAT(DP_REMIND_TIME, ':00') < date_format(NOW(), '%H:%i:%s') ";
            List<AppDrugPlan> list = sysDao.getServiceDo().findSqlMap(sql,map,AppDrugPlan.class);
            if(list!=null && !list.isEmpty()){
                for(AppDrugPlan drugPlan : list){
                    if("1".equals(drugPlan.getOnlyOne())) {
                        drugPlan.setDpState(CommonEnable.JINYONG.getValue());
                        sysDao.getServiceDo().modify(drugPlan);
                    }
                    String content = "服药时间到啦！";
                    List<AppDrugPlanExtend> ls = sysDao.getServiceDo().loadByPk(AppDrugPlanExtend.class,"drugPlanId",drugPlan.getId());
                    for(AppDrugPlanExtend extend:ls){
                        AppDrugGuide guide = (AppDrugGuide) sysDao.getServiceDo().find(AppDrugGuide.class,extend.getDrugId());
                        String drugFrequency = "";
                        String drugTaking = "";
                        String drugUsage = "";
                        if("0"!=guide.getDgFrequency()) {//用药频次不为“其他”
                            CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_FREQUENCY, guide.getDgFrequency());
                            if(value!=null) {
                                drugFrequency=value.getCodeTitle();
                            }
                        }else{
                            drugFrequency=guide.getDgFrequencyOther();
                        }
                        if(guide.getDgTaking()!=null) {
                            CdCode value1 = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_TAKING, guide.getDgTaking());
                            if (value1 != null) {
                                drugTaking = value1.getCodeTitle();
                            }
                        }
                        if(guide.getDgUsage()!=null) {
                            CdCode value2 = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_USAGE, guide.getDgUsage());
                            if (value2 != null) {
                                drugUsage = value2.getCodeTitle();
                            }
                        }
                        content += guide.getDgDrugName()+"，"+drugTaking+drugUsage+"，"+drugFrequency+"。";
                    }
                    sysDao.getAppNoticeDao().addNotices("用药提醒",content,NoticesType.YYTX.getValue(),drugPlan.getDpPatientId(),drugPlan.getDpPatientId(),drugPlan.getId(),DrPatientType.PATIENT.getValue());
                }
            }
        }
    }

    /**
     * 随访消息提醒
     */
    public void followVisit(String userId)throws Exception{
        AppDrUser drUser = (AppDrUser)sysDao.getServiceDo().find(AppDrUser.class,userId);
        if(drUser!=null){
            List<AppFollowPlan> list = sysDao.getAppFollowPlanDao().findAllPlan(drUser.getId());
            if(list!=null&&list.size()>0){
                for(AppFollowPlan ls:list){
                    sysDao.getAppNoticeDao().addNotices("随访提醒",
                            ExtendDate.getChineseYMD(ls.getSfFollowDate())+"您有需完成的随访，请注意",
                            NoticesType.SFTX.getValue(), "系统提醒", ls.getSfFollowDoctorid(), "", DrPatientType.DR.getValue());
                }
            }
        }
    }

    /**
     * 当天临时随访提醒
     */
    public void followNow(String userId)throws Exception{
        AppDrUser drUser = (AppDrUser)sysDao.getServiceDo().find(AppDrUser.class,userId);
        if(drUser!=null){
            Calendar cal = Calendar.getInstance();
            String type = "";
            if(cal.get(Calendar.HOUR_OF_DAY)>7&&cal.get(Calendar.HOUR_OF_DAY)<=9){
                type = CommonWarnSet.SFBD.getValue();
            }else if(cal.get(Calendar.HOUR_OF_DAY)<=17&&cal.get(Calendar.HOUR_OF_DAY)>15){
                type = CommonWarnSet.SFSWD.getValue();
            }
            if(StringUtils.isNotBlank(type)){
                List<AppFollowPlanTxEntity> list = sysDao.getAppFollowPlanDao().findAllDayPlan(type,drUser.getId());
                if(list!=null&&list.size()>0){
                    for(AppFollowPlanTxEntity ls:list){
                        sysDao.getAppNoticeDao().addNotices("随访提醒",
                                "今日，您已随访"+ls.getYsfs()+"人，还需随访"+ls.getWsfs()+"人",
                                NoticesType.SFTX.getValue(), "系统提醒", ls.getDrId(), ls.getId(), DrPatientType.DR.getValue());
                    }
                }
            }
        }
    }
    /**
     * 药品存量预警
     */
    public void drugRunOutToDr(String userId)throws Exception{
        AppDrUser drUser = (AppDrUser)sysDao.getServiceDo().find(AppDrUser.class,userId);
        if(drUser!=null){
            List<AppDrugGuide> ls = sysDao.getAppDrugGuideDao().findGuidByTime(drUser.getId());
            if(ls!=null && ls.size()>0){
                for (AppDrugGuide guide:ls) {
                    double warnNum = 10;//默认10次
                    if(guide.getDgWarnNum()!=null){
                        warnNum = Double.parseDouble(guide.getDgWarnNum());
                    }else if(guide.getDgCommonWarnNum()!=null){
                        warnNum = Double.parseDouble(guide.getDgCommonWarnNum());
                    }
                    int leftday = ExtendDate.getDayNumBetween2Date(Calendar.getInstance(),guide.getDgEndTime());
                    double freq = Double.parseDouble(guide.getDgFrequency());//3
                    if((leftday>0&&leftday*freq < warnNum)||(leftday==0&&freq<warnNum)){
                        AppDrug drug = (AppDrug) sysDao.getServiceDo().find(AppDrug.class,guide.getDgDrugId());
                        AppPatientUser patient = (AppPatientUser) sysDao.getServiceDo().find(AppPatientUser.class,guide.getDgPatientId());
                        int num=0;
                        if(leftday!=0){
                            num= (new Double(leftday*freq)).intValue();
                        }else if(freq>=1){
                            num= (new Double(freq)).intValue();

                        }
                        if(drug!=null&&patient!=null){
                            String title = drug.getDrugName()+",剩余次数："+num;
                            String content1="您的"+drug.getStrDrugType()+":"+drug.getDrugName()+" 可使用次数低于"+(new Double(warnNum)).intValue()+"次，请及时就诊";
                            String content2= patient.getPatientName()+"的"+drug.getStrDrugType()+":"+drug.getDrugName()+" 可使用次数低于"+String.valueOf((int)warnNum)+"次，请及时就诊";
                            //发给患者
                            List<AppWarningSetting> setList = sysDao.getAppWarningSettingDao().findSetting(guide.getDgPatientId(), CommonWarnSet.YYDQ.getValue());
                            if(!setList.isEmpty()&&setList.get(0).getWsState().equals(CommonEnable.QIYONG.getValue())||setList.isEmpty()){
                                sysDao.getAppNoticeDao().addNotices(title,content1, NoticesType.YYDQJJ.getValue(),"系统提醒",guide.getDgPatientId(),guide.getId(),DrPatientType.PATIENT.getValue());
                            }
                            //发给医生
                            sysDao.getAppNoticeDao().addNotices(title,content2, NoticesType.YYDQJJ.getValue(),"系统提醒",guide.getDgDocId(),guide.getId(),DrPatientType.DR.getValue());
                            AppDrugGuide vo = (AppDrugGuide)sysDao.getServiceDo().find(AppDrugGuide.class,guide.getId());
                            if(vo != null){
                                vo.setDgSendState("1");//状态1为已发送
                                sysDao.getServiceDo().modify(vo);
                            }
                        }
                    }
                }
            }
        }
    }
    /**
     * 居民体征数据未更新消息数据
     */
    public void findTzyjToDr(AppDrUser drUser)throws Exception{
        if(drUser!=null){
            List<AppDrTzyjEntity> ls = sysDao.getAppSignsWarningSettingDao().findTzyjList(drUser.getId());
            if(ls!=null && ls.size()>0){
                //编号
                String code="";
                AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
                if(dept!=null){
                    if(StringUtils.isNotBlank(dept.getHospAreaCode())) {
                        AppSerial serial = sysDao.getAppSignformDao().getAppSerial(dept.getHospAreaCode().substring(0, 4), "tzyj");
                        Map<String,Object> bcnum = new HashMap<>();
                        if (serial != null) {
                            bcnum = sysDao.getAppSignformDao().getNum(serial.getSerialNo(),SignFormType.APPSTATE.getValue());
                            serial.setSerialNo(bcnum.get("old").toString());
                            sysDao.getServiceDo().modify(serial);
                            code = bcnum.get("new").toString();
                        }
                    }
                }
                String num=String.valueOf(ls.size());
                for(AppDrTzyjEntity v:ls){
                    AppSignsWarningRecord tt = sysDao.getAppSignsWarningSettingDao().findOne(v.getDrId(),v.getPatientId(),v.getDisType());
                    if(tt!=null){
                        tt.setSwrDayNum(v.getDayNum());
                        sysDao.getServiceDo().modify(tt);
                    }else{
                        AppSignsWarningRecord table = new AppSignsWarningRecord();
                        table.setSwrColor(v.getColor());
                        table.setSwrDayNum(v.getDayNum());
                        table.setSwrDisType(v.getDisType());
                        table.setSwrDrId(v.getDrId());
                        table.setSwrTxState("0");
                        table.setSwrUserId(v.getPatientId());
                        table.setSwrCreateTime(Calendar.getInstance());
                        table.setSwrTeamId(v.getTeamId());
                        sysDao.getServiceDo().add(table);

                    }
                   /* AppNotice notice = new AppNotice();
                    notice.setNoticeTitle("体征指标预警");
                    notice.setNoticeType(NoticesType.TZZSYJ.getValue());
                    notice.setNoticeCreatePeople("系统提醒");
                    notice.setNoticeReceivePeople(v.getDrId());
                    notice.setNoticeCreateTime(Calendar.getInstance());
                    notice.setNoticeRead(NoticesReadType.WEIDU.getValue());*/
                    AppPatientUser user =(AppPatientUser) sysDao.getServiceDo().find(AppPatientUser.class,v.getPatientId());
                    String userName = "";
                    String colorName = "";
                    String disTypeName = "";
                    if(user!=null){
                        userName = user.getPatientName();
                        if("red".equals(v.getColor())){
                            colorName = "红标";
                        }else if("yellow".equals(v.getColor())){
                            colorName = "黄标";
                        }else if("green".equals(v.getColor())){
                            colorName = "绿标";
                        }
                        if("201".equals(v.getDisType())){
                            disTypeName = "高血压";
                        }else if("202".equals(v.getDisType())){
                            disTypeName = "糖尿病";
                        }
//                        notice.setNoticeForeign(user.getId());
                    }
//                    notice.setNoticeMtype("1");
                    String content = userName+"("+disTypeName+colorName+"),已连续"+v.getDayNum()+"天未测量，请及时处理。";
//                    notice.setNoticeContent(content);
                    AppSignsRecordTable table = new AppSignsRecordTable();
                    table.setSrtDisType(v.getDisType());
                    AppLabelManage labelManage = sysDao.getAppLabelManageDao().findLabelByValue("2",v.getDisType());
                    if(labelManage!=null){
                        table.setSrtDisName(labelManage.getLabelTitle());
                    }
                    table.setSrtDayNum(v.getDayNum());
                    table.setSrtCode(code);
                    table.setSrtContent(content);
                    table.setSrtCreateTime(Calendar.getInstance());
                    table.setSrtPatientId(user.getId());
                    table.setSrtDrId(v.getDrId());
                    table.setSrtTeamId(v.getTeamId());
                    table.setSrtState("0");
                    sysDao.getServiceDo().add(table);
                }
                sysDao.getAppNoticeDao().addNotices("体征指标预警", "您好，您有"+num+"个患者在规定了时间没有测量体征，请及时处理。",
                        NoticesType.TZZSYJ.getValue()+","+"1", "系统提醒", drUser.getId(), code, DrPatientType.DR.getValue());
            }
        }

    }

    /**
     * 健康教育履约提醒
     * @param drUser
     * @param lyNum
     * @param type
     * @return
     */
    public List<AppPerformanceRecordEntity> healthEdution(AppDrUser drUser,String lyNum,String type)throws Exception{
        List<AppPerformanceRecordEntity> tts = new ArrayList<>();
        if(drUser!=null){
            AppCommLyQvo qvo = new AppCommLyQvo();
            AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
            String areaCode = "";
            if(dept!=null){
                if(StringUtils.isNotBlank(dept.getHospAreaCode())) {
                    areaCode = dept.getHospAreaCode().substring(0, 4) + "00000000";
                }
            }
            qvo.setDrId(drUser.getId());
            qvo.setPerType(PerformanceType.JKJY.getValue());
            qvo.setHospId(drUser.getDrHospId());
            qvo.setAreaCode(areaCode);
            if("1".equals(type)){
                if("0".equals(drUser.getDrTxState())){
                    List<AppDrSignPeoleListEntity> ls=sysDao.getAppSignformDao().findSignFormList(qvo);
                    if(ls!=null && ls.size()>0){
                        int num = 0;
                        for(AppDrSignPeoleListEntity ll:ls){
                            if(StringUtils.isNotBlank(ll.getFwbValue())){
                                qvo.setPatientIdno(ll.getPatientIdno());
                                //健康教育履约提醒
                                int count = Integer.parseInt(ll.getSignCount());//已完成数
                                int shouNum=0;
                                Calendar cal = Calendar.getInstance();
                                int mounth = cal.get(Calendar.MONTH)+1;
                                if(StringUtils.isNotBlank(ll.getSerSpace())){
                                    shouNum = Integer.parseInt(ll.getSerSpace())*mounth;
                                }
                                if(count<shouNum){
                                    num++;
                                    AppPerformanceRecord table = new AppPerformanceRecord();
                                    table.setAprPatientId(ll.getPatientId());
                                    table.setAprSignId(ll.getSignId());
                                    table.setAprPcNum(lyNum);
                                    table.setAprCreateTime(Calendar.getInstance());
                                    table.setAprDrId(drUser.getId());
                                    table.setAprType(PerType.JKJY.getValue());
                                    sysDao.getServiceDo().add(table);
                                }
                            }
                        }
                        if(num!=0){
                            Calendar cal = Calendar.getInstance();
                            String time = ExtendDate.getYMD(cal);
                            sysDao.getAppNoticeDao().addNotices("健康教育履约提醒","截止"+time+" 您签约居民中有"+num+"人未完成健康教育任务。",
                                    NoticesType.GZJHTX.getValue()+","+PerType.JKJY.getValue(),"系统消息",drUser.getId(),lyNum,DrPatientType.DR.getValue());
                        }
                    }
                }
            }else{
                List<AppDrSignPeoleListEntity> ls=sysDao.getAppSignformDao().findSignFormList(qvo);
                if(ls!=null && ls.size()>0){
                    for(AppDrSignPeoleListEntity ll:ls){
                        if(StringUtils.isNotBlank(ll.getFwbValue())){
                            qvo.setPatientIdno(ll.getPatientIdno());
                            //健康教育履约提醒
                            int count = Integer.parseInt(ll.getSignCount());//已完成数
                            int shouNum=0;
                            Calendar cal = Calendar.getInstance();
                            int mounth = cal.get(Calendar.MONTH)+1;
                            if(StringUtils.isNotBlank(ll.getSerSpace())){
                                shouNum = Integer.parseInt(ll.getSerSpace())*mounth;
                            }
                            if(count<shouNum){
                                AppPatientUser user = sysDao.getAppPatientUserDao().findByUserId(ll.getPatientId());
                                if(user!=null){
                                    AppPerformanceRecordEntity tt = new AppPerformanceRecordEntity();
                                    tt.setPatientId(user.getId());
                                    tt.setSignId(ll.getSignId());
                                    tt.setPatientAge(user.getPatientAge());
                                    tt.setPatientGender(user.getPatientGender());
                                    tt.setPatientIdNo(user.getPatientIdno());
                                    tt.setPatientImageName(user.getPatientImageName());
                                    tt.setPatientImageUrl(user.getPatientImageurl());
                                    tt.setPatientName(user.getPatientName());
                                    tt.setFwType("1");
                                    tts.add(tt);
                                }
                            }
                        }
                    }
                }
            }
        }
        return tts;
    }

    /**
     * 健康指导履约提醒
     * @param drUser
     * @param lyNum
     * @param type
     */
    public List<AppPerformanceRecordEntity> healthGuide(AppDrUser drUser,String lyNum,String type)throws Exception{
        List<AppPerformanceRecordEntity> tts = new ArrayList<>();
        if(drUser!=null) {
            AppCommLyQvo qvo = new AppCommLyQvo();
            AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
            String areaCode = "";
            if(dept!=null){
                if(StringUtils.isNotBlank(dept.getHospAreaCode())) {
                    areaCode = dept.getHospAreaCode().substring(0, 4) + "00000000";
                }
            }
            qvo.setAreaCode(areaCode);
            qvo.setHospId(drUser.getDrHospId());
            qvo.setDrId(drUser.getId());
            qvo.setPerType(PerformanceType.JKZD.getValue());
            if("1".equals(type)){
                if ("0".equals(drUser.getDrTxState())) {
                    List<AppDrSignPeoleListEntity> ls = sysDao.getAppSignformDao().findSignFormList(qvo);
                    if (ls != null && ls.size() > 0) {
                        int num = 0;
                        for (AppDrSignPeoleListEntity ll : ls) {
                            if (StringUtils.isNotBlank(ll.getFwbValue())) {
                                qvo.setPatientIdno(ll.getPatientIdno());
                                //健康指导履约提醒
                                int count = Integer.parseInt(ll.getSignCount());//已完成数
                                int shouNum=0;
                                Calendar cal = Calendar.getInstance();
                                int mounth = cal.get(Calendar.MONTH)+1;
                                if(StringUtils.isNotBlank(ll.getSerSpace())){
                                    shouNum = Integer.parseInt(ll.getSerSpace())*mounth;
                                }
                                if(count<shouNum){
                                    num++;
                                    AppPerformanceRecord table = new AppPerformanceRecord();
                                    table.setAprPatientId(ll.getPatientId());
                                    table.setAprPcNum(lyNum);
                                    table.setAprSignId(ll.getSignId());
                                    table.setAprCreateTime(Calendar.getInstance());
                                    table.setAprDrId(drUser.getId());
                                    table.setAprType(PerType.JKZD.getValue());
                                    sysDao.getServiceDo().add(table);
                                }
//                            }

                            }
                        }
                        if(num!=0){
                            Calendar cal = Calendar.getInstance();
                            String time = ExtendDate.getYMD(cal);
                            sysDao.getAppNoticeDao().addNotices("健康指导履约提醒","截止"+time+" 您签约居民中有"+num+"人未完成健康指导任务。",
                                    NoticesType.GZJHTX.getValue()+","+PerType.JKZD.getValue(),"系统消息",drUser.getId(),lyNum,DrPatientType.DR.getValue());
                        }
                    }
                }

            }else{
                List<AppDrSignPeoleListEntity> ls = sysDao.getAppSignformDao().findSignFormList(qvo);
                if (ls != null && ls.size() > 0) {
                    for (AppDrSignPeoleListEntity ll : ls) {
                        if (StringUtils.isNotBlank(ll.getFwbValue())) {
                            qvo.setPatientIdno(ll.getPatientIdno());
                            //健康指导履约提醒
                            int count = Integer.parseInt(ll.getSignCount());//已完成数
                            int shouNum=0;
                            Calendar cal = Calendar.getInstance();
                            int mounth = cal.get(Calendar.MONTH)+1;
                            if(StringUtils.isNotBlank(ll.getSerSpace())){
                                shouNum = Integer.parseInt(ll.getSerSpace())*mounth;
                            }
                            if(count<shouNum){
                                AppPatientUser user = sysDao.getAppPatientUserDao().findByUserId(ll.getPatientId());
                                if(user!=null){
                                    AppPerformanceRecordEntity tt = new AppPerformanceRecordEntity();
                                    tt.setPatientId(user.getId());
                                    tt.setSignId(ll.getSignId());
                                    tt.setPatientAge(user.getPatientAge());
                                    tt.setPatientGender(user.getPatientGender());
                                    tt.setPatientIdNo(user.getPatientIdno());
                                    tt.setPatientImageName(user.getPatientImageName());
                                    tt.setPatientImageUrl(user.getPatientImageurl());
                                    tt.setPatientName(user.getPatientName());
                                    tt.setFwType("1");
                                    tts.add(tt);
                                }
                            }
                        }
                    }
                }
            }
        }
        return tts;
    }

    /**
     * 新生儿家庭访视履约提醒
     * @param drUser
     * @param lyNum
     * @param type
     */
    public List<AppPerformanceRecordEntity> newChildVisit(AppDrUser drUser,String lyNum,String type)throws Exception{
        AppCommLyQvo qvo = new AppCommLyQvo();
        List<AppPerformanceRecordEntity> tts = new ArrayList<>();
        if(drUser!=null) {
            AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
            String areaCode = "";
            if(dept!=null){
                if(StringUtils.isNotBlank(dept.getHospAreaCode())) {
                    areaCode = dept.getHospAreaCode().substring(0, 4) + "00000000";
                }
            }
            qvo.setAreaCode(areaCode);
            qvo.setHospId(drUser.getDrHospId());
            qvo.setDrId(drUser.getId());
            //查询新生儿家庭访视记录表(做过访视的人员)
            qvo.setFollowType("1");
            qvo.setFwType(ResidentMangeType.ETLZLS.getValue());
            if("1".equals(type)){
                if ("0".equals(drUser.getDrTxState())) {
                    List<AppDrSignPeoleListEntity> ls = sysDao.getAppSignformDao().findSignFormList(qvo);
                    if(ls!=null && ls.size()>0){
                        int num = 0;
                        for(AppDrSignPeoleListEntity ll:ls){
                            if(Integer.parseInt(ll.getSignCount())<1){
                                num++;
                                AppPerformanceRecord table = new AppPerformanceRecord();
                                table.setAprPatientId(ll.getPatientId());
                                table.setAprSignId(ll.getSignId());
                                table.setAprPcNum(lyNum);
                                table.setAprCreateTime(Calendar.getInstance());
                                table.setAprDrId(drUser.getId());
                                table.setAprType(PerType.XSEFS.getValue());
                                sysDao.getServiceDo().add(table);

                            }
                        }
                        if(num!=0){
                            Calendar cal1 = Calendar.getInstance();
                            String time = ExtendDate.getYMD(cal1);
                            sysDao.getAppNoticeDao().addNotices("新生儿家庭访视履约提醒","截止"+time+" 您签约居民中有"+num+"人需进行新生儿家庭访视。",
                                    NoticesType.GZJHTX.getValue()+","+PerType.XSEFS.getValue(),"系统消息",drUser.getId(),lyNum,DrPatientType.DR.getValue());
                        }
                    }
                }

            }else{
                List<AppDrSignPeoleListEntity> ls = sysDao.getAppSignformDao().findSignFormList(qvo);
                if(ls!=null && ls.size()>0){
                    for(AppDrSignPeoleListEntity ll:ls){
                        if(Integer.parseInt(ll.getSignCount())<1){
                            AppPatientUser user = sysDao.getAppPatientUserDao().findByUserId(ll.getPatientId());
                            if(user!=null){
                                AppPerformanceRecordEntity tt = new AppPerformanceRecordEntity();
                                tt.setPatientId(user.getId());
                                tt.setSignId(ll.getSignId());
                                tt.setPatientGender(user.getPatientGender());
                                tt.setPatientIdNo(user.getPatientIdno());
                                tt.setPatientAge(user.getPatientAge());
                                tt.setPatientImageName(user.getPatientImageName());
                                tt.setPatientImageUrl(user.getPatientImageurl());
                                tt.setPatientName(user.getPatientName());
                                tt.setFwType("1");
                                tts.add(tt);
                            }
                        }
                    }
                }
            }

        }
        return tts;
    }

    /**
     * 0-6岁儿童健康检查履约提醒
     * @param drUser
     * @param lyNum
     * @param type
     */
    public  List<AppPerformanceRecordEntity> childHealth(AppDrUser drUser,String lyNum,String type)throws Exception{
        AppCommLyQvo qvo = new AppCommLyQvo();
        List<AppPerformanceRecordEntity> tts = new ArrayList<>();
        if(drUser!=null){
            qvo.setDrId(drUser.getId());
            qvo.setFollowType("2");
            if("1".equals(type)){
                if ("0".equals(drUser.getDrTxState())) {
                    List<AppChildLyEntity> list = sysDao.getAppPerformanceRegularFollowupDao().findChildHealth(qvo);
                    if(list!=null && list.size()>0){
                        int num=0;
                        for(AppChildLyEntity ll:list){
                            if(!"0".equals(ll.getCountTj())){
                                if(Integer.parseInt(ll.getCount())<Integer.parseInt(ll.getCountTj())){
                                    num++;
                                    AppPerformanceRecord table = new AppPerformanceRecord();
                                    table.setAprPatientId(ll.getPatientId());
                                    table.setAprPcNum(lyNum);
                                    table.setAprSignId(ll.getSignId());
                                    table.setAprCreateTime(Calendar.getInstance());
                                    table.setAprDrId(drUser.getId());
                                    table.setAprType(PerType.ETJKTJ.getValue());
                                    sysDao.getServiceDo().add(table);
                                }
                            }
                        }
                        if(num!=0){
                            Calendar cal1 = Calendar.getInstance();
                            String time = ExtendDate.getYMD(cal1);//截止2017-11-5有10个儿童未及时进行儿童健康检查，其中1人为您的签约居民。
                            sysDao.getAppNoticeDao().addNotices("0-6岁儿童健康检查","截止"+time+" 您签约居民中有"+num+"个儿童未及时进行儿童健康检查。",
                                    NoticesType.GZJHTX.getValue()+","+PerType.ETJKTJ.getValue(),"系统消息",drUser.getId(),lyNum,DrPatientType.DR.getValue());
                        }
                    }
                }
            }else{
                List<AppChildLyEntity> list = sysDao.getAppPerformanceRegularFollowupDao().findChildHealth(qvo);
                if(list!=null && list.size()>0){
                    for(AppChildLyEntity ll:list){
                        if(!"0".equals(ll.getCountTj())){
                            if(Integer.parseInt(ll.getCount())<Integer.parseInt(ll.getCountTj())){
                                AppPatientUser user = sysDao.getAppPatientUserDao().findByUserId(ll.getPatientId());
                                if(user!=null){
                                    AppPerformanceRecordEntity tt = new AppPerformanceRecordEntity();
                                    tt.setPatientId(user.getId());
                                    tt.setSignId(ll.getSignId());
                                    tt.setPatientAge(user.getPatientAge());
                                    tt.setPatientGender(user.getPatientGender());
                                    tt.setPatientIdNo(user.getPatientIdno());
                                    tt.setPatientImageName(user.getPatientImageName());
                                    tt.setPatientImageUrl(user.getPatientImageurl());
                                    tt.setPatientName(user.getPatientName());
                                    tt.setFwType("1");
                                    tts.add(tt);
                                }
                            }
                        }
                    }
                }
            }
        }
        return tts;
    }

    /**
     * 产后访视履约提醒
     * @param drUser
     * @param lyNum
     * @param type
     */
    public List<AppPerformanceRecordEntity> postpartumVisit(AppDrUser drUser,String lyNum,String type)throws Exception{
        AppCommLyQvo qvo = new AppCommLyQvo();
        List<AppPerformanceRecordEntity> tts = new ArrayList<>();
        if(drUser != null){
            qvo.setDrId(drUser.getId());
            qvo.setFwType(ResidentMangeType.YCF.getValue());
            if("1".equals(type)){
                if("0".equals(drUser.getDrTxState())){
                    List<AppLyTxEntity> list = sysDao.getAppPerformanceRegularFollowupDao().findPostPartumList(qvo);
                    if(list!=null && list.size()>0){
                        int num =0;
                        for(AppLyTxEntity ll:list){
                            if(Integer.parseInt(ll.getCount())<1){
                                num++;
                                AppPerformanceRecord table = new AppPerformanceRecord();
                                table.setAprPatientId(ll.getPatientId());
                                table.setAprPcNum(lyNum);
                                table.setAprSignId(ll.getSignId());
                                table.setAprCreateTime(Calendar.getInstance());
                                table.setAprDrId(drUser.getId());
                                table.setAprType(PerType.CHFS.getValue());
                                sysDao.getServiceDo().add(table);
                            }
                        }
                        if(num!=0){
                            Calendar cal1 = Calendar.getInstance();
                            String time = ExtendDate.getYMD(cal1);//截止2017-11-5有10个孕产妇需进行产后视访，其中1人为您的签约居民。
                            sysDao.getAppNoticeDao().addNotices("产后视访履约提醒","截止"+time+" 您签约居民中有"+num+"个孕产妇需进行产后视访。",
                                    NoticesType.GZJHTX.getValue()+","+PerType.CHFS.getValue(),"系统消息",drUser.getId(),lyNum,DrPatientType.DR.getValue());
                        }
                    }
                }
            }else{
                List<AppLyTxEntity> list = sysDao.getAppPerformanceRegularFollowupDao().findPostPartumList(qvo);
                if(list!=null && list.size()>0){
                    for(AppLyTxEntity ll:list){
                        if(Integer.parseInt(ll.getCount())<1){
                            AppPatientUser user =sysDao.getAppPatientUserDao().findByUserId(ll.getPatientId());
                            if(user!=null){
                                AppPerformanceRecordEntity tt = new AppPerformanceRecordEntity();
                                tt.setPatientId(user.getId());
                                tt.setSignId(ll.getSignId());
                                tt.setPatientAge(user.getPatientAge());
                                tt.setPatientGender(user.getPatientGender());
                                tt.setPatientIdNo(user.getPatientIdno());
                                tt.setPatientImageName(user.getPatientImageName());
                                tt.setPatientImageUrl(user.getPatientImageurl());
                                tt.setPatientName(user.getPatientName());
                                tt.setFwType("1");
                                tts.add(tt);
                            }
                        }
                    }
                }
            }
        }
        return tts;
    }

    /**
     * 用药指导履约提醒
     * @param drUser
     * @param lyNum
     * @param type
     */
    public  List<AppPerformanceRecordEntity> guidelines(AppDrUser drUser,String lyNum,String type)throws Exception{
        AppCommLyQvo qvo = new AppCommLyQvo();
        List<AppPerformanceRecordEntity> tts = new ArrayList<>();
        if(drUser!=null) {
            AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
            String areaCode = "";
            if(dept!=null){
                if(StringUtils.isNotBlank(dept.getHospAreaCode())) {
                    areaCode = dept.getHospAreaCode().substring(0, 4) + "00000000";
                }
            }
            qvo.setAreaCode(areaCode);
            qvo.setHospId(drUser.getDrHospId());
            qvo.setDrId(drUser.getId());
            qvo.setPerType(PerformanceType.YYZD.getValue());
            if("1".equals(type)){
                if ("0".equals(drUser.getDrTxState())) {
                    List<AppDrSignPeoleListEntity> ls = sysDao.getAppSignformDao().findSignFormList(qvo);
                    if (ls != null && ls.size() > 0) {
                        int num = 0;
                        for (AppDrSignPeoleListEntity ll : ls) {
                            if (StringUtils.isNotBlank(ll.getFwbValue())) {
                                qvo.setPatientIdno(ll.getPatientIdno());
                                //用药指导履约提醒
                                int count = Integer.parseInt(ll.getSignCount());//已完成数
                                int shouNum=0;
                                Calendar cal = Calendar.getInstance();
                                int mounth = cal.get(Calendar.MONTH)+1;
                                if(StringUtils.isNotBlank(ll.getSerSpace())){
                                    shouNum = Integer.parseInt(ll.getSerSpace())*mounth;
                                }
                                if(count<shouNum){
                                    num++;
                                    AppPerformanceRecord table = new AppPerformanceRecord();
                                    table.setAprPatientId(ll.getPatientId());
                                    table.setAprPcNum(lyNum);
                                    table.setAprSignId(ll.getSignId());
                                    table.setAprCreateTime(Calendar.getInstance());
                                    table.setAprDrId(drUser.getId());
                                    table.setAprType(PerType.YYZD.getValue());
                                    sysDao.getServiceDo().add(table);
                                }
                            }
                        }
                        if(num!=0){
                            Calendar cal = Calendar.getInstance();
                            String time = ExtendDate.getYMD(cal);
                            sysDao.getAppNoticeDao().addNotices("用药指导履约提醒","截止"+time+" 有"+num+"个居民未完成用药指导。",
                                    NoticesType.GZJHTX.getValue()+","+PerType.YYZD.getValue(),"系统消息",drUser.getId(),lyNum,DrPatientType.DR.getValue());
                        }
                    }
                }
            }else{
                List<AppDrSignPeoleListEntity> ls = sysDao.getAppSignformDao().findSignFormList(qvo);
                if (ls != null && ls.size() > 0) {
                    for (AppDrSignPeoleListEntity ll : ls) {
                        if (StringUtils.isNotBlank(ll.getFwbValue())) {
                            qvo.setPatientIdno(ll.getPatientIdno());
                            //用药指导履约提醒
                            int count = Integer.parseInt(ll.getSignCount());//已完成数
                            int shouNum=0;
                            Calendar cal = Calendar.getInstance();
                            int mounth = cal.get(Calendar.MONTH)+1;
                            if(StringUtils.isNotBlank(ll.getSerSpace())){
                                shouNum = Integer.parseInt(ll.getSerSpace())*mounth;
                            }
                            if(count<shouNum){
                                AppPatientUser user = sysDao.getAppPatientUserDao().findByUserId(ll.getPatientId());
                                if(user!=null){
                                    AppPerformanceRecordEntity tt = new AppPerformanceRecordEntity();
                                    tt.setPatientId(user.getId());
                                    tt.setSignId(ll.getSignId());
                                    tt.setPatientAge(user.getPatientAge());
                                    tt.setPatientGender(user.getPatientGender());
                                    tt.setPatientIdNo(user.getPatientIdno());
                                    tt.setPatientImageName(user.getPatientImageName());
                                    tt.setPatientImageUrl(user.getPatientImageurl());
                                    tt.setPatientName(user.getPatientName());
                                    tt.setFwType("1");
                                    tts.add(tt);
                                }
                            }
                        }
                    }
                }
            }

        }
        return tts;
    }

    /**
     * 高血压定期随访履约提醒
     * @param drUser
     * @param lyNum
     * @param type
     */
    public  List<AppPerformanceRecordEntity> HighBloodPressure(AppDrUser drUser,String lyNum,String type)throws Exception{
        AppCommQvo qvo = new AppCommQvo();
        List<AppPerformanceRecordEntity> tts = new ArrayList<>();
        if(drUser!=null) {
            qvo.setDrId(drUser.getId());
            qvo.setSignlx(ResidentMangeType.GXY.getValue());
            if("1".equals(type)){
                if ("0".equals(drUser.getDrTxState())) {
                    List<AppLyTxEntity> list = sysDao.getAppPerFormanceDao().findVisit(qvo);
                    if(list!=null && list.size()>0){
                        int num = 0;
                        //当前时间算季度
                        Calendar cal = Calendar.getInstance();
                        int month = cal.get(Calendar.MONTH)+1;
                        int jd = 0;
                        if(month<4){
                            jd = 1;
                        }else if(month<7){
                            jd = 2;
                        }else if(month<10){
                            jd = 3;
                        }else {
                            jd = 4;
                        }
                        for(AppLyTxEntity ll:list){
                            if(Integer.parseInt(ll.getCount())<jd) {
                                num++;
                                AppPerformanceRecord table = new AppPerformanceRecord();
                                table.setAprPatientId(ll.getPatientId());
                                table.setAprSignId(ll.getSignId());
                                table.setAprPcNum(lyNum);
                                table.setAprCreateTime(Calendar.getInstance());
                                table.setAprDrId(drUser.getId());
                                table.setAprType(PerType.GXYSF.getValue());
                                sysDao.getServiceDo().add(table);

                            }
                        }
                        if(num!=0){
                            String time = ExtendDate.getYMD(cal);
                            sysDao.getAppNoticeDao().addNotices("高血压履约提醒","截止"+time+"您签约居民中有"+num+"个居民需进行高血压随访服务。",
                                    NoticesType.GZJHTX.getValue()+","+PerType.GXYSF.getValue(),"系统消息",drUser.getId(),lyNum,DrPatientType.DR.getValue());
                        }
                    }
                }
            }else{
                List<AppLyTxEntity> list = sysDao.getAppPerFormanceDao().findVisit(qvo);
                if(list!=null && list.size()>0){
                    //当前时间算季度
                    Calendar cal = Calendar.getInstance();
                    int month = cal.get(Calendar.MONTH)+1;
                    int jd = 0;
                    if(month<4){
                        jd = 1;
                    }else if(month<7){
                        jd = 2;
                    }else if(month<10){
                        jd = 3;
                    }else {
                        jd = 4;
                    }
                    for(AppLyTxEntity ll:list){
                        if(Integer.parseInt(ll.getCount())<jd) {
                            AppPatientUser user = sysDao.getAppPatientUserDao().findByUserId(ll.getPatientId());
                            if(user!=null){
                                AppPerformanceRecordEntity tt = new AppPerformanceRecordEntity();
                                tt.setPatientId(user.getId());
                                tt.setSignId(ll.getSignId());
                                tt.setPatientAge(user.getPatientAge());
                                tt.setPatientGender(user.getPatientGender());
                                tt.setPatientIdNo(user.getPatientIdno());
                                tt.setPatientImageName(user.getPatientImageName());
                                tt.setPatientImageUrl(user.getPatientImageurl());
                                tt.setPatientName(user.getPatientName());
                                tt.setFwType("1");
                                tts.add(tt);
                            }
                        }
                    }
                }
            }
        }
        return tts;
    }

    /**
     * 糖尿病履约提醒
     * @param drUser
     * @param lyNum
     * @param type
     */
    public List<AppPerformanceRecordEntity> diabetes(AppDrUser drUser,String lyNum,String type)throws Exception{
        AppCommQvo qvo = new AppCommQvo();
        List<AppPerformanceRecordEntity> tts = new ArrayList<>();
        if(drUser!=null) {
            qvo.setDrId(drUser.getId());
            qvo.setSignlx(ResidentMangeType.TNB.getValue());
            if("1".equals(type)){
                if ("0".equals(drUser.getDrTxState())) {
                    List<AppLyTxEntity> list = sysDao.getAppPerFormanceDao().findVisit(qvo);
                    if(list!=null && list.size()>0){
                        int num = 0;
                        Calendar cal = Calendar.getInstance();
                        int month = cal.get(Calendar.MONTH)+1;
                        int jd = 0;
                        if(month<4){
                            jd = 1;
                        }else if(month<7){
                            jd = 2;
                        }else if(month<10){
                            jd = 3;
                        }else {
                            jd = 4;
                        }
                        for(AppLyTxEntity ll:list){
                            //当前时间算季度
                            if(Integer.parseInt(ll.getCount())<jd) {
                                num++;
                                AppPerformanceRecord table = new AppPerformanceRecord();
                                table.setAprPatientId(ll.getPatientId());
                                table.setAprSignId(ll.getSignId());
                                table.setAprPcNum(lyNum);
                                table.setAprCreateTime(Calendar.getInstance());
                                table.setAprDrId(drUser.getId());
                                table.setAprType(PerType.TNBSF.getValue());
                                sysDao.getServiceDo().add(table);

                            }
                        }
                        if(num!=0){
                            String time = ExtendDate.getYMD(cal);
                            sysDao.getAppNoticeDao().addNotices("糖尿病履约提醒","截止"+time+"您签约居民中有"+num+"个居民需进行糖尿病随访服务。",
                                    NoticesType.GZJHTX.getValue()+","+PerType.TNBSF.getValue(),"系统消息",drUser.getId(),lyNum,DrPatientType.DR.getValue());
                        }
                    }
                }

            }else{
                List<AppLyTxEntity> list = sysDao.getAppPerFormanceDao().findVisit(qvo);
                if(list!=null && list.size()>0){
                    Calendar cal = Calendar.getInstance();
                    int month = cal.get(Calendar.MONTH)+1;
                    int jd = 0;
                    if(month<4){
                        jd = 1;
                    }else if(month<7){
                        jd = 2;
                    }else if(month<10){
                        jd = 3;
                    }else {
                        jd = 4;
                    }
                    for(AppLyTxEntity ll:list){
                        //当前时间算季度
                        if(Integer.parseInt(ll.getCount())<jd) {
                            AppPatientUser user = sysDao.getAppPatientUserDao().findByUserId(ll.getPatientId());
                            if(user!=null){
                                AppPerformanceRecordEntity tt = new AppPerformanceRecordEntity();
                                tt.setPatientId(user.getId());
                                tt.setSignId(ll.getSignId());
                                tt.setPatientAge(user.getPatientAge());
                                tt.setPatientGender(user.getPatientGender());
                                tt.setPatientIdNo(user.getPatientIdno());
                                tt.setPatientImageName(user.getPatientImageName());
                                tt.setPatientImageUrl(user.getPatientImageurl());
                                tt.setPatientName(user.getPatientName());
                                tt.setFwType("1");
                                tts.add(tt);
                            }
                        }
                    }
                }
            }
        }
        return tts;
    }

    /**
     * 重性精神病履约提醒
     * @param drUser
     * @param lyNum
     * @param type
     */
    public List<AppPerformanceRecordEntity> MentalIllness(AppDrUser drUser,String lyNum,String type)throws Exception{
        AppCommQvo qvo = new AppCommQvo();
        List<AppPerformanceRecordEntity> tts = new ArrayList<>();
        if(drUser!=null) {
            qvo.setDrId(drUser.getId());
            qvo.setSignlx(ResidentMangeType.YZJSZY.getValue());
            if("1".equals(type)){
                if ("0".equals(drUser.getDrTxState())) {
                    List<AppLyTxEntity> list = sysDao.getAppPerFormanceDao().findVisit(qvo);
                    if(list!=null && list.size()>0){
                        int num = 0;
                        //当前时间算季度
                        Calendar cal = Calendar.getInstance();
                        int month = cal.get(Calendar.MONTH)+1;
                        int jd = 0;
                        if(month<4){
                            jd = 1;
                        }else if(month<7){
                            jd = 2;
                        }else if(month<10){
                            jd = 3;
                        }else {
                            jd = 4;
                        }
                        for(AppLyTxEntity ll:list){
                            if(Integer.parseInt(ll.getCount())<jd) {
                                num++;
                                AppPerformanceRecord table = new AppPerformanceRecord();
                                table.setAprPatientId(ll.getPatientId());
                                table.setAprSignId(ll.getSignId());
                                table.setAprPcNum(lyNum);
                                table.setAprCreateTime(Calendar.getInstance());
                                table.setAprDrId(drUser.getId());
                                table.setAprType(PerType.ZXJSBSF.getValue());
                                sysDao.getServiceDo().add(table);

                            }
                        }
                        if(num!=0){
                            String time = ExtendDate.getYMD(cal);
                            sysDao.getAppNoticeDao().addNotices("重性精神疾病履约提醒","截止"+time+"您签约居民中有"+num+"个居民需进行重性精神疾病随访服务。",
                                    NoticesType.GZJHTX.getValue()+","+PerType.ZXJSBSF.getValue(),"系统消息",drUser.getId(),lyNum,DrPatientType.DR.getValue());
                        }
                    }
                }
            }else{
                List<AppLyTxEntity> list = sysDao.getAppPerFormanceDao().findVisit(qvo);
                if(list!=null && list.size()>0){
                    //当前时间算季度
                    Calendar cal = Calendar.getInstance();
                    int month = cal.get(Calendar.MONTH)+1;
                    int jd = 0;
                    if(month<4){
                        jd = 1;
                    }else if(month<7){
                        jd = 2;
                    }else if(month<10){
                        jd = 3;
                    }else {
                        jd = 4;
                    }
                    for(AppLyTxEntity ll:list){
                        if(Integer.parseInt(ll.getCount())<jd) {
                            AppPatientUser user = sysDao.getAppPatientUserDao().findByUserId(ll.getPatientId());
                            if(user!=null){
                                AppPerformanceRecordEntity tt = new AppPerformanceRecordEntity();
                                tt.setPatientId(user.getId());
                                tt.setSignId(ll.getSignId());
                                tt.setPatientAge(user.getPatientAge());
                                tt.setPatientGender(user.getPatientGender());
                                tt.setPatientIdNo(user.getPatientIdno());
                                tt.setPatientImageName(user.getPatientImageName());
                                tt.setPatientImageUrl(user.getPatientImageurl());
                                tt.setPatientName(user.getPatientName());
                                tt.setFwType("1");
                                tts.add(tt);
                            }
                        }
                    }
                }
            }

        }
        return tts;
    }

    /**
     * 肺结核随访履约提醒
     * @param drUser
     * @param lyNum
     * @param type
     */
    public List<AppPerformanceRecordEntity> tuberculosis(AppDrUser drUser,String lyNum,String type)throws Exception{
        AppCommQvo qvo = new AppCommQvo();
        List<AppPerformanceRecordEntity> tts = new ArrayList<>();
        if(drUser!=null) {
            qvo.setDrId(drUser.getId());
            qvo.setSignlx(ResidentMangeType.JHB.getValue());
            if("1".equals(type)){
                if ("0".equals(drUser.getDrTxState())) {
                    List<AppLyTxEntity> list = sysDao.getAppPerFormanceDao().findVisit(qvo);
                    if(list!=null && list.size()>0){
                        int num = 0;
                        //当前时间算季度
                        Calendar cal = Calendar.getInstance();
                        int month = cal.get(Calendar.MONTH)+1;
                        int jd = 0;
                        if(month<4){
                            jd = 1;
                        }else if(month<7){
                            jd = 2;
                        }else if(month<10){
                            jd = 3;
                        }else {
                            jd = 4;
                        }
                        for(AppLyTxEntity ll:list){
                            if(Integer.parseInt(ll.getCount())<jd) {
                                num++;
                                AppPerformanceRecord table = new AppPerformanceRecord();
                                table.setAprPatientId(ll.getPatientId());
                                table.setAprSignId(ll.getSignId());
                                table.setAprPcNum(lyNum);
                                table.setAprCreateTime(Calendar.getInstance());
                                table.setAprDrId(drUser.getId());
                                table.setAprType(PerType.JHBSF.getValue());
                                sysDao.getServiceDo().add(table);
                            }
                        }
                        if(num!=0){
                            String time = ExtendDate.getYMD(cal);
                            sysDao.getAppNoticeDao().addNotices("肺结核病履约提醒","截止"+time+"您签约居民中有"+num+"个居民需进行肺结核病随访服务。",
                                    NoticesType.GZJHTX.getValue()+","+PerType.JHBSF.getValue(),"系统消息",drUser.getId(),lyNum,DrPatientType.DR.getValue());
                        }
                    }
                }
            }else{
                List<AppLyTxEntity> list = sysDao.getAppPerFormanceDao().findVisit(qvo);
                if(list!=null && list.size()>0){
                    //当前时间算季度
                    Calendar cal = Calendar.getInstance();
                    int month = cal.get(Calendar.MONTH)+1;
                    int jd = 0;
                    if(month<4){
                        jd = 1;
                    }else if(month<7){
                        jd = 2;
                    }else if(month<10){
                        jd = 3;
                    }else {
                        jd = 4;
                    }
                    for(AppLyTxEntity ll:list){
                        if(Integer.parseInt(ll.getCount())<jd) {
                            AppPatientUser user = sysDao.getAppPatientUserDao().findByUserId(ll.getPatientId());
                            if(user!=null){
                                AppPerformanceRecordEntity tt = new AppPerformanceRecordEntity();
                                tt.setPatientId(user.getId());
                                tt.setSignId(ll.getSignId());
                                tt.setPatientAge(user.getPatientAge());
                                tt.setPatientGender(user.getPatientGender());
                                tt.setPatientIdNo(user.getPatientIdno());
                                tt.setPatientImageName(user.getPatientImageName());
                                tt.setPatientImageUrl(user.getPatientImageurl());
                                tt.setPatientName(user.getPatientName());
                                tt.setFwType("1");
                                tts.add(tt);
                            }
                        }
                    }
                }
            }
        }
        return tts;
    }

    /**
     * 中医药健康指导(0-36月龄)履约提醒
     * @param drUser
     * @param lyNum
     * @param type
     */
    public  List<AppPerformanceRecordEntity> tcmHealthGuide(AppDrUser drUser,String lyNum,String type)throws Exception{
        AppCommQvo qvo = new AppCommQvo();
        List<AppPerformanceRecordEntity> tts = new ArrayList<>();
        if(drUser!=null){
            qvo.setDrId(drUser.getId());
            if("1".equals(type)){
                if("0".equals(drUser.getDrTxState())){
                    List<AppTcmLyEntity> list = sysDao.getAppPerFormanceDao().findTcmLy(qvo);
                    if(list!=null && list.size()>0){
                        int num = 0;
                        for(AppTcmLyEntity ll:list){
                            if(!"0".equals(ll.getCountTj())){
                                if(Integer.parseInt(ll.getCountGuide())<Integer.parseInt(ll.getCountTj())){
                                    num++;
                                    AppPerformanceRecord table = new AppPerformanceRecord();
                                    table.setAprPatientId(ll.getPatientId());
                                    table.setAprSignId(ll.getSignId());
                                    table.setAprPcNum(lyNum);
                                    table.setAprCreateTime(Calendar.getInstance());
                                    table.setAprDrId(drUser.getId());
                                    table.setAprType(PerType.ZYTZBS.getValue());
                                    sysDao.getServiceDo().add(table);

                                }
                            }
                        }
                        if(num != 0){//截止2017-11-5有10个儿童未进行中医药健康指导，其中1人为您的签约居民。
                            Calendar cal = Calendar.getInstance();
                            String time = ExtendDate.getYMD(cal);
                            sysDao.getAppNoticeDao().addNotices("中医药健康指导（0-36月龄）履约提醒","截止"+time+"您签约居民中有"+num+"个儿童未进行中医药健康指导。",
                                    NoticesType.GZJHTX.getValue()+","+PerType.ZYTZBS.getValue(),"系统消息",drUser.getId(),lyNum,DrPatientType.DR.getValue());
                        }
                    }
                }

            }else{
                List<AppTcmLyEntity> list = sysDao.getAppPerFormanceDao().findTcmLy(qvo);
                if(list!=null && list.size()>0){
                    for(AppTcmLyEntity ll:list){
                        if(!"0".equals(ll.getCountTj())){
                            if(Integer.parseInt(ll.getCountGuide())<Integer.parseInt(ll.getCountTj())){
                                AppPatientUser user = sysDao.getAppPatientUserDao().findByUserId(ll.getPatientId());
                                if(user!=null){
                                    AppPerformanceRecordEntity tt = new AppPerformanceRecordEntity();
                                    tt.setPatientId(user.getId());
                                    tt.setSignId(ll.getSignId());
                                    tt.setPatientAge(user.getPatientAge());
                                    tt.setPatientGender(user.getPatientGender());
                                    tt.setPatientIdNo(user.getPatientIdno());
                                    tt.setPatientImageName(user.getPatientImageName());
                                    tt.setPatientImageUrl(user.getPatientImageurl());
                                    tt.setPatientName(user.getPatientName());
                                    tt.setFwType("1");
                                    tts.add(tt);
                                }

                            }
                        }
                    }
                }
            }

        }
        return tts;
    }

    /**
     * 孕期保健服务履约提醒
     * @param drUser
     * @param lyNum
     * @param type
     */
    public List<AppPerformanceRecordEntity> prenatalCare(AppDrUser drUser,String lyNum,String type)throws Exception{//服务类型为孕产妇，本年度未进行第一次产前检查、产前随访服务的对象、
        AppCommQvo qvo = new AppCommQvo();
        List<AppPerformanceRecordEntity> tts = new ArrayList<>();
        if(drUser!=null){
            qvo.setDrId(drUser.getId());
            if("1".equals(type)){
                if("0".equals(drUser.getDrTxState())){
                    List<AppLyTxEntity> list = sysDao.getAppPerFormanceDao().findPrenatalCare(qvo);
                    if(list!=null && list.size()>0){
                        int num = 0;
                        for(AppLyTxEntity ll:list){
                            if("0".equals(ll.getCount())){
                                num++;
                                AppPerformanceRecord table = new AppPerformanceRecord();
                                table.setAprPatientId(ll.getPatientId());
                                table.setAprSignId(ll.getSignId());
                                table.setAprPcNum(lyNum);
                                table.setAprCreateTime(Calendar.getInstance());
                                table.setAprDrId(drUser.getId());
                                table.setAprType(PerType.YQBJFW.getValue());
                                sysDao.getServiceDo().add(table);
                            }
                        }
                        if(num!=0){//截止2017-11-5有2个孕产妇未及时进行孕期检查，其中1人为您的签约居民。
                            Calendar cal = Calendar.getInstance();
                            String time = ExtendDate.getYMD(cal);
                            sysDao.getAppNoticeDao().addNotices("孕期保健服务履约提醒","截止"+time+"您签约居民中有"+num+"个孕产妇未及时进行孕期检查。",
                                    NoticesType.GZJHTX.getValue()+","+PerType.YQBJFW.getValue(),"系统消息",drUser.getId(),lyNum,DrPatientType.DR.getValue());
                        }
                    }
                }
            }else{
                List<AppLyTxEntity> list = sysDao.getAppPerFormanceDao().findPrenatalCare(qvo);
                if(list!=null && list.size()>0){
                    for(AppLyTxEntity ll:list){
                        if("0".equals(ll.getCount())){
                            AppPatientUser user = sysDao.getAppPatientUserDao().findByUserId(ll.getPatientId());
                            if(user!=null){
                                AppPerformanceRecordEntity tt = new AppPerformanceRecordEntity();
                                tt.setPatientId(user.getId());
                                tt.setSignId(ll.getSignId());
                                tt.setPatientAge(user.getPatientAge());
                                tt.setPatientGender(user.getPatientGender());
                                tt.setPatientIdNo(user.getPatientIdno());
                                tt.setPatientImageName(user.getPatientImageName());
                                tt.setPatientImageUrl(user.getPatientImageurl());
                                tt.setPatientName(user.getPatientName());
                                tt.setFwType("1");
                                tts.add(tt);
                            }
                        }
                    }
                }
            }
        }
        return tts;
    }

    /**
     * 产后42天健康检查记录履约提醒
     * @param drUser
     * @param lyNum
     * @param type
     */
    public List<AppPerformanceRecordEntity> postpartum(AppDrUser drUser,String lyNum,String type)throws Exception{//本年度未进行产后42天健康检查的对象，包含基卫数据。
        AppCommQvo qvo = new AppCommQvo();
        List<AppPerformanceRecordEntity> tts = new ArrayList<>();
        if(drUser!=null){
            qvo.setDrId(drUser.getId());
            if("1".equals(type)){
                if("0".equals(drUser.getDrTxState())){
                    List<AppLyTxEntity> list = sysDao.getAppPerFormanceDao().findPostPartum(qvo);
                    if(list !=null && list.size()>0){
                        int num = 0;
                        for(AppLyTxEntity ll:list){
                            if("0".equals(ll.getCount())){
                                num++;
                                AppPerformanceRecord table = new AppPerformanceRecord();
                                table.setAprPatientId(ll.getPatientId());
                                table.setAprSignId(ll.getSignId());
                                table.setAprPcNum(lyNum);
                                table.setAprCreateTime(Calendar.getInstance());
                                table.setAprDrId(drUser.getId());
                                table.setAprType(PerType.CHJKJC.getValue());
                                sysDao.getServiceDo().add(table);
                            }
                        }
                        if(num != 0){//截止2017-11-5有10个孕产妇需进行产后42天健康检查，其中1人为您的签约居民。
                            Calendar cal = Calendar.getInstance();
                            String time = ExtendDate.getYMD(cal);
                            sysDao.getAppNoticeDao().addNotices("产后42天健康检查记录履约提醒","截止"+time+"您签约居民中有"+num+"个孕产妇需进行产后42天健康检查。",
                                    NoticesType.GZJHTX.getValue()+","+PerType.CHJKJC.getValue(),"系统消息",drUser.getId(),lyNum,DrPatientType.DR.getValue());
                        }
                    }
                }
            }else{
                List<AppLyTxEntity> list = sysDao.getAppPerFormanceDao().findPostPartum(qvo);
                if(list !=null && list.size()>0){
                    for(AppLyTxEntity ll:list){
                        if("0".equals(ll.getCount())){
                            AppPatientUser user = sysDao.getAppPatientUserDao().findByUserId(ll.getPatientId());
                            if(user!=null){
                                AppPerformanceRecordEntity tt = new AppPerformanceRecordEntity();
                                tt.setPatientId(user.getId());
                                tt.setSignId(ll.getSignId());
                                tt.setPatientAge(user.getPatientAge());
                                tt.setPatientGender(user.getPatientGender());
                                tt.setPatientIdNo(user.getPatientIdno());
                                tt.setPatientImageName(user.getPatientImageName());
                                tt.setPatientImageUrl(user.getPatientImageurl());
                                tt.setPatientName(user.getPatientName());
                                tt.setFwType("1");
                                tts.add(tt);
                            }
                        }
                    }
                }
            }

        }
        return tts;
    }

    /**
     * 健康体检履约提醒
     * @param drUser
     * @param lyNum
     * @param type
     */
    public  List<AppPerformanceRecordEntity> healthyCheckUp(AppDrUser drUser,String lyNum,String type)throws Exception{
        AppCommLyQvo qvo = new AppCommLyQvo();
        List<AppPerformanceRecordEntity> tts = new ArrayList<>();
        if(drUser!=null){
            AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
            String areaCode = "";
            if(dept!=null){
                if(StringUtils.isNotBlank(dept.getHospAreaCode())) {
                    areaCode = dept.getHospAreaCode().substring(0, 4) + "00000000";
                }
            }
            qvo.setAreaCode(areaCode);
            qvo.setHospId(drUser.getDrHospId());
            qvo.setDrId(drUser.getId());
            qvo.setPerType(PerformanceType.JKTJ.getValue());
            if("1".equals(type)){
                if("0".equals(drUser.getDrTxState())){
                    List<AppDrSignPeoleListEntity> ls=sysDao.getAppSignformDao().findSignFormList(qvo);
                    if(ls!=null && ls.size()>0){
                        int num = 0;
                        for(AppDrSignPeoleListEntity ll:ls){
                            if(StringUtils.isNotBlank(ll.getFwbValue())){
                                //健康体检履约提醒
                                if("0".equals(ll.getSignCount())){
                                    num++;
                                    AppPerformanceRecord table = new AppPerformanceRecord();
                                    table.setAprPatientId(ll.getPatientId());
                                    table.setAprSignId(ll.getSignId());
                                    table.setAprPcNum(lyNum);
                                    table.setAprCreateTime(Calendar.getInstance());
                                    table.setAprDrId(drUser.getId());
                                    table.setAprType(PerType.JKTJ.getValue());
                                    sysDao.getServiceDo().add(table);
                                }
                            }
                        }
                        if(num!=0){//截止2017-11-5有10个居民需进行健康体检，其中8人为您的签约居民。
                            Calendar cal = Calendar.getInstance();
                            String time = ExtendDate.getYMD(cal);
                            sysDao.getAppNoticeDao().addNotices("健康体检履约提醒","截止"+time+"您签约居民中有"+num+"人需进行健康体检。",
                                    NoticesType.GZJHTX.getValue()+","+PerType.JKTJ.getValue(),"系统消息",drUser.getId(),lyNum,DrPatientType.DR.getValue());
                        }
                    }
                }

            }else{
                List<AppDrSignPeoleListEntity> ls=sysDao.getAppSignformDao().findSignFormList(qvo);
                if(ls!=null && ls.size()>0){
                    for(AppDrSignPeoleListEntity ll:ls){
                        if(StringUtils.isNotBlank(ll.getFwbValue())){
                            //健康体检履约提醒
                            if("0".equals(ll.getSignCount())){
                                AppPatientUser user =sysDao.getAppPatientUserDao().findByUserId(ll.getPatientId());
                                if(user!=null){
                                    AppPerformanceRecordEntity tt = new AppPerformanceRecordEntity();
                                    tt.setPatientId(user.getId());
                                    tt.setSignId(ll.getSignId());
                                    tt.setPatientAge(user.getPatientAge());
                                    tt.setPatientGender(user.getPatientGender());
                                    tt.setPatientIdNo(user.getPatientIdno());
                                    tt.setPatientImageName(user.getPatientImageName());
                                    tt.setPatientImageUrl(user.getPatientImageurl());
                                    tt.setPatientName(user.getPatientName());
                                    tt.setFwType("1");
                                    tts.add(tt);
                                }
                            }
                        }
                    }
                }
            }

        }
        return tts;
    }

	/******************************************** 新履约功能(根据服务包) ********************************************/
	/**
	 * 医生钻取某一项未完成数的人员信息
	 * 
	 * 比如钻取[健康指导]中未完成的人员信息
	 * 
	 * @param drId 医生ID
	 * @param serverType 服务类型
	 * @author lyy
	 * @return
	 */
	public List<AppPerformanceRecordEntity> findUnfinishedPerListByServerSetMeal(String drId, String serverType) throws Exception {
		List<AppPerformanceRecordEntity> returnList = new ArrayList<AppPerformanceRecordEntity>();
		String serverTypeTrans = this.transServerType(serverType);
		// 查询当前医生服务包
		AppDrUser drUser = sysDao.getAppDrUserDao().findByUserId(drId);
		AppServeSetmealQvo serveSetmealQvo = new AppServeSetmealQvo();
		serveSetmealQvo.setRoleType("2");
		if (StringUtils.isNotBlank(drUser.getDrHospId())) {
			AppHospDept hospDept = (AppHospDept) this.sysDao.getServiceDo().find(AppHospDept.class, drUser.getDrHospId());
			serveSetmealQvo.setAreaCode(hospDept.getHospAreaCode());
			serveSetmealQvo.setHospId(drUser.getDrHospId());
		}
		// 查询当前地区下，签约的所有类型和数量
		String areaCode = "";
		if (StringUtils.isNotBlank(serveSetmealQvo.getAreaCode())) {
			areaCode = AreaUtils.getAreaCode(serveSetmealQvo.getAreaCode());
		}
		serveSetmealQvo.setOpenArea(areaCode);

		// 查询该地区或医院下所有的服务包
		List<AppServeSetmeal> ls = sysDao.getAppServeSetmealDao().findList(serveSetmealQvo);
		if (ls == null || ls.size() == 0) {
			return returnList;
		}
		// 得出服务包最后结果
		Map<String, Map<String, List<String>>> resultMap = sysDao.getAppServeSetmealDao().dealAppServeSetmeal(ls);

		// 查询医生签约的人员
		List<AppSignForm> appSignFormList = sysDao.getAppSignformDao().findFormByDr(drId);
		// 当前年
		String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
		if (appSignFormList != null && appSignFormList.size() > 0) {
			for (AppSignForm appSignForm : appSignFormList) {
				// 应提供的频次数
				int pcNum = 1;
				// 当前签约人员的服务人群类型
				String signPersonServerType = sysDao.getAppLabelGroupDao().findFwValue(appSignForm.getId());
				// 计算服务包ID对应的服务对象和频次
				Map<String, List<String>> map = sysDao.getAppServeSetmealDao().countAppServeSetmeals(appSignForm.getSignpackageid(), resultMap);
				// 计算不同服务对象下的服务内容和频次
				List<String> serverContentAndPcNumList = sysDao.getAppServeSetmealDao().countAppServeObjects(map, signPersonServerType);
				// 是否要把此签约人返回
				boolean flag = true;
				// 循环服务内容和频次,计算该服务内容的最大频次
				for (String serverContentAndPcNum : serverContentAndPcNumList) {
					String[] serverContentAndPcNumSz = serverContentAndPcNum.split("&&&");
					if (serverTypeTrans.equals(serverContentAndPcNumSz[0])) {
						if (StringUtils.isNotBlank(serverContentAndPcNumSz[1]) && !"null".equals(serverContentAndPcNumSz[1])) {
							pcNum = Integer.valueOf(serverContentAndPcNumSz[1]);
							flag = false;
							break;
						}
					}
				}
				if (flag) {
					continue;
				}
				// 计算本年医生已做
				int countyear = 0;
				if (serverTypeTrans.equals(PerformanceType.JKJY.getValue())) {// 健康教育
					if (StringUtils.isNotBlank(drUser.getDrHospId())) {
						countyear = sysDao.getAppPerformanceStatisticsDao().getHealthEducation(null, year, null, appSignForm.getSignPatientIdNo(), null, drUser.getDrHospId(), null);
					} else if (StringUtils.isNotBlank(serveSetmealQvo.getAreaCode())) {
						countyear = sysDao.getAppPerformanceStatisticsDao().getHealthEducation(null, year, null, appSignForm.getSignPatientIdNo(), null, null, AreaUtils.getAreaCode(serveSetmealQvo.getAreaCode()));
					}
				} else if (serverTypeTrans.equals(PerformanceType.JKZD.getValue())) {// 健康指导
					if (StringUtils.isNotBlank(drUser.getDrHospId())) {
						countyear = sysDao.getAppPerformanceStatisticsDao().getHealthGuidance(null, year, null, appSignForm.getSignPatientIdNo(), null, drUser.getDrHospId(), null);
					} else if (StringUtils.isNotBlank(serveSetmealQvo.getAreaCode())) {
						countyear = sysDao.getAppPerformanceStatisticsDao().getHealthGuidance(null, year, null, appSignForm.getSignPatientIdNo(), null, null, AreaUtils.getAreaCode(serveSetmealQvo.getAreaCode()));
					}
				} else if (serverTypeTrans.equals(PerformanceType.DQSF.getValue() + "_" + ResidentMangeType.ETLZLS.getValue())) {// 新生儿家庭访视
					if (StringUtils.isNotBlank(drUser.getDrHospId())) {
						countyear = sysDao.getAppPerformanceStatisticsDao().getRegularFollowup(null, year, null, "1", appSignForm.getSignPatientIdNo(), null, drUser.getDrHospId(), null);
					} else if (StringUtils.isNotBlank(serveSetmealQvo.getAreaCode())) {
						countyear = sysDao.getAppPerformanceStatisticsDao().getRegularFollowup(null, year, null, "1", appSignForm.getSignPatientIdNo(), null, null, AreaUtils.getAreaCode(serveSetmealQvo.getAreaCode()));
					}
				} else if (serverTypeTrans.equals(PerformanceType.DQSF.getValue() + "_" + ResidentMangeType.ETLZLS.getValue() + "_2")) {// 儿童体检
					if (StringUtils.isNotBlank(drUser.getDrHospId())) {
						countyear = sysDao.getAppPerformanceStatisticsDao().getRegularFollowup(null, year, null, "2", appSignForm.getSignPatientIdNo(), null, drUser.getDrHospId(), null);
					} else if (StringUtils.isNotBlank(serveSetmealQvo.getAreaCode())) {
						countyear = sysDao.getAppPerformanceStatisticsDao().getRegularFollowup(null, year, null, "2", appSignForm.getSignPatientIdNo(), null, null, AreaUtils.getAreaCode(serveSetmealQvo.getAreaCode()));
					}
				} else if (serverTypeTrans.equals(PerformanceType.DQSF.getValue() + "_" + ResidentMangeType.GXY.getValue())) {// 高血压随访
					if (StringUtils.isNotBlank(drUser.getDrHospId())) {
						countyear = sysDao.getAppPerformanceStatisticsDao().getRegularFollowup(null, year, null, "5", appSignForm.getSignPatientIdNo(), null, drUser.getDrHospId(), null);
					} else if (StringUtils.isNotBlank(serveSetmealQvo.getAreaCode())) {
						countyear = sysDao.getAppPerformanceStatisticsDao().getRegularFollowup(null, year, null, "5", appSignForm.getSignPatientIdNo(), null, null, AreaUtils.getAreaCode(serveSetmealQvo.getAreaCode()));
					}
				} else if (serverTypeTrans.equals(PerformanceType.DQSF.getValue() + "_" + ResidentMangeType.TNB.getValue())) {// 糖尿病随访
					if (StringUtils.isNotBlank(drUser.getDrHospId())) {
						countyear = sysDao.getAppPerformanceStatisticsDao().getRegularFollowup(null, year, null, "6", appSignForm.getSignPatientIdNo(), null, drUser.getDrHospId(), null);
					} else if (StringUtils.isNotBlank(serveSetmealQvo.getAreaCode())) {
						countyear = sysDao.getAppPerformanceStatisticsDao().getRegularFollowup(null, year, null, "6", appSignForm.getSignPatientIdNo(), null, null, AreaUtils.getAreaCode(serveSetmealQvo.getAreaCode()));
					}
				} else if (serverTypeTrans.equals(PerformanceType.DQSF.getValue() + "_" + ResidentMangeType.YZJSZY.getValue())) {// 严重精神障碍随访
					if (StringUtils.isNotBlank(drUser.getDrHospId())) {
						countyear = sysDao.getAppPerformanceStatisticsDao().getRegularFollowup(null, year, null, "7", appSignForm.getSignPatientIdNo(), null, drUser.getDrHospId(), null);
					} else if (StringUtils.isNotBlank(serveSetmealQvo.getAreaCode())) {
						countyear = sysDao.getAppPerformanceStatisticsDao().getRegularFollowup(null, year, null, "7", appSignForm.getSignPatientIdNo(), null, null, AreaUtils.getAreaCode(serveSetmealQvo.getAreaCode()));
					}
				} else if (serverTypeTrans.equals(PerformanceType.DQSF.getValue() + "_" + ResidentMangeType.JHB.getValue())) {// 严重精神障碍随访
					if (StringUtils.isNotBlank(drUser.getDrHospId())) {
						countyear = sysDao.getAppPerformanceStatisticsDao().getRegularFollowup(null, year, null, "8", appSignForm.getSignPatientIdNo(), null, drUser.getDrHospId(), null);
					} else if (StringUtils.isNotBlank(serveSetmealQvo.getAreaCode())) {
						countyear = sysDao.getAppPerformanceStatisticsDao().getRegularFollowup(null, year, null, "8", appSignForm.getSignPatientIdNo(), null, null, AreaUtils.getAreaCode(serveSetmealQvo.getAreaCode()));
					}
				} else if (serverTypeTrans.equals(PerformanceType.JKZX.getValue())) {// 健康咨询
					if (StringUtils.isNotBlank(drUser.getDrHospId())) {
						countyear = sysDao.getAppPerformanceStatisticsDao().getHealthConsultation(null, year, null, appSignForm.getSignPatientIdNo(), null, drUser.getDrHospId(), null);
					} else if (StringUtils.isNotBlank(serveSetmealQvo.getAreaCode())) {
						countyear = sysDao.getAppPerformanceStatisticsDao().getHealthConsultation(null, year, null, appSignForm.getSignPatientIdNo(), null, null, AreaUtils.getAreaCode(serveSetmealQvo.getAreaCode()));
					}
				} else if (serverTypeTrans.equals(PerformanceType.ZYYJKZD.getValue())) {// 中医药健康指导
					if (StringUtils.isNotBlank(drUser.getDrHospId())) {
						countyear = sysDao.getAppPerformanceStatisticsDao().getChineseGuidance(null, year, null, appSignForm.getSignPatientIdNo(), null, drUser.getDrHospId(), null);
					} else if (StringUtils.isNotBlank(serveSetmealQvo.getAreaCode())) {
						countyear = sysDao.getAppPerformanceStatisticsDao().getChineseGuidance(null, year, null, appSignForm.getSignPatientIdNo(), null, null, AreaUtils.getAreaCode(serveSetmealQvo.getAreaCode()));
					}
				} else if (serverTypeTrans.equals(PerformanceType.YQBJFW.getValue())) {// 孕期保健服务
					if (StringUtils.isNotBlank(drUser.getDrHospId())) {
						countyear = sysDao.getAppPerformanceStatisticsDao().getPrenatalCare(null, year, null, appSignForm.getSignPatientIdNo(), null, drUser.getDrHospId(), null);
					} else if (StringUtils.isNotBlank(serveSetmealQvo.getAreaCode())) {
						countyear = sysDao.getAppPerformanceStatisticsDao().getPrenatalCare(null, year, null, appSignForm.getSignPatientIdNo(), null, null, AreaUtils.getAreaCode(serveSetmealQvo.getAreaCode()));
					}
				} else if (serverTypeTrans.equals(PerformanceType.CHFS.getValue())) {// 产后视访
					if (StringUtils.isNotBlank(drUser.getDrHospId())) {
						countyear = sysDao.getAppPerformanceStatisticsDao().getPostpartumVisit(null, year, null, appSignForm.getSignPatientIdNo(), null, drUser.getDrHospId(), null);
					} else if (StringUtils.isNotBlank(serveSetmealQvo.getAreaCode())) {
						countyear = sysDao.getAppPerformanceStatisticsDao().getPostpartumVisit(null, year, null, appSignForm.getSignPatientIdNo(), null, null, AreaUtils.getAreaCode(serveSetmealQvo.getAreaCode()));
					}
				} else if (serverTypeTrans.equals(PerformanceType.JKTJ.getValue())) {// 健康体检
					if (StringUtils.isNotBlank(drUser.getDrHospId())) {
						countyear = sysDao.getAppPerformanceStatisticsDao().getPhysicalExamination(null, year, null, appSignForm.getSignPatientIdNo(), null, drUser.getDrHospId(), null);
					} else if (StringUtils.isNotBlank(serveSetmealQvo.getAreaCode())) {
						countyear = sysDao.getAppPerformanceStatisticsDao().getPhysicalExamination(null, year, null, appSignForm.getSignPatientIdNo(), null, null, AreaUtils.getAreaCode(serveSetmealQvo.getAreaCode()));
					}
				} else if (serverTypeTrans.equals(PerformanceType.ZYTZBS.getValue())) {// 中医体质辨识
					if (StringUtils.isNotBlank(drUser.getDrHospId())) {
						countyear = sysDao.getAppPerformanceStatisticsDao().getConstitutionIdentification(null, year, null, appSignForm.getSignPatientIdNo(), null, drUser.getDrHospId(), null);
					} else if (StringUtils.isNotBlank(serveSetmealQvo.getAreaCode())) {
						countyear = sysDao.getAppPerformanceStatisticsDao().getConstitutionIdentification(null, year, null, appSignForm.getSignPatientIdNo(), null, null, AreaUtils.getAreaCode(serveSetmealQvo.getAreaCode()));
					}
				} else if (serverTypeTrans.equals(PerformanceType.YYZD.getValue())) {// 用药指导
					if (StringUtils.isNotBlank(drUser.getDrHospId())) {
						countyear = sysDao.getAppPerformanceStatisticsDao().getMedicationGuidance(null, year, null, appSignForm.getSignPatientIdNo(), null, drUser.getDrHospId(), null);
					} else if (StringUtils.isNotBlank(serveSetmealQvo.getAreaCode())) {
						countyear = sysDao.getAppPerformanceStatisticsDao().getMedicationGuidance(null, year, null, appSignForm.getSignPatientIdNo(), null, null, AreaUtils.getAreaCode(serveSetmealQvo.getAreaCode()));
					}
				} else if (serverTypeTrans.equals(PerformanceType.GGFW.getValue())) {// 公共服务
					if (StringUtils.isNotBlank(drUser.getDrHospId())) {
						countyear = sysDao.getAppPerformanceStatisticsDao().getPublicService(year, null, drUser.getDrHospId(), null);
					} else if (StringUtils.isNotBlank(serveSetmealQvo.getAreaCode())) {
						countyear = sysDao.getAppPerformanceStatisticsDao().getPublicService(year, null, null, AreaUtils.getAreaCode(serveSetmealQvo.getAreaCode()));
					}
				}
				// System.out.println("患者：" + appSignForm.getPatientName() + "，应提供的频次数：" + pcNum + "，今年完成数：" + countyear);
				// 如果频次大于今年应做的数量,则该签约人员未完成
				if (pcNum > countyear || (pcNum == 0 && countyear == 0)) {
					AppPerformanceRecordEntity entity = new AppPerformanceRecordEntity();
					entity.setPatientId(appSignForm.getSignPatientId());
					entity.setPatientName(appSignForm.getPatientName());
					entity.setPatientIdNo(appSignForm.getSignPatientIdNo());
					entity.setPatientAge(String.valueOf(appSignForm.getSignPatientAge()));
					entity.setPatientGender(appSignForm.getSignPatientGender());
					entity.setSignId(appSignForm.getId());
					entity.setFwType(serverType);
					returnList.add(entity);
				}
			}
		}
		return returnList;
	}
	
	/**
	 * 服务类型转换
	 */
	private String transServerType(String serverType) throws Exception{
		if (PerType.JKJY.getValue().equals(serverType)) {// 健康教育
			return PerformanceType.JKJY.getValue();
		} else if (PerType.JKZD.getValue().equals(serverType)) {// 健康指导
			return PerformanceType.JKZD.getValue();
		} else if (PerType.XSEFS.getValue().equals(serverType)) {// 新生儿家庭访视
			return PerformanceType.DQSF.getValue() + "_" + ResidentMangeType.ETLZLS.getValue();
		} else if (PerType.ETJKTJ.getValue().equals(serverType)) {// 0-6岁儿童健康检查
			return PerformanceType.DQSF.getValue() + "_" + ResidentMangeType.ETLZLS.getValue() + "_2";
		} else if (PerType.ETZYYJKZD.getValue().equals(serverType)) {// 中医药健康指导
			return PerformanceType.ZYYJKZD.getValue();
		} else if (PerType.YQBJFW.getValue().equals(serverType)) {// 孕产妇保健
			return PerformanceType.YQBJFW.getValue();
		} else if (PerType.CHFS.getValue().equals(serverType)) {// 产后访视
			return PerformanceType.CHFS.getValue();
		} else if (PerType.CHJKJC.getValue().equals(serverType)) {// 产后42天健康检查记录
			return PerformanceType.CHFS.getValue();
		} else if (PerType.JKTJ.getValue().equals(serverType)) {// 健康体检
			return PerformanceType.JKTJ.getValue();
		} else if (PerType.YYZD.getValue().equals(serverType)) {// 用药指导
			return PerformanceType.YYZD.getValue();
		} else if (PerType.GXYSF.getValue().equals(serverType)) {// 高血压定期随访
			return PerformanceType.DQSF.getValue() + "_" + ResidentMangeType.GXY.getValue();
		} else if (PerType.TNBSF.getValue().equals(serverType)) {// 糖尿病定期随访
			return PerformanceType.DQSF.getValue() + "_" + ResidentMangeType.TNB.getValue();
		} else if (PerType.ZXJSBSF.getValue().equals(serverType)) {// 重性精神疾病定期随访
			return PerformanceType.DQSF.getValue() + "_" + ResidentMangeType.YZJSZY.getValue();
		} else if (PerType.JHBSF.getValue().equals(serverType)) {// 肺结核病定期随访
			return PerformanceType.DQSF.getValue() + "_" + ResidentMangeType.JHB.getValue();
		} else if (PerType.GGFW.getValue().equals(serverType)) {// 公共服务
			return PerformanceType.GGFW.getValue();
		} else if (PerType.ZYTZBS.getValue().equals(serverType)) {// 中医体质辨识
			return PerformanceType.ZYTZBS.getValue();
		}
		return "";
	}

	public void assessMonthXx(AppDrUser drUser)throws Exception{
        List<AssessNewsEntity>  list = sysDao.getAssessmentDao().findAssessMonthSignById(drUser.getId());
        if(list!= null && list.size()>0){
            int num = list.size();
            sysDao.getAppNoticeDao().addNotices("绩效考核消息",
                    "到期前一个月还有 " + num +" 人未完成考核！",
                    NoticesType.JXXX.getValue()+",1", "系统提醒", drUser.getId(), "", DrPatientType.DR.getValue());
        }
    }

    public void assessWeekXx(AppDrUser drUser) throws Exception{
	    //判断是否是本月的最后一周
        Calendar cal = Calendar.getInstance();//系统当前时间
        int monthOne = cal.get(Calendar.MONTH);//当前月
        cal.add(Calendar.DATE,7);//加7天
        int monthTwo = cal.get(Calendar.MONTH);//获取月份
        if(monthTwo!=monthOne){//两个月的值不等说明当前日期是本月最后一周
            List<String> list = sysDao.getAssessmentDao().findAssessWeekSignById(drUser.getId());
            if(list != null && list.size()>0){
                sysDao.getAppNoticeDao().addNotices("绩效考核消息",
                        "还有 " + list.size() +" 人未上传每月社区服务凭证！",
                        NoticesType.JXXX.getValue()+",2", "系统提醒", drUser.getId(), "", DrPatientType.DR.getValue());
            }
        }
    }
}
