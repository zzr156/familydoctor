package com.ylz.view.sign.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionContext;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.assessment.po.Assessment;
import com.ylz.bizDo.assessment.po.AssessmentDetail;
import com.ylz.bizDo.assessment.po.AssessmentTeamShare;
import com.ylz.bizDo.assessment.vo.*;
import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.bizDo.cd.vo.CdAddressSvo;
import com.ylz.bizDo.web.vo.WebHealthReportVo;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.exception.ActionException;
import com.ylz.packcommon.common.util.Base64Utils;
import com.ylz.packcommon.common.util.ExcelUtil;
import com.ylz.packcommon.common.util.ExtendDate;
import com.ylz.packcommon.common.util.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.BeanUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;
import java.util.List;

import static com.ylz.packcommon.common.util.HttpPostUtils.doPostJson;

/**
 * Created by zms on 2018/6/5.
 */
@Action(
        value = "assessmentAction",
        results = {
                @Result(name = "json", type = "json", params = {"root", "jsons", "contentType", "application/json"}),
                @Result(name = "jsonFile", type = "json", params = {"root", "jsons", "contentType", "text/html"}),
                @Result(name = "jsonLayui", type = "json", params = {"root", "jsonLayui", "excludeNullProperties", "true", "contentType", "application/json"})
        }
)
public class AssessmentAction extends CommonAction {

    /**
     * 上传佐证相关属性
     */
    private List<File> fileWorkRecordComplete;// 档案齐全（11考核项）
    private List<String> fileWorkRecordCompleteFileName;
    private List<File> fileWorkValidTalk;// 有效沟通（13考核项）
    private List<String> fileWorkValidTalkFileName;
    private List<File> fileTeamConsult;// 健康咨询（31考核项）
    private List<String> fileTeamConsultFileName;
    private List<File> fileTeamCommAct;// 社区团队活动（32考核项）
    private List<String> fileTeamCommActFileName;
    private List<File> fileOld;// 老年人随访（44考核项）
    private List<String> fileOldFileName;
    private List<File> fileYoung;// 儿童随访（48考核项）
    private List<String> fileYoungFileName;
    private List<File> filePregnant;// 孕产妇随访（46考核项）
    private List<String> filePregnantFileName;
    private List<File> fileChronic;// 慢性病随访（410考核项）
    private List<String> fileChronicFileName;
    private List<File> filePsychosis;// 结核病、严重精神障碍随访（411考核项）
    private List<String> filePsychosisFileName;
    private List<File> filePsychosisRecord;// 结核病、严重精神障碍服务记录（412考核项）
    private List<String> filePsychosisRecordFileName;


    /**
     * 查询下级区域
     */
    public String queryCommList() {
        AssessListQvo qvo = (AssessListQvo) getJson(AssessListQvo.class);
        if (qvo != null) {
            try {
                List<CdAddressSvo> list = sysDao.getCdAddressDao().findById(qvo.getAreaCode());
                this.getJsons().setRows(list);
                this.getJsons().setCode("800");
            } catch (Exception e) {
                e.printStackTrace();
                this.getJsons().setCode("900");
                this.getJsons().setMsg(e.getMessage());
            }
        } else {
            this.getJsons().setCode("900");
            this.getJsons().setMsg("参数格式错误!");
        }
        return "json";
    }

    /**
     * 查询某个签约单对应的考核表的考核信息、考核明细信息、所属人群、考核记录、审核记录
     */
    public String commList() {
        AssessmentVo qvo = (AssessmentVo) getJson(AssessmentVo.class);
        if (qvo != null) {
            try {
                // 考核信息、考核明细信息
                List<Assessment> assessmentList = sysDao.getServiceDo().loadByPk(Assessment.class, "signId", qvo.getSignId());
                String assessmentId = null;
                if (assessmentList != null && assessmentList.size() > 0) {
                    assessmentId = assessmentList.get(0).getId();
                    List<AssessmentDetail> detailList = sysDao.getServiceDo().loadByPk(AssessmentDetail.class, "assessmentId", assessmentId);
                    this.getJsons().setVo(assessmentList.get(0));
                    this.getJsons().setRows(detailList);
                }
                // 所属人群
                List<AssessmentGroup> groupList = sysDao.getAssessmentDao().findGroup(qvo);
                List<String> groups = new ArrayList<>();
                if (groupList != null && groupList.size() > 0) {
                    for (AssessmentGroup group : groupList) {
                        groups.add(group.getLabelGroup());
                    }
                }
                this.getJsons().setQvo(groups.toArray());
                // 考核、审核操作记录
                Map<String, Object> map = new HashMap<>();
                if (StringUtils.isNotBlank(assessmentId)) {
                    map.put("assessLog", sysDao.getAssessLogDao().findLogList(assessmentId));
                    map.put("reviewLog", sysDao.getReviewLogDao().findLogList(assessmentId));
                }
                this.getJsons().setMap(map);
                this.getJsons().setCode("800");
            } catch (Exception e) {
                e.printStackTrace();
                this.getJsons().setCode("900");
                this.getJsons().setMsg(e.getMessage());
            }
        } else {
            this.getJsons().setCode("900");
            this.getJsons().setMsg("参数格式错误!");
        }
        return "json";
    }

    /**
     * 绩效考核主查询
     */
    public String assessList() {
        AssessListQvo qvo = (AssessListQvo) this.getJsonLay(AssessListQvo.class);
        if (qvo != null) {
            try {
                // 考核列表
                List<AssessListVo> list = sysDao.getAssessmentDao().findAssessList(qvo);
                // 某个机构下已经考核的签约单数量（弃用该功能）
                //String numberMsg = sysDao.getAssessmentDao().findAssess(qvo.getHospId());
                //this.getJsonLayui().setMsg(numberMsg);
                this.getJsonLayui().setData(list);
                this.getJsonLayui().setCount(qvo.getItemCount());
            } catch (Exception e) {
                e.printStackTrace();
                this.getJsons().setCode("900");
                this.getJsons().setMsg(e.getMessage());
            }
        } else {
            this.getJsons().setCode("900");
            this.getJsons().setMsg("参数格式错误!");
        }
        return "jsonLayui";
    }

