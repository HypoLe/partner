package com.boco.eoms.materials.model;

/**
 * 仓库表
 * 
 * @author wanghongliang
 * 
 */
public class Store {
	private String id;
	private String name;
	/**
	 * 仓库管理员
	 */
	private String controller;
	/**
	 * 负责人
	 */
	private String responsiblePerson;
	private String address;
	private Integer telephone;
	/**
	 * 所属部门
	 */
	private String manageDepartmant;
	/**
	 * 存货分类
	 */
	private String inventoryCategory;
	private String remark;

	

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

	public String getController() {
		return controller;
	}

	public void setController(String controller) {
		this.controller = controller;
	}

	public String getResponsiblePerson() {
		return responsiblePerson;
	}

	public void setResponsiblePerson(String responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getTelephone() {
		return telephone;
	}

	public void setTelephone(Integer telephone) {
		this.telephone = telephone;
	}

	public String getManageDepartmant() {
		return manageDepartmant;
	}

	public void setManageDepartmant(String manageDepartmant) {
		this.manageDepartmant = manageDepartmant;
	}

	public String getInventoryCategory() {
		return inventoryCategory;
	}

	public void setInventoryCategory(String inventoryCategory) {
		this.inventoryCategory = inventoryCategory;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
