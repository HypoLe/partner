package com.boco.eoms.worksheet;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricTaskInstance;

import com.boco.activiti.partner.process.model.OperationReturnResult;
import com.boco.activiti.partner.process.model.OperationReturnResultDetail;
import com.boco.activiti.partner.process.model.OperationShopConfigModel;
import com.boco.activiti.partner.process.model.PnrAndroidWorkOderPhotoFile;
import com.boco.activiti.partner.process.model.PnrTaskTicket;
import com.boco.activiti.partner.process.model.PnrTaskTicketHandle;
import com.boco.activiti.partner.process.model.PnrTransferOffice;
import com.boco.activiti.partner.process.model.PnrTransferOfficeHandle;
import com.boco.activiti.partner.process.model.PnrTroubleTicket;
import com.boco.activiti.partner.process.model.PnrTroubleTicketHandle;
import com.boco.activiti.partner.process.service.IOperationReturnResultDetailService;
import com.boco.activiti.partner.process.service.IOperationReturnResultService;
import com.boco.activiti.partner.process.service.IPnrAndroidWorkOderPhotoFileService;
import com.boco.activiti.partner.process.service.IPnrTaskTicketHandleService;
import com.boco.activiti.partner.process.service.IPnrTaskTicketService;
import com.boco.activiti.partner.process.service.IPnrTransferOfficeHandleService;
import com.boco.activiti.partner.process.service.IPnrTransferOfficeService;
import com.boco.activiti.partner.process.service.IPnrTroubleTicketHandleService;
import com.boco.activiti.partner.process.service.IPnrTroubleTicketService;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.common.log.BocoLog;
import com.boco.eoms.partner.process.util.CommonUtils;
import com.boco.eoms.util.InterfaceUtil;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;
/**
 * 与运维商城的接口
 * 1、判断方法 isAlive
 * 2、返回url方法 newShowPageUrl
 * 
 * @author chenbing
 *
 */
public class OperationShopService {
	private DateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private OperationShopConfigModel xmlConfigModel=(OperationShopConfigModel)ApplicationContextHolder.getInstance().getBean("operationShopConfigModel");
	private final static String TROUBLE_FLAG="trouble";
	private final static String TASK_FLAG="task";

	private final String PATH=xmlConfigModel.getVisitHtmlPathPrefix();

	private final  String baseUrl = xmlConfigModel.getPlaceHtmlPathPrefix();
	private final  String visitUrl = xmlConfigModel.getVisitProjectUrl();
	private final  String imgServerUrl = xmlConfigModel.getImgServerUrl();
//	private final static int TROUBLE_DELETE_STATE = 1;
//	private final static int TASK_DELETE_STATE = 4;
	public  final static String TICKETTYPE_TROUBLE="trouble";
	public  final static String TICKETTYPE_TROUBLEDETAIL="troubleDetail";
	private final static int TROUBLE_DONE_STATE = 5;
	private final static int TASK_DONE_STATE = 5;
	private IPnrTroubleTicketService troubleService;
	private IOperationReturnResultService operationReturnResultService;
	private IOperationReturnResultDetailService operationReturnResultDetailService;
	private IPnrTaskTicketService taskService;
	private IPnrTransferOfficeService transferService;
	private IPnrTroubleTicketHandleService troubleHandleService ;
	private IPnrTransferOfficeHandleService transferOfficeHandleService ;
	private HistoryService historyService ;
	private IPnrTaskTicketHandleService taskHandleService;
//	故障照片service
	private IPnrAndroidWorkOderPhotoFileService pnrService;

	/**
	 * ①网络商城回填结束后，将工单号、订单号、回填完成时间、订单总金额发送给现场综合维护管理系统。
	 * ②网络商城回填结束后，将故障单号、商品名称、商品规格、商品单价、商品数量、总金额发送给现场综合维护管理系统--明细。
	 * 根据ticketType值判断传递内容（trouble-->①；troubleDetail-->②） 
	 * @param msg
	 * @return  返回内容：0或1；0代表失败，1代表成功
	 * @throws Exception 
	 * @throws Exception
	 */
	public String archiveNotify(String msg){
		String value ="0";
		
		InterfaceUtil doc = new InterfaceUtil();
		HashMap map =new HashMap() ;
		String xpathOpDetail="//opDetail/recordInfo/fieldInfo";
		msg = doc.changeCharaterOpposite(msg);
		BocoLog.info("OperationShopService", 96, "用于测试穿过来的字符串msg:"+msg);
		try {
			map = doc.xmlElements(msg, map, "FieldContent",xpathOpDetail);
		} catch (Exception e) {			
			BocoLog.trace("OperationShopService", 100, "msg:"+msg,e);
			return "0";
		}
		//获得对象
		String ticketType=StaticMethod.nullObject2String(map.get("ticketType"));
		
		if(ticketType.equals(TICKETTYPE_TROUBLE)){
			
			String processInstanceId=StaticMethod.nullObject2String(map.get("processInstanceId"));
			String orderId=StaticMethod.nullObject2String(map.get("orderId"));
			String backfillTime=StaticMethod.nullObject2String(map.get("backfillTime"));
			String orderPrice=StaticMethod.nullObject2String(map.get("orderPrice"));
			OperationReturnResult entity = new OperationReturnResult();
			entity.setTicketType(ticketType);
			entity.setProcessInstanceId(processInstanceId);
			entity.setOrderId(orderId);
			try {
				entity.setBackfillTime(sFormat.parse(backfillTime));
			} catch (ParseException e) {
				BocoLog.trace("OperationShopService", 118, "msg:"+msg,e);
				return "0";
			}
			entity.setOrderPrice(orderPrice);
			entity.setInsertTime(new Date());
			
			value = recordOperationReturnResult(entity);
			
		}else if(ticketType.equals(TICKETTYPE_TROUBLEDETAIL)){
			
			String faultOrderId=StaticMethod.nullObject2String(map.get("faultOrderId"));
			String goodName=StaticMethod.nullObject2String(map.get("goodName"));
			String goodStandard=StaticMethod.nullObject2String(map.get("goodStandard"));
			String goodPrice=StaticMethod.nullObject2String(map.get("goodPrice"));
			String goodNumber=StaticMethod.nullObject2String(map.get("goodNumber"));
			String totalMoney=StaticMethod.nullObject2String(map.get("totalMoney"));
			
			OperationReturnResultDetail entity = new OperationReturnResultDetail();
			entity.setTicketType(ticketType);
			entity.setFaultOrderId(faultOrderId);
			entity.setGoodName(goodName);
			entity.setGoodStandard(goodStandard);
			entity.setGoodPrice(goodPrice);
			entity.setGoodNumber(goodNumber);
			entity.setTotalMoney(totalMoney);
			
			entity.setInsertTime(new Date());
			
			value = recordOperationReturnResultDetail(entity);
			
		}
		
		return value;
	}
	//archiveNotify 明细记录
	private String recordOperationReturnResultDetail(OperationReturnResultDetail entity){
		String str ="0";
		operationReturnResultDetailService = (IOperationReturnResultDetailService) ApplicationContextHolder.getInstance().getBean("operationReturnResultDetailService");
		boolean t = operationReturnResultDetailService.save(entity);
		if(t){
			str ="1";
		}

		return str;
	}
	//archiveNotify 汇总记录
	private String recordOperationReturnResult(OperationReturnResult entity){
		String str = "0";
		operationReturnResultService = (IOperationReturnResultService) ApplicationContextHolder.getInstance().getBean("operationReturnResultService");
	/*	//判断当前记录是否存在
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		int count = operationReturnResultService.count(search);
		if(count>0){
			v = 1;
//			return value;
		}*/
		
		boolean b =operationReturnResultService.save(entity);
		
		if(b){
			str="1";
		}
		
		return str;
		
	}
	
