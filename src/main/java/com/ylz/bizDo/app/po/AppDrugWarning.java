package com.ylz.bizDo.app.po;

import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bean.BasePO;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**药品存量预警设置
 * Created by zzl on 2017/6/17.
 */
@Entity
@Table(name = "APP_DRUG_WARNING")
public class AppDrugWarning extends BasePO {
    // Fields
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键
    @Column(name = "DW_DRUG_ID", length =36 )
    private String dwDrugId;//药品id
    @Column(name = "DW_WARN_NUM", length =10 )
    private String dwWarnNum;//预警次数
    @Column(name = "DW_COMMON_WARN_NUM", length =10 )
    private String dwCommonWarnNum;//通用预警次数
    @Column(name = "DW_DR_ID", length = 36)
    private String dwDrId;//医生id

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDwDrugId() {
        return dwDrugId;
    }

    public void setDwDrugId(String dwDrugId) {
        this.dwDrugId = dwDrugId;
    }

    public String getDwWarnNum() {
        return dwWarnNum;
    }

    public void setDwWarnNum(String dwWarnNum) {
        this.dwWarnNum = dwWarnNum;
    }

    public String getDwCommonWarnNum() {
        return dwCommonWarnNum;
    }

    public void setDwCommonWarnNum(String dwCommonWarnNum) {
        this.dwCommonWarnNum = dwCommonWarnNum;
    }

    public String getDwDrId() {
        return dwDrId;
    }

    public void setDwDrId(String dwDrId) {
        this.dwDrId = dwDrId;
    }

    public String getDrugName(){
        if(StringUtils.isNotBlank(this.getDwDrugId())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            AppDrug drug = (AppDrug) dao.getServiceDo().find(AppDrug.class,this.getDwDrugId());
            if(drug != null) {
                return drug.getDrugName();
            }
        }
        return "";
    }

}
