package com.ylz.bizDo.smjq.smVo;

/**
 * Created by zzl on 2019/4/19.
 */
public class AppNcdHEQvo {
    private String id;//主键
    private String tableTitle;//标题
	private String tableHealthType;//健康教育类型(1、普通人群 2、儿童(0-6岁) 3、孕产妇 4、老年人 5、高血压 6、糖尿病 7、严重精神障碍 8、结核病 9、残疾人 99、未知 100、家签政策)
	private String tableContent;//内容
	private String tableObject;//具体接收对象(1、普通人群 2、儿童(0-6岁) 3、孕产妇 4、老年人 5、高血压 6、糖尿病 7、严重精神障碍 8、结核病 9、残疾人)
	private String tablePeopleList;//具体推送人员身份证多人用分号隔开
    private String tableCjrid;//创建人id
    private String tableCjrxm;//创建人姓名
    private String tableHospId;//机构id
    private String cityCode;//地市编码（福州3501 莆田3503 三明3504 泉州3505 漳州3506 南平3507）
    private String drId;//推送医生


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTableTitle() {
        return tableTitle;
    }

    public void setTableTitle(String tableTitle) {
        this.tableTitle = tableTitle;
    }

    public String getTableHealthType() {
        return tableHealthType;
    }

    public void setTableHealthType(String tableHealthType) {
        this.tableHealthType = tableHealthType;
    }

    public String getTableContent() {
        return tableContent;
    }

    public void setTableContent(String tableContent) {
        this.tableContent = tableContent;
    }

    public String getTableObject() {
        return tableObject;
    }

    public void setTableObject(String tableObject) {
        this.tableObject = tableObject;
    }

    public String getTablePeopleList() {
        return tablePeopleList;
    }

    public void setTablePeopleList(String tablePeopleList) {
        this.tablePeopleList = tablePeopleList;
    }

    public String getTableCjrid() {
        return tableCjrid;
    }

    public void setTableCjrid(String tableCjrid) {
        this.tableCjrid = tableCjrid;
    }

    public String getTableCjrxm() {
        return tableCjrxm;
    }

    public void setTableCjrxm(String tableCjrxm) {
        this.tableCjrxm = tableCjrxm;
    }

    public String getTableHospId() {
        return tableHospId;
    }

    public void setTableHospId(String tableHospId) {
        this.tableHospId = tableHospId;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }
}