	public String isAlive(String msg) throws Exception {		
		String value="0";
		InterfaceUtil doc = new InterfaceUtil();
		HashMap map =new HashMap() ;
		String xpathOpDetail="//opDetail/recordInfo/fieldInfo";
//		BocoLog.trace("OperationShopService-isAlive", 145, "msg转义前:"+msg);

		msg = doc.changeCharaterOpposite(msg);
//		BocoLog.trace("OperationShopService-isAlive", 148, "msg转义后:"+msg);
		map = doc.xmlElements(msg, map, "FieldContent",xpathOpDetail);
		String flag=StaticMethod.nullObject2String(map.get("ticketType"));
		String processInstanceId=StaticMethod.nullObject2String(map.get("processInstanceId"));
		
		//boolean b=isExistWorkSheet(flag,processInstanceId);
		
		//BocoLog.trace("OperationShopService", 38, msg);
		/*if(b){
			value="1";
		}*/
		if(flag.equals(TROUBLE_FLAG)){
		/*	PnrTroubleTicket pnrTroubleTicket = isExistPnrTroubleTicket(processInstanceId);
			
			if(pnrTroubleTicket !=null){
				value="1;"+sFormat.format(pnrTroubleTicket.getArchivingTime());
			}*/
			//做更改---2014-06-19
//            PnrTransferOffice pnrTransferOffice = isExistPnrTransferOffice(processInstanceId);
//			
//			if(pnrTransferOffice !=null){
//				
//				if(pnrTransferOffice.getState()==TROUBLE_DONE_STATE){
//					
//					value="1;"+sFormat.format(pnrTransferOffice.getArchivingTime());
//				}else if("1".equals(pnrTransferOffice.getIsDistribute())){
//					
//					value="3";//已关联
//				}else{
//					value="2";					
//				}
//			}
			
			//做更改---2016-07-12
			//判断是工单号，还是周期领用表编号
			if(!"".equals(processInstanceId)){
				boolean result = InterfaceUtil.isNumeric(processInstanceId);
				if(result){ //工单号
					 PnrTransferOffice pnrTransferOffice = isExistPnrTransferOffice(processInstanceId); //要把公客和非故障抛出掉 还没做？
						if(pnrTransferOffice !=null){
							if(pnrTransferOffice.getState()==TROUBLE_DONE_STATE){
								value="1;"+sFormat.format(pnrTransferOffice.getArchivingTime());
							}else if("1".equals(pnrTransferOffice.getIsDistribute())){
								value="3";//已关联
							}else{
								value="2";					
							}
						}
				}else{ //周期领用表报表编号
					//1.判断报表编号是否存在
					transferService = (IPnrTransferOfficeService) ApplicationContextHolder.getInstance().getBean("pnrTransferOfficeService");
					boolean existsCycleReportNumber = transferService.existsCycleReportNumber(processInstanceId);
					if(existsCycleReportNumber){
						//2.判断报表编号是否已经验证过了
						boolean verifiedCycleReportNumber = transferService.isVerifiedCycleReportNumber(processInstanceId);
						if(verifiedCycleReportNumber){
							value="3"; 
						}else{
							//取报表提交时间
							Date submitDate = transferService.getSubmitDateByReportNum(processInstanceId);
							if(submitDate != null){
								value="1;"+sFormat.format(submitDate);
								//插入验证信息
								transferService.insertVerifiedNumber(processInstanceId, "cycle");
							}else{
								value="2"; //代表还未处理完毕
							}
						}
					}
				}
			}
		}/*else if (flag.equals(TASK_FLAG)){
			PnrTaskTicket pnrTaskTicket = isExistPnrTaskTicket(processInstanceId);
			
			if(pnrTaskTicket !=null){
				value="1;"+sFormat.format(pnrTaskTicket.getArchivingTime());
			}
		}*/
		BocoLog.info("网络商城-校验接口-OperationShopService-isAlive-工单号"+processInstanceId, 109,"返回值是(0代表不存在,否则存在):"+value);
		return value;
	}
	
