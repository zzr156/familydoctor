package com.ylz.bizDo.cd.dao;


import com.ylz.bizDo.cd.entity.CdPositionEntity;
import com.ylz.bizDo.cd.po.CdPosition;
import com.ylz.bizDo.cd.vo.PositionQvo;
import com.ylz.bizDo.cd.vo.PositionVo;

import java.util.List;

public interface CdPositionDao {
	
	public List<CdPosition> findAll() throws Exception;//查询全部
	public CdPosition findIsPosition(String num, String id) throws Exception;
	public List<CdPosition> findCdPosition(PositionQvo qvo) throws Exception;//职位管理分页
	public List<CdPositionEntity> findByUserId(String userId) throws Exception;
	public List<PositionVo> findPosition() throws Exception;
}
