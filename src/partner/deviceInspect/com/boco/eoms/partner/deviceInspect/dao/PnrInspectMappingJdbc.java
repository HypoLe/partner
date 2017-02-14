package com.boco.eoms.partner.deviceInspect.dao;

import java.util.List;
import java.util.Map;

public interface PnrInspectMappingJdbc {

	public List<Map<String, String>> getAllTableName(String whereStr);
	
	public List<Map<String, String>> getAllDeviceTypeName(String whereStr);
	
	/** 获得所有的设备 */
	public List<Map<String, Object>> getAllDevice(Integer curPage,Integer pageSize,String tableName,String filedName, String whereStr);
	
	/** 更新网络资源是否关联到代维资源 */
	public void updateNetres(String tableName,String inspectId,String inspectName,String idStr);
	
	/** 取消代维资源与网络资源的关联 */
	public void cancleRelationInspectDevice(String idStr);
}
