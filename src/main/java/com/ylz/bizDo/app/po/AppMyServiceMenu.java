package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 我的菜单服务
 */
@Entity
@Table(name = "APP_MY_SERVICE_MENU")
public class AppMyServiceMenu extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键
    @Column(name = "SERVICE_DR_PATIENT_ID", length = 36)
    private String serviceDrPatientId;//所属用户
    @Column(name = "SERVICE_MENU_ID", length = 36)
    private String serviceMenuId;//菜单主键
    @Column(name = "SERVICE_SORT", length = 20)
    private Integer serviceSort;//排序


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceDrPatientId() {
        return serviceDrPatientId;
    }

    public void setServiceDrPatientId(String serviceDrPatientId) {
        this.serviceDrPatientId = serviceDrPatientId;
    }

    public String getServiceMenuId() {
        return serviceMenuId;
    }

    public void setServiceMenuId(String serviceMenuId) {
        this.serviceMenuId = serviceMenuId;
    }

    public Integer getServiceSort() {
        return serviceSort;
    }

    public void setServiceSort(Integer serviceSort) {
        this.serviceSort = serviceSort;
    }
}
