package com.boco.eoms.sheet.interfaceBase.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.system.user.model.TawSystemUser;
import com.boco.eoms.commons.system.user.service.ITawSystemUserManager;
import com.boco.eoms.sheet.interfaceBase.service.IInterfaceServiceManage;
import com.boco.eoms.sheet.base.model.BaseMain;
import com.boco.eoms.sheet.base.service.ILinkService;
import com.boco.eoms.sheet.base.service.IMainService;
import com.boco.eoms.sheet.base.service.ITaskService;
import com.boco.eoms.sheet.base.util.Constants;
import com.boco.eoms.sheet.base.util.UUIDHexGenerator;
import com.boco.eoms.sheet.interfaceBase.util.InterfaceConstants;
import com.boco.eoms.sheet.interfaceBase.util.InterfaceUtilProperties;

public abstract class InterfaceServiceManageAbstract implements IInterfaceServiceManage{

	/**
	 * 归档
	 */
	public String checkinWorkSheet(HashMap interfaceMap, List attach) throws Exception{
		Map map = this.initMap(interfaceMap,attach,InterfaceConstants.WORKSHEET_HOLD);
		
		if(map.get("phaseId")==null)
			throw new Exception("phaseId为空");
		
		IMainService iMainService = (IMainService)ApplicationContextHolder.getInstance().getBean(this.getMainBeanId());
	
		map = setBaseMap(map);
		String sheetNo = StaticMethod.nullObject2String(map.get("serialNo"));
		System.out.println("serialNo="+sheetNo);
		List list = iMainService.getMainListByParentSheetId(sheetNo);
		if(list.size()>0){
			BaseMain main = (BaseMain)list.get(0);
			map.put("id", main.getId());
			map.put("operateUserId", main.getSendUserId());
			map.put("endUserId", main.getSendUserId());   
			map.put("endDeptId", main.getSendDeptId());
			map.put("endRoleId", main.getSendRoleId());
			map.put("status", Constants.SHEET_HOLD);

			
			return this.dealSheet(main,map, attach);
		}else{
			throw new Exception("没找到sheetNo＝"+sheetNo+"对应的工单");
		}
	}

	/**
	 * 派发工单
	 */
	public String newWorkSheet(HashMap interfaceMap, List attach) throws Exception{
		HashMap columnMap = new HashMap();
		columnMap.put("selfSheet", this.setNewInterfacePara());

		Map map = this.initMap(interfaceMap,attach,InterfaceConstants.WORKSHEET_NEW);
		map = setBaseMap(map);
		System.out.println("setBaseMap complete");
		map.put("correlationKey",new String[]{UUIDHexGenerator.getInstance().getID()});
		
//		WPSEngineServiceMethod sm = new WPSEngineServiceMethod();
		String processTemplateName = this.getProcessTemplateName();
		String operateName = this.getOperateName();
		String userId = this.getSendUser(map);
		if(userId==null || userId.equals(""))
			throw new Exception("userId is null");
		System.out.println("userId="+userId);
		map.put("sendUserId", userId);
		map.put("operateUserId", userId);
		map.put("operateRoleId", map.get("sendRoleId"));
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date  = new Date();
		map.put("sendTime",new String[]{dateFormat.format(date)});
		
		ITawSystemUserManager userMgr = 
			(ITawSystemUserManager)ApplicationContextHolder.getInstance().getBean("itawSystemUserManager");
		TawSystemUser user = userMgr.getUserByuserid(userId);
		if(user!=null){
			map.put("sendDeptId", user.getDeptid());
			map.put("sendContact", user.getMobile());
		
			map.put("operateDeptId", user.getDeptid());
			map.put("operaterContact", user.getMobile());
		}
		
//		System.out.println("prepareMap start");
//		HashMap WpsMap = sm.prepareMap(map,columnMap);
//		System.out.println("start addpara");
//		WpsMap = this.addPara(WpsMap);
//		if(WpsMap.get("corrKey")!=null)
//			System.out.println("add corrKey:"+WpsMap.get("corrKey").toString());
//		Map mainMap = sm.sendNewSheet(WpsMap, userId, processTemplateName, operateName);
//		System.out.println("sendNewSheet over");
		return "";
	}

	

	/**
	 * 重派
	 */
	public String renewWorkSheet(HashMap interfaceMap, List attach) throws Exception{
		IMainService iMainService = (IMainService)ApplicationContextHolder.getInstance().getBean(this.getMainBeanId());
		
		Map map = this.initMap(interfaceMap,attach,InterfaceConstants.WORKSHEET_RENEW);
		map = setBaseMap(map);
		
		if(map.get("phaseId")==null)
			throw new Exception("phaseId为空");
		
		String sheetNo = StaticMethod.nullObject2String(map.get("serialNo"));
		
		List list = iMainService.getMainListByParentSheetId(sheetNo);
		if(list.size()>0){
			BaseMain main = (BaseMain)list.get(0);
			map.put("id", main.getId());
			map.put("operateRoleId", main.getSendRoleId());
			System.out.println("renew sendRoleId="+main.getSendRoleId());
			System.out.println("renew operateRoleId="+map.get("operateRoleId"));
			return this.dealSheet(main,map, attach);
		}else
			throw new Exception("没找到sheetNo＝"+sheetNo+"对应的工单");
	}

