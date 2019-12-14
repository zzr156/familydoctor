package com.ylz.bizDo.cd.dao;

import com.ylz.bizDo.cd.po.CdShortMessage;
import com.ylz.bizDo.cd.vo.CdShortMessageQvo;

import java.util.List;

public interface CdShortMessageDao {

	public List<CdShortMessage> findListQvo(CdShortMessageQvo qvo) throws Exception;

}
