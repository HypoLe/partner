package com.boco.eoms.materials.model;

/**
 * 材料信息表
 * 
 * @author wanghongliang
 * 
 */
public class Material {

	private String id;
	/**
	 * 材料名称
	 */
	private String name;
	/**
	 * 材料规格
	 */
	private String specification;
	/**
	 * 材料编码
	 */
	private String encode;
	/**
	 * 材料单位
	 */
	private String unit;
	/**
	 * 材料类别号
	 */
	private String categoryNum;
	/**
	 * 类别名称
	 */
	private String categoryName;
	/**
	 * 货位号
	 */
	private String bin;
	/**
	 * 库存上限
	 */
	private Integer inventoryMax;
	/**
	 * 库存下限
	 */
	private Integer inventoryMin;
	/**
	 * 生产厂家
	 */
	private String manufacturer;
	private String remark;
	/**
	 * 入库单价
	 */
	private double inputPrice;

	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public String getEncode() {
		return encode;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getCategoryNum() {
		return categoryNum;
	}

	public void setCategoryNum(String categoryNum) {
		this.categoryNum = categoryNum;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getBin() {
		return bin;
	}

	public void setBin(String bin) {
		this.bin = bin;
	}

	public Integer getInventoryMax() {
		return inventoryMax;
	}

	public void setInventoryMax(Integer inventoryMax) {
		this.inventoryMax = inventoryMax;
	}

	public Integer getInventoryMin() {
		return inventoryMin;
	}

	public void setInventoryMin(Integer inventoryMin) {
		this.inventoryMin = inventoryMin;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public double getInputPrice() {
		return inputPrice;
	}

	public void setInputPrice(double inputPrice) {
		this.inputPrice = inputPrice;
	}

}
