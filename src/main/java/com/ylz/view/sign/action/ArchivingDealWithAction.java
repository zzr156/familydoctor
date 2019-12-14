package com.ylz.view.sign.action;

import com.ylz.bizDo.cd.po.CdUser;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.util.ExcelReader;
import com.ylz.packcommon.common.util.JsonUtil;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by zzl on 2019/1/4.
 */
@SuppressWarnings("all")
@Action(
        value="archivingDealWith",
        results={
                @Result(name="edit", location="/intercept/sign/archivingDealWith/archivingDealWith_list.jsp"),
                @Result(name = "json", type = "json",params={"root","jsons","contentType", "application/json"}),
                @Result(name = "jList", type = "json",params={"root","jList","contentType", "application/json"}),
                @Result(name = "ajson", type = "json", params = {"root", "ajson", "contentType", "application/json"}),
                @Result(name = "jsonLayui", type = "json",params={"root","jsonLayui","excludeNullProperties","true","contentType", "application/json"}),
                @Result(name = "jsonUpload", type = "json",params={"root","jsons","contentType", "text/html"})
        }
)
public class ArchivingDealWithAction extends CommonAction {
    private File upExcel; //上传的文件
    private String upExcelFileName; //文件名称
    private String upExcelContentType; //文件类型


    /**
     * 准备新增或者修改
     * @return
     */
    public String forAddOrEdit(){
        return "edit";
    }

    /**
     * 导入建档立卡数据excel
     * @return
     */
    public String importExcel(){
        try{
            String num = this.getRequest().getParameter("num");
            String areaCode = this.getRequest().getParameter("areaCode");
            ExcelReader excelReader = new ExcelReader();
            InputStream is2 = new FileInputStream(upExcel);
            int numm = 1;
            if("2".equals(num)){
                numm = 3;
            }
            Map<Integer, String> map = excelReader.readExcelContent(is2,numm);//读取Excel数据内容
            String result = "";
            if(map.size() >0) {
                if("1".equals(num)){//新增条数不能超过200条
                    if(map.size()>3000){
                        this.jsons.setMsg("导入的数据不能超过3000条");
                    }else{//添加导入
                        result = this.getSysDao().getAppArchivingCardPeopeDao().addImportExcel(map,areaCode);
                        this.jsons.setMsg(result);
                    }
                }else if ("2".equals(num)){//修改条数
                    if(map.size()>3000){
                        this.jsons.setMsg("导入的数据不能超过3000条");
                    }else{//修改导入
                        result = this.getSysDao().getAppArchivingCardPeopeDao().modifyImportExcel(map,areaCode);
                        this.jsons.setMsg(result);
                    }
                }else if("3".equals(num)){//删除条数不能超过500条
                    if(map.size()>3000){
                        this.jsons.setMsg("导入的数据不能超过3000条");
                    }else{//删除导入
                        result = this.getSysDao().getAppArchivingCardPeopeDao().delImportExcel(map,areaCode);
                        this.jsons.setMsg(result);
                    }
                }

            }else{
                this.jsons.setMsg("exceel无数据!");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.jsons.setMsg(e.getMessage());
        }
        return "jsonUpload";
    }

    public File getUpExcel() {
        return upExcel;
    }

    public void setUpExcel(File upExcel) {
        this.upExcel = upExcel;
    }

    public String getUpExcelFileName() {
        return upExcelFileName;
    }

    public void setUpExcelFileName(String upExcelFileName) {
        this.upExcelFileName = upExcelFileName;
    }

    public String getUpExcelContentType() {
        return upExcelContentType;
    }

    public void setUpExcelContentType(String upExcelContentType) {
        this.upExcelContentType = upExcelContentType;
    }
}
