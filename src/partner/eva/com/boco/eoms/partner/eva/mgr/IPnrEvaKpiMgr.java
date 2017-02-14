package com.boco.eoms.partner.eva.mgr;

import com.boco.eoms.partner.eva.model.PnrEvaKpi;

/**
 * <p>
 * Title:指标业务方法类
 * </p>
 * <p>
 * Description:指标业务方法类
 * </p>
 * <p>
 * Date:2008-11-20 上午11:06:40
 * </p>
 * 
 * @author 李秋野
 * @version 3.5.1
 * 
 */
public interface IPnrEvaKpiMgr {

	/**
	 * 根据主键id查询指标
	 * 
	 * @param id
	 *            主键
	 * @return
	 */
	public PnrEvaKpi getKpi(String id);

	/**
	 * 根据主键id标志和删除查询指标
	 * 
	 * @param id
	 *            主键
	 * @param deleted
	 *            删除标志
	 * @return
	 */
	public PnrEvaKpi getKpi(String id, String deleted);

	/**
	 * 保存指标类型
	 * 
	 * @param EvaKpi
	 *            指标
	 */
	public void saveKpi(PnrEvaKpi kpi);

	/**
	 * 保存指标类型，同时保存节点
	 * 
	 * @param kpi
	 *            指标
	 * @param parentNodeId
	 *            父节点ID
	 */
	public void saveKpiAndNode(PnrEvaKpi kpi, String parentNodeId,String isActived);
	public void saveKpiAndNode(PnrEvaKpi kpi, String parentNodeId);
	/**
	 * 删除kpi指标
	 * 
	 * @param kpi
	 *            指标
	 */
	public void removeKpi(PnrEvaKpi kpi);

	/**
	 * 获得某指标在某模板的某个周期的考核分数
	 * 
	 * @param templateId
	 *            模板Id
	 * @param kpiId
	 *            指标Id
	 * @param date
	 *            日期
	 * @return
	 */
	public float getScoreOfKpi(String templateId, String kpiId, String date);

	/**
	 * 根据指标Id返回指标名称
	 * 
	 * @param id
	 *            指标Id
	 * @return
	 */
	public String id2Name(String id);

	/**
	 * 计算某节点下目前可分配权重
	 * 
	 * @param parentNodeId
	 *            父节点Id
	 * @return
	 */
	public float getNextLevelUsableWt(String parentNodeId);

	/**
	 * 计算某节点目前可调整权重的最小值
	 * 
	 * @param parentNodeId
	 *            父节点Id
	 * @param kpiId
	 *            指标Id（修改指标时计算权重传入kpiId，新增指标时计算权重则传入空字符串）
	 * @return
	 */
	public float getMinWt(String parentNodeId, String kpiId);

	/**
	 * 计算某节点目前可调整权重的最大值
	 * 
	 * @param parentNodeId
	 *            父节点Id
	 * @param kpiId
	 *            指标Id（修改指标时计算权重传入kpiId，新增指标时计算权重则传入空字符串）
	 * @return
	 */
	public float getMaxWt(String parentNodeId, String kpiId);
	/**
	 * 判断指标名称是否唯一
	 * 
	 *      
	 */
	public Boolean isunique(final String kpiName,final String parentNodeId);
}
