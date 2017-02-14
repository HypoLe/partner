package com.boco.eoms.sheet.base.atom;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.abdera.Abdera;
import org.apache.abdera.factory.Factory;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
import org.apache.abdera.model.Person;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.EOMSAttributes;
import com.boco.eoms.base.util.StaticMethod;
//import com.boco.eoms.base.util.EOMSMgr;
import com.boco.eoms.commons.system.dict.exceptions.DictServiceException;
import com.boco.eoms.commons.system.priv.a_fsh.Ip;
import com.boco.eoms.commons.system.priv.a_fsh.Ip_pair;
import com.boco.eoms.commons.system.priv.a_fsh.Ipconfig;
import com.boco.eoms.commons.system.priv.a_fsh.Ipconfig_creat;
import com.boco.eoms.commons.system.user.model.TawSystemUser;
import com.boco.eoms.commons.system.user.model.TawSystemUser;
import com.boco.eoms.commons.system.user.service.ITawSystemUserManager;
import com.boco.eoms.sheet.base.model.BaseMain;
import com.boco.eoms.sheet.base.model.EomsDoneSheetView;
import com.boco.eoms.sheet.base.model.EomsStartedByMeView;
import com.boco.eoms.sheet.base.model.EomsUndoSheetView;
import com.boco.eoms.sheet.base.task.ITask;
import com.boco.eoms.sheet.base.util.QuerySqlInit;
import com.boco.eoms.commons.system.dict.service.ID2NameService;
import com.boco.eoms.commons.system.dict.service.IDictService;

/**
 * 
 * @author yangliangliang TODO 该类用于传递wap所需数据公共信息
 */

public class AtomUtilForTask {
	/**
	 * 返回任务列表所需信息
	 * 
	 * @param taskListMap
	 * @param request
	 * @return
	 */
	public static Feed makeFeedByTask(HashMap taskListMap,
			HttpServletRequest request) {
		int total = ((Integer) taskListMap.get("taskTotal")).intValue();
		List taskList = (List) taskListMap.get("taskList");

		// 创建ATOM源
		Factory factory = Abdera.getNewFactory();
		Feed feed = factory.newFeed();
		for (int i = taskList.size() - 1; i >= 0; i--) {
			ITask task = (ITask) taskList.get(i);
			Entry entry = feed.insertEntry();
			entry.setContent(request.getRequestURI()
					+ "?method=showAtomDetailPage&taskId=" + task.getId()
					+ "&beanName=" + request.getParameter("beanName") + "");
			entry.setTitle(task.getSheetId());
			entry.setSummary(task.getTitle());
			entry.setPublished(task.getCreateTime());
			entry.setRights("0");// 0:无需截位 1:需要截位
		}
		// 总记录数
		feed.setText("" + total);
		return feed;
	}

	/**
	 * 返回阶段回复所需的任务对象及属性
	 * 
	 * @param taskList
	 * @param request
	 * @return
	 */
	public static Feed showPreOperateList(List taskList,
			HttpServletRequest request) {

		// 创建ATOM源
		Factory factory = Abdera.getNewFactory();
		Feed feed = factory.newFeed();

		for (int i = taskList.size() - 1; i >= 0; i--) {
			ITask task = (ITask) taskList.get(i);
			Entry entry = feed.insertEntry();
			entry.setContent(task.getOperateRoleId()); // 目的任务对象
			entry.setSummary(task.getOperateType()); // 目的任务对象类型
			entry.setTitle(task.getTaskOwner()); // 目的任务对象所有者

		}
		return feed;
	}

