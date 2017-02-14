package com.boco.eoms.commons.system.priv.webapp.action;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import utils.Nop3Utils;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.StaticVariable;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.loging.BocoLog;
import com.boco.eoms.commons.system.priv.model.TawSystemPrivMenuItem;
import com.boco.eoms.commons.system.priv.model.TawSystemPrivOperation;
import com.boco.eoms.commons.system.priv.service.ITawSystemPrivMenuItemManager;
import com.boco.eoms.commons.system.priv.service.ITawSystemPrivOperationManager;
import com.boco.eoms.commons.system.priv.service.ITawSystemPrivUserAssignManager;
import com.boco.eoms.commons.system.priv.webapp.form.TawSystemPrivMenuItemForm;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.commons.ui.bo.TawSystemTreeBo;
import com.boco.eoms.commons.ui.util.JSONUtil;
import com.google.common.base.Strings;

/**
 * Action class to handle CRUD on a TawSystemPrivMenuItem object
 * 
 * @struts.action name="tawSystemPrivMenuItemForm"
 *                path="/tawSystemPrivMenuItems" scope="request"
 *                validate="false" parameter="method" input="mainMenu"
 * @struts.action name="tawSystemPrivMenuItemForm"
 *                path="/editTawSystemPrivMenuItem" scope="request"
 *                validate="false" parameter="method" input="list"
 * @struts.action name="tawSystemPrivMenuItemForm"
 *                path="/saveTawSystemPrivMenuItem" scope="request"
 *                validate="true" parameter="method" input="edit"
 * @struts.action-set-property property="cancellable" value="true"
 * @struts.action-forward name="edit"
 *                        path="/WEB-INF/pages/tawSystemPrivMenuItem/tawSystemPrivMenuItemForm.jsp"
 * @struts.action-forward name="list"
 *                        path="/WEB-INF/pages/tawSystemPrivMenuItem/tawSystemPrivMenuItemList.jsp"
 * @struts.action-forward name="search" path="/tawSystemPrivMenuItems.html"
 *                        redirect="true"
 */
public final class TawSystemPrivMenuItemAction extends BaseAction {

	private static String VALIDMENUITEMS_LIST = "ValidMenuItemsList";

	/**
	 * 在编辑菜单树图时删除一个已配置的菜单项
	 */
	public void xdelete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String id = StaticMethod.null2String(request.getParameter("id"));
		String privid = StaticMethod
				.null2String(request.getParameter("privid"));
		String code = StaticMethod.null2String(request.getParameter("code"));

		ITawSystemPrivMenuItemManager mgr = (ITawSystemPrivMenuItemManager) getBean("ItawSystemPrivMenuItemManager");

		mgr.removeMenuItemAndSon(privid, code);

		JSONUtil.success(response, "菜单项删除成功");

	}

	public void xsave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ITawSystemPrivMenuItemManager mgr = (ITawSystemPrivMenuItemManager) getBean("ItawSystemPrivMenuItemManager");
		TawSystemPrivMenuItemForm menuItemform = (TawSystemPrivMenuItemForm) form;

		if (mgr.checkIsExists(menuItemform.getMenuid(), menuItemform.getCode())) {
			JSONUtil.fail(response, "菜单单项未保存：要添加的节点已存在");
		} else {
			menuItemform.setIsLeaf(String.valueOf(StaticVariable.LEAF));
			menuItemform.setIsHide(String.valueOf(StaticVariable.UNHIDE));
			TawSystemPrivMenuItem parent = (TawSystemPrivMenuItem) convert(menuItemform);

			// 添加菜单的批量添加功能,By Steve. 2011,11,Start
			BocoLog.info(this, "Todoo fuck we get batchFlag of "
					+ request.getParameter("batchFlag"));
			String nodeId = parent.getCode();

			// 保存第一级父菜单，该方法不能加入递归中，这是为了在不启用递归模式时使用
			mgr.saveMenuItemAndParentMenu(parent);

			if (Strings.nullToEmpty(request.getParameter("batchFlag")).equals(
					"enable")) {

				// 采用递归算法批量保存节点和子节点
				rescursionTawSystemPrivOperation(nodeId, parent);
			}
			// 添加菜单的批量添加功能,By Steve. 2011,11,End

			JSONUtil.success(response, "菜单保存成功");
		}

	}

	/*
	 * Only for this action. By Steve, 2011-11
	 */
	private void rescursionTawSystemPrivOperation(String nodeId,
			TawSystemPrivMenuItem myParent) throws IllegalAccessException,
			InvocationTargetException {

		List<TawSystemPrivOperation> saveUsageList = new ArrayList();

		// Get manager
		ITawSystemPrivOperationManager iSystemPrivOperationManager = Nop3Utils
				.getService(ITawSystemPrivOperationManager.class,
						"ItawSystemPrivOperationManager");
		ITawSystemPrivMenuItemManager iSystemPrivMenuItemManager = Nop3Utils
				.getService(ITawSystemPrivMenuItemManager.class,
						"ItawSystemPrivMenuItemManager");

		// Get undeleted records
		saveUsageList = iSystemPrivOperationManager.getModules(nodeId, "0");

		if (saveUsageList == null || saveUsageList.size() < 1) {
			return;
		}

		TawSystemPrivMenuItem myChild = new TawSystemPrivMenuItem();

		for (TawSystemPrivOperation tawSystemPrivOperation : saveUsageList) {
			myChild = new TawSystemPrivMenuItem();
			// WARNING: When java.util.Date formate, replace this method.
			BeanUtils.copyProperties(myChild, myParent);
			myChild.setId(null);
			myChild.setCode(tawSystemPrivOperation.getCode());
			myChild.setParentcode(nodeId);
			iSystemPrivMenuItemManager.saveMenuItemAndParentMenu(myChild);
			// This child is next loop's parent.
			rescursionTawSystemPrivOperation(myChild.getCode(), myChild);
		}

	}

	public String xGetNodes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String menuid = StaticMethod
				.null2String(request.getParameter("privid"));
		String parentCode = StaticMethod.null2String(request
				.getParameter("parentcode"));
		TawSystemTreeBo treebo = TawSystemTreeBo.getInstance();
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		boolean isadmin = sessionform.isAdmin();
		JSONArray jsonRoot;
		if (isadmin) {
			jsonRoot = treebo.getPrivMenuItemTree(menuid, parentCode);
		} else {
			jsonRoot = treebo.getUserPrivItemTree(menuid, sessionform
					.getUserid(), parentCode);
		}
		response.setContentType("text/xml;charset=UTF-8");
		response.getWriter().print(jsonRoot.toString());
		return null;
	}

}
