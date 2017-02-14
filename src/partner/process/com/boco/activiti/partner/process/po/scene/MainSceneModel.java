package com.boco.activiti.partner.process.po.scene;

/**
 * 	公客工单 场景模板 主场景模板 model类
 	* @author WangJun
 	* @ClassName: MainSceneModel
 	* @Version 版本 
 	* @ModifiedBy 修改人
 	* @Copyright Boco
 	* @date Mar 31, 2016 1:16:31 PM
 	* @description 类描述
 */
public class MainSceneModel {
	//主场景val值
	private String mainSceneValue;
	
	//主场景中文名
	private String mainSceneName;
	
	//是否被选中
	private String checked;

	public String getMainSceneValue() {
		return mainSceneValue;
	}

	public void setMainSceneValue(String mainSceneValue) {
		this.mainSceneValue = mainSceneValue;
	}

	public String getMainSceneName() {
		return mainSceneName;
	}

	public void setMainSceneName(String mainSceneName) {
		this.mainSceneName = mainSceneName;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}
	
}
