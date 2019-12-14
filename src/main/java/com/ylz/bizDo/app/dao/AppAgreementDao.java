package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.po.AppAgreement;
import com.ylz.bizDo.app.vo.AppAgreementQvo;

import java.util.List;

/**
 * 协议表
 */
public interface AppAgreementDao {
    public List<AppAgreement> findListQvo(AppAgreementQvo qvo) throws Exception;

    public AppAgreement findByCityId(String patientCity) throws Exception;

    public AppAgreement findByHospId(String hospId, String qyType) throws Exception;

    public List<AppAgreement> findListHosp(AppAgreementQvo qvo) throws Exception;
    //查找已启用的协议
    public List<AppAgreement> findEnabled(AppAgreement qvo) throws Exception;

    public AppAgreement findState(String hospId)throws Exception;

    /**
     * 根据身份证返回协议内容
     * @param idno
     * @return
     */
    public String findContentByIdno(String idno) throws Exception;
}
