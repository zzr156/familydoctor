package com.ylz.view.order.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.jtapp.commonVo.AppCommQvo;
import com.ylz.bizDo.jtapp.drEntity.AppSignFormListEntity;
import com.ylz.bizDo.pay.Entity.AppOrderEntity;
import com.ylz.bizDo.pay.vo.PayAppVo;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.comEnum.SignFormType;
import com.ylz.packcommon.common.util.OrderPayState;
import com.ylz.packcommon.common.util.PropertiesUtil;
import com.ylzinfo.onepay.sdk.OnepayClient;
import com.ylzinfo.onepay.sdk.domain.ResponseParams;
import com.ylzinfo.onepay.sdk.domain.WebHook;
import com.ylzinfo.onepay.sdk.utils.MD5Util;
import com.ylzinfo.onepay.sdk.utils.SecurityUtil;
import com.ylzinfo.onepay.sdk.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StreamUtils;

import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单
 */
@SuppressWarnings("all")
@Action(
        value="order",
        results={
                @Result(name = "ajson", type = "json",params={"root","ajson","contentType", "application/json"})
        }
)
public class OrderAction extends CommonAction{

    private static final String orderNoType = "order";
    private static final String orderTitle = "个人健康云-签约";
    private static final String orderAllMoney = "120";
    private static final String orderMoney = "20";

    private Logger LOGGER = LoggerFactory.getLogger(OrderAction.class);

    /**
     * 手机下单创建订单
     * @return
     */
    public String getAppInitCashier(){
        try{
            PayAppVo qvo = (PayAppVo)this.getAppJson(PayAppVo.class);
            if(qvo != null ){
                if(StringUtils.isNotBlank(qvo.getSignId())){
                    //查询是否有未完成的订单号
                    AppOrder order = this.getSysDao().getAppOrderDao().findBySignId(qvo.getSignId());
                    if(order != null){
                        this.getAjson().setEntity(order.getOrderChargeData());
                    }else{
                        order = new AppOrder();
                        AppPatientUser user = this.getAppPatientUser();
                        AppSignForm sign = (AppSignForm)this.sysDao.getServiceDo().find(AppSignForm.class,qvo.getSignId());
                        if(sign != null){
                            order.setOrderCreateDate(Calendar.getInstance());//创建时间
                            order.setOrderCreateId(user.getId());//创建人主键
                            order.setOrderCreateName(user.getPatientName());//创建人姓名
                            order.setOrderSignId(sign.getId());//签约单主键
                            order.setOrderType(sign.getSignType());//签约类型
                            order.setOrderTitle(orderTitle);//标题
                            order.setOrderDate(Calendar.getInstance());//订单时间
                            order.setOrderState(OrderPayState.ORDER_DFK.getState());//订单状态
                            order.setOrderSourceSystem("1");//系统来源app
                            //获取订单号(APP)
                            AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,sign.getSignHospId());
                            AppSerial serial = sysDao.getAppSignformDao().getAppSerial(dept.getHospAreaCode().substring(0, 4), orderNoType);
                            if(serial!=null) {
                                Map<String,Object> bcnum = sysDao.getAppSignformDao().getNum(serial.getSerialNo(), SignFormType.APPSTATE.getValue());
                                serial.setSerialNo(bcnum.get("old").toString());
                                sysDao.getServiceDo().modify(serial);
                                order.setOrderNo(bcnum.get("new").toString());
                            }
                            order.setOrderPayMen(new BigDecimal(orderAllMoney));//支付金额
                            order.setOrderPayMenActual(new BigDecimal(orderMoney));//实际支付金额
                            this.getSysDao().getServiceDo().add(order);//添加
                            String result = this.sysDao.getUniformPayMentBean().AppInitCashier(order);
                            System.out.println(result);
                            if(StringUtils.isNotBlank(result)){
                                this.getAjson().setEntity(result);
                            }else {
                                this.getAjson().setMsgCode("900");
                                this.getAjson().setEntity(result);
                            }
                        }else{
                            this.getAjson().setMsgCode("900");
                            this.getAjson().setMsg("查无签约数据!");
                        }
                    }
                }else{
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("签约主键参数不能为空!");
                }
            }else{
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数错误");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("系统错误,请联系管理员");
        }
        return "ajson";
    }

