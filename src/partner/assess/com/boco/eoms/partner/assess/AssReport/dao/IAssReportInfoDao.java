/**
 * 
 */
package com.boco.eoms.partner.assess.AssReport.dao;

import java.util.List;

import com.boco.eoms.partner.assess.AssReport.model.AssReportInfo;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Date:Dec 2, 2010 6:23:42 AM
 * </p>
 * 
 * @author 戴志刚
 * @version 1.0
 * 
 */
public interface IAssReportInfoDao {
	/**
	 * 根据id得到对应的汇总信息
	 * 
	 * @param id
	 *            汇总信息id
	 *            
	 * @return AssReportInfo 汇总信息
	 */
	public AssReportInfo getAssReportInfo(String id);
	/**
	 * 保存汇总信息
	 * 
	 * @param AssReportInfo
	 *                 汇总信息
	 *            
	 * @return void 
	 */
	public void saveAssReportInfo(AssReportInfo assReportInfo);
	/**
	 * 根据时间地域厂商等查询对应信息
	 * 
	 * @param AssReportInfo
	 *                 汇总信息
	 *            
	 * @return AssReportInfo 
	 */
	public List getReportInfoByTimeAndPartner(String taskId,String areaId,
			String timeType,String time, String partnerId,String publicFlag);
	/**
	 * 根据树节点Id时间地域厂商等查询对应信息
	 * 
	 * @param AssReportInfo
	 *                 汇总信息
	 *            
	 * @return AssReportInfo 
	 */	
	public List getReportInfoByTreeNodeId(final String treeNodeId,final String areaId,
			final String timeType,final String time, final String partnerId,final String publicFlag) ;
}
