package com.boco.eoms.partner.assess.AssAutoCollection.model;

import com.boco.eoms.base.model.BaseObject;

/**
 * <p>
 * Title:采集类型
 * </p>
 * <p>
 * Description:采集类型
 * </p>
 * <p>
 * Thu Mar 31 09:11:04 CST 2011
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public class AssCollectionType extends BaseObject {

	/**
	 * 主键
	 */
	private String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 *
	 * 分类名称
	 *
	 */
	private java.lang.String name;
   
	public void setName(java.lang.String name){
		this.name= name;       
	}
   
	public java.lang.String getName(){
		return this.name;
	}

	/**
	 *
	 * 服务地址
	 *
	 */
	private java.lang.String serviceAddr;
   
	public void setServiceAddr(java.lang.String serviceAddr){
		this.serviceAddr= serviceAddr;       
	}
   
	public java.lang.String getServiceAddr(){
		return this.serviceAddr;
	}

	/**
	 *
	 * 对应树图节点Id
	 *
	 */
	private java.lang.String treeNodeId;
   
	public void setTreeNodeId(java.lang.String treeNodeId){
		this.treeNodeId= treeNodeId;       
	}
   
	public java.lang.String getTreeNodeId(){
		return this.treeNodeId;
	}

	/**
	 *
	 * 数据库用户名称
	 *
	 */
	private java.lang.String userName;

	public java.lang.String getUserName() {
		return userName;
	}

	public void setUserName(java.lang.String userName) {
		this.userName = userName;
	}
	
	/**
	 *
	 * 数据库密码
	 *
	 */
	private java.lang.String password;	

	public java.lang.String getPassword() {
		return password;
	}

	public void setPassword(java.lang.String password) {
		this.password = password;
	}
	
	public boolean equals(Object o) {
		if( o instanceof AssCollectionType ) {
			AssCollectionType assCollectionType=(AssCollectionType)o;
			if (this.id != null || this.id.equals(assCollectionType.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

}