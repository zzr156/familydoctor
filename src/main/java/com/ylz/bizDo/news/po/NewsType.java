package com.ylz.bizDo.news.po;

import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bean.BasePO;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 新闻类别
 * @author dws
 *
 */
@Entity
@Table(name = "NEWS_TYPE")
public class NewsType extends BasePO {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
	@GeneratedValue(generator="paymentableGenerator")
    @GenericGenerator(name="paymentableGenerator",strategy="uuid2")
	private String id;//主键
	@Column(name = "type_num", length = 20)
	private String typeNum;//类别编码
	@Column(name = "type_name", length = 20)
	private String typeName;//类别名称
	@Column(name = "type_pid", length = 36)
	private String typePid;//字关联
	@Column(name = "type_state", length = 2)
	private String typeState;//是否启用
	@Column(name = "type_imageurl", length = 100)
	private String typeImageUrl;//图片路径

	// Constructors

	/** default constructor */
	public NewsType() {
	}

	/** minimal constructor */
	public NewsType(String id) {
		this.id = id;
	}


	// Property accessors
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTypeNum() {
		return this.typeNum;
	}

	public void setTypeNum(String typeNum) {
		this.typeNum = typeNum;
	}

	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	
	public String getTypePid() {
		return this.typePid;
	}

	public void setTypePid(String typePid) {
		this.typePid = typePid;
	}

	public String getTypeState() {
		return this.typeState;
	}

	public void setTypeState(String typeState) {
		this.typeState = typeState;
	}


	public String getTypeImageUrl() {
		return typeImageUrl;
	}

	public void setTypeImageUrl(String typeImageUrl) {
		this.typeImageUrl = typeImageUrl;
	}

	public NewsType(String id, String typeNum, String typeName, String typePid,
			String typeState, String typeImageUrl) {
		super();
		this.id = id;
		this.typeNum = typeNum;
		this.typeName = typeName;
		this.typePid = typePid;
		this.typeState = typeState;
		this.typeImageUrl = typeImageUrl;
	}

	public String getTypePName(){
		if(StringUtils.isNotBlank(this.getTypePid())){
			SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
			NewsType p = (NewsType)dao.getServiceDo().find(NewsType.class, this.getTypePid());
				if(p != null) {
                    return p.getTypeName();
                }
		}
		return "";
	}

}