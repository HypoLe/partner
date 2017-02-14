package com.boco.activiti.partner.process.service.impl;


import com.boco.activiti.partner.process.dao.INetResInspectDraftDao;
import com.boco.activiti.partner.process.model.NetResInspectDraft;
import com.boco.activiti.partner.process.service.INetResInspectDraftService;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;

/**
 
 */
public class NetResInspectDraftServiceImpl extends CommonGenericServiceImpl<NetResInspectDraft> implements INetResInspectDraftService {

    private INetResInspectDraftDao netResInspectDraftDao;

	public INetResInspectDraftDao getNetResInspectDraftDao() {
		return netResInspectDraftDao;
	}

	public void setNetResInspectDraftDao(
			INetResInspectDraftDao netResInspectDraftDao) {
		this.netResInspectDraftDao = netResInspectDraftDao;
		this.setCommonGenericDao(netResInspectDraftDao);
	}
}
