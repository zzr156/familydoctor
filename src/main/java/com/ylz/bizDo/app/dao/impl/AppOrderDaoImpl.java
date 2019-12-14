package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppOrderDao;
import com.ylz.bizDo.app.po.AppOrder;
import com.ylz.bizDo.app.po.AppSignForm;
import com.ylz.bizDo.pay.Entity.AppOrderEntity;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.util.ExtendDate;
import com.ylz.packcommon.common.util.OrderPayState;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by asus on 2017/07/24.
 */
@Service("appOrderDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppOrderDaoImpl implements AppOrderDao {


    @Autowired
    private SysDao sysDao;

    @Override
    public AppOrder findBySignId(String signId) throws Exception{
        return (AppOrder) sysDao.getServiceDo().getSessionFactory().getCurrentSession()
                .createCriteria(AppOrder.class)
                .add(Restrictions.eq("orderSignId", signId))
                .add(Restrictions.eq("orderState", OrderPayState.ORDER_DFK.getState()))
                .uniqueResult();
    }

    @Override
    public AppOrder findByOrderNo(String orderNo) throws Exception{
        return (AppOrder) sysDao.getServiceDo().getSessionFactory().getCurrentSession()
                .createCriteria(AppOrder.class)
                .add(Restrictions.eq("orderNo", orderNo))
                .uniqueResult();
    }

    @Override
    public AppOrder findByOutChargeNo(String outChargeNo) throws Exception{
        return (AppOrder) sysDao.getServiceDo().getSessionFactory().getCurrentSession()
                .createCriteria(AppOrder.class)
                .add(Restrictions.eq("orderChargeNo", outChargeNo))
                .uniqueResult();
    }

    @Override
    public AppOrderEntity findByOrderhargeNo(String outChargeNo) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("ORDER_CHARGE_NO",outChargeNo);
        String sql = "SELECT\n" +
                "\tt.ORDER_TITLE orderTitle,\n" +
                "\tt.ORDER_PAY_MEN_ACTUAL orderPayMenActual,\n" +
                "\tt.ORDER_STATE orderState,\n" +
                "\tt.ORDER_DATE orderDate,\n" +
                "\tt.ORDER_DUE_DATE orderDueDate,\n" +
                "\tt.ORDER_PAY_MENT_MODE orderPayMentMode,\n" +
                "\tt.ORDER_CHARGE_NO orderChargeNo\n" +
                "FROM\n" +
                "\tAPP_ORDER t WHERE 1=1  \n" +
                "AND t.ORDER_CHARGE_NO = :ORDER_CHARGE_NO";
        List<AppOrderEntity> ls = this.sysDao.getServiceDo().findSqlMapRVo(sql,map,AppOrderEntity.class);
        if(ls != null && ls.size() >0){
            return  ls.get(0);
        }
        return null;
    }

    @Override
    public AppOrderEntity findByOrderSignId(String signId) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("ORDER_SIGN_ID",signId);
        String sql = "SELECT\n" +
                "\tt.ORDER_TITLE orderTitle,\n" +
                "\tt.ORDER_PAY_MEN_ACTUAL orderPayMenActual,\n" +
                "\tt.ORDER_STATE orderState,\n" +
                "\tt.ORDER_DATE orderDate,\n" +
                "\tt.ORDER_DUE_DATE orderDueDate,\n" +
                "\tt.ORDER_PAY_MENT_MODE orderPayMentMode,\n" +
                "\tt.ORDER_CHARGE_NO orderChargeNo\n" +
                "FROM\n" +
                "\tAPP_ORDER t WHERE 1=1  \n" +
                "AND t.ORDER_SIGN_ID = :ORDER_SIGN_ID";
        List<AppOrderEntity> ls = this.sysDao.getServiceDo().findSqlMapRVo(sql,map,AppOrderEntity.class);
        if(ls != null && ls.size() >0){
            return  ls.get(0);
        }
        return null;
    }

    @Override
    public boolean updateOrder(String outChargeNo, String payTime, String channel) throws Exception{
        boolean result = true;
        try{
            AppOrder order = sysDao.getAppOrderDao().findByOutChargeNo(outChargeNo);
            AppSignForm form = (AppSignForm)sysDao.getServiceDo().find(AppSignForm.class,order.getOrderSignId());
            order.setOrderDueDate(ExtendDate.getCalendarYmshms(payTime));
            order.setOrderPayMentMode(channel);
            order.setOrderState(OrderPayState.ORDER_YFK.getState());
            sysDao.getServiceDo().modify(order);
            form.setSignPayState("1");//已缴费
            sysDao.getServiceDo().modify(form);
        }catch (Exception e){
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    @Override
    public List<AppOrderEntity> findByOrderCreateIdList(String patientId) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("ORDER_CREATE_ID",patientId);
        String sql = "SELECT\n" +
                "\tt.ORDER_TITLE orderTitle,\n" +
                "\tt.ORDER_PAY_MEN_ACTUAL orderPayMenActual,\n" +
                "\tt.ORDER_STATE orderState,\n" +
                "\tt.ORDER_DATE orderDate,\n" +
                "\tt.ORDER_DUE_DATE orderDueDate,\n" +
                "\tt.ORDER_PAY_MENT_MODE orderPayMentMode,\n" +
                "\tt.ORDER_CHARGE_NO orderChargeNo\n" +
                "FROM\n" +
                "\tAPP_ORDER t WHERE 1=1  \n" +
                "AND t.ORDER_CREATE_ID = :ORDER_CREATE_ID";
        List<AppOrderEntity> ls = this.sysDao.getServiceDo().findSqlMapRVo(sql,map,AppOrderEntity.class);
        return ls;
    }
}
