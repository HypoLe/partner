/**
 * 
 */
package com.boco.eoms.partner.assess.AssExecute.webapp.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.assess.AssExecute.mgr.IAssKpiInstanceMgr;
import com.boco.eoms.partner.assess.AssExecute.mgr.IAssTaskDetailMgr;
import com.boco.eoms.partner.assess.AssExecute.mgr.IAssTaskMgr;
import com.boco.eoms.partner.assess.AssExecute.model.AssKpiInstance;
import com.boco.eoms.partner.assess.AssExecute.model.AssTask;
import com.boco.eoms.partner.assess.AssExecute.model.AssTaskDetail;
import com.boco.eoms.partner.assess.AssExecute.webapp.form.AssKpiInstanceForm;
import com.boco.eoms.partner.assess.AssFactory.mgr.IAssFactoryMgr;
import com.boco.eoms.partner.assess.AssFee.mgr.IFeeTotalMgr;
import com.boco.eoms.partner.assess.AssFee.model.FeeTotal;
import com.boco.eoms.partner.assess.AssReport.mgr.IAssReportInfoMgr;
import com.boco.eoms.partner.assess.AssReport.model.AssReportInfo;
import com.boco.eoms.partner.assess.AssTree.mgr.IAssKpiMgr;
import com.boco.eoms.partner.assess.AssTree.mgr.IAssTemplateMgr;
import com.boco.eoms.partner.assess.AssTree.model.AssKpi;
import com.boco.eoms.partner.assess.util.AssConstants;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Date:Nov 30, 2010 5:40:15 PM
 * </p>
 * 
 * @author 戴志刚
 * @version 1.0
 * 
 */
public class ChangeAssExecuteAction extends AssExecuteAction {
	public ChangeAssExecuteAction() { 
		beenNameForTreeMgr = "IchangeAssTreeMgr";
		beenNameForTemplateMgr = "IchangeAssTemplateMgr";
		beenNameForKpiMgr = "IchangeAssKpiMgr";
		beenNameForTaskMgr = "IchangeAssTaskMgr";
		beenNameForTaskDetailMgr ="IchangeAssTaskDetailMgr";
		beenNameForKpiInstanceMgr = "IchangeAssKpiInstanceMgr";
		beenNameForReportInfoMgr = "IchangeAssReportInfoMgr";
		beenNameForFactoryMgr = "IchangeAssFactoryMgr";
		beenNameForRoleService = "IchangeAssRoleService";
		beenNameForFlowMgr = "IchangeAssFlowMgr";
		beenNameForOperateStepMgr = "IchangeAssOperateStepMgr";
		templateTypeNode = "1003";
//		交换专业
		specialty = "112131102";
		beenNameForSelectedInstanceMgr = "IchangeAssSelectedInstanceMgr";
		} 

}
