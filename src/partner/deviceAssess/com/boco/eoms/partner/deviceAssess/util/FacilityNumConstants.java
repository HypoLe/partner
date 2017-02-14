package com.boco.eoms.partner.deviceAssess.util;

import com.boco.eoms.partner.deviceAssess.model.FacilityNum;

/**
 * <p>
 * Title:设备量信息
 * </p>
 * <p>
 * Description:设备量信息
 * </p>
 * <p>
 * Wed Sep 29 11:28:40 CST 2010
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public class FacilityNumConstants {
	
	/**
	 * list key
	 */
	public final static String FACILITYNUM_LIST = "facilityNumList";
	/**
	 * 交换
	 */	
	public final static String EXCHANGE = "1121601";
	/**
	 * GSM无线orTD无线
	 */	
	public final static String GSMTD = "1121602";
	/**
	 * IP承载网（单位：套）
	 */	
	public final static String IP = "1121603";
	/**
	 * 传输
	 */	
	public final static String TEANSFERS = "1121604";
	/**
	 * 数据业务 
	 */	
	public final static String DATEOPERATION = "1121605";
	/**
	 * 智能网
	 */	
	public final static String BRAINS = "1121606";
	/**
	 * CMNET
	 */	
	public final static String CMNET = "1121607";	
	
	public static int sumBackEstimate(String speciality,FacilityNum backEstimate){
		if(EXCHANGE.equals(speciality)){
			return backEstimate.getTmsc()+backEstimate.getMscserver()+backEstimate.getMgw()+backEstimate.getHlrvlr()+backEstimate.getStp();
		} else if (GSMTD.equals(speciality)){
			return backEstimate.getBsc()+backEstimate.getBts()+backEstimate.getRnc()+backEstimate.getNodeB();
		} else if (IP.equals(speciality)){
			return backEstimate.getCr()+backEstimate.getBr()+backEstimate.getAr()+backEstimate.getCe();
		} else if (TEANSFERS.equals(speciality)){
			return backEstimate.getWdm()+backEstimate.getOtnason()+backEstimate.getSdhmstp()+backEstimate.getPtn()+backEstimate.getPon();
		} else if (DATEOPERATION.equals(speciality)){
			return backEstimate.getGgsn()+backEstimate.getSgsn()+backEstimate.getNote()+backEstimate.getMultimediaMes()+backEstimate.getColoringRing()+backEstimate.getWap();
		} else if (BRAINS.equals(speciality)){
			return backEstimate.getScp()+backEstimate.getSmp()+backEstimate.getVc();
		} else if (CMNET.equals(speciality)){
			return backEstimate.getBb()+backEstimate.getBc()+backEstimate.getBi()+backEstimate.getPb()+backEstimate.getSw();
		}
		return  backEstimate.getTmsc()+backEstimate.getMscserver()+backEstimate.getMgw()+backEstimate.getHlrvlr()+backEstimate.getStp()+
				backEstimate.getBsc()+backEstimate.getBts()+backEstimate.getRnc()+backEstimate.getNodeB()+
				backEstimate.getCr()+backEstimate.getBr()+backEstimate.getAr()+backEstimate.getCe()+ 
				backEstimate.getWdm()+backEstimate.getOtnason()+backEstimate.getSdhmstp()+backEstimate.getPtn()+backEstimate.getPon()+
				backEstimate.getGgsn()+backEstimate.getSgsn()+backEstimate.getNote()+backEstimate.getMultimediaMes()+backEstimate.getColoringRing()+backEstimate.getWap()+
				backEstimate.getScp()+backEstimate.getSmp()+backEstimate.getVc()+
				backEstimate.getBb()+backEstimate.getBc()+backEstimate.getBi()+backEstimate.getPb()+backEstimate.getSw();
	}
	
//	public static int backEstimate(String type){
//		
//	}
}