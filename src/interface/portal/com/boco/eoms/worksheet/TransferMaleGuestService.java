package com.boco.eoms.worksheet;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.activiti.partner.process.model.PnrTransferOffice;
import com.boco.activiti.partner.process.model.TransferMaleGuestInform;
import com.boco.activiti.partner.process.service.IPnrTransferMaleGuestInformService;
import com.boco.activiti.partner.process.service.IPnrTransferOfficeService;
import com.boco.activiti.partner.process.service.IPnrTroubleTicketService;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.common.log.BocoLog;
import com.boco.eoms.commons.system.area.model.TawSystemArea;
import com.boco.eoms.commons.system.area.service.ITawSystemAreaManager;
import com.boco.eoms.partner.baseinfo.mgr.PartnerUserAndAreaMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerUserAndArea;
import com.boco.eoms.partner.process.util.CommonUtils;
import com.boco.eoms.util.InterfaceUtil;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

/**
 * 传输局公客接口
 * 1.派单方法 newWorkSheet
 * 
 * @author wangyue
 *
 */
public class TransferMaleGuestService {
	
	private DateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	//传输局接口service
	private IPnrTransferOfficeService mgr;
	/**公客通知接口servie*/
	private IPnrTransferMaleGuestInformService maleGuestInformService;
	
	
	
