package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppSignServeTeamDao;
import com.ylz.packaccede.allDo.SysDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zzl on 2017/8/17.
 */
@Service("appSignServeTeamDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppSignServeTeamDaoImpl implements AppSignServeTeamDao {
    @Autowired
    private SysDao sysDao;

}
