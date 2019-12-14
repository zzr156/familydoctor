package com.ylz.task;

import com.alibaba.fastjson.JSON;
import com.ylz.bizDo.app.entity.AppHealthCardRecodesVo;
import com.ylz.bizDo.app.entity.AppSignFormEntity;
import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.app.vo.AppSignInfoAllVo;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.jtapp.basicHealthEntity.T_dwellerfile;
import com.ylz.bizDo.jtapp.basicHealthVo.HomeServiceItemsQvo;
import com.ylz.bizDo.jtapp.commonEntity.AppTcmDtEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppTcmRecordEntity;
import com.ylz.bizDo.jtapp.commonEntity.AssessNewsEntity;
import com.ylz.bizDo.jtapp.commonVo.AppTcmBsQvo;
import com.ylz.bizDo.jtapp.commonVo.AppTcmRecordQvo;
import com.ylz.bizDo.jtapp.commonVo.AppTcmYbQvo;
import com.ylz.bizDo.jtapp.commonVo.AppTcmZdQvo;
import com.ylz.bizDo.jtapp.drEntity.AppDrPatientFwEntity;
import com.ylz.bizDo.jtapp.patientEntity.AppTestPatientEntity;
import com.ylz.bizDo.plan.Entity.AppFollowPlanTxEntity;
import com.ylz.bizDo.plan.po.AppFollowPlan;
import com.ylz.bizDo.web.po.WebDrUser;
import com.ylz.bizDo.web.vo.WebSignVo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.comEnum.*;
import com.ylz.packcommon.common.exception.ActionException;
import com.ylz.packcommon.common.util.*;
import com.ylz.task.async.SecurityCardAsyncBean;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: zk
 * Date: 13-1-22
 * Time: 下午1:56
 * To change this template use File | Settings | File Templates.
 */
public class TestJob{

