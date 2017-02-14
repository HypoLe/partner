package com.boco.eoms.partner.tranEva.webapp.action;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.ui.util.JSONUtil;
import com.boco.eoms.partner.tranEva.mgr.ITranKpiConfigMgr;
import com.boco.eoms.partner.tranEva.model.TranKpiConfig;
import com.boco.eoms.partner.tranEva.webapp.form.TranKpiConfigForm;

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
 
 public final class TranKpiConfigAction extends BaseAction {
 
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
		ITranKpiConfigMgr tranKpiConfigMgr = (ITranKpiConfigMgr) getBean("ItranKpiConfigMgr");
		// 取下级节点
		StringBuffer whereStr = new StringBuffer();
		whereStr.append(" where 1=1 ");
		whereStr.append(" and  parentNodeId='");
		whereStr.append(nodeId);
		whereStr.append("' and kpiId = '");
		whereStr.append(kpiId);
		whereStr.append("'");
		List list = tranKpiConfigMgr.getTranKpiConfigs(whereStr.toString());
		for (Iterator nodeIter = list.iterator(); nodeIter.hasNext();) {
			TranKpiConfig tranKpiConfig = (TranKpiConfig) nodeIter.next();
			JSONObject jitem = new JSONObject();
			jitem.put("id", tranKpiConfig.getNodeId());
			jitem.put("text", tranKpiConfig.getRemark());
			// 设置右键菜单
			if(tranKpiConfig.getNodeId().length()<8){
				jitem.put("allowChild", true);
			}
			jitem.put("allowEdit", true);
			jitem.put("allowDelete", true);
			// 设置左键点击可触发action
			jitem.put("allowClick", true);
			// 设置是否为叶子节点
			boolean leafFlag = true;
			if (tranKpiConfigMgr.isHasNextLevel(tranKpiConfig.getNodeId())) {
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
		TranKpiConfigForm tranKpiConfigForm = (TranKpiConfigForm) form;
		tranKpiConfigForm.setKpiId(kpiId);
		tranKpiConfigForm.setParentNodeId(nodeId);
		ITranKpiConfigMgr tranKpiConfigMgr = (ITranKpiConfigMgr) getBean("ItranKpiConfigMgr");
		if(!"1".equals(nodeId)){
			TranKpiConfig tranKpiConfig = tranKpiConfigMgr.getTranKpiConfigByNodeId(nodeId);
			request.setAttribute("algorithmValue", tranKpiConfig.getAlgorithm());
		}
		updateFormBean(mapping, request, tranKpiConfigForm);
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
		ITranKpiConfigMgr tranKpiConfigMgr = (ITranKpiConfigMgr) getBean("ItranKpiConfigMgr");
		TranKpiConfig tranKpiConfig = tranKpiConfigMgr.getTranKpiConfigByNodeId(nodeId);
		if(nodeId.length()>7){
			TranKpiConfig tranKpiConfigParent = tranKpiConfigMgr.getTranKpiConfigByNodeId(tranKpiConfig.getParentNodeId());
			request.setAttribute("algorithmValue", tranKpiConfigParent.getAlgorithm());
		}
		TranKpiConfigForm tranKpiConfigForm = (TranKpiConfigForm) convert(tranKpiConfig);
		updateFormBean(mapping, request, tranKpiConfigForm);
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
		ITranKpiConfigMgr tranKpiConfigMgr = (ITranKpiConfigMgr) getBean("ItranKpiConfigMgr");
		TranKpiConfigForm tranKpiConfigForm = (TranKpiConfigForm) form;
		TranKpiConfig tranKpiConfig = (TranKpiConfig) convert(tranKpiConfigForm);
		tranKpiConfigMgr.saveTranKpiConfig(tranKpiConfig);
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
		ITranKpiConfigMgr tranKpiConfigMgr = (ITranKpiConfigMgr) getBean("ItranKpiConfigMgr");
		tranKpiConfigMgr.removeTranKpiConfigByNodeId(nodeId);
		return mapping.findForward("success");
	}
}