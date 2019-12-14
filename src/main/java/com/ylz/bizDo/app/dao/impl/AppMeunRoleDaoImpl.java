package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppMeunRoleDao;
import com.ylz.packaccede.allDo.SysDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("appMeunRoleDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppMeunRoleDaoImpl implements AppMeunRoleDao {
    @Autowired
    private SysDao sysDao;

}
