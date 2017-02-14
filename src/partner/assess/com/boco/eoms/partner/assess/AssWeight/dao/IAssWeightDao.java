package com.boco.eoms.partner.assess.AssWeight.dao;

import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.partner.assess.AssWeight.model.AssWeight;

/**
 * 
 * <p>
 * Title:节点个性权重Dao接口
 * </p>
 * <p>
 * Description:节点个性权重Dao接口
 * </p>
 * <p>
 * Date:2010-11-26 上午10：03
 * </p>
 * 
 * @author 戴志刚
 * @version 1.0
 * 
 */
public interface IAssWeightDao extends Dao {

	/**
	 * 根据主键area 和 nodeId 查询权重
	 * 
	 * @param area
	 *            地域
	 * @param nodeId
	 *            节点Id          
	 * @return AssWeight
	 */
	public AssWeight getWeight(String area,String nodeId);

	/**
	 * 保存权重
	 * 
	 * @param weight
	 *            权重
	 */
	public void saveWeight(AssWeight weight);

	/**
	 * 删除权重
	 * 
	 * @param weight
	 *            权重
	 */
	public void removeWeight(AssWeight weight);
	/**
	 * 更新权重
	 * 
	 * @param weight
	 *            权重
	 */
	public void updateWeight(AssWeight weight);

}
