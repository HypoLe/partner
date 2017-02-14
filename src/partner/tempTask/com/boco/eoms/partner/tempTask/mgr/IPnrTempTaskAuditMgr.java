package com.boco.eoms.partner.tempTask.mgr;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.tempTask.model.PnrTempTaskAudit;
import com.boco.eoms.partner.tempTask.model.PnrTempTaskMain;


/**
 * <p>
 * Title:服务临时任务审核
 * </p>
 * <p>
 * Description:服务临时任务审核
 * 
 * @author daizhigang
 * @version 1.0
 * 
 */
 public interface IPnrTempTaskAuditMgr {
 
	/**
	 *
	 * 取服务临时任务审核列表
	 * @return 返回服务临时任务审核列表
	 */
	public List getPnrTempTaskAudits(final String tempTaskId);
	/**
	 *
	 * 取某人的待审核列表
	 * @return 返回取某人的待审核列表
	 */
	public Map getPnrTempTaskUnAudits(final Integer curPage,final Integer pageSize,final String userId,final String deptId);
  
	public PnrTempTaskAudit getPnrTempTaskAuditByState(final String tempTaskId,final String state);

	/**
	 * 根据主键查询服务临时任务审核信息
	 * @param id 主键
	 * @return 返回某id的对象
	 */
	public PnrTempTaskAudit getPnrTempTaskAudit(final String id);
    
	/**
	 * 保存服务临时任务审核信息
	 * @param pnrTempTaskAudit 服务临时任务审核信息
	 */
	public void savePnrTempTaskAudit(PnrTempTaskAudit pnrTempTaskAudit);
    
	/**
	 * 保存服务临时任务审核信息
	 * @param pnrTempTaskAudit 服务临时任务审核信息
	 */
    public void savePnrAgreeMainAudit(PnrTempTaskMain pnrTempTaskMain,String toOrgId,String toOrgType);
    	 
}