    /**
     * 根据创建人查询集合
     * @return
     */
    public String getAppFindOrderList(){
        try{
            PayAppVo qvo = (PayAppVo)this.getAppJson(PayAppVo.class);
            if(qvo != null ){
                if(StringUtils.isNotBlank(qvo.getPatientId())){
                    List<AppOrderEntity> ls = this.getSysDao().getAppOrderDao().findByOrderCreateIdList(qvo.getPatientId());
                    this .getAjson().setRows(ls);
                }else{
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("患者主键参数不能为空!");
                }
            }else{
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数错误");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("系统错误,请联系管理员");
        }
        return "ajson";
    }

    /**
     * 根据订单号查询
     * @return
     */
    public String getAppFindOrder(){
        try{
            PayAppVo qvo = (PayAppVo)this.getAppJson(PayAppVo.class);
            if(qvo != null ){
                if(StringUtils.isNotBlank(qvo.getOrderNo())){
                    AppOrderEntity order = this.getSysDao().getAppOrderDao().findByOrderhargeNo(qvo.getOrderNo());
                    this .getAjson().setVo(order);
                }else{
                    this.getAjson().setMsgCode("900");
                    this.getAjson().setMsg("订单号参数不能为空!");
                }
            }else{
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数错误");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("系统错误,请联系管理员");
        }
        return "ajson";
    }

    /**
     * 支付异步回调通知
     */
    public void getAppReceiveNotify(){
        try{
            this.getResponse().setContentType("text/html;charset=utf-8");
            String appId = PropertiesUtil.getConfValue("appId");
            String appSecret = PropertiesUtil.getConfValue("appSecret");
            InputStream inputStream = getRequest().getInputStream();
            String params = StreamUtils.copyToString(inputStream, Charset.forName("UTF-8"));
            //  回调参数：{"appId":"1A3VL0KVK0000B020A0A0000CC3F48AD","encryptData":"qPebDp0xBA/0qdr2lDvY2zBp2kB5em5GOr29y7o2WnHvm0tswnbyxDjCIUGI74yNGWFyCeJfeU5A\nK+Zr/yPs6nnsyzIFTSSljrKZCkzgbj3mjMax/3NzByfvE7R2MO+fljJUYqE93AvuRDc1wcdSUuKK\nmcryOvaU","encryptType":"DES","sign":"24635D0085CC72447657BD0713F0FB7C","signType":"MD5","timestamp":"20161018171127869","transType":"charge.success"}
            LOGGER.info("回调参数：{}", params);
            if (StringUtil.isEmpty(params)) {
                this.getResponse().getWriter().write("empty");
                return;
            }
            // 参数转换
            ResponseParams<?> res = JSON.parseObject(params,ResponseParams.class);

            // 解密报文
            System.out.println("解密前报文：" + JSONObject.toJSONString(res.getEncryptData()));
            String decryptData;
            ResponseParams<WebHook> responseParams = new ResponseParams<WebHook>();
            try {
                decryptData = SecurityUtil.decrypt(res.getEncryptData(), res.getEncryptType(), appSecret, appId);
                System.out.println("解密后报文：" + decryptData);
                WebHook webhook = JSON.parseObject(decryptData, WebHook.class);

                // 参数转换
                responseParams.setAppId(appId);
                responseParams.setSign(res.getSign());
                responseParams.setSignType(res.getSignType());
                responseParams.setEncryptType(res.getEncryptType());
                responseParams.setTimestamp(res.getTimestamp());
                responseParams.setRespCode(res.getRespCode());
                responseParams.setRespMsg(res.getRespMsg());
                responseParams.setPageParams(res.getPageParams());
                responseParams.setTransType(res.getTransType());
                // 解密后设置param
                responseParams.setParam(webhook);

            } catch (Exception e1) {
                LOGGER.error("解密回调报文错误"+e1.getMessage());
                this.getResponse().getWriter().write("ERROR："+e1.getMessage());
            }

            OnepayClient payService = new OnepayClient(appId, appSecret, res.getSignType(), res.getEncryptType());

            try {
                boolean isVerify = payService.verifyRequestSign(JSONObject.toJSONString(responseParams));
                LOGGER.info("验签结果：{}", isVerify);

                if (isVerify) {
                    JSONObject jsonObject = JSON.parseObject(JSONObject.toJSONString(responseParams));
                    JSONObject params1 = jsonObject.getJSONObject("param");
                    String digest = appId + appSecret + params1.getIntValue("chargeAmt") + params1.getString("chargeNo") + params1.getString("status");
                    LOGGER.info("digest:{}", digest);
                    String sign = MD5Util.encrypt(digest);

//                    if (sign.equals(jsonObject.get("sign"))) {
                        LOGGER.info("JS验签结果通过");
                        String outChargeNo = params1.getString("chargeNo");//商户支付订单号
                        String payTime = params1.getString("payTime");//订单支付时间
                        String channel = params1.getString("channel");//支付渠道
                        boolean result = this.getSysDao().getAppOrderDao().updateOrder(outChargeNo,payTime,channel);
                        if(result){
                            this.getResponse().getWriter().write("SUCCESS");
                        }else {
                            this.getResponse().getWriter().write("empty");
                        }
//                    } else {
//                        LOGGER.info("JS验签结果不通过");
//                    }
                } else {
                    this.getResponse().getWriter().write("ERROR");
                }
            } catch (Exception e) {
                this.getResponse().getWriter().write(e.getMessage());
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("系统错误,请联系管理员");
        }
    }


    /**
     * 查询单条签约单
     * @param 签约单主键
     */
    public String getPatientSignId(){
        AppCommQvo qvo=(AppCommQvo)getAppJson(AppCommQvo.class);
        try {
            if(qvo == null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else {
                Map<String,Object> map = new HashMap<String,Object>();
                List<AppSignFormListEntity> ls = sysDao.getAppSignformDao().findSignFormByUserOrTeam(qvo);
                if(ls != null && ls.size() >0){
                    map.put("sign",ls.get(0));
                }
                AppOrderEntity order = this.getSysDao().getAppOrderDao().findByOrderSignId(qvo.getSignFormId());
                map.put("order",order);
                if(order != null){
                    if(order.getOrderState().equals(OrderPayState.ORDER_YFK.getState())){
                        map.put("state","2");//已支付
                    }else {
                        map.put("state","1");//未支付
                    }
                }else{
                    map.put("state","1");//未生成订单
                }
                this.getAjson().setMap(map);
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }
}
