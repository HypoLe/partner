package com.boco.eoms.partner.assess.AssWeight.mgr;

import com.boco.eoms.partner.assess.AssWeight.model.AssWeight;


/**
 * <p>
 * Title:个性权重业务方法类
 * </p>
 * <p>
 * Description:个性权重业务方法类
 * </p>
 * <p>
 * Date:2010-11-26 上午10:17
 * </p>
 * 
 * @author 戴志刚
 * @version 1.0
 * 
 */
public interface IAssWeightMgr {

	/**
	 * 根据主键area 和 nodeId 查询权重
	 * 
	 * @param area
	 *            地域
	 * @param nodeId
	 *            节点ID
	 * @return
	 */
	public AssWeight getWeight(String area ,String nodeId);

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
	 * @param id
	 *            权重所对应的ID
	 */
	public void removeWeight(AssWeight weight);

	
	/**
	 * 修改权重
	 * 
	 * @param weight
	 *            权重
	 */
	public void updateWeight(AssWeight weight);

}
