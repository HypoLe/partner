package com.boco.eoms.partner.taskManager.dao;

import java.util.List;

import com.boco.eoms.deviceManagement.common.dao.CommonGenericDao;
import com.boco.eoms.partner.taskManager.model.CarApprove;

public interface ICarApproveDao extends CommonGenericDao<CarApprove, String> {

	/**
	 * 提交申请
	 * @param carApprove
	 * @param carApproveLink
	 * @param carApproveTask
	 * @param carTask
	 */
	public Object commitCarApprove(Object c);
	
	/**
	 * 更新车辆状态
	 * @param carId
	 * @param staue
	 */
	public void updateCarStatue(String carNum,String staue);
	/**
	 * 
	 *@Description:更新车辆调度状态和车辆的申请单号。
	 *@date May 15, 2013 6:14:31 PM
	 *@author Fengguangping fengguangping@boco.com.cn
	 *@param carNumber：车牌号
	 *@param dispatchStatus：当前调度状态
	 *@param applyid：申请主键id
	 */
	public void updateCarDispatchStatusAndApplyId(String carNumber,String dispatchStatus,String applyid);
	
	/**
	 * 查询车辆申请或审批列表，为方便以后的一个申请单多个任务
	 * @param offset
	 * @param pagesize
	 * @param whereStr
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getAllCarApproveOrApply(int offset,int pagesize,String whereStr,String joinStr);
	
	/**
	 * 由我申请的列表
	 * @param offset
	 * @param pagesize
	 * @param whereStr
	 * @return
	 */
	public List getByMeApply(int offset, int pagesize,String whereStr);
}
