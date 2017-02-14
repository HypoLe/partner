package com.boco.eoms.partner.eva.mgr;

import java.util.List;

import com.boco.eoms.partner.eva.model.PnrEvaKpiInstance;

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
public interface IPnrEvaKpiInstanceMgr {

	/**
	 * 根据主键id查询指标考核实例
	 * 
	 * @param id
	 *            主键
	 * @return
	 */
	public PnrEvaKpiInstance getKpiInstance(String id);

	/**
	 * 保存指标考核实例
	 * 
	 * @param EvaKpiInstance
	 *            指标考核实例
	 */
	public void saveKpiInstance(PnrEvaKpiInstance kpiInstance);

	/**
	 * 删除指标考核实例
	 * 
	 * @param EvaKpiInstance
	 *            指标考核实例
	 */
	public void removeKpiInstance(PnrEvaKpiInstance kpiInstance);

	/**
	 * 查询某任务某时间所属周期的所有指标考核实例
	 * 王思轩 2009-1-20
	 * @param  任务id，时间类型，时间，合作伙伴ID
	 * @return 指标考核实例
	 */
	public PnrEvaKpiInstance getKpiInstanceByTimeAndPartner(final String taskDetailId,
			final String timeType,final String time, final String partnerId);
	
	/**
	 * 查询某任务某时间所属周期的所有指标考核实例
	 * 王思轩 2009-1-22
	 * @param  任务id，合作伙伴ID，时间类型，时间范围，
	 * @return 指标考核实例
	 */
	public List getKpiInstanceListByTimeAndPartner(final String taskDetailId, 
			final String partnerId, final String timeType,
			final String startTime, final String endTime, final String isPublish);

	/**
	 * 详细任务ID转换为模板名称
	 * 王思轩 09-1-21
	 * @return
	 */
	public String id2Name(String id);
	
	/**
	 * 根据任务详情信息Id查询指标考核实例
	 * 贾智会 09-11-05
	 * @return
	 */
	public PnrEvaKpiInstance getKpiInstanceByTaskDetailId(String taskDetailId);

	/**
	 * 根据地域信息得到所有草稿列表
	 * 
	 * @return
	 */
	public List getDraftKpiInstanceList(String areaId);
	/**
	 * 查询某任务某时间所属周期的所有指标考核实例
	 * @param  任务id，时间类型，执行时间，合作伙伴ID，发布标识，
	 * @return 指标考核实例
	 */
	public List getKpiInstanceAndDetail(final String taskId,
			final String timeType,final String time, 
			final String partnerId,final String isPublish);
}
