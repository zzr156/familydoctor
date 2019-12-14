package com.ylz.bizDo.jtapp.basicHealthEntity;

/**
 * Created by lintingjie on 2017/7/21.
 */
public class CommitteeEntity {

    private String id;//居委会编号
    private String ref_ci_id;//相关社区
    private String jname;//名称
    private String jdesc;
    private String plxh00;//村（居）委会排行

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRef_ci_id() {
        return ref_ci_id;
    }

    public void setRef_ci_id(String ref_ci_id) {
        this.ref_ci_id = ref_ci_id;
    }

    public String getJname() {
        return jname;
    }

    public void setJname(String jname) {
        this.jname = jname;
    }

    public String getJdesc() {
        return jdesc;
    }

    public void setJdesc(String jdesc) {
        this.jdesc = jdesc;
    }

    public String getPlxh00() {
        return plxh00;
    }

    public void setPlxh00(String plxh00) {
        this.plxh00 = plxh00;
    }
}
