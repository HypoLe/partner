package com.boco.eoms.partner.netresource.model.trans;

import com.boco.eoms.partner.netresource.model.EomsModel;

/**
 * 类说明：网络资源--传输线路--光交接箱
 * 创建人： zhangkeqi
 * 创建时间：2012-11-27 18:51:08
 */
public class IrmsTransOdf extends EomsModel{

	/**主键*/
	private String id;
		
	/**光电交接箱名称，作为业务主键，命名要求唯一。在光缆/电缆之间起交接作用的设备，它由多个连接纤芯/电缆的端子组成。[例]重庆新牌坊/GJ001*/
	private String odfName;
		
	/**CQXPF/GJ001*/
	private String odfNo;
		
	/**关联到【空间资源】-【区域】表-【区域名称】*/
	private String relatedDistrict;
		
	/**设备当前所处状态，在下拉框中选择，包括：工程、在网、退网。*/
	private String status;
		
	/**设备的供货厂家，在下拉框中选择。*/
	private String vendor;
		
	/**光交接箱的具体位置*/
	private String position;
		
	/**经度*/
	private String longitude;
		
	/**纬度*/
	private String latitude;
		
	/**型号*/
	private String type;
		
	/**单面、双面*/
	private String faceNum;
		
	/**每面行数*/
	private String eachFaceRowNum;
		
	/**每面列数*/
	private String eachFaceColNum;
		
	/**自建、合建、共建、附挂附穿、租用、购买、置换,默认为自建*/
	private String property;
		
	/**枚举值：自用、出租、共享*/
	private String use;
		
	/**使用单位*/
	private String useUnit;
		
	/**所有权人*/
	private String owner;
		
	/**容量*/
	private String capacity;
		
	/**备注*/
	private String remark;
		
	/**创建时间*/
	private String createTime;
		
	/**所属区域ID*/
	private String relatedDistirctId;
		

	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public String getOdfName() {
		return this.odfName;
	}
	
	public void setOdfName(String odfName) {
		this.odfName = odfName;
	}
	public String getOdfNo() {
		return this.odfNo;
	}
	
	public void setOdfNo(String odfNo) {
		this.odfNo = odfNo;
	}
	public String getRelatedDistrict() {
		return this.relatedDistrict;
	}
	
	public void setRelatedDistrict(String relatedDistrict) {
		this.relatedDistrict = relatedDistrict;
	}
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	public String getVendor() {
		return this.vendor;
	}
	
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public String getPosition() {
		return this.position;
	}
	
	public void setPosition(String position) {
		this.position = position;
	}
	public String getLongitude() {
		return this.longitude;
	}
	
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return this.latitude;
	}
	
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getType() {
		return this.type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	public String getFaceNum() {
		return this.faceNum;
	}
	
	public void setFaceNum(String faceNum) {
		this.faceNum = faceNum;
	}
	public String getEachFaceRowNum() {
		return this.eachFaceRowNum;
	}
	
	public void setEachFaceRowNum(String eachFaceRowNum) {
		this.eachFaceRowNum = eachFaceRowNum;
	}
	public String getEachFaceColNum() {
		return this.eachFaceColNum;
	}
	
	public void setEachFaceColNum(String eachFaceColNum) {
		this.eachFaceColNum = eachFaceColNum;
	}
	public String getProperty() {
		return this.property;
	}
	
	public void setProperty(String property) {
		this.property = property;
	}
	public String getUse() {
		return this.use;
	}
	
	public void setUse(String use) {
		this.use = use;
	}
	public String getUseUnit() {
		return this.useUnit;
	}
	
	public void setUseUnit(String useUnit) {
		this.useUnit = useUnit;
	}
	public String getOwner() {
		return this.owner;
	}
	
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getCapacity() {
		return this.capacity;
	}
	
	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getRelatedDistirctId() {
		return this.relatedDistirctId;
	}
	
	public void setRelatedDistirctId(String relatedDistirctId) {
		this.relatedDistirctId = relatedDistirctId;
	}
	
}
