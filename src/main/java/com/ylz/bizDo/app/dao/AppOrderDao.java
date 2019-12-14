package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.po.AppOrder;
import com.ylz.bizDo.pay.Entity.AppOrderEntity;

import java.util.List;

/**
 * Created by asus on 2017/07/24.
 */
public interface AppOrderDao {

    public AppOrder findBySignId(String signId) throws Exception;

    public AppOrder findByOrderNo(String orderNo) throws Exception;

    public AppOrder findByOutChargeNo(String outChargeNo) throws Exception;

    public boolean updateOrder(String outChargeNo, String payTime, String channel) throws Exception;

    public AppOrderEntity findByOrderhargeNo(String outChargeNo) throws Exception;

    public AppOrderEntity findByOrderSignId(String signId) throws Exception;

    public List<AppOrderEntity> findByOrderCreateIdList(String patientId) throws Exception;
}
