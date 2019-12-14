package com.ylz.view.assess.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppSignForm;
import com.ylz.bizDo.app.vo.AppSignFormVo;
import com.ylz.bizDo.assessment.po.Assessment;
import com.ylz.bizDo.assessment.po.AssessmentDetail;
import com.ylz.bizDo.assessment.vo.*;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.util.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ylz.packcommon.common.util.HttpPostUtils.doPostJson;

/**
 * 绩效考核APP接口
 *
 * @author mxx
 * @date 2018年9月27日
 */
@SuppressWarnings("all")
@Action(
        value = "appAssessmentAction",
        results = {
                @Result(name = "ajson", type = "json", params = {"root", "ajson", "contentType", "application/json"}),
                @Result(name = "jsonFile", type = "json", params = {"root", "ajson", "contentType", "text/html"})
        }
)
public class AppAssessmentAction extends CommonAction {

    /**
     * 参数加密密钥
     */
    static String password = "jwylzdoctoryw(*&^%";


    // *********************【绩】【效】【查】【询】*********************

    /**
     * 查询绩效考核列表
     */
    public String findAssessList() {
        AssessListQvo qvo = (AssessListQvo) getAppJson(AssessListQvo.class);
        if (qvo != null) {
            try {
                List<AssessListVo> list = sysDao.getAssessmentDao().findAssessList(qvo);
                if (list != null && list.size() > 0) {
                    this.getAjson().setQvo(qvo);
                    this.getAjson().setRows(list);
                    this.getAjson().setMsgCode("800");
                }
            } catch (Exception e) {
                e.printStackTrace();
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg(e.getMessage());
            }
        } else {
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("参数格式错误");
        }
        return "ajson";
    }

    /**
     * 查询居民签约信息
     */
    public String findSignInfo() {
        AssessmentQvo qvo = (AssessmentQvo) getAppJson(AssessmentQvo.class);
        if (qvo != null) {
            try {
                AppSignFormVo signInfoVo = (AppSignFormVo) sysDao.getAppSignformDao().findAppSignFormById(qvo.getSignId());
                if (signInfoVo != null) {
                    this.getAjson().setVo(signInfoVo);
                    this.getAjson().setMsgCode("800");
                }
            } catch (Exception e) {
                e.printStackTrace();
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg(e.getMessage());
            }
        } else {
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("参数格式错误");
        }
        return "ajson";
    }

    // *********************【绩】【效】【考】【核】*********************

