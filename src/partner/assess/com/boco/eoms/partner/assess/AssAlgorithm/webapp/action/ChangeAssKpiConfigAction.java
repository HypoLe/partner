package com.boco.eoms.partner.assess.AssAlgorithm.webapp.action;


/**
 * <p>
 * Title:指标打分配置
 * </p>
 * <p>
 * Description:指标打分配置
 * </p>
 * <p>
 * Tue Nov 16 09:08:10 CST 2010
 * </p>
 * 
 * @moudle.getAuthor() benweiwei
 * @moudle.getVersion() 1.0
 * 
 */
 
public class ChangeAssKpiConfigAction extends AssKpiConfigAction {
	public ChangeAssKpiConfigAction(){
		 beenNameForKpiConfigMgr = "IchangeAssKpiConfigMgr";
		 beenNameForSelectedInstanceMgr = "IchangeAssSelectedInstanceMgr";
		 beenNameForKpiMgr = "IchangeAssKpiMgr";
	}
}