package com.boco.activiti.partner.process.po.scene;

/**
 * 	材料下拉选的model
 	* @author WangJun
 	* @ClassName: MaterialModel
 	* @Version 版本 
 	* @ModifiedBy 修改人
 	* @Copyright Boco
 	* @date Apr 5, 2016 9:56:17 AM
 	* @description 类描述
 */
public class MaterialModel {
	//材料值
	private String materialValue;
	
	//材料名字
	private String materialName;
	
	//是否被选中
	private String selected;
	


	public MaterialModel() {
		super();
		
	}
	
	public MaterialModel(String materialValue, String materialName,
			String selected) {
		super();
		this.materialValue = materialValue;
		this.materialName = materialName;
		this.selected = selected;
	}

	public String getMaterialValue() {
		return materialValue;
	}

	public void setMaterialValue(String materialValue) {
		this.materialValue = materialValue;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}
}
