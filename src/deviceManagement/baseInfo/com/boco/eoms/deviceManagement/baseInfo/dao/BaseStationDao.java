package com.boco.eoms.deviceManagement.baseInfo.dao;

import java.util.List;
import java.util.Map;

import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.deviceManagement.baseInfo.model.BaseStation;
import com.boco.eoms.deviceManagement.common.dao.CommonGenericDao;
import com.boco.eoms.deviceManagement.faultInfo.model.BaseStationFaultRecord;
import com.boco.eoms.partner.baseinfo.model.TawPartnerCar;
/**
 * 基站信息管理
 * @author lee
 *
 */
public interface BaseStationDao extends CommonGenericDao<BaseStation, String> {
}