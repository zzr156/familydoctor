package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by asus on 2018/04/13.
 */
@Entity
@Table(name = "APP_BASIC_DATA_TEMP")
public class AppBasicDataTemp extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键
    @Column(name = "BASICE_DATA_ID",length = 36)
    private String basiceDataId;//数据主键

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBasiceDataId() {
        return basiceDataId;
    }

    public void setBasiceDataId(String basiceDataId) {
        this.basiceDataId = basiceDataId;
    }
}