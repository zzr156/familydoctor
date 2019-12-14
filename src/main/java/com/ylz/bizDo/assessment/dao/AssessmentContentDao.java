package com.ylz.bizDo.assessment.dao;

import com.ylz.bizDo.assessment.po.AssessmentContent;
import com.ylz.bizDo.assessment.vo.AssessmentContentVo;

import java.util.List;

/**
 * Created by zms on 2018/6/8.
 */
public interface AssessmentContentDao {

    public AssessmentContent findAssessContents(String contentCode) throws Exception;

    /**
     * 根据所属人群获取考核项目列表
     *
     * @param groups 所属人群
     * @return
     */
    public List<AssessmentContentVo> getAssessmentContents(String[] groups) throws Exception;
}
