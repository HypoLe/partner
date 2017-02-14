/**
 * 
 */
package com.boco.eoms.partner.assess.AssTree.mgr;

import java.util.ArrayList;
import java.util.List;

import com.boco.eoms.partner.assess.AssTree.model.AssKpi;


/**
 * <p>
 * Title:指标业务方法类
 * </p>
 * <p>
 * Description:指标业务方法类
 * </p>
 * <p>
 * Date:Nov 24, 2010 3:36:30 PM
 * </p>
 * 
 * @author 戴志刚
 * @version 1.0
 * 
 */
public interface IAssKpiMgr {

	/**
	 * 根据主键id查询指标
	 * 
	 * @param id
	 *            主键
	 * @return
	 */
	public AssKpi getKpi(String id);

	/**
	 * 根据主键id标志和删除查询指标
	 * 
	 * @param id
	 *            主键
	 * @param deleted
	 *            删除标志
	 * @return
	 */
	public AssKpi getKpi(String id, String deleted);

	/**
	 * 保存指标类型
	 * 
	 * @param AssKpi
	 *            指标
	 */
	public void saveKpi(AssKpi kpi);

	/**
	 * 保存指标类型，同时保存节点
	 * 
	 * @param kpi
	 *            指标
	 * @param parentNodeId
	 *            父节点ID
	 */
	public void saveKpiAndNode(AssKpi kpi, String parentNodeId,String isActived,String isTotal,String oneTotal);
	public void saveKpiAndNode(AssKpi kpi, String parentNodeId);
	/**
	 * 删除kpi指标
	 * 
	 * @param kpi
	 *            指标
	 */
	public void removeKpi(AssKpi kpi);

	/**
	 * 通过节点Id取指标
	 * 
	 * @param nodeId
	 */
	public AssKpi getKpiByNodeId(String nodeId);

	/**
	 * 查询某指标分类下的所有指标
	 * 
	 * @param parentNodeId
	 *            指标分类id
	 * @return
	 */
	public ArrayList listKpiOfType(String parentNodeId);

	/**
	 * 删除某指标分类下的所有指标
	 * 
	 * @param parentNodeId
	 *            指标分类id
	 * @return
	 */
	public void removeKpiOfType(final String parentNodeId);

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
	public Float getNextLevelUsableWt(String parentNodeId);

	/**
	 * 计算某节点目前可调整权重的最小值
	 * 
	 * @param parentNodeId
	 *            父节点Id
	 * @param kpiId
	 *            指标Id（修改指标时计算权重传入kpiId，新增指标时计算权重则传入空字符串）
	 * @return
	 */
	public Float getMinWt(String parentNodeId, String kpiId);

	/**
	 * 计算某节点目前可调整权重的最大值
	 * 
	 * @param parentNodeId
	 *            父节点Id
	 * @param kpiId
	 *            指标Id（修改指标时计算权重传入kpiId，新增指标时计算权重则传入空字符串）
	 * @return
	 */
	public Float getMaxWt(String parentNodeId, String kpiId);
	/**
	 * 判断指标名称是否唯一
	 * 
	 *      
	 */
	public Boolean isunique(final String kpiName,final String parentNodeId);
	
	/**
	 * 按条件得到kpi指标
	 *      
	 */	
	public List getAssKpis( final String whereStr ) ;
}
