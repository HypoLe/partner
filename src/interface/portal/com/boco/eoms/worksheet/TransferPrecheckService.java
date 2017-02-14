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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.boco.activiti.partner.process.model.MasteSelCopyTask;
import com.boco.activiti.partner.process.model.OperationReturnResult;
import com.boco.activiti.partner.process.model.OperationReturnResultDetail;
import com.boco.activiti.partner.process.model.OperationShopConfigModel;
import com.boco.activiti.partner.process.model.PnrAndroidPhotoFile;
import com.boco.activiti.partner.process.model.PnrAndroidWorkOderPhotoFile;
import com.boco.activiti.partner.process.model.PnrTransferOffice;
import com.boco.activiti.partner.process.model.TransferMaleGuestInform;
import com.boco.activiti.partner.process.po.SceneTableModel;
import com.boco.activiti.partner.process.po.TaskModel;
import com.boco.activiti.partner.process.service.IMasteSelCopyTaskService;
import com.boco.activiti.partner.process.service.IOperationReturnResultDetailService;
import com.boco.activiti.partner.process.service.IOperationReturnResultService;
import com.boco.activiti.partner.process.service.IPnrAndroidWorkOderPhotoFileService;
import com.boco.activiti.partner.process.service.IPnrTransferMaleGuestInformService;
import com.boco.activiti.partner.process.service.IPnrTransferNewPrecheckService;
import com.boco.activiti.partner.process.service.IPnrTransferOfficeService;
import com.boco.activiti.partner.process.service.IPnrTransferPrecheckService;
import com.boco.activiti.partner.process.service.IPnrTroubleTicketService;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.FileDownLoad;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.common.log.BocoLog;
import com.boco.eoms.commons.system.area.model.TawSystemArea;
import com.boco.eoms.commons.system.area.service.ITawSystemAreaManager;
import com.boco.eoms.commons.system.dict.exceptions.DictDAOException;
import com.boco.eoms.commons.system.user.dao.hibernate.TawSystemUserDaoHibernate;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcService;
import com.boco.eoms.partner.baseinfo.mgr.PartnerUserAndAreaMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerUserAndArea;
import com.boco.eoms.partner.process.util.CommonUtils;
import com.boco.eoms.util.AttachRef;
import com.boco.eoms.util.InterfaceUtil;
import com.boco.eoms.util.JspToHtml;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;
import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * 传输局公客接口
 * 1.派单方法 newWorkSheet
 * 
 * @author wangyue
 *
 */
public class TransferPrecheckService {
	
	//运维商城接口service
	private OperationShopConfigModel xmlConfigModel=(OperationShopConfigModel)ApplicationContextHolder.getInstance().getBean("operationShopConfigModel");

	private IPnrTransferOfficeService mgr;
	private IPnrTransferOfficeService transferService;
	private final static int TRASFER_DONE_STATE = 5;
	private DateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final String PATH=xmlConfigModel.getVisitHtmlPathPrefix();
	private HistoryService historyService ;
	private final  String baseUrl = xmlConfigModel.getPlaceHtmlPathPrefix();
	private IPnrAndroidWorkOderPhotoFileService pnrService;
	private final  String visitUrl = xmlConfigModel.getVisitProjectUrl();
	private final  String imgServerUrl = xmlConfigModel.getImgServerUrl();
	private IOperationReturnResultService operationReturnResultService;
	private IOperationReturnResultDetailService operationReturnResultDetailService;
	public  final static String TICKETTYPE_TROUBLE="trouble";
	public  final static String TICKETTYPE_TROUBLEDETAIL="troubleDetail";
	
