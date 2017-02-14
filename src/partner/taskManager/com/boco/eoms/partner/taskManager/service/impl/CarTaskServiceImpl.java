package com.boco.eoms.partner.taskManager.service.impl;

import java.util.List;

import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.taskManager.dao.ICarTaskDao;
import com.boco.eoms.partner.taskManager.model.CarTask;
import com.boco.eoms.partner.taskManager.service.ICarTaskService;

public class CarTaskServiceImpl extends CommonGenericServiceImpl<CarTask> implements ICarTaskService {

	private ICarTaskDao carTaskDao;

	public ICarTaskDao getCarTaskDao() {
		return carTaskDao;
	}

	public void setCarTaskDao(ICarTaskDao carTaskDao) {
		this.setCommonGenericDao(carTaskDao);
		this.carTaskDao = carTaskDao;
	}
	/**
	 * 
	 *@Description:根据车牌号和申请单主键获取车辆的当前任务;
	 *@date May 15, 2013 5:39:15 PM
	 *@author Fengguangping fengguangping@boco.com.cn
	 *@param carNumber:车牌号
	 *@param applyId:申请单主键
	 *@return
	 *@throws Exception
	 */
	public CarTask getCarCurrentTask(String carNumber,String applyId) throws Exception{
		CarTask carTask=null;
		String hql="from CarTask where carApproveId='"+applyId+"' and carNum='"+carNumber+"'";
		List list=carTaskDao.findByHql(hql);
		if (list!=null&&list.size()==1) {
			carTask =(CarTask)list.get(0);
		}
		return carTask;
	}
	
}
