package com.ylz.bizDo.jtapp.commonVo;

/**指导
 * Created by zzl on 2017/8/28.
 */
public class AppTcmGuideVo  {
    private String id;
    private String title;
    private String content;
    private String imageUrl;
    private String imageName;
    private String guideType;//指导类型


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

    public String getGuideType() {
        return guideType;
    }

    public void setGuideType(String guideType) {
        this.guideType = guideType;
    }
}
