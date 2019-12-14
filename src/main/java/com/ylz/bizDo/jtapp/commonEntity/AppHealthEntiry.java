package com.ylz.bizDo.jtapp.commonEntity;

import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;

import java.math.BigInteger;

/**
 * Created by zzl on 2017/6/19.
 */
public class AppHealthEntiry {
    private String id;//主键id
    private String title;//标题
    private String time;//时间
    private String type;//类型
    private String content;//内容
    private String imageUrl;//图片
    private String transmit;//发布次数
    private String enshrine;//收藏次数
    private String browse;//浏览次数
    private String drName;//医生姓名
    private String typeName;//类型名称
    private String drId;//医生id
    private String drTypeName;//医生类别名称
    private String enshrineState;//收藏状态 0未收藏 1已收藏
    private String newsId;//模板id


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTransmit() {
        return transmit;
    }

    public void setTransmit(String transmit) {
        this.transmit = transmit;
    }

    public String getEnshrine() {
        return enshrine;
    }

    public void setEnshrine(String enshrine) {
        this.enshrine = enshrine;
    }

    public String getBrowse() {
        return browse;
    }

    public void setBrowse(String browse) {
        this.browse = browse;
    }

    public String getDrName() {
        return drName;
    }

    public void setDrName(String drName) {
        this.drName = drName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) throws Exception {
        if(StringUtils.isNotBlank(this.getType())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_RESIDENTMANGE, this.getType());
            if(value!=null){
                typeName = value.getCodeTitle();
            }
        }
        this.typeName = typeName;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getDrTypeName() {
        return drTypeName;
    }

    public void setDrTypeName(String drTypeName) throws Exception {
        if(StringUtils.isNotBlank(this.getDrId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            AppDrUser drUser = (AppDrUser) dao.getServiceDo().find(AppDrUser.class,this.getDrId());
            if(drUser!=null){
                drTypeName = drUser.getDrTypeName();
            }
        }
        this.drTypeName = drTypeName;
    }

    public String getEnshrineState() {
        return enshrineState;
    }

    public void setEnshrineState(BigInteger enshrineState) {
        if(enshrineState != null){
            this.enshrineState = enshrineState.toString();
        }else {
            this.enshrineState = "0";
        }
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }
}
