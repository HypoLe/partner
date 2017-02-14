package com.boco.eoms.commons.mms.base.foot;


public abstract class BaseFootInfo implements IFootInfo {

	public String nullObject2String(Object o) {
		String str = "";
		try {
			str = o.toString();
		} catch (Exception e) {
			return "";
		}
		return str;
	}

}
