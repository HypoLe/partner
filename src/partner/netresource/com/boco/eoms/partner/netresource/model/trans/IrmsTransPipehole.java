package com.boco.eoms.partner.netresource.model.trans;

import com.boco.eoms.partner.netresource.model.EomsModel;

/**
 * 类说明：网络资源--传输线路--管孔
 * 创建人： zhangkeqi
 * 创建时间：2012-11-27 18:51:07
 */
public class IrmsTransPipehole extends EomsModel{

	/**主键*/
	private String id;
		
	/**管孔名称，作为业务主键，命名要求唯一。管道段截面的空心部分，可穿放若干条光、电缆或子管孔，一个管道段可以有多个管孔。管孔名称即管孔编号,按照从左到右,从下到上的顺序进行编号 [例] 5*/
	private String pipeHoleName;
		
	/**所属管道段，关联【管道段】表-【管道段名称】。*/
	private String relatedPipelineSection;
		
	/**标示出管孔在所属管道段截面图中的位置*/
	private String crossSectionPosition;
		
	/**枚举值：未用,占用,预占,已坏.*/
	private String status;
		
	/**枚举值：波纹管、梅花管，组合*/
	private String type;
		
	/**枚举值：硅管、PVC、钢管等*/
	private String material;
		
	/**管孔用途,枚举值：自用,出租,共享*/
	private String use;
		
	/**管孔使用单位。例：中国移动。*/
	private String useUnit;
		
	/**管孔所有权人。例：中国电信。*/
	private String owner;
		
	/**备注*/
	private String remark;
		
	/**创建时间*/
	private String createTime;
		
	/**所属管道段ID*/
	private String relatedPipelineSectionId;
		

	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public String getPipeHoleName() {
		return this.pipeHoleName;
	}
	
	public void setPipeHoleName(String pipeHoleName) {
		this.pipeHoleName = pipeHoleName;
	}
	public String getRelatedPipelineSection() {
		return this.relatedPipelineSection;
	}
	
	public void setRelatedPipelineSection(String relatedPipelineSection) {
		this.relatedPipelineSection = relatedPipelineSection;
	}
	public String getCrossSectionPosition() {
		return this.crossSectionPosition;
	}
	
	public void setCrossSectionPosition(String crossSectionPosition) {
		this.crossSectionPosition = crossSectionPosition;
	}
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	public String getType() {
		return this.type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	public String getMaterial() {
		return this.material;
	}
	
	public void setMaterial(String material) {
		this.material = material;
	}
	public String getUse() {
		return this.use;
	}
	
	public void setUse(String use) {
		this.use = use;
	}
	public String getUseUnit() {
		return this.useUnit;
	}
	
	public void setUseUnit(String useUnit) {
		this.useUnit = useUnit;
	}
	public String getOwner() {
		return this.owner;
	}
	
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getRelatedPipelineSectionId() {
		return this.relatedPipelineSectionId;
	}
	
	public void setRelatedPipelineSectionId(String relatedPipelineSectionId) {
		this.relatedPipelineSectionId = relatedPipelineSectionId;
	}
	
}
