package com.ylz.bizDo.assessment.dao.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.bizDo.app.po.AppSignForm;
import com.ylz.bizDo.assessment.dao.AssessmentDetailDao;
import com.ylz.bizDo.assessment.po.*;
import com.ylz.bizDo.assessment.vo.*;
import com.ylz.bizDo.assessment.vo.view.AppEvaluationVo;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.DrPatientType;
import com.ylz.packcommon.common.comEnum.NoticesType;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zms on 2018/6/8.
 */
@Service("assessmentDetailDao")
@Transactional(rollbackFor = {Exception.class})
public class AssessmentDetailDaoImpl implements AssessmentDetailDao {

    @Autowired
    private SysDao sysDao;

    private Logger logger = LoggerFactory.getLogger(AssessmentDetailDaoImpl.class);

    private ReentrantLock lock = new ReentrantLock();

    @Override
    public void save(AssessmentDetail detail, AssessmentVo vo, Assessment assessment) throws Exception {
        if (StringUtils.isNotBlank(detail.getId())) {// 修改考核明细（如果是32考核项则不修改考核明细，而是修改团队共享）
            AssessmentDetail oldDetail = (AssessmentDetail) sysDao.getServiceDo().find(AssessmentDetail.class, detail.getId());
            if ("32".equals(oldDetail.getContentCode())) {
                int year = Calendar.getInstance().get(Calendar.YEAR);
                int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
                if (StringUtils.isNotBlank(vo.getActivityTime())) {// 补考核
                    year = Integer.parseInt(vo.getActivityTime().split("-")[0]);
                    month = Integer.parseInt(vo.getActivityTime().split("-")[1]);
                }
                AssessmentTeamShare oldShare = sysDao.getAssessmentTeamShareDao().getShare(assessment.getTeamId(), year, month);
                if (oldShare != null) {// 修改团队共享
                    String optionWeb = saveJson(detail.getOptionWeb(), oldShare.getOptionWeb());
                    saveTeamShare(assessment, vo, optionWeb);
                } else {// 新增团队共享
                    String optionWeb = saveJson(detail.getOptionWeb(), null);
                    saveTeamShare(assessment, vo, optionWeb);
                }
            } else {
                oldDetail.setScorePre(detail.getScorePre());
                oldDetail.setOptionWeb(saveJson(detail.getOptionWeb(), oldDetail.getOptionWeb()));
            }
            oldDetail.setFileName(detail.getFileName());
            oldDetail.setUpdateUserId(detail.getUpdateUserId());
            oldDetail.setUpdateUserName(detail.getUpdateUserName());
            oldDetail.setScoreAft(null);// 重置审核分
            oldDetail.setReason(null);// 清空审核原因
            sysDao.getServiceDo().modify(oldDetail);
            saveAssessLog(detail, "新上传[" + sysDao.getAssessmentContentDao().findAssessContents(oldDetail.getContentCode()).getShortContent() + "]照片");
        } else {// 新增考核明细
            if ("32".equals(detail.getContentCode())) {// 新增团队共享
                String optionWeb = saveJson(detail.getOptionWeb(), null);
                saveTeamShare(assessment, vo, optionWeb);
                detail.setOptionWeb(null);// 32考核项的图片在初始化的时候生成
            } else {
                detail.setOptionWeb(saveJson(detail.getOptionWeb(), null));
            }
            sysDao.getServiceDo().add(detail);
            // 返回主键ID，避免单考核项初次批量上传图片时会生成多条考核明细
            if ("11".equals(detail.getContentCode())) {
                vo.getDetailMap().get("11").setId(detail.getId());
            } else if ("13".equals(detail.getContentCode())) {
                vo.getDetailMap().get("13").setId(detail.getId());
            } else if ("31".equals(detail.getContentCode())) {
                vo.getDetailMap().get("31").setId(detail.getId());
            } else if ("32".equals(detail.getContentCode())) {
                vo.getDetailMap().get("32").setId(detail.getId());
            } else if ("44".equals(detail.getContentCode())) {
                vo.getDetailMap().get("44").setId(detail.getId());
            } else if ("46".equals(detail.getContentCode())) {
                vo.getDetailMap().get("46").setId(detail.getId());
            } else if ("48".equals(detail.getContentCode())) {
                vo.getDetailMap().get("48").setId(detail.getId());
            } else if ("410".equals(detail.getContentCode())) {
                vo.getDetailMap().get("410").setId(detail.getId());
            } else if ("411".equals(detail.getContentCode())) {
                vo.getDetailMap().get("411").setId(detail.getId());
            } else if ("412".equals(detail.getContentCode())) {
                vo.getDetailMap().get("412").setId(detail.getId());
            }
            saveAssessLog(detail, "上传[" + sysDao.getAssessmentContentDao().findAssessContents(detail.getContentCode()).getShortContent() + "]照片");
        }
    }