	/**
	 * 阶段通知
	 * @param interfaceMap
	 * @param attach
	 * @return
	 */
	public String suggestWorkSheet(HashMap interfaceMap, List attach) throws Exception{
		Map map = this.initMap(interfaceMap,attach,InterfaceConstants.WORKSHEET_SUGGEST);
		map = setBaseMap(map);		
		map.put("operateType", "-11");	//阶段通知标识
		
		IMainService iMainService = (IMainService)ApplicationContextHolder.getInstance().getBean(this.getMainBeanId());
		ILinkService iLinkService = (ILinkService)ApplicationContextHolder.getInstance().getBean(this.getLinkBeanId());
		ITaskService iTaskService = (ITaskService)ApplicationContextHolder.getInstance().getBean(this.getTaskBeanId());
		
		HashMap columnMap = new HashMap();
		HashMap sheetMap = new HashMap();
			
		String sheetId = StaticMethod.nullObject2String(map.get("serialNo"));
		if(sheetId==null || sheetId.equals(""))
			throw new Exception("sheetId为空");
		BaseMain main = null;
		List list = iMainService.getMainListByParentSheetId(sheetId);
		if(list.size()>0)
			main = (BaseMain)list.get(0);
		if(main==null)
			throw new Exception("没找到sheetNo＝"+sheetId+"对应的工单");	
		
		map.put("title", main.getTitle());
		map.put("sheetId", main.getSheetId());
		
		sheetMap.put("main", main);
		sheetMap.put("link", iLinkService.getLinkObject().getClass().newInstance());
		sheetMap.put("operate", this.getPageColumnName()); 
		columnMap.put("selfSheet", sheetMap);
		map.put("operateRoleId", main.getSendRoleId());
		
		String operateUserId = this.getSendUser(map);
//
//		WPSEngineServiceMethod sm = new WPSEngineServiceMethod();
//		
//		List taskList = iTaskService.getCurrentUndoTask(main.getId());
//		for(int i=0;taskList!=null&&i<taskList.size();i++){			
//			ITask task = (ITask) taskList.get(i);
//			String roleId = task.getTaskOwner();
//			map = sm.setAcceptRole(roleId, map);
//			sm.performProcessEvent("",iMainService,iLinkService,iTaskService, sm.prepareMap(map, columnMap));
//		}
		
		return main.getSheetId();
	}

	/**
	 * 退回
	 * @param interfaceMap
	 * @param attach
	 * @return
	 */
	public String untreadWorkSheet(HashMap interfaceMap, List attach) throws Exception{
		IMainService iMainService = (IMainService)ApplicationContextHolder.getInstance().getBean(this.getMainBeanId());
		
		Map map = this.initMap(interfaceMap,attach,InterfaceConstants.WORKSHEET_UNTREAD);
		map = setBaseMap(map);
		String sheetNo = StaticMethod.nullObject2String(map.get("serialNo"));
		BaseMain main = null;
		List list = iMainService.getMainListByParentSheetId(sheetNo);
		if(list.size()>0)
			main = (BaseMain)list.get(0);
		if(main==null)
			throw new Exception("没找到sheetNo＝"+sheetNo+"对应的工单");
		map.put("id", main.getId());
		map.put("operateRoleId", main.getSendRoleId());
		
		return this.dealSheet(main,map, attach);
	}
	
	/**
	 * 取消工单
	 * @throws Exception 
	 */	
	public String cancelWorkSheet(HashMap interfaceMap) throws Exception{
		IMainService iMainService = (IMainService)ApplicationContextHolder.getInstance().getBean(this.getMainBeanId());
		List attach = null;
		Map map = this.initMap(interfaceMap,attach,InterfaceConstants.WORKSHEET_CANCEL);
		map = setBaseMap(map);
		String sheetNo = StaticMethod.nullObject2String(map.get("serialNo"));
		List list = iMainService.getMainListByParentSheetId(sheetNo);
		if(list.size()>0){
			BaseMain main = (BaseMain)list.get(0);
			map.put("id", main.getId());
			map.put("status", Constants.SHEET_CANCEL);
			map.put("operateRoleId", main.getSendRoleId());
			return this.dealSheet(main,map, attach);			
		}else
			throw new Exception("没找到sheetNo＝"+sheetNo+"对应的工单");
		
	}
	
	public String getPageColumnName(){
		return Constants.pageColumnName;
	}
	
