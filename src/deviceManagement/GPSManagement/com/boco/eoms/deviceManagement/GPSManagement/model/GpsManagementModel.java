package com.boco.eoms.deviceManagement.GPSManagement.model;

import com.boco.eoms.base.model.BaseObject;


public class GpsManagementModel  extends BaseObject {
	private String id;
	private String gsmid;//GSM号码
	private String gpstype;//设备类型
	private String factory;//生产厂家
	private String gpsmodel;//型号
	private String gpsselfnumber;//机身序列号
	private String deptid;//所属部门
	private String remark;//备注 
	private String deleted;//是否删除
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	public String getGsmid() {
		return gsmid;
	}
	public void setGsmid(String gsmid) {
		this.gsmid = gsmid;
	}
	public String getDeptid() {
		return deptid;
	}
	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}
	public String getFactory() {
		return factory;
	}
	public void setFactory(String factory) {
		this.factory = factory;
	}
	public String getGpsmodel() {
		return gpsmodel;
	}
	public void setGpsmodel(String gpsmodel) {
		this.gpsmodel = gpsmodel;
	}
	public String getGpsselfnumber() {
		return gpsselfnumber;
	}
	public void setGpsselfnumber(String gpsselfnumber) {
		this.gpsselfnumber = gpsselfnumber;
	}
	public String getGpstype() {
		return gpstype;
	}
	public void setGpstype(String gpstype) {
		this.gpstype = gpstype;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		return false;
	}
}
