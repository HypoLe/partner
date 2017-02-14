package com.boco.eoms.deviceManagement.GPSManagement.service;

import com.boco.eoms.deviceManagement.GPSManagement.dao.GpsManagementdao;
import com.boco.eoms.deviceManagement.GPSManagement.model.GpsManagementModel;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;

public class GpsManagementServiceImpl extends CommonGenericServiceImpl<GpsManagementModel>
		implements GpsManagementService {
	private GpsManagementdao  gpsManagementdao;

	public GpsManagementdao getGpsManagementdao() {
		return gpsManagementdao;
	}

	public void setGpsManagementdao(GpsManagementdao gpsManagementdao) {
		this.gpsManagementdao = gpsManagementdao;
		this.setCommonGenericDao(gpsManagementdao);
	}
}
