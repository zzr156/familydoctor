package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppHealthMeddleDao;
import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.app.vo.PerformanceDataQvo;
import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.jtapp.commonEntity.AppNewTcmLookEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppTcmLookEntity;
import com.ylz.bizDo.jtapp.commonVo.AppTcmGuideQvo;
import com.ylz.bizDo.jtapp.drEntity.AppDrMeddleEntity;
import com.ylz.bizDo.jtapp.drVo.AppDrQvo;
import com.ylz.bizDo.jtapp.drVo.AppHealthMeddleQvo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.*;
import com.ylz.packcommon.common.exception.DaoException;
import com.ylz.packcommon.common.util.AreaUtils;
import com.ylz.packcommon.common.util.ExtendDate;
import com.ylz.packcommon.common.util.FileUtils;
import com.ylz.packcommon.common.util.PropertiesUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2017/6/29.
 */
@Service("appHealthMeddleDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppHealthMeddleDaoImpl implements AppHealthMeddleDao {
    @Autowired
    private SysDao sysDao;

    @Override
    public void saveToPatient(AppHealthMeddleQvo qvo) throws Exception {

        if(StringUtils.isNotBlank(qvo.getPatientId())){
            String[] patientIds = qvo.getPatientId().split(";");
            AppGuideTemplate template =(AppGuideTemplate) sysDao.getServiceDo().find(AppGuideTemplate.class,qvo.getModId());
            for(String patientId:patientIds){
                PerformanceDataQvo qqvo = new PerformanceDataQvo();
                AppHealthMeddle table = new AppHealthMeddle();
                table.setHmDrId(qvo.getDrId());
                String fwType = "";
                String sermeal = "";
                List<AppSignForm> signForms = this.sysDao.getServiceDo().loadByPk(AppSignForm.class,"signPatientId",patientId);
                if(signForms!=null && signForms.size()>0){
                    AppSignForm signForm = signForms.get(0);
                    if(signForm!=null){
                        table.setHmTeamId(signForm.getSignTeamId());
                        fwType = sysDao.getAppLabelGroupDao().findFwValue(signForm.getId());
                        sermeal = signForm.getSignpackageid();
                    }
                }
                if(StringUtils.isNotBlank(qvo.getDrId())){
                    AppDrUser drUser = (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class,qvo.getDrId());
                    if(drUser!=null){
                        qqvo.setDrId(drUser.getId());
                        qqvo.setDrTel(drUser.getDrTel());
                        qqvo.setDrGender(drUser.getDrGender());
                        qqvo.setDrPwd(drUser.getDrPwd());
                        qqvo.setDrAccount(drUser.getDrAccount());
                        qqvo.setDrName(drUser.getDrName());
                        AppHospDept hospDept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
                        if(hospDept!=null){
                            table.setHmHospId(hospDept.getId());
                            table.setHmAreaCode(hospDept.getHospAreaCode());
                            qqvo.setHospTel(hospDept.getHospTel());
                            qqvo.setHospAddress(hospDept.getHospAddress());
                            qqvo.setHospAreaCode(hospDept.getHospAreaCode());
                            qqvo.setHospName(hospDept.getHospName());
                            qqvo.setHospId(hospDept.getId());
                        }
                    }
                }
                if(template==null){
                    table.setHmTitle(qvo.getTitle());
                    table.setHmContent(qvo.getContent());
                    table.setHmCreateTime(Calendar.getInstance());
                    table.setHmModId(qvo.getModId());
                    table.setHmDisType(qvo.getDisType());
                    table.setHmMedType(qvo.getMedType());
                    if(StringUtils.isNotBlank(qvo.getImageUrl())){
//                        String path = sysDao.getIoUtils().pathUrl(PropertiesUtil.getConfValue("filePicture"),PropertiesUtil.getConfValue("filePictureYz"),qvo.getImageName());
//                        FileUtils.decoderBase64File(qvo.getImageUrl(),PropertiesUtil.getConfValue("filePicture")+path);
//                        table.setHmImageUrl(path);
                        Map<String,Object> map = sysDao.getIoUtils().getCtyunOosSample(qvo.getImageUrl(), CommonShortType.YISHENG.getValue());
                        table.setHmImageUrl(map.get("objectUrl").toString());
//                    user.setPatientImageName(map.get("objectName").toString());
                    }

                }else{
                    table.setHmTitle(template.getGuideTitle());
                    table.setHmContent(template.getGuideContent());
                    table.setHmCreateTime(Calendar.getInstance());
                    table.setHmModId(template.getId());
                    table.setHmDisType(template.getGuideDiseaseType());
                    table.setHmMedType(template.getGuideMeddleType());
                    table.setHmImageUrl(template.getGuideImageUrl());
                }
                table.setHmPatientId(patientId);
                table.setHmGuideType(CommonGuideType.JKZD.getValue());
                sysDao.getServiceDo().add(table);
                AppWorkingPlan plan = new AppWorkingPlan();
                plan.setPlanType(CommonWorkPlanType.GY.getValue());
                plan.setPlanDate(Calendar.getInstance());
                plan.setPlanFinishDate(Calendar.getInstance());
                plan.setPlanState(CommonWorkPlanState.YWC.getValue());
                plan.setPlanDrId(table.getHmDrId());
                plan.setPlanTeamId(table.getHmTeamId());
                plan.setPlanHospId(table.getHmHospId());
                plan.setPlanAreaCode(table.getHmAreaCode());
                plan.setPlanForeignId(table.getHmPatientId());
                sysDao.getServiceDo().add(plan);
                qqvo.setTeamId(table.getHmTeamId());
                qqvo.setPerForeign(table.getId());
                sysDao.getAppNoticeDao().addNotices("健康指导消息提醒",
                        "您好，"+table.getHmDrName()+"医生在"+ ExtendDate.getChineseYMD(Calendar.getInstance())+"给您发了一条健康指导，请注意查看。", NoticesType.JKZD.getValue()+","+"2",qvo.getDrId(),patientId,table.getId(), DrPatientType.PATIENT.getValue());

                AppPatientUser user = (AppPatientUser)sysDao.getServiceDo().find(AppPatientUser.class,patientId);
                if(user!=null){
                    qqvo.setPerType(PerformanceType.JKZD.getValue());
                    qqvo.setPerIdno(user.getPatientIdno());
                    qqvo.setPerName(user.getPatientName());
                    if(StringUtils.isNotBlank(user.getPatientCity())){
                        CdAddress p = sysDao.getCdAddressDao().findByCode(user.getPatientCity());
                        if(p != null){
                            String code = AreaUtils.getAreaCode(p.getCtcode(),p.getLevel());
                            CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE,code);
                            if(value != null) {
                                qqvo.setPerArea(value.getCodeTitle());
                            }
                        }
                    }
                    if(StringUtils.isNotBlank(qqvo.getPerArea())){
                        if(StringUtils.isNotBlank(qqvo.getPerType())){
//                            String result = null;
//                            if (qqvo.getPerArea().equals(AddressType.FZ.getValue())) {
//
//                            } else if (qqvo.getPerArea().equals(AddressType.QZ.getValue())) {
//                                result = "qz_";
//                            } else if (qqvo.getPerArea().equals(AddressType.ZZ.getValue())) {
//                                result = "zz_";
//                            } else if (qqvo.getPerArea().equals(AddressType.PT.getValue())) {
//                                result = "pt_";
//                            } else if (qqvo.getPerArea().equals(AddressType.NP.getValue())) {
//                                result = "np_";
//                            } else if (qqvo.getPerArea().equals(AddressType.SM.getValue())) {
//                                result = "sm_";
//                            }
//                            if(StringUtils.isNotBlank(result)){
//                                qqvo.setDrId(result+qqvo.getDrId());
//                                qqvo.setHospId(result+qqvo.getHospId());
//                            }
                            sysDao.getAppPerformanceStatisticsDao().addPerformanceData(qqvo,sermeal,fwType);
//                            sysDao.getAppPerformanceTableDao().addPerformanceOne(qqvo,sermeal,fwType,qqvo.getPerType());
//                            if(StringUtils.isNotBlank(sermeal)){
//                                String[] sermeals = sermeal.split(";");
//                                for(String mealId:sermeals){//遍历服务包主键
//                                    AppServeSetmeal meal = (AppServeSetmeal)sysDao.getServiceDo().find(AppServeSetmeal.class,mealId);
//                                    if(meal != null){
//                                        if(StringUtils.isNotBlank(meal.getSersmObjectValue())){
//                                            String[] groupIds = meal.getSersmGroupId().split(";");//组合编号
//                                            for(String groupId:groupIds){
//                                                AppServeGroup group = (AppServeGroup)sysDao.getServiceDo().find(AppServeGroup.class,groupId);
//                                                if(group != null){
//                                                    if(StringUtils.isNotBlank(group.getSergObjectId()) && StringUtils.isNotBlank(group.getSergPkId())){
//                                                        AppServeObject object = (AppServeObject)sysDao.getServiceDo().find(AppServeObject.class,group.getSergObjectId());
//                                                        if(LabelManageType.FWRQ.getValue().equals(object.getSeroLabelType()) && fwType.indexOf(object.getSeroFwType())!=-1){
//                                                            AppServePackage pakege = sysDao.getAppServePackageDao().findPakege(group.getSergPkId(),PerformanceType.JKZD.getValue());
//                                                            if(pakege != null){
//                                                                qqvo.setSermValue(meal.getSersmValue());
//                                                                qqvo.setSergValue(group.getSergValue());
//                                                                qqvo.setSerpkValue(pakege.getSerpkValue());
//                                                                qqvo.setSermContent(pakege.getSerpkRemark());
//                                                                int count = sysDao.getAppPerformanceTableDao().findCount(qqvo);
//                                                                count +=1;
//                                                                qqvo.setServeNum(String.valueOf(count));
//                                                                sysDao.getAppPerformanceStatisticsDao().addPerformance(qqvo);
//                                                            }
//                                                        }
//                                                    }
//                                                }
//                                            }
//                                        }
//                                    }
//                                }
//                            }
                        }
                    }
                }
            }
        }

    }

    /**
     * 查看指导内容
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public AppDrMeddleEntity findById(String id) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("id",id);
        String sql = " SELECT a.ID id,a.HM_TITLE title,a.HM_CONTENT content," +
                "a.HM_IMAGE_URL imageUrl,HM_CREATE_TIME time," +
                "a.HM_MED_TYPE meddleType,a.HM_DIS_TYPE disType,a.HM_DR_ID drId,'' drName FROM APP_HEALTH_MEDDLE a WHERE 1=1";
        sql += " AND a.ID = :id";
        List<AppDrMeddleEntity> vos = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppDrMeddleEntity.class);
        if(vos!=null && vos.size()>0){
            AppDrMeddleEntity vo = vos.get(0);
            return vo;
        }
        return null;
    }

    @Override
    public AppDrMeddleEntity findByIdModdl(String id) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("id",id);
        String sql = " SELECT a.ID id,a.GUIDE_TITLE title,a.GUIDE_CONTENT content," +
                "a.GUIDE_IMAGE_URL imageUrl,a.GUIDE_CREATE_TIME time," +
                "a.GUIDE_MEDDLE_TYPE meddleType,a.GUIDE_DISEASE_TYPE disType,a.GUIDE_CREATE_ID drId,'' drName FROM APP_GUIDE_TEMPLATE a WHERE 1=1";
        sql += " AND a.ID = :id";
        List<AppDrMeddleEntity> vos = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppDrMeddleEntity.class);
        if(vos!=null && vos.size()>0){
            AppDrMeddleEntity vo = vos.get(0);
            return vo;
        }
        return null;
    }

    /**
     * 查询健康指导模板列表
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public List<AppDrMeddleEntity> findByQvo(AppDrQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = " SELECT a.ID id,a.GUIDE_TITLE title,a.GUIDE_CONTENT content,a.GUIDE_DISEASE_TYPE illType,a.GUIDE_DISEASE_TYPE disType,a.GUIDE_MEDDLE_TYPE medType,a.GUIDE_MEDDLE_TYPE meddleType," +
                "a.GUIDE_CREATE_TIME time,a.GUIDE_CREATE_ID drId,'' drName,a.GUIDE_IMAGE_URL imageUrl FROM APP_GUIDE_TEMPLATE a WHERE 1=1";
        if(StringUtils.isNotBlank(qvo.getUserId())){
            map.put("userId",qvo.getUserId());
            sql += " AND a.GUIDE_CREATE_ID = :userId";
        }else{
            map.put("hospId",qvo.getHospId());
            sql += " AND a.GUIDE_HOSP_ID=:hospId";
        }
        if(StringUtils.isNotBlank(qvo.getDisType())){
            map.put("disType",qvo.getDisType());
            sql += " AND a.GUIDE_DISEASE_TYPE = :disType";
        }
        if(StringUtils.isNotBlank(qvo.getMedType())){
            map.put("medType",qvo.getMedType());
            sql += " AND a.GUIDE_MEDDLE_TYPE = :medType";
        }
        map.put("type", CommonGuideType.JKZD.getValue());
        sql += " AND a.GUIDE_TYPE =:type";
        sql += " ORDER BY a.GUIDE_CREATE_TIME DESC";
        List<AppDrMeddleEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppDrMeddleEntity.class);
        return ls;
    }

    /**
     * 查询中医药体质辨识指导记录
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public List<AppTcmLookEntity> findByTcm(AppTcmGuideQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT ID id," +
                "TCMR_ID tcmId," +
                "TCMR_RESULT_TYPE tzType," +
                "TCMR_SCORE df," +
                "'' resultList," +
                "TCMR_RESULT_VALUE result " +
                "FROM APP_TCM_RESULT WHERE 1=1";
        map.put("tcmId",qvo.getJlId());
        sql += " AND TCMR_ID=:tcmId";
        sql += " ORDER BY TCMR_RESULT_VALUE,TCMR_RESULT_TYPE";
        List<AppTcmLookEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppTcmLookEntity.class);
        return ls;
    }

    @Override
    public List<AppNewTcmLookEntity> findByTcmGuide(AppTcmGuideQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT ID id," +
                "TCMR_ID tcmId," +
                "TCMR_RESULT_TYPE tzType," +
                "TCMR_SCORE df," +
                "'' resultList," +
                "TCMR_RESULT_VALUE result " +
                "FROM APP_TCM_RESULT WHERE 1=1";
        map.put("tcmId",qvo.getJlId());
        sql += " AND TCMR_ID=:tcmId";
        sql += " ORDER BY TCMR_RESULT_VALUE,TCMR_RESULT_TYPE";
        List<AppNewTcmLookEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppNewTcmLookEntity.class);
        return ls;
    }
}
