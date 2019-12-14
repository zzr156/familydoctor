package com.ylz.bizDo.cd.dao.impl;

import com.ylz.bizDo.cd.dao.CdUserXxxDao;
import com.ylz.packaccede.allDo.SysDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("cdUserXxxDao")
@Transactional(rollbackForClassName={"Exception"})
public class CdUserXxxDaoImpl implements CdUserXxxDao {

	@Autowired
	private SysDao sysDao;
}
