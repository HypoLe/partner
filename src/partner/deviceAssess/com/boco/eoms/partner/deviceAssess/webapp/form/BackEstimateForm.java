package com.boco.eoms.partner.deviceAssess.webapp.form;

import com.boco.eoms.base.webapp.form.BaseForm;

/**
 * <p>
 * Title:后评估指标体系
 * </p>
 * <p>
 * Description:后评估指标体系
 * </p>
 * <p>
 * Thu Oct 14 10:55:23 CST 2010
 * </p>
 *  
 * @moudle.getAuthor() benweiwei
 * @moudle.getVersion() 1.0
 * 
 */
public class BackEstimateForm extends BaseForm implements java.io.Serializable {

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
	 * 设备故障率(指标)
	 *
	 */
	private java.lang.String facilityFauTar;
   
	public void setFacilityFauTar(java.lang.String facilityFauTar){
		this.facilityFauTar= facilityFauTar;       
	}
   
	public java.lang.String getFacilityFauTar(){
		return this.facilityFauTar;
	}

	/**
	 *
	 * 坏板率(指标)
	 *
	 */
	private java.lang.String badPlankTar;
   
	public void setBadPlankTar(java.lang.String badPlankTar){
		this.badPlankTar= badPlankTar;       
	}
   
	public java.lang.String getBadPlankTar(){
		return this.badPlankTar;
	}

	/**
	 *
	 * 重大故障次数(指标)
	 *
	 */
	private java.lang.String bigFaultTar;
   
	public void setBigFaultTar(java.lang.String bigFaultTar){
		this.bigFaultTar= bigFaultTar;       
	}
   
	public java.lang.String getBigFaultTar(){
		return this.bigFaultTar;
	}

	/**
	 *
	 * 设备问题数(指标)
	 *
	 */
	private java.lang.String facilityQueTar;
   
	public void setFacilityQueTar(java.lang.String facilityQueTar){
		this.facilityQueTar= facilityQueTar;       
	}
   
	public java.lang.String getFacilityQueTar(){
		return this.facilityQueTar;
	}

	/**
	 *
	 * 故障平均时长(指标)
	 *
	 */
	private java.lang.String faultAvgTar;
   
	public void setFaultAvgTar(java.lang.String faultAvgTar){
		this.faultAvgTar= faultAvgTar;       
	}
   
	public java.lang.String getFaultAvgTar(){
		return this.faultAvgTar;
	}

	/**
	 *
	 * 业务恢复平均时长(指标)
	 *
	 */
	private java.lang.String operationRenewTar;
   
	public void setOperationRenewTar(java.lang.String operationRenewTar){
		this.operationRenewTar= operationRenewTar;       
	}
   
	public java.lang.String getOperationRenewTar(){
		return this.operationRenewTar;
	}

	/**
	 *
	 * 板件返修平均时长(指标)
	 *
	 */
	private java.lang.String plankRepairTar;
   
	public void setPlankRepairTar(java.lang.String plankRepairTar){
		this.plankRepairTar= plankRepairTar;       
	}
   
	public java.lang.String getPlankRepairTar(){
		return this.plankRepairTar;
	}

	/**
	 *
	 * 咨询服务反馈平均时长(指标)
	 *
	 */
	private java.lang.String referServeTar;
   
	public void setReferServeTar(java.lang.String referServeTar){
		this.referServeTar= referServeTar;       
	}
   
	public java.lang.String getReferServeTar(){
		return this.referServeTar;
	}

	/**
	 *
	 * 技术服务满意度(指标)
	 *
	 */
	private java.lang.String artServeTar;
   
	public void setArtServeTar(java.lang.String artServeTar){
		this.artServeTar= artServeTar;       
	}
   
	public java.lang.String getArtServeTar(){
		return this.artServeTar;
	}

	/**
	 *
	 * 工程服务满意度(指标)
	 *
	 */
	private java.lang.String projectServeTar;
   
	public void setProjectServeTar(java.lang.String projectServeTar){
		this.projectServeTar= projectServeTar;       
	}
   
	public java.lang.String getProjectServeTar(){
		return this.projectServeTar;
	}

	/**
	 *
	 * 培训服务满意度(指标)
	 *
	 */
	private java.lang.String trainServeTar;
   
	public void setTrainServeTar(java.lang.String trainServeTar){
		this.trainServeTar= trainServeTar;       
	}
   
	public java.lang.String getTrainServeTar(){
		return this.trainServeTar;
	}

	/**
	 *
	 * 特通服务满意度(指标)
	 *
	 */
	private java.lang.String specialServeTar;
   
	public void setSpecialServeTar(java.lang.String specialServeTar){
		this.specialServeTar= specialServeTar;       
	}
   
	public java.lang.String getSpecialServeTar(){
		return this.specialServeTar;
	}

	/**
	 *
	 * 软件升级成功率(指标)
	 *
	 */
	private java.lang.String softwareSucTar;
   
	public void setSoftwareSucTar(java.lang.String softwareSucTar){
		this.softwareSucTar= softwareSucTar;       
	}
   
	public java.lang.String getSoftwareSucTar(){
		return this.softwareSucTar;
	}

	/**
	 *
	 * 设备故障率(得分)
	 *
	 */
	private java.lang.String facilityFauPoi;
   
	public void setFacilityFauPoi(java.lang.String facilityFauPoi){
		this.facilityFauPoi= facilityFauPoi;       
	}
   
	public java.lang.String getFacilityFauPoi(){
		return this.facilityFauPoi;
	}

	/**
	 *
	 * 坏板率(得分)
	 *
	 */
	private java.lang.String badPlankPoi;
   
