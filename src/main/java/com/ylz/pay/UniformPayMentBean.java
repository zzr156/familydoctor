package com.ylz.pay;

import com.ylz.bizDo.app.po.AppHospDept;
import com.ylz.bizDo.app.po.AppOrder;
import com.ylz.bizDo.app.po.AppSerial;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.util.JsonUtil;
import com.ylz.packcommon.common.util.PropertiesUtil;
import com.ylzinfo.onepay.sdk.OnepayClient;
import com.ylzinfo.onepay.sdk.domain.ChargeGoodsDetailVO;
import com.ylzinfo.onepay.sdk.domain.ChargeParams;
import com.ylzinfo.onepay.sdk.domain.ChargeResult;
import com.ylzinfo.onepay.sdk.domain.ResponseParams;
import com.ylzinfo.onepay.sdk.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统一支付
 */
@Component("uniformPayMentBean")
public class UniformPayMentBean {

    @Autowired
    private SysDao sysDao;

    /**
     * 添加订单
     */
    public boolean payAddOrder(){
        boolean flag = false;

        return flag;
    }

    /**
     * 创建收银台连接，可直接在浏览器打开
     */
    public void payCreateCashier(String deptId,String type) {
        try {
            OnepayClient onepayClient = new OnepayClient(PropertiesUtil.getConfValue("onepayUrl"),
                                    PropertiesUtil.getConfValue("appId"), PropertiesUtil.getConfValue("appSecret"));
            /**
             * 创建收银台参数
             */
            String outTradeNo = "";
            if(type.equals("1")){
                outTradeNo = "APP";
            }

            ChargeParams payParams = new ChargeParams();
            //获取订单号
            AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,deptId);
            AppSerial serial = sysDao.getAppSignformDao().getAppSerial(dept.getHospAreaCode().substring(0, 4), "order");
            if(serial!=null) {
                Map<String,Object> bcnum = sysDao.getAppSignformDao().getNum(serial.getSerialNo(),"");
                serial.setSerialNo(bcnum.get("old").toString());
                sysDao.getServiceDo().modify(serial);
                outTradeNo = outTradeNo+bcnum.get("new").toString();
            }

            // 订单金额（分）
            payParams.setChargeAmt(1);
            // 商户订单号
            payParams.setOutChargeNo(outTradeNo);
            // 支付完成后跳回商户的页面
            payParams.setReturnUrl("http://192.168.30.55:7077/family-doctor/open/test.jsp");
            // 商品展示的页面
            payParams.setShowUrl("");
            // 订单标题，Subject格式 : 应用简称-订单标题摘要
            payParams.setSubject("易惠-收银台");
            // 商户订单日期，yyyyMMddHHmmss
            payParams.setOutChargeTime(DateUtil.getCurrentDateTime());

            // 商户自定义附加参数 原样返回
            Map<String, String> optional = new HashMap<String, String>();
            optional.put("name", "小易");
            optional.put("color", "red");
            payParams.setOptional(optional);

            // 订单详情
            List<ChargeGoodsDetailVO> opChargeGoodsDtls = new ArrayList<ChargeGoodsDetailVO>();
            ChargeGoodsDetailVO opChargeGoodsDtlVO = new ChargeGoodsDetailVO();
            opChargeGoodsDtlVO.setGoodsName("商户名称"); // 商品名称
            opChargeGoodsDtlVO.setGoodsInfo("商户信息"); // 商品信息
            opChargeGoodsDtlVO.setPrice(BigDecimal.ONE); // 商品价格
            opChargeGoodsDtlVO.setOrgPrice(BigDecimal.ONE); // 商品原价
            opChargeGoodsDtlVO.setQuantity("1"); // 商品数量
            opChargeGoodsDtlVO.setChargeAmt(BigDecimal.ONE); // 明细金额
            opChargeGoodsDtlVO.setRemark("备注"); // 明细备注
            payParams.setChargeGoodsDetail(opChargeGoodsDtls);

            String cashierUrl = onepayClient.createCashier(payParams, false);
            System.out.println(cashierUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 手机统一下载订单
     * @param order
     */
    public String  AppInitCashier(AppOrder order) {

        try {

            OnepayClient onepayClient = new OnepayClient(PropertiesUtil.getConfValue("onepayUrl"),
                    PropertiesUtil.getConfValue("appId"), PropertiesUtil.getConfValue("appSecret"));
            /**
             * 初始化收银台参数
             */
            ChargeParams payParams = new ChargeParams();
            Integer amt = (int)(order.getOrderPayMenActual().doubleValue()*100);
            // 订单金额（分）
            payParams.setChargeAmt(amt);
            // 商户订单号
            payParams.setOutChargeNo(order.getOrderNo());
            // 支付完成后跳回商户的页面
//            payParams.setReturnUrl("http://192.168.30.55:7077/family-doctor/open/test.jsp");
            // 商品展示的页面
//            payParams.setShowUrl("");
            // 订单标题，Subject格式 : 应用简称-订单标题摘要
            payParams.setSubject(order.getOrderTitle());
            // 商户订单日期，yyyyMMddHHmmss
            payParams.setOutChargeTime(DateUtil.getCurrentDateTime());

            // 商户自定义附加参数，原样返回，平台仅做保存
            Map<String, String> optional = new HashMap<String, String>();
            optional.put("signId", order.getOrderSignId());
            optional.put("orderId", order.getId());
            payParams.setOptional(optional);

            // 订单详情
            List<ChargeGoodsDetailVO> opChargeGoodsDtls = new ArrayList<ChargeGoodsDetailVO>();
            ChargeGoodsDetailVO opChargeGoodsDtlVO = new ChargeGoodsDetailVO();
            opChargeGoodsDtlVO.setGoodsName("个人签约支付"); // 商品名称
            opChargeGoodsDtlVO.setGoodsInfo("个人签约支付"); // 商品信息
            opChargeGoodsDtlVO.setPrice(order.getOrderPayMenActual()); // 商品价格
            opChargeGoodsDtlVO.setOrgPrice(order.getOrderPayMen()); // 商品原价
            opChargeGoodsDtlVO.setQuantity("1"); // 商品数量
            opChargeGoodsDtlVO.setChargeAmt(order.getOrderPayMenActual()); // 明细金额
            opChargeGoodsDtlVO.setRemark("个人签约支付"); // 明细备注
            opChargeGoodsDtls.add(opChargeGoodsDtlVO);
            payParams.setChargeGoodsDetail(opChargeGoodsDtls);
            ResponseParams<ChargeResult> cParams = onepayClient.initCashier(payParams);
            String json = JsonUtil.toJson(cParams);
            order.setOrderChargeData(json);
            if(cParams.getParam() != null) {
                order.setOrderChargeNo(cParams.getParam().getChargeNo());
            }
            this.sysDao.getServiceDo().modify(order);
            return  json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



}
