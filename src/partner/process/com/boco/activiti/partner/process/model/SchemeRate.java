package com.boco.activiti.partner.process.model;

import java.io.Serializable;

public class SchemeRate  implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String county;
	private String time_month;
	private int interface_audit_num;
	private double interface_audit_money;
	private int interface_approved_num;
	private double interface_approved_money;
	private int interface_delay_approved_num;
	private double interface_delay_approved_money;
	private int interface_reject_num;
	private double interface_reject_money;
	private int interface_monthapproved_num;
	private double interface_monthapproved_money;
	
	private int artery_audit_num;
	private double artery_audit_money;
	private int artery_approved_num;
	private double artery_approved_money;
	private int artery_delay_approved_num;
	private double artery_delay_approved_money;
	private int artery_reject_num;
	private double artery_reject_money;
	private int artery_monthapproved_num;
	private double artery_monthapproved_money;
	
	private int m_artery_audit_num;
	private double m_artery_audit_money;
	private int m_artery_approved_num;
	private double m_artery_approved_money;
	private int m_artery_delay_approved_num;
	private double m_artery_delay_approved_money;
	private int m_artery_reject_num;
	private double m_artery_reject_money;
	private int m_artery_monthapproved_num;
	private double m_artery_monthapproved_money;
	
	private int ro_audit_num;
	private double ro_audit_money;
	private int ro_approved_num;
	private double ro_approved_money;
	private int ro_delay_approved_num;
	private double ro_delay_approved_money;
	private int ro_reject_num;
	private double ro_reject_money;
	private int ro_monthapproved_num;
	private double ro_monthapproved_money;
	
	private int qualified_num;
	private int reject_num;
	private String qualified_rate;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getTime_month() {
		return time_month;
	}
	public void setTime_month(String time_month) {
		this.time_month = time_month;
	}
	public int getInterface_audit_num() {
		return interface_audit_num;
	}
	public void setInterface_audit_num(int interface_audit_num) {
		this.interface_audit_num = interface_audit_num;
	}
	public double getInterface_audit_money() {
		return interface_audit_money;
	}
	public void setInterface_audit_money(double interface_audit_money) {
		this.interface_audit_money = interface_audit_money;
	}
	public int getInterface_approved_num() {
		return interface_approved_num;
	}
	public void setInterface_approved_num(int interface_approved_num) {
		this.interface_approved_num = interface_approved_num;
	}
	public double getInterface_approved_money() {
		return interface_approved_money;
	}
	public void setInterface_approved_money(double interface_approved_money) {
		this.interface_approved_money = interface_approved_money;
	}
	public int getInterface_delay_approved_num() {
		return interface_delay_approved_num;
	}
	public void setInterface_delay_approved_num(int interface_delay_approved_num) {
		this.interface_delay_approved_num = interface_delay_approved_num;
	}
	public double getInterface_delay_approved_money() {
		return interface_delay_approved_money;
	}
	public void setInterface_delay_approved_money(
			double interface_delay_approved_money) {
		this.interface_delay_approved_money = interface_delay_approved_money;
	}
	public int getInterface_reject_num() {
		return interface_reject_num;
	}
	public void setInterface_reject_num(int interface_reject_num) {
		this.interface_reject_num = interface_reject_num;
	}
	public double getInterface_reject_money() {
		return interface_reject_money;
	}
	public void setInterface_reject_money(double interface_reject_money) {
		this.interface_reject_money = interface_reject_money;
	}
	public int getInterface_monthapproved_num() {
		return interface_monthapproved_num;
	}
	public void setInterface_monthapproved_num(int interface_monthapproved_num) {
		this.interface_monthapproved_num = interface_monthapproved_num;
	}
	public double getInterface_monthapproved_money() {
		return interface_monthapproved_money;
	}
	public void setInterface_monthapproved_money(
			double interface_monthapproved_money) {
		this.interface_monthapproved_money = interface_monthapproved_money;
	}
	public int getArtery_audit_num() {
		return artery_audit_num;
	}
	public void setArtery_audit_num(int artery_audit_num) {
		this.artery_audit_num = artery_audit_num;
	}
	public double getArtery_audit_money() {
		return artery_audit_money;
	}
	public void setArtery_audit_money(double artery_audit_money) {
		this.artery_audit_money = artery_audit_money;
	}
	public int getArtery_approved_num() {
		return artery_approved_num;
	}
	public void setArtery_approved_num(int artery_approved_num) {
		this.artery_approved_num = artery_approved_num;
	}
	public double getArtery_approved_money() {
		return artery_approved_money;
	}
	public void setArtery_approved_money(double artery_approved_money) {
		this.artery_approved_money = artery_approved_money;
	}
	public int getArtery_delay_approved_num() {
		return artery_delay_approved_num;
	}
	public void setArtery_delay_approved_num(int artery_delay_approved_num) {
		this.artery_delay_approved_num = artery_delay_approved_num;
	}
	public double getArtery_delay_approved_money() {
		return artery_delay_approved_money;
	}
	public void setArtery_delay_approved_money(double artery_delay_approved_money) {
		this.artery_delay_approved_money = artery_delay_approved_money;
	}
	public int getArtery_reject_num() {
		return artery_reject_num;
	}
	public void setArtery_reject_num(int artery_reject_num) {
		this.artery_reject_num = artery_reject_num;
	}
	public double getArtery_reject_money() {
		return artery_reject_money;
	}
	public void setArtery_reject_money(double artery_reject_money) {
		this.artery_reject_money = artery_reject_money;
	}
	public int getArtery_monthapproved_num() {
		return artery_monthapproved_num;
	}
	public void setArtery_monthapproved_num(int artery_monthapproved_num) {
		this.artery_monthapproved_num = artery_monthapproved_num;
	}
	public double getArtery_monthapproved_money() {
		return artery_monthapproved_money;
	}
	public void setArtery_monthapproved_money(double artery_monthapproved_money) {
		this.artery_monthapproved_money = artery_monthapproved_money;
	}
	public int getM_artery_audit_num() {
		return m_artery_audit_num;
	}
	public void setM_artery_audit_num(int m_artery_audit_num) {
		this.m_artery_audit_num = m_artery_audit_num;
	}
	public double getM_artery_audit_money() {
		return m_artery_audit_money;
	}
	public void setM_artery_audit_money(double m_artery_audit_money) {
		this.m_artery_audit_money = m_artery_audit_money;
	}
	public int getM_artery_approved_num() {
		return m_artery_approved_num;
	}
	public void setM_artery_approved_num(int m_artery_approved_num) {
		this.m_artery_approved_num = m_artery_approved_num;
	}
	public double getM_artery_approved_money() {
		return m_artery_approved_money;
	}
	public void setM_artery_approved_money(double m_artery_approved_money) {
		this.m_artery_approved_money = m_artery_approved_money;
	}
	public int getM_artery_delay_approved_num() {
		return m_artery_delay_approved_num;
	}
	public void setM_artery_delay_approved_num(int m_artery_delay_approved_num) {
		this.m_artery_delay_approved_num = m_artery_delay_approved_num;
	}
	public double getM_artery_delay_approved_money() {
		return m_artery_delay_approved_money;
	}
	public void setM_artery_delay_approved_money(
			double m_artery_delay_approved_money) {
		this.m_artery_delay_approved_money = m_artery_delay_approved_money;
	}
	public int getM_artery_reject_num() {
		return m_artery_reject_num;
	}
	public void setM_artery_reject_num(int m_artery_reject_num) {
		this.m_artery_reject_num = m_artery_reject_num;
	}
	public double getM_artery_reject_money() {
		return m_artery_reject_money;
	}
	public void setM_artery_reject_money(double m_artery_reject_money) {
		this.m_artery_reject_money = m_artery_reject_money;
	}
	public int getM_artery_monthapproved_num() {
		return m_artery_monthapproved_num;
	}
	public void setM_artery_monthapproved_num(int m_artery_monthapproved_num) {
		this.m_artery_monthapproved_num = m_artery_monthapproved_num;
	}
	public double getM_artery_monthapproved_money() {
		return m_artery_monthapproved_money;
	}
	public void setM_artery_monthapproved_money(double m_artery_monthapproved_money) {
		this.m_artery_monthapproved_money = m_artery_monthapproved_money;
	}
	public int getRo_audit_num() {
		return ro_audit_num;
	}
	public void setRo_audit_num(int ro_audit_num) {
		this.ro_audit_num = ro_audit_num;
	}
	public double getRo_audit_money() {
		return ro_audit_money;
	}
	public void setRo_audit_money(double ro_audit_money) {
		this.ro_audit_money = ro_audit_money;
	}
	public int getRo_approved_num() {
		return ro_approved_num;
	}
	public void setRo_approved_num(int ro_approved_num) {
		this.ro_approved_num = ro_approved_num;
	}
	public double getRo_approved_money() {
		return ro_approved_money;
	}
	public void setRo_approved_money(double ro_approved_money) {
		this.ro_approved_money = ro_approved_money;
	}
	public int getRo_delay_approved_num() {
		return ro_delay_approved_num;
	}
	public void setRo_delay_approved_num(int ro_delay_approved_num) {
		this.ro_delay_approved_num = ro_delay_approved_num;
	}
	public double getRo_delay_approved_money() {
		return ro_delay_approved_money;
	}
	public void setRo_delay_approved_money(double ro_delay_approved_money) {
		this.ro_delay_approved_money = ro_delay_approved_money;
	}
	public int getRo_reject_num() {
		return ro_reject_num;
	}
	public void setRo_reject_num(int ro_reject_num) {
		this.ro_reject_num = ro_reject_num;
	}
	public double getRo_reject_money() {
		return ro_reject_money;
	}
	public void setRo_reject_money(double ro_reject_money) {
		this.ro_reject_money = ro_reject_money;
	}
	public int getRo_monthapproved_num() {
		return ro_monthapproved_num;
	}
	public void setRo_monthapproved_num(int ro_monthapproved_num) {
		this.ro_monthapproved_num = ro_monthapproved_num;
	}
	public double getRo_monthapproved_money() {
		return ro_monthapproved_money;
	}
	public void setRo_monthapproved_money(double ro_monthapproved_money) {
		this.ro_monthapproved_money = ro_monthapproved_money;
	}
	public int getQualified_num() {
		return qualified_num;
	}
	public void setQualified_num(int qualified_num) {
		this.qualified_num = qualified_num;
	}
	public int getReject_num() {
		return reject_num;
	}
	public void setReject_num(int reject_num) {
		this.reject_num = reject_num;
	}
	public String getQualified_rate() {
		return qualified_rate;
	}
	public void setQualified_rate(String qualified_rate) {
		this.qualified_rate = qualified_rate;
	}
}
