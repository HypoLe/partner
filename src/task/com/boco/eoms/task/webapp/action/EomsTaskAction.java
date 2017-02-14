package com.boco.eoms.task.webapp.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.dict.util.DictMgrLocator;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.task.model.Eoms_Task_User;
import com.boco.eoms.task.mgr.IEomsTask;

public final class EomsTaskAction extends BaseAction {

	/**
	 * 初始化录入页面
	 */
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession();

		TawSystemSessionForm usermes = (TawSystemSessionForm) this
				.getUser(session);
		JSONArray jsonRoot = new JSONArray();

		request.setAttribute("usermes", usermes); // 设置用户信息

		IEomsTask eomstask = (IEomsTask) getBean("IEomsTaskManager");

		// 人员树图默认展示数据---auditUser
		String auditUser = "[]";
		List list = eomstask.getEomsTaskUser(usermes.getUserid());
		Eoms_Task_User eomsTaskUser = null;
		if (!list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				eomsTaskUser = (Eoms_Task_User) list.get(i);
				JSONObject jitem = new JSONObject();
				jitem.put("id", eomsTaskUser.getUserid());
				jitem.put("name", eomsTaskUser.getUsername());
				jitem.put("nodeType", "user");
				jsonRoot.put(jitem);
			}
			request.setAttribute("auditUser", jsonRoot.toString());
		} else {
			request.setAttribute("auditUser", auditUser);
		}

		request.setAttribute("userlist", list);

		return mapping.findForward("init");

	}

	/**
	 * 保存数据
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			String showAuditUser = request.getParameter("showAuditUser");
			String usid = request.getParameter("usid"); // 用户userid
			String userid = request.getParameter("userid"); // 用户id
			String username = request.getParameter("username"); // 用户名称

			JSONArray jsonArray = JSONArray.fromString(showAuditUser); // 解析json对象

			List listarr = new ArrayList();

			List list;

			for (int i = 0; i < jsonArray.length(); i++) {

				JSONObject objs = (JSONObject) jsonArray.get(i); // 得到一个用户对象
				Eoms_Task_User eomstaskuser = new Eoms_Task_User();
				eomstaskuser.setManagerid(usid);
				eomstaskuser.setManagername(username);
				eomstaskuser.setUserid(objs.get("id").toString());
				eomstaskuser.setUsername(objs.get("name").toString());
				listarr.add(eomstaskuser);
			}

			IEomsTask eomstask = (IEomsTask) getBean("IEomsTaskManager");

			eomstask.savaEomsTaskUser(listarr, usid);

			list = eomstask.getEomsTaskUser(usid);

			request.setAttribute("userlist", list);
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mapping.findForward("fail");
		}

		return init(mapping, form, request, response);
	}
}