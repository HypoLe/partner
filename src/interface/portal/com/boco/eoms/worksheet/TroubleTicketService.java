package com.boco.eoms.worksheet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;

import com.boco.activiti.partner.process.dao.IPnrBsResourceSynchJDBCDao;
import com.boco.activiti.partner.process.model.PnrTroubleTicket;
import com.boco.activiti.partner.process.service.IPnrTroubleTicketService;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.common.log.BocoLog;
import com.boco.eoms.commons.system.area.model.TawSystemArea;
import com.boco.eoms.commons.system.area.service.ITawSystemAreaManager;
import com.boco.eoms.partner.baseinfo.mgr.PartnerUserAndAreaMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerUserAndArea;
import com.boco.eoms.partner.process.util.CommonUtils;
import com.boco.eoms.partner.res.mgr.PnrResConfigMgr;
import com.boco.eoms.partner.res.model.PnrResConfig;
import com.boco.eoms.util.InterfaceUtil;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;
/**
 * 与公客系统的接口
 * 1、派单方法 newWorkSheet
 * 2、催单方法 remindersWorkSheet
 * @author chenbing
 *
 */
public class TroubleTicketService {
	private DateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final static String NO_FIND_BSNAME="未检索到基站信息"; 
	private IPnrTroubleTicketService mgr;
	
	public String isAlive(String msg) {
		BocoLog.trace("TroubleTicketService", 34, msg);
		System.out.println("收到握手信号" + msg);
		msg = "成功接收信息:"+msg;
		return msg;
	}

