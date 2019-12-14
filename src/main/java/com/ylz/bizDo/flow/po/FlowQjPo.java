//package com.ylz.bizDo.flow.po;
//
//import java.util.Calendar;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
//import org.hibernate.annotations.GenericGenerator;
//
//import com.ylz.packcommon.common.bean.BasePO;
//
///**
// * Created by PC on 2015/11/11.
// */
//@Entity
//@Table(name="FL_QJ")
//public class FlowQjPo  extends BasePO {
//    @Id
//    @Column(name = "userId", unique = true, nullable = false, length = 36)
//    @GeneratedValue(generator = "paymentableGenerator")
//    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
//    private String id;//ID
//    @Column(name = "qjdata")
//    private Calendar qjdata;
//    @Column(name = "nr")
//    private String nr;
//    @Column(name = "days")
//    private String days;
//    @Column(name = "squser")
//    private String squser;
//    @Column(name = "flowkey")
//    private String flowkey;
//    @Column(name = "flowpid")
//    private String flowpid;
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public Calendar getQjdata() {
//        return qjdata;
//    }
//
//    public void setQjdata(Calendar qjdata) {
//        this.qjdata = qjdata;
//    }
//
//    public String getNr() {
//        return nr;
//    }
//
//    public void setNr(String nr) {
//        this.nr = nr;
//    }
//
//    public String getDays() {
//        return days;
//    }
//
//    public void setDays(String days) {
//        this.days = days;
//    }
//
//    public String getSquser() {
//        return squser;
//    }
//
//    public void setSquser(String squser) {
//        this.squser = squser;
//    }
//
//    public String getFlowkey() {
//        return flowkey;
//    }
//
//    public void setFlowkey(String flowkey) {
//        this.flowkey = flowkey;
//    }
//
//    public String getFlowpid() {
//        return flowpid;
//    }
//
//    public void setFlowpid(String flowpid) {
//        this.flowpid = flowpid;
//    }
//}
