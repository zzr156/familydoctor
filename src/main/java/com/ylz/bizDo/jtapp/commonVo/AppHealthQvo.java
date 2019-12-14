package com.ylz.bizDo.jtapp.commonVo;

import com.ylz.packcommon.common.CommConditionVo;

/**健康教育
 * Created by zzl on 2017/6/19.
 */
public class AppHealthQvo extends CommConditionVo {
    private String id;//id
    private String userId;//用户id
    private String type;//1个人 2医生
    private String content;//搜索条件（医生、标题、内容）
    private String hedId;//个人健康教育id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getHedId() {
        return hedId;
    }

    public void setHedId(String hedId) {
        this.hedId = hedId;
    }
}
