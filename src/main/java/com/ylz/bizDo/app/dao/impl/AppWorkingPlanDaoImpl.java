package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppWorkingPlanDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 工作计划
 */
@Service("appWorkingPlanDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppWorkingPlanDaoImpl implements AppWorkingPlanDao {
}