    /**
     * 初始化考核表
     */
    public String initAssess() {
        AssessmentVo qvo = (AssessmentVo) getJson(AssessmentVo.class);
        if (qvo != null) {
            try {
                // 接口参数
                interfaceQvo iQvo = sysDao.getAssessmentDao().findInterceParams(qvo);

                // 不存在则生成考核表，存在则查询考核表
                Assessment assessment = null;
                if (StringUtils.isBlank(qvo.getAssessId())) {
                    assessment = createAssessment(qvo);// 生成考核表
                } else {
                    assessment = (Assessment) sysDao.getServiceDo().find(Assessment.class, qvo.getAssessId());// 查询考核表
                }

                // 查询签约单
                AppSignForm signForm = (AppSignForm) sysDao.getServiceDo().find(AppSignForm.class, assessment.getSignId());
                if (signForm == null) {
                    throw new Exception("签约单数据异常!");
                }

                // 解决有健康评估报告考核时却没有的问题
                WebHealthReportVo vo = new WebHealthReportVo();
                vo.setSignLableId(assessment.getSignId());
                AppHealthReport healthReport = sysDao.getAppHealthAssessmentDao().findHealthReport(vo);
                if (healthReport != null) {
                    if (!"1".equals(signForm.getSignHealthReportState())) {
                        signForm.setSignHealthReportState("1");// 已生成健康评估报告
                        sysDao.getServiceDo().modify(signForm);
                    }
                }

                // 用户评价考核（手机APP）
                sysDao.getAssessmentDetailDao().appEvaluate(assessment);
                // 团队共享考核
                sysDao.getAssessmentDetailDao().teamShareInit(assessment, signForm);
                // 公共自动生成考核
                sysDao.getAssessmentDetailDao().commonAutoItem(iQvo, assessment);
                // 个性化自动生成考核
                sysDao.getAssessmentDetailDao().personalItem(iQvo, qvo, assessment);

                // 更新考核表
                assessment.setDetailNum(qvo.getDetailNum());// 更新考核项总数
                List<String[]> itemList = getItemsByGroup(qvo);
                updateAssessment(assessment, itemList);// 更新考核表
                this.getJsons().setCode("800");
            } catch (Exception e) {
                e.printStackTrace();
                this.getJsons().setCode("900");
                this.getJsons().setMsg(e.getMessage());
            }
        } else {
            this.getJsons().setCode("900");
            this.getJsons().setMsg("参数格式错误!");
        }
        return "json";
    }

    /**
     * 保存考核表（考核表和考核明细表的保存没有在同一个事务中）
     */
    public String saveAssessment() {
        AssessmentVo vo = (AssessmentVo) getJson(AssessmentVo.class);
        if (vo != null) {
            try {
                // 上传文件前处理
                String result = uploadFiles();
                // 上传文件（调用图片上传接口）
                List<Map<String, String[]>> backFilePaths = remoteUpload(result);
                // 查询考核表
                Assessment assessment = (Assessment) sysDao.getServiceDo().find(Assessment.class, vo.getAssessId());
                // 更新考核明细信息
                saveAssessmentDetail(assessment, vo, backFilePaths);
                // 更新考核信息（更新考核总得分和已完成的项目数量）
                List<String[]> itemList = getItemsByGroup(vo);
                modifyAssessment(assessment, itemList);
                this.getJsons().setCode("800");
            } catch (Exception e) {
                e.printStackTrace();
                this.getJsons().setCode("900");
                this.getJsons().setMsg(e.getMessage());
            }
        } else {
            this.getJsons().setCode("900");
            this.getJsons().setMsg("参数格式错误!");
        }
        return "jsonFile";
    }

    /**
     * 保存考核完成状态
     */
    public String finishSave() {
        AssessmentVo qvo = (AssessmentVo) getJson(AssessmentVo.class);
        if (qvo != null) {
            try {
                Assessment assessment = (Assessment) sysDao.getServiceDo().find(Assessment.class, qvo.getAssessId());
                if (assessment == null) {
                    throw new Exception("考核表不存在!");
                }
                assessment.setIsFinish("1");
                sysDao.getServiceDo().modify(assessment);
                this.getJsons().setMsg("已标记为 考核完成！");
                this.getJsons().setCode("800");
            } catch (Exception e) {
                e.printStackTrace();
                this.getJsons().setCode("900");
                this.getJsons().setMsg("保存出错!");
            }
        } else {
            this.getJsons().setCode("900");
            this.getJsons().setMsg("参数格式错误!");
        }
        return "json";
    }

    /**
     * 查询协议到期前一个月还未考核的人数
     */
    public String findNotAssess() {
        AssessmentVo qvo = (AssessmentVo) getJson(AssessmentVo.class);
        if (qvo != null) {
            try {
                List<AssessmentVo> list = sysDao.getAssessmentDao().findNotAssess(qvo.getHospId());
                int notAssessNum = 0;
                if (list != null && list.size() > 0) {
                    notAssessNum = list.get(0).getNotAssessNum().intValue();
                }
                this.getJsons().setTotal(notAssessNum);
            } catch (Exception e) {
                e.printStackTrace();
                this.getJsons().setCode("900");
                this.getJsons().setMsg(e.getMessage());
            }
        } else {
            this.getJsons().setCode("900");
            this.getJsons().setMsg("参数格式错误!");
        }
        return "json";
    }

    /**
     * 每月需上传佐证的考核项目，提前一周提醒还有那些人还未考核
     */
    public String findNotAssessDetail() {
        AssessmentVo qvo = (AssessmentVo) getJson(AssessmentVo.class);
        if (qvo != null) {
            try {
                List<String> patients = sysDao.getAssessmentDao().findNotAssessDetail(qvo);
                this.getJsons().setVo(patients);
                this.getJsons().setCode("800");
            } catch (Exception e) {
                e.printStackTrace();
                this.getJsons().setCode("900");
                this.getJsons().setMsg(e.getMessage());
            }
        } else {
            this.getJsons().setCode("900");
            this.getJsons().setMsg("参数格式错误!");
        }
        return "json";
    }

    /**
     * 下载文件
     */
    public String download() throws Exception {
        String filePath = ServletActionContext.getRequest().getParameter("filePath");
        String fileName = ServletActionContext.getRequest().getParameter("fileName");
        AssessFileVo vo = new AssessFileVo();
        vo.setFileName(fileName);
        vo.setFilePath(filePath);
        sysDao.getAssessmentDao().downLoad(vo);
        return null;
    }

    /**
     * 删除图片、附件
     */
    public String delDetailOption() {
        AssessDetailQvo qvo = (AssessDetailQvo) getJson(AssessDetailQvo.class);
        if (qvo != null) {
            try {
                AssessmentDetail detail = sysDao.getAssessmentDetailDao().delOption(qvo);
                this.getJsons().setCode("800");
                this.getJsons().setResult(detail.getOptionWeb());
                this.getJsons().setMsg(qvo.getIndex().toString());// 删除的索引返回给前端
            } catch (Exception e) {
                e.printStackTrace();
                this.getJsons().setCode("900");
                this.getJsons().setMsg(e.getMessage());
            }
        } else {
            this.getJsons().setCode("900");
            this.getJsons().setMsg("参数格式错误!");
        }
        return "json";
    }

    /**
     * 审核保存
     */
    public String reviewSave() {
        ReviewVo vo = (ReviewVo) getJson(ReviewVo.class);
        if (vo != null) {
            try {
                String userId = this.getRequest().getSession().getAttribute("UserId").toString();
                String userName = this.getRequest().getSession().getAttribute("UserName").toString();
                for (AssessmentDetail detail : vo.getDetails()) {
                    detail.setUpdateUserId(userId);
                    detail.setUpdateUserName(userName);
                }
                sysDao.getAssessmentDetailDao().saveReview(vo);
                this.getJsons().setCode("800");
            } catch (Exception e) {
                e.printStackTrace();
                this.getJsons().setCode("900");
                this.getJsons().setMsg(e.getMessage());
            }
        } else {
            this.getJsons().setCode("900");
            this.getJsons().setMsg("参数格式错误!");
        }
        return "json";
    }

