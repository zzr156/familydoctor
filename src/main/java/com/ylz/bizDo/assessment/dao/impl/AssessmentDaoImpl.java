package com.ylz.bizDo.assessment.dao.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionContext;
import com.ylz.bizDo.app.po.AppSignForm;
import com.ylz.bizDo.assessment.dao.AssessmentDao;
import com.ylz.bizDo.assessment.po.Assessment;
import com.ylz.bizDo.assessment.po.ReviewLog;
import com.ylz.bizDo.assessment.vo.*;
import com.ylz.bizDo.jtapp.commonEntity.AssessNewsEntity;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.comEnum.SignFormType;
import com.ylz.packcommon.common.util.ExtendDate;
import com.ylz.packcommon.hyd.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.*;

/**
 * Created by zms on 2018/6/8.
 */
@Service("assessmentDao")
@Transactional(rollbackFor = {Exception.class})
public class AssessmentDaoImpl implements AssessmentDao {

    @Autowired
    private SysDao sysDao;

    @Override
    public List<AssessListVo> findAssessList(AssessListQvo qvo) throws Exception {
        Map<String, Object> map = new HashMap<>();
        // 分页SQL拼接（自定义拼接分页SQL的目的：提升查询性能）
        StringBuilder sqlCount = new StringBuilder();
        boolean labelGroup = false;
        boolean labelEconomics = false;
        boolean patientuser = false;
        boolean assessment = false;
        if (StringUtils.isNotBlank(qvo.getPatientName()) || StringUtils.isNotBlank(qvo.getPatientIdNo()) ||
                StringUtils.isNotBlank(qvo.getCommittee())) {
            patientuser = true;
        }
        if (StringUtils.isNotBlank(qvo.getIsReview()) || StringUtils.isNotBlank(qvo.getState()) ||
                StringUtils.isNotBlank(qvo.getRating())) {
            assessment = true;
        }
        if (StringUtils.isNotBlank(qvo.getGroup())) {
            labelGroup = true;
        }
        if (StringUtils.isNotBlank(qvo.getEconomics())) {
            labelEconomics = true;
        }
        sqlCount.append(" SELECT t1.id id FROM app_sign_form t1 ");
        if (patientuser) {
            sqlCount.append(" LEFT JOIN app_patient_user t4 ON t1.sign_patient_id = t4.id ");
        }
        if (assessment) {
            sqlCount.append(" LEFT JOIN assessment t5 ON t1.id = t5.sign_id ");
        }
        if (labelGroup) {
            sqlCount.append(" LEFT JOIN app_label_group t2 ON t1.id = t2.label_sign_id ");
        }
        if (labelEconomics) {
            sqlCount.append(" LEFT JOIN app_label_economics t6 ON t1.id = t6.label_sign_id ");
        }

        // 查询SQL拼接
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT t1.id signId, t1.sign_num signNum, t1.sign_date signDate, t1.sign_dr_id drId, ");
        sql.append(" t1.sign_team_id teamId, t1.sign_state signState, t1.sign_from_date signFromDate, ");
        sql.append(" t1.sign_to_date signToDate, t1.sign_patient_idno idno, '' age, ");
        sql.append(" group_concat(t2.label_value) groupName, ");
        sql.append(" t4.id patientId, t4.patient_name name, t4.patient_gender sex, t4.patient_tel patientTel ");
        if ("1".equals(qvo.getIsAssess())) {// 已考核
            sql.append(" , t5.id assessmentId, t5.total_score_pre totalScorePre, t5.total_score_aft totalScoreAft, ");
            sql.append(" t5.detail_num detailNum, t5.finish_num finishNum, t5.is_review isReview, t5.is_finish isFinish ");
            sql.append(" FROM app_sign_form t1 ");
            sql.append(" LEFT JOIN app_patient_user t4 ON t1.sign_patient_id = t4.id ");
            sql.append(" LEFT JOIN assessment t5 ON t1.id = t5.sign_id ");
            sql.append(" LEFT JOIN app_label_group t2 ON t1.id = t2.label_sign_id ");
            if (labelEconomics) {
                sql.append(" LEFT JOIN app_label_economics t6 ON t1.id = t6.label_sign_id ");
            }
        } else if ("0".equals(qvo.getIsAssess()) || "3".equals(qvo.getIsAssess())) {// 未考核
            sql.append(" FROM app_sign_form t1 ");
            sql.append(" LEFT JOIN app_patient_user t4 ON t1.sign_patient_id = t4.id ");
            sql.append(" LEFT JOIN app_label_group t2 ON t1.id = t2.label_sign_id ");
            if (labelEconomics) {
                sql.append(" LEFT JOIN app_label_economics t6 ON t1.id = t6.label_sign_id ");
            }
        } else {// 已考核 + 未考核（默认查询）
            sql.append(" , t1.assess_state assessState ");
            sql.append(" FROM app_sign_form t1 ");
            sql.append(" LEFT JOIN app_patient_user t4 ON t1.sign_patient_id = t4.id ");
            sql.append(" LEFT JOIN app_label_group t2 ON t1.id = t2.label_sign_id ");
            if (labelEconomics) {
                sql.append(" LEFT JOIN app_label_economics t6 ON t1.id = t6.label_sign_id ");
            }
        }
        sql.append(" WHERE 1 = 1 ");
        sqlCount.append(" WHERE 1 = 1 ");

        // 历史签约单（签约单状态）
        if (StringUtils.isNotBlank(qvo.getHistory())) {
            if ("0".equals(qvo.getHistory())) {// 不显示无效签约单
                sql.append(" and t1.sign_state in ('0', '2') ");
                sqlCount.append(" and t1.sign_state in ('0', '2') ");
            } else if ("1".equals(qvo.getHistory())) {// 只显示过期签约单
                sql.append(" and t1.sign_state = '4' and t1.sign_urrender_reason = '签约到期，自动解约' ");
                sqlCount.append(" and t1.sign_state = '4' and t1.sign_urrender_reason = '签约到期，自动解约' ");
            }
        } else {
            sql.append(" and (t1.sign_state in ('0', '2') or (t1.sign_state = '4' and t1.sign_urrender_reason = '签约到期，自动解约')) ");
            sqlCount.append(" and (t1.sign_state in ('0', '2') or (t1.sign_state = '4' and t1.sign_urrender_reason = '签约到期，自动解约')) ");
        }
        // 地区
        String hospLevel = (String) ActionContext.getContext().getSession().get("HospLevel");
        String hospAreaCode = (String) ActionContext.getContext().getSession().get("HospAreaCode");
        if (StringUtils.isNotBlank(qvo.getAreaCode())) {// 查询某个地区的签约居民【条件查询】
            if ("2".equals(hospLevel)) {// 市级账号查找某个区县范围内的签约居民
                sql.append(" and t1.sign_area_code like :signAreaCode ");
                sqlCount.append(" and t1.sign_area_code like :signAreaCode ");
                map.put("signAreaCode", qvo.getAreaCode().substring(0, 6) + "%");
            } else {// 县级账号查找某个乡镇范围内的签约居民
                sql.append(" and t1.sign_area_code like :signAreaCode ");
                sqlCount.append(" and t1.sign_area_code like :signAreaCode ");
                map.put("signAreaCode", qvo.getAreaCode().substring(0, 9) + "%");
            }
        } else {// 查询用户所在区域下的签约居民【默认查询】
            if ("2".equals(hospLevel)) {// 市级账号查找整个市范围内的签约居民
                sql.append(" and t1.sign_area_code like :signAreaCode ");
                sqlCount.append(" and t1.sign_area_code like :signAreaCode ");
                map.put("signAreaCode", hospAreaCode.substring(0, 4) + "%");
            } else if ("3".equals(hospLevel)) {// 区县账号查找整个区县范围内的签约居民
                sql.append(" and t1.sign_area_code like :signAreaCode ");
                sqlCount.append(" and t1.sign_area_code like :signAreaCode ");
                map.put("signAreaCode", hospAreaCode.substring(0, 6) + "%");
            } else if ("4".equals(hospLevel)) {// 乡镇账号查找整个乡镇范围内的签约居民
                sql.append(" and t1.sign_area_code like :signAreaCode ");
                sqlCount.append(" and t1.sign_area_code like :signAreaCode ");
                map.put("signAreaCode", hospAreaCode.substring(0, 9) + "%");
            }
        }
        // 机构
        if (StringUtils.isNotBlank(qvo.getHospId())) {
            sql.append(" and t1.sign_hosp_id = :hospId ");
            sqlCount.append(" and t1.sign_hosp_id = :hospId ");
            map.put("hospId", qvo.getHospId());
        }
        // 居民姓名
        if (StringUtils.isNotBlank(qvo.getPatientName())) {
            sql.append(" and t4.patient_name like :patientName");
            sqlCount.append(" and t4.patient_name like :patientName");
            map.put("patientName", "%" + qvo.getPatientName() + "%");
        }
        // 居民身份证号
        if (StringUtils.isNotBlank(qvo.getPatientIdNo())) {
            sql.append(" and t4.patient_idno = :patientIdNo");
            sqlCount.append(" and t4.patient_idno = :patientIdNo");
            map.put("patientIdNo", qvo.getPatientIdNo());
        }
        // 签约编号
        if (StringUtils.isNotBlank(qvo.getSignNum())) {
            sql.append(" and t1.sign_num = :signNum");
            sqlCount.append(" and t1.sign_num = :signNum");
            map.put("signNum", qvo.getSignNum());
        }
        // 团队ID
        if (StringUtils.isNotBlank(qvo.getTeamId())) {
            sql.append(" and t1.sign_team_id = :signTeamId");
            sqlCount.append(" and t1.sign_team_id = :signTeamId");
            map.put("signTeamId", qvo.getTeamId());
        }
        // 医生ID
        if (StringUtils.isNotBlank(qvo.getDrId())) {
            sql.append(" and t1.sign_dr_id = :drId");
            sqlCount.append(" and t1.sign_dr_id = :drId");
            map.put("drId", qvo.getDrId());
        }
        // 签约日期开始
        if (StringUtils.isNotBlank(qvo.getSignDateStart())) {
            sql.append(" and t1.sign_date >= :signDateStart ");
            sqlCount.append(" and t1.sign_date >= :signDateStart ");
            map.put("signDateStart", qvo.getSignDateStart() + " 00:00:00");
        }
        // 签约日期结束
        if (StringUtils.isNotBlank(qvo.getSignDateEnd())) {
            sql.append(" and t1.sign_date <=:signDateEnd ");
            sqlCount.append(" and t1.sign_date <=:signDateEnd ");
            map.put("signDateEnd", qvo.getSignDateEnd() + " 23:59:59");
        }
        // 协议开始日期开始
        if (StringUtils.isNotBlank(qvo.getSignFromDateStart())) {
            sql.append(" and t1.sign_from_date >= :signFromDateStart ");
            sqlCount.append(" and t1.sign_from_date >= :signFromDateStart ");
            map.put("signFromDateStart", qvo.getSignFromDateStart() + " 00:00:00");
        }
        // 协议开始日期结束
        if (StringUtils.isNotBlank(qvo.getSignFromDateEnd())) {
            sql.append(" and t1.sign_from_date <=:signFromDateEnd ");
            sqlCount.append(" and t1.sign_from_date <=:signFromDateEnd ");
            map.put("signFromDateEnd", qvo.getSignFromDateEnd() + " 23:59:59");
        }
        // 协议截止日期开始
        if (StringUtils.isNotBlank(qvo.getSignToDateStart())) {
            sql.append(" and t1.sign_to_date >= :signToDateStart ");
            sqlCount.append(" and t1.sign_to_date >= :signToDateStart ");
            map.put("signToDateStart", qvo.getSignToDateStart() + " 00:00:00");
        }
        // 协议截止日期结束
        if (StringUtils.isNotBlank(qvo.getSignToDateEnd())) {
            sql.append(" and t1.sign_to_date <=:signToDateEnd ");
            sqlCount.append(" and t1.sign_to_date <=:signToDateEnd ");
            map.put("signToDateEnd", qvo.getSignToDateEnd() + " 23:59:59");
        }
        // 服务人群
        if (StringUtils.isNotBlank(qvo.getGroup())) {
            String[] groups = qvo.getGroup().split(",");// 所属人群多选
            sql.append(" and ( ");
            sqlCount.append(" and ( ");
            boolean notFirst = false;
            for (String group : groups) {
                if (notFirst) {
                    sql.append(" or ");
                    sqlCount.append(" or ");
                }
                if ("health".equals(group)) {
                    sql.append(" t2.label_value in ('1', '9', '99') ");
                    sqlCount.append(" t2.label_value in ('1', '9', '99') ");
                    notFirst = true;
                } else if ("young".equals(group)) {
                    sql.append(" t2.label_value = '2' ");
                    sqlCount.append(" t2.label_value = '2' ");
                    notFirst = true;
                } else if ("pregnant".equals(group)) {
                    sql.append(" t2.label_value = '3' ");
                    sqlCount.append(" t2.label_value = '3' ");
                    notFirst = true;
                } else if ("old".equals(group)) {
                    sql.append(" t2.label_value= '4' ");
                    sqlCount.append(" t2.label_value= '4' ");
                    notFirst = true;
                } else if ("chronic".equals(group)) {
                    sql.append(" t2.label_value in ('5', '6') ");
                    sqlCount.append(" t2.label_value in ('5', '6') ");
                    notFirst = true;
                } else if ("psychosis".equals(group)) {
                    sql.append(" t2.label_value = '7' ");
                    sqlCount.append(" t2.label_value = '7' ");
                    notFirst = true;
                } else if ("tuberculosis".equals(group)) {
                    sql.append(" t2.label_value = '8' ");
                    sqlCount.append(" t2.label_value = '8' ");
                    notFirst = true;
                }
            }
            sql.append(" ) ");
            sqlCount.append(" ) ");
        }
        // 经济类型
        if (StringUtils.isNotBlank(qvo.getEconomics())) {
            String[] economics = qvo.getEconomics().split(",");// 经济类型多选
            sql.append(" and t6.label_value in (:economics) ");
            sqlCount.append(" and t6.label_value in (:economics) ");
            map.put("economics", economics);
        }
        // 村、居委会
        if (StringUtils.isNotBlank(qvo.getCommittee())) {
            sql.append(" and t4.patient_neighborhood_committee = :committee ");
            sqlCount.append(" and t4.patient_neighborhood_committee = :committee ");
            map.put("committee", qvo.getCommittee());
        }
        // 是否已考核
        if (StringUtils.isNotBlank(qvo.getIsAssess())) {
            if ("0".equals(qvo.getIsAssess())) {
                sql.append(" and (t1.assess_state = '0' or t1.assess_state is null) ");
                sqlCount.append(" and (t1.assess_state = '0' or t1.assess_state is null) ");
            } else if ("1".equals(qvo.getIsAssess())) {
                sql.append(" and t1.assess_state = '1' ");
                sqlCount.append(" and t1.assess_state = '1' ");
                // 是否已审核
                if (StringUtils.isNotBlank(qvo.getIsReview())) {
                    if ("0".equals(qvo.getIsReview())) {
                        sql.append(" and t5.is_review = '0' ");
                        sqlCount.append(" and t5.is_review = '0' ");
                    } else if ("1".equals(qvo.getIsReview())) {
                        sql.append(" and t5.is_review in ('1', '2') ");
                        sqlCount.append(" and t5.is_review in ('1', '2') ");
                    }
                }
                // 状态（已考核完成、未考核完成、已审核、审核中）
                if (StringUtils.isNotBlank(qvo.getState())) {
                    String[] states = qvo.getState().split(",");
                    sql.append(" and ( ");
                    sqlCount.append(" and ( ");
                    boolean notFirst = false;
                    for (String state : states) {
                        if (notFirst) {
                            sql.append(" or ");
                            sqlCount.append(" or ");
                        }
                        if ("1".equals(state)) {// 已考核完成
                            sql.append(" t5.is_finish = '1' ");
                            sqlCount.append(" t5.is_finish = '1' ");
                            notFirst = true;
                        } else if ("2".equals(state)) {// 未考核完成
                            sql.append(" t5.is_finish = '0' ");
                            sqlCount.append(" t5.is_finish = '0' ");
                            notFirst = true;
                        } else if ("3".equals(state)) {// 已审核
                            sql.append(" t5.is_review in ('1', '2') ");
                            sqlCount.append(" t5.is_review in ('1', '2') ");
                            notFirst = true;
                        } else if ("4".equals(state)) {// 审核中
                            sql.append(" t5.is_review in ('1', '2') and t5.total_score_aft < t5.total_score_pre ");
                            sqlCount.append(" t5.is_review in ('1', '2') and t5.total_score_aft < t5.total_score_pre ");
                            notFirst = true;
                        }
                    }
                    sql.append(" ) ");
                    sqlCount.append(" ) ");
                }
                // 考核评级
                if (StringUtils.isNotBlank(qvo.getRating())) {
                    String[] ratings = qvo.getRating().split(",");
                    sql.append(" and ( ");
                    sqlCount.append(" and ( ");
                    boolean notFirst = false;
                    for (String rating : ratings) {
                        if (notFirst) {
                            sql.append(" or ");
                            sqlCount.append(" or ");
                        }
                        if ("0".equals(rating)) {// 不合格
                            sql.append(" t5.total_score_aft < 60 ");
                            sqlCount.append(" t5.total_score_aft < 60 ");
                            notFirst = true;
                        } else if ("1".equals(rating)) {// 合格
                            sql.append(" t5.total_score_aft = 60 ");
                            sqlCount.append(" t5.total_score_aft = 60 ");
                            notFirst = true;
                        } else if ("2".equals(rating)) {// 良
                            sql.append(" (t5.total_score_aft > 60 and t5.total_score_aft <= 80) ");
                            sqlCount.append(" (t5.total_score_aft > 60 and t5.total_score_aft <= 80) ");
                            notFirst = true;
                        } else if ("3".equals(rating)) {// 优
                            sql.append(" t5.total_score_aft > 80 ");
                            sqlCount.append(" t5.total_score_aft > 80 ");
                            notFirst = true;
                        }
                    }
                    sql.append(" ) ");
                    sqlCount.append(" ) ");
                }
            } else if ("3".equals(qvo.getIsAssess())) {
                sql.append(" and (t1.assess_state = '0' or t1.assess_state is null) ");
                sqlCount.append(" and (t1.assess_state = '0' or t1.assess_state is null) ");
                // 获取当前日期和一个月后的当前日期
                Calendar calendar = Calendar.getInstance();
                String currentDate = ExtendDate.getYMD(calendar) + " 00:00:00";
                calendar.add(Calendar.MONTH, 1);
                String oneMonthLater = ExtendDate.getYMD(calendar) + " 00:00:00";
                sql.append(" and t1.sign_to_date >= :currentDate and t1.sign_to_date < :oneMonthLater ");
                sqlCount.append(" and t1.sign_to_date >= :currentDate and t1.sign_to_date < :oneMonthLater ");
                map.put("currentDate", currentDate);
                map.put("oneMonthLater", oneMonthLater);
            }
        }
        // 随机抽取
        List<AssessListVo> list = new ArrayList<>();
        if (StringUtils.isNotBlank(qvo.getRandom())) {
            sql.append(" and t5.is_extract = '0' ");// 从未被抽取过的数据中抽取
            sql.append(" group by t1.id ");
            sql.append(" order by rand() limit " + qvo.getRandom());
            list = sysDao.getServiceDo().findSqlMapRVo(sql.toString(), map, AssessListVo.class);
            if (list != null && list.size() > 0) {
                changeExtractState(list);// 修改抽取状态
            }
        } else {
            sql.append(" group by t1.id ");
            sqlCount.append(" group by t1.id ");
            list = sysDao.getServiceDo().findSqlMapRVo(sql.toString(), sqlCount.toString(), map, AssessListVo.class, qvo);
        }
        if (list != null && list.size() > 0) {
            return list;
        }
        return null;
    }

