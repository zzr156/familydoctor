package com.ylz.bizDo.cd.po;


import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bean.BasePO;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;

/**
 * Created by PC on 2016/2/15.
 * 消息
 */
@Entity
@Table(name="CD_MSG")
public class CdMsg extends BasePO {
    @Id
    @Column(name = "id", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键
    @Column(name = "MSG_TITLE",length = 100)
    private String msgTitle;//标题
    @Column(name = "MSG_TEXT")
    private String msgText;//内容
    @Column(name = "MSG_CREATER_DATE")
    private Calendar msgCreaterDate;//创建时间
    @Column(name = "MSG_USERID",length = 36)
    private String msgUserid;//创建者id
    @Column(name = "MSG_USER_NAME",length = 36)
    private String msgUserName;//创建者
    @Column(name = "MSG_TYPE",length = 36)
    private String msgType;
    @Column(name = "MSG_CONTENT_ID",length = 36)
    private String msgContentId;//消息内容id,如送检单id
    @Column(name = "MSG_SP_STATE",length = 36)
    private String msgSpState;//送检单状态
    
    
    public String getMsgSpState() {
		return msgSpState;
	}

	public void setMsgSpState(String msgSpState) {
		this.msgSpState = msgSpState;
	}

	public String getMsgContentId() {
		return msgContentId;
	}

	public void setMsgContentId(String msgContentId) {
		this.msgContentId = msgContentId;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    public String getMsgText() {
        return msgText;
    }

    public void setMsgText(String msgText) {
        this.msgText = msgText;
    }
    @JSON(format="yyyy-MM-dd HH:mm")
    public Calendar getMsgCreaterDate() {
        return msgCreaterDate;
    }

    public void setMsgCreaterDate(Calendar msgCreaterDate) {
        this.msgCreaterDate = msgCreaterDate;
    }

    public String getMsgUserid() {
        return msgUserid;
    }

    public void setMsgUserid(String msgUserid) {
        this.msgUserid = msgUserid;
    }

    public String getMsgUserName() {
        return msgUserName;
    }

    public void setMsgUserName(String msgUserName) {
        this.msgUserName = msgUserName;
    }
  //根据msgType获取消息类别名称
  	public String getMsgTypeName() throws Exception{
  		SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
  		CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_MSGTYPE, this.getMsgType());
  		if(value!=null) {
            return value.getCodeTitle();
        }
  		return "";
  	}
    public String getUserNames(){
        String names=null;
        SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
        List<CdMsgAccepter> ls=dao.getServiceDo().loadByPk(CdMsgAccepter.class,"AccepterMsgId",this.getId());
        if(ls!=null && ls.size()>0){
            for(CdMsgAccepter s:ls){
                if(StringUtils.isBlank(names)){
                    names =  s.getAccepterName();
                }else{
                    names = names +";"+ s.getAccepterName();
                }
            }
        }
        return names;
    }

    public String getStrMsgCreateDate(){
    	if(this.getMsgCreaterDate()!=null){
    		return ExtendDate.getYMD_h_m_s(this.getMsgCreaterDate());
    	}else{
    		return "";
    	}
    }
    public void setStrMsgCreateDate(String strMsgCreateDate){
    	this.setMsgCreaterDate(ExtendDate.getCalendar(strMsgCreateDate));
    }

}
