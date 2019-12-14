package com.ylz.bizDo.app.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ylz.bizDo.app.dao.AppPerformanceStatisticsDao;
import com.ylz.bizDo.app.entity.AppServeManageEntity;
import com.ylz.bizDo.app.entity.AppServeManagePerformanceEntity;
import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppHospDept;
import com.ylz.bizDo.app.po.AppLabelGroup;
import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.bizDo.app.po.AppPerformanceChineseGuidance;
import com.ylz.bizDo.app.po.AppPerformanceChineseHealthcare;
import com.ylz.bizDo.app.po.AppPerformanceConstitutionIdentification;
import com.ylz.bizDo.app.po.AppPerformanceHealthConsultation;
import com.ylz.bizDo.app.po.AppPerformanceHealthEducation;
import com.ylz.bizDo.app.po.AppPerformanceHealthGuidance;
import com.ylz.bizDo.app.po.AppPerformanceHealthRecord;
import com.ylz.bizDo.app.po.AppPerformanceMedicationGuidance;
import com.ylz.bizDo.app.po.AppPerformancePhysicalExamination;
import com.ylz.bizDo.app.po.AppPerformancePostpartumVisit;
import com.ylz.bizDo.app.po.AppPerformancePrenatalCare;
import com.ylz.bizDo.app.po.AppPerformanceRegularFollowup;
import com.ylz.bizDo.app.po.AppPerformanceTable;
import com.ylz.bizDo.app.po.AppPerformanceVaccination;
import com.ylz.bizDo.app.po.AppServeSetmeal;
import com.ylz.bizDo.app.po.AppSignForm;
import com.ylz.bizDo.app.vo.AppServeManageQvo;
import com.ylz.bizDo.app.vo.AppServeSetmealQvo;
import com.ylz.bizDo.app.vo.PerformanceDataQvo;
import com.ylz.bizDo.jtapp.commonVo.AppCommQvo;
import com.ylz.bizDo.jtapp.signSersetEntity.AppSignServeGroupEntity;
import com.ylz.bizDo.jtapp.signSersetEntity.AppSignServeMealEntity;
import com.ylz.bizDo.jtapp.signSersetEntity.AppSignServeObjectEntity;
import com.ylz.bizDo.jtapp.signSersetEntity.AppSignServePkEntity;
import com.ylz.bizDo.web.po.WebDrUser;
import com.ylz.bizDo.web.po.WebHospDept;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.comEnum.PerType;
import com.ylz.packcommon.common.comEnum.PerformanceType;
import com.ylz.packcommon.common.comEnum.ResidentMangeType;
import com.ylz.packcommon.common.comEnum.SignFormType;
import com.ylz.packcommon.common.util.AgeUtil;
import com.ylz.packcommon.common.util.AreaUtils;
import com.ylz.packcommon.common.util.ExtendDate;
import com.ylz.packcommon.common.util.Md5Util;
import com.ylz.packcommon.common.util.PinyinUtil;

/**
 * 履约
 */
