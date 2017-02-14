package com.boco.eoms.materials.model;

/**
 * 库存表
 * 
 * @author wanghongliang
 * 
 */
public class StoreInventory {
	private String id;
	/**
	 * 仓库主键ID
	 */
	private String storeSid;
	/**
	 * 仓库名称
	 */

	private String storeSname;
	/**
	 * 材料名称
	 */
	private String materialName;
	/**
	 * 单价
	 */
	private double materialPrice;
	/**
	 * 库存数量
	 */
	private Integer onhand;
	/**
	 * 材料编码
	 */
	private String materialEncode;
	/**
	 * 材料规格
	 */
	private String materialSpecification;
	/**
	 * 材料类别号
	 */
	private String materialCategoryNum;
	/**
	 * 类别详情
	 */
	private String materialCategoryDetail;
	/**
	 * 生产厂家
	 */
	private String materialManufacturer;
	/**
	 * 材料 单位(米、个)
	 */
	private String materialUnit;
	/**
	 * 材料主键ID
	 */
	private String materialId;
	/**
	 * 总金额
	 */
	private double totalAmount;

	private String remark;

	public String getStoreSid() {
		return storeSid;
	}

	public void setStoreSid(String storeSid) {
		this.storeSid = storeSid;
	}

	public String getStoreSname() {
		return storeSname;
	}

	public void setStoreSname(String storeSname) {
		this.storeSname = storeSname;
	}

	public double getMaterialPrice() {
		return materialPrice;
	}

	public void setMaterialPrice(double materialPrice) {
		this.materialPrice = materialPrice;
	}

	public Integer getOnhand() {
		return onhand;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public void setOnhand(Integer onhand) {
		this.onhand = onhand;
	}

	public String getMaterialEncode() {
		return materialEncode;
	}

	public void setMaterialEncode(String materialEncode) {
		this.materialEncode = materialEncode;
	}

	public String getMaterialSpecification() {
		return materialSpecification;
	}

	public void setMaterialSpecification(String materialSpecification) {
		this.materialSpecification = materialSpecification;
	}

	public String getMaterialCategoryNum() {
		return materialCategoryNum;
	}

	public void setMaterialCategoryNum(String materialCategoryNum) {
		this.materialCategoryNum = materialCategoryNum;
	}

	public String getMaterialCategoryDetail() {
		return materialCategoryDetail;
	}

	public void setMaterialCategoryDetail(String materialCategoryDetail) {
		this.materialCategoryDetail = materialCategoryDetail;
	}

	public String getMaterialManufacturer() {
		return materialManufacturer;
	}

	public void setMaterialManufacturer(String materialManufacturer) {
		this.materialManufacturer = materialManufacturer;
	}

	public String getMaterialUnit() {
		return materialUnit;
	}

	public void setMaterialUnit(String materialUnit) {
		this.materialUnit = materialUnit;
	}

	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
