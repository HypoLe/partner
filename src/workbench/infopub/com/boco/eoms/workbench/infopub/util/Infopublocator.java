package com.boco.eoms.workbench.infopub.util;

import com.boco.eoms.base.util.ApplicationContextHolder;

public class Infopublocator {
	
	public static InfopubUtil InfopubUtilInstance(){
		return (InfopubUtil) ApplicationContextHolder.getInstance().getBean("defaultForums");
		
	}

}