	public String isAlive(String msg) {
		BocoLog.trace("IPnrTransferOfficeService", 34, msg);
		System.out.println("收到握手信号" + msg);
		msg = "成功接收信息:"+msg;
		return msg;
	}
	/**
	 * ①网络商城回填结束后，将工单号、订单号、回填完成时间、订单总金额发送给现场综合维护管理系统。
	 * ②网络商城回填结束后，将故障单号、商品名称、商品规格、商品单价、商品数量、总金额发送给现场综合维护管理系统--明细。
	 * 根据ticketType值判断传递内容（trouble-->①；troubleDetail-->②） 
	 * @param msg
	 * @return  返回内容：0或1；0代表失败，1代表成功
	 * @throws Exception 
	 * @throws Exception
	 */
	public String recordGoods(String msg){
		String value ="0";
		
		InterfaceUtil doc = new InterfaceUtil();
		HashMap map =new HashMap() ;
		String xpathOpDetail="//opDetail/recordInfo/fieldInfo";
		BocoLog.trace("亿阳提供-预检预修接口recordGoods-记录商城产品", 95, "传过来msg-转义前:"+msg);

		msg = doc.changeCharaterOpposite(msg);
		
		BocoLog.trace("亿阳提供-预检预修接口recordGoods-记录商城产品", 95, "传过来msg-转义后:"+msg);

		try {
			map = doc.xmlElements(msg, map, "FieldContent",xpathOpDetail);
		} catch (Exception e) {			
			BocoLog.trace("亿阳提供-预检预修接口recordGoods-记录商城产品", 100, "解析出错msg:"+msg,e);
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
				BocoLog.trace("亿阳提供-预检预修接口recordGoods", 118, "回填日期错误msg:"+msg,e);
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
	/**
	 * 工单校验接口
	 */
	public String isExist(String msg) throws Exception {		
		String value="0";
		InterfaceUtil doc = new InterfaceUtil();
		HashMap map =new HashMap() ;
		String xpathOpDetail="//opDetail/recordInfo/fieldInfo";

		msg = doc.changeCharaterOpposite(msg);
		map = doc.xmlElements(msg, map, "FieldContent",xpathOpDetail);
		String processInstanceId=StaticMethod.nullObject2String(map.get("processInstanceId"));
		//判断是工单号，还是周期领用表编号
		if(!"".equals(processInstanceId)){
			boolean result = InterfaceUtil.isNumeric(processInstanceId);
			if(result){ //工单号
				 PnrTransferOffice pnrTransferOffice = isExistPnrTransferOffice(processInstanceId); //要把公客和非故障抛出掉 还没做？
					if(pnrTransferOffice !=null){
						if(pnrTransferOffice.getState()==TRASFER_DONE_STATE){
							value="1;"+sFormat.format(pnrTransferOffice.getArchivingTime());
						}else if("1".equals(pnrTransferOffice.getIsDistribute())){
							value="3";//已关联
						}else{
							value="2";					
						}
					}
			}else{ //周期领用表报表编号
				//1.判断报表编号是否存在
				mgr = (IPnrTransferOfficeService) ApplicationContextHolder.getInstance().getBean("pnrTransferOfficeService");
				boolean existsCycleReportNumber = mgr.existsCycleReportNumber(processInstanceId);
				if(existsCycleReportNumber){
					//2.判断报表编号是否已经验证过了
					boolean verifiedCycleReportNumber = mgr.isVerifiedCycleReportNumber(processInstanceId);
					if(verifiedCycleReportNumber){
						value="3"; 
					}else{
						//取报表提交时间
						Date submitDate = mgr.getSubmitDateByReportNum(processInstanceId);
						if(submitDate != null){
							value="1;"+sFormat.format(submitDate);
							//插入验证信息
							mgr.insertVerifiedNumber(processInstanceId, "cycle");
						}else{
							value="2"; //代表还未处理完毕
						}
					}
				}
			}
		}
		
		BocoLog.info("亿阳提供-预检预修接口isExist-校验", 175,"返回值是(0代表不存在,否则存在):"+value);
		return value;
	}
	
	/**
	  * 返回url接口
	  * @param msg
	  * @return
	  * @throws Exception
	  */

	public String openShowUrl(String msg) throws Exception{
		InterfaceUtil doc = new InterfaceUtil();		
		String flag="transferPrecheck", processInstanceId="";		
		String url="";
		String head="",baseInfo="",flowInfo="",doInfo="",footer="";
		
		HashMap map =new HashMap() ;
		String xpathOpDetail="//opDetail/recordInfo/fieldInfo";
		msg = doc.changeCharaterOpposite(msg);
		map = doc.xmlElements(msg, map, "FieldContent",xpathOpDetail);
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
				mgr = (IPnrTransferOfficeService) ApplicationContextHolder.getInstance().getBean("pnrTransferOfficeService");
				boolean existsCycleReportNumber = mgr.existsCycleReportNumber(processInstanceId);
				if(existsCycleReportNumber){
					url=visitUrl+"/activiti/pnrTransferMaleGuest/pnrTransferOfficeMaleGuest.do?method=gotoDetailCycleReport&id=961511&processInstanceId="+processInstanceId+"&type=interface&userName=lizhong";
				}else{
					url=PATH+"/error.html";
				}
			}
		}
		
//		String pathUrl=PATH+"/error.html";
//			JspToHtml jspToHtml = new JspToHtml();		
//			head=getHeadInfo();
//			footer=getFooterInfo();			
//				
//				baseInfo=getTransferOfficeBaseInfo(processInstanceId);
////				doInfo=getTransferDoInfo(processInstanceId);
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
//			
//			//生成事前照片的页面
//			List<PnrAndroidPhotoFile> pfiles1=getPicturesAdvance(processInstanceId);
//			String html1=getPicturesAdvanceHtml(pfiles1);
//			
//			jspToHtml.createHtml(html1, processInstanceId+"sqzp");
			
			
       BocoLog.info("亿阳提供-预检预修接口openShowUrl-返回url", 219,"返回值是url:"+url);

		return url;
		
	}
	/*
	 * 返回传输局工单对象
	 */
	private PnrTransferOffice isExistPnrTransferOffice(String processInstanceId){
		PnrTransferOffice pnrTransferOffice=null;
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
	//	search.addFilterEqual("themeInterface", "interface");		
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
	 
	//页面头部
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
	
	//传输局工单
	private String getTransferOfficeBaseInfo(String processInstanceId){
		String transferBaseInfo="";
		transferService = (IPnrTransferOfficeService) ApplicationContextHolder
		.getInstance().getBean("pnrTransferOfficeService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		search.addFilterEqual("themeInterface", "interface");
		search.addSortAsc("sendTime");
//		SearchResult<PnrTransferOffice> pnrTransferOffices = transferService.searchAndCount(search);
//		PnrTransferOffice pnrTransferOffice =null;
//		if(pnrTransferOffices.getTotalCount()>0){
//			
//			pnrTransferOffice = pnrTransferOffices.getResult().get(0);
//			transferBaseInfo = getTransferBaseInfo(pnrTransferOffice);
//		}
		List<PnrTransferOffice> pnrTransferOffices = transferService.getWorkOrderDetails(processInstanceId);
		PnrTransferOffice pnrTransferOffice =null;
		if(pnrTransferOffices.size()>0){
			pnrTransferOffice = pnrTransferOffices.get(0);
			transferBaseInfo = getTransferBaseInfo(pnrTransferOffice);
		}
		
		return transferBaseInfo;
	}
	//传输局页面
	private  String getTransferBaseInfo(PnrTransferOffice pnrTransferOffice){
		String title=pnrTransferOffice.getTheme()==null?"":pnrTransferOffice.getTheme();
		String limit=pnrTransferOffice.getProcessLimit()==null?"":pnrTransferOffice.getProcessLimit().toString();
		String creatTime=pnrTransferOffice.getSubmitApplicationTime()==null?"":sFormat.format(pnrTransferOffice.getSubmitApplicationTime());
		String type=pnrTransferOffice.getSubType()==null?"":pnrTransferOffice.getSubType();
		String connectPerson=pnrTransferOffice.getConnectPerson()==null?"":pnrTransferOffice.getConnectPerson();
		String faultDescription=pnrTransferOffice.getFaultDescription()==null?"":pnrTransferOffice.getFaultDescription();
		String taskAssignee=pnrTransferOffice.getTaskAssignee()==null?"":pnrTransferOffice.getTaskAssignee();
		String processInstanceId=pnrTransferOffice.getProcessInstanceId()==null?"noprocessInstanceId":pnrTransferOffice.getProcessInstanceId();
		String createWork=pnrTransferOffice.getCreateWork()==null?"":pnrTransferOffice.getCreateWork();
		String initiatorDept=pnrTransferOffice.getInitiatorDept()==null?"":pnrTransferOffice.getInitiatorDept();
		String initiatorMobilePhone=pnrTransferOffice.getInitiatorMobilePhone()==null?"":pnrTransferOffice.getInitiatorMobilePhone();
		String workOrderTypeName =pnrTransferOffice.getWorkOrderTypeName()==null?"":pnrTransferOffice.getWorkOrderTypeName();
		String subTypeName=pnrTransferOffice.getSubTypeName()==null?"":pnrTransferOffice.getSubTypeName();
		String bearService=pnrTransferOffice.getBearService()==null?"":pnrTransferOffice.getBearService();
		String workOrderDegree=pnrTransferOffice.getWorkOrderDegree()==null?"":pnrTransferOffice.getWorkOrderDegree();
		String keyWord=pnrTransferOffice.getKeyWord()==null?"":pnrTransferOffice.getKeyWord();
		String deptHead=pnrTransferOffice.getDeptHead()==null?"":pnrTransferOffice.getDeptHead();
		String deptHeadMobilePhone=pnrTransferOffice.getDeptHeadMobilePhone()==null?"":pnrTransferOffice.getDeptHeadMobilePhone();
		String lineName=pnrTransferOffice.getLineName()==null?"":pnrTransferOffice.getLineName();
		String projectStartPoint=pnrTransferOffice.getProjectStartPoint()==null?"":pnrTransferOffice.getProjectStartPoint();
		String projectEndPoint=pnrTransferOffice.getProjectEndPoint()==null?"":pnrTransferOffice.getProjectEndPoint();
		String specificLocation=pnrTransferOffice.getSpecificLocation()==null?"":pnrTransferOffice.getSpecificLocation();
		String workType=pnrTransferOffice.getWorkType()==null?"":pnrTransferOffice.getWorkType();
		Double projectAmount=pnrTransferOffice.getProjectAmount()==null?0:pnrTransferOffice.getProjectAmount();
		Double compensate=pnrTransferOffice.getCompensate()==null?0:pnrTransferOffice.getCompensate();
		Double calculateIncomeRatio=pnrTransferOffice.getCalculateIncomeRatio()==null?0:pnrTransferOffice.getCalculateIncomeRatio();
		String constructionReasons=pnrTransferOffice.getConstructionReasons()==null?"":pnrTransferOffice.getConstructionReasons();
		Double layingCable =pnrTransferOffice.getLayingCable()==null?0:pnrTransferOffice.getLayingCable();
		Double rightingDemolitionPole =pnrTransferOffice.getRightingDemolitionPole()==null?0:pnrTransferOffice.getRightingDemolitionPole();
		Double repairPipeline =pnrTransferOffice.getRepairPipeline()==null?0:pnrTransferOffice.getRepairPipeline();
		Double wireLaying =pnrTransferOffice.getWireLaying()==null?0:pnrTransferOffice.getWireLaying();
		Double excavationTrench =pnrTransferOffice.getExcavationTrench()==null?0:pnrTransferOffice.getExcavationTrench();
		Double fiberOpticCableConnector =pnrTransferOffice.getFiberOpticCableConnector()==null?0:pnrTransferOffice.getFiberOpticCableConnector();
		String mainQuantityOther =pnrTransferOffice.getMainQuantityOther()==null?"":pnrTransferOffice.getMainQuantityOther();
		if(!"".equals(type)){
			type = CommonUtils.getId2NameString(type, 1, ",");
		}
		if(!"".equals(taskAssignee)){
			taskAssignee = CommonUtils.getId2NameString(taskAssignee, 4, ",");
		}
		//工单基本信息
		String baseInfo="<div id=\"sheet-detail-page\"><div id=\"sheetinfo\">" +
		"<table class=\"formTable\"><caption>工单基本信息</caption></table>" +
		"<table id=\"sheet\" class=\"formTable\" style=\"width:95%\">" +
		"<tr><td class=\"label\" style=\"width:10%\">工单主题</td>"+
		"<td class=\"content\" style=\"width:20%\">"+title+"</td>"+
		"<td class=\"label\" style=\"width:10%\">预检预修申请提交时间</td>"+
		"<td class=\"content\" style=\"width:20%\">"+creatTime+"</td>" +
		"<td class=\"label\" style=\"width:10%\">工单发起人</td>"+
		"<td class=\"content\" style=\"width:20%\">"+createWork+"</td>"+	
		"</tr>"+		
		
		
		"<tr>" +
		"<td class=\"label\" style=\"width:10%\">发起人部门</td>"+
		"<td class=\"content\" style=\"width:20%\">"+initiatorDept+"</td>"+		
		"<td class=\"label\" style=\"width:10%\">发起人电话</td>"+
		"<td class=\"content\" style=\"width:20%\">"+initiatorMobilePhone+"</td>"+		
		"<td class=\"label\" style=\"width:10%\">主场景</td>"+
		"<td class=\"content\" style=\"width:20%\">"+workOrderTypeName+"</td>"+		
		"</tr>"+
		
		"<tr><td class=\"label\" style=\"width:10%\">子场景</td>"+
		"<td class=\"content\" style=\"width:20%\">"+subTypeName+"</td>"+		
		"<td class=\"label\" style=\"width:10%\">承载业务级别</td>"+
		"<td class=\"content\" style=\"width:20%\">"+bearService+"</td>"+
		"<td class=\"label\" style=\"width:10%\">紧急程度</td>"+
		"<td class=\"content\" style=\"width:20%\">"+workOrderDegree+"</td>"+		
		"</tr>"+
		
		"<tr>" +
		"<td class=\"label\" style=\"width:10%\">关键字</td>"+
		"<td class=\"content\" style=\"width:20%\">"+keyWord+"</td>"+	
		"<td class=\"label\" style=\"width:10%\">区县维护负责人</td>"+
		"<td class=\"content\" style=\"width:20%\">"+deptHead+"</td>"+		
		"<td class=\"label\" style=\"width:10%\">区县维护负责人电话</td>"+
		"<td class=\"content\" style=\"width:20%\">"+deptHeadMobilePhone+"</td>"+		
		"</tr>"+
		
//		"<tr><td class=\"label\">故障处理时限（单位：小时）</td>"+
//		"<td class=\"content\">"+limit+"</td>"+		
//		"<td class=\"label\">预检预修申请提交时间</td>"+
//		"<td class=\"content\">"+creatTime+"</td>"+		
//		"</tr>"+
		
		
//		"<tr>"+		
//		"<td class=\"label\">工单子类型</td>"+
//		"<td class=\"content\">"+type+"</td>"+		
//		"<td class=\"label\">故障联系人</td>"+
//		"<td class=\"content\">"+connectPerson+"</td></tr>"+
//		
//		"<tr><td class=\"label\">故障描述</td>"+
//		"<td class=\"content\">"+faultDescription+"</td>"+		
//		"<td class=\"label\">工单接收人</td>"+
//		"<td class=\"content\">"+taskAssignee+"</td></tr>"+
		"<table></div></div>";
		
		
		
		//线路信息
		String lineInfo="<div id=\"sheet-detail-page\"><div id=\"sheetinfo\">" +
		"<table class=\"formTable\"><caption>线路信息</caption></table>" +
		"<table id=\"sheet\" class=\"formTable\" style=\"width:95%\">" +
		"<tr><td class=\"label\" style=\"width:10%\">线路名称</td>"+
		"<td class=\"content\" style=\"width:20%\">"+lineName+"</td>"+
		"<td class=\"label\" style=\"width:10%\">项目起点</td>"+
		"<td class=\"content\" style=\"width:20%\">"+projectStartPoint+"</td>" +
		"<td class=\"label\" style=\"width:10%\">项目终点</td>"+
		"<td class=\"content\" style=\"width:20%\">"+projectEndPoint+"</td>"+
		"</tr>"+
		
		"<tr>" +
		"<td class=\"label\" style=\"width:10%\">线路级别</td>"+
		"<td class=\"content\" style=\"width:20%\">"+workType+"</td>"+	
		"<td class=\"label\" style=\"width:10%\">具体地点（标石，杆号，人井号）</td>"+
		"<td class=\"content\" style=\"width:20%\">"+specificLocation+"</td>"+			
		"</tr>"+
		
		"<table></div></div>";
		
		
		
		//金额信息
		String moneyInfo="<div id=\"sheet-detail-page\"><div id=\"sheetinfo\">" +
		"<table class=\"formTable\"><caption>金额信息</caption></table>" +
		"<table id=\"sheet\" class=\"formTable\" style=\"width:95%\">" +
		"<tr><td class=\"label\" style=\"width:10%\">项目金额估算</td>"+
		"<td class=\"content\" style=\"width:20%\">"+projectAmount+"</td>"+
		"<td class=\"label\" style=\"width:10%\">赔补收入</td>"+
		"<td class=\"content\" style=\"width:20%\">"+compensate+"</td>" +
		"<td class=\"label\" style=\"width:10%\">收支比</td>"+
		"<td class=\"content\" style=\"width:20%\">"+calculateIncomeRatio+"</td>"+
		"</tr>"+
		
		"<table></div></div>";
		
		
		
		//项目描述
		String proInfo="<div id=\"sheet-detail-page\"><div id=\"sheetinfo\">" +
		"<table class=\"formTable\"><caption>项目描述</caption></table>" +
		"<table id=\"sheet\" class=\"formTable\" style=\"width:95%\">" +
		"<tr><td class=\"label\">实施原因及必要性</td>"+
		"<td colspan=\"3\" class=\"content\">"+constructionReasons+"</td>"+
		"</tr>"+
		"<tr>"+
		"<td class=\"label\">事前照片</td>"+
		"<td class=\"content\"><a href=\""+processInstanceId+"sqzp.html\" target=\"_blank\">查看<a/></td>" +
		"<td class=\"label\">事中、事后照片</td>"+
		"<td class=\"content\"><a href=\""+processInstanceId+".html\" target=\"_blank\">查看<a/></td>" +
	
		"</tr>"+
		"<table></div></div>";
		
		
		//资源现状
		String resourceInfo="<div id=\"sheet-detail-page\"><div id=\"sheetinfo\">" +
		"<table class=\"formTable\"><caption>资源现状</caption></table>" +
		"<table id=\"sheet\" class=\"formTable\" style=\"width:95%\">" +
		"<tr><td class=\"label\" style=\"width:10%\">光缆</td>"+
		"<td class=\"content\" style=\"width:20%\">"+layingCable+"&nbsp;皮长公里</td>"+
		"<td class=\"label\" style=\"width:10%\">电杆</td>"+
		"<td class=\"content\" style=\"width:20%\">"+rightingDemolitionPole+"&nbsp;颗</td>" +
		"<td class=\"label\" style=\"width:10%\">管道</td>"+
		"<td class=\"content\" style=\"width:20%\">"+repairPipeline+"&nbsp;孔公里</td>"+
		"</tr>"+
		
		
		
		"<tr>" +
		"<td class=\"label\" style=\"width:10%\">钢绞线</td>"+
		"<td class=\"content\" style=\"width:20%\">"+wireLaying+"&nbsp;公里</td>" +
		"<td class=\"label\" style=\"width:10%\">光交接箱</td>"+
		"<td class=\"content\" style=\"width:20%\">"+excavationTrench+"&nbsp;个</td>"+
		"<td class=\"label\" style=\"width:10%\">光分路器箱</td>"+
		"<td class=\"content\" style=\"width:20%\">"+fiberOpticCableConnector+"&nbsp;个</td>" +
		"</tr>"+
		
		
		"<tr><td class=\"label\" style=\"width:10%\">其它</td>"+
		"<td class=\"content\" style=\"width:20%\">"+mainQuantityOther+"</td>"+
		"</tr>"+
		"<table></div></div>";
		
		//获取子场景信息
		String scene =this.getScene(processInstanceId);
		
		baseInfo+=lineInfo+moneyInfo+proInfo+resourceInfo+scene;
		
		
		return baseInfo;
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
		"var jq=jQuery.noConflict();var curPage=1;var total="+size+";"+
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
	
	/****************************** 工单信息传送接口--start *************************************/

	/**
	 * 工单信息传送接口
	 */
	public String preDispatch(String msg){
		InterfaceUtil doc = new InterfaceUtil();
		HashMap sheetMapOpDetail = new HashMap();
		String workOrderNo = "";
		
		//解析成xml形式 
		msg =doc.changeCharaterOpposite(msg);
		
		//定义一个预检预修工单对象
		PnrTransferOffice  precheck=null;
		
		try {
			precheck = getPnrTransferOfficeByMsg(msg,sheetMapOpDetail,doc);
		} catch (Exception e) {
//			e.printStackTrace();
			BocoLog.error("亿阳提供-预检预修接口preDispatch-派发", 600, "解析出错-msg:"+msg,e );
		}
		//缺少详细校验
		if(precheck!=null && precheck.isWorkFlag()){
			workOrderNo = "0";
			mgr = (IPnrTransferOfficeService) ApplicationContextHolder
			.getInstance().getBean("pnrTransferOfficeService");
			String themeInterface = precheck.getThemeInterface();
			if(themeInterface!=null && "interface".equals(themeInterface)){//预检预修工单
				
				//判断版本标志：1 代表新预检预修，空代表旧预检预修；根据不同的版本，调用不同的方法
				if(precheck.getVersionFlag()==null || "".equals(precheck.getVersionFlag())){//旧预检预修工单
					mgr.oldPrecheckAdd(precheck);
				}else if("1".equals(precheck.getVersionFlag().trim())){//新预检预修工单
					mgr.newPrecheckAdd(precheck);
				}else if("2".equals(precheck.getVersionFlag().trim())){
					mgr.threePrecheckAdd(precheck);
				}
			}else if(themeInterface!=null && "overhaul".equals(themeInterface)){
					mgr.overHaulRemakeAdd(precheck);
			}else if(themeInterface!=null && "arteryPrecheck".equals(themeInterface)){
					mgr.arteryPrecheckAdd(precheck);
			}else if(themeInterface!=null && "oltBbuProcess".equals(themeInterface)){
					mgr.oltBbuAdd(precheck);
			}
			
			
			
		}else{
			workOrderNo = "1";
			BocoLog.trace("亿阳提供-预检预修接口preDispatch-派发", 611, "非市公司审批完毕环节或县传输局(COUNTRY_CSJ)不存在");
		}
		
		return getReturnXml(workOrderNo,precheck.getReturnMessage());
	}
	/**
	 * 解析数据返回一个预检预修的工单信息
	  * @author wangyue
	  * @title: getPnrTransferOfficeByMsg
	  * @date Oct 14, 2014 5:46:40 PM
	  * @param msg
	  * @param sheetMapOpDetail
	  * @param doc
	  * @return
	  * @throws Exception PnrTransferOffice
	 */
	private PnrTransferOffice getPnrTransferOfficeByMsg(String msg,HashMap sheetMapOpDetail,InterfaceUtil doc ) throws Exception{
		PnrTransferOffice precheck = new PnrTransferOffice();
		
		String xpathOpDetail="//msg/opDetail/recordInfo/fieldInfo";
		sheetMapOpDetail = doc.xmlElements(msg, sheetMapOpDetail, "FieldContent",xpathOpDetail);
		
		//拟稿人--故障联系人
		String drafter = StaticMethod.nullObject2String(sheetMapOpDetail.get("drafter"));
		precheck.setConnectPerson(drafter);
		//组织
		String organization = StaticMethod.nullObject2String(sheetMapOpDetail.get("organization"));
		//工单号 
		String workOrder = StaticMethod.nullObject2String(sheetMapOpDetail.get("workOrder"));
		//判断工单号在表中是否存在，存在取出该条工单信息
		mgr = (IPnrTransferOfficeService) ApplicationContextHolder
		.getInstance().getBean("pnrTransferOfficeService");
		IPnrTransferPrecheckService pnrTransferPrecheckService = (IPnrTransferPrecheckService) ApplicationContextHolder
		.getInstance().getBean("pnrTransferPrecheckService");
		List<Map> oneTransferOffice= mgr.judgeWorkOrderIsExit(workOrder);
		if(oneTransferOffice!=null && oneTransferOffice.size()>0){
			precheck.setWorkFlag(true);
			
			String country_csj = StaticMethod.nullObject2String(oneTransferOffice.get(0).get("COUNTRY_CSJ"));
			String state = StaticMethod.nullObject2String(oneTransferOffice.get(0).get("STATE"));
			String male_guest_number = StaticMethod.nullObject2String(oneTransferOffice.get(0).get("MALE_GUEST_NUMBER"));
			//取出存在工单的版本标志：1代表新预检预修工单，空代表旧预检预修工单
			String versionFlag = StaticMethod.nullObject2String(oneTransferOffice.get(0).get("VERSION_FLAG"));
			precheck.setVersionFlag(versionFlag);
			String themeInterface = StaticMethod.nullObject2String(oneTransferOffice.get(0).get("THEMEINTERFACE"));
			precheck.setThemeInterface(themeInterface);
			if(!"".equals(country_csj)&&("8".equals(state))&&"".equals(male_guest_number)){
				
				String countryCSJ = oneTransferOffice.get(0).get("COUNTRY_CSJ").toString();
				if(themeInterface!=null && "interface".equals(themeInterface)){
					precheck.setInitiator(countryCSJ);
					precheck.setOneInitiator(countryCSJ);
				}
				precheck.setId(oneTransferOffice.get(0).get("ID").toString());
				precheck.setProcessInstanceId(oneTransferOffice.get(0).get("PROCESS_INSTANCE_ID").toString());
				List<Map> daiWeiGSList = pnrTransferPrecheckService.getDaiWeiSGSByCountryCSJ(countryCSJ);
				if(daiWeiGSList!=null &&daiWeiGSList.size()>0){
					if(daiWeiGSList.get(0).get("DAIWEI_GS_ID")!=null && !"".equals(daiWeiGSList.get(0).get("DAIWEI_GS_ID"))){
						
						precheck.setTaskAssignee(daiWeiGSList.get(0).get("DAIWEI_GS_ID").toString());
						
					}
				}
				precheck.setReturnMessage("操作成功！");
			}else{
				precheck.setWorkFlag(false);
				precheck.setReturnMessage("该工单未处于调用接口状态，操作失败！");
			}
		}else{
			precheck.setWorkFlag(false);
			precheck.setReturnMessage("该工单号不存在，操作失败！");
		}
		String[] areaes = organization.split("/");
		//地市
		String city = "";
		//区县
		String country="";
		if(areaes.length==3 && !"".equals(areaes[2])){
			country = areaes[2];
		}
		if(areaes.length>=2){
			String[] realCity = areaes[1].split("市");
			city= realCity[0];
		}
		//工单来源--默认商城
		precheck.setFaultSource("商城");
		//工单发生时间--默认工单传过来时间
		precheck.setCreateTime(new Date());
		//工单字类型
		//precheck.setSubType("101230601");
		//附件url（多个用#号隔开）
		int count = 0;
		if(msg.indexOf("<attachRef>")!=-1){
			
			InterfaceUtil util =new  InterfaceUtil();
			List<AttachRef> list = util.getAttachRefFromXml(msg);
			//附件下载
			List<Map> dataList = new ArrayList();
			/*if(list!=null && list.size()>0){
				count=list.size();
				for(AttachRef attachref:list){
					System.out.println("attachref.getAttachURL()"+attachref.getAttachURL());
					String strRemoteAddr = attachref.getAttachURL();
					//String physicalPath="/usr/boco/tomcat/imgtemp";
					String physicalPath="/partner/uploadfile/partner/uploadfile/activiti/transferOffice";
					Thread downThread = new Thread(new FileDownLoad(strRemoteAddr,
							physicalPath));
					downThread.start();
					//将下载成功的附件信息存入实体类中
					Map map = new HashMap();
					map.put("url", physicalPath);
					map.put("name", attachref.getAttachURL());
					map.put("length", attachref.getAttachLength());
					dataList.add(map);
				}
				//precheck.setList(dataList);
			}*/
		}
		
		//获取附件名称
		//附件个数
		//precheck.setAttachmentsNum(count);
		//商城中的工单号
		String maleguestNumber = StaticMethod.nullObject2String(sheetMapOpDetail.get("maleGuestNumber"));
		//商场工单号--接口工单号
		precheck.setMaleGuestNumber(maleguestNumber);
		
		//工单标题
		String theme = StaticMethod.nullObject2String(sheetMapOpDetail.get("theme"));
		//precheck.setTheme(theme);
		//工单描述--工单主题+工单详情
		//precheck.setFaultDescription(theme+"工单详情");
		//工单状态--state默认为0
		precheck.setState(0);
		//运维接口标志--interface
		if(precheck.getThemeInterface()!=null && "interface".equals(precheck.getThemeInterface())){
			precheck.setThemeInterface("interface");
		}else if(precheck.getThemeInterface()!=null && "overhaul".equals(precheck.getThemeInterface())){
			precheck.setThemeInterface("overhaul");
		}else if(precheck.getThemeInterface()!=null && "arteryPrecheck".equals(precheck.getThemeInterface())){
			precheck.setThemeInterface("arteryPrecheck");
		}
		
		
		
		return precheck;
	}
	
	
	public String getReturnXml(String msg,String returnMessage){
		if("0".equals(msg)){
			msg = "<msg><router><to>SAS</to><msgId>20140718163852693551</msgId>" +
			"<time>2014-07-18 16:38:52</time><serviceName>proWnoOrderByCS</serviceName>" +
			"<from>CS</from>" +
			"</router>" +
			"<data>" +
			"<Return_Msg>"+returnMessage+"</Return_Msg>" +
			"</data>" +
			"</msg>";
		}else{
			msg="<msg><router><to>SAS</to><msgId>20140718163852693551</msgId>" +
			"<time>2014-07-18 16:38:52</time><serviceName>proWnoOrderByCS</serviceName>" +
			"<from>CS</from>" +
			"</router>" +
			"<data>" +
			"<Return_Code>"+returnMessage+"</Return_Code>" +
			"</data>" +
			"</msg>";
		}
		return msg;
		/*if("0".equals(msg)){
			msg = "<msg><router><to>SAS</to><msgId>"+StaticMethod
    		.getCurrentDateTime("yyyyMMddHHmmss")+StaticMethod.getRandomCharAndNumr(6)+"</msgId>" +
			"<time>"+sFormat.format(new Date())+"</time><serviceName>proWnoOrderByCS</serviceName>" +
			"<from>CS</from>" +
			"</router>" +
			"<data>" +
			"<Return_Code>0</Return_Code>" +
			"<Return_msg>"+returnMessage+"</Return_msg>" +
			"</data>" +
			"<result>" +
			"<code>0</code>" +
			"<desc>"+returnMessage+"</desc>" +
			"</result>" +
			"</msg>";
		}else{
			msg="<msg><router><to>SAS</to><msgId>"+StaticMethod
    		.getCurrentDateTime("yyyyMMddHHmmss")+StaticMethod.getRandomCharAndNumr(6)+"</msgId>" +
			"<time>"+sFormat.format(new Date())+"</time><serviceName>proWnoOrderByCS</serviceName>" +
			"<from>CS</from>" +
			"</router>" +
			"<data>" +
			"<Return_Code>-1</Return_Code>" +
			"<Return_msg>"+returnMessage+"</Return_msg>" +
			"</data>" +
			"<result>" +
			"<code>-1</code>" +
			"<desc>"+returnMessage+"</desc>" +
			"</result>" +
			"</msg>";
		}
		return msg;*/
	}
	
	public String getScene(String processInstanceId){		
		List<SceneTableModel> sceneTableList=new ArrayList<SceneTableModel>();
		
		IMasteSelCopyTaskService masteSelCopyTaskService = (IMasteSelCopyTaskService)ApplicationContextHolder.getInstance().getBean("masteSelCopyTaskService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		search.addSortAsc("seq");
		SearchResult<MasteSelCopyTask> resultTask=masteSelCopyTaskService.searchAndCount(search);
		List<MasteSelCopyTask> resultTaskList =resultTask.getResult();
		
		int js=1;
		
		int num = resultTaskList.size();
		
		String titleInfo="<br><br><table class=\"formTable\"><caption>场景模板-材料清单</caption></table>";
		
		String sceneNameDiv="";
		
		String sceneTable="";
		
		String sceneInfo="";
		String childSceneId="";
		SceneTableModel sceneTableModel =null;
		MasteSelCopyTask  entity=null;
		for(int i=0;i<num;i++){
		
			sceneTableModel=new SceneTableModel();

			entity= resultTaskList.get(i);
			//设置子场景id值
			childSceneId=entity.getChildSceneId();
			sceneTableModel.setChildSceneId(childSceneId);
				
			CommonSpringJdbcService jdbcService = (CommonSpringJdbcService) ApplicationContextHolder.getInstance().getBean("commonSpringJdbcService");
			String searchSql ="select d.class_name,d.thead_name,d.copy_name,d.thead_no,d.type,decode(d.is_quota,'1','YES','NO') is_quota from  maste_copy_thead d  " +
					"where d.copy_no='" +childSceneId+"' order by d.seq";
			List<ListOrderedMap> resultList = jdbcService.queryForList(searchSql);
			
			String childSceneName="";
			int tableHeaderLength=resultList.size();
			String[] tableHeader=new String[tableHeaderLength];  
	
			List  list = new ArrayList();
			int j =0;
			
			for (ListOrderedMap listOrderedMap : resultList) {
				
				tableHeader[j]=(String)listOrderedMap.get("thead_name");
				j++;
				childSceneName =(String)listOrderedMap.get("copy_name");
			}
			j=0;
			
			
			
			//获取需要查找的sql
			String dataSqlConstr ="select r.con_strs,r.task_rel_strs from maste_condition_rel_quota r where r.copy_no='"+childSceneId+"'";
			List<ListOrderedMap> dataConstr = jdbcService.queryForList(dataSqlConstr);
			
			String task_rel_strs ="";
			String con_strs ="";
			for(ListOrderedMap listOrderedMap : dataConstr){
				task_rel_strs = (String)listOrderedMap.get("task_rel_strs");
				con_strs = (String)listOrderedMap.get("con_strs");
			}
			
			String selDataSql = "select "+task_rel_strs.replace("#", ",")+",ITEM_NO from maste_rel_task_item where CHILD_SCENE_ID='"+childSceneId+"' and PROCESS_INSTANCE_ID='"+processInstanceId+"'";
			
			String[] con = con_strs==null?null:con_strs.split("#");
			String newStr ="";
			String lowerStr = "";
			int csize= con==null?0: con.length;
			
			for(int x=0;x<csize;x++){
				System.out.println("执行了:"+csize+"-000"+selDataSql);
				lowerStr = con[x].toLowerCase();
				newStr="(select tbody_name from maste_copy_tbody  where maste_copy_tbody.tbody_no=maste_rel_task_item."+lowerStr+") "+lowerStr;
				selDataSql = selDataSql.replace(lowerStr, newStr);
			}
			System.out.println("000000000selDataSql:"+selDataSql);
			List<ListOrderedMap> dataList = jdbcService.queryForList(selDataSql);
			
			String[] dataFiled = task_rel_strs.split("#"); 
			String[] tr=null;

			String item_no="";
			
			sceneNameDiv="<div style='margin-bottom:10px;'>"+childSceneName+"</div>";
			sceneTable +="<table class='formTable' style='width:95%'>";
			sceneTable +="<tr>";
			for(int y=0;y<tableHeaderLength;y++){
				sceneTable+="<td style='background-color: #f7f7f7;vertical-align: top;'>"+tableHeader[y]+"</td>";
			}
			sceneTable +="</tr>";
			for(ListOrderedMap listOrderedMap : dataList){
				sceneTable+="<tr>"; 
				for(int r=0;r<tableHeaderLength;r++){
					if("MAIN_TYPE".equalsIgnoreCase(dataFiled[r])){
						item_no =(String)listOrderedMap.get("ITEM_NO");
						String mainMaterialInfo="",head="",footer="",path="";
					    try{
					    	 mainMaterialInfo=checkMainMaterialForDetails(item_no, processInstanceId);
					    	 JspToHtml jspToHtml = new JspToHtml();
					    	 head=getHeadInfo();
							 footer=getFooterInfo();
							 String htmlContext=head+mainMaterialInfo+footer;
							 path=jspToHtml.createHtml(htmlContext, item_no+"-"+processInstanceId+"zc"+js);
							 path=path.replace(baseUrl, PATH);
							 sceneTable+="<td><a href=\""+path+"\" target=\"_blank\">查看</a></td>";
							 js+=1;
					    } catch (Exception e){
					    	e.printStackTrace();
					    }
						
					}else if("AUXILIARY_TYPE".equalsIgnoreCase(dataFiled[r])){
						item_no =(String)listOrderedMap.get("ITEM_NO");
						String auxiliaryMaterialInfo="",head="",footer="",path="";
					    try{
					    	 auxiliaryMaterialInfo=checkAssistMaterialForDetails(item_no, processInstanceId);
					    	 JspToHtml jspToHtml = new JspToHtml();
					    	 head=getHeadInfo();
							 footer=getFooterInfo();
							 String htmlContext=head+auxiliaryMaterialInfo+footer;
							 path=jspToHtml.createHtml(htmlContext, item_no+"-"+processInstanceId+"fc"+js);
							 path=path.replace(baseUrl, PATH);
							 sceneTable+="<td><a href=\""+path+"\" target=\"_blank\">查看</a></td>";
							 js+=1;
					    } catch (Exception e){
					    	e.printStackTrace();
					    }
					}else {						
						sceneTable+="<td>"+(String)listOrderedMap.get(dataFiled[r])+"</td>";
					}
				 	
				}
				sceneTable+="</tr>";
			}
			sceneTable+="</table>";
			
			sceneInfo+=sceneNameDiv+sceneTable;
			
			sceneTable="";
			
		}
			
		return titleInfo+sceneInfo;
	}
	
	
	/**
	 * 查看主材
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String checkMainMaterialForDetails(String itemNo,String processInstanceId
			) throws Exception {

		CommonSpringJdbcService jdbcService = (CommonSpringJdbcService) ApplicationContextHolder.getInstance().getBean("commonSpringJdbcService");
		String searchSql ="select d.sort_num,d.name,d.standard,d.unit,k.num," +
				"k.per_price,k.total_price,decode(k.is_whether_old,'NO','否','是') w from maste_data_task k " +
				"left join maste_data d on k.data_id = d.id " +
				"where k.type='1' and k.item_no='"+itemNo+"' and k.process_instance_id='"+processInstanceId+"'" +
						" order by to_number(nvl(d.sort_num,'0')) asc";
		
 		List<ListOrderedMap> resultList = jdbcService.queryForList(searchSql);
		
 		String titleInfo="<table class=\"formTable\"><caption>主材数据</caption></table>";
				
		String mainMaterialTable="";
				
		//设置表头
		int tableHeaderLength=8;
		String[] tableHeader=new String[tableHeaderLength];  
		tableHeader[0]="序号";
		tableHeader[1]="名称";
		tableHeader[2]="规格程式";
		tableHeader[3]="单位";
		tableHeader[4]="数量";
		tableHeader[5]="单价";
		tableHeader[6]="合价";
		tableHeader[7]="材料是否利旧";
		
		mainMaterialTable+="<table class=\"formTable\" style='width:50%'>";
		//设置主材标题行
		mainMaterialTable+="<tr>";
		for(int i=0;i<tableHeaderLength;i++){
			mainMaterialTable+="<td style='background-color: #f7f7f7;vertical-align: top;'>"+tableHeader[i]+"</td>";
		}
		mainMaterialTable+="</tr>";
		//设置数据行
		for(ListOrderedMap listOrderedMap : resultList){
			String num=listOrderedMap.get("num")==null?"0":Double.parseDouble(listOrderedMap.get("num").toString())+"";
			String per_price=listOrderedMap.get("per_price")==null?"0":Double.parseDouble(listOrderedMap.get("per_price").toString())+"";
			String total_price=listOrderedMap.get("total_price")==null?"0":Double.parseDouble(listOrderedMap.get("total_price").toString())+"";
			mainMaterialTable+="<tr>";
			mainMaterialTable+="<td>"+(String)listOrderedMap.get("sort_num")+"</td>";
			mainMaterialTable+="<td>"+(String)listOrderedMap.get("name")+"</td>";
			mainMaterialTable+="<td>"+(String)listOrderedMap.get("standard")+"</td>";
			mainMaterialTable+="<td>"+(String)listOrderedMap.get("unit")+"</td>";
			mainMaterialTable+="<td>"+num+"</td>";
			mainMaterialTable+="<td>"+per_price+"</td>";
			mainMaterialTable+="<td>"+total_price+"</td>";
			mainMaterialTable+="<td>"+listOrderedMap.get("w")+"</td>";
			mainMaterialTable+="</tr>";
		}
		mainMaterialTable+="</table>";
	
		return titleInfo+mainMaterialTable;
	}
	
	/**
	 * 查看辅材
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String checkAssistMaterialForDetails(String itemNo,String processInstanceId
			) throws Exception {
		
		CommonSpringJdbcService jdbcService = (CommonSpringJdbcService) ApplicationContextHolder.getInstance().getBean("commonSpringJdbcService");
		String searchSql ="select d.sort_num,d.name,d.standard,d.unit,k.num," +
				"k.per_price,k.total_price,decode(k.is_whether_old,'NO','否','是') w from maste_data_task k " +
				"left join maste_data d on k.data_id = d.id " +
				"where k.type='0' and k.item_no='"+itemNo+"' and k.process_instance_id='"+processInstanceId+"'" +
						" order by to_number(nvl(d.sort_num,'0')) asc";
		
 		List<ListOrderedMap> resultList = jdbcService.queryForList(searchSql);
		
 		String titleInfo="<table class=\"formTable\"><caption>辅材数据</caption></table>";
		
		String assistMaterialTable="";
		
		List<SceneTableModel> assistMaterialTableList=new ArrayList<SceneTableModel>();
		//设置表头
		int tableHeaderLength=8;
		String[] tableHeader=new String[tableHeaderLength];  
		tableHeader[0]="序号";
		tableHeader[1]="名称";
		tableHeader[2]="规格程式";
		tableHeader[3]="单位";
		tableHeader[4]="数量";
		tableHeader[5]="单价";
		tableHeader[6]="合价";
		tableHeader[7]="材料是否利旧";
		
		assistMaterialTable+="<table class=\"formTable\" style='width:50%'>";
		//设置辅材标题行
		assistMaterialTable+="<tr>";
		for(int i=0;i<tableHeaderLength;i++){
			assistMaterialTable+="<td style='background-color: #f7f7f7;vertical-align: top;'>"+tableHeader[i]+"</td>";
		}
		assistMaterialTable+="</tr>";
		//设置辅材数据行
		for(ListOrderedMap listOrderedMap : resultList){
			String num=listOrderedMap.get("num")==null?"0":Double.parseDouble(listOrderedMap.get("num").toString())+"";
			String per_price=listOrderedMap.get("per_price")==null?"0":Double.parseDouble(listOrderedMap.get("per_price").toString())+"";
			String total_price=listOrderedMap.get("total_price")==null?"0":Double.parseDouble(listOrderedMap.get("total_price").toString())+"";
			assistMaterialTable+="<tr>";
			assistMaterialTable+="<td>"+(String)listOrderedMap.get("sort_num")+"</td>";
			assistMaterialTable+="<td>"+(String)listOrderedMap.get("name")+"</td>";
			assistMaterialTable+="<td>"+(String)listOrderedMap.get("standard")+"</td>";
			assistMaterialTable+="<td>"+(String)listOrderedMap.get("unit")+"</td>";
			assistMaterialTable+="<td>"+num+"</td>";
			assistMaterialTable+="<td>"+per_price+"</td>";
			assistMaterialTable+="<td>"+total_price+"</td>";
			assistMaterialTable+="<td>"+listOrderedMap.get("w")+"</td>";
			assistMaterialTable+="</tr>";
		}
		assistMaterialTable+="</table>";

	
		
		return titleInfo+assistMaterialTable;
	}
	/**
	 * 事前照片查看的HTML
	 * @throws DictDAOException 
	 */
	
	private String getPicturesAdvanceHtml(List<PnrAndroidPhotoFile> pfiles) throws DictDAOException{
		TawSystemUserDaoHibernate dao = (TawSystemUserDaoHibernate) ApplicationContextHolder.getInstance().getBean("tawSystemUserDao");
		String html="";
		String userid="";//拍照人
		int size = pfiles.size();
		html ="<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=gbk\"/><link rel=\"stylesheet\" type=\"text/css\" href=\"../../../../css/typo.css\"/>"+
		"<link rel=\"stylesheet\" type=\"text/css\" href=\"../../../../css/jquery-ui-1.8.14.custom.css\"/>"+
		"</head>"+
		"<script type=\"text/javascript\" charset=\"utf-8\" src=\"../../../../scripts/base/eoms.js\"></script>"+
		"<script type=\"text/javascript\" src=\"../../../../scripts/jquery-1.5.1.min.js\"></script>"+
		"<script type=\"text/javascript\" src=\"../../../../scripts/jquery-ui-1.8.14.custom.min.js\"></script>"+
		"<script type=\"text/javascript\">"+
		"var jq=jQuery.noConflict();var curPage=1;var total="+size+";"+
		"jq(function(){jq(\"#shoupicture\").hide();jq(\"#close\").click(function(){jq(\"#shoupicture\").hide();});"+
		"jq(\"#up\").click(function(){if(curPage == '' || curPage-0<=1){alert('当前已经是第一张');return;}else{curPage = curPage-1;"+
		"var srcu=document.getElementById(curPage).src;jq(\"#showImg\").html(\"<img width='600' height='480' src='\"+srcu+\"'/>\");"+
		"}});jq(\"#down\").click(function(){if(curPage<total){curPage=curPage-0+1;var srcd=document.getElementById(curPage).src;"+
		"jq(\"#showImg\").html(\"<img width='600' height='480' src='\"+srcd+\"'/>\");}else{alert(\"当前已是最后一张\");return;}});});"+
		"function pictureId(id){curPage = id; jq(this).parent();var srcc=document.getElementById(curPage).src;var height = jq(window).height();var width = jq(window).width();"+
		"jq(\"#shoupicture\").css(\"left\",width/2-400+\"px\").css(\"top\",height/2-300+\"px\");jq(\"#shoupicture\").show();"+
		"jq(\"#showImg\").html(\"<img width='600' height='480' src='\"+srcc+\"'>\");}</script>"+
		"<body><table class=\"table list\" id=\"list\" cellSpacing=\"0\" cellPadding=\"0\">"+
		"<thead id=\"ext-gen9\"><tr><th class=\"sortable\" >照片</th><th class=\"sortable\" >拍照时间</th><th class=\"sortable\" >拍照人</th><th class=\"sortable\" >经纬度</th><th class=\"sortable\" >照片地址</th><th class=\"sortable\" >照片描述</th></tr></thead>"+
		"<tbody>";
		if(size>0){
			for(int i=0;i<size;i++){
				if(i%2==0){					
					html+="<tr class=\"even\">";
				}else{					
					html+="<tr class=\"odd\">";
				}
				
				if(pfiles.get(i).getState()!=null && "2".equals(pfiles.get(i).getState())){					
					html+="<td><img src=\""+imgServerUrl+"/"+(String)pfiles.get(i).getPath()+"\" height=\"20px\" width=\"50px\" onclick=\"pictureId("+(i+1)+")\" id=\""+(i+1)+"\"/></td>";
				}else{
					html+="<td><img src=\""+imgServerUrl+"/"+(String)pfiles.get(i).getPath()+"\" height=\"20px\" width=\"50px\" onclick=\"pictureId("+(i+1)+")\" id=\""+(i+1)+"\"/></td>";
					
				}
				
				if(pfiles.get(i).getCreateTime()!=null){
					
					html+="<td>"+pfiles.get(i).getCreateTime()+"</td>";
				}else{
					
					html+="<td>无</td>";
				}
				
				if(pfiles.get(i).getUserId()!=null){
					
					html+="<td>"+dao.id2Name(pfiles.get(i).getUserId())+"</td>";
				}else{
					
					html+="<td>无</td>";
				}
				
				if(pfiles.get(i).getLongitude()!=null){
					html+="<td>"+pfiles.get(i).getLongitude()+";"+pfiles.get(i).getLatitude()+"</td>";				
				}else{
					html+="<td>无</td>";
				}
				if(pfiles.get(i).getFaultLocation()!=null){
					html+="<td>"+pfiles.get(i).getFaultLocation()+"</td>";				
				}else{
					html+="<td>无</td>";
				}
				if(pfiles.get(i).getFaultDescription()!=null){
					html+="<td>"+pfiles.get(i).getFaultDescription()+"</td></tr>";				
				}else{
					html+="<td>无</td></tr>";
				}
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
	/**
	 * 查看事前照片
	 * 
	 */
	private List<PnrAndroidPhotoFile> getPicturesAdvance(String processInstanceId){
		IPnrTransferNewPrecheckService pnrTransferNewPrecheckService = (IPnrTransferNewPrecheckService) ApplicationContextHolder.getInstance().getBean("pnrTransferNewPrecheckService");
		List<PnrAndroidPhotoFile> photoList=new ArrayList <PnrAndroidPhotoFile>();
		photoList= pnrTransferNewPrecheckService
				.showPhotoByProcessInstanceId(processInstanceId);
		return photoList;
	}
	
	/**
	  * 记录商城派发接口
	  * @param msg
	  * @return
	  * @throws Exception
	  */

	public String recordSheet(String msg) throws Exception{
		InterfaceUtil doc = new InterfaceUtil();		
		String processInstanceId="";		
		String outMsg="0";
		HashMap map =new HashMap() ;
		String xpathOpDetail="//opDetail/recordInfo/fieldInfo";
		msg = doc.changeCharaterOpposite(msg);
		map = doc.xmlElements(msg, map, "FieldContent",xpathOpDetail);
		processInstanceId=StaticMethod.nullObject2String(map.get("processInstanceId"));
		
		Search search=new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		transferService = (IPnrTransferOfficeService) ApplicationContextHolder
		.getInstance().getBean("pnrTransferOfficeService");
		
		SearchResult<PnrTransferOffice> list = transferService.searchAndCount(search);
		PnrTransferOffice pnrTransferOffice =null;		
		if(list.getTotalCount()>0){
			pnrTransferOffice = list.getResult().get(0);
			pnrTransferOffice.setIsDistribute("0");
			transferService.save(pnrTransferOffice);
			outMsg="1";
		}else{
			outMsg="0";
		}
						
      BocoLog.info("亿阳提供-记录商城派发接口recordSheet-信息", 219,"返回值:"+outMsg);

		return outMsg;
		
	}
}
