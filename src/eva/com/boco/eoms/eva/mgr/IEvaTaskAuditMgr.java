package com.boco.eoms.eva.mgr;

import java.util.List;
import java.util.Map;

import com.boco.eoms.eva.model.EvaTaskAudit;

/**
 * 
 * <p>
 * Title:考核任务明细业务方法类
 * </p>
 * <p>
 * Description:考核任务明细业务方法类
 * </p>
 * <p>
 * Date:2009-11-3 上午10:53:41
 * </p>
 * 
 * @author 贾智会
 * @version 1.0
 * 
 */
public interface IEvaTaskAuditMgr {
	
	

	
	/**
	 * 根据id查找审核任务的某条记录
	 * 
	 * @param Id
	 *            任务考核Id
	 * @return EvaTaskAudit
	 */
	public EvaTaskAudit getEvaTaskAudit(String id);
	
	/**
	 * 根据任务Id查找审核任务的某条记录
	 * 
	 * @param taskId
	 * 
	 * @return EvaTaskAudit
	 */
	public List getEvaTaskAuditByTaskId(String taskId,String partner);

	/**
	 * 根据模任务编号，审核时间，审核周期,合作伙伴查出任务审核信息
	 * 
	 * @param   taskId
	 *            任务考核Id
	 * @param	auditTime
	 * 				考核时间
	 * @param	auditCycle
	 * 				考核周期
	 * @param	partner
	 * 				合作伙伴
	 * @return	EvaTaskAudit
	 */
	public List getEvaTaskAudit(String taskId,String auditTime,
			String auditCycle, String partner);
	
	/**
	 * 保存要审核的任务
	 * 
	 * @param	taskAudit
	 * 
	 * @return
	 */
	public void saveEvaTaskAudit(EvaTaskAudit taskAudit);
	
	
	/**
	 * 根据审核条件显示设呢和任务列表
	 * 
	 * @param	Integer
	 * 
	 * @param	curPage
	 * 
	 * @param	whereStr
	 * 			
	 * 			查询条件
	 * @return	map
	 */
	public Map getEvaTaskAuditByOrgType(final Integer curPage,
			final Integer pageSize, final String whereStr);
}
	
