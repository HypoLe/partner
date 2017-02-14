package com.boco.eoms.eva.dao;

import java.util.List;

import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.eva.model.EvaKpiInstanceForAudit;

/**
 * 
 * <p>
 * Title:考核实例Dao接口
 * </p>
 * <p>
 * Description:考核实例Dao接口
 * </p>
 * <p>
 * Date:2008-12-10 上午10:30:52
 * </p>
 * 
 * @author 李秋野
 * @version 3.5.1
 * 
 */
public interface IEvaKpiInstanceForAuditDao extends Dao {

	/**
	 * 根据主键id查询指标考核实例
	 * 
	 * @param id
	 *            主键
	 * @return
	 */
	public EvaKpiInstanceForAudit getKpiInstanceForAudit(String id);

	/**
	 * 保存指标考核实例
	 * 
	 * @param EvaKpiInstanceForAudit
	 *            指标考核实例
	 */
	public void saveKpiInstanceForAudit(EvaKpiInstanceForAudit kpiInstance);

	/**
	 * 删除指标考核实例
	 * 
	 * @param EvaKpiInstanceForAudit
	 *            指标考核实例
	 */
	public void removeKpiInstanceForAudit(EvaKpiInstanceForAudit kpiInstance);

	/**
	 * 查询某任务某时间所属周期的指标考核实例
	 * 王思轩 2009-1-20
	 * @param  任务id，时间类型，时间，合作伙伴ID
	 * @return 指标考核实例
	 */
	public EvaKpiInstanceForAudit getKpiInstanceForAuditByTimeAndPartner(final String taskDetailId,
			final String timeType,final String time, final String partnerId);
	/**
	 * 查询某任务某scope指标考核实例
	 */
	public List getKpiInstanceForAuditByScope(final String taskDetailId, final String scope) ;
	/**
	 * 查询某任务某时间所属周期的所有指标考核实例
	 * 王思轩 2009-1-22
	 * @param  任务id，合作伙伴ID，时间类型，时间范围，
	 * @return 指标考核实例
	 */
	public List getKpiInstanceForAuditListByTimeAndPartner(final String taskDetailId, 
			final String partnerId, final String timeType,
			final String startTime, final String endTime, final String isPublish);
	/**
	 * 查询某任务某时间所属周期的所有指标考核实例
	 * 
	 * @param orgId
	 *            任务Id
	 * @param date
	 *            日期
	 * @return
	 */
	public List listKpiInstanceForAuditOfTemplate(final String orgId,
			final String startDate, final String endDate);

	/**
	 * 查询某任务某时间所属周期的某指标考核实例
	 * 
	 * @param orgId
	 *            任务Id
	 * @param date
	 *            日期
	 * @return
	 */
	public EvaKpiInstanceForAudit getKpiInstanceForAuditOfTemplate(final String orgId,
			final String kpiId, final String startDate, final String endDate);
	
	/**
	 * 详细任务ID转换为模板名称
	 * 王思轩 09-1-21
	 * @return
	 */
	public String id2Name(String id);
	
	/**
	 * 查询某任务所有指标考核实例
	 * @param  任务id
	 * @return 指标考核实例
	 */
	public List getKpiInstanceForAuditListByTask(final String taskId);
}
