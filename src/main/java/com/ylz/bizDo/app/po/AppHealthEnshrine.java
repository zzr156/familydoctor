package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/** 健康教育收藏表
 * Created by zzl on 2017/6/22.
 */
@Entity
@Table(name = "APP_HEALTH_ENSHRINE")
public class AppHealthEnshrine extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键
    @Column(name = "HEN_USER_ID", length = 36)
    private String henUserId;//收藏用户
    @Column(name = "HEN_HEALTH_ID",length = 36)
    private String henHealthId;//健康教育id
    @Column(name = "HEN_USER_NAME", length = 50)
    private String henUserName;//用户姓名
    @Column(name = "HEN_HEALTH_TITLE", length = 200)
    private String henHealthTitle;//健康教育标题
    @Column(name = "HEN_IMAGE_URL", length = 200)
    private String henImageUrl;//健康教育图片
    @Column(name = "HEN_CONTENT")
    private String henContent;//健康教育内容
    @Column(name = "HEN_TIME")
    private Calendar henTime;//收藏时间
    @Column(name = "HEN_TYPE", length = 10)
    private String henType;//教育类型
    @Column(name = "HEN_CREATE_ID",length = 36)
    private String henCreateId;//健康教育模板创建者id
    @Column(name = "HEN_CREATE_TIME")
    private Calendar henCreateTime;//健康教育模板创建时间


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHenUserId() {
        return henUserId;
    }

    public void setHenUserId(String henUserId) {
        this.henUserId = henUserId;
    }

    public String getHenHealthId() {
        return henHealthId;
    }

    public void setHenHealthId(String henHealthId) {
        this.henHealthId = henHealthId;
    }

    public String getHenUserName() {
        return henUserName;
    }

    public void setHenUserName(String henUserName) {
        this.henUserName = henUserName;
    }

    public String getHenHealthTitle() {
        return henHealthTitle;
    }

    public void setHenHealthTitle(String henHealthTitle) {
        this.henHealthTitle = henHealthTitle;
    }

    public String getHenImageUrl() {
        return henImageUrl;
    }

    public void setHenImageUrl(String henImageUrl) {
        this.henImageUrl = henImageUrl;
    }

    public String getHenContent() {
        return henContent;
    }

    public void setHenContent(String henContent) {
        this.henContent = henContent;
    }

    public Calendar getHenTime() {
        return henTime;
    }

    public void setHenTime(Calendar henTime) {
        this.henTime = henTime;
    }

    public String getHenType() {
        return henType;
    }

    public void setHenType(String henType) {
        this.henType = henType;
    }

    public String getHenCreateId() {
        return henCreateId;
    }

    public void setHenCreateId(String henCreateId) {
        this.henCreateId = henCreateId;
    }

    public Calendar getHenCreateTime() {
        return henCreateTime;
    }

    public void setHenCreateTime(Calendar henCreateTime) {
        this.henCreateTime = henCreateTime;
    }
}
