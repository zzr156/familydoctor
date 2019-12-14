package com.ylz.packcommon.common.comEnum;

/**健康档案
 * Created by zzl on 2017/7/12.
 */
public enum CommonHealth {
    /**
     * 门诊基本诊疗信息
     */
    MZJBZLXX("0101"),
    /**
     * 门诊费用明细
     */
    MZFYMX("0102"),
    /**
     * 门诊检查报告
     */
    MZJCBG("0131"),
    /**
    * 门诊检验报告
    */
    MZJYBG("0121"),
    /**
    * 住院基本诊疗信息
    */
    ZYJBZLXX("0201"),
    /**
     * 住院用药记录
     */
    ZYYYJL("0241"),
    /**
     * 住院检查报告
     */
    ZYJCBG("0231"),
    /**
     * 住院检验报告
     */
    ZYJYBG("0221"),
    /**
     * 长期医嘱
     */
    CQYZ("0214"),
    /**
     * 临时医嘱
     */
    LSYZ("0215"),
    /**
     * 病案首页
     */
    BASY("0211"),
    /**
     * 住院费用明细
     */
    ZYFYMX("0202"),
    /**
     * 门诊用药记录
     */
    MZYYJL("0141"),
    /**
     * 体检报告
     */
    TJBG("0301"),

    /**
     * 入院记录
     */
    RYJL("0212"),

    /**
     * 出院小结
     */
    CYXJ("0213");
    private String value;
    private CommonHealth(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
