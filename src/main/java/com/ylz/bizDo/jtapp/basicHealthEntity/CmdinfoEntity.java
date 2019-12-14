package com.ylz.bizDo.jtapp.basicHealthEntity;

/**
 * Created by lintingjie on 2017/7/23.
 */
public class CmdinfoEntity {

    private String ci_id;//编号
    private String ccl_id;//慢性病种类(1高血压，3糖尿病)
    private String ci_type;//疾病类型（见附录疾病类型）

    public String getCi_id() {
        return ci_id;
    }

    public void setCi_id(String ci_id) {
        this.ci_id = ci_id;
    }

    public String getCcl_id() {
        return ccl_id;
    }

    public void setCcl_id(String ccl_id) {
        this.ccl_id = ccl_id;
    }

    public String getCi_type() {
        return ci_type;
    }

    public void setCi_type(String ci_type) {
        this.ci_type = ci_type;
    }
}
