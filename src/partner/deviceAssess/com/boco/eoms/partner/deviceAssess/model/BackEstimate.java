package com.boco.eoms.partner.deviceAssess.model;

import com.boco.eoms.base.model.BaseObject;

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
 * @author benweiwei
 * @version 1.0
 * 
 */
public class BackEstimate extends BaseObject {

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
	private java.lang.Double facilityFauTar;
   
	public void setFacilityFauTar(java.lang.Double facilityFauTar){
		this.facilityFauTar= facilityFauTar;       
	}
   
	public java.lang.Double getFacilityFauTar(){
		return this.facilityFauTar;
	}

	/**
	 *
	 * 坏板率(指标)
	 *
	 */
	private java.lang.Double badPlankTar;
   
	public void setBadPlankTar(java.lang.Double badPlankTar){
		this.badPlankTar= badPlankTar;       
	}
   
	public java.lang.Double getBadPlankTar(){
		return this.badPlankTar;
	}

	/**
	 *
	 * 重大故障次数(指标)
	 *
	 */
	private java.lang.Integer bigFaultTar;
   
	public void setBigFaultTar(java.lang.Integer bigFaultTar){
		this.bigFaultTar= bigFaultTar;       
	}
   
	public java.lang.Integer getBigFaultTar(){
		return this.bigFaultTar;
	}

	/**
	 *
	 * 设备问题数(指标)
	 *
	 */
	private java.lang.Integer facilityQueTar;
   
	public void setFacilityQueTar(java.lang.Integer facilityQueTar){
		this.facilityQueTar= facilityQueTar;       
	}
   
	public java.lang.Integer getFacilityQueTar(){
		return this.facilityQueTar;
	}

	/**
	 *
	 * 故障平均时长(指标)
	 *
	 */
	private java.lang.Double faultAvgTar;
   
	public void setFaultAvgTar(java.lang.Double faultAvgTar){
		this.faultAvgTar= faultAvgTar;       
	}
   
	public java.lang.Double getFaultAvgTar(){
		return this.faultAvgTar;
	}

	/**
	 *
	 * 业务恢复平均时长(指标)
	 *
	 */
	private java.lang.Double operationRenewTar;
   
	public void setOperationRenewTar(java.lang.Double operationRenewTar){
		this.operationRenewTar= operationRenewTar;       
	}
   
	public java.lang.Double getOperationRenewTar(){
		return this.operationRenewTar;
	}

	/**
	 *
	 * 板件返修平均时长(指标)
	 *
	 */
	private java.lang.Double plankRepairTar;
   
	public void setPlankRepairTar(java.lang.Double plankRepairTar){
		this.plankRepairTar= plankRepairTar;       
	}
   
	public java.lang.Double getPlankRepairTar(){
		return this.plankRepairTar;
	}

	/**
	 *
	 * 咨询服务反馈平均时长(指标)
	 *
	 */
	private java.lang.Double referServeTar;
   
	public void setReferServeTar(java.lang.Double referServeTar){
		this.referServeTar= referServeTar;       
	}
   
	public java.lang.Double getReferServeTar(){
		return this.referServeTar;
	}

	/**
	 *
	 * 技术服务满意度(指标)
	 *
	 */
	private java.lang.Integer artServeTar;
   
	public void setArtServeTar(java.lang.Integer artServeTar){
		this.artServeTar= artServeTar;       
	}
   
	public java.lang.Integer getArtServeTar(){
		return this.artServeTar;
	}

	/**
	 *
	 * 工程服务满意度(指标)
	 *
	 */
	private java.lang.Integer projectServeTar;
   
	public void setProjectServeTar(java.lang.Integer projectServeTar){
		this.projectServeTar= projectServeTar;       
	}
   
	public java.lang.Integer getProjectServeTar(){
		return this.projectServeTar;
	}

	/**
	 *
	 * 培训服务满意度(指标)
	 *
	 */
	private java.lang.Integer trainServeTar;
   
	public void setTrainServeTar(java.lang.Integer trainServeTar){
		this.trainServeTar= trainServeTar;       
	}
   
	public java.lang.Integer getTrainServeTar(){
		return this.trainServeTar;
	}

	/**
	 *
	 * 特通服务满意度(指标)
	 *
	 */
	private java.lang.Integer specialServeTar;
   
	public void setSpecialServeTar(java.lang.Integer specialServeTar){
		this.specialServeTar= specialServeTar;       
	}
   
	public java.lang.Integer getSpecialServeTar(){
		return this.specialServeTar;
	}

	/**
	 *
	 * 软件升级成功率(指标)
	 *
	 */
	private java.lang.Double softwareSucTar;
   
	public void setSoftwareSucTar(java.lang.Double softwareSucTar){
		this.softwareSucTar= softwareSucTar;       
	}
   
	public java.lang.Double getSoftwareSucTar(){
		return this.softwareSucTar;
	}

	/**
	 *
	 * 设备故障率(得分)
	 *
	 */
	private java.lang.Integer facilityFauPoi;
   
	public void setFacilityFauPoi(java.lang.Integer facilityFauPoi){
		this.facilityFauPoi= facilityFauPoi;       
	}
   
	public java.lang.Integer getFacilityFauPoi(){
		return this.facilityFauPoi;
	}

