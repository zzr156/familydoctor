package com.ylz.view.ysapp.action;

import com.ylz.bizDo.app.entity.AppWorkdaySettingEntity;
import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.app.vo.AppWorkdaySettingQvo;
import com.ylz.bizDo.jtapp.drEntity.AppDrTzEntity;
import com.ylz.bizDo.jtapp.drEntity.AppDrTzyjSetEntity;
import com.ylz.bizDo.jtapp.drEntity.AppSignsWarningRecordEntity;
import com.ylz.bizDo.jtapp.drVo.AppDrTzQvo;
import com.ylz.bizDo.jtapp.drVo.AppDrYjSetQvo;
import com.ylz.bizDo.jtapp.drVo.AppSignsWarQvo;
import com.ylz.bizDo.jtapp.drVo.AppSignsWarningRecordQvo;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.comEnum.DrPatientType;
import com.ylz.packcommon.common.comEnum.NoticesType;
import com.ylz.packcommon.common.util.ExtendDate;
import com.ylz.packcommon.common.util.ExtendDateUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.joda.time.DateTimeConstants;

import java.util.*;

/**
 * 医生工作日设置
 */
@SuppressWarnings("all")
@Action(
        value="ysworkday",
        results={
                @Result(name = "ajson", type = "json",params={"root","ajson","contentType", "application/json"})
        }
)
public class YsAppWorkDayAction  extends CommonAction {


