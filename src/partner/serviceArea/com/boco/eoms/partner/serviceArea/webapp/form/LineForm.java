package com.boco.eoms.partner.serviceArea.webapp.form;

import com.boco.eoms.base.webapp.form.BaseForm;

/**
 * <p>
 * Title:线路
 * </p>
 * <p>
 * Description:服务资源配置 线路
 * </p>
 * <p>
 * Fri Nov 13 10:10:56 CST 2009
 * </p>
 * 
 * @moudle.getAuthor() wangjunfeng
 * @moudle.getVersion() 1.0
 * 
 */
public class LineForm extends BaseForm implements java.io.Serializable {

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
	 * 段落名称
	 *
	 */
	private java.lang.String lineName;
   
	public void setLineName(java.lang.String lineName){
		this.lineName= lineName;       
	}
   
	public java.lang.String getLineName(){
		return this.lineName;
	}

	/**
	 *
	 * 所属地市
	 *
	 */
	private java.lang.String region;
   
	public void setRegion(java.lang.String region){
		this.region= region;       
	}
   
	public java.lang.String getRegion(){
		return this.region;
	}

	/**
	 *
	 * 所属县区
	 *
	 */
	private java.lang.String city;
   
	public void setCity(java.lang.String city){
		this.city= city;       
	}
   
	public java.lang.String getCity(){
		return this.city;
	}

	/**
	 *
	 * 所属网格
	 *
	 */
	private java.lang.String grid;
   
	public void setGrid(java.lang.String grid){
		this.grid= grid;       
	}
   
	public java.lang.String getGrid(){
		return this.grid;
	}

	/**
	 *
	 * 合作伙伴
	 *
	 */
	private java.lang.String provider;
   
	public void setProvider(java.lang.String provider){
		this.provider= provider;       
	}
   
	public java.lang.String getProvider(){
		return this.provider;
	}

	/**
	 *
	 * 是否全业务内
	 *
	 */
	private java.lang.String isFullService;
   
	public void setIsFullService(java.lang.String isFullService){
		this.isFullService= isFullService;       
	}
   
	public java.lang.String getIsFullService(){
		return this.isFullService;
	}

	/**
	 *
	 * 段落长度
	 *
	 */
	private java.lang.String lineLength;
   
	public void setLineLength(java.lang.String lineLength){
		this.lineLength= lineLength;       
	}
   
	public java.lang.String getLineLength(){
		return this.lineLength;
	}

	/**
	 *
	 * 线路级别（一干、二干）
	 *
	 */
	private java.lang.String grade;
   
	public void setGrade(java.lang.String grade){
		this.grade= grade;       
	}
   
	public java.lang.String getGrade(){
		return this.grade;
	}

	/**
	 *
	 * 新增人员账号
	 *
	 */
	private java.lang.String userNameNew;
   
	public void setUserNameNew(java.lang.String userNameNew){
		this.userNameNew= userNameNew;       
	}
   
	public java.lang.String getUserNameNew(){
		return this.userNameNew;
	}

	/**
	 *
	 * 修改人员账号
	 *
	 */
	private java.lang.String userNameModify;
   
	public void setUserNameModify(java.lang.String userNameModify){
		this.userNameModify= userNameModify;       
	}
   
	public java.lang.String getUserNameModify(){
		return this.userNameModify;
	}

	/**
	 *
	 * 删除人员账号
	 *
	 */
	private java.lang.String userNameDel;
   
	public void setUserNameDel(java.lang.String userNameDel){
		this.userNameDel= userNameDel;       
	}
   
	public java.lang.String getUserNameDel(){
		return this.userNameDel;
	}

	/**
	 *
	 * 新增时间
	 *
	 */
	private java.lang.String timeNew;
   
	public void setTimeNew(java.lang.String timeNew){
		this.timeNew= timeNew;       
	}
   
	public java.lang.String getTimeNew(){
		return this.timeNew;
	}

	/**
	 *
	 * 修改时间
	 *
	 */
	private java.lang.String timeModify;
   
	public void setTimeModify(java.lang.String timeModify){
		this.timeModify= timeModify;       
	}
   
	public java.lang.String getTimeModify(){
		return this.timeModify;
	}

	/**
	 *
	 * 删除时间
	 *
	 */
	private java.lang.String timeDel;
   
	public void setTimeDel(java.lang.String timeDel){
		this.timeDel= timeDel;       
	}
   
	public java.lang.String getTimeDel(){
		return this.timeDel;
	}
	
	
	/**
	 *
	 * 是否删除
	 *
	 */
	private java.lang.String isDel;
  
	public void setIsDel(java.lang.String isDel){
		this.isDel= isDel;       
	}
  
	public java.lang.String getIsDel(){
		return this.isDel;
	}


}