package com.ylz.bizDo.cd.dao;


import com.ylz.bizDo.cd.entity.CdMenuEntity;
import com.ylz.bizDo.cd.po.CdMenu;
import com.ylz.bizDo.cd.vo.MenuOneVo;
import com.ylz.bizDo.cd.vo.MenuSvo;
import com.ylz.bizDo.cd.vo.MenuTwoVo;

import java.util.List;

public interface CdMenuDo  {
	public List<CdMenu> findStateTrue() throws Exception;
	public List<CdMenu> selectAll() throws Exception;
	public List<CdMenu> finRM(String id) throws Exception;
	//根据角色查询菜单
	public List<CdMenu> findRoId(String[] rid) throws Exception;
	public List<CdMenu> top() throws Exception;
	public List<MenuSvo> findSByState() throws Exception;
	public List<CdMenuEntity> findMenuList() throws Exception;
	public List<MenuSvo> findAll() throws Exception;
	public List<MenuSvo> findByRoId(String[] rid) throws Exception;

	public List<MenuTwoVo> findTopMenulist(String[] rid,String userId) throws Exception;
	public List<MenuOneVo> findTopMenulist(String userId) throws Exception;

	public List<MenuTwoVo> findTwoList(String fid) throws Exception;

	public List<MenuTwoVo> findTwoListAc(String id, String userId) throws Exception;
}
