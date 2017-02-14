package com.boco.activiti.partner.process.service.impl;

import java.util.List;
import java.util.Map;

import com.boco.activiti.partner.process.dao.IFaultSataDao;
import com.boco.activiti.partner.process.service.IFaultStatService;


public class FaultStatServiceImpl implements IFaultStatService{
	private IFaultSataDao faultSataDao;
	@Override
	public Map<String, Object> faultStatCityList(String startTime,
			String endTime) {
		Map<String,Object> map=faultSataDao.faultStatCityList(startTime,endTime);
		return map;
	}
	@Override
	public Map<String, Object> faultStatCountyList(String startTime,
			String endTime,String city) {
		Map<String,Object> map=faultSataDao.faultStatCountyList(startTime,endTime,city);
		return map;
	}
	@Override
	public Map<String, Object> timeoutGongdanList(String type,String city,String startTime,String endTime,String cityId) {
		Map<String,Object> map=faultSataDao.timeoutGongdanList(type,city,startTime,endTime,cityId);
		return map;
	}
	public IFaultSataDao getFaultSataDao() {
		return faultSataDao;
	}
	public void setFaultSataDao(IFaultSataDao faultSataDao) {
		this.faultSataDao = faultSataDao;
	}
	
}
