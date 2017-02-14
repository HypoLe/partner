package com.boco.eoms.netresource.line.model;

import java.util.Date;

import com.boco.eoms.base.webapp.form.BaseForm;

/**
 * 线路信息管理
* @Title: 
* 
* @Description: TODO
* 
* @author WangGuangping  
* 
* @date Feb 15, 2012 6:55:43 PM
* 
* @version V1.0   
*
 */

public class LinesForm  extends BaseForm implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;//ID key
	
	private String lineNo; // 线路编号
	
	private String lineName; // 线路名称
	
	private String city; // 所属地市
	
	private String region; // 所属区县
	
	private String grid; // 所属网格
	
	private String partner; // 合作伙伴
	
	private String maintainType; // 维护类型
	
	private String level; // 线路等级
	
	private String manager; // 客户经理
	
	private String managerTel; // 客户经理电话
	
	private String contacter; // 联系人
	
	private String contacterTel; // 联系人电话
	
	private String remark; // 线路信息
	
	private String createTime; // 新增时间
	
	private String creator; // 新增人
	
	private String modifyTime; // 修改时间
	
	private String mender; // 修改人
	
	private String isdeleted; // 是否删除 0未删除 1删除
	
	private String openTime; // 开通时间
	
	private Integer userYear; // 使用年限
	
	private String beginLong; // 起点经度
	
	private String beginLat; // 起点纬度
	
	private String endLong; // 终点经度
	
	private String endLat; // 终点纬度
	
	private String errorScope;//误差
	
	private Integer orderId; //排序号

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLineNo() {
		return lineNo;
	}

	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getGrid() {
		return grid;
	}

	public void setGrid(String grid) {
		this.grid = grid;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getMaintainType() {
		return maintainType;
	}

	public void setMaintainType(String maintainType) {
		this.maintainType = maintainType;
	}

	public String getBeginLong() {
		return beginLong;
	}

	public void setBeginLong(String beginLong) {
		this.beginLong = beginLong;
	}

	public String getBeginLat() {
		return beginLat;
	}

	public void setBeginLat(String beginLat) {
		this.beginLat = beginLat;
	}

	public String getEndLong() {
		return endLong;
	}

	public void setEndLong(String endLong) {
		this.endLong = endLong;
	}

	public String getEndLat() {
		return endLat;
	}

	public void setEndLat(String endLat) {
		this.endLat = endLat;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getManagerTel() {
		return managerTel;
	}

	public void setManagerTel(String managerTel) {
		this.managerTel = managerTel;
	}

	public String getContacter() {
		return contacter;
	}

	public void setContacter(String contacter) {
		this.contacter = contacter;
	}

	public String getContacterTel() {
		return contacterTel;
	}

	public void setContacterTel(String contacterTel) {
		this.contacterTel = contacterTel;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getMender() {
		return mender;
	}

	public void setMender(String mender) {
		this.mender = mender;
	}

	public String getIsdeleted() {
		return isdeleted;
	}

	public void setIsdeleted(String isdeleted) {
		this.isdeleted = isdeleted;
	}

	public Integer getUserYear() {
		return userYear;
	}

	public void setUserYear(Integer userYear) {
		this.userYear = userYear;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public String getErrorScope() {
		return errorScope;
	}

	public void setErrorScope(String errorScope) {
		this.errorScope = errorScope;
	}

	
}
