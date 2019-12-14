package com.ylz.bizDo.assessment.dao;

import com.ylz.bizDo.app.po.AppSignForm;
import com.ylz.bizDo.assessment.po.Assessment;
import com.ylz.bizDo.assessment.po.AssessmentDetail;
import com.ylz.bizDo.assessment.vo.AssessDetailQvo;
import com.ylz.bizDo.assessment.vo.AssessmentVo;
import com.ylz.bizDo.assessment.vo.ReviewVo;
import com.ylz.bizDo.assessment.vo.interfaceQvo;

import java.util.List;
import java.util.Map;

/**
 * Created by zms on 2018/6/8.
 */
public interface AssessmentDetailDao {
    //保存
    public void save(AssessmentDetail po, AssessmentVo vo, Assessment masterPo) throws Exception;

    //审核保存
    public void saveReview(ReviewVo vo) throws Exception;

    // 统计考核总分数
    public double countScore(String assessmentId, List<String[]> groupList) throws Exception;

    // 统计审核总得分
    public double countScoreAft(String assessmentId, List<String[]> groupList) throws Exception;

    // 统计已考核项数量
    public int countFinishNum(String assessmentId) throws Exception;

    // 删除附件明细
    public AssessmentDetail delOption(AssessDetailQvo qvo) throws Exception;

    // 批量删除附件明细
    public void delOptions(AssessDetailQvo qvo) throws Exception;

    // 公共自动生成接口
    public void commonAutoItem(interfaceQvo qvo, Assessment assessment) throws Exception;

    // 个性化自动生成接口
    public void personalItem(interfaceQvo iQvo, AssessmentVo qvo, Assessment assessment) throws Exception;

    // 团队共享初始化
    public void teamShareInit(Assessment masterPo, AppSignForm signForm) throws Exception;

    // 满意度测评
    public void appEvaluate(Assessment vo) throws Exception;

    // 查询考核明细信息
    public AssessmentDetail findAssessmentDetail(String assessId, String contentCode) throws Exception;

    // 查询考核明细列表
    public List<AssessmentDetail> listAssessmentDetail(String assessmentId, String contentCode) throws Exception;
}
