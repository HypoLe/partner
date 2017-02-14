package com.boco.activiti.partner.process.model;

import java.util.Date;

/**
 * 公客工单通知实体类
 * @author wangyue
 *
 */
public class TransferMaleGuestInform {
	/**主键id*/
	private String id;
	/**公客工单编号*/
	private String maleGuestId;
	/**通知内容*/
	private String fieldContent;
	/**通知类型:1 自复;2 催单;3 更改联系方式；4 改约*/
	private String flag;
	/**调用接口时间*/
	private Date callTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMaleGuestId() {
		return maleGuestId;
	}
	public void setMaleGuestId(String maleGuestId) {
		this.maleGuestId = maleGuestId;
	}
	public String getFieldContent() {
		return fieldContent;
	}
	public void setFieldContent(String fieldContent) {
		this.fieldContent = fieldContent;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public Date getCallTime() {
		return callTime;
	}
	public void setCallTime(Date callTime) {
		this.callTime = callTime;
	}
	
	
}
