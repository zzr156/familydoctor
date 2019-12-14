package com.ylz.bizDo.assessment.dao.impl;

import com.ylz.bizDo.assessment.dao.AssessmentContentDao;
import com.ylz.bizDo.assessment.po.AssessmentContent;
import com.ylz.bizDo.assessment.vo.AssessmentContentVo;
import com.ylz.packaccede.allDo.SysDao;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zms on 2018/6/8.
 */
@Service("assessmentContentDao")
@Transactional(rollbackForClassName={"Exception"})
public class AssessmentContentDaoImpl implements AssessmentContentDao {
    @Autowired
    private SysDao sysDao;

    @Override
    public AssessmentContent findAssessContents(String contentCode) throws Exception {
        return (AssessmentContent) sysDao.getServiceDo().getSessionFactory().getCurrentSession()
                .createCriteria(AssessmentContent.class)
                .add(Restrictions.eq("code", contentCode))
                .setCacheable(true)
                .uniqueResult();
    }

    /**
     * 根据所属人群获取考核项目列表
     *
     * @param groups 所属人群
     * @return
     */
    @Override
    public List<AssessmentContentVo> getAssessmentContents(String[] groups) throws Exception {
        String commonItems = "11,12,13,21,31,32,41,51";// 公共考核项目
        if (groups != null && groups.length > 0) {
            for (String group : groups) {
                if ("health".equals(group)) {// 健康人群个性化考核项
                    commonItems += ",42";
                } else if ("young".equals(group)) {// 儿童个性化考核项
                    commonItems += ",47,48";
                } else if ("pregnant".equals(group)) {// 孕产妇个性化考核项
                    commonItems += ",45,46";
                } else if ("old".equals(group)) {// 老年人个性化考核项
                    commonItems += ",43,44";
                } else if ("chronic".equals(group)) {// 慢性病个性化考核项
                    commonItems += ",49,410";
                }
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("code", commonItems.split(","));
        StringBuilder sql = new StringBuilder();
        sql.append(" select t1.id id, t1.code code, t1.content content, t1.method method, t1.project_id projectId, t1.proof proof, ");
        sql.append(" t1.score score, t1.short_content shortContent, t1.order_no orderNo, t1.need_upload needUpload ");
        sql.append(" from assessment_content t1 where t1.code in (:code) order by t1.order_no asc; ");
        return sysDao.getServiceDo().findSqlMapRVo(sql.toString(), map, AssessmentContentVo.class);
    }
}