	public String isAlive(String msg) {
		BocoLog.trace("TransferMaleGuestService", 34, msg);
		System.out.println("收到握手信号" + msg);
		msg = "成功接收信息:"+msg;
		return msg;
	}
	
	
	/*****************************************传送工单接口--start******************************************************/
	/**
	 * 新增传输局公客工单
	 * @param msg
	 * @return
	 */
	public String SendNewOrder(String msg){
		InterfaceUtil doc = new InterfaceUtil();
		HashMap sheetMapOpDetail = new HashMap();
		String workOrderNo="";
		BocoLog.info("亿阳提供-传输局公客接口-TransferMaleGuestService-SendNewOrder-派单", 64, "接收-msg:"+msg);

		//解析成xml形式 
		msg =doc.changeCharaterOpposite(msg);
		
		//定义一个传输局公客工单对象
		PnrTransferOffice  maleGuest=null;
		
		
		try{
			maleGuest = getPnrTransferOfficeByMsg(msg,sheetMapOpDetail,doc);
		}catch(Exception e){
			e.printStackTrace();
			BocoLog.error("亿阳提供-传输局公客接口-TransferMaleGuestService-SendNewOrder-派单", 74, "解析出错-msg:"+msg,e );
		}
		if(maleGuest!=null){
			workOrderNo = maleGuest.getMaleGuestNumber();
		}

		// 1解析参数 2发单 3发短信通知 4回执信息
		String errList = workOrderNo;
		if (workOrderNo != null && !"".equals(workOrderNo)){
			try {
				if (maleGuest != null && !"".equals(maleGuest)) {
					
					mgr = (IPnrTransferOfficeService) ApplicationContextHolder
					.getInstance().getBean("pnrTransferOfficeService");
					
					//解析公客工单成功
					//协议中必填项不进行校验、由于公客页面显示需要两个非必填字段--在此对两个字段进行校验，如空则赋值为无
					//装机地址
					String installAddress = maleGuest.getInstallAddress();
					if(installAddress==null ||"".equals(installAddress)){
						maleGuest.setInstallAddress("无");
					}
					
					//障碍业务号码
					String barrierNumber = maleGuest.getBarrierNumber();
					if(barrierNumber==null || "".equals(barrierNumber)){
						maleGuest.setBarrierNumber("无");
					}
					
					//公客标志
					maleGuest.setThemeInterface("maleGuest");
					//工单处理状态
					maleGuest.setState(0);
					//投诉与正常状态的工单加上的两个属性，这里设置默认值。
					maleGuest.setMaleGuestState("0");
					maleGuest.setParentProcessInstanceId("-");
					
					//工单号
					String gkSerialNo = maleGuest.getMaleGuestNumber();
					//判断重障碍字段：空  代表正常派单；1 重修；2 投诉；3 非本传输局责任二次派发；4 即是投诉又是非本传输局责任
					String repeatState = maleGuest.getRepeatState();
					if("1".equals(repeatState)){
						//重修--将原先工单状态改变，并将工单主题改成：重修+工单
						//根据工单号查找之前该工单数据
						List<PnrTransferOffice> oldMaleGuestList = getMaleGuestByNumber(gkSerialNo);
						PnrTransferOffice oldMaleGuest = null;
						if(oldMaleGuestList!=null && oldMaleGuestList.size()>0){
							oldMaleGuest = oldMaleGuestList.get(0);
						}
						if(oldMaleGuest==null || "".equals(oldMaleGuest)){//该工单号不存在重复，直接存数据
							errList="0";
							mgr.performAdd(maleGuest);
						}else{
						//重修工单
							oldMaleGuest.setState(0);
							String theme = oldMaleGuest.getTheme();
							oldMaleGuest.setTheme("(重修)"+theme);
							oldMaleGuest.setRepeatState("1");
							//修改工单--主题和工单状态
							errList="0";
							mgr.updateMaleGuestByWorkOrder(oldMaleGuest,"1");
						}
						
					}else if("2".equals(repeatState)){
						//投诉工单
						String theme = maleGuest.getTheme();
						maleGuest.setTheme("(投诉)"+theme);
						maleGuest.setRepeatState("2");
						errList="0";
						
						//if(CommonUtils.isHaveCountry(maleGuest.getCountry())){
							
							mgr.performAddTogether(maleGuest);//测试区县---济南市中区160411
					   // }else{
						//	mgr.performAdd(maleGuest);
							
					//	}
//						mgr.performAddTogether(maleGuest);
						
					}else if("3".equals(repeatState)){
						//非本传输局责任--二次派单--新增一个标志字段
						List<PnrTransferOffice> list  = getMaleGuestByNumber(gkSerialNo);
						if(list!=null && list.size()>0){
							for(PnrTransferOffice oneMaleGuest :list){
								oneMaleGuest.setDoFlag("1");
								mgr.updateMaleGuestByWorkOrder(oneMaleGuest,"2");
							}
						}
						//将传来的工单--新增一个工单
						errList="0";
						maleGuest.setRepeatState("3");
						mgr.performAdd(maleGuest);
					}else if("4".equals(repeatState)){
						//非本传输局责任
						List<PnrTransferOffice> list  = getMaleGuestByNumber(gkSerialNo);
						if(list!=null && list.size()>0){
							for(PnrTransferOffice oneMaleGuest :list){
								oneMaleGuest.setDoFlag("1");
								mgr.updateMaleGuestByWorkOrder(oneMaleGuest,"2");
							}
						}
						//投诉工单
						String theme = maleGuest.getTheme();
						maleGuest.setTheme("(投诉)"+theme);
						maleGuest.setRepeatState("4");
						errList="0";
						mgr.performAdd(maleGuest);
					}else{
						//正常派单
						if(isExistWorkSheet(workOrderNo)){//重复工单--直接调用回单接口--提示重复信息
							errList="该工单是重复工单，但未填写重修标识！";
							
						}else{
							errList="0";
						//	if(CommonUtils.isHaveCountry(maleGuest.getCountry())){
								
								mgr.performAddTogether(maleGuest);//测试区县---济南市中区160411
						//	}else{
						//		mgr.performAdd(maleGuest);
								
						//	}
						}
					}
				}else{
					errList="没有工单基本信息";
				}
			} 
			catch (Exception e) {
				e.printStackTrace();
				errList = e.toString();
			}
		}
			else{
				errList = "工单编号不能为空";
				BocoLog.trace("亿阳提供-传输局公客接口-TransferMaleGuestService-SendNewOrder-派单", 192,"公客工单号为空了！");
			}
		
		return getReturnXml(errList);
	}
	
	
	// 返回一个传输局公客工单对象
	private PnrTransferOffice getPnrTransferOfficeByMsg(String msg,HashMap sheetMapOpDetail,InterfaceUtil doc ) throws Exception{
		PnrTransferOffice maleGuest = new PnrTransferOffice();
		String xpathOpDetail="//msg/data/opDetail/recordInfo/fieldInfo";
		sheetMapOpDetail = doc.xmlElements(msg, sheetMapOpDetail, "FieldContent",xpathOpDetail);
		
		//公客工单号
		String gkSerialNo = StaticMethod.nullObject2String(sheetMapOpDetail.get("confCRMTicketNo"));
		maleGuest.setMaleGuestNumber(gkSerialNo);
		//工单主题
		String theme = StaticMethod.nullObject2String(sheetMapOpDetail.get("theme"));
		maleGuest.setTheme(theme);
		//故障处理时限 默认处理时限 72小时
		String processLimit = StaticMethod.nullObject2String(sheetMapOpDetail.get("processLimit"));	
		maleGuest.setProcessLimit(Double.parseDouble(Integer.toString(StaticMethod.getIntValue(processLimit,72))));
		//故障站点
		String failedSite =StaticMethod.nullObject2String(sheetMapOpDetail.get("failedSite"));
		maleGuest.setFaileSite(failedSite);
		//故障来源
		String faultSource= StaticMethod.nullObject2String(sheetMapOpDetail.get("faultSource"));
		maleGuest.setFaultSource(faultSource);
		//故障发生时间
		String createTime= StaticMethod.nullObject2String(sheetMapOpDetail.get("createTime"));
		if(!"".equals(createTime)){
			maleGuest.setCreateTime(sFormat.parse(createTime));
		}
		//是否集团客户
		String isCustomers= StaticMethod.nullObject2String(sheetMapOpDetail.get("isCustomers"));
		maleGuest.setIsCustomers(isCustomers);
		//工单子类型
		String subType= StaticMethod.nullObject2String(sheetMapOpDetail.get("subType"));
		maleGuest.setSubType(subType);
		//涉及专业
		String specialty= StaticMethod.nullObject2String(sheetMapOpDetail.get("specialty"));
		maleGuest.setSpecialty(specialty);
		//故障联系人
		String connectPerson= StaticMethod.nullObject2String(sheetMapOpDetail.get("connectPerson"));
		maleGuest.setConnectPerson(connectPerson);
		//机线员
		String dealuserid= StaticMethod.nullObject2String(sheetMapOpDetail.get("dealuserid"));
		maleGuest.setEngineer(dealuserid);
		//装机地址
		String install_addr= StaticMethod.nullObject2String(sheetMapOpDetail.get("install_addr"));
		maleGuest.setInstallAddress(install_addr);
		//接入方式
		String access_type= StaticMethod.nullObject2String(sheetMapOpDetail.get("access_type"));
		maleGuest.setPattern(access_type);
		//账号
		String busi_nbr= StaticMethod.nullObject2String(sheetMapOpDetail.get("busi_nbr"));
		maleGuest.setBusiNbr(busi_nbr);
		//故障描述
		String faultDescription= StaticMethod.nullObject2String(sheetMapOpDetail.get("faultDescription"));
		maleGuest.setFaultDescription(faultDescription);
		//地市
		String cityName= StaticMethod.nullObject2String(sheetMapOpDetail.get("cityName"));
		String conuntryId = getCountryIdByCityMatchTable(cityName);
		if(!"".equals(conuntryId)&& conuntryId.length()>=4){
			cityName = conuntryId.substring(0, 4);
		}
		maleGuest.setCity(cityName);
		maleGuest.setCountry(conuntryId);
		//局站编码
		String site_cd= StaticMethod.nullObject2String(sheetMapOpDetail.get("site_cd"));
		maleGuest.setSiteCd(site_cd);
		//局站名称
		String site_name= StaticMethod.nullObject2String(sheetMapOpDetail.get("site_name"));
		maleGuest.setStationName(site_name);
		//交接箱编码
		String ccp1_cd= StaticMethod.nullObject2String(sheetMapOpDetail.get("ccp1_cd"));
		maleGuest.setCcpCd(ccp1_cd);
		//交接箱名称
		String ccp1_name= StaticMethod.nullObject2String(sheetMapOpDetail.get("ccp1_name"));
		maleGuest.setSpliceBoxName(ccp1_name);
		//主干电缆编码
		String cable_cd= StaticMethod.nullObject2String(sheetMapOpDetail.get("cable_cd"));
		maleGuest.setCableNumber(cable_cd);
		//分线盒编码
		String dp_cd= StaticMethod.nullObject2String(sheetMapOpDetail.get("dp_cd"));
		maleGuest.setBranchBoxNumber(dp_cd);
		//一级分光器编码
		String splitter1_cd= StaticMethod.nullObject2String(sheetMapOpDetail.get("splitter1_cd"));
		maleGuest.setFirstOpticalNumber(splitter1_cd);
		//一级分光器端口
		String splitter1_port= StaticMethod.nullObject2String(sheetMapOpDetail.get("splitter1_port"));
		maleGuest.setFirstOpticalPort(splitter1_port);
		//二级分光器编码
		String splitter2_cd= StaticMethod.nullObject2String(sheetMapOpDetail.get("splitter2_cd"));
		maleGuest.setSecondOpticalNumber(splitter2_cd);
		//二级分光器端口
		String splitter2_port= StaticMethod.nullObject2String(sheetMapOpDetail.get("splitter2_port"));
		maleGuest.setSecondOpticalPort(splitter2_port);
		//光交接箱编码
		String occp_cd= StaticMethod.nullObject2String(sheetMapOpDetail.get("occp_cd"));
		maleGuest.setSpliceBoxNumber(occp_cd);
		//光交接箱端子
		String occp_dz= StaticMethod.nullObject2String(sheetMapOpDetail.get("occp_dz"));
		maleGuest.setSpliceBoxPort(occp_dz);
		//传输局id
		String transferOfficeId= StaticMethod.nullObject2String(sheetMapOpDetail.get("transferOffice_id"));
		maleGuest.setTransferOfficeId(transferOfficeId);
		//外包公司id
		String epiboly= StaticMethod.nullObject2String(sheetMapOpDetail.get("epiboly"));
		maleGuest.setInitiator(epiboly);
		//施工队id
		String taskAssignee= StaticMethod.nullObject2String(sheetMapOpDetail.get("taskAssignee"));
		maleGuest.setTaskAssignee(taskAssignee);
		//接线员
		String operator= StaticMethod.nullObject2String(sheetMapOpDetail.get("operator"));
		maleGuest.setOperator(operator);
		//障碍业务号码
		String barrierNumber = StaticMethod.nullObject2String(sheetMapOpDetail.get("barrierNumber"));
		maleGuest.setBarrierNumber(barrierNumber);
		//重障碍
		String repeatState = StaticMethod.nullObject2String(sheetMapOpDetail.get("repeatState"));
		maleGuest.setRepeatState(repeatState);
		
		return maleGuest;
	}
	