	/**
	 *
	 * 坏板率(得分)
	 *
	 */
	private java.lang.Integer badPlankPoi;
   
	public void setBadPlankPoi(java.lang.Integer badPlankPoi){
		this.badPlankPoi= badPlankPoi;       
	}
   
	public java.lang.Integer getBadPlankPoi(){
		return this.badPlankPoi;
	}

	/**
	 *
	 * 重大故障次数(得分)
	 *
	 */
	private java.lang.Integer bigFaultPoi;
   
	public void setBigFaultPoi(java.lang.Integer bigFaultPoi){
		this.bigFaultPoi= bigFaultPoi;       
	}
   
	public java.lang.Integer getBigFaultPoi(){
		return this.bigFaultPoi;
	}

	/**
	 *
	 * 设备问题数(得分)
	 *
	 */
	private java.lang.Integer facilityQuePoi;
   
	public void setFacilityQuePoi(java.lang.Integer facilityQuePoi){
		this.facilityQuePoi= facilityQuePoi;       
	}
   
	public java.lang.Integer getFacilityQuePoi(){
		return this.facilityQuePoi;
	}

	/**
	 *
	 * 故障平均时长(得分)
	 *
	 */
	private java.lang.Integer faultAvgPoi;
   
	public void setFaultAvgPoi(java.lang.Integer faultAvgPoi){
		this.faultAvgPoi= faultAvgPoi;       
	}
   
	public java.lang.Integer getFaultAvgPoi(){
		return this.faultAvgPoi;
	}

	/**
	 *
	 * 业务恢复平均时长(得分)
	 *
	 */
	private java.lang.Integer operationRenewPoi;
   
	public void setOperationRenewPoi(java.lang.Integer operationRenewPoi){
		this.operationRenewPoi= operationRenewPoi;       
	}
   
	public java.lang.Integer getOperationRenewPoi(){
		return this.operationRenewPoi;
	}

	/**
	 *
	 * 板件返修平均时长(得分)
	 *
	 */
	private java.lang.Integer plankRepairPoi;
   
	public void setPlankRepairPoi(java.lang.Integer plankRepairPoi){
		this.plankRepairPoi= plankRepairPoi;       
	}
   
	public java.lang.Integer getPlankRepairPoi(){
		return this.plankRepairPoi;
	}

	/**
	 *
	 * 咨询服务反馈平均时长(得分)
	 *
	 */
	private java.lang.Integer referServePoi;
   
	public void setReferServePoi(java.lang.Integer referServePoi){
		this.referServePoi= referServePoi;       
	}
   
	public java.lang.Integer getReferServePoi(){
		return this.referServePoi;
	}

	/**
	 *
	 * 技术服务满意度(得分)
	 *
	 */
	private java.lang.Integer artServePoi;
   
	public void setArtServePoi(java.lang.Integer artServePoi){
		this.artServePoi= artServePoi;       
	}
   
	public java.lang.Integer getArtServePoi(){
		return this.artServePoi;
	}

	/**
	 *
	 * 工程服务满意度(得分)
	 *
	 */
	private java.lang.Integer projectServePoi;
   
	public void setProjectServePoi(java.lang.Integer projectServePoi){
		this.projectServePoi= projectServePoi;       
	}
   
	public java.lang.Integer getProjectServePoi(){
		return this.projectServePoi;
	}

	/**
	 *
	 * 培训服务满意度(得分)
	 *
	 */
	private java.lang.Integer trainServePoi;
   
	public void setTrainServePoi(java.lang.Integer trainServePoi){
		this.trainServePoi= trainServePoi;       
	}
   
	public java.lang.Integer getTrainServePoi(){
		return this.trainServePoi;
	}

	/**
	 *
	 * 特通服务满意度(得分)
	 *
	 */
	private java.lang.Integer specialServePoi;
   
	public void setSpecialServePoi(java.lang.Integer specialServePoi){
		this.specialServePoi= specialServePoi;       
	}
   
	public java.lang.Integer getSpecialServePoi(){
		return this.specialServePoi;
	}

	/**
	 *
	 * 软件升级成功率(得分)
	 *
	 */
	private java.lang.Integer softwareSucPoi;
   
	public void setSoftwareSucPoi(java.lang.Integer softwareSucPoi){
		this.softwareSucPoi= softwareSucPoi;       
	}
   
	public java.lang.Integer getSoftwareSucPoi(){
		return this.softwareSucPoi;
	}

	/**
	 *
	 * 年
	 *
	 */
	private java.lang.Integer year;
   
	public void setYear(java.lang.Integer year){
		this.year= year;       
	}
   
	public java.lang.Integer getYear(){
		return this.year;
	}

	/**
	 *
	 * 季度
	 *
	 */
	private java.lang.Integer quarter;
   
	public void setQuarter(java.lang.Integer quarter){
		this.quarter= quarter;       
	}
   
	public java.lang.Integer getQuarter(){
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
	private java.lang.Integer total;	
	
	public java.lang.Integer getTotal() {
		return total;
	}

	public void setTotal(java.lang.Integer total) {
		this.total = total;
	}

	public boolean equals(Object o) {
		if( o instanceof BackEstimate ) {
			BackEstimate backEstimate=(BackEstimate)o;
			if (this.id != null || this.id.equals(backEstimate.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public int hashCode(){
		return super.hashCode();
	}	
}