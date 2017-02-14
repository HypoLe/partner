package com.boco.eoms.partner.assess.AssExecute.dao;

import java.util.List;

import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.partner.assess.AssExecute.model.AssKpiInstance;

/**
 * <p>
 * Title:考核实例Dao接口
 * </p>
 * <p>
 * Description:考核实例Dao接口
 * </p>
 * <p>
 * Date:Nov 26, 2010 5:52:06 PM
 * </p>
 * 
 * @author 戴志刚
 * @version 1.0
 * 
 */
public interface IAssKpiInstanceDao extends Dao {

	/**
	 * 根据主键id查询指标考核实例
	 * 
	 * @param id
	 *            主键
	 * @return
	 */
	public AssKpiInstance getKpiInstance(String id);

	/**
	 * 保存指标考核实例
	 * 
	 * @param AssKpiInstance
	 *            指标考核实例
	 */
	public void saveKpiInstance(AssKpiInstance kpiInstance);

	/**
	 * 删除指标考核实例
	 * 
	 * @param AssKpiInstance
	 *            指标考核实例
	 */
	public void removeKpiInstance(AssKpiInstance kpiInstance);

	/**
	 * 查询某任务某时间所属周期的指标考核实例
	 * 王思轩 2009-1-20
	 * @param  任务id，时间类型，时间，合作伙伴ID
	 * @return 指标考核实例
	 */
	public AssKpiInstance getKpiInstanceByTimeAndPartner(final String taskDetailId,
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
	 * 查询某任务某时间所属周期的所有指标考核实例
	 * 
	 * @param orgId
	 *            任务Id
	 * @param date
	 *            日期
	 * @return
	 */
	public List listKpiInstanceOfTemplate(final String orgId,
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
	public AssKpiInstance getKpiInstanceOfTemplate(final String orgId,
			final String kpiId, final String startDate, final String endDate);
	
	/**
	 * 详细任务ID转换为模板名称
	 * 王思轩 09-1-21
	 * @return
	 */
	public String id2Name(String id);
	/**
	 * 查询某任务某时间所属周期的指标考核实例
	 * 王思轩 2009-1-20
	 * @param  任务id，时间类型，时间，合作伙伴ID
	 * @return 指标考核实例
	 */	
	public AssKpiInstance getKpiInstanceByTimeAndPartner(
			final String taskDetailId, final String timeType,
			final String time, final String partnerId, final String city) ;
	/**
	 * 查询的指标考核实例
	 * 
	 * @param  taskDetailId,reportId
	 * @return 指标考核实例
	 */	
	public AssKpiInstance getKpiInstanceByReport(final String taskDetailId, final String reportId) ;

	/**
	 * 查询的指标考核实例
	 * 
	 * @param  parentNodeId父节点Id,partnerId 合作伙伴Id,timeStr 考核时间
	 * @return 指标考核实例
	 */		
	public List listKpiInstance(final String parentNodeId,final String partnerId, final String timeStr, final String city) ;

	/**
	 * 根据reportId得到评估指标分项得分
	 * 
	 * @param  reportId
	 * @return List
	 */	
	public List getKpiInstancesByReport(final String reportId);
	/**
	 * 得到评估指标分项得分
	 * 
	 * @param  nodeId指标节点Id,city 对应地市,time 执行时间
	 * @return Float
	 */	
	public List  getKpiInstanceRealScore(final String nodeId,
			final String city, final String time,final String partnerId) ;
	/**
	 * 得到指标考核实例
	 * 
	 * @param  reportId考核报表Id,specialty 对应专业
	 * @return List
	 */		
	public List getReportInfoBySpecialty(String reportId, String specialty) ;
	/**
	 * 得到指标考核实例
	 * 
	 * @param  reportId考核报表Id,quote kpi是否引用上月数据
	 * @return List
	 */	
	public List getAssKpiInstanceByQuote(String reportId, String quote) ;
}
