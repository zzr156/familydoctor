package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * poss机绑定血糖仪和血压计信息
 * Created by zzl on 2018/8/20.
 */
@Entity
@Table(name = "APP_POSS_BINDING")
public class AppPossBinding extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键
    @Column(name = "POSS_SN",length = 50)
    private String possSn;//poss机sn码
    @Column(name = "GLUCOMETER_SN",length = 50)
    private String glucometerSn;//血糖仪sn码
    @Column(name = "SPHYGMOMANOMETER_SN",length = 50)
    private String sphygmomanometerSn;//血压计sn码

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPossSn() {
        return possSn;
    }

    public void setPossSn(String possSn) {
        this.possSn = possSn;
    }

    public String getGlucometerSn() {
        return glucometerSn;
    }

    public void setGlucometerSn(String glucometerSn) {
        this.glucometerSn = glucometerSn;
    }

    public String getSphygmomanometerSn() {
        return sphygmomanometerSn;
    }

    public void setSphygmomanometerSn(String sphygmomanometerSn) {
        this.sphygmomanometerSn = sphygmomanometerSn;
    }
}
