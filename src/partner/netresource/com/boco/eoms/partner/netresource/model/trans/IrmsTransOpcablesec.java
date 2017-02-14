package com.boco.eoms.partner.netresource.model.trans;

import com.boco.eoms.partner.netresource.model.EomsModel;

/**
 * 类说明：网络资源--传输线路--光缆段
 * 创建人： zhangkeqi
 * 创建时间：2012-11-27 18:51:08
 */
public class IrmsTransOpcablesec extends EomsModel{

	/**主键*/
	private String id;
		
	/**光缆段名称，作为业务主键，命名要求唯一。由多根光纤组成（如24芯、48芯），位于相邻局站之间的光缆连接部分，光缆段经过若干光交接点设备，若干光缆段构成光缆线路。[例]YXH-1号井-接头盒-SF-10号井-接头盒*/
	private String opcablesectionName;
		
	/**关联到【光缆表】-【光缆名称】*/
	private String relatedOpcable;
		
	/**始端名称，命名需规范以实现数据关联。*/
	private String startPointName;
		
	/**末端名称，命名需规范以实现数据关联。*/
	private String endPointName;
		
	/**描述此光缆段从一局站出到另外一局站入顺序经过了哪些外线设施，以逗号进行分隔。注意命名规范。例如：引上1,管道段2,引上3,杆路段4,直埋5,挂墙6。*/
	private String opcableConnectFacilities;
		
	/**纤芯数*/
	private String opcableCoreNum;
		
	/**如：GYTS-48、GYTA-24等。*/
	private String opcableType;
		
	/**光缆生产厂家*/
	private String vendor;
		
	/**自建、合建、共建、附挂附穿、租用、购买、置换*/
	private String property;
		
	/**使用单位*/
	private String useUnit;
		
	/**所有权人*/
	private String owner;
		
	/**枚举值：自用、出租、共享。*/
	private String use;
		
	/**皮长，单位公里。例如：500。*/
	private String length;
		
	/**备注*/
	private String remark;
		
	/**创建时间*/
	private String createTime;
		
	/**所属光缆ID*/
	private String relatedOpcableId;
		

	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public String getOpcablesectionName() {
		return this.opcablesectionName;
	}
	
	public void setOpcablesectionName(String opcablesectionName) {
		this.opcablesectionName = opcablesectionName;
	}
	public String getRelatedOpcable() {
		return this.relatedOpcable;
	}
	
	public void setRelatedOpcable(String relatedOpcable) {
		this.relatedOpcable = relatedOpcable;
	}
	public String getStartPointName() {
		return this.startPointName;
	}
	
	public void setStartPointName(String startPointName) {
		this.startPointName = startPointName;
	}
	public String getEndPointName() {
		return this.endPointName;
	}
	
	public void setEndPointName(String endPointName) {
		this.endPointName = endPointName;
	}
	public String getOpcableConnectFacilities() {
		return this.opcableConnectFacilities;
	}
	
	public void setOpcableConnectFacilities(String opcableConnectFacilities) {
		this.opcableConnectFacilities = opcableConnectFacilities;
	}
	public String getOpcableCoreNum() {
		return this.opcableCoreNum;
	}
	
	public void setOpcableCoreNum(String opcableCoreNum) {
		this.opcableCoreNum = opcableCoreNum;
	}
	public String getOpcableType() {
		return this.opcableType;
	}
	
	public void setOpcableType(String opcableType) {
		this.opcableType = opcableType;
	}
	public String getVendor() {
		return this.vendor;
	}
	
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public String getProperty() {
		return this.property;
	}
	
	public void setProperty(String property) {
		this.property = property;
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
	public String getUse() {
		return this.use;
	}
	
	public void setUse(String use) {
		this.use = use;
	}
	public String getLength() {
		return this.length;
	}
	
	public void setLength(String length) {
		this.length = length;
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
	public String getRelatedOpcableId() {
		return this.relatedOpcableId;
	}
	
	public void setRelatedOpcableId(String relatedOpcableId) {
		this.relatedOpcableId = relatedOpcableId;
	}
	
}
