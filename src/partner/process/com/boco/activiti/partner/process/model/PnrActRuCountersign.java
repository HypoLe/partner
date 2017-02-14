package com.boco.activiti.partner.process.model;

import java.io.Serializable;
/**
 * 会审运行辅助表，表中数据待会审结束删除
 * @author John
 *
 */
public class PnrActRuCountersign  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String processInstanceId;
	private String userId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	public PnrActRuCountersign(){
		
	}
	
}
