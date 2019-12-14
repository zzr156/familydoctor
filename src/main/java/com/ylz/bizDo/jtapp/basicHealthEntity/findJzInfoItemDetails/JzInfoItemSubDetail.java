/**
 * 
 */
package com.ylz.bizDo.jtapp.basicHealthEntity.findJzInfoItemDetails;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 *<p>Title:JzInfoItemSubDetail</p> 
 *<p>Description:就诊详细子信息</p>
 *<p>Company:ylz</p>
 *@author cairongjie
 *@date 2016-6-23 上午11:38:00
 */
@XStreamAlias("JzInfoItemSubDetail")
public class JzInfoItemSubDetail {
	private String serial;
	private String value;
	public JzInfoItemSubDetail(){}
	public JzInfoItemSubDetail(String serial,String value){
		this.serial=serial;
		this.value=value;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "EhrDTO [serial=" + serial + ", value=" + value + "]";
	}

}
