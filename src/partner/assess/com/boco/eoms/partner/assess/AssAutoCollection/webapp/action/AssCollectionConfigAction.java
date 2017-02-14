package com.boco.eoms.partner.assess.AssAutoCollection.webapp.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.partner.assess.AssAutoCollection.mgr.AssAutoCollectionMgr;
import com.boco.eoms.partner.assess.AssAutoCollection.mgr.AssCollectionConfigMgr;
import com.boco.eoms.partner.assess.AssAutoCollection.model.AssAutoCollection;
import com.boco.eoms.partner.assess.AssAutoCollection.model.AssCollectionConfig;
import com.boco.eoms.partner.assess.AssAutoCollection.util.AssCollectionConfigConstants;
import com.boco.eoms.partner.assess.AssAutoCollection.webapp.form.AssCollectionConfigForm;
import com.boco.eoms.partner.assess.util.AssConstants;

/**
 * <p>
 * Title:采集配置
 * </p>
 * <p>
 * Description:采集配置
 * </p>
 * <p>
 * Thu Mar 31 09:11:04 CST 2011
 * </p>
 * 
 * @moudle.getAuthor() benweiwei
 * @moudle.getVersion() 1.0
 * 
 */
public final class AssCollectionConfigAction extends BaseAction {
 
	/**
	 * 未指定方法时默认调用的方法
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return search(mapping, form, request, response);
	}
 	
 	/**
	 * 新增采集配置
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
    	String nodeId = StaticMethod.null2String(request.getParameter("nodeId"));
    	request.setAttribute("parentNodeId", nodeId);
		return mapping.findForward("edit");
	}
	
	/**
	 * 修改采集配置
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AssCollectionConfigMgr assCollectionConfigMgr = (AssCollectionConfigMgr) getBean("assCollectionConfigMgr");
		String nodeId = StaticMethod.null2String(request.getParameter("nodeId"));
		AssCollectionConfig assCollectionConfig = assCollectionConfigMgr.getAssCollectionConfigByNodeId(nodeId);
		AssCollectionConfigForm assCollectionConfigForm = (AssCollectionConfigForm) convert(assCollectionConfig);
		updateFormBean(mapping, request, assCollectionConfigForm);
		return mapping.findForward("edit");
	}
	
	/**
	 * 保存采集配置
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AssCollectionConfigMgr assCollectionConfigMgr = (AssCollectionConfigMgr) getBean("assCollectionConfigMgr");
		AssAutoCollectionMgr assAutoCollectionMgr = (AssAutoCollectionMgr) getBean("assAutoCollectionMgr");
		AssCollectionConfigForm assCollectionConfigForm = (AssCollectionConfigForm) form;
		boolean isNew = (null == assCollectionConfigForm.getId() || "".equals(assCollectionConfigForm.getId()));
		AssCollectionConfig assCollectionConfig = (AssCollectionConfig) convert(assCollectionConfigForm);
//		增加树图配置
		AssAutoCollection assAutoCollection = null;
		if (isNew) {
			assAutoCollection =  new AssAutoCollection();
			String parentNodeId = StaticMethod.null2String(request.getParameter("parentNodeId"));
			assAutoCollection.setParentNodeId(parentNodeId);
			assAutoCollection.setNodeType("sqlConfig");
			assAutoCollection.setLeaf(AssConstants.NODE_LEAF);
			assAutoCollection.setNodeName(assCollectionConfig.getName());
			assAutoCollectionMgr.saveAssAutoCollection(assAutoCollection);
		} else {
			assAutoCollection = assAutoCollectionMgr.getAssAutoCollectionByNodeId(assCollectionConfig.getTreeNodeId());
			assAutoCollection.setNodeName(assCollectionConfig.getName());
			assAutoCollectionMgr.saveAssAutoCollection(assAutoCollection);
		}
		assCollectionConfig.setTreeNodeId(assAutoCollection.getNodeId());
		assCollectionConfigMgr.saveAssCollectionConfig(assCollectionConfig);
		return mapping.findForward("success");
	}
	
	/**
	 * 删除采集配置
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward remove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AssCollectionConfigMgr assCollectionConfigMgr = (AssCollectionConfigMgr) getBean("assCollectionConfigMgr");
		AssAutoCollectionMgr assAutoCollectionMgr = (AssAutoCollectionMgr) getBean("assAutoCollectionMgr");
		String nodeId = StaticMethod.null2String(request.getParameter("nodeId"));
		AssCollectionConfig assCollectionConfig = assCollectionConfigMgr.getAssCollectionConfigByNodeId(nodeId);
		assCollectionConfigMgr.removeAssCollectionConfig(assCollectionConfig.getId());
		assAutoCollectionMgr.removeAssAutoCollectionByNodeId(nodeId);
		return mapping.findForward("success");
	}
	
	/**
	 * 分页显示采集配置列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				AssCollectionConfigConstants.ASSCOLLECTIONCONFIG_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = Integer.valueOf(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		AssCollectionConfigMgr assCollectionConfigMgr = (AssCollectionConfigMgr) getBean("assCollectionConfigMgr");
		Map map = (Map) assCollectionConfigMgr.getAssCollectionConfigs(pageIndex, pageSize, "");
		List list = (List) map.get("result");
		request.setAttribute(AssCollectionConfigConstants.ASSCOLLECTIONCONFIG_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("list");
	}
	
}