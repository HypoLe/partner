package com.boco.eoms.partner.inspect.model;

import java.io.Serializable;

/**
 * 按代维公司进行统计任务的完成情况
 * @author liaojiming
 *
 */
public class InspectPlanGisPnr implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String pnrDept;				   //代维部门
	private String pnrId;				   //代维公司
	private Integer resNum;				   //资源总数
	private Integer resDoneNum;            //已完成数
	public String getPnrDept() {
		return pnrDept;
	}
	public void setPnrDept(String pnrDept) {
		this.pnrDept = pnrDept;
	}
	public Integer getResNum() {
		return resNum;
	}
	public void setResNum(Integer resNum) {
		this.resNum = resNum;
	}
	public Integer getResDoneNum() {
		return resDoneNum;
	}
	public void setResDoneNum(Integer resDoneNum) {
		this.resDoneNum = resDoneNum;
	}
	public String getPnrId() {
		return pnrId;
	}
	public void setPnrId(String pnrId) {
		this.pnrId = pnrId;
	}
	
	
	
}
