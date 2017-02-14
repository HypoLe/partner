package com.boco.eoms.deviceManagement.externalForceCheck.service;

import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.deviceManagement.externalForceCheck.dao.ExternalForceCheckdao;
import com.boco.eoms.deviceManagement.externalForceCheck.model.ExternalForceCheckModel;

public class ExternalForceCheckServiceImpl extends CommonGenericServiceImpl<ExternalForceCheckModel>
		implements ExternalForceCheckService {
	private ExternalForceCheckdao  externalForceCheckdao;

	public ExternalForceCheckdao getExternalForceCheckdao() {
		return externalForceCheckdao;
	}

	public void setExternalForceCheckdao(ExternalForceCheckdao externalForceCheckdao) {
		this.externalForceCheckdao = externalForceCheckdao;
		this.setCommonGenericDao(externalForceCheckdao);
	}

	
	
}
