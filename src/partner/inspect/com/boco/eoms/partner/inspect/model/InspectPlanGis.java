package com.boco.eoms.partner.inspect.model;

import java.io.Serializable;

/**
 * 按地市进行统计任务的完成情况
 * @author liaojiming
 *
 */
public class InspectPlanGis implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String city;
	private Integer resNum;				   //资源总数
	private Integer resDoneNum;            //已完成数
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
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
	
	
}
