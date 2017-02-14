package com.boco.activiti.partner.process.po;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: wyl
 * Date: 13-9-12
 * Time: 下午2:58
 * 附件下载列表类
 * To change this template use File | Settings | File Templates.
 */
public class DownAttachMentModel implements Serializable {
	
	   /**附件id*/
	   private String accessoriesid ;
	
	   //tr.accessoriespath,
	   /**附件路径*/
	   private String accessoriespath ;
	   
  
    /**
     *     工单流程ID
     */
    private String processInstanceId;

    /**工单编码*/
    private String sheetId;

    //工单主题 项目名称
    private String theme;
    
    /**
     * 工单类型
     */
   private String themeinterface;
   
	/**
	 * 所属环节
	 */
   private String taskdefkey;

   /**附件中文名称*/
   private String accessoriescnname ;
   
   /**附件名称*/
   private String accessoriesname ;
   
   /**所属区域*/
   private String city;
   
   /**区县*/
   private String country;
   
    //派单时间
    private Date sendTime;
    
    //AppCode
    private String appcode;
    

	/**
	 * @return the processInstanceId
	 */
	public String getProcessInstanceId() {
		return processInstanceId;
	}

	/**
	 * @param processInstanceId the processInstanceId to set
	 */
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	/**
	 * @return the sheetId
	 */
	public String getSheetId() {
		return sheetId;
	}

	/**
	 * @param sheetId the sheetId to set
	 */
	public void setSheetId(String sheetId) {
		this.sheetId = sheetId;
	}

	/**
	 * @return the theme
	 */
	public String getTheme() {
		return theme;
	}

	/**
	 * @param theme the theme to set
	 */
	public void setTheme(String theme) {
		this.theme = theme;
	}

	/**
	 * @return the themeinterface
	 */
	public String getThemeinterface() {
		return themeinterface;
	}

	/**
	 * @param themeinterface the themeinterface to set
	 */
	public void setThemeinterface(String themeinterface) {
		this.themeinterface = themeinterface;
	}

	/**
	 * @return the taskdefkey
	 */
	public String getTaskdefkey() {
		return taskdefkey;
	}

	/**
	 * @param taskdefkey the taskdefkey to set
	 */
	public void setTaskdefkey(String taskdefkey) {
		this.taskdefkey = taskdefkey;
	}

	/**
	 * @return the accessoriescnname
	 */
	public String getAccessoriescnname() {
		return accessoriescnname;
	}

	/**
	 * @param accessoriescnname the accessoriescnname to set
	 */
	public void setAccessoriescnname(String accessoriescnname) {
		this.accessoriescnname = accessoriescnname;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the sendTime
	 */
	public Date getSendTime() {
		return sendTime;
	}

	/**
	 * @param sendTime the sendTime to set
	 */
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public void setAccessoriesid(String accessoriesid) {
		this.accessoriesid = accessoriesid;
	}

	public String getAccessoriesid() {
		return accessoriesid;
	}

	public void setAccessoriespath(String accessoriespath) {
		this.accessoriespath = accessoriespath;
	}

	public String getAccessoriespath() {
		return accessoriespath;
	}

	public void setAccessoriesname(String accessoriesname) {
		this.accessoriesname = accessoriesname;
	}

	public String getAccessoriesname() {
		return accessoriesname;
	}

	public void setAppcode(String appcode) {
		this.appcode = appcode;
	}

	public String getAppcode() {
		return appcode;
	}
        
    

}
