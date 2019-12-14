package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by hzk on 2017/6/21.
 */
/**
 * 流水号表
 */
@Entity
@Table(name = "APP_SERIAL")
public class AppSerial extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键
    @Column(name ="SERIAL_NO", length = 50)
    private String serialNo;//流水号
    @Column(name ="SERIAL_CODE", length = 30)
    private String serialCode;//编码
    @Column(name ="SERIAL_NAME", length = 50)
    private String serialName;//中文描述
    @Column(name ="SERIAL_TYPE", length = 15)
    private String serialType;//类型

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getSerialCode() {
        return serialCode;
    }

    public void setSerialCode(String serialCode) {
        this.serialCode = serialCode;
    }

    public String getSerialName() {
        return serialName;
    }

    public void setSerialName(String serialName) {
        this.serialName = serialName;
    }

    public String getSerialType() {
        return serialType;
    }

    public void setSerialType(String serialType) {
        this.serialType = serialType;
    }
}