	/**
	 * 判断工单是否已经存在
	 * @param serialNo  工单编号
	 * @return
	 */
	private boolean isExistWorkSheet(String serialNo){
		boolean isExist = true;
		mgr = (IPnrTransferOfficeService) ApplicationContextHolder
		.getInstance().getBean("pnrTransferOfficeService");
		Search search = new Search();
		search.addFilterEqual("maleGuestNumber", serialNo);
		SearchResult<PnrTransferOffice> list = mgr.searchAndCount(search);
		if(list.getTotalCount()==0){
			isExist = false;
		}
		
		return isExist;
	}
	
	/**
	 * 根据工单号查询出之前工单信息--针对重修工单
	 * @param serialNo 工单号
	 * @return
	 */
	@SuppressWarnings("unused")
	private List<PnrTransferOffice> getMaleGuestByNumber(String serialNo){
		mgr = (IPnrTransferOfficeService) ApplicationContextHolder
		.getInstance().getBean("pnrTransferOfficeService");
		Search search = new Search();
		search.addFilterEqual("maleGuestNumber", serialNo);
		List<PnrTransferOffice> list = mgr.search(search);
		return list;
	}
	
	
	/**
	 * 根据地市获得派单人
	 * @param city
	 * @return
	 */
	private PartnerUserAndArea getInitiatorByCity(String city){
		PartnerUserAndAreaMgr  puserArea = (PartnerUserAndAreaMgr) ApplicationContextHolder.getInstance().getBean("partnerUserAndAreaMgr");
		PartnerUserAndArea obj =null ;
		obj = puserArea.getPartnerUserAndAreaByAreaId(city);
		
		return obj;
	}
	
	
	public String getReturnXml(String mag){
		String msg="";
		if("0".equals(mag)){
			msg = "<msg><router><to>SAS</to><msgId>20140718163852693551</msgId>" +
					"<time>2014-07-18 16:38:52</time><serviceName>proWnoOrderByCS</serviceName>" +
					"<from>CS</from>" +
					"</router>" +
					"<data>" +
					"<Return_Code>0</Return_Code>" +
					"<Return_Msg>调用公客接口成功</Return_Msg>" +
					"</data>" +
					"</msg>";
		}else{
			msg="<msg><router><to>SAS</to><msgId>20140718163852693551</msgId>" +
					"<time>2014-07-18 16:38:52</time><serviceName>proWnoOrderByCS</serviceName>" +
					"<from>CS</from>" +
					"</router>" +
					"<data>" +
					"<Return_Code>1</Return_Code>" +
					"<Return_Msg>调用公客接口失败！："+mag+"</Return_Msg>" +
					"</data>" +
					"</msg>";
		}
		return msg;
	}
	/**
	 * 根据地市名称获取地市id
	  * @author wangyue
	  * @title: getAreaIdByAreaName
	  * @date Sep 22, 2014 11:23:13 AM
	  * @param cityName
	  * @return TawSystemArea
	 */
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
	
