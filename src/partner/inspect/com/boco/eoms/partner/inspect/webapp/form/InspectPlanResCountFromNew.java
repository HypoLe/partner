package com.boco.eoms.partner.inspect.webapp.form;

import java.io.Serializable;

public class InspectPlanResCountFromNew implements Serializable{
	
	private String name;
	private String exportname;//导出用的
	private String cityName;
	private String cityId;
	private String countryName;
	private String countryId;
	private String personName;
	private String year;
	private String month;
	private String v_stage_a; //vip 基站 站内总数
	private String v_stage_w; //vip 基站 本月完成情况
	private String v_stage_d; //vip 基站 带巡检
	private String a_stage_a; //A 基站 站内总数
	private String a_stage_w; //A 基站 本月完成情况
	private String a_stage_d; //A 基站 带巡检
	private String b_stage_a; 
	private String b_stage_w; 
	private String b_stage_d;
	private String c_stage_a; 
	private String c_stage_w; 
	private String c_stage_d;
	private String a_accessNetwork_a; //接入网  a类  站内总数
	private String a_accessNetwork_w; //          本月完成情况
	private String a_accessNetwork_d; //          带巡检
	private String b_accessNetwork_a; //接入网  b类
	private String b_accessNetwork_w; 
	private String b_accessNetwork_d; 
	private String c_accessNetwork_a; //接入网  c类
	private String c_accessNetwork_w; 
	private String c_accessNetwork_d;
	private String j_tower_a;   //铁塔及天馈 季度  站内总数
	private String j_tower_w;   //铁塔及天馈 季度  本月完成情况
	private String j_tower_d;
	private String y_tower_a;   //铁塔及天馈 月标准  站内总数
	private String y_tower_w;
	private String y_tower_d;  
	private String z_distribution_a;   //市分 标准  站内总数
	private String z_distribution_w;
	private String z_distribution_d; 
	private String v_distribution_a;   //市分 vip  站内总数
	private String v_distribution_w;
	private String v_distribution_d; 
	private String a_distribution_a;   //市分 A类  站内总数
	private String a_distribution_w;
	private String a_distribution_d; 
	private String b_distribution_a;   //市分 B类  站内总数
	private String b_distribution_w;
	private String b_distribution_d; 
	private String a_wlan_a;   //wlan A类  站内总数
	private String a_wlan_w;
	private String a_wlan_d;   
	private String b_wlan_a;   //wlan B类  站内总数
	private String b_wlan_w;
	private String b_wlan_d;   
	private String c_wlan_a;   //wlan C类  站内总数
	private String c_wlan_w;
	private String c_wlan_d;
	
