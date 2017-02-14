package com.boco.eoms.partner.deviceInspect.service;

import java.util.List;
import java.util.Map;

import com.boco.eoms.deviceManagement.common.service.CommonGenericService;
import com.boco.eoms.partner.deviceInspect.model.PnrInspectMapping;

public interface PnrInspectMappingService extends CommonGenericService<PnrInspectMapping> {

	public List<Map<String, String>> getAllTableName(String whereStr);
	
	public List<Map<String, String>> getAllDeviceTypeName(String whereStr);
	
	/** 获得所有的设备 */
	public List<Map<String, Object>> getAllDevice(Integer curPage,Integer pageSize,String tableName,String filedName, String whereStr);
	
	/** 更新网络资源是否关联到代维资源 */
	public void updateNetres(String tableName,String inspectId,String inspectName,String idStr);
	
	/** 取消代维资源与网络资源的关联 */
	public void cancleRelationInspectDevice(Map<String,String> map,String idStr);
	/**
	 * 通过设备类别名称，网络资源字段值 获得List<PnrInspectMapping>
	 * @param deviceType
	 * @param netresFieldValue
	 * @return List
	 */
	public List<PnrInspectMapping> getPnrInspectMapping(String deviceType,String netresFieldValue);
}
