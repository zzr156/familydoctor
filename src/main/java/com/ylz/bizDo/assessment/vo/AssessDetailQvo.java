package com.ylz.bizDo.assessment.vo;

/**
 * Created by zms on 2018/6/15.
 */
public class AssessDetailQvo {

    private String id;
    private Integer index;
    private String indexes;// 逗号分隔的索引串
    private String month;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getIndexes() {
        return indexes;
    }

    public void setIndexes(String indexes) {
        this.indexes = indexes;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
