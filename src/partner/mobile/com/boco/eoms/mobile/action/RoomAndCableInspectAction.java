package com.boco.eoms.mobile.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.deviceManagement.common.utils.CommonConstants;
import com.boco.eoms.mobile.po.CellItem;
import com.boco.eoms.mobile.po.DianganCabel;
import com.boco.eoms.mobile.po.EquipmentRoomItem;
import com.boco.eoms.mobile.po.GjxCableOpticalCableItem;
import com.boco.eoms.mobile.po.LinkEquipmentItem;
import com.boco.eoms.mobile.po.LinkEquipmentOpticalCableItem;
import com.boco.eoms.mobile.po.LinkEquipmentTerminalItem;
import com.boco.eoms.mobile.po.PortOpticalCableItem;
import com.boco.eoms.mobile.po.WellsOpticalCableItem;
import com.boco.eoms.mobile.service.IRoomAndCableInspectService;
import com.boco.eoms.mobile.util.MobileCommonUtils;
import com.boco.eoms.mobile.util.MobileConstants;

public final class RoomAndCableInspectAction extends BaseAction {
	/**
	 * 查询人井对应的光缆数据
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void getWellsOpticalCableList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out
				.println("----------RoomAndCableInspectAction getWellsOpticalCableList------走了吗");
		// pnr_inspect_plan_res中的id
		String planResId = StaticMethod.nullObject2String(request
				.getParameter("planResId"));

		// 分页
		String pageIndexTemp = StaticMethod.nullObject2String((request
				.getParameter("pageIndex")));
		Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(pageIndexTemp) ? 0 : (Integer
				.parseInt(pageIndexTemp) - 1));
		// 获取每页显示条数
		Integer pageSize = CommonConstants.PAGE_SIZE10;

		System.out.println("------pageIndexTemp=" + pageIndexTemp);

		System.out.println("-------------planResId=" + planResId);
		IRoomAndCableInspectService roomAndCableInspectService = (IRoomAndCableInspectService) ApplicationContextHolder
				.getInstance().getBean("roomAndCableInspectService");
		int count = 0;
		List<WellsOpticalCableItem> list = null;
		try {
			count = roomAndCableInspectService
					.getWellsOpticalCableCount(planResId);

			list = roomAndCableInspectService.getWellsOpticalCableList(
					planResId, pageIndex, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> returnMap = new HashMap<String, Object>();

		returnMap.put("datas", list);
		returnMap.put("count", count);
		JSONArray returnArray = JSONArray.fromObject(returnMap);
		String returnStr = returnArray.toString();
		System.out.println("--------------------1111=" + returnStr);
		MobileCommonUtils.responseWrite(response, returnStr, "UTF-8");

		// System.out.println("------------planResId=" + planResId);
		// String returnStr = "[{\"datas\":
		// [{\"id\":\"C4C4D07FEDB545708C4FEFF23B6D8A4E\",\"cableName\":\"光缆1\",\"pipeHoleInfor\":\"信息1\"},{\"id\":\"2\",\"cableName\":\"光缆2\",\"pipeHoleInfor\":\"信息2\"},{\"id\":\"3\",\"cableName\":\"光缆3\",\"pipeHoleInfor\":\"信息3\"}]}]";
		// MobileCommonUtils.responseWrite(response, returnStr, "UTF-8");
		return;
	}

	/**
	 * 删除人井对应的光缆的信息
	 * 
	 * @author WANGJUN
	 * @title: deleteWellsOpticalCable
	 * @date Sep 29, 2016 4:02:20 PM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 *             void
	 */
	public void deleteWellsOpticalCable(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = StaticMethod.nullObject2String(request
				.getParameter("cableId"));
		System.out.println("-----------id=" + id);
		String operateType = StaticMethod.nullObject2String(request
				.getParameter("operateType"));
		IRoomAndCableInspectService roomAndCableInspectService = (IRoomAndCableInspectService) ApplicationContextHolder
				.getInstance().getBean("roomAndCableInspectService");
		int rows = roomAndCableInspectService.updateWellsOpticalCable(id, "",
				"", operateType);
		String returnJson = MobileConstants.failureStr;
		if (rows > 0) {
			returnJson = MobileConstants.successStr;
		}
		// System.out.println("-----------operateType="+operateType);
		// String returnJson = "[{\"success\":\"true\"}]";
		MobileCommonUtils.responseWrite(response, returnJson, "UTF-8");
		return;
	}

