package com.ylz.bizDo.jtapp.drVo;

import com.ylz.packcommon.common.CommConditionVo;

/**医生查询健康教育列表条件
 * Created by zzl on 2017/6/22.
 */
public class AppDrHealthListQvo extends CommConditionVo {
    private String id;//根据医生id查
    private String content;//根据标题或内容所含有查询
    private String isEnshrine;//是否收藏

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIsEnshrine() {
        return isEnshrine;
    }

    public void setIsEnshrine(String isEnshrine) {
        this.isEnshrine = isEnshrine;
    }
}
