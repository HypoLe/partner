package com.boco.eoms.partner.deviceAssess.mgr;

import java.util.Map;

import com.boco.eoms.base.model.PageModel;
import com.boco.eoms.partner.deviceAssess.model.DeviceAssessApprove;

/** 
 * Description: 后评估统一审批 
 * Copyright:   Copyright (c)2011
 * Company:     BOCO
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Nov 2, 2011 3:49:04 PM 
 */
public interface DeviceAssessApproveMgr {

	/**
	 * 分页查询
	 * @param offset
	 * @param pageSize
	 * @param params
	 * @return
	 */
	public PageModel findApproveList(Integer offset,  Integer pageSize,DeviceAssessApprove approve);
	
	/**
	 * 保存或者修改
	 */
	public void saveOrUpdateApprove(DeviceAssessApprove deviceAssessApprove);
	
	/**
	 * 作废（删除具体事件表和审批表）
	 * @param approveId
	 */
	public void deleteApprove(String approveId);
	
	/**
	 * 通过ID获取
	 * @param approveId
	 */
	public DeviceAssessApprove getDeviceAssessApprove(String approveId);
	
	/**
	 * 根据事件类型以及事件ID查询
	 * @param assessId
	 * @return
	 */
	public DeviceAssessApprove getDeviceAssessApprove(String assessType,String assessId);
	
	/**
	 * 修改审批表以及具体事件表审批状态
	 * @param approveId
	 * @param state
	 */
	public void modifyApprove(String approveId,Integer state);
	
	/**
	 * 分组显示所有事件以及事件数
	 * @return
	 */
	public Map<String,Integer> findAllDeviceAssessApprove();
}