	public static Feed showUndoListForPortal(HashMap taskListMap,
			HttpServletRequest request) throws DictServiceException {
		int total = ((Integer) taskListMap.get("taskTotal")).intValue();
		List taskList = (List) taskListMap.get("taskList");

		// 创建ATOM源
		Factory factory = Abdera.getNewFactory();
		Feed feed = factory.newFeed();

		ID2NameService userService = (ID2NameService) ApplicationContextHolder
				.getInstance().getBean("ID2NameGetServiceCatch");
		IDictService service = (IDictService) ApplicationContextHolder
				.getInstance().getBean("DictService");
		int overTimeTaskCount = ((Integer)StaticMethod.nullObject2Integer(taskListMap.get("overTimeTaskCount"))).intValue();
		
		String route = StaticMethod.nullObject2String(request
				.getParameter("route"));
		String url = getUrl(route,request);
		
		for (int i = taskList.size() - 1; i >= 0; i--) {
			ITask task = (ITask) taskList.get(i);
			Entry entry = feed.insertEntry();
			entry.setTitle(task.getSheetId());
			entry.setContent(url + request.getRequestURI()
					+ "?method=showDetailPage&sheetKey=" + task.getSheetKey()
					+ "&taskId=" + task.getId() + "&taskName="
					+ task.getTaskName() + "&operateRoleId="
					+ task.getOperateRoleId() + "&TKID=" + task.getId()
					+ "&piid=" + task.getProcessId() + "&taskStatus="
					+ task.getTaskStatus() + "&preLinkId="
					+ task.getPreLinkId() + "");
			entry.setSummary(task.getTitle());
			entry.setUpdated(task.getCompleteTimeLimit());
			entry.setPublished(task.getCreateTime());
			ITawSystemUserManager userManager = (ITawSystemUserManager) ApplicationContextHolder
					.getInstance().getBean("itawSystemUserManager");
			TawSystemUser user = userManager.getUserByuserid(task
					.getTaskOwner());
			Person person = entry.addAuthor("author");
			String userName = userService.id2Name(task.getTaskOwner(),
					"tawSystemUserDao");
			person.setName(userName);
			String taskStatus = (String) service.itemId2name(
					"dict-sheet-common#taskStatus", task.getTaskStatus());
			entry.setRights(taskStatus);
			entry.setLanguage(task.getTaskDisplayName());
		}
		// 总记录数
		feed.setText("" + total);
		String ip = request.getServerName() + ":"
		+ request.getServerPort();
		feed.setTitle(url + request.getRequestURI()
				+ "?method=showListsendundo");
		feed.setRights(overTimeTaskCount + "");
		return feed;
	}

	public static Feed showUndoAllSheetListForPortal(HashMap taskListMap,
			HttpServletRequest request) throws DictServiceException {
		int total = ((Integer) taskListMap.get("taskTotal")).intValue();
		List taskList = (List) taskListMap.get("taskList");

		// 创建ATOM源
		Factory factory = Abdera.getNewFactory();
		Feed feed = factory.newFeed();
		ID2NameService userService = (ID2NameService) ApplicationContextHolder
				.getInstance().getBean("ID2NameGetServiceCatch");
		IDictService service = (IDictService) ApplicationContextHolder
				.getInstance().getBean("DictService");

		String route = StaticMethod.nullObject2String(request
				.getParameter("route"));
		String url = getUrl(route,request);
		
		// for (int i = 0; i < taskList.size(); i++) {
		for (int i = taskList.size() - 1; i >= 0; i--) {
			EomsUndoSheetView task = (EomsUndoSheetView) taskList.get(i);
			Entry entry = feed.insertEntry();
			entry.setTitle(task.getSheetId());
			String url2 = request.getRequestURI();
			String oldSheetType = url2.substring(url2.lastIndexOf("/") + 1, url2
					.indexOf(".do"));

			String sheetType = task.getSheetType();
			if(url2.indexOf("alllist")!=-1){
				url2 = url2.replaceAll("/alllist/", "/"+sheetType+"/");
			}			
			url2 = url2.replaceAll(oldSheetType, sheetType);
			// 由于新业务试点工单的特殊性，所以必须将之replace掉
			if (sheetType.equals("businesspilot")) {
				url2 = url2.replaceFirst("businesspilot", "business");
			}
			entry.setContent(url + url2
					+ "?method=showDetailPage&sheetKey=" + task.getSheetKey()
					+ "&taskId=" + task.getId() + "&taskName="
					+ task.getTaskName() + "&operateRoleId="
					+ task.getOperateRoleId() + "&TKID=" + task.getId()
					+ "&piid=" + task.getProcessId() + "&taskStatus="
					+ task.getTaskStatus() + "&preLinkId="
					+ task.getPreLinkId() + "");
			entry.setSummary(task.getTitle());
			entry.setUpdated(task.getCompleteTimeLimit());
			entry.setPublished(task.getCreateTime());
			String userName = userService.id2Name(task.getTaskOwner(),
					"tawSystemUserDao");
			Person person = entry.addAuthor("author");
			person.setName(userName);
			entry.setAttributeValue("sheetTypeName", task.getSheetTypeName());
			String taskStatus = (String) service.itemId2name(
					"dict-sheet-common#taskStatus", task.getTaskStatus());
			entry.setRights(taskStatus);
			entry.setLanguage(task.getTaskDisplayName());
		}
		// 总记录数
		feed.setText("" + total);
		feed.setTitle(url + request.getRequestURI()
				+ "?method=getUndoAllLists");
		return feed;
	}