    @Autowired
    public SysDao sysDao=(SysDao) SpringHelper.getBean("sysDao");
    private Logger log = null;
    public void test()
    {
        System.out.println("Spring Quartz的TestJob任务被调用！");
    }
    //体检提醒111111111111
    public void medicalAlert(){
        try {
            Calendar cal = Calendar.getInstance();
            List<AppChildHealthPlan> ls =sysDao.getAppChildHealthPlanDao().findByYj(cal);
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
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 药品存量预警
     */
    public void drugRunOut(){
        try {
            List<AppDrugGuide> ls = sysDao.getAppDrugGuideDao().findGuidByTime();
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
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    //孕产妇保健计划提醒
    public void pregnantPlan(){
        try {
            List<AppPregnantPlan> list = sysDao.getAppPregnantPlanDao().findPlanAlert();
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
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    /**
     * 续签提醒
     */
    public void renewalReminder(){
        try {
            //发送绿级提醒
            List<AppSignForm> greenList = sysDao.getAppSignformDao().findByGreen();
            if(greenList!=null && greenList.size()>0){
                for(AppSignForm ls:greenList){
                    sysDao.getAppNoticeDao().addNotices("续签提醒",  "您好，您的家庭医生签约服务将于"+ExtendDate.getChineseYMD(ls.getSignToDate())+"到期，建议续签。", NoticesType.XTXX.getValue()+","+NoticesMType.XQXX.getValue(), "系统提醒",
                            ls.getSignPatientId(), ls.getId(), DrPatientType.PATIENT.getValue());
                    ls.setSignGreenState(CommSF.YES.getValue());
                    sysDao.getServiceDo().modify(ls);
                }
            }
            //发送黄级提醒
            List<AppSignForm> yellowList = sysDao.getAppSignformDao().findByYellow();
            if(yellowList!=null && yellowList.size()>0){
                for(AppSignForm ls:yellowList){
                    sysDao.getAppNoticeDao().addNotices("续签提醒",  "您好，您的家庭医生签约服务将于"+ExtendDate.getChineseYMD(ls.getSignToDate())+"到期，建议续签。", NoticesType.XTXX.getValue()+","+NoticesMType.XQXX.getValue(), "系统提醒",
                            ls.getSignPatientId(), ls.getId(), DrPatientType.PATIENT.getValue());
                    ls.setSignYellowState(CommSF.YES.getValue());
                    sysDao.getServiceDo().modify(ls);
                }
            }
            //发送红级提醒
            List<AppSignForm> redList = sysDao.getAppSignformDao().findByRed();
            if(redList!=null && redList.size()>0){
                for(AppSignForm ls:redList){
                    sysDao.getAppNoticeDao().addNotices("续签提醒", "您好，您的家庭医生签约服务将于"+ExtendDate.getChineseYMD(ls.getSignToDate())+"到期，建议续签。", NoticesType.XTXX.getValue()+","+NoticesMType.XQXX.getValue(), "系统提醒",
                            ls.getSignPatientId(), ls.getId(), DrPatientType.PATIENT.getValue());
                    ls.setSignRedState(CommSF.YES.getValue());
                    sysDao.getServiceDo().modify(ls);
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
            //签约居家养老服务列表
            List<AppSignForm> signList = sysDao.getServiceDo().loadByPk(AppSignForm.class,"signServiceB","2");
            for(AppSignForm sign : signList){
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
                        bDate.add(Calendar.DAY_OF_YEAR,Integer.parseInt(codeCycle.getCodeValue())/Integer.parseInt(codeFrequency.getCodeValue()));
                        int leftDays = ExtendDate.getDayNumBetween2Date(Calendar.getInstance(),bDate);
                        if(leftDays>0 && leftDays<=Integer.parseInt(remindDays)){
                            sysDao.getAppNoticeDao().addNotices("居家养老提醒", "您好，"+ExtendDate.getChineseYMD(bDate)+"将对您进行"+manageName+"，请做好准备",
                                    NoticesType.JJYL.getValue(), "系统提醒", sign.getSignPatientId(), sign.getId(), DrPatientType.PATIENT.getValue());
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
     * @return
     */
    public void chileInoculation(){
        try {
            List<AppChildInoculationPlan> list = sysDao.getAppChildInoculationPlanDao().findList();
            String parientId = "";
            String str = "";
            String strDate = "";
            String id = "";
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
                        AppPatientUser user = (AppPatientUser) sysDao.getServiceDo().find(AppPatientUser.class,parientId);
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
                AppPatientUser user = (AppPatientUser) sysDao.getServiceDo().find(AppPatientUser.class,parientId);
                sysDao.getAppNoticeDao().addNotices("儿童计划免疫提醒",
                        user.getPatientName()+"家长，请您于" + strDate + "带儿童到门诊接种疫苗："+str+"。",
                        NoticesType.YFJZTX.getValue(), "系统提醒", parientId, id, DrPatientType.PATIENT.getValue());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 随访消息提醒
     */
    public void followVisit(){
      try {
          List<AppFollowPlan> list = sysDao.getAppFollowPlanDao().findAllPlan();
          if(list!=null&&list.size()>0){
              for(AppFollowPlan ls:list){
                  sysDao.getAppNoticeDao().addNotices("随访提醒",
                          ExtendDate.getChineseYMD(ls.getSfFollowDate())+"您有需完成的随访，请注意",
                          NoticesType.SFTX.getValue(), "系统提醒", ls.getSfFollowDoctorid(), "", DrPatientType.DR.getValue());
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
            Calendar cal = Calendar.getInstance();
            String type = "";
            if(cal.get(Calendar.HOUR_OF_DAY)>7&&cal.get(Calendar.HOUR_OF_DAY)<=9){
                type = CommonWarnSet.SFBD.getValue();
            }else if(cal.get(Calendar.HOUR_OF_DAY)<=17&&cal.get(Calendar.HOUR_OF_DAY)>15){
                type = CommonWarnSet.SFSWD.getValue();
            }
            if(StringUtils.isNotBlank(type)){
                List<AppFollowPlanTxEntity> list = sysDao.getAppFollowPlanDao().findAllDayPlan(type);
                if(list!=null&&list.size()>0){
                    for(AppFollowPlanTxEntity ls:list){
                        sysDao.getAppNoticeDao().addNotices("随访提醒",
                                "今日，您已随访"+ls.getYsfs()+"人，还需随访"+ls.getWsfs()+"人",
                                NoticesType.SFTX.getValue(), "系统提醒", ls.getDrId(), ls.getId(), DrPatientType.DR.getValue());
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
            HashMap<String,Object> map = new HashMap<>();
            String sql = "select * from APP_DRUG_PLAN where DP_STATE = '1' ";
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
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 定期发送健康教育
     */
    public void fshealthE(){
        try {
            List<AppUserHealthED> list = sysDao.getNewsTableDao().findByPush();
            if(list!=null && list.size()>0){
                for(AppUserHealthED ls:list){
                    if(PushState.ZDRY.getValue().equals(ls.getHedObject())){//指定人员推送
                        sysDao.getAppNoticeDao().addNotices("系统消息", "您新收到一笔系统消息，请注意查看。",
                                NoticesType.XTXX.getValue(), "系统", ls.getHedUserId(), ls.getId(), null);
                        ls.setHedPushOstate("1");
                        sysDao.getServiceDo().modify(ls);
                    }else if(PushState.QBRY.getValue().equals(ls.getHedObject())){//全部人员推送
                        sysDao.getAppNoticeDao().addNoticesAllTag(ls.getHedTitle(),"您新收到一笔系统消息，请注意查看。",
                                NoticesType.XTXX.getValue(),ls.getHedAreaCode(),"1",
                                ls.getId(),"allpeople",ls.getHedAreaCode(),true);
                        ls.setHedPushOstate("1");
                        sysDao.getServiceDo().modify(ls);
                    }else{
                        if(StringUtils.isNotBlank(ls.getHedObjectXx())){
                            String[] sss = ls.getHedObjectXx().split(",");
                            sysDao.getAppNoticeDao().addNoticesAllTag(ls.getHedTitle(),"您新收到一笔系统消息，请注意查看。",
                                    NoticesType.XTXX.getValue(),ls.getHedAreaCode(),ls.getHedObjectXx(),
                                    ls.getId(),ls.getHedObject(),ls.getHedAreaCode(),true);
                        }
                        ls.setHedPushOstate("1");
                        sysDao.getServiceDo().modify(ls);
                    }

                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 设置每月医生履约提醒状态为
     */
    public void changeTxState(){
        String sql = "update APP_DR_USER SET DR_TX_STATE = '0' ";
        sysDao.getServiceDo().update(sql);
    }

    /**
     * 保存基卫体征数据信息
     */
    public void saveTzShuju(){
        sysDao.getSecurityCardAsyncBean().findTzshuju();
    }

    /**
     * 签约时间到期时修改签约状态
     * 修改转签签约状态
     * 福州增加当未做续签操作的签约信息过期后自动续签，默认服务套餐是基础服务包,add by WangCheng
     *
     */
    public void chageSignState(){
        try {
            //签约时间到期时修改签约状态
            List<AppSignForm> list = sysDao.getAppSignformDao().findOverdue();
            if(list!=null && list.size()>0){
                for(AppSignForm ll:list){
                    if("2".equals(ll.getSignGoToSignState())){//续签旧签约单
                        ll.setSignContractState("1");
                    }
                    String signState = ll.getSignState();
                    ll.setSignState(SignFormType.YJY.getValue());
                    ll.setSignSurrenderDate(Calendar.getInstance());
                    ll.setSignUrrenderReason("签约到期，自动解约");
                    sysDao.getServiceDo().modify(ll);
                    List<AppArchivingCardPeople> lisp = sysDao.getServiceDo().loadByPk(AppArchivingCardPeople.class,"archivingPatientIdno",ll.getSignPatientIdNo());

                    //如果是福州的话,把未续签的签约信息自动续签，并且签约套餐为基础服务包 19年03月07号添加三明自动续签
                    if((AddressType.FZS.getValue().equals(AreaUtils.getAreaCode(ll.getSignAreaCode(),"2"))||AddressType.SMS.getValue().equals(AreaUtils.getAreaCode(ll.getSignAreaCode(),"2")))&&"0".equals(ll.getSignGoToSignState())){
//                    if(AddressType.FZS.getValue().equals(AreaUtils.getAreaCode(ll.getSignAreaCode(),"2")) && "0".equals(ll.getSignGoToSignState())) {
                        //生成薪的签约单数据
                        AppSignForm newSignForm = new AppSignForm();
                        AppSignFormEntity signform = new AppSignFormEntity();
                        CopyUtils.Copy(ll, signform);
                        CopyUtils.Copy(signform,newSignForm);

                        String cal1 = ExtendDate.getYMD_h_m_s(ll.getSignToDate());
                        Calendar call = ExtendDate.getCalendar(cal1);
                        call.add(Calendar.DATE, 1);
                        newSignForm.setSignFromDate(call);
                        newSignForm.setSignDate(Calendar.getInstance());
                        Calendar calendarOhter = ExtendDate.getCalendar(cal1);
                        calendarOhter.add(Calendar.YEAR, +1);
//                        calendarOhter.add(Calendar.DAY_OF_MONTH, -1);
                        newSignForm.setSignToDate(calendarOhter);
                        if(AddressType.FZS.getValue().equals(AreaUtils.getAreaCode(ll.getSignAreaCode(),"2"))){
                            newSignForm.setSignState(SignFormType.YQY.getValue());
                            newSignForm.setSignPayState("1");
                        }else{
                            newSignForm.setSignState(signState);
                        }
                        newSignForm.setSignSurrenderDate(null);
                        newSignForm.setSignUrrenderReason(null);

                        //新生成批次表
                        AppSignBatch newSignBatch = new AppSignBatch();

                        //找到原来批次表的数据
                        AppSignBatch signBatch = (AppSignBatch) sysDao.getServiceDo().find(AppSignBatch.class, newSignForm.getSignBatchId());
                        CopyUtils.Copy(signBatch, newSignBatch);

                        //处理流水号表batch
                        if (StringUtils.isNotBlank(newSignForm.getSignAreaCode())) {
                            AppSerial serial = sysDao.getAppSignformDao().getAppSerial(newSignForm.getSignAreaCode().substring(0, 4), "batch");
                            if (serial != null) {
                                Map<String, Object> batchNum = sysDao.getAppSignformDao().getNum(serial.getSerialNo(), SignFormType.WEBSTATE.getValue());
                                serial.setSerialNo(batchNum.get("old").toString());
                                sysDao.getServiceDo().modify(serial);
                                //生成新的批次号
                                newSignBatch.setBatchNum(batchNum.get("new").toString());//批次号
                            }
                        }
                        sysDao.getServiceDo().add(newSignBatch);

                        //处理流水号表sign
                        if (StringUtils.isNotBlank(newSignForm.getSignAreaCode())) {
                            AppSerial signSerial = sysDao.getAppSignformDao().getAppSerial(newSignForm.getSignAreaCode().substring(0, 4), "sign");
                            if (signSerial != null) {
                                Map<String, Object> signNum = sysDao.getAppSignformDao().getNum(signSerial.getSerialNo(), SignFormType.WEBSTATE.getValue());
                                signSerial.setSerialNo(signNum.get("old").toString());
                                sysDao.getServiceDo().modify(signSerial);
                                //生成新的签约编号
                                newSignForm.setSignNum(signNum.get("new").toString());//批次号
                            }
                        }
                        newSignForm.setSignBatchId(newSignBatch.getId());
                        //福州自动续签默认服务套餐是基础服务包，其他的和原来的一样
                        if(AddressType.FZS.getValue().equals(AreaUtils.getAreaCode(ll.getSignAreaCode(),"2"))){
                            AppServeSetmeal meal = sysDao.getAppServeSetmealDao().findByValue("35010002");
                            if(meal != null){
                                newSignForm.setSignpackageid(meal.getId());
                            }
                        }
                        sysDao.getServiceDo().add(newSignForm);
                        ll.setSignGoToSignState("2");
                        ll.setSignContractState("1");
                        sysDao.getServiceDo().modify(ll);
                        //服务人群
                        List<AppLabelGroup> labelList = sysDao.getServiceDo().loadByPk(AppLabelGroup.class, "labelSignId", ll.getId());
                        if(labelList!=null && labelList.size()>0) {
                            for (AppLabelGroup g : labelList) {
                                AppLabelGroup alg = new AppLabelGroup();
                                alg.setLabelId(g.getLabelId());
                                alg.setLabelSignId(newSignForm.getId());
                                alg.setLabelTeamId(newSignForm.getSignTeamId());
                                alg.setLabelTitle(g.getLabelTitle());
                                alg.setLabelValue(g.getLabelValue());
                                alg.setLabelType(g.getLabelType());
                                alg.setLabelColor(g.getLabelColor());
                                alg.setLabelAreaCode(newSignForm.getSignAreaCode());
                                sysDao.getServiceDo().add(alg);
                            }
                        }

                        //疾病类型
                        List<AppLabelDisease> labelDiseases = sysDao.getServiceDo().loadByPk(AppLabelDisease.class,"labelSignId",ll.getId());
                        if(labelDiseases!=null && labelDiseases.size()>0){
                            for(AppLabelDisease g:labelDiseases){
                                AppLabelDisease alg = new AppLabelDisease();
                                alg.setLabelId(g.getLabelId());
                                alg.setLabelSignId(newSignForm.getId());
                                alg.setLabelTeamId(newSignForm.getSignTeamId());
                                alg.setLabelTitle(g.getLabelTitle());
                                alg.setLabelValue(g.getLabelValue());
                                alg.setLabelType(g.getLabelType());
                                alg.setLabelColor(g.getLabelColor());
                                alg.setLabelAreaCode(newSignForm.getSignAreaCode());
                                sysDao.getServiceDo().add(alg);
                            }
                        }
                        //经济类型
                        boolean jdlk = false;
                        List<AppLabelEconomics> listE = sysDao.getServiceDo().loadByPk(AppLabelEconomics.class,"labelSignId",ll.getId());
                        if(listE!=null && listE.size()>0){
                            for(AppLabelEconomics g:listE){
                                if(EconomicType.JDLKPKRK.getValue().equals(g.getLabelValue())){
                                    jdlk = true;
                                }
                                AppLabelEconomics alg = new AppLabelEconomics();
                                alg.setLabelId(g.getLabelId());
                                alg.setLabelSignId(newSignForm.getId());
                                alg.setLabelTeamId(newSignForm.getSignTeamId());
                                alg.setLabelTitle(g.getLabelTitle());
                                alg.setLabelValue(g.getLabelValue());
                                alg.setLabelType(g.getLabelType());
                                alg.setLabelColor(g.getLabelColor());
                                alg.setLabelAreaCode(newSignForm.getSignAreaCode());
                                sysDao.getServiceDo().add(alg);
                            }
                        }
                        String fwType = sysDao.getAppLabelGroupDao().findFwValue(ll.getId());
                        String[] fwTypes = null;
                        if(StringUtils.isNotBlank(fwType)){
                            fwTypes = fwType.split(",");
                        }
                        if(jdlk){//经济类型为建档立卡，调用公共方法
                            AppPatientUser user = (AppPatientUser) sysDao.getServiceDo().find(AppPatientUser.class,newSignForm.getSignPatientId());
                            sysDao.getAppSignformDao().addOrModifyArchivingSign(newSignForm.getSignPatientIdNo(),newSignForm.getId(),newSignForm.getSignDrId(),newSignForm.getSignTeamId(),newSignForm.getSignState(),fwTypes,newSignForm.getSignAreaCode(),newSignForm.getSignHospId(),newSignForm.getSignFromDate(),user);
                        }else{
                            //修改花名册数据
                            if(lisp != null && lisp.size()>0){
                                for(AppArchivingCardPeople archivingCardPeople:lisp){
                                    //判断签约的机构与花名册居民的街道是否一致，一致修改签约信息
                                    if(StringUtils.isNotBlank(archivingCardPeople.getAddrRuralCode())){
                                        if (AreaUtils.getAreaCode(newSignForm.getSignAreaCode(), "2").equals(AreaUtils.getAreaCode(archivingCardPeople.getAddrRuralCode(),"2"))) {
                                            archivingCardPeople.setSignState(newSignForm.getSignState());
                                            archivingCardPeople.setSignId(newSignForm.getId());
                                            archivingCardPeople.setDrId(newSignForm.getSignDrId());
                                            archivingCardPeople.setTeamId(newSignForm.getSignTeamId());
                                            archivingCardPeople.setHospId(newSignForm.getSignHospId());
                                            archivingCardPeople.setSignFromDate(newSignForm.getSignFromDate());
                                            sysDao.getServiceDo().modify(archivingCardPeople);
                                            //添加建档立卡服务标签
                                            sysDao.getAppLabelGroupDao().addLabel(fwTypes,newSignForm.getId(),newSignForm.getSignTeamId(),newSignForm.getSignAreaCode(),LabelManageType.JDLK.getValue());
                                        }
                                    }

                                /*else{//不一致清空签约信息当居民为未签约
                                    archivingCardPeople.setSignState(null);
                                    archivingCardPeople.setSignId(null);
                                    archivingCardPeople.setDrId(null);
                                    archivingCardPeople.setTeamId(null);
                                    archivingCardPeople.setHospId(null);
                                    archivingCardPeople.setSignFromDate(null);
                                    if(StringUtils.isBlank(archivingCardPeople.getNotSignReason())){
                                        archivingCardPeople.setNotSignReason("21");
                                    }
                                    sysDao.getServiceDo().modify(archivingCardPeople);
                                }*/
                                }
                            }
                        }
                    }else{
                        if(lisp != null && lisp.size()>0){
                            for(AppArchivingCardPeople llp:lisp){//清空建档立卡花名册表人员签约单数据
                                llp.setSignState(null);
                                llp.setSignId(null);
                                llp.setDrId(null);
                                llp.setTeamId(null);
                                llp.setHospId(null);
                                llp.setSignFromDate(null);
                                if(StringUtils.isBlank(llp.getNotSignReason())){
                                    llp.setNotSignReason("15");
                                }
                                sysDao.getServiceDo().modify(llp);
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /* *
     * 莆田签约数据推送前置机
     * cxw
     * */
    public void ptSignToYiBaoQzj() {
       try {
           System.out.println("开始推送莆田签约数据导前置机");
           List<AppSignInfoAllVo> list = sysDao.getWebSignFormDao().findAllSign();
           int count = 0;
           if(list!=null && list.size()>0){
               System.out.println("查询签约数据完毕,共"+list.size()+"条");
               for (AppSignInfoAllVo svo : list){
                   count++;
                   sysDao.getSecurityCardAsyncBean().ptSignToYiBao(svo,count);
               }
           }
           System.out.println("推送完成,共："+list.size()+"条 完成："+count+"条");
       }catch (Exception e){
           e.printStackTrace();
       }
    }

    /**
     *   签约调度取前置机数据 更新签约状态为2
     *   cxw
     */

	public void ptYiBaoQzjToSign()throws Exception
	{
		System.out.println("开始取前置机农合数据状态");
        sysDao.getSecurityCardAsyncBean().ptYiBaoQzjToSign();
		System.out.println("更新完成");
	}

    /**
     *  获取农合参保数据插入app_sign_hfs_ssc
     *  cxw
     * @throws Exception
     */
	public void ptGetNhSignSsc()throws Exception
    {
        System.out.println("开始取农合参保数据");
        sysDao.getSecurityCardAsyncBean().ptGetNhSignSsc();
        System.out.println("插入sign_ssc完成");
    }



    /**
     * 修改转签签约状态
     */
    public void changeZqSign(){
        try {
            //修改转签签约状态
            List<AppSignForm> zqlist = sysDao.getAppSignformDao().findByDate();
            if(zqlist!=null && zqlist.size()>0){
                for(AppSignForm ll:zqlist){
                    ll.setSignState(SignFormType.YUQY.getValue());
                    sysDao.getServiceDo().modify(ll);
                    //查询是否是建档立卡经济类型
                    String jjType = sysDao.getAppLabelGroupDao().findJjValue(ll.getId());
                    if(StringUtils.isNotBlank(jjType)){
                        if(jjType.indexOf(EconomicType.JDLKPKRK.getValue())!=-1){//是建档立卡经济人群
                            String fwType = sysDao.getAppLabelGroupDao().findFwValue(ll.getId());
                            String[] fwTypes = null;
                            if(StringUtils.isNotBlank(fwType)){
                                fwTypes = fwType.split(",");
                            }
                            AppPatientUser user = (AppPatientUser) sysDao.getServiceDo().find(AppPatientUser.class,ll.getSignPatientId());
                            sysDao.getAppSignformDao().addOrModifyArchivingSign(ll.getSignPatientIdNo(),ll.getId(),ll.getSignDrId(),ll.getSignTeamId(),ll.getSignState(),fwTypes,ll.getSignAreaCode(),ll.getSignHospId(),ll.getSignFromDate(),user);
                        }
                    }
               /* List<AppArchivingCardPeople> lisp = sysDao.getServiceDo().loadByPk(AppArchivingCardPeople.class,"archivingPatientIdno",ll.getSignPatientIdNo());
                if(lisp != null && lisp.size()>0){
                    for(AppArchivingCardPeople llp:lisp){
                        llp.setSignState(ll.getSignState());
                        llp.setSignId(ll.getId());
                        llp.setDrId(ll.getSignDrId());
                        llp.setTeamId(ll.getSignTeamId());
                        llp.setHospId(ll.getSignHospId());
                        llp.setSignFromDate(ll.getSignFromDate());
                        sysDao.getServiceDo().modify(llp);
                        //获取服务人群
                        String ss = sysDao.getAppLabelGroupDao().findFwValue(ll.getId());
                        if(StringUtils.isNotBlank(ss)){
                            String[] sss = ss.split(",");
                            sysDao.getAppLabelGroupDao().addLabel(sss,llp.getId(),llp.getTeamId(),ll.getSignAreaCode(),LabelManageType.JDLK.getValue());
                        }
                    }
                }*/
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 到期续签数据修改续签状态为签约状态
     */
    public void changeRenewSign(){
        try {
            //到期续签数据修改续签状态为签约状态
            List<AppSignForm> xqlist = sysDao.getAppSignformDao().findChangeRenewalList();
            if(xqlist!=null && xqlist.size()>0){
                for(AppSignForm ll:xqlist){
                    ll.setSignState(SignFormType.YUQY.getValue());
                    sysDao.getServiceDo().modify(ll);
                    //查询是否是建档立卡经济类型
                    String jjType = sysDao.getAppLabelGroupDao().findJjValue(ll.getId());
                    if(StringUtils.isNotBlank(jjType)){
                        if(jjType.indexOf(EconomicType.JDLKPKRK.getValue())!=-1){//是建档立卡经济人群
                            String fwType = sysDao.getAppLabelGroupDao().findFwValue(ll.getId());
                            String[] fwTypes = null;
                            if(StringUtils.isNotBlank(fwType)){
                                fwTypes = fwType.split(",");
                            }
                            AppPatientUser user = (AppPatientUser) sysDao.getServiceDo().find(AppPatientUser.class,ll.getSignPatientId());
                            sysDao.getAppSignformDao().addOrModifyArchivingSign(ll.getSignPatientIdNo(),ll.getId(),ll.getSignDrId(),ll.getSignTeamId(),ll.getSignState(),fwTypes,ll.getSignAreaCode(),ll.getSignHospId(),ll.getSignFromDate(),user);
                        }
                    }

//                List<AppArchivingCardPeople> lisp = sysDao.getServiceDo().loadByPk(AppArchivingCardPeople.class,"archivingPatientIdno",ll.getSignPatientIdNo());
//                if(lisp != null && lisp.size()>0){
//                    for(AppArchivingCardPeople llp:lisp){
//                        llp.setSignState(ll.getSignState());
//                        llp.setSignId(ll.getId());
//                        llp.setDrId(ll.getSignDrId());
//                        llp.setTeamId(ll.getSignTeamId());
//                        llp.setSignFromDate(ll.getSignFromDate());
//                        llp.setHospId(ll.getSignHospId());
//                        sysDao.getServiceDo().modify(llp);
//                        //获取服务人群
//                        String ss = sysDao.getAppLabelGroupDao().findFwValue(ll.getId());
//                        if(StringUtils.isNotBlank(ss)){
//                            String[] sss = ss.split(",");
//                            sysDao.getAppLabelGroupDao().addLabel(sss,llp.getId(),llp.getTeamId(),ll.getSignAreaCode(),LabelManageType.JDLK.getValue());
//                        }
//                    }
//                }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 死亡解约调度
     */
    public void surrenderSign(){
       try {
           //查询未执行死亡解约
           List<AppSurrenderSign> list = sysDao.getServiceDo().loadByPk(AppSurrenderSign.class,"patientState","0");
           if(list != null && list.size()>0){
               for(AppSurrenderSign ll:list){
                   AppSignForm form = sysDao.getAppSignformDao().findFormByJmda(ll.getPatientIdNo());
                   if(form != null){
                       form.setSignState(SignFormType.YJY.getValue());
                       form.setSignDelType("1");//死亡
                       form.setSignDieDate(ll.getPatientTimeDeath());
                       form.setSignDelReason(ll.getPatientCauseDeath());
                       sysDao.getServiceDo().modify(form);
                       ll.setPatientState("1");
                       sysDao.getServiceDo().modify(ll);
                       List<AppArchivingCardPeople> lisp = sysDao.getServiceDo().loadByPk(AppArchivingCardPeople.class,"archivingPatientIdno",form.getSignPatientIdNo());
                       if(lisp != null && lisp.size()>0){
                           for(AppArchivingCardPeople llp:lisp){
                               llp.setSignState(null);
                               llp.setSignId(null);
                               llp.setDrId(null);
                               llp.setTeamId(null);
                               llp.setHospId(null);
                               llp.setSignFromDate(null);
                               if(StringUtils.isBlank(llp.getNotSignReason())){
                                   llp.setNotSignReason("15");
                               }
                               sysDao.getServiceDo().modify(llp);
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
     * 处理服务包
     */
    public void dealWithServeMeal(){
        try {
            //查询冗余字段值为空的服务包集合
            List<AppServeSetmeal> list = sysDao.getAppServeSetmealDao().findAllByNotGroup();
            if(list != null && list.size()>0){
                for(AppServeSetmeal ll:list){
                    //每个服务包所需的赋值字段
                    String objectValue = null;//服务对象编号
                    String objectType = null;//服务对象类型
                    String objectTitle = null;//服务对象名称
                    String pkValue = null;//服务内容编号
                    String pkTitle = null;//服务内容名称
                    String pkType = null;//服务内容类型
                    String book = null;//服务内容说明
                    if(StringUtils.isNotBlank(ll.getSersmGroupId())){
                        String[] groupIds = ll.getSersmGroupId().split(";");
                        if(groupIds!=null && groupIds.length>0){
                            for(String groupId:groupIds){
                                AppServeGroup group =(AppServeGroup) sysDao.getServiceDo().find(AppServeGroup.class,groupId);
                                if(group != null){
                                    if(StringUtils.isBlank(objectValue)){
                                        objectValue = group.getSergObjectValue();
                                    }else{
                                        objectValue += ";"+group.getSergObjectValue();
                                    }
                                    if(StringUtils.isBlank(objectTitle)){
                                        objectTitle = group.getSergObjectTitle();
                                    }else{
                                        objectTitle += ","+group.getSergObjectTitle();
                                    }
                                    if(StringUtils.isBlank(objectType)){
                                        objectType = group.getSergObjectType();
                                    }else{
                                        objectType += ";"+group.getSergObjectType();
                                    }
                                    if(StringUtils.isBlank(pkTitle)){
                                        pkTitle = group.getSergPkTitle();
                                    }else{
                                        pkTitle += ";"+group.getSergPkTitle();
                                    }
                                    if(StringUtils.isBlank(pkType)){
                                        pkType = group.getSergPkType();
                                    }else{
                                        pkType += ";"+group.getSergPkType();
                                    }
                                    if(StringUtils.isBlank(pkValue)){
                                        pkValue = group.getSergPkValue();
                                    }else{
                                        pkValue += ";"+group.getSergPkValue();
                                    }
                                    if(StringUtils.isBlank(book)){
                                        book = group.getSergObjectTitle()+":"+group.getSergPkTitle()+"<br/>";
                                    }else{
                                        book += group.getSergObjectTitle()+":"+group.getSergPkTitle()+"<br/>";
                                    }
                                }
                            }
                        }
                    }
                    ll.setSersmObjectValue(objectValue);
                    ll.setSersmObjectTitle(objectTitle);
                    ll.setSersmObjectType(objectType);
                    ll.setSersmPkValue(pkValue);
                    ll.setSersmPkTitle(pkTitle);
                    ll.setSersmPkType(pkType);
                    ll.setSersmBook(book);
                    sysDao.getServiceDo().modify(ll);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 查询建档立卡花名册修改建档情况
     */
    public void findArchivingChangeJd(){
        try {
            System.out.println("开始:");
            List<AppArchivingCardPeople> list = sysDao.getAppArchivingCardPeopeDao().findNotJd();
            if(list != null && list.size()>0){
                System.out.println(list.size());
                for(AppArchivingCardPeople ll:list){
                    if(StringUtils.isNotBlank(ll.getArchivingPatientIdno())){
                        String cityCode = "";
                        if(StringUtils.isNotBlank(ll.getAddrRuralCode())){
                            cityCode = AreaUtils.getAreaCode(ll.getAddrRuralCode(),"2");
                        }else if(StringUtils.isNotBlank(ll.getAddrCountyCode())){
                            cityCode = AreaUtils.getAreaCode(ll.getAddrCountyCode(),"2");
                        }
                        if(StringUtils.isNotBlank(cityCode)){
                            String address = null;
                            String urlType="";
                            JSONObject jsonParam = new JSONObject();
                            AppHealthCardRecodesVo vo = new AppHealthCardRecodesVo();
                            vo.setIdNo(ll.getArchivingPatientIdno());
                            vo.setUrlType(cityCode);
                            CloseableHttpClient client = HttpClients.createDefault();
                            if(StringUtils.isNotBlank(vo.getUrlType())){
                                if (vo.getUrlType().equals(AddressType.FZS.getValue())) {
                                    urlType = AddressType.FZ.getValue();
                                    address = PropertiesUtil.getConfValue("FZ");
                                } else if (vo.getUrlType().equals(AddressType.QZS.getValue())) {
                                    urlType = AddressType.QZ.getValue();
                                    address = PropertiesUtil.getConfValue("QZ");
                                } else if (vo.getUrlType().equals(AddressType.ZZS.getValue())) {
                                    urlType = AddressType.ZZ.getValue();
                                    address = PropertiesUtil.getConfValue("ZZ");
                                } else if (vo.getUrlType().equals(AddressType.PTS.getValue())) {
                                    urlType = AddressType.PT.getValue();
                                    address = PropertiesUtil.getConfValue("PT");
                                } else if (vo.getUrlType().equals(AddressType.NPS.getValue())) {
                                    urlType = AddressType.NP.getValue();
                                    address = PropertiesUtil.getConfValue("NP");
                                } else if (vo.getUrlType().equals(AddressType.SMS.getValue())) {
                                    urlType = AddressType.SM.getValue();
                                    address = PropertiesUtil.getConfValue("SM");
                                } else if (vo.getUrlType().equals(AddressType.CS.getValue())) {
                                    urlType = AddressType.CS.getValue();
                                    address = PropertiesUtil.getConfValue("CS");
                                }else{
                                    urlType = AddressType.SXS.getValue();
                                    address = PropertiesUtil.getConfValue("SX");
                                }
                                jsonParam.put("idNo",vo.getIdNo());
                                jsonParam.put("type","2");
                                jsonParam.put("urlType",urlType);
                                if(address!=null){
                                    String reslut = HtmlUtils.getInstance().executeResponseJson(client, "post", address, jsonParam, "utf-8");
                                    if(reslut!=null && reslut != ""){
                                        JSONObject jsonAll = JSONObject.fromObject(reslut);
                                        if(jsonAll.get("entity")!=""){
                                            String entvo=jsonAll.get("entity").toString();
                                            if(StringUtils.isNotBlank(entvo)){
                                                JSONObject entVo = JSONObject.fromObject(entvo);
                                                String entity =entVo.get("entity").toString();
                                                if(StringUtils.isNotBlank(entity)){
                                                    JSONObject entvoAll = JSONObject.fromObject(entity);
                                                    String jmdah = null;
                                                    String jtdah = null;
                                                    if(entvoAll.get("jmdah")!= null && entvoAll.get("jmdah")!= ""){
                                                        jmdah = entvoAll.get("jmdah").toString();

                                                    }
                                                    if(entvoAll.get("jtdah")!= null && entvoAll.get("jtdah")!= ""){
                                                        jtdah = entvoAll.get("jtdah").toString();
                                                    }
                                                    if(StringUtils.isNotBlank(jmdah)){
                                                        ll.setRhfId(jmdah);
                                                        sysDao.getServiceDo().modify(ll);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                System.out.println("结束");
            }
        } catch (Exception e) {
            new ActionException(this.getClass(), e.getMessage());
        }
    }

    /**
     * 修改建档立卡人员签约情况
     */
    public void changeArchivingSignState(){
        try{
            System.out.println("开始调度changeArchivingSignState方法");
            List<AppArchivingCardPeople> list = sysDao.getAppArchivingCardPeopeDao().findBySignId();
            if(list != null && list.size()>0){
                for(AppArchivingCardPeople pp:list){
//                    AppPatientUser user = sysDao.getAppPatientUserDao().findPatientIdNo(pp.getArchivingPatientIdno());
//                    if(user != null){
//                        AppSignForm form = sysDao.getAppSignformDao().findSignFormByUserState(user.getId());
                    AppSignForm form =sysDao.getAppSignformDao().findByPatientIdno(pp.getArchivingPatientIdno());
                    if(form != null){
                        String fwValue = sysDao.getAppLabelGroupDao().findFwValue(form.getId());
                        String[] fwTypes = null;
                        if(StringUtils.isNotBlank(fwValue)){
                            fwTypes = fwValue.split(",");
                        }
                        AppPatientUser user = (AppPatientUser)sysDao.getServiceDo().find(AppPatientUser.class,form.getSignPatientId());
                        sysDao.getAppSignformDao().addOrModifyArchivingSign(pp.getArchivingPatientIdno(),form.getId(),form.getSignDrId(),form.getSignTeamId(),form.getSignState(),fwTypes,form.getSignAreaCode(),form.getSignHospId(),form.getSignFromDate(),user);
                    }
//                    }
                }
            }
            System.out.println("结束调度changeArchivingSignState方法");
        }catch (Exception e) {
            new ActionException(this.getClass(), e.getMessage());
        }

    }

    //福州查询已解约却未续签，自动续签
    public void findFzSign() throws Exception{
        System.out.println("开始");
        List<AppSignForm> listS = sysDao.getAppSignformDao().findOverdueSign();
        if(listS != null && listS.size()>0){
            for(AppSignForm ls:listS){
                //查询这个患者是否已有签约
                String[] signState = new String[]{SignFormType.YQY.getValue(),SignFormType.YUQY.getValue(),SignFormType.XQ.getValue()};
                AppSignForm form = sysDao.getAppSignformDao().findSignBySignState(signState,ls.getSignPatientId());
                if(form == null){
                    List<AppArchivingCardPeople> lisp = sysDao.getServiceDo().loadByPk(AppArchivingCardPeople.class,"archivingPatientIdno",ls.getSignPatientIdNo());
                    AppSignForm newSignForm = new AppSignForm();
                    AppSignFormEntity signform = new AppSignFormEntity();
                    CopyUtils.Copy(ls, signform);
                    CopyUtils.Copy(signform,newSignForm);
                    String cal1 = ExtendDate.getYMD_h_m_s(ls.getSignToDate());
                    Calendar call = ExtendDate.getCalendar(cal1);
                    call.add(Calendar.DATE, 1);
                    newSignForm.setSignFromDate(call);
                    newSignForm.setSignDate(Calendar.getInstance());
                    Calendar calendarOhter = ExtendDate.getCalendar(cal1);
                    calendarOhter.add(Calendar.YEAR, +1);
                    calendarOhter.add(Calendar.DAY_OF_MONTH, -1);
                    newSignForm.setSignToDate(calendarOhter);
                    newSignForm.setSignState(SignFormType.YUQY.getValue());
                    newSignForm.setSignPayState("0");
                    newSignForm.setSignSurrenderDate(null);
                    newSignForm.setSignUrrenderReason(null);

                    //新生成批次表
                    AppSignBatch newSignBatch = new AppSignBatch();

                    //找到原来批次表的数据
                    AppSignBatch signBatch = (AppSignBatch) sysDao.getServiceDo().find(AppSignBatch.class, newSignForm.getSignBatchId());
                    CopyUtils.Copy(signBatch, newSignBatch);

                    //处理流水号表batch
                    if (StringUtils.isNotBlank(newSignForm.getSignAreaCode())) {
                        AppSerial serial = sysDao.getAppSignformDao().getAppSerial(newSignForm.getSignAreaCode().substring(0, 4), "batch");
                        if (serial != null) {
                            Map<String, Object> batchNum = sysDao.getAppSignformDao().getNum(serial.getSerialNo(), SignFormType.WEBSTATE.getValue());
                            serial.setSerialNo(batchNum.get("old").toString());
                            sysDao.getServiceDo().modify(serial);
                            //生成新的批次号
                            newSignBatch.setBatchNum(batchNum.get("new").toString());//批次号
                        }
                    }
                    sysDao.getServiceDo().add(newSignBatch);

                    //处理流水号表sign
                    if (StringUtils.isNotBlank(newSignForm.getSignAreaCode())) {
                        AppSerial signSerial = sysDao.getAppSignformDao().getAppSerial(newSignForm.getSignAreaCode().substring(0, 4), "sign");
                        if (signSerial != null) {
                            Map<String, Object> signNum = sysDao.getAppSignformDao().getNum(signSerial.getSerialNo(), SignFormType.WEBSTATE.getValue());
                            signSerial.setSerialNo(signNum.get("old").toString());
                            sysDao.getServiceDo().modify(signSerial);
                            //生成新的签约编号
                            newSignForm.setSignNum(signNum.get("new").toString());//批次号
                        }
                    }
                    newSignForm.setSignBatchId(newSignBatch.getId());
                    //自动续签默认服务套餐是基础服务包
                    AppServeSetmeal meal = sysDao.getAppServeSetmealDao().findByValue("35010002");
                    if(meal != null){
                        newSignForm.setSignpackageid(meal.getId());
                        if (org.apache.commons.lang3.StringUtils.isNotBlank(meal.getId())) {
                            String text = sysDao.getAppSignformDao().findPkRemark(meal.getId());
                            newSignForm.setSigntext(text);
                        }
                    }
                    sysDao.getServiceDo().add(newSignForm);
                    ls.setSignGoToSignState("2");
                    ls.setSignContractState("1");
                    sysDao.getServiceDo().modify(ls);
                    //服务人群
                    List<AppLabelGroup> labelList = sysDao.getServiceDo().loadByPk(AppLabelGroup.class, "labelSignId", ls.getId());
                    if(labelList!=null && labelList.size()>0) {
                        for (AppLabelGroup g : labelList) {
                            AppLabelGroup alg = new AppLabelGroup();
                            alg.setLabelId(g.getLabelId());
                            alg.setLabelSignId(newSignForm.getId());
                            alg.setLabelTeamId(newSignForm.getSignTeamId());
                            alg.setLabelTitle(g.getLabelTitle());
                            alg.setLabelValue(g.getLabelValue());
                            alg.setLabelType(g.getLabelType());
                            alg.setLabelColor(g.getLabelColor());
                            alg.setLabelAreaCode(newSignForm.getSignAreaCode());
                            sysDao.getServiceDo().add(alg);
                        }
                    }

                    //疾病类型
                    List<AppLabelDisease> labelDiseases = sysDao.getServiceDo().loadByPk(AppLabelDisease.class,"labelSignId",ls.getId());
                    if(labelDiseases!=null && labelDiseases.size()>0){
                        for(AppLabelDisease g:labelDiseases){
                            AppLabelDisease alg = new AppLabelDisease();
                            alg.setLabelId(g.getLabelId());
                            alg.setLabelSignId(newSignForm.getId());
                            alg.setLabelTeamId(newSignForm.getSignTeamId());
                            alg.setLabelTitle(g.getLabelTitle());
                            alg.setLabelValue(g.getLabelValue());
                            alg.setLabelType(g.getLabelType());
                            alg.setLabelColor(g.getLabelColor());
                            alg.setLabelAreaCode(newSignForm.getSignAreaCode());
                            sysDao.getServiceDo().add(alg);
                        }
                    }
                    //经济类型
                    boolean jdlk = false;
                    List<AppLabelEconomics> listE = sysDao.getServiceDo().loadByPk(AppLabelEconomics.class,"labelSignId",ls.getId());
                    if(listE!=null && listE.size()>0){
                        for(AppLabelEconomics g:listE){
                            if(EconomicType.JDLKPKRK.getValue().equals(g.getLabelValue())){
                                jdlk = true;
                            }
                            AppLabelEconomics alg = new AppLabelEconomics();
                            alg.setLabelId(g.getLabelId());
                            alg.setLabelSignId(newSignForm.getId());
                            alg.setLabelTeamId(newSignForm.getSignTeamId());
                            alg.setLabelTitle(g.getLabelTitle());
                            alg.setLabelValue(g.getLabelValue());
                            alg.setLabelType(g.getLabelType());
                            alg.setLabelColor(g.getLabelColor());
                            alg.setLabelAreaCode(newSignForm.getSignAreaCode());
                            sysDao.getServiceDo().add(alg);
                        }
                    }
                    String fwType = sysDao.getAppLabelGroupDao().findFwValue(ls.getId());
                    String[] fwTypes = null;
                    if(StringUtils.isNotBlank(fwType)){
                        fwTypes = fwType.split(",");
                    }
                    if(jdlk){//经济类型为建档立卡，调用公共方法
                        AppPatientUser user = (AppPatientUser) sysDao.getServiceDo().find(AppPatientUser.class,newSignForm.getSignPatientId());
                        sysDao.getAppSignformDao().addOrModifyArchivingSign(newSignForm.getSignPatientIdNo(),newSignForm.getId(),newSignForm.getSignDrId(),newSignForm.getSignTeamId(),newSignForm.getSignState(),fwTypes,newSignForm.getSignAreaCode(),newSignForm.getSignHospId(),newSignForm.getSignFromDate(),user);
                    }else{
                        //修改花名册数据
                        if(lisp != null && lisp.size()>0){
                            for(AppArchivingCardPeople archivingCardPeople:lisp){
                                //判断签约的机构与花名册居民的街道是否一致，一致修改签约信息
                                if(StringUtils.isNotBlank(archivingCardPeople.getAddrRuralCode())){
                                    if (AreaUtils.getAreaCode(newSignForm.getSignAreaCode(), "2").equals(AreaUtils.getAreaCode(archivingCardPeople.getAddrRuralCode(),"2"))) {
                                        archivingCardPeople.setSignState(newSignForm.getSignState());
                                        archivingCardPeople.setSignId(newSignForm.getId());
                                        archivingCardPeople.setDrId(newSignForm.getSignDrId());
                                        archivingCardPeople.setTeamId(newSignForm.getSignTeamId());
                                        archivingCardPeople.setHospId(newSignForm.getSignHospId());
                                        archivingCardPeople.setSignFromDate(newSignForm.getSignFromDate());
                                        sysDao.getServiceDo().modify(archivingCardPeople);
                                        //添加建档立卡服务标签
                                        sysDao.getAppLabelGroupDao().addLabel(fwTypes,newSignForm.getId(),newSignForm.getSignTeamId(),newSignForm.getSignAreaCode(),LabelManageType.JDLK.getValue());
                                    }
                                }
                            }
                        }
                    }
                }
            }
            System.out.println("结束");
        }
    }

    /**
     * 到期前一个月还未考核的人数消息调度
     */
    public void assessMonthXx(){
      try {
          //查询到期前一个月未考核人数 医生分组
          List<AssessNewsEntity>  list = sysDao.getAssessmentDao().findAssessMonthSign();
          if(list != null && list.size()>0){
              for(AssessNewsEntity ll:list){
                  sysDao.getAppNoticeDao().addNotices("绩效考核消息",
                          "到期前一个月还有 " + ll.getNotAssessNum() +" 人未考核！",
                          NoticesType.JXXX.getValue(), "系统提醒", ll.getDrId(), "", DrPatientType.DR.getValue());
              }
          }
      }catch (Exception e){
          e.printStackTrace();
      }
    }

    /**
     *
     * 每月需上传佐证的考核项目，提前一周提醒还有那些人还未考核
     *
     */
    public void assessWeekXx(){
        try{
            //查询提前一周前还未考核人数 医生分组
            List<AssessNewsEntity>  list = sysDao.getAssessmentDao().findAssessWeekSign();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 每年一月一号调用初始CdAddressPeople表数据
     */
    public void yearCallForCityPeople(){
        try {
            sysDao.getAppCityAreaDao().getCityAreaInitNew();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 处理身份证重复的居民信息
     */
    public void dealWithRepeatUser(){
        try {
            //处理居民身份证有签约单的重复数据
            Map<String,Object> map = new HashedMap();
            List<AppTestPatientEntity> list = sysDao.getAppPatientUserDao().findRepeatPatient();
            if(list != null && list.size()>0){
//                AppTestPatientEntity ll = list.get(0);
                for(AppTestPatientEntity ll:list){
                    //根据身份证查询重复居民各对应居民主键的签约单数量,保留数量最多的
                    List<AppTestPatientEntity> lisSc = sysDao.getAppPatientUserDao().findRepeatPatientByIdno(ll.getPatientIdno());
                    boolean flag = false;
                    String patientId = "";
                    String patientIdno = ll.getPatientIdno();
                    if(lisSc != null && lisSc.size()>0){
                        AppTestPatientEntity tp = lisSc.get(0);
                        //如果第一个的数量值是1则说明重复身份证居民对应的个主键所对应的签约单都只有一条,此时处理方式,保留最新签约单那条记录的数据
                        if("1".equals(tp.getNum().toString())){
                            flag = true;//作为进入最新签约数据保留方法判断
                        }else{//保留最多的签约单
                            patientId = tp.getPatientIdno();//获取保留的居民主键
                        }
                    }
                    if(flag){//相同的签约单,保留最新的
                        //根据身份证查询签约单
                        map.put("patientIdno",ll.getPatientIdno());
                        String sqlSign = "SELECT\n" +
                                "\ta.*\n" +
                                "FROM\n" +
                                "\tapp_sign_form a\n" +
                                "INNER JOIN app_patient_user b ON a.SIGN_PATIENT_ID = b.ID\n" +
                                "WHERE\n" +
                                "\tb.PATIENT_IDNO = :patientIdno\n" +
                                "AND a.SIGN_STATE IN ('0', '2')\n" +
                                "ORDER BY\n" +
                                "\ta.HS_UPDATE_TIME DESC";
                        List<AppSignForm> lisSign = sysDao.getServiceDo().findSqlMap(sqlSign,map,AppSignForm.class);
                        if(lisSign != null && lisSign.size()>0){
                            for (int i=0;i<lisSign.size();i++){
                                AppSignForm form = lisSign.get(i);
                                if(i!=0){//保留第一个最新数据
                                    //取签约单的居民id,查询居民信息记录,修改状态为998
                                    AppPatientUser user = (AppPatientUser)sysDao.getServiceDo().find(AppPatientUser.class,form.getSignPatientId());
                                    if(user != null){
                                        user.setPatientUpHpis("998");
                                        sysDao.getServiceDo().modify(user);
                                    }
                                    //修改签约单位解约状态,原因是身份证重复导致签约单多条
                                    form.setSignState(SignFormType.YJY.getValue());
                                    form.setSignUrrenderReason("身份证重复,签约单重复");
                                    sysDao.getServiceDo().modify(form);
                                }
                            }
                        }
                    }else{//查询此身份证下,不属于此居民主键的居民信息集合和签约信息
                        map.put("idno",patientIdno);
                        map.put("userId",patientId);
                        String sqll = "SELECT * FROM app_patient_user \n" +
                                "WHERE PATIENT_IDNO = :idno\n" +
                                "AND ID <> :userId";
                        List<AppPatientUser> listUser = sysDao.getServiceDo().findSqlMap(sqll,map,AppPatientUser.class);
                        if(listUser != null && listUser.size()>0){
                            for(AppPatientUser user:listUser){
                                user.setPatientUpHpis("998");
                                sysDao.getServiceDo().modify(user);
                                AppSignForm form = sysDao.getAppSignformDao().findSignFormByState(user.getId());
                                if(form != null){
                                    form.setSignState(SignFormType.YJY.getValue());
                                    form.setSignUrrenderReason("身份证重复,签约单重复");
                                    sysDao.getServiceDo().modify(form);
                                }
                            }
                        }
                    }
                }
            }
            System.out.println("结束");
        }catch (Exception e){
          e.printStackTrace();
        }
    }

    /**
     * 删除重复身份证居民造成的签约数据错误的数据
     */
    public void delRepeatSign(){
        try {
            List<AppSignForm> list = sysDao.getAppSignformDao().findSignByRepeatSign();
            if(list != null && list.size()>0){
                for (AppSignForm form:list){
                    //先删label表数据，在删签约单数据
                    //删除服务类型
                    List<AppLabelGroup> listg = sysDao.getAppLabelGroupDao().findBySignGroup(form.getId(),"3");
                    if(listg != null && listg.size()>0){
                        for(AppLabelGroup group:listg){
                            sysDao.getServiceDo().delete(group);
                        }
                    }
                    //删除疾病类型
                    List<AppLabelDisease> listd = sysDao.getAppLabelGroupDao().findBySignDisease(form.getId(),"2");
                    if(listd != null && listd.size()>0){
                        for(AppLabelDisease dis:listd){
                            sysDao.getServiceDo().delete(dis);
                        }
                    }
                    //删除经济类型
                    List<AppLabelEconomics> liste = sysDao.getAppLabelGroupDao().findBySignEcon(form.getId(),"4");
                    if(liste != null && liste.size()>0){
                        for(AppLabelEconomics econ:liste){
                            sysDao.getServiceDo().delete(econ);
                        }
                    }
                    //删除签约单
                    sysDao.getServiceDo().delete(form);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 删除重复的998状态的居民信息
     */
    public void delRepeatPatient(){
       try {
           List<AppPatientUser> list = sysDao.getAppPatientUserDao().findByUpHpis();
           if(list != null && list.size()>0){
               for(AppPatientUser user:list){
                   sysDao.getServiceDo().delete(user);
               }
           }
       }catch (Exception e){
           e.printStackTrace();
       }
    }

    /**
     * 处理建档立卡标签和签约的服务人群不同问题
     */
    public void changeArchivingSignLabel(){
        try{
            System.out.println("开始调度changeArchivingSignLabel方法");
            Map<String,Object> map = new HashMap<>();
            String sql = "SELECT * FROM (\n" +
                    "SELECT\n" +
                    "a.ID,\n" +
                    "a.ARCHIVING_PATIENT_IDNO,\n" +
                    "\t(SELECT GROUP_CONCAT(LABEL_VALUE ORDER BY LABEL_VALUE) FROM app_label_archiving WHERE LABEL_ARCHIVING_ID = a.ID ) arValue,\n" +
                    "\t(SELECT GROUP_CONCAT(LABEL_VALUE ORDER BY LABEL_VALUE) FROM app_label_group WHERE LABEL_SIGN_ID = a.SIGN_ID ) signValue\n" +
                    "FROM\n" +
                    "\tapp_archivingcard_people a\n" +
                    "WHERE\n" +
                    "\ta.SOURCE_TYPE = '3'\n" +
                    "AND a.SIGN_ID IS NOT NULL  ) cc WHERE cc.arValue != cc.signValue ";
            List<Map> lisMap = sysDao.getServiceDo().findSqlMap(sql,map);
            if(lisMap != null && lisMap.size()>0){
                for(Map mm:lisMap){
                    if(mm.get("ID")!= null){
                        AppArchivingCardPeople pp = (AppArchivingCardPeople)sysDao.getServiceDo().find(AppArchivingCardPeople.class,mm.get("ID").toString());
                        if(pp != null){
                            if(StringUtils.isNotBlank(pp.getSignId())){
                                AppSignForm form =(AppSignForm) sysDao.getServiceDo().find(AppSignForm.class,pp.getSignId());
                                if(form != null){
                                    String fwType = sysDao.getAppLabelGroupDao().findFwValue(form.getId());
                                    String[] fwTypes = null;
                                    if (StringUtils.isNotBlank(fwType)) {
                                        fwTypes = fwType.split(",");
                                    }

                                    if(fwTypes != null && fwTypes.length>0){
                                        boolean flag = false;
                                        if(fwTypes.length>1 && fwType.indexOf("1") !=-1){
                                            String urlType = "";
                                            String address = "";
                                            //服务人群不止一个，且有普通人群，查询基卫服务人群确定该居民服务类型
                                            if (AreaUtils.getAreaCode(form.getSignAreaCode(),"2").equals(AddressType.FZS.getValue())) {
                                                urlType = AddressType.FZ.getValue();
                                                address = PropertiesUtil.getConfValue("FZurlFwType");
                                            } else if (AreaUtils.getAreaCode(form.getSignAreaCode(),"2").equals(AddressType.QZS.getValue())) {
                                                urlType = AddressType.QZ.getValue();
                                                address = PropertiesUtil.getConfValue("QZurlFwType");
                                            } else if (AreaUtils.getAreaCode(form.getSignAreaCode(),"2").equals(AddressType.ZZS.getValue())) {
                                                urlType = AddressType.ZZ.getValue();
                                                address = PropertiesUtil.getConfValue("ZZurlFwType");
                                            } else if (AreaUtils.getAreaCode(form.getSignAreaCode(),"2").equals(AddressType.PTS.getValue())) {
                                                urlType = AddressType.PT.getValue();
                                                address = PropertiesUtil.getConfValue("PTurlFwType");
                                            } else if (AreaUtils.getAreaCode(form.getSignAreaCode(),"2").equals(AddressType.NPS.getValue())) {
                                                urlType = AddressType.NP.getValue();
                                                address = PropertiesUtil.getConfValue("NPurlFwType");
                                            } else if (AreaUtils.getAreaCode(form.getSignAreaCode(),"2").equals(AddressType.SMS.getValue())) {
                                                urlType = AddressType.SM.getValue();
                                                address = PropertiesUtil.getConfValue("SMurlFwType");
                                            } else if (AreaUtils.getAreaCode(form.getSignAreaCode(),"2").equals(AddressType.CS.getValue())) {
                                                urlType = AddressType.CS.getValue();
                                                address = PropertiesUtil.getConfValue("CSurlFwType");
                                            }else{
                                                urlType = AddressType.SXS.getValue();
                                                address = PropertiesUtil.getConfValue("SXurlFwType");
                                            }
                                            JSONObject jsonParam = new JSONObject();// 调用的参数
                                            jsonParam.put("idNo", pp.getArchivingPatientIdno());
                                            jsonParam.put("urlType", urlType);
                                            CloseableHttpClient client = HttpClients.createDefault();
                                            String result = HtmlUtils.getInstance().executeResponseJson(client, "post", address, jsonParam, "utf-8");
                                            if (result != null) {// 接口调用正常
                                                JSONObject jsonAll = JSONObject.fromObject(result);
                                                if ("800".equals(jsonAll.get("msgCode").toString())) {
                                                    if(jsonAll.get("vo").toString() != null){
                                                        AppDrPatientFwEntity ls = JSON.parseObject(jsonAll.get("vo").toString(), AppDrPatientFwEntity.class);
                                                        if(ls != null){
                                                            if("1".equals(ls.getIs06child())){//0-6儿童
                                                                flag = true;
                                                            }
                                                            if("1".equals(ls.getIslnr())){//老年人
                                                                flag = true;
                                                            }
                                                            if("1".equals(ls.getIsycf())){//孕产妇
                                                                flag = true;
                                                            }
                                                            if("1".equals(ls.getIsgxy())){//高血压
                                                                flag = true;
                                                            }
                                                            if("1".equals(ls.getIstnb())){//糖尿病
                                                                flag = true;
                                                            }
                                                            if("1".equals(ls.getIsjhb())){//结核病
                                                                flag = true;
                                                            }
                                                            if("1".equals(ls.getIscjr())){//残疾人
                                                                flag = true;
                                                            }
                                                            if("1".equals(ls.getIszxjsb())){//重性精神病
                                                                flag = true;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            if(flag){//是重点人群，删除普通人群服务
                                                AppLabelGroup group = sysDao.getAppLabelGroupDao().findGroupBySignAndValue(form.getId(),"1");
                                                if(group != null){
                                                    sysDao.getServiceDo().delete(group);
                                                }
                                                fwType = fwType.replace("1,","");
                                                sysDao.getAppLabelGroupDao().addLabel(fwType.split(","), pp.getId(), form.getSignTeamId(), form.getSignAreaCode(), LabelManageType.JDLK.getValue());
                                            }
                                        }else{
                                            sysDao.getAppLabelGroupDao().addLabel(fwTypes, pp.getId(), form.getSignTeamId(), form.getSignAreaCode(), LabelManageType.JDLK.getValue());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            System.out.println("结束调度changeArchivingSignLabel方法");
        }catch (Exception e) {
            new ActionException(this.getClass(), e.getMessage());
        }
    }

    /**
     * 从省库同步医生和机构数据到互联网阿里云
     */
    public void syDrAndHosp(){
//        List<WebDrUser> listDr =
    }
}
