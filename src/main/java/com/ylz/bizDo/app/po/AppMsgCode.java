package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

//短信表
@Entity
@Table(name = "APP_MSG_CODE")
public class AppMsgCode extends BasePO {

    // Fields
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;
    @Column(name = "MSG_RESULT_BODY", length = 36)
    private String msgResultBody;//短信内容
    @Column(name = "MSG_LAST_TIME")
    private Calendar msgLastTime;//短信过期时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsgResultBody() {
        return msgResultBody;
    }

    public void setMsgResultBody(String msgResultBody) {
        this.msgResultBody = msgResultBody;
    }

    public Calendar getMsgLastTime() {
        return msgLastTime;
    }

    public void setMsgLastTime(Calendar msgLastTime) {
        this.msgLastTime = msgLastTime;
    }
}
