package com.boco.eoms.materials.model;

import java.util.Date;

/**
 * 库存历史记录表
 * 
 * @author wanghongliang
 * 
 */
public class StoreInventoryHistory {
	private String id;
	/**
	 * 仓库名称
	 */
	private String storeSname;
	/**
	 * 仓库主键
	 */
	private String storeSid;
	/**
	 * 材料名称
	 */
	private String materialName;
	/**
	 * 材料主键
	 */
	private String materialId;
	/**
	 * 材料变动时间
	 */
	private Date materialUpdateDate;
	/**
	 * 库存数量
	 */
	private Integer onhand;

	public String getStoreSname() {
		return storeSname;
	}

	public void setStoreSname(String storeSname) {
		this.storeSname = storeSname;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStoreSid() {
		return storeSid;
	}

	public void setStoreSid(String storeSid) {
		this.storeSid = storeSid;
	}

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}

	public Date getMaterialUpdateDate() {
		return materialUpdateDate;
	}

	public void setMaterialUpdateDate(Date materialUpdateDate) {
		this.materialUpdateDate = materialUpdateDate;
	}

	public Integer getOnhand() {
		return onhand;
	}

	public void setOnhand(Integer onhand) {
		this.onhand = onhand;
	}

}
