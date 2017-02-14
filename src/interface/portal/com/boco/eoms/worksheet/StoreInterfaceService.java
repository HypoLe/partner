package com.boco.eoms.worksheet;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.activiti.partner.process.model.PnrTransferOffice;
import com.boco.activiti.partner.process.service.IPnrTransferOfficeService;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.FileDownLoad;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.common.log.BocoLog;
import com.boco.eoms.util.AttachRef;
import com.boco.eoms.util.InterfaceUtil;

/**
 * 
  * @author wangyue
  * @ClassName: StoreInterfaceService
  * @Copyright 亿阳信通
  * @date Oct 14, 2014 5:18:30 PM
  * @description 运维商城接口
 */
public class StoreInterfaceService {
	//运维商城接口service
	private IPnrTransferOfficeService mgr;
	
	public String isAlive(String msg) {
		BocoLog.trace("TransferMaleGuestService", 34, msg);
		System.out.println("收到握手信号" + msg);
		msg = "成功接收信息:"+msg;
		return msg;
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
			e.printStackTrace();
		}
		//缺少详细校验
		if(precheck!=null){
			workOrderNo = "0";
			mgr = (IPnrTransferOfficeService) ApplicationContextHolder
			.getInstance().getBean("pnrTransferOfficeService");
			mgr.precheckAdd(precheck);
		}else{
			workOrderNo = "1";
		}
		BocoLog.trace("预检预修工单接口-TransferMaleGuestService", 75, new Date()+"--msg-:"+msg);
		
		
		return getReturnXml(workOrderNo);
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
		//根据组织获取传输分局负责人--派单人--第一派发人
		mgr = (IPnrTransferOfficeService) ApplicationContextHolder
		.getInstance().getBean("pnrTransferOfficeService");
		List<Map> transferOfficeList= mgr.getTransferOfficePerson(city);
		//地市id
		String areaId = "";
		if(transferOfficeList!=null && transferOfficeList.size()>0){
			String userId = transferOfficeList.get(0).get("USERID").toString();
			precheck.setInitiator(userId);
			precheck.setOneInitiator(userId);
			areaId = transferOfficeList.get(0).get("AREAID").toString();
		}
		//根据组织获取外包公司负责人--第二派发人并判断外包负责人人数,多余一个或者没有,将工单派发到市传输局,再由市传输局进行派发
		if(country!=null && !"".equals(country)){
			List<Map> countries = mgr.getEpiboly(areaId, country,"daiwei");
			//该区县只有一个外包负责人
			if(countries!=null && countries.size()==1){
				String userId = countries.get(0).get("USERID").toString();
				precheck.setTaskAssignee(userId);
			List<Map> newTransfer = mgr.getEpiboly(areaId, country, "csj");
			if(newTransfer!=null && newTransfer.size()==1){
				String newTransferOffice = newTransfer.get(0).get("USERID").toString();
				precheck.setInitiator(newTransferOffice);
				precheck.setOneInitiator(newTransferOffice);
			}
			}
		}
		//工单来源--默认商城
		precheck.setFaultSource("商城");
		//工单发生时间--默认工单传过来时间
		precheck.setCreateTime(new Date());
		//工单字类型
		precheck.setSubType("101230601");
		//附件url（多个用#号隔开）
		int count = 0;
		if(msg.indexOf("<attachRef>")!=-1){
			
			InterfaceUtil util =new  InterfaceUtil();
			List<AttachRef> list = util.getAttachRefFromXml(msg);
			//附件下载
			List<Map> dataList = new ArrayList();
			if(list!=null && list.size()>0){
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
				precheck.setList(dataList);
			}
		}
		
		//获取附件名称
		//附件个数
		precheck.setAttachmentsNum(count);
		//商城中的工单号
		String maleguestNumber = StaticMethod.nullObject2String(sheetMapOpDetail.get("maleGuestNumber"));
		//商场工单号--接口工单号
		precheck.setMaleGuestNumber(maleguestNumber);
		
		//工单标题
		String theme = StaticMethod.nullObject2String(sheetMapOpDetail.get("theme"));
		precheck.setTheme(theme);
		//工单描述--工单主题+工单详情
		precheck.setFaultDescription(theme+"工单详情");
		//工单状态--state默认为0
		precheck.setState(0);
		//运维接口标志--interface
		precheck.setThemeInterface("interface");
		
		
		
		return precheck;
	}
	
	
	public String getReturnXml(String msg){
		if("0".equals(msg)){
			msg = "<msg><router><to>SAS</to><msgId>20140718163852693551</msgId>" +
			"<time>2014-07-18 16:38:52</time><serviceName>proWnoOrderByCS</serviceName>" +
			"<from>CS</from>" +
			"</router>" +
			"<data>" +
			"<Return_Msg>操作成功!</Return_Msg>" +
			"</data>" +
			"</msg>";
		}else{
			msg="<msg><router><to>SAS</to><msgId>20140718163852693551</msgId>" +
			"<time>2014-07-18 16:38:52</time><serviceName>proWnoOrderByCS</serviceName>" +
			"<from>CS</from>" +
			"</router>" +
			"<data>" +
			"<Return_Code>操作失败!</Return_Code>" +
			"</data>" +
			"</msg>";
		}
		return msg;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
