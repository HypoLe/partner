package com.boco.eoms.partner.property.service.impl;

import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.property.dao.IPnrElectricityBillsDao;
import com.boco.eoms.partner.property.dao.IPnrRemindMsgDao;
import com.boco.eoms.partner.property.model.PnrRemindMsg;
import com.boco.eoms.partner.property.service.IPnrElectricityBillsService;
import com.boco.eoms.partner.property.service.IPnrRemindMsgService;
/**
 * 类说明：物业合同管理-提示信息
 * 创建人： fengguangping
 * 创建时间：2012-08-27 16:57:46
 */
public class PnrRemindMsgServiceImpl extends CommonGenericServiceImpl<PnrRemindMsg>
		implements IPnrRemindMsgService {
	private IPnrRemindMsgDao pnrRemindMsgDao;
	public void setPnrRemindMsgDao(IPnrRemindMsgDao pnrRemindMsgDao) {
		this.setCommonGenericDao(pnrRemindMsgDao);
	}

	public IPnrRemindMsgDao getPnrRemindMsgDao() {
		return pnrRemindMsgDao;
	}
	
}
