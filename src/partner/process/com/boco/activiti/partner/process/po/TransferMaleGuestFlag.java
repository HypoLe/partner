package com.boco.activiti.partner.process.po;
/**
 * 
  * @author wangyue
  * @ClassName: TransferMaleGuestFlag
  * @Copyright 亿阳信通
  * @date Sep 15, 2014 5:57:00 PM
  * @description 工单状态接收实体类
 */
public class TransferMaleGuestFlag {
	/**主键id*/
	private String id;
	/**工单编号*/
	private String confCRMTicketNo;
	/**地市和处理人*/
	private String workSheetSymbol;
	/**处理人联系方式*/
	private String fieldFlag;
	/**工单状态：待派发；待处理；待审核*/
	private String fieldContent;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getConfCRMTicketNo() {
		return confCRMTicketNo;
	}
	public void setConfCRMTicketNo(String confCRMTicketNo) {
		this.confCRMTicketNo = confCRMTicketNo;
	}
	public String getWorkSheetSymbol() {
		return workSheetSymbol;
	}
	public void setWorkSheetSymbol(String workSheetSymbol) {
		this.workSheetSymbol = workSheetSymbol;
	}
	public String getFieldFlag() {
		return fieldFlag;
	}
	public void setFieldFlag(String fieldFlag) {
		this.fieldFlag = fieldFlag;
	}
	public String getFieldContent() {
		return fieldContent;
	}
	public void setFieldContent(String fieldContent) {
		this.fieldContent = fieldContent;
	}
	
	
}
