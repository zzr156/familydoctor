package com.ylz.bizDo.app.dao.impl;

import com.google.gson.reflect.TypeToken;
import com.ylz.bizDo.app.dao.AppHealthDataDao;
import com.ylz.bizDo.app.po.AppHealthData;
import com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.ClinicalDocument;
import com.ylz.bizDo.jtapp.basicHealthEntity.findpersonandjzinfo.JzInfoDTO;
import com.ylz.bizDo.jtapp.basicHealthEntity.findpersonandjzinfo.PersonAndJzInfoDTO;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.comEnum.CommonHealth;
import com.ylz.packcommon.common.util.JsonUtil;
import net.sf.json.JSONObject;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

/**
 * Created by asus on 2017/07/27.
 */
@Service("appHealthDataDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppHealthDataDaoImpl implements AppHealthDataDao {

    @Autowired
    private SysDao sysDao;

    /**
     * 根据用户主键查询数据
     * @param type
     * @param idNo
     * @return
     */
    @Override
    public AppHealthData findByType(String type, String idNo) throws Exception{
        return (AppHealthData) sysDao.getServiceDo().getSessionFactory().getCurrentSession()
                .createCriteria(AppHealthData.class)
                .add(Restrictions.eq("healthIdno", idNo))
                .add(Restrictions.eq("healthType", type))
                .uniqueResult();
    }

    /**
     * 根据病人主键和编号查询
     * @param patientId
     * @param ghh000
     * @return
     */
    @Override
    public AppHealthData findByPatientId(String patientId, String ghh000,String type) throws Exception{
        return (AppHealthData) sysDao.getServiceDo().getSessionFactory().getCurrentSession()
                .createCriteria(AppHealthData.class)
                .add(Restrictions.eq("ghh000", ghh000))
                .add(Restrictions.eq("patientId", patientId))
                .add(Restrictions.eq("healthType", type))
                .uniqueResult();
    }

    /**
     * 添加数据
     * @param jsonall
     * @param idNo
     * @param card
     * @param type
     */
    @Override
    public void addHealthDataImplements(JSONObject jsonall,String idNo,String card,String type,String requestUserId,String requestUserName,String userType) throws Exception{
        if(jsonall.get("vo")!=null){
            PersonAndJzInfoDTO vo = JsonUtil.fromJson(jsonall.get("vo").toString(),PersonAndJzInfoDTO.class);
            if(vo != null){
                AppHealthData data = sysDao.getAppHealthDataDao().findByType(type,idNo);
                if(data != null){
                    data.setHealthDate(Calendar.getInstance());
                    data.setHealthData(jsonall.get("vo").toString());
                    sysDao.getServiceDo().modify(data);
                }else{
                    data = new AppHealthData();
                    data.setHealthIdno(idNo);
                    data.setHealthCard(card);
                    data.setHealthType(type);
                    data.setHealthDate(Calendar.getInstance());
                    data.setHealthData(jsonall.get("vo").toString());
                    sysDao.getServiceDo().add(data);
                }
                if(vo.getJzInfoDTOs() != null && vo.getJzInfoDTOs().size() >0){
                    for(JzInfoDTO v : vo.getJzInfoDTOs()){
                        if(type.equals("1")){
                            //门诊基本诊疗信息 getHealthDiagnosisBase
                            String mzjbxx = sysDao.getSecurityCardAsyncBean().getHealthInSynchro(v.getSsnumber(),v.getOrganizationCode(),v.getGhh000(),v.getPatientId(),v.getAreacode(),"getHealthDiagnosisBase",requestUserId,requestUserName,userType);
                            //门诊费用明细 getHealthMzDiagnosisCost
                            String mzfymx = sysDao.getSecurityCardAsyncBean().getHealthInSynchro(v.getSsnumber(),v.getOrganizationCode(),v.getGhh000(),v.getPatientId(),v.getAreacode(),"getHealthMzDiagnosisCost",requestUserId,requestUserName,userType);
                            //门诊用药记录 getDiagnosisMedicine
                            String mzyyjl = sysDao.getSecurityCardAsyncBean().getHealthInSynchro(v.getSsnumber(),v.getOrganizationCode(),v.getGhh000(),v.getPatientId(),v.getAreacode(),"getDiagnosisMedicine",requestUserId,requestUserName,userType);
                            this.getAddDataSynchro(v.getSsnumber(),v.getOrganizationCode(),v.getGhh000(),v.getPatientId(),v.getAreacode(),CommonHealth.MZJBZLXX.getValue(),mzjbxx);
                            this.getAddDataSynchro(v.getSsnumber(),v.getOrganizationCode(),v.getGhh000(),v.getPatientId(),v.getAreacode(),CommonHealth.MZFYMX.getValue(),mzfymx);
                            this.getAddDataSynchro(v.getSsnumber(),v.getOrganizationCode(),v.getGhh000(),v.getPatientId(),v.getAreacode(),CommonHealth.MZYYJL.getValue(),mzyyjl);
                        }else if(type.equals("2")){
                            //住院基本诊疗信息 getHealthZyDiagnosisBase
                            String zyjbzlxx = sysDao.getSecurityCardAsyncBean().getHealthInSynchro(v.getSsnumber(),v.getOrganizationCode(),v.getGhh000(),v.getPatientId(),v.getAreacode(),"getHealthZyDiagnosisBase",requestUserId,requestUserName,userType);
                            //住院费用明细  getHealthZyDiagnosisCost
                            String zyjbmx = sysDao.getSecurityCardAsyncBean().getHealthInSynchro(v.getSsnumber(),v.getOrganizationCode(),v.getGhh000(),v.getPatientId(),v.getAreacode(),"getHealthZyDiagnosisCost",requestUserId,requestUserName,userType);
                            //住院用药记录 getHealthDiagnosisMedicine
                            String zyyyjl = sysDao.getSecurityCardAsyncBean().getHealthInSynchro(v.getSsnumber(),v.getOrganizationCode(),v.getGhh000(),v.getPatientId(),v.getAreacode(),"getHealthDiagnosisMedicine",requestUserId,requestUserName,userType);
                            //长期医嘱 getCqyz
                            String cqyz = sysDao.getSecurityCardAsyncBean().getHealthInSynchro(v.getSsnumber(),v.getOrganizationCode(),v.getGhh000(),v.getPatientId(),v.getAreacode(),"getCqyz",requestUserId,requestUserName,userType);
                            //临时医嘱 getLsyz
                            String lsyz = sysDao.getSecurityCardAsyncBean().getHealthInSynchro(v.getSsnumber(),v.getOrganizationCode(),v.getGhh000(),v.getPatientId(),v.getAreacode(),"getLsyz",requestUserId,requestUserName,userType);
                            this.getAddDataSynchro(v.getSsnumber(),v.getOrganizationCode(),v.getGhh000(),v.getPatientId(),v.getAreacode(),CommonHealth.ZYJBZLXX.getValue(),zyjbzlxx);
                            this.getAddDataSynchro(v.getSsnumber(),v.getOrganizationCode(),v.getGhh000(),v.getPatientId(),v.getAreacode(),CommonHealth.ZYFYMX.getValue(),zyjbmx);
                            this.getAddDataSynchro(v.getSsnumber(),v.getOrganizationCode(),v.getGhh000(),v.getPatientId(),v.getAreacode(),CommonHealth.ZYYYJL.getValue(),zyyyjl);
                            this.getAddDataSynchro(v.getSsnumber(),v.getOrganizationCode(),v.getGhh000(),v.getPatientId(),v.getAreacode(),CommonHealth.CQYZ.getValue(),cqyz);
                            this.getAddDataSynchro(v.getSsnumber(),v.getOrganizationCode(),v.getGhh000(),v.getPatientId(),v.getAreacode(),CommonHealth.LSYZ.getValue(),lsyz);
                        }else if(type.equals("3")){
                            //体检报告 getmedicalReport
                            String tjbg = sysDao.getSecurityCardAsyncBean().getHealthInSynchro(v.getSsnumber(),v.getOrganizationCode(),v.getGhh000(),v.getPatientId(),v.getAreacode(),"getmedicalReport",requestUserId,requestUserName,userType);
                            this.getAddDataSynchro(v.getSsnumber(),v.getOrganizationCode(),v.getGhh000(),v.getPatientId(),v.getAreacode(),CommonHealth.TJBG.getValue(),tjbg);
                        }else if(type.equals("5")){
                            //门诊检查报告 getHealthMzJcExamReport
                            String mzjcbg = sysDao.getSecurityCardAsyncBean().getHealthInSynchro(v.getSsnumber(),v.getOrganizationCode(),v.getGhh000(),v.getPatientId(),v.getAreacode(),"getHealthMzJcExamReport",requestUserId,requestUserName,userType);
                            //门诊检验报告 getHealthMzJyExamReport
                            String mzjybg = sysDao.getSecurityCardAsyncBean().getHealthInSynchro(v.getSsnumber(),v.getOrganizationCode(),v.getGhh000(),v.getPatientId(),v.getAreacode(),"getHealthMzJyExamReport",requestUserId,requestUserName,userType);
                            //住院检查报告 getHealthZyJcExamReport
                            String zyjcbg = sysDao.getSecurityCardAsyncBean().getHealthInSynchro(v.getSsnumber(),v.getOrganizationCode(),v.getGhh000(),v.getPatientId(),v.getAreacode(),"getHealthZyJcExamReport",requestUserId,requestUserName,userType);
                            //住院检验报告 getHealthZyJyExamReport
                            String zyjybg = sysDao.getSecurityCardAsyncBean().getHealthInSynchro(v.getSsnumber(),v.getOrganizationCode(),v.getGhh000(),v.getPatientId(),v.getAreacode(),"getHealthZyJyExamReport",requestUserId,requestUserName,userType);
                            this.getAddDataSynchro(v.getSsnumber(),v.getOrganizationCode(),v.getGhh000(),v.getPatientId(),v.getAreacode(),CommonHealth.MZJCBG.getValue(),mzjcbg);
                            this.getAddDataSynchro(v.getSsnumber(),v.getOrganizationCode(),v.getGhh000(),v.getPatientId(),v.getAreacode(),CommonHealth.MZJYBG.getValue(),mzjybg);
                            this.getAddDataSynchro(v.getSsnumber(),v.getOrganizationCode(),v.getGhh000(),v.getPatientId(),v.getAreacode(),CommonHealth.ZYJCBG.getValue(),zyjcbg);
                            this.getAddDataSynchro(v.getSsnumber(),v.getOrganizationCode(),v.getGhh000(),v.getPatientId(),v.getAreacode(),CommonHealth.ZYJYBG.getValue(),zyjybg);
                        }
                    }
                }
            }
        }
    }

    private void getAddDataSynchro(String card,String organizationCode,String ghh000,String patientId,String areaCode,String type,String result) throws Exception{
        JSONObject jsonall = JSONObject.fromObject(result);
        if(jsonall.get("rows") != null){
            List<ClinicalDocument> lsDetail = JsonUtil.fromJson(jsonall.get("rows").toString(), new TypeToken<List<ClinicalDocument>>() {}.getType());
            if(lsDetail != null && lsDetail.size()>0){
                AppHealthData data = sysDao.getAppHealthDataDao().findByPatientId(patientId,ghh000,type);
                if(data != null){
                    data.setHealthData(jsonall.get("rows").toString());
                    sysDao.getServiceDo().modify(data);
                }else {
                    data = new AppHealthData();
                    data.setHealthCard(card);
                    data.setAreaCode(areaCode);
                    data.setGhh000(ghh000);
                    data.setOrganizationCode(organizationCode);
                    data.setPatientId(patientId);
                    data.setHealthData(jsonall.get("rows").toString());
                    data.setHealthType(type);
                    data.setHealthDate(Calendar.getInstance());
                    sysDao.getServiceDo().add(data);
                }

            }
        }
    }
}
