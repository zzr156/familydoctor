package com.ylz.bizDo.cd.po;

import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bean.BasePO;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 基础数据管理实体
 */
@Entity
@Table(name = "CD_CODE")
@Cache(usage= CacheConcurrencyStrategy.READ_WRITE)//要使用hibernate的二级缓存Cache的注释一定要添加上
public class CdCode extends BasePO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
	@GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
	private String id;//主键
	
	@Column(name = "code_value", length = 4)
	private String codeValue;//值
	
	@Column(name = "code_title")
	private String codeTitle;//名称,标题
	
	@Column(name = "code_group", length = 20)
	private String codeGroup;//组名
	
	@Column(name = "code_remark", length = 100)
	private String codeRemark;//备注
	
	@Column(name = "code_sort", length = 10)
	private Integer codeSort;//排序
	
	@Column(name = "code_state", length = 1)
	private String codeState;//状态  1 为 与医保对接状态 0 为不对接

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCodeValue() {
		return codeValue;
	}

	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}

	public String getCodeTitle() {
		return codeTitle;
	}

	public void setCodeTitle(String codeTitle) {
		this.codeTitle = codeTitle;
	}

	public String getCodeGroup() {
		return codeGroup;
	}

	public void setCodeGroup(String codeGroup) {
		this.codeGroup = codeGroup;
	}

	public String getCodeRemark() {
		return codeRemark;
	}

	public void setCodeRemark(String codeRemark) {
		this.codeRemark = codeRemark;
	}

	public Integer getCodeSort() {
		return codeSort;
	}

	public void setCodeSort(Integer codeSort) {
		this.codeSort = codeSort;
	}
	
	public String getCodeState() {
		return codeState;
	}

	public void setCodeState(String codeState) {
		this.codeState = codeState;
	}


	//根据state获取部门状态名称
	public String getStateName() throws Exception{
		SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
		CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_ENABLE, this.getCodeState());
		if(value!=null) {
            return value.getCodeTitle();
        }
		return "";
	}

}