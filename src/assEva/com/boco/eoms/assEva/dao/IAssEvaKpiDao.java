package com.boco.eoms.assEva.dao;

import java.util.ArrayList;

import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.assEva.model.AssEvaKpi;

/**
 * 
 * <p>
 * Title:考核指标Dao接口
 * </p>
 * <p>
 * Description:考核指标Dao接口
 * </p>
 * <p>
 * Date:2008-12-4 下午08:29:50
 * </p>
 * 
 * @author 李秋野
 * @version 3.5.1
 * 
 */
public interface IAssEvaKpiDao extends Dao {

	/**
	 * 根据主键id查询指标
	 * 
	 * @param id
	 *            主键
	 * @return
	 */
	public AssEvaKpi getKpi(String id);

	/**
	 * 根据主键id标志和删除查询指标
	 * 
	 * @param id
	 *            主键
	 * @param deleted
	 *            删除标志
	 * @return
	 */
	public AssEvaKpi getKpi(String id, String deleted);

	/**
	 * 通过节点Id取指标
	 * 
	 * @param nodeId
	 */
	public AssEvaKpi getKpiByNodeId(String nodeId);

	/**
	 * 保存指标类型
	 * 
	 * @param AssEvaKpi
	 *            指标
	 */
	public void saveKpi(AssEvaKpi kpi);
	
	/**
	 * 保存指标类型
	 * 
	 * @param AssEvaKpi
	 *            指标
	 */
	public void saveNewKpi(AssEvaKpi kpi);

	/**
	 * 删除指标
	 * 
	 * @param kpi
	 *            指标
	 */
	public void removeKpi(AssEvaKpi kpi);

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

}
