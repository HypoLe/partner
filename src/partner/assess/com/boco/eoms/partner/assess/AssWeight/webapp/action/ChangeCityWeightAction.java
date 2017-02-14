package com.boco.eoms.partner.assess.AssWeight.webapp.action;


/**
 * <p>
 * Title:地市配置权重
 * </p>
 * <p>
 * Description:地市配置权重
 * </p>
 * <p>
 * Tue Feb 22 15:42:16 CST 2011
 * </p>
 * 
 * @moudle.getAuthor() benweiwei
 * @moudle.getVersion() 1.0
 * 
 */
public class ChangeCityWeightAction extends CityWeightAction {
	
	public ChangeCityWeightAction(){
		beenNameForCityWeightMgr = "IchangeCityWeightMgr";
		beenNameForRoleIdList = "lineAssRoleIdList";
		beenNameForTreeMgr = "IchangeAssTreeMgr";
	}
}