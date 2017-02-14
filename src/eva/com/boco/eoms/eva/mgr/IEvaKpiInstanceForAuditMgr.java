package com.boco.eoms.eva.mgr;

import java.util.List;

import com.boco.eoms.eva.model.EvaKpiInstanceForAudit;
import com.boco.eoms.eva.model.EvaTemplate;

/**
 * <p>
 * Title:指标考核实例业务方法类
 * </p>
 * <p>
 * Description:指标考核实例业务方法类
 * </p>
 * <p>
 * Date:2008-11-26 下午10:43:41
 * </p>
 * 
 * @author 李秋野
 * @version 3.5.1
 * 
 */
public interface IEvaKpiInstanceForAuditMgr {

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
	 * 查询某任务某时间所属周期的所有指标考核实例
	 * 
	 * @param orgId
	 *            任务Id
	 * @param date
	 *            日期
	 * @return
	 */
	public List listKpiInstanceForAuditOfTemplate(String orgId, String date);

	/**
	 * 判断某任务在某时间段内的考核实例是否已经生成
	 * 
	 * @param orgId
	 *            任务Id
	 * @param date
	 *            日期（yyyy-MM-dd）
	 * @return
	 */
	public boolean isInstanceExistsInTime(String orgId, String date);

	/**
	 * 生成某模板的考核实例
	 * 
	 * @param orgId
	 *            任务Id
	 * @param genOperatorId
	 *            生成操作执行人
	 */
	public void genKpiInstanceForAudit(String orgId, String genOperatorId);

	/**
	 * 获得某指标在某任务的某个周期内的考核分数
	 * 
	 * @param orgId
	 *            任务Id
	 * @param kpiId
	 *            指标Id
	 * @param date
	 *            日期
	 * @return
	 */
	public EvaKpiInstanceForAudit getKpiInstanceForAudit(String orgId, String kpiId,
			String date);
	
	/**
	 * 查询某任务某时间所属周期的所有指标考核实例
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
	 * 获得某任务的所有指标，指标包含某个周期的考核分数，若模板本周期内未生成过实例则返回空列表
	 * 
	 * @param orgId
	 *            任务Id
	 * @param date
	 *            日期
	 * @return
	 */
	public List listKpiOfTemplateWithScore(String orgId, String date);
	
	/**
	 * 计算某任务在某个周期内的总分
	 * 
	 * @param orgId
	 *            任务Id
	 * @param date
	 *            日期
	 * @return
	 */
	public Float getTotalScoreOfTemplate(String orgId, String date);
	
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
