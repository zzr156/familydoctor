package com.ylz.bizDo.cd.dao;

import com.ylz.bizDo.cd.po.CdCarouselimg;
import com.ylz.bizDo.cd.vo.CdCarouselimgQvo;

import java.util.List;

public interface CdCarouselimgDao  {

	public List<CdCarouselimg> findEmperorCarouselimgList(CdCarouselimgQvo Qvo) throws Exception;
    public List<CdCarouselimg> findAllOrdasc() throws Exception;
}
