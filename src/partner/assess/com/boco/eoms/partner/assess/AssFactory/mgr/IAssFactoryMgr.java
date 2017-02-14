package com.boco.eoms.partner.assess.AssFactory.mgr;

import java.util.List;

import com.boco.eoms.partner.assess.AssFactory.model.AssFactory;

/**
 * <p>
 * Title:考核厂家关联方法类
 * </p>
 * <p>
 * Description:考核厂家关联方法类
 * </p>
 * <p>
 * Date:2009-10-27 下午 14：04
 * </p>
 * 
 * @author 贾智会
 * @version 1.0
 * 
 */
public interface IAssFactoryMgr {

	/**
	 * 根据 模板id 查询单个考核厂家信息
	 * 
	 * @param templateId
	 *            模板Id        
	 * @return AssFactory
	 */
	public AssFactory getKpiFactory(String id);
	
	/**
	 * 根据 模板id 查询所有考核厂家信息
	 * 
	 * @param templateId
	 *            模板Id        
	 * @return AssFactory
	 */
	public List getAllKpiFactory(String templateId);
	
	/**
	 * 根据 节点id 查询所有考核厂家信息
	 * 
	 * @param nodeId
	 *            节点Id        
	 * @return AssFactory
	 */
	public List getAllKpiFactoryByNodeId(String nodeId);

	/**
	 * 保存考核厂商信息
	 * 
	 * @param kpiFactory
	 *            考核厂商信息
	 */
	public void saveKpiFactory(AssFactory kpiFactory);

	/**
	 * 删除考核厂商信息
	 * 
	 * @param kpiFactory
	 *            考核厂商信息
	 */
	public void removeKpiFactory(AssFactory kpiFactory);
	/**
	 * 更新考核厂商信息
	 * 
	 * @param kpiFactory
	 *            更新考核厂商信息
	 */
	public void updateKpiFactory(AssFactory kpiFactory);
	
	/**
	 * 根据节点Id找到模板Id
	 * 
	 * @param nodeId
	 *            节点Id
	 */
	public String findTemplateId(String nodeId); 
	
	/**
	 * 根据厂商Id查找厂商名称
	 * 贾智会 2009-11-18
	 * @param fuctury
	 * 
	 * @return
	 */
	public AssFactory getKpiFactoryByFactory(String factory);
	
	/**
	 * 根据地市，专业得到符合条件的大合作伙伴Id
	 * @param city 地市
	 * @param specialty 专业
	 * @return
	 */	
	public List getPartnerDepts(String city,String specialty) ;
}