    @Override
    public String findAssess(String hospId) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append(" select count(1) assessedNum from assessment t1, app_sign_form t2 where t1.sign_id=t2.id ");
        sql.append(" and t1.hosp_id = :hospId and t2.sign_to_date >= sysdate() ");
        Map<String, Object> map = new HashMap<>();
        map.put("hospId", hospId);
        List<Map> result = sysDao.getServiceDo().findSqlMap(sql.toString(), map);
        if (result != null && result.size() > 0) {
            return result.get(0).get("assessedNum").toString();
        }
        return "0";
    }

    @Override
    public Assessment findAssessment(Assessment assessment) throws Exception {
        String sql = " select * from assessment where sign_id = :signId ";
        Map<String, Object> map = new HashMap<>();
        map.put("signId", assessment.getSignId());
        List list = sysDao.getServiceDo().findSqlMap(sql, map, Assessment.class);
        if (list != null && list.size() > 0) {
            return (Assessment) list.get(0);
        }
        return null;
    }

    public void changeExtractState(List<AssessListVo> assessListVos) throws Exception {
        List<String> ids = new ArrayList<>();
        for (AssessListVo vo : assessListVos) {
            ids.add(vo.getAssessmentId());
        }
        String sql = " update assessment set is_extract = '1' where id in (:ids) ";
        Map<String, Object> map = new HashMap<>();
        map.put("ids", ids.toArray());
        sysDao.getServiceDo().update(sql, map);
    }

    @Override
    public Assessment saveOrUpdateAssessment(Assessment assessment) throws Exception {
        Assessment oldAssessment = null;
        if (StringUtils.isNotBlank(assessment.getId())) {
            oldAssessment = (Assessment) sysDao.getServiceDo().find(Assessment.class, assessment.getId());
        }
        if (oldAssessment != null) {// 修改
            oldAssessment.setState("0");// 退回状态重新置为0
            oldAssessment.setIsExtract("0");// 抽取状态重置为0
            // 已经经过审核的修改保存次数
            if (StringUtils.isNotBlank(oldAssessment.getIsReview()) && !"0".equals(oldAssessment.getIsReview())) {
                if (assessment.getSaveNum() != null) {// 第二次保存的时候po.getSaveNum才会传值
                    oldAssessment.setSaveNum(oldAssessment.getSaveNum() == 0 ? 1 : oldAssessment.getSaveNum() + 1);
                }
            }
            sysDao.getServiceDo().modify(oldAssessment);
            return oldAssessment;
        } else {// 新增
            AppSignForm form = (AppSignForm) sysDao.getServiceDo().find(AppSignForm.class, assessment.getSignId());
            if (form == null) {
                throw new Exception("签约单数据异常！");
            } else {
                form.setSignAssesState("1");// 已生成考核记录
                sysDao.getServiceDo().modify(form);
            }
            sysDao.getServiceDo().add(assessment);
            return assessment;
        }
    }

    @Override
    public List<AssessmentGroup> findGroup(AssessmentVo qvo) throws Exception {
        StringBuilder sql = new StringBuilder();
        // 严重精神障碍、结核病、残疾人、未知都归为健康人群
        sql.append(" select distinct(case label_value when '1' then 'health' ");
        sql.append(" when '9' then 'health' when '99' then 'health' ");
        sql.append(" when '2' then 'young' when '3' then 'pregnant' when '4' then 'old' ");
        sql.append(" when '5' then 'chronic' when '6' then 'chronic' ");
        sql.append(" when '7' then 'psychosis' when '8' then 'psychosis' else 'health' end) labelGroup ");
        sql.append(" from app_label_group where label_sign_id = :signId ");
        Map<String, Object> map = new HashMap<>();
        map.put("signId", qvo.getSignId());
        List<AssessmentGroup> list = sysDao.getServiceDo().findSqlMapRVo(sql.toString(), map, AssessmentGroup.class);
        List<AssessmentGroup> finalList = new ArrayList<>();
        if (list != null && list.size() > 0) {// 一个人不可能即是健康人群又是其他人群（需求是这么定的）
            boolean isOther = false;
            boolean isHealth = false;
            for (AssessmentGroup group : list) {
                if ("health".equals(group.getLabelGroup())) {
                    isHealth = true;
                } else {
                    isOther = true;
                }
            }
            if (isOther && isHealth) {
                for (AssessmentGroup group : list) {
                    if (!"health".equals(group.getLabelGroup())) {
                        finalList.add(group);
                    }
                }
            } else {
                finalList = list;
            }
        } else {// 如果没有查询到则默认为健康人群
            AssessmentGroup group = new AssessmentGroup();
            group.setLabelGroup("health");
            finalList.add(group);
        }
        return finalList;
    }

    @Override
    public interfaceQvo findInterceParams(AssessmentVo qvo) throws Exception {
        Map<String, Object> map = new HashMap<>();
        StringBuilder sql = new StringBuilder();
        sql.append(" select t2.patient_idno idno, t2.patient_name patientName, ");
        sql.append(" t2.patient_birthday birthday, t1.sign_from_date signTimeStart, t1.sign_to_date signTimeEnd ");
        sql.append(" from app_sign_form t1, app_patient_user t2 ");
        sql.append(" where t1.sign_patient_id = t2.id and t1.id = :signId ");
        map.put("signId", qvo.getSignId());
        List<interfaceQvo> list = sysDao.getServiceDo().findSqlMapRVo(sql.toString(), map, interfaceQvo.class);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public int treatAssess(ReviewVo vo) throws Exception {
        if (StringUtils.isBlank(vo.getAssessmentId())) {
            throw new Exception("考核表主键为空!");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("assessmentId", vo.getAssessmentId());
        if ("3".equals(vo.getHospLevel()) || "2".equals(vo.getHospLevel())) {// 区县以上级别
            map.put("state", "1");
        } else if ("4".equals(vo.getHospLevel())) {// 乡镇
            map.put("state", "2");
        }
        String sql = " update assessment set state = :state where id = :assessmentId ";
        ReviewLog reviewLog = new ReviewLog();
        reviewLog.setUpdateUserId(vo.getUpdateUserId());
        reviewLog.setUpdateUserName(vo.getUpdateUserName());
        reviewLog.setAssessmentId(vo.getAssessmentId());
        reviewLog.setSignAreaCode(vo.getSignAreaCode());
        reviewLog.setContent("退回考核表");
        sysDao.getServiceDo().add(reviewLog);
        return sysDao.getServiceDo().update(sql, map);
    }

    @Override
    public List<AssessmentVo> findNotAssess(String hospId) throws Exception {
        // 获取当前日期及一个月后的当前日期
        Calendar calendar = Calendar.getInstance();
        String currentDate = ExtendDate.getYMD(calendar) + " 00:00:00";
        calendar.add(Calendar.MONTH, 1);
        String oneMonthLater = ExtendDate.getYMD(calendar) + " 00:00:00";
        // 统计
        Map<String, Object> map = new HashMap<>();
        StringBuilder sql = new StringBuilder();
        sql.append(" select count(1) notAssessNum from app_sign_form where sign_hosp_id = :signHospId ");
        sql.append(" and sign_state in ('0', '2')  and (assess_state = '0' or assess_state is null) ");
        sql.append(" and sign_to_date >= :currentDate and sign_to_date < :oneMonthLater ");
        map.put("signHospId", hospId);
        map.put("currentDate", currentDate);
        map.put("oneMonthLater", oneMonthLater);
        return sysDao.getServiceDo().findSqlMapRVo(sql.toString(), map, AssessmentVo.class);
    }

    @Override
    public List<String> findNotAssessDetail(AssessmentVo qvo) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append(" select t3.patient_name patientName, t2.option_web optionWeb ");
        sql.append(" from assessment t1 left join ");
        sql.append(" (select assessment_id, option_web from assessment_detail where content_code = '32') t2 ");
        sql.append(" on t1.id = t2.assessment_id, ");
        sql.append(" app_patient_user t3, app_sign_form t4 where t1.patient_id = t3.id and t1.hosp_id = :hospId ");
        sql.append(" and t4.id = t1.sign_id and t4.sign_to_date >= SYSDATE() ");
        Map<String, Object> map = new HashMap<>();
        map.put("hospId", qvo.getHospId());
        List<AssessDetailVo> ls = sysDao.getServiceDo().findSqlMapRVo(sql.toString(), map, AssessDetailVo.class);
        List<String> patients = new ArrayList<>();
        for (AssessDetailVo detailVo : ls) {
            int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
            JSONObject jObj = (JSONObject) JSONObject.parse(detailVo.getOptionWeb());
            if (jObj == null) {// 字段为空
                patients.add(detailVo.getPatientName());
                continue;
            }
            if (jObj.get(month + "") == null) {// 相应月份为空
                patients.add(detailVo.getPatientName());
                continue;
            }
            JSONArray jArray = (JSONArray) jObj.get(month + "");
            if (jArray.size() == 0) {// 相应月份的图片个数为空(即上传后又被删除了)
                patients.add(detailVo.getPatientName());
            }
        }
        if (patients.size() > 0)
            return patients;
        return null;
    }

    @Override
    public Object downLoad(AssessFileVo vo) throws Exception {
        InputStream in = null;
        OutputStream os = null;
        if (StringUtils.isBlank(vo.getFilePath())) {
            throw new Exception("文件路径为空!");
        }
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;fileName=" + vo.getFileName());
        try {
            String filePath = vo.getFilePath();
            URL url = new URL(filePath);
            // 打开本地文件流
            in = new BufferedInputStream(url.openStream());
            // 激活下载操作
            os = response.getOutputStream();
            // 循环写入输出流
            byte[] b = new byte[2048];
            int length;
            while ((length = in.read(b)) > 0) {
                os.write(b, 0, length);
            }
            return null;
        } catch (Exception e) {
            throw new BusinessException("下载失败" + e.toString());
        } finally {
            if (in != null) {
                in.close();
            }
            if (os != null) {
                os.close();
            }
        }
    }

    @Override
    public int countSignNum(AssessListQvo qvo) throws Exception {
        Map<String, Object> map = new HashMap<>();
        StringBuilder sql = new StringBuilder();
        if (StringUtils.isNotBlank(qvo.getEconomics())) {
            sql.append(" select count(1) signNum from ( ");
            sql.append(" select count(1) from app_sign_form t1 left join app_label_economics t2 on t1.id = t2.label_sign_id ");
        } else {
            sql.append(" select count(1) signNum from app_sign_form t1 ");
        }
        sql.append(" where 1 = 1 ");
        // 历史签约单（签约单状态）
        if (StringUtils.isNotBlank(qvo.getHistory())) {
            if ("0".equals(qvo.getHistory())) {// 不显示无效签约单
                sql.append(" and t1.sign_state in ('0', '2') ");
            } else if ("1".equals(qvo.getHistory())) {// 只显示过期签约单
                sql.append(" and t1.sign_state = '4' and t1.sign_urrender_reason = '签约到期，自动解约' ");
            }
        } else {
            sql.append(" and ((t1.sign_state in ('0', '2')) or (t1.sign_state = '4' and t1.sign_urrender_reason = '签约到期，自动解约')) ");
        }
        // 查询条件（市、区县、乡镇、机构、团队、医生）
        if (StringUtils.isNotBlank(qvo.getDrId())) {// 医生ID
            sql.append(" and t1.sign_dr_id = :drId");
            map.put("drId", qvo.getDrId());
        }
        if (StringUtils.isNotBlank(qvo.getTeamId())) {// 团队ID
            sql.append(" and t1.sign_team_id = :signTeamId ");
            map.put("signTeamId", qvo.getTeamId());
        } else if (StringUtils.isNotBlank(qvo.getHospId())) {// 机构ID
            sql.append(" and t1.sign_hosp_id = :signHospId ");
            map.put("signHospId", qvo.getHospId());
        } else if (StringUtils.isNotBlank(qvo.getAreaCode())) {// 地区编码
            if ("3".equals(qvo.getAreaLevel())) {// 区县级
                sql.append(" and t1.sign_area_code like :signAreaCode ");
                map.put("signAreaCode", qvo.getAreaCode().substring(0, 6) + "%");
            } else if ("4".equals(qvo.getAreaLevel())) {// 乡镇
                sql.append(" and t1.sign_area_code like :signAreaCode ");
                map.put("signAreaCode", qvo.getAreaCode().substring(0, 9) + "%");
            }
        }
        // 签约日期开始
        if (StringUtils.isNotBlank(qvo.getSignDateStart())) {
            sql.append(" and t1.sign_date >= :signDateStart ");
            map.put("signDateStart", qvo.getSignDateStart() + " 00:00:00");
        }
        // 签约日期结束
        if (StringUtils.isNotBlank(qvo.getSignDateEnd())) {
            sql.append(" and t1.sign_date <= :signDateEnd ");
            map.put("signDateEnd", qvo.getSignDateEnd() + " 23:59:59");
        }
        // 协议开始日期开始
        if (StringUtils.isNotBlank(qvo.getSignFromDateStart())) {
            sql.append(" and t1.sign_from_date >= :signFromDateStart ");
            map.put("signFromDateStart", qvo.getSignFromDateStart() + " 00:00:00");
        }
        // 协议开始日期结束
        if (StringUtils.isNotBlank(qvo.getSignFromDateStart())) {
            sql.append(" and t1.sign_from_date <=:signFromDateEnd ");
            map.put("signFromDateEnd", qvo.getSignFromDateEnd() + " 23:59:59");
        }
        // 协议截止日期开始
        if (StringUtils.isNotBlank(qvo.getSignToDateStart())) {
            sql.append(" and t1.sign_to_date >= :signToDateStart ");
            map.put("signToDateStart", qvo.getSignToDateStart() + " 00:00:00");
        }
        // 协议截止日期结束
        if (StringUtils.isNotBlank(qvo.getSignToDateEnd())) {
            sql.append(" and t1.sign_to_date <=:signToDateEnd ");
            map.put("signToDateEnd", qvo.getSignToDateEnd() + " 23:59:59");
        }
        // 经济类型
        if (StringUtils.isNotBlank(qvo.getEconomics())) {
            String[] economics = qvo.getEconomics().split(",");// 经济类型多选
            sql.append(" and t2.label_value in (:economics) ");
            sql.append(" group by t1.id) g ");
            map.put("economics", economics);
        }
        List<AssessmentCountVo> list = sysDao.getServiceDo().findSqlMapRVo(sql.toString(), map, AssessmentCountVo.class);
        if (list != null && list.size() > 0) {
            return list.get(0).getSignNum().intValue();
        } else {
            return 0;
        }
    }

    @Override
    public List<AssessmentCountVo> countSignNumForArea(AssessListQvo qvo) throws Exception {
        Map<String, Object> map = new HashMap<>();
        StringBuilder sql = new StringBuilder();
        if (StringUtils.isNotBlank(qvo.getEconomics())) {
            if ("2".equals(qvo.getAreaLevel())) {// 市级账号统计区县
                sql.append(" select concat(left(g.signAreaCode, 6), '000000') signAreaCode, count(1) signNum ");
            } else if ("3".equals(qvo.getAreaLevel())) {// 区县级账号统计乡镇
                sql.append(" select concat(left(g.signAreaCode, 9), '000') signAreaCode, count(1) signNum ");
            }
            sql.append(" from (select t1.sign_area_code signAreaCode from app_sign_form t1 left join app_label_economics t2 on t1.id = t2.label_sign_id ");
        } else {
            if ("2".equals(qvo.getAreaLevel())) {// 市级账号统计区县
                sql.append(" select concat(left(t1.sign_area_code, 6), '000000') signAreaCode, count(1) signNum from app_sign_form t1 ");
            } else if ("3".equals(qvo.getAreaLevel())) {// 区县级账号统计乡镇
                sql.append(" select concat(left(t1.sign_area_code, 9), '000') signAreaCode, count(1) signNum from app_sign_form t1 ");
            }
        }
        sql.append(" where 1 = 1 ");
        // 历史签约单（签约单状态）
        if (StringUtils.isNotBlank(qvo.getHistory())) {
            if ("0".equals(qvo.getHistory())) {// 不显示无效签约单
                sql.append(" and t1.sign_state in ('0', '2') ");
            } else if ("1".equals(qvo.getHistory())) {// 只显示过期签约单
                sql.append(" and t1.sign_state = '4' and t1.sign_urrender_reason = '签约到期，自动解约' ");
            }
        } else {
            sql.append(" and ((t1.sign_state in ('0', '2')) or (t1.sign_state = '4' and t1.sign_urrender_reason = '签约到期，自动解约')) ");
        }
        // 查询条件（市、区县）
        if (StringUtils.isNotBlank(qvo.getAreaCode())) {// 地区编码
            if ("2".equals(qvo.getAreaLevel())) {// 市级
                sql.append(" and t1.sign_area_code like :signAreaCode ");
                map.put("signAreaCode", qvo.getAreaCode().substring(0, 4) + "%");
            } else if ("3".equals(qvo.getAreaLevel())) {// 区县级
                sql.append(" and t1.sign_area_code like :signAreaCode ");
                map.put("signAreaCode", qvo.getAreaCode().substring(0, 6) + "%");
            }
        }
        // 签约日期开始
        if (StringUtils.isNotBlank(qvo.getSignDateStart())) {
            sql.append(" and t1.sign_date >= :signDateStart ");
            map.put("signDateStart", qvo.getSignDateStart() + " 00:00:00");
        }
        // 签约日期结束
        if (StringUtils.isNotBlank(qvo.getSignDateEnd())) {
            sql.append(" and t1.sign_date <= :signDateEnd ");
            map.put("signDateEnd", qvo.getSignDateEnd() + " 23:59:59");
        }
        // 协议开始日期开始
        if (StringUtils.isNotBlank(qvo.getSignFromDateStart())) {
            sql.append(" and t1.sign_from_date >= :signFromDateStart ");
            map.put("signFromDateStart", qvo.getSignFromDateStart() + " 00:00:00");
        }
        // 协议开始日期结束
        if (StringUtils.isNotBlank(qvo.getSignFromDateStart())) {
            sql.append(" and t1.sign_from_date <=:signFromDateEnd ");
            map.put("signFromDateEnd", qvo.getSignFromDateEnd() + " 23:59:59");
        }
        // 协议截止日期开始
        if (StringUtils.isNotBlank(qvo.getSignToDateStart())) {
            sql.append(" and t1.sign_to_date >= :signToDateStart ");
            map.put("signToDateStart", qvo.getSignToDateStart() + " 00:00:00");
        }
        // 协议截止日期结束
        if (StringUtils.isNotBlank(qvo.getSignToDateEnd())) {
            sql.append(" and t1.sign_to_date <=:signToDateEnd ");
            map.put("signToDateEnd", qvo.getSignToDateEnd() + " 23:59:59");
        }
        // 经济类型
        if (StringUtils.isNotBlank(qvo.getEconomics())) {
            String[] economics = qvo.getEconomics().split(",");// 经济类型多选
            sql.append(" and t2.label_value in (:economics) ");
            sql.append(" group by t1.id) g ");
            map.put("economics", economics);
        }
        if (StringUtils.isNotBlank(qvo.getAreaCode())) {// 地区编码
            if (StringUtils.isNotBlank(qvo.getEconomics())) {
                if ("2".equals(qvo.getAreaLevel())) {// 市级账号查询区县
                    sql.append(" group by left(g.signAreaCode, 6) ");
                } else if ("3".equals(qvo.getAreaLevel())) {// 区县级账号查询乡镇
                    sql.append(" group by left(g.signAreaCode, 9) ");
                }
            } else {
                if ("2".equals(qvo.getAreaLevel())) {// 市级账号查询区县
                    sql.append(" group by left(t1.sign_area_code, 6) ");
                } else if ("3".equals(qvo.getAreaLevel())) {// 区县级账号查询乡镇
                    sql.append(" group by left(t1.sign_area_code, 9) ");
                }
            }
        }
        return sysDao.getServiceDo().findSqlMapRVo(sql.toString(), map, AssessmentCountVo.class);
    }

    @Override
    public int countFinishNum(AssessListQvo qvo) throws Exception {
        Map<String, Object> map = new HashMap<>();
        StringBuilder sql = new StringBuilder();
        sql.append(" select count(1) assessNum from assessment t1 ");
        sql.append(" left join app_sign_form t2 on t1.sign_id = t2.id ");
        sql.append(" where t1.is_finish = '1' ");
        // 历史签约单（签约单状态）
        if (StringUtils.isNotBlank(qvo.getHistory())) {
            if ("0".equals(qvo.getHistory())) {// 不显示无效签约单
                sql.append(" and t2.sign_state in ('0', '2') ");
            } else if ("1".equals(qvo.getHistory())) {// 只显示过期签约单
                sql.append(" and t2.sign_state = '4' and t2.sign_urrender_reason = '签约到期，自动解约' ");
            }
        } else {
            sql.append(" and ((t2.sign_state in ('0', '2')) or (t2.sign_state = '4' and t2.sign_urrender_reason = '签约到期，自动解约')) ");
        }
        // 查询条件（市、区县、乡镇、机构、团队、医生）
        if (StringUtils.isNotBlank(qvo.getDrId())) {// 医生ID
            sql.append(" and t2.sign_dr_id = :drId");
            map.put("drId", qvo.getDrId());
        }
        if (StringUtils.isNotBlank(qvo.getTeamId())) {// 团队ID
            sql.append(" and t2.sign_team_id = :signTeamId ");
            map.put("signTeamId", qvo.getTeamId());
        } else if (StringUtils.isNotBlank(qvo.getHospId())) {// 机构ID
            sql.append(" and t2.sign_hosp_id = :signHospId ");
            map.put("signHospId", qvo.getHospId());
        } else if (StringUtils.isNotBlank(qvo.getAreaCode())) {// 地区编码
            if ("3".equals(qvo.getAreaLevel())) {// 区县级
                sql.append(" and t2.sign_area_code like :signAreaCode ");
                map.put("signAreaCode", qvo.getAreaCode().substring(0, 6) + "%");
            } else if ("4".equals(qvo.getAreaLevel())) {// 乡镇
                sql.append(" and t2.sign_area_code like :signAreaCode ");
                map.put("signAreaCode", qvo.getAreaCode().substring(0, 9) + "%");
            }
        }
        // 签约日期开始
        if (StringUtils.isNotBlank(qvo.getSignDateStart())) {
            sql.append(" and t2.sign_date >= :signDateStart ");
            map.put("signDateStart", qvo.getSignDateStart() + " 00:00:00");
        }
        // 签约日期结束
        if (StringUtils.isNotBlank(qvo.getSignDateEnd())) {
            sql.append(" and t2.sign_date <= :signDateEnd ");
            map.put("signDateEnd", qvo.getSignDateEnd() + " 23:59:59");
        }
        // 协议开始日期开始
        if (StringUtils.isNotBlank(qvo.getSignFromDateStart())) {
            sql.append(" and t1.sign_from_date >= :signFromDateStart ");
            map.put("signFromDateStart", qvo.getSignFromDateStart() + " 00:00:00");
        }
        // 协议开始日期结束
        if (StringUtils.isNotBlank(qvo.getSignFromDateStart())) {
            sql.append(" and t1.sign_from_date <=:signFromDateEnd ");
            map.put("signFromDateEnd", qvo.getSignFromDateEnd() + " 23:59:59");
        }
        // 协议截止日期开始
        if (StringUtils.isNotBlank(qvo.getSignToDateStart())) {
            sql.append(" and t2.sign_to_date >= :signToDateStart ");
            map.put("signToDateStart", qvo.getSignToDateStart() + " 00:00:00");
        }
        // 协议截止日期结束
        if (StringUtils.isNotBlank(qvo.getSignToDateEnd())) {
            sql.append(" and t2.sign_to_date <=:signToDateEnd ");
            map.put("signToDateEnd", qvo.getSignToDateEnd() + " 23:59:59");
        }
        List<AssessmentCountVo> list = sysDao.getServiceDo().findSqlMapRVo(sql.toString(), map, AssessmentCountVo.class);
        if (list != null && list.size() > 0) {
            return list.get(0).getFinishNum().intValue();
        } else {
            return 0;
        }
    }

    @Override
    public List<AssessmentCountVo> countRatingNum(AssessListQvo qvo) throws Exception {
        Map<String, Object> map = new HashMap<>();
        StringBuilder sql = new StringBuilder();
        sql.append(" select g.rating, count(1) ratingNum from ( ");
        sql.append(" select ");
        sql.append(" case ");
        sql.append(" when t1.total_score_aft = 60 then '合格' ");
        sql.append(" when t1.total_score_aft > 60 and t1.total_score_aft <= 80 then '良' ");
        sql.append(" when t1.total_score_aft > 80 then '优' ");
        sql.append(" else '不合格' end rating ");
        sql.append(" from assessment t1 ");
        sql.append(" left join app_sign_form t2 on t1.sign_id = t2.id ");
        if (StringUtils.isNotBlank(qvo.getEconomics())) {
            sql.append(" left join app_label_economics t3 on t2.id = t3.label_sign_id ");
        }
        sql.append(" where 1 = 1 ");
        // 历史签约单（签约单状态）
        if (StringUtils.isNotBlank(qvo.getHistory())) {
            if ("0".equals(qvo.getHistory())) {// 不显示无效签约单
                sql.append(" and t2.sign_state in ('0', '2') ");
            } else if ("1".equals(qvo.getHistory())) {// 只显示过期签约单
                sql.append(" and t2.sign_state = '4' and t2.sign_urrender_reason = '签约到期，自动解约' ");
            }
        } else {
            sql.append(" and ((t2.sign_state in ('0', '2')) or (t2.sign_state = '4' and t2.sign_urrender_reason = '签约到期，自动解约')) ");
        }
        // 查询条件（市、区县、乡镇、机构、团队、医生）
        if (StringUtils.isNotBlank(qvo.getDrId())) {// 医生ID
            sql.append(" and t2.sign_dr_id = :drId");
            map.put("drId", qvo.getDrId());
        }
        if (StringUtils.isNotBlank(qvo.getTeamId())) {// 团队ID
            sql.append(" and t2.sign_team_id = :signTeamId ");
            map.put("signTeamId", qvo.getTeamId());
        } else if (StringUtils.isNotBlank(qvo.getHospId())) {// 机构ID
            sql.append(" and t2.sign_hosp_id = :signHospId ");
            map.put("signHospId", qvo.getHospId());
        } else if (StringUtils.isNotBlank(qvo.getAreaCode())) {// 地区编码
            if ("3".equals(qvo.getAreaLevel())) {// 区县级
                sql.append(" and t2.sign_area_code like :signAreaCode ");
                map.put("signAreaCode", qvo.getAreaCode().substring(0, 6) + "%");
            } else if ("4".equals(qvo.getAreaLevel())) {// 乡镇
                sql.append(" and t2.sign_area_code like :signAreaCode ");
                map.put("signAreaCode", qvo.getAreaCode().substring(0, 9) + "%");
            }
        }
        // 签约日期开始
        if (StringUtils.isNotBlank(qvo.getSignDateStart())) {
            sql.append(" and t2.sign_date >= :signDateStart ");
            map.put("signDateStart", qvo.getSignDateStart() + " 00:00:00");
        }
        // 签约日期结束
        if (StringUtils.isNotBlank(qvo.getSignDateEnd())) {
            sql.append(" and t2.sign_date <= :signDateEnd ");
            map.put("signDateEnd", qvo.getSignDateEnd() + " 23:59:59");
        }
        // 协议开始日期开始
        if (StringUtils.isNotBlank(qvo.getSignFromDateStart())) {
            sql.append(" and t2.sign_from_date >= :signFromDateStart ");
            map.put("signFromDateStart", qvo.getSignFromDateStart() + " 00:00:00");
        }
        // 协议开始日期结束
        if (StringUtils.isNotBlank(qvo.getSignFromDateStart())) {
            sql.append(" and t2.sign_from_date <=:signFromDateEnd ");
            map.put("signFromDateEnd", qvo.getSignFromDateEnd() + " 23:59:59");
        }
        // 协议截止日期开始
        if (StringUtils.isNotBlank(qvo.getSignToDateStart())) {
            sql.append(" and t2.sign_to_date >= :signToDateStart ");
            map.put("signToDateStart", qvo.getSignToDateStart() + " 00:00:00");
        }
        // 协议截止日期结束
        if (StringUtils.isNotBlank(qvo.getSignToDateEnd())) {
            sql.append(" and t2.sign_to_date <=:signToDateEnd ");
            map.put("signToDateEnd", qvo.getSignToDateEnd() + " 23:59:59");
        }
        // 经济类型
        if (StringUtils.isNotBlank(qvo.getEconomics())) {
            String[] economics = qvo.getEconomics().split(",");// 经济类型多选
            sql.append(" and t3.label_value in (:economics) ");
            map.put("economics", economics);
        }
        sql.append(" group by t2.id ");
        sql.append(" ) g group by g.rating ");
        return sysDao.getServiceDo().findSqlMapRVo(sql.toString(), map, AssessmentCountVo.class);
    }

    @Override
    public List<AssessmentCountVo> countRatingNumForArea(AssessListQvo qvo) throws Exception {
        Map<String, Object> map = new HashMap<>();
        StringBuilder sql = new StringBuilder();
        if ("2".equals(qvo.getAreaLevel())) {// 市级账号统计区县
            sql.append(" select concat(left(g.signAreaCode, 6), '000000') signAreaCode, g.rating, count(1) ratingNum from ( ");
        } else if ("3".equals(qvo.getAreaLevel())) {// 区县级账号统计乡镇
            sql.append(" select concat(left(g.signAreaCode, 9), '000') signAreaCode, g.rating, count(1) ratingNum from ( ");
        }
        sql.append(" select t2.sign_area_code signAreaCode, ");
        sql.append(" case ");
        sql.append(" when t1.total_score_aft = 60 then '合格' ");
        sql.append(" when t1.total_score_aft > 60 and t1.total_score_aft <= 80 then '良' ");
        sql.append(" when t1.total_score_aft > 80 then '优' ");
        sql.append(" else '不合格' end rating ");
        sql.append(" from assessment t1 ");
        sql.append(" left join app_sign_form t2 on t1.sign_id = t2.id ");
        if (StringUtils.isNotBlank(qvo.getEconomics())) {
            sql.append(" left join app_label_economics t3 on t2.id = t3.label_sign_id ");
        }
        sql.append(" where 1 = 1 ");
        // 历史签约单（签约单状态）
        if (StringUtils.isNotBlank(qvo.getHistory())) {
            if ("0".equals(qvo.getHistory())) {// 不显示无效签约单
                sql.append(" and t2.sign_state in ('0', '2') ");
            } else if ("1".equals(qvo.getHistory())) {// 只显示过期签约单
                sql.append(" and t2.sign_state = '4' and t2.sign_urrender_reason = '签约到期，自动解约' ");
            }
        } else {
            sql.append(" and ((t2.sign_state in ('0', '2')) or (t2.sign_state = '4' and t2.sign_urrender_reason = '签约到期，自动解约')) ");
        }
        // 查询条件（市、区县）
        if (StringUtils.isNotBlank(qvo.getAreaCode())) {// 地区编码
            if ("2".equals(qvo.getAreaLevel())) {// 市级
                sql.append(" and t2.sign_area_code like :signAreaCode ");
                map.put("signAreaCode", qvo.getAreaCode().substring(0, 4) + "%");
            } else if ("3".equals(qvo.getAreaLevel())) {// 区县级
                sql.append(" and t2.sign_area_code like :signAreaCode ");
                map.put("signAreaCode", qvo.getAreaCode().substring(0, 6) + "%");
            }
        }
        // 签约日期开始
        if (StringUtils.isNotBlank(qvo.getSignDateStart())) {
            sql.append(" and t2.sign_date >= :signDateStart ");
            map.put("signDateStart", qvo.getSignDateStart() + " 00:00:00");
        }
        // 签约日期结束
        if (StringUtils.isNotBlank(qvo.getSignDateEnd())) {
            sql.append(" and t2.sign_date <= :signDateEnd ");
            map.put("signDateEnd", qvo.getSignDateEnd() + " 23:59:59");
        }
        // 协议开始日期开始
        if (StringUtils.isNotBlank(qvo.getSignFromDateStart())) {
            sql.append(" and t2.sign_from_date >= :signFromDateStart ");
            map.put("signFromDateStart", qvo.getSignFromDateStart() + " 00:00:00");
        }
        // 协议开始日期结束
        if (StringUtils.isNotBlank(qvo.getSignFromDateStart())) {
            sql.append(" and t2.sign_from_date <=:signFromDateEnd ");
            map.put("signFromDateEnd", qvo.getSignFromDateEnd() + " 23:59:59");
        }
        // 协议截止日期开始
        if (StringUtils.isNotBlank(qvo.getSignToDateStart())) {
            sql.append(" and t2.sign_to_date >= :signToDateStart ");
            map.put("signToDateStart", qvo.getSignToDateStart() + " 00:00:00");
        }
        // 协议截止日期结束
        if (StringUtils.isNotBlank(qvo.getSignToDateEnd())) {
            sql.append(" and t2.sign_to_date <=:signToDateEnd ");
            map.put("signToDateEnd", qvo.getSignToDateEnd() + " 23:59:59");
        }
        // 经济类型
        if (StringUtils.isNotBlank(qvo.getEconomics())) {
            String[] economics = qvo.getEconomics().split(",");// 经济类型多选
            sql.append(" and t3.label_value in (:economics) ");
            map.put("economics", economics);
        }
        sql.append(" group by t2.id ");
        if ("2".equals(qvo.getAreaLevel())) {// 市级
            sql.append(" ) g group by left(g.signAreaCode, 6), g.rating ");
        } else if ("3".equals(qvo.getAreaLevel())) {// 区县级
            sql.append(" ) g group by left(g.signAreaCode, 9), g.rating ");
        }
        return sysDao.getServiceDo().findSqlMapRVo(sql.toString(), map, AssessmentCountVo.class);
    }

    @Override
    public List<AssessNewsEntity> findAssessMonthSign() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("SIGN_STATE", new String[]{SignFormType.YUQY.getValue(), SignFormType.YQY.getValue()});
        map.put("SIGN_TO_DATE", ExtendDate.getYMD(Calendar.getInstance()) + " 00:00:00");
        String sql = "select SIGN_DR_ID drId,count(1) notAssessNum from app_sign_form where 1=1 \n" +
                "and sign_to_date between :SIGN_TO_DATE and DATE_ADD(:SIGN_TO_DATE, INTERVAL 1 MONTH) \n" +
                "and assess_state = '0' and SIGN_STATE IN (:SIGN_STATE)\n" +
                "GROUP BY SIGN_DR_ID";
        List<AssessNewsEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql, map, AssessNewsEntity.class);
        return list;
    }

    @Override
    public List<AssessNewsEntity> findAssessWeekSign() throws Exception {
        return null;
    }

    @Override
    public List<AssessNewsEntity> findAssessMonthSignById(String drId) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("DR_ID", drId);
        map.put("SIGN_STATE", new String[]{SignFormType.YUQY.getValue(), SignFormType.YQY.getValue()});
        map.put("SIGN_TO_DATE", ExtendDate.getYMD(Calendar.getInstance()) + " 00:00:00");
        String sql = "select SIGN_DR_ID drId from app_sign_form where 1=1 \n" +
                "and sign_to_date between :SIGN_TO_DATE and DATE_ADD(:SIGN_TO_DATE, INTERVAL 1 MONTH) \n" +
                "and assess_state = '0' and SIGN_STATE IN (:SIGN_STATE)\n" +
                "and sign_dr_id =:DR_ID ";
        List<AssessNewsEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql, map, AssessNewsEntity.class);
        return list;
    }

    @Override
    public List<String> findAssessWeekSignById(String drId) throws Exception {
        String sql = "select t3.patient_name patientName,t2.option_web optionWeb\n" +
                "from assessment t1 left join (select assessment_id,option_web from assessment_detail where CONTENT_CODE='32') t2 on t1.id = t2.assessment_id,\n" +
                "app_patient_user t3 ,app_sign_form t4\n" +
                "where t1.patient_id = t3.ID\n" +
                "and t1.DOCTOR_ID = :DR_ID\n" +
                "and t4.id = t1.sign_id\n" +
                "and t4.sign_to_date >= SYSDATE()";//签约未到期的
        HashMap map = new HashMap();
        map.put("DR_ID", drId);
        List<AssessDetailVo> ls = sysDao.getServiceDo().findSqlMapRVo(sql, map, AssessDetailVo.class);
        List<String> patients = new ArrayList<>();
        for (AssessDetailVo detailVo : ls) {
            int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
            JSONObject jObj = (JSONObject) JSONObject.parse(detailVo.getOptionWeb());
            //字段为空
            if (jObj == null) {
                patients.add(detailVo.getPatientName());
                continue;
            }
            //相应月份为空
            if (jObj.get(month + "") == null) {
                patients.add(detailVo.getPatientName());
                continue;
            }
            //相应月份的图片个数为空(即上传后又被删除了)
            JSONArray jArray = (JSONArray) jObj.get(month + "");
            if (jArray.size() == 0) {
                patients.add(detailVo.getPatientName());
            }
        }
        if (patients.size() > 0)
            return patients;
        return null;
    }
}
