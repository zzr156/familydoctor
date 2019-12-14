package com.ylz.bizDo.web.dao;

import com.ylz.bizDo.app.entity.AppSignFormEntity;
import com.ylz.bizDo.app.po.AppHfsSignSsc;
import com.ylz.bizDo.app.po.AppOutpatientNumber;
import com.ylz.bizDo.app.po.AppSignForm;
import com.ylz.bizDo.app.vo.AppSignInfoAllVo;
import com.ylz.bizDo.cd.po.CdUser;
import com.ylz.bizDo.plan.po.AppOpenRemind;
import com.ylz.bizDo.web.vo.WebFamilySignVo;
import com.ylz.bizDo.web.vo.WebSignVo;
import com.ylz.packcommon.common.exception.ActionException;

import java.util.List;
import java.util.Map;

/**
 * 签约接口
 */
public interface WebSignFormDao {
    //福州签约web版接口 对外
    public AppSignForm webSignUp(WebSignVo vo) throws Exception;

    //签约web版接口  对外
    public WebSignVo webSignUpTempPt(WebSignVo vo) throws Exception;

    /**
     * 接收web数据接口
     * @throws Exception
     */
    public String signUpWeb(AppSignForm l,String url) throws Exception;


    /**
     * Dmao
     * web签约保存（新）
     */

    public AppSignFormEntity websignAdd(WebSignVo vo)throws Exception;

    public AppSignForm websignmodify(WebSignVo vo)throws Exception;

    public String findCity(String s,String a) throws Exception;

    /**
     * 查询2017-12-25新版签约数据
     * cxw
     * @returnq
     */
     public List<AppSignInfoAllVo> findAllSign() throws Exception;

    /**
     * 通过身份证查询参保人员 （莆田）
     * @param ptIdNo
     * @return
     */
    public AppHfsSignSsc findHfsSignSscByPtIdNo(String ptIdNo) throws Exception;

    /**
     * 初始化编号
     */
    //public void testNo();

    /**
     * Dmao
     * web签约保存（家庭版）
     */

    public AppSignForm webFamilysignAdd(WebFamilySignVo vo)throws  Exception;

    /**
     *医保本年度门诊 更新数据
     */
    public AppOutpatientNumber OutpatientNumberUpdate(AppOutpatientNumber vo)throws Exception;

    // 拒签
    public AppSignForm websignrefuse(AppSignForm vo)throws Exception;

    // 续签
    public AppSignForm websignRenew(WebSignVo vo)throws Exception;

    /**
     * 通过excel表格导入续签（批量续签)
     * WangCheng
     * @param map
     * @param user
     * @param orgId
     * @return
     * @throws Exception
     */
    public String addExcelRenew(Map<Integer,String> map, CdUser user,String orgId) throws Exception;
}
