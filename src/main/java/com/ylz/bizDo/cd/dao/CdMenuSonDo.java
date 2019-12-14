package com.ylz.bizDo.cd.dao;


import com.ylz.bizDo.cd.entity.CdMenuSonEntity;
import com.ylz.bizDo.cd.po.CdMenuSon;

import java.util.List;

public interface CdMenuSonDo {
	public List<CdMenuSon> selectAll() throws Exception;
	public List<CdMenuSon> findSonRoId(String[] rid) throws Exception;
	public List<CdMenuSon> findSonRoId(String[] rid,String mid) throws Exception;
	public List<CdMenuSonEntity> findMenuSonId(String id) throws Exception;
}
