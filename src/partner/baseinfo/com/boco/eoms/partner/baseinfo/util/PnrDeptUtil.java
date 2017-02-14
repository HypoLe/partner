package com.boco.eoms.partner.baseinfo.util;

import utils.PartnerPrivUtils;

public class PnrDeptUtil {
	public static String getRightForGoBack(String id,String type) {
		String hasRightGoBack="";
		if ("areaId".equals(type)) {
			int idLen=id.length();
			if (idLen==PartnerPrivUtils.AreaId_length_Province) {
				return hasRightGoBack="1";
			}else if (idLen==PartnerPrivUtils.AreaId_length_City) {
				return hasRightGoBack="2";
			}else if (idLen==PartnerPrivUtils.AreaId_length_County) {
				return hasRightGoBack="3";
			}
		}else{
			int idLen=id.length();
			if (idLen==PartnerPrivUtils.getProvinceDeptLength()) {
				return hasRightGoBack="1";
			}else if (idLen==PartnerPrivUtils.getCityDeptLength()) {
				return hasRightGoBack="2";
			}else if (idLen==PartnerPrivUtils.getCountyDeptLength()) {
				return hasRightGoBack="3";
			}else if (idLen==PartnerPrivUtils.getGroupDeptLength()) {
				return hasRightGoBack="4";
			}
		}
		return hasRightGoBack;
	}
}
