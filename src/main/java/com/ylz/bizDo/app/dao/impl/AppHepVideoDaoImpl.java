package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppHepVideoDao;
import com.ylz.bizDo.app.po.AppHepVideo;
import com.ylz.bizDo.app.po.AppSystemVsersion;
import com.ylz.bizDo.app.vo.AppHepVideoQvo;
import com.ylz.bizDo.app.vo.AppSystemVesionQvo;
import com.ylz.packaccede.allDo.SysDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HepVideo Entity 健康教育宣传视频
 *
 * @author dws 2018-11-14
 *
 */
@Service("appHepVideoDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppHepVideoDaoImpl implements AppHepVideoDao {

    @Autowired
    private SysDao sysDao;


    /**
     * 分页查询
     * @param qvo
     * @return
     */
    @Override
    public List<AppHepVideo> findListQvo(AppHepVideoQvo qvo) throws Exception{
        Map<String,Object> map = new HashMap <String,Object>();
        String sql = "SELECT * FROM APP_HEP_VIDEO  as a WHERE 1=1 ";
        if(StringUtils.isNotBlank(qvo.getVideoState())){
            map.put("VIDEO_STATE",qvo.getVideoState());
            sql += " AND a.VIDEO_STATE = :VIDEO_STATE ";
        }
        sql +=  " ORDER BY a.HS_CREATE_DATE DESC ";
        return sysDao.getServiceDo().findSqlMap(sql, map, AppHepVideo.class, qvo);
    }
}
