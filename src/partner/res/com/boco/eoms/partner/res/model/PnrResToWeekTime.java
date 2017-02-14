package com.boco.eoms.partner.res.model;

import java.io.Serializable;

/**
 * 巡检专业-》资源类别-》资源级别 自动指定 周期
 * @author chenbing
 *
 */
public class PnrResToWeekTime implements Serializable {
	/**
	 * id     表主键
	 * weekID 为周期ID
	 * pnrID  为资源ID
	 */
	private String id;
	private String weekID;
	private String pnrID;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getWeekID() {
		return weekID;
	}
	public void setWeekID(String weekID) {
		this.weekID = weekID;
	}
	public String getPnrID() {
		return pnrID;
	}
	public void setPnrID(String pnrID) {
		this.pnrID = pnrID;
	}
	public PnrResToWeekTime() {
		
	}
	
}