    @Override
    public void saveReview(ReviewVo vo) throws Exception {
        Assessment assessment = (Assessment) sysDao.getServiceDo().find(Assessment.class, vo.getAssessmentId());
        if (assessment == null) {
            throw new Exception("找不到对应的考核表");
        }
        for (AssessmentDetail detail : vo.getDetails()) {
            if (StringUtils.isBlank(detail.getId())) {
                continue;
            }
            AssessmentDetail entity = (AssessmentDetail) sysDao.getServiceDo().find(AssessmentDetail.class, detail.getId());
            entity.setScoreAft(detail.getScoreAft());//审核分数
            entity.setReason(detail.getReason());//原因
            entity.setUpdateUserId(detail.getUpdateUserId());//操作人
            entity.setUpdateUserName(detail.getUpdateUserName());
            sysDao.getServiceDo().modify(entity);
            saveReviewLog(entity, "审核[" + sysDao.getAssessmentContentDao().findAssessContents(entity.getContentCode()).getShortContent() + "]，修改分数为" + detail.getScoreAft() + "，原因：" + (entity.getReason() == null ? "" : entity.getReason()));
        }
        if (vo.getDetails().size() > 0) {
            // 修改后总分保存
            assessment.setTotalScoreAft(vo.getTotalScoreAft());
            // 审核状态设置为是
            if ("3".equals(vo.getHospLevel()) || "2".equals(vo.getHospLevel())) {
                // 区县以上级别审核
                assessment.setIsReview("1");
                // 设置审核次数
                assessment.setReviewNum(assessment.getReviewNum() == null ? 1 : assessment.getReviewNum() + 1);
            } else if ("4".equals(vo.getHospLevel())) {
                // 如果没有被审核过或者没有被区县审核过时才进行乡镇审核的相关操作
                if (assessment.getIsReview() == null || !"1".equals(assessment.getIsReview())) {
                    // 乡镇审核
                    assessment.setIsReview("2");
                    // 设置已审核次数
                    assessment.setReviewNum(assessment.getReviewNum() == null ? 1 : assessment.getReviewNum() + 1);
                }
            }
            sysDao.getServiceDo().modify(assessment);
            // 审核完成后发送消息提醒
            if (StringUtils.isNotBlank(assessment.getPatientId())) {
                AppPatientUser user = (AppPatientUser) sysDao.getServiceDo().find(AppPatientUser.class, assessment.getPatientId());
                if (user != null) {
                    if (StringUtils.isNotBlank(assessment.getSignId())) {
                        AppSignForm form = (AppSignForm) sysDao.getServiceDo().find(AppSignForm.class, assessment.getSignId());
                        if (form != null) {
                            sysDao.getAppNoticeDao().addNotices("绩效审核消息",
                                    "居民-" + user.getPatientName() + ",考核表已被审核。 您还有" + assessment.getIsReview() + "次修改机会",
                                    NoticesType.JXXX.getValue() + ",3", "系统提醒", form.getSignDrId(), "", DrPatientType.DR.getValue());
                        }
                    }
                }
            }
        }
    }

    @Override
    public double countScore(String assessmentId, List<String[]> groupList) throws Exception {
        Map<String, Object> map = new HashMap<>();
        StringBuilder commonSql = new StringBuilder();
        commonSql.append(" select * from assessment_detail where assessment_id = :assessmentId ");
        commonSql.append(" and content_code in (:contentCodes) ");
        map.put("assessmentId", assessmentId);
        double commonScorePre = 0;
        double groupOneScorePre = 0;
        double groupTwoScorePre = 0;
        double groupThreeScorePre = 0;
        // 公共考核项得分计算
        String[] commonCodes = {"11", "12", "13", "21", "31", "32", "41", "51"};// 公共考核项目
        map.put("contentCodes", commonCodes);
        List<AssessmentDetail> commonDetails = sysDao.getServiceDo().findSqlMap(commonSql.toString(), map, AssessmentDetail.class);
        for (AssessmentDetail detail : commonDetails) {
            commonScorePre += detail.getScorePre() == null ? 0 : detail.getScorePre().doubleValue();
        }
        // 个性化考核项得分计算
        if (groupList != null) {
            map.clear();
            map.put("assessmentId", assessmentId);
            map.put("contentCodes", groupList.get(0));
            List<AssessmentDetail> groupOneDetails = sysDao.getServiceDo().findSqlMap(commonSql.toString(), map, AssessmentDetail.class);
            for (AssessmentDetail detail : groupOneDetails) {
                groupOneScorePre += detail.getScorePre() == null ? 0 : detail.getScorePre().doubleValue();
            }
            if (groupList.size() > 1) {
                map.clear();
                map.put("assessmentId", assessmentId);
                map.put("contentCodes", groupList.get(1));
                List<AssessmentDetail> groupTwoDetails = sysDao.getServiceDo().findSqlMap(commonSql.toString(), map, AssessmentDetail.class);
                for (AssessmentDetail detail : groupTwoDetails) {
                    groupTwoScorePre += detail.getScorePre() == null ? 0 : detail.getScorePre().doubleValue();
                }
            }
            if (groupList.size() > 2) {
                map.clear();
                map.put("assessmentId", assessmentId);
                map.put("contentCodes", groupList.get(2));
                List<AssessmentDetail> groupThreeDetails = sysDao.getServiceDo().findSqlMap(commonSql.toString(), map, AssessmentDetail.class);
                for (AssessmentDetail detail : groupThreeDetails) {
                    groupThreeScorePre += detail.getScorePre() == null ? 0 : detail.getScorePre().doubleValue();
                }
            }
        }
        double maxScorePre = groupOneScorePre;
        if (groupTwoScorePre > maxScorePre) {
            maxScorePre = groupTwoScorePre;
        }
        if (groupThreeScorePre > maxScorePre) {
            maxScorePre = groupThreeScorePre;
        }
        return commonScorePre + maxScorePre;
    }