@Service("appPerformanceStatisticsDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppPerformanceStatisticsDaoImpl implements AppPerformanceStatisticsDao {

    @Autowired
    private SysDao sysDao;

    /**
     * 添加履约数据
     * @param vo
     */
    @Override
    public void addPerformanceData(PerformanceDataQvo vo,String sermeal,String fwType) throws Exception{
        WebHospDept whosp = null;
        WebDrUser wdruser = null;
//        Calendar now = Calendar.getInstance();
//        String nowString = ExtendDate.getYMD(now);
        if(StringUtils.isBlank(vo.getPerCreateDate())){
                vo.setPerCreateDate(ExtendDate.getYMD(Calendar.getInstance()));
        }
        String nowString = vo.getPerCreateDate();
        Calendar now = ExtendDate.getCalendar(vo.getPerCreateDate());
        String year = nowString.split("-")[0];
        String month = nowString.split("-")[1];
        String yearMonth = year + "-" + month;
        String areaCode = null;
        String hospId = null;
        String hospName = null;
        String drId = null;
        String drName = null;
        //医院信息 根据医院id查询是否存在，没有自动创建医院
        AppHospDept adept= (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,vo.getHospId());
        if(adept==null){//创建医院
            whosp=new WebHospDept();
            whosp.setId(vo.getHospId());
            whosp.setHospName(vo.getHospName());
            whosp.setHospAreaCode(vo.getHospAreaCode());
            whosp.setHospAddress(vo.getHospAddress());
            whosp.setHospTel(vo.getHospTel());
            sysDao.getServiceDo().add(whosp);
            areaCode = whosp.getHospAreaCode();
            hospId = whosp.getId();
            hospName = whosp.getHospName();
        }else{
            areaCode = adept.getHospAreaCode();
            hospId = adept.getId();
            hospName = adept.getHospName();
        }
        //医生信息 根据医生id查询是否存在，没有自动创建医生
        AppDrUser dr= (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class,vo.getDrId());
        if(dr==null){
            wdruser=new WebDrUser();
            wdruser.setId(vo.getDrId());
            wdruser.setDrName(vo.getDrName());
            wdruser.setDrAccount(vo.getDrAccount());
            wdruser.setDrPwd(vo.getDrPwd());
            wdruser.setDrGender(vo.getDrGender());
            wdruser.setDrTel(vo.getDrTel());
            if(StringUtils.isNotBlank(vo.getDrTel())){
                if(vo.getDrTel().length()>=11) {
                    wdruser.setDrTelPwd(Md5Util.MD5(vo.getDrTel().substring(vo.getDrTel().length()-6,vo.getDrTel().length())));
                }
            }
            if(StringUtils.isNotBlank(wdruser.getDrName())) {
                wdruser.setDrBopomofo(PinyinUtil.getPinYinHeadChar(wdruser.getDrName()));
            }
            sysDao.getServiceDo().add(wdruser);
            drId = wdruser.getId();
            drName = wdruser.getDrName();
        }else{
            drId = dr.getId();
            drName = dr.getDrName();
        }
        //添加履约和服务包关联
        if(PerformanceType.GGFW.getValue().equals(vo.getPerType())){
            sysDao.getAppPerformanceTableDao().addPerformanceOne(vo,sermeal,fwType,vo.getPerType());
        }else if(vo.getPerType().equals(PerformanceType.JMJKDA.getValue())){//居民健康档案
            AppPerformanceHealthRecord p = new AppPerformanceHealthRecord();
            p.setPerAreaCode(areaCode);
            p.setPerCreateDate(now);
            p.setPerDrId(drId);
            p.setPerDrName(drName);
            p.setPerHospId(hospId);
            p.setPerHospName(hospName);
            p.setPerMonth(month);
            p.setPerYear(year);
            p.setPerYearMonth(yearMonth);
            p.setPerIdno(vo.getPerIdno());
            p.setPerName(vo.getPerName());
            p.setPerSource(vo.getPerSource());
            p.setPerForeign(vo.getPerForeign());
            sysDao.getServiceDo().add(p);
        }else if(vo.getPerType().equals(PerformanceType.JKJY.getValue())){//健康教育
            AppPerformanceHealthEducation p = new AppPerformanceHealthEducation();
            p.setPerAreaCode(areaCode);
            p.setPerCreateDate(now);
            p.setPerDrId(drId);
            p.setPerDrName(drName);
            p.setPerHospId(hospId);
            p.setPerHospName(hospName);
            p.setPerMonth(month);
            p.setPerYear(year);
            p.setPerYearMonth(yearMonth);
            p.setPerIdno(vo.getPerIdno());
            p.setPerName(vo.getPerName());
            p.setPerSource(vo.getPerSource());
            p.setPerForeign(vo.getPerForeign());
            sysDao.getServiceDo().add(p);
        }else if(vo.getPerType().equals(PerformanceType.JKZD.getValue())){//健康指导
            AppPerformanceHealthGuidance p = new AppPerformanceHealthGuidance();
            p.setPerAreaCode(areaCode);
            p.setPerCreateDate(now);
            p.setPerDrId(drId);
            p.setPerDrName(drName);
            p.setPerHospId(hospId);
            p.setPerHospName(hospName);
            p.setPerMonth(month);
            p.setPerYear(year);
            p.setPerYearMonth(yearMonth);
            p.setPerIdno(vo.getPerIdno());
            p.setPerName(vo.getPerName());
            p.setPerSource(vo.getPerSource());
            p.setPerForeign(vo.getPerForeign());
            sysDao.getServiceDo().add(p);
        }else if(vo.getPerType().equals(PerformanceType.DQSF.getValue())){//定期随访
            AppPerformanceRegularFollowup p = new AppPerformanceRegularFollowup();
            p.setPerAreaCode(areaCode);
            p.setPerCreateDate(now);
            p.setPerDrId(drId);
            p.setPerDrName(drName);
            p.setPerHospId(hospId);
            p.setPerHospName(hospName);
            p.setPerMonth(month);
            p.setPerYear(year);
            p.setPerYearMonth(yearMonth);
            p.setPerIdno(vo.getPerIdno());
            p.setPerName(vo.getPerName());
            p.setPerSource(vo.getPerSource());
            p.setPerFollowType(vo.getPerFollowType());
            p.setPerFollowNextDate(vo.getPerFollowNextDate());
            p.setPerForeign(vo.getPerForeign());
            sysDao.getServiceDo().add(p);
        }else if(vo.getPerType().equals(PerformanceType.JKZX.getValue())){//健康咨询
            AppPerformanceHealthConsultation p = new AppPerformanceHealthConsultation();
            p.setPerAreaCode(areaCode);
            p.setPerCreateDate(now);
            p.setPerDrId(drId);
            p.setPerDrName(drName);
            p.setPerHospId(hospId);
            p.setPerHospName(hospName);
            p.setPerMonth(month);
            p.setPerYear(year);
            p.setPerYearMonth(yearMonth);
            p.setPerIdno(vo.getPerIdno());
            p.setPerName(vo.getPerName());
            p.setPerSource(vo.getPerSource());
            p.setPerForeign(vo.getPerForeign());
            sysDao.getServiceDo().add(p);
        }else if(vo.getPerType().equals(PerformanceType.YFJZ.getValue())){//预防接种
            AppPerformanceVaccination p = new AppPerformanceVaccination();
            p.setPerAreaCode(areaCode);
            p.setPerCreateDate(now);
            p.setPerDrId(drId);
            p.setPerDrName(drName);
            p.setPerHospId(hospId);
            p.setPerHospName(hospName);
            p.setPerMonth(month);
            p.setPerYear(year);
            p.setPerYearMonth(yearMonth);
            p.setPerIdno(vo.getPerIdno());
            p.setPerName(vo.getPerName());
            p.setPerSource(vo.getPerSource());
            p.setPerForeign(vo.getPerForeign());
            sysDao.getServiceDo().add(p);
        }else if(vo.getPerType().equals(PerformanceType.ZYYJKZD.getValue())){//中医药健康指导
            AppPerformanceChineseGuidance p = new AppPerformanceChineseGuidance();
            p.setPerAreaCode(areaCode);
            p.setPerCreateDate(now);
            p.setPerDrId(drId);
            p.setPerDrName(drName);
            p.setPerHospId(hospId);
            p.setPerHospName(hospName);
            p.setPerMonth(month);
            p.setPerYear(year);
            p.setPerYearMonth(yearMonth);
            p.setPerIdno(vo.getPerIdno());
            p.setPerName(vo.getPerName());
            p.setPerSource(vo.getPerSource());
            p.setPerForeign(vo.getPerForeign());
            sysDao.getServiceDo().add(p);
        }else if(vo.getPerType().equals(PerformanceType.YQBJFW.getValue())){//孕期保健服务
            AppPerformancePrenatalCare p = new AppPerformancePrenatalCare();
            p.setPerAreaCode(areaCode);
            p.setPerCreateDate(now);
            p.setPerDrId(drId);
            p.setPerDrName(drName);
            p.setPerHospId(hospId);
            p.setPerHospName(hospName);
            p.setPerMonth(month);
            p.setPerYear(year);
            p.setPerYearMonth(yearMonth);
            p.setPerIdno(vo.getPerIdno());
            p.setPerName(vo.getPerName());
            p.setPerSource(vo.getPerSource());
            p.setPerForeign(vo.getPerForeign());
            sysDao.getServiceDo().add(p);
        }else if(vo.getPerType().equals(PerformanceType.CHFS.getValue())){//产后视访
            AppPerformancePostpartumVisit p = new AppPerformancePostpartumVisit();
            p.setPerAreaCode(areaCode);
            p.setPerCreateDate(now);
            p.setPerDrId(drId);
            p.setPerDrName(drName);
            p.setPerHospId(hospId);
            p.setPerHospName(hospName);
            p.setPerMonth(month);
            p.setPerYear(year);
            p.setPerYearMonth(yearMonth);
            p.setPerIdno(vo.getPerIdno());
            p.setPerName(vo.getPerName());
            p.setPerSource(vo.getPerSource());
            p.setPerForeign(vo.getPerForeign());
            sysDao.getServiceDo().add(p);
        }else if(vo.getPerType().equals(PerformanceType.JKTJ.getValue())){//健康体检
            AppPerformancePhysicalExamination p = new AppPerformancePhysicalExamination();
            p.setPerAreaCode(areaCode);
            p.setPerCreateDate(now);
            p.setPerDrId(drId);
            p.setPerDrName(drName);
            p.setPerHospId(hospId);
            p.setPerHospName(hospName);
            p.setPerMonth(month);
            p.setPerYear(year);
            p.setPerYearMonth(yearMonth);
            p.setPerIdno(vo.getPerIdno());
            p.setPerName(vo.getPerName());
            p.setPerSource(vo.getPerSource());
            p.setPerForeign(vo.getPerForeign());
            sysDao.getServiceDo().add(p);
        }else if(vo.getPerType().equals(PerformanceType.ZYTZBS.getValue())){//中医体质辨识
            AppPerformanceConstitutionIdentification p = new AppPerformanceConstitutionIdentification();
            p.setPerAreaCode(areaCode);
            p.setPerCreateDate(now);
            p.setPerDrId(drId);
            p.setPerDrName(drName);
            p.setPerHospId(hospId);
            p.setPerHospName(hospName);
            p.setPerMonth(month);
            p.setPerYear(year);
            p.setPerYearMonth(yearMonth);
            p.setPerIdno(vo.getPerIdno());
            p.setPerName(vo.getPerName());
            p.setPerSource(vo.getPerSource());
            p.setPerForeign(vo.getPerForeign());
            sysDao.getServiceDo().add(p);
        }else if(vo.getPerType().equals(PerformanceType.ZYYBJZD.getValue())){//中医药保健指导
            AppPerformanceChineseHealthcare p = new AppPerformanceChineseHealthcare();
            p.setPerAreaCode(areaCode);
            p.setPerCreateDate(now);
            p.setPerDrId(drId);
            p.setPerDrName(drName);
            p.setPerHospId(hospId);
            p.setPerHospName(hospName);
            p.setPerMonth(month);
            p.setPerYear(year);
            p.setPerYearMonth(yearMonth);
            p.setPerIdno(vo.getPerIdno());
            p.setPerName(vo.getPerName());
            p.setPerSource(vo.getPerSource());
            p.setPerForeign(vo.getPerForeign());
            sysDao.getServiceDo().add(p);
        }else if(vo.getPerType().equals(PerformanceType.YYZD.getValue())){//用药指导
            AppPerformanceMedicationGuidance p = new AppPerformanceMedicationGuidance();
            p.setPerAreaCode(areaCode);
            p.setPerCreateDate(now);
            p.setPerDrId(drId);
            p.setPerDrName(drName);
            p.setPerHospId(hospId);
            p.setPerHospName(hospName);
            p.setPerMonth(month);
            p.setPerYear(year);
            p.setPerYearMonth(yearMonth);
            p.setPerIdno(vo.getPerIdno());
            p.setPerName(vo.getPerName());
            p.setPerSource(vo.getPerSource());
            p.setPerForeign(vo.getPerForeign());
            sysDao.getServiceDo().add(p);
        }
    }

    /**
     * 管理端履约统计
     * @param qvo
     * @return
     */
    @Override
    public Map<String, Object> findAppSingPerformanceManage(AppCommQvo qvo) throws Exception{
    	// 存储本年已做记录的结果
    	ConcurrentHashMap<String, Integer> saveCountYearMap = new ConcurrentHashMap<String, Integer>();
    	
        Map<String,Object> returnMap = new HashMap<String,Object>();
        AppServeManageQvo manageQvo = new AppServeManageQvo();
        manageQvo.setSerYear(qvo.getSignYear());
        if(StringUtils.isNotBlank(qvo.getSignHospId())){
            AppHospDept hospDept = (AppHospDept) this.sysDao.getServiceDo().find(AppHospDept.class,qvo.getSignHospId());
            manageQvo.setAreaCode(AreaUtils.getAreaCodeAll(hospDept.getHospAreaCode(),"2"));
            manageQvo.setSerHospId(qvo.getSignHospId());
            manageQvo.setHospId(qvo.getSignHospId());
        }
        if(StringUtils.isNotBlank(qvo.getSignAreaCode())){
            manageQvo.setAreaCode(AreaUtils.getAreaCodeAll(qvo.getSignAreaCode(),"2"));
            manageQvo.setSerAreaCode(AreaUtils.getAreaCode(qvo.getSignAreaCode()));
            manageQvo.setAreaCode(qvo.getSignAreaCode());
        }

        List<AppServeManageEntity> lsServe = this.sysDao.getAppServeRoleDao().findManageEntity(manageQvo);
        if(lsServe != null && lsServe.size() >0){
            for(AppServeManageEntity vo : lsServe){
                manageQvo.setSerValue(vo.getSerValue());
                if(vo.getSerValue().equals(PerformanceType.JKJY.getValue())){//健康教育
                    int countShouldBecompleted = 0;//应完成
                    List<AppServeManagePerformanceEntity>  ls = this.getManagePerformanceCompleted(manageQvo);//应完成数
                    if(ls != null && ls.size() >0){
                        for(AppServeManagePerformanceEntity p : ls){
                            countShouldBecompleted += Integer.parseInt(p.getSetNum()) * Integer.parseInt(p.getSignCount());
                        }
                    }
                    Map<String,Object> map = new LinkedHashMap<String,Object>();
                    map.put("countShouldBecompleted",countShouldBecompleted);//应完成
                    //计算本年医生已做
                    int countyear = 0;
                    if(StringUtils.isNotBlank(manageQvo.getSerHospId())){
                        countyear = this.getHealthEducation(null,qvo.getSignYear(),null,null,null,manageQvo.getSerHospId(),null);
                    }else if(StringUtils.isNotBlank(manageQvo.getSerAreaCode())){
                        countyear = this.getHealthEducation(null,qvo.getSignYear(),null,null,null,null,manageQvo.getSerAreaCode());
                    }
                    //计算本年未做
                    int countYearNotDone = countShouldBecompleted - countyear;
                    if(countYearNotDone < 0){
                        countYearNotDone = 0;
                    }
                    map.put("countyear",countyear);
                    map.put("countYearNotDone",countYearNotDone);
                    returnMap.put("jkjy",map);
                }else if(vo.getSerValue().equals(PerformanceType.JKZD.getValue())){//健康指导
                    int countShouldBecompleted = 0;//应完成
                    List<AppServeManagePerformanceEntity>  ls = this.getManagePerformanceCompleted(manageQvo);//应完成数
                    if(ls != null && ls.size() >0){
                        for(AppServeManagePerformanceEntity p : ls){
                            countShouldBecompleted += Integer.parseInt(p.getSetNum()) * Integer.parseInt(p.getSignCount());
                        }
                    }
                    Map<String,Object> map = new LinkedHashMap<String,Object>();
                    map.put("countShouldBecompleted",countShouldBecompleted);//应完成
                    //计算本年医生已做
                    int countyear = 0;
                    if(StringUtils.isNotBlank(manageQvo.getSerHospId())){
                        countyear = this.getHealthGuidance(null,qvo.getSignYear(),null,null,null,manageQvo.getSerHospId(),null);
                    }else if(StringUtils.isNotBlank(manageQvo.getSerAreaCode())){
                        countyear = this.getHealthGuidance(null,qvo.getSignYear(),null,null,null,null,manageQvo.getSerAreaCode());
                    }
                    //计算本年未做
                    int countYearNotDone = countShouldBecompleted - countyear;
                    if(countYearNotDone < 0){
                        countYearNotDone = 0;
                    }
                    map.put("countyear",countyear);
                    map.put("countYearNotDone",countYearNotDone);
                    returnMap.put("jkzd",map);
                }else if(vo.getSerValue().equals(PerformanceType.DQSF.getValue())){//定期随访
                    List<AppServeManagePerformanceEntity>  ls = this.getManagePerformanceCompleted(manageQvo);//应完成数
                    if(ls != null && ls.size() >0) {
                        for (AppServeManagePerformanceEntity p : ls) {
                            if(p.getObjectValue().equals(ResidentMangeType.ETLZLS.getValue())){//儿童
                                String[] result = p.getSignCount().split(";;;");
                                //新生儿访视
                                Map<String,Object> mapXsyfs = new LinkedHashMap<String,Object>();
                                //应完成
                                int countShouldBecompletedEtxsfs = Integer.parseInt(result[0]);
                                //本年已做
                                int countyearEtxsfs = 0;
								if (StringUtils.isNotBlank(manageQvo.getSerHospId())) {
									String key = "SF_" + null + "_" + qvo.getSignYear() + "_" + null + "_" + "1" + "_" + null + "_" + null + "_" + manageQvo.getSerHospId() + "_" + null;
									if (saveCountYearMap.containsKey(key)) {
										countyearEtxsfs = saveCountYearMap.get(key);
									} else {
										countyearEtxsfs = this.getRegularFollowup(null, qvo.getSignYear(), null, "1", null, null, manageQvo.getSerHospId(), null);
										saveCountYearMap.put(key, countyearEtxsfs);
									}
								} else if (StringUtils.isNotBlank(manageQvo.getSerAreaCode())) {
									String key = "SF_" + null + "_" + qvo.getSignYear() + "_" + null + "_" + "1" + "_" + null + "_" + null + "_" + null + "_" + manageQvo.getSerAreaCode();
									if (saveCountYearMap.containsKey(key)) {
										countyearEtxsfs = saveCountYearMap.get(key);
									} else {
										countyearEtxsfs = this.getRegularFollowup(null, qvo.getSignYear(), null, "1", null, null, null, manageQvo.getSerAreaCode());
										saveCountYearMap.put(key, countyearEtxsfs);
									}
								}
                                mapXsyfs.put("countShouldBecompleted",countShouldBecompletedEtxsfs);
                                mapXsyfs.put("countyear",countyearEtxsfs);
                                returnMap.put("etxsfs",mapXsyfs);
                                //儿童体检
                                Map<String,Object> mapEttj = new LinkedHashMap<String,Object>();
                                //应完成
                                int countShouldBecompletedEttj = Integer.parseInt(result[1]);
                                //本年已做
                                int countyearEttj = 0;
								if (StringUtils.isNotBlank(manageQvo.getSerHospId())) {
									String key = "SF_" + null + "_" + qvo.getSignYear() + "_" + null + "_" + "2" + "_" + null + "_" + null + "_" + manageQvo.getSerHospId() + "_" + null;
									if (saveCountYearMap.containsKey(key)) {
										countyearEttj = saveCountYearMap.get(key);
									} else {
										countyearEttj = this.getRegularFollowup(null, qvo.getSignYear(), null, "2", null, null, manageQvo.getSerHospId(), null);
										saveCountYearMap.put(key, countyearEttj);
									}
								} else if (StringUtils.isNotBlank(manageQvo.getSerAreaCode())) {
									String key = "SF_" + null + "_" + qvo.getSignYear() + "_" + null + "_" + "2" + "_" + null + "_" + null + "_" + null + "_" + manageQvo.getSerAreaCode();
									if (saveCountYearMap.containsKey(key)) {
										countyearEttj = saveCountYearMap.get(key);
									} else {
										countyearEttj = this.getRegularFollowup(null, qvo.getSignYear(), null, "2", null, null, null, manageQvo.getSerAreaCode());
										saveCountYearMap.put(key, countyearEttj);
									}
								}
                                //计算本年未做
                                int countYearNotDoneEttj = countShouldBecompletedEttj - countyearEttj;
                                if(countYearNotDoneEttj < 0){
                                    countYearNotDoneEttj = 0;
                                }
                                mapEttj.put("countShouldBecompleted",countShouldBecompletedEttj);
                                mapEttj.put("countyear",countyearEttj);
                                mapEttj.put("countYearNotDone",countYearNotDoneEttj);
                                returnMap.put("ettj",mapEttj);
                            }else if(p.getObjectValue().equals(ResidentMangeType.GXY.getValue())){//高血压
                                //高血压
                                Map<String,Object> mapGxy = new LinkedHashMap<String,Object>();
                                //应完成
                                int countShouldBecompletedGxy = Integer.parseInt(p.getSetNum()) * Integer.parseInt(p.getSignCount());
                                //本年已做
                                int countyearGxy = 0;
								if (StringUtils.isNotBlank(manageQvo.getSerHospId())) {
									String key = "SF_" + null + "_" + qvo.getSignYear() + "_" + null + "_" + "5" + "_" + null + "_" + null + "_" + manageQvo.getSerHospId() + "_" + null;
									if (saveCountYearMap.containsKey(key)) {
										countyearGxy = saveCountYearMap.get(key);
									} else {
										countyearGxy = this.getRegularFollowup(null, qvo.getSignYear(), null, "5", null, null, manageQvo.getSerHospId(), null);
										saveCountYearMap.put(key, countyearGxy);
									}
								} else if (StringUtils.isNotBlank(manageQvo.getSerAreaCode())) {
									String key = "SF_" + null + "_" + qvo.getSignYear() + "_" + null + "_" + "5" + "_" + null + "_" + null + "_" + null + "_" + manageQvo.getSerAreaCode();
									if (saveCountYearMap.containsKey(key)) {
										countyearGxy = saveCountYearMap.get(key);
									} else {
										countyearGxy = this.getRegularFollowup(null, qvo.getSignYear(), null, "5", null, null, null, manageQvo.getSerAreaCode());
										saveCountYearMap.put(key, countyearGxy);
									}
								}
                                //计算本年未做
                                int countYearNotDoneGxy = countShouldBecompletedGxy - countyearGxy;
                                if(countYearNotDoneGxy < 0){
                                    countYearNotDoneGxy = 0;
                                }
                                mapGxy.put("countShouldBecompleted",countShouldBecompletedGxy);
                                mapGxy.put("countyear",countyearGxy);
                                mapGxy.put("countYearNotDone",countYearNotDoneGxy);
                                returnMap.put("gxy",mapGxy);
                            }else if(p.getObjectValue().equals(ResidentMangeType.TNB.getValue())){//糖尿病
                                //糖尿病
                                Map<String,Object> mapTnb = new LinkedHashMap<String,Object>();
                                //应完成
                                int countShouldBecompletedTnb = Integer.parseInt(p.getSetNum()) * Integer.parseInt(p.getSignCount());
                                //本年已做
                                int countyearTnb = 0;
								if (StringUtils.isNotBlank(manageQvo.getSerHospId())) {
									String key = "SF_" + null + "_" + qvo.getSignYear() + "_" + null + "_" + "6" + "_" + null + "_" + null + "_" + manageQvo.getSerHospId() + "_" + null;
									if (saveCountYearMap.containsKey(key)) {
										countyearTnb = saveCountYearMap.get(key);
									} else {
										countyearTnb = this.getRegularFollowup(null, qvo.getSignYear(), null, "6", null, null, manageQvo.getSerHospId(), null);
										saveCountYearMap.put(key, countyearTnb);
									}
								} else if (StringUtils.isNotBlank(manageQvo.getSerAreaCode())) {
									String key = "SF_" + null + "_" + qvo.getSignYear() + "_" + null + "_" + "6" + "_" + null + "_" + null + "_" + null + "_" + manageQvo.getSerAreaCode();
									if (saveCountYearMap.containsKey(key)) {
										countyearTnb = saveCountYearMap.get(key);
									} else {
										countyearTnb = this.getRegularFollowup(null, qvo.getSignYear(), null, "6", null, null, null, manageQvo.getSerAreaCode());
										saveCountYearMap.put(key, countyearTnb);
									}
								}
                                //计算本年未做
                                int countYearNotDoneTnb = countShouldBecompletedTnb - countyearTnb;
                                if(countYearNotDoneTnb < 0){
                                    countYearNotDoneTnb = 0;
                                }
                                mapTnb.put("countShouldBecompleted",countShouldBecompletedTnb);
                                mapTnb.put("countyear",countyearTnb);
                                mapTnb.put("countYearNotDone",countYearNotDoneTnb);
                                returnMap.put("tnb",mapTnb);
                            }else if(p.getObjectValue().equals(ResidentMangeType.YZJSZY.getValue())){//严重精神障碍
                                //严重精神障碍
                                Map<String,Object> mapJc = new LinkedHashMap<String,Object>();
                                //应完成
                                int countShouldBecompletedJc = Integer.parseInt(p.getSetNum()) * Integer.parseInt(p.getSignCount());
                                //本年已做
                                int countyearJc = 0;
								if (StringUtils.isNotBlank(manageQvo.getSerHospId())) {
									String key = "SF_" + null + "_" + qvo.getSignYear() + "_" + null + "_" + "7" + "_" + null + "_" + null + "_" + manageQvo.getSerHospId() + "_" + null;
									if (saveCountYearMap.containsKey(key)) {
										countyearJc = saveCountYearMap.get(key);
									} else {
										countyearJc = this.getRegularFollowup(null, qvo.getSignYear(), null, "7", null, null, manageQvo.getSerHospId(), null);
										saveCountYearMap.put(key, countyearJc);
									}
								} else if (StringUtils.isNotBlank(manageQvo.getSerAreaCode())) {
									String key = "SF_" + null + "_" + qvo.getSignYear() + "_" + null + "_" + "7" + "_" + null + "_" + null + "_" + null + "_" + manageQvo.getSerAreaCode();
									if (saveCountYearMap.containsKey(key)) {
										countyearJc = saveCountYearMap.get(key);
									} else {
										countyearJc = this.getRegularFollowup(null, qvo.getSignYear(), null, "7", null, null, null, manageQvo.getSerAreaCode());
										saveCountYearMap.put(key, countyearJc);
									}
								}
                                //计算本年未做
                                int countYearNotDoneJc = countShouldBecompletedJc - countyearJc;
                                if(countYearNotDoneJc < 0){
                                    countYearNotDoneJc = 0;
                                }
                                mapJc.put("countShouldBecompleted",countShouldBecompletedJc);
                                mapJc.put("countyear",countyearJc);
                                mapJc.put("countYearNotDone",countYearNotDoneJc);
                                returnMap.put("yzjczi",mapJc);
                            }else if(p.getObjectValue().equals(ResidentMangeType.JHB.getValue())){//结核病
                                //结核病
                                Map<String,Object> mapJhb = new LinkedHashMap<String,Object>();
                                ////应完成
                                int countShouldBecompletedJnb = Integer.parseInt(p.getSetNum()) * Integer.parseInt(p.getSignCount());
                                ////本年已做
                                int countyearJnb = 0;
								if (StringUtils.isNotBlank(manageQvo.getSerHospId())) {
									String key = "SF_" + null + "_" + qvo.getSignYear() + "_" + null + "_" + "8" + "_" + null + "_" + null + "_" + manageQvo.getSerHospId() + "_" + null;
									if (saveCountYearMap.containsKey(key)) {
										countyearJnb = saveCountYearMap.get(key);
									} else {
										countyearJnb = this.getRegularFollowup(null, qvo.getSignYear(), null, "8", null, null, manageQvo.getSerHospId(), null);
										saveCountYearMap.put(key, countyearJnb);
									}
								} else if (StringUtils.isNotBlank(manageQvo.getSerAreaCode())) {
									String key = "SF_" + null + "_" + qvo.getSignYear() + "_" + null + "_" + "8" + "_" + null + "_" + null + "_" + null + "_" + manageQvo.getSerAreaCode();
									if (saveCountYearMap.containsKey(key)) {
										countyearJnb = saveCountYearMap.get(key);
									} else {
										countyearJnb = this.getRegularFollowup(null, qvo.getSignYear(), null, "8", null, null, null, manageQvo.getSerAreaCode());
										saveCountYearMap.put(key, countyearJnb);
									}
								}
                                //计算本年未做
                                int countYearNotDoneJnb = countShouldBecompletedJnb - countyearJnb;
                                if(countYearNotDoneJnb < 0){
                                    countYearNotDoneJnb = 0;
                                }
                                mapJhb.put("countShouldBecompleted",countShouldBecompletedJnb);
                                mapJhb.put("countyear",countyearJnb);
                                mapJhb.put("countYearNotDone",countYearNotDoneJnb);
                                returnMap.put("jhb",mapJhb);
                            }
                        }
                    }
                }else if(vo.getSerValue().equals(PerformanceType.JKZX.getValue())){//健康咨询
                    Map<String,Object> map = new LinkedHashMap<String,Object>();
                    //计算本年医生已做
                    int countyear = 0;
					if (StringUtils.isNotBlank(manageQvo.getSerHospId())) {
						String key = "JKZX_" + null + "_" + qvo.getSignYear() + "_" + null + "_" + null + "_" + null + "_" + manageQvo.getSerHospId() + "_" + null;
						if (saveCountYearMap.containsKey(key)) {
							countyear = saveCountYearMap.get(key);
						} else {
							countyear = this.getHealthConsultation(null, qvo.getSignYear(), null, null, null, manageQvo.getSerHospId(), null);
							saveCountYearMap.put(key, countyear);
						}
					} else if (StringUtils.isNotBlank(manageQvo.getSerAreaCode())) {
						String key = "JKZX_" + null + "_" + qvo.getSignYear() + "_" + null + "_" + null + "_" + null + "_" + null + "_" + manageQvo.getSerAreaCode();
						if (saveCountYearMap.containsKey(key)) {
							countyear = saveCountYearMap.get(key);
						} else {
							countyear = this.getHealthConsultation(null, qvo.getSignYear(), null, null, null, null, manageQvo.getSerAreaCode());
							saveCountYearMap.put(key, countyear);
						}
					}
                    map.put("countyear",countyear);
                    returnMap.put("jkzx",map);
                }else if(vo.getSerValue().equals(PerformanceType.ZYYJKZD.getValue())){//中医药健康指导
                    Map<String,Object> map = new LinkedHashMap<String,Object>();
                    int countShouldBecompleted = 0;//应完成
                    List<AppServeManagePerformanceEntity>  ls = this.getManagePerformanceCompleted(manageQvo);//应完成数
                    if(ls != null && ls.size() >0){
                        for(AppServeManagePerformanceEntity p : ls){
                            countShouldBecompleted += Integer.parseInt(p.getSetNum()) * Integer.parseInt(p.getSignCount());
                        }
                    }
                    map.put("countShouldBecompleted",countShouldBecompleted);//应完成
                    //计算本年医生已做
                    int countyear = 0;
					if (StringUtils.isNotBlank(manageQvo.getSerHospId())) {
						String key = "ZYYJKZD_" + null + "_" + qvo.getSignYear() + "_" + null + "_" + null + "_" + null + "_" + manageQvo.getSerHospId() + "_" + null;
						if (saveCountYearMap.containsKey(key)) {
							countyear = saveCountYearMap.get(key);
						} else {
							countyear = this.getChineseGuidance(null, qvo.getSignYear(), null, null, null, manageQvo.getSerHospId(), null);
							saveCountYearMap.put(key, countyear);
						}
					} else if (StringUtils.isNotBlank(manageQvo.getSerAreaCode())) {
						String key = "ZYYJKZD_" + null + "_" + qvo.getSignYear() + "_" + null + "_" + null + "_" + null + "_" + null + "_" + manageQvo.getSerAreaCode();
						if (saveCountYearMap.containsKey(key)) {
							countyear = saveCountYearMap.get(key);
						} else {
							countyear = this.getChineseGuidance(null, qvo.getSignYear(), null, null, null, null, manageQvo.getSerAreaCode());
							saveCountYearMap.put(key, countyear);
						}
					}
                    //计算本年未做
                    int countYearNotDone = countShouldBecompleted - countyear;
                    if(countYearNotDone < 0){
                        countYearNotDone = 0;
                    }
                    map.put("countyear",countyear);
                    map.put("countYearNotDone",countYearNotDone);
                    returnMap.put("zyyjkzd",map);
                }else if(vo.getSerValue().equals(PerformanceType.YQBJFW.getValue())){//孕期保健服务
                    Map<String,Object> map = new LinkedHashMap<String,Object>();
                    int countShouldBecompleted = 0;//应完成
                    List<AppServeManagePerformanceEntity>  ls = this.getManagePerformanceCompleted(manageQvo);//应完成数
                    if(ls != null && ls.size() >0){
                        for(AppServeManagePerformanceEntity p : ls){
                            countShouldBecompleted += Integer.parseInt(p.getSetNum()) * Integer.parseInt(p.getSignCount());
                        }
                    }
                    map.put("countShouldBecompleted",countShouldBecompleted);//应完成
                    //计算本年医生已做
                    int countyear = 0;
					if (StringUtils.isNotBlank(manageQvo.getSerHospId())) {
						String key = "YQBJFW_" + null + "_" + qvo.getSignYear() + "_" + null + "_" + null + "_" + null + "_" + manageQvo.getSerHospId() + "_" + null;
						if (saveCountYearMap.containsKey(key)) {
							countyear = saveCountYearMap.get(key);
						} else {
							countyear = this.getPrenatalCare(null, qvo.getSignYear(), null, null, null, manageQvo.getSerHospId(), null);
							saveCountYearMap.put(key, countyear);
						}
					} else if (StringUtils.isNotBlank(manageQvo.getSerAreaCode())) {
						String key = "YQBJFW_" + null + "_" + qvo.getSignYear() + "_" + null + "_" + null + "_" + null + "_" + null + "_" + manageQvo.getSerAreaCode();
						if (saveCountYearMap.containsKey(key)) {
							countyear = saveCountYearMap.get(key);
						} else {
							countyear = this.getPrenatalCare(null, qvo.getSignYear(), null, null, null, null, manageQvo.getSerAreaCode());
							saveCountYearMap.put(key, countyear);
						}
					}
                    //计算本年未做
                    int countYearNotDone = countShouldBecompleted - countyear;
                    if(countYearNotDone < 0){
                        countYearNotDone = 0;
                    }
                    map.put("countyear",countyear);
                    map.put("countYearNotDone",countYearNotDone);
                    returnMap.put("yqbjfw",map);
                }else if(vo.getSerValue().equals(PerformanceType.CHFS.getValue())){//产后视访
                    Map<String,Object> map = new LinkedHashMap<String,Object>();
                    int countShouldBecompleted = 0;//应完成
                    List<AppServeManagePerformanceEntity>  ls = this.getManagePerformanceCompleted(manageQvo);//应完成数
                    if(ls != null && ls.size() >0){
                        for(AppServeManagePerformanceEntity p : ls){
                            countShouldBecompleted += Integer.parseInt(p.getSetNum()) * Integer.parseInt(p.getSignCount());
                        }
                    }
                    map.put("countShouldBecompleted",countShouldBecompleted);//应完成
                    //计算本年医生已做
                    int countyear = 0;
					if (StringUtils.isNotBlank(manageQvo.getSerHospId())) {
						String key = "CHFS_" + null + "_" + qvo.getSignYear() + "_" + null + "_" + null + "_" + null + "_" + manageQvo.getSerHospId() + "_" + null;
						if (saveCountYearMap.containsKey(key)) {
							countyear = saveCountYearMap.get(key);
						} else {
							countyear = this.getPostpartumVisit(null, qvo.getSignYear(), null, null, null, manageQvo.getSerHospId(), null);
							saveCountYearMap.put(key, countyear);
						}
					} else if (StringUtils.isNotBlank(manageQvo.getSerAreaCode())) {
						String key = "CHFS_" + null + "_" + qvo.getSignYear() + "_" + null + "_" + null + "_" + null + "_" + null + "_" + manageQvo.getSerAreaCode();
						if (saveCountYearMap.containsKey(key)) {
							countyear = saveCountYearMap.get(key);
						} else {
							countyear = this.getPostpartumVisit(null, qvo.getSignYear(), null, null, null, null, manageQvo.getSerAreaCode());
							saveCountYearMap.put(key, countyear);
						}
					}
                    //计算本年未做
                    int countYearNotDone = countShouldBecompleted - countyear;
                    if(countYearNotDone < 0){
                        countYearNotDone = 0;
                    }
                    map.put("countyear",countyear);
                    map.put("countYearNotDone",countYearNotDone);
                    returnMap.put("chfs",map);
                }else if(vo.getSerValue().equals(PerformanceType.JKTJ.getValue())){//健康体检
                    int countShouldBecompleted = 0;//应完成
                    List<AppServeManagePerformanceEntity>  ls = this.getManagePerformanceCompleted(manageQvo);//应完成数
                    if(ls != null && ls.size() >0){
                        for(AppServeManagePerformanceEntity p : ls){
                            countShouldBecompleted += Integer.parseInt(p.getSetNum()) * Integer.parseInt(p.getSignCount());
                        }
                    }
                    Map<String,Object> map = new LinkedHashMap<String,Object>();
                    map.put("countShouldBecompleted",countShouldBecompleted);//应完成
                    //计算本年医生已做
                    int countyear = 0;
					if (StringUtils.isNotBlank(manageQvo.getSerHospId())) {
						String key = "JKTJ_" + null + "_" + qvo.getSignYear() + "_" + null + "_" + null + "_" + null + "_" + manageQvo.getSerHospId() + "_" + null;
						if (saveCountYearMap.containsKey(key)) {
							countyear = saveCountYearMap.get(key);
						} else {
							countyear = this.getPhysicalExamination(null, qvo.getSignYear(), null, null, null, manageQvo.getSerHospId(), null);
							saveCountYearMap.put(key, countyear);
						}
					} else if (StringUtils.isNotBlank(manageQvo.getSerAreaCode())) {
						String key = "JKTJ_" + null + "_" + qvo.getSignYear() + "_" + null + "_" + null + "_" + null + "_" + null + "_" + manageQvo.getSerAreaCode();
						if (saveCountYearMap.containsKey(key)) {
							countyear = saveCountYearMap.get(key);
						} else {
							countyear = this.getPhysicalExamination(null, qvo.getSignYear(), null, null, null, null, manageQvo.getSerAreaCode());
							saveCountYearMap.put(key, countyear);
						}
					}
                    //计算本年未做
                    int countYearNotDone = countShouldBecompleted - countyear;
                    if(countYearNotDone < 0){
                        countYearNotDone = 0;
                    }
                    map.put("countyear",countyear);
                    map.put("countYearNotDone",countYearNotDone);
                    returnMap.put("jktj",map);
                }else if(vo.getSerValue().equals(PerformanceType.ZYTZBS.getValue())){//中医体质辨识
                    Map<String,Object> map = new LinkedHashMap<String,Object>();
                    int countShouldBecompleted = 0;//应完成
                    List<AppServeManagePerformanceEntity>  ls = this.getManagePerformanceCompleted(manageQvo);//应完成数
                    if(ls != null && ls.size() >0){
                        for(AppServeManagePerformanceEntity p : ls){
                            countShouldBecompleted += Integer.parseInt(p.getSetNum()) * Integer.parseInt(p.getSignCount());
                        }
                    }
                    map.put("countShouldBecompleted",countShouldBecompleted);//应完成
                    //计算本年医生已做
                    int countyear = 0;
					if (StringUtils.isNotBlank(manageQvo.getSerHospId())) {
						String key = "ZYTZBS_" + null + "_" + qvo.getSignYear() + "_" + null + "_" + null + "_" + null + "_" + manageQvo.getSerHospId() + "_" + null;
						if (saveCountYearMap.containsKey(key)) {
							countyear = saveCountYearMap.get(key);
						} else {
							countyear = this.getConstitutionIdentification(null, qvo.getSignYear(), null, null, null, manageQvo.getSerHospId(), null);
							saveCountYearMap.put(key, countyear);
						}
					} else if (StringUtils.isNotBlank(manageQvo.getSerAreaCode())) {
						String key = "ZYTZBS_" + null + "_" + qvo.getSignYear() + "_" + null + "_" + null + "_" + null + "_" + null + "_" + manageQvo.getSerAreaCode();
						if (saveCountYearMap.containsKey(key)) {
							countyear = saveCountYearMap.get(key);
						} else {
							countyear = this.getConstitutionIdentification(null, qvo.getSignYear(), null, null, null, null, manageQvo.getSerAreaCode());
							saveCountYearMap.put(key, countyear);
						}
					}
                    //计算本年未做
                    int countYearNotDone = countShouldBecompleted - countyear;
                    if(countYearNotDone < 0){
                        countYearNotDone = 0;
                    }
                    map.put("countyear",countyear);
                    map.put("countYearNotDone",countYearNotDone);
                    returnMap.put("zytzbs",map);
                }else if(vo.getSerValue().equals(PerformanceType.YYZD.getValue())){//用药指导
                    Map<String,Object> map = new LinkedHashMap<String,Object>();
                    int countShouldBecompleted = 0;//应完成
                    List<AppServeManagePerformanceEntity>  ls = this.getManagePerformanceCompleted(manageQvo);//应完成数
                    if(ls != null && ls.size() >0){
                        for(AppServeManagePerformanceEntity p : ls){
                            countShouldBecompleted += Integer.parseInt(p.getSetNum()) * Integer.parseInt(p.getSignCount());
                        }
                    }
                    map.put("countShouldBecompleted",countShouldBecompleted);//应完成
                    //计算本年医生已做
                    int countyear = 0;
					if (StringUtils.isNotBlank(manageQvo.getSerHospId())) {
						String key = "YYZD_" + null + "_" + qvo.getSignYear() + "_" + null + "_" + null + "_" + null + "_" + manageQvo.getSerHospId() + "_" + null;
						if (saveCountYearMap.containsKey(key)) {
							countyear = saveCountYearMap.get(key);
						} else {
							countyear = this.getMedicationGuidance(null, qvo.getSignYear(), null, null, null, manageQvo.getSerHospId(), null);
							saveCountYearMap.put(key, countyear);
						}
					} else if (StringUtils.isNotBlank(manageQvo.getSerAreaCode())) {
						String key = "YYZD_" + null + "_" + qvo.getSignYear() + "_" + null + "_" + null + "_" + null + "_" + null + "_" + manageQvo.getSerAreaCode();
						if (saveCountYearMap.containsKey(key)) {
							countyear = saveCountYearMap.get(key);
						} else {
							countyear = this.getMedicationGuidance(null, qvo.getSignYear(), null, null, null, null,	manageQvo.getSerAreaCode());
							saveCountYearMap.put(key, countyear);
						}
					}
                    //计算本年未做
                    int countYearNotDone = countShouldBecompleted - countyear;
                    if(countYearNotDone < 0){
                        countYearNotDone = 0;
                    }
                    map.put("countyear",countyear);
                    map.put("countYearNotDone",countYearNotDone);
                    returnMap.put("yyzd",map);
                }
            }
        }
        return returnMap;
    }
    /**
     * 医生履约统计
     * @param qvo
     * @return
     */
    @Override
    public Map<String, Object> findAppSingPerformanceDr(AppCommQvo qvo) throws Exception{
        Map<String,Object> returnMap = new HashMap<String,Object>();
        AppDrUser drUser = (AppDrUser) this.sysDao.getServiceDo().find(AppDrUser.class,qvo.getDrId());
        AppHospDept hospDept = (AppHospDept) this.sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
        String drId = drUser.getId();
        AppServeManageQvo manageQvo = new AppServeManageQvo();
        manageQvo.setSerYear(qvo.getSignYear());
        manageQvo.setHospId(drUser.getDrHospId());
        manageQvo.setAreaCode(AreaUtils.getAreaCodeAll(hospDept.getHospAreaCode(),"2"));
        List<AppServeManageEntity> lsServe = this.sysDao.getAppServeRoleDao().findManageEntity(manageQvo);
        if(lsServe != null && lsServe.size() >0){
            for(AppServeManageEntity vo : lsServe){
                manageQvo.setSerValue(vo.getSerValue());
                manageQvo.setSerDrId(drUser.getId());
                if(vo.getSerValue().equals(PerformanceType.JKJY.getValue())){//健康教育
                    int countShouldBecompleted = 0;//应完成
                    List<AppServeManagePerformanceEntity>  ls = this.getManagePerformanceCompleted(manageQvo);//应完成数
                    if(ls != null && ls.size() >0){
                        for(AppServeManagePerformanceEntity p : ls){
                            countShouldBecompleted += Integer.parseInt(p.getSetNum()) * Integer.parseInt(p.getSignCount());
                        }
                    }
                    Map<String,Object> map = new LinkedHashMap<String,Object>();
                    map.put("countShouldBecompleted",countShouldBecompleted);//应完成
                    //计算本年医生已做
                    int countyear = this.getHealthEducation(null,qvo.getSignYear(),null,null,drId,null,null);
                    //计算本年未做
                    int countYearNotDone = countShouldBecompleted - countyear;
                    if(countYearNotDone < 0){
                        countYearNotDone = 0;
                    }
                    map.put("countyear",countyear);
                    map.put("countYearNotDone",countYearNotDone);
                    map.put("type", PerType.JKJY.getValue());
                    map.put("drId",drId);
                    returnMap.put("jkjy",map);
                }else if(vo.getSerValue().equals(PerformanceType.JKZD.getValue())){//健康指导
                    int countShouldBecompleted = 0;//应完成
                    List<AppServeManagePerformanceEntity>  ls = this.getManagePerformanceCompleted(manageQvo);//应完成数
                    if(ls != null && ls.size() >0){
                        for(AppServeManagePerformanceEntity p : ls){
                            countShouldBecompleted += Integer.parseInt(p.getSetNum()) * Integer.parseInt(p.getSignCount());
                        }
                    }
                    Map<String,Object> map = new LinkedHashMap<String,Object>();
                    map.put("countShouldBecompleted",countShouldBecompleted);//应完成
                    //计算本年医生已做
                    int countyear = this.getHealthGuidance(null,qvo.getSignYear(),null,null,drId,null,null);
                    //计算本年未做
                    int countYearNotDone = countShouldBecompleted - countyear;
                    if(countYearNotDone < 0){
                        countYearNotDone = 0;
                    }
                    map.put("countyear",countyear);
                    map.put("countYearNotDone",countYearNotDone);
                    map.put("type",PerType.JKZD.getValue());
                    map.put("drId",drId);
                    returnMap.put("jkzd",map);
                }else if(vo.getSerValue().equals(PerformanceType.DQSF.getValue())){//定期随访
                    List<AppServeManagePerformanceEntity>  ls = this.getManagePerformanceCompleted(manageQvo);//应完成数
                    if(ls != null && ls.size() >0) {
                        for (AppServeManagePerformanceEntity p : ls) {
                            if(p.getObjectValue().equals(ResidentMangeType.ETLZLS.getValue())){//儿童
                                String[] result = p.getSignCount().split(";;;");
                                //新生儿访视
                                Map<String,Object> mapXsyfs = new LinkedHashMap<String,Object>();
                                //应完成
                                int countShouldBecompletedEtxsfs = Integer.parseInt(result[0]);
                                //本年已做
                                int countyearEtxsfs = this.getRegularFollowup(null,qvo.getSignYear(),null,"1",null,drId,null,null);
                                mapXsyfs.put("countShouldBecompleted",countShouldBecompletedEtxsfs);
                                mapXsyfs.put("countyear",countyearEtxsfs);
//                                mapXsyfs.put("type",PerType.XSEFS.getValue());
//                                mapXsyfs.put("drId",drId);
                                returnMap.put("etxsfs",mapXsyfs);
                                //儿童体检
                                Map<String,Object> mapEttj = new LinkedHashMap<String,Object>();
                                //应完成
                                int countShouldBecompletedEttj = Integer.parseInt(result[1]);
                                //本年已做
                                int countyearEttj = this.getRegularFollowup(null,qvo.getSignYear(),null,"2",null,drId,null,null);
                                //计算本年未做
                                int countYearNotDoneEttj = countShouldBecompletedEttj - countyearEttj;
                                if(countYearNotDoneEttj < 0){
                                    countYearNotDoneEttj = 0;
                                }
                                mapEttj.put("countShouldBecompleted",countShouldBecompletedEttj);
                                mapEttj.put("countyear",countyearEttj);
                                mapEttj.put("countYearNotDone",countYearNotDoneEttj);
                                mapEttj.put("type",PerType.ETJKTJ.getValue());
                                mapEttj.put("drId",drId);
                                returnMap.put("ettj",mapEttj);
                            }else if(p.getObjectValue().equals(ResidentMangeType.GXY.getValue())){//高血压
                                //高血压
                                Map<String,Object> mapGxy = new LinkedHashMap<String,Object>();
                                //应完成
                                int countShouldBecompletedGxy = Integer.parseInt(p.getSetNum()) * Integer.parseInt(p.getSignCount());
                                //本年已做
                                int countyearGxy = this.getRegularFollowup(null,qvo.getSignYear(),null,"5",null,drId,null,null);
                                //计算本年未做
                                int countYearNotDoneGxy = countShouldBecompletedGxy - countyearGxy;
                                if(countYearNotDoneGxy < 0){
                                    countYearNotDoneGxy = 0;
                                }
                                mapGxy.put("countShouldBecompleted",countShouldBecompletedGxy);
                                mapGxy.put("countyear",countyearGxy);
                                mapGxy.put("countYearNotDone",countYearNotDoneGxy);
                                mapGxy.put("type",PerType.GXYSF.getValue());
                                mapGxy.put("drId",drId);
                                returnMap.put("gxy",mapGxy);
                            }else if(p.getObjectValue().equals(ResidentMangeType.TNB.getValue())){//糖尿病
                                //糖尿病
                                Map<String,Object> mapTnb = new LinkedHashMap<String,Object>();
                                //应完成
                                int countShouldBecompletedTnb = Integer.parseInt(p.getSetNum()) * Integer.parseInt(p.getSignCount());
                                //本年已做
                                int countyearTnb = this.getRegularFollowup(null,qvo.getSignYear(),null,"6",null,drId,null,null);
                                //计算本年未做
                                int countYearNotDoneTnb = countShouldBecompletedTnb - countyearTnb;
                                if(countYearNotDoneTnb < 0){
                                    countYearNotDoneTnb = 0;
                                }
                                mapTnb.put("countShouldBecompleted",countShouldBecompletedTnb);
                                mapTnb.put("countyear",countyearTnb);
                                mapTnb.put("countYearNotDone",countYearNotDoneTnb);
                                mapTnb.put("type",PerType.TNBSF.getValue());
                                mapTnb.put("drId",drId);
                                returnMap.put("tnb",mapTnb);
                            }else if(p.getObjectValue().equals(ResidentMangeType.YZJSZY.getValue())){//严重精神障碍
                                //严重精神障碍
                                Map<String,Object> mapJc = new LinkedHashMap<String,Object>();
                                //应完成
                                int countShouldBecompletedJc = Integer.parseInt(p.getSetNum()) * Integer.parseInt(p.getSignCount());
                                //本年已做
                                int countyearJc= this.getRegularFollowup(null,qvo.getSignYear(),null,"7",null,drId,null,null);
                                //计算本年未做
                                int countYearNotDoneJc = countShouldBecompletedJc - countyearJc;
                                if(countYearNotDoneJc < 0){
                                    countYearNotDoneJc = 0;
                                }
                                mapJc.put("countShouldBecompleted",countShouldBecompletedJc);
                                mapJc.put("countyear",countyearJc);
                                mapJc.put("countYearNotDone",countYearNotDoneJc);
                                mapJc.put("type",PerType.ZXJSBSF.getValue());
                                mapJc.put("drId",drId);
                                returnMap.put("yzjczi",mapJc);
                            }else if(p.getObjectValue().equals(ResidentMangeType.JHB.getValue())){//结核病
                                //结核病
                                Map<String,Object> mapJhb = new LinkedHashMap<String,Object>();
                                ////应完成
                                int countShouldBecompletedJnb = Integer.parseInt(p.getSetNum()) * Integer.parseInt(p.getSignCount());
                                ////本年已做
                                int countyearJnb = this.getRegularFollowup(null,qvo.getSignYear(),null,"8",null,drId,null,null);
                                //计算本年未做
                                int countYearNotDoneJnb = countShouldBecompletedJnb - countyearJnb;
                                if(countYearNotDoneJnb < 0){
                                    countYearNotDoneJnb = 0;
                                }
                                mapJhb.put("countShouldBecompleted",countShouldBecompletedJnb);
                                mapJhb.put("countyear",countyearJnb);
                                mapJhb.put("countYearNotDone",countYearNotDoneJnb);
                                mapJhb.put("type",PerType.JHBSF.getValue());
                                mapJhb.put("drId",drId);
                                returnMap.put("jhb",mapJhb);
                            }
                        }
                    }
                }else if(vo.getSerValue().equals(PerformanceType.JKZX.getValue())){//健康咨询
                    Map<String,Object> map = new LinkedHashMap<String,Object>();
                    //计算本年医生已做
                    int countyear = this.getHealthConsultation(null,qvo.getSignYear(),null,null,drId,null,null);
                    map.put("countyear",countyear);
                    returnMap.put("jkzx",map);
                }else if(vo.getSerValue().equals(PerformanceType.ZYYJKZD.getValue())){//中医药健康指导
                    Map<String,Object> map = new LinkedHashMap<String,Object>();
                    int countShouldBecompleted = 0;//应完成
                    List<AppServeManagePerformanceEntity>  ls = this.getManagePerformanceCompleted(manageQvo);//应完成数
                    if(ls != null && ls.size() >0){
                        for(AppServeManagePerformanceEntity p : ls){
                            countShouldBecompleted += Integer.parseInt(p.getSetNum()) * Integer.parseInt(p.getSignCount());
                        }
                    }
                    map.put("countShouldBecompleted",countShouldBecompleted);//应完成
                    //计算本年医生已做
                    int countyear = this.getChineseGuidance(null,qvo.getSignYear(),null,null,drId,null,null);
                    //计算本年未做
                    int countYearNotDone = countShouldBecompleted - countyear;
                    if(countYearNotDone < 0){
                        countYearNotDone = 0;
                    }
                    map.put("countyear",countyear);
                    map.put("countYearNotDone",countYearNotDone);
                    map.put("type",PerType.ETZYYJKZD.getValue());
                    map.put("drId",drId);
                    returnMap.put("zyyjkzd",map);
                }else if(vo.getSerValue().equals(PerformanceType.YQBJFW.getValue())){//孕期保健服务
                    Map<String,Object> map = new LinkedHashMap<String,Object>();
                    int countShouldBecompleted = 0;//应完成
                    List<AppServeManagePerformanceEntity>  ls = this.getManagePerformanceCompleted(manageQvo);//应完成数
                    if(ls != null && ls.size() >0){
                        for(AppServeManagePerformanceEntity p : ls){
                            countShouldBecompleted += Integer.parseInt(p.getSetNum()) * Integer.parseInt(p.getSignCount());
                        }
                    }
                    map.put("countShouldBecompleted",countShouldBecompleted);//应完成
                    //计算本年医生已做
                    int countyear = this.getPrenatalCare(null,qvo.getSignYear(),null,null,drId,null,null);
                    //计算本年未做
                    int countYearNotDone = countShouldBecompleted - countyear;
                    if(countYearNotDone < 0){
                        countYearNotDone = 0;
                    }
                    map.put("countyear",countyear);
                    map.put("countYearNotDone",countYearNotDone);
                    map.put("type",PerType.YQBJFW.getValue());
                    map.put("drId",drId);
                    returnMap.put("yqbjfw",map);
                }else if(vo.getSerValue().equals(PerformanceType.CHFS.getValue())){//产后视访
                    Map<String,Object> map = new LinkedHashMap<String,Object>();
                    int countShouldBecompleted = 0;//应完成
                    List<AppServeManagePerformanceEntity>  ls = this.getManagePerformanceCompleted(manageQvo);//应完成数
                    if(ls != null && ls.size() >0){
                        for(AppServeManagePerformanceEntity p : ls){
                            countShouldBecompleted += Integer.parseInt(p.getSetNum()) * Integer.parseInt(p.getSignCount());
                        }
                    }
                    map.put("countShouldBecompleted",countShouldBecompleted);//应完成
                    //计算本年医生已做
                    int countyear = this.getPostpartumVisit(null,qvo.getSignYear(),null,null,drId,null,null);
                    //计算本年未做
                    int countYearNotDone = countShouldBecompleted - countyear;
                    if(countYearNotDone < 0){
                        countYearNotDone = 0;
                    }
                    map.put("countyear",countyear);
                    map.put("countYearNotDone",countYearNotDone);
                    map.put("type",PerType.CHFS.getValue());
                    map.put("drId",drId);
                    returnMap.put("chfs",map);
                }else if(vo.getSerValue().equals(PerformanceType.JKTJ.getValue())){//健康体检
                    int countShouldBecompleted = 0;//应完成
                    List<AppServeManagePerformanceEntity>  ls = this.getManagePerformanceCompleted(manageQvo);//应完成数
                    if(ls != null && ls.size() >0){
                        for(AppServeManagePerformanceEntity p : ls){
                            countShouldBecompleted += Integer.parseInt(p.getSetNum()) * Integer.parseInt(p.getSignCount());
                        }
                    }
                    Map<String,Object> map = new LinkedHashMap<String,Object>();
                    map.put("countShouldBecompleted",countShouldBecompleted);//应完成
                    //计算本年医生已做
                    int countyear = this.getPhysicalExamination(null,qvo.getSignYear(),null,null,drId,null,null);
                    //计算本年未做
                    int countYearNotDone = countShouldBecompleted - countyear;
                    if(countYearNotDone < 0){
                        countYearNotDone = 0;
                    }
                    map.put("countyear",countyear);
                    map.put("countYearNotDone",countYearNotDone);
                    map.put("type",PerType.JKTJ.getValue());
                    map.put("drId",drId);
                    returnMap.put("jktj",map);
                }else if(vo.getSerValue().equals(PerformanceType.ZYTZBS.getValue())){//中医体质辨识
                    Map<String,Object> map = new LinkedHashMap<String,Object>();
                    int countShouldBecompleted = 0;//应完成
                    List<AppServeManagePerformanceEntity>  ls = this.getManagePerformanceCompleted(manageQvo);//应完成数
                    if(ls != null && ls.size() >0){
                        for(AppServeManagePerformanceEntity p : ls){
                            countShouldBecompleted += Integer.parseInt(p.getSetNum()) * Integer.parseInt(p.getSignCount());
                        }
                    }
                    map.put("countShouldBecompleted",countShouldBecompleted);//应完成
                    //计算本年医生已做
                    int countyear = this.getConstitutionIdentification(null,qvo.getSignYear(),null,null,drId,null,null);
                    //计算本年未做
                    int countYearNotDone = countShouldBecompleted - countyear;
                    if(countYearNotDone < 0){
                        countYearNotDone = 0;
                    }
                    map.put("countyear",countyear);
                    map.put("countYearNotDone",countYearNotDone);
                    map.put("type",PerType.ZYTZBS.getValue());
                    map.put("drId",drId);
                    returnMap.put("zytzbs",map);
                }else if(vo.getSerValue().equals(PerformanceType.YYZD.getValue())){//用药指导
                    Map<String,Object> map = new LinkedHashMap<String,Object>();
                    int countShouldBecompleted = 0;//应完成
                    List<AppServeManagePerformanceEntity>  ls = this.getManagePerformanceCompleted(manageQvo);//应完成数
                    if(ls != null && ls.size() >0){
                        for(AppServeManagePerformanceEntity p : ls){
                            countShouldBecompleted += Integer.parseInt(p.getSetNum()) * Integer.parseInt(p.getSignCount());
                        }
                    }
                    map.put("countShouldBecompleted",countShouldBecompleted);//应完成
                    //计算本年医生已做
                    int countyear = this.getMedicationGuidance(null,qvo.getSignYear(),null,null,drId,null,null);
                    //计算本年未做
                    int countYearNotDone = countShouldBecompleted - countyear;
                    if(countYearNotDone < 0){
                        countYearNotDone = 0;
                    }
                    map.put("countyear",countyear);
                    map.put("countYearNotDone",countYearNotDone);
                    map.put("type",PerType.YYZD.getValue());
                    map.put("drId",drId);
                    returnMap.put("yyzd",map);
                }
            }
        }
        return returnMap;
    }

    /**
     * 统计个人履约数据
     * @param qvo
     * @return
     */
    @Override
    public Map<String, Object> findAppSingPerformance(AppCommQvo qvo) throws Exception{
//        String now = ExtendDate.getYMD(ExtendDate.getCalendar(qvo.getSignYear()));
        String now = ExtendDate.getYMD(Calendar.getInstance());
        String year = now.split("-")[0];
        String month = now.split("-")[1];
        String yearMonth = year+"-"+month;
        Map<String,Object> returnMap = new HashMap<String,Object>();
        AppPatientUser user = (AppPatientUser) this.sysDao.getServiceDo().find(AppPatientUser.class,qvo.getPatientId());
        AppSignForm form = this.sysDao.getAppSignformDao().findSignFormByUserState(user.getId());
        String drId = form.getSignDrId();//医生主键
        AppDrUser drUser = (AppDrUser) this.sysDao.getServiceDo().find(AppDrUser.class,form.getSignDrId());
        AppHospDept hospDept = (AppHospDept) this.sysDao.getServiceDo().find(AppHospDept.class,form.getSignHospId());
        returnMap.put("drUserName",drUser.getDrName());
        returnMap.put("hospName",hospDept.getHospName());
        String historyDate = this.sysDao.getAppSignformDao().findMinDate(qvo.getPatientId(),drId);//第一次签约的年份
        String historyYear = historyDate.split("-")[0];
        int spaceYear = Integer.parseInt(year) - Integer.parseInt(historyYear);//年度间隔
        int monthAge = AgeUtil.getMonthAge(user.getPatientBirthday().getTime());//月龄当前
//        int monthSpaceAge = AgeUtil.getMonthAge(Calendar.getInstance().getTime(),ExtendDate.getLastYearMonth().getTime());//间隔月龄
//        int monthYearAge = monthAge + monthSpaceAge;
        List<AppLabelGroup> ls = this.sysDao.getAppLabelGroupDao().findBySignGroup(form.getId(),"3");
        if(ls != null && ls.size()>0){
            String result = null;
            for(AppLabelGroup p : ls){
                if(StringUtils.isNotBlank(result)){
                    result += ";"+p.getLabelValue();
                }else{
                    result = p.getLabelValue();
                }
            }
            AppServeManageQvo manageQvo = new AppServeManageQvo();
            manageQvo.setAreaCode(user.getPatientCity());
            manageQvo.setHospId(form.getSignHospId());
            manageQvo.setResult(result);
            List<AppServeManageEntity> lsServe = this.sysDao.getAppServeRoleDao().findManageEntity(manageQvo);
            if(lsServe != null && lsServe.size() >0){
                for(AppServeManageEntity vo : lsServe){
                    if(vo.getSerValue().equals(PerformanceType.JKJY.getValue())){//健康教育
                        Map<String,Object> map = new LinkedHashMap<String,Object>();
                        //计算本月已做
                        int countMonth = this.getHealthEducation(yearMonth,null,null,user.getPatientIdno(),drId,null,null);
                        //计算本年已做
                        int countyear = this.getHealthEducation(null,year,null,user.getPatientIdno(),drId,null,null);
                        //计算历史已做
                        int countHistory = this.getHealthEducation(null,null,historyYear,user.getPatientIdno(),drId,null,null);
                        //计算本年应做
                        int countShouldYear = Integer.parseInt(vo.getSetNum());
                        //历史应做
                        int countHistoryShouldYear = countShouldYear + (countShouldYear * spaceYear);
                        map.put("countMonth",String.valueOf(countMonth));
                        map.put("countyear",String.valueOf(countyear));
                        map.put("countHistory",String.valueOf(countHistory));
                        map.put("countShouldYear",String.valueOf(countShouldYear));
                        map.put("countHistoryShouldYear",String.valueOf(countHistoryShouldYear));
                        returnMap.put("jkjy",map);
                    }else if(vo.getSerValue().equals(PerformanceType.JKZD.getValue())){//健康指导
                        Map<String,Object> map = new LinkedHashMap<String,Object>();
                        //计算本月已做
                        int countMonth = this.getHealthGuidance(yearMonth,null,null,user.getPatientIdno(),drId,null,null);
                        //计算本年已做
                        int countyear = this.getHealthGuidance(null,year,null,user.getPatientIdno(),drId,null,null);
                        //计算历史已做
                        int countHistory = this.getHealthGuidance(null,null,historyYear,user.getPatientIdno(),drId,null,null);
                        //计算本年应做
                        int countShouldYear = Integer.parseInt(vo.getSetNum());
                        //历史应做
                        int countHistoryShouldYear = countShouldYear + (countShouldYear * spaceYear);
                        map.put("countMonth",String.valueOf(countMonth));
                        map.put("countyear",String.valueOf(countyear));
                        map.put("countHistory",String.valueOf(countHistory));
                        map.put("countShouldYear",String.valueOf(countShouldYear));
                        map.put("countHistoryShouldYear",String.valueOf(countHistoryShouldYear));
                        returnMap.put("jkzd",map);
                    }else if(vo.getSerValue().equals(PerformanceType.DQSF.getValue())){//定期随访
                        for(AppLabelGroup p : ls){
                            if(p.getLabelValue().equals(ResidentMangeType.ETLZLS.getValue())){//儿童
                                //新生儿访视
                                Map<String,Object> mapXsyfs = new LinkedHashMap<String,Object>();
                                //计算本月已做
                                int countMonthXsyfs = this.getRegularFollowup(yearMonth,null,null,"1",user.getPatientIdno(),drId,null,null);
                                //计算本年已做
                                int countyearXsyfs = this.getRegularFollowup(null,year,null,"1",user.getPatientIdno(),drId,null,null);
                                //随访本月计划数
                                int countShouldMonth = this.getSfShould(user.getId(),"2",yearMonth);
                                mapXsyfs.put("countMonth",String.valueOf(countMonthXsyfs));
                                mapXsyfs.put("countyear",String.valueOf(countyearXsyfs));
                                mapXsyfs.put("countShouldYear",String.valueOf(countyearXsyfs));
                                mapXsyfs.put("countShouldMonth",countShouldMonth);
                                if(countyearXsyfs > 0){
                                    returnMap.put("etxsfs",mapXsyfs);
                                }
                                //儿童体检
                                Map<String,Object> mapEttj = new LinkedHashMap<String,Object>();
                                //计算本月已做
                                int countMonthEttj = this.getRegularFollowup(yearMonth,null,null,"2",user.getPatientIdno(),drId,null,null);
                                //计算本年已做
                                int countyearEttj = this.getRegularFollowup(null,year,null,"2",user.getPatientIdno(),drId,null,null);
                                //计算历史已做
                                int countHistoryEttj = this.getRegularFollowup(null,null,historyYear,"2",user.getPatientIdno(),drId,null,null);
                                //计算本年应做
                                int countShouldYear = Integer.parseInt("1");
                                //历史应做
                                int countHistoryShouldYear = countShouldYear + (countShouldYear * spaceYear);

                                mapEttj.put("countMonth",String.valueOf(countMonthEttj));
                                mapEttj.put("countyear",String.valueOf(countyearEttj));
                                mapEttj.put("countHistory",String.valueOf(countHistoryEttj));
                                mapEttj.put("countShouldYear",String.valueOf(countShouldYear));
                                mapEttj.put("countHistoryShouldYear",String.valueOf(countHistoryShouldYear));
                                returnMap.put("ettj",mapEttj);
                            }else if(p.getLabelValue().equals(ResidentMangeType.GXY.getValue())){//高血压
                                Map<String,Object> mapGxy = new LinkedHashMap<String,Object>();
                                ///计算本月已做
                                int countMonthGxy = this.getRegularFollowup(yearMonth,null,null,"5",user.getPatientIdno(),drId,null,null);
                                //计算本年已做
                                int countyearGxy = this.getRegularFollowup(null,year,null,"5",user.getPatientIdno(),drId,null,null);
                                //计算历史已做
                                int countHistoryGxy = this.getRegularFollowup(null,null,historyYear,"5",user.getPatientIdno(),drId,null,null);
                                //计算本年应做
                                int countShouldYear = Integer.parseInt("4");
                                //历史应做
                                int countHistoryShouldYear = countShouldYear + (countShouldYear * spaceYear);
                                //计算本月应做
                                int countShouldMonth = this.getSfShould(user.getId(),"5",yearMonth);
                                mapGxy.put("countMonth",String.valueOf(countMonthGxy));
                                mapGxy.put("countyear",String.valueOf(countyearGxy));
                                mapGxy.put("countHistory",String.valueOf(countHistoryGxy));
                                mapGxy.put("countShouldMonth",String.valueOf(countShouldMonth));
                                if(countyearGxy > countShouldYear){
                                    mapGxy.put("countShouldYear",countyearGxy);
                                }else{
                                    mapGxy.put("countShouldYear",countShouldYear);
                                }
                                if(countHistoryGxy > countHistoryShouldYear){
                                    mapGxy.put("countHistoryShouldYear",countHistoryGxy);
                                }else{
                                    mapGxy.put("countHistoryShouldYear",countHistoryShouldYear);
                                }
                                returnMap.put("gxy",mapGxy);
                            }else if(p.getLabelValue().equals(ResidentMangeType.TNB.getValue())){//糖尿病
                                Map<String,Object> mapTnb = new LinkedHashMap<String,Object>();
                                ///计算本月已做
                                int countMonthTnb = this.getRegularFollowup(yearMonth,null,null,"6",user.getPatientIdno(),drId,null,null);
                                //计算本年已做
                                int countyearTnb = this.getRegularFollowup(null,year,null,"6",user.getPatientIdno(),drId,null,null);
                                //计算历史已做
                                int countHistoryTnb = this.getRegularFollowup(null,null,historyYear,"6",user.getPatientIdno(),drId,null,null);
                                //计算本年应做
                                int countShouldYear = Integer.parseInt("4");
                                //历史应做
                                int countHistoryShouldYear = countShouldYear + (countShouldYear * spaceYear);
                                //本月计划数
                                int countShouldMonth = this.getSfShould(user.getId(),"6",yearMonth);
                                mapTnb.put("countMonth",String.valueOf(countMonthTnb));
                                mapTnb.put("countyear",String.valueOf(countyearTnb));
                                mapTnb.put("countHistory",String.valueOf(countHistoryTnb));
                                mapTnb.put("countShouldMonth",String.valueOf(countShouldMonth));
                                if(countyearTnb > countShouldYear){
                                    mapTnb.put("countShouldYear",String.valueOf(countyearTnb));
                                }else{
                                    mapTnb.put("countShouldYear",String.valueOf(countShouldYear));
                                }
                                if(countHistoryTnb > countHistoryShouldYear){
                                    mapTnb.put("countHistoryShouldYear",String.valueOf(countHistoryTnb));
                                }else{
                                    mapTnb.put("countHistoryShouldYear",String.valueOf(countHistoryShouldYear));
                                }
                                returnMap.put("tnb",mapTnb);
                            }else if(p.getLabelValue().equals(ResidentMangeType.YZJSZY.getValue())){//严重精神障碍
                                Map<String,Object> mapJc = new LinkedHashMap<String,Object>();
                                ///计算本月已做
                                int countMonthJc = this.getRegularFollowup(yearMonth,null,null,"7",user.getPatientIdno(),drId,null,null);
                                //计算本年已做
                                int countyearJc = this.getRegularFollowup(null,year,null,"7",user.getPatientIdno(),drId,null,null);
                                //计算历史已做
                                int countHistoryJc = this.getRegularFollowup(null,null,historyYear,"7",user.getPatientIdno(),drId,null,null);
                                //计算本年应做
                                int countShouldYear = Integer.parseInt("4");
                                //历史应做
                                int countHistoryShouldYear = countShouldYear + (countShouldYear * spaceYear);
                                //本月计划数
                                int countShouldMonth = this.getSfShould(user.getId(),"7",yearMonth);
                                mapJc.put("countMonth",String.valueOf(countMonthJc));
                                mapJc.put("countyear",String.valueOf(countyearJc));
                                mapJc.put("countHistory",String.valueOf(countHistoryJc));
                                mapJc.put("countShouldMonth",String.valueOf(countShouldMonth));
                                if(countyearJc > countShouldYear){
                                    mapJc.put("countShouldYear",String.valueOf(countyearJc));
                                }else{
                                    mapJc.put("countShouldYear",String.valueOf(countShouldYear));
                                }
                                if(countHistoryJc > countHistoryShouldYear){
                                    mapJc.put("countHistoryShouldYear",String.valueOf(countHistoryJc));
                                }else{
                                    mapJc.put("countHistoryShouldYear",String.valueOf(countHistoryShouldYear));
                                }
                                returnMap.put("yzjczi",mapJc);
                            }else if(p.getLabelValue().equals(ResidentMangeType.JHB.getValue())){//结核病
                                Map<String,Object> mapJhb = new LinkedHashMap<String,Object>();
                                ///计算本月已做
                                int countMonthJhb = this.getRegularFollowup(yearMonth,null,null,"8",user.getPatientIdno(),drId,null,null);
                                //计算本年已做
                                int countyearJhb = this.getRegularFollowup(null,year,null,"8",user.getPatientIdno(),drId,null,null);
                                //计算历史已做
                                int countHistoryJhb = this.getRegularFollowup(null,null,historyYear,"8",user.getPatientIdno(),drId,null,null);
                                //计算本年应做
                                int countShouldYear = Integer.parseInt("4");
                                //历史应做
                                int countHistoryShouldYear = countShouldYear + (countShouldYear * spaceYear);
                                //本月计划数
                                int countShouldMonth = this.getSfShould(user.getId(),"8",yearMonth);
                                mapJhb.put("countMonth",String.valueOf(countMonthJhb));
                                mapJhb.put("countyear",String.valueOf(countyearJhb));
                                mapJhb.put("countHistory",String.valueOf(countHistoryJhb));
                                mapJhb.put("countShouldMonth",String.valueOf(countShouldMonth));
                                if(countyearJhb > countShouldYear){
                                    mapJhb.put("countShouldYear",String.valueOf(countyearJhb));
                                }else{
                                    mapJhb.put("countShouldYear",String.valueOf(countShouldYear));
                                }
                                if(countHistoryJhb > countHistoryShouldYear){
                                    mapJhb.put("countHistoryShouldYear",String.valueOf(countHistoryJhb));
                                }else{
                                    mapJhb.put("countHistoryShouldYear",String.valueOf(countHistoryShouldYear));
                                }
                                returnMap.put("jhb",mapJhb);
                            }
                        }
                    }else if(vo.getSerValue().equals(PerformanceType.JKZX.getValue())){//健康咨询
                        Map<String,Object> map = new LinkedHashMap<String,Object>();
                        //计算本月已做
                        int countMonth = this.getHealthConsultation(yearMonth,null,null,user.getPatientIdno(),drId,null,null);
                        //计算本年已做
                        int countyear = this.getHealthConsultation(null,year,null,user.getPatientIdno(),drId,null,null);
                        //计算历史已做
                        int countHistory = this.getHealthConsultation(null,null,historyYear,user.getPatientIdno(),drId,null,null);
                        map.put("countMonth",countMonth);
                        map.put("countyear",countyear);
                        map.put("countHistory",countHistory);
                        returnMap.put("jkzx",map);
                    }else if(vo.getSerValue().equals(PerformanceType.ZYYJKZD.getValue())){//中医药健康指导
                        if(monthAge <= 36){
                            //中医药健康指导
                            Map<String,Object> mapZyyjkzd = new LinkedHashMap<String,Object>();
                            ///计算本月已做
                            int countMonthZyyjkzd = this.getChineseGuidance(yearMonth,null,null,user.getPatientIdno(),drId,null,null);
                            //计算本年已做
                            int countyearZyyjkzd = this.getChineseGuidance(null,year,null,user.getPatientIdno(),drId,null,null);
                            //计算历史已做
                            int countHistoryZyyjkzd = this.getChineseGuidance(null,null,historyYear,user.getPatientIdno(),drId,null,null);
                            mapZyyjkzd.put("countMonth",String.valueOf(countMonthZyyjkzd));
                            mapZyyjkzd.put("countyear",String.valueOf(countyearZyyjkzd));
                            mapZyyjkzd.put("countHistory",String.valueOf(countHistoryZyyjkzd));
                            mapZyyjkzd.put("countShouldYear",String.valueOf(countyearZyyjkzd));
                            mapZyyjkzd.put("countHistoryShouldYear",String.valueOf(spaceYear+countyearZyyjkzd));
                            returnMap.put("zyyjkzd",mapZyyjkzd);
                        }
                    }else if(vo.getSerValue().equals(PerformanceType.YQBJFW.getValue())){//孕期保健服务
                        Map<String,Object> map = new LinkedHashMap<String,Object>();
                        //计算本月已做
                        int countMonth = this.getPrenatalCare(yearMonth,null,null,user.getPatientIdno(),drId,null,null);
                        //计算本年已做
                        int countyear = this.getPrenatalCare(null,year,null,user.getPatientIdno(),drId,null,null);
                        //计算历史已做
                        int countHistory = this.getPrenatalCare(null,null,historyYear,user.getPatientIdno(),drId,null,null);
                        //计算本年应做
                        int countShouldYear = Integer.parseInt("1");
                        map.put("countMonth",String.valueOf(countMonth));
                        map.put("countyear",String.valueOf(countyear));
                        map.put("countHistory",String.valueOf(countHistory));
                        map.put("countShouldYear",String.valueOf(countShouldYear));
                        map.put("countHistoryShouldYear",String.valueOf(countHistory));
                        returnMap.put("yqbjfw",map);
                    }else if(vo.getSerValue().equals(PerformanceType.CHFS.getValue())){//产后视访
                        Map<String,Object> map = new LinkedHashMap<String,Object>();
                        //计算本月已做
                        int countMonth = this.getPostpartumVisit(yearMonth,null,null,user.getPatientIdno(),drId,null,null);
                        //计算本年已做
                        int countyear = this.getPostpartumVisit(null,year,null,user.getPatientIdno(),drId,null,null);
                        map.put("countMonth",String.valueOf(countMonth));
                        map.put("countyear",String.valueOf(countyear));
                        map.put("countShouldYear",String.valueOf("1"));
                        returnMap.put("chfs",map);
                    }else if(vo.getSerValue().equals(PerformanceType.JKTJ.getValue())){//健康体检
                        Map<String,Object> map = new LinkedHashMap<String,Object>();
                        //计算本月已做
                        int countMonth = this.getPhysicalExamination(yearMonth,null,null,user.getPatientIdno(),drId,null,null);
                        //计算本年已做
                        int countyear = this.getPhysicalExamination(null,year,null,user.getPatientIdno(),drId,null,null);
                        //计算历史已做
                        int countHistory = this.getPhysicalExamination(null,null,historyYear,user.getPatientIdno(),drId,null,null);
                        //计算本年应做
                        int countShouldYear = Integer.parseInt(vo.getSetNum());
                        //历史应做
                        int countHistoryShouldYear = countShouldYear + (countHistory * spaceYear);
                        map.put("countMonth",String.valueOf(countMonth));
                        map.put("countyear",String.valueOf(countyear));
                        map.put("countHistory",String.valueOf(countHistory));
                        map.put("countShouldYear",String.valueOf(countShouldYear));
                        map.put("countHistoryShouldYear",String.valueOf(countHistoryShouldYear));
                        returnMap.put("jktj",map);
                    }else if(vo.getSerValue().equals(PerformanceType.ZYTZBS.getValue())){//中医体质辨识
                        Map<String,Object> map = new LinkedHashMap<String,Object>();
                        //计算本月已做
                        int countMonth = this.getConstitutionIdentification(yearMonth,null,null,user.getPatientIdno(),drId,null,null);
                        //计算本年已做
                        int countyear = this.getConstitutionIdentification(null,year,null,user.getPatientIdno(),drId,null,null);
                        //计算历史已做
                        int countHistory = this.getConstitutionIdentification(null,null,historyYear,user.getPatientIdno(),drId,null,null);
                        //计算本年应做
                        int countShouldYear = Integer.parseInt(vo.getSetNum());
                        //历史应做
                        int countHistoryShouldYear = countShouldYear + (countShouldYear * spaceYear);
                        map.put("countMonth",String.valueOf(countMonth));
                        map.put("countyear",String.valueOf(countyear));
                        map.put("countHistory",String.valueOf(countHistory));
                        map.put("countShouldYear",String.valueOf(countShouldYear));
                        map.put("countHistoryShouldYear",String.valueOf(countHistoryShouldYear));
                        returnMap.put("zytzbs",map);
                    }else if(vo.getSerValue().equals(PerformanceType.YYZD.getValue())){//用药指导
                        Map<String,Object> map = new LinkedHashMap<String,Object>();
                        //计算本月已做
                        int countMonth = this.getMedicationGuidance(yearMonth,null,null,user.getPatientIdno(),drId,null,null);
                        //计算本年已做
                        int countyear = this.getMedicationGuidance(null,year,null,user.getPatientIdno(),drId,null,null);
                        //计算历史已做
                        int countHistory = this.getMedicationGuidance(null,null,historyYear,user.getPatientIdno(),drId,null,null);
                        //计算本年应做
                        int countShouldYear = Integer.parseInt(vo.getSetNum());
                        //历史应做
                        int countHistoryShouldYear = countShouldYear + (countShouldYear * spaceYear);
                        map.put("countMonth",String.valueOf(countMonth));
                        map.put("countyear",String.valueOf(countyear));
                        map.put("countHistory",String.valueOf(countHistory));
                        map.put("countShouldYear",String.valueOf(countShouldYear));
                        map.put("countHistoryShouldYear",String.valueOf(countHistoryShouldYear));
                        returnMap.put("yyzd",map);
                    }
                }
            }
        }
        return returnMap;
    }

    /**
     * 健康指导(履约)
     * @param yearMonth 年月
     * @param year 年
     * @param historyYear 历史
     * @param patientIdNo 患者身份证
     * @param drId 医生主键
     * @param hospId 医院主键
     * @param areaCode 行政区划
     * @return
     */
    @Override
    public int getHealthGuidance(String yearMonth, String year,String historyYear,String patientIdNo,String drId,String hospId,String areaCode) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT\n" +
                "\tcount(1)\n" +
                "FROM\n" +
                "\tAPP_PERFORMANCE_HEALTH_GUIDANCE t \n" +
                "WHERE \n" +
                "1=1";
        if(StringUtils.isNotBlank(yearMonth)){
            map.put("PER_YEAR_MONTH",yearMonth);
            sql += " AND t.PER_YEAR_MONTH = :PER_YEAR_MONTH";
        }

        if(StringUtils.isNotBlank(year) && StringUtils.isBlank(historyYear)){
            map.put("PER_YEAR",year);
            sql += " AND t.PER_YEAR = :PER_YEAR";
        }

        if(StringUtils.isNotBlank(year) && StringUtils.isNotBlank(historyYear)){
            map.put("PER_YEAR_START",historyYear);
            map.put("PER_YEAR_END",year);
            sql += " AND t.PER_YEAR >= :PER_YEAR_START";
            sql += " AND t.PER_YEAR <= :PER_YEAR_END";
        }

        if(StringUtils.isNotBlank(patientIdNo)){
            map.put("PER_IDNO",patientIdNo);
            sql += " AND t.PER_IDNO = :PER_IDNO";
        }

        if(StringUtils.isNotBlank(drId)){
            map.put("PER_DR_ID",drId);
            sql += " AND t.PER_DR_ID = :PER_DR_ID";
        }

        if(StringUtils.isNotBlank(hospId)){
            map.put("PER_HOSP_ID",hospId);
            sql += " AND t.PER_HOSP_ID = :PER_HOSP_ID";
        }

        if(StringUtils.isNotBlank(areaCode)){
            map.put("PER_AREA_CODE",areaCode+"%");
            sql += " AND t.PER_AREA_CODE like :PER_AREA_CODE";
        }
        return sysDao.getServiceDo().findCount(sql,map);
    }

    /**
     * 健康教育统计(履约)
     * @param yearMonth 年月
     * @param year 年
     * @param historyYear 历史
     * @param patientIdNo 患者身份证
     * @param drId 医生主键
     * @param hospId 医院主键
     * @param areaCode 行政区划
     * @return
     */
    @Override
    public int getHealthEducation(String yearMonth, String year,String historyYear,String patientIdNo,String drId,String hospId,String areaCode) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT\n" +
                "\tcount(1)\n" +
                "FROM\n" +
                "\tAPP_PERFORMANCE_HEALTH_EDUCATION t \n" +
                "WHERE \n" +
                "1=1";
        if(StringUtils.isNotBlank(yearMonth)){
            map.put("PER_YEAR_MONTH",yearMonth);
            sql += " AND t.PER_YEAR_MONTH = :PER_YEAR_MONTH";
        }

        if(StringUtils.isNotBlank(year) && StringUtils.isBlank(historyYear)){
            map.put("PER_YEAR",year);
            sql += " AND t.PER_YEAR = :PER_YEAR";
        }

        if(StringUtils.isNotBlank(year) && StringUtils.isNotBlank(historyYear)){
            map.put("PER_YEAR_START",historyYear);
            map.put("PER_YEAR_END",year);
            sql += " AND t.PER_YEAR >= :PER_YEAR_START";
            sql += " AND t.PER_YEAR <= :PER_YEAR_END";
        }

        if(StringUtils.isNotBlank(patientIdNo)){
            map.put("PER_IDNO",patientIdNo);
            sql += " AND t.PER_IDNO = :PER_IDNO";
        }

        if(StringUtils.isNotBlank(drId)){
            map.put("PER_DR_ID",drId);
            sql += " AND t.PER_DR_ID = :PER_DR_ID";
        }

        if(StringUtils.isNotBlank(hospId)){
            map.put("PER_HOSP_ID",hospId);
            sql += " AND t.PER_HOSP_ID = :PER_HOSP_ID";
        }

        if(StringUtils.isNotBlank(areaCode)){
            map.put("PER_AREA_CODE",areaCode+"%");
            sql += " AND t.PER_AREA_CODE like :PER_AREA_CODE";
        }

        return sysDao.getServiceDo().findCount(sql,map);
    }

    /**
     * 健康咨询(履约)
     * @param yearMonth 年月
     * @param year 年
     * @param historyYear 历史
     * @param patientIdNo 患者身份证
     * @param drId 医生主键
     * @param hospId 医院主键
     * @param areaCode 行政区划
     * @return
     */
    public int getHealthConsultation(String yearMonth, String year,String historyYear,String patientIdNo,String drId,String hospId,String areaCode) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT\n" +
                "\tcount(1)\n" +
                "FROM\n" +
                "\tAPP_PERFORMANCE_HEALTH_CONSULTATION t \n" +
                "WHERE \n" +
                "1=1";
        if(StringUtils.isNotBlank(yearMonth)){
            map.put("PER_YEAR_MONTH",yearMonth);
            sql += " AND t.PER_YEAR_MONTH = :PER_YEAR_MONTH";
        }

        if(StringUtils.isNotBlank(year) && StringUtils.isBlank(historyYear)){
            map.put("PER_YEAR",year);
            sql += " AND t.PER_YEAR = :PER_YEAR";
        }

        if(StringUtils.isNotBlank(year) && StringUtils.isNotBlank(historyYear)){
            map.put("PER_YEAR_START",historyYear);
            map.put("PER_YEAR_END",year);
            sql += " AND t.PER_YEAR >= :PER_YEAR_START";
            sql += " AND t.PER_YEAR <= :PER_YEAR_END";
        }

        if(StringUtils.isNotBlank(patientIdNo)){
            map.put("PER_IDNO",patientIdNo);
            sql += " AND t.PER_IDNO = :PER_IDNO";
        }

        if(StringUtils.isNotBlank(drId)){
            map.put("PER_DR_ID",drId);
            sql += " AND t.PER_DR_ID = :PER_DR_ID";
        }

        if(StringUtils.isNotBlank(hospId)){
            map.put("PER_HOSP_ID",hospId);
            sql += " AND t.PER_HOSP_ID = :PER_HOSP_ID";
        }

        if(StringUtils.isNotBlank(areaCode)){
            map.put("PER_AREA_CODE",areaCode+"%");
            sql += " AND t.PER_AREA_CODE like :PER_AREA_CODE";
        }
        return sysDao.getServiceDo().findCount(sql,map);
    }

    /**
     * 健康体检(履约)
     * @param yearMonth 年月
     * @param year 年
     * @param historyYear 历史
     * @param patientIdNo 患者身份证
     * @param drId 医生主键
     * @param hospId 医院主键
     * @param areaCode 行政区划
     * @return
     */
    @Override
    public int getPhysicalExamination(String yearMonth, String year, String historyYear, String patientIdNo, String drId,String hospId,String areaCode)throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT\n" +
                "\tcount(1)\n" +
                "FROM\n" +
                "\tAPP_PERFORMANCE_PHYSICAL_EXAMINATION t \n" +
                "WHERE \n" +
                "1=1";
        if(StringUtils.isNotBlank(yearMonth)){
            map.put("PER_YEAR_MONTH",yearMonth);
            sql += " AND t.PER_YEAR_MONTH = :PER_YEAR_MONTH";
        }

        if(StringUtils.isNotBlank(year) && StringUtils.isBlank(historyYear)){
            map.put("PER_YEAR",year);
            sql += " AND t.PER_YEAR = :PER_YEAR";
        }

        if(StringUtils.isNotBlank(year) && StringUtils.isNotBlank(historyYear)){
            map.put("PER_YEAR_START",historyYear);
            map.put("PER_YEAR_END",year);
            sql += " AND t.PER_YEAR >= :PER_YEAR_START";
            sql += " AND t.PER_YEAR <= :PER_YEAR_END";
        }

        if(StringUtils.isNotBlank(patientIdNo)){
            map.put("PER_IDNO",patientIdNo);
            sql += " AND t.PER_IDNO = :PER_IDNO";
        }

        if(StringUtils.isNotBlank(drId)){
            map.put("PER_DR_ID",drId);
            sql += " AND t.PER_DR_ID = :PER_DR_ID";
        }

        if(StringUtils.isNotBlank(hospId)){
            map.put("PER_HOSP_ID",hospId);
            sql += " AND t.PER_HOSP_ID = :PER_HOSP_ID";
        }

        if(StringUtils.isNotBlank(areaCode)){
            map.put("PER_AREA_CODE",areaCode+"%");
            sql += " AND t.PER_AREA_CODE like :PER_AREA_CODE";
        }

        return sysDao.getServiceDo().findCount(sql,map);
    }

    /**
     * 中医体质辨识(履约)
     * @param yearMonth 年月
     * @param year 年
     * @param historyYear 历史
     * @param patientIdNo 患者身份证
     * @param drId 医生主键
     * @param hospId 医院主键
     * @param areaCode 行政区划
     * @return
     */
    @Override
    public int getConstitutionIdentification(String yearMonth, String year, String historyYear, String patientIdNo, String drId,String hospId,String areaCode) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT\n" +
                "\tcount(1)\n" +
                "FROM\n" +
                "\tAPP_PERFORMANCE_CONSTITUTION_IDENTIFICATION t \n" +
                "WHERE \n" +
                "1=1";
        if(StringUtils.isNotBlank(yearMonth)){
            map.put("PER_YEAR_MONTH",yearMonth);
            sql += " AND t.PER_YEAR_MONTH = :PER_YEAR_MONTH";
        }

        if(StringUtils.isNotBlank(year) && StringUtils.isBlank(historyYear)){
            map.put("PER_YEAR",year);
            sql += " AND t.PER_YEAR = :PER_YEAR";
        }

        if(StringUtils.isNotBlank(year) && StringUtils.isNotBlank(historyYear)){
            map.put("PER_YEAR_START",historyYear);
            map.put("PER_YEAR_END",year);
            sql += " AND t.PER_YEAR >= :PER_YEAR_START";
            sql += " AND t.PER_YEAR <= :PER_YEAR_END";
        }

        if(StringUtils.isNotBlank(patientIdNo)){
            map.put("PER_IDNO",patientIdNo);
            sql += " AND t.PER_IDNO = :PER_IDNO";
        }

        if(StringUtils.isNotBlank(drId)){
            map.put("PER_DR_ID",drId);
            sql += " AND t.PER_DR_ID = :PER_DR_ID";
        }

        if(StringUtils.isNotBlank(hospId)){
            map.put("PER_HOSP_ID",hospId);
            sql += " AND t.PER_HOSP_ID = :PER_HOSP_ID";
        }

        if(StringUtils.isNotBlank(areaCode)){
            map.put("PER_AREA_CODE",areaCode+"%");
            sql += " AND t.PER_AREA_CODE like :PER_AREA_CODE";
        }

        return sysDao.getServiceDo().findCount(sql,map);
    }


    /**
     * 慢病用药指导(履约)
     * @param yearMonth 年月
     * @param year 年
     * @param historyYear 历史
     * @param patientIdNo 患者身份证
     * @param drId 医生主键
     * @param hospId 医院主键
     * @param areaCode 行政区划
     * @return
     */
    @Override
    public int getMedicationGuidance(String yearMonth, String year, String historyYear, String patientIdNo, String drId,String hospId,String areaCode)throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT\n" +
                "\tcount(1)\n" +
                "FROM\n" +
                "\tAPP_PERFORMANCE_MEDICATION_GUIDANCE t \n" +
                "WHERE \n" +
                "1=1";
        if(StringUtils.isNotBlank(yearMonth)){
            map.put("PER_YEAR_MONTH",yearMonth);
            sql += " AND t.PER_YEAR_MONTH = :PER_YEAR_MONTH";
        }

        if(StringUtils.isNotBlank(year) && StringUtils.isBlank(historyYear)){
            map.put("PER_YEAR",year);
            sql += " AND t.PER_YEAR = :PER_YEAR";
        }

        if(StringUtils.isNotBlank(year) && StringUtils.isNotBlank(historyYear)){
            map.put("PER_YEAR_START",historyYear);
            map.put("PER_YEAR_END",year);
            sql += " AND t.PER_YEAR >= :PER_YEAR_START";
            sql += " AND t.PER_YEAR <= :PER_YEAR_END";
        }

        if(StringUtils.isNotBlank(patientIdNo)){
            map.put("PER_IDNO",patientIdNo);
            sql += " AND t.PER_IDNO = :PER_IDNO";
        }

        if(StringUtils.isNotBlank(drId)){
            map.put("PER_DR_ID",drId);
            sql += " AND t.PER_DR_ID = :PER_DR_ID";
        }

        if(StringUtils.isNotBlank(hospId)){
            map.put("PER_HOSP_ID",hospId);
            sql += " AND t.PER_HOSP_ID = :PER_HOSP_ID";
        }

        if(StringUtils.isNotBlank(areaCode)){
            map.put("PER_AREA_CODE",areaCode+"%");
            sql += " AND t.PER_AREA_CODE like :PER_AREA_CODE";
        }
        return sysDao.getServiceDo().findCount(sql,map);
    }
    
    /**
     * 公共服务(履约)已完成
     * 
     * @author lyy
     * @param year 年
     * @param drId 医生主键
     * @param hospId 医院主键
     * @param areaCode 行政区划
     * @return
     */
    @Override
    public int getPublicService(String year, String drId,String hospId,String areaCode) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT ");
        sb.append(" 	COUNT(*) ");
        sb.append(" FROM ");
        sb.append(" 	app_performance_table t ");
        sb.append(" WHERE ");
        sb.append(" 	1 = 1 ");

        if(StringUtils.isNotBlank(year)){
            map.put("PER_YEAR",year);
            sb.append(" AND t.PER_YEAR = :PER_YEAR ");
        }

        if(StringUtils.isNotBlank(drId)){
            map.put("PER_DR_ID",drId);
            sb.append(" AND t.PER_DR_ID = :PER_DR_ID ");
        }

        if(StringUtils.isNotBlank(hospId)){
            map.put("PER_HOSP_ID",hospId);
            sb.append(" AND t.PER_HOSP_ID = :PER_HOSP_ID ");
        }

        if(StringUtils.isNotBlank(areaCode)){
            map.put("PER_AREA_CODE",areaCode+"%");
            sb.append(" AND t.PER_AREA_CODE like :PER_AREA_CODE ");
        }
        return sysDao.getServiceDo().findCount(sb.toString(),map);
    }

    /**
     * 随访工作(履约)
     * @param yearMonth 年月
     * @param year 年
     * @param historyYear 历史
     * @param patientIdNo 患者身份证
     * @param followType 类型 新生儿访视,儿童体检,高血压,糖尿病,神经病,结核病
     * @param drId 医生主键
     * @param hospId 医院主键
     * @param areaCode 行政区划
     * @return
     */
    @Override
    public int getRegularFollowup(String yearMonth, String year, String historyYear,String followType, String patientIdNo, String drId,String hospId,String areaCode)throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = null;
        if(StringUtils.isNotBlank(followType)){
            if(followType.equals("1")){
                sql = "SELECT\n" +
                        "\tcount(1)\n" +
                        "FROM\n" +
                        "\tAPP_PERFORMANCE_REGULAR_FOLLOWUP t\n" +
                        "INNER JOIN APP_SIGN_CHILD_OR_WOMEN c ON t.PER_IDNO = c.SCOW_PATIENT_IDNO\n" +
                        "INNER JOIN APP_SIGN_FORM s ON s.ID = c.SCOW_SIGN_ID \n" +
                        "WHERE\n" +
                        "1=1";
                sql += " AND TIMESTAMPDIFF(DAY,c.SCOW_OUT_HOSPITAL_DATE,t.PER_CREATE_DATE)  <= 7\n" +
                       " AND TIMESTAMPDIFF(DAY,c.SCOW_OUT_HOSPITAL_DATE,s.SIGN_FROM_DATE)  <= 7\n";
            }else  if(followType.equals("2")){
                sql = "SELECT count(1) FROM (SELECT\n" +
                        "\t*\n" +
                        "FROM\n" +
                        "\tAPP_PERFORMANCE_REGULAR_FOLLOWUP t \n" +
                        "WHERE \n" +
                        "1=1";
            }else{
                sql = "SELECT\n" +
                        "\tcount(1)\n" +
                        "FROM\n" +
                        "\tAPP_PERFORMANCE_REGULAR_FOLLOWUP t \n" +
                        "WHERE \n" +
                        "1=1";
            }
        }

        if(StringUtils.isNotBlank(followType)){
            map.put("PER_FOLLOW_TYPE",followType);
            sql += " AND t.PER_FOLLOW_TYPE = :PER_FOLLOW_TYPE";
        }

        if(StringUtils.isNotBlank(yearMonth)){
            map.put("PER_YEAR_MONTH",yearMonth);
            sql += " AND t.PER_YEAR_MONTH = :PER_YEAR_MONTH";
        }

        if(StringUtils.isNotBlank(year) && StringUtils.isBlank(historyYear)){
            map.put("PER_YEAR",year);
            sql += " AND t.PER_YEAR = :PER_YEAR";
        }

        if(StringUtils.isNotBlank(year) && StringUtils.isNotBlank(historyYear)){
            map.put("PER_YEAR_START",historyYear);
            map.put("PER_YEAR_END",year);
            sql += " AND t.PER_YEAR >= :PER_YEAR_START";
            sql += " AND t.PER_YEAR <= :PER_YEAR_END";
        }

        if(StringUtils.isNotBlank(patientIdNo)){
            map.put("PER_IDNO",patientIdNo);
            sql += " AND t.PER_IDNO = :PER_IDNO";
        }

        if(StringUtils.isNotBlank(drId)){
            map.put("PER_DR_ID",drId);
            sql += " AND t.PER_DR_ID = :PER_DR_ID";
        }

        if(StringUtils.isNotBlank(hospId)){
            map.put("PER_HOSP_ID",hospId);
            sql += " AND t.PER_HOSP_ID = :PER_HOSP_ID";
        }

        if(StringUtils.isNotBlank(areaCode)){
            map.put("PER_AREA_CODE",areaCode+"%");
            sql += " AND t.PER_AREA_CODE like :PER_AREA_CODE";
        }
        if(followType.equals("2")){
            sql += " GROUP BY t.PER_IDNO  ) c";
        }
        return sysDao.getServiceDo().findCount(sql,map);
    }

    /**
     * 中医药健康指导（0-36月龄）履约
     * @param yearMonth 年月
     * @param year 年
     * @param historyYear 历史
     * @param patientIdNo 患者身份证
     * @param drId 医生主键
     * @param hospId 医院主键
     * @param areaCode 行政区划
     * @return
     */
    @Override
    public int getChineseGuidance(String yearMonth, String year, String historyYear, String patientIdNo, String drId, String hospId, String areaCode) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT count(1) FROM (SELECT\n" +
                "\t*\n" +
                "FROM\n" +
                "\tAPP_PERFORMANCE_CHINESE_GUIDANCE t \n" +
                "WHERE \n" +
                "1=1";
        if(StringUtils.isNotBlank(yearMonth)){
            map.put("PER_YEAR_MONTH",yearMonth);
            sql += " AND t.PER_YEAR_MONTH = :PER_YEAR_MONTH";
        }

        if(StringUtils.isNotBlank(year) && StringUtils.isBlank(historyYear)){
            map.put("PER_YEAR",year);
            sql += " AND t.PER_YEAR = :PER_YEAR";
        }

        if(StringUtils.isNotBlank(year) && StringUtils.isNotBlank(historyYear)){
            map.put("PER_YEAR_START",historyYear);
            map.put("PER_YEAR_END",year);
            sql += " AND t.PER_YEAR >= :PER_YEAR_START";
            sql += " AND t.PER_YEAR <= :PER_YEAR_END";
        }

        if(StringUtils.isNotBlank(patientIdNo)){
            map.put("PER_IDNO",patientIdNo);
            sql += " AND t.PER_IDNO = :PER_IDNO";
        }else{
            map.put("AGE","6");
            sql += " AND year(curdate())-if(length(PER_IDNO)=18,substring(PER_IDNO,7,4),if(length(PER_IDNO)=15,concat('19',substring(PER_IDNO,7,2)),null)) <= :AGE";
        }

        if(StringUtils.isNotBlank(drId)){
            map.put("PER_DR_ID",drId);
            sql += " AND t.PER_DR_ID = :PER_DR_ID";
        }

        if(StringUtils.isNotBlank(hospId)){
            map.put("PER_HOSP_ID",hospId);
            sql += " AND t.PER_HOSP_ID = :PER_HOSP_ID";
        }

        if(StringUtils.isNotBlank(areaCode)){
            map.put("PER_AREA_CODE",areaCode+"%");
            sql += " AND t.PER_AREA_CODE like :PER_AREA_CODE";
        }
        sql += " GROUP BY t.PER_IDNO  ) c";
        return sysDao.getServiceDo().findCount(sql,map);
    }

    /**
     * 产后访视(履约)
     * @param yearMonth 年月
     * @param year 年
     * @param historyYear 历史
     * @param patientIdNo 患者身份证
     * @param drId 医生主键
     * @param hospId 医院主键
     * @param areaCode 行政区划
     * @return
     */
    @Override
    public int getPostpartumVisit(String yearMonth, String year, String historyYear, String patientIdNo, String drId, String hospId, String areaCode) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT\n" +
                "\tcount(1)\n" +
                "FROM\n" +
                "\tAPP_PERFORMANCE_POSTPARTUM_VISIT t \n" +
                "INNER JOIN APP_SIGN_CHILD_OR_WOMEN c ON t.PER_IDNO = c.SCOW_PATIENT_IDNO\n" +
                "WHERE \n" +
                "1=1";
        if(StringUtils.isNotBlank(yearMonth)){
            map.put("PER_YEAR_MONTH",yearMonth);
            sql += " AND t.PER_YEAR_MONTH = :PER_YEAR_MONTH";
        }

        if(StringUtils.isNotBlank(year) && StringUtils.isBlank(historyYear)){
            map.put("PER_YEAR",year);
            sql += " AND t.PER_YEAR = :PER_YEAR";
            sql += " AND TIMESTAMPDIFF(DAY,c.SCOW_OUT_HOSPITAL_DATE,t.PER_CREATE_DATE)  <= 7\n";
        }

        if(StringUtils.isNotBlank(year) && StringUtils.isNotBlank(historyYear)){
            map.put("PER_YEAR_START",historyYear);
            map.put("PER_YEAR_END",year);
            sql += " AND t.PER_YEAR >= :PER_YEAR_START";
            sql += " AND t.PER_YEAR <= :PER_YEAR_END";
        }

        if(StringUtils.isNotBlank(patientIdNo)){
            map.put("PER_IDNO",patientIdNo);
            sql += " AND t.PER_IDNO = :PER_IDNO";
        }

        if(StringUtils.isNotBlank(drId)){
            map.put("PER_DR_ID",drId);
            sql += " AND t.PER_DR_ID = :PER_DR_ID";
        }

        if(StringUtils.isNotBlank(hospId)){
            map.put("PER_HOSP_ID",hospId);
            sql += " AND t.PER_HOSP_ID = :PER_HOSP_ID";
        }

        if(StringUtils.isNotBlank(areaCode)){
            map.put("PER_AREA_CODE",areaCode+"%");
            sql += " AND t.PER_AREA_CODE like :PER_AREA_CODE";
        }
        return sysDao.getServiceDo().findCount(sql,map);
    }

    /**
     * 孕期保健服务(履约)
     * @param yearMonth 年月
     * @param year 年
     * @param historyYear 历史
     * @param patientIdNo 患者身份证
     * @param drId 医生主键
     * @param hospId 医院主键
     * @param areaCode 行政区划
     * @return
     */
    public int getPrenatalCare(String yearMonth, String year, String historyYear, String patientIdNo, String drId, String hospId, String areaCode) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT\n" +
                "\tcount(1)\n" +
                "FROM\n" +
                "\tAPP_PERFORMANCE_PRENATAL_CARE t \n" +
                "INNER JOIN APP_SIGN_CHILD_OR_WOMEN c ON t.PER_IDNO = c.SCOW_PATIENT_IDNO\n" +
                "WHERE \n" +
                "1=1";
        if(StringUtils.isNotBlank(yearMonth)){
            map.put("PER_YEAR_MONTH",yearMonth);
            sql += " AND t.PER_YEAR_MONTH = :PER_YEAR_MONTH";
        }

        if(StringUtils.isNotBlank(year) && StringUtils.isBlank(historyYear)){
            map.put("PER_YEAR",year);
            sql += " AND t.PER_YEAR = :PER_YEAR";
            sql += " AND c.SCOW_EXPECT_13WEEK_DATE >= c.SCOW_LAST_MENSTRUAL_DATE AND c.SCOW_PREGNANCY_13WEEK_DATE <= c.SCOW_EXPECT_13WEEK_DATE ";
        }

        if(StringUtils.isNotBlank(year) && StringUtils.isNotBlank(historyYear)){
            map.put("PER_YEAR_START",historyYear);
            map.put("PER_YEAR_END",year);
            sql += " AND t.PER_YEAR >= :PER_YEAR_START ";
            sql += " AND t.PER_YEAR <= :PER_YEAR_END ";
            sql += " AND c.SCOW_EXPECT_13WEEK_DATE >= c.SCOW_LAST_MENSTRUAL_DATE AND c.SCOW_PREGNANCY_13WEEK_DATE <= c.SCOW_EXPECT_13WEEK_DATE";
        }


        if(StringUtils.isNotBlank(patientIdNo)){
            map.put("PER_IDNO",patientIdNo);
            sql += " AND t.PER_IDNO = :PER_IDNO";
        }

        if(StringUtils.isNotBlank(drId)){
            map.put("PER_DR_ID",drId);
            sql += " AND t.PER_DR_ID = :PER_DR_ID";
        }

        if(StringUtils.isNotBlank(hospId)){
            map.put("PER_HOSP_ID",hospId);
            sql += " AND t.PER_HOSP_ID = :PER_HOSP_ID";
        }

        if(StringUtils.isNotBlank(areaCode)){
            map.put("PER_AREA_CODE",areaCode+"%");
            sql += " AND t.PER_AREA_CODE like :PER_AREA_CODE";
        }
        return sysDao.getServiceDo().findCount(sql,map);
    }

    /**
     * 应完成情况履约(医生,医院,省市区)
     * @param manageQvo
     * @return
     */
    @Override
    public List<AppServeManagePerformanceEntity> getManagePerformanceCompleted(AppServeManageQvo manageQvo) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String hospId = " '' hospId,";
        String drId = " '' drId, ";
        String areaCode = " '' areaCode, ";
        String year = " '' year, ";
        if(StringUtils.isNotBlank(manageQvo.getSerHospId())){
            hospId = "'"+manageQvo.getSerHospId()+"' hospId, ";
        }
        if(StringUtils.isNotBlank(manageQvo.getSerDrId())){
            drId = "'"+manageQvo.getSerDrId()+"' drId, ";
        }
        if(StringUtils.isNotBlank(manageQvo.getSerAreaCode())){
            areaCode = "'"+manageQvo.getSerAreaCode()+"' areaCode, ";
        }
        if(StringUtils.isNotBlank(manageQvo.getSerYear())){
            year = "'"+manageQvo.getSerYear()+"' year, ";
        }
        String sql = "SELECT\n" +
                "\ts.SON_SERVE_SET_NUM setNum,\n" +
                "\tst.SER_OBJECT_TITLE obectTitle,\n" +
                "\tst.SER_OBJECT_VALUE objectValue,\n" +
                "\tst.SER_TITLE serTitle,\n" +
                "\tst.SER_VALUE serValue,\n" +
                 hospId +
                 areaCode +
                 drId +
                 year +
                "\t'' signCount\n" +
                "FROM\n" +
                "\tAPP_SERVE_ROLE c\n" +
                "INNER JOIN APP_SERVE_ROLE_SON s ON c.ID = s.SON_SERVE_ROLE_ID\n" +
                "INNER JOIN APP_SERVE_SETTING st ON st.ID = s.SON_SERVE_ID\n" +
                "WHERE\n" +
                "1=1";
        if(StringUtils.isNotBlank(manageQvo.getSerValue())){
            map.put("SER_VALUE",manageQvo.getSerValue());
            sql += " AND st.SER_VALUE = :SER_VALUE ";
        }
        if(StringUtils.isNotBlank(manageQvo.getResult())){
            String[] resultString = manageQvo.getResult().split(";");
            map.put("SER_OBJECT_VALUE",resultString);
            sql += " AND st.SER_OBJECT_VALUE in :SER_OBJECT_VALUE ";
        }
        if(StringUtils.isNotBlank(manageQvo.getHospId())){
            map.put("SERVE_ROLE_HOSP_ID",manageQvo.getHospId());
            sql += " AND c.SERVE_ROLE_HOSP_ID = :SERVE_ROLE_HOSP_ID ";
        }
        sql += " ORDER BY st.SER_OBJECT_VALUE,st.SER_VALUE";

        List<AppServeManagePerformanceEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppServeManagePerformanceEntity.class);
        if(ls != null && ls.size() >0){
            return setEntitySignCount(ls);
        }else{
            sql = "SELECT\n" +
                    "\ts.SON_SERVE_SET_NUM setNum,\n" +
                    "\tst.SER_OBJECT_TITLE obectTitle,\n" +
                    "\tst.SER_OBJECT_VALUE objectValue,\n" +
                    "\tst.SER_TITLE serTitle,\n" +
                    "\tst.SER_VALUE serValue,\n" +
                    hospId +
                    areaCode +
                    drId +
                    year +
                    "\t'' signCount\n" +
                    "FROM\n" +
                    "\tAPP_SERVE_ROLE c\n" +
                    "INNER JOIN APP_SERVE_ROLE_SON s ON c.ID = s.SON_SERVE_ROLE_ID\n" +
                    "INNER JOIN APP_SERVE_SETTING st ON st.ID = s.SON_SERVE_ID\n" +
                    "WHERE\n" +
                    "1=1";
            if(StringUtils.isNotBlank(manageQvo.getSerValue())){
                map.put("SER_VALUE",manageQvo.getSerValue());
                sql += " AND st.SER_VALUE = :SER_VALUE ";
            }
            if(StringUtils.isNotBlank(manageQvo.getResult())){
                String[] resultString = manageQvo.getResult().split(";");
                map.put("SER_OBJECT_VALUE",resultString);
                sql += " AND st.SER_OBJECT_VALUE in :SER_OBJECT_VALUE ";
            }
            if(StringUtils.isNotBlank(manageQvo.getAreaCode())){
                map.put("SERVE_ROLE_AREA_CODE",manageQvo.getAreaCode());
                sql += " AND c.SERVE_ROLE_AREA_CODE = :SERVE_ROLE_AREA_CODE ";
            }
            sql += " ORDER BY st.SER_OBJECT_VALUE,st.SER_VALUE";
            ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppServeManagePerformanceEntity.class);
            if(ls != null && ls.size()>0){
                return  setEntitySignCount(ls);
            }
            return  null;
        }
    }
    
    /**
     * 应完成情况履约实体setSignCount
     */
    private List<AppServeManagePerformanceEntity> setEntitySignCount(List<AppServeManagePerformanceEntity> ls) throws Exception{
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<String, String>();
        for (AppServeManagePerformanceEntity entity : ls) {
            String key = entity.getSerValue() + "_" + entity.getObjectValue() + "_" + entity.getHospId() + "_"
                    + entity.getAreaCode() + "_" + entity.getDrId() + "_" + entity.getYear();
            if (map.containsKey(key)) {
                entity.setSignCount(map.get(key));
            } else {
                String signCount = getSignCount(entity);
                entity.setSignCount(signCount);
                map.put(key, signCount);
            }
        }
        return ls;
    }
    
    /**
     * 由实体AppServeManagePerformanceEntity获取signCount
     */
    private String getSignCount(AppServeManagePerformanceEntity entity) throws Exception{
        if (StringUtils.isNotBlank(entity.getObjectValue()) && StringUtils.isNotBlank(entity.getYear())) {
            String start = ExtendDate.getYMD(ExtendDate.getYearFirst(Integer.parseInt(entity.getYear())));
            String end = ExtendDate.getYMD(ExtendDate.getYearLast(Integer.parseInt(entity.getYear())));
            if (entity.getSerValue().equals(PerformanceType.DQSF.getValue())
                    && entity.getObjectValue().equals(ResidentMangeType.ETLZLS.getValue())) {
                Map<String, Object> map = new HashMap<String, Object>();
                String[] signStates = new String[] { SignFormType.YQY.getValue(), SignFormType.YUQY.getValue() };
                map.put("SIGN_STATE", signStates);
                map.put("LABEL_TYPE", "3");
                map.put("STATEDATE", start + " 00:00:00");
                map.put("ENDDATE", end + " 23:59:59");
                String sqlNew = "SELECT\n" + "\tcount(1)\n" + "FROM\n" + "\tAPP_SIGN_FORM f\n"
                        + "LEFT JOIN APP_LABEL_GROUP l ON f.ID = l.LABEL_SIGN_ID\n"
                        + "LEFT JOIN APP_SIGN_CHILD_OR_WOMEN c ON f.ID = c.SCOW_SIGN_ID\n"
                        + "LEFT JOIN APP_PERFORMANCE_REGULAR_FOLLOWUP t ON  t.PER_IDNO = c.SCOW_PATIENT_IDNO\n"
                        + "WHERE\n" + "1=1";
                sqlNew += " AND f.SIGN_STATE IN (:SIGN_STATE) ";
                sqlNew += "  AND l.LABEL_TYPE = :LABEL_TYPE  ";
                sqlNew += " AND f.SIGN_FROM_DATE >= :STATEDATE AND f.SIGN_FROM_DATE <= :ENDDATE ";
                String result = null;
                if (StringUtils.isNotBlank(entity.getObjectValue())) {
                    if (entity.getObjectValue().equals(ResidentMangeType.PTRQ.getValue())) {// 普通人群
                        result = " f.MANAGE_PLAIN_COUNT ";
                    } else if (entity.getObjectValue().equals(ResidentMangeType.ETLZLS.getValue())) {// 儿童(0~6岁)
                        result = " f.MANAGE_CHILD_COUNT ";
                    } else if (entity.getObjectValue().equals(ResidentMangeType.YCF.getValue())) {// 孕产妇
                        result = " f.MANAGE_MATERNAL_COUNT ";
                    } else if (entity.getObjectValue().equals(ResidentMangeType.LNR.getValue())) {// 老年人
                        result = " f.MANAGE_OLD_PEOPLE_COUNT ";
                    } else if (entity.getObjectValue().equals(ResidentMangeType.GXY.getValue())) {// 高血压
                        result = " f.MANAGE_BLOOD_COUNT ";
                    } else if (entity.getObjectValue().equals(ResidentMangeType.TNB.getValue())) {// 糖尿病
                        result = " f.MANAGE_DIABETES_COUNT ";
                    } else if (entity.getObjectValue().equals(ResidentMangeType.YZJSZY.getValue())) {// 严重精神障碍
                        result = " f.MANAGE_PSYCHOSIS_COUNT ";
                    } else if (entity.getObjectValue().equals(ResidentMangeType.JHB.getValue())) {// 结核病
                        result = " f.MANAGE_PHTHISIS_COUNT ";
                    } else if (entity.getObjectValue().equals(ResidentMangeType.CJR.getValue())) {// 残疾人
                        result = " f.MANAGE_DISABLED_PEOPLE_COUNT ";
                    }
                }

                String sql = "SELECT sum(" + result + ") FROM APP_EXERCISE_COUNT f  WHERE 1=1";

                map.put("MANAGE_YEAR", entity.getYear());
                sql += " AND f.MANAGE_YEAR = :MANAGE_YEAR ";

                if (StringUtils.isNotBlank(entity.getObjectValue())) {
                    map.put("LABEL_VALUE", entity.getObjectValue());
                    sqlNew += " AND l.LABEL_VALUE = :LABEL_VALUE ";
                }

                if (StringUtils.isNotBlank(entity.getDrId())) {
                    map.put("SIGN_DR_ID", entity.getDrId());
                    map.put("MANAGE_DR_ID", entity.getDrId());
                    sql += " AND f.MANAGE_DR_ID = :MANAGE_DR_ID ";
                    sqlNew += " AND f.SIGN_DR_ID = :SIGN_DR_ID ";
                }

                if (StringUtils.isNotBlank(entity.getHospId())) {
                    map.put("SIGN_HOSP_ID", entity.getHospId());
                    map.put("MANAGE_HOSP_ID", entity.getHospId());
                    sql += " AND f.MANAGE_HOSP_ID = :MANAGE_HOSP_ID ";
                    sqlNew += " AND f.SIGN_HOSP_ID = :SIGN_HOSP_ID ";
                }

                if (StringUtils.isNotBlank(entity.getAreaCode())) {
                    map.put("SIGN_AREA_CODE", entity.getAreaCode() + "%");
                    sql += " AND f.MANAGE_AREA_CODE LIKE :MANAGE_AREA_CODE ";
                    sqlNew += " AND f.SIGN_AREA_CODE LIKE :SIGN_AREA_CODE ";
                }

                sqlNew += " AND TIMESTAMPDIFF(DAY,c.SCOW_OUT_HOSPITAL_DATE,t.PER_CREATE_DATE)  <= 7\n"
                        + " AND TIMESTAMPDIFF(DAY,c.SCOW_OUT_HOSPITAL_DATE,f.SIGN_FROM_DATE)  <= 7\n";
                int countNew = sysDao.getServiceDo().findCount(sqlNew, map);
                int count = sysDao.getServiceDo().findCount(sql, map);
                return String.valueOf(count) + ";;;" + String.valueOf(countNew);
            } else if (entity.getSerValue().equals(PerformanceType.ZYYJKZD.getValue())
                    && entity.getObjectValue().equals(ResidentMangeType.ETLZLS.getValue())) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("AGE", "3");
                String[] signStates = new String[] { SignFormType.YQY.getValue(), SignFormType.YUQY.getValue() };
                map.put("SIGN_STATE", signStates);
                map.put("STATEDATE", start + " 00:00:00");
                map.put("ENDDATE", end + " 23:59:59");
                map.put("LABEL_TYPE", "3");
                String sql = "SELECT\n" + "\tcount(1)\n" + "FROM\n" + "\tAPP_SIGN_FORM f\n"
                        + "LEFT JOIN APP_LABEL_GROUP l ON f.ID = l.LABEL_SIGN_ID\n" + "WHERE\n" + "1=1";
                sql += " AND f.SIGN_STATE IN (:SIGN_STATE) ";
                sql += " AND l.LABEL_TYPE = :LABEL_TYPE ";
                sql += " AND year(curdate())-if(length(SIGN_PATIENT_IDNO)=18,substring(SIGN_PATIENT_IDNO,7,4),if(length(SIGN_PATIENT_IDNO)=15,concat('19',substring(SIGN_PATIENT_IDNO,7,2)),null)) <= :AGE";
                sql += " AND f.SIGN_FROM_DATE >= :STATEDATE AND f.SIGN_FROM_DATE <= :ENDDATE ";
                if (StringUtils.isNotBlank(entity.getObjectValue())) {
                    map.put("LABEL_VALUE", entity.getObjectValue());
                    sql += " AND l.LABEL_VALUE = :LABEL_VALUE ";
                }

                if (StringUtils.isNotBlank(entity.getDrId())) {
                    map.put("SIGN_DR_ID", entity.getDrId());
                    sql += " AND f.SIGN_DR_ID = :SIGN_DR_ID ";
                }

                if (StringUtils.isNotBlank(entity.getHospId())) {
                    map.put("SIGN_HOSP_ID", entity.getHospId());
                    sql += " AND f.SIGN_HOSP_ID = :SIGN_HOSP_ID ";
                }

                if (StringUtils.isNotBlank(entity.getAreaCode())) {
                    map.put("SIGN_AREA_CODE", entity.getAreaCode() + "%");
                    sql += " AND f.SIGN_AREA_CODE LIKE :SIGN_AREA_CODE ";
                }
                int count = sysDao.getServiceDo().findCount(sql, map);
                return String.valueOf(count);
            } else {
                Map<String, Object> map = new HashMap<String, Object>();
                String result = null;
                if (StringUtils.isNotBlank(entity.getObjectValue())) {
                    if (entity.getObjectValue().equals(ResidentMangeType.PTRQ.getValue())) {// 普通人群
                        result = " f.MANAGE_PLAIN_COUNT ";
                    } else if (entity.getObjectValue().equals(ResidentMangeType.ETLZLS.getValue())) {// 儿童(0~6岁)
                        result = " f.MANAGE_CHILD_COUNT ";
                    } else if (entity.getObjectValue().equals(ResidentMangeType.YCF.getValue())) {// 孕产妇
                        result = " f.MANAGE_MATERNAL_COUNT ";
                    } else if (entity.getObjectValue().equals(ResidentMangeType.LNR.getValue())) {// 老年人
                        result = " f.MANAGE_OLD_PEOPLE_COUNT ";
                    } else if (entity.getObjectValue().equals(ResidentMangeType.GXY.getValue())) {// 高血压
                        result = " f.MANAGE_BLOOD_COUNT ";
                    } else if (entity.getObjectValue().equals(ResidentMangeType.TNB.getValue())) {// 糖尿病
                        result = " f.MANAGE_DIABETES_COUNT ";
                    } else if (entity.getObjectValue().equals(ResidentMangeType.YZJSZY.getValue())) {// 严重精神障碍
                        result = " f.MANAGE_PSYCHOSIS_COUNT ";
                    } else if (entity.getObjectValue().equals(ResidentMangeType.JHB.getValue())) {// 结核病
                        result = " f.MANAGE_PHTHISIS_COUNT ";
                    } else if (entity.getObjectValue().equals(ResidentMangeType.CJR.getValue())) {// 残疾人
                        result = " f.MANAGE_DISABLED_PEOPLE_COUNT ";
                    }
                }

                String sql = "SELECT sum(" + result + ") FROM APP_EXERCISE_COUNT f  WHERE 1=1";

                map.put("MANAGE_YEAR", entity.getYear());
                sql += " AND f.MANAGE_YEAR = :MANAGE_YEAR ";

                if (StringUtils.isNotBlank(entity.getDrId())) {
                    map.put("MANAGE_DR_ID", entity.getDrId());
                    sql += " AND f.MANAGE_DR_ID = :MANAGE_DR_ID ";
                }

                if (StringUtils.isNotBlank(entity.getHospId())) {
                    map.put("MANAGE_HOSP_ID", entity.getHospId());
                    sql += " AND f.MANAGE_HOSP_ID = :MANAGE_HOSP_ID ";
                }

                if (StringUtils.isNotBlank(entity.getAreaCode())) {
                    map.put("MANAGE_AREA_CODE", entity.getAreaCode() + "%");
                    sql += " AND f.MANAGE_AREA_CODE LIKE :MANAGE_AREA_CODE ";
                }

                int count = sysDao.getServiceDo().findCount(sql, map);
                return String.valueOf(count);
            }
        }
        return "0";
    }

    /**
     * 查询患者每月计划数（应完成数）
     * @param patientId
     * @param type
     * @param month
     * @return
     */
    public int getSfShould(String patientId,String type,String month) throws Exception{
        Map<String,Object> map = new HashMap<>();
        map.put("patientId",patientId);
        map.put("type",type);
        map.put("month", month);
        String sql = "SELECT COUNT(1) FROM APP_FOLLOW_PLAN WHERE 1=1 " +
                "AND SF_FOLLOW_PATIENTID =:patientId AND SF_FOLLOW_TYPE=:type AND DATE_FORMAT(SF_FOLLOW_DATE,'%Y-%m')=:month";
        int count = sysDao.getServiceDo().findCount(sql,map);
        return count;
    }

    /**
     * 管理端履约统计
     * 
     * @author lyy
     * @param qvo
     * @return
     */
	@Override
	public Map<String, Object> findNewAppSingPerformanceManage(AppCommQvo qvo) throws Exception{
		Map<String, Object> returnMap = new HashMap<String, Object>();

		// 设置查询条件
		AppServeSetmealQvo serveSetmealQvo = new AppServeSetmealQvo();
		serveSetmealQvo.setRoleType("9999");
		if (StringUtils.isNotBlank(qvo.getSignHospId())) {
			AppHospDept hospDept = (AppHospDept) this.sysDao.getServiceDo().find(AppHospDept.class, qvo.getSignHospId());
			serveSetmealQvo.setAreaCode(hospDept.getHospAreaCode());
			serveSetmealQvo.setHospId(qvo.getSignHospId());
		}
		if (StringUtils.isNotBlank(qvo.getSignAreaCode())) {
			serveSetmealQvo.setAreaCode(qvo.getSignAreaCode());
		}
		// 查询当前地区下，签约的所有类型和数量
		String areaCode = "";
		if (StringUtils.isNotBlank(serveSetmealQvo.getAreaCode())) {
			areaCode = AreaUtils.getAreaCode(serveSetmealQvo.getAreaCode());
		} else {
			areaCode = AreaUtils.getAreaCode(qvo.getSignAreaCode());
		}
		serveSetmealQvo.setOpenArea(areaCode);
		serveSetmealQvo.setPageSize(100000);

		// Map<服务内容, 总数量>
		Map<String, Integer> numberMap = new HashMap<String, Integer>();
		try {
			// 查询该地区或医院下所有的服务包
			List<AppServeSetmeal> ls = sysDao.getAppServeSetmealDao().findList(serveSetmealQvo);
			System.out.println("该用户的服务包数量：" + ls.size());
			// 得出服务包最后结果
			Map<String, Map<String, List<String>>> resultMap = sysDao.getAppServeSetmealDao().dealAppServeSetmeal(ls);
			// 查询除今天外的数据
			List<Map> list1 = sysDao.getAppExerciseCountNewDao().getExerciseCountByYearAndAreaCode(qvo.getSignYear(), areaCode);
			System.out.println("除今天外的数据数量：" + list1.size());
			// 查询今天的数据
			List<Map> list2 = sysDao.getAppSignformDao().findSetmealCountByAreaCodeAndYear(areaCode, qvo.getSignYear());
			System.out.println("今天的数据数量：" + list2.size());
			// 数据合并
			List<Map> countMapList = mergeCountMapListByAreaCode(list1, list2);
			System.out.println("合并最终数据数量：" + countMapList.size());

			// 计算应完成量
			for (Map countMap : countMapList) {
				// 循环取出服务包ID
				String packageId = (String) countMap.get("SIGN_PACKAGEID");
				// 循环取出服务对象
				String labelValue = (String) countMap.get("LABEL_VALUE");
				// 循环取出对应的签约人数
				String signNumber = countMap.get("SIGN_NUMBER").toString();
				if (StringUtils.isBlank(packageId) || StringUtils.isBlank(labelValue) || StringUtils.isBlank(signNumber)) {
					continue;
				}
				// 计算服务包ID对应的服务对象和频次
				Map<String, List<String>> map = sysDao.getAppServeSetmealDao().countAppServeSetmeals(packageId, resultMap);
				// 计算不同服务对象下的服务对象和频次
				List<String> serverContentAndPcNumList = sysDao.getAppServeSetmealDao().countAppServeObjects(map, labelValue);
				if (serverContentAndPcNumList != null) {
					for (String serverContentAndPcNum : serverContentAndPcNumList) {
						String[] scpNums = serverContentAndPcNum.split("&&&");
						if (!numberMap.containsKey(scpNums[0])) {
							numberMap.put(scpNums[0], Integer.valueOf(signNumber.toString()) * Integer.valueOf(scpNums[1]));
						} else {
							numberMap.put(scpNums[0], Integer.valueOf(signNumber.toString()) * Integer.valueOf(scpNums[1]) + numberMap.get(scpNums[0]));
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> jkjyMap = new LinkedHashMap<String, Object>();
		Map<String, Object> jkzdMap = new LinkedHashMap<String, Object>();
		Map<String, Object> xsyfsMap = new LinkedHashMap<String, Object>();
		Map<String, Object> ettjMap = new LinkedHashMap<String, Object>();
		Map<String, Object> gxyMap = new LinkedHashMap<String, Object>();
		Map<String, Object> tnbMap = new LinkedHashMap<String, Object>();
		Map<String, Object> yzjcziMap = new LinkedHashMap<String, Object>();
		Map<String, Object> jhbMap = new LinkedHashMap<String, Object>();
		Map<String, Object> jkzxMap = new LinkedHashMap<String, Object>();
		Map<String, Object> zyyjkzdMap = new LinkedHashMap<String, Object>();
		Map<String, Object> yqbjfwMap = new LinkedHashMap<String, Object>();
		Map<String, Object> chfsMap = new LinkedHashMap<String, Object>();
		Map<String, Object> jktjMap = new LinkedHashMap<String, Object>();
		Map<String, Object> zytzbsMap = new LinkedHashMap<String, Object>();
		Map<String, Object> yyzdMap = new LinkedHashMap<String, Object>();
		Map<String, Object> ggfwMap = new LinkedHashMap<String, Object>();

		System.out.println("========================");
		System.out.println(numberMap);
		System.out.println("========================");

		// 健康教育
		if (numberMap.containsKey(PerformanceType.JKJY.getValue())) {
			Integer numberValue = numberMap.get(PerformanceType.JKJY.getValue());
			// 应完成
			jkjyMap.put("countShouldBecompleted", numberValue);
			// 计算本年医生已做
			int countyear = 0;
			if (StringUtils.isNotBlank(qvo.getSignHospId())) {
				countyear = this.getHealthEducation(null, qvo.getSignYear(), null, null, null, qvo.getSignHospId(), null);
			} else if (StringUtils.isNotBlank(qvo.getSignAreaCode())) {
				countyear = this.getHealthEducation(null, qvo.getSignYear(), null, null, null, null, AreaUtils.getAreaCode(qvo.getSignAreaCode()));
			}
			jkjyMap.put("countyear", countyear);
			jkjyMap.put("countYearNotDone", countYearNotDone(numberValue, countyear));
			returnMap.put("jkjy", jkjyMap);
		} else {
			// 应完成
			jkjyMap.put("countShouldBecompleted", "0");
			jkjyMap.put("countyear", "0");
			jkjyMap.put("countYearNotDone", "0");
			returnMap.put("jkjy", jkjyMap);
		}

		// 健康指导
		if (numberMap.containsKey(PerformanceType.JKZD.getValue())) {
			Integer numberValue = numberMap.get(PerformanceType.JKZD.getValue());
			// 应完成
			jkzdMap.put("countShouldBecompleted", numberValue);
			// 计算本年医生已做
			int countyear = 0;
			if (StringUtils.isNotBlank(qvo.getSignHospId())) {
				countyear = this.getHealthGuidance(null, qvo.getSignYear(), null, null, null, qvo.getSignHospId(), null);
			} else if (StringUtils.isNotBlank(qvo.getSignAreaCode())) {
				countyear = this.getHealthGuidance(null, qvo.getSignYear(), null, null, null, null, AreaUtils.getAreaCode(qvo.getSignAreaCode()));
			}
			jkzdMap.put("countyear", countyear);
			jkzdMap.put("countYearNotDone", countYearNotDone(numberValue, countyear));
			returnMap.put("jkzd", jkzdMap);
		} else {
			// 应完成
			jkzdMap.put("countShouldBecompleted", "0");
			jkzdMap.put("countyear", "0");
			jkzdMap.put("countYearNotDone", "0");
			returnMap.put("jkzd", jkzdMap);
		}

		// 新生儿家庭访视
		if (numberMap.containsKey(PerformanceType.DQSF.getValue() + "_" + ResidentMangeType.ETLZLS.getValue())) {
			Integer numberValue = numberMap.get(PerformanceType.DQSF.getValue() + "_" + ResidentMangeType.ETLZLS.getValue());
			// 应完成
			xsyfsMap.put("countShouldBecompleted", numberValue);
			// 本年已做
			int countyearEtxsfs = 0;
			if (StringUtils.isNotBlank(qvo.getSignHospId())) {
				countyearEtxsfs = this.getRegularFollowup(null, qvo.getSignYear(), null, "1", null, null, qvo.getSignHospId(), null);
			} else if (StringUtils.isNotBlank(qvo.getSignAreaCode())) {
				countyearEtxsfs = this.getRegularFollowup(null, qvo.getSignYear(), null, "1", null, null, null, AreaUtils.getAreaCode(qvo.getSignAreaCode()));
			}
			xsyfsMap.put("countyear", countyearEtxsfs);
			xsyfsMap.put("countYearNotDone", countYearNotDone(numberValue, countyearEtxsfs));

			// 应完成
			ettjMap.put("countShouldBecompleted", numberValue);
			// 本年已做
			int countyearEttj = 0;
			if (StringUtils.isNotBlank(qvo.getSignHospId())) {
				countyearEttj = this.getRegularFollowup(null, qvo.getSignYear(), null, "2", null, null, qvo.getSignHospId(), null);
			} else if (StringUtils.isNotBlank(qvo.getSignAreaCode())) {
				countyearEttj = this.getRegularFollowup(null, qvo.getSignYear(), null, "2", null, null, null, AreaUtils.getAreaCode(qvo.getSignAreaCode()));
			}
			ettjMap.put("countyear", countyearEttj);
			ettjMap.put("countYearNotDone", countYearNotDone(numberValue, countyearEttj));

			returnMap.put("etxsfs", xsyfsMap);
			returnMap.put("ettj", ettjMap);
		} else {
			// 应完成
			ettjMap.put("countShouldBecompleted", "0");
			ettjMap.put("countyear", "0");
			ettjMap.put("countYearNotDone", "0");
			returnMap.put("etxsfs", ettjMap);
			returnMap.put("ettj", ettjMap);
		}

		// 高血压随访
		if (numberMap.containsKey(PerformanceType.DQSF.getValue() + "_" + ResidentMangeType.GXY.getValue())) {
			Integer numberValue = numberMap.get(PerformanceType.DQSF.getValue() + "_" + ResidentMangeType.GXY.getValue());
			// 应完成
			gxyMap.put("countShouldBecompleted", numberValue);
			// 本年已做
			int countyearGxy = 0;
			if (StringUtils.isNotBlank(qvo.getSignHospId())) {
				countyearGxy = this.getRegularFollowup(null, qvo.getSignYear(), null, "5", null, null, qvo.getSignHospId(), null);
			} else if (StringUtils.isNotBlank(qvo.getSignAreaCode())) {
				countyearGxy = this.getRegularFollowup(null, qvo.getSignYear(), null, "5", null, null, null, AreaUtils.getAreaCode(qvo.getSignAreaCode()));
			}
			gxyMap.put("countyear", countyearGxy);
			gxyMap.put("countYearNotDone", countYearNotDone(numberValue, countyearGxy));
			returnMap.put("gxy", gxyMap);
		} else {
			// 应完成
			gxyMap.put("countShouldBecompleted", "0");
			gxyMap.put("countyear", "0");
			gxyMap.put("countYearNotDone", "0");
			returnMap.put("gxy", gxyMap);
		}

		// 糖尿病随访
		if (numberMap.containsKey(PerformanceType.DQSF.getValue() + "_" + ResidentMangeType.TNB.getValue())) {
			Integer numberValue = numberMap.get(PerformanceType.DQSF.getValue() + "_" + ResidentMangeType.TNB.getValue());
			// 应完成
			tnbMap.put("countShouldBecompleted", numberValue);
			// 本年已做
			int countyearTnb = 0;
			if (StringUtils.isNotBlank(qvo.getSignHospId())) {
				countyearTnb = this.getRegularFollowup(null, qvo.getSignYear(), null, "6", null, null, qvo.getSignHospId(), null);
			} else if (StringUtils.isNotBlank(qvo.getSignAreaCode())) {
				countyearTnb = this.getRegularFollowup(null, qvo.getSignYear(), null, "6", null, null, null, AreaUtils.getAreaCode(qvo.getSignAreaCode()));
			}
			tnbMap.put("countyear", countyearTnb);
			tnbMap.put("countYearNotDone", countYearNotDone(numberValue, countyearTnb));
			returnMap.put("tnb", tnbMap);
		} else {
			// 应完成
			tnbMap.put("countShouldBecompleted", "0");
			tnbMap.put("countyear", "0");
			tnbMap.put("countYearNotDone", "0");
			returnMap.put("tnb", tnbMap);
		}

		// 严重精神障碍随访
		if (numberMap.containsKey(PerformanceType.DQSF.getValue() + "_" + ResidentMangeType.YZJSZY.getValue())) {
			Integer numberValue = numberMap.get(PerformanceType.DQSF.getValue() + "_" + ResidentMangeType.YZJSZY.getValue());
			// 应完成
			yzjcziMap.put("countShouldBecompleted", numberValue);
			// 本年已做
			int countyearJc = 0;
			if (StringUtils.isNotBlank(qvo.getSignHospId())) {
				countyearJc = this.getRegularFollowup(null, qvo.getSignYear(), null, "7", null, null, qvo.getSignHospId(), null);
			} else if (StringUtils.isNotBlank(qvo.getSignAreaCode())) {
				countyearJc = this.getRegularFollowup(null, qvo.getSignYear(), null, "7", null, null, null, AreaUtils.getAreaCode(qvo.getSignAreaCode()));
			}
			yzjcziMap.put("countyear", countyearJc);
			yzjcziMap.put("countYearNotDone", countYearNotDone(numberValue, countyearJc));
			returnMap.put("yzjczi", yzjcziMap);
		} else {
			// 应完成
			yzjcziMap.put("countShouldBecompleted", "0");
			yzjcziMap.put("countyear", "0");
			yzjcziMap.put("countYearNotDone", "0");
			returnMap.put("yzjczi", yzjcziMap);
		}

		// 结核病随访
		if (numberMap.containsKey(PerformanceType.DQSF.getValue() + "_" + ResidentMangeType.JHB.getValue())) {
			Integer numberValue = numberMap.get(PerformanceType.DQSF.getValue() + "_" + ResidentMangeType.JHB.getValue());
			// 应完成
			jhbMap.put("countShouldBecompleted", numberValue);
			// 本年已做
			int countyearJnb = 0;
			if (StringUtils.isNotBlank(qvo.getSignHospId())) {
				countyearJnb = this.getRegularFollowup(null, qvo.getSignYear(), null, "8", null, null, qvo.getSignHospId(), null);
			} else if (StringUtils.isNotBlank(qvo.getSignAreaCode())) {
				countyearJnb = this.getRegularFollowup(null, qvo.getSignYear(), null, "8", null, null, null, AreaUtils.getAreaCode(qvo.getSignAreaCode()));
			}
			jhbMap.put("countyear", countyearJnb);
			jhbMap.put("countYearNotDone", countYearNotDone(numberValue, countyearJnb));
			returnMap.put("jhb", jhbMap);
		} else {
			// 应完成
			jhbMap.put("countShouldBecompleted", "0");
			jhbMap.put("countyear", "0");
			jhbMap.put("countYearNotDone", "0");
			returnMap.put("jhb", jhbMap);
		}

		// 健康咨询
		if (numberMap.containsKey(PerformanceType.JKZX.getValue())) {
			Integer numberValue = numberMap.get(PerformanceType.JKZX.getValue());
			// 应完成
			jkzxMap.put("countShouldBecompleted", numberValue);
			// 计算本年医生已做
			int countyear = 0;
			if (StringUtils.isNotBlank(qvo.getSignHospId())) {
				countyear = this.getHealthConsultation(null, qvo.getSignYear(), null, null, null, qvo.getSignHospId(), null);
			} else if (StringUtils.isNotBlank(qvo.getSignAreaCode())) {
				countyear = this.getHealthConsultation(null, qvo.getSignYear(), null, null, null, null, AreaUtils.getAreaCode(qvo.getSignAreaCode()));
			}
			jkzxMap.put("countyear", countyear);
			jkzxMap.put("countYearNotDone", countYearNotDone(numberValue, countyear));
			returnMap.put("jkzx", jkzxMap);
		} else {
			// 应完成
			jkzxMap.put("countShouldBecompleted", "0");
			jkzxMap.put("countyear", "0");
			jkzxMap.put("countYearNotDone", "0");
			returnMap.put("jkzx", jkzxMap);
		}

		// 中医药健康指导
		if (numberMap.containsKey(PerformanceType.ZYYJKZD.getValue())) {
			Integer numberValue = numberMap.get(PerformanceType.ZYYJKZD.getValue());
			// 应完成
			zyyjkzdMap.put("countShouldBecompleted", numberValue);
			// 计算本年医生已做
			int countyear = 0;
			if (StringUtils.isNotBlank(qvo.getSignHospId())) {
				countyear = this.getChineseGuidance(null, qvo.getSignYear(), null, null, null, qvo.getSignHospId(), null);
			} else if (StringUtils.isNotBlank(qvo.getSignAreaCode())) {
				countyear = this.getChineseGuidance(null, qvo.getSignYear(), null, null, null, null, AreaUtils.getAreaCode(qvo.getSignAreaCode()));
			}
			zyyjkzdMap.put("countyear", countyear);
			zyyjkzdMap.put("countYearNotDone", countYearNotDone(numberValue, countyear));
			returnMap.put("zyyjkzd", zyyjkzdMap);
		} else {
			// 应完成
			zyyjkzdMap.put("countShouldBecompleted", "0");
			zyyjkzdMap.put("countyear", "0");
			zyyjkzdMap.put("countYearNotDone", "0");
			returnMap.put("zyyjkzd", zyyjkzdMap);
		}

		// 孕期保健服务
		if (numberMap.containsKey(PerformanceType.YQBJFW.getValue())) {
			Integer numberValue = numberMap.get(PerformanceType.YQBJFW.getValue());
			// 应完成
			yqbjfwMap.put("countShouldBecompleted", numberValue);
			// 计算本年医生已做
			int countyear = 0;
			if (StringUtils.isNotBlank(qvo.getSignHospId())) {
				countyear = this.getPrenatalCare(null, qvo.getSignYear(), null, null, null, qvo.getSignHospId(), null);
			} else if (StringUtils.isNotBlank(qvo.getSignAreaCode())) {
				countyear = this.getPrenatalCare(null, qvo.getSignYear(), null, null, null, null, AreaUtils.getAreaCode(qvo.getSignAreaCode()));
			}
			yqbjfwMap.put("countyear", countyear);
			yqbjfwMap.put("countYearNotDone", countYearNotDone(numberValue, countyear));
			returnMap.put("yqbjfw", yqbjfwMap);
		} else {
			// 应完成
			yqbjfwMap.put("countShouldBecompleted", "0");
			yqbjfwMap.put("countyear", "0");
			yqbjfwMap.put("countYearNotDone", "0");
			returnMap.put("yqbjfw", yqbjfwMap);
		}

		// 产后视访
		if (numberMap.containsKey(PerformanceType.CHFS.getValue())) {
			Integer numberValue = numberMap.get(PerformanceType.CHFS.getValue());
			// 应完成
			chfsMap.put("countShouldBecompleted", numberValue);
			// 计算本年医生已做
			int countyear = 0;
			if (StringUtils.isNotBlank(qvo.getSignHospId())) {
				countyear = this.getPostpartumVisit(null, qvo.getSignYear(), null, null, null, qvo.getSignHospId(), null);
			} else if (StringUtils.isNotBlank(qvo.getSignAreaCode())) {
				countyear = this.getPostpartumVisit(null, qvo.getSignYear(), null, null, null, null, AreaUtils.getAreaCode(qvo.getSignAreaCode()));
			}
			chfsMap.put("countyear", countyear);
			chfsMap.put("countYearNotDone", countYearNotDone(numberValue, countyear));
			returnMap.put("chfs", chfsMap);
		} else {
			// 应完成
			chfsMap.put("countShouldBecompleted", "0");
			chfsMap.put("countyear", "0");
			chfsMap.put("countYearNotDone", "0");
			returnMap.put("chfs", chfsMap);
		}

		// 健康体检
		if (numberMap.containsKey(PerformanceType.JKTJ.getValue())) {
			Integer numberValue = numberMap.get(PerformanceType.JKTJ.getValue());
			// 应完成
			jktjMap.put("countShouldBecompleted", numberValue);
			// 计算本年医生已做
			int countyear = 0;
			if (StringUtils.isNotBlank(qvo.getSignHospId())) {
				countyear = this.getPhysicalExamination(null, qvo.getSignYear(), null, null, null, qvo.getSignHospId(), null);
			} else if (StringUtils.isNotBlank(qvo.getSignAreaCode())) {
				countyear = this.getPhysicalExamination(null, qvo.getSignYear(), null, null, null, null, AreaUtils.getAreaCode(qvo.getSignAreaCode()));
			}
			jktjMap.put("countyear", countyear);
			jktjMap.put("countYearNotDone", countYearNotDone(numberValue, countyear));
			returnMap.put("jktj", jktjMap);
		} else {
			// 应完成
			jktjMap.put("countShouldBecompleted", "0");
			jktjMap.put("countyear", "0");
			jktjMap.put("countYearNotDone", "0");
			returnMap.put("jktj", jktjMap);
		}

		// 中医体质辨识
		if (numberMap.containsKey(PerformanceType.ZYTZBS.getValue())) {
			Integer numberValue = numberMap.get(PerformanceType.ZYTZBS.getValue());
			// 应完成
			zytzbsMap.put("countShouldBecompleted", numberValue);
			// 计算本年医生已做
			int countyear = 0;
			if (StringUtils.isNotBlank(qvo.getSignHospId())) {
				countyear = this.getConstitutionIdentification(null, qvo.getSignYear(), null, null, null, qvo.getSignHospId(), null);
			} else if (StringUtils.isNotBlank(qvo.getSignAreaCode())) {
				countyear = this.getConstitutionIdentification(null, qvo.getSignYear(), null, null, null, null, AreaUtils.getAreaCode(qvo.getSignAreaCode()));
			}
			zytzbsMap.put("countyear", countyear);
			zytzbsMap.put("countYearNotDone", countYearNotDone(numberValue, countyear));
			returnMap.put("zytzbs", zytzbsMap);
		} else {
			// 应完成
			zytzbsMap.put("countShouldBecompleted", "0");
			zytzbsMap.put("countyear", "0");
			zytzbsMap.put("countYearNotDone", "0");
			returnMap.put("zytzbs", zytzbsMap);
		}

		// 用药指导
		if (numberMap.containsKey(PerformanceType.YYZD.getValue())) {
			Integer numberValue = numberMap.get(PerformanceType.YYZD.getValue());
			// 应完成
			yyzdMap.put("countShouldBecompleted", numberValue);
			// 计算本年医生已做
			int countyear = 0;
			if (StringUtils.isNotBlank(qvo.getSignHospId())) {
				countyear = this.getMedicationGuidance(null, qvo.getSignYear(), null, null, null, qvo.getSignHospId(), null);
			} else if (StringUtils.isNotBlank(qvo.getSignAreaCode())) {
				countyear = this.getMedicationGuidance(null, qvo.getSignYear(), null, null, null, null, AreaUtils.getAreaCode(qvo.getSignAreaCode()));
			}
			yyzdMap.put("countyear", countyear);
			yyzdMap.put("countYearNotDone", countYearNotDone(numberValue, countyear));
			returnMap.put("yyzd", yyzdMap);
		} else {
			// 应完成
			yyzdMap.put("countShouldBecompleted", "0");
			yyzdMap.put("countyear", "0");
			yyzdMap.put("countYearNotDone", "0");
			returnMap.put("yyzd", yyzdMap);
		}

		// 用药指导
		if (numberMap.containsKey(PerformanceType.YYZD.getValue())) {
			Integer numberValue = numberMap.get(PerformanceType.YYZD.getValue());
			// 应完成
			yyzdMap.put("countShouldBecompleted", numberValue);
			// 计算本年医生已做
			int countyear = 0;
			if (StringUtils.isNotBlank(qvo.getSignHospId())) {
				countyear = this.getMedicationGuidance(null, qvo.getSignYear(), null, null, null, qvo.getSignHospId(), null);
			} else if (StringUtils.isNotBlank(qvo.getSignAreaCode())) {
				countyear = this.getMedicationGuidance(null, qvo.getSignYear(), null, null, null, null, AreaUtils.getAreaCode(qvo.getSignAreaCode()));
			}
			yyzdMap.put("countyear", countyear);
			yyzdMap.put("countYearNotDone", countYearNotDone(numberValue, countyear));
			returnMap.put("yyzd", yyzdMap);
		} else {
			// 应完成
			yyzdMap.put("countShouldBecompleted", "0");
			yyzdMap.put("countyear", "0");
			yyzdMap.put("countYearNotDone", "0");
			returnMap.put("yyzd", yyzdMap);
		}

		// 公共服务
		if (numberMap.containsKey(PerformanceType.GGFW.getValue())) {
			Integer numberValue = numberMap.get(PerformanceType.GGFW.getValue());
			// 应完成
			ggfwMap.put("countShouldBecompleted", numberValue);
			// 计算本年医生已做
			int countyear = 0;
			if (StringUtils.isNotBlank(qvo.getSignHospId())) {
				countyear = this.getPublicService(qvo.getSignYear(), null, qvo.getSignHospId(), null);
			} else if (StringUtils.isNotBlank(qvo.getSignAreaCode())) {
				countyear = this.getPublicService(qvo.getSignYear(), null, null, AreaUtils.getAreaCode(qvo.getSignAreaCode()));
			}
			ggfwMap.put("countyear", countyear);
			ggfwMap.put("countYearNotDone", countYearNotDone(numberValue, countyear));
			returnMap.put("ggfw", ggfwMap);
		} else {
			// 应完成
			ggfwMap.put("countShouldBecompleted", "0");
			ggfwMap.put("countyear", "0");
			ggfwMap.put("countYearNotDone", "0");
			returnMap.put("ggfw", ggfwMap);
		}
		System.out.println("============= 统计完成，最后结果 =============");
		System.out.println("returnMap：" + returnMap);
		System.out.println("===================================");
		return returnMap;
	}
    
    /**
	 * 数据合并（地区）
	 * 
	 * @param list1
	 *            除今天外的数据
	 * @param list2
	 *            今天的数据
	 * @return
	 */
	private List<Map> mergeCountMapListByAreaCode(List<Map> list1, List<Map> list2) throws Exception{
		// 记录今天多余的数据
		List<Map> returnMapList = new LinkedList<Map>();
		returnMapList.addAll(list1);
		returnMapList.addAll(list2);
		if (list2 == null || list2.size() == 0 || list1 == null || list1.size() == 0) {
			return returnMapList;
		}
		for (Map oldMap : list1) {
			for (Map newMap : list2) {
				if (newMap.get("SIGN_PACKAGEID") != null && oldMap.get("SIGN_PACKAGEID") != null) {
					if (newMap.get("SIGN_PACKAGEID").toString().equals(oldMap.get("SIGN_PACKAGEID").toString()) && newMap.get("LABEL_VALUE").toString().equals(oldMap.get("LABEL_VALUE").toString())
							&& newMap.get("SIGN_AREA_CODE").toString().equals(oldMap.get("SIGN_AREA_CODE").toString())) {
						Integer newSignNumber = Integer.valueOf(newMap.get("SIGN_NUMBER").toString()) + Integer.valueOf(oldMap.get("SIGN_NUMBER").toString());
						returnMapList.remove(oldMap);
						returnMapList.remove(newMap);
						oldMap.put("SIGN_NUMBER", newSignNumber.toString());
						returnMapList.add(oldMap);
					}
				}
			}
		}
		return returnMapList;
	}

	/**
	 * 数据合并（医生ID）
	 * 
	 * @param list1
	 *            除今天外的数据
	 * @param list2
	 *            今天的数据
	 * @return
	 */
	private List<Map> mergeCountMapListByDrId(List<Map> list1, List<Map> list2) throws Exception{
		// 记录今天多余的数据
		List<Map> returnMapList = new LinkedList<Map>();
		returnMapList.addAll(list1);
		returnMapList.addAll(list2);
		if (list2 == null || list2.size() == 0 || list1 == null || list1.size() == 0) {
			return returnMapList;
		}
		for (Map oldMap : list1) {
			for (Map newMap : list2) {
				if (newMap.get("SIGN_PACKAGEID") != null && oldMap.get("SIGN_PACKAGEID") != null) {
					if (newMap.get("SIGN_PACKAGEID").toString().equals(oldMap.get("SIGN_PACKAGEID").toString()) 
							&& newMap.get("LABEL_VALUE").toString().equals(oldMap.get("LABEL_VALUE").toString())
							&& newMap.get("SIGN_DR_ID").toString().equals(oldMap.get("SIGN_DR_ID").toString())) {
						Integer newSignNumber = Integer.valueOf(newMap.get("SIGN_NUMBER").toString()) + Integer.valueOf(oldMap.get("SIGN_NUMBER").toString());
						returnMapList.remove(oldMap);
						returnMapList.remove(newMap);
						oldMap.put("SIGN_NUMBER", newSignNumber.toString());
						returnMapList.add(oldMap);
					}
				}
			}
		}
		return returnMapList;
	}
	
	/**
	 * 计算未完成 应完成-已完成
	 * 
	 * @param countShouldBecompleted 应完成
	 * @param countyear 已完成
	 * @return
	 */
	private int countYearNotDone(int countShouldBecompleted, int countyear) throws Exception{
		int countYearNotDone = countShouldBecompleted - countyear;
		if (countYearNotDone < 0) {
			countYearNotDone = 0;
		}
		return countYearNotDone;
	}

    /**
     * 统计个人履约数据
     * @param qvo
     * @return
     */
    @Override
    public Map<String, Object> findNewAppSingPerformance(AppCommQvo qvo) throws Exception{
        Map<String,Object> returnMap = new HashMap<>();
        //查询个人签约单
        AppPatientUser user = (AppPatientUser)sysDao.getServiceDo().find(AppPatientUser.class,qvo.getPatientId());
        String fwrq = sysDao.getAppLabelGroupDao().findFwValue(qvo.getSignFormId());
        String jjlx = sysDao.getAppLabelGroupDao().findJjValue(qvo.getSignFormId());
        int monthAge = AgeUtil.getMonthAge(user.getPatientBirthday().getTime());//月龄当前
        List<AppLabelGroup> gl = sysDao.getServiceDo().loadByPk(AppLabelGroup.class,"labelSignId",qvo.getSignFormId());
        List<AppSignServePkEntity> pklist = new ArrayList<>();
        String pkIds = "";
        //查询服务数据
        List<AppSignServeMealEntity> listM = sysDao.getAppServeSetmealDao().findSignMeal(qvo.getSignpackageid());
        if(listM!=null && listM.size()>0){
            for(AppSignServeMealEntity lm:listM){//遍历服务包数据
                if(lm.getGroupList()!=null && lm.getGroupList().size()>0){
                    for(AppSignServeGroupEntity lg:lm.getGroupList()){//遍历服务组合数据
                        if(lg.getObEntiry()!=null){
                            AppSignServeObjectEntity vo = lg.getObEntiry();
                            if("3".equals(vo.getLabelType()) && StringUtils.isNotBlank(fwrq)){//服务人群
                                if(fwrq.indexOf(vo.getFwType())!=-1){
                                    if(lg.getPkList()!=null && lg.getPkList().size()>0){
                                        for(AppSignServePkEntity lpk:lg.getPkList()){
                                            if(StringUtils.isBlank(pkIds)){
                                                pkIds = lpk.getId();
                                                pklist.add(lpk);
                                            }else{
                                                if(pkIds.indexOf(lpk.getId())==-1){
                                                    pkIds += ";"+lpk.getId();
                                                    pklist.add(lpk);
                                                }
                                            }
                                        }
                                    }
                                }
                            }else if("4".equals(vo.getLabelType()) && StringUtils.isNotBlank(jjlx)){
                                if(jjlx.indexOf(vo.getFwType())!=-1){
                                    if(lg.getPkList()!=null && lg.getPkList().size()>0){
                                        for(AppSignServePkEntity lpk:lg.getPkList()){
                                            if(StringUtils.isBlank(pkIds)){
                                                pkIds = lpk.getId();
                                                pklist.add(lpk);
                                            }else{
                                                if(pkIds.indexOf(lpk.getId())==-1){
                                                    pkIds += ";"+lpk.getId();
                                                    pklist.add(lpk);
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
        }
        if(pklist!=null && pklist.size()>0){
            for(AppSignServePkEntity lpk:pklist){
                Calendar cal = Calendar.getInstance();
                int yearMonth = cal.get(Calendar.MONTH)+1;
                int year = cal.get(Calendar.YEAR);
                String historyDate = this.sysDao.getAppSignformDao().findMinDate(qvo.getPatientId(),qvo.getDrId());//第一次签约的年份
                String historyYear = historyDate.split("-")[0];
                Map<String,Object> mm = new HashMap<>();
                int countMonth = 0;
                int countyear = 0;
                int countHistory = 0;
                if(PerformanceType.JKJY.getValue().equals(lpk.getValue())){/*健康教育履约统计*/
                    //计算完成数
                    //计算本月已做
                    countMonth = this.getHealthEducation(String.valueOf(yearMonth),null,null,user.getPatientIdno(),qvo.getDrId(),null,null);
                    //计算本年已做
                    countyear = this.getHealthEducation(null,String.valueOf(year),null,user.getPatientIdno(),qvo.getDrId(),null,null);
                    //计算历史已做
                    countHistory = this.getHealthEducation(null,null,historyYear,user.getPatientIdno(),qvo.getDrId(),null,null);
                    mm.put("countMonth",countMonth);
                    mm.put("countyear",countyear);
                    mm.put("countHistory",countHistory);
                    returnMap.put("JKJY",mm);
                }else if(PerformanceType.JKZD.getValue().equals(lpk.getValue())){ /*健康指导履约统计*/
                    //计算本月已做
                    countMonth = this.getHealthGuidance(String.valueOf(yearMonth),null,null,user.getPatientIdno(),qvo.getDrId(),null,null);
                    //计算本年已做
                    countyear = this.getHealthGuidance(null,String.valueOf(year),null,user.getPatientIdno(),qvo.getDrId(),null,null);
                    //计算历史已做
                    countHistory = this.getHealthGuidance(null,null,historyYear,user.getPatientIdno(),qvo.getDrId(),null,null);
                    mm.put("countMonth",countMonth);
                    mm.put("countyear",countyear);
                    mm.put("countHistory",countHistory);
                    returnMap.put("JKZD",mm);
                }else if(PerformanceType.DQSF.getValue().equals(lpk.getValue())){/*定期随访*/
                    if(gl!=null && gl.size()>0){
                        for(AppLabelGroup gg:gl){
                            if(ResidentMangeType.ETLZLS.getValue().equals(gg.getLabelValue())){//新生儿
                                //新生儿访视
                                Map<String,Object> mapXsyfs = new LinkedHashMap<String,Object>();
                                //计算本月已做
                                int countMonthXsyfs = this.getRegularFollowup(String.valueOf(yearMonth),null,null,"1",user.getPatientIdno(),qvo.getDrId(),null,null);
                                //计算本年已做
                                int countyearXsyfs = this.getRegularFollowup(null,String.valueOf(year),null,"1",user.getPatientIdno(),qvo.getDrId(),null,null);
                                //随访本月计划数
//                                    int countShouldMonth = this.getSfShould(signform.getSignPatientId(),"2",String.valueOf(yearMonth));
                                mapXsyfs.put("countMonth",String.valueOf(countMonthXsyfs));
                                mapXsyfs.put("countyear",String.valueOf(countyearXsyfs));
                                if(countyearXsyfs > 0){
                                    returnMap.put("etxsfs",mapXsyfs);
                                }
                                //儿童体检
                                Map<String,Object> mapEttj = new LinkedHashMap<String,Object>();
                                //计算本月已做
                                int countMonthEttj = this.getRegularFollowup(String.valueOf(yearMonth),null,null,"2",user.getPatientIdno(),qvo.getDrId(),null,null);
                                //计算本年已做
                                int countyearEttj = this.getRegularFollowup(null,String.valueOf(year),null,"2",user.getPatientIdno(),qvo.getDrId(),null,null);
                                //计算历史已做
                                int countHistoryEttj = this.getRegularFollowup(null,null,historyYear,"2",user.getPatientIdno(),qvo.getDrId(),null,null);

                                mapEttj.put("countMonth",String.valueOf(countMonthEttj));
                                mapEttj.put("countyear",String.valueOf(countyearEttj));
                                mapEttj.put("countHistory",String.valueOf(countHistoryEttj));
                                returnMap.put("ettj",mapEttj);
                            }else if(ResidentMangeType.GXY.getValue().equals(gg.getLabelValue())){//高血压
                                Map<String,Object> mapGxy = new LinkedHashMap<String,Object>();
                                ///计算本月已做
                                int countMonthGxy = this.getRegularFollowup(String.valueOf(yearMonth),null,null,"5",user.getPatientIdno(),qvo.getDrId(),null,null);
                                //计算本年已做
                                int countyearGxy = this.getRegularFollowup(null,String.valueOf(year),null,"5",user.getPatientIdno(),qvo.getDrId(),null,null);
                                //计算历史已做
                                int countHistoryGxy = this.getRegularFollowup(null,null,historyYear,"5",user.getPatientIdno(),qvo.getDrId(),null,null);
                                mapGxy.put("countMonth",String.valueOf(countMonthGxy));
                                mapGxy.put("countyear",String.valueOf(countyearGxy));
                                mapGxy.put("countHistory",String.valueOf(countHistoryGxy));
                                returnMap.put("gxy",mapGxy);
                            }else if(ResidentMangeType.TNB.getValue().equals(gg.getLabelValue())){//糖尿病
                                Map<String,Object> mapTnb = new LinkedHashMap<String,Object>();
                                ///计算本月已做
                                int countMonthTnb = this.getRegularFollowup(String.valueOf(yearMonth),null,null,"6",user.getPatientIdno(),qvo.getDrId(),null,null);
                                //计算本年已做
                                int countyearTnb = this.getRegularFollowup(null,String.valueOf(year),null,"6",user.getPatientIdno(),qvo.getDrId(),null,null);
                                //计算历史已做
                                int countHistoryTnb = this.getRegularFollowup(null,null,historyYear,"6",user.getPatientIdno(),qvo.getDrId(),null,null);
                                //本月计划数
//                                    int countShouldMonth = this.getSfShould(signform.getSignPatientId(),"6",String.valueOf(yearMonth));
                                mapTnb.put("countMonth",String.valueOf(countMonthTnb));
                                mapTnb.put("countyear",String.valueOf(countyearTnb));
                                mapTnb.put("countHistory",String.valueOf(countHistoryTnb));
//                                    mapTnb.put("countShouldMonth",String.valueOf(countShouldMonth));
                                returnMap.put("tnb",mapTnb);
                            }else if(ResidentMangeType.YZJSZY.getValue().equals(gg.getLabelValue())){//严重精神障碍
                                Map<String,Object> mapJc = new LinkedHashMap<String,Object>();
                                ///计算本月已做
                                int countMonthJc = this.getRegularFollowup(String.valueOf(yearMonth),null,null,"7",user.getPatientIdno(),qvo.getDrId(),null,null);
                                //计算本年已做
                                int countyearJc = this.getRegularFollowup(null,String.valueOf(year),null,"7",user.getPatientIdno(),qvo.getDrId(),null,null);
                                //计算历史已做
                                int countHistoryJc = this.getRegularFollowup(null,null,historyYear,"7",user.getPatientIdno(),qvo.getDrId(),null,null);
                                //本月计划数
//                                    int countShouldMonth = this.getSfShould(signform.getId(),"7",String.valueOf(yearMonth));
                                mapJc.put("countMonth",String.valueOf(countMonthJc));
                                mapJc.put("countyear",String.valueOf(countyearJc));
                                mapJc.put("countHistory",String.valueOf(countHistoryJc));
//                                    mapJc.put("countShouldMonth",String.valueOf(countShouldMonth));
                                returnMap.put("yzjczi",mapJc);
                            }else if(ResidentMangeType.JHB.getValue().equals(gg.getLabelValue())){//结核病
                                Map<String,Object> mapJhb = new LinkedHashMap<String,Object>();
                                ///计算本月已做
                                int countMonthJhb = this.getRegularFollowup(String.valueOf(yearMonth),null,null,"8",user.getPatientIdno(),qvo.getDrId(),null,null);
                                //计算本年已做
                                int countyearJhb = this.getRegularFollowup(null,String.valueOf(year),null,"8",user.getPatientIdno(),qvo.getDrId(),null,null);
                                //计算历史已做
                                int countHistoryJhb = this.getRegularFollowup(null,null,historyYear,"8",user.getPatientIdno(),qvo.getDrId(),null,null);

                                //本月计划数
//                                    int countShouldMonth = this.getSfShould(user.getId(),"8",String.valueOf(yearMonth));
                                mapJhb.put("countMonth",String.valueOf(countMonthJhb));
                                mapJhb.put("countyear",String.valueOf(countyearJhb));
                                mapJhb.put("countHistory",String.valueOf(countHistoryJhb));
//                                    mapJhb.put("countShouldMonth",String.valueOf(countShouldMonth));
                                returnMap.put("jhb",mapJhb);
                            }
                        }
                    }

                }else if(PerformanceType.JKZX.getValue().equals(lpk.getValue())){/*健康咨询*/
                    Map<String,Object> map = new LinkedHashMap<String,Object>();
                    //计算本月已做
                    countMonth = this.getHealthConsultation(String.valueOf(yearMonth),null,null,user.getPatientIdno(),qvo.getDrId(),null,null);
                    //计算本年已做
                    countyear = this.getHealthConsultation(null,String.valueOf(year),null,user.getPatientIdno(),qvo.getDrId(),null,null);
                    //计算历史已做
                    countHistory = this.getHealthConsultation(null,null,historyYear,user.getPatientIdno(),qvo.getDrId(),null,null);
                    map.put("countMonth",countMonth);
                    map.put("countyear",countyear);
                    map.put("countHistory",countHistory);
                    returnMap.put("jkzx",map);
                }else if(PerformanceType.ZYYJKZD.getValue().equals(lpk.getValue())){//中医药健康指导
                    if(monthAge <= 36){
                        //中医药健康指导
                        Map<String,Object> mapZyyjkzd = new LinkedHashMap<String,Object>();
                        ///计算本月已做
                        int countMonthZyyjkzd = this.getChineseGuidance(String.valueOf(yearMonth),null,null,user.getPatientIdno(),qvo.getDrId(),null,null);
                        //计算本年已做
                        int countyearZyyjkzd = this.getChineseGuidance(null,String.valueOf(year),null,user.getPatientIdno(),qvo.getDrId(),null,null);
                        //计算历史已做
                        int countHistoryZyyjkzd = this.getChineseGuidance(null,null,historyYear,user.getPatientIdno(),qvo.getDrId(),null,null);
                        mapZyyjkzd.put("countMonth",String.valueOf(countMonthZyyjkzd));
                        mapZyyjkzd.put("countyear",String.valueOf(countyearZyyjkzd));
                        mapZyyjkzd.put("countHistory",String.valueOf(countHistoryZyyjkzd));
                        returnMap.put("zyyjkzd",mapZyyjkzd);
                    }
                }else if(PerformanceType.YQBJFW.getValue().equals(lpk.getValue())){//孕期保健服务
                    Map<String,Object> map = new LinkedHashMap<String,Object>();
                    //计算本月已做
                    countMonth = this.getPrenatalCare(String.valueOf(yearMonth),null,null,user.getPatientIdno(),qvo.getDrId(),null,null);
                    //计算本年已做
                    countyear = this.getPrenatalCare(null,String.valueOf(year),null,user.getPatientIdno(),qvo.getDrId(),null,null);
                    //计算历史已做
                    countHistory = this.getPrenatalCare(null,null,historyYear,user.getPatientIdno(),qvo.getDrId(),null,null);
                    //计算本年应做
//                        int countShouldYear = Integer.parseInt("1");
                    map.put("countMonth",String.valueOf(countMonth));
                    map.put("countyear",String.valueOf(countyear));
                    map.put("countHistory",String.valueOf(countHistory));
//                        map.put("countShouldYear",String.valueOf(countShouldYear));
//                        map.put("countHistoryShouldYear",String.valueOf(countHistory));
                    returnMap.put("yqbjfw",map);
                }else if(PerformanceType.CHFS.getValue().equals(lpk.getValue())){//产后视访
                    Map<String,Object> map = new LinkedHashMap<String,Object>();
                    //计算本月已做
                    countMonth = this.getPostpartumVisit(String.valueOf(yearMonth),null,null,user.getPatientIdno(),qvo.getDrId(),null,null);
                    //计算本年已做
                    countyear = this.getPostpartumVisit(null,String.valueOf(year),null,user.getPatientIdno(),qvo.getDrId(),null,null);
                    map.put("countMonth",String.valueOf(countMonth));
                    map.put("countyear",String.valueOf(countyear));
//                        map.put("countShouldYear",String.valueOf("1"));
                    returnMap.put("chfs",map);
                }else if(PerformanceType.JKTJ.getValue().equals(lpk.getValue())){//健康体检
                    Map<String,Object> map = new LinkedHashMap<String,Object>();
                    //计算本月已做
                    countMonth = this.getPhysicalExamination(String.valueOf(yearMonth),null,null,user.getPatientIdno(),qvo.getDrId(),null,null);
                    //计算本年已做
                    countyear = this.getPhysicalExamination(null,String.valueOf(year),null,user.getPatientIdno(),qvo.getDrId(),null,null);
                    //计算历史已做
                    countHistory = this.getPhysicalExamination(null,null,historyYear,user.getPatientIdno(),qvo.getDrId(),null,null);
                    map.put("countMonth",String.valueOf(countMonth));
                    map.put("countyear",String.valueOf(countyear));
                    map.put("countHistory",String.valueOf(countHistory));
                    returnMap.put("jktj",map);
                }else if(PerformanceType.ZYTZBS.getValue().equals(lpk.getValue())){//中医体质辨识
                    Map<String,Object> map = new LinkedHashMap<String,Object>();
                    //计算本月已做
                    countMonth = this.getConstitutionIdentification(String.valueOf(yearMonth),null,null,user.getPatientIdno(),qvo.getDrId(),null,null);
                    //计算本年已做
                    countyear = this.getConstitutionIdentification(null,String.valueOf(year),null,user.getPatientIdno(),qvo.getDrId(),null,null);
                    //计算历史已做
                    countHistory = this.getConstitutionIdentification(null,null,historyYear,user.getPatientIdno(),qvo.getDrId(),null,null);
                    map.put("countMonth",String.valueOf(countMonth));
                    map.put("countyear",String.valueOf(countyear));
                    map.put("countHistory",String.valueOf(countHistory));
                    returnMap.put("zytzbs",map);
                }else if(PerformanceType.YYZD.getValue().equals(lpk.getValue())){//用药指导
                    Map<String,Object> map = new LinkedHashMap<String,Object>();
                    //计算本月已做
                    countMonth = this.getMedicationGuidance(String.valueOf(yearMonth),null,null,user.getPatientIdno(),qvo.getDrId(),null,null);
                    //计算本年已做
                    countyear = this.getMedicationGuidance(null,String.valueOf(year),null,user.getPatientIdno(),qvo.getDrId(),null,null);
                    //计算历史已做
                    countHistory = this.getMedicationGuidance(null,null,historyYear,user.getPatientIdno(),qvo.getDrId(),null,null);
                    map.put("countMonth",String.valueOf(countMonth));
                    map.put("countyear",String.valueOf(countyear));
                    map.put("countHistory",String.valueOf(countHistory));
                    returnMap.put("yyzd",map);
                }else{//其他

                }
            }
        }
        return returnMap;
    }

    /**
     * 医生履约统计
     * @param qvo
     * @return
     */
    @Override
    public Map<String, Object> findNewAppSingPerformanceDr(AppCommQvo qvo) throws Exception{
		Map<String, Object> returnMap = new HashMap<String, Object>();

		// 查询当前医生服务包
		AppDrUser drUser = sysDao.getAppDrUserDao().findByUserId(qvo.getDrId());
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

		// Map<服务内容, 总数量>
		Map<String, Integer> numberMap = new HashMap<String, Integer>();
		try {
			// 查询该地区或医院下所有的服务包
			List<AppServeSetmeal> ls = sysDao.getAppServeSetmealDao().findList(serveSetmealQvo);
			if (ls == null || ls.size() == 0) {
				return returnMap;
			}
			System.out.println("该用户的服务包数量：" + ls.size());
			// 得出服务包最后结果
			Map<String, Map<String, List<String>>> resultMap = sysDao.getAppServeSetmealDao().dealAppServeSetmeal(ls);
			// List<Map> list2 = sysDao.getAppSignformDao().findSetmealCountByDrIdAndYear(qvo.getDrId(), qvo.getSignYear());
			// 查询除今天外的数据
			List<Map> list1 = sysDao.getAppExerciseCountNewDao().getExerciseCountByYearAndDrId(qvo.getSignYear(), qvo.getDrId());
			System.out.println("除今天外的数据数量：" + list1.size());
			// 查询今天的数据
			List<Map> list2 = sysDao.getAppSignformDao().findSetmealCountByDrIdAndYear(qvo.getDrId(), qvo.getSignYear());
			System.out.println("今天的数据数量：" + list2.size());
			// 数据合并
			List<Map> countMapList = mergeCountMapListByDrId(list1, list2);
			System.out.println("醫生的数据数量：" + countMapList.size());

			// 计算应完成量
			for (Map countMap : countMapList) {
				// 循环取出服务包ID
				String packageId = (String) countMap.get("SIGN_PACKAGEID");
				// 循环取出服务对象
				String labelValue = (String) countMap.get("LABEL_VALUE");
				// 循环取出对应的签约人数
				String signNumber = countMap.get("SIGN_NUMBER").toString();
				if (StringUtils.isBlank(packageId) || StringUtils.isBlank(labelValue) || StringUtils.isBlank(signNumber)) {
					continue;
				}
				// 计算服务包ID对应的服务对象和频次
				Map<String, List<String>> map = sysDao.getAppServeSetmealDao().countAppServeSetmeals(packageId, resultMap);
				// 计算不同服务对象下的统计内容和频次
				List<String> serverContentAndPcNumList = sysDao.getAppServeSetmealDao().countAppServeObjects(map, labelValue);
				if (serverContentAndPcNumList != null) {
					for (String serverContentAndPcNum : serverContentAndPcNumList) {
						String[] scpNums = serverContentAndPcNum.split("&&&");
						if (!numberMap.containsKey(scpNums[0])) {
							numberMap.put(scpNums[0], Integer.valueOf(signNumber.toString()) * Integer.valueOf(scpNums[1]));
						} else {
							numberMap.put(scpNums[0], Integer.valueOf(signNumber.toString()) * Integer.valueOf(scpNums[1]) + numberMap.get(scpNums[0]));
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> jkjyMap = new LinkedHashMap<String, Object>();
		Map<String, Object> jkzdMap = new LinkedHashMap<String, Object>();
		Map<String, Object> xsyfsMap = new LinkedHashMap<String, Object>();
		Map<String, Object> ettjMap = new LinkedHashMap<String, Object>();
		Map<String, Object> gxyMap = new LinkedHashMap<String, Object>();
		Map<String, Object> tnbMap = new LinkedHashMap<String, Object>();
		Map<String, Object> yzjcziMap = new LinkedHashMap<String, Object>();
		Map<String, Object> jhbMap = new LinkedHashMap<String, Object>();
		Map<String, Object> jkzxMap = new LinkedHashMap<String, Object>();
		Map<String, Object> zyyjkzdMap = new LinkedHashMap<String, Object>();
		Map<String, Object> yqbjfwMap = new LinkedHashMap<String, Object>();
		Map<String, Object> chfsMap = new LinkedHashMap<String, Object>();
		Map<String, Object> jktjMap = new LinkedHashMap<String, Object>();
		Map<String, Object> zytzbsMap = new LinkedHashMap<String, Object>();
		Map<String, Object> yyzdMap = new LinkedHashMap<String, Object>();
		Map<String, Object> ggfwMap = new LinkedHashMap<String, Object>();

		System.out.println("========================");
		System.out.println(numberMap);
		System.out.println("========================");

		// 健康教育
		if (numberMap.containsKey(PerformanceType.JKJY.getValue())) {
			Integer numberValue = numberMap.get(PerformanceType.JKJY.getValue());
			// 应完成
			jkjyMap.put("countShouldBecompleted", numberValue);
			// 计算本年医生已做
			int countyear = this.getHealthEducation(null, qvo.getSignYear(), null, null, qvo.getDrId(), null, null);
			jkjyMap.put("countyear", countyear);
			jkjyMap.put("countYearNotDone", countYearNotDone(numberValue, countyear));
			returnMap.put("jkjy", jkjyMap);
		} else {
			// 应完成
			jkjyMap.put("countShouldBecompleted", "0");
			jkjyMap.put("countyear", "0");
			jkjyMap.put("countYearNotDone", "0");
			returnMap.put("jkjy", jkjyMap);
		}

		// 健康指导
		if (numberMap.containsKey(PerformanceType.JKZD.getValue())) {
			Integer numberValue = numberMap.get(PerformanceType.JKZD.getValue());
			// 应完成
			jkzdMap.put("countShouldBecompleted", numberValue);
			// 计算本年医生已做
			int countyear = this.getHealthGuidance(null, qvo.getSignYear(), null, null, qvo.getSignHospId(), null, null);
			jkzdMap.put("countyear", countyear);
			jkzdMap.put("countYearNotDone", countYearNotDone(numberValue, countyear));
			returnMap.put("jkzd", jkzdMap);
		} else {
			// 应完成
			jkzdMap.put("countShouldBecompleted", "0");
			jkzdMap.put("countyear", "0");
			jkzdMap.put("countYearNotDone", "0");
			returnMap.put("jkzd", jkzdMap);
		}

		// 新生儿家庭访视
		if (numberMap.containsKey(PerformanceType.DQSF.getValue() + "_" + ResidentMangeType.ETLZLS.getValue())) {
			Integer numberValue = numberMap.get(PerformanceType.DQSF.getValue() + "_" + ResidentMangeType.ETLZLS.getValue());
			// 应完成
			xsyfsMap.put("countShouldBecompleted", numberValue);
			// 本年已做
			int countyearEtxsfs = this.getRegularFollowup(null, qvo.getSignYear(), null, "1", null, qvo.getDrId(), null, null);
			xsyfsMap.put("countyear", countyearEtxsfs);
			xsyfsMap.put("countYearNotDone", countYearNotDone(numberValue, countyearEtxsfs));

			// 应完成
			ettjMap.put("countShouldBecompleted", numberValue);
			// 本年已做
			int countyearEttj = this.getRegularFollowup(null, qvo.getSignYear(), null, "2", null, qvo.getDrId(), null, null);
			;
			ettjMap.put("countyear", countyearEttj);
			ettjMap.put("countYearNotDone", countYearNotDone(numberValue, countyearEttj));

			returnMap.put("etxsfs", xsyfsMap);
			returnMap.put("ettj", ettjMap);
		} else {
			// 应完成
			ettjMap.put("countShouldBecompleted", "0");
			ettjMap.put("countyear", "0");
			ettjMap.put("countYearNotDone", "0");
			returnMap.put("etxsfs", ettjMap);
			returnMap.put("ettj", ettjMap);
		}

		// 高血压随访
		if (numberMap.containsKey(PerformanceType.DQSF.getValue() + "_" + ResidentMangeType.GXY.getValue())) {
			Integer numberValue = numberMap.get(PerformanceType.DQSF.getValue() + "_" + ResidentMangeType.GXY.getValue());
			// 应完成
			gxyMap.put("countShouldBecompleted", numberValue);
			// 本年已做
			int countyearGxy = this.getRegularFollowup(null, qvo.getSignYear(), null, "5", null, qvo.getDrId(), null, null);
			gxyMap.put("countyear", countyearGxy);
			gxyMap.put("countYearNotDone", countYearNotDone(numberValue, countyearGxy));
			returnMap.put("gxy", gxyMap);
		} else {
			// 应完成
			gxyMap.put("countShouldBecompleted", "0");
			gxyMap.put("countyear", "0");
			gxyMap.put("countYearNotDone", "0");
			returnMap.put("gxy", gxyMap);
		}

		// 糖尿病随访
		if (numberMap.containsKey(PerformanceType.DQSF.getValue() + "_" + ResidentMangeType.TNB.getValue())) {
			Integer numberValue = numberMap.get(PerformanceType.DQSF.getValue() + "_" + ResidentMangeType.TNB.getValue());
			// 应完成
			tnbMap.put("countShouldBecompleted", numberValue);
			// 本年已做
			int countyearTnb = this.getRegularFollowup(null, qvo.getSignYear(), null, "6", null, qvo.getDrId(), null, null);
			tnbMap.put("countyear", countyearTnb);
			tnbMap.put("countYearNotDone", countYearNotDone(numberValue, countyearTnb));
			returnMap.put("tnb", tnbMap);
		} else {
			// 应完成
			tnbMap.put("countShouldBecompleted", "0");
			tnbMap.put("countyear", "0");
			tnbMap.put("countYearNotDone", "0");
			returnMap.put("tnb", tnbMap);
		}

		// 严重精神障碍随访
		if (numberMap.containsKey(PerformanceType.DQSF.getValue() + "_" + ResidentMangeType.YZJSZY.getValue())) {
			Integer numberValue = numberMap.get(PerformanceType.DQSF.getValue() + "_" + ResidentMangeType.YZJSZY.getValue());
			// 应完成
			yzjcziMap.put("countShouldBecompleted", numberValue);
			// 本年已做
			int countyearJc = this.getRegularFollowup(null, qvo.getSignYear(), null, "7", null, qvo.getDrId(), null, null);
			yzjcziMap.put("countyear", countyearJc);
			yzjcziMap.put("countYearNotDone", countYearNotDone(numberValue, countyearJc));
			returnMap.put("yzjczi", yzjcziMap);
		} else {
			// 应完成
			yzjcziMap.put("countShouldBecompleted", "0");
			yzjcziMap.put("countyear", "0");
			yzjcziMap.put("countYearNotDone", "0");
			returnMap.put("yzjczi", yzjcziMap);
		}

		// 结核病随访
		if (numberMap.containsKey(PerformanceType.DQSF.getValue() + "_" + ResidentMangeType.JHB.getValue())) {
			Integer numberValue = numberMap.get(PerformanceType.DQSF.getValue() + "_" + ResidentMangeType.JHB.getValue());
			// 应完成
			jhbMap.put("countShouldBecompleted", numberValue);
			// 本年已做
			int countyearJnb = this.getRegularFollowup(null, qvo.getSignYear(), null, "8", null, qvo.getDrId(), null, null);
			jhbMap.put("countyear", countyearJnb);
			jhbMap.put("countYearNotDone", countYearNotDone(numberValue, countyearJnb));
			returnMap.put("jhb", jhbMap);
		} else {
			// 应完成
			jhbMap.put("countShouldBecompleted", "0");
			jhbMap.put("countyear", "0");
			jhbMap.put("countYearNotDone", "0");
			returnMap.put("jhb", jhbMap);
		}

		// 健康咨询
		if (numberMap.containsKey(PerformanceType.JKZX.getValue())) {
			Integer numberValue = numberMap.get(PerformanceType.JKZX.getValue());
			// 应完成
			jkzxMap.put("countShouldBecompleted", numberValue);
			// 计算本年医生已做
			int countyear = this.getHealthConsultation(null, qvo.getSignYear(), null, null, qvo.getDrId(), null, null);
			jkzxMap.put("countyear", countyear);
			jkzxMap.put("countYearNotDone", countYearNotDone(numberValue, countyear));
			returnMap.put("jkzx", jkzxMap);
		} else {
			// 应完成
			jkzxMap.put("countShouldBecompleted", "0");
			jkzxMap.put("countyear", "0");
			jkzxMap.put("countYearNotDone", "0");
			returnMap.put("jkzx", jkzxMap);
		}

		// 中医药健康指导
		if (numberMap.containsKey(PerformanceType.ZYYJKZD.getValue())) {
			Integer numberValue = numberMap.get(PerformanceType.ZYYJKZD.getValue());
			// 应完成
			zyyjkzdMap.put("countShouldBecompleted", numberValue);
			// 计算本年医生已做
			int countyear = this.getChineseGuidance(null, qvo.getSignYear(), null, null, qvo.getDrId(), null, null);
			zyyjkzdMap.put("countyear", countyear);
			zyyjkzdMap.put("countYearNotDone", countYearNotDone(numberValue, countyear));
			returnMap.put("zyyjkzd", zyyjkzdMap);
		} else {
			// 应完成
			zyyjkzdMap.put("countShouldBecompleted", "0");
			zyyjkzdMap.put("countyear", "0");
			zyyjkzdMap.put("countYearNotDone", "0");
			returnMap.put("zyyjkzd", zyyjkzdMap);
		}

		// 孕期保健服务
		if (numberMap.containsKey(PerformanceType.YQBJFW.getValue())) {
			Integer numberValue = numberMap.get(PerformanceType.YQBJFW.getValue());
			// 应完成
			yqbjfwMap.put("countShouldBecompleted", numberValue);
			// 计算本年医生已做
			int countyear = this.getPrenatalCare(null, qvo.getSignYear(), null, null, qvo.getDrId(), null, null);
			yqbjfwMap.put("countyear", countyear);
			yqbjfwMap.put("countYearNotDone", countYearNotDone(numberValue, countyear));
			returnMap.put("yqbjfw", yqbjfwMap);
		} else {
			// 应完成
			yqbjfwMap.put("countShouldBecompleted", "0");
			yqbjfwMap.put("countyear", "0");
			yqbjfwMap.put("countYearNotDone", "0");
			returnMap.put("yqbjfw", yqbjfwMap);
		}

		// 产后视访
		if (numberMap.containsKey(PerformanceType.CHFS.getValue())) {
			Integer numberValue = numberMap.get(PerformanceType.CHFS.getValue());
			// 应完成
			chfsMap.put("countShouldBecompleted", numberValue);
			// 计算本年医生已做
			int countyear = this.getPostpartumVisit(null, qvo.getSignYear(), null, null, qvo.getDrId(), null, null);
			chfsMap.put("countyear", countyear);
			chfsMap.put("countYearNotDone", countYearNotDone(numberValue, countyear));
			returnMap.put("chfs", chfsMap);
		} else {
			// 应完成
			chfsMap.put("countShouldBecompleted", "0");
			chfsMap.put("countyear", "0");
			chfsMap.put("countYearNotDone", "0");
			returnMap.put("chfs", chfsMap);
		}

		// 健康体检
		if (numberMap.containsKey(PerformanceType.JKTJ.getValue())) {
			Integer numberValue = numberMap.get(PerformanceType.JKTJ.getValue());
			// 应完成
			jktjMap.put("countShouldBecompleted", numberValue);
			// 计算本年医生已做
			int countyear = this.getPhysicalExamination(null, qvo.getSignYear(), null, null, qvo.getDrId(), null, null);
			jktjMap.put("countyear", countyear);
			jktjMap.put("countYearNotDone", countYearNotDone(numberValue, countyear));
			returnMap.put("jktj", jktjMap);
		} else {
			// 应完成
			jktjMap.put("countShouldBecompleted", "0");
			jktjMap.put("countyear", "0");
			jktjMap.put("countYearNotDone", "0");
			returnMap.put("jktj", jktjMap);
		}

		// 中医体质辨识
		if (numberMap.containsKey(PerformanceType.ZYTZBS.getValue())) {
			Integer numberValue = numberMap.get(PerformanceType.ZYTZBS.getValue());
			// 应完成
			zytzbsMap.put("countShouldBecompleted", numberValue);
			// 计算本年医生已做
			int countyear = this.getConstitutionIdentification(null, qvo.getSignYear(), null, null, qvo.getDrId(), null, null);
			zytzbsMap.put("countyear", countyear);
			zytzbsMap.put("countYearNotDone", countYearNotDone(numberValue, countyear));
			returnMap.put("zytzbs", zytzbsMap);
		} else {
			// 应完成
			zytzbsMap.put("countShouldBecompleted", "0");
			zytzbsMap.put("countyear", "0");
			zytzbsMap.put("countYearNotDone", "0");
			returnMap.put("zytzbs", zytzbsMap);
		}

		// 用药指导
		if (numberMap.containsKey(PerformanceType.YYZD.getValue())) {
			Integer numberValue = numberMap.get(PerformanceType.YYZD.getValue());
			// 应完成
			yyzdMap.put("countShouldBecompleted", numberValue);
			// 计算本年医生已做
			int countyear = this.getMedicationGuidance(null, qvo.getSignYear(), null, null, qvo.getDrId(), null, null);
			yyzdMap.put("countyear", countyear);
			yyzdMap.put("countYearNotDone", countYearNotDone(numberValue, countyear));
			returnMap.put("yyzd", yyzdMap);
		} else {
			// 应完成
			yyzdMap.put("countShouldBecompleted", "0");
			yyzdMap.put("countyear", "0");
			yyzdMap.put("countYearNotDone", "0");
			returnMap.put("yyzd", yyzdMap);
		}

		// 用药指导
		if (numberMap.containsKey(PerformanceType.YYZD.getValue())) {
			Integer numberValue = numberMap.get(PerformanceType.YYZD.getValue());
			// 应完成
			yyzdMap.put("countShouldBecompleted", numberValue);
			// 计算本年医生已做
			int countyear = this.getMedicationGuidance(null, qvo.getSignYear(), null, null, qvo.getDrId(), null, null);
			yyzdMap.put("countyear", countyear);
			yyzdMap.put("countYearNotDone", countYearNotDone(numberValue, countyear));
			returnMap.put("yyzd", yyzdMap);
		} else {
			// 应完成
			yyzdMap.put("countShouldBecompleted", "0");
			yyzdMap.put("countyear", "0");
			yyzdMap.put("countYearNotDone", "0");
			returnMap.put("yyzd", yyzdMap);
		}

		// 公共服务
		if (numberMap.containsKey(PerformanceType.GGFW.getValue())) {
			Integer numberValue = numberMap.get(PerformanceType.GGFW.getValue());
			// 应完成
			ggfwMap.put("countShouldBecompleted", numberValue);
			// 计算本年医生已做
			int countyear = this.getPublicService(qvo.getSignYear(), qvo.getDrId(), null, null);
			ggfwMap.put("countyear", countyear);
			ggfwMap.put("countYearNotDone", countYearNotDone(numberValue, countyear));
			returnMap.put("ggfw", ggfwMap);
		} else {
			// 应完成
			ggfwMap.put("countShouldBecompleted", "0");
			ggfwMap.put("countyear", "0");
			ggfwMap.put("countYearNotDone", "0");
			returnMap.put("ggfw", ggfwMap);
		}
		System.out.println("============= 统计完成，最后结果 =============");
		System.out.println("returnMap：" + returnMap);
		System.out.println("===================================");
        return returnMap;
    }

    /**
     * 添加履约数据（新）
     * @param vo
     */
    public void addPerformance(PerformanceDataQvo vo) throws Exception{
        if(StringUtils.isBlank(vo.getServeDate())){
            vo.setServeDate(ExtendDate.getYMD(Calendar.getInstance()));
        }
        String nowString = vo.getServeDate();
        Calendar now = ExtendDate.getCalendar(vo.getServeDate());

        String year = nowString.split("-")[0];
        String month = nowString.split("-")[1];
        String yearMonth = year + "-" + month;

        AppPerformanceTable table = new AppPerformanceTable();
        table.setPerAreaCode(vo.getHospAreaCode());
        table.setPerDrId(vo.getDrId());
        table.setPerHospId(vo.getHospId());
        table.setPerPatientIdNo(vo.getPerIdno());
        table.setPerPatientName(vo.getPerName());
        table.setPerSergValue(vo.getSergValue());
        table.setPerSermContent(vo.getSermContent());
        table.setPerSermValue(vo.getSermValue());
        table.setPerSerpkValue(vo.getSerpkValue());
        table.setPerYear(year);
        table.setPerMonth(month);
        table.setPerYearMonth(yearMonth);
        if(StringUtils.isNotBlank(vo.getDrId())){
            AppDrUser drUser = (AppDrUser)sysDao.getServiceDo().find(AppDrUser.class,vo.getDrId());
            if(drUser != null){
                table.setPerDrName(drUser.getDrName());
                if(StringUtils.isNotBlank(drUser.getDrHospId())){
                    AppHospDept hospDept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
                    if(hospDept != null){
                        table.setPerHospId(hospDept.getId());
                        table.setPerHospName(hospDept.getHospName());
                    }
                }
            }
        }
        table.setPerServeDate(ExtendDate.getCalendar(vo.getServeDate()));
        table.setPerCreateDate(ExtendDate.getCalendar(vo.getServeDate()));
        table.setPerServeNum(vo.getServeNum());
        table.setPerSource(vo.getPerSource());
        table.setPerTeamId(vo.getTeamId());
        table.setPerType(vo.getPerType());
        sysDao.getServiceDo().add(table);
    }
}
