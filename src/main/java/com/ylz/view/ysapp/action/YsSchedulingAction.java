package com.ylz.view.ysapp.action;

import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.plan.Entity.AppFollowPlanTxEntity;
import com.ylz.bizDo.plan.po.AppFollowPlan;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.comEnum.CommonEnable;
import com.ylz.packcommon.common.comEnum.CommonWarnSet;
import com.ylz.packcommon.common.comEnum.DrPatientType;
import com.ylz.packcommon.common.comEnum.NoticesType;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.Calendar;
import java.util.List;

/**
 * 医生调度接口
 * Created by zzl on 2017/10/31.
 */
@SuppressWarnings("all")
@Action(
        value="ysScheduling",
        results={
                @Result(name = "ajson", type = "json",params={"root","ajson","contentType", "application/json"})
        }
)
public class YsSchedulingAction extends CommonAction {
    /**
     * 随访消息提醒
     */
    public void followVisit(){
        try {
            AppDrUser drUser = this.getAppDrUser();
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
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 当天临时随访提醒
     */
    public void followNow(){
      try {
          AppDrUser drUser = this.getAppDrUser();
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
      }catch (Exception e){
          e.printStackTrace();
      }
    }
    /**
     * 药品存量预警
     */
    public void drugRunOutToDr(){
        try {
            AppDrUser drUser = this.getAppDrUser();
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

}
