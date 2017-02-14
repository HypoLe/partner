package com.boco.eoms.partner.property.service.impl;

import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.property.dao.IPnrElectricityBillsCountDao;
import com.boco.eoms.partner.property.model.PnrElectricityBillsCount;
import com.boco.eoms.partner.property.service.IPnrElectricityBillsCountService;

/**
 * 类说明：网络资源--空间资源--电费统计
 * 创建人： fengguangping
 * 创建时间：2012-09-28 10:52:23
 */
public class PnrElectricityBillsCountServiceImpl extends CommonGenericServiceImpl<PnrElectricityBillsCount>
		implements IPnrElectricityBillsCountService {
	private IPnrElectricityBillsCountDao pnrElectricityBillsCountDao;
	
	public void setPnrElectricityBillsCountDao(IPnrElectricityBillsCountDao pnrElectricityBillsCountDao) {
		this.setCommonGenericDao(pnrElectricityBillsCountDao);
	}
	public IPnrElectricityBillsCountDao getIPnrElectricityBillsCountDao() {
		return this.pnrElectricityBillsCountDao;
	}
}
