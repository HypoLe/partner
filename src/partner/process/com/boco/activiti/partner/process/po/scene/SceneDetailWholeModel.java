package com.boco.activiti.partner.process.po.scene;

import java.util.List;

/**
 * 	公客工单 场景模板 场景详情model类
 	* @author WangJun
 	* @ClassName: SceneDetailWholeModel
 	* @Version 版本 
 	* @ModifiedBy 修改人
 	* @Copyright Boco
 	* @date Apr 1, 2016 10:23:32 AM
 	* @description 类描述
 */
public class SceneDetailWholeModel {
	//主场景val值
	private String mainSceneValue;
	
	//主场景中文名
	private String mainSceneName;
	
	//主场景val值
	private String childSceneValue;
	
	//主场景中文名
	private String childSceneName;
	
	//包换的属性的值（可以理解为表头）
	private List<String> properties; 
	
	//明细
	//明细第一行 比较特殊
	private SceneDetailModel firstLineSceneDetail;
	
	//其他明细
	private List<SceneDetailModel> sceneDetailList;
	
	//明细条数（除掉表头的实际数据行数）
	private int sceneDetailListSize;
	
	//总的项目金额
	private String totalAmount;
	//该工单是否可以更改 子场景的单位数
	private String isHave;
	
	public String getIsHave() {
		return isHave;
	}

	public void setIsHave(String isHave) {
		this.isHave = isHave;
	}

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

	public List<String> getProperties() {
		return properties;
	}

	public void setProperties(List<String> properties) {
		this.properties = properties;
	}

	public List<SceneDetailModel> getSceneDetailList() {
		return sceneDetailList;
	}

	public void setSceneDetailList(List<SceneDetailModel> sceneDetailList) {
		this.sceneDetailList = sceneDetailList;
	}

	public int getSceneDetailListSize() {
		return sceneDetailListSize;
	}

	public void setSceneDetailListSize(int sceneDetailListSize) {
		this.sceneDetailListSize = sceneDetailListSize;
	}

	public SceneDetailModel getFirstLineSceneDetail() {
		return firstLineSceneDetail;
	}

	public void setFirstLineSceneDetail(SceneDetailModel firstLineSceneDetail) {
		this.firstLineSceneDetail = firstLineSceneDetail;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	
}
