package com.boco.activiti.partner.process.po;

import java.io.Serializable;

public class ShowInWalkNum implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	private String cityId;//地市id
	
	//应急 工单数量
	private double neednum_crash;//应急 工单发起 工单数量
	private double workchecknum_crash;//应急	工单发起审核 工单数量
	private double city_l_examinenum_crash;//应急 市线路主管审核 工单数量	
	private double city_l_auditnum_crash;//应急 市线路主任审核 工单数量	
	private double city_m_examinenum_crash;//应急 市运维主管审核 工单数量	
	private double city_m_auditnum_crash;//应急 市运维主任审核 工单数量	
	private double city_v_auditnum_crash;//应急 市公司副总审核 工单数量	
	private double pro_l_examinenum_crash;//应急 省线路主管审核 工单数量	
	private double pro_l_auditnum_crash;//应急 省线路总经理审核 工单数量	
	private double pro_m_examinenum_crash;//应急 省运维主管审核 工单数量	
	private double pro_m_auditnum_crash;//应急 省运维总经理审批 工单数量	
	
	//应急 工单金额
	private double needmoney_crash;//应急 工单发起 工单金额
	private double workcheckmoney_crash;//应急	工单发起审核 工单金额
	private double city_l_examinemoney_crash;//应急 市线路主管审核 工单金额	
	private double city_l_auditmoney_crash;//应急 市线路主任审核 工单金额	
	private double city_m_examinemoney_crash;//应急 市运维主管审核 工单金额		
	private double city_m_auditmoney_crash;//应急 市运维主任审核 工单金额
	private double city_v_auditmoney_crash;//应急 市公司副总审核 工单金额
	private double pro_l_examinemoney_crash;//应急 省线路主管审核 工单金额
	private double pro_l_auditmoney_crash;//应急 省线路总经理审核 工单金额
	private double pro_m_examinemoney_crash;//应急 省运维主管审核 工单金额
	private double pro_m_auditmoney_crash;//应急 省运维总经理审批 工单金额	
	
	//常规 工单数量
	private double neednum;//常规 工单发起 工单数量
	private double workchecknum;//常规 工单发起审核 工单数量
	private double city_l_examinenum;//常规 市线路主管审核 工单数量
	private double city_l_auditnum;//常规 市线路主任审核 工单数量
	private double city_m_examinenum;//常规 市运维主管审核 工单数量
	private double city_m_auditnum;//常规 市运维主任审核 工单数量
	private double city_v_auditnum;//常规 市公司副总审核 工单数量
	private double pro_l_examinenum;//常规 省线路主管审核 工单数量
	private double pro_l_auditnum;//常规 省线路总经理审核 工单数量
	private double pro_m_examinenum;//常规 省运维主管审核 工单数量
	private double pro_m_auditnum;//常规 省运维总经理审批 工单数量
	
	//常规 工单金额
	private double needmoney;//常规 工单发起 工单金额
	private double workcheckmoney;//常规 工单发起审核 工单金额
	private double city_l_examinemoney;//常规 市线路主管审核 工单金额
	private double city_l_auditmoney;//常规 市线路主任审核 工单金额
	private double city_m_examinemoney;//常规 市运维主管审核 工单金额
	private double city_m_auditmoney;//常规 市运维主任审核 工单金额
	private double city_v_auditmoney;//常规 市公司副总审核 工单金额
	private double pro_l_examinemoney;//常规 省线路主管审核 工单金额
	private double pro_l_auditmoney;//常规 省线路总经理审核 工单金额
	private double pro_m_examinemoney;//常规 省运维主管审核 工单金额
	private double pro_m_auditmoney;//常规 省运维总经理审批 工单金额
	
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public double getNeednum_crash() {
		return neednum_crash;
	}
	public void setNeednum_crash(double neednum_crash) {
		this.neednum_crash = neednum_crash;
	}
	public double getWorkchecknum_crash() {
		return workchecknum_crash;
	}
	public void setWorkchecknum_crash(double workchecknum_crash) {
		this.workchecknum_crash = workchecknum_crash;
	}
	public double getCity_l_examinenum_crash() {
		return city_l_examinenum_crash;
	}
	public void setCity_l_examinenum_crash(double city_l_examinenum_crash) {
		this.city_l_examinenum_crash = city_l_examinenum_crash;
	}
	public double getCity_l_auditnum_crash() {
		return city_l_auditnum_crash;
	}
	public void setCity_l_auditnum_crash(double city_l_auditnum_crash) {
		this.city_l_auditnum_crash = city_l_auditnum_crash;
	}
	public double getCity_m_examinenum_crash() {
		return city_m_examinenum_crash;
	}
	public void setCity_m_examinenum_crash(double city_m_examinenum_crash) {
		this.city_m_examinenum_crash = city_m_examinenum_crash;
	}
	public double getCity_m_auditnum_crash() {
		return city_m_auditnum_crash;
	}
	public void setCity_m_auditnum_crash(double city_m_auditnum_crash) {
		this.city_m_auditnum_crash = city_m_auditnum_crash;
	}
	public double getCity_v_auditnum_crash() {
		return city_v_auditnum_crash;
	}
	public void setCity_v_auditnum_crash(double city_v_auditnum_crash) {
		this.city_v_auditnum_crash = city_v_auditnum_crash;
	}
	public double getPro_l_examinenum_crash() {
		return pro_l_examinenum_crash;
	}
	public void setPro_l_examinenum_crash(double pro_l_examinenum_crash) {
		this.pro_l_examinenum_crash = pro_l_examinenum_crash;
	}
	public double getPro_l_auditnum_crash() {
		return pro_l_auditnum_crash;
	}
	public void setPro_l_auditnum_crash(double pro_l_auditnum_crash) {
		this.pro_l_auditnum_crash = pro_l_auditnum_crash;
	}
	public double getPro_m_examinenum_crash() {
		return pro_m_examinenum_crash;
	}
	public void setPro_m_examinenum_crash(double pro_m_examinenum_crash) {
		this.pro_m_examinenum_crash = pro_m_examinenum_crash;
	}
	public double getPro_m_auditnum_crash() {
		return pro_m_auditnum_crash;
	}
	public void setPro_m_auditnum_crash(double pro_m_auditnum_crash) {
		this.pro_m_auditnum_crash = pro_m_auditnum_crash;
	}
	public double getNeedmoney_crash() {
		return needmoney_crash;
	}
	public void setNeedmoney_crash(double needmoney_crash) {
		this.needmoney_crash = needmoney_crash;
	}
	public double getWorkcheckmoney_crash() {
		return workcheckmoney_crash;
	}
	public void setWorkcheckmoney_crash(double workcheckmoney_crash) {
		this.workcheckmoney_crash = workcheckmoney_crash;
	}
	public double getCity_l_examinemoney_crash() {
		return city_l_examinemoney_crash;
	}
	public void setCity_l_examinemoney_crash(double city_l_examinemoney_crash) {
		this.city_l_examinemoney_crash = city_l_examinemoney_crash;
	}
	public double getCity_l_auditmoney_crash() {
		return city_l_auditmoney_crash;
	}
	public void setCity_l_auditmoney_crash(double city_l_auditmoney_crash) {
		this.city_l_auditmoney_crash = city_l_auditmoney_crash;
	}
	public double getCity_m_examinemoney_crash() {
		return city_m_examinemoney_crash;
	}
	public void setCity_m_examinemoney_crash(double city_m_examinemoney_crash) {
		this.city_m_examinemoney_crash = city_m_examinemoney_crash;
	}
	public double getCity_m_auditmoney_crash() {
		return city_m_auditmoney_crash;
	}
	public void setCity_m_auditmoney_crash(double city_m_auditmoney_crash) {
		this.city_m_auditmoney_crash = city_m_auditmoney_crash;
	}
	public double getCity_v_auditmoney_crash() {
		return city_v_auditmoney_crash;
	}
	public void setCity_v_auditmoney_crash(double city_v_auditmoney_crash) {
		this.city_v_auditmoney_crash = city_v_auditmoney_crash;
	}
	public double getPro_l_examinemoney_crash() {
		return pro_l_examinemoney_crash;
	}
	public void setPro_l_examinemoney_crash(double pro_l_examinemoney_crash) {
		this.pro_l_examinemoney_crash = pro_l_examinemoney_crash;
	}
	public double getPro_l_auditmoney_crash() {
		return pro_l_auditmoney_crash;
	}
	public void setPro_l_auditmoney_crash(double pro_l_auditmoney_crash) {
		this.pro_l_auditmoney_crash = pro_l_auditmoney_crash;
	}
	public double getPro_m_examinemoney_crash() {
		return pro_m_examinemoney_crash;
	}
	public void setPro_m_examinemoney_crash(double pro_m_examinemoney_crash) {
		this.pro_m_examinemoney_crash = pro_m_examinemoney_crash;
	}
	public double getPro_m_auditmoney_crash() {
		return pro_m_auditmoney_crash;
	}
	public void setPro_m_auditmoney_crash(double pro_m_auditmoney_crash) {
		this.pro_m_auditmoney_crash = pro_m_auditmoney_crash;
	}
	public double getNeednum() {
		return neednum;
	}
	public void setNeednum(double neednum) {
		this.neednum = neednum;
	}
	public double getWorkchecknum() {
		return workchecknum;
	}
	public void setWorkchecknum(double workchecknum) {
		this.workchecknum = workchecknum;
	}
	public double getCity_l_examinenum() {
		return city_l_examinenum;
	}
	public void setCity_l_examinenum(double city_l_examinenum) {
		this.city_l_examinenum = city_l_examinenum;
	}
	public double getCity_l_auditnum() {
		return city_l_auditnum;
	}
	public void setCity_l_auditnum(double city_l_auditnum) {
		this.city_l_auditnum = city_l_auditnum;
	}
	public double getCity_m_examinenum() {
		return city_m_examinenum;
	}
	public void setCity_m_examinenum(double city_m_examinenum) {
		this.city_m_examinenum = city_m_examinenum;
	}
	public double getCity_m_auditnum() {
		return city_m_auditnum;
	}
	public void setCity_m_auditnum(double city_m_auditnum) {
		this.city_m_auditnum = city_m_auditnum;
	}
	public double getCity_v_auditnum() {
		return city_v_auditnum;
	}
	public void setCity_v_auditnum(double city_v_auditnum) {
		this.city_v_auditnum = city_v_auditnum;
	}
	public double getPro_l_examinenum() {
		return pro_l_examinenum;
	}
	public void setPro_l_examinenum(double pro_l_examinenum) {
		this.pro_l_examinenum = pro_l_examinenum;
	}
	public double getPro_l_auditnum() {
		return pro_l_auditnum;
	}
	public void setPro_l_auditnum(double pro_l_auditnum) {
		this.pro_l_auditnum = pro_l_auditnum;
	}
	public double getPro_m_examinenum() {
		return pro_m_examinenum;
	}
	public void setPro_m_examinenum(double pro_m_examinenum) {
		this.pro_m_examinenum = pro_m_examinenum;
	}
	public double getPro_m_auditnum() {
		return pro_m_auditnum;
	}
	public void setPro_m_auditnum(double pro_m_auditnum) {
		this.pro_m_auditnum = pro_m_auditnum;
	}
	public double getNeedmoney() {
		return needmoney;
	}
	public void setNeedmoney(double needmoney) {
		this.needmoney = needmoney;
	}
	public double getWorkcheckmoney() {
		return workcheckmoney;
	}
	public void setWorkcheckmoney(double workcheckmoney) {
		this.workcheckmoney = workcheckmoney;
	}
	public double getCity_l_examinemoney() {
		return city_l_examinemoney;
	}
	public void setCity_l_examinemoney(double city_l_examinemoney) {
		this.city_l_examinemoney = city_l_examinemoney;
	}
	public double getCity_l_auditmoney() {
		return city_l_auditmoney;
	}
	public void setCity_l_auditmoney(double city_l_auditmoney) {
		this.city_l_auditmoney = city_l_auditmoney;
	}
	public double getCity_m_examinemoney() {
		return city_m_examinemoney;
	}
	public void setCity_m_examinemoney(double city_m_examinemoney) {
		this.city_m_examinemoney = city_m_examinemoney;
	}
	public double getCity_m_auditmoney() {
		return city_m_auditmoney;
	}
	public void setCity_m_auditmoney(double city_m_auditmoney) {
		this.city_m_auditmoney = city_m_auditmoney;
	}
	public double getCity_v_auditmoney() {
		return city_v_auditmoney;
	}
	public void setCity_v_auditmoney(double city_v_auditmoney) {
		this.city_v_auditmoney = city_v_auditmoney;
	}
	public double getPro_l_examinemoney() {
		return pro_l_examinemoney;
	}
	public void setPro_l_examinemoney(double pro_l_examinemoney) {
		this.pro_l_examinemoney = pro_l_examinemoney;
	}
	public double getPro_l_auditmoney() {
		return pro_l_auditmoney;
	}
	public void setPro_l_auditmoney(double pro_l_auditmoney) {
		this.pro_l_auditmoney = pro_l_auditmoney;
	}
	public double getPro_m_examinemoney() {
		return pro_m_examinemoney;
	}
	public void setPro_m_examinemoney(double pro_m_examinemoney) {
		this.pro_m_examinemoney = pro_m_examinemoney;
	}
	public double getPro_m_auditmoney() {
		return pro_m_auditmoney;
	}
	public void setPro_m_auditmoney(double pro_m_auditmoney) {
		this.pro_m_auditmoney = pro_m_auditmoney;
	}
	
	
	

}
