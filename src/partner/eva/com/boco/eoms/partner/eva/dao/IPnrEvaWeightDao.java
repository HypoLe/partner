package com.boco.eoms.partner.eva.dao;

import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.partner.eva.model.PnrEvaWeight;

/**
 * 
 * <p>
 * Title:节点个性权重Dao接口
 * </p>
 * <p>
 * Description:节点个性权重Dao接口
 * </p>
 * <p>
 * Date:2009-10-26 上午10：03
 * </p>
 * 
 * @author 贾智会
 * @version 1.0
 * 
 */
public interface IPnrEvaWeightDao extends Dao {

	/**
	 * 根据主键area 和 nodeId 查询权重
	 * 
	 * @param area
	 *            地域
	 * @param nodeId
	 *            节点Id          
	 * @return PnrEvaWeight
	 */
	public PnrEvaWeight getWeight(String area,String nodeId);

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
	 * @param weight
	 *            权重
	 */
	public void removeWeight(PnrEvaWeight weight);
	/**
	 * 更新权重
	 * 
	 * @param weight
	 *            权重
	 */
	public void updateWeight(PnrEvaWeight weight);

}
