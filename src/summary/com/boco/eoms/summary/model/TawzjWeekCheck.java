package com.boco.eoms.summary.model;

import com.boco.eoms.base.model.BaseObject;

/**
 * @author 龚玉峰
 *
 */
public class TawzjWeekCheck extends BaseObject{
  
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id ;  // id
	
	private String weekid; // 周id
	
	
	private String sender;  // 送审人
	
	private String auditer; // 审核人
	
	private String state;   // 状态
	
	private String flag ;  // 几级审核
	
	private String opinion  ;  //审核意见
	
	
	public String getAuditer() {
		return auditer;
	}


	public void setAuditer(String auditer) {
		this.auditer = auditer;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}

 


	public String getOpinion() {
		return opinion;
	}


	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}


	public String getSender() {
		return sender;
	}


	public void setSender(String sender) {
		this.sender = sender;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getWeekid() {
		return weekid;
	}


	public void setWeekid(String weekid) {
		this.weekid = weekid;
	}


	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		return false;
	}


	public String getFlag() {
		return flag;
	}


	public void setFlag(String flag) {
		this.flag = flag;
	}

}