	public String newShowPageUrl(String msg) throws Exception{
		InterfaceUtil doc = new InterfaceUtil();		
		String flag="", processInstanceId="";		
		String url="";
		String head="",baseInfo="",flowInfo="",doInfo="",footer="";
		
		HashMap map =new HashMap() ;
		String xpathOpDetail="//opDetail/recordInfo/fieldInfo";
		BocoLog.trace("OperationShopService-newShowPageUrl", 244, "msg转义前:"+msg);
		msg = doc.changeCharaterOpposite(msg);
//      BocoLog.trace("OperationShopService-newShowPageUrl", 199, "msg转义后:"+msg);
		map = doc.xmlElements(msg, map, "FieldContent",xpathOpDetail);
		flag=StaticMethod.nullObject2String(map.get("ticketType"));
		processInstanceId=StaticMethod.nullObject2String(map.get("processInstanceId"));
		if(!"".equals(processInstanceId)){
			boolean result = InterfaceUtil.isNumeric(processInstanceId);
			if(result){ //工单号
				transferService = (IPnrTransferOfficeService) ApplicationContextHolder.getInstance().getBean("pnrTransferOfficeService");
				List<PnrTransferOffice> pnrTransferOffices = transferService.getWorkOrderDetails(processInstanceId);
				PnrTransferOffice pnrTransferOffice =null;
				String themeInterface="";
				if(pnrTransferOffices.size()>0){
					pnrTransferOffice = pnrTransferOffices.get(0);
					if(pnrTransferOffice.getThemeInterface()!=null && !"".equals(pnrTransferOffice.getThemeInterface())){
						themeInterface=pnrTransferOffice.getThemeInterface();
						if("interface".equals(themeInterface)){
							url=visitUrl+"/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=gotoDetail&id=961502&processInstanceId="+processInstanceId+"&type=interface&userName=lizhong";
						}else if("arteryPrecheck".equals(themeInterface)){
							url=visitUrl+"/activiti/pnrTransferPrecheck/pnrTransferArteryPrecheck.do?method=gotoDetail&id=961503&processInstanceId="+processInstanceId+"&type=interface&userName=lizhong";
						}else if("overhaul".equals(themeInterface)){
							url=visitUrl+"/activiti/pnrTransferPrecheck/pnrOverhaulRemake.do?method=gotoDetail&id=961502&processInstanceId="+processInstanceId+"&type=interface&userName=lizhong";
						}else if("oltBbuProcess".equals(themeInterface)){
							url=visitUrl+"/activiti/pnrOltBbuRoom/pnrOltBbuRoom.do?method=gotoDetail&id=961508&processInstanceId="+processInstanceId+"&type=interface&userName=lizhong";
						}
//						else if("transferOffice".equals(themeInterface)){
//							url=visitUrl+"/activiti/pnrTransferOffice/pnrTransferOffice.do?method=gotoDetail&id=961510&processInstanceId="+processInstanceId+"&type=interface&userName=lizhong";
//						}else if("maleGuest".equals(themeInterface)){
//							url=visitUrl+"/activiti/pnrTransferMaleGuest/pnrTransferOfficeMaleGuest.do?method=gotoDetail&id=961511&processInstanceId="+processInstanceId+"&type=interface&userName=lizhong";
//						}
						else{
							url=PATH+"/error.html";
						}
					}else{
						url=PATH+"/error.html";
					}
				}else{
					url=PATH+"/error.html";
				}
			}else{ //报表编号
				transferService = (IPnrTransferOfficeService) ApplicationContextHolder.getInstance().getBean("pnrTransferOfficeService");
				boolean existsCycleReportNumber = transferService.existsCycleReportNumber(processInstanceId);
				if(existsCycleReportNumber){
					url=visitUrl+"/activiti/pnrTransferMaleGuest/pnrTransferOfficeMaleGuest.do?method=gotoDetailCycleReport&id=961511&processInstanceId="+processInstanceId+"&type=interface&userName=lizhong";
				}else{
					url=PATH+"/error.html";
				}
			}
		}
		
		
		//判断，
//		BocoLog.trace("OperationShopService", 60,msg+"--processInstanceId--"+processInstanceId+"----flag--"+flag);
//		boolean b=isExistWorkSheet(flag,processInstanceId);
//		String pathUrl=PATH+"/error.html";
////		if(b){
//			JspToHtml jspToHtml = new JspToHtml();		
//			head=getHeadInfo();
//			footer=getFooterInfo();
//			
//			if(OperationShopService.TASK_FLAG.equals(flag)){
//				baseInfo=getTaskTicketBaseInfo(processInstanceId);
//				doInfo=getTaskDoInfo(processInstanceId);
//				
//			}else if(OperationShopService.TROUBLE_FLAG.equals(flag)){
//				
////				baseInfo=getTroubleTicketBaseInfo(processInstanceId);
//				baseInfo=getTransferOfficeBaseInfo(processInstanceId);
//				doInfo=getTransferDoInfo(processInstanceId);
//			}
//			
//			flowInfo=getTroubleOrTaskFlowInfo(processInstanceId);
//			
//			String htmlContext=head+baseInfo+flowInfo+doInfo+footer;
//			
//			pathUrl =jspToHtml.createHtml(htmlContext, flag+processInstanceId);
//			pathUrl=pathUrl.replace(baseUrl, PATH);
//			
//			//生成故障照片的页面
//			List<PnrAndroidWorkOderPhotoFile> pfiles = getTroublePhotosDatas(processInstanceId);
//			String html= getTroublePhotosHtml(pfiles);
//			
//			jspToHtml.createHtml(html, processInstanceId);
//		}
			
        BocoLog.info("网络商城-打开工单接口-OperationShopService-newShowPageUrl-工单号"+processInstanceId,143,"返回值是url:"+url);

		return url;
		
	}
	
	private boolean isExistWorkSheet(String flag,String processInstanceId){
		boolean isExist = false;
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		int size=0;
		
		if(OperationShopService.TROUBLE_FLAG.equals(flag)){
//			search.addFilterNotEqual("state", TROUBLE_DELETE_STATE);
			search.addFilterEqual("state", TROUBLE_DONE_STATE);
			troubleService = (IPnrTroubleTicketService) ApplicationContextHolder
			.getInstance().getBean("pnrTroubleTicketService");
			
			SearchResult<PnrTroubleTicket> list = troubleService.searchAndCount(search);
			size=list.getTotalCount();
			
		}else if(OperationShopService.TASK_FLAG.equals(flag)){
//			search.addFilterNotEqual("state", TASK_DELETE_STATE);
			search.addFilterEqual("state", TASK_DONE_STATE);
			taskService= (IPnrTaskTicketService) ApplicationContextHolder
			.getInstance().getBean("pnrTaskTicketService");
			SearchResult<PnrTaskTicket> list = taskService.searchAndCount(search);
			size=list.getTotalCount();

		}	
	
		
		if(size>0){
			isExist = true;
		}
		
		return isExist;
	}
	/*
	 * 返回故障工单对象
	 */
	private PnrTroubleTicket isExistPnrTroubleTicket(String processInstanceId){
		PnrTroubleTicket pnrTroubleTicket=null;
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);		
		search.addFilterEqual("state", TROUBLE_DONE_STATE);
		search.addSortDesc("sendTime");
		
		troubleService = (IPnrTroubleTicketService) ApplicationContextHolder
		.getInstance().getBean("pnrTroubleTicketService");
		
		SearchResult<PnrTroubleTicket> list = troubleService.searchAndCount(search);
		
		if(list.getTotalCount()>0){
			pnrTroubleTicket = list.getResult().get(0);
		}
		
