package com.boco.eoms.partner.deviceInspect.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.deviceInspect.dao.PnrInspectMappingDao;
import com.boco.eoms.partner.deviceInspect.dao.PnrInspectMappingJdbc;
import com.boco.eoms.partner.deviceInspect.model.PnrInspectMapping;
import com.boco.eoms.partner.deviceInspect.service.PnrInspectMappingService;

public class PnrInspectMappingServiceImpl extends CommonGenericServiceImpl<PnrInspectMapping> implements PnrInspectMappingService {

	private PnrInspectMappingDao pnrInspectMappingDao ;
	
	private PnrInspectMappingJdbc pnrInspectMappingJdbc;
	
	public List<Map<String, String>> getAllTableName(String whereStr) {
		
		return pnrInspectMappingJdbc.getAllTableName(whereStr);
	}
	
	public List<Map<String, String>> getAllDeviceTypeName(String whereStr) {
		
		return pnrInspectMappingJdbc.getAllDeviceTypeName(whereStr);
	}
	
	public List<Map<String, Object>> getAllDevice(Integer curPage,Integer pageSize,String tableName,
			String filedName, String whereStr) {
		// TODO Auto-generated method stub
		return pnrInspectMappingJdbc.getAllDevice(curPage,pageSize,tableName, filedName, whereStr);
	}
	
	public void updateNetres(String tableName,String inspectId,String inspectName,String idStr) {
		// TODO Auto-generated method stub
		pnrInspectMappingJdbc.updateNetres(tableName,inspectId, inspectName, idStr);
	}
	
	public void cancleRelationInspectDevice(Map<String, String> map,
			String idStr) {
		//1.把Link表中对应的数据删除
		pnrInspectMappingJdbc.cancleRelationInspectDevice(idStr);
		//2.跟新网络资源
		Iterator<String> it = map.keySet().iterator();
		while (it.hasNext()) {
			String id = it.next();
			String tableName = map.get(id);
			pnrInspectMappingJdbc.updateNetres(tableName, "", "", "'"+id+"'");
		}
		
	}

	public List<PnrInspectMapping> getPnrInspectMapping(String deviceType,String netresFieldValue) {
		// TODO Auto-generated method stub、
		List<PnrInspectMapping> list =null;
		StringBuffer sql = new StringBuffer();
		sql.append(" from PnrInspectMapping p where p.deviceType='").append(deviceType).append("'");
		sql.append(" and p.netresFieldValue='").append(netresFieldValue).append("'");
		list=pnrInspectMappingDao.findByHql(sql.toString());
		return list;
	}
	
	public PnrInspectMappingDao getPnrInspectMappingDao() {
		return pnrInspectMappingDao;
	}

	public void setPnrInspectMappingDao(PnrInspectMappingDao pnrInspectMappingDao) {
		this.pnrInspectMappingDao = pnrInspectMappingDao;
		this.setCommonGenericDao(pnrInspectMappingDao);
	}

	public PnrInspectMappingJdbc getPnrInspectMappingJdbc() {
		return pnrInspectMappingJdbc;
	}

	public void setPnrInspectMappingJdbc(PnrInspectMappingJdbc pnrInspectMappingJdbc) {
		this.pnrInspectMappingJdbc = pnrInspectMappingJdbc;
	}

	
}
