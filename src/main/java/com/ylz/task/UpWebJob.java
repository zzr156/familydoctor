package com.ylz.task;

import com.google.gson.reflect.TypeToken;
import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.bizDo.app.po.AppSignForm;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.jtapp.basicHealthEntity.EnterpatientEntity;
import com.ylz.bizDo.plan.Entity.AppJwMedica;
import com.ylz.bizDo.plan.po.AppDiabetesTable;
import com.ylz.bizDo.plan.po.AppFollowPlan;
import com.ylz.bizDo.plan.po.AppHdBlooPressureTable;
import com.ylz.bizDo.plan.po.AppMedicationTable;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.comEnum.CommSF;
import com.ylz.packcommon.common.comEnum.OpenTheInterfaceState;
import com.ylz.packcommon.common.comEnum.ResidentMangeType;
import com.ylz.packcommon.common.comEnum.SignFormType;
import com.ylz.packcommon.common.exception.ActionException;
import com.ylz.packcommon.common.util.*;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Created by hzk on 2017/7/29.
 * 家签调度
 */
public class UpWebJob {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(UpWebJob.class);
    @Autowired
    public SysDao sysDao = (SysDao) SpringHelper.getBean("sysDao");


    /**
     * 创建环信账号
     */
    public void createEaseTask(){
        try {
            SysDao sysDao = (SysDao) SpringHelper.getBean("sysDao");
            sysDao.getAppSignControlDao().createEase();
        } catch (Exception e) {
            new ActionException(this.getClass(), e.getMessage());
        }
    }

    /**
     * 家签上传web调度
     */
    public void upWebTask() {
        try {
            SysDao sysDao = (SysDao) SpringHelper.getBean("sysDao");
            Map<String, Object> map = new HashedMap();
            String[] signStates = new String[]{SignFormType.YUQY.getValue(),SignFormType.YQY.getValue()};
            map.put("signState",signStates);
            String url = PropertiesUtil.getConfValue("weburl");
            String sql = "SELECT * from APP_SIGN_FORM a where a.SIGN_STATE IN (:signState) and a.UP_HPIS is null";//查询未上传数据
            List<AppSignForm> ls = sysDao.getServiceDo().findSqlMap(sql, map, AppSignForm.class);
            if (ls != null && !ls.isEmpty()) {
                for (AppSignForm l : ls) {
                    try {
                        String rt = sysDao.getWebSignFormDao().signUpWeb(l, url);
                        Map m = (Map) JacksonUtils.getObject(rt, Map.class);
                        if (m.get("errCode").toString().equals("900")) {
                            logger.error("单号" + l.getId() + "上传失败:" + rt);
                        } else if (m.get("errCode").toString().equals("800")) {
                            l.setUpHpis("1");//上传web成功
                            sysDao.getServiceDo().modify(l);
                        }
                    } catch (Exception e) {

                    }
                }
            }
        } catch (Exception e) {
            new ActionException(this.getClass(), e.getMessage());
        }
    }

