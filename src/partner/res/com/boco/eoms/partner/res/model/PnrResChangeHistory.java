package com.boco.eoms.partner.res.model;

import java.util.Date;

/**
 * 资源修改记录
 * @author wangyue
 *
 */
public class PnrResChangeHistory {
	/**主键id*/
	private String id;
	/**修改的资源id*/
	private String resId;
	/**修改时间*/
	private Date changeTime;
	/**操作人帐号*/
	private String changePerson;
	/**修改前资源级别*/
	private String oldLevel;
	/**修改后资源级别*/
	private String newLevel;
	/**修改前资源名称*/
	private String oldName;
	/**修改后资源名称*/
	private String newName;
	/**修改类型:1 仅修改类别;2 仅修改名称;3 修改级别和名称;4 删除资源*/
	private String changeState;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getResId() {
		return resId;
	}
	public void setResId(String resId) {
		this.resId = resId;
	}
	public Date getChangeTime() {
		return changeTime;
	}
	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}
	public String getChangePerson() {
		return changePerson;
	}
	public void setChangePerson(String changePerson) {
		this.changePerson = changePerson;
	}
	public String getOldLevel() {
		return oldLevel;
	}
	public void setOldLevel(String oldLevel) {
		this.oldLevel = oldLevel;
	}
	public String getNewLevel() {
		return newLevel;
	}
	public void setNewLevel(String newLevel) {
		this.newLevel = newLevel;
	}
	public String getOldName() {
		return oldName;
	}
	public void setOldName(String oldName) {
		this.oldName = oldName;
	}
	public String getNewName() {
		return newName;
	}
	public void setNewName(String newName) {
		this.newName = newName;
	}
	public String getChangeState() {
		return changeState;
	}
	public void setChangeState(String changeState) {
		this.changeState = changeState;
	}
	
	
}
