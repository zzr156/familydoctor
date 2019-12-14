package com.ylz.bizDo.jtapp.patientEntity;

import com.ylz.bizDo.app.po.AppHealthEnshrine;
import com.ylz.bizDo.app.po.AppLabelManage;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.news.po.NewsTable;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 个人返回健康教育列表
 * Created by zzl on 2017/6/22.
 */
public class AppHealthListEntity  {
    private String id;//健康教育主键
    private String title;//标题
    private String imageUrl;//图片
    private String type;//类型
    private String browse;//浏览次数
    private String transmit;//发布次数
    private String enshrine;//收藏次数
    private String isEnshrine;//是否收藏
    private String time;//健康教育时间
    private String drId;//医生id
    private String drName;//医生名字
    private String drType;//医生类型
    private String drTypeName;//医生类别名称
    private String typeName;//分类名称
    private String content;//内容
    private String hedId;//模板id
    private String toUserName;//接收人姓名
    private String toUserId;//接收人id


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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrowse() {
        return browse;
    }

    public void setBrowse(String browse) {
        if(StringUtils.isNotBlank(this.getHedId())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            NewsTable table = (NewsTable) dao.getServiceDo().find(NewsTable.class,this.getHedId());
            if(table!=null){
                browse = table.getTableBrowse();
            }
        }else{
            browse="0";
        }
        this.browse = browse;
    }

    public String getTransmit() {
        return transmit;
    }

    public void setTransmit(String transmit) {
        if(StringUtils.isNotBlank(this.getHedId())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            NewsTable table = (NewsTable) dao.getServiceDo().find(NewsTable.class,this.getHedId());
            if(table!=null){
                transmit = table.getTableTransmit();
            }
        }else{
            transmit = "0";
        }

        this.transmit = transmit;
    }

    public String getEnshrine() {
        return enshrine;
    }

    public void setEnshrine(String enshrine) {

        if(StringUtils.isNotBlank(this.getHedId())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            NewsTable table = (NewsTable) dao.getServiceDo().find(NewsTable.class,this.getHedId());
            if(table!=null){
                enshrine = table.getTableEnshrine();
            }
        }else{
            enshrine = "0";
        }
        this.enshrine = enshrine;
    }

    public String getIsEnshrine() {
        return isEnshrine;
    }

    public void setIsEnshrine(String isEnshrine) {
        SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
        if(StringUtils.isNotBlank(this.getId())){
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("hedId",this.getId());
            List<AppHealthEnshrine> table = dao.getServiceDo().loadByPk(AppHealthEnshrine.class,"henHealthId",this.getId());
            if(table!=null&&table.size()>0){
                isEnshrine = "1";//已收藏
            }else{
                isEnshrine = "0";//未收藏
            }
        }
        this.isEnshrine = isEnshrine;
    }

    public String getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        if(time != null) {
            this.time = ExtendDate.getYMD_h_m(time);
        }
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getDrName() {
        return drName;
    }

    public void setDrName(String drName) {
        this.drName = drName;
    }

    public String getDrType() {
        return drType;
    }

    public void setDrType(String drType) {
        this.drType = drType;
    }

    public String getDrTypeName() {
        return drTypeName;
    }

    public void setDrTypeName(String drTypeName) throws Exception {
        if(StringUtils.isNotBlank(this.getDrType())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_WORKTYPE, this.getDrType());
            if(value!=null) {
                drTypeName = value.getCodeTitle();
            }
        }

        this.drTypeName = drTypeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) throws Exception {
        if(StringUtils.isNotBlank(this.getType())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            AppLabelManage value = dao.getAppLabelManageDao().findLabelByValue("3",this.getType());
            if(value!=null) {
                typeName = value.getLabelTitle();
            }
        }
        this.typeName = typeName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHedId() {
        return hedId;
    }

    public void setHedId(String hedId) {
        this.hedId = hedId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }
}
