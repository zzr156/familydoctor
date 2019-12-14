package com.ylz.bizDo.cd.dao;


import com.ylz.bizDo.cd.entity.CdRoleMenuEntity;
import com.ylz.bizDo.cd.entity.CdRoleMenuSonEntity;
import com.ylz.bizDo.cd.po.CdRole;
import com.ylz.bizDo.cd.vo.RoleVo;

import java.util.List;

public interface CdRoleDo   {
	public List<CdRole> selectAll(String userLevel) throws Exception;//查询全部
	public List<CdRole> findRole(RoleVo qvo) throws Exception;//分页查询
	public List<RoleVo> findRoleAll() throws Exception;
	public List<CdRoleMenuEntity> findRoleMenu(String id) throws Exception;
	public List<CdRoleMenuSonEntity> findRoleMenuSon(String id) throws Exception;
	public List<RoleVo> findRoleByDeptId(String deptId) throws Exception;
}