	//通过区县对照表获取区县ID
	private String getCountryIdByCityMatchTable(String gkCityName){
		String str="";
		IPnrTroubleTicketService mgr = (IPnrTroubleTicketService) ApplicationContextHolder
		.getInstance().getBean("pnrTroubleTicketService");
		Map<String, String> map= mgr.getCityOrCoruntryIdByGkCountryName(gkCityName);
		
		if(map.get("COUNTRY_ID") !=null){
			
			str =map.get("COUNTRY_ID");
		}
		
		return str;
	}
	
	/********************************************传送工单接口--end*************************************************/
	
	/**********************************************工单通知接口*******************************************************/
	
	public String workorderinfor(String msg){
		InterfaceUtil doc = new InterfaceUtil();
		HashMap sheetMapOpDetail = new HashMap();
		String workOrderNo="";
		
		//解析成xml形式 
		msg =doc.changeCharaterOpposite(msg);
		
		//定义一个传输局公客工单通知接口对象
		TransferMaleGuestInform  maleGuestInfrm=null;
		
		try{
			maleGuestInfrm = getmaleGuestInformByMsg(msg,sheetMapOpDetail,doc);
		}catch(Exception e){
			e.printStackTrace();
			BocoLog.error("亿阳提供-传输局公客接口-TransferMaleGuestService-SendNewOrder-工单通知", 448, "解析出错-msg:"+msg,e );
		}
		
		if(maleGuestInfrm!=null){
			workOrderNo=maleGuestInfrm.getMaleGuestId();
		}
		String errList = workOrderNo;
		String flag = "";
		String message = "";
		if(workOrderNo!=null && !"".equals(workOrderNo)){
			//通知内容
			String content = maleGuestInfrm.getFieldContent();
			if(content==null || "".equals(content)){
				errList = errList+";通知内容不能为空";
			}
			//通知类型
			flag = maleGuestInfrm.getFlag();
			if(flag==null || "".equals(flag)){
				errList = errList+";通知类型不能为空";
			}
			Date nowDate;
			try {
				nowDate = sFormat.parse(sFormat.format(new Date()));
				maleGuestInfrm.setCallTime(nowDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(errList.equals(workOrderNo)){
				maleGuestInformService = (IPnrTransferMaleGuestInformService) ApplicationContextHolder
				.getInstance().getBean("pnrTransferMaleGuestInformService");
				
				errList = "0";
				maleGuestInformService.save(maleGuestInfrm);
			}
			
			
			//根据通知类型做出不同的操作
			if("1".equals(flag)){
				//执行自复方法
			}else if("2".equals(flag)){
				//判断被催单的工单是否已经处理完毕：没处理 短信催单；处理过，调用回单接口
				mgr = (IPnrTransferOfficeService) ApplicationContextHolder
				.getInstance().getBean("pnrTransferOfficeService");
				
				String returnMsg = mgr.judgeOrderIsDo(workOrderNo);
				returnMsg = returnMsg.trim();
				if("1".equals(returnMsg)){//工单不存在
					message = "该工单不存在！";
				}else if("2".equals(returnMsg)){//该工单未处理
					//执行催单方法
					mgr.maleGuestReminder(maleGuestInfrm);
					message = "已经催单了！";
				}else if("3".equals(returnMsg)){//该工单已处理,调用回单接口
					//调用回单接口
					String xmlMsg = mgr.doMaleGurstInterface(workOrderNo);
					//判断返回信息，提示不同的反馈信息
					InterfaceUtil util =new  InterfaceUtil();
					List<String> list = util.getReturnXmlMsg(xmlMsg);
					if(list!=null && list.size()>0){
						String doFlag = list.get(0);
						if("0".equals(doFlag.trim())){
							errList = "0";
							message = "成功！";
						}else{
							errList = "1";
							message = "回单接口调用失败！";
							BocoLog.trace("亿阳提供-传输局公客接口-ProCloseWorkorderService--workorderinfor--工单通知--催单", 515, "该次催单公客工单编号为"+workOrderNo+",该工单已处理完，但调用公客回单接口失败！");
						}
					}
				}
			}else if("3".equals(flag)){
				//更改联系电话
			}else if("4".equals(flag)){
				//改约
			}
		}else{
			errList = "工单编号不能为空";
			BocoLog.trace("亿阳提供-传输局公客接口-ProCloseWorkorderService--workorderinfor--工单通知", 526, "工单编号为空了！");
		}
		return getXMLByMsg(errList,flag,message);
	}
	
	
	/**
	 * 根据解析的xml获得公客接口对象
	 * @return
	 */
	public TransferMaleGuestInform  getmaleGuestInformByMsg(String msg,HashMap sheetMapOpDetail,InterfaceUtil doc){
		TransferMaleGuestInform maleGuestInfrm = new TransferMaleGuestInform();
		String xpathOpDetail="//msg/data/opDetail/recordInfo/fieldInfo";
		try {
			sheetMapOpDetail = doc.xmlElements(msg, sheetMapOpDetail, "FieldContent",xpathOpDetail);
			
			//公客工单号
			String confCRMTicketNo = StaticMethod.nullObject2String(sheetMapOpDetail.get("confCRMTicketNo"));
			maleGuestInfrm.setMaleGuestId(confCRMTicketNo);
			//通知内容
			String fieldContent = StaticMethod.nullObject2String(sheetMapOpDetail.get("fieldContent"));
			maleGuestInfrm.setFieldContent(fieldContent);
			//通知类型：1 自复；2 催单;3 更改联系电话 4  改约
			String flag = StaticMethod.nullObject2String(sheetMapOpDetail.get("flag"));
			maleGuestInfrm.setFlag(flag);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return maleGuestInfrm;
	}
	
	public String getXMLByMsg(String msg,String flag,String returnMsg){
		String message ="";
		if("1".equals(flag)){
			message = "自复成功";
		}else if("2".equals(flag)){
			message = returnMsg;
		}else if("3".equals(flag)){
			message = "已经通知更改联系电话了";
		}else if("4".equals(flag)){
			message = "已经通知改约了";
		}
		String result = "0";
		if(!"0".equals(msg)){
			result = "1";
		}
		String mag = "<msg><router><to>WNO</to><msgId>20140721172250617302</msgId>" +
				"<time>2014-07-21 17:22:50</time>" +
				"<serviceName>proCloseWorkorder</serviceName>" +
				"<from>CS</from>" +
				"</router>" +
				"<data>	" +
				"<Return_Code>"+result+"</Return_Code>" +
				"<Return_Msg>"+message+"</Return_Msg>" +
				"</data>" +
				"</msg>";
		
		return mag;
	}
	
	/**
	 * 解析调用回单接口返回的串
	 * @param msg
	 * @param sheetMapOpDetail
	 * @param doc
	 * @return
	 */
	public String[] getReturnXmlMsg(String msg,HashMap sheetMapOpDetail,InterfaceUtil doc){
		String xpathOpDetail="//msg/data";
		String[] msgArray = new String[2]; 
		try {
			sheetMapOpDetail = doc.xmlElements(msg, sheetMapOpDetail, "FieldContent",xpathOpDetail);
			
			//成功标志
			String returnCode = StaticMethod.nullObject2String(sheetMapOpDetail.get("Return_Code"));
			msgArray[0] = returnCode;
			//描述
			String returnMsg = StaticMethod.nullObject2String(sheetMapOpDetail.get("Return_Msg"));
			msgArray[1] = returnMsg;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msgArray;
	}
}
