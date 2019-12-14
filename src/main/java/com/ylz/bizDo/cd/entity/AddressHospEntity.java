package com.ylz.bizDo.cd.entity;

/**
 * Created by asus on 2017/07/20.
 */
public class AddressHospEntity {
    private String id;
    private String name;
    private String state;
    private String level;
    private String source;//1.计生2.基卫

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
