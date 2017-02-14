package com.boco.activiti.partner.process.po;
/**
 * 
  * @author wangyue
  * @ClassName: TransferMaleGuestReturn
  * @Copyright 亿阳信通
  * @date Sep 16, 2014 9:50:20 AM
  * @description 工单回单接口实体类
 */
public class TransferMaleGuestReturn {
	/**工单编号*/
	private String confCRMTicketNo;
	/**障碍原因编码*/
	private String Reason_id;
	/**障碍原因*/
	private String Reason_name;
	/**处理人id*/
	private String Back_userid;
	/**处理人名称*/
	private String Back_username;
	/**回单时间*/
	private String Back_dt;
	/**回单标志：1 障碍处理成功；2 障碍非当前分局责任3 障碍非传输局责任*/
	private String flag;
	/**回单描述*/
	private String Back_info;
	public String getConfCRMTicketNo() {
		return confCRMTicketNo;
	}
	public void setConfCRMTicketNo(String confCRMTicketNo) {
		this.confCRMTicketNo = confCRMTicketNo;
	}
	public String getReason_id() {
		return Reason_id;
	}
	public void setReason_id(String reason_id) {
		Reason_id = reason_id;
	}
	public String getReason_name() {
		return Reason_name;
	}
	public void setReason_name(String reason_name) {
		Reason_name = reason_name;
	}
	public String getBack_userid() {
		return Back_userid;
	}
	public void setBack_userid(String back_userid) {
		Back_userid = back_userid;
	}
	public String getBack_username() {
		return Back_username;
	}
	public void setBack_username(String back_username) {
		Back_username = back_username;
	}
	public String getBack_dt() {
		return Back_dt;
	}
	public void setBack_dt(String back_dt) {
		Back_dt = back_dt;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getBack_info() {
		return Back_info;
	}
	public void setBack_info(String back_info) {
		Back_info = back_info;
	}
	
	
}
