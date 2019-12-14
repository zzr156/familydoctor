package com.ylz.view.hzapp.action;

import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.*;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * 患者端调度接口
 * Created by zzl on 2017/10/31.
 */
@SuppressWarnings("all")
@Action(
        value="hzScheduling",
        results={
                @Result(name = "ajson", type = "json",params={"root","ajson","contentType", "application/json"})
        }
)
public class HzSchedulingAction extends CommonAction {
    /**
     * 体检提醒
     */
    public void medicalAlert(){
      try {
          AppPatientUser user = this.getAppPatientUser();
          if(user!=null){
              Calendar cal = Calendar.getInstance();
              List<AppChildHealthPlan> ls =sysDao.getAppChildHealthPlanDao().findByYj(cal,user.getId());
              if (ls != null && ls.size() > 0) {
                  for (AppChildHealthPlan v : ls) {
                      List<AppWarningSetting> setList = sysDao.getAppWarningSettingDao().findSetting(v.getChpUserId(), CommonWarnSet.TJXT.getValue());
                      int leftday = ExtendDate.getDayNumBetween2Date(Calendar.getInstance(),v.getChpPlanDate());
                      int warnNum = 3;//默认3天
                      if(!setList.isEmpty()){
                          warnNum = Integer.parseInt(setList.get(0).getWsNum());
                      }
                      if((!setList.isEmpty()&&setList.get(0).getWsState().equals(CommonEnable.QIYONG.getValue())&&warnNum>=leftday)
                              ||(setList.isEmpty()&&warnNum>=leftday)) {
                          sysDao.getAppNoticeDao().addNotices("儿童" + v.getChpTitle() + "体检提醒", "您好，" + ExtendDate.getChineseYMD(v.getChpPlanDate()) + "您家的儿童满" + v.getChpTitle() + "啦，请及时进行健康体检。", NoticesType.TJTX.getValue(), "系统提醒", v.getChpUserId(), v.getId(), DrPatientType.PATIENT.getValue());
                          v.setChpTxState("1");
                          sysDao.getServiceDo().modify(v);
                      }
                  }
              }
          }
      }catch (Exception e){
          e.printStackTrace();
      }
    }

    /**
     * 药品存量预警
     */
    public void drugRunOut(){
        try {
            AppPatientUser user = this.getAppPatientUser();
            if(user!=null){
                List<AppDrugGuide> ls = sysDao.getAppDrugGuideDao().findGuidByTime(user.getId());
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
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 孕妇保健计划提醒
     */
    public void pregnantPlan(){
        try {
            AppPatientUser user = this.getAppPatientUser();
            if(user!=null){
                List<AppPregnantPlan> list = sysDao.getAppPregnantPlanDao().findPlanAlert(user.getId());
                if(!list.isEmpty()){
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
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 续签提醒
     */
    public void renewalReminder(){
        try {
            AppPatientUser user = this.getAppPatientUser();
            if(user!=null){
                //发送绿级提醒
                List<AppSignForm> greenList = sysDao.getAppSignformDao().findByGreen(user.getId());
                if(greenList!=null && greenList.size()>0){
                    for(AppSignForm ls:greenList){
                        sysDao.getAppNoticeDao().addNotices("续签提醒",  "您好，您的家庭医生签约服务将于"+ExtendDate.getChineseYMD(ls.getSignToDate())+"到期，建议续签。", NoticesType.XTXX.getValue()+","+ NoticesMType.XQXX.getValue(), "系统提醒",
                                ls.getSignPatientId(), ls.getId(), DrPatientType.PATIENT.getValue());
                        ls.setSignGreenState(CommSF.YES.getValue());
                        sysDao.getServiceDo().modify(ls);
                    }
                }
                //发送黄级提醒
                List<AppSignForm> yellowList = sysDao.getAppSignformDao().findByYellow(user.getId());
                if(yellowList!=null && yellowList.size()>0){
                    for(AppSignForm ls:yellowList){
                        sysDao.getAppNoticeDao().addNotices("续签提醒",  "您好，您的家庭医生签约服务将于"+ExtendDate.getChineseYMD(ls.getSignToDate())+"到期，建议续签。", NoticesType.XTXX.getValue()+","+NoticesMType.XQXX.getValue(), "系统提醒",
                                ls.getSignPatientId(), ls.getId(), DrPatientType.PATIENT.getValue());
                        ls.setSignYellowState(CommSF.YES.getValue());
                        sysDao.getServiceDo().modify(ls);
                    }
                }
                //发送红级提醒
                List<AppSignForm> redList = sysDao.getAppSignformDao().findByRed(user.getId());
                if(redList!=null && redList.size()>0){
                    for(AppSignForm ls:redList){
                        sysDao.getAppNoticeDao().addNotices("续签提醒", "您好，您的家庭医生签约服务将于"+ExtendDate.getChineseYMD(ls.getSignToDate())+"到期，建议续签。", NoticesType.XTXX.getValue()+","+NoticesMType.XQXX.getValue(), "系统提醒",
                                ls.getSignPatientId(), ls.getId(), DrPatientType.PATIENT.getValue());
                        ls.setSignRedState(CommSF.YES.getValue());
                        sysDao.getServiceDo().modify(ls);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 居家养老提醒
     */
    public void homeCareReminder(){
        try {
            AppPatientUser user = this.getAppPatientUser();
            if(user!=null){
                //签约居家养老服务列表
                List<AppSignForm> signList = sysDao.getServiceDo().loadByPk(AppSignForm.class,"signServiceB","2");
                for(AppSignForm sign : signList){
                    if(user.getId().equals(sign.getSignPatientId())){
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
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 儿童体检免疫提醒
     */
    public void chileInoculation(){
        try{
            AppPatientUser user = this.getAppPatientUser();
            if(user!=null){
                List<AppChildInoculationPlan> list = sysDao.getAppChildInoculationPlanDao().findList(user.getId());
                String parientId = "";
                String str = "";
                String strDate = "";
                String id = "";
                if(list !=null && list.size()>0){
                    for(AppChildInoculationPlan ls:list){
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
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 用药提醒
     */
    public void drugReminder(){
        try {
            AppPatientUser user = this.getAppPatientUser();
            if(user!=null){
                HashMap<String,Object> map = new HashMap<>();
                map.put("userId",user.getId());
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
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
