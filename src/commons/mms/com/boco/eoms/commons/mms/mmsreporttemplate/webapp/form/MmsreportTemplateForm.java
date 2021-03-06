package com.boco.eoms.commons.mms.mmsreporttemplate.webapp.form;

import com.boco.eoms.base.webapp.form.BaseForm;

/**
 * <p>
 * Title:彩信报模板
 * </p>
 * <p>
 * Description:彩信报系统
 * </p>
 * <p>
 * Tue Feb 17 14:50:50 CST 2009
 * </p>
 * 
 * @moudle.getAuthor() 李志刚
 * @moudle.getVersion() 3.5
 * 
 */
public class MmsreportTemplateForm extends BaseForm implements java.io.Serializable {

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
	 * 彩信报名称
	 *
	 */
	private java.lang.String mmsName;
   
	public void setMmsName(java.lang.String mmsName){
		this.mmsName= mmsName;       
	}
   
	public java.lang.String getMmsName(){
		return this.mmsName;
	}

	/**
	 *
	 * 彩信报表说明
	 *
	 */
	private java.lang.String mmsReportDesc;
   
	public void setMmsReportDesc(java.lang.String mmsReportDesc){
		this.mmsReportDesc= mmsReportDesc;       
	}
   
	public java.lang.String getMmsReportDesc(){
		return this.mmsReportDesc;
	}

	/**
	 *
	 * 执行周期
	 *
	 */
	private java.lang.String executeCycle;
   
	public void setExecuteCycle(java.lang.String executeCycle){
		this.executeCycle= executeCycle;       
	}
   
	public java.lang.String getExecuteCycle(){
		return this.executeCycle;
	}

	/**
	 *
	 * 报表呈现类型
	 *
	 */
	private java.lang.String reportDisplayType;
   
	public void setReportDisplayType(java.lang.String reportDisplayType){
		this.reportDisplayType= reportDisplayType;       
	}
   
	public java.lang.String getReportDisplayType(){
		return this.reportDisplayType;
	}

	/**
	 *
	 * 报表生成时间点
	 *
	 */
	private java.lang.String reportCreatDate;
   
	public void setReportCreatDate(java.lang.String reportCreatDate){
		this.reportCreatDate= reportCreatDate;       
	}
   
	public java.lang.String getReportCreatDate(){
		return this.reportCreatDate;
	}

	/**
	 *
	 * 报表生成图片类型
	 *
	 */
	private java.lang.String pictureFormat;
   
	public void setPictureFormat(java.lang.String pictureFormat){
		this.pictureFormat= pictureFormat;       
	}
   
	public java.lang.String getPictureFormat(){
		return this.pictureFormat;
	}

	/**
	 *
	 * 统计报表id 需要一个xml配置文件，获取id，确定报表使用的excel
	 *
	 */
	private java.lang.String statReportId;
   
	public void setStatReportId(java.lang.String statReportId){
		this.statReportId= statReportId;       
	}
   
	public java.lang.String getStatReportId(){
		return this.statReportId;
	}
	private java.lang.String[] statReportIds;
	   
	public void setStatReportId(java.lang.String[] statReportIds){
		this.statReportIds= statReportIds;       
	}
   
	public java.lang.String[] getStatReportIds(){
		return this.statReportIds;
	}
	
	private java.lang.String statReportType;
	
	public java.lang.String getStatReportType() {
		return statReportType;
	}

	public void setStatReportType(java.lang.String statReportType) {
		this.statReportType = statReportType;
	}
	
	/**
	 *
	 * 信息发布类别id
	 *
	 */
	private java.lang.String froumid;
   
	public void setFroumid(java.lang.String froumid){
		this.froumid= froumid;       
	}
   
	public java.lang.String getFroumid(){
		return this.froumid;
	}
	/**
	 * 彩信报创建人
	 * 
	 */
	private java.lang.String userid;
	   
	public void setUserid(java.lang.String userid){
		this.userid= userid;       
	}
   
	public java.lang.String getUserid(){
		return this.userid;
	}
	/**
	 *
	 * 信息发布信息id
	 *
	 */
	private java.lang.String threadid;
   
	public void setThreadid(java.lang.String threadid){
		this.threadid= threadid;       
	}
   
	public java.lang.String getThreadid(){
		return this.threadid;
	}
	/**
	 * 
	 * 对应轮询表中的subid
	 * 
	 */
	private java.lang.String jobid;
	
	public void setJobid(String jobid){
		this.jobid = jobid;
	}
	
	public String getJobid(){
		return this.jobid;
	}

}