	public Map loadDefaultMap(Map interfaceMap,String filePath,String nodePath) throws Exception{
		InterfaceUtilProperties properties = new InterfaceUtilProperties();
		filePath = StaticMethod.getFilePathForUrl("classpath:"+filePath);
		return properties.getMapFromXml(interfaceMap, filePath, nodePath);
	}
	public Map setBaseMap(Map map){
		try{
//			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			Date date  = new Date();
			
			IMainService iMainService = (IMainService)ApplicationContextHolder.getInstance().getBean(this.getMainBeanId());
			ILinkService iLinkService = (ILinkService)ApplicationContextHolder.getInstance().getBean(this.getLinkBeanId());
			
			String mainBeanId = this.getMainBeanId(); 
			map.put("beanId",new String[]{mainBeanId});
			System.out.println("mainClassName="+iMainService.getMainObject().getClass().getName());
			System.out.println("linkClassName="+iLinkService.getLinkObject().getClass().getName());
			map.put("mainClassName",new String[]{iMainService.getMainObject().getClass().getName()});
			map.put("linkClassName",new String[]{iLinkService.getLinkObject().getClass().getName()});
			
//			if(map.get("sheetCompleteLimit")==null)
//				map.put("sheetCompleteLimit",new String[]{dateFormat.format(date)});
//			if(map.get("taskCompleteTime")==null)
//				map.put("taskCompleteTime",new String[]{dateFormat.format(date)});

		}catch(Exception err){
			err.printStackTrace();
		} 
		return map;
	}
	
	public String dealSheet(BaseMain main,Map map, List attach) throws Exception{

		System.out.println("start dealSheet");
		HashMap columnMap = new HashMap();
		HashMap sheetMap = new HashMap();

//		IMainService iMainService = (IMainService)ApplicationContextHolder.getInstance().getBean(this.getMainBeanId());
		ILinkService iLinkService = (ILinkService)ApplicationContextHolder.getInstance().getBean(this.getLinkBeanId());
		ITaskService iTaskService = (ITaskService)ApplicationContextHolder.getInstance().getBean(this.getTaskBeanId());
		
//		String serialNo = StaticMethod.nullObject2String(map.get("serialNo"));
//		if(serialNo==null || serialNo.equals(""))
//			throw new Exception("serialNo为空");
		
//		BaseMain main = null;
//		List list = iMainService.getMainListByParentSheetId(serialNo);
//		if(list.size()>0)
//			main = (BaseMain)list.get(0);
//		if(main==null)
//			throw new Exception("没找到sheetNo＝"+serialNo+"对应的工单");
		
		sheetMap.put("main", main);
		sheetMap.put("link", iLinkService.getLinkObject().getClass().newInstance());
		sheetMap.put("operate", this.getPageColumnName()); 
		columnMap.put("selfSheet", sheetMap);
		
		System.out.println("full columnMap");
		String operateUserId = StaticMethod.nullObject2String(map.get("operateUserId"));
		if(operateUserId==null || operateUserId.equals(""))
			operateUserId = main.getSendUserId();
		
		System.out.println("operateUserId="+operateUserId);
		ITawSystemUserManager userMgr = 
			(ITawSystemUserManager)ApplicationContextHolder.getInstance().getBean("itawSystemUserManager");
		TawSystemUser user = userMgr.getUserByuserid(operateUserId);
		if(user!=null){
			map.put("sendDeptId", user.getDeptid());
			map.put("sendContact", user.getMobile());
		
			map.put("operateDeptId", user.getDeptid());
			map.put("operaterContact", user.getMobile());
		}
		map.put("operateUserId", operateUserId);
		map.put("sheetId", main.getSheetId());
		map.put("correlationKey", main.getCorrelationKey());
		map.put("mainId", main.getId());

		System.out.println("full map");
		
		String sheetKey = main.getId();
//		WPSEngineServiceMethod sm = new WPSEngineServiceMethod();
		return "";

	}
	
	public abstract String getMainBeanId();
	public abstract String getLinkBeanId();
	public abstract String getTaskBeanId();
	public abstract String getProcessTemplateName();
	public abstract String getOperateName();
	public String getSendUser(Map map){
		return StaticMethod.nullObject2String(map.get("sendUserId"));
	}
	public abstract String getSheetAttachCode();
	public abstract Map initMap(Map map,List attach,String type) throws Exception;
	public HashMap addPara(HashMap hashMap){
		return hashMap;
	}
	
	public Map setNewInterfacePara() throws Exception{
		IMainService iMainService = (IMainService)ApplicationContextHolder.getInstance().getBean(this.getMainBeanId());
		ILinkService iLinkService = (ILinkService)ApplicationContextHolder.getInstance().getBean(this.getLinkBeanId());
		
		HashMap sheetMap = new HashMap();
		sheetMap.put("main", iMainService.getMainObject().getClass().newInstance());
		sheetMap.put("link", iLinkService.getLinkObject().getClass().newInstance());
		sheetMap.put("operate", this.getPageColumnName());
		
		return sheetMap;
	}
	
	public void notifyWorkSheet() {
		// TODO Auto-generated method stub
		
	}
	public void withdrawWorkSheet() throws Exception{
		// TODO Auto-generated method stub
		
	}
	public void replyWorkSheet() {
		// TODO Auto-generated method stub
		
	}
	public void confirmWorkSheet() {
		// TODO Auto-generated method stub
		
	}
	
	public String getAttach(List attachList){
//		WPSEngineServiceMethod wm = new WPSEngineServiceMethod();
		return "";
	}
	

}
