package com.boco.eoms.partner.baseinfo.model;


/**  
 * @Title: PnrResConfigAmount.java
 * @Package com.boco.eoms.partner.baseinfo.model
 * @Description: XXX
 * @author fengguangping fengguangping@boco.com.cn
 * @date Jan 23, 2013  3:34:00 PM  
 */
public class PnrResConfigAmount {
	/**主键id**/
	private String id;
	
	/**代维专业**/
	private String professional;
	
	/**代维公司deptMagId**/
	private String deptMagId;
	
	/**资源数量**/
	private double amount;
	
	/**时间年**/
	private int timeY;
	
	/**时间月**/
	private int timeM;
	
	/**添加时间**/
	private String addTime;
	
	/**备注**/
	private String remark;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProfessional() {
		return professional;
	}
	public void setProfessional(String professional) {
		this.professional = professional;
	}
	public String getDeptMagId() {
		return deptMagId;
	}
	public void setDeptMagId(String deptMagId) {
		this.deptMagId = deptMagId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public int getTimeY() {
		return timeY;
	}
	public void setTimeY(int timeY) {
		this.timeY = timeY;
	}
	public int getTimeM() {
		return timeM;
	}
	public void setTimeM(int timeM) {
		this.timeM = timeM;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
