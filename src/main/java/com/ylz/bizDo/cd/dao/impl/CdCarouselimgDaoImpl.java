package com.ylz.bizDo.cd.dao.impl;

import com.ylz.bizDo.cd.dao.CdCarouselimgDao;
import com.ylz.bizDo.cd.po.CdCarouselimg;
import com.ylz.bizDo.cd.vo.CdCarouselimgQvo;
import com.ylz.packaccede.allDo.SysDao;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("cdCarouselimgDao")
@Transactional(rollbackForClassName={"Exception"})
public class CdCarouselimgDaoImpl  implements CdCarouselimgDao{

	@Autowired
	private SysDao sysDao;
	

	public List<CdCarouselimg> findEmperorCarouselimgList(CdCarouselimgQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT * FROM CD_CAROUSEL_IMG a WHERE 1=1 ";
        List ls = sysDao.getServiceDo().findSqlMap(sql, map, CdCarouselimg.class, qvo);
        if (ls!=null) {
            return ls;
        }
        return  null;
	}
    public List<CdCarouselimg> findAllOrdasc() throws Exception{
        return sysDao.getServiceDo()
                .getSessionFactory()
                .getCurrentSession()
                .createCriteria(CdCarouselimg.class)
                .addOrder(Order.asc("px"))
                .list();
    }


}
