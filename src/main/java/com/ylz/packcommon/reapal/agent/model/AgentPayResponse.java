package com.ylz.packcommon.reapal.agent.model;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-7-20
 * Time: 下午7:53
 * 代付请求对象类
 */
public class AgentPayResponse implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    private String batch_no;
    private String detail_no;
    protected String merchant_id; // 商户ID
    protected String version; // 版本号
    protected String sign_type; // 签名类型
    protected String sign; // 签名
    protected String charset; // 编码
	protected String batch_status;//批次状态

    protected String result_code;
    protected String result_msg;
    protected String content;
    private String next_tag;//下一页标识（用于批次较多时的分页查询）
	public String getBatch_no() {
		return batch_no;
	}
	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}
	public String getDetail_no() {
		return detail_no;
	}
	public void setDetail_no(String detail_no) {
		this.detail_no = detail_no;
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
	public String getResult_code() {
		return result_code;
	}
	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}
	public String getResult_msg() {
		return result_msg;
	}
	public void setResult_msg(String result_msg) {
		this.result_msg = result_msg;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getNext_tag() {
		return next_tag;
	}
	public void setNext_tag(String next_tag) {
		this.next_tag = next_tag;
	}

	public String getBatch_status() {
		return batch_status;
	}

	public void setBatch_status(String batch_status) {
		this.batch_status = batch_status;
	}

	@Override
	public String toString() {
		return "AgentPayResponse{" +
				"batch_no='" + batch_no + '\'' +
				", detail_no='" + detail_no + '\'' +
				", merchant_id='" + merchant_id + '\'' +
				", version='" + version + '\'' +
				", sign_type='" + sign_type + '\'' +
				", sign='" + sign + '\'' +
				", charset='" + charset + '\'' +
				", batch_status='" + batch_status + '\'' +
				", result_code='" + result_code + '\'' +
				", result_msg='" + result_msg + '\'' +
				", content='" + content + '\'' +
				", next_tag='" + next_tag + '\'' +
				'}';
	}
}