		return pnrTroubleTicket;
	}
	/*
	 * 返回传输局工单对象
	 */
	private PnrTransferOffice isExistPnrTransferOffice(String processInstanceId){
		PnrTransferOffice pnrTransferOffice=null;
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);		
	 //   search.addFilterEqual("state", TROUBLE_DONE_STATE);
		//关闭公客工单校验 maleGuest
		search.addFilterNotEqual("themeInterface", "maleGuest");
		//关闭抢修工单校验 transferOffice
		search.addFilterNotEqual("themeInterface", "transferOffice");
		search.addSortDesc("sendTime");
		
		transferService = (IPnrTransferOfficeService) ApplicationContextHolder
		.getInstance().getBean("pnrTransferOfficeService");
		
		SearchResult<PnrTransferOffice> list = transferService.searchAndCount(search);
		
		if(list.getTotalCount()>0){
			pnrTransferOffice = list.getResult().get(0);
		}
		
		return pnrTransferOffice;
	}
	/*
	 * 返回任务工单对象
	 */
	private PnrTaskTicket isExistPnrTaskTicket(String processInstanceId){
		PnrTaskTicket pnrTaskTicket = null;
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		search.addFilterEqual("state",TASK_DONE_STATE);
		search.addSortDesc("createTime");
		
		taskService= (IPnrTaskTicketService) ApplicationContextHolder
		.getInstance().getBean("pnrTaskTicketService");
		SearchResult<PnrTaskTicket> list = taskService.searchAndCount(search);
		
		if(list.getTotalCount()>0){
			pnrTaskTicket = list.getResult().get(0);			
		}
		
		return pnrTaskTicket;
	}
	
	private String getTroubleOrTaskFlowInfo(String processInstanceId){
		String troubleFlowInfo="";
		
		historyService= (HistoryService) ApplicationContextHolder.getInstance().getBean("historyService");
		List<HistoricTaskInstance> historicTasks = historyService
		.createHistoricTaskInstanceQuery().processInstanceId(
				processInstanceId).list();
		
		List <Map<String,String>> flowInfos= new ArrayList<Map<String,String>>();
		Map<String ,String> map =null;
		int size =historicTasks.size();
		
		for(int i =0;i<size;i++){
			map=new HashMap<String, String>();
			map.put("name", historicTasks.get(i).getName());
			
			if(historicTasks.get(i).getStartTime()!=null){
				map.put("startTime", sFormat.format(historicTasks.get(i).getStartTime()));
			}
			if(historicTasks.get(i).getEndTime()!=null){
				map.put("endTime", sFormat.format(historicTasks.get(i).getEndTime()));
			}
			
			map.put("assignee", historicTasks.get(i).getAssignee());
			flowInfos.add(map);
		}
	
		troubleFlowInfo=getFlowInfo(flowInfos);		
		return troubleFlowInfo;
	}
	
	private String getTroubleDoInfo(String processInstanceId){
		String troubleDoInfo="";
		
		troubleHandleService = (IPnrTroubleTicketHandleService) ApplicationContextHolder
				.getInstance().getBean("pnrTroubleTicketHandleService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		search.addSort("receiveTime", true);//按回复时间排序
		
		List<PnrTroubleTicketHandle> handlelist = troubleHandleService
				.searchAndCount(search).getResult();
		
		List <Map<String,String>> doInfos= new ArrayList<Map<String,String>>();
		Map<String ,String> map =null;
		int size = handlelist.size();
		String transport="";
		String isRecover="";
		String doPersons="";
		String receivePerson="";
		
		for(int i =0;i< size;i++){
			map= new HashMap<String, String>();
			
			if(handlelist.get(i).getTransport()!=null){
				transport=CommonUtils.getId2NameString(handlelist.get(i).getTransport(), 1, ",");
				map.put("transport",transport);
			}
			map.put("mileage",handlelist.get(i).getMileage().toString());
			
			if(handlelist.get(i).getIsRecover()!=null){
				isRecover=CommonUtils.getId2NameString(handlelist.get(i).getIsRecover(), 1, ",");
				map.put("isRecover",isRecover);
			}
			if(handlelist.get(i).getReceiveTime()!=null){
				
				map.put("receiveTime",sFormat.format(handlelist.get(i).getReceiveTime()));
			}
			
			map.put("handleDescription",handlelist.get(i).getHandleDescription());			
			
			if(handlelist.get(i).getDoPersons()!=null){
				doPersons=CommonUtils.getId2NameString(handlelist.get(i).getDoPersons(), 4, ",");
				
				map.put("doPersons",doPersons);
			}
			if(handlelist.get(i).getReceivePerson()!=null){
				receivePerson=CommonUtils.getId2NameString(handlelist.get(i).getReceivePerson(), 4, ",");
				map.put("receivePerson",receivePerson);			
			}
			
			doInfos.add(map);
	
		}
		
		
		troubleDoInfo = getTroubleDoHtml(doInfos);
		
		return troubleDoInfo;
	}
	//传输局处理过程
	private String getTransferDoInfo(String processInstanceId){
		String transferDoInfo="";
		
		transferOfficeHandleService = (IPnrTransferOfficeHandleService) ApplicationContextHolder
		.getInstance().getBean("pnrTransferOfficeHandleService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		search.addSort("receiveTime", true);//按回复时间排序
		
		List<PnrTransferOfficeHandle> handlelist = transferOfficeHandleService
		.searchAndCount(search).getResult();
		
		List <Map<String,String>> doInfos= new ArrayList<Map<String,String>>();
		Map<String ,String> map =null;
		int size = handlelist.size();
		String isRecover="";
		String receivePerson="";
		
		for(int i =0;i< size;i++){
			map= new HashMap<String, String>();			
			
			if(handlelist.get(i).getIsRecover()!=null){
				isRecover=CommonUtils.getId2NameString(handlelist.get(i).getIsRecover(), 1, ",");
				map.put("isRecover",isRecover);
			}
			if(handlelist.get(i).getReceiveTime()!=null){
				
				map.put("receiveTime",sFormat.format(handlelist.get(i).getReceiveTime()));
			}
			
			map.put("handleDescription",handlelist.get(i).getHandleDescription());			
			
				
			map.put("doPersons",handlelist.get(i).getDoPersons());
			if(handlelist.get(i).getReceivePerson()!=null){
				receivePerson=CommonUtils.getId2NameString(handlelist.get(i).getReceivePerson(), 4, ",");
				map.put("receivePerson",receivePerson);			
			}
			
			
			doInfos.add(map);
			
		}
		
		
		transferDoInfo = getTransferDoHtml(doInfos);
		
		return transferDoInfo;
	}
	private String getTaskDoInfo(String processInstanceId){
		String taskDoInfo="";
		
		taskHandleService = (IPnrTaskTicketHandleService) ApplicationContextHolder
				.getInstance().getBean("pnrTaskTicketHandleService");

		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		search.addSort("receiveTime", true);//按回复时间排序
		
        List<PnrTaskTicketHandle> handlelist = taskHandleService.searchAndCount(search).getResult();

		
		List <Map<String,String>> doInfos= new ArrayList<Map<String,String>>();
		Map<String ,String> map =null;
		
		int size = handlelist.size();
		
		String transport="";
		String doPersons="";
		String taskAssignee="";
		
		for(int i =0;i< size;i++){
			map= new HashMap<String, String>();
			
			if(handlelist.get(i).getTransport()!=null){
				transport=CommonUtils.getId2NameString(handlelist.get(i).getTransport(), 1, ",");
				map.put("transport",transport);
			}
			map.put("mileage",handlelist.get(i).getMileage().toString());
			if(handlelist.get(i).getReceiveTime()!=null){
				
				map.put("receiveTime",sFormat.format(handlelist.get(i).getReceiveTime()));
			}
			if(handlelist.get(i).getDoPersons()!=null){
				doPersons=CommonUtils.getId2NameString(handlelist.get(i).getDoPersons(), 4, ",");
				map.put("doPersons",doPersons);
			}
			map.put("handleDescription",handlelist.get(i).getHandleDescription());			
			if(handlelist.get(i).getTaskAssignee()!=null){
				transport=CommonUtils.getId2NameString(handlelist.get(i).getTaskAssignee(), 4, ",");
				map.put("taskAssignee",transport);			
			}
			
			doInfos.add(map);
			
		}
				
		taskDoInfo = getTaskDoHtml(doInfos);		
		return taskDoInfo;
	}
	private String getTroubleTicketBaseInfo(String processInstanceId){
		String troubleBaseInfo="";
		troubleService= (IPnrTroubleTicketService) ApplicationContextHolder
		.getInstance().getBean("pnrTroubleTicketService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		search.addSortAsc("sendTime");
		SearchResult<PnrTroubleTicket> pnrTroubleTickets = troubleService.searchAndCount(search);
		PnrTroubleTicket pnrTroubleTicket =null;
		if(pnrTroubleTickets.getTotalCount()>0){
			
			pnrTroubleTicket = pnrTroubleTickets.getResult().get(0);
			troubleBaseInfo = getTroubleBaseInfo(pnrTroubleTicket);
		}
		
		return troubleBaseInfo;
	}
	//传输局工单
	private String getTransferOfficeBaseInfo(String processInstanceId){
		String transferBaseInfo="";
		transferService = (IPnrTransferOfficeService) ApplicationContextHolder
		.getInstance().getBean("pnrTransferOfficeService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		search.addSortAsc("sendTime");
		SearchResult<PnrTransferOffice> pnrTransferOffices = transferService.searchAndCount(search);
		PnrTransferOffice pnrTransferOffice =null;
		if(pnrTransferOffices.getTotalCount()>0){
			
			pnrTransferOffice = pnrTransferOffices.getResult().get(0);
			transferBaseInfo = getTransferBaseInfo(pnrTransferOffice);
		}
		
		return transferBaseInfo;
	}
	private String getTaskTicketBaseInfo(String processInstanceId){
		String taskBaseInfo="";
		taskService= (IPnrTaskTicketService) ApplicationContextHolder
		.getInstance().getBean("pnrTaskTicketService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		search.addSortAsc("createTime");
		SearchResult<PnrTaskTicket> pnrTaskTickets = taskService.searchAndCount(search);
		PnrTaskTicket pnrTaskTicket =null;
		
		if(pnrTaskTickets.getTotalCount()>0){
			
			pnrTaskTicket = pnrTaskTickets.getResult().get(0);
			
			taskBaseInfo = getTicketBaseInfo(pnrTaskTicket);
		}
		
		return taskBaseInfo;
	}
	private  String getHeadInfo(){
		String head="<html><head><title>山东联通现场综合维护管理系统V1.1</title>" +
		"  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=gbk\"/>" +
			"<link rel=\"stylesheet\" type=\"text/css\" href=\"/worksheet/boco/ext-all.css\" />" +
			"<link rel=\"stylesheet\" type=\"text/css\" href=\"/worksheet/boco/theme.css\"/>" +
						" <link rel=\"stylesheet\" type=\"text/css\" href=\"/worksheet/boco/xtheme-gray.css\"/>" +
										"</head><body>";
		return head;
	}
	private  String getFooterInfo(){
		String footer="</body></html>";
		return footer;
	}
	/**
	 * 
	 * @param flowInfos
	 * 工单状态：name
	 * 开始时间:startTime
	 * 结束时间:endTime
	 * 操作人:assignee
	 * @return
	 */
	private  String getFlowInfo(List<Map<String, String>> flowInfos){
		
		int size=flowInfos.size();
		String name="",startTime="",endTime="",assignee="";
		
		String middleString="";
		int i=0;
		
		for (Iterator iter = flowInfos.iterator(); iter.hasNext();) {
			Map map = (Map)iter.next();
			name =StaticMethod.nullObject2String(map.get("name"));
			startTime =StaticMethod.nullObject2String(map.get("startTime"));
			endTime =StaticMethod.nullObject2String(map.get("endTime"));
			assignee =StaticMethod.nullObject2String(map.get("assignee"));
			
			if(!"".equals(assignee)){
				assignee = CommonUtils.getId2NameString(assignee, 4, ",");
			}
			
			if(i%2==0){
				middleString+="<tr class=\"even\">";
			}else{
				middleString+="<tr class=\"odd\">";
				
			}
			
			middleString+="<td>"+name+"</td>" +
			"<td>"+startTime+"</td>" +
			"<td>"+endTime+"</td>" +
			"<td>"+assignee+"</td>" +
			"</tr>";
		}
		
		
		
		String flowInfo="<div id=\"content\" class=\"clearfix\">"+
		"<div id=\"main\"><br/><br/>"+
		"<table class=\"formTable\"><caption>流转信息</caption></table>" +
		"<span class=\"pagebanner\">共 "+size+" 条记录。</span>"+
		"<table id=\"historicTasks\" cellpadding=\"0\" class=\"listTable taskList\" cellspacing=\"0\">"+
		"<thead><tr>"+
		"<th class=\"sortable\">工单状态</a></th>"+
		"<th class=\"sortable\">开始时间</a></th>"+
		"<th class=\"sortable\">结束时间</a></th>"+
		"<th class=\"sortable\">操作人</a></th>"+
		"</tr></thead>"+
		"<tbody>"+		
		middleString+
		"</tbody>" +
		"</table></div></div>";
		return flowInfo;
	}
	/**
	 * 
	 * @param doInfos
	 * 
	 * 交通方式:transport
	 * 里程:mileage
	 * 故障是否恢复:isRecover
	 * 填单时间:receiveTime
	 * 处理人:doPersons
	 * 处理描述 :handleDescription
	 * 
	 * @return
	 */
	private  String getTroubleDoHtml(List<Map<String, String>> doInfos){
		
		int size = doInfos.size();
		
		String middleInfo="";
		
		
		int i=1;
		
		for(Iterator iterator =doInfos.iterator();iterator.hasNext();){
			
			Map map = (Map) iterator.next();
			
			middleInfo+="<div class=\"history-item-title\">" +
					i+"、"+map.get("receiveTime")+"&nbsp;"+map.get("receivePerson")+"&nbsp;"+
					"<div/>";
			middleInfo+="<div class=\"history-item-content\">"+
			"<table id=\"sheet\" class=\"formTable\">";
			
			middleInfo+="<tr>"+
				"<td class=\"label\">交通方式</td>"+
				"<td class=\"content\">"+map.get("transport")+"</td>"+		
				"<td class=\"label\">里程</td>"+
				"<td class=\"content\">"+map.get("mileage")+"(单位：公里)</td>"+
				"</tr>"+
				"<tr>"+
				"<td class=\"label\">故障是否恢复</td>"+
				"<td class=\"content\">"+map.get("isRecover")+"</td>"+		
				"<td class=\"label\">填单时间</td>"+
				"<td class=\"content\">"+map.get("receiveTime")+"</td>"+
				"</tr>"+
				"<tr>"+		
				"<td class=\"label\">处理人</td>"+
				"<td class=\"content\">"+map.get("doPersons")+"</td>"+
				"<td class=\"label\">处理描述</td>"+
				"<td class=\"content\">"+map.get("handleDescription")+"</td>"+
				"</tr>";
			
			middleInfo+="</table></div>";
			
			i++;
		}
		
		
		String doInfo="<div style=\"height:20;\"></div>"+
		"<div id=\"history\" class=\"panel\">"+
		"<table class=\"formTable\"><caption>工单处理信息</caption></table>" +
		middleInfo+
		"</div></div>";
		
		
		
		return doInfo;
	}
	/**
	 * 
	 * @param doInfos
	 * 
	 * 故障是否恢复:isRecover
	 * 填单时间:receiveTime
	 * 处理人:doPersons
	 * 处理描述 :handleDescription
	 * 
	 * @return
	 */
	private  String getTransferDoHtml(List<Map<String, String>> doInfos){
		
		int size = doInfos.size();
		
		String middleInfo="";
		
		
		int i=1;
		
		for(Iterator iterator =doInfos.iterator();iterator.hasNext();){
			
			Map map = (Map) iterator.next();
			
			middleInfo+="<div class=\"history-item-title\">" +
			i+"、"+map.get("receiveTime")+"&nbsp;"+map.get("receivePerson")+"&nbsp;"+
			"<div/>";
			middleInfo+="<div class=\"history-item-content\">"+
			"<table id=\"sheet\" class=\"formTable\">";
			
			middleInfo+=
			"<tr>"+
			"<td class=\"label\">故障是否恢复</td>"+
			"<td class=\"content\">"+map.get("isRecover")+"</td>"+		
			"<td class=\"label\">填单时间</td>"+
			"<td class=\"content\">"+map.get("receiveTime")+"</td>"+
			"</tr>"+
			"<tr>"+		
			"<td class=\"label\">处理人</td>"+
			"<td class=\"content\">"+map.get("doPersons")+"</td>"+
			"<td class=\"label\">处理描述</td>"+
			"<td class=\"content\">"+map.get("handleDescription")+"</td>"+
			"</tr>";
			
			middleInfo+="</table></div>";
			
			i++;
		}
		
		
		String doInfo="<div style=\"height:20;\"></div>"+
		"<div id=\"history\" class=\"panel\">"+
		"<table class=\"formTable\"><caption>工单处理信息</caption></table>" +
		middleInfo+
		"</div></div>";
		
		
		
		return doInfo;
	}
	/**
	 * 
	 * @param doInfos
	 * 
	 * 交通方式:transport
	 * 里程:mileage
	 * 填单时间:receiveTime
	 * 处理人:doPersons
	 * 完成情况 :handleDescription
	 * 
	 * @return
	 */
	private  String getTaskDoHtml(List<Map<String, String>> doInfos){
			
		int size = doInfos.size();
		
		String middleInfo="";
				
		int i=1;
		
		for(Iterator iterator =doInfos.iterator();iterator.hasNext();){
			
			Map map = (Map) iterator.next();
			
			middleInfo+="<div class=\"history-item-title\">" +
					i+"、"+map.get("receiveTime")+"&nbsp;"+map.get("taskAssignee")+"&nbsp;"+
					"<div/>";
			middleInfo+="<div class=\"history-item-content\">"+
			"<table id=\"sheet\" class=\"formTable\">";
			
			middleInfo+="<tr>"+
				"<td class=\"label\">交通方式</td>"+
				"<td class=\"content\">"+map.get("transport")+"</td>"+		
				"<td class=\"label\">里程</td>"+
				"<td class=\"content\">"+map.get("mileage")+"(单位：公里)</td>"+
				"</tr>"+
				"<tr>"+	
				"<td class=\"label\">填单时间</td>"+
				"<td class=\"content\">"+map.get("receiveTime")+"</td>"+
				"<td class=\"label\">处理人</td>"+
				"<td class=\"content\">"+map.get("doPersons")+"</td>"+
				"</tr>"+
				"<tr>"+		
				
				"<td class=\"label\">完成情况</td>"+
				"<td class=\"content\" colspan=3>"+map.get("handleDescription")+"</td>"+
				"</tr>";
			
			middleInfo+="</table></div>";
			
			i++;
		}
		
		
		String doInfo="<div style=\"height:20;\"></div>"+
		"<div id=\"history\" class=\"panel\">"+
		"<table class=\"formTable\"><caption>工单处理信息</caption></table>" +
		middleInfo+
		"</div></div>";		
		
		return doInfo;
	}
	
	private  String getTroubleBaseInfo(PnrTroubleTicket pnrTroubleTicket){
		String title=pnrTroubleTicket.getTheme()==null?"":pnrTroubleTicket.getTheme();
		String limit=pnrTroubleTicket.getProcessLimit()==null?"":pnrTroubleTicket.getProcessLimit().toString();
		String site=pnrTroubleTicket.getFailedSite()==null?"":pnrTroubleTicket.getFailedSite();
		String creatTime=pnrTroubleTicket.getCreateTime()==null?"":sFormat.format(pnrTroubleTicket.getCreateTime());
		String specialty=pnrTroubleTicket.getSpecialty()==null?"":pnrTroubleTicket.getSpecialty();
		String isGroup=pnrTroubleTicket.getIsCustomers()==null?"":pnrTroubleTicket.getIsCustomers().toString();
		String type=pnrTroubleTicket.getSubType()==null?"":pnrTroubleTicket.getSubType();
		String connectPerson=pnrTroubleTicket.getConnectPerson()==null?"":pnrTroubleTicket.getConnectPerson();
		String faultDescription=pnrTroubleTicket.getFaultDescription()==null?"":pnrTroubleTicket.getFaultDescription();
		String taskAssignee=pnrTroubleTicket.getTaskAssignee()==null?"":pnrTroubleTicket.getTaskAssignee();
		String processInstanceId=pnrTroubleTicket.getProcessInstanceId()==null?"noprocessInstanceId":pnrTroubleTicket.getProcessInstanceId();
		
		if(!"".equals(specialty)){
			specialty = CommonUtils.getId2NameString(specialty, 1, ",");
		}
		
		if(!"".equals(isGroup)){
			isGroup = CommonUtils.getId2NameString(isGroup, 1, ",");
		}
		
		if(!"".equals(type)){
			type = CommonUtils.getId2NameString(type, 1, ",");
		}
		if(!"".equals(taskAssignee)){
			taskAssignee = CommonUtils.getId2NameString(taskAssignee, 4, ",");
		}
		
		String baseInfo="<div id=\"sheet-detail-page\"><div id=\"sheetinfo\">" +
		"<table class=\"formTable\"><caption>工单基本信息</caption></table>" +
		"<table id=\"sheet\" class=\"formTable\">" +
		"<tr><td class=\"label\">工单主题</td>"+
		"<td colspan=\"3\">"+title+"</td></tr>"+
		
		"<tr><td class=\"label\">故障处理时限（单位：小时）</td>"+
		"<td class=\"content\">"+limit+"</td>"+		
		"<td class=\"label\">故障站点</td>"+
		"<td class=\"content\">"+site+"</td></tr>"+

		"<tr><td class=\"label\">故障发生时间</td>"+
		"<td class=\"content\">"+creatTime+"</td>"+		
		"<td class=\"label\">涉及专业</td>"+
		"<td class=\"content\">"+specialty+"</td></tr>"+

		"<tr><td class=\"label\">是否集客户</td>"+
		"<td class=\"content\">"+isGroup+"</td>"+		
		"<td class=\"label\">工单子类型</td>"+
		"<td class=\"content\">"+type+"</td></tr>"+

		"<tr><td class=\"label\">故障联系人</td>"+
		"<td class=\"content\">"+connectPerson+"</td>"+		
		"<td class=\"label\">故障描述</td>"+
		"<td class=\"content\">"+faultDescription+"</td></tr>"+

		"<tr><td class=\"label\">工单接收人</td>"+
		"<td class=\"content\">"+taskAssignee+"</td>" +
		"<td class=\"label\">照片</td>"+
		"<td class=\"content\"><a href=\""+processInstanceId+".html\" target=\"_blank\">查看<a/></td></tr>" +
		"<table></div></div>";
		
		return baseInfo;
	}
	//传输局页面
	private  String getTransferBaseInfo(PnrTransferOffice pnrTransferOffice){
		String title=pnrTransferOffice.getTheme()==null?"":pnrTransferOffice.getTheme();
		String limit=pnrTransferOffice.getProcessLimit()==null?"":pnrTransferOffice.getProcessLimit().toString();
		String creatTime=pnrTransferOffice.getCreateTime()==null?"":sFormat.format(pnrTransferOffice.getCreateTime());
		String type=pnrTransferOffice.getSubType()==null?"":pnrTransferOffice.getSubType();
		String connectPerson=pnrTransferOffice.getConnectPerson()==null?"":pnrTransferOffice.getConnectPerson();
		String faultDescription=pnrTransferOffice.getFaultDescription()==null?"":pnrTransferOffice.getFaultDescription();
		String taskAssignee=pnrTransferOffice.getTaskAssignee()==null?"":pnrTransferOffice.getTaskAssignee();
		String processInstanceId=pnrTransferOffice.getProcessInstanceId()==null?"noprocessInstanceId":pnrTransferOffice.getProcessInstanceId();
		
		if(!"".equals(type)){
			type = CommonUtils.getId2NameString(type, 1, ",");
		}
		if(!"".equals(taskAssignee)){
			taskAssignee = CommonUtils.getId2NameString(taskAssignee, 4, ",");
		}
		
		String baseInfo="<div id=\"sheet-detail-page\"><div id=\"sheetinfo\">" +
		"<table class=\"formTable\"><caption>工单基本信息</caption></table>" +
		"<table id=\"sheet\" class=\"formTable\">" +
		"<tr><td class=\"label\">工单主题</td>"+
		"<td class=\"content\">"+title+"</td>"+
		"<td class=\"label\">照片</td>"+
		"<td class=\"content\"><a href=\""+processInstanceId+".html\" target=\"_blank\">查看<a/></td></tr>"+
		
		"<tr><td class=\"label\">故障处理时限（单位：小时）</td>"+
		"<td class=\"content\">"+limit+"</td>"+		
		"<td class=\"label\">故障发生时间</td>"+
		"<td class=\"content\">"+creatTime+"</td>"+		
		"</tr>"+
		
		"<tr>"+		
		"<td class=\"label\">工单子类型</td>"+
		"<td class=\"content\">"+type+"</td>"+		
		"<td class=\"label\">故障联系人</td>"+
		"<td class=\"content\">"+connectPerson+"</td></tr>"+
		
		"<tr><td class=\"label\">故障描述</td>"+
		"<td class=\"content\">"+faultDescription+"</td>"+		
		"<td class=\"label\">工单接收人</td>"+
		"<td class=\"content\">"+taskAssignee+"</td></tr>"+
		"<table></div></div>";
		
		return baseInfo;
	}
	
	private  String getTicketBaseInfo(PnrTaskTicket pnrTaskTicket){
		
		String title=pnrTaskTicket.getTheme()==null?"":pnrTaskTicket.getTheme();
		String subType=pnrTaskTicket.getSubType()==null?"":pnrTaskTicket.getSubType();
		String site=pnrTaskTicket.getSite()==null?"":pnrTaskTicket.getSite();
		String startTime=pnrTaskTicket.getStartTime()==null?"":sFormat.format(pnrTaskTicket.getStartTime());
		String endTime=pnrTaskTicket.getEndTime()==null?"":sFormat.format(pnrTaskTicket.getEndTime());
		String specialty=pnrTaskTicket.getSpecialty()==null?"":pnrTaskTicket.getSpecialty();
		String candidateGroup=pnrTaskTicket.getCandidateGroup()==null?"":pnrTaskTicket.getCandidateGroup();
		String content=pnrTaskTicket.getContent()==null?"":pnrTaskTicket.getContent();
		
		if(!"".equals(subType)){
			subType = CommonUtils.getId2NameString(subType, 1, ",");
		}
		if(!"".equals(specialty)){
			specialty = CommonUtils.getId2NameString(specialty, 1, ",");
		}
		if(!"".equals(candidateGroup)){
			candidateGroup = CommonUtils.getId2NameString(candidateGroup, 3, ",");
		}
		
		
		String baseInfo="<div id=\"sheet-detail-page\"><div id=\"sheetinfo\">" +
		"<table class=\"formTable\"><caption>工单基本信息</caption></table>" +
		"<table id=\"sheet\" class=\"formTable\">" +
		"<tr><td class=\"label\">工单主题</td>"+
		"<td colspan=\"3\">"+title+"</td></tr>"+
		
		"<tr><td class=\"label\">工单子类型</td>"+
		"<td class=\"content\">"+subType+"</td>"+		
		"<td class=\"label\">站点</td>"+
		"<td class=\"content\">"+site+"</td></tr>"+
		
		"<tr><td class=\"label\">计划开始时间</td>"+
		"<td class=\"content\">"+startTime+"</td>"+		
		"<td class=\"label\">计划结束时间</td>"+
		"<td class=\"content\">"+endTime+"</td></tr>"+
		
		"<tr><td class=\"label\">涉及专业</td>"+
		"<td class=\"content\">"+specialty+"</td>"+		
		"<td class=\"label\">工单接收部门</td>"+
		"<td class=\"content\">"+candidateGroup+"</td></tr>"+
		
		"<tr><td class=\"label\">工单内容</td>"+
		"<td class=\"content\" colspan=\"3\">"+content+"</td></tr>" +
		"<table></div></div>";
		
		return baseInfo;
	}
	
	/**
	 * 故障工单的照片数据
	 * 
	 */
	private List<PnrAndroidWorkOderPhotoFile> getTroublePhotosDatas(String processInstanceId){
		pnrService  = (IPnrAndroidWorkOderPhotoFileService)ApplicationContextHolder.getInstance().getBean("pnrAndroidWorkOderPhotoFileService");
		Search search = new Search();
	    search.addFilterEqual("picture_id", processInstanceId);
	    search.addSortAsc("createTime");		
		List<PnrAndroidWorkOderPhotoFile> pfiles = pnrService.search(search);
			
		return pfiles;
	}
	/**
	 * 故障照片查看的HTML
	 */
	
	private String getTroublePhotosHtml(List<PnrAndroidWorkOderPhotoFile> pfiles){
		String html="";
		int size = pfiles.size();
		/*html ="<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=gbk\"/><link rel=\"stylesheet\" type=\"text/css\" href=\"../../../../css/typo.css\"/>"+
		"<link rel=\"stylesheet\" type=\"text/css\" href=\"../../../../css/jquery-ui-1.8.14.custom.css\"/>"+
		"</head>"+
		"<script type=\"text/javascript\" charset=\"utf-8\" src=\"../../../../scripts/base/eoms.js\"></script>"+
		"<script type=\"text/javascript\" src=\"../../../../scripts/jquery-1.5.1.min.js\"></script>"+
		"<script type=\"text/javascript\" src=\"../../../../scripts/jquery-ui-1.8.14.custom.min.js\"></script>"+
		"<script type=\"text/javascript\">"+
		"var jq=jQuery.noConflict();var curPage=1;var total=6;"+
		"jq(function(){jq(\"#shoupicture\").hide();jq(\"#close\").click(function(){jq(\"#shoupicture\").hide();});"+
		"jq(\"#up\").click(function(){if(curPage == '' || curPage-0<=1){alert('当前已经是第一张');return;}else{curPage = curPage-1;"+
		"var srcu=document.getElementById(curPage).src;jq(\"#showImg\").html(\"<img width='600' height='480' src='\"+srcu+\"'/>\");"+
		"}});jq(\"#down\").click(function(){if(curPage<total){curPage=curPage-0+1;var srcd=document.getElementById(curPage).src;"+
		"jq(\"#showImg\").html(\"<img width='600' height='480' src='\"+srcd+\"'/>\");}else{alert(\"当前已是最后一张\");return;}});});"+
		"function pictureId(id){curPage = id; jq(this).parent();var srcc=document.getElementById(curPage).src;var height = jq(window).height();var width = jq(window).width();"+
		"jq(\"#shoupicture\").css(\"left\",width/2-400+\"px\").css(\"top\",height/2-300+\"px\");jq(\"#shoupicture\").show();"+
		"jq(\"#showImg\").html(\"<img width='600' height='480' src='\"+srcc+\"'>\");}</script>"+
		"<body><table class=\"table list\" id=\"list\" cellSpacing=\"0\" cellPadding=\"0\">"+
		"<thead id=\"ext-gen9\"><tr><th class=\"sortable\" >照片</th><th class=\"sortable\" >时间</th></tr></thead>"+
		"<tbody>";
		if(size>0){
			
			for(int i=0;i<size;i++){
				if(i%2==0){					
					html+="<tr class=\"even\">";
				}else{					
					html+="<tr class=\"odd\">";
				}
				html+="<td><img src=\""+visitUrl+"/"+pfiles.get(i).getPath()+"\" height=\"20px\" width=\"50px\" onclick=\"pictureId("+(i+1)+")\" id=\""+(i+1)+"\"/></td>"+
				"<td>"+sFormat.format(pfiles.get(i).getCreateTime())+"</td>"+
			    "</tr>";				
			}
			
		}
		html+="</tbody></table>"+
		"<div id=\"comments-info\" class=\"tabContent\"></div>"+
		"<div id=\"shoupicture\" style=\"background: gray; height:480px;width: 600px; position: absolute;z-index: 15;\">"+
		"<table style=\"width: 100%;\">"+
		"<tr>"+
		"<td id=\"photoType\" style=\"width: 15%;text-align: center;color: white;\"></td>"+
		"<td style=\"width: 70%;text-align: center\"><input type=\"button\" class=\"btn\" value=\"上一张\" id=\"up\" /> "+
		"<input type=\"button\" class=\"btn\" value=\"下一张\" id=\"down\" /></td>"+
		"<td style=\"text-align: center;\"><input type=\"button\" value=\"关闭\" id=\"close\" class=\"btn\" ></td></tr></table>"+
		"<div id=\"showImg\" align=\"center\"></div>"+
		"</div><body></html>";*/
		html ="<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=gbk\"/><link rel=\"stylesheet\" type=\"text/css\" href=\"../../../../css/typo.css\"/>"+
		"<link rel=\"stylesheet\" type=\"text/css\" href=\"../../../../css/jquery-ui-1.8.14.custom.css\"/>"+
		"</head>"+
		"<script type=\"text/javascript\" charset=\"utf-8\" src=\"../../../../scripts/base/eoms.js\"></script>"+
		"<script type=\"text/javascript\" src=\"../../../../scripts/jquery-1.5.1.min.js\"></script>"+
		"<script type=\"text/javascript\" src=\"../../../../scripts/jquery-ui-1.8.14.custom.min.js\"></script>"+
		"<script type=\"text/javascript\">"+
		"var jq=jQuery.noConflict();var curPage=1;var total=6;"+
		"jq(function(){jq(\"#shoupicture\").hide();jq(\"#close\").click(function(){jq(\"#shoupicture\").hide();});"+
		"jq(\"#up\").click(function(){if(curPage == '' || curPage-0<=1){alert('当前已经是第一张');return;}else{curPage = curPage-1;"+
		"var srcu=document.getElementById(curPage).src;jq(\"#showImg\").html(\"<img width='600' height='480' src='\"+srcu+\"'/>\");"+
		"}});jq(\"#down\").click(function(){if(curPage<total){curPage=curPage-0+1;var srcd=document.getElementById(curPage).src;"+
		"jq(\"#showImg\").html(\"<img width='600' height='480' src='\"+srcd+\"'/>\");}else{alert(\"当前已是最后一张\");return;}});});"+
		"function pictureId(id){curPage = id; jq(this).parent();var srcc=document.getElementById(curPage).src;var height = jq(window).height();var width = jq(window).width();"+
		"jq(\"#shoupicture\").css(\"left\",width/2-400+\"px\").css(\"top\",height/2-300+\"px\");jq(\"#shoupicture\").show();"+
		"jq(\"#showImg\").html(\"<img width='600' height='480' src='\"+srcc+\"'>\");}</script>"+
		"<body><table class=\"table list\" id=\"list\" cellSpacing=\"0\" cellPadding=\"0\">"+
		"<thead id=\"ext-gen9\"><tr><th class=\"sortable\" >照片</th><th class=\"sortable\" >时间</th><th class=\"sortable\" >经纬度</th></tr></thead>"+
		"<tbody>";
		if(size>0){
			
			for(int i=0;i<size;i++){
				if(i%2==0){					
					html+="<tr class=\"even\">";
				}else{					
					html+="<tr class=\"odd\">";
				}
				
				if(pfiles.get(i).getState()!=null && pfiles.get(i).getState()==2){					
					html+="<td><img src=\""+imgServerUrl+"/"+pfiles.get(i).getImgPath()+"\" height=\"20px\" width=\"50px\" onclick=\"pictureId("+(i+1)+")\" id=\""+(i+1)+"\"/></td>";
				}else{
					html+="<td><img src=\""+visitUrl+"/"+pfiles.get(i).getPath()+"\" height=\"20px\" width=\"50px\" onclick=\"pictureId("+(i+1)+")\" id=\""+(i+1)+"\"/></td>";
					
				}
				
				if(pfiles.get(i).getCreateTime()!=null){
					
					html+="<td>"+sFormat.format(pfiles.get(i).getCreateTime())+"</td>";
				}else{
					
					html+="<td>空</td>";
				}
				html+="<td>"+pfiles.get(i).getLongitude()+";"+pfiles.get(i).getLatitude()+"</td></tr>";				
			}
			
		}
		html+="</tbody></table>"+
		"<div id=\"comments-info\" class=\"tabContent\"></div>"+
		"<div id=\"shoupicture\" style=\"background: gray; height:480px;width: 600px; position: absolute;z-index: 15;\">"+
		"<table style=\"width: 100%;\">"+
		"<tr>"+
		"<td id=\"photoType\" style=\"width: 15%;text-align: center;color: white;\"></td>"+
		"<td style=\"width: 70%;text-align: center\"><input type=\"button\" class=\"btn\" value=\"上一张\" id=\"up\" /> "+
		"<input type=\"button\" class=\"btn\" value=\"下一张\" id=\"down\" /></td>"+
		"<td style=\"text-align: center;\"><input type=\"button\" value=\"关闭\" id=\"close\" class=\"btn\" ></td></tr></table>"+
		"<div id=\"showImg\" align=\"center\"></div>"+
		"</div><body></html>";
		
		return html;
	}
	
}
