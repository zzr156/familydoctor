package com.ylz.bizDo.cd.vo;

/**
 * 公用接收类
 */
public class SysCommQvo {
    private String id;
    private String id2;

    private String menuck;//菜单集合
    private String sonck;//按钮集合

    public String getMenuck() {
        return menuck;
    }

    public void setMenuck(String menuck) {
        this.menuck = menuck;
    }

    public String getSonck() {
        return sonck;
    }

    public void setSonck(String sonck) {
        this.sonck = sonck;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId2() {
        return id2;
    }

    public void setId2(String id2) {
        this.id2 = id2;
    }
}
