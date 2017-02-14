package com.boco.eoms.partner.eva.dao;

import java.util.List;
import java.util.Map;

import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.partner.eva.model.PnrEvaTaskAudit;

public interface IPnrEvaTaskAuditDao extends Dao{
	
	//根据id查找审核任务的某条记录
	public PnrEvaTaskAudit getPnrEvaTaskAudit(String id);
	
	//根据id查找审核任务的某条记录
	public List getPnrEvaTaskAuditByTaskId(String taskId,String partner);
	
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
	 * @return	PnrEvaTaskAudit
	 */
	public List getPnrEvaTaskAudit(String taskId, String auditTime,
			String auditCycle, String partner);
	
	//保存要审核的任务
	public void savePnrEvaTaskAudit(PnrEvaTaskAudit taskAudit);
	
	//根据审核条件显示审核任务列表
	public Map getPnrEvaTaskAuditByOrgType(final Integer curPage,
			final Integer pageSize, final String whereStr);
}
