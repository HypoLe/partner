package com.boco.eoms.partner.assess.AssWeight.model;

import com.boco.eoms.base.model.BaseObject;

/**
 * <p>
 * Title:节点个性权重
 * </p>
 * <p>
 * Description:记录各地市节点的个性权重
 * </p>
 * <p>
 * Date:Oct 19, 2009 2:56:30 PM
 * </p>
 * 
 * @author 戴志刚
 * @version 2.0
 * 
 */
public class AssWeight extends BaseObject{

	/**
	 * 主键
	 */
	private String id;

	/**
	 * 节点Id
	 */
	private String nodeId;

	/**
	 * 节点个性权重
	 */
	private float weight;

	/**
	 * 地域信息
	 */
	private String area;
	
	/**
	 *地域名称 
	 */
	private String areaName ;
	

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public boolean equals(Object o) {
		return this == o;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
}
