package com.boco.eoms.deviceManagement.externalForceCheck.service;

import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.deviceManagement.externalForceCheck.dao.ExternalForceCheckSublistdao;
import com.boco.eoms.deviceManagement.externalForceCheck.model.ExternalForceCheckSublist;

public class ExternalForceCheckSublistServiceImpl extends CommonGenericServiceImpl<ExternalForceCheckSublist>
		implements ExternalForceCheckSublistService {
	private ExternalForceCheckSublistdao  externalForceCheckSublistdao;

	public ExternalForceCheckSublistdao getExternalForceCheckSublistdao() {
		return externalForceCheckSublistdao;
	}

	public void setExternalForceCheckSublistdao(
			ExternalForceCheckSublistdao externalForceCheckSublistdao) {
		this.externalForceCheckSublistdao = externalForceCheckSublistdao;
		this.setCommonGenericDao(externalForceCheckSublistdao);
	}
	
	
	
}
