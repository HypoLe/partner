package com.boco.eoms.worksheet;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.boco.activiti.partner.process.model.PnrTransferOffice;
import com.boco.activiti.partner.process.model.TransferMaleGuestInform;
import com.boco.activiti.partner.process.service.IPnrTransferMaleGuestInformService;
import com.boco.activiti.partner.process.service.IPnrTransferOfficeService;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.common.log.BocoLog;
import com.boco.eoms.util.InterfaceUtil;

/**
 * 2.2.2 工单通知接口
 * @author wangyue
 *
 */
public class ProCloseWorkorderService {
	private DateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**公客通知接口servie*/
	private IPnrTransferMaleGuestInformService maleGuestInformService;
	

	public String isAlive(String msg) {
		BocoLog.trace("ProCloseWorkorderService", 34, msg);
		System.out.println("收到握手信号" + msg);
		msg = "成功接收信息:"+msg;
		return msg;
	}
	
	
	public String workorderinfor(String msg){
		InterfaceUtil doc = new InterfaceUtil();
		HashMap sheetMapOpDetail = new HashMap();
		String workOrderNo="";
		
		//解析成xml形式 
		msg =doc.changeCharaterOpposite(msg);
		
		//定义一个传输局公客工单通知接口对象
		TransferMaleGuestInform  maleGuestInfrm=null;
		
		maleGuestInfrm = getmaleGuestInformByMsg(msg,sheetMapOpDetail,doc);
		if(maleGuestInfrm!=null){
			workOrderNo=maleGuestInfrm.getMaleGuestId();
		}
		BocoLog.trace("派单通知接口-ProCloseWorkorderService", 75, new Date()+"--msg-:"+msg);
		
		String errList = workOrderNo;
		String flag = "";
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
				//执行催单方法
			}else if("3".equals(flag)){
				//更改联系电话
			}else if("4".equals(flag)){
				//改约
			}
		}else{
			errList = "工单编号不能为空";
		}
		return getXMLByMsg(errList,flag);
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
	
	public String getXMLByMsg(String msg,String flag){
		String message ="";
		if("1".equals(flag)){
			message = "自复成功";
		}else if("2".equals(flag)){
			message = "已经催单了";
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
}
