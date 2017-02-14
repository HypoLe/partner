package com.boco.eoms.util;

import com.boco.activiti.partner.process.po.TransferMaleGuestFlag;

/**
 * 用于调用公客接口的xml
 * @author wangyue
 *
 */
public class GetXMLToCallInterface {
	
	/**
	 * 获得工单状态接收XML
	 * @return
	 */
	public String getXMLToMaleGuestFlag(TransferMaleGuestFlag maleGuest){
		
		
		String msg = "<msg>" +
		"<router>" +
		"<to>WNO</to>" +
		"<msgId>20140721172250617302</msgId>" +
		"<time>2014-07-21 17:22:50</time>" +
		"<serviceName>startInvoke</serviceName>" +
		"<from>CS</from>" +
		"</router>" +
		"<data>" +
		"<confCRMTicketNo>"+maleGuest.getConfCRMTicketNo()+"</confCRMTicketNo>" +
		"<WorkSheetSymbol>"+maleGuest.getWorkSheetSymbol()+"</WorkSheetSymbol>" +
		"<fieldFlag>"+maleGuest.getFieldFlag()+"</fieldFlag>" +
		"<fieldContent>"+maleGuest.getFieldContent()+"</fieldContent>" +
		"</data>" +
		"</msg>";
		return msg;
	}
	
	public String getXMLToMaleGuestReturn(String workOrderNo){
		if(workOrderNo!=null&&!"".equals(workOrderNo)){
			//障碍原因编码
			
			//障碍原因
			
			//回单人工号
			
			//回单人姓名
			
			//回单时间
			
			//回单标示
			
			//回单描述
		}
		
		String msg="<msg>" +
		"<router>" +
		"<to>WNO</to>" +
		"<msgId>20140721172250617302</msgId>" +
		"<time>2014-07-21 17:22:50</time>" +
		"<serviceName>startInvoke</serviceName>" +
		"<from>CS</from>" +
		"</router>" +
		"<data>	" +
		"<confCRMTicketNo>工单编号</confCRMTicketNo>" +
		"<Reason_id>障碍原因编码</Reason_id>" +
		"<Reason_name>障碍原因</Reason_name>" +
		"<Back_userid>回单人工号</Back_userid>" +
		"<Back_username>回单人姓名</Back_username>" +
		"<Back_dt>回单时间</Back_dt>" +
		"<flag>回单标示</flag>" +
		"<Back_info>回单描述</Back_info>" +
		"</data>" +
		"</msg>";
		
		return msg;
	}
}
