package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**转签从表
 * Created by zzl on 2017/11/21.
 */
@Entity
@Table(name = "APP_GOTO_SIGN_FB")
public class AppGotosignFb extends BasePO{
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键id
    @Column(name = "GS_REASON_TYPE",length = 10)
    private String gsReasonType;//转签原因
    @Column(name = "GS_ID",length = 36)
    private String gsId;//转签记录主键

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGsReasonType() {
        return gsReasonType;
    }

    public void setGsReasonType(String gsReasonType) {
        this.gsReasonType = gsReasonType;
    }

    public String getGsId() {
        return gsId;
    }

    public void setGsId(String gsId) {
        this.gsId = gsId;
    }
}
