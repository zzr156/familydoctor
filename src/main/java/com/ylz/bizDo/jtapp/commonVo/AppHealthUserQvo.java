package com.ylz.bizDo.jtapp.commonVo;

import com.ylz.packcommon.common.CommConditionVo;

/**患者添加健康教育
 * Created by zzl on 2017/6/21.
 */
public class AppHealthUserQvo  extends CommConditionVo {
    private String hedId;//健康教育id
    private String title;//健康教育标题
    private String userIds;//患者id
    private String drId;//医生id
    private String drName;//医生姓名
    private String content;
    private String imageUrl;
    private String imageName;
    private String type;

    public String getHedId() {
        return hedId;
    }

    public void setHedId(String hedId) {
        this.hedId = hedId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserIds() {
        return userIds;
    }

    public void setUserIds(String userIds) {
        this.userIds = userIds;
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

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
