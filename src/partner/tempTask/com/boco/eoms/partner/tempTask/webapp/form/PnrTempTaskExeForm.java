package com.boco.eoms.partner.tempTask.webapp.form;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.form.BaseForm;

/**
 * <p>
 * Title:合作伙伴工作执行管理
 * </p>
 * <p>
 * Description:合作伙伴临时任务执行信息
 * </p>
 * <p>
 * Mon Apr 26 14:09:01 CST 2010
 * </p>
 * 
 * @moudle.getAuthor() daizhigang
 * @moudle.getVersion() 1.0
 * 
 */
public class PnrTempTaskExeForm extends BaseForm implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

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
	private java.lang.String exeStartTime;
   
	public void setExeStartTime(java.lang.String exeStartTime){
		this.exeStartTime= exeStartTime;       
	}
   
	public java.lang.String getExeStartTime(){
		return this.exeStartTime;
	}


	/**
	 *
	 * 结束时间(暂时不用)
	 *
	 */
	private java.lang.String exeEndTime;
   
	public void setExeEndTime(java.lang.String exeEndTime){
		this.exeEndTime= exeEndTime;       
	}
   
	public java.lang.String getExeEndTime(){
		return this.exeEndTime;
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

}