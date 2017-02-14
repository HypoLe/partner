/**
 * 
 */
package com.boco.eoms.partner.assess.AssReport.mgr;

import com.boco.eoms.partner.assess.AssReport.model.AssReportInfo;

/**
 * <p>
 * Title:考核报表信息业务方法类
 * </p>
 * <p>
 * Description:考核报表信息业务方法类
 * </p>
 * <p>
 * Date:Dec 2, 2010 6:08:58 AM
 * </p>
 * 
 * @author 戴志刚
 * @version 1.0
 * 
 */
public interface IAssReportInfoMgr {
	
	public void saveAssReportInfo(AssReportInfo assReportInfo);

	public AssReportInfo getAssReportInfo(String id);
	
	public AssReportInfo getReportInfoByTimeAndPartner(String taskId,String areaId,
			String timeType,String time, String partnerId,String publicFlag);
	
	public AssReportInfo getReportInfoByTreeNodeId(String treeNodeId,String areaId,
			String timeType,String time, String partnerId,String publicFlag);
	
}
