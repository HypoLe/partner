package com.boco.eoms.partner.inspect.dao;

import java.util.List;

public interface IInspectPlanGisDaoJdbc {

	/**
	 * 删除以前资源完成情况统计的数据（每天都要重新统计）
	 */
	public void deleteInspectGisCityres();
	
	/**
	 * 每天统计后，保存今天统计的数据
	 */
	public void saveInspectPlanGis();
	
	/**
	 * 删除以前资源完成情况统计的数据
	 */
	public void deleteInspectGisPnrDept();
	
	/**
	 * 保存任务的完成情况
	 */
	public void saveInspectPlanGisPnrDept();
	
	/**
	 * 查询当前地市下任务的完成数
	 * @param city
	 * @return
	 */
	public List findInspectplanGis(String city);
}
