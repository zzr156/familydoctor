package com.ylz.bizDo.jtapp.drEntity;

import com.ylz.bizDo.app.po.AppLabelManage;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**指导模板
 * Created by zzl on 2017/6/20.
 */
public class AppGuideModelEntity {
    private String id;//主键id
    private String title;//标题
    private String content;//内容
    private String time;//时间
    private String guideType;//指导模板类型
    private String diseaseType;//疾病类型
    private String meddleType;//干预类型
    private String imageUrl;//图片
    private String guideTypeName;//指导模板类型名称
    private String diseaseTypeName;//疾病类型名称
    private String meddleTypeName;//干预类型名称

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getGuideType() {
        return guideType;
    }

    public void setGuideType(String guideType) {
        this.guideType = guideType;
    }

    public String getDiseaseType() {
        return diseaseType;
    }

    public void setDiseaseType(String diseaseType) {
        this.diseaseType = diseaseType;
    }

    public String getMeddleType() {
        return meddleType;
    }

    public void setMeddleType(String meddleType) {
        this.meddleType = meddleType;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getGuideTypeName() {
        return guideTypeName;
    }

    public void setGuideTypeName(String guideTypeName) {
        this.guideTypeName = guideTypeName;
    }

    public String getDiseaseTypeName() {
        return diseaseTypeName;
    }

    public void setDiseaseTypeName(String diseaseTypeName) {
        if(StringUtils.isNotBlank(this.getDiseaseType())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            List<AppLabelManage> ls=dao.getServiceDo().loadByPk(AppLabelManage.class,"labelType","2");
            for(AppLabelManage v:ls){
                if(v.getLabelValue().equals(this.getDiseaseType())){
                    diseaseTypeName = v.getLabelTitle();
                }
            }
        }
        this.diseaseTypeName = diseaseTypeName;
    }

    public String getMeddleTypeName() {
        return meddleTypeName;
    }

    public void setMeddleTypeName(String meddleTypeName) {
        this.meddleTypeName = meddleTypeName;
    }
}
