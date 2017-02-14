package com.boco.eoms.materials.model;

/**
 * 字典表
 * 
 * @author wanghongliang
 * 
 */
public class SysDict {
	private String id;
	/**
	 * 字典名称
	 */
	private String dictName;
	/**
	 * 值
	 */
	private String dictId;
	/**
	 * 父ID
	 */
	private Integer parentDictId;
	/**
	 * 是否为叶子节点 1:是 0:否
	 */
	private Integer leaf;
	/**
	 * 备注
	 */
	private String dictRemark;

	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	public String getDictId() {
		return dictId;
	}

	public void setDictId(String dictId) {
		this.dictId = dictId;
	}

	public Integer getParentDictId() {
		return parentDictId;
	}

	public void setParentDictId(Integer parentDictId) {
		this.parentDictId = parentDictId;
	}

	

	public Integer getLeaf() {
		return leaf;
	}

	public void setLeaf(Integer leaf) {
		this.leaf = leaf;
	}

	public String getDictRemark() {
		return dictRemark;
	}

	public void setDictRemark(String dictRemark) {
		this.dictRemark = dictRemark;
	}

}
