package com.ylz.bizDo.jtapp.commonVo;

import com.ylz.packcommon.common.CommConditionVo;

/**健康教育搜索条件
 * Created by zzl on 2017/6/20.
 */
public class AppHealthSearchQvo extends CommConditionVo {
    private String content;//搜索内容

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
