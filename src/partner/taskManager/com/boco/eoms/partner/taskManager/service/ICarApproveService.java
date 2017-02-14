package com.boco.eoms.partner.taskManager.service;

import java.util.List;

import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.deviceManagement.common.service.CommonGenericService;
import com.boco.eoms.partner.taskManager.model.CarApprove;
import com.boco.eoms.partner.taskManager.model.CarApproveLink;
import com.boco.eoms.partner.taskManager.model.CarApproveTask;
import com.boco.eoms.partner.taskManager.model.CarTask;

public interface ICarApproveService extends CommonGenericService<CarApprove> {

	/**
	 * 获得登录者的部门
	 * @param sessionForm
	 * @return
	 */
	public String getDeptId(TawSystemSessionForm sessionForm);
	
	/**
	 * 提交申请
	 * @param carApprove
	 * @param carApproveLink
	 * @param carApproveTask
	 * @param carTask
	 */
	public void commitCarApprove(CarApprove carApprove,CarApproveLink carApproveLink,CarApproveTask carApproveTask,CarTask carTask);
	
	/**
	 * 更新车辆状态
	 * @param carId
	 * @param staue
	 */
	public void updateCarStatue(String carNum,String staue);
	
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
	 * 车辆审批
	 * @param list
	 * @param approveStatus
	 * @param carNum
	 */
	public void saveAllObject(List<Object> list,String approveStatus,String carNum);
	
	/**
	 * 车辆归还
	 * @param list
	 * @param carNum
	 */
	public void backApplyCar(List<Object> list,String carNum);
	
	/**
	 * 终止车辆的使用
	 * @param list
	 * @param carNum
	 */
	public void endApplyCar(List<Object> list,String carNum);
	
	/**
	 * 由我申请的列表
	 * @param offset
	 * @param pagesize
	 * @param whereStr
	 * @return
	 */
	public List getByMeApply(int offset, int pagesize,String whereStr);
}
