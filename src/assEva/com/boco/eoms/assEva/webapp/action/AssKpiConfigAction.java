package com.boco.eoms.assEva.webapp.action;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.assEva.mgr.IAssKpiConfigMgr;
import com.boco.eoms.assEva.model.AssKpiConfig;
import com.boco.eoms.assEva.webapp.form.AssKpiConfigForm;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.ui.util.JSONUtil;

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
 
 public final class AssKpiConfigAction extends BaseAction {
 
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
		return tree(mapping, form, request, response);
	}
	
	/**
	 * 指标打分配置树页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward tree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String kpiId = StaticMethod.null2String(request.getParameter("kpiId"));
		request.setAttribute("kpiId", kpiId);
		return mapping.findForward("tree");
	}
 	
 	/**
	 * 生成指标打分配置树JSON数据
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String getNodes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String kpiId = StaticMethod.null2String(request.getParameter("kpiId"));
		String nodeId = StaticMethod.null2String(request.getParameter("node"));
		JSONArray jsonRoot = new JSONArray();
		IAssKpiConfigMgr assKpiConfigMgr = (IAssKpiConfigMgr) getBean("IassKpiConfigMgr");
		// 取下级节点
		StringBuffer whereStr = new StringBuffer();
		whereStr.append(" where 1=1 ");
		whereStr.append(" and  parentNodeId='");
		whereStr.append(nodeId);
		whereStr.append("' and kpiId = '");
		whereStr.append(kpiId);
		whereStr.append("'");
		List list = assKpiConfigMgr.getAssKpiConfigs(whereStr.toString());
		for (Iterator nodeIter = list.iterator(); nodeIter.hasNext();) {
			AssKpiConfig assKpiConfig = (AssKpiConfig) nodeIter.next();
			JSONObject jitem = new JSONObject();
			jitem.put("id", assKpiConfig.getNodeId());
			jitem.put("text", assKpiConfig.getRemark());
			// 设置右键菜单
			if(assKpiConfig.getNodeId().length()<8){
				jitem.put("allowChild", true);
			}
			jitem.put("allowEdit", true);
			jitem.put("allowDelete", true);
			// 设置左键点击可触发action
			jitem.put("allowClick", true);
			// 设置是否为叶子节点
			boolean leafFlag = true;
			if (assKpiConfigMgr.isHasNextLevel(assKpiConfig.getNodeId())) {
				leafFlag = false;
			}
			jitem.put("leaf", leafFlag);
			// TODO 设置鼠标悬浮提示
//			jitem.put("qtip", your tips here);
			jsonRoot.put(jitem);
		}
		JSONUtil.print(response, jsonRoot.toString());
		return null;
	}
 	
 	/**
	 * 新增指标打分配置
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
		String nodeId = StaticMethod.null2String(request.getParameter("node"));
		String kpiId = StaticMethod.null2String(request.getParameter("kpiId"));
		AssKpiConfigForm assKpiConfigForm = (AssKpiConfigForm) form;
		assKpiConfigForm.setKpiId(kpiId);
		assKpiConfigForm.setParentNodeId(nodeId);
		IAssKpiConfigMgr assKpiConfigMgr = (IAssKpiConfigMgr) getBean("IassKpiConfigMgr");
		if(!"1".equals(nodeId)){
			AssKpiConfig assKpiConfig = assKpiConfigMgr.getAssKpiConfigByNodeId(nodeId);
			request.setAttribute("algorithmValue", assKpiConfig.getAlgorithm());
		}
		updateFormBean(mapping, request, assKpiConfigForm);
		return mapping.findForward("edit");
	}
	
	/**
	 * 修改指标打分配置
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
		String nodeId = StaticMethod.null2String(request.getParameter("node"));
		IAssKpiConfigMgr assKpiConfigMgr = (IAssKpiConfigMgr) getBean("IassKpiConfigMgr");
		AssKpiConfig assKpiConfig = assKpiConfigMgr.getAssKpiConfigByNodeId(nodeId);
		if(nodeId.length()>7){
			AssKpiConfig assKpiConfigParent = assKpiConfigMgr.getAssKpiConfigByNodeId(assKpiConfig.getParentNodeId());
			request.setAttribute("algorithmValue", assKpiConfigParent.getAlgorithm());
		}
		AssKpiConfigForm assKpiConfigForm = (AssKpiConfigForm) convert(assKpiConfig);
		updateFormBean(mapping, request, assKpiConfigForm);
		return mapping.findForward("edit");
	}
	
	/**
	 * 保存指标打分配置
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
		IAssKpiConfigMgr assKpiConfigMgr = (IAssKpiConfigMgr) getBean("IassKpiConfigMgr");
		AssKpiConfigForm assKpiConfigForm = (AssKpiConfigForm) form;
		AssKpiConfig assKpiConfig = (AssKpiConfig) convert(assKpiConfigForm);
		assKpiConfigMgr.saveAssKpiConfig(assKpiConfig);
		return mapping.findForward("success");
	}
	
	/**
	 * 删除指标打分配置
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
		String nodeId = StaticMethod.null2String(request.getParameter("nodeId"));
		IAssKpiConfigMgr assKpiConfigMgr = (IAssKpiConfigMgr) getBean("IassKpiConfigMgr");
		assKpiConfigMgr.removeAssKpiConfigByNodeId(nodeId);
		return mapping.findForward("success");
	}
}