package com.boco.eoms.summary.webapp.form;

import com.boco.eoms.base.webapp.form.BaseForm;

/**
 * <p>
 * Title:值周数据
 * </p>
 * <p>
 * Description:true
 * </p>
 * <p>
 * Tue Jun 16 17:25:37 CST 2009
 * </p>
 * 
 * @moudle.getAuthor() LXM
 * @moudle.getVersion() 3.5
 * 
 */
public class TawDutyWeekForm extends BaseForm implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 锟斤拷锟�
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
	 * 用户名
	 *
	 */
	private java.lang.String userId;
   
	public void setUserId(java.lang.String userId){
		this.userId= userId;       
	}
   
	public java.lang.String getUserId(){
		return this.userId;
	}

	/**
	 *
	 * 填写时间
	 *
	 */
	private java.lang.String insertTime;
   
	public void setInsertTime(java.lang.String insertTime){
		this.insertTime= insertTime;       
	}
   
	public java.lang.String getInsertTime(){
		return this.insertTime;
	}

	/**
	 *
	 * 周标识
	 *
	 */
	private java.lang.String weekFlag;
   
	public void setWeekFlag(java.lang.String weekFlag){
		this.weekFlag= weekFlag;       
	}
   
	public java.lang.String getWeekFlag(){
		return this.weekFlag;
	}
	private String userName;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 *
	 * 部门
	 *
	 */
	private java.lang.String deptId;
   
	public void setDeptId(java.lang.String deptId){
		this.deptId= deptId;       
	}
   
	public java.lang.String getDeptId(){
		return this.deptId;
	}
	private java.lang.String title;
	
	public java.lang.String getTitle() {
		return title;
	}

	public void setTitle(java.lang.String title) {
		this.title = title;
	}
	/**
	 *
	 * 删除标志
	 *
	 */
	private java.lang.String deleted;
   
	public void setDeleted(java.lang.String deleted){
		this.deleted= deleted;       
	}
   
	public java.lang.String getDeleted(){
		return this.deleted;
	}


	protected String accessoriesId;
	
	public String getAccessoriesId() {
		return accessoriesId;
	}
	
	public void setAccessoriesId(String accessoriesId) {
		this.accessoriesId = accessoriesId;
	}
private String gejieaccessoriesId;
	
	public String getGejieaccessoriesId() {
		return gejieaccessoriesId;
	}

	public void setGejieaccessoriesId(String gejieaccessoriesId) {
		this.gejieaccessoriesId = gejieaccessoriesId;
	}
	
	private String yiliuaccessoriesId;
	
	public String getYiliuaccessoriesId() {
		return yiliuaccessoriesId;
	}

	public void setYiliuaccessoriesId(String yiliuaccessoriesId) {
		this.yiliuaccessoriesId = yiliuaccessoriesId;
	}
	
	private String olpaccessoriesId;
	
	public String getOlpaccessoriesId() {
		return olpaccessoriesId;
	}

	public void setOlpaccessoriesId(String olpaccessoriesId) {
		this.olpaccessoriesId = olpaccessoriesId;
	}
}