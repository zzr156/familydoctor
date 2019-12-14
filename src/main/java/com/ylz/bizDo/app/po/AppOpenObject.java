package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/** 服务开放中间表
 * Created by zzl on 2017/8/18.
 */
@Entity
@Table(name="APP_OPEN_OBJECT")
public class AppOpenObject extends BasePO{
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键
    @Column(name = "OPEN_SER_ID",length = 36)
    private String openSerId;//服务id
    @Column(name = "OPEN_HOSP_ID",length = 36)
    private String openHospId;//医院id

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOpenSerId() {
        return openSerId;
    }

    public void setOpenSerId(String openSerId) {
        this.openSerId = openSerId;
    }

    public String getOpenHospId() {
        return openHospId;
    }

    public void setOpenHospId(String openHospId) {
        this.openHospId = openHospId;
    }
}
