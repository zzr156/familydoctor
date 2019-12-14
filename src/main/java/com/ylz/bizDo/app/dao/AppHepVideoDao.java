package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.po.AppHepVideo;
import com.ylz.bizDo.app.vo.AppHepVideoQvo;

import java.util.List;

/**
 * HepVideo Entity 健康教育宣传视频
 *
 * @author dws 2018-11-14
 *
 */
public interface AppHepVideoDao {

    /**
     * 分页查询
     * @param qvo
     * @return
     */
    public List<AppHepVideo> findListQvo(AppHepVideoQvo qvo) throws Exception;
}
