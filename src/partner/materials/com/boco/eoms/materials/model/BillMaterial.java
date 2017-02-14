package com.boco.eoms.materials.model;

/**
 * 单据中详细货品信息表(出入库中间表)
 * 
 * @author wanghongliang
 * 
 */
public class BillMaterial {
	private String id;
	/**
	 * 单据主键id
	 */
	private String storeBillId;
	/**
	 * 材料主键id
	 */
	private String materialId;
	
	/**
	 * 材料名称
	 */
	private String materialName;

	private String encode;
	private String unit;
	private String specification;
	
	/**
	 * 数量
	 */
	private Integer materialAmount;
	/**
	 * 单价
	 */
	private double materialPrice;
	private String remark;

	private double totalAmount;
	
	

	public double getTotalAmount() {
		return totalAmount;
	}



	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}



	public String getUnit() {
		return unit;
	}

	

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	public String getStoreBillId() {
		return storeBillId;
	}

	public void setStoreBillId(String storeBillId) {
		this.storeBillId = storeBillId;
	}

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}

	public Integer getMaterialAmount() {
		return materialAmount;
	}

	public void setMaterialAmount(Integer materialAmount) {
		this.materialAmount = materialAmount;
	}

	public double getMaterialPrice() {
		return materialPrice;
	}

	public void setMaterialPrice(double materialPrice) {
		this.materialPrice = materialPrice;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getEncode() {
		return encode;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

}