    @Override
    public double countScoreAft(String assessmentId, List<String[]> groupList) throws Exception {
        Map<String, Object> map = new HashMap<>();
        StringBuilder commonSql = new StringBuilder();
        commonSql.append(" select * from assessment_detail where assessment_id = :assessmentId ");
        commonSql.append(" and content_code in (:contentCodes) ");
        map.put("assessmentId", assessmentId);
        double commonScoreAft = 0;
        double groupOneScoreAft = 0;
        double groupTwoScoreAft = 0;
        double groupThreeScoreAft = 0;
        // 公共考核项得分计算
        String[] commonCodes = {"11", "12", "13", "21", "31", "32", "41", "51"};// 公共考核项目
        map.put("contentCodes", commonCodes);
        List<AssessmentDetail> commonDetails = sysDao.getServiceDo().findSqlMap(commonSql.toString(), map, AssessmentDetail.class);
        for (AssessmentDetail detail : commonDetails) {
            if (detail.getScoreAft() == null) {
                commonScoreAft += detail.getScorePre() == null ? 0 : detail.getScorePre().doubleValue();
            } else {
                commonScoreAft += detail.getScoreAft().doubleValue();
            }
        }
        // 个性化考核项得分计算
        if (groupList != null) {
            map.clear();
            map.put("assessmentId", assessmentId);
            map.put("contentCodes", groupList.get(0));
            List<AssessmentDetail> groupOneDetails = sysDao.getServiceDo().findSqlMap(commonSql.toString(), map, AssessmentDetail.class);
            for (AssessmentDetail detail : groupOneDetails) {
                if (detail.getScoreAft() == null) {
                    groupOneScoreAft += detail.getScorePre() == null ? 0 : detail.getScorePre().doubleValue();
                } else {
                    groupOneScoreAft += detail.getScoreAft().doubleValue();
                }
            }
            if (groupList.size() > 1) {
                map.clear();
                map.put("assessmentId", assessmentId);
                map.put("contentCodes", groupList.get(1));
                List<AssessmentDetail> groupTwoDetails = sysDao.getServiceDo().findSqlMap(commonSql.toString(), map, AssessmentDetail.class);
                for (AssessmentDetail detail : groupTwoDetails) {
                    if (detail.getScoreAft() == null) {
                        groupTwoScoreAft += detail.getScorePre() == null ? 0 : detail.getScorePre().doubleValue();
                    } else {
                        groupTwoScoreAft += detail.getScoreAft().doubleValue();
                    }
                }
            }
            if (groupList.size() > 2) {
                map.clear();
                map.put("assessmentId", assessmentId);
                map.put("contentCodes", groupList.get(2));
                List<AssessmentDetail> groupThreeDetails = sysDao.getServiceDo().findSqlMap(commonSql.toString(), map, AssessmentDetail.class);
                for (AssessmentDetail detail : groupThreeDetails) {
                    if (detail.getScoreAft() == null) {
                        groupThreeScoreAft += detail.getScorePre() == null ? 0 : detail.getScorePre().doubleValue();
                    } else {
                        groupThreeScoreAft += detail.getScoreAft().doubleValue();
                    }
                }
            }
        }
        double maxScoreAft = groupOneScoreAft;
        if (groupTwoScoreAft > maxScoreAft) {
            maxScoreAft = groupTwoScoreAft;
        }
        if (groupThreeScoreAft > maxScoreAft) {
            maxScoreAft = groupThreeScoreAft;
        }
        return commonScoreAft + maxScoreAft;
    }

    @Override
    public int countFinishNum(String assessmentId) throws Exception {
        int finishNum = 0;
        Map<String, Object> map = new HashMap<>();
        StringBuilder sql = new StringBuilder();
        sql.append(" select count(distinct(content_code)) finishNum from assessment_detail where assessment_id = :assessmentId ");
        map.put("assessmentId", assessmentId);
        List<Map> result = sysDao.getServiceDo().findSqlMap(sql.toString(), map);
        if (result != null && result.size() > 0) {
            finishNum = Integer.parseInt(result.get(0).get("finishNum").toString());
        }
        // 判断32考核项是否已经考核完成
        return finishNum;
    }

    @Override
    public AssessmentDetail delOption(AssessDetailQvo qvo) throws Exception {
        AssessmentDetail detail = (AssessmentDetail) sysDao.getServiceDo().find(AssessmentDetail.class, qvo.getId());
        if (detail == null) {
            throw new Exception("不存在该考核明细信息");
        }
        String jsonOption = "";
        if ("32".equals(detail.getContentCode())) {
            jsonOption = delJson32(detail, qvo);
        } else {
            jsonOption = delJson(detail, qvo);
        }
        detail.setOptionWeb(jsonOption);
        sysDao.getServiceDo().modify(detail);
        saveAssessLog(detail, "删除了[" + sysDao.getAssessmentContentDao().findAssessContents(detail.getContentCode()).getShortContent() + "]上传的一张照片");
        return detail;
    }

    @Override
    public void delOptions(AssessDetailQvo qvo) throws Exception {
        AssessmentDetail detail = (AssessmentDetail) sysDao.getServiceDo().find(AssessmentDetail.class, qvo.getId());
        if (detail == null) {
            throw new Exception("不存在该考核明细信息");
        }
        String jsonOption = "";
        if ("32".equals(detail.getContentCode())) {
            jsonOption = delJson32(detail, qvo);
        } else {
            jsonOption = delJsons(detail, qvo);
        }
        detail.setOptionWeb(jsonOption);
        sysDao.getServiceDo().modify(detail);
        saveAssessLog(detail, "删除了[" + sysDao.getAssessmentContentDao().findAssessContents(detail.getContentCode()).getShortContent() + "]上传的一张照片");
    }

    @Override
    public void commonAutoItem(interfaceQvo qvo, Assessment assessment) throws Exception {
        // 查询居民所属地区
        String cityCode = "";
        CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE, assessment.getSignAreaCode().substring(0, 4));
        if (value != null) {
            cityCode = value.getCodeTitle();
        }
        qvo.setUrlType(cityCode);

        // 12考核项
        Map<String, String> resultMap = new HashMap<>();// 保存接口返回的结果
        String resultDecryp = sysDao.getSecurityCardAsyncBean().findElectronicArchives(qvo, null, null, null);
        resultMap.put("12", resultDecryp);

        // 21考核项
        AssessReadJwVo vo = new AssessReadJwVo();
        vo.setAct("findVisitRecords");// 基卫刷卡记录
        vo.setUrlType(cityCode);
        vo.setIdcardno(qvo.getIdno());
        vo.setSignEndTime(qvo.getSignTimeEnd());
        vo.setSignStartTime(qvo.getSignTimeStart());
        resultDecryp = sysDao.getSecurityCardAsyncBean().findExecuteDetail(vo, null, null, null);
        resultMap.put("21", resultDecryp);