	/**
	 * 修改人井对应的光缆的信息
	 * 
	 * @author WANGJUN
	 * @title: modifyWellsOpticalCable
	 * @date Sep 30, 2016 10:13:06 AM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 *             void
	 */
	public void modifyWellsOpticalCable(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = StaticMethod.nullObject2String(request
				.getParameter("cableId"));
		System.out.println("-----------id=" + id);
		String cableName = StaticMethod.nullObject2String(request
				.getParameter("cableName"));
		System.out.println("-----------cableName=" + cableName);
		String pipeHoleInfor = StaticMethod.nullObject2String(request
				.getParameter("pipeHoleInfor"));
		System.out.println("-----------pipeHoleInfor=" + pipeHoleInfor);
		String operateType = StaticMethod.nullObject2String(request
				.getParameter("operateType"));
		System.out.println("-----------operateType=" + operateType);
		IRoomAndCableInspectService roomAndCableInspectService = (IRoomAndCableInspectService) ApplicationContextHolder
				.getInstance().getBean("roomAndCableInspectService");
		int rows = roomAndCableInspectService.updateWellsOpticalCable(id,
				cableName, pipeHoleInfor, operateType);
		String returnJson = MobileConstants.failureStr;
		if (rows > 0) {
			returnJson = MobileConstants.successStr;
		}
		// System.out.println("-----------operateType="+operateType);
		// String returnJson = "[{\"success\":\"true\"}]";
		MobileCommonUtils.responseWrite(response, returnJson, "UTF-8");
		return;
	}

	/**
	 * 查询光交箱端子占用信息
	 * 
	 * @param planResId
	 * @return
	 */
	public void getGjxPortOpticalCableList(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out
				.println("----------RoomAndCableInspectAction getGjxPortOpticalCableList------查询光交箱端子占用信息");
		// pnr_inspect_plan_res中的id
		String planResId = StaticMethod.nullObject2String(request
				.getParameter("planResId"));

		// 分页   
		String pageIndexTemp = StaticMethod.nullObject2String((request
				.getParameter("pageIndex")));
		Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(pageIndexTemp) ? 0 : (Integer
				.parseInt(pageIndexTemp) - 1));
		// 获取每页显示条数
		Integer pageSize = CommonConstants.PAGE_SIZE10;

		System.out.println("------pageIndexTemp=" + pageIndexTemp);

