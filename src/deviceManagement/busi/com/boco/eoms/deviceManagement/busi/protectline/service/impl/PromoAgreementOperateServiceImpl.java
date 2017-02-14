package com.boco.eoms.deviceManagement.busi.protectline.service.impl;



import com.boco.eoms.deviceManagement.busi.protectline.dao.PromoAgreementOperateDao;
import com.boco.eoms.deviceManagement.busi.protectline.model.PromoAgreementOperate;
import com.boco.eoms.deviceManagement.busi.protectline.service.PromoAgreementOperateService;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;


 

public class PromoAgreementOperateServiceImpl extends
CommonGenericServiceImpl<PromoAgreementOperate> implements
PromoAgreementOperateService {

public void setPromoAgreementOperateDao(PromoAgreementOperateDao promoAgreementOperateDao) {
this.setCommonGenericDao(promoAgreementOperateDao);
}




}




