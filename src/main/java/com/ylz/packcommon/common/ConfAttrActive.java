package com.ylz.packcommon.common;

/**
 * 配置文件的变量id
 *
 * @author hzk
 */
public enum ConfAttrActive
{


    news_file("news_file","新闻附件路径");
    String id, desc;
    ConfAttrActive(String _s1, String _s2)
    {
        this.id = _s1;
        this.desc = _s2;
    }

    public String getId()
    {
        return id;
    }

    public String getDesc()
    {
        return desc;
    }
}
