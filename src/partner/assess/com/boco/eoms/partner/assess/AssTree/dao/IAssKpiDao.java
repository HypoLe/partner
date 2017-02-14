/**
 * 
 */
package com.boco.eoms.partner.assess.AssTree.dao;

import java.util.ArrayList;
import java.util.List;

import com.boco.eoms.partner.assess.AssTree.model.AssKpi;


/**
 * <p>
 * Title:后评估指标Dao接口类
 * </p>
 * <p>
 * Description:后评估指标Dao接口类
 * </p>
 * <p>
 * Date:Nov 24, 2010 3:38:30 PM
 * </p>
 * 
 * @author 戴志刚
 * @version 1.0
 * 
 */
public interface IAssKpiDao {

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
	 * 通过节点Id取指标
	 * 
	 * @param nodeId
	 */
	public AssKpi getKpiByNodeId(String nodeId);

	/**
	 * 保存指标类型
	 * 
	 * @param AssKpi
	 *            指标
	 */
	public void saveKpi(AssKpi kpi);
	
	/**
	 * 保存指标类型
	 * 
	 * @param AssKpi
	 *            指标
	 */
	public void saveNewKpi(AssKpi kpi);

	/**
	 * 删除指标
	 * 
	 * @param kpi
	 *            指标
	 */
	public void removeKpi(AssKpi kpi);

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
