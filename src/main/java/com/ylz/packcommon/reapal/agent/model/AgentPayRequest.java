package com.ylz.packcommon.reapal.agent.model;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-7-20
 * Time: 下午7:53
 * 代付请求对象类
 */
public class AgentPayRequest implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    protected String merchant_id; // 商户ID
    protected String version; // 版本号
    protected String sign_type; // 签名类型
    protected String sign; // 签名
    protected String charset; // 编码
    protected String trans_time; // 交易时间
    protected String notify_url; // 商户后台系统的回调地址
    private String batch_no; // 批次号
    private String batch_count;// 批次总数量
    private String batch_amount;// 批次总金额
    private String pay_type;// 紧急程度，0加急/1普通,默认为1
    private String content;// 批次明细
    // 序号,银行账户,开户名,开户行,分行,支行,公/私,金额,币种,省,市,手机号,证件类型,证件号,用户协议号,商户订单号,备注
    private String next_tag;//下一页标识（用于批次较多时的分页查询）
    private String detail_no; // 批次流水号
    
    
	public String getNext_tag() {
		return next_tag;
	}
	public void setNext_tag(String next_tag) {
		this.next_tag = next_tag;
	}
	public String getMerchant_id() {
		return merchant_id;
	}
	public void setMerchant_id(String merchant_id) {
		this.merchant_id = merchant_id;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	public String getTrans_time() {
		return trans_time;
	}
	public void setTrans_time(String trans_time) {
		this.trans_time = trans_time;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public String getBatch_no() {
		return batch_no;
	}
	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}
	public String getBatch_count() {
		return batch_count;
	}
	public void setBatch_count(String batch_count) {
		this.batch_count = batch_count;
	}
	public String getBatch_amount() {
		return batch_amount;
	}
	public void setBatch_amount(String batch_amount) {
		this.batch_amount = batch_amount;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDetail_no() {
		return detail_no;
	}
	public void setDetail_no(String detail_no) {
		this.detail_no = detail_no;
	}
	
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	@Override
	public String toString() {
		return "AgentPayRequest [merchant_id=" + merchant_id + ", version="
				+ version + ", sign_type=" + sign_type + ", sign=" + sign
				+ ", charset=" + charset + ", trans_time=" + trans_time
				+ ", notify_url=" + notify_url + ", batch_no=" + batch_no
				+ ", batch_count=" + batch_count + ", batch_amount="
				+ batch_amount + ", pay_type="
						+ pay_type + ", content=" + content + ", next_tag="
				+ next_tag + ", detail_no=" + detail_no + "]";
	}

}

