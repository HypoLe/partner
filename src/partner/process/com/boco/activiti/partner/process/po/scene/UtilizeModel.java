package com.boco.activiti.partner.process.po.scene;

/**
 * 	是否利旧下拉选的model
 	* @author WangJun
 	* @ClassName: MaterialModel
 	* @Version 版本 
 	* @ModifiedBy 修改人
 	* @Copyright Boco
 	* @date Apr 5, 2016 9:56:17 AM
 	* @description 类描述
 */
public class UtilizeModel {
	//是否利旧值
	private String utilizeValue;
	
	//是否利旧名字
	private String utilizeName;
	
	//是否被选中
	private String selected;
	
	public UtilizeModel(){
		
	}

	public UtilizeModel(String utilizeValue, String utilizeName, String selected) {
		super();
		this.utilizeValue = utilizeValue;
		this.utilizeName = utilizeName;
		this.selected = selected;
	}

	public String getUtilizeValue() {
		return utilizeValue;
	}

	public void setUtilizeValue(String utilizeValue) {
		this.utilizeValue = utilizeValue;
	}

	public String getUtilizeName() {
		return utilizeName;
	}

	public void setUtilizeName(String utilizeName) {
		this.utilizeName = utilizeName;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}
}
