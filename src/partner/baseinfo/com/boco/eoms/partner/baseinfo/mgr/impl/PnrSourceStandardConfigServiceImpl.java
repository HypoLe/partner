package com.boco.eoms.partner.baseinfo.mgr.impl;

import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.baseinfo.dao.IPnrSourceStandardConfigDao;
import com.boco.eoms.partner.baseinfo.mgr.IPnrSourceStandardConfigService;
import com.boco.eoms.partner.baseinfo.model.PnrSourceStandardConfig;
/**
 * 类说明：代维资源配置模块
 * 创建人： fengguangping
 * 创建时间：2012-12-27 16:18:56
 */
public class PnrSourceStandardConfigServiceImpl extends CommonGenericServiceImpl<PnrSourceStandardConfig>
		implements IPnrSourceStandardConfigService {
	private IPnrSourceStandardConfigDao pnrSourceStandardConfigDao;
	
	public void setPnrSourceStandardConfigDao(IPnrSourceStandardConfigDao pnrSourceStandardConfigDao) {
		this.setCommonGenericDao(pnrSourceStandardConfigDao);
	}
	public IPnrSourceStandardConfigDao getIPnrSourceStandardConfigDao() {
		return this.pnrSourceStandardConfigDao;
	}
}
