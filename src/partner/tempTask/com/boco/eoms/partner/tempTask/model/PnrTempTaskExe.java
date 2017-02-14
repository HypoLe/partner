package com.boco.eoms.partner.tempTask.model;

import com.boco.eoms.base.model.BaseObject;
import com.boco.eoms.base.util.StaticMethod;

/**
 * <p>
 * Title:合作伙伴临时任务执行
 * </p>
 * <p>
 * Description:合作伙伴临时任务管理主表信息
 * </p>
 * <p>
 * Mon Apr 26 14:09:01 CST 2010
 * </p>
 * 
 * @author daizhigang
 * @version 1.0
 * 
 */
public class PnrTempTaskExe extends BaseObject {

	/**
	 * 主键
	 */
	private String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 *
	 * 临时任务main表id
	 *
	 */
	private java.lang.String tempTaskId;
   
	public void setTempTaskId(java.lang.String tempTaskId){
		this.tempTaskId= tempTaskId;       
	}
   
	public java.lang.String getTempTaskId(){
		return this.tempTaskId;
	}

	/**
	 *
	 * 工作任务id
	 *
	 */
	private java.lang.String workId;
   
	public void setWorkId(java.lang.String workId){
		this.workId= workId;       
	}
   
	public java.lang.String getWorkId(){
		return this.workId;
	}

	/**
	 *
	 * 开始时间
	 *
	 */
	private java.sql.Timestamp exeStartTime;
   
	public void setExeStartTime(java.sql.Timestamp exeStartTime){
		this.exeStartTime= exeStartTime;       
	}
   
	public java.sql.Timestamp getExeStartTime(){
		return this.exeStartTime;
	}
	
	private java.lang.String exeStartTimeStr;
	
	public java.lang.String getExeStartTimeStr(){
		exeStartTimeStr = StaticMethod.getTimestampString(this.exeStartTime);
		return exeStartTimeStr;
	}

	/**
	 *
	 * 结束时间(暂时不用)
	 *
	 */
	private java.sql.Timestamp exeEndTime;
   
	public void setExeEndTime(java.sql.Timestamp exeEndTime){
		this.exeEndTime= exeEndTime;       
	}
   
	public java.sql.Timestamp getExeEndTime(){
		return this.exeEndTime;
	}
	
	private java.lang.String exeEndTimeStr;
	
	public java.lang.String getExeEndTimeStr(){
		exeEndTimeStr = StaticMethod.getTimestampString(this.exeEndTime);
		return exeEndTimeStr;
	}
	/**
	 *
	 * 工作内容描述
	 *
	 */
	private java.lang.String exeContent;
   
	public void setExeContent(java.lang.String exeContent){
		this.exeContent= exeContent;       
	}
   
	public java.lang.String getExeContent(){
		return this.exeContent;
	}
	/**
	 *
	 * 附件
	 *
	 */
	private java.lang.String accessoriesId;

	public java.lang.String getAccessoriesId() {
		return accessoriesId;
	}

	public void setAccessoriesId(java.lang.String accessoriesId) {
		this.accessoriesId = accessoriesId;
	}

	public boolean equals(Object o) {
		if( o instanceof PnrTempTaskWork ) {
			PnrTempTaskWork pnrTempTaskWork=(PnrTempTaskWork)o;
			if (this.id != null || this.id.equals(pnrTempTaskWork.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}