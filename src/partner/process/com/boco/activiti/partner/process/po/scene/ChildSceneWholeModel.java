package com.boco.activiti.partner.process.po.scene;

import java.util.List;

/**
 *  公客工单 场景模板 子场景整体model类
 	* @author WangJun
 	* @ClassName: ChildSceneWholeModel
 	* @Version 版本 
 	* @ModifiedBy 修改人
 	* @Copyright Boco
 	* @date Apr 1, 2016 8:57:16 AM
 	* @description 类描述
 */

public class ChildSceneWholeModel {
	//主场景的值
	private String mainSceneValue;
	
	//主场景的名字
	private String mainSceneName;
	
	//子场景集合
	private List<ChildSceneModel> childSceneList;

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

	public List<ChildSceneModel> getChildSceneList() {
		return childSceneList;
	}

	public void setChildSceneList(List<ChildSceneModel> childSceneList) {
		this.childSceneList = childSceneList;
	}
	
	
}


