package com.ylz.bizDo.plan.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**通用随访疾病史记录
 * Created by zzl on 2017/11/28.
 */
@Entity
@Table(name = "APP_GENERAL_DISEASE_TABLE")
public class AppGeneralDiseaseTable extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;
    @Column(name = "GDT_GEN_ID",length = 36)
    private String gdtGenId;//通用随访外键
    @Column(name = "GDT_DISEASE_VALUE",length = 2)
    private String gdtDiseaseValue;//疾病值 1无 2高血压 3糖尿病 4冠心病 5慢性阻塞性肺疾病 6恶性肿瘤 7脑卒中 8严重精神障碍 9结核病 10肝炎 11其他法定传染病 12职业病 13其他
    @Column(name = "GDT_DISEASE_TITLE",length = 100)
    private String gdtDiseaseTitle;//输入的疾病名称
    @Column(name = "GDT_CONFIRMED_DATE")
    private Calendar gdtConfirmedDate;//确诊时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGdtGenId() {
        return gdtGenId;
    }

    public void setGdtGenId(String gdtGenId) {
        this.gdtGenId = gdtGenId;
    }

    public String getGdtDiseaseValue() {
        return gdtDiseaseValue;
    }

    public void setGdtDiseaseValue(String gdtDiseaseValue) {
        this.gdtDiseaseValue = gdtDiseaseValue;
    }

    public String getGdtDiseaseTitle() {
        return gdtDiseaseTitle;
    }

    public void setGdtDiseaseTitle(String gdtDiseaseTitle) {
        this.gdtDiseaseTitle = gdtDiseaseTitle;
    }

    public Calendar getGdtConfirmedDate() {
        return gdtConfirmedDate;
    }

    public void setGdtConfirmedDate(Calendar gdtConfirmedDate) {
        this.gdtConfirmedDate = gdtConfirmedDate;
    }
}
