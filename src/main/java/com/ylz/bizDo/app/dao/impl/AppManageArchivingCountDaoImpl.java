package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppManageArchivingCountDao;
import com.ylz.bizDo.app.po.AppTeam;
import com.ylz.bizDo.app.vo.AppManageArchivingCountQvo;
import com.ylz.packaccede.allDo.SysDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2018/7/18.
 */
@Service("appManageArchivingCountDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppManageArchivingCountDaoImpl implements AppManageArchivingCountDao {
    @Autowired
    private SysDao sysDao;

}
