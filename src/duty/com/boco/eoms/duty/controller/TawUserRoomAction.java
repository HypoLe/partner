/**
 * @see
 * <p>功能描述：用于机房人员对应等功能的类。</p>
 * @author 赵川
 * @version 2.0
 */

package com.boco.eoms.duty.controller;

import javax.sql.*;
import java.util.*;

import net.sf.json.JSONObject;

import org.apache.commons.logging.LogFactory;

import com.boco.eoms.duty.service.ITawExpertInfoManager;
import com.boco.eoms.duty.service.ITawRmAssignworkManager;
import com.boco.eoms.duty.util.DutyMgrLocator;
import javax.servlet.http.*;
import javax.servlet.*;

import org.apache.struts.action.*;
import org.apache.struts.util.*;

import com.boco.eoms.duty.model.*;
import com.boco.eoms.duty.dao.*;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.common.util.*;
import com.boco.eoms.common.controller.*;
import com.boco.eoms.commons.system.cptroom.bo.TawSystemCptroomBo;
import com.boco.eoms.commons.system.cptroom.model.TawSystemCptroom;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.priv.bo.TawSystemAssignBo;
import com.boco.eoms.commons.system.priv.model.TawSystemPrivRegion;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.commons.system.user.service.ITawSystemUserRefRoleManager;
import com.boco.eoms.commons.ui.util.UIConstants;
import com.boco.eoms.duty.bo.TawExpertRoomBO;
import com.boco.eoms.duty.bo.TawRmAssignworkBO;
import com.boco.eoms.duty.bo.TawUserRoomBO;
import com.boco.eoms.duty.bo.TawRmSysteminfoBO;

// import com.boco.eoms.jbzl.dao.TawApparatusroomDAO;
// import com.boco.eoms.jbzl.model.TawApparatusroom;
// import com.boco.eoms.log.bo.logBO;

public class TawUserRoomAction extends Action {
	private com.boco.eoms.db.util.ConnectionPool ds = com.boco.eoms.db.util.ConnectionPool
			.getInstance();

	private static int PAGE_LENGTH = 20;
	// 整合调整关于国际化
	static {
		ResourceBundle prop = ResourceBundle
				.getBundle("resources.application_zh_CN");
		try {
			PAGE_LENGTH = Integer.parseInt(prop.getString("list.page.length"));
		} catch (Exception e) {
		}
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionForward myforward = null;
		String myaction = mapping.getParameter();

		if (isCancelled(request)) {
			return mapping.findForward("cancel");
		}
		if ("".equalsIgnoreCase(myaction)) {
			myforward = mapping.findForward("failure");
		} else if ("VIEW".equalsIgnoreCase(myaction)) {
			myforward = performView(mapping, form, request, response);
		} else if ("EDIT".equalsIgnoreCase(myaction)) {
			myforward = performEdit(mapping, form, request, response);
		} else if ("ADD".equalsIgnoreCase(myaction)) {
			myforward = performAdd(mapping, form, request, response);
		} else if ("SAVE".equalsIgnoreCase(myaction)) {
			myforward = performSave(mapping, form, request, response);
		} else if ("SAVENEW".equalsIgnoreCase(myaction)) {
			myforward = performSaveNEW(mapping, form, request, response);
		} else if ("REMOVE".equalsIgnoreCase(myaction)) {
			myforward = performRemove(mapping, form, request, response);
		} else if ("TRASH".equalsIgnoreCase(myaction)) {
			myforward = performTrash(mapping, form, request, response);
		} else if ("LIST".equalsIgnoreCase(myaction)) {
			myforward = performList(mapping, form, request, response);
		} else if ("NEW".equalsIgnoreCase(myaction)) {
			myforward = performNew(mapping, form, request, response);
		} else if ("EXPERTNEW".equalsIgnoreCase(myaction)) {
			myforward = performExpertNew(mapping, form, request, response);
		} else if ("SAVEEXPERTNEW".equalsIgnoreCase(myaction)) {
			myforward = performSaveExpertNew(mapping, form, request, response);
		}else {
			myforward = mapping.findForward("failure");
		}
		return myforward;
	}

	/**
	 * @see 显示机房人员对应关系
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward performNew(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		// edit by wangheqi 2.7 to 3.5
		TawSystemSessionForm saveSessionBeanForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		/*
		 * SaveSessionBeanForm saveSessionBeanForm = (SaveSessionBeanForm)
		 * httpServletRequest.getSession().getAttribute("SaveSessionBeanForm");
		 */
		TawUserRoomBO tawUserRoomBO = null;
		TawUserRoomDAO tawUserRoomDAO = null;
		Vector DeptUser = null;
		Vector RoomUserTrue = null;
		Vector RoomUserFalse = null;
		// edit by wangheqi
		TawSystemCptroomBo cptroomBO = TawSystemCptroomBo.getInstance();
		TawSystemCptroom tawApparatusroom = null;