    /**
     * 工作日设置
     * @return
     */
    public String appWorksheet() {
        try {
            AppWorkdaySettingEntity vo = (AppWorkdaySettingEntity) getAppJson(AppWorkdaySettingEntity.class);
            if(vo != null){
                AppDrUser drUser = this.getAppDrUser();
                AppWorkdaySetting p = this.getSysDao().getAppWorkdaySettingDao().findByDoctorId(drUser.getId());
                if(p != null){
                    p.setWsClosingTime(vo.getWsClosingTime());
                    p.setWsNightTime(vo.getWsNightTime());
                    p.setWsWorkShift(vo.getWsWorkShift());
                    if(vo.getWsWeek() != null && vo.getWsWeek().size() >0 ){
                        if(vo.getWsWeek().size() == 7){
                            int i = 0;
                            for(String str : vo.getWsWeek()){
                                if(i == 0){
                                    p.setWsMon(str);
                                }
                                if(i == 1){
                                    p.setWsTues(str);
                                }
                                if(i == 2){
                                    p.setWsWed(str);
                                }
                                if(i == 3){
                                    p.setWsThur(str);
                                }
                                if(i == 4){
                                    p.setWsFri(str);
                                }
                                if(i == 5){
                                    p.setWsSat(str);
                                }
                                if(i == 6){
                                    p.setWsSun(str);
                                }
                                i++;
                            }
                        }else{
                            this.getAjson().setMsg("参数格式错误!");
                            this.getAjson().setMsgCode("900");
                            return "ajson";
                        }
                    }
                    this.getSysDao().getServiceDo().modify(p);
                }else{
                    p = new AppWorkdaySetting();
                    p.setWsCreatetime(Calendar.getInstance());
                    p.setWsDrId(drUser.getId());
                    p.setWsWorkShift(vo.getWsWorkShift());
                    p.setWsNightTime(vo.getWsNightTime());
                    p.setWsClosingTime(vo.getWsClosingTime());
                    if(vo.getWsWeek() != null && vo.getWsWeek().size() >0 ){
                        if(vo.getWsWeek().size() == 7){
                            int i = 0;
                            for(String str : vo.getWsWeek()){
                                if(i == 0){
                                    p.setWsMon(str);
                                }
                                if(i == 1){
                                    p.setWsTues(str);
                                }
                                if(i == 2){
                                    p.setWsWed(str);
                                }
                                if(i == 3){
                                    p.setWsThur(str);
                                }
                                if(i == 4){
                                    p.setWsFri(str);
                                }
                                if(i == 5){
                                    p.setWsSat(str);
                                }
                                if(i == 6){
                                    p.setWsSun(str);
                                }
                                i++;
                            }
                        }else{
                            this.getAjson().setMsg("参数格式错误!");
                            this.getAjson().setMsgCode("900");
                            return "ajson";
                        }
                    }
                    this.getSysDao().getServiceDo().add(p);
                }
                this.getAjson().setMsg("保存成功!");
            }else{
                this.getAjson().setMsg("参数格式错误!");
                this.getAjson().setMsgCode("900");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }


    /**
     * 工作日查询
     * @return
     */
    public String appWorksheetLook() {
        try {
            AppWorkdaySettingQvo qvo = (AppWorkdaySettingQvo)this.getAppJson(AppWorkdaySettingQvo.class);
            if(qvo != null) {
                AppWorkdaySetting p = this.getSysDao().getAppWorkdaySettingDao().findByDoctorId(qvo.getWsDrId());
                if(p != null){
                    AppWorkdaySettingEntity entity = new AppWorkdaySettingEntity();
                    entity.setId(p.getId());
                    entity.setWsClosingTime(p.getWsClosingTime());
                    entity.setWsNightTime(p.getWsNightTime());
                    entity.setWsWorkShift(p.getWsWorkShift());
                    List<String> ls = new ArrayList<String>();
                    ls.add(p.getWsMon());
                    ls.add(p.getWsTues());
                    ls.add(p.getWsWed());
                    ls.add(p.getWsThur());
                    ls.add(p.getWsFri());
                    ls.add(p.getWsSat());
                    ls.add(p.getWsSun());
                    entity.setWsWeek(ls);
                    this.getAjson().setVo(entity);
                }
            }else{
                this.getAjson().setMsg("参数格式错误!");
                this.getAjson().setMsgCode("900");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 判断当前医生是否是工作日
     * @return
     */
    public String appWorkBooleanWork(){
        try {
            AppWorkdaySettingQvo qvo = (AppWorkdaySettingQvo)this.getAppJson(AppWorkdaySettingQvo.class);
            if(qvo != null) {
                this.getAjson().setEntity("0");
                AppWorkdaySetting p = this.getSysDao().getAppWorkdaySettingDao().findByDoctorId(qvo.getWsDrId());
                if(p != null) {
                    boolean blWeek = false;
                    int week = ExtendDateUtil.getWeekOfDate(ExtendDate.getYMD(Calendar.getInstance()));
                    String result = null;
                    switch(week) {
                        case DateTimeConstants.SUNDAY:
                            result = p.getWsSun();
                            break;
                        case DateTimeConstants.MONDAY:
                            result = p.getWsMon();
                            break;
                        case DateTimeConstants.TUESDAY:
                            result = p.getWsTues();
                            break;
                        case DateTimeConstants.WEDNESDAY:
                            result = p.getWsWed();
                            break;
                        case DateTimeConstants.THURSDAY:
                            result = p.getWsThur();
                            break;
                        case DateTimeConstants.FRIDAY:
                            result = p.getWsFri();
                            break;
                        case DateTimeConstants.SATURDAY:
                            result = p.getWsSat();
                            break;
                    }
                    if(StringUtils.isNotBlank(result)){
                        String time = ExtendDate.getHHMM(Calendar.getInstance());
                        String start = null;
                        String end = null;
                        if(result.contains("1")){
                            String[] results = result.split(",");
                            if(results != null){
                                for(int i=0;i<results.length;i++){
                                    if(results[i].equals("1")){
                                          if(i == 0){
                                              start = p.getWsWorkShift().split(",")[0];
                                              end = p.getWsWorkShift().split(",")[1];
                                          }else if(i == 1){
                                              start = p.getWsClosingTime().split(",")[0];
                                              end = p.getWsClosingTime().split(",")[1];
                                          }else if(i == 2){
                                              start = p.getWsNightTime().split(",")[0];
                                              end = p.getWsNightTime().split(",")[1];
                                          }
                                          if(ExtendDate.isInZone(ExtendDate.getLong(start),ExtendDate.getLong(end),ExtendDate.getLong(time))){
                                             blWeek = true;
                                             break;
                                          }
                                    }
                                }
                            }
                        }
                        if(!blWeek){
                            this.getAjson().setMsg("您好，该时间段医生不提供咨询服务，请查看医生工作时间表！");
                            this.getAjson().setEntity("1");
                        }
                    }else{
                        this.getAjson().setMsg("参数格式错误!");
                        this.getAjson().setMsgCode("900");
                    }
                }
            }else{
                this.getAjson().setMsg("参数格式错误!");
                this.getAjson().setMsgCode("900");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 医生设置所管居民未更新体征数据的预警时间
     * @return
     */
    public String setSigns(){
        try{
            AppDrYjSetQvo qvo = (AppDrYjSetQvo)this.getAppJson(AppDrYjSetQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                AppDrUser drUser = this.getAppDrUser();
                if(drUser!=null){
                    if(qvo.getTnb()!=null){
                        qvo.getTnb().setDrId(drUser.getId());
                    }
                    if(qvo.getGxy()!=null){
                        qvo.getGxy().setDrId(drUser.getId());
                    }
                    String str = sysDao.getAppSignsWarningSettingDao().setSigns(qvo);
                    if("true".equals(str)){
                        this.getAjson().setMsgCode("800");
                        this.getAjson().setMsg("设置成功");
                    }else{
                        this.getAjson().setMsgCode("900");
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 医生获取居民未及时检测消息列表
     * @return
     */
    public String findTzxxList(){
        try{
            AppDrUser drUser = this.getAppDrUser();
            List<AppSignsWarningRecordEntity> list = sysDao.getAppSignsWarningSettingDao().findTzxxList(drUser.getId());
            if(list!=null && list.size()>0){
                for(AppSignsWarningRecordEntity ll:list){
                    String content = ll.getPatientName()+"("+ll.getColorName()+ll.getDisTypeName()+"),已连续"+ll.getDayNum()+"天未测量，请及时处理。";
                    ll.setContent(content);
                }
                this.getAjson().setMsgCode("800");
                this.getAjson().setRows(list);
            }else{
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("暂无数据");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 发送消息给患者
     * @return
     */
    public String fsTzxxToPatient(){
        try{
            AppSignsWarningRecordQvo qvo = (AppSignsWarningRecordQvo)this.getAppJson(AppSignsWarningRecordQvo.class);
            if(qvo == null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                AppDrUser drUser = this.getAppDrUser();
                if(drUser!=null){
                    qvo.setDrId(drUser.getId());
                }

                String str = sysDao.getAppNoticeDao().fsTzxxToPatient(qvo);

                /*AppSignsWarningRecord record = (AppSignsWarningRecord)sysDao.getServiceDo().find(AppSignsWarningRecord.class,qvo.getId());
                sysDao.getAppNoticeDao().addNotices("体征指标预警",
                        "您好，您已连续"+record.getSwrDayNum()+"天未进行"+record.getDisTypeName()+"测量，请及时进行自我健康监测。("+record.getDrName()+":"+record.getDrWorkName()+")",
                        NoticesType.JKJCYCTX.getValue(),record.getSwrDrId(),record.getSwrUserId(),record.getId(),DrPatientType.PATIENT.getValue());
                record.setSwrTxState("1");
                sysDao.getServiceDo().modify(record);*/
                if("true".equals(str)){
                    this.getAjson().setMsgCode("800");
                    this.getAjson().setMsg("发送成功");
                }else {
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg(str);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }


    /**
     * 回拨
     * @return
     */
    public String dialBack(){
        try{
            AppSignsWarningRecordQvo qvo = (AppSignsWarningRecordQvo)this.getAppJson(AppSignsWarningRecordQvo.class);
            if(qvo == null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                AppSignsWarningRecord record = (AppSignsWarningRecord)sysDao.getServiceDo().find(AppSignsWarningRecord.class,qvo.getId());
                record.setSwrTxState("1");
                sysDao.getServiceDo().modify(record);
                this.getAjson().setMsgCode("800");
                this.getAjson().setMsg("回拨成功");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 查询体征数据居民列表
     * @return
     */
    public String findPatientList(){
        try{
            AppDrTzQvo qvo = (AppDrTzQvo)this.getAppJson(AppDrTzQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                AppDrUser drUser = this.getAppDrUser();
                if(drUser!=null){
                    qvo.setDrId(drUser.getId());
                }
                Map<String,Object> map = new HashMap<String,Object>();
                int gxyCount = 0;
                int tnbCount = 0;
                List<AppDrTzEntity> gxyList = new ArrayList<AppDrTzEntity>();
                List<AppDrTzEntity> tnbList = new ArrayList<AppDrTzEntity>();
                List<AppDrTzEntity> list = sysDao.getAppPatientUserDao().findTzJmList(qvo);
                if(list!=null && list.size()>0){
                    for(AppDrTzEntity ll:list){
                        if("201".equals(ll.getDisType())){
                            gxyCount++;
                            gxyList.add(ll);
                        }else if("202".equals(ll.getDisType())){
                            tnbCount++;
                            tnbList.add(ll);
                        }

                    }
                }
                map.put("gxyCount",gxyCount);
                map.put("gxyList",gxyList);
                map.put("tnbCount",tnbCount);
                map.put("tnbList",tnbList);
                this.getAjson().setMsgCode("800");
                this.getAjson().setMap(map);
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 查询个人体征预警设置
     * @return
     */
    public String findTyTzSet(){
        try{
            AppDrTzQvo qvo = (AppDrTzQvo)this.getAppJson(AppDrTzQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                AppDrUser drUser = this.getAppDrUser();
                if(drUser!=null){
                    qvo.setDrId(drUser.getId());
                    List<AppDrTzyjSetEntity> vos = sysDao.getAppSignsWarningSettingDao().findTzyjSet(qvo);
                    if(vos!=null && vos.size()>0){
                        this.getAjson().setMsgCode("800");
                        this.getAjson().setVo(vos.get(0));
//                        this.getAjson().setRows(vos);
                    }

                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    public String findTztxSet(){
        try{
            AppDrTzQvo qvo = (AppDrTzQvo)this.getAppJson(AppDrTzQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                AppDrUser drUser = this.getAppDrUser();
                if(drUser!=null) {
                    Map<String,Object> map = new HashMap<String,Object>();
                    qvo.setDrId(drUser.getId());
                    List<AppDrTzyjSetEntity> vo = sysDao.getAppSignsWarningSettingDao().findTzyjSet(qvo);
                    map.put("setVo",vo);//查询通用
                    List<AppWarningSetting> setlist = sysDao.getServiceDo().loadByPk(AppWarningSetting.class, "wsUserId", qvo.getDrId());
                    map.put("setList",setlist);//随访预警设置
                    List<AppDrTzEntity> list = sysDao.getAppSignsWarningSettingDao().findPeopleList(qvo);
                    map.put("peopleList",list);
                    this.getAjson().setMap(map);
                    this.getAjson().setMsgCode("800");
                }
            }


        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 删除体征设置
     * @return
     */
    public String delTzSet(){
        try {
            AppDrTzQvo qvo = (AppDrTzQvo)this.getAppJson(AppDrTzQvo.class);
            if(qvo == null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                AppDrUser drUser = this.getAppDrUser();
                if(drUser!=null){
                    qvo.setDrId(drUser.getId());
                    String str = sysDao.getAppSignsWarningSettingDao().delTzSet(qvo);
                    if("true".equals(str)){
                        this.getAjson().setMsgCode("800");
                        this.getAjson().setMsg("删除成功");
                    }else {
                        this.getAjson().setMsg("删除失败");
                        this.getAjson().setMsgCode("900");
                    }
                }

            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 查询体征预警消息人员列表
     * @return
     */
    public String findTzxxPeople(){
        try{
            AppSignsWarningRecordQvo qvo = (AppSignsWarningRecordQvo)this.getAppJson(AppSignsWarningRecordQvo.class);
            if(qvo == null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
               // List<AppSignsRecordTable> list = sysDao.getServiceDo().loadByPk(AppSignsRecordTable.class,"srtCode",qvo.getCode());
                List<AppSignsRecordTable> list = sysDao.getAppNoticeDao().findTzxxPeople(qvo);
                if(list!=null && list.size()>0){
                    this.getAjson().setQvo(qvo);
                    this.getAjson().setMsgCode("800");
                    this.getAjson().setRows(list);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }
}
