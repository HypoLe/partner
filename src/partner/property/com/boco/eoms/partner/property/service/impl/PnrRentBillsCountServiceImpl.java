package com.boco.eoms.partner.property.service.impl;

import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.property.dao.IPnrRentBillsCountDao;
import com.boco.eoms.partner.property.model.PnrRentBillsCount;
import com.boco.eoms.partner.property.service.IPnrRentBillsCountService;

/**
 * 类说明：网络资源--空间资源--租赁费用统计
 * 创建人： fengguangping
 * 创建时间：2012-09-28 10:52:24
 */
public class PnrRentBillsCountServiceImpl extends CommonGenericServiceImpl<PnrRentBillsCount>
		implements IPnrRentBillsCountService {
	private IPnrRentBillsCountDao pnrRentBillsCountDao;
	
	public void setPnrRentBillsCountDao(IPnrRentBillsCountDao pnrRentBillsCountDao) {
		this.setCommonGenericDao(pnrRentBillsCountDao);
	}
	public IPnrRentBillsCountDao getIPnrRentBillsCountDao() {
		return this.pnrRentBillsCountDao;
	}
}
