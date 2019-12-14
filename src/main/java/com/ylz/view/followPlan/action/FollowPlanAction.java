package com.ylz.view.followPlan.action;

import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppFollowSetting;
import com.ylz.bizDo.app.po.AppLabelManage;
import com.ylz.bizDo.app.po.AppWarningSetting;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.jtapp.commonEntity.AppGluEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppPressureEntity;
import com.ylz.bizDo.jtapp.drVo.AppWarningSettingQvo;
import com.ylz.bizDo.plan.Entity.*;
import com.ylz.bizDo.plan.po.*;
import com.ylz.bizDo.plan.vo.*;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.CommonEnable;
import com.ylz.packcommon.common.comEnum.FollowPlanState;
import com.ylz.packcommon.common.comEnum.ResidentMangeType;
import com.ylz.packcommon.common.util.JsonUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2017/7/23.
 */
@SuppressWarnings("all")
@Action(
        value = "follow",
        results = {
                @Result(name = "ajson", type = "json", params = {"root", "ajson", "contentType", "application/json"})
        }
)
public class FollowPlanAction extends CommonAction {

    /**
     * 随访初始化
     */
    public String appFollowPlanInit() {
        try {
            List<CdCode> followWays = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_FOLLOWWAY, CommonEnable.QIYONG.getValue());
            List<AppLabelManage> serviceGroups = this.sysDao.getAppLabelManageDao().findByType("3");
            HashMap<String,Object> map = new HashMap<>();
            map.put("followWays",followWays);
            map.put("serviceGroups",serviceGroups);
            this.getAjson().setMap(map);
            this.getAjson().setMsgCode("800");
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 根据服务人群筛选(制定随访计划)
     * @param qvo group
     * @return
     */
    public String appFollowGroup() {
       try {
           AppFollowGroupQvo qvo = (AppFollowGroupQvo) getAppJson(AppFollowGroupQvo.class);
           AppDrUser drUser = getAppDrUser();
           if (qvo == null || drUser==null) {
               this.getAjson().setMsgCode("900");
               this.getAjson().setMsg("参数格式错误");
           } else {
//               qvo.setDrId(drUser.getId());
               if(StringUtils.isNotBlank(qvo.getTeamId())){
                   List<AppFollowGroupEntity> list = sysDao.getAppNewFollowPlanDao().findFollowByGroup(qvo);
                   this.getAjson().setRows(list);
                   this.getAjson().setQvo(qvo);
                   this.getAjson().setMsgCode("800");
               }else{
                   this.getAjson().setMsg("团队id不能为空!");
                   this.getAjson().setMsgCode("900");
               }

           }
       }catch (Exception e){
           e.printStackTrace();
       }
        return "ajson";
    }

    /**
     * 随访当天预警设置
     * @return
     */
    public String appFollowWarnDay() {
        AppFollowWarnQvo qvo = (AppFollowWarnQvo) getAppJson(AppFollowWarnQvo.class);
        AppDrUser drUser = getAppDrUser();
        if (qvo == null || drUser ==null) {
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("参数格式错误");
        } else {
            List<AppFollowSetting> list = sysDao.getServiceDo().loadByPk(AppFollowSetting.class,"fsDoctorId",drUser.getId());
            if(list!=null && !list.isEmpty()){
                AppFollowSetting dayFollow = list.get(0);
                dayFollow.setFsState(qvo.getWarnState());
                dayFollow.setFsMorning(qvo.getMorningTime());
                dayFollow.setFsNoon(qvo.getNoonTime());
                dayFollow.setFsNight(qvo.getNightTime());
                sysDao.getServiceDo().modify(dayFollow);
            }else{
                AppFollowSetting follow = new AppFollowSetting();
                follow.setFsDoctorId(drUser.getId());
                follow.setFsState(qvo.getWarnState());
                follow.setFsMorning(qvo.getMorningTime());
                follow.setFsNoon(qvo.getNoonTime());
                follow.setFsNight(qvo.getNightTime());
                sysDao.getServiceDo().add(follow);
            }
            this.getAjson().setMsg("设置成功");
            this.getAjson().setMsgCode("800");
        }
        return "ajson";
    }

    /**
     * 随访预警设置
     * @return
     */
    public String appFollowWarn() {
        try {
            AppWarningSettingQvo qvo = (AppWarningSettingQvo) getAppJson(AppWarningSettingQvo.class);
            AppDrUser drUser = getAppDrUser();
            if (qvo == null || drUser ==null) {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            } else {
                List<AppWarningSetting> list = sysDao.getAppWarningSettingDao().findSetting(drUser.getId(), qvo.getType());
                if(list!=null && !list.isEmpty()){
                    AppWarningSetting set = list.get(0);
                    set.setWsState(qvo.getState());
                    if(StringUtils.isNotBlank(qvo.getNum())) {
                        set.setWsNum(qvo.getNum());
                    }
                    sysDao.getServiceDo().modify(set);
                    this.getAjson().setVo(set);
                }else{
                    AppWarningSetting set = new AppWarningSetting();
                    set.setWsUserId(drUser.getId());
                    set.setWsType(qvo.getType());
                    set.setWsState(qvo.getState());
                    if(StringUtils.isNotBlank(qvo.getNum())) {
                        set.setWsNum(qvo.getNum());
                    }
                    sysDao.getServiceDo().add(set);
                    this.getAjson().setVo(set);
                }
                this.getAjson().setMsg("设置成功");
                this.getAjson().setMsgCode("800");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 查询随访预警
     * @return
     */
    public String appFindFollowWarn(){
        AppDrUser drUser = getAppDrUser();
        try {
            if (drUser==null) {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            } else {
//                HashMap<String,Object> map = new HashMap<>();
//                List<AppFollowSetting> list = sysDao.getServiceDo().loadByPk(AppFollowSetting.class,"fsDoctorId",drUser.getId());
//                if(list!=null && !list.isEmpty()){
//                    map.put("dayTime",list.get(0));
//                }
                List<AppWarningSetting> setlist = sysDao.getServiceDo().loadByPk(AppWarningSetting.class, "wsUserId", drUser.getId());
                if (setlist != null && setlist.size() > 0) {
                    this.getAjson().setRows(setlist);
                    this.getAjson().setMsg("800");
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
     * 开始随访获取人员基本信息 包括服务类型与随访记录
     * @return
     */
    public String appFollowPatienBasic(){
        AppFollwCommQvo qvo=(AppFollwCommQvo) getAppJson(AppFollwCommQvo.class);
        try {
            if (qvo == null) {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            } else {
                //获取基本信息
                AppFollowPatienBasic vo=sysDao.getAppSaveFollowTableDao().findFollBasic(qvo);
                getAjson().setVo(vo);
                //获取随访记录
                //List<AppFollowPlan> ls=sysDao.getAppSaveFollowTableDao().findFollLosg(qvo);

                //getAjson().setRows(ls);
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 新增随访计划(制定随访计划)
     * @param patientId;//多个用分割隔开
     * @param followWay;//随访方式
     * @param teamId;
     * @param followDate;//随访计划时间
     * @return
     */
    public String appAddFollowPlan(){
        AppFollowAddQvo qvo=(AppFollowAddQvo) getAppJson(AppFollowAddQvo.class);
        AppDrUser drUser = getAppDrUser();
        try {
            if (qvo == null || drUser==null) {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            } else {
                if(StringUtils.isNotBlank(qvo.getPatientId())){
                    String[] patientIds = qvo.getPatientId().split(";");
                    for(int i=0;i<patientIds.length;i++){
                        qvo.setPatientId(patientIds[i]);
                        AppFollowPlan plan = sysDao.getAppNewFollowPlanDao().addFollowPlan(qvo,drUser);
                        this.getAjson().setVo(plan);
                    }
                    this.getAjson().setMsgCode("800");
                    this.getAjson().setMsg("添加成功");
                    return "ajson";
                }else{
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("参数格式错误");
                    return "ajson";
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
     * 最后随访诊断单获取 根据患者id和服务人群类型 LableValue
     * @param qvo id patientId 患者id persGroup 服务人群类型
     * @return
     * @throws Exception
     */
    public String findMaxPersGroupTwo(){
        AppFollwCommQvo qvo=(AppFollwCommQvo) getAppJson(AppFollwCommQvo.class);
        try {
            if (qvo == null) {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            } else {
                Object vo=sysDao.getAppSaveFollowTableDao().findMaxPersGroup(qvo);
                HashMap<String,Object> map = new HashMap<>();
                if(ResidentMangeType.TNB.getValue().equals(qvo.getPersGroup())) {
                    AppGluEntity glu = sysDao.getAppUserBloodgluDao().findLatestT(qvo.getPatientId());
                    map.put("glu",glu);
                    AppPressureEntity pressureEntity = sysDao.getAppUserBloodpressureDao().findLatest(qvo.getPatientId());
                    map.put("pressure",pressureEntity);
                }
                if(ResidentMangeType.GXY.getValue().equals(qvo.getPersGroup())) {
                    AppPressureEntity pressureEntity = sysDao.getAppUserBloodpressureDao().findLatest(qvo.getPatientId());
                    map.put("pressure",pressureEntity);
                }
                AppFollowPlan plan = (AppFollowPlan) sysDao.getServiceDo().find(AppFollowPlan.class,qvo.getId());
                if(plan!=null){
                    List<AppFollowPublic> lss = sysDao.getServiceDo().loadByPk(AppFollowPublic.class,"followNum",plan.getSfFollowNum());
                    if(lss!=null&&lss.size()>0){
                        this.getAjson().setRows(lss);
                    }
                }
                getAjson().setVo(vo);
                getAjson().setMap(map);
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }
    /**
     * 最后随访诊断单获取 根据患者id和服务人群类型 LableValue
     * @param qvo id patientId 患者id persGroup 服务人群类型
     * @return
     * @throws Exception
     */
    public String findMaxPersGroup(){
        AppFollwCommQvo qvo=(AppFollwCommQvo) getAppJson(AppFollwCommQvo.class);
        try {
            if (qvo == null) {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            } else {
                Object vo=sysDao.getAppSaveFollowTableDao().findMaxPersGroup(qvo);
                HashMap<String,Object> map = new HashMap<>();
                if(qvo.getPersGroup().equals("6")) {
                    AppGluEntity glu = sysDao.getAppUserBloodgluDao().findLatest(qvo.getPatientId());
                    map.put("glu",glu);
                }
                if(qvo.getPersGroup().equals("5")) {
                    AppPressureEntity pressureEntity = sysDao.getAppUserBloodpressureDao().findLatest(qvo.getPatientId());
                    map.put("pressure",pressureEntity);
                }
                getAjson().setVo(vo);
                getAjson().setMap(map);
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 保存高血压随访记录
     * @return
     */
    public String saveHdBloo(){
        try{
            AppSaveFollowQvo qvo = (AppSaveFollowQvo)getAppJson(AppSaveFollowQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                if(StringUtils.isNotBlank(qvo.getSaveHdBloo().getId())){
                    AppHdBlooPressureTable vv = (AppHdBlooPressureTable)sysDao.getServiceDo().find(AppHdBlooPressureTable.class,qvo.getSaveHdBloo().getId());
                    if(vv!=null){
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("此次随访已保存，不可重复保存");
                        return "ajson";
                    }
                }
                AppDrUser user = this.getAppDrUser();
                if(user!=null){
                    qvo.getSaveHdBloo().setVisitDoctorName(user.getId());
                }
                AppSaveFollowQvo vo = sysDao.getAppSaveFollowTableDao().saveHdBloo(qvo);
                this.getAjson().setVo(vo.getSaveHdBloo());
                this.getAjson().setRows(vo.getUserMedicine());
                this.getAjson().setMsgCode("800");
            }

        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 保存糖尿病随访记录
     * @return
     */
    public String saveDiabetes(){
        try{
            AppSaveFollowQvo qvo = (AppSaveFollowQvo)getAppJson(AppSaveFollowQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                if(StringUtils.isNotBlank(qvo.getSaveDiabetes().getId())){
                    AppDiabetesTable vv = (AppDiabetesTable)sysDao.getServiceDo().find(AppDiabetesTable.class,qvo.getSaveDiabetes().getId());
                    if(vv!=null){
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("此次随访已保存，不可重复保存");
                        return "ajson";
                    }
                }
                AppDrUser user = this.getAppDrUser();
                if(user!=null){
                    qvo.getSaveDiabetes().setVisitDoctorName(user.getId());
                }
                AppSaveFollowQvo vo = sysDao.getAppSaveFollowTableDao().saveDiabetes(qvo);
                this.getAjson().setVo(vo.getSaveDiabetes());
                this.getAjson().setRows(vo.getUserMedicine());
                this.getAjson().setMsgCode("800");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 查询随访计划
     * @param qvo group
     * @param qvo day
     * @param qvo patientName
     * @return
     */
    public String appFindFollowPlan() {
        try {
            AppFollowGroupQvo qvo = (AppFollowGroupQvo) getAppJson(AppFollowGroupQvo.class);
            AppDrUser drUser = getAppDrUser();
            if (qvo == null || drUser==null) {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            } else {
//                qvo.setDrId(drUser.getId());
                if(StringUtils.isNotBlank(qvo.getTeamId())){
                    List<AppFollowGroupEntity> list = sysDao.getAppNewFollowPlanDao().findFollowPlan(qvo);
                    this.getAjson().setRows(list);
                    this.getAjson().setQvo(qvo);
                    this.getAjson().setMsgCode("800");
                }else{
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("团队id不能为空!");
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
     * 查询医生的随访记录
     * @param qvo group
     * @param qvo startDate
     * @param qvo endDate
     * @param qvo patientName
     * @return
     */
    public String appFindFollowRecord() {
        try {
            AppFollowGroupQvo qvo = (AppFollowGroupQvo) getAppJson(AppFollowGroupQvo.class);
            AppDrUser drUser = getAppDrUser();
            if (qvo == null || drUser==null) {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            } else {
                qvo.setDrId(drUser.getId());
                List<AppFollowGroupEntity> list = sysDao.getAppNewFollowPlanDao().findFollowRecord(qvo);
                this.getAjson().setRows(list);
                this.getAjson().setQvo(qvo);
                this.getAjson().setMsgCode("800");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 查询患者的随访记录
     * @param patientId
     * @return
     */
    public String appFindPersonFollow() {
       try {
           AppFollowGroupQvo qvo = (AppFollowGroupQvo) getAppJson(AppFollowGroupQvo.class);
           if (qvo == null) {
               this.getAjson().setMsgCode("900");
               this.getAjson().setMsg("参数格式错误");
           } else {
               List<AppFollowRecordEntity> list = sysDao.getAppNewFollowPlanDao().findPersonFollowRecord(qvo);
               this.getAjson().setRows(list);
               this.getAjson().setQvo(qvo);
               this.getAjson().setMsgCode("800");
           }
       }catch (Exception e){
           e.printStackTrace();
           this.getAjson().setMsg(e.getMessage());
           this.getAjson().setMsgCode("900");
       }
        return "ajson";
    }

    /**
     *  查询随访数据
     * @return
     */
    public String findFollow(){
        try{
            AppPlanQvo qvo = (AppPlanQvo)getAppJson(AppPlanQvo.class);
            if(qvo == null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                Map<String,Object> map = new HashMap<String,Object>();
                if(StringUtils.isNotBlank(qvo.getId())){
                    String strId[] = qvo.getId().split(";");
                    String strType[] = qvo.getType().split(";");
                    for(int i= 0;i<strId.length;i++){
                        if(strType[i].equals(ResidentMangeType.GXY.getValue())){//查询高血压数据
                            AppHdBlooPressureTableEntity voHD = sysDao.getAppSaveFollowTableDao().findHd(strId[i]);
                            map.put("gxy",voHD);
                        }else if(strType[i].equals(ResidentMangeType.TNB.getValue())){//糖尿病
                            AppDiabetesTableEntity voDia = sysDao.getAppSaveFollowTableDao().findDiabetes(strId[i]);
                            map.put("tnb",voDia);
                        }else if(strType[i].equals(ResidentMangeType.ETLZLS.getValue())){//新生儿家庭访视记录表
                            AppNewChildrenTableEntity voChild = sysDao.getAppSaveFollowTableDao().findNewChild(strId[i]);
                            map.put("newChild",voChild);
                        }else if(strType[i].equals(ResidentMangeType.YCF.getValue())){//产后访视记录表
                            AppPostpartumVisitEntity voPost = sysDao.getAppSaveFollowTableDao().findPostPar(strId[i]);
                            map.put("postPar",voPost);
                        }else if(strType[i].equals(ResidentMangeType.TY.getValue())){//查询通用随访记录表
                            AppGeneralEntity voTy = sysDao.getAppSaveFollowTableDao().findTy(strId[i]);
                            map.put("general",voTy);
                        }else if(strType[i].equals(ResidentMangeType.YZJSZY.getValue())){//查询严重精神病随访记录表
                            AppMentalVisitEntity voJsb = sysDao.getAppSaveFollowTableDao().findMentalVisit(strId[i]);
                            map.put("mental",voJsb);
                        }else if(ResidentMangeType.JHB.getValue().equals(strType[i])){//查询结核病随访信息
                            AppFirstTBFollowVisitTable table = (AppFirstTBFollowVisitTable)sysDao.getServiceDo().find(AppFirstTBFollowVisitTable.class,strId[i]);
                            if(table != null){//查询结核病第一次入户随访信息
                                AppFirstTBFollowVisitEntity voFtb = sysDao.getAppSaveFollowTableDao().findFtbVisit(strId[i]);
                                map.put("ftb",voFtb);
                            }else{//查询结核病随访信息
                                AppTBFollowVisitEntity voTb = sysDao.getAppSaveFollowTableDao().findTbVisit(strId[i]);
                                map.put("tb",voTb);
                            }
                        }
                    }
                }
                this.getAjson().setMap(map);
                this.getAjson().setMsgCode("800");
            }

        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     *  随访失访或死亡
     *  @param followId;//随访主表id
     *  @param state;//失访或死亡
     *  @param reason;//失访或死亡原因
     *  @param date;//失访下次随访日期或死亡日期
     *  @param xAxis;
     *  @param yAxis;
     * @return
     */
    public String AppFollowState(){
        try{
            AppFollowStateQvo qvo = (AppFollowStateQvo)getAppJson(AppFollowStateQvo.class);
            if(qvo == null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                AppFollowPlan vo = sysDao.getAppNewFollowPlanDao().saveFollowPlanState(qvo);
                this.getAjson().setVo(vo);
                this.getAjson().setMsg("提交成功");
                this.getAjson().setMsgCode("800");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     *  随访的坐标等信息
     *  @param planDoctorId
     *  @param planToDay 随访日期
     *  @return
     */
    public String AppFollowLocation(){
        try{
            AppFollowPlanQvo qvo = (AppFollowPlanQvo)getAppJson(AppFollowPlanQvo.class);
            if(qvo == null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                List<AppFollowPlanXYEntity> list = sysDao.getAppNewFollowPlanDao().findDrFollowLocation(qvo.getPlanDoctorId(),qvo.getPlanToDay());
                this.getAjson().setRows(list);
                this.getAjson().setMsgCode("800");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     *  随访列表
     *  @param planDoctorId
     * @return
     */
    public String AppFollowLocationList(){
        try{
            AppFollowPlanQvo qvo = (AppFollowPlanQvo)getAppJson(AppFollowPlanQvo.class);
            if(qvo == null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                List<AppFollowListEntity> list = sysDao.getAppNewFollowPlanDao().findDrFollowLocationList(qvo);
                this.getAjson().setQvo(qvo);
                this.getAjson().setRows(list);
                this.getAjson().setMsgCode("800");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }


    /**
     * 随访统计
     * @return
     */
    public String appFollowLocatonCount(){
        try{
            AppFollowPlanQvo qvo = (AppFollowPlanQvo)getAppJson(AppFollowPlanQvo.class);
            if(qvo == null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                Map<String, Object> map = sysDao.getAppNewFollowPlanDao().findDrFollowLocationCount(qvo);
                this.getAjson().setMap(map);
                this.getAjson().setMsgCode("800");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 新生儿家庭访视记录表
     * @return
     */
    public String saveChild(){
        try{
            AppSaveFollowQvo qvo = (AppSaveFollowQvo)getAppJson(AppSaveFollowQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                if(StringUtils.isNotBlank(qvo.getSaveNewChild().getId())){
                    AppChild vv = (AppChild)sysDao.getServiceDo().find(AppChild.class,qvo.getSaveNewChild().getId());
                    if(vv!=null){
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("此次随访已保存，不可重复保存");
                        return "ajson";
                    }
                }
                AppDrUser user = this.getAppDrUser();
                if(user!=null){
                    qvo.getSaveNewChild().setChildVisitDoctorId(user.getId());
                }
                AppSaveFollowQvo vo = sysDao.getAppSaveFollowTableDao().saveNewChild(qvo);
                if(vo!=null){
                    this.getAjson().setVo(vo.getSaveNewChild());
                    this.getAjson().setMsgCode("800");
                }else{
                    this.getAjson().setMsgCode("900");
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
     * 保存产后访视记录表
     * @return
     */
    public String savePostPartum(){
        try{
            AppSaveFollowQvo qvo = (AppSaveFollowQvo)getAppJson(AppSaveFollowQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                if(StringUtils.isNotBlank(qvo.getPostPar().getId())){
                    AppPostpartumVisit vv = (AppPostpartumVisit)sysDao.getServiceDo().find(AppPostpartumVisit.class,qvo.getPostPar().getId());
                    if(vv!=null){
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("此次随访已保存，不可重复保存");
                        return "ajson";
                    }
                }
                AppDrUser user = this.getAppDrUser();
                if(user!=null){
                    qvo.getPostPar().setPostDoctorId(user.getId());
                }
                AppSaveFollowQvo vo = sysDao.getAppSaveFollowTableDao().savePostPar(qvo);
                this.getAjson().setVo(vo.getPostPar());
                this.getAjson().setMsgCode("800");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 删除随访计划
     * @return
     */
    public String delFollowPlan(){
        try{
            AppPlanQvo qvo = (AppPlanQvo)getAppJson(AppPlanQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                AppFollowPlan plan = (AppFollowPlan) sysDao.getServiceDo().find(AppFollowPlan.class,qvo.getId());
                if(plan!=null){
                    List<AppFollowPlan> list = sysDao.getServiceDo().loadByPk(AppFollowPlan.class,"sfFollowNum",plan.getSfFollowNum());
                    if(list !=null && list.size()>0){
                        for(AppFollowPlan ll:list){
                            if(FollowPlanState.WEIWANCHENG.getValue().equals(ll.getSfFollowState())){
                                sysDao.getServiceDo().delete(ll);
                            }
                        }
                    }
                    if(FollowPlanState.WEIWANCHENG.getValue().equals(plan.getSfFollowState())){
                        this.getAjson().setMsg("删除成功");
                        this.getAjson().setMsgCode("800");
                    }else{
                        this.getAjson().setMsg("已随访不可删除");
                        this.getAjson().setMsgCode("900");
                    }
                }else{
                    this.getAjson().setMsg("无此随访计划");
                    this.getAjson().setMsgCode("900");
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
     * 保存通用随访数据
     * @return
     */
    public String saveGeneral(){
        try {
            AppGeneralQvo qvo = (AppGeneralQvo)this.getAppJson(AppGeneralQvo.class);
            if(qvo == null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                if(StringUtils.isNotBlank(qvo.getId())){
                    AppGeneralTable table = (AppGeneralTable)sysDao.getServiceDo().find(AppGeneralTable.class,qvo.getId());
                    if(table!=null){
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("此次随访已保存，不可重复保存");
                        return "ajson";
                    }
                }
                AppDrUser drUser = this.getAppDrUser();
                if(drUser!=null){
                    qvo.setGenDrId(drUser.getId());
                }
                AppGeneralQvo vo = sysDao.getAppSaveFollowTableDao().saveGeneral(qvo);
                if(vo!=null){
                    this.getAjson().setVo(vo);
                    this.getAjson().setMsgCode("800");
                }else{
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("查无计划");
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
     * 基卫保存糖尿病数据
     * @return
     */
    public String addBasicDiabetes(){
        try{
            AppBasicDiseaseQvo qvo = (AppBasicDiseaseQvo)this.getAppJson(AppBasicDiseaseQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                String str = sysDao.getAppFollowPlanDao().addBasicDiabetes(qvo);
                if("true".equals(str)){
                    this.getAjson().setMsgCode("800");
                }else{
                    this.getAjson().setMsg(str);
                    this.getAjson().setMsgCode("900");
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
     * 保存基卫高血压随访接口
     * @return
     */
    public String addBasicHdBloo(){
        try{
            AppBasicHdBlooQvo qvo = (AppBasicHdBlooQvo)this.getAppJson(AppBasicHdBlooQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                String str = sysDao.getAppFollowPlanDao().addBasicHdBloo(qvo);
                if("true".equals(str)){
                    this.getAjson().setMsgCode("800");
                }else{
                    this.getAjson().setMsg(str);
                    this.getAjson().setMsgCode("900");
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
     * 保存精神病随访
     * @return
     */
    public String saveMentalVisit(){
        try{
            AppSaveFollowQvo qvo = (AppSaveFollowQvo)getAppJson(AppSaveFollowQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                if(StringUtils.isNotBlank(qvo.getSaveMentalVisit().getId())){
                    AppMentalVisitTable vv = (AppMentalVisitTable)sysDao.getServiceDo().find(AppMentalVisitTable.class,qvo.getSaveMentalVisit().getId());
                    if(vv!=null){
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("此次随访已保存，不可重复保存");
                        return "ajson";
                    }
                }
                AppDrUser user = this.getAppDrUser();
                if(user!=null){
                    qvo.getSaveMentalVisit().setVisitDoctorId(user.getId());
                }
                AppSaveFollowQvo vo = sysDao.getAppSaveFollowTableDao().saveMentalVisit(qvo);
                this.getAjson().setVo(vo.getSaveMentalVisit());
                this.getAjson().setRows(vo.getUserMedicine());
                this.getAjson().setMsgCode("800");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 保存肺结核第一次入户随访记录
     * @return
     */
    public String saveFTBFollowVisit(){
        try{
            AppSaveFollowQvo qvo = (AppSaveFollowQvo)getAppJson(AppSaveFollowQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                if(StringUtils.isNotBlank(qvo.getSaveFTB().getId())){
                    AppFirstTBFollowVisitTable vv = (AppFirstTBFollowVisitTable)sysDao.getServiceDo().find(AppFirstTBFollowVisitTable.class,qvo.getSaveFTB().getId());
                    if(vv!=null){
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("此次随访已保存，不可重复保存");
                        return "ajson";
                    }
                }
                AppDrUser user = this.getAppDrUser();
                if(user!=null){
                    qvo.getSaveFTB().setFtbDoctorId(user.getId());
                }
                AppSaveFollowQvo vo = sysDao.getAppSaveFollowTableDao().saveFTBVisit(qvo);
                this.getAjson().setVo(vo.getSaveFTB());
                this.getAjson().setMsgCode("800");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 保存结核病随访信息
     * @return
     */
    public String saveTbFollowVisit(){
        try {
            AppSaveFollowQvo qvo = (AppSaveFollowQvo)getAppJson(AppSaveFollowQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                if(StringUtils.isNotBlank(qvo.getSaveTB().getId())){
                    AppTBFollowVisitTable vv = (AppTBFollowVisitTable)sysDao.getServiceDo().find(AppTBFollowVisitTable.class,qvo.getSaveTB().getId());
                    if(vv!=null){
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("此次随访已保存，不可重复保存");
                        return "ajson";
                    }
                }
                AppDrUser user = this.getAppDrUser();
                if(user!=null){
                    qvo.getSaveTB().setVisitDoctorId(user.getId());
                }
                AppSaveFollowQvo vo = sysDao.getAppSaveFollowTableDao().saveTBVisit(qvo);
                this.getAjson().setVo(vo.getSaveTB());
                this.getAjson().setMsgCode("800");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }
}
