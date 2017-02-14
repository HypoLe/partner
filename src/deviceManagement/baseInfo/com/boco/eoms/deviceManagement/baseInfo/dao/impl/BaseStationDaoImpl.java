package com.boco.eoms.deviceManagement.baseInfo.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.deviceManagement.baseInfo.dao.BaseStationDao;
import com.boco.eoms.deviceManagement.baseInfo.model.BaseStation;
import com.boco.eoms.deviceManagement.common.dao.CommonGenericDaoImpl;
import com.boco.eoms.deviceManagement.faultInfo.model.BaseStationFaultRecord;
import com.boco.eoms.partner.baseinfo.model.TawPartnerCar;

public class BaseStationDaoImpl extends  CommonGenericDaoImpl<BaseStation, String> implements BaseStationDao{

}
