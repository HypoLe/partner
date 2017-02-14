package com.boco.eoms.partner.eva.mgr;

import com.boco.eoms.partner.eva.model.PnrEvaWeight;

/**
 * <p>
 * Title:个性权重业务方法类
 * </p>
 * <p>
 * Description:个性权重业务方法类
 * </p>
 * <p>
 * Date:2009-10-26 上午10:17
 * </p>
 * 
 * @author 贾智会
 * @version 1.0
 * 
 */
public interface IPnrEvaWeightMgr {

	/**
	 * 根据主键area 和 nodeId 查询权重
	 * 
	 * @param area
	 *            地域
	 * @param nodeId
	 *            节点ID
	 * @return
	 */
	public PnrEvaWeight getWeight(String area ,String nodeId);

	/**
	 * 保存权重
	 * 
	 * @param weight
	 *            权重
	 */
	public void saveWeight(PnrEvaWeight weight);

	/**
	 * 删除权重
	 * 
	 * @param id
	 *            权重所对应的ID
	 */
	public void removeWeight(PnrEvaWeight weight);

	
	/**
	 * 修改权重
	 * 
	 * @param weight
	 *            权重
	 */
	public void updateWeight(PnrEvaWeight weight);

}
