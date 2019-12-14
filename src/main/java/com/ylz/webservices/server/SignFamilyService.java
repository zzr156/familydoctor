package com.ylz.webservices.server;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * 对外签约接口
 */
@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface SignFamilyService {

    /**
     * 各地市web签约数据上传接口
     * @return
     */
    public String upWebSign(String json);

    /**
     * 添加或修改医院机构
     * @return
     */
    public String webSaveHosp(String json);

    /**
     * 添加或修改医生信息
     * @return
     */
    public String webSaveDr(String json);

    /**
     * 添加或修改团队信息
     * @return
     */
    public String webSaveTeam(String json);

    /**
     * 添加或删除团队成员
     * @return
     */
    public String webSaveTeamM(String json);

//    /**
//     * 删除当前签约单
//     * @return
//     */
//    public String webDeleteSign(String json);
//
//    /**
//     * 修改签约单缴费状态
//     * @return
//     */
//    public String webModifySign(String json);

    /**
     * 签约单团队变更
     * @return
     */
    public String webChangeSign(String json);

    /**
     * 添加服务套餐信息
     * @return
     */
    public String addServeMeal(String json);

    /**
     * 履约数据上传
     * @return
     */
    public String webAppPerformanceData(String json);
}