	public static Feed showDoneAllSheetListForPortal(HashMap taskListMap,
			HttpServletRequest request) throws DictServiceException {
		int total = ((Integer) taskListMap.get("taskTotal")).intValue();
		List taskList = (List) taskListMap.get("taskList");

		// 创建ATOM源
		Factory factory = Abdera.getNewFactory();
		Feed feed = factory.newFeed();
		ID2NameService userService = (ID2NameService) ApplicationContextHolder
				.getInstance().getBean("ID2NameGetServiceCatch");
		IDictService service = (IDictService) ApplicationContextHolder
				.getInstance().getBean("DictService");

		String route = StaticMethod.nullObject2String(request
				.getParameter("route"));
		String url = getUrl(route,request);
		
		// for (int i = 0; i < taskList.size(); i++) {
		for (int i = taskList.size() - 1; i >= 0; i--) {
			
			
			
			EomsDoneSheetView task = (EomsDoneSheetView) taskList.get(i);
			Entry entry = feed.insertEntry();
			entry.setTitle(task.getSheetId());
			String url2 = request.getRequestURI();
			String oldSheetType = url2.substring(url2.lastIndexOf("/") + 1, url2
					.indexOf(".do"));
			String sheetType = task.getSheetType();
			if(url2.indexOf("alllist")!=-1){
				url2 = url2.replaceAll("/alllist/", "/"+sheetType+"/");
			}
			url2 = url2.replaceAll(oldSheetType, sheetType);
			// 由于新业务试点工单的特殊性，所以必须将之replace掉
			if (sheetType.equals("businesspilot")) {
				url2 = url2.replaceFirst("businesspilot", "business");
			}
			entry.setContent(url + url2
					+ "?method=showDetailPage&sheetKey=" + task.getSheetKey());
			entry.setSummary(task.getTitle());
			entry.setUpdated(task.getCompleteTimeLimit());
			entry.setPublished(task.getCreateTime());
			String userName = userService.id2Name(task.getTaskOwner(),
					"tawSystemUserDao");
			Person person = entry.addAuthor("author");
			person.setName(userName);
			entry.setAttributeValue("sheetTypeName", task.getSheetTypeName());
			String taskStatus = (String) service.itemId2name(
					"dict-sheet-common#taskStatus", task.getTaskStatus());
			entry.setRights(taskStatus);
			entry.setLanguage(task.getTaskDisplayName());
		}
		// 总记录数
		feed.setText("" + total);
		feed.setTitle(url + request.getRequestURI()
				+ "?method=getDoneAllLists&ifAtom=true");
		return feed;
	}

	
	public static Feed showStartedByMeAllSheetListForPortal(HashMap taskListMap,
			HttpServletRequest request) throws DictServiceException {
		int total = ((Integer) taskListMap.get("taskTotal")).intValue();
		List taskList = (List) taskListMap.get("taskList");

		// 创建ATOM源
		Factory factory = Abdera.getNewFactory();
		Feed feed = factory.newFeed();
		ID2NameService userService = (ID2NameService) ApplicationContextHolder
				.getInstance().getBean("ID2NameGetServiceCatch");
		IDictService service = (IDictService) ApplicationContextHolder
				.getInstance().getBean("DictService");

		String route = StaticMethod.nullObject2String(request
				.getParameter("route"));
		String url = getUrl(route,request);
		
		// for (int i = 0; i < taskList.size(); i++) {
		for (int i = taskList.size() - 1; i >= 0; i--) {
			
			
			
			EomsStartedByMeView task = (EomsStartedByMeView) taskList.get(i);
			Entry entry = feed.insertEntry();
			entry.setTitle(task.getSheetId());
			String url2 = request.getRequestURI();
			String oldSheetType = url2.substring(url2.lastIndexOf("/") + 1, url2
					.indexOf(".do"));
			String sheetType = task.getSheetType();
			if(url2.indexOf("alllist")!=-1){
				url2 = url2.replaceAll("/alllist/", "/"+sheetType+"/");
			}
			url2 = url2.replaceAll(oldSheetType, sheetType);
			// 由于新业务试点工单的特殊性，所以必须将之replace掉
			if (sheetType.equals("businesspilot")) {
				url2 = url2.replaceFirst("businesspilot", "business");
			}
			entry.setContent(url + url2
					+ "?method=showDetailPage&sheetKey=" + task.getId());
			entry.setSummary(task.getTitle());
			entry.setUpdated(task.getSheetCompleteLimit());
			entry.setPublished(task.getSendTime());
			String userName = userService.id2Name(task.getSendUserId(),
					"tawSystemUserDao");
			Person person = entry.addAuthor("author");
			person.setName(userName);
			entry.setAttributeValue("sheetTypeName", task.getSheetTypeName());
			String taskStatus = (String) service.itemId2name(
					"dict-sheet-common#sheetStatus", task.getStatus());
			entry.setRights(taskStatus);
			//entry.setLanguage(task.getSheetAcceptLimit());
		}
		// 总记录数
		feed.setText("" + total);
		feed.setTitle(url + request.getRequestURI()
				+ "?method=getAllListsStartedByme&ifAtom=true");
		return feed;
	}
	
	
	public static Feed showdoneListForPortal(HashMap taskListMap,
			HttpServletRequest request) throws DictServiceException {
		int total = ((Integer) taskListMap.get("taskTotal")).intValue();
		List taskList = (List) taskListMap.get("taskList");
		String beanName = StaticMethod.null2String(request
				.getParameter("beanName"));
		// 创建ATOM源
		Factory factory = Abdera.getNewFactory();
		Feed feed = factory.newFeed();
		IDictService service = (IDictService) ApplicationContextHolder
				.getInstance().getBean("DictService");
		String[] variables = QuerySqlInit.getAllDictItemsName(beanName).split(
				",");
		
		String route = StaticMethod.nullObject2String(request
				.getParameter("route"));
		String url = getUrl(route,request);
		
		for (int i = taskList.size() - 1; i >= 0; i--) {
			try {
				Object[] objectList = (Object[]) taskList.get(i);
				Map ListMap = new HashMap();
				for (int j = 0; j < objectList.length; j++) {
					String variablesKey = variables[j];
					if (variablesKey.indexOf("main.") >= 0
							|| variablesKey.indexOf("task.") >= 0) {
						variablesKey = variablesKey.substring(5);
					}
					ListMap.put(variablesKey, objectList[j]);
				}
				Entry entry = feed.insertEntry();
				entry.setTitle(StaticMethod.nullObject2String(ListMap
						.get("sheetId")));
				entry.setContent(url + request.getRequestURI()
						+ "?method=showDetailPage&sheetKey="
						+ StaticMethod.nullObject2String(ListMap.get("id"))
						+ "");
				entry.setSummary(StaticMethod.nullObject2String(ListMap
						.get("title")));
				entry.setUpdated((Date) ListMap.get("sheetCompleteLimit"));
				entry.setPublished((Date) ListMap.get("SendTime"));
				entry.setEdited((Date) ListMap.get("sheetAcceptLimit"));

				String sheetStatus = (String) service.itemId2name(
						"dict-sheet-common#sheetStatus", StaticMethod
								.nullObject2String(ListMap.get("status")));
				entry.setRights(sheetStatus);
			} catch (Exception e) {
				BaseMain main = (BaseMain) taskList.get(i);			
				Entry entry = feed.insertEntry();
				entry.setTitle(main.getSheetId());
				entry.setContent(url + request.getRequestURI()
						+"?method=showDetailPage&sheetKey=" + main.getId()+"");
				entry.setSummary(main.getTitle());	
			    entry.setUpdated(main.getSheetCompleteLimit());
			    entry.setPublished(main.getSendTime());
			    entry.setEdited(main.getSheetAcceptLimit());
			   
			    String sheetStatus = (String) service.itemId2name("dict-sheet-common#sheetStatus", main.getStatus());
				entry.setRights(sheetStatus);
			}
		}
		// 总记录数
		feed.setText("" + total);
		feed.setTitle( url + request.getRequestURI()
				+ "?method=showListsenddone");
		return feed;
	}
	
	public static String getUrl(String type,HttpServletRequest request) {
		EOMSAttributes attr = (EOMSAttributes) ApplicationContextHolder
				.getInstance().getBean("eomsAttributes");
		String menu_ip = attr.getMenu_ipOpen();
		if (menu_ip.equals("on") && !type.equals("")) {

			Ipconfig ipconfig = Ipconfig_creat.getIpconfig();
			Ip_pair[] ip_pairs = ipconfig.getIp_pair();
			for (int i = 0; i < ip_pairs.length; i++) {
				if (ip_pairs[i].getName().equals("default")) {
					Ip[] ipArr = ip_pairs[i].getIp();
					for (int j = 0; j < ipArr.length; j++) {
						if ("dns".equals(type)
								&& type.equals(ipArr[j].getType())) {
							String url = ipArr[j].getDnsname();
							url = "http://" + url;
							return url;
						}else if (type.equals(ipArr[j].getType())) {
							String url = ipArr[j].getUrl();
							url = "http://" + url;
							return url;
						}
					}
				}
			}
		}else{
			return "http://" + request.getServerName() + ":"
			+ request.getServerPort();
		}
		return null;

	}

}
