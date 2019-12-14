package com.ylz.packcommon.common.comEnum;

/**查询履约人员钻取类别
 * Created by zzl on 2017/11/24.
 */
public enum PerType {
    /**
     * 健康教育履约
     */
    JKJY("1"),
    /**
     * 健康指导履约
     */
    JKZD("2"),
    /**
     * 新生儿家庭访视
     */
    XSEFS("3"),
    /**
     * 0-6岁儿童健康检查
     */
    ETJKTJ("4"),
    /**
     * 中医药健康指导
     */
    ETZYYJKZD("5"),
    /**
     * 孕期保健服务
     */
    YQBJFW("6"),
    /**
     * 产后视访
     */
    CHFS("7"),
    /**
     * 产后42天健康检查记录
     */
    CHJKJC("8"),
    /**
     * 健康体检
     */
    JKTJ("9"),
    /**
     * 用药指导
     */
    YYZD("10"),
    /**
     * 高血压定期随访
     */
    GXYSF("11"),
    /**
     * 糖尿病定期随访
     */
    TNBSF("12"),
    /**
     * 重性精神疾病定期随访
     */
    ZXJSBSF("13"),
    /**
     * 肺结核病定期随访
     */
    JHBSF("14"),
    /**
     * 中医体质辨识
     */
    ZYTZBS("15"),
    /**
     * 公共服务
     */
    GGFW("16");
    private String value;
    private PerType(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
