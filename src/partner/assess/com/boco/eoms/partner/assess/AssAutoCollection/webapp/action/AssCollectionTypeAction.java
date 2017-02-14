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
import com.boco.eoms.partner.assess.AssAutoCollection.mgr.AssCollectionTypeMgr;
import com.boco.eoms.partner.assess.AssAutoCollection.model.AssAutoCollection;
import com.boco.eoms.partner.assess.AssAutoCollection.model.AssCollectionConfig;
import com.boco.eoms.partner.assess.AssAutoCollection.model.AssCollectionType;
import com.boco.eoms.partner.assess.AssAutoCollection.util.AssCollectionTypeConstants;
import com.boco.eoms.partner.assess.AssAutoCollection.webapp.form.AssCollectionTypeForm;
import com.boco.eoms.partner.assess.util.AssConstants;

/**
 * <p>
 * Title:采集类型
 * </p>
 * <p>
 * Description:采集类型
 * </p>
 * <p>
 * Thu Mar 31 09:11:04 CST 2011
 * </p>
 * 
 * @moudle.getAuthor() benweiwei
 * @moudle.getVersion() 1.0
 * 
 */
public final class AssCollectionTypeAction extends BaseAction {
 
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
	 * 新增采集类型
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
//    	得到节点Id
    	String nodeId = StaticMethod.null2String(request.getParameter("nodeId"));
//		AssAutoCollectionMgr assAutoCollectionMgr = (AssAutoCollectionMgr) getBean("assAutoCollectionMgr");
//		保存采集类型
//		AssAutoCollection assAutoCollection =  new AssAutoCollection();
//		assAutoCollection.setParentNodeId(AssConstants.TREE_ROOT_ID);
//		assAutoCollection.setNodeType("type");
//		assAutoCollection.setLeaf(AssConstants.NODE_LEAF);
//		assAutoCollectionMgr.saveAssAutoCollection(assAutoCollection);
		
//		request.setAttribute("assAutoCollection", assAutoCollection);
    	request.setAttribute("nodeId", nodeId);
//    	request.setAttribute("nodeType", "type");
		return mapping.findForward("edit");
	}
	
	/**
	 * 修改采集类型
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
		AssCollectionTypeMgr assCollectionTypeMgr = (AssCollectionTypeMgr) getBean("assCollectionTypeMgr");
		String nodeId = StaticMethod.null2String(request.getParameter("nodeId"));
		AssCollectionType assCollectionType = assCollectionTypeMgr.getAssCollectionTypeByNodeId(nodeId);
		AssCollectionTypeForm assCollectionTypeForm = (AssCollectionTypeForm) convert(assCollectionType);
		updateFormBean(mapping, request, assCollectionTypeForm);
		return mapping.findForward("edit");
	}
	
	/**
	 * 保存采集类型
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
		AssCollectionTypeMgr assCollectionTypeMgr = (AssCollectionTypeMgr) getBean("assCollectionTypeMgr");
		AssAutoCollectionMgr assAutoCollectionMgr = (AssAutoCollectionMgr) getBean("assAutoCollectionMgr");

		AssCollectionTypeForm assCollectionTypeForm = (AssCollectionTypeForm) form;
		AssCollectionType assCollectionType = (AssCollectionType) convert(assCollectionTypeForm);
		AssAutoCollection assAutoCollection = null;
		if(assCollectionType.getId()==null||"".equals(assCollectionType.getId())){
			assAutoCollection =  new AssAutoCollection();
			assAutoCollection.setParentNodeId(AssConstants.TREE_ROOT_ID);
			assAutoCollection.setNodeType("type");
			assAutoCollection.setLeaf(AssConstants.NODE_LEAF);
			assAutoCollection.setNodeName(assCollectionType.getName());
			assAutoCollectionMgr.saveAssAutoCollection(assAutoCollection);
		} else {
			assAutoCollection = assAutoCollectionMgr.getAssAutoCollectionByNodeId(assCollectionType.getTreeNodeId());
			assAutoCollection.setNodeName(assCollectionType.getName());
			assAutoCollectionMgr.saveAssAutoCollection(assAutoCollection);
		}
		assCollectionType.setTreeNodeId(assAutoCollection.getNodeId());
		
		assCollectionTypeMgr.saveAssCollectionType(assCollectionType);
		return mapping.findForward("success");
	}
	
	/**
	 * 删除采集类型
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
		AssCollectionTypeMgr assCollectionTypeMgr = (AssCollectionTypeMgr) getBean("assCollectionTypeMgr");
		AssAutoCollectionMgr assAutoCollectionMgr = (AssAutoCollectionMgr) getBean("assAutoCollectionMgr");
		AssCollectionConfigMgr assCollectionConfigMgr = (AssCollectionConfigMgr) getBean("assCollectionConfigMgr");
		
		String nodeId = StaticMethod.null2String(request.getParameter("nodeId"));
		AssCollectionType assCollectionType = assCollectionTypeMgr.getAssCollectionTypeByNodeId(nodeId);
		
		assCollectionTypeMgr.removeAssCollectionType(assCollectionType.getId());
		List list = (List)assAutoCollectionMgr.getChildNodes(nodeId);
		AssAutoCollection assAutoCollection = null;
		for(int i = 0;i<list.size();i++){
			assAutoCollection = (AssAutoCollection)list.get(i);
			AssCollectionConfig assCollectionConfig = assCollectionConfigMgr.getAssCollectionConfigByNodeId(assAutoCollection.getNodeId());
			assCollectionConfigMgr.removeAssCollectionConfig(assCollectionConfig.getId());
		}
		assAutoCollectionMgr.removeAssAutoCollectionByNodeId(nodeId);
		return mapping.findForward("success");
	}
	
	/**
	 * 分页显示采集类型列表
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
				AssCollectionTypeConstants.ASSCOLLECTIONTYPE_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = Integer.valueOf(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		AssCollectionTypeMgr assCollectionTypeMgr = (AssCollectionTypeMgr) getBean("assCollectionTypeMgr");
		Map map = (Map) assCollectionTypeMgr.getAssCollectionTypes(pageIndex, pageSize, "");
		List list = (List) map.get("result");
		request.setAttribute(AssCollectionTypeConstants.ASSCOLLECTIONTYPE_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("list");
	}
	
}