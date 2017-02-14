package com.boco.eoms.partner.sheet.commontask.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.eoms.sheet.base.service.impl.SheetInfoShowServiceImpl;

/** 
 * Description:  
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Oct 22, 2012 12:29:21 PM 
 */
public class PnrCommonTaskInfoShowServiceImpl extends
		SheetInfoShowServiceImpl {
	
	@SuppressWarnings("unchecked")
	public Map getAttachmentAttributeOfOjbect(){
		Map objectMap = new HashMap();
		List mainAttachmentAttributes = new ArrayList();
		mainAttachmentAttributes.add("sheetAccessories");

		List linkAttachmentAttributes = new ArrayList();
		linkAttachmentAttributes.add("nodeAccessories");
			
		objectMap.put("mainObject", mainAttachmentAttributes);
		objectMap.put("linkObject", linkAttachmentAttributes);
		return objectMap;
	}
}
