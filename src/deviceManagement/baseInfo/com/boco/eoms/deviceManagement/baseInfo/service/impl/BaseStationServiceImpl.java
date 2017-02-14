package com.boco.eoms.deviceManagement.baseInfo.service.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.deviceManagement.baseInfo.dao.BaseStationDao;
import com.boco.eoms.deviceManagement.baseInfo.model.BaseStation;
import com.boco.eoms.deviceManagement.baseInfo.service.BaseStationService;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.deviceManagement.faultInfo.dao.BaseStationFaultRecordDao;
import com.boco.eoms.deviceManagement.faultInfo.model.BaseStationFaultRecord;
import com.boco.eoms.deviceManagement.faultInfo.service.BaseStationFaultRecordService;

public class BaseStationServiceImpl extends
CommonGenericServiceImpl<BaseStation>  implements BaseStationService{
	public void setBaseStationDao(
			BaseStationDao baseStationDao) {
		this.setCommonGenericDao(baseStationDao);
	}
}
