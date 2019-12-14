package com.ylz.bizDo.cd.dao;


import com.ylz.bizDo.cd.po.CdRoleMenu;

import java.util.List;

public interface CdRoleMenuDo   {
	public List<CdRoleMenu> finRM(String rid) throws Exception;
	public List<CdRoleMenu> findRidList(String rid) throws Exception;
}
