/**
 * 
 */
package com.boco.eoms.partner.assess.AssReport.webapp.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.system.dict.service.ID2NameService;
import com.boco.eoms.partner.assess.AssExecute.mgr.IAssTaskMgr;
import com.boco.eoms.partner.assess.AssFactory.mgr.IAssFactoryMgr;
import com.boco.eoms.partner.assess.AssReport.mgr.IAssReportInfoMgr;
import com.boco.eoms.partner.assess.AssReport.model.AssReportInfo;
import com.boco.eoms.partner.assess.AssRight.service.IAssRoleService;
import com.boco.eoms.partner.assess.util.AssConstants;


/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Date:Dec 2, 2010 6:39:50 AM
 * </p>
 * 
 * @author 戴志刚
 * @version 1.0
 * 
 */
public class ChangeAssReportInfoAction extends AssReportInfoAction{
	
	public ChangeAssReportInfoAction() { 
		beenNameForTreeMgr = "IchangeAssTreeMgr";
		beenNameForTemplateMgr = "IchangeAssTemplateMgr";
		beenNameForKpiMgr = "IchangeAssKpiMgr";
		beenNameForTaskMgr = "IchangeAssTaskMgr";
		beenNameForTaskDetailMgr ="IchangeAssTaskDetailMgr";
		beenNameForKpiInstanceMgr = "IchangeAssKpiInstanceMgr";
		beenNameForReportInfoMgr = "IchangeAssReportInfoMgr";
		beenNameForFactoryMgr = "IchangeAssFactoryMgr";
		beenNameForRoleService = "IchangeAssRoleService";
		beenNameForOperateStepMgr = "IchangeAssOperateStepMgr";
		templateTypeNode = "1003";
//		交换
		specialty = "112131102";
		beenNameForCityWeightMgr = "IchangeCityWeightMgr";
		beenNameForCityWeightExeMgr = "IchangeCityWeightExeMgr";
		} 	
	
}
