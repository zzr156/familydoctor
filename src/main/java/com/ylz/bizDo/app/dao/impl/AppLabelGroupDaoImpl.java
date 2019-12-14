package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppLabelGroupDao;
import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.app.vo.AppLabelManageQvo;
import com.ylz.bizDo.jtapp.commonEntity.AppDiseaseLabelEntity;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.comEnum.AppRoleType;
import com.ylz.packcommon.common.comEnum.DiseaseType;
import com.ylz.packcommon.common.comEnum.LabelManageType;
import com.ylz.packcommon.common.comEnum.ResidentMangeType;
import com.ylz.packcommon.common.util.XintSqlStr;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("appLabelGroupDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppLabelGroupDaoImpl implements AppLabelGroupDao {
    @Autowired
    private SysDao sysDao;

    @Override
    public List<AppLabelGroup> findBySignGroup(String signId, String type) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = " SELECT * FROM APP_LABEL_GROUP t WHERE 1=1 ";
        if(StringUtils.isNotBlank(signId)){
            map.put("LABEL_SIGN_ID",signId);
            sql += " AND t.LABEL_SIGN_ID = :LABEL_SIGN_ID ";
        }
        if(StringUtils.isNotBlank(type)){
            map.put("LABEL_TYPE",type);
            sql += " AND t.LABEL_TYPE = :LABEL_TYPE ";
        }
           List<AppLabelGroup> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppLabelGroup.class);
        if(ls != null && ls.size() >0){
            return ls;
        }
        return null;
    }

    /**
     * 添加标签数据
     * @param value
     * @param signId
     * @param teamId
     * @param areaCode
     * @param type
     */
    @Override
    public void addLabel(String[] value,String signId,String teamId,String areaCode,String type) throws Exception{
        if(value!=null && value.length>0 && StringUtils.isNotBlank(type)){
            if(LabelManageType.JBLX.getValue().equals(type)){//疾病类型
                List<AppLabelDisease> labelList = sysDao.getServiceDo().loadByPk(AppLabelDisease.class, "labelSignId", signId);
                if(labelList!=null && labelList.size()>0){
                    for (AppLabelDisease label : labelList) {
                        sysDao.getServiceDo().delete(label);
                    }
                }
            }else if(LabelManageType.FWRQ.getValue().equals(type)){//服务人群
                List<AppLabelGroup> labelList = sysDao.getServiceDo().loadByPk(AppLabelGroup.class, "labelSignId", signId);
                if(labelList!=null && labelList.size()>0){
                    for (AppLabelGroup label : labelList) {
                        sysDao.getServiceDo().delete(label);
                    }
                }
            }else if(LabelManageType.JJLX.getValue().equals(type)){//经济类型
                List<AppLabelEconomics> labelList = sysDao.getServiceDo().loadByPk(AppLabelEconomics.class, "labelSignId", signId);
                if(labelList!=null && labelList.size()>0){
                    for (AppLabelEconomics label : labelList) {
                        sysDao.getServiceDo().delete(label);
                    }
                }
            }else if(LabelManageType.JDLK.getValue().equals(type)){//建档立卡
                List<AppLabelArchiving> labelList = sysDao.getServiceDo().loadByPk(AppLabelArchiving.class, "labelArchivingId", signId);
                if(labelList!=null && labelList.size()>0){
                    for (AppLabelArchiving label : labelList) {
                        sysDao.getServiceDo().delete(label);
                    }
                }
            }

            String resultStr = null;
            for(String g:value){
                String[] s1 = g.split("\\|");
//                List<AppLabelManage> manage = sysDao.getServiceDo().loadByPk(AppLabelManage.class, "labelValue", s1[0]);
                AppLabelManage manage = null;
                if(LabelManageType.JDLK.getValue().equals(type)){
                    manage = sysDao.getAppLabelManageDao().findLabelByValue(LabelManageType.FWRQ.getValue(),s1[0]);
                }else{
                    manage = sysDao.getAppLabelManageDao().findLabelByValue(type,s1[0]);
                }
                if (manage != null) {
//                    if(resultStr != null){
//                        resultStr += ";"+ manage.getLabelSort();
//                    }else{
//                        resultStr = manage.getLabelSort();
//                    }
                    if(LabelManageType.JBLX.getValue().equals(type)){//疾病类型
                        AppLabelDisease alg = new AppLabelDisease();
                        alg.setLabelId(manage.getId());
                        alg.setLabelSignId(signId);
                        alg.setLabelTeamId(teamId);
                        alg.setLabelTitle(manage.getLabelTitle());
                        alg.setLabelValue(manage.getLabelValue());
                        alg.setLabelType(manage.getLabelType());
                        alg.setLabelAreaCode(areaCode);
                        if("2".equals(manage.getLabelType())){
                            if (s1.length > 1) {
                                alg.setLabelColor(s1[1]);
                            } else {
                                alg.setLabelColor("gray");
                            }
                        }
                        sysDao.getServiceDo().add(alg);
                    }else if(LabelManageType.FWRQ.getValue().equals(type)){//服务人群
                        AppLabelGroup alg = new AppLabelGroup();
                        alg.setLabelId(manage.getId());
                        alg.setLabelSignId(signId);
                        alg.setLabelTeamId(teamId);
                        alg.setLabelTitle(manage.getLabelTitle());
                        alg.setLabelValue(manage.getLabelValue());
                        alg.setLabelType(manage.getLabelType());
                        alg.setLabelAreaCode(areaCode);
                        sysDao.getServiceDo().add(alg);
                    }else if(LabelManageType.JJLX.getValue().equals(type)){//经济类型
                        AppLabelEconomics alg = new AppLabelEconomics();
                        alg.setLabelId(manage.getId());
                        alg.setLabelSignId(signId);
                        alg.setLabelTeamId(teamId);
                        alg.setLabelTitle(manage.getLabelTitle());
                        alg.setLabelValue(manage.getLabelValue());
                        alg.setLabelType(manage.getLabelType());
                        alg.setLabelAreaCode(areaCode);
                        sysDao.getServiceDo().add(alg);
                    }else if(LabelManageType.JDLK.getValue().equals(type)){//建档立卡人群
                        AppLabelArchiving alg = new AppLabelArchiving();
                        alg.setLabelId(manage.getId());
                        alg.setLabelArchivingId(signId);
                        alg.setLabelTeamId(teamId);
                        alg.setLabelTitle(manage.getLabelTitle());
                        alg.setLabelValue(manage.getLabelValue());
                        alg.setLabelType(manage.getLabelType());
                        alg.setLabelAreaCode(areaCode);
                        sysDao.getServiceDo().add(alg);
                    }
                }
            }
//            this.addSignLabelData(resultStr,type,signId);
        }
    }


    @Override
    public List<AppLabelEconomics> findBySignEconomics(String signId, String type)  throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = " SELECT * FROM APP_LABEL_ECONOMICS t WHERE 1=1 ";
        if(StringUtils.isNotBlank(signId)){
            map.put("LABEL_SIGN_ID",signId);
            sql += " AND t.LABEL_SIGN_ID = :LABEL_SIGN_ID ";
        }
        if(StringUtils.isNotBlank(type)){
            map.put("LABEL_TYPE",type);
            sql += " AND t.LABEL_TYPE = :LABEL_TYPE ";
        }
        List<AppLabelEconomics> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppLabelEconomics.class);
        if(ls != null && ls.size() >0){
            return ls;
        }
        return null;
    }

    @Override
    public List<AppLabelDisease> findBySignDisease(String signId, String type)  throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = " SELECT * FROM APP_LABEL_DISEASE t WHERE 1=1 ";
        if(StringUtils.isNotBlank(signId)){
            map.put("LABEL_SIGN_ID",signId);
            sql += " AND t.LABEL_SIGN_ID = :LABEL_SIGN_ID ";
        }
        if(StringUtils.isNotBlank(type)){
            map.put("LABEL_TYPE",type);
            sql += " AND t.LABEL_TYPE = :LABEL_TYPE ";
        }
        List<AppLabelDisease> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppLabelDisease.class);
        if(ls != null && ls.size() >0){
            return ls;
        }
        return null;
    }

    @Override
    public List<AppLabelEconomics> findBySignEcon(String signId, String value) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = " SELECT * FROM APP_LABEL_ECONOMICS t WHERE 1=1 ";
        if(StringUtils.isNotBlank(signId)){
            map.put("LABEL_SIGN_ID",signId);
            sql += " AND t.LABEL_SIGN_ID = :LABEL_SIGN_ID ";
        }
        if(StringUtils.isNotBlank(value)){
            map.put("LABEL_VALUE",value);
            sql += " AND t.LABEL_VALUE = :LABEL_VALUE ";
        }
        List<AppLabelEconomics> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppLabelEconomics.class);
        if(ls != null && ls.size() >0){
            return ls;
        }
        return null;
    }

    @Override
    public String findFwValue(String formId) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("signId",formId);
        String sql = "SELECT GROUP_CONCAT(LABEL_VALUE ORDER BY LABEL_VALUE) labelValue FROM APP_LABEL_GROUP WHERE LABEL_SIGN_ID = :signId AND LABEL_TYPE='3'";
        List<Map> mm = sysDao.getServiceDo().findSqlMap(sql,map);
        if(mm!=null && mm.size()>0){
            if(mm.get(0).get("labelValue")!=null){
                return mm.get(0).get("labelValue").toString();
            }
        }
        return null;
    }

    @Override
    public String findJjValue(String formId) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("signId",formId);
        String sql = "SELECT GROUP_CONCAT(LABEL_VALUE) labelValue FROM APP_LABEL_ECONOMICS WHERE LABEL_SIGN_ID = :signId ";
        List<Map> mm = sysDao.getServiceDo().findSqlMap(sql,map);
        if(mm!=null && mm.size()>0){
            if(mm.get(0).get("labelValue")!=null){
                return mm.get(0).get("labelValue").toString();
            }
        }
        return null;
    }

    @Override
    public List<AppDiseaseLabelEntity> findLabelGroupByQvo(AppLabelManageQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<>();
        String sql = "SELECT\n" +
                "\tb.ID id,\n" +
                "\ta.ID patientId,\n" +
                "\ta.PATIENT_NAME patientName,\n" +
                "\ta.PATIENT_IDNO patientIdno,\n" +
                "\t(SELECT c.LABEL_TITLE FROM app_label_group c WHERE c.LABEL_SIGN_ID = b.ID AND c.LABEL_TYPE = '3' AND c.LABEL_VALUE = '5' GROUP BY b.ID) hbpLabel,\n" +
                "\t(SELECT c.LABEL_COLOR FROM app_label_disease c WHERE c.LABEL_SIGN_ID = b.ID AND c.LABEL_TYPE = '2' AND c.LABEL_VALUE = '201' GROUP BY b.ID) hbpLabelColor,\n" +
                "\t(SELECT c.LABEL_TITLE FROM app_label_group c WHERE c.LABEL_SIGN_ID = b.ID AND c.LABEL_TYPE = '3' AND c.LABEL_VALUE = '6' GROUP BY b.ID) dmLabel,\n" +
                "\t(SELECT c.LABEL_COLOR FROM app_label_disease c WHERE c.LABEL_SIGN_ID = b.ID AND c.LABEL_TYPE = '2' AND c.LABEL_VALUE = '202' GROUP BY b.ID) dmLabelColor,\n" +
                "\t(SELECT c.LABEL_TITLE FROM app_label_group c WHERE c.LABEL_SIGN_ID = b.ID AND c.LABEL_TYPE = '3' AND c.LABEL_VALUE = '7' GROUP BY b.ID) pmPLabel,\n" +
                "\t(SELECT c.LABEL_COLOR FROM app_label_disease c WHERE c.LABEL_SIGN_ID = b.ID AND c.LABEL_TYPE = '2' AND c.LABEL_VALUE = '207' GROUP BY b.ID) pmPLabelColor,\n" +
                "\t(SELECT c.LABEL_TITLE FROM app_label_group c WHERE c.LABEL_SIGN_ID = b.ID AND c.LABEL_TYPE = '3' AND c.LABEL_VALUE = '8' GROUP BY b.ID) tbLabel,\n" +
                "\t(SELECT c.LABEL_COLOR FROM app_label_disease c WHERE c.LABEL_SIGN_ID = b.ID AND c.LABEL_TYPE = '2' AND c.LABEL_VALUE = '208' GROUP BY b.ID) tbLabelColor,\n" +
                "\ta.PATIENT_X xCoordinate,\n" +
                "\ta.PATIENT_Y yCoordinate\n" +
                "FROM\n" +
                "\tapp_patient_user a\n" +
                "INNER JOIN app_sign_form b ON a.ID = b.SIGN_PATIENT_ID\n" +
                "INNER JOIN app_label_group d ON b.ID = d.LABEL_SIGN_ID \n" +
                "WHERE\n" +
                "\tb.SIGN_STATE IN ('0','2')\n" +
                "AND d.LABEL_VALUE IN ('5','6','7','8') ";
        if(StringUtils.isNotBlank(qvo.getOrgId())){
            map.put("orgId",qvo.getOrgId());
            sql += " AND b.SIGN_HOSP_ID = :orgId ";
        }
        if(!AppRoleType.SHEQU.getValue().equals(qvo.getRole())){//不是管理员
            if(qvo.getRole().indexOf(AppRoleType.SHEQU.getValue())==-1){//又没有医院权限，查询团队
                String teamId = sysDao.getAppTeamMemberDao().findByDrId(qvo.getDrId());
                if(StringUtils.isNotBlank(teamId)){
                    map.put("teamIds",teamId.split(","));
                    sql += " AND b.SIGN_TEAM_ID IN (:teamIds) ";
                }else{
//                    sql += " AND b.SIGN_TEAM_ID IS NULL";
                    qvo.setItemCount(0);
                    qvo.setPageCount(0);
                    return null;
                }
            }
        }
        if(StringUtils.isNotBlank(qvo.getName())){
            map.put("name","%"+qvo.getName()+"%");
            sql += " AND a.PATIENT_NAME LIKE :name ";
        }
        if(StringUtils.isNotBlank(qvo.getIdno())){
            map.put("idno",qvo.getIdno());
            sql += " AND a.PATIENT_IDNO = :idno ";
        }
        sql += " GROUP BY a.ID ";
        List<AppDiseaseLabelEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppDiseaseLabelEntity.class,qvo);
        return list;
    }

    @Override
    public AppDiseaseLabelEntity findLabelGroupByOne(String signId) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("signId",signId);
        String sql = "SELECT\n" +
                "\tb.ID id,\n" +
                "\ta.ID patientId,\n" +
                "\ta.PATIENT_NAME patientName,\n" +
                "\ta.PATIENT_IDNO patientIdno,\n" +
                "\t(SELECT c.LABEL_VALUE FROM app_label_group c WHERE c.LABEL_SIGN_ID = b.ID AND c.LABEL_TYPE = '3' AND c.LABEL_VALUE = '5' GROUP BY b.ID) hbpLabel,\n" +
                "\t(SELECT c.LABEL_COLOR FROM app_label_disease c WHERE c.LABEL_SIGN_ID = b.ID AND c.LABEL_TYPE = '2' AND c.LABEL_VALUE = '201' GROUP BY b.ID) hbpLabelColor,\n" +
                "\t(SELECT c.LABEL_VALUE FROM app_label_group c WHERE c.LABEL_SIGN_ID = b.ID AND c.LABEL_TYPE = '3' AND c.LABEL_VALUE = '6' GROUP BY b.ID) dmLabel,\n" +
                "\t(SELECT c.LABEL_COLOR FROM app_label_disease c WHERE c.LABEL_SIGN_ID = b.ID AND c.LABEL_TYPE = '2' AND c.LABEL_VALUE = '202' GROUP BY b.ID) dmLabelColor,\n" +
                "\t(SELECT c.LABEL_VALUE FROM app_label_group c WHERE c.LABEL_SIGN_ID = b.ID AND c.LABEL_TYPE = '3' AND c.LABEL_VALUE = '7' GROUP BY b.ID) pmPLabel,\n" +
                "\t(SELECT c.LABEL_COLOR FROM app_label_disease c WHERE c.LABEL_SIGN_ID = b.ID AND c.LABEL_TYPE = '2' AND c.LABEL_VALUE = '207' GROUP BY b.ID) pmPLabelColor,\n" +
                "\t(SELECT c.LABEL_VALUE FROM app_label_group c WHERE c.LABEL_SIGN_ID = b.ID AND c.LABEL_TYPE = '3' AND c.LABEL_VALUE = '8' GROUP BY b.ID) tbLabel,\n" +
                "\t(SELECT c.LABEL_COLOR FROM app_label_disease c WHERE c.LABEL_SIGN_ID = b.ID AND c.LABEL_TYPE = '2' AND c.LABEL_VALUE = '208' GROUP BY b.ID) tbLabelColor,\n" +
                "\ta.PATIENT_X xCoordinate," +
                "\ta.PATIENT_Y yCoordinate " +
                "FROM\n" +
                "\tapp_patient_user a\n" +
                "INNER JOIN app_sign_form b ON a.ID = b.SIGN_PATIENT_ID\n" +
                "INNER JOIN app_label_group d ON b.ID = d.LABEL_SIGN_ID \n" +
                "WHERE\n" +
                "\tb.SIGN_STATE IN ('0','2')\n" +
                "AND d.LABEL_VALUE IN ('5','6','7','8') AND b.ID = :signId ";
        List<AppDiseaseLabelEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppDiseaseLabelEntity.class);
        if(list != null && list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public AppLabelDisease findDiseaseByOne(String value, String signId) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("value",value);
        map.put("signId",signId);
        String sql = "SELECT * FROM APP_LABEL_DISEASE WHERE 1=1 ";
        sql += " AND LABEL_VALUE =:value  AND LABEL_SIGN_ID =:signId ";
        List<AppLabelDisease> list = sysDao.getServiceDo().findSqlMap(sql,map,AppLabelDisease.class);
        if(list != null && list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public void addLabelDis(AppPatientUser user) throws Exception {
        if(user != null){
            AppSignForm form = sysDao.getAppSignformDao().getSignFormUserId(user.getId());
            if(form != null){
                //查询服务类型
                List<AppLabelGroup> labelGroups = sysDao.getAppLabelGroupDao().findBySignGroup(form.getId(),"3");
                if(labelGroups != null && labelGroups.size()>0){
                    for(AppLabelGroup labelGroup:labelGroups){
                        if(ResidentMangeType.GXY.getValue().equals(labelGroup.getLabelValue())){//高血压服务人群
                            //查询是否有高血压疾病标签
                            AppLabelDisease disease = sysDao.getAppLabelGroupDao().findDiseaseByOne(DiseaseType.GXY.getValue(),form.getId());
                            if(disease == null){
                                AppLabelManage manage = sysDao.getAppLabelManageDao().findLabelByValue("2",DiseaseType.GXY.getValue());
                                if(manage != null){
                                    disease = new AppLabelDisease();
                                    disease.setLabelColor("gray");
                                    disease.setLabelTeamId(form.getSignTeamId());
                                    disease.setLabelSignId(form.getId());
                                    disease.setLabelId(manage.getId());
                                    disease.setLabelTitle(manage.getLabelTitle());
                                    disease.setLabelType(manage.getLabelType());
                                    disease.setLabelValue(manage.getLabelValue());
                                    disease.setLabelAreaCode(form.getSignAreaCode());
                                    sysDao.getServiceDo().add(disease);
                                }
                            }
                        }else if(ResidentMangeType.TNB.getValue().equals(labelGroup.getLabelValue())){//糖尿病服务人群
                            //查询是否有糖尿病疾病标签
                            AppLabelDisease disease = sysDao.getAppLabelGroupDao().findDiseaseByOne(DiseaseType.TNB.getValue(),form.getId());
                            if(disease == null){
                                AppLabelManage manage = sysDao.getAppLabelManageDao().findLabelByValue("2",DiseaseType.TNB.getValue());
                                if(manage != null){
                                    disease = new AppLabelDisease();
                                    disease.setLabelColor("gray");
                                    disease.setLabelTeamId(form.getSignTeamId());
                                    disease.setLabelSignId(form.getId());
                                    disease.setLabelId(manage.getId());
                                    disease.setLabelTitle(manage.getLabelTitle());
                                    disease.setLabelType(manage.getLabelType());
                                    disease.setLabelValue(manage.getLabelValue());
                                    disease.setLabelAreaCode(form.getSignAreaCode());
                                    sysDao.getServiceDo().add(disease);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 根据签约单主键查询人口服务类型
     * WangCheng
     * @param signId
     * @return
     */
    public List<AppLabelGroup> listLabelGroup(String signId)  throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "select * from app_label_group a where 1=1";
        if(StringUtils.isNotEmpty(signId)){
            map.put("signId",signId);
            sql += " and a.LABEL_SIGN_ID = :signId";
        }
        sql += " GROUP BY a.LABEL_VALUE desc";
        List<AppLabelGroup> list = sysDao.getServiceDo().findSqlMap(sql,map,AppLabelGroup.class);
        if(list != null && list.size() > 0){
            return list;
        }
        return null;
    }

    /**
     * 根据签约单主键查询人口经济性质
     * WangCheng
     * @param signId
     * @return
     */
    public List<AppLabelEconomics> listLabelEconomics(String signId)  throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "select * from app_label_economics a where 1=1";
        if(StringUtils.isNotEmpty(signId)){
            map.put("signId",signId);
            sql += " and a.LABEL_SIGN_ID = :signId";
        }
        sql += " GROUP BY a.LABEL_VALUE desc";
        List<AppLabelEconomics> list = sysDao.getServiceDo().findSqlMap(sql,map,AppLabelEconomics.class);
        if(list != null && list.size() > 0){
            return list;
        }
        return null;
    }

    /**
     * 签约单生成服务类型,经济类型,疾病类型
     * @param resultStr
     * @param type
     * @param signId
     */
    public void  addSignLabelData(String resultStr,String type,String signId)throws Exception{
//        XintSqlStr vo = XintSqlStr.getInstance(0);
//        if(StringUtils.isNotBlank(resultStr)){
//            String[] resultNum = resultStr.split(";");
//            if(resultNum != null){
//                for(String s : resultNum){
//                    vo.addToIdx(Integer.parseInt(s));
//                }
//            }
//        }
//        AppSignForm signForm = (AppSignForm) sysDao.getServiceDo().find(AppSignForm.class,signId);
//        if(signForm != null){
//            if(LabelManageType.JBLX.getValue().equals(type)){//疾病类型
//                signForm.setSignDisease(vo.toXint());
//            }else if(LabelManageType.FWRQ.getValue().equals(type)){//服务人群
//                signForm.setSignGroup(vo.toXint());
//            }else if(LabelManageType.JJLX.getValue().equals(type)){//经济类型
//                signForm.setSignEconomics(vo.toXint());
//            }
//            sysDao.getServiceDo().modify(signForm);
//        }
    }

    @Override
    public AppLabelGroup findGroupBySignAndValue(String signId, String value) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("signId",signId);
        map.put("value",value);
        String sql = "SELECT * FROM APP_LABEL_GROUP WHERE LABEL_SIGN_ID =:signId AND LABEL_VALUE =:value AND LABEL_TYPE = '3'";
        List<AppLabelGroup> list = sysDao.getServiceDo().findSqlMap(sql,map,AppLabelGroup.class);
        if(list != null && list.size()>0){
            return list.get(0);
        }
        return null;
    }
}
