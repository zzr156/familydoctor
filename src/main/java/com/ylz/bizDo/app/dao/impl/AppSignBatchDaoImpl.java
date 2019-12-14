package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppSignBatchDao;
import com.ylz.packaccede.allDo.SysDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zzl on 2017/6/14.
 */
@Service("appSignBatchDao")
@Transactional
public class AppSignBatchDaoImpl implements AppSignBatchDao {
    @Autowired
    private SysDao sysDao;
}