    /**
     * 初始化考核表
     */
    public String initAssess() throws Exception {
        try {
            AssessmentVo qvo = (AssessmentVo) getAppJson(AssessmentVo.class);
            if (qvo != null) {
                // 设置所属人群
                List<String> groups = new ArrayList<>();
                List<AssessmentGroup> assessmentGroupList = sysDao.getAssessmentDao().findGroup(qvo);
                if (assessmentGroupList != null && assessmentGroupList.size() > 0) {
                    for (AssessmentGroup group : assessmentGroupList) {
                        groups.add(group.getLabelGroup());
                    }
                }
                qvo.setGroups(groups.toArray(groups.toArray(new String[groups.size()])));

                // 接口参数对象
                interfaceQvo iQvo = sysDao.getAssessmentDao().findInterceParams(qvo);

                // 不存在则生成考核表，存在则查询考核表
                Assessment assessment = null;
                if (StringUtils.isBlank(qvo.getAssessId())) {
                    assessment = createAssessment(qvo);// 生成考核表
                } else {
                    assessment = (Assessment) sysDao.getServiceDo().find(Assessment.class, qvo.getAssessId());
                }
                // 查询签约单
                AppSignForm signForm = (AppSignForm) sysDao.getServiceDo().find(AppSignForm.class, assessment.getSignId());

                // 手机APP端用户
                sysDao.getAssessmentDetailDao().appEvaluate(assessment);
                // 初始化团队共享
                sysDao.getAssessmentDetailDao().teamShareInit(assessment, signForm);
                // 公共自动生成项目
                sysDao.getAssessmentDetailDao().commonAutoItem(iQvo, assessment);
                // 个性化自动生成接口
                sysDao.getAssessmentDetailDao().personalItem(iQvo, qvo, assessment);

                // 更新考核表
                List<String[]> itemList = getItemsByGroup(qvo);
                updateAssessment(assessment, itemList);// 更新考核表

                // 根据所属人群加载考核表模板
                List<AssessmentContentVo> contentList = sysDao.getAssessmentContentDao().getAssessmentContents(groups.toArray(new String[groups.size()]));
                // 将考核详情与考核表模板进行组装
                String outerNetUrl = PropertiesUtil.getConfValue("outerNetUrl");// 图片外网地址
                List<AssessmentDetail> detailList = sysDao.getServiceDo().loadByPk(AssessmentDetail.class, "assessmentId", assessment.getId());
                for (AssessmentDetail detail : detailList) {
                    for (int i = 0; i < contentList.size(); i++) {
                        if (detail.getContentCode().equals(contentList.get(i).getCode())) {
                            contentList.get(i).setDetailId(detail.getId());
                            contentList.get(i).setScorePre(detail.getScorePre() == null ? 0 : detail.getScorePre().doubleValue());
                            contentList.get(i).setOptionWeb(detail.getOptionWeb() == null ? "" :
                                    (detail.getOptionWeb().contains("interface") ? detail.getOptionWeb().replace("interface", outerNetUrl + "interface") :
                                            detail.getOptionWeb().replace("open", outerNetUrl + "open")));
                        }
                    }
                }
                this.getAjson().setRows(contentList);
                this.getAjson().setMsgCode("800");
            } else {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 保存考核表（考核表和考核明细表的保存没有在同一个事务中）
     */
    public String saveAssessment() throws IOException {
        try {
            AssessmentVo vo = (AssessmentVo) getAppJson(AssessmentVo.class);
            if (vo != null) {
                // 设置所属人群
                List<String> groups = new ArrayList<>();
                List<AssessmentGroup> assessmentGroupList = sysDao.getAssessmentDao().findGroup(vo);
                if (assessmentGroupList != null && assessmentGroupList.size() > 0) {
                    for (AssessmentGroup group : assessmentGroupList) {
                        groups.add(group.getLabelGroup());
                    }
                }
                vo.setGroups(groups.toArray(groups.toArray(new String[groups.size()])));

                // 上传文件前处理
                String result = uploadFiles(vo);
                // 上传文件（调用图片上传接口）
                List<Map<String, String[]>> backFilePaths = remoteUpload(result);
                // 查询考核表
                Assessment assessment = (Assessment) sysDao.getServiceDo().find(Assessment.class, vo.getAssessId());
                // 更新考核明细信息
                AssessmentDetail detail = sysDao.getAssessmentDetailDao().findAssessmentDetail(vo.getAssessId(), vo.getContentCode());
                Map<String, AssessmentDetail> detailMap = new HashMap<>();
                if (detail == null) {
                    detail = new AssessmentDetail();
                }
                detailMap.put(vo.getContentCode(), detail);
                vo.setDetailMap(detailMap);
                saveAssessmentDetail(assessment, vo, backFilePaths);
                // 更新考核信息（更新考核总得分和已完成的项目数量）
                List<String[]> itemList = getItemsByGroup(vo);
                modifyAssessment(assessment, itemList);
                this.getAjson().setMsgCode("800");
                this.getAjson().setMsg("上传成功!");
            } else {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "jsonFile";
    }

    /**
     * 查询所属人群对应的考核项
     */
    private List<String[]> getItemsByGroup(AssessmentVo qvo) {
        String[] healthItems = {"42"};
        String[] oldItems = {"43", "44"};
        String[] pregnantItems = {"45", "46"};
        String[] youngItems = {"47", "48"};
        String[] chronicItems = {"49", "410"};
        String[] psychosisItems = {"411", "412"};
        List<String[]> itemList = new ArrayList<>();
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
        // 考核项数量
        int detailNum = 8;// 公共考核项为8项
        Map<String, Integer> groupNumMap = new HashMap<>();
        groupNumMap.put("health", 1);
        groupNumMap.put("young", 2);
        groupNumMap.put("pregnant", 2);
        groupNumMap.put("old", 2);
        groupNumMap.put("chronic", 2);
        groupNumMap.put("psychosis", 2);
        for (String group : vo.getGroups()) {
            if ("health".equals(group)) {
                detailNum += groupNumMap.get("health");
            } else if ("young".equals(group)) {
                detailNum += groupNumMap.get("young");
            } else if ("pregnant".equals(group)) {
                detailNum += groupNumMap.get("pregnant");
            } else if ("old".equals(group)) {
                detailNum += groupNumMap.get("old");
            } else if ("chronic".equals(group)) {
                detailNum += groupNumMap.get("chronic");
            } else if ("psychosis".equals(group)) {
                detailNum += groupNumMap.get("psychosis");
            }
        }
        assessment.setDetailNum(detailNum);// 需要完成的项目数量
        assessment.setFinishNum(0);
        assessment.setIsReview("0");
        assessment.setTotalScoreAft(new BigDecimal(0));
        assessment.setTotalScorePre(new BigDecimal(0));
        assessment.setSaveNum(0);
        assessment.setReviewNum(0);
        assessment.setIsExtract("0");
        assessment.setState("0");
        // 操作人ID、姓名
        AppDrUser drUser = this.getAppDrUser();
        assessment.setUpdateUserId(drUser.getId());
        assessment.setUpdateUserName(drUser.getDrName());
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

        // 解决少部分已考核的签约单的考核状态却显示为未考核的问题
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
     * 下载文件
     */
    public String download() throws Exception {
        String fileName = ServletActionContext.getRequest().getParameter("fileName");
        String filePath = ServletActionContext.getRequest().getParameter("filePath");
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
        try {
            AssessDetailQvo qvo = (AssessDetailQvo) getAppJson(AssessDetailQvo.class);
            if (qvo != null) {
                sysDao.getAssessmentDetailDao().delOptions(qvo);
                this.getAjson().setMsgCode("800");
                this.getAjson().setMsg("删除成功!");
            } else {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("删除失败!");
        }
        return "ajson";
    }

    /**
     * 保存考核明细表
     */
    public void saveAssessmentDetail(Assessment assessment, AssessmentVo vo, List<Map<String, String[]>> maps) throws Exception {
        for (Map<String, String[]> map : maps) {
            for (Map.Entry<String, String[]> entry : map.entrySet()) {
                // 前台封装的detailMap必须包含有后台获得的文件才能保存
                if (vo.getDetailMap().get(entry.getKey()) != null) {
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
                    // 操作人Id、姓名
                    AppDrUser drUser = this.getAppDrUser();
                    assessmentDetail.setUpdateUserId(drUser.getId());
                    assessmentDetail.setUpdateUserName(drUser.getDrName());
                    sysDao.getAssessmentDetailDao().save(assessmentDetail, vo, assessment);
                }
            }
        }
    }

    /**
     * 文件批量上传
     */
    public String uploadFiles(AssessmentVo vo) throws IOException {
        String[] attachmentFiles = vo.getAttachmentFiles().split(",");
        String[] getAttachmentFileNames = vo.getAttachmentFileNames().split(",");
        JSONObject uploadJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        int i = 0;// 文件名数组的下标
        if (attachmentFiles != null && attachmentFiles.length > 0) {
            for (String attachmentFile : attachmentFiles) {
                if (attachmentFile != null) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("base64", attachmentFile);
                    jsonObject.put("fileName", getAttachmentFileNames[i]);
                    jsonObject.put("assessProjectCode", vo.getContentCode());
                    jsonArray.add(jsonObject);
                    i++;
                }
            }
        }
        uploadJson.put("attachmentDetailVos", jsonArray);
        return uploadJson.toJSONString();
    }

    /**
     * 调用远程上传接口
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
            backFilePath.put(jo.getString("assessProjectCode"), new String[]{jo.getString("fileName"), urlAppName + "/" + jo.getString("filePath")});
            backFilePaths.add(backFilePath);
        }
        return backFilePaths;
    }

    /**
     * 保存考核完成状态
     */
    public String finishSave() {
        AssessmentVo qvo = (AssessmentVo) getAppJson(AssessmentVo.class);
        if (qvo != null) {
            try {
                Assessment assessment = (Assessment) sysDao.getServiceDo().find(Assessment.class, qvo.getAssessId());
                if (assessment == null) {
                    throw new Exception("考核表不存在!");
                }
                assessment.setIsFinish("1");
                sysDao.getServiceDo().modify(assessment);
                this.getAjson().setMsg("已标记为 考核完成！");
                this.getAjson().setMsgCode("800");
            } catch (Exception e) {
                e.printStackTrace();
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("保存出错!");
            }
        } else {
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("参数格式错误!");
        }
        return "ajson";
    }


    // ********************【绩】【效】【统】【计】**********************

    /**
     * 绩效考核统计
     */
    public String assessCount() {
        try {
            AssessListQvo qvo = (AssessListQvo) getAppJson(AssessListQvo.class);
            if (qvo != null) {
                qvo.setHistory("0");// 不统计历史记录，即只统计当前有效的签约单

                AssessCountVo result = new AssessCountVo();
                DecimalFormat df = new DecimalFormat("0.0000");// 保留4位小数
                // 签约数量统计
                int signNum = sysDao.getAssessmentDao().countSignNum(qvo);
                result.setSignNum(signNum);// 签约数
                List<AssessmentCountVo> list = sysDao.getAssessmentDao().countRatingNum(qvo);
                if (list != null && list.size() > 0 && signNum != 0) {
                    // 已考核人数和未考核人数统计
                    int assessNum = 0;// 已考核数
                    for (AssessmentCountVo vo : list) {// 统计已经考核的人员数量
                        assessNum += vo.getRatingNum().intValue();
                    }
                    result.setAssessNum(assessNum);
                    result.setNotAssessNum(signNum - assessNum);
                    result.setFinishNum(assessNum);// 兼容旧版，不要删除
                    result.setNotFinishNum(signNum - assessNum);// 兼容旧版，不要删除
                    // 各评级人数统计
                    for (AssessmentCountVo vo : list) {
                        if ("合格".equals(vo.getRating())) {
                            result.setQualifiedNum(vo.getRatingNum().intValue());
                            result.setQualifiedRate(Double.parseDouble(df.format((double) vo.getRatingNum().intValue() / assessNum)));
                        } else if ("良".equals(vo.getRating())) {
                            result.setGoodNum(vo.getRatingNum().intValue());
                            result.setGoodRate(Double.parseDouble(df.format((double) vo.getRatingNum().intValue() / assessNum)));
                        } else if ("优".equals(vo.getRating())) {
                            result.setExcellentNum(vo.getRatingNum().intValue());
                            result.setExcellentRate(Double.parseDouble(df.format((double) vo.getRatingNum().intValue() / assessNum)));
                        } else {
                            result.setNotQualifiedNum(vo.getRatingNum().intValue());
                            result.setNotQualifiedRate(Double.parseDouble(df.format((double) vo.getRatingNum().intValue() / assessNum)));
                        }
                    }
                }
                this.getAjson().setVo(result);
                this.getAjson().setMsgCode("800");
            } else {
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }
}