	private String v_jifang_a;
	private String v_jifang_w;
	private String v_jifang_d;
	private String a_jifang_a;
	private String a_jifang_w;
	private String a_jifang_d;
	private String b_jifang_a;
	private String b_jifang_w;
	private String b_jifang_d;
	private String c_jifang_a;
	private String c_jifang_w;
	private String c_jifang_d;
	
	
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getCountryId() {
		return countryId;
	}
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public String getV_stage_a() {
		return v_stage_a;
	}
	public void setV_stage_a(String v_stage_a) {
		this.v_stage_a = v_stage_a;
	}
	public String getV_stage_d() {
		return v_stage_d;
	}
	public void setV_stage_d(String v_stage_d) {
		this.v_stage_d = v_stage_d;
	}
	public String getA_stage_a() {
		return a_stage_a;
	}
	public void setA_stage_a(String a_stage_a) {
		this.a_stage_a = a_stage_a;
	}
	public String getA_stage_d() {
		return a_stage_d;
	}
	public void setA_stage_d(String a_stage_d) {
		this.a_stage_d = a_stage_d;
	}
	public String getB_stage_a() {
		return b_stage_a;
	}
	public void setB_stage_a(String b_stage_a) {
		this.b_stage_a = b_stage_a;
	}
	public String getB_stage_d() {
		return b_stage_d;
	}
	public void setB_stage_d(String b_stage_d) {
		this.b_stage_d = b_stage_d;
	}
	public String getC_stage_a() {
		return c_stage_a;
	}
	public void setC_stage_a(String c_stage_a) {
		this.c_stage_a = c_stage_a;
	}
	public String getC_stage_d() {
		return c_stage_d;
	}
	public void setC_stage_d(String c_stage_d) {
		this.c_stage_d = c_stage_d;
	}
	public String getA_accessNetwork_a() {
		return a_accessNetwork_a;
	}
	public void setA_accessNetwork_a(String network_a) {
		a_accessNetwork_a = network_a;
	}
	public String getA_accessNetwork_d() {
		return a_accessNetwork_d;
	}
	public void setA_accessNetwork_d(String network_d) {
		a_accessNetwork_d = network_d;
	}
	public String getB_accessNetwork_a() {
		return b_accessNetwork_a;
	}
	public void setB_accessNetwork_a(String network_a) {
		b_accessNetwork_a = network_a;
	}
	public String getB_accessNetwork_d() {
		return b_accessNetwork_d;
	}
	public void setB_accessNetwork_d(String network_d) {
		b_accessNetwork_d = network_d;
	}
	public String getC_accessNetwork_a() {
		return c_accessNetwork_a;
	}
	public void setC_accessNetwork_a(String network_a) {
		c_accessNetwork_a = network_a;
	}
	public String getC_accessNetwork_d() {
		return c_accessNetwork_d;
	}
	public void setC_accessNetwork_d(String network_d) {
		c_accessNetwork_d = network_d;
	}
	public String getJ_tower_a() {
		return j_tower_a;
	}
	public void setJ_tower_a(String j_tower_a) {
		this.j_tower_a = j_tower_a;
	}
	public String getJ_tower_d() {
		return j_tower_d;
	}
	public void setJ_tower_d(String j_tower_d) {
		this.j_tower_d = j_tower_d;
	}
	public String getY_tower_a() {
		return y_tower_a;
	}
	public void setY_tower_a(String y_tower_a) {
		this.y_tower_a = y_tower_a;
	}
	public String getY_tower_d() {
		return y_tower_d;
	}
	public void setY_tower_d(String y_tower_d) {
		this.y_tower_d = y_tower_d;
	}
	public String getZ_distribution_a() {
		return z_distribution_a;
	}
	public void setZ_distribution_a(String z_distribution_a) {
		this.z_distribution_a = z_distribution_a;
	}
	public String getZ_distribution_d() {
		return z_distribution_d;
	}
	public void setZ_distribution_d(String z_distribution_d) {
		this.z_distribution_d = z_distribution_d;
	}
	public String getV_distribution_a() {
		return v_distribution_a;
	}
	public void setV_distribution_a(String v_distribution_a) {
		this.v_distribution_a = v_distribution_a;
	}
	public String getV_distribution_d() {
		return v_distribution_d;
	}
	public void setV_distribution_d(String v_distribution_d) {
		this.v_distribution_d = v_distribution_d;
	}
	public String getA_distribution_a() {
		return a_distribution_a;
	}
	public void setA_distribution_a(String a_distribution_a) {
		this.a_distribution_a = a_distribution_a;
	}
	public String getA_distribution_d() {
		return a_distribution_d;
	}
	public void setA_distribution_d(String a_distribution_d) {
		this.a_distribution_d = a_distribution_d;
	}
	public String getB_distribution_a() {
		return b_distribution_a;
	}
	public void setB_distribution_a(String b_distribution_a) {
		this.b_distribution_a = b_distribution_a;
	}
	public String getB_distribution_d() {
		return b_distribution_d;
	}
	public void setB_distribution_d(String b_distribution_d) {
		this.b_distribution_d = b_distribution_d;
	}
	public String getA_wlan_a() {
		return a_wlan_a;
	}
	public void setA_wlan_a(String a_wlan_a) {
		this.a_wlan_a = a_wlan_a;
	}
	public String getA_wlan_d() {
		return a_wlan_d;
	}
	public void setA_wlan_d(String a_wlan_d) {
		this.a_wlan_d = a_wlan_d;
	}
	public String getB_wlan_a() {
		return b_wlan_a;
	}
	public void setB_wlan_a(String b_wlan_a) {
		this.b_wlan_a = b_wlan_a;
	}
	public String getB_wlan_d() {
		return b_wlan_d;
	}
	public void setB_wlan_d(String b_wlan_d) {
		this.b_wlan_d = b_wlan_d;
	}
	public String getC_wlan_a() {
		return c_wlan_a;
	}
	public void setC_wlan_a(String c_wlan_a) {
		this.c_wlan_a = c_wlan_a;
	}
	public String getC_wlan_d() {
		return c_wlan_d;
	}
	public void setC_wlan_d(String c_wlan_d) {
		this.c_wlan_d = c_wlan_d;
	}
	public String getV_stage_w() {
		return v_stage_w;
	}
	public void setV_stage_w(String v_stage_w) {
		this.v_stage_w = v_stage_w;
	}
	public String getA_stage_w() {
		return a_stage_w;
	}
	public void setA_stage_w(String a_stage_w) {
		this.a_stage_w = a_stage_w;
	}
	public String getB_stage_w() {
		return b_stage_w;
	}
	public void setB_stage_w(String b_stage_w) {
		this.b_stage_w = b_stage_w;
	}
	public String getC_stage_w() {
		return c_stage_w;
	}
	public void setC_stage_w(String c_stage_w) {
		this.c_stage_w = c_stage_w;
	}
	public String getA_accessNetwork_w() {
		return a_accessNetwork_w;
	}
	public void setA_accessNetwork_w(String network_w) {
		a_accessNetwork_w = network_w;
	}
	public String getB_accessNetwork_w() {
		return b_accessNetwork_w;
	}
	public void setB_accessNetwork_w(String network_w) {
		b_accessNetwork_w = network_w;
	}
	public String getC_accessNetwork_w() {
		return c_accessNetwork_w;
	}
	public void setC_accessNetwork_w(String network_w) {
		c_accessNetwork_w = network_w;
	}
	public String getJ_tower_w() {
		return j_tower_w;
	}
	public void setJ_tower_w(String j_tower_w) {
		this.j_tower_w = j_tower_w;
	}
	public String getY_tower_w() {
		return y_tower_w;
	}
	public void setY_tower_w(String y_tower_w) {
		this.y_tower_w = y_tower_w;
	}
	public String getZ_distribution_w() {
		return z_distribution_w;
	}
	public void setZ_distribution_w(String z_distribution_w) {
		this.z_distribution_w = z_distribution_w;
	}
	public String getV_distribution_w() {
		return v_distribution_w;
	}
	public void setV_distribution_w(String v_distribution_w) {
		this.v_distribution_w = v_distribution_w;
	}
	public String getA_distribution_w() {
		return a_distribution_w;
	}
	public void setA_distribution_w(String a_distribution_w) {
		this.a_distribution_w = a_distribution_w;
	}
	public String getB_distribution_w() {
		return b_distribution_w;
	}
	public void setB_distribution_w(String b_distribution_w) {
		this.b_distribution_w = b_distribution_w;
	}
	public String getA_wlan_w() {
		return a_wlan_w;
	}
	public void setA_wlan_w(String a_wlan_w) {
		this.a_wlan_w = a_wlan_w;
	}
	public String getB_wlan_w() {
		return b_wlan_w;
	}
	public void setB_wlan_w(String b_wlan_w) {
		this.b_wlan_w = b_wlan_w;
	}
	public String getC_wlan_w() {
		return c_wlan_w;
	}
	public void setC_wlan_w(String c_wlan_w) {
		this.c_wlan_w = c_wlan_w;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getExportname() {
		return exportname;
	}
	public void setExportname(String exportname) {
		this.exportname = exportname;
	}
	public String getV_jifang_a() {
		return v_jifang_a;
	}
	public void setV_jifang_a(String v_jifang_a) {
		this.v_jifang_a = v_jifang_a;
	}
	public String getV_jifang_w() {
		return v_jifang_w;
	}
	public void setV_jifang_w(String v_jifang_w) {
		this.v_jifang_w = v_jifang_w;
	}
	public String getV_jifang_d() {
		return v_jifang_d;
	}
	public void setV_jifang_d(String v_jifang_d) {
		this.v_jifang_d = v_jifang_d;
	}
	public String getA_jifang_a() {
		return a_jifang_a;
	}
	public void setA_jifang_a(String a_jifang_a) {
		this.a_jifang_a = a_jifang_a;
	}
	public String getA_jifang_w() {
		return a_jifang_w;
	}
	public void setA_jifang_w(String a_jifang_w) {
		this.a_jifang_w = a_jifang_w;
	}
	public String getA_jifang_d() {
		return a_jifang_d;
	}
	public void setA_jifang_d(String a_jifang_d) {
		this.a_jifang_d = a_jifang_d;
	}
	public String getB_jifang_a() {
		return b_jifang_a;
	}
	public void setB_jifang_a(String b_jifang_a) {
		this.b_jifang_a = b_jifang_a;
	}
	public String getB_jifang_w() {
		return b_jifang_w;
	}
	public void setB_jifang_w(String b_jifang_w) {
		this.b_jifang_w = b_jifang_w;
	}
	public String getB_jifang_d() {
		return b_jifang_d;
	}
	public void setB_jifang_d(String b_jifang_d) {
		this.b_jifang_d = b_jifang_d;
	}
	public String getC_jifang_a() {
		return c_jifang_a;
	}
	public void setC_jifang_a(String c_jifang_a) {
		this.c_jifang_a = c_jifang_a;
	}
	public String getC_jifang_w() {
		return c_jifang_w;
	}
	public void setC_jifang_w(String c_jifang_w) {
		this.c_jifang_w = c_jifang_w;
	}
	public String getC_jifang_d() {
		return c_jifang_d;
	}
	public void setC_jifang_d(String c_jifang_d) {
		this.c_jifang_d = c_jifang_d;
	}
	
}