	/**
	 * 接收对方的调用，对方传参数（含工单基本信息的字符串）
	 * 
	 * @param workOrderNo
	 *            公客工单号
	 * @param opDetail
	 *            工单基本信息串
	 * @param attachRef
	 *            附件信息串
	 * @return
	 */
	public String newWorkSheet(String msg) {
		InterfaceUtil doc = new InterfaceUtil();
		HashMap sheetMapWorkOrderNo = new HashMap();
		HashMap sheetMapOpDetail = new HashMap();
		String workOrderNo="";
		msg =doc.changeCharaterOpposite(msg);

		PnrTroubleTicket troubleTicket=null;
		try {
			troubleTicket = getPnrTroubleTicketByMsg(msg,sheetMapWorkOrderNo,sheetMapOpDetail,doc);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(troubleTicket!=null){
			
			workOrderNo=troubleTicket.getGkSerialNo();
		}
		
		BocoLog.trace("派单接口-TroubleTicketService", 75, new Date()+"--msg-:"+msg);

		// 1解析参数 2发单 3发短信通知 4回执信息
		String errList = workOrderNo;
		boolean dealFlag = false;
		if (workOrderNo != null && !"".equals(workOrderNo))
			try {
				if (troubleTicket != null && !"".equals(troubleTicket)) {
					
					
					String gkSerialNo =workOrderNo;
					boolean b = isExistWorkSheet(gkSerialNo);
					if(b){
						errList=errList+";同一工单不能重复派单";
					}
					String theme =troubleTicket.getTheme();
					if("".equals(theme)){
						errList=errList+";工单主题不能为空";
					}				
				
					String gkCityName =troubleTicket.getGkCityName();
					if("".equals(gkCityName)){
						errList=errList+";地市名称不能为空";
					}
					//功课的名称格式 地市-区县
					String[] gkCityInfo = gkCityName.split("-");
					gkCityName = gkCityInfo[0];
					TawSystemArea  area = getAreaIdByAreaName(gkCityName);
					if(area!=null){
						troubleTicket.setGkCityId(area.getAreaid());
					}else{
						errList=errList+";地市名称不存在";
					}
					
					//区县
					/*if(gkCityInfo.length==2){
						String gkCountryName=gkCityInfo[1];
						TawSystemArea  gkCountry = getAreaIdByAreaNameCountry(gkCountryName,troubleTicket.getGkCityId());
						if(gkCountry!=null){
							troubleTicket.setGkCountryId(gkCountry.getAreaid());
						}
					}*/
					//通过静态表找区县ID
					String countryId = getCountryIdByCityMatchTable(troubleTicket.getGkCityName());
					troubleTicket.setGkCountryId(countryId);
					
					
					//故障站点---*根据站点的名称找到，如果没有站点，同样不知道地市信息，可以把工单给superUser协管理员。
					String setFailedSite=troubleTicket.getFailedSite();
					if("".equals(setFailedSite)){
						errList=errList+";故障站点不能为空";
					}
					//通过站点名称，找到巡检资源的信息
					//2014-05-20改造
					String[] setFailedSiteStr=setFailedSite.split("-");
					if(setFailedSiteStr.length>2){
						setFailedSite = setFailedSiteStr[2];						
					}else{
						setFailedSite=NO_FIND_BSNAME;
					}
					//2014-05-20改造--end
					PnrResConfig p = getFailedSiteByName(setFailedSite);
					String	initiator ="";
					//工单派发人
					/*//首先通过传过来的地市信息，找下地市调度人配置了没！！！！！！！！！	
					
				    PartnerUserAndArea objAndArea= getInitiatorByCity(troubleTicket.getGkCityId());
					if(objAndArea!=null&&objAndArea.getUserId()!=null){							
						initiator =objAndArea.getUserId();							
					}*/	
					//先通过区县，在通过地---调度人！
					PartnerUserAndArea objAndArea= getInitiatorByCity(troubleTicket.getGkCountryId());
					if(objAndArea!=null&&objAndArea.getUserId()!=null){							
						initiator =objAndArea.getUserId();							
					}else{
						
						PartnerUserAndArea objAndCity= getInitiatorByCity(troubleTicket.getGkCityId());
						
						if(objAndCity!=null&&objAndCity.getUserId()!=null){							
							initiator =objAndCity.getUserId();	
						}
						
					}					
					
					
					if(p!=null&&p.getId()!=null){						
						troubleTicket.setMainResId(p.getId());							
					//处理人，即站点负责人						
						if(p.getChargePerson()!=null&&p.getChargePerson().length()>0){
							
							troubleTicket.setTaskAssignee(p.getChargePerson());
							dealFlag=true;
						}
						
					//查看工单的派发人是否存在
						if("".equals(initiator)){
					//其次通过传过来的站点，得到巡检资源的归属地市，来找下地市调度人配置了没！！！		
							PartnerUserAndArea obj= getInitiatorByCity(p.getCity());
							if(obj!=null&&obj.getUserId()!=null){								
								initiator =obj.getUserId();								
							}
					
						}
					}
					
					if("".equals(initiator)||initiator==null){
						initiator =CommonUtils.troubleInitiator;
						
					}
					
					troubleTicket.setInitiator(initiator);					
					String createTime ="";
					if(troubleTicket.getCreateTime()!=null){						
						createTime=sFormat.format(troubleTicket.getCreateTime());
					}
					if("".equals(createTime)){
						errList=errList+";故障发生时间不能为空";
					}
													
					String connectPerson =troubleTicket.getConnectPerson();
					
					if("".equals(connectPerson)){
						errList=errList+";故障联系人不能为空";
					}
					
					String faultDescription =troubleTicket.getFaultDescription();
					if("".equals(faultDescription)){
						errList=errList+";故障描述不能为空";
					}
					
					String fromDate = StaticMethod.getCurrentDateTime();
					troubleTicket.setSendTime(sFormat.parse(fromDate));					
					
					if(errList.equals(workOrderNo)){
					 mgr = (IPnrTroubleTicketService) ApplicationContextHolder
						.getInstance().getBean("pnrTroubleTicketService");
					
						errList="0";
						mgr.performAdd(troubleTicket, dealFlag);
					}else{
						troubleTicket =null;
					}
				}else{
					errList="没有工单基础信息";
				}
			} catch (Exception e) {
				e.printStackTrace();
				errList = e.toString();
			}
			else
				errList = "工单编号不能为空";
		
		/*if("0".equals(errList)){
		String msgString=	sendToGKInformation(workOrderNo, "biz_NetWork_tailSend","派发工单环节");
		System.out.println(msgString);
		}*/
		return errList;
	}
//通过区县对照表获取区县ID
	private String getCountryIdByCityMatchTable(String gkCityName){
		String str="";
		mgr = (IPnrTroubleTicketService) ApplicationContextHolder
		.getInstance().getBean("pnrTroubleTicketService");
		Map<String, String> map= mgr.getCityOrCoruntryIdByGkCountryName(gkCityName);
		
		if(map.get("COUNTRY_ID") !=null){
			
			str =map.get("COUNTRY_ID");
		}
		
		return str;
	}
// 返回一个对象
	private PnrTroubleTicket getPnrTroubleTicketByMsg(String msg,HashMap sheetMapWorkOrderNo,HashMap sheetMapOpDetail,InterfaceUtil doc ) throws Exception{
		PnrTroubleTicket troubleTicket = new PnrTroubleTicket();
		String xpathWorkOrderNo="//msg/data/workOrderNo/workOrderInfo";
		String xpathOpDetail="//msg/data/opDetail/recordInfo/fieldInfo";
		sheetMapWorkOrderNo = doc.xmlElements(msg, sheetMapWorkOrderNo, "FieldContent",xpathWorkOrderNo);
		sheetMapOpDetail = doc.xmlElements(msg, sheetMapOpDetail, "FieldContent",xpathOpDetail);
		
		String gkSerialNo = StaticMethod.nullObject2String(sheetMapWorkOrderNo.get("workOrderNo"));
		troubleTicket.setGkSerialNo(gkSerialNo);
		String theme = StaticMethod.nullObject2String(sheetMapOpDetail.get("theme"));
		troubleTicket.setTheme(theme);
		String processLimit = StaticMethod.nullObject2String(sheetMapOpDetail.get("processLimit"));	
		troubleTicket.setProcessLimit(Double.parseDouble(Integer.toString(StaticMethod.getIntValue(processLimit,1))));

		String gkCityName =StaticMethod.nullObject2String(sheetMapOpDetail.get("cityName"));
		troubleTicket.setGkCityName(gkCityName);
		String setFailedSite= StaticMethod.nullObject2String(sheetMapOpDetail.get("failedSite"));
		//故障站点通过关系表-获取站点名称--在包装成 gkCityName-站点名称
		setFailedSite = getBSNameByTwoFields(gkCityName,setFailedSite);
		
		troubleTicket.setFailedSite(setFailedSite);
		troubleTicket.setFaultSource("101011005");

		String createTime = StaticMethod.nullObject2String(sheetMapOpDetail.get("createTime"));
		if(!"".equals(createTime)){			
			troubleTicket.setCreateTime(sFormat.parse(createTime));
		}

		troubleTicket.setIsCustomers(1030101);
		troubleTicket.setSubType("101220101");
		troubleTicket.setSpecialty("101010806");
		String connectPerson =StaticMethod.nullObject2String(sheetMapOpDetail.get("connectPerson"));
		troubleTicket.setConnectPerson(connectPerson);

		String faultDescription =StaticMethod.nullObject2String(sheetMapOpDetail.get("faultDescription"));
		troubleTicket.setFaultDescription(faultDescription);


		return troubleTicket;
	}
	//通过与公客系统的关系表获取基站名称
	private String getBSNameByTwoFields(String gkCityName,String setFailedSite){
		
		String bsName = "";		
		IPnrBsResourceSynchJDBCDao  dao = (IPnrBsResourceSynchJDBCDao) ApplicationContextHolder.getInstance().getBean("pnrBsResourceSynchJDBCDao");

		bsName= dao.getBsNameByCityInfoAndBs(gkCityName, setFailedSite);
		if(!"".equals(bsName)){
			bsName=gkCityName+"-"+bsName;
		}else{
			bsName=setFailedSite;
		}
		return bsName;
	}
	public String remindersWorkSheet(String msg) throws Exception {
		String workOrderNo,remindersWorkSheetString;
		InterfaceUtil doc = new InterfaceUtil();
		HashMap sheetMapSerialNo = new HashMap();
		HashMap sheetMapOpDetail = new HashMap();
//		催单:接收催单命令；发短信给处理人；回执信息。
		String xpathWorkOrderNo="//msg/data/workOrderNo/workOrderInfo";

		String xpathOpDetail="//msg/data/opDetail/recordInfo/fieldInfo";
		BocoLog.trace("催单接口-TroubleTicketService", 269, new Date()+"--remindersWorkSheet:"+msg);
		sheetMapSerialNo = doc.xmlElements(msg, sheetMapSerialNo, "FieldContent",xpathWorkOrderNo);
		sheetMapOpDetail = doc.xmlElements(msg, sheetMapOpDetail, "FieldContent",xpathOpDetail);
		String serialNo = StaticMethod.nullObject2String(sheetMapSerialNo.get("workOrderNo"));
		String reminderMsg = StaticMethod.nullObject2String(sheetMapOpDetail.get("remindersWorkSheet"));
		String errList =serialNo;
		if(!"".equals(serialNo)){
		
			if (reminderMsg != null && !"".equals(reminderMsg)) {
				try {
					if("".equals(reminderMsg)){
						errList=errList+";催单信息不能为空";
					}else{
						boolean flag = isExistWorkSheet(serialNo);
						if(flag){							
							String userId = getMsgReceiveBySerialNo(serialNo);							
							CommonUtils.sendMsg(reminderMsg, userId);
							errList="0";
						}else{
							errList=errList+";工单编号不正确";
						}
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					errList = e.toString();

				}
			}else{
				errList="没有催单信息";
			}
		}else{
			errList = "工单编号不能为空";
		}	

		return errList;

	}
	private PnrResConfig getFailedSiteByName(String resName){
		PnrResConfigMgr pnrResConfigMgr = (PnrResConfigMgr) ApplicationContextHolder.getInstance().getBean("PnrResConfigMgr");
		Search search = new Search();
		search.addFilterEqual("resName", resName);
		PnrResConfig pnrResConfig =null ;
		SearchResult<PnrResConfig> result = pnrResConfigMgr.searchAndCount(search);
		
		if(result.getTotalCount()>0){
			pnrResConfig=result.getResult().get(0);
		}
		
		return pnrResConfig;
	}
	
	private PartnerUserAndArea getInitiatorByCity(String city){
		PartnerUserAndAreaMgr  puserArea = (PartnerUserAndAreaMgr) ApplicationContextHolder.getInstance().getBean("partnerUserAndAreaMgr");
		PartnerUserAndArea obj =null ;
		obj = puserArea.getPartnerUserAndAreaByAreaId(city);
		
		return obj;
	}
	
	private TawSystemArea getAreaIdByAreaName(String cityName){
		ITawSystemAreaManager  tawArea = (ITawSystemAreaManager) ApplicationContextHolder.getInstance().getBean("ItawSystemAreaManager");
		String cNameString = "";
		if(cityName!=null&&cityName.length()>0){
			cNameString=cityName.trim();
		}
		if(cNameString.length()>2){
			cNameString = cNameString.substring(0, 2);
		}
		TawSystemArea  obj = null;
		List<TawSystemArea> objAreaList = null;
		String where = " where sysarea.ordercode=2 and sysarea.areaname like '"+cNameString+"%'";
		objAreaList= tawArea.getareas(where);
		if(objAreaList.size()>0){
			obj=objAreaList.get(0);
		}
		
		return obj;
	}
	private TawSystemArea getAreaIdByAreaNameCountry(String countryName,String cityId){
		ITawSystemAreaManager  tawArea = (ITawSystemAreaManager) ApplicationContextHolder.getInstance().getBean("ItawSystemAreaManager");
		String cNameString = "";
		if(countryName!=null&&countryName.length()>0){
			cNameString=countryName.trim();
		}
		if(cNameString.length()>2){
			cNameString = cNameString.substring(0, 2);
		}
		TawSystemArea  obj = null;
		List<TawSystemArea> objAreaList = null;
		String where = " where sysarea.ordercode=3 and sysarea.parentAreaid ='"+cityId+"' and sysarea.areaname like '"+cNameString+"%'";
		objAreaList= tawArea.getareas(where);
		if(objAreaList.size()>0){
			obj=objAreaList.get(0);
		}
		
		return obj;
	}
	private boolean isExistWorkSheet(String serialNo){
		boolean isExist = true;
		 mgr = (IPnrTroubleTicketService) ApplicationContextHolder
		.getInstance().getBean("pnrTroubleTicketService");
		Search search = new Search();
		search.addFilterEqual("gkSerialNo", serialNo);
		SearchResult<PnrTroubleTicket> list = mgr.searchAndCount(search);
		if(list.getTotalCount()==0){
			isExist = false;
		}
		
		return isExist;
	}
	private String getMsgReceiveBySerialNo(String SerialNo){
		String userId=CommonUtils.troubleInitiator;
		TaskService taskService = (TaskService)ApplicationContextHolder
		.getInstance().getBean("taskService");
		 mgr = (IPnrTroubleTicketService) ApplicationContextHolder
		.getInstance().getBean("pnrTroubleTicketService");
		Search search = new Search();
		search.addFilterEqual("gkSerialNo", SerialNo);
		search.addSortDesc("sendTime");
		SearchResult<PnrTroubleTicket> list =mgr.searchAndCount(search);
		if(list.getTotalCount()>0){
			PnrTroubleTicket pnrTroubleTicket = list.getResult().get(0);
			List<Task> objList =taskService.createTaskQuery().processInstanceId(pnrTroubleTicket.getProcessInstanceId()).list();
			if(objList.size()>0){								
				userId = objList.get(0).getAssignee();
			}
		}
		return userId;
	}
	/*
	 *已移动到/partner_qg/src/partner/process/com/boco/eoms/partner/process/util/CommonUtils.java 
	 */
	/*private String sendToGKInformation(String workOrderNo,String method,String sendMsg){
		
		String url=CommonUtils.GK_INTERFACE_URL;
		String msgStr = "<msg>" +
				"<router>" +
				"<to>sasa</to>" +
				"<msgId>随机编码</msgId>" +
		"<time>"+sFormat.format(new Date())+"</time>" +
				"<serviceName>"+method+"</serviceName>" +
		"<from>wlwh</from></router><data>" +
		"<workOrderNo>"+workOrderNo+"</workOrderNo>" +
		"<tail>"+sendMsg+"</tail>" +
		"</data></msg>";
		
		String getMsg=InterfaceUtil.gkAgencyMethod(url, "startInvoke", msgStr);
		return 	getMsg;
	}
	*/
	public TroubleTicketService() {

	}
}
