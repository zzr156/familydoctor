package com.ylz.bizDo.cd.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Created by PC on 2016/2/15.
 * 消息接收者
 */
@Entity
@Table(name="CD_MSG_ACCEPTER")
public class CdMsgAccepter extends BasePO {
    @Id
    @Column(name = "id", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键
    @Column(name = "ACCEPTER_MSG_ID",length = 36)
    private String AccepterMsgId;//消息id
    @Column(name = "ACCEPTER_USER_ID",length = 36)
    private String AccepterUserId;//接收者用户id
    @Column(name = "ACCEPTER_USER_NAME",length = 36)
    private String AccepterName;//接收者用户姓名
    @Column(name = "READ_DATE")
    private Calendar ReadDate;//读件时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccepterMsgId() {
        return AccepterMsgId;
    }

    public void setAccepterMsgId(String accepterMsgId) {
        AccepterMsgId = accepterMsgId;
    }

    public String getAccepterUserId() {
        return AccepterUserId;
    }

    public void setAccepterUserId(String accepterUserId) {
        AccepterUserId = accepterUserId;
    }

    public String getAccepterName() {
        return AccepterName;
    }

    public void setAccepterName(String accepterName) {
        AccepterName = accepterName;
    }

    public Calendar getReadDate() {
        return ReadDate;
    }

    public void setReadDate(Calendar readDate) {
        ReadDate = readDate;
    }
}