		// TawApparatusroomDAO tawApparatusroomDAO=null;
		// TawApparatusroom tawApparatusroom=null;
		String room_name = null;

		// saveSessionBeanForm =
		// (SaveSessionBeanForm)request.getSession().getAttribute("SaveSessionBeanForm");
		if (saveSessionBeanForm == null) {
			return mapping.findForward("timeout");
		}

		try {
			tawUserRoomBO = new TawUserRoomBO(ds);
			tawUserRoomDAO = new TawUserRoomDAO(ds);
			// 取得全部人员列表，参数dept_id = -1
			int dept_id = -1;
			DeptUser = tawUserRoomBO.getDeptUser(dept_id);
			request.setAttribute("DeptUser", DeptUser);
			// 传值roomId
			int room_id = Integer.parseInt(request.getParameter("roomId"));
			request.setAttribute("roomId", request.getParameter("roomId"));
			// 本机房参加人员
			RoomUserTrue = tawUserRoomDAO.getRoomUser(room_id, 0);
			request.setAttribute("RoomUserTrue", RoomUserTrue);
			// 本机房不参加人员
			RoomUserFalse = tawUserRoomDAO.getRoomUser(room_id, 1);
			request.setAttribute("RoomUserFalse", RoomUserFalse);
			// 机房人员信息
			// tawApparatusroomDAO = new TawApparatusroomDAO(ds);
			tawApparatusroom = cptroomBO.getTawSystemCptroomById(new Integer(
					room_id), 0);
			// 机房名称
			room_name = tawApparatusroom.getRoomname();
			request.setAttribute("ROOMNAME", room_name);
			// 特殊排班处理com.boco.eoms.duty.util.DutyMgrLocator
			// if(StaticMethod.getNodeName("DUTY.OtherAssign").equals(String.valueOf(room_id)))
			if (DutyMgrLocator.getAttributes().getDutyOtherAssign().equals(
					String.valueOf(room_id))) {
				TawRmSysteminfoDAO tawRmSysteminfoDAO = new TawRmSysteminfoDAO(
						ds);
				TawRmSysteminfo tawRmSysteminfo = tawRmSysteminfoDAO
						.retrieve(room_id + "");
				request.setAttribute("UserNum", String.valueOf(tawRmSysteminfo
						.getMaxdutynum()));
			}

			// 取当前人所属的部门域
			//Vector deptPriv = new Vector();
			String deptStr = "";
			deptStr = saveSessionBeanForm.getDeptid();
			
			/*TawSystemDept tawSystemDept = null;
			TawSystemAssignBo privBO = TawSystemAssignBo.getInstance();
			ITawSystemDeptManager mgr = (ITawSystemDeptManager) ApplicationContextHolder
					.getInstance().getBean("ItawSystemDeptManager");
			List listFdept = new ArrayList();
			TawRmAssignworkBO tawRmAssignworkBO = null;
			TawSystemPrivRegion tawSystemPrivRegion = null;*/
			if (saveSessionBeanForm.getUserid().equals(StaticVariable.ADMIN)) {
				deptStr = "-1";
			} 
			String deptId = request.getParameter("deptId"); 
			String deptName = request.getParameter("deptName"); 
			if(deptId!=null&&!deptId.equals("0")&&!"".equals(deptId)){
				deptStr = deptId;
				request.setAttribute("deptName", deptName);
			}
			/*else {

				deptPriv = StaticMethod
						.list2vector(privBO
								.getPermissions(
										saveSessionBeanForm.getUserid(),
										com.boco.eoms.base.util.StaticVariable.PRIV_ASSIGNTYPE_USER,
										com.boco.eoms.base.util.StaticVariable.PRIV_TYPE_REGION_DEPT));

				if (deptPriv.size() > 0) {
					for (int i = 0; i < deptPriv.size(); i++) {

						tawSystemPrivRegion = (TawSystemPrivRegion) deptPriv
								.elementAt(i);
						if (!saveSessionBeanForm.getDeptid().equals(
								tawSystemPrivRegion.getRegionid())) {

							deptStr = deptStr + ","
									+ tawSystemPrivRegion.getRegionid();

						}

					}

				}
				listFdept = mgr.getFdeptInfoduty(deptStr, "0");
				for (Iterator it = listFdept.iterator(); it.hasNext();) {
					tawSystemDept = (TawSystemDept) it.next();
					deptStr = deptStr + "," + tawSystemDept.getDeptId();
				}
			}*/
			DeptUser = tawUserRoomBO.getDeptUser(deptStr);
			
			request.setAttribute("DeptUser", DeptUser);
			request.setAttribute("deptStr", deptStr);
		} catch (Exception e) {
			generalError(request, e);
			return mapping.findForward("failure");
		} finally {
			saveSessionBeanForm = null;
			tawUserRoomBO = null;
			tawUserRoomDAO = null;
			DeptUser = null;
			RoomUserTrue = null;
			RoomUserFalse = null;
			cptroomBO = null;
			// tawApparatusroomDAO=null;
			tawApparatusroom = null;
			room_name = null;
		}
		return mapping.findForward("success");
	}

	/**
	 * @see 显示机房专家人员对应关系
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward performExpertNew(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		TawSystemSessionForm saveSessionBeanForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		TawExpertRoomBO tawExpertRoomBO = null;
		TawExpertRoomDAO tawExpertRoomDAO = null;
		Vector DeptUser = new Vector();
		Vector RoomUserTrue = null;
		TawSystemCptroomBo cptroomBO = TawSystemCptroomBo.getInstance();
		TawSystemCptroom tawApparatusroom = null;

		String room_name = null;

		if (saveSessionBeanForm == null) {
			return mapping.findForward("timeout");
		}

		try {
			tawExpertRoomBO = new TawExpertRoomBO(ds);
			tawExpertRoomDAO = new TawExpertRoomDAO(ds);
			// 取得全部人员列表，参数dept_id = -1
//			int dept_id = -1;
//			DeptUser = tawExpertRoomBO.getDeptUser(dept_id);
//			request.setAttribute("DeptUser", DeptUser);
			
			List expertlist = new ArrayList();
			ITawExpertInfoManager mgr = (ITawExpertInfoManager)ApplicationContextHolder.getInstance().getBean("ITawExpertInfoManager");
			expertlist = mgr.getTawExpertInfosByCondition("");
			for(int i=0;i<expertlist.size();i++){
				TawExpertInfo tawExpertInfo = new TawExpertInfo();
				tawExpertInfo = (TawExpertInfo)expertlist.get(i);
				String expertId = tawExpertInfo.getExpertId();
				String expertName = tawExpertInfo.getExpertName();
				
				DeptUser.add(expertId);
				DeptUser.add(expertName);
				
			}
			request.setAttribute("DeptUser", DeptUser);
			
			// 传值roomId
			int room_id = Integer.parseInt(request.getParameter("roomId"));
			request.setAttribute("roomId", request.getParameter("roomId"));
			// 本机房参加人员
			RoomUserTrue = tawExpertRoomDAO.getRoomExpert(room_id);
			request.setAttribute("RoomUserTrue", RoomUserTrue);
			// 机房人员信息
			tawApparatusroom = cptroomBO.getTawSystemCptroomById(new Integer(
					room_id), 0);
			// 机房名称
			room_name = tawApparatusroom.getRoomname();
			request.setAttribute("ROOMNAME", room_name);
			

			// 取当前人所属的部门域
			String deptStr = "";
			deptStr = saveSessionBeanForm.getDeptid();
			if (saveSessionBeanForm.getUserid().equals(StaticVariable.ADMIN)) {
				deptStr = "-1";
			} 
			DeptUser = tawExpertRoomBO.getDeptUser(deptStr);
			request.setAttribute("DeptUser", DeptUser);
			request.setAttribute("deptStr", deptStr);
		} catch (Exception e) {
			generalError(request, e);
			return mapping.findForward("failure");
		} finally {
			saveSessionBeanForm = null;
			tawExpertRoomBO = null;
			tawExpertRoomDAO = null;
			DeptUser = null;
			RoomUserTrue = null;
			cptroomBO = null;
			// tawApparatusroomDAO=null;
			tawApparatusroom = null;
			room_name = null;
		}
		return mapping.findForward("success");
	}
	/**
	 * @see 保存机房专家人员对应关系
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward performSaveExpertNew(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		TawSystemSessionForm saveSessionBeanForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String str_user_true = null;

		TawExpertRoomBO tawExpertRoomBO = null;
		if (saveSessionBeanForm == null) {
			return mapping.findForward("timeout");
		}

		// 取得机房id
		int roomId = Integer.parseInt(request.getParameter("roomId"));
		request.setAttribute("roomId", String.valueOf(request
				.getParameter("roomId")));
		// 取得本机房参加人员
		str_user_true = request.getParameter("user_true");
		try {
			tawExpertRoomBO = new TawExpertRoomBO(ds);
			// 更新机房人员信息
			tawExpertRoomBO.updateUserRoom(roomId, str_user_true);
		} catch (Exception e) {
			return mapping.findForward("failure");
		} finally {
			saveSessionBeanForm = null;
			str_user_true = null;
			tawExpertRoomBO = null;
			// logbo=null;
		}
		request.setAttribute("SAVEFLAG", "true");
		return mapping.findForward("success");
	}
	/**
	 * @see 机房人员对应关系列表
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward performList(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		return mapping.findForward("success");
	}

	/**
	 * @see 机房人员对应关系查看
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward performView(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {

		TawUserRoomDAO tawUserRoomDAO = null;
		String userId = null;
		TawUserRoom tawUserRoom = null;

		TawUserRoomForm form = (TawUserRoomForm) actionForm;
		try {

			tawUserRoomDAO = new TawUserRoomDAO(ds);

			userId = request.getParameter("userId");
			int roomId = Integer.parseInt(request.getParameter("roomId"));
			tawUserRoom = tawUserRoomDAO.retrieve(userId, roomId);
			if (tawUserRoom == null) {
				ActionErrors aes = new ActionErrors();
				aes.add(aes.GLOBAL_ERROR, new ActionError(
						"error.object.notfound", "TawUserRoom"));
				saveErrors(request, aes);
			} else {
				org.apache.commons.beanutils.BeanUtils.populate(form,
						org.apache.commons.beanutils.BeanUtils
								.describe(tawUserRoom));
			}
		} catch (Exception e) {
			generalError(request, e);
			return mapping.findForward("failure");
		} finally {
			tawUserRoomDAO = null;
			userId = null;
			tawUserRoom = null;
		}
		return mapping.findForward("success");
	}

	/**
	 * @see 保存机房人员对应关系
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward performSaveNEW(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		// edit by wangheqi 2.7 to 3.5
		TawSystemSessionForm saveSessionBeanForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		/*
		 * SaveSessionBeanForm saveSessionBeanForm = (SaveSessionBeanForm)
		 * httpServletRequest.getSession().getAttribute("SaveSessionBeanForm");
		 */
		String str_user_true = null;
		String str_user_false = null;

		TawUserRoomBO tawUserRoomBO = null;
		// logBO logbo=null;

		// saveSessionBeanForm =
		// (SaveSessionBeanForm)request.getSession().getAttribute("SaveSessionBeanForm");
		if (saveSessionBeanForm == null) {
			return mapping.findForward("timeout");
		}

		// 取得机房id
		int roomId = Integer.parseInt(request.getParameter("roomId"));
		request.setAttribute("roomId", String.valueOf(request
				.getParameter("roomId")));
		// 取得本机房参加人员，本机房不参加人员
		str_user_true = request.getParameter("user_true");
		str_user_false = request.getParameter("user_false");

		try {
			tawUserRoomBO = new TawUserRoomBO(ds);
			// 更新机房人员信息
			tawUserRoomBO.updateUserRoom(roomId, str_user_true, str_user_false);
			// logbo = new logBO(ds);
			// boolean bool =
			// logbo.insertLogToDB(saveSessionBeanForm.getWrf_UserID(),"机房配置用户",StaticVariable.OPER,request.getRemoteAddr());
		} catch (Exception e) {
			return mapping.findForward("failure");
		} finally {
			saveSessionBeanForm = null;
			str_user_true = null;
			str_user_false = null;
			tawUserRoomBO = null;
			// logbo=null;
		}
		request.setAttribute("SAVEFLAG", "true");
		return mapping.findForward("success");
	}

	/**
	 * @see 保存机房人员对应关系
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward performSave(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		return mapping.findForward("success");
	}

	/**
	 * @see 编辑机房人员对应关系
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward performEdit(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		TawUserRoomForm form = (TawUserRoomForm) actionForm;
		return mapping.findForward("success");
	}

	/**
	 * @see 新增机房人员对应关系
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward performAdd(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		TawUserRoomForm form = (TawUserRoomForm) actionForm;
		form.setStrutsAction(TawUserRoomForm.ADD);
		return mapping.findForward("success");
	}

	/**
	 * @see 删除机房人员对应关系
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward performRemove(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		return performView(mapping, actionForm, request, response);
	}

	/**
	 * @see 删除机房人员对应关系
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward performTrash(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		TawUserRoomForm form = (TawUserRoomForm) actionForm;
		try {

			TawUserRoomDAO tawUserRoomDAO = new TawUserRoomDAO(ds);

			String userId = request.getParameter("userId");
			int roomId = Integer.parseInt(request.getParameter("roomId"));
			tawUserRoomDAO.delete(userId, roomId);
		} catch (Exception e) {
			generalError(request, e);
			return mapping.findForward("failure");
		}
		return mapping.findForward("success");
	}

	private void sqlDuplicateError(HttpServletRequest request, String objName) {
		ActionErrors aes = new ActionErrors();
		aes.add(aes.GLOBAL_ERROR, new ActionError("errors.database.duplicate",
				objName));
		saveErrors(request, aes);
	}

	private void generalError(HttpServletRequest request, Exception e) {
		ActionErrors aes = new ActionErrors();
		aes.add(aes.GLOBAL_ERROR, new ActionError("error.general", e
				.getMessage()));
		saveErrors(request, aes);
		e.printStackTrace();
	}
}