    /**
     * 随访高血压上传到基卫
     */
    public void savePressure() {
        CloseableHttpClient client = HttpClients.createDefault();
        try {
            System.out.println("开始高血压随访上传基卫调度------------------------------");
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            String patientId = "";
            List<AppFollowPlan> list = sysDao.getAppNewFollowPlanDao().findDoneByType(ResidentMangeType.GXY.getValue());
            if (list != null && list.size() > 0) {
                for (AppFollowPlan plan : list) {
                    if (!CommSF.YES.getValue().equals(plan.getSfIsOrNot()) && plan.getSfFollowDate()!=null) {
                        JSONObject jsonParam = new JSONObject();
                        JSONObject json1 = new JSONObject();
                        String code = AreaUtils.getAreaCode(plan.getSfHosAreaCode(), "2");
                        CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE, code);
                        if (value != null) {
                            jsonParam.put("urlType", value.getCodeTitle());
                            json1.put("urlType", value.getCodeTitle());
//                            json1.put("urlType", "0591");
                        }
                        AppPatientUser patient = (AppPatientUser) sysDao.getServiceDo().find(AppPatientUser.class, plan.getSfFollowPatientid());
                        if(patient!=null){
                            patientId = patient.getPatientIdno();
                            json1.put("idcardno", patient.getPatientIdno());
                        }
                        json1.put("currentPage", "1");
                        String url1 = PropertiesUtil.getConfValue("idCardHealthUrl") + "/appCommon.action?act=getEnterpatientList";
                        String state = PropertiesUtil.getConfValue("openTheInterface");
                        String str1 = null;
                        if(OpenTheInterfaceState.NOT.getValue().equals(state)){
                            str1 = HtmlUtils.getInstance().executeResponseJson(client, "post", url1, json1, "utf-8");
                        }else{
                            str1 = sysDao.getSecurityCardAsyncBean().getDateBasic("","",json1.toString(),plan.getSfFollowPatientid(),"getEnterpatientList");
                        }
                        sysDao.getSecurityCardAsyncBean().getBasicLog(json1.toString(),"","",str1, "","getEnterpatientList");
                        if(StringUtils.isNotBlank(str1)){
                            JSONObject jsonall = JSONObject.fromObject(str1);
                            if ("null".equals(jsonall.get("rows").toString())) {
                                continue;
                            }
                            List<EnterpatientEntity> ls = JsonUtil.fromJson(jsonall.get("rows").toString(), new TypeToken<List<EnterpatientEntity>>() {
                            }.getType());
                            if(ls!=null&&ls.size()>0) {
                                EnterpatientEntity enterpatientEntity = ls.get(0);
                                if("0".equals(enterpatientEntity.getIsgxy())){
                                    JSONObject param = new JSONObject();
                                    param.put("urlType", value.getCodeTitle());
                                    param.put("ref_ci_id",enterpatientEntity.getRef_ci_id());
                                    param.put("ref_cjid",enterpatientEntity.getRef_cjid());
                                    param.put("name",enterpatientEntity.getName());
                                    param.put("sex",enterpatientEntity.getSex());
                                    param.put("birthday",enterpatientEntity.getBirthday());
                                    param.put("idcardno",enterpatientEntity.getIdcardno());
                                    param.put("jwbsbh","1;");//既往病史，1高血压
                                    param.put("jwbsjb","高血压 时间："+ExtendDate.getYMD(Calendar.getInstance())+",");//既往病史，1高血压
                                    param.put("jdrq00",ExtendDate.getYYYYMMD(Calendar.getInstance()));//既往病史，1高血压
                                    String url = PropertiesUtil.getConfValue("idCardHealthUrl") + "/appCommon.action?act=uploadHealthCareRecord";
                                    String str = null;
                                    if(OpenTheInterfaceState.NOT.getValue().equals(state)){
                                        str = HtmlUtils.getInstance().executeResponseJson(client, "post", url, param, "utf-8");
                                    }else{
                                        str = sysDao.getSecurityCardAsyncBean().getDateBasic("","",param.toString(),enterpatientEntity.getIdcardno(),"uploadHealthCareRecord");
                                    }
                                    sysDao.getSecurityCardAsyncBean().getBasicLog(param.toString(),"","",str, "","uploadHealthCareRecord");
                                }
                                jsonParam.put("df_id", ls.get(0).getDf_id());
                            }
                            jsonParam.put("mxjbbz", "1");//慢性疾病编号（1-高血压3-糖尿病）
                            jsonParam.put("sfrq00", ExtendDate.getYYYYMMD(plan.getSfFollowDate()));
                            jsonParam.put("sfysqm", plan.getSfFollowDoctorid());
                            jsonParam.put("sffs00", plan.getSfFollowMode());
                            jsonParam.put("xcsfrq", ExtendDate.getYYYYMMD(plan.getNextTime()));//下次随访日期
                            jsonParam.put("mark", "1");//慢性疾病标志(1-高血压患者用药情况，2-糖尿病患者用药情况)
                            List<AppHdBlooPressureTable> list1 = sysDao.getServiceDo().loadByPk(AppHdBlooPressureTable.class, "visitId", plan.getId());

                            if (list1 != null && list1.size() > 0) {
                                AppHdBlooPressureTable table = list1.get(0);
                                jsonParam.put("ssy", table.getBloodPressureOne());//收缩压
                                jsonParam.put("szy", table.getBloodPressureTwo());//舒张压
                                jsonParam.put("tzone", table.getWeight());//体重
                                jsonParam.put("tztwo", table.getNextWeight());//体重（计划kg）
                                jsonParam.put("sg0000", table.getHeight());//身高(cm)
                                jsonParam.put("tzzs00", table.getBmi());//体质指数
                                jsonParam.put("xlone", table.getHeartRate());//心率（高血压：目前）
                                //jsonParam.put("xltwo",table.getHeartRate());//心率（高血压：计划）
                                jsonParam.put("qttz00", table.getSignsOther());//其他体征
                                jsonParam.put("rxylone", table.getSmokingToDay());//日吸烟量（目前 支）
                                jsonParam.put("rxyltwo", table.getSmokingNextToDay());//日吸烟量（计划 支）
                                jsonParam.put("ryjlone", table.getDrinkingToDay());//日饮酒量（目前 两）
                                jsonParam.put("ryjltwo", table.getDrinkingNextToDay());//日饮酒量（计划 两）
                                jsonParam.put("ydzcone", table.getActivityToWeek());//运动周次（目前 次/周）
                                jsonParam.put("ydzctwo", table.getActivityNextToWeek());//运动周次（计划 次/周）
                                jsonParam.put("ydrcone", table.getActivityToTime());//运动分次（目前 分钟/次）
                                jsonParam.put("ydrctwo", table.getActivityNextToTime());//运动分次（计划 分钟/次）
                                CdCode salt = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_SYQK, table.getSaltSituation());
                                if (salt != null) {
                                    jsonParam.put("syqkone", salt.getCodeTitle());//摄盐情况（高血压目前：轻-轻，中-中，重-重）
                                }
                                CdCode nextSalt = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_SYQK, table.getSaltNextSituation());
                                if (nextSalt != null) {
                                    jsonParam.put("syqktwo", nextSalt.getCodeTitle());//摄盐情况（高血压计划：轻-轻，中-中，重-重）
                                }
                                jsonParam.put("xltzqk", table.getMentalityAdjust());//心理调整（1--良好，2--一般，3--差）
                                jsonParam.put("zyxwqk", table.getFollowingBehavior());//遵医情况（1--良好，2---一般，3--差）
                                jsonParam.put("qtfzjc", table.getFzCheck());//其他辅助检查
                                jsonParam.put("fyycx0", table.getMedicationAdherence());//服药依从性（1--规律，2--间断，3--不服药）
                                jsonParam.put("ywblfy", table.getDrugReaction());//药物不良反应（0--无，1--有）
                                jsonParam.put("blfyms", table.getDrugReactionContent());//不良反应描述
                                jsonParam.put("ccsffl", table.getVisitThisType());//此次随访分类（1--控制满意，2--控制不满意，3--不良反应，4--并发症）
                                jsonParam.put("zzqk00", table.getReferral());//转诊情况（0--无，1--有）
                                if (StringUtils.isNotBlank(table.getReferralReason())) {
                                    jsonParam.put("zzyy00", table.getReferralReason());//转诊原因
                                }
                                if (StringUtils.isNotBlank(table.getReferralDept())) {
                                    jsonParam.put("zzjgks", table.getReferralDept());//转诊机构科室
                                }
                                //jsonParam.put("zzbz00","");//转诊备注
                                AppDrUser drUser = (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class,plan.getSfFollowDoctorid());
                                if(drUser != null){
                                    jsonParam.put("ywysxm",drUser.getDrName());//院外医生姓名
                                    String[] drId = drUser.getId().split("_");
                                    if(drId.length > 1){
                                        jsonParam.put("sfyszj",drId[1]);//随访医生员工主键
                                    }else{
                                        jsonParam.put("sfyszj",drId[0]);//随访医生员工主键
                                    }

                                }

                                List<Map> list3 = new ArrayList<>();
                                List<AppMedicationTable> list2 = sysDao.getServiceDo().loadByPk(AppMedicationTable.class, "visitId", table.getId());
                                for (AppMedicationTable medicationTable : list2) {
                                    HashMap<String, Object> map = new HashMap<>();
                                    map.put("ywmc", medicationTable.getMedicineName());
                                    map.put("ywyf", medicationTable.getUserToDay());
                                    map.put("ywyl", medicationTable.getUserToTime());
                                    list3.add(map);
                                }
                                jsonParam.put("yyqkList", list3);
                                String[] symptoms = table.getSymptoms().split(";");
                                for (String zz : symptoms) {
                                    CdCode symptom = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_XYSYMPTOM, zz);
                                    if (symptom != null) {
                                        switch (symptom.getCodeValue()) {
                                            case "0":
                                                jsonParam.put("wuzz00", "1");//无症状
                                                break;
                                            case "1":
                                                jsonParam.put("ttty", "1");//头痛头晕
                                                break;
                                            case "2":
                                                jsonParam.put("exot", "1");//恶心呕吐
                                                break;
                                            case "3":
                                                jsonParam.put("yhem", "1");//眼花耳鸣
                                                break;
                                            case "4":
                                                jsonParam.put("hxkn", "1");//呼吸困难

                                                break;
                                            case "5":
                                                jsonParam.put("xjxm", "1");//心悸胸闷
                                                break;
                                            case "6":
                                                jsonParam.put("bccxbz", "1");//鼻衄出血不知
                                                break;
                                            case "7":
                                                jsonParam.put("szfm", "1");//四肢发麻
                                                break;
                                            case "8":
                                                jsonParam.put("xzsz", "1");//下肢水肿
                                                break;
                                            case "9":
                                                jsonParam.put("tqzz", table.getSymptomsOther());//其他
                                                break;
                                            default:
                                                break;
                                        }
                                    }
                                }


                            }

                            String url = PropertiesUtil.getConfValue("idCardHealthUrl") + "/appCommon.action?act=saveOrUpdateMxjbsf";
                            String result = null;
                            if(OpenTheInterfaceState.NOT.getValue().equals(state)){
                                result =  HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
                            }else{
                                result = sysDao.getSecurityCardAsyncBean().getDateBasic("","",jsonParam.toString(),patientId,"saveOrUpdateMxjbsf");
                            }
                            System.out.println(jsonParam.toString());
                            sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(),"","",result, "","saveOrUpdateMxjbsf");
                            if(StringUtils.isNotBlank(result)){
                                System.out.println("高血压："+plan.getId()+"："+result);
                                JSONObject jsonAll = JSONObject.fromObject(result);
                                if("800".equals(jsonAll.get("msgCode"))){
                                    plan.setSfIsOrNot(CommSF.YES.getValue());
                                    sysDao.getServiceDo().modify(plan);
                                }else{
                                    plan.setNotSuccessReason(jsonAll.get("msg").toString());
                                    sysDao.getServiceDo().modify(plan);
                                }
                            }
                        }
                    }
                }
            }
            System.out.println("结束高血压随访上传基卫调度-----------------------");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 随访糖尿病上传到基卫
     */
    public void saveTnbPressure() {
        CloseableHttpClient client = HttpClients.createDefault();
        try {
            System.out.println("开始糖尿病随访上传基卫调度------------------------------");
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            String patientId = "";
            List<AppFollowPlan> list = sysDao.getAppNewFollowPlanDao().findDoneByType(ResidentMangeType.TNB.getValue());
            if (list != null && list.size() > 0) {
                for (AppFollowPlan plan : list) {
                    if (!CommSF.YES.getValue().equals(plan.getSfIsOrNot()) && plan.getSfFollowDate()!=null) {
                        JSONObject jsonParam = new JSONObject();
                        JSONObject json1 = new JSONObject();
                        String code = AreaUtils.getAreaCode(plan.getSfHosAreaCode(),"2");
                        CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE,code);
                        if(value != null) {
                            jsonParam.put("urlType", value.getCodeTitle());
//                            jsonParam.put("urlType", "7");
                            json1.put("urlType", value.getCodeTitle());
//                            json1.put("urlType", "7");
                        }
                        AppPatientUser patient = (AppPatientUser) sysDao.getServiceDo().find(AppPatientUser.class,plan.getSfFollowPatientid());
                        if(patient!=null){
                            patientId = patient.getId();
                            json1.put("idcardno", patient.getPatientIdno());
                        }
                        json1.put("currentPage", "1");
                        String url1 = PropertiesUtil.getConfValue("idCardHealthUrl") + "/appCommon.action?act=getEnterpatientList";
                        String state = PropertiesUtil.getConfValue("openTheInterface");
                        String str1 = null;
                        if(OpenTheInterfaceState.NOT.getValue().equals(state)){
                            str1 = HtmlUtils.getInstance().executeResponseJson(client, "post", url1, json1, "utf-8");
                        }else{
                            str1 = sysDao.getSecurityCardAsyncBean().getDateBasic("","",json1.toString(),patientId,"getEnterpatientList");
                        }
                        sysDao.getSecurityCardAsyncBean().getBasicLog(json1.toString(),"","",str1, "","getEnterpatientList");
                        if(StringUtils.isNotBlank(str1)){
                            JSONObject jsonall = JSONObject.fromObject(str1);
                            if(jsonall.get("rows")==null){
                                continue;
                            }
                            List<EnterpatientEntity> ls = JsonUtil.fromJson(jsonall.get("rows").toString(),new TypeToken<List<EnterpatientEntity>>() {}.getType());
                            if(ls!=null&&ls.size()>0) {
                                EnterpatientEntity enterpatientEntity = ls.get(0);
                                if("0".equals(enterpatientEntity.getIstnb())){
                                    JSONObject param = new JSONObject();
                                    //param.put("urlType", value.getCodeTitle());
                                    param.put("urlType", "7");
                                    param.put("ref_ci_id",enterpatientEntity.getRef_ci_id());
                                    param.put("ref_cjid",enterpatientEntity.getRef_cjid());
                                    param.put("name",enterpatientEntity.getName());
                                    param.put("sex",enterpatientEntity.getSex());
                                    param.put("birthday",enterpatientEntity.getBirthday());
                                    param.put("idcardno",enterpatientEntity.getIdcardno());
                                    param.put("jwbsbh","3;");//既往病史，1高血压 3糖尿病
                                    param.put("jwbsjb","糖尿病 时间："+ExtendDate.getYMD(Calendar.getInstance())+",");
                                    param.put("jdrq00",ExtendDate.getYYYYMMD(Calendar.getInstance()));
                                    String url = PropertiesUtil.getConfValue("idCardHealthUrl") + "/appCommon.action?act=uploadHealthCareRecord";
                                    String str = null;
                                    if(OpenTheInterfaceState.NOT.getValue().equals(state)){
                                        str = HtmlUtils.getInstance().executeResponseJson(client, "post", url, param, "utf-8");
                                    }else{
                                        str = sysDao.getSecurityCardAsyncBean().getDateBasic("","",param.toString(),patientId,"uploadHealthCareRecord");
                                    }
                                    sysDao.getSecurityCardAsyncBean().getBasicLog(param.toString(),"","",str, "","uploadHealthCareRecord");
                                }
                                jsonParam.put("df_id", ls.get(0).getDf_id());
                            }

                            jsonParam.put("mxjbbz","3");//慢性疾病编号（1-高血压3-糖尿病）
                            jsonParam.put("sfrq00",ExtendDate.getYYYYMMD(plan.getSfFollowDate()));

                            jsonParam.put("sfysqm",plan.getSfFollowDoctorid());
                            jsonParam.put("sffs00",plan.getSfFollowMode());
                            jsonParam.put("xcsfrq",ExtendDate.getYYYYMMD(plan.getNextTime()));//下次随访日期
                            jsonParam.put("mark","2");//慢性疾病标志(1-高血压患者用药情况，2-糖尿病患者用药情况)
                            List<AppDiabetesTable> list1 = sysDao.getServiceDo().loadByPk(AppDiabetesTable.class,"visitId",plan.getId());
                            if(list1!=null&& list1.size()>0){
                                AppDiabetesTable table = list1.get(0);
                                if(StringUtils.isNotBlank(table.getSymptoms())){
                                    String str = table.getSymptoms();
                                    if(str.indexOf("0")!=-1){
                                        jsonParam.put("wuzz00","1");
                                    }else{
                                        jsonParam.put("wuzz00","0");
                                    }
                                    if(str.indexOf("1")!=-1){
                                        jsonParam.put("dy","1");
                                    }else{
                                        jsonParam.put("dy","0");
                                    }
                                    if(str.indexOf("2")!=-1){
                                        jsonParam.put("ds","1");
                                    }else{
                                        jsonParam.put("ds","0");
                                    }
                                    if(str.indexOf("3")!=-1){
                                        jsonParam.put("dn","1");
                                    }else{
                                        jsonParam.put("dn","0");
                                    }
                                    if(str.indexOf("4")!=-1){
                                        jsonParam.put("slmh","1");
                                    }else{
                                        jsonParam.put("slmh","0");
                                    }
                                    if(str.indexOf("5")!=-1){
                                        jsonParam.put("gl","1");
                                    }else{
                                        jsonParam.put("gl","0");
                                    }
                                    if(str.indexOf("6")!=-1){
                                        jsonParam.put("szfm","1");
                                    }else{
                                        jsonParam.put("szfm","0");
                                    }
                                    if(str.indexOf("7")!=-1){
                                        jsonParam.put("xzfz","1");
                                    }else{
                                        jsonParam.put("xzfz","0");
                                    }
                                    if(str.indexOf("9")!=-1){
                                        jsonParam.put("tqzz",table.getSymptomsOther());
                                    }
                                }

                                jsonParam.put("ssy",table.getBloodPressureOne());//收缩压
                                jsonParam.put("szy",table.getBloodPressureTwo());//舒张压

                                jsonParam.put("tzone",table.getWeight());//体重
                                jsonParam.put("tztwo",table.getNextWeight());//体重（计划kg）

                                jsonParam.put("sg0000",table.getHeight());//身高(cm)
                                jsonParam.put("tzzs00",table.getBmi());//体质指数
                                if("0".equals(table.getDorsalisPedisPulse())){
                                    jsonParam.put("zbdmpd","1");//足背动脉搏动
                                }else{
                                    jsonParam.put("zbdmpd","2");
                                }
                                jsonParam.put("qttz00",table.getSignsOther());//其他体征

                                jsonParam.put("rxylone",table.getSmokingToDay());//日吸烟量（目前 支）
                                jsonParam.put("rxyltwo",table.getSmokingNextToDay());//日吸烟量（计划 支）
                                jsonParam.put("ryjlone",table.getDrinkingToDay());//日饮酒量（目前 两）
                                jsonParam.put("ryjltwo",table.getDrinkingNextToDay());//日饮酒量（计划 两）

                                jsonParam.put("ydzcone",table.getActivityToWeek());//运动周次（目前 次/周）
                                jsonParam.put("ydzctwo",table.getActivityNextToWeek());//运动周次（计划 次/周）
                                jsonParam.put("ydrcone",table.getActivityToTime());//运动分次（目前 分钟/次）
                                jsonParam.put("ydrctwo",table.getActivityNextToTime());//运动分次（计划 分钟/次）
//
                                jsonParam.put("zsqkone",table.getFood());//主食情况
                                jsonParam.put("zsqktwo",table.getNextFood());//主食情况（计划 克/每天）
                                jsonParam.put("xltzqk",table.getMentalityAdjust());//心理调整（1--良好，2--一般，3--差）
                                jsonParam.put("zyxwqk",table.getFollowingBehavior());//遵医情况（1--良好，2---一般，3--差）

                                jsonParam.put("kfxtz0",table.getFastingBloodSugar());//空腹血糖值
                                jsonParam.put("chxtz0","");//餐后2小时血糖值
                                jsonParam.put("thxhdb",table.getThHemoglobin());//糖化血红蛋白
                                jsonParam.put("jcrq00",ExtendDate.getYYYYMMD(ExtendDate.getCalendar(table.getFzCheckDate())));//检查日期
                                jsonParam.put("qtfzjc",table.getOtherCheck());//其他辅助检查
                                jsonParam.put("fyycx0",table.getMedicationAdherence());//服药依从性（1--规律，2--间断，3--不服药）

                                jsonParam.put("ywblfy",table.getDrugReaction());//药物不良反应（0--无，1--有）
                                jsonParam.put("blfyms","");//不良反应描述

                                jsonParam.put("dxtfy",table.getLowBloodGlucose());//低血糖反应
                                jsonParam.put("ccsffl",table.getVisitThisType());//此次随访分类
                                List<AppMedicationTable> lists = sysDao.getServiceDo().loadByPk(AppMedicationTable.class,"visitId",table.getId());
                                if(lists!=null&&lists.size()>0){
                                    List lsss = new ArrayList();
                                    for(AppMedicationTable lss:lists){
                                        AppJwMedica vv = new AppJwMedica();
                                        vv.setYwmc(lss.getMedicineName());
                                        vv.setYwyf(lss.getUserToDay());
                                        vv.setYwyl(lss.getUserToTime());
                                        lsss.add(vv);
                                    }
                                    jsonParam.put("yyqkList",lsss);//用药情况列表
                                }
                                jsonParam.put("yds000",table.getInsulin());//胰岛素种类
                                jsonParam.put("ydsyf0",table.getUserInsulin());//胰岛素用法和用量
                                jsonParam.put("zzqk00",table.getReferral());//转诊情况
                                jsonParam.put("zzyy00",table.getReferralReason());//转诊原因
                                jsonParam.put("zzjgks",table.getReferralOrg()+":"+table.getReferralDept());//转诊机构科室
                                jsonParam.put("zzbz00","");//转诊备注
                                AppDrUser drUser = (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class,plan.getSfFollowDoctorid());
                                if(drUser != null){
                                    jsonParam.put("ywysxm",drUser.getDrName());//院外医生姓名
                                    String[] drId = drUser.getId().split("_");
                                    if(drId.length > 1){
                                        jsonParam.put("sfyszj",drId[1]);//随访医生员工主键
                                    }else{
                                        jsonParam.put("sfyszj",drId[0]);//随访医生员工主键
                                    }

                                }


                            }
                            String url = PropertiesUtil.getConfValue("idCardHealthUrl") + "/appCommon.action?act=saveOrUpdateMxjbsf";
                            String result = null;
                            if(OpenTheInterfaceState.NOT.getValue().equals(state)){
                                result = HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
                            }else{
                                result = sysDao.getSecurityCardAsyncBean().getDateBasic("","",jsonParam.toString(),patientId,"saveOrUpdateMxjbsf");
                            }
                            sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(),"","",result, "","saveOrUpdateMxjbsf");
                            if(StringUtils.isNotBlank(result)){
                                System.out.println("糖尿病:"+plan.getId()+"："+result);
                                JSONObject jsonAll = JSONObject.fromObject(result);
                                if("800".equals(jsonAll.get("msgCode"))){
                                    plan.setSfIsOrNot(CommSF.YES.getValue());
                                    sysDao.getServiceDo().modify(plan);
                                }else{
                                    plan.setNotSuccessReason(jsonAll.get("msg").toString());
                                    sysDao.getServiceDo().modify(plan);
                                }
                            }

                        }
                    }
                }
            }
            System.out.println("结束糖尿病随访上传基卫调度------------------------------");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                client.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 上传严重精神病随访到基卫
     */
    public void saveMentalVisit() {
        CloseableHttpClient client = HttpClients.createDefault();
        try {
            System.out.println("开始严重精神病随访上传基卫调度------------------------------");
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            String patientId = "";
            List<AppFollowPlan> list = sysDao.getAppNewFollowPlanDao().findDoneByType(ResidentMangeType.YZJSZY.getValue());
            if (list != null && list.size() > 0) {
                for (AppFollowPlan plan : list) {
                    if (!CommSF.YES.getValue().equals(plan.getSfIsOrNot()) && plan.getSfFollowDate()!=null) {
                        JSONObject jsonParam = new JSONObject();
                        JSONObject json1 = new JSONObject();
                        String code = AreaUtils.getAreaCode(plan.getSfHosAreaCode(), "2");
                        CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE, code);
                        if (value != null) {
                            jsonParam.put("urlType", value.getCodeTitle());
                            json1.put("urlType", value.getCodeTitle());
                        }
                        AppPatientUser patient = (AppPatientUser) sysDao.getServiceDo().find(AppPatientUser.class, plan.getSfFollowPatientid());
                        if(patient!=null){
                            patientId = patient.getPatientIdno();
                            json1.put("idcardno", patient.getPatientIdno());
                        }
                        json1.put("currentPage", "1");
                        String url1 = PropertiesUtil.getConfValue("idCardHealthUrl") + "/appCommon.action?act=getEnterpatientList";
                        String state = PropertiesUtil.getConfValue("openTheInterface");
                        String str1 = null;
                        if(OpenTheInterfaceState.NOT.getValue().equals(state)){
                            str1 = HtmlUtils.getInstance().executeResponseJson(client, "post", url1, json1, "utf-8");
                        }else{
                            str1 = sysDao.getSecurityCardAsyncBean().getDateBasic("","",json1.toString(),plan.getSfFollowPatientid(),"getEnterpatientList");
                        }
                        sysDao.getSecurityCardAsyncBean().getBasicLog(json1.toString(),"","",str1, "","getEnterpatientList");
                        if(StringUtils.isNotBlank(str1)){
                            JSONObject jsonall = JSONObject.fromObject(str1);
                            if ("null".equals(jsonall.get("rows").toString())) {
                                continue;
                            }
                            List<EnterpatientEntity> ls = JsonUtil.fromJson(jsonall.get("rows").toString(), new TypeToken<List<EnterpatientEntity>>() {
                            }.getType());
                            if(ls!=null&&ls.size()>0) {
                                EnterpatientEntity enterpatientEntity = ls.get(0);
                                if("0".equals(enterpatientEntity.getIsgxy())){
                                    JSONObject param = new JSONObject();
                                    param.put("urlType", value.getCodeTitle());
                                    param.put("ref_ci_id",enterpatientEntity.getRef_ci_id());
                                    param.put("ref_cjid",enterpatientEntity.getRef_cjid());
                                    param.put("name",enterpatientEntity.getName());
                                    param.put("sex",enterpatientEntity.getSex());
                                    param.put("birthday",enterpatientEntity.getBirthday());
                                    param.put("idcardno",enterpatientEntity.getIdcardno());
                                    param.put("jwbsbh","1;");//既往病史，1高血压
                                    param.put("jwbsjb","高血压 时间："+ExtendDate.getYMD(Calendar.getInstance())+",");//既往病史，1高血压
                                    param.put("jdrq00",ExtendDate.getYYYYMMD(Calendar.getInstance()));//既往病史，1高血压
                                    String url = PropertiesUtil.getConfValue("idCardHealthUrl") + "/appCommon.action?act=uploadHealthCareRecord";
                                    String str = null;
                                    if(OpenTheInterfaceState.NOT.getValue().equals(state)){
                                        str = HtmlUtils.getInstance().executeResponseJson(client, "post", url, param, "utf-8");
                                    }else{
                                        str = sysDao.getSecurityCardAsyncBean().getDateBasic("","",param.toString(),enterpatientEntity.getIdcardno(),"uploadHealthCareRecord");
                                    }
                                    sysDao.getSecurityCardAsyncBean().getBasicLog(param.toString(),"","",str, "","uploadHealthCareRecord");
                                }
                                jsonParam.put("df_id", ls.get(0).getDf_id());
                            }
                            jsonParam.put("mxjbbz", "1");//慢性疾病编号（1-高血压3-糖尿病）
                            jsonParam.put("sfrq00", ExtendDate.getYYYYMMD(plan.getSfFollowDate()));
                            jsonParam.put("sfysqm", plan.getSfFollowDoctorid());
                            jsonParam.put("sffs00", plan.getSfFollowMode());
                            jsonParam.put("xcsfrq", ExtendDate.getYYYYMMD(plan.getNextTime()));//下次随访日期
                            jsonParam.put("mark", "1");//慢性疾病标志(1-高血压患者用药情况，2-糖尿病患者用药情况)
                            List<AppHdBlooPressureTable> list1 = sysDao.getServiceDo().loadByPk(AppHdBlooPressureTable.class, "visitId", plan.getId());

                            if (list1 != null && list1.size() > 0) {
                                AppHdBlooPressureTable table = list1.get(0);
                                jsonParam.put("ssy", table.getBloodPressureOne());//收缩压
                                jsonParam.put("szy", table.getBloodPressureTwo());//舒张压
                                jsonParam.put("tzone", table.getWeight());//体重
                                jsonParam.put("tztwo", table.getNextWeight());//体重（计划kg）
                                jsonParam.put("sg0000", table.getHeight());//身高(cm)
                                jsonParam.put("tzzs00", table.getBmi());//体质指数
                                jsonParam.put("xlone", table.getHeartRate());//心率（高血压：目前）
                                //jsonParam.put("xltwo",table.getHeartRate());//心率（高血压：计划）
                                jsonParam.put("qttz00", table.getSignsOther());//其他体征
                                jsonParam.put("rxylone", table.getSmokingToDay());//日吸烟量（目前 支）
                                jsonParam.put("rxyltwo", table.getSmokingNextToDay());//日吸烟量（计划 支）
                                jsonParam.put("ryjlone", table.getDrinkingToDay());//日饮酒量（目前 两）
                                jsonParam.put("ryjltwo", table.getDrinkingNextToDay());//日饮酒量（计划 两）
                                jsonParam.put("ydzcone", table.getActivityToWeek());//运动周次（目前 次/周）
                                jsonParam.put("ydzctwo", table.getActivityNextToWeek());//运动周次（计划 次/周）
                                jsonParam.put("ydrcone", table.getActivityToTime());//运动分次（目前 分钟/次）
                                jsonParam.put("ydrctwo", table.getActivityNextToTime());//运动分次（计划 分钟/次）
                                CdCode salt = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_SYQK, table.getSaltSituation());
                                if (salt != null) {
                                    jsonParam.put("syqkone", salt.getCodeTitle());//摄盐情况（高血压目前：轻-轻，中-中，重-重）
                                }
                                CdCode nextSalt = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_SYQK, table.getSaltNextSituation());
                                if (nextSalt != null) {
                                    jsonParam.put("syqktwo", nextSalt.getCodeTitle());//摄盐情况（高血压计划：轻-轻，中-中，重-重）
                                }
                                jsonParam.put("xltzqk", table.getMentalityAdjust());//心理调整（1--良好，2--一般，3--差）
                                jsonParam.put("zyxwqk", table.getFollowingBehavior());//遵医情况（1--良好，2---一般，3--差）
                                jsonParam.put("qtfzjc", table.getFzCheck());//其他辅助检查
                                jsonParam.put("fyycx0", table.getMedicationAdherence());//服药依从性（1--规律，2--间断，3--不服药）
                                jsonParam.put("ywblfy", table.getDrugReaction());//药物不良反应（0--无，1--有）
                                jsonParam.put("blfyms", table.getDrugReactionContent());//不良反应描述
                                jsonParam.put("ccsffl", table.getVisitThisType());//此次随访分类（1--控制满意，2--控制不满意，3--不良反应，4--并发症）
                                jsonParam.put("zzqk00", table.getReferral());//转诊情况（0--无，1--有）
                                if (StringUtils.isNotBlank(table.getReferralReason())) {
                                    jsonParam.put("zzyy00", table.getReferralReason());//转诊原因
                                }
                                if (StringUtils.isNotBlank(table.getReferralDept())) {
                                    jsonParam.put("zzjgks", table.getReferralDept());//转诊机构科室
                                }
                                //jsonParam.put("zzbz00","");//转诊备注
                                AppDrUser drUser = (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class,plan.getSfFollowDoctorid());
                                if(drUser != null){
                                    jsonParam.put("ywysxm",drUser.getDrName());//院外医生姓名
                                    String[] drId = drUser.getId().split("_");
                                    if(drId.length > 1){
                                        jsonParam.put("sfyszj",drId[1]);//随访医生员工主键
                                    }else{
                                        jsonParam.put("sfyszj",drId[0]);//随访医生员工主键
                                    }

                                }

                                List<Map> list3 = new ArrayList<>();
                                List<AppMedicationTable> list2 = sysDao.getServiceDo().loadByPk(AppMedicationTable.class, "visitId", table.getId());
                                for (AppMedicationTable medicationTable : list2) {
                                    HashMap<String, Object> map = new HashMap<>();
                                    map.put("ywmc", medicationTable.getMedicineName());
                                    map.put("ywyf", medicationTable.getUserToDay());
                                    map.put("ywyl", medicationTable.getUserToTime());
                                    list3.add(map);
                                }
                                jsonParam.put("yyqkList", list3);
                                String[] symptoms = table.getSymptoms().split(";");
                                for (String zz : symptoms) {
                                    CdCode symptom = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_XYSYMPTOM, zz);
                                    if (symptom != null) {
                                        switch (symptom.getCodeValue()) {
                                            case "0":
                                                jsonParam.put("wuzz00", "1");//无症状
                                                break;
                                            case "1":
                                                jsonParam.put("ttty", "1");//头痛头晕
                                                break;
                                            case "2":
                                                jsonParam.put("exot", "1");//恶心呕吐
                                                break;
                                            case "3":
                                                jsonParam.put("yhem", "1");//眼花耳鸣
                                                break;
                                            case "4":
                                                jsonParam.put("hxkn", "1");//呼吸困难

                                                break;
                                            case "5":
                                                jsonParam.put("xjxm", "1");//心悸胸闷
                                                break;
                                            case "6":
                                                jsonParam.put("bccxbz", "1");//鼻衄出血不知
                                                break;
                                            case "7":
                                                jsonParam.put("szfm", "1");//四肢发麻
                                                break;
                                            case "8":
                                                jsonParam.put("xzsz", "1");//下肢水肿
                                                break;
                                            case "9":
                                                jsonParam.put("tqzz", table.getSymptomsOther());//其他
                                                break;
                                            default:
                                                break;
                                        }
                                    }
                                }


                            }

                            String url = PropertiesUtil.getConfValue("idCardHealthUrl") + "/appCommon.action?act=saveOrUpdateMxjbsf";
                            String result = null;
                            if(OpenTheInterfaceState.NOT.getValue().equals(state)){
                                result =  HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
                            }else{
                                result = sysDao.getSecurityCardAsyncBean().getDateBasic("","",jsonParam.toString(),patientId,"saveOrUpdateMxjbsf");
                            }
                            System.out.println(jsonParam.toString());
                            sysDao.getSecurityCardAsyncBean().getBasicLog(jsonParam.toString(),"","",result, "","saveOrUpdateMxjbsf");
                            if(StringUtils.isNotBlank(result)){
                                System.out.println("高血压："+plan.getId()+"："+result);
                                JSONObject jsonAll = JSONObject.fromObject(result);
                                if("800".equals(jsonAll.get("msgCode"))){
                                    plan.setSfIsOrNot(CommSF.YES.getValue());
                                    sysDao.getServiceDo().modify(plan);
                                }else{
                                    plan.setNotSuccessReason(jsonAll.get("msg").toString());
                                    sysDao.getServiceDo().modify(plan);
                                }
                            }
                        }
                    }
                }
            }
            System.out.println("结束高血压随访上传基卫调度-----------------------");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
