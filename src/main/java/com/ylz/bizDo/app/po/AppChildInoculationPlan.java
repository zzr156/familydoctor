package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

    /**儿童计划免疫表
 * Created by zzl on 2017/7/21.
 */
@Entity
@Table(name = "APP_CHILD_INOCULATION_PLAN ")
public class AppChildInoculationPlan extends BasePO {

    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键
    @Column(name = "INOCULATION_NUM",length = 10 )
    private String inoculationNum;//接种针次
    @Column(name = "INOCULATION_DATE")
    private Calendar inoculationDate;// 接种日期
    @Column(name = "INOCULATION_CODE",length = 50)
    private String inoculationCode;//接种疫苗编码
    @Column(name = "INOCULATION_NAME",length = 100)
    private String inoculationName;// 接种疫苗简称
    @Column(name = "INOCULATION_MYKH",length = 100)
    private String inouclationMykh;//免疫卡号
    @Column(name = "INOCULATION_USER_ID",length = 36)
    private String inoculationUserId;//所属用户主键
    @Column(name = "INOCULATION_TX_STATE",length = 10)
    private String inoculationTxState="0";//是否发送提醒 1已发送 0 未发送

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInoculationNum() {
        return inoculationNum;
    }

    public void setInoculationNum(String inoculationNum) {
        this.inoculationNum = inoculationNum;
    }

    public Calendar getInoculationDate() {
        return inoculationDate;
    }

    public void setInoculationDate(Calendar inoculationDate) {
        this.inoculationDate = inoculationDate;
    }

    public String getInoculationCode() {
        return inoculationCode;
    }

    public void setInoculationCode(String inoculationCode) {
        this.inoculationCode = inoculationCode;
    }

    public String getInoculationName() {
        return inoculationName;
    }

    public void setInoculationName(String inoculationName) {
        this.inoculationName = inoculationName;
    }

    public String getInoculationUserId() {
        return inoculationUserId;
    }

    public void setInoculationUserId(String inoculationUserId) {
        this.inoculationUserId = inoculationUserId;
    }

    public String getInouclationMykh() {
        return inouclationMykh;
    }

    public void setInouclationMykh(String inouclationMykh) {
        this.inouclationMykh = inouclationMykh;
    }

    public String getInoculationTxState() {
        return inoculationTxState;
    }

    public void setInoculationTxState(String inoculationTxState) {
        this.inoculationTxState = inoculationTxState;
    }
}