	public void setBadPlankPoi(java.lang.String badPlankPoi){
		this.badPlankPoi= badPlankPoi;       
	}
   
	public java.lang.String getBadPlankPoi(){
		return this.badPlankPoi;
	}

	/**
	 *
	 * 重大故障次数(得分)
	 *
	 */
	private java.lang.String bigFaultPoi;
   
	public void setBigFaultPoi(java.lang.String bigFaultPoi){
		this.bigFaultPoi= bigFaultPoi;       
	}
   
	public java.lang.String getBigFaultPoi(){
		return this.bigFaultPoi;
	}

	/**
	 *
	 * 设备问题数(得分)
	 *
	 */
	private java.lang.String facilityQuePoi;
   
	public void setFacilityQuePoi(java.lang.String facilityQuePoi){
		this.facilityQuePoi= facilityQuePoi;       
	}
   
	public java.lang.String getFacilityQuePoi(){
		return this.facilityQuePoi;
	}

	/**
	 *
	 * 故障平均时长(得分)
	 *
	 */
	private java.lang.String faultAvgPoi;
   
	public void setFaultAvgPoi(java.lang.String faultAvgPoi){
		this.faultAvgPoi= faultAvgPoi;       
	}
   
	public java.lang.String getFaultAvgPoi(){
		return this.faultAvgPoi;
	}

	/**
	 *
	 * 业务恢复平均时长(得分)
	 *
	 */
	private java.lang.String operationRenewPoi;
   
	public void setOperationRenewPoi(java.lang.String operationRenewPoi){
		this.operationRenewPoi= operationRenewPoi;       
	}
   
	public java.lang.String getOperationRenewPoi(){
		return this.operationRenewPoi;
	}

	/**
	 *
	 * 板件返修平均时长(得分)
	 *
	 */
	private java.lang.String plankRepairPoi;
   
	public void setPlankRepairPoi(java.lang.String plankRepairPoi){
		this.plankRepairPoi= plankRepairPoi;       
	}
   
	public java.lang.String getPlankRepairPoi(){
		return this.plankRepairPoi;
	}

	/**
	 *
	 * 咨询服务反馈平均时长(得分)
	 *
	 */
	private java.lang.String referServePoi;
   
	public void setReferServePoi(java.lang.String referServePoi){
		this.referServePoi= referServePoi;       
	}
   
	public java.lang.String getReferServePoi(){
		return this.referServePoi;
	}

	/**
	 *
	 * 技术服务满意度(得分)
	 *
	 */
	private java.lang.String artServePoi;
   
	public void setArtServePoi(java.lang.String artServePoi){
		this.artServePoi= artServePoi;       
	}
   
	public java.lang.String getArtServePoi(){
		return this.artServePoi;
	}

	/**
	 *
	 * 工程服务满意度(得分)
	 *
	 */
	private java.lang.String projectServePoi;
   
	public void setProjectServePoi(java.lang.String projectServePoi){
		this.projectServePoi= projectServePoi;       
	}
   
	public java.lang.String getProjectServePoi(){
		return this.projectServePoi;
	}

	/**
	 *
	 * 培训服务满意度(得分)
	 *
	 */
	private java.lang.String trainServePoi;
   
	public void setTrainServePoi(java.lang.String trainServePoi){
		this.trainServePoi= trainServePoi;       
	}
   
	public java.lang.String getTrainServePoi(){
		return this.trainServePoi;
	}

	/**
	 *
	 * 特通服务满意度(得分)
	 *
	 */
	private java.lang.String specialServePoi;
   
	public void setSpecialServePoi(java.lang.String specialServePoi){
		this.specialServePoi= specialServePoi;       
	}
   
	public java.lang.String getSpecialServePoi(){
		return this.specialServePoi;
	}

	/**
	 *
	 * 软件升级成功率(得分)
	 *
	 */
	private java.lang.String softwareSucPoi;
   
	public void setSoftwareSucPoi(java.lang.String softwareSucPoi){
		this.softwareSucPoi= softwareSucPoi;       
	}
   
	public java.lang.String getSoftwareSucPoi(){
		return this.softwareSucPoi;
	}

	/**
	 *
	 * 年
	 *
	 */
	private java.lang.String year;
   
	public void setYear(java.lang.String year){
		this.year= year;       
	}
   
	public java.lang.String getYear(){
		return this.year;
	}

	/**
	 *
	 * 季度
	 *
	 */
	private java.lang.String quarter;
   
	public void setQuarter(java.lang.String quarter){
		this.quarter= quarter;       
	}
   
	public java.lang.String getQuarter(){
		return this.quarter;
	}

	/**
	 *
	 * 厂家
	 *
	 */
	private java.lang.String factory;
   
	public void setFactory(java.lang.String factory){
		this.factory= factory;       
	}
   
	public java.lang.String getFactory(){
		return this.factory;
	}

	/**
	 *
	 * 专业
	 *
	 */
	private java.lang.String speciality;
   
	public void setSpeciality(java.lang.String speciality){
		this.speciality= speciality;       
	}
   
	public java.lang.String getSpeciality(){
		return this.speciality;
	}

	/**
	 *
	 * 设备类型
	 *
	 */
	private java.lang.String equipmentType;
   
	public void setEquipmentType(java.lang.String equipmentType){
		this.equipmentType= equipmentType;       
	}
   
	public java.lang.String getEquipmentType(){
		return this.equipmentType;
	}
	
	/**
	 *
	 * 总分
	 *
	 */
	private java.lang.String total;	
	
	public java.lang.String getTotal() {
		return total;
	}

	public void setTotal(java.lang.String total) {
		this.total = total;
	}
}