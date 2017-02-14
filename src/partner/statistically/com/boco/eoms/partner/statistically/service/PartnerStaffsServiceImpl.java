package com.boco.eoms.partner.statistically.service;

import com.boco.eoms.partner.baseinfo.model.PartnerUser;
import com.boco.eoms.partner.statistically.dao.PartnerStaffsDao;
import com.boco.eoms.partner.statistically.utils.BaseServiceImpl;

public class PartnerStaffsServiceImpl extends
BaseServiceImpl<PartnerUser> implements
PartnerStaffsService {
	public void setPartnerStaffsDao(PartnerStaffsDao partnerStaffsDao) {
		setBaseDao(partnerStaffsDao);
	}
}
