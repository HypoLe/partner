package com.boco.eoms.partner.deviceAssess.mgr.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.boco.eoms.base.model.PageModel;
import com.boco.eoms.partner.deviceAssess.dao.DeviceAssessApproveDao;
import com.boco.eoms.partner.deviceAssess.mgr.DeviceAssessApproveMgr;
import com.boco.eoms.partner.deviceAssess.model.DeviceAssessApprove;

/** 
 * Description: 后评估统一审批  
 * Copyright:   Copyright (c)2011
 * Company:     BOCO
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Nov 2, 2011 3:57:59 PM 
 */
public class DeviceAssessApproveMgrImpl implements DeviceAssessApproveMgr {
	private DeviceAssessApproveDao deviceAssessApproveDao;
	
	public PageModel findApproveList(Integer offset,  Integer pageSize,DeviceAssessApprove approve){
		//只查询待审批
		StringBuffer hql = new StringBuffer("from DeviceAssessApprove where state=2 ");
		List params = new ArrayList();
		if(!StringUtils.isEmpty(approve.getAssessType())){
			hql.append("and assessType=? ");
			params.add(approve.getAssessType());
		}
		if(!StringUtils.isEmpty(approve.getName())){
			hql.append("and name like '%"+approve.getName()+"%' ");
		}
		if(!StringUtils.isEmpty(approve.getSheetNum())){
			hql.append("and sheetNum like '%"+approve.getSheetNum()+"%' ");
		}
		if(!"admin".equals(approve.getApproveUser())){
			hql.append("and approveUser=? ");
			params.add(approve.getApproveUser());
		}
		hql.append(" order by commitTime desc");
		return deviceAssessApproveDao.searchPaginated(hql.toString(), params.toArray(), offset, pageSize);
	}
	
	/**
	 * 保存或者修改
	 */
	public void saveOrUpdateApprove(DeviceAssessApprove deviceAssessApprove){
		deviceAssessApproveDao.saveObject(deviceAssessApprove);
	}
	
	
	public void deleteApprove(String approveId){
		DeviceAssessApprove approve = getDeviceAssessApprove(approveId);
		String className = approve.getClassName();
		String assessId = approve.getAssessId();
		deviceAssessApproveDao.removeObject(DeviceAssessApprove.class, approveId);
		String hql = "delete from " + className + " where id='" + assessId + "'";
		deviceAssessApproveDao.bulkUpdate(hql);
	}

	public DeviceAssessApproveDao getDeviceAssessApproveDao() {
		return deviceAssessApproveDao;
	}

	public void setDeviceAssessApproveDao(
			DeviceAssessApproveDao deviceAssessApproveDao) {
		this.deviceAssessApproveDao = deviceAssessApproveDao;
	}
	/**
	 * 通过ID获取
	 * @param approveId
	 */
	public DeviceAssessApprove getDeviceAssessApprove(String approveId){
		return (DeviceAssessApprove)deviceAssessApproveDao.getObject(DeviceAssessApprove.class, approveId);
	}
	
	@SuppressWarnings("unchecked")
	public DeviceAssessApprove getDeviceAssessApprove(String assessType,String assessId){
		StringBuffer hql = new StringBuffer("from DeviceAssessApprove where assessId =?");
		List params = new ArrayList();
		params.add(assessId);
		if(!StringUtils.isEmpty(assessType)){
			hql.append(" and assessType=?");
			params.add(assessType);
		}
		List<DeviceAssessApprove> list = deviceAssessApproveDao.find(hql.toString(),params);
		return list.size()>0 ? list.get(0) : null;
	}
	
	/**
	 * 修改审批表以及具体事件表审批状态
	 * @param approveId
	 */
	public void modifyApprove(String approveId,Integer state){
		DeviceAssessApprove approve = getDeviceAssessApprove(approveId);
		approve.setState(state);
		saveOrUpdateApprove(approve);
		String className = approve.getClassName();
		String assessId = approve.getAssessId();
		String hql = "update " + className + " set total="+state+" where id='" + assessId + "'";
		deviceAssessApproveDao.bulkUpdate(hql);
	}
	
	public Map<String,Integer> findAllDeviceAssessApprove(){
		String hql = "select assessType,count(assessType) from DeviceAssessApprove group by assessType";
		List list = deviceAssessApproveDao.find(hql);
		Map<String,Integer> map = new HashMap<String,Integer>();
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[])list.get(i);
			map.put(obj[0].toString(), Integer.parseInt(obj[1].toString()));
		}
		return map;
	}
	
}
