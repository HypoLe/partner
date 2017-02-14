package com.boco.activiti.partner.process.po.scene;

/**
 *  公客工单 场景模板 子场景model类
 	* @author WangJun
 	* @ClassName: ChildScene
 	* @Version 版本 
 	* @ModifiedBy 修改人
 	* @Copyright Boco
 	* @date Apr 1, 2016 8:59:59 AM
 	* @description 类描述
 */
public class ChildSceneModel {
	//主场景val值
	private String childSceneValue;
	
	//主场景中文名
	private String childSceneName;
	
	//是否被选中
	private String checked;

	public String getChildSceneValue() {
		return childSceneValue;
	}

	public void setChildSceneValue(String childSceneValue) {
		this.childSceneValue = childSceneValue;
	}

	public String getChildSceneName() {
		return childSceneName;
	}

	public void setChildSceneName(String childSceneName) {
		this.childSceneName = childSceneName;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}
}