		System.out.println("-------------planResId=" + planResId);
		IRoomAndCableInspectService roomAndCableInspectService = (IRoomAndCableInspectService) ApplicationContextHolder
				.getInstance().getBean("roomAndCableInspectService");
		int count = 0;
		List<PortOpticalCableItem> list = null;
		try {
			count = roomAndCableInspectService
					.getGjxPortOpticalCableCount(planResId);

			list = roomAndCableInspectService.getGjxPortOpticalCableList(
					planResId, pageIndex, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> returnMap = new HashMap<String, Object>();

		returnMap.put("datas", list);
		returnMap.put("count", count);
		JSONArray returnArray = JSONArray.fromObject(returnMap);
		String returnStr = returnArray.toString();
		System.out.println("--------------------1111=" + returnStr);
		MobileCommonUtils.responseWrite(response, returnStr, "UTF-8");

		// System.out.println("------------planResId=" + planResId);
		// String returnStr = "[{\"datas\":
		// [{\"id\":\"C4C4D07FEDB545708C4FEFF23B6D8A4E\",\"cableName\":\"光缆1\",\"pipeHoleInfor\":\"信息1\"},{\"id\":\"2\",\"cableName\":\"光缆2\",\"pipeHoleInfor\":\"信息2\"},{\"id\":\"3\",\"cableName\":\"光缆3\",\"pipeHoleInfor\":\"信息3\"}]}]";
		// MobileCommonUtils.responseWrite(response, returnStr, "UTF-8");
		return;
	}
	
	/**
	 * 查询光交箱端子占用信息
	 * 
	 * @param planResId
	 * @return
	 */
	public void getGjxTerminalList(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out
				.println("----------RoomAndCableInspectAction getGjxTerminalList------查询光交箱端子");
		// 分页   
		String pageIndexTemp = StaticMethod.nullObject2String((request
				.getParameter("pageIndex")));
		System.out.println("pageIndexTemp="+pageIndexTemp);
		Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(pageIndexTemp) ? 0 : (Integer
				.parseInt(pageIndexTemp) - 1));
		String gjxId = StaticMethod.nullObject2String(request.getParameter("gjxId"));
		System.out.println("gjxId="+gjxId);
		
		String module = StaticMethod.nullObject2String(request.getParameter("module"));
		System.out.println("module="+module);
		// 获取每页显示条数
		Integer pageSize = CommonConstants.PAGE_SIZE10;
		IRoomAndCableInspectService roomAndCableInspectService = (IRoomAndCableInspectService) ApplicationContextHolder.getInstance().getBean("roomAndCableInspectService");
		int count = 0;
		List<PortOpticalCableItem> list = null;
		try {
			count = roomAndCableInspectService.getGjxTerminalCount(gjxId,module);
			list = roomAndCableInspectService.getGjxTerminalList(gjxId,module,pageIndex,pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("datas", list);
		returnMap.put("count", count);
		JSONArray returnArray = JSONArray.fromObject(returnMap);
		String returnStr = returnArray.toString();
		System.out.println("--------------------1111=" + returnStr);
		MobileCommonUtils.responseWrite(response, returnStr, "UTF-8");
		return;
	}
	
	
	

	/**
	 * 对光交箱端子的信息修改、删除
	 * 
	 */
	public void updateGjxPortOpticalCable(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = StaticMethod.nullObject2String(request
				.getParameter("cableId"));
		System.out.println("-----------id=" + id);
		String gjxid = StaticMethod.nullObject2String(request
				.getParameter("gjxid"));
		String module = StaticMethod.nullObject2String(request
				.getParameter("module"));
		String port = StaticMethod.nullObject2String(request
				.getParameter("port"));
		String status = StaticMethod.nullObject2String(request
				.getParameter("status"));
		String userInfo = StaticMethod.nullObject2String(request
				.getParameter("userInfo"));
		String operateType = StaticMethod.nullObject2String(request
				.getParameter("operateType"));
		IRoomAndCableInspectService roomAndCableInspectService = (IRoomAndCableInspectService) ApplicationContextHolder
				.getInstance().getBean("roomAndCableInspectService");
		int rows = roomAndCableInspectService.updateGjxPortOpticalCable(gjxid,module,
				port, status, userInfo, operateType);
		String returnJson = MobileConstants.failureStr;
		if (rows > 0) {
			returnJson = MobileConstants.successStr;
		}
		// System.out.println("-----------operateType="+operateType);
		// String returnJson = "[{\"success\":\"true\"}]";
		MobileCommonUtils.responseWrite(response, returnJson, "UTF-8");
		return;
	}

	/**
	 * 查询光交箱经过缆信息
	 * 
	 * @param planResId
	 * @return
	 */
	public void getGjxCableOpticalCableList(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out
				.println("----------RoomAndCableInspectAction getGjxPortOpticalCableList------查询光交箱经过缆信息");
		// pnr_inspect_plan_res中的id
		String planResId = StaticMethod.nullObject2String(request
				.getParameter("planResId"));

		// 分页   
		String pageIndexTemp = StaticMethod.nullObject2String((request
				.getParameter("pageIndex")));
		Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(pageIndexTemp) ? 0 : (Integer
				.parseInt(pageIndexTemp) - 1));
		// 获取每页显示条数
		Integer pageSize = CommonConstants.PAGE_SIZE10;

		System.out.println("------pageIndexTemp=" + pageIndexTemp);

		System.out.println("-------------planResId=" + planResId);
		IRoomAndCableInspectService roomAndCableInspectService = (IRoomAndCableInspectService) ApplicationContextHolder
				.getInstance().getBean("roomAndCableInspectService");
		int count = 0;
		List<GjxCableOpticalCableItem> list = null;
		try {
			count = roomAndCableInspectService
					.getGjxCableOpticalCableCount(planResId);

			list = roomAndCableInspectService.getGjxCableOpticalCableList(
					planResId, pageIndex, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> returnMap = new HashMap<String, Object>();

		returnMap.put("datas", list);
		returnMap.put("count", count);
		JSONArray returnArray = JSONArray.fromObject(returnMap);
		String returnStr = returnArray.toString();
		System.out.println("--------------------1111=" + returnStr);
		MobileCommonUtils.responseWrite(response, returnStr, "UTF-8");

		// System.out.println("------------planResId=" + planResId);
		// String returnStr = "[{\"datas\":
		// [{\"id\":\"C4C4D07FEDB545708C4FEFF23B6D8A4E\",\"cableName\":\"光缆1\",\"pipeHoleInfor\":\"信息1\"},{\"id\":\"2\",\"cableName\":\"光缆2\",\"pipeHoleInfor\":\"信息2\"},{\"id\":\"3\",\"cableName\":\"光缆3\",\"pipeHoleInfor\":\"信息3\"}]}]";
		// MobileCommonUtils.responseWrite(response, returnStr, "UTF-8");
		return;
	}

	/**
	 * 对光交箱经过缆的信息修改、删除
	 * 
	 */
	public void updateGjxCableOpticalCable(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String gjxid = StaticMethod.nullObject2String(request
				.getParameter("gjxid"));
		String ofiberName = StaticMethod.nullObject2String(request
				.getParameter("ofiberName"));
		System.out.println("-----------ofiberName="+ofiberName);
		String coreSequence = StaticMethod.nullObject2String(request
				.getParameter("coreSequence"));
		System.out.println("-----------coreSequence="+coreSequence);
		String fportNum = StaticMethod.nullObject2String(request.getParameter("fportNum"));
		System.out.println("-----------fportNum="+fportNum);
		String coreStatus = StaticMethod.nullObject2String(request.getParameter("coreStatus"));
		System.out.println("-----------coreStatus="+coreStatus);
		String  resId = StaticMethod.nullObject2String(request
				.getParameter("resId"));
		System.out.println("-----------resId="+resId);
		String operateType = StaticMethod.nullObject2String(request
				.getParameter("operateType"));
		System.out.println("-----------operateType="+operateType);
		IRoomAndCableInspectService roomAndCableInspectService = (IRoomAndCableInspectService) ApplicationContextHolder
				.getInstance().getBean("roomAndCableInspectService");
		if("add".equals(operateType)){
			 boolean existGjxCableOpticalCable = roomAndCableInspectService.isExistGjxCableOpticalCable(resId, ofiberName.trim(), coreSequence.trim());
			if(existGjxCableOpticalCable){
				MobileCommonUtils.responseWrite(response, "[{\"success\":\"exist\"}]", "UTF-8");
				return;
			}
		}
		int rows = roomAndCableInspectService.updateGjxCableOpticalCable(resId,gjxid,ofiberName,
				coreSequence, operateType,fportNum,coreStatus);
		String returnJson = MobileConstants.failureStr;
		if (rows > 0) {
			returnJson = MobileConstants.successStr;
		}
		// System.out.println("-----------operateType="+operateType);
		// String returnJson = "[{\"success\":\"true\"}]";
		MobileCommonUtils.responseWrite(response, returnJson, "UTF-8");
		return;
	}
	
	/**
	 * 查询接入机房-机房设备
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void getAccessRoomEquipmentRoomList(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out
				.println("----------RoomAndCableInspectAction getAccessRoomEquipmentRoomList------走了吗");
		// pnr_inspect_plan_res中的id
		String planResId = StaticMethod.nullObject2String(request.getParameter("planResId"));
		// 分页
		String pageIndexTemp = StaticMethod.nullObject2String((request
				.getParameter("pageIndex")));
		Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(pageIndexTemp) ? 0 : (Integer
				.parseInt(pageIndexTemp) - 1));
		// 获取每页显示条数
		Integer pageSize = CommonConstants.PAGE_SIZE10;

		System.out.println("------pageIndexTemp=" + pageIndexTemp);

		System.out.println("-------------planResId=" + planResId);
		IRoomAndCableInspectService roomAndCableInspectService = (IRoomAndCableInspectService) ApplicationContextHolder
				.getInstance().getBean("roomAndCableInspectService");
		int count = 0;
		List<EquipmentRoomItem> list = null;
		try {
			count = roomAndCableInspectService
					.getAccessRoomEquipmentRoomCount(planResId);

			list = roomAndCableInspectService.getAccessRoomEquipmentRoomList(
					planResId, pageIndex, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> returnMap = new HashMap<String, Object>();

		returnMap.put("datas", list);
		returnMap.put("count", count);
		JSONArray returnArray = JSONArray.fromObject(returnMap);
		String returnStr = returnArray.toString();
		System.out.println("--------------------1111=" + returnStr);
		MobileCommonUtils.responseWrite(response, returnStr, "UTF-8");
		return;
	}
	
	/**
	 *   接入机房 机房设备 新增、修改、删除
	 	 * @author WANGJUN
	 	 * @title: updateAccessRoomEquipmentRoom
	 	 * @date Oct 14, 2016 9:47:30 AM
	 	 * @param mapping
	 	 * @param form
	 	 * @param request
	 	 * @param response
	 	 * @throws ServletException
	 	 * @throws IOException void
	 */
	public void updateAccessRoomEquipmentRoom(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String roomId = StaticMethod.nullObject2String(request
				.getParameter("roomId"));
		System.out.println("-----------roomId="+roomId);
		
		String deviceName = StaticMethod.nullObject2String(request
				.getParameter("deviceName"));
		System.out.println("-----------deviceName="+deviceName);
		
		String networkType = StaticMethod.nullObject2String(request
				.getParameter("networkType"));
		System.out.println("-----------networkType="+networkType);
		
		String deviceVender = StaticMethod.nullObject2String(request
				.getParameter("deviceVender"));
		System.out.println("-----------deviceVender="+deviceVender);
		
		String deviceType = StaticMethod.nullObject2String(request
				.getParameter("deviceType"));
		System.out.println("-----------deviceType="+deviceType);
		
		
		String  resId = StaticMethod.nullObject2String(request
				.getParameter("resId"));
		System.out.println("-----------resId="+resId);
		
		
		String operateType = StaticMethod.nullObject2String(request
				.getParameter("operateType"));
		System.out.println("-----------operateType="+operateType);
		
		IRoomAndCableInspectService roomAndCableInspectService = (IRoomAndCableInspectService) ApplicationContextHolder
				.getInstance().getBean("roomAndCableInspectService");
		
		//判断设备名称是否在系统中已经存在
		if("add".equals(operateType)){
			//int num = roomAndCableInspectService.getEquipmentRoomByDeviceNameCount(resId,deviceName.trim());
			boolean existRoomEquipment = roomAndCableInspectService.isExistRoomEquipment(resId,deviceName.trim());
			if(existRoomEquipment){
				MobileCommonUtils.responseWrite(response, "[{\"success\":\"exist\"}]", "UTF-8");
				return;
			}
		}
		int rows = roomAndCableInspectService.updateAccessRoomEquipmentRoom(resId,roomId,deviceName,networkType,deviceVender,deviceType,operateType);
		
		String returnJson = MobileConstants.failureStr;
		if (rows > 0) {
			returnJson = MobileConstants.successStr;
		}
		MobileCommonUtils.responseWrite(response, returnJson, "UTF-8");
		return;
	}
	
	/**
	 * 查询接入机房-链接设备
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void getRoomShelfList(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out
				.println("----------RoomAndCableInspectAction getRoomShelfList------进入");
		// pnr_inspect_plan_res中的id
		String planResId = StaticMethod.nullObject2String(request.getParameter("planResId"));
		// 分页
		String pageIndexTemp = StaticMethod.nullObject2String((request
				.getParameter("pageIndex")));
		Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(pageIndexTemp) ? 0 : (Integer
				.parseInt(pageIndexTemp) - 1));
		// 获取每页显示条数
		Integer pageSize = CommonConstants.PAGE_SIZE10;

		System.out.println("------pageIndexTemp=" + pageIndexTemp);

		System.out.println("-------------planResId=" + planResId);
		IRoomAndCableInspectService roomAndCableInspectService = (IRoomAndCableInspectService) ApplicationContextHolder
				.getInstance().getBean("roomAndCableInspectService");
		int count = 0;
		List<LinkEquipmentItem> list = null;
		try {
			count = roomAndCableInspectService
					.getShelfCount(planResId);

			list = roomAndCableInspectService.getShelfList(
					planResId, pageIndex, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> returnMap = new HashMap<String, Object>();

		returnMap.put("datas", list);
		returnMap.put("count", count);
		JSONArray returnArray = JSONArray.fromObject(returnMap);
		String returnStr = returnArray.toString();
		System.out.println("--------------------查询接入机房-链接设备 返回json= " + returnStr);
		MobileCommonUtils.responseWrite(response, returnStr, "UTF-8");
		return;
	}
	/**
	 * 查询接入机房-链接设备 端子占用
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void getRoomShelfPortList(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out
				.println("----------RoomAndCableInspectAction getRoomShelfPortList------进入");
		// pnr_inspect_plan_res中的id
		String shelfId = StaticMethod.nullObject2String(request.getParameter("rackId"));
		// 分页
		String pageIndexTemp = StaticMethod.nullObject2String((request
				.getParameter("pageIndex")));
		Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(pageIndexTemp) ? 0 : (Integer
				.parseInt(pageIndexTemp) - 1));
		// 获取每页显示条数
		Integer pageSize = CommonConstants.PAGE_SIZE10;

		System.out.println("------pageIndexTemp=" + pageIndexTemp);

		System.out.println("-------------shelfId=" + shelfId);
		IRoomAndCableInspectService roomAndCableInspectService = (IRoomAndCableInspectService) ApplicationContextHolder
				.getInstance().getBean("roomAndCableInspectService");
		int count = 0;
		List<LinkEquipmentTerminalItem> list = null;
		try {
			count = roomAndCableInspectService.getRoomPortCount(shelfId);
			list = roomAndCableInspectService.getRoomPortList(
					shelfId, pageIndex, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("datas", list);
		returnMap.put("count", count);
		JSONArray returnArray = JSONArray.fromObject(returnMap);
		String returnStr = returnArray.toString();
		System.out.println("--------------------查询接入机房-链接设备 端子占用 返回json= " + returnStr);
		MobileCommonUtils.responseWrite(response, returnStr, "UTF-8");
		return;
	}
	
	/**
	 *   接查询接入机房-链接设备 端子占用   修改、删除
	 	 * @author WANGJUN
	 	 * @title: updateAccessRoomEquipmentRoom
	 	 * @date Oct 14, 2016 9:47:30 AM
	 	 * @param mapping
	 	 * @param form
	 	 * @param request
	 	 * @param response
	 	 * @throws ServletException
	 	 * @throws IOException void
	 */
	public void updateRoomPort(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String shelfId = StaticMethod.nullObject2String(request
				.getParameter("rackId"));
		System.out.println("-----------shelfId="+shelfId);
		
		String port = StaticMethod.nullObject2String(request
				.getParameter("linkTerminalLabel"));
		System.out.println("-----------port="+port);
		
//		String status = StaticMethod.nullObject2String(request
//				.getParameter("status"));
//		System.out.println("-----------status="+status);
		
		String userInfo = StaticMethod.nullObject2String(request
				.getParameter("linkOccupantInfor"));
		System.out.println("-----------userInfo="+userInfo);
		
		String operateType = StaticMethod.nullObject2String(request
				.getParameter("operateType"));
		System.out.println("-----------operateType="+operateType);
		
		String portId = StaticMethod.nullObject2String(request
				.getParameter("portId"));
		System.out.println("-----------portId="+portId);
		
		IRoomAndCableInspectService roomAndCableInspectService = (IRoomAndCableInspectService) ApplicationContextHolder
				.getInstance().getBean("roomAndCableInspectService");
		int rows = roomAndCableInspectService.updateRoomShelfPort( shelfId , port, "", userInfo, operateType,portId);
		
		String returnJson = MobileConstants.failureStr;
		if (rows > 0) {
			returnJson = MobileConstants.successStr;
		}
		MobileCommonUtils.responseWrite(response, returnJson, "UTF-8");
		return;
	}
	
	
	/**
	 * 查询接入机房-链接设备 经过缆
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void getRoomShelfCableList(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out
				.println("----------RoomAndCableInspectAction getRoomShelfCableList------进入");
		// pnr_inspect_plan_res中的id
		String shelfId = StaticMethod.nullObject2String(request.getParameter("rackId"));
		// 分页
		String pageIndexTemp = StaticMethod.nullObject2String((request
				.getParameter("pageIndex")));
		Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(pageIndexTemp) ? 0 : (Integer
				.parseInt(pageIndexTemp) - 1));
		// 获取每页显示条数
		Integer pageSize = CommonConstants.PAGE_SIZE10;

		System.out.println("------pageIndexTemp=" + pageIndexTemp);

		System.out.println("-------------shelfId=" + shelfId);
		IRoomAndCableInspectService roomAndCableInspectService = (IRoomAndCableInspectService) ApplicationContextHolder
				.getInstance().getBean("roomAndCableInspectService");
		int count = 0;
		List<LinkEquipmentOpticalCableItem> list = null;
		try {
			count = roomAndCableInspectService.getRoomCabelCount(shelfId);
			list = roomAndCableInspectService.getRoomCabelList(
					shelfId, pageIndex, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("datas", list);
		returnMap.put("count", count);
		JSONArray returnArray = JSONArray.fromObject(returnMap);
		String returnStr = returnArray.toString();
		System.out.println("--------------------查询接入机房-链接设备 经过缆 返回json= " + returnStr);
		MobileCommonUtils.responseWrite(response, returnStr, "UTF-8");
		return;
	}
	
	/**
	 *   接查询接入机房-链接设备 经过缆   修改、删除 增加
	 	 * @author WANGJUN
	 	 * @title: updateAccessRoomEquipmentRoom
	 	 * @date Oct 14, 2016 9:47:30 AM
	 	 * @param mapping
	 	 * @param form
	 	 * @param request
	 	 * @param response
	 	 * @throws ServletException
	 	 * @throws IOException void
	 */
	public void updateRoomCable(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//String shelfId ,String ofiberId,String ofiberNo,String num,String operateType
		String shelfId = StaticMethod.nullObject2String(request
				.getParameter("rackId"));
		System.out.println("-----------shelfId="+shelfId);
		
		String ofiberId = StaticMethod.nullObject2String(request
				.getParameter("linkCableId"));
		System.out.println("-----------ofiberId="+ofiberId);
		
//		String status = StaticMethod.nullObject2String(request
//				.getParameter("status"));
//		System.out.println("-----------status="+status);
		
		String ofiberNo = StaticMethod.nullObject2String(request
				.getParameter("linkCableName"));
		System.out.println("-----------ofiberNo="+ofiberNo);
		String num = StaticMethod.nullObject2String(request
				.getParameter("linkCoreNumber"));
		System.out.println("-----------num="+num);
		String operateType = StaticMethod.nullObject2String(request
				.getParameter("operateType"));
		System.out.println("-----------operateType="+operateType);
		
		String extrenityNo = StaticMethod.nullObject2String(request
				.getParameter("extrenityNo"));
		System.out.println("-----------extrenityNo="+extrenityNo);
		
		String extrenityXh = StaticMethod.nullObject2String(request
				.getParameter("extrenityXh"));
		System.out.println("-----------extrenityXh="+extrenityXh);
		
		String extrenityStatus = StaticMethod.nullObject2String(request
				.getParameter("extrenityStatus"));
		System.out.println("-----------extrenityStatus="+extrenityStatus);
		
		IRoomAndCableInspectService roomAndCableInspectService = (IRoomAndCableInspectService) ApplicationContextHolder
				.getInstance().getBean("roomAndCableInspectService");
		int rows = roomAndCableInspectService.updateRoomShelfCable( shelfId , ofiberId, ofiberNo, num, operateType,extrenityNo,extrenityXh,extrenityStatus);
		
		String returnJson = MobileConstants.failureStr;
		if (rows > 0) {
			returnJson = MobileConstants.successStr;
		}
		MobileCommonUtils.responseWrite(response, returnJson, "UTF-8");
		return;
	}
	
	
	
	/**
	 * 查询电杆 经过缆
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void getDianganCableList(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out
				.println("----------RoomAndCableInspectAction getDianganCableList------进入");
		// pnr_inspect_plan_res中的id
		String planResId = StaticMethod.nullObject2String(request.getParameter("planResId"));
		// 分页
		String pageIndexTemp = StaticMethod.nullObject2String((request
				.getParameter("pageIndex")));
		Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(pageIndexTemp) ? 0 : (Integer
				.parseInt(pageIndexTemp) - 1));
		// 获取每页显示条数
		Integer pageSize = CommonConstants.PAGE_SIZE10;

		System.out.println("------pageIndexTemp=" + pageIndexTemp);

		System.out.println("-------------planResId=" + planResId);
		IRoomAndCableInspectService roomAndCableInspectService = (IRoomAndCableInspectService) ApplicationContextHolder
				.getInstance().getBean("roomAndCableInspectService");
		int count = 0;
		List<DianganCabel> list = null;
		try {
			count = roomAndCableInspectService.getDianganCabelCount(planResId);
			list = roomAndCableInspectService.getDianganCabelList(
					planResId, pageIndex, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("datas", list);
		returnMap.put("count", count);
		JSONArray returnArray = JSONArray.fromObject(returnMap);
		String returnStr = returnArray.toString();
		System.out.println("--------------------查询电杆 经过缆 返回json= " + returnStr);
		MobileCommonUtils.responseWrite(response, returnStr, "UTF-8");
		return;
	}
	
	/**
	 *   查询电杆 经过缆     修改、删除 增加
	 	 * @author WANGJUN
	 	 * @title: updateAccessRoomEquipmentRoom
	 	 * @date Oct 14, 2016 9:47:30 AM
	 	 * @param mapping
	 	 * @param form
	 	 * @param request
	 	 * @param response
	 	 * @throws ServletException
	 	 * @throws IOException void
	 */
	public void updateDianganCable(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// barId ,String planId,String ofiberNo,String num,String ofiberType,String operateType
		String planResId = StaticMethod.nullObject2String(request
				.getParameter("planResId"));
		System.out.println("-----------planResId="+planResId);
		
		String barId = StaticMethod.nullObject2String(request
				.getParameter("barId"));
		System.out.println("-----------barId="+barId);
		
//		String status = StaticMethod.nullObject2String(request
//				.getParameter("status"));
//		System.out.println("-----------status="+status);
		
		String ofiberNo = StaticMethod.nullObject2String(request
				.getParameter("ofiberNo"));
		System.out.println("-----------ofiberNo="+ofiberNo);
		String num = StaticMethod.nullObject2String(request
				.getParameter("num"));
		System.out.println("-----------num="+num);
		String ofiberType = StaticMethod.nullObject2String(request
				.getParameter("ofiberType"));
		System.out.println("-----------ofiberType="+ofiberType);
		String operateType = StaticMethod.nullObject2String(request
				.getParameter("operateType"));
		System.out.println("-----------operateType="+operateType);
		String id = StaticMethod.nullObject2String(request
				.getParameter("id"));
		System.out.println("-----------id="+id);
		
		IRoomAndCableInspectService roomAndCableInspectService = (IRoomAndCableInspectService) ApplicationContextHolder
				.getInstance().getBean("roomAndCableInspectService");
		int rows = roomAndCableInspectService.updateDianganCable( barId ,  planResId,  ofiberNo,  num,  ofiberType,  operateType,id);
		
		String returnJson = MobileConstants.failureStr;
		if (rows > 0) {
			returnJson = MobileConstants.successStr;
		}
		MobileCommonUtils.responseWrite(response, returnJson, "UTF-8");
		return;
	}
	
	/**
	 * 查询室分 小区
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void getCellList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out
				.println("----------RoomAndCableInspectAction getCellList------走了吗");
		// pnr_inspect_plan_res中的id
		String planResId = StaticMethod.nullObject2String(request
				.getParameter("planResId"));
		//网络类型：2G 3G 4G
		String networkType = StaticMethod.nullObject2String(request
				.getParameter("type"));
		// 分页
		String pageIndexTemp = StaticMethod.nullObject2String((request
				.getParameter("pageIndex")));
		Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(pageIndexTemp) ? 0 : (Integer
				.parseInt(pageIndexTemp) - 1));
		// 获取每页显示条数
		Integer pageSize = CommonConstants.PAGE_SIZE10;

		System.out.println("------pageIndexTemp=" + pageIndexTemp);

		System.out.println("-------------planResId=" + planResId);
		System.out.println("-------------type=" + networkType);
		IRoomAndCableInspectService roomAndCableInspectService = (IRoomAndCableInspectService) ApplicationContextHolder
				.getInstance().getBean("roomAndCableInspectService");
		int count = 0;
		List<CellItem> list = null;
		try {
			count = roomAndCableInspectService
					.getCellCount(planResId,networkType);

			list = roomAndCableInspectService.getCellList(
					planResId,networkType,pageIndex, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> returnMap = new HashMap<String, Object>();

		returnMap.put("datas", list);
		returnMap.put("count", count);
		JSONArray returnArray = JSONArray.fromObject(returnMap);
		String returnStr = returnArray.toString();
		System.out.println("--------------------1111=" + returnStr);
		MobileCommonUtils.responseWrite(response, returnStr, "UTF-8");
		return;
	}
	
	/**
	 * 
	 	 * @author WANGJUN
	 	 * @title: updateCell
	 	 * @date Nov 1, 2016 11:06:20 AM
	 	 * @param mapping
	 	 * @param form
	 	 * @param request
	 	 * @param response
	 	 * @throws ServletException
	 	 * @throws IOException void
	 */
	public void updateCell(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String planResId = StaticMethod.nullObject2String(request.getParameter("planResId"));
		System.out.println("-----------planResId="+planResId);
		
		String siteName = StaticMethod.nullObject2String(request.getParameter("siteName"));//基站名称
		System.out.println("-----------barId="+siteName);
		
		String cellName = StaticMethod.nullObject2String(request.getParameter("cellName"));//小区名称
		System.out.println("-----------cellName="+cellName);
		
		String neCode = StaticMethod.nullObject2String(request.getParameter("neCode"));//网元编码
		System.out.println("-----------neCode="+neCode);
		
		String networkType = StaticMethod.nullObject2String(request.getParameter("type"));//网元类型
		System.out.println("-----------networkType="+networkType);
		
		String isCovered = StaticMethod.nullObject2String(request.getParameter("isCovered"));//是否覆盖
		System.out.println("-----------isCovered="+isCovered);
		
		String cellInfor = StaticMethod.nullObject2String(request.getParameter("cellInfor"));//异常情况
		System.out.println("-----------cellInfor="+cellInfor);
		
		String operateType = StaticMethod.nullObject2String(request.getParameter("operateType"));//操作类型
		System.out.println("-----------operateType="+operateType);
		
		IRoomAndCableInspectService roomAndCableInspectService = (IRoomAndCableInspectService) ApplicationContextHolder
				.getInstance().getBean("roomAndCableInspectService");
		int rows = roomAndCableInspectService.updateCell(planResId,siteName,cellName,neCode,networkType,isCovered,cellInfor,operateType);
		
		String returnJson = MobileConstants.failureStr;
		if (rows > 0) {
			returnJson = MobileConstants.successStr;
		}
		MobileCommonUtils.responseWrite(response, returnJson, "UTF-8");
		return;
	}
}