        // 公共项目自动生成明细保存
        saveCommonAutoDetail(resultMap, assessment);
    }

    /**
     * 公共项目自动生成明细保存
     */
    public void saveCommonAutoDetail(Map<String, String> resultMap, Assessment assessment) throws Exception {
        for (Map.Entry<String, String> entry : resultMap.entrySet()) {
            AssessmentDetail detail = new AssessmentDetail();
            String code = entry.getKey();
            JSONObject jObj = (JSONObject) JSONObject.parse(entry.getValue());
            if (jObj != null) {
                logger.info(code + ":" + jObj.get("message"));
                if ("12".equals(code)) {
                    detail.setAssessmentId(assessment.getId());
                    detail.setContentCode("12");
                    if ("1".equals(jObj.get("entity"))) {
                        detail.setScorePre(new BigDecimal(15));
                    } else {
                        detail.setScorePre(new BigDecimal(0));
                    }
                } else if ("21".equals(code)) {
                    detail.setAssessmentId(assessment.getId());
                    detail.setContentCode("21");
                    if ("1".equals(jObj.get("entity"))) {
                        detail.setScorePre(new BigDecimal(15));
                    } else {
                        detail.setScorePre(new BigDecimal(0));
                    }
                }
                detail.setSignAreaCode(assessment.getSignAreaCode());
                saveCommonAuto(detail);
            }
        }
    }

    public void saveCommonAuto(AssessmentDetail detail) throws Exception {
        String hql = " from AssessmentDetail where assessmentId = :assessmentId and contentCode = :contentCode ";
        Map<String, Object> map = new HashMap<>(2);
        map.put("assessmentId", detail.getAssessmentId());
        map.put("contentCode", detail.getContentCode());
        try {
            List<AssessmentDetail> details = sysDao.getServiceDo().findHqlMap(hql, map);
            if (details != null && details.size() > 0) {// 修改
                AssessmentDetail entity = details.get(0);
                entity.setScorePre(detail.getScorePre());// 只修改审核前分数
                sysDao.getServiceDo().modify(entity);
            } else {// 新增
                sysDao.getServiceDo().add(detail);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AssessmentDetail savePersonalDetail(String result, String act, String group, Assessment assessment, AssessmentDetail oldDetail) throws Exception {
        AssessmentDetail newDetail = new AssessmentDetail();
        JSONObject jObj = (JSONObject) JSONObject.parse(result);
        String entity = (String) jObj.get("entity");
        if (StringUtils.isNotBlank(entity)) {
            // 将接口返回的信息打印到logback.xml中配置的日志文件中
            logger.info(act + ":" + jObj.get("message").toString());
            newDetail.setAssessmentId(assessment.getId());
            newDetail.setSignAreaCode(assessment.getSignAreaCode());

            // 所有人群的公共考核项
            if ("".equals(act)) {
                newDetail.setContentCode("41");
                if ("1".equals(entity)) {// 有健康评估报告得15分
                    newDetail.setScorePre(new BigDecimal(15));
                } else {
                    newDetail.setScorePre(new BigDecimal(0));
                }
                addOrUpdateDetail(newDetail);
                return newDetail;
            }

            // 健康体检表
            if ("findHealthfile".equals(act)) {
                if ("health".equals(group)) {// 健康人群体检表
                    newDetail.setContentCode("42");
                    if ("1".equals(entity)) {
                        newDetail.setScorePre(new BigDecimal(15));
                    } else {
                        newDetail.setScorePre(new BigDecimal(0));
                    }
                    addOrUpdateDetail(newDetail);
                    return newDetail;
                } else if ("old".equals(group)) {// 老年人人群体检表
                    newDetail.setContentCode("43");
                    if ("1".equals(entity)) {
                        newDetail.setScorePre(new BigDecimal(10));
                    } else {
                        newDetail.setScorePre(new BigDecimal(0));
                    }
                    addOrUpdateDetail(newDetail);
                    return newDetail;
                }
            }

            // 孕产妇随访记录
            if ("findGravidaFollowUpRecords".equals(act)) {
                newDetail.setContentCode("45");
                double percent = Double.parseDouble(entity);
                if (new BigDecimal(percent).compareTo(new BigDecimal(1)) >= 0) {
                    newDetail.setScorePre(new BigDecimal(10));
                } else {
                    newDetail.setScorePre(new BigDecimal(0));
                }
                addOrUpdateDetail(newDetail);
                return newDetail;
            }

            // 孕产妇基卫产检记录
            if ("findAntenatalRecords".equals(act)) {
                newDetail.setContentCode("45");
                if ("1".equals(entity)) {
                    newDetail.setScorePre(new BigDecimal(5));
                } else {
                    newDetail.setScorePre(new BigDecimal(0));
                }
                addOrUpdateDetail(newDetail);
                return newDetail;
            }

            // 儿童随访记录
            if ("findChildFollowUpRecords".equals(act)) {
                newDetail.setContentCode("47");
                double percent = Double.parseDouble(entity);
                newDetail.setScorePre(new BigDecimal(10).multiply(new BigDecimal(percent)));
                addOrUpdateDetail(newDetail);
                return newDetail;
            }

            // 儿童健康检查记录表
            if ("findChildHealthRecords".equals(act)) {
                newDetail.setContentCode("47");
                if ("1".equals(entity)) {
                    newDetail.setScorePre(new BigDecimal(5));
                } else {
                    newDetail.setScorePre(new BigDecimal(0));
                }
                addOrUpdateDetail(newDetail);
                return newDetail;
            }

            // 慢性病随访记录（1-高血压，3-糖尿病）
            if ("findChronicDiseaseFollowUpRecords".equals(act)) {
                newDetail.setContentCode("49");
                int times = Integer.parseInt(entity);
                if (times <= 4) {
                    newDetail.setScorePre(new BigDecimal(2.5).multiply(new BigDecimal(times)));
                } else {
                    newDetail.setScorePre(new BigDecimal(10));
                }
                if (oldDetail == null) {// 先计算高血压得分
                    return newDetail;
                } else {// 后计算糖尿病得分
                    if (oldDetail.getScorePre().compareTo(newDetail.getScorePre()) >= 0) {// 如果高血压得分高于糖尿病得分则取高血压得分作为慢性病考核得分
                        addOrUpdateDetail(oldDetail);
                        return oldDetail;
                    } else {// 取糖尿病得分作为慢性病考核得分
                        addOrUpdateDetail(newDetail);
                        return newDetail;
                    }
                }
            }
        }
        return null;
    }

    public void savePersonal41Detail(Assessment assessment, String healthReport) throws Exception {
        AssessmentDetail newDetail = new AssessmentDetail();
        newDetail.setAssessmentId(assessment.getId());
        newDetail.setSignAreaCode(assessment.getSignAreaCode());
        newDetail.setContentCode("41");
        if ("1".equals(healthReport)) {// 有健康评估报告得15分
            newDetail.setScorePre(new BigDecimal(15));
        } else {
            newDetail.setScorePre(new BigDecimal(0));
        }
        addOrUpdateDetail(newDetail);
    }

    public void savePersonal42Detail(Assessment assessment, String readFileLog) throws Exception {
        AssessmentDetail newDetail = new AssessmentDetail();
        newDetail.setAssessmentId(assessment.getId());
        newDetail.setSignAreaCode(assessment.getSignAreaCode());
        newDetail.setContentCode("42");
        if ("1".equals(readFileLog)) {// 有调阅记录得15分
            newDetail.setScorePre(new BigDecimal(15));
        } else {
            newDetail.setScorePre(new BigDecimal(0));
        }
        addOrUpdateDetail(newDetail);
    }

    /**
     * 新增或修改考核明细
     */
    public void addOrUpdateDetail(AssessmentDetail detail) throws Exception {
        String hql = " from AssessmentDetail where assessmentId = :assessmentId and contentCode = :contentCode ";
        Map<String, Object> map = new HashMap<>(2);
        map.put("assessmentId", detail.getAssessmentId());
        map.put("contentCode", detail.getContentCode());
        List<AssessmentDetail> details = sysDao.getServiceDo().findHqlMap(hql, map);
        if (details != null && details.size() > 0) {// 修改
            details.get(0).setScorePre(detail.getScorePre());// 只修改审核前分数
            sysDao.getServiceDo().modify(details.get(0));
        } else {// 新增
            sysDao.getServiceDo().add(detail);
        }
    }

    @Override
    public void personalItem(interfaceQvo iQvo, AssessmentVo qvo, Assessment assessment) throws Exception {
        String[] groups = qvo.getGroups();
        for (String group : groups) {
            // personalCall("", group, iQvo, assessment);// 所有人群都需要考核的个性化考核项（41考核项）
            AppSignForm signForm = (AppSignForm) sysDao.getServiceDo().find(AppSignForm.class, assessment.getSignId());
            if (signForm == null) {
                throw new Exception("签约单数据异常!");
            }
            savePersonal41Detail(assessment, signForm.getSignHealthReportState());

            if ("health".equals(group)) {
                personalCall("findHealthfile", "health", iQvo, assessment, null);
                //String readFileLog = "1";
                //savePersonal42Detail(assessment, readFileLog);
            } else if ("young".equals(group)) {
                personalCall("findChildFollowUpRecords", "young", iQvo, assessment, null);
                //personalCall("findChildHealthRecords", "young", iQvo, assessment);
            } else if ("pregnant".equals(group)) {
                personalCall("findGravidaFollowUpRecords", "pregnant", iQvo, assessment, null);
                //personalCall("findAntenatalRecords", "pregnant", iQvo, assessment);
            } else if ("old".equals(group)) {
                personalCall("findHealthfile", "old", iQvo, assessment, null);
            } else if ("chronic".equals(group)) {
                // 慢性病随访记录（1-高血压，3-糖尿病）【分高血压和糖尿病，规则：取高血压或糖尿病中得分较高者的得分】
                AssessmentDetail detail = personalCall("findChronicDiseaseFollowUpRecords?1", "chronic", iQvo, assessment, null);
                personalCall("findChronicDiseaseFollowUpRecords?3", "chronic", iQvo, assessment, detail);
            }
        }
    }

    @Override
    public void teamShareInit(Assessment masterPo, AppSignForm signForm) throws Exception {
        int year = signForm.getSignFromDate().get(Calendar.YEAR);
        int month = signForm.getSignFromDate().get(Calendar.MONTH) + 1;
        List<AssessmentTeamShare> list = sysDao.getAssessmentTeamShareDao().listShare(masterPo.getTeamId(), year, month);
        if (list != null && list.size() > 0) {// 存在共享资源则同步到当前考核对象的考核表中
            // 组装optionWeb
            JSONObject jObj = new JSONObject(true);
            for (AssessmentTeamShare share : list) {
                if (StringUtils.isNotBlank(share.getOptionWeb())) {
                    jObj.put(share.getYear() + "-" + share.getMonth() + "", JSONArray.parse(share.getOptionWeb()));
                }
            }
            String optionWeb = jObj.toJSONString();

            // 查询并更新当前考核表的团队共享考核项
            String sql = " select * from assessment_detail where assessment_id = :assessmentId and content_code = :contentCode ";
            Map<String, Object> map = new HashMap<>();
            map.put("assessmentId", masterPo.getId());
            map.put("contentCode", "32");
            List<AssessmentDetail> details = sysDao.getServiceDo().findSqlMap(sql, map, AssessmentDetail.class);
            if (details != null && details.size() > 0) {// 修改
                AssessmentDetail detail = details.get(0);
                detail.setOptionWeb(optionWeb);
                // 计算分数
                if (list.size() > 2) {// 第三个月开始算分
                    if (list.size() > 12) {
                        detail.setScorePre(new BigDecimal(10));
                    } else {
                        detail.setScorePre(new BigDecimal(list.size() - 2));
                    }
                } else {
                    detail.setScorePre(new BigDecimal(0));
                }
                sysDao.getServiceDo().modify(detail);
            } else {// 新增
                AssessmentDetail detail = new AssessmentDetail();
                detail.setAssessmentId(masterPo.getId());
                detail.setContentCode("32");
                detail.setSignAreaCode(masterPo.getSignAreaCode());
                detail.setOptionWeb(optionWeb);
                // 计算分数
                if (list.size() > 2) {// 第三个月开始算分
                    detail.setScorePre(new BigDecimal(list.size() - 2));
                } else {
                    detail.setScorePre(new BigDecimal(0));
                }
                detail.setUpdateUserId(list.get(list.size() - 1).getUpdateUserId());
                detail.setUpdateUserName(list.get(list.size() - 1).getUpdateUserName());
                sysDao.getServiceDo().add(detail);
            }
        }
    }

    @Override
    public void appEvaluate(Assessment vo) throws Exception {
        AppSignForm signForm = (AppSignForm) sysDao.getServiceDo().find(AppSignForm.class, vo.getSignId());
        if (signForm == null) {
            throw new Exception("签约单数据异常!");
        }
        StringBuilder sql = new StringBuilder();
        sql.append(" select case when evaluation_score >= 13 then 10 when evaluation_score >=10 then 9 ");
        sql.append(" when evaluation_score >= 7  then 8 when evaluation_score >= 4  then 7 else 6 end evaluationScore ");
        sql.append(" from(select if (evaluation_competence is null or evaluation_competence = '', 0, evaluation_competence) + ");
        sql.append(" if (evaluation_service_attitude is null or evaluation_service_attitude = '', 0, evaluation_service_attitude) + ");
        sql.append(" if (evaluation_recovery_speed is null or evaluation_recovery_speed = '', 0, evaluation_recovery_speed) evaluation_score ");
        sql.append(" from app_dr_evaluation ");
        sql.append(" where evaluation_patient_id = :patientId ");
        sql.append(" and evaluation_dr_id = :drId ");
        sql.append(" and evaluation_date between :signFromDate and :signToDate ");
        sql.append(" order by evaluation_average desc ");
        sql.append(" limit 1) t ");
        Map<String, Object> map = new HashMap<>();
        map.put("patientId", vo.getPatientId());
        map.put("drId", vo.getDoctorId());
        map.put("signFromDate", signForm.getSignFromDate());
        map.put("signToDate", signForm.getSignToDate());
        List<AppEvaluationVo> list = sysDao.getServiceDo().findSqlMapRVo(sql.toString(), map, AppEvaluationVo.class);
        if (list != null && list.size() > 0) {
            appEvaluationSave(vo, list.get(0));// 保存评价明细
        } else {
            AppEvaluationVo evaluationVo = new AppEvaluationVo();
            evaluationVo.setEvaluationScore(new BigInteger("0"));
            appEvaluationSave(vo, evaluationVo);// 保存评价明细
        }
    }

    /**
     * 保存评价明细
     */
    public void appEvaluationSave(Assessment masterPo, AppEvaluationVo vo) throws Exception {
        String hql = " from AssessmentDetail where assessmentId = :assessmentId and contentCode = '51' ";
        Map<String, Object> map = new HashMap<>();
        map.put("assessmentId", masterPo.getId());
        List<AssessmentDetail> details = sysDao.getServiceDo().findHqlMap(hql, map);
        if (details != null && details.size() > 0) {// 修改
            AssessmentDetail detail = details.get(0);
            detail.setScorePre(new BigDecimal(vo.getEvaluationScore()));
            sysDao.getServiceDo().modify(detail);
        } else {// 新增
            AssessmentDetail detail = new AssessmentDetail();
            detail.setAssessmentId(masterPo.getId());
            detail.setContentCode("51");
            detail.setSignAreaCode(masterPo.getSignAreaCode());
            detail.setScorePre(new BigDecimal(vo.getEvaluationScore()));
            sysDao.getServiceDo().add(detail);
        }
    }

    // 个性化接口调用
    public AssessmentDetail personalCall(String act, String group, interfaceQvo iqvo, Assessment assessment, AssessmentDetail detail) throws Exception {
        String cityCode = "";
        CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE, assessment.getSignAreaCode().substring(0, 4));
        if (value != null) {
            cityCode = value.getCodeTitle();
        }
        iqvo.setUrlType(cityCode);
        AssessReadJwVo readJwVo = new AssessReadJwVo();
        readJwVo.setUrlType(cityCode);
        readJwVo.setIdcardno(iqvo.getIdno());
        readJwVo.setSignStartTime(iqvo.getSignTimeStart());
        readJwVo.setSignEndTime(iqvo.getSignTimeEnd());
        String resultDecryp = null;
        boolean flag = false;// 蛋疼的健康人群的接口乱套，设个标志位
        if (StringUtils.isBlank(act)) {
            // 需求说健康人群的所有人群接口是健康体检表的接口
            if ("health".equals(group)) {
                readJwVo.setAct("findHealthfile");
                flag = true;
                act = "findHealthfile";
                readJwVo.setType("1");
                resultDecryp = sysDao.getSecurityCardAsyncBean().findExecuteDetail(readJwVo, null, null, null);
            } else {
                // 需求说其他人群的所有人群接口是电子居民档案接口
                resultDecryp = sysDao.getSecurityCardAsyncBean().findElectronicArchives(iqvo, null, null, null);
            }
        } else {// 类型
            if ("health".equals(group)) {
                iqvo.setType("1");
                // 需求说其他人群的所有人群接口是电子居民档案接口
                resultDecryp = sysDao.getSecurityCardAsyncBean().findElectronicArchives(iqvo, null, null, null);
            } else {
                if ("old".equals(group)) {
                    readJwVo.setType("2");
                } else if ("chronic".equals(group)) {
                    readJwVo.setType(String.valueOf(act.charAt(act.length() - 1)));
                }
                if ("young".equals(group)) {
                    readJwVo.setChildName(iqvo.getPatientName());
                    readJwVo.setBirthday(iqvo.getBirthday());
                }
                if ("chronic".equals(group)) {
                    act = act.substring(0, act.length() - 2);
                    // 慢性病的方法做特殊处理
                    readJwVo.setAct(act);
                } else {
                    // 慢性病的方法做特殊处理
                    readJwVo.setAct(act);
                }
                resultDecryp = sysDao.getSecurityCardAsyncBean().findExecuteDetail(readJwVo, null, null, null);
            }
        }
        if (flag) act = "";// act重置为空，即所有人群
        AssessmentDetail retDetail = savePersonalDetail(resultDecryp, act, group, assessment, detail);
        return retDetail;
    }

    /**
     * 保存审核日志
     */
    public void saveReviewLog(AssessmentDetail po, String logContent) throws Exception {
        if (StringUtils.isBlank(po.getAssessmentId())) {
            throw new Exception("考核表主键为空!");
        }
        if (StringUtils.isBlank(po.getUpdateUserId())) {
            throw new Exception("操作人为空!");
        }
        ReviewLog reviewLog = new ReviewLog();
        reviewLog.setUpdateUserName(po.getUpdateUserName());
        reviewLog.setUpdateUserId(po.getUpdateUserId());
        reviewLog.setAssessmentId(po.getAssessmentId());
        reviewLog.setContent(logContent);
        reviewLog.setSignAreaCode(po.getSignAreaCode());
        sysDao.getServiceDo().add(reviewLog);
    }

    /**
     * 保存考核日志
     */
    public void saveAssessLog(AssessmentDetail po, String logContent) throws Exception {
        if (StringUtils.isBlank(po.getAssessmentId())) {
            throw new Exception("考核表主键为空!");
        }
        if (StringUtils.isBlank(po.getUpdateUserId())) {
            throw new Exception("操作人为空!");
        }
        AssessLog assessLog = new AssessLog();
        assessLog.setUpdateUserName(po.getUpdateUserName());
        assessLog.setUpdateUserId(po.getUpdateUserId());
        assessLog.setAssessmentId(po.getAssessmentId());
        assessLog.setContent(logContent);
        assessLog.setSignAreaCode(po.getSignAreaCode());
        sysDao.getServiceDo().add(assessLog);
    }

    public String saveJson32(String newOptionWeb, String oldOption) throws Exception {
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        JSONObject jObj = new JSONObject();
        JSONArray jArray = new JSONArray();
        if (StringUtils.isBlank(oldOption)) {// 新增
            jObj = new JSONObject();
            jArray = new JSONArray();
            jArray.add(newOptionWeb);
            jObj.put(month + "", jArray);
        } else {// 修改
            jObj = (JSONObject) JSONArray.parse(oldOption);
            JSONArray array = new JSONArray();
            // 修改里的新增
            if (jObj.get(month + "") == null) {
                array.add(newOptionWeb);
                jObj.put(month + "", array);
            } else {
                // 修改里的修改
                array = (JSONArray) jObj.get(month + "");
                array.add(newOptionWeb);
            }
        }
        return jObj.toJSONString();
    }

    public String saveJson(String newOptionWeb, String oldOptionWeb) throws Exception {
        JSONArray jArray = new JSONArray();
        if (StringUtils.isBlank(oldOptionWeb)) {// 新增
            jArray.add(newOptionWeb);
            return jArray.toJSONString();
        } else {// 修改
            jArray = (JSONArray) JSONArray.parse(oldOptionWeb);
            jArray.add(newOptionWeb);
            return jArray.toJSONString();
        }
    }

    public String delJson(AssessmentDetail detail, AssessDetailQvo qvo) throws Exception {
        String jsonData = detail.getOptionWeb();
        if (StringUtils.isBlank(jsonData)) return "";// 如果已为空就直接返回
        JSONArray jArray = (JSONArray) JSONArray.parse(jsonData);
        jArray.remove(qvo.getIndex().intValue());// 手动拆箱
        if (jArray.size() == 0) {
            Assessment assessment = (Assessment) sysDao.getServiceDo().find(Assessment.class, detail.getAssessmentId());
            assessment.setTotalScorePre(assessment.getTotalScorePre().subtract(detail.getScorePre()));// 考核表的总分跟着变化
            detail.setScorePre(new BigDecimal(0));// 如果没有图片，那么分数就置为0
            detail.setScoreAft(null);// 重置审核分
            detail.setReason(null);// 清空审核原因
            return "";
        }
        return jArray.toJSONString();
    }

    /**
     * 删除时Json处理（批量删除图片）
     */
    public String delJsons(AssessmentDetail detail, AssessDetailQvo qvo) throws Exception {
        String[] indexes = qvo.getIndexes().split(",");// 待删除图片的索引
        String optionWeb = detail.getOptionWeb();
        if (StringUtils.isBlank(optionWeb)) {// 没有图片需要删除
            return "";
        }
        JSONArray oldArray = (JSONArray) JSONArray.parse(optionWeb);
        JSONArray newArray = new JSONArray();
        for (int i = 0; i < oldArray.size(); i++) {
            boolean needDel = false;
            for (String index : indexes) {
                if (i == Integer.parseInt(index)) {// 图片需要删除
                    needDel = true;
                    break;
                }
            }
            if (!needDel) {
                newArray.add(oldArray.get(i));
            }
        }
        if (newArray.size() == 0) {
            Assessment assessment = (Assessment) sysDao.getServiceDo().find(Assessment.class, detail.getAssessmentId());
            assessment.setTotalScorePre(assessment.getTotalScorePre().subtract(detail.getScorePre()));// 考核表的总分跟着变化
            detail.setScorePre(new BigDecimal(0));//如果没有图片，那么分数就置为0
            return "";
        }
        return newArray.toJSONString();
    }

    /**
     * 删除32考核项JSON处理
     */
    public String delJson32(AssessmentDetail detail, AssessDetailQvo qvo) throws Exception {
        String jsonData = detail.getOptionWeb();
        JSONObject jObj = (JSONObject) JSONArray.parse(jsonData);
        JSONArray jArray = (JSONArray) jObj.get(qvo.getMonth());
        jArray.remove(qvo.getIndex().intValue());//手动拆箱
        // 上传了2个月后分数才能减，即从第三个月开始算起，如果过第三个月上传了，则分数为+1，删除则分数为-1
        if (jArray.size() == 0 && jObj.size() > 2) {
            detail.setScorePre(detail.getScorePre().subtract(new BigDecimal(1)));//分数减去1
            Assessment assessment = (Assessment) sysDao.getServiceDo().find(Assessment.class, detail.getAssessmentId());
            assessment.setTotalScorePre(assessment.getTotalScorePre().subtract(new BigDecimal(1)));
            sysDao.getServiceDo().modify(assessment);//考核表总分改变
        }
        return jObj.toJSONString();
    }

    /**
     * 保存、修改团队共享
     */
    public AssessmentTeamShare saveTeamShare(Assessment masterPo, AssessmentVo vo, String optionWeb) throws Exception {
        lock.lock();// 共享变量上锁
        try {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            if (StringUtils.isNotBlank(vo.getActivityTime())) {// 补考核
                year = Integer.parseInt(vo.getActivityTime().split("-")[0]);
                month = Integer.parseInt(vo.getActivityTime().split("-")[1]);
            }
            AssessmentTeamShare teamShare = sysDao.getAssessmentTeamShareDao().getShare(masterPo.getTeamId(), year, month);
            if (teamShare == null) {// 新增
                teamShare = new AssessmentTeamShare();
                teamShare.setTeamId(masterPo.getTeamId());
                teamShare.setYear(year);
                teamShare.setMonth(month);
                teamShare.setOptionWeb(optionWeb);
                teamShare.setUpdateUserId(masterPo.getUpdateUserId());
                teamShare.setUpdateUserName(masterPo.getUpdateUserName());
                teamShare.setSignAreaCode(masterPo.getSignAreaCode());
                sysDao.getServiceDo().add(teamShare);
            } else {// 修改
                sysDao.getServiceDo().getSessionFactory().getCurrentSession().evict(teamShare);
                teamShare.setOptionWeb(optionWeb);
                teamShare.setUpdateUserId(masterPo.getUpdateUserId());
                teamShare.setUpdateUserName(masterPo.getUpdateUserName());
                sysDao.getServiceDo().modify(teamShare);
            }
            return teamShare;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();// 释放锁
        }
        return null;
    }

    /**
     * 同步团队共享
     */
    public void syncTeamShare(final AssessmentTeamShare teamShare, final double scorePre) throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String sql = " select t1.* from assessment_detail t1, assessment_team_share t2, assessment t3 " +
                        " where t2.team_id = t3.team_id and t1.assessment_id = t3.id and t1.content_code = '32' " +
                        " and t3.team_id = :teamId ";
                Map<String, Object> map = new HashMap<>();
                map.put("teamId", teamShare.getTeamId());
                List<AssessmentDetail> details = sysDao.getServiceDo().findSqlMap(sql, map, AssessmentDetail.class);
                List<String> detailIds = new ArrayList<>();// 考核明细id集合
                if (details != null && details.size() > 0) {
                    for (AssessmentDetail detail : details) {
                        detailIds.add(detail.getId());
                    }
                }
                // 更新
                String updateSql = " update assessment_detail set option_Web = :optionWeb, score_pre = :scorePre where id in (:detailIds) ";
                Map<String, Object> updateMap = new HashMap<>();
                updateMap.put("optionWeb", teamShare.getOptionWeb());
                updateMap.put("scorePre", scorePre);
                updateMap.put("detailIds", detailIds.toArray());
                sysDao.getServiceDo().update(updateSql, updateMap);
            }
        }).start();
    }

    /**
     * 根据考核ID和考核项编码查找考核明细记录
     */
    @Override
    public AssessmentDetail findAssessmentDetail(String assessmentId, String contentCode) throws Exception {
        String sql = "select * from assessment_detail where assessment_id = :assessmentId and content_code = :contentCode ";
        Map<String, Object> map = new HashMap<>();
        map.put("assessmentId", assessmentId);
        map.put("contentCode", contentCode);
        List<AssessmentDetail> details = sysDao.getServiceDo().findSqlMap(sql, map, AssessmentDetail.class);
        if (details != null && details.size() > 0) {
            return details.get(0);
        }
        return null;
    }

    /**
     * 根据考核ID和考核项编码查找考核明细记录
     */
    @Override
    public List<AssessmentDetail> listAssessmentDetail(String assessmentId, String contentCode) throws Exception {
        StringBuilder sql = new StringBuilder();
        if (StringUtils.isBlank(assessmentId) && StringUtils.isNotBlank(contentCode)) {
            sql.append(" select count(assessment_id) assessNum, a.* from assessment_detail a where 1 = 1 ");
        } else {
            sql.append(" select a.* from assessment_detail a where 1 = 1 ");
        }
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNotBlank(assessmentId)) {
            sql.append(" and a.assessment_id = :assessmentId ");
            map.put("assessmentId", assessmentId);
        }
        if (StringUtils.isNotBlank(contentCode)) {
            sql.append(" and a.content_code = :contentCode ");
            map.put("contentCode", contentCode);
        }
        if (StringUtils.isBlank(assessmentId) && StringUtils.isNotBlank(contentCode)) {
            sql.append(" group by a.assessment_id having assessNum > 1 ");
        } else {
            sql.append(" order by a.hs_update_time desc ");
        }
        List<AssessmentDetail> details = sysDao.getServiceDo().findSqlMap(sql.toString(), map, AssessmentDetail.class);
        return details;
    }
}
