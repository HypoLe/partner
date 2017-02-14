package com.boco.eoms.deviceManagement.busi.protectline.service.impl;

import com.boco.eoms.deviceManagement.busi.protectline.dao.PromoAgreementDao;
import com.boco.eoms.deviceManagement.busi.protectline.model.PromoAgreement;
import com.boco.eoms.deviceManagement.busi.protectline.service.PromoAgreementService;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;



public class PromoAgreementServiceImpl extends
              CommonGenericServiceImpl<PromoAgreement> implements
              PromoAgreementService {

	public void setPromoAgreementDao(PromoAgreementDao promoAgreementDao) {
		this.setCommonGenericDao(promoAgreementDao);
	}
	
	
	
	
}