    /**
     * 审核退回
     */
    public String retreat() {
        ReviewVo vo = (ReviewVo) getJson(ReviewVo.class);
        if (vo != null) {
            try {
                String userId = this.getRequest().getSession().getAttribute("UserId").toString();
                String userName = this.getRequest().getSession().getAttribute("UserName").toString();
                vo.setUpdateUserId(userId);
                vo.setUpdateUserName(userName);
                int result = sysDao.getAssessmentDao().treatAssess(vo);
                this.getJsons().setResult(String.valueOf(result));
                this.getJsons().setCode("800");
            } catch (Exception e) {
                e.printStackTrace();
                this.getJsons().setCode("900");
                this.getJsons().setMsg(e.getMessage());
            }
        } else {
            this.getJsons().setCode("900");
            this.getJsons().setMsg("参数格式错误!");
        }
        return "json";
    }

    /**
     * 导出绩效考核审核列表
     */
    public String exportAuditList() {
        AssessListQvo qvo = (AssessListQvo) this.getJsonLay(AssessListQvo.class);
        if (qvo == null) {
            qvo = new AssessListQvo();
        }
        String type = getRequest().getParameter("typeNum");
        String numberCount = getRequest().getParameter("numberCount");
        if (type.equals("2")) {
            qvo.setPageSize(99999999);
        } else {
            qvo.setPageSize(Integer.valueOf(numberCount));
        }
        try {
            List<AssessListVo> list = sysDao.getAssessmentDao().findAssessList(qvo);
            String sheetName = "绩效考核统计表";// Sheet名称
            String mainTitle = "绩效考核统计";// 主标题
            if (StringUtils.isNotBlank(qvo.getRating())) {
                if ("0".equals(qvo.getRating())) {
                    mainTitle = "绩效考核统计（不合格）";
                } else if ("1".equals(qvo.getRating())) {
                    mainTitle = "绩效考核统计（合格）";
                } else if ("2".equals(qvo.getRating())) {
                    mainTitle = "绩效考核统计（良）";
                } else {
                    mainTitle = "绩效考核统计（优）";
                }
            }
            String subTitle = "日期：" + ExtendDate.getChineseYMD(Calendar.getInstance());// 副标题
            String[] headers = {"序号", "姓名", "签约编号", "性别", "年龄", "身份证号", "签约时间", "考核完成", "到期日期", "当前得分", "当前评级"};
            String[] datasetNames = {"name", "signNum", "sex", "age", "idno", "signDate", "isFinish", "signToDate", "totalScore", "rating"};
            getResponse().reset();
            getResponse().setContentType("application/vnd..ms-excel");
            getResponse().setHeader("content-Disposition", "attachment;filename=" + URLEncoder.encode("绩效考核统计信息.xls", "utf-8"));
            ExcelUtil<AssessListVo> excelUtil = new ExcelUtil<AssessListVo>();
            excelUtil.exportExcelStyleOne(sheetName, mainTitle, subTitle, headers, datasetNames, list, getResponse().getOutputStream(), "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 导出绩效考核统计报表
     */
    public String exportStatisticReport() {
        AssessListQvo qvo = (AssessListQvo) this.getJsonLay(AssessListQvo.class);
        if (qvo == null) {
            qvo = new AssessListQvo();
        }
        qvo.setPageSize(99999999);
        try {
            List<AssessReportVo> list = generateReportData(qvo);
            String sheetName = "绩效考核统计报表";// Sheet名称
            String mainTitle = URLDecoder.decode(getRequest().getParameter("reportTitle"), "UTF-8") + "绩效考核报表";// 主标题
            String subTitle = "日期：" + ExtendDate.getChineseYMD(Calendar.getInstance());// 副标题
            String[] headers = {"序号", "地区/机构/团队/个人", "签约数", "已考核数", "优", "良", "合格", "不合格", "绩效金额"};
            String[] datasetNames = {"statisticObjName", "signNum", "assessNum", "excellentNum", "goodNum", "qualifiedNum", "unQualifiedNum", "subsidy"};
            getResponse().reset();
            getResponse().setContentType("application/vnd..ms-excel");
            getResponse().setHeader("content-Disposition", "attachment;filename=" + URLEncoder.encode("绩效考核统计报表.xls", "utf-8"));
            ExcelUtil<AssessReportVo> excelUtil = new ExcelUtil<AssessReportVo>();
            excelUtil.exportExcelStyleOne(sheetName, mainTitle, subTitle, headers, datasetNames, list, getResponse().getOutputStream(), "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询所属人群对应的考核项
     */
    private List<String[]> getItemsByGroup(AssessmentVo qvo) {
        List<String[]> itemList = new ArrayList<>();
        String[] healthItems = {"42"};
        String[] oldItems = {"43", "44"};
        String[] pregnantItems = {"45", "46"};
        String[] youngItems = {"47", "48"};
        String[] chronicItems = {"49", "410"};
        String[] psychosisItems = {"411", "412"};
        for (String group : qvo.getGroups()) {
            if ("health".equals(group)) {
                itemList.add(healthItems);
            } else if ("young".equals(group)) {
                itemList.add(youngItems);
            } else if ("pregnant".equals(group)) {
                itemList.add(pregnantItems);
            } else if ("old".equals(group)) {
                itemList.add(oldItems);
            } else if ("chronic".equals(group)) {
                itemList.add(chronicItems);
            } else if ("psychosis".equals(group)) {
                itemList.add(psychosisItems);
            }
        }
        return itemList;
    }

    /**
     * 生成考核表
     */
    public Assessment createAssessment(AssessmentVo vo) throws Exception {
        Assessment assessment = new Assessment();
        assessment.setSignId(vo.getSignId());
        assessment.setPatientId(vo.getPatientId());
        assessment.setDoctorId(vo.getDrId());
        assessment.setHospId(vo.getHospId());
        assessment.setTeamId(vo.getTeamId());
        assessment.setDetailNum(vo.getDetailNum());
        assessment.setFinishNum(0);
        assessment.setIsFinish("0");
        assessment.setIsReview("0");
        assessment.setTotalScoreAft(new BigDecimal(0));
        assessment.setTotalScorePre(new BigDecimal(0));
        assessment.setSaveNum(0);
        assessment.setReviewNum(0);
        assessment.setIsExtract("0");
        assessment.setState("0");
        // 操作人ID、姓名
        String userId = this.getRequest().getSession().getAttribute("UserId").toString();
        String userName = this.getRequest().getSession().getAttribute("UserName").toString();
        assessment.setUpdateUserId(userId);
        assessment.setUpdateUserName(userName);
        AppSignForm form = (AppSignForm) sysDao.getServiceDo().find(AppSignForm.class, vo.getSignId());
        if (form == null) {
            throw new Exception("签约单数据异常!");
        }
        form.setSignAssesState("1");// 已生成考核记录
        sysDao.getServiceDo().modify(form);
        assessment.setSignAreaCode(form.getSignAreaCode());
        sysDao.getServiceDo().add(assessment);
        return assessment;
    }

    /**
     * 更新考核表（初始化考核表后更新）
     */
    public Assessment updateAssessment(Assessment assessment, List<String[]> groupList) throws Exception {
        // 统计考核总得分
        double totalScorePre = sysDao.getAssessmentDetailDao().countScore(assessment.getId(), groupList);
        assessment.setTotalScorePre(new BigDecimal(totalScorePre));

        // 统计审核总得分
        double totalScoreAft = sysDao.getAssessmentDetailDao().countScoreAft(assessment.getId(), groupList);
        assessment.setTotalScoreAft(new BigDecimal(totalScoreAft));

        // 统计已考核项数量（没有用到这个字段，所以先注释掉）
        /*int finishNum = sysDao.getAssessmentDetailDao().countFinishNum(assessment.getId());
        assessment.setFinishNum(finishNum);*/

        // 解决少部分已考核的签约单的考核状态显示为未考核的问题
        AppSignForm form = (AppSignForm) sysDao.getServiceDo().find(AppSignForm.class, assessment.getSignId());
        if (form == null) {
            throw new Exception("签约单数据异常!");
        }
        if (!"1".equals(form.getSignAssesState())) {
            form.setSignAssesState("1");// 已生成考核记录
            sysDao.getServiceDo().modify(form);
        }

        sysDao.getServiceDo().modify(assessment);
        return assessment;
    }

    /**
     * 更新考核表（上传佐证后更新）
     */
    public Assessment modifyAssessment(Assessment assessment, List<String[]> groupList) throws Exception {
        assessment.setIsExtract("0");// 抽取状态重置为0
        assessment.setState("0");// 退回状态重新置为0

        // 统计考核总得分
        double totalScorePre = sysDao.getAssessmentDetailDao().countScore(assessment.getId(), groupList);
        assessment.setTotalScorePre(new BigDecimal(totalScorePre));

        // 统计审核总得分
        double totalScoreAft = sysDao.getAssessmentDetailDao().countScoreAft(assessment.getId(), groupList);
        assessment.setTotalScoreAft(new BigDecimal(totalScoreAft));

        // 统计已考核项数量（没有用到这个字段，所以先注释掉）
        /*int finishNum = sysDao.getAssessmentDetailDao().countFinishNum(assessment.getId());
        assessment.setFinishNum(finishNum);*/

        // 审核后保存修改次数（区县审核后只能修改1次，乡镇审核后只能修改两次）
        if (StringUtils.isNotBlank(assessment.getIsReview()) && !"0".equals(assessment.getIsReview())) {// 如果已被审核
            if (assessment.getSaveNum() == 0 || assessment.getSaveNum() == null) {
                assessment.setSaveNum(1);
            } else {
                assessment.setSaveNum(assessment.getSaveNum() + 1);
            }
        }
        sysDao.getServiceDo().modify(assessment);
        return assessment;
    }

    /**
     * 保存考核明细表
     */
    public void saveAssessmentDetail(Assessment assessment, AssessmentVo vo, List<Map<String, String[]>> maps) throws Exception {
        for (Map<String, String[]> map : maps) {
            for (Map.Entry<String, String[]> entry : map.entrySet()) {
                if (vo.getDetailMap().get(entry.getKey()) != null) {// 前台封装的detailMap必须包含有后台获得的文件才能保存
                    AssessmentDetail assessmentDetail = new AssessmentDetail();
                    assessmentDetail.setAssessmentId(assessment.getId());
                    assessmentDetail.setSignAreaCode(assessment.getSignAreaCode());
                    assessmentDetail.setContentCode(entry.getKey());
                    assessmentDetail.setFileName(entry.getValue()[0]);// 文件名
                    assessmentDetail.setOptionWeb(entry.getValue()[1]);// 文件路径
                    if ("11".equals(entry.getKey())) {
                        assessmentDetail.setScorePre(new BigDecimal(10));
                        assessmentDetail.setId(vo.getDetailMap().get("11").getId());
                    } else if ("13".equals(entry.getKey())) {
                        assessmentDetail.setScorePre(new BigDecimal(5));
                        assessmentDetail.setId(vo.getDetailMap().get("13").getId());
                    } else if ("32".equals(entry.getKey())) {
                        assessmentDetail.setId(vo.getDetailMap().get("32").getId());
                    } else if ("31".equals(entry.getKey())) {
                        assessmentDetail.setScorePre(new BigDecimal(5));
                        assessmentDetail.setId(vo.getDetailMap().get("31").getId());
                    } else if ("44".equals(entry.getKey())) {
                        assessmentDetail.setScorePre(new BigDecimal(5));
                        assessmentDetail.setId(vo.getDetailMap().get("44").getId());
                    } else if ("46".equals(entry.getKey())) {
                        assessmentDetail.setScorePre(new BigDecimal(5));
                        assessmentDetail.setId(vo.getDetailMap().get("46").getId());
                    } else if ("48".equals(entry.getKey())) {
                        assessmentDetail.setScorePre(new BigDecimal(5));
                        assessmentDetail.setId(vo.getDetailMap().get("48").getId());
                    } else if ("410".equals(entry.getKey())) {
                        assessmentDetail.setScorePre(new BigDecimal(5));
                        assessmentDetail.setId(vo.getDetailMap().get("410").getId());
                    } else if ("411".equals(entry.getKey())) {
                        assessmentDetail.setScorePre(new BigDecimal(10));
                        assessmentDetail.setId(vo.getDetailMap().get("411").getId());
                    } else if ("412".equals(entry.getKey())) {
                        assessmentDetail.setScorePre(new BigDecimal(5));
                        assessmentDetail.setId(vo.getDetailMap().get("412").getId());
                    }
                    assessmentDetail.setUpdateUserId(this.getRequest().getSession().getAttribute("UserId").toString());
                    assessmentDetail.setUpdateUserName(this.getRequest().getSession().getAttribute("UserName").toString());
                    sysDao.getAssessmentDetailDao().save(assessmentDetail, vo, assessment);
                }
            }
        }
    }

    /**
     * 文件批量上传
     */
    public String uploadFiles() {
        JSONObject uploadJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try {
            // 档案齐全（11考核项）
            int i = 0;
            if (fileWorkRecordComplete != null) {
                for (File complete : fileWorkRecordComplete) {
                    if (complete != null) {
                        uploadFile(jsonArray, complete, fileWorkRecordCompleteFileName.get(i), "11");
                        i++;
                    }
                }
            }
            // 有效沟通（13考核项）
            if (fileWorkValidTalk != null) {
                i = 0;
                for (File talk : fileWorkValidTalk) {
                    if (talk != null) {
                        uploadFile(jsonArray, talk, fileWorkValidTalkFileName.get(i), "13");
                        i++;
                    }

                }
            }
            // 健康咨询（31考核项）
            if (fileTeamConsult != null) {
                i = 0;
                for (File consult : fileTeamConsult) {
                    if (consult != null) {
                        uploadFile(jsonArray, consult, fileTeamConsultFileName.get(i), "31");
                        i++;
                    }
                }
            }
            // 社区团队活动（32考核项）
            if (fileTeamCommAct != null) {
                i = 0;
                for (File commAct : fileTeamCommAct) {
                    if (commAct != null) {
                        uploadFile(jsonArray, commAct, fileTeamCommActFileName.get(i), "32");
                        i++;
                    }
                }
            }
            // 老年人随访（44考核项）
            if (fileOld != null) {
                i = 0;
                for (File old : fileOld) {
                    if (old != null) {
                        uploadFile(jsonArray, old, fileOldFileName.get(i), "44");
                        i++;
                    }
                }
            }
            // 孕产妇随访（46考核项）
            if (filePregnant != null) {
                i = 0;
                for (File pregnant : filePregnant) {
                    if (pregnant != null) {
                        uploadFile(jsonArray, pregnant, filePregnantFileName.get(i), "46");
                        i++;
                    }
                }
            }
            // 儿童随访（48考核项）
            if (fileYoung != null) {
                i = 0;
                for (File young : fileYoung) {
                    if (young != null) {
                        uploadFile(jsonArray, young, fileYoungFileName.get(i), "48");
                        i++;
                    }
                }
            }
            // 慢性病随访（410考核项）
            if (fileChronic != null) {
                i = 0;
                for (File chronic : fileChronic) {
                    if (chronic != null) {
                        uploadFile(jsonArray, chronic, fileChronicFileName.get(i), "410");
                        i++;
                    }
                }
            }
            // 结核病、严重精神障碍随访（411考核项）
            if (filePsychosis != null) {
                i = 0;
                for (File chronic : filePsychosis) {
                    if (chronic != null) {
                        uploadFile(jsonArray, chronic, filePsychosisFileName.get(i), "411");
                        i++;
                    }
                }
            }
            // 结核病、严重精神障碍服务记录（412考核项）
            if (filePsychosisRecord != null) {
                i = 0;
                for (File chronic : filePsychosisRecord) {
                    if (chronic != null) {
                        uploadFile(jsonArray, chronic, filePsychosisRecordFileName.get(i), "412");
                        i++;
                    }
                }
            }
            uploadJson.put("attachmentDetailVos", jsonArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uploadJson.toJSONString();
    }

    /**
     * 单文件上传
     */
    public JSONArray uploadFile(JSONArray jsonArray, File file, String FileName, String ContentCode) throws Exception {
        if (file != null) {
            String fileType = FileName.split("\\.")[1];
            if (!("mp3".equalsIgnoreCase(fileType))) {// 如果不是音频文件则进行压缩（正常情况下不是音频就是图片）
                if (file.length() > 1000000) {// 图片大于1MB则进行压缩
                    reduceImg(file.getPath(), file.getPath(), 600);// 压缩后替换原文件（图片宽度为600）
                }
            }
            FileInputStream in = null;
            try {
                in = new FileInputStream(file);
                byte[] bytes = new byte[in.available()];
                in.read(bytes);// 这行不能删除否则图片会损坏
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("base64", Base64Utils.encode(bytes));
                jsonObject.put("fileName", FileName);
                jsonObject.put("assessProjectCode", ContentCode);
                jsonArray.add(jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (in != null) {
                    in.close();
                }
            }
        }
        return jsonArray;
    }

    /**
     * 调用上传文件接口
     */
    public List<Map<String, String[]>> remoteUpload(String result) throws Exception {
        String url = PropertiesUtil.getConfValue("assessUploadUrl");// 图片上传IP
        String urlAppName = PropertiesUtil.getConfValue("assessUploadUrlAppName");// 图片上传应用名称
        String interfaceUrl = "attachmentAction.action?act=uploadFile";
        String jsonString = doPostJson(result, url + "/" + urlAppName + "/" + interfaceUrl);
        JSONObject jsonObject = JSON.parseObject(jsonString);
        JSONArray jsonArray = JSONArray.parseArray(jsonObject.getString("rows"));
        List<Map<String, String[]>> backFilePaths = new ArrayList<>();
        Map<String, String[]> backFilePath = null;// 接口返回的文件路径
        for (Object o : jsonArray) {
            JSONObject jo = (JSONObject) o;
            backFilePath = new HashMap<>();// 接口返回的文件路径
            String filePath = "";
            if ("interface".equals(urlAppName)) {// 如果工程名是interface则图片路径中不包含工程名
                filePath = jo.getString("filePath");
            } else {
                filePath = urlAppName + "/" + jo.getString("filePath");
            }
            backFilePath.put(jo.getString("assessProjectCode"), new String[]{jo.getString("fileName"), filePath});
            backFilePaths.add(backFilePath);
        }
        return backFilePaths;
    }

    /**
     * 统计报表
     */
    public String statisticReport() {
        try {
            AssessListQvo qvo = (AssessListQvo) getJson(AssessListQvo.class);
            if (qvo != null) {
                List<AssessReportVo> reportList = generateReportData(qvo);
                this.getJsonLayui().setData(reportList);
                this.getJsonLayui().setCount(reportList.size());
            } else {
                this.getJsons().setCode("900");
                this.getJsons().setMsg("参数格式错误!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getJsons().setCode("900");
            this.getJsons().setMsg("统计出错，请联系系统管理员!");
        }
        return "jsonLayui";
    }

    /**
     * 生成各区域的报表记录
     */
    private void createReportRecordForArea(AssessListQvo qvo, List<AssessReportVo> reportList, List<CdAddress> subAreas) {
        try {
            // 统计签约数
            List<AssessmentCountVo> signNumList = sysDao.getAssessmentDao().countSignNumForArea(qvo);
            // 统计各评级数量
            List<AssessmentCountVo> ratingNumList = sysDao.getAssessmentDao().countRatingNumForArea(qvo);
            // 合并同一区域的统计数据
            AssessReportVo report = null;
            for (CdAddress subArea : subAreas) {
                int assessNum = 0;// 已考核数 = 优、良、合格、不合格数总和
                report = new AssessReportVo();
                report.setStatisticObjType("1");
                report.setStatisticObjCode(subArea.getCtcode());
                report.setStatisticObjName(subArea.getAreaSname());
                for (AssessmentCountVo vo : signNumList) {// 签约数
                    if (vo.getSignAreaCode().equals(subArea.getCtcode())) {
                        report.setSignNum(vo.getSignNum().intValue());
                    }
                }
                for (AssessmentCountVo vo : ratingNumList) {// 各评级数量
                    if (vo.getSignAreaCode().equals(subArea.getCtcode())) {
                        if ("优".equals(vo.getRating())) {
                            report.setExcellentNum(vo.getRatingNum().intValue());
                        } else if ("良".equals(vo.getRating())) {
                            report.setGoodNum(vo.getRatingNum().intValue());
                        } else if ("合格".equals(vo.getRating())) {
                            report.setQualifiedNum(vo.getRatingNum().intValue());
                        } else if ("不合格".equals(vo.getRating())) {
                            report.setUnQualifiedNum(vo.getRatingNum().intValue());
                        }
                        assessNum += vo.getRatingNum().intValue();
                    }
                }
                // 已考核数
                report.setAssessNum(assessNum);
                // 绩效金额
                report.setSubsidy(report.getExcellentNum() * 100 + report.getGoodNum() * 80 + report.getQualifiedNum() * 50);
                reportList.add(report);
            }
        } catch (Exception e) {
            new ActionException(this.getClass(), e.getMessage());
        }
    }

    /**
     * 生成报表数据
     */
    private List<AssessReportVo> generateReportData(AssessListQvo qvo) throws Exception {
        List<AssessReportVo> reportList = new ArrayList<>();
        AssessReportVo report = null;
        if (StringUtils.isNotBlank(qvo.getDrId())) {// 医生ID
            AppTeamMember member = sysDao.getAppTeamMemberDao().findMemberByDrId(qvo.getDrId());
            report = new AssessReportVo();
            report.setStatisticObjType("4");
            report.setStatisticObjCode(member.getMemDrId());
            report.setStatisticObjName(member.getMemDrName());
            createReportRecord(qvo, report);
            reportList.add(report);
        } else if (StringUtils.isNotBlank(qvo.getTeamId())) {// 团队ID
            List<AppTeamMember> teamMembers = sysDao.getAppTeamMemberDao().findTeamId(qvo.getTeamId());
            for (AppTeamMember doctor : teamMembers) {
                qvo.setTeamId(qvo.getTeamId());
                qvo.setDrId(doctor.getMemDrId());
                report = new AssessReportVo();
                report.setStatisticObjType("4");
                report.setStatisticObjCode(doctor.getMemDrId());
                report.setStatisticObjName(doctor.getMemDrName());
                createReportRecord(qvo, report);
                reportList.add(report);
            }
        } else if (StringUtils.isNotBlank(qvo.getHospId())) {// 机构ID
            List<AppTeam> teams = sysDao.getAppTeamDao().findAll(qvo.getHospId());
            for (AppTeam team : teams) {
                qvo.setTeamId(team.getId());
                report = new AssessReportVo();
                report.setStatisticObjType("3");
                report.setStatisticObjCode(team.getId());
                report.setStatisticObjName(team.getTeamName());
                createReportRecord(qvo, report);
                reportList.add(report);
            }
        } else if (StringUtils.isNotBlank(qvo.getAreaCode())) {// 地区编码
            CdAddress area = (CdAddress) sysDao.getServiceDo().find(CdAddress.class, qvo.getAreaCode());
            if ("4".equals(area.getLevel())) {// 如果是乡镇级别，则查询机构，否则查询地区
                List<AppHospDept> hospDepts = sysDao.getAppHospDeptDao().findByAreaCode(area.getCtcode());
                for (AppHospDept hospDept : hospDepts) {
                    qvo.setHospId(hospDept.getId());
                    report = new AssessReportVo();
                    report.setStatisticObjType("2");
                    report.setStatisticObjCode(hospDept.getId());
                    report.setStatisticObjName(hospDept.getHospName());
                    createReportRecord(qvo, report);
                    reportList.add(report);
                }
            } else {
                List<CdAddress> subAreas = sysDao.getCdAddressDao().findSubAreas(area.getCtcode());// 市级区域编码
                qvo.setAreaLevel(area.getLevel());
                createReportRecordForArea(qvo, reportList, subAreas);
            }
        } else {// 市级或县级账号不带参查询
            String hospLevel = (String) ActionContext.getContext().getSession().get("HospLevel");
            String hospAreaCode = (String) ActionContext.getContext().getSession().get("HospAreaCode");
            List<CdAddress> subAreas = new ArrayList<>();
            if ("2".equals(hospLevel)) {// 统计市域内所有区县
                subAreas = sysDao.getCdAddressDao().findSubAreas(hospAreaCode.substring(0, 4) + "00000000");// 市级区域编码
            } else if ("3".equals(hospLevel)) {// 统计区县下的所有乡镇
                subAreas = sysDao.getCdAddressDao().findSubAreas(hospAreaCode.substring(0, 6) + "000000");// 区县级区域编码
            }
            qvo.setAreaLevel(hospLevel);
            qvo.setAreaCode(hospAreaCode);
            createReportRecordForArea(qvo, reportList, subAreas);
        }
        // 合计
        AssessReportVo totalReport = new AssessReportVo();
        int totalSignNum = 0;
        int totalAssessNum = 0;
        int totalExcellentNum = 0;
        int totalGoodNum = 0;
        int totalQualifiedNum = 0;
        int totalUnqualifiedNum = 0;
        double totalSubsidy = 0;
        for (AssessReportVo reportVo : reportList) {
            totalSignNum += reportVo.getSignNum();
            totalAssessNum += reportVo.getAssessNum();
            totalExcellentNum += reportVo.getExcellentNum();
            totalGoodNum += reportVo.getGoodNum();
            totalQualifiedNum += reportVo.getQualifiedNum();
            totalUnqualifiedNum += reportVo.getUnQualifiedNum();
            totalSubsidy += reportVo.getSubsidy();
        }
        totalReport.setStatisticObjType("4");
        totalReport.setStatisticObjName("合计");
        totalReport.setSignNum(totalSignNum);
        totalReport.setAssessNum(totalAssessNum);
        totalReport.setExcellentNum(totalExcellentNum);
        totalReport.setGoodNum(totalGoodNum);
        totalReport.setQualifiedNum(totalQualifiedNum);
        totalReport.setUnQualifiedNum(totalUnqualifiedNum);
        totalReport.setSubsidy(totalSubsidy);
        reportList.add(totalReport);
        return reportList;
    }

    /**
     * 创建单条报表记录
     */
    private void createReportRecord(AssessListQvo qvo, AssessReportVo report) {
        try {
            report.setSignNum(sysDao.getAssessmentDao().countSignNum(qvo));
            int assessNum = 0;// 已考核数 = 优、良、合格、不合格数总和
            List<AssessmentCountVo> countVos = sysDao.getAssessmentDao().countRatingNum(qvo);
            for (AssessmentCountVo countVo : countVos) {
                if ("优".equals(countVo.getRating())) {
                    report.setExcellentNum(countVo.getRatingNum().intValue());
                } else if ("良".equals(countVo.getRating())) {
                    report.setGoodNum(countVo.getRatingNum().intValue());
                } else if ("合格".equals(countVo.getRating())) {
                    report.setQualifiedNum(countVo.getRatingNum().intValue());
                } else if ("不合格".equals(countVo.getRating())) {
                    report.setUnQualifiedNum(countVo.getRatingNum().intValue());
                }
                assessNum += countVo.getRatingNum().intValue();
            }
            report.setAssessNum(assessNum);
            report.setSubsidy(report.getExcellentNum() * 100 + report.getGoodNum() * 80 + report.getQualifiedNum() * 50);
        } catch (Exception e) {
            new ActionException(this.getClass(), e.getMessage());
        }
    }

    /**
     * 压缩图片
     *
     * @param fromSrc     源文件
     * @param toSrc       目标文件
     * @param targetWidth 压缩后图片宽度
     */
    public void reduceImg(String fromSrc, String toSrc, int targetWidth) {
        try {
            File oldFile = new File(fromSrc);
            // 检查图片文件是否存在
            if (!oldFile.exists()) {
                System.out.println("文件不存在！");
            } else {
                // 获得源图片的宽高并存入数组中
                int[] results = getImgWidthHeight(oldFile);
                if (results == null || results[0] == 0 || results[1] == 0) {
                    System.out.println("无法获取图片大小！");
                } else {
                    // 按比例缩放或扩大图片大小，将双精度类型转为整型
                    int targetHeight = (int) (targetWidth * ((results[1] * 1.0) / results[0]));
                    // 开始读取文件并进行压缩
                    Image src = ImageIO.read(oldFile);

                    // 构造一个类型为预定义图像类型之一的 BufferedImage
                    BufferedImage tag = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);

                    // 绘制图像
                    // getScaledInstance表示创建此图像的缩放版本，返回一个新的缩放版本Image，按指定的width，height呈现图像。
                    // Image.SCALE_SMOOTH 选择图像平滑度比缩放速度具有更高优先级的图像缩放算法。
                    tag.getGraphics().drawImage(src.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH), 0, 0, null);
                    FileOutputStream out = null;
                    try {
                        // 创建文件输出流
                        out = new FileOutputStream(toSrc);
                        // 将图片按JPEG压缩，保存到out中
                        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
                        encoder.encode(tag);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        // 关闭文件输出流
                        if (out != null) {
                            out.close();
                        }
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取图片宽度和高度
     *
     * @param file 图片路径
     * @return 返回图片的宽度和高度
     */
    private int[] getImgWidthHeight(File file) throws IOException {
        InputStream is = null;
        BufferedImage src = null;
        int result[] = {0, 0};
        try {
            is = new FileInputStream(file);// 获得文件输入流
            src = ImageIO.read(is);// 从流里将图片写入缓冲图片区
            result[0] = src.getWidth(null); // 得到源图片宽
            result[1] = src.getHeight(null);// 得到源图片高
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                is.close();  // 关闭输入流
            }
        }
        return result;
    }

    public List<File> getFileWorkRecordComplete() {
        return fileWorkRecordComplete;
    }

    public void setFileWorkRecordComplete(List<File> fileWorkRecordComplete) {
        this.fileWorkRecordComplete = fileWorkRecordComplete;
    }

    public List<String> getFileWorkRecordCompleteFileName() {
        return fileWorkRecordCompleteFileName;
    }

    public void setFileWorkRecordCompleteFileName(List<String> fileWorkRecordCompleteFileName) {
        this.fileWorkRecordCompleteFileName = fileWorkRecordCompleteFileName;
    }

    public List<File> getFileWorkValidTalk() {
        return fileWorkValidTalk;
    }

    public void setFileWorkValidTalk(List<File> fileWorkValidTalk) {
        this.fileWorkValidTalk = fileWorkValidTalk;
    }

    public List<String> getFileWorkValidTalkFileName() {
        return fileWorkValidTalkFileName;
    }

    public void setFileWorkValidTalkFileName(List<String> fileWorkValidTalkFileName) {
        this.fileWorkValidTalkFileName = fileWorkValidTalkFileName;
    }

    public List<File> getFileTeamConsult() {
        return fileTeamConsult;
    }

    public void setFileTeamConsult(List<File> fileTeamConsult) {
        this.fileTeamConsult = fileTeamConsult;
    }

    public List<String> getFileTeamConsultFileName() {
        return fileTeamConsultFileName;
    }

    public void setFileTeamConsultFileName(List<String> fileTeamConsultFileName) {
        this.fileTeamConsultFileName = fileTeamConsultFileName;
    }

    public List<File> getFileTeamCommAct() {
        return fileTeamCommAct;
    }

    public void setFileTeamCommAct(List<File> fileTeamCommAct) {
        this.fileTeamCommAct = fileTeamCommAct;
    }

    public List<String> getFileTeamCommActFileName() {
        return fileTeamCommActFileName;
    }

    public void setFileTeamCommActFileName(List<String> fileTeamCommActFileName) {
        this.fileTeamCommActFileName = fileTeamCommActFileName;
    }

    public List<File> getFileOld() {
        return fileOld;
    }

    public void setFileOld(List<File> fileOld) {
        this.fileOld = fileOld;
    }

    public List<String> getFileOldFileName() {
        return fileOldFileName;
    }

    public void setFileOldFileName(List<String> fileOldFileName) {
        this.fileOldFileName = fileOldFileName;
    }

    public List<File> getFileYoung() {
        return fileYoung;
    }

    public void setFileYoung(List<File> fileYoung) {
        this.fileYoung = fileYoung;
    }

    public List<String> getFileYoungFileName() {
        return fileYoungFileName;
    }

    public void setFileYoungFileName(List<String> fileYoungFileName) {
        this.fileYoungFileName = fileYoungFileName;
    }

    public List<File> getFilePregnant() {
        return filePregnant;
    }

    public void setFilePregnant(List<File> filePregnant) {
        this.filePregnant = filePregnant;
    }

    public List<String> getFilePregnantFileName() {
        return filePregnantFileName;
    }

    public void setFilePregnantFileName(List<String> filePregnantFileName) {
        this.filePregnantFileName = filePregnantFileName;
    }

    public List<File> getFileChronic() {
        return fileChronic;
    }

    public void setFileChronic(List<File> fileChronic) {
        this.fileChronic = fileChronic;
    }

    public List<String> getFileChronicFileName() {
        return fileChronicFileName;
    }

    public void setFileChronicFileName(List<String> fileChronicFileName) {
        this.fileChronicFileName = fileChronicFileName;
    }

    public List<File> getFilePsychosis() {
        return filePsychosis;
    }

    public void setFilePsychosis(List<File> filePsychosis) {
        this.filePsychosis = filePsychosis;
    }

    public List<String> getFilePsychosisFileName() {
        return filePsychosisFileName;
    }

    public void setFilePsychosisFileName(List<String> filePsychosisFileName) {
        this.filePsychosisFileName = filePsychosisFileName;
    }

    public List<File> getFilePsychosisRecord() {
        return filePsychosisRecord;
    }

    public void setFilePsychosisRecord(List<File> filePsychosisRecord) {
        this.filePsychosisRecord = filePsychosisRecord;
    }

    public List<String> getFilePsychosisRecordFileName() {
        return filePsychosisRecordFileName;
    }

    public void setFilePsychosisRecordFileName(List<String> filePsychosisRecordFileName) {
        this.filePsychosisRecordFileName = filePsychosisRecordFileName;
    }

    /**
     * 去除重复数据（考核详细数据）
     */
    public void duplicateRemoval() {
        String[] contentCodes = {"11", "12", "13", "21", "31", "32", "41", "42", "43", "44", "45", "46", "47", "48", "49", "410", "411", "412", "51"};
        for (String contentCode : contentCodes) {
            int deletedNum = 0;
            int orderNum = 0;
            try {
                List<AssessmentDetail> detailList = sysDao.getAssessmentDetailDao().listAssessmentDetail(null, contentCode);
                System.out.println(contentCode + " 考核项存在重复数据的考核表有 " + detailList.size() + " 条");
                List<AssessmentDetail> details = null;
                for (AssessmentDetail assessmentDetail : detailList) {
                    details = sysDao.getAssessmentDetailDao().listAssessmentDetail(assessmentDetail.getAssessmentId(), contentCode);
                    System.out.println("");
                    System.out.println("序号：" + ++orderNum + " 考核表：" + assessmentDetail.getAssessmentId() + " 的 " + contentCode + " 考核项存在 " + details.size() + " 条重复记录");
                    deletedNum = 0;
                    for (int i = 0; i < details.size(); i++) {
                        if (i != 0) {// 删除重复记录（第一条不处理）
                            sysDao.getServiceDo().delete(details.get(i));
                            deletedNum++;
                        }
                    }
                    System.out.println("已删除 " + deletedNum + " 条记录，开始重新统计分数。");
                    Assessment assessment = (Assessment) sysDao.getServiceDo().find(Assessment.class, assessmentDetail.getAssessmentId());
                    // 设置所属人群
                    AssessmentVo qvo = new AssessmentVo();
                    qvo.setSignId(assessment.getSignId());
                    List<String> groups = new ArrayList<>();
                    List<AssessmentGroup> assessmentGroupList = sysDao.getAssessmentDao().findGroup(qvo);
                    if (assessmentGroupList != null && assessmentGroupList.size() > 0) {
                        for (AssessmentGroup group : assessmentGroupList) {
                            groups.add(group.getLabelGroup());
                        }
                    }
                    qvo.setGroups(groups.toArray(groups.toArray(new String[groups.size()])));
                    // 重新计算分数
                    updateAssessment(assessment, getItemsByGroup(qvo));
                }
                System.out.print("=====去除重复记录操作完成！=====");
                System.out.println("");
                System.out.println("");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将旧的共享数据变更为新的数据格式的共享数据
     */
    public void handleShareData() {
        System.out.println("=====开始整理团队共享数据=====");
        int orderNum = 0;
        try {
            List<AssessmentTeamShare> list = sysDao.getAssessmentTeamShareDao().listShare(null, null, null);
            System.out.println("=====共有：" + list.size() + " 条共享数据待处理！");
            for (AssessmentTeamShare share : list) {
                System.out.println();
                System.out.println("=====序号：" + ++orderNum + " 开始处理=====");
                JSONObject jObj = JSONObject.parseObject(share.getOptionWeb());
                AssessmentTeamShare teamShare = null;
                for (Map.Entry<String, Object> entry : jObj.entrySet()) {
                    // 查找某个团队某年某月是否有共享数据（如果无则新增）
                    teamShare = new AssessmentTeamShare();
                    BeanUtils.copyProperties(share, teamShare);
                    teamShare.setId(null);
                    teamShare.setYear(2018);
                    teamShare.setMonth(Integer.valueOf(entry.getKey()));
                    teamShare.setOptionWeb(entry.getValue() + "");
                    sysDao.getServiceDo().add(teamShare);
                }
                System.out.println("=====主键：" + share.getId() + "处理完成=====");
            }
            System.out.print("=====共享数据整理完成！=====");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 对所有考核表重新统计考核分
     */
    public void countScoreAgain() {
        System.out.println("=====统计开始=====");
        int orderNum = 0;
        try {
            String sql = " select t1.* from assessment t1 left join app_sign_form t2 on t1.sign_id = t2.id where t2.sign_state in ('0', '2') ";
            List<Assessment> list = sysDao.getServiceDo().findSqlList(sql, Assessment.class);
            System.out.println("=====共有：" + list.size() + " 条考核表待处理！=====");
            for (Assessment assessment : list) {
                System.out.println("序号：" + ++orderNum + " 开始统计！");
                // 设置所属人群
                AssessmentVo qvo = new AssessmentVo();
                qvo.setSignId(assessment.getSignId());
                List<String> groups = new ArrayList<>();
                List<AssessmentGroup> assessmentGroupList = sysDao.getAssessmentDao().findGroup(qvo);
                if (assessmentGroupList != null && assessmentGroupList.size() > 0) {
                    for (AssessmentGroup group : assessmentGroupList) {
                        groups.add(group.getLabelGroup());
                    }
                }
                qvo.setGroups(groups.toArray(groups.toArray(new String[groups.size()])));
                // 重新计算分数
                updateAssessment(assessment, getItemsByGroup(qvo));
            }
            System.out.print("=====统计完成！=====");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 重新统计属于多个服务人群的签约单
     */
    public void countPersonalProject() {
        System.out.println("=====统计开始=====");
        int orderNum = 0;
        try {
            String sql = " select count(assessment_id) assessNum, a.* from assessment_detail a where 1 = 1 " +
                    "and a.content_code like '4%' group by a.assessment_id having assessNum > 3 ";
            List<AssessmentDetail> list = sysDao.getServiceDo().findSqlList(sql, AssessmentDetail.class);
            System.out.println("=====共有：" + list.size() + " 条考核表待处理！=====");
            for (AssessmentDetail assessmentDetail : list) {
                System.out.println("序号：" + ++orderNum + " 开始统计！");
                Assessment assessment = (Assessment) sysDao.getServiceDo().find(Assessment.class, assessmentDetail.getAssessmentId());
                // 设置所属人群
                AssessmentVo qvo = new AssessmentVo();
                qvo.setSignId(assessment.getSignId());
                List<String> groups = new ArrayList<>();
                List<AssessmentGroup> assessmentGroupList = sysDao.getAssessmentDao().findGroup(qvo);
                if (assessmentGroupList != null && assessmentGroupList.size() > 0) {
                    for (AssessmentGroup group : assessmentGroupList) {
                        groups.add(group.getLabelGroup());
                    }
                }
                qvo.setGroups(groups.toArray(groups.toArray(new String[groups.size()])));
                // 重新计算分数
                updateAssessment(assessment, getItemsByGroup(qvo));
            }
            System.out.print("=====统计完成！=====");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
