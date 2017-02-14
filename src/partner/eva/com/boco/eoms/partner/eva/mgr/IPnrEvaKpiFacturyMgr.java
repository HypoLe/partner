package com.boco.eoms.partner.eva.mgr;

import java.util.List;
import com.boco.eoms.partner.eva.model.PnrEvaKpiFactury;

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
public interface IPnrEvaKpiFacturyMgr {

	/**
	 * 根据 模板id 查询单个考核厂家信息
	 * 
	 * @param templateId
	 *            模板Id        
	 * @return PnrEvaKpiFactury
	 */
	public PnrEvaKpiFactury getKpiFactury(String id);
	
	/**
	 * 根据 模板id 查询所有考核厂家信息
	 * 
	 * @param templateId
	 *            模板Id        
	 * @return PnrEvaKpiFactury
	 */
	public List getAllKpiFactury(String templateId);
	
	/**
	 * 根据 节点id 查询所有考核厂家信息
	 * 
	 * @param nodeId
	 *            节点Id        
	 * @return PnrEvaKpiFactury
	 */
	public List getAllKpiFacturyByNodeId(String nodeId);

	/**
	 * 保存考核厂商信息
	 * 
	 * @param kpiFactury
	 *            考核厂商信息
	 */
	public void saveKpiFactury(PnrEvaKpiFactury kpiFactury);

	/**
	 * 删除考核厂商信息
	 * 
	 * @param kpiFactury
	 *            考核厂商信息
	 */
	public void removeKpiFactury(PnrEvaKpiFactury kpiFactury);
	/**
	 * 更新考核厂商信息
	 * 
	 * @param kpiFactury
	 *            更新考核厂商信息
	 */
	public void updateKpiFactury(PnrEvaKpiFactury kpiFactury);
	
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
	public PnrEvaKpiFactury getKpiFacturyByFactury(String factury);
	

